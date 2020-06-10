/*
 * 文件名：projEPSController.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：wangshen
 * 修改时间：2015年4月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.project.projectmanager.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.glaway.foundation.common.util.JsonUtil;
import com.glaway.foundation.common.vo.TreeNode;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.config.service.EpsConfigRemoteFeignServiceI;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.ids.config.vo.EpsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/projEPSController")
public class ProjEPSController {

    @Autowired
    private EpsConfigRemoteFeignServiceI epsConfigService;
    

    /**
     * 跳转EPS树页面
     * 
     * @return
     */
    @RequestMapping(params = "goEPSTree")
    public ModelAndView goEPSTree(HttpServletRequest req) {
        return new ModelAndView("com/glaway/ids/project/projectmanager/EPSTree");
    }

    /**
     * EPS树
     * 
     * @return
     */
    @RequestMapping(params = "getEPSTree")
    public void getEPSTree(HttpServletRequest req, HttpServletResponse resp) {
      //  List<EpsConfig> configs = epsConfigService.getUsableEpsTree(new EpsConfig());
        String id=req.getParameter("id");
        EpsConfig config =null;
        //判断是否为根节点
        if(org.apache.commons.lang.StringUtils.isBlank(id)){
            config = new EpsConfig();
        }else{
            String configStr = epsConfigService.getEpsConfig(id);
            config = JSON.parseObject(JsonFromatUtil.formatJsonToList(configStr),new TypeReference<EpsConfig>(){});
        }

        //孩子list
        //转换成符合easyui显示的格式
        String epsStr = epsConfigService.getTreeNodes(config);
        List<TreeNode> node = JSON.parseObject(JsonFromatUtil.formatJsonToList(epsStr),new TypeReference<List<TreeNode>>(){});
        
        String json = JsonUtil.toJsonString(node);
        TagUtil.ajaxResponse(resp, json);

    }

}
