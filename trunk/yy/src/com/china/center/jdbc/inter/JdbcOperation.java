package com.china.center.jdbc.inter;


import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.china.center.jdbc.util.PageSeparate;


/**
 * JdbcOperation<br>
 * ���ݿ�ײ�Ĳ����ӿ�
 */

public interface JdbcOperation
{
    void query(String sql, PreparedStatementSetter pss, final RowCallbackHandler handler)
        throws DataAccessException;

    void query(String sql, Object[] arg, final RowCallbackHandler handler)
        throws DataAccessException;

    void query(String sql, final RowCallbackHandler handler)
        throws DataAccessException;

    Object queryForObject(String sql, Class requiredType)
        throws DataAccessException;

    Object queryForObject(String sql, Object[] args, Class requiredType)
        throws DataAccessException;

    Map queryForMap(String sql)
        throws DataAccessException;

    Map queryForMap(String sql, Object... args)
        throws DataAccessException;

    List queryForList(String sql)
        throws DataAccessException;

    List queryForList(String sql, Object... args)
        throws DataAccessException;

    /**
     * ������ѯ
     * 
     * @param condtition
     *            ��ѯ������ WHERE ��ʼ
     * @param claz
     *            ʵ�����
     * @param args
     *            ����
     * @return List<T>
     * @throws DataAccessException
     */
    <T> List<T> queryForList(String condtition, Class<T> claz, Object... args)
        throws DataAccessException;

    /**
     * ������ѯ(���������������ѯ)
     * 
     * @param fk
     *            ���������ֵ
     * @param claz
     *            ʵ�����
     * @return List<T>
     * @throws DataAccessException
     */
    <T> List<T> queryForListByFK(Object fk, Class<T> claz, int index)
        throws DataAccessException;

    /**
     * ������ѯ
     * 
     * @param columnName
     *            ��ѯfeild
     * @param claz
     *            ʵ�����
     * @param args
     *            ����
     * @return List<T>
     * @throws DataAccessException
     */
    <T> List<T> queryForListByField(String fieldName, Class<T> claz, Object... args)
        throws DataAccessException;

    /**
     * �Զ���sql������ѯ
     * 
     * @param condtition
     *            ��ѯ������ WHERE ��ʼ
     * @param claz
     *            ʵ�����
     * @param args
     *            ����
     * @return List<T>
     * @throws DataAccessException
     */
    <T> List<T> queryForListBySql(String sql, Class<T> claz, Object... args)
        throws DataAccessException;

    /**
     * ����ȫsql��ѯʵ��
     * 
     * @param <T>
     * @param sql
     * @param claz
     * @param args
     * @return
     * @throws DataAccessException
     */
    <T> Query queryObjectsBySql(String sql, Object... args)
        throws DataAccessException;

    /**
     * ����������ѯʵ��
     * 
     * @param <T>
     * @param condtition
     * @param claz
     * @param args
     * @return
     * @throws DataAccessException
     */
    <T> Query queryObjects(String condtition, Class<T> claz, Object... args)
        throws DataAccessException;

    /**
     * ����Unique��ѯ
     * 
     * @param <T>
     * @param condtition
     * @param claz
     * @param args
     * @return
     * @throws DataAccessException
     */
    <T> Query queryObjectsByUnique(Class<T> claz, Object... args)
        throws DataAccessException;

    /**
     * ���ݷ�ҳ���ѯ
     * 
     * @param condtition
     *            ��ѯ����
     * @param page
     *            ��ҳ����
     * @param claz
     *            �����
     * @param args
     *            sql����
     * @return ��ѯ���
     * @throws DataAccessException
     */
    <T> List<T> queryObjectsByPageSeparate(String condtition, PageSeparate page, Class<T> claz,
                                           Object... args)
        throws DataAccessException;

    <T> List<T> queryObjectsBySqlAndPageSeparate(String sql, PageSeparate page, Class<T> claz,
                                                 Object... args)
        throws DataAccessException;

    int update(final String sql)
        throws DataAccessException;

