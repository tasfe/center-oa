/**
 * File Name: ResultTools.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-7-11<br>
 * Grant: open source to everybody
 */
package com.china.center.tools;


import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ColumnMapRowMapper;

import com.china.center.annosql.tools.BeanUtils;


/**
 * ResultTools
 * 
 * @author zhuzhu
 * @version 2009-7-11
 * @see ResultTools
 * @since 1.0
 */
public abstract class ResultTools extends BeanUtils
{
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
}
