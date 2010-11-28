/**
 * File Name: StorageRelationListenerSailImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.listener.impl;


import com.china.center.oa.product.listener.StorageRelationListener;
import com.china.center.oa.product.vs.StorageRelationBean;
import com.china.center.oa.sail.dao.OutDAO;


/**
 * StorageRelationListenerSailImpl(库单未库管通过是预占库存的)
 * 
 * @author ZHUZHU
 * @version 2010-11-21
 * @see StorageRelationListenerSailImpl
 * @since 3.0
 */
public class StorageRelationListenerSailImpl implements StorageRelationListener
{
    private OutDAO outDAO = null;

    /**
     * default constructor
     */
    public StorageRelationListenerSailImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.listener.StorageRelationListener#onFindMaySailStorageRelation(com.china.center.oa.product.vs.StorageRelationBean)
     */
    public int onFindPreassignByStorageRelation(StorageRelationBean bean)
    {
        // 销售单库管未通过是预占库存的
        // 出库单提交后也是预占库存的
        int sumInOut = outDAO.sumNotEndProductInOutByStorageRelation(bean.getProductId(), bean
            .getDepotpartId(), bean.getPriceKey(), bean.getStafferId());

        int sumInIn = outDAO.sumNotEndProductInInByStorageRelation(bean.getProductId(), bean
            .getDepotpartId(), bean.getPriceKey(), bean.getStafferId());

        return sumInOut + sumInIn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "StorageRelationListener.SailImpl";
    }

    /**
     * @return the outDAO
     */
    public OutDAO getOutDAO()
    {
        return outDAO;
    }

    /**
     * @param outDAO
     *            the outDAO to set
     */
    public void setOutDAO(OutDAO outDAO)
    {
        this.outDAO = outDAO;
    }

}