package com.china.center.common.taglib;


import javax.servlet.jsp.JspException;


/**
 * ҳ�濪ʼ��ǩ
 * 
 * @author zhuzhu
 * @version 2007-3-14
 * @see PageCache
 * @since
 */

public class PageCache extends BodyTagCenterSupport
{
    /**
     * Ĭ�Ϲ�����
     */
    public PageCache()
    {}

    public int doStartTag()
        throws JspException
    {
        // ҳ����ʾ���ַ�
        StringBuffer buffer = new StringBuffer();

        writeCache(buffer);

        this.writeContext(buffer.toString());

        return EVAL_BODY_INCLUDE;
    }

    private void writeCache(StringBuffer buffer)
    {

        String line = "\r\n";

        // <input type=hidden name=cacheEle id=cacheEle value=''/>
        // <input type=hidden name=cacheFlag id=cacheFlag value="0"/>

        buffer.append("<input type=hidden name=cacheEle id=cacheEle value=''/>").append(line);
        buffer.append("<input type=hidden name=cacheFlag id=cacheFlag value=0/>").append(line);
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
