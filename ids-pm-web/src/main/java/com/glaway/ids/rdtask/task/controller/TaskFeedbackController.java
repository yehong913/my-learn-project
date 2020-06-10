package com.glaway.ids.rdtask.task.controller;

import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.util.I18nUtil;
import com.glaway.foundation.common.util.ResourceUtil;
import com.glaway.foundation.common.util.UserUtil;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.model.common.UploadFile;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.jackrabbit.util.JackrabbitUtil;
import com.glaway.ids.constant.JackrabbitConstants;
import com.glaway.ids.constant.TaskFeedbackConstants;
import com.glaway.ids.rdtask.task.form.TaskFeedbackInfo;
import com.glaway.ids.rdtask.task.service.TaskFeedbackFeignServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 〈一句话功能简述〉
 * @author: sunmeng
 * @ClassName: TaskFeedbackController
 * @Date: 2019/8/8-13:33
 * @since
 */
@Controller
@RequestMapping("/taskFeedbackController")
public class TaskFeedbackController extends BaseController {

    @Autowired
    private TaskFeedbackFeignServiceI taskFeedbackFeignService;

    /**
     * 新增交付物
     *
     * @param taskFeedbackInfo
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "saveTaskFeedback")
    @ResponseBody
    public FeignJson saveTaskFeedback(TaskFeedbackInfo taskFeedbackInfo, HttpServletRequest request) {
        String checkP = request.getParameter("checkboxP");
        String currentId = UserUtil.getCurrentUser().getId();
        String taskId = request.getParameter("taskId");
        String progressRate = request.getParameter("progressRate");
        String progressRateRemark = request.getParameter("progressRateRemark");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("taskFeedbackInfo",taskFeedbackInfo);
        map.put("checkP",checkP);
        map.put("userId",currentId);
        map.put("taskId",taskId);
        map.put("progressRate",progressRate);
        map.put("progressRateRemark",progressRateRemark);
        FeignJson j = taskFeedbackFeignService.saveTaskFeedback(map);
        return j;
    }

    /**
     * 提交审批
     *
     * @params
     */
    @RequestMapping(params = "doSubmitApprove")
    @ResponseBody
    public FeignJson doSubmitApprove(HttpServletRequest req, HttpServletResponse response) {
        String leader = req.getParameter("leader");
        String planId = req.getParameter("planId");
        String userId = ResourceUtil.getCurrentUser().getId();

        FeignJson fj = taskFeedbackFeignService.doSubmitApprove(leader,planId,userId);
        return fj;
    }

    /**
     * Description: <br>
     * 上传文件附件<br>
     * Implement: <br>
     * <br>
     *
     * @param request
     * @param response
     * @return 返回
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "addFileAttachments", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson addFileAttachments(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try {
            UploadFile uploadFile = new UploadFile(request);
            String attachmentURL = addFileAttachments(uploadFile);
            j.setObj(attachmentURL);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new GWException(e);
        }
        finally {
            return j;
        }
    }

    /**
     * Description: <br>
     * 上传文件附件<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     *
     * @return
     * @throws GWException
     * @see
     */
    public String addFileAttachments(UploadFile uploadFile)
            throws GWException {
        try {
            MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
            uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            Set<String> mapKeys = fileMap.keySet();
            String attachmentName = "";
            String attachmentURL = "";
            for (String mapKey : mapKeys) {
                MultipartFile mf = fileMap.get(mapKey);
                InputStream fos = mf.getInputStream();// 获取文件流
                attachmentName = mf.getOriginalFilename();
                attachmentURL = JackrabbitUtil.handleFileUpload(fos, attachmentName,
                        JackrabbitConstants.TASK_FEEDBACK_FILE_PATH, false);

            }
            return attachmentURL;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new GWException(e);
        }
    }
}
