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
import com.china.center.annotation.Id;
import com.china.center.annotation.Table;


/**
 * ProviderBean
 * 
 * @author zhuzhu
 * @version 2008-12-19
 * @see ProviderBean
 * @since 1.0
 */
@Entity(name = "��Ӧ��", inherit = true)
@Table(name = "T_CENTER_PROVIDE")
public class ProviderBean extends AbstractProviderBean
{
    protected static final long seriaVersionUID = 1L;

    @Id
    private String id = "";

    public ProviderBean()
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

}
