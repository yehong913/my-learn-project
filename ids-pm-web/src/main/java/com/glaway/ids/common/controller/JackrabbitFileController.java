package com.glaway.ids.common.controller;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.glaway.foundation.core.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.I18nUtil;
import com.glaway.foundation.jackrabbit.util.JackrabbitUtil;


/**
 * 文件下载公共controller
 * @author zhangdaihao
 */
@Controller
@RequestMapping("/jackrabbitFileController")
public class JackrabbitFileController extends BaseController {

    private static final OperationLog LOG = BaseLogFactory.getOperationLog(JackrabbitFileController.class);

    /**
     * 文件下载
     * 
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "fileDown")
    public void fileDown(HttpServletRequest request, HttpServletResponse response) {
        InputStream is = null;
        ServletOutputStream out = null;
        String filePath = request.getParameter("filePath");
        String fileName = request.getParameter("fileName");
        try {
            filePath = URLDecoder.decode(filePath, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            LOG.error(I18nUtil.getValue("com.glaway.ids.common.operation.jackrabbit.decodeFilePathError"), e);
        }
        is = JackrabbitUtil.downLoadFile(filePath);
        String contentType = "application/x-download";
        response.setContentType(contentType);
        try {
            response.setHeader("Content-Disposition",
                "attachment;filename=\"" + new String(fileName.getBytes("gb2312"), "iso8859-1") + "\"");
        }
        catch (UnsupportedEncodingException e) {
            LOG.error(I18nUtil.getValue("com.glaway.ids.common.operation.jackrabbit.encodeFileNameError"), e);
        }
        try {
            out = response.getOutputStream();
        }
        catch (IOException e) {
            LOG.error(I18nUtil.getValue("com.glaway.ids.common.operation.jackrabbit.outputStreamError"), e);
        }
        byte[] bytes = new byte[0xffff];
        int b = 0;
        try {
            while ((b = is.read(bytes, 0, 0xffff)) > 0) {
                out.write(bytes, 0, b);
            }
        }
        catch (IOException e) {
            LOG.error(I18nUtil.getValue("com.glaway.ids.common.operation.jackrabbit.outputStreamError"), e);
        }
        try {
            is.close();
        }
        catch (IOException e) {
            LOG.error(I18nUtil.getValue("com.glaway.ids.common.operation.jackrabbit.IOCloseError"), e);
        }
        try {
            out.flush();
        }
        catch (IOException e) {
            LOG.error(I18nUtil.getValue("com.glaway.ids.common.operation.jackrabbit.IOCloseError"), e);
        }
    }
}
