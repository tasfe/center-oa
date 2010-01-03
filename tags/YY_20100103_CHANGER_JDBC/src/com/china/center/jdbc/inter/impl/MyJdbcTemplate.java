/*
 * File Name: MyJdbcTemplate.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-3-29
 * Grant: open source to everybody
 */
package com.china.center.jdbc.inter.impl;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.SqlParameterValue;

import com.china.center.annosql.AutoCreateSql;
import com.china.center.annosql.MYSqlException;
import com.china.center.annosql.tools.BeanTools;
import com.china.center.annosql.tools.BeanUtils;
import com.china.center.jdbc.inter.AdapterCache;
import com.china.center.jdbc.inter.Convert;
import com.china.center.jdbc.inter.DBAdapter;
import com.china.center.jdbc.inter.IbatisDaoSupport;
import com.china.center.jdbc.inter.JdbcOperation;
import com.china.center.jdbc.inter.MyBatchPreparedStatementSetter;
import com.china.center.jdbc.inter.MyDataSource;
import com.china.center.jdbc.inter.MyPreparedStatementSetter;
import com.china.center.jdbc.inter.OtherProcess;
import com.china.center.jdbc.inter.Query;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;


/**
 * ��װ��JdbcTemplate(����Ҫ��ʾ��ת��)
 * 
 * @author zhuzhu
 * @version 2007-3-3
 * @see MyJdbcTemplate
 * @since
 */

public class MyJdbcTemplate implements JdbcOperation
{
    private final Log _logger = LogFactory.getLog(getClass());

    private Convert convertEncode = null;

    private JdbcTemplate jdbcTemplate = null;

    private DBAdapter dbAdapter = null;

    private AdapterCache adapterCache = null;

    private IbatisDaoSupport ibatisDaoSupport = null;

    private boolean cache = false;

    private boolean show_sql = false;

    /**
     * �Զ�����sql
     */
    private AutoCreateSql autoCreateSql = null;

    public MyJdbcTemplate(MyDataSource dataSource)
    {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        this.convertEncode = dataSource.getConvertEncode();
    }

    public MyJdbcTemplate(DataSource dataSource, Convert convertEncode)
    {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        this.convertEncode = convertEncode;
    }

    /**
     * default constructor
     */
    public MyJdbcTemplate()
    {}

    public void query(String sql, MyPreparedStatementSetter pss, final RowCallbackHandler handler)
        throws DataAccessException
    {
        jdbcTemplate.query(getSql(sql), getPreparedStatementSetter(pss), new RowCallbackHandler()
        {
            public void processRow(ResultSet rs)
                throws SQLException
            {
                handler.processRow(new MyResultSet(rs, convertEncode));
            }
        });
    }

    public void query(String sql, PreparedStatementSetter pss, final RowCallbackHandler handler)
        throws DataAccessException
    {
        jdbcTemplate.query(getSql(sql), new MyPreparedStatementSetterImpl(pss, convertEncode),
            new RowCallbackHandler()
            {
                public void processRow(ResultSet rs)
                    throws SQLException
                {
                    handler.processRow(new MyResultSet(rs, convertEncode));
                }
            });
    }

    public void query(String sql, Object[] arg, final RowCallbackHandler handler)
        throws DataAccessException
    {
        jdbcTemplate.query(getSql(sql), getEncodeArray(arg), new RowCallbackHandler()
        {
            public void processRow(ResultSet rs)
                throws SQLException
            {
                handler.processRow(new MyResultSet(rs, convertEncode));
            }
        });
    }

    public void query(String sql, final RowCallbackHandler handler)
        throws DataAccessException
    {
        jdbcTemplate.query(getSql(sql), (PreparedStatementSetter)null, new RowCallbackHandler()
        {
            public void processRow(ResultSet rs)
                throws SQLException
            {
                handler.processRow(new MyResultSet(rs, convertEncode));
            }
        });
    }

    public Object queryForObject(String sql, Class requiredType)
        throws DataAccessException
    {
        if (requiredType == String.class)
        {
            return convertEncode.decode((String)jdbcTemplate.queryForObject(getSql(sql),
                requiredType));
        }
        else
        {
            return jdbcTemplate.queryForObject(getSql(sql), requiredType);
        }
    }

