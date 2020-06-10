	function planTemplateSearch() {
		var queryParams = $('#plantemplates').datagrid('options').queryParams;

		$('#planTemplateList').find('*').each(function() {
			queryParams[$(this).attr('name')] = $(this).val();
		});
		$('#plantemplates').datagrid({
			url : 'planTemplateController.do?datagrid&field=id',
			pageNumber : 1
		});

	}

	//重置
	function planTemplateSearchReset() {
		$("#planTemplateList").find(":input").val("");
		planTemplateSearch();
	}
	//给datagrid添加formmater，增加名称的链接
	
	function WPSPlanList(val, row, value) {
		return '<a href="javascript:void(0)" onclick="openDialogWPSPlans(\''
				+ row.id + '\')"><font color=blue>'+row.name+'</font></a>';
	}

	function openDialogWPSPlans(param) {
		var dialogUrl = 'planTemplateDetailController.do?planTemplateDetail&planTemplateId='
			+ param;
		createDialog('WPSPlanList',dialogUrl);
	}
	

	
	
	function add1(title,addurl,gname,width,height) {
		gridname=gname;
		createwindow1(title, addurl,width,height);
	}

	function createwindow1(title, addurl,width,height) {
		width = width?width:730;
		height = height?height:350;
		if(width=="100%" || height=="100%"){
			width = document.body.offsetWidth;
			height =document.body.offsetHeight-100;
		}
		if(typeof(windowapi) == 'undefined'){
			var dialogUrl=addurl;
			createDialog('add1',dialogUrl);
		}else{
			var dialogUrl=addurl;
			createDialog('add1',dialogUrl);
		}
		
	}
	
	function importPlanTem(iframe){
		$('#btn_sub', iframe.document).click();
		return false;
	}
	
	
	
	
	
	function getPlanTemplate(){
		var rows = $("#plantemplates").datagrid('getSelections');
		if(rows.length==1){
			return rows[0];
		}
	}
	
	
	
	//操作列显示-重构版-R105
	function operations(val, row, value){
		var returnStr = '';
		if(row.approveStatus=='拟制中'){
			returnStr +='<a title="删除" class="basis ui-icon-minus" onclick="doDelete(\''+row.id+'\')" style="display:inline-block"></a>'/*+
			'<a id="ok" title="启用" class="basis ui-icon-start" style="display:inline-block"></a>'+
			'<a id="no" title="禁用" class="basis ui-icon-closethick" style="display:inline-block"></a>';*/
			return returnStr +="<a title='提交' class='basis ui-icon-submitted_approval' onclick=\"goSubmitApprove('planTemplateController.do?goSubmitApprove','plantemplates',\'"+row.approveStatus + "\',\'"+ row.id + "\',this)\" style='display:inline-block'>" +
			"</a>";
		}else if(row.approveStatus=='已审批'){
			
			if(row.status=="启用")	{
				returnStr +='<a title="删除" class="basis ui-icon-minus" onclick="doDelete(\''+row.id+'\')" style="display:inline-block"></a>'+
				'<a title="禁用" class="basis ui-icon-forbidden" onclick="statusOperate(\''
				+ row.status + '\',\''+ row.id + '\',this)" style="display:inline-block"></a>';
			}else if(row.status=="禁用"){
				returnStr +='<a title="删除" class="basis ui-icon-minus" onclick="doDelete(\''+row.id+'\')" style="display:inline-block"></a>'+
				'<a title="启用" class="basis ui-icon-enable" onclick="statusOperate(\''
				+ row.status + '\',\''+ row.id + '\',this)" style="display:inline-block"></a>';
			}else{
				returnStr +='<a title="删除" class="basis ui-icon-minus" onclick="doDelete(\''+row.id+'\')" style="display:inline-block"></a>';
			}
			return returnStr;
		}else{
			//returnStr +='<a title="删除" class="basis ui-icon-minus" onclick="doDelete(\''+row.id+'\')" style="display:inline-block"></a>';
		}
		//return returnStr +="<a title='提交' class='basis ui-icon-submitted_approval' onclick=\"goSubmitApprove('planTemplateController.do?goSubmitApprove','plantemplates',\'"+row.approveStatus + "\',\'"+ row.id + "\',this)\" style='display:inline-block'>" +
				//"</a>";
	}
	
	//查询计划模板状态
	function approveStatusShow2(val, row, value){
		var status;
		if(row.approveStatus=="qiyong"){		
			status = "启用";
		}else if(row.approveStatus=="jinyong"){
			status = "禁用";
		}else if(row.approveStatus=="nizhi"){
			status ="拟制中";
		}else if(row.approveStatus=="shenhe"){
			status ="审批中";
		}else if(row.approveStatus=="xiuding"){
			status ="修订中";
		}
		//如果没有流程，则不需要点击
		var resultUrl = status;
		if(row.processInstanceId != null && row.processInstanceId != '' && row.processInstanceId != undefined){
			resultUrl = '<a href="javascript:void(0)" onclick="openFlowTasks(\''
				+ row.processInstanceId + '\')"><font color=blue>'+status+'</font></a>';
		}
		return resultUrl;
	}
	
	//弹出对象对应的流程
	function openFlowTasks(procInstId){
		/*var tabTitle = '计划模板工作流';
		var url = 'taskFlowCommonController.do?taskFlowList&taskNumber='+taskNumber;
		createdetailwindow_close(tabTitle, url, 800, 400);*/
		var tabTitle = '计划模板工作流';
		createdetailwindow(tabTitle,
				'generateFlowDiagramController.do?initDrowFlowActivitiDiagram'
						+ '&procInstId=' + procInstId, 800, 600);
	}
	
	function versionFormatter(val, row, value) {
		if(row.bizVersion != undefined && row.bizVersion != null && row.bizVersion != '') {
			return '<a href="javascript:void(0)" onclick="openDialogVersionDetail(\''
			+ row.id + '\')"><font color=blue>'+row.bizVersion+'</font></a>';	
		}
	}
	
	function openDialogVersionDetail(param) {
		var dialogUrl = 'planTemplateDetailController.do?planTemplateVersionDetail&planTemplateId='
			+ param;
		createDialog('versionDetailList',dialogUrl);
	}
	
	
