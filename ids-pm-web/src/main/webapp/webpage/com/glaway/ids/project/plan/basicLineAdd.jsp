<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>计划基线新增</title>
<t:base type="jquery,easyui,tools"></t:base>
<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
</head>
<body> 
	<div class="easyui-layout">
		<fd:form id="basicLineAddForm">
			<input id="allplanList" name="allplanList" type="hidden" value="${allplanList}" />
			<div style = "height:200px">
				<fd:inputText id="basicLineName" title="{com.glaway.ids.pm.project.plan.basicLine.basicName}" 
					name="ownerDept" value="${basicLine_.basicLineName}" required="true" />	
				<fd:inputTextArea id="remark" title="{com.glaway.ids.common.lable.remark}" 
						name="remark" value="${basicLine_.remark}" />	
			</div>
			
			<fd:lazyLoadingTreeGrid url="basicLineController.do?basicLineAddList&projectId=${projectId}" id="basicLineAddList"
					width="100%" height="280px" imgUrl="plug-in/dhtmlxSuite/imgs/"
					initWidths="58,200,*,*,*,*,*,*"
					columnIds="planNumber,planName,planLevelInfo,bizCurrentInfo,ownerInfo,planStartTime,planEndTime,workTime"
					header="编号,计划名称,计划等级,状态,负责人,开始时间,结束时间,工期(天)"
					columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;']"
					colAlign="left,left,left,left,left,left,left,left"
					colSortings="na,na,na,na,na,na,na,na"
					colTypes="ro,tree,ro,ro,ro,ro,ro,ro"
					enableTreeGridLines="true" enableLoadingStatus="true"
					enableMultiselect="true">
			</fd:lazyLoadingTreeGrid>
		</fd:form>
	</div>
	<input type="button" id="btn_sub" style="display: none;" onclick="addBasicLine();">
	
	<fd:dialog id="viewPlanDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.viewPlan}">
	    <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	
	<script type="text/javascript">
	$(document).ready(function () {
		$("#basicLineAddList").treegrid({
			onClickRow : edit22
		}); 
	});
	
	function addBasicLine() {
		var win = $.fn.lhgdialog("getSelectParentWin");
		var basicLineName = $('#basicLineName').val();
		var remark = $('#remark').val();
		
		if (basicLineName == "") {
			win.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyName"/>');
			return false;
		}
		var selectedId = basicLineAddList.getSelectedRowId();
		if (selectedId != undefined && selectedId != null && selectedId != '') {
			$.ajax({
				url : 'basicLineController.do?saveBasicLine',
				type : 'post',
				data : {
					ids : selectedId,
					basicLineName : basicLineName,
					remark : remark,
					projectId : '${projectId}'
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					tip(d.msg);
					if (d.success) {
						var basicLineListSearch = win.basicLineListSearch();
						$.fn.lhgdialog("closeSelect");
					}
				}
			});
		}
		else {
	    	win.tip('<spring:message code="com.glaway.ids.pm.project.plan.basicLine.emptyPlan"/>');
			return false;
	    }
	}
		
	// 查看计划信息
	function viewPlan_(id) {
		var dialogUrl = 'basicLineController.do?goCheck&id=' + id;
		createDialog('viewPlanDialog', dialogUrl);
	}
	
	// 这段代码的意思？？？是否还需要
	function edit22(row) {
		var parent = $("#basicLineAddList").treegrid('getParent', row.id);
		var rows =  $("#basicLineAddList").treegrid("getSelections");
		
		var a = 0;
		for (var i = 0; i < rows.length; i++) {
			if (rows[i].id == row.id) {
				a = 1;
			}
		}
		
		if (a == 1) {
			if(parent != null) {
				$('#basicLineAddList').treegrid('select', parent.id);
			}
			
			var rows = $('#basicLineAddList').treegrid("getChildren", row.id)
			for (var i = 0; i < rows.length; i++) {
				$('#basicLineAddList').treegrid('select', rows[i].id);
			}
		} else {
			var child = $("#basicLineAddList").treegrid('getChildren', row.id);
			for (var i = 0; i < child.length; i++) {
				$("#basicLineAddList").treegrid('unselect', child[i].id);
			}
		}
	} 
	</script>
</body>
</html>