    public Object queryForObject(String sql, Object[] args, Class requiredType)
        throws DataAccessException
    {
        if (args == null || args.length == 0)
        {
            return queryForObject(sql, requiredType);
        }

        Object[] dir = new Object[args.length];

        for (int i = 0; i < args.length; i++ )
        {
            if (args[i] instanceof String)
            {
                dir[i] = convertEncode.encode((String)args[i]);
            }
            else
            {
                dir[i] = args[i];
            }
        }

        if (requiredType == String.class)
        {
            return convertEncode.decode((String)jdbcTemplate.queryForObject(getSql(sql), dir,
                requiredType));
        }
        else
        {
            return jdbcTemplate.queryForObject(getSql(sql), dir, requiredType);
        }
    }

    public Map queryForMap(String sql)
        throws DataAccessException
    {
        try
        {
            return convertEncode.decodeMap(jdbcTemplate.queryForMap(getSql(sql)));
        }
        catch (EmptyResultDataAccessException e)
        {
            return null;
        }
        catch (IncorrectResultSizeDataAccessException e)
        {
            return null;
        }

    }

    /**
     * ��ѯ����
     * 
     * @param sql
     * @param args
     * @return
     * @throws DataAccessException
     */
    public Map queryForMap(String sql, Object... args)
        throws DataAccessException
    {
        try
        {
            return convertEncode.decodeMap(jdbcTemplate.queryForMap(getSql(sql),
                getEncodeArray(args)));
        }
        catch (EmptyResultDataAccessException e)
        {
            return null;
        }
        catch (IncorrectResultSizeDataAccessException e)
        {
            return null;
        }
    }

    public List queryForList(String sql)
        throws DataAccessException
    {
        return convertEncode.decodeMapInList(jdbcTemplate.queryForList(getSql(sql)));
    }

    @SuppressWarnings("unchecked")
    public List<Map> queryForList(String sql, Object... args)
        throws DataAccessException
    {
        return convertEncode.decodeMapInList(jdbcTemplate.queryForList(getSql(sql),
            getEncodeArray(args)));
    }

    public int update(final String sql)
        throws DataAccessException
    {
        return this.updateInner(sql, null);
    }

    /**
     * Description:����sql�������ݿ����� (����Ҫ��ʾ��ת��)<br>
     * user like this:<br>
     * jdbc.update(sql, new MyPreparedStatementSetter(){<br>
     * &nbsp;&nbsp;&nbsp; public void setValues(List list) throws SQLException{<br>
     * &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;list.add(new Integer( -1));<br>
     * &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;list.add(bb.getBrandName());<br>
     * &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;list.add(bb.getBrandDesc());<br> }<br>
     * });<br>
     * 
     * @param sql
     *            ��ѯ�����
     * @param pss
     *            �Զ����PreparedStatementSetter
     * @return int
     * @throws DataAccessException
     */
    public int update(String sql, final MyPreparedStatementSetter myPss)
        throws DataAccessException
    {
        return this.updateInner(sql, getPreparedStatementSetter(myPss));
    }

    /**
     * Description:����sql�������ݿ����� (����Ҫ��ʾ��ת��)<br>
     * user like this:<br>
     * jdbc.update(sql, new MyPreparedStatementSetter(){<br>
     * &nbsp;&nbsp;&nbsp; public void setValues(List list) throws SQLException{<br>
     * &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;list.add(new Integer( -1));<br>
     * &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;list.add(bb.getBrandName());<br>
     * &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;list.add(bb.getBrandDesc());<br> }<br>
     * });<br>
     * 
     * @param sql
     *            ��ѯ�����
     * @param pss
     *            �Զ����PreparedStatementSetter
     * @return int
     * @throws DataAccessException
     */
    public int update(String sql, final MyPreparedStatementSetter myPss,
                      final List<Integer> columnType)
        throws DataAccessException
    {
        return this.updateInner(sql, getPreparedStatementSetter(myPss, columnType));
    }

    public int update(String sql, final PreparedStatementSetter myPss)
        throws DataAccessException
    {
        return this.updateInner(sql, new MyPreparedStatementSetterImpl(myPss, convertEncode));
    }

    /**
     * NOTE �������Ž�jdbcTemplate�ķ���,��֤update�����ڴ˽�������
     * 
     * @param sql
     * @param myPss
     * @return
     * @throws DataAccessException
     */
    private int updateInner(String sql, final PreparedStatementSetter myPss)
        throws DataAccessException
    {
        int i = -1;

        String fsql = getSql(sql);

        if (myPss == null)
        {
            i = this.jdbcTemplate.update(fsql);
        }
        else
        {
            i = jdbcTemplate.update(fsql, myPss);
        }

        cacheNote(fsql);

        return i;
    }

