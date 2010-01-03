/**
 *
 */
package com.china.centet.yongyin.manager;


import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.common.MYException;
import com.china.center.tools.CommonTools;
import com.china.center.tools.FileTools;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.centet.yongyin.bean.FlowDefineBean;
import com.china.centet.yongyin.bean.FlowInstanceBean;
import com.china.centet.yongyin.bean.FlowInstanceLogBean;
import com.china.centet.yongyin.bean.FlowTokenBean;
import com.china.centet.yongyin.bean.FlowViewerBean;
import com.china.centet.yongyin.bean.User;
import com.china.centet.yongyin.constant.Constant;
import com.china.centet.yongyin.constant.FlowConstant;
import com.china.centet.yongyin.dao.CommonDAO;
import com.china.centet.yongyin.dao.FlowBelongDAO;
import com.china.centet.yongyin.dao.FlowConsignDAO;
import com.china.centet.yongyin.dao.FlowDefineDAO;
import com.china.centet.yongyin.dao.FlowInstanceDAO;
import com.china.centet.yongyin.dao.FlowInstanceLogDAO;
import com.china.centet.yongyin.dao.FlowInstanceViewDAO;
import com.china.centet.yongyin.dao.FlowTokenDAO;
import com.china.centet.yongyin.dao.FlowViewerDAO;
import com.china.centet.yongyin.dao.StafferDAO;
import com.china.centet.yongyin.dao.UserDAO;
import com.china.centet.yongyin.vo.FlowDefineBeanVO;
import com.china.centet.yongyin.vo.FlowInstanceBeanVO;
import com.china.centet.yongyin.vo.FlowInstanceLogBeanVO;
import com.china.centet.yongyin.vo.FlowTokenBeanVO;
import com.china.centet.yongyin.vo.FlowViewerBeanVO;
import com.china.centet.yongyin.wokflow.plugin.CommonPlugin;
import com.china.centet.yongyin.wokflow.plugin.impl.AllUserPlugin;
import com.china.centet.yongyin.wokflow.plugin.impl.RolePlugin;
import com.china.centet.yongyin.wokflow.plugin.impl.StafferPlugin;
import com.china.centet.yongyin.wokflow.plugin.impl.UserPlugin;


/**
 * @author Administrator
 */
public class WorkFlowManager
{
    private FlowDefineDAO flowDefineDAO = null;

    private FlowInstanceDAO flowInstanceDAO = null;

    private FlowInstanceLogDAO flowInstanceLogDAO = null;

    private FlowTokenDAO flowTokenDAO = null;

    private FlowConsignDAO flowConsignDAO = null;

    private CommonDAO commonDAO = null;

    private FlowBelongDAO flowBelongDAO = null;

    private AllUserPlugin allUserPlugin = null;

    private RolePlugin rolePlugin = null;

    private UserPlugin userPlugin = null;

    private StafferPlugin stafferPlugin = null;

    private UserDAO userDAO = null;

    private String attchmentPath = "";

    private StafferDAO stafferDAO = null;

    private FlowInstanceViewDAO flowInstanceViewDAO = null;

    private FlowViewerDAO flowViewerDAO = null;

    /**
     *
     */
    public WorkFlowManager()
    {}

    /**
     * �������̶���
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean addFlowDefine(FlowDefineBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(bean);

        checkAddBean(bean);

        bean.setId(commonDAO.getSquenceString());

        List<FlowTokenBean> tokens = bean.getTokens();

        flowDefineDAO.saveEntityBean(bean);

        // �������̵Ļ���
        for (FlowTokenBean flowTokenBean : tokens)
        {
            flowTokenBean.setFlowId(bean.getId());

            flowTokenDAO.saveEntityBean(flowTokenBean);
        }

        return true;
    }

    /**
     * �������̲���
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean processFlowViewer(User user, String flowId, List<FlowViewerBean> views)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, flowId, views);

        flowViewerDAO.deleteEntityBeansByFK(flowId);

        for (FlowViewerBean flowInstanceViewerBean : views)
        {
            flowInstanceViewerBean.setFlowId(flowId);

            flowViewerDAO.saveEntityBean(flowInstanceViewerBean);
        }

        return true;
    }

    /**
     * @param bean
     * @throws MYException
     */
    private void checkAddBean(FlowDefineBean bean)
        throws MYException
    {
        if (flowDefineDAO.countName(bean.getName()) > 0)
        {
            throw new MYException("�����Ѿ�����");
        }
    }

