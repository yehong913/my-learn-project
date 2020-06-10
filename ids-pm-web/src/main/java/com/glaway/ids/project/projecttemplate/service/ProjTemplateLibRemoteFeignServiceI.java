package com.glaway.ids.project.projecttemplate.service;


import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.dto.rep.RepRoleFileAuthDto;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.projectmanager.vo.ProjLibRoleFileAuthVo;
import com.glaway.ids.project.projecttemplate.fallback.ProjTemplateLibRemoteFeignServiceCallBack;
import com.glaway.ids.project.projecttemplate.fallback.ProjTemplateRoleRemoteFeignServiceCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 项目模板文档库service
 * 
 * @author wangshen
 * @version 2015年5月26日
 * @see ProjTemplateLibRemoteFeignServiceI
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = ProjTemplateLibRemoteFeignServiceCallBack.class)
public interface ProjTemplateLibRemoteFeignServiceI {


    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateDetailRestController/judgeAndAddValidRoleAuthSize.do")
    boolean judgeAndAddValidRoleAuthSize(@RequestBody List<RepRoleFileAuthDto> roleFileAuthsList);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateDetailRestController/convertProjTemplateRoleFileAuthsVO.do")
    List<ProjLibRoleFileAuthVo> convertProjTemplateRoleFileAuthsVO(@RequestBody List<RepRoleFileAuthDto> roleFileAuthsList);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateDetailRestController/applyTemplete.do")
    FeignJson applyTemplete(@RequestParam(value = "templateId",required = false) String templateId,@RequestParam(value = "libId",required = false) String libId,
                           @RequestBody TSUserDto userDto, @RequestParam(value = "orgId",required = false)String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateDetailRestController/isFileNameRepeat.do")
    boolean isFileNameRepeat(@RequestParam(value = "templateId",required = false) String templateId, @RequestParam(value = "fileName",required = false) String fileName);
}
