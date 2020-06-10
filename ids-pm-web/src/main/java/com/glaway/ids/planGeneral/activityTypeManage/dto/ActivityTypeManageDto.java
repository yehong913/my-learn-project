package com.glaway.ids.planGeneral.activityTypeManage.dto;

import com.glaway.foundation.common.vdata.GLVData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 活动类型管理信息
 * <p>
 * Created by LHR on 2019/8/26.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityTypeManageDto extends GLVData {

    private String code;

    private String name;

    private String status;

    private String remark;

    private String avaliable = "1";

    private String statusName;
}
