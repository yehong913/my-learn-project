/*
 * 文件名：FallBackKnowledgeCategoryServiceI.java 版权：Copyright by www.glaway.com 描述： 修改人：shitian
 * 修改时间：2019年6月11日 跟踪单号： 修改单号： 修改内容：
 */

package com.glaway.ids.project.plan.fallback;


import com.glaway.ids.common.vo.KnowledgeCatgoryDto;
import com.glaway.ids.common.vo.KnowledgeReferenceDto;
import com.glaway.ids.project.plan.service.FeignKnowledgeCategoryRemoteServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class FallBackKnowledgeCategoryRemoteServiceICallBack implements FallbackFactory<FeignKnowledgeCategoryRemoteServiceI>
{

    @Override
    public FeignKnowledgeCategoryRemoteServiceI create(Throwable cause)
    {
        return new FeignKnowledgeCategoryRemoteServiceI() {


            @Override
            public String getKnowledgeCategoryList(KnowledgeCatgoryDto knowledgeCategory) {
                return null;
            }

            @Override
            public Map<String, String> getLibraryNameMap() {
                return null;
            }

            @Override
            public List<KnowledgeReferenceDto> getKnowledgeReferenceList(KnowledgeReferenceDto knowledgeReferenceDto, int page, int rows, boolean isPage) {
                return null;
            }
        };
    }

}
