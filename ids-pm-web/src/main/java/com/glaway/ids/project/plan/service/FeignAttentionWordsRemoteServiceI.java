/*
 * 文件名：FeignAttentionWordsService.java 版权：Copyright by www.glaway.com 描述： 修改人：xst 修改时间：2019年8月8日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.glaway.ids.project.plan.service;


import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.common.vo.AttentionWordsDto;
import com.glaway.ids.common.vo.SearchWordsVo;
import com.glaway.ids.project.plan.fallback.FallbackAttentionWordsRemoteServiceCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@FeignClient(value = FeignConstants.KES_KLM_SERVICE, fallbackFactory = FallbackAttentionWordsRemoteServiceCallBack.class)
public interface FeignAttentionWordsRemoteServiceI
{

    /**
     * SearchWordsVo searchWordsVo, int page, int rows, boolean isPage
     *
     * @param
     * @param
     * @param
     * @param
     * @return
     * @see
     */
    @RequestMapping(value = FeignConstants.KES_KLM_FEIGN_SERVICE+"/feign/attentionWordsRestController/getAttentionWordsList.do")
    List<AttentionWordsDto> getAttentionWordsList(@RequestBody FeignJson j);

    @RequestMapping(value = FeignConstants.KES_KLM_FEIGN_SERVICE+"/feign/attentionWordsRestController/getAttentionWordsCount.do")
    long getAttentionWordsCount(@RequestBody SearchWordsVo searchWordsVo);

    /**
     * SearchWordsVo searchWordsVo, TSUserDto currentUserDto
     *
     * @see
     */
    @RequestMapping(value = FeignConstants.KES_KLM_FEIGN_SERVICE+"/feign/attentionWordsRestController/saveAttentionWords.do")
    void saveAttentionWords(@RequestBody SearchWordsVo searchWordsVo, @RequestParam("userId") String userId);

    @RequestMapping(value = FeignConstants.KES_KLM_FEIGN_SERVICE+"/feign/attentionWordsRestController/getKnowledgeItemMap.do")
    Map<String, String> getKnowledgeItemMap();
}
