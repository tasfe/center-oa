/**
 *
 */
package com.china.centet.yongyin.constant;

/**
 * @author Administrator
 */
public interface StockConstant
{
    /**
     * ��ʼ̬
     */
    int STOCK_STATUS_INIT = 0;

    /**
     * �ύ
     */
    int STOCK_STATUS_SUBMIT = 1;

    /**
     * ����
     */
    int STOCK_STATUS_REJECT = 2;

    /**
     * ������ͨ��
     */
    int STOCK_STATUS_MANAGERPASS = 3;

    /**
     * ѯ��Աͨ��(����ѯ��Աͨ��)
     */
    int STOCK_STATUS_PRICEPASS = 4;

    /**
     * �ɹ�����ͨ��
     */
    int STOCK_STATUS_STOCKPASS = 5;

    /**
     * �ɹ�����ͨ��
     */
    int STOCK_STATUS_STOCKMANAGERPASS = 6;

    /**
     * �ɹ�����
     */
    int STOCK_STATUS_END = 7;

    /**
     * �ɹ�����(����)
     */
    int STOCK_STATUS_LASTEND = 8;

    /**
     * �ɹ�item�Ŀ�ʼ
     */
    int STOCK_ITEM_STATUS_INIT = 0;

    /**
     * �ɹ�item�Ľ���
     */
    int STOCK_ITEM_STATUS_END = 1;

    /**
     * �ɹ�item��ѯ�۽���
     */
    int STOCK_ITEM_STATUS_ASK = 2;

    /**
     * ���쳣
     */
    int EXCEPTSTATUS_COMMON = 0;

    /**
     * �쳣(�۸�����С��)
     */
    int EXCEPTSTATUS_EXCEPTION_MIN = 1;

    /**
     * �쳣(Ǯ����)
     */
    int EXCEPTSTATUS_EXCEPTION_MONEY = 2;

    int DISPLAY_YES = 0;

    int DISPLAY_NO = 1;

    /**
     * û�и���
     */
    int STOCK_PAY_NO = 0;

    /**
     * ����
     */
    int STOCK_PAY_YES = 1;

    /**
     * ��������
     */
    int STOCK_PAY_APPLY = 2;

    /**
     * ����
     */
    int STOCK_PAY_REJECT = 3;

    /**
     * �ɹ���item�Ƿ񱻿ⵥ���� ��
     */
    int STOCK_ITEM_REF_NO = 0;

    /**
     * �ɹ���item�Ƿ񱻿ⵥ���� ��
     */
    int STOCK_ITEM_REF_YES = 1;

    /**
     * �ɹ�δ����
     */
    int STOCK_OVERTIME_NO = 0;

    /**
     * �ɹ�����
     */
    int STOCK_OVERTIME_YES = 1;

    /**
     * �ɹ�������״̬--��ʼ
     */
    int STOCK_ITEM_PAY_STATUS_INIT = 0;

    /**
     * �ɹ�������״̬--ʹ��
     */
    int STOCK_ITEM_PAY_STATUS_USED = 1;

}
