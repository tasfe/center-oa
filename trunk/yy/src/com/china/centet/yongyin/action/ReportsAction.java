/**
 * File Name: ReportsAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-2-18<br>
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.action;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.china.center.common.ConditionParse;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.centet.yongyin.Helper;
import com.china.centet.yongyin.bean.DepotpartBean;
import com.china.centet.yongyin.bean.Product;
import com.china.centet.yongyin.bean.StatProductBean;
import com.china.centet.yongyin.bean.StorageBean;
import com.china.centet.yongyin.bean.StorageLogBean;
import com.china.centet.yongyin.bean.StorageRelationBean;
import com.china.centet.yongyin.dao.DepotpartDAO;
import com.china.centet.yongyin.dao.ProductDAO;
import com.china.centet.yongyin.dao.StorageDAO;


/**
 * ����
 * 
 * @author zhuzhu
 * @version 2008-2-18
 * @see
 * @since
 */
public class ReportsAction extends DispatchAction
{
    private StorageDAO storageDAO = null;

    private ProductDAO productDAO = null;

    private DepotpartDAO depotpartDAO = null;

    /**
     * default constructor
     */
    public ReportsAction()
    {}

    /**
     * �ṩ�̵㱨����������춯�Ĳ�Ʒ������<br>
     * �ṩ����ʱ��Ҫ�ֲ�λ�ṩ��������ṹ����λ��Ʒ����ԭʼ�������춯������<br>
     * ��ǰ��������ѯ����ʱ�䣬�������̵����ԣ�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward statReports(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        ConditionParse condition = new ConditionParse();

        setCondition(request, condition);

        // ��ȡָ��ʱ���ڵĲ������춯
        List<StorageLogBean> logs = storageDAO.queryStorageLogByCondition(condition);

        List<StatProductBean> statList = statProduct(logs, request);

        request.getSession().setAttribute("statList", statList);

        List<DepotpartBean> list = depotpartDAO.listDepotpart();

        request.setAttribute("depotpartList", list);

        return mapping.findForward("queryReports");
    }

    /**
     * ��ѯ������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryForStatReports(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse reponse)
        throws ServletException
    {
        List<DepotpartBean> list = depotpartDAO.listDepotpart();

        request.setAttribute("depotpartList", list);

        return mapping.findForward("queryReports");
    }

    /**
     * listStorageLog
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward listStorageLog(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        Map<String, List<StorageLogBean>> map = (Map<String, List<StorageLogBean>>)request.getSession().getAttribute(
            "logsMap");

        String productId = request.getParameter("productId");

        List<StorageLogBean> lits = map.get(productId);

        List<StatProductBean> statList = new ArrayList<StatProductBean>();

        for (StorageLogBean storageLogBean : lits)
        {
            StatProductBean bean = new StatProductBean();

            BeanUtil.copyProperties(bean, storageLogBean);

            getProductReprot(bean);

            statList.add(bean);
        }

        request.setAttribute("listStorageLog", statList);

        return mapping.findForward("listStorageLog");
    }

    /**
     * listStorageLog2
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward listStorageLog2(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        ConditionParse condition = new ConditionParse();

        condition.addCondition("and t1.productId = t2.id");

        String productId = request.getParameter("productId");

        String depotpartId = request.getParameter("depotpartId");

        condition.addCondition("t1.productId", "=", productId);

        condition.addCondition("t1.depotpartId", "=", depotpartId);

        condition.addCondition("order by t1.logTime");

        // ��ȡָ��ʱ���ڵĲ������춯
        List<StorageLogBean> lits = storageDAO.queryStorageLogByCondition(condition);

        List<StatProductBean> statList = new ArrayList<StatProductBean>();

        for (StorageLogBean storageLogBean : lits)
        {
            StatProductBean bean = new StatProductBean();

            BeanUtil.copyProperties(bean, storageLogBean);

            getProductReprot(bean);

            statList.add(bean);
        }

        request.setAttribute("listStorageLog", statList);

        return mapping.findForward("listStorageLog");
    }

    public ActionForward export(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        OutputStream out = null;

        List<StatProductBean> statList = (List<StatProductBean>)request.getSession().getAttribute(
            "statList");

        String filenName = TimeTools.now("MMddHHmmss") + ".xls";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WritableWorkbook wwb = null;
        WritableSheet ws = null;

        try
        {
            out = reponse.getOutputStream();

            wwb = Workbook.createWorkbook(out);
            ws = wwb.createSheet("sheel1", 0);
            int i = 0, j = 0;

            WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
                jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);

            WritableCellFormat format = new WritableCellFormat(font);

            ws.addCell(new Label(j++ , i, "����", format));
            ws.addCell(new Label(j++ , i, "��λ", format));
            ws.addCell(new Label(j++ , i, "��Ʒ", format));
            ws.addCell(new Label(j++ , i, "ԭʼ����", format));
            ws.addCell(new Label(j++ , i, "�춯����", format));
            ws.addCell(new Label(j++ , i, "��ǰ����", format));

            // NumberFormat nf = new NumberFormat("###,##0.00");
            NumberFormat nf = new NumberFormat("###,##0");

            jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf);

            for (StatProductBean statProductBean : statList)
            {
                j = 0;
                i++ ;

                ws.addCell(new Label(j++ , i, statProductBean.getDepotpartName()));
                ws.addCell(new Label(j++ , i, statProductBean.getStorageName()));
                ws.addCell(new Label(j++ , i, statProductBean.getProductName()));
                ws.addCell(new jxl.write.Number(j++ , i, statProductBean.getPreAmount(), wcfN));
                ws.addCell(new jxl.write.Number(j++ , i, statProductBean.getChangeAmount(), wcfN));
                ws.addCell(new jxl.write.Number(j++ , i, statProductBean.getCurrentAmount(), wcfN));
            }

            wwb.write();

        }
        catch (Exception e)
        {
            return null;
        }
        finally
        {
            if (wwb != null)
            {
                try
                {
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

    public ActionForward exportAll(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        OutputStream out = null;

        List<StorageRelationBean> statList = storageDAO.queryStorageRelationByCondition(
            new ConditionParse(), false);

        String filenName = TimeTools.now("MMddHHmmss") + "_ALL.xls";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WritableWorkbook wwb = null;

        WritableSheet ws = null;

        try
        {
            out = reponse.getOutputStream();

            wwb = Workbook.createWorkbook(out);
            ws = wwb.createSheet(TimeTools.now("MMddHHmmss") + "ȫ���̵�", 0);
            int i = 0, j = 0;

            WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
                jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);

            WritableCellFormat format = new WritableCellFormat(font);

            ws.addCell(new Label(j++ , i, "����", format));
            ws.addCell(new Label(j++ , i, "��λ", format));
            ws.addCell(new Label(j++ , i, "��Ʒ", format));
            ws.addCell(new Label(j++ , i, "��Ʒ����", format));
            ws.addCell(new Label(j++ , i, "����", format));

            for (StorageRelationBean statProductBean : statList)
            {
                j = 0;
                i++ ;

                ws.addCell(new Label(j++ , i, statProductBean.getDepotpartName()));
                ws.addCell(new Label(j++ , i, statProductBean.getStorageName()));
                ws.addCell(new Label(j++ , i, statProductBean.getProductName()));
                ws.addCell(new Label(j++ , i, statProductBean.getProductCode()));
                ws.addCell(new Label(j++ , i, String.valueOf(statProductBean.getAmount())));
            }

            wwb.write();

        }
        catch (Exception e)
        {
            return null;
        }
        finally
        {
            if (wwb != null)
            {
                try
                {
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
     * ͳ��
     * 
     * @param logs
     */
    private List<StatProductBean> statProduct(List<StorageLogBean> logs, HttpServletRequest request)
    {
        Map<String, List<StorageLogBean>> map = new HashMap<String, List<StorageLogBean>>();

        // �ѷ���ͳ�ƵĲ�Ʒ�ŵ�map����[����+product:Logs]
        for (StorageLogBean storageLogBean : logs)
        {
            String key = storageLogBean.getProductId() + ";" + storageLogBean.getDepotpartId();

            if ( !map.containsKey(key))
            {
                map.put(key, new ArrayList<StorageLogBean>());
            }

            map.get(key).add(storageLogBean);
        }

        // ����session�����ѯ
        request.getSession().setAttribute("logsMap", map);

        // ͳ��
        return statInner(map);
    }

