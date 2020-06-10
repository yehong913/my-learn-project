<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>计划列表</title>
	<meta http-equiv="X-UA-Compatible" content="edge"></meta>
	<script src="webpage/com/glaway/ids/pm/project/plan/planList.js"></script>
	<style type="text/css">
		.col_width{width:150px;}
		.col_width2{width:200px;}
		.col_width3{width:110px;}
		.col_width4{width:80px;}
		.search_title_box2{line-height:34px;}
		.glaway-input2{positon:relative; top:-2px}
		.qhview{display:inline-block; height:30px; line-height:30px;}
		.qhview .search_title{margin-top:0px}
		.col_set{display:inline-block; float:left;margin:0px; top:0px;padding-top:3px;}
		.button_size{padding:1px 10px; filter:none}
		.gla_div{
			position: relative;

			float: right;}
		.gla_div a.button_size{height:18px;height:20px !important; width:80px; line-height:19px; vertical-align:center;padding:2px 10px; border-radius:2px ; display:block; overflow:visible;
			position: absolute;
			right: 6px; top:-49px
		}
		.gla_div a.button_size:hover{height:18px;height:20px !important; width:80px; line-height:19px; vertical-align:center;padding:2px 10px; border-radius:2px ;display:block; border:none}
		.l-btn-text{margin:0}
		.l-btn-left{margin-top:-3px}
		.l-btn{border-radius:0}
		.easyui-menubutton{border:#fff solid 1px;padding: 2px}
		.easyui-menubutton:hover{padding:2px;}
	</style>
</head>
<body style="overflow: hidden;">
<div >

	<div style="padding: 1px; width: 100%;" title="" id="left_page_panel">
		<div style=" right:25px;top:11px; position: absolute; z-index: 20;"><fd:helpButton help="helpDoc:PlanList"/></div>
		<div id="searchCondition_" class="easyui-panel" title="查询条件"
			 style="clear: both; width: 100%; margin: 10px; background: #fafafa;"
			 data-options="collapsed:true,
                collapsible:true,onExpand:function(){expandPanel();},onCollapse:function(){collpasePanel();}">
			<fd:searchform id="seachPlanTag" onClickSearchBtn="searchPlan()"
						   onClickResetBtn="tagSearchReset()" isMoreShow="false">
				<fd:inputText title="{com.glaway.ids.pm.project.plan.planName}"
							  name="Plan.planName" id="searchPlanName" value="${planName}"
							  queryMode="like" />
				<fd:combobox id="searchIsDelay" textField="name" title="是否延期"
							 name="Plan.isDelay" editable="false" valueField="id"
							 value="${isDelay}" url="planController.do?planIsDelayList"
							 queryMode="in" multiple="true" prompt="全部" />
				<fd:combobox id="searchPlanLevel" textField="name"
							 title="{com.glaway.ids.pm.project.plan.planLevel}"
							 name="Plan.planLevelInfo.id" editable="false" valueField="id"
							 value="${planLevel}" url="planController.do?useablePlanLevelList"
							 queryMode="in" multiple="true" prompt="全部" />
				<fd:combobox id="searchBizCurrent" textField="title"
							 title="{com.glaway.ids.common.lable.status}"
							 name="Plan.bizCurrent" editable="false" valueField="name"
							 value="${bizCurrent}" prompt="全部"
							 url="planController.do?statusList" queryMode="in" multiple="true" />
				<fd:inputText title="编号" name="Plan.planNumber"
							  value="${planNumber}" id="searchPlanNumber" queryMode="like" />
				<fd:inputText title="{com.glaway.ids.common.lable.owner}"
							  id="searchOwner" value="${owner}" />
				<fd:inputDateRange id="planDateRange" interval="0"
								   title="{com.glaway.ids.common.lable.starttime}"
								   name="Plan.planStartTime" opened="0" />
				<fd:inputDateRange id="planEndDateRange" interval="0"
								   title="{com.glaway.ids.common.lable.endtime}"
								   name="Plan.planEndTime" opened="0" />
				<fd:inputNumber title="{com.glaway.ids.pm.project.plan.workTime}"
								name="Plan.workTime" value="${workTime}" id="searchWorkTime"
								queryMode="le&&ge" />
				<fd:inputNumber title="{com.glaway.ids.common.lable.progress}"
								name="Plan.progressRate" value="${progressRate}"
								id="searchProgressRate" queryMode="le&&ge" />
				<fd:combobox id="searchTaskNameType" textField="name"
							 title="{com.glaway.ids.pm.project.plan.taskNameType}"
							 name="Plan.taskNameType" editable="false" value="${taskNameType}"
							 valueField="id" url="planController.do?getTaskNameTypes"
							 queryMode="in" multiple="true" prompt="全部" />
				<fd:combobox id="searchTaskType" textField="name"
							 title="{com.glaway.ids.pm.project.plan.taskType}"
							 value="${taskType}" name="Plan.taskType" editable="false"
							 valueField="name" url="planController.do?taskTypeList"
							 queryMode="in" multiple="true" prompt="全部" />

			</fd:searchform>
			<div class="gla_div"
				 style="position: relative; margin-right: 119px; margin-top: 15px; ">
				<fd:linkbutton classStyle="button_size" value="视图中查询"
							   onclick="viewSearchPlanList();"></fd:linkbutton>
			</div>
		</div>
		<c:if test="${ viewPlan != 'view'  }">
			<fd:toolbar id="toolbar">

				<div style=" height:34px; line-height:34px;clear:both;  position:relative;">
					<fd:toolbarGroup align="left">
						<c:if test="${ projectBizCurrent != 'PAUSED' &&  projectBizCurrent != 'CLOSED'}">
							<c:if test="${ viewPlan != 'view'  }">
								<a href="javascript:void(0);" class="easyui-menubutton"   menu="#morePlanMenu" style="float:left; margin-left:8px; margin-top:6px;">
									<i class="basis ui-icon-plus" style="width:16px; height:16px; overflow: hidden; display:inline-block; vertical-align:middle; margin-right:3px;"></i><spring:message code="com.glaway.ids.pm.project.plan.resource.addPlan"/>
								</a>
								<div id="morePlanMenu" style="width: 130px;">
									<fd:linkbutton id="addPlanBtn" onclick="addPlan()" value="手工增加" operationCode="planAdd"
												   iconCls="basis ui-icon-pencil" />
									<fd:linkbutton id="importMPPPlanBtn" onclick="importPlan('mpp')" operationCode="planImport"
												   value="{com.glaway.ids.pm.project.plan.mppImport}" iconCls="basis ui-icon-import"/>
									<fd:linkbutton id="importExcelPlanBtn" onclick="importPlan('excel')" operationCode="planImport"
												   value="{com.glaway.ids.pm.project.plan.excelImport}" iconCls="basis ui-icon-import"/>
									<fd:linkbutton id="importTempPlanBtn"
												   onclick="importPlan('template')" value="{com.glaway.ids.pm.project.plan.templateImport}"
												   iconCls="basis ui-icon-import" operationCode="planImport" />
									<fd:linkbutton id="addBom"
												   onclick="selectBomPlan()" value="调用BOM"
												   iconCls="basis ui-icon-import"/>
								</div>
							</c:if>
						</c:if>
						<fd:linkbutton id="assignPlanBtn" onclick="assignPlan()" operationCode="planMultiAssign"
									   value="{com.glaway.ids.pm.project.plan.assignPlan}" iconCls="basis ui-icon-issue"
						/>
						<c:choose>
							<c:when test="${empty param.kddProductType}">
								<fd:linkbutton id="viewGanTTBtn" onclick="viewGanTT()" value="{com.glaway.ids.pm.project.plan.gantePicture}"
											   iconCls="basis ui-icon-ganttchart" />
								<fd:linkbutton id="viewGraph" onclick="viewGraph()" value="{com.glaway.ids.pm.project.plan.netPicture}"
											   iconCls="basis ui-icon-intersectionchart" />
							</c:when>
						</c:choose>
						<c:if test="${ viewPlan != 'view'  }">
							<a href="javascript:void(0);" class="easyui-menubutton" style="padding-top: 1px;padding-left:2px;margin-top: 2px;" menu="#moreMenu">
								<spring:message code="com.glaway.ids.pm.project.plan.moreOpera"/>
							</a>
						</c:if>
						<div id="moreMenu" style="width: 150px;">
							<c:choose>
								<c:when test="${empty param.kddProductType}">
									<fd:linkbutton id="manageBasicLine" onclick="manageBasicLine()"
												   value="{com.glaway.ids.pm.project.plan.basicLine.basicManager}" iconCls="basis ui-icon-baselinemanagement"
												   operationCode="basicLineManager" />
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
							<c:if test="${ viewPlan != 'view'  }">
								<fd:linkbutton id="multiModifyPlan" onclick="multiModifyPlan()" operationCode="planMultiModifyPlan"
											   value="{com.glaway.ids.pm.project.plan.massModify}" iconCls="basis ui-icon-pencil"/>

								<fd:linkbutton id="multiChangePlan" onclick="multiChangePlan()" operationCode="planMultiChangePlan"
											   value="{com.glaway.ids.pm.project.plan.massChange}" iconCls="basis ui-icon-planchange"/>
								<fd:linkbutton id="saveAsPlanTemplate2"
											   onclick="saveAsPlanTemplate('main')" value="{com.glaway.ids.pm.project.plan.saveAsPlanTemplate}"
											   iconCls="basis ui-icon-save"
											   operationCode="plansaveAsPlanTemplateForMore" />
								<fd:linkbutton id="exportPlanMpp2" onclick="exportPlanMpp()"
											   value="{com.glaway.ids.pm.project.plan.exportMppPlan}" iconCls="basis ui-icon-export"
											   operationCode="planExportPlanMppForMore" />
								<fd:linkbutton id="exportPlanExcel2" onclick="exportPlanExcel()"
											   value="{com.glaway.ids.pm.project.plan.exportExcelPlan}" iconCls="basis ui-icon-export"
											   operationCode="planExportPlanMppForMore" />
							</c:if>
						</div>
					</fd:toolbarGroup>

					<c:if test="${ viewPlan != 'view'  }">
                <span style=" float:right; height:30px; margin-right:0px; margin-top:0; right:4px;  top:-1px; position:absolute; display:inline-block">
				<fd:toolbarGroup >
					<span style="display:inline-block; margin:2px 12px; width:80px; float:left; text-align:right">列设置</span>
					<span class="col_set" >
				        <input type="hidden" id="aaa" />
					<select id="planListShowColumn" style="width:90px; height:32px;"></select>
					<div id="sp" style = "display:none">
						<div style="margin-top:3px;">
							<div><input type="checkbox" name="lang" value="planNo" id="planNo"><label for="planNo">编号</label></div>
							<div><input type="checkbox" name="lang" value="planLevel" id="planLevel"><label for="planLevel">计划等级</label><br/></div>
							<div><input type="checkbox" name="lang" value="planType" id="planType"><label for="planType">计划类型</label><br/></div>
							<div><input type="checkbox" name="lang" value="planTaskType" id="planTaskType"><label for="planTaskType">计划类别</label><br/></div>
							<div><input type="checkbox" name="lang" value="status" id="status"><label for="status">状态</label><br/></div>
							<div><input type="checkbox" name="lang" value="owner" id="owner"><label for="owner">负责人</label><br/></div>
							<div><input type="checkbox" name="lang" value="planStartTime" id="planStartTime"><label for="planStartTime">开始时间</label><br/></div>
							<div><input type="checkbox" name="lang" value="planEndTime" id="planEndTime"><label for="planEndTime">结束时间</label><br/></div>
							<div><input type="checkbox" name="lang" value="assigner" id="assigner"><label for="assigner">发布人</label><br/></div>
							<div><input type="checkbox" name="lang" value="assignTime" id="assignTime"><label for="assignTime">发布时间</label><br/></div>
							<div><input type="checkbox" name="lang" value="workTime" id="workTime"><label for="workTime">工期(天)</label><br/></div>
							<div><input type="checkbox" name="lang" value="preposePlan" id="preposePlan"><label for="preposePlan">前置计划</label><br/></div>
							<div><input type="checkbox" name="lang" value="mileStone" id="mileStone"><label for="mileStone">里程碑</label><br/></div>
							<div><input type="checkbox" name="lang" value="creator" id="creator"><label for="creator">创建者</label><br/></div>
							<div><input type="checkbox" name="lang" value="createTime" id="createTime"><label for="createTime">创建时间</label><br/></div>
						</div>
					</div>
					</span>
					<span class="qhview">
					     <fd:combotree url="planViewController.do?list&projectId=${projectId}" required="false"
									   lazy="true" lazyUrl="planViewController.do?list&projectId=${projectId}"
									   treePidKey="pid" id="switchPlanView" treeIdKey="id" editable="false"  value=""
									   treeName="name" title="{com.glaway.ids.pm.project.planview.switch}" onChange="switchPlanList"
									   onHidePanel="viewChange" panelHeight="200px" classStyle="col_width3" spanCss="col_width2" />
                        <input id="oldTreeName" value="${planViewInfoName}" type="hidden" />
                        <input id="oldTreeId" value="${planViewInfoId}" type="hidden" />
					</span>
					<span class="qhview">
					    <fd:combobox id="setPlanView" textField="name" title="{com.glaway.ids.pm.project.planview.setting}"
									 name="setPlanView" editable="false" valueField="id"
									 url="planViewController.do?setPlanViewList" queryMode="in" multiple="false"
									 prompt="" onChange="alertList"  classStyle="col_width4" spanCss="col_width2" style="width:80px"/>
	                </span>
				</fd:toolbarGroup>
			</span>
					</c:if>
				</div>

			</fd:toolbar>
		</c:if>
	</div>

	<div id="datagridDiv" style="width:100%; clear:both;">

		<div style="" title="&nbsp;" fit="true" border="false"


			 id="right_page_panel_plan">

		</div>
	</div>
	<input id="isPlanAddPlanAfter" name="isPlanAddPlanAfter" type="hidden" value="${isPlanAddPlanAfter}" />
	<input id="isPlanInsert" name="isPlanInsert" type="hidden" value="${isPlanInsert}" />
	<input id="projectId" name="projectId" type="hidden" value="${projectId}" />
	<input id="projectBizCurrent" name="projectBizCurrent" type="hidden" value="${projectBizCurrent}" />
	<input id="isModify" name="isModify" type="hidden" value="${isModify}" />
	<input id="currentUserId" name="currentUserId" type="hidden" value="${currentUserId}" />
	<input id="isProjectManger" name="isProjectManger" type="hidden" value="${isProjectManger}" />
	<input id="isPmo" name="isPmo" type="hidden" value="${isPmo}" />
	<input id="warningDay" name="warningDay" type="hidden" value="${warningDay}" />
	<input id="warningDayFlag" name="warningDayFlag" type="hidden" value="${warningDayFlag}" />
	<input id="currentDate" name="currentDate" type="hidden" value="${currentDate}" />
	<input id="webRoot" name="webRoot" type="hidden" value="${webRoot}" />

	<fd:form id="importPlanForm_"
			 url="planController.do?savePlanByPlanTemplateId&planId=${plan_.parentPlanId}&projectId=${projectId}&type=${type}&realPlanId=${plan_.id}">
		<input id="planTemplateId" name="planTemplateId" type="hidden" />
		<input id="planTemplateName" name="planTemplateName" type="hidden" />
	</fd:form>
</div>

<fd:dialog id="addPlanDialog" width="880px" height="650px" max="false"
		   modal="true" title="{com.glaway.ids.pm.project.plan.resource.addPlan}">
</fd:dialog>
<fd:dialog id="addPlanForGeneralPageDialog" width="880px" height="650px" max="false"
		   modal="true" title="{com.glaway.ids.pm.project.plan.resource.addPlan}">
</fd:dialog>
<fd:dialog id="addViewTimeDialog" width="750px" height="400px"
		   modal="true" title="{com.glaway.ids.pm.project.planView.savaByTime}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="commitViewDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="saveAsViewDialog" width="550px" height="130px" modal="true" title="{com.glaway.ids.pm.project.planView.savaAsView}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="saveViewDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="addSubPlanDialog" width="880px" height="650px" max="false"
		   modal="true" title="{com.glaway.ids.pm.project.plan.addSonPlan}">
</fd:dialog>
<fd:dialog id="addPlanAfterDialog" width="880px" height="650px" max="false"
		   modal="true" title="{com.glaway.ids.pm.project.plan.addPlanAfterIn}">
</fd:dialog>
<fd:dialog id="modifyPlanDialog" width="880px" height="650px" max="false"
		   modal="true" title="{com.glaway.ids.pm.project.plan.resource.modifyPlan}">
</fd:dialog>
<fd:dialog id="multiModifyDialog" width="850px" height="500px"
		   modal="true" title="{com.glaway.ids.pm.project.plan.massModify}">
</fd:dialog>
<fd:dialog id="viewPlanDialog" width="750px" height="530px" modal="true" title="{com.glaway.ids.pm.project.plan.viewPlan}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="importMppDialog" width="400px" height="150px"
		   modal="true" title="{com.glaway.ids.pm.project.plan.mppImport}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="importPlanTem"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="importDialog2" width="400px" height="200px"
		   modal="true" title="{com.glaway.ids.pm.project.plan.importPlan}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="importDialog2"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="assignPlanDialog" width="400px" height="150px" modal="true"  title="{com.glaway.ids.pm.project.plan.assignPlan}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="assignPlanDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="assignMassDialog" width="750px" height="500px" modal="true" max="false" title="{com.glaway.ids.pm.project.plan.assignPlan}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="assignMassDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="changePlanDialog" width="880px" height="650px"
		   modal="true" title="{com.glaway.ids.pm.project.plan.resource.changePlan}">
</fd:dialog>
<fd:dialog id="multiChangeDialog" width="780px" height="600px"
		   modal="true" title="{com.glaway.ids.pm.project.plan.massChange}">
</fd:dialog>

<fd:dialog id="viewPlanTaskDialog" width="690px" height="500px"
		   modal="true" title="{com.glaway.ids.pm.project.plan.viewTaskInfo}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="inheritDialog" width="400px" height="300px"
		   modal="true" title="{com.glaway.ids.pm.project.plan.inheritParentDocument}">
</fd:dialog>
<fd:dialog id="saveAsPlanTemplateDia" width="820px" height="400px"
		   title="{com.glaway.ids.pm.project.plan.saveAsPlanTemplate}">
	<fd:dialogbutton name="{com.glaway.ids.pm.project.plan.saveAndSubmit}" callback="saveAndSub"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="savePlanName"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="saveAsPlanTemplateDiaAll" width="950px" height="600px"
		   title="{com.glaway.ids.pm.project.plan.saveAsPlanTemplate}">
	<fd:dialogbutton name="{com.glaway.ids.pm.project.plan.saveAndSubmit}" callback="saveAllAndSub"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="saveAllPlanName"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="goSubmit" width="400px" height="180px" modal="true"
		   title="{com.glaway.ids.pm.project.plan.toApprove}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="goSubmitPlan"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="planImportSearchDialog" width="730px" height="400px"
		   modal="true" title="{com.glaway.ids.pm.project.plan.planTemplateList}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="importSubmit"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="subPlanImportSearchDialog" width="730px" height="400px"
		   modal="true" title="{com.glaway.ids.pm.project.plan.planTemplateList}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="importSubSubmit"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="allPlanImportSearchDialog" width="730px" height="400px"
		   modal="true" title="{com.glaway.ids.pm.project.plan.planTemplateList}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="importAllSubmit"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="ganttDialog" width="800px" height="600px"
		   modal="true" title="{com.glaway.ids.pm.project.plan.gantePicture}" max="true" maxFun="true">
</fd:dialog>
<fd:dialog id="networkGraphDialog" width="800px" height="600px"
		   modal="true" title="{com.glaway.ids.pm.project.plan.netPicture}" max="true" maxFun="true">
</fd:dialog>
<fd:dialog id="setByDepartmentDialog" width="800px" height="400px"
		   modal="true" title="{com.glaway.ids.pm.project.planview.setting.department}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="setByDepartment"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="addCustomPlanViewDialog" width="1200px" height="600px"
		   modal="true" title="{com.glaway.ids.pm.project.planView.customView}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="customPlanViewConfirm"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="viewManagementDialog" width="1100px" height="400px"
		   modal="true" title="{com.glaway.ids.pm.project.planview.management}">
</fd:dialog>
<fd:dialog id="importExcelDialog" width="400px" height="150px"
		   modal="true" title="{com.glaway.ids.pm.project.plan.excelImport2}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="importPlanExcel"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="bomDialog" width="800px" height="580px" modal="true" title="选择部件">
	<fd:dialogbutton name="下一步" callback="selectBomNodes"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="bomNodeDialog" width="680px" height="480px" modal="true" title="选择BOM节点">
	<fd:dialogbutton name="上一步" callback="openSelectPart"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="sumbitBom"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="taskFlowResolve" width="780px" height="500px" modal="true" title="{com.glaway.ids.pm.project.task.taskdetail.flowResolve}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="doFlowResolve"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="editFlowTaskTd" width="1440px" height="600px"  beforClose="planListSearch()"
		   modal="true" title="{com.glaway.ids.pm.project.task.taskdetail.editFlowTask}" max="false" min="false" maxFun="true">
</fd:dialog>
<!-- 子页面的填出窗口 -->
<fd:dialog id="editFlowTaskFr" width="1440px" height="600px"
		   modal="true" title="{com.glaway.ids.pm.project.task.taskdetail.editFlowTask}" max="false" min="false" maxFun="true"
		   beforClose="reloadAndCloseAll()">
</fd:dialog>
<fd:dialog id="editFlowTaskFr2" width="1440px" height="600px"
		   modal="true" title="{com.glaway.ids.pm.project.task.taskdetail.editFlowTask}" max="false" min="false" maxFun="true"
		   beforClose="reloadAndCloseAll()">
</fd:dialog>


<script type="text/javascript">
	var showColumnIds = [];
	var showSearchFormFlag = 0;

	var dom = "";

	var refFlag = false;

	var mygridHeight = "";

	var bizCurrent = '';
	var planLevel = '';
	var workTime_condition = '';
	var progressRate_condition = '';
	var taskNameType = '';
	var taskType = '';
	var isDelay = '';
	var userName = '';
	var progressRate = '';
	var workTime = '';
	var planNumber = '';
	var planName = '';
	var planStartTime = '';
	var planEndTime = '';


	$(document).ready(function() {

		if ('${refreshTree}' == 'true') { // 非审批页面进入才刷新右侧树
			try{
				parent.loadTree("${projectId}", 1, "plan");

			}catch(e){

			}
		}

		if ('${planAdd}' == 'true') {
			$('#addPlanBtn').show();
		}


		if ('${planImport}' == 'true') {
			$('#importMPPPlanBtn').show();
			$('#importTempPlanBtn').show();
		}


		if ('${planMultiAssign}' == 'true') {
			$('#assignPlanBtn').show();
		}

		if ('${planMultiAssign}' == 'true') {
			$('#assignPlanBtn').show();
		}

		if ('true' != $("#isModify").val()) {
			$("#addPlanBtn").attr("style", "display:none;");
			$("#importMPPPlanBtn").attr("style", "display:none;");
			$("#importTempPlanBtn").attr("style", "display:none;");
			$("#assignPlanBtn").attr("style", "display:none;");
			$("#multiModifyPlan").attr("style", "display:none;");
			$("#multiChangePlan").attr("style", "display:none;");
			$("#manageBasicLine").attr("style", "display:none;");
		} else {
			if ('EDITING' == $("#projectBizCurrent").val()) {
				if ('true' == $("#isPmo").val()) {
					$("#assignPlanBtn").attr("style", "display:none;");
					$("#multiChangePlan")
							.attr("style", "display:none;");
				} else {

					$("#assignPlanBtn").attr("style", "display:none;");

					$("#multiChangePlan")
							.attr("style", "display:none;");

					$("#manageBasicLine")
							.attr("style", "display:none;");
				}
			}
		}

		if ($('#projectBizCurrent').val() == 'CLOSED'
				|| $('#projectBizCurrent').val() == 'PAUSED') {
			$("#searchBizCurrent").combobox({
				readonly : true
			});
		}



		setTimeout(function(){


			var documentHeight;
			//	var documentHeight1 = $(window).height();
			try {
				documentHeight=parent.$("#tabiframe").height();
			}catch (e) {

			}


			/*alert(documentHeight1);
            var documentHeight2 = document.body.clientHeight;
            if(documentHeight1 > documentHeight2){
                documentHeight = documentHeight2;
            }else{
                documentHeight = documentHeight1;
            }*/


			var obj = document.getElementById("left_page_panel");
			var northDivHeight = obj.offsetHeight;
			$("#datagridDiv").css({'height':documentHeight-northDivHeight+'px'});
			mygridHeight = documentHeight-northDivHeight - 30+'px';

			var loadUrl = '/ids-pm-web/planController.do?goDatagridPage&isIframe=true&mygridHeight='+mygridHeight+'&isViewPage=${isViewPage}&header=${headerShow}&columnIds=${columnIds}&projectId=${projectId}&isModify=${isModify}&planRevocationOperationCode=${planRevocationOperationCode}&planModifyOperationCode=${planModifyOperationCode}&planAssignOperationCode=${planAssignOperationCode}&planDeleteOperationCode=${planDeleteOperationCode}&planChangeOperationCode=${planChangeOperationCode}&planDiscardOperationCode=${planDiscardOperationCode}&isPmo=${isPmo}&isProjectManger=${isProjectManger}&planConcernOperationCode=${planConcernOperationCode}&planUnconcernOperationCode=${planUnconcernOperationCode}';
			try{
				loadPage("#right_page_panel_plan", loadUrl + '&refreshTree=true', "");
			}catch (e) {

			}
			refFlag = true;
			$("#planDateRange_BeginDate").datebox("setValue",'${planStartTime1}');
			$("#planDateRange_EndDate").datebox("setValue",'${planStartTime2}');
			$("#planEndDateRange_BeginDate").datebox("setValue",'${planEndTime1}');
			$("#planEndDateRange_EndDate").datebox("setValue",'${planEndTime2}');
			$('#glaway_input_readonly_searchWorkTime select').combobox('setValue', '${workTimeCondition}');
			$('#glaway_input_readonly_searchProgressRate select').combobox('setValue', '${progressRateCondition}');
			try{
				$('#switchPlanView').comboztree('setText', '${planViewInfoName}');
				$('#switchPlanView').comboztree('setValue', '${planViewInfoId}');

			}catch(e){

			}

			$("#sp").show();

			dom = $("#right_page_panel_plan iframe")[0].contentWindow;

			$('#planListShowColumn').combo({
				multiple:true
			});
			$('#sp').appendTo($('#planListShowColumn').combo('panel'));
			$('#planListShowColumn').combo('setValue', '${showColumn}').combo('setText', '${headers}');

			if('${showColumn}'!='') {
				$('#sp input').each(function(){
					var flag = false;
					for(var i=0; i<'${showColumn}'.split(',').length; i++) {
						if($(this).val()=='${showColumn}'.split(',')[i]) {
							flag = true;
							break;
						}
					}
					if(flag) {
						$(this).attr('checked', true);
						$(this).parent('div').css('background-color','#FFE6B0');
					}
				});
			}

			$('#sp input').click(function(){
				var checkedValArr = new Array();
				var checkedTextArr = new Array();
				var textArr = new Array();
				$('#sp label').each(function(){
					textArr.push($(this).html());
				});
				$('input[name="lang"]').each(function(i){
					if($(this).get(0).checked) {
						$(this).parent('div').css('background-color','#FFE6B0');
						checkedValArr.push($(this).val());
						checkedTextArr.push(textArr[i]);
					}
					else {
						$(this).parent('div').css('background-color','');
					}
				});
				$('#aaa').val(checkedValArr.join(','));
				$('#planListShowColumn').combo('setValue', checkedValArr.join(',')).combo('setText', checkedTextArr.join(','));
			});

		},500);
	});


	function getSearchFormData(){
		userName = $('#searchOwner').val();
		progressRate = $('#searchProgressRate').val();
		if (progressRate < 0) {
			progressRate = -1;
		}
		workTime = $('#searchWorkTime').val();
		if (workTime < 0) {
			workTime = -1;
		}
		planNumber = $('#searchPlanNumber').val();
		planName = $('#searchPlanName').val();

		$('#seachPlanTag').find('*').each(function() {
			if ($(this).attr('name') == 'Plan.planLevelInfo.id') {
				if (planLevel == '') {
					planLevel = $(this).val();
				} else {
					planLevel = planLevel + "," + $(this).val();
				}
			}
			if ($(this).attr('name') == 'Plan.isDelay') {
				if (isDelay == '') {
					isDelay = $(this).val();
				} else {
					isDelay = isDelay + "," + $(this).val();
				}
			}
			if ($(this).attr('name') == 'Plan.bizCurrent') {
				if (bizCurrent == '') {
					bizCurrent = $(this).val();
				} else {
					bizCurrent = bizCurrent + "," + $(this).val();
				}
			}
			if ($(this).attr('name') == 'Plan.workTime_condition') {
				workTime_condition = $(this).val();
			}

			if ($(this).attr('name') == 'Plan.progressRate_condition') {
				progressRate_condition = $(this).val();
			}

			if ($(this).attr('name') == 'Plan.taskNameType') {
				if (taskNameType == '') {
					taskNameType = $(this).val();
				} else {
					taskNameType = taskNameType + "," + $(this).val();
				}
			}

			if ($(this).attr('name') == 'Plan.taskType') {
				if (taskType == '') {
					taskType = $(this).val();
				} else {
					taskType = taskType + "," + $(this).val();
				}
			}
		});

		var planStartTime_Begin = $('#planDateRange_BeginDate').datebox(
				'getValue');
		var planStartTime_End = $('#planDateRange_EndDate').datebox('getValue');
		var planEndTime_Begin = $('#planEndDateRange_BeginDate').datebox(
				'getValue');
		var planEndTime_End = $('#planEndDateRange_EndDate')
				.datebox('getValue')

		if (planStartTime_Begin != null && planStartTime_Begin != ''
				&& planStartTime_Begin != undefined
				&& planStartTime_End != null && planStartTime_End != ''
				&& planStartTime_End != undefined) {
			planStartTime = planStartTime_Begin + "," + planStartTime_End;
		}
		if (planEndTime_Begin != null && planEndTime_Begin != ''
				&& planEndTime_Begin != undefined && planEndTime_End != null
				&& planEndTime_End != '' && planEndTime_End != undefined) {
			planEndTime = planEndTime_Begin + "," + planEndTime_End;
		}

		$.ajax({
			type : 'POST',
			url : 'planController.do?setConditionToSession',
			data : {
				planNumber : planNumber,
				planName : planName,
				isDelay : isDelay,
				planLevel : planLevel,
				bizCurrent : bizCurrent,
				userName : userName,
				planStartTime : planStartTime,
				planEndTime : planEndTime,
				workTime : workTime,
				workTime_condition : workTime_condition,
				progressRate : progressRate,
				progressRate_condition : progressRate_condition,
				taskNameType : taskNameType,
				taskType : taskType
			},
			cache : false,
			async : false,
			success : function(data){

			}
		});
	}


	function removeSession(){
		$.ajax({
			type : 'POST',
			url : 'planController.do?removeSession',
			data : {

			},
			cache : false,
			async : false,
			success : function(data){

			}
		});
	}

	function expandPanel(){
		var documentHeight;
		var documentHeight1 = $(window).height();
		var documentHeight2 = document.body.clientHeight;
		if(documentHeight1 > documentHeight2){
			documentHeight = documentHeight2;
		}else{
			documentHeight = documentHeight1;
		}
		if(isIE()){
			documentHeight = document.body.clientHeight;
		}
		var obj = document.getElementById("left_page_panel");
		var northDivHeight = obj.offsetHeight;
		$("#datagridDiv").css({'height':documentHeight-northDivHeight+'px'});
		mygridHeight = documentHeight-northDivHeight - 30+'px';
		if(refFlag){
			getSearchFormData();
			var loadUrl = 'planController.do?goDatagridPage&isIframe=true&mygridHeight='+mygridHeight+'&isViewPage=${isViewPage}&columnIds=${columnIds}&projectId=${projectId}&isModify=${isModify}&planRevocationOperationCode=${planRevocationOperationCode}&planModifyOperationCode=${planModifyOperationCode}&planAssignOperationCode=${planAssignOperationCode}&planDeleteOperationCode=${planDeleteOperationCode}&planChangeOperationCode=${planChangeOperationCode}&planDiscardOperationCode=${planDiscardOperationCode}&isPmo=${isPmo}&isProjectManger=${isProjectManger}&planConcernOperationCode=${planConcernOperationCode}&planUnconcernOperationCode=${planUnconcernOperationCode}';
			loadPage("#right_page_panel_plan", loadUrl + '&refreshTree=true', "");

			dom = $("#right_page_panel_plan iframe")[0].contentWindow;
		}

	}

	function collpasePanel(){
		var documentHeight;
		var documentHeight1 = $(window).height();
		var documentHeight2 = document.body.clientHeight;
		if(documentHeight1 > documentHeight2){
			documentHeight = documentHeight1;
		}else{
			documentHeight = documentHeight2;
		}
		if(isIE()){
			documentHeight = document.body.clientHeight;
		}
		var obj = document.getElementById("left_page_panel");
		var northDivHeight = obj.offsetHeight;
		$("#datagridDiv").css({'height':documentHeight-northDivHeight+'px'});
		mygridHeight = documentHeight-northDivHeight - 30+'px';

		if(refFlag){
			getSearchFormData();
			var loadUrl = 'planController.do?goDatagridPage&isIframe=true&mygridHeight='+mygridHeight+'&isViewPage=${isViewPage}&columnIds=${columnIds}&projectId=${projectId}&isModify=${isModify}&planRevocationOperationCode=${planRevocationOperationCode}&planModifyOperationCode=${planModifyOperationCode}&planAssignOperationCode=${planAssignOperationCode}&planDeleteOperationCode=${planDeleteOperationCode}&planChangeOperationCode=${planChangeOperationCode}&planDiscardOperationCode=${planDiscardOperationCode}&isPmo=${isPmo}&isProjectManger=${isProjectManger}&planConcernOperationCode=${planConcernOperationCode}&planUnconcernOperationCode=${planUnconcernOperationCode}';
			loadPage("#right_page_panel_plan", loadUrl + '&refreshTree=true', "");

			dom = $("#right_page_panel_plan iframe")[0].contentWindow;
		}

	}



	function isIE(){
		if(!! window.ActiveXObject || "ActiveXObject" in window){
			return true;
		}
		return false;
	}

	/**
	 * id=panelId的panel加载laod url
	 * @param panelId
	 * @param url
	 * @param title 标题
	 */
	function loadPage(panelId,url, title) {
		if(null!=url&&""!=url){
			/*
            * 此处为判断该节点是是否需要添加ifarme通过url参数读取满足不同需求指标
            *
            * 								--朱聪 2016/6/13
            * */
			if (url.indexOf('isIframe') != -1) {
				try {
					$(panelId).panel({
						href : "",
						title : title,
						content:createFrameForIDS(url)
					});
				} catch (e) {
					// TODO: handle exception
				}
			}else{
				$(panelId).panel({
					href : url,
					title : title
				});
			}

		}

	}

	function createFrameForIDS(url) {
		var s = '<iframe name="tabiframe" id="tabiframe"  scrolling="no" frameborder="0"  src="'+url+'" style="width:100%;height:98%;overflow-y:hidden;"></iframe>';
		return s;
	}


	//弹出对象对应的流程
	function openFlowPlans(taskNumber) {
		var tabTitle = '计划工作流';
		var url = '${webRoot}/taskFlowCommonController.do?taskFlowList&taskNumber='+taskNumber+'&type=1';
		createdetailwindow_close(tabTitle, url, 800, 400);
	}


	function loadUrl(){
		var loadUrl = 'planController.do?goDatagridPage&isIframe=true&mygridHeight='+mygridHeight+'&isViewPage=${isViewPage}&columnIds=${columnIds}&projectId=${projectId}&isModify=${isModify}&planRevocationOperationCode=${planRevocationOperationCode}&planModifyOperationCode=${planModifyOperationCode}&planAssignOperationCode=${planAssignOperationCode}&planDeleteOperationCode=${planDeleteOperationCode}&planChangeOperationCode=${planChangeOperationCode}&planDiscardOperationCode=${planDiscardOperationCode}&isPmo=${isPmo}&isProjectManger=${isProjectManger}&planConcernOperationCode=${planConcernOperationCode}&planUnconcernOperationCode=${planUnconcernOperationCode}';
		loadPage("#right_page_panel_plan", loadUrl + '&refreshTree=true', "");
		dom = $("#right_page_panel_plan iframe")[0].contentWindow;

	}


	function searchPlan() {

		var userName = $('#searchOwner').val();
		var progressRate = $('#searchProgressRate').val();
		if (progressRate < 0) {
			progressRate = -1;
		}
		var workTime = $('#searchWorkTime').val();
		if (workTime < 0) {
			workTime = -1;
		}
		var planNumber = $('#searchPlanNumber').val();
		var planName = $('#searchPlanName').val();

		var planStartTime = '';
		var planEndTime = '';
		var planStartTime_Begin = $('#planDateRange_BeginDate').datebox(
				'getValue');
		var planStartTime_End = $('#planDateRange_EndDate').datebox('getValue');
		var planEndTime_Begin = $('#planEndDateRange_BeginDate').datebox(
				'getValue');
		var planEndTime_End = $('#planEndDateRange_EndDate')
				.datebox('getValue')

		if (planStartTime_Begin != null && planStartTime_Begin != ''
				&& planStartTime_Begin != undefined
				&& planStartTime_End != null && planStartTime_End != ''
				&& planStartTime_End != undefined) {
			planStartTime = planStartTime_Begin + "," + planStartTime_End;
		}
		if (planEndTime_Begin != null && planEndTime_Begin != ''
				&& planEndTime_Begin != undefined && planEndTime_End != null
				&& planEndTime_End != '' && planEndTime_End != undefined) {
			planEndTime = planEndTime_Begin + "," + planEndTime_End;
		}

		var bizCurrent = '';
		var planLevel = '';
		var workTime_condition = '';
		var progressRate_condition = '';
		var taskNameType = '';
		var taskType = '';
		var isDelay = '';
		$('#seachPlanTag').find('*').each(function() {
			if ($(this).attr('name') == 'Plan.planLevelInfo.id') {
				if (planLevel == '') {
					planLevel = $(this).val();
				} else {
					planLevel = planLevel + "," + $(this).val();
				}
			}
			if ($(this).attr('name') == 'Plan.isDelay') {
				if (isDelay == '') {
					isDelay = $(this).val();
				} else {
					isDelay = isDelay + "," + $(this).val();
				}
			}
			if ($(this).attr('name') == 'Plan.bizCurrent') {
				if (bizCurrent == '') {
					bizCurrent = $(this).val();
				} else {
					bizCurrent = bizCurrent + "," + $(this).val();
				}
			}
			if ($(this).attr('name') == 'Plan.workTime_condition') {
				workTime_condition = $(this).val();
			}

			if ($(this).attr('name') == 'Plan.progressRate_condition') {
				progressRate_condition = $(this).val();
			}

			if ($(this).attr('name') == 'Plan.taskNameType') {
				if (taskNameType == '') {
					taskNameType = $(this).val();
				} else {
					taskNameType = taskNameType + "," + $(this).val();
				}
			}

			if ($(this).attr('name') == 'Plan.taskType') {
				if (taskType == '') {
					taskType = $(this).val();
				} else {
					taskType = taskType + "," + $(this).val();
				}
			}
		});

		$.ajax({
			url : 'planController.do?searchDatagrid&projectId=${projectId}&isModify=${isModify}&planRevocationOperationCode=${planRevocationOperationCode}&planModifyOperationCode=${planModifyOperationCode}&planAssignOperationCode=${planAssignOperationCode}&planDeleteOperationCode=${planDeleteOperationCode}&planChangeOperationCode=${planChangeOperationCode}&planDiscardOperationCode=${planDiscardOperationCode}&planConcernOperationCode=${planConcernOperationCode}&planUnconcernOperationCode=${planUnconcernOperationCode}',
			type : 'post',
			data : {
				planNumber : planNumber,
				planName : planName,
				isDelay : isDelay,
				planLevel : planLevel,
				bizCurrent : bizCurrent,
				userName : userName,
				planStartTime : planStartTime,
				planEndTime : planEndTime,
				workTime : workTime,
				workTime_condition : workTime_condition,
				progressRate : progressRate,
				progressRate_condition : progressRate_condition,
				taskNameType : taskNameType,
				taskType : taskType
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				dom.mygrid.clearAll();
				dom.mygrid.parse(d.obj, 'js');
			}
		});
	}

	//重置
	function tagSearchReset() {
		$('#searchPlanNumber').textbox("clear");
		$('#searchPlanName').textbox("clear");
		$('#searchWorkTime').textbox("clear");
		$('#searchProgressRate').textbox("clear");
		$("#planDateRange_BeginDate").datebox("clear");
		$("#planDateRange_EndDate").datebox("clear");
		$("#planEndDateRange_BeginDate").datebox("clear");
		$("#planEndDateRange_EndDate").datebox("clear");
		$('#searchPlanLevel').combobox("clear");
		$('#searchBizCurrent').combobox("clear");
		$('#searchIsDelay').combobox("clear");
		$('#searchOwner').textbox("clear");

		$('#searchTaskNameType').combobox("clear");
		$('#searchTaskType').combobox("clear");
	}

	//清空查询条件、列表数据重新加载

	function planListSearch() {

		var treeValue=$("#switchPlanView").comboztree("getSelected");
		//可以展开的 节点添加 count_类属性
		dom.addCountClass();
		// 统计已经展开节点的索引
		dom.expandsCount();

		tagSearchReset();

		$.ajax({
			url : 'planController.do?reloadPlanListWithView&projectId=${projectId}&isModify=${isModify}&planRevocationOperationCode=${planRevocationOperationCode}&planModifyOperationCode=${planModifyOperationCode}&planAssignOperationCode=${planAssignOperationCode}&planDeleteOperationCode=${planDeleteOperationCode}&planChangeOperationCode=${planChangeOperationCode}&planDiscardOperationCode=${planDiscardOperationCode}&isPmo=${isPmo}&planConcernOperationCode=${planConcernOperationCode}&planUnconcernOperationCode=${planUnconcernOperationCode}',
			type : 'post',
			cache : false,
			data:{
				planViewId : treeValue[0].id
			},
			success : function(data) {
				var d = $.parseJSON(data);
				dom.mygrid.clearAll();
				dom.mygrid.parse(d.obj, 'js');
				dom.doExpandSelect();
			}
		});
	}




	// 继承父项
	function inheritParent() {
		var dialogUrl = 'deliverablesInfoController.do?goAddInherit&planId='
				+ dom.mygrid.getSelectedRowId();
		createDialog('inheritDialog', dialogUrl);
	}

	// 新增计划
	function addPlan() {
		var dialogUrl = 'planController.do?goAddPlanForGeneralPage&projectId='
				+ $('#projectId').val()
				+ '&isPmo=' + '${isPmo}'+'&isProjectManger=' + '${isProjectManger}'+ '&teamId=${teamId}' ;
		createDialog('addPlanForGeneralPageDialog', dialogUrl);
	}

	// 新增子计划
	function addSubPlan() {
		$.ajax({
			url : 'taskDetailController.do?judgePlanDocumant',
			type : 'post',
			data : {
				id : dom.mygrid.getSelectedRowId()
			},
			cache : false,
			success : function(data) {
				if (data != null) {
					var d = $.parseJSON(data);
					if (d.obj == false) {
						var dialogUrl = 'planController.do?goAddPlanForGeneralPage&projectId='
								+ $('#projectId').val()
								+ '&isPmo=' + '${isPmo}'
								+ '&isProjectManger=' + '${isProjectManger}'
								+ '&teamId=${teamId}&parentPlanId='+ dom.mygrid.getSelectedRowId();

						createDialog('addSubPlanDialog', dialogUrl);
					} else {


					}
				} else {
					top.tip(d.msg);
				}
			}
		});
	}

	// 下方插入计划
	function addPlanAfter() {
		var dialogUrl = 'planController.do?goAddPlanForGeneralPage&projectId='
				+ $('#projectId').val()
				+ '&isPmo=' + '${isPmo}'
				+ '&isProjectManger=' + '${isProjectManger}'
				+ '&teamId=${teamId}&beforePlanId='+dom.mygrid.getSelectedRowId() ;

		createDialog('addPlanAfterDialog', dialogUrl);
	}

	// 打开修改计划页面
	function modifyPlanOnTree(id) {
		var owner = dom.mygrid.getRowAttribute(id, "owner");
		var createBy = dom.mygrid.getRowAttribute(id, "createBy");
		var parentPlanId = dom.mygrid.getRowAttribute(id, "parentPlanId");
		if(parentPlanId == "" || parentPlanId == null || parentPlanId == undefined || parentPlanId == 'undefined'){
			//  var dialogUrl = 'planController.do?goUpdate&id=' + id;
			var typeIds;
			$.ajax({
				url: 'tabCombinationTemplateController.do?goTabView2',
				data: {
					planId : id
				},
				type: "POST",
				async:false,
				success: function(data) {
					var json = $.parseJSON(data);
					if(json.success) {
						var obj = json.obj;
						for(var i=0;i<obj.length;i++){
							if(obj[i].displayAccess != "" && obj[i].displayAccess != null){
								var flag = true;
								try{
									flag = eval(obj[i].displayAccess);
								}catch (e) {

								}
								if(flag){
									if(typeIds != undefined && typeIds != "undefined"){
										typeIds = typeIds + "," +obj[i].typeId;
									}else{
										typeIds = obj[i].typeId;
									}
								}
							}else{
								if(typeIds != undefined && typeIds != "undefined"){
									typeIds = typeIds + "," +obj[i].typeId;
								}else{
									typeIds = obj[i].typeId;
								}
							}
						}
					}
				}
			});

			var dialogUrl = 'planController.do?loadPlanAddInfo&fromType=update&id=' + id + '&typeIds='+typeIds;
			createDialog('modifyPlanDialog', dialogUrl);
		}else{
			if('${currentUserId}' == owner || owner == "undefined" || owner == undefined || '${currentUserId}' ==createBy){
				//var dialogUrl = 'planController.do?goUpdate&id=' + id;
				var typeIds;
				$.ajax({
					url: 'tabCombinationTemplateController.do?goTabView2',
					data: {
						planId : id
					},
					type: "POST",
					async:false,
					success: function(data) {
						var json = $.parseJSON(data);
						if(json.success) {
							var obj = json.obj;
							for(var i=0;i<obj.length;i++){
								if(obj[i].displayAccess != "" && obj[i].displayAccess != null){
									var flag = true;
									try{
										flag = eval(obj[i].displayAccess);
									}catch (e) {

									}
									if(flag){
										if(typeIds != undefined && typeIds != "undefined"){
											typeIds = typeIds + "," +obj[i].typeId;
										}else{
											typeIds = obj[i].typeId;
										}
									}
								}else{
									if(typeIds != undefined && typeIds != "undefined"){
										typeIds = typeIds + "," +obj[i].typeId;
									}else{
										typeIds = obj[i].typeId;
									}
								}
							}
						}
					}
				});
				var dialogUrl = 'planController.do?loadPlanAddInfo&fromType=update&id=' + id+ '&typeIds='+typeIds;
				createDialog('modifyPlanDialog', dialogUrl);
			} else{
				$.ajax({
					type : 'POST',
					data : {
						id :id
					},
					url : 'planController.do?getPlanBizCurrentById',
					cache : false,
					success : function(data){
						var d = $.parseJSON(data);
						if(d.success){
							//   var dialogUrl = 'planController.do?goUpdate&id=' + id;
							var typeIds;
							$.ajax({
								url: 'tabCombinationTemplateController.do?goTabView2',
								data: {
									planId : id
								},
								type: "POST",
								async:false,
								success: function(data) {
									var json = $.parseJSON(data);
									if(json.success) {
										var obj = json.obj;
										for(var i=0;i<obj.length;i++){
											if(obj[i].displayAccess != "" && obj[i].displayAccess != null){
												var flag = true;
												try{
													flag = eval(obj[i].displayAccess);
												}catch (e) {

												}
												if(flag){
													if(typeIds != undefined && typeIds != "undefined"){
														typeIds = typeIds + "," +obj[i].typeId;
													}else{
														typeIds = obj[i].typeId;
													}
												}
											}else{
												if(typeIds != undefined && typeIds != "undefined"){
													typeIds = typeIds + "," +obj[i].typeId;
												}else{
													typeIds = obj[i].typeId;
												}
											}

										}
									}
								}
							});
							var dialogUrl = 'planController.do?loadPlanAddInfo&fromType=update&id=' + id+ '&typeIds='+typeIds;
							createDialog('modifyPlanDialog', dialogUrl);
						}else{
							top.tip("您无权修改该计划");
							planListSearch();
						}
					}
				});
			}
		}
	}


	//参考判断js
	var isReference = false;
	function showReferenceTab(){
		if(isReference){
			return true;
		}else{
			return false;
		}
	}

	//风险清单判断js
	var isRiskList = false;
	function showRiskListTab(){
		if(isRiskList){
			return true;
		}else{
			return false;
		}
	}

	//问题判断js
	var isProblem = false;
	function showProblemTab(){
		if(isProblem){
			return true;
		}else{
			return false;
		}
	}

	//评审判断js
	var isReview = false;
	function showReviewTab(){
		if(isReview){
			return true;
		}else{
			return false;
		}
	}


	//基本信息判断js
	var isBase = true;
	function showBaseTab(){
		if(isBase){
			return true;
		}else{
			return false;
		}
	}

	//输入判断js
	var isInput = true;
	function showInputTab(){
		if(isInput){
			return true;
		}else{
			return false;
		}
	}

	//输出判断js
	var isOutput = true;
	function showOutputTab(){
		if(isOutput){
			return true;
		}else{
			return false;
		}
	}

	//资源判断js
	var isResource = true;
	function showResourceTab(){
		if(isResource){
			return true;
		}else{
			return false;
		}
	}


	// 删除计划
	function deleteOnTree(id) {
		var createBy = dom.mygrid.getRowAttribute(id, "createBy");
		var parentPlanId = dom.mygrid.getRowAttribute(id, "parentPlanId");
		if(parentPlanId == "" || parentPlanId == null || parentPlanId == undefined || parentPlanId == 'undefined'){
			deletePlanOnTree(id);
		}else{
			if('${currentUserId}' == createBy){  //缺陷23606修复,改为当前用户是创建者即可删除，原先为负责人
				deletePlanOnTree(id);
			}
			else{
				$.ajax({
					type : 'POST',
					data : {
						id :id
					},
					url : 'planController.do?getPlanBizCurrentById',
					cache : false,
					success : function(data){
						var d = $.parseJSON(data);
						if(d.success){
							deletePlanOnTree(id);
						}else{
							top.tip("您无权删除该计划");
							planListSearch();
						}
					}
				});
			}
		}

	}

	// 关注计划
	function concernOnTree(id) {
		if (id != null) {
			$.ajax({
				url : 'planController.do?concernPlan',
				type : 'post',
				data : {
					id : id,
					concernCode:'1'
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						planListSearch();
						top.tip("关注计划成功");
					}
				}
			});
		}
	}

	// 取消关注计划
	function unconcernOnTree(id) {
		if (id != null) {
			$.ajax({
				url : 'planController.do?concernPlan',
				type : 'post',
				data : {
					id : id,
					concernCode:'0'
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						planListSearch();
						top.tip("取消关注计划成功");
					}
				}
			});
		}
	}

	function deletePlanOnTree(id){
		if (id != null) {
			$.ajax({
				type : 'POST',
				data : {
					planId : id
				},
				url : 'planController.do?checkBeforePlanDel',
				cache : false,
				async : false,
				success : function(data){
					var da = $.parseJSON(data);
					if(da.success){
						top.Alert.confirm(
								'<spring:message code="com.glaway.ids.common.confirmBatchDel"/>',
								function(r) {
									if (r) {
										$.ajax({
											url : 'planController.do?doBatchDel',
											type : 'post',
											data : {
												ids : id
											},
											cache : false,
											success : function(data) {
												var d = $.parseJSON(data);
												if (d.success) {
													var msg = d.msg;
													top.tip(msg);
													dom.mygrid.deleteRow(id);
													refreshAfterDel(id);
												}
											}
										});
									}
								});
					}else{
						top.Alert.confirm(
								'删除该条计划会同时将其子计划等下级计划删除，确认删除？',
								function(r) {
									if (r) {
										$.ajax({
											url : 'planController.do?doBatchDel',
											type : 'post',
											data : {
												ids : id
											},
											cache : false,
											success : function(data) {
												var d = $.parseJSON(data);
												if (d.success) {
													var msg = d.msg;
													top.tip(msg);
													dom.mygrid.deleteRow(id);
													refreshAfterDel(id);
												}
											}
										});
									}
								});
					}
				}
			});

		}
	}

	// 单条下达计划
	function assignPlanOnTree(id) {
		debugger;
		var owner = dom.mygrid.getRowAttribute(id, "owner");
		var createBy = dom.mygrid.getRowAttribute(id, "createBy");
		var parentPlanId = dom.mygrid.getRowAttribute(id, "parentPlanId");
		if(parentPlanId == "" || parentPlanId == null || parentPlanId == undefined || parentPlanId == 'undefined'){
			assignPlanForTree(id);
		}else{
			if('${currentUserId}' == owner || owner == "undefined" || owner == undefined){
				assignPlanForTree(id);
			}else if('${currentUserId}' == createBy){   //应吴可要求，在73环境上添加创建者可提交计划
				assignPlanForTree(id);
			} else{
				$.ajax({
					type : 'POST',
					data : {
						id :id
					},
					url : 'planController.do?getPlanBizCurrentById',
					cache : false,
					success : function(data){
						var d = $.parseJSON(data);
						if(d.success){
							assignPlanForTree(id);
						}else{
							top.tip("您无权发布该计划");
							planListSearch();
						}
					}
				});
			}
		}


	}


	function assignPlanForTree(id){
		//获取参数判断是否是从KDD入口进入IDS
		var kddProductType='${param.kddProductType}';

		$.ajax({
			type:'POST',
			data:{
				ids : id,
				useObjectType : "PLAN"
			},
			url:"planController.do?checkOriginIsNullBeforeSub",
			cache:false,
			success:function(data){
				var d = $.parseJSON(data);
				if(d.success){
					//驳回时，如果之前是父子计划一起发布的，计划树中父计划不给单独提交：
					$.ajax({
						url : 'planController.do?checkisParentChildAllPublish&id=' + id,
						type : 'post',
						data : {},
						cache : false,
						success : function(data) {
							var d = $.parseJSON(data);
							var msg = d.msg;
							if (d.success) {
								top.tip(msg);
								return false;
							}else{
								$.ajax({
									url : 'planController.do?pdAssign&id=' + id,
									type : 'post',
									data : {'kddProductType':kddProductType},
									cache : false,
									success : function(data) {
										var d = $.parseJSON(data);
										var msg = d.msg;
										if (d.success) {
											var dialogUrl = 'planController.do?goAssignPlanOne&id=' + id+'&kddProductType='+kddProductType;
											createDialog('assignPlanDialog', dialogUrl);
										} else {
											top.tip(msg);
										}
									}
								});
							}
						}
					});
				}else{
					top.tip("存在没有挂载的输入，不可发布");
					return false;
				}
			}
		});
	}

	// 单条变更计划
	function changePlanOnTree(id) {
		debugger;
		var typeIds;
		$.ajax({
			url: 'tabCombinationTemplateController.do?goTabView2',
			data: {
				planId : id
			},
			type: "POST",
			async:false,
			success: function(data) {

				var json = $.parseJSON(data);
				if(json.success) {
					var obj = json.obj;
					for(var i=0;i<obj.length;i++){
						if(obj[i].displayAccess != "" && obj[i].displayAccess != null){
							var flag = true;
							try{
								flag = eval(obj[i].displayAccess);
							}catch (e) {

							}
							if(flag){
								if(typeIds != undefined && typeIds != "undefined"){
									typeIds = typeIds + "," +obj[i].typeId;
								}else{
									typeIds = obj[i].typeId;
								}
							}
						}else{
							if(typeIds != undefined && typeIds != "undefined"){
								typeIds = typeIds + "," +obj[i].typeId;
							}else{
								typeIds = obj[i].typeId;
							}
						}
					}
				}
			}
		});


		var dialogUrl = 'planChangeController.do?goChangePlanOne&planId=' + id+'&typeIds='+typeIds;
		createDialog('changePlanDialog', dialogUrl);
	}

	// 变更计划
	function changePlanOnTreeFlow(id) {

		var typeIds;
		$.ajax({
			url: 'tabCombinationTemplateController.do?goTabView2',
			data: {
				planId : id
			},
			type: "POST",
			async:false,
			success: function(data) {
				var json = $.parseJSON(data);
				if(json.success) {
					var obj = json.obj;
					for(var i=0;i<obj.length;i++){
						if(obj[i].displayAccess != "" && obj[i].displayAccess != null){
							var flag = true;
							try{
								flag = eval(obj[i].displayAccess);
							}catch (e) {

							}
							if(flag){
								if(typeIds != undefined && typeIds != "undefined"){
									typeIds = typeIds + "," +obj[i].typeId;
								}else{
									typeIds = obj[i].typeId;
								}
							}
						}else{
							if(typeIds != undefined && typeIds != "undefined"){
								typeIds = typeIds + "," +obj[i].typeId;
							}else{
								typeIds = obj[i].typeId;
							}
						}
					}
				}
			}
		});

		var dialogUrl = 'planChangeController.do?goChangePlanFlow&planId=' + id+'&path='+'tree'+'&typeIds='+typeIds;
		createDialog('changePlanDialog', dialogUrl);
	}


	function revocationPlanOnTree(id){
		$.ajax({
			type : 'POST',
			data : {
				id : id
			},
			url : 'planChangeController.do?revocationPlanChange',
			cache : false,
			success : function(data){
				var d = $.parseJSON(data);
				top.tip(d.msg);
				if(d.success){
					planListSearch();
				}
			}
		});
	}

	// 废弃计划
	function discardPlan(id) {
		$.ajax({
			url : 'planController.do?pdChild',
			type : 'post',
			data : {
				id : id
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					top.Alert.confirm('<spring:message code="com.glaway.ids.pm.project.plan.confirmDiscard"/>', function(r) {
						if (r) {
							$.ajax({
								url : 'planController.do?doDiscard',
								type : 'post',
								data : {
									id : id
								},
								cache : false,
								success : function(data) {
									var d = $.parseJSON(data);
									if (d.success) {
										var msg = d.msg;
										top.tip(msg);
										planListSearch();
									}
								}
							});
						}
					});
				} else {
					top.Alert.confirm('<spring:message code="com.glaway.ids.pm.project.plan.confirmDiscardTwo"/>', function(r) {
						if (r) {
							$.ajax({
								url : 'planController.do?doDiscard',
								type : 'post',
								data : {
									id : id
								},
								cache : false,
								success : function(data) {
									var d = $.parseJSON(data);
									if (d.success) {
										var msg = d.msg;
										top.tip(msg);
										planListSearch();
									}
								}
							});
						}
					});
				}
			}
		});
	}

	// 打开批量修改计划页面
	function multiModifyPlan() {
		$('#moreMenu').menu('hide', {});

		var treeValue=$("#switchPlanView").comboztree("getSelected");
		var dialogUrl = 'planController.do?goModifyMass&projectId='
				+ $('#projectId').val();
		createDialog('multiModifyDialog', dialogUrl);
	}

	// 查看计划信息
	function viewPlan(id) {
		var dialogUrl = 'planController.do?goCheck&id=' + id;
		if('${param.iframeTarget}'=='resubmitFlow') {
			parent.createResubmitFlowDialog(dialogUrl);
		}
		else {
			createDialog('viewPlanDialog', dialogUrl);
		}
	}

	// 查看任务信息
	function viewPlanTask() {
		$
				.ajax({
					url : 'planController.do?checkPlanStatus&id='
							+ dom.mygrid.getSelectedRowId(),
					type : 'post',
					data : {},
					cache : false,
					success : function(data) {

						var d = $.parseJSON(data);
						var msg = d.msg;
						if (d.success) {
							var url = '${webRoot}/taskDetailController.do?goCheck&isIframe=true&id='
									+ dom.mygrid.getSelectedRowId()+ '&teamId=' + '${teamId}';
							var id = dom.mygrid.getSelectedRowId();
							var name = d.obj;
							top.addTabById(name, url, 'pictures', id)

						} else {
							var dialogUrl = 'planController.do?goCheck&id='
									+ dom.mygrid.getSelectedRowId();
							createDialog('viewPlanTaskDialog', dialogUrl);
						}
					}
				});
	}

	// 导入子计划
	function importSubPlan(type) {
		$.ajax({
			url : 'taskDetailController.do?judgePlanDocumant',
			type : 'post',
			data : {
				id : dom.mygrid.getSelectedRowId()
			},
			cache : false,
			success : function(data) {
				if(type == 'rdFlow') {
					isOpenEditor(dom.mygrid.getSelectedRowId());
				}else{
					if (data != null) {
						var d = $.parseJSON(data);
						if (d.obj == false) {
							if (type == 'mpp') {
								var dialogUrl = 'planController.do?goImportPlan&parentPlanId='
										+ dom.mygrid.getSelectedRowId() + '&projectId='
										+ $('#projectId').val()+'&currentUserId='+'${currentUserId}';
								createDialog('importMppDialog', dialogUrl);
							} else if(type == 'template') {
								var url = 'planTemplateController.do?planTemplate_import&isDialog=true';
								$("#" + 'subPlanImportSearchDialog')
										.lhgdialog("open", "url:" + url);
							} else {
								var dialogUrl = 'planController.do?goImportExcel&planId='
										+ dom.mygrid.getSelectedRowId() + "&type=childPlan"
										+ '&projectId=' + $('#projectId').val();
								createDialog('importExcelDialog', dialogUrl);
							}
						} else {
							top.tip('<spring:message code="com.glaway.ids.pm.project.plan.importChildLimit"/>');
						}
					} else {
						top.tip(d.msg);
					}
				}
			}
		});
	}

	//子计划导入的入口：
	function isOpenEditor(planId){
		var newdate = new Date().getTime();
		$.ajax({
			url : 'applyFlowResolveForChangeController.do?flowResolveJudge&isIframe=true',
			type : 'post',
			data : {
				planId : planId
			},
			cache : false,
			success : function(data) {
				if (data != null) {
					var d = $.parseJSON(data);
					userId = d.msg.split('<br/>')[0];
					if (d.success == true) {
						if (d.obj == "prepare") {
							var dialogUrl = 'taskFlowResolveController.do?goPlanResolve&isIframe=true&id=' + planId;
							$("#" + 'taskFlowResolve').lhgdialog("open", "url:" + dialogUrl);
						} else {
							var x = d.obj.split(',');
							var isEnableFlag = x[0];
							var status = x[1];
							var url;
							if ("ORDERED" == status) {
								// 流程变更分支
								url = "applyFlowResolveForChangeController.do?flowResolveEditorForOrdered&rdflowWeb_Nginx=${rdflowWeb_Nginx}&type=plan&isIframe=true&newDate=" + newdate;
							} else {
								// 流程分解分支
								url = "taskFlowResolveController.do?flowResolveEditor&rdflowWeb_Nginx=${rdflowWeb_Nginx}&isIframe=true&newDate=" + newdate;
							}
							// isEnableFlag为1表示只能查看、不能编辑或者变更
							$.ajax({
								type : 'POST',
								url : url,
								data : {
									id : planId
								},
								success : function(data) {
									var furl = "${rdflowWeb_Nginx}/rdflowCommonController.do?goOpenEditorPage&isEnableFlag="
											+ isEnableFlag
											+ "&parentPlanId="
											+ planId
											+ "&status="
											+ status
											+ "&fromType="
											+ "PLAN"
											+ "&enterType="
											+ "subPlanImport"
											+ "&newDate="
											+ newdate
											+ "&userId="
											+ userId;
									$("#" + 'editFlowTaskTd').lhgdialog("open", "url:" + furl);
								}
							});
						}
					} else {
						tip(d.msg);
					}
				} else {
					tip('<spring:message code="com.glaway.ids.pm.project.task.flowResolveFailure"/>');
				}
			}
		});
	}


	function createDia(id, furl) {
		$("#" + id).lhgdialog("open", "url:" + furl);
	}

	function reloadAndCloseAll() {
		beginFun();
	}

	function beginFun() {
		try {
			planListSearch();
		} catch (e) {
			// TODO: handle exception
		}
		return true;
	};

	function doFlowResolve(iframe) {
		iframe.doFlowResolve();
		return false;
	}

	// 批量下达计划
	function assignPlan() {
		//获取参数判断是否是从KDD入口进入IDS
		var kddProductType='${param.kddProductType}';
		var dialogUrl = 'planController.do?goAssignPlanMass&projectId=${projectId}&kddProductType='+kddProductType;
		createDialog('assignMassDialog', dialogUrl);
	}

	//导出exl
	function courseListImportXls() {
		var rows = $("#planList").treegrid("getSelections");
		if (rows.length != 1) {
			top
					.tip('<spring:message code="com.glaway.ids.pm.project.plan.selectImportParent"/>');
		} else {
			openuploadwin('Excel导入', 'planController.do?upload&id='
					+ $('#planList').treegrid('getSelections')[0].id,
					"planList");
		}
	}

	// 另存为计划模板
	function saveAsPlanTemplate(type) {
		$('#moreMenu').menu('hide', {});
		var url = "";
		if (type == "right") {
			url = "planTemplateController.do?goSavePlanAsTemple&fromType=planSaveAsTemplate&planId="
					+ dom.mygrid.getSelectedRowId() + "&type="+ type+ "&projectId="
					+ $('#projectId').val();
			var dialogUrl = url;
			createDialog('saveAsPlanTemplateDiaAll', dialogUrl);
		} else if (type == "main") {
			var dataSize = dom.mygrid.getRowsNum(); //获取mygrid中展现的当前的数据条数，未展开的不算
			if (dataSize == 0) {
				top.tip('当前计划无数据，请添加数据后再进行当前操作');
			} else {
				url = "planTemplateController.do?goSavePlanAsTemple&fromType=planSaveAsTemplate&projectId="
						+ $('#projectId').val()+ "&type="+ type;
				var dialogUrl = url;
				createDialog('saveAsPlanTemplateDiaAll', dialogUrl);
			}

		}
	}
	var submit_flag = true;
	function assignPlanDialog(iframe){
		if(submit_flag){
			submit_flag = false;
			iframe.startAssignProcess();
		}
		return false;
	}

	function assignMassDialog(iframe){
		iframe.startAssignProcess();
		return false;
	}

	function saveAndSub(iframe) {
		iframe.btnType = "submit";
		iframe.planSaveAsTemplateSubmit();
		return false;
	}

	function saveAllAndSub(iframe) {
		iframe.btnType = "submit";
		iframe.planSaveAsTemplateSubmit();
		return false;
	}

	function savePlanName(iframe) {
		iframe.planSaveAsTemplate();
		return false;
	}

	function saveAllPlanName(iframe){
		iframe.addPlanTemplate();
		return false;
	}

	//根据另存为模板界面传过来的id打开上传页面
	function setPlanTemplateId(plantemplateid) {
		var dialogUrl = 'planTemplateController.do?goSubmitApprove&planTemplateId='
				+ plantemplateid;
		createDialog('goSubmit', dialogUrl);
	}

	function goSubmitPlan(iframe) {
		iframe.startPlanTemProcess();
		reloadTable();
		top.tip("提交成功");
	}

	function importPlan(type) {
		var projectId = $('#projectId').val();
		$.post(
				'planController.do?checkBeforeImport',
				{
					'projectId' : projectId
				},
				function(data) {

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
											if (type == 'mpp') {
												var dialogUrl = 'planController.do?goImportPlan&projectId='
														+ $('#projectId').val()+'&currentUserId='+'${currentUserId}';
												createDialog(
														'importMppDialog',
														dialogUrl);
											} else if(type == 'template') {
												var url = 'planTemplateController.do?planTemplate_import&isDialog=true';
												$("#"+ 'allPlanImportSearchDialog')
														.lhgdialog("open","url:"+ url);
											} else {
												var dialogUrl = 'planController.do?goImportExcel&projectId='
														+ projectId;
												createDialog('importExcelDialog',dialogUrl);
											}
										}
									});
						} else if (b == 0) {
							if (type == 'mpp') {
								var dialogUrl = 'planController.do?goImportPlan&projectId='
										+ $('#projectId').val()+'&currentUserId='+'${currentUserId}';
								createDialog('importMppDialog',
										dialogUrl);
							} else if(type == 'template') {
								var url = 'planTemplateController.do?planTemplate_import&isDialog=true';
								$("#" + 'allPlanImportSearchDialog')
										.lhgdialog("open", "url:" + url);
							} else {
								var dialogUrl = 'planController.do?goImportExcel&projectId='
										+ $('#projectId').val();
								createDialog('importExcelDialog',dialogUrl);
							}
						} else {
							top.tip(msg);
						}
					}
				})
	}

	function importDialog(iframe) {
		saveObj();
		planListSearch();
		return ture;
	}

	function importDialog2(iframe) {
		saveObj();
		planListSearch();
		return ture;
	}

	// 批量变更计划
	function multiChangePlan() {
		$('#moreMenu').menu('hide', {});

		var dialogUrl = 'planChangeMassController.do?goChangePlanMass&projectId=' + $('#projectId').val();
		createDialog('multiChangeDialog', dialogUrl);
	}

	// 导出计划Excel
	function exportPlan() {
		$.ajax({
			url : 'planController.do?pdExportPlan',
			type : 'post',
			data : {},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var msg = d.msg;
					var a = msg.indexOf("操作成功");
					if (a == 0) {
						postFormDataToAction("planController.do?exportXls&projectId=${projectId}");
					} else {
						top.tip(msg);
					}

				}
			}
		});
	}

	function exportPlanMpp() {
		$('#moreMenu').menu('hide', {});
		postFormDataToAction("planController.do?exportPlan&projectId=${projectId}");
	}

	function exportPlanMppSingle() {
		postFormDataToAction("planController.do?exportPlanSingle&planIdForMpp="
				+ dom.mygrid.getSelectedRowId());
	}

	/**
	 * Jeecg Excel 导出
	 * 代入查询条件
	 */


	function postFormDataToAction(url) {
		var params = '';
		var planName = $('#planName').val();
		if (planName != null && planName != '' && planName != undefined) {
			params = params + '&planName=' + planName;
		}
		var assignTimeStart = $('#assignTimeStart').val();
		if (assignTimeStart != null && assignTimeStart != ''
				&& assignTimeStart != undefined) {
			params = params + '&assignTimeStart=' + assignTimeStart;
		}
		var assignTimeStart = $('#assignTimeStart').val();
		if (assignTimeStart != null && assignTimeStart != ''
				&& assignTimeStart != undefined) {
			params = params + '&assignTimeStart=' + assignTimeStart;
		}
		var assignTimeEnd = $('#assignTimeEnd').val();
		if (assignTimeEnd != null && assignTimeEnd != ''
				&& assignTimeEnd != undefined) {
			params = params + '&assignTimeEnd=' + assignTimeEnd;
		}
		var planStartTimeStart = $('#planStartTimeStart').val();
		if (planStartTimeStart != null && planStartTimeStart != ''
				&& planStartTimeStart != undefined) {
			params = params + '&planStartTimeStart=' + planStartTimeStart;
		}
		var planStartTimeEnd = $('#planStartTimeEnd').val();
		if (planStartTimeEnd != null && planStartTimeEnd != ''
				&& planStartTimeEnd != undefined) {
			params = params + '&planStartTimeEnd=' + planStartTimeEnd;
		}
		var planEndTimeStart = $('#planEndTimeStart').val();
		if (planEndTimeStart != null && planEndTimeStart != ''
				&& planEndTimeStart != undefined) {
			params = params + '&planEndTimeStart=' + planEndTimeStart;
		}
		var planEndTimeEnd = $('#planEndTimeEnd').val();
		if (planEndTimeEnd != null && planEndTimeEnd != ''
				&& planEndTimeEnd != undefined) {
			params = params + '&planEndTimeEnd=' + planEndTimeEnd;
		}

		window.location.href = url + encodeURI(params);
	}

	/**
	 * 添加事件打开窗口
	 * @param title 编辑框标题
	 * @param addurl//目标页面地址
	 */
	function add(title, addurl, gname, width, height) {
		gridname = gname;
		createwindow(title, addurl, width, height);
	}
	/**
	 * 创建添加或编辑窗口
	 *
	 * @param title
	 * @param addurl
	 * @param saveurl
	 */
	function createwindow(title, addurl, width, height) {
		width = width ? width : 700;
		height = height ? height : 400;
		if (width == "100%" || height == "100%") {
			width = document.body.offsetWidth;
			height = document.body.offsetHeight - 100;
		}
		$.fn.lhgdialog({
			content : 'url:' + addurl,
			lock : true,
			width : width,
			height : height,
			title : title,
			opacity : 0.3,
			cache : false,
			ok : function() {
				iframe = this.iframe.contentWindow;
				saveObj();
				planListSearch();
				return false;
			},
			cancelVal : '取消',
			cancel : true
		});
	}

	function reloadTable() {
		planListSearch();
	}

	//选择部件
	function selectBomPlan() {
		var projectId = $('#projectId').val();
		var dialogUrl = 'planController.do?goSelectPart&projectId='+projectId+"&planId="+"";
		createDialog('bomDialog', dialogUrl);
	}

	var bomPlanId = "";

	//选择部件
	function addBomAfter() {
		var projectId = $('#projectId').val();
		var planId = dom.mygrid.getSelectedRowId();
		bomPlanId = planId;
		var dialogUrl = 'planController.do?goSelectPart&projectId='+projectId+"&planId="+planId;
		createDialog('bomDialog', dialogUrl);
	}

	//选择部件-下一步操作
	function selectBomNodes(iframe) {
		var datas = iframe.getSelectedItems();
		if (datas.length==0){
			tip("请选择一条数据");
			return false;
		}else if (datas.length!=1){
			tip("只能选择一条数据");
			return false;
		}
		var partId = datas[0].id;
		var planId = '${planId}';
		if (bomPlanId!=""){
			planId = bomPlanId;
		}
		var projectId = '${projectId}';
		var dialogUrl = 'planController.do?goBomNodes&partId='+partId+'&projectId='+projectId+"&planId="+planId;
		createDialog('bomNodeDialog', dialogUrl);
	}

	//选择BOM节点-上一步
	function openSelectPart() {
		var dialogUrl = 'planController.do?goSelectPart';
		createDialog('bomDialog', dialogUrl);
	}

	function sumbitBom(iframe) {
		bomPlanId = "";
		var flag = iframe.sumbit();
		if (flag){
			planListSearch();
		} else{
			return false;
		}

	}

	//下方导入计划
	function insertPlan(type) {

		if (type == 'mpp') {
			var dialogUrl = 'planController.do?goImportPlan&planId='
					+ dom.mygrid.getSelectedRowId() + '&projectId='
					+ $('#projectId').val() + '&type=insert'+'&currentUserId='+'${currentUserId}';
			createDialog('importMppDialog', dialogUrl);
		} else if(type == 'template'){
			var url = 'planTemplateController.do?planTemplate_import&isDialog=true';
			$("#" + 'planImportSearchDialog').lhgdialog("open", "url:" + url);
		} else {
			var dialogUrl = 'planController.do?goImportExcel&planId='
					+ dom.mygrid.getSelectedRowId() + '&type=nextPlan'
					+ '&projectId='+ $('#projectId').val();
			createDialog('importExcelDialog', dialogUrl);
		}
	}

	function importSubmit(iframe) {
		var planTemplate = iframe.getPlanTemplateDialog();
		if (planTemplate == null) {
			top.tip('<spring:message code="com.glaway.ids.pm.project.plan.savePlanByPlanTemplateIdFail"/>');
			return false;
		} else {
			$("#planTemplateId").val(planTemplate.id);
			var planId = dom.mygrid.getSelectedRowId();
			var parentPlanId = dom.mygrid.getRowAttribute(planId, "parentPlanId");
			$('#importPlanForm_')
					.form(
							'submit',
							{
								url : 'planController.do?savePlanByPlanTemplateId&planId='
										+ planId
										+ '&projectId='
										+ $('#projectId').val()
										+ '&type=insert'+'&currentUserId='+'${currentUserId}'
										+'&parentPlanId='+parentPlanId,
								success : function(data) {
									var jsonobj = $.parseJSON(data);
									if (jsonobj.success) {
										top.tip(jsonobj.msg);
										planListSearch();
										$.fn.lhgdialog("closeSelect");
									} else {
										top.tip(jsonobj.msg);
									}

								}
							});
		}
	}

	function commitViewDialog(iframe) {
		iframe.setTimeCondition();
		return false;
	}

	function saveAsDialog(iframe){
		//iframe.saveAsView();
		var select=	iframe.getSelected();
		$.ajax({
			url:	'planViewController.do?doViewSaveAs',
			type : 'post',
			data: {
				viewId : viewId,
				viewName : viewName,
				timeC : timeC,
				yearC : yearC,
				seasonmonthC : seasonmonthC,
				planRangeC : planRangeC
			},
			cache: false,
			success: function(data)
			{
				var d=$.parseJSON(data);
				if('true' == d.succuess)
				{
					var win =$.fn.lhgdialog("getSelectParentWin");

					$.fn.lhgdialog("closeSelect");
				}
			}
		})
	}

	function importSubSubmit(iframe) {
		var planTemplate = iframe.getPlanTemplateDialog();
		if (planTemplate == null) {
			top.tip('<spring:message code="com.glaway.ids.pm.project.plan.savePlanByPlanTemplateIdFail"/>');
			return false;
		} else {
			$("#planTemplateId").val(planTemplate.id);
			var parentPlanId = dom.mygrid.getSelectedRowId();
			$('#importPlanForm_')
					.form(
							'submit',
							{
								url : 'planController.do?savePlanByPlanTemplateId&projectId='
										+ $('#projectId').val()
										+ '&parentPlanId='
										+ parentPlanId
										+ '&planId=' + parentPlanId+'&currentUserId='+'${currentUserId}',
								success : function(data) {
									var jsonobj = $.parseJSON(data);
									if (jsonobj.success) {
										top.tip(jsonobj.msg);
										planListSearch();
										$.fn.lhgdialog("closeSelect");
									} else {
										top.tip(jsonobj.msg);
									}

								}
							});
		}
	}

	function importAllSubmit(iframe) {
		var planTemplate = iframe.getPlanTemplateDialog();
		if (planTemplate == null) {
			top.tip('<spring:message code="com.glaway.ids.pm.project.plan.savePlanByPlanTemplateIdFail"/>');
			return false;
		} else {
			$("#planTemplateId").val(planTemplate.id);
			$('#importPlanForm_')
					.form(
							'submit',
							{
								url : 'planController.do?savePlanByPlanTemplateId&projectId='
										+ $('#projectId').val()+'&currentUserId='+'${currentUserId}',
								success : function(data) {
									var jsonobj = $.parseJSON(data);
									if (jsonobj.success) {
										top.tip(jsonobj.msg);
										planListSearch();
										$.fn.lhgdialog("closeSelect");
									} else {
										top.tip(jsonobj.msg);
										return false;
									}

								}
							});
		}
	}

	function viewGanTT() {
		try{
			var currProjectContext = '${pageContext.request.contextPath }';
			createDialog('ganttDialog',
					"webpage/com/glaway/ids/gantt/ganttView.html?&paramArr="
					+ ($('#projectId').val())+'_'+currProjectContext.substring(1,currProjectContext.length)+'_'+ $('#switchPlanView').combotree('getValue'));
		}catch(e){

		}

	}

	function viewGraph() {
		var currProjectContext = '${pageContext.request.contextPath }';
		createDialog('networkGraphDialog',
				"webpage/com/glaway/ids/jgraph/web/network.html?m=1&paramArr="
				+ ($('#projectId').val())+'_'+currProjectContext.substring(1,currProjectContext.length)+'_'+ $('#switchPlanView').combotree('getValue'));
	}

	var tabTitle = '';

	function manageBasicLine() {
		$('#moreMenu').menu('hide', {});
		var projectId = $('#projectId').val();
		tabTitle = '<spring:message code="com.glaway.ids.pm.project.plan.basicLine.basicManager"/>';
		try{
			top.$('#maintabs').tabs('close', tabTitle);
			top.addTab(tabTitle, '${webRoot}/basicLineController.do?basicLine&projectId='
					+ projectId+"&isIframe=true&afterIframe=true", 'pictures')
		}catch(e){
			top.addTab(tabTitle, '${webRoot}/basicLineController.do?basicLine&projectId='+ projectId+"&isIframe=true&afterIframe=true", 'pictures')
		}
	}

	function treeLoadSuccess() {
		// 选中计划分解时所对应的计划任务
		var taskDetailGetPlanId = '${taskDetailGetPlanId}';
		if (taskDetailGetPlanId != "") {
			dom.mygrid.selectRowById(taskDetailGetPlanId);
			dom.mygrid.openItem(taskDetailGetPlanId);
		}
	}

	function importPlanTem(iframe) {
		$('#btn_sub', iframe.document).click();
		return false;
	}

	function alertList(newValue, oldValue) {
		var showColumnIds = $('#planListShowColumn').combo('getValues');
		var planViewInfoId = $('#switchPlanView').combotree('getValue');
		var url = '';
		var dialogId = '';
		switch(newValue) {
				//更新视图
			case '1':
				url = "planViewController.do?doUpdateView&showColumnIds="+showColumnIds;
				break;
				//另存视图
			case '2':
				userName = $('#searchOwner').val();
				progressRate = $('#searchProgressRate').val();
				if (progressRate < 0) {
					progressRate = -1;
				}
				workTime = $('#searchWorkTime').val();
				if (workTime < 0) {
					workTime = -1;
				}
				planNumber = $('#searchPlanNumber').val();
				planName = $('#searchPlanName').val();

				var bizCurrentAll = $('#searchBizCurrent').combobox('getValues');
				for(var i in bizCurrentAll){
					if(i == 0){
						bizCurrent = bizCurrentAll[i]
					}else{
						bizCurrent = bizCurrent + "," + bizCurrentAll[i];
					}
				}

				var planLevelAll = $('#searchPlanLevel').combobox('getValues');
				for(var i in planLevelAll){
					if(i == 0){
						planLevel = planLevelAll[i]
					}else{
						planLevel = planLevel + "," + planLevelAll[i];
					}
				}

				var isDelayAll = $('#searchIsDelay').combobox('getValues');
				for(var i in isDelayAll){
					if(i == 0){
						isDelay = isDelayAll[i]
					}else{
						isDelay = isDelay + "," + isDelayAll[i];
					}
				}


				var taskNameTypeAll = $('#searchTaskNameType').combobox('getValues');
				for(var i in taskNameTypeAll){
					if(i == 0){
						taskNameType = taskNameTypeAll[i]
					}else{
						taskNameType = taskNameType + "," + taskNameTypeAll[i];
					}
				}

				var taskTypeAll = $('#searchTaskType').combobox('getValues');
				for(var i in taskTypeAll){
					if(i == 0){
						taskType = taskTypeAll[i]
					}else{
						taskType = taskType + "," + taskTypeAll[i];
					}
				}

				$('#seachPlanTag').find('*').each(function() {
				/*	if ($(this).attr('name') == 'Plan.planLevelInfo.id') {
						if (planLevel == '') {
							planLevel = $(this).val();
						} else {
							planLevel = planLevel + "," + $(this).val();
						}
					}
					if ($(this).attr('name') == 'Plan.isDelay') {
						if (isDelay == '') {
							isDelay = $(this).val();
						} else {
							isDelay = isDelay + "," + $(this).val();
						}
					}*/
					/*if ($(this).attr('name') == 'Plan.bizCurrent') {
						if (bizCurrent == '') {
							bizCurrent = $(this).val();
						} else {
							bizCurrent = bizCurrent + "," + $(this).val();
						}
					}*/

					if ($(this).attr('name') == 'Plan.workTime_condition') {
						workTime_condition = $(this).val();
					}

					if ($(this).attr('name') == 'Plan.progressRate_condition') {
						progressRate_condition = $(this).val();
					}

					/*if ($(this).attr('name') == 'Plan.taskNameType') {
						if (taskNameType == '') {
							taskNameType = $(this).val();
						} else {
							taskNameType = taskNameType + "," + $(this).val();
						}
					}*/

					/*if ($(this).attr('name') == 'Plan.taskType') {
						if (taskType == '') {
							taskType = $(this).val();
						} else {
							taskType = taskType + "," + $(this).val();
						}
					}*/
				});
debugger;
				var planStartTime_Begin = $('#planDateRange_BeginDate').datebox(
						'getValue');
				var planStartTime_End = $('#planDateRange_EndDate').datebox('getValue');
				var planEndTime_Begin = $('#planEndDateRange_BeginDate').datebox(
						'getValue');
				var planEndTime_End = $('#planEndDateRange_EndDate')
						.datebox('getValue')

				if (planStartTime_Begin != null && planStartTime_Begin != ''
						&& planStartTime_Begin != undefined
						&& planStartTime_End != null && planStartTime_End != ''
						&& planStartTime_End != undefined) {
					planStartTime = planStartTime_Begin + "," + planStartTime_End;
				}
				if (planEndTime_Begin != null && planEndTime_Begin != ''
						&& planEndTime_Begin != undefined && planEndTime_End != null
						&& planEndTime_End != '' && planEndTime_End != undefined) {
					planEndTime = planEndTime_Begin + "," + planEndTime_End;
				}
				url = 'planViewController.do?goViewSaveAs&viewId='+ planViewInfoId+'&showColumnIds='+showColumnIds+'&planNumber='+planNumber+'&planName='+planName+'&isDelay='+isDelay+'&planLevel='+planLevel+'&bizCurrent='+bizCurrent+'&userName='+userName+'&planStartTime='+planStartTime+'&planEndTime='+planEndTime+'&workTime='+workTime+'&workTime_condition='+workTime_condition+'&progressRate='+progressRate+'&progressRate_condition='+progressRate_condition+'&taskNameType='+taskNameType+'&taskType='+taskType;
				dialogId = 'saveAsViewDialog';
				break;
				//按部门设置
			case '3':
				url = "planViewController.do?goDepartment"
				dialogId = 'setByDepartmentDialog';
				break;
				//按时间设置视图
			case '4':
				url = 'planViewController.do?goPlanViewTime';
				dialogId='addViewTimeDialog';
				break;
				//自定义视图
			case '5':
				url = 'planViewController.do?goCustomPlanView&projectId=${projectId}&isModify=${isModify}&planRevocationOperationCode=${planRevocationOperationCode}&planModifyOperationCode=${planModifyOperationCode}&planAssignOperationCode=${planAssignOperationCode}&planDeleteOperationCode=${planDeleteOperationCode}&planChangeOperationCode=${planChangeOperationCode}&planDiscardOperationCode=${planDiscardOperationCode}&isPmo=${isPmo}&isProjectManger=${isProjectManger}&planConcernOperationCode=${planConcernOperationCode}&planUnconcernOperationCode=${planUnconcernOperationCode}';
				dialogId='addCustomPlanViewDialog';
				break;
				//视图管理
			case '6':
				url = "planViewController.do?goViewManagement&projectId=${projectId}&defaultViewId="+ planViewInfoId;
				dialogId = 'viewManagementDialog';
				break;
			default :
				break;
		}
		if(newValue != '') {
			if(newValue == '2' || newValue == '3' || newValue == '4' || newValue == '5' || newValue == '6') {
				$("#"+dialogId).lhgdialog("open", "url:"+url);
				$('#setPlanView').combobox('setValue', '');
			}
			if(newValue=='1') {
				saveViewSearchInfo(planViewInfoId, url);
			}

		}
	}

	function setByDepartment(iframe) {
		iframe.saveSetConditionByDept();
		return false;
	}

	function customPlanViewConfirm(iframe) {
		iframe.saveCustomView();
		return false;
	}

	var switchPlanListFlag = false;
	function switchPlanList(newVal,oldVal){
        //console.log("arg======================"+JSON.stringify(arguments))
		if(!switchPlanListFlag) {
			switchPlanListFlag = true;
			return;
		}
		debugger;
		var oldId = $('#oldTreeId').val();
		if(oldId == newVal){
			setTimeout(function(){
				setPlanViewName();
			},10);
			return false;
		}
		var treeValue=$("#switchPlanView").comboztree("getSelected");

		if(treeValue[0].pid == 'null' || treeValue[0].pid == null || treeValue[0].pid == ''){
            $("#switchPlanView").comboztree("setValue",oldVal);
			$("#switchPlanView").comboztree("setText",$("#oldTreeName").val());
		    return false;
        }
        $("#oldTreeName").val(treeValue[0].name);
        $("#oldTreeId").val(treeValue[0].id);
		$.ajax({
			type : 'POST',
			data : {
				planViewId : treeValue[0].id
			},
			url:'planController.do?reloadPlanListWithView&projectId=${projectId}&isModify=${isModify}&planRevocationOperationCode=${planRevocationOperationCode}&planModifyOperationCode=${planModifyOperationCode}&planAssignOperationCode=${planAssignOperationCode}&planDeleteOperationCode=${planDeleteOperationCode}&planChangeOperationCode=${planChangeOperationCode}&planDiscardOperationCode=${planDiscardOperationCode}&isPmo=${isPmo}&isProjectManger=${isProjectManger}&planConcernOperationCode=${planConcernOperationCode}&planUnconcernOperationCode=${planUnconcernOperationCode}',
			cache:false,
			success:function(data){
				var d = $.parseJSON(data);
				$.ajax({
					type:'POST',
					data:{
						planViewId : treeValue[0].id,
						projectId : '${projectId}'
					},
					url:'planController.do?saveUseProjectPlanViewLink',
					cache:true,
					success:function(data){
						var obj = $.parseJSON(data);
						if(obj.success){
							tagSearchReset();
							$.ajax({
								type:'POST',
								data:{
									planViewId : treeValue[0].id
								},
								url:'planController.do?getSearchCondition',
								cache:false,
								success:function(data){
									var a = [];
									var d1 = $.parseJSON(data);
									var arr = eval(d1.obj);
									$.each(arr,function(i,v){
										if(v.attributeName == "Plan.planName"){
											$("#searchPlanName").textbox('setValue',v.attributeValue);
										}else if(v.attributeName == "Plan.isDelay"){
											$("#searchIsDelay").combobox('setValue',v.attributeValue);
										}else if(v.attributeName == "Plan.planLevelInfo.id"){
											$("#searchPlanLevel").combobox('setValues',v.attributeValue.split(','));
										}else if(v.attributeName == "Plan.bizCurrent"){
											$("#searchBizCurrent").combobox('setValues',v.attributeValue.split(','));
										}else if(v.attributeName == "Plan.planNumber"){
											$("#searchPlanNumber").textbox('setValue',v.attributeValue);
										}else if(v.attributeName == "Plan.owner"){
											$("#searchOwner").textbox('setValue',v.attributeValue);
										}else if(v.attributeName == "Plan.planStartTime"){
											$("#planDateRange_BeginDate").datebox("setValue",v.attributeValue.split(',')[0]);
											$("#planDateRange_EndDate").datebox("setValue",v.attributeValue.split(',')[1]);
										}else if(v.attributeName == "Plan.planEndTime"){
											$("#planEndDateRange_BeginDate").datebox("setValue",v.attributeValue.split(',')[0]);
											$("#planEndDateRange_EndDate").datebox("setValue",v.attributeValue.split(',')[1]);
										}else if(v.attributeName == "Plan.workTime"){
											$("#searchWorkTime").textbox('setValue',v.attributeValue);
											$('#glaway_input_readonly_searchWorkTime select').combobox('setValue', v.attributeCondition);
										}else if(v.attributeName == "Plan.progressRate"){
											$("#searchProgressRate").textbox('setValue',v.attributeValue);
											$('#glaway_input_readonly_searchProgressRate select').combobox('setValue', v.attributeCondition);
										}else if(v.attributeName == "Plan.taskNameType"){
											$("#searchTaskNameType").combobox('setValues',v.attributeValue.split(','));
										}else if(v.attributeName == "Plan.taskType"){
											$("#searchTaskType").combobox('setValues',v.attributeValue.split(','));
										}
									});

								}
							});
							$.ajax({
								type:'POST',
								data:{
									planViewId : treeValue[0].id
								},
								url:'planController.do?getShowColumn',
								cache:false,
								success:function(data){
									var dat = $.parseJSON(data);
									var arr1 = eval(dat.obj);
									$('#planListShowColumn').combo('setValues', arr1[0].columnId);
									if(arr1[0].columnId !='') {

										$('#sp input').each(function(){


											var flag = false;
											for(var i=0; i<arr1[0].columnId.split(',').length; i++) {
												if($(this).val()==arr1[0].columnId.split(',')[i]) {
													flag = true;
													break;
												}
											}
											if(flag) {
												$(this).attr('checked', true);
												$(this).parent('div').css('background-color','#FFE6B0');
											}else{
												$(this).attr('checked', false);
												$(this).parent('div').css('background-color','');
											}
										});
									}
									$('#planListShowColumn').combo('setText', dat.msg.split(',<br/>')[0]);
								}
							});

							loadUrl();
						}
					}
				});


			}
		});
	}

	function setPlanViewName(){
		$("#switchPlanView").comboztree("setText",$("#oldTreeName").val());
	}

	function saveViewDialog(iframe)
	{
		iframe.saveAsNewView();
		return false;
	}

	function setViewBy4() {
		var dialogUrl = 'planViewController.do?goPlanViewTime&viewId='
				+ $('#projectId').val()
				+ '$viewId=' + '${viewId}';
		$("#"+'addViewTimeDialog').lhgdialog("open", "url:"+dialogUrl);
	}

	function saveViewSearchInfo(planViewInfoId, url) {
		var userName = $('#searchOwner').val();
		var progressRate = $('#searchProgressRate').val();
		if (progressRate < 0) {
			progressRate = -1;
		}
		var workTime = $('#searchWorkTime').val();
		if (workTime < 0) {
			workTime = -1;
		}
		var planNumber = $('#searchPlanNumber').val();
		var planName = $('#searchPlanName').val();

		var planStartTime = '';
		var planEndTime = '';
		var planStartTime_Begin = $('#planDateRange_BeginDate').datebox(
				'getValue');
		var planStartTime_End = $('#planDateRange_EndDate').datebox('getValue')
		var planEndTime_Begin = $('#planEndDateRange_BeginDate').datebox(
				'getValue');
		var planEndTime_End = $('#planEndDateRange_EndDate')
				.datebox('getValue')
		var bizCurrent = '';
		var planLevel = '';
		var workTime_condition = '';
		var progressRate_condition = '';
		var taskNameType = '';
		var taskType = '';
		var isDelay = '';
		if (planStartTime_Begin != null && planStartTime_Begin != ''
				&& planStartTime_Begin != undefined
				&& planStartTime_End != null && planStartTime_End != ''
				&& planStartTime_End != undefined) {
			planStartTime = planStartTime_Begin + "," + planStartTime_End;
		}
		if (planEndTime_Begin != null && planEndTime_Begin != ''
				&& planEndTime_Begin != undefined && planEndTime_End != null
				&& planEndTime_End != '' && planEndTime_End != undefined) {
			planEndTime = planEndTime_Begin + "," + planEndTime_End;
		}

		$('#seachPlanTag').find('*').each(function() {
			if ($(this).attr('name') == 'Plan.planLevelInfo.id') {
				if (planLevel == '') {
					planLevel = $(this).val();
				} else {
					planLevel = planLevel + "," + $(this).val();
				}
			}
			if ($(this).attr('name') == 'Plan.isDelay') {
				if (isDelay == '') {
					isDelay = $(this).val();
				} else {
					isDelay = isDelay + "," + $(this).val();
				}
			}
			if ($(this).attr('name') == 'Plan.bizCurrent') {
				if (bizCurrent == '') {
					bizCurrent = $(this).val();
				} else {
					bizCurrent = bizCurrent + "," + $(this).val();
				}
			}
			if ($(this).attr('name') == 'Plan.workTime_condition') {
				workTime_condition = $(this).val();
			}

			if ($(this).attr('name') == 'Plan.progressRate_condition') {
				progressRate_condition = $(this).val();
			}

			if ($(this).attr('name') == 'Plan.taskNameType') {
				if (taskNameType == '') {
					taskNameType = $(this).val();
				} else {
					taskNameType = taskNameType + "," + $(this).val();
				}
			}

			if ($(this).attr('name') == 'Plan.taskType') {
				if (taskType == '') {
					taskType = $(this).val();
				} else {
					taskType = taskType + "," + $(this).val();
				}
			}
		});
		$.ajax({
			url : url,
			type : 'post',
			data : {
				planViewInfoId : planViewInfoId,
				planNumber : planNumber,
				planName : planName,
				isDelay : isDelay,
				planLevel : planLevel,
				bizCurrent : bizCurrent,
				userName : userName,
				planStartTime : planStartTime,
				planEndTime : planEndTime,
				workTime : workTime,
				workTime_condition : workTime_condition,
				progressRate : progressRate,
				progressRate_condition : progressRate_condition,
				taskNameType : taskNameType,
				taskType : taskType
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				top.tip(d.msg);
				$('#setPlanView').combobox('setValue', '');
				if (d.success) {
					//重新加载列表数据
					callBackRefreshList(planViewInfoId);
				}
			}
		});
	}

	//刷新视图树
	function switchPlanTreeReload(isSwitch,id,name) {
		var treeObj = $.fn.zTree.getZTreeObj("switchPlanViewTree");
		treeObj.reAsyncChildNodes(null, "refresh");
		if(isSwitch) {
			setTimeout(function(){
				if(isSwitch){
					$('#switchPlanView').comboztree('setValue', id);
					$('#switchPlanView').comboztree('setText', name);
					callBackRefreshList(id);
				}
			},1000);
		}
	}

	function callBackRefreshList1(planViewInfoId) {
		$.ajax({
			type : 'POST',
			data : {
				planViewId : planViewInfoId
			},
			url:'planController.do?reloadPlanListWithView&projectId=${projectId}&isModify=${isModify}&planRevocationOperationCode=${planRevocationOperationCode}&planModifyOperationCode=${planModifyOperationCode}&planAssignOperationCode=${planAssignOperationCode}&planDeleteOperationCode=${planDeleteOperationCode}&planChangeOperationCode=${planChangeOperationCode}&planDiscardOperationCode=${planDiscardOperationCode}&isPmo=${isPmo}&isProjectManger=${isProjectManger}&planConcernOperationCode=${planConcernOperationCode}&planUnconcernOperationCode=${planUnconcernOperationCode}',
			cache:false,
			success:function(data){
				var d = $.parseJSON(data);
				var header = d.msg.split('<br/>')[0];
				mygrid.clearAll();
				mygrid.setHeader("编号,计划名称");
				mygrid.setColumnIds("planNumber,planName");
				mygrid.setColTypes('txt,txt');
				mygrid.init();
				var js = {rows:[{'id':'1','planNumber':'1234','planName':'werwer'}]};
				mygrid.parse(js,'jsarray');
			}
		});
	}

	function callBackRefreshList(planViewInfoId) {
		$.ajax({
			type : 'POST',
			data : {
				planViewId : planViewInfoId
			},
			url:'planController.do?reloadPlanListWithView&projectId=${projectId}&isModify=${isModify}&planRevocationOperationCode=${planRevocationOperationCode}&planModifyOperationCode=${planModifyOperationCode}&planAssignOperationCode=${planAssignOperationCode}&planDeleteOperationCode=${planDeleteOperationCode}&planChangeOperationCode=${planChangeOperationCode}&planDiscardOperationCode=${planDiscardOperationCode}&isPmo=${isPmo}&isProjectManger=${isProjectManger}&planConcernOperationCode=${planConcernOperationCode}&planUnconcernOperationCode=${planUnconcernOperationCode}',
			cache:false,
			success:function(data){
				var d = $.parseJSON(data);
				dom.mygrid.clearAll();
				dom.mygrid.parse(d.obj, 'js');
				loadUrl();
			}
		});
	}

	//刷新
	function refreshViewTree(id, name, type) {
		var treeObj = $.fn.zTree.getZTreeObj("switchPlanViewTree");
		treeObj.reAsyncChildNodes(null, "refresh");
		setTimeout(function(){
			if(type == 'delete'){
				var newId = $('#switchPlanView').comboztree('getValue');
				if(id == newId) {
					tagSearchReset();
					$('#switchPlanView').comboztree('setValue', '<spring:message code="com.glaway.ids.pm.project.planview.wholePlanId"/>');
					$('#switchPlanView').comboztree('setText', '<spring:message code="com.glaway.ids.pm.project.planview.wholePlanName"/>');
					callBackRefreshList('<spring:message code="com.glaway.ids.pm.project.planview.wholePlanId"/>');
				}
			}
			else if(type == 'update') {
				var newId = $('#switchPlanView').comboztree('getValue');
				if(id == newId) {
					$('#switchPlanView').comboztree('setText', name);
				}
			}
		},1000);
	}

	function refreshViewTreeByPublish(projectIds, userIds, planViewInfoId, name) {
		var treeObj = $.fn.zTree.getZTreeObj("switchPlanViewTree");
		treeObj.reAsyncChildNodes(null, "refresh");
		setTimeout(function(){
			var defaultViewId = $('#switchPlanView').comboztree('getValue');
			if(defaultViewId == planViewInfoId &&
					(projectIds == '' || projectIds.indexOf('${projectId}') != -1) &&
					(userIds == '' || userIds.indexOf('${currentUserId}') != -1)) {
				$('#switchPlanView').comboztree('setText', name);
			} else {
				tagSearchReset();
				$('#switchPlanView').comboztree('setValue', '<spring:message code="com.glaway.ids.pm.project.planview.wholePlanId"/>');
				$('#switchPlanView').comboztree('setText', '<spring:message code="com.glaway.ids.pm.project.planview.wholePlanName"/>');
				callBackRefreshList('<spring:message code="com.glaway.ids.pm.project.planview.wholePlanId"/>');
			}
		},1000);
	}

	function exportPlanExcel() {
		$('#moreMenu').menu('hide', {});
		window.location.href = "planController.do?exportXls&projectId=${projectId}";
	}

	function exportPlanExcelSingle() {
		postFormDataToAction("planController.do?exportXlsSingle&planId="
				+ dom.mygrid.getSelectedRowId());
	}

	function importPlanExcel(iframe) {
		saveOrUp(iframe);
		return false;
	}

	function plan_downErrorReport(dataListAndErrorMap) {
		top.Alert.confirm(
				'<spring:message code="com.glaway.ids.common.confirmDownloadErrorReport"/>',
				function(r) {
					if (r) {
						var url = 'planController.do?downErrorReport';
						downloadErrorReport(url,dataListAndErrorMap);
					}
				});
	}

	function showSearchForm(){

		if(showSearchFormFlag % 2 == 0){
			$("#seachPlanTag").show();

			var documentHeight = $(window).height();
			var obj = document.getElementById("left_page_panel");
			var northDivHeight = obj.offsetHeight;
			$("#datagridDiv").css({'height':documentHeight-northDivHeight+'px'});
			mygridHeight = documentHeight-northDivHeight - 30+'px';

			var loadUrl = 'planController.do?goDatagridPage&isIframe=true&mygridHeight='+mygridHeight+'&isViewPage=${isViewPage}&columnIds=${columnIds}&projectId=${projectId}&isModify=${isModify}&planRevocationOperationCode=${planRevocationOperationCode}&planModifyOperationCode=${planModifyOperationCode}&planAssignOperationCode=${planAssignOperationCode}&planDeleteOperationCode=${planDeleteOperationCode}&planChangeOperationCode=${planChangeOperationCode}&planDiscardOperationCode=${planDiscardOperationCode}&isPmo=${isPmo}&isProjectManger=${isProjectManger}&planConcernOperationCode=${planConcernOperationCode}&planUnconcernOperationCode=${planUnconcernOperationCode}';
			loadPage("#right_page_panel_plan", loadUrl + '&refreshTree=true', "");

			dom = $("#right_page_panel_plan iframe")[0].contentWindow;

			showSearchFormFlag++;
		}else{
			$("#seachPlanTag").hide();

			var documentHeight = $(window).height();
			var obj = document.getElementById("left_page_panel");
			var northDivHeight = obj.offsetHeight;
			$("#datagridDiv").css({'height':documentHeight-northDivHeight+'px'});
			mygridHeight = documentHeight-northDivHeight - 30+'px';

			var loadUrl = 'planController.do?goDatagridPage&isIframe=true&mygridHeight='+mygridHeight+'&isViewPage=${isViewPage}&columnIds=${columnIds}&projectId=${projectId}&isModify=${isModify}&planRevocationOperationCode=${planRevocationOperationCode}&planModifyOperationCode=${planModifyOperationCode}&planAssignOperationCode=${planAssignOperationCode}&planDeleteOperationCode=${planDeleteOperationCode}&planChangeOperationCode=${planChangeOperationCode}&planDiscardOperationCode=${planDiscardOperationCode}&isPmo=${isPmo}&isProjectManger=${isProjectManger}&planConcernOperationCode=${planConcernOperationCode}&planUnconcernOperationCode=${planUnconcernOperationCode}';
			loadPage("#right_page_panel_plan", loadUrl + '&refreshTree=true', "");

			dom = $("#right_page_panel_plan iframe")[0].contentWindow;

			showSearchFormFlag++;
		}
	}


	function viewSearchPlanList() {


		var treeValue=$("#switchPlanView").comboztree("getSelected");

		var userName = $('#searchOwner').val();
		var progressRate = $('#searchProgressRate').val();
		if (progressRate < 0) {
			progressRate = -1;
		}
		var workTime = $('#searchWorkTime').val();
		if (workTime < 0) {
			workTime = -1;
		}
		var planNumber = $('#searchPlanNumber').val();
		var planName = $('#searchPlanName').val();

		var planStartTime = '';
		var planEndTime = '';
		var planStartTime_Begin = $('#planDateRange_BeginDate').datebox(
				'getValue');
		var planStartTime_End = $('#planDateRange_EndDate').datebox('getValue')
		var planEndTime_Begin = $('#planEndDateRange_BeginDate').datebox(
				'getValue');
		var planEndTime_End = $('#planEndDateRange_EndDate')
				.datebox('getValue')

		if (planStartTime_Begin != null && planStartTime_Begin != ''
				&& planStartTime_Begin != undefined
				&& planStartTime_End != null && planStartTime_End != ''
				&& planStartTime_End != undefined) {
			planStartTime = planStartTime_Begin + "," + planStartTime_End;
		}
		if (planEndTime_Begin != null && planEndTime_Begin != ''
				&& planEndTime_Begin != undefined && planEndTime_End != null
				&& planEndTime_End != '' && planEndTime_End != undefined) {
			planEndTime = planEndTime_Begin + "," + planEndTime_End;
		}

		var bizCurrent = '';
		var planLevel = '';
		var workTime_condition = '';
		var progressRate_condition = '';
		var taskNameType = '';
		var taskType = '';
		var isDelay = '';
		$('#seachPlanTag').find('*').each(function() {
			if ($(this).attr('name') == 'Plan.planLevelInfo.id') {
				if (planLevel == '') {
					planLevel = $(this).val();
				} else {
					planLevel = planLevel + "," + $(this).val();
				}
			}
			if ($(this).attr('name') == 'Plan.isDelay') {
				if (isDelay == '') {
					isDelay = $(this).val();
				} else {
					isDelay = isDelay + "," + $(this).val();
				}
			}
			if ($(this).attr('name') == 'Plan.bizCurrent') {
				if (bizCurrent == '') {
					bizCurrent = $(this).val();
				} else {
					bizCurrent = bizCurrent + "," + $(this).val();
				}
			}
			if ($(this).attr('name') == 'Plan.workTime_condition') {
				workTime_condition = $(this).val();
			}

			if ($(this).attr('name') == 'Plan.progressRate_condition') {
				progressRate_condition = $(this).val();
			}

			if ($(this).attr('name') == 'Plan.taskNameType') {
				if (taskNameType == '') {
					taskNameType = $(this).val();
				} else {
					taskNameType = taskNameType + "," + $(this).val();
				}
			}

			if ($(this).attr('name') == 'Plan.taskType') {
				if (taskType == '') {
					taskType = $(this).val();
				} else {
					taskType = taskType + "," + $(this).val();
				}
			}
		});

		$.ajax({
			url : 'planController.do?reloadPlanListWithView&projectId=${projectId}&isModify=${isModify}&planRevocationOperationCode=${planRevocationOperationCode}&planModifyOperationCode=${planModifyOperationCode}&planAssignOperationCode=${planAssignOperationCode}&planDeleteOperationCode=${planDeleteOperationCode}&planChangeOperationCode=${planChangeOperationCode}&planDiscardOperationCode=${planDiscardOperationCode}&isPmo=${isPmo}&isProjectManger=${isProjectManger}&planConcernOperationCode=${planConcernOperationCode}&planUnconcernOperationCode=${planUnconcernOperationCode}',
			type : 'post',
			data : {
				planNumber_ : planNumber,
				planViewId : treeValue[0].id,
				planName : planName,
				isDelay : isDelay,
				planLevel : planLevel,
				bizCurrent : bizCurrent,
				userName : userName,
				planStartTime : planStartTime,
				planEndTime : planEndTime,
				workTime : workTime,
				workTime_condition : workTime_condition,
				progressRate : progressRate,
				progressRate_condition : progressRate_condition,
				taskNameType : taskNameType,
				taskType : taskType
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				dom.mygrid.clearAll();
				dom.mygrid.parse(d.obj, 'js');
			}
		});
	}


	function refreshAfterDel(id){
	    dom.refreshAfterDel(id);
    }

</script>

</body>
</html>
