/*
 * 文件名：MppParseI.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年4月2日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.util.mpputil;

import java.io.InputStream;
import java.util.List;

import net.sf.mpxj.ProjectCalendar;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Task;

import com.glaway.foundation.common.exception.GWException;

/**
 * 〈Mpp读取方法接口〉
 * 〈功能详细描述〉
 * @author Administrator
 * @version 2015年4月2日
 * @see MppParseI
 * @since
 */

public interface MppParseI {
    
    /**
     * 通过文件流获得MPP数据
     * 
     * @param inputStream
     * @return
     * @throws GWException 
     * @see 
     */
    List<Task> getListFromMPP(InputStream inputStream) throws GWException;
    
    /**
     * 通过数据组装MPP文件
     * 
     * @param mppList
     * @param type
     * @return
     * @throws GWException 
     * @see 
     */
    ProjectFile saveFile(List<Object> mppList, String type) throws GWException;

    /**
     * 获取日期设置
     * 
     * @param file
     * @param type
     * @return
     * @throws GWException 
     * @see 
     */
    ProjectCalendar getWorkTimeSets(ProjectFile file, String type);
}
