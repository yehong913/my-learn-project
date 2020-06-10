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
<input id="cellId" name="cellId" type="hidden" value="${cellId}">
<input id="parentPlanId" name="parentPlanId" type="hidden" value="${parentPlanId}">
<input id="useObjectId" name="useObjectId" type="hidden" value="${tempPlanResourceLinkInfo_.useObjectId }">
<input id="useObjectType" name="useObjectType" type="hidden" value="${tempPlanResourceLinkInfo_.useObjectType}">
<input id="id" name="id" type="hidden" value="${tempPlanResourceLinkInfo_.id }">
<input id="resourceId" name="resourceId" type="hidden" value="${tempPlanResourceLinkInfo_.resourceId }">
<fd:inputText id="resourceName"  title="{com.glaway.ids.pm.project.plan.resource.resourceName}" readonly="true" name="resourceName" value="${tempPlanResourceLinkInfo_.resourceName}"/>
<fd:inputText id="useRate"  title="{com.glaway.ids.pm.project.plan.resource.useRate}" name="useRate"  
	value="${tempPlanResourceLinkInfo_.useRate}" required="true"/>
<fd:inputDate id="startTime" title="{com.glaway.ids.common.lable.starttime}" value='${tempPlanResourceLinkInfo_.startTime}'  
	editable="false" required="true"></fd:inputDate>
<fd:inputDate id="endTime" title="{com.glaway.ids.common.lable.endtime}" value='${tempPlanResourceLinkInfo_.endTime}' 
	editable="false"   required="true" ></fd:inputDate>

<div style="display: none">
	<fd:inputDate id="planStartTime"  value='${planStartTime}'></fd:inputDate>
	<fd:inputDate id="planEndTime" value='${planEndTime}'></fd:inputDate>
</div>

<script type="text/javascript">
	function submitEditResource() {
		var  datas;
		var cellId = $('#cellId').val();
		var id = $('#id').val();
		var resourceId = $('#resourceId').val();
		var useRate = $('#useRate').val();
		var startTime = $('#startTime').datebox('getValue');
		var endTime = $('#endTime').datebox('getValue');
		var resourceType = $('#resourceType').val();
		var resourceName = $('#resourceName').val();
		var reg = new RegExp("^[0-9]*$");
		var planStartTime = $('#planStartTime').datebox('getValue');
		var planEndTime = $('#planEndTime').datebox('getValue');
		var useObjectId = $('#useObjectId').val();
		var parentPlanId = $('#parentPlanId').val();		
		if(useRate == ''){
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.resource.emptyPercent"/>');
			return false;
		}		
		if(useRate > 1000){
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.resource.percentUpperLimit"/>');
			return false;
		}		
		if(useRate < 0){
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.resource.percentLowerLimit"/>');
			return false;
		}		
		if(!reg.test(useRate)){
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.resource.percentMustNumber"/>');
			return false;			}
		
		if(startTime == ''){
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.resource.emptyStart"/>');
			return false;
		}		
		if(endTime == ''){
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.resource.emptyEnd"/>');
			return false;
		}
		if(endTime < startTime){
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.plan.endNoEarlierThanStart"/>');
			return false;
		}
		if((startTime < planStartTime)||(endTime > planEndTime)){
			//"资源【"+resourceName+"】的资源使用区间需收敛在任务时间范围【"+planStartTime+"-"+planEndTime+"】内"
			top.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.pdResourceTwo" arguments="' + resourceName + ',' + planStartTime + ','+ planEndTime + '"/>');
			return false;
		}		
		datas= {
			id : id,
			cellId : cellId,
			resourceId : resourceId,
			useRate : useRate,
			useObjectId : useObjectId,
			resourceType : resourceType,
			resourceName : resourceName,
			startTime : startTime,
			endTime : endTime,
			parentPlanId : parentPlanId
		};		
		$.ajax({
			url : 'taskFlowResolveController.do?doUpdateResourceForChange',
			async : false,
			cache : false,
			type : 'post',
			data : datas,
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var win = $.fn.lhgdialog("getSelectParentWin") ;
					win.initResourceChangeFlow();
					$.fn.lhgdialog("closeSelect");
				}
				else{
					top.tip(d.msg);
				}				
			}
		});
	}
</script>
</body>
