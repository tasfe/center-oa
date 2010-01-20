/**
 * File Name: CityConfigManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-1-3<br>
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
import com.china.center.oa.examine.bean.CityProfitExamineBean;
import com.china.center.oa.examine.bean.ExamineBean;
import com.china.center.oa.examine.bean.NewCustomerExamineBean;
import com.china.center.oa.examine.bean.OldCustomerExamineBean;
import com.china.center.oa.examine.bean.ProfitExamineBean;
import com.china.center.oa.examine.dao.CityProfitExamineDAO;
import com.china.center.oa.examine.dao.CustomerExamineLogDAO;
import com.china.center.oa.examine.dao.ExamineDAO;
import com.china.center.oa.examine.dao.NewCustomerExamineDAO;
import com.china.center.oa.examine.dao.OldCustomerExamineDAO;
import com.china.center.oa.examine.dao.ProfitExamineDAO;
import com.china.center.oa.examine.helper.ExamineHelper;
import com.china.center.oa.plan.bean.PlanBean;
import com.china.center.oa.plan.dao.PlanDAO;
import com.china.center.oa.publics.User;
import com.china.center.oa.publics.bean.LogBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.CommonDAO2;
import com.china.center.oa.publics.dao.LogDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * CityConfigManager
 * 
 * @author zhuzhu
 * @version 2009-1-3
 * @see ExamineManager
 * @since 1.0
 */
@Exceptional
@Bean(name = "examineManager")
public class ExamineManager
{
    private NewCustomerExamineDAO newCustomerExamineDAO = null;

    private OldCustomerExamineDAO oldCustomerExamineDAO = null;

    private ProfitExamineDAO profitExamineDAO = null;

    private CityProfitExamineDAO cityProfitExamineDAO = null;

    private CustomerExamineLogDAO customerExamineLogDAO = null;

    private ExamineDAO examineDAO = null;

    private CommonDAO2 commonDAO2 = null;

    private StafferDAO stafferDAO = null;

    private LogDAO logDAO = null;

    private PlanDAO planDAO = null;

    public ExamineManager()
    {}

    /**
     * ���ӿ���
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public synchronized boolean addBean(User user, ExamineBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        // ����Ĳ���Ҫ���
        if (bean.getAbs() == ExamineConstant.EXAMINE_ABS_FALSE)
        {
            checkAdd(bean);
        }
        else
        {
            bean.setParentId("0");

            bean.setStafferId(user.getStafferId());

            bean.setCreaterId(user.getStafferId());
        }

        bean.setId(commonDAO2.getSquenceString());

        bean.setLogTime(TimeTools.now());

        examineDAO.saveEntityBean(bean);

        return true;
    }

    /**
     * �����¿ͻ�����
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean configNewCustomerExamine(User user, String parentId,
                                            List<NewCustomerExamineBean> items)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, items);

        checkConfig(parentId);

        newCustomerExamineDAO.deleteEntityBeansByFK(parentId);

        for (NewCustomerExamineBean newCustomerExamineBean : items)
        {
            newCustomerExamineBean.setId(commonDAO2.getSquenceString());
            newCustomerExamineDAO.saveEntityBean(newCustomerExamineBean);
        }

        return true;
    }

    /**
     * �����Ͽͻ�����
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean configOldCustomerExamine(User user, String parentId,
                                            List<OldCustomerExamineBean> items)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, items);

        checkConfig(parentId);

        oldCustomerExamineDAO.deleteEntityBeansByFK(parentId);

        for (OldCustomerExamineBean oldCustomerExamineBean : items)
        {
            oldCustomerExamineBean.setId(commonDAO2.getSquenceString());
            oldCustomerExamineDAO.saveEntityBean(oldCustomerExamineBean);
        }

        return true;
    }

    /**
     * ���ë����
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateProfitExamine(User user, String profitExamineId, String reason,
                                       double newProfit)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, profitExamineId);

        ProfitExamineBean bean = checkUpdateProfitExamine(profitExamineId);

        bean.setPlanValue(newProfit);

        // д��־
        saveApproveLog(user, bean.getParentId(), ModuleConstant.MODULE_EXAMINE,
            PublicConstant.OPERATION_CHANGE, reason);

        profitExamineDAO.updateEntityBean(bean);

        ExamineBean examine = examineDAO.find(bean.getParentId());

        if (examine == null)
        {
            throw new MYException("���ݲ��걸");
        }

        // ���ӵ�ϵͳ�����Ƿ�֧
        checkProfit(examine.getId(), examine.getParentId());

        List<ProfitExamineBean> items = profitExamineDAO.queryEntityBeansByFK(bean.getParentId());

        double total = 0.0d;

        for (ProfitExamineBean profitExamineBean2 : items)
        {
            total += profitExamineBean2.getPlanValue();
        }

        // ���¿��˵�ҵ���ܶ�
        examineDAO.updateTotalProfit(bean.getParentId(), total);

        return true;
    }

    /**
     * checkUpdateP
     * 
     * @param profitExamineId
     * @return
     * @throws MYException
     */
    private ProfitExamineBean checkUpdateProfitExamine(String profitExamineId)
        throws MYException
    {
        ProfitExamineBean bean = profitExamineDAO.find(profitExamineId);

        if (bean == null)
        {
            throw new MYException("���ݲ��걸");
        }

        if (bean.getStatus() == ExamineConstant.EXAMINE_ITEM_STATUS_END)
        {
            throw new MYException("�˿������Ѿ�����,�����޸�");
        }

        return bean;
    }

