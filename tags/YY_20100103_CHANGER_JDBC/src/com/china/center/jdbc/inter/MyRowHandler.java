/*
 * �ļ�����ISPRowHandler.java
 * ��Ȩ��Copyright 2002-2007 centerchina Tech. Co. Ltd. All Rights Reserved.
 * ������
 * �޸��ˣ�c60012340
 * �޸�ʱ�䣺2007-6-19
 * ���ٵ��ţ�
 * �޸ĵ��ţ�
 * �޸����ݣ�����
 */
package com.china.center.jdbc.inter;


import java.lang.reflect.InvocationTargetException;

import com.ibatis.sqlmap.client.event.RowHandler;


/**
 * ��һ�仰���ܼ�����
 * 
 * @author c60012340
 * @version 2007-6-19
 * @see MyRowHandler
 * @since
 */

public class MyRowHandler implements RowHandler
{
    private Convert convertEncode = null;

    private RowHandler rowHandler = null;

    public MyRowHandler(RowHandler rowHandler, Convert convertEncode)
    {
        this.rowHandler = rowHandler;
        this.convertEncode = convertEncode;
    }

    public void handleRow(Object valueObject)
    {
        try
        {
            convertEncode.decodeObject(valueObject);
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        rowHandler.handleRow(valueObject);
    }

}
