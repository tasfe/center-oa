/**
 * File Name: ShortMessageConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-7-28<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.note.bean;


import com.china.center.annotation.Defined;


/**
 * ShortMessageConstant
 * 
 * @author ZHUZHU
 * @version 2009-7-28
 * @see ShortMessageConstant
 * @since 1.0
 */
public interface ShortMessageConstant
{
    /**
     * only send
     */
    int MTYPE_ONLY_SEND = 0;

    /**
     * send and receive
     */
    int MTYPE_ONLY_SEND_RECEIVE = 1;

    /**
     * INIT
     */
    @Defined(key = "smsStatus", value = "��ʼ")
    int STATUS_INIT = 0;

    /**
     * STATUS_WAIT_SEND
     */
    @Defined(key = "smsStatus", value = "�ȴ�����")
    int STATUS_WAIT_SEND = 11;

    /**
     * send success
     */
    @Defined(key = "smsStatus", value = "���ͳɹ�")
    int STATUS_SEND_SUCCESS = 1;

    /**
     * failure
     */
    @Defined(key = "smsStatus", value = "����ʧ��")
    int STATUS_SEND_FAILURE = 2;

    /**
     * receive success
     */
    @Defined(key = "smsStatus", value = "���ջظ�")
    int STATUS_RECEIVE_SUCCESS = 3;

    /**
     * timeout
     */
    @Defined(key = "smsStatus", value = "��ʱ����")
    int STATUS_OVER_TIME = 99;

    /**
     * end
     */
    @Defined(key = "smsStatus", value = "��������")
    int STATUS_END_COMMON = 100;
}
