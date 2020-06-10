<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<div id="document" class="easyui-panel" fit="true">
	 <input id="documentStatusList" name="documentStatusList" type="hidden" value="${documentStatusList}" />
	 <fd:toolbar id="riskTagListtb">
		<fd:toolbarGroup align="left">
			<fd:linkbutton value="{com.glaway.ids.pm.project.task.taskdetail.editRiskList}" iconCls="basis ui-icon-pencil" onclick="openRisk()" id="openRisk" />
			<fd:linkbutton value="{com.glaway.ids.pm.project.task.taskdetail.viewRiskList}" iconCls="basis ui-icon-search" onclick="openRisk()" id="viewRisk" />
		</fd:toolbarGroup>
	</fd:toolbar>
	<fd:datagrid id="deliverablesInfoList" checkbox="false"  pagination="true"
			url="taskDetailController.do?getDocRelationList&id=${planId}" fit="true"
			toolbar="#riskTagListtb" idField="deliverableId" fitColumns="true">
		<fd:colOpt title="{com.glaway.ids.pm.project.task.attachment}">
			<fd:colOptBtn tipTitle="{com.glaway.ids.pm.project.task.selectFromProjectLib}" iconCls="basis ui-icon-search"
				hideOption="hideCheck()" onClick="checkLine"></fd:colOptBtn>
			<fd:colOptBtn tipTitle="{com.glaway.ids.pm.project.task.uploadFromLocality}" iconCls="basis ui-icon-send"
				hideOption="hideUpdate()" onClick="uploadLine"></fd:colOptBtn>
			<fd:colOptBtn tipTitle="提交审批" iconCls="basis ui-icon-submitted_approval"
				hideOption="hideSubmit()" onClick="submitLine"></fd:colOptBtn>
		</fd:colOpt>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.deliverableName}" field="deliverableName" width="120"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.rdtask.flowResolve.document}" field="docName" width="120" formatterFunName="addLink"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.bizVersion}" field="version" width="120" ></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.common.lable.status}" field="status" width="100"/>
	</fd:datagrid>
</div>

<fd:dialog id="taskDocCheckLineDialog" width="1000px" height="500px" modal="true" title="{com.glaway.ids.pm.project.task.taskdetail.selectDeliverables}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="taskDocCheckLineDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="projLibSubimtDialog" width="800px" height="600px" modal="true" title="{com.glaway.ids.pm.project.task.taskdetail.uploadAttachment}">
  	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="projLibSubimt"></fd:dialogbutton>
  	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
		
<fd:dialog id="openDocumentInfoDialog" width="1100px" height="650px" modal="true" title="{com.glaway.ids.pm.project.plan.basicLine.showDocDetail}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="submitDialogForDocCheckLine" width="730px" height="260px" modal="true"
	title="{com.glaway.ids.pm.project.projectmanager.submit}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="beOk"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="beCancel"></fd:dialogbutton>
</fd:dialog>

<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
<script type="text/javascript">

function openRisk() {
	var title = '<spring:message code="com.glaway.ids.pm.project.task.taskdetail.projectManage"/>';
	if (!top.$('#maintabs').tabs('exists', title)) {
		top.addTabOrRefresh(title,
				"projectMenuController.do?projectMenu&projectId=${projectId}" + "&fromType=fromTaskOpenRisk",
				'pictures');
	} else {
		top.$('#maintabs').tabs('close', title);
		top.addTabOrRefresh(title,
				"projectMenuController.do?projectMenu&projectId=${projectId}" + "&fromType=fromTaskOpenRisk",
				'pictures');
	}
}

function hideCheck(row,index) {
	//项目为非暂停状态且计划为叶子节点的时候显示按钮
	if ('${output_status }'!= 'pause'&&'${isOwner}'=='true') {
		if ('${plan_.bizCurrent}' == "ORDERED") {
			return false;
		}
		else {
			return true;
		}
	}
	else {
		return true;
	}
}

