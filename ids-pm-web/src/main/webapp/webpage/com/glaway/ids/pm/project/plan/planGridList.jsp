<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>计划列表</title>
    <meta http-equiv="X-UA-Compatible" content="edge"></meta>
<t:base type="jquery,easyui,tools"></t:base>
<script src="webpage/com/glaway/ids/pm/project/plan/planList.js"></script>

<script type="text/javascript">
function onRightClick(rowId, cellIndex, event, row) {

	// 禁止IE、Chrome和Firefox的右键事件
	document.oncontextmenu = function(event) {
		return false;
	}
	var createBy = mygrid.getRowAttribute(rowId, "createBy");
	var owner = mygrid.getRowAttribute(rowId, "owner");
	var parentPlanId = mygrid.getRowAttribute(rowId, "parentPlanId");
	var parent_Id = mygrid.getRowAttribute(rowId, "parent_Id");
	var isCreateByPmo = mygrid.getRowAttribute(rowId, "isCreateByPmo");
	var flowStatus = mygrid.getRowAttribute(rowId, "flowStatus");
	var isAssignSingleBack = mygrid.getRowAttribute(rowId, "isAssignSingleBack");
	var bizCurrent = mygrid.getRowAttribute(rowId, "bizCurrent");
	var parent_owner = mygrid.getRowAttribute(rowId, "parent_owner");
	var parent_createBy = mygrid.getRowAttribute(rowId, "parent_createBy");
	var parent_flowStatus = mygrid.getRowAttribute(rowId,
			"parent_flowStatus");
	var parent_isAssignSingleBack = mygrid.getRowAttribute(rowId,
			"parent_isAssignSingleBack");
	var parent_bizCurrent = mygrid.getRowAttribute(rowId,
			"parent_bizCurrent");
	var project_bizCurrent = mygrid.getRowAttribute(rowId,
			"project_bizCurrent");
	var opContent = mygrid.getRowAttribute(rowId, "opContent");
	var result = mygrid.getRowAttribute(rowId, "result");
	var planId = mygrid.getRowAttribute(rowId, "id");
	var planTaskType = mygrid.getRowAttribute(rowId, "taskType");
	var planTaskNameType = mygrid.getRowAttribute(rowId, "taskNameType");
	var planType = mygrid.getRowAttribute(rowId, "planType");

	$('#saveAsPlanTemplate').show();
	$('#exportPlan').show();
	$('#exportPlanMpp').show();
	$('#exportPlanExcel').show();
	$('#viewTask').hide();

	if ('true' == parent.$("#isModify").val()) {

		$('#addSubPlan').show();
		$('#importMppSubPlan').show();
		$('#imporExcelSubPlan').show();
		$('#importTempSubPlan').show();
		$('#addPlanAfter').show();
		$('#insertPlan_').show();
		$('#insertMppPlan').show();
		$('#insertExcelPlan').show();
		$('#insertTempPlan').show();
        $('#addBomPlan').show();
		if ('STARTING' == project_bizCurrent) {
			$('#addSubPlan').show();
            $('#addBomPlan').show();
            $('#importSubPlan').show();
			$('#importMppSubPlan').show();
			$('#imporExcelSubPlan').show();
			$('#importTempSubPlan').show();
			if (parentPlanId == null || parentPlanId == ''
					|| parentPlanId == undefined) {
			}
			if (bizCurrent == 'EDITING') {
				if ((parent.$("#currentUserId").val() == createBy)
						|| (('true' == parent.$("#isProjectManger").val()) && isCreateByPmo)) {
					if (flowStatus != null && flowStatus != ''
							&& flowStatus != undefined
							&& flowStatus != 'NORMAL'
							&& isAssignSingleBack !='true') {
						$('#addSubPlan').hide();
                        $('#addBomPlan').hide();
						$('#importSubPlan').hide();
						$('#importMppSubPlan').hide();
						$('#imporExcelSubPlan').hide();
						$('#importTempSubPlan').hide();
					}

				} else {
					$('#addSubPlan').hide();
                    $('#addBomPlan').hide();
                    $('#importSubPlan').hide();
					$('#importMppSubPlan').hide();
					$('#imporExcelSubPlan').hide();
					$('#importTempSubPlan').hide();
				}
				//如果父计划是执行中，负责人控制该计划研发流程导入按钮（bug24347）：
				$.ajax({
		            url : 'planController.do?getPlanBizCurrentAndOwnerInfoById',
		            type : 'post',
		            data : {id : planId},
		            cache : false,
		            success : function(data) {
		                var d = $.parseJSON(data);
		                if (d.success) {
		                	if(d.obj.split('-')[0] == 'EDITING' && parent.$("#currentUserId").val() == createBy){
		                		$('#importSubPlan').show();
		                	}
		                	else if(d.obj.split('-')[0] == 'ORDERED' && parent.$("#currentUserId").val() == d.obj.split('-')[1]){
		                		$('#importSubPlan').show();
		                	}else{
		                		 $('#importSubPlan').hide();
		                	}
		                }
		            }
		        });


			}else if(bizCurrent == 'TOBERECEIVED' || bizCurrent == 'LAUNCHED'){
                $('#importSubPlan').hide();
                $('#addSubPlan').hide();
                $('#addBomPlan').hide();
            } else if (bizCurrent == 'ORDERED' ) {
				$('#viewTask').show();
				if (parent.$("#currentUserId").val() == owner) {
					$('#importSubPlan').show();
					if (flowStatus != null && flowStatus != ''
							&& flowStatus != undefined
							&& flowStatus != 'NORMAL'
							&& isAssignSingleBack !='true') {
						$('#addSubPlan').hide();
                        $('#addBomPlan').hide();
						$('#importMppSubPlan').hide();
						$('#imporExcelSubPlan').hide();
						$('#importTempSubPlan').hide();
					}
				} else {
					$('#addSubPlan').hide();
                    $('#addBomPlan').hide();
                    $('#importSubPlan').hide();
					$('#importMppSubPlan').hide();
					$('#imporExcelSubPlan').hide();
					$('#importTempSubPlan').hide();
				}

			} else if (bizCurrent == 'FEEDBACKING'
					|| bizCurrent == 'FINISH') {
				$('#viewTask').show();
				$('#addSubPlan').hide();
                $('#addBomPlan').hide();
                $('#importSubPlan').hide();
				$('#importMppSubPlan').hide();
				$('#imporExcelSubPlan').hide();
				$('#importTempSubPlan').hide();
			} else {
				$('#addSubPlan').hide();
                $('#addBomPlan').hide();
                $('#importSubPlan').hide();
				$('#imporExcelSubPlan').hide();
				$('#importTempSubPlan').hide();
			}
		} else if ('EDITING' == project_bizCurrent) {
			//bug:24578 【IDS R3.4.09】
			/* if ('true' != parent.$("#isPmo").val()) {
				$('#addSubPlan').hide();
                $('#addBomPlan').hide();
                $('#importSubPlan').hide();
				$('#importMppSubPlan').hide();
				$('#imporExcelSubPlan').hide();
				$('#importTempSubPlan').hide();
			} */
		} else {
			$('#addSubPlan').hide();
            $('#addBomPlan').hide();
            $('#importSubPlan').hide();
			$('#importMppSubPlan').hide();
			$('#imporExcelSubPlan').hide();
			$('#importTempSubPlan').hide();
		}
		if (opContent == '流程分解') {
			$('#addSubPlan').hide();
            $('#addBomPlan').hide();
			$('#importMppSubPlan').hide();
			$('#imporExcelSubPlan').hide();
			$('#importTempSubPlan').hide();
			if (bizCurrent == 'EDITING' && parent.$("#currentUserId").val() == createBy){
				$('#importSubPlan').show();
				if (flowStatus != null && flowStatus != ''
					&& flowStatus != undefined
					&& flowStatus != 'NORMAL'
					&& isAssignSingleBack !='true') {
	                $('#importSubPlan').hide();
				}
				//判断父子计划提交时,父计划待接收驳回成拟制中时，且子计划有状态不是拟制中时,无法再进行子计划研发流程模板导入
				 $.ajax({
			            url : 'planController.do?childBizCurrentExistAllEditing',
			            type : 'post',
			            data : {parentPlanId : planId},
			            cache : false,
			            success : function(data) {
			                var d = $.parseJSON(data);
			                if (d.success) {
			                	$('#importSubPlan').hide();
			                }
			            }
			        });

			}else if(bizCurrent == 'ORDERED' && parent.$("#currentUserId").val() == owner)
			{
				$('#importSubPlan').show();
			}
			else{
				$('#importSubPlan').hide();
			}
		}

		if (planTaskType == '流程计划') {
			$('#addPlanAfter').hide();
            $('#insertPlan_').hide();
			$('#insertMppPlan').hide();
			$('#insertExcelPlan').hide();
			$('#insertTempPlan').hide();
		}

		if ('INVALID' == bizCurrent) {
			$('#viewTask').show();
			$('#importMppSubPlan').hide();
		}

		if (parentPlanId != null && parentPlanId != ''
				&& parentPlanId != undefined && parent_Id != null
				&& parent_Id != '' && parent_Id != undefined) {
			if (parent_owner != null && parent_owner != ''
					&& parent_owner != undefined) {
				if (parent_bizCurrent == 'EDITING') {
					if (parent.$("#currentUserId").val() == parent_createBy
							|| (('true' == parent.$("#isProjectManger").val()) && isCreateByPmo)) {
						if (parent_flowStatus != null
								&& parent_flowStatus != ''
								&& parent_flowStatus != undefined
								&& parent_flowStatus != 'NORMAL'
								&& parent_isAssignSingleBack != 'true') {
							$('#addPlanAfter').hide();
                            $('#insertPlan_').hide();
							$('#insertMppPlan').hide();
							$('#insertExcelPlan').hide();
							$('#insertTempPlan').hide();
						}
					} else {
						$('#addPlanAfter').hide();
                        $('#insertPlan_').hide();
						$('#insertMppPlan').hide();
						$('#insertExcelPlan').hide();
						$('#insertTempPlan').hide();
					}
				} else if (parent_bizCurrent == 'ORDERED' ) {
					if (parent.$("#currentUserId").val() == parent_owner) {
						if (parent_flowStatus != null
								&& parent_flowStatus != ''
								&& parent_flowStatus != undefined
								&& parent_flowStatus != 'NORMAL'
								&& parent_isAssignSingleBack != 'true') {
							$('#addPlanAfter').hide();
                            $('#insertPlan_').hide();
							$('#insertMppPlan').hide();
							$('#insertExcelPlan').hide();
							$('#insertTempPlan').hide();
						}
					} else {
						$('#addPlanAfter').hide();
                        $('#insertPlan_').hide();
						$('#insertMppPlan').hide();
						$('#insertExcelPlan').hide();
						$('#insertTempPlan').hide();
					}
				} else {
					$('#addPlanAfter').hide();
                    $('#insertPlan_').hide();
					$('#insertMppPlan').hide();
					$('#insertExcelPlan').hide();
					$('#insertTempPlan').hide();
					;
				}
			}
		}

		if (parentPlanId == null || parentPlanId == ''
				|| parentPlanId == undefined) {
			if ('false' == $("#isPlanAddPlanAfter").val()) {
				$('#addPlanAfter').hide();
			}
			if ('false' == $("#isPlanInsert").val()) {
                $('#insertPlan_').hide();
				$('#insertMppPlan').hide();
				$('#insertExcelPlan').hide();
				$('#insertTempPlan').hide();
			}

			if ('true' == $("#isPlanAddPlanAfter").val()) {
				if ('true' == parent.$("#isPmo").val()
						|| 'true' == parent.$("#isProjectManger").val()) {
					$('#addPlanAfter').show();
				} else {
					$('#addPlanAfter').hide();
				}
			}
			if ('true' == $("#isPlanInsert").val()) {
				if ('true' == parent.$("#isPmo").val()
						|| 'true' == parent.$("#isProjectManger").val()) {
                    $('#insertPlan_').show();
					$('#insertMppPlan').show();
					$('#insertExcelPlan').show();
					$('#insertTempPlan').show();
				} else {
                    $('#insertPlan_').hide();
					$('#insertMppPlan').hide();
					$('#insertExcelPlan').hide();
					$('#insertTempPlan').hide();
				}
			}
		} else {
			if ('false' == $("#isPlanAddPlanAfter").val()) {
				$('#addPlanAfter').hide();
			}
			if ('false' == $("#isPlanInsert").val()) {
                $('#insertPlan_').hide();
				$('#insertMppPlan').hide();
				$('#insertExcelPlan').hide();
				$('#insertTempPlan').hide();
			}
		}

		if ('true' != parent.$("#isModify").val()) {
			$('#addPlanAfter').hide();
            $('#insertPlan_').hide();
			$('#insertMppPlan').hide();
			$('#insertExcelPlan').hide();
			$('#insertTempPlan').hide();
		}

		if (planType == '评审任务') {
			$('#addSubPlan').hide();
            $('#addBomPlan').hide();
            $('#importSubPlan').hide();
			$('#importMppSubPlan').hide();
			$('#imporExcelSubPlan').hide();
			$('#importTempSubPlan').hide();
		}
		//获取参数判断是否是从KDD入口进入IDS
		var kddProductType='${param.kddProductType}';
		//对于KDD入口中设计类的计划右键操作按钮权限的控制    隐藏插入子计划  mpp导入子计划   导入计划模板子计划
		if(kddProductType!=null&&"kddProduct"==kddProductType){
			//增加设计类右键权限显示
			if(planTaskNameType=="4"){
				$('#addSubPlan').hide();
                $('#addBomPlan').hide();
                $('#importSubPlan').hide();
				$('#importMppSubPlan').hide();
				$('#imporExcelSubPlan').hide();
				$('#importTempSubPlan').hide();
			}
		}
        if('${planInsert_}' == 'false' || '${planInsert_}' == "false"){
            $("#insertPlan_").hide();
        }
        if('${planAddSubPlan_}' == 'false' || '${planAddSubPlan_}' == "false"){
            $("#addSubPlan").css('display',"none");
        }
        if('${planSaveAsPlanTemplate_}' == 'false' || '${planSaveAsPlanTemplate_}' == "false"){
            $("#saveAsPlanTemplate").css('display',"none");
        }
        if('${planExportPlanMppForMore_}' == 'false' || '${planExportPlanMppForMore_}' == "false"){
            $("#exportPlan").css('display',"none");
        }
        if('${viewPlanTask_}' == 'false' || '${viewPlanTask_}' == "false"){
            $("#viewTask").css('display',"none");
        }
        if('${planImportSubPlan_}' == 'false' || '${planImportSubPlan_}' == "false"){
            $("#importSubPlan").css('display',"none");
        }
        if('${planAddPlanAfter_}' == 'false' || '${planAddPlanAfter_}' == "false"){
            $("#addPlanAfter").css('display',"none");
        }
		if (result == "false") {
			$('#menusEditing').menu('show', {
				left : event.screenX-400,
				top : event.clientY-30
			});
		} else {
			mygrid.onSelect(rowId);
		}
	} else {
		$('#addSubPlan').hide();
        $('#addBomPlan').hide();
        $('#importSubPlan').hide();
		$('#importMppSubPlan').hide();
		$('#imporExcelSubPlan').hide();
		$('#importTempSubPlan').hide();
		$('#addPlanAfter').hide();
        $('#insertPlan_').hide();
		$('#insertMppPlan').hide();
		$('#insertExcelPlan').hide();
		$('#insertTempPlan').hide();
		$('#saveAsPlanTemplate').hide();
        $('#exportPlan').hide();
		$('#exportPlanMpp').hide();
		$('#exportPlanExcel').hide();
		 //只有已下达、完工反馈中和已完工的计划才能查看任务信息
		 if (bizCurrent == 'ORDERED' || bizCurrent == 'FEEDBACKING'
				|| bizCurrent == 'FINISH') {
			$('#viewTask').show();

			$('#menusEditing').menu('show', {
				left : event.screenX-400,
				top : event.clientY-30
			});
		 }
	}
	 var mouseFlg = false;

	 $('#menusEditing').mouseover(function(){
		 mouseFlg = true;
	 });

	 setTimeout(function(){
		if(!mouseFlg){
			$('#menusEditing').menu('hide');
		}

	},2000);
}



