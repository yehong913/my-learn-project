package com.glaway.ids.project.projectmanager.fallback;

import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.vo.TreeNode;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileAttachmentDto;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileDto;
import com.glaway.ids.config.dto.ProjectLibTemplateDto;
import com.glaway.ids.config.dto.ProjectLibTemplateFileCategoryDto;
import com.glaway.ids.project.projectmanager.dto.ProjectLibAuthTemplateLinkDto;
import com.glaway.ids.project.projectmanager.service.ProjLibRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectLibTemplateRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.vo.ProjLibDocumentVo;
import com.glaway.ids.project.projectmanager.vo.ProjLibRoleFileAuthVo;
import com.glaway.ids.project.projectmanager.vo.RepFileAuthVo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProjectLibTemplateRemoteFeignServiceCallBack implements FallbackFactory<ProjectLibTemplateRemoteFeignServiceI> {

	@Override
	public ProjectLibTemplateRemoteFeignServiceI create(Throwable throwable) {
		return new ProjectLibTemplateRemoteFeignServiceI(){
			@Override
			public List<ProjectLibTemplateDto> getAllUseProjectLibTemplate() {
				return null;
			}

			@Override
			public ProjectLibTemplateDto getProjLibTemplateEntity(String templateId) {
				return null;
			}

			@Override
			public FeignJson getTemplateCategoryRootNodeId(String templateId) {
				return null;
			}

			@Override
			public List<ProjLibRoleFileAuthVo> getProjLibTemplateRoleFileAuths(String fileId) {
				return null;
			}

			@Override
			public FeignJson deleteProjectLibTemplateByIds(String ids) {
				return null;
			}

			@Override
			public PageList queryProjectLibTemplates(Map<String, Object> map) {
				return null;
			}

			@Override
			public FeignJson startOrStopTemplateByIds(String ids, String status, TSUserDto userDto, String orgId) {
				return null;
			}

			@Override
			public FeignJson saveProjectLibTemplate(String name, String remark, TSUserDto userDto, String orgId) {
				return null;
			}

			@Override
			public boolean checkRoleFileAuthExistChange(String templateId, String fileId, List<RepFileAuthVo> repFileAuthVoList) {
				return false;
			}

			@Override
			public FeignJson saveProjLibRoleFileAuth(String templateId, String fileId, List<RepFileAuthVo> repFileAuthVoList, String userId, String orgId) {
				return null;
			}

			@Override
			public FeignJson doAddTreeNode(String name, String parentId, String templateId, TSUserDto userDto, String orgId) {
				return null;
			}

			@Override
			public ProjectLibTemplateFileCategoryDto getProjectLibTemplateCategoryEntity(String id) {
				return null;
			}

			@Override
			public FeignJson doUpdateTreeNode(String id, String name, TSUserDto userDto, String orgId) {
				return null;
			}

			@Override
			public boolean checkCategoryExistChildNode(String id) {
				return false;
			}

			@Override
			public FeignJson deleteProjectLibTemplateFileCategory(String id) {
				return null;
			}

			@Override
			public FeignJson doMoveNode(String id, String name, String targetId, String moveType, TSUserDto userDto, String orgId) {
				return null;
			}

			@Override
			public FeignJson doUpdateProjectLibTemplate(String templateId, String name, String remark, TSUserDto userDto, String orgId) {
				return null;
			}

			@Override
			public List<TreeNode> getProjectLibTemplateFileCategorys(String templateId) {
				return null;
			}
		};

	}

}



