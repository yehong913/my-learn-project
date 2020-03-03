package com.didispace.chapter25.activeMQTest.activeMQController;


import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CustomController {
    @JmsListener(destination = "soireeQueue")
    public   void  readMessage( String  message){
        log.info("接受到的消息是"+message);
    }
}
