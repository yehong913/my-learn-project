<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>按部门设置</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
<script type="text/javascript">

/**
 *角色的操作
 */
function selectRole() {
	var selects = "";
	var selectMode = 'multi';
	selects = $("#roleId").val();
	
	var url = encodeURI('generalSelectRoleController.do?goGetGeneralSelectRole&selectMode='
			+ selectMode + '&selects=' + selects);
	url = encodeURI(url);
	var dialogUrl = url;
	createDialog('openRole', dialogUrl);
		
}

function saveRoleP(iframe) {
	debugger;
	var ids = '';
	var roleCodes = '';
	var roleNames = '';
	var selectRoles = iframe.getSelectRoles();
	var item = selectRoles.split(',');
	for (var i = 0; i < item.length; i++) {
		var roleItem = item[i];
		var singleRole = roleItem.split(':');
		if (i == item.length - 1) {
			ids =ids+ singleRole[0];
			roleCodes = roleCodes+singleRole[1].split('-')[1];
			roleNames = roleNames+singleRole[1].split('-')[0];
		} else {
			ids = ids+singleRole[0]+',';
			roleCodes = roleCodes+singleRole[1].split('-')[1]+',';
			roleNames = roleNames+singleRole[1].split('-')[0]+',';
		}
	}

	if (ids != null && ids !=undefined && ids != '') {
		$("#roleId").val(ids);
		$("#searchRolesId").searchbox("setValue",roleNames);
		$("#roleName").val(roleNames);
		return true;
	}
	return false;
}
function saveProcessTask() {
	debugger;
	 var repFileTypeId = '${repFileTypeId}'; 
	var taskName = $("#processName").textbox("getValue");
	var roleName = $("#roleName").val();
	var taskType = $("#processType").combobox("getValue");
	var numbers = $("#processNumbers").textbox("getValue");
	var remark = $("#remark").val();
	
	var signPercent=getAssignPercent();
	if(taskName == null || taskName == undefined || taskName == '' ){
		top.tip('<spring:message code="com.glaway.ids.pm.config.bpmn.taskNameNotEmpty"/>');
		return false;
	}
	if(taskName.length > 30){
		top.tip('<spring:message code="com.glaway.ids.pm.config.bpmn.taskNameNotMoreThanThirty"/>');
		return false;
	}
	/*if(roleName == null || roleName == undefined || roleName == '' ){
		top.tip('<spring:message code="com.glaway.ids.pm.config.bpmn.taskRoleNameNotEmpty"/>');
		return false;
	}*/
	if(taskType == 'counterSign'){
		var reg = new RegExp("(^[1-9][0-9]{0,1}$|100)");
		if(!reg.test(signPercent)){
			top.tip('<spring:message code="com.glaway.ids.pm.config.bpmn.pleaseSelectAssignNumber" />')
			return false;
		}
	}
	 url="repFileTypeConfigController.do?doAddTask";
	$.ajax({
		type : 'POST',
		url : url,
		data : {
			repFileTypeId :repFileTypeId,
			taskName : taskName,
			roleName : roleName,
			taskType : taskType,
			numbers : numbers,
			remark : remark,
			formId : '${formId}',
			approvePercent : signPercent
		},
		success : function(data){
			debugger;
			var d = $.parseJSON(data);
			if(d.success){
				top.tip(d.msg);
				var win = $.fn.lhgdialog("getSelectParentWin");
				win.reloadTaskDataGrid();
				$.fn.lhgdialog("closeSelect");
			}else{
				top.tip(d.msg);
			}
		}
	}); 
}

function onChangeSignMode(){
	debugger;
	var va = $("#processType").combobox('getValue');
	debugger;
		if (va == 'singleSign') {
			 $("#progessDivId").hide();
			 $('#processNumbers').textbox('setValue',"单人");
			 $('#processNumbers').textbox('readonly',true);
			
		} else if(va == 'vieSign'){
			$("#progessDivId").hide();
			$('#processNumbers').textbox('setValue',"多人");
		    $('#processNumbers').textbox('readonly',true);
		} else if(va == 'counterSign'){
			$("#progessDivId").show();
			$('#processNumbers').textbox('setValue',"多人");
			$('#processNumbers').textbox('readonly',true);
		}
}

function onChangePercent(){
	debugger;
	var va = $("#progressId").combobox('getValue');
	debugger;
		if (va == 'signCustomer') {
			$("#percentDivId").show();
			
		}else{
			$("#percentDivId").hide();
		}
}
function getAssignPercent(){
	debugger;
	var percent = $("#progressId").combobox("getValue");
	if(percent == 'signAll'){
		return "100";
	}else if(percent == 'signThreeQuarter'){
		return "75";
	}else{
		return $('#signPercentId').numberbox('getValue');
	}
}

</script>
	<div>
		<fd:inputText id="processName" name="processName" title="{com.glaway.ids.pm.config.bpmn.taskName}" required="true" />
		
		<input id="roleName" name="roleName" type="hidden" />
		<input id="roleId" name="roleId" type="hidden" />
		<%--<fd:inputSearch id="searchRolesId"  searcher="selectRole" title="{com.glaway.ids.pm.config.bpmn.roleName}" editable="false"  required="true"></fd:inputSearch>--%>
						
		<fd:combobox id="processType" name="processType" title="{com.glaway.ids.pm.config.bpmn.processType}" valueField="id"
				textField="type" panelMaxHeight="200" required="true" editable="false"
				selectedValue="${project_.phase}"
				data="singleSign_单人审批_checked,vieSign_竞争审批,counterSign_会签模式"
				readonly="false"  onChange="onChangeSignMode()"/>	
		<fd:inputText id="processNumbers" name="processNumbers" title="{com.glaway.ids.pm.config.bpmn.enableNumbers}" value="单人" required="true" readonly="true"/>
		<div id="progessDivId" style="display:hide,">
			<fd:combobox id="progressId" name="progressId" title="{com.glaway.ids.pm.config.bpmn.assignType}" required="true" onChange="onChangePercent()"
							editable="false"  textField="signPercent"
							selectedValue="${signPercent}" valueField="id"
							data="signAll_全部通过方结束会签_checked,signThreeQuarter_75%通过即结束会签,signCustomer_自定义比例" multiple="false"
							panelHeight="120" />
			<div id="percentDivId" style="float:left;width:20px;margin-top:9px;margin-left:100px">
				<fd:inputNumber id="signPercentId" name="signPercent" min="1" max="100" value="${signPercent}" 
				prompt="请输入1~100之间的数字"   suffix="%通过后即结束会签"></fd:inputNumber>
			</div>		
		</div>				
		<fd:inputTextArea name="remark" id="remark" title="{com.glaway.ids.pm.config.bpmn.remark}"
				value="" readonly="false" />		
	</div>
	<fd:dialog id="openRole" width="1040px" height="550px" modal="true" title="{com.glaway.ids.pm.project.projectmanager.role.selectRoles}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
			callback="saveRoleP"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
			callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
</body>
</html>
