/*
 * �ļ�����ISPDataSource.java
 * ��Ȩ��Copyright 2002-2007 centerchina Tech. Co. Ltd. All Rights Reserved.
 * ������
 * �޸��ˣ�zhuzhu
 * �޸�ʱ�䣺2007-3-5
 * ���ٵ��ţ�
 * �޸ĵ��ţ�
 * �޸����ݣ�����
 */
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
