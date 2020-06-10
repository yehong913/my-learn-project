package com.glaway.ids.qualityTest.dto;

import com.glaway.foundation.common.vdata.GLVData;
import lombok.Data;


/**
 * @Description: 〈一句话功能简述〉
 * @author: sunmeng
 * @ClassName: CofigDataGridTestDto
 * @Date: 2019/10/30-15:28
 * @since
 */
@Data
public class CofigDataGridTestDto extends GLVData {
    /**
     * 名称
     */
    private String name;

    /**
     * 规则
     */
    private String rule;

    /**
     * 计划关联id
     */
    private String planId;
}
