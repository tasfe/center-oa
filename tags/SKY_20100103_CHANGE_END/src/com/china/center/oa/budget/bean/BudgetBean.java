/**
 * File Name: BudgetBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-12-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.bean;


import java.io.Serializable;
import java.util.List;

import com.china.center.annotation.Entity;
import com.china.center.annotation.FK;
import com.china.center.annotation.Html;
import com.china.center.annotation.Id;
import com.china.center.annotation.Ignore;
import com.china.center.annotation.JCheck;
import com.china.center.annotation.Join;
import com.china.center.annotation.Table;
import com.china.center.annotation.enums.Element;
import com.china.center.oa.constant.BudgetConstant;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * BudgetBean
 * 
 * @author zhuzhu
 * @version 2008-12-2
 * @see BudgetBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_BUDGET")
public class BudgetBean implements Serializable
{
    @Id
    private String id = "";

    @Html(title = "Ԥ�����", must = true, maxLength = 80)
    private String name = "";

    @FK
    @Html(title = "����Ԥ��", must = true, name = "parentName")
    @Join(tagClass = BudgetBean.class, alias = "BudgetBean2")
    private String parentId = "0";

    @Join(tagClass = StafferBean.class)
    private String stafferId = "";

    @Join(tagClass = LocationBean.class)
    private String locationId = "";

    private String logTime = "";

    @Html(title = "Ԥ�㲿��", must = true)
    private String budgetDepartment = "";

    @Html(title = "Ԥ�����", must = true, type = Element.SELECT)
    private String year = "";

    @Html(title = "��ʼʱ��", must = true, type = Element.DATE)
    private String beginDate = "";

    @Html(title = "����ʱ��", must = true, type = Element.DATE)
    private String endDate = "";

    private int status = BudgetConstant.BUDGET_STATUS_INIT;

    private int carryStatus = BudgetConstant.BUDGET_CARRY_INIT;

    @Html(title = "Ԥ������", must = true, type = Element.SELECT)
    private int type = BudgetConstant.BUDGET_TYPE_COMPANY;

    @Html(title = "Ԥ�����", must = true, type = Element.SELECT)
    private int level = BudgetConstant.BUDGET_LEVEL_YEAR;

    private double total = 0.0d;

    @Html(title = "�����ܶ�", must = true, maxLength = 20, oncheck = JCheck.ONLY_FLOAT)
    private double sail = 0.0d;

    @Html(title = "ë����", must = true, maxLength = 20, oncheck = JCheck.ONLY_FLOAT)
    private double orgProfit = 0.0d;

    @Html(title = "������", must = true, maxLength = 20, oncheck = JCheck.ONLY_FLOAT)
    private double realProfit = 0.0d;

    @Html(title = "����޶�", must = true, maxLength = 20, oncheck = JCheck.ONLY_FLOAT)
    private double outSave = 0.0d;

    @Html(title = "�����", must = true, maxLength = 20, oncheck = JCheck.ONLY_FLOAT)
    private double outMoney = 0.0d;

    @Html(title = "��С�տ�", must = true, maxLength = 20, oncheck = JCheck.ONLY_FLOAT)
    private double inMoney = 0.0d;

    private double realMonery = 0.0d;

    @Html(title = "����", type = Element.TEXTAREA, maxLength = 200)
    private String description = "";

    @Ignore
    private List<BudgetItemBean> items = null;

    public BudgetBean()
    {}

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
     * @return the beginDate
     */
    public String getBeginDate()
    {
        return beginDate;
    }

    /**
     * @param beginDate
     *            the beginDate to set
     */
    public void setBeginDate(String beginDate)
    {
        this.beginDate = beginDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate()
    {
        return endDate;
    }

    /**
     * @param endDate
     *            the endDate to set
     */
    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
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
     * @return the carryStatus
     */
    public int getCarryStatus()
    {
        return carryStatus;
    }

    /**
     * @param carryStatus
     *            the carryStatus to set
     */
    public void setCarryStatus(int carryStatus)
    {
        this.carryStatus = carryStatus;
    }

    /**
     * @return the total
     */
    public double getTotal()
    {
        return total;
    }

    /**
     * @param total
     *            the total to set
     */
    public void setTotal(double total)
    {
        this.total = total;
    }

    /**
     * @return the realMonery
     */
    public double getRealMonery()
    {
        return realMonery;
    }

    /**
     * @param realMonery
     *            the realMonery to set
     */
    public void setRealMonery(double realMonery)
    {
        this.realMonery = realMonery;
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
     * @return the items
     */
    public List<BudgetItemBean> getItems()
    {
        return items;
    }

    /**
     * @param items
     *            the items to set
     */
    public void setItems(List<BudgetItemBean> items)
    {
        this.items = items;
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
     * @return the level
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * @param level
     *            the level to set
     */
    public void setLevel(int level)
    {
        this.level = level;
    }

    /**
     * @return the sail
     */
    public double getSail()
    {
        return sail;
    }

    /**
     * @param sail
     *            the sail to set
     */
    public void setSail(double sail)
    {
        this.sail = sail;
    }

    /**
     * @return the orgProfit
     */
    public double getOrgProfit()
    {
        return orgProfit;
    }

    /**
     * @param orgProfit
     *            the orgProfit to set
     */
    public void setOrgProfit(double orgProfit)
    {
        this.orgProfit = orgProfit;
    }

    /**
     * @return the realProfit
     */
    public double getRealProfit()
    {
        return realProfit;
    }

    /**
     * @param realProfit
     *            the realProfit to set
     */
    public void setRealProfit(double realProfit)
    {
        this.realProfit = realProfit;
    }

    /**
     * @return the outSave
     */
    public double getOutSave()
    {
        return outSave;
    }

    /**
     * @param outSave
     *            the outSave to set
     */
    public void setOutSave(double outSave)
    {
        this.outSave = outSave;
    }

    /**
     * @return the outMoney
     */
    public double getOutMoney()
    {
        return outMoney;
    }

    /**
     * @param outMoney
     *            the outMoney to set
     */
    public void setOutMoney(double outMoney)
    {
        this.outMoney = outMoney;
    }

    /**
     * @return the inMoney
     */
    public double getInMoney()
    {
        return inMoney;
    }

    /**
     * @param inMoney
     *            the inMoney to set
     */
    public void setInMoney(double inMoney)
    {
        this.inMoney = inMoney;
    }

    /**
     * @return the parentId
     */
    public String getParentId()
    {
        return parentId;
    }

    /**
     * @param parentId
     *            the parentId to set
     */
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    /**
     * @return the year
     */
    public String getYear()
    {
        return year;
    }

    /**
     * @param year
     *            the year to set
     */
    public void setYear(String year)
    {
        this.year = year;
    }

    /**
     * @return the budgetDepartment
     */
    public String getBudgetDepartment()
    {
        return budgetDepartment;
    }

    /**
     * @param budgetDepartment
     *            the budgetDepartment to set
     */
    public void setBudgetDepartment(String budgetDepartment)
    {
        this.budgetDepartment = budgetDepartment;
    }
}
