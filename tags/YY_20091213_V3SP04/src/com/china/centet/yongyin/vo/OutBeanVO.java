/**
 * �� �� ��: OutVO.java <br>
 * �� Ȩ: centerchina Technologies Co., Ltd. Copyright YYYY-YYYY, All rights reserved
 * <br>
 * �� ��: <����> <br>
 * �� �� ��: admin <br>
 * �޸�ʱ��: 2008-1-5 <br>
 * ���ٵ���: <���ٵ���> <br>
 * �޸ĵ���: <�޸ĵ���> <br>
 * �޸�����: <�޸�����> <br>
 */
package com.china.centet.yongyin.vo;


import com.china.centet.yongyin.bean.OutBean;


/**
 * Out��չ��
 * 
 * @author admin
 * @version [�汾��, 2008-1-5]
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾]
 */
public class OutBeanVO extends OutBean
{
    private String locationName = "";

    private String destinationName = "";

    private String depotpartName = "";

    private String customerAddress = "";

    public OutBeanVO()
    {}

    /**
     * @return ���� locationName
     */
    public String getLocationName()
    {
        return locationName;
    }

    /**
     * @param ��locationName���и�ֵ
     */
    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }

    /**
     * @return the destinationName
     */
    public String getDestinationName()
    {
        return destinationName;
    }

    /**
     * @param destinationName
     *            the destinationName to set
     */
    public void setDestinationName(String destinationName)
    {
        this.destinationName = destinationName;
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

    /**
     * @return the customerAddress
     */
    public String getCustomerAddress()
    {
        return customerAddress;
    }

    /**
     * @param customerAddress
     *            the customerAddress to set
     */
    public void setCustomerAddress(String customerAddress)
    {
        this.customerAddress = customerAddress;
    }
}
