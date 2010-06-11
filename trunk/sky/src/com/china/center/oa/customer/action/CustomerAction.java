/**
 * File Name: StafferAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.action;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jfree.data.category.CategoryDataset;

import com.china.center.common.ConditionParse;
import com.china.center.common.KeyConstant;
import com.china.center.common.MYException;
import com.china.center.common.PageSeparateTools;
import com.china.center.common.jfreechart.CreateChartServiceImpl;
import com.china.center.common.json.AjaxResult;
import com.china.center.common.query.CommonQuery;
import com.china.center.common.query.QueryConfig;
import com.china.center.common.query.QueryItemBean;
import com.china.center.fileReader.ReaderFile;
import com.china.center.fileReader.ReaderFileFactory;
import com.china.center.oa.constant.AuthConstant;
import com.china.center.oa.constant.CustomerConstant;
import com.china.center.oa.credit.dao.OutStatDAO;
import com.china.center.oa.customer.bean.AssignApplyBean;
import com.china.center.oa.customer.bean.CustomerApplyBean;
import com.china.center.oa.customer.bean.CustomerBean;
import com.china.center.oa.customer.bean.CustomerCheckBean;
import com.china.center.oa.customer.bean.CustomerCheckItemBean;
import com.china.center.oa.customer.dao.AssignApplyDAO;
import com.china.center.oa.customer.dao.ChangeLogDAO;
import com.china.center.oa.customer.dao.CustomerApplyDAO;
import com.china.center.oa.customer.dao.CustomerCheckDAO;
import com.china.center.oa.customer.dao.CustomerCheckItemDAO;
import com.china.center.oa.customer.dao.CustomerDAO;
import com.china.center.oa.customer.dao.CustomerHisDAO;
import com.china.center.oa.customer.dao.StafferVSCustomerDAO;
import com.china.center.oa.customer.helper.CustomerHelper;
import com.china.center.oa.customer.manager.CustomerManager;
import com.china.center.oa.customer.vo.CustomerApplyVO;
import com.china.center.oa.customer.vo.CustomerHisVO;
import com.china.center.oa.customer.vo.CustomerVO;
import com.china.center.oa.customer.wrap.NotPayWrap;
import com.china.center.oa.facade.CustomerFacade;
import com.china.center.oa.helper.Helper;
import com.china.center.oa.publics.User;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.CityDAO;
import com.china.center.oa.publics.dao.LocationDAO;
import com.china.center.oa.publics.dao.ProvinceDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.publics.trigger.DBOprTrigger;
import com.china.center.oa.worklog.dao.VisitDAO;
import com.china.center.oa.worklog.dao.WorkLogDAO;
import com.china.center.tools.ActionTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.FileTools;
import com.china.center.tools.JSONTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.url.ajax.json.OprMap;


public class CustomerAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private CustomerApplyDAO customerApplyDAO = null;

    private CustomerDAO customerDAO = null;

    private AssignApplyDAO assignApplyDAO = null;

    private WorkLogDAO workLogDAO = null;

    private OutStatDAO outStatDAO = null;

    private VisitDAO visitDAO = null;

    private CustomerHisDAO customerHisDAO = null;

    private CustomerCheckDAO customerCheckDAO = null;

    private CustomerCheckItemDAO customerCheckItemDAO = null;

    private StafferVSCustomerDAO stafferVSCustomerDAO = null;

    private QueryConfig queryConfig = null;

    private LocationDAO locationDAO = null;

    private ChangeLogDAO changeLogDAO = null;

    private ProvinceDAO provinceDAO = null;

    private StafferDAO stafferDAO = null;

    private CityDAO cityDAO = null;

    private CustomerFacade customerFacade = null;

    private CustomerManager customerManager = null;

    private UserManager userManager = null;

    private DBOprTrigger dbOprTrigger = null;

    private static String QUERYCUSTOMER = "queryCustomer";

    private static String QUERYAPPLYCUSTOMER = "queryApplyCustomer";

    private static String QUERYHISCUSTOMER = "queryHisCustomer";

    private static String QUERYCANASSIGNCUSTOMER = "queryCanAssignCustomer";

    private static String QUERYASSIGNAPPLY = "queryAssignApply";

    private static String QUERYCUSTOMERASSIGN = "queryCustomerAssign";

    private static String QUERYCHECKHISCUSTOMER = "queryCheckHisCustomer";

    private static String QUERYCHANGELOG = "queryChangeLog";

    private static String QUERYWORKCUSTOMERINCUSTOMER = "queryWorkCustomerInCustomer";

    private static String QUERYAPPLYCUSTOMERFORCODE = "queryApplyCustomerForCode";

    private static String QUERYAPPLYCUSTOMERFORCREDIT = "queryApplyCustomerForCredit";

    /**
     * default constructor
     */
    public CustomerAction()
    {}

    /**
     * querySelfCustomer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfCustomer(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        String jsonstr = "";

        if (userManager.containAuth(user, AuthConstant.CUSTOMER_QUERY_LOCATION))
        {
            // �������������еĿͻ�
            condtion.addCondition("CustomerBean.locationId", "=", user.getLocationId());

            ActionTools.processJSONQueryCondition(QUERYCUSTOMER, request, condtion);

            jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCUSTOMER, request, condtion,
                this.customerDAO);
        }
        else
        {
            final String stafferId = user.getStafferId();

            ActionTools.processJSONQueryCondition(QUERYCUSTOMER, request, condtion);

            jsonstr = ActionTools.querySelfBeanByJSONAndToString(QUERYCUSTOMER, request, condtion,
                new CommonQuery()
                {
                    public int getCount(String key, HttpServletRequest request,
                                        ConditionParse condition)
                    {
                        return customerDAO.countSelfCustomerByConstion(stafferId, condition);
                    }

                    public String getOrderPfix(String key, HttpServletRequest request)
                    {
                        return "CustomerBean";
                    }

                    public List queryResult(String key, HttpServletRequest request,
                                            ConditionParse queryCondition)
                    {
                        return customerDAO.querySelfCustomerByConstion(stafferId,
                            PageSeparateTools.getCondition(request, key),
                            PageSeparateTools.getPageSeparate(request, key));
                    }

                    public String getSortname(HttpServletRequest request)
                    {
                        return request.getParameter(ActionTools.SORTNAME);
                    }
                });
        }

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * ��ѯ�����µĿͻ��ֲ�(�������տͻ���)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCustomerAssign(ActionMapping mapping, ActionForm form,
                                             final HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        condtion.addCondition("t3.locationId", "=", user.getLocationId());

        ActionTools.processJSONQueryCondition(QUERYCUSTOMERASSIGN, request, condtion);

        String jsonstr = ActionTools.querySelfBeanByJSONAndToString(QUERYCUSTOMERASSIGN, request,
            condtion, new CommonQuery()
            {
                public int getCount(String key, HttpServletRequest request,
                                    ConditionParse condition)
                {
                    return customerDAO.countCustomerAssignByConstion(condition);
                }

                public String getOrderPfix(String key, HttpServletRequest request)
                {
                    return "t1";
                }

                public List queryResult(String key, HttpServletRequest request,
                                        ConditionParse queryCondition)
                {
                    return customerDAO.queryCustomerAssignByConstion(
                        PageSeparateTools.getCondition(request, key),
                        PageSeparateTools.getPageSeparate(request, key));
                }

                public String getSortname(HttpServletRequest request)
                {
                    return request.getParameter(ActionTools.SORTNAME);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * ���ɿͻ��ֲ�ͼ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCustomerDistribute(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        if ( !userManager.containAuth(user, AuthConstant.CUSTOMER_DISTRIBUTE))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "û��Ȩ��");

            return mapping.findForward("error");
        }

        String path = servlet.getServletContext().getRealPath("/") + "temp/";

        String ttemp = TimeTools.now("yyyy/MM/dd/HH/");

        path = path + ttemp;

        FileTools.mkdirs(path);

        String fileName = SequenceTools.getSequence() + ".png";

        List<LocationBean> locationList = locationDAO.listEntityBeans();

        double[] data = new double[locationList.size()];

        String[] columnKeys = new String[locationList.size()];

        int total = 0;

        for (int i = 0; i < locationList.size(); i++ )
        {
            data[i] = customerDAO.countByLocationId(locationList.get(i).getId());

            total += data[i];

            columnKeys[i] = locationList.get(i).getName();
        }

        CreateChartServiceImpl pm = new CreateChartServiceImpl(path);

        double[][] datas = new double[][] {data};

        String[] rowKeys = {"Customers:" + total};

        CategoryDataset dataset = pm.getBarData(datas, rowKeys, columnKeys);

        pm.createBarChart(dataset, "���ֹ�˾", "�ͻ�����", "�ͻ��ֲ�", fileName);

        List<String> urlList = new ArrayList<String>();

        urlList.add("../temp/" + ttemp + fileName);

        request.setAttribute("urlList", urlList);

        return mapping.findForward("queryCustomerDistribute");
    }

    /**
     * ��ѯ��־
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryChangeLog(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addCondition("order by logTime desc");

        ActionTools.processJSONQueryCondition(QUERYCHANGELOG, request, condtion);

        String jsonstr = ActionTools.queryBeanByJSONAndToString(QUERYCHANGELOG, request, condtion,
            this.changeLogDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * ����ְԱ�Ŀͻ��ֲ�ͼ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryStafferCustomerDistribute(ActionMapping mapping, ActionForm form,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        if ( !userManager.containAuth(user, AuthConstant.CUSTOMER_RECLAIM))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "û��Ȩ��");

            return mapping.findForward("error");
        }

        LocationBean lbean = locationDAO.find(user.getLocationId());

        if (lbean == null)
        {
            return ActionTools.toError("����Ϊ��", mapping, request);
        }

        String url = createCustmoerDistribute(user.getLocationId(), lbean.getName());

        List<String> urlList = new ArrayList<String>();

        urlList.add(url);

        request.setAttribute("urlList", urlList);

        return mapping.findForward("queryCustomerDistribute");
    }

    /**
     * �������������ְԱ�ֲ�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAllStafferCustomerDistribute(ActionMapping mapping, ActionForm form,
                                                           HttpServletRequest request,
                                                           HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        if ( !userManager.containAuth(user, AuthConstant.CUSTOMER_DISTRIBUTE))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "û��Ȩ��");

            return mapping.findForward("error");
        }

        List<LocationBean> locationlist = locationDAO.listEntityBeans();

        List<String> urlList = new ArrayList<String>();

        for (LocationBean locationBean : locationlist)
        {
            urlList.add(createCustmoerDistribute(locationBean.getId(), locationBean.getName()));
        }

        request.setAttribute("urlList", urlList);

        return mapping.findForward("queryCustomerDistribute");
    }

    /**
     * ��������ֲ�
     * 
     * @param locationId
     *            ����
     * @return ������·��
     */
    private String createCustmoerDistribute(String locationId, String locationName)
    {
        String path = servlet.getServletContext().getRealPath("/") + "temp/";

        String ttemp = TimeTools.now("yyyy/MM/dd/HH/");

        path = path + ttemp;

        FileTools.mkdirs(path);

        String fileName = SequenceTools.getSequence() + ".png";

        List<StafferBean> stafferList = stafferDAO.queryStafferByLocationId(locationId);

        List<OprMap> lop = new ArrayList<OprMap>();

        int total = 0;
        for (StafferBean stafferBean : stafferList)
        {
            int count = stafferVSCustomerDAO.countByStafferId(stafferBean.getId());

            if (count > 0)
            {
                OprMap mms = new OprMap();

                mms.setKey(stafferBean.getName());

                mms.setValue(count);

                lop.add(mms);
            }

            total += count;
        }

        double[] data = new double[lop.size()];

        String[] columnKeys = new String[lop.size()];

        for (int i = 0; i < lop.size(); i++ )
        {
            data[i] = (Integer)lop.get(i).getValue();

            columnKeys[i] = lop.get(i).getKey().toString();
        }

        CreateChartServiceImpl pm = new CreateChartServiceImpl(path);

        double[][] datas = new double[][] {data};

        String[] rowKeys = {"Customers:" + total};

        CategoryDataset dataset = pm.getBarData(datas, rowKeys, columnKeys);

        // һ��ְԱ75
        int width = lop.size() * 60 > 900 ? lop.size() * 60 : 900;

        pm.createBarChart(dataset, locationName + "��˾ְԱ", "�ͻ�����", locationName + "�ͻ��ֲ�", fileName,
            width);

        return "../temp/" + ttemp + fileName;
    }

    /**
     * ��ѯ��ʷ�޸�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryHisCustomer(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        if ( !userManager.containAuth(user, AuthConstant.CUSTOMER_QUERY_HIS))
        {
            return null;
        }

        // ֻ�ܿ����Լ��Ŀͻ�
        condtion.addCondition("CustomerHisBean.CUSTOMERID", "=", id);

        ActionTools.processJSONQueryCondition(QUERYHISCUSTOMER, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYHISCUSTOMER, request, condtion,
            this.customerHisDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * ��ѯ��Ҫ�˶ԵĿͻ�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCheckHisCustomer(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        // ֻ�ܿ����Լ��Ŀͻ�
        condtion.addIntCondition("CustomerHisBean.checkStatus", "=", CustomerConstant.HIS_CHECK_NO);

        ActionTools.processJSONQueryCondition(QUERYCHECKHISCUSTOMER, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCHECKHISCUSTOMER, request,
            condtion, this.customerHisDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryApplyCustomer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryApplyCustomer(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request,
                                            HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        condtion.addIntCondition("CustomerApplyBean.opr", "<>", CustomerConstant.OPR_UPATE_CREDIT);

        if (userManager.containAuth(user, AuthConstant.CUSTOMER_CHECK))
        {
            condtion.addCondition("CustomerApplyBean.locationId", "=", user.getLocationId());

            condtion.addIntCondition("CustomerApplyBean.status", "=",
                CustomerConstant.STATUS_APPLY);
        }
        else
        {
            // ֻ�ܿ����Լ��Ŀͻ�
            condtion.addCondition("CustomerApplyBean.updaterId", "=", user.getStafferId());
        }

        ActionTools.processJSONQueryCondition(QUERYAPPLYCUSTOMER, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYAPPLYCUSTOMER, request,
            condtion, this.customerApplyDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryApplyCustomerForCredit
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryApplyCustomerForCredit(ActionMapping mapping, ActionForm form,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        condtion.addIntCondition("CustomerApplyBean.opr", "=", CustomerConstant.OPR_UPATE_CREDIT);

        condtion.addCondition("CustomerApplyBean.locationId", "=", user.getLocationId());

        condtion.addIntCondition("CustomerApplyBean.status", "=", CustomerConstant.STATUS_APPLY);

        ActionTools.processJSONQueryCondition(QUERYAPPLYCUSTOMERFORCREDIT, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYAPPLYCUSTOMERFORCREDIT,
            request, condtion, this.customerApplyDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryApplyCustomerForCode
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryApplyCustomerForCode(ActionMapping mapping, ActionForm form,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addIntCondition("CustomerApplyBean.opr", "<>", CustomerConstant.OPR_UPATE_CREDIT);

        condtion.addIntCondition("CustomerApplyBean.status", "=",
            CustomerConstant.STATUS_WAIT_CODE);

        ActionTools.processJSONQueryCondition(QUERYAPPLYCUSTOMERFORCODE, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYAPPLYCUSTOMERFORCODE, request,
            condtion, this.customerApplyDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryCanAssignCustomer(�ɷ����)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCanAssignCustomer(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        String selltype = request.getParameter("selltype");

        if (StringTools.isNullOrNone(selltype))
        {
            // Ĭ������չ(�ն�ȫ������,��չ�Ƿֹ�˾����)
            selltype = "1";

            condtion.addIntCondition("CustomerBean.selltype", "=", selltype);
        }

        if ("0".equals(selltype))
        {
            condtion.addIntCondition("CustomerBean.status", "=", CustomerConstant.REAL_STATUS_IDLE);
        }
        else
        {
            condtion.addCondition("CustomerBean.locationId", "=", user.getLocationId());

            condtion.addIntCondition("CustomerBean.status", "=", CustomerConstant.REAL_STATUS_IDLE);
        }

        // exportAssign(request);

        ActionTools.processJSONQueryCondition(QUERYCANASSIGNCUSTOMER, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCANASSIGNCUSTOMER, request,
            condtion, this.customerDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * exportAssign
     * 
     * @param condtion
     */
    public void exportAssign(HttpServletRequest request)
    {
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addCondition("CustomerBean.locationId", "=", user.getLocationId());

        condtion.addIntCondition("CustomerBean.status", "=", CustomerConstant.REAL_STATUS_IDLE);

        OutputStream out = null;

        String filenName = null;

        filenName = "c:/exportAssign_" + TimeTools.now("MMddHHmmss") + ".xls";

        WritableWorkbook wwb = null;

        WritableSheet ws = null;

        List<CustomerBean> list = this.customerDAO.queryEntityBeansByCondition(condtion);

        try
        {
            out = new FileOutputStream(filenName);

            // create a excel
            wwb = Workbook.createWorkbook(out);

            ws = wwb.createSheet("exportAssign", 0);

            int i = 0, j = 0;

            CustomerBean element = null;

            WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
                jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);

            WritableFont font2 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD,
                false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);

            WritableCellFormat format = new WritableCellFormat(font);

            WritableCellFormat format2 = new WritableCellFormat(font2);

            ws.addCell(new Label(j++ , i, "�ͻ�����", format2));
            ws.addCell(new Label(j++ , i, "�ͻ�����", format));
            ws.addCell(new Label(j++ , i, "��˾", format));
            ws.addCell(new Label(j++ , i, "��ϵ��", format));
            ws.addCell(new Label(j++ , i, "�ֻ�", format));
            ws.addCell(new Label(j++ , i, "�̻�", format));
            ws.addCell(new Label(j++ , i, "��ַ", format));

            for (Iterator iter = list.iterator(); iter.hasNext();)
            {
                element = (CustomerBean)iter.next();

                CustomerHelper.decryptCustomer(element);

                j = 0;
                i++ ;

                ws.addCell(new Label(j++ , i, element.getName()));
                ws.addCell(new Label(j++ , i, element.getCode()));

                ws.addCell(new Label(j++ , i, element.getCompany()));
                ws.addCell(new Label(j++ , i, element.getConnector()));
                ws.addCell(new Label(j++ , i, element.getHandphone()));
                ws.addCell(new Label(j++ , i, element.getTel()));
                ws.addCell(new Label(j++ , i, element.getAddress()));

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (wwb != null)
            {
                try
                {
                    wwb.write();
                    wwb.close();
                }
                catch (Exception e1)
                {}
            }
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e1)
                {}
            }
        }
    }

    /**
     * preForAddApplyCustomer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddApplyCustomer(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
        return mapping.findForward("addCustomer");
    }

    /**
     * addApplyCustomer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addApplyCustomer(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CustomerApplyBean bean = new CustomerApplyBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            bean.setCreaterId(user.getStafferId());

            customerFacade.applyAddCustomer(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "�ɹ�����ͻ�:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "����ʧ��:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryApplyCustomer");
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
    public ActionForward assignApplyCustomerCode(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);
        try
        {
            String id = request.getParameter("id");

            String code = request.getParameter("code");

            customerFacade.assignApplyCustomerCode(user.getId(), id, code.trim());

            request.setAttribute(KeyConstant.MESSAGE, "����ͻ�����ɹ�,�ͻ���������");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "����ʧ��:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryApplyCustomerForCode");
    }

    /**
     * addUpdateApplyCustomer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addUpdateApplyCustomer(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        // ��ֹĳЩ����ֵ���޸�
        CustomerBean bean = customerDAO.find(id);

        if (bean == null)
        {
            ActionTools.toError("�ͻ�������", "queryApplyCustomer", mapping, request);
        }

        CustomerApplyBean apply = new CustomerApplyBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            BeanUtil.copyProperties(apply, bean);

            customerFacade.applyUpdateCustomer(user.getId(), apply);

            request.setAttribute(KeyConstant.MESSAGE, "�ɹ������޸Ŀͻ�:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "�����޸�ʧ��:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryApplyCustomer");
    }

    /**
     * addUpdateApplyCustomer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addDelApplyCustomer(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        CustomerApplyBean apply = new CustomerApplyBean();

        AjaxResult ajax = new AjaxResult();

        try
        {
            CustomerBean bean = customerDAO.find(id);

            if (bean == null)
            {
                ajax.setError("�ͻ�������");

                return JSONTools.writeResponse(response, ajax);
            }

            BeanUtil.copyProperties(apply, bean);

            User user = Helper.getUser(request);

            customerFacade.applyDelCustomer(user.getId(), apply);

            ajax.setSuccess("�ɹ�����ɾ���ͻ�:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("����ɾ��ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * �ֹ�ȫ��ͬ���ͻ��ֹ�˾����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward synchronizationAllCustomerLocation(ActionMapping mapping,
                                                            ActionForm form,
                                                            HttpServletRequest request,
                                                            HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        User user = Helper.getUser(request);

        try
        {
            if (userManager.containAuth(user, AuthConstant.CUSTOMER_SYNCHRONIZATION))
            {
                dbOprTrigger.synchronizationAllCustomerLocation();

                ajax.setSuccess("ͬ���ɹ�");
            }
            else
            {
                ajax.setError("û��Ȩ��");
            }
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("ͬ��ʧ��");
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * delApplyCustomer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward delApplyCustomer(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            customerFacade.delApplyCustomer(user.getId(), id);

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
     * addUpdateApplyCustomer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findCustomer(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String update = request.getParameter("update");

        String linkId = request.getParameter("linkId");

        CommonTools.saveParamers(request);

        User user = Helper.getUser(request);

        CustomerVO vo = customerDAO.findVO(id);

        if (vo == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "�ͻ�������");

            return mapping.findForward("querySelfCustomer");
        }

        request.setAttribute("bean", vo);

        try
        {
            // �޸ģ���Ҫ��֤Ȩ��
            if ("1".equals(update))
            {
                boolean hasAuth = customerManager.hasCustomerAuth(user.getStafferId(), id);

                if ( !hasAuth)
                {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "û�д˿ͻ����޸�Ȩ��");

                    return mapping.findForward("querySelfCustomer");
                }

                CustomerHelper.decryptCustomer(vo);
            }
            else if ("0".equals(update))
            {
                if (userManager.containAuth(user, AuthConstant.CUSTOMER_OQUERY))
                {
                    CustomerHelper.decryptCustomer(vo);
                }
                else
                {
                    CustomerHelper.handleCustomer(vo);
                }
            }
            else if ("2".equals(update))
            {
                boolean hasAuth = customerManager.hasCustomerAuth(user.getStafferId(), id);

                if ( !hasAuth && !userManager.containAuth(user, AuthConstant.CUSTOMER_CHECK))
                {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "û��Ȩ��");

                    return mapping.findForward("queryApplyCustomer");
                }

                CustomerHelper.decryptCustomer(vo);
            }
            // process linkid
            else if ("3".equals(update))
            {
                if (StringTools.isNullOrNone(linkId))
                {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "���ݲ��걸");

                    return mapping.findForward("error");
                }

                CustomerCheckItemBean item = customerCheckItemDAO.find(linkId);

                if (item == null)
                {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "���ݲ��걸");

                    return mapping.findForward("error");
                }

                if ( !item.getCustomerId().equals(id))
                {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "���ݲ��걸");

                    return mapping.findForward("error");
                }

                CustomerCheckBean pbean = customerCheckDAO.find(item.getParentId());

                if (pbean == null)
                {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "���ݲ��걸");

                    return mapping.findForward("error");
                }

                if ( !pbean.getApplyerId().equals(user.getStafferId()))
                {
                    boolean hasAuth = customerManager.hasCustomerAuth(user.getStafferId(), id);

                    if ( !hasAuth)
                    {
                        request.setAttribute(KeyConstant.ERROR_MESSAGE, "Ȩ�޲���");

                        return mapping.findForward("error");
                    }
                }

                if (pbean.getBeginTime().compareTo(TimeTools.now()) > 0
                    || pbean.getEndTime().compareTo(TimeTools.now()) < 0)
                {
                    boolean hasAuth = customerManager.hasCustomerAuth(user.getStafferId(), id);

                    if ( !hasAuth)
                    {
                        request.setAttribute(KeyConstant.ERROR_MESSAGE, "����ָ����ʱ��,�޷�����");

                        return mapping.findForward("error");
                    }
                }

                CustomerHelper.decryptCustomer(vo);
            }
            else
            {
                if (userManager.containAuth(user, AuthConstant.CUSTOMER_OQUERY))
                {
                    CustomerHelper.decryptCustomer(vo);
                }
                else
                {
                    CustomerHelper.handleCustomer(vo);
                }
            }
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getMessage());

            return mapping.findForward("querySelfCustomer");
        }

        if ("1".equals(update))
        {
            return mapping.findForward("updateCustomer");
        }
        else
        {
            return mapping.findForward("detailCustomer");
        }
    }

    /**
     * addUpdateApplyCustomer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findApplyCustomer(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String updateCode = request.getParameter("updateCode");

        User user = Helper.getUser(request);

        try
        {
            CustomerApplyVO vo = customerApplyDAO.findVO(id);

            if (vo == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "�ͻ�������");

                return mapping.findForward("querySelfCustomer");
            }

            request.setAttribute("bean", vo);

            // �޸Ŀͻ�����
            if (userManager.containAuth(user, AuthConstant.CUSTOMER_ASSIGN_CODE)
                && "1".equals(updateCode))
            {
                CustomerHelper.handleCustomer(vo);

                request.setAttribute("updateCode", "1");

                return mapping.findForward("detailCustomer");
            }

            boolean isSelfApply = vo.getUpdaterId().equals(user.getStafferId());

            boolean hasAuth = customerManager.hasCustomerAuth(user.getStafferId(), id);

            if ( !isSelfApply && !hasAuth
                && !userManager.containAuth(user, AuthConstant.CUSTOMER_CHECK))
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "û��Ȩ��");

                return mapping.findForward("queryApplyCustomer");
            }

            CustomerHelper.decryptCustomer(vo);
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getMessage());

            return mapping.findForward("queryApplyCustomer");
        }

        return mapping.findForward("detailCustomer");
    }

    /**
     * addUpdateApplyCustomer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findHisCustomer(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        CustomerHisVO vo = customerHisDAO.findVO(id);

        if (vo == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "�ͻ�������");

            return mapping.findForward("querySelfCustomer");
        }

        CustomerHelper.decryptCustomer(vo);

        request.setAttribute("bean", vo);

        return mapping.findForward("detailCustomer");
    }

    /**
     * processApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward processApply(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String operation = request.getParameter("operation");

        String reson = request.getParameter("reson");

        String resultMsg = "";

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            if ("0".equals(operation))
            {
                customerFacade.passApplyCustomer(user.getId(), id);
            }

            if ("1".equals(operation))
            {
                customerFacade.rejectApplyCustomer(user.getId(), id, reson);
            }

            resultMsg = "�ɹ���������";

            if ("2".equals(operation))
            {
                customerFacade.delApplyCustomer(user.getId(), id);

                resultMsg = "�ɹ�ɾ������";
            }
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            resultMsg = "��������ʧ��:" + e.getMessage();

            ajax.setError();
        }

        CommonTools.removeParamers(request);

        ajax.setMsg(resultMsg);

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * addAssignApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addAssignApply(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            String cids = request.getParameter("cids");

            String[] customerIds = cids.split("~");

            AssignApplyBean bean = new AssignApplyBean();

            bean.setUserId(user.getStafferId());

            bean.setStafferId(user.getStafferId());

            bean.setLocationid(user.getLocationId());

            for (String eachItem : customerIds)
            {
                if ( !StringTools.isNullOrNone(eachItem))
                {
                    bean.setCustomerId(eachItem.trim());

                    customerFacade.addAssignApply(user.getId(), bean);
                }
            }

            ajax.setSuccess("�ɹ���������");
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            ajax.setError("����ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * �˶Կͻ��޸�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward checkHisCustomer(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            String cids = request.getParameter("cids");

            String[] customerIds = cids.split("~");

            for (String eachItem : customerIds)
            {
                if ( !StringTools.isNullOrNone(eachItem))
                {
                    customerFacade.checkHisCustomer(user.getId(), eachItem);
                }
            }

            ajax.setSuccess("�ɹ��˶Կͻ�");
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            ajax.setError("�˶�ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * queryAssignApply(��ѯ�ɷ�������)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAssignApply(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        condtion.addCondition("AssignApplyBean.locationId", "=", user.getLocationId());

        ActionTools.processJSONQueryCondition(QUERYASSIGNAPPLY, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYASSIGNAPPLY, request, condtion,
            this.assignApplyDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * �������ͻ�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward processAssignApply(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request,
                                            HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            String cids = request.getParameter("cids");

            String opr = request.getParameter("opr");

            String[] customerIds = cids.split("~");

            for (String eachItem : customerIds)
            {
                if ( !StringTools.isNullOrNone(eachItem))
                {
                    if ("0".equals(opr))
                    {
                        customerFacade.passAssignApply(user.getId(), eachItem);
                    }

                    if ("1".equals(opr))
                    {
                        customerFacade.rejectAssignApply(user.getId(), eachItem);
                    }
                }
            }

            ajax.setSuccess("�ɹ���������");
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            ajax.setError("��������ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * ���շ���ͻ�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward reclaimAssignCustomer(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            String cids = request.getParameter("customerIds");

            String[] customerIds = cids.split("~");

            for (String eachItem : customerIds)
            {
                if ( !StringTools.isNullOrNone(eachItem))
                {
                    customerFacade.reclaimAssignCustomer(user.getId(), eachItem);
                }
            }

            ajax.setSuccess("�ɹ����տͻ�");
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            ajax.setError("���տͻ�ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * ����ְԱ�Ŀͻ�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward reclaimStafferCustomer(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            String stafferId = request.getParameter("stafferId");

            String flag = request.getParameter("flag");

            customerFacade.reclaimStafferAssignCustomer(user.getId(), stafferId,
                CommonTools.parseInt(flag));

            ajax.setSuccess("�ɹ�����ְԱ�ͻ�");
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            ajax.setError("����ְԱ�ͻ�ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * popStafferQuery
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward popStafferCommonQuery(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        QueryItemBean query = queryConfig.findQueryCondition(QUERYCUSTOMER);

        if (query == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "û�����ò�ѯ,���ʵ");

            return mapping.findForward("queryStaffer");
        }

        request.setAttribute("query", query);

        return mapping.findForward("commonQuery");
    }

    /**
     * export
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward exportNotPay(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        List<NotPayWrap> beanList = outStatDAO.listNotPayWrap();

        OutputStream out = null;

        String filenName = null;

        filenName = "NotPay_" + TimeTools.now("MMddHHmmss") + ".xls";

        if (beanList.size() == 0)
        {
            return null;
        }

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WritableWorkbook wwb = null;

        WritableSheet ws = null;

        try
        {
            out = reponse.getOutputStream();

            // create a excel
            wwb = Workbook.createWorkbook(out);

            ws = wwb.createSheet("NOTPAY", 0);

            int i = 0, j = 0;

            NotPayWrap element = null;

            WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
                jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);

            WritableFont font2 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD,
                false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);

            WritableCellFormat format = new WritableCellFormat(font);

            WritableCellFormat format2 = new WritableCellFormat(font2);

            ws.addCell(new Label(j++ , i, "�ͻ�����", format));
            ws.addCell(new Label(j++ , i, "�ͻ�����", format));
            ws.addCell(new Label(j++ , i, "���õȼ�", format));
            ws.addCell(new Label(j++ , i, "���÷���", format));
            ws.addCell(new Label(j++ , i, "Ӧ���˿�", format));

            for (Iterator iter = beanList.iterator(); iter.hasNext();)
            {
                element = (NotPayWrap)iter.next();

                j = 0;
                i++ ;

                ws.addCell(new Label(j++ , i, element.getCname()));
                ws.addCell(new Label(j++ , i, element.getCcode()));

                ws.addCell(new Label(j++ , i, element.getCreditName()));

                ws.addCell(new jxl.write.Number(j++ , i, element.getCreditVal()));

                ws.addCell(new jxl.write.Number(j++ , i, element.getNotPay(), format2));

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return null;
        }
        finally
        {
            if (wwb != null)
            {
                try
                {
                    wwb.write();
                    wwb.close();
                }
                catch (Exception e1)
                {}
            }
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e1)
                {}
            }
        }

        return null;
    }

    /**
     * ����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward uploadCustomer(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        if ( !userManager.containAuth(user, AuthConstant.CUSTOMER_UPLOAD))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "û��Ȩ��");

            return mapping.findForward("uploadCustomer");
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

            return mapping.findForward("uploadCustomer");
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

                    String stafferId = getStafferId(obj);

                    int currentNumber = reader.getCurrentLineNumber();

                    boolean addSucess = false;

                    if (obj.length >= 35)
                    {
                        addSucess = innerAdd(user, builder, obj, stafferId, currentNumber);
                    }
                    else
                    {
                        builder.append("��[" + currentNumber + "]����:").append(
                            "���ݳ��Ȳ���34��,��ע����Ϊ��,����Ϣ����ʱ�䲻����Ϊ��").append("<br>");
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

                return mapping.findForward("uploadCustomer");
            }
        }

        rds.close();

        StringBuilder result = new StringBuilder();

        result.append("����ɹ�:").append(success).append("��,ʧ��:").append(fault).append("��<br>");

        result.append(builder.toString());

        request.setAttribute(KeyConstant.MESSAGE, result.toString());

        return mapping.findForward("uploadCustomer");
    }

    /**
     * query worklog visit
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryWorkCustomer(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        String targerId = request.getParameter("targerId");

        condtion.addCondition("VisitBean.targerId", "=", targerId);

        ActionTools.processJSONQueryCondition(QUERYWORKCUSTOMERINCUSTOMER, request, condtion);

        condtion.addCondition("order by WorkLogBean.logTime desc");

        List list = ActionTools.selfCommonQueryBeanInnerByJSON(QUERYWORKCUSTOMERINCUSTOMER,
            request, condtion, new CommonQuery()
            {
                public int getCount(String key, HttpServletRequest request,
                                    ConditionParse condition)
                {
                    return visitDAO.countWorkItemByConstion(condition);
                }

                public String getOrderPfix(String key, HttpServletRequest request)
                {
                    return "WorkLogBean";
                }

                public List queryResult(String key, HttpServletRequest request,
                                        ConditionParse queryCondition)
                {
                    return visitDAO.queryWorkItemByConstion(PageSeparateTools.getCondition(
                        request, key), PageSeparateTools.getPageSeparate(request, key));
                }

                public String getSortname(HttpServletRequest request)
                {
                    return request.getParameter(ActionTools.SORTNAME);
                }
            });

        String jsonstr = JSONTools.getJSONString(list, PageSeparateTools.getPageSeparate(request,
            QUERYWORKCUSTOMERINCUSTOMER));

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * handleStafferId
     * 
     * @param obj
     * @param stafferId
     * @return
     */
    private String getStafferId(String[] obj)
    {
        String stafferId = "";

        if ( !StringTools.isNullOrNone(obj[1]))
        {
            StafferBean sb = stafferDAO.findyStafferByName(obj[1]);

            if (sb != null)
            {
                stafferId = sb.getId();
            }
        }

        return stafferId;
    }

    /**
     * @param user
     * @param builder
     * @param obj
     * @param stafferId
     * @param currentNumber
     * @return
     */
    private boolean innerAdd(User user, StringBuilder builder, String[] obj, String stafferId,
                             int currentNumber)
    {
        boolean addSucess = false;

        try
        {
            CustomerBean bean = createCustomerBean(obj);

            // �����Ƿ�ʹ��
            if ( !StringTools.isNullOrNone(stafferId))
            {
                bean.setStatus(CustomerConstant.REAL_STATUS_USED);
            }

            customerManager.addCustomer(user, bean, stafferId);

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

    private CustomerBean createCustomerBean(String[] obj)
        throws MYException
    {
        CustomerBean bean = new CustomerBean();

        int i = 2;

        bean.setFormtype(CommonTools.parseInt(obj[i++ ]));// �ͻ���Դ
        bean.setBeginConnectTime(obj[i++ ]);// ��ʼ��ϵ�ͻ�ʱ��
        bean.setCompany(obj[i++ ]);// �ͻ���˾

        String name = obj[i++ ];

        if (StringTools.isNullOrNone(name))
        {
            throw new MYException("�ͻ�����Ϊ��");
        }

        bean.setName(name.trim());// �ͻ�����

        String code = obj[i++ ];

        if (StringTools.isNullOrNone(code))
        {
            throw new MYException("�ͻ�����Ϊ��");
        }

        bean.setCode(code.trim());// �ͻ�����

        bean.setSelltype(CommonTools.parseInt(obj[i++ ]));// �ͻ�����
        bean.setProtype(CommonTools.parseInt(obj[i++ ]));// �ͻ�����1
        bean.setNewtype(CommonTools.parseInt(obj[i++ ]));// �ͻ�����2
        bean.setMtype(CommonTools.parseInt(obj[i++ ]));// �ͻ�����
        bean.setHtype(CommonTools.parseInt(obj[i++ ]));// ��ҵ����
        bean.setQqtype(CommonTools.parseInt(obj[i++ ]));// �ͻ��ȼ�
        bean.setRtype(CommonTools.parseInt(obj[i++ ]));// ��������
        bean.setBlog(CommonTools.parseInt(obj[i++ ]));// ������ʷ�ɽ�
        bean.setCard(CommonTools.parseInt(obj[i++ ]));// ������Ƭ
        i++ ;// ʡ

        String cityName = obj[i++ ];

        CityBean city = cityDAO.findCityByName(cityName);

        if (city == null)
        {
            throw new MYException("����[%s]������", cityName);
        }

        bean.setProvinceId(city.getParentId());
        bean.setCityId(city.getId());

        bean.setConnector(obj[i++ ]);// ��ϵ��
        bean.setPost(obj[i++ ]);// ְ��
        bean.setHandphone(obj[i++ ]);// �ֻ�
        bean.setTel(obj[i++ ]);// ����
        bean.setFax(obj[i++ ]);// ����
        bean.setQq(obj[i++ ]);// QQ
        bean.setMsn(obj[i++ ]);// MSN
        bean.setMail(obj[i++ ]);// E-mail
        bean.setWeb(obj[i++ ]);// ��ַ
        bean.setAddress(obj[i++ ]);// ��ַ
        bean.setPostcode(obj[i++ ]);// �ʱ�
        bean.setBirthday(obj[i++ ]);// ����
        bean.setBank(obj[i++ ]);// ��������
        bean.setAccounts(obj[i++ ]);// �ʺ�
        bean.setDutycode(obj[i++ ]);// ˰��
        bean.setFlowcom(obj[i++ ]);// ָ����������˾
        bean.setLoginTime(obj[i++ ]);// ��Ϣ����ʱ��

        if (obj.length >= 36)
        {
            bean.setDescription(obj[i++ ]);
        }

        return bean;
    }

    /**
     * @return the customerApplyDAO
     */
    public CustomerApplyDAO getCustomerApplyDAO()
    {
        return customerApplyDAO;
    }

    /**
     * @param customerApplyDAO
     *            the customerApplyDAO to set
     */
    public void setCustomerApplyDAO(CustomerApplyDAO customerApplyDAO)
    {
        this.customerApplyDAO = customerApplyDAO;
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
     * @return the customerHisDAO
     */
    public CustomerHisDAO getCustomerHisDAO()
    {
        return customerHisDAO;
    }

    /**
     * @param customerHisDAO
     *            the customerHisDAO to set
     */
    public void setCustomerHisDAO(CustomerHisDAO customerHisDAO)
    {
        this.customerHisDAO = customerHisDAO;
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
     * @return the customerFacade
     */
    public CustomerFacade getCustomerFacade()
    {
        return customerFacade;
    }

    /**
     * @param customerFacade
     *            the customerFacade to set
     */
    public void setCustomerFacade(CustomerFacade customerFacade)
    {
        this.customerFacade = customerFacade;
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
     * @return the provinceDAO
     */
    public ProvinceDAO getProvinceDAO()
    {
        return provinceDAO;
    }

    /**
     * @param provinceDAO
     *            the provinceDAO to set
     */
    public void setProvinceDAO(ProvinceDAO provinceDAO)
    {
        this.provinceDAO = provinceDAO;
    }

    /**
     * @return the cityDAO
     */
    public CityDAO getCityDAO()
    {
        return cityDAO;
    }

    /**
     * @param cityDAO
     *            the cityDAO to set
     */
    public void setCityDAO(CityDAO cityDAO)
    {
        this.cityDAO = cityDAO;
    }

    /**
     * @return the assignApplyDAO
     */
    public AssignApplyDAO getAssignApplyDAO()
    {
        return assignApplyDAO;
    }

    /**
     * @param assignApplyDAO
     *            the assignApplyDAO to set
     */
    public void setAssignApplyDAO(AssignApplyDAO assignApplyDAO)
    {
        this.assignApplyDAO = assignApplyDAO;
    }

    /**
     * @return the customerManager
     */
    public CustomerManager getCustomerManager()
    {
        return customerManager;
    }

    /**
     * @param customerManager
     *            the customerManager to set
     */
    public void setCustomerManager(CustomerManager customerManager)
    {
        this.customerManager = customerManager;
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

    /**
     * @return the dbOprTrigger
     */
    public DBOprTrigger getDbOprTrigger()
    {
        return dbOprTrigger;
    }

    /**
     * @param dbOprTrigger
     *            the dbOprTrigger to set
     */
    public void setDbOprTrigger(DBOprTrigger dbOprTrigger)
    {
        this.dbOprTrigger = dbOprTrigger;
    }

    /**
     * @return the changeLogDAO
     */
    public ChangeLogDAO getChangeLogDAO()
    {
        return changeLogDAO;
    }

    /**
     * @param changeLogDAO
     *            the changeLogDAO to set
     */
    public void setChangeLogDAO(ChangeLogDAO changeLogDAO)
    {
        this.changeLogDAO = changeLogDAO;
    }

    /**
     * @return the customerCheckDAO
     */
    public CustomerCheckDAO getCustomerCheckDAO()
    {
        return customerCheckDAO;
    }

    /**
     * @param customerCheckDAO
     *            the customerCheckDAO to set
     */
    public void setCustomerCheckDAO(CustomerCheckDAO customerCheckDAO)
    {
        this.customerCheckDAO = customerCheckDAO;
    }

    /**
     * @return the customerCheckItemDAO
     */
    public CustomerCheckItemDAO getCustomerCheckItemDAO()
    {
        return customerCheckItemDAO;
    }

    /**
     * @param customerCheckItemDAO
     *            the customerCheckItemDAO to set
     */
    public void setCustomerCheckItemDAO(CustomerCheckItemDAO customerCheckItemDAO)
    {
        this.customerCheckItemDAO = customerCheckItemDAO;
    }

    /**
     * @return the workLogDAO
     */
    public WorkLogDAO getWorkLogDAO()
    {
        return workLogDAO;
    }

    /**
     * @param workLogDAO
     *            the workLogDAO to set
     */
    public void setWorkLogDAO(WorkLogDAO workLogDAO)
    {
        this.workLogDAO = workLogDAO;
    }

    /**
     * @return the visitDAO
     */
    public VisitDAO getVisitDAO()
    {
        return visitDAO;
    }

    /**
     * @param visitDAO
     *            the visitDAO to set
     */
    public void setVisitDAO(VisitDAO visitDAO)
    {
        this.visitDAO = visitDAO;
    }

    /**
     * @return the outStatDAO
     */
    public OutStatDAO getOutStatDAO()
    {
        return outStatDAO;
    }

    /**
     * @param outStatDAO
     *            the outStatDAO to set
     */
    public void setOutStatDAO(OutStatDAO outStatDAO)
    {
        this.outStatDAO = outStatDAO;
    }

}
