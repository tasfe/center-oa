/*
 * File Name: Product.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-3-25
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.bean;


import java.io.Serializable;

import com.china.center.annotation.Entity;
import com.china.center.annotation.Id;
import com.china.center.annotation.Ignore;
import com.china.center.annotation.Table;
import com.china.center.tools.StringTools;


/**
 * ��Ʒ
 * 
 * @author ZHUZHU
 * @version 2007-3-25
 * @see
 * @since
 */
@Entity
@Table(name = "T_CENTER_PRODUCT")
public class Product implements Serializable
{
    @Id
    private String id = "";

    private String name = "";

    private String code = "";

    @Ignore
    private int num = 0;

    private String modify = "";

    /**
     * 0����ʽ 1����ʱ
     */
    private int temp = 0;

    /**
     * 0:ÿ�� 1:����
     */
    private int type = 0;

    /**
     * ��Ʒ������
     */
    private int genre = 0;

    /**
     * ������ID
     */
    private String refId = "";

    private String cityFlag = "";

    /**
     * ��ƷͼƬ��·��
     */
    private String picPath = "";

    /**
     * default constructor
     */
    public Product()
    {}

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
     * @return the num
     */
    public int getNum()
    {
        return num;
    }

    /**
     * @param num
     *            the num to set
     */
    public void setNum(int num)
    {
        this.num = num;
    }

    /**
     * @return the modify
     */
    public String getModify()
    {
        return modify;
    }

    /**
     * @param modify
     *            the modify to set
     */
    public void setModify(String modify)
    {
        this.modify = modify;
    }

    /**
     * @return the temp
     */
    public int getTemp()
    {
        return temp;
    }

    /**
     * @param temp
     *            the temp to set
     */
    public void setTemp(int temp)
    {
        this.temp = temp;
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
        if (refId != null)
        {
            this.refId = refId;
        }
    }

    /**
     * @return the cityFlag
     */
    public String getCityFlag()
    {
        return cityFlag;
    }

    /**
     * @param cityFlag
     *            the cityFlag to set
     */
    public void setCityFlag(String cityFlag)
    {
        if ( !StringTools.isNullOrNone(cityFlag))
        {
            this.cityFlag = cityFlag;
        }
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
     * @return the genre
     */
    public int getGenre()
    {
        return genre;
    }

    /**
     * @param genre
     *            the genre to set
     */
    public void setGenre(int genre)
    {
        this.genre = genre;
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

}