function hideUpdate(row,index) {
	//项目为非暂停状态且计划为叶子节点的时候显示按钮
	if ('${output_status }'!= 'pause'&&'${isOwner}'=='true') {
		if ('${plan_.bizCurrent}' == "ORDERED") {
			return false;
		}
		else {
			return true;
		}
	}
	else {
		return true;
	}
}

function hideSubmit(row,index) {
	if ((row.docName != null && row.docName != '' 
		&& row.docName != 'undefined') && row.status=="拟制中") {
		//项目为非暂停状态且计划为叶子节点的时候显示按钮
		if ('${output_status }'!= 'pause'&&'${currentUserId}'==row.docCreateBy) {
			if ('${plan_.bizCurrent}' == "ORDERED") {
				return false;
			}
			else {
				return true;
			}
		}
		else {
			return true;
		}
	}
	else {
		return true;
	}
}

function checkLine(id,index) {
	$('#deliverablesInfoList').datagrid('selectRow',index);
 	var row = $('#deliverablesInfoList').datagrid('getSelected');
	idRow=row.id;
	var dialogUrl = 'projLibController.do?goProjLibLayout0&id=${projectId}';
	$("#"+'taskDocCheckLineDialog').lhgdialog("open", "url:"+dialogUrl);
}
function taskDocCheckLineDialog(iframe) {
	//iframe = this.iframe.contentWindow;
	if (iframe.validateSelectedNum()) {
		var docId = iframe.getSelectionsId();
		var rows=$('#deliverablesInfoList').datagrid('getSelections');
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
					if (msg=='<spring:message code="com.glaway.ids.pm.project.plan.deliverables.updateSuccess"/>') {
						 $('#planResolve').attr("style","display:none");
				          $('#flowResolve').attr("style","display:none");
					}
					reloadTable();
				}
			}
		});
		return true;
	} else {
		return false;
	}
}

function uploadLine(o,index) {
 	$('#deliverablesInfoList').datagrid('selectRow',index);
  	var row = $('#deliverablesInfoList').datagrid('getSelected');
	createaddwindow('projLibController.do?goAdd2&deliverableId=' + row.deliverableId + '&projectId=${projectId}'+ '&opType=LINKDELIVER', row.deliverableId);
}

$(function() {
    $('#deliverablesInfoList').datagrid({
   		onLoadSuccess: function() {
    		var datas = $('#deliverablesInfoList').datagrid("getRows");
   		 	var count = 0;
   		 	if (planType=='0') {
   			 	for (var i in datas ) {
   	    			if (datas[i].docName!=null) {
   	    				count++;
   	    			}
   	    		} 	
   			 	if (count == datas.length) {
    				$('#parentSubmit').attr("style","display:");
    			}
   		    }
  		 	beginFun();
    	}
    });

    if('${viewType}'=='VIEW'){
    	$("#openRisk").attr("style", "display:none;");
		$("#viewRisk").attr("style", "display:none;");
    }else{
        if('${isRiskPluginValid}' == 'true')
        {
        	var taskNameType="${plan_.taskNameType}";	
        	var taskNameTypeRisk = "${taskNameTypeRisk}";
        	if (taskNameType != null && taskNameType != '' 
        		&& taskNameType != 'undefined' && taskNameType == taskNameTypeRisk
        		&& '${isOwner}'=='true') {
       			if ('${plan_.bizCurrent}' == "FEEDBACKING"
       	    		|| '${plan_.bizCurrent}' == "FINISH") {
       	        	$("#openRisk").attr("style", "display:none;");
       	    		$("#viewRisk").attr("style", "display:");
       	        }
       	        else if ('${plan_.bizCurrent}' == "ORDERED") {
       	        	$("#openRisk").attr("style", "display:");
       				$("#viewRisk").attr("style", "display:none;");
       	        }
       	        else {
       	        	$("#openRisk").attr("style", "display:none;");
       				$("#viewRisk").attr("style", "display:none;");
       	        }
       		}
       	    else {
       			$("#openRisk").attr("style", "display:none;");
       			$("#viewRisk").attr("style", "display:none;");
        	}
        }else{
        	$("#openRisk").attr("style", "display:none;");
    		$("#viewRisk").attr("style", "display:none;");
        }
    }

});

