//输出
var dataGrideId = "";
var idRow='';
window.TC003 = new Object();
var _this =  window.TC003;
var enterType;
var onlyReadonly;
var fromType;
var planBizCurrent;

!function () {   

    _this.getDeliverableName = function (val, row, value) {
        var fromDetailType = $('#fromDetailType').val();
    	    if('planResolve' == fromDetailType || 'planChange' == fromDetailType || 'mxgraphPlanChange' == fromDetailType){
    	    	return row.name;
    	    }
    	    else{
    	    	 return row.deliverableName;
    	    }
    }

    _this.getVersion = function(val, row, value){
        return row.version;
    }
    
    _this.getStatus = function(val, row, value){
        return row.status;
    }

    _this.checkLine = function() {
        debugger
        /*		var tab = $('#tt').tabs('getSelected');
                var index = $('#tt').tabs('getTabIndex',tab);
                var dataGrideId = $("#dataGrideId_"+index).val();*/
        var rows = $('#'+dataGrideId).datagrid('getSelections');
        if (rows == null || rows == '' || rows == 'undefined' || rows.length == 0){
            tip("请选择一记录");
            return false;
        }

        if(rows.length>1){
            tip("只能选择一条记录");
            return false;
        }
        var row=rows[0];
        idRow=row.id;
        var projectId = $("#projectId").val();
        var dialogUrl = 'projLibController.do?goProjLibLayout0&id='+projectId;
        $("#"+'taskDocCheckLineDialog').lhgdialog("open", "url:"+dialogUrl);
    }

    _this.uploadLine = function() {	
        debugger
        var rows = $('#'+dataGrideId).datagrid('getSelections');
        if (rows == null || rows == '' || rows == 'undefined' || rows.length == 0){
            tip("请选择一记录");
            return false;
        }
        if(rows.length>1){
            tip("只能选择一条记录");
            return false;
        }
        var row=rows[0];
        var projectId = $("#projectId").val();
        createaddwindow('projLibController.do?goAdd2&deliverableId=' + row.deliverableId + '&projectId='+projectId+'&opType=LINKDELIVER', row.deliverableId);
    }

    //打开PLM系统
    _this.addPLM = function(){
        var rows = $('#'+dataGrideId).datagrid('getSelections');
        if (rows == null || rows == '' || rows == 'undefined' || rows.length == 0){
            tip("请选择一记录");
            return false;
        }
        if(rows.length>1){
            tip("只能选择一条记录");
            return false;
        }
        var row=rows[0];
        var useObjectId = $('#useObjectId').val();
        var useObjectType = $('#useObjectType').val();
        $.ajax({
            type:'POST',
            url:'planController.do?checkPlm',
            cache:false,
            success:function(data){
                var d = $.parseJSON(data);
                if(d.success){
                    var dialogUrl = '/ids-pm-web/planController.do?goAddPlm&useObjectId='+useObjectId+'&useObjectType='+useObjectType;
                    createDialog('plmDialog', dialogUrl);
                }else{
                    top.tip("PLM系统异常，请联系管理员");
                }
            }
        });
    }

    _this.submitLine = function() {
        var rows = $('#'+dataGrideId).datagrid('getSelections');
        if (rows == null || rows == '' || rows == 'undefined' || rows.length == 0){
            tip("请选择一记录");
            return false;
        }
        if(rows.length>1){
            tip("只能选择一条记录");
            return false;
        }
        var row=rows[0];
        if(row.status!="拟制中"){
            tip("只能选择一条拟制中数据提交");
            return false;
        }
        $.ajax({
            url : 'projLibController.do?submitProcess',
            type : 'post',
            data : {
                id : row.docId,
                entityName : 'ProjectFile',
                businessType : 'fileType:'+row.fileTypeId,
                docName : row.docName
            },
            cache : false,
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    $('#submitDialogForDocCheckLine').lhgdialog(
                        "open",
                        "url:"
                        + 'commonFlowController.do?getDynamicForm&taskNumber='+ row.docId
                        + '&entityName=ProjectFile'
                        + '&businessType='+d.obj);
                } else {
                    top.tip(d.msg);
                }
            }
        });
    }


    _this.addFlagLink = function (val, row, value) {
        if (row.docName!=null&&row.docName!='') {
            if (row.orginType=='PLM'){
                var path = "<a  href='#' title='PLM详情' style='color:blue' onclick='TC003.plmShowPage(\""+ row.docId+ "\",\""+ row.fileType+ "\",\""+row.docName+"\")'>"
                    + row.docName
                    + "</a>"
                return path;
            } else {
                var userLevel = $("#userLevel").val();
                if ((row.havePower == true || row.havePower == 'true')&& userLevel >= row.securityLevel) {
                    return "<a href='#' onclick='TC003.showDocDetail(\""
                        + row.docId
                        + "\""
                        + ','
                        + "\""
                        + row.download
                        + "\""
                        + ','
                        + "\""
                        + row.detail
                        + "\")'  id='myDoc'  style='color:blue'>" + row.docName +"</a>";
                } else {
                    return row.docName;
                }
            }
        } else
            return ;
    }

    _this.plmShowPage = function (id, type,val) {
        if (type=="文档"){
            type = 'doc';
        } else if (type=="图档") {
            type = 'epm';
        }else {
            type = 'part';
        }
        var url = "/plm-web/IDSIntegratedPLMController/selectInfoBefore.do?type="+type+"&id="+id+ '&isIframe=true&afterIframe=true';
        top.addTabByIdOrRefresh(val, url, '', id);
    }

    _this.addLink = function (val, row, value) {
    	debugger
        if (val!=null&&val!='') {
            var userLevel = $("#userLevel").val();
            /*if ((row.havePower == true || row.havePower == 'true')&& userLevel >= row.securityLevel) {*/
            if (userLevel >= row.securityLevel) {
                return "<a href='#' onclick='TC003.showDocDetail(\""
                    + row.docId
                    + "\""
                    + ','
                    + "\""
                    + row.download
                    + "\""
                    + ','
                    + "\""
                    + row.detail
                    + "\")'  id='myDoc'  style='color:blue'>" + val +"</a>";
            } else {
                return val;
            }
        } else
            return ;
    }

