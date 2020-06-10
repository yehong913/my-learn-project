package com.glaway.ids.qualityTest.controller;

import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.qualityTest.dto.CofigFormTestDto;
import com.glaway.ids.qualityTest.feign.QualityTestFeignServiceI;
import com.glaway.ids.util.CommonInitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description: 〈一句话功能简述〉
 * @author:
 * @ClassName: QualityTestController
 * @Date: 2019/10/30-15:26
 * @since
 */
@Controller
@RequestMapping("/qualityTestController")
public class QualityTestController extends BaseController {

    @Autowired
    private QualityTestFeignServiceI qualityTestFeignService;

    @RequestMapping(params = "addQualityDataGrid")
    @ResponseBody
    public FeignJson addQualityDataGrid(HttpServletRequest request) {
        String useObjectId = request.getParameter("useObjectId");
        FeignJson j = qualityTestFeignService.addQualityDataGrid(useObjectId);
        return j;
    }

    @RequestMapping(params = "searchDataGrid")
    @ResponseBody
    public void searchDataGrid (HttpServletRequest request, HttpServletResponse response) {
        String useObjectId = request.getParameter("useObjectId");
        FeignJson j = qualityTestFeignService.searchDataGrid(useObjectId);
        Map<String,Object> result = j.getAttributes();
        String datagridStr = "{\"rows\":" + result.get("rows") + ",\"total\":" + result.get("total") + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

    @RequestMapping(params = "saveQualityDataGrid")
    @ResponseBody
    public FeignJson saveQualityDataGrid (HttpServletRequest request) {
        String planId = request.getParameter("planId");
        String useObjectId = request.getParameter("useObjectId");
        return qualityTestFeignService.saveQualityDataGrid(planId,useObjectId);
    }

    @RequestMapping(params = "saveFormTest")
    @ResponseBody
    public FeignJson saveFormTest(CofigFormTestDto formTestDto, HttpServletRequest request) {
        CommonInitUtil.initGLVDataForCreate(formTestDto);
        return qualityTestFeignService.saveFormTest(formTestDto);
    }

    @RequestMapping(params = "initQualityFormTest")
    @ResponseBody
    public FeignJson initQualityFormTest(HttpServletRequest request) {
        String planId = request.getParameter("planId");
        return qualityTestFeignService.getFormTestByPlanId(planId);
    }

    @RequestMapping(params = "searchList")
    @ResponseBody
    public void searchList (HttpServletRequest request, HttpServletResponse response) {
        String planId = request.getParameter("planId");
        FeignJson j = qualityTestFeignService.searchList(planId);
        Map<String,Object> result = j.getAttributes();
        String datagridStr = "{\"rows\":" + result.get("rows") + ",\"total\":" + result.get("total") + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

    @RequestMapping(params = "updateFormTest")
    @ResponseBody
    public FeignJson updateFormTest(CofigFormTestDto formTestDto, HttpServletRequest request) {
        CommonInitUtil.initGLVDataForUpdate(formTestDto);
        return qualityTestFeignService.updateFormTest(formTestDto);
    }
}
