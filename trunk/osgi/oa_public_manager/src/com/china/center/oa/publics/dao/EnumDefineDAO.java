/**
 * File Name: EnumDefineDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-16<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.publics.bean.EnumDefineBean;


/**
 * EnumDefineDAO
 * 
 * @author ZHUZHU
 * @version 2010-8-16
 * @see EnumDefineDAO
 * @since 1.0
 */
public interface EnumDefineDAO extends DAO<EnumDefineBean, EnumDefineBean>
{
    int countRef(String tableName, String column, String value);
}
