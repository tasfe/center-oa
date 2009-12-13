/*
 * �� �� ��:  TagServletContextListener.java
 * ��    Ȩ:  centerchina Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * ��    ��:  <����>
 * �� �� ��:  admin
 * �޸�ʱ��:  2007-10-11
 * ���ٵ���:  <���ٵ���>
 * �޸ĵ���:  <�޸ĵ���>
 * �޸�����:  <�޸�����>
 */
package com.china.centet.yongyin.jms;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.ConfigurationReader;
import org.exolab.jms.server.JmsServer;

import com.china.center.tools.FileTools;
import com.china.center.tools.ResourceLocator;


/**
 * JMS������
 * 
 * @author admin
 * @version [�汾��, 2007-10-11]
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾]
 */
public class JMSListener implements ServletContextListener
{

    public void contextDestroyed(ServletContextEvent arg0)
    {

    }

    public void contextInitialized(ServletContextEvent evt)
    {
        String rootPath = evt.getServletContext().getRealPath("/") + "WEB-INF/classes/";

        System.setProperty("openjms.home", FileTools.getCommonPath(rootPath));
        // org.exolab.jms.message.MessageImpl
        // org.exolab.jms.messagemgr.DefaultMessageCache

        Configuration config;
        try
        {
            config = ConfigurationReader.read(ResourceLocator.getResource("classpath:config/openjms.xml"));

            JmsServer service = new JmsServer(config);

            // ��������
            service.init();

            JmsServer.version();

            System.out.println("������Ϣ����...");

        }
        catch (Throwable e)
        {
            System.out.println("������Ϣ����ʧ��");
            e.printStackTrace();
        }

    }
}
