package com.glaway.ids.config.fallback;

import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileDto;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileTypeDto;
import com.glaway.foundation.system.lifecycle.entity.LifeCycleStatus;
import com.glaway.ids.config.service.RepFileTypeConfigRemoteFeignServiceI;
import com.glaway.ids.project.menu.dto.ProjTemplateMenuDto;
import com.glaway.ids.project.projecttemplate.dto.ProjTemplateDto;
import com.glaway.ids.project.projecttemplate.dto.ProjTmpLibAuthLibTmpLinkDto;
import com.glaway.ids.project.projecttemplate.service.ProjTemplateRemoteFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProjTemplateRemoteFeignServiceCallBack implements FallbackFactory<ProjTemplateRemoteFeignServiceI> {

	@Override
	public ProjTemplateRemoteFeignServiceI create(Throwable throwable) {
		return new  ProjTemplateRemoteFeignServiceI(){

			@Override
			public boolean isPTOM(String userId) {
				return false;
			}

			@Override
			public FeignJson deleteTemplate(List<ProjTemplateDto> projTemplateDtos) {
				return null;
			}

			@Override
			public ProjTemplateDto getProjTemplateEntity(String id) {
				return null;
			}

			@Override
			public FeignJson openOrClose(ProjTemplateDto templateDto, String type) {
				return null;
			}

			@Override
			public PageList queryEntity(List<ConditionVO> conditionList, String userName, String orgId) {
				return null;
			}

			@Override
			public FeignJson getLifeCycleStatusList() {
				return null;
			}

			@Override
			public FeignJson doSaveNewTemplate(String templateId, String name, String remark, String method, Map<String, Object> map, String orgId) {
				return null;
			}

			@Override
			public ProjTemplateDto getProjTemplateByBizId(String bizId) {
				return null;
			}

			@Override
			public List<ProjTemplateMenuDto> searchProjTemplateMenu(ProjTemplateMenuDto projTemplateMenuDto) {
				return null;
			}

			@Override
			public FeignJson backToVersion(String id, String bizId, String type, TSUserDto userDto, String orgId) {
				return null;
			}

			@Override
			public List<RepFileDto> getFolderTree(String projectId, String havePower, String userId) {
				return null;
			}

			@Override
			public List<ProjTmpLibAuthLibTmpLinkDto> getProjTmpLibAuthLibTmpLinkByTemplateId(String templateId) {
				return null;
			}

			@Override
			public List<ProjTemplateDto> getProjTemplateListByNameAndBizId(String name, String bizId) {
				return null;
			}

			@Override
			public List<ProjTemplateDto> getProjTemplateListByName(String name) {
				return null;
			}

			@Override
			public FeignJson copyProjTemplate(ProjTemplateDto projTemplateDto, String templateId, String userId, String orgId) {
				return null;
			}

			@Override
			public FeignJson startProjTemplateProcess(String templateId, String leader, String deptLeader, TSUserDto userDto, String orgId) {
				return null;
			}

			@Override
			public List<ProjTemplateDto> searchProjTemplate(ProjTemplateDto projTemplateDto) {
				return null;
			}

			@Override
			public FeignJson completeProjTemplateProcess(ProjTemplateDto projTemplate, String userId, String orgId) {
				return null;
			}

			@Override
			public FeignJson getLibIdByTemplateId(String templateId) {
				return null;
			}

			@Override
			public FeignJson SaveAsTemplate(Map<String, Object> map) {
				return null;
			}

			@Override
			public FeignJson getVersionHistoryAndCount(String bizId, Integer pageSize, Integer pageNum) {
				return null;
			}

			@Override
			public FeignJson saveProjectTemplateDetailByExcel(Map<String, Object> objMap) {
				return null;
			}

			@Override
			public FeignJson getImportDataList(List<Map<String, Object>> map, String userId, String projectTemplateId, String orgId) {
				return new FeignJson();
			}

			@Override
			public List<ProjTemplateDto> searchAllProjTemplate() {
				return null;
			}
		};
	}
}

