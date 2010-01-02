/**
 * File Name: StafferBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-2<br>
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.bean;


import java.io.Serializable;

import com.china.center.annotation.Entity;
import com.china.center.annotation.Html;
import com.china.center.annotation.Id;
import com.china.center.annotation.JCheck;
import com.china.center.annotation.Join;
import com.china.center.annotation.Table;
import com.china.center.annotation.Unique;
import com.china.center.annotation.enums.Element;
import com.china.center.oa.constant.PublicConstant;


/**
 * StafferBean
 * 
 * @author zhuzhu
 * @version 2008-11-2
 * @see StafferBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_OASTAFFER")
public class StafferBean2 implements Serializable
{
    @Id
    private String id = "";

    @Unique
    @Html(title = "����", must = true, maxLength = 100)
    private String name = "";

    @Html(title = "����", must = true, maxLength = 20, oncheck = JCheck.ONLY_NUMBER_OR_LETTER)
    private String code = "";

    @Html(title = "��������", must = true, type = Element.SELECT)
    private int examType = 0;

    /**
     * status
     */
    private int status = 0;

    @Html(title = "�ֹ�˾", must = true, type = Element.SELECT)
    @Join(tagClass = LocationBean.class)
    private String locationId = "";

    @Html(title = "�Ա�", must = true, type = Element.SELECT)
    private int sex = PublicConstant.SEX_MAN;

    @Html(title = "��ҵʱ��", type = Element.DATE)
    private String graduateDate = "";

    @Html(title = "רҵ", maxLength = 100)
    private String specialty = "";

    @Html(title = "ѧ��", maxLength = 100)
    private String graduate = "";

    @Html(title = "��ҵԺУ", maxLength = 100)
    private String graduateSchool = "";

    @Html(title = "����", type = Element.SELECT)
    private String departmentId = "";

    @Html(title = "ְԱ����", type = Element.SELECT)
    private String postId = "";

    @Html(title = "��λ", type = Element.INPUT, name = "principalshipName", must = true)
    private String principalshipId = "";

    @Html(title = "����", maxLength = 20)
    private String nation = "";

    @Html(title = "����", maxLength = 20)
    private String city = "";

    @Html(title = "��ַ", maxLength = 100)
    private String address = "";

    @Html(title = "������ò", maxLength = 20)
    private String visage = "";

    @Html(title = "���֤", maxLength = 50, oncheck = JCheck.ONLY_NUMBER_OR_LETTER)
    private String idCard = "";

    @Html(title = "����", type = Element.DATE)
    private String birthday = "";

    @Html(title = "�ƶ��绰", oncheck = JCheck.ONLY_NUMBER, maxLength = 20)
    private String handphone = "";

    @Html(title = "�ֻ�����", oncheck = JCheck.ONLY_NUMBER, maxLength = 20)
    private String subphone = "";

    private String picPath = "";

    private String idiograph = "";

    private String logTime = "";

    @Html(title = "���ö��", oncheck = JCheck.ONLY_FLOAT, maxLength = 20)
    private double credit = 0.0d;

    @Html(title = "����", type = Element.TEXTAREA, maxLength = 200)
    private String description = "";

    /**
     * default constructor
     */
    public StafferBean2()
    {}

    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }

        if (this == obj)
        {
            return true;
        }

        if (obj instanceof StafferBean)
        {
            StafferBean oo = (StafferBean)obj;

            return this.id.equals(oo.getId());
        }

        return false;
    }

    public int hashCode()
    {
        return this.id.hashCode();
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
     * @return the graduateDate
     */
    public String getGraduateDate()
    {
        return graduateDate;
    }

    /**
     * @param graduateDate
     *            the graduateDate to set
     */
    public void setGraduateDate(String graduateDate)
    {
        this.graduateDate = graduateDate;
    }

    /**
     * @return the specialty
     */
    public String getSpecialty()
    {
        return specialty;
    }

    /**
     * @param specialty
     *            the specialty to set
     */
    public void setSpecialty(String specialty)
    {
        this.specialty = specialty;
    }

    /**
     * @return the graduate
     */
    public String getGraduate()
    {
        return graduate;
    }

    /**
     * @param graduate
     *            the graduate to set
     */
    public void setGraduate(String graduate)
    {
        this.graduate = graduate;
    }

    /**
     * @return the graduateSchool
     */
    public String getGraduateSchool()
    {
        return graduateSchool;
    }

    /**
     * @param graduateSchool
     *            the graduateSchool to set
     */
    public void setGraduateSchool(String graduateSchool)
    {
        this.graduateSchool = graduateSchool;
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
     * @return the postId
     */
    public String getPostId()
    {
        return postId;
    }

    /**
     * @param postId
     *            the postId to set
     */
    public void setPostId(String postId)
    {
        this.postId = postId;
    }

    /**
     * @return the principalshipId
     */
    public String getPrincipalshipId()
    {
        return principalshipId;
    }

    /**
     * @param principalshipId
     *            the principalshipId to set
     */
    public void setPrincipalshipId(String principalshipId)
    {
        this.principalshipId = principalshipId;
    }

    /**
     * @return the nation
     */
    public String getNation()
    {
        return nation;
    }

    /**
     * @param nation
     *            the nation to set
     */
    public void setNation(String nation)
    {
        this.nation = nation;
    }

    /**
     * @return the city
     */
    public String getCity()
    {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city)
    {
        this.city = city;
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
     * @return the visage
     */
    public String getVisage()
    {
        return visage;
    }

    /**
     * @param visage
     *            the visage to set
     */
    public void setVisage(String visage)
    {
        this.visage = visage;
    }

    /**
     * @return the idCard
     */
    public String getIdCard()
    {
        return idCard;
    }

    /**
     * @param idCard
     *            the idCard to set
     */
    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
    }

    /**
     * @return the birthday
     */
    public String getBirthday()
    {
        return birthday;
    }

    /**
     * @param birthday
     *            the birthday to set
     */
    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
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
     * @return the subphone
     */
    public String getSubphone()
    {
        return subphone;
    }

    /**
     * @param subphone
     *            the subphone to set
     */
    public void setSubphone(String subphone)
    {
        this.subphone = subphone;
    }

    /**
     * @return the picPath
     */
    public String getPicPath()
    {
        return picPath;
    }

    /**
     * @param picPath
     *            the picPath to set
     */
    public void setPicPath(String picPath)
    {
        this.picPath = picPath;
    }

    /**
     * @return the idiograph
     */
    public String getIdiograph()
    {
        return idiograph;
    }

    /**
     * @param idiograph
     *            the idiograph to set
     */
    public void setIdiograph(String idiograph)
    {
        this.idiograph = idiograph;
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
     * @return the examType
     */
    public int getExamType()
    {
        return examType;
    }

    /**
     * @param examType
     *            the examType to set
     */
    public void setExamType(int examType)
    {
        this.examType = examType;
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
     * @return the credit
     */
    public double getCredit()
    {
        return credit;
    }

    /**
     * @param credit
     *            the credit to set
     */
    public void setCredit(double credit)
    {
        this.credit = credit;
    }
}
