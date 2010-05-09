/**
 * File Name: CustomerManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.manager;


import net.sourceforge.sannotations.annotation.Bean;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.annosql.constant.AnoConstant;
import com.china.center.common.MYException;
import com.china.center.oa.constant.CustomerConstant;
import com.china.center.oa.constant.OperationConstant;
import com.china.center.oa.constant.PublicConstant;
import com.china.center.oa.customer.bean.AssignApplyBean;
import com.china.center.oa.customer.bean.ChangeLogBean;
import com.china.center.oa.customer.bean.CustomerApplyBean;
import com.china.center.oa.customer.bean.CustomerBean;
import com.china.center.oa.customer.bean.CustomerHisBean;
import com.china.center.oa.customer.dao.AssignApplyDAO;
import com.china.center.oa.customer.dao.ChangeLogDAO;
import com.china.center.oa.customer.dao.CustomerApplyDAO;
import com.china.center.oa.customer.dao.CustomerDAO;
import com.china.center.oa.customer.dao.CustomerHisDAO;
import com.china.center.oa.customer.dao.StafferVSCustomerDAO;
import com.china.center.oa.customer.helper.CustomerHelper;
import com.china.center.oa.customer.vs.StafferVSCustomerBean;
import com.china.center.oa.publics.User;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.CityDAO;
import com.china.center.oa.publics.dao.CommonDAO2;
import com.china.center.oa.publics.dao.LocationVSCityDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.vs.LocationVSCityBean;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * CustomerManager
 * 
 * @author zhuzhu
 * @version 2008-11-2
 * @see CustomerManager
 * @since 1.0
 */
@Bean(name = "customerManager")
public class CustomerManager
{
    private UserDAO userDAO = null;

    private CustomerApplyDAO customerApplyDAO = null;

    private CustomerDAO customerDAO = null;

    private CustomerHisDAO customerHisDAO = null;

    private LocationVSCityDAO locationVSCityDAO = null;

    private StafferVSCustomerDAO stafferVSCustomerDAO = null;

    private CityDAO cityDAO = null;

    private ChangeLogDAO changeLogDAO = null;

    private CommonDAO2 commonDAO2 = null;

    private AssignApplyDAO assignApplyDAO = null;

    private StafferDAO stafferDAO = null;

    public CustomerManager()
    {}

    /**
     * applyAddCustomer(�������ӿͻ���code��ϵͳ�Զ����ɵ�)
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean applyAddCustomer(User user, CustomerApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean, bean.getName());

        bean.setName(bean.getName().trim());

        checkAddBean(bean);

        CustomerHelper.encryptCustomer(bean);

        bean.setStatus(CustomerConstant.STATUS_APPLY);

        bean.setUpdaterId(user.getStafferId());

        bean.setOpr(CustomerConstant.OPR_ADD);

        bean.setLocationId(user.getLocationId());

        bean.setId(commonDAO2.getSquenceString());

        bean.setCode(bean.getId());

        bean.setLoginTime(TimeTools.now());

        bean.setCreateTime(TimeTools.now());

        customerApplyDAO.saveEntityBean(bean);

        return true;
    }

    /**
     * addAssignApply(���ӿͻ���ְԱ�Ķ�Ӧ��ϵ)
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public synchronized boolean addAssignApply(User user, AssignApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        CustomerBean cus = checkAssign(bean);

        cus.setStatus(CustomerConstant.REAL_STATUS_APPLY);

        assignApplyDAO.saveEntityBean(bean);

        customerDAO.updateEntityBean(cus);

        return true;
    }

    /**
     * @param bean
     * @throws MYException
     */
    private CustomerBean checkAssign(AssignApplyBean bean)
        throws MYException
    {
        if (assignApplyDAO.countByUnique(bean.getCustomerId()) > 0)
        {
            throw new MYException("�ͻ��Ѿ�������");
        }

        CustomerBean cus = customerDAO.find(bean.getCustomerId());

        if (cus == null)
        {
            throw new MYException("�������Ŀͻ�������");
        }

        if (cus.getStatus() != CustomerConstant.REAL_STATUS_IDLE)
        {
            throw new MYException("�������Ŀͻ�[%s]���ڿ���̬,���ʵ", cus.getName());
        }

        checkAtt(bean, cus);

        return cus;
    }

