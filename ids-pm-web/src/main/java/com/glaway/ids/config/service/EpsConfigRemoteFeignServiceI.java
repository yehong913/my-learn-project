package com.glaway.ids.config.service;


import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.config.fallback.EpsConfigServiceCallBack;
import com.glaway.ids.config.vo.EpsConfig;

/**
 * 配置EPS接口
 * 
 * @author wangshen
 * @version 2015年5月26日
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = EpsConfigServiceCallBack.class)
public interface EpsConfigRemoteFeignServiceI {

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/epsConfigRestController/getEpsConfig.do")
    String getEpsConfig(@RequestParam("id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/epsConfigRestController/addEpsConfig.do")
    void addEpsConfig(@RequestBody EpsConfig epsConfig);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/epsConfigRestController/searchTreeNode.do")
    String searchTreeNode(@RequestBody EpsConfig epsConfig);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/epsConfigRestController/getEpsParentList.do")
    void getEpsParentList(@RequestBody Map<String,Object> mapList);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/epsConfigRestController/getMaxEpsConfigPlace.do")
    int getMaxEpsConfigPlace();
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/epsConfigRestController/saveOrUpdateEpsConfig.do")
    void saveOrUpdateEpsConfig(@RequestBody EpsConfig epsConfig);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/epsConfigRestController/getParentNode.do")
    String getParentNode(@RequestBody EpsConfig epsConfig);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/epsConfigRestController/modify.do")
    void modify(@RequestBody EpsConfig epsConfig, @RequestParam("nochange")boolean nochange);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/epsConfigRestController/getEpsconfigList.do")
    FeignJson getEpsconfigList(@RequestBody EpsConfig epsConfig);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/epsConfigRestController/doBatchDel.do")
    void doBatchDel(@RequestParam("ids")String ids);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/epsConfigRestController/doBatchStartOrStop.do")
    void doBatchStartOrStop(@RequestParam("ids") String ids,@RequestParam("state")String state);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/epsConfigRestController/getList.do")
    String getList();

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/epsConfigRestController/getEpsNamePathById.do")
    FeignJson getEpsNamePathById(@RequestParam(value = "id",required = false) String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/epsConfigRestController/getEpsTreeNodes.do")
    String getEpsTreeNodes();

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/epsConfigRestController/getTreeNodes.do")
    String getTreeNodes(@RequestBody EpsConfig epsConfig);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/epsConfigRestController/doBatchDelIsHaveChildList.do")
    FeignJson doBatchDelIsHaveChildList(@RequestParam(value = "ids", required = false) String ids);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/epsConfigRestController/doUpdate.do")
    FeignJson doUpdate(@RequestBody EpsConfig epsConfig);

}
