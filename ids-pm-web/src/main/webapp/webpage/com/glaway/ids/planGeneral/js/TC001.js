//基本信息
window.TC001 = new Object();
var _this =  window.TC001;
!function(){
_this.taskDescription = function (val, row, value) {
    var str='<a href="javascript:void(0)" style="color:black" class="tips_resourceList"  title="'+row.remark+'" >' + row.logInfo + '</a>'
    return str ;
}

_this.downLoadFile = function (val, row, value) {
	if(row.filePath!=""){
        return '<a href="planLogController.do?downFile&id='+ row.id +'">' +
            '<span class="basis ui-icon-attachment" title="下载">&nbsp;&nbsp;&nbsp;&nbsp;</span>' +
            '</a>';
    }
}

_this.taskDescription = function (val, row, value) {
    var str='<a href="javascript:void(0)" style="color:black" class="tips_resourceList"  title="'+row.remark+'" >' + row.logInfo + '</a>'
    return str ;
}

_this.projectName = function (){
	debugger
	if(showProjectInfo=='true'){
		$("#projectNameDiv").show();
	}else{
		$('#projectNameDiv').hide();
	}
}

_this.projectManagerNames = function (){
	if(showProjectInfo=='true'){
		$("#projectManagerNamesDiv").show();
	}else{
		$("#projectManagerNamesDiv").hide();	
	}
}

_this.startProjectTime = function (){
	if(showProjectInfo=='true'){
		$("#startProjectTimeDiv").show();
	}else{
		$("#startProjectTimeDiv").hide();	
	}
}

_this.endProjectTime = function (){
	if(showProjectInfo=='true'){
		$("#endProjectTimeDiv").show();
	}else{
		$("#endProjectTimeDiv").hide();	
	}
}

_this.eps = function (){
	if(showProjectInfo=='true'){
		$("#epsDiv").show();
	}else{
		$("#epsDiv").hide();	
	}
}

_this.process = function (){
	if(showProjectInfo=='true'){
		$("#processDiv").show();
	}else{
		$("#processDiv").hide();	
	}
}

_this.assigner = function (){
	var assigner;

	try{
		assigner = $("#assigner").val();
	}catch (e) {

	}

	if(showProjectInfo=='true' || (assigner != "" && assigner != null && assigner != undefined && assigner != 'undefined') ){
		$("#assignerDiv").show();
	}else{
		$("#assignerDiv").hide();	
	}
}

_this.assignTime = function (){
	var assignTime;
	try{
		assignTime = $("#assignTime").val();
	}catch (e) {

	}
	if(showProjectInfo=='true'  || (assignTime != "" && assignTime != null && assignTime != undefined && assignTime != 'undefined') ){
		$("#assignTimeDiv").show();
	}else{
		$("#assignTimeDiv").hide();	
	}
}

_this.progressRate = function (){
	if(showProjectInfo=='true'){
		$("#progressRateDiv").show();
	}else{
		$("#progressRateDiv").hide();	
	}
}

_this.preposeIds = function (){
		$("#preposeIdsDiv").show();
}

_this.parentPlanId = function (){
	if(showProjectInfo=='true'){
		$("#parentPlanIdDiv").show();
	}else{
		$("#parentPlanIdDiv").hide();	
	}
}

/**
 * 选择前置计划页面
 */
_this.selectPreposePlan = function() {
    var url = 'planController.do?goPlanPreposeTree';
    if ($('#projectId').val() != "") {
        url = url + '&projectId=' + $('#projectId').val();
    }
    if ($('#parentPlanId').val() != "") {
        url = url + '&parentPlanId=' + $('#parentPlanId').val();
    }
    if ($('#id').val() != "") {
        url = url + '&id=' + $('#id').val();
    }
    if($('#preposeIds').val() != "") {
        url = url + '&preposeIds=' + $('#preposeIds').val();
    }
    //createPopupwindow('项目计划', url, '', '', hiddenId, textId);
    createDialog('preposePlanDialog',url);
}

	_this.ownerChange = function(){
		debugger;
		var owner = $('#Plan-owner').combobox('getValue');
		$.ajax({
			url : "taskFlowResolveController.do?setDeptNameByOwnerId",
			type : 'post',
			data : {
				'ownerId' : owner
			},
			cache : false,
			success : function(data2) {
				var d2 = $.parseJSON(data2);
				if(d2.success == true){
					$('#Plan-ownerDept').textbox("setValue",d2.obj);
				}
			}
		});
	}
}();

