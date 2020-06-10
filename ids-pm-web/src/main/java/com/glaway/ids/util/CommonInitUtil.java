package com.glaway.ids.util;

import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.entity.GLObject;
import com.glaway.foundation.common.util.ResourceUtil;
import com.glaway.foundation.common.util.UserUtil;
import com.glaway.foundation.common.vdata.GLVData;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * @Description: 初始化人员&时间
 * @author: sunmeng
 * @ClassName: CommonInitUtil
 * @Date: 2019/7/2-17:05
 * @since
 */
public class CommonInitUtil {

    public static void initGLVDataForCreate(GLVData data) {
        TSUserDto curUser = UserUtil.getCurrentUser();
        data.setCreateBy(curUser.getId());
        data.setCreateFullName(curUser.getRealName());
        data.setCreateName(curUser.getUserName());
        data.setCreateTime(new Date());
        //防止管理员处理数据报错
        if (StringUtils.isNotBlank(ResourceUtil.getCurrentUserOrg().getId())) {
            data.setCreateOrgId(ResourceUtil.getCurrentUserOrg().getId());
        }
    }

    public static void initGLVDataForUpdate(GLVData data) {
        TSUserDto curUser = UserUtil.getCurrentUser();
        data.setUpdateBy(curUser.getId());
        data.setUpdateFullName(curUser.getRealName());
        data.setUpdateName(curUser.getUserName());
        data.setUpdateTime(new Date());
    }

    public static void initGLObjectForCreate(GLObject data) {
        TSUserDto curUser = UserUtil.getCurrentUser();
        data.setCreateBy(curUser.getId());
        data.setCreateFullName(curUser.getRealName());
        data.setCreateName(curUser.getUserName());
        data.setCreateTime(new Date());
        //防止管理员处理数据报错
        if (StringUtils.isNotBlank(ResourceUtil.getCurrentUserOrg().getId())) {
            data.setCreateOrgId(ResourceUtil.getCurrentUserOrg().getId());
        }
    }

    public static void initGLObjectForUpdate(GLObject data) {
        TSUserDto curUser = UserUtil.getCurrentUser();
        data.setUpdateBy(curUser.getId());
        data.setUpdateFullName(curUser.getRealName());
        data.setUpdateName(curUser.getUserName());
        data.setUpdateTime(new Date());
    }
}
