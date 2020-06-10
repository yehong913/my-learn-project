package com.glaway.ids.project.plm.fallback;

import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.common.vo.TreeNode;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.plm.dto.CheckOutInfoVO;
import com.glaway.ids.project.plm.dto.ContainerDto;
import com.glaway.ids.project.plm.dto.PartMasterDto;
import com.glaway.ids.project.plm.dto.PlmTypeDefinitionDto;
import com.glaway.ids.project.plm.service.PartServiceII;
import com.glaway.ids.project.plm.vo.PartVo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class PartServiceIICallBack implements FallbackFactory<PartServiceII> {

    @Override
    public PartServiceII create(Throwable cause) {
        return new PartServiceII() {
            @Override
            public List<CheckOutInfoVO> findExistPart(List<ConditionVO> conditionList,
                                                                String partId, String productId,
                                                                String viewId, String userId) {
                return null;
            }

            @Override
            public int getExistPartSizeByCondition(List<ConditionVO> conditionList,
                                                             String partId, String productId,
                                                             String viewId, String userId) {
                return 0;
            }

            @Override
            public Boolean isSonPartExist(String fatherPartId, List<String> sonIdList) {
                return null;
            }

            @Override
            public FeignJson insertExistPart(String productId, String partId,
                                                       String partIds, String partIdForNew,
                                                       String userId, String lazy) {
                return null;
            }

            @Override
            public List<PartVo> saveExistPart(String partId,
                                                        List<PartMasterDto> masterList) {
                return null;
            }

            @Override
            public List<PartVo> saveSubExistPart(String roleAId, String partId,
                                                           List<PartMasterDto> masterList) {
                return null;
            }

            @Override
            public List<ContainerDto> getContextList(String userId) {
                return null;
            }

            @Override
            public List<TreeNode> getPartBomTree(String partId, String userId) {
                return null;
            }

            @Override
            public Map<String, String> getPartInfo(String partId, String userId) {
                return null;
            }
        };
    }
}
