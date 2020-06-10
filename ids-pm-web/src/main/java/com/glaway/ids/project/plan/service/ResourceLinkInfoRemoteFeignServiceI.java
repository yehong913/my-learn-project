package com.glaway.ids.project.plan.service;

import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.plan.fallback.ResourceLinkInfoRemoteFeignServiceCallBack;
import com.glaway.ids.project.plan.vo.CheckResourceUsedRateVO;
import com.glaway.ids.project.projectmanager.dto.ResourceDto;
import com.glaway.ids.project.projectmanager.dto.ResourceLinkInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * ResourceLinkInfo ServiceI
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author wangyangzan
 * @version 2018年4月26日
 * @see ResourceLinkInfoRemoteFeignServiceI
 * @since
 */
@FeignClient(value = FeignConstants.ID_COMMON_SERVICE,fallbackFactory = ResourceLinkInfoRemoteFeignServiceCallBack.class)
public interface ResourceLinkInfoRemoteFeignServiceI  {


    @RequestMapping(value = FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/resourceLinkInfoRestController/queryResourceList.do")
    List<ResourceLinkInfoDto> queryResourceList(@RequestBody ResourceLinkInfoDto resourceLinkInfo, @RequestParam("page") int page,
                                                @RequestParam("rows") int rows,@RequestParam("isPage") boolean isPage);

    @RequestMapping(value = FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/resourceLinkInfoRestController/doAddResourceForWork.do")
    void doAddResourceForWork(@RequestParam("ids") String ids, @RequestParam("useObjectId") String useObjectId, @RequestParam("planStartTime2") String planStartTime2,
                              @RequestParam("planEndTime2") String planEndTime2, @RequestParam("useObjectType") String useObjectType);
    
    @RequestMapping(FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/ResourceRestController/doAdd.do")
    Map<String,Object> doAdd(@RequestBody ResourceDto resource);
    
    
    @RequestMapping(FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/resourceLinkInfoRestController/doAddResourceLinkInfo.do")
    void doAddResourceLinkInfo(@RequestBody ResourceLinkInfoDto dto);
    
    @RequestMapping(FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/resourceLinkInfoRestController/getResourceLinkInfoEntity.do")
    ResourceLinkInfoDto getResourceLinkInfoEntity(@RequestParam(value = "id",required = false) String id);

    @RequestMapping(FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/resourceLinkInfoRestController/getCount.do")
    long getCount(@RequestBody ResourceLinkInfoDto dto);

    @RequestMapping(FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/resourceLinkInfoRestController/getAllLinkInfo.do")
    List<Map<String, Object>> getAllLinkInfo(@RequestBody ResourceLinkInfoDto resourceLinkInfoDto);

    @RequestMapping(FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/ResourceRestController/conditionSearchForCheckResource.do")
    List<CheckResourceUsedRateVO> conditionSearchForCheckResource(@RequestParam(value = "resourceId", required = false) String resourceId,
                                                                  @RequestParam(value = "startTime", required = false) String startTime,
                                                                  @RequestParam(value = "endTime", required = false) String endTime,
                                                                  @RequestParam(value = "page", required = false) int page,
                                                                  @RequestParam(value = "rows", required = false) int rows,
                                                                  @RequestParam(value = "useobjectid", required = false) String useobjectid);

    @RequestMapping(FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/resourceLinkInfoRestController/updateResourceLinkInfoTimeByDto.do")
    void updateResourceLinkInfoTimeByDto(@RequestBody List<ResourceLinkInfoDto> res);
}