var idRow='';

	// 生命周期状态name、title转换
	function viewDocBizCurrent(val, row, value) {
		var statusList = eval($('#documentStatusList').val());
		for (var i = 0; i < statusList.length; i++) {
			if (val == statusList[i].name) {
				return statusList[i].title;
			}
		}
	}
	
	//暂时没用
	function uploadDocument(id) {
		$.fn.lhgdialog({
			content : 'url:projLibController.do?goAdd2&projectId=${projectId}',
			lock : true,
			width : 630,
			height : 400,
			title : '<spring:message code="com.glaway.ids.pm.project.task.taskdetail.uploadFile"/>',
			opacity : 0.3,
			cache : false,
			ok : function() {
				iframe = this.iframe.contentWindow;
				},
			cancelVal : '<spring:message code="com.glaway.ids.common.btn.close"/>',
			cancel : true
		});
	}
	
	var prodDocId = '';
	function getProdDocId(docId) {
		prodDocId = docId;
	}
	
	function addLink(val, row, value) {
		if (val!=null&&val!='') {
			if ((row.havePower == true || row.havePower == 'true')&& '${userLevel}' >= row.securityLevel) {
				return "<a href='#' onclick='showDocDetail(\""
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
	
	function showDocDetail(id,download,detail) {
		var url="projLibController.do?viewProjectDocDetail&opFlag=1&id="+id+ "&download=" + download+ "&detail=" + detail;
		createDialog('openDocumentInfoDialog',url);
	}
	
	function createdetailwindow2(title, url, width, height) {
		width = width ? width : 700;
		height = height ? height : 400;
		if (width == "100%" || height == "100%") {
			width = document.body.offsetWidth;
			height = document.body.offsetHeight - 100;
		}
		$.fn.lhgdialog({
			content : 'url:' + url,
			width : width,
			height : height,
			title : title,
			button:[{name:'<spring:message code="com.glaway.ids.common.btn.close"/>',focus : true}]
		});
	}
	
	function createaddwindow(url,id) {
		idRow=id;
		$("#"+'projLibSubimtDialog').lhgdialog("open","url:"+url);
	}
	
	function projLibSubimt(iframe) {
		var result = false;
		var result = iframe.saveFileBasicAndUpload();
		if(result){
			reloadTable(); 
			return true;
		} else {
			return false;
		}
	}
	
	function vidateRemark(remark,length) {
		if (remark != null && remark != ''&& remark != 'undefined') {
			if (remark.length > length) {
				tip('<spring:message code="com.glaway.ids.common.remarkLengthParam" arguments="' + length + '"/>');
				return false;
			}
	   	}
		return true;
	}
	
	function uploadFile(docId) {
		debugger;
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
				//如果上传成功，不需要提示
				//var rst = ajaxRstAct(d);
				debugger;
				if (d.success) {
					var msg = d.msg;
					if(msg=='<spring:message code="com.glaway.ids.pm.project.plan.deliverables.updateSuccess"/>'){
						 $('#planResolve').attr("style","display:none");
				          $('#flowResolve').attr("style","display:none");
					}
					reloadTable();
				}
			}
		});
	}
	
	function submitLine(id,index) {
		$('#deliverablesInfoList').datagrid('selectRow',index);
	 	var row = $('#deliverablesInfoList').datagrid('getSelected');
		$.ajax({
            url : 'projLibController.do?submitProcess',
            type : 'post',
            data : {
                id : row.docId,
                entityName : '${entityName}',
                businessType : '${businessType}'+row.fileTypeId,
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
                            + '&entityName=${entityName}'
                            + '&businessType='+d.obj);
                } else {
                    top.tip(d.msg);
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
        $('#deliverablesInfoList').datagrid('unselectAll');
        $('#deliverablesInfoList').datagrid('reload');
    }
</script>
