package com.china.center.common.taglib;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import com.china.center.jdbc.util.PageSeparate;


/**
 * ҳ���׼�ؼ�
 * 
 * @author zhuzhu
 * @version 2007-3-14
 * @see PageTurning
 * @since
 */

public class PageTurning extends BodyTagCenterSupport
{
    private String url = "";

    /**
     * Ĭ�Ϲ�����
     */
    public PageTurning()
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

        buffer.append(
            "<input type='button' id=preButton style='cursor: pointer;" + dis1
                + "' class='button_class' accesskey='B' value='&nbsp;��һҳ&nbsp;'").append(
            preButtonD).append(line);
        buffer.append(
            "onclick='javaScript:document.location.href=\"" + this.url + "&page=previous\"'>").append(
            line);

        buffer.append(
            "<input type='button' id=nextButton style='cursor: pointer;" + dis2
                + "' class='button_class' accesskey='N' value='&nbsp;��һҳ&nbsp;'").append(
            nextButtonD).append(line);

        buffer.append(
            "onclick='javaScript:document.location.href=\"" + this.url + "&page=next\"'>").append(
            line);

        buffer.append("</tr></table>").append(line);

    }

    private String getPageAttributeNameInSession(HttpServletRequest request)
    {
        return "A_page";
    }

    private void writeEnd(StringBuffer buffer)
    {}

    /**
     * @return the url
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

}
