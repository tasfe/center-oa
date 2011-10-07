/**
 * File Name: BudgetLogDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-5-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.budget.bean.BudgetLogBean;
import com.china.center.oa.budget.vo.BudgetLogVO;


/**
 * BudgetLogDAO
 * 
 * @author ZHUZHU
 * @version 2011-5-14
 * @see BudgetLogDAO
 * @since 3.0
 */
public interface BudgetLogDAO extends DAO<BudgetLogBean, BudgetLogVO>
{
    long sumBudgetLogByBudgetItemId(String budgetItemId);

    long sumBudgetLogByLevel(String level, String levelId);

    /**
     * 统计金额
     * 
     * @param condition
     * @return
     */
    long sumVOBudgetLogByCondition(ConditionParse condition);

    int updateUserTypeByRefId(String refId, int useType, String billIds);

    int updateStatuseByRefId(String refId, int status);
}
