/*
 * File Name: StstBean.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-9-2
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.bean;

/**
 * �̵㱨��
 * 
 * @author zhuzhu
 * @version 2007-9-2
 * @see
 * @since
 */
public class StatProductBean extends StorageLogBean
{
    /**
     * ��λ
     */
    private String storageName = "";

    private String depotpartName = "";

    /**
     * ��Ʒ����
     */
    private String productName = "";

    /**
     * ��ǰ����
     */
    private int currentAmount = 0;

    /**
     * default constructor
     */
    public StatProductBean()
    {}

    /**
     * @return the storageName
     */
    public String getStorageName()
    {
        return storageName;
    }

    /**
     * @return the productName
     */
    public String getProductName()
    {
        return productName;
    }

    /**
     * @param storageName
     *            the storageName to set
     */
    public void setStorageName(String storageName)
    {
        this.storageName = storageName;
    }

    /**
     * @param productName
     *            the productName to set
     */
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    /**
     * @return the currentAmount
     */
    public int getCurrentAmount()
    {
        return currentAmount;
    }

    /**
     * @param currentAmount
     *            the currentAmount to set
     */
    public void setCurrentAmount(int currentAmount)
    {
        this.currentAmount = currentAmount;
    }

    /**
     * @return the depotpartName
     */
    public String getDepotpartName()
    {
        return depotpartName;
    }

    /**
     * @param depotpartName
     *            the depotpartName to set
     */
    public void setDepotpartName(String depotpartName)
    {
        this.depotpartName = depotpartName;
    }
}