/*    function showDocDetail(id,download,detail) {*/
   _this.showDocDetail = function (id,download,detail) {
        var url="projLibController.do?viewProjectDocDetail&opFlag=1&id="+id+ "&download=" + download+ "&detail=" + detail;
        createDialog('openDocumentInfoDialog',url);
    }

   
   _this.isShowColumn = function(){
	   debugger
       var fromDetailType =  $("#fromDetailType").val();
       if(fromType=='4028f00d6db34426016db365b27c0000'){
           if (planBizCurrent == "ORDERED"&&enterType=='2') {
               setTimeout(function(){
                   $('#'+dataGrideId).datagrid('hideColumn', 'docName');
                   $('#'+dataGrideId).datagrid('hideColumn', 'orginType');
                   $('#'+dataGrideId).datagrid('hideColumn', 'required');
               }, 10);
           }else {
               setTimeout(function(){
                   $('#'+dataGrideId).datagrid('hideColumn', 'docName');
                   $('#'+dataGrideId).datagrid('hideColumn', 'deleteFlag');
                   $('#'+dataGrideId).datagrid('hideColumn', 'ext2');
                   $('#'+dataGrideId).datagrid('hideColumn', 'ext3');
                   $('#'+dataGrideId).datagrid('hideColumn', 'orginType');
                   $('#'+dataGrideId).datagrid('hideColumn', 'required');
               }, 10);
           }
       }else{
           if(enterType=='2'){
               setTimeout(function(){
                   $('#'+dataGrideId).datagrid('hideColumn', 'deleteFlag');
                   $('#'+dataGrideId).datagrid('hideColumn', 'orginType');
                   $('#'+dataGrideId).datagrid('hideColumn', 'required');
               }, 10);
           }else if(fromDetailType=="planResolve" || 'mxgraphPlanChange' == fromDetailType){
               setTimeout(function(){
                   $('#'+dataGrideId).datagrid('hideColumn', 'deleteFlag');
                   $('#'+dataGrideId).datagrid('hideColumn', 'ext2');
                   $('#'+dataGrideId).datagrid('hideColumn', 'ext3');
               }, 10);
           }else{
        	   setTimeout(function(){
                   $('#'+dataGrideId).datagrid('hideColumn', 'deleteFlag');
                   $('#'+dataGrideId).datagrid('hideColumn', 'ext2');
                   $('#'+dataGrideId).datagrid('hideColumn', 'ext3');
                   $('#'+dataGrideId).datagrid('hideColumn', 'orginType');
                   $('#'+dataGrideId).datagrid('hideColumn', 'required');
               }, 10);
           }
       }
   }
   
   _this.isEditor = function(){
       if(fromType=='4028f00d6db34426016db365b27c0000') {
           if (planBizCurrent == "ORDERED"&&enterType=='2') {
               $("#TC003PLM系统关联Div").show();
           }else {
               $("#TC003PLM系统关联Div").hide();
           }
       }else{
           $("#TC003PLM系统关联Div").hide();
       }
       if(enterType=='2' || onlyReadonly=='1'){
           $("#TC003新增Div").hide();
           $("#TC003删除Div").hide();
           $("#TC003继承父项Div").hide();
       }else{
           $("#TC003新增Div").show();
           $("#TC003删除Div").show();
           $("#TC003继承父项Div").show();
       }
   }
   
   _this.isShowButton = function(){
        planBizCurrent = $("#planBizCurrent").val();
		if(enterType=='2'　&& $("#isOwner").val() =='true' &&planBizCurrent == "ORDERED"){
			$("#TC003提交Div").show();
			$("#TC003本地上传挂接Div").show();
			$("#TC003从项目库查找挂接Div").show();
		}else{
			$("#TC003提交Div").hide();
			$("#TC003本地上传挂接Div").hide();
			$("#TC003从项目库查找挂接Div").hide();
		}
	   }
   
