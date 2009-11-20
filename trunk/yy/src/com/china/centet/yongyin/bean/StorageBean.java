/**
 * �� �� ��: StorageBean.java <br>
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
import com.china.center.annotation.Ignore;
import com.china.center.annotation.JCheck;
import com.china.center.annotation.Table;
import com.china.center.annotation.enums.Element;


/**
 * ��λ��
 * 
 * @author admin
 * @version [�汾��, 2008-1-5]
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾]
 */
@Entity(name = "��λ")
@Table(name = "T_CENTER_STORAGE")
public class StorageBean implements Serializable
{
    @Id
    private String id = "";

    @Html(title = "��λ����", must = true, maxLength = 20, oncheck = {JCheck.NOT_NONE,
        JCheck.ONLY_COMMONCHAR})
    private String name = "";

    @Html(title = "��Ʒ����", oncheck = JCheck.ONLY_NUMBER)
    private int amount = 0;

    private String depotpartId = "";

    @Ignore
    @Html(title = "�洢��Ʒ", type = Element.SELECT, name = "productName", readonly = true)
    private String productId = "";

    private String locationId = "";

    @Html(title = "����", type = Element.TEXTAREA, maxLength = 200)
    private String description = "";

    public StorageBean()
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
     * @return ���� amount
     */
    public int getAmount()
    {
        return amount;
    }

    /**
     * @param ��amount���и�ֵ
     */
    public void setAmount(int amount)
    {
        this.amount = amount;
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
     * @return ���� depotpartId
     */
    public String getDepotpartId()
    {
        return depotpartId;
    }

    /**
     * @param ��depotpartId���и�ֵ
     */
    public void setDepotpartId(String depotpartId)
    {
        this.depotpartId = depotpartId;
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
     * @return ���� productId
     */
    public String getProductId()
    {
        return productId;
    }

    /**
     * @param ��productId���и�ֵ
     */
    public void setProductId(String productId)
    {
        if (productId != null)
        {
            this.productId = productId;
        }
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
}
