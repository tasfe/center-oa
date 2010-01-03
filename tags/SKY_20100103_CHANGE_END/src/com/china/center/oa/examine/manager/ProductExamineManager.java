/**
 * File Name: ProductExamineManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-2-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.examine.manager;


import java.util.List;

import net.sourceforge.sannotations.annotation.Bean;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.common.ConditionParse;
import com.china.center.common.MYException;
import com.china.center.oa.constant.ExamineConstant;
import com.china.center.oa.constant.ModuleConstant;
import com.china.center.oa.constant.PlanConstant;
import com.china.center.oa.constant.PublicConstant;
import com.china.center.oa.examine.bean.ProductExamineBean;
import com.china.center.oa.examine.bean.ProductExamineItemBean;
import com.china.center.oa.examine.dao.ProductExamineDAO;
import com.china.center.oa.examine.dao.ProductExamineItemDAO;
import com.china.center.oa.examine.helper.ExamineHelper;
import com.china.center.oa.plan.bean.PlanBean;
import com.china.center.oa.plan.dao.PlanDAO;
import com.china.center.oa.publics.User;
import com.china.center.oa.publics.bean.LogBean;
import com.china.center.oa.publics.dao.CommonDAO2;
import com.china.center.oa.publics.dao.LogDAO;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.TimeTools;


/**
 * ProductExamineManager
 * 
 * @author zhuzhu
 * @version 2009-2-14
 * @see ProductExamineManager
 * @since 1.0
 */
@Exceptional
@Bean(name = "productExamineManager")
public class ProductExamineManager
{
    private ProductExamineDAO productExamineDAO = null;

    private ProductExamineItemDAO productExamineItemDAO = null;

    private CommonDAO2 commonDAO2 = null;

    private LogDAO logDAO = null;

    private PlanDAO planDAO = null;

    public ProductExamineManager()
    {}

    /**
     * ���Ӳ�Ʒ����
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean addBean(User user, ProductExamineBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkAdd(bean);

        bean.setId(commonDAO2.getSquenceString());

        bean.setStatus(ExamineConstant.EXAMINE_STATUS_INIT);

        bean.setLogTime(TimeTools.now());

        productExamineDAO.saveEntityBean(bean);

        // ����Ĭ�ϵĿ�����
        for (ProductExamineItemBean item : bean.getItems())
        {
            item.setId(commonDAO2.getSquenceString());

            item.setPid(bean.getId());

            productExamineItemDAO.saveEntityBean(item);
        }

        return true;
    }

    /**
     * �޸Ĳ�Ʒ����
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateBean(User user, ProductExamineBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkUpdate(bean);

        bean.setStatus(ExamineConstant.EXAMINE_STATUS_INIT);

        productExamineDAO.updateEntityBean(bean);

        return true;
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean delBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        checkDel(id);

        productExamineDAO.deleteEntityBean(id);

        productExamineItemDAO.deleteEntityBeansByFK(id);

        return true;
    }

    /**
     * �������еĿ���״̬
     * 
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public int updateAllStatus()
    {
        // �Ȳ�ѯ������ʱ��С�ڽ���Ĳ�Ʒ����
        ConditionParse condition = new ConditionParse();

        condition.addCondition("endTime", "<=", TimeTools.now());

        // �����е�
        condition.addCondition("status", "=", ExamineConstant.EXAMINE_STATUS_PASS);

        List<ProductExamineBean> list = productExamineDAO.queryEntityBeansByCondition(condition);

        int total = 0;
        
        // ѭ������
        for (ProductExamineBean productExamineBean : list)
        {
            // ���������Ƿ񶼽�����
            List<ProductExamineItemBean> temp = productExamineItemDAO.queryEntityBeansByFK(productExamineBean.getId());

            boolean isEnd = true;

            for (ProductExamineItemBean productExamineItemBean : temp)
            {
                if (productExamineItemBean.getStatus() != ExamineConstant.EXAMINE_ITEM_STATUS_END)
                {
                    isEnd = false;

                    break;
                }
            }

            if (isEnd)
            {
                // ������Ʒ����
                productExamineDAO.updateStatus(productExamineBean.getId(),
                    ExamineConstant.EXAMINE_STATUS_END);

                total++ ;
            }
        }

        return total;
    }

    /**
     * ���ò�Ʒ����
     * 
     * @param user
     * @param parentId
     * @param items
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean configProductExamine(User user, String parentId,
                                        List<ProductExamineItemBean> items)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, items);

        checkConfig(parentId);

        productExamineItemDAO.deleteEntityBeansByFK(parentId);

        for (ProductExamineItemBean item : items)
        {
            item.setId(commonDAO2.getSquenceString());

            item.setPid(parentId);

            productExamineItemDAO.saveEntityBean(item);
        }

        return true;
    }

    /**
     * �ύ����(ֱ�ӽ�������)
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean submitBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        checkSubmit(user, id);

        // ���¿��˵�����
        productExamineDAO.updateStatus(id, ExamineConstant.EXAMINE_STATUS_PASS);

        // ����ִ�мƻ�(���ĵ�һ��)
        createPlans(id);

        saveApproveLog(user, id, ModuleConstant.MODULE_EXAMINE, PublicConstant.OPERATION_SUBMIT,
            "�����ƶ����ύȷ��,��������״̬");

        return true;
    }

    /**
     * ���ɿ���ִ�мƻ�--�ƻ�����5
     * 
     * @param id
     */
    private void createPlans(String id)
    {
        ProductExamineBean bean = productExamineDAO.find(id);

        List<ProductExamineItemBean> pList = productExamineItemDAO.queryEntityBeansByFK(id);

        for (ProductExamineItemBean item : pList)
        {
            createProductPlan(item, bean);
        }
    }

