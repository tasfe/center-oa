/**
 * File Name: OutConstanst.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-10-4<br>
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.constant;

/**
 * OutConstanst
 * 
 * @author zhuzhu
 * @version 2008-10-4
 * @see
 * @since
 */
public interface OutConstanst
{
    /**
     * ��������(������������������ǵ���������������)
     */
    int INBILL_SELF_IN = 8;

    /**
     * ���۵�
     */
    int OUT_TYPE_OUTBILL = Constant.OUT_TYPE_OUTBILL;

    /**
     * ��ⵥ
     */
    int OUT_TYPE_INBILL = Constant.OUT_TYPE_INBILL;

    /**
     * �ϳɵ�
     */
    int OUT_TYPE_COMPOSE = 2;

    /**
     * ��Ʒ�Ϸ�
     */
    int OUTTYPES_COMPOSE = 7;

    /**
     * �����ƶ�
     */
    int OUT_TYPE_MOVE = 8;

    /**
     * �Զ�����
     */
    int OUT_TYPE_MODIFY = 9;

    /**
     * ��������
     */
    int OUT_CREDIT_COMMON = 0;

    /**
     * ���ó�֧
     */
    int OUT_CREDIT_OVER = 1;

    /**
     * �۸�Ϊ0
     */
    int OUT_CREDIT_MIN = 2;

    /**
     * �����տ�
     */
    int OUT_SAIL_TYPE_COMMON = 0;

    /**
     * �����
     */
    int OUT_SAIL_TYPE_MONEY = 1;

    /**
     * ʹ��ҵ��Ա���ö�ȺͿͻ����ö��(�ͻ����ö������)
     */
    int OUT_SAIL_TYPE_CREDIT_AND_CUR = 2;

    /**
     * �������ͻ�
     */
    String BLACK_LEVEL = "90000000000000000000";
}
