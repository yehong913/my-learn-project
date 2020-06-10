<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划批量下达驳回确认节点，后续考虑用通知代替</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
	<div border="false" class="div-msg">
		<div class="easyui-layout">
			<fd:form id="formId">
				<input id="statusList" name="statusList" type="hidden" value="${statusList}">
				<fd:lazyLoadingTreeGrid url="planController.do?assignListViewTree" id="planAssignViewList"
					width="100%" height="330px" enableMultiselect="true"
					initWidths="0,0,200,*,*,*,*,*" imgUrl="plug-in/icons_greenfolders/"
					columnIds="id,planNumber,planName,planLevelInfo,ownerInfo,planStartTime,planEndTime,workTime"
					header="ID, ,计划名称,计划等级,负责人,开始时间,结束时间,工期(天)"
					columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;']"
					colAlign="left,left,left,left,left,left,left,left"
					colSortings="na,na,na,na,na,na,na,na,"
					colTypes="ro,ro,tree,ro,ro,ro,ro,ro"
					enableTreeGridLines="true" enableLoadingStatus="true">
				</fd:lazyLoadingTreeGrid>
			</fd:form>
		</div>
		<div class="div-msg-btn">
			<div>
				<fd:linkbutton onclick="sure()" value="{com.glaway.ids.common.btn.confirm}" classStyle="button_nor" />
				<fd:linkbutton id="cancelBtn" onclick="closePlan()" value="{com.glaway.ids.common.btn.cancel}" classStyle="button_nor" />
			</div>			
		</div> 
	</div> 
	<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
	<fd:dialog id="viewPlanDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.viewPlan}">
	    <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<script type="text/javascript">
		$(document).ready(function (){
			$('#cancelBtn').focus();
		});
		
		// 查看计划信息
		function viewPlan(id) {
			var dialogUrl = 'planController.do?goCheck&id=' + id;
			createDialog('viewPlanDialog',dialogUrl);
		}
		
		
		function sure() {
			    	$.ajax({
						url : 'planController.do?completePlanFlow',
						type : 'post',
						data : {
						},
						cache : false,
						success : function(data) {
							var d = $.parseJSON(data);
							if(d.success){
// 								W.getData();
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
		
		function closePlan() {
			$.fn.lhgdialog("closeSelect");
		}
	</script>
</body>
</html>