    /**
     * �������̶���
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    @Exceptional
    public boolean addFlowInstance(User user, FlowInstanceBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        bean.setId(SequenceTools.getSequence("WF_", 5));

        bean.setStatus(FlowConstant.FLOW_INSTANCE_BEGIN);

        flowInstanceDAO.saveEntityBean(bean);

        // ��ʼ����
        int status = beginFlowInstance(user, bean);

        flowInstanceDAO.updateStatus(bean.getId(), status);

        return true;
    }

    /**
     * �޸�����ʵ��
     * 
     * @param user
     * @param bean
     * @param attchmentPath
     *            ����Ŀ¼
     * @param delAttachment
     *            �Ƿ�ָ��ɾ������
     * @return boolean
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    @Exceptional(sensitiveException = {RuntimeException.class, IOException.class})
    public boolean updateFlowInstance(User user, FlowInstanceBean bean, boolean delAttachment)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        FlowInstanceBean oldBean = flowInstanceDAO.find(bean.getId());

        if (oldBean == null)
        {
            throw new MYException("����ʵ��������");
        }

        if (oldBean.getStatus() != FlowConstant.FLOW_INSTANCE_BEGIN)
        {
            throw new MYException("�����޸�����ʵ��");
        }

        bean.setStatus(FlowConstant.FLOW_INSTANCE_BEGIN);

        // �̳�ԭ�еĸ���
        boolean hasAccachment = !StringTools.isNullOrNone(bean.getAttachment());

        flowInstanceDAO.updateEntityBean(bean);

        // ��ʼ����
        int status = beginFlowInstance(user, bean);

        flowInstanceDAO.updateStatus(bean.getId(), status);

        // ֤���µĸ��� ɾ��ԭ�еĸ���
        if ( !StringTools.isNullOrNone(oldBean.getAttachment()))
        {
            if (delAttachment || hasAccachment)
            {
                File file = new File(FileTools.formatPath2(this.attchmentPath)
                                     + oldBean.getAttachment());

                file.delete();
            }
        }

        return true;
    }

    /**
     * �޸�����ʵ���ĸ���
     * 
     * @param user
     * @param instanceId
     * @param attachment
     *            �µĸ���
     * @param delAttachment
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    @Exceptional(sensitiveException = {RuntimeException.class, IOException.class})
    public boolean updateFlowInstanceAttachment(User user, String instanceId, String fileName,
                                                String attachment, boolean delAttachment)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, instanceId);

        FlowInstanceBean oldBean = flowInstanceDAO.find(instanceId);

        if (oldBean == null)
        {
            throw new MYException("����ʵ��������");
        }

        // �̳�ԭ�еĸ���
        boolean hasAccachment = !StringTools.isNullOrNone(attachment);

        String oldAttachment = oldBean.getAttachment();

        oldBean.setAttachment(attachment);

        oldBean.setFileName(fileName);

        // ֤���µĸ��� ɾ��ԭ�еĸ���
        if ( !StringTools.isNullOrNone(oldBean.getAttachment()))
        {
            if (delAttachment || hasAccachment)
            {
                File file = new File(FileTools.formatPath2(this.attchmentPath) + oldAttachment);

                file.delete();

                flowInstanceDAO.updateEntityBean(oldBean);
            }
        }

        return true;
    }

    /**
     * ��ʼ����
     * 
     * @param bean
     */
    private int beginFlowInstance(User user, FlowInstanceBean bean)
        throws MYException
    {
        return nextFlowInstance("", user, bean);
    }

