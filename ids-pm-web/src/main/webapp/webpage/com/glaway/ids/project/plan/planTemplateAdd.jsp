<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>计划列表</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow: hidden;">

<div class="easyui-layout" fit="true">
	<input type = "hidden" id = "projectId" name = "projectId" value = "${projectId}"/>
	<input id="step" name="step" type="hidden" value="2" />
	
	 <c:if test="${type != 'projtemplate' }">
	 <div region="north" style="width: 100%;height:180px" title="" id="north_page_panel">
	 	<fd:inputText id="templateName" name="templateName" title="模板名称" required="true" value = "${template_.name}" readonly="${type eq 'update' || type eq 'revise'}"/>
	 	<fd:inputTextArea id="remark" name="remark" title="{com.glaway.ids.common.lable.remark}" value="${template_.remark}"/>
	 </div>
	</c:if>
	 	<c:if test="${type != 'projtemplate' }">
	 	<div region="center" style="padding: 1px; width: 100%;" title="" id="center_page_panel">
	 	</c:if>
		 	<fd:linkbutton id="addPlanBtn" onclick="addPlan()" value="新增计划"
					iconCls="basis ui-icon-plus" />
			<%--<fd:linkbutton id="importMPPPlanBtn" onclick="importPlan('mpp')"
					value="{com.glaway.ids.pm.project.plan.mppImport}" iconCls="basis ui-icon-import"/>--%>
			<fd:linkbutton id="importExcelPlanBtn" onclick="importPlan('excel')"
					value="{com.glaway.ids.pm.project.plan.excelImport}" iconCls="basis ui-icon-import"/>
	
		   
	    	<%--<fd:linkbutton id="exportPlanMpp2" onclick="exportPlan('mpp')"
				value="{com.glaway.ids.pm.project.plan.exportMppPlan}" iconCls="basis ui-icon-export"/>--%>
			<fd:linkbutton id="exportPlanExcel2" onclick="exportPlan('excel')"
				value="{com.glaway.ids.pm.project.plan.exportExcelPlan}" iconCls="basis ui-icon-export"/>
		
			<fd:linkbutton id="multiModifyPlan" onclick="multiModifyPlan()"
					value="{com.glaway.ids.pm.project.plan.massModify}" iconCls="basis ui-icon-pencil"/>
			<div style="padding: 0 4px; height:80%;">
				<fd:lazytreegrid id="planAddList" idField="id" treeField="planName" url="${url}&planTemplateId=${planTemplateId}&type=${type}&projectId=${projectId}&id=${projectTemplateId}&flag=${flag}&planId=${plan_.id}"
								 style="width:100%;height:99%;" imgUrl="plug-in/dhtmlxSuite/imgs/" lazyUrl="${url}&planTemplateId=${planTemplateId}&type=${type}&projectId=${projectId}&id=${projectTemplateId}&flag=${flag}&planId=${plan_.id}">
					<fd:columns>
						<fd:column field="planNumber" title="序号" width="40"></fd:column>
						<fd:column field="optBtn" title="操作" width="80"></fd:column>
						<fd:column field="planName" title="计划名称 " width="150"></fd:column>
						<fd:column field="planLevelInfo" title="计划等级"></fd:column>
						<fd:column field="workTime" title="参考工期（天）" width="120"></fd:column>
						<fd:column field="milestone" title="里程碑" width="80"></fd:column>
						<fd:column field="inputs" title="输入项名称"></fd:column>
						<%-- <fd:column field="isNecessary" title="是否必要" width="80"></fd:column> --%>
						<fd:column field="delivaryName" title="交付项名称"></fd:column>
						<fd:column field="preposePlans" title="前置计划"></fd:column>
					</fd:columns>
					<fd:eventListener event="onRightClick" listener="onRightClickFuname"></fd:eventListener>
					<%-- 	<fd:eventListener event="onLoadSuccess" listener="onloadSuccess1"></fd:eventListener>
                        <fd:eventListener event="onRowSelect" listener="onRowSelect"></fd:eventListener> --%>
				</fd:lazytreegrid>
			</div>
		<c:if test="${type != 'projtemplate' }">			
	 	</div>
	 	</c:if>
	 	
		<fd:dialog id="addPlanDialog" width="750px" height="600px"
				modal="true" title="{com.glaway.ids.pm.project.plan.resource.addPlan}">
		</fd:dialog>
		
		<fd:dialog id="updatePlanDialog" width="750px" height="600px"
				modal="true" title="修改计划">
		</fd:dialog>		
		<fd:dialog id="addSubPlanDialog" width="750px" height="600px"
				modal="true" title="{com.glaway.ids.pm.project.plan.addSonPlan}">
		</fd:dialog>
		<fd:dialog id="viewPlanDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.viewPlan}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="multiModifyDialog" width="750px" height="500px"
				modal="true" title="{com.glaway.ids.pm.project.plan.massModify}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
							 callback="saveTemplateAndSubmitOk"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
							 callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>

	<fd:menu id="menusEditing">
		<fd:menuitem text="{com.glaway.ids.pm.project.plan.addSonPlan}" onclick="addSubPlan_()"
			iconCls="basis ui-icon-plus" id="addSubPlan" />
		<fd:menuitem text="{com.glaway.ids.pm.project.plan.addPlanAfter}" onclick="addPlanAfter_()"
			iconCls="basis ui-icon-plus" id="addPlanAfter" />
	</fd:menu>
