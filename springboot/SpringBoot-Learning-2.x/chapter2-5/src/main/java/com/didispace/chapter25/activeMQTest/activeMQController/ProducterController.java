package com.didispace.chapter25.activeMQTest.activeMQController;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.Queue;
import javax.jms.Topic;


@RestController
public class ProducterController {
    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Resource
    private Queue queue;
    @Resource
    private Topic topic;


   @RequestMapping("/sendMessage")
   public  void  sendMessage(@RequestParam("msg") String message){

       this.jmsMessagingTemplate.convertAndSend(this.queue,message);
   }

    @RequestMapping("/sendTopic")
    public  void  sendMsg(@RequestParam("msg") String message){
        this.jmsMessagingTemplate.convertAndSend(this.topic,message);
    }


    @Bean
    public Queue queue(){
       return new ActiveMQQueue("soireeQueue");
    }
    @Bean
    public  Topic  topic(){
       return  new ActiveMQTopic("soireeTopic");
    }


}
