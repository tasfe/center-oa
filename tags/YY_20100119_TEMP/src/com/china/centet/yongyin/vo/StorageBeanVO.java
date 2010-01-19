/**
 * �� �� ��: StorageBeanVO.java <br>
 * �� Ȩ: centerchina Technologies Co., Ltd. Copyright YYYY-YYYY, All rights reserved
 * <br>
 * �� ��: <����> <br>
 * �� �� ��: admin <br>
 * �޸�ʱ��: 2008-1-6 <br>
 * ���ٵ���: <���ٵ���> <br>
 * �޸ĵ���: <�޸ĵ���> <br>
 * �޸�����: <�޸�����> <br>
 */
package com.china.centet.yongyin.vo;


import com.china.centet.yongyin.bean.StorageBean;


/**
 * VO
 * 
 * @author admin
 * @version [�汾��, 2008-1-6]
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾]
 */
public class StorageBeanVO extends StorageBean
{
    private String depotpartName = "";

    private String productName = "";

    /**
     * Ĭ�Ϲ��캯��
     */
    public StorageBeanVO()
    {}

    /**
     * @return ���� depotpartName
     */
    public String getDepotpartName()
    {
        return depotpartName;
    }

    /**
     * @param ��depotpartName���и�ֵ
     */
    public void setDepotpartName(String depotpartName)
    {
        this.depotpartName = depotpartName;
    }

    /**
     * @return ���� productName
     */
    public String getProductName()
    {
        return productName;
    }

    /**
     * @param ��productName���и�ֵ
     */
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

}
