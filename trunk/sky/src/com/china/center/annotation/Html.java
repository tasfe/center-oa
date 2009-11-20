/*
 * File Name: Html.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-10-7
 * Grant: open source to everybody
 */
package com.china.center.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.china.center.annotation.enums.Element;


/**
 * jspչʾ��annotation
 * 
 * @author zhuzhu
 * @version 2007-10-7
 * @see
 * @since
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Html
{
    /**
     * ҳ��չ������
     * 
     * @return
     */
    Element type() default Element.INPUT;

    /**
     * Element��name
     * 
     * @return
     */
    String name() default "";

    /**
     * չ�ֵı���
     * 
     * @return String
     */
    String title();

    /**
     * ҳ�����ʾ
     * 
     * @return
     */
    String tip() default "";

    /**
     * �����(�ڽ�����ռλ��ͨ��js��̬��ʾ)(��ʱ��ʹ��)
     * 
     * @return
     */
    boolean abstracts() default false;

    /**
     * չ�ֵ�˳��(��ʱ��ʹ��)
     * 
     * @return
     */
    int order() default 0;

    /**
     * �Զ�У��Ĺ���
     * 
     * @return
     */
    String[] oncheck() default {};

    /**
     * �Ƿ����
     * 
     * @return
     */
    boolean must() default false;

    /**
     * ֻ��
     * 
     * @return
     */
    boolean readonly() default false;

    /**
     * ��󳤶�(������input��Ч)
     * 
     * @return
     */
    int maxLength() default 0;
}
