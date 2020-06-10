<%@page import="java.util.*"%>
<%@page import="com.glaway.foundation.system.lifecycle.entity.*"%>
<%@page import="com.glaway.ids.config.auth.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<style type="text/css">
.codegenSetTable {
	border-left: 1px dotted #ccc;
	border-top: 1px dotted #ccc;
}

.codegenSetTable tr th {
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#F9F9F9,
		endColorstr=#efefef, GradientType=0);
	BACKGROUND-COLOR: #efefef;
	height: 24px;
	padding: 0;
	border-right: 1px dotted #ddd;
	border-bottom: 1px dotted #ddd;
	text-align: left;
}

.codegenSetTable tr th span {
	margin: 0 4px
}

.codegenSetTable tr td {
	border-right: 1px dotted #ccc;
	border-bottom: 1px dotted #ccc;
	padding: 2px 4px;
	height: 18px;
}

.options-stauts{
	pointer-events: none;
	color:gray;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		
		var trArray = $("#lifeCycleStatus_table_body tr");
		for (var i = 0; i < trArray.length; i++) {
			var tr = trArray[i]
			var statusName = $(tr).find(
					"input[name*='lifeCycleStatusList[" + i + "].name']").val();
			var ckArray = $(tr).find("input[name*='lifeCycleStatus_ck_']");
			var actions = new Array();
			for (var j = 0; j < ckArray.length; j++) {
				var ck = ckArray[j];
				var flg = ck.name.split('+')[0]; 
				var status = ck.name.split('+')[1];
				var auth = ck.name.split('+')[2];
				debugger;
				var authElement = document.getElementById(flg + "+" + status+ "+" + auth);
				var authLabelElement = document.getElementById(flg + "+" + status+ "+" + auth + "+label");
				if(status == 'nizhi'){
					if(auth != 'remove'
						&& auth != 'update'
						&& auth != 'download'
						&& auth != 'upload'
						&& auth != 'rollback'
						&& auth != 'history'){
						authElement.disabled = true;
						authLabelElement.className='options-stauts';
					}
					else{
						authElement.disabled = false;
					}
				}
				else if(status == 'shenpi'){
					if(auth != 'download'
						&& auth != 'history'){
						authElement.disabled = true;
						authLabelElement.className='options-stauts';
					}
					else{
						authElement.disabled = false;
					}
				}
				else{
					if(auth != 'upload'
						&& auth != 'download'
						&& auth != 'history'
						&& auth != 'revise'){
						authElement.disabled = true;
						authLabelElement.className='options-stauts';
					}
					else{
						authElement.disabled = false;
					}
				}
			}
		}
	});

	function saveLifeCycleStatusAuthConfig() {
		var permissions = $.toJSON(rep_getLifeCyclePermission());
		//改参数用来判断是否是从KDD中进入的业务配置
		var kddProductType='${param.kddProductType}';
		$.ajax({
			url : 'repFileLifeCycleAuthConfigController.do?saveConfig',
			type : 'post',
			data : {
				'permissions' : permissions,
				'objectClass' : 'com.glaway.foundation.rep.entity.RepFile',
				'kddProductType' : kddProductType
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				tip(d.msg);
			}
		});

	}

	function rep_getLifeCyclePermission() {
		var permissions = new Array();
		var trArray = $("#lifeCycleStatus_table_body tr");
		for (var i = 0; i < trArray.length; i++) {
			var tr = trArray[i]
			var statusName = $(tr).find(
					"input[name*='lifeCycleStatusList[" + i + "].name']").val();
			var ckArray = $(tr).find("input[name*='lifeCycleStatus_ck_']");
			var actions = new Array();
			for (var j = 0; j < ckArray.length; j++) {
				var ck = ckArray[j];
				var action = new Object();
				//action['actionCode'] = $(ck).next(":hidden").val();
				action['actionCode'] = ck.name.split('+')[2];
				action['checked'] = ck.checked;
				actions.push(action);
			}
			var permission = new Object();
			permission['statusName'] = statusName;
			permission['actions'] = actions;
			permissions.push(permission);
		}
		return permissions;
	}
	
	var actionCodeArr = [];
	
	function check(id){
		var val = id.split(",");
		if(val[1] == 'list'){
			var listBoxe = document.getElementById("lifeCycleStatus_ck_+"+val[0]+ "+list");
			if(!listBoxe.checked){
				if(actionCodeArr == null || actionCodeArr == '' || actionCodeArr == undefined){
					var trArray = $("#lifeCycleStatus_table_body tr");
					for (var i = 0; i < trArray.length; i++) {
						var tr = trArray[i]
						var statusName = $(tr).find(
								"input[name*='lifeCycleStatusList[" + i + "].name']").val();
						var ckArray = $(tr).find("input[name*='lifeCycleStatus_ck_']");
						for (var j = 0; j < ckArray.length; j++) {
							var ck = ckArray[j];
							//var actionCode = $(ck).next(":hidden").val();
							var actionCode = ck.name.split('+')[2];
							actionCodeArr.push(actionCode);
						}
						break;
					}
				}
				for(var j=0;j<actionCodeArr.length;j++){
					if(actionCodeArr[j] != val[1]){
						var listBoxe = document.getElementById("lifeCycleStatus_ck_+"+val[0]+ "+"+actionCodeArr[j]);
						listBoxe.checked = false;
					}
				}
			}
		}
		else if(val[1] == 'detail'){
			var detailBoxe = document.getElementById("lifeCycleStatus_ck_+"+val[0]+ "+detail");
			if(!detailBoxe.checked){
				if(actionCodeArr == null || actionCodeArr == '' || actionCodeArr == undefined){
					var trArray = $("#lifeCycleStatus_table_body tr");
					for (var i = 0; i < trArray.length; i++) {
						var tr = trArray[i]
						var statusName = $(tr).find(
								"input[name*='lifeCycleStatusList[" + i + "].name']").val();
						var ckArray = $(tr).find("input[name*='lifeCycleStatus_ck_']");
						for (var j = 0; j < ckArray.length; j++) {
							var ck = ckArray[j];
							//var actionCode = $(ck).next(":hidden").val();
							var actionCode = ck.name.split('+')[2];
							actionCodeArr.push(actionCode);
						}
						break;
					}
				}
				for(var j=0;j<actionCodeArr.length;j++){
					if(actionCodeArr[j] != val[1] && actionCodeArr[j] != 'list'){
						var listBoxe = document.getElementById("lifeCycleStatus_ck_+"+val[0]+ "+"+actionCodeArr[j]);
						listBoxe.checked = false;
					}
				}
			}
			else{
				var listBoxe = document.getElementById("lifeCycleStatus_ck_+"+val[0]+ "+list");
				listBoxe.checked = true;
			}
		}else{
			var listBoxe = document.getElementById("lifeCycleStatus_ck_+"+val[0]+ "+list");
			listBoxe.checked = true;
			var detailBoxe = document.getElementById("lifeCycleStatus_ck_+"+val[0]+ "+detail");
			detailBoxe.checked = true;
		}
	}
