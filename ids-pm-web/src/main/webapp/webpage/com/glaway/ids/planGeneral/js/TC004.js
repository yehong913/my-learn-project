window.TC004 = new Object();
var _this =  window.TC004;
var enterType;
var onlyReadonly;
var fromDetailType;
var isEnableFlag;
var dataGrideId = "";

!function(){

//资源名称链接事件
_this.resourceNameLink = function(val, row, value) {
	 return '<a href="#" onclick="TC004.viewResourceCharts(\'' + row.id + '\')" style="color:blue">' + val + '</a>';
}

// 资源名称链接事件
_this.viewResourceCharts = function(id){
    debugger
    var tab = $('#tt').tabs('getSelected');
    var index = $('#tt').tabs('getTabIndex',tab);
    var startTime = $("#startTime"+index).val();
    var endTime = $("#endTime"+index).val();
   	dataGrideId = "ResourceLinkInfo";
    var rows = $('#'+dataGrideId).datagrid('getRows');
    var index1 = $('#'+dataGrideId).datagrid('getRowIndex', id);
    var row = rows[index1];
    fromDetailType = $('#fromDetailType').val();
    if('planResolve' == fromDetailType || 'mxgraphPlanChange' == fromDetailType){
    	var planId = $('#id').val();
    	if('mxgraphPlanChange' == fromDetailType){
    		$.ajax({
				url : '/ids-pm-web/applyFlowResolveForChangeController.do?getRealPlanId',
				type : 'post',
				data : {
					'cellId' : $("#cellId").val(),
					'parentPlanId' : $("#parentPlanId").val()
				},
				cache : false,
				success : function(data) {
				 var d = $.parseJSON(data);
	                if (d.success) {
	                	planId = d.obj;
	                }
				}
			});
    	}
    	if(row.useRate != null && row.useRate != '' && row.startTime != null && row.startTime != '' && row.endTime != null && row.endTime != ''){
				var url = "/ids-pm-web/resourceLinkInfoController.do?goToToBeUsedRateReport&resourceId="+ row.resourceInfo.id + "&resourceLinkId="
			          + row.id + "&resourceUseRate=" + row.useRate + "&startTime=" + row.startTime + "&endTime=" + row.endTime + "&useObjectId="+planId+"";
				  createDialog('resourceDialog',url);
//			parent.resourceChartsFtctShow(url);
		}
		else{
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.resource.emptyUseInfo"/>');
		}
    }else{
    	 var planId= $("#planId").val();
    	    if (row.useRate != null && row.useRate != '' && row.startTime != null && row.startTime != '' && row.endTime != null && row.endTime != '') {
    	        createDialog('resourceDialog',"/ids-pm-web/resourceLinkInfoController.do?goToUsedRateReport&resourceId="
    	            + row.resourceId + "&startTime="+row.startTime+"&endTime="+row.endTime+"");
    	    }
    	    else {
    	        alert('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyUseInfo"/>');
    	    }
    }

}

// 使用百分比
_this.viewUseRate2 = function (val, row, value){
    return progressRateGreen2(val);
}

function progressRateGreen2(val) {
    if (val == undefined || val == null || val == '') {
        val = 0;
    }
    return val + '%' ;
}

function viewStartEndTime(val, row, value) {
    var start = row.startTime;
    var end = row.endTime;
    if ((start != undefined && start !=null && start != '')
        && (end != undefined && end !=null && end != '')) {
        return dateFmtYYYYMMDD(start) + "~" + dateFmtYYYYMMDD(end);
    }
    return "";
}

_this.isEditor = function(){
	debugger
	if(enterType=='2' || onlyReadonly=='1' || "1" == $("#isEnableFlag").val()){
		$("#TC004新增Div").hide();
		$("#TC004删除Div").hide();
		$("#TC004修改Div").hide();
	}else{
		$("#TC004新增Div").show();
		$("#TC004删除Div").show();
		$("#TC004修改Div").show();
	}
   }

//新增资源
_this.addResource = function(){
	if("1" == $("#isEnableFlag").val()){
		top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
		return;
	}
	var dialogUrl = 'taskFlowResolveController.do?goAddResource&cellId='+$("#cellId").val()+'&parentPlanId='+$("#parentPlanId").val();
	fromDetailType = $('#fromDetailType').val();
    if('mxgraphPlanChange' == fromDetailType){
    	dialogUrl = 'applyFlowResolveForChangeController.do?goChangeAddResource&cellId='+$("#cellId").val()+'&parentPlanId='+$("#parentPlanId").val();
    }
	createDialog('addResourceDialog',dialogUrl);
   }

	//删除资源
	_this.deleteSelections2 = function (gridname, url) {
		if("1" == $("#isEnableFlag").val()){
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
			return;
		}
		 dataGrideId = "ResourceLinkInfo";
		 var row = $('#'+dataGrideId).datagrid('getSelected');
         if (row == null || row == '' || row == 'undefined'){
             tip("请选择一记录");
             return false;
         }
         debugger
         var ids = [];
         var rows = $('#' + dataGrideId).datagrid('getChecked');
         for ( var i = 0; i < rows.length; i++) {
             ids.push(rows[i].id);
         }

		if('planResolve' == fromDetailType){
	    	$.ajax({
				url : 'resourceLinkInfoController.do?doBatchDelForPlanResolve',
				type : 'post',
				data : {
					'ids' : ids.join(",")
				},
				cache : false,
				success : function(data) {
					$("#"+dataGrideId).datagrid("reload");
				}
			});
	    }else if('mxgraphPlanChange' == fromDetailType){
	    	debugger
	    	$.ajax({
				url : 'taskFlowResolveController.do?doDelChangeResource',
				type : 'post',
				data : {
					'id' : row.id,
					'useObjectId' : row.useObjectId,
					'parentPlanId': $('#parentPlanId').val()
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						$.ajax({
							type : 'POST',
							url : 'taskFlowResolveController.do?changeResourceList',
							async : false,
							data : {
									cellId : $('#cellId').val(),
									parentPlanId : $('#parentPlanId').val()
								},
							success : function(data) {
								debugger
								 $("#ResourceLinkInfo").datagrid('clearSelections');		
								 $("#ResourceLinkInfo").datagrid("loadData",data);
							} 
						});
					}
					else{
						var msg = d.msg.split('<br/>')
						top.tip(msg[0]); // John
					}
				}
			});
	    }
	}


	// 维护资源
	_this.modifyResource = function(){
		dataGrideId = "ResourceLinkInfo";
		var rows = $('#'+dataGrideId).datagrid('getSelections');
		if (rows.length == 0) {
			top.tip("请选择一条数据");
			return false;
		}
		if (rows.length > 1) {
			top.tip("当前操作只能选择一条数据");
			return false;
		}

		var row;
		index = $('#'+dataGrideId).datagrid('getRowIndex',
			$('#'+dataGrideId).datagrid('getSelected'));
		var all = $('#'+dataGrideId).datagrid('getRows');
		for (var i = 0; i < all.length; i++) {
			var inx = $('#'+dataGrideId).datagrid('getRowIndex', all[i]);
			if (inx == index) {
				row = all[i];
			}
		}

		var dialogUrl = "";
		if('planResolve' == fromDetailType) {
			dialogUrl = 'taskFlowResolveController.do?goModify' + '&id=' + row.id;
		}else if('mxgraphPlanChange' == fromDetailType){
			dialogUrl = 'taskFlowResolveController.do?goModifyForChange' + '&id=' + row.id;
			dialogUrl = dialogUrl + '&parentPlanId='+$("#parentPlanId").val();
			dialogUrl = dialogUrl + '&useObjectId='+row.useObjectId;
		}
		createDialog('modifyResourceDialog',dialogUrl);
	}
	   
}();

