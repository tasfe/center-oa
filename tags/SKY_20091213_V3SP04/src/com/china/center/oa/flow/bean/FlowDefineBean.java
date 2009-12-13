/**
 *
 */
package com.china.center.oa.flow.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.china.center.annotation.Entity;
import com.china.center.annotation.Html;
import com.china.center.annotation.Id;
import com.china.center.annotation.Ignore;
import com.china.center.annotation.Join;
import com.china.center.annotation.Table;
import com.china.center.annotation.Unique;
import com.china.center.annotation.enums.Element;
import com.china.center.annotation.enums.JoinType;
import com.china.center.oa.constant.FlowConstant;
import com.china.center.oa.flow.vs.FlowVSTemplateBean;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * @author Administrator
 */
@Entity(name = "���̶���")
@Table(name = "T_CENTER_OAFLOWDEFINE")
public class FlowDefineBean implements Serializable
{
    @Id
    private String id = "";

    @Unique
    @Html(title = "����", must = true, maxLength = 100)
    private String name = "";

    private String logTime = "";

    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String stafferId = "";

    /**
     * ���̷���(112)
     */
    @Html(title = "����", must = true, type = Element.SELECT)
    private int type = 0;

    @Html(title = "����", must = true, type = Element.SELECT)
    private int parentType = FlowConstant.FLOW_PARENTTYPE_ROOT;

    /**
     * ģʽ
     */
    @Html(title = "ģʽ", must = true, type = Element.SELECT)
    private int mode = FlowConstant.FLOW_MODE_NONE;

    private int status = FlowConstant.FLOW_STATUS_INIT;

    @Html(title = "����", type = Element.TEXTAREA, maxLength = 255)
    private String description = "";

    @Ignore
    private List<FlowTokenBean> tokens = new ArrayList<FlowTokenBean>();

    @Ignore
    private List<FlowViewerBean> views = new ArrayList<FlowViewerBean>();

    /**
     * ����ģ��
     */
    @Ignore
    private List<FlowVSTemplateBean> templates = new ArrayList<FlowVSTemplateBean>();

    /**
     *
     */
    public FlowDefineBean()
    {}

    public String toString()
    {
        return this.id + ';' + this.name;
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
     * @return the logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @param logTime
     *            the logTime to set
     */
    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
    }

    /**
     * @return the stafferId
     */
    public String getStafferId()
    {
        return stafferId;
    }

    /**
     * @param stafferId
     *            the stafferId to set
     */
    public void setStafferId(String stafferId)
    {
        this.stafferId = stafferId;
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
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
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
     * @return the tokens
     */
    public List<FlowTokenBean> getTokens()
    {
        return tokens;
    }

    /**
     * @param tokens
     *            the tokens to set
     */
    public void setTokens(List<FlowTokenBean> tokens)
    {
        this.tokens = tokens;
    }

    /**
     * @return the views
     */
    public List<FlowViewerBean> getViews()
    {
        return views;
    }

    /**
     * @param views
     *            the views to set
     */
    public void setViews(List<FlowViewerBean> views)
    {
        this.views = views;
    }

    /**
     * @return the mode
     */
    public int getMode()
    {
        return mode;
    }

    /**
     * @param mode
     *            the mode to set
     */
    public void setMode(int mode)
    {
        this.mode = mode;
    }

    /**
     * @return the templates
     */
    public List<FlowVSTemplateBean> getTemplates()
    {
        return templates;
    }

    /**
     * @param templates
     *            the templates to set
     */
    public void setTemplates(List<FlowVSTemplateBean> templates)
    {
        this.templates = templates;
    }

    /**
     * @return the parentType
     */
    public int getParentType()
    {
        return parentType;
    }

    /**
     * @param parentType
     *            the parentType to set
     */
    public void setParentType(int parentType)
    {
        this.parentType = parentType;
    }
}
