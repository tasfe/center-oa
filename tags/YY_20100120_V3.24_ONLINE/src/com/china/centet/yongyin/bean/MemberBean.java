/*
 * File Name: BankBean.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-12-16
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.bean;


import java.io.Serializable;

import com.china.center.annotation.Entity;
import com.china.center.annotation.Html;
import com.china.center.annotation.Id;
import com.china.center.annotation.JCheck;
import com.china.center.annotation.Table;
import com.china.center.annotation.enums.Element;
import com.china.centet.yongyin.constant.Constant;
import com.china.centet.yongyin.constant.MemberContant;


/**
 * ����
 * 
 * @author zhuzhu
 * @version 2007-12-16
 * @see
 * @since
 */
@Entity(name = "��Ա")
@Table(name = "T_CENTER_MEMBER")
public class MemberBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @Html(title = "����", must = true, maxLength = 40)
    private String name = "";

    private String userId = "";

    private String locationId = "";

    @Html(title = "����", must = true, maxLength = 40)
    private String cardNo = "";

    @Html(title = "����", type = Element.PASSWORD, maxLength = 40)
    private String password = "";

    @Html(title = "����", maxLength = 200)
    private String email = "";

    @Html(title = "��ͥ�绰", maxLength = 20)
    private String connect = "";

    @Html(title = "��ͥ��ַ", maxLength = 100)
    private String address = "";

    @Html(title = "�ֻ�", oncheck = JCheck.ONLY_NUMBER, maxLength = 20)
    private String handphone = "";

    @Html(title = "����", type = Element.TEXTAREA, maxLength = 100)
    private String description = "";

    /**
     * ��ͨ���������𿨣�����
     */
    @Html(title = "�ȼ�", type = Element.SELECT, must = true)
    private int grade = MemberContant.GRADE_COMMON;

    @Html(title = "�Ա�", type = Element.SELECT)
    private int sex = Constant.SEX_MALE;

    /**
     * ����
     */
    private int point = 0;

    /**
     * ����ʹ�õĻ���
     */
    private int usepoint = 0;

    /**
     * ���� ��ͨ ����
     */
    @Html(title = "����", type = Element.SELECT, must = true)
    private int type = MemberContant.TYPE_COMMON;

    private String logTime = "";

    @Html(title = "��˾", maxLength = 100)
    private String company = "";

    @Html(title = "ְλ", maxLength = 100)
    private String position = "";

    @Html(title = "ʡ��", maxLength = 20)
    private String area = "";

    private double rebate = 0.0d;

    /**
     * default constructor
     */
    public MemberBean()
    {}

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the userId
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     * @return the locationId
     */
    public String getLocationId()
    {
        return locationId;
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
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
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
     * @return the cardNo
     */
    public String getCardNo()
    {
        return cardNo;
    }

    /**
     * @param cardNo
     *            the cardNo to set
     */
    public void setCardNo(String cardNo)
    {
        this.cardNo = cardNo;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
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
     * @return the connect
     */
    public String getConnect()
    {
        return connect;
    }

    /**
     * @param connect
     *            the connect to set
     */
    public void setConnect(String connect)
    {
        this.connect = connect;
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
     * @return the handphone
     */
    public String getHandphone()
    {
        return handphone;
    }

    /**
     * @param handphone
     *            the handphone to set
     */
    public void setHandphone(String handphone)
    {
        this.handphone = handphone;
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
     * @return the grade
     */
    public int getGrade()
    {
        return grade;
    }

    /**
     * @param grade
     *            the grade to set
     */
    public void setGrade(int grade)
    {
        this.grade = grade;
    }

    /**
     * @return the sex
     */
    public int getSex()
    {
        return sex;
    }

    /**
     * @param sex
     *            the sex to set
     */
    public void setSex(int sex)
    {
        this.sex = sex;
    }

    /**
     * @return the point
     */
    public int getPoint()
    {
        return point;
    }

    /**
     * @param point
     *            the point to set
     */
    public void setPoint(int point)
    {
        this.point = point;
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
     * @return the company
     */
    public String getCompany()
    {
        return company;
    }

    /**
     * @param company
     *            the company to set
     */
    public void setCompany(String company)
    {
        this.company = company;
    }

    /**
     * @return the position
     */
    public String getPosition()
    {
        return position;
    }

    /**
     * @param position
     *            the position to set
     */
    public void setPosition(String position)
    {
        this.position = position;
    }

    /**
     * @return the area
     */
    public String getArea()
    {
        return area;
    }

    /**
     * @param area
     *            the area to set
     */
    public void setArea(String area)
    {
        this.area = area;
    }

    /**
     * @return the rebate
     */
    public double getRebate()
    {
        return rebate;
    }

    /**
     * @param rebate
     *            the rebate to set
     */
    public void setRebate(double rebate)
    {
        this.rebate = rebate;
    }

    /**
     * @return the usepoint
     */
    public int getUsepoint()
    {
        return usepoint;
    }

    /**
     * @param usepoint
     *            the usepoint to set
     */
    public void setUsepoint(int usepoint)
    {
        this.usepoint = usepoint;
    }
}
