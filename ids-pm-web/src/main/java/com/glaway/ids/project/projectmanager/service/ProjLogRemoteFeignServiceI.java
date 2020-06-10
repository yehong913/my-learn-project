package com.glaway.ids.project.projectmanager.service;


import com.glaway.foundation.businessobject.service.BusinessObjectServiceI;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.projectmanager.fallback.ProjLogRemoteFeignServiceCallBack;
import com.glaway.ids.project.projectmanager.fallback.ProjectRemoteFeignServiceCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 〈项目日志服务接口〉
 * 〈功能详细描述〉
 * @author Songjie
 * @version 2015年7月22日
 * @see ProjLogRemoteFeignServiceI
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = ProjLogRemoteFeignServiceCallBack.class)
public interface ProjLogRemoteFeignServiceI {


    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/saveProjLog.do")
    void saveProjLog(@RequestParam(value = "projectNumber",required = false) String projectNumber, @RequestParam(value = "projectId",required = false) String projectId,
                     @RequestParam(value = "logInfo",required = false) String logInfo, @RequestParam(value = "remark",required = false)  String remark, @RequestBody TSUserDto userDto);
    


}