<fd:dialog id="importExcelDialog" width="400px" height="150px" modal="true" title="{com.glaway.ids.common.msg.import}">
        <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="importPlanTemMpp"></fd:dialogbutton>
        <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="submitApproveDialog" width="400px" height="180px" modal="true"
			title="{com.glaway.ids.pm.project.plan.toApprove}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="submitApproveOK"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="importTemplateDialog" width="730px" height="400px"
        modal="true" title="{com.glaway.ids.pm.project.plan.planTemplateList}">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="importSubmit"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="importMppDialog" width="400px" height="150px"
		modal="true" title="{com.glaway.ids.pm.project.plan.mppImport}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="importPlanTem"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

</div>		
<script type="text/javascript">
//新增计划
function addPlan() {
	var dialogUrl = 'planTemplateController.do?goAddBefore&planTemplateId=${planTemplateId}&id=${projectTemplateId}&type=add';
	$("#addPlanDialog").lhgdialog("open", "url:" + dialogUrl);
/*	createDialog('addPlanDialog', dialogUrl);*/
	/* top.jeasyui.util.commonMask('open','  ');
	setTimeout(function() {
		top.jeasyui.util.commonMask('close');
	}, 1500) */
}

function planListSearch() {
	debugger;
	var type = '${type}';
	var url = '';
	if(type == 'projtemplate'){
		url = 'projTemplateDetailController.do?getPlanListFor&projectTemplateId=${projectTemplateId}'
	}else{
		url = 'planTemplateController.do?getPlanListFor&planTemplateId=${planTemplateId}';
	}
	
	//可以展开的 节点添加 count_类属性
	addCountClass();
	// 统计已经展开节点的索引
	var expands = new Array();
	var ix = 0;
	var inde = 0;
	var reg2 = RegExp(/minus/);
	$(".count_").each(function() {
		inde = inde + 1;
		if ($(this).attr("src").match(reg2)) {
			expands[ix++] = inde - 1;
		}
	});
	
	$.ajax({
		url : url,
		type : 'post',
		cache : false,
		success : function(data) {
			var d = $.parseJSON(data);
			debugger;
			mygrid_planAddList.clearAll();
			mygrid_planAddList.parse(d.obj, 'js');
			if(d.obj.length > 0){
				$("#planAddListMsg").hide();
			}
			doExpandSelect(expands);
		}
	});
}

