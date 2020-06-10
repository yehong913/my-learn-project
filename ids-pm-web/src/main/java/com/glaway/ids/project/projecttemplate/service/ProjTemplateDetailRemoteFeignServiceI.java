package com.glaway.ids.project.projecttemplate.service;


import java.util.List;

import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.config.fallback.ProjTemplateRemoteFeignServiceCallBack;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.projecttemplate.fallback.ProjTemplateDetailRemoteFeignServiceCallBack;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 〈项目模板WBS计划接口〉
 * 〈功能详细描述〉
 * @author wangshen
 * @version 2015年5月4日
 * @see ProjTemplateDetailRemoteFeignServiceI
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = ProjTemplateDetailRemoteFeignServiceCallBack.class)
public interface ProjTemplateDetailRemoteFeignServiceI {

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateDetailRestController/convertPlanjTemplateDetail2Plan.do")
    List<PlanDto> convertPlanjTemplateDetail2Plan(@RequestParam("projTemplateId") String projTemplateId);

}
