/**
 * File Name: OtherProcess.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-8-9<br>
 * Grant: open source to everybody
 */
package com.china.center.jdbc.inter;

/**
 * some db special process in auto create sql
 * 
 * @author zhuzhu
 * @version 2008-8-9
 * @see
 * @since
 */
public interface OtherProcess
{
    void setJdbc(JdbcOperation jdbcOperation);

    /**
     * ���Ψһ��sequence
     * 
     * @return
     */
    long getUniqueSequence();
}
