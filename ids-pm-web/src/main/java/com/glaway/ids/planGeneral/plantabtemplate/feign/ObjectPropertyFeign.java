package com.glaway.ids.planGeneral.plantabtemplate.feign;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.planGeneral.plantabtemplate.callback.ObjectPropertyFeignCallBack;
import com.glaway.ids.planGeneral.plantabtemplate.dto.ObjectPropertyInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @Title: Feign
 * @Description: 元数据属性Feign
 * @author wuxing
 * @date 2019-08-27
 * @version V1.0
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = ObjectPropertyFeignCallBack.class)
public interface ObjectPropertyFeign {

    /**
     * 功能描述：根据页签模板ID查询所有数据
     * @param tabId
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/objectPropertyRestController/getAllPropertyByTabId.do")
    List<ObjectPropertyInfoDto> getAllPropertyByTabId(@RequestParam(value = "tabId",required = false) String tabId);

    /**
     * 功能描述：删除数据
     * @param id
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/objectPropertyRestController/doDelete.do")
    FeignJson doDelete(@RequestParam(value = "id",required = false) String id);
}
