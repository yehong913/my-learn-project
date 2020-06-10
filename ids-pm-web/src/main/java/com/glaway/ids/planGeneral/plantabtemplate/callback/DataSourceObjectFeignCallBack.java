package com.glaway.ids.planGeneral.plantabtemplate.callback;

import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.planGeneral.plantabtemplate.dto.DataSourceObjectDto;
import com.glaway.ids.planGeneral.plantabtemplate.dto.ObjectPropertyInfoDto;
import com.glaway.ids.planGeneral.plantabtemplate.feign.DataSourceObjectFeign;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class DataSourceObjectFeignCallBack implements FallbackFactory<DataSourceObjectFeign> {

    @Override
    public DataSourceObjectFeign create(Throwable cause) {

        return new DataSourceObjectFeign() {

            @Override
            public PageList searchDatagrid(Map<String, String> params) {
                return null;
            }

            @Override
            public DataSourceObjectDto saveOrUpdate(DataSourceObjectDto dto) {
                return null;
            }

            @Override
            public FeignJson doDelete(String id) {
                return null;
            }

            @Override
            public List<Map<String, String>> getAllDataSourceObject(String tabId) {
                return null;
            }

            @Override
            public DataSourceObjectDto queryDataSourceObjectDtoById(String id) {
                return null;
            }

            @Override
            public FeignJson saveAllInfo(Map<String, Object> requestMap) {
                return null;
            }

            @Override
            public List<DataSourceObjectDto> getAllDataByTabId(String tabId) {
                return null;
            }

            @Override
            public FeignJson dataRollBack(Map<String, Object> requestMap) {
                return null;
            }

            @Override
            public FeignJson updateOrReviseInfo(Map<String, Object> requestMap) {
                return null;
            }
        };
    }
}
