/*
 * File Name: AutoCreateSql.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-9-28
 * Grant: open source to everybody
 */
package com.china.center.annosql;

/**
 * �Զ�����sql�Ľӿ�
 * 
 * @author zhuzhu
 * @version 2007-9-28
 * @see
 * @since
 */
public interface AutoCreateSql
{
    /**
     * sql��󻺳�
     */
    int MAX_BUFFER_SQL = 2000;

    String PREFIX = "anno_";

    String insertSql(Class claz)
        throws MYSqlException;

    String updateSql(Class claz)
        throws MYSqlException;

    String updateSql(Class claz, String columnName)
        throws MYSqlException;

    String updateFieldSql(Class claz, String fieldName)
        throws MYSqlException;

    String delSql(Class claz)
        throws MYSqlException;

    String delSql(Class claz, String columnName)
        throws MYSqlException;

    /**
     * ��ѯ��ϸ��
     * 
     * @param id
     * @param claz
     * @return
     * @throws MYSqlException
     */
    String querySql(Class<?> claz)
        throws MYSqlException;

    /**
     * ��ѯ��ϸ��
     * 
     * @param id
     * @param claz
     * @return
     * @throws MYSqlException
     */
    String querySql(String columnName, Class<?> claz)
        throws MYSqlException;

    /**
     * ��ѯ��ϸ��
     * 
     * @param id
     * @param claz
     * @return
     * @throws MYSqlException
     */
    String queryByCondtionSql(String condition, Class<?> claz)
        throws MYSqlException;

    /**
     * ����sql��ǰ׺
     * 
     * @param claz
     * @return
     */
    String prefix(Class<?> claz);
}
