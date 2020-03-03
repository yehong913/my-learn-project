package com.didispace.chapter25.ThreadTest.ThreadLocalExample;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/threadLocal")
public class ThreadLocalController {


    @RequestMapping("/test")
    @ResponseBody
    public  Long test(){
            return  RequestHolder.getId();
    }

}
