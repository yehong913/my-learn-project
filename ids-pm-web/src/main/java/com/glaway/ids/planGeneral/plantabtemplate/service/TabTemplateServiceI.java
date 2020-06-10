package com.glaway.ids.planGeneral.plantabtemplate.service;

import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.planGeneral.plantabtemplate.dto.ObjectPropertyInfoDto;
import com.glaway.ids.planGeneral.plantabtemplate.dto.TabTemplateDto;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * @Title: ServiceI
 * @Description: 页签模版管理ServiceI
 * @author wuxing
 * @date 2019-08-27
 * @version V1.0
 */
public interface TabTemplateServiceI {

    /**
     * 功能描述：根据查询条件展示列表(数据过滤)
     * @param conditionList
     */
    void searchDatagrid(List<ConditionVO> conditionList, HttpServletResponse response);

    /**
     * 功能描述：批量/单条 启用或禁用页签模版
     * @param ids id集合
     * @param status 状态(启用“1”或者禁用“0”)
     * @return AjaxJson
     */
    AjaxJson doStartOrStop(String ids, String status);

    /**
     * 功能描述：批量/单条 删除页签模版
     * @param ids id集合(“，”分隔)
     * @return AjaxJson
     */
    AjaxJson doBatchDelete(String ids);

    /**
     * 功能描述：数据保存
     * @param dto
     * @return AjaxJson
     */
    AjaxJson doSave(HttpServletRequest request, TabTemplateDto dto);

    List<List<List<ObjectPropertyInfoDto>>> goTabView(HttpServletRequest request, @RequestParam String id);


    /**
     * 功能描述：判断名称是否重复
     * @param name
     * @return AjaxJson
     */
    AjaxJson isRepeatName(String name, String id);

    List<List<List<List<ObjectPropertyInfoDto>>>> goTabsView(HttpServletRequest request, String id,String displayAccess,String fromShow,String typeIds);


    /**
     * 功能描述：根据ID查询数据
     * @param id
     * @return TabTemplateDto
     */
    TabTemplateDto queryInfoById(String id);

    /**
     * 功能描述：根据ID复制数据
     * @param id
     * @return TabTemplateDto
     */
    TabTemplateDto copyEntity(String id);


    /**
     * 功能描述：数据保存
     * @param dto
     * @return AjaxJson
     */
    AjaxJson doUpdateOrRevise(String updateOrRevise, TabTemplateDto dto);

    /**
     * 功能描述：撤回
     * @param params
     * @return FeignJson
     */
    FeignJson doRevoke(Map<String,String> params);

    /**
     * 功能描述：回退
     * @param params
     * @return FeignJson
     */
    FeignJson doBack(Map<String,String> params);

    /**
     * 功能描述：提交审批
     * @param params
     * @return FeignJson
     */
    FeignJson doSubmitApprove(Map<String,String> params);

    /**
     * 获取历史信息
     * @param bizId    版本id
     * @param pageSize 页码
     * @param pageNum  每页数量
     * @return
     */
    FeignJson getVersionDatagridStr(String bizId, Integer pageSize, Integer pageNum);
}