    public int update(String sql, final Object... args)
        throws DataAccessException
    {
        return update(sql, new MyPreparedStatementSetter()
        {
            public void setValues(List list)
                throws SQLException
            {
                for (int i = 0; i < args.length; i++ )
                {
                    list.add(args[i]);
                }
            }
        });
    }

    public long queryForLong(String sql)
        throws DataAccessException
    {
        return jdbcTemplate.queryForLong(getSql(sql));
    }

    public int queryForInt(String sql)
        throws DataAccessException
    {
        return jdbcTemplate.queryForInt(getSql(sql));
    }

    public int queryForInt(String sql, Object... arg)
        throws DataAccessException
    {
        return jdbcTemplate.queryForInt(getSql(sql), getEncodeArray(arg));
    }

    /**
     * NOTE ��������sql
     */
    public int[] batchUpdate(final String... sql)
        throws DataAccessException
    {
        if (sql == null || sql.length == 0)
        {
            throw new InvalidDataAccessApiUsageException("SQL must not be null");
        }

        for (int i = 0; i < sql.length; i++ )
        {
            sql[i] = convertEncode.encode(sql[i]);
        }

        int[] result = jdbcTemplate.batchUpdate(sql);

        for (String str : sql)
        {
            cacheNote(str);
        }

        return result;
    }

    public int[] batchUpdate(String sql, final MyBatchPreparedStatementSetter pss)
        throws DataAccessException
    {
        return this.batchUpdateInner(sql, getBatchPreparedStatementSetter(pss));
    }

    public int[] batchUpdate(String sql, final BatchPreparedStatementSetter pss)
        throws DataAccessException
    {
        return this.batchUpdateInner(sql, new MyBatchPreparedStatementSetterImpl(pss,
            convertEncode));
    }

    /**
     * NOTE �������µ��ڲ�ʵ��
     * 
     * @param sql
     * @param pss
     * @return
     * @throws DataAccessException
     */
    private int[] batchUpdateInner(String sql, BatchPreparedStatementSetter pss)
        throws DataAccessException
    {
        int[] result = null;

        String fsql = getSql(sql);

        result = jdbcTemplate.batchUpdate(fsql, pss);

        cacheNote(fsql);

        return result;
    }

    private String getSql(String sql)
    {
        if (sql == null)
        {
            throw new InvalidDataAccessApiUsageException("SQL must not be null");
        }

        sql = convertEncode.encode(sql);

        if (_logger.isDebugEnabled())
        {
            _logger.debug("prepare sql:" + sql);
        }

        if (this.show_sql)
        {
            System.out.println(sql);
        }

        return sql;
    }

    private PreparedStatementSetter getPreparedStatementSetter(
                                                               final MyPreparedStatementSetter myPss)
    {
        return new PreparedStatementSetter()
        {
            public void setValues(PreparedStatement ps)
                throws SQLException
            {
                List list = new ArrayList();
                myPss.setValues(list);
                setPreparedStatement(list, ps, null);
            }
        };
    }

    private PreparedStatementSetter getPreparedStatementSetter(
                                                               final MyPreparedStatementSetter myPss,
                                                               final List<Integer> columnType)
    {
        return new PreparedStatementSetter()
        {
            public void setValues(PreparedStatement ps)
                throws SQLException
            {
                List list = new ArrayList();
                myPss.setValues(list);
                setPreparedStatement(list, ps, columnType);
            }
        };
    }

    private BatchPreparedStatementSetter getBatchPreparedStatementSetter(
                                                                         final MyBatchPreparedStatementSetter myBps)
    {
        return new BatchPreparedStatementSetter()
        {
            public int getBatchSize()
            {
                return myBps.getBatchSize();
            }

            public void setValues(PreparedStatement ps, int i)
                throws SQLException
            {
                List list = new ArrayList();
                myBps.setValues(list, i);
                setPreparedStatement(list, ps, null);
            }

        };
    }

