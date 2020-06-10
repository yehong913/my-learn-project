// 负责人变更联动
function ownerChange(){
	debugger;
	var departList = eval($('#departList').val());
	var owner = $('#owner').combobox('getValue');
	for(var i=0;i<departList.length;i++){
		if(owner == ''){
			$('#ownerDept').textbox("setValue","");
		}else if( owner == departList[i].userId){
			$("#ownerDept").textbox("setValue",departList[i].departname);
		}
	}
}

var inputType1 = undefined;
var inputType2 = undefined;

// 保存计划
function addPlan() {
	debugger;
	if(checkPlanWithInput()){
		var id = $('#id').val();
		var planNumber = $('#planNumber').val();
		var projectId = $('#projectId').val();
		var parentPlanId = $('#parentPlanId').val();
		var beforePlanId = $('#beforePlanId').val();
		var bizCurrent = $('#bizCurrent').val();
		var flowStatus = $('#flowStatus').val();
		var isStandard = $('#isStandard').val();
		var planName = $('#planName').val();
		var taskNameId='';
		if(planName == undefined){
			planName = $('#planName2').combobox('getText');
			taskNameId=$('#planName2').combobox('getValue');
		}
		
		var remark = $('#remark').val();
		var owner = $('#owner').combobox('getValue');
		var userList = eval($('#userList2').val());
		for (var i = 0; i < userList.length; i++) {
			var a = owner.indexOf(userList[i].realName);
			if (a == 0) {
				owner = userList[i].id;
			}
		}
		
		var planLevel = $('#planLevel').combobox('getValue');
		var planLevelList = eval($('#planLevelList').val());
		for (var i = 0; i < planLevelList.length; i++) {
			if (planLevel == planLevelList[i].name) {
				planLevel = planLevelList[i].id;
			}
		}
		var planStartTime = $('#planStartTime').datebox('getValue');
		var workTime = $('#workTime').val();
		var planEndTime = $('#planEndTime').datebox('getValue');
		var milestone = $('#milestone').combobox('getValue');
		var milestoneRm;
		var preposeIds = $('#preposeIds').val();
		if(milestone == "否"){
			milestoneRm = "false";
		}else if(milestone == "false"){
			milestoneRm = "false";
		}else if(milestone == "true"){
			milestoneRm = "true";
		}
		
		var taskNameType = $('#taskNameType').combobox('getValue');
		var taskType = $('#taskType').combobox('getValue');
		
		var id = $('#id').val();
		var planStartTime = $('#planStartTime').datebox('getValue');
		var planEndTime = $('#planEndTime').datebox('getValue');
		
		$.ajax({
			url : 'resourceLinkInfoController.do?pdResource',
			type : 'post',
			data : {
				id : id,
				planStartTime : planStartTime,
				planEndTime : planEndTime
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				var msg = d.msg;
				if(d.success){
					$.post('planController.do?doSave', {
						'id' : id,
						'planNumber' : planNumber,
						'projectId' : projectId,
						'parentPlanId' : parentPlanId,
						'beforePlanId' : beforePlanId,
						'planStartTime' : planStartTime,
						'planEndTime' : planEndTime,
						'bizCurrent' : bizCurrent,
						'remark' : remark,
						'planName' : planName,
						'owner' : owner, 
						'flowStatus' : flowStatus, 
						'planLevel' : planLevel,
						'workTime' : workTime,
						'milestone'	: milestoneRm,
						'preposeIds' : preposeIds,
						'taskNameId':taskNameId,
						'taskNameType' : taskNameType,
						'taskType' : taskType
					}, function(data) {
					    debugger;
						var win = $.fn.lhgdialog("getSelectParentWin") ;
						var planListSearch = win.planListSearch();
						$.fn.lhgdialog("closeSelect");
					});
				}else{
					top.tip(msg);
				}
			}
		});
		
	}
}

//var inputsListData;
/*function initInputs(this_) {
	debugger;
	var datas="";
	var flg=false;
	if(this_ != null && this_ != '' && this_ != undefined){
		datas=this_;
	}else{
		datas={useObjectId : $('#useObjectId').val(),useObjectType : $('#useObjectType').val(),preposeIds : $('#preposeIds').val()}
	}
	$.ajax({
		type : 'POST',
		url : 'inputsController.do?list',
		async : false,
		data : datas,
		success : function(data) {
			try {
				$("#inputsList").datagrid("loadData",data);
			}
			catch(e) {
				//alert('3e:'+e)
			}
			finally {
				flg= true;
			}
		} 
	});
	return flg;
}*/

