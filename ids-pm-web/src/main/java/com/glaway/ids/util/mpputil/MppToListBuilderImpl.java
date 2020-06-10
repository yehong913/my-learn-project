package com.glaway.ids.util.mpputil;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.glaway.foundation.common.exception.GWException;

import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Task;


/*
 * 文件名：MppToListBuilderImpl.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年3月26日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

/**
 * 〈MPP读取实现类〉
 * 〈功能详细描述〉
 * 
 * @author duanpengfei
 * @version 2015年3月26日
 * @see MppToListBuilderImpl
 * @since
 */

public class MppToListBuilderImpl implements MppBuilder {
    
    Map<String,Object> mppMap = new HashMap<String,Object>();
    
    public MppToListBuilderImpl(){
        mppMap.put(MppConstants.IDS_PLANTEMPLATE_MPP, new MppParseUtil());
    }

    private List<Task> list = new ArrayList<Task>();

    private ProjectFile file = new ProjectFile();

    @Override
    public void buildClumn(InputStream inputStream) throws GWException{
        MppParseUtil mppParse = new MppParseUtil();
        list = mppParse.getListFromMPP(inputStream);
    }

    @Override
    public List<Task> retrieveResult() {
        return list;
    }

    @Override
    public void bulidFile(String key,List<Object> mppList,String type)  throws GWException{
        file = getMppParse(key).saveFile(mppList,type);
    }

    @Override
    public ProjectFile retrieveFile() {
        return file;
    }
    
    /**
     * Description: 
     * 
     * @param key
     * @return 
     * @see 
     */
    private MppParseI getMppParse(String key) {
        MppParseI mppParse = (MppParseI)mppMap.get(key);
        return mppParse;
    }

}