    private void setPreparedStatement(List list, PreparedStatement ps,
                                      final List<Integer> columnType)
        throws SQLException
    {
        int pos = 1;
        for (Iterator iter = list.iterator(); iter.hasNext();)
        {
            Object element = iter.next();

            if (element == null)
            {
                if (columnType != null)
                {
                    if (columnType.size() >= pos)
                    {
                        ps.setNull(pos++ , columnType.get(pos - 2));
                    }
                    else
                    {
                        ps.setObject(pos++ , null);
                    }
                }
                else
                {
                    ps.setString(pos++ , null);
                }

                continue;
            }

            if (element instanceof String)
            {
                ps.setString(pos++ , convertEncode.encode((String)element));
                continue;
            }

            if (element instanceof Boolean)
            {
                ps.setBoolean(pos++ , (Boolean)element);
                continue;
            }

            if (element instanceof Integer)
            {
                ps.setInt(pos++ , ((Integer)element).intValue());
                continue;
            }

            if (element instanceof Long)
            {
                ps.setLong(pos++ , ((Long)element).longValue());
                continue;
            }

            if (element instanceof Float)
            {
                ps.setFloat(pos++ , ((Float)element).floatValue());
                continue;
            }

            if (element instanceof Double)
            {
                ps.setDouble(pos++ , ((Double)element).doubleValue());
                continue;
            }

            if (element instanceof Byte)
            {
                ps.setByte(pos++ , ((Byte)element).byteValue());
                continue;
            }

            if (element instanceof Short)
            {
                ps.setShort(pos++ , ((Short)element).shortValue());
                continue;
            }

            if (element instanceof Timestamp)
            {
                ps.setTimestamp(pos++ , (Timestamp)element);
                continue;
            }

            if (element instanceof java.sql.Date)
            {
                ps.setDate(pos++ , (java.sql.Date)element);
                continue;
            }

            if (element instanceof java.util.Date)
            {
                ps.setDate(pos++ , new java.sql.Date( ((java.util.Date)element).getTime()));
                continue;
            }

            if (element instanceof Calendar)
            {
                Calendar cal = (Calendar)element;
                ps.setTimestamp(pos++ , new java.sql.Timestamp(cal.getTime().getTime()));
                continue;
            }

            ps.setString(pos++ , convertEncode.encode(element.toString()));
        }
    }

    /**
     * ת��arg�����string������ת�����������͵�SqlParameterValue<br>
     * 
     * @param arg
     * @return
     */
    private Object[] getEncodeArray(Object[] arg)
    {
        if (arg == null || arg.length == 0)
        {
            return arg;
        }

        for (int i = 0; i < arg.length; i++ )
        {
            if (arg[i] instanceof String)
            {
                arg[i] = convertEncode.encode((String)arg[i]);
            }

            if (arg[i] instanceof Character)
            {
                Character ch = (Character)arg[i];

                SqlParameterValue sch = new SqlParameterValue(java.sql.Types.CHAR, ch);

                arg[i] = sch;
            }
        }

        return arg;
    }

    public Object query(String sql, PreparedStatementSetter pss, final ResultSetExtractor rse)
        throws DataAccessException
    {
        return jdbcTemplate.query(getSql(sql), new MyPreparedStatementSetterImpl(pss,
            convertEncode), new ResultSetExtractor()
        {
            public Object extractData(ResultSet rs)
                throws SQLException, DataAccessException
            {
                return rse.extractData(new MyResultSet(rs, convertEncode));
            }
        });
    }

    public void execute(final String sql)
        throws DataAccessException
    {
        // NOTE
        String fsql = getSql(sql);

        jdbcTemplate.execute(fsql);

        cacheNote(fsql);
    }

    /**
     * ��������
     * 
     * @param object
     */
    private void processObject(Object object)
    {
        Field field = BeanTools.getIdField(object.getClass());

        if (field == null)
        {
            return;
        }

        if (BeanTools.isIdAutoIncrement(field))
        {
            Map map = new HashMap();

            OtherProcess otherProcess = dbAdapter.getOtherProcess();

            otherProcess.setJdbc(this);

            map.put(field.getName(), otherProcess.getUniqueSequence());

            try
            {
                BeanUtils.populate(object, map);
            }
            catch (IllegalAccessException e)
            {
                _logger.warn(e, e);
            }
            catch (InvocationTargetException e)
            {
                _logger.warn(e, e);
            }
        }
    }

