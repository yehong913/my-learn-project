<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划批量下达</title>
<t:base type="jquery,easyui,tools"></t:base>
<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
<script type="text/javascript">
	$(document).ready(function (){
		$('#cancelBtn').focus();
	});
	var ids2 = [];
	function startAssignProcess() {
		var win = $.fn.lhgdialog("getSelectParentWin");
		var ids = planAssignList.getSelectedRowId();
		if (ids != null && ids != '') {
			ids2 = ids.split(',');
			$.ajax({
				type:'POST',
				data:{
					ids : ids2.join(","),
					useObjectType : "PLAN"
				},
				url:"planController.do?checkOriginIsNullBeforeSub",
				cache:false,
				success:function(data){
					debugger;
					var d = $.parseJSON(data);
					if(d.success){
						$.ajax({
							url : 'planController.do?pdAssignAll&projectId=${projectId}&ids='+ids2,
							type : 'post',
							data : {},
							cache : false,
							success : function(data) {
								var d = $.parseJSON(data);
								var msg = d.msg;
								if (d.success) {
									//获取参数判断是否是从KDD入口进入IDS
									var kddProductType='${param.kddProductType}';
									var dialogUrl = 'planController.do?goAssignPlanMassUser&kddProductType='+kddProductType;
									createDialog('selectDialog', dialogUrl);
								} else {
									win.tip(msg);
									return false;
								}
							}
						});
					}else{
					    top.tip(d.msg);
					    return false;

						// top.Alert.confirm(d.msg, function(r) {
						// 	if(r){
								/*$.ajax({
									url : 'planController.do?pdAssignAll&projectId=${projectId}&ids='+ids2,
									type : 'post',
									data : {},
									cache : false,
									success : function(data) {
										var d = $.parseJSON(data);
										var msg = d.msg;
										if (d.success) {
											//获取参数判断是否是从KDD入口进入IDS
											var kddProductType='${param.kddProductType}';
											var dialogUrl = 'planController.do?goAssignPlanMassUser&kddProductType='+kddProductType;
											createDialog('selectDialog', dialogUrl);
										} else {
											win.tip(msg);
											return false;
										}
									}
								});*/
							// }
						// });
					}
				}
			});
			
			
		} else {
			win.tip('<spring:message code="com.glaway.ids.pm.project.plan.selectGrant"/>');
			return false;
		}
	}

	function selectDialog(iframe) {
		var flg = iframe.startAssignProcess(ids2);

		return false;
	}

	// 查看计划信息
	function viewPlan_(id) {
		var dialogUrl = 'planController.do?goCheck&id=' + id;
		createDialog('viewPlanDialog', dialogUrl);
	}
</script>
</head>
<body>
	<div class="easyui-layout">
		<form id="formId">
			<fd:lazyLoadingTreeGrid
				url="planController.do?planAssignList&projectId=${projectId}"
				id="planAssignList" width="100%" height="495px"
				enableMultiselect="true" initWidths="0,0,200,*,*,*,*,*"
				imgUrl="plug-in/icons_greenfolders/"
				columnIds="id,planNumber,planName,planLevelInfo,ownerInfo,planStartTime,planEndTime,workTime"
				header="ID, ,计划名称,计划等级,负责人,开始时间,结束时间,工期(天)"
				columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;']"
				colAlign="left,left,left,left,left,left,left,left"
				colSortings="na,na,na,na,na,na,na,na"
				colTypes="ro,ro,tree,ro,ro,ro,ro,ro" enableTreeGridLines="true"
				enableLoadingStatus="true">
			</fd:lazyLoadingTreeGrid>
		</form>
	</div>

	<fd:dialog id="viewPlanDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.viewPlan}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="selectDialog" width="400px" height="150px" modal="true" title="{com.glaway.ids.pm.project.plan.basicLine.chosePeople}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="selectDialog"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
</body>
</html>
