package com.china.center.jdbc.inter;


import javax.sql.DataSource;



/**
 * ��װ�����ݿ�Դ�ӿ�
 *
 * @author zhuzhu
 * @version 2007-3-5
 * @see MyDataSource
 * @since
 */

public interface MyDataSource extends DataSource
{
    Convert getConvertEncode();
}
