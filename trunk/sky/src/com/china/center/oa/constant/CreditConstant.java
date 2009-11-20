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
}
