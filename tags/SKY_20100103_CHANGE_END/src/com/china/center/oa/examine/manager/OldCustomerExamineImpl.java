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
import java.util.Map;

import net.sourceforge.sannotations.annotation.Bean;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.common.MYException;
import com.china.center.oa.constant.ExamineConstant;
import com.china.center.oa.customer.dao.CustomerDAO;
import com.china.center.oa.examine.bean.CustomerExamineLogBean;
import com.china.center.oa.examine.bean.ExamineBean;
import com.china.center.oa.examine.bean.OldCustomerExamineBean;
import com.china.center.oa.examine.dao.CustomerExamineLogDAO;
import com.china.center.oa.examine.dao.ExamineDAO;
import com.china.center.oa.examine.dao.OldCustomerExamineDAO;
import com.china.center.oa.examine.wrap.CustomerWrap;
import com.china.center.oa.plan.bean.PlanBean;
import com.china.center.oa.plan.manager.CarryPlan;
import com.china.center.oa.plan.manager.PlanManager;
import com.china.center.tools.StringTools;


/**
 * �¿ͻ��Ŀ���ʵ��
 * 
 * @author zhuzhu
 * @version 2009-1-11
 * @see OldCustomerExamineImpl
 * @since 1.0
 */
@Exceptional
@Bean(name = "oldCustomerExamineImpl", initMethod = "init")
public class OldCustomerExamineImpl implements CarryPlan
{
    private OldCustomerExamineDAO oldCustomerExamineDAO = null;

    private ExamineDAO examineDAO = null;

    private CustomerDAO customerDAO = null;

    private CustomerExamineLogDAO customerExamineLogDAO = null;

    /**
     * default constructor
     */
    public OldCustomerExamineImpl()
    {}

    /**
     * ��ʼ��ע��
     */
    public void init()
    {
        PlanManager.addCarryPlan("oldCustomerExamineImpl");
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

        OldCustomerExamineBean item = oldCustomerExamineDAO.find(itemId);

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

        // ��ÿ��˵�ְԱ��ʱ�䣬��out�����ѯ�����ϼ�¼������
        Map map = oldCustomerExamineDAO.queryOutCustomerByCondition(examine.getStafferId(),
            item.getBeginTime(), item.getEndTime());

        List<CustomerWrap> clist = (List<CustomerWrap>)map.get("clist");

        int count = (Integer)map.get("count");

        // ɾ����ʱlog
        customerExamineLogDAO.deleteEntityBeansByFK(itemId);

        for (CustomerWrap customerBean : clist)
        {
            saveLog(plan, item, customerBean);
        }

        item.setRealValue(count);

        if (end)
        {
            processResult(item);
        }
        else
        {
            item.setResult(ExamineConstant.EXAMINE_RESULT_INIT);
        }

        // �޸�
        oldCustomerExamineDAO.updateEntityBean(item);

        return true;
    }

    /**
     * @param item
     */
    private void processResult(OldCustomerExamineBean item)
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

    /**
     * ������־�����㿼�˲�ѯ
     * 
     * @param plan
     * @param item
     * @param customerBean
     */
    private void saveLog(PlanBean plan, OldCustomerExamineBean item, CustomerWrap customerBean)
    {
        CustomerExamineLogBean log = new CustomerExamineLogBean();

        log.setPlanId(plan.getId());

        log.setItemId(item.getId());

        log.setCustomerId(customerBean.getId());

        log.setCustomerName(customerBean.getName());

        log.setOutId(customerBean.getOutId());
        
        log.setLogTime(customerBean.getLogTime());

        customerExamineLogDAO.saveEntityBean(log);
    }

    /**
     * �Ͽͻ����˵ı�־
     */
    public int getPlanType()
    {
        return ExamineConstant.EXAMINE_ITEM_TYPE_OLDCUSTOMER;
    }

    /**
     * @return the oldCustomerExamineDAO
     */
    public OldCustomerExamineDAO getOldCustomerExamineDAO()
    {
        return oldCustomerExamineDAO;
    }

    /**
     * @param oldCustomerExamineDAO
     *            the oldCustomerExamineDAO to set
     */
    public void setOldCustomerExamineDAO(OldCustomerExamineDAO oldCustomerExamineDAO)
    {
        this.oldCustomerExamineDAO = oldCustomerExamineDAO;
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
}
