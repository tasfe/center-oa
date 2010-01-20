/**
 * File Name: BudgetApplyManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-6-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.manager;


import java.util.ArrayList;
import java.util.List;

import net.sourceforge.sannotations.annotation.Bean;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.common.MYException;
import com.china.center.oa.budget.bean.BudgetApplyBean;
import com.china.center.oa.budget.bean.BudgetBean;
import com.china.center.oa.budget.bean.BudgetItemBean;
import com.china.center.oa.budget.bean.FeeItemBean;
import com.china.center.oa.budget.dao.BudgetApplyDAO;
import com.china.center.oa.budget.dao.BudgetDAO;
import com.china.center.oa.budget.dao.BudgetItemDAO;
import com.china.center.oa.budget.dao.FeeItemDAO;
import com.china.center.oa.budget.helper.BudgetHelper;
import com.china.center.oa.budget.vo.BudgetItemVO;
import com.china.center.oa.constant.BudgetConstant;
import com.china.center.oa.constant.ModuleConstant;
import com.china.center.oa.constant.OperationConstant;
import com.china.center.oa.publics.User;
import com.china.center.oa.publics.bean.LogBean;
import com.china.center.oa.publics.dao.CommonDAO2;
import com.china.center.oa.publics.dao.LogDAO;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.TimeTools;


/**
 * BudgetApplyManager
 * 
 * @author zhuzhu
 * @version 2009-6-14
 * @see BudgetApplyManager
 * @since 1.0
 */
@Exceptional
@Bean(name = "budgetApplyManager")
public class BudgetApplyManager
{
    private BudgetDAO budgetDAO = null;

    private BudgetItemDAO budgetItemDAO = null;

    private CommonDAO2 commonDAO2 = null;

    private FeeItemDAO feeItemDAO = null;

    private BudgetApplyDAO budgetApplyDAO = null;

    private LogDAO logDAO = null;

    /**
     * default constructor
     */
    public BudgetApplyManager()
    {}

    /**
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public synchronized boolean addBean(User user, BudgetApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkAddBean(bean);

        bean.setId(commonDAO2.getSquenceString20());

        budgetApplyDAO.saveEntityBean(bean);

        saveItemList(bean);

        // save log
        log(user, bean.getId(), OperationConstant.OPERATION_SUBMIT, bean.getDescription());

        log(user, bean.getBudgetId(), OperationConstant.OPERATION_UPDATE, bean.getDescription());

        return true;
    }

    /**
     * pass
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean passBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        int nextStatus = checkPass(user, id);

        BudgetApplyBean apply = budgetApplyDAO.find(id);

        if (nextStatus != BudgetConstant.BUDGET_APPLY_STATUS_END)
        {
            budgetApplyDAO.updateStatus(id, nextStatus);

            log(user, apply.getId(), OperationConstant.OPERATION_PASS, "ͨ��Ԥ����");
        }
        else
        {
            List<BudgetItemBean> applyItemList = budgetItemDAO.queryEntityBeansByFK(id);

            List<BudgetItemBean> currentItemList = budgetItemDAO.queryEntityBeansByFK(apply.getBudgetId());

            // NOTE if BUDGET_APPLY_STATUS_END,copy budget and delete apply
            List<BudgetItemBean> newList = compareItemListAndModifyCurrentItemListReturnNewItemList(
                apply.getBudgetId(), applyItemList, currentItemList);

            // copy apply to current
            budgetItemDAO.updateAllEntityBeans(currentItemList);

            // add new item to newList
            budgetItemDAO.saveAllEntityBeans(newList);

            deleteApply(id);
        }

        log(user, apply.getBudgetId(), OperationConstant.OPERATION_PASS, "ͨ��Ԥ����");

        return true;
    }

    /**
     * rejectBean
     * 
     * @param user
     * @param id
     * @param reson
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean rejectBean(User user, String id, String reson)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        BudgetApplyBean apply = budgetApplyDAO.find(id);

        if (apply == null)
        {
            throw new MYException("���벻����");
        }

        deleteApply(id);

        log(user, apply.getBudgetId(), OperationConstant.OPERATION_REJECT, reson);

        return true;
    }

    /**
     * deleteApply
     * 
     * @param id
     */
    private void deleteApply(String id)
    {
        budgetApplyDAO.deleteEntityBean(id);

        budgetItemDAO.deleteEntityBeansByFK(id);

        logDAO.deleteEntityBean(id);
    }