</script>
<style>
.borstl {
	border: 0;
	border: none;
	border: 1px solid #54A5D5;
}
</style>
<%--<div style="float: right; margin-right: 10px;">
	<fd:helpButton help="helpDoc:DocumentLifeCyclePermissions"></fd:helpButton>
</div>--%>
<div style="padding: 3px; height: 25px; width: auto;"
	class="datagrid-toolbar">
	<fd:linkbutton id="saveLifeCycleStatusAuthConfigBtn" onclick="saveLifeCycleStatusAuthConfig()" 
		value="保存" iconCls="basis basis ui-icon-save" operationCode="lifeCycleStatusAuthSaveCode" />
	<div style="float:right;margin-right: 5px;margin-top: 5px">
		<fd:helpButton help="helpDoc:DocumentLifeCyclePermissions"></fd:helpButton>
	</div>
</div>
<div
	style="width: auto;overflow-y: auto;">
	<table width="100%" id="lifeCycleStatus_table" class="codegenSetTable">
		<tr>
			<th width="100px">状态名</th>
			<th width="100px">状态标题</th>
			<th width="500px">操作权限</th>
		</tr>
		<tbody id="lifeCycleStatus_table_body">
			<%
			    Map<String, HashMap<ProjectLibraryAuthorityEnum, Boolean>> permissionMap = (Map<String, HashMap<ProjectLibraryAuthorityEnum, Boolean>>)request.getAttribute("permissionMap");
			    List<LifeCycleStatus> lifeCycleStatusList = (List<LifeCycleStatus>)request.getAttribute("lifeCycleStatusList");
			    for (int i = 0; i < lifeCycleStatusList.size(); i++ ) {
			        LifeCycleStatus status = lifeCycleStatusList.get(i);
			%>
			<tr>
				<input name="lifeCycleStatusList[<%=i%>].id" type="hidden"
					value="<%=status.getId()%>" />
				<td align="left"><input name="lifeCycleStatusList[<%=i%>].name"
					maxlength="255" type="hidden" style="width: 100px;"
					value="<%=status.getName()%>"> <label
					class="Validform_label"><%=status.getName()%></label></td>
				<td align="left"><input
					name="lifeCycleStatusList[<%=i%>].title" maxlength="255"
					type="hidden" style="width: 100px;" value="<%=status.getTitle()%>">
					<label class="Validform_label"><%=status.getTitle()%></label></td>
				<td align="left">
					<ul>
						<%
						    if (permissionMap != null) {
						            HashMap<ProjectLibraryAuthorityEnum, Boolean> perm = permissionMap.get(status.getName());
						            ProjectLibraryAuthorityEnum[] actions = ProjectLibraryAuthorityEnum.values();
						            for (ProjectLibraryAuthorityEnum action : actions) {
						                String checkBoxId = "lifeCycleStatus_ck_" + "+" + status.getName() + "+" +action.getActionCode();
						                String labelId = checkBoxId + "+label";
						                String statusName = status.getName();
						                String checked = "checked=checked";
						                if (!perm.get(action)) {
						                    checked = "";
						                }
						%>
						<li
							style="float: left; width: 100px; padding: 0px; list-style: none; margin-bottom: 5px; display: inline-block;">
							<input id="<%=checkBoxId%>" style="margin: 0px;" disabled="disabled" type="checkbox"  onclick="check('<%=statusName%>'+','+'<%=action.getActionCode()%>')" 
							name="<%=checkBoxId%>" <%=checked%> /> <input
							style="margin: 0px;" type="hidden"
							value="<%=action.getActionCode()%>" /> <label id="<%=labelId%>"
							style="width: 70px; margin: 0px;" for="<%=checkBoxId%>"
							title="<%=action.getName()%>"><%=action.getName()%></label>
						</li>
						<%
						    }
						        }
						%>
					</ul>
				</td>
			</tr>

			<%
			    }
			%>
		</tbody>
	</table>
</div>