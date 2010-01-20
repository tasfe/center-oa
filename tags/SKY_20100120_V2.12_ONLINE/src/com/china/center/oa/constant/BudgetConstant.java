/**
 * File Name: BudgetConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-12-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.constant;


import com.china.center.annotation.Defined;


/**
 * BudgetConstant
 * 
 * @author zhuzhu
 * @version 2008-12-2
 * @see BudgetConstant
 * @since 1.0
 */
public interface BudgetConstant
{
    /**
     * Ԥ��--��ʼ��
     */
    @Defined(key = "budgetStatus", value = "��ʼ��")
    int BUDGET_STATUS_INIT = 0;

    /**
     * Ԥ��--����
     */
    @Defined(key = "budgetStatus", value = "����")
    int BUDGET_STATUS_REJECT = 1;

    /**
     * �ύ
     */
    @Defined(key = "budgetStatus", value = "�ύ")
    int BUDGET_STATUS_SUBMIT = 2;

    /**
     * Ԥ��--ͨ��(��ʽԤ��)
     */
    @Defined(key = "budgetStatus", value = "ͨ��")
    int BUDGET_STATUS_PASS = 3;

    /**
     * Ԥ��--����
     */
    @Defined(key = "budgetStatus", value = "����")
    int BUDGET_STATUS_END = 99;

    /**
     * Ԥ��ִ��--��ʼ��
     */
    @Defined(key = "budgetCarry", value = "δִ��")
    int BUDGET_CARRY_INIT = 0;

    /**
     * Ԥ��ִ��--������
     */
    @Defined(key = "budgetCarry", value = "ִ����")
    int BUDGET_CARRY_DOING = 1;

    /**
     * Ԥ��ִ��--ִ��ƽ��
     */
    @Defined(key = "budgetCarry", value = "ƽ��")
    int BUDGET_CARRY_BALANCE = 2;

    /**
     * Ԥ��ִ��--��֧
     */
    @Defined(key = "budgetCarry", value = "��֧")
    int BUDGET_CARRY_OVER = 3;

    /**
     * Ԥ��ִ��--��ʡ
     */
    @Defined(key = "budgetCarry", value = "��Լ")
    int BUDGET_CARRY_LESS = 4;

    @Defined(key = "budgetType", value = "��˾Ԥ��")
    int BUDGET_TYPE_COMPANY = 0;

    @Defined(key = "budgetType", value = "����Ԥ��")
    int BUDGET_TYPE_DEPARTMENT = 1;

    @Defined(key = "budgetLevel", value = "���Ԥ��")
    int BUDGET_LEVEL_YEAR = 0;

    @Defined(key = "budgetLevel", value = "����Ԥ��")
    int BUDGET_LEVEL_QUARTER = 1;

    @Defined(key = "budgetLevel", value = "�¶�Ԥ��")
    int BUDGET_LEVEL_MONTH = 2;

    /**
     * ��Ԥ��
     */
    String BUDGET_ROOT = "0";

    /**
     * Ԥ���ִ�������200��ʼ��299
     */
    int BUGFET_PLAN_TYPE = 201;

    /**
     * ���Ԥ��
     */
    @Defined(key = "budgetApplyType", value = "���Ԥ��")
    int BUDGET_APPLY_TYPE_MODIFY = 0;

    /**
     * ׷��Ԥ��
     */
    @Defined(key = "budgetApplyType", value = "׷��Ԥ��")
    int BUDGET_APPLY_TYPE_ADD = 1;

    /**
     * ��ʼ��
     */
    @Defined(key = "budgetApplyStatus", value = "��ʼ��")
    int BUDGET_APPLY_STATUS_INIT = 0;

    /**
     * ����
     */
    @Defined(key = "budgetApplyStatus", value = "����")
    int BUDGET_APPLY_STATUS_REJECT = 1;

    /**
     * �ύ
     */
    @Defined(key = "budgetApplyStatus", value = "�������ܼ��׼")
    int BUDGET_APPLY_STATUS_WAIT_CFO = 2;

    /**
     * �����ܼ�ͨ��
     */
    @Defined(key = "budgetApplyStatus", value = "���ܾ����׼")
    int BUDGET_APPLY_STATUS_WAIT_COO = 3;

    /**
     * �ܾ���ͨ��
     */
    @Defined(key = "budgetApplyStatus", value = "�����³���׼")
    int BUDGET_APPLY_STATUS_WAIT_CEO = 4;

    /**
     * �Ѻ�׼
     */
    @Defined(key = "budgetApplyStatus", value = "�Ѻ�׼")
    int BUDGET_APPLY_STATUS_END = 99;
}