    /**
     * ����annotation�������
     * 
     * @param object
     * @return
     * @throws DataAccessException
     */
    public int save(final Object object)
        throws DataAccessException
    {
        String sql = null;
        try
        {
            sql = autoCreateSql.insertSql(object.getClass());
        }
        catch (MYSqlException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }

        final List<String> parameterList = new ArrayList<String>();

        final List<Integer> parameterTypeList = new ArrayList<Integer>();

        processObject(object);

        int result = this.update(getAutoSql(sql, parameterList), new MyPreparedStatementSetter()
        {
            @SuppressWarnings("unchecked")
            public void setValues(List list)
                throws SQLException
            {
                for (String str : parameterList)
                {
                    try
                    {
                        list.add(BeanUtils.getPropertyValue(object, str));

                        Field field = BeanTools.getFieldIgnoreCase(str, object.getClass());

                        parameterTypeList.add(BeanTools.getFieldType(field));
                    }
                    catch (IllegalAccessException e)
                    {
                        _logger.warn(e, e);
                        throw new RuntimeException(e);
                    }
                    catch (InvocationTargetException e)
                    {
                        _logger.warn(e, e);
                        throw new RuntimeException(e);
                    }
                    catch (NoSuchMethodException e)
                    {
                        _logger.warn(e, e);
                        throw new RuntimeException(e);
                    }
                }
            }
        }, parameterTypeList);

        return result;
    }

    public int delete(String sql, Class claz, Object... args)
        throws DataAccessException
    {
        return this.update(getDeleteHead(claz) + sql, args);
    }

    private String getDeleteHead(Class<?> claz)
    {
        return "delete from " + BeanTools.getTableName(claz) + " ";
    }

    /**
     * ����������ѯʵ�����
     * 
     * @param <T>
     * @param id
     * @param claz
     * @return
     * @throws DataAccessException
     */
    public <T> T find(Object id, Class<T> claz)
        throws DataAccessException
    {
        T result = null;

        if (cache)
        {
            result = adapterCache.find(claz, id);

            if (result != null)
            {
                return result;
            }
        }

        result = this.find(id, BeanTools.getIdFieldName(claz), claz);

        if (cache && result != null)
        {
            adapterCache.addSingle(claz, id, result);
        }

        return result;
    }

    /**
     * ���ݶ���������������Ψһ��ʵ��
     */
    public <T> T findByUnique(Class<T> claz, Object... keys)
        throws DataAccessException
    {
        String[] fieldName = BeanTools.getUniqueFields(claz);

        if (fieldName == null || fieldName.length == 0)
        {
            throw new RuntimeException(claz.getName() + " miss unique field");
        }

        List<T> result = queryForListByFieldsInner(fieldName, claz, keys);

        if (result.size() == 0)
        {
            return null;
        }

        if (result.size() > 1)
        {
            throw new RuntimeException("the key is duplicate!");
        }

        return result.get(0);
    }

    private <T> List<T> queryForListByFieldsInner(String[] fieldName, Class<T> claz,
                                                  Object... args)
        throws DataAccessException
    {
        String sql = getUniqueQuerySql(fieldName, claz);

        return this.queryForList(sql, claz, args);
    }

    /**
     * @param <T>
     * @param fieldName
     * @param claz
     * @return
     */
    private <T> String getUniqueQuerySql(String[] fieldName, Class<T> claz)
    {
        String sql = "";

        String[] columnNames = new String[fieldName.length];

        int i = 0;
        for (String str : fieldName)
        {
            Field field = BeanTools.getFieldIgnoreCase(str, claz);

            if (field == null)
            {
                throw new RuntimeException(claz.getName() + " miss field " + str);
            }

            columnNames[i++ ] = BeanTools.getColumnName(field);
        }

        for (int k = 0; k < columnNames.length; k++ )
        {
            if (k != columnNames.length - 1)
            {
                sql += autoCreateSql.prefix(claz) + '.' + columnNames[k] + " = ? and ";
            }
            else
            {
                sql += autoCreateSql.prefix(claz) + '.' + columnNames[k] + " = ?";
            }
        }
        return "where " + sql;
    }

    public <T> T find(Object id, String fieldName, Class<T> claz)
        throws DataAccessException
    {
        List<Map> list = queryBySingleFieldInner(id, fieldName, claz);

        if (list.size() == 0)
        {
            return null;
        }

        if (list.size() > 1)
        {
            throw new RuntimeException("the key is duplicate!");
        }

        Map result = list.get(0);

        T obj = getUnique(claz, result);

        return obj;
    }