    /**
     * ��ʼ������һ������
     * 
     * @param bean
     */
    private int nextFlowInstance(String option, User user, FlowInstanceBean bean)
        throws MYException
    {
        FlowTokenBean token = flowTokenDAO.findToken(bean.getFlowId(), bean.getStatus());

        if (token == null)
        {
            throw new MYException("����û�е�ǰ����");
        }

        if (token.isEnding())
        {
            throw new MYException("����ʵ���Ѿ�����");
        }

        hasAuth(user, token, bean.getId(), bean.getLocationId());

        FlowTokenBean next = flowTokenDAO.findToken(bean.getFlowId(), token.getNextOrders());

        if (next == null)
        {
            throw new MYException("��һ����Ϊ��,��ȷ�ϲ���");
        }

        if ( !next.isEnding())
        {
            processBelong(bean.getId(), next);
        }
        else
        {
            // ���̽����Ĵ���
            flowBelongDAO.deleteEntityBeansByFK(bean.getId());

            // ������ĵ���Ա
            processViewer(bean.getId(), bean.getFlowId());
        }

        addFlowLog(option, Constant.OPRMODE_PASS, token.getId(), next.getId(), bean, user);

        return next.getOrders();
    }

    /**
     * ͨ������ʵ��
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean passFlowInstance(User user, String instanceId, String opinion)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, instanceId);

        FlowInstanceBean bean = flowInstanceDAO.find(instanceId);

        if (bean == null)
        {
            throw new MYException("����ʵ��������");
        }

        // ��һ������
        int status = nextFlowInstance(opinion, user, bean);

        flowInstanceDAO.updateStatus(bean.getId(), status);

        return true;
    }

    /**
     * ͨ������ʵ��
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    @Exceptional
    public boolean rejectFlowInstance(User user, String instanceId, String opinion)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, instanceId);

        FlowInstanceBean bean = flowInstanceDAO.find(instanceId);

        if (bean == null)
        {
            throw new MYException("����ʵ��������");
        }

        // ���ص��������
        int status = rejectFlowInstanceInner(opinion, user, bean);

        flowInstanceDAO.updateStatus(bean.getId(), status);

        return true;
    }

    /**
     * �������̵��������
     * 
     * @param bean
     */
    private int rejectFlowInstanceInner(String option, User user, FlowInstanceBean bean)
        throws MYException
    {
        FlowTokenBean token = flowTokenDAO.findToken(bean.getFlowId(), bean.getStatus());

        if (token == null)
        {
            throw new MYException("����û�п�ʼ����");
        }

        hasAuth(user, token, bean.getId(), bean.getLocationId());

        FlowTokenBean next = flowTokenDAO.findBeginToken(bean.getFlowId());

        if (next == null)
        {
            throw new MYException("��һ����Ϊ��,��ȷ�ϲ���");
        }

        // ɾ�����е����̹���
        flowBelongDAO.deleteEntityBeansByFK(bean.getId());

        addFlowLog(option, Constant.OPRMODE_REJECT, token.getId(), next.getId(), bean, user);

        return next.getOrders();
    }

    /**
     * �Ƿ���Ȩ�޲���������
     * 
     * @param flowId
     * @param user
     * @return
     */
    public boolean hasApplyFlow(String flowId, User user)
    {
        FlowTokenBean bean = flowTokenDAO.findBeginToken(flowId);

        if (bean == null)
        {
            return false;
        }

        if (bean.getType() == FlowConstant.FLOW_TYPE_ALL)
        {
            return true;
        }

        if (bean.getType() == FlowConstant.FLOW_TYPE_USER)
        {
            return user.getId().equals(bean.getProcesser());
        }

        if (bean.getType() == FlowConstant.FLOW_TYPE_ROLE)
        {
            return user.getType() == CommonTools.parseInt(bean.getProcesser());
        }

        return false;
    }