    /**
     * applyUpdateCustomer
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public synchronized boolean applyUpdateCustomer(User user, CustomerApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean, bean.getId());

        checkUpdateBean(bean);

        // ɾ�����ڵ�(ֱ�Ӹ���)
        customerApplyDAO.deleteEntityBean(bean.getId());

        bean.setUpdaterId(user.getStafferId());

        CustomerHelper.encryptCustomer(bean);

        bean.setStatus(CustomerConstant.STATUS_APPLY);

        bean.setOpr(CustomerConstant.OPR_UPDATE);

        bean.setLocationId(user.getLocationId());

        bean.setLoginTime(TimeTools.now());

        customerApplyDAO.saveEntityBean(bean);

        return true;
    }

    /**
     * applyDelCustomer
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean applyDelCustomer(User user, CustomerApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean, bean.getId());

        checkDelCustomer(bean);

        // ɾ�����ڵ�(ֱ�Ӹ���)
        customerApplyDAO.deleteEntityBean(bean.getId());

        CustomerHelper.encryptCustomer(bean);

        bean.setUpdaterId(user.getStafferId());

        bean.setStatus(CustomerConstant.STATUS_APPLY);

        bean.setOpr(CustomerConstant.OPR_DEL);

        bean.setLocationId(user.getLocationId());

        bean.setLoginTime(TimeTools.now());

        customerApplyDAO.saveEntityBean(bean);

        return true;
    }

    /**
     * ���ɾ��
     * 
     * @param bean
     * @throws MYException
     */
    private void checkDelCustomer(CustomerApplyBean bean)
        throws MYException
    {
        if (customerDAO.countCustomerInOut(bean.getId()) > 0
            || customerDAO.countCustomerInBill(bean.getId()) > 0)
        {
            throw new MYException("�ͻ�[%s]�Ѿ���ʹ��,����ɾ��", bean.getName());
        }
    }

    /**
     * updateBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean rejectApplyCustomer(User user, String cid, String reson)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, cid);

        CustomerApplyBean bean = customerApplyDAO.find(cid);

        checkPass(bean);

        bean.setStatus(CustomerConstant.STATUS_REJECT);

        bean.setReson(reson);

        customerApplyDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * updateBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public synchronized boolean passApplyCustomer(User user, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, cid);

        CustomerApplyBean bean = customerApplyDAO.find(cid);

        checkPass(bean);

        if (bean.getOpr() == CustomerConstant.OPR_ADD)
        {
            // �������ı�״̬
            customerApplyDAO.updateStatus(cid, CustomerConstant.STATUS_WAIT_CODE);
            // handleAdd(user, cid, bean);
        }

        if (bean.getOpr() == CustomerConstant.OPR_UPDATE)
        {
            handleUpdate(user, cid, bean);
        }

        if (bean.getOpr() == CustomerConstant.OPR_DEL)
        {
            checkDelCustomer(bean);

            handleDelete(cid, bean);
        }

        return true;
    }

    /**
     * �������
     * 
     * @param user
     * @param cid
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public synchronized boolean assignApplyCustomerCode(User user, String cid, String code)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, cid, code);

        CustomerApplyBean bean = customerApplyDAO.find(cid);

        checkAssignCode(bean, code);

        bean.setCode(code);

        customerApplyDAO.updateCode(cid, code);

        handleAdd(user, cid, bean);

        return true;
    }

    /**
     * ͨ����������
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public synchronized boolean passAssignApply(User user, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, cid);

        AssignApplyBean apply = assignApplyDAO.find(cid);

        CustomerBean cus = checkOprAssign(apply, true);

        checkAtt(apply, cus);

        // ɾ������
        assignApplyDAO.deleteEntityBean(cid);

        StafferVSCustomerBean vs = new StafferVSCustomerBean();

        vs.setStafferId(apply.getStafferId());

        vs.setCustomerId(apply.getCustomerId());

        // �����Ӧ��ϵ
        addStafferVSCustomer(vs);

        // �޸Ŀͻ�״̬
        cus.setStatus(CustomerConstant.REAL_STATUS_USED);

        customerDAO.updateEntityBean(cus);

        return true;
    }

    /**
     * �����ϵ,˳���¼��־
     * 
     * @param vs
     */
    private void addStafferVSCustomer(StafferVSCustomerBean vs)
    {
        stafferVSCustomerDAO.saveEntityBean(vs);

        CustomerBean cb = customerDAO.find(vs.getCustomerId());

        if (cb == null)
        {
            return;
        }

        StafferBean sb = stafferDAO.find(vs.getStafferId());

        if (sb == null)
        {
            return;
        }

        ChangeLogBean log = new ChangeLogBean();

        log.setCustomerCode(cb.getId());
        log.setCustomerName(cb.getName());
        log.setCustomerCode(cb.getCode());

        log.setStafferId(sb.getId());

        log.setStafferName(sb.getName());

        log.setLogTime(TimeTools.now());

        log.setOperation(OperationConstant.OPERATION_CHANGELOG_ADD);

        changeLogDAO.saveEntityBean(log);
    }

