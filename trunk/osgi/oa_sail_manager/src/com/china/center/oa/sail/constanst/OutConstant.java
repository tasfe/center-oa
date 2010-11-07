/**
 * File Name: OutConstanst.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-10-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.constanst;

/**
 * OutConstanst
 * 
 * @author ZHUZHU
 * @version 2008-10-4
 * @see
 * @since
 */
public interface OutConstant
{
    /**
     * 自主调入(从其他区域调出，但是单据属于其他区域)
     */
    int INBILL_SELF_IN = 8;

    /**
     * 销售单
     */
    int OUT_TYPE_OUTBILL = 0;

    /**
     * 入库单
     */
    int OUT_TYPE_INBILL = 1;

    /**
     * 合成单
     */
    int OUT_TYPE_COMPOSE = 2;

    /**
     * 产品合分
     */
    int OUTTYPES_COMPOSE = 7;

    /**
     * 仓区移动
     */
    int OUT_TYPE_MOVE = 8;

    /**
     * 自动修正
     */
    int OUT_TYPE_MODIFY = 9;

    /**
     * 信用正常
     */
    int OUT_CREDIT_COMMON = 0;

    /**
     * 信用超支
     */
    int OUT_CREDIT_OVER = 1;

    /**
     * 价格为0
     */
    int OUT_CREDIT_MIN = 2;

    /**
     * 货到收款
     */
    int OUT_SAIL_TYPE_COMMON = 0;

    /**
     * 款到发货
     */
    int OUT_SAIL_TYPE_MONEY = 1;

    /**
     * 使用业务员信用额度和客户信用额度(客户信用额度优先)
     */
    int OUT_SAIL_TYPE_CREDIT_AND_CUR = 2;

    /**
     * 黑名单客户
     */
    String BLACK_LEVEL = "90000000000000000000";

    /**
     * 提交
     */
    int STATUS_SUBMIT = 1;

    /**
     * 驳回
     */
    int STATUS_REJECT = 2;

    /**
     * ADMIN通过(一般此通过即是销售单已经OK status in (3, 4))
     */
    int STATUS_PASS = 3;

    /**
     * 会计通过
     */
    int STATUS_SEC_PASS = 4;

    /**
     * 总经理通过
     */
    int STATUS_MANAGER_PASS = 6;

    /**
     * 物流管理员通过
     */
    int STATUS_FLOW_PASS = 7;
}
