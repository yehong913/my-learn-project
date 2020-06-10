package com.glaway.ids.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.ids.common.service.PluginValidateServiceI;

/**
 * 
 * 插件验证接口实现服务
 * @author blcao
 * @version 2019年1月7日
 * @see PluginValidateServiceImpl
 * @since
 */
@Service("pluginValidateService")
@Transactional
public class PluginValidateServiceImpl implements PluginValidateServiceI{

    /**
     * 接口
     */
    @Autowired
    DiscoveryClient discoveryClient;
   
    @Override
    public boolean isValidatePlugin(String pluginName) {
        boolean pluginValid = false;
        if(!CommonUtil.isEmpty(pluginName)){
            List<ServiceInstance> inss = discoveryClient.getInstances(pluginName);
            if (inss.size()>0) {                
                pluginValid = true;
            }
        }
        return pluginValid;
    }

    @Override
    public String getServicePath(String pluginName) {
        String path = "";
        List<ServiceInstance> inss = discoveryClient.getInstances(pluginName);
        if (!CommonUtil.isEmpty(inss)) {
            EurekaDiscoveryClient.EurekaServiceInstance serviceIns = (EurekaDiscoveryClient.EurekaServiceInstance) inss.get(0);
            path = serviceIns.getInstanceInfo().getInstanceId().toString();
        }
        return path;
    }
}
