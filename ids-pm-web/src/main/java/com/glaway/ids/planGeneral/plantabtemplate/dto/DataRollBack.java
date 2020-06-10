package com.glaway.ids.planGeneral.plantabtemplate.dto;

import com.glaway.foundation.common.vdata.GLVData;

/**
 * 数据源对象回滚
 * @Date: 2019/8/28
 */
public class DataRollBack extends GLVData {

    private String id;

    private String status;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DataRollBack{" + "id='" + id + '\'' + ", status='" + status + '\'' + '}';
    }
}
