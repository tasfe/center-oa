/**
 * File Name: ExamineConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-1-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.constant;


import com.china.center.annotation.Defined;


/**
 * ExamineConstant
 * 
 * @author zhuzhu
 * @version 2009-1-7
 * @see ExamineConstant
 * @since 1.0
 */
public interface ExamineConstant
{
    /**
     * ��ʼ��
     */
    @Defined(key = "examineStatus", value = "��ʼ��")
    int EXAMINE_STATUS_INIT = 0;

    /**
     * ��ҵ��Աȷ��
     */
    @Defined(key = "examineStatus", value = "��ҵ��Աȷ��")
    int EXAMINE_STATUS_SUBMIT = 1;

    /**
     * ҵ��Ա����
     */
    @Defined(key = "examineStatus", value = "ҵ��Ա����")
    int EXAMINE_STATUS_REJECT = 2;

    /**
     * ��������
     */
    @Defined(key = "examineStatus", value = "��������")
    int EXAMINE_STATUS_PASS = 3;

    /**
     * ���˽���
     */
    @Defined(key = "examineStatus", value = "���˽���")
    int EXAMINE_STATUS_END = 4;

    /**
     * �ն�
     */
    @Defined(key = "examineType", value = "�ն�")
    int EXAMINE_TYPE_TER = CustomerConstant.SELLTYPE_TER;

    /**
     * ��չ
     */
    @Defined(key = "examineType", value = "��չ")
    int EXAMINE_TYPE_EXPEND = CustomerConstant.SELLTYPE_EXPEND;

    @Defined(key = "examineAbs", value = "�ǳ���")
    int EXAMINE_ABS_FALSE = 0;

    /**
     * ��չ
     */
    @Defined(key = "examineAbs", value = "����")
    int EXAMINE_ABS_TRUE = 1;

    /**
     * �ֹ�˾������
     */
    @Defined(key = "attType", value = "�ֹ�˾������")
    int EXAMINE_ATTTYPE_LOCATION = 0;

    /**
     * ���ž�����
     */
    @Defined(key = "attType", value = "���ž�����")
    int EXAMINE_ATTTYPE_DEPARTMENT = 1;

    /**
     * ���˿���
     */
    @Defined(key = "attType", value = "���˿���")
    int EXAMINE_ATTTYPE_PERSONAL = 2;

    /**
     * ������--�¿ͻ�����
     */
    int EXAMINE_ITEM_TYPE_NEWCUSTOMER = 1;

    /**
     * ������--�Ͽͻ�����
     */
    int EXAMINE_ITEM_TYPE_OLDCUSTOMER = 2;

    /**
     * ������--����
     */
    int EXAMINE_ITEM_TYPE_PROFIT = 3;

    /**
     * ������--��������
     */
    int EXAMINE_ITEM_TYPE_CPROFIT = 4;

    /**
     * ������--��Ʒ����(����)
     */
    int EXAMINE_ITEM_TYPE_PRODUCT = 5;
    
    /**
     * ������--��Ʒ����
     */
    int EXAMINE_ITEM_TYPE_PRODUCT2 = 9995;

    /**
     * ������--�¿ͻ�����(�ն�)
     */
    int EXAMINE_ITEM_TYPE_TER_NEWCUSTOMER = 6;

    /**
     * ������--�Ͽͻ�����(�ն�)
     */
    int EXAMINE_ITEM_TYPE_TER_OLDCUSTOMER = 7;

    /**
     * ��ʼ��
     */
    @Defined(key = "examineResult", value = "��ʼ")
    int EXAMINE_RESULT_INIT = 0;

    /**
     * ���˽��--�պ�
     */
    @Defined(key = "examineResult", value = "���")
    int EXAMINE_RESULT_OK = 1;

    /**
     * ���˽��--����Ԥ��
     */
    @Defined(key = "examineResult", value = "����Ԥ��")
    int EXAMINE_RESULT_GOOD = 2;

    @Defined(key = "examineResult", value = "δ���")
    int EXAMINE_RESULT_LESS = 3;

    /**
     * ������״̬--��ʼ
     */
    @Defined(key = "examineItemStatus", value = "��ʼ")
    int EXAMINE_ITEM_STATUS_INIT = 0;

    /**
     * ������״̬--����
     */
    @Defined(key = "examineItemStatus", value = "����")
    int EXAMINE_ITEM_STATUS_END = 1;
}
