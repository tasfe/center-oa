/**
 *
 */
package com.china.centet.yongyin.constant;

/**
 * ѯ�۵ĳ�������
 * 
 * @author Administrator
 */
public interface PriceConstant
{
    /**
     * ����
     */
    int PRICE_COMMON = 0;

    /**
     * �ܾ�
     */
    int PRICE_REJECT = 1;

    /**
     * ѯ�۳�ʼ��
     */
    int PRICE_ASK_STATUS_INIT = 0;

    /**
     * ѯ����(ѯ��û�н���)
     */
    int PRICE_ASK_STATUS_PROCESSING = 1;

    /**
     * ѯ���쳣
     */
    int PRICE_ASK_STATUS_EXCEPTION = 2;

    /**
     * ѯ�۽���
     */
    int PRICE_ASK_STATUS_END = 3;

    /**
     * ѯ������-����
     */
    int PRICE_ASK_TYPE_INNER = 0;

    /**
     * ѯ������-����
     */
    int PRICE_ASK_TYPE_NET = 1;

    /**
     * û������
     */
    int OVERTIME_NO = 0;

    /**
     * ����
     */
    int OVERTIME_YES = 1;

    /**
     * �����Ƿ�����-��
     */
    int HASAMOUNT_OK = 0;

    /**
     * �����Ƿ�����-��
     */
    int HASAMOUNT_NO = 1;

    /**
     * ѯ�۽����̶�-һ��(2Сʱ)
     */
    int PRICE_INSTANCY_COMMON = 0;

    /**
     * ѯ�۽����̶�-����(1Сʱ)
     */
    int PRICE_INSTANCY_INSTANCY = 1;

    /**
     * ѯ�۽����̶�-�ǳ�����(30����)
     */
    int PRICE_INSTANCY_VERYINSTANCY = 2;

    /**
     * ѯ�۽����̶�-����ѯ��(4Сʱ)
     */
    int PRICE_INSTANCY_NETWORK = 3;

}
