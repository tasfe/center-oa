/**
 * File Name: MakeConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: ZHUACHEN<br>
 * CreateTime: 2009-10-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.constant;


import com.china.center.annotation.Defined;


/**
 * MakeConstant
 * 
 * @author ZHUZHU
 * @version 2009-10-8
 * @see MakeConstant
 * @since 1.0
 */
public interface MakeConstant
{
    int MAKE_TOKEN_01 = 1;

    int MAKE_TOKEN_02 = 2;

    int MAKE_TOKEN_03 = 3;

    int MAKE_TOKEN_04 = 4;

    int MAKE_TOKEN_05 = 5;

    int MAKE_TOKEN_06 = 6;

    int MAKE_TOKEN_07 = 7;

    int MAKE_TOKEN_08 = 8;

    int MAKE_TOKEN_09 = 9;

    int MAKE_TOKEN_10 = 10;

    int MAKE_TOKEN_11 = 11;

    int MAKE_TOKEN_12 = 12;

    int MAKE_TOKEN_13 = 13;

    int MAKE_TOKEN_14 = 14;

    /**
     * ���Ʋ�Ʒ
     */
    @Defined(key = "makeType", value = "���Ʋ�Ʒ")
    int MAKE_TYPE_CUSTOMIZE = 0;

    /**
     * ���ɲ�Ʒ
     */
    @Defined(key = "makeType", value = "���в�Ʒ")
    int MAKE_TYPE_SELF = 1;

    /**
     * �Ƿ����--��
     */
    @Defined(key = "sampleType", value = "��")
    int SAMPLETYPE_YES = 0;

    /**
     * �Ƿ����--��
     */
    @Defined(key = "sampleType", value = "��")
    int SAMPLETYPE_NO = 1;

    /**
     * ���ʽ-���ڸ���
     */
    @Defined(key = "mbillType", value = "���ڸ���")
    int BILLTYPE_DIRECT = 0;

    /**
     * ���ʽ-�����
     */
    @Defined(key = "mbillType", value = "�����")
    int BILLTYPE_REDIRECT = 1;

    /**
     * �ͻ�����-��ͨ�ͻ�
     */
    @Defined(key = "mcustomerType", value = "������")
    int CUSTOMERTYPE_COMMON = 0;

    /**
     * �ͻ�����-�߼��ͻ�
     */
    @Defined(key = "mcustomerType", value = "�ն�")
    int CUSTOMERTYPE_SPECIAL = 1;

    /**
     * �Ƿ����--��
     */
    @Defined(key = "appraisalType", value = "��")
    int APPRAISALTYPE_YES = 0;

    /**
     * �Ƿ����--��
     */
    @Defined(key = "appraisalType", value = "��")
    int APPRAISALTYPE_NO = 1;

    /**
     * ���й���
     */
    @Defined(key = "gujiaType", value = "���й���(4Сʱ�ڸ���)")
    int GUJIATYPE_CENTER = 0;

    /**
     * ��������
     */
    @Defined(key = "gujiaType", value = "��������(24Сʱ�ڸ���)")
    int GUJIATYPE_FACTORY = 1;

    /**
     * ���--����
     */
    @Defined(key = "designType", value = "����")
    int DESIGNTYPE_INIT = 0;

    /**
     * ���--ȫ��
     */
    @Defined(key = "designType", value = "ȫ��")
    int DESIGNTYPE_ALL = 1;

    @Defined(key = "mappType", value = "�ڼ���������ѯ��")
    int APP_TYPE_00 = 0;

    @Defined(key = "mappType", value = "������ѯ��")
    int APP_TYPE_01 = 1;

    @Defined(key = "mappType", value = "�����ѯ��")
    int APP_TYPE_02 = 2;

    /**
     * �Ƿ����-��
     */
    int END_TOKEN_NO = 0;

    /**
     * �Ƿ����-��
     */
    int END_TOKEN_YES = 1;

    /**
     * ���̽���
     */
    int STATUS_END = 9999;

    /**
     * ��ʼ
     */
    int TOKEN_BEGIN = 1;

    /**
     * �ڶ����Ķ��Ƹ���
     */
    int MAKE_FILE_TYPE_21 = 101;

    /**
     * ������ѯ
     */
    @Defined(key = "requestType", value = "������ѯ")
    int REQUEST_TYPE_00 = 0;

    @Defined(key = "requestType", value = "�ɱ�Ԥ��")
    int REQUEST_TYPE_01 = 1;

    @Defined(key = "requestType", value = "���й���")
    int REQUEST_TYPE_02 = 2;

    @Defined(key = "requestType", value = "��������")
    int REQUEST_TYPE_03 = 3;

    @Defined(key = "requestType", value = "����")
    int REQUEST_TYPE_04 = 4;

    @Defined(key = "requestType", value = "ȫ��")
    int REQUEST_TYPE_05 = 5;

    /**
     * ��������
     */
    @Defined(key = "exceptionReason", value = "����")
    int EXCEPTION_END_0 = 0;

    @Defined(key = "exceptionReason", value = "����δ��")
    int EXCEPTION_END_1 = 1;

    @Defined(key = "exceptionReason", value = "���δ��")
    int EXCEPTION_END_2 = 2;

    @Defined(key = "exceptionReason", value = "�ͻ�����仯")
    int EXCEPTION_END_3 = 3;

    @Defined(key = "exceptionReason", value = "��������")
    int EXCEPTION_END_4 = 4;

    @Defined(key = "exceptionReason", value = "����ʱ�䲻��")
    int EXCEPTION_END_5 = 5;

    @Defined(key = "exceptionReason", value = "�������������������")
    int EXCEPTION_END_6 = 6;

    @Defined(key = "exceptionReason", value = "����")
    int EXCEPTION_END_99 = 99;
}
