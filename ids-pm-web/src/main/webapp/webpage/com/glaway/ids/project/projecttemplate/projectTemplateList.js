function dateFormatter(val, row, value) {
		return dateFmtYYYYMMDD(val);
}

//人员id、realName转换
function userFormatter(value,row,index) {
	debugger;
	if (row.firstFullName != null && row.firstName != null){
		return row.firstFullName + "-" + row.firstName;
	} else {
		return row.createFullName + "-" + row.createName;
	}
}


function viewTemplate(val, row, value) {
	return "<a title='查看' style='color:blue' onclick='openTemplateDetail(\""+row.id+"\",\""+row.projTmplName+"\")'>"+row.projTmplName+"</a>";
}

function openTemplateDetail(id,name){
	var url="projTemplateController.do?goProjTemplateLayout&id="+id;
	createdetailwindowfortemplate(name+"模板详情", url, "1000", "500")
}



function doExcute(url,tipMsg,templates){
	
	
	Alert.confirm(tipMsg, function(r) {
		   if (r) {
				$.ajax({
					url :url,
					type : 'post',
					data : {
						'templates' : $.toJSON(templates),
					},
					cache : false,
					async : false,
					success : function(data) {
						var d = $.parseJSON(data);
						var rst = ajaxRstAct_(d);
						if (d.success) {
//							var msg = d.msg;
//							top.tip(msg);
							reload();   
							
						}
					}
				});
			}
		});
}

function reload() {
	$(''+projTemplateGrid).datagrid('reload');
	$(''+projTemplateGrid).datagrid('uncheckAll');
	$(''+projTemplateGrid).datagrid('clearSelections');
	 
}

//计划模板状态
function showProjTemplateStatus(val, row, value){
	if(row.processInstanceId != '' && row.processInstanceId != null && row.processInstanceId != undefined){
		return '<a href="#" onclick="openFlowTasks(\''
			+ row.id + '\')"><font color=blue>'+val+'</font></a>';
	}
	return val;
}

//弹出对象对应的流程
function openFlowTasks(taskNumber){
	var tabTitle = '项目模板工作流';
	var url = '${webRoot}/taskFlowCommonController.do?taskFlowList&taskNumber='+taskNumber;
	createdetailwindow_close(tabTitle, url, 800, 400);
}

function vidateLength(value, length) {
	if (value != null && value != ''&& value != 'undefined') {
		if (value.length > length) {
			return false;
		}
   	}
	return true;
}

function formatOp(value, row, index) {
	if(row.processInstanceId != '' && row.processInstanceId != null && row.processInstanceId != undefined){
		return '<a href=\'javascript:void(0)\' onclick="viewProcessDef(\''+row.processInstanceId +'\',\''+row.projTmplName+'\')" title=\'查看流程图\' ><font color=blue>'+value+'</font></a>'; 	      		
	}
	return value;
}

function viewProcessDef(procInstId,title)
{
	createdetailwindow(title+'-项目模板工作流','generateFlowDiagramController.do?initDrowFlowActivitiDiagram&procInstId='+procInstId,800,600);
}