    /**
     * ͳ�ƿ�ʼ
     * 
     * @param map
     * @return
     */
    private List<StatProductBean> statInner(Map<String, List<StorageLogBean>> map)
    {
        List<StatProductBean> statList = new ArrayList<StatProductBean>();

        List<StorageLogBean> temp = null;

        // ͳ��ÿ����Ʒ
        for (Map.Entry<String, List<StorageLogBean>> entry : map.entrySet())
        {
            StatProductBean bean = new StatProductBean();

            temp = entry.getValue();

            boolean frist = true;
            int init = 0, change = 0;
            for (StorageLogBean log : temp)
            {
                if (frist)
                {
                    init = log.getPreAmount();

                    BeanUtil.copyProperties(bean, log);

                    frist = false;
                }

                change += log.getChangeAmount();
            }

            bean.setPreAmount(init);

            bean.setChangeAmount(change);

            // ��õ�ǰ������
            StorageRelationBean relation = storageDAO.findStorageRelationByDepotpartAndProcut(
                bean.getDepotpartId(), bean.getProductId());

            if (relation == null)
            {
                bean.setCurrentAmount(0);
            }
            else
            {
                bean.setCurrentAmount(relation.getAmount());
            }

            getProductReprot(bean);

            statList.add(bean);
        }

        return statList;
    }

