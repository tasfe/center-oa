/**
 * File Name: FinanceItemDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.dao.impl;


import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.dao.FinanceItemDAO;
import com.china.center.oa.tax.vo.FinanceItemVO;


/**
 * FinanceItemDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-2-6
 * @see FinanceItemDAOImpl
 * @since 1.0
 */
public class FinanceItemDAOImpl extends BaseDAO<FinanceItemBean, FinanceItemVO> implements FinanceItemDAO
{
    public long sumInByCondition(ConditionParse condition)
    {
        String sql = BeanTools.getSumHead(claz, "inmoney") + condition.toString();

        return this.jdbcOperation.queryForLong(sql);
    }

    public long sumOutByCondition(ConditionParse condition)
    {
        String sql = BeanTools.getSumHead(claz, "outmoney") + condition.toString();

        return this.jdbcOperation.queryForLong(sql);
    }
}
