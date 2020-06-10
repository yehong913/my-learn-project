package com.glaway.ids.project.projectmanager.controller;


/*
 * 文件名：ProjLogController.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：wangshen
 * 修改时间：2015年4月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.core.common.model.json.DataGrid;
import com.glaway.foundation.fdk.dev.service.threemember.FeignUserService;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.ids.project.projectmanager.dto.ProjLogDto;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.service.ProjLogRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.glaway.foundation.common.util.POIExcelUtil;
import com.glaway.foundation.common.util.UserUtil;
import com.glaway.foundation.common.util.param.ExcelVo;


@Controller
@RequestMapping("/projLogController")
public class ProjLogController {

    @Autowired
    private ProjectRemoteFeignServiceI projectService;

    @Autowired
    private ProjLogRemoteFeignServiceI projLogService;

    @Autowired
    private FeignUserService userService;

    /**
     * 跳转项目日志页面
     *
     * @return
     */
    @RequestMapping(params = "goProjectLog")
    public ModelAndView goProjectLog(Project project, HttpServletRequest req) {
        req.setAttribute("id", project.getId());
        return new ModelAndView("com/glaway/ids/pm/project/projectmanager/projLogList");
    }



    /**
     * 项目日志
     *
     * @return
     */
    @RequestMapping(params = "queryProjectLog")
    public void queryProjectLog(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        String page = StringUtils.defaultString(req.getParameter("page"), "0");
        String rows = StringUtils.defaultString(req.getParameter("rows"),"10");
        List<ProjLogDto> projLogList = null;
        if (StringUtils.isNotEmpty(id)) {
            Project project = projectService.getProjectEntity(id);
            if (null != project && StringUtils.isNotEmpty(project.getProjectNumber())) {
                String projLogListStr = projectService.getProjLogByProjectId(project.getId(),Integer.parseInt(page), Integer.parseInt(rows), true);
                projLogList = JSON.parseObject(JsonFromatUtil.formatJsonToList(projLogListStr),new TypeReference<List<ProjLogDto>>(){});
                long count=  projectService.getProjLogListCount(project.getProjectNumber());
                String json = JSONArray.toJSONString(projLogList);
                json = "{\"rows\":" + json + ",\"total\":" + count + "}";
                TagUtil.ajaxResponse(resp, json);
            }
        }

    }

    /**
     * 项目日志不分页
     * 
     * @return
     */
    @RequestMapping(params = "queryProjectLogNotPage")
    public void queryProjectLogNotPage(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        List<ProjLogDto> projLogList = null;
        if (StringUtils.isNotEmpty(id)) {
            Project project = projectService.getProjectEntity(id);
           /* Project project = JSON.parseObject(JsonFromatUtil.formatJsonToList(projectStr),new TypeReference<Project>(){});*/
            if (null != project && StringUtils.isNotEmpty(project.getProjectNumber())) {
                String logStr = projectService.getProjLogByProjectId(project.getId(),0, 0, false);
                projLogList = JSON.parseObject(JsonFromatUtil.formatJsonToList(logStr),new TypeReference<List<ProjLogDto>>(){});
                for(ProjLogDto p:projLogList){
                    if (StringUtils.isNotBlank(p.getCreateBy())) {
                        TSUserDto userDto = userService.getUserByUserId(p.getCreateBy());
                        if (StringUtils.isNotBlank(userDto.getRealName()) && StringUtils.isNotBlank(userDto.getUserName())) {
                            p.setShowName(userDto.getRealName() + "-" + userDto.getUserName());
                        }
                    }
                }
                long count=  projectService.getProjLogListCount(project.getProjectNumber());                
                String json = JSONArray.toJSONString(projLogList);
                json = "{\"rows\":" + json + ",\"total\":" + count + "}";
                TagUtil.ajaxResponse(resp, json);
            }
        }

    }



    /**
     * 导出项目日志excel
     *
     * @param request
     * @param response
     * @param dataGrid
     * @see
     */
    @RequestMapping(params = "exportProjectLog")
    public void exportProjectLog(ProjLogDto projLog, HttpServletRequest request,
                                 HttpServletResponse response, DataGrid dataGrid) {

        String projectId = request.getParameter("projectId");
        Project project = projectService.getProjectEntity(projectId);
        String codedFileName = "项目日志";
        if (project != null) {
            codedFileName = project.getProjectNumber() + codedFileName;
        }
        String projLogListStr = projectService.getProjLogByProjectId(project.getId(),0,0,false);
        List<ProjLogDto> projLogList = JSON.parseObject(JsonFromatUtil.formatJsonToList(projLogListStr),new TypeReference<List<ProjLogDto>>(){});
        fileDownLoad(response, projLogList, null, "true");
    }

    /**
     * 导出Excel (包含导出数据、下载模板、导出错误报告)
     *
     * @return
     */
    private void fileDownLoad(HttpServletResponse response, List<ProjLogDto> dataList, Map<String, String> errorMsgMap, String isExport) {

        String excelName = POIExcelUtil.createExcelName(isExport, "项目日志", errorMsgMap);

        boolean bExport = false;
        if ("true".equals(isExport)) {
            bExport = true;
        }

        List<String> columns = Arrays.asList(new String[] {"logInfo:操作", "showName:操作者", "createTime:操作时间", "remark:备注"});

        Map<String, List<String>> validationDataMap = new HashMap<String, List<String>>();

        ExcelVo excelVo = new ExcelVo();
        excelVo.setTitle("项目日志");
        excelVo.setColumns(columns);
        excelVo.setDataList(dataList);
        excelVo.setValidationDataMap(validationDataMap);
        excelVo.setErrorMsgMap(errorMsgMap);

        HSSFWorkbook workbook = POIExcelUtil.getInstance().exportExcel(bExport, excelVo, "yyyy-MM-dd");

        POIExcelUtil.responseReportWithName(response, workbook, excelName);
    }

}
