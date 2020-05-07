package com.soiree.springcloud.feignserver.service;

import com.soiree.springcloud.feignserver.domain.CommonResult;
import com.soiree.springcloud.feignserver.domain.User;
import com.soiree.springcloud.feignserver.service.impl.FeignServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.*;

@FeignClient(value="user-service",fallback = FeignServiceImpl.class)
public interface FeignService {


    @PostMapping("/user/create")
    CommonResult create(@RequestBody User user);

    @GetMapping("/user/{id}")
    CommonResult<User> getUser(@PathVariable(value = "id") Long id);

    @GetMapping("/user/getByUsername")
    CommonResult<User> getByUsername(@RequestParam(value = "username") String username);

    @PostMapping("/user/update")
    CommonResult update(@RequestBody User user);

    @PostMapping("/user/delete/{id}")
    CommonResult delete(@PathVariable(value = "id") Long id);

}