function doExpandSelect(expands) {
	addCountClass();
	$(".count_")
			.each(
					function(index) {
						for ( var i in expands) {
							if (index == expands[i]) {
								this.parentNode.parentNode.parentNode.parentNode.parentNode.grid
										.doExpand(this);
							}
						}

					});
}

function addPlanTemplate(){
	
	var templateName = $("#templateName").val();
	var remark = $("#remark").val();
	if(templateName == "" || templateName == null){
		top.tip("模板名称不能为空");
		return false;
	}
	
	$.ajax({
		type:'POST',
		data:{
			templateName : templateName,
			planTemplateId : '${planTemplateId}',
			type : '${type}'
		},
		url:'planTemplateController.do?checkTemplateNameBeforeSave',
		cache:false,
		success:function(data){
			var obj = $.parseJSON(data);
			if(obj.success){
				$.ajax({
					type:'POST',
					data:{
						templateName : templateName,
						planTemplateId : '${planTemplateId}',
						type : '${type}',
						remark : remark
					},
					url:'planTemplateController.do?savePlanTemplate',
					cache:false,
					success:function(data){
						var d = $.parseJSON(data);
						top.tip(d.msg);
						if(d.success){
							if('${fromType}' == 'planSaveAsTemplate'){
								$.fn.lhgdialog("closeSelect");
							}else{
								var win = $.fn.lhgdialog("getSelectParentWin");
								win.reloadTable();
								$.fn.lhgdialog("closeSelect");
							}

						}
					}
				});
			}else{
				top.tip("模板名称重复");
				return false;
			}
		}
	});
	
}


function planSaveAsTemplateSubmit(){
	var templateName = $("#templateName").val();
	if(templateName == "" || templateName == null){
		top.tip("模板名称不能为空");
		return false;
	}
	var remark = $("#remark").val();
	
	$.ajax({
		type:'POST',
		data:{
			templateName : templateName,
			planTemplateId : '${planTemplateId}',
			remark : remark,
			type : '${type}'
		},
		url:'planTemplateController.do?checkTemplateNameBeforeSave',
		cache:false,
		success:function(data){
			var obj = $.parseJSON(data);
			if(obj.success){
				$.ajax({
					type:'POST',
					data:{
						templateName : templateName,
						planTemplateId : '${planTemplateId}',
						type : '${type}'
					},
					url:'planTemplateController.do?savePlanTemplate',
					cache:false,
					success:function(data){
						debugger;
						var d = $.parseJSON(data);
						var win = $.fn.lhgdialog("getSelectParentWin");
						win.tip(d.msg);
						if(d.success){
							var planTemplateId=d.obj;
						    afterSubmit(planTemplateId);
						}
						/* else{
							$.fn.lhgdialog("closeSelect");
						} */
					}
				});
			}else{
				top.tip("模板名称重复");
				return false;
			}
		}
	});
}

//用于区分保存和保存提交按钮
var btnType = "";
//获取保存后的计划模板id
function afterSubmit(planTemplateId) {
	if(btnType=='submit'){
		var dialogUrl = 'planTemplateController.do?goSubmitApprove&planTemplateId='
			+ planTemplateId;
		createDialog('submitApproveDialog', dialogUrl);
	}
}


function submitApproveOK(iframe){
	iframe.startPlanTemProcess();
	return false;
} 

