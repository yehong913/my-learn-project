package com.glaway.ids.project.plan.dto;

import com.glaway.foundation.common.vdata.GLVData;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 交付项名称库文档类型
 * @author: sunmeng
 * @ClassName: NameStandardDeliveryRelationDto
 * @Date: 2019/7/2-15:53
 * @since
 */
@Getter
@Setter
public class NameStandardDeliveryRelationDto extends GLVData {

    private String nameStandardId = null;

    private String deliveryStandardId = null;

    private DeliveryStandardDto deliveryStandard = null;

    private NameStandardDto nameStandard = null;

    @Override
    public String toString() {
        return "NameStandardDeliveryRelation " + " [nameStandardId: " + getNameStandardId() + "]"
                + " [deliveryStandardId: " + getDeliveryStandardId() + "]";
    }
}
