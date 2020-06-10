<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>项目计划基本信息查看</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
	<body>


			<%-- <fd:form id="formId"> --%>
		<%--		<fd:lazyLoadingTreeGrid
					url="planChangeMassController.do?planChangeSelectListView&projectId=${projectId}"
					id="planChangeSelectList" width="100%" height="450px"
					enableMultiselect="true" initWidths="0,0,200,*,*,*,*,*,*"
					imgUrl="plug-in/icons_greenfolders/"
					columnIds="id,planNumber,planName,planLevelInfo,ownerInfo,planStartTime,planEndTime,workTime,milestone"
					header="ID, ,计划名称,计划等级,负责人,开始时间,结束时间,工期(天),里程碑"
					columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;']"
					colAlign="left,left,left,left,left,left,left,left,left"
					colSortings="na,na,na,na,na,na,na,na,na,"
					colTypes="ro,ro,tree,ro,ro,ro,ro,ro,ro" enableTreeGridLines="true"
					enableLoadingStatus="true">
				</fd:lazyLoadingTreeGrid>--%>

			<fd:lazytreegrid id="planChangeSelectList" idField="id" treeField="planName" singleSelect="false" style="width:100%;height:100%;"
							 url="planChangeMassController.do?planChangeSelectListView&projectId=${projectId}" >
				<fd:columns>
					<fd:column field="planName" title="计划名称" formatter="viewPlanTwo" />
					<fd:column field="planLevelName" title="计划等级" />
					<fd:column field="ownerRealName" title="负责人" />
					<fd:column field="planStartTime" title="开始时间" />
					<fd:column field="planEndTime" title="结束时间" />
					<fd:column field="workTime" title="工期(天)" />
					<fd:column field="milestone" title="里程碑" />
				</fd:columns>
			</fd:lazytreegrid>
				

				
			<%-- </fd:form> --%>
		<fd:dialog id="viewPlanDialog" width="750px" height="500px"
			modal="true" title="{com.glaway.ids.pm.project.plan.viewPlan}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.close}"
				callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>

	</body>

	<script type="text/javascript">
		//计划名称链接事件
		function viewPlanTwo(val, row, value) {
			debugger;
			if(row.bizCurrent != "EDITING" || row.flowStatus != "NORMAL"){
				return '<a href="#" onclick="viewPlan(\'' + row.id
						+ '\')" style="color:gray">' + row.planName + '</a>';
			}else{
				return '<a href="#" onclick="viewPlan(\'' + row.id
						+ '\')" style="color:blue">' + row.planName + '</a>';
			}

		}

		// 查看计划信息
		function viewPlan(id) {
			var dialogUrl = 'planController.do?goCheck&id=' + id;
			createDialog('viewPlanDialog', dialogUrl);
		}


	</script>
</html>