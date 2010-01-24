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
public class TimeTools
{
    /**
     * ƽ��ÿ������
     */
    private static int[] daysInMonth = new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private static String[] week = new String[] {"", "������", "����һ", "���ڶ�", "������", "������", "������",
        "������"};

    public static String LONG_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String SHORT_FORMAT = "yyyy-MM-dd";

    /**
     * ÿ��ĺ���
     */
    private static long DAY_MS = 24 * 3600 * 1000;

    /**
     * ������ڼ�
     * 
     * @param dayOfWeek
     * @return
     */
    public static String getWeekDay(int dayOfWeek)
    {
        if (dayOfWeek <= 0 || dayOfWeek > 7)
        {
            return "";
        }

        return week[dayOfWeek];
    }

    /**
     * getYeay
     * 
     * @return
     */
    public static int getYeay()
    {
        Calendar cal = Calendar.getInstance();

        return cal.get(Calendar.YEAR);
    }

    /**
     * ������ڼ�
     * 
     * @param dateStr
     * @return
     */
    public static String getWeekDay(String dateStr)
    {
        Calendar cal = Calendar.getInstance();

        cal.setTime(TimeTools.getDateByFormat(dateStr, TimeTools.SHORT_FORMAT));

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        return getWeekDay(dayOfWeek);
    }

    /**
     * Description: ͨ��LONG_FORMAT�ַ������ʱ��<br>
     * 
     * @param format
     * @return Date (�쳣����null)
     */
    public static Date getDate(String format)
    {
        Date date = null;
        try
        {
            SimpleDateFormat df = new SimpleDateFormat(LONG_FORMAT);
            date = df.parse(format);
        }
        catch (Exception e)
        {
            date = null;
        }
        return date;
    }

    /**
     * ��õ�ǰ�ļ�����(��1��ʼ 1���Ǵ���)
     * 
     * @return
     */
    public int getCurrentSeason()
    {
        Calendar cal = Calendar.getInstance();

        int month = cal.get(Calendar.MONTH) + 1;

        if (month <= 3)
        {
            return 1;
        }

        if (month <= 6)
        {
            return 2;
        }

        if (month <= 9)
        {
            return 3;
        }

        return 4;
    }

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
     * ��yyyy-MM-dd HH:mm:ss��yyyy-MM-dd
     * 
     * @param time
     * @return
     */
    public static String changeTimeToDate(String time)
    {
        return time.substring(0, SHORT_FORMAT.length());
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
     * ���ָ������ʱ���ǰ������
     * 
     * @param org
     *            yyyy-MM-dd HH:mm:ss
     * @param days
     *            ����
     * @return
     */
    public static String getStringByOrgAndDays(String org, int days)
    {
        return getStringByFormat(new Date(getDateByFormat(org, LONG_FORMAT).getTime() + days
                                          * DAY_MS), LONG_FORMAT);
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
        return getDateString(days, SHORT_FORMAT);
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
     * ����ָ����stringʱ����N����ʱ��
     * 
     * @param date(LONG_FORMAT)
     * @param days
     * @return
     */
    public static String getSpecialDateStringByDays(String date, int days)
    {
        Date dateByFormat = getDateByFormat(date, TimeTools.LONG_FORMAT);

        return getStringByFormat(new Date(dateByFormat.getTime() + 24 * 3600 * 1000 * days),
            TimeTools.LONG_FORMAT);
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
        return getDateString(days, LONG_FORMAT);
    }

    /**
     * ���ָ����ʱ��
     * 
     * @param days
     * @param format
     * @return
     */
    public static String getDateFullString(int days, String format)
    {
        return getDateString(days, format);
    }

    public static int cdate(String compareA, String compareB)
    {
        Date d1 = getDateByFormat(compareA, SHORT_FORMAT);
        Date d2 = getDateByFormat(compareB, SHORT_FORMAT);

        return (int) ( (d1.getTime() - d2.getTime()) / DAY_MS);
    }

    /**
     * Description: ͨ��LONG_FORMAT�ַ������ʱ��<br>
     * 
     * @param format
     * @return Date (�쳣����null)
     */
    public static java.sql.Date getSqlDate(String dateString)
    {
        Date date = null;
        try
        {
            SimpleDateFormat df = new SimpleDateFormat(SHORT_FORMAT);
            date = df.parse(dateString);
        }
        catch (Exception e)
        {
            return null;
        }

        return new java.sql.Date(date.getTime());
    }

    /**
     * Description: ͨ��LONG_FORMAT�ַ������ʱ��<br>
     * 
     * @param format
     * @return Date (�쳣����null)
     */
    public static String getStringBySqlDate(java.sql.Date date)
    {
        return getStringByFormat(new Date(date.getTime()), SHORT_FORMAT);
    }

    /**
     * Description:ͨ���ƶ��ĸ�ʽ���������date <br>
     * 
     * @param dateString
     *            ���ڵ��ַ�����
     * @param format
     *            ��ʽ�����ַ��� eg��LONG_FORMAT
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
     * ��ø�ʽ��������(yyyy-MM-dd)
     * 
     * @param str
     * @return
     */
    public static String getFormatDateStr(String str)
    {
        Date tem = getDateByFormat(str, SHORT_FORMAT);

        if (tem == null)
        {
            return "";
        }

        return getStringByFormat(tem, SHORT_FORMAT);
    }

    /**
     * ��ø�ʽ��������+ʱ��(yyyy-MM-dd HH:mm:ss)
     * 
     * @param str
     * @return
     */
    public static String getFormatDateTimeStr(String str)
    {
        Date tem = getDateByFormat(str, LONG_FORMAT);

        if (tem == null)
        {
            return "";
        }

        return getStringByFormat(tem, LONG_FORMAT);
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
            SimpleDateFormat df = new SimpleDateFormat(LONG_FORMAT);
            dateString = df.format(date);
        }
        catch (Exception e)
        {
            dateString = "";
        }
        return dateString;
    }

    public static String getString(long time)
    {
        return getStringByFormat(new Date(time), LONG_FORMAT);
    }

    public static String getString(long time, String foramt)
    {
        return getStringByFormat(new Date(time), foramt);
    }

    public static String getShortString(long time)
    {
        return getStringByFormat(new Date(time), SHORT_FORMAT);
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

    public static String now(String format)
    {
        return getStringByFormat(new Date(), format);
    }

    public static String now_short()
    {
        return getStringByFormat(new Date(), SHORT_FORMAT);
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

        return getStringByFormat(new Date(cal.getTime().getTime()), SHORT_FORMAT);
    }

    public static String getLastestMonthBegin()
    {
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.MONTH, -1);

        cal.set(Calendar.DAY_OF_MONTH, 1);

        return getStringByFormat(new Date(cal.getTime().getTime()), SHORT_FORMAT);
    }

    public static void main(String[] args)
    {
        System.out.println(getFormatDateTimeStr("2009-1-12 2:45:1"));

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, 2009);

        cal.set(Calendar.DAY_OF_YEAR, 67);

        int week = cal.get(Calendar.DAY_OF_WEEK);

        System.out.println(week);

        System.out.println(getShortString(cal.getTimeInMillis()));
    }
}
