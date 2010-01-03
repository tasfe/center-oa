/**
 * File Name: GroupConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.constant;


import com.china.center.annotation.Defined;


/**
 * GroupConstant
 * 
 * @author zhuzhu
 * @version 2009-4-7
 * @see GroupConstant
 * @since 1.0
 */
public interface GroupConstant
{
    /**
     * ����
     */
    @Defined(key = "groupType", value = "����")
    int GROUP_TYPE_PRIVATE = 0;

    /**
     * ����
     */
    @Defined(key = "groupType", value = "����")
    int GROUP_TYPE_PUBLIC = 1;

    /**
     * ϵͳ
     */
    @Defined(key = "groupType", value = "ϵͳ")
    int GROUP_TYPE_SYSTEM = 2;
}
