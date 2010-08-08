package com.china.center.common.taglib;


import javax.servlet.jsp.JspException;


/**
 * 页面开始标签
 * 
 * @author ZHUZHU
 * @version 2007-3-14
 * @see PageButton
 * @since
 */

public class PageButton extends BodyTagCenterSupport
{
    private String leftWidth = "87%";

    private String rightWidth = "13%";

    /**
     * 默认构建器
     */
    public PageButton()
    {}

    public int doStartTag()
        throws JspException
    {
        // 页面显示的字符
        StringBuffer buffer = new StringBuffer();

        writeStart(buffer);

        this.writeContext(buffer.toString());
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag()
        throws JspException
    {
        // 页面显示的字符
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