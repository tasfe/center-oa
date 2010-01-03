/**
 * File Name: WorkLogConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-2-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.constant;


import com.china.center.annotation.Defined;


/**
 * WorkLogConstant
 * 
 * @author zhuzhu
 * @version 2009-2-15
 * @see WorkLogConstant
 * @since 1.0
 */
public interface WorkLogConstant
{
    /**
     * ��־��ʼ
     */
    @Defined(key = "workLogStatus", value = "��ʼ")
    int WORKLOG_STATUS_INIT = 0;

    /**
     * ��־�ύ
     */
    @Defined(key = "workLogStatus", value = "�ύ")
    int WORKLOG_STATUS_SUBMIT = 1;

    /**
     * �����ύ
     */
    @Defined(key = "workLogResult", value = "����")
    int WORKLOG_RESULT_COMMON = 0;

    /**
     * �����ύ
     */
    @Defined(key = "workLogResult", value = "����")
    int WORKLOG_RESULT_LAZY = 1;
    
    /**
     * �ݷ�
     */
    int WORKTYPE_VISIT = 0;
}
