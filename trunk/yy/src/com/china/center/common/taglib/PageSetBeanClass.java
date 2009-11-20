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
     * Ĭ�Ϲ�����
     */
    public PageSetBeanClass()
    {}

    public int doStartTag()
        throws JspException
    {
        pageContext.setAttribute(TagLibConstant.CENTER_BEAN_CLASS, this.value);

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

}