    /**
     * ����ë����(���Ŀ���)
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean configProfitExamine(User user, String parentId, List<ProfitExamineBean> items)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, items);

        ExamineBean bean = checkConfig(parentId);

        profitExamineDAO.deleteEntityBeansByFK(parentId);

        for (ProfitExamineBean profitExamineBean : items)
        {
            profitExamineBean.setId(commonDAO2.getSquenceString());
            profitExamineDAO.saveEntityBean(profitExamineBean);
        }

        // ���ӵ�ϵͳ�����Ƿ�֧
        checkProfit(parentId, bean.getParentId());

        double total = 0.0d;

        for (ProfitExamineBean profitExamineBean2 : items)
        {
            total += profitExamineBean2.getPlanValue();
        }

        // ���¿��˵�ҵ���ܶ�
        examineDAO.updateTotalProfit(parentId, total);

        return true;
    }

    /**
     * ��������ë����
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean configCityProfitExamine(User user, String parentId,
                                           List<CityProfitExamineBean> items)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, items);

        ExamineBean bean = checkConfig2(parentId);

        // handle readonly update
        if (ExamineHelper.isReadonly(bean.getStatus()))
        {
            // cancle task
            List<CityProfitExamineBean> cpList = cityProfitExamineDAO.queryEntityBeansByFK(bean.getId());

            for (CityProfitExamineBean item : cpList)
            {
                planDAO.deleteEntityBeansByFK(item.getId());
            }

            updateItemsInner(parentId, items);

            // add new task
            for (CityProfitExamineBean item : items)
            {
                createCPPlan(item);
            }

            saveApproveLog(user, parentId, ModuleConstant.MODULE_EXAMINE,
                PublicConstant.OPERATION_CHANGE, "��" + user.getStafferId() + "����������ͻ��ɽ�����");
        }
        else
        {
            updateItemsInner(parentId, items);
        }

        return true;
    }

    /**
     * @param parentId
     * @param items
     */
    private void updateItemsInner(String parentId, List<CityProfitExamineBean> items)
    {
        cityProfitExamineDAO.deleteEntityBeansByFK(parentId);

        for (CityProfitExamineBean item : items)
        {
            item.setId(commonDAO2.getSquenceString());

            cityProfitExamineDAO.saveEntityBean(item);
        }
    }

    /**
     * ����޸�����
     * 
     * @param parentId
     * @throws MYException
     */
    private ExamineBean checkConfig(String parentId)
        throws MYException
    {
        ExamineBean bean = examineDAO.find(parentId);

        if (bean == null)
        {
            throw new MYException("���˲�����,��ȷ��");
        }

        if (ExamineHelper.isReadonly(bean.getStatus()))
        {
            throw new MYException("ֻ�г�ʼ�Ͳ��صĿ����޸�");
        }

        return bean;
    }

    private ExamineBean checkConfig2(String parentId)
        throws MYException
    {
        ExamineBean bean = examineDAO.find(parentId);

        if (bean == null)
        {
            throw new MYException("���˲�����,��ȷ��");
        }

        return bean;
    }

