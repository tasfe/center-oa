/**
 * File Name: FinanceBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.bean;


import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * 凭证项
 * 
 * @author ZHUZHU
 * @version 2011-2-6
 * @see FinanceItemBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_FINANCEITEM")
public class FinanceItemBean implements Serializable
{
    @Id
    private String id = "";

    @FK
    private String pid = "";

    private String pareId = "";

    private String name = "";

    private int type = 0;

    private int forward = 0;

    /**
     * 主关联单据
     */
    private String refId = "";

    private String refOut = "";

    private String refBill = "";

    private String refStock = "";

    /**
     * 凭证时间
     */
    private String financeDate = "";

    @FK(index = AnoConstant.FK_FIRST)
    @Join(tagClass = TaxBean.class)
    private String taxId = "";

    private String taxId0 = "";

    private String taxId1 = "";

    private String taxId2 = "";

    private String taxId3 = "";

    private String taxId4 = "";

    private String taxId5 = "";

    private String taxId6 = "";

    private String taxId7 = "";

    private String taxId8 = "";

    private String dutyId = PublicConstant.DEFAULR_DUTY_ID;

    /**
     * 准确到微分,就是小数点后四位
     */
    private long inmoney = 0;

    /**
     * 准确到微分,就是小数点后四位
     */
    private long outmoney = 0;

    private String description = "";

    private String logTime = "";

    private String unitId = "";

    /**
     * 0:客户 1:供应商 2:纳税实体
     */
    private int unitType = 0;

    private String departmentId = "";

    private String stafferId = "";

    /**
     * default constructor
     */
    public FinanceItemBean()
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
     * @return the pid
     */
    public String getPid()
    {
        return pid;
    }

    /**
     * @param pid
     *            the pid to set
     */
    public void setPid(String pid)
    {
        this.pid = pid;
    }

    /**
     * @return the pareId
     */
    public String getPareId()
    {
        return pareId;
    }

    /**
     * @param pareId
     *            the pareId to set
     */
    public void setPareId(String pareId)
    {
        this.pareId = pareId;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the forward
     */
    public int getForward()
    {
        return forward;
    }

    /**
     * @param forward
     *            the forward to set
     */
    public void setForward(int forward)
    {
        this.forward = forward;
    }

    /**
     * @return the taxId
     */
    public String getTaxId()
    {
        return taxId;
    }

    /**
     * @param taxId
     *            the taxId to set
     */
    public void setTaxId(String taxId)
    {
        this.taxId = taxId;
    }

    /**
     * @return the inmoney
     */
    public long getInmoney()
    {
        return inmoney;
    }

    /**
     * @param inmoney
     *            the inmoney to set
     */
    public void setInmoney(long inmoney)
    {
        this.inmoney = inmoney;
    }

    /**
     * @return the outmoney
     */
    public long getOutmoney()
    {
        return outmoney;
    }

    /**
     * @param outmoney
     *            the outmoney to set
     */
    public void setOutmoney(long outmoney)
    {
        this.outmoney = outmoney;
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
     * @return the unitId
     */
    public String getUnitId()
    {
        return unitId;
    }

    /**
     * @param unitId
     *            the unitId to set
     */
    public void setUnitId(String unitId)
    {
        this.unitId = unitId;
    }

    /**
     * @return the unitType
     */
    public int getUnitType()
    {
        return unitType;
    }

    /**
     * @param unitType
     *            the unitType to set
     */
    public void setUnitType(int unitType)
    {
        this.unitType = unitType;
    }

    /**
     * @return the departmentId
     */
    public String getDepartmentId()
    {
        return departmentId;
    }

    /**
     * @param departmentId
     *            the departmentId to set
     */
    public void setDepartmentId(String departmentId)
    {
        this.departmentId = departmentId;
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
     * @return the taxId0
     */
    public String getTaxId0()
    {
        return taxId0;
    }

    /**
     * @param taxId0
     *            the taxId0 to set
     */
    public void setTaxId0(String taxId0)
    {
        this.taxId0 = taxId0;
    }

    /**
     * @return the taxId1
     */
    public String getTaxId1()
    {
        return taxId1;
    }

    /**
     * @param taxId1
     *            the taxId1 to set
     */
    public void setTaxId1(String taxId1)
    {
        this.taxId1 = taxId1;
    }

    /**
     * @return the taxId2
     */
    public String getTaxId2()
    {
        return taxId2;
    }

    /**
     * @param taxId2
     *            the taxId2 to set
     */
    public void setTaxId2(String taxId2)
    {
        this.taxId2 = taxId2;
    }

    /**
     * @return the taxId3
     */
    public String getTaxId3()
    {
        return taxId3;
    }

    /**
     * @param taxId3
     *            the taxId3 to set
     */
    public void setTaxId3(String taxId3)
    {
        this.taxId3 = taxId3;
    }

    /**
     * @return the taxId4
     */
    public String getTaxId4()
    {
        return taxId4;
    }

    /**
     * @param taxId4
     *            the taxId4 to set
     */
    public void setTaxId4(String taxId4)
    {
        this.taxId4 = taxId4;
    }

    /**
     * @return the taxId5
     */
    public String getTaxId5()
    {
        return taxId5;
    }

    /**
     * @param taxId5
     *            the taxId5 to set
     */
    public void setTaxId5(String taxId5)
    {
        this.taxId5 = taxId5;
    }

    /**
     * @return the taxId6
     */
    public String getTaxId6()
    {
        return taxId6;
    }

    /**
     * @param taxId6
     *            the taxId6 to set
     */
    public void setTaxId6(String taxId6)
    {
        this.taxId6 = taxId6;
    }

    /**
     * @return the taxId7
     */
    public String getTaxId7()
    {
        return taxId7;
    }

    /**
     * @param taxId7
     *            the taxId7 to set
     */
    public void setTaxId7(String taxId7)
    {
        this.taxId7 = taxId7;
    }

    /**
     * @return the taxId8
     */
    public String getTaxId8()
    {
        return taxId8;
    }

    /**
     * @param taxId8
     *            the taxId8 to set
     */
    public void setTaxId8(String taxId8)
    {
        this.taxId8 = taxId8;
    }

    /**
     * @return the refId
     */
    public String getRefId()
    {
        return refId;
    }

    /**
     * @param refId
     *            the refId to set
     */
    public void setRefId(String refId)
    {
        this.refId = refId;
    }

    /**
     * @return the refOut
     */
    public String getRefOut()
    {
        return refOut;
    }

    /**
     * @param refOut
     *            the refOut to set
     */
    public void setRefOut(String refOut)
    {
        this.refOut = refOut;
    }

    /**
     * @return the refBill
     */
    public String getRefBill()
    {
        return refBill;
    }

    /**
     * @param refBill
     *            the refBill to set
     */
    public void setRefBill(String refBill)
    {
        this.refBill = refBill;
    }

    /**
     * @return the refStock
     */
    public String getRefStock()
    {
        return refStock;
    }

    /**
     * @param refStock
     *            the refStock to set
     */
    public void setRefStock(String refStock)
    {
        this.refStock = refStock;
    }

    /**
     * @return the financeDate
     */
    public String getFinanceDate()
    {
        return financeDate;
    }

    /**
     * @param financeDate
     *            the financeDate to set
     */
    public void setFinanceDate(String financeDate)
    {
        this.financeDate = financeDate;
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
            .append("FinanceItemBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("pid = ")
            .append(this.pid)
            .append(TAB)
            .append("pareId = ")
            .append(this.pareId)
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("forward = ")
            .append(this.forward)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append("refOut = ")
            .append(this.refOut)
            .append(TAB)
            .append("refBill = ")
            .append(this.refBill)
            .append(TAB)
            .append("refStock = ")
            .append(this.refStock)
            .append(TAB)
            .append("financeDate = ")
            .append(this.financeDate)
            .append(TAB)
            .append("taxId = ")
            .append(this.taxId)
            .append(TAB)
            .append("taxId0 = ")
            .append(this.taxId0)
            .append(TAB)
            .append("taxId1 = ")
            .append(this.taxId1)
            .append(TAB)
            .append("taxId2 = ")
            .append(this.taxId2)
            .append(TAB)
            .append("taxId3 = ")
            .append(this.taxId3)
            .append(TAB)
            .append("taxId4 = ")
            .append(this.taxId4)
            .append(TAB)
            .append("taxId5 = ")
            .append(this.taxId5)
            .append(TAB)
            .append("taxId6 = ")
            .append(this.taxId6)
            .append(TAB)
            .append("taxId7 = ")
            .append(this.taxId7)
            .append(TAB)
            .append("taxId8 = ")
            .append(this.taxId8)
            .append(TAB)
            .append("dutyId = ")
            .append(this.dutyId)
            .append(TAB)
            .append("inmoney = ")
            .append(this.inmoney)
            .append(TAB)
            .append("outmoney = ")
            .append(this.outmoney)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("unitId = ")
            .append(this.unitId)
            .append(TAB)
            .append("unitType = ")
            .append(this.unitType)
            .append(TAB)
            .append("departmentId = ")
            .append(this.departmentId)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
