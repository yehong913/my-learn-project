/*
 * 文件名：AuthorityManager.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：TangJun
 * 修改时间：2015年9月22日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.config.auth;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.glaway.foundation.common.exception.NullParameterException;
import com.glaway.foundation.security.auth.DefaultCodeCombiner;
import com.glaway.foundation.security.auth.PermissionCodeCombiner;


/**
 * 项目库权限枚举值处理
 * @author blcao
 */
public class ProjLibAuthManager {

    /**
     * Description: <br>
     * 1、对一组权限进行转码<br>
     * 
     * @param actionList
     * @return
     * @see
     */
    public static long encodeAuthorityActions(List<ProjectLibraryAuthorityEnum> actionList) {
        if (actionList == null) {
            throw new NullParameterException();
        }
        long result = 0;
        if (actionList != null) {
            for (ProjectLibraryAuthorityEnum action : actionList) {
                int order = action.getOrder();
                // order代表的是在二进制表示中，从右数第几位
                result += 1 << (order - 1);
            }
        }
        return result;
    }

    /**
     * Description: <br>
     * 1、对一组权限进行转码<br>
     * 
     * @return
     * @see
     */
    public static long encodeAuthorityActions(ProjectLibraryAuthorityEnum[] actions) {
        if (actions == null) {
            throw new NullParameterException();
        }
        List<ProjectLibraryAuthorityEnum> actionList = Arrays.asList(actions);
        return encodeAuthorityActions(actionList);
    }

    /**
     * Description: <br>
     * 1、根据permissionCode解码权限<br>
     * 
     * @param permissionCode
     * @return
     * @see
     */
    public static List<ProjectLibraryAuthorityEnum> decodePermissionCode(long permissionCode) {
        if (permissionCode < 0) {
            throw new NullParameterException();
        }
        ProjectLibraryAuthorityEnum[] actionArray = ProjectLibraryAuthorityEnum.values();
        List<ProjectLibraryAuthorityEnum> actionList = new ArrayList<ProjectLibraryAuthorityEnum>();
        if (actionArray != null) {
            for (int i = 0; i < actionArray.length; i++ ) {
                ProjectLibraryAuthorityEnum action = actionArray[i];
                int order = action.getOrder();
                // 根据2进制的规则，当m%(2^(n))的值大于等于2^(n-1)时，m二进制表达式的从右第n位的值为1
                // 所以如果满足条件，则该order的行为是被允许的
                if ((permissionCode % (Math.pow(2, order))) >= (Math.pow(2, (order - 1)))) {
                    actionList.add(actionArray[i]);
                }
            }
        }
        return actionList;
    }

    /**
     * Description: <br>
     * 1、根据所给的permissionCode获得一组行为的是否可执行<br>
     * Implement: <br>
     * 1、先找到action<br>
     * 2、和所有的action匹配<br>
     * 
     * @return
     * @see
     */
    public static Map<ProjectLibraryAuthorityEnum, Boolean> getActionMapByPermissionCode(long permissionCode) {
        // 已有权限的行为
        List<ProjectLibraryAuthorityEnum> actionList = ProjLibAuthManager.decodePermissionCode(permissionCode);
        // 所有的行为
        ProjectLibraryAuthorityEnum[] actions = ProjectLibraryAuthorityEnum.values();
        // 所有行为是否有权限
        Map<ProjectLibraryAuthorityEnum, Boolean> result = new HashMap<ProjectLibraryAuthorityEnum, Boolean>();
        if (actions != null && actions.length > 0) {
            for (int i = 0; i < actions.length; i++ ) {
                ProjectLibraryAuthorityEnum action = actions[i];
                // 在已有权限的行为里能不能找到
                if (actionList.contains(action)) {
                    result.put(action, true);
                }
                else {
                    result.put(action, false);
                }
            }
        }
        return result;
    }

    /**
     * Description: <br>
     * 1、合并一组permissionCode<br>
     * Implement: <br>
     * 1、使用默认的合并算法来合并<br>
     * 2、…<br>
     * 
     * @param permissionCodeList
     * @return
     * @see
     */
    public static long combinePermissionCode(List<Long> permissionCodeList) {
        if (permissionCodeList == null) {
            throw new NullParameterException();
        }
        return combinePermissionCode(permissionCodeList, new DefaultCodeCombiner());
    }

    /**
     * Description: <br>
     * 1、根据所给的合并算法来合并一组permissionCode<br>
     * 
     * @param permissionCodeList
     * @param combiner
     * @return
     * @see
     */
    public static long combinePermissionCode(List<Long> permissionCodeList,
                                             PermissionCodeCombiner combiner) {
        if (permissionCodeList == null) {
            throw new NullParameterException();
        }
        long result = 0;
        if (permissionCodeList != null && permissionCodeList.size() > 0) {
            result = permissionCodeList.get(0);
            for (int i = 1; i < permissionCodeList.size(); i++ ) {
                result = combiner.combine(result, permissionCodeList.get(i));
            }
        }
        return result;
    }

    /**
     * Description: <br>
     * 1、获取权限的映射关系<br>
     * 
     * @return
     * @see
     */
    public static Map<String, ProjectLibraryAuthorityEnum> getAllAuthActionMap() {
        Map<String, ProjectLibraryAuthorityEnum> authorityActionMap = new HashMap<String, ProjectLibraryAuthorityEnum>();
        ProjectLibraryAuthorityEnum[] authArray = ProjectLibraryAuthorityEnum.values();
        for (ProjectLibraryAuthorityEnum auth : authArray) {
            authorityActionMap.put(auth.getActionCode(), auth);
        }
        return authorityActionMap;
    }

    /**
     * Description: <br>
     * 1、获取权限的编码列表<br>
     * 
     * @return
     * @see
     */
    public static List<String> getAllAuthActionCode() {
        List<String> authorityActionCodes = new ArrayList<String>();
        ProjectLibraryAuthorityEnum[] authArray = ProjectLibraryAuthorityEnum.values();
        for (ProjectLibraryAuthorityEnum auth : authArray) {
            authorityActionCodes.add(auth.getActionCode());
        }
        return authorityActionCodes;
    }

    /**
     * Description: <br>
     * 1、根据permissionCode解码权限<br>
     * 
     * @param permissionCode
     * @return
     * @see
     */
    public static List<String> decodePermissionCode(String permissionCode) {
        List<String> authorityActionCodes = new ArrayList<String>();
        List<ProjectLibraryAuthorityEnum> ProjectLibraryAuthorityEnums = decodePermissionCode(Long.valueOf(permissionCode));
        for (ProjectLibraryAuthorityEnum authorityAction : ProjectLibraryAuthorityEnums) {
            authorityActionCodes.add(authorityAction.getActionCode());
        }
        return authorityActionCodes;
    }


}