    /**
     * �������
     * 
     * @param apply
     * @param cus
     * @throws MYException
     */
    private void checkAtt(AssignApplyBean apply, CustomerBean cus)
        throws MYException
    {
        StafferBean sb = stafferDAO.find(apply.getStafferId());

        if (sb == null)
        {
            throw new MYException("�����ְԱ������");
        }

        // NOTE zhuzhu �жϿͻ������Ժ�ҵ����Ƿ��Ǻ�
        if (cus.getSelltype() != sb.getExamType())
        {
            throw new MYException("ְԱ[%s]���ն���չ���ԺͿͻ�[%s]���ն���չ���Բ�һ��", sb.getName(), cus.getName());
        }
    }

    /**
     * ���շ���ͻ�
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public synchronized boolean reclaimAssignCustomer(User user, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, cid);

        // ɾ����Ӧ��ϵ
        stafferVSCustomerDAO.deleteEntityBeansByFK(cid, AnoConstant.FK_FIRST);

        // �ָ�����״̬
        customerDAO.updateCustomerstatus(cid, CustomerConstant.REAL_STATUS_IDLE);

        return true;
    }

    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public synchronized boolean reclaimStafferAssignCustomer(User user, String stafferId, int flag)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, stafferId);

        // �ȸ���״̬
        customerDAO.updateCustomerByStafferId(stafferId, CustomerConstant.REAL_STATUS_IDLE, flag);

        // ��ɾ����Ӧ��ϵ(���ܵߵ��������޷����¿ͻ�״̬)
        customerDAO.delCustomerByStafferId(stafferId, flag);

        return true;
    }

    /**
     * ���ط�������(����ɾ��)
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public synchronized boolean rejectAssignApply(User user, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, cid);

        AssignApplyBean apply = assignApplyDAO.find(cid);

        CustomerBean cus = checkOprAssign(apply, false);

        // ɾ������
        assignApplyDAO.deleteEntityBean(cid);

        // ֻ������̬�вŸ���
        if (cus.getStatus() == CustomerConstant.REAL_STATUS_APPLY)
        {
            // �޸Ŀͻ�״̬
            cus.setStatus(CustomerConstant.REAL_STATUS_IDLE);

            customerDAO.updateEntityBean(cus);
        }

        return true;
    }

    /**
     * ������ͻ��������߼�У��
     * 
     * @param apply
     * @return
     * @throws MYException
     */
    private CustomerBean checkOprAssign(AssignApplyBean apply, boolean pass)
        throws MYException
    {
        if (apply == null)
        {
            throw new MYException("���벻����,��ˢ������");
        }

        CustomerBean cus = customerDAO.find(apply.getCustomerId());

        if (cus == null)
        {
            throw new MYException("�������Ŀͻ�������");
        }

        if (pass && cus.getStatus() == CustomerConstant.REAL_STATUS_USED)
        {
            throw new MYException("�������Ŀͻ�[%s]�Ѿ���ʹ��,���ʵ", cus.getName());
        }

        return cus;
    }

    /**
     * @param cid
     * @param bean
     */
    private void handleDelete(String cid, CustomerApplyBean bean)
    {
        CustomerBean cbean = new CustomerBean();

        bean.setLoginTime(TimeTools.now());

        BeanUtil.copyProperties(cbean, bean);

        // ��apply����ɾ��
        customerApplyDAO.deleteEntityBean(cid);

        // ������������
        customerDAO.deleteEntityBean(cid);

        // ��Ҫɾ��ְԱ�Ϳͻ��Ķ�Ӧ��ϵ
        stafferVSCustomerDAO.deleteEntityBeansByFK(bean.getId(), AnoConstant.FK_FIRST);
    }

