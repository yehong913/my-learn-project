package com.glaway.ids.util.mpputil;
import java.io.InputStream;
import java.util.List;

import com.glaway.foundation.common.exception.GWException;

import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Task;


/*
 * 文件名：Director.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年3月26日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

/**
 * 〈组装Mpp数据〉
 * 〈导入Mpp组装数据〉
 * 
 * @author Administrator
 * @version 2015年3月26日
 * @see MppDirector
 * @since
 */

public class MppDirector {

    /**
     * Description: <br>
     * 导入Mpp流后组装数据返回
     * @param inputStream
     * @return 
     * @see 
     */
    public List<Task> construct(InputStream inputStream) throws GWException{
        MppBuilder builder = new MppToListBuilderImpl();
        builder.buildClumn(inputStream);
        return builder.retrieveResult();
    }
    
    
    /**
     * Description: <br>
     * 通过数据组成文件<br>
     * 
     * @return
     * @see 
     */
    public ProjectFile getMppFile(String key,List mppList,String type) throws GWException{
        MppBuilder builder = new MppToListBuilderImpl();
        builder.bulidFile(key, mppList,type);
        return builder.retrieveFile();
    }
}