function initResourceChangeFlow() {
	$("#ResourceLinkInfo").datagrid('clearSelections');		
	$("#ResourceLinkInfo").datagrid("reload");
}

//资源
$(function() {
	isEnableFlag = $("#isEnableFlag").val();
	enterType = $("#enterType").val();
	onlyReadonly = $("#onlyReadonly").val();
    var tab = $('#tt').tabs('getSelected');
    var index = $('#tt').tabs('getTabIndex',tab);
	var taskNumber = $("#taskNumber").val();
    dataGrideId =  "ResourceLinkInfo";
    _this.isEditor(); 
    var url = "";
    fromDetailType = $('#fromDetailType').val();
    var cellId = $("#cellId").val();
    var parentPlanId = $("#parentPlanId").val();
    if('planResolve' == fromDetailType){
    	url = "taskFlowResolveController.do?resourceList&cellId="+cellId+"&parentPlanId="+parentPlanId+"";
    }else if('planChange' == fromDetailType){
		url = 'planChangeController.do?resourceListView&formId=' + taskNumber;
	}
	else if('mxgraphPlanChange' == fromDetailType){
		url = "applyFlowResolveForChangeController.do?changeResourceList&cellId="+cellId+"&parentPlanId="+parentPlanId+"";
	}
    else{
    	var planId = $("#planId").val();
    	url = 'resourceLinkInfoController.do?list&useObjectType=PLAN&useObjectId='+ planId;
    }
    
    $('#'+dataGrideId).datagrid(
	        {
	            url : url,
	            pageNumber : 1
	        });
});

function addResourceDialog(iframe){
	 iframe.submitSelectData();
}

function initResourceFlowTask() {
	$("#"+dataGrideId).datagrid("reload");
}

function modifyResourceDialog(iframe){
	iframe.submitEditResource();
}