function importPlan(type) {
	debugger;
	var source = '${type}';
    $.post(
        'planTemplateController.do?checkBeforeImport',
        {
            'planTemplateId' : '${planTemplateId}',
            'projectTemplateId' : '${projectTemplateId}',
            'type' : source
        },
        function(data) {
        	debugger;
            var d = $.parseJSON(data);
            if (d.success) {
                var msg = d.msg;
                var a = msg.indexOf('<spring:message code="com.glaway.ids.pm.project.plan.doSuccess"/>');
                var b = d.msg.indexOf('<spring:message code="com.glaway.ids.pm.project.plan.noData"/>');
                if (a == 0) {
                    top.Alert.confirm(
                        '<spring:message code="com.glaway.ids.pm.project.plan.confirmCoverImport"/>',
                        function(r) {
                            if (r) {
                                if (type == 'excel') {
                                	var url = 'planTemplateController.do?goImportExcel&planTemplateId=${planTemplateId}&projectTemplateId=${projectTemplateId}';
                                    createDialog('importExcelDialog', url);
                                } else if(type == 'template'){
                                    //模板导入
                                	var url = 'planTemplateController.do?planTemplate_import&isDialog=true';
                                    createDialog('importTemplateDialog', url);
                                }else if(type == 'mpp'){
                                	var dialogUrl = 'planTemplateController.do?goImportTemplatePlan&planTemplateId=${planTemplateId}';
									createDialog(
										'importMppDialog',
										dialogUrl);
                                }
                            }
                        });
                } else if (b == 0) {
                    if (type == 'excel') {
                    	//excel导入
                    	var url = 'planTemplateController.do?goImportExcel&planTemplateId=${planTemplateId}&projectTemplateId=${projectTemplateId}';
						$("#" + 'importExcelDialog')
								.lhgdialog("open", "url:" + url);
                    } else if(type == 'template'){
                    	//模板导入
                    	var url = 'planTemplateController.do?planTemplate_import&isDialog=true';
                        createDialog('importTemplateDialog', url);
                    }else if(type == 'mpp'){
                    	var dialogUrl = 'planTemplateController.do?goImportTemplatePlan&planTemplateId=${planTemplateId}';
						createDialog(
							'importMppDialog',
							dialogUrl);
                    }
                }
            }
        });
}

function exportPlan(type) {
	var source = '${type}';
	
	if(source == 'projtemplate'){
		if('mpp' == type){
			window.location.href = 'planTemplateController.do?doExport&id=${projectTemplateId}&type=projectTemplate';
		}else{
			//导出EXCEL-项目模板
			window.location.href = "projTemplateController.do?exportXls&projectTemplateId=${projectTemplateId}";
			
		}
	}else{
		if( 'mpp' == type) {
			window.location.href = 'planTemplateController.do?doExport&id='+'${planTemplateId}&type=planTemplate';
			
		} else {
			
			//导出EXCEL-计划模板
			window.location.href = "planTemplateController.do?exportXls&planTemplateId=${planTemplateId}";
		}
	}
}

function importPlanTem(iframe) {
	saveOrUp(iframe);
	return false;
}

function onRightClickFuname(id,ind,event){
	debugger;
	document.oncontextmenu = function(event) {
		return false;
	}

	mygrid_planAddList.selectRowById(id);
	$("#addSubPlan").show();
	$("#importMppSubPlan").show();
	$("#imporExcelSubPlan").show();
	$("#importTempSubPlan").show();
	$("#addPlanAfter").show();
	$("#insertMppPlan").show();
	$("#insertExcelPlan").show();
	$("#insertTempPlan").show();
	$("#saveAsPlanTemplate").show();
	$("#exportPlanMpp").show();
	$("#exportPlanExcel").show();

	$('#menusEditing').menu('show', {
		left : event.screenX-474,
		top : event.clientY
	});
}


//新增子计划
function addSubPlan_() {
	var dialogUrl = 'planTemplateController.do?goAddBefore&planTemplateId=${planTemplateId}&id=${projectTemplateId}&parentPlanId='
		+ mygrid_planAddList.getSelectedRowId();
	createDialog('addSubPlanDialog', dialogUrl);
}

function viewPlan_(id){
	var dialogUrl = 'planTemplateController.do?goViewPlan&planTemplateId=${planTemplateId}&id=${projectTemplateId}&planId='+id+'&projectId='+$("#projectId").val();
	createDialog('viewPlanDialog', dialogUrl);
}

function addPlanAfter_(){
	var dialogUrl = 'planTemplateController.do?goAddBefore&planTemplateId=${planTemplateId}&id=${projectTemplateId}&beforePlanId='
		+ mygrid_planAddList.getSelectedRowId();
	createDialog('addPlanDialog', dialogUrl);
}

