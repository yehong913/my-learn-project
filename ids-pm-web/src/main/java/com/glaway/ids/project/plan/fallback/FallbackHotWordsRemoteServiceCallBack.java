/*
 * 文件名：FallbackHotWordsService.java 版权：Copyright by www.glaway.com 描述： 修改人：xst 修改时间：2019年8月8日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.glaway.ids.project.plan.fallback;


import com.glaway.ids.common.vo.HotWordsDto;
import com.glaway.ids.project.plan.service.FeignHotWordsRemoteServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class FallbackHotWordsRemoteServiceCallBack implements FallbackFactory<FeignHotWordsRemoteServiceI>
{

    @Override
    public FeignHotWordsRemoteServiceI create(Throwable cause)
    {
        return new FeignHotWordsRemoteServiceI()
        {
            @Override
            public void addHotWords(HotWordsDto hotWordDto) {

            }

            @Override
            public int getHotWordsFrequency(HotWordsDto hotWordsDto) {
                return 0;
            }

            @Override
            public List<HotWordsDto> getHotWords(HotWordsDto hotWordDto) {
                return null;
            }

            @Override
            public List<String> queryTheMostHotWords(HotWordsDto contidionDto, int rownum) {
                return null;
            }

            @Override
            public List<String> queryAlikeHotWords(HotWordsDto contidionDto, int rownum) {
                return null;
            }
        };
    }

}
