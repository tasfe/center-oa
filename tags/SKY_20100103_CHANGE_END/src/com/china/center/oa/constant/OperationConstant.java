/**
 * File Name: ModuleConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-12-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.constant;


import com.china.center.annotation.Defined;


/**
 * ModuleConstant
 * 
 * @author zhuzhu
 * @version 2008-12-10
 * @see OperationConstant
 * @since 1.0
 */
public interface OperationConstant
{
    /**
     * �ύ
     */
    String OPERATION_SUBMIT = "�ύ";

    /**
     * ͨ��
     */
    String OPERATION_PASS = "ͨ��";

    /**
     * ����
     */
    String OPERATION_REJECT = "����";

    /**
     * ����
     */
    String OPERATION_UPDATE = "����";

    /**
     * ����
     */
    String OPERATION_END = "����";

    /**
     * �ҿ�
     */
    @Defined(key = "changeLogOpr", value = "�ҿ�")
    int OPERATION_CHANGELOG_ADD = 0;

    /**
     * ж��
     */
    @Defined(key = "changeLogOpr", value = "ж��")
    int OPERATION_CHANGELOG_DEL = 1;
}