    /**
     * �޸Ŀ���
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateBean(User user, ExamineBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkUpdate(user, bean);

        bean.setStatus(ExamineConstant.EXAMINE_STATUS_INIT);

        examineDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * ɾ������(��û�����е�)
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

        checkDel(user, id);

        // ɾ��������
        delItems(id);

        examineDAO.deleteEntityBean(id);

        // ɾ����ص���־
        logDAO.deleteEntityBeansByFK(id);

        return true;
    }

    /**
     * ɾ������(���е�)
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean delBean2(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        checkDel2(user, id);

        // ɾ��������
        delItems2(id);

        examineDAO.deleteEntityBean(id);

        saveApproveLog(user, id, ModuleConstant.MODULE_EXAMINE, PublicConstant.OPERATION_DEL,
            user.getStafferId() + "ɾ������");

        return true;
    }

    /**
     * ɾ��������
     * 
     * @param id
     */
    private void delItems(String id)
    {
        // ɾ���������󿼺���
        cityProfitExamineDAO.deleteEntityBeansByFK(id);

        // ɾ���¿ͻ�������
        newCustomerExamineDAO.deleteEntityBeansByFK(id);

        // ɾ���Ͽͻ�������
        oldCustomerExamineDAO.deleteEntityBeansByFK(id);

        // ɾ�����󿼺���
        profitExamineDAO.deleteEntityBeansByFK(id);
    }

    /**
     * ɾ��������ͼƻ�
     * 
     * @param id
     */
    private void delItems2(String id)
    {
        // ���򿼺�
        List<CityProfitExamineBean> cList = cityProfitExamineDAO.queryEntityBeansByFK(id);
        for (CityProfitExamineBean cityProfitExamineBean : cList)
        {
            planDAO.deleteEntityBeansByFK(cityProfitExamineBean.getId());
        }
        // ɾ���������󿼺���
        cityProfitExamineDAO.deleteEntityBeansByFK(id);

        List<NewCustomerExamineBean> nList = newCustomerExamineDAO.queryEntityBeansByFK(id);
        for (NewCustomerExamineBean newCustomerExamineBean : nList)
        {
            customerExamineLogDAO.deleteEntityBeansByFK(newCustomerExamineBean.getId());

            planDAO.deleteEntityBeansByFK(newCustomerExamineBean.getId());
        }
        // ɾ���¿ͻ�������
        newCustomerExamineDAO.deleteEntityBeansByFK(id);

        List<OldCustomerExamineBean> oList = oldCustomerExamineDAO.queryEntityBeansByFK(id);
        for (OldCustomerExamineBean oldCustomerExamineBean : oList)
        {
            customerExamineLogDAO.deleteEntityBeansByFK(oldCustomerExamineBean.getId());

            planDAO.deleteEntityBeansByFK(oldCustomerExamineBean.getId());
        }
        // ɾ���Ͽͻ�������
        oldCustomerExamineDAO.deleteEntityBeansByFK(id);

        List<ProfitExamineBean> pList = profitExamineDAO.queryEntityBeansByFK(id);
        for (ProfitExamineBean profitExamineBean : pList)
        {
            planDAO.deleteEntityBeansByFK(profitExamineBean.getId());
        }
        // ɾ�����󿼺���
        profitExamineDAO.deleteEntityBeansByFK(id);
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

        ExamineBean bean = checkSubmit(user, id);

        // ���¿��˵�����
        examineDAO.updateStatus(id, ExamineConstant.EXAMINE_STATUS_PASS);

        // ����ִ�мƻ�(���ĵ�һ��)
        createPlans(bean);

        saveApproveLog(user, id, ModuleConstant.MODULE_EXAMINE, PublicConstant.OPERATION_SUBMIT,
            "�����ƶ����ύȷ��,��������״̬");

        return true;
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
     * ҵ��Աͨ��
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
        return true;
    }

