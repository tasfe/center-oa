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
import com.china.center.annotation.Join;
import com.china.center.annotation.enums.Element;
import com.china.center.annotation.enums.JoinType;
import com.china.center.oa.constant.CustomerConstant;
import com.china.center.oa.constant.PublicConstant;
import com.china.center.oa.credit.bean.CreditLevelBean;
import com.china.center.oa.publics.bean.AreaBean;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.ProvinceBean;


/**
 * AbstractBean
 * 
 * @author ZHUZHU
 * @version 2008-11-9
 * @see AbstractBean
 * @since 1.0
 */
public abstract class AbstractBean implements Serializable
{
    @Html(title = "�ͻ�����", must = true, maxLength = 40, oncheck = JCheck.ONLY_NUMBER_OR_LETTER)
    private String code = "";

    @Html(title = "ʡ", must = true, type = Element.SELECT)
    @Join(tagClass = ProvinceBean.class)
    private String provinceId = "";

    @Html(title = "����", must = true, type = Element.SELECT)
    @Join(tagClass = CityBean.class)
    private String cityId = "";

    @Html(title = "����", type = Element.SELECT)
    @Join(tagClass = AreaBean.class, type = JoinType.LEFT)
    private String areaId = "";

    @Html(title = "�Ա�", must = true, type = Element.SELECT)
    private int sex = PublicConstant.SEX_MAN;

    /**
     * 1.���˹��� 2.�Ź�
     */
    @Html(title = "�ͻ�����", must = true, type = Element.SELECT)
    private int mtype = 0;

    @Html(title = "��ҵ����", must = true, type = Element.SELECT)
    private int htype = 0;

    @Html(title = "��ϵʱ��", type = Element.DATE)
    private String beginConnectTime = "";

    /**
     * 0:û�� 1����
     */
    @Html(title = "��ʷ�ɽ�", must = true, type = Element.SELECT)
    private int blog = 0;

    @Html(title = "������Ƭ", must = true, type = Element.SELECT)
    private int card = 0;

    /**
     * �Ƿ��Ѿ�������¿ͻ��� 0:��û�� 1���Ѿ���
     */
    private int hasNew = 0;

    @Html(title = "ְ��", maxLength = 100)
    private String post = "";

    @Html(title = "QQ����", must = true, maxLength = 40)
    private String qq = "";

    @Html(title = "MSN����", maxLength = 40)
    private String msn = "";

    @Html(title = "��ַ", maxLength = 255)
    private String web = "";

    @Html(title = "����", type = Element.DATE)
    private String birthday = "";

    @Html(title = "��ϵ��", must = true)
    private String connector = "";

    @Html(title = "��ϵ�˲���")
    private String cdepartement = "";

    /**
     * locationId
     */
    @Join(tagClass = LocationBean.class)
    private String locationId = "";

    /**
     * 0:�ն� 1:��չ
     */
    @Html(title = "�ͻ�����", must = true, type = Element.SELECT)
    private int selltype = CustomerConstant.SELLTYPE_TER;

    /**
     * 0���ˡ�1��ҵ��2����
     */
    @Html(title = "�ͻ�����1", must = true, type = Element.SELECT)
    private int protype = 0;

    /**
     * 0�¿ͻ���1�Ͽͻ�
     */
    @Html(title = "�ͻ�����2", must = true, type = Element.SELECT)
    private int newtype = CustomerConstant.NEWTYPE_NEW;

    /**
     * 0���ʿͻ���1һ��ͻ���2ά���ͻ���3�����ͻ�
     */
    @Html(title = "�ͻ��ȼ�", must = true, type = Element.SELECT)
    private int qqtype = 0;

    /**
     * 0Ǳ�ڿͻ���1����ͻ���2ȷ���ͻ���3̸�пͻ���4�ɽ��ͻ���5�����ɽ��ͻ�(real)
     */
    @Html(title = "��������", must = true, type = Element.SELECT)
    private int rtype = 0;

    /**
     * 0���硢1�绰��2չ�ᡢ3�ͻ����ܡ�4���ѽ��� 99����
     */
    @Html(title = "�ͻ���Դ", must = true, type = Element.SELECT)
    private int formtype = 0;

    /**
     * 0:ok 1:����
     */
    private int status = 0;

    private String createrId = "";

    @Html(title = "��˾/����", maxLength = 200)
    private String company = "";

    @Html(title = "��ַ", maxLength = 200, must = true)
    private String address = "";

    @Html(title = "�ƶ��绰", maxLength = 200)
    private String handphone = "";

    @Html(title = "�̻�", maxLength = 200)
    private String tel = "";

    @Html(title = "��������", must = true, maxLength = 100)
    private String mail = "";

