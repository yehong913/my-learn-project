package com.glaway.ids.planGeneral.plantabtemplate.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glaway.foundation.common.dto.TSDepartDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.common.util.UserUtil;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.planGeneral.plantabtemplate.dto.ObjectPropertyInfoDto;
import com.glaway.ids.planGeneral.plantabtemplate.dto.TabTemplateDto;
import com.glaway.ids.planGeneral.plantabtemplate.feign.TabTemplateFeign;
import com.glaway.ids.planGeneral.plantabtemplate.service.TabTemplateServiceI;
import com.glaway.ids.planGeneral.plantabtemplate.utils.DatagridStrUtils;
import com.glaway.ids.planGeneral.tabCombinationTemplate.feign.TabCombinationTemplateFeignServiceI;
import com.glaway.ids.planGeneral.tabCombinationTemplate.vo.CombinationTemplateVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Title: ServiceImpl
 * @Description: 页签模版管理ServiceImpl
 * @author wuxing
 * @date 2019-08-27
 * @version V1.0
 */
@Service("tabTemplateServiceImpl")
public class TabTemplateServiceImpl implements TabTemplateServiceI {

    //页签模版Feign
    @Autowired
    private TabTemplateFeign tabTemplateFeign;

    @Autowired
    private TabCombinationTemplateFeignServiceI tabCbTemplateFeignService;