    /**
     * �Ƿ��в���Ȩ��
     * 
     * @param user
     * @param token
     * @throws MYException
     */
    private void hasAuth(User user, FlowTokenBean token, String instanceId, String locationId)
        throws MYException
    {
        if (token.getType() == FlowConstant.FLOW_TYPE_ALL)
        {
            return;
        }

        // ί�������ߵ��ж�����
        if (flowConsignDAO.hasConsign(instanceId, token.getId(), user.getId()))
        {
            return;
        }

        // ��ɫֻ���������µĽ�ɫ�������
        if (token.getType() == FlowConstant.FLOW_TYPE_ROLE)
        {
            if (CommonTools.parseInt(token.getProcesser()) == Constant.ROLE_TOP)
            {
                if ( !token.getProcesser().equals(String.valueOf(user.getType())))
                {
                    throw new MYException("û��Ȩ�޲���");
                }
            }
            else
            {
                if ( !token.getProcesser().equals(String.valueOf(user.getType()))
                    || !user.getLocationID().equals(locationId))
                {
                    throw new MYException("û��Ȩ�޲���");
                }
            }
        }

        // ��Ա�ǿ������
        if (token.getType() == FlowConstant.FLOW_TYPE_USER)
        {
            if ( !token.getProcesser().equals(user.getId()))
            {
                throw new MYException("û��Ȩ�޲���");
            }
        }
    }

    /**
     * ������̶���
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    @Exceptional
    public boolean updateFlowDefine(FlowDefineBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(bean);

        FlowDefineBean old = flowDefineDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("����ʵ��������");
        }

        if ( !old.getName().equals(bean.getName()) && flowDefineDAO.countName(bean.getName()) > 0)
        {
            throw new MYException("���������Ѿ�����");
        }

        flowDefineDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * �޸����̻���
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    @Exceptional
    public boolean updateFlowToken(FlowDefineBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(bean);

        FlowDefineBean old = flowDefineDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("����ʵ��������");
        }

        if (flowInstanceDAO.countNotEndInstance(bean.getId()) > 0)
        {
            throw new MYException("�����´���û�н�����ʵ��,�����޸Ļ���");
        }

        List<FlowTokenBean> tokens = bean.getTokens();

        flowTokenDAO.deleteEntityBeansByFK(bean.getId());

        for (FlowTokenBean flowTokenBean : tokens)
        {
            flowTokenDAO.saveEntityBean(flowTokenBean);
        }

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
    @Exceptional
    public boolean delFlowDefine(String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        FlowDefineBean old = flowDefineDAO.find(id);

        if (old == null)
        {
            throw new MYException("���̶��岻����");
        }

        if (flowInstanceDAO.countByFlowId(id) > 0)
        {
            throw new MYException("�����´���ʵ��,����ɾ��");
        }

        flowDefineDAO.deleteEntityBean(id);

        flowTokenDAO.deleteEntityBeansByFK(id);

        flowViewerDAO.deleteEntityBeansByFK(id);

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
    @Exceptional(sensitiveException = {RuntimeException.class, IOException.class})
    public boolean delFlowInstance(String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        FlowInstanceBean old = flowInstanceDAO.find(id);

        if (old == null)
        {
            throw new MYException("����ʵ��������");
        }

        if (old.getStatus() != FlowConstant.FLOW_INSTANCE_BEGIN)
        {
            throw new MYException("����ʵ�����ڳ�ʼ̬,����ɾ��");
        }

        flowInstanceDAO.deleteEntityBean(id);

        flowInstanceLogDAO.deleteEntityBeansByFK(id);

        flowBelongDAO.deleteEntityBeansByFK(id);

        // ɾ�����̲�����
        flowInstanceViewDAO.deleteEntityBeansByFK(id);

        // ֤���µĸ��� ɾ��ԭ�еĸ���
        if ( !StringTools.isNullOrNone(old.getAttachment()))
        {
            File file = new File(FileTools.formatPath2(this.attchmentPath) + old.getAttachment());

            file.delete();
        }

        return true;
    }

    /**
     * �ϳ�����
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    @Exceptional
    public boolean dropFlowDefine(String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        FlowDefineBean old = flowDefineDAO.find(id);

        if (old == null)
        {
            throw new MYException("����ʵ��������");
        }

        if (flowInstanceDAO.countNotEndInstance(id) > 0)
        {
            throw new MYException("�����´���û�н�����ʵ��,���ܷ��������̶���");
        }

        flowDefineDAO.updateFlowDefineStatus(id, FlowConstant.FLOW_STATUS_DROP);

        return true;
    }

    /**
     * ��ѯview��VO
     * 
     * @param flowId
     * @return
     */
    public List<FlowViewerBeanVO> queryFlowVieweVOByFlowId(String flowId)
    {
        List<FlowViewerBeanVO> views = flowViewerDAO.queryEntityVOsByFK(flowId);

        for (FlowViewerBeanVO flowViewerBeanVO : views)
        {
            String name = getAdapterPlugin(flowViewerBeanVO.getType()).getProcesserName(
                flowViewerBeanVO.getProcesser());

            flowViewerBeanVO.setProcesserName(name);
        }

        return views;
    }

