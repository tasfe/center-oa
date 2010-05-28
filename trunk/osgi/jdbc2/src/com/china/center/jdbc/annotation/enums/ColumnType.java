/*
 * File Name: ColumnType.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-9-29
 * Grant: open source to everybody
 */
package com.china.center.jdbc.annotation.enums;

/**
 * defined ColumnType
 * 
 * @author ZHUZHU
 * @version 2007-9-29
 * @see
 * @since
 */
public enum ColumnType
{
    /**
     * 默认，不确定
     */
    DEFAULT,

    /**
     * 字符
     */
    VARVHAR,

    /**
     * 整型
     */
    INTRGER,

    /**
     * 数值
     */
    NUMBER,

    /**
     * boolean
     */
    BOOLEAN,

    /**
     * 日期
     */
    DATE,

    /**
     * 日期时间
     */
    DATETIME,

    /**
     * 大字段
     */
    BLOB
}
