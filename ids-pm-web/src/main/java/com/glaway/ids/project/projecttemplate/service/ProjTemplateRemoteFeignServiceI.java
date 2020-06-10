package com.glaway.ids.project.projecttemplate.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileDto;
import com.glaway.foundation.system.lifecycle.entity.LifeCycleStatus;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.config.fallback.ProjTemplateRemoteFeignServiceCallBack;
import com.glaway.ids.config.fallback.RepFileTypeConfigServiceCallBack;
import com.glaway.ids.project.menu.dto.ProjTemplateMenuDto;
import com.glaway.ids.project.projecttemplate.dto.ProjTemplateDto;
import com.glaway.ids.project.projecttemplate.dto.ProjTmpLibAuthLibTmpLinkDto;
import feign.Feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 项目模板service
 * 
 * @author wangshen
 * @version 2015年3月24日
 * @see ProjTemplateRemoteFeignServiceI
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = ProjTemplateRemoteFeignServiceCallBack.class)
public interface ProjTemplateRemoteFeignServiceI {

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/isPTOM.do")
    boolean isPTOM(@RequestParam("userId") String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/getProjTemplateEntity.do")
    ProjTemplateDto getProjTemplateEntity(@RequestParam("id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/deleteTemplate.do")
    FeignJson deleteTemplate(@RequestBody List<ProjTemplateDto> projTemplateDtos);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/openOrClose.do")
    FeignJson openOrClose(@RequestBody ProjTemplateDto templateDto, @RequestParam("type") String type);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/queryEntity.do")
    PageList queryEntity(@RequestBody List<ConditionVO> conditionList, @RequestParam(value = "userName", required = false) String userName, @RequestParam(value = "orgId", required = false) String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/getLifeCycleStatusList.do")
    FeignJson getLifeCycleStatusList();

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/doSaveNewTemplate.do")
    FeignJson doSaveNewTemplate(@RequestParam(value = "templateId", required = false) String templateId, @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "remark", required = false) String remark, @RequestParam(value = "method", required = false) String method,
                                @RequestBody Map<String, Object> map, @RequestParam(value = "orgId", required = false) String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/getProjTemplateByBizId.do")
    ProjTemplateDto getProjTemplateByBizId(@RequestParam(value = "bizId", required = false) String bizId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/searchProjTemplateMenu.do")
    List<ProjTemplateMenuDto> searchProjTemplateMenu(@RequestBody ProjTemplateMenuDto projTemplateMenuDto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/backToVersion.do")
    FeignJson backToVersion(@RequestParam("id") String id, @RequestParam("bizId") String bizId, @RequestParam("type") String type,
                            @RequestBody TSUserDto userDto, @RequestParam("orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/getProjTemplateListByNameAndBizId.do")
    List<ProjTemplateDto> getProjTemplateListByNameAndBizId(@RequestParam("name") String name, @RequestParam("bizId") String bizId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/getProjTemplateListByName.do")
    List<ProjTemplateDto> getProjTemplateListByName(@RequestParam("name") String name);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/copyProjTemplate.do")
    FeignJson copyProjTemplate(@RequestBody ProjTemplateDto projTemplateDto, @RequestParam("templateId") String templateId,
                               @RequestParam("userId") String userId, @RequestParam("orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/getVersionHistoryAndCount.do")
    FeignJson getVersionHistoryAndCount(@RequestParam("bizId") String bizId, @RequestParam("pageSize") Integer pageSize,
                                        @RequestParam("pageNum") Integer pageNum);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/getProjTmpLibAuthLibTmpLinkByTemplateId.do")
    List<ProjTmpLibAuthLibTmpLinkDto> getProjTmpLibAuthLibTmpLinkByTemplateId(@RequestParam(value = "templateId", required = false) String templateId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/startProjTemplateProcess.do")
    FeignJson startProjTemplateProcess(@RequestParam(value = "templateId", required = false) String templateId, @RequestParam(value = "leader", required = false) String leader,
                                       @RequestParam(value = "deptLeader", required = false) String deptLeader, @RequestBody TSUserDto userDto, @RequestParam(value = "orgId", required = false) String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/completeProjTemplateProcess.do")
    FeignJson completeProjTemplateProcess(@RequestBody ProjTemplateDto projTemplate, @RequestParam(value = "userId", required = false) String userId, @RequestParam(value = "orgId", required = false) String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/searchProjTemplate.do")
    List<ProjTemplateDto> searchProjTemplate(@RequestBody ProjTemplateDto projTemplateDto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/searchAllProjTemplate.do")
    List<ProjTemplateDto> searchAllProjTemplate();

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/SaveAsTemplate.do")
    FeignJson SaveAsTemplate(@RequestBody Map<String, Object> map);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/getFolderTree.do")
    List<RepFileDto> getFolderTree(@RequestParam("projectId") String projectId, @RequestParam("havePower") String havePower, @RequestParam("userId") String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/getLibIdByTemplateId.do")
    FeignJson getLibIdByTemplateId(@RequestParam(value = "templateId", required = false) String templateId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/saveProjectTemplateDetailByExcel.do")
    FeignJson saveProjectTemplateDetailByExcel(@RequestBody Map<String, Object> objMap);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projTemplateRestController/getImportDataList.do")
    FeignJson getImportDataList(@RequestBody List<Map<String, Object>> map, @RequestParam(value = "userId", required = false) String userId, @RequestParam(value = "projectTemplateId", required = false) String projectTemplateId, @RequestParam(value = "orgId", required = false) String orgId);
}


