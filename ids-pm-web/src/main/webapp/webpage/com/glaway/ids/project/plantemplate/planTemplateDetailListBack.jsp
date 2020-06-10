<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>计划模板详细</title>
<t:base type="jquery,easyui_iframe,tools"></t:base>
<!-- <script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script> -->


</head>
<body>
	<div class="easyui-panel div-msg">
		<div class="easyui-layout">
			<fd:form id="formId">
				<input type="hidden" id="planTemplateId" value="${planTemplate_.id}">
				<input type="hidden" id="taskId" value="${taskId}">
				<input type="hidden" id="taskTest" value="${taskTest}">
				<fd:lazyLoadingTreeGrid id="planTemplateDetailList"
					url="planTemplateDetailController.do?datagrid&id=${planTemplate_.id}"
					width="100%" height="350px;" imgUrl="plug-in/icons_greenfolders/"
					initWidths="*,200,*,*,*,*,*"
					columnIds="planTmpNumber,name,planLevel,workTimeDisplay,milestone,deliverablesName,preposeName"
					header="计划序号,计划名称,计划等级,工期(天),里程碑,交付项名称,前置名称"
					columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;']"
					colAlign="left,left,left,left,left,left,left"
					colSortings="na,na,na,na,na,na,na"
					colTypes="ro,tree,ro,ro,ro,ro,ro"
					enableTreeGridLines="true" enableLoadingStatus="false">
				</fd:lazyLoadingTreeGrid>
			</fd:form>
		</div>
		
	 	<div class="div-msg-btn">
			<div>
				<fd:linkbutton id="backSubmit" onclick="backSubmit2()" value="提交审批" classStyle="button_nor" />
				<fd:linkbutton onclick="closePlan()" value="{com.glaway.ids.common.btn.cancel}" classStyle="button_nor" id="focusThree" />
			</div>
		</div>
	</div>
</body>
<script src="webpage/com/glaway/ids/project/plantemplate/planTemplateDetailList.js"></script>
<script type="text/javascript">
$(document).ready(function (){		
	setTimeout(function() {
		$('#focusThree').focus();;
	}, 1000);
});

function backSubmit2() {
	var planTemplateId = $('#planTemplateId').val();
	var taskId = $('#taskId').val();
	var taskNumber = $('#taskTest').val();
	$.ajax({
		type : 'POST',
		url : 'planTemplateController.do?doBackSubmit&planTemplateId='
				+ planTemplateId,
		data : {
			taskId : taskId,
			taskNumber : taskNumber
		},
		success : function(data) {
// 			W.getData();
			try{
            	$.fn.lhgdialog("getSelectParentWin").parent.flashTaskShow();
            }catch(e){
            	
            }
            
            try{
            	$.fn.lhgdialog("getSelectParentWin").parent.$("#startTaskListMore_wait").datagrid('reload');
            }catch(e){
            	
            }
			$.fn.lhgdialog("closeSelect");
		}
	});
}
function closePlan() {
	$.fn.lhgdialog("closeSelect");
}
</script>
</html>

