/*
 * 文件名：FallbackKnowledgeReferenceService.java 版权：Copyright by www.glaway.com 描述： 修改人：xst
 * 修改时间：2019年8月8日 跟踪单号： 修改单号： 修改内容：
 */

package com.glaway.ids.project.plan.fallback;


import com.glaway.ids.common.vo.KnowledgeReferenceDto;
import com.glaway.ids.project.plan.service.FeignKnowledgeReferenceRemoteServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class FallbackKnowledgeReferenceRemoteServiceCallBack implements FallbackFactory<FeignKnowledgeReferenceRemoteServiceI>
{

    @Override
    public FeignKnowledgeReferenceRemoteServiceI create(Throwable cause)
    {
        return new FeignKnowledgeReferenceRemoteServiceI()
        {

            @Override
            public List<KnowledgeReferenceDto> getKnowledgeReferenceList(KnowledgeReferenceDto knowledgeReferenceDto, int page, int rows, boolean isPage) {
                return null;
            }

            @Override
            public long getKnowledgeReferenceListCount(KnowledgeReferenceDto knowledgeReference) {
                return 0;
            }

            @Override
            public void saveKnowledgeReference(KnowledgeReferenceDto knowledgeReferenceDto) {

            }

            @Override
            public void deleteKnowledgeReference(KnowledgeReferenceDto knowledgeReferenceDto) {

            }
        };
    }

}