function initInputs(this_) {
	debugger;
	var datas="";
	var flg=false;
	if(this_ != null && this_ != '' && this_ != undefined){
		datas=this_;
	}else{
		datas={useObjectId : $('#useObjectId').val(),useObjectType : $('#useObjectType').val(),projectId:$('#projectId').val()}
	}
	$.ajax({
		type : 'POST',
		url : 'inputsController.do?list',
		async : false, 
		data : datas,
		success : function(data) {
			try {
				if(data == null){
					data = new Array();
				}
				/*var newDataObj = new Object();
				var newDataArr = new Array();
				var newData = null;
				if(data!=null && data['rows']!=null && data['rows'].length>0) {
					for(var i=0; i<data['rows'].length; i++) {
						newData = data['rows'][i];
						newData['id'] = newData['tempId'];
						newDataArr.push(newData);
					}
					newDataObj['rows'] = newDataArr;
					newDataObj['total'] = data['total'];
				}*/
				$("#inputsList").datagrid("loadData",data);
			}
			catch(e) {
				//alert('3e:'+e)
			}
			finally {
				flg= true;
			}
		} 
	});
	return flg;
}

var deliverablesInfoListData;
function initDocument(this_) {
	debugger;
	var datas = new Object();
	var flg=false;
	if(this_ != null && this_ != '' && this_ != undefined){
		datas=this_;
	}else{
		//datas={useObjectId : $('#useObjectId').val(),useObjectType : $('#useObjectType').val()}
		datas.useObjectId = $('#useObjectId').val();
		datas.useObjectType = $('#useObjectType').val();
	}
	//alert($.toJSON(datas))
	$.ajax({
		type : 'POST',
		url : 'deliverablesInfoController.do?list',
		async : false,
		data : datas,
		success : function(result) {
			try {
				$("#deliverablesInfoList").datagrid("loadData",result['rows']);
			}
			catch(e) {
				//alert('3e:'+e)
			}
			finally {
				flg= true;
			}
		} 
	});
	return flg;
}


function initResource(this_) {
	var datas="";
	var flg=false;
	if(this_ != null && this_ != '' && this_ != undefined){
		datas=this_;
	}else{
		datas={useObjectId : $('#useObjectId').val(),useObjectType : $('#useObjectType').val()}
	}
	$.ajax({
		type : 'POST',
		url : 'resourceLinkInfoController.do?list',
		async : false,
		data : datas,
		success : function(data) {
			try {
				$("#resourceList").datagrid("loadData",data);
				$('#resourceList').datagrid('unselectAll');
			}
			catch(e) {
				//alert('3e:'+e)
			}
			finally {
				flg= true;
			}
		} 
	});
	return flg;
}

function closePlan() {
	$.ajax({
		type : 'POST',
		data : {useObjectId : $('#useObjectId').val()},
		url : 'inputsController.do?clearRedis',
		cache : false,
		success:function(data){
			
		}
	});
	$.fn.lhgdialog("closeSelect");
}

// 计划名称链接事件
function viewPlanInfo(val, row, index) {
	return '<a href="#" onclick="viewPlan(\'' + row.id
			+ '\')" style="color:blue">' + val + '</a>';
}

// 计划等级
function formatterByBusinessConfig(val, row, index) {
	if (val != undefined && val != null && val != '') {
		if (val.name != null && val.name != '') {
			return val.name;
		} else {
			return val.id;
		}
	}
	return val;
}



// 人员id、realName转换
function viewUserRealName(val, row, index) {
	if (val != undefined && val != null && val != '') {
		var realName = val.realName;
		if (realName != undefined && realName != null && realName != '') {
			return realName + "-" + val.userName;
		} else {
			return val.id;
		}
	}
}

function workTimeFormatter(val, row, index) {
	if (val != undefined && val != null && val != '') {
		return val + "天";
	}
	return "";
}

function riskFormatter(val, row, index) {
	if (val != undefined && val != null && val != '') {
		if(val == 'high'){
			return "高";
		}
		else if(val == 'middle'){
			return "中";
		}
		else if(val == 'low'){
			return "低";
		}
		else{
			return "无";
		}
	}
	return "无";
}

function milestonematter(val, row, index) {
	if (val != undefined && val != null && val != '') {
		if(val == 'false'){
			return "否";
		}else{
			return "是";
		}
	}
	return "";
}

// 使用百分比
function viewUseRate(val, row, value) {
	return progressRateGr
}