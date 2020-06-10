/*
 * 文件名：FeignHotWordsService.java 版权：Copyright by www.glaway.com 描述： 修改人：xst 修改时间：2019年8月8日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.glaway.ids.project.plan.service;


import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.common.vo.HotWordsDto;
import com.glaway.ids.project.plan.fallback.FallbackHotWordsRemoteServiceCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(value = FeignConstants.KES_KLM_SERVICE, fallbackFactory = FallbackHotWordsRemoteServiceCallBack.class)
public interface FeignHotWordsRemoteServiceI
{
    @RequestMapping(value = FeignConstants.KES_KLM_FEIGN_SERVICE+"/feign/hotWordsRestController/addHotWords.do")
    void addHotWords(@RequestBody HotWordsDto hotWordDto);

    /**
     * 获取单词热度
     *
     * @return
     * @see
     */
    @RequestMapping(value = FeignConstants.KES_KLM_FEIGN_SERVICE+"/feign/hotWordsRestController/getHotWordsFrequency.do")
    int getHotWordsFrequency(@RequestBody HotWordsDto hotWordsDto);

    /**
     * 根据条件从数据库获取热词
     *
     * @return
     * @see
     */
    @RequestMapping(value = FeignConstants.KES_KLM_FEIGN_SERVICE+"/feign/hotWordsRestController/getHotWords.do")
    List<HotWordsDto> getHotWords(@RequestBody HotWordsDto hotWordDto);

    /**
     * 获取最热的rownum条热词、精确查找
     *
     * @param rownum
     * @return
     * @see
     */
    @RequestMapping(value = FeignConstants.KES_KLM_FEIGN_SERVICE+"/feign/hotWordsRestController/queryTheMostHotWords.do")
    List<String> queryTheMostHotWords(@RequestBody HotWordsDto contidionDto,
                                      @RequestParam("rownum") int rownum);

    /**
     * 获取最热的rownum条热词、模糊匹配
     *
     * @param rownum
     * @return
     * @see
     */
    @RequestMapping(value = FeignConstants.KES_KLM_FEIGN_SERVICE+"/feign/hotWordsRestController/queryAlikeHotWords.do")
    List<String> queryAlikeHotWords(@RequestBody HotWordsDto contidionDto,
                                    @RequestParam("rownum") int rownum);
}
