package com.glaway.ids.config.service;


import java.util.List;
import java.util.Map;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.config.fallback.BusinessConfigServiceCallBack;
import com.glaway.ids.config.vo.BusinessConfig;
import com.glaway.foundation.core.common.hibernate.qbc.CriteriaQuery;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;

import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.ids.project.projectmanager.dto.BusinessConfigDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 配置业务接口
 * 
 * @author Administrator
 * @version 2015年3月26日
 * @see PlanBusinessConfigServiceI
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = BusinessConfigServiceCallBack.class)
public interface PlanBusinessConfigServiceI{
    
    /**
     * 获取comboboxList
     * 
     * @param bc
     * @return
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/getBusinessConfigsList.do")
    String getBusinessConfigsList(@RequestBody BusinessConfig bc);
    
    /**
     * 搜索启用的配置
     * 
     * @param bc
     * @return
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/searchUseableBusinessConfigs.do")
    List<BusinessConfig> searchUseableBusinessConfigs(@RequestBody BusinessConfig bc);

    /**
     * 根据业务配置属性搜索
     * 
     * @param bc
     * @return
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/searchBusinessConfigs.do")
    List<BusinessConfig> searchBusinessConfigs(@RequestBody BusinessConfig bc);
    
    /**
     * 根据具体名称获取配置
     * 
     * @param bc
     * @return 
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/getBusinessConfigsByDetailNames.do")
    List<BusinessConfig> getBusinessConfigsByDetailNames(@RequestBody BusinessConfig bc);
    
    /**
     * 根据业务配置属性分页搜索
     * 
     * @param bc
     * @param page
     * @param rows
     * @return
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/searchBusinessConfigsForPage.do")
    List<BusinessConfig> searchBusinessConfigsForPage(@RequestBody BusinessConfig bc, @RequestParam("page") int page, @RequestParam("rows") int rows);
    
    /**
     * 
     * @param bc
     * @param page
     * @param rows
     * @param notIn
     * @return
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/searchDeliverablesForPage.do")
    List<BusinessConfig> searchDeliverablesForPage(@RequestBody BusinessConfig bc, @RequestParam("page") int page, @RequestParam("rows") int rows, @RequestParam("notIn") String notIn);

    /**
     * 
     * @param bc
     * @param notIn
     * @return
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/getDeliverablesCount.do")
    long getDeliverablesCount(@RequestBody BusinessConfig bc, @RequestParam("notIn")String notIn);
    
    /**
     * 根据搜索条件获取记录总条数
     * 
     * @param bc
     * @return 
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/getSearchCount.do")
    long getSearchCount(@RequestBody BusinessConfig bc);
    
    /**
     * 增加业务配置
     * 
     * @param bc
     * @return 
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/add.do")
    BusinessConfig add(@RequestBody BusinessConfig bc);
    
    /**
     * 修改业务配置
     * 
     * @param bc
     * @return 
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/modify.do")
    BusinessConfig modify(@RequestBody BusinessConfig bc);
    
    /**
     * 逻辑删除
     * 
     * @param bc 
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/logicDelete.do")
    void logicDelete(@RequestBody BusinessConfig bc);
    
    
    /**
     * 启用/禁用
     * 
     * @param bc
     * @param type
     * @return 
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/startOrStop.do")
    BusinessConfig startOrStop(@RequestBody BusinessConfig bc, @RequestParam("type") String type);
    
    /**
     * 搜索
     * 
     * @param conditionList
     * @return 
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/queryEntity.do")
    PageList queryEntity(@RequestBody List<ConditionVO> conditionList, @RequestParam("isPage") boolean isPage);
    
    /**
     * Description: 
     * 
     * @param bcon
     * @return 
     * @see 
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/searchBusinessConfigAccurate.do")
    String searchBusinessConfigAccurate(@RequestBody BusinessConfig bcon);

    /**
     * 处理excel导入数据
     * 
     * @param dataFromExcel
     * @return 
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/doData.do")
    FeignJson doData(@RequestBody List<String> dataFromExcel, @RequestParam("configType") String configType,@RequestParam("userId") String userId,@RequestParam("orgId") String orgId);

    /**
     * 校验数据
     * 
     * @param row
     * @param strForBc
     * @param errorMsgMap
     * @return 
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/checkData.do")
    Map<String, String> checkData(@RequestParam("row") int row, @RequestParam("strForBc") String strForBc, @RequestBody Map<String, String> errorMsgMap);
    
    /**
     * 获取配置名
     * 
     * @param configTypeName
     * @return 
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/getConfigTypeName.do")
    FeignJson getConfigTypeName(@RequestParam("configTypeName") String configTypeName);
    
    /**
     * 批量删除
     * 
     * @param ids
     * @param msg
     * @return 
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/doBatchDel.do")
    String doBatchDel(@RequestParam("ids") String ids, @RequestParam("msg")String msg);
    
    /**
     * 批量更改状态
     * 
     * @param ids
     * @param state
     * @return 
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/doBatchStartOrStop.do")
    void doBatchStartOrStop(@RequestParam("ids")String ids,@RequestParam("state") String state);
    
    /**
     * 导入重名校验
     * 
     * @param names
     * @param errorMsgMap
     * @return 
     * @see
     */
/*    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/checkImportNames.do")
    Map<String, String> checkImportNames(@RequestBody List<String> names,@RequestBody Map<String, String> errorMsgMap);*/
    
