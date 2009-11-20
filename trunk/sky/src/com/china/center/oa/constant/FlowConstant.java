/**
 *
 */
package com.china.center.oa.constant;


import com.china.center.annotation.Defined;


/**
 * @author Administrator
 */
public interface FlowConstant
{
    /**
     * ��ͨģʽ
     */
    @Defined(key = "flowMode", value = "��ͨģʽ")
    int FLOW_MODE_NONE = 0;

    /**
     * ģ��ģʽ
     */
    @Defined(key = "flowMode", value = "ģ��ģʽ")
    int FLOW_MODE_TEMPLATE = 1;

    /**
     * ������
     */
    @Defined(key = "parentType", value = "������")
    int FLOW_PARENTTYPE_ROOT = 0;

    /**
     * ������
     */
    @Defined(key = "parentType", value = "������")
    int FLOW_PARENTTYPE_SUB = 1;

    /**
     * HTMLģʽ
     */
    // @Defined(key = "flowMode", value = "HTMLģʽ")
    int FLOW_MODE_HTML = 2;

    /**
     * �ۺ�ģʽ
     */
    // @Defined(key = "flowMode", value = "�ۺ�ģʽ")
    int FLOW_MODE_ALL = 3;

    /**
     * ְԱ
     */
    @Defined(key = "flowPluginType", value = "ְԱ���")
    int FLOW_PLUGIN_STAFFER = 0;

    /**
     * Ⱥ��
     */
    @Defined(key = "flowPluginType", value = "Ⱥ����")
    int FLOW_PLUGIN_GROUP = 1;

    /**
     * ����
     */
    @Defined(key = "flowPluginType", value = "�������")
    int FLOW_PLUGIN_SELF = 2;

    /**
     * �����̲��
     */
    int FLOW_PLUGIN_SUBFLOW = 998;

    /**
     * ����
     */
    @Defined(key = "flowPluginType", value = "�������")
    int FLOW_PLUGIN_END = 999;

    /**
     * ��
     */
    int FLOW_PLUGIN_NONE = 9999;

    /**
     * ���̳�ʼ��
     */
    @Defined(key = "flowStatus", value = "��ʼ")
    int FLOW_STATUS_INIT = 0;

    /**
     * ���̷���
     */
    @Defined(key = "flowStatus", value = "����")
    int FLOW_STATUS_REALSE = 1;

    /**
     * ���̷���
     */
    @Defined(key = "flowStatus", value = "����")
    int FLOW_STATUS_DROP = 2;

    /**
     * ����ʵ����ʼ
     */
    int FLOW_INSTANCE_BEGIN = 0;

    /**
     * ����ʵ������
     */
    int FLOW_INSTANCE_END = 999;

    /**
     * FK��userId��indexֵ
     */
    int FK_INDEX_USERID = 1;

    /**
     * ֻ��
     */
    int TEMPLATE_READONLY_YES = 1;

    /**
     * ֻ��-no
     */
    int TEMPLATE_READONLY_NO = 0;

    /**
     * д����-yes
     */
    int TEMPLATE_EDIT_YES = TEMPLATE_READONLY_YES;

    /**
     * д����-no
     */
    int TEMPLATE_EDIT_NO = TEMPLATE_READONLY_NO;

    /**
     * ���߱�
     */
    int OPERATION_NO = 0;

    /**
     * �߱�
     */
    int OPERATION_YES = 1;

    /**
     * ��������--��ͨ����
     */
    @Defined(key = "tokenType", value = "��ͨ����")
    int TOKEN_TYPE_REALTOKEN = 0;

    /**
     * ��������--������
     */
    @Defined(key = "tokenType", value = "������")
    int TOKEN_TYPEE_ABSTOKEN = 1;

    /**
     * �ύģʽ--����ѡ��
     */
    @Defined(key = "tokenMode", value = "����ѡ��")
    int TOKEN_MODE_SELF = 0;

    /**
     * �ύģʽ--�Զ�,����Ҫѡ����Ա
     */
    @Defined(key = "tokenMode", value = "�Զ�ѡ��")
    int TOKEN_MODE_AUTO = 1;

    /**
     * �ύģʽ--ѡ��ָ������Ա
     */
    @Defined(key = "tokenMode", value = "ѡ��ָ����Χ")
    int TOKEN_MODE_SELECT = 2;

    @Defined(key = "instanceOper", value = "�ύ")
    int OPERATION_SUBMIT = 0;

    @Defined(key = "instanceOper", value = "ͨ��")
    int OPERATION_PASS = 1;

    @Defined(key = "instanceOper", value = "���ص���һ����")
    int OPERATION_REJECT = 2;

    @Defined(key = "instanceOper", value = "���ص���ʼ")
    int OPERATION_REJECTALL = 3;

    @Defined(key = "instanceOper", value = "�쳣��ֹ")
    int OPERATION_EXEND = 4;

    @Defined(key = "instanceOper", value = "����")
    int OPERATION_END = 5;

    @Defined(key = "instanceOper", value = "���ص������̵���һ����")
    int OPERATION_PARENT_REJECT = 6;
}
