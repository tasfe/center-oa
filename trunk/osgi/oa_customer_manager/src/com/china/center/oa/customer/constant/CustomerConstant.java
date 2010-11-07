/**
 * File Name: CustomerConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * CustomerConstant
 * 
 * @author ZHUZHU
 * @version 2008-11-9
 * @see CustomerConstant
 * @since 1.0
 */
public interface CustomerConstant
{
    /**
     * ok
     */
    @Defined(key = "customerStatus", value = "正常")
    int STATUS_OK = 0;

    /**
     * apply
     */
    @Defined(key = "customerStatus", value = "申请")
    int STATUS_APPLY = 1;

    /**
     * 审批后等待分配code
     */
    @Defined(key = "customerStatus", value = "等待分配编码")
    int STATUS_WAIT_CODE = 3;

    /**
     * reject
     */
    @Defined(key = "customerStatus", value = "驳回")
    int STATUS_REJECT = 2;

    // customerOpr
    @Defined(key = "customerOpr", value = "增加")
    int OPR_ADD = 0;

    @Defined(key = "customerOpr", value = "更新")
    int OPR_UPDATE = 1;

    @Defined(key = "customerOpr", value = "删除")
    int OPR_DEL = 2;

    /**
     * UPATE_CREDIT
     */
    @Defined(key = "customerOpr", value = "更新客户信用")
    int OPR_UPATE_CREDIT = 3;

    /**
     * 更新利润分配比例
     */
    @Defined(key = "customerOpr", value = "更新利润分配比例")
    int OPR_UPATE_ASSIGNPER = 4;

    /**
     * 空闲的客户
     */
    @Defined(key = "realCustomerStatus", value = "空闲")
    int REAL_STATUS_IDLE = 0;

    /**
     * 被使用的客户
     */
    @Defined(key = "realCustomerStatus", value = "使用")
    int REAL_STATUS_USED = 1;

    /**
     * 申请中的客户
     */
    @Defined(key = "realCustomerStatus", value = "申请中")
    int REAL_STATUS_APPLY = 2;

    /**
     * 无成交记录
     */
    @Defined(key = "blog", value = "无历史成交")
    int BLOG_NO = 0;

    /**
     * 有成交记录
     */
    @Defined(key = "blog", value = "有历史成交")
    int BLOG_YES = 1;

    /**
     * 无名片
     */
    @Defined(key = "card", value = "无名片")
    int CARD_NO = 0;

    /**
     * 有名片
     */
    @Defined(key = "card", value = "有名片")
    int CARD_YES = 1;

    /**
     * 回收全部
     */
    int RECLAIMSTAFFER_ALL = 0;

    /**
     * 回收拓展
     */
    int RECLAIMSTAFFER_EXPEND = 1;

    /**
     * 回收终端
     */
    int RECLAIMSTAFFER_TER = 2;

    /**
     * 终端
     */
    int SELLTYPE_TER = 0;

    /**
     * 拓展
     */
    int SELLTYPE_EXPEND = 1;

    /**
     * 未核对
     */
    @Defined(key = "checkStatus", value = "未核对")
    int HIS_CHECK_NO = 0;

    /**
     * 核对
     */
    @Defined(key = "checkStatus", value = "核对")
    int HIS_CHECK_YES = 1;

    /**
     * 是新客户
     */
    int HASNEW_YES = 0;

    /**
     * 不是新客户
     */
    int HASNEW_NO = 1;

    /**
     * 新客户
     */
    @Defined(key = "c_newType", value = "新客户")
    int NEWTYPE_NEW = 0;

    /**
     * 老客户
     */
    @Defined(key = "c_newType", value = "老客户")
    int NEWTYPE_OLD = 1;

    /**
     * 默认级别
     */
    String CREDITLEVELID_DEFAULT = "90000000000000000001";

    /**
     * 默认信用杠杆倍数
     */
    int DEFAULT_LEVER = 1;
}
