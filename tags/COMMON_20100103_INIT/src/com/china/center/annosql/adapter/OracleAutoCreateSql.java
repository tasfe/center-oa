/*
 * File Name: OracleAutoCreateSql.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-9-28
 * Grant: open source to everybody
 */
package com.china.center.annosql.adapter;

import com.china.center.annosql.MYSqlException;

/**
 * oracle���Զ�����sql��ʵ��
 *
 * @author zhuzhu
 * @version 2007-9-28
 * @see
 * @since
 */
public class OracleAutoCreateSql extends BaseAutoCreateSql
{
	/**
	 * ����oracleû���������ͣ�����squence����
	 */
	public String insertSql(Class claz) throws MYSqlException
	{
		return insertSqlInner(claz, false);
	}
}
