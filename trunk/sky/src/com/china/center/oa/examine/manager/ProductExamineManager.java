/**
 * File Name: ProductExamineManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-2-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.examine.manager;

import java.util.ArrayList;
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
import com.china.center.oa.examine.bean.CitySailBean;
import com.china.center.oa.examine.bean.ExamineBean;
import com.china.center.oa.examine.bean.ProductCityExamineItemBean;
import com.china.center.oa.examine.bean.ProductExamineBean;
import com.china.center.oa.examine.bean.ProductExamineItemBean;
import com.china.center.oa.examine.dao.CityConfigDAO;
import com.china.center.oa.examine.dao.CityProfitExamineDAO;
import com.china.center.oa.examine.dao.CitySailDAO;
import com.china.center.oa.examine.dao.ExamineDAO;
import com.china.center.oa.examine.dao.ProductCityExamineItemDAO;
import com.china.center.oa.examine.dao.ProductExamineDAO;
import com.china.center.oa.examine.helper.ExamineHelper;
import com.china.center.oa.examine.vo.CityConfigVO;
import com.china.center.oa.plan.bean.PlanBean;
import com.china.center.oa.plan.dao.PlanDAO;
import com.china.center.oa.publics.User;
import com.china.center.oa.publics.bean.LogBean;
import com.china.center.oa.publics.dao.CommonDAO2;
import com.china.center.oa.publics.dao.LogDAO;
import com.china.center.oa.tools.OATools;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
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
    
    private CommonDAO2 commonDAO2 = null;
    
    private CityProfitExamineDAO cityProfitExamineDAO = null;
    
    private ExamineDAO examineDAO = null;
    
    private CityConfigDAO cityConfigDAO = null;
    
    private CitySailDAO citySailDAO = null;
    
    private ProductCityExamineItemDAO productCityExamineItemDAO = null;
    
    private LogDAO logDAO = null;
    
    private PlanDAO planDAO = null;
    
    public ProductExamineManager()
    {
    }
    
    /**
     * ���Ӳ�Ʒ����
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = { MYException.class })
    public boolean addBean(User user, ProductExamineBean bean,
            String[] stafferIdList) throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);
        
        checkAdd(user, bean, stafferIdList);
        
        bean.setId(commonDAO2.getSquenceString());
        
        bean.setStatus(ExamineConstant.EXAMINE_STATUS_INIT);
        
        bean.setLogTime(TimeTools.now());
        
        productExamineDAO.saveEntityBean(bean);
        
        // ����Ĭ�ϵĿ�����
        for (ProductCityExamineItemBean item : bean.getItems())
        {
            item.setId(commonDAO2.getSquenceString());
            
            item.setPid(bean.getId());
            
            productCityExamineItemDAO.saveEntityBean(item);
        }
        
        return true;
    }
    
    /**
     * delBean
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = { MYException.class })
    public boolean delBean(User user, String id) throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);
        
        checkDel(id);
        
        productExamineDAO.deleteEntityBean(id);
        
        productCityExamineItemDAO.deleteEntityBeansByFK(id);
        
        return true;
    }
    
    /**
     * �������еĿ���״̬
     * 
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = { MYException.class })
    public int updateAllStatus()
    {
        // �Ȳ�ѯ������ʱ��С�ڽ���Ĳ�Ʒ����
        ConditionParse condition = new ConditionParse();
        
        condition.addCondition("endTime", "<=", TimeTools.now());
        
        // �����е�
        condition.addCondition("status",
                "=",
                ExamineConstant.EXAMINE_STATUS_PASS);
        
        List<ProductExamineBean> list = productExamineDAO.queryEntityBeansByCondition(condition);
        
        int total = 0;
        
        // ѭ������
        for (ProductExamineBean productExamineBean : list)
        {
            // ���������Ƿ񶼽�����
            List<ProductCityExamineItemBean> temp = productCityExamineItemDAO.queryEntityBeansByFK(productExamineBean.getId());
            
            boolean isEnd = true;
            
            for (ProductCityExamineItemBean productExamineItemBean : temp)
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
                
                total++;
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
    @Transactional(rollbackFor = { MYException.class })
    public boolean configProductExamine(User user, String parentId,
            List<ProductExamineItemBean> items) throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, items);
        
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
    @Transactional(rollbackFor = { MYException.class })
    public boolean submitBean(User user, String id) throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);
        
        checkSubmit(user, id);
        
        // ���¿��˵�����
        productExamineDAO.updateStatus(id, ExamineConstant.EXAMINE_STATUS_PASS);
        
        // ����ִ�мƻ�(���ĵ�һ��)
        createPlans(id);
        
        saveApproveLog(user,
                id,
                ModuleConstant.MODULE_EXAMINE,
                PublicConstant.OPERATION_SUBMIT,
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
        
        List<ProductCityExamineItemBean> pList = productCityExamineItemDAO.queryEntityBeansByFK(id);
        
        for (ProductCityExamineItemBean item : pList)
        {
            createProductPlan(item, bean);
        }
    }
    
    /**
     * ��Ʒ���˵����ȷ����ʱ1��
     * 
     * @param item
     */
    private void createProductPlan(ProductCityExamineItemBean item,
            ProductExamineBean bean)
    {
        PlanBean plan = new PlanBean();
        
        plan.setId(commonDAO2.getSquenceString());
        
        plan.setFkId(item.getId());
        
        plan.setType(ExamineConstant.EXAMINE_ITEM_TYPE_PRODUCT2);
        
        plan.setCarryType(PlanConstant.PLAN_CARRYTYPES_EVERYDAY);
        
        plan.setStatus(PlanConstant.PLAN_STATUS_INIT);
        
        plan.setLogTime(TimeTools.now());
        
        plan.setBeginTime(bean.getBeginTime());
        
        plan.setEndTime(bean.getEndTime());
        
        plan.setCarryTime(TimeTools.getStringByOrgAndDays(bean.getEndTime(), 1));
        
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
    private void checkSubmit(User user, String id) throws MYException
    {
        ProductExamineBean old = productExamineDAO.find(id);
        
        if (old == null)
        {
            throw new MYException("��ȷ�Ͽ����Ƿ����");
        }
        
        if (!user.getStafferId().equals(old.getCreaterId()))
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
    private void checkAdd(User user, ProductExamineBean bean,
            String[] stafferIdList) throws MYException
    {
        List<ProductCityExamineItemBean> itemList = new ArrayList();
        
        bean.setItems(itemList);
        
        //��ȡ��ǰ�Ĳ������
        int financeYear = OATools.getFinanceYear();
        
        //ѭ�����еĿ���ְԱ
        for (String stafferId : stafferIdList)
        {
            ExamineBean examineBean = examineDAO.findByStafferIdAndYear(stafferId,
                    financeYear);
            
            if (examineBean == null)
            {
                throw new MYException("��ǰϵͳ��û��ְԱ[%s]�ĸ��˿���,���߿���û����׼",
                        user.getStafferName());
            }
            
            //��ѯ�Ƿ���ڵ�ǰ������ȵ����򿼺�ָ��(����ָ���ڸ��˿�������)
            List<String> list = cityProfitExamineDAO.queryDistinctCityByparentId(examineBean.getId());
            
            if (ListTools.isEmptyOrNull(list))
            {
                throw new MYException("��ǰϵͳ��ְԱ[%s]�ĸ��˿���û��ѡ������",
                        user.getStafferName());
            }
            
            for (String cityId : list)
            {
                CityConfigVO cityConfig = cityConfigDAO.findVO(cityId);
                
                if (cityConfig == null)
                {
                    throw new MYException("����ȱ������");
                }
                
                if (StringTools.isNullOrNone(cityConfig.getBespread()))
                {
                    throw new MYException("����[%s]ȱ����������",
                            cityConfig.getCityName());
                }
                
                CitySailBean citySail = citySailDAO.find(cityConfig.getBespread());
                
                if (citySail == null)
                {
                    throw new MYException("����[%d]����������",
                            cityConfig.getCityName());
                }
                
                ProductCityExamineItemBean item = new ProductCityExamineItemBean();
                
                item.setCityId(cityId);
                
                item.setStafferId(stafferId);
                
                item.setProductId(bean.getProductId());
                
                item.setBeginTime(bean.getBeginTime());
                
                item.setEndTime(bean.getEndTime());
                
                item.setPlanValue(citySail.getAmount() / citySail.getMonth()
                        * bean.getMonth());
                
                itemList.add(item);
            }
        }
    }
    
    /**
     * checkDel
     * 
     * @param id
     * @throws MYException
     */
    private void checkDel(String id) throws MYException
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
     * д���ݿ���־
     * 
     * @param user
     * @param id
     */
    private void saveApproveLog(User user, String id, String module,
            String operation, String logs)
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
    
    public CityProfitExamineDAO getCityProfitExamineDAO()
    {
        return cityProfitExamineDAO;
    }
    
    public void setCityProfitExamineDAO(
            CityProfitExamineDAO cityProfitExamineDAO)
    {
        this.cityProfitExamineDAO = cityProfitExamineDAO;
    }
    
    public ExamineDAO getExamineDAO()
    {
        return examineDAO;
    }
    
    public void setExamineDAO(ExamineDAO examineDAO)
    {
        this.examineDAO = examineDAO;
    }
    
    public ProductCityExamineItemDAO getProductCityExamineItemDAO()
    {
        return productCityExamineItemDAO;
    }
    
    public void setProductCityExamineItemDAO(
            ProductCityExamineItemDAO productCityExamineItemDAO)
    {
        this.productCityExamineItemDAO = productCityExamineItemDAO;
    }
    
    public CityConfigDAO getCityConfigDAO()
    {
        return cityConfigDAO;
    }
    
    public void setCityConfigDAO(CityConfigDAO cityConfigDAO)
    {
        this.cityConfigDAO = cityConfigDAO;
    }
    
    public CitySailDAO getCitySailDAO()
    {
        return citySailDAO;
    }
    
    public void setCitySailDAO(CitySailDAO citySailDAO)
    {
        this.citySailDAO = citySailDAO;
    }
}