    /**
     * ����ִ�мƻ���ע�ᶨʱ����
     * 
     * @param id
     */
    private void createPlans(ExamineBean bean)
    {
        // �¿ͻ�������
        List<NewCustomerExamineBean> newList = newCustomerExamineDAO.queryEntityBeansByFK(bean.getId());

        for (NewCustomerExamineBean newCustomerExamineBean : newList)
        {
            createNewPlan(newCustomerExamineBean, bean);
        }

        // �Ͽͻ�������
        List<OldCustomerExamineBean> oldList = oldCustomerExamineDAO.queryEntityBeansByFK(bean.getId());

        for (OldCustomerExamineBean item : oldList)
        {
            createOldPlan(item, bean.getType());
        }

        // ������Ŀ���
        List<ProfitExamineBean> pList = profitExamineDAO.queryEntityBeansByFK(bean.getId());

        for (ProfitExamineBean item : pList)
        {
            createProfitPlan(item, bean);
        }

        // ��������ļƻ�
        List<CityProfitExamineBean> cpList = cityProfitExamineDAO.queryEntityBeansByFK(bean.getId());

        for (CityProfitExamineBean item : cpList)
        {
            createCPPlan(item);
        }
    }

    /**
     * ���������ȷ����ʱ20��
     * 
     * @param item
     */
    private void createCPPlan(CityProfitExamineBean item)
    {
        PlanBean plan = new PlanBean();

        plan.setId(commonDAO2.getSquenceString());

        plan.setFkId(item.getId());

        plan.setType(ExamineConstant.EXAMINE_ITEM_TYPE_CPROFIT);

        plan.setCarryType(PlanConstant.PLAN_CARRYTYPES_EVERYDAY);

        plan.setStatus(PlanConstant.PLAN_STATUS_INIT);

        plan.setLogTime(TimeTools.now());

        plan.setBeginTime(item.getBeginTime());

        plan.setEndTime(item.getEndTime());

        plan.setCarryTime(TimeTools.getStringByOrgAndDays(item.getEndTime(), 20));

        plan.setDescription("ϵͳ�Զ����ɵ�����");

        planDAO.saveEntityBean(plan);
    }

    /**
     * ��ʱ20�����ȷ��
     * 
     * @param item
     */
    private void createProfitPlan(ProfitExamineBean item, ExamineBean bean)
    {
        PlanBean plan = new PlanBean();

        plan.setId(commonDAO2.getSquenceString());

        plan.setFkId(item.getId());

        plan.setType(ExamineConstant.EXAMINE_ITEM_TYPE_PROFIT);

        plan.setCarryType(PlanConstant.PLAN_CARRYTYPES_EVERYDAY);

        plan.setStatus(PlanConstant.PLAN_STATUS_INIT);

        plan.setLogTime(TimeTools.now());

        plan.setBeginTime(item.getBeginTime());

        plan.setEndTime(item.getEndTime());

        if (bean.getAttType() == ExamineConstant.EXAMINE_ATTTYPE_PERSONAL)
        {
            // ����ִ��
            plan.setOrderIndex(0);
        }

        if (bean.getAttType() == ExamineConstant.EXAMINE_ATTTYPE_DEPARTMENT)
        {
            // ���ִ��
            plan.setOrderIndex(10);
        }

        if (bean.getAttType() == ExamineConstant.EXAMINE_ATTTYPE_LOCATION)
        {
            // ���ִ��
            plan.setOrderIndex(100);
        }

        plan.setCarryTime(TimeTools.getStringByOrgAndDays(item.getEndTime(), 20));

        plan.setDescription("ϵͳ�Զ����ɵ�����");

        planDAO.saveEntityBean(plan);
    }

    /**
     * �Ͽͻ��Ŀ���ͬ��������5��ȷ��
     * 
     * @param item
     */
    private void createOldPlan(OldCustomerExamineBean item, int type)
    {
        PlanBean plan = new PlanBean();

        plan.setId(commonDAO2.getSquenceString());

        plan.setFkId(item.getId());

        if (type == ExamineConstant.EXAMINE_TYPE_EXPEND)
        {
            plan.setType(ExamineConstant.EXAMINE_ITEM_TYPE_OLDCUSTOMER);
        }
        else
        {
            plan.setType(ExamineConstant.EXAMINE_ITEM_TYPE_TER_OLDCUSTOMER);
        }

        plan.setCarryType(PlanConstant.PLAN_CARRYTYPES_EVERYDAY);

        plan.setStatus(PlanConstant.PLAN_STATUS_INIT);

        plan.setLogTime(TimeTools.now());

        plan.setBeginTime(item.getBeginTime());

        plan.setEndTime(item.getEndTime());

        plan.setCarryTime(TimeTools.getStringByOrgAndDays(item.getEndTime(), 5));

        plan.setDescription("ϵͳ�Զ����ɵ�����");

        planDAO.saveEntityBean(plan);
    }

