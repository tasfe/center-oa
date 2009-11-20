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
 * ҳ��table
 * 
 * @author zhuzhu
 * @version 2007-3-14
 * @see PageBodyTable
 * @since
 */

public class PageBodyTable extends BodyTagCenterSupport
{
    private String width = "100%";

    private String tableClass = "table0";

    private int cells = 2;

    private String index = "";

    private String clasz = "";

    /**
     * Ĭ�Ϲ�����
     */
    public PageBodyTable()
    {}

    public int doStartTag()
        throws JspException
    {
        // ҳ����ʾ���ַ�
        StringBuffer buffer = new StringBuffer();

        writeStart(buffer);

        // HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();

        // ����ҳ������
        pageContext.setAttribute(TagLibConstant.CENTER_CELL_INDEX + this.index, new Integer(0));

        pageContext.setAttribute(TagLibConstant.CENTER_CELLS_INIT + this.index, new Integer(
            this.cells));

        pageContext.setAttribute(TagLibConstant.CENTER_TRS_INDEX + this.index, new Integer(0));

        // ����class
        if ( !this.isNullOrNone(clasz))
        {
            pageContext.setAttribute(TagLibConstant.CENTER_BEAN_CLASS, clasz);
        }

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
        buffer.append(
            "<table align='center' width='" + width + "' cellpadding='0' cellspacing='1' class='"
                + tableClass + "'>").append(line);

    }

    private void writeEnd(StringBuffer buffer)
    {
        String line = "\r\n";
        buffer.append("</table>").append(line);

    }

    public String getTableClass()
    {
        return tableClass;
    }

    public void setTableClass(String tableClass)
    {
        this.tableClass = tableClass;
    }

    public String getWidth()
    {
        return width;
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public int getCells()
    {
        return cells;
    }

    public void setCells(int cells)
    {
        this.cells = cells;
    }

    public String getIndex()
    {
        return index;
    }

    public void setIndex(String index)
    {
        this.index = index;
    }

    /**
     * @return ���� clasz
     */
    public String getClasz()
    {
        return clasz;
    }

    /**
     * @param ��clasz���и�ֵ
     */
    public void setClasz(String clasz)
    {
        this.clasz = clasz;
    }

}
