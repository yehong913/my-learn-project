package com.glaway.ids.util.mpputil;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.glaway.foundation.fdk.dev.service.threemember.FeignUserService;
import com.glaway.ids.config.constant.ConfigTypeConstants;
import net.sf.mpxj.Day;
import net.sf.mpxj.Duration;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectCalendar;
import net.sf.mpxj.ProjectCalendarHours;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.RelationType;
import net.sf.mpxj.Resource;
import net.sf.mpxj.ResourceAssignment;
import net.sf.mpxj.Task;
import net.sf.mpxj.TaskMode;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.reader.ProjectReader;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.log.BaseLog;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.common.util.DateUtil;
import com.glaway.foundation.common.util.I18nUtil;


/**
 * 〈Mpp读取方法〉 〈功能详细描述〉
 * 
 * @author duanpengfei
 * @version 2015年3月26日
 * @see MppParseUtil
 * @since
 */
public class MppParseUtil implements MppParseI
{
    private static final String NATURE_DAY = "自然日";

    private static final String WORK_DAY = "工作日";

    private static final BaseLog LOG = BaseLogFactory.getSystemLog(MppParseUtil.class);

    public static Map<String, Object> columnIndexMap = new HashMap<String, Object>();

    @Autowired
    private FeignUserService userService;

    static
    {
        // 级别
        columnIndexMap.put(ConfigTypeConstants.PLANLEVEL, 1);
        // 交付项
        columnIndexMap.put(ConfigTypeConstants.DELIVER_STANDARDNAME, 2);
        columnIndexMap.put(ConfigTypeConstants.OWNERNAME, 3);
        // 前置缓存
        columnIndexMap.put(ConfigTypeConstants.PREPOSECACHE, 20);
        // 前置缓存
        columnIndexMap.put(ConfigTypeConstants.TASK_NAME_TYPE, 22);
    }

    @Override
    public List<Task> getListFromMPP(InputStream inputStream)
        throws GWException
    {
        List<Task> taskList = new ArrayList<Task>();
        try
        {
            // new MSPDIReader();
            ProjectReader reader = new MPPReader();
            ProjectFile projectFile = reader.read(inputStream);
            taskList = projectFile.getAllTasks();
        }
        catch (MPXJException e)
        {
            LOG.error(I18nUtil.getValue("com.glaway.ids.common.operation.mppParse.mppParseError"), e);
            throw new GWException(I18nUtil.getValue("com.glaway.ids.common.operation.mppParse.mppParseError"));
        }
        finally
        {
            try
            {
                inputStream.close();
            }
            catch (IOException e)
            {
                LOG.error(I18nUtil.getValue("com.glaway.ids.common.operation.iostream.closeError"), e);
                throw new GWException(I18nUtil.getValue("com.glaway.ids.common.operation.iostream.closeError"));
            }
        }
        return taskList;
    }

    @Override
    public ProjectCalendar getWorkTimeSets(ProjectFile file, String type)
    {
        ProjectCalendar projectCalendar = file.addDefaultBaseCalendar();
        projectCalendar.setName(type);
        if (WORK_DAY.equals(type))
        {
            projectCalendar.setName("工作日历");
            projectCalendar.setWorkingDay(Day.SATURDAY, false);
            projectCalendar.setWorkingDay(Day.SUNDAY, false);
            ProjectCalendarHours saturday = projectCalendar.addCalendarHours(Day.SATURDAY);
            saturday.addRange(ProjectCalendar.DEFAULT_WORKING_MORNING);
            saturday.addRange(ProjectCalendar.DEFAULT_WORKING_AFTERNOON);
            ProjectCalendarHours sunday = projectCalendar.addCalendarHours(Day.SUNDAY);
            sunday.addRange(ProjectCalendar.DEFAULT_WORKING_MORNING);
            sunday.addRange(ProjectCalendar.DEFAULT_WORKING_AFTERNOON);
        }
        else if (NATURE_DAY.equals(type))
        {
            projectCalendar.setWorkingDay(Day.SATURDAY, true);
            projectCalendar.setWorkingDay(Day.SUNDAY, true);
            ProjectCalendarHours saturday = projectCalendar.addCalendarHours(Day.SATURDAY);
            saturday.addRange(ProjectCalendar.DEFAULT_WORKING_MORNING);
            saturday.addRange(ProjectCalendar.DEFAULT_WORKING_AFTERNOON);
            ProjectCalendarHours sunday = projectCalendar.addCalendarHours(Day.SUNDAY);
            sunday.addRange(ProjectCalendar.DEFAULT_WORKING_MORNING);
            sunday.addRange(ProjectCalendar.DEFAULT_WORKING_AFTERNOON);
        }
        else
        {
            // projectCalendar.addCalendarException(fromDate, toDate);特殊日期设置
            projectCalendar.setName("公司日历");
            projectCalendar.setWorkingDay(Day.SATURDAY, false);
            projectCalendar.setWorkingDay(Day.SUNDAY, false);
            ProjectCalendarHours saturday = projectCalendar.addCalendarHours(Day.SATURDAY);
            saturday.addRange(ProjectCalendar.DEFAULT_WORKING_MORNING);
            saturday.addRange(ProjectCalendar.DEFAULT_WORKING_AFTERNOON);
            ProjectCalendarHours sunday = projectCalendar.addCalendarHours(Day.SUNDAY);
            sunday.addRange(ProjectCalendar.DEFAULT_WORKING_MORNING);
            sunday.addRange(ProjectCalendar.DEFAULT_WORKING_AFTERNOON);
        }
        return projectCalendar;
    }

