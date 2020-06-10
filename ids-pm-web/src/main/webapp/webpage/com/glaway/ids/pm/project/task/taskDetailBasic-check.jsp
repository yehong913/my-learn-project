<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>


<div>
	<fd:panel id="panel1" border="false" collapsible="false" width="99%" >
		<div>
		<c:if test="${!kddValid}">
			<!-- 顶部存放 begin -->
			<fd:form id="formobj" url="planController.do?doUpdate">
				<input id="id" name="id" type="hidden" value="${plan_.id}">
	
				<fd:inputText title="{com.glaway.ids.pm.project.plan.planName}" id="planName" name="planName"
					value="${plan_.planName}" readonly="true"></fd:inputText>
	
				<fd:inputText title="{com.glaway.ids.common.lable.project}" id="projectName" name="projectName"
					value="${plan_.project.name}-${plan_.project.projectNumber}"
					readonly="true"></fd:inputText>
	
				<fd:inputText title="{com.glaway.ids.pm.project.projectmanager.projectManager}" id="projectManagerNames" 
					name="projectManagerNames" value="${plan_.project.projectManagerNames}" readonly="true"></fd:inputText>
	
				<fd:inputDate title="{com.glaway.ids.pm.project.projectmanager.projectStartTime}" id="startProjectTime" name="startProjectTime"
					value="${plan_.project.startProjectTime}" readonly="true"></fd:inputDate>
	
				<fd:inputDate title="{com.glaway.ids.pm.project.projectmanager.projectEndTime}" id="endProjectTime" name="endProjectTime"
					value="${plan_.project.endProjectTime}" readonly="true"></fd:inputDate>
						
				
				<fd:inputText title="{com.glaway.ids.pm.project.projectmanager.type}" id="epsName" name="epsName"
					value="${plan_.project.epsName}"
					readonly="true"></fd:inputText>	
					
				<fd:inputNumber title="{com.glaway.ids.pm.project.projectmanager.projectProgress}" id="process" name="process"
					value="${plan_.project.process}" precision="2" suffix="%" validType="equalOneHundred[6]" 
					readonly="true" />	
	
				<fd:inputText title="{com.glaway.ids.common.lable.owner}" id="ownerName" name="ownerName"
					value="${plan_.ownerInfo.realName}-${plan_.ownerInfo.userName}"
					readonly="true"></fd:inputText>
	
				<fd:inputText title="{com.glaway.ids.pm.project.plan.dept}" id="ownerDept" name="ownerDept"
					value="${plan_.ownerDept}" readonly="true"></fd:inputText>
	
				<fd:inputText title="{com.glaway.ids.common.lable.status}" id="status" name="status" value="${plan_.status}" 
					readonly="true"></fd:inputText>
	
				<fd:inputText title="{com.glaway.ids.pm.project.plan.planprogress}" id="progressRate" name="progressRate"
					value="${plan_.progressRate}%" readonly="true"></fd:inputText>
	
				<fd:inputDate title="{com.glaway.ids.common.lable.planstarttime}" id="planStartTime" name="planStartTime"
					value="${plan_.planStartTime}" readonly="true"></fd:inputDate>
	
				<fd:inputDate title="{com.glaway.ids.common.lable.planendtime}" id="planEndTime" name="planEndTime"
					value="${plan_.planEndTime}" readonly="true"></fd:inputDate>
	
				<fd:inputText title="{com.glaway.ids.pm.project.task.workTime}" id="workTime" name="workTime"
					value="${plan_.workTime}" readonly="true"></fd:inputText>
	
				<fd:combobox id="taskNameType" textField="typename" title="{com.glaway.ids.pm.project.plan.taskNameType}"
					selectedValue="${plan_.taskNameType}" readonly="true"
					editable="false" valueField="typecode" required="true"
					url="taskFlowResolveController.do?getTaskNameTypes"></fd:combobox>
	
				<fd:combobox id="taskType" textField="name" title="{com.glaway.ids.pm.project.plan.taskType}"
					selectedValue="${plan_.taskType}" readonly="true" 
					editable="false" valueField="name" required="true"
					url="planController.do?getTaskTypes&parentPlanId=${plan_.parentPlanId}&projectId=${plan_.projectId}">
				</fd:combobox>
	
				<fd:inputText title="{com.glaway.ids.pm.project.plan.planLevel}" id="planLevelName" name="planLevelName"
						value="${plan_.planLevelInfo.name}" readonly="true"></fd:inputText>
	
				<fd:inputText title="{com.glaway.ids.pm.project.plan.preposePlans}" id="preposePlans" name="preposePlans"
					value="${plan_.preposePlans}" readonly="true"></fd:inputText>
	
				<fd:inputText title="{com.glaway.ids.pm.project.task.taskdetail.parentPlan}" id="parentPlanName"
					name="parentPlanName" value="${plan_.parentPlanName}"
					readonly="true"></fd:inputText>
	
				<fd:inputText title="{com.glaway.ids.pm.project.projecttemplate.creator}" id="createFullName" name="createFullName"
						value="${plan_.createFullName}-${plan_.createName}" readonly="true"></fd:inputText>
						
				<fd:inputDate title="{com.glaway.ids.pm.project.projecttemplate.createTime}" id="createTime" name="createTime"
					value="${plan_.createTime}" readonly="true"></fd:inputDate>
	
				<c:if
					test="${plan_.assignerInfo.realName != '' && plan_.assignerInfo.realName != null}">
					<fd:inputText title="发布人" id="assignerName" name="assignerName"
						value="${plan_.assignerInfo.realName}-${plan_.assignerInfo.userName}"
						readonly="true"></fd:inputText>
				</c:if>
				<c:if
					test="${plan_.assignerInfo.realName == '' || plan_.assignerInfo.realName == null}">
					<fd:inputText title="发布人" id="assignerName" name="assignerName"
						value="${plan_.assignerInfo.realName}" readonly="true"></fd:inputText>
				</c:if>
	
				<fd:inputDate title="发布时间" id="assignTime" name="assignTime"
					value="${plan_.assignTime}" readonly="true"></fd:inputDate>
	
				<fd:inputText title="{com.glaway.ids.pm.project.plan.milestone}" id="milestone" name="milestone"
					value="${plan_.milestoneName}" readonly="true"></fd:inputText>
	
				<fd:inputTextArea id="remark" title="{com.glaway.ids.common.lable.remark}" name="remark"
					value="${plan_.remark}" readonly="true"></fd:inputTextArea>
			</fd:form>
		</c:if>
		
		<c:if test="${kddValid}">
			<fd:form id="formobj" url="planController.do?doUpdate">
				<input id="id" name="id" type="hidden" value="${plan_.id}">
	
				<fd:inputText title="{com.glaway.ids.pm.project.plan.planName}" id="planName" name="planName"
					value="${plan_.planName}" readonly="true"></fd:inputText>
	
				<fd:inputText title="{com.glaway.ids.pm.kdd.plan.detail.projectName}" id="projectName" name="projectName"
					value="${plan_.project.name}-${plan_.project.projectNumber}"
					readonly="true"></fd:inputText>
	
				<fd:inputText title="{com.glaway.ids.pm.kdd.plan.detail.projectManager}" id="projectManagerNames" 
					name="projectManagerNames" value="${plan_.project.projectManagerNames}" readonly="true"></fd:inputText>
	
				<fd:inputDate title="{com.glaway.ids.pm.kdd.plan.detail.projectStartTime}" id="startProjectTime" name="startProjectTime"
					value="${plan_.project.startProjectTime}" readonly="true"></fd:inputDate>
	
				<fd:inputDate title="{com.glaway.ids.pm.kdd.plan.detail.projectEndTime}" id="endProjectTime" name="endProjectTime"
					value="${plan_.project.endProjectTime}" readonly="true"></fd:inputDate>
						
				<fd:inputText title="{com.glaway.ids.common.lable.owner}" id="ownerName" name="ownerName"
					value="${plan_.ownerInfo.realName}-${plan_.ownerInfo.userName}"
					readonly="true"></fd:inputText>
	
				<fd:inputText title="{com.glaway.ids.pm.project.plan.dept}" id="ownerDept" name="ownerDept"
					value="${plan_.ownerDept}" readonly="true"></fd:inputText>
	
				<fd:inputText title="{com.glaway.ids.common.lable.status}" id="status" name="status" value="${plan_.status}" 
					readonly="true"></fd:inputText>
	
				<fd:inputText title="{com.glaway.ids.pm.project.plan.planprogress}" id="progressRate" name="progressRate"
					value="${plan_.progressRate}%" readonly="true"></fd:inputText>
	
				<fd:inputDate title="{com.glaway.ids.common.lable.planstarttime}" id="planStartTime" name="planStartTime"
					value="${plan_.planStartTime}" readonly="true"></fd:inputDate>
	
				<fd:inputDate title="{com.glaway.ids.common.lable.planendtime}" id="planEndTime" name="planEndTime"
					value="${plan_.planEndTime}" readonly="true"></fd:inputDate>
	
				<fd:inputText title="{com.glaway.ids.pm.project.task.workTime}" id="workTime" name="workTime"
					value="${plan_.workTime}" readonly="true"></fd:inputText>
	
				<fd:combobox id="taskNameType" textField="typename" title="{com.glaway.ids.pm.project.plan.taskNameType}"
					selectedValue="${plan_.taskNameType}" readonly="true"
					editable="false" valueField="typecode" required="true"
					url="taskFlowResolveController.do?getTaskNameTypes"></fd:combobox>
	
				<fd:combobox id="taskType" textField="name" title="{com.glaway.ids.pm.project.plan.taskType}"
					selectedValue="${plan_.taskType}" readonly="true" 
					editable="false" valueField="name" required="true"
					url="planController.do?getTaskTypes&parentPlanId=${plan_.parentPlanId}&projectId=${plan_.projectId}">
				</fd:combobox>
	
				<fd:inputText title="{com.glaway.ids.pm.project.plan.planLevel}" id="planLevelName" name="planLevelName"
						value="${plan_.planLevelInfo.name}" readonly="true"></fd:inputText>
	
				<fd:inputText title="{com.glaway.ids.pm.project.plan.preposePlans}" id="preposePlans" name="preposePlans"
					value="${plan_.preposePlans}" readonly="true"></fd:inputText>
	
				<fd:inputText title="{com.glaway.ids.pm.project.task.taskdetail.parentPlan}" id="parentPlanName"
					name="parentPlanName" value="${plan_.parentPlanName}"
					readonly="true"></fd:inputText>
	
				<fd:inputText title="{com.glaway.ids.pm.project.projecttemplate.creator}" id="createFullName" name="createFullName"
						value="${plan_.createFullName}-${plan_.createName}" readonly="true"></fd:inputText>
						
				<fd:inputDate title="{com.glaway.ids.pm.project.projecttemplate.createTime}" id="createTime" name="createTime"
					value="${plan_.createTime}" readonly="true"></fd:inputDate>
	
				<c:if
					test="${plan_.assignerInfo.realName != '' && plan_.assignerInfo.realName != null}">
					<fd:inputText title="{com.glaway.ids.common.lable.assigner}" id="assignerName" name="assignerName"
						value="${plan_.assignerInfo.realName}-${plan_.assignerInfo.userName}"
						readonly="true"></fd:inputText>
				</c:if>
				<c:if
					test="${plan_.assignerInfo.realName == '' || plan_.assignerInfo.realName == null}">
					<fd:inputText title="{com.glaway.ids.common.lable.assigner}" id="assignerName" name="assignerName"
						value="${plan_.assignerInfo.realName}" readonly="true"></fd:inputText>
				</c:if>
	
				<fd:inputDate title="{com.glaway.ids.common.lable.assignTime}" id="assignTime" name="assignTime"
					value="${plan_.assignTime}" readonly="true"></fd:inputDate>
	
				<fd:inputText title="{com.glaway.ids.pm.project.plan.milestone}" id="milestone" name="milestone"
					value="${plan_.milestoneName}" readonly="true"></fd:inputText>
	
				<fd:inputTextArea id="remark" title="{com.glaway.ids.common.lable.remark}" name="remark"
					value="${plan_.remark}" readonly="true"></fd:inputTextArea>
			</fd:form>
		</c:if>
		</div>
	</fd:panel>
	
	<fd:panel id="panel2" border="false" title="操作日志" collapsible="false" width="99%" fit="false">
		<!-- 顶部存放 end -->
		<!-- 底部存放  begin -->
		<fd:datagrid pagination="false" idField="taskDetailBasicOperation"
			url="planLogController.do?list&planId=${plan_.id}"
			id="taskDetailBasicOperation" fit="false" fitColumns="true" width="100%">
			<fd:dgCol title="{com.glaway.ids.pm.project.task.attachmentDownload}" field="filePath"
				formatterFunName="downLoadFile"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.logInfo}" field="logInfo"
				formatterFunName="taskDescription" ></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.common.lable.operator}" field="createName"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.common.lable.operateTime}" field="createTimeStr"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.common.lable.remark}" field="remark" hidden="true"></fd:dgCol>
		</fd:datagrid>
	</fd:panel>
