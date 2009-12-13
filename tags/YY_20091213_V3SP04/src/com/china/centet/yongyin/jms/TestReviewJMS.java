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
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * @author Liang.xf 2004-12-27 openJms �ǳ־ö����첽������ʾ www.javayou.com
 */
public class TestReviewJMS implements MessageListener
{
    private TopicConnection topicConnection;

    private TopicSession topicSession;

    private Topic topic;

    private TopicSubscriber topicSubscriber;

    private int ii = 0;

    TestReviewJMS()
    {
        try
        {
            // ȡ��JNDI�����ĺ�����
            Hashtable properties = new Hashtable();
            properties.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.exolab.jms.jndi.InitialContextFactory");
            properties.put(Context.PROVIDER_URL, "rmi://10.40.9.229:1099/");
            Context context = new InitialContext(properties);

            // ȡ��Topic�����ӹ���������
            TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory)context.lookup("JmsTopicConnectionFactory");
            topicConnection = topicConnectionFactory.createTopicConnection();

            // ����Topic�ĻỰ�����ڽ�����Ϣ
            topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            topic = (Topic)context.lookup("topic1");

            // ����Topic subscriber
            topicSubscriber = topicSession.createSubscriber(topic);
            // ���ö��ļ���
            topicSubscriber.setMessageListener(this);

            // ������Ϣ����
            topicConnection.start();
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

    public static void main(String[] args)
    {
        System.out.println("��ͬ��������Ϣ�Ľ��գ�");
        try
        {
            new TestReviewJMS();
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    // �յ�������Ϣ���Զ����ô˷���
    public void onMessage(Message message)
    {
        try
        {
            String messageText = null;
            if (message instanceof TextMessage) messageText = ((TextMessage)message).getText();
            System.out.println("002" + messageText);
            ii++ ;

            if (ii > 200000)
            {
                System.out.println("002:" + ii);
            }
        }
        catch (JMSException e)
        {
            System.out.println("002:" + "ERROR");
        }
    }
}
