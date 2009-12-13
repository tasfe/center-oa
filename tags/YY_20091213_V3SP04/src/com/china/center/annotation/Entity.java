/*
 * File Name: Table.java CopyRight: Copyright by www.center.china Description:
 * Creater: zhuAchen CreateTime: 2007-9-28 Grant: open source to everybody
 */
package com.china.center.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * the defined of bean
 * 
 * @author zhuzhu
 * @version 2007-9-28
 * @see
 * @since
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity
{
    /**
     * �Ƿ�̳�ʵ�� Ĭ��false
     * 
     * @return
     */
    boolean inherit() default false;

    /**
     * Description: ��ʾ��ʵ�庬��
     * 
     * @return String
     */
    String name() default "";

    /**
     * �Ƿ񻺴�
     * 
     * @return
     */
    boolean cache() default false;
}