    /**
     * �ȽϷ���������item
     * 
     * @param applyItemList
     * @param currentItemList
     * @throws MYException
     */
    private List<BudgetItemBean> compareItemListAndModifyCurrentItemListReturnNewItemList(
                                                                                          String budgetId,
                                                                                          List<BudgetItemBean> applyItemList,
                                                                                          List<BudgetItemBean> currentItemList)
        throws MYException
    {
        List<BudgetItemBean> newList = new ArrayList();

        // copy apply to current
        for (BudgetItemBean apply : applyItemList)
        {
            boolean isContain = false;

            for (BudgetItemBean current : currentItemList)
            {
                if (current.getFeeItemId().equals(apply.getFeeItemId()))
                {
                    current.setBudget(apply.getBudget());

                    current.setUseMonery(current.getBudget());

                    isContain = true;

                    break;
                }
            }

            if ( !isContain)
            {
                apply.setBudgetId(budgetId);

                // set appltItem to save
                apply.setUseMonery(apply.getBudget());

                apply.setCarryStatus(BudgetConstant.BUDGET_CARRY_DOING);

                newList.add(apply);
            }
        }

        return newList;
    }

    /**
     * checkPass
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    private int checkPass(User user, String id)
        throws MYException
    {
        BudgetApplyBean apply = budgetApplyDAO.find(id);

        if (apply == null)
        {
            throw new MYException("���벻����");
        }

        apply.setItems(budgetItemDAO.queryEntityBeansByFK(id));

        BudgetBean budget = budgetDAO.find(apply.getBudgetId());

        if (budget == null)
        {
            throw new MYException("Ԥ�㲻����");
        }

        if (budget.getStatus() == BudgetConstant.BUDGET_STATUS_END)
        {
            throw new MYException("Ԥ���Ѿ�����,���ܽ��б��");
        }

        if (apply.getStatus() < BudgetConstant.BUDGET_APPLY_STATUS_WAIT_CFO)
        {
            throw new MYException("��������");
        }

        checkLegality(apply, budget);

        // get next status
        return BudgetHelper.getNextApplyStatus(budget, apply);
    }

    /**
     * @param budgetId
     * @return
     * @throws MYException
     */
    public boolean whetherApply(String budgetId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(budgetId);

        int count = budgetApplyDAO.countByUnique(budgetId);

        if (count > 0)
        {
            throw new MYException("�Ѿ����ڱ������׷��");
        }

        return true;
    }

    /**
     * saveItemList
     * 
     * @param bean
     */
    private void saveItemList(BudgetApplyBean bean)
    {
        List<BudgetItemBean> itemList = bean.getItems();

        for (BudgetItemBean budgetItemBean : itemList)
        {
            budgetItemBean.setBudgetId(bean.getId());
        }

        budgetItemDAO.saveAllEntityBeans(itemList);
    }

    /**
     * @param bean
     * @throws MYException
     */
    private void checkAddBean(BudgetApplyBean bean)
        throws MYException
    {
        if (budgetApplyDAO.countByUnique(bean.getBudgetId()) > 0)
        {
            throw new MYException("�Ѿ�����Ԥ��������׷��");
        }

        BudgetBean budget = budgetDAO.find(bean.getBudgetId());

        if (budget == null)
        {
            throw new MYException("Ԥ�㲻����");
        }

        if (ListTools.isEmptyOrNull(bean.getItems()))
        {
            throw new MYException("û��Ԥ����");
        }

        // NOTE core check
        checkLegality(bean, budget);
    }

    /**
     * @param bean
     * @param budget
     * @throws MYException
     */
    private void checkLegality(BudgetApplyBean bean, BudgetBean budget)
        throws MYException
    {
        List<BudgetItemVO> currentItemList = budgetItemDAO.queryEntityVOsByFK(bean.getBudgetId());

        // can not drop exist item
        checkDrop(currentItemList, bean.getItems());

        // check modify
        if (bean.getType() == BudgetConstant.BUDGET_APPLY_TYPE_MODIFY)
        {
            handleCheckModify(bean, budget);
        }

        // check add
        if (bean.getType() == BudgetConstant.BUDGET_APPLY_TYPE_ADD)
        {
            handleCheckAdd(bean, budget);
        }

        // if not BUDGET_TYPE_COMPANY,check contain
        if (budget.getType() != BudgetConstant.BUDGET_TYPE_COMPANY)
        {
            // check budget
            compareSubItem(budgetItemDAO.queryEntityVOsByFK(budget.getParentId()),
                bean.getItems(), budget.getId());
        }
    }

