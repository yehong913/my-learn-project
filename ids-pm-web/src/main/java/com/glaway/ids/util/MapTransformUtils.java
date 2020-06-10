package com.glaway.ids.util;

import com.alibaba.fastjson.JSON;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

/**
 * @Description: (map和bean之间的相互转换)
 * @author: sunmeng
 * @ClassName: MapTransformUtils
 * @Date: 2019/6/4-13:12
 * @since
 */
public class MapTransformUtils {

    /**
     * (map转bean)
     * @param map
     * @param beanClass
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T mapTOObject(Map<String,Object> map,Class<T> beanClass) throws Exception {
        if (map == null) {
            return null;
        }
        T obj = beanClass.newInstance();
        BeanUtils.populate(obj,map);

        return obj;
    }


    /**
     * (bean转map)
     * @param obj
     * @return
     */
    public static Map<?,?> objectToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        return new org.apache.commons.beanutils.BeanMap(obj);
    }

    /**
     * map转AjaxJson
     * @param obj
     * @return
     */
    public static AjaxJson mapToAjaxJson(Object obj) {
        AjaxJson j = new AjaxJson();
        if (obj == null) {
            return j;
        }
        j = JSON.parseObject(JSON.toJSONString(obj),AjaxJson.class);
        return j;
    }
}
