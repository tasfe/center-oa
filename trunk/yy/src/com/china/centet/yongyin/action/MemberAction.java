/*
 * File Name: ConsignAction.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2008-1-14
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.action;


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
import com.china.center.common.PageSeparateTools;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.Mathematica;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.centet.yongyin.Helper;
import com.china.centet.yongyin.bean.ConsumeBean;
import com.china.centet.yongyin.bean.ExchangeBean;
import com.china.centet.yongyin.bean.MemberBean;
import com.china.centet.yongyin.bean.Role;
import com.china.centet.yongyin.bean.User;
import com.china.centet.yongyin.constant.Constant;
import com.china.centet.yongyin.dao.CustomerDAO;
import com.china.centet.yongyin.dao.ProductTypeDAO;
import com.china.centet.yongyin.manager.ConsumeManager;
import com.china.centet.yongyin.manager.MemeberManager;
import com.china.centet.yongyin.vo.ConsumeBeanVO;
import com.china.centet.yongyin.vo.ExchangeBeanVO;


/**
 * ��Ա��action
 * 
 * @author zhuzhu
 * @version 2008-1-14
 * @see
 * @since
 */
public class MemberAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private MemeberManager memeberManager = null;

    private ConsumeManager consumeManager = null;

    private ProductTypeDAO productTypeDAO = null;

    private CustomerDAO customerDAO = null;

    /**
     * default constructor
     */
    public MemberAction()
    {}

    /**
     * ��ҳ��ѯ��Ա
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryMembers(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        ConditionParse condtion = new ConditionParse();

        List<MemberBean> list = null;
        try
        {
            if (PageSeparateTools.isFirstLoad(request))
            {
                setCondition(request, condtion);

                int total = memeberManager.countByCondtion(condtion);

                PageSeparate page = new PageSeparate(total, Constant.PAGE_SIZE - 10);

                PageSeparateTools.initPageSeparate(condtion, page, request, "queryMembers");

                list = memeberManager.queryMemberByCondtion(condtion, page);
            }
            else
            {
                PageSeparateTools.processSeparate(request, "queryMembers");

                list = memeberManager.queryMemberByCondtion(PageSeparateTools.getCondition(
                    request, "queryMembers"), PageSeparateTools.getPageSeparate(request,
                    "queryMembers"));
            }
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "��ѯ��Աʧ��:" + e.getMessage());

            _logger.error("queryMembers", e);

            return mapping.findForward("error");
        }

        request.setAttribute("listMember", list);

        return mapping.findForward("queryMembers");
    }

    /**
     * ��ҳ��ѯ��Ա
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryMember(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        ConditionParse condtion = new ConditionParse();

        List<MemberBean> list = null;
        try
        {
            if (PageSeparateTools.isFirstLoad(request))
            {
                setCondition(request, condtion);

                int total = memeberManager.countByCondtion(condtion);

                PageSeparate page = new PageSeparate(total, Constant.PAGE_SIZE - 10);

                PageSeparateTools.initPageSeparate(condtion, page, request, "rptQueryMember");

                list = memeberManager.queryMemberByCondtion(condtion, page);
            }
            else
            {
                PageSeparateTools.processSeparate(request, "rptQueryMember");

                list = memeberManager.queryMemberByCondtion(PageSeparateTools.getCondition(
                    request, "rptQueryMember"), PageSeparateTools.getPageSeparate(request,
                    "rptQueryMember"));
            }
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "��ѯ��Աʧ��:" + e.getMessage());

            _logger.error("queryMembers", e);

            return mapping.findForward("error");
        }

        request.setAttribute("listMember", list);

        return mapping.findForward("rptQueryMember");
    }

    /**
     * ���ӻ�Ա
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward addMember(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        MemberBean bean = new MemberBean();

        BeanUtil.getBean(bean, request);

        User user = Helper.getUser(request);

        bean.setUserId(user.getId());

        bean.setLocationId(user.getLocationID());

        bean.setLogTime(TimeTools.now());

        try
        {
            memeberManager.addMember(bean);

            request.setAttribute(KeyConstant.MESSAGE, "�ɹ����ӻ�Ա:" + bean.getCardNo());
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "���ӻ�Աʧ��:" + e.getMessage());

            return mapping.findForward("error");
        }

        CommonTools.removeParamers(request);

        request.setAttribute("forward", 1);

        return queryMembers(mapping, form, request, reponse);
    }

    /**
     * ���ӻ�Ա���ֶһ�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward addExchange(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        ExchangeBean bean = new ExchangeBean();

        BeanUtil.getBean(bean, request);

        User user = Helper.getUser(request);

        bean.setUserId(user.getId());

        bean.setLogTime(TimeTools.now());

        try
        {
            consumeManager.addExchange(bean);

            request.setAttribute(KeyConstant.MESSAGE, "�ɹ����ӻ��ֶһ�");
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "����ʧ��:" + e.getMessage());

            return mapping.findForward("error");
        }

        request.setAttribute("forward", 1);

        return queryExchanges(mapping, form, request, reponse);
    }

    /**
     * ��Ա��ϸ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward findMember(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        String type = request.getParameter("type");

        MemberBean bean = memeberManager.findMemberById(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "��Ա������");

            return mapping.findForward("error");
        }

        BeanUtil.getBean(bean, request);

        request.setAttribute("bean", bean);

        if ("1".equals(type))
        {
            return mapping.findForward("updateMember");
        }

        // ��ô����ߵ�����

        return mapping.findForward("detailMember");
    }

    /**
     * �����Ӷһ���׼��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddExchange(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("memberId");

        MemberBean bean = memeberManager.findMemberById(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "��Ա������");

            return mapping.findForward("error");
        }

        BeanUtil.getBean(bean, request);

        request.setAttribute("bean", bean);

        // ��ô����ߵ�����

        return mapping.findForward("addExchange");
    }

    /**
     * �޸Ļ�Ա
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward updateMember(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        MemberBean bean = memeberManager.findMemberById(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "��Ա������");

            return mapping.findForward("error");
        }

        BeanUtil.getBean(bean, request);

        try
        {
            memeberManager.updateMember(bean);

            request.setAttribute(KeyConstant.MESSAGE, "�޸Ļ�Ա�ɹ�:" + bean.getCardNo());
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "�޸Ļ�Աʧ��:" + e.getMessage());

            return mapping.findForward("error");
        }

        CommonTools.removeParamers(request);

        request.setAttribute("forward", 1);

        return queryMembers(mapping, form, request, reponse);
    }

    /**
     * ɾ����Ա
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward delMember(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        MemberBean bean = memeberManager.findMemberById(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "��Ա������");

            return mapping.findForward("error");
        }

        if (Helper.getUser(request).getRole() != Role.SHOP)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "ֻ�е곤����ɾ����Ա");

            return mapping.findForward("error");
        }

        try
        {
            memeberManager.delMember(Helper.getUser(request), id);

            request.setAttribute(KeyConstant.MESSAGE, "ɾ����Ա�ɹ�:" + bean.getCardNo());
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "ɾ����Աʧ��:" + e.getMessage());

            return mapping.findForward("error");
        }

        request.setAttribute("forward", 1);

        return queryMembers(mapping, form, request, reponse);
    }

    /**
     * ��ҳ��ѯ��Ա���Ѽ�¼
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryConsumes(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        ConditionParse condtion = new ConditionParse();

        List<ConsumeBeanVO> list = null;
        try
        {
            if (PageSeparateTools.isFirstLoad(request))
            {
                setConsumesCondition(request, condtion);

                int total = consumeManager.countByCondtion(condtion);

                PageSeparate page = new PageSeparate(total, Constant.PAGE_SIZE - 10);

                PageSeparateTools.initPageSeparate(condtion, page, request, "queryConsumes");

                list = consumeManager.queryConsumesByCondtion(condtion, page);
            }
            else
            {
                PageSeparateTools.processSeparate(request, "queryConsumes");

                list = consumeManager.queryConsumesByCondtion(PageSeparateTools.getCondition(
                    request, "queryConsumes"), PageSeparateTools.getPageSeparate(request,
                    "queryConsumes"));
            }
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "��ѯ��Աʧ��:" + e.getMessage());

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        request.setAttribute("listConsume", list);

        double total = 0.0d;
        for (ConsumeBeanVO consumeBeanVO : list)
        {
            total += consumeBeanVO.getCost();
        }

        Mathematica math = new Mathematica(total);

        request.setAttribute("total", math.toString());

        return mapping.findForward("queryConsumes");
    }

    /**
     * ��ҳ��ѯ��Ա���Ѽ�¼
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryExchanges(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        ConditionParse condtion = new ConditionParse();

        List<ExchangeBeanVO> list = null;
        try
        {
            if (PageSeparateTools.isFirstLoad(request))
            {
                setExchangeCondition(request, condtion);

                int total = consumeManager.countExchangeBeanVOByCondtion(condtion);

                PageSeparate page = new PageSeparate(total, Constant.PAGE_SIZE - 10);

                PageSeparateTools.initPageSeparate(condtion, page, request, "queryExchanges");

                list = consumeManager.queryExchangeBeanVOsByCondtion(condtion, page);
            }
            else
            {
                PageSeparateTools.processSeparate(request, "queryExchanges");

                list = consumeManager.queryExchangeBeanVOsByCondtion(
                    PageSeparateTools.getCondition(request, "queryExchanges"),
                    PageSeparateTools.getPageSeparate(request, "queryExchanges"));
            }
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "��ѯʧ��:" + e.getMessage());

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        request.setAttribute("list", list);

        return mapping.findForward("queryExchanges");
    }

    /**
     * ���ӻ�Ա����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward addConsume(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        ConsumeBean bean = new ConsumeBean();

        BeanUtil.getBean(bean, request);

        User user = Helper.getUser(request);

        bean.setUserId(user.getId());

        bean.setLocationId(user.getLocationID());

        bean.setLogTime(TimeTools.now());

        try
        {
            consumeManager.addConsume(bean);

            request.setAttribute(KeyConstant.MESSAGE, "�ɹ����ӻ�Ա����");
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "���ӻ�Ա����ʧ��:" + e.getMessage());

            return mapping.findForward("error");
        }

        CommonTools.removeParamers(request);

        request.setAttribute("forward", 1);

        return queryConsumes(mapping, form, request, reponse);
    }

    /**
     * ������ϸ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward findConsume(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        ConsumeBeanVO bean = consumeManager.findConsumeVO(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "���Ѽ�¼������");

            return mapping.findForward("error");
        }

        request.setAttribute("bean", bean);

        // ��ô����ߵ�����
        return mapping.findForward("detailConsume");
    }

    private void setConsumesCondition(HttpServletRequest request, ConditionParse condtion)
    {
        String cardNo = request.getParameter("cardNo");

        if ( !StringTools.isNullOrNone(cardNo))
        {
            condtion.addCondition("MemberBean.cardNo", "like", cardNo);
        }

        String productName = request.getParameter("productName");

        if ( !StringTools.isNullOrNone(productName))
        {
            condtion.addCondition("Product.name", "like", productName);
        }

        String alogTime = request.getParameter("alogTime");

        if ( !StringTools.isNullOrNone(alogTime))
        {
            condtion.addCondition("ConsumeBean.logTime", ">=", alogTime);
        }

        String blogTime = request.getParameter("blogTime");

        if ( !StringTools.isNullOrNone(blogTime))
        {
            condtion.addCondition("ConsumeBean.logTime", "<=", blogTime);
        }

        // ��Աֻ�ܿ����Լ�¼������Ѽ�¼
        if (Helper.getUser(request).getRole() == Role.SHOPMEMBER)
        {
            condtion.addCondition("MemberBean.userId", "=", Helper.getUser(request).getId());
        }

        condtion.addCondition("order by ConsumeBean.id desc");
    }

    private void setExchangeCondition(HttpServletRequest request, ConditionParse condtion)
    {
        String memberId = request.getParameter("memberId");

        if ( !StringTools.isNullOrNone(memberId))
        {
            condtion.addCondition("ExchangeBean.memberId", "=", memberId);
        }

        condtion.addCondition("order by ExchangeBean.id desc");
    }

    private void setCondition(HttpServletRequest request, ConditionParse condtion)
    {
        String name = request.getParameter("name");

        if ( !StringTools.isNullOrNone(name))
        {
            condtion.addCondition("name", "like", name);
        }

        String cardNo = request.getParameter("cardNo");

        if ( !StringTools.isNullOrNone(cardNo))
        {
            condtion.addCondition("cardNo", "like", cardNo);
        }

        String grade = request.getParameter("grade");

        if ( !StringTools.isNullOrNone(grade))
        {
            condtion.addIntCondition("grade", "=", grade);
        }

        String type = request.getParameter("type");

        if ( !StringTools.isNullOrNone(type))
        {
            condtion.addIntCondition("type", "=", type);
        }

        condtion.addCondition("order by id desc");
    }

    /**
     * @return the memeberManager
     */
    public MemeberManager getMemeberManager()
    {
        return memeberManager;
    }

    /**
     * @param memeberManager
     *            the memeberManager to set
     */
    public void setMemeberManager(MemeberManager memeberManager)
    {
        this.memeberManager = memeberManager;
    }

    /**
     * @return the consumeManager
     */
    public ConsumeManager getConsumeManager()
    {
        return consumeManager;
    }

    /**
     * @param consumeManager
     *            the consumeManager to set
     */
    public void setConsumeManager(ConsumeManager consumeManager)
    {
        this.consumeManager = consumeManager;
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

}