    /**
     * @param user
     * @param cid
     * @param bean
     * @throws MYException
     */
    private void handleUpdate(User user, String cid, CustomerApplyBean bean)
        throws MYException
    {
        CustomerBean cbean = new CustomerBean();

        CustomerHisBean hisbean = new CustomerHisBean();

        bean.setLoginTime(TimeTools.now());

        BeanUtil.copyProperties(cbean, bean);

        BeanUtil.copyProperties(hisbean, bean);

        // ��apply����ɾ��
        customerApplyDAO.deleteEntityBean(cid);

        checkRealAdd(cbean);

        CustomerBean oldBean = customerDAO.find(cid);

        if (oldBean == null)
        {
            throw new MYException("�޸ĵĿͻ�������");
        }

        cbean.setStatus(oldBean.getStatus());

        // ���뵽��������
        customerDAO.updateEntityBean(cbean);

        hisbean.setCustomerId(cid);

        // ��¼��his
        customerHisDAO.saveEntityBean(hisbean);
    }

    /**
     * �������Ӵ�apply����ʽ��
     * 
     * @param user
     * @param cid
     * @param bean
     * @throws MYException
     */
    private void handleAdd(User user, String cid, CustomerApplyBean bean)
        throws MYException
    {
        String createrId = bean.getCreaterId();

        CustomerBean cbean = new CustomerBean();

        CustomerHisBean hisbean = new CustomerHisBean();

        bean.setLoginTime(TimeTools.now());

        BeanUtil.copyProperties(cbean, bean);

        BeanUtil.copyProperties(hisbean, bean);

        // ��apply����ɾ��
        customerApplyDAO.deleteEntityBean(cid);

        checkRealAdd(cbean);

        cbean.setStatus(CustomerConstant.REAL_STATUS_IDLE);

        // �����Ӧ��ϵ
        if (stafferVSCustomerDAO.countByUnique(bean.getId()) == 0)
        {
            StafferVSCustomerBean vs = new StafferVSCustomerBean();

            vs.setStafferId(createrId);

            vs.setCustomerId(bean.getId());

            addStafferVSCustomer(vs);

            // �޸ĳɱ�ʹ�õĿͻ�(����û������Ļ����ˣ���һ��bug)
            cbean.setStatus(CustomerConstant.REAL_STATUS_USED);
        }

        // ���뵽��������
        customerDAO.saveEntityBean(cbean);

        hisbean.setCustomerId(cid);

        hisbean.setUpdaterId(user.getStafferId());

        hisbean.setCheckStatus(CustomerConstant.HIS_CHECK_YES);

        // ��¼��his
        customerHisDAO.saveEntityBean(hisbean);
    }

    /**
     * �������ǵ����
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public void addCustomer(User user, CustomerBean bean, String stafferId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        // ����ID
        bean.setId(commonDAO2.getSquenceString());

        bean.setStatus(CustomerConstant.REAL_STATUS_IDLE);

        bean.setLoginTime(TimeTools.now());

        bean.setCreateTime(TimeTools.now());

        // ����
        CustomerHelper.encryptCustomer(bean);

        CustomerHisBean hisbean = new CustomerHisBean();

        bean.setLoginTime(TimeTools.now());

        BeanUtil.copyProperties(hisbean, bean);

        if (customerDAO.countByUnique(bean.getName().trim()) > 0)
        {
            throw new MYException("�ͻ�����[%s]�Ѿ�����", bean.getName());
        }

        checkRealAdd(bean);

        // ���뵽��������
        customerDAO.saveEntityBean(bean);

        hisbean.setCustomerId(bean.getId());

        hisbean.setUpdaterId(user.getStafferId());

        // ��¼��his
        customerHisDAO.saveEntityBean(hisbean);

        // ����ְԱ�Ϳͻ��Ķ�Ӧ��ϵ
        if ( !StringTools.isNullOrNone(stafferId))
        {
            StafferVSCustomerBean vs = new StafferVSCustomerBean();

            vs.setStafferId(stafferId);

            vs.setCustomerId(bean.getId());

            addStafferVSCustomer(vs);
        }
    }

    /**
     * @param bean
     * @throws MYException
     */
    private void checkPass(CustomerApplyBean bean)
        throws MYException
    {
        if (bean == null)
        {
            throw new MYException("�����Ŀͻ�������");
        }

        if (bean.getStatus() != CustomerConstant.STATUS_APPLY)
        {
            throw new MYException("�����Ŀͻ���������̬");
        }
    }

