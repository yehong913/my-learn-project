package com.glaway.ids.project.projecttemplate.service;


import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.projecttemplate.fallback.ProjTemplateDetailRemoteFeignServiceCallBack;
import com.glaway.ids.project.projecttemplate.fallback.ProjTemplateRoleRemoteFeignServiceCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 项目模板service
 * 
 * @author wangshen
 * @version 2015年5月4日
 * @see ProjTemplateRoleRemoteFeignServiceI
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = ProjTemplateRoleRemoteFeignServiceCallBack.class)
public interface ProjTemplateRoleRemoteFeignServiceI {

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateDetailRestController/getTeamIdByTemplateId.do")
    FeignJson getTeamIdByTemplateId(@RequestParam("projTemplateId") String projTemplateId);

}
