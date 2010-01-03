/*
 * �� �� ��:  BodyTagCenterSupport.java
 * ��    Ȩ:  centerchina Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * ��    ��:  <����>
 * �� �� ��:  admin
 * �޸�ʱ��:  2007-10-11
 * ���ٵ���:  <���ٵ���>
 * �޸ĵ���:  <�޸ĵ���>
 * �޸�����:  <�޸�����>
 */
package com.china.center.common.taglib;


import java.io.IOException;

import javax.servlet.jsp.tagext.BodyTagSupport;


/**
 * <һ�仰���ܼ���> <������ϸ����>
 * 
 * @author admin
 * @version [�汾��, 2007-10-11]
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾]
 */
public class BodyTagCenterSupport extends BodyTagSupport
{
    protected String line = "\r\n";

    public void writeContext(String content)
    {
        try
        {
            this.pageContext.getOut().print(content);
        }
        catch (IOException e)
        {
            // e.printStackTrace();
        }
    }

    public boolean isNullOrNone(String name)
    {
        if (name == null || "".equals(name.trim()))
        {
            return true;
        }

        return false;
    }
}
