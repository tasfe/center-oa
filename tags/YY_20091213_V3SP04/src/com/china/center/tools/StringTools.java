/*
 * �ļ�����StringTools.java
 * ��Ȩ��Copyright by www.centerchina.com
 * ������
 * �޸��ˣ�zhu
 * �޸�ʱ�䣺2006-7-12
 * ���ٵ��ţ�
 * �޸ĵ��ţ�
 * �޸����ݣ�
 */

package com.china.center.tools;


import java.util.Properties;


/**
 * String�Ĺ�����
 * 
 * @author zhu
 * @version 2006-7-12
 * @see StringTools
 * @since
 */

public class StringTools
{
    /**
     * Ĭ��ϵͳ�ַ���
     */
    private static String SYSTEM_DEFAULT_ENCODING = "GBK";

    /**
     * �Ƿ���ʾ��Ҫת��
     */
    private static boolean isNeedChange = true;

    static
    {
        Properties pp = System.getProperties();

        SYSTEM_DEFAULT_ENCODING = (String)pp.get("file.encoding");

        if ( !Constant.DEFAULT_CHAESET.equals(SYSTEM_DEFAULT_ENCODING))
        {
            isNeedChange = false;
        }
        else
        {
            isNeedChange = true;
        }
    }

    /**
     * Description: ��ʽ���ַ���(�û��齨��ʹ��)<br>
     * 
     * @param name
     * @return String
     */
    public static String formatString(String name)
    {
        String s = "0000000000" + name;
        return s.substring(s.length() - 10, s.length());
    }

    /**
     * ���ϵͳ���ַ���
     * 
     * @return SYSTEM_DEFAULT_ENCODING
     */
    public static String getLocalCharSet()
    {
        return SYSTEM_DEFAULT_ENCODING;
    }

    /**
     * ���ָ�����ַ�����
     * 
     * @param src
     * @param secSet
     * @param dirSet
     * @return
     */
    public static String getStringBySet(String src, String secSet, String dirSet)
    {
        if (isNullOrNone(src))
        {
            return "";
        }

        if (secSet.equals(dirSet))
        {
            return src;
        }

        try
        {
            // ת��
            src = new String(src.getBytes(secSet), dirSet);
        }
        catch (Exception e)
        {
            return "";
        }

        return src;
    }

    /**
     * Description:��ñ��Ĭ���ַ�(GBK) <br>
     * 
     * @param name
     * @return String
     */
    public static String getDefaultString(String name)
    {
        if (isNullOrNone(name))
        {
            return "";
        }

        if ( !isNeedChange)
        {
            return name;
        }

        return getStringBySet(name, SYSTEM_DEFAULT_ENCODING, Constant.DEFAULT_CHAESET);
    }

    /**
     * Description:��GBKת��ɱ��ص��ַ���<br>
     * 
     * @param name
     * @return String
     */
    public static String getLocalString(String name)
    {
        if (isNullOrNone(name))
        {
            return "";
        }

        if ( !isNeedChange)
        {
            return name;
        }

        return getStringBySet(name, Constant.DEFAULT_CHAESET, SYSTEM_DEFAULT_ENCODING);

    }

    /**
     * isNullOrNone
     * 
     * @param name
     * @return
     */
    public static boolean isNullOrNone(String name)
    {
        if (name == null || "".equals(name.trim()))
        {
            return true;
        }

        return false;
    }

    public static String trim(String str)
    {
        if (str == null)
        {
            return null;
        }

        return str.trim();
    }

    public static String truncate(String s, int begin)
    {
        if (isNullOrNone(s))
        {
            return "";
        }

        if (begin >= 0)
        {
            if (begin >= s.length())
            {
                return "";
            }

            return s.substring(begin);
        }

        begin = Math.abs(begin);

        if (begin >= s.length())
        {
            return s;
        }

        return s.substring(s.length() - begin);
    }

    public static String print(String s)
    {
        if (isNullOrNone(s))
        {
            return "";
        }

        return s;
    }

    /**
     * �Ƚ��ַ��Ĵ�С ����asc�Ƚ�
     * 
     * @param str1
     * @param str2
     * @return
     */
    public static int compare(String str1, String str2)
    {
        if (isNullOrNone(str1) && isNullOrNone(str2))
        {
            return 0;
        }

        if (isNullOrNone(str1) && !isNullOrNone(str2))
        {
            return 1;
        }

        if ( !isNullOrNone(str1) && isNullOrNone(str2))
        {
            return -1;
        }

        if (str1.equals(str2))
        {
            return 0;
        }

        int length = Math.min(str1.length(), str2.length());

        for (int i = 0; i < length; i++ )
        {
            if (str1.charAt(i) != str2.charAt(i))
            {
                return str1.charAt(i) - str2.charAt(i);
            }
        }

        return str1.length() - str2.length();
    }

    /**
     * formatString20
     * 
     * @param name
     * @return
     */
    public static String formatString20_yy(String name)
    {
        return "20" + TimeTools.now("yyyyMMdd") + formatString(name);
    }

}
