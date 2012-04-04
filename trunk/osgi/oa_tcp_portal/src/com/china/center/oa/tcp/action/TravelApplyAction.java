/**
 * File Name: TravelApplyAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.User;
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.HandleQueryCondition;
import com.china.center.actionhelper.common.JSONPageSeparateTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.budget.constant.BudgetConstant;
import com.china.center.oa.budget.dao.BudgetItemDAO;
import com.china.center.oa.budget.dao.FeeItemDAO;
import com.china.center.oa.budget.vo.FeeItemVO;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.vo.FlowLogVO;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.oa.tcp.bean.TcpApproveBean;
import com.china.center.oa.tcp.bean.TcpFlowBean;
import com.china.center.oa.tcp.bean.TcpShareBean;
import com.china.center.oa.tcp.bean.TravelApplyBean;
import com.china.center.oa.tcp.bean.TravelApplyItemBean;
import com.china.center.oa.tcp.bean.TravelApplyPayBean;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.constanst.TcpFlowConstant;
import com.china.center.oa.tcp.dao.ExpenseApplyDAO;
import com.china.center.oa.tcp.dao.TcpApplyDAO;
import com.china.center.oa.tcp.dao.TcpApproveDAO;
import com.china.center.oa.tcp.dao.TcpFlowDAO;
import com.china.center.oa.tcp.dao.TcpHandleHisDAO;
import com.china.center.oa.tcp.dao.TcpPrepaymentDAO;
import com.china.center.oa.tcp.dao.TcpShareDAO;
import com.china.center.oa.tcp.dao.TravelApplyDAO;
import com.china.center.oa.tcp.dao.TravelApplyItemDAO;
import com.china.center.oa.tcp.dao.TravelApplyPayDAO;
import com.china.center.oa.tcp.helper.TCPHelper;
import com.china.center.oa.tcp.manager.TcpFlowManager;
import com.china.center.oa.tcp.manager.TravelApplyManager;
import com.china.center.oa.tcp.vo.TcpApproveVO;
import com.china.center.oa.tcp.vo.TcpHandleHisVO;
import com.china.center.oa.tcp.vo.TravelApplyItemVO;
import com.china.center.oa.tcp.vo.TravelApplyVO;
import com.china.center.oa.tcp.wrap.TcpParamWrap;
import com.china.center.osgi.jsp.ElTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.FileTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.UtilStream;
import com.china.center.tools.WriteFileBuffer;


/**
 * TravelApplyAction
 * 
 * @author ZHUZHU
 * @version 2011-7-20
 * @see TravelApplyAction
 * @since 3.0
 */
