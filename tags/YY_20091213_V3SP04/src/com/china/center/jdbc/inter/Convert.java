/*
 * �ļ�����Convert.java
 * ��Ȩ��Copyright by www.centerchina.com
 * ������
 * �޸��ˣ�zhuzhu
 * �޸�ʱ�䣺2007-3-3
 *
 */

package com.china.center.jdbc.inter;


import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;


/**
 * Convert�ӿ�(ת��)
 * 
 * @author zhuzhu
 * @version 2007-3-3
 * @see Convert
 * @since
 */

public interface Convert
{
    /**
     * Description: �����ݿ⵽����Ľ���<br>
     * 
     * @param originStr
     * @return String
     */
    String decode(String originStr);

    /**
     * Description:�ӳ������ݿ�ı��� <br>
     * 
     * @param originStr
     * @return String
     */
    String encode(String originStr);

    /**
     * Description: ��List[Map]�������<br>
     * 
     * @param list
     * @return List
     */
    List decodeMapInList(List list);

    /**
     * Description:��map������� <br>
     * 
     * @param map
     * @return Map
     */
    Map decodeMap(Map map);

    Object decodeObject(Object obj)
        throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;

    Object encodeObject(Object obj)
        throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;

    List decodeList(List srcList)
        throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;

}
