/*
 * �ļ�����PageStart.java
 * ��Ȩ��Copyright 2002-2007 centerchina Tech. Co. Ltd. All Rights Reserved.
 * ������
 * �޸��ˣ�zhuzhu
 * �޸�ʱ�䣺2007-3-14
 * ���ٵ��ţ�
 * �޸ĵ��ţ�
 * �޸����ݣ�����
 */
package com.china.center.common.taglib;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import com.china.center.jdbc.util.PageSeparate;


/**
 * ҳ���׼�ؼ�
 * 
 * @author zhuzhu
 * @version 2007-3-14
 * @see PageFormTurning
 * @since
 */

public class PageFormTurning extends BodyTagCenterSupport
{
    private String form = "";

    private String method = "";

    private String action = "";

    /**
     * Ĭ�Ϲ�����
     */
    public PageFormTurning()
    {}

    public int doStartTag()
        throws JspException
    {
        // ҳ����ʾ���ַ�
        StringBuffer buffer = new StringBuffer();

        writeStart(buffer);

        this.writeContext(buffer.toString());
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag()
        throws JspException
    {
        // ҳ����ʾ���ַ�
        StringBuffer buffer = new StringBuffer();

        writeEnd(buffer);

        this.writeContext(buffer.toString());
        return EVAL_PAGE;
    }

    private void writeStart(StringBuffer buffer)
    {
        HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();

        // <table width='100%' border='0' cellpadding='0' cellspacing='0'
        // align='center' class='table1'>
        // <tr>
        // <td colspan='8' align='right' height='25'>
        // �ܼ�¼:${A_page.rowCount}&nbsp;ҳ��:${A_page.nowPage}/${A_page.pageCount}
        // <input type='button' id=preButton style='cursor: pointer;' class='button_class' value='&nbsp;��һҳ&nbsp;'
        // onclick='javaScript:document.location.href='./common.do?method=queryCustmer&page=previous''>
        // </a>
        // </c:if> <c:if test='${next}'>
        // <input type='button' id=nextButton style='cursor: pointer;' class='button_class' value='&nbsp;��һҳ&nbsp;'
        // onclick='javaScript:document.location.href='./common.do?method=queryCustmer&page=next''>
        // </c:if></td>
        // </tr>
        // </table>

        PageSeparate page = (PageSeparate)request.getSession().getAttribute(
            getPageAttributeNameInSession(request));

        if (page == null)
        {
            return;
        }

        buffer.append(
            "<table width='100%' border='0' cellpadding='0' cellspacing='0' "
                + "align='center' class='table1'").append(line);

        buffer.append("<tr><td colspan='8' align='right' height='25'>").append(line);
        buffer.append(
            "�ܼ�¼:" + page.getRowCount() + "&nbsp;ҳ��:" + page.getNowPage() + "/"
                + page.getPageCount() + "").append("&nbsp;").append(line);

        String preButtonD = "";
        String nextButtonD = "";

        String dis1 = "";
        String dis2 = "";
        if ( !page.hasPrevPage())
        {
            preButtonD = " disabled='disabled' ";

            dis1 = "color: gray;";
        }

        if ( !page.hasNextPage())
        {
            nextButtonD = " disabled='disabled' ";

            dis2 = "color: gray;";
        }

        buffer.append("<input type='hidden' id=page name=page>").append(
            "<input type='button' id=preButton style='cursor: pointer;" + dis1
                + "' class='button_class' accesskey='B' value='&nbsp;��һҳ&nbsp;'").append(
            preButtonD).append(line);
        // previous
        buffer.append("onclick='" + getJS("previous") + "'>").append(line);

        buffer.append(
            "<input type='button' id=nextButton style='cursor: pointer;" + dis2
                + "' class='button_class' accesskey='N' value='&nbsp;��һҳ&nbsp;'").append(
            nextButtonD).append(line);

        buffer.append("onclick='" + getJS("next") + "'>").append(line);

        buffer.append("</tr></table>").append(line);
    }

    private String getPageAttributeNameInSession(HttpServletRequest request)
    {
        return "A_page";
    }

    private String getJS(String page)
    {
        String js = "javaScript:";
        if (isNullOrNone(this.method))
        {
            js += this.form + ".action = \"" + this.action + "\";";
        }
        else
        {
            js += this.form + ".method.value = \"" + this.method + "\";";
        }

        js += this.form + ".page.value = \"" + page + "\";if(window.$Dbuttons){$Dbuttons(true);";

        js += this.form + ".submit();}else{" + this.form + ".submit();}";

        return js;
    }

    private void writeEnd(StringBuffer buffer)
    {}

    /**
     * @return the form
     */
    public String getForm()
    {
        return form;
    }

    /**
     * @return the method
     */
    public String getMethod()
    {
        return method;
    }

    /**
     * @return the action
     */
    public String getAction()
    {
        return action;
    }

    /**
     * @param form
     *            the form to set
     */
    public void setForm(String form)
    {
        this.form = form;
    }

    /**
     * @param method
     *            the method to set
     */
    public void setMethod(String method)
    {
        this.method = method;
    }

    /**
     * @param action
     *            the action to set
     */
    public void setAction(String action)
    {
        this.action = action;
    }

}