    @Html(title = "����", maxLength = 40)
    private String fax = "";

    @Html(title = "�ʱ�", maxLength = 40)
    private String postcode = "";

    @Html(title = "����", maxLength = 40)
    private String bank = "";

    @Html(title = "�����ʺ�", maxLength = 40)
    private String accounts = "";

    @Html(title = "˰��", maxLength = 100)
    private String dutycode = "";

    @Html(title = "ָ������", maxLength = 100)
    private String flowcom = "";

    private String loginTime = "";

    @Html(title = "����", type = Element.TEXTAREA, maxLength = 200)
    private String description = "";

    @Join(tagClass = CreditLevelBean.class, type = JoinType.LEFT)
    private String creditLevelId = CustomerConstant.CREDITLEVELID_DEFAULT;

    private double creditVal = 30.0d;

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
     * @return the provinceId
     */
    public String getProvinceId()
    {
        return provinceId;
    }

    /**
     * @param provinceId
     *            the provinceId to set
     */
    public void setProvinceId(String provinceId)
    {
        this.provinceId = provinceId;
    }

    /**
     * @return the cityId
     */
    public String getCityId()
    {
        return cityId;
    }

    /**
     * @param cityId
     *            the cityId to set
     */
    public void setCityId(String cityId)
    {
        this.cityId = cityId;
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
     * @return the selltype
     */
    public int getSelltype()
    {
        return selltype;
    }

    /**
     * @param selltype
     *            the selltype to set
     */
    public void setSelltype(int selltype)
    {
        this.selltype = selltype;
    }

    /**
     * @return the protype
     */
    public int getProtype()
    {
        return protype;
    }

    /**
     * @param protype
     *            the protype to set
     */
    public void setProtype(int protype)
    {
        this.protype = protype;
    }

    /**
     * @return the newtype
     */
    public int getNewtype()
    {
        return newtype;
    }

    /**
     * @param newtype
     *            the newtype to set
     */
    public void setNewtype(int newtype)
    {
        this.newtype = newtype;
    }

    /**
     * @return the qqtype
     */
    public int getQqtype()
    {
        return qqtype;
    }

    /**
     * @param qqtype
     *            the qqtype to set
     */
    public void setQqtype(int qqtype)
    {
        this.qqtype = qqtype;
    }

    /**
     * @return the rtype
     */
    public int getRtype()
    {
        return rtype;
    }

    /**
     * @param rtype
     *            the rtype to set
     */
    public void setRtype(int rtype)
    {
        this.rtype = rtype;
    }

    /**
     * @return the formtype
     */
    public int getFormtype()
    {
        return formtype;
    }

    /**
     * @param formtype
     *            the formtype to set
     */
    public void setFormtype(int formtype)
    {
        this.formtype = formtype;
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
     * @return the createrId
     */
    public String getCreaterId()
    {
        return createrId;
    }

    /**
     * @param createrId
     *            the createrId to set
     */
    public void setCreaterId(String createrId)
    {
        this.createrId = createrId;
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
     * @return the mail
     */
    public String getMail()
    {
        return mail;
    }

    /**
     * @param mail
     *            the mail to set
     */
    public void setMail(String mail)
    {
        this.mail = mail;
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
     * @return the postcode
     */
    public String getPostcode()
    {
        return postcode;
    }

    /**
     * @param postcode
     *            the postcode to set
     */
    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
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
     * @return the dutycode
     */
    public String getDutycode()
    {
        return dutycode;
    }

    /**
     * @param dutycode
     *            the dutycode to set
     */
    public void setDutycode(String dutycode)
    {
        this.dutycode = dutycode;
    }

    /**
     * @return the flowcom
     */
    public String getFlowcom()
    {
        return flowcom;
    }

    /**
     * @param flowcom
     *            the flowcom to set
     */
    public void setFlowcom(String flowcom)
    {
        this.flowcom = flowcom;
    }

    /**
     * @return the loginTime
     */
    public String getLoginTime()
    {
        return loginTime;
    }

    /**
     * @param loginTime
     *            the loginTime to set
     */
    public void setLoginTime(String loginTime)
    {
        this.loginTime = loginTime;
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
     * @return the beginConnectTime
     */
    public String getBeginConnectTime()
    {
        return beginConnectTime;
    }

    /**
     * @param beginConnectTime
     *            the beginConnectTime to set
     */
    public void setBeginConnectTime(String beginConnectTime)
    {
        this.beginConnectTime = beginConnectTime;
    }

    /**
     * @return the blog
     */
    public int getBlog()
    {
        return blog;
    }

    /**
     * @param blog
     *            the blog to set
     */
    public void setBlog(int blog)
    {
        this.blog = blog;
    }

    /**
     * @return the post
     */
    public String getPost()
    {
        return post;
    }

    /**
     * @param post
     *            the post to set
     */
    public void setPost(String post)
    {
        this.post = post;
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
     * @return the msn
     */
    public String getMsn()
    {
        return msn;
    }

    /**
     * @param msn
     *            the msn to set
     */
    public void setMsn(String msn)
    {
        this.msn = msn;
    }

    /**
     * @return the web
     */
    public String getWeb()
    {
        return web;
    }

    /**
     * @param web
     *            the web to set
     */
    public void setWeb(String web)
    {
        this.web = web;
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
     * @return the card
     */
    public int getCard()
    {
        return card;
    }

    /**
     * @param card
     *            the card to set
     */
    public void setCard(int card)
    {
        this.card = card;
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
     * @return the areaId
     */
    public String getAreaId()
    {
        return areaId;
    }

    /**
     * @param areaId
     *            the areaId to set
     */
    public void setAreaId(String areaId)
    {
        this.areaId = areaId;
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
     * @return the hasNew
     */
    public int getHasNew()
    {
        return hasNew;
    }

    /**
     * @param hasNew
     *            the hasNew to set
     */
    public void setHasNew(int hasNew)
    {
        this.hasNew = hasNew;
    }

    /**
     * @return the cdepartement
     */
    public String getCdepartement()
    {
        return cdepartement;
    }

    /**
     * @param cdepartement
     *            the cdepartement to set
     */
    public void setCdepartement(String cdepartement)
    {
        this.cdepartement = cdepartement;
    }

    /**
     * @return the creditLevelId
     */
    public String getCreditLevelId()
    {
        return creditLevelId;
    }

    /**
     * @param creditLevelId
     *            the creditLevelId to set
     */
    public void setCreditLevelId(String creditLevelId)
    {
        this.creditLevelId = creditLevelId;
    }

    /**
     * @return the creditVal
     */
    public double getCreditVal()
    {
        return creditVal;
    }

    /**
     * @param creditVal
     *            the creditVal to set
     */
    public void setCreditVal(double creditVal)
    {
        this.creditVal = creditVal;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String tab = ",";

        StringBuilder retValue = new StringBuilder();

        retValue.append("AbstractBean ( ").append(super.toString()).append(tab).append("code = ").append(
            this.code).append(tab).append("provinceId = ").append(this.provinceId).append(tab).append(
            "cityId = ").append(this.cityId).append(tab).append("areaId = ").append(this.areaId).append(
            tab).append("sex = ").append(this.sex).append(tab).append("mtype = ").append(
            this.mtype).append(tab).append("htype = ").append(this.htype).append(tab).append(
            "beginConnectTime = ").append(this.beginConnectTime).append(tab).append("blog = ").append(
            this.blog).append(tab).append("card = ").append(this.card).append(tab).append(
            "hasNew = ").append(this.hasNew).append(tab).append("post = ").append(this.post).append(
            tab).append("qq = ").append(this.qq).append(tab).append("msn = ").append(this.msn).append(
            tab).append("web = ").append(this.web).append(tab).append("birthday = ").append(
            this.birthday).append(tab).append("connector = ").append(this.connector).append(tab).append(
            "cdepartement = ").append(this.cdepartement).append(tab).append("locationId = ").append(
            this.locationId).append(tab).append("selltype = ").append(this.selltype).append(tab).append(
            "protype = ").append(this.protype).append(tab).append("newtype = ").append(
            this.newtype).append(tab).append("qqtype = ").append(this.qqtype).append(tab).append(
            "rtype = ").append(this.rtype).append(tab).append("formtype = ").append(this.formtype).append(
            tab).append("status = ").append(this.status).append(tab).append("createrId = ").append(
            this.createrId).append(tab).append("company = ").append(this.company).append(tab).append(
            "address = ").append(this.address).append(tab).append("handphone = ").append(
            this.handphone).append(tab).append("tel = ").append(this.tel).append(tab).append(
            "mail = ").append(this.mail).append(tab).append("fax = ").append(this.fax).append(tab).append(
            "postcode = ").append(this.postcode).append(tab).append("bank = ").append(this.bank).append(
            tab).append("accounts = ").append(this.accounts).append(tab).append("dutycode = ").append(
            this.dutycode).append(tab).append("flowcom = ").append(this.flowcom).append(tab).append(
            "loginTime = ").append(this.loginTime).append(tab).append("description = ").append(
            this.description).append(tab).append("creditLevelId = ").append(this.creditLevelId).append(
            tab).append("creditVal = ").append(this.creditVal).append(tab).append(" )");

        return retValue.toString();
    }
}
