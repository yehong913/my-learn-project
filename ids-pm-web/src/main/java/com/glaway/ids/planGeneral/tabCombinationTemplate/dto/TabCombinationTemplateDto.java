package com.glaway.ids.planGeneral.tabCombinationTemplate.dto;

import com.glaway.foundation.common.vdata.GLVData;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 页签组合模板Dto
 * @author: sunmeng
 * @ClassName: TabCombinationTemplateDto
 * @Date: 2019/8/29-19:43
 * @since
 */
@Getter
@Setter
public class TabCombinationTemplateDto extends GLVData {

    //模版名称
    private String name;

    private String status;

    //编号
    private String code;

    //活动类型Id
    private String activityId;

    //模版类型(活动类型管理-活动名称)
    private String templateName;

    //备注
    private String remake;

    //流程实例变量
    private String processInstanceId;
}
