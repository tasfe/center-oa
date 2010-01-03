/**
 * File Name: StafferAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.action;


import java.util.HashMap;
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

import com.china.center.annosql.constant.AnoConstant;
import com.china.center.common.ConditionParse;
import com.china.center.common.KeyConstant;
import com.china.center.common.MYException;
import com.china.center.common.json.AjaxResult;
import com.china.center.common.query.HandleResult;
import com.china.center.common.query.QueryConfig;
import com.china.center.oa.constant.CustomerConstant;
import com.china.center.oa.customer.bean.ProductTypeBean;
import com.china.center.oa.customer.bean.ProviderBean;
import com.china.center.oa.customer.bean.ProviderHisBean;
import com.china.center.oa.customer.bean.ProviderUserBean;
import com.china.center.oa.customer.dao.ProductTypeDAO;
import com.china.center.oa.customer.dao.ProductTypeVSCustomerDAO;
import com.china.center.oa.customer.dao.ProviderDAO;
import com.china.center.oa.customer.dao.ProviderHisDAO;
import com.china.center.oa.customer.dao.ProviderUserDAO;
import com.china.center.oa.customer.vo.ProductTypeVSCustomerVO;
import com.china.center.oa.customer.vo.ProviderVO;
import com.china.center.oa.customer.vs.ProductTypeVSCustomer;
import com.china.center.oa.facade.CustomerFacade;
import com.china.center.oa.helper.Helper;
import com.china.center.oa.publics.User;
import com.china.center.tools.ActionTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.JSONTools;
import com.china.center.tools.RandomTools;
import com.china.center.tools.StringTools;


public class ProviderAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private QueryConfig queryConfig = null;

    private ProviderDAO providerDAO = null;

    private CustomerFacade customerFacade = null;

    private ProviderHisDAO providerHisDAO = null;

    private ProviderUserDAO providerUserDAO = null;

    private ProductTypeDAO productTypeDAO = null;

    private ProductTypeVSCustomerDAO productTypeVSCustomerDAO = null;

    private static String QUERYPROVIDER = "queryProvider";

    private static String QUERYCHECKHISPROVIDER = "queryCheckHisProvider";

    /**
     * default constructor
     */
    public ProviderAction()
    {}

    /**
     * ��ѯ��Ӧ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryProvider(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYPROVIDER, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYPROVIDER, request, condtion,
            this.providerDAO, new HandleResult()
            {
                public void handle(Object obj)
                {
                    ProviderVO vo = (ProviderVO)obj;

                    List<ProviderUserBean> list = providerUserDAO.queryEntityVOsByFK(vo.getId());

                    if (list.size() > 0)
                    {
                        vo.setLoginName(list.get(0).getName());
                    }

                    // ��ȡ����
                    List<ProductTypeVSCustomerVO> typeList = productTypeVSCustomerDAO.queryEntityVOsByFK(
                        vo.getId(), AnoConstant.FK_FIRST);

                    StringBuilder sb = new StringBuilder();
                    for (ProductTypeVSCustomerVO productTypeVSCustomerVO : typeList)
                    {
                        sb.append(productTypeVSCustomerVO.getProductTypeName() + "/");
                    }

                    vo.setTypeName(sb.toString());
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * �ͻ��󶨲�Ʒ����(ǰ��)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward preForBing(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String pid = request.getParameter("id");

        List<ProductTypeBean> ptype = productTypeDAO.listEntityBeans();

        request.setAttribute("list", ptype);

        // ��Ӧ��
        ProviderBean bean = providerDAO.find(pid);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "��Ӧ�̲�����");

            return mapping.findForward("queryProvider");
        }

        request.setAttribute("bean", bean);

        List<ProductTypeVSCustomer> list = productTypeVSCustomerDAO.queryVSByCustomerId(pid);

        Map<String, String> ps = new HashMap<String, String>();

        for (ProductTypeVSCustomer productTypeVSCustomer : list)
        {
            ps.put(productTypeVSCustomer.getProductTypeId(), productTypeVSCustomer.getCustomerId());
        }

        request.setAttribute("mapVS", ps);

        return mapping.findForward("bingProductType");
    }

    /**
     * bingProductTypeToProvider
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward bingProductTypeToProvider(ActionMapping mapping, ActionForm form,
                                                   HttpServletRequest request,
                                                   HttpServletResponse reponse)
        throws ServletException
    {
        String pid = request.getParameter("pid");

        String[] productTypeIds = request.getParameterValues("productTypeId");

        if (productTypeIds == null)
        {
            productTypeIds = new String[0];
        }

        try
        {
            User user = Helper.getUser(request);

            customerFacade.bingProductTypeToCustmer(user.getId(), pid, productTypeIds);

            request.setAttribute(KeyConstant.MESSAGE, "�����ͳɹ�");
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "������ʧ��:" + e.getMessage());
        }

        return mapping.findForward("queryProvider");
    }

    /**
     * ���ӹ�Ӧ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addProvider(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ProviderBean bean = new ProviderBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            customerFacade.addProvider(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "�ɹ����ӹ�Ӧ��:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "���ӹ�Ӧ��ʧ��:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryProvider");
    }

    /**
     * addOrUpdateUserBean
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addOrUpdateUserBean(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        ProviderUserBean bean = new ProviderUserBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            bean.setLocationId(user.getLocationId());

            boolean isAdd = StringTools.isNullOrNone(bean.getId());

            String password = RandomTools.getRandomString(10);

            if (isAdd)
            {
                bean.setPassword(password);
            }

            customerFacade.addOrUpdateUserBean(user.getId(), bean);

            if (isAdd)
            {
                request.setAttribute(KeyConstant.MESSAGE, "�ɹ����ӹ�Ӧ�̵�¼�û�,����:" + password);
            }
            else
            {
                request.setAttribute(KeyConstant.MESSAGE, "�ɹ��޸Ĺ�Ӧ�̵�¼�û�");
            }
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "����Ӧ�̵�¼�û�ʧ��:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryProvider");
    }

    /**
     * �޸Ĺ�Ӧ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateProvider(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ProviderBean bean = new ProviderBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            customerFacade.updateProvider(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "�޸Ĺ�Ӧ�̳ɹ�:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "�޸Ĺ�Ӧ��ʧ��:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryProvider");
    }

    /**
     * ɾ����Ӧ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward delProvider(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            customerFacade.delProvider(user.getId(), id);

            ajax.setSuccess("�ɹ�ɾ����Ӧ��");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("ɾ����Ӧ��ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * updateUserPassword
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateUserPassword(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request,
                                            HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            List<ProviderUserBean> list = providerUserDAO.queryEntityBeansByFK(id);

            if (list.size() == 0)
            {
                throw new MYException("û�й�Ӧ���û�");
            }

            String password = RandomTools.getRandomString(10);

            customerFacade.updateUserPassword(user.getId(), list.get(0).getId(), password);

            ajax.setSuccess("�����û�����ɹ�:" + password);
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("�����û�����ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * �鿴��Ӧ����ϸ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findProvider(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String update = request.getParameter("update");

        // User user = Helper.getUser(request);

        ProviderBean vo = providerDAO.find(id);

        if (vo == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "��Ӧ�̲�����");

            return mapping.findForward("querySelfCustomer");
        }

        request.setAttribute("bean", vo);

        if ("1".equals(update))
        {
            return mapping.findForward("updateProvider");
        }

        // detailProvider
        return mapping.findForward("detailProvider");
    }

    /**
     * findProviderUser
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findProviderUser(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        List<ProviderUserBean> list = providerUserDAO.queryEntityVOsByFK(id);

        if (list.size() > 1)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "���ݴ���");

            return mapping.findForward("querySelfCustomer");
        }

        ProviderUserBean bean = null;

        if (list.size() == 1)
        {
            bean = list.get(0);
        }

        request.setAttribute("bean", bean);
        request.setAttribute("provideId", id);

        // detailProvider
        return mapping.findForward("addOrUpdateProviderUser");
    }

    /**
     * findHisProvider
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findHisProvider(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        ProviderHisBean vo = providerHisDAO.find(id);

        if (vo == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "��Ӧ�̲�����");

            return mapping.findForward("querySelfCustomer");
        }

        request.setAttribute("bean", vo);

        // detailProvider
        return mapping.findForward("detailProvider");
    }

    /**
     * queryCheckHisCustomer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCheckHisProvider(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addIntCondition("ProviderHisBean.checkStatus", "=", CustomerConstant.HIS_CHECK_NO);

        ActionTools.processJSONQueryCondition(QUERYCHECKHISPROVIDER, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCHECKHISPROVIDER, request,
            condtion, this.providerHisDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * checkHisProvider
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward checkHisProvider(ActionMapping mapping, ActionForm form,
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
                    customerFacade.checkHisProvider(user.getId(), eachItem);
                }
            }

            ajax.setSuccess("�ɹ��˶Թ�Ӧ��");
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            ajax.setError("�˶�ʧ��:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
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
     * @return the providerDAO
     */
    public ProviderDAO getProviderDAO()
    {
        return providerDAO;
    }

    /**
     * @param providerDAO
     *            the providerDAO to set
     */
    public void setProviderDAO(ProviderDAO providerDAO)
    {
        this.providerDAO = providerDAO;
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
     * @return the providerHisDAO
     */
    public ProviderHisDAO getProviderHisDAO()
    {
        return providerHisDAO;
    }

    /**
     * @param providerHisDAO
     *            the providerHisDAO to set
     */
    public void setProviderHisDAO(ProviderHisDAO providerHisDAO)
    {
        this.providerHisDAO = providerHisDAO;
    }

    /**
     * @return the providerUserDAO
     */
    public ProviderUserDAO getProviderUserDAO()
    {
        return providerUserDAO;
    }

    /**
     * @param providerUserDAO
     *            the providerUserDAO to set
     */
    public void setProviderUserDAO(ProviderUserDAO providerUserDAO)
    {
        this.providerUserDAO = providerUserDAO;
    }

    /**
     * @return the productTypeDAO
     */
    public ProductTypeDAO getProductTypeDAO()
    {
        return productTypeDAO;
    }

    /**
     * @param productTypeDAO
     *            the productTypeDAO to set
     */
    public void setProductTypeDAO(ProductTypeDAO productTypeDAO)
    {
        this.productTypeDAO = productTypeDAO;
    }

    /**
     * @return the productTypeVSCustomerDAO
     */
    public ProductTypeVSCustomerDAO getProductTypeVSCustomerDAO()
    {
        return productTypeVSCustomerDAO;
    }

    /**
     * @param productTypeVSCustomerDAO
     *            the productTypeVSCustomerDAO to set
     */
    public void setProductTypeVSCustomerDAO(ProductTypeVSCustomerDAO productTypeVSCustomerDAO)
    {
        this.productTypeVSCustomerDAO = productTypeVSCustomerDAO;
    }
}