    /**
     * 功能描述：根据查询条件展示列表(数据过滤)
     * @param conditionList
     */
    @Override
    public void searchDatagrid(List<ConditionVO> conditionList, HttpServletResponse response) {
        Map<String, String> params = new HashMap<String, String>();
        for (ConditionVO conditionVO : conditionList) {
            params.put("sort", conditionVO.getSort());
            params.put(conditionVO.getKey(), conditionVO.getValue());
        }
        PageList pageList = tabTemplateFeign.searchDatagrid(params);
        String datagridStr = DatagridStrUtils.getDatagridStr(pageList, true);
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * 功能描述：批量/单条 启用或禁用页签模版
     * @param ids id集合
     * @param status 状态(启用“1”或者禁用“0”)
     */
    @Override
    public AjaxJson doStartOrStop(String ids, String status) {
        AjaxJson ajaxJson = new AjaxJson();
        FeignJson feignJson = tabTemplateFeign.doStartOrStop(ids, status);
        if (feignJson.isSuccess()){
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg(feignJson.getMsg());
        }else{
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(feignJson.getMsg());
        }
        return ajaxJson;
    }

    /**
     * 功能描述：批量/单条 删除页签模版
     * @param ids id集合(“，”分隔)
     */
    @Override
    public AjaxJson doBatchDelete(String ids) {
        AjaxJson ajaxJson = new AjaxJson();
        FeignJson feignJson = tabTemplateFeign.doBatchDelete(ids);
        if (feignJson.isSuccess()){
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg(feignJson.getMsg());
        }else{
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(feignJson.getMsg());
        }
        return ajaxJson;
    }

    /**
     * 功能描述：数据保存
     * @param dto
     * @return AjaxJson
     */
    @Override
    public AjaxJson doSave(HttpServletRequest request, TabTemplateDto dto) {
        TSUserDto userDto = UserUtil.getCurrentUser();
        TSDepartDto departDto = userDto.getTSDepart();
        //判断是新增还是修改
        if (StringUtils.isEmpty(dto.getId())){
            dto.setCreateBy(userDto.getUserId());
            dto.setCreateFullName(userDto.getRealName());
            dto.setCreateName(userDto.getUserName());
            dto.setCreateOrgId(departDto.getDeptCode());
            dto.setCreateTime(new Date());
        }else{
            dto.setUpdateBy(userDto.getUserId());
            dto.setUpdateFullName(userDto.getRealName());
            dto.setUpdateName(userDto.getUserName());
            dto.setUpdateOrgId(departDto.getDeptCode());
            dto.setUpdateTime(new Date());
        }
        dto.setExt1(userDto.getId());
        dto = tabTemplateFeign.doSave(dto);
        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.setObj(dto);
        ajaxJson.setSuccess(true);
        return ajaxJson;
    }

    /**
     * 功能描述：判断名称是否重复
     * @param name
     * @return AjaxJson
     */
    @Override
    public AjaxJson isRepeatName(String name, String id) {
        AjaxJson ajaxJson = new AjaxJson();
        boolean isRepeat = tabTemplateFeign.isRepeatName(name, id);
        if (isRepeat){
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }

    /**
     * 功能描述：根据ID查询数据
     * @param id
     * @return TabTemplateDto
     */
    @Override
    public TabTemplateDto queryInfoById(String id) {
        return tabTemplateFeign.queryTabTemplate(id);
    }

    /**
     * 功能描述：根据ID复制数据
     * @param id
     * @return TabTemplateDto
     */
    @Override
    public TabTemplateDto copyEntity(String id) {
        return tabTemplateFeign.copyEntity(id);
    }

    /**
     * 功能描述：数据保存
     * @param dto
     * @return AjaxJson
     */
    @Override
    public AjaxJson doUpdateOrRevise(String updateOrRevise, TabTemplateDto dto) {
        String userId = UserUtil.getInstance().getUser().getId();
        dto = tabTemplateFeign.doUpdateOrRevise(dto,userId,updateOrRevise);
        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.setObj(dto);
        ajaxJson.setSuccess(true);
        return ajaxJson;
    }

    /**
     * 功能描述：撤回
     * @param params
     * @return FeignJson
     */
    @Override
    public FeignJson doRevoke(Map<String, String> params) {
        return tabTemplateFeign.doBack(params);
    }

    @Override
    public FeignJson doBack(Map<String, String> params) {
        return tabTemplateFeign.doBack(params);
    }

    @Override
    public FeignJson doSubmitApprove(Map<String, String> params) {
        return tabTemplateFeign.doSubmitApprove(params);
    }

    @Override
    public FeignJson getVersionDatagridStr(String bizId, Integer pageSize, Integer pageNum) {
        return tabTemplateFeign.getVersionDatagridStr(bizId, pageSize, pageNum);
    }

    @Override
    public List<List<List<List<ObjectPropertyInfoDto>>>> goTabsView(HttpServletRequest request, String id,String displayAccess,String fromShow,String typeIds) {
        //获取页签组合模板对应的页签
        List<List<List<List<ObjectPropertyInfoDto>>>> listss = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        FeignJson combTemplateInfos = tabCbTemplateFeignService.getCombTemplateInfos(id);
        List<CombinationTemplateVo> combinationTemplateVoList = mapper.convertValue(combTemplateInfos.getObj(), new TypeReference<List<CombinationTemplateVo>>() {
        });

        combinationTemplateVoList.forEach(vo -> {
            if(!CommonUtil.isEmpty(typeIds)){
                if("all".equals(typeIds)){
                    String tabId = vo.getTypeId();
                    List<List<List<ObjectPropertyInfoDto>>> lists = new ArrayList<>();
                    //获取对象信息
                    List<ObjectPropertyInfoDto> arrayList = tabTemplateFeign.queryObjectPropertyInfoDtoTabId(tabId);
                    ObjectPropertyInfoDto objectPropertyInfoDto = new ObjectPropertyInfoDto();
                    if(CommonUtil.isEmpty(arrayList) && !CommonUtil.isEmpty(vo.getUrl())){
                        if(CommonUtil.isEmpty(fromShow)) {
                            objectPropertyInfoDto.setLoadUrl(vo.getUrl()+"&fromType=tabTemplate");
                        }else {
                            objectPropertyInfoDto.setLoadUrl(vo.getUrl());
                        }
                        objectPropertyInfoDto.setOrderNumber(String.valueOf(vo.getOrderNum()));
                        objectPropertyInfoDto.setPropertyName(vo.getName());
                        objectPropertyInfoDto.setDisplayUsage("2");
                        objectPropertyInfoDto.setControl("0");
                        objectPropertyInfoDto.setId(vo.getId());
                        objectPropertyInfoDto.setDisplay(displayAccess);
                    }
                    //排序
                    List<ObjectPropertyInfoDto> orderList = arrayList.stream().sorted((o1, o2) -> Integer.valueOf(o1.getOrderNumber()).compareTo(Integer.valueOf(o2.getOrderNumber()))).collect(Collectors.toList());
                    //组装数据
                    if(!CommonUtil.isEmpty(orderList)){
                        orderList.forEach(it -> {
                            it.setDisplayUsage("1");
                            List<List<ObjectPropertyInfoDto>> list = new ArrayList<>();
                            List<ObjectPropertyInfoDto> dtos = new ArrayList<>();
                            dtos.add(it);
                            list.add(dtos);
                            lists.add(list);
                        });
                    }else{
                        List<List<ObjectPropertyInfoDto>> list = new ArrayList<>();
                        List<ObjectPropertyInfoDto> dtos = new ArrayList<>();
                        dtos.add(objectPropertyInfoDto);
                        list.add(dtos);
                        lists.add(list);
                    }

                    //出现表格格式,二次组装
                    lists.forEach(thisList -> {
                        if (!CommonUtil.isEmpty(thisList.get(0).get(0).getControl()) && thisList.get(0).get(0).getControl().equals("6")){
                            List<ObjectPropertyInfoDto> objectPropertyInfoDtoList = new ArrayList<>();
                            lists.forEach(it -> {
                                if (it.get(0).get(0).getControl().equals("7") && thisList.get(0).get(0).getDataSourceId().equals(it.get(0).get(0).getDataSourceId()) && thisList.get(0).get(0).getObjectPath().equals(it.get(0).get(0).getObjectPath())){
                                    objectPropertyInfoDtoList.add(it.get(0).get(0));
                                }
                            });
                            thisList.add(objectPropertyInfoDtoList);
                        }
                    });

                    //筛选按钮
                    Boolean flag = false;
                    List<List<List<ObjectPropertyInfoDto>>> newlists = new ArrayList();
                    List<List<ObjectPropertyInfoDto>> itList = new ArrayList<>();
                    int size = lists.size();
                    for (int i = 0; i < size; i++) {
                        if (!CommonUtil.isEmpty(lists.get(i).get(0).get(0).getControl()) && !lists.get(i).get(0).get(0).getControl().equals("8")){
                            newlists.add(lists.get(i));
                            flag = true;
                        }else {

                            if(i+1 < size){
                                itList.add(lists.get(i).get(0));
                                if(!CommonUtil.isEmpty(lists.get(i+1).get(0).get(0).getControl()) && !lists.get(i+1).get(0).get(0).getControl().equals("8")){
                                    newlists.add(itList);
                                    itList = new ArrayList<>();
                                }


                            } else {
                                itList.add(lists.get(i).get(0));
                                newlists.add(itList);
                            }
                        }
                    }
                    //剔除为7的列表
                    if(!CommonUtil.isEmpty(newlists)) {
                        List<List<List<ObjectPropertyInfoDto>>> collect = newlists.stream().filter(i ->!CommonUtil.isEmpty(i.get(0).get(0).getControl()) && !i.get(0).get(0).getControl().equals("7")).collect(Collectors.toList());
                        List<List<List<ObjectPropertyInfoDto>>> list = collect.stream().filter(it -> it.size() != 0).collect(Collectors.toList());
                        if(!CommonUtil.isEmpty(list)) {
                            list.get(0).get(0).get(0).setExt1(vo.getName());
                            list.get(0).get(0).get(0).setExt2(vo.getTypeId());
                            listss.add(list);
                        }
                    }
                }else{
                    for(String typeId : typeIds.split(",")){
                        if(!CommonUtil.isEmpty(typeId) && vo.getTypeId().equals(typeId)){
                            String tabId = vo.getTypeId();
                            List<List<List<ObjectPropertyInfoDto>>> lists = new ArrayList<>();
                            //获取对象信息
                            List<ObjectPropertyInfoDto> arrayList = tabTemplateFeign.queryObjectPropertyInfoDtoTabId(tabId);
                            ObjectPropertyInfoDto objectPropertyInfoDto = new ObjectPropertyInfoDto();
                            if(CommonUtil.isEmpty(arrayList) && !CommonUtil.isEmpty(vo.getUrl())){
                                if(CommonUtil.isEmpty(fromShow)) {
                                    objectPropertyInfoDto.setLoadUrl(vo.getUrl()+"&fromType=tabTemplate");
                                }else {
                                    objectPropertyInfoDto.setLoadUrl(vo.getUrl());
                                }
                                objectPropertyInfoDto.setOrderNumber(String.valueOf(vo.getOrderNum()));
                                objectPropertyInfoDto.setPropertyName(vo.getName());
                                objectPropertyInfoDto.setDisplayUsage("2");
                                objectPropertyInfoDto.setControl("0");
                                objectPropertyInfoDto.setId(vo.getId());
                                objectPropertyInfoDto.setDisplay(displayAccess);
                            }
                            //排序
                            List<ObjectPropertyInfoDto> orderList = arrayList.stream().sorted((o1, o2) -> Integer.valueOf(o1.getOrderNumber()).compareTo(Integer.valueOf(o2.getOrderNumber()))).collect(Collectors.toList());
                            //组装数据
                            if(!CommonUtil.isEmpty(orderList)){
                                orderList.forEach(it -> {
                                    it.setDisplayUsage("1");
                                    List<List<ObjectPropertyInfoDto>> list = new ArrayList<>();
                                    List<ObjectPropertyInfoDto> dtos = new ArrayList<>();
                                    dtos.add(it);
                                    list.add(dtos);
                                    lists.add(list);
                                });
                            }else{
                                List<List<ObjectPropertyInfoDto>> list = new ArrayList<>();
                                List<ObjectPropertyInfoDto> dtos = new ArrayList<>();
                                dtos.add(objectPropertyInfoDto);
                                list.add(dtos);
                                lists.add(list);
                            }

                            //出现表格格式,二次组装
                            lists.forEach(thisList -> {
                                if (!CommonUtil.isEmpty(thisList.get(0).get(0).getControl()) && thisList.get(0).get(0).getControl().equals("6")){
                                    List<ObjectPropertyInfoDto> objectPropertyInfoDtoList = new ArrayList<>();
                                    lists.forEach(it -> {
                                        if (it.get(0).get(0).getControl().equals("7") && thisList.get(0).get(0).getDataSourceId().equals(it.get(0).get(0).getDataSourceId()) && thisList.get(0).get(0).getObjectPath().equals(it.get(0).get(0).getObjectPath())){
                                            objectPropertyInfoDtoList.add(it.get(0).get(0));
                                        }
                                    });
                                    thisList.add(objectPropertyInfoDtoList);
                                }
                            });

                            //筛选按钮
                            Boolean flag = false;
                            List<List<List<ObjectPropertyInfoDto>>> newlists = new ArrayList();
                            List<List<ObjectPropertyInfoDto>> itList = new ArrayList<>();
                            int size = lists.size();
                            for (int i = 0; i < size; i++) {
                                if (!CommonUtil.isEmpty(lists.get(i).get(0).get(0).getControl()) && !lists.get(i).get(0).get(0).getControl().equals("8")){
                                    newlists.add(lists.get(i));
                                    flag = true;
                                }else {

                                    if(i+1 < size){
                                        itList.add(lists.get(i).get(0));
                                        if(!CommonUtil.isEmpty(lists.get(i+1).get(0).get(0).getControl()) && !lists.get(i+1).get(0).get(0).getControl().equals("8")){
                                            newlists.add(itList);
                                            itList = new ArrayList<>();
                                        }


                                    } else {
                                        itList.add(lists.get(i).get(0));
                                        newlists.add(itList);
                                    }
                                }
                            }
                            //剔除为7的列表
                            if(!CommonUtil.isEmpty(newlists)) {
                                List<List<List<ObjectPropertyInfoDto>>> collect = newlists.stream().filter(i ->!CommonUtil.isEmpty(i.get(0).get(0).getControl()) && !i.get(0).get(0).getControl().equals("7")).collect(Collectors.toList());
                                List<List<List<ObjectPropertyInfoDto>>> list = collect.stream().filter(it -> it.size() != 0).collect(Collectors.toList());
                                if(!CommonUtil.isEmpty(list)) {
                                    list.get(0).get(0).get(0).setExt1(vo.getName());
                                    list.get(0).get(0).get(0).setExt2(vo.getTypeId());
                                    listss.add(list);
                                }
                            }
                        }
                    }
                }
            }
            /*if(!CommonUtil.isEmpty(displayAccess) && (displayAccess.equals(vo.getDisplayAccess()) || vo.getDisplayAccess().equals("1")) || displayAccess.equals("all")){*/

          /*  }*/

        });

        return listss;
    }

    @Override
    public List<List<List<ObjectPropertyInfoDto>>> goTabView(HttpServletRequest request, String id) {
        List<List<List<ObjectPropertyInfoDto>>> lists = new ArrayList<>();
        //获取对象信息
        List<ObjectPropertyInfoDto> arrayList = tabTemplateFeign.queryObjectPropertyInfoDtoTabId(id);
        //排序
        List<ObjectPropertyInfoDto> orderList = arrayList.stream().sorted((o1, o2) -> Integer.valueOf(o1.getOrderNumber()).compareTo(Integer.valueOf(o2.getOrderNumber()))).collect(Collectors.toList());
        //组装数据
        orderList.forEach(it -> {
            List<List<ObjectPropertyInfoDto>> list = new ArrayList<>();
            List<ObjectPropertyInfoDto> dtos = new ArrayList<>();
            dtos.add(it);
            list.add(dtos);
            lists.add(list);
        });
        //出现表格格式,二次组装
        lists.forEach(thisList -> {
            if (thisList.get(0).get(0).getControl().equals("6")){
                List<ObjectPropertyInfoDto> objectPropertyInfoDtoList = new ArrayList<>();
                lists.forEach(it -> {
                    if (it.get(0).get(0).getControl().equals("7") && thisList.get(0).get(0).getDataSourceId().equals(it.get(0).get(0).getDataSourceId()) && thisList.get(0).get(0).getObjectPath().equals(it.get(0).get(0).getObjectPath())){
                        objectPropertyInfoDtoList.add(it.get(0).get(0));
                    }
                });
                thisList.add(objectPropertyInfoDtoList);
            }
        });

        //筛选按钮
        Boolean flag = false;
        List<List<List<ObjectPropertyInfoDto>>> newlists = new ArrayList();
        List<List<ObjectPropertyInfoDto>> itList = new ArrayList<>();
        int size = lists.size();
        for (int i = 0; i < size; i++) {
            if (!lists.get(i).get(0).get(0).getControl().equals("8")){
                    newlists.add(lists.get(i));
                    flag = true;
                }else {

                    if(i+1 < size){
                        itList.add(lists.get(i).get(0));
                        if(!lists.get(i+1).get(0).get(0).getControl().equals("8")){
                            newlists.add(itList);
                            itList = new ArrayList<>();
                        }


                    } else {
                        itList.add(lists.get(i).get(0));
                        newlists.add(itList);
                    }
                }
        }
        //剔除为7的列表
        List<List<List<ObjectPropertyInfoDto>>> collect = newlists.stream().filter(i -> !i.get(0).get(0).getControl().equals("7")).collect(Collectors.toList());

        List<List<List<ObjectPropertyInfoDto>>> list = collect.stream().filter(it -> it.size() != 0).collect(Collectors.toList());

        return list;
    }


    public List<List<Integer>> getA(Integer[] data){
        List<List<Integer>> list = new ArrayList<>();
        ArrayList<Integer> group = null;
        for (int i = 0; i < data.length; i++) {
            group = new ArrayList<Integer>();
            group.add(data[i]);
            while (i+1<data.length && data[i+1] == data[i] + 1){
                group.add(data[++i]);
            }
            list.add(group);
        }
        return list;
    }
}
