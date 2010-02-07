/**
 * File Name: PublicConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.constant;

/**
 * PublicConstant
 * 
 * @author zhuzhu
 * @version 2008-11-2
 * @see
 * @since
 */
public interface PublicConstant
{
    /**
     * �ɼ�
     */
    int ROLE_VISIBLE_YES = 1;

    /**
     * ���ɼ�
     */
    int ROLE_VISIBLE_NO = 0;

    /**
     * ��Ȩ�޵ļ���
     */
    int ROLE_LEVEL_ROOT = 0;

    /**
     * ����Ȩ��
     */
    int AUTH_TYPE_LOCATION = 0;

    /**
     * ϵͳȨ��
     */
    int AUTH_TYPE_SYSTEM = 1;

    /**
     * ��
     */
    int SEX_MAN = 0;

    /**
     * Ů
     */
    int SEX_WOMAN = 0;

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
     * ���ڵ�
     */
    int BOTTOMFLAG_NO = 0;

    /**
     * Ҷ�ڵ�
     */
    int BOTTOMFLAG_YES = 1;

    /**
     * ��������
     */
    String VIRTUAL_LOCATION = "-1";

    /**
     * �ܲ�����
     */
    String CENTER_LOCATION = "0";

    /**
     * ��������Ա
     */
    String SUPER_MANAGER = "0";

    /**
     * ������С����
     */
    int PASSWORD_MIN_LENGTH = 10;

    /**
     * ��֯�ṹ�Ķ���
     */
    int ORG_ROOT = 0;

    /**
     * ��֯�ṹ�ĵ�һ��
     */
    int ORG_FIRST = 1;

    /**
     * ����--�ύ
     */
    String OPERATION_SUBMIT = "�ύ";

    /**
     * ����--ͨ��
     */
    String OPERATION_PASS = "ͨ��";

    /**
     * ����--����
     */
    String OPERATION_REJECT = "����";

    /**
     * ����--�쳣����
     */
    String OPERATION_EXCEPTIONEND = "�쳣����";

    /**
     * ���
     */
    String OPERATION_CHANGE = "���";

    /**
     * ���
     */
    String OPERATION_DEL = "ɾ��";
}
