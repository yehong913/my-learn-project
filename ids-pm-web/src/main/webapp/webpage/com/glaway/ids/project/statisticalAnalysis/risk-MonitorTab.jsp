<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">

//风险名称链接事件
function viewRiskInfoSearch(val, row, index) {
	if(val == undefined)val = '';
	if(row.name!='正在处理的风险'&&row.name!='已处理的风险'&&row.name!='未发生的风险'){
		shtml = '<span title="'+val+'">'+val+'</span>';
		return '<a href="#" onclick="viewRiskForMonitor(\'' + row.id + '\')" style="color:blue">' + shtml + '</a>';
	} else {
		return '<span style="font-weight:bold" title="'+row.name+'">'+row.name+'</span>' ;
	}
}

//评估等级链接事件
function viewAssessInfo(val, row, index) {
	return '<a href="#" onclick="viewAssess(\'' + row.id + '\')" style="color:blue">' + val + '</a>';
}


//查看风险信息
function viewRiskForMonitor(id) {
	createDialog('checkRiskDialog2','riskModifyController.do?goCheck&isCheck=true&id=' + id);
}

//查看评估信息
function viewAssess(id) {
	var dialogUrl = 'riskController.do?goCheckAccess&id='+id;
	createDialog('accessCheckRiskDialog',dialogUrl);
}

//问题数链接事件-所有的问题
function viewProblemInfo(val, row, index) {
	debugger
	if (val == 0) {
		return val;
	} else {
		return '<a href="#" onclick="viewProblem(\'' + row.id +'\''+',\'1'+ '\')" style="color:blue">' + val + '</a>';
	}
} 

//问题数链接事件-未处理的问题
function viewUnCloseProblemInfo(val, row, index) {
	if (val == 0) {
		return val;
	} else {
		return '<a href="#" onclick="viewProblem(\'' + row.id +'\''+',\'2'+ '\')" style="color:blue">' + val + '</a>';
	}
} 
//问题数链接事件-已处理的问题
function viewCloseProblem(val, row, index) {
	if (val == 0) {
		return val;
	} else {
		return '<a href="#" onclick="viewProblem(\'' + row.id +'\''+',\'3'+ '\')" style="color:blue">' + val + '</a>';
	}
} 

//查看问题信息
function viewProblem(id, problemStatus) {
	var dialogUrl = 'riskProblemController.do?goRiskProblemListCheck&id='+id+'&problemStatus='+problemStatus;
	createDialog('problemCheckRiskDialog',dialogUrl);
}

//导出
function exportRisk() {
	// 导出计划Excel
	postFormDataToAction("riskMonitorController.do?doDownloadRisk&isExport=true&projectId=${projectId}");
}

/**
 * Jeecg Excel 导出
 * 代入查询条件
 */
function postFormDataToAction(url) {
	var params = '';
	window.location.href = url + encodeURI(params);
}
</script>

	<div style="padding: 1px; overflow-x: hidden">
		<fd:toolbar id="toolbar">
			<fd:toolbarGroup align="left">
				<fd:linkbutton id="exportRisk1" onclick="exportRisk()"
					value="导出" iconCls="basis ui-icon-export"/>
			</fd:toolbarGroup>
		</fd:toolbar>
		
		<%-- <fd:datagrid id="riskList" checkbox="false" toolbar="" fitColumns="true" rownumbers="false" 
				url="riskMonitorController.do?searchDatagrid&projectId=${projectId}" idField="id" fit="true" >	
			<fd:dgCol title="{com.glaway.ids.pm.project.statisticalAnalysis.riskName}" field="name" width="25"  sortable="true" formatterFunName="viewRiskInfoSearch"></fd:dgCol>
			<fd:dgCol title="负责人" field="ownerName" width="10"  sortable="true"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.statisticalAnalysis.assess}" field="assess" width="10" sortable="true" formatterFunName="viewAssessInfo"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.statisticalAnalysis.problemCount}" field="problemCount" width="5" sortable="true" formatterFunName="viewProblemInfo"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.statisticalAnalysis.unCloseProblem}" field="unCloseProblem" width="5"  sortable="true" formatterFunName="viewUnCloseProblemInfo"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.statisticalAnalysis.closedProblemCount}" field="closedProblemCount" width="20"  sortable="true" formatterFunName="viewCloseProblem"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.statisticalAnalysis.planStatus}" field="status" width="5"  sortable="true"></fd:dgCol>
		</fd:datagrid> --%>
	<fd:lazydatagrid id="riskList" fitColumns="true" idField="id" url="riskMonitorController.do?searchDatagrid&projectId=${projectId}"  style="height:700px;width:1500px" toolbar="" fit="false" >		
		<fd:columns>
			<fd:column  field="name" formatter="viewRiskInfoSearch" width="300">风险名称</fd:column>
			<fd:column  field="assess" formatter="viewAssessInfo" width="300">评估</fd:column>
			<fd:column  field="problemCount" formatter="viewProblemInfo" width="200">问题总数</fd:column>
			<fd:column  field="unCloseProblem" formatter="viewUnCloseProblemInfo" width="200">未处理的问题数</fd:column>
			<fd:column  field="closedProblemCount" formatter="viewCloseProblem" width="200">已处理的问题数</fd:column>
			<fd:column  field="status" width="300">状态</fd:column>
		</fd:columns>
	</fd:lazydatagrid>	
	</div>

<fd:dialog id="problemCheckRiskDialog" width="1100px" height="560px" modal="true" title="{com.glaway.ids.pm.project.statisticalAnalysis.checkRisk}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
	
<fd:dialog id="accessCheckRiskDialog" width="735px" height="560px" modal="true" title="{com.glaway.ids.pm.project.statisticalAnalysis.assessRisk}">
</fd:dialog>

	<fd:dialog id="checkRiskDialog2" width="1100px" height="560px" modal="true" title="{com.glaway.ids.pm.project.risk.viewRisk}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>




