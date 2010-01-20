/**
 * File Name: StafferConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-2-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.constant;


import com.china.center.annotation.Defined;


/**
 * StafferConstant
 * 
 * @author zhuzhu
 * @version 2009-2-8
 * @see StafferConstant
 * @since 1.0
 */
public interface StafferConstant
{
    /**
     * ��������Ա
     */
    String SUPER_STAFFER = "99999999";
    
    @Defined(key = "examType", value = "�ն�")
    int EXAMTYPE_TERMINAL = ExamineConstant.EXAMINE_TYPE_TER;

    @Defined(key = "examType", value = "��չ")
    int EXAMTYPE_EXPAND = ExamineConstant.EXAMINE_TYPE_EXPEND;

    @Defined(key = "examType", value = "����")
    int EXAMTYPE_OTHER = 99;

    @Defined(key = "stafferStatus", value = "����")
    int STATUS_COMMON = 0;
    
    @Defined(key = "stafferStatus", value = "����")
    int STATUS_DROP = 99;
}
