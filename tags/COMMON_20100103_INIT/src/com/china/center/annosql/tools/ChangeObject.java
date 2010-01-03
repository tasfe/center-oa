/*
 * File Name: ChangeObject.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-9-29
 * Grant: open source to everybody
 */
package com.china.center.annosql.tools;

/**
 * ��java�����Զ�ת����sql����
 * 
 * @author zhuzhu
 * @version 2007-9-29
 * @see
 * @since
 */
public abstract class ChangeObject
{
    public static Object getSqlObject(Object src)
    {
        if (src == null)
        {
            return "";
        }

        if (src instanceof String || src instanceof Integer || src instanceof Float
            || src instanceof Double || src instanceof Boolean)
        {
            return src;
        }

        if (src instanceof java.util.Date)
        {
            return new java.sql.Date( ((java.util.Date)src).getTime());
        }

        return src;
    }
}