/*
 * File Name: StorageDAO.java CopyRight: Copyright by www.center.china
 * Description: Creater: zhuAchen CreateTime: 2007-12-15 Grant: open source to
 * everybody
 */
package com.china.centet.yongyin.manager;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.china.center.common.ConditionParse;
import com.china.center.common.MYException;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.centet.yongyin.bean.DepotpartBean;
import com.china.centet.yongyin.bean.LogBean;
import com.china.centet.yongyin.bean.Product;
import com.china.centet.yongyin.bean.StorageBean;
import com.china.centet.yongyin.bean.StorageLogBean;
import com.china.centet.yongyin.bean.StorageRelationBean;
import com.china.centet.yongyin.constant.Constant;
import com.china.centet.yongyin.constant.LockConstant;
import com.china.centet.yongyin.constant.OutConstanst;
import com.china.centet.yongyin.dao.DepotpartDAO;
import com.china.centet.yongyin.dao.ProductDAO;
import com.china.centet.yongyin.dao.StorageDAO;
import com.china.centet.yongyin.dao.UserDAO;
import com.china.centet.yongyin.vo.StorageBeanVO;


/**
 * ��λ���߼�����
 * 
 * @author zhuzhu
 * @version 2007-12-15
 * @see
 * @since
 */
public class StorageManager
{
    private final Log _logger = LogFactory.getLog(getClass());

    private final Log logger1 = LogFactory.getLog("sec");

    private final Log monitorLog = LogFactory.getLog("bill");

    private StorageDAO storageDAO = null;

    private DepotpartDAO depotpartDAO = null;

    private ProductDAO productDAO = null;

    private UserDAO userDAO = null;

    private DataSourceTransactionManager transactionManager = null;

    /**
     * default constructor
     */
    public StorageManager()
    {}

