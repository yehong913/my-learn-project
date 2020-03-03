package com.didispace.chapter25.activeMQTest;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 发送者
 */
@Slf4j
public class QueneTopicReceiverTest {
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
            //消费者
            MessageConsumer  messageConsumer=session.createConsumer(destination);
            messageConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if(message instanceof  TextMessage){
                        try {
                            TextMessage textMessage= (TextMessage) message;
                            System.out.println("data:===="+textMessage.getText());
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

       /*     boolean  sign=false;
            while(true){
             TextMessage  textMessage= (TextMessage) messageConsumer.receive();

             if(null == textMessage){
                 break;
             }
             log.info(textMessage.getText());
            }*/
            System.in.read();
            session.commit();
            session.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        QueneTopicReceiverTest queneSender= new QueneTopicReceiverTest();
        queneSender.sendMessage();
    }


}
