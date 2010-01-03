package com.china.center.common.taglib;


import javax.servlet.jsp.JspException;


/**
 * ҳ�浥Ԫ��
 * 
 * @author zhuzhu
 * @version 2007-3-14
 * @see PageSetBeanClass
 * @since
 */

public class PageSetBeanClass extends BodyTagCenterSupport
{
    /**
     * ����
     */
    private String value = "";
    
    /**
     * 0:add 1:update
     */
    private int opr = 0;

    /**
     * Ĭ�Ϲ�����
     */
    public PageSetBeanClass()
    {}

    public int doStartTag()
        throws JspException
    {
        pageContext.setAttribute(TagLibConstant.CENTER_BEAN_CLASS, this.value);
        
        pageContext.setAttribute(TagLibConstant.CENTER_BEAN_OPR, this.opr);

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag()
        throws JspException
    {
        return EVAL_PAGE;
    }

    /**
     * @return the value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * @return the opr
     */
    public int getOpr()
    {
        return opr;
    }

    /**
     * @param opr the opr to set
     */
    public void setOpr(int opr)
    {
        this.opr = opr;
    }

}
