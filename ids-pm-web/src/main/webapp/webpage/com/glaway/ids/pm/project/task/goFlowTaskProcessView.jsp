<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>流程任务</title>
	<t:base type="jquery,easyui,tools,DatePicker,lhgdialog"></t:base>
</head>
<body>
	<div id="viewTaskFlow" fit="true" style="width:1440px;height:600px;">
		<input type="hidden" id="parentPlanId" name="parentPlanId" value="${parentPlanId}" />
		<input type="hidden" id ="status" name="status" value="${status}"/>
	</div>

	<script type="text/javascript">
		$(function($) {
			debugger;
			var newdate = new Date().getTime();
			var parentPlanId = $("#parentPlanId").val();
			var status = $("#status").val();		
			if("ORDERED" == status){
				url = "taskFlowResolveController.do?flowResolveEditorForViewChange&newDate="+newdate+"&formId=${formId}&id="+parentPlanId+"";
			}
			else{
				url = "taskFlowResolveController.do?flowResolveEditor&newDate="+newdate+"&formId=${formId}";
			}
			$.ajax({
				type : 'POST',
				url : url,
				data : {
					id : parentPlanId
				},
				success : function(data) {
					debugger;
					var win = $.fn.lhgdialog("getSelectParentWin") ;
					$.fn.lhgdialog("closeSelect");
					/*var url = "${rdflowWeb_Nginx}/webpage/com/glaway/ids/rdtask/workFlow/examples/editors/workfloweditor.jsp?isEnableFlag=1"*/
					var url = "${rdflowWeb_Nginx}/rdflowCommonController.do?goOpenEditorPage&isEnableFlag=1"
							+"&parentPlanId=${parentPlanId}&status=${status}&newDate="+newdate+"&userId=${userId}";
// 					win.$("#"+'goFlowTaskGfl2').lhgdialog("open","url:"+url);
				  win.$("#"+'goFlowTaskGfl2').lhgdialog({
						content : 'url:' + url,
						id : 'goFlowTaskGfl2',
						title : "查看流程任务",
						height : '900',
						width : '140',
						max : false,
						min : false,
						maxFun : true
					});  
					
/* 					window.open(
							"webpage/com/glaway/ids/rdtask/workFlow/examples/editors/workfloweditor.html?isEnableFlag=1&parentPlanId=${parentPlanId}&status=${status}",
							'编辑流程任务',
							'height=900,width=1440,top=10,left=0,toolbar=no,menubar=no,scrollbars=yes,location=no,status=no'); */
					/* $("#viewTaskFlow").load("webpage/com/glaway/ids/rdtask/workFlow/examples/editors/workfloweditor.html?isEnableFlag=1&parentPlanId=${parentPlanId}&status=${status}"); */
				} 
			});
		});
	</script>
 <fd:dialog id="goFlowTaskGfl2" height="900" width="1440" max="false" min="false" maxFun="true" ></fd:dialog> 
</body>
</html>