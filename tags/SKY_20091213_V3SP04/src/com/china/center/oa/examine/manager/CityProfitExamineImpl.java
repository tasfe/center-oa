/**
 * File Name: NewCustomerExamineImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-1-11<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.examine.manager;


import net.sourceforge.sannotations.annotation.Bean;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.common.MYException;
import com.china.center.oa.constant.ExamineConstant;
import com.china.center.oa.examine.bean.CityProfitExamineBean;
import com.china.center.oa.examine.bean.ExamineBean;
import com.china.center.oa.examine.dao.CityProfitExamineDAO;
import com.china.center.oa.examine.dao.CustomerExamineLogDAO;
import com.china.center.oa.examine.dao.ExamineDAO;
import com.china.center.oa.plan.bean.PlanBean;
import com.china.center.oa.plan.manager.CarryPlan;
import com.china.center.oa.plan.manager.PlanManager;
import com.china.center.tools.StringTools;


/**
 * �������󿼺�--ʵ��
 * 
 * @author zhuzhu
 * @version 2009-1-11
 * @see CityProfitExamineImpl
 * @since 1.0
 */
@Exceptional
@Bean(name = "cityProfitExamineImpl", initMethod = "init")
public class CityProfitExamineImpl implements CarryPlan
{
    private ExamineDAO examineDAO = null;

    private CityProfitExamineDAO cityProfitExamineDAO = null;

    private CustomerExamineLogDAO customerExamineLogDAO = null;

    /**
     * default constructor
     */
    public CityProfitExamineImpl()
    {}

    /**
     * ��ʼ��ע��
     */
    public void init()
    {
        PlanManager.addCarryPlan("cityProfitExamineImpl");
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

        CityProfitExamineBean item = cityProfitExamineDAO.find(itemId);

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

        // ��ѯָ��ʱ���ڵ���������֮��
        double sum = cityProfitExamineDAO.queryCityProfit(item.getCityId(), item.getBeginTime(),
            item.getEndTime());

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
        cityProfitExamineDAO.updateEntityBean(item);

        return true;
    }

    /**
     * @param item
     */
    private void processResult(CityProfitExamineBean item)
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
        return ExamineConstant.EXAMINE_ITEM_TYPE_CPROFIT;
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
     * @return the cityProfitExamineDAO
     */
    public CityProfitExamineDAO getCityProfitExamineDAO()
    {
        return cityProfitExamineDAO;
    }

    /**
     * @param cityProfitExamineDAO
     *            the cityProfitExamineDAO to set
     */
    public void setCityProfitExamineDAO(CityProfitExamineDAO cityProfitExamineDAO)
    {
        this.cityProfitExamineDAO = cityProfitExamineDAO;
    }
}
