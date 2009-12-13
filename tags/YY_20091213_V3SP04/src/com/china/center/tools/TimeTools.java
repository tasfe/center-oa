/*
 * �ļ�����TimeTools.java
 * ��Ȩ��Copyright by www.centerchina.com
 * ������
 * �޸��ˣ�zhu
 * �޸�ʱ�䣺2006-7-7
 * ���ٵ��ţ�
 * �޸ĵ��ţ�
 * �޸����ݣ�
 */

package com.china.center.tools;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * ʱ��Ĺ�����
 * 
 * @author zhu
 * @version 2006-7-7
 * @see TimeTools
 * @since
 */
public abstract class TimeTools
{
    /**
     * ƽ��ÿ������
     */
    private static int[] daysInMonth = new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     * ���ָ���·ݵ�����
     * 
     * @param year
     *            ��
     * @param month
     *            �·�,��1��ʼ��12(���Խ��Ĭ��Ϊ1)
     * @return days
     */
    public static int getDaysOfMonth(int year, int month)
    {
        if (month < 1 || month > 12)
        {
            month = 1;
        }

        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
        {
            if (month == 2)
            {
                return 29;
            }
        }

        return daysInMonth[month - 1];
    }

    /**
     * Description: ͨ��"yyyy-MM-dd HH:mm:ss"�ַ������ʱ��<br>
     * 
     * @param format
     * @return Date (�쳣����null)
     */
    public static Date getDate(String format)
    {
        Date date = null;
        try
        {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = df.parse(format);
        }
        catch (Exception e)
        {
            date = null;
        }
        return date;
    }

    /**
     * ��ʽ��
     * 
     * @param days
     * @param format
     * @return
     */
    public static String getSpecialDateString(int days, String format)
    {
        return getDateString(days, format);
    }

    /**
     * ���ָ����ʱ��
     * 
     * @param days
     *            ��ǰʱ���ǰ�������
     * @return ʱ���ַ���
     */
    public static String getDateString(int days, String format)
    {
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DAY_OF_YEAR, days);

        return getStringByFormat(new Date(cal.getTimeInMillis()), format);
    }

    /**
     * ���ָ����ʱ��
     * 
     * @param days
     *            ��ǰʱ���ǰ�������
     * @return ʱ���ַ���
     */
    public static String getDateShortString(int days)
    {
        return getDateString(days, "yyyy-MM-dd");
    }

    /**
     * ���ָ����ʱ��
     * 
     * @param days
     *            ��ǰʱ���ǰ�������
     * @return ʱ���ַ���
     */
    public static String getDateFullString(int days)
    {
        return getDateString(days, "yyyy-MM-dd HH:mm:ss");
    }

    public static int cdate(String compareA, String compareB)
    {
        Date d1 = getDateByFormat(compareA, "yyyy-MM-dd");
        Date d2 = getDateByFormat(compareB, "yyyy-MM-dd");

        return (int) ( (d1.getTime() - d2.getTime()) / (24 * 3600 * 1000));
    }

    /**
     * Description: ͨ��"yyyy-MM-dd HH:mm:ss"�ַ������ʱ��<br>
     * 
     * @param format
     * @return Date (�쳣����null)
     */
    public static java.sql.Date getSqlDate(String dateString)
    {
        Date date = null;
        try
        {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            date = df.parse(dateString);
        }
        catch (Exception e)
        {
            return null;
        }

        return new java.sql.Date(date.getTime());
    }

    /**
     * Description: ͨ��"yyyy-MM-dd HH:mm:ss"�ַ������ʱ��<br>
     * 
     * @param format
     * @return Date (�쳣����null)
     */
    public static String getStringBySqlDate(java.sql.Date date)
    {
        return getStringByFormat(new Date(date.getTime()), "yyyy-MM-dd");
    }

    /**
     * Description:ͨ���ƶ��ĸ�ʽ���������date <br>
     * 
     * @param dateString
     *            ���ڵ��ַ�����
     * @param format
     *            ��ʽ�����ַ��� eg��"yyyy-MM-dd HH:mm:ss"
     * @return Date (�쳣����null)
     */
    public static Date getDateByFormat(String dateString, String format)
    {
        Date date = null;
        try
        {
            SimpleDateFormat df = new SimpleDateFormat(format);
            date = df.parse(dateString);
        }
        catch (Exception e)
        {
            date = null;
        }

        return date;
    }

    /**
     * Description:ͨ��ʱ���ø�ʽ�����ַ��� <br>
     * 
     * @param date
     *            ʱ��
     * @return String ��ʽ������ַ���(�쳣���ؿ�)
     */
    public static String getString(Date date)
    {
        if (date == null)
        {
            return "";
        }

        String dateString = "";
        try
        {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateString = df.format(date);
        }
        catch (Exception e)
        {
            dateString = "";
        }
        return dateString;
    }

    /**
     * Description:ͨ��ʱ���ø�ʽ�����ַ��� <br>
     * 
     * @param addTime
     *            ������ʱ��
     * @return String ��ʽ������ַ���(�쳣���ؿ�)
     */
    public static String getDateTimeString(long addTime)
    {
        return getString(new Date(new Date().getTime() + addTime));
    }

    public static String now()
    {
        return getString(new Date());
    }

    public static String now(String format)
    {
        return getStringByFormat(new Date(), format);
    }

    public static String now_short()
    {
        return getStringByFormat(new Date(), "yyyy-MM-dd");
    }

    /**
     * Description: ͨ���ƶ��ĸ�ʽ����ʽ���ʱ����ַ���<br>
     * 
     * @param date
     *            ��Ҫ��ʽ����ʱ��
     * @param format
     *            ��ʽ������ eg:yyyy-MM-dd HH:mm:ss
     * @return String ��ʽ������ַ���(�쳣���ؿ�)
     */
    public static String getStringByFormat(Date date, String format)
    {
        String dateString = "";
        try
        {
            SimpleDateFormat df = new SimpleDateFormat(format);
            dateString = df.format(date);
        }
        catch (Exception e)
        {
            dateString = "";
        }
        return dateString;
    }

    public static String getMonthBegin()
    {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DAY_OF_MONTH, 1);

        return getStringByFormat(new Date(cal.getTime().getTime()), "yyyy-MM-dd");
    }

    public static String getLastestMonthBegin()
    {
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.MONTH, -1);

        cal.set(Calendar.DAY_OF_MONTH, 1);

        return getStringByFormat(new Date(cal.getTime().getTime()), "yyyy-MM-dd");
    }

    public static void main(String[] args)
    {
        System.out.println(getLastestMonthBegin());
    }

    /**
     * get current time(before or after days)
     * 
     * @param days
     * @return
     */
    public static String now(int days)
    {
        return getDateFullString(days);
    }

}
