/**
 * File Name: FlowManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.manager;


import java.io.Serializable;
import java.util.List;

import net.sourceforge.sannotations.annotation.Bean;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.common.MYException;
import com.china.center.oa.constant.FlowConstant;
import com.china.center.oa.flow.bean.FlowDefineBean;
import com.china.center.oa.flow.bean.FlowTokenBean;
import com.china.center.oa.flow.bean.FlowViewerBean;
import com.china.center.oa.flow.bean.TemplateFileBean;
import com.china.center.oa.flow.dao.FlowDefineDAO;
import com.china.center.oa.flow.dao.FlowInstanceDAO;
import com.china.center.oa.flow.dao.FlowTokenDAO;
import com.china.center.oa.flow.dao.FlowVSTemplateDAO;
import com.china.center.oa.flow.dao.FlowViewerDAO;
import com.china.center.oa.flow.dao.TemplateFileDAO;
import com.china.center.oa.flow.dao.TokenVSHanderDAO;
import com.china.center.oa.flow.dao.TokenVSOperationDAO;
import com.china.center.oa.flow.dao.TokenVSTemplateDAO;
import com.china.center.oa.flow.plugin.HandlerPlugin;
import com.china.center.oa.flow.vo.FlowDefineVO;
import com.china.center.oa.flow.vo.FlowTokenVO;
import com.china.center.oa.flow.vo.FlowViewerVO;
import com.china.center.oa.flow.vo.TokenVSHanderVO;
import com.china.center.oa.flow.vs.FlowVSTemplateBean;
import com.china.center.oa.flow.vs.TokenVSHanderBean;
import com.china.center.oa.flow.vs.TokenVSTemplateBean;
import com.china.center.oa.group.dao.GroupDAO;
import com.china.center.oa.publics.User;
import com.china.center.oa.publics.dao.CommonDAO2;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;


/**
 * FlowManager
 * 
 * @author zhuzhu
 * @version 2009-4-26
 * @see FlowManager
 * @since 1.0
 */
@Exceptional
@Bean(name = "flowManager")
public class FlowManager
{
    private CommonDAO2 commonDAO2 = null;

    private FlowDefineDAO flowDefineDAO = null;

    private TemplateFileDAO templateFileDAO = null;

    private FlowTokenDAO flowTokenDAO = null;

    private FlowViewerDAO flowViewerDAO = null;

    private FlowInstanceDAO flowInstanceDAO = null;

    private TokenVSHanderDAO tokenVSHanderDAO = null;

    private TokenVSTemplateDAO tokenVSTemplateDAO = null;

    private TokenVSOperationDAO tokenVSOperationDAO = null;

    private FlowVSTemplateDAO flowVSTemplateDAO = null;

    private GroupDAO groupDAO = null;

    private PluginManager pluginManager = null;

    private StafferDAO stafferDAO = null;

    /**
     * default constructor
     */
    public FlowManager()
    {}