var planBizCurrent;
var showProjectInfo;
//初始化：
$(function() {
	$('#saveBaseInfoButton').attr("display","");
	planBizCurrent = $("#planBizCurrent").val();
	showProjectInfo = $("#showProjectInfo").val();
	_this.projectName();
	_this.projectManagerNames();
	_this.startProjectTime();
	_this.endProjectTime();
	_this.eps();
	_this.process();
	_this.assigner();
	_this.assignTime();
	_this.progressRate();
	_this.preposeIds();
	_this.parentPlanId();
	debugger;
	$("#"+"Plan-owner").combobox({ onChange:function(newVal, oldVal){
			eval($("#operationEventForPlanOwner").val());
		}});
	var planId = $("#planId").val();
    var fromDetailType = $("#fromDetailType").val();
    if('planResolve' == fromDetailType || 'mxgraphPlanChange' == fromDetailType){
    	$("#milestoneDiv").hide();
    	$("#bizCurrentDiv").hide();
    	$("#createFullNameDiv").hide();
    	$("#createTimeDiv").hide();
    	$("#taskTypeDiv").hide();    	
    	$("#Plan-workTime").attr("readonly", "true");
    	$("#Plan-planStartTime").attr("readonly", "true");
    	$("#Plan-planEndTime").attr("readonly", "true");
    	if($("#isEnableFlag").val() != "1"){
    		$("#saveBaseInfoButton").show();
    	}
    }
    try {
    	$('#taskDetailBasicOperation').datagrid(
	        {
	            url : 'planLogController.do?list&planId='
	                + planId,
	            pageNumber : 1
	        });
	} catch (e) {
	}   
});

function preposePlanDialog(iframe){
    debugger;
    var preposeIds;
    var preposePlans;
    var preposeEndTime;
    var selectedId = iframe.mygrid_planPreposeList.getSelectedRowId();
    if(selectedId != undefined && selectedId != null && selectedId != ''){
        var selectedIdArr = selectedId.split(",");
        if(selectedIdArr.length > 0){
            for(var i=0;i<selectedIdArr.length;i++){
                var id = selectedIdArr[i];
                if(preposeIds != null && preposeIds != '' && preposeIds != undefined){
                    preposeIds = preposeIds + ',' + id;
                }else{
                    preposeIds = id;
                }
                var planName = iframe.mygrid_planPreposeList.getRowAttribute(id,'displayName');
                if(preposePlans != null && preposePlans != '' && preposePlans != undefined){
                    preposePlans = preposePlans + ',' + planName;
                }else{
                    preposePlans = planName;
                }
                var planEndTime = iframe.mygrid_planPreposeList.getRowAttribute(id,'planEndTime');
                if(preposeEndTime != null && preposeEndTime != '' && preposeEndTime != undefined){
                    if(preposeEndTime < planEndTime){
                        preposeEndTime = planEndTime;
                    }
                }else{
                    preposeEndTime = planEndTime;
                }
            }
        }
    }
    savePreposePlan(preposeIds, preposePlans, preposeEndTime);
}


/**
 * 前置计划选择后，更新页面值（每次均覆盖）
 */
function savePreposePlan(preposeIds, preposePlans, preposeEndTime) {
    debugger;
    var projectId = $('#projectId').val();
    if (preposeIds != null && preposeIds != ''
        && preposeIds != undefined) {
        $('#preposeIds').val(preposeIds);
    } else {
        $('#preposeIds').val('');
    }
    if (preposePlans != null && preposePlans != ''
        && preposePlans != undefined) {
        $('#Plan-preposeIds').textbox("setValue",preposePlans);
    } else {
        $('#Plan-preposeIds').textbox("setValue","");
    }
    // 前置计划的最晚完成时间
    if (preposeEndTime != null && preposeEndTime != ''
        && preposeEndTime != undefined) {
        $('#preposeEndTime').val(preposeEndTime);

        $.ajax({
            url : 'planController.do?getNextDay',
            type : 'post',
            data : {
                preposeEndTime : preposeEndTime,
                projectId :projectId
            },
            cache : false,
            success : function(data) {
                if (data != null) {
                    var d = $.parseJSON(data);
                    if(d.success == true){
                        preposeEndTime = d.obj;
                        $('#planStartTime').datebox("setValue",preposeEndTime);
                    }
                    else{
                    }
                }
                else{
                }
            }
        });


    } else {
        $('#preposeEndTime').val('');
        $('#planStartTime').datebox("setValue",$("#planStartTimeRm").val());
    }
}

