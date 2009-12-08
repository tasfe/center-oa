/**
 * File Name: CreditConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: ZHUACHEN<br>
 * CreateTime: 2009-11-1<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.constant;


import com.china.center.annotation.Defined;


/**
 * CreditConstant
 * 
 * @author ZHUZHU
 * @version 2009-11-1
 * @see CreditConstant
 * @since 1.0
 */
public interface CreditConstant
{
    /**
     * static credit
     */
    @Defined(key = "creditType", value = "��ָ̬��")
    int CREDIT_TYPE_STATIC = 0;

    /**
     * dynamic credit
     */
    @Defined(key = "creditType", value = "��ָ̬��")
    int CREDIT_TYPE_DYNAMIC = 1;

    /**
     * percent item
     */
    @Defined(key = "creditItemType", value = "�ٷ���")
    int CREDIT_ITEM_TYPE_PERCENT = 0;

    /**
     * real item
     */
    @Defined(key = "creditItemType", value = "ʵ��ֵ")
    int CREDIT_ITEM_TYPE_REAL = 1;

    /**
     * obverse face
     */
    @Defined(key = "creditItemFace", value = "����ָ��")
    int CREDIT_ITEM_FACE_OBVERSE = 0;

    /**
     * negative face
     */
    @Defined(key = "creditItemFace", value = "����ָ��")
    int CREDIT_ITEM_FACE_NEGATIVE = 1;

    /**
     * sub yes
     */
    @Defined(key = "creditItemSub", value = "������ֵ")
    int CREDIT_ITEM_SUB_YES = 0;

    /**
     * sub no
     */
    @Defined(key = "creditItemSub", value = "ֱ����ֵ")
    int CREDIT_ITEM_SUB_NO = 1;

    /**
     * ֱ������(�ܾ�������)
     */
    String SET_DRECT = "10000000000000009999";

    /**
     * δ���ڼӷ�
     */
    String OUT_COMMON_ITEM = "80000000000000000042";

    /**
     * δ���ڼӷֵĸ���
     */
    String OUT_COMMON_ITEM_PARENT = "90000000000000000005";

    /**
     * ���������󵥱Ƚ��׶�
     */
    String OUT_MAX_BUSINESS = "80000000000000000051";

    /**
     * ���������󵥱Ƚ��׶�-����
     */
    String OUT_MAX_BUSINESS_PARENT = "90000000000000000006";

    /**
     * ������Ƚ����ܶ�
     */
    String OUT_TOTAL_BUSINESS = "80000000000000000061";

    /**
     * ������Ƚ����ܶ�-����
     */
    String OUT_TOTAL_BUSINESS_PARENT = "90000000000000000007";

    /**
     * ���ڼ��ֵ���
     */
    String OUT_DELAY_ITEM = "80000000000000000041";

    /**
     * ���ڼ��ֵ��εĸ���
     */
    String OUT_DELAY_ITEM_PARENT = OUT_COMMON_ITEM_PARENT;

    /**
     * δ����
     */
    int PAY_NOT = 0;

    /**
     * ����
     */
    int PAY_YES = 1;

    /**
     * ���۵�δ����
     */
    int CREDIT_OUT_INIT = 0;

    /**
     * ���۵��Ѿ�����
     */
    int CREDIT_OUT_END = 1;
}
