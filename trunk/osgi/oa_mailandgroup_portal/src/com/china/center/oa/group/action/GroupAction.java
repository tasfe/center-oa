/**
 * File Name: WorkLogAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-2-16<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.group.action;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.osgi.framework.BundleContext;

import com.center.china.osgi.publics.User;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.gm.constant.GroupConstant;
import com.china.center.oa.group.bean.GroupBean;
import com.china.center.oa.group.dao.GroupDAO;
import com.china.center.oa.group.dao.GroupVSStafferDAO;
import com.china.center.oa.group.vo.GroupVSStafferVO;
import com.china.center.oa.group.vs.GroupVSStafferBean;
import com.china.center.oa.mg.facade.MailGroupFacade;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.vo.StafferVO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.StringTools;


/**
 * WorkLogAction
 * 
 * @author ZHUZHU
 * @version 2009-2-16
 * @see GroupAction
 * @since 1.0
 */
public class GroupAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private BundleContext bundleContext = null;

    private MailGroupFacade mailGroupFacade = null;

    private GroupDAO groupDAO = null;

    private StafferDAO stafferDAO = null;

    private GroupVSStafferDAO groupVSStafferDAO = null;

    private static final String QUERYGROUP = "queryGroup";

    private static final String QUERYPUBLICGROUP = "queryPublicGroup";

    private static final String QUERYSYSTEMGROUP = "querySystemGroup";

    /**
     * default constructor
     */
    public GroupAction()
    {
    }

    /**
     * queryGroup
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response)
        throws ServletException
    {
        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        String stafferId = request.getParameter("stafferId");

        if (StringTools.isNullOrNone(stafferId))
        {
            condtion.addCondition("GroupBean.stafferId", "=", user.getStafferId());
        }

        // filter system group
        // condtion.addCondition("GroupBean.type", "<>", GroupConstant.GROUP_TYPE_SYSTEM);

        ActionTools.processJSONQueryCondition(QUERYGROUP, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYGROUP, request, condtion, this.groupDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryPublicGroup
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryPublicGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response)
        throws ServletException
    {
        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addIntCondition("GroupBean.type", "=", GroupConstant.GROUP_TYPE_PUBLIC);

        ActionTools.processJSONQueryCondition(QUERYPUBLICGROUP, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYPUBLICGROUP, request, condtion, this.groupDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * querySystemGroup
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySystemGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response)
        throws ServletException
    {
        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addIntCondition("GroupBean.type", "=", GroupConstant.GROUP_TYPE_SYSTEM);

        ActionTools.processJSONQueryCondition(QUERYSYSTEMGROUP, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSYSTEMGROUP, request, condtion, this.groupDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * addOrUpdateGroup
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addOrUpdateGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response)
        throws ServletException
    {
        GroupBean bean = new GroupBean();

        String update = request.getParameter("update");

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            bean.setStafferId(user.getStafferId());

            createItems(request, bean);

            if ("1".equals(update))
            {
                mailGroupFacade.updateGroup(user.getId(), bean);
            }
            else
            {
                mailGroupFacade.addGroup(user.getId(), bean);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功保存群组");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存群组失败:" + e.getMessage());
        }

        if (bean.getType() == GroupConstant.GROUP_TYPE_PRIVATE)
        {
            return mapping.findForward("queryGroup");
        }

        if (bean.getType() == GroupConstant.GROUP_TYPE_SYSTEM)
        {
            return mapping.findForward("querySystemGroup");
        }

        // return mapping.findForward("queryPublicGroup");
        return mapping.findForward("queryGroup");
    }

    /**
     * find群组
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                   HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        String update = request.getParameter("update");

        GroupBean bean = groupDAO.find(id);

        if (bean == null)
        {
            return ActionTools.toError("群组不存在", mapping, request);
        }

        List<GroupVSStafferVO> items = groupVSStafferDAO.queryEntityVOsByFK(id);

        request.setAttribute("bean", bean);

        request.setAttribute("items", items);

        if ("1".equals(update))
        {
            return mapping.findForward("updateGroup");
        }

        return mapping.findForward("detailGroup");
    }

    /**
     * 构建Visit
     * 
     * @param request
     * @param bean
     * @throws MYException
     */
    private void createItems(HttpServletRequest request, GroupBean bean)
        throws MYException
    {
        List<GroupVSStafferBean> items = new ArrayList<GroupVSStafferBean>();

        bean.setItems(items);

        String[] vsStafferIds = request.getParameterValues("vsStafferIds");

        if (vsStafferIds == null)
        {
            return;
        }

        for (int i = 0; i < vsStafferIds.length; i++ )
        {
            if (StringTools.isNullOrNone(vsStafferIds[i]))
            {
                continue;
            }

            GroupVSStafferBean vs = new GroupVSStafferBean();

            vs.setStafferId(vsStafferIds[i]);

            items.add(vs);
        }
    }

    /**
     * delGroup
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward delGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            mailGroupFacade.delGroup(user.getId(), id);

            ajax.setSuccess("成功删除群组");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除群组失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * query group
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                       HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        Set<GroupBean> result = new HashSet<GroupBean>();

        User user = Helper.getUser(request);

        result.addAll(groupDAO.listPublicGroup());

        result.addAll(groupDAO.queryEntityBeansByFK(user.getStafferId()));

        request.setAttribute("beanList", result);

        return mapping.findForward("rptQueryGroup");
    }

    /**
     * 群组内职员查询
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryGroupMember(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                             HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String pid = request.getParameter("pid");

        GroupBean group = groupDAO.find(pid);

        List<StafferVO> list = new ArrayList<StafferVO>();

        List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK(pid);

        for (GroupVSStafferBean groupVSStafferBean : vs)
        {
            list.add(stafferDAO.findVO(groupVSStafferBean.getStafferId()));
        }

        request.setAttribute("beanList", list);

        request.setAttribute("group", group);

        return mapping.findForward("rptQueryGroupMember");
    }

    /**
     * 职员的查询(根据群组或者个人或者其他，不分页)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryStaffer2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        int mode = CommonTools.parseInt(request.getParameter("mode"));

        String pid = request.getParameter("pid");

        List<StafferVO> list = new ArrayList<StafferVO>();

        if (mode == 0)
        {
            list.add(stafferDAO.findVO(pid));
        }

        if (mode == 1)
        {
            List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK(pid);

            for (GroupVSStafferBean groupVSStafferBean : vs)
            {
                list.add(stafferDAO.findVO(groupVSStafferBean.getStafferId()));
            }
        }

        request.setAttribute("beanList", list);

        return mapping.findForward("rptQueryStaffer2");
    }

    /**
     * @return the mailGroupFacade
     */
    public MailGroupFacade getMailGroupFacade()
    {
        return mailGroupFacade;
    }

    /**
     * @param mailGroupFacade
     *            the mailGroupFacade to set
     */
    public void setMailGroupFacade(MailGroupFacade mailGroupFacade)
    {
        this.mailGroupFacade = mailGroupFacade;
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
     * @return the groupVSStafferDAO
     */
    public GroupVSStafferDAO getGroupVSStafferDAO()
    {
        return groupVSStafferDAO;
    }

    /**
     * @param groupVSStafferDAO
     *            the groupVSStafferDAO to set
     */
    public void setGroupVSStafferDAO(GroupVSStafferDAO groupVSStafferDAO)
    {
        this.groupVSStafferDAO = groupVSStafferDAO;
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
     * @return the bundleContext
     */
    public BundleContext getBundleContext()
    {
        return bundleContext;
    }

    /**
     * @param bundleContext
     *            the bundleContext to set
     */
    public void setBundleContext(BundleContext bundleContext)
    {
        this.bundleContext = bundleContext;
    }
}
