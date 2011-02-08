/**
 * File Name: BankListenerTaxImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-31<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.listener.impl;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.expression.Expression;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.listener.BankListener;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tax.dao.TaxDAO;


/**
 * BankListenerTaxImpl
 * 
 * @author ZHUZHU
 * @version 2011-1-31
 * @see BankListenerTaxImpl
 * @since 1.0
 */
public class BankListenerTaxImpl implements BankListener
{
    private TaxDAO taxDAO = null;

    private CommonDAO commonDAO = null;

    /**
     * default constructor
     */
    public BankListenerTaxImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.finance.listener.BankListener#onAddBank(com.china.center.oa.finance.bean.BankBean)
     */
    public void onAddBank(User user, BankBean bank)
        throws MYException
    {
        // 帐户自动生成科目(资产类--借--银行)
        TaxBean bean = new TaxBean();

        bean.setName(bank.getName());

        bean.setCode(bank.getCode());

        bean.setPtype("90000000000000000001");

        bean.setDepartment(bank.getDepartment());

        bean.setForward(TaxConstanst.TAX_FORWARD_OUT);

        bean.setRefId(bank.getId());

        bean.setStaffer(bank.getStaffer());

        bean.setType(TaxConstanst.TAX_TYPE_BANK);

        bean.setId(commonDAO.getSquenceString20());

        Expression exp = new Expression(bean, this);

        exp.check("#name || #code &unique @taxDAO", "名称或者编码已经存在");

        taxDAO.saveEntityBean(bean);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.finance.listener.BankListener#onDeleteBank(com.china.center.oa.finance.bean.BankBean)
     */
    public void onDeleteBank(User user, BankBean bank)
        throws MYException
    {
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("TaxBean.refId", "=", bank.getId());

        int count = taxDAO.countByCondition(con.toString());

        if (count > 0)
        {
            throw new MYException("帐户被科目使用,不能删除");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    @Override
    public String getListenerType()
    {
        return "BankListener.TaxImpl";
    }

    /**
     * @return the taxDAO
     */
    public TaxDAO getTaxDAO()
    {
        return taxDAO;
    }

    /**
     * @param taxDAO
     *            the taxDAO to set
     */
    public void setTaxDAO(TaxDAO taxDAO)
    {
        this.taxDAO = taxDAO;
    }

    /**
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }

}