/*
 * File Name: CommonTools.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-4-10
 * Grant: open source to everybody
 */
package com.china.center.tools;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;


/**
 * 公用函数
 * 
 * @author zhuzhu
 * @version 2007-4-10
 * @see
 * @since
 */
public abstract class CommonTools
{
    /**
     * 获得财务年度的开始日期
     * 
     * @return
     */
    public static String getFinanceBeginDate()
    {
        Calendar cal = Calendar.getInstance();

        int month = cal.get(Calendar.MONTH);

        int year = cal.get(Calendar.YEAR);

        // 上一年的
        if (month >= 0 && month <= 1)
        {
            return (year - 1) + "-03-01";
        }

        return year + "-03-01";
    }

    /**
     * 获得财务年度的结束日期
     * 
     * @return
     */
    public static String getFinanceEndDate()
    {
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);

        int month = cal.get(Calendar.MONTH);

        // 0-2月份
        if (month >= 0 && month <= 1)
        {
            int realYear = year;

            int daysOfMonth = TimeTools.getDaysOfMonth(realYear, 2);

            return realYear + "-02-" + daysOfMonth;
        }

        int realYear = year + 1;

        int daysOfMonth = TimeTools.getDaysOfMonth(realYear, 2);

        return realYear + "-02-" + daysOfMonth;
    }

    /**
     * 自动保存paramers到Attribute里面
     */
    public static void saveParamers(HttpServletRequest request)
    {
        Map map = request.getParameterMap();

        Set set = map.entrySet();

        Map.Entry element = null;
        String[] oo = null;

        for (Iterator iter = set.iterator(); iter.hasNext();)
        {
            element = (Map.Entry)iter.next();

            if (element.getValue() instanceof String[])
            {
                oo = (String[])element.getValue();

                if (oo.length == 1)
                {
                    request.setAttribute(element.getKey().toString(), oo[0]);
                }
                else
                {
                    request.setAttribute(element.getKey().toString(), oo);
                }
            }

        }
    }

    /**
     * 自动保存paramers到Attribute里面
     */
    public static void removeParamers(HttpServletRequest request)
    {
        Map map = request.getParameterMap();

        Set set = map.entrySet();

        Map.Entry element = null;
        String[] oo = null;

        for (Iterator iter = set.iterator(); iter.hasNext();)
        {
            element = (Map.Entry)iter.next();

            if (element.getValue() instanceof String[])
            {
                oo = (String[])element.getValue();

                if (oo.length == 1)
                {
                    oo[0] = null;
                }
                else
                {
                    oo = null;
                }
            }

        }
    }

    public static Map saveParamersToMap(HttpServletRequest request)
    {
        Map map = request.getParameterMap();

        Map<String, String> result = new HashMap();

        Set set = map.entrySet();

        Map.Entry element = null;

        String[] oo = null;

        for (Iterator iter = set.iterator(); iter.hasNext();)
        {
            element = (Map.Entry)iter.next();

            if (element.getValue() instanceof String[])
            {
                oo = (String[])element.getValue();

                if (oo.length == 1)
                {
                    if (oo[0] != null)
                    {
                        result.put(element.getKey().toString(), oo[0].toString());

                        request.setAttribute(element.getKey().toString(), oo[0]);
                    }
                    else
                    {
                        // System.out.println(element.getKey().toString());
                    }
                }
                else
                {
                    request.setAttribute(element.getKey().toString(), oo);
                }
            }

        }

        return result;
    }

    /**
     * 深度拷贝Serializable对象(函数不能保证正确copy一切Serializable对象)<br>
     * 但是对于数据对象则完全可以保证
     * 
     * @param oldValue
     * @return
     */
    public static Object deepCopy(Object oldValue)
    {
        Object newValue = null;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try
        {
            oos = new ObjectOutputStream(bout);
            oos.writeObject(oldValue);
            ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
            ois = new ObjectInputStream(bin);
            newValue = ois.readObject();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (oos != null)
                {
                    oos.close();
                }
                if (ois != null)
                {
                    ois.close();
                }
            }
            catch (Exception e)
            {

            }
        }
        return newValue;
    }

    public static int parseInt(String s)
    {
        if (RegularExpress.isGuid(s.trim()))
        {
            return Integer.parseInt(s);
        }

        return 0;
    }

    public static void closeDatatStream(Map<String, InputStream> datatStream)
    {
        for (Map.Entry<String, InputStream> entry : datatStream.entrySet())
        {
            InputStream in = entry.getValue();

            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {}
            }
        }
    }

    private static int twonInner(int num)
    {
        int tem = num;

        int count = 0;

        while (tem >= 2)
        {
            count++ ;

            tem = tem >> 1;
        }

        return count;
    }

    private static void twon(int num, List<Integer> nList)
    {
        if (num <= 0)
        {
            return;
        }

        int n = twonInner(num);

        nList.add(n);

        int dex = num - (int)Math.pow(2, n);

        twon(dex, nList);
    }

    private static List<Integer> twon(int num)
    {
        List<Integer> nList = new ArrayList<Integer>();

        twon(num, nList);

        return nList;
    }

    public static Object[] deepCopy(Object oldValue, int count)
    {
        Object org = deepCopy(oldValue);

        Object[] each = null;

        if (count == 1)
        {
            return new Object[] {org};
        }

        List<Integer> nList = twon(count);

        Object[] result = new Object[count];

        int max = nList.get(0);

        Object[] temp = null;

        int index = 0;

        for (int i = 0; i <= max; i++ )
        {
            Object[] tempo = new Object[(int)Math.pow(2, i)];

            if (i == 0)
            {
                tempo[0] = org;

                each = tempo;
            }
            else
            {
                temp = (Object[])deepCopy(each);

                System.arraycopy(each, 0, tempo, 0, each.length);

                System.arraycopy(temp, 0, tempo, each.length, temp.length);

                each = tempo;
            }

            if (nList.contains(i))
            {
                System.arraycopy(each, 0, result, index, each.length);

                index += each.length;
            }

            temp = null;

            tempo = null;
        }

        return result;
    }
}
