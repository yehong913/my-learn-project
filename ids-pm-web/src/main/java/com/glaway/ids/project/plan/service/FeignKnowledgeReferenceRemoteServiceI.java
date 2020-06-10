/*
 * 文件名：FeignKnowledgeReferenceService.java 版权：Copyright by www.glaway.com 描述： 修改人：xst
 * 修改时间：2019年8月8日 跟踪单号： 修改单号： 修改内容：
 */

package com.glaway.ids.project.plan.service;


import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.common.vo.KnowledgeReferenceDto;
import com.glaway.ids.project.plan.fallback.FallbackKnowledgeReferenceRemoteServiceCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(value = FeignConstants.KES_KLM_SERVICE, fallbackFactory = FallbackKnowledgeReferenceRemoteServiceCallBack.class)
public interface FeignKnowledgeReferenceRemoteServiceI
{

    @RequestMapping(value = "/kes-klm-service/feign/knowledgeReferenceRestController/getKnowledgeReferenceList.do")
    List<KnowledgeReferenceDto> getKnowledgeReferenceList(@RequestBody KnowledgeReferenceDto knowledgeReferenceDto,
                                                          @RequestParam("page") int page,
                                                          @RequestParam("rows") int rows,
                                                          @RequestParam("isPage") boolean isPage);

    @RequestMapping(value = "/kes-klm-service/feign/knowledgeReferenceRestController/getKnowledgeReferenceListCount.do")
    long getKnowledgeReferenceListCount(@RequestBody KnowledgeReferenceDto knowledgeReference);

    @RequestMapping(value = "/kes-klm-service/feign/knowledgeReferenceRestController/saveKnowledgeReference.do")
    void saveKnowledgeReference(@RequestBody KnowledgeReferenceDto knowledgeReferenceDto);

    @RequestMapping(value = "/kes-klm-service/feign/knowledgeReferenceRestController/deleteKnowledgeReference.do")
    void deleteKnowledgeReference(@RequestBody KnowledgeReferenceDto knowledgeReferenceDto);
}
