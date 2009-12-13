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


/**
 * ҳ�濪ʼ��ǩ
 * 
 * @author zhuzhu
 * @version 2007-3-14
 * @see ButtonTag
 * @since
 */

public class ButtonTag extends BodyTagCenterSupport
{
    private Object object = null;

    private String leftWidth = "87%";

    private String rightWidth = "13%";

    private String url = "";

    /**
     * Ĭ�Ϲ�����
     */
    public ButtonTag()
    {}

    public int doStartTag()
        throws JspException
    {
        // ҳ����ʾ���ַ�
        StringBuffer buffer = new StringBuffer();

        HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();

        this.url = request.getContextPath() + TagLibConstant.DEST_FOLDER_NAME;

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
        String line = "\r\n";
        buffer.append("</td></tr></table></td></tr>").append(line);
        buffer.append("<tr><td height='10' colspan='2'></td>").append(line);
        buffer.append("</tr><tr><td background='" + this.url + "dot_line.gif' colspan='2'></td>").append(
            line);
        buffer.append(
            "</tr><tr><td height='10' colspan='2'></td></tr><tr><td width='" + leftWidth + "'>").append(
            line);

    }

    private void writeEnd(StringBuffer buffer)
    {
        String line = "\r\n";
        buffer.append("</td><td width='" + rightWidth + "'></td></tr></table>").append(line);

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
     * @return Returns the leftWidth.
     */
    public String getLeftWidth()
    {
        return leftWidth;
    }

    /**
     * @param leftWidth
     *            The leftWidth to set.
     */
    public void setLeftWidth(String leftWidth)
    {
        this.leftWidth = leftWidth;
    }

    /**
     * @return Returns the rightWidth.
     */
    public String getRightWidth()
    {
        return rightWidth;
    }

    /**
     * @param rightWidth
     *            The rightWidth to set.
     */
    public void setRightWidth(String rightWidth)
    {
        this.rightWidth = rightWidth;
    }

}