    /**
     * @param bean
     * @throws MYException
     */
    private void handleCheckModify(BudgetApplyBean bean, BudgetBean budget)
        throws MYException
    {
        // if modify,the total only equal or less than current budget, and the modify budge must bigger than
        // realMonery
        List<BudgetItemBean> items = bean.getItems();

        double total = 0.0d;

        for (BudgetItemBean budgetItemBean : items)
        {
            BudgetItemVO currentItem = budgetItemDAO.findVOByBudgetIdAndFeeItemId(
                bean.getBudgetId(), budgetItemBean.getFeeItemId());

            total += budgetItemBean.getBudget();

            // the modify budge must bigger than realMonery
            if (currentItem != null)
            {
                if (budgetItemBean.getBudget() < currentItem.getRealMonery())
                {
                    throw new MYException("Ԥ����[%s]�Ѿ�ʹ����%s,���Ը��ĺ��Ԥ�㲻��С��%s,����ǰ�޸ĺ����%s",
                        currentItem.getFeeItemName(),
                        MathTools.formatNum(currentItem.getRealMonery()),
                        MathTools.formatNum(currentItem.getRealMonery()),
                        MathTools.formatNum(budgetItemBean.getBudget()));
                }
            }
            else
            {
                // add item,need check item is exist in parent
                if (budget.getType() != BudgetConstant.BUDGET_TYPE_COMPANY)
                {
                    BudgetItemBean parentItem = budgetItemDAO.findByBudgetIdAndFeeItemId(
                        budget.getParentId(), budgetItemBean.getFeeItemId());

                    if (parentItem == null)
                    {
                        throw new MYException("Ԥ����[%s]��������,���Ǹ�Ԥ��û�д�Ԥ����",
                            currentItem.getFeeItemName());
                    }
                }
            }
        }

        double currentBudget = budgetItemDAO.sumBudgetTotal(bean.getBudgetId());

        if (currentBudget < total)
        {
            throw new MYException("��ǰԤ���ܶ���%s,�������������%s�����ܴ��ڵ�ǰ�ܶ������±��",
                MathTools.formatNum(currentBudget), MathTools.formatNum(total));
        }
    }

    /**
     * handleCheckAdd
     * 
     * @param bean
     * @throws MYException
     */
    private void handleCheckAdd(BudgetApplyBean bean, BudgetBean budget)
        throws MYException
    {
        // if modify,the total only equal or less than current budget, and the modify budge must bigger than
        // realMonery
        List<BudgetItemBean> items = bean.getItems();

        double total = 0.0d;

        for (BudgetItemBean budgetItemBean : items)
        {
            BudgetItemVO currentItem = budgetItemDAO.findVOByBudgetIdAndFeeItemId(
                bean.getBudgetId(), budgetItemBean.getFeeItemId());

            total += budgetItemBean.getBudget();

            if (currentItem != null)
            {
                if (budgetItemBean.getBudget() < currentItem.getBudget())
                {
                    throw new MYException("Ԥ����[%s]Ԥ����%s,���ĺ��Ԥ�㲻��С��%s,����ǰ�޸ĺ����%s",
                        currentItem.getFeeItemName(),
                        MathTools.formatNum(currentItem.getBudget()),
                        MathTools.formatNum(currentItem.getRealMonery()),
                        MathTools.formatNum(budgetItemBean.getBudget()));
                }
            }
            else
            {
                // add item,need check item is exist in parent
                if (budget.getType() != BudgetConstant.BUDGET_TYPE_COMPANY)
                {
                    BudgetItemBean parentItem = budgetItemDAO.findByBudgetIdAndFeeItemId(
                        budget.getParentId(), budgetItemBean.getFeeItemId());

                    if (parentItem == null)
                    {
                        throw new MYException("Ԥ����[%s]��������,���Ǹ�Ԥ��û�д�Ԥ����",
                            currentItem.getFeeItemName());
                    }
                }
            }
        }

        double currentBudget = budgetItemDAO.sumBudgetTotal(bean.getBudgetId());

        if (total < currentBudget)
        {
            throw new MYException("��ǰԤ���ܶ���%s,�������������%s������С�ڵ�ǰ�ܶ�������׷��",
                MathTools.formatNum(currentBudget), MathTools.formatNum(total));
        }
    }

    /**
     * checkDrop
     * 
     * @param currentList
     * @param modifyLsit
     * @throws MYException
     */
    private void checkDrop(List<BudgetItemVO> currentList, List<BudgetItemBean> modifyLsit)
        throws MYException
    {
        for (BudgetItemVO currentItem : currentList)
        {
            boolean has = false;

            for (BudgetItemBean modifyItem : modifyLsit)
            {
                if (modifyItem.getFeeItemId().equals(currentItem.getFeeItemId()))
                {
                    has = true;
                    break;
                }
            }

            if ( !has)
            {
                throw new MYException("��ʧԤ����[%s]", currentItem.getFeeItemName());
            }
        }
    }

