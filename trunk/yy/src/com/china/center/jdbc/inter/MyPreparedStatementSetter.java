/*
 * �ļ�����MyPreparedStatementSetter.java
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
 * @see MyPreparedStatementSetter
 * @since
 */

public interface MyPreparedStatementSetter
{
    public void setValues(List list)
        throws SQLException;

}
