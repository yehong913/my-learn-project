<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>计划基线修改</title>
<t:base type="jquery,easyui,tools"></t:base>
<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
</head>
<body>
<div border="false" class="easyui-panel div-msg" fit="true">
	<div class="easyui-layout" fit="true">
		<fd:form id="basicLineAddForm">	
			<div style = "height:200px">
				<fd:inputText id="basicLineName" title="{com.glaway.ids.pm.project.plan.basicLine.basicName}"  
					name="ownerDept" value="${basicLine_.basicLineName}" required="true" />	
				<fd:inputTextArea id="remark" title="{com.glaway.ids.common.lable.remark}" 
						name="remark" value="${basicLine_.remark}" />
			</div>
			<fd:lazyLoadingTreeGrid url="basicLineController.do?basicLineEditList&basicLineId=${basicLineId}" id="basicLineEditList"
					width="100%" height="280px;" imgUrl="plug-in/icons_greenfolders/"
					initWidths="20,200,*,*,*,*,*,*"
					columnIds="planNumber,planName,planLevelInfo,bizCurrentInfo,ownerInfo,planStartTime,planEndTime,workTime"
					header=" ,计划名称,计划等级,状态,负责人,开始时间,结束时间,工期(天)"
					columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;']"
					colAlign="left,left,left,left,left,left,left,left"
					colSortings="na,na,na,na,na,na,na,na"
					colTypes="ro,tree,ro,ro,ro,ro,ro,ro"
					enableTreeGridLines="true" enableLoadingStatus="true"
					enableMultiselect="true">
			</fd:lazyLoadingTreeGrid>
       	</fd:form>
	</div>

	<div class="div-msg-btn">
		<div>
			<fd:linkbutton onclick="addBasicLine()" value="{com.glaway.ids.common.btn.submit}" classStyle="button_nor" />
			<fd:linkbutton onclick="closePlan()" value="{com.glaway.ids.common.btn.cancel}" classStyle="button_nor"  id="focusOne"/>		
		</div>
	</div>

	<fd:dialog id="viewPlanDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.viewPlan}">
	    <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog" />
	</fd:dialog>
</div>

	<script type="text/javascript">
	$(document).ready(function () {
		$('#focusOne').focus();
	});
	
	function addBasicLine() {
		var basicLineName = $('#basicLineName').val();
		var remark = $('#remark').val();
		
		if (basicLineName == "") {
			tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyName"/>');
			return false;
		}
			
    	$.ajax({
			url : 'basicLineController.do?startBasicLineFlow',
			type : 'post',
			data : {
				basicLineName : basicLineName,
				remark : remark,
				basicLineId : '${basicLineId}',
				taskId : '${taskId}'
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				tip(d.msg);
				if (d.success) {
// 					W.getData();
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
			}
		});
	}
		
	// 查看计划信息
	function viewPlan(id) {
		var dialogUrl = 'basicLineController.do?goCheck&id=' + id;
		createDialog('viewPlanDialog', dialogUrl);
	}
	</script>
</body>
</html>
