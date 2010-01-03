/**
 * File Name: NewCustomerExamineImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-1-11<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.examine.manager;


import java.util.List;

import net.sourceforge.sannotations.annotation.Bean;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.common.MYException;
import com.china.center.oa.constant.ExamineConstant;
import com.china.center.oa.examine.bean.ExamineBean;
import com.china.center.oa.examine.bean.ProfitExamineBean;
import com.china.center.oa.examine.dao.CustomerExamineLogDAO;
import com.china.center.oa.examine.dao.ExamineDAO;
import com.china.center.oa.examine.dao.ProfitDAO;
import com.china.center.oa.examine.dao.ProfitExamineDAO;
import com.china.center.oa.plan.bean.PlanBean;
import com.china.center.oa.plan.manager.CarryPlan;
import com.china.center.oa.plan.manager.PlanManager;
import com.china.center.tools.StringTools;


/**
 * ����Ŀ���ʵ��
 * 
 * @author zhuzhu
 * @version 2009-1-11
 * @see ProfitExamineImpl
 * @since 1.0
 */
@Exceptional
@Bean(name = "profitExamineImpl", initMethod = "init")
public class ProfitExamineImpl implements CarryPlan
{
    private ExamineDAO examineDAO = null;

    private ProfitDAO profitDAO = null;

    private ProfitExamineDAO profitExamineDAO = null;

    private CustomerExamineLogDAO customerExamineLogDAO = null;

    /**
     * default constructor
     */
    public ProfitExamineImpl()
    {}

    /**
     * ��ʼ��ע��
     */
    public void init()
    {
        PlanManager.addCarryPlan("profitExamineImpl");
    }

    /**
     * ʵʩ�ƻ�
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean carryPlan(PlanBean plan, boolean end)
    {
        String itemId = plan.getFkId();

        if (StringTools.isNullOrNone(itemId))
        {
            return true;
        }

        ProfitExamineBean item = profitExamineDAO.find(itemId);

        if (item == null)
        {
            return true;
        }

        // ״̬����Ѿ�����,��ִ��
        if (item.getStatus() == ExamineConstant.EXAMINE_ITEM_STATUS_END)
        {
            return true;
        }

        ExamineBean examine = examineDAO.find(item.getParentId());

        if (examine == null)
        {
            return true;
        }

        double sum = 0.0d;

        // ��ÿ��˵�ְԱ��ʱ�䣬������������ѯ��ô���(���˵�)
        if (examine.getAttType() == ExamineConstant.EXAMINE_ATTTYPE_PERSONAL)
        {
            sum = profitDAO.countProfitByTime(examine.getStafferId(), item.getBeginTime(),
                item.getEndTime());
        }
        // ��Ŀ����/�ֹ�˾�ĵ�(���ӿ��˵ĳɼ����)
        else if (examine.getAttType() == ExamineConstant.EXAMINE_ATTTYPE_DEPARTMENT
                 || examine.getAttType() == ExamineConstant.EXAMINE_ATTTYPE_LOCATION)
        {
            // ������е��ӿ���
            List<ExamineBean> subs = examineDAO.queryEntityBeansByFK(examine.getId());

            for (ExamineBean examineBean : subs)
            {
                ProfitExamineBean tem = profitExamineDAO.findByParentAndStep(examineBean.getId(),
                    item.getStep());

                if (tem != null)
                {
                    // ѭ����� ��ø�����Ŀ���
                    sum += tem.getRealValue();
                }
            }
        }

        item.setRealValue(sum);

        if (end)
        {
            processResult(item);
        }
        else
        {
            item.setResult(ExamineConstant.EXAMINE_RESULT_INIT);
        }

        // �޸�
        profitExamineDAO.updateEntityBean(item);

        return true;
    }

    /**
     * @param item
     */
    private void processResult(ProfitExamineBean item)
    {
        // ����Ԥ��
        if (item.getRealValue() > item.getPlanValue())
        {
            item.setResult(ExamineConstant.EXAMINE_RESULT_GOOD);
        }

        if (item.getRealValue() == item.getPlanValue())
        {
            item.setResult(ExamineConstant.EXAMINE_RESULT_OK);
        }

        if (item.getRealValue() < item.getPlanValue())
        {
            item.setResult(ExamineConstant.EXAMINE_RESULT_LESS);
        }

        item.setStatus(ExamineConstant.EXAMINE_ITEM_STATUS_END);
    }

    public int getPlanType()
    {
        return ExamineConstant.EXAMINE_ITEM_TYPE_PROFIT;
    }

    /**
     * @return the examineDAO
     */
    public ExamineDAO getExamineDAO()
    {
        return examineDAO;
    }

    /**
     * @param examineDAO
     *            the examineDAO to set
     */
    public void setExamineDAO(ExamineDAO examineDAO)
    {
        this.examineDAO = examineDAO;
    }

    /**
     * @return the customerExamineLogDAO
     */
    public CustomerExamineLogDAO getCustomerExamineLogDAO()
    {
        return customerExamineLogDAO;
    }

    /**
     * @param customerExamineLogDAO
     *            the customerExamineLogDAO to set
     */
    public void setCustomerExamineLogDAO(CustomerExamineLogDAO customerExamineLogDAO)
    {
        this.customerExamineLogDAO = customerExamineLogDAO;
    }

    /**
     * @return the profitDAO
     */
    public ProfitDAO getProfitDAO()
    {
        return profitDAO;
    }

    /**
     * @param profitDAO
     *            the profitDAO to set
     */
    public void setProfitDAO(ProfitDAO profitDAO)
    {
        this.profitDAO = profitDAO;
    }

    /**
     * @return the profitExamineDAO
     */
    public ProfitExamineDAO getProfitExamineDAO()
    {
        return profitExamineDAO;
    }

    /**
     * @param profitExamineDAO
     *            the profitExamineDAO to set
     */
    public void setProfitExamineDAO(ProfitExamineDAO profitExamineDAO)
    {
        this.profitExamineDAO = profitExamineDAO;
    }
}
