/**
 * File Name: CommonQuery.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-12-14<br>
 * Grant: open source to everybody
 */
package com.china.center.common.query;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.china.center.common.ConditionParse;


/**
 * CommonQuery
 * 
 * @author zhuzhu
 * @version 2008-12-14
 * @see CommonQuery
 * @since 1.0
 */
public interface CommonQuery
{
    /**
     * �����������
     * 
     * @param key
     * @param request
     * @param queryCondition
     * @return
     */
    int getCount(String key, HttpServletRequest request, ConditionParse queryCondition);

    /**
     * ��ѯ�����
     * 
     * @param key
     * @param request
     * @param queryCondition
     * @return
     */
    List queryResult(String key, HttpServletRequest request, ConditionParse queryCondition);

    /**
     * ��������ǰ׺
     * 
     * @param key
     * @param request
     * @return
     */
    String getOrderPfix(String key, HttpServletRequest request);

    /**
     * ������������
     * 
     * @param request
     * @return
     */
    String getSortname(HttpServletRequest request);
}
