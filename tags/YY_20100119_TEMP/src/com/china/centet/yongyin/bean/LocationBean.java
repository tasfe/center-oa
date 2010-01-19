/*
 * File Name: LocationBean.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-12-15
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


/**
 * <����>
 * 
 * @author zhuzhu
 * @version 2007-12-15
 * @see
 * @since
 */
@Entity(cache = true)
@Table(name = "t_center_location")
public class LocationBean implements Serializable
{
    @Id
    private String id = "";

    @Html(title = "��������", must = true, maxLength = 20)
    private String locationName = "";

    @Html(title = "�����־", must = true, oncheck = JCheck.ONLY_LETTER, maxLength = 10, tip = "ֻ������ĸ")
    private String locationCode = "";

    @Html(title = "��������", oncheck = "maxLength(100)", type = Element.TEXTAREA)
    private String description = "";

    /**
     * default constructor
     */
    public LocationBean()
    {}

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @return the locationName
     */
    public String getLocationName()
    {
        return locationName;
    }

    /**
     * @return the locationCode
     */
    public String getLocationCode()
    {
        return locationCode;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
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
     * @param locationName
     *            the locationName to set
     */
    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }

    /**
     * @param locationCode
     *            the locationCode to set
     */
    public void setLocationCode(String locationCode)
    {
        this.locationCode = locationCode;
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
