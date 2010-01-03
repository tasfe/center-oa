/**
 * File Name: CityConfigAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-1-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.examine.action;


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
import com.china.center.common.query.CommonQuery;
import com.china.center.fileReader.ReaderFile;
import com.china.center.fileReader.ReaderFileFactory;
import com.china.center.oa.constant.AuthConstant;
import com.china.center.oa.customer.bean.CustomerBean;
import com.china.center.oa.customer.dao.CustomerDAO;
import com.china.center.oa.customer.dao.StafferVSCustomerDAO;
import com.china.center.oa.customer.vs.StafferVSCustomerBean;
import com.china.center.oa.examine.bean.CityConfigBean;
import com.china.center.oa.examine.bean.CityProfitBean;
import com.china.center.oa.examine.bean.ProfitBean;
import com.china.center.oa.examine.dao.CityConfigDAO;
import com.china.center.oa.examine.dao.CityProfitDAO;
import com.china.center.oa.examine.dao.ProfitDAO;
import com.china.center.oa.examine.vo.CityConfigVO;
import com.china.center.oa.facade.ExamineFacade;
import com.china.center.oa.helper.Helper;
import com.china.center.oa.publics.User;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.tools.ActionTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.JSONTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.TimeTools;


/**
 * CityConfigAction
 * 
 * @author zhuzhu
 * @version 2009-1-3
 * @see CityConfigAction
 * @since 1.0
 */
