package com.glaway.ids.common.service;



/**
 * 
 * 插件验证接口服务
 * @author blcao
 * @version 2019年1月7日
 * @see PluginValidateServiceI
 * @since
 */
public interface PluginValidateServiceI {
    
    /**
     * 
     * 插件是否有效
     * 
     * @param pluginName
     * @return 
     * @see
     */
    boolean isValidatePlugin(String pluginName);

    /**
     * 获取服务对应的路径
     * @param pluginName
     * @return
     */
    String getServicePath(String pluginName);
}
