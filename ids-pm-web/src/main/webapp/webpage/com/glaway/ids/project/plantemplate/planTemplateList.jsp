<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>计划模板</title>
	<t:base type="jquery,easyui,tools"></t:base>

	<style>
		.myDisableImg{
			filter:alpha(opacity=40);
			opacity:0.4;
			margin-right:10px
		}
		.myAbleImg{
			margin-right:10px;
		}
	</style>
</head>
<body style="overflow: hidden;">
<div class="easyui-layout" fit="true">
<input id="currentUserId" name="currentUserId" type="hidden" value="${currentUserId}">
	<div region="center" style="padding: 1px;">
		<div id="planTemplateList" style="padding: 3px;height: auto">
			 <fd:searchform id="planTemplateTag" onClickSearchBtn="searchPlanTemplate()" onClickResetBtn="tagSearchResetForPlanTemplate()" help="helpDoc:PlanTemplate">
				<fd:inputText title="{com.glaway.ids.pm.project.plantemplate.name}" maxLength="-1" name="name" id="name" queryMode="like"/>
				<fd:inputText title="{com.glaway.ids.common.lable.creator}" maxLength="-1" name="createName" id="planTemplateCreateName"/>
				<fd:inputDateRange id="createTime" interval="1" name="PlanTemplate.createTime" title="{com.glaway.ids.common.lable.createtime}"></fd:inputDateRange>
				<%-- <fd:inputDateTime name="PlanTemplate.createTime" title="创建时间" id="createTime" editable="false" queryMode="le&&ge&&lt&&gt&&eq"></fd:inputDateTime> --%>
				<fd:combobox id="PlanTemplateBizCurrent" maxLength="-1" textField="title" title="{com.glaway.ids.common.lable.status}" name="PlanTemplate.bizCurrent" 
					multiple="true" editable="false" prompt="全部" valueField="name" url="planTemplateController.do?lifeCycleStatus" queryMode="in"></fd:combobox>
		     </fd:searchform>
			<fd:toolbar>
				<fd:toolbarGroup align="left">
					<fd:linkbutton id="addPlanBtn" onclick="addPlanTemplate()" value="新增" operationCode = "planTemplateAddCode"
						iconCls="basis ui-icon-plus" />
					<%-- <fd:linkbutton
						onclick="add1('导入','planTemplateController.do?goImport&currentUserId=${currentUserId}','plantemplates',730,320)"
						value="{com.glaway.ids.common.msg.import}" iconCls="basis ui-icon-import" operationCode="planTemplateImportCode"/> --%>
					<fd:linkbutton
						onclick="deleteALLSelectForTemp('批量删除','planTemplateController.do?doBatchDel','plantemplates',null,null)"
						value="{com.glaway.ids.common.msg.delete}" iconCls="basis ui-icon-minus" operationCode="planTemplateDeleteCode" />
