package com.glaway.ids.planGeneral.plantabtemplate.feign;

import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.planGeneral.plantabtemplate.callback.TabTemplateFeignCallBack;
import com.glaway.ids.planGeneral.plantabtemplate.dto.DataSourceObjectDto;
import com.glaway.ids.planGeneral.plantabtemplate.dto.ObjectPropertyInfoDto;
import com.glaway.ids.planGeneral.plantabtemplate.dto.TabTemplateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Title: Feign
 * @Description: 页签模版管理Feign
 * @author wuxing
 * @date 2019-08-27
 * @version V1.0
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE ,fallbackFactory = TabTemplateFeignCallBack.class)
public interface TabTemplateFeign {

    /**
     * 功能描述：根据查询条件展示列表
     * @param params 查询条件Map
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabTemplateRestController/searchDatagrid.do")
    PageList searchDatagrid(@RequestBody Map<String, String> params);

    /**
     * 功能描述：批量/单条 启用或禁用页签模版
     * @param ids id集合
     * @param status 状态(启用“1”或者禁用“0”)
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabTemplateRestController/doStartOrStop.do")
    FeignJson doStartOrStop(@RequestParam(value = "ids",required = false) String ids,
                            @RequestParam(value = "status",required = false) String status);

    /**
     * 功能描述：批量/单条 删除页签模版
     * @param ids id集合(“，”分隔)
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabTemplateRestController/doBatchDelete.do")
    FeignJson doBatchDelete(@RequestParam(value = "ids",required = false) String ids);

    /**
     * 功能描述：数据保存
     * @param dto
     * @return dto
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabTemplateRestController/doSave.do")
    TabTemplateDto doSave(@RequestBody TabTemplateDto dto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabTemplateRestController/queryDataSourceByTabId.do")
    List<DataSourceObjectDto> queryDataSourceByTabId(@RequestParam(value = "id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabTemplateRestController/queryObjectPropertyInfoDtoTabId.do")
    List<ObjectPropertyInfoDto> queryObjectPropertyInfoDtoTabId(@RequestParam(value = "id") String id);

    /**
     * 功能描述：根据ID查询数据
     * @param id
     * @return id
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabTemplateRestController/queryTabTemplate.do")
    TabTemplateDto queryTabTemplate(@RequestParam(value = "id",required = false) String id);

    /**
     * 功能描述：判断名称是否重复
     * @param name
     * @return AjaxJson
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabTemplateRestController/isRepeatName.do")
    boolean isRepeatName(@RequestParam(value = "name",required = false) String name,
                         @RequestParam(value = "id",required = false) String id);

    /**
     * 功能描述：根据ID复制数据
     * @param id
     * @return TabTemplateDto
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabTemplateRestController/copyEntity.do")
    TabTemplateDto copyEntity(@RequestParam(value = "id",required = false) String id);

    /**
     * 获取所有启动的未删除的页签模板
     * @return
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabTemplateRestController/getAllTabTemplates.do")
    List<TabTemplateDto> getAllTabTemplates(@RequestParam(value = "avaliable",required = false) String avaliable,
                                            @RequestParam(value = "stopFlag",required = false) String stopFlag);

    /**
     * 功能描述：数据保存
     * @param dto
     * @return dto
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabTemplateRestController/doUpdateOrRevise.do")
    TabTemplateDto doUpdateOrRevise(@RequestBody TabTemplateDto dto,@RequestParam(value = "userId",required = false) String userId,
                                    @RequestParam(value = "updateOrRevise",required = false) String updateOrRevise);

    /**
     * 功能描述：回退
     * @param params
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabTemplateRestController/doBack.do")
    FeignJson doBack(@RequestBody Map<String, String> params);

    /**
     * 功能描述：提交审批
     * @param params
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabTemplateRestController/doSubmitApprove.do")
    FeignJson doSubmitApprove(@RequestBody Map<String, String> params);

    /**
     * 获取历史信息
     * @param bizId    版本id
     * @param pageSize 页码
     * @param pageNum  每页数量
     * @return
     */
    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabTemplateRestController/getVersionDatagridStr.do")
    FeignJson getVersionDatagridStr(@RequestParam(value = "bizId",required = false) String bizId, @RequestParam("pageSize") Integer pageSize,
                                    @RequestParam("pageNum") Integer pageNum);
}
