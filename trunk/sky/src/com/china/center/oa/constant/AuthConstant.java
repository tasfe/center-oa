/**
 * File Name: AuthConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.constant;

/**
 * AuthConstant
 * 
 * @author zhuzhu
 * @version 2008-11-2
 * @see AuthConstant
 * @since 1.0
 */
public interface AuthConstant
{
    /**
     * Ĭ�϶��е�
     */
    String PUNLIC_AUTH = "0000";

    /**
     * ��������
     */
    String LOCATION_OPR = "010101";

    /**
     * ������Ա
     */
    String STAFFER_OPR = "010201";

    /**
     * ������ɫ(��ʱ�Ͳ����û�һ����Ȩ��)
     */
    String ROLE_OPR = "010401";

    /**
     * �����û�
     */
    String USER_OPR = "010401";

    /**
     * ��֯�ṹ����
     */
    String ORG_MANAGER = "0105";

    /**
     * ���Ź���
     */
    String DEPARTMENT_MANAGER = "0106";

    /**
     * ְ�����
     */
    String POST_MANAGER = "0107";

    /**
     * ������ɾ�Ŀͻ�
     */
    String CUSTOMER_OPR = "0202";

    /**
     * �����ͻ�
     */
    String CUSTOMER_CHECK = "0203";

    /**
     * �鿴�Ǳ��˵Ŀͻ���ϵ��ʽ
     */
    String CUSTOMER_OQUERY = "0204";

    /**
     * �������ͻ�
     */
    String CUSTOMER_APPLY_ASSIGN = "0205";

    /**
     * ��ѯ��ʷ
     */
    String CUSTOMER_QUERY_HIS = "0206";

    /**
     * ��ѯ�ֹ�˾���пͻ�
     */
    String CUSTOMER_QUERY_LOCATION = "0207";

    /**
     * ����ͻ�
     */
    String CUSTOMER_UPLOAD = "0208";

    /**
     * �ͻ��ֲ�
     */
    String CUSTOMER_DISTRIBUTE = "0209";

    /**
     * �ͻ��ֹ�˾ȫ��ͬ��
     */
    String CUSTOMER_SYNCHRONIZATION = "0210";

    /**
     * �ͻ�����
     */
    String CUSTOMER_RECLAIM = "0211";

    /**
     * ��ѯ��Ӧ��
     */
    String CUSTOMER_QUERY_PROVIDER = "0212";

    /**
     * ������Ӧ��
     */
    String CUSTOMER_OPR_PROVIDER = "0213";

    /**
     * �ͻ��������
     */
    String CUSTOMER_ASSIGN_CODE = "0214";

    /**
     * �ͻ�/��Ӧ�� �޸ĺ˶�
     */
    String CUSTOMER_HIS_CHECK = "0215";

    /**
     * �ͻ����
     */
    String CUSTOMER_CHECK_PARENT = "0216";

    /**
     * �ͻ����
     */
    String CUSTOMER_CHECK_COMMON = "021601";

    /**
     * �ͻ��������
     */
    String CUSTOMER_CHECK_CHECK = "021602";

    /**
     * ������ƽ���鿴
     */
    String CUSTOMER_CHECK_ALLQUERY = "021603";

    /**
     * �ͻ��������
     */
    String CUSTOMER_CREDIT_CHECK = "0217";

    /**
     * ��ѯ���п�������
     */
    String CITYCONFIG_QUERY = "0301";

    /**
     * �������п�������
     */
    String CITYCONFIG_OPR = "0302";

    /**
     * ��ѯ����
     */
    String EXAMINE_QUERY = "0303";

    /**
     * ��������
     */
    String EXAMINE_OPR = "0304";

    /**
     * ���˱��ȷ��
     */
    String EXAMINE_UPDATE_COMMIT = "0305";

    /**
     * �ͻ������ѯ
     */
    String EXAMINE_PROFIT_QUERY = "0306";

    /**
     * �ͻ��������
     */
    String EXAMINE_PROFIT_OPR = "0307";

    /**
     * ҵ��Ա����־
     */
    String WORKLOG_OPR = "0401";

    /**
     * ��־����
     */
    String WORKLOG_ANY = "0402";

    /**
     * ��־�ͻ�ȫ�ֲ�ѯ
     */
    String WORKLOG_GBOAL_QUERY = "0403";

    /**
     * Ԥ���ѯ
     */
    String BUDGET_QUERY = "0501";

    /**
     * Ԥ�����
     */
    String BUDGET_OPR = "0502";

    /**
     * Ԥ�����
     */
    String BUDGET_CHECK = "0503";

    /**
     * ��Ԥ�������
     */
    String BUDGET_ADDROOT = "0504";

    /**
     * ��Ԥ������(����Ԥ��)
     */
    String BUDGET_OPRROOT = "0505";

    /**
     * Ԥ����
     */
    String BUDGET_CHANGE = "0506";

    /**
     * Ԥ����--������
     */
    String BUDGET_CHANGE_APPLY = "050601";

    /**
     * Ԥ��������--�����ܼ�
     */
    String BUDGET_CHANGE_APPROVE_CFO = "050602";

    /**
     * Ԥ��������--�ܾ���
     */
    String BUDGET_CHANGE_APPROVE_COO = "050603";

    /**
     * Ԥ��������--���³�
     */
    String BUDGET_CHANGE_APPROVE_CEO = "050604";

    /**
     * ˽��Ⱥ�����
     */
    String GROUP_OPR = "0601";

    /**
     * ����Ⱥ�����
     */
    String GROUP_PUBLIC_OPR = "0602";

    /**
     * ϵͳȺ�����
     */
    String GROUP_SYSTEM_OPR = "0603";

    /**
     * ����ģ�����
     */
    String FLOW_TEMPLATE_OPR = "0701";

    /**
     * ���̶������
     */
    String FLOW_DEFINED_OPR = "0702";

    /**
     * ǿ�Ʒ���
     */
    String FLOW_DEFINED_FORCE_DROP = "0703";

    /**
     * ���õȼ�����
     */
    String CREDIT_OPR = "0901";

    /**
     * ���õȼ�����
     */
    String CREDIT_LEVEL_OPR = "0902";

    /**
     * ǿ���޸�
     */
    String CREDIT_FORCE_UPDATE = "0903";
}