<%-- 					<fd:linkbutton
						onclick="planTemplateExport('planTemplateController.do?doExport','plantemplates')"
						value="{com.glaway.ids.common.msg.export}" iconCls="basis ui-icon-output" operationCode="planTemplateExportCode" /> --%>
					<%--	<fd:linkbutton
						onclick="goSubmitApprove('planTemplateController.do?goSubmitApprove','plantemplates')"
						value="提交审批" iconCls="icon-ok" /> --%>
					<fd:linkbutton
						onclick="doBatchStatusChange('启用')"
						value="{com.glaway.ids.common.start}" iconCls="basis ui-icon-enable" operationCode="planTemplateEnableCode" />
					<fd:linkbutton
						onclick="doBatchStatusChange('禁用')"
						value="{com.glaway.ids.common.stop}" iconCls="basis ui-icon-forbidden"  operationCode="planTemplateDisableCode" />
				</fd:toolbarGroup>
			</fd:toolbar>
		</div>
		
		<fd:datagrid url="planTemplateController.do?conditionSearch&field=id"
			toolbar="#planTemplateList" checkbox="true" idField="id"
			id="plantemplates" style="height: 300px;" fit="true" fitColumns="true">
			<fd:colOpt title="{com.glaway.ids.common.lable.operation}" >
			 		<fd:colOptBtn tipTitle="修改" iconCls="basis ui-icon-pencil" hideOption="hideUpdate${entry}()"  onClick="doUpdate" operationCode="planTemplateUpdateCode"  ></fd:colOptBtn>
                    <fd:colOptBtn tipTitle="{com.glaway.ids.common.start}" iconCls="basis ui-icon-enable" hideOption="hideEnable"  onClick="statusOperate" operationCode="planTemplateEnableCode"  ></fd:colOptBtn>
                    <fd:colOptBtn tipTitle="{com.glaway.ids.common.stop}" iconCls="basis ui-icon-forbidden" hideOption="hideDisable"  onClick="statusOperate" operationCode="planTemplateDisableCode"  ></fd:colOptBtn>
                    <fd:colOptBtn tipTitle="{com.glaway.ids.common.msg.delete}" iconCls="basis ui-icon-minus" hideOption="hideDel"  onClick="doDelete" operationCode="planTemplateDeleteCode"  ></fd:colOptBtn>
                    <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.approve}" iconCls="basis ui-icon-submitted_approval" hideOption="hideSubmit"  onClick="goPlanSubmitApprove" operationCode="planTemplateSubmitLineCode"></fd:colOptBtn>
                    <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.copy}" iconCls="basis ui-icon-copy" onClick="doCopy" operationCode="planTemplateCopyCode"></fd:colOptBtn>
                    <fd:colOptBtn tipTitle="{com.glaway.ids.common.msg.export}" iconCls="basis ui-icon-export" onClick="doExport" operationCode="planTemplateExportCode"></fd:colOptBtn>
                    <fd:colOptBtn tipTitle="回退" iconCls="basis ui-icon-return" hideOption="hideBack" onClick="doBack" operationCode="planTemplateBackCode"></fd:colOptBtn>
                    <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.revise}" iconCls="basis ui-icon-revise" hideOption="hideRevise" onClick="doRevise" operationCode="planTemplateReviseCode"></fd:colOptBtn>
                    <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.revoke}" iconCls="basis ui-icon-revoke" hideOption="hideRevoke"  onClick="doRevoke" operationCode="planTemplateRevokeCode"></fd:colOptBtn>                          

        	</fd:colOpt>
			<fd:dgCol title="{com.glaway.ids.pm.project.plantemplate.name}" field="name"  formatterFunName="WPSPlanList"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.plantemplate.bizVersion}" field="bizVersion"  align="left" sortable="true" formatterFunName="versionFormatter" />
			<fd:dgCol field="remark" title="{com.glaway.ids.common.lable.remark}"  sortable="false" formatterFunName="formatterRemark${entry}"/>
			<fd:dgCol field="createName" title="{com.glaway.ids.common.lable.creator}" sortable="true"/>
			<fd:dgCol field="createTimeStr" title="{com.glaway.ids.common.lable.createtime}" sortable="true"/>
			<fd:dgCol field="approveStatus" title="{com.glaway.ids.common.lable.status}" formatterFunName="approveStatusFormat" sortable="true"/>
		</fd:datagrid>
	</div>
	
</div>
	<fd:dialog id="goPlanSubmitApprove" width="420px" height="120px" modal="true" title="{com.glaway.ids.pm.project.plan.toApprove}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="startPlanTemProcess"></fd:dialogbutton>
 		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="WPSPlanList" width="940px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.planTemplateDeatail}">
	 	<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="add1" width="730px" height="320px" modal="true" title="{com.glaway.ids.common.msg.import}">
	 	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="importPlanTem"></fd:dialogbutton>
	 	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="addPlanTemplateDialog" width="950px" height="600px"
		modal="true" title="新增计划模板">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addPlanTemplateConfirm"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="updatePlanTemplateDialog" width="950px" height="600px"
		modal="true" title="修改计划模板">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addPlanTemplateConfirm"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="versionDetailList" width="900px" height="500px" modal="true" title="历史版本信息查看">
        <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
    </fd:dialog>
    <fd:dialog id="copyDialog" width="750px" height="300px"
        modal="true" title="{com.glaway.ids.pm.project.plantemplate.copy}">
        <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="copyConfirm"></fd:dialogbutton>
        <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
    </fd:dialog>
    <fd:dialog id="reviseDialog" width="950px" height="600px"
        modal="true" title="{com.glaway.ids.pm.project.plantemplate.revise}">
        <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addPlanTemplateConfirm"></fd:dialogbutton>
        <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
    </fd:dialog>

<script src="webpage/com/glaway/ids/project/plantemplate/planTemplateList.js"></script>
<script>
	/* 国际化转移的方法*/
	function planTemplateExport(url,gname){
		gridname=gname;
	    var id = '';
	    var rows = $("#"+gname).datagrid('getSelections');
	   if (rows.length == 1) {
						id=rows[0].id;
						location.href=url+'&id='+id;
		} else {
			tip('<spring:message code="com.glaway.ids.pm.project.plantemplate.selectOneExport"/>');
			return;
		}
	}
	
	function doExport(id, index) {
		var row = $("#plantemplates").datagrid("getRows")[index];
		window.location.href = 'planTemplateController.do?doExport&id='+row.id;
	}
	
	
