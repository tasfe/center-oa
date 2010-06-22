package com.china.center.tools;


import java.util.Formatter;
import java.util.Properties;


/**
 * String的工具类
 * 
 * @author zhu
 * @version 2006-7-12
 * @see StringTools
 * @since
 */

public class StringTools
{
    /**
     * 默认系统字符集
     */
    private static String SYSTEM_DEFAULT_ENCODING = "GBK";

    public static final String LINE_SPLIT = "\r\n";

    /**
     * 是否显示需要转码
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
     * Description: 格式化字符串(用户组建表使用)<br>
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
     * formatString20
     * 
     * @param name
     * @return
     */
    public static String formatString20(String name)
    {
        return "10" + TimeTools.now("yyyyMMdd") + formatString(name);
    }

    /**
     * 获得系统的字符集
     * 
     * @return SYSTEM_DEFAULT_ENCODING
     */
    public static String getLocalCharSet()
    {
        return SYSTEM_DEFAULT_ENCODING;
    }

    /**
     * 获得指定的字符编码
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
            // 转码
            src = new String(src.getBytes(secSet), dirSet);
        }
        catch (Exception e)
        {
            return "";
        }

        return src;
    }

    /**
     * Description:获得编程默认字符(GBK) <br>
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
     * Description:把GBK转码成本地的字符集<br>
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

    public static String getISOString(String name)
    {
        if (isNullOrNone(name))
        {
            return "";
        }

        return getStringBySet(name, Constant.DEFAULT_CHAESET, "ISO8859-1");
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

    public static boolean isNullOrNone(Object name)
    {
        if (name == null)
        {
            return true;
        }

        if (name instanceof String)
        {
            String temp = (String)name;

            if ("".equals(temp.trim()))
            {
                return true;
            }
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

    public static String truncate2(String s, int length)
    {
        if (isNullOrNone(s))
        {
            return "";
        }

        if (length >= s.length())
        {
            return s;
        }

        return s.substring(0, length);
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
     * 比较字符的大小 根据asc比较
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
     * format
     * 
     * @param format
     * @param args
     * @return
     */
    public static String format(String format, Object... args)
    {
        return new Formatter().format(format, args).toString();
    }

    public static String formatln(String format, Object... args)
    {
        return format(format, args).toString() + LINE_SPLIT;
    }

}