package com.soiree.springcloud.feignserver.service.impl;

import com.soiree.springcloud.feignserver.domain.CommonResult;
import com.soiree.springcloud.feignserver.domain.User;
import com.soiree.springcloud.feignserver.service.FeignService;
import org.springframework.stereotype.Component;

@Component
public class FeignServiceImpl  implements FeignService {
    @Override
    public CommonResult create(User user) {
        User defaultUser = new User(-1L, "defaultUser", "123456");
        return new CommonResult<>(defaultUser);
    }

    @Override
    public CommonResult<User> getUser(Long id) {
        User defaultUser = new User(-1L, "defaultUser", "123456");
        return new CommonResult<>(defaultUser);
    }

    @Override
    public CommonResult<User> getByUsername(String username) {
        User defaultUser = new User(-1L, "defaultUser", "123456");
        return new CommonResult<>(defaultUser);
    }

    @Override
    public CommonResult update(User user) {
        return new CommonResult("调用失败，服务被降级",500);
    }

    @Override
    public CommonResult delete(Long id) {
        return new CommonResult("调用失败，服务被降级",500);
    }
}
