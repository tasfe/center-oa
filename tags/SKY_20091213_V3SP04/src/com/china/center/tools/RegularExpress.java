package com.china.center.tools;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegularExpress
{
    /**
     * Automatically generated method: RegularExpress
     */
    private RegularExpress()
    {

    }

    /**
     * Description: ����Ƿ�Ϊ��Ч�ַ� <br>
     * [�����б�˵��ÿ��������;]
     * 
     * @param name
     * @return boolean
     */
    public static boolean isValidString(String name)
    {
        if (name == null || "".equals(name))
        {
            return false;
        }

        Pattern p = Pattern.compile("[ ,./<>?'\";:~!`#$%\\^&��*\\(\\)\\-=\\+\\\\|\\{\\}\\[\\]]");
        Matcher m = p.matcher(name);

        return !m.find();
    }

    /**
     * Description: ���ʱ���ʽ�Ƿ�Ϊ������ʱ�����ʽ <br>
     * [�����б�˵��ÿ��������;]
     * 
     * @param date
     * @return boolean
     */
    public static boolean isLongDate(String date)
    {
        if (date == null || "".equals(date))
        {
            return false;
        }

        Pattern p = Pattern.compile("^[0-9]{4}-[01][0-9]-[0-3][0-9] [0-2][0-9]:[0-5][0-9]:[0-5][0-9]$");
        Matcher m = p.matcher(date);

        return m.find();
    }

    /**
     * Description: ���ʱ���ʽ�Ƿ�Ϊ�����ո�ʽ <br>
     * [�����б�˵��ÿ��������;]
     * 
     * @param date
     * @return boolean
     */
    public static boolean isShortDate(String date)
    {
        if (date == null || "".equals(date))
        {
            return false;
        }

        Pattern p = Pattern.compile("^[0-9]{4}-[01][0-9]-[0-3][0-9]$");
        Matcher m = p.matcher(date);

        return m.find();
    }

    /**
     * Description:���GUID�Ƿ�Ϊȫ�������� <br>
     * [�����б�˵��ÿ��������;]
     * 
     * @param id
     * @return boolean
     */
    public static boolean isGuid(String id)
    {
        if (id == null || "".equals(id))
        {
            return false;
        }

        Pattern p = Pattern.compile("^[0-9]+$");
        Matcher m = p.matcher(id);

        return m.find();
    }

    /**
     * Description: �Ƿ������� <br>
     * 
     * @param id
     * @return boolean
     */
    public static boolean isNumber(String id)
    {
        if (id == null || "".equals(id))
        {
            return false;
        }

        Pattern p = Pattern.compile("^[0-9]+$");
        Matcher m = p.matcher(id);

        return m.find();
    }
    
    public static boolean isDouble(String id)
    {
        if (id == null || "".equals(id))
        {
            return false;
        }

        Pattern p = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
        
        Matcher m = p.matcher(id);

        return m.find();
    }

    /**
     * Description: �Ƿ������ֺ���ĸ��� <br>
     * 
     * @param str
     * @return boolean
     * @exception/throws [Υ������] [Υ��˵��]
     * @see [�ࡢ��#��������#��Ա]
     */
    public static boolean isNumberOrLetter(String str)
    {
        if (str == null || "".equals(str))
        {
            return false;
        }
        Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher m = p.matcher(str);

        return m.find();
    }

    /**
     * Description: �Ƿ������ֺ��ַ���� <br>
     * 
     * @param str
     * @return boolean
     * @exception/throws [Υ������] [Υ��˵��]
     * @see [�ࡢ��#��������#��Ա]
     */
    public static boolean isNumOrLetter(String str)
    {
        if (str == null || "".equals(str))
        {
            return false;
        }
        Pattern p = Pattern.compile("^[A-Za-z.@0-9]+$");
        Matcher m = p.matcher(str);

        return m.find();
    }
}
