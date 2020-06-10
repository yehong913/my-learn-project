/*
 * 文件名：FallbackAttentionWordsService.java 版权：Copyright by www.glaway.com 描述： 修改人：xst 修改时间：2019年8月8日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.glaway.ids.project.plan.fallback;


import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.vo.AttentionWordsDto;
import com.glaway.ids.common.vo.SearchWordsVo;
import com.glaway.ids.project.plan.service.FeignAttentionWordsRemoteServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class FallbackAttentionWordsRemoteServiceCallBack implements FallbackFactory<FeignAttentionWordsRemoteServiceI>
{

    @Override
    public FeignAttentionWordsRemoteServiceI create(Throwable cause)
    {
        return new FeignAttentionWordsRemoteServiceI()
        {
            @Override
            public Map<String, String> getKnowledgeItemMap()
            {
                return null;
            }

            @Override
            public List<AttentionWordsDto> getAttentionWordsList(FeignJson j)
            {
                return null;
            }

            @Override
            public long getAttentionWordsCount(SearchWordsVo searchWordsVo)
            {
                return 0;
            }

            @Override
            public void saveAttentionWords(SearchWordsVo searchWordsVo, String userId) {

            }
        };
    }

}
