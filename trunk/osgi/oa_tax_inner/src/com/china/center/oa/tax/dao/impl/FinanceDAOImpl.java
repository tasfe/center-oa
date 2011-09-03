/**
 * File Name: FinanceDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.dao.impl;


import java.util.ArrayList;
import java.util.List;

import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.oa.tax.vo.FinanceVO;


/**
 * FinanceDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-2-6
 * @see FinanceDAOImpl
 * @since 1.0
 */
public class FinanceDAOImpl extends BaseDAO<FinanceBean, FinanceVO> implements FinanceDAO
{
    public boolean updateCheck(String id, String reason)
    {
        String sql = BeanTools.getUpdateHead(claz) + "set status = ?, checks = ? where id = ?";

        this.jdbcOperation.update(sql, TaxConstanst.FINANCE_STATUS_CHECK, reason, id);

        return true;
    }

    public int updateLockToEnd(String beginTime, String endTime)
    {
        String sql = BeanTools.getUpdateHead(claz)
                     + "set locks = ? where financeDate >= ? and financeDate <= ?";

        return this.jdbcOperation.update(sql, TaxConstanst.FINANCE_LOCK_YES, beginTime, endTime);
    }

    public List<FinanceBean> queryRefFinanceItemByBillId(String billId)
    {
        ConditionParse condtion = new ConditionParse();
        condtion.addWhereStr();
        condtion.addCondition("refId", "=", billId);

        List<FinanceBean> result1 = this.queryEntityBeansByCondition(condtion);

        condtion.clear();
        condtion.addWhereStr();
        condtion.addCondition("refBill", "=", billId);

        List<FinanceBean> result2 = this.queryEntityBeansByCondition(condtion);

        return sumList(result1, result2);
    }

    public List<FinanceBean> queryRefFinanceItemByOutId(String outId)
    {
        ConditionParse condtion = new ConditionParse();
        condtion.addWhereStr();
        condtion.addCondition("refId", "=", outId);

        List<FinanceBean> result1 = this.queryEntityBeansByCondition(condtion);

        condtion.clear();
        condtion.addWhereStr();
        condtion.addCondition("refOut", "=", outId);

        List<FinanceBean> result2 = this.queryEntityBeansByCondition(condtion);

        return sumList(result1, result2);
    }

    public List<FinanceBean> queryRefFinanceItemByRefId(String refId)
    {
        ConditionParse condtion = new ConditionParse();
        condtion.addWhereStr();
        condtion.addCondition("refId", "=", refId);

        return this.queryEntityBeansByCondition(condtion);
    }

    public List<FinanceBean> queryRefFinanceItemByStockId(String stockId)
    {
        ConditionParse condtion = new ConditionParse();
        condtion.addWhereStr();
        condtion.addCondition("refId", "=", stockId);

        List<FinanceBean> result1 = this.queryEntityBeansByCondition(condtion);

        condtion.clear();
        condtion.addWhereStr();
        condtion.addCondition("refStock", "=", stockId);

        List<FinanceBean> result2 = this.queryEntityBeansByCondition(condtion);

        return sumList(result1, result2);
    }

    private List<FinanceBean> sumList(List<FinanceBean> result1, List<FinanceBean> result2)
    {
        List<FinanceBean> result = new ArrayList();

        result.addAll(result1);

        for (FinanceBean each : result2)
        {
            if ( !result.contains(each))
            {
                result.add(each);
            }
        }

        return result;
    }
}
