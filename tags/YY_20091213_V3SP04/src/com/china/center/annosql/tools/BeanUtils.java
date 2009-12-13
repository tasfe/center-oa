/*
 * File Name: BeanUtils.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-9-29
 * Grant: open source to everybody
 */
package com.china.center.annosql.tools;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.ColumnMapRowMapper;

import com.china.center.annosql.InnerBean;


/**
 * <����>
 * 
 * @author zhuzhu
 * @version 2007-9-29
 * @see
 * @since
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils
{
    private static final Log _logger = LogFactory.getLog(BeanUtils.class);

    /**
     * getPropertyValue
     * 
     * @param bean
     * @param name
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static Object getPropertyValue(Object bean, String name)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        return BeanUtilsBean.getInstance().getPropertyUtils().getNestedProperty(bean, name);
    }

    /**
     * ���Կ���(�����ִ�Сд)
     * 
     * @param obj
     * @param map
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static void populateIgnorCase(Object obj, Map map)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        // ��map���������
        Map<String, Object> news = new HashMap<String, Object>(map.size());

        List<InnerBean> columns = BeanTools.getAllClassFieldsInner(obj.getClass());

        for (InnerBean field : columns)
        {
            Object temp = map.get(field.getColumnName().toUpperCase());

            if (obj != null)
            {
                if (temp != null && temp instanceof java.sql.Date)
                {
                    temp = new java.util.Date( ((java.sql.Date)temp).getTime());
                }

                if (temp != null && temp instanceof Timestamp)
                {
                    temp = new java.util.Date( ((Timestamp)temp).getTime());
                }

                news.put(field.getFieldName(), temp);
            }
        }

        try
        {
            org.apache.commons.beanutils.BeanUtils.populate(obj, news);
        }
        catch (IllegalArgumentException e)
        {
            _logger.error(e, e);
        }
    }

    /**
     * ���Կ���(�����ִ�Сд)
     * 
     * @param obj
     * @param map
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static void populateSqlFieldIgnorCase(Object obj, Map map)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        // ��map���������
        Map<String, Object> news = new HashMap<String, Object>(map.size());

        List<InnerBean> columns = BeanTools.getAllClassSqlFieldsInner(obj.getClass());

        for (InnerBean field : columns)
        {
            Object temp = map.get(field.getColumnName().toUpperCase());

            if (temp != null)
            {
                if (temp instanceof java.sql.Date)
                {
                    temp = new java.util.Date( ((java.sql.Date)temp).getTime());
                }

                if (temp instanceof Timestamp)
                {
                    temp = new java.util.Date( ((Timestamp)temp).getTime());
                }

                news.put(field.getFieldName(), temp);
            }
        }

        org.apache.commons.beanutils.BeanUtils.populate(obj, news);
    }

    /**
     * �ӽ����������Bean
     * 
     * @param bean
     * @param rs
     * @throws SQLException
     */
    public static void getBeanFromResultSet(Object bean, ResultSet rs)
        throws SQLException
    {
        rs.next();

        ColumnMapRowMapper mapper = new ColumnMapRowMapper();

        Map map = (Map)mapper.mapRow(rs, 0);

        try
        {
            populateIgnorCase(bean, map);
        }
        catch (IllegalAccessException e)
        {
            _logger.warn(e, e);
        }
        catch (InvocationTargetException e)
        {
            _logger.warn(e, e);
        }
        catch (NoSuchMethodException e)
        {
            _logger.warn(e, e);
        }

    }

    /**
     * �ӽ����������Beans
     * 
     * @param <T>
     * @param list
     *            ���յĽ��
     * @param rs
     *            �����
     * @param claz
     *            ����
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public static <T> void getBeansFromResultSet(final List<T> list, ResultSet rs, Class claz)
        throws SQLException
    {
        ColumnMapRowMapper mapper = new ColumnMapRowMapper();

        int rowNum = 0;

        while (rs.next())
        {
            try
            {
                T obj = (T)claz.newInstance();

                Map map = (Map)mapper.mapRow(rs, rowNum++ );

                populateIgnorCase(obj, map);

                list.add(obj);

            }
            catch (InstantiationException e)
            {
                _logger.warn(e, e);
            }
            catch (IllegalAccessException e)
            {
                _logger.warn(e, e);
            }
            catch (InvocationTargetException e)
            {
                _logger.warn(e, e);
            }
            catch (NoSuchMethodException e)
            {
                _logger.warn(e, e);
            }

        }
    }

    /**
     * ����field����
     * 
     * @param <T>
     * @param mapList
     * @param cla
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> List<T> populateListIgnoreByField(List<Map> mapList, Class<T> claz)
        throws IllegalArgumentException, IllegalAccessException, InstantiationException
    {
        List<InnerBean> columns = BeanTools.getAllClassFieldsInner(claz);

        List<T> result = new ArrayList<T>(mapList.size());

        for (Map<?, ?> map : mapList)
        {
            T obj = reflectByFieldAndSqlColumn(claz, columns, map);

            result.add(obj);
        }

        return result;
    }

    /**
     * ����field���з���,ͬʱ����ʹ��@Column
     * 
     * @param <T>
     * @param claz
     * @param columns
     * @param map
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private static <T> T reflectByFieldAndSqlColumn(Class<T> claz, List<InnerBean> columns,
                                                    Map<?, ?> map)
        throws InstantiationException, IllegalAccessException
    {
        Map<String, Object> news = new HashMap<String, Object>();

        for (Map.Entry<?, ?> entry : map.entrySet())
        {
            news.put(entry.getKey().toString().toUpperCase(), entry.getValue());
        }

        T obj = claz.newInstance();

        for (InnerBean innerBean : columns)
        {
            Object oo = news.get(innerBean.getColumnName().toUpperCase());

            if (oo == null)
            {
                continue;
            }

            Field field = innerBean.getField();

            field.setAccessible(true);

            field.set(obj, BaseType.getValue(oo, field.getType()));
        }

        return obj;
    }

}