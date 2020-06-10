<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<script type="text/javascript">
	//编写自定义JS代码
</script>
</head>
<body>
<input id="useObjectId" name="useObjectId" type="hidden" value="${tempPlanResourceLinkInfo_.useObjectId }">
<input id="useObjectType" name="useObjectType" type="hidden" value="${tempPlanResourceLinkInfo_.useObjectType}">
	<div class="easyui-panel"  id="plan" style="width:auto;height:380px;" fit="true">
		<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
		<fd:form id="resourceLinkeForm">	
			<input id="id" name="id" type="hidden" value="${tempPlanResourceLinkInfo_.id }">
			<input id="resourceId" name="resourceId" type="hidden" value="${tempPlanResourceLinkInfo_.resourceId }">
			<table style="width:auto;height:auto;">
				<tr>
					<td>
						<fd:inputText id="resourceName" title="{com.glaway.ids.pm.project.plan.resource.resourceName}" readonly="true"  name="resourceName" value="${tempPlanResourceLinkInfo_.resourceName}" /></td>
					<td>
						<fd:inputText id="useRate"  title="{com.glaway.ids.pm.project.plan.resource.useRate}"  name="useRate"  value="${tempPlanResourceLinkInfo_.useRate}" required="true"/></td>
				</tr>	
				<tr>
				<td>
				<fd:inputDate id="startTime" title="{com.glaway.ids.common.lable.starttime}" value='${tempPlanResourceLinkInfo_.startTime}'  editable="false" required="true"></fd:inputDate></td>	
						
						
				<td>
				<fd:inputDate id="endTime" title="{com.glaway.ids.common.lable.endtime}" value='${tempPlanResourceLinkInfo_.endTime}' editable="false"   required="true" ></fd:inputDate></td>	
				</tr>	
			</table>
			<input id="planStartTime" name="planStartTime" type="hidden"
				value="${planStartTime}">
			<input id="planEndTime" name="planEndTime" type="hidden"
				value="${planEndTime}">
	</fd:form>
		
	</div>
<script type="text/javascript">

function saveEditResource() {
	var  datas;
	var id = $('#id').val();
	var resourceId = $('#resourceId').val();
	var useRate = $('#useRate').val();
	var startTime = $('#startTime').datebox('getValue');
	var endTime = $('#endTime').datebox('getValue');
	var resourceType = $('#resourceType').val();
	var resourceName = $('#resourceName').val();
	var planStartTime = $('#planStartTime').val();
	var planEndTime = $('#planEndTime').val();
	var reg = new RegExp("^[0-9]*$");
	
	if(useRate == ''){
		tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyPercent"/>');
		return false;
	}
	
	if(useRate > 1000){
		tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.percentUpperLimit"/>');
		return false;
	}
	
	if(useRate < 0){
		tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.percentLowerLimit"/>');
		return false;
	}
	
	if(!reg.test(useRate)){
		tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.percentMustNumber"/>');
		return false;			}
	
	if(startTime == ''){
		tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyStart"/>');
		return false;
	}
	
	if(endTime == ''){
		tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyEnd"/>');
		return false;
	}
	
	
	
	if(endTime < startTime){
		tip('<spring:message code="com.glaway.ids.pm.project.plan.endNoEarlierThanStart"/>');
		return false;
	}
	
	
	datas= {
		useObjectId : $("#useObjectId").val(),
		useObjectType : $("#useObjectType").val(),
		id : id,
		resourceId : resourceId,
		useRate : useRate,
		resourceType : resourceType,
		resourceName : resourceName,
		startTime : startTime,
		endTime : endTime
	};
	
	$.ajax({
		url : 'planChangeMassController.do?doUpdateResource',
		async : false,
		cache : false,
		type : 'post',
		data : datas,
		cache : false,
		success : function(data) {
			
		}
	});
	return datas;
}


function initResource() {
	var datas="";
	var flg=false;
		datas={useObjectId : $('#useObjectId').val(),useObjectType : $('#useObjectType').val()}
	$.ajax({
		type : 'POST',
		url : 'resourceLinkInfoController.do?list',
		async : false,
		data : datas,
		success : function(data) {
			$("#resourceList").datagrid("loadData",data);
		} 
	});
}

function closePlan() {
	$.fn.lhgdialog("closeSelect");
}

</script>
</body>
