/**
 * File Name: CityConfigAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-1-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.examine.action;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.china.center.oa.constant.AuthConstant;
import com.china.center.oa.constant.ExamineConstant;
import com.china.center.oa.constant.StafferConstant;
import com.china.center.oa.constant.SysConfigConstant;
import com.china.center.oa.examine.bean.AbstractExamineItem;
import com.china.center.oa.examine.bean.CityProfitBean;
import com.china.center.oa.examine.bean.CityProfitExamineBean;
import com.china.center.oa.examine.bean.ExamineBean;
import com.china.center.oa.examine.bean.NewCustomerExamineBean;
import com.china.center.oa.examine.bean.OldCustomerExamineBean;
import com.china.center.oa.examine.bean.ProfitExamineBean;
import com.china.center.oa.examine.dao.CityProfitDAO;
import com.china.center.oa.examine.dao.CityProfitExamineDAO;
import com.china.center.oa.examine.dao.ExamineDAO;
import com.china.center.oa.examine.dao.NewCustomerExamineDAO;
import com.china.center.oa.examine.dao.OldCustomerExamineDAO;
import com.china.center.oa.examine.dao.ProfitExamineDAO;
import com.china.center.oa.examine.helper.ExamineHelper;
import com.china.center.oa.examine.vo.CityProfitExamineVO;
import com.china.center.oa.examine.vo.ExamineVO;
import com.china.center.oa.facade.ExamineFacade;
import com.china.center.oa.helper.Helper;
import com.china.center.oa.publics.User;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.LocationDAO;
import com.china.center.oa.publics.dao.LocationVSCityDAO;
import com.china.center.oa.publics.dao.LogDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.publics.vo.LocationVSCityVO;
import com.china.center.oa.publics.vo.LogVO;
import com.china.center.tools.ActionTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.JSONTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.url.ajax.json.JSONArray;


/**
 * ExamineAction
 * 
 * @author zhuzhu
 * @version 2009-1-3
 * @see ExamineAction
 * @since 1.0
 */