    /**
     * Description:
     * 
     * @param bean
     * @return
     * @throws MYException
     * @since <IVersion>
     */
    public synchronized boolean addStorage(final StorageBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(bean);

        JudgeTools.judgeParameterIsNull(bean.getName());

        if (storageDAO.countByName(bean.getName(), bean.getDepotpartId()) > 0)
        {
            throw new MYException(StorageBean.class, "�Ѿ�����!");
        }

        // if (bean.getAmount() < 0)
        // {
        // throw new MYException(StorageBean.class, "�в�Ʒ��������С��0!");
        // }

        // if (StringTools.isNullOrNone(bean.getProductId()))
        // {
        // if (bean.getAmount() != 0)
        // {
        // throw new MYException("û�в�Ʒ��ʱ����ָ������");
        // }
        // }

        // ���ӹ���Ա���������ݿ����������
        TransactionTemplate tran = new TransactionTemplate(transactionManager);

        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    storageDAO.addStorage(bean);

                    String ids = bean.getProductId();

                    String[] ss = ids.split("#");

                    StorageRelationBean relation = new StorageRelationBean();

                    relation.setLocationId(bean.getLocationId());

                    relation.setStorageId(bean.getId());

                    relation.setDepotpartId(bean.getDepotpartId());

                    for (String string : ss)
                    {
                        if ( !StringTools.isNullOrNone(string))
                        {
                            if (storageDAO.countProcutBydepotpart(relation.getDepotpartId(),
                                string) > 0)
                            {
                                Product product = productDAO.findProductById(string);
                                throw new RuntimeException("��Ʒ��" + product.getName()
                                                           + "���Ѿ����ڲ����У���ȷ��");
                            }

                            relation.setProductId(string);

                            storageDAO.addStorageRelation(relation);
                        }
                    }

                    return Boolean.TRUE;
                }

            });
        }
        catch (TransactionException e)
        {
            _logger.error(e, e);
            throw new MYException("�ڲ�����");
        }
        catch (DataAccessException e)
        {
            _logger.error(e, e);
            throw new MYException("�ڲ�����");
        }
        catch (RuntimeException e)
        {
            _logger.error(e, e);
            throw new MYException(e.getMessage());
        }

        _logger.info("�ɹ����Ӵ�λ:" + bean.getName());

        return true;
    }

    /**
     * Description:�������ۿ��ı䶯
     * 
     * @param temp
     * @param bean
     *            �����Ĵ�λ
     * @since <IVersion>
     */
    private void saleProductChange(StorageRelationBean bean, StorageRelationBean des_relation,
                                   int change, LogBean logBean, String seq)
    {
        DepotpartBean temp = depotpartDAO.findDepotpartById(bean.getDepotpartId());

        // ������ok��ok
        if (des_relation != null)
        {
            DepotpartBean temp1 = depotpartDAO.findDepotpartById(des_relation.getDepotpartId());

            // ok��ok
            if (temp.getType() == Constant.TYPE_DEPOTPART_OK
                && temp1.getType() == Constant.TYPE_DEPOTPART_OK)
            {
                return;
            }
        }

        // �������Ʒ�ֵķ����˱䶯����Ҫͬ�����������
        if (temp.getType() == Constant.TYPE_DEPOTPART_OK)
        {
            // NOTE synchronized handle product
            synchronized (LockConstant.CENTER_PRODUCT_AMOUNT_LOCK)
            {
                Product product = productDAO.findProductById(bean.getProductId());

                if (product == null)
                {
                    throw new RuntimeException("��Ʒ������");
                }

                // ��ǰ���ۿ��
                int current = productDAO.getTotal(bean.getProductId(), bean.getLocationId());

                int count = current + change;

                if (count < 0)
                {
                    throw new RuntimeException("���ۿ���Ʒ�������䶯���С��0��ȷ��ʵ�ʿ������ۿ��");
                }

                // ͬ�������ۿ������
                productDAO.modifyTatol(bean.getProductId(), count, bean.getLocationId());

                logger1.info("saleProductChange�����޸Ŀ��[" + product.getName() + "]����:[" + current
                             + "-->" + count + "](�������ִ��ʧ�ܴ˲�����)");

                logBean.setOutId(seq);
                logBean.setType(OutConstanst.OUT_TYPE_MOVE);
                logBean.setOutType(OutConstanst.OUT_TYPE_MOVE);
                logBean.setProductName(product.getName());
                logBean.setBeforCount(current);
                logBean.setAfterCount(count);
                logBean.setCurrent(change);
                logBean.setPreStatus(99);
                logBean.setAfterStatus(99);
                logBean.setLocationId(bean.getLocationId());
            }
        }
    }

    /**
     * updateStorage
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    public synchronized boolean updateStorage(final StorageBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(bean);

        JudgeTools.judgeParameterIsNull(bean.getName(), bean.getId());

        final StorageBean old = storageDAO.findStorageById(bean.getId());

        if (old == null)
        {
            throw new MYException(StorageBean.class, "������!");
        }

        if ( !old.getName().equals(bean.getName()))
        {
            if (storageDAO.countByName(bean.getName(), bean.getDepotpartId()) > 0)
            {
                throw new MYException(StorageBean.class, "�Ѿ�����!");
            }
        }

        // ���ӹ���Ա���������ݿ����������
        TransactionTemplate tran = new TransactionTemplate(transactionManager);

        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    storageDAO.modfiyStorage(bean);

                    updateRelationInner(bean);

                    return Boolean.TRUE;
                }

            });
        }
        catch (TransactionException e)
        {
            _logger.error(e, e);
            throw new MYException("�ڲ�����");
        }
        catch (DataAccessException e)
        {
            _logger.error(e, e);
            throw new MYException("�ڲ�����");
        }
        catch (RuntimeException e)
        {
            _logger.error(e, e);
            throw new MYException(e.getMessage());
        }

        _logger.info("�ɹ��޸Ĵ�λ:" + bean.getName());
        return true;
    }

    /**
     * �޸Ĵ�λ��Ĳ�Ʒ
     * 
     * @param depotpartId
     * @param productIds
     * @param dirStorage
     * @return
     * @throws MYException
     */
    public boolean changeDefaultStorage(final String depotpartId, final String[] productIds,
                                        final String dirStorage)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(depotpartId, dirStorage);

        for (String productId : productIds)
        {
            StorageRelationBean bean = storageDAO.findStorageRelationByDepotpartAndProcut(
                depotpartId, productId);

            if (bean == null)
            {
                throw new MYException("ȱ�ٶ�Ӧ��ϵ");
            }
        }

        StorageBean sbean = storageDAO.findStorageById(dirStorage);

        if (sbean == null)
        {
            throw new MYException("ȱ��Ŀ�Ĵ�λ");
        }

        // ���ӹ���Ա���������ݿ����������
        TransactionTemplate tran = new TransactionTemplate(transactionManager);

        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    for (String productId : productIds)
                    {
                        StorageRelationBean bean = storageDAO.findStorageRelationByDepotpartAndProcut(
                            depotpartId, productId);

                        if (bean == null)
                        {
                            throw new RuntimeException("ȱ�ٶ�Ӧ��ϵ");
                        }

                        bean.setStorageId(dirStorage);

                        storageDAO.updateStorageRelation(bean);

                        _logger.info("�޸Ĵ�λ��Ʒ��Ŀ�Ĵ�λ�ɹ�:" + productId + "|" + dirStorage);
                    }

                    return Boolean.TRUE;
                }

            });
        }
        catch (TransactionException e)
        {
            _logger.error(e, e);
            throw new MYException("ϵͳ����");
        }
        catch (DataAccessException e)
        {
            _logger.error(e, e);
            throw new MYException("ϵͳ����");
        }
        catch (RuntimeException e)
        {
            _logger.error(e, e);
            throw new MYException(e.getMessage());
        }

        return true;
    }

    /**
     * Description:
     * 
     * @param src
     * @param dest
     * @param src_Depotpart
     * @param dest_Depotpart
     * @param bean
     * @return
     * @throws MYException
     * @since <IVersion>
     */
    public synchronized String moveStorage(String src_Depotpart, String dest_Depotpart,
                                           final String productId, final int amount,
                                           final String stafferName)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(src_Depotpart, dest_Depotpart);

        JudgeTools.judgeParameterIsNull(amount, true);

        if (amount <= 0)
        {
            throw new MYException("�ƶ��������������0");
        }

        if (src_Depotpart.equals(dest_Depotpart))
        {
            throw new MYException("ͬһ����������ת�Ʋ�Ʒ");
        }

        final DepotpartBean srcBean = depotpartDAO.findDepotpartById(src_Depotpart);

        final DepotpartBean destBean = depotpartDAO.findDepotpartById(dest_Depotpart);

        Product product = productDAO.findProductById(productId);

        if (storageDAO.countProcutBydepotpart(destBean.getId(), productId) <= 0)
        {
            throw new MYException(destBean.getName() + "������û�� " + product.getName());
        }

        final StorageRelationBean relation = storageDAO.findStorageRelationByDepotpartAndProcut(
            srcBean.getId(), productId);

        final StorageRelationBean des_relation = storageDAO.findStorageRelationByDepotpartAndProcut(
            destBean.getId(), productId);

        if (relation == null)
        {
            throw new MYException("��λ�Ͳ�Ʒ�Ĺ�ϵ�����ڣ������²���!");
        }

        if (des_relation == null)
        {
            throw new MYException("��λ�Ͳ�Ʒ�Ĺ�ϵ�����ڣ������²���!");
        }

        if (relation.getAmount() - amount < 0)
        {
            throw new MYException("�����µĲ�Ʒ��" + product.getName() + "���������㣬�����²���!");
        }

        // ���ӹ���Ա���������ݿ����������
        TransactionTemplate tran = new TransactionTemplate(transactionManager);

        String rst = null;
        try
        {
            rst = (String)tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    int old = relation.getAmount();

                    relation.setAmount(relation.getAmount() - amount);

                    // ����Դ����������
                    LogBean logBean = new LogBean();

                    String rs = updateRelationInner(relation, des_relation, old, -amount,
                        stafferName, Constant.OPR_STORAGE_MOVE, null, logBean);

                    if ( !StringTools.isNullOrNone(logBean.getProductName()))
                    {
                        monitorLog.info(logBean);
                    }

                    old = des_relation.getAmount();

                    des_relation.setAmount(des_relation.getAmount() + amount);

                    logBean = new LogBean();

                    // ����Ŀ�Ĳ���������
                    rs = updateRelationInner(des_relation, relation, old, amount, stafferName,
                        Constant.OPR_STORAGE_MOVE, rs, logBean);

                    if ( !StringTools.isNullOrNone(logBean.getProductName()))
                    {
                        monitorLog.info(logBean);
                    }

                    return rs;
                }

            });
        }
        catch (TransactionException e)
        {
            _logger.error(e, e);
            throw new MYException("�ڲ�����");
        }
        catch (DataAccessException e)
        {
            _logger.error(e, e);
            throw new MYException("�ڲ�����");
        }
        catch (RuntimeException e)
        {
            _logger.error(e, e);
            throw new MYException(e.getCause().toString());
        }

        return rst;
    }

    /**
     * Description:
     * 
     * @param src
     * @param dest
     * @param src_Storage
     * @param dest_Storage
     * @param bean
     * @return
     * @throws MYException
     * @since <IVersion>
     */
    public synchronized boolean changeStorage(final String id, final int amount,
                                              final String stafferName)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        if (amount < 0)
        {
            throw new MYException("�����������0");
        }

        // ���ӹ���Ա���������ݿ����������
        TransactionTemplate tran = new TransactionTemplate(transactionManager);

        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    StorageRelationBean relation = storageDAO.findStorageRelationById(id);

                    int old = relation.getAmount();

                    relation.setAmount(amount);

                    if (relation == null)
                    {
                        return Boolean.TRUE;
                    }

                    LogBean logBean = new LogBean();

                    updateRelationInner(relation, null, old, amount - old, stafferName,
                        Constant.OPR_STORAGE_UPDATE, null, logBean);

                    if ( !StringTools.isNullOrNone(logBean.getProductName()))
                    {
                        monitorLog.info(logBean);
                    }

                    return Boolean.TRUE;
                }

            });
        }
        catch (TransactionException e)
        {
            _logger.error(e, e);
            throw new MYException("�ڲ�����");
        }
        catch (DataAccessException e)
        {
            _logger.error(e, e);
            throw new MYException("�ڲ�����");
        }
        catch (RuntimeException e)
        {
            _logger.error(e, e);
            throw new MYException(e.getMessage());
        }

        return true;
    }

    /**
     * Description: �ڲ��޸Ĵ�λ�Ĳ�Ʒ����
     * 
     * @param bean
     * @param old
     * @param change
     * @param logSeqId
     *            ��־������
     * @since <IVersion>
     */
    private String updateRelationInner(StorageRelationBean bean, StorageRelationBean des_relation,
                                       int old, int change, String stafferName, int type,
                                       String logSeqId, LogBean logBean)
    {
        // �޸Ĺ�ϵ�Ĳ�Ʒ����
        storageDAO.updateStorageRelation(bean);

        StorageLogBean log = new StorageLogBean();

        log = new StorageLogBean();

        BeanUtil.copyProperties(log, bean);

        log.setStorageId(bean.getStorageId());

        log.setType(type);

        log.setPreAmount(old);

        log.setAfterAmount(bean.getAmount());

        log.setChangeAmount(change);

        log.setLogTime(TimeTools.now());

        log.setUser(stafferName);

        if ( !StringTools.isNullOrNone(logSeqId))
        {
            log.setSerializeId(logSeqId);
        }

        String seq = storageDAO.addStorageLog(log);

        logBean.setStaffer(stafferName);
        logBean.setUser(stafferName);

        saleProductChange(bean, des_relation, log.getChangeAmount(), logBean, seq);

        return seq;
    }

    /**
     * ɾ����λ
     * 
     * @param id
     * @return
     * @throws MYException
     */
    public boolean delStorage(final String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id, true);

        if ("0".equals(id))
        {
            throw new MYException("Ĭ�ϴ�λ����ɾ��");
        }

        final StorageBean bean = storageDAO.findStorageById(id);

        if (bean == null)
        {
            throw new MYException(StorageBean.class, "������!");
        }

        List<StorageRelationBean> rs = storageDAO.queryStorageRelationByStorageId(id);

        for (StorageRelationBean storageRelationBean : rs)
        {
            if (storageRelationBean.getAmount() > 0)
            {
                throw new MYException("��λ����ʱ�в�Ʒ��������Ϊ0,����ɾ����λ!");
            }
        }

        // ���������ݿ����������
        TransactionTemplate tran = new TransactionTemplate(transactionManager);

        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    storageDAO.delStorage(id);

                    storageDAO.delStorageRelationByStorageId(id);

                    return Boolean.TRUE;
                }

            });
        }
        catch (TransactionException e)
        {
            _logger.error(e, e);
            throw new MYException("�ڲ�����");
        }
        catch (DataAccessException e)
        {
            _logger.error(e, e);
            throw new MYException("�ڲ�����");
        }

        _logger.info("�ɹ�ɾ����λ:" + bean.getName());

        return true;
    }

    public List<StorageBean> queryStorageByDepotpartId(String depotpartId)
    {
        return storageDAO.listStorage(depotpartId);
    }

    public List<StorageBeanVO> queryStorageVOByDepotpartId(String depotpartId)
    {
        return storageDAO.listStorageVO(depotpartId);
    }

    public List<StorageBean> queryStorageByCondition(ConditionParse condition)
    {
        return storageDAO.queryStorageByCondition(condition);
    }

    public StorageBean findStorageById(String id)
    {
        return storageDAO.findStorageById(id);
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
     * @return ���� transactionManager
     */
    public DataSourceTransactionManager getTransactionManager()
    {
        return transactionManager;
    }

    /**
     * @param ��transactionManager���и�ֵ
     */
    public void setTransactionManager(DataSourceTransactionManager transactionManager)
    {
        this.transactionManager = transactionManager;
    }

    /**
     * @return ���� depotpartDAO
     */
    public DepotpartDAO getDepotpartDAO()
    {
        return depotpartDAO;
    }

    /**
     * @param ��depotpartDAO���и�ֵ
     */
    public void setDepotpartDAO(DepotpartDAO depotpartDAO)
    {
        this.depotpartDAO = depotpartDAO;
    }

    /**
     * @return ���� productDAO
     */
    public ProductDAO getProductDAO()
    {
        return productDAO;
    }

    /**
     * @param ��productDAO���и�ֵ
     */
    public void setProductDAO(ProductDAO productDAO)
    {
        this.productDAO = productDAO;
    }

    /**
     * @param bean
     */
    private void updateRelationInner(final StorageBean bean)
    {
        List<StorageRelationBean> relations = storageDAO.queryStorageRelationByStorageId(bean.getId());

        List<String> news = new ArrayList<String>();

        String ids = bean.getProductId();

        String[] ss = ids.split("#");

        StorageRelationBean relation = new StorageRelationBean();

        relation.setStorageId(bean.getId());

        relation.setDepotpartId(bean.getDepotpartId());

        relation.setLocationId(bean.getLocationId());

        for (String string : ss)
        {
            if ( !StringTools.isNullOrNone(string))
            {
                boolean del = false;
                StorageRelationBean item = null;
                // ѭ����ϵ�������ĺ�ɾ�����ҳ�
                for (Iterator<StorageRelationBean> it = relations.iterator(); it.hasNext();)
                {
                    item = it.next();

                    if (item.getProductId().equals(string))
                    {
                        it.remove();
                        del = true;
                        break;
                    }
                }

                if ( !del)
                {
                    news.add(string);
                }
            }
        }

        // ѭ��ɾ�����µĹ�ϵ
        for (StorageRelationBean item : relations)
        {
            if (item.getAmount() != 0)
            {
                Product product = productDAO.findProductById(item.getProductId());

                throw new RuntimeException("��Ʒ��" + product.getName() + "���Ѿ����ڲ������������������ܲ���!");
            }

            storageDAO.delStorageRelation(item.getId());
        }

        // ���������Ĳ�Ʒ��ϵ
        for (String string2 : news)
        {
            if (storageDAO.countProcutBydepotpart(relation.getDepotpartId(), string2) > 0)
            {
                Product product = productDAO.findProductById(string2);
                throw new RuntimeException("��Ʒ��" + product.getName() + "���Ѿ����ڲ����У���ȷ��");
            }

            relation.setProductId(string2);

            storageDAO.addStorageRelation(relation);
        }
    }

    /**
     * @return the userDAO
     */
    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    /**
     * @param userDAO
     *            the userDAO to set
     */
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }
}
