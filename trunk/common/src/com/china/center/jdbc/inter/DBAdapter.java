/**
 *
 */
package com.china.center.jdbc.inter;

import com.china.center.annosql.AutoCreateSql;

/**
 * ���ݿ������������
 * 
 * @author Administrator
 */
public interface DBAdapter
{
    /**
     * ��ѯ��class
     * 
     * @return
     */
    Query getQuery();
    
    AutoCreateSql getAutoCreateSql();
    
    /**
     * get OtherProcess
     * @return
     */
    OtherProcess getOtherProcess();
}