    private void checkAssignCode(CustomerApplyBean bean, String newCode)
        throws MYException
    {
        if (bean == null)
        {
            throw new MYException("�����Ŀͻ�������");
        }

        if (bean.getStatus() != CustomerConstant.STATUS_WAIT_CODE)
        {
            throw new MYException("�����Ŀͻ����ڵȴ��������״̬");
        }

        // ���code�Ƿ��ظ�
        if ( !bean.getCode().equals(newCode))
        {
            if (customerApplyDAO.countByCode(newCode) > 0 || customerDAO.countByCode(newCode) > 0)
            {
                throw new MYException("����Ŀͻ������ظ�:" + newCode);
            }
        }
    }

    /**
     * ���ӵ���ʽ��ļ���
     * 
     * @param cbean
     * @throws MYException
     */
    private void checkRealAdd(CustomerBean cbean)
        throws MYException
    {
        // �޸���������
        String cityId = cbean.getCityId();

        if (StringTools.isNullOrNone(cityId))
        {
            throw new MYException("�������Բ�����,��ȷ�����ݵ�׼ȷ��");
        }

        LocationVSCityBean lvc = locationVSCityDAO.findByUnique(cityId);

        if (lvc == null)
        {
            CityBean cb = cityDAO.find(cityId);

            if (cb == null)
            {
                throw new MYException("�������Բ���ȷ,��ȷ�����ݵ�׼ȷ��");
            }

            // throw new MYException("����[%s]û�й������κηֹ�˾,��ȷ��");
            // Ĭ�Ϸ��䵽�ܲ�
            cbean.setLocationId(PublicConstant.CENTER_LOCATION);
        }
        else
        {
            cbean.setLocationId(lvc.getLocationId());
        }
    }

    /**
     * delApply
     * 
     * @param user
     * @param stafferId
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean delApply(User user, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, cid);

        CustomerApplyBean bean = customerApplyDAO.find(cid);

        if (bean == null)
        {
            throw new MYException("����Ŀͻ�������");
        }

        if ( !user.getStafferId().equals(bean.getUpdaterId()))
        {
            throw new MYException("ֻ�������˲ſ���ɾ��");
        }

        // ��apply����ɾ��
        customerApplyDAO.deleteEntityBean(cid);

        return true;
    }

    /**
     * checkHisCustomer
     * 
     * @param user
     * @param cid
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean checkHisCustomer(User user, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, cid);

        customerHisDAO.updateCheckStatus(cid, CustomerConstant.HIS_CHECK_YES);

        return true;
    }

    @Exceptional
    public boolean hasCustomerAuth(String stafferId, String customerId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(stafferId, customerId);

        return stafferVSCustomerDAO.countByStafferIdAndCustomerId(stafferId, customerId) > 0;
    }

    /**
     * @param bean
     * @throws MYException
     */
    private void checkAddBean(CustomerApplyBean bean)
        throws MYException
    {
        if (customerApplyDAO.countByUnique(bean.getName().trim()) > 0)
        {
            throw new MYException("�ͻ�[%s]�Ѿ���������", bean.getName());
        }

        if (customerDAO.countByUnique(bean.getName().trim()) > 0)
        {
            throw new MYException("�ͻ�����[%s]�Ѿ�����", bean.getName());
        }

        // ��֤�������Ƿ��ظ�
        if (customerApplyDAO.countByCode(bean.getCode().trim()) > 0)
        {
            throw new MYException("�ͻ�����[%s]�Ѿ���������", bean.getName());
        }

        // ��֤�Ѿ�ok�Ŀͻ����Ƿ�code�ظ�
        if (customerDAO.countByCode(bean.getCode().trim()) > 0)
        {
            throw new MYException("�ͻ�����[%s]�Ѿ�����", bean.getName());
        }
    }

    /**
     * ���update���Ƿ��������
     * 
     * @param bean
     * @throws MYException
     */
    private void checkUpdateBean(CustomerApplyBean bean)
        throws MYException
    {
        CustomerBean old = customerDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("�ͻ�������,�����²���");
        }

