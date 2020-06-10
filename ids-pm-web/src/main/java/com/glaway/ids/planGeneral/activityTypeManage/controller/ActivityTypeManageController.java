package com.glaway.ids.planGeneral.activityTypeManage.controller;

import com.alibaba.fastjson.JSON;
import com.glaway.foundation.common.fdexception.FdException;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.system.serial.SerialNumberManager;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.planGeneral.activityTypeManage.dto.ActivityTypeManageDto;
import com.glaway.ids.planGeneral.activityTypeManage.feign.ActivityTypeManageFeign;
import com.glaway.ids.util.CodeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LHR on 2019/8/26.
 */
@Controller
@RequestMapping("/activityTypeManageController")
public class ActivityTypeManageController extends BaseController {

    @Autowired
    private ActivityTypeManageFeign activityTypeManageFeign;

    /**
     * 活动类型管理页面跳转
     *
     * @return
     */
    @RequestMapping(params = "activityTypeManagePage")
    public ModelAndView activityTypeManagePage() {
        return new ModelAndView("com/glaway/ids/planGeneral/activityTypeManage/activityTypeManagePage");
    }


    /**
     * easyuiAJAX请求数据
     *
     * @param req
     * @param response
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest req, HttpServletResponse response) {
        List<ConditionVO> conditionList = RequestMapUtil.getQueryCondition(req);
        String status = req.getParameter("status");
        if (status.equals("enable")||status.equals("disable")) {
            ConditionVO vo = new ConditionVO();
            vo.setCondition("like");
            vo.setKey("status");
            vo.setValue(status);
            conditionList.add(vo);
        }
        String type = req.getParameter("type");
        Map<String, String> params = new HashMap<String, String>();
        if (!CommonUtil.isEmpty(type)) {
            params.put("type", type);
        }
        FeignJson feignJson = new FeignJson();
        Map<String, Object> map = new HashMap<>();
        map.put("conditionList", conditionList);
        map.put("params", params);
        feignJson.setAttributes(map);
        FeignJson pageList = activityTypeManageFeign.queryEntity(feignJson);
        String datagridStr = (String) pageList.getObj();
        TagUtil.ajaxResponse(response, datagridStr);
    }

//    /**
//     * 专家信息页面
//     *
//     * @param activityTypeManage
//     * @return
//     * @see
//     */
//    @RequestMapping(params = "searchDatagrid")
//    public void searchDatagrid(HttpServletRequest request, HttpServletResponse response,
//                               ActivityTypeManageDto activityTypeManage) {
//        Map<String, String> params = new HashMap<String, String>();
//        List<ConditionVO> conditionList = RequestMapUtil.getQueryCondition(request);
//        for (ConditionVO conditionVO : conditionList) {
//            params.put(conditionVO.getKey(), conditionVO.getValue());
//        }
//        PageList pageList = expertFeignServiceImpl.queryEntity1(conditionList);
//        long count = pageList.getCount();
//        String datagridStr = "{\"rows\":" + JSON.toJSONString(pageList.getResultList())
//                + ",\"total\":" + count + "}";
//        List<ExpertVo> li = pageList.getResultList();
//        TagUtil.ajaxResponse(response, datagridStr);
//    }

    /**
     * 跳转到新增页面
     *
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (StringUtils.isNotEmpty(id)){
            ActivityTypeManageDto activityTypeManage = activityTypeManageFeign.queryActivityTypeManageById(id);
            request.setAttribute("activity", activityTypeManage);
        }
        return new ModelAndView("com/glaway/ids/planGeneral/activityTypeManage/activityTypeManage-add");
    }

    /**
     * 活动类型新增保存
     *
     * @return
     * @see
     */

    @SuppressWarnings("finally")
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(HttpServletResponse response, HttpServletRequest request) {
        String userId = UserUtil.getCurrentUser().getId();
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        String id = request.getParameter("id");

        FeignJson feignJson = activityTypeManageFeign.doAddActivityTypeManage(userId, name, remark, id);

        AjaxJson ajaxJson = CodeUtils.tranFeignJsonToAjaxJson(feignJson);

        return ajaxJson;
    }

    /**
     * 活动类型批量删除
     *
     * @param response
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDeleteBatch")
    @ResponseBody
    public AjaxJson doDeleteBatch(HttpServletResponse response, HttpServletRequest request) {
        String userId = UserUtil.getCurrentUser().getId();
        String ids = request.getParameter("ids");

        FeignJson feignJson = activityTypeManageFeign.doDeleteBatch(userId, ids);

        AjaxJson ajaxJson = CodeUtils.tranFeignJsonToAjaxJson(feignJson);

        return ajaxJson;
    }

    /**
     * 活动类型批量删除前的校验
     *
     * @param response
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "deleteBatchBeforeCheckDate")
    @ResponseBody
    public AjaxJson deleteBatchBeforeCheckDate(HttpServletResponse response, HttpServletRequest request) {
        String ids = request.getParameter("ids");
        FeignJson feignJson = activityTypeManageFeign.deleteBatchBeforeCheckDate(ids);
        AjaxJson ajaxJson = CodeUtils.tranFeignJsonToAjaxJson(feignJson);
        return ajaxJson;
    }
    

    /**
     * 活动类型批量启动/禁用
     *
     * @param response
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doStartOrStop")
    @ResponseBody
    public AjaxJson doStartOrStop(HttpServletResponse response, HttpServletRequest request) {
        String userId = UserUtil.getCurrentUser().getId();
        String ids = request.getParameter("ids");
        String status = request.getParameter("status");

        FeignJson feignJson = activityTypeManageFeign.doStartOrStop(userId, ids, status);

        AjaxJson ajaxJson = CodeUtils.tranFeignJsonToAjaxJson(feignJson);

        return ajaxJson;
    }


    /**
     * 活动类型批量启动/禁用
     *
     * @param response
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDelete")
    @ResponseBody
    public AjaxJson doDelete(HttpServletResponse response, HttpServletRequest request) {
        String userId = UserUtil.getCurrentUser().getId();
        String id = request.getParameter("id");

        FeignJson feignJson = activityTypeManageFeign.doDeleteBatch(userId, id);

        AjaxJson ajaxJson = CodeUtils.tranFeignJsonToAjaxJson(feignJson);

        return ajaxJson;
    }
}
