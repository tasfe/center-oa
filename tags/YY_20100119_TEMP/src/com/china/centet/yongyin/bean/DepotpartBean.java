/**
 * �� �� ��: DepotpartBean.java <br>
 * �� Ȩ: centerchina Technologies Co., Ltd. Copyright YYYY-YYYY, All rights reserved
 * <br>
 * �� ��: <����> <br>
 * �� �� ��: admin <br>
 * �޸�ʱ��: 2008-1-5 <br>
 * ���ٵ���: <���ٵ���> <br>
 * �޸ĵ���: <�޸ĵ���> <br>
 * �޸�����: <�޸�����> <br>
 */
package com.china.centet.yongyin.bean;


import java.io.Serializable;

import com.china.center.annotation.Entity;
import com.china.center.annotation.Html;
import com.china.center.annotation.Id;
import com.china.center.annotation.Table;
import com.china.center.annotation.enums.Element;


/**
 * ����bean
 * 
 * @author admin
 * @version [�汾��, 2008-1-5]
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾]
 */
@Entity(name = "����", cache = true)
@Table(name = "T_CENTER_DEPOTPART")
public class DepotpartBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @Html(title = "��������", must = true, maxLength = 40)
    private String name = "";

    private String locationId = "";

    /**
     * 1�ɷ� 0and2������
     */
    @Html(title = "��������", type = Element.SELECT)
    private int type = 0;

    @Html(title = "����", type = Element.TEXTAREA)
    private String description = "";

    public DepotpartBean()
    {}

    /**
     * @return ���� id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param ��id���и�ֵ
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return ���� name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param ��name���и�ֵ
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return ���� locationId
     */
    public String getLocationId()
    {
        return locationId;
    }

    /**
     * @param ��locationId���и�ֵ
     */
    public void setLocationId(String locationId)
    {
        this.locationId = locationId;
    }

    /**
     * @return ���� description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param ��description���и�ֵ
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return ���� type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param ��type���и�ֵ
     */
    public void setType(int type)
    {
        this.type = type;
    }

}