    /**
     * �¿ͻ������ȷ����ʱ5��
     * 
     * @param newCustomerExamineBean
     */
    private void createNewPlan(NewCustomerExamineBean newCustomerExamineBean, ExamineBean bean)
    {
        PlanBean plan = new PlanBean();

        plan.setId(commonDAO2.getSquenceString());

        plan.setFkId(newCustomerExamineBean.getId());

        if (bean.getType() == ExamineConstant.EXAMINE_TYPE_EXPEND)
        {
            plan.setType(ExamineConstant.EXAMINE_ITEM_TYPE_NEWCUSTOMER);
        }
        else
        {
            plan.setType(ExamineConstant.EXAMINE_ITEM_TYPE_TER_NEWCUSTOMER);
        }

        plan.setCarryType(PlanConstant.PLAN_CARRYTYPES_EVERYDAY);

        plan.setStatus(PlanConstant.PLAN_STATUS_INIT);

        plan.setLogTime(TimeTools.now());

        plan.setBeginTime(newCustomerExamineBean.getBeginTime());

        plan.setEndTime(newCustomerExamineBean.getEndTime());

        plan.setCarryTime(TimeTools.getStringByOrgAndDays(newCustomerExamineBean.getEndTime(), 5));

        plan.setDescription("ϵͳ�Զ����ɵ�����");

        planDAO.saveEntityBean(plan);
    }

    /**
     * ҵ��Ա����(д��־��)
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean rejectBean(User user, String id, String reason)
        throws MYException
    {
        // JudgeTools.judgeParameterIsNull(user, id);
        //
        // checkPassOrReject(user, id);
        //
        // examineDAO.updateStatus(id, ExamineConstant.EXAMINE_STATUS_REJECT);
        //
        // saveApproveLog(user, id, ModuleConstant.MODULE_EXAMINE, PublicConstant.OPERATION_REJECT,
        // reason);

        return true;
    }

    /**
     * ����޸�
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void checkUpdate(User user, ExamineBean bean)
        throws MYException
    {
        ExamineBean old = examineDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("���˲�����,�����²���");
        }

        if (ExamineHelper.isReadonly(old.getStatus()))
        {
            throw new MYException("ֻ�г�ʼ�Ͳ��صĿ����޸�");
        }

        if ( !user.getStafferId().equals(old.getCreaterId()))
        {
            throw new MYException("ֻ�п����ƶ��߿����޸�");
        }

        // ��Ϣ���ܵ�������
        bean.setStafferId(old.getStafferId());

        bean.setAttType(old.getAttType());

        bean.setLocationId(old.getLocationId());

        bean.setYear(old.getYear());
    }

    /**
     * ��Ҫ�жϿ��˵�״̬���Ƿ����ƶ��ߵ�
     * 
     * @param user
     * @param id
     * @throws MYException
     */
    private void checkDel(User user, String id)
        throws MYException
    {
        ExamineBean old = examineDAO.find(id);

        if (old == null)
        {
            throw new MYException("��ȷ�Ͽ����Ƿ����");
        }

        if ( !user.getStafferId().equals(old.getCreaterId()))
        {
            throw new MYException("ֻ�п����ƶ��߿���ɾ��");
        }

        if (ExamineHelper.isReadonly(old.getStatus()))
        {
            throw new MYException("ֻ�г�ʼ�Ͳ��صĿ���ɾ��");
        }

        if (examineDAO.countByFK(old.getId()) > 0)
        {
            throw new MYException("�����ӿ���,����ɾ��");
        }
    }

    /**
     * ��Ҫ�жϿ��˵�״̬���Ƿ����ƶ��ߵ�
     * 
     * @param user
     * @param id
     * @throws MYException
     */
    private void checkDel2(User user, String id)
        throws MYException
    {
        ExamineBean old = examineDAO.find(id);

        if (old == null)
        {
            throw new MYException("��ȷ�Ͽ����Ƿ����");
        }

        if (old.getStatus() != ExamineConstant.EXAMINE_STATUS_PASS)
        {
            throw new MYException("ֻ�����еĿ��˿��Է���");
        }

        if (old.getAttType() != ExamineConstant.EXAMINE_ATTTYPE_PERSONAL)
        {
            throw new MYException("ֻ�и��˵Ŀ��˿��Է���");
        }
    }

