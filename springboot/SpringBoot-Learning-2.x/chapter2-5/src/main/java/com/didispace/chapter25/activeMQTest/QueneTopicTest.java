package com.didispace.chapter25.activeMQTest;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 发送者
 *
 * 点对点PTP 消费模式
 * 一对一的消费模式
 */
public class QueneTopicTest {
    Connection connection;

    public  void  sendMessage(){
        try {
            //链接activeMq服务器
            ConnectionFactory  connectionFactory= new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
            //创建链接并拿会话
            connection = connectionFactory.createConnection();
            connection.start();
            Session session=connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            //目的地
            Destination  destination=session.createTopic("TopicQueue");
            //生产者
            MessageProducer messageProducer=session.createProducer(destination);



            for (int i=0;i<300;i++){
                TextMessage  textMessage=session.createTextMessage("i00000标="+i);
                messageProducer.send(textMessage);
            }
            session.commit();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        QueneTopicTest queneSender= new QueneTopicTest();
        queneSender.sendMessage();
    }


}
