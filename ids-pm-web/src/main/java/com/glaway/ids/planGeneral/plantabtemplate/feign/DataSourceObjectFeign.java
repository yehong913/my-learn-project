package com.glaway.ids.planGeneral.plantabtemplate.feign;

import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.planGeneral.plantabtemplate.callback.DataSourceObjectFeignCallBack;
import com.glaway.ids.planGeneral.plantabtemplate.dto.DataSourceObjectDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


/**
 * @Title: Feign
 * @Description: 数据源对象信息管理Feign
 * @author wuxing
 * @date 2019-08-27
 * @version V1.0
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = DataSourceObjectFeignCallBack.class)
public interface DataSourceObjectFeign {

    /**
     * 功能描述：根据查询条件展示列表
     * @param params 查询条件Map
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/dataSourceObjectRestController/searchDatagrid.do")
    PageList searchDatagrid(@RequestBody Map<String, String> params);

    /**
     * 功能描述：保存数据
     * @param dto
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/dataSourceObjectRestController/saveOrUpdate.do")
    DataSourceObjectDto saveOrUpdate(@RequestBody DataSourceObjectDto dto);

    /**
     * 功能描述：删除数据
     * @param id
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/dataSourceObjectRestController/doDelete.do")
    FeignJson doDelete(@RequestParam(value = "id",required = false) String id);

    /**
     * 功能描述：查询所有数据对象集合
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/dataSourceObjectRestController/getAllDataSourceObject.do")
    List<Map<String, String>> getAllDataSourceObject(@RequestParam(value = "tabId",required = false) String tabId);

    /**
     * 功能描述：根据主键Id查询数据
     * @param id
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/dataSourceObjectRestController/queryDataSourceObjectDtoById.do")
    DataSourceObjectDto queryDataSourceObjectDtoById(@RequestParam(value = "id",required = false) String id);

    /**
     * 功能描述：保存所有信息
     * @param requestMap key:dataSourceObjectList,objectPropertyList
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/dataSourceObjectRestController/saveAllInfo.do")
    FeignJson saveAllInfo(@RequestBody Map<String, Object> requestMap);

    /**
     * 功能描述：根据页签模板ID查询所有数据
     * @param tabId
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/dataSourceObjectRestController/getAllDataByTabId.do")
    List<DataSourceObjectDto> getAllDataByTabId(@RequestParam(value = "tabId",required = false) String tabId);

    /**
     * 功能描述：数据回滚
     * @param requestMap key:dataSourceObjectList,objectPropertyList
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/dataSourceObjectRestController/dataRollBack.do")
    FeignJson dataRollBack(@RequestBody Map<String, Object> requestMap);

    /**
     * 功能描述：保存所有信息
     * @param requestMap key:dataSourceObjectList,objectPropertyList
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/dataSourceObjectRestController/updateOrReviseInfo.do")
    FeignJson updateOrReviseInfo(@RequestBody Map<String, Object> requestMap);
}