function viewPlanTask_(){
	parent.viewPlanTask();
}

function addSubPlan_(){
	parent.addSubPlan();
}

function importSubPlan_(type){
	parent.importSubPlan(type);
}

function addPlanAfter_(){
	parent.addPlanAfter();
}

function insertPlan_(type){
	parent.insertPlan(type);
}

function saveAsPlanTemplate_(type){
	parent.saveAsPlanTemplate(type);
}

function exportPlanMppSingle_(){
	parent.exportPlanMppSingle();
}

function exportPlanExcelSingle_(){
	parent.exportPlanExcelSingle();
}

function addBomAfter_(){
    parent.addBomAfter();
}


// 打开修改计划页面
function modifyPlanOnTree_(id) {
	parent.modifyPlanOnTree(id);
}


function assignPlanOnTree_(id){
	parent.assignPlanOnTree(id);
}

function deleteOnTree_(id){
	parent.deleteOnTree(id);
}

//关注计划
function concernOnTree_(id){
    parent.concernOnTree(id);
}

//取消关注计划
function unconcernOnTree_(id){
    parent.unconcernOnTree(id);
}

function changePlanOnTreeFlow_(id){
	parent.changePlanOnTreeFlow(id);
}

function changePlanOnTree_(id){
	parent.changePlanOnTree(id);
}

