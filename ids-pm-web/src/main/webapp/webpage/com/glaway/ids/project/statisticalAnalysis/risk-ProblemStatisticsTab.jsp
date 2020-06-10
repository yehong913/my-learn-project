<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

	<div style="padding: 1px; overflow-x: hidden">
		<input id="projectId" type="hidden" value="${projectId}" />
		<div id="problemListtb" >
			<fd:toolbar id="toolbar">
				<fd:toolbarGroup align="left">
						<fd:linkbutton onclick="riskProblemTreeExport()" value="{com.glaway.ids.common.btn.export}" id="exportProblem"
						iconCls="basis ui-icon-export"/>
				</fd:toolbarGroup>
			</fd:toolbar>
		</div>
		
		<fd:lazyLoadingTreeGrid url="riskProblemController.do?getRiskProblemTreeFor&projectId=${projectId}" id="riskProblemTree"
			width="99%" height="80%" imgUrl="plug-in/icons_greenfolders/"
			initWidths="200,350,120,100,*"
			columnIds="problem,discrib,reportPerson,reportTime,status"
			header="问题,描述,登记人,登记时间,状态"
			columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;']"
			colAlign="left,left,left,left,left"
			colSortings="na,na,na,na,na"
			colTypes="tree,ro,ro,ro,ro"
			enableTreeGridLines="false" 
			enableLoadingStatus="true"
			enableMultiselect="true">
		</fd:lazyLoadingTreeGrid>
	</div>
	<fd:dialog id="measureDialog" width="1100px" height="560px" modal="true" title="查看解决措施">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>

<script type="text/javascript">
	
	// 导出风险问题数据
	function riskProblemTreeExport() {
		var url = encodeURI('riskProblemController.do?doDownloadRiskProblem&isExport=true&projectId=${projectId}&allCategory=true');
		url = encodeURI(url);
		window.location.href = url;
	}
	
	//刷新树
	function refreshTree(objStr) {
		riskProblemTree.clearAll();
		riskProblemTree.parse(objStr, 'js');
	}
	
	//查看问题信息的生命周期状态
	function showlifeCycle(id) {
		//RiskProblemInfo  id
		//弹出对象对应的流程
		var tabTitle = '风险问题工作流';
		var url = '${webRoot}/taskFlowCommonController.do?taskFlowList&taskNumber='+id;
		createdetailwindow_close(tabTitle, url, 800, 400);
	}
	
	function viewMeasure(problemId) {
		var dialogUrl = 'riskProblemTaskController.do?goRiskProblemMeasureListCheck&id='+problemId;
		createDialog('measureDialog',dialogUrl);
	}
</script>