function deleteALLSelectForTemp(title, url, gname) {
	var flag=true;
	gridname = gname;
	var ids = [];
	//var names = [];
	var rows = $("#" + gname).datagrid('getSelections');
	if (rows.length > 0) {		
		for (var i = 0; i < rows.length; i++) {
			if(rows[i].approveStatus!='nizhi')
			{
				flag=false;
				break;
				//names.push('"'+rows[i].name+'"');
			}
			ids.push(rows[i].id);
		}
		if(flag==false){
			tip('<spring:message code="com.glaway.ids.pm.project.plantemplate.plantemplate.deleteCheck"/>');
			return;
		}
		
		top.Alert.confirm('<spring:message code="com.glaway.ids.pm.project.plantemplate.confirmBatchDel"/>', function(r) {			 
			if (r) {
				$.ajax({
					url : url,
					type : 'post',
					data : {
						ids : ids.join(',')
					},
					cache : false,
					success : function(data) {	
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
							$("#" + gname).datagrid('unselectAll');
							ids = '';
						}
					}
				});
			}
		});
	} else {
		tip('请选择需要操作的模板');
	}
}
//启用禁用点击事件
function statusOperate(tmp,index){
	 
	$('#plantemplates').datagrid('selectRow',index);
	var rows = $('#plantemplates').datagrid('getSelections');
	if(rows.length>1){
		top.Alert.confirm('<spring:message code="com.glaway.ids.pm.project.plantemplate.checkOperationRows"/>');
		return;
	}
	var row=rows[rows.length-1];
	var status = row.approveStatus;
	var changeToStatus="启用";
	if(status=="qiyong"){
		changeToStatus="禁用";
	}
	top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmBatchOperate" arguments="' + changeToStatus + '"/>', function(r) {
		   if (r) {
			   changeToStatus = encodeURI(encodeURI(changeToStatus));
	$.ajax({
		//&planTemplateId='+id+'&status='+encodeURI(changeToStatus)
		url: 'planTemplateController.do?doStatusChange&status='+changeToStatus+'&planTemplateId='+row.id,
		type : "POST",
		dataType : "text",
		async : false,
		cache : false,
		success : function(data) {
			var d = $.parseJSON(data);
			if(d.success){
				tip(d.msg);
				reloadTable();
			}else{
				return;
			}
			
		}
	});
			}
	});
}
function doDelete(tmp,index){
	var row = $("#plantemplates").datagrid("getRows")[index];
	top.Alert.confirm('<spring:message code="com.glaway.ids.pm.project.plantemplate.confrimDel"/>', function(r) {
		if(r){
			$.ajax({
				url : 'planTemplateController.do?doBatchDel',
				type : 'post',
				data : {
					ids : row.id
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var msg = d.msg;
						tip(msg);
						reloadTable();
						$("#plantemplates").datagrid('unselectAll');
						ids='';
					}
				}
			});
		}
				
});
}
//批量启用禁用事件
function doBatchStatusChange(type){
	var uType;
	if(type=="启用"){
		uType="禁用";
	}else{
		uType="启用";
	}
	 
	var rows = $("#plantemplates").datagrid('getSelections');
	if(rows.length==0){
		top.tip('请选择需要操作的模板');
		return;
	}
	
	var ids = [];
	if(type=="禁用"){//要禁用
		for ( var i = 0; i < rows.length; i++) {
			if(rows[i].approveStatus != "qiyong"){
				top.tip('<spring:message code="com.glaway.ids.common.onlyStopStarted"/>');
				return;
			}
		}
	}
	if(type=="启用"){
		for ( var i = 0; i < rows.length; i++) {
			if(rows[i].approveStatus !="jinyong"){
				top.tip('<spring:message code="com.glaway.ids.common.onlyStartStopped"/>');
				return;
			}
		}
	}
	
	for ( var i = 0; i < rows.length; i++) {
		
		if(rows[i].approveStatus=="nizhi"||rows[i].approveStatus=="shenhe"){
			top.tip('spring:message code="com.glaway.ids.common.operateLimit" arguments="'+uType+','+type+'"');
			return;
		}
		ids.push(rows[i].id);
	}
	
	top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmBatchOperate" arguments="' + type + '"/>', function(r) {
		if(r){
			$.ajax({
				url : 'planTemplateController.do?doStatusChange',
				type : 'post',
				data : {
					planTemplateId : ids.join(","),
					status:encodeURI(encodeURI(type))
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var msg = d.msg;
						top.tip(msg);
						reloadTable();
						$("#plantemplates").datagrid('unselectAll');
						ids='';
					}
				}
			});
		}
	});
	
	
}

