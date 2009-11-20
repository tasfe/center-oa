/**
 * File Name: AbstractHandlerPlugin.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-5-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.plugin.impl;


import java.util.List;

import com.china.center.eltools.ElTools;
import com.china.center.oa.flow.plugin.HandlerPlugin;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * AbstractHandlerPlugin
 * 
 * @author zhuzhu
 * @version 2009-5-10
 * @see AbstractHandlerPlugin
 * @since 1.0
 */
public abstract class AbstractHandlerPlugin implements HandlerPlugin
{
    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.flow.plugin.HandlerPlugin#getHandleName(java.lang.String)
     */
    public abstract String getHandleName(String handleId);

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.flow.plugin.HandlerPlugin#getType()
     */
    public abstract int getType();

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.flow.plugin.HandlerPlugin#getTypeName()
     */
    public String getTypeName()
    {
        return ElTools.get("flowPluginType", getType());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.flow.plugin.HandlerPlugin#hasAuth(java.lang.String, java.util.List)
     */
    public abstract boolean hasAuth(String instanceId, List<String> processers);

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.flow.plugin.HandlerPlugin#listInstanceViewer(java.lang.String)
     */
    public abstract List<StafferBean> listInstanceViewer(String instanceId);

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.flow.plugin.HandlerPlugin#listNextHandler(java.lang.String)
     */
    public abstract List<StafferBean> listNextHandler(String instanceId);

}
