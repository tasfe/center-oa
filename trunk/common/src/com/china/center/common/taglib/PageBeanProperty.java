package com.china.center.common.taglib;


import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import com.china.center.annosql.tools.BeanTools;
import com.china.center.annotation.Html;


/**
 * ҳ�浥Ԫ��
 * 
 * @author zhuzhu
 * @version 2007-3-14
 * @see PageBeanProperty
 * @since
 */

public class PageBeanProperty extends BodyTagCenterSupport
{
    private int width = 15;

    private String align = "left";

    private String index = "";

    private String trId = "";

    /**
     * ����
     */
    private String field = "";

    private String endTag = "";

    /**
     * ԭʼֵ
     */
    private String value = "";

    private String innerString = "";

    private String outString = "";

    private Html html = null;

    private int cell = 1;

    /**
     * Ĭ�Ϲ�����
     */
    public PageBeanProperty()
    {}

    public int getLastWidth()
    {
        int tatol = 100;
        Integer ints = (Integer)pageContext.getAttribute(TagLibConstant.CENTER_CELLS_INIT
                                                         + this.index);

        int ll = tatol / ints.intValue() - this.width;

        return ll > 0 ? ll : 5;
    }

    public int doStartTag()
        throws JspException
    {
        // ҳ����ʾ���ַ�
        StringBuffer buffer = new StringBuffer();

        // ����ҳ������
        int allIndex = ((Integer)pageContext.getAttribute(TagLibConstant.CENTER_CELL_INDEX
                                                          + this.index)).intValue();

        int cells = ((Integer)pageContext.getAttribute(TagLibConstant.CENTER_CELLS_INIT
                                                       + this.index)).intValue();

        int trIndex = ((Integer)pageContext.getAttribute(TagLibConstant.CENTER_TRS_INDEX
                                                         + this.index)).intValue();

        // �����
        String claz = (String)pageContext.getAttribute(TagLibConstant.CENTER_BEAN_CLASS);

        // �����ӻ��Ǹ���
        int opr = (Integer)pageContext.getAttribute(TagLibConstant.CENTER_BEAN_OPR);

        HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();

        Object oval = null;

        if (opr == TagLibConstant.BEAN_UPDATE)
        {
            oval = request.getAttribute(TagLibConstant.CENTER_BEAN_UPDATBEAN);
        }

        Class cla = null;
        try
        {
            if (oval == null)
            {
                cla = Class.forName(claz);
            }
            else
            {
                cla = oval.getClass();
            }
        }
        catch (ClassNotFoundException e)
        {
            throw new JspException(e);
        }

        Field fields = BeanTools.getFieldIgnoreCase(this.field, cla);

        if (field == null)
        {
            throw new JspException(this.field + " not exist in " + claz);
        }

        // ���field��HTML
        html = BeanTools.getPropertyHtml(fields);

        if (html == null)
        {
            throw new JspException(field + " do not have Html");
        }

        // ��Ҫ������
        if (allIndex % cells == 0 || this.cell <= TagLibConstant.ALL_CELLS)
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
            String trIds = "";

            if ( !this.isNullOrNone(trId))
            {
                trIds = "id='" + trId + "'";
            }

            buffer.append("<tr class='" + clc + "' id='" + fields.getName() + "_TR'").append(trIds).append(
                ">\r\n");
        }

        int cellk = this.cell <= 0 ? (cells * 2 - 1) : (this.cell * 2) - 1;

        if ( (opr == TagLibConstant.BEAN_UPDATE || opr == TagLibConstant.BEAN_DISPLAY)
            && isNullOrNone(this.value))
        {
            oval = request.getAttribute(TagLibConstant.CENTER_BEAN_UPDATBEAN);

            if (oval != null)
            {
                fields.setAccessible(true);

                try
                {
                    Object oo = fields.get(oval);

                    if (oo != null)
                    {
                        this.value = oo.toString();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        String[] rs = WriteBeanProperty.writeProperty(cla, this.field, this.value, cellk,
            this.innerString, this.outString, this.width + "%", getLastWidth() + "%", this.align,
            request.getContextPath(), opr);

        this.value = "";

        this.endTag = rs[1];

        this.writeContext(buffer.toString() + rs[0]);

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag()
        throws JspException
    {
        // ҳ����ʾ���ַ�
        StringBuffer buffer = new StringBuffer();

        // ��ǰ��CELL��

        int allIndex = ((Integer)pageContext.getAttribute(TagLibConstant.CENTER_CELL_INDEX
                                                          + this.index)).intValue()
                       + this.cell;

        int cells = ((Integer)pageContext.getAttribute(TagLibConstant.CENTER_CELLS_INIT
                                                       + this.index)).intValue();

        int trIndex = ((Integer)pageContext.getAttribute(TagLibConstant.CENTER_TRS_INDEX
                                                         + this.index)).intValue();

        writeEnd(buffer);

        if (allIndex % cells == 0 || this.cell <= TagLibConstant.ALL_CELLS)
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

    private void writeEnd(StringBuffer buffer)
    {
        String line = "\r\n";
        buffer.append(this.endTag).append(line);

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

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    /**
     * @return ���� trId
     */
    public String getTrId()
    {
        return trId;
    }

    /**
     * @param ��trId���и�ֵ
     */
    public void setTrId(String trId)
    {
        this.trId = trId;
    }

    /**
     * @return ���� field
     */
    public String getField()
    {
        return field;
    }

    /**
     * @param ��field���и�ֵ
     */
    public void setField(String field)
    {
        this.field = field;
    }

    /**
     * @return ���� value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @param ��value���и�ֵ
     */
    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * @return ���� innerString
     */
    public String getInnerString()
    {
        return innerString;
    }

    /**
     * @param ��innerString���и�ֵ
     */
    public void setInnerString(String innerString)
    {
        this.innerString = innerString;
    }

    /**
     * @return the cell
     */
    public int getCell()
    {
        return cell;
    }

    /**
     * @param cell
     *            the cell to set
     */
    public void setCell(int cell)
    {
        this.cell = cell;
    }

    /**
     * @return the outString
     */
    public String getOutString()
    {
        return outString;
    }

    /**
     * @param outString
     *            the outString to set
     */
    public void setOutString(String outString)
    {
        this.outString = outString;
    }

}
