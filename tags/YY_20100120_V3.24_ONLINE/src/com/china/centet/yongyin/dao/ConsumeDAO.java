/*
 * File Name: ConsumeDAO.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-12-15
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.dao;


import com.china.center.jdbc.inter.impl.BaseDAO2;
import com.china.centet.yongyin.bean.ConsumeBean;
import com.china.centet.yongyin.vo.ConsumeBeanVO;


/**
 * ��Ա���ѵ�dao
 * 
 * @author zhuzhu
 * @version 2007-12-15
 * @see
 * @since
 */
public class ConsumeDAO extends BaseDAO2<ConsumeBean, ConsumeBeanVO>
{
    /**
     * ɾ����Ա�����Ѽ�¼
     * 
     * @param memberId
     * @return
     */
    public boolean delConsumesByMemberId(String memberId)
    {
        return this.jdbcOperation.delete(memberId, "memberId", this.claz) > 0;
    }
}
