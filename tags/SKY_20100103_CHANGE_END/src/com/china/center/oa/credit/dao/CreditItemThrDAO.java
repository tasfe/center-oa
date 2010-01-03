/**
 * File Name: CreditItemThrDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: ZHUACHEN<br>
 * CreateTime: 2009-10-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.dao;


import java.util.List;

import net.sourceforge.sannotations.annotation.Bean;

import com.china.center.jdbc.inter.impl.BaseDAO2;
import com.china.center.oa.constant.CreditConstant;
import com.china.center.oa.credit.bean.CreditItemThrBean;
import com.china.center.oa.credit.vo.CreditItemThrVO;
import com.china.center.tools.ListTools;


/**
 * CreditItemThrDAO
 * 
 * @author ZHUZHU
 * @version 2009-10-27
 * @see CreditItemThrDAO
 * @since 1.0
 */
@Bean(name = "creditItemThrDAO")
public class CreditItemThrDAO extends BaseDAO2<CreditItemThrBean, CreditItemThrVO>
{
    /**
     * countByPidAndIndexPos
     * 
     * @param pid
     * @param indexPos
     * @return
     */
    public int countByPidAndIndexPos(String pid, int indexPos)
    {
        return this.countByCondition("where pid = ? and indexPos = ?", pid, indexPos);
    }

    /**
     * findMaxDelayItem
     * 
     * @return
     */
    public CreditItemThrBean findMaxDelayItem()
    {
        List<CreditItemThrBean> list = this.queryEntityBeansByCondition(
            "where pid = ? order by indexPos desc", CreditConstant.OUT_DELAY_ITEM);

        if (ListTools.isEmptyOrNull(list))
        {
            return null;
        }

        return list.get(0);
    }

    /**
     * findDelayItemByDays
     * 
     * @param days
     * @return
     */
    public CreditItemThrBean findDelayItemByDays(int days)
    {
        List<CreditItemThrBean> list = this.queryEntityBeansByCondition(
            "where pid = ? and indexPos >= ? order by indexPos asc",
            CreditConstant.OUT_DELAY_ITEM, days);

        if (ListTools.isEmptyOrNull(list))
        {
            return findMaxDelayItem();
        }

        return list.get(0);
    }

    /**
     * findMaxDelayItem
     * 
     * @return
     */
    public CreditItemThrBean findMaxBusiness()
    {
        List<CreditItemThrBean> list = this.queryEntityBeansByCondition(
            "where pid = ? order by indexPos desc", CreditConstant.OUT_MAX_BUSINESS);

        if (ListTools.isEmptyOrNull(list))
        {
            return null;
        }

        return list.get(0);
    }

    /**
     * findSingleMaxBusinessByValue
     * 
     * @param sigleMax
     * @return
     */
    public CreditItemThrBean findSingleMaxBusinessByValue(double sigleMax)
    {
        // ��λ����Ԫ
        int level = (int) (sigleMax / 10000.0d);

        List<CreditItemThrBean> list = this.queryEntityBeansByCondition(
            "where pid = ? and indexPos >= ? order by indexPos asc",
            CreditConstant.OUT_MAX_BUSINESS, level);

        if (ListTools.isEmptyOrNull(list))
        {
            return findMaxBusiness();
        }

        return list.get(0);
    }

    /**
     * findTotalBusiness
     * 
     * @return
     */
    public CreditItemThrBean findTotalBusiness()
    {
        List<CreditItemThrBean> list = this.queryEntityBeansByCondition(
            "where pid = ? order by indexPos desc", CreditConstant.OUT_TOTAL_BUSINESS);

        if (ListTools.isEmptyOrNull(list))
        {
            return null;
        }

        return list.get(0);
    }

    public CreditItemThrBean findTotalBusinessByValue(double sumBusiness)
    {
        // ��λ����Ԫ
        int level = (int) (sumBusiness / 10000.0d);

        List<CreditItemThrBean> list = this.queryEntityBeansByCondition(
            "where pid = ? and indexPos >= ? order by indexPos asc",
            CreditConstant.OUT_TOTAL_BUSINESS, level);

        if (ListTools.isEmptyOrNull(list))
        {
            return findTotalBusiness();
        }

        return list.get(0);
    }
}