    /**
     * �������̶���
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean addFlowDefine(User user, FlowDefineBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(bean);

        checkAddBean(bean);

        bean.setId(commonDAO2.getSquenceString20());

        flowDefineDAO.saveEntityBean(bean);

        // �������̺�ģ��Ķ�Ӧ��ϵ
        if (bean.getMode() == FlowConstant.FLOW_MODE_TEMPLATE
            && !ListTools.isEmptyOrNull(bean.getTemplates()))
        {
            List<FlowVSTemplateBean> templates = bean.getTemplates();

            for (FlowVSTemplateBean flowVSTemplateBean : templates)
            {
                flowVSTemplateBean.setFlowId(bean.getId());

                flowVSTemplateDAO.saveEntityBean(flowVSTemplateBean);
            }
        }

        return true;
    }

    /**
     * ���û��ڲ����ύ���̶���
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean configFlowToken(User user, FlowDefineBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(bean);

        checkConfig(bean);

        // ɾ��֮ǰ�Ķ���
        deleteFlowToken(bean.getId());

        // save tokens
        saveTokens(bean.getTokens());

        // release the flow define
        flowDefineDAO.updateStatus(bean.getId(), FlowConstant.FLOW_STATUS_REALSE);

        return true;
    }

    /**
     * configFlowView
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean configFlowView(User user, FlowViewerBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(bean);

        checkConfigView(bean);

        flowViewerDAO.deleteEntityBeansByFK(bean.getFlowId());

        if ( !StringTools.isNullOrNone(bean.getProcesser()))
        {
            flowViewerDAO.saveEntityBean(bean);
        }

        return true;
    }

    /**
     * saveTokens
     * 
     * @param tokens
     * @throws MYException
     */
    private void saveTokens(List<FlowTokenBean> tokens)
        throws MYException
    {
        // config every token
        for (FlowTokenBean flowTokenBean : tokens)
        {
            flowTokenBean.setId(commonDAO2.getSquenceString20());

            // if the token is ending tokensystem need not config other infomation
            if (flowTokenBean.isEnding())
            {
                flowTokenDAO.saveEntityBean(flowTokenBean);

                continue;
            }

            // save real token
            if (flowTokenBean.getType() == FlowConstant.TOKEN_TYPE_REALTOKEN)
            {
                List<TokenVSHanderBean> handlers = flowTokenBean.getHandles();

                for (TokenVSHanderBean tokenVSHanderBean : handlers)
                {
                    tokenVSHanderBean.setTokenId(flowTokenBean.getId());

                    tokenVSHanderDAO.saveEntityBean(tokenVSHanderBean);
                }

                flowTokenBean.getOperation().setTokenId(flowTokenBean.getId());

                tokenVSOperationDAO.saveEntityBean(flowTokenBean.getOperation());

                List<TokenVSTemplateBean> templates = flowTokenBean.getTempaltes();

                for (TokenVSTemplateBean tokenVSTemplateBean : templates)
                {
                    tokenVSTemplateBean.setTokenId(flowTokenBean.getId());

                    tokenVSTemplateDAO.saveEntityBean(tokenVSTemplateBean);
                }
            }
            else
            {
                // check this flow contain subflow template
                checkTemplate(flowTokenBean);
            }

            // save token
            flowTokenDAO.saveEntityBean(flowTokenBean);
        }
    }

    /**
     * checkTemplate
     * 
     * @param flowTokenBean
     * @param subFlowId
     * @throws MYException
     */
    private void checkTemplate(FlowTokenBean flowTokenBean)
        throws MYException
    {
        String subFlowId = flowTokenBean.getSubFlowId();

        FlowDefineBean subFlow = flowDefineDAO.find(subFlowId);

        if (subFlow == null)
        {
            throw new MYException("�����̲�����");
        }

        List<FlowVSTemplateBean> subTemplateList = flowVSTemplateDAO.queryEntityBeansByFK(subFlowId);

        List<FlowVSTemplateBean> parentTemplateList = flowVSTemplateDAO.queryEntityBeansByFK(flowTokenBean.getFlowId());

        for (FlowVSTemplateBean sub : subTemplateList)
        {
            boolean isContain = false;

            for (FlowVSTemplateBean parent : parentTemplateList)
            {
                if (sub.getTemplateId().equals(parent.getTemplateId()))
                {
                    isContain = true;

                    break;
                }
            }

            if ( !isContain)
            {
                TemplateFileBean template = templateFileDAO.find(sub.getTemplateId());

                if (template != null)
                {
                    throw new MYException("������ȱ������ģ��:" + template.getName());
                }
            }
        }
    }

