package com.glaway.ids.config.fallback;

import com.glaway.foundation.businessobject.attribute.dto.EntityAttributeAdditionalAttributeDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.pbmn.activity.dto.BpmnTaskDto;
import com.glaway.ids.config.service.RepFileTypeConfigRemoteFeignServiceI;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileTypeDto;
import com.glaway.ids.config.vo.BpmnTaskVo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RepFileTypeConfigServiceCallBack implements FallbackFactory<RepFileTypeConfigRemoteFeignServiceI> {

	@Override
	public RepFileTypeConfigRemoteFeignServiceI create(Throwable throwable) {
		return new  RepFileTypeConfigRemoteFeignServiceI(){

			@Override
			public Boolean checkFileTypeCodeBeforeSave(String parentId, String repFileTypeId, String fileTypeCode) {
				return null;
			}

			@Override
			public List<BpmnTaskVo> deployTaskFlow(String type, String typeId, String ids, String processName, String basePath, TSUserDto userDto) {
				return null;
			}

			@Override
			public FeignJson editEntityAttributeAdditionalAttribute(String oldId, List<EntityAttributeAdditionalAttributeDto> entityAttributeAdditionalAttributeList) {
				return null;
			}

			@Override
			public List<BpmnTaskVo> getFromRedis(String type, String id) {
				return null;
			}

			@Override
			public List<BpmnTaskVo> moveTaskVoById(String type, String typeId, String ids, String moveType) {
				return null;
			}

			@Override
			public List<BpmnTaskVo> batchDeleteFromRedis(String type, String typeId, String ids) {
				return null;
			}

			@Override
			public List<BpmnTaskVo> addListToRedis(String type, String typeId, List<BpmnTaskVo> taskVoList) {
				return null;
			}

			@Override
			public boolean isTaskNameRepeat(String type, String typeId, String name) {
				return false;
			}

			@Override
			public List<BpmnTaskVo> addTaskVoToRedis(String type, String typeId, BpmnTaskVo task) {
				return null;
			}

			@Override
			public List<BpmnTaskVo> queryBpmnTaskList(String originId) {
				return null;
			}

			@Override
			public FeignJson changeRepFileTypeStatus(String type, String ids) {
				return null;
			}

			@Override
			public void clearBpmnTaskVoList(String type, String typeId) {

			}

			@Override
			public void doExportXls(String fileTypeCode, String fileTypeName) {

			}

			@Override
			public FeignJson deleteRepFileTypeConfig(String ids) {
				return null;
			}

			@Override
			public FeignJson saveRepFileTypeConfig(String repFileTypeId, String fileTypeCode, String fileTypeName, String generatorInfoId, String description, String userId) {
				return null;
			}

			@Override
			public List<RepFileTypeDto> getRepFileTypeConfigList(String repFileTypeConfigParentId, String fileTypeCode, String fileTypeName, String entrance, String docTypeId) {
				return null;
			}

			@Override
			public List<BpmnTaskDto> getBpmnTaskList(String originId) {
				return null;
			}

			@Override
			public Map<String, String> getCustomAttrMap(Map<String, String> idAttrMap, String entityUri) {
				return null;
			}
		};
	}
}