function saveBaseInfo(){
	debugger;
	if("1" == $("#isEnableFlag").val()){
		top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
		return;
	}
	var id = $('#id').val();
	var cellId = $('#cellId').val();
	var parentPlanId = $('#parentPlanId').val();
	var isStandard = $('#isStandard').val();
	var planName = $('#Plan-planName').val();
	
	var owner = $('#Plan-owner').combobox('getValue'); 
	var workTime = $('#Plan-workTime').val(); 
	var planLevel = $('#Plan-planLevel').combobox('getValue');
	
	var planLevelList = eval($('#planLevelList').val());
	for (var i = 0; i < planLevelList.length; i++) {
		if (planLevel == planLevelList[i].name) {
			planLevel = planLevelList[i].id;
		}
	}
	var remark = $('#Plan-remark').val();
	var preposeIds = $('#preposeIds').val();
	var allPreposeIds = $('#allPreposeIds').val();

	if (owner == "") {
		top.tip('负责人不能为空');
		return false;
	}
	
	var userList = eval($('#userList2').val());
	for (var i = 0; i < userList.length; i++) {
		if (owner == userList[i].realName) {
			owner = userList[i].id;
		}
	}

	var fromDetailType = $('#fromDetailType').val(); 
	var url = 'taskFlowResolveController.do?saveFlowTask';
	if(fromDetailType=="mxgraphPlanChange"){
		//变更保存：
		url = 'applyFlowResolveForChangeController.do?saveFlowTaskForChange';
	}
	
	top.jeasyui.util.commonMask('open','请稍候...');
	$.ajax({
		url : url,
		type : 'post',
		data : {
			id : id,
			cellId : cellId,
			parentPlanId : parentPlanId,
			planName : planName,
			owner : owner,
			workTime : workTime,
			planLevel : planLevel,
			remark : remark,
			preposeIds:preposeIds,
			allPreposeIds: allPreposeIds
		},
		cache : false,
		success : function(data) {
			debugger;
			var d = $.parseJSON(data);
			top.jeasyui.util.commonMask('close');
			if(d.success == true){
				window.parent.saveSuccess(cellId, planName, workTime);
				//如果选中为必填，需要改变字体颜色
				window.parent.saveGraph();
				baseInfoId = d.obj;
				if (baseInfoId != undefined && baseInfoId != '') {
					$("#id").val(baseInfoId);
					var index0Tab = $('#tt').tabs('getSelected');
					var change = "0";
//					if(planName != '${plan_.planName}'){
//						change = "1";
//					}else{
//						change = "0";
//					}
//					try {
//						$('#tt').tabs('close', 5);
//					} catch (e) {
//						// TODO: handle exception
//					}
//					try {
//						$('#tt').tabs('close', 4);
//					} catch (e) {
//						// TODO: handle exception
//					}
//					try {
//						$('#tt').tabs('close', 3);
//					} catch (e) {
//						// TODO: handle exception
//					}
//					try {
//						$('#tt').tabs('close', 2);
//					} catch (e) {
//						// TODO: handle exception
//					}
//					try {
//						$('#tt').tabs('close', 1);
//					} catch (e) {
//						// TODO: handle exception
//					}
//					index0Tab.panel('refresh', 'taskFlowResolveController.do?goTab&tabIndex=0&cellId='+cellId+'&parentPlanId='+parentPlanId+'&isSave=true&change='+change);
				}
			}
			else{
				var msg = d.msg.split('<br/>')
				top.tip(msg[0]); // John
			}
		}
	});
}