    @Override
    public ProjectFile saveFile(List<Object> mppList, String type)
        throws GWException
    {
        ProjectFile file = new ProjectFile();
        Map<String, Object> paraMap = new HashMap<String, Object>();
        int index = 0;
        for (Object obj : mppList)
        {
            MppInfo mppInfo = (MppInfo)obj;
            try
            {
                if (mppInfo.getId() == 0)
                {
                    continue;
                }
                Task newTask = null;

                if (mppInfo.getParentId() != null
                    && !CommonUtil.mapIsEmpty(paraMap, mppInfo.getParentId()))
                {
                    Task parentTask = (Task)paraMap.get(mppInfo.getParentId());
                    newTask = parentTask.addTask();
                }
                else
                {
                    newTask = file.addTask();
                }
                if (index == 0)
                {
                    if (StringUtils.isNotEmpty(type))
                    {
                        ProjectCalendar projectCalendar = getWorkTimeSets(file, type);
                        newTask.setCalendar(projectCalendar);
                    }
                }
                index++ ;
                newTask.setID(mppInfo.getId());

                newTask.setName(mppInfo.getName());
                newTask.setDurationText(mppInfo.getDuration());
                if (mppInfo.getMilestone() != null)
                {
                    newTask.setMilestone(mppInfo.getMilestone());
                }

                if (mppInfo.getStartTime() != null)
                {
                    String start = DateUtil.getStringFromDate(mppInfo.getStartTime(), "yyyy-MM-dd");
                    newTask.setStart(java.sql.Date.valueOf(start));
                    newTask.setStartText(start);
                }
                String end = "";
                if (mppInfo.getEndTime() != null)
                {
                    end = DateUtil.getStringFromDate(mppInfo.getEndTime(), "yyyy-MM-dd");
                    newTask.setFinish(java.sql.Date.valueOf(end));
                    newTask.setFinishText(end);
                }

                newTask.setText((int)MppParseUtil.columnIndexMap.get(ConfigTypeConstants.TASK_NAME_TYPE), mppInfo.getTaskNameType());

                newTask.setText(
                    (int)MppParseUtil.columnIndexMap.get(ConfigTypeConstants.PLANLEVEL),
                    mppInfo.getPlanLevel());
                newTask.setTaskMode(TaskMode.MANUALLY_SCHEDULED);
                newTask.setText(
                    (int)MppParseUtil.columnIndexMap.get(ConfigTypeConstants.DELIVER_STANDARDNAME),
                    mppInfo.getDocumentName());

                newTask.setText(
                    (int)MppParseUtil.columnIndexMap.get(ConfigTypeConstants.PREPOSECACHE),
                    mppInfo.getPreposeName());

                if (NATURE_DAY.equals(type))
                {
                    Resource resource = file.addResource();
                    resource.setName(" ");
                    ResourceAssignment resourceAssignment = newTask.addResourceAssignment(resource);
                    resourceAssignment.setFinish(java.sql.Date.valueOf(end));
                }

                // 计划上级的ID保存计划模板详细的ID
                paraMap.put(newTask.getID().toString(), newTask);
            }
            catch (Exception e)
            {
                LOG.warn(I18nUtil.getValue("com.glaway.ids.common.operation.mppParse.mppBuildError"), e);
                throw new GWException("数据名称为【" + mppInfo.getName() + "】,组装MPP文件失败");
            }

        }

        // 给前置赋值
        List<Task> taskList = file.getAllTasks();
        for (Task task : taskList)
        {
            String preposeName = task.getText((int)MppParseUtil.columnIndexMap.get(ConfigTypeConstants.PREPOSECACHE));
            if (StringUtils.isNotEmpty(preposeName))
            {
                String[] preposeStr = preposeName.split("[,]");
                for (String str : preposeStr)
                {
                    Task preposeTask = (Task)paraMap.get(str);
                    task.addPredecessor(preposeTask, RelationType.FINISH_START, null);
                }
            }
        }

        return file;
    }

    private void setActualDuration(String duration) {
        // TODO Auto-generated method stub
        
    }
}
