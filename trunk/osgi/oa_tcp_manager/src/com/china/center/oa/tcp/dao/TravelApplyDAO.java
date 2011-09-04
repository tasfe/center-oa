/**
 * File Name: TravelApplyDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.tcp.bean.TravelApplyBean;
import com.china.center.oa.tcp.vo.TravelApplyVO;


/**
 * TravelApplyDAO
 * 
 * @author ZHUZHU
 * @version 2011-7-10
 * @see TravelApplyDAO
 * @since 3.0
 */
public interface TravelApplyDAO extends DAO<TravelApplyBean, TravelApplyVO>
{
    int updateStatus(String id, int status);
}
