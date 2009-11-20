/*
 * �ļ�����MyBatchPreparedStatementSetter.java
 * ��Ȩ��Copyright by www.centerchina.com
 * ������
 * �޸��ˣ�zhuzhu
 * �޸�ʱ�䣺2007-3-3
 *
 */

package com.china.center.jdbc.inter;


import java.sql.SQLException;
import java.util.List;


/**
 * ��һ�仰���ܼ�����
 * 
 * @author zhuzhu
 * @version 2007-3-3
 * @see MyBatchPreparedStatementSetter
 * @since
 */

public interface MyBatchPreparedStatementSetter
{
    void setValues(List list, int i)
        throws SQLException;

    /**
     * Return the size of the batch.
     */
    int getBatchSize();
}
