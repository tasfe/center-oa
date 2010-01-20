/**
 * File Name: ProviderBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-12-19<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.bean;


import com.china.center.annotation.Entity;
import com.china.center.annotation.FK;
import com.china.center.annotation.Id;
import com.china.center.annotation.Table;
import com.china.center.oa.constant.CustomerConstant;


/**
 * ProviderBean
 * 
 * @author zhuzhu
 * @version 2008-12-19
 * @see ProviderHisBean
 * @since 1.0
 */
@Entity(name = "��Ӧ����ʷ", inherit = true)
@Table(name = "T_CENTER_PROVIDE_HIS")
public class ProviderHisBean extends AbstractProviderBean
{
    protected static final long seriaVersionUID = 1L;

    @Id(autoIncrement = true)
    private String id = "";

    @FK
    private String providerId = "";

    private int checkStatus = CustomerConstant.HIS_CHECK_NO;

    public ProviderHisBean()
    {}

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
     * @return the providerId
     */
    public String getProviderId()
    {
        return providerId;
    }

    /**
     * @param providerId
     *            the providerId to set
     */
    public void setProviderId(String providerId)
    {
        this.providerId = providerId;
    }

    /**
     * @return the checkStatus
     */
    public int getCheckStatus()
    {
        return checkStatus;
    }

    /**
     * @param checkStatus
     *            the checkStatus to set
     */
    public void setCheckStatus(int checkStatus)
    {
        this.checkStatus = checkStatus;
    }

}