function discardPlan_(id){
	parent.discardPlan(id);
}

function viewPlan_(id){
	parent.viewPlan(id);
}

function revocationPlanOnTree_(id){
	parent.revocationPlanOnTree(id);
}


function openFlowPlans_(id){
    parent.openFlowPlans(id);
}

function addCountClass() {
	var reg = RegExp(/plus|minus/);
	$(".objbox img").each(function() {
		if ($(this).attr("src").match(reg)) {
			$(this).addClass("count_");
		}
	});

}

var expands = new Array();
function expandsCount(){
	var ix = 0;
	var inde = 0;
	var reg2 = RegExp(/minus/);
	$(".count_").each(function() {
		inde = inde + 1;
		if ($(this).attr("src").match(reg2)) {
			expands[ix++] = inde - 1;
		}
	});
}

function doExpandSelect() {
	addCountClass();
	$(".count_").each(
					function(index) {
						for ( var i in expands) {
							if (index == expands[i]) {
								this.parentNode.parentNode.parentNode.parentNode.parentNode.grid
										.doExpand(this);
							}
						}

					});
}

function refreshAfterDel(id) {
    $.ajax({
        url : 'planController.do?getParentProcess&planId='+id,
        type : 'get',
        cache : false,
        success : function(data) {
            var d = $.parseJSON(data);
            if (d.success) {
                var map = d.attributes;
                $.each(map,function(key, value){
                    $('#'+key+'').text(value + "%");
                    $('#'+key+'').width(value + "%");
                });
            }
        }
    });
}

