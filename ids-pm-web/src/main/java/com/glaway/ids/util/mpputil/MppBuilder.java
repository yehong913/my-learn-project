package com.glaway.ids.util.mpputil;
import java.io.InputStream;
import java.util.List;

import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Task;

import com.glaway.foundation.common.exception.GWException;


/*
 * 文件名：MppToListBuilderI.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年3月26日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

/**
 * 〈MPP读取接口〉
 * 〈功能详细描述〉
 * 
 * @author duanpengfei
 * @version 2015年3月26日
 * @see MppBuilder
 * @since
 */

public interface MppBuilder {

    /**
     * Description: <br>
     * 通过Mpp流建造列对应的产品
     * @param inputStream
     * @throws GWException
     * @see
     */
    void buildClumn(InputStream inputStream) throws GWException;

    /**
     * Description: <br>
     * 返回产品
     * @return 
     * @see 
     */
    List<Task> retrieveResult();
    
    /**
     * Description: <br>
     * 通过数据组装文件<br>
     * @param key
     * @param mppList 
     * @param projectId
     * @throws GWException
     * @see 
     */
    void bulidFile(String key, List<Object> mppList, String projectId) throws GWException;
    
    /**
     * Description: <br>
     * 返回文件<br>
     * 
     * @return 
     * @see 
     */
    ProjectFile retrieveFile();
}
