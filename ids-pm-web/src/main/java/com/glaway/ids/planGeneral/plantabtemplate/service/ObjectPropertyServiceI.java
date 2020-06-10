package com.glaway.ids.planGeneral.plantabtemplate.service;

import com.glaway.foundation.core.common.model.json.AjaxJson;

import javax.servlet.http.HttpServletResponse;


/**
 * @Title: ServiceI
 * @Description: 元数据属性ServiceI
 * @author wuxing
 * @date 2019-08-27
 * @version V1.0
 */
public interface ObjectPropertyServiceI {

    /**
     * 功能描述：根据页签模板ID查询所有数据
     * @param response
     * @param tabId
     */
    void getAllPropertyByTabId(String tabId, HttpServletResponse response);

    /**
     * 功能描述：删除数据
     * @param id
     */
    AjaxJson doDelete(String id);
}