</script>
<%--    <style type="text/css">

        #mm{
            position: absolute;
            background: #fff;
            margin-left: 160px;}
    </style>--%>
</head>
<body>
<div>

		<%
			String header = request.getAttribute("headerShow")==null?"":request.getAttribute("headerShow").toString();
			/* header = "编号,进度,操作,计划名称,计划等级,计划类型,计划类别,状态,负责人,开始时间,结束时间,下达人,下达时间,工期(天),前置计划,里程碑,创建者,创建时间"; */
			request.setAttribute("test", header);
			String columnIds = request.getAttribute("columnIds")==null?"":request.getAttribute("columnIds").toString();
			/* columnIds = "planNumber,progressRate,optBtn,planName,planLevelInfo,taskNameTypeDisplay,taskType,bizCurrentInfo,ownerInfo,planStartTime,planEndTime,assignerInfo,assignTime,workTime,preposePlans,milestone,creator,createTime"; */
			request.setAttribute("columnIds", columnIds);
			String mygridHeight = request.getAttribute("mygridHeight")==null?"":request.getAttribute("mygridHeight").toString();
			request.setAttribute("mygridHeight", mygridHeight);
		%>


		<fd:lazyLoadingTreeGrid id="mygrid"
				url="planController.do?list&projectId=${projectId}&isModify=${isModify}&planRevocationOperationCode=${planRevocationOperationCode}&planModifyOperationCode=${planModifyOperationCode}&planAssignOperationCode=${planAssignOperationCode}&planDeleteOperationCode=${planDeleteOperationCode}&planChangeOperationCode=${planChangeOperationCode}&planDiscardOperationCode=${planDiscardOperationCode}&isPmo=${isPmo}&isProjectManger=${isProjectManger}&planConcernOperationCode=${planConcernOperationCode}&planUnconcernOperationCode=${planUnconcernOperationCode}"
                width="99%" height="${mygridHeight}" imgUrl="plug-in/dhtmlxSuite/imgs/"
				initWidths="52,80,120,230,100,100,80,80,120,80,80,120,80,80,120,80,120,95"
				columnIds="${columnIds}"
				header="${test}"
				columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;']"
				colAlign="left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left"
				colSortings="na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na"
				colTypes="ro,ro,ro,tree,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
				enableTreeGridLines="true" enableLoadingStatus="false"
				onRightClick="onRightClick">
		</fd:lazyLoadingTreeGrid>
		<input type = "hidden" id = "isPlanAddPlanAfter" name = "isPlanAddPlanAfter" value = "${isPlanAddPlanAfter}" />
		<input type = "hidden" id = "isPlanInsert" name = "isPlanInsert" value = "${isPlanInsert}" />


    <div id="menusEditing" class="easyui-menu" style="width:200px;">
        <c:if test="${isViewPage!='true'}">
            <div id="viewTask" data-options="iconCls:'basis ui-icon-eye'" onclick="viewPlanTask_()"><spring:message code="com.glaway.ids.pm.project.plan.viewTaskInfo"/></div>
        </c:if>
        <input id="pId" name="pId" type="hidden" class="inputxt">
        <div id="addSubPlan" data-options="iconCls:'basis ui-icon-plus'" onclick="addSubPlan_()"><spring:message code="com.glaway.ids.pm.project.plan.addSonPlan"/></div>
        <div id = "importSubPlan" data-options="iconCls:'basis ui-icon-import'">
            <span><spring:message code="com.glaway.ids.pm.project.plan.morePlanImportOpera"/></span>
            <div style="width:150px;">
                <div id="importMppSubPlan" data-options="iconCls:'basis ui-icon-import'" onclick="importSubPlan_('mpp')"><spring:message code="com.glaway.ids.pm.plan.mppImport"/></div>
                <div id="imporExcelSubPlan" data-options="iconCls:'basis ui-icon-import'" onclick="importSubPlan_('excel')"><spring:message code="com.glaway.ids.pm.plan.excelImport"/></div>
                <div id="importTempSubPlan" data-options="iconCls:'basis ui-icon-import'" onclick="importSubPlan_('template')"><spring:message code="com.glaway.ids.pm.plan.templateImport"/></div>
                <div id="importRdFlowSubPlan" data-options="iconCls:'basis ui-icon-import'" onclick="importSubPlan_('rdFlow')"><spring:message code="com.glaway.ids.pm.plan.rdflowImport"/></div>
            </div>
        </div>
        <div id="addPlanAfter" data-options="iconCls:'basis ui-icon-plus'" onclick="addPlanAfter_()">在下方插入计划</div>
        <div id = "insertPlan_" data-options="iconCls:'basis ui-icon-import'">
            <span><spring:message code="com.glaway.ids.pm.plan.addPlanAfter"/></span>
            <div style="width:150px;">
                <div id="insertMppPlan" data-options="iconCls:'basis ui-icon-import'" onclick="insertPlan_('mpp')"><spring:message code="com.glaway.ids.pm.plan.mppImport"/></div>
                <div id="insertExcelPlan" data-options="iconCls:'basis ui-icon-import'" onclick="insertPlan_('excel')"><spring:message code="com.glaway.ids.pm.plan.excelImport"/></div>
                <div id="insertTempPlan" data-options="iconCls:'basis ui-icon-import'" onclick="insertPlan_('template')"><spring:message code="com.glaway.ids.pm.plan.templateImport"/></div>
                <%-- <div id="insertRdFlowPlan" data-options="iconCls:'basis ui-icon-import'" onclick="insertPlan_('rdFlow')"><spring:message code="com.glaway.ids.pm.plan.rdflowImport"/></div> --%>
            </div>
        </div>
        <div id="saveAsPlanTemplate" data-options="iconCls:'basis ui-icon-save'" onclick="saveAsPlanTemplate_('right')"><spring:message code="com.glaway.ids.pm.project.plan.saveAsPlanTemplate"/></div>
        <div id="exportPlan" data-options="iconCls:'basis ui-icon-export'">
            <span data-options="iconCls:'basis ui-icon-export'"><spring:message code="com.glaway.ids.pm.plan.exportPlan"/></span>
            <div style="width:150px;">
                <div id="exportPlanMpp" data-options="iconCls:'basis ui-icon-export'" onclick="exportPlanMppSingle_()"><spring:message code="com.glaway.ids.pm.plan.mppExport"/></div>
                <div id="exportPlanExcel" data-options="iconCls:'basis ui-icon-export'" onclick="exportPlanExcelSingle_()"><spring:message code="com.glaway.ids.pm.plan.excelExport"/></div>
            </div>
        </div>
        <div id="addBomPlan" data-options="iconCls:'basis ui-icon-import'" onclick="addBomAfter_()">调用BOM</div>
    </div>

    <%--<fd:menu id="menusEditing">
		<c:if test="${isViewPage!='true'}">
			<fd:menuitem text="{com.glaway.ids.pm.project.plan.viewTaskInfo}" onclick="viewPlanTask_()"
				iconCls="basis ui-icon-eye" id="viewTask"
				operationCode="viewPlanTask_" />
		</c:if>
		<input id="pId" name="pId" type="hidden" class="inputxt">
		<fd:menuitem text="{com.glaway.ids.pm.project.plan.addSonPlan}" onclick="addSubPlan_()"
			iconCls="basis ui-icon-plus" id="addSubPlan"
			operationCode="planAddSubPlan_" />
		<fd:menuitem id="importSubPlan" iconCls="basis ui-icon-import" text="{com.glaway.ids.pm.project.plan.morePlanImportOpera}"
			 >

            <div id="mm" class="easyui-menu" >

                <div>
                    <span>Open</span>
                    <div style="width:150px;">
                        <fd:linkbutton value="{com.glaway.ids.pm.project.plan.importSubPlanofmpp}" onclick="importSubPlan_('mpp')" iconCls="basis ui-icon-import" id="importMppSubPlan"
                                       operationCode="planImportSubPlan_" />
                        <fd:linkbutton value="{com.glaway.ids.pm.project.plan.importSubPlanofExcel}" onclick="importSubPlan_('excel')"
                                       iconCls="basis ui-icon-import" id="imporExcelSubPlan"
                                       operationCode="planImportSubPlan_" />
                        <fd:linkbutton value="{com.glaway.ids.pm.project.plan.importSubPlanoftem}" onclick="importSubPlan_('template')"
                                       iconCls="basis ui-icon-import" id="importTempSubPlan"
                                       operationCode="planImportSubPlan_" />
                    </div>
                </div>


            </div>

           &lt;%&ndash; <a href="javascript:void(0);" class="easyui-menubutton"
			   data-options="hasDownArrow:false" style="height: 20px; padding-top: 0.5px;margin-top: -1px;" menu="#morePlanImportMenu">
				<spring:message code="com.glaway.ids.pm.project.plan.morePlanImportOpera"/>
			</a>&ndash;%&gt;
		</fd:menuitem>
		&lt;%&ndash;<div id="morePlanImportMenu" style="width: 150px;padding-left: 10px;">
			<fd:linkbutton value="{com.glaway.ids.pm.project.plan.importSubPlanofmpp}" onclick="importSubPlan_('mpp')" iconCls="basis ui-icon-import" id="importMppSubPlan"
						 operationCode="planImportSubPlan_" />
			<fd:linkbutton value="{com.glaway.ids.pm.project.plan.importSubPlanofExcel}" onclick="importSubPlan_('excel')"
						 iconCls="basis ui-icon-import" id="imporExcelSubPlan"
						 operationCode="planImportSubPlan_" />
			<fd:linkbutton value="{com.glaway.ids.pm.project.plan.importSubPlanoftem}" onclick="importSubPlan_('template')"
						 iconCls="basis ui-icon-import" id="importTempSubPlan"
						 operationCode="planImportSubPlan_" />
		</div>&ndash;%&gt;
		&lt;%&ndash;<fd:menuitem text="{com.glaway.ids.pm.project.plan.importSubPlanofmpp}" onclick="importSubPlan_('mpp')"
			iconCls="basis ui-icon-import" id="importMppSubPlan"
			operationCode="planImportSubPlan_" />
		<fd:menuitem text="{com.glaway.ids.pm.project.plan.importSubPlanofExcel}" onclick="importSubPlan_('excel')"
			iconCls="basis ui-icon-import" id="imporExcelSubPlan"
			operationCode="planImportSubPlan_" />
		<fd:menuitem text="{com.glaway.ids.pm.project.plan.importSubPlanoftem}" onclick="importSubPlan_('template')"
			iconCls="basis ui-icon-import" id="importTempSubPlan"
			operationCode="planImportSubPlan_" />&ndash;%&gt;
		<fd:menuitem text="在下方插入计划（手工）" onclick="addPlanAfter_()"
			iconCls="basis ui-icon-plus" id="addPlanAfter"
			operationCode="planAddPlanAfter_" />
		<fd:menuitem text="{com.glaway.ids.pm.project.plan.insertPlanofmpp}" onclick="insertPlan_('mpp')"
			iconCls="basis ui-icon-import" id="insertMppPlan"
			operationCode="planInsert_" />
		<fd:menuitem text="{com.glaway.ids.pm.project.plan.insertPlanofExcel}" onclick="insertPlan_('excel')"
			iconCls="basis ui-icon-import" id="insertExcelPlan"
			operationCode="planInsert_" />
		<fd:menuitem text="{com.glaway.ids.pm.project.plan.insertPlanoftem}" onclick="insertPlan_('template')"
			iconCls="basis ui-icon-import" id="insertTempPlan"
			operationCode="planInsert_" />
		<fd:menuitem text="{com.glaway.ids.pm.project.plan.saveAsPlanTemplate}" onclick="saveAsPlanTemplate_('right')"
			iconCls="basis ui-icon-save" id="saveAsPlanTemplate"
			operationCode="planSaveAsPlanTemplate_" />
		<fd:menuitem text="{com.glaway.ids.pm.project.plan.exportMppPlan}" onclick="exportPlanMppSingle_()"
			iconCls="basis ui-icon-export" id="exportPlanMpp"
			operationCode="planExportPlanMppForMore_"/>
		<fd:menuitem text="{com.glaway.ids.pm.project.plan.exportExcelPlan}" onclick="exportPlanExcelSingle_()"
			iconCls="basis ui-icon-export" id="exportPlanExcel"
			operationCode="planExportPlanMppForMore_"/>
	</fd:menu>--%>
	<fd:menu id="subPlanMenusEditing">
		<fd:menuitem text="{com.glaway.ids.pm.project.plan.importSubPlanofmpp}" onclick="importSubPlan_('mpp')"
			iconCls="basis ui-icon-import" id="importMppSubPlan"
			operationCode="planImportSubPlan_" />
		<fd:menuitem text="{com.glaway.ids.pm.project.plan.importSubPlanofExcel}" onclick="importSubPlan_('excel')"
			iconCls="basis ui-icon-import" id="imporExcelSubPlan"
			operationCode="planImportSubPlan_" />
		<fd:menuitem text="{com.glaway.ids.pm.project.plan.importSubPlanoftem}" onclick="importSubPlan_('template')"
			iconCls="basis ui-icon-import" id="importTempSubPlan"
			operationCode="planImportSubPlan_" />
	</fd:menu>
</div>


</body>
</html>