    /**
     * checkSubmit
     * 
     * @param user
     * @param id
     * @throws MYException
     */
    private ExamineBean checkSubmit(User user, String id)
        throws MYException
    {
        ExamineBean old = examineDAO.find(id);

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
            throw new MYException("ֻ�г�ʼ�Ͳ��صĿ����ύ");
        }

        if (old.getAbs() == ExamineConstant.EXAMINE_ABS_TRUE)
        {
            throw new MYException("���󿼺˲���Ҫ�ύ");
        }

        List<ProfitExamineBean> pList = profitExamineDAO.queryEntityBeansByFK(id);

        // ֻ����չ����ҵ������
        if (ListTools.isEmptyOrNull(pList) && old.getType() == ExamineConstant.EXAMINE_TYPE_EXPEND)
        {
            throw new MYException("����û���ƶ�ҵ��,�벹��");
        }

        checkPersonal(id, old);

        // ���Ǹ�����
        if (old.getAttType() != ExamineConstant.EXAMINE_ATTTYPE_LOCATION)
        {
            ExamineBean parent = examineDAO.find(old.getParentId());

            if (parent == null)
            {
                throw new MYException("�������˲�����");
            }

            checkProfit(id, old.getParentId());
        }

        return old;
    }

    /**
     * @param id
     * @param old
     * @throws MYException
     */
    private void checkPersonal(String id, ExamineBean old)
        throws MYException
    {
        if (old.getAttType() == ExamineConstant.EXAMINE_ATTTYPE_PERSONAL)
        {
            List<NewCustomerExamineBean> tem = newCustomerExamineDAO.queryEntityBeansByFK(id);

            if (ListTools.isEmptyOrNull(tem))
            {
                throw new MYException("���˿���û���ƶ��¿ͻ�����,�벹��");
            }

            List<OldCustomerExamineBean> tem1 = oldCustomerExamineDAO.queryEntityBeansByFK(id);

            if (ListTools.isEmptyOrNull(tem1))
            {
                throw new MYException("���˿���û���ƶ��Ͽͻ�����,�벹��");
            }
        }
    }

    /**
     * ���ҵ�������Ǵ����Ƿ�Ӹ����ķֽ������
     * 
     * @param id
     * @param rootExamineId
     * @throws MYException
     */
    private void checkProfit(String currentExamineId, String rootExamineId)
        throws MYException
    {
        // ���ڵ㲻���߼��ж�
        if ("0".equals(rootExamineId))
        {
            return;
        }

        // �����ύ�е�ҵ���Ƿ�һ��
        List<ProfitExamineBean> parentList = profitExamineDAO.queryEntityBeansByFK(rootExamineId);

        ConditionParse condition = new ConditionParse();

        condition.addCondition("PARENTID", "=", rootExamineId);

        // ��ѯ�ӿ���
        List<ExamineBean> subList = examineDAO.queryEntityBeansByCondition(condition);

        // ����������,�����Ӧ������(��������Ƿ��·�)
        for (ProfitExamineBean profitExamineBean : parentList)
        {
            double total = profitExamineBean.getPlanValue();

            for (ExamineBean examineBean : subList)
            {
                ProfitExamineBean tem = profitExamineDAO.findByParentAndStep(examineBean.getId(),
                    profitExamineBean.getStep());

                if (tem != null)
                {
                    total = total - tem.getPlanValue();
                }
            }

            ProfitExamineBean cur = profitExamineDAO.findByParentAndStep(currentExamineId,
                profitExamineBean.getStep());

            if (cur == null)
            {
                throw new MYException("ҵ���������ݲ��걸");
            }

            if (total < 0)
            {
                throw new MYException("��ҵ������֮���ڴ���%d�����ڸ�ҵ������", profitExamineBean.getStep());
            }

        }
    }

    /**
     * checkPass
     * 
     * @param user
     * @param id
     * @throws MYException
     */
    public void checkPassOrReject(User user, String id)
        throws MYException
    {
        ExamineBean old = examineDAO.find(id);

        if (old == null)
        {
            throw new MYException("��ȷ�Ͽ����Ƿ����");
        }

        if ( !user.getStafferId().equals(old.getStafferId()))
        {
            throw new MYException("ֻ�п����˿��Բ���");
        }

        if (old.getStatus() != ExamineConstant.EXAMINE_STATUS_SUBMIT)
        {
            throw new MYException("ֻ���ύ�Ŀ��˿��Բ���");
        }
    }

    /**
     * ���add�Ƿ�ɹ�
     * 
     * @param bean
     * @throws MYException
     */
    private void checkAdd(ExamineBean bean)
        throws MYException
    {
        StafferBean sb = stafferDAO.find(bean.getStafferId());

        if (sb == null)
        {
            throw new MYException("��ȷ�Ͽ��˵�ҵ��Ա�Ƿ����");
        }

        if (StringTools.isNullOrNone(bean.getParentId()))
        {
            throw new MYException("��ȷ�Ͽ������ݵ��걸");
        }

        if ( !sb.getLocationId().equals(bean.getLocationId()))
        {
            throw new MYException("���˷ֹ�˾��ְԱ�ֹ�˾��һ��,���ʵ");
        }

        // �ڷֹ�˾��������һ����ָ�����ݿ���
        if (bean.getAttType() != ExamineConstant.EXAMINE_ATTTYPE_LOCATION)
        {
            if (examineDAO.countByUnique(bean.getStafferId(), bean.getYear(), bean.getAttType()) > 0)
            {
                throw new MYException("�����ظ�����ȷ����%s�Ƿ��Ѿ��ƶ�%d��ȿ���", sb.getName(), bean.getYear());
            }
        }
        else
        {
            if (examineDAO.countInLocationType(bean.getStafferId(), bean.getYear(),
                bean.getAttType(), bean.getType()) > 0)
            {
                throw new MYException("�����ظ�2����ȷ����%s�Ƿ��Ѿ��ƶ�%d��ȿ���", sb.getName(), bean.getYear());
            }
        }

        // ���Ǹ�����(ֻ����չ��)
        checkNorRootExpend(bean);
    }

    /**
     * @param bean
     * @throws MYException
     */
    private void checkNorRootExpend(ExamineBean bean)
        throws MYException
    {
        if (bean.getAttType() != ExamineConstant.EXAMINE_ATTTYPE_LOCATION
            && bean.getType() == ExamineConstant.EXAMINE_TYPE_EXPEND)
        {
            ExamineBean parent = examineDAO.find(bean.getParentId());

            if (parent == null)
            {
                throw new MYException("�������˲�����");
            }

            // У�鸸�����˵���ȷ��
            if (bean.getAttType() == ExamineConstant.EXAMINE_ATTTYPE_DEPARTMENT
                && parent.getAttType() != ExamineConstant.EXAMINE_ATTTYPE_LOCATION)
            {
                throw new MYException("�������˲��Ƿֹ�˾������");
            }

            if (bean.getAttType() == ExamineConstant.EXAMINE_ATTTYPE_PERSONAL
                && parent.getAttType() != ExamineConstant.EXAMINE_ATTTYPE_DEPARTMENT)
            {
                throw new MYException("�������˲��ǲ��ž�����");
            }

            if ( !bean.getLocationId().equals(parent.getLocationId()))
            {
                throw new MYException("���ӿ��˲���ͬһ�ֹ�˾");
            }
        }
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

    public NewCustomerExamineDAO getNewCustomerExamineDAO()
    {
        return newCustomerExamineDAO;
    }

    public void setNewCustomerExamineDAO(NewCustomerExamineDAO newCustomerExamineDAO)
    {
        this.newCustomerExamineDAO = newCustomerExamineDAO;
    }

    public OldCustomerExamineDAO getOldCustomerExamineDAO()
    {
        return oldCustomerExamineDAO;
    }

    public void setOldCustomerExamineDAO(OldCustomerExamineDAO oldCustomerExamineDAO)
    {
        this.oldCustomerExamineDAO = oldCustomerExamineDAO;
    }

    public ProfitExamineDAO getProfitExamineDAO()
    {
        return profitExamineDAO;
    }

    public void setProfitExamineDAO(ProfitExamineDAO profitExamineDAO)
    {
        this.profitExamineDAO = profitExamineDAO;
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
}