// 继承父项
   _this.inheritParent = function() {
	   if ("1" == $("#isEnableFlag").val()) {
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
			return;
		}
		if ($("#parentPlanId").val() != null
				&& $("#parentPlanId").val() != undefined) {
			var dialogUrl = 'taskFlowResolveController.do?goAddInherit&cellId='
					+ $("#cellId").val()
					+ '&parentPlanId='
					+ $("#parentPlanId").val();

            var fromDetailType = $('#fromDetailType').val();
	 	    if('mxgraphPlanChange' == fromDetailType){
	 	    	dialogUrl = 'applyFlowResolveForChangeController.do?goAddInheritForChange&cellId='+$("#cellId").val()+'&parentPlanId='+$("#parentPlanId").val();
	 	    }

			createDialog('inheritDialog', dialogUrl);
		} else {
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.deliveryNoParent"/>');
		}
   }

//新增交付项
   _this.addDeliverable = function () {
	   var gridname = 'DeliverablesInfo';
	   
	   if("1" == $("#isEnableFlag").val()){
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
			return;
		}
		var dialogUrl = 'taskFlowResolveController.do?goAdd&type=OUTPUT&cellId='+$("#cellId").val()+'&parentPlanId='+$("#parentPlanId").val();

        var fromDetailType = $('#fromDetailType').val();
 	    if('mxgraphPlanChange' == fromDetailType){
 	    	dialogUrl = 'taskFlowResolveController.do?goChangeAdd&type=OUTPUT&cellId='+$("#cellId").val()+'&parentPlanId='+$("#parentPlanId").val();
 	    }
 	    
		 $("#addOutDialog").lhgdialog("open",'url:' + dialogUrl+"");
   }

