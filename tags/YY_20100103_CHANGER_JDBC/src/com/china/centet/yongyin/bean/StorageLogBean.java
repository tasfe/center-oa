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
import com.china.center.annotation.Id;
import com.china.center.annotation.Table;


/**
 * ��λ��
 * 
 * @author admin
 * @version [�汾��, 2008-1-5]
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾]
 */
@Entity(name = "��λ��¼")
@Table(name = "T_CENTER_STORAGELOG")
public class StorageLogBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    private String serializeId = "";

    private int type = 0;

    private int preAmount = 0;

    private int afterAmount = 0;

    private int changeAmount = 0;

    private String depotpartId = "";

    private String storageId = "";

    private String productId = "";

    private String locationId = "";

    private String logTime = "";

    /**
     * �������û�
     */
    private String user = "";

    private String description = "";

    public StorageLogBean()
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
        this.productId = productId;
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

    /**
     * @return ���� preAmount
     */
    public int getPreAmount()
    {
        return preAmount;
    }

    /**
     * @param ��preAmount���и�ֵ
     */
    public void setPreAmount(int preAmount)
    {
        this.preAmount = preAmount;
    }

    /**
     * @return ���� afterAmount
     */
    public int getAfterAmount()
    {
        return afterAmount;
    }

    /**
     * @param ��afterAmount���и�ֵ
     */
    public void setAfterAmount(int afterAmount)
    {
        this.afterAmount = afterAmount;
    }

    /**
     * @return ���� changeAmount
     */
    public int getChangeAmount()
    {
        return changeAmount;
    }

    /**
     * @param ��changeAmount���и�ֵ
     */
    public void setChangeAmount(int changeAmount)
    {
        this.changeAmount = changeAmount;
    }

    /**
     * @return ���� storageId
     */
    public String getStorageId()
    {
        return storageId;
    }

    /**
     * @param ��storageId���и�ֵ
     */
    public void setStorageId(String storageId)
    {
        this.storageId = storageId;
    }

    /**
     * @return ���� logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @param ��logTime���и�ֵ
     */
    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
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
     * @return the user
     */
    public String getUser()
    {
        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(String user)
    {
        this.user = user;
    }

    /**
     * @return the serializeId
     */
    public String getSerializeId()
    {
        return serializeId;
    }

    /**
     * @param serializeId
     *            the serializeId to set
     */
    public void setSerializeId(String serializeId)
    {
        this.serializeId = serializeId;
    }
}
