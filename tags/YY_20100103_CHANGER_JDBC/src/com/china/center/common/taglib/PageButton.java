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
 * @see PageButton
 * @since
 */

public class PageButton extends BodyTagCenterSupport
{
    private String leftWidth = "87%";

    private String rightWidth = "13%";

    /**
     * Ĭ�Ϲ�����
     */
    public PageButton()
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
        String line = "\r\n";
        buffer.append("<tr><td width='" + leftWidth + "'>").append(line);

    }

    private void writeEnd(StringBuffer buffer)
    {
        String line = "\r\n";
        buffer.append("</td><td width='" + rightWidth + "'></td></tr>").append(line);

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
