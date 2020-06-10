package com.glaway.ids.models;


import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.glaway.ids.project.plantemplate.utils.SupportFlagConstants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.common.util.I18nUtil;
import com.glaway.foundation.common.util.ResourceUtil;
import com.glaway.ids.project.plantemplate.support.planTemplate.vo.PlanTemplateReq;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class HttpClientUtil {
    private static final OperationLog log = BaseLogFactory.getOperationLog(HttpClientUtil.class);
    /**
     * Description: <br>
     * 通过对象，返回结果
     * 
     * @param postUrl
     * @return
     * @throws Exception
     * @see
     */
    public static JsonResult httpClientPostByTest(String postUrl, Object obj)
        throws Exception {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        JsonRequery req = new JsonRequery();
        req.setReqObj(gson.toJson(obj));
        /*
         * JsonResult jsonResult = new JsonResult();
         * if(CommonUtil.mapIsEmpty(UserInfoSingleton.getInstance().getUserInfoMap(),
         * "sessionId")){
         * jsonResult.setRetCode("X0002");
         * jsonResult.setRetMsg("用户信息缺失！");
         * return jsonResult;
         * }
         */
        String jsonStr = gson.toJson(req);
        return httpClientPost(postUrl, jsonStr, "4028ef354b5d7bb1014b5d7cffa10043");
    }

    /**
     * Description: <br>
     * 通过对象，返回结果
     * 
     * @param postUrl
     * @return
     * @throws Exception
     * @see
     */
    public static JsonResult httpClientPostByObject(String postUrl, Object obj, File file)
        throws Exception {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        JsonRequery req = new JsonRequery();
        req.setReqObj(gson.toJson(obj));
        /*
         * JsonResult jsonResult = new JsonResult();
         * if(CommonUtil.mapIsEmpty(UserInfoSingleton.getInstance().getUserInfoMap(),
         * "sessionId")){
         * jsonResult.setRetCode("X0002");
         * jsonResult.setRetMsg("用户信息缺失！");
         * return jsonResult;
         * }
         */
        String jsonStr = gson.toJson(req);
        JsonResult jsonResult = new JsonResult();
        if (file == null) {
            jsonResult = httpClientPost(postUrl, jsonStr,
                ResourceUtil.getSessionId());
        }
        else {
            jsonResult = httpClientPostByFileList(postUrl, jsonStr,
                ResourceUtil.getSessionId(), file);
        }
        return jsonResult;
    }

    /**
     * Description: <br>
     * 通过对象，返回文件流
     * 
     * @param postUrl
     * @return
     * @throws Exception
     * @see
     */
    public static InputStream httpClientPostStreamByObject(String postUrl, Object obj)
        throws Exception {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        JsonRequery req = new JsonRequery();
        req.setReqObj(gson.toJson(obj));
        /*
         * JsonResult jsonResult = new JsonResult();
         * if(CommonUtil.mapIsEmpty(UserInfoSingleton.getInstance().getUserInfoMap(),
         * "sessionId")){
         * jsonResult.setRetCode("X0002");
         * jsonResult.setRetMsg("用户信息缺失！");
         * return jsonResult;
         * }
         */
        String jsonStr = gson.toJson(req);
        InputStream inputStream = httpClientPostStream(postUrl, jsonStr,
            ResourceUtil.getSessionUserName().getId());
        return inputStream;
    }

    /**
     * 通过jsonStr，返回结果
     * 
     * @param postUrl
     * @param jsonStr
     * @return
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    private static JsonResult httpClientPost(String postUrl, String jsonStr, String sessionId)
        throws Exception {
        // 创建httpClient请求
        DefaultHttpClient httpclient = new DefaultHttpClient();
        JsonResult jsonResult = new JsonResult();
        try{
            HttpPost httpPost = new HttpPost(postUrl);// 这里的postUrl应放置工程配置文件中，此处仅为demo
            httpPost.addHeader("sessionId", sessionId);
            List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
            params.add(new BasicNameValuePair("data", jsonStr));
            httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

            // 通过post 向接口发起请求
            HttpResponse response = httpclient.execute(httpPost);
            // 请求响应实体
            HttpEntity entity = (HttpEntity)response.getEntity();
            String str = EntityUtils.toString(entity);
            Gson g = new Gson();
            jsonResult = g.fromJson(str, JsonResult.class);
            if(!CommonUtil.isEmpty(jsonResult.getRetObj())) {
            	jsonResult.setRetObj(g.toJson(jsonResult.getRetObj()));
            }
        }catch(Exception e){
            log.warn(I18nUtil.getValue("com.glaway.ids.pm.config.models.interfaceFailure"), e);
            jsonResult.setRetCode("X0002");
            jsonResult.setRetMsg(I18nUtil.getValue("com.glaway.ids.pm.config.models.interfaceFailure"));
            return jsonResult;
        }finally{
            httpclient.close();
        }
        return jsonResult;
    }

    /**
     * 通过jsonStr与文件流，返回结果
     * 
     * @param postUrl
     * @param jsonStr
     * @return
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    private static JsonResult httpClientPostByFileList(String postUrl, String jsonStr,
                                                       String sessionId, File file)
        throws Exception {

        if (file == null) {
            JsonResult jsonResult = new JsonResult();
            jsonResult.setRetCode("X0010");
            jsonResult.setRetMsg(I18nUtil.getValue("com.glaway.ids.pm.config.models.uploadFileEmpty"));
            return jsonResult;
        }
        
        // 创建httpClient请求
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JsonResult jsonResult = new JsonResult();
        try{
            HttpPost httpPost = new HttpPost(postUrl);
            httpPost.addHeader("sessionId", sessionId);

            HttpParams params = httpPost.getParams();
            params.setParameter("Content-Type", "tex/html;charset=UTF-8");
            httpPost.setParams(params);
            ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
            ContentBody fileBody = new FileBody(file);
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("file", fileBody);
            ContentBody data = new StringBody(jsonStr, contentType);
            reqEntity.addPart("data", data);

            httpPost.setEntity(reqEntity);

            // 通过post 向接口发起请求
            HttpResponse response = httpClient.execute(httpPost);

            // 请求响应实体
            HttpEntity entity = (HttpEntity)response.getEntity();
            String str = EntityUtils.toString(entity);
            Gson g = new Gson();
            jsonResult = g.fromJson(str, JsonResult.class);
            jsonResult.setRetObj(g.toJson(jsonResult.getRetObj()));
            jsonResult.setRetMsg(jsonResult.getRetMsg());
        }catch(Exception e){
            log.warn(I18nUtil.getValue("com.glaway.ids.pm.config.models.interfaceFailure"), e);
            jsonResult.setRetCode("X0002");
            jsonResult.setRetMsg(I18nUtil.getValue("com.glaway.ids.pm.config.models.interfaceFailure"));
            return jsonResult;
        }finally{
            httpClient.close();
        }
        
        return jsonResult;
    }

    /**
     * 获得下载文件的流
     * 
     * @param postUrl
     * @param jsonStr
     * @return
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public static InputStream httpClientPostStream(String postUrl, String jsonStr, String sessionId)
        throws Exception {
        InputStream is = null;
        // 创建httpClient请求
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(postUrl);// 这里的postUrl应放置工程配置文件中，此处仅为demo
        httpPost.addHeader("sessionId", sessionId);
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("data", jsonStr));
        httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

        // 通过post 向接口发起请求
        HttpResponse response = httpclient.execute(httpPost);
        // 请求响应实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            is = entity.getContent();
        }
        return is;
    }

    public static void main(String[] args)
        throws Exception {
        String url = "";

        /*
         * url = "http://192.168.111.110:8080/ids/planTemplateDetailSupport.do?queryWBSPlan";
         * PlanTemplateDetailReq req = new PlanTemplateDetailReq();
         * req.setPlanTemplateId("4028efee4c73023a014c730615aa0001");
         */

        /*
         * url = "http://192.168.111.110:8080/ids/deliverableSupport.do?queryDeliverable";
         * DeliverablesInfo deliverablesInfo = new DeliverablesInfo();
         * deliverablesInfo.setUseObjectId("4028efee4c729796014c729a85f20012");
         * deliverablesInfo.setUseObjectType(CommonConstants.DELIVERABLE_TYPE_PLANTEMPLATE);
         */

        /*
         * url = "http://192.168.111.110:8080/ids/planTemplateSupport.do?exportMpp";
         * PlanTemplateReq req = new PlanTemplateReq();
         * req.setId("4028efee4c79c707014c79c7b4ae0001");
         * InputStream inputStream = HttpClientUtil.httpClientPostByTest(url, req);
         * FileUtil.downFile("tt.xml", inputStream, "F:");
         */

        url = "http://192.168.111.110:8080/ids/planTemplateSupport.do?doDel";
        PlanTemplateReq req = new PlanTemplateReq();
        req.setId("4028efee4c927f5d014c9285255d0001");
        req.setCreator("admin");
        req.setLeader("admin");
        // req.setSupportFlag(SupportFlagConstants.APPROVE);
        req.setApproveTaskId("165024");
        req.setSupportFlag(SupportFlagConstants.BACK_APPROVE);
        HttpClientUtil.httpClientPostByTest(url, req);
    }
}
