/**
 * File Name: BaseType.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-3-2<br>
 * Grant: open source to everybody
 */
package com.china.center.annosql.tools;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;


/**
 * <����>
 * 
 * @author zhuzhu
 * @version 2008-3-2
 * @see
 * @since
 */
public abstract class BaseType
{
    private static Set<Class> baseClasses = new HashSet<Class>();

    private static Set<Class> timeClasses = new HashSet<Class>();

    static
    {
        baseClasses.add(String.class);
        baseClasses.add(Integer.class);
        baseClasses.add(int.class);
        baseClasses.add(Long.class);
        baseClasses.add(long.class);
        baseClasses.add(Float.class);
        baseClasses.add(float.class);
        baseClasses.add(Double.class);
        baseClasses.add(double.class);
        baseClasses.add(Short.class);
        baseClasses.add(short.class);
        baseClasses.add(Boolean.class);
        baseClasses.add(boolean.class);
        baseClasses.add(Character.class);
        baseClasses.add(char.class);
        baseClasses.add(Byte.class);
        baseClasses.add(byte.class);
        baseClasses.add(BigDecimal.class);

        timeClasses.add(java.sql.Date.class);
        timeClasses.add(java.util.Date.class);
        timeClasses.add(java.util.Calendar.class);
        timeClasses.add(java.sql.Timestamp.class);
        timeClasses.add(java.sql.Time.class);

        baseClasses.addAll(timeClasses);
    }

    public static boolean isBaseType(Class claz)
    {
        return baseClasses.contains(claz);
    }

    public static boolean isTimeType(Class claz)
    {
        return timeClasses.contains(claz);
    }

    /**
     * ����src��ֵ���ָ�����͵�ʱ������
     * 
     * @param src
     * @param dirClass
     * @return
     */
    public static <T> T getTimeValue(Object src, Class<T> dirClass)
    {
        java.util.Date date = (java.util.Date)src;

        long timeLong = date.getTime();

        if (dirClass == java.sql.Date.class)
        {
            return (T)new java.sql.Date(timeLong);
        }

        if (dirClass == java.sql.Timestamp.class)
        {
            return (T)new java.sql.Timestamp(timeLong);
        }

        if (dirClass == java.sql.Time.class)
        {
            return (T)new java.sql.Time(timeLong);
        }

        if (dirClass == java.util.Calendar.class)
        {
            Calendar cal = Calendar.getInstance();

            cal.setTimeInMillis(timeLong);

            return (T)cal;
        }

        return (T)date;
    }

    /**
     * ��ü����Ե�value��ֵ(��Ҫ��long int��String)
     * 
     * @param src
     * @param dirClass
     * @return
     */
    public static Object getValue(Object src, Class dirClass)
    {
        if (src.getClass() == dirClass)
        {
            return src;
        }

        // long int��String
        if (dirClass == String.class
            && (src.getClass() == int.class || src.getClass() == Integer.class
                || src.getClass() == long.class || src.getClass() == Long.class
                || src.getClass() == float.class || src.getClass() == Float.class
                || src.getClass() == double.class || src.getClass() == Double.class))
        {
            return src.toString();
        }

        if (isTimeType(dirClass))
        {
            return getTimeValue(src, dirClass);
        }

        String temp = src.toString();

        // String��int
        if (dirClass == int.class || dirClass == Integer.class)
        {
            return Integer.parseInt(temp);
        }

        // String��long
        if (dirClass == long.class || dirClass == long.class)
        {
            return Long.parseLong(temp);
        }

        // String��float
        if (dirClass == float.class || dirClass == Float.class)
        {
            return Float.parseFloat(temp);
        }

        // String��ldouble
        if (dirClass == double.class || dirClass == Double.class)
        {
            return Double.parseDouble(temp);
        }

        // String��ldouble
        if (dirClass == char.class || dirClass == Character.class)
        {
            return temp.charAt(0);
        }

        // String��ldouble
        if (dirClass == boolean.class || dirClass == Boolean.class)
        {
            if ("true".equalsIgnoreCase(temp) || "1".equals(temp))
            {
                return Boolean.TRUE;
            }
            else
            {
                return Boolean.FALSE;
            }
        }

        return src;
    }

    public static Object getDefaultValue(Class dirClass)
    {
        if (dirClass == String.class)
        {
            return "";
        }

        if (dirClass == int.class || dirClass == Integer.class || dirClass == BigDecimal.class)
        {
            return new Integer(0);
        }

        if (dirClass == long.class || dirClass == Long.class)
        {
            return new Long(0L);
        }

        if (dirClass == float.class || dirClass == Float.class)
        {
            return new Float(0.0f);
        }

        if (dirClass == double.class || dirClass == Double.class)
        {
            return new Double(0.0d);
        }

        if (dirClass == byte.class || dirClass == Byte.class)
        {
            byte b = 0;
            return new Byte(b);
        }

        if (dirClass == char.class || dirClass == Character.class)
        {
            char c = '0';

            return new Character(c);
        }

        if (dirClass == boolean.class || dirClass == Boolean.class)
        {
            return Boolean.FALSE;
        }

        if (dirClass == java.sql.Date.class)
        {
            return new java.sql.Date(System.currentTimeMillis());
        }

        if (dirClass == java.sql.Timestamp.class)
        {
            return new java.sql.Timestamp(System.currentTimeMillis());
        }

        if (dirClass == java.sql.Time.class)
        {
            return new java.sql.Time(System.currentTimeMillis());
        }

        if (dirClass == java.util.Date.class)
        {
            return new java.util.Date(System.currentTimeMillis());
        }

        if (dirClass == java.util.Calendar.class)
        {
            Calendar cal = Calendar.getInstance();

            return cal;
        }

        return null;
    }
}
