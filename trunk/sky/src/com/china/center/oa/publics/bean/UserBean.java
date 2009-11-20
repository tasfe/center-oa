/*
 * File Name: User.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-3-25
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.bean;


import java.io.Serializable;
import java.util.List;

import com.china.center.annotation.Entity;
import com.china.center.annotation.FK;
import com.china.center.annotation.Html;
import com.china.center.annotation.Id;
import com.china.center.annotation.Ignore;
import com.china.center.annotation.Join;
import com.china.center.annotation.Table;
import com.china.center.annotation.Unique;
import com.china.center.annotation.enums.Element;
import com.china.center.annotation.enums.JoinType;
import com.china.center.oa.constant.PublicConstant;
import com.china.center.oa.publics.vs.RoleAuthBean;


/**
 * ��Ա��
 * 
 * @author ZHUZHU
 * @version 2007-3-25
 * @see
 * @since
 */
@Entity(cache = true)
@Table(name = "T_CENTER_OAUSER")
public class UserBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @Unique
    @Html(title = "��¼��", must = true, maxLength = 16)
    private String name = "";

    @Html(title = "����", must = true, maxLength = 16, type = Element.PASSWORD)
    private String password = "";

    @FK
    @Join(tagClass = StafferBean.class)
    @Html(title = "ְԱ", must = true, type = Element.SELECT)
    private String stafferId = "";

    @Join(tagClass = LocationBean.class, type = JoinType.LEFT)
    @Html(title = "�ֹ�˾", must = true, type = Element.SELECT)
    private String locationId = "";

    @Join(tagClass = RoleBean.class)
    @Html(title = "��ɫ", must = true, type = Element.SELECT)
    private String roleId = "";

    /**
     * 0:���� 1:����
     */
    private int status = PublicConstant.LOGIN_STATUS_COMMON;

    /**
     * ��¼ʧ�ܴ���
     */
    private int fail = 0;

    private String loginTime = "";

    @Ignore
    private List<RoleAuthBean> auth = null;

    /**
     * default constructor
     */
    public UserBean()
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
     * @return the roleId
     */
    public String getRoleId()
    {
        return roleId;
    }

    /**
     * @param roleId
     *            the roleId to set
     */
    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
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
     * @return the fail
     */
    public int getFail()
    {
        return fail;
    }

    /**
     * @param fail
     *            the fail to set
     */
    public void setFail(int fail)
    {
        this.fail = fail;
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
     * @return the auth
     */
    public List<RoleAuthBean> getAuth()
    {
        return auth;
    }

    /**
     * @param auth
     *            the auth to set
     */
    public void setAuth(List<RoleAuthBean> auth)
    {
        this.auth = auth;
    }
}
