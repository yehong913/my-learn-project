package com.glaway.ids.project.projectmanager.service;


import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.vo.TreeNode;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.config.dto.ProjectLibTemplateDto;
import com.glaway.ids.config.dto.ProjectLibTemplateFileCategoryDto;
import com.glaway.ids.project.projectmanager.fallback.ProjLibRemoteFeignServiceCallBack;
import com.glaway.ids.project.projectmanager.fallback.ProjectLibTemplateRemoteFeignServiceCallBack;
import com.glaway.ids.project.projectmanager.vo.ProjLibRoleFileAuthVo;
import com.glaway.ids.project.projectmanager.vo.RepFileAuthVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


/**
 * 项目库权限模板处理
 * 
 * @author blcao
 * @version 2016年6月29日
 * @see ProjectLibTemplateRemoteFeignServiceI
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = ProjectLibTemplateRemoteFeignServiceCallBack.class)
public interface ProjectLibTemplateRemoteFeignServiceI {


    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectLibTemplateRestController/getAllUseProjectLibTemplate.do")
    List<ProjectLibTemplateDto> getAllUseProjectLibTemplate();

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectLibTemplateRestController/getProjLibTemplateEntity.do")
    ProjectLibTemplateDto getProjLibTemplateEntity(@RequestParam("templateId") String templateId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectLibTemplateRestController/getTemplateCategoryRootNodeId.do")
    FeignJson getTemplateCategoryRootNodeId(@RequestParam("templateId") String templateId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectLibTemplateRestController/getProjLibTemplateRoleFileAuths.do")
    List<ProjLibRoleFileAuthVo> getProjLibTemplateRoleFileAuths(@RequestParam(value = "fileId",required = false)  String fileId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectLibTemplateRestController/getProjectLibTemplateFileCategorys.do")
    List<TreeNode> getProjectLibTemplateFileCategorys(@RequestParam("templateId") String templateId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectLibTemplateRestController/deleteProjectLibTemplateByIds.do")
    FeignJson deleteProjectLibTemplateByIds(@RequestParam("ids") String ids);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectLibTemplateRestController/queryProjectLibTemplates.do")
    PageList queryProjectLibTemplates(@RequestBody Map<String,Object> map);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectLibTemplateRestController/startOrStopTemplateByIds.do")
    FeignJson startOrStopTemplateByIds(@RequestParam("ids") String ids, @RequestParam("status") String status, @RequestBody TSUserDto userDto,@RequestParam("orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectLibTemplateRestController/saveProjectLibTemplate.do")
    FeignJson saveProjectLibTemplate(@RequestParam("name") String name,@RequestParam(value = "remark",required = false) String remark,@RequestBody TSUserDto userDto,@RequestParam("orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectLibTemplateRestController/checkRoleFileAuthExistChange.do")
    boolean checkRoleFileAuthExistChange(@RequestParam(value = "templateId",required = false) String templateId, @RequestParam(value = "fileId",required = false) String fileId,
                                         @RequestBody  List<RepFileAuthVo> repFileAuthVoList);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectLibTemplateRestController/saveProjLibRoleFileAuth.do")
    FeignJson saveProjLibRoleFileAuth(@RequestParam(value = "templateId",required = false) String templateId,@RequestParam(value = "fileId",required = false)  String fileId,
                                      @RequestBody List<RepFileAuthVo> repFileAuthVoList,@RequestParam(value = "userId",required = false) String userId,@RequestParam(value = "orgId",required = false) String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectLibTemplateRestController/doAddTreeNode.do")
    FeignJson doAddTreeNode(@RequestParam(value = "name",required = false) String name,@RequestParam(value = "parentId",required = false) String parentId,
                            @RequestParam(value = "templateId",required = false) String templateId,@RequestBody TSUserDto userDto,@RequestParam(value = "orgId",required = false) String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectLibTemplateRestController/getProjectLibTemplateCategoryEntity.do")
    ProjectLibTemplateFileCategoryDto getProjectLibTemplateCategoryEntity(@RequestParam("id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectLibTemplateRestController/doUpdateTreeNode.do")
    FeignJson doUpdateTreeNode(@RequestParam("id") String id,@RequestParam("name") String name,@RequestBody TSUserDto userDto,@RequestParam("orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectLibTemplateRestController/checkCategoryExistChildNode.do")
    boolean checkCategoryExistChildNode(@RequestParam("id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectLibTemplateRestController/deleteProjectLibTemplateFileCategory.do")
    FeignJson deleteProjectLibTemplateFileCategory(@RequestParam("id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectLibTemplateRestController/doMoveNode.do")
    FeignJson doMoveNode(@RequestParam(value = "id",required = false) String id,@RequestParam(value = "name",required = false) String name,@RequestParam(value = "targetId",required = false) String targetId,
                         @RequestParam(value = "moveType",required = false) String moveType,@RequestBody TSUserDto userDto,@RequestParam(value = "orgId",required = false) String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectLibTemplateRestController/doUpdateProjectLibTemplate.do")
    FeignJson doUpdateProjectLibTemplate(@RequestParam(value = "templateId",required = false) String templateId,@RequestParam(value = "name",required = false) String name,
                                         @RequestParam(value = "remark",required = false) String remark,@RequestBody TSUserDto userDto,@RequestParam(value = "orgId",required = false) String orgId);

}
