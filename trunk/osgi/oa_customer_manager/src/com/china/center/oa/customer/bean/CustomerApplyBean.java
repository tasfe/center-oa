/**
 * File Name: CustomerApplyBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.bean;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * CustomerApplyBean
 * 
 * @author ZHUZHU
 * @version 2008-11-9
 * @see CustomerApplyBean
 * @since 1.0
 */
@Entity(inherit = true)
@Table(name = "T_CENTER_CUSTOMER_APPLY")
public class CustomerApplyBean extends AbstractBean
{
    @Id
    private String id = "";

    @Unique
    @Html(title = "客户名称", must = true, maxLength = 100)
    private String name = "";

    /**
     * 0:add 1:update 2:delete 3:update2
     */
    private int opr = 0;

    /**
     * updaterId
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String updaterId = "";

    private String oprReson = "";

    private String reson = "";

    /**
     * @return the reson
     */
    public String getReson()
    {
        return reson;
    }

    /**
     * @param reson
     *            the reson to set
     */
    public void setReson(String reson)
    {
        this.reson = reson;
    }

    /**
     * default constructor
     */
    public CustomerApplyBean()
    {
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
     * @return the opr
     */
    public int getOpr()
    {
        return opr;
    }

    /**
     * @param opr
     *            the opr to set
     */
    public void setOpr(int opr)
    {
        this.opr = opr;
    }

    /**
     * @return the oprReson
     */
    public String getOprReson()
    {
        return oprReson;
    }

    /**
     * @param oprReson
     *            the oprReson to set
     */
    public void setOprReson(String oprReson)
    {
        this.oprReson = oprReson;
    }

    /**
     * @return the updaterId
     */
    public String getUpdaterId()
    {
        return updaterId;
    }

    /**
     * @param updaterId
     *            the updaterId to set
     */
    public void setUpdaterId(String updaterId)
    {
        this.updaterId = updaterId;
    }
}
