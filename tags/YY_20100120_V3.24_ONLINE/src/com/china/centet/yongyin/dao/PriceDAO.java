/*
 * File Name: BankDAO.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-12-15
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.dao;


import com.china.center.jdbc.inter.impl.BaseDAO2;
import com.china.centet.yongyin.bean.PriceBean;
import com.china.centet.yongyin.vo.PriceBeanVO;


/**
 * ���е�dao
 * 
 * @author zhuzhu
 * @version 2007-12-15
 * @see
 * @since
 */
public class PriceDAO extends BaseDAO2<PriceBean, PriceBeanVO>
{
    /**
     * default constructor
     */
    public PriceDAO()
    {}

    public int countByPriceWebId(String priceWebId)
    {
        return this.countBycondition("where priceWebId = ?", priceWebId);
    }
}
