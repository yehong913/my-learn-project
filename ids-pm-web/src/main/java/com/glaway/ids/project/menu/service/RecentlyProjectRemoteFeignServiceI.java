package com.glaway.ids.project.menu.service;


import java.util.List;

import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.service.CommonService;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.config.fallback.RepFileTypeConfigServiceCallBack;
import com.glaway.ids.project.menu.fallback.RecentlyProjectRemoteFeignServiceCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 左侧项目最近N条列表树
 * 
 * @author wangshen
 * @version 2015年4月14日
 * @see RecentlyProjectRemoteFeignServiceI
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = RecentlyProjectRemoteFeignServiceCallBack.class)
public interface RecentlyProjectRemoteFeignServiceI {



    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectMenuRestController/updateRecentlyByProjectId.do")
    void updateRecentlyByProjectId(@RequestParam("projectId") String projectId,@RequestBody TSUserDto userDto);

}
