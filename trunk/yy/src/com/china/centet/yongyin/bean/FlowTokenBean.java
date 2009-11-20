/**
 *
 */
package com.china.centet.yongyin.bean;


import java.io.Serializable;

import com.china.center.annotation.Entity;
import com.china.center.annotation.FK;
import com.china.center.annotation.Id;
import com.china.center.annotation.Join;
import com.china.center.annotation.Table;
import com.china.centet.yongyin.constant.FlowConstant;


/**
 * @author Administrator
 */
@Entity(name = "���̻���")
@Table(name = "T_CENTER_FLOWDEFINETOKEN")
public class FlowTokenBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    private String name = "";

    @FK
    @Join(tagClass = FlowDefineBean.class)
    private String flowId = "";

    private String processer = "";

    private int preOrders = 0;

    private int orders = 0;

    private int nextOrders = 0;

    private int type = FlowConstant.FLOW_TYPE_USER;

    private boolean begining = false;

    private boolean ending = false;

    private String description = "";

    /**
     *
     */
    public FlowTokenBean()
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
     * @return the flowId
     */
    public String getFlowId()
    {
        return flowId;
    }

    /**
     * @param flowId
     *            the flowId to set
     */
    public void setFlowId(String flowId)
    {
        this.flowId = flowId;
    }

    /**
     * @return the processer
     */
    public String getProcesser()
    {
        return processer;
    }

    /**
     * @param processer
     *            the processer to set
     */
    public void setProcesser(String processer)
    {
        this.processer = processer;
    }

    /**
     * @return the orders
     */
    public int getOrders()
    {
        return orders;
    }

    /**
     * @param orders
     *            the orders to set
     */
    public void setOrders(int orders)
    {
        this.orders = orders;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the begining
     */
    public boolean isBegining()
    {
        return begining;
    }

    /**
     * @param begining
     *            the begining to set
     */
    public void setBegining(boolean begining)
    {
        this.begining = begining;
    }

    /**
     * @return the ending
     */
    public boolean isEnding()
    {
        return ending;
    }

    /**
     * @param ending
     *            the ending to set
     */
    public void setEnding(boolean ending)
    {
        this.ending = ending;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the preOrders
     */
    public int getPreOrders()
    {
        return preOrders;
    }

    /**
     * @param preOrders
     *            the preOrders to set
     */
    public void setPreOrders(int preOrders)
    {
        this.preOrders = preOrders;
    }

    /**
     * @return the nextOrders
     */
    public int getNextOrders()
    {
        return nextOrders;
    }

    /**
     * @param nextOrders
     *            the nextOrders to set
     */
    public void setNextOrders(int nextOrders)
    {
        this.nextOrders = nextOrders;
    }
}
