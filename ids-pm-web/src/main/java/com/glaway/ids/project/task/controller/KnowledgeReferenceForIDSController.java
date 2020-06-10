package com.glaway.ids.project.task.controller;


import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.common.util.param.GWConstants;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.ids.common.vo.KnowledgeReferenceDto;
import com.glaway.ids.project.plan.service.FeignKnowledgeReferenceRemoteServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 知识参考
 * 
 * @author wqb
 * @version 2019年12月20日 16:45:15
 * @since
 */
@Controller
@RequestMapping("/knowledgeReferenceForIDSController")
public class KnowledgeReferenceForIDSController
{

    /** 知识参考服务 */
    @Autowired
    private FeignKnowledgeReferenceRemoteServiceI knowledgeReferenceService;

    /**
     * 增加知识参考的信息
     * 
     * @param knowledgeReference
     * @param req
     * @param response
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "selectReference")
    @ResponseBody
    public AjaxJson selectReference(KnowledgeReferenceDto knowledgeReference,
                                    HttpServletRequest req, HttpServletResponse response)
    {
        AjaxJson j = new AjaxJson();
        j.setSuccess(false);
        String msg = "选择参考成功";
        String errorMsg = "选择参考失败";
        try
        {
            if (!CommonUtil.isEmpty(knowledgeReference)
                && !CommonUtil.isEmpty(knowledgeReference.getLibId())
                && !CommonUtil.isEmpty(knowledgeReference.getCode())
                && !CommonUtil.isEmpty(knowledgeReference.getTaskId())
                && !CommonUtil.isEmpty(knowledgeReference.getType()))
            {
                List<KnowledgeReferenceDto> knowledgeReferenceList = knowledgeReferenceService.getKnowledgeReferenceList(
                    knowledgeReference, 1, 10, false);
                // 如果为空，则保存数据
                if (CommonUtil.isEmpty(knowledgeReferenceList))
                {
                    CommonUtil.glObjectSet(knowledgeReference);
                    knowledgeReferenceService.saveKnowledgeReference(knowledgeReference);
                }
                else
                {
                    msg = "取消参考成功";
                    errorMsg = "取消参考失败";
                    knowledgeReferenceService.deleteKnowledgeReference(knowledgeReference);
                }
                j.setSuccess(true);
            }
        }
        catch (Exception e)
        {
            msg = errorMsg;
            Object[] params = new Object[] {msg, msg};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2002, params, e);
        }
        finally
        {
            j.setMsg(msg);
            return j;
        }
    }

    /**
     * 取消知识参考的信息
     * 
     * @param knowledgeReference
     * @param req
     * @param response
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "cancelReference")
    @ResponseBody
    public AjaxJson cancelReference(KnowledgeReferenceDto knowledgeReference,
                                    HttpServletRequest req, HttpServletResponse response)
    {
        AjaxJson j = new AjaxJson();
        j.setSuccess(false);
        String msg = "取消参考成功";
        String errorMsg = "取消参考失败";
        try
        {
            if (!CommonUtil.isEmpty(knowledgeReference)
                && !CommonUtil.isEmpty(knowledgeReference.getLibId())
                && !CommonUtil.isEmpty(knowledgeReference.getCode())
                && !CommonUtil.isEmpty(knowledgeReference.getTaskId())
                && !CommonUtil.isEmpty(knowledgeReference.getType()))
            {
                List<KnowledgeReferenceDto> knowledgeReferenceList = knowledgeReferenceService.getKnowledgeReferenceList(
                    knowledgeReference, 1, 10, false);
                // 如果为空，则保存数据
                if (CommonUtil.isEmpty(knowledgeReferenceList))
                {
                    msg = "选择参考成功";
                    errorMsg = "选择参考失败";
                    CommonUtil.glObjectSet(knowledgeReference);
                    knowledgeReferenceService.saveKnowledgeReference(knowledgeReference);
                }
                else
                {
                    knowledgeReferenceService.deleteKnowledgeReference(knowledgeReference);
                }
                j.setSuccess(true);
            }
        }
        catch (Exception e)
        {
            msg = errorMsg;
            Object[] params = new Object[] {msg, msg};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2002, params, e);
        }
        finally
        {
            j.setMsg(msg);
            return j;
        }
    }

}
