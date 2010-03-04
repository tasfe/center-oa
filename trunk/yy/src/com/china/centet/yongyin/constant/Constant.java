/*
 * File Name: Constant.java CopyRight: Copyright by www.center.china
 * Description: Creater: zhuAchen CreateTime: 2007-8-16 Grant: open source to
 * everybody
 */
package com.china.centet.yongyin.constant;

/**
 * Constant
 * 
 * @author ZHUZHU
 * @version 2007-8-16
 * @see
 * @since
 */
public interface Constant
{
    int PAGE_SIZE = 20;

    int PAGE_COMMON_SIZE = 10;

    int PAGE_EXPORT = 500;

    /**
     * 0
     */
    String SYSTEM_LOCATION = "0";

    String VERSION10 = "1.0";

    String VERSION20 = "2.0";

    String VERSION30 = "3.0";

    String VERSION40 = "4.0";

    String CURRENT_VERSION = "2.0";

    String OUT_VERSION10 = "OUT";

    String OUT_VERSION20 = "OUT2.0";

    int VERSION_INDEX = 99;

    /**
     * ���ڵ�
     */
    int BOTTOMFLAG_NO = 0;

    /**
     * Ҷ�ڵ�
     */
    int BOTTOMFLAG_YES = 1;

    /**
     * ���۵�
     */
    int OUT_TYPE_OUTBILL = 0;

    /**
     * ��ⵥ
     */
    int OUT_TYPE_INBILL = 1;

    /**
     * ����
     */
    int STATUS_SAVE = 0;

    /**
     * �ύ
     */
    int STATUS_SUBMIT = 1;

    /**
     * ����
     */
    int STATUS_REJECT = 2;

    /**
     * ADMINͨ��
     */
    int STATUS_PASS = 3;

    /**
     * ���ͨ��
     */
    int STATUS_SEC_PASS = 4;

    /**
     * �ܾ���ͨ��
     */
    int STATUS_MANAGER_PASS = 6;

    /**
     * ��������Աͨ��
     */
    int STATUS_FLOW_PASS = 7;

    /**
     * δ����
     */
    int PAY_NOT = 0;

    /**
     * ����
     */
    int PAY_YES = 1;

    /**
     * ����
     */
    int PAY_OVER = 2;

    /**
     * �տ
     */
    int BILL_RECIVE = 0;

    /**
     * ���
     */
    int BILL_PAY = 1;

    /**
     * �����ڸ���
     */
    int BILL_TYPE_CHANGE = 2;

    /**
     * ����������
     */
    int BILL_TYPE_CHANGE_IN_CITY = 3;

    /**
     * ��������
     */
    int BILL_TYPE_CHANGE_CITY = 4;

    /**
     * ��ʽ����
     */
    int TEMPTYPE_COMMON = 0;

    /**
     * ��ʱ����
     */
    int TEMPTYPE_TEMP = 1;

    /**
     * Ȩ����֤����
     */
    char AUTH_PASS = '1';

    /**
     * �ɼ�
     */
    int ROLE_VISIBLE_YES = 1;

    /**
     * ���ɼ�
     */
    int ROLE_VISIBLE_NO = 0;

    /**
     * ��������Ա
     */
    int SUPER_SYSTEM_ROLE = 3;

    /**
     * ������С����
     */
    int PASSWORD_MIN_LENGTH = 10;

    /**
     * ȫ��session�����ʾ
     */
    String CURRENTLOCATIONID = "currentLocationId";

    /**
     * ����
     */
    int LOGIN_STATUS_LOCK = 1;

    /**
     * ״̬����
     */
    int LOGIN_STATUS_COMMON = 0;

    /**
     * ����¼ʧ�ܴ���
     */
    int LOGIN_FAIL_MAX = 5;

    /**
     * �ⵥ�ύ
     */
    int LOG_OPR_SUBMIT = 0;

    /**
     * �ⵥ����
     */
    int LOG_OPR_REJECT = 1;

    /**
     * ��Ʒ��
     */
    int TYPE_DEPOTPART_OK = 1;

    /**
     * ��Ʒ��
     */
    int TYPE_DEPOTPART_BAD = 0;

    /**
     * ���ϲ�
     */
    int TYPE_DEPOTPART_FEI = 2;

    /**
     * ��ʼ��
     */
    int OPR_STORAGE_INIT = 0;

    /**
     * �޸�
     */
    int OPR_STORAGE_UPDATE = 1;

    /**
     * �ⵥͨ��
     */
    int OPR_STORAGE_OUTBILL = 2;

    /**
     * ��ⵥ
     */
    int OPR_STORAGE_OUTBILLIN = 3;

    /**
     * �ƶ�
     */
    int OPR_STORAGE_MOVE = 4;

    /**
     * �ϳ�
     */
    int OPR_STORAGE_COMPOSE = 5;

    /**
     * ����ͨ��
     */
    int OPR_OUT_PASS = 0;

    /**
     * ��������
     */
    int OPR_OUT_REJECT = 1;

    /**
     * ����ķ���
     */
    int TRANSPORT_TYPE = 0;

    /**
     * ���䷽ʽ
     */
    int TRANSPORT_COMMON = 1;

    /**
     * ��������ʼ̬
     */
    int CONSIGN_INIT = 1;

    /**
     * ��������ͨ��̬
     */
    int CONSIGN_PASS = 2;

    /**
     * �������Ļظ� ��ʼ
     */
    int CONSIGN_REPORT_INIT = 0;

    /**
     * �������Ļظ� �����ջ�
     */
    int CONSIGN_REPORT_COMMON = 1;

    /**
     * �������Ļظ� �쳣�ջ�
     */
    int CONSIGN_REPORT_EXCEPTION = 2;

    // <option value="1">����</option>
    // <option value="4">����</option>
    /**
     * ����
     */
    int INBILL_OUT = 1;

    /**
     * ����
     */
    int INBILL_IN = 4;

    /**
     * ����;
     */
    int IN_WAY_NO = 0;

    /**
     * ��;
     */
    int IN_WAY = 1;

    /**
     * ��;����
     */
    int IN_WAY_OVER = 2;

    /**
     * �����쳣���ĳ�ʼ̬
     */
    int PRO_EXCEPTION_INIT = 0;

    /**
     * �ύ
     */
    int PRO_EXCEPTION_SUBMIT = 1;

    /**
     * ����
     */
    int PRO_EXCEPTION_REJECT = 2;

    /**
     * ͨ��
     */
    int PRO_EXCEPTION_PASS = 3;

    /**
     * ��
     */
    int SEX_MALE = 0;

    /**
     * Ů
     */
    int SEX_WOMAN = 1;

    /**
     * ͨ������
     */
    int OPRMODE_PASS = 0;

    /**
     * ����
     */
    int OPRMODE_REJECT = 1;

    int ROLE_TOP = 3;

    String SYSTEM_USER = "999999";
}