    /**
     * ��ѯVO
     * 
     * @param id
     * @return
     */
    public FlowDefineBeanVO findFlowDefineVO(String id)
    {
        FlowDefineBeanVO vo = flowDefineDAO.findVO(id);

        if (vo == null)
        {
            return null;
        }

        List<FlowTokenBeanVO> vos = flowTokenDAO.queryEntityVOsByFK(id);

        vos.remove(vos.size() - 1);

        Collections.sort(vos, new Comparator<FlowTokenBeanVO>()
        {
            public int compare(FlowTokenBeanVO o1, FlowTokenBeanVO o2)
            {
                return o1.getOrders() - o2.getOrders();
            }
        });

        organiseVO(vos);

        vo.setTokensVO(vos);

        return vo;
    }

    /**
     * ��֯VO��չʾ
     * 
     * @param vos
     */
    private void organiseVO(List<FlowTokenBeanVO> vos)
    {
        for (FlowTokenBeanVO flowTokenBeanVO : vos)
        {
            String processerName = getAdapterPlugin(flowTokenBeanVO.getType()).getProcesserName(
                flowTokenBeanVO.getProcesser());

            flowTokenBeanVO.setProcesserName(processerName);
        }
    }

    /**
     * ��ѯ����ʵ��VO
     * 
     * @param id
     * @return
     */
    public FlowInstanceBeanVO findFlowInstanceVO(String id)
    {
        FlowInstanceBeanVO vo = flowInstanceDAO.findVO(id);

        if (vo == null)
        {
            return null;
        }

        List<FlowInstanceLogBeanVO> logVO = flowInstanceLogDAO.queryEntityVOsByFK(id);

        Collections.sort(logVO, new Comparator<FlowInstanceLogBeanVO>()
        {
            public int compare(FlowInstanceLogBeanVO o1, FlowInstanceLogBeanVO o2)
            {
                return StringTools.compare(o1.getLogTime(), o2.getLogTime());
            }
        });

        vo.setLogsVO(logVO);

        return vo;
    }

    private void addFlowLog(String opinion, int oprMode, String currentTokenId,
                            String nextTokenId, FlowInstanceBean bean, User user)
    {
        FlowInstanceLogBean log = new FlowInstanceLogBean();

        log.setOpinion(opinion);

        log.setOprMode(oprMode);

        log.setFlowId(bean.getFlowId());

        log.setInstanceId(bean.getId());

        log.setUserId(user.getId());

        log.setLogTime(TimeTools.now());

        log.setTokenId(currentTokenId);

        log.setNextTokenId(nextTokenId);

        flowInstanceLogDAO.saveEntityBean(log);
    }

    private CommonPlugin getAdapterPlugin(int type)
    {
        if (type == FlowConstant.FLOW_TYPE_ALL)
        {
            return allUserPlugin;
        }

        if (type == FlowConstant.FLOW_TYPE_USER)
        {
            return userPlugin;
        }

        if (type == FlowConstant.FLOW_TYPE_ROLE)
        {
            return rolePlugin;
        }

        if (type == FlowConstant.FLOW_TYPE_STAFFER)
        {
            return stafferPlugin;
        }

        return stafferPlugin;
    }

