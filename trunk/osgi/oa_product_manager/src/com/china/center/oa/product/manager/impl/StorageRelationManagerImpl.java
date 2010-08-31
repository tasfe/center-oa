/**
 * File Name: StorageRelationManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-25<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.manager.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.StorageBean;
import com.china.center.oa.product.bean.StorageLogBean;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.DepotpartDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.StorageDAO;
import com.china.center.oa.product.dao.StorageLogDAO;
import com.china.center.oa.product.dao.StorageRelationDAO;
import com.china.center.oa.product.manager.StorageRelationManager;
import com.china.center.oa.product.vs.StorageRelationBean;
import com.china.center.oa.product.wrap.ProductChangeWrap;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.tools.TimeTools;


/**
 * StorageRelationManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-25
 * @see StorageRelationManagerImpl
 * @since 1.0
 */
public class StorageRelationManagerImpl implements StorageRelationManager
{
    private final Log _logger = LogFactory.getLog(getClass());

    private PlatformTransactionManager transactionManager = null;

    private StorageLogDAO storageLogDAO = null;

    private DepotDAO depotDAO = null;

    private DepotpartDAO depotpartDAO = null;

    private StorageDAO storageDAO = null;

    private ProductDAO productDAO = null;

    private CommonDAO commonDAO = null;

    private StorageRelationDAO storageRelationDAO = null;

