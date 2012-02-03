/**
 * File Name: BudgetManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-5-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.manager;


import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.budget.bean.BudgetBean;
import com.china.center.oa.budget.bean.BudgetItemBean;
import com.china.center.oa.budget.bean.BudgetLogBean;
import com.china.center.oa.budget.vo.BudgetVO;


/**
 * BudgetManager
 * 
 * @author ZHUZHU
 * @version 2011-5-14
 * @see BudgetManager
 * @since 3.0
 */
public interface BudgetManager
{
    boolean addBean(User user, BudgetBean bean)
        throws MYException;

    /**
     * 增加预算使用日志(全局同步操作),无事务
     * 
     * @param user
     * @param refId
     * @param logBean
     * @return
     * @throws MYException
     */
    boolean addBudgetLogListWithoutTransactional(User user, String refId,
                                                 List<BudgetLogBean> logBean)
        throws MYException;

    /**
     * deleteBudgetLogListWithoutTransactional
     * 
     * @param user
     * @param refId
     * @return
     * @throws MYException
     */
    boolean deleteBudgetLogListWithoutTransactional(User user, String refId)
        throws MYException;

    /**
     * 更新预算使用的状态
     * 
     * @param user
     * @param refId
     * @param status
     * @return
     * @throws MYException
     */
    boolean updateBudgetLogStatusWithoutTransactional(User user, String refId, int status)
        throws MYException;

    /**
     * 更新费用使用类型(同时更新billids)
     * 
     * @param user
     * @param refId
     * @param userType
     * @param billIds
     * @return
     * @throws MYException
     */
    boolean updateBudgetLogUserTypeByRefIdWithoutTransactional(User user, String refId,
                                                               int userType, String billIds)
        throws MYException;

    boolean updateBean(User user, BudgetBean bean)
        throws MYException;

    boolean updateItemBean(User user, BudgetItemBean bean, String reason)
        throws MYException;

    boolean delItemBean(User user, String id)
        throws MYException;

    boolean delBean(User user, String id)
        throws MYException;

    boolean passBean(User user, String id)
        throws MYException;

    boolean rejectBean(User user, String id, String reson)
        throws MYException;

    BudgetVO findBudgetVO(String id)
        throws MYException;

    BudgetBean findBudget(String id)
        throws MYException;

    /**
     * 配置预算的开始
     */
    void initCarryStatus();

    /**
     * 合计所有的递归使用的子项(当前实际使用的情况)
     * 
     * @param budgetItemBean
     * @return
     */
    double sumHasUseInEachBudgetItem(BudgetItemBean budgetItemBean);

    /**
     * 合计子项的预占和使用(当前使用+预占的情况,多用于修改的最大校验)
     * 
     * @param budgetItemBean
     * @return
     */
    double sumPreAndUseInEachBudgetItem(BudgetItemBean budgetItemBean);

    /**
     * 统计预算实际使用金额(当前实际使用的情况)
     * 
     * @param budget
     * @return
     */
    double sumHasUseInEachBudget(BudgetBean budget);

    /**
     * 统计预算预占和使用(当前使用+预占的情况,多用于修改的最大校验)
     * 
     * @param budget
     * @return
     */
    double sumPreAndUseInEachBudget(BudgetBean budget);
}
