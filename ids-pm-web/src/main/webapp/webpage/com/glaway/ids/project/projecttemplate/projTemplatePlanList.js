function getDeliverables(infoObj){
	var value = infoObj.dataValue;
	var node = infoObj.node;
	var tree = infoObj.tabletreeObj;
	if(value<=0){
		return;
	}
	var chooseStr = '<A class="easyui-linkbutton l-btn l-btn-plain" onclick="deliverableListDetailShow(\''+node.id+'\')" style="text-align:center"  data-options="disabled:false ,iconCls:\'icon-tip\'" plain="true">'+
	'<SPAN class=l-btn-left>'+
	'<SPAN class="l-btn-text icon-tip l-btn-icon-left"></SPAN>'+
	'</SPAN></A>';
	
	return chooseStr;
}

function deliverableListDetailShow(param){
	$.fn.lhgdialog({
		content: 'url:planTemplateDetailController.do?goDeliverables&planTemplateDetailId='+param,
		lock : true,
		width:600,
		height:400,
		zIndex : 9999,
		title:'交付项 列表信息',
		opacity : 0.3,
		cache:false,
	    ok: function(){
	    	iframe = this.iframe.contentWindow;
			saveObj();
			return false;
	    },
	    cancelVal: '取消',
	    cancel: true /*为true等价于function(){}*/
	});
}

$(function(){
	if($("#taskId").val()==''){
		$("#submitField").css("display","none");
	}
});

function backSubmit(){
	var planTemplateId = $('#planTemplateId').val();
	var taskId = $('#taskId').val();
	var taskNumber = $('#taskTest').val();
	$.ajax({
		type : 'POST',
		url : 'planTemplateController.do?doBackSubmit&planTemplateId='+planTemplateId,
		data :{
			taskId : taskId,
			taskNumber : taskNumber
		},
		success : function(data) {
//			W.getData();
			try{
            	$.fn.lhgdialog("getSelectParentWin").parent.flashTaskShow();
            }catch(e){
            	
            }
            
            try{
            	$.fn.lhgdialog("getSelectParentWin").parent.$("#startTaskListMore_wait").datagrid('reload');
            }catch(e){
            	
            }
			$.fn.lhgdialog("closeSelect");
		} 
	});
}

function addDay(val, row, value){
	if(val.dataValue!=""){
		return val.dataValue+"天";
	}
}
