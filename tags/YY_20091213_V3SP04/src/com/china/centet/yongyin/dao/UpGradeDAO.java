/*
 * File Name: UpGradeDAO.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-8-16
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.dao;


import com.china.centet.yongyin.bean.Version;


/**
 * ������DAO
 * 
 * @author zhuzhu
 * @version 2007-8-16
 * @see
 * @since
 */
public interface UpGradeDAO
{
    /**
     * ������ݿ�����İ汾
     */
    Version getVersion();

    /**
     * ���°汾
     * 
     * @param version
     * @return
     */
    boolean updateVersion(Version version);

    void initMenuItem();
}
