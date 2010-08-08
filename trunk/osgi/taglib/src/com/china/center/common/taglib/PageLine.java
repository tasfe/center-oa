package com.china.center.common.taglib;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;


/**
 * 页面开始标签
 * 
 * @author ZHUZHU
 * @version 2007-3-14
 * @see PageLine
 * @since
 */

public class PageLine extends BodyTagCenterSupport
{
    private Object object = null;

    // 0为初始的分割线，1为结尾的分割线
    private String flag = "0";

    private String url = "";

    /**
     * 默认构建器
     */
    public PageLine()
    {}

    public int doStartTag()
        throws JspException
    {
        HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();

        this.url = request.getContextPath() + TagLibConstant.DEST_FOLDER_NAME;
        return 2;
    }

    public int doEndTag()
        throws JspException
    {
        // 页面显示的字符
        StringBuffer buffer = new StringBuffer();

        writePageLine(buffer);

        this.writeContext(buffer.toString());
        return EVAL_PAGE;
    }

    private void writePageLine(StringBuffer buffer)
    {
        String line = "\r\n";
        if ("1".equals(flag))
        {
            buffer.append("<tr>").append(line);
            buffer.append("<td height='10' colspan='2'></td>");
            buffer.append("</tr>");
        }
        buffer.append("<tr>").append(line);
        buffer.append("<td background='" + this.url + "dot_line.gif' colspan='2'></td>").append(
            line);
        buffer.append("</tr>").append(line);

        buffer.append("<tr>").append(line);
        buffer.append("<td height='10' colspan='2'></td>");
        buffer.append("</tr>");

    }

    /**
     * @return Returns the object.
     */
    public Object getObject()
    {
        return object;
    }

    /**
     * @param object
     *            The object to set.
     */
    public void setObject(Object object)
    {
        this.object = object;
    }

    /**
     * @return Returns the flag.
     */
    public String getFlag()
    {
        return flag;
    }

    /**
     * @param flag
     *            The flag to set.
     */
    public void setFlag(String flag)
    {
        this.flag = flag;
    }

}