public class CityConfigAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private CityConfigDAO cityConfigDAO = null;

    private ExamineFacade examineFacade = null;

    private CityProfitDAO cityProfitDAO = null;

    private StafferDAO stafferDAO = null;

    private ProfitDAO profitDAO = null;

    private CustomerDAO customerDAO = null;

    private StafferVSCustomerDAO stafferVSCustomerDAO = null;

    private UserManager userManager = null;

    private static String QUERYCITYCONFIG = "queryCityConfig";

    private static String QUERYCITYPROFIT = "queryCityProfit";

    /**
     * �ͻ�����
     */
    private static String QUERYPROFIT = "queryProfit";

    public CityConfigAction()
    {}

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
    public ActionForward queryCityConfig(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYCITYCONFIG, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCITYCONFIG, request, condtion,
            this.cityConfigDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * ��ѯ�ͻ�����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryProfit(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYPROFIT, request, condtion);

        condtion.addString("order by ProfitBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYPROFIT, request, condtion,
            this.profitDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * ��ѯ�����µ�����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCityProfit(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        final String cityId = request.getParameter("cityId");

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addCondition("cityId", "=", cityId);

        String jsonstr = ActionTools.querySelfBeanByJSONAndToString(QUERYCITYPROFIT, request,
            condtion, new CommonQuery()
            {
                public int getCount(String key, HttpServletRequest request,
                                    ConditionParse condition)
                {
                    return cityProfitDAO.countByFK(cityId);
                }

                public String getOrderPfix(String key, HttpServletRequest request)
                {
                    return "CityProfitBean";
                }

                public List queryResult(String key, HttpServletRequest request,
                                        ConditionParse queryCondition)
                {
                    return cityProfitDAO.queryEntityVOsByFK(cityId);
                }

                public String getSortname(HttpServletRequest request)
                {
                    return request.getParameter(ActionTools.SORTNAME);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * findCityConfig
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findCityConfig(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String update = request.getParameter("update");

        CityConfigVO bean = cityConfigDAO.findVO(id);

        if (bean == null)
        {
            return ActionTools.toError("���ò�����", mapping, request);
        }

        request.setAttribute("bean", bean);

        if ("1".equals(update))
        {
            return mapping.findForward("updateCityConfig");
        }

        return mapping.findForward("detailCityConfig");
    }

    /**
     * �޸ĵ�������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateCityConfig(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CityConfigBean bean = new CityConfigBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            examineFacade.updateCityConfig(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "�ɹ��޸ĵ��п�������");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "�޸�ʧ��:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryCityConfig");
    }

    /**
     * �޸�������������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateCityProfit(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CityProfitBean bean = new CityProfitBean();

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            BeanUtil.getBean(bean, request);

            examineFacade.updateCityProfit(user.getId(), bean);

            ajax.setSuccess("�ɹ��޸���������");
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            ajax.setError("�޸���������ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * �����ܶ���и���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateCityProfitByToatl(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response)
        throws ServletException
    {
        String cityId = request.getParameter("cityId");

        String total = request.getParameter("total");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            examineFacade.updateCityProfitByTotal(user.getId(), cityId,
                CommonTools.parseInt(total));

            ajax.setSuccess("�ɹ��޸���������");
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            ajax.setError("�޸���������ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * ����ͻ�����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward uploadProfit(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        if ( !userManager.containAuth(user, AuthConstant.EXAMINE_PROFIT_OPR))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "û��Ȩ��");

            return mapping.findForward("uploadProfit");
        }

        RequestDataStream rds = new RequestDataStream(request);

        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "����ʧ��");

            return mapping.findForward("uploadProfit");
        }

        int success = 0;

        int fault = 0;

        StringBuilder builder = new StringBuilder();

        if (rds.haveStream())
        {
            try
            {
                ReaderFile reader = ReaderFileFactory.getXLSReader();

                reader.readFile(rds.getUniqueInputStream());

                while (reader.hasNext())
                {
                    String[] obj = (String[])reader.next();

                    // ��һ�к���
                    if (reader.getCurrentLineNumber() == 1)
                    {
                        continue;
                    }

                    int currentNumber = reader.getCurrentLineNumber();

                    boolean addSucess = false;

                    if (obj.length >= 4)
                    {
                        addSucess = innerAdd(user, builder, obj, currentNumber);
                    }
                    else
                    {
                        builder.append("��[" + currentNumber + "]����:").append("���ݳ��Ȳ���4��").append(
                            "<br>");
                    }

                    if (addSucess)
                    {
                        success++ ;
                    }
                    else
                    {
                        fault++ ;
                    }
                }
            }
            catch (Exception e)
            {
                _logger.error(e, e);

                request.setAttribute(KeyConstant.ERROR_MESSAGE, "����ʧ��");

                return mapping.findForward("uploadProfit");
            }
        }

        rds.close();

        StringBuilder result = new StringBuilder();

        result.append("����ɹ�:").append(success).append("��,ʧ��:").append(fault).append("��<br>");

        result.append(builder.toString());

        request.setAttribute(KeyConstant.MESSAGE, result.toString());

        return mapping.findForward("uploadProfit");
    }

    /**
     * innerAdd
     * 
     * @param user
     * @param builder
     * @param obj
     * @param currentNumber
     */
    private boolean innerAdd(User user, StringBuilder builder, String[] obj, int currentNumber)
    {
        boolean addSucess = false;
        try
        {
            ProfitBean bean = createProfitBean(obj);

            examineFacade.addOrUpdateProfit(user.getId(), bean);

            addSucess = true;
        }
        catch (Exception e)
        {
            addSucess = false;

            builder.append("<font color=red>��[" + currentNumber + "]�д���:").append(e.getMessage()).append(
                "</font><br>");
        }

        return addSucess;
    }

    /**
     * ��������
     * 
     * @param obj
     * @return
     * @throws MYException
     */
    private ProfitBean createProfitBean(String[] obj)
        throws MYException
    {
        ProfitBean bean = new ProfitBean();

        int i = 0;

        bean.setOrgDate(TimeTools.getFormatDateStr(obj[i++ ]));

        CustomerBean cb = customerDAO.findCustomerByCode(obj[i++ ].trim());

        if (cb == null)
        {
            throw new MYException("�ͻ�������");
        }

        // ���õ�������
        bean.setCityId(cb.getCityId());

        bean.setProvinceId(cb.getProvinceId());

        bean.setAreaId(cb.getAreaId());

        bean.setCustomerId(cb.getId());

        // ���ÿͻ�������
        bean.setSellType(cb.getSelltype());

        StafferVSCustomerBean vs = stafferVSCustomerDAO.findByUnique(bean.getCustomerId());

        if (vs == null)
        {
            throw new MYException("�ͻ�û�к�ְԱ����");
        }

        bean.setStafferId(vs.getStafferId());

        bean.setProfit(CommonTools.parseFloat(obj[i++ ]));

        String code = obj[i++ ];

        StafferBean sb = stafferDAO.find(vs.getStafferId());

        if (sb == null)
        {
            throw new MYException("�ͻ�������,�乤��%s", code);
        }

        bean.setLocationId(sb.getLocationId());

        if ( !code.equals(sb.getCode()))
        {
            throw new MYException("�ͻ�[%s]��Ӧ��ְԱ��%s,����%s,����%s", cb.getCode(), sb.getName(),
                sb.getCode(), code);
        }

        return bean;
    }

    /**
     * ɾ��ĳ��ĳ�µ�����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward delProfit(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String year = request.getParameter("year");

        String month = request.getParameter("month");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            int count = examineFacade.delProfitByYearAndMonth(user.getId(),
                CommonTools.parseInt(year), CommonTools.parseInt(month));

            ajax.setSuccess("�ɹ�ɾ������(��):" + count);
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            ajax.setError("ɾ������ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * @return the cityConfigDAO
     */
    public CityConfigDAO getCityConfigDAO()
    {
        return cityConfigDAO;
    }

    /**
     * @param cityConfigDAO
     *            the cityConfigDAO to set
     */
    public void setCityConfigDAO(CityConfigDAO cityConfigDAO)
    {
        this.cityConfigDAO = cityConfigDAO;
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
     * @return the profitDAO
     */
    public ProfitDAO getProfitDAO()
    {
        return profitDAO;
    }

    /**
     * @param profitDAO
     *            the profitDAO to set
     */
    public void setProfitDAO(ProfitDAO profitDAO)
    {
        this.profitDAO = profitDAO;
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
}
