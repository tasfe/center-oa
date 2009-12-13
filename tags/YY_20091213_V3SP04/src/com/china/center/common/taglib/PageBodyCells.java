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
 * ҳ�浥Ԫ��
 * 
 * @author zhuzhu
 * @version 2007-3-14
 * @see PageBodyCells
 * @since
 */
public class PageBodyCells extends BodyTagCenterSupport
{
    private int width = 15;

    private String align = "left";

    private String index = "";

    private String title = "";

    /**
     * ��ͨ��CELL�� 0:ȫ����ͨ<br>
     */
    private int celspan = 1;

    private String id = "";

    /**
     * Ĭ�Ϲ�����
     */
    public PageBodyCells()
    {}

    public int doStartTag()
        throws JspException
    {
        // ҳ����ʾ���ַ�
        StringBuffer buffer = new StringBuffer();

        // HttpServletRequest request =
        // (HttpServletRequest)pageContext.getRequest();

        // CELL������ֵ
        int allIndex = ((Integer)pageContext.getAttribute(TagLibConstant.CENTER_CELL_INDEX
                                                          + this.index)).intValue();

        // �����ÿ��CELL������
        int cells = ((Integer)pageContext.getAttribute(TagLibConstant.CENTER_CELLS_INIT
                                                       + this.index)).intValue();

        // TR������ֵ
        int trIndex = ((Integer)pageContext.getAttribute(TagLibConstant.CENTER_TRS_INDEX
                                                         + this.index)).intValue();

        // ��Ҫ������

        if (allIndex % cells == 0)
        {
            String clc = null;
            if (trIndex % 2 == 0)
            {
                clc = "content1";
            }
            else
            {
                clc = "content2";
            }

            String al = "";

            if (celspan == 0)
            {
                if ( !this.isNullOrNone(this.align))
                {
                    al = " align = '" + this.align + "' ";
                }
            }

            String idTR = id + "_TR";

            buffer.append("<tr id='" + idTR + "' class='" + clc + "' " + al + ">").append("\r\n");
        }

        writeStart(buffer);

        this.writeContext(buffer.toString());

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag()
        throws JspException
    {
        // ҳ����ʾ���ַ�
        StringBuffer buffer = new StringBuffer();

        // ����������colspan ��Ϊ����ֵ

        int cells = ((Integer)pageContext.getAttribute(TagLibConstant.CENTER_CELLS_INIT
                                                       + this.index)).intValue();

        int add = this.celspan == 0 ? cells : this.celspan;

        int allIndex = ((Integer)pageContext.getAttribute(TagLibConstant.CENTER_CELL_INDEX
                                                          + this.index)).intValue()
                       + add;

        int trIndex = ((Integer)pageContext.getAttribute(TagLibConstant.CENTER_TRS_INDEX
                                                         + this.index)).intValue();

        writeEnd(buffer);

        if (allIndex % cells == 0)
        {
            buffer.append("</tr>");

            pageContext.setAttribute(TagLibConstant.CENTER_TRS_INDEX + this.index, new Integer(
                trIndex + 1));
        }

        pageContext.setAttribute(TagLibConstant.CENTER_CELL_INDEX + this.index, new Integer(
            allIndex));

        this.writeContext(buffer.toString());

        return EVAL_PAGE;
    }

    private void writeStart(StringBuffer buffer)
    {
        String line = "\r\n";

        String temp = "";
        String temp1 = "";

        int col = 0;

        int cells = ((Integer)pageContext.getAttribute(TagLibConstant.CENTER_CELLS_INIT
                                                       + this.index)).intValue();
        if (celspan == 0)
        {
            col = cells * 2;
        }
        else
        {
            col = this.celspan * 2 - 1;
        }

        String colspanString = " colspan = '" + col + "' ";

        if ( !this.isNullOrNone(id))
        {
            temp = " id='" + id + "' ";
            temp1 = " id='" + id + "_SEC' ";
        }

        if (celspan != 0)
        {
            buffer.append(
                "<td width='" + width + "%' align='" + align + "'" + temp + ">" + title + "��</td>").append(
                line);

            buffer.append("<td " + temp1 + colspanString + ">").append(line);
        }
        else
        {
            // û�б���TD��
            buffer.append("<td " + temp + colspanString + ">").append(line);
        }

    }

    private void writeEnd(StringBuffer buffer)
    {
        String line = "\r\n";
        buffer.append("</td>").append(line);

    }

    public String getIndex()
    {
        return index;
    }

    public void setIndex(String index)
    {
        this.index = index;
    }

    public String getAlign()
    {
        return align;
    }

    public void setAlign(String align)
    {
        this.align = align;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public int getCelspan()
    {
        return celspan;
    }

    public void setCelspan(int celspan)
    {
        if (celspan > -1)
        {
            this.celspan = celspan;
        }
    }
}