    /**
     * default constructor
     */
    public StorageRelationManagerImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.StorageRelationManager#changeStorageRelationWithTransaction(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.wrap.ProductChangeWrap)
     */
    public synchronized boolean changeStorageRelationWithoutTransaction(User user, ProductChangeWrap bean)
        throws MYException
    {
        StorageBean storageBean = storageDAO.find(bean.getStorageId());

        if (storageBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        DepotpartBean depotpartBean = depotpartDAO.find(storageBean.getDepotpartId());

        if (depotpartBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        DepotBean depotBean = depotDAO.find(depotpartBean.getLocationId());

        if (depotBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        ProductBean productBean = productDAO.find(bean.getProductId());

        if (productBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (productBean.getAbstractType() == ProductConstant.ABSTRACT_TYPE_YES)
        {
            throw new MYException("虚拟产品没有库存,请确认操作");
        }

        StorageRelationBean relation = storageRelationDAO.findByStorageIdAndProductIdAndPriceKey(bean.getStorageId(),
            bean.getProductId(), bean.getPriceKey());

        if (relation == null && bean.getChange() < 0)
        {
            throw new MYException("仓库[%s]下仓区[%s]下储位[%s]的产品[%s]库存不够,当前库存为[%d],需要使用[%d]", depotBean.getName(),
                depotpartBean.getName(), storageBean.getName(), productBean.getName(), 0, -bean.getChange());
        }

        if (relation == null && bean.getChange() >= 0)
        {
            // 增加一个空的库存
            StorageRelationBean newStorageRelation = new StorageRelationBean();

            newStorageRelation.setId(commonDAO.getSquenceString20());
            newStorageRelation.setStorageId(bean.getStorageId());
            newStorageRelation.setLocationId(depotBean.getId());
            newStorageRelation.setDepotpartId(depotpartBean.getId());
            newStorageRelation.setPrice(bean.getPrice());
            newStorageRelation.setPriceKey(bean.getPriceKey());
            newStorageRelation.setAmount(0);
            newStorageRelation.setLastPrice(bean.getPrice());

            storageRelationDAO.saveEntityBean(newStorageRelation);

            relation = newStorageRelation;
        }

        // 查看库存大小
        if (relation.getAmount() + bean.getChange() < 0)
        {
            throw new MYException("仓库[%s]下仓区[%s]下储位[%s]的产品[%s]库存不够,当前库存为[%d],需要使用[%d]", depotBean.getName(),
                depotpartBean.getName(), storageBean.getName(), productBean.getName(), relation.getAmount(), -bean
                    .getChange());
        }

        // 之前储位内产品的数量
        int preAmount = storageRelationDAO.sumProductInStorage(bean.getProductId(), bean.getStorageId());

        int preAmount1 = storageRelationDAO.sumProductInDepotpartId(bean.getProductId(), depotpartBean.getId());

        int newAmount = relation.getAmount() + bean.getChange();

        // 库存数量符合
        relation.setAmount(newAmount);

        storageRelationDAO.updateEntityBean(relation);

        // 之后储位内产品的数量
        int afterAmount = storageRelationDAO.sumProductInStorage(bean.getProductId(), bean.getStorageId());

        int afterAmount1 = storageRelationDAO.sumProductInDepotpartId(bean.getProductId(), depotpartBean.getId());

        // save log
        // 记录仓区的产品异动数量
        StorageLogBean log = new StorageLogBean();

        log.setProductId(bean.getProductId());
        log.setLocationId(depotBean.getId());
        log.setDepotpartId(depotpartBean.getId());
        log.setStorageId(bean.getStorageId());

        log.setType(bean.getType());

        log.setPreAmount(preAmount);

        log.setAfterAmount(afterAmount);

        log.setPreAmount1(preAmount1);

        log.setAfterAmount1(afterAmount1);

        log.setChangeAmount(bean.getChange());

        log.setLogTime(TimeTools.now());

        log.setUser(user.getStafferName());

        log.setDescription(bean.getDescription());

        storageLogDAO.saveEntityBean(log);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.StorageRelationManager#changeStorageRelationWithoutTransaction(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.wrap.ProductChangeWrap)
     */
    public synchronized boolean changeStorageRelationWithTransaction(final User user, final ProductChangeWrap bean)
        throws MYException
    {
        try
        {
            // 增加管理员操作在数据库事务中完成
            TransactionTemplate tran = new TransactionTemplate(transactionManager);

            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    try
                    {
                        changeStorageRelationWithoutTransaction(user, bean);
                    }
                    catch (MYException e)
                    {
                        throw new RuntimeException(e.getErrorContent());
                    }

                    return Boolean.TRUE;
                }
            });
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            throw new MYException(e.getCause().toString());
        }

        return true;
    }

    /**
     * @return the transactionManager
     */
    public PlatformTransactionManager getTransactionManager()
    {
        return transactionManager;
    }

    /**
     * @param transactionManager
     *            the transactionManager to set
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager)
    {
        this.transactionManager = transactionManager;
    }

    /**
     * @return the storageLogDAO
     */
    public StorageLogDAO getStorageLogDAO()
    {
        return storageLogDAO;
    }

    /**
     * @param storageLogDAO
     *            the storageLogDAO to set
     */
    public void setStorageLogDAO(StorageLogDAO storageLogDAO)
    {
        this.storageLogDAO = storageLogDAO;
    }

    /**
     * @return the storageRelationDAO
     */
    public StorageRelationDAO getStorageRelationDAO()
    {
        return storageRelationDAO;
    }

    /**
     * @param storageRelationDAO
     *            the storageRelationDAO to set
     */
    public void setStorageRelationDAO(StorageRelationDAO storageRelationDAO)
    {
        this.storageRelationDAO = storageRelationDAO;
    }

    /**
     * @return the depotDAO
     */
    public DepotDAO getDepotDAO()
    {
        return depotDAO;
    }

    /**
     * @param depotDAO
     *            the depotDAO to set
     */
    public void setDepotDAO(DepotDAO depotDAO)
    {
        this.depotDAO = depotDAO;
    }

    /**
     * @return the depotpartDAO
     */
    public DepotpartDAO getDepotpartDAO()
    {
        return depotpartDAO;
    }

    /**
     * @param depotpartDAO
     *            the depotpartDAO to set
     */
    public void setDepotpartDAO(DepotpartDAO depotpartDAO)
    {
        this.depotpartDAO = depotpartDAO;
    }

    /**
     * @return the storageDAO
     */
    public StorageDAO getStorageDAO()
    {
        return storageDAO;
    }

    /**
     * @param storageDAO
     *            the storageDAO to set
     */
    public void setStorageDAO(StorageDAO storageDAO)
    {
        this.storageDAO = storageDAO;
    }

    /**
     * @return the productDAO
     */
    public ProductDAO getProductDAO()
    {
        return productDAO;
    }

    /**
     * @param productDAO
     *            the productDAO to set
     */
    public void setProductDAO(ProductDAO productDAO)
    {
        this.productDAO = productDAO;
    }

    /**
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }

}
