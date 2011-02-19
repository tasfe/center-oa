/**
 * File Name: StockPayAoolyBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.bean;


import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.finance.constant.StockPayApplyConstant;
import com.china.center.oa.product.bean.ProviderBean;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * 采购付款申请单
 * 
 * @author ZHUZHU
 * @version 2011-2-17
 * @see StockPayApplyBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_STOCKPAYAPPLY")
public class StockPayApplyBean implements Serializable
{
    @Id
    private String id = "";

    private int status = StockPayApplyConstant.APPLY_STATUS_INIT;

    @Join(tagClass = InvoiceBean.class, type = JoinType.LEFT)
    private String invoiceId = "";

    @Join(tagClass = DutyBean.class)
    private String dutyId = "";

    @FK
    @Join(tagClass = ProviderBean.class)
    private String provideId = "";

    private String stockId = "";

    @Join(tagClass = StafferBean.class)
    private String stafferId = "";

    @FK(index = AnoConstant.FK_FIRST)
    private String stockItemId = "";

    /**
     * 付款单ID
     */
    private String inBillId = "";

    private double moneys = 0.0d;

    private String payDate = "";

    private String locationId = "";

    private String logTime = "";

    private String description = "";

    /**
     * default constructor
     */
    public StockPayApplyBean()
    {
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * @return the invoiceId
     */
    public String getInvoiceId()
    {
        return invoiceId;
    }

    /**
     * @param invoiceId
     *            the invoiceId to set
     */
    public void setInvoiceId(String invoiceId)
    {
        this.invoiceId = invoiceId;
    }

    /**
     * @return the dutyId
     */
    public String getDutyId()
    {
        return dutyId;
    }

    /**
     * @param dutyId
     *            the dutyId to set
     */
    public void setDutyId(String dutyId)
    {
        this.dutyId = dutyId;
    }

    /**
     * @return the provideId
     */
    public String getProvideId()
    {
        return provideId;
    }

    /**
     * @param provideId
     *            the provideId to set
     */
    public void setProvideId(String provideId)
    {
        this.provideId = provideId;
    }

    /**
     * @return the stockId
     */
    public String getStockId()
    {
        return stockId;
    }

    /**
     * @param stockId
     *            the stockId to set
     */
    public void setStockId(String stockId)
    {
        this.stockId = stockId;
    }

    /**
     * @return the stockItemId
     */
    public String getStockItemId()
    {
        return stockItemId;
    }

    /**
     * @param stockItemId
     *            the stockItemId to set
     */
    public void setStockItemId(String stockItemId)
    {
        this.stockItemId = stockItemId;
    }

    /**
     * @return the inBillId
     */
    public String getInBillId()
    {
        return inBillId;
    }

    /**
     * @param inBillId
     *            the inBillId to set
     */
    public void setInBillId(String inBillId)
    {
        this.inBillId = inBillId;
    }

    /**
     * @return the moneys
     */
    public double getMoneys()
    {
        return moneys;
    }

    /**
     * @param moneys
     *            the moneys to set
     */
    public void setMoneys(double moneys)
    {
        this.moneys = moneys;
    }

    /**
     * @return the payDate
     */
    public String getPayDate()
    {
        return payDate;
    }

    /**
     * @param payDate
     *            the payDate to set
     */
    public void setPayDate(String payDate)
    {
        this.payDate = payDate;
    }

    /**
     * @return the locationId
     */
    public String getLocationId()
    {
        return locationId;
    }

    /**
     * @param locationId
     *            the locationId to set
     */
    public void setLocationId(String locationId)
    {
        this.locationId = locationId;
    }

    /**
     * @return the logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @param logTime
     *            the logTime to set
     */
    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the stafferId
     */
    public String getStafferId()
    {
        return stafferId;
    }

    /**
     * @param stafferId
     *            the stafferId to set
     */
    public void setStafferId(String stafferId)
    {
        this.stafferId = stafferId;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("StockPayApplyBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("invoiceId = ")
            .append(this.invoiceId)
            .append(TAB)
            .append("dutyId = ")
            .append(this.dutyId)
            .append(TAB)
            .append("provideId = ")
            .append(this.provideId)
            .append(TAB)
            .append("stockId = ")
            .append(this.stockId)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("stockItemId = ")
            .append(this.stockItemId)
            .append(TAB)
            .append("inBillId = ")
            .append(this.inBillId)
            .append(TAB)
            .append("moneys = ")
            .append(this.moneys)
            .append(TAB)
            .append("payDate = ")
            .append(this.payDate)
            .append(TAB)
            .append("locationId = ")
            .append(this.locationId)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