    /**
     * ��Ʒ���˵����ȷ����ʱ5��
     * 
     * @param item
     */
    private void createProductPlan(ProductExamineItemBean item, ProductExamineBean bean)
    {
        PlanBean plan = new PlanBean();

        plan.setId(commonDAO2.getSquenceString());

        plan.setFkId(item.getId());

        plan.setType(ExamineConstant.EXAMINE_ITEM_TYPE_PRODUCT);

        plan.setCarryType(PlanConstant.PLAN_CARRYTYPES_EVERYDAY);

        plan.setStatus(PlanConstant.PLAN_STATUS_INIT);

        plan.setLogTime(TimeTools.now());

        plan.setBeginTime(bean.getBeginTime());

        plan.setEndTime(bean.getEndTime());

        plan.setCarryTime(TimeTools.getStringByOrgAndDays(bean.getEndTime(), 5));

        plan.setDescription("ϵͳ�Զ����ɵ�����");

        planDAO.saveEntityBean(plan);
    }

    /**
     * checkSubmit
     * 
     * @param user
     * @param id
     * @throws MYException
     */
    private void checkSubmit(User user, String id)
        throws MYException
    {
        ProductExamineBean old = productExamineDAO.find(id);

        if (old == null)
        {
            throw new MYException("��ȷ�Ͽ����Ƿ����");
        }

        if ( !user.getStafferId().equals(old.getCreaterId()))
        {
            throw new MYException("ֻ�п����ƶ��߿����ύ");
        }

        if (ExamineHelper.isReadonly(old.getStatus()))
        {
            throw new MYException("ֻ�г�ʼ̬�Ŀ����ύ");
        }
    }

    /**
     * ���ӵ�ҵ���߼����
     * 
     * @param bean
     * @throws MYException
     */
    private void checkAdd(ProductExamineBean bean)
        throws MYException
    {}

    /**
     * checkUpdate
     * 
     * @param bean
     * @throws MYException
     */
    private void checkUpdate(ProductExamineBean bean)
        throws MYException
    {}

    /**
     * checkDel
     * 
     * @param id
     * @throws MYException
     */
    private void checkDel(String id)
        throws MYException
    {
        ProductExamineBean old = productExamineDAO.find(id);

        if (old == null)
        {
            throw new MYException("��Ʒ���˲�����,��ȷ��");
        }

        if (ExamineHelper.isReadonly(old.getStatus()))
        {
            throw new MYException("ֻ�г�ʼ̬����ɾ��");
        }
    }

    /**
     * ���ü��
     * 
     * @param id
     * @throws MYException
     */
    private void checkConfig(String id)
        throws MYException
    {
        ProductExamineBean parent = productExamineDAO.find(id);

        if (parent == null)
        {
            throw new MYException("��Ʒ���˲�����,��ȷ��");
        }

        if (ExamineHelper.isReadonly(parent.getStatus()))
        {
            throw new MYException("ֻ�г�ʼ̬��������");
        }
    }

    /**
     * д���ݿ���־
     * 
     * @param user
     * @param id
     */
    private void saveApproveLog(User user, String id, String module, String operation, String logs)
    {
        LogBean log = new LogBean();
        log.setFkId(id);
        log.setLocationId(user.getLocationId());
        log.setStafferId(user.getStafferId());
        log.setModule(module);
        log.setOperation(operation);
        log.setLogTime(TimeTools.now());
        log.setLog(logs);
        logDAO.saveEntityBean(log);
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
     * @return the planDAO
     */
    public PlanDAO getPlanDAO()
    {
        return planDAO;
    }

    /**
     * @param planDAO
     *            the planDAO to set
     */
    public void setPlanDAO(PlanDAO planDAO)
    {
        this.planDAO = planDAO;
    }
}