    /**
     * ����һ������
     * 
     * @param claz
     * @return
     * @throws DataAccessException
     */
    int update(Object obj)
        throws DataAccessException;

    /**
     * ����һ������
     * 
     * @param claz
     * @param column
     *            ����
     * @return
     * @throws DataAccessException
     */
    int updateEntry(Object obj, String fieldName)
        throws DataAccessException;

    /**
     * ����һ��field��ͨ������
     * 
     * @param fieldName
     * @param fieldValue
     * @param id
     * @param claz
     * @return
     * @throws DataAccessException
     */
    int updateField(String fieldName, Object fieldValue, Object id, Class claz)
        throws DataAccessException;

    int update(String sql, final PreparedStatementSetter myPss)
        throws DataAccessException;

    /**
     * ����ʵ��
     * 
     * @param sql
     * @param args
     * @return
     * @throws DataAccessException
     */
    int update(String sql, final Object... args)
        throws DataAccessException;

    /**
     * ����annotation����������ݿ�
     * 
     * @param object
     * @return
     * @throws DataAccessException
     */
    int save(final Object object)
        throws DataAccessException;

    /**
     * ��������ɾ��һ������
     * 
     * @param claz
     * @return
     * @throws DataAccessException
     */
    int delete(Object keyValue, Class claz)
        throws DataAccessException;

    /**
     * ����ָ��������ɾ������
     * 
     * @param claz
     * @return
     * @throws DataAccessException
     */
    int delete(Object keyValue, String fieldName, Class claz)
        throws DataAccessException;

    /**
     * delete entry by sql
     * 
     * @param claz
     * @param sql
     * @param args
     * @return
     * @throws DataAccessException
     */
    int delete(String whereSql, Class claz, final Object... args)
        throws DataAccessException;

    /**
     * ����������ѯ����
     * 
     * @param id
     *            ����ֵ
     * @param claz
     *            Ŀ�����
     * @return
     * @throws DataAccessException
     */
    <T> T find(Object id, Class<T> claz)
        throws DataAccessException;

    /**
     * ����������ѯ����
     * 
     * @param claz
     *            Ŀ�����
     * @param keys
     *            ��������ֵ
     * @return
     * @throws DataAccessException
     */
    <T> T findByUnique(Class<T> claz, Object... keys)
        throws DataAccessException;

    /**
     * ����������ѯ����
     * 
     * @param id
     *            ����ֵ
     * @param fieldName
     *            �������������(����ֱ�ӵ�����)
     * @param claz
     *            Ŀ�����
     * @return
     * @throws DataAccessException
     */
    <T> T find(Object id, String fieldName, Class<T> claz)
        throws DataAccessException;

    long queryForLong(String sql)
        throws DataAccessException;

    int queryForInt(String sql)
        throws DataAccessException;

    int queryForInt(String sql, Object... arg)
        throws DataAccessException;

    // boolean queryForUnique(Object object) throws DataAccessException;
    // boolean queryForExist(Object object, Strin) throws DataAccessException;

    int[] batchUpdate(final String... sql)
        throws DataAccessException;

    Object query(String sql, PreparedStatementSetter pss, final ResultSetExtractor rse)
        throws DataAccessException;

    int[] batchUpdate(String sql, BatchPreparedStatementSetter pss);

    void query(String sql, MyPreparedStatementSetter pss, final RowCallbackHandler handler)
        throws DataAccessException;

    int update(String sql, final MyPreparedStatementSetter myPss)
        throws DataAccessException;

    int update(String sql, final MyPreparedStatementSetter myPss, final List<Integer> columnType)
        throws DataAccessException;

    int[] batchUpdate(String sql, final MyBatchPreparedStatementSetter pss)
        throws DataAccessException;

    void execute(String sql)
        throws DataAccessException;

    DataSource getDataSource();

    /**
     * ��û��� ������ �����ֶ�֪ͨ
     * 
     * @return
     */
    AdapterCache getAdapterCache();

    /**
     * ���Ibatis�Ľӿ�ʵ��
     * 
     * @return
     */
    IbatisDaoSupport getIbatisDaoSupport();
}
