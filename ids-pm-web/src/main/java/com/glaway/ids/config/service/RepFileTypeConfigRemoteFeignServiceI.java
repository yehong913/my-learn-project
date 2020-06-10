

package com.glaway.ids.config.service;

import java.util.List;
import java.util.Map;

import com.glaway.foundation.businessobject.attribute.dto.EntityAttributeAdditionalAttributeDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.common.pbmn.activity.dto.BpmnTaskDto;
import com.glaway.ids.config.fallback.RepFileTypeConfigServiceCallBack;

import com.glaway.foundation.fdk.dev.dto.rep.RepFileTypeDto;
import com.glaway.ids.config.vo.BpmnTaskVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * 文件名：RepFileTypeConfigRemoteFeignServiceI.java
 * 版权：Copyright by www.glaway.com
 * 描述：文档类型设置接口类
 * 修改人：zhousuxia
 * 修改时间：2018年7月26日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = RepFileTypeConfigServiceCallBack.class)
public interface RepFileTypeConfigRemoteFeignServiceI {


    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileTypeConfigRestController/getRepFileTypeConfigList.do")
    List<RepFileTypeDto> getRepFileTypeConfigList(@RequestParam(value = "repFileTypeConfigParentId",required = false) String repFileTypeConfigParentId,@RequestParam(value = "fileTypeCode",required = false) String fileTypeCode,
                                                         @RequestParam(value = "fileTypeName",required = false) String fileTypeName ,@RequestParam(value = "entrance",required = false)String entrance, @RequestParam(value = "docTypeId",required = false)String docTypeId);


    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileTypeConfigRestController/checkFileTypeCodeBeforeSave.do")
    Boolean checkFileTypeCodeBeforeSave(@RequestParam("parentId") String parentId, @RequestParam("repFileTypeId") String repFileTypeId,
                                        @RequestParam("fileTypeCode") String fileTypeCode);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileTypeConfigRestController/saveRepFileTypeConfig.do")
    FeignJson saveRepFileTypeConfig(@RequestParam("repFileTypeId") String repFileTypeId, @RequestParam("fileTypeCode") String fileTypeCode,@RequestParam("fileTypeName") String fileTypeName,
                                    @RequestParam("generatorInfoId") String generatorInfoId, @RequestParam("description") String description,@RequestParam("userId") String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileTypeConfigRestController/deleteRepFileTypeConfig.do")
    FeignJson deleteRepFileTypeConfig(@RequestParam("ids") String ids);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileTypeConfigRestController/changeRepFileTypeStatus.do")
    FeignJson changeRepFileTypeStatus(@RequestParam("type") String type, @RequestParam("ids") String ids);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileTypeConfigRestController/doExportXls.do")
    void doExportXls(@RequestParam("fileTypeCode") String fileTypeCode,@RequestParam("fileTypeName") String fileTypeName);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileTypeConfigRestController/clearBpmnTaskVoList.do")
    void clearBpmnTaskVoList(@RequestParam("type") String type,@RequestParam("typeId") String typeId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileTypeConfigRestController/queryBpmnTaskList.do")
    List<BpmnTaskVo> queryBpmnTaskList(@RequestParam("originId") String originId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileTypeConfigRestController/addListToRedis.do")
    List<BpmnTaskVo> addListToRedis(@RequestParam("type") String type, @RequestParam("typeId") String typeId,@RequestBody List<BpmnTaskVo> taskVoList);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileTypeConfigRestController/isTaskNameRepeat.do")
    boolean isTaskNameRepeat(@RequestParam("type") String type, @RequestParam("typeId") String typeId,@RequestParam("name")  String name);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileTypeConfigRestController/addTaskVoToRedis.do")
    List<BpmnTaskVo> addTaskVoToRedis(@RequestParam("type") String type, @RequestParam("typeId") String typeId,@RequestBody BpmnTaskVo task);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileTypeConfigRestController/moveTaskVoById.do")
    List<BpmnTaskVo> moveTaskVoById(@RequestParam("type") String type,@RequestParam("typeId") String typeId, @RequestParam("ids") String ids,@RequestParam("moveType") String moveType);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileTypeConfigRestController/batchDeleteFromRedis.do")
    List<BpmnTaskVo> batchDeleteFromRedis(@RequestParam("type") String type, @RequestParam("typeId") String typeId, @RequestParam("ids") String ids);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileTypeConfigRestController/getFromRedis.do")
    List<BpmnTaskVo> getFromRedis(@RequestParam("type") String type,@RequestParam("id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileTypeConfigRestController/editEntityAttributeAdditionalAttribute.do")
    FeignJson editEntityAttributeAdditionalAttribute(@RequestParam("oldId") String oldId, @RequestBody List<EntityAttributeAdditionalAttributeDto> entityAttributeAdditionalAttributeList);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileTypeConfigRestController/deployTaskFlow.do")
    List<BpmnTaskVo> deployTaskFlow(@RequestParam("type") String type, @RequestParam("typeId") String typeId, @RequestParam("ids") String ids,
                                    @RequestParam("processName") String processName, @RequestParam("basePath") String basePath,@RequestBody TSUserDto userDto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileTypeConfigRestController/getCustomAttrMap.do")
    Map<String, String> getCustomAttrMap(@RequestBody Map<String, String> idAttrMap,@RequestParam("entityUri") String entityUri);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileTypeConfigRestController/getBpmnTaskList.do")
    List<BpmnTaskDto> getBpmnTaskList(@RequestParam(value = "originId",required = false) String originId);
    
}
