/**
 *
 */
package com.china.centet.yongyin.facade;


import com.china.centet.yongyin.bean.User;


/**
 * @author Administrator
 */
public interface CommonFacede
{
    /**
     * ��¼�ӿ�
     * 
     * @param name
     * @param password
     * @return
     */
    User login(String name, String password);
}