    /**
     * @param <T>
     * @param claz
     * @param result
     * @return
     */
    private <T> T getUnique(Class<T> claz, Map result)
    {
        T obj = null;

        try
        {
            obj = claz.newInstance();
        }
        catch (InstantiationException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }

        try
        {
            BeanUtils.populateSqlFieldIgnorCase(obj, result);
        }
        catch (IllegalAccessException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }
        catch (NoSuchMethodException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }

        return obj;
    }

    /**
     * @param <T>
     * @param id
     * @param fieldName
     * @param claz
     * @return
     */
    private <T> List<Map> queryBySingleFieldInner(Object id, String fieldName, Class<T> claz)
    {
        if (fieldName == null)
        {
            throw new RuntimeException("miss id in " + claz.getName());
        }

        Field field = BeanTools.getFieldIgnoreCase(fieldName, claz);

        if (field == null)
        {
            throw new RuntimeException(fieldName + " not exist in " + claz.getName());
        }

        String columnName = BeanTools.getColumnName(field);

        String sql = null;
        try
        {
            sql = autoCreateSql.querySql(columnName, claz);
        }
        catch (MYSqlException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }

        List<Map> list = this.queryForList(sql, id);

        return list;
    }

    public <T> List<T> queryForListByField(String fieldName, Class<T> claz, Object... args)
        throws DataAccessException
    {
        Field field = BeanTools.getFieldIgnoreCase(fieldName, claz);

        if (field == null)
        {
            throw new RuntimeException(claz.getName() + " miss field " + fieldName);
        }

        String columnName = BeanTools.getColumnName(field);

        return this.queryForList(
            "where " + autoCreateSql.prefix(claz) + '.' + columnName + " = ?", claz, args);
    }