//删除交付项
   _this.deleteSelections = function(gridname, url) {
	   var gridname = 'DeliverablesInfo';
       var rows = $("#"+gridname).datagrid('getSelections');
       for(var i =0;i<rows.length;i++){
           if(rows[i].origin != ''&&rows[i].origin !=null&&rows[i].origin !=undefined){
               top.tip('"' + rows[i].name + '"为标准交付项，不能删除，请重新选择');
               return false;
           }
       }

       var ids = [];
       if (rows.length > 0) {
           top.Alert.confirm('确定删除该记录？',
               function(r) {
                   if (r) {
                       for ( var i = 0; i < rows.length; i++) {
                           ids.push(rows[i].id);
                       }

                       var fromDetailType = $('#fromDetailType').val();
	           	 	    if('mxgraphPlanChange' == fromDetailType){
		           			$.ajax({
		           				url : 'applyFlowResolveForChangeController.do?doDelChangeOutput',
		           				type : 'post',
		           				data : {
		           					'id' : ids.join(','),
		           					'cellId' :$('#cellId').val(),
		           					'parentPlanId' : $('#parentPlanId').val()
		           				},
		           				cache : false,
		           				success : function(data) {
		           					debugger
		           					var d = $.parseJSON(data);
		           					if (d.success) {
		           					 $("#"+gridname).datagrid('clearSelections');		
		           					 $("#"+gridname).datagrid("reload");
		           					}
		           					else{
		           						var msg = d.msg.split('<br/>')
		           						top.tip(msg[0]); 
		           					}
		           				}
		           			});
	           	 	   }else{
	           	 		   $.ajax({
	                           url : url,
	                           type : 'post',
	                           data : {
	                               ids : ids.join(',')
	                           },
	                           cache : false,
	                           success : function(data) {
	                               for(var i=rows.length-1;i>-1;i--){
	                                   var a = $("#"+gridname).datagrid('getRowIndex',rows[i]);
	                                   $("#"+gridname).datagrid('deleteRow',$("#"+gridname).datagrid('getRowIndex',rows[i]));

	                               }
	                               $("#"+gridname).datagrid('clearSelections');
	                               _this.loadData();                             
	                           }
	                       });
	           	 	   }
                   }
               });
       } else {
           top.tip('请选择需要删除的记录');
       }
   }
   
   _this.loadData = function(this_){
       debugger;
       _this.isShowColumn();
       _this.isEditor();
       _this.isShowButton();
       var datas = new Object();
       var flg=false;
       if(this_ != null && this_ != '' && this_ != undefined){
           datas=this_;
       }else{
           //datas={useObjectId : $('#useObjectId').val(),useObjectType : $('#useObjectType').val()}
           datas.useObjectId = $('#planId').val();
           datas.useObjectType = "PLAN";
       }
       //alert($.toJSON(datas))
       $.ajax({
           type : 'POST',
           url : 'deliverablesInfoController.do?list',
           async : false,
           data : datas,
           success : function(result) {
               try {
                   $("#eliverablesInfo").datagrid("loadData",result['rows']);
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
   
  //必要：
  _this.isNecessary = function (val, row, index) {
	 debugger;
	 if (row.required == "true")
			return "是";
		else
			return "否";
	}	
	 //去向：
    _this.inputOrOutputName = function(val, row, value){    
         return row.result;
    }
   
}();

function initOutputsChangeFlow() {
	$("#DeliverablesInfo").datagrid("reload");
}

function initOutputsFlowTask() {
	$("#DeliverablesInfo").datagrid("reload");
}

function addOutDialog(iframe){
	iframe.submitSelectData();
}



function addInheritDialog(iframe){
	iframe.submitSelectData();
}


function createaddwindow(url,id) {
    idRow=id;
    $("#"+'projLibSubimtDialog').lhgdialog("open","url:"+url);
}

//输出添加PLM系统
function addPlmDialog(iframe) {
    if (iframe.outVaild()) {
        var selectObjects = iframe.getSelectObjects();
        //主键id
        var plmId = selectObjects[0].id;
        //名称
        var name = selectObjects[0].name;
        //类型
        var typeName = selectObjects[0].typename;
        //版本
        var bizVersion = selectObjects[0].bizVersion.substr(0,1);
        //状态
        var status = selectObjects[0].status;
        var rows=$('#'+dataGrideId).datagrid('getSelections');
        var id=rows[0].deliverableId;
        $.ajax({
            url : 'deliverablesInfoController.do?updateByPlm',
            type : 'post',
            data : {
                id : id,
                docId : plmId,
                docName:name,
                fileType:typeName,
                versionCode:bizVersion,
                statusCode:status,
                orginType:'PLM'
            },
            cache : false,
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    var msg = d.msg;
                    tip(msg);
                    refreshDatagrid();
                    refreshSubmitBtn();
                }
            }
        });
        return true;
    } else {
        return false;
    }
}


function taskDocCheckLineDialog(iframe) {
    //iframe = this.iframe.contentWindow;
    if (iframe.validateSelectedNum()) {
        var docId = iframe.getSelectionsId();
        var rows=$('#'+dataGrideId).datagrid('getSelections');
        var row=rows[0];
        $.ajax({
            url : 'deliverablesInfoController.do?update',
            type : 'post',
            data : {
                id : row.deliverableId,
                fileId : docId
            },
            cache : false,
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    var msg = d.msg;
                    tip(msg);
                    refreshDatagrid();
                    refreshSubmitBtn();
                }
            }
        });
        return true;
    } else {
        return false;
    }
}

