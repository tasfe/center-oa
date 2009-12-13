/**
 * File Name: MailConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.constant;


import com.china.center.annotation.Defined;


/**
 * MailConstant
 * 
 * @author zhuzhu
 * @version 2009-4-12
 * @see MailConstant
 * @since 1.0
 */
public interface MailConstant
{
    /**
     * �ռ���
     */
    int MAX_LENGTH = 500;
    
    /**
     * δ�Ķ�
     */
    @Defined(key = "mailStatus", value = "δ�Ķ�")
    int STATUS_INIT = 0;

    /**
     * ���Ķ�
     */
    @Defined(key = "mailStatus", value = "���Ķ�")
    int STATUS_READ = 1;

    /**
     * δ�ظ�
     */
    @Defined(key = "mailFeeback", value = "δ�ظ�")
    int FEEBACK_NO = 0;

    /**
     * �ѻظ�
     */
    @Defined(key = "mailFeeback", value = "�ѻظ�")
    int FEEBACK_YES = 1;

    /**
     * �޸���
     */
    @Defined(key = "mailAttachment", value = "�޸���")
    int ATTACHMENT_NO = 0;

    /**
     * �и���
     */
    @Defined(key = "mailAttachment", value = "�и���")
    int ATTACHMENT_YES = 1;
}