    /**
     * @param bean
     */
    private void getProductReprot(StatProductBean bean)
    {
        // ��ò�Ʒ
        Product pp = productDAO.findProductById(bean.getProductId());

        if (pp != null)
        {
            bean.setProductName(pp.getName());
        }

        StorageBean sb = storageDAO.findStorageById(bean.getStorageId());
        if (sb != null)
        {
            bean.setStorageName(sb.getName());
        }

        DepotpartBean db = depotpartDAO.findDepotpartById(bean.getDepotpartId());

        if (db != null)
        {
            bean.setDepotpartName(db.getName());
        }
    }

    private void setCondition(HttpServletRequest request, ConditionParse condition)
    {
        condition.addCondition("t1.locationId", "=", Helper.getCurrentLocationId(request));

        String beginDate = request.getParameter("beginDate");

        condition.addCondition("and t1.productId = t2.id");

        if ( !StringTools.isNullOrNone(beginDate))
        {
            condition.addCondition("logTime", ">=", beginDate + " 00:00:00");
        }

        String endDate = request.getParameter("endDate");

        if ( !StringTools.isNullOrNone(endDate))
        {
            condition.addCondition("logTime", "<=", endDate + " 23:59:59");
        }

        String depotpartId = request.getParameter("depotpartId");

        if ( !StringTools.isNullOrNone(depotpartId))
        {
            condition.addCondition("depotpartId", "=", depotpartId);
        }

        String type = request.getParameter("type");

        if ( !StringTools.isNullOrNone(type))
        {
            condition.addCondition("t2.type", "=", type);
        }

        condition.addCondition("order by t1.id");
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

    /**
     * @return the depotpartDAO
     */
    public DepotpartDAO getDepotpartDAO()
    {
        return depotpartDAO;
    }

    /**
     * @param depotpartDAO
     *            the depotpartDAO to set
     */
    public void setDepotpartDAO(DepotpartDAO depotpartDAO)
    {
        this.depotpartDAO = depotpartDAO;
    }

}
