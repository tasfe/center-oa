/*
 * File Name: SaveOutAction.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-12-15
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.wokflow.sale.action;


import org.jbpm.JbpmConfiguration;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.china.centet.yongyin.bean.User;
import com.china.centet.yongyin.manager.OutManager;
import com.china.centet.yongyin.wokflow.JbpmHelper;


/**
 * �������۵�
 * 
 * @author zhuzhu
 * @version 2007-12-15
 * @see
 * @since
 */
public class PassOutAction implements ActionHandler
{
    private OutManager outManager = null;

    private JbpmConfiguration jbpmConfiguration = null;

    /**
     * �������۵�
     */
    public void execute(ExecutionContext exc)
        throws Exception
    {
        JbpmHelper helper = new JbpmHelper(exc);

        // ���request���������
        String fullId = helper.getStringVariable("fullId");

        String reason = helper.getStringVariable("reason");

        String depotpartId = helper.getStringVariable("depotpartId");

        int nextStatus = helper.getIntVariable("nextStatus");

        User user = helper.get("user", User.class);

        outManager.pass(fullId, user, nextStatus, reason, depotpartId);
    }

    /**
     * @return the outManager
     */
    public OutManager getOutManager()
    {
        return outManager;
    }

    /**
     * @param outManager
     *            the outManager to set
     */
    public void setOutManager(OutManager outManager)
    {
        this.outManager = outManager;
    }

    /**
     * @return the jbpmConfiguration
     */
    public JbpmConfiguration getJbpmConfiguration()
    {
        return jbpmConfiguration;
    }

    /**
     * @param jbpmConfiguration
     *            the jbpmConfiguration to set
     */
    public void setJbpmConfiguration(JbpmConfiguration jbpmConfiguration)
    {
        this.jbpmConfiguration = jbpmConfiguration;
    }
}
