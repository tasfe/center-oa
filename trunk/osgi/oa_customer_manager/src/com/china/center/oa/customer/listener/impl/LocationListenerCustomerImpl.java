/**
 * File Name: LocationListenerCustomerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.listener.impl;


import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.customer.dao.AssignApplyDAO;
import com.china.center.oa.customer.dao.CustomerDAO;
import com.china.center.oa.customer.dao.StafferVSCustomerDAO;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.listener.LocationListener;
import com.china.center.oa.publics.vs.LocationVSCityBean;


/**
 * LocationListenerCustomerImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-8
 * @see LocationListenerCustomerImpl
 * @since 1.0
 */
public class LocationListenerCustomerImpl implements LocationListener
{
    private CustomerDAO customerDAO = null;

    private StafferVSCustomerDAO stafferVSCustomerDAO = null;

    private AssignApplyDAO assignApplyDAO = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.LocationListener#onAddLocationVSCityBefore(com.center.china.osgi.publics.User,
     *      java.lang.String, java.util.List)
     */
    public void onAddLocationVSCityBefore(User user, String locationId, List<LocationVSCityBean> list)
        throws MYException
    {
        customerDAO.updateCustomerLocation(locationId, PublicConstant.CENTER_LOCATION);
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
        customerDAO.updateCustomerLocationByCity(each.getCityId(), locationId);
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
        // 更新区域下的用户到中心区域
        customerDAO.updateCustomerLocation(locationId, PublicConstant.CENTER_LOCATION);
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
        // NOTE ZHUZHU 区域迁移核心的逻辑处理
        for (LocationVSCityBean each : deleteList)
        {
            // 更新拓展客户的申请状态
            customerDAO.updateCityCustomerToInit(each.getCityId());

            // 更新客户的新旧属性为新客户,因为关系剥离后客户的新旧属性肯定是新的
            stafferVSCustomerDAO.updateNewByCityId(each.getCityId());

            // 删除存在的客户关系
            stafferVSCustomerDAO.delVSByCityId(each.getCityId());

            // 更新申请中的拓展客户的申请状态
            customerDAO.updateApplyCityCustomerToInit(each.getCityId());

            // 删除申请
            assignApplyDAO.delAssignByCityId(each.getCityId());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "LocationListener.CustomerImpl";
    }

    /**
     * @return the customerDAO
     */
    public CustomerDAO getCustomerDAO()
    {
        return customerDAO;
    }

    /**
     * @param customerDAO
     *            the customerDAO to set
     */
    public void setCustomerDAO(CustomerDAO customerDAO)
    {
        this.customerDAO = customerDAO;
    }

    /**
     * @return the stafferVSCustomerDAO
     */
    public StafferVSCustomerDAO getStafferVSCustomerDAO()
    {
        return stafferVSCustomerDAO;
    }

    /**
     * @param stafferVSCustomerDAO
     *            the stafferVSCustomerDAO to set
     */
    public void setStafferVSCustomerDAO(StafferVSCustomerDAO stafferVSCustomerDAO)
    {
        this.stafferVSCustomerDAO = stafferVSCustomerDAO;
    }

    /**
     * @return the assignApplyDAO
     */
    public AssignApplyDAO getAssignApplyDAO()
    {
        return assignApplyDAO;
    }

    /**
     * @param assignApplyDAO
     *            the assignApplyDAO to set
     */
    public void setAssignApplyDAO(AssignApplyDAO assignApplyDAO)
    {
        this.assignApplyDAO = assignApplyDAO;
    }

}
