/*
 * File Name: Column.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-9-28
 * Grant: open source to everybody
 */
package com.china.center.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.china.center.annotation.enums.JoinType;

/**
 * definded the join in db
 *
 * @author zhuzhu
 * @version 2007-9-28
 * @see
 * @since
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Join
{
	/**
	 * ���ӷ�ʽ
	 *
	 * @return
	 */
	JoinType type() default JoinType.EQUAL;

	/**
	 * Ŀ��bean
	 *
	 * @return
	 */
	Class tagClass();

	/**
	 * Ŀ����ֶ�(�ඨ���ֶ�)
	 *
	 * @return
	 */
	String tagField() default "";

	/**
	 * �Զ������ӵı�ı���
	 *
	 * @return
	 */
	String alias() default "";

}
