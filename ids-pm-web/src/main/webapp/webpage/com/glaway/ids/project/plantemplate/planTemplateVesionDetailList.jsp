<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>版本信息</title>
<t:base type="jquery,easyui_iframe,tools"></t:base>
</head>
<body>
	<fd:panel  border="false" title="版本详情" collapsible="false" fit="false" width="100%">
        <fd:datagrid url="planTemplateDetailController.do?getVersionHistory&bizId=${planTemplate_.bizId}" width="100%"
                idField="id" id="planTmplListss" fitColumns="true" fit="false" checkbox="false">
            <fd:dgCol title="{com.glaway.ids.common.lable.operation}" field="operation" width="30"  formatterFunName="operation" />
            <fd:dgCol title="{com.glaway.ids.pm.project.plantemplate.bizVersion}" field="bizVersion" width="30" formatterFunName="bizVersionFormatter"/>
            <fd:dgCol title="{com.glaway.ids.pm.project.plantemplate.version.modifyPeople}" field="creator" width="48" />
            <fd:dgCol title="{com.glaway.ids.pm.project.plantemplate.version.modifyTime}" field="createTime" width="75" />
            <fd:dgCol title="{com.glaway.ids.common.lable.status}" field="bizCurrent" width="36"  formatterFunName="statusFormatter"/>
            <fd:dgCol title="{com.glaway.ids.common.lable.remark}" field="remark" width="75" />
        </fd:datagrid>
    </fd:panel>
    
    <fd:panel  border="false" title="操作详情" collapsible="false" fit="false" width="100%">
        <fd:datagrid url="planTemplateDetailController.do?getOptLogList&bizId=${planTemplate_.bizId}" width="100%"
                idField="id" id="planTmplList" fit="false" fitColumns="true" checkbox="false" pagination="false">
            <fd:dgCol title="{com.glaway.ids.pm.project.plantemplate.version.logInfo}" field="logInfo" width="290" />   
            <fd:dgCol title="{com.glaway.ids.pm.project.plantemplate.version.operator}" field="createName" /> 
            <fd:dgCol title="{com.glaway.ids.pm.project.plantemplate.version.operaTime}" field="createTime" width="100" />
        </fd:datagrid>
    </fd:panel>
    
    <fd:dialog id="showVersionDetail" title="{com.glaway.ids.pm.project.plantemplate.showDetail}" width="940" height="500" modal="true">
        <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" id="showVersionDetail_closeBtn" callback="hideDialog"></fd:dialogbutton>
    </fd:dialog>
</body>
<script src="webpage/com/glaway/ids/project/plantemplate/planTemplateList.js"></script>
<script type="text/javascript">
	//查询计划模板状态
	function statusFormatter(val, row, value){
	    var status;
	    if(row.bizCurrent=="qiyong"){        
	        status = "启用";
	    }else if(row.bizCurrent=="jinyong"){
	        status = "禁用";
	    }else if(row.bizCurrent=="nizhi"){
	        status ="拟制中";
	    }else if(row.bizCurrent=="shenhe"){
	        status ="审批中";
	    }else if(row.bizCurrent=="xiuding"){
	        status ="修订中";
	    }
	    //如果没有流程，则不需要点击
	    var resultUrl = status;
	    if(row.processInstanceId != null && row.processInstanceId != '' && row.processInstanceId != undefined){
	        resultUrl = '<a href="#" onclick="openFlowTasks(\''
	            + row.processInstanceId + '\')"><font color=blue>'+status+'</font></a>';
	    }
	    return resultUrl;
	}
	
	//弹出对象对应的流程
	function openFlowTasks(procInstId){
	    var tabTitle = '计划模板工作流';
	    createdetailwindow(tabTitle,
	            'generateFlowDiagramController.do?initDrowFlowActivitiDiagram'
	                    + '&procInstId=' + procInstId, 800, 600);
	}
	
	function operation(val, row, index){
	    var returnStr ='<a onclick="showVersionDetail(\''+row.id+'\')" class="basis ui-icon-eye" title="查看" style="display: inline-block;cursor:hand;"></a>';
	    return returnStr;
	}

	function showVersionDetail(id){
		var dialogUrl = 'planTemplateDetailController.do?planTemplateDetail&planTemplateId='
            + id;
        createDialog('showVersionDetail',dialogUrl);
	}
	
	function bizVersionFormatter(val, row, index){
	    var returnStr ='<span style="cursor:hand"><a style="color:blue" onclick="showVersionDetail2(\''+row.id+'\',\''+val+'\')">'+val+'</a></span>';
	    return returnStr;
	}

	function showVersionDetail2(id,val){
        var url='${webRoot}/planTemplateController.do?goShowDetail&id='+id;
        createdetailwindow(val+'版本详情',
	            url, 940, 500);
//         $("#showVersionDetail").lhgdialog("open","url:"+url);	        
	}
</script>
</html>

