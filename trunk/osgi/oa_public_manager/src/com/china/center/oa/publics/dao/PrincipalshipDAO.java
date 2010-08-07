/**
 * File Name: PrincipalshipDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.publics.bean.PrincipalshipBean;


/**
 * PrincipalshipDAO
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see PrincipalshipDAO
 * @since 1.0
 */
public interface PrincipalshipDAO extends DAO<PrincipalshipBean, PrincipalshipBean>
{
    List<PrincipalshipBean> querySubPrincipalship(String id);
}