// 刷新计划列表-计划模板
function savePlanTemplateExcelSuccess(obj) {
	$.ajax({
        url : 'planTemplateController.do?getPlanListFor&planTemplateId='+ obj,
        type : 'post',
        cache : false,
        success : function(data) {
        	var d = $.parseJSON(data);
            debugger;
            mygrid_planAddList.clearAll();
            mygrid_planAddList.parse(d.obj, 'js');
            if(d.obj.length > 0){
                $("#planAddListMsg").hide();
            }
        }
    });
}

//刷新计划列表-项目模板
function saveProjectTemplateExcelSuccess(obj) {
	$.ajax({
        url : 'projTemplateDetailController.do?getPlanListFor&projectTemplateId='+ obj,
        type : 'post',
        cache : false,
        success : function(data) {
        	var d = $.parseJSON(data);
            debugger;
            mygrid_planAddList.clearAll();
            mygrid_planAddList.parse(d.obj, 'js');
            if(d.obj.length > 0){
                $("#planAddListMsg").hide();
            }
        }
    });
}

function planTemplate_downErrorReport(dataListAndErrorMap) {
    top.Alert.confirm(
        '<spring:message code="com.glaway.ids.common.confirmDownloadErrorReport"/>',
        function(r) {
            if (r) {
                var url = 'planTemplateController.do?downErrorReport';
                downloadErrorReport(url,dataListAndErrorMap);                              
            }
        });
}


function modifyPlanOnTree_(id,parentPlanId){
	var dialogUrl = 'planTemplateController.do?goAdd&planId='+id+'&planTemplateId=${planTemplateId}&id=${projectTemplateId}&type=update&parentPlanId='+parentPlanId+'&projectId='+$("#projectId").val();
	createDialog('updatePlanDialog', dialogUrl);
}


function deleteOnTree_(id){
	if (id != null) {
		top.Alert.confirm(
			'<spring:message code="com.glaway.ids.common.confirmBatchDel"/>',
			function(r) {
				if (r) {
					$.ajax({
						url : 'planTemplateController.do?doBatchPlanTemplate',
						type : 'post',
						data : {
							ids : id,
							projectTemplateId : '${projectTemplateId}',
							planTemplateId : '${planTemplateId}'
						},
						cache : false,
						success : function(data) {
							var d = $.parseJSON(data);
							if (d.success) {
								top.tip("删除成功");
								mygrid_planAddList.deleteRow(id);
							}
						}
					});
				}
			});
	}
}

function addCountClass() {
	var reg = RegExp(/plus|minus/);
	$(".objbox img").each(function() {
		if ($(this).attr("src").match(reg)) {
			$(this).addClass("count_");
		}
	});

}

// 打开批量修改计划页面
function multiModifyPlan() {
// 	$('#moreMenu').menu('hide', {});

	var dialogUrl = 'planTemplateController.do?goModifyMass&planTemplateId=${planTemplateId}&id=${projectTemplateId}&type=${type}';
	createDialog('multiModifyDialog', dialogUrl);
}

function saveTemplateAndSubmitOk(iframe){
	iframe.save2();
	return false;
}

function importSubmit(iframe) {
	debugger;
    var planTemplate = iframe.getPlanTemplateDialog();
    if (planTemplate == null) {
        top.tip('<spring:message code="com.glaway.ids.pm.project.plan.savePlanByPlanTemplateIdFail"/>');
        return false;
    } else {
    	$.ajax({
            url : 'projTemplateController.do?saveProjectTemplatePlan',
            type : 'post',
            data : {
            	projectTemplateId : '${projectTemplateId}',
                planTemplateId : planTemplate.id
            },
            cache : false,
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    top.tip(d.msg);
                    planListSearch();
                } else {
                    top.tip(d.msg);
                    return false;
                }
            }
        });
    }
}

function importPlanTemMpp(iframe) {
	$('#btn_sub', iframe.document).click();
	return false;
}

</script>
</body>
</html>