public class TravelApplyAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private TravelApplyManager travelApplyManager = null;

    private TcpApplyDAO tcpApplyDAO = null;

    private TcpFlowDAO tcpFlowDAO = null;

    private TcpApproveDAO tcpApproveDAO = null;

    private TcpPrepaymentDAO tcpPrepaymentDAO = null;

    private TcpShareDAO tcpShareDAO = null;

    private TcpFlowManager tcpFlowManager = null;

    private TravelApplyDAO travelApplyDAO = null;

    private TravelApplyItemDAO travelApplyItemDAO = null;

    private TravelApplyPayDAO travelApplyPayDAO = null;

    private BudgetItemDAO budgetItemDAO = null;

    private FeeItemDAO feeItemDAO = null;

    private FlowLogDAO flowLogDAO = null;

    private TcpHandleHisDAO tcpHandleHisDAO = null;

    private ExpenseApplyDAO expenseApplyDAO = null;

    private OutBillDAO outBillDAO = null;

    private DutyDAO dutyDAO = null;

    private FinanceDAO financeDAO = null;

    private AttachmentDAO attachmentDAO = null;

    private static String QUERYSELFTRAVELAPPLY = "tcp.querySelfTravelApply";

    private static String QUERYALLTRAVELAPPLY = "tcp.queryAllTravelApply";

    private static String QUERYSELFAPPROVE = "tcp.queryTcpSelfApprove";

    private static String QUERYPOOLAPPROVE = "tcp.queryTcpPoolApprove";

    private static String RPTQUERYTRAVELAPPLY = "rptQueryTravelApply";

    private static String QUERYTCPHIS = "tcp.queryTcpHis";

    /**
     * default constructor
     */
    public TravelApplyAction()
    {
    }

    /**
     * queryTravelApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfTravelApply(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYSELFTRAVELAPPLY, request, condtion);

        String type = request.getParameter("type");

        if ("2".equals(type))
        {
            condtion.addCondition("AND (TravelApplyBean.stafferId = '" + user.getStafferId()
                                  + "' OR TravelApplyBean.borrowStafferId = '"
                                  + user.getStafferId() + "')");
        }
        else
        {
            condtion.addCondition("TravelApplyBean.stafferId", "=", user.getStafferId());
        }

        condtion.addIntCondition("TravelApplyBean.type", "=", type);

        condtion.addCondition("order by TravelApplyBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSELFTRAVELAPPLY, request,
            condtion, this.travelApplyDAO, new HandleResult<TravelApplyVO>()
            {
                public void handle(TravelApplyVO vo)
                {
                    TCPHelper.chageVO(vo);

                    // 当前处理人
                    List<TcpApproveVO> approveList = tcpApproveDAO.queryEntityVOsByFK(vo.getId());

                    for (TcpApproveVO tcpApproveVO : approveList)
                    {
                        if (tcpApproveVO.getPool() == TcpConstanst.TCP_POOL_COMMON)
                        {
                            vo.setProcesser(vo.getProcesser() + tcpApproveVO.getApproverName()
                                            + ';');
                        }
                    }
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryAllTravelApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAllTravelApply(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYALLTRAVELAPPLY, request, condtion);

        condtion.addCondition("order by TravelApplyBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYALLTRAVELAPPLY, request,
            condtion, this.travelApplyDAO, new HandleResult<TravelApplyVO>()
            {
                public void handle(TravelApplyVO vo)
                {
                    TCPHelper.chageVO(vo);

                    // 当前处理人
                    List<TcpApproveVO> approveList = tcpApproveDAO.queryEntityVOsByFK(vo.getId());

                    for (TcpApproveVO tcpApproveVO : approveList)
                    {
                        if (tcpApproveVO.getPool() == TcpConstanst.TCP_POOL_COMMON)
                        {
                            vo.setProcesser(vo.getProcesser() + tcpApproveVO.getApproverName()
                                            + ';');
                        }
                    }
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryPoolApprove
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryPoolApprove(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        String cacheKey = QUERYPOOLAPPROVE;

        ActionTools.processJSONQueryCondition(cacheKey, request, condtion);

        condtion.addCondition("TcpApproveBean.approverId", "=", user.getStafferId());

        condtion.addIntCondition("TcpApproveBean.pool", "=", TcpConstanst.TCP_POOL_POOL);

        condtion.addCondition("order by TcpApproveBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(cacheKey, request, condtion,
            this.tcpApproveDAO, new HandleResult<TcpApproveVO>()
            {
                public void handle(TcpApproveVO vo)
                {
                    TCPHelper.getTcpApproveVO(vo);

                    if (vo.getType() <= 10)
                    {
                        vo.setUrl(TcpConstanst.TCP_TRAVELAPPLY_DETAIL_URL + vo.getApplyId());
                    }
                    else
                    {
                        vo.setUrl(TcpConstanst.TCP_EXPENSE_DETAIL_URL + vo.getApplyId());
                    }
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryTcpHis
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryTcpHis(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        String cacheKey = QUERYTCPHIS;

        ActionTools.processJSONQueryCondition(cacheKey, request, condtion);

        condtion.addCondition("TcpHandleHisBean.stafferId", "=", user.getStafferId());

        condtion.addCondition("order by TcpHandleHisBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(cacheKey, request, condtion,
            this.tcpHandleHisDAO, new HandleResult<TcpHandleHisVO>()
            {
                public void handle(TcpHandleHisVO vo)
                {
                    TCPHelper.getTcpHandleHisVO(vo);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * 查询待我处理的
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfApprove(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        String cacheKey = QUERYSELFAPPROVE;

        ActionTools.processJSONQueryCondition(cacheKey, request, condtion);

        condtion.addCondition("TcpApproveBean.approverId", "=", user.getStafferId());

        condtion.addIntCondition("TcpApproveBean.pool", "=", TcpConstanst.TCP_POOL_COMMON);

        condtion.addCondition("order by TcpApproveBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(cacheKey, request, condtion,
            this.tcpApproveDAO, new HandleResult<TcpApproveVO>()
            {
                public void handle(TcpApproveVO vo)
                {
                    TCPHelper.getTcpApproveVO(vo);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * preForAddTravelApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddTravelApply(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response)
        throws ServletException
    {
        prepareInner(request);

        String type = request.getParameter("type");

        return mapping.findForward("addTravelApply" + type);
    }

    /**
     * prepareInner
     * 
     * @param request
     */
    private void prepareInner(HttpServletRequest request)
    {
        String type = request.getParameter("type");

        List<FeeItemVO> feeItemList = feeItemDAO.listEntityVOs();

        // 只有出差的特殊处理
        if ("0".equals(type))
        {
            for (Iterator iterator = feeItemList.iterator(); iterator.hasNext();)
            {
                FeeItemVO feeItemVO = (FeeItemVO)iterator.next();

                if (feeItemVO.getId().equals(BudgetConstant.FEE_ITEM_TRAVELLING))
                {
                    iterator.remove();
                    break;
                }
            }
        }

        request.setAttribute("feeItemList", feeItemList);

        // 群组
        request.setAttribute("pluginType", "group");
        request.setAttribute("pluginValue", TcpFlowConstant.GROUP_DM);
    }

    /**
     * deleteTravelApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteTravelApply(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            travelApplyManager.deleteTravelApplyBean(user, id);

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * findTravelApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findTravelApply(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        String id = request.getParameter("id");

        String update = request.getParameter("update");

        TravelApplyVO bean = travelApplyManager.findVO(id);

        if (bean == null)
        {
            return ActionTools.toError("数据异常,请重新操作", mapping, request);
        }

        prepareInner(request);

        request.setAttribute("bean", bean);

        request.setAttribute("update", update);

        // 查询关联的付款单和凭证
        List<OutBillBean> billList = outBillDAO.queryEntityBeansByFK(id);

        request.setAttribute("billList", billList);

        List<FinanceBean> financeList = financeDAO.queryEntityBeansByFK(id);

        request.setAttribute("financeList", financeList);

        // 2是稽核修改
        if ("1".equals(update) || "3".equals(update))
        {
            if ( !TCPHelper.canTravelApplyUpdate(bean))
            {
                return ActionTools.toError("申请当前状态下不能被修改", mapping, request);
            }

            List<AttachmentBean> attachmentList = bean.getAttachmentList();

            String attacmentIds = "";

            for (AttachmentBean attachmentBean : attachmentList)
            {
                attacmentIds = attacmentIds + attachmentBean.getId() + ";";
            }

            request.setAttribute("attacmentIds", attacmentIds);

            List<TravelApplyItemVO> itemVOList = bean.getItemVOList();

            // 出差特殊处理屏蔽差旅费
            if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_TRAVEL)
            {
                for (Iterator iterator = itemVOList.iterator(); iterator.hasNext();)
                {
                    TravelApplyItemVO travelApplyItemVO = (TravelApplyItemVO)iterator.next();

                    if (travelApplyItemVO.getFeeItemId().equals(BudgetConstant.FEE_ITEM_TRAVELLING))
                    {
                        iterator.remove();

                        break;
                    }
                }
            }

            return mapping.findForward("updateTravelApply" + bean.getType());
        }

        // 获取审批日志
        List<FlowLogBean> logs = flowLogDAO.queryEntityBeansByFK(id);

        List<FlowLogVO> logsVO = new ArrayList<FlowLogVO>();

        for (FlowLogBean flowLogBean : logs)
        {
            logsVO.add(TCPHelper.getTCPFlowLogVO(flowLogBean));
        }

        request.setAttribute("logList", logsVO);

        // 处理
        if ("2".equals(update))
        {
            // 先鉴权
            List<TcpApproveBean> approveList = tcpApproveDAO.queryEntityBeansByFK(id);

            boolean hasAuth = false;

            for (TcpApproveBean tcpApproveBean : approveList)
            {
                if (tcpApproveBean.getApproverId().equals(user.getStafferId()))
                {
                    hasAuth = true;

                    break;
                }
            }

            if ( !hasAuth)
            {
                return ActionTools.toError("没有处理的权限", mapping, request);
            }

            // 获得当前的处理环节
            TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());

            request.setAttribute("token", token);

            if (token.getNextPlugin().startsWith("group"))
            {
                // 群组
                request.setAttribute("pluginType", "group");

                request.setAttribute("pluginValue", token.getNextPlugin().substring(6));
            }
            else
            {
                request.setAttribute("pluginType", "");
                request.setAttribute("pluginValue", "");
            }

            return mapping.findForward("processTravelApply" + bean.getType());
        }

        return mapping.findForward("detailTravelApply" + bean.getType());
    }

    /**
     * downAttachmentFile
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public ActionForward downAttachmentFile(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        String path = getAttachmentPath();

        String id = request.getParameter("id");

        AttachmentBean bean = attachmentDAO.find(id);

        if (bean == null)
        {
            return ActionTools.toError(mapping, request);
        }

        path += bean.getPath();

        File file = new File(path);

        OutputStream out = response.getOutputStream();

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename="
                                                  + StringTools.getStringBySet(bean.getName(),
                                                      "GBK", "ISO8859-1"));

        UtilStream us = new UtilStream(new FileInputStream(file), out);

        us.copyAndCloseStream();

        return null;
    }

    /**
     * addTravelApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addOrUpdateTravelApply(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
        TravelApplyBean bean = new TravelApplyBean();

        // 模板最多10M
        RequestDataStream rds = new RequestDataStream(request, 1024 * 1024 * 10L);

        try
        {
            rds.parser();
        }
        catch (FileUploadBase.SizeLimitExceededException e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:附件超过10M");

            return mapping.findForward("error");
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败");

            return mapping.findForward("error");
        }

        BeanUtil.getBean(bean, rds.getParmterMap());

        String addOrUpdate = rds.getParameter("addOrUpdate");

        String oprType = rds.getParameter("oprType");

        String processId = rds.getParameter("processId");

        // 出差申请的特殊处理
        if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_TRAVEL)
        {
            changeTravel(bean, rds);
        }

        ActionForward afor = parserAttachment(mapping, request, rds, bean);

        if (afor != null)
        {
            return afor;
        }

        rds.close();

        // 子项的组装
        fillTravel(rds, bean);

        try
        {
            User user = Helper.getUser(request);

            bean.setLogTime(TimeTools.now());

            if ("0".equals(addOrUpdate))
            {
                travelApplyManager.addTravelApplyBean(user, bean);
            }
            else
            {
                travelApplyManager.updateTravelApplyBean(user, bean);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功保存费用申请");

            // 提交
            if ("1".equals(oprType))
            {
                travelApplyManager.submitTravelApplyBean(user, bean.getId(), processId);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功提交费用申请");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作费用申请失败:" + e.getMessage());
        }

        return mapping.findForward("querySelfTravelApply" + bean.getType());
    }

    /**
     * rptQueryTravelApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryTravelApply(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String cacheKey = RPTQUERYTRAVELAPPLY;

        final User user = Helper.getUser(request);

        List<TravelApplyVO> voList = ActionTools.commonQueryInPageSeparate(cacheKey, request,
            this.travelApplyDAO, new HandleQueryCondition()
            {
                public void setQueryCondition(HttpServletRequest request, ConditionParse condtion)
                {
                    String name = request.getParameter("name");

                    String id = request.getParameter("id");

                    String type = request.getParameter("type");

                    String stype = request.getParameter("stype");

                    if ( !StringTools.isNullOrNone(name))
                    {
                        condtion.addCondition("TravelApplyBean.name", "like", name);
                    }

                    if ( !StringTools.isNullOrNone(id))
                    {
                        condtion.addCondition("TravelApplyBean.id", "like", id);
                    }

                    if ( !StringTools.isNullOrNone(stype))
                    {
                        condtion.addIntCondition("TravelApplyBean.stype", "=", stype);
                    }

                    // 查询自己借款的单据
                    condtion.addCondition("TravelApplyBean.borrowStafferId", "=", user
                        .getStafferId());

                    // 查询结束(申请或借款,到财务支付,流程就算结束)
                    condtion.addIntCondition("TravelApplyBean.status", ">=",
                        TcpConstanst.TCP_STATUS_LAST_CHECK);

                    if ( !StringTools.isNullOrNone(type))
                    {
                        if ("98".equals(type))
                        {
                            condtion.addCondition("and TravelApplyBean.type in (2, 3)");
                        }
                        else
                        {
                            condtion.addIntCondition("TravelApplyBean.type", "=", type);
                        }
                    }

                    condtion.addIntCondition("TravelApplyBean.feedback", "=",
                        TcpConstanst.TCP_APPLY_FEEDBACK_NO);
                }
            });

        for (TravelApplyVO travelApplyVO : voList)
        {
            TCPHelper.chageVO(travelApplyVO);
        }

        return mapping.findForward(cacheKey);
    }

    /**
     * 处理
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward processTravelApplyBean(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");
        String oprType = request.getParameter("oprType");
        String reason = request.getParameter("reason");
        String processId = request.getParameter("processId");
        try
        {
            User user = Helper.getUser(request);

            TcpParamWrap param = new TcpParamWrap();

            param.setId(id);
            param.setType(oprType);
            param.setReason(reason);
            param.setProcessId(processId);

            // 组装参数
            fillWrap(request, param);

            // 提交
            if ("0".equals(oprType))
            {
                travelApplyManager.passTravelApplyBean(user, param);
            }
            else
            {
                travelApplyManager.rejectTravelApplyBean(user, param);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功处理借款申请");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理借款申请失败:" + e.getMessage());
        }

        return mapping.findForward("querySelfApprove");
    }

    /**
     * fillWrap
     * 
     * @param request
     * @param param
     * @throws MYException
     */
    private void fillWrap(HttpServletRequest request, TcpParamWrap param)
        throws MYException
    {
        String[] ppid = request.getParameterValues("p_cid");

        // 稽核处理
        if (ppid != null && ppid.length > 0)
        {
            String[] pcmoneysList = request.getParameterValues("p_cmoneys");
            String[] pcdescriptionList = request.getParameterValues("p_cdescription");
            List<TravelApplyPayBean> payList = travelApplyPayDAO
                .queryEntityBeansByFK(param.getId());
            for (int i = 0; i < ppid.length; i++ )
            {
                for (TravelApplyPayBean travelApplyPayBean : payList)
                {
                    if (travelApplyPayBean.getId().equals(ppid[i]))
                    {
                        travelApplyPayBean.setCmoneys(MathTools.doubleToLong2(pcmoneysList[i]));
                        travelApplyPayBean.setCdescription(pcdescriptionList[i]);
                    }
                }
            }

            param.setOther(payList);
        }

        // 处理采购货比三家
        String[] cids = request.getParameterValues("i_cid");

        if (cids != null && cids.length > 0)
        {
            String[] checkPrices = request.getParameterValues("i_checkPrices");
            String[] moneys = request.getParameterValues("i_moneys");
            String[] purpose = request.getParameterValues("i_purpose");

            List<TravelApplyItemBean> list = new ArrayList();

            long m1 = 0L;
            for (int i = 0; i < cids.length; i++ )
            {
                if (StringTools.isNullOrNone(cids[i]))
                {
                    continue;
                }

                TravelApplyItemBean item = new TravelApplyItemBean();

                item.setId(cids[i]);
                item.setCheckPrices(MathTools.doubleToLong2(checkPrices[i]));
                item.setMoneys(MathTools.doubleToLong2(moneys[i]));
                item.setPurpose(purpose[i]);

                m1 += item.getMoneys();

                list.add(item);
            }

            param.setOther2(list);
        }

        String[] bankIds = request.getParameterValues("bankId");

        // 财务付款
        if (bankIds != null && bankIds.length > 0)
        {
            String[] payTypes = request.getParameterValues("payType");
            String[] moneys = request.getParameterValues("money");

            List<OutBillBean> outBillList = new ArrayList<OutBillBean>();

            for (int i = 0; i < bankIds.length; i++ )
            {
                if (StringTools.isNullOrNone(bankIds[i]))
                {
                    continue;
                }

                OutBillBean outBill = new OutBillBean();

                outBill.setBankId(bankIds[i]);

                outBill.setPayType(MathTools.parseInt(payTypes[i]));

                outBill.setMoneys(MathTools.parseDouble(moneys[i]));

                outBillList.add(outBill);
            }

            param.setOther(outBillList);
        }

        String dutyId = request.getParameter("dutyId");

        if (dutyId != null)
        {
            param.setDutyId(dutyId);
        }
    }

    /**
     * 认领
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward drawApprove(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String ids = request.getParameter("ids");

            User user = Helper.getUser(request);

            String[] splits = ids.split(";");

            for (String id : splits)
            {
                if ( !StringTools.isNullOrNone(id))
                {
                    tcpFlowManager.drawApprove(user, id);
                }
            }

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * 退领
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward odrawApprove(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            tcpFlowManager.odrawApprove(user, id);

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * exportTCP
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward exportTCP(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        OutputStream out = null;

        String filenName = "TCP_" + TimeTools.now("MMddHHmmss") + ".csv";

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        ConditionParse condtion = JSONPageSeparateTools.getCondition(request, QUERYSELFAPPROVE);

        int count = this.tcpApproveDAO.countVOByCondition(condtion.toString());

        try
        {
            out = response.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            write.writeLine("日期,标识,目的,申请人,系列,单据类型,部门,付款类型,申请费用,稽核费用");

            PageSeparate page = new PageSeparate();

            page.reset2(count, 2000);

            WriteFileBuffer line = new WriteFileBuffer(write);

            while (page.nextPage())
            {
                List<TcpApproveVO> voFList = tcpApproveDAO
                    .queryEntityVOsByCondition(condtion, page);

                for (TcpApproveVO vo : voFList)
                {
                    TCPHelper.getTcpApproveVO(vo);

                    line.reset();

                    line.writeColumn("[" + vo.getLogTime() + "]");
                    line.writeColumn(vo.getId());
                    line.writeColumn(StringTools.getExportString(vo.getName()));
                    line.writeColumn(vo.getApplyerName());
                    line.writeColumn(ElTools.get("tcpStype", vo.getStype()));

                    line.writeColumn(ElTools.get("tcpType", vo.getType()));
                    line.writeColumn(changeString(vo.getDepartmentName()));
                    line.writeColumn(ElTools.get("tcpPayType", vo.getPayType()));
                    line.writeColumn(changeString(vo.getShowTotal()));
                    line.writeColumn(changeString(vo.getShowCheckTotal()));

                    line.writeLine();
                }
            }

            write.close();
        }
        catch (Throwable e)
        {
            _logger.error(e, e);

            return null;
        }
        finally
        {
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e1)
                {
                }
            }

            if (write != null)
            {

                try
                {
                    write.close();
                }
                catch (IOException e1)
                {
                }
            }
        }

        return null;
    }

    private String changeString(String str)
    {
        return str.replaceAll(",", "");
    }

    /**
     * 变成long
     * 
     * @param bean
     * @param rds
     */
    private void changeTravel(TravelApplyBean bean, RequestDataStream rds)
    {
        String airplaneCharges = rds.getParameter("airplaneCharges");

        bean.setAirplaneCharges(TCPHelper.doubleToLong2(airplaneCharges));

        String trainCharges = rds.getParameter("trainCharges");

        bean.setTrainCharges(TCPHelper.doubleToLong2(trainCharges));

        String busCharges = rds.getParameter("busCharges");

        bean.setBusCharges(TCPHelper.doubleToLong2(busCharges));

        String hotelCharges = rds.getParameter("hotelCharges");

        bean.setHotelCharges(TCPHelper.doubleToLong2(hotelCharges));

        String entertainCharges = rds.getParameter("entertainCharges");

        bean.setEntertainCharges(TCPHelper.doubleToLong2(entertainCharges));

        String allowanceCharges = rds.getParameter("allowanceCharges");

        bean.setAllowanceCharges(TCPHelper.doubleToLong2(allowanceCharges));

        String other1Charges = rds.getParameter("other1Charges");

        bean.setOther1Charges(TCPHelper.doubleToLong2(other1Charges));

        String other2Charges = rds.getParameter("other2Charges");

        bean.setOther2Charges(TCPHelper.doubleToLong2(other2Charges));
    }

    private long getTravelItemTotal(TravelApplyBean bean)
    {
        long total = 0L;

        total += bean.getAirplaneCharges();
        total += bean.getTrainCharges();

        total += bean.getBusCharges();
        total += bean.getHotelCharges();

        total += bean.getEntertainCharges();
        total += bean.getAllowanceCharges();

        total += bean.getOther1Charges();
        total += bean.getOther2Charges();

        return total;
    }

    /**
     * fillTravel
     * 
     * @param rds
     * @param bean
     */
    private void fillTravel(RequestDataStream rds, TravelApplyBean bean)
    {
        // 费用明细
        List<TravelApplyItemBean> itemList = new ArrayList<TravelApplyItemBean>();

        bean.setItemList(itemList);

        // i_beginDate
        List<String> beginDateList = rds.getParameters("i_beginDate");
        List<String> productNameList = rds.getParameters("i_productName");
        List<String> amountList = rds.getParameters("i_amount");
        List<String> pricesList = rds.getParameters("i_prices");
        List<String> endDateList = rds.getParameters("i_endDate");
        List<String> feeItemList = rds.getParameters("i_feeItem");
        List<String> moneysList = rds.getParameters("i_moneys");
        List<String> descriptionList = rds.getParameters("i_description");

        // 存在没有的可能
        if (feeItemList != null && feeItemList.size() > 0)
        {
            for (int i = 0; i < feeItemList.size(); i++ )
            {
                String each = feeItemList.get(i);

                if (StringTools.isNullOrNone(each))
                {
                    continue;
                }

                // 过滤差旅费
                if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_TRAVEL
                    && BudgetConstant.FEE_ITEM_TRAVELLING.equals(each))
                {
                    continue;
                }

                TravelApplyItemBean item = new TravelApplyItemBean();

                if (beginDateList != null && beginDateList.size() > 0)
                {
                    item.setBeginDate(beginDateList.get(i));
                }

                if (endDateList != null && endDateList.size() > 0)
                {
                    item.setEndDate(endDateList.get(i));
                }

                if (productNameList != null && productNameList.size() > 0)
                {
                    item.setProductName(productNameList.get(i));
                }

                if (amountList != null && amountList.size() > 0)
                {
                    item.setAmount(MathTools.parseInt(amountList.get(i)));
                }

                if (pricesList != null && pricesList.size() > 0)
                {
                    item.setPrices(MathTools.doubleToLong2(pricesList.get(i)));
                }

                item.setFeeItemId(feeItemList.get(i));
                item.setMoneys(TCPHelper.doubleToLong2(moneysList.get(i)));
                item.setDescription(descriptionList.get(i));

                itemList.add(item);
            }
        }

        // 特殊处理
        if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_TRAVEL)
        {
            // 自动组装差旅费
            TravelApplyItemBean travelItem = new TravelApplyItemBean();
            travelItem.setBeginDate(bean.getBeginDate());
            travelItem.setEndDate(bean.getEndDate());
            travelItem.setFeeItemId(BudgetConstant.FEE_ITEM_TRAVELLING);
            travelItem.setMoneys(getTravelItemTotal(bean));
            travelItem.setDescription("系统自动组装的差旅费");

            itemList.add(0, travelItem);
        }

        // 总费用
        long total = 0L;

        for (TravelApplyItemBean each : itemList)
        {
            total += each.getMoneys();
        }

        bean.setTotal(total);

        // 采购默认全部借款
        if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_STOCK)
        {
            bean.setBorrowTotal(bean.getTotal());
        }

        List<TravelApplyPayBean> payList = new ArrayList<TravelApplyPayBean>();

        bean.setPayList(payList);

        // 收款明细
        if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_YES)
        {
            List<String> receiveTypeList = rds.getParameters("p_receiveType");
            List<String> bankList = rds.getParameters("p_bank");
            List<String> userNameList = rds.getParameters("p_userName");
            List<String> bankNoList = rds.getParameters("p_bankNo");
            List<String> pmoneysList = rds.getParameters("p_moneys");
            List<String> pdescriptionList = rds.getParameters("p_description");

            if (receiveTypeList != null && receiveTypeList.size() > 0)
            {

                for (int i = 0; i < receiveTypeList.size(); i++ )
                {
                    String each = receiveTypeList.get(i);

                    if (StringTools.isNullOrNone(each))
                    {
                        continue;
                    }

                    TravelApplyPayBean pay = new TravelApplyPayBean();

                    pay.setReceiveType(MathTools.parseInt(receiveTypeList.get(i)));
                    pay.setBankName(bankList.get(i));
                    pay.setUserName(userNameList.get(i));
                    pay.setBankNo(bankNoList.get(i));
                    pay.setMoneys(TCPHelper.doubleToLong2(pmoneysList.get(i)));
                    pay.setDescription(pdescriptionList.get(i));

                    payList.add(pay);
                }

                long paytotal = 0L;

                for (TravelApplyPayBean each : payList)
                {
                    paytotal += each.getMoneys();
                }

                bean.setBorrowTotal(paytotal);
            }
        }

        // 费用分担
        List<TcpShareBean> shareList = new ArrayList<TcpShareBean>();

        bean.setShareList(shareList);

        List<String> budgetIdeList = rds.getParameters("s_budgetId");
        List<String> departmentIdList = rds.getParameters("s_departmentId");
        List<String> approverIdList = rds.getParameters("s_approverId");
        List<String> ratioList = rds.getParameters("s_ratio");

        int rtotal = 0;

        for (String each : ratioList)
        {
            rtotal += MathTools.parseInt(each);
        }

        int shareType = 0;

        if (rtotal != 100)
        {
            shareType = 1;
        }

        for (int i = 0; i < budgetIdeList.size(); i++ )
        {
            String each = budgetIdeList.get(i);

            if (StringTools.isNullOrNone(each))
            {
                continue;
            }

            TcpShareBean share = new TcpShareBean();

            share.setBudgetId(budgetIdeList.get(i));
            share.setDepartmentId(departmentIdList.get(i));
            share.setApproverId(approverIdList.get(i));

            // 自动识别是分担还是金额
            if (shareType == 0)
            {
                share.setRatio(MathTools.parseInt(ratioList.get(i)));
            }
            else
            {
                share.setRealMonery(TCPHelper.doubleToLong2(ratioList.get(i)));
            }

            shareList.add(share);
        }
    }

    /**
     * parserAttachment
     * 
     * @param mapping
     * @param request
     * @param rds
     * @param bean
     * @return
     */
    private ActionForward parserAttachment(ActionMapping mapping, HttpServletRequest request,
                                           RequestDataStream rds, TravelApplyBean travelApply)
    {
        List<AttachmentBean> attachmentList = new ArrayList<AttachmentBean>();

        travelApply.setAttachmentList(attachmentList);

        String addOrUpdate = rds.getParameter("addOrUpdate");

        // 更新新加入之前
        if ("1".equals(addOrUpdate))
        {
            String attacmentIds = rds.getParameter("attacmentIds");

            String[] split = attacmentIds.split(";");

            for (String each : split)
            {
                if (StringTools.isNullOrNone(each))
                {
                    continue;
                }

                AttachmentBean att = attachmentDAO.find(each);

                if (att != null)
                {
                    attachmentList.add(att);
                }
            }
        }

        // parser attachment
        if ( !rds.haveStream())
        {
            return null;
        }

        Map<String, InputStream> streamMap = rds.getStreamMap();

        for (Map.Entry<String, InputStream> entry : streamMap.entrySet())
        {
            AttachmentBean bean = new AttachmentBean();

            FileOutputStream out = null;

            UtilStream ustream = null;

            try
            {
                String savePath = mkdir(this.getAttachmentPath());

                String fileAlais = SequenceTools.getSequence();

                String fileName = FileTools.getFileName(rds.getFileName(entry.getKey()));

                String rabsPath = '/' + savePath + '/' + fileAlais + "."
                                  + FileTools.getFilePostfix(fileName).toLowerCase();

                String filePath = this.getAttachmentPath() + '/' + rabsPath;

                bean.setName(fileName);

                bean.setPath(rabsPath);

                bean.setLogTime(TimeTools.now());

                out = new FileOutputStream(filePath);

                ustream = new UtilStream(entry.getValue(), out);

                ustream.copyStream();

                attachmentList.add(bean);
            }
            catch (IOException e)
            {
                _logger.error(e, e);

                request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存失败");

                return mapping.findForward("querySelfTravelApply");
            }
            finally
            {
                if (ustream != null)
                {
                    try
                    {
                        ustream.close();
                    }
                    catch (IOException e)
                    {
                        _logger.error(e, e);
                    }
                }
            }
        }

        return null;
    }

    private String mkdir(String root)
    {
        String path = TimeTools.now("yyyy/MM/dd/HH") + "/"
                      + SequenceTools.getSequence(String.valueOf(new Random().nextInt(1000)));

        FileTools.mkdirs(root + '/' + path);

        return path;
    }

    /**
     * @return the flowAtt
     */
    public String getAttachmentPath()
    {
        return ConfigLoader.getProperty("tcpAttachmentPath");
    }

    /**
     * @return the travelApplyManager
     */
    public TravelApplyManager getTravelApplyManager()
    {
        return travelApplyManager;
    }

    /**
     * @param travelApplyManager
     *            the travelApplyManager to set
     */
    public void setTravelApplyManager(TravelApplyManager travelApplyManager)
    {
        this.travelApplyManager = travelApplyManager;
    }

    /**
     * @return the tcpApplyDAO
     */
    public TcpApplyDAO getTcpApplyDAO()
    {
        return tcpApplyDAO;
    }

    /**
     * @param tcpApplyDAO
     *            the tcpApplyDAO to set
     */
    public void setTcpApplyDAO(TcpApplyDAO tcpApplyDAO)
    {
        this.tcpApplyDAO = tcpApplyDAO;
    }

    /**
     * @return the tcpFlowDAO
     */
    public TcpFlowDAO getTcpFlowDAO()
    {
        return tcpFlowDAO;
    }

    /**
     * @param tcpFlowDAO
     *            the tcpFlowDAO to set
     */
    public void setTcpFlowDAO(TcpFlowDAO tcpFlowDAO)
    {
        this.tcpFlowDAO = tcpFlowDAO;
    }

    /**
     * @return the tcpPrepaymentDAO
     */
    public TcpPrepaymentDAO getTcpPrepaymentDAO()
    {
        return tcpPrepaymentDAO;
    }

    /**
     * @param tcpPrepaymentDAO
     *            the tcpPrepaymentDAO to set
     */
    public void setTcpPrepaymentDAO(TcpPrepaymentDAO tcpPrepaymentDAO)
    {
        this.tcpPrepaymentDAO = tcpPrepaymentDAO;
    }

    /**
     * @return the tcpShareDAO
     */
    public TcpShareDAO getTcpShareDAO()
    {
        return tcpShareDAO;
    }

    /**
     * @param tcpShareDAO
     *            the tcpShareDAO to set
     */
    public void setTcpShareDAO(TcpShareDAO tcpShareDAO)
    {
        this.tcpShareDAO = tcpShareDAO;
    }

    /**
     * @return the travelApplyDAO
     */
    public TravelApplyDAO getTravelApplyDAO()
    {
        return travelApplyDAO;
    }

    /**
     * @param travelApplyDAO
     *            the travelApplyDAO to set
     */
    public void setTravelApplyDAO(TravelApplyDAO travelApplyDAO)
    {
        this.travelApplyDAO = travelApplyDAO;
    }

    /**
     * @return the travelApplyItemDAO
     */
    public TravelApplyItemDAO getTravelApplyItemDAO()
    {
        return travelApplyItemDAO;
    }

    /**
     * @param travelApplyItemDAO
     *            the travelApplyItemDAO to set
     */
    public void setTravelApplyItemDAO(TravelApplyItemDAO travelApplyItemDAO)
    {
        this.travelApplyItemDAO = travelApplyItemDAO;
    }

    /**
     * @return the travelApplyPayDAO
     */
    public TravelApplyPayDAO getTravelApplyPayDAO()
    {
        return travelApplyPayDAO;
    }

    /**
     * @param travelApplyPayDAO
     *            the travelApplyPayDAO to set
     */
    public void setTravelApplyPayDAO(TravelApplyPayDAO travelApplyPayDAO)
    {
        this.travelApplyPayDAO = travelApplyPayDAO;
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
     * @return the attachmentDAO
     */
    public AttachmentDAO getAttachmentDAO()
    {
        return attachmentDAO;
    }

    /**
     * @param attachmentDAO
     *            the attachmentDAO to set
     */
    public void setAttachmentDAO(AttachmentDAO attachmentDAO)
    {
        this.attachmentDAO = attachmentDAO;
    }

    /**
     * @return the flowLogDAO
     */
    public FlowLogDAO getFlowLogDAO()
    {
        return flowLogDAO;
    }

    /**
     * @param flowLogDAO
     *            the flowLogDAO to set
     */
    public void setFlowLogDAO(FlowLogDAO flowLogDAO)
    {
        this.flowLogDAO = flowLogDAO;
    }

    /**
     * @return the tcpApproveDAO
     */
    public TcpApproveDAO getTcpApproveDAO()
    {
        return tcpApproveDAO;
    }

    /**
     * @param tcpApproveDAO
     *            the tcpApproveDAO to set
     */
    public void setTcpApproveDAO(TcpApproveDAO tcpApproveDAO)
    {
        this.tcpApproveDAO = tcpApproveDAO;
    }

    /**
     * @return the tcpFlowManager
     */
    public TcpFlowManager getTcpFlowManager()
    {
        return tcpFlowManager;
    }

    /**
     * @param tcpFlowManager
     *            the tcpFlowManager to set
     */
    public void setTcpFlowManager(TcpFlowManager tcpFlowManager)
    {
        this.tcpFlowManager = tcpFlowManager;
    }

    /**
     * @return the outBillDAO
     */
    public OutBillDAO getOutBillDAO()
    {
        return outBillDAO;
    }

    /**
     * @param outBillDAO
     *            the outBillDAO to set
     */
    public void setOutBillDAO(OutBillDAO outBillDAO)
    {
        this.outBillDAO = outBillDAO;
    }

    /**
     * @return the financeDAO
     */
    public FinanceDAO getFinanceDAO()
    {
        return financeDAO;
    }

    /**
     * @param financeDAO
     *            the financeDAO to set
     */
    public void setFinanceDAO(FinanceDAO financeDAO)
    {
        this.financeDAO = financeDAO;
    }

    /**
     * @return the tcpHandleHisDAO
     */
    public TcpHandleHisDAO getTcpHandleHisDAO()
    {
        return tcpHandleHisDAO;
    }

    /**
     * @param tcpHandleHisDAO
     *            the tcpHandleHisDAO to set
     */
    public void setTcpHandleHisDAO(TcpHandleHisDAO tcpHandleHisDAO)
    {
        this.tcpHandleHisDAO = tcpHandleHisDAO;
    }

    /**
     * @return the expenseApplyDAO
     */
    public ExpenseApplyDAO getExpenseApplyDAO()
    {
        return expenseApplyDAO;
    }

    /**
     * @param expenseApplyDAO
     *            the expenseApplyDAO to set
     */
    public void setExpenseApplyDAO(ExpenseApplyDAO expenseApplyDAO)
    {
        this.expenseApplyDAO = expenseApplyDAO;
    }

    /**
     * @return the dutyDAO
     */
    public DutyDAO getDutyDAO()
    {
        return dutyDAO;
    }

    /**
     * @param dutyDAO
     *            the dutyDAO to set
     */
    public void setDutyDAO(DutyDAO dutyDAO)
    {
        this.dutyDAO = dutyDAO;
    }

}
