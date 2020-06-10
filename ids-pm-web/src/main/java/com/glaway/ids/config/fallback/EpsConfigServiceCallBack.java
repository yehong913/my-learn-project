package com.glaway.ids.config.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.config.service.EpsConfigRemoteFeignServiceI;
import com.glaway.ids.config.vo.EpsConfig;

import feign.hystrix.FallbackFactory;

@Component
public class EpsConfigServiceCallBack implements FallbackFactory<EpsConfigRemoteFeignServiceI> {

    @Override
    public EpsConfigRemoteFeignServiceI create(Throwable throwable) {
        return new EpsConfigRemoteFeignServiceI(){

            @Override
            public String getEpsConfig(String id) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void addEpsConfig(EpsConfig epsConfig) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public String searchTreeNode(EpsConfig epsConfig) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void getEpsParentList(Map<String, Object> mapList) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public int getMaxEpsConfigPlace() {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public void saveOrUpdateEpsConfig(EpsConfig epsConfig) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public String getParentNode(EpsConfig epsConfig) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void modify(EpsConfig epsConfig, boolean nochange) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public FeignJson getEpsconfigList(EpsConfig epsConfig) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void doBatchDel(String ids) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void doBatchStartOrStop(String ids, String state) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public String getList() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public FeignJson getEpsNamePathById(String id) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getEpsTreeNodes() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getTreeNodes(EpsConfig epsConfig) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public FeignJson doBatchDelIsHaveChildList(String ids) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public FeignJson doUpdate(EpsConfig epsConfig) {
                // TODO Auto-generated method stub
                return null;
            }

		};
    }
}
