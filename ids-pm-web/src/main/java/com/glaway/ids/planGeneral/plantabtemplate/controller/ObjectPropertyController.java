package com.glaway.ids.planGeneral.plantabtemplate.controller;

import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.ids.planGeneral.plantabtemplate.service.ObjectPropertyServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title: Controller
 * @Description: 计划通用化--元数据属性
 * @author wuxing
 * @date 2019-08-27
 * @version V1.0
 */
@Controller
@RequestMapping("/objectPropertyController")
public class ObjectPropertyController {

    //元数据属性ServiceImpl
    @Autowired
    private ObjectPropertyServiceI objectPropertyServiceImpl;

    /**
     * 功能描述：跳转视图控件编辑选择数据查找页面
     * @return ModelAndView
     */
    @RequestMapping(params = "searchObjectPropertyPage")
    public ModelAndView searchObjectPropertyPage(HttpServletRequest request, String tabId) {
        request.setAttribute("tabId", tabId);
        return new ModelAndView("com/glaway/ids/planGeneral/planTabTemplate/searchObjectPropertyPage");
    }

    /**
     * 功能描述：根据页签模板Id查询所有数据
     * @param response
     * @param tabId
     */
    @RequestMapping(params = "getAllPropertyByTabId")
    public void getAllPropertyByTabId(String tabId, HttpServletResponse response) {
        objectPropertyServiceImpl.getAllPropertyByTabId(tabId, response);
    }

    /**
     * 功能描述：删除数据
     * @param id
     */
    @RequestMapping(params = "doDelete")
    @ResponseBody
    public AjaxJson doDelete(String id){
        return objectPropertyServiceImpl.doDelete(id);
    }
}
