package com.glaway.ids.planGeneral.plantabtemplate.utils;

import com.alibaba.fastjson.JSON;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：Datagrid数据展示
 * @Date: 2019/8/27
 */
public class DatagridStrUtils {

    public static String getDatagridStr(PageList pageList, boolean isCount){
        List resultList = new ArrayList();
        String datagridStr = "{\"rows\":" + "[{}]" + ",\"total\":" + 0 + "}";
        long count = 0;
        if (pageList!=null&&pageList.getResultList()!=null){
            resultList = pageList.getResultList();
            if (isCount){
                count = pageList.getCount();
            }else{
                count = resultList.size();
            }
            datagridStr = "{\"rows\":" + JSON.toJSONString(resultList) + ",\"total\":" + count + "}";
        }
        return datagridStr;
    }

}
