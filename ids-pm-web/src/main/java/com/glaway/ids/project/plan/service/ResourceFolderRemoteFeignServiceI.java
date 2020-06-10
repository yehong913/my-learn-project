package com.glaway.ids.project.plan.service;


import java.util.List;

import com.glaway.foundation.businessobject.service.BusinessObjectServiceI;
import com.glaway.foundation.common.vo.TreeNode;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.plan.fallback.ResourceFolderRemoteFeignServiceCallBack;
import com.glaway.ids.project.plan.fallback.ResourceRemoteFeignServiceCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 资源树接口
 * @author wangyangzan
 * @version 2016年1月7日
 * @see ResourceFolderRemoteFeignServiceI
 * @since
 */

@FeignClient(value = FeignConstants.ID_COMMON_SERVICE,fallbackFactory = ResourceFolderRemoteFeignServiceCallBack.class)
public interface ResourceFolderRemoteFeignServiceI{


    @RequestMapping(value = FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/ResourceFolderRestController/getTreeNodesForPm.do")
    List<TreeNode> getTreeNodesForPm();

}
