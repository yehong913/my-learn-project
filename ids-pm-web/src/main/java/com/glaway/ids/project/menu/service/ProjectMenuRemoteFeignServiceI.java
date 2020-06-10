package com.glaway.ids.project.menu.service;


import java.util.List;

import com.glaway.foundation.common.service.CommonService;
import com.glaway.foundation.common.vo.TreeNode;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.menu.fallback.ProjectMenuRemoteFeignServiceCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 左侧项目列表树
 * 
 * @author wangshen
 * @version 2015年4月14日
 * @see ProjectMenuRemoteFeignServiceI
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = ProjectMenuRemoteFeignServiceCallBack.class)
public interface ProjectMenuRemoteFeignServiceI{



    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectMenuRestController/getconstructionProjectMenuTree.do")
    FeignJson constructionProjectMenuTree(@RequestParam(value = "projectId",required = false) String projectId, @RequestParam(value = "currentUserId",required = false) String currentUserId);

}