    /**
     * updateFlowDefine
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateFlowDefine(User user, FlowDefineBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(bean);

        checkUpdate(bean);

        flowDefineDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * ɾ������
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean delFlowDefine(User user, Serializable flowId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, flowId);

        checkDel(user, flowId);

        flowDefineDAO.deleteEntityBean(flowId);

        // delete token
        deleteFlowToken(flowId);

        // delete views
        flowViewerDAO.deleteEntityBeansByFK(flowId);

        // delete template
        flowVSTemplateDAO.deleteEntityBeansByFK(flowId);

        return true;
    }

    /**
     * ��������
     * 
     * @param bean
     * @param flowId
     * @param forceDrop
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean dropFlowDefine(User user, Serializable flowId, boolean forceDrop)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, flowId);

        checkDrop(user, flowId, forceDrop);

        flowDefineDAO.updateStatus(flowId, FlowConstant.FLOW_STATUS_DROP);

        return true;
    }

    /**
     * findFlowDefine
     * 
     * @param id
     * @return
     * @throws MYException
     */
    public FlowDefineVO findFlowDefine(Serializable id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        FlowDefineVO bean = flowDefineDAO.findVO(id);

        if (bean == null)
        {
            return null;
        }

        bean.setTokenVOs(flowTokenDAO.queryEntityVOsByFK(id));

        bean.setTokens(flowTokenDAO.queryEntityBeansByFK(id));

        bean.setTemplates(flowVSTemplateDAO.queryEntityBeansByFK(id));

        bean.setViews(flowViewerDAO.queryEntityBeansByFK(id));

        List<FlowTokenVO> tokenVOs = bean.getTokenVOs();

        // get detail token
        for (FlowTokenVO flowTokenBean : tokenVOs)
        {
            flowTokenBean.setHandleVOs(tokenVSHanderDAO.queryEntityVOsByFK(flowTokenBean.getId()));

            getHandles(flowTokenBean.getHandleVOs());

            flowTokenBean.setTempalteVOs(tokenVSTemplateDAO.queryEntityVOsByFK(flowTokenBean.getId()));

            flowTokenBean.setOperation(tokenVSOperationDAO.findByUnique(flowTokenBean.getId()));
        }

        return bean;
    }

    public FlowViewerVO findFlowViewer(Serializable id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        FlowViewerVO bean = flowViewerDAO.findVO(id);

        if (bean == null)
        {
            return null;
        }

        getViewVO(bean);

        return bean;
    }

    /**
     * delFlowToken
     * 
     * @param user
     * @param flowId
     * @return
     * @throws MYException
     */
    private boolean deleteFlowToken(Serializable flowId)
        throws MYException
    {
        List<FlowTokenBean> tokens = flowTokenDAO.queryEntityBeansByFK(flowId);

        for (FlowTokenBean flowTokenBean : tokens)
        {
            // delete token
            flowTokenDAO.deleteEntityBean(flowTokenBean.getId());

            // delete handle
            tokenVSHanderDAO.deleteEntityBeansByFK(flowTokenBean.getId());

            // delete operation
            tokenVSOperationDAO.deleteEntityBeansByFK(flowTokenBean.getId());

            // delete template
            tokenVSTemplateDAO.deleteEntityBeansByFK(flowTokenBean.getId());
        }

        return true;
    }

    /**
     * @param id
     * @throws MYException
     */
    private void checkDel(User user, Serializable id)
        throws MYException
    {
        FlowDefineBean old = flowDefineDAO.find(id);

        if (old == null)
        {
            throw new MYException("���̶��岻����");
        }

        if ( ! (old.getStatus() == FlowConstant.FLOW_STATUS_INIT || old.getStatus() == FlowConstant.FLOW_STATUS_DROP))
        {
            throw new MYException("ֻ�г�ʼ�ͷ��������̿���ɾ��");
        }

        // zhuzhu ������û��ʵ��
        if (flowInstanceDAO.countByFK(id) > 0)
        {
            throw new MYException("���̶����´�������ʵ��,����ɾ��");
        }
    }

