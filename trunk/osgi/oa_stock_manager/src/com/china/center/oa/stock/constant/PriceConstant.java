/**
 *
 */
package com.china.center.oa.stock.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * 询价的常量定义
 * 
 * @author Administrator
 */
public interface PriceConstant
{
    /**
     * 正常
     */
    int PRICE_COMMON = 0;

    /**
     * 拒绝
     */
    int PRICE_REJECT = 1;

    /**
     * 询价初始化
     */
    @Defined(key = "priceAskStatus", value = "开始")
    int PRICE_ASK_STATUS_INIT = 0;

    /**
     * 询价中(询价没有结束)
     */
    @Defined(key = "priceAskStatus", value = "询价中")
    int PRICE_ASK_STATUS_PROCESSING = 1;

    /**
     * 询价异常
     */
    @Defined(key = "priceAskStatus", value = "驳回")
    int PRICE_ASK_STATUS_EXCEPTION = 2;

    /**
     * 询价结束
     */
    @Defined(key = "priceAskStatus", value = "结束")
    int PRICE_ASK_STATUS_END = 3;

    /**
     * 询价类型-内网
     */
    @Defined(key = "priceAskType", value = "内网询价")
    int PRICE_ASK_TYPE_INNER = 0;

    /**
     * 询价类型-外网
     */
    @Defined(key = "priceAskType", value = "外网询价")
    int PRICE_ASK_TYPE_NET = 1;

    /**
     * 询价类型-(内外网询价)
     */
    @Defined(key = "priceAskType", value = "内外网询价")
    int PRICE_ASK_TYPE_BOTH = 2;

    /**
     * 普通存储
     */
    int PRICE_ASK_SAVE_TYPE_COMMON = 0;

    /**
     * 虚拟存储
     */
    int PRICE_ASK_SAVE_TYPE_ABS = 1;

    /**
     * 没有逾期
     */
    int OVERTIME_NO = 0;

    /**
     * 逾期
     */
    int OVERTIME_YES = 1;

    /**
     * 数量是否满足-是
     */
    int HASAMOUNT_OK = 0;

    /**
     * 数量是否满足-否
     */
    int HASAMOUNT_NO = 1;

    /**
     * 询价紧急程度-一般(2小时)
     */
    @Defined(key = "priceAskInstancy", value = "一般")
    int PRICE_INSTANCY_COMMON = 0;

    /**
     * 询价紧急程度-紧急(1小时)
     */
    @Defined(key = "priceAskInstancy", value = "紧急")
    int PRICE_INSTANCY_INSTANCY = 1;

    /**
     * 询价紧急程度-非常紧急(30分钟)
     */
    @Defined(key = "priceAskInstancy", value = "非常紧急")
    int PRICE_INSTANCY_VERYINSTANCY = 2;

    /**
     * 询价紧急程度-定点11点
     */
    int PRICE_INSTANCY_NETWORK_1130 = 3;

    /**
     * 询价紧急程度-定点14点
     */
    int PRICE_INSTANCY_NETWORK_1530 = 4;

    /**
     * 询价紧急程度-定点16点
     */
    int PRICE_INSTANCY_NETWORK_16 = 5;

}
