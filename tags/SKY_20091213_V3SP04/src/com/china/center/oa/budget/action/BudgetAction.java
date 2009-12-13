/**
 * File Name: StafferAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.action;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.china.center.common.ConditionParse;
import com.china.center.common.KeyConstant;
import com.china.center.common.MYException;
import com.china.center.common.json.AjaxResult;
import com.china.center.common.query.HandleResult;
import com.china.center.common.query.QueryConfig;
import com.china.center.oa.budget.bean.BudgetApplyBean;
import com.china.center.oa.budget.bean.BudgetBean;
import com.china.center.oa.budget.bean.BudgetItemBean;
import com.china.center.oa.budget.bean.FeeItemBean;
import com.china.center.oa.budget.dao.BudgetApplyDAO;
import com.china.center.oa.budget.dao.BudgetDAO;
import com.china.center.oa.budget.dao.BudgetItemDAO;
import com.china.center.oa.budget.dao.BudgetLogDAO;
import com.china.center.oa.budget.dao.FeeItemDAO;
import com.china.center.oa.budget.helper.BudgetHelper;
import com.china.center.oa.budget.manager.BudgetApplyManager;
import com.china.center.oa.budget.manager.BudgetManager;
import com.china.center.oa.budget.vo.BudgetItemVO;
import com.china.center.oa.budget.vo.BudgetLogVO;
import com.china.center.oa.budget.vo.BudgetVO;
import com.china.center.oa.constant.AuthConstant;
import com.china.center.oa.constant.BudgetConstant;
import com.china.center.oa.constant.PublicConstant;
import com.china.center.oa.facade.BudgetFacade;
import com.china.center.oa.helper.Helper;
import com.china.center.oa.helper.LocationHelper;
import com.china.center.oa.publics.User;
import com.china.center.oa.publics.dao.LogDAO;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.publics.vo.LogVO;
import com.china.center.tools.ActionTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.JSONTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


public class BudgetAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private UserManager userManager = null;

    private QueryConfig queryConfig = null;

    private BudgetManager budgetManager = null;

    private BudgetFacade budgetFacade = null;

    private BudgetApplyDAO budgetApplyDAO = null;

    private BudgetApplyManager budgetApplyManager = null;

    private BudgetDAO budgetDAO = null;

    private BudgetLogDAO budgetLogDAO = null;

    private FeeItemDAO feeItemDAO = null;

    private BudgetItemDAO budgetItemDAO = null;

    private LogDAO logDAO = null;

    private static String QUERYBUDGET = "queryBudget";

    private static String QUERYBUDGETLOG = "queryBudgetLog";

    private static String QUERYRUNBUDGET = "queryRunBudget";

    private static String QUERYBUDGETAPPLY = "queryBudgetApply";

    private static String QUERYFEEITEM = "queryFeeItem";

    /**
     * default constructor
     */
    public BudgetAction()
    {}

    /**
     * queryBudget(��ѯԤ��)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryBudget(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        // û��check��û�и�Ȩ��ֻ�ܿ����Լ��Ļ���ͨ���ģ����˸�Ԥ�㣩
        if ( !userManager.containAuth(user, AuthConstant.BUDGET_CHECK)
            && !userManager.containAuth(user, AuthConstant.BUDGET_OPRROOT))
        {
            condtion.addCondition("and (BudgetBean.stafferId = '" + user.getStafferId()
                                  + "' or BudgetBean.status = "
                                  + BudgetConstant.BUDGET_STATUS_PASS + ")");

            // condtion.addIntCondition("BudgetBean.type", "!=", BudgetConstant.BUDGET_TYPE_COMPANY);
        }

        // û��check�����и�Ȩ��ֻ�ܿ����Լ��Ļ���ͨ����(������Ԥ��)
        if ( !userManager.containAuth(user, AuthConstant.BUDGET_CHECK)
            && userManager.containAuth(user, AuthConstant.BUDGET_OPRROOT))
        {
            condtion.addCondition("and (BudgetBean.stafferId = '" + user.getStafferId()
                                  + "' or BudgetBean.status = "
                                  + BudgetConstant.BUDGET_STATUS_PASS + ")");
        }

        ActionTools.processJSONQueryCondition(QUERYBUDGET, request, condtion);

        condtion.addCondition("order by BudgetBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYBUDGET, request, condtion,
            this.budgetDAO, new HandleResult<BudgetVO>()
            {
                public void handle(BudgetVO obj)
                {
                    BudgetHelper.formatBudgetVO(obj);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryBudgetLog
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryBudgetLog(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYBUDGETLOG, request, condtion);

        condtion.addCondition("order by BudgetLogBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYBUDGETLOG, request, condtion,
            this.budgetLogDAO, new HandleResult<BudgetLogVO>()
            {
                public void handle(BudgetLogVO obj)
                {
                    BudgetHelper.formatBudgetLog(obj);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * query run budget
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryRunBudget(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        // only can query pass and self budget
        condtion.addCondition("BudgetBean.stafferId", "=", user.getStafferId());

        condtion.addIntCondition("BudgetBean.status", "=", BudgetConstant.BUDGET_STATUS_PASS);

        ActionTools.processJSONQueryCondition(QUERYRUNBUDGET, request, condtion);

        condtion.addCondition("order by BudgetBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYRUNBUDGET, request, condtion,
            this.budgetDAO, new HandleResult<BudgetVO>()
            {
                public void handle(BudgetVO obj)
                {
                    String formatNum = MathTools.formatNum(obj.getTotal());

                    obj.setStotal(formatNum);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryBudgetApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryBudgetApply(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        if (userManager.containAuth(user, AuthConstant.BUDGET_CHANGE_APPROVE_CFO,
            AuthConstant.BUDGET_CHANGE_APPROVE_COO, AuthConstant.BUDGET_CHANGE_APPROVE_CEO))
        {
            condtion.addIntCondition("BudgetApplyBean.status", ">=",
                BudgetConstant.BUDGET_APPLY_STATUS_WAIT_CFO);
        }
        else
        {
            condtion.addCondition("BudgetApplyBean.stafferId", "=", user.getStafferId());
        }

        ActionTools.processJSONQueryCondition(QUERYBUDGETAPPLY, request, condtion);

        condtion.addCondition("order by BudgetApplyBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYBUDGETAPPLY, request, condtion,
            this.budgetApplyDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryFeeItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryFeeItem(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYFEEITEM, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYFEEITEM, request, condtion,
            this.feeItemDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * preForAddBudget
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddBudget(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String parentId = request.getParameter("parentId");

        String type = request.getParameter("type");

        BudgetBean parentBean = budgetDAO.find(parentId);

        if (parentBean == null)
        {
            return ActionTools.toError("��Ԥ�㲻����", QUERYBUDGET, mapping, request);
        }

        parentBean.setItems(budgetItemDAO.queryEntityBeansByFK(parentId));

        request.setAttribute("pbean", parentBean);

        // add department budget
        if ("1".equals(type))
        {
            if (parentBean.getType() == BudgetConstant.BUDGET_TYPE_DEPARTMENT)
            {
                int nextLevel = BudgetHelper.getNextType(parentBean.getLevel());

                if (nextLevel > BudgetConstant.BUDGET_LEVEL_MONTH)
                {
                    return ActionTools.toError("�¶�Ԥ���Ѿ���С,������ϸ��", QUERYBUDGET, mapping, request);
                }

                request.setAttribute("nextLevel", nextLevel);
            }
            else
            {
                request.setAttribute("nextLevel", BudgetConstant.BUDGET_LEVEL_YEAR);
            }
        }

        List<FeeItemBean> feeItems = feeItemDAO.listEntityBeans();

        request.setAttribute("feeItems", feeItems);

        return mapping.findForward("addBudget");
    }

    /**
     * queryReference
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryReference(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String parentId = request.getParameter("pid");

        BudgetBean parentBean = budgetDAO.find(parentId);

        if (parentBean == null)
        {
            return ActionTools.toError("��Ԥ�㲻����", QUERYBUDGET, mapping, request);
        }

        List<BudgetItemVO> items = budgetItemDAO.queryEntityVOsByFK(parentId);

        request.setAttribute("pbean", parentBean);

        request.setAttribute("items", items);

        List<BudgetBean> subBudget = budgetDAO.querySubmitBudgetByParentId(parentId);

        // handle item
        for (BudgetItemVO budgetItemBean : items)
        {
            String subDesc = "";

            double total = 0.0d;

            for (BudgetBean budgetBean : subBudget)
            {
                BudgetItemBean subBudgetItemBean = budgetItemDAO.findByBudgetIdAndFeeItemId(
                    budgetBean.getId(), budgetItemBean.getFeeItemId());

                if (subBudgetItemBean != null)
                {
                    subDesc += budgetBean.getName() + " ";

                    total += subBudgetItemBean.getBudget();
                }
            }

            budgetItemBean.setDescription(subDesc);

            double last = budgetItemBean.getBudget() - total;

            budgetItemBean.setSbudget(MathTools.formatNum(last));
        }

        return mapping.findForward("rptQueryReference");
    }

    /**
     * rptQueryCurrentBudget
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryCurrentBudget(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        List<BudgetVO> currentRunBudgetList = budgetDAO.queryCurrentRunBudget();

        request.setAttribute("currentRunBudgetList",
            BudgetHelper.formatBudgetList(currentRunBudgetList));

        return mapping.findForward("rptQueryCurrentBudget");
    }

    /**
     * rptQueryCurrentBudgetItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryBudgetItem(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request,
                                            HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("budgetId");

        request.setAttribute("beanList",
            BudgetHelper.formatBudgetItemList(budgetItemDAO.queryEntityVOsByFK(id)));

        return mapping.findForward("rptQueryBudgetItem");
    }

    /**
     * addUser
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addBudget(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        String opr = request.getParameter("opr");

        try
        {
            BudgetBean bean = new BudgetBean();

            BeanUtil.getBean(bean, request);

            bean.setItems(getBudgetItem(request));

            bean.setLogTime(TimeTools.now());

            bean.setStafferId(user.getStafferId());

            if (LocationHelper.isVirtualLocation(user.getLocationId()))
            {
                bean.setLocationId(PublicConstant.CENTER_LOCATION);
            }
            else
            {
                bean.setLocationId(user.getLocationId());
            }

            if ("1".equals(opr))
            {
                bean.setStatus(BudgetConstant.BUDGET_STATUS_SUBMIT);
            }

            budgetFacade.addBudget(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "����Ԥ��ɳɹ�");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "����Ԥ��ʧ��:" + e.getMessage());
        }

        return mapping.findForward(QUERYBUDGET);
    }

    /**
     * addBudgetApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addBudgetApply(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        String applyReason = request.getParameter("applyReason");
        String oprType = request.getParameter("oprType");

        try
        {
            BudgetApplyBean bean = new BudgetApplyBean();

            BeanUtil.getBean(bean, request);

            bean.setDescription(applyReason);

            bean.setType(CommonTools.parseInt(oprType));

            bean.setItems(getBudgetItem(request));

            bean.setLogTime(TimeTools.now());

            bean.setStafferId(user.getStafferId());

            bean.setStatus(BudgetConstant.BUDGET_STATUS_SUBMIT);

            budgetFacade.addBudgetApply(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "�������Ԥ��ɹ�");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "�������Ԥ��ʧ��:" + e.getMessage());
        }

        return mapping.findForward(QUERYBUDGETAPPLY);
    }

    /**
     * addFeeItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addOrUpdateFeeItem(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request,
                                            HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        try
        {
            FeeItemBean bean = new FeeItemBean();

            BeanUtil.getBean(bean, request);

            if ( !StringTools.isNullOrNone(bean.getId()))
            {
                budgetFacade.updateFeeItem(user.getId(), bean);
            }
            else
            {
                budgetFacade.addFeeItem(user.getId(), bean);
            }

            request.setAttribute(KeyConstant.MESSAGE, "����Ԥ����ɹ�");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "����Ԥ��ʧ��:" + e.getMessage());
        }

        return mapping.findForward(QUERYFEEITEM);
    }

    /**
     * ������ɫ
     * 
     * @param request
     * @param bean
     */
    private List<BudgetItemBean> getBudgetItem(HttpServletRequest request)
    {
        List<BudgetItemBean> items = new ArrayList<BudgetItemBean>();
        User user = Helper.getUser(request);

        String[] names = request.getParameterValues("item_name");
        String[] budgets = request.getParameterValues("item_budget");
        String[] descriptions = request.getParameterValues("item_description");

        // check null
        if (names == null || names.length == 0)
        {
            return items;
        }

        for (int i = 0; i < names.length; i++ )
        {
            if (StringTools.isNullOrNone(names[i]))
            {
                continue;
            }

            BudgetItemBean item = new BudgetItemBean();

            item.setFeeItemId(names[i]);

            item.setBudget(Double.parseDouble(budgets[i]));

            // set useMoney equals budget
            item.setUseMonery(item.getBudget());

            item.setLocationId(user.getLocationId());

            if (descriptions.length > i)
            {
                item.setDescription(descriptions[i]);
            }

            items.add(item);
        }

        return items;
    }

    /**
     * updateBudget
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateBudget(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        String opr = request.getParameter("opr");

        try
        {
            BudgetBean bean = new BudgetBean();

            BeanUtil.getBean(bean, request);

            bean.setItems(getBudgetItem(request));

            bean.setLogTime(TimeTools.now());

            bean.setStafferId(user.getStafferId());

            if (LocationHelper.isVirtualLocation(user.getLocationId()))
            {
                bean.setLocationId(PublicConstant.CENTER_LOCATION);
            }
            else
            {
                bean.setLocationId(user.getLocationId());
            }

            if ("1".equals(opr))
            {
                bean.setStatus(BudgetConstant.BUDGET_STATUS_SUBMIT);
            }

            budgetFacade.updateBudget(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "�޸�Ԥ��ɹ�");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "�޸�Ԥ��ʧ��:" + e.getMessage());
        }

        return mapping.findForward(QUERYBUDGET);
    }

    /**
     * queryLog
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryLog(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        List<LogVO> logs = logDAO.queryEntityVOsByFK(id);

        ajax.setSuccess(logs);

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * delBudget
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward delBudget(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            budgetFacade.delBudget(user.getId(), id);

            ajax.setSuccess("�ɹ�ɾ��");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("ɾ��ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * delFeeItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward delFeeItem(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            budgetFacade.deleteFeeItem(user.getId(), id);

            ajax.setSuccess("�ɹ�ɾ��Ԥ����");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("ɾ��Ԥ����ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * findUser
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findBudget(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        User user = Helper.getUser(request);

        String update = request.getParameter("update");

        BudgetVO vo = null;
        try
        {
            vo = budgetManager.findBudgetVO(id);

            if (vo == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "Ԥ�㲻����");

                return mapping.findForward(QUERYBUDGET);
            }

            request.setAttribute("bean", vo);
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "��ѯʧ��:" + e.getMessage());

            return mapping.findForward(QUERYBUDGET);
        }

        BudgetBean parentBean = budgetDAO.find(vo.getParentId());

        if (parentBean == null)
        {
            return ActionTools.toError("��Ԥ�㲻����", QUERYBUDGET, mapping, request);
        }

        request.setAttribute("pbean", parentBean);

        List<FeeItemBean> feeItems = feeItemDAO.listEntityBeans();

        request.setAttribute("feeItems", feeItems);

        // whether apply
        if ("2".equals(update) || "3".equals(update))
        {
            try
            {
                budgetApplyManager.whetherApply(id);
            }
            catch (MYException e)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getMessage());

                return mapping.findForward(QUERYRUNBUDGET);
            }
        }

        // ֻ���Լ������ڳ�ʼ�Ͳ��صĿ����޸�
        if ("1".equals(update)
            && user.getStafferId().equals(vo.getStafferId())
            && (vo.getStatus() == BudgetConstant.BUDGET_STATUS_INIT || vo.getStatus() == BudgetConstant.BUDGET_STATUS_REJECT))
        {
            return mapping.findForward("updateBudget");
        }
        // forward ���
        else if ("2".equals(update) && vo.getStatus() == BudgetConstant.BUDGET_STATUS_PASS
                 && user.getStafferId().equals(vo.getStafferId()))
        {
            return mapping.findForward("updateBudget2");
        }
        // forward ׷��
        else if ("3".equals(update) && vo.getStatus() == BudgetConstant.BUDGET_STATUS_PASS
                 && user.getStafferId().equals(vo.getStafferId()))
        {
            return mapping.findForward("updateBudget3");
        }
        else
        {
            return mapping.findForward("detailBudget");
        }
    }

    /**
     * findBudgetApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findBudgetApply(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        BudgetApplyBean bean = null;

        BudgetVO vo = null;

        try
        {
            bean = budgetApplyDAO.find(id);

            if (bean == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "Ԥ�����벻����");

                return mapping.findForward(QUERYBUDGET);
            }

            vo = budgetManager.findBudgetVO(bean.getBudgetId());

            if (vo == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "Ԥ�㲻����");

                return mapping.findForward(QUERYBUDGET);
            }

            request.setAttribute("bean", vo);

            request.setAttribute("applyBean", bean);
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "��ѯʧ��:" + e.getMessage());

            return mapping.findForward(QUERYBUDGET);
        }

        BudgetBean parentBean = budgetDAO.find(vo.getParentId());

        if (parentBean == null)
        {
            return ActionTools.toError("��Ԥ�㲻����", QUERYBUDGET, mapping, request);
        }

        request.setAttribute("pbean", parentBean);

        List<FeeItemBean> feeItems = feeItemDAO.listEntityBeans();

        request.setAttribute("feeItems", feeItems);

        // ������
        List<BudgetItemVO> items = budgetItemDAO.queryEntityVOsByFK(id);

        // format
        for (BudgetItemVO budgetItemVO : items)
        {
            BudgetHelper.formatBudgetItem(budgetItemVO);
        }

        request.setAttribute("items", items);

        return mapping.findForward("detailBudgetApply");
    }

    /**
     * findFeeItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findFeeItem(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        FeeItemBean bean = null;
        try
        {
            bean = feeItemDAO.find(id);

            if (bean == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "Ԥ�������");

                return mapping.findForward(QUERYFEEITEM);
            }

            request.setAttribute("bean", bean);
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "��ѯʧ��:" + e.getMessage());

            return mapping.findForward(QUERYBUDGET);
        }

        return mapping.findForward("updateFeeItem");
    }

    /**
     * ���Ԥ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward auditingBudget(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        User user = Helper.getUser(request);

        String opr = request.getParameter("opr");

        String reason = request.getParameter("reason");

        AjaxResult ajax = new AjaxResult();

        try
        {
            if ("0".equals(opr))
            {
                budgetFacade.passBudget(user.getId(), id);
            }

            if ("1".equals(opr))
            {
                budgetFacade.rejectBudget(user.getId(), id, reason);
            }

            ajax.setSuccess("����Ԥ��ɹ�");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("����Ԥ��ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * auditingBudgetApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward auditingBudgetApply(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        User user = Helper.getUser(request);

        String opr = request.getParameter("opr");

        String mode = request.getParameter("mode");

        String reason = request.getParameter("reason");

        AjaxResult ajax = new AjaxResult();

        try
        {
            if ("0".equals(opr))
            {
                budgetFacade.passBudgetApply(user.getId(), mode, id);
            }

            if ("1".equals(opr))
            {
                budgetFacade.rejectBudgetApply(user.getId(), mode, id, reason);
            }

            ajax.setSuccess("��׼Ԥ�����ɹ�");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("��׼Ԥ����ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * @return the userManager
     */
    public UserManager getUserManager()
    {
        return userManager;
    }

    /**
     * @param userManager
     *            the userManager to set
     */
    public void setUserManager(UserManager userManager)
    {
        this.userManager = userManager;
    }

    /**
     * @return the queryConfig
     */
    public QueryConfig getQueryConfig()
    {
        return queryConfig;
    }

    /**
     * @param queryConfig
     *            the queryConfig to set
     */
    public void setQueryConfig(QueryConfig queryConfig)
    {
        this.queryConfig = queryConfig;
    }

    /**
     * @return the budgetManager
     */
    public BudgetManager getBudgetManager()
    {
        return budgetManager;
    }

    /**
     * @param budgetManager
     *            the budgetManager to set
     */
    public void setBudgetManager(BudgetManager budgetManager)
    {
        this.budgetManager = budgetManager;
    }

    /**
     * @return the budgetFacade
     */
    public BudgetFacade getBudgetFacade()
    {
        return budgetFacade;
    }

    /**
     * @param budgetFacade
     *            the budgetFacade to set
     */
    public void setBudgetFacade(BudgetFacade budgetFacade)
    {
        this.budgetFacade = budgetFacade;
    }

    /**
     * @return the budgetDAO
     */
    public BudgetDAO getBudgetDAO()
    {
        return budgetDAO;
    }

    /**
     * @param budgetDAO
     *            the budgetDAO to set
     */
    public void setBudgetDAO(BudgetDAO budgetDAO)
    {
        this.budgetDAO = budgetDAO;
    }

    /**
     * @return the budgetItemDAO
     */
    public BudgetItemDAO getBudgetItemDAO()
    {
        return budgetItemDAO;
    }

    /**
     * @param budgetItemDAO
     *            the budgetItemDAO to set
     */
    public void setBudgetItemDAO(BudgetItemDAO budgetItemDAO)
    {
        this.budgetItemDAO = budgetItemDAO;
    }

    /**
     * @return the feeItemDAO
     */
    public FeeItemDAO getFeeItemDAO()
    {
        return feeItemDAO;
    }

    /**
     * @param feeItemDAO
     *            the feeItemDAO to set
     */
    public void setFeeItemDAO(FeeItemDAO feeItemDAO)
    {
        this.feeItemDAO = feeItemDAO;
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
     * @return the budgetApplyDAO
     */
    public BudgetApplyDAO getBudgetApplyDAO()
    {
        return budgetApplyDAO;
    }

    /**
     * @param budgetApplyDAO
     *            the budgetApplyDAO to set
     */
    public void setBudgetApplyDAO(BudgetApplyDAO budgetApplyDAO)
    {
        this.budgetApplyDAO = budgetApplyDAO;
    }

    /**
     * @return the budgetApplyManager
     */
    public BudgetApplyManager getBudgetApplyManager()
    {
        return budgetApplyManager;
    }

    /**
     * @param budgetApplyManager
     *            the budgetApplyManager to set
     */
    public void setBudgetApplyManager(BudgetApplyManager budgetApplyManager)
    {
        this.budgetApplyManager = budgetApplyManager;
    }

    /**
     * @return the budgetLogDAO
     */
    public BudgetLogDAO getBudgetLogDAO()
    {
        return budgetLogDAO;
    }

    /**
     * @param budgetLogDAO
     *            the budgetLogDAO to set
     */
    public void setBudgetLogDAO(BudgetLogDAO budgetLogDAO)
    {
        this.budgetLogDAO = budgetLogDAO;
    }

}
