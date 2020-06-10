// 显示发起人名称
function viewStartName(val, row, value) {
	return row.createrFullname + "-" + row.creater;
}

//显示完成时间
function viewEndTime(val, row, index) {
	//如果是已完成与已关闭，则显示数据
	var resultVal = "";
	if('已完成'==row.status || '已关闭'== row.status){
		resultVal = dateFmtYYYYMMDD(val);
	}
	return resultVal;
}

function formatOp(value, row, index) {
	return '<a href=\'javascript:void(0)\' onclick="viewProcessDef(\''+row.procInstId +'\',\''+row.title+'\')" title=\'查看流程图\'  class=\'basis ui-icon-eye\' style=\'display:inline-block\'></a>'; 	      		
}

function viewProcessDef(procInstId,title)
{
	createdetailwindow(title,'generateFlowDiagramController.do?initDrowFlowActivitiDiagram&procInstId='+procInstId,800,600);
}
