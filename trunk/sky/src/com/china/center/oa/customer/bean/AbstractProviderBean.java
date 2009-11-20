/**
 * File Name: AbstractBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.bean;


import java.io.Serializable;

import com.china.center.annotation.Html;
import com.china.center.annotation.JCheck;
import com.china.center.annotation.Unique;
import com.china.center.annotation.enums.Element;


/**
 * AbstractBean
 * 
 * @author zhuzhu
 * @version 2008-11-9
 * @see AbstractProviderBean
 * @since 1.0
 */
public abstract class AbstractProviderBean implements Serializable
{
    @Html(title = "����", must = true, maxLength = 80)
    private String name = "";

    @Unique
    @Html(title = "����", must = true, oncheck = JCheck.ONLY_NUMBER_OR_LETTER, maxLength = 40)
    private String code = "";

    @Html(title = "��ϵ��", maxLength = 40)
    private String connector = "";

    @Html(title = "��ַ", maxLength = 200)
    private String address = "";

    @Html(title = "�ֻ�", maxLength = 40)
    private String phone = "";

    @Html(title = "�̻�", maxLength = 40)
    private String tel = "";

    @Html(title = "����", maxLength = 40)
    private String fax = "";

    @Html(title = "QQ", maxLength = 40)
    private String qq = "";

    @Html(title = "E-Mail", maxLength = 40)
    private String email = "";

    @Html(title = "����", maxLength = 100)
    private String bank = "";

    @Html(title = "�ʺ�", maxLength = 40)
    private String accounts = "";

    /**
     * 109���ʽ
     */
    @Html(title = "��������", type = Element.SELECT, must = true)
    private int type = 0;

    private int mtype = 0;

    private int htype = 0;

    private int bakId = 0;

    private String logTime = "";

    @Html(title = "����", type = Element.TEXTAREA, maxLength = 200)
    private String description = "";

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
     * @return the code
     */
    public String getCode()
    {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code)
    {
        this.code = code;
    }

    /**
     * @return the connector
     */
    public String getConnector()
    {
        return connector;
    }

    /**
     * @param connector
     *            the connector to set
     */
    public void setConnector(String connector)
    {
        this.connector = connector;
    }

    /**
     * @return the address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * @return the phone
     */
    public String getPhone()
    {
        return phone;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    /**
     * @return the tel
     */
    public String getTel()
    {
        return tel;
    }

    /**
     * @param tel
     *            the tel to set
     */
    public void setTel(String tel)
    {
        this.tel = tel;
    }

    /**
     * @return the fax
     */
    public String getFax()
    {
        return fax;
    }

    /**
     * @param fax
     *            the fax to set
     */
    public void setFax(String fax)
    {
        this.fax = fax;
    }

    /**
     * @return the qq
     */
    public String getQq()
    {
        return qq;
    }

    /**
     * @param qq
     *            the qq to set
     */
    public void setQq(String qq)
    {
        this.qq = qq;
    }

    /**
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * @return the bank
     */
    public String getBank()
    {
        return bank;
    }

    /**
     * @param bank
     *            the bank to set
     */
    public void setBank(String bank)
    {
        this.bank = bank;
    }

    /**
     * @return the accounts
     */
    public String getAccounts()
    {
        return accounts;
    }

    /**
     * @param accounts
     *            the accounts to set
     */
    public void setAccounts(String accounts)
    {
        this.accounts = accounts;
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
     * @return the mtype
     */
    public int getMtype()
    {
        return mtype;
    }

    /**
     * @param mtype
     *            the mtype to set
     */
    public void setMtype(int mtype)
    {
        this.mtype = mtype;
    }

    /**
     * @return the htype
     */
    public int getHtype()
    {
        return htype;
    }

    /**
     * @param htype
     *            the htype to set
     */
    public void setHtype(int htype)
    {
        this.htype = htype;
    }

    /**
     * @return the bakId
     */
    public int getBakId()
    {
        return bakId;
    }

    /**
     * @param bakId
     *            the bakId to set
     */
    public void setBakId(int bakId)
    {
        this.bakId = bakId;
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
}
