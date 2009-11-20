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
import org.springframework.beans.support.PagedListHolder;

import com.china.center.annosql.tools.BeanTools;
import com.china.center.common.ConditionParse;
import com.china.center.common.KeyConstant;
import com.china.center.common.MYException;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.StringTools;
import com.china.centet.yongyin.Helper;
import com.china.centet.yongyin.bean.DepotpartBean;
import com.china.centet.yongyin.bean.Product;
import com.china.centet.yongyin.bean.ProductAmount;
import com.china.centet.yongyin.bean.StorageBean;
import com.china.centet.yongyin.bean.StorageRelationBean;
import com.china.centet.yongyin.bean.User;
import com.china.centet.yongyin.bean.helper.LocationHelper;
import com.china.centet.yongyin.constant.Constant;
import com.china.centet.yongyin.dao.DepotpartDAO;
import com.china.centet.yongyin.dao.ProductDAO;
import com.china.centet.yongyin.dao.StorageDAO;
import com.china.centet.yongyin.manager.DepotpartManager;
import com.china.centet.yongyin.manager.ProductManager;
import com.china.centet.yongyin.manager.StorageManager;
import com.china.centet.yongyin.vo.StorageBeanVO;


/**
 * �����ʹ�λ��action
 * 
 * @author admin
 * @version [�汾��, 2008-1-5]
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾]
 */
