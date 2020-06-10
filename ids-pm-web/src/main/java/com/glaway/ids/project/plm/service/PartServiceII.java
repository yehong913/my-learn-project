package com.glaway.ids.project.plm.service;

import java.util.List;
import java.util.Map;

import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.plm.dto.CheckOutInfoVO;
import com.glaway.ids.project.plm.dto.ContainerDto;
import com.glaway.ids.project.plm.dto.PartMasterDto;
import com.glaway.ids.project.plm.dto.PlmTypeDefinitionDto;
import com.glaway.ids.project.plm.fallback.PartServiceIICallBack;
import com.glaway.ids.project.plm.vo.PartVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.common.vo.TreeNode;

import com.glaway.foundation.fdk.dev.common.FeignJson;


@FeignClient(value= FeignConstants.ID_PLM_SERVICE, fallbackFactory = PartServiceIICallBack.class)
public interface PartServiceII {

    /**
     * 查询除自己以外成品和对象最新
     * @param conditionList
     * @param partId
     * @return
     */
    @RequestMapping("plm-service/feign/idsPartController/findExistPart.do")
    List<CheckOutInfoVO> findExistPart(@RequestBody List<ConditionVO> conditionList, @RequestParam("partId") String partId, @RequestParam("productId") String productId, @RequestParam("viewId") String viewId, @RequestParam("userId") String userId);

    /**
     * 获取除自己以外成品和对象最新的数量
     * @param conditionList
     * @return
     */
    @RequestMapping("plm-service/feign/idsPartController/getExistPartSizeByCondition.do")
    int getExistPartSizeByCondition(@RequestBody List<ConditionVO> conditionList,@RequestParam("partId") String partId,@RequestParam("productId") String productId,@RequestParam("viewId") String viewId, @RequestParam("userId") String userId);

    /**
     * 判断父级部件中是否已存在传入的子级部件
     * @param fatherPartId
     * @param sonIdList
     * @return
     */
    @RequestMapping("plm-service/feign/idsPartController/isSonPartExist.do")
    Boolean isSonPartExist(@RequestParam("fatherPartId") String fatherPartId,@RequestBody List<String> sonIdList);

    /**
     * 新增现有部件保存usageLink
     * @param partId
     * @return
     */
    @RequestMapping("plm-service/feign/idsPartController/insertExistPart.do")
    FeignJson insertExistPart(@RequestParam("productId") String productId,@RequestParam("partId") String partId,
                              @RequestParam("partIds") String partIds,@RequestParam("partIdForNew") String partIdForNew,
                              @RequestParam("userId") String userId,@RequestParam("lazy") String lazy );

    /**
     * 保存现有部件--全局部件
     * @param partId
     * @param masterList
     * @return
     */
    @RequestMapping("plm-service/feign/idsPartController/saveExistPart.do")
    List<PartVo> saveExistPart(@RequestParam("partId") String partId,@RequestBody List<PartMasterDto> masterList);

    /**
     * 保存现有部件--特定部件
     * @param roleAId
     * @param partId
     * @param masterList
     * @return
     */
    @RequestMapping("plm-service/feign/idsPartController/saveSubExistPart.do")
    List<PartVo> saveSubExistPart(@RequestParam("roleAId") String roleAId, @RequestParam("partId") String partId, @RequestBody List<PartMasterDto> masterList);

    @RequestMapping(value="plm-service/feign/idsPartController/getContextList.do")
    List<ContainerDto> getContextList(@RequestParam("userId") String userId);

    /**
     * 获得bom树结构
     * @param partId
     * @param userId
     * @return
     */
    @RequestMapping(value="plm-service/feign/idsPartController/getPartBomTree.do")
    List<TreeNode> getPartBomTree(@RequestParam("partId") String partId,@RequestParam("userId") String userId);


    /**
     * 获得part信息 返回值map<编号，名称>
     * @param partId
     * @param userId
     * @return
     */
    @RequestMapping(value="plm-service/feign/idsPartController/getPartInfo.do")
    Map<String,String> getPartInfo(@RequestParam("partId") String partId,@RequestParam("userId") String userId);
}
