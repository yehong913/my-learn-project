package com.glaway.ids.planGeneral.plantabtemplate.service.impl;

import com.alibaba.fastjson.JSON;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.planGeneral.plantabtemplate.dto.ObjectPropertyInfoDto;
import com.glaway.ids.planGeneral.plantabtemplate.feign.ObjectPropertyFeign;
import com.glaway.ids.planGeneral.plantabtemplate.service.ObjectPropertyServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @Title: ServiceImpl
 * @Description: 元数据属性ServiceImpl
 * @author wuxing
 * @date 2019-08-27
 * @version V1.0
 */
@Service("objectPropertyServiceI")
public class ObjectPropertyServiceImpl implements ObjectPropertyServiceI {

    //元数据属性Feign
    @Autowired
    private ObjectPropertyFeign objectPropertyFeign;

    /**
     * 功能描述：根据页签模板ID查询所有数据
     * @param response
     * @param tabId
     */
    @Override
    public void getAllPropertyByTabId(String tabId, HttpServletResponse response) {
        List<ObjectPropertyInfoDto> dtoList = objectPropertyFeign.getAllPropertyByTabId(tabId);
        String json = JSON.toJSON(dtoList).toString();
        json = json.replaceAll("false","\"否\"");
        json = json.replaceAll("true","\"是\"");
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 功能描述：删除数据
     * @param id
     */
    @Override
    public AjaxJson doDelete(String id) {
        AjaxJson ajaxJson = new AjaxJson();
        FeignJson feignJson = objectPropertyFeign.doDelete(id);
        if (feignJson.isSuccess()){
            ajaxJson.setMsg("数据删除成功");
            ajaxJson.setSuccess(true);
        }else{
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("数据删除失败");
        }
        return ajaxJson;
    }
}
