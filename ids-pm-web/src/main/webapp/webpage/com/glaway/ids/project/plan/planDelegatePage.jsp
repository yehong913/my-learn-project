<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>计划委派</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

</head>
<script type="text/javascript">
    function delegatePlan(){
        var delegateUserId = $("#delegateUserId").combobox('getValue');
        var leaderId = $("#leaderId").val();
        var departLeaderId = $("#departLeaderId").val();
        var changeType = $("#changeType").combobox('getValue');
        var changeRemark = $("#changeRemark").textbox('getValue');
        if(delegateUserId == ""){
            top.tip("被委派人不能为空");
            return false;
        }
        if(changeType == ""){
            top.tip("变更类别不能为空");
            return false;
        }
        if(leaderId == ""){
            top.tip("室领导不能为空");
            return false;
        }
        if(departLeaderId == ""){
            top.tip("部门领导不能为空");
            return false;
        }
        $.ajax({
            type : 'POST',
            data : {
                planId : '${planId}',
                delegateUserId : delegateUserId,
                changeType : changeType,
                leaderId : leaderId,
                departLeaderId : departLeaderId,
                changeRemark : changeRemark,
                flag : 'delegate'
            },
            url : 'planController.do?delegatePlan',
            async : false,
            cache :false,
            success : function(data){
                var d = $.parseJSON(data);
                if(d.success){
                    top.tip("委派申请提交成功");
                    //刷新主页面

                    setTimeout(function(){
                        refreshTabsByTabsName('待办任务,首页');
                        //$.fn.lhgdialog("closeAll");
                    },500);
                    setTimeout(function(){
                        $.fn.lhgdialog("closeAll");
                        //$.fn.lhgdialog("closeAll");
                    },1000);

                }else{
                    top.tip("委派申请提交失败");
                    return false;
                }
            }
        });
    }


    function startPlanDelegateProcessSubmit(){
        var delegateUserId = $("#delegateUserId").combobox('getValue');
        var leaderId = $("#leaderId").val();
        var departLeaderId = $("#departLeaderId").val();
        var changeType = $("#changeType").combobox('getValue');
        var changeRemark = $("#changeRemark").textbox('getValue');
        if(delegateUserId == ""){
            top.tip("被委派人不能为空");
            return false;
        }
        if(changeType == ""){
            top.tip("变更类别不能为空");
            return false;
        }
        if(leaderId == ""){
            top.tip("室领导不能为空");
            return false;
        }
        if(departLeaderId == ""){
            top.tip("部门领导不能为空");
            return false;
        }
        $.ajax({
            type : 'POST',
            data : {
                planId : '${planId}',
                delegateUserId : delegateUserId,
                changeType : changeType,
                leaderId : leaderId,
                departLeaderId : departLeaderId,
                changeRemark : changeRemark
            },
            url : 'planController.do?startPlanDelegateProcess',
            async : false,
            cache :false,
            success : function(data){
                var d = $.parseJSON(data);
                if(d.success){
                    top.tip("委派申请提交成功");
                    setTimeout(function(){
                        var currTab = top.getTabById('${planId}');
                        top.tabRefresh(currTab);
                        //刷新主页面
                        var currTab=top.getTabById('${tabId}');
                        top.tabRefresh(currTab);
                        $.fn.lhgdialog("closeSelect");
                        $.fn.lhgdialog("closeAll");
                    },500);

                }else{
                    top.tip("委派申请提交失败");
                    return false;
                }
            }
        });
    }

</script>
<body>
<fd:form id="planDelegateForm">
    <c:choose>
        <c:when test="${empty temporyPlanId}">
            <%--<fd:inputSearchUser id="delegateUserId" name="delegateUserId" title="被委派人"  required="true" value="${delegateUserId}">

            </fd:inputSearchUser>--%>
            <fd:combobox id="delegateUserId" textField="realName" title="被委派人" selectedValue="${delegateUserId}"
                         editable="true" valueField="id" required="true"
                         panelMaxHeight="200" url="planController.do?userList2&projectId=${TemporaryPlan_.projectId}" />
            <fd:combotree title="{com.glaway.ids.pm.project.plan.changeType}" treeIdKey="id" name="changeType"
                          url="planChangeMassController.do?planChangeCategorylList" id="changeType" treePidKey="parentId"
                          editable="false" multiple="false" panelHeight="200" treeName="name" required="true"
                          prompt="请选择"></fd:combotree>
            <fd:inputSearchUser id="leaderId" name="leaderId" title="室领导"  required="true" value="${leaderId}">
            </fd:inputSearchUser>
            <fd:inputSearchUser id="departLeaderId" name="departLeaderId" title="部门领导"  required="true" value="${departLeaderId}"/>
            <fd:inputTextArea id="changeRemark" title="{com.glaway.ids.common.lable.remark}" name="changeRemark"/>
        </c:when>
        <c:otherwise>
            <fd:inputText id="planName" name = "planName" title="计划名称" readonly="true" value="${TemporaryPlan_.planName}" ></fd:inputText>

            <fd:combotree title="{com.glaway.ids.pm.project.plan.changeType}" treeIdKey="id" name="changeType" readonly="true" value="${TemporaryPlan_.changeTypeInfo.name}"
                          url="planChangeMassController.do?planChangeCategorylList" id="changeType" treePidKey="parentId"
                          editable="false" multiple="false" panelHeight="200" treeName="name" required="true"
                          prompt="请选择"></fd:combotree>
            <fd:inputSearchUser id="createBy" name="createBy" title="计划负责人"  required="true" value="${TemporaryPlan_.createBy}" readonly="true">
            </fd:inputSearchUser>
          <%--  <fd:inputSearchUser id="delegateUserId" name="delegateUserId" title="被委派人"  required="true" value="${TemporaryPlan_.owner}" readonly="true">
            </fd:inputSearchUser>--%>
            <fd:combobox id="delegateUserId" textField="realName" title="被委派人" selectedValue="${TemporaryPlan_.owner}" value="${TemporaryPlan_.owner}"
                         editable="true" valueField="id" required="true" readonly="true"
                         panelMaxHeight="200" url="planController.do?userList2&projectId=${TemporaryPlan_.projectId}" />
            <fd:inputTextArea id="changeRemark" title="{com.glaway.ids.common.lable.remark}" name="changeRemark" readonly="true" value="${TemporaryPlan_.changeRemark}"/>
        </c:otherwise>
    </c:choose>

</fd:form>

</body>