function hideEnable(row,index){
	if( row.approveStatus=="jinyong")
		return false;
	else
		return true;
}

function goPlanSubmitApprove(tmp,index) {
	var row = $("#plantemplates").datagrid("getRows")[index];
/* 	$('#plantemplates').datagrid('unselectAll');
	$('#plantemplates').datagrid('selectRow',index);
	var row = $('#plantemplates').datagrid('getSelected');
	var rowIndex=$('#plantemplates').datagrid('getRowIndex',row); */
/* 	if(row.approveStatus!="nizhi"&& rowIndex===index){
		tip('<spring:message code="com.glaway.ids.pm.project.plantemplate.notRepeatSubmit"/>');
		return;} */
	var dialogUrl='planTemplateController.do?goSubmitApproveForList&planTemplateId='
		+ row.id;
	createDialog('goPlanSubmitApprove',dialogUrl);
}

function startPlanTemProcess(iframe){
	iframe.startPlanTemProcess();
	return false;
}


function hideDisable(row,index){
	if( row.approveStatus=="qiyong")
		return false;
	else
		return true;
}

function hideDel(row,index){
	if(row.approveStatus=='nizhi') {
		return false;	
	} else {
		return true;	
	}
}

function hideUpdatePlanTemp(row,index){
	if(row.approveStatus=='nizhi' || row.approveStatus == 'xiuding') {
		return false;
	}	
	else {
		return true;
	}		
}

function hideSubmit(row,index){
	if(row.approveStatus=='nizhi' || row.approveStatus == 'xiuding')
		return false;
	else
		return true;
}

function hideRevise(row,index){
    if(row.approveStatus=='qiyong' || row.approveStatus=='jinyong') {
    	return false;
    }
    else {
    	return true;	
    }
}

function hideRevoke(row,index){
    if(row.approveStatus=='xiuding') {
        return false;
    }
    else {
        return true;    
    }
}

function hideBack(row,index){
    if(row.approveStatus=='xiuding' || row.approveStatus=='nizhi') {
    	var bizVersion = row.bizVersion;
        var bizVersionNumber = bizVersion.split(".");           
        if(bizVersionNumber[1]=="1"){
            return true;
        } else {
        	return false;
        }       
    }
    else {
        return true;    
    }
}

function searchPlanTemplate(){
	var queryParams=$("#plantemplates").fddatagrid('queryParams',"planTemplateTag");
	queryParams['PlanTemplate.bizCurrent'] = '';
	$('#planTemplateTag').find('*').each(function() {
		
		if($(this).attr('name') == 'PlanTemplate.bizCurrent'){
			queryParams[$(this).attr('name')] += ","+$(this).val();
		}else
		if($(this).attr('name') == 'PlanTemplate.bizCurrent_condition'){
			queryParams[$(this).attr('name')] = 'in';
		} 	
	});
	$('#plantemplates').datagrid({
		url : 'planTemplateController.do?conditionSearch',
		queryParams : queryParams,
		pageNumber : 1
	});
	$('#plantemplates').datagrid('unselectAll');
	$('#plantemplates').datagrid('clearSelections');
	$('#plantemplates').datagrid('clearChecked');
	
	/*
	var searchObjArr = $('#planTemplateSearchForm').serializeArray();
	var searchParam = new Object();
	if(searchObjArr!=null && searchObjArr.length>0) {
		searchParam['PlanTemplate.bizCurrent']='';
		for(var i=0; i<searchObjArr.length; i++) {
			if(searchObjArr[i].name == 'PlanTemplate.bizCurrent'){
				searchParam[searchObjArr[i].name] += ","+searchObjArr[i].value;
			}else
			if(searchObjArr[i].name == 'PlanTemplate.bizCurrent_condition'){
				searchParam[searchObjArr[i].name] = 'in';
			}else
				searchParam[searchObjArr[i].name] = searchObjArr[i].value;
			
		}
		var tmp = searchParam['PlanTemplate.bizCurrent'];
		if(tmp!=''){
		}
	}
	
	$('#plantemplates').datagrid({
		url : 'planTemplateController.do?conditionSearch',
		queryParams : searchParam
	});
	*/
}

function tagSearchResetForPlanTemplate(){
	$("#name").textbox("clear");
	$("#planTemplateCreateName").textbox("clear");
	$("#PlanTemplateBizCurrent").combobox("clear");
	//$("#createTime").combo("clear");
	$("#createTime_BeginDate").datebox("clear");
	$("#createTime_EndDate").datebox("clear");
	
}