public class ExamineAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private NewCustomerExamineDAO newCustomerExamineDAO = null;

    private OldCustomerExamineDAO oldCustomerExamineDAO = null;

    private ProfitExamineDAO profitExamineDAO = null;

    private CityProfitExamineDAO cityProfitExamineDAO = null;

    private CityProfitDAO cityProfitDAO = null;

    private LocationVSCityDAO locationVSCityDAO = null;

    private ExamineFacade examineFacade = null;

    private ExamineDAO examineDAO = null;

    private StafferDAO stafferDAO = null;

    private UserManager userManager = null;

    private ParameterDAO parameterDAO = null;

    private LocationDAO locationDAO = null;

    private LogDAO logDAO = null;

    private static String QUERYEXAMINE = "queryExamine";

    public ExamineAction()
    {}

    /**
     * ��ѯ����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryExamine(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        if (userManager.containAuth(user, AuthConstant.EXAMINE_OPR))
        {
            // condtion.addCondition("ExamineBean.createrId", "=", user.getStafferId());
        }
        else if (userManager.containAuth(user, AuthConstant.EXAMINE_UPDATE_COMMIT))
        {

        }
        else
        {
            condtion.addCondition("ExamineBean.stafferId", "=", user.getStafferId());

            condtion.addCondition("ExamineBean.status", ">", ExamineConstant.EXAMINE_STATUS_INIT);
        }

        ActionTools.processJSONQueryCondition(QUERYEXAMINE, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYEXAMINE, request, condtion,
            this.examineDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryProfitExamineForUpdate
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryProfitExamineForUpdate(ActionMapping mapping, ActionForm form,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        List<ProfitExamineBean> list = profitExamineDAO.queryEntityBeansByFK(id);

        String jsonstr = JSONTools.getJSONString(list);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * �޸�����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateProfitExamine(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String reason = request.getParameter("reason");

        String newProfit = request.getParameter("newProfit");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            examineFacade.updateProfitExamine(user.getId(), id, reason,
                Double.parseDouble(newProfit));

            ajax.setSuccess("�ɹ��޸Ŀ���");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("�޸Ŀ���ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * ��ѯ��������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findExamine(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String update = request.getParameter("update");

        ExamineVO bean = examineDAO.findVO(id);

        if (bean == null)
        {
            return ActionTools.toError("���˲�����", mapping, request);
        }

        handleStafferList(request);

        request.setAttribute("bean", bean);

        if (bean.getStatus() == ExamineConstant.EXAMINE_STATUS_SUBMIT
            || bean.getStatus() == ExamineConstant.EXAMINE_STATUS_PASS
            || bean.getStatus() == ExamineConstant.EXAMINE_STATUS_END)
        {}

        List<LocationBean> locationList = locationDAO.listEntityBeans();

        request.setAttribute("locationList", locationList);

        if ("1".equals(update))
        {
            return mapping.findForward("updateExamine");
        }

        return mapping.findForward("detailExamine");
    }

    /**
     * ����ְԱ(ֻ���ն˺���չ��ְԱ)
     * 
     * @param request
     */
    private void handleStafferList(HttpServletRequest request)
    {
        User user = Helper.getUser(request);

        List<StafferBean> stafferList = stafferDAO.queryStafferByLocationId(user.getLocationId());

        for (Iterator iterator = stafferList.iterator(); iterator.hasNext();)
        {
            StafferBean stafferBean = (StafferBean)iterator.next();

            if (stafferBean.getExamType() != StafferConstant.EXAMTYPE_EXPAND
                && stafferBean.getExamType() != StafferConstant.EXAMTYPE_TERMINAL)
            {
                iterator.remove();
            }
        }

        request.setAttribute("stafferList", stafferList);
    }

    /**
     * ��ѯ�¿ͻ�����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryNewCustomerExamine(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("pid");

        String look = request.getParameter("look");

        if (StringTools.isNullOrNone(id))
        {
            Object tep = request.getAttribute("pid");

            if (tep != null)
            {
                id = tep.toString();
            }
        }

        ExamineVO examine = examineDAO.findVO(id);

        if (examine == null)
        {
            return ActionTools.toError("���˲�����", mapping, request);
        }

        List<NewCustomerExamineBean> list = newCustomerExamineDAO.queryEntityBeansByFK(id);

        if (ListTools.isEmptyOrNull(list))
        {
            // �Զ���ÿ��˵�03-01��02-28������
            try
            {
                list = createDefaultCustomerExamine(id, examine.getYear(),
                    NewCustomerExamineBean.class);
            }
            catch (Exception e)
            {
                return ActionTools.toError("ϵͳ����", mapping, request);
            }
        }

        // ����
        Collections.sort(list, new Comparator<NewCustomerExamineBean>()
        {
            public int compare(NewCustomerExamineBean o1, NewCustomerExamineBean o2)
            {
                return o1.getStep() - o2.getStep();
            }
        });

        request.setAttribute("newList", list);

        request.setAttribute("examine", examine);

        CommonTools.saveParamers(request);

        // �鿴
        if ("1".equals(look))
        {
            int totalPlan = 0;

            int totalReal = 0;

            for (NewCustomerExamineBean oldCustomerExamineBean : list)
            {
                totalPlan += oldCustomerExamineBean.getPlanValue();

                totalReal += oldCustomerExamineBean.getRealValue();
            }

            request.setAttribute("totalPlan", totalPlan);
            request.setAttribute("totalReal", totalReal);

            return mapping.findForward("configNewCustomerExamine2");
        }
        else
        {
            // ����
            return mapping.findForward("configNewCustomerExamine");
        }
    }

    /**
     * ͨ����Ԫ����ʾ�ӿ��˵��¿ͻ�������Ϣ(����Ѿ��������readonly��ģʽ)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAllSubNewCustomerExamine(ActionMapping mapping, ActionForm form,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("pid");

        String look = request.getParameter("look");

        if (StringTools.isNullOrNone(id))
        {
            Object tep = request.getAttribute("pid");

            if (tep != null)
            {
                id = tep.toString();
            }
        }

        ExamineVO examine = examineDAO.findVO(id);

        if (examine == null)
        {
            return ActionTools.toError("���˲�����", mapping, request);
        }

        List<ExamineVO> subs = examineDAO.queryEntityVOsByFK(id);

        if (ListTools.isEmptyOrNull(subs))
        {
            return ActionTools.toError(examine.getName() + "û���ӿ���", mapping, request);
        }

        Map<String, List<NewCustomerExamineBean>> subMap = new HashMap<String, List<NewCustomerExamineBean>>();

        Map<String, Boolean> readonlyModal = new HashMap<String, Boolean>();

        for (ExamineBean examineBean : subs)
        {
            List<NewCustomerExamineBean> list = newCustomerExamineDAO.queryEntityBeansByFK(examineBean.getId());

            if (ListTools.isEmptyOrNull(list))
            {
                try
                {
                    list = createDefaultCustomerExamine(examineBean.getId(),
                        examineBean.getYear(), NewCustomerExamineBean.class);
                }
                catch (Exception e)
                {
                    return ActionTools.toError("ϵͳ����", mapping, request);
                }
            }

            // ����
            Collections.sort(list, new Comparator<NewCustomerExamineBean>()
            {
                public int compare(NewCustomerExamineBean o1, NewCustomerExamineBean o2)
                {
                    return o1.getStep() - o2.getStep();
                }
            });

            subMap.put(examineBean.getId(), list);

            // ֻ�г�ʼ�Ͳ��صĿ����޸�
            boolean readonly = ExamineHelper.isReadonly(examineBean.getStatus());

            readonlyModal.put(examineBean.getId(), readonly);
        }

        request.setAttribute("subMap", subMap);

        request.setAttribute("readonlyModal", readonlyModal);

        try
        {
            List<NewCustomerExamineBean> defaultList = createDefaultCustomerExamine("0",
                examine.getYear(), NewCustomerExamineBean.class);

            request.setAttribute("defaultList", defaultList);
        }
        catch (Exception e)
        {
            return ActionTools.toError("ϵͳ����", mapping, request);
        }

        request.setAttribute("parentBean", examine);

        request.setAttribute("subs", subs);

        CommonTools.saveParamers(request);

        // �鿴
        if ("1".equals(look))
        {
            return mapping.findForward("configAllNewCustomerExamine2");
        }
        else
        {
            // ����
            return mapping.findForward("configAllNewCustomerExamine");
        }
    }

    /**
     * ��ѯ�Ͽͻ�����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryOldCustomerExamine(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("pid");

        String look = request.getParameter("look");

        if (StringTools.isNullOrNone(id))
        {
            Object tep = request.getAttribute("pid");

            if (tep != null)
            {
                id = tep.toString();
            }
        }

        ExamineVO examine = examineDAO.findVO(id);

        if (examine == null)
        {
            return ActionTools.toError("���˲�����", mapping, request);
        }

        List<OldCustomerExamineBean> list = oldCustomerExamineDAO.queryEntityBeansByFK(id);

        if (ListTools.isEmptyOrNull(list))
        {
            // �Զ���ÿ��˵�03-01��02-28������
            try
            {
                list = createDefaultCustomerExamine(id, examine.getYear(),
                    OldCustomerExamineBean.class);
            }
            catch (Exception e)
            {
                return ActionTools.toError("ϵͳ����", mapping, request);
            }
        }

        // ����
        Collections.sort(list, new Comparator<OldCustomerExamineBean>()
        {
            public int compare(OldCustomerExamineBean o1, OldCustomerExamineBean o2)
            {
                return o1.getStep() - o2.getStep();
            }
        });

        request.setAttribute("oldList", list);

        request.setAttribute("examine", examine);

        CommonTools.saveParamers(request);

        // �鿴
        if ("1".equals(look))
        {
            int totalPlan = 0;
            int totalReal = 0;
            for (OldCustomerExamineBean oldCustomerExamineBean : list)
            {
                totalPlan += oldCustomerExamineBean.getPlanValue();

                totalReal += oldCustomerExamineBean.getRealValue();
            }

            request.setAttribute("totalPlan", totalPlan);
            request.setAttribute("totalReal", totalReal);

            return mapping.findForward("configOldCustomerExamine2");
        }
        else
        {
            // ����
            return mapping.findForward("configOldCustomerExamine");
        }
    }

    /**
     * ͨ����Ԫ����ʾ�ӿ��˵��Ͽͻ�������Ϣ(����Ѿ��������readonly��ģʽ)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAllSubOldCustomerExamine(ActionMapping mapping, ActionForm form,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("pid");

        String look = request.getParameter("look");

        if (StringTools.isNullOrNone(id))
        {
            Object tep = request.getAttribute("pid");

            if (tep != null)
            {
                id = tep.toString();
            }
        }

        ExamineVO examine = examineDAO.findVO(id);

        if (examine == null)
        {
            return ActionTools.toError("���˲�����", mapping, request);
        }

        List<ExamineVO> subs = examineDAO.queryEntityVOsByFK(id);

        if (ListTools.isEmptyOrNull(subs))
        {
            return ActionTools.toError(examine.getName() + "û���ӿ���", mapping, request);
        }

        Map<String, List<OldCustomerExamineBean>> subMap = new HashMap<String, List<OldCustomerExamineBean>>();

        Map<String, Boolean> readonlyModal = new HashMap<String, Boolean>();

        for (ExamineBean examineBean : subs)
        {
            List<OldCustomerExamineBean> list = oldCustomerExamineDAO.queryEntityBeansByFK(examineBean.getId());

            if (ListTools.isEmptyOrNull(list))
            {
                try
                {
                    list = createDefaultCustomerExamine(examineBean.getId(),
                        examineBean.getYear(), OldCustomerExamineBean.class);
                }
                catch (Exception e)
                {
                    return ActionTools.toError("ϵͳ����", mapping, request);
                }
            }

            // ����
            Collections.sort(list, new Comparator<AbstractExamineItem>()
            {
                public int compare(AbstractExamineItem o1, AbstractExamineItem o2)
                {
                    return o1.getStep() - o2.getStep();
                }
            });

            subMap.put(examineBean.getId(), list);

            // ֻ�г�ʼ�Ͳ��صĿ����޸�
            boolean readonly = ExamineHelper.isReadonly(examineBean.getStatus());

            readonlyModal.put(examineBean.getId(), readonly);
        }

        request.setAttribute("subMap", subMap);

        request.setAttribute("readonlyModal", readonlyModal);

        try
        {
            List<OldCustomerExamineBean> defaultList = createDefaultCustomerExamine("0",
                examine.getYear(), OldCustomerExamineBean.class);

            request.setAttribute("defaultList", defaultList);
        }
        catch (Exception e)
        {
            return ActionTools.toError("ϵͳ����", mapping, request);
        }

        request.setAttribute("parentBean", examine);

        request.setAttribute("subs", subs);

        CommonTools.saveParamers(request);

        // �鿴
        if ("1".equals(look))
        {
            return mapping.findForward("configAllOldCustomerExamine2");
        }
        else
        {
            // ����
            return mapping.findForward("configAllOldCustomerExamine");
        }
    }

    /**
     * ����--ë����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryProfitExamine(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request,
                                            HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("pid");

        String look = request.getParameter("look");

        if (StringTools.isNullOrNone(id))
        {
            Object tep = request.getAttribute("pid");

            if (tep != null)
            {
                id = tep.toString();
            }
        }

        ExamineVO examine = examineDAO.findVO(id);

        if (examine == null)
        {
            return ActionTools.toError("���˲�����", mapping, request);
        }

        List<ProfitExamineBean> list = profitExamineDAO.queryEntityBeansByFK(id);

        if (list.size() != 0 && list.size() != 12)
        {
            return ActionTools.toError("ҵ�����ݲ��걸,���ɾ������", mapping, request);
        }

        Collections.sort(list, new Comparator<ProfitExamineBean>()
        {
            public int compare(ProfitExamineBean o1, ProfitExamineBean o2)
            {
                return o1.getStep() - o2.getStep();
            }
        });

        // Ĭ������12����
        if (list.size() == 0)
        {
            createDefaultProfit(id, examine.getYear(), list);
        }

        request.setAttribute("newList", list);

        request.setAttribute("examine", examine);

        CommonTools.saveParamers(request);

        // �鿴
        if ("1".equals(look))
        {
            double totalPlan = 0;

            double totalReal = 0;

            for (ProfitExamineBean oldCustomerExamineBean : list)
            {
                totalPlan += oldCustomerExamineBean.getPlanValue();

                totalReal += oldCustomerExamineBean.getRealValue();
            }

            request.setAttribute("totalPlan", MathTools.formatNum(totalPlan));

            request.setAttribute("totalReal", MathTools.formatNum(totalReal));

            return mapping.findForward("configProfitExamine2");
        }
        else
        {
            // ����
            return mapping.findForward("configProfitExamine");
        }
    }

    /**
     * ͨ����Ԫ����ʾ�ӿ��˵�������Ϣ(����Ѿ��������readonly��ģʽ)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAllSubProfitExamine(ActionMapping mapping, ActionForm form,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response)
        throws ServletException
    {
        String pid = request.getParameter("pid");

        String look = request.getParameter("look");

        if (StringTools.isNullOrNone(pid))
        {
            Object tep = request.getAttribute("pid");

            if (tep != null)
            {
                pid = tep.toString();
            }
        }

        ExamineVO examine = examineDAO.findVO(pid);

        if (examine == null)
        {
            return ActionTools.toError("���˲�����", mapping, request);
        }

        List<ExamineVO> subs = examineDAO.queryEntityVOsByFK(pid);

        if (ListTools.isEmptyOrNull(subs))
        {
            return ActionTools.toError(examine.getName() + "û���ӿ���", mapping, request);
        }

        Map<String, List<ProfitExamineBean>> subMap = new HashMap<String, List<ProfitExamineBean>>();

        Map<String, Boolean> readonlyModal = new HashMap<String, Boolean>();

        for (ExamineBean examineBean : subs)
        {
            List<ProfitExamineBean> list = profitExamineDAO.queryEntityBeansByFK(examineBean.getId());

            if (ListTools.isEmptyOrNull(list))
            {
                try
                {
                    createDefaultProfit(examineBean.getId(), examine.getYear(), list);
                }
                catch (Exception e)
                {
                    return ActionTools.toError("ϵͳ����", mapping, request);
                }
            }

            // ����
            Collections.sort(list, new Comparator<AbstractExamineItem>()
            {
                public int compare(AbstractExamineItem o1, AbstractExamineItem o2)
                {
                    return o1.getStep() - o2.getStep();
                }
            });

            subMap.put(examineBean.getId(), list);

            // ֻ�г�ʼ�Ͳ��صĿ����޸�
            boolean readonly = ExamineHelper.isReadonly(examineBean.getStatus());

            readonlyModal.put(examineBean.getId(), readonly);
        }

        request.setAttribute("subMap", subMap);

        request.setAttribute("readonlyModal", readonlyModal);

        // ����Ĭ�ϵ��·ݴ���
        List<ProfitExamineBean> defaultList = new ArrayList<ProfitExamineBean>();

        createDefaultProfit("0", examine.getYear(), defaultList);

        request.setAttribute("defaultList", defaultList);

        request.setAttribute("parentBean", examine);

        request.setAttribute("subs", subs);

        CommonTools.saveParamers(request);

        // �鿴
        if ("1".equals(look))
        {
            return mapping.findForward("configAllProfitExamine2");
        }
        else
        {
            // ����
            return mapping.findForward("configAllProfitExamine");
        }
    }

    /**
     * ����Ĭ�ϵ����󿼺�
     * 
     * @param id
     * @param examine
     * @param list
     */
    private void createDefaultProfit(String pid, int year, List<ProfitExamineBean> list)
    {
        String dataOfYear = parameterDAO.getString(SysConfigConstant.DATE_OF_YEAR);

        // ��ò���ʼ���·�
        int beginMonth = CommonTools.parseInt(dataOfYear.substring(0, 2));

        for (int step = 1; step <= 12; step++ )
        {
            // ������·���
            if (beginMonth > 12)
            {
                year++ ;

                beginMonth = 1;
            }

            int end = TimeTools.getDaysOfMonth(year, beginMonth);

            String smonth = "0" + beginMonth;

            smonth = smonth.substring(smonth.length() - 2);

            ProfitExamineBean pe = new ProfitExamineBean();

            pe.setStep(step);

            pe.setParentId(pid);

            pe.setBeginTime(year + "-" + smonth + "-01 00:00:00");

            pe.setEndTime(year + "-" + smonth + "-" + end + " 23:59:59");

            list.add(pe);

            beginMonth++ ;
        }
    }

    /**
     * ����Ĭ�ϵ�52����
     * 
     * @param pid
     * @param year
     * @param list
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private <T extends AbstractExamineItem> List<T> createDefaultCustomerExamine(String pid,
                                                                                 int year,
                                                                                 Class<T> claz)
        throws Exception
    {
        List<T> list = new ArrayList<T>();

        String dataOfYear = parameterDAO.getString(SysConfigConstant.DATE_OF_YEAR);

        // ��ò���ʼ���·�
        int beginMonth = CommonTools.parseInt(dataOfYear.substring(0, 2));

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, year);

        cal.set(Calendar.MONTH, beginMonth - 1);

        cal.set(Calendar.DAY_OF_MONTH, 1);

        // �����03-01������ȵĿ�ʼ����
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);

        String firstBeginWeek = year + "-" + dataOfYear;

        String firstEndWeek = "";
        // 1������ 7������ 2����һ 1������(1-7)
        if (dayOfWeek == 1)
        {
            firstEndWeek = TimeTools.getShortString(cal.getTimeInMillis());
        }
        else
        {
            int tempEnd = 8 - dayOfWeek;

            cal.set(Calendar.DAY_OF_YEAR, dayOfYear + tempEnd);

            firstEndWeek = TimeTools.getShortString(cal.getTimeInMillis());
        }

        T pe = claz.newInstance();

        pe.setStep(1);

        pe.setParentId(pid);

        pe.setBeginTime(firstBeginWeek + " 00:00:00");

        pe.setEndTime(firstEndWeek + " 23:59:59");

        list.add(pe);

        int nextYear = year + 1;

        int nextMonth = beginMonth - 1;

        String nextMonthStr = nextMonth > 10 ? String.valueOf(nextMonth) : "0" + nextMonth;

        int nextDay = TimeTools.getDaysOfMonth(nextYear, nextMonth);

        String lastEndDay = nextYear + "-" + nextMonthStr + "-" + nextDay;

        for (int step = 2; step <= 54; step++ )
        {
            int current = cal.get(Calendar.DAY_OF_YEAR);

            cal.set(Calendar.DAY_OF_YEAR, current + 1);

            String ibegin = TimeTools.getShortString(cal.getTimeInMillis());

            int rst = TimeTools.cdate(ibegin, lastEndDay);

            if (rst > 0)
            {
                break;
            }

            cal.set(Calendar.DAY_OF_YEAR, current + 7);

            String iend = TimeTools.getShortString(cal.getTimeInMillis());

            rst = TimeTools.cdate(iend, lastEndDay);

            if (rst >= 0)
            {
                iend = lastEndDay;
            }

            T each = claz.newInstance();

            each.setStep(step);

            each.setParentId(pid);

            each.setBeginTime(ibegin + " 00:00:00");

            each.setEndTime(iend + " 23:59:59");

            list.add(each);
        }

        return list;
    }

    /**
     * ��ѯ��������󿼺�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCityProfitExamine(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("pid");

        String look = request.getParameter("look");

        ExamineVO examine = examineDAO.findVO(id);

        if (examine == null)
        {
            return ActionTools.toError("���˲�����", mapping, request);
        }

        List<String> list = cityProfitExamineDAO.queryDistinctCityByparentId(id);

        List<LocationVSCityVO> llits = locationVSCityDAO.queryEntityVOsByFK(examine.getLocationId());

        request.setAttribute("list", list);

        JSONArray jarr = new JSONArray(list);

        request.setAttribute("listJSON", jarr.toString());

        jarr = new JSONArray(llits, true);

        request.setAttribute("llistJSON", jarr.toString());

        request.setAttribute("examine", examine);

        request.setAttribute("llits", llits);

        CommonTools.saveParamers(request);

        // �鿴
        if ("1".equals(look))
        {
            List<CityProfitExamineVO> cList = cityProfitExamineDAO.queryEntityVOsByFK(id);

            // ����
            Collections.sort(cList, new Comparator<AbstractExamineItem>()
            {
                public int compare(AbstractExamineItem o1, AbstractExamineItem o2)
                {
                    return StringTools.compare(o1.getBeginTime(), o2.getBeginTime());
                }
            });

            request.setAttribute("cList", cList);

            return mapping.findForward("configCityProfitExamine2");
        }
        else
        {
            // ����
            return mapping.findForward("configCityProfitExamine");
        }
    }

    /**
     * ͨ����Ԫ����ʾ�ӿ��˵�����������Ϣ(����Ѿ��������readonly��ģʽ)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAllSubCityProfitExamine(ActionMapping mapping, ActionForm form,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response)
        throws ServletException
    {
        String pid = request.getParameter("pid");

        String look = request.getParameter("look");

        if (StringTools.isNullOrNone(pid))
        {
            Object tep = request.getAttribute("pid");

            if (tep != null)
            {
                pid = tep.toString();
            }
        }

        ExamineVO examine = examineDAO.findVO(pid);

        if (examine == null)
        {
            return ActionTools.toError("���˲�����", mapping, request);
        }

        List<ExamineVO> subs = examineDAO.queryEntityVOsByFK(pid);

        if (ListTools.isEmptyOrNull(subs))
        {
            return ActionTools.toError(examine.getName() + "û���ӿ���", mapping, request);
        }

        Map<String, Boolean> readonlyModal = new HashMap<String, Boolean>();

        Map<String, String> listJSONMap = new HashMap<String, String>();

        List<LocationVSCityVO> llits = locationVSCityDAO.queryEntityVOsByFK(examine.getLocationId());

        request.setAttribute("llits", llits);

        JSONArray tt = new JSONArray(llits, true);

        request.setAttribute("llistJSON", tt.toString());

        for (ExamineVO examineBean : subs)
        {
            List<String> list = cityProfitExamineDAO.queryDistinctCityByparentId(examineBean.getId());

            JSONArray jarr = new JSONArray(list);

            listJSONMap.put(examineBean.getId(), jarr.toString());

            // ֻ�г�ʼ�Ͳ��صĿ����޸�
            boolean readonly = ExamineHelper.isReadonly(examineBean.getStatus());

            readonlyModal.put(examineBean.getId(), readonly);
        }

        request.setAttribute("listJSONMap", listJSONMap);

        request.setAttribute("readonlyModal", readonlyModal);

        request.setAttribute("parentBean", examine);

        request.setAttribute("subs", subs);

        CommonTools.saveParamers(request);

        // �鿴
        if ("1".equals(look))
        {
            List<CityProfitExamineVO> result = new ArrayList<CityProfitExamineVO>();

            for (ExamineVO examineBean : subs)
            {
                List<CityProfitExamineVO> tem = cityProfitExamineDAO.queryEntityVOsByFK(examineBean.getId());

                for (CityProfitExamineVO cityProfitExamineVO : tem)
                {
                    cityProfitExamineVO.setStafferName(examineBean.getStafferName());
                }

                result.addAll(tem);
            }

            // ����
            Collections.sort(result, new Comparator<AbstractExamineItem>()
            {
                public int compare(AbstractExamineItem o1, AbstractExamineItem o2)
                {
                    return StringTools.compare(o1.getBeginTime(), o2.getBeginTime());
                }
            });

            request.setAttribute("cList", result);

            return mapping.findForward("configAllCityProfitExamine2");
        }
        else
        {
            // ����
            return mapping.findForward("configAllCityProfitExamine");
        }
    }

    /**
     * �����¿ͻ��Ŀ�������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward configNewCustomerExamine(ActionMapping mapping, ActionForm form,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("pid");

        ExamineBean examine = examineDAO.find(id);

        if (examine == null)
        {
            return ActionTools.toError("���˲�����", mapping, request);
        }

        User user = Helper.getUser(request);

        List<NewCustomerExamineBean> nList = null;

        try
        {
            // ���Ĭ�ϵĹ���
            nList = createDefaultCustomerExamine(id, examine.getYear(),
                NewCustomerExamineBean.class);
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            return ActionTools.toError("ϵͳ����", mapping, request);
        }

        createNews(request, id, nList, false);

        request.setAttribute("pid", id);

        try
        {
            examineFacade.configNewCustomerExamine(user.getId(), id, nList);

            request.setAttribute(KeyConstant.MESSAGE, "�ɹ����濼����Ϣ");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "���濼����Ϣʧ��:" + e.getMessage());
        }

        return queryNewCustomerExamine(mapping, form, request, response);
    }

    /**
     * �������е��ӿ��˵��¿ͻ��Ŀ�������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward configAllNewCustomerExamine(ActionMapping mapping, ActionForm form,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response)
        throws ServletException
    {
        String pid = request.getParameter("pid");

        ExamineBean examine = examineDAO.find(pid);

        if (examine == null)
        {
            return ActionTools.toError("���˲�����", mapping, request);
        }

        User user = Helper.getUser(request);

        String[] subIds = request.getParameterValues("subItemId");

        String errorMsg = "";

        for (String eachItem : subIds)
        {
            ExamineBean eachBean = examineDAO.find(eachItem);

            if (eachBean == null)
            {
                return ActionTools.toError("�ӿ��˲�����", mapping, request);
            }

            // ֻ�г�ʼ�Ͳ��صĿ����޸�
            boolean readonly = ExamineHelper.isReadonly(eachBean.getStatus());

            // �����Ĳ��޸�
            if (readonly)
            {
                continue;
            }

            List<NewCustomerExamineBean> nList = null;

            try
            {
                // ���Ĭ�ϵĹ���
                nList = createDefaultCustomerExamine(eachItem, eachBean.getYear(),
                    NewCustomerExamineBean.class);
            }
            catch (Exception e1)
            {
                _logger.error(e1, e1);

                return ActionTools.toError("ϵͳ����", mapping, request);
            }

            createNews(request, eachItem, nList, true);

            try
            {
                examineFacade.configNewCustomerExamine(user.getId(), eachItem, nList);

                request.setAttribute(KeyConstant.MESSAGE, "�ɹ����濼����Ϣ");
            }
            catch (MYException e)
            {
                _logger.warn(e, e);

                errorMsg += "���濼����Ϣʧ��:" + e.getMessage() + " ";

            }
        }

        if ( !StringTools.isNullOrNone(errorMsg))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, errorMsg);
        }

        // �������
        CommonTools.removeParamers(request);

        request.setAttribute("pid", pid);

        return queryAllSubNewCustomerExamine(mapping, form, request, response);
    }

    /**
     * configOldCustomerExamine
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward configOldCustomerExamine(ActionMapping mapping, ActionForm form,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("pid");

        ExamineBean examine = examineDAO.find(id);

        if (examine == null)
        {
            return ActionTools.toError("���˲�����", mapping, request);
        }

        User user = Helper.getUser(request);

        List<OldCustomerExamineBean> nList = null;

        try
        {
            // ���Ĭ�ϵĹ���
            nList = createDefaultCustomerExamine(id, examine.getYear(),
                OldCustomerExamineBean.class);
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            return ActionTools.toError("ϵͳ����", mapping, request);
        }

        createOlds(request, id, nList, false);

        request.setAttribute("pid", id);

        try
        {
            examineFacade.configOldCustomerExamine(user.getId(), id, nList);

            request.setAttribute(KeyConstant.MESSAGE, "�ɹ����濼����Ϣ");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "���濼����Ϣʧ��:" + e.getMessage());
        }

        return queryOldCustomerExamine(mapping, form, request, response);
    }

    /**
     * �������е��ӿ��˵��Ͽͻ��Ŀ�������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward configAllOldCustomerExamine(ActionMapping mapping, ActionForm form,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response)
        throws ServletException
    {
        String pid = request.getParameter("pid");

        ExamineBean examine = examineDAO.find(pid);

        if (examine == null)
        {
            return ActionTools.toError("���˲�����", mapping, request);
        }

        User user = Helper.getUser(request);

        String[] subIds = request.getParameterValues("subItemId");

        String errorMsg = "";

        for (String eachItem : subIds)
        {
            ExamineBean eachBean = examineDAO.find(eachItem);

            if (eachBean == null)
            {
                return ActionTools.toError("�ӿ��˲�����", mapping, request);
            }

            // ֻ�г�ʼ�Ͳ��صĿ����޸�
            boolean readonly = ExamineHelper.isReadonly(eachBean.getStatus());

            // �����Ĳ��޸�
            if (readonly)
            {
                continue;
            }

            List<OldCustomerExamineBean> nList = null;

            try
            {
                // ���Ĭ�ϵĹ���
                nList = createDefaultCustomerExamine(eachItem, eachBean.getYear(),
                    OldCustomerExamineBean.class);
            }
            catch (Exception e1)
            {
                _logger.error(e1, e1);

                return ActionTools.toError("ϵͳ����", mapping, request);
            }

            createOlds(request, eachItem, nList, true);

            try
            {
                examineFacade.configOldCustomerExamine(user.getId(), eachItem, nList);

                request.setAttribute(KeyConstant.MESSAGE, "�ɹ����濼����Ϣ");
            }
            catch (MYException e)
            {
                _logger.warn(e, e);

                errorMsg += "���濼����Ϣʧ��:" + e.getMessage() + " ";

            }
        }

        if ( !StringTools.isNullOrNone(errorMsg))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, errorMsg);
        }

        // �������
        CommonTools.removeParamers(request);

        request.setAttribute("pid", pid);

        return queryAllSubOldCustomerExamine(mapping, form, request, response);
    }

    /**
     * ����ë����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward configProfitExamine(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("pid");

        ExamineBean examine = examineDAO.find(id);

        if (examine == null)
        {
            return ActionTools.toError("���˲�����", mapping, request);
        }

        User user = Helper.getUser(request);

        List<ProfitExamineBean> nList = new ArrayList<ProfitExamineBean>();

        if ( !createProfits(request, id, examine.getYear(), nList, false))
        {
            return ActionTools.toError("ҵ���������ݷǷ�,�����²���", mapping, request);
        }

        request.setAttribute("pid", id);

        try
        {
            examineFacade.configProfitExamine(user.getId(), id, nList);

            request.setAttribute(KeyConstant.MESSAGE, "�ɹ����濼����Ϣ");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "���濼����Ϣʧ��:" + e.getMessage());
        }

        return queryProfitExamine(mapping, form, request, response);
    }

    /**
     * �������е��ӿ��˵�����Ŀ�������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward configAllProfitExamine(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
        String pid = request.getParameter("pid");

        ExamineBean examine = examineDAO.find(pid);

        if (examine == null)
        {
            return ActionTools.toError("���˲�����", mapping, request);
        }

        User user = Helper.getUser(request);

        String[] subIds = request.getParameterValues("subItemId");

        String errorMsg = "";

        for (String eachItem : subIds)
        {
            ExamineBean eachBean = examineDAO.find(eachItem);

            if (eachBean == null)
            {
                return ActionTools.toError("�ӿ��˲�����", mapping, request);
            }

            // ֻ�г�ʼ�Ͳ��صĿ����޸�
            boolean readonly = ExamineHelper.isReadonly(eachBean.getStatus());

            // �����Ĳ��޸�
            if (readonly)
            {
                continue;
            }

            List<ProfitExamineBean> nList = new ArrayList<ProfitExamineBean>();

            if ( !createProfits(request, eachItem, examine.getYear(), nList, true))
            {
                return ActionTools.toError("ҵ���������ݷǷ�,�����²���", mapping, request);
            }

            try
            {
                examineFacade.configProfitExamine(user.getId(), eachItem, nList);

                request.setAttribute(KeyConstant.MESSAGE, "�ɹ����濼����Ϣ");
            }
            catch (MYException e)
            {
                _logger.warn(e, e);

                errorMsg += "���濼����Ϣʧ��:" + e.getMessage() + " ";

            }
        }

        if ( !StringTools.isNullOrNone(errorMsg))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, errorMsg);
        }

        // �������
        CommonTools.removeParamers(request);

        request.setAttribute("pid", pid);

        return queryAllSubProfitExamine(mapping, form, request, response);
    }

    /**
     * ��������ë����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward configCityProfitExamine(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("pid");

        ExamineBean examine = examineDAO.find(id);

        if (examine == null)
        {
            return ActionTools.toError("���˲�����", mapping, request);
        }

        User user = Helper.getUser(request);

        List<CityProfitExamineBean> nList = new ArrayList<CityProfitExamineBean>();

        createCityProfits(request, id, nList, examine, false);

        request.setAttribute("pid", id);

        try
        {
            examineFacade.configCityProfitExamine(user.getId(), id, nList);

            request.setAttribute(KeyConstant.MESSAGE, "�ɹ����濼����Ϣ");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "���濼����Ϣʧ��:" + e.getMessage());
        }

        return queryCityProfitExamine(mapping, form, request, response);
    }

    /**
     * �������е��ӿ��˵�����Ŀ�������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward configAllCityProfitExamine(ActionMapping mapping, ActionForm form,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response)
        throws ServletException
    {
        String pid = request.getParameter("pid");

        ExamineBean examine = examineDAO.find(pid);

        if (examine == null)
        {
            return ActionTools.toError("���˲�����", mapping, request);
        }

        User user = Helper.getUser(request);

        String[] subIds = request.getParameterValues("subItemId");

        String errorMsg = "";

        for (String eachItem : subIds)
        {
            ExamineBean eachBean = examineDAO.find(eachItem);

            if (eachBean == null)
            {
                return ActionTools.toError("�ӿ��˲�����", mapping, request);
            }

            // ֻ�г�ʼ�Ͳ��صĿ����޸�
            boolean readonly = ExamineHelper.isReadonly(eachBean.getStatus());

            // �����Ĳ��޸�
            if (readonly)
            {
                continue;
            }

            List<CityProfitExamineBean> nList = new ArrayList<CityProfitExamineBean>();

            createCityProfits(request, eachItem, nList, examine, true);

            try
            {
                examineFacade.configCityProfitExamine(user.getId(), eachItem, nList);

                request.setAttribute(KeyConstant.MESSAGE, "�ɹ����濼����Ϣ");
            }
            catch (MYException e)
            {
                _logger.warn(e, e);

                errorMsg += "���濼����Ϣʧ��:" + e.getMessage() + " ";

            }
        }

        if ( !StringTools.isNullOrNone(errorMsg))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, errorMsg);
        }

        // �������
        CommonTools.removeParamers(request);

        request.setAttribute("pid", pid);

        return queryAllSubCityProfitExamine(mapping, form, request, response);
    }

    /**
     * createNews
     * 
     * @param request
     * @param id
     * @param nList
     * @param byId
     *            realValue�Ƿ�����id�Ļ�ȡkey
     */
    private void createNews(HttpServletRequest request, String id,
                            List<NewCustomerExamineBean> nList, boolean byId)
    {
        String key = byId ? "realValue_" + id : "realValue";

        String[] rts = request.getParameterValues(key);

        if (nList.size() != rts.length)
        {
            return;
        }

        for (int i = 0; i < rts.length; i++ )
        {
            NewCustomerExamineBean nce = nList.get(i);

            nce.setParentId(id);

            nce.setPlanValue(CommonTools.parseInt(rts[i]));
        }
    }

    /**
     * createOlds
     * 
     * @param request
     * @param id
     * @param nList
     * @param byId
     *            realValue�Ƿ�����id�Ļ�ȡkey
     */
    private void createOlds(HttpServletRequest request, String id,
                            List<OldCustomerExamineBean> nList, boolean byId)
    {
        String key = byId ? "realValue_" + id : "realValue";

        String[] rts = request.getParameterValues(key);

        if (nList.size() != rts.length)
        {
            return;
        }

        for (int i = 0; i < rts.length; i++ )
        {
            OldCustomerExamineBean nce = nList.get(i);

            nce.setParentId(id);

            nce.setPlanValue(CommonTools.parseInt(rts[i]));
        }
    }

    /**
     * ����ҵ��������
     * 
     * @param request
     * @param id
     * @param year
     * @param nList
     * @param byId
     *            realValue�Ƿ�����id�Ļ�ȡkey
     * @return
     */
    private boolean createProfits(HttpServletRequest request, String id, int year,
                                  List<ProfitExamineBean> nList, boolean byId)
    {
        String key = byId ? "realValue_" + id : "realValue";

        String[] rts = request.getParameterValues(key);

        if (rts.length != 12)
        {
            return false;
        }

        createDefaultProfit(id, year, nList);

        for (int i = 0; i < nList.size(); i++ )
        {
            ProfitExamineBean nce = nList.get(i);

            nce.setParentId(id);

            nce.setPlanValue(CommonTools.parseFloat(rts[i]));
        }

        return true;
    }

    /**
     * ��֯�����ë����
     * 
     * @param request
     * @param id
     * @param nList
     * @param byId
     *            realValue�Ƿ�����id�Ļ�ȡkey
     */
    private void createCityProfits(HttpServletRequest request, String id,
                                   List<CityProfitExamineBean> nList, ExamineBean examine,
                                   boolean byId)
    {
        String key = byId ? "city_" + id : "city";

        String[] citys = request.getParameterValues(key);

        if (citys == null || citys.length == 0)
        {
            return;
        }

        for (int i = 0; i < citys.length; i++ )
        {
            if (StringTools.isNullOrNone(citys[i]))
            {
                continue;
            }

            List<CityProfitBean> cps = cityProfitDAO.queryEntityBeansByFK(citys[i]);

            String dataOfYear = parameterDAO.getString(SysConfigConstant.DATE_OF_YEAR);

            // ��ò���ʼ���·�
            int beginMonth = CommonTools.parseInt(dataOfYear.substring(0, 2));

            // ѭ��������·�
            int step = 1;

            for (CityProfitBean cityProfitBean : cps)
            {
                CityProfitExamineBean nce = new CityProfitExamineBean();

                nce.setParentId(id);

                nce.setCityId(citys[i]);

                nce.setStep(step++ );

                int month = cityProfitBean.getMonth();

                int year = examine.getYear();

                // ������·���
                if (month < beginMonth)
                {
                    year++ ;
                }

                int end = TimeTools.getDaysOfMonth(year, month);

                String smonth = "0" + month;

                smonth = smonth.substring(smonth.length() - 2);

                nce.setBeginTime(year + "-" + smonth + "-01 00:00:00");

                nce.setEndTime(year + "-" + smonth + "-" + end + " 23:59:59");

                // ������������
                nce.setPlanValue(cityProfitBean.getProfit());

                nList.add(nce);
            }
        }
    }

    /**
     * preForAddExamine
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddExamine(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        handleStafferList(request);

        List<LocationBean> locationList = locationDAO.listEntityBeans();

        request.setAttribute("locationList", locationList);

        return mapping.findForward("addExamine");
    }

    /**
     * �������ڲ�ѯ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryExamine(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String year = request.getParameter("year");

        String locationId = request.getParameter("locationId");

        String attType = request.getParameter("attType");

        String type = request.getParameter("type");

        int dirAttType = 0;

        // ���ž�����
        if (String.valueOf(ExamineConstant.EXAMINE_ATTTYPE_DEPARTMENT).equals(attType))
        {
            dirAttType = ExamineConstant.EXAMINE_ATTTYPE_LOCATION;
        }

        if (String.valueOf(ExamineConstant.EXAMINE_ATTTYPE_PERSONAL).equals(attType))
        {
            dirAttType = ExamineConstant.EXAMINE_ATTTYPE_DEPARTMENT;
        }

        ConditionParse condition = new ConditionParse();

        condition.addIntCondition("ExamineBean.status", "=", ExamineConstant.EXAMINE_STATUS_PASS);

        condition.addIntCondition("ExamineBean.attType", "=", dirAttType);

        condition.addIntCondition("ExamineBean.year", "=", year);

        condition.addIntCondition("ExamineBean.type", "=", type);

        condition.addCondition("ExamineBean.locationId", "=", locationId);

        List<ExamineVO> list = examineDAO.queryEntityVOsByCondition(condition);

        request.setAttribute("list", list);

        return mapping.findForward("rptQueryExamine");
    }

    /**
     * addExamine
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addExamine(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ExamineBean bean = new ExamineBean();

        String errorMsg = "";

        String absId = "";

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            // ���ն˿��˵Ĵ���
            // if (bean.getType() == ExamineConstant.EXAMINE_TYPE_TER)
            // {
            // bean.setAttType(ExamineConstant.EXAMINE_ATTTYPE_PERSONNAL);
            // }

            // ������չ��(��һ������)
            if (true || bean.getType() == ExamineConstant.EXAMINE_TYPE_EXPEND)
            {
                errorMsg = processExpend(bean, errorMsg, user);
            }
            else
            {
                // �����ն˵�(���Ƚ���һ������ĸ�����,Ȼ�������ӿ���)
                ExamineBean abs = new ExamineBean();

                BeanUtil.copyProperties(abs, bean);

                abs.setAbs(ExamineConstant.EXAMINE_ABS_TRUE);

                try
                {
                    examineFacade.addExamine(user.getId(), abs);
                }
                catch (MYException e)
                {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "���濼�˻�����Ϣʧ��:" + e.getMessage());

                    return mapping.findForward("queryExamine");
                }

                absId = abs.getId();
                // ���ÿ����ĸ���
                bean.setParentId(absId);

                // ������չ�Ŀ���
                List<ExamineBean> subs = new ArrayList<ExamineBean>();

                createSubExamine(bean, user, subs);

                for (ExamineBean examineBean : subs)
                {
                    try
                    {
                        examineFacade.addExamine(user.getId(), examineBean);
                    }
                    catch (MYException e)
                    {
                        errorMsg += e.getErrorContent();
                    }
                }
            }

            if (StringTools.isNullOrNone(errorMsg))
            {
                request.setAttribute(KeyConstant.MESSAGE, "�ɹ�������˿��˻�����Ϣ");
            }
            else
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, errorMsg);
            }
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "���濼�˻�����Ϣʧ��:" + e.getMessage());

            return mapping.findForward("queryExamine");
        }

        String forward = request.getParameter("forward");

        if ("1".equals(forward))
        {
            // ��չ������
            if (bean.getType() == ExamineConstant.EXAMINE_TYPE_EXPEND)
            {
                if (bean.getAttType() != ExamineConstant.EXAMINE_ATTTYPE_PERSONAL)
                {
                    request.setAttribute("bean", bean);

                    return mapping.findForward("configExamineItem");
                }
                else
                {
                    request.setAttribute("id", bean.getParentId());

                    return configAllSubExamineItem(mapping, form, request, response);
                }
            }
            else
            {
                if (bean.getAttType() != ExamineConstant.EXAMINE_ATTTYPE_PERSONAL)
                {
                    request.setAttribute("bean", bean);

                    return mapping.findForward("configExamineItem");
                }
                else
                {
                    request.setAttribute("id", bean.getParentId());

                    return configAllSubExamineItem(mapping, form, request, response);
                }
                // �ն˵�����
                // request.setAttribute("id", bean.getId());

                // return configAllSubExamineItem(mapping, form, request, response);
            }
        }

        return mapping.findForward("queryExamine");
    }

    /**
     * @param bean
     * @param errorMsg
     * @param user
     * @return
     * @throws MYException
     */
    private String processExpend(ExamineBean bean, String errorMsg, User user)
        throws MYException
    {
        if (bean.getAttType() != ExamineConstant.EXAMINE_ATTTYPE_PERSONAL)
        {
            createExamine(bean, user);

            examineFacade.addExamine(user.getId(), bean);
        }
        else
        {
            // ������չ�Ŀ���
            List<ExamineBean> subs = new ArrayList<ExamineBean>();

            createSubExamine(bean, user, subs);

            for (ExamineBean examineBean : subs)
            {
                try
                {
                    examineFacade.addExamine(user.getId(), examineBean);
                }
                catch (MYException e)
                {
                    errorMsg += e.getErrorContent();
                }
            }
        }

        return errorMsg;
    }

    /**
     * �޸�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateExamine(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ExamineBean bean = new ExamineBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            createExamine(bean, user);

            examineFacade.updateExamine(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "�ɹ����濼�˻�����Ϣ:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "���濼�˻�����Ϣʧ��:" + e.getMessage());

            return mapping.findForward("queryExamine");
        }

        String forward = request.getParameter("forward");

        if ("1".equals(forward))
        {
            ExamineVO vo = examineDAO.findVO(bean.getId());

            request.setAttribute("bean", vo);

            request.setAttribute("pid", bean.getId());

            return mapping.findForward("configExamineItem");
        }

        return mapping.findForward("queryExamine");
    }

    /**
     * ���ÿ�����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward configExamineItem(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String look = request.getParameter("look");

        CommonTools.saveParamers(request);

        ExamineVO vo = examineDAO.findVO(id);

        request.setAttribute("bean", vo);

        // �鿴����
        if ("1".equals(look))
        {
            if ( !ExamineHelper.isReadonly(vo.getStatus()))
            {
                return ActionTools.toError("����û���ύ,���ܲ鿴����", mapping, request);
            }

            if (vo.getType() == ExamineConstant.EXAMINE_TYPE_EXPEND)
            {
                return mapping.findForward("lookExamineItem");
            }
            else
            {
                return mapping.findForward("lookTerExamineItem");
            }
        }
        else
        {
            if (vo.getType() == ExamineConstant.EXAMINE_TYPE_EXPEND)
            {
                return mapping.findForward("configExamineItem");
            }
            else
            {
                return mapping.findForward("configTerExamineItem");
            }
        }
    }

    /**
     * ���ò����������Ա����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward configAllSubExamineItem(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response)
        throws ServletException
    {
        // ���ſ��˵�id
        String id = request.getParameter("id");

        if (StringTools.isNullOrNone(id))
        {
            Object tep = request.getAttribute("id");

            if (tep != null)
            {
                id = tep.toString();
            }
        }

        CommonTools.saveParamers(request);

        ExamineVO vo = examineDAO.findVO(id);

        if (vo == null)
        {
            return ActionTools.toError("���˲�����", mapping, request);
        }

        if (vo.getAttType() == ExamineConstant.EXAMINE_ATTTYPE_PERSONAL
            && vo.getType() == ExamineConstant.EXAMINE_TYPE_EXPEND)
        {
            return ActionTools.toError("���˿���û���ӿ���", mapping, request);
        }

        if (examineDAO.countByFK(id) == 0)
        {
            return ActionTools.toError("û���ӿ���", mapping, request);
        }

        request.setAttribute("bean", vo);

        CommonTools.saveParamers(request);

        if (vo.getType() == ExamineConstant.EXAMINE_TYPE_EXPEND)
        {
            return mapping.findForward("configAllSubItem");
        }
        else
        {
            return mapping.findForward("configAllTerSubItem");
        }
    }

    /**
     * ɾ������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward delExamine(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            examineFacade.delExamine(user.getId(), id);

            ajax.setSuccess("�ɹ�ɾ������");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("ɾ������ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * ɾ�����п���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward delExamine2(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            examineFacade.delExamine2(user.getId(), id);

            ajax.setSuccess("�ɹ�ɾ������");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("ɾ������ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * �ύ����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward submitExamine(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            examineFacade.submitExamine(user.getId(), id);

            ajax.setSuccess("�ɹ��ύ����");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("�ύ����ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * ��ѯ������־
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryExaminLog(ActionMapping mapping, ActionForm form,
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
     * ͨ������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward passExamine(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            examineFacade.passExamine(user.getId(), id);

            ajax.setSuccess("�ɹ�ͨ������");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("ͨ������ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * ���ؿ���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rejectExamine(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String reason = request.getParameter("reason");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            examineFacade.rejectExamine(user.getId(), id, reason);

            ajax.setSuccess("�ɹ����ؿ���");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("���ؿ���ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * createExamine
     * 
     * @param bean
     * @param user
     * @throws MYException
     */
    private void createExamine(ExamineBean bean, User user)
        throws MYException
    {
        if (bean.getAttType() == ExamineConstant.EXAMINE_ATTTYPE_LOCATION)
        {
            bean.setParentId("0");
        }

        bean.setCreaterId(user.getStafferId());

        StafferBean sbean = stafferDAO.find(bean.getStafferId());

        if (sbean == null)
        {
            throw new MYException("�����˲�����");
        }

        // ���ÿ�������
        if (bean.getAttType() != ExamineConstant.EXAMINE_ATTTYPE_LOCATION)
        {
            if (sbean.getExamType() == StafferConstant.EXAMTYPE_TERMINAL)
            {
                bean.setType(ExamineConstant.EXAMINE_TYPE_TER);
            }
            else if (sbean.getExamType() == StafferConstant.EXAMTYPE_EXPAND)
            {
                bean.setType(ExamineConstant.EXAMINE_TYPE_EXPEND);
            }
            else
            {
                throw new MYException("�����˲���ҵ��Ա");
            }
        }
        else
        {
            // �����Զ����Դӽ����ȡ,��������û��ʵ��
        }

        String dataOfYear = parameterDAO.getString(SysConfigConstant.DATE_OF_YEAR);

        int beginMonth = CommonTools.parseInt(dataOfYear.substring(0, 2));

        int nbm = (beginMonth - 1) > 0 ? beginMonth - 1 : 12;

        String endMonth = "";

        if (nbm > 10)
        {
            endMonth = String.valueOf(nbm);
        }
        else
        {
            endMonth = "0" + String.valueOf(nbm);
        }

        bean.setBeginDate(bean.getYear() + "-" + dataOfYear.trim());

        // ��ʱ��������
        int day = TimeTools.getDaysOfMonth(bean.getYear() + 1, 2);

        bean.setEndDate( (bean.getYear() + 1) + "-" + endMonth + "-" + day);

        bean.setLogTime(TimeTools.now());

        bean.setStatus(ExamineConstant.EXAMINE_STATUS_INIT);
    }

    /**
     * �ƶ�������˿���
     * 
     * @param bean
     * @param user
     * @throws MYException
     */
    private void createSubExamine(ExamineBean bean, User user, List<ExamineBean> subs)
        throws MYException
    {
        bean.setCreaterId(user.getStafferId());

        String staffs = bean.getStafferId();

        String[] ss = staffs.split(",");

        // ��ʱ��������
        int day = TimeTools.getDaysOfMonth(bean.getYear() + 1, 2);

        String dataOfYear = parameterDAO.getString(SysConfigConstant.DATE_OF_YEAR);

        int beginMonth = CommonTools.parseInt(dataOfYear.substring(0, 2));

        int nbm = (beginMonth - 1) > 0 ? beginMonth - 1 : 12;

        String endMonth = "";

        if (nbm > 10)
        {
            endMonth = String.valueOf(nbm);
        }
        else
        {
            endMonth = "0" + String.valueOf(nbm);
        }

        for (String string : ss)
        {
            if (StringTools.isNullOrNone(string))
            {
                continue;
            }

            StafferBean sbean = stafferDAO.find(string);

            if (sbean == null)
            {
                throw new MYException("�����˲�����");
            }

            ExamineBean sub = new ExamineBean();

            BeanUtil.copyProperties(sub, bean);

            sub.setName(bean.getName() + "_" + sbean.getName());

            sub.setStafferId(string);

            // ���ÿ�������
            if (sbean.getExamType() != bean.getType())
            {
                throw new MYException("�������Ͳ�ƥ��,��ȷ�����ն˻�����չ");
            }

            sub.setType(bean.getType());

            sub.setBeginDate(bean.getYear() + "-" + dataOfYear.trim());

            sub.setEndDate( (bean.getYear() + 1) + "-" + endMonth + "-" + day);

            sub.setLogTime(TimeTools.now());

            sub.setStatus(ExamineConstant.EXAMINE_STATUS_INIT);

            subs.add(sub);
        }
    }

    /**
     * @return the examineFacade
     */
    public ExamineFacade getExamineFacade()
    {
        return examineFacade;
    }

    /**
     * @param examineFacade
     *            the examineFacade to set
     */
    public void setExamineFacade(ExamineFacade examineFacade)
    {
        this.examineFacade = examineFacade;
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
     * @return the parameterDAO
     */
    public ParameterDAO getParameterDAO()
    {
        return parameterDAO;
    }

    /**
     * @param parameterDAO
     *            the parameterDAO to set
     */
    public void setParameterDAO(ParameterDAO parameterDAO)
    {
        this.parameterDAO = parameterDAO;
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
     * @return the cityProfitDAO
     */
    public CityProfitDAO getCityProfitDAO()
    {
        return cityProfitDAO;
    }

    /**
     * @param cityProfitDAO
     *            the cityProfitDAO to set
     */
    public void setCityProfitDAO(CityProfitDAO cityProfitDAO)
    {
        this.cityProfitDAO = cityProfitDAO;
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
     * @return the locationDAO
     */
    public LocationDAO getLocationDAO()
    {
        return locationDAO;
    }

    /**
     * @param locationDAO
     *            the locationDAO to set
     */
    public void setLocationDAO(LocationDAO locationDAO)
    {
        this.locationDAO = locationDAO;
    }
}
