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
import com.china.center.oa.customer.dao.CustomerDAO;
import com.china.center.oa.examine.bean.CustomerExamineLogBean;
import com.china.center.oa.examine.bean.ProductExamineBean;
import com.china.center.oa.examine.bean.ProductExamineItemBean;
import com.china.center.oa.examine.dao.CustomerExamineLogDAO;
import com.china.center.oa.examine.dao.ProductExamineDAO;
import com.china.center.oa.examine.dao.ProductExamineItemDAO;
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
 * @see ProductExamineImpl
 * @since 1.0
 */
@Exceptional
@Bean(name = "productExamineImpl", initMethod = "init")
public class ProductExamineImpl implements CarryPlan
{
    private ProductExamineDAO productExamineDAO = null;

    private ProductExamineItemDAO productExamineItemDAO = null;

    private CustomerDAO customerDAO = null;

    private CustomerExamineLogDAO customerExamineLogDAO = null;

    /**
     * default constructor
     */
    public ProductExamineImpl()
    {}

    /**
     * ��ʼ��ע��
     */
    public void init()
    {
        PlanManager.addCarryPlan("productExamineImpl");
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

        ProductExamineItemBean item = productExamineItemDAO.find(itemId);

        if (item == null)
        {
            return true;
        }

        // ״̬����Ѿ�����,��ִ��
        if (item.getStatus() == ExamineConstant.EXAMINE_ITEM_STATUS_END)
        {
            return true;
        }

        ProductExamineBean examine = productExamineDAO.find(item.getPid());

        if (examine == null)
        {
            return true;
        }

        // ��ÿ��˵�ְԱ��ʱ�䣬��out�����ѯ�����ϼ�¼������
        List<CustomerWrap> clist = productExamineDAO.queryProductOutCustomerByCondition(
            examine.getProductId(), item.getStafferId(), examine.getBeginTime(),
            examine.getEndTime());

        // ɾ����ʱlog
        customerExamineLogDAO.deleteEntityBeansByFK(itemId);

        for (CustomerWrap customerBean : clist)
        {
            saveLog(plan, itemId, customerBean);
        }

        item.setRealValue(clist.size());

        if (end)
        {
            processResult(item);

            // ���ڲ�Ʒ������˵���еĿ���ʱ��һ��(ֱ�Ӱ�������������)
            if (examine.getStatus() != ExamineConstant.EXAMINE_STATUS_END)
            {
                productExamineDAO.updateStatus(examine.getId(), ExamineConstant.EXAMINE_STATUS_END);
            }
        }
        else
        {
            item.setResult(ExamineConstant.EXAMINE_RESULT_INIT);
        }

        // �޸�
        productExamineItemDAO.updateEntityBean(item);

        return true;
    }

    /**
     * @param item
     */
    private void processResult(ProductExamineItemBean item)
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
    private void saveLog(PlanBean plan, String itemId, CustomerWrap customerBean)
    {
        CustomerExamineLogBean log = new CustomerExamineLogBean();

        log.setPlanId(plan.getId());

        log.setItemId(itemId);

        log.setCustomerId(customerBean.getId());

        log.setCustomerName(customerBean.getName());

        log.setOutId(customerBean.getOutId());

        customerExamineLogDAO.saveEntityBean(log);
    }

    /**
     * ��Ʒ���˵ı�־
     */
    public int getPlanType()
    {
        return ExamineConstant.EXAMINE_ITEM_TYPE_PRODUCT;
    }

    /**
     * @return the productExamineDAO
     */
    public ProductExamineDAO getProductExamineDAO()
    {
        return productExamineDAO;
    }

    /**
     * @param productExamineDAO
     *            the productExamineDAO to set
     */
    public void setProductExamineDAO(ProductExamineDAO productExamineDAO)
    {
        this.productExamineDAO = productExamineDAO;
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

    /**
     * @return the productExamineItemDAO
     */
    public ProductExamineItemDAO getProductExamineItemDAO()
    {
        return productExamineItemDAO;
    }

    /**
     * @param productExamineItemDAO
     *            the productExamineItemDAO to set
     */
    public void setProductExamineItemDAO(ProductExamineItemDAO productExamineItemDAO)
    {
        this.productExamineItemDAO = productExamineItemDAO;
    }
}
