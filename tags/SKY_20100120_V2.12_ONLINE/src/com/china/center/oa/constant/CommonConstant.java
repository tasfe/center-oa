/**
 * File Name: CommonConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-3-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.constant;


import com.china.center.annotation.Defined;


/**
 * CommonConstant
 * 
 * @author zhuzhu
 * @version 2009-3-15
 * @see CommonConstant
 * @since 1.0
 */
public interface CommonConstant
{
    /**
     * ��ʼ
     */
    @Defined(key = "commonStatus", value = "��ʼ")
    int STATUS_INIT = 0;

    @Defined(key = "commonStatus", value = "�ύ")
    int STATUS_SUBMIT = 1;

    /**
     * ͨ��
     */
    @Defined(key = "commonStatus", value = "ͨ��")
    int STATUS_PASS = 2;

    /**
     * ����
     */
    @Defined(key = "commonStatus", value = "����")
    int STATUS_REJECT = 3;

    /**
     * ����
     */
    @Defined(key = "commonStatus", value = "����")
    int STATUS_END = 99;

    @Defined(key = "commonResult", value = "��ʼ")
    int RESULT_INIT = 0;

    @Defined(key = "commonResult", value = "����")
    int RESULT_OK = 1;

    @Defined(key = "commonResult", value = "�쳣")
    int RESULT_ERROR = 2;
}