function addPlanTemplate(){
	var dialogUrl = 'planTemplateController.do?goAddPlanTemplate&type=add';
	createDialog('addPlanTemplateDialog', dialogUrl);
}

function addPlanTemplateConfirm(iframe){
	iframe.addPlanTemplate();
	return false;
}

/**
 * 修改模板
 */
function doUpdate(id,index){
	var row = $("#plantemplates").datagrid("getRows")[index];
	var dialogUrl = 'planTemplateController.do?goAddPlanTemplate&type=update&planTemplateId='+row.id;
	createDialog('updatePlanTemplateDialog', dialogUrl);
}

/**
 * 复制模板
 */
function doCopy(id, index) {
	var row = $("#plantemplates").datagrid("getRows")[index];
    var dialogUrl = 'planTemplateController.do?goCopy&planTemplateId='+row.id;
    createDialog('copyDialog', dialogUrl);
}

function copyConfirm(iframe){
    iframe.doCopyTemplate();
    return false;
}

/**
 * 修订模板
 */
function doRevise(id, index) {
	var row = $("#plantemplates").datagrid("getRows")[index];
    var dialogUrl = 'planTemplateController.do?goAddPlanTemplate&type=revise&planTemplateId='+row.id;
    createDialog('reviseDialog', dialogUrl);
}

/**
 * 撤销模板
 */
function doRevoke(id, index) {
	top.Alert.confirm(
        '<spring:message code="com.glaway.ids.pm.plantemplate.confirmRevoke"/>',
        function(r) {
            if (r) {
            	var row = $("#plantemplates").datagrid("getRows")[index];
                var url = 'planTemplateController.do?doRevoke';
                $.ajax({
                    url : url,
                    type : 'post',
                    data : {
                        id : row.id,
                        bizId : row.bizId,
                        bizVersion : row.bizVersion
                    },
                    cache : false,
                    success : function(data) {
                        var d = $.parseJSON(data);
                        var msg = d.msg;
                        tip(msg);
                        if (d.success) {
                            window.setTimeout("reloadTable()", 500);//等待0.5秒，让分类树结构加载出来
                        }
                    }
                });
           }
    });
}

/**
 * 回退模板
 */
function doBack(id, index) {
	top.Alert.confirm(
        '<spring:message code="com.glaway.ids.pm.plantemplate.confirmBack"/>',
        function(r) {
            if (r) {
            	var row = $("#plantemplates").datagrid("getRows")[index];
                var url = 'planTemplateController.do?doBack';
                $.ajax({
                    url : url,
                    type : 'post',
                    data : {
                        id : row.id,
                        bizId : row.bizId,
                        bizVersion : row.bizVersion
                    },
                    cache : false,
                    success : function(data) {
                        var d = $.parseJSON(data);
                        var msg = d.msg;
                        tip(msg);
                        if (d.success) {
                            window.setTimeout("reloadTable()", 500);//等待0.5秒，让分类树结构加载出来
                        }
                    }
                });
           }
	});
}

function formatterRemarkPlanTemp(value, row, index) {
	if (value == undefined)
        value = '';
    var shtml;
    shtml = '<span title="'+value+'">' + value + '</span>';
    return shtml;
}


//查询计划模板状态
function approveStatusFormat(val, row, value){
	var status;
	if(row.approveStatus=="qiyong"){		
		status = "启用";
	}else if(row.approveStatus=="jinyong"){
		status = "禁用";
	}else if(row.approveStatus=="nizhi"){
		status ="拟制中";
	}else if(row.approveStatus=="shenhe"){
		status ="审批中";
	}else if(row.approveStatus=="xiuding"){
		status ="修订中";
	}
	//如果没有流程，则不需要点击
	var resultUrl = status;
	if(row.processInstanceId != null && row.processInstanceId != '' && row.processInstanceId != undefined){
		resultUrl = '<a href="javascript:void(0)" onclick="openFlowTasksForPlanTemplate(\''
			+ row.processInstanceId + '\')"><font color=blue>'+status+'</font></a>';
	}
	return resultUrl;
}

//弹出对象对应的流程
function openFlowTasksForPlanTemplate(procInstId){
	/*var tabTitle = '计划模板工作流';
	var url = 'taskFlowCommonController.do?taskFlowList&taskNumber='+taskNumber;
	createdetailwindow_close(tabTitle, url, 800, 400);*/
	var tabTitle = '计划模板工作流';
	createdetailwindow(tabTitle,
			'generateFlowDiagramController.do?initDrowFlowActivitiDiagram'
					+ '&procInstId=' + procInstId, 800, 600);
}

</script>
</body>
</html>
