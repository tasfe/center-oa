/**
 * File Name: LocationListenerProductImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.listener.impl;


import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.dao.ProductVSLocationDAO;
import com.china.center.oa.publics.listener.LocationListener;
import com.china.center.oa.publics.vs.LocationVSCityBean;


/**
 * LocationListenerProductImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-15
 * @see LocationListenerProductImpl
 * @since 1.0
 */
public class LocationListenerProductImpl implements LocationListener
{
    private ProductVSLocationDAO productVSLocationDAO = null;

    /**
     * default constructor
     */
    public LocationListenerProductImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.LocationListener#onAddLocationVSCityBefore(com.center.china.osgi.publics.User,
     *      java.lang.String, java.util.List)
     */
    public void onAddLocationVSCityBefore(User user, String locationId, List<LocationVSCityBean> list)
        throws MYException
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.LocationListener#onAddLocationVSCityEach(com.center.china.osgi.publics.User,
     *      java.lang.String, com.china.center.oa.publics.vs.LocationVSCityBean)
     */
    public void onAddLocationVSCityEach(User user, String locationId, LocationVSCityBean each)
        throws MYException
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.LocationListener#onDeleteLocation(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    public void onDeleteLocation(User user, String locationId)
        throws MYException
    {
        ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        condition.addCondition("locationId", "=", locationId);

        int count = productVSLocationDAO.countByCondition(condition.toString());

        if (count > 0)
        {
            throw new MYException("分公司被产品引用,不能删除");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.LocationListener#onDeleteLocationVSCity(com.center.china.osgi.publics.User,
     *      java.lang.String, java.util.List)
     */
    public void onDeleteLocationVSCity(User user, String locationId, List<LocationVSCityBean> deleteList)
        throws MYException
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "Product.Impl";
    }

    /**
     * @return the productVSLocationDAO
     */
    public ProductVSLocationDAO getProductVSLocationDAO()
    {
        return productVSLocationDAO;
    }

    /**
     * @param productVSLocationDAO
     *            the productVSLocationDAO to set
     */
    public void setProductVSLocationDAO(ProductVSLocationDAO productVSLocationDAO)
    {
        this.productVSLocationDAO = productVSLocationDAO;
    }
}
