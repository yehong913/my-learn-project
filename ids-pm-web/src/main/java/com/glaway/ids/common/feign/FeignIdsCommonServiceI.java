package com.glaway.ids.common.feign;

import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.common.fallback.FeignIdsCommonServiceCallback;
import com.glaway.ids.config.fallback.BusinessConfigRemoteFeignServiceCallback;
import com.glaway.ids.project.plan.fallback.DeliverablesInfoRemoteFeignServiceCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by LHR on 2019/8/9.
 */
@FeignClient(value = FeignConstants.ID_COMMON_SERVICE,fallbackFactory = FeignIdsCommonServiceCallback.class)
public interface FeignIdsCommonServiceI {

    /**
     * 根据code查询  RepFileType 实例的ID 使得 CODE作为该实例的唯一标识
     * @param fileTypeCode
     * @return
     */
    @RequestMapping(value = FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/repFileTypeQueryRestController/getFileTypeIdByCode.do")
    String getFileTypeIdByCode(@RequestParam(value = "fileTypeCode") String fileTypeCode);
}