</div>

	<script type="text/javascript">
	function viewMilestone(milestone){
		if(milestone == '' || milestone == 'false'){
			return '<spring:message code="com.glaway.ids.pm.project.task.taskdetail.no"/>';
		}
		else{
			return '<spring:message code="com.glaway.ids.pm.project.task.taskdetail.yes"/>';
		}
	}

	$(function(){
		$.ajax({
			url : 'planController.do?checkPlanStatus&id=${plan_.id}',
			type : 'post',
			data : {},
			cache : false,
			success : function(data) {
			}
		});
	});
	
	function taskDescription(val,row,value){
		//return '<a href="javascript:void(0)"  class="tips_taskDetailBasicOperation"  title="'+row.remark+'">' + row.logInfo + '</a>';
		var str='<a href="javascript:void(0)" style="color:black" class="tips_resourceList"  title="'+row.remark+'" >' + row.logInfo + '</a>'
		return str ;
	}
	
	function downLoadFile(val, row, value){
		if(row.filePath!=""){
			return '<a href="taskFeedbackController.do?downFile&id='+ row.id +'">' +
				   '<span class="basis ui-icon-attachment" title="<spring:message code='com.glaway.ids.pm.project.task.attachmentDownload'/>">&nbsp;&nbsp;&nbsp;&nbsp;</span>' + 
				   '</a>';
		}
	}
	
	$(function(){
		if($('#parentPlanName').textbox("getValue")==""){
			$('#parentPlanName').textbox("setValue","无");
		}
		if($('#preposePlans').textbox("getValue")==""){
			$('#preposePlans').textbox("setValue","无");
		}
		if($('#planLevelName').textbox("getValue")==""){
			$('#planLevelName').textbox("setValue","无");
		}
	})
	</script>

