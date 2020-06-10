<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>计划基线复制</title>
<t:base type="jquery,easyui,tools"></t:base>
<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
</head>
<body>
	<div class="easyui-layout">
		<fd:form id="basicLineAddForm">	
			<input id="copyBasicLineName" name="copyBasicLineName" type="hidden" value="${copyBasicLineName}" />
			<div style = "height:200px">
				<fd:inputText id="basicLineName" title="{com.glaway.ids.pm.project.plan.basicLine.basicName}"  
					name="ownerDept" value="${basicLine_.basicLineName}" required="true" />	
				<fd:inputTextArea id="remark" title="{com.glaway.ids.common.lable.remark}" 
					name="remark" value="${basicLine_.remark}" />
			</div>
			<fd:lazyLoadingTreeGrid url="basicLineController.do?basicLineCopyList&basicLineIdForCopy=${basicLineIdForCopy}" id="basicLineCopyList"
					width="100%" height="280px" imgUrl="plug-in/dhtmlxSuite/imgs/"
					initWidths="200,*,*,*,*,*,*"
					columnIds="planName,planLevelInfo,ownerInfo,planStartTime,planEndTime,workTime"
					header="计划名称,计划等级,负责人,开始时间,结束时间,工期(天)"
					columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;']"
					colAlign="left,left,left,left,left,left"
					colSortings="na,na,na,na,na,na"
					colTypes="tree,ro,ro,ro,ro,ro"
					enableTreeGridLines="true" enableLoadingStatus="true"
					enableMultiselect="true">
			</fd:lazyLoadingTreeGrid>
		</fd:form>
	</div>
	<input type="button" id="btn_sub" style="display: none;" onclick="copyBasicLine();">
	
	<fd:dialog id="viewPlanDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.viewPlan}">
	    <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	
	<script type="text/javascript">
	$(document).ready(function (){
		$("#basicLineCopyList").treegrid({
			onClickRow : editCopy
		}); 
	});
	
	function editCopy(row) {
		var parent = $("#basicLineCopyList").treegrid('getParent',row.id);
		var rows =  $("#basicLineCopyList").treegrid("getSelections");
		
		var a = 0;
		for(var i = 0; i < rows.length; i++) {
			if(rows[i].id == row.id){
				a = 1;
			}
		}
		
		if (a == 1) {
			if (parent != null) {
				$('#basicLineCopyList').treegrid('select',parent.id);
			}
			
			var rows = $('#basicLineCopyList').treegrid("getChildren", row.id)
			for (var i = 0; i < rows.length; i++) {
				$('#basicLineCopyList').treegrid('select', rows[i].id);
			}
		} else {
			var child = $("#basicLineCopyList").treegrid('getChildren', row.id);
			for (var i = 0; i < child.length; i++) {
				$("#basicLineCopyList").treegrid('unselect', child[i].id);
			}
		}
	}
	
	
	function copyBasicLine() {
		var win = $.fn.lhgdialog("getSelectParentWin");
		var copyBasicLineName = $('#copyBasicLineName').val();
		var basicLineName = $('#basicLineName').val();
		var remark = $('#remark').val();
		if (basicLineName == "") {
			win.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyName"/>');
			return false;
		}
		if (basicLineName == copyBasicLineName) {
			win.tip('<spring:message code="com.glaway.ids.pm.project.plan.basicLine.existingName"/>');
			return false;
		}
		
		var selectedId = basicLineCopyList.getSelectedRowId();
		if (selectedId != undefined && selectedId != null && selectedId != '') {
			$.ajax({
				url : 'basicLineController.do?copyBasicLine',
				type : 'post',
				data : {
					ids : selectedId,
					basicLineName : basicLineName,
					remark : remark,
					projectId : '${projectId}',
					basicLineIdForCopy : '${basicLineIdForCopy}'
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
	function viewPlan(id) {
		var dialogUrl = 'basicLineController.do?goCheck&id=' + id;
		createDialog('viewPlanDialog', dialogUrl);
	}
	</script>
</body>
</html>
