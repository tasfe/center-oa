/**
 * File Name: PlanConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-1-11<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.constant;


import com.china.center.annotation.Defined;


/**
 * PlanConstant
 * 
 * @author zhuzhu
 * @version 2009-1-11
 * @see PlanConstant
 * @since 1.0
 */
public interface PlanConstant
{
    /**
     * �ƻ��ĳ�ʼ̬
     */
    @Defined(key = "planStatus", value = "��ʼ")
    int PLAN_STATUS_INIT = 0;

    @Defined(key = "planStatus", value = "����")
    int PLAN_STATUS_END = 1;

    /**
     * һ��ִ��
     */
    @Defined(key = "carryType", value = "һ��ִ��")
    int PLAN_CARRYTYPES_ONCE = 0;

    /**
     * ÿ��ִ��
     */
    @Defined(key = "carryType", value = "ÿ��ִ��")
    int PLAN_CARRYTYPES_EVERYDAY = 1;
}
