/**
 * File Name: CustomerCheckManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-3-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.manager;


import java.util.List;

import net.sourceforge.sannotations.annotation.Bean;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.common.ConditionParse;
import com.china.center.common.MYException;
import com.china.center.oa.constant.CommonConstant;
import com.china.center.oa.customer.bean.CustomerCheckBean;
import com.china.center.oa.customer.bean.CustomerCheckItemBean;
import com.china.center.oa.customer.dao.CustomerCheckDAO;
import com.china.center.oa.customer.dao.CustomerCheckItemDAO;
import com.china.center.oa.customer.dao.CustomerDAO;
import com.china.center.oa.customer.dao.StafferVSCustomerDAO;
import com.china.center.oa.customer.vo.StafferVSCustomerVO;
import com.china.center.oa.publics.User;
import com.china.center.oa.publics.dao.CommonDAO2;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.TimeTools;


/**
 * CustomerCheckManager
 * 
 * @author zhuzhu
 * @version 2009-3-15
 * @see CustomerCheckManager
 * @since 1.0
 */
@Exceptional
@Bean(name = "customerCheckManager")
public class CustomerCheckManager
{
    private CustomerCheckDAO customerCheckDAO = null;

    private CustomerCheckItemDAO customerCheckItemDAO = null;

    private StafferVSCustomerDAO stafferVSCustomerDAO = null;

    private CustomerDAO customerDAO = null;

    private CommonDAO2 commonDAO2 = null;

    /**
     * default constructor
     */
    public CustomerCheckManager()
    {}

    /**
     * addBean(�ύ)
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean addBean(User user, CustomerCheckBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        bean.setStatus(CommonConstant.STATUS_SUBMIT);

        bean.setApplyerId(user.getStafferId());

        bean.setId(SequenceTools.getSequence("C_"));

        customerCheckDAO.saveEntityBean(bean);

        return true;
    }

    /**
     * passBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean passBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        CustomerCheckBean bean = checkPass(id);

        bean.setStatus(CommonConstant.STATUS_PASS);

        bean.setApproverId(user.getStafferId());

        customerCheckDAO.updateEntityBean(bean);

        int current = customerCheckItemDAO.countByFK(id);

        // �����gonon�����ͨ��,����Ҫ����Ĳ���
        if (current > 0)
        {
            return true;
        }

        // ����item
        List<StafferVSCustomerVO> list = stafferVSCustomerDAO.queryEntityVOsByFK(bean.getCheckerId());

        for (StafferVSCustomerVO stafferVSCustomerVO : list)
        {
            CustomerCheckItemBean item = new CustomerCheckItemBean();

            item.setCustomerId(stafferVSCustomerVO.getCustomerId());
            item.setCustomerName(stafferVSCustomerVO.getCustomerName());
            item.setCustomerCode(stafferVSCustomerVO.getCustomerCode());
            item.setStafferId(stafferVSCustomerVO.getStafferId());
            item.setParentId(bean.getId());
            item.setStatus(CommonConstant.STATUS_INIT);
            item.setLogTime(TimeTools.now());

            customerCheckItemDAO.saveEntityBean(item);
        }

        return true;
    }

    /**
     * ��������˶�
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean goonBean(User user, CustomerCheckBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkGoon(user, bean.getId());

        bean.setStatus(CommonConstant.STATUS_SUBMIT);

        customerCheckDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * updateCheckItem
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateCheckItem(User user, CustomerCheckItemBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkUpdateCheckItem(user, bean);

        bean.setStatus(CommonConstant.STATUS_END);

        bean.setLogTime(TimeTools.now());

        customerCheckItemDAO.updateEntityBean(bean);

        return true;
    }

    @Transactional(rollbackFor = {MYException.class})
    public int autoChangeStatus()
        throws MYException
    {
        ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        condition.addIntCondition("status", "=", CommonConstant.STATUS_PASS);

        condition.addCondition("endTime", "<", TimeTools.now());

        List<CustomerCheckBean> list = customerCheckDAO.queryEntityBeansByCondition(condition);

        for (CustomerCheckBean customerCheckBean : list)
        {
            customerCheckBean.setRetInit(countResult(customerCheckBean, CommonConstant.RESULT_INIT));

            customerCheckBean.setRetOK(countResult(customerCheckBean, CommonConstant.RESULT_OK));

            customerCheckBean.setRetError(countResult(customerCheckBean,
                CommonConstant.RESULT_ERROR));

            customerCheckBean.setStatus(CommonConstant.STATUS_END);

            customerCheckDAO.updateEntityBean(customerCheckBean);
        }

        return list.size();
    }

    /**
     * @param customerCheckBean
     * @return
     */
    private int countResult(CustomerCheckBean customerCheckBean, int result)
    {
        ConditionParse icon = new ConditionParse();

        icon.addWhereStr();

        icon.addCondition("parentId", "=", customerCheckBean.getId());

        icon.addIntCondition("ret", "=", result);

        return customerCheckItemDAO.countByCondition(icon.toString());
    }