public class DepotpartAndStorageAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private DepotpartManager depotpartManager = null;

    private StorageManager storageManager = null;

    private ProductManager productManager = null;

    private DepotpartDAO depotpartDAO = null;

    private StorageDAO storageDAO = null;

    private ProductDAO productDAO = null;

    /**
     * Description: ��ѯ�����µĲ���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     * @since <IVersion>
     */
    public ActionForward queryDepotpart(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        User oprUser = Helper.getUser(request);

        if ( !LocationHelper.isSystemLocation(oprUser.getLocationID()))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "ֻ���ܲ����ܲ�������!");

            return mapping.findForward("error");
        }

        ConditionParse condition = new ConditionParse();

        condition.addCondition("locationId", "=", oprUser.getLocationID());

        List<DepotpartBean> list = depotpartDAO.queryDepotpartByCondition(condition);

        request.setAttribute("depotpartList", list);

        return mapping.findForward("listDepotpart");
    }

    /**
     * Description: ��ѯ�����µĲ���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     * @since <IVersion>
     */
    public ActionForward queryDepotpartForMove(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse reponse)
        throws ServletException
    {
        User oprUser = Helper.getUser(request);

        ConditionParse condition = new ConditionParse();

        condition.addCondition("locationId", "=", oprUser.getLocationID());

        List<DepotpartBean> list = depotpartDAO.queryDepotpartByCondition(condition);

        request.setAttribute("depotpartList", list);

        return mapping.findForward("moveDepotpart");
    }

    /**
     * Description: ��ѯ�����µĴ�λ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     * @since <IVersion>
     */
    public ActionForward queryStorage(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String depotpartId = request.getParameter("depotpartId");

        CommonTools.saveParamers(request);

        if (StringTools.isNullOrNone(depotpartId))
        {
            depotpartId = (String)request.getAttribute("depotpartId");

            if (StringTools.isNullOrNone(depotpartId))
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "�����²���");
                return queryDepotpart(mapping, form, request, reponse);
            }
        }

        List<StorageBeanVO> list = storageDAO.listStorageVO(depotpartId);

        StorageBeanVO vo = new StorageBeanVO();

        vo.setId("0");

        vo.setName("Ĭ�ϴ�λ");

        vo.setDepotpartName("�޲�������");

        list.add(vo);

        for (StorageBeanVO storageBeanVO : list)
        {
            List<StorageRelationBean> relations = storageDAO.queryStorageRelationByStorageId(
                depotpartId, storageBeanVO.getId());

            setStorageBeanVO(storageBeanVO, relations, false);
        }

        request.setAttribute("listStorage", list);

        return mapping.findForward("listStorage");
    }

    /**
     * Description: ��ѯ�����µĲ���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     * @since <IVersion>
     */
    public ActionForward findStorage(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");
        String modfiy = request.getParameter("modfiy");
        String depotpartId = request.getParameter("depotpartId");

        if (StringTools.isNullOrNone(id))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "�����²���");

            return queryDepotpart(mapping, form, request, reponse);
        }

        if ("0".equals(id) && !"2".equals(modfiy))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "Ĭ�ϴ�λ�����޸�");

            return mapping.findForward("error");
        }

        StorageBean storageBean = storageManager.findStorageById(id);

        if (storageBean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE,
                BeanTools.getEntryName(StorageBean.class) + "������");

            return queryDepotpart(mapping, form, request, reponse);
        }

        List<StorageRelationBean> relations = storageDAO.queryStorageRelationByStorageId(
            depotpartId, id);

        for (StorageRelationBean storageRelationBean : relations)
        {
            Product product = productManager.findProductById(storageRelationBean.getProductId());

            if (product != null)
            {
                storageRelationBean.setProductName(product.getName() + "     ������"
                                                   + storageRelationBean.getAmount() + "��");
            }
        }

        StorageBeanVO vo = getStorageBeanVO(storageBean, relations);

        request.setAttribute("bean", vo);

        request.setAttribute("relations", relations);

        if ("2".equals(modfiy))
        {
            List<StorageBean> list = storageDAO.queryStorageByDepotpartId(depotpartId);

            request.setAttribute("list", list);

            return mapping.findForward("defaultStorage");
        }
        else
        {
            return mapping.findForward("updateStorage");
        }
    }

    /**
     * Description:���VO
     * 
     * @param storageBean
     * @return
     * @since <IVersion>
     */
    private StorageBeanVO getStorageBeanVO(StorageBean storageBean,
                                           List<StorageRelationBean> relations)
    {
        StorageBeanVO vo = new StorageBeanVO();

        BeanUtil.copyProperties(vo, storageBean);

        DepotpartBean bean = depotpartManager.findDepotpartById(storageBean.getDepotpartId());

        if (bean != null)
        {
            vo.setDepotpartName(bean.getName());
        }

        setStorageBeanVO(vo, relations, true);

        return vo;
    }

    private void setStorageBeanVO(StorageBeanVO vo, List<StorageRelationBean> relations,
                                  boolean zhe)
    {
        String temp = "";
        String ids = "";
        Product product = null;
        for (StorageRelationBean storageRelationBean : relations)
        {
            product = productManager.findProductById(storageRelationBean.getProductId());

            if (product != null)
            {
                if (zhe)
                {
                    temp += product.getName() + "   ������" + storageRelationBean.getAmount() + "��"
                            + "\r\n";
                }
                else
                {
                    temp += product.getName() + " ";
                }

                ids += product.getId() + "#";
            }
        }

        vo.setProductId(ids);

        vo.setProductName(temp);

    }

    /**
     * Description: ��ѯ�����µĲ���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     * @since <IVersion>
     */
    public ActionForward findDepotpart(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        if (StringTools.isNullOrNone(id))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "�����²���");

            return queryDepotpart(mapping, form, request, reponse);
        }

        DepotpartBean depotpartBean = depotpartManager.findDepotpartById(id);

        if (depotpartBean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE,
                BeanTools.getEntryName(DepotpartBean.class) + "������");

            return queryDepotpart(mapping, form, request, reponse);
        }

        request.setAttribute("depotpart", depotpartBean);

        String modfiy = request.getParameter("modfiy");

        if (modfiy == null)
        {
            return mapping.findForward("detailDepotpart");
        }
        else
        {
            return mapping.findForward("updateDepotpart");
        }
    }

    /**
     * Description: ɾ�������µĲ���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     * @since <IVersion>
     */
    public ActionForward delDepotpart(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        if (StringTools.isNullOrNone(id))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "�����²���");

            return queryDepotpart(mapping, form, request, reponse);
        }

        try
        {
            depotpartManager.delDepotpart(id);
        }
        catch (MYException e)
        {
            _logger.warn(e, e);
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

            return queryDepotpart(mapping, form, request, reponse);
        }

        request.setAttribute(KeyConstant.MESSAGE, "�ɹ�ɾ������");

        return queryDepotpart(mapping, form, request, reponse);
    }

    /**
     * Description: ɾ�������µĲ���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     * @since <IVersion>
     */
    public ActionForward delStorage(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        if (StringTools.isNullOrNone(id))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "�����²���");

            return queryStorage(mapping, form, request, reponse);
        }

        if ("0".equals(id))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "Ĭ�ϴ�λ����ɾ��");

            return mapping.findForward("error");
        }

        try
        {
            storageManager.delStorage(id);
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

            return queryStorage(mapping, form, request, reponse);
        }

        request.setAttribute(KeyConstant.MESSAGE, "�ɹ�ɾ����λ");

        return queryStorage(mapping, form, request, reponse);
    }

    /**
     * Description: ��ѯ�����µĲ���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     * @since <IVersion>
     */
    public ActionForward addDepotpart(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        User oprUser = Helper.getUser(request);

        DepotpartBean depotpartBean = new DepotpartBean();

        BeanUtil.getBean(depotpartBean, request);

        depotpartBean.setLocationId(oprUser.getLocationID());

        try
        {
            depotpartManager.addDepotpart(depotpartBean);
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

            return queryDepotpart(mapping, form, request, reponse);
        }

        return queryDepotpart(mapping, form, request, reponse);
    }

    /**
     * Description: ���Ӵ�λ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     * @since <IVersion>
     */
    public ActionForward addStorage(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        StorageBean bean = new StorageBean();

        CommonTools.saveParamers(request);

        BeanUtil.getBean(bean, request);

        DepotpartBean temp = depotpartManager.findDepotpartById(bean.getDepotpartId());

        if (temp == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "���������ڣ������²���");

            return queryStorage(mapping, form, request, reponse);
        }

        bean.setLocationId(temp.getLocationId());

        try
        {
            storageManager.addStorage(bean);
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

            return queryStorage(mapping, form, request, reponse);
        }

        return queryStorage(mapping, form, request, reponse);
    }

    /**
     * Description: ���Ӵ�λ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     * @since <IVersion>
     */
    public ActionForward updateStorage(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        StorageBean bean = new StorageBean();

        CommonTools.saveParamers(request);

        BeanUtil.getBean(bean, request);

        DepotpartBean temp = depotpartManager.findDepotpartById(bean.getDepotpartId());

        if (temp == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "���������ڣ������²���");

            return queryStorage(mapping, form, request, reponse);
        }

        bean.setLocationId(temp.getLocationId());

        try
        {
            storageManager.updateStorage(bean);
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

            return queryStorage(mapping, form, request, reponse);
        }

        return queryStorage(mapping, form, request, reponse);
    }

    /**
     * Ĭ�ϴ�λ�Ĳ�Ʒ��ת��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward changeDefaultStorage(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse reponse)
        throws ServletException
    {
        String depotpartId = request.getParameter("depotpartId");

        String dirStorage = request.getParameter("dirStorage");

        String productId = request.getParameter("productId");

        String[] productIds = productId.split("#");

        try
        {
            storageManager.changeDefaultStorage(depotpartId, productIds, dirStorage);
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

            return queryStorage(mapping, form, request, reponse);
        }

        request.setAttribute(KeyConstant.MESSAGE, "��Ʒ��λ��ת�Ƴɹ�");

        return queryStorage(mapping, form, request, reponse);
    }

    /**
     * Description: ��ѯ�����µĲ���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     * @since <IVersion>
     */
    public ActionForward updateDepotpart(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        User oprUser = Helper.getUser(request);

        DepotpartBean depotpartBean = new DepotpartBean();

        BeanUtil.getBean(depotpartBean, request);

        depotpartBean.setLocationId(oprUser.getLocationID());

        try
        {
            depotpartManager.updateDepotpart(depotpartBean);
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

            return queryDepotpart(mapping, form, request, reponse);
        }

        return queryDepotpart(mapping, form, request, reponse);
    }

    /**
     * queryProduct
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryProduct(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String firstLoad = request.getParameter("firstLoad");

        String rpt = request.getParameter("rpt");

        String depotpartId = request.getParameter("depotpartId");

        String depotpartType = request.getParameter("depotpartType");

        DepotpartBean depotpartBean = null;

        if ( !StringTools.isNullOrNone(depotpartId))
        {
            depotpartBean = depotpartDAO.findDepotpartById(depotpartId);
        }

        if ("1".equals(firstLoad))
        {
            String name = request.getParameter("name");

            String code = request.getParameter("code");

            String storageName = request.getParameter("storageName");

            ConditionParse condtion = new ConditionParse();

            if ( !StringTools.isNullOrNone(name))
            {
                condtion.addCondition("t0.name", "like", name);
            }

            if ( !StringTools.isNullOrNone(code))
            {
                condtion.addCondition("t0.code", "like", code);
            }

            // storageName
            if ( !StringTools.isNullOrNone(storageName))
            {
                condtion.addCondition("t2.name", "like", storageName);
            }

            if ( !StringTools.isNullOrNone(depotpartId))
            {
                condtion.addCondition("t1.depotpartId", "=", depotpartId);
            }

            if ( !StringTools.isNullOrNone(depotpartType))
            {
                condtion.addIntCondition("t3.type", "=", depotpartType);
            }

            List<StorageRelationBean> list = null;

            list = storageDAO.queryStorageRelationByCondition(condtion, true);

            for (StorageRelationBean storageRelationBean : list)
            {
                ProductAmount amount = productDAO.findProductAmount(
                    storageRelationBean.getProductId(), Constant.SYSTEM_LOCATION);

                if (amount != null)
                {
                    storageRelationBean.setMayAmount(amount.getNum());
                }
            }

            // ��ҳ�Ĺؼ����󣬰�list����ȥ�γ�ҳ��list
            PagedListHolder pageList = new PagedListHolder(list);
            // һ����¼һҳ,��ʵ�������У�ҳ�����Ӧ�������õ�

            pageList.setPageSize(Constant.PAGE_SIZE - 5);
            request.getSession().setAttribute("ProductList", pageList);

            request.setAttribute("productList", pageList.getPageList());
        }
        else
        {
            // ���������firstLoad˵�������Ѿ���Session�����ˣ�ֱ�Ӵ�sessionȡ����
            PagedListHolder pageList = (PagedListHolder)request.getSession().getAttribute(
                "ProductList");

            // page�����Ǵ�ҳ�洫���Ŀ��Ʒ�ҳ�Ĳ���
            String page = request.getParameter("page");
            if ("next".equals(page))
            {
                pageList.nextPage();
            }
            else if ("previous".equals(page))
            {
                pageList.previousPage();
            }

            request.setAttribute("productList", pageList.getPageList());
        }

        request.setAttribute("depotpartBean", depotpartBean);

        ConditionParse condition = new ConditionParse();

        List<DepotpartBean> list = depotpartDAO.queryDepotpartByCondition(condition);

        // ������еĲ���
        request.setAttribute("depotpartList", list);

        if ("1".equals(rpt))
        {
            return mapping.findForward("rptProductList1");
        }

        // ȫ�ֿ�Ĳ�ѯ
        if ("2".equals(rpt) || "3".equals(rpt))
        {
            return mapping.findForward("listProductFour");
        }

        return mapping.findForward("productList");
    }

    /**
     * @return ���� depotpartManager
     */
    public DepotpartManager getDepotpartManager()
    {
        return depotpartManager;
    }

    /**
     * @param ��depotpartManager���и�ֵ
     */
    public void setDepotpartManager(DepotpartManager depotpartManager)
    {
        this.depotpartManager = depotpartManager;
    }

    /**
     * @return ���� depotpartDAO
     */
    public DepotpartDAO getDepotpartDAO()
    {
        return depotpartDAO;
    }

    /**
     * @param ��depotpartDAO���и�ֵ
     */
    public void setDepotpartDAO(DepotpartDAO depotpartDAO)
    {
        this.depotpartDAO = depotpartDAO;
    }

    /**
     * @return the storageDAO
     */
    public StorageDAO getStorageDAO()
    {
        return storageDAO;
    }

    /**
     * @param storageDAO
     *            the storageDAO to set
     */
    public void setStorageDAO(StorageDAO storageDAO)
    {
        this.storageDAO = storageDAO;
    }

    /**
     * @return ���� storageManager
     */
    public StorageManager getStorageManager()
    {
        return storageManager;
    }

    /**
     * @param ��storageManager���и�ֵ
     */
    public void setStorageManager(StorageManager storageManager)
    {
        this.storageManager = storageManager;
    }

    /**
     * @return ���� productManager
     */
    public ProductManager getProductManager()
    {
        return productManager;
    }

    /**
     * @param ��productManager���и�ֵ
     */
    public void setProductManager(ProductManager productManager)
    {
        this.productManager = productManager;
    }

    /**
     * @return the productDAO
     */
    public ProductDAO getProductDAO()
    {
        return productDAO;
    }

    /**
     * @param productDAO
     *            the productDAO to set
     */
    public void setProductDAO(ProductDAO productDAO)
    {
        this.productDAO = productDAO;
    }
}
