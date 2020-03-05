package com.soiree.swagger.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.soiree.swagger.common.CommonResult;
import com.soiree.swagger.mapper.PmsBrandMapper;
import com.soiree.swagger.model.PmsBrand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

@Api(tags = "PmsBrandController", description = "商品品牌管理")
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Resource
    private PmsBrandMapper pmsBrandMapper;

    @ApiOperation("获取所有品牌列表")
    @RequestMapping(value="/byId/{id}",method = RequestMethod.GET)
    public String test(@PathVariable("id") Long id){
        log.info("请求记录"+id);
        PmsBrand pmsBrand=pmsBrandMapper.selectByPrimaryKey(id);
        return  JSONObject.toJSONString(pmsBrand);
    }


    @ApiOperation("添加品牌")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createBrand(@RequestBody PmsBrand pmsBrand) {
        CommonResult commonResult;
        int count = pmsBrandMapper.insert(pmsBrand);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrand);

        } else {
            commonResult = CommonResult.failed("操作失败");

        }
        return commonResult;
    }


}
