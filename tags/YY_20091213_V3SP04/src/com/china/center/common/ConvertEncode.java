/*
 * Created on 2005-5-27
 * �ļ�����ConvertEncode.java
 * ��Ȩ��Copyright 2003-2004 centerchina Tech. Co. Ltd. All Rights Reserved.
 * ������
 */
package com.china.center.common;


import java.io.UnsupportedEncodingException;


/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: centerchina
 * </p>
 * 
 * @author c36091
 * @version 1.0
 */
public class ConvertEncode
{
    private String databaseEncoding = "ISO8859-1";

    private String systemEncoding = "GBK";

    /**
     * 
     *
     */
    public ConvertEncode()
    {

    }

    /**
     * �ڴ����ݿ�ȡ���ַ�����ʱ����Ҫ�����ݿ�ı��뷽ʽ����decode�Ա���ȷ��ʾ
     * 
     * @param originStr
     * @return
     */
    public String decode(String originStr)
    {
        String s = originStr;
        if (originStr != null)
        {
            try
            {
                s = new String(originStr.getBytes(databaseEncoding), systemEncoding);
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            s = "";
        }

        return s;
    }

    /**
     * �ڲ������ݿ��ʱ����Ҫ���ַ��������ݿ���뷽ʽ����encode
     * 
     * @param originStr
     * @return
     */
    public String encode(String originStr)
    {
        String s = originStr;
        if (originStr != null)
        {
            try
            {
                s = new String(originStr.getBytes(systemEncoding), databaseEncoding);
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            s = "";
        }

        return s;
    }

    /**
     * @return Returns the destEncoding.
     */
    public String getSystemEncoding()
    {
        return systemEncoding;
    }

    /**
     * @param destEncoding
     *            The destEncoding to set.
     */
    public void setSystemEncoding(String destEncoding)
    {
        this.systemEncoding = destEncoding;
    }

    /**
     * @return Returns the originEncoding.
     */
    public String getDatabaseEncoding()
    {
        return databaseEncoding;
    }

    /**
     * @param originEncoding
     *            The originEncoding to set.
     */
    public void setDatabaseEncoding(String originEncoding)
    {
        this.databaseEncoding = originEncoding;
    }
}