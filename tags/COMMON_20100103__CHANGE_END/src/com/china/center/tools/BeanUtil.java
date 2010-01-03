/*
 * File Name: BeanUtil.java CopyRight: Copyright by www.center.china
 * Description: Creater: zhuAchen CreateTime: 2007-3-29 Grant: open source to
 * everybody
 */
package com.china.center.tools;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * BeanUtil
 * 
 * @author zhuzhu
 * @version 2007-3-29
 * @see
 * @since
 */
public class BeanUtil extends BeanUtils
{
    private static final Log _logger = LogFactory.getLog(BeanUtil.class);

    private BeanUtil()
    {}

    public static void getBean(Object obj, HttpServletRequest request)
    {
        try
        {
            BeanUtils.populate(obj, request.getParameterMap());
        }
        catch (IllegalAccessException e)
        {}
        catch (InvocationTargetException e)
        {}
        catch (Exception e)
        {}
    }

    public static void getBean(Object obj, Map map)
    {
        try
        {
            BeanUtils.populate(obj, map);
        }
        catch (IllegalAccessException e)
        {}
        catch (InvocationTargetException e)
        {}
        catch (Exception e)
        {}
    }

    public static String getProperty(Object obj, String name)
    {
        try
        {
            return BeanUtils.getProperty(obj, name);
        }
        catch (IllegalAccessException e)
        {
            _logger.error(e, e);
            return "";
        }
        catch (InvocationTargetException e)
        {
            _logger.error(e, e);
            return "";
        }
        catch (NoSuchMethodException e)
        {
            _logger.error(e, e);
            return "";
        }
    }

    /**
     * Description: bean�����Կ���
     * 
     * @param dest
     * @param orig
     */
    public static void copyProperties(Object dest, Object orig)
    {
        try
        {
            BeanUtils.copyProperties(dest, orig);
        }
        catch (IllegalAccessException e)
        {}
        catch (InvocationTargetException e)
        {}
    }

    public static void getBeanInner(Object obj, Map<?, ?> properties)
    {
        // Loop through the property name/value pairs to be set
        Iterator<?> names = properties.keySet().iterator();
        Method method = null;
        while (names.hasNext())
        {
            String name = (String)names.next();
            if (name == null)
            {
                continue;
            }

            Object value = properties.get(name);

            method = getMethod(obj, name, true);

            if (method == null)
            {
                continue;
            }

            try
            {
                Class[] clazs = method.getParameterTypes();

                if (clazs.length == 1)
                {
                    if (String.class.equals(clazs[0]))
                    {
                        method.invoke(obj, value.toString());
                    }
                    else if (Integer.class.equals(clazs[0]))
                    {
                        method.invoke(obj, new Integer(value.toString()));
                    }
                    else
                    {
                        method.invoke(obj, value);
                    }
                }
            }
            catch (IllegalArgumentException e)
            {}
            catch (IllegalAccessException e)
            {}
            catch (InvocationTargetException e)
            {}
            catch (Exception ee)
            {}

        }
    }

    public static List<?> getListBean(List<Map> list, Class claz)
    {
        List result = new ArrayList();
        for (Map map : list)
        {
            try
            {
                Object o = claz.newInstance();

                getBeanInner(o, map);

                result.add(o);
            }
            catch (InstantiationException e)
            {}
            catch (IllegalAccessException e)
            {}
        }

        return result;
    }

    private static Method getMethod(Object obj, String methodName, boolean ignore)
    {
        Method[] methods = obj.getClass().getMethods();

        // �ȹ���set����
        for (int i = 0; i < methods.length; i++ )
        {
            if (methods[i].getName().startsWith("set"))
            {
                if (ignore)
                {
                    if (methodName.toLowerCase().equals(
                        methods[i].getName().substring(3).toLowerCase()))
                    {
                        return methods[i];
                    }
                }
                else
                {
                    String tem = methods[i].getName().substring(3);

                    tem = String.valueOf(tem.charAt(0)).toUpperCase() + tem.substring(1);

                    if (methodName.equals(tem))
                    {
                        return methods[i];
                    }
                }
            }
        }

        return null;
    }

    /**
     * Description: ����toString<br>
     * 
     * @param obj
     * @return String
     */
    public static String toStrings(Object obj)
    {
        if (obj == null)
        {
            return "null";
        }

        if (obj instanceof String || obj instanceof Boolean || obj instanceof Integer)
        {
            return obj.toString();
        }

        if (obj instanceof List || obj instanceof Map)
        {
            return ToStringBuilder.reflectionToString(obj) + '(' + obj.toString() + ')';
        }

        return ToStringBuilder.reflectionToString(obj);
    }

    /**
     * Description: ����ӡlist����Ķ���,������־�߳�<br>
     * 
     * @param obj
     * @param ignal
     * @return String
     */
    public static String toStrings(Object obj, boolean ignal)
    {
        if (obj == null)
        {
            return "null";
        }

        if (obj instanceof String || obj instanceof Boolean || obj instanceof Integer)
        {
            return obj.toString();
        }

        if (obj instanceof List || obj instanceof Map)
        {
            if (ignal)
            {
                return ToStringBuilder.reflectionToString(obj);
            }

            return ToStringBuilder.reflectionToString(obj) + '(' + obj.toString() + ')';
        }

        return ToStringBuilder.reflectionToString(obj);
    }

}
