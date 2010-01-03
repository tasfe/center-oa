/*
 * File Name: JCheck.java CopyRight: Copyright by www.center.china Description:
 * Creater: zhuAchen CreateTime: 2007-12-13 Grant: open source to everybody
 */
package com.china.center.annotation;

/**
 * �Զ�У��
 * 
 * @author zhuzhu
 * @version 2007-12-13
 * @see
 * @since
 */
public interface JCheck
{
    /**
     * �������� 
     */
    String ONLY_NUMBER = "isNumber;";
    
    /**
     * ����Ϊ��
     */
    String NOT_NONE = "notNone;";
    
    /**
     * �����ַ�
     */
    String ONLY_COMMONCHAR = "isCommonChar;";
    
    /**
     * ��ĸ/����
     */
    String ONLY_NUMBER_OR_LETTER = "isNumberOrLetter;";
    
    /**
     * ��������ĸ
     */
    String ONLY_LETTER = "isLetter;";
    
    /**
     * ������
     */
    String ONLY_FLOAT = "isFloat;";
}
