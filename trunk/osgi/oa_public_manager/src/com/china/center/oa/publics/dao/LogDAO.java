/**
 * File Name: LogDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.publics.bean.LogBean;
import com.china.center.oa.publics.vo.LogVO;


/**
 * LogDAO
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see LogDAO
 * @since 1.0
 */
public interface LogDAO extends DAO<LogBean, LogVO>
{
    LogBean findLogBeanByFKAndPosid(String fk, String posid);
}
