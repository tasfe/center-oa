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


import javax.servlet.jsp.JspException;


/**
 * ҳ�濪ʼ��ǩ
 * 
 * @author zhuzhu
 * @version 2007-3-14
 * @see PageBody
 * @since
 */

public class PageBody extends BodyTagCenterSupport
{
    private String width = "85%";

    /**
     * @return Returns the bodyWidth.
     */
    public String getWidth()
    {
        return width;
    }

    /**
     * @param bodyWidth
     *            The bodyWidth to set.
     */
    public void setWidth(String bodyWidth)
    {
        this.width = bodyWidth;
    }

    /**
     * Ĭ�Ϲ�����
     */
    public PageBody()
    {}

    public int doStartTag()
        throws JspException
    {
        // ҳ����ʾ���ַ�
        StringBuffer buffer = new StringBuffer();

        writePageBodyStart(buffer);

        this.writeContext(buffer.toString());
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag()
        throws JspException
    {
        // ҳ����ʾ���ַ�
        StringBuffer buffer = new StringBuffer();
        writePageBodyEnd(buffer);
        this.writeContext(buffer.toString());
        return EVAL_PAGE;
    }

    private void writePageBodyStart(StringBuffer buffer)
    {
        buffer.append("<table width='").append(width).append(
            "' border='0' cellpadding='0' cellspacing='0' align='center'> ");
    }

    private void writePageBodyEnd(StringBuffer buffer)
    {
        buffer.append("</table>");
    }
}
