<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui_iframe,tools"></t:base>
<div region="center" style="padding: 1px;">
	<!-- 标题 -->
	<!-- 表单区域 -->
	<div style="padding: 1px;">
		<fd:form id="procAddformobj">
			<input type="hidden" name="parentPlanId" id="parentPlanId" value="${parentPlanId}" />
			<input type="hidden" name="flowTaskStatus" id="flowTaskStatus" value="${flowTaskStatus}" />
			<c:choose>
				<c:when test="${isStandard=='ok'}">
					<fd:combobox title="{com.glaway.ids.pm.project.proctemplate.name}" id="procTmplName" textField="name"
						editable="true" valueField="name" required="true"
						panelMaxHeight="200"
						url="planController.do?standardValue"></fd:combobox>
				</c:when>
				<c:when test="${isStandard=='true'}">
					<fd:combobox title="{com.glaway.ids.pm.project.proctemplate.name}" id="procTmplName" textField="name"
						editable="true" valueField="name"
						validType="comboboxDistinctValidate['procTmplName']"
						panelMaxHeight="200"
						url="planController.do?standardValue" maxLength="30" required="true"></fd:combobox>
				</c:when>
				<c:otherwise>
					<fd:inputText id="procTmplName" name="procTmplName" required="true"
						title="{com.glaway.ids.pm.project.proctemplate.name}"></fd:inputText>
				</c:otherwise>
			</c:choose>
			<fd:inputText id="procTmpSubName" name="procTmpSubName" title="{com.glaway.ids.pm.project.plan.procTmpSubName}" required="false"></fd:inputText>
			<fd:inputTextArea id="remark" title="{com.glaway.ids.pm.project.task.remark}" name="remark"></fd:inputTextArea>
		</fd:form>
	</div>

	<script>
	function saveAsTemplate() {
		var procTmplName = "";
		if ("${isStandard}" == "false") {
			procTmplName = $('#procTmplName').val();
			if (procTmplName == "") {
				top.tip('<spring:message code="com.glaway.ids.pm.rdtask.proctemplate.emptyName"/>');
				return false;
			}
		} else {
			procTmplName = $('#procTmplName').combobox('getValue');
			if (procTmplName == "") {
				top.tip('<spring:message code="com.glaway.ids.pm.rdtask.proctemplate.emptyName"/>'); 
				return false;
			}
		}
		if (procTmplName.length > 30) {

			top.tip('<spring:message code="com.glaway.ids.pm.rdtask.proctemplate.nameLength"/>')
			return;
		}

		if ($("#procTmpSubName").val().length > 30) {
			top.tip('<spring:message code="com.glaway.ids.pm.rdtask.proctemplate.nameAdditionLength"/>')
			return;
		}
		if ($("#remark").val().length > 200) {
			top.tip('<spring:message code="com.glaway.ids.common.remarkLength"/>')
			return;
		}
		if('${isStandard}' == 'true'){
				$.ajax({
					url : 'deliverablesInfoController.do?pdNameForOther',
					type : 'post',
					data : {
						planName : procTmplName
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						var msg = d.msg;
						if(d.success){
							$.ajax({
								url : 'taskFlowResolveController.do?flowTaskSaveAsTemplate',
								type : "POST",
								dataType : "text",
								data : {
									parentPlanId : $("#parentPlanId").val(),
									flowTaskStatus : $("#flowTaskStatus").val(),
									procTmplName : procTmplName,
									procTmpSubName : $("#procTmpSubName").val(),
									remark : $("#remark").val()
								},
								async : false,
								cache : false,
								success : function(data) {
									var d = $.parseJSON(data);
									var msg = d.msg.split('<br/>')
									top.tip(msg[0]);
									if (d.success) {
										$.fn.lhgdialog("closeSelect");
									}
								}
							});
						}else{
							top.tip('名称不在活动名称库中，请重新选择！');
							return false;
						}
					}
				});
		}else{
			$.ajax({
				url : 'taskFlowResolveController.do?flowTaskSaveAsTemplate',
				type : "POST",
				dataType : "text",
				data : {
					parentPlanId : $("#parentPlanId").val(),
					flowTaskStatus : $("#flowTaskStatus").val(),
					procTmplName : procTmplName,
					procTmpSubName : $("#procTmpSubName").val(),
					remark : $("#remark").val()
				},
				async : false,
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					var msg = d.msg.split('<br/>')
					top.tip(msg[0]);
					if (d.success) {
						$.fn.lhgdialog("closeSelect");
					}
				}
			});			
		}
	}
	
	function closePlan() {
		$.fn.lhgdialog("closeSelect");
	}
	</script>