    private void processBelong(String instanceId, FlowTokenBean token)
        throws MYException
    {
        getAdapterPlugin(token.getType()).processFlowInstanceBelong(instanceId, token);
    }

    private void processViewer(String instanceId, String flowId)
        throws MYException
    {
        // getAdapterPlugin(token);
        List<FlowViewerBean> viewers = flowViewerDAO.queryEntityBeansByFK(flowId);

        for (FlowViewerBean flowViewerBean : viewers)
        {
            getAdapterPlugin(flowViewerBean.getType()).processFlowInstanceViewer(instanceId,
                flowViewerBean);
        }
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
     * @return the flowInstanceLogDAO
     */
    public FlowInstanceLogDAO getFlowInstanceLogDAO()
    {
        return flowInstanceLogDAO;
    }

    /**
     * @param flowInstanceLogDAO
     *            the flowInstanceLogDAO to set
     */
    public void setFlowInstanceLogDAO(FlowInstanceLogDAO flowInstanceLogDAO)
    {
        this.flowInstanceLogDAO = flowInstanceLogDAO;
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
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }

    /**
     * @return the flowConsignDAO
     */
    public FlowConsignDAO getFlowConsignDAO()
    {
        return flowConsignDAO;
    }

    /**
     * @param flowConsignDAO
     *            the flowConsignDAO to set
     */
    public void setFlowConsignDAO(FlowConsignDAO flowConsignDAO)
    {
        this.flowConsignDAO = flowConsignDAO;
    }

    /**
     * @return the userDAO
     */
    public UserDAO getUserDAO()
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
     * @return the flowBelongDAO
     */
    public FlowBelongDAO getFlowBelongDAO()
    {
        return flowBelongDAO;
    }

    /**
     * @param flowBelongDAO
     *            the flowBelongDAO to set
     */
    public void setFlowBelongDAO(FlowBelongDAO flowBelongDAO)
    {
        this.flowBelongDAO = flowBelongDAO;
    }

    /**
     * @return the attchmentPath
     */
    public String getAttchmentPath()
    {
        return attchmentPath;
    }

    /**
     * @param attchmentPath
     *            the attchmentPath to set
     */
    public void setAttchmentPath(String attchmentPath)
    {
        this.attchmentPath = attchmentPath;
    }

    /**
     * @return the allUserPlugin
     */
    public AllUserPlugin getAllUserPlugin()
    {
        return allUserPlugin;
    }

    /**
     * @return the rolePlugin
     */
    public RolePlugin getRolePlugin()
    {
        return rolePlugin;
    }

    /**
     * @return the userPlugin
     */
    public UserPlugin getUserPlugin()
    {
        return userPlugin;
    }

    /**
     * @return the stafferPlugin
     */
    public StafferPlugin getStafferPlugin()
    {
        return stafferPlugin;
    }

    /**
     * @param allUserPlugin
     *            the allUserPlugin to set
     */
    public void setAllUserPlugin(AllUserPlugin allUserPlugin)
    {
        this.allUserPlugin = allUserPlugin;
    }

    /**
     * @param rolePlugin
     *            the rolePlugin to set
     */
    public void setRolePlugin(RolePlugin rolePlugin)
    {
        this.rolePlugin = rolePlugin;
    }

    /**
     * @param userPlugin
     *            the userPlugin to set
     */
    public void setUserPlugin(UserPlugin userPlugin)
    {
        this.userPlugin = userPlugin;
    }

    /**
     * @param stafferPlugin
     *            the stafferPlugin to set
     */
    public void setStafferPlugin(StafferPlugin stafferPlugin)
    {
        this.stafferPlugin = stafferPlugin;
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
     * @return the flowInstanceViewDAO
     */
    public FlowInstanceViewDAO getFlowInstanceViewDAO()
    {
        return flowInstanceViewDAO;
    }

    /**
     * @param flowInstanceViewDAO
     *            the flowInstanceViewDAO to set
     */
    public void setFlowInstanceViewDAO(FlowInstanceViewDAO flowInstanceViewDAO)
    {
        this.flowInstanceViewDAO = flowInstanceViewDAO;
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
}
