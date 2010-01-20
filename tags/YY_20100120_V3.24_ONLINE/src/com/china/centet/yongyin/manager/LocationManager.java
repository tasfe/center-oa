/*
 * File Name: LocationManager.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-12-15
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.manager;


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
import com.china.center.tools.JudgeTools;
import com.china.centet.yongyin.bean.LocationBean;
import com.china.centet.yongyin.bean.Product;
import com.china.centet.yongyin.bean.ProductAmount;
import com.china.centet.yongyin.bean.User;
import com.china.centet.yongyin.dao.CommonDAO;
import com.china.centet.yongyin.dao.LocationDAO;
import com.china.centet.yongyin.dao.ProductDAO;
import com.china.centet.yongyin.dao.UserDAO;


/**
 * <����>
 * 
 * @author zhuzhu
 * @version 2007-12-15
 * @see
 * @since
 */
public class LocationManager
{
    private final Log _logger = LogFactory.getLog(getClass());

    private LocationDAO locationDAO = null;

    private CommonDAO commonDAO = null;

    private ProductDAO productDAO = null;

    private UserDAO userDAO = null;

    private DataSourceTransactionManager transactionManager = null;

    /**
     * default constructor
     */
    public LocationManager()
    {}

    /**
     * ��������
     * 
     * @param locationBean
     * @return
     * @throws MYException
     */
    public String addLocation(final LocationBean locationBean, final User user)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(locationBean);

        // ���ӹ���Ա���������ݿ����������
        TransactionTemplate tran = new TransactionTemplate(transactionManager);

        final String locationId = commonDAO.getSquenceString();

        user.setLocationID(locationId);

        try
        {
            JudgeTools.judgeParameterIsNull(user.getName(), locationBean.getLocationName());

            if (locationDAO.countByName(locationBean.getLocationName()) > 0)
            {
                throw new MYException("���������Ѿ�����!");
            }

            if (locationDAO.countByCode(locationBean.getLocationCode()) > 0)
            {
                throw new MYException("�����־�Ѿ�����!");
            }

            if (userDAO.findUserByLoginName(user.getName()) != null)
            {
                throw new MYException("����Ա�Ѿ�����!");
            }

            // ����ID

            locationBean.setId(locationId);

            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    // ��������
                    locationDAO.addLocation(locationBean);

                    // ��������Ĺ���Ա
                    userDAO.addUser2(user);

                    List<Product> proList = productDAO.queryProductByCondtion(
                        new ConditionParse(), false);

                    ProductAmount amount = new ProductAmount();

                    amount.setLocationId(locationId);

                    // ����
                    for (Product object : proList)
                    {
                        amount.setProductId(object.getId());

                        amount.setProductName(object.getName());

                        productDAO.addProductAmount2(amount);
                    }

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("�����������", e);
            throw new MYException("���ݿ��ڲ�����");
        }
        catch (DataAccessException e)
        {
            _logger.error("�����������", e);
            throw new MYException(e.getCause().toString());
        }

        return locationId;
    }

    public List<LocationBean> listLocation()
    {
        return locationDAO.listLocation();
    }

    public LocationBean findLocationById(String id)
    {
        return locationDAO.findLocation(id);
    }

    /**
     * @return the locationDAO
     */
    public LocationDAO getLocationDAO()
    {
        return locationDAO;
    }

    /**
     * @return the transactionManager
     */
    public DataSourceTransactionManager getTransactionManager()
    {
        return transactionManager;
    }

    /**
     * @param locationDAO
     *            the locationDAO to set
     */
    public void setLocationDAO(LocationDAO locationDAO)
    {
        this.locationDAO = locationDAO;
    }

    /**
     * @param transactionManager
     *            the transactionManager to set
     */
    public void setTransactionManager(DataSourceTransactionManager transactionManager)
    {
        this.transactionManager = transactionManager;
    }

    /**
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @return the productDAO
     */
    public ProductDAO getProductDAO()
    {
        return productDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
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
