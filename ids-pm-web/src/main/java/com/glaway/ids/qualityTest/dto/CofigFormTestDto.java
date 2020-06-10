package com.glaway.ids.qualityTest.dto;

import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.vdata.GLVData;
import lombok.Data;


/**
 * @Description: 〈一句话功能简述〉
 * @author: sunmeng
 * @ClassName: CofigFormTestDto
 * @Date: 2019/10/30-15:29
 * @since
 */
@Data
public class CofigFormTestDto extends GLVData {
    /**
     * 质量单名
     */
    private String name;

    /**
     * 检查人
     */
    private String checkPerson;

    /**
     * 检查人信息
     */
    private String checkInfo;

    /**
     * 周期
     */
    private String period;

    /**
     * 结论
     */
    private String approve;

    /**
     * 备注
     */
    private String remark;

    /**
     * 计划关联id
     */
    private String planId;
}
