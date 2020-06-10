
  $(function() {
	  var tab = $('#tt').tabs('getSelected');
	  var index = $('#tt').tabs('getTabIndex',tab);
	  var dataGrideId = $("#dataGrideId_"+index).val();
    	var planId = $("#planId").val();
    	$('#'+dataGrideId).datagrid(
				{
					url : 'resourceLinkInfoController.do?list&useObjectType=PLAN&useObjectId='
							+ planId,
					pageNumber : 1
				});
	});

//资源名称链接事件
	function resourceNameLink(val, row, value) {
		return '<a href="#" onclick="viewResourceCharts(\'' + row.id + '\')" style="color:blue">' + val + '</a>';
	}

	// 资源名称链接事件
	function viewResourceCharts(id) {
		debugger
		var tab = $('#tt').tabs('getSelected');
		var index = $('#tt').tabs('getTabIndex',tab);
		var startTime = $("#startTime_"+index).val();
		var endTime = $("#endTime_"+index).val();
		var dataGrideId = $("#dataGrideId_"+index).val();
	    var rows = $('#'+dataGrideId).datagrid('getRows');
	    var index1 = $('#'+dataGrideId).datagrid('getRowIndex', id);
	 	var row = rows[index1];
	 	var planId= $("#planId").val();	 	
		if (row.useRate != null && row.useRate != '' && row.startTime != null && row.startTime != '' && row.endTime != null && row.endTime != '') {
			createDialog('resourceDialog',"resourceLinkInfoController.do?goToUsedRateReport&resourceId="
						+ row.resourceId + "&startTime="+startTime+"&endTime="+endTime+"");
		}
		else {
			alert('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyUseInfo"/>');
		}
	}

	// 使用百分比
	function viewUseRate2(val, row, value) {
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