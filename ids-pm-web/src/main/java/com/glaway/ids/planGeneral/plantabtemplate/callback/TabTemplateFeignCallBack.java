package com.glaway.ids.planGeneral.plantabtemplate.callback;

import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.planGeneral.plantabtemplate.dto.DataSourceObjectDto;
import com.glaway.ids.planGeneral.plantabtemplate.dto.ObjectPropertyInfoDto;
import com.glaway.ids.planGeneral.plantabtemplate.dto.TabTemplateDto;
import com.glaway.ids.planGeneral.plantabtemplate.feign.TabTemplateFeign;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TabTemplateFeignCallBack implements FallbackFactory<TabTemplateFeign> {

    @Override
    public TabTemplateFeign create(Throwable cause) {
        return new TabTemplateFeign() {

            @Override
            public PageList searchDatagrid(Map<String, String> params) {
                return null;
            }

            @Override
            public FeignJson doStartOrStop(String ids, String status) {
                return null;
            }

            @Override
            public FeignJson doBatchDelete(String ids) {
                return null;
            }

            @Override
            public TabTemplateDto doSave(TabTemplateDto dto) {
                return null;
            }

            @Override
            public List<DataSourceObjectDto> queryDataSourceByTabId(String id) {
                return null;
            }

            @Override
            public List<ObjectPropertyInfoDto> queryObjectPropertyInfoDtoTabId(String id) {
                return null;
            }

            @Override
            public TabTemplateDto queryTabTemplate(String id) {
                return null;
            }

            @Override
            public boolean isRepeatName(String name, String id) {
                return false;
            }

            @Override
            public TabTemplateDto copyEntity(String id) {
                return null;
            }

            @Override
            public List<TabTemplateDto> getAllTabTemplates(String avaliable,String stopFlag) {
                return null;
            }

            @Override
            public TabTemplateDto doUpdateOrRevise(TabTemplateDto dto, String userId,String updateOrRevise) {
                return null;
            }

            @Override
            public FeignJson doBack(Map<String, String> params) {
                return null;
            }

            @Override
            public FeignJson doSubmitApprove(Map<String, String> params) {
                return null;
            }

            @Override
            public FeignJson getVersionDatagridStr(String bizId, Integer pageSize,
                                                             Integer pageNum) {
                return null;
            }

        };
    }
}
