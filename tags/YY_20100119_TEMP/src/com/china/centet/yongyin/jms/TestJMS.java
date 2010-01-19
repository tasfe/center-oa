/**
 * �� �� ��: TestJMS.java <br>
 * �� Ȩ: centerchina Technologies Co., Ltd. Copyright YYYY-YYYY, All rights reserved
 * <br>
 * �� ��: <����> <br>
 * �� �� ��: admin <br>
 * �޸�ʱ��: 2008-1-4 <br>
 * ���ٵ���: <���ٵ���> <br>
 * �޸ĵ���: <�޸ĵ���> <br>
 * �޸�����: <�޸�����> <br>
 */
package com.china.centet.yongyin.jms;


import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class TestJMS
{

    public static void main(String[] args)
    {
        try
        {
            // ȡ��JNDI�����ĺ�����
            Hashtable properties = new Hashtable();
            properties.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.exolab.jms.jndi.InitialContextFactory");
            // openJmsĬ�ϵĶ˿���1099
            properties.put(Context.PROVIDER_URL, "rmi://10.40.9.229:1099/");
            Context context = new InitialContext(properties);
            // ���JMS Topic���Ӷ��й���
            TopicConnectionFactory factory = (TopicConnectionFactory)context.lookup("JmsTopicConnectionFactory");

            // ����һ��Topic���ӣ�������
            TopicConnection topicConnection = factory.createTopicConnection();
            topicConnection.start();

            // ����һ��Topic�Ự���������Զ�Ӧ��
            TopicSession topicSession = topicConnection.createTopicSession(false,
                Session.AUTO_ACKNOWLEDGE);

            // lookup �õ� topic1
            Topic topic = (Topic)context.lookup("topic1");
            // ��Topic�Ự����Topic������
            TopicPublisher topicPublisher = topicSession.createPublisher(topic);

            // ������Ϣ��Topic
            System.out.println("��Ϣ������Topic");
            TextMessage message = topicSession.createTextMessage("��ã���ӭ����Topic����Ϣ");
            topicPublisher.publish(message);

            // ��Դ����������� ... ...
        }
        catch (NamingException e)
        {
            e.printStackTrace();
        }
        catch (JMSException e)
        {
            e.printStackTrace();
        }
    }

}
