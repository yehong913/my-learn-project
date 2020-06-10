package com.glaway.ids.project.projectmanager.fallback;

import com.glaway.foundation.common.dto.TSRoleDto;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileAttachmentDto;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileDto;
import com.glaway.foundation.fdk.dev.dto.rep.RepRoleFileAuthDto;
import com.glaway.ids.project.projectmanager.dto.ProjDocRelationDto;
import com.glaway.ids.project.projectmanager.dto.ProjLibFileDto;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.dto.ProjectLibAuthTemplateLinkDto;
import com.glaway.ids.project.projectmanager.service.ProjLibRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.vo.ProjLibDocumentVo;
import com.glaway.ids.project.projectmanager.vo.ProjLibRoleFileAuthVo;
import com.glaway.ids.project.projectmanager.vo.RepFileAuthVo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProjLibRemoteFeignServiceCallBack implements FallbackFactory<ProjLibRemoteFeignServiceI> {

	@Override
	public ProjLibRemoteFeignServiceI create(Throwable throwable) {
		return new ProjLibRemoteFeignServiceI(){
			@Override
			public boolean isHidProjLibOperForDir(String id) {
				return false;
			}

			@Override
			public void initEntityAttrAdditionalAttribute(Map<String, Object> map, String entityAttrName, String entityAttrVal) {

			}

			@Override
			public Map<String, String> saveEntityAttrAdditionalAttributeVal(Map<String, Object> paramMap) {
				return null;
			}

			@Override
			public FeignJson updateVariablesAndTodoTask(String newFiledId, String procInstId, String docName) {
				return null;
			}

			@Override
			public FeignJson backVersion(String id, String bizId) {
				return null;
			}

			@Override
			public List<ProjLibDocumentVo> getFilesByBizId(String bizId) {
				return null;
			}

			@Override
			public void saveEntityAttrAdditionalAttribute(Map<String, Object> attrMap) {

			}

			@Override
			public boolean delFileAndAuthById(String folderId) {
				return false;
			}

			@Override
			public FeignJson updateFile(String method, ProjLibDocumentVo document, String id, String message, String userId) {
				return null;
			}

			@Override
			public List<ProjLibFileDto> getProjLibFile(String fileId) {
				return null;
			}

			@Override
			public FeignJson saveProjLibRoleFileAuth(String fileId, List<RepFileAuthVo> repFileAuthVoList, String userId) {
				return null;
			}

			@Override
			public String getCategoryFileAuths(String fileId, String userId) {
				return null;
			}

			@Override
			public Boolean checkNameExist(RepFileDto repFileDto) {
				return null;
			}

			@Override
			public FeignJson attachmentBatchDel(String ids) {
				return null;
			}

			@Override
			public Boolean checkRoleFileAuthExistChange(String fileId, List<RepFileAuthVo> repList) {
				return null;
			}

			@Override
			public List<ProjLibRoleFileAuthVo> getProjLibRoleFileAuths(String fileId) {
				return null;
			}

			@Override
			public Boolean delFileById(String fileId) {
				return null;
			}

			@Override
			public FeignJson beforeDelFolder(String folderId) {
				return new FeignJson();
			}

			@Override
			public PageList queryEntity(Map<String, Object> map, String folderId, String projectId, String createName, String modifiName, String docTypeId, String userId) {
				return null;
			}

			@Override
			public FeignJson doAddProjLibDoc(Map<String, Object> map, String docattachmentNames, String docattachmentURLs, String docSecurityLevelFroms, String type) {
				return null;
			}

			@Override
			public void updatePathRepFile(String parentId, String repFileIds) {

			}

			@Override
			public Map<String, List<RepRoleFileAuthDto>> getTemplateRoleAuths(String projectId, String templateId, List<TSRoleDto> roles) {
				return null;
			}

			@Override
			public FeignJson getDocumentFileAuths(String fileId, String userId) {
				return null;
			}

			@Override
			public FeignJson getDocNamePath(String folderId) {
				return null;
			}

			@Override
			public FeignJson doBatchDel(List<ProjLibDocumentVo> docVos, String datasStr) {
				return null;
			}

			@Override
			public Boolean isRootFolder(String fileId) {
				return null;
			}

			@Override
			public String getProjectIdByFileId(String folderId) {
				return null;
			}

			@Override
			public Boolean isHidProjLibOper(String projectId) {
				return null;
			}

			@Override
			public void updateFileAttachment(RepFileAttachmentDto fileAttachment) {

			}

			@Override
			public List<ProjLibDocumentVo> getVersionHistory(String bizId, Integer pageSize, Integer pageNum, boolean isPage) {
				return null;
			}

			@Override
			public FeignJson changeEachOtherForVo(String srcId, String destId) {
				return null;
			}

			@Override
			public FeignJson updateTreeOrderNum(String docId, String parentId) {
				return null;
			}

			@Override
			public Boolean checkCategoryNameExist(RepFileDto repFileDto) {
				return null;
			}

			@Override
			public List<ProjectLibAuthTemplateLinkDto> getProjectLibAuthTemplateLinkId(String projectId) {
				return null;
			}

			@Override
			public List<RepFileDto> getFolderTree(String projectId, String havePower, String userId) {
				return null;
			}

			@Override
			public ProjLibDocumentVo getProjDocmentVoById(String folderId) {
				return null;
			}

			@Override
			public List<ProjLibDocumentVo> getRepList(String fileId, String folderId, String projectId, String userId) {
				return null;
			}

			@Override
			public void addRepFileAttachment(RepFileAttachmentDto fileAttachment) {

			}

			@Override
			public String createFile(ProjLibDocumentVo document, String userId) {
				return null;
			}

			@Override
			public boolean validateReptDocNum(String docNumber) {
				return false;
			}

            @Override
            public List<ProjDocRelationDto> getAllDocRelationList() {
                // TODO Auto-generated method stub
                return null;
            }

			@Override
			public FeignJson applyTemplate(String projectId, Map<String, List<RepRoleFileAuthDto>> map, String templateId, String userId, String orgId) {
				return null;
			}

			@Override
			public FeignJson submitProcess(Map<String, String> params) {
				return new FeignJson();
			}

			@Override
			public List<ProjDocRelationDto> getDocRelation(String quoteId) {
				return null;
			}

			@Override
			public FeignJson getfolderIdByDeliverableId(String deliverableId,String projectId) {
				return new FeignJson();
			}


		};
	}

}



