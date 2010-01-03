/**
 *
 */
package com.china.centet.yongyin.constant;

/**
 * @author Administrator
 */
public interface FlowConstant
{
    /**
     * ��Աʽ����
     */
    int FLOW_TYPE_USER = 0;

    /**
     * ��ɫʽ����
     */
    int FLOW_TYPE_ROLE = 1;

    /**
     * ȫ��
     */
    int FLOW_TYPE_ALL = 2;

    /**
     * ְԱ����
     */
    int FLOW_TYPE_STAFFER = 3;

    /**
     * ��
     */
    int FLOW_TYPE_NONE = 99;

    /**
     * ���̳�ʼ��
     */
    int FLOW_STATUS_INIT = 0;

    /**
     * ���̷���
     */
    int FLOW_STATUS_REALSE = 1;

    /**
     * ���̷���
     */
    int FLOW_STATUS_DROP = 2;

    /**
     * ����ʵ����ʼ
     */
    int FLOW_INSTANCE_BEGIN = 0;

    /**
     * ����ʵ������
     */
    int FLOW_INSTANCE_END = 99;

    /**
     * FK��userId��indexֵ
     */
    int FK_INDEX_USERID = 1;
}