    /**
     * checkDrop
     * 
     * @param user
     * @param id
     * @throws MYException
     */
    private void checkDrop(User user, Serializable id, boolean forceDrop)
        throws MYException
    {
        FlowDefineBean old = flowDefineDAO.find(id);

        if (old == null)
        {
            throw new MYException("���̶��岻����");
        }

        if (old.getStatus() != FlowConstant.FLOW_STATUS_REALSE)
        {
            throw new MYException("ֻ�з��������̿��Է���");
        }

        // ZHUZHU ������û��ʵ��
        if ( !forceDrop && flowInstanceDAO.countProcessInstanceByFlowId(id) > 0)
        {
            throw new MYException("���̶����´���δ��ɵ�����ʵ��,���ܷ���");
        }
    }

    /**
     * @param bean
     * @throws MYException
     */
    private void checkAddBean(FlowDefineBean bean)
        throws MYException
    {
        if (flowDefineDAO.countByUnique(bean.getName()) > 0)
        {
            throw new MYException("���������Ѿ�����");
        }

        if (bean.getMode() == FlowConstant.FLOW_MODE_TEMPLATE
            && ListTools.isEmptyOrNull(bean.getTemplates()))
        {
            throw new MYException("��������ģ��ģʽ��,����ʱû��ѡ��ģ��");
        }
    }

    /**
     * checkConfig
     * 
     * @param bean
     * @throws MYException
     */
    private void checkConfig(FlowDefineBean bean)
        throws MYException
    {
        FlowDefineBean old = flowDefineDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("���̲�����");
        }

        if (old.getStatus() != FlowConstant.FLOW_STATUS_INIT)
        {
            throw new MYException("ֻ�г�ʼ�����̿������û���");
        }

