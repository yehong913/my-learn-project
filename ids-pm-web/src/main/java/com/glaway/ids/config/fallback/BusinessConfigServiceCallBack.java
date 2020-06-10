package com.glaway.ids.config.fallback;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.config.service.PlanBusinessConfigServiceI;
import com.glaway.ids.config.vo.BusinessConfig;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.hibernate.qbc.CriteriaQuery;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.ids.project.projectmanager.dto.BusinessConfigDto;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BusinessConfigServiceCallBack implements FallbackFactory<PlanBusinessConfigServiceI> {

	@Override
	public PlanBusinessConfigServiceI create(Throwable throwable) {
		return new  PlanBusinessConfigServiceI(){

			@Override
			public String getBusinessConfigsList(BusinessConfig bc) {
				return null;
			}

			@Override
			public List<BusinessConfig> searchUseableBusinessConfigs(BusinessConfig bc) {
				return null;
			}

			@Override
			public List<BusinessConfig> searchBusinessConfigs(BusinessConfig bc) {
				return null;
			}

			@Override
			public List<BusinessConfig> getBusinessConfigsByDetailNames(BusinessConfig bc) {
				return null;
			}

			@Override
			public List<BusinessConfig> searchBusinessConfigsForPage(BusinessConfig bc, int page, int rows) {
				return null;
			}

			@Override
			public List<BusinessConfig> searchDeliverablesForPage(BusinessConfig bc, int page, int rows, String notIn) {
				return null;
			}

			@Override
			public List<BusinessConfig> searchAllBusinessConfigs(String configType) {
				return null;
			}

			@Override
			public BusinessConfigDto getBusinessConfigEntity(String id) {
				return null;
			}

			@Override
			public long getDeliverablesCount(BusinessConfig bc, String notIn) {
				return 0;
			}

			@Override
			public long getSearchCount(BusinessConfig bc) {
				return 0;
			}

			@Override
			public BusinessConfig add(BusinessConfig bc) {
				return null;
			}

			@Override
			public BusinessConfig modify(BusinessConfig bc) {
				return null;
			}

			@Override
			public void logicDelete(BusinessConfig bc) {

			}

			@Override
			public BusinessConfig startOrStop(BusinessConfig bc, String type) {
				return null;
			}

			@Override
			public PageList queryEntity(List<ConditionVO> conditionList, boolean isPage) {
				return null;
			}

			@Override
			public String searchBusinessConfigAccurate(BusinessConfig bcon) {
				return null;
			}

			@Override
			public FeignJson doData(List<String> dataFromExcel, String configType, String userId, String orgId) {
				return new FeignJson();
			}

			@Override
			public Map<String, String> checkData(int row, String strForBc, Map<String, String> errorMsgMap) {
				return null;
			}

			@Override
			public FeignJson getConfigTypeName(String configTypeName) {
				return null;
			}

			@Override
			public String doBatchDel(String ids, String msg) {
				return null;
			}

			@Override
			public void doBatchStartOrStop(String ids, String state) {

			}

			@Override
			public Map<String, String> checkImportNos(List<String> nos, Map<String, String> errorMsgMap) {
				return null;
			}

			@Override
			public List<BusinessConfig> searchTreeNode(BusinessConfig businessConfig) {
				return null;
			}

			@Override
			public void getBusinessConfigParentList(BusinessConfig targetNode, List<BusinessConfig> allList, List<BusinessConfig> parentList) {

			}

			@Override
			public BusinessConfig getParentNode(BusinessConfig epsConfig) {
				return null;
			}

			@Override
			public int getMaxPlace(String configType) {
				return 0;
			}

			@Override
			public List<BusinessConfig> getListByAfter(String rankQuality, String configType) {
				return null;
			}

			@Override
			public List<BusinessConfig> getChildList(BusinessConfig bc) {
				return null;
			}

			@Override
			public void businessConfigSaveOrUpdate(BusinessConfig businessConfig) {

			}

			@Override
			public void getDataGrid(CriteriaQuery cq, boolean flag) {

			}

			@Override
			public BusinessConfig getBusinessConfig(String id) {
				return null;
			}

			@Override
			public List<BusinessConfig> getBusinessConfigListByConfigType(String configType) {
				return null;
			}

			@Override
			public String getProjectPhaseList() {
				return null;
			}

		};
	}
}

