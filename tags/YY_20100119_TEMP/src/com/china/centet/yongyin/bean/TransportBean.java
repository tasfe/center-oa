/*
 * File Name: TransportBean.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2008-1-14
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
 * ���䷽ʽ
 * 
 * @author zhuzhu
 * @version 2008-1-14
 * @see
 * @since
 */
@Entity(name = "���䷽ʽ")
@Table(name = "T_CENTER_TRANSPORT")
public class TransportBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @Html(title = "����", must = true, maxLength = 10, oncheck = {JCheck.NOT_NONE,
        JCheck.ONLY_COMMONCHAR})
    private String name = "";

    private int type = 0;

    @Html(title = "��������", type = Element.SELECT, must = true, oncheck = JCheck.NOT_NONE)
    private String parent = "0";

    /**
     * default constructor
     */
    public TransportBean()
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
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @return the parent
     */
    public String getParent()
    {
        return parent;
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
     * @param type
     *            the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @param parent
     *            the parent to set
     */
    public void setParent(String parent)
    {
        if (parent != null)
        {
            this.parent = parent;
        }
    }

}