    public <T> List<T> queryForList(String condtition, Class<T> claz, Object... args)
        throws DataAccessException
    {
        List<T> result = null;

        // ��������
        if (cache && BeanTools.isCache(claz))
        {
            result = adapterCache.query(claz, condtition, args);

            if ( !ListTools.isEmptyOrNull(result))
            {
                return result;
            }
        }

        String sql = null;
        try
        {
            sql = autoCreateSql.queryByCondtionSql(condtition, claz);
        }
        catch (MYSqlException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }

        List<Map> list = this.queryForList(sql, args);

        try
        {
            result = BeanTools.getBeans(list, claz);

            if (cache && BeanTools.isCache(claz))
            {
                adapterCache.addMore(claz, result, condtition, args);
            }

            return result;
        }
        catch (InstantiationException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e)
        {
            throw new RuntimeException(e);
        }
        catch (NoSuchMethodException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * ���������ѯ
     */
    public <T> List<T> queryForListByFK(Object fk, Class<T> claz, int index)
        throws DataAccessException
    {
        return queryForListByField(BeanTools.getFKFieldName(claz, index), claz, fk);
    }

    /**
     * @param obj
     * @return
     * @throws DataAccessException
     */
    public int update(final Object object)
        throws DataAccessException
    {
        return this.updateEntry(object, BeanTools.getIdFieldName(object.getClass()));
    }

    /**
     * ����ID�����ֶ�
     */
    public int updateField(final String fieldName, final Object fieldValue, final Object id,
                           Class claz)
        throws DataAccessException
    {
        Field field = BeanTools.getFieldIgnoreCase(fieldName, claz);

        if (field == null)
        {
            throw new RuntimeException(fieldName + " not exist in " + claz.getName());
        }

        if (BeanTools.getIdField(claz) == null)
        {
            throw new RuntimeException("ID not exist in " + claz.getName());
        }

        String sql = null;
        try
        {
            sql = autoCreateSql.updateFieldSql(claz, fieldName);
        }
        catch (MYSqlException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }

        final List<String> parameterList = new ArrayList<String>();

        int result = this.update(getAutoSql(sql, parameterList), new MyPreparedStatementSetter()
        {
            @SuppressWarnings("unchecked")
            public void setValues(List list)
                throws SQLException
            {
                list.add(fieldValue);
                list.add(id);
            }
        });

        return result;
    }

    /**
     * @param obj
     * @return
     * @throws DataAccessException
     */
    public int updateEntry(final Object object, String fieldName)
        throws DataAccessException
    {
        Field field = BeanTools.getFieldIgnoreCase(fieldName, object.getClass());

        if (field == null)
        {
            throw new RuntimeException(fieldName + " not exist in " + object.getClass().getName());
        }

        String columnName = BeanTools.getColumnName(field);
        String sql = null;
        try
        {
            sql = autoCreateSql.updateSql(object.getClass(), columnName);
        }
        catch (MYSqlException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }

        final List<String> parameterList = new ArrayList<String>();

        int result = this.update(getAutoSql(sql, parameterList), new MyPreparedStatementSetter()
        {
            @SuppressWarnings("unchecked")
            public void setValues(List list)
                throws SQLException
            {
                for (String str : parameterList)
                {
                    try
                    {
                        list.add(BeanUtils.getPropertyValue(object, str));
                    }
                    catch (IllegalAccessException e)
                    {
                        _logger.warn(e, e);
                        throw new RuntimeException(e);
                    }
                    catch (InvocationTargetException e)
                    {
                        _logger.warn(e, e);
                        throw new RuntimeException(e);
                    }
                    catch (NoSuchMethodException e)
                    {
                        _logger.warn(e, e);
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        return result;
    }

    public int delete(Object keyValue, Class claz)
        throws DataAccessException
    {
        String sql = null;
        try
        {
            sql = autoCreateSql.delSql(claz);
        }
        catch (MYSqlException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }

        int result = this.update(sql, keyValue);

        return result;
    }

    public int delete(Object keyValue, String fieldName, Class claz)
        throws DataAccessException
    {
        Field field = null;

        if (StringTools.isNullOrNone(fieldName))
        {
            field = BeanTools.getIdField(claz);
        }

        field = BeanTools.getFieldIgnoreCase(fieldName, claz);

        if (field == null)
        {
            throw new RuntimeException(fieldName + " not exist in " + claz.getName());
        }

        String columnName = BeanTools.getColumnName(field);

        String sql = null;
        try
        {
            sql = autoCreateSql.delSql(claz, columnName);
        }
        catch (MYSqlException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }

        int result = this.update(sql, keyValue);

        return result;
    }

    public <T> Query queryObjects(String condtition, Class<T> claz, Object... args)
        throws DataAccessException
    {
        String sql = null;
        try
        {
            sql = autoCreateSql.queryByCondtionSql(condtition, claz);
        }
        catch (MYSqlException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }

        return this.queryObjectsBySql(sql, claz, args);
    }

    /**
     * ��claz��query��ѯ��Ҫ��Ϊ��access�ķ�ҳ
     * 
     * @param <T>
     * @param sql
     * @param claz
     * @param args
     * @return
     * @throws DataAccessException
     */
    private <T> Query queryObjectsBySql(String sql, Class<T> claz, Object... args)
        throws DataAccessException
    {
        Query query = dbAdapter.getQuery();

        query.setSqlString(sql);

        query.setParamters(args);

        query.setJdbcOperation(this);

        query.setPrefix(autoCreateSql.prefix(claz));

        query.setIdColumn(BeanTools.getIdColumn(claz));

        query.setCache(this.cache);

        query.setAdapterCache(this.adapterCache);

        return query;
    }

    /**
     * ����֪ͨ
     * 
     * @param sql
     */
    private void cacheNote(String sql)
    {
        if (this.cache)
        {
            adapterCache.cacheNote(sql);
        }
    }

    public <T> Query queryObjectsBySql(String sql, Object... args)
        throws DataAccessException
    {
        Query query = dbAdapter.getQuery();

        query.setSqlString(sql);

        query.setParamters(args);

        query.setJdbcOperation(this);

        query.setCache(this.cache);

        query.setAdapterCache(this.adapterCache);

        return query;
    }

    /**
     * ������յ�sql
     * 
     * @param sql
     * @param parameterList
     * @return
     */
    private static String getAutoSql(String sql, List<String> parameterList)
    {
        // ����sql�е�#column#
        int index = sql.indexOf("#");

        int secIndex = -1;

        while (index != -1)
        {
            secIndex = sql.indexOf("#", index + 1);

            if (secIndex != -1)
            {
                parameterList.add(sql.substring(index + 1, secIndex));

                sql = sql.substring(0, index) + '?' + sql.substring(secIndex + 1);
            }

            index = sql.indexOf("#");
        }

        return sql;
    }

    public Convert getConvertEncode()
    {
        return convertEncode;
    }

    public void setConvertEncode(Convert convertEncode)
    {
        this.convertEncode = convertEncode;
    }

    public JdbcTemplate getJdbcTemplate()
    {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DataSource getDataSource()
    {
        return jdbcTemplate.getDataSource();
    }

    public <T> List<T> queryForListBySql(String sql, Class<T> claz, Object... args)
        throws DataAccessException
    {
        List<Map> list = this.queryForList(sql, args);

        try
        {
            return BeanTools.getBeans(list, claz);
        }
        catch (InstantiationException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e)
        {
            throw new RuntimeException(e);
        }
        catch (NoSuchMethodException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * ִ��DDL����
     * 
     * @param ddlSql
     */
    public void executeDDL(String ddlSql)
    {
        PreparedStatement ps = null;

        DataSource dataSource = jdbcTemplate.getDataSource();

        Connection connect = null;

        try
        {
            connect = dataSource.getConnection();

            ps = connect.prepareStatement(ddlSql);

            ps.execute();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (ps != null)
                {
                    ps.close();
                }
            }
            catch (SQLException e)
            {
                _logger.error(e, e);
            }

            try
            {
                if (connect != null)
                {
                    connect.close();
                }
            }

            catch (SQLException e)
            {
                _logger.error(e, e);
            }
        }
    }

    /**
     * @return the show_sql
     */
    public boolean isShow_sql()
    {
        return show_sql;
    }

    /**
     * @param show_sql
     *            the show_sql to set
     */
    public void setShow_sql(boolean show_sql)
    {
        this.show_sql = show_sql;
    }

    /**
     * ͨ����ҳ�����ѯ�б�
     */
    public <T> List<T> queryObjectsByPageSeparate(String condtition, PageSeparate page,
                                                  Class<T> claz, Object... args)
        throws DataAccessException
    {
        int max = page.getPageSize();
        if (page.getSectionFoot() + page.getPageSize() > page.getRowCount())
        {
            max = page.getRowCount() - page.getSectionFoot();
        }

        return this.queryObjects(condtition, claz, args).setFirstResult(page.getSectionFoot()).setMaxResults(
            max).list(claz);
    }

    public <T> Query queryObjectsByUnique(Class<T> claz, Object... args)
        throws DataAccessException
    {
        String[] fieldName = BeanTools.getUniqueFields(claz);

        if (fieldName == null || fieldName.length == 0)
        {
            throw new RuntimeException(claz.getName() + " miss unique field");
        }

        String sql = getUniqueQuerySql(fieldName, claz);
        try
        {
            sql = autoCreateSql.queryByCondtionSql(sql, claz);
        }
        catch (MYSqlException e)
        {
            _logger.warn(e, e);
            throw new RuntimeException(e);
        }

        return this.queryObjectsBySql(sql, claz, args);
    }

    public <T> List<T> queryObjectsBySqlAndPageSeparate(String sql, PageSeparate page,
                                                        Class<T> claz, Object... args)
        throws DataAccessException
    {
        int max = page.getPageSize();
        if (page.getSectionFoot() + page.getPageSize() > page.getRowCount())
        {
            max = page.getRowCount() - page.getSectionFoot();
        }

        return this.queryObjectsBySql(sql, args).setFirstResult(page.getSectionFoot()).setMaxResults(
            max).list(claz);
    }

    /**
     * @return the dbAdapter
     */
    public DBAdapter getDbAdapter()
    {
        return dbAdapter;
    }

    /**
     * @param dbAdapter
     *            the dbAdapter to set
     */
    public void setDbAdapter(DBAdapter dbAdapter)
    {
        this.dbAdapter = dbAdapter;

        this.autoCreateSql = this.dbAdapter.getAutoCreateSql();
    }

    /**
     * @return the cache
     */
    public boolean isCache()
    {
        return cache;
    }

    /**
     * @param cache
     *            the cache to set
     */
    public void setCache(boolean cache)
    {
        this.cache = cache;
    }

    /**
     * @return the adapterCache
     */
    public AdapterCache getAdapterCache()
    {
        return adapterCache;
    }

    /**
     * @param adapterCache
     *            the adapterCache to set
     */
    public void setAdapterCache(AdapterCache adapterCache)
    {
        this.adapterCache = adapterCache;
    }

    public IbatisDaoSupport getIbatisDaoSupport()
    {
        // set the same dataSource in application
        this.ibatisDaoSupport.setDataSource(this.getDataSource());

        return this.ibatisDaoSupport;
    }

    /**
     * @param ibatisDaoSupport
     *            the ibatisDaoSupport to set
     */
    public void setIbatisDaoSupport(IbatisDaoSupport ibatisDaoSupport)
    {
        this.ibatisDaoSupport = ibatisDaoSupport;
    }
}