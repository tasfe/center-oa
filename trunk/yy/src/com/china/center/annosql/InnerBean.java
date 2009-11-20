/*
 * �� �� ��:  InnerBean.java
 * ��    Ȩ:  centerchina Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * ��    ��:  <����>
 * �� �� ��:  admin
 * �޸�ʱ��:  2007-9-30
 * ���ٵ���:  <���ٵ���>
 * �޸ĵ���:  <�޸ĵ���>
 * �޸�����:  <�޸�����>
 */
package com.china.center.annosql;


import java.io.Serializable;
import java.lang.reflect.Field;


/**
 * @author admin
 * @version [�汾��, 2007-9-30]
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾]
 */
public class InnerBean implements Serializable
{
    private String columnName = "";

    private String fieldName = "";

    private boolean relationship = false;

    private Field field = null;

    public InnerBean()
    {}

    /**
     * @return ���� columnName
     */
    public String getColumnName()
    {
        return columnName;
    }

    /**
     * @param ��columnName���и�ֵ
     */
    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    /**
     * @return ���� fieldName
     */
    public String getFieldName()
    {
        return fieldName;
    }

    /**
     * @param ��fieldName���и�ֵ
     */
    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    /**
     * @return the relationship
     */
    public boolean isRelationship()
    {
        return relationship;
    }

    /**
     * @param relationship
     *            the relationship to set
     */
    public void setRelationship(boolean relationship)
    {
        this.relationship = relationship;
    }

    /**
     * @return the field
     */
    public Field getField()
    {
        return field;
    }

    /**
     * @param field
     *            the field to set
     */
    public void setField(Field field)
    {
        this.field = field;
    }

}