    /**
     * 导入重编号校验
     * 
     * @param nos
     * @param errorMsgMap
     * @return 
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/checkImportNos.do")
    Map<String, String> checkImportNos(@RequestParam("nos") List<String> nos, @RequestBody Map<String, String> errorMsgMap);

    
    /**
     * 变更类别的查询
     *
     * @return 
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/searchTreeNode.do")
    List<BusinessConfig> searchTreeNode(@RequestBody BusinessConfig businessConfig);

    /**
     * 变更类别的查询
     *
     * @return 
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/getBusinessConfigParentList.do")
    void getBusinessConfigParentList(@RequestBody BusinessConfig targetNode,@RequestParam("allList") List<BusinessConfig> allList,
                                     @RequestParam("parentList") List<BusinessConfig> parentList);
    /**
     * 变更类别的查询
     *
     * @return 
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/getParentNode.do")
    BusinessConfig getParentNode(@RequestBody BusinessConfig epsConfig);
    /**
     * 根据项目配置的分类  获取该类别下最大位置的信息 属性字段的信息
     * @return
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/getMaxPlace.do")
    int getMaxPlace(@RequestParam("configType")String configType);
    
    /**
     * 根据项目配置的  获取不同配置下  改排序属性之后字段的所有配置的集合
     * @param rankQuality
     * @return
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/getListByAfter.do")
    List<BusinessConfig> getListByAfter(@RequestParam("configType")String rankQuality,@RequestParam("configType") String configType);
    
    /**
     * 更具配置的类别 获取相关配置下所有的子配置集合
     * @param bc
     * @return
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/getChildList.do")
    List<BusinessConfig>  getChildList(@RequestBody BusinessConfig bc);


    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/businessConfigSaveOrUpdate.do")
    void businessConfigSaveOrUpdate(@RequestBody BusinessConfig businessConfig);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/getDataGrid.do")
    void getDataGrid(@RequestBody CriteriaQuery cq, @RequestParam("flag") boolean flag);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/getBusinessConfig.do")
    BusinessConfig getBusinessConfig(@RequestParam("id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/getBusinessConfigListByConfigType.do")
    List<BusinessConfig> getBusinessConfigListByConfigType(@RequestParam("configType") String configType);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/getProjectPhaseList.do")
    String getProjectPhaseList();

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/getBusinessConfigEntity.do")
    BusinessConfigDto getBusinessConfigEntity(@RequestParam("id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planBusinessConfigController/searchAllBusinessConfigs.do")
    List<BusinessConfig> searchAllBusinessConfigs(@RequestParam(value = "configType",required = false) String configType);

}
