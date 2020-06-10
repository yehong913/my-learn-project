/*
 * 文件名：ProjLibService.java
 * 版权：Copyright by www.glaway.com
 * 描述：项目库接口
 * 修改人：wangshen
 * 修改时间：2015年5月17日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.project.projectmanager.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.glaway.foundation.businessobject.attribute.dto.EntityAttributeAdditionalAttributeDto;
import com.glaway.foundation.common.dto.TSRoleDto;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileAttachmentDto;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileDto;
import com.glaway.foundation.fdk.dev.dto.rep.RepRoleFileAuthDto;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.projectmanager.dto.ProjDocRelationDto;
import com.glaway.ids.project.projectmanager.dto.ProjLibFileDto;
import com.glaway.ids.project.projectmanager.dto.ProjectLibAuthTemplateLinkDto;
import com.glaway.ids.project.projectmanager.fallback.ProjLibRemoteFeignServiceCallBack;
import com.glaway.ids.project.projectmanager.fallback.ProjectRemoteFeignServiceCallBack;

import com.glaway.foundation.common.entity.GLObject;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.ids.project.projectmanager.vo.ProjLibDocumentVo;
import com.glaway.ids.project.projectmanager.vo.ProjLibRoleFileAuthVo;
import com.glaway.ids.project.projectmanager.vo.RepFileAuthVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author xxzhang
 * @version 2016年3月1日
 * @see ProjLibServiceI
 * @since
 */
