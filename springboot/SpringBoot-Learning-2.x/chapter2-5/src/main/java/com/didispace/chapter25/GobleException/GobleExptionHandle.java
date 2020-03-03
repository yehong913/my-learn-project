package com.didispace.chapter25.GobleException;


import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GobleExptionHandle {

    @ExceptionHandler(value = RuntimeException.class)
    public String runTimeException(HttpServletRequest request,RuntimeException e){
        return "运行代码错误";
    }

    @ExceptionHandler(value = Exception.class)
    public String exception(HttpServletRequest request,RuntimeException e){
        return "error";
    }


    @ExceptionHandler(value = Error.class)
    public String exception1(HttpServletRequest request,RuntimeException e){
        return "error";
    }


}
