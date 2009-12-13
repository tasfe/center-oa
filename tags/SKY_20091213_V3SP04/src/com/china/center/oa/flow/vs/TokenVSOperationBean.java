/**
 * File Name: FlowVSTemplate.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.vs;


import java.io.Serializable;

import com.china.center.annotation.Entity;
import com.china.center.annotation.FK;
import com.china.center.annotation.Id;
import com.china.center.annotation.Table;
import com.china.center.annotation.Unique;
import com.china.center.oa.constant.FlowConstant;


/**
 * FlowVSTemplate
 * 
 * @author zhuzhu
 * @version 2009-4-26
 * @see TokenVSOperationBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_VS_TOKEN_OPERATION")
public class TokenVSOperationBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    private String flowId = "";

    @FK
    @Unique
    private String tokenId = "";

    private int pass = FlowConstant.OPERATION_YES;

    private int reject = FlowConstant.OPERATION_NO;

    /**
     * ���������沵�ص������̵���һ��
     */
    private int rejectParent = FlowConstant.OPERATION_NO;

    private int rejectAll = FlowConstant.OPERATION_NO;

    private int ends = FlowConstant.OPERATION_NO;

    /**
     * liminal value(if the flow liminal bigger than it,the flow will end)
     */
    private double liminal = 0.0d;

    private int exends = FlowConstant.OPERATION_NO;

    /**
     * default constructor
     */
    public TokenVSOperationBean()
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
     * @return the tokenId
     */
    public String getTokenId()
    {
        return tokenId;
    }

    /**
     * @param tokenId
     *            the tokenId to set
     */
    public void setTokenId(String tokenId)
    {
        this.tokenId = tokenId;
    }

    /**
     * @return the pass
     */
    public int getPass()
    {
        return pass;
    }

    /**
     * @param pass
     *            the pass to set
     */
    public void setPass(int pass)
    {
        this.pass = pass;
    }

    /**
     * @return the reject
     */
    public int getReject()
    {
        return reject;
    }

    /**
     * @param reject
     *            the reject to set
     */
    public void setReject(int reject)
    {
        this.reject = reject;
    }

    /**
     * @return the rejectAll
     */
    public int getRejectAll()
    {
        return rejectAll;
    }

    /**
     * @param rejectAll
     *            the rejectAll to set
     */
    public void setRejectAll(int rejectAll)
    {
        this.rejectAll = rejectAll;
    }

    /**
     * @return the ends
     */
    public int getEnds()
    {
        return ends;
    }

    /**
     * @param ends
     *            the ends to set
     */
    public void setEnds(int ends)
    {
        this.ends = ends;
    }

    /**
     * @return the exends
     */
    public int getExends()
    {
        return exends;
    }

    /**
     * @param exends
     *            the exends to set
     */
    public void setExends(int exends)
    {
        this.exends = exends;
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

    public double getLiminal()
    {
        return liminal;
    }

    public void setLiminal(double liminal)
    {
        this.liminal = liminal;
    }

    /**
     * @return the rejectParent
     */
    public int getRejectParent()
    {
        return rejectParent;
    }

    /**
     * @param rejectParent
     *            the rejectParent to set
     */
    public void setRejectParent(int rejectParent)
    {
        this.rejectParent = rejectParent;
    }
}