    /**
     * compareSubItem
     * 
     * @param parentSubItem
     * @param subItem
     * @throws MYException
     */
    private void compareSubItem(List<BudgetItemVO> parentSubItem, List<BudgetItemBean> subItem,
                                String budgetId)
        throws MYException
    {
        for (BudgetItemBean item : subItem)
        {
            boolean isOK = false;

            for (BudgetItemVO pitem : parentSubItem)
            {
                if (pitem.getFeeItemId().equals(item.getFeeItemId()))
                {
                    // total money,less than current
                    if (compareMoney(pitem, item.getBudget(), budgetId))
                    {
                        isOK = true;
                    }
                    else
                    {
                        throw new MYException("[%s]��Ԥ��ͳ�����Ԥ��", pitem.getFeeItemName());
                    }
                }
            }

            if ( !isOK)
            {
                FeeItemBean feeItem = feeItemDAO.find(item.getFeeItemId());

                if (feeItem == null)
                {
                    throw new MYException("Ԥ�������");
                }

                throw new MYException("��Ԥ��ȱ���ύ��Ԥ����[%s]", feeItem.getName());
            }
        }
    }

    /**
     * compareMoney
     * 
     * @param pitem
     * @param currentMoney
     * @return
     */
    private boolean compareMoney(BudgetItemBean pitem, double currentMoney, String budgetId)
    {
        // this list cotain the modfiy budget(so we must filter it)
        List<BudgetBean> allSubBudget = budgetDAO.querySubmitBudgetByParentId(pitem.getBudgetId());

        double total = currentMoney;

        for (BudgetBean budgetBean : allSubBudget)
        {
            // filter myself
            if (budgetBean.getId().equals(budgetId))
            {
                continue;
            }

            BudgetItemBean subFeeItem = budgetItemDAO.findByBudgetIdAndFeeItemId(
                budgetBean.getId(), pitem.getFeeItemId());

            if (subFeeItem != null)
            {
                total += subFeeItem.getUseMonery();
            }
        }

        return pitem.getBudget() >= total;
    }

    /**
     * log in db
     * 
     * @param user
     * @param id
     */
    private void log(User user, String id, String operation, String reson)
    {
        // ��¼������־
        LogBean log = new LogBean();

        log.setFkId(id);

        log.setLocationId(user.getLocationId());
        log.setStafferId(user.getStafferId());
        log.setLogTime(TimeTools.now());
        log.setModule(ModuleConstant.MODULE_BUDGET);
        log.setOperation(operation);
        log.setLog(reson);

        logDAO.saveEntityBean(log);
    }

    /**
     * @return the budgetDAO
     */
    public BudgetDAO getBudgetDAO()
    {
        return budgetDAO;
    }

    /**
     * @param budgetDAO
     *            the budgetDAO to set
     */
    public void setBudgetDAO(BudgetDAO budgetDAO)
    {
        this.budgetDAO = budgetDAO;
    }

    /**
     * @return the budgetItemDAO
     */
    public BudgetItemDAO getBudgetItemDAO()
    {
        return budgetItemDAO;
    }

    /**
     * @param budgetItemDAO
     *            the budgetItemDAO to set
     */
    public void setBudgetItemDAO(BudgetItemDAO budgetItemDAO)
    {
        this.budgetItemDAO = budgetItemDAO;
    }

    /**
     * @return the commonDAO2
     */
    public CommonDAO2 getCommonDAO2()
    {
        return commonDAO2;
    }

    /**
     * @param commonDAO2
     *            the commonDAO2 to set
     */
    public void setCommonDAO2(CommonDAO2 commonDAO2)
    {
        this.commonDAO2 = commonDAO2;
    }

    /**
     * @return the budgetApplyDAO
     */
    public BudgetApplyDAO getBudgetApplyDAO()
    {
        return budgetApplyDAO;
    }

    /**
     * @param budgetApplyDAO
     *            the budgetApplyDAO to set
     */
    public void setBudgetApplyDAO(BudgetApplyDAO budgetApplyDAO)
    {
        this.budgetApplyDAO = budgetApplyDAO;
    }

    /**
     * @return the logDAO
     */
    public LogDAO getLogDAO()
    {
        return logDAO;
    }

    /**
     * @param logDAO
     *            the logDAO to set
     */
    public void setLogDAO(LogDAO logDAO)
    {
        this.logDAO = logDAO;
    }

    /**
     * @return the feeItemDAO
     */
    public FeeItemDAO getFeeItemDAO()
    {
        return feeItemDAO;
    }

    /**
     * @param feeItemDAO
     *            the feeItemDAO to set
     */
    public void setFeeItemDAO(FeeItemDAO feeItemDAO)
    {
        this.feeItemDAO = feeItemDAO;
    }
}