function projLibSubimt(iframe) {
    var result = false;
    var result = iframe.saveFileBasicAndUpload();
    if (result) {
        refreshSubmitBtn();
    }
    return result;
}


function uploadFile(docId) {	
    $.ajax({
        url : 'deliverablesInfoController.do?update',
        type : 'post',
        data : {
            id: idRow,
            fileId : docId
        },
        cache : false,
        success : function(data) {
            var d = $.parseJSON(data);
            debugger;
            if (d.success) {
                var msg = d.msg;
                /*if(msg=='交付项文档挂接成功'){
                     $('#planResolve').attr("style","display:none");
                     $('#flowResolve').attr("style","display:none");
                }*/
                refreshDatagrid();
            }
        }
    });
}

function beOk(iframe) {
    var inputs = iframe.document.getElementsByTagName("input");
    if(inputs.length>0){
        for(var i=0;i<inputs.length;i++){
            var curName = inputs[i].name;
            if(inputs[i].name.indexOf("leader_")>=0 && curName.split("_").length==2) {
                var leader = iframe.$('input[name='+inputs[i].name+']').val();
                if(leader == null || leader == ''){
                    top.tip("所有选择不能为空");
                    return false;
                }
            }
        }

    }
    var options = {
        submitCallback : function() {
            refreshDatagrid();
            setTimeout(function() {
                $.fn.lhgdialog("closeAll")
            }, 100);
        }
    }
    setTimeout(function() {
        iframe.submitForm(options);
    }, 100)
    return false;

}

function beCancel(iframe) {
    refreshDatagrid();
    setTimeout(function() {
        $.fn.lhgdialog("closeAll")
    }, 100)
}

function beClose() {
    refreshDatagrid()
    setTimeout(function() {
        $.fn.lhgdialog("closeAll")
    }, 100)
    return true;
}

function refreshDatagrid() {
    var planId = $("#planId").val();
    $('#'+dataGrideId).datagrid(
        {
            url : 'taskDetailController.do?getDocRelationList&id='
                + planId,
            pageNumber : 1
        });
}

function refreshSubmitBtn() {
    var planId = $("#planId").val();
    $.ajax({
        url: '/ids-pm-web/planController.do?isButtonShow',
        data: {
            planId : planId
        },
        type: "GET",
        success: function(data) {
            var d = $.parseJSON(data);
            debugger;
            if (d.success) {
                $('#parentSubmit').attr("style", "display:");
            } else {
                $('#parentSubmit').attr("style", "display:none");
            }
        }
    });
}

$(function() {
	onlyReadonly = $("#onlyReadonly").val();
	enterType = $("#enterType").val();
    fromType = $('#fromType').val();
    planBizCurrent = $("#planBizCurrent").val();
    var tab = $('#tt').tabs('getSelected');
    var index = $('#tt').tabs('getTabIndex',tab);
    var taskNumber = $("#taskNumber").val();
    dataGrideId = "DeliverablesInfo";
    var planId = $("#planId").val();
    _this.isShowColumn();
    _this.isEditor();
    _this.isShowButton();
    var url = "";
    var fromDetailType = $('#fromDetailType').val();
    var cellId = $("#cellId").val();
    var parentPlanId = $("#parentPlanId").val();
    debugger
    if('planResolve' == fromDetailType){
    	url = "taskFlowResolveController.do?outputList&cellId="+cellId+"&parentPlanId="+parentPlanId+""
    }else if('planChange' == fromDetailType){
        url = 'planChangeController.do?documentListViewForPlanChange&formId=' + taskNumber;
    }else if('mxgraphPlanChange' == fromDetailType){
    	url = "applyFlowResolveForChangeController.do?changeOutputList&cellId="+cellId+"&parentPlanId="+parentPlanId+""
    }
    
    else{
    	url = 'taskDetailController.do?getDocRelationList&id=' + planId;
    }

    $('#'+dataGrideId).datagrid(
        {
            url : url,
            pageNumber : 1
        });

});