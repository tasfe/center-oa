/**
 * File Name: CustomerConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.constant;


import com.china.center.annotation.Defined;


/**
 * CustomerConstant
 * 
 * @author ZHUZHU
 * @version 2008-11-9
 * @see CustomerConstant
 * @since 1.0
 */
public interface CustomerConstant
{
    /**
     * ok
     */
    int STATUS_OK = 0;

    /**
     * apply
     */
    int STATUS_APPLY = 1;

    /**
     * ������ȴ�����code
     */
    int STATUS_WAIT_CODE = 3;

    /**
     * reject
     */
    int STATUS_REJECT = 2;

    int OPR_ADD = 0;

    int OPR_UPDATE = 1;

    int OPR_DEL = 2;

    /**
     * UPATE_CREDIT
     */
    int OPR_UPATE_CREDIT = 3;

    /**
     * ���еĿͻ�
     */
    int REAL_STATUS_IDLE = 0;

    /**
     * ��ʹ�õĿͻ�
     */
    int REAL_STATUS_USED = 1;

    /**
     * �����еĿͻ�
     */
    int REAL_STATUS_APPLY = 2;

    /**
     * �޳ɽ���¼
     */
    int BLOG_NO = 0;

    /**
     * �гɽ���¼
     */
    int BLOG_YES = 1;

    /**
     * ����Ƭ
     */
    int CARD_NO = 0;

    /**
     * ����Ƭ
     */
    int CARD_YES = 1;

    /**
     * ����ȫ��
     */
    int RECLAIMSTAFFER_ALL = 0;

    /**
     * ������չ
     */
    int RECLAIMSTAFFER_EXPEND = 1;

    /**
     * �����ն�
     */
    int RECLAIMSTAFFER_TER = 2;

    /**
     * �ն�
     */
    int SELLTYPE_TER = 0;

    /**
     * ��չ
     */
    int SELLTYPE_EXPEND = 1;

    @Defined(key = "checkStatus", value = "δ�˶�")
    int HIS_CHECK_NO = 0;

    @Defined(key = "checkStatus", value = "�˶�")
    int HIS_CHECK_YES = 1;

    /**
     * ���¿ͻ�
     */
    int HASNEW_YES = 0;

    /**
     * �����¿ͻ�
     */
    int HASNEW_NO = 1;

    /**
     * �¿ͻ�
     */
    @Defined(key = "c_newType", value = "�¿ͻ�")
    int NEWTYPE_NEW = 0;

    /**
     * �Ͽͻ�
     */
    @Defined(key = "c_newType", value = "�Ͽͻ�")
    int NEWTYPE_OLD = 1;

    /**
     * Ĭ�ϼ���
     */
    String CREDITLEVELID_DEFAULT = "90000000000000000001";
}