        if (ListTools.isEmptyOrNull(bean.getTokens()))
        {
            throw new MYException("û�����û���");
        }
    }

    /**
     * checkConfigView
     * 
     * @param bean
     * @throws MYException
     */
    private void checkConfigView(FlowViewerBean bean)
        throws MYException
    {

    }

    /**
     * checkUpdate
     * 
     * @param bean
     * @throws MYException
     */
    private void checkUpdate(FlowDefineBean bean)
        throws MYException
    {
        FlowDefineBean old = flowDefineDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("���̲�����");
        }

        if ( !old.getName().equals(bean.getName()))
        {
            if (flowDefineDAO.countByUnique(bean.getName()) > 0)
            {
                throw new MYException("��������[%s]�Ѿ�����", bean.getName());
            }
        }

        if (old.getStatus() != FlowConstant.FLOW_STATUS_INIT)
        {
            throw new MYException("ֻ�г�ʼ�����̿����޸�");
        }

        // can not change mode
        bean.setMode(old.getMode());

        // can not change status
        bean.setStatus(old.getStatus());
    }

    /**
     * getHandles
     */
    private void getHandles(List<TokenVSHanderVO> handles)
        throws MYException
    {
        for (TokenVSHanderVO tokenVSHanderVO : handles)
        {
            HandlerPlugin plugin = pluginManager.getHandlerPlugin(tokenVSHanderVO.getType());

            if (plugin == null)
            {
                throw new MYException("û�м���ָ���Ĳ��");
            }

            tokenVSHanderVO.setProcessName(plugin.getHandleName(tokenVSHanderVO.getProcesser()));

            tokenVSHanderVO.setProcessType(plugin.getTypeName());
        }
    }

    /**
     * getViewVO
     * 
     * @param view
     * @throws MYException
     */
    private void getViewVO(FlowViewerVO view)
        throws MYException
    {
        HandlerPlugin plugin = pluginManager.getHandlerPlugin(view.getType());

        if (plugin == null)
        {
            throw new MYException("û�м���ָ���Ĳ��");
        }

        String name = plugin.getHandleName(view.getProcesser());

        view.setProcesserName(name);

        view.setProcesserType(plugin.getTypeName());
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
     * @return the flowDefineDAO
     */
    public FlowDefineDAO getFlowDefineDAO()
    {
        return flowDefineDAO;
    }

    /**
     * @param flowDefineDAO
     *            the flowDefineDAO to set
     */
    public void setFlowDefineDAO(FlowDefineDAO flowDefineDAO)
    {
        this.flowDefineDAO = flowDefineDAO;
    }

    /**
     * @return the templateFileDAO
     */
    public TemplateFileDAO getTemplateFileDAO()
    {
        return templateFileDAO;
    }

    /**
     * @param templateFileDAO
     *            the templateFileDAO to set
     */
    public void setTemplateFileDAO(TemplateFileDAO templateFileDAO)
    {
        this.templateFileDAO = templateFileDAO;
    }

    /**
     * @return the flowVSTemplateDAO
     */
    public FlowVSTemplateDAO getFlowVSTemplateDAO()
    {
        return flowVSTemplateDAO;
    }

    /**
     * @param flowVSTemplateDAO
     *            the flowVSTemplateDAO to set
     */
    public void setFlowVSTemplateDAO(FlowVSTemplateDAO flowVSTemplateDAO)
    {
        this.flowVSTemplateDAO = flowVSTemplateDAO;
    }

    /**
     * @return the flowTokenDAO
     */
    public FlowTokenDAO getFlowTokenDAO()
    {
        return flowTokenDAO;
    }

    /**
     * @param flowTokenDAO
     *            the flowTokenDAO to set
     */
    public void setFlowTokenDAO(FlowTokenDAO flowTokenDAO)
    {
        this.flowTokenDAO = flowTokenDAO;
    }

    /**
     * @return the flowViewerDAO
     */
    public FlowViewerDAO getFlowViewerDAO()
    {
        return flowViewerDAO;
    }

    /**
     * @param flowViewerDAO
     *            the flowViewerDAO to set
     */
    public void setFlowViewerDAO(FlowViewerDAO flowViewerDAO)
    {
        this.flowViewerDAO = flowViewerDAO;
    }

    /**
     * @return the tokenVSHanderDAO
     */
    public TokenVSHanderDAO getTokenVSHanderDAO()
    {
        return tokenVSHanderDAO;
    }

    /**
     * @param tokenVSHanderDAO
     *            the tokenVSHanderDAO to set
     */
    public void setTokenVSHanderDAO(TokenVSHanderDAO tokenVSHanderDAO)
    {
        this.tokenVSHanderDAO = tokenVSHanderDAO;
    }

    /**
     * @return the tokenVSTemplateDAO
     */
    public TokenVSTemplateDAO getTokenVSTemplateDAO()
    {
        return tokenVSTemplateDAO;
    }

    /**
     * @param tokenVSTemplateDAO
     *            the tokenVSTemplateDAO to set
     */
    public void setTokenVSTemplateDAO(TokenVSTemplateDAO tokenVSTemplateDAO)
    {
        this.tokenVSTemplateDAO = tokenVSTemplateDAO;
    }

    /**
     * @return the tokenVSOperationDAO
     */
    public TokenVSOperationDAO getTokenVSOperationDAO()
    {
        return tokenVSOperationDAO;
    }

    /**
     * @param tokenVSOperationDAO
     *            the tokenVSOperationDAO to set
     */
    public void setTokenVSOperationDAO(TokenVSOperationDAO tokenVSOperationDAO)
    {
        this.tokenVSOperationDAO = tokenVSOperationDAO;
    }

    /**
     * @return the flowInstanceDAO
     */
    public FlowInstanceDAO getFlowInstanceDAO()
    {
        return flowInstanceDAO;
    }

    /**
     * @param flowInstanceDAO
     *            the flowInstanceDAO to set
     */
    public void setFlowInstanceDAO(FlowInstanceDAO flowInstanceDAO)
    {
        this.flowInstanceDAO = flowInstanceDAO;
    }

    /**
     * @return the groupDAO
     */
    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    /**
     * @param groupDAO
     *            the groupDAO to set
     */
    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
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
     * @return the pluginManager
     */
    public PluginManager getPluginManager()
    {
        return pluginManager;
    }

    /**
     * @param pluginManager
     *            the pluginManager to set
     */
    public void setPluginManager(PluginManager pluginManager)
    {
        this.pluginManager = pluginManager;
    }
}
