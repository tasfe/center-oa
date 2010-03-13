/**
 * 
 */
package com.china.center.oa.constant;


import com.china.center.annotation.Defined;


/**
 * @author ZHUZHU
 */
public interface ProductConstant
{
    /**
     * ����
     */
    int STATUS_COMMON = 0;

    /**
     * ����
     */
    int STATUS_LOCK = 1;

    /**
     * ע��
     */
    int STATUS_LOGOUT = 2;

    /**
     * ����
     */
    int TEMP_SELF = 0;

    /**
     * ������
     */
    int TEMP_PUBLIC = 1;

    /**
     * ����̬
     */
    @Defined(key = "productStatStatus", value = "����̬")
    int STAT_STATUS_COMMON = 0;

    /**
     * Ԥ��̬
     */
    @Defined(key = "productStatStatus", value = "Ԥ��̬")
    int STAT_STATUS_ALERT = 1;

    /**
     * ����
     */
    @Defined(key = "productOrderStatus", value = "Ԥ��")
    int ORDER_STATUS_COMMON = 0;

    /**
     * ����
     */
    @Defined(key = "productOrderStatus", value = "����")
    int ORDER_STATUS_END = 1;

    /**
     * ͳ������
     */
    int STAT_DAYS = 15;
}
