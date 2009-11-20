package com.china.center.common.taglib;


import javax.servlet.jsp.JspException;

import com.url.ajax.json.JSONObject;


/**
 * ҳ��option
 * 
 * @author zhuzhu
 * @version 2007-3-14
 * @see PageDefined
 * @since
 */

public class PageDefined extends BodyTagCenterSupport
{
    /**
     * Ĭ�Ϲ�����
     */
    public PageDefined()
    {}

    public int doStartTag()
        throws JspException
    {
        // ҳ����ʾ���ַ�
        StringBuffer buffer = new StringBuffer();

        writeDiv(buffer);

        this.writeContext(buffer.toString());
        return EVAL_BODY_INCLUDE;
    }

    private void writeDiv(StringBuffer buffer)
    {
        JSONObject object = new JSONObject();

        object.createMapList(PageSelectOption.optionMap, false);
        
        buffer.append(object.toString());
    }

    public int doEndTag()
        throws JspException
    {
        // ҳ����ʾ���ַ�
        StringBuffer buffer = new StringBuffer();

        this.writeContext(buffer.toString());
        return EVAL_PAGE;
    }

}
