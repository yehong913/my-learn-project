<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html> 
<html>
<head>
<title>流程任务变更填写原因</title>
<t:base type="jquery,easyui,tools"></t:base>
<c:if test="${!empty outwards}">
	<c:forEach items="${outwards}" var="out">
		<script type="text/javascript" src="webpage/com/glaway/changeFlowTask/changeFlowTask_${out.optionValue}.js"></script>
	</c:forEach>
</c:if>
</head>
<body>
<div>
    <fd:form id="planChangeForm">
		<input id="parentPlanId" name="parentPlanId" value="${parentPlanId}" type="hidden" />	
		<input id="parentPlanName" name="parentPlanName" value="${parentPlanName}" type="hidden" />	
		<input id="status" name="status" value="${status}" type="hidden" />			
		<fd:combobox id="changeType" textField="name" title="{com.glaway.ids.pm.project.plan.changeType}"  
			editable="false" valueField="id" required="true" panelMaxHeight="200"
			url="planChangeMassController.do?planChangeCategorylList" />
		<fd:inputTextArea id="changeRemark" title="{com.glaway.ids.common.lable.remark}" name="changeRemark"/>	
	</fd:form>
</div>
		
<script type="text/javascript">
	function startAssignProcess(iframe) {
		var win =iframe.contentWindow;
	  	var leader = win.$('#leader').val();
		var deptLeader = win.$('#deptLeader').val();
		var changeType = $('#changeType').val();
		var changeRemark = $('#changeRemark').val();
		var parentPlanId = $('#parentPlanId').val();
		var status = $('#status').val();
		var parentPlanName = $('#parentPlanName').val();
 		if (leader == '') {
			top.tip('<spring:message code="com.glaway.ids.common.emptyOfficeLeader"/>');
			return false;
		} else if (deptLeader == '') {
			top.tip('<spring:message code="com.glaway.ids.common.emptyDepartLeader"/>');
			return false;
		} else{
			var url = 'taskFlowResolveController.do?startPlanChange';
	    	$.ajax({
				url : url,
				type : 'post',
				data : {
					parentPlanId : parentPlanId,
					leader : leader,
					deptLeader : deptLeader,
					changeType : changeType,
					changeRemark : changeRemark
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					var msg = d.msg.split('<br/>')
					top.tip(msg[0]);
					refreshTabsByTabsName('待办任务,首页');					
 					setTimeout(function(){
				  		$.fn.lhgdialog("closeAll");
				  	},200);
 					
					debugger;
					if (d.success) {
						var flowTaskForChange = d.obj;
						$('#status').val("1");
						var win2 = $.fn.lhgdialog("getSelectParentWin") ;
						<c:if test="${!empty outwards}">
							<c:forEach items="${outwards}" var="out">
								var outType = '${out.optionValue}';
								eval("outSystemDataSynchronizationForSubmit_" + outType+ "(flowTaskForChange)");
							</c:forEach>
						</c:if>
					  	var tab = top.$('#maintabs').tabs('getTab', parentPlanName);
					  	tab.panel('refresh');
					}
				}
			});  
		}
 		return true;
	}
	
	function goStart() {
		var changeType = $('#changeType').combobox('getValue');
		var changeRemark = $('#changeRemark').val();
		if (changeType == "") {
			top.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyChangeType"/>');
			return false;
		}
		if (changeRemark.length>200) {
			top.tip('<spring:message code="com.glaway.ids.common.remarkLength"/>')
			return false;
		}
		
		var url='${pageContext.request.contextPath}/taskFlowResolveController.do?assignTaskPlanForChange&parentPlanId='+$('#parentPlanId').val()
				+'&status='+$('#status').val() +'&changeType='+changeType+'&changeRemark='+changeRemark;
		$.fn.lhgdialog({
			href:url,
			height : 200,
			width : 400,
			title : '<spring:message code="com.glaway.ids.common.lable.change"/>',
			button:[{
				name:'<spring:message code="com.glaway.ids.common.btn.confirm"/>',callback:function(){
					return startAssignProcess(this.iframe);
				}
			
			},{name:'<spring:message code="com.glaway.ids.common.btn.cancel"/>',focus : true}]
		});
	}

	function closePlan() {
		window.parent.closeDiv("submit");
	}
</script>
</body>
</html>