/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author xxzhang
 * @version 2016年3月1日
 * @see ProjLibRemoteFeignServiceI
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = ProjLibRemoteFeignServiceCallBack.class)
public interface ProjLibRemoteFeignServiceI {


    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/validateReptDocNum.do")
    boolean validateReptDocNum(@RequestParam(value = "docNumber",required = false) String docNumber);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/createFile.do")
    String createFile(@RequestBody ProjLibDocumentVo document,@RequestParam("userId") String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/addRepFileAttachment.do")
    void addRepFileAttachment(@RequestBody RepFileAttachmentDto fileAttachment);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/updateFileAttachment.do")
    void updateFileAttachment(@RequestBody RepFileAttachmentDto fileAttachment);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/getRepList.do")
    List<ProjLibDocumentVo> getRepList(@RequestParam("fileId") String fileId, @RequestParam("folderId") String folderId,@RequestParam("projectId") String projectId,@RequestParam("userId") String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/isHidProjLibOperForDir.do")
    boolean isHidProjLibOperForDir(@RequestParam("id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/getProjDocmentVoById.do")
    ProjLibDocumentVo getProjDocmentVoById(@RequestParam(value = "folderId",required = false)  String folderId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/getProjectIdByFileId.do")
    String getProjectIdByFileId(@RequestParam("folderId") String folderId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/getFolderTree.do")
    List<RepFileDto> getFolderTree(@RequestParam("projectId") String projectId, @RequestParam("havePower") String havePower, @RequestParam("userId") String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/getProjectLibAuthTemplateLinkId.do")
    List<ProjectLibAuthTemplateLinkDto> getProjectLibAuthTemplateLinkId(@RequestParam("projectId") String projectId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/checkCategoryNameExist.do")
    Boolean checkCategoryNameExist(@RequestBody RepFileDto repFileDto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/isRootFolder.do")
    Boolean isRootFolder(@RequestParam("fileId") String fileId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/delFileById.do")
    Boolean delFileById(@RequestParam("fileId") String fileId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/beforeDelFolder.do")
    FeignJson beforeDelFolder(@RequestParam(value = "folderId",required = false) String folderId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/isHidProjLibOper.do")
    Boolean isHidProjLibOper(@RequestParam("projectId") String projectId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/getCategoryFileAuths.do")
    String getCategoryFileAuths(@RequestParam("fileId") String fileId,@RequestParam("userId") String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/getDocNamePath.do")
    FeignJson getDocNamePath(@RequestParam("folderId") String folderId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/doBatchDel.do")
    FeignJson doBatchDel(@RequestBody List<ProjLibDocumentVo> docVos, @RequestParam("datasStr") String datasStr);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/updatePathRepFile.do")
    void updatePathRepFile(@RequestParam("parentId") String parentId,@RequestParam("repFileIds") String repFileIds);


    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/queryEntity.do")
    PageList queryEntity(@RequestBody Map<String,Object> map,@RequestParam("folderId") String folderId,@RequestParam("projectId") String projectId,
                         @RequestParam("createName") String createName,@RequestParam("modifiName") String modifiName, @RequestParam("docTypeId") String docTypeId,@RequestParam("userId") String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/doAddProjLibDoc.do")
    FeignJson doAddProjLibDoc(@RequestBody Map<String,Object> map,@RequestParam("docattachmentNames") String docattachmentNames,
                              @RequestParam("docattachmentURLs") String docattachmentURLs,@RequestParam("docSecurityLevelFroms") String docSecurityLevelFroms,@RequestParam("type") String type);


    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/checkNameExist.do")
    Boolean checkNameExist(@RequestBody RepFileDto repFileDto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/getProjLibRoleFileAuths.do")
    List<ProjLibRoleFileAuthVo> getProjLibRoleFileAuths(@RequestParam("fileId") String fileId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/checkRoleFileAuthExistChange.do")
    Boolean checkRoleFileAuthExistChange(@RequestParam("fileId") String fileId,@RequestBody List<RepFileAuthVo> repList);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/saveProjLibRoleFileAuth.do")
    FeignJson saveProjLibRoleFileAuth(@RequestParam("fileId") String fileId, @RequestBody List<RepFileAuthVo> repFileAuthVoList,@RequestParam("userId") String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/attachmentBatchDel.do")
    FeignJson attachmentBatchDel(@RequestParam("ids") String ids);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/getProjLibFile.do")
    List<ProjLibFileDto> getProjLibFile(@RequestParam("fileId") String fileId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/updateFile.do")
    FeignJson updateFile(@RequestParam("method") String method, @RequestBody ProjLibDocumentVo document,@RequestParam("id") String id, @RequestParam("message") String message,@RequestParam("userId") String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/updateVariablesAndTodoTask.do")
    FeignJson updateVariablesAndTodoTask(@RequestParam("newFiledId") String newFiledId, @RequestParam("procInstId") String procInstId, @RequestParam("docName") String docName);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/getFilesByBizId.do")
    List<ProjLibDocumentVo> getFilesByBizId(@RequestParam("bizId") String bizId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/backVersion.do")
    FeignJson backVersion(@RequestParam("id") String id,@RequestParam("bizId") String bizId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/getAllDocRelationList.do")
    List<ProjDocRelationDto> getAllDocRelationList();

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/submitProcess.do")
    FeignJson submitProcess(@RequestBody Map<String,String> params);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/saveEntityAttrAdditionalAttribute.do")
    void saveEntityAttrAdditionalAttribute(@RequestBody Map<String,Object> attrMap);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/initEntityAttrAdditionalAttribute.do")
    void initEntityAttrAdditionalAttribute(@RequestBody Map<String,Object> map,@RequestParam("entityAttrName") String entityAttrName,@RequestParam("entityAttrVal") String entityAttrVal);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/saveEntityAttrAdditionalAttributeVal.do")
    Map<String,String> saveEntityAttrAdditionalAttributeVal(@RequestBody Map<String, Object> paramsMap);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/getDocRelation.do")
    List<ProjDocRelationDto> getDocRelation(@RequestParam(value = "quoteId",required = false) String quoteId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/getfolderIdByDeliverableId.do")
    FeignJson getfolderIdByDeliverableId(@RequestParam(value = "deliverableId",required = false) String deliverableId,@RequestParam(value = "projectId",required = false) String projectId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/changeEachOtherForVo.do")
    FeignJson changeEachOtherForVo(@RequestParam(value = "srcId",required = false) String srcId,@RequestParam(value = "destId",required = false) String destId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/updateTreeOrderNum.do")
    FeignJson updateTreeOrderNum(@RequestParam(value = "docId",required = false) String docId,@RequestParam(value = "parentId",required = false) String parentId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/delFileAndAuthById.do")
    boolean delFileAndAuthById(@RequestParam(value = "folderId",required = false) String folderId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/getTemplateRoleAuths.do")
    Map<String, List<RepRoleFileAuthDto>> getTemplateRoleAuths(@RequestParam(value = "projectId",required = false) String projectId,
                                                               @RequestParam(value = "templateId",required = false) String templateId,
                                                               @RequestBody List<TSRoleDto> roles);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/applyTemplate.do")
    FeignJson applyTemplate(@RequestParam(value = "projectId",required = false) String projectId, @RequestBody Map<String, List<RepRoleFileAuthDto>> map,
                            @RequestParam(value = "templateId",required = false) String templateId,
                            @RequestParam(value = "userId",required = false) String userId,@RequestParam(value = "orgId",required = false) String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/getVersionHistory.do")
    List<ProjLibDocumentVo> getVersionHistory(@RequestParam(value = "bizId",required = false) String bizId, @RequestParam(value = "pageSize",required = false) Integer pageSize,
                                              @RequestParam(value = "pageNum",required = false) Integer pageNum, @RequestParam(value = "isPage",required = false) boolean isPage);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projLibRestController/getDocumentFileAuths.do")
    FeignJson getDocumentFileAuths(@RequestParam(value = "fileId",required = false) String fileId,@RequestParam(value = "userId",required = false) String userId);
}
