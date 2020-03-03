package com.didispace.chapter25.ThreadTest.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ThreadTestController {

	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		return "test";
	}
}
