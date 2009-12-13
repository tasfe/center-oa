/*
 * File Name: ErrorConstant.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-10-7
 * Grant: open source to everybody
 */
package com.china.center.annosql;

import com.china.center.annotation.Note;

/**
 * ���������뼯��(16����) <br>
 * ��8λ xxxxxxxx <br>
 * ǰ0-2λ��������(00������ 01��sql 02���߼�) <br>
 * ǰ3-4λģ������(00������) <br>
 * ǰ5-8λ����Ĵ�������<br>
 *
 * @author zhuzhu
 * @version 2007-10-7
 * @see
 * @since
 */
public interface ErrorConstant
{
	@Note(value = "parameter is null")
	String PARAMETER_NULL = "00000001";

	@Note(value = "object is not a Entity")
	String OBJECT_NOT_ENTITY = "01000001";

	@Note(value = "missing id in object")
	String MISSING_ID = "01000002";

	@Note(value = "columns is empty")
	String COLUMN_EMPTY = "01000003";

	@Note(value = "miss join is null")
	String JOIN_NULL = "01000004";

	@Note(value = "object is not Inherit")
	String OBJECT_NOT_INHERIT = "02000001";
}