        if ( !old.getName().trim().equals(bean.getName().trim()))
        {
            if (customerApplyDAO.countByUnique(bean.getName().trim()) > 0)
            {
                throw new MYException("�ͻ�����[%s]�Ѿ�����", bean.getName());
            }

            if (customerDAO.countByUnique(bean.getName().trim()) > 0)
            {
                throw new MYException("�ͻ�����[%s]�Ѿ�����", bean.getName());
            }
        }

        if ( !old.getCode().trim().equals(bean.getCode().trim()))
        {
            // ��֤�������Ƿ��ظ�
            if (customerApplyDAO.countByCode(bean.getCode().trim()) > 0)
            {
                throw new MYException("�ͻ�����[%s]�Ѿ�����", bean.getCode());
            }

            // ��֤�Ѿ�ok�Ŀͻ����Ƿ�code�ظ�
            if (customerDAO.countByCode(bean.getCode().trim()) > 0)
            {
                throw new MYException("�ͻ�����[%s]�Ѿ�����", bean.getCode());
            }
        }

        CustomerApplyBean cbean = customerApplyDAO.find(bean.getId());

        if (cbean != null)
        {
            if (cbean.getOpr() == CustomerConstant.OPR_UPATE_CREDIT)
            {
                throw new MYException("�ͻ�[%s]���ھ�̬�����޸�,���Ƚ���������", bean.getName());
            }
        }
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
     * @return the userDAO
     */
    public UserDAO getuserDAO()
    {
        return userDAO;
    }

    /**
     * @param userDAO
     *            the userDAO to set
     */
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    /**
     * @return the customerApplyDAO
     */
    public CustomerApplyDAO getCustomerApplyDAO()
    {
        return customerApplyDAO;
    }

    /**
     * @param customerApplyDAO
     *            the customerApplyDAO to set
     */
    public void setCustomerApplyDAO(CustomerApplyDAO customerApplyDAO)
    {
        this.customerApplyDAO = customerApplyDAO;
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
     * @return the customerHisDAO
     */
    public CustomerHisDAO getCustomerHisDAO()
    {
        return customerHisDAO;
    }

    /**
     * @param customerHisDAO
     *            the customerHisDAO to set
     */
    public void setCustomerHisDAO(CustomerHisDAO customerHisDAO)
    {
        this.customerHisDAO = customerHisDAO;
    }

    /**
     * @return the locationVSCityDAO
     */
    public LocationVSCityDAO getLocationVSCityDAO()
    {
        return locationVSCityDAO;
    }

    /**
     * @param locationVSCityDAO
     *            the locationVSCityDAO to set
     */
    public void setLocationVSCityDAO(LocationVSCityDAO locationVSCityDAO)
    {
        this.locationVSCityDAO = locationVSCityDAO;
    }

    /**
     * @return the cityDAO
     */
    public CityDAO getCityDAO()
    {
        return cityDAO;
    }

    /**
     * @param cityDAO
     *            the cityDAO to set
     */
    public void setCityDAO(CityDAO cityDAO)
    {
        this.cityDAO = cityDAO;
    }

    /**
     * @return the assignApplyDAO
     */
    public AssignApplyDAO getAssignApplyDAO()
    {
        return assignApplyDAO;
    }

    /**
     * @param assignApplyDAO
     *            the assignApplyDAO to set
     */
    public void setAssignApplyDAO(AssignApplyDAO assignApplyDAO)
    {
        this.assignApplyDAO = assignApplyDAO;
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
     * @return the stafferDAO
     */
    public StafferDAO getStafferDAO()
    {
        return stafferDAO;
    }

    /**
     * @param stafferDAO
     *            the stafferDAO to set
     */
    public void setStafferDAO(StafferDAO stafferDAO)
    {
        this.stafferDAO = stafferDAO;
    }

    /**
     * @return the changeLogDAO
     */
    public ChangeLogDAO getChangeLogDAO()
    {
        return changeLogDAO;
    }

    /**
     * @param changeLogDAO
     *            the changeLogDAO to set
     */
    public void setChangeLogDAO(ChangeLogDAO changeLogDAO)
    {
        this.changeLogDAO = changeLogDAO;
    }

}