    /**
     * checkUpdateCheckItem
     * 
     * @param bean
     * @throws MYException
     */
    private void checkUpdateCheckItem(User user, CustomerCheckItemBean bean)
        throws MYException
    {
        CustomerCheckItemBean old = customerCheckItemDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("���ݲ��걸");
        }

        if (old.getStatus() != CommonConstant.STATUS_INIT)
        {
            throw new MYException("״̬����,�����ύ");
        }

        CustomerCheckBean parent = customerCheckDAO.find(old.getParentId());

        if (parent == null)
        {
            throw new MYException("���ݲ��걸");
        }

        if ( !user.getStafferId().equals(parent.getApplyerId()))
        {
            throw new MYException("ֻ���ύ�Լ�������˲�");
        }
    }

    /**
     * delBean
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean delBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        checkdel(user, id);

        customerCheckDAO.deleteEntityBean(id);

        customerCheckItemDAO.deleteEntityBeansByFK(id);

        return true;
    }

    /**
     * rejectBean
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean rejectBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        CustomerCheckBean bean = checkReject(id);

        bean.setStatus(CommonConstant.STATUS_REJECT);

        customerCheckDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * checkPass
     * 
     * @param bean
     * @throws MYException
     */
    private CustomerCheckBean checkPass(String id)
        throws MYException
    {
        CustomerCheckBean old = customerCheckDAO.find(id);

        if (old == null)
        {
            throw new MYException("���ݲ��걸");
        }

        if (old.getStatus() != CommonConstant.STATUS_SUBMIT)
        {
            throw new MYException("û���ύ,����ͨ��");
        }

        return old;
    }

    /**
     * checkGoon
     * 
     * @param id
     * @return
     * @throws MYException
     */
    private CustomerCheckBean checkGoon(User user, String id)
        throws MYException
    {
        CustomerCheckBean old = customerCheckDAO.find(id);

        if (old == null)
        {
            throw new MYException("���ݲ��걸");
        }

        if (old.getStatus() != CommonConstant.STATUS_END)
        {
            throw new MYException("û�н���,���ܼ�������˶�");
        }

        if ( !user.getStafferId().equals(old.getApplyerId()))
        {
            throw new MYException("ֻ�ܼ����˶��Լ������");
        }

        if (old.getRetInit() == 0)
        {
            throw new MYException("�Ѿ�ȫ���˶Խ���,���ܼ����˶�");
        }

        return old;
    }

    /**
     * checkdel
     * 
     * @param user
     * @param id
     * @throws MYException
     */
    private void checkdel(User user, String id)
        throws MYException
    {
        CustomerCheckBean old = customerCheckDAO.find(id);

        if (old == null)
        {
            throw new MYException("���ݲ��걸");
        }

        if ( ! (old.getStatus() != CommonConstant.STATUS_INIT
                || old.getStatus() != CommonConstant.STATUS_REJECT || old.getStatus() != CommonConstant.STATUS_PASS))
        {
            throw new MYException("״̬����,����ɾ��");
        }

        if ( !user.getStafferId().equals(old.getApplyerId()))
        {
            throw new MYException("ֻ��ɾ���Լ�������");
        }
    }

    /**
     * checkReject
     * 
     * @param id
     * @throws MYException
     */
    private CustomerCheckBean checkReject(String id)
        throws MYException
    {
        CustomerCheckBean old = customerCheckDAO.find(id);

        if (old == null)
        {
            throw new MYException("���ݲ��걸");
        }

        if (old.getStatus() != CommonConstant.STATUS_SUBMIT)
        {
            throw new MYException("û���ύ,���ܲ���");
        }

        return old;
    }

    /**
     * @return the customerCheckDAO
     */
    public CustomerCheckDAO getCustomerCheckDAO()
    {
        return customerCheckDAO;
    }

    /**
     * @param customerCheckDAO
     *            the customerCheckDAO to set
     */
    public void setCustomerCheckDAO(CustomerCheckDAO customerCheckDAO)
    {
        this.customerCheckDAO = customerCheckDAO;
    }

    /**
     * @return the customerCheckItemDAO
     */
    public CustomerCheckItemDAO getCustomerCheckItemDAO()
    {
        return customerCheckItemDAO;
    }

    /**
     * @param customerCheckItemDAO
     *            the customerCheckItemDAO to set
     */
    public void setCustomerCheckItemDAO(CustomerCheckItemDAO customerCheckItemDAO)
    {
        this.customerCheckItemDAO = customerCheckItemDAO;
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
     * @return the stafferVSCustomerDAO
     */
    public StafferVSCustomerDAO getStafferVSCustomerDAO()
    {
        return stafferVSCustomerDAO;
    }

    /**
     * @param stafferVSCustomerDAO
     *            the stafferVSCustomerDAO to set
     */
    public void setStafferVSCustomerDAO(StafferVSCustomerDAO stafferVSCustomerDAO)
    {
        this.stafferVSCustomerDAO = stafferVSCustomerDAO;
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
