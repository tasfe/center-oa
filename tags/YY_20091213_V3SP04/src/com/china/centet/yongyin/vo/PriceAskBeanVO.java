/**
 *
 */
package com.china.centet.yongyin.vo;


import java.util.List;

import com.china.center.annotation.Entity;
import com.china.center.annotation.Ignore;
import com.china.center.annotation.Relationship;
import com.china.centet.yongyin.bean.PriceAskBean;


/**
 * @author Administrator
 */
@Entity(inherit = true)
public class PriceAskBeanVO extends PriceAskBean
{
    @Relationship(relationField = "userId", tagField = "stafferName")
    private String userName = "";

    @Relationship(relationField = "productId", tagField = "name")
    private String productName = "";

    @Relationship(relationField = "locationId", tagField = "locationName")
    private String locationName = "";

    @Relationship(relationField = "productId", tagField = "code")
    private String productCode = "";

    @Relationship(relationField = "puserId", tagField = "stafferName")
    private String puserName = "";

    @Ignore
    private List<PriceAskProviderBeanVO> itemVO = null;

    /**
     *
     */
    public PriceAskBeanVO()
    {}

    /**
     * @return the userName
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /**
     * @return the productName
     */
    public String getProductName()
    {
        return productName;
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
     * @return the puserName
     */
    public String getPuserName()
    {
        return puserName;
    }

    /**
     * @param puserName
     *            the puserName to set
     */
    public void setPuserName(String puserName)
    {
        this.puserName = puserName;
    }

    /**
     * @return the productCode
     */
    public String getProductCode()
    {
        return productCode;
    }

    /**
     * @param productCode
     *            the productCode to set
     */
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

    /**
     * @return the itemVO
     */
    public List<PriceAskProviderBeanVO> getItemVO()
    {
        return itemVO;
    }

    /**
     * @param itemVO
     *            the itemVO to set
     */
    public void setItemVO(List<PriceAskProviderBeanVO> itemVO)
    {
        this.itemVO = itemVO;
    }

    /**
     * @return the locationName
     */
    public String getLocationName()
    {
        return locationName;
    }

    /**
     * @param locationName
     *            the locationName to set
     */
    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }
}
