package com.glaway.ids.project.plan.service;


import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.common.vo.KnowledgeCatgoryDto;
import com.glaway.ids.common.vo.KnowledgeReferenceDto;
import com.glaway.ids.project.plan.fallback.FallBackKnowledgeCategoryRemoteServiceICallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


/**
 * 配置知识分类接口
 * 
 * @author wqb
 * @version 2019年12月19日 19:35:33
 * @see FeignKnowledgeCategoryRemoteServiceI
 * @since
 */
@FeignClient(value = FeignConstants.KES_KLM_SERVICE, fallbackFactory = FallBackKnowledgeCategoryRemoteServiceICallBack.class)
public interface FeignKnowledgeCategoryRemoteServiceI
{

    /**
     * Description: <br> 条件查询知识分类数据
     *
     * @param knowledgeCategory
     * @return
     */
    @RequestMapping(value = FeignConstants.KES_KLM_FEIGN_SERVICE+"/feign/knowledgeCategoryRestController/getKnowledgeCategoryList.do")
    String getKnowledgeCategoryList(@RequestBody KnowledgeCatgoryDto knowledgeCategory);

    /**
     * 获取知识库名称map
     *
     * @return
     * @see
     */
    @RequestMapping(value = "/kes-klm-service/feign/knowledgeSourceRestController/getLibraryNameMap.do")
    Map<String, String> getLibraryNameMap();

    @RequestMapping(value = "/kes-klm-service/feign/knowledgeReferenceRestController/getKnowledgeReferenceList.do")
    List<KnowledgeReferenceDto> getKnowledgeReferenceList(@RequestBody KnowledgeReferenceDto knowledgeReferenceDto,
                                                          @RequestParam("page") int page,
                                                          @RequestParam("rows") int rows,
                                                          @RequestParam("isPage") boolean isPage);
}
