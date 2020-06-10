<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html id="123">
<head>
<title>新建计划</title>
	<t:base type="jquery,easyui_iframe,tools,DatePicker,lhgdialog"></t:base>
	<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>

</head>
<body>
	
	<div id="plan" border="false" class="easyui-panel div-msg" fit="true">
	<fd:toolbar help="helpDoc:CreatePlan"></fd:toolbar>
		<div class="easyui-panel" fit="true" id="zhezhao">
			<fd:form id="planAddForm">
				<input id="id" name="id" type="hidden"  value="${plan_.id}">
				<input id="useObjectId" name="useObjectId" type="hidden" value="${useObjectId}">
				<input id="useObjectType" name="useObjectType" type="hidden" value="${useObjectType}">
				<input id="isModifyPage" name="isModifyPage" type="hidden" value="${disabled}">
				<input id="planNumber" name="planNumber" type="hidden" value="${plan_.planNumber}">
				<input id="projectId" name="projectId" type="hidden" value="${plan_.projectId}">
				<input id="parentPlanId" name="parentPlanId" type="hidden" value="${plan_.parentPlanId}">
				<input id="beforePlanId" name="beforePlanId" type="hidden" value="${plan_.beforePlanId}">
				<input id="bizCurrent" name="bizCurrent" type="hidden" value="${plan_.bizCurrent}">
				<input id="preposeList" name="preposeList" type="hidden" value="${plan_.preposeList}">
				<input id="flowStatus" name="flowStatus" type="hidden" value="${plan_.flowStatus}">
				<input id="rescLinkInfoList" name="rescLinkInfoList" type="hidden" value="${plan_.rescLinkInfoList}">
				<input id="parentStartTime" name="parentStartTime" type="hidden" value="${parentStartTime}">
				<input id="parentEndTime" name="parentEndTime" type="hidden" value="${parentEndTime}">
				<input id="preposeEndTime" name="preposeEndTime" type="hidden" value="${preposeEndTime}">
				<input id="departList" name="departList" type="hidden" value="${departList}">
				<input id="isStandard" name="isStandard" type="hidden" value="${isStandard}">
				<input id="userList" name="userList" type="hidden" value="${userList}">
				<input id="userList2" name="userList2" type="hidden" value="${userList2}">		
				<input id="planStartTimeRm" name="planStartTimeRm" type="hidden" value="${planStartTimeRm}">	
				<input id="planEndTimeRm" name="planEndTimeRm" type="hidden" value="${planEndTimeRm}">		
				<input id="ownerShow" name="ownerShow" type="hidden" value="${ownerShow}">	
				<input id="planLevelShow" name="planLevelShow" type="hidden" value="${planLevelShow}">
				<input id="planLevelList" name="planLevelList" type="hidden" value="${planLevelList}">		
				<div class="gla_div">
					<ul>
						<li>
							<div class="gla_step">
								<table class="step_dot">
									<tr>
										<td class="stepFirst"><i></i><div><span>1</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/></div></div></td>
										<td class="stepEnd"><i></i><div><span>2</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.input"/></div></div></td>									
										<td class="stepEnd"><i></i><div><span>3</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.output"/></div></div></td>
										<td class="stepEnd"><i></i><div><span>4</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.resource"/></div></div></td>
									</tr>
								</table>
							</div>
						</li>
					</ul>
				</div>
				<div id="td1">
					<c:choose>
						<c:when test="${isStandard}">
							<fd:combobox id="planName2" textField="name" title="{com.glaway.ids.pm.project.plan.planName}" required="true" selectedValue="${plan_.planName}" 
									editable="true" valueField="id" panelMaxHeight="200" maxLength="50" name="planName2"
									url="planController.do?standardValueExceptDesign" onChange="nameTaskTypeChange('true')"></fd:combobox>
						</c:when>
						<c:otherwise>
							<fd:inputText id="planName" name="planName" title="{com.glaway.ids.pm.project.plan.planName}" required="true" value="${plan_.planName}" onChange="nameTaskTypeChange('false')"/>
						</c:otherwise>
					</c:choose>
				</div>
				<div id="td2">
					<fd:inputText id="planName3" name="planName3" title="{com.glaway.ids.pm.project.plan.planName}" readonly="true"  required="true" value="${plan_.planName}" />
				</div>
				
				<fd:combobox id="owner" textField="realName" title="{com.glaway.ids.common.lable.owner}" selectedValue="${ownerShow}" 
						onChange="ownerChange()" editable="true" valueField="id" required="true" 
					 	panelMaxHeight="200" url="planController.do?userList2&projectId=${plan_.projectId}" />
			
				<fd:combobox id="planLevel" textField="name" title="{com.glaway.ids.pm.project.plan.planLevel}" selectedValue="${planLevelShow}" 
						editable="false" valueField="id" url="planController.do?useablePlanLevelList" />
	
				<fd:inputText id="ownerDept" title="{com.glaway.ids.pm.project.plan.dept}" readonly="true" name="ownerDept" value="${plan_.ownerDept}" />
		
				<fd:inputText id="workTime" title="{com.glaway.ids.pm.project.plan.workTime}" value='${plan_.workTime}' onChange="workTimeLinkage('workTime')" />
	
				<fd:combobox id="milestone" textField="name" title="{com.glaway.ids.pm.project.plan.milestone}" selectedValue="${plan_.milestone}"  
						editable="false" valueField="id" required="true" url="planController.do?milestoneList" onChange="milestoneChange()" />
			
				<fd:inputDate id="planStartTime" title="{com.glaway.ids.common.lable.starttime}" value='${plan_.planStartTime}' 
						onChange="workTimeLinkage('planStartTime')" editable="false" required="true" />
			
				<fd:inputDate id="planEndTime" title="{com.glaway.ids.common.lable.endtime}" value='${plan_.planEndTime}' editable="false" 
						onChange="workTimeLinkage('planEndTime')" required="true" />
			
				<fd:combobox id="taskNameType" textField="typename" title="{com.glaway.ids.pm.project.plan.taskNameType}" selectedValue="${plan_.taskNameType}" 
						readonly="true"   editable="false" valueField="typecode"
					 	required="true" url="planController.do?getTaskNameTypes" />
			
				<fd:combobox id="taskType" textField="name" title="{com.glaway.ids.pm.project.plan.taskType}" selectedValue="${plan_.taskType}" 
						editable="false" valueField="name" required="true"
						url="planController.do?getTaskTypes&parentPlanId=${plan_.parentPlanId}&projectId=${plan_.projectId}" />
			
				<fd:inputSearch id="preposePlans" title="{com.glaway.ids.pm.project.plan.preposePlans}" value="${plan_.preposePlans}" editable="false" searcher="selectPreposePlan('#preposeIds','#preposePlans')" />		
				<input name="preposeIds" id="preposeIds" value="${plan_.preposeIds}" type="hidden">
			
				<fd:inputText title="{com.glaway.ids.common.lable.status}" id="status" name="status" value="${plan_.status}" readonly="true" />
				<!-- 
				<fd:inputText title="{com.glaway.ids.pm.project.projecttemplate.creator}" id="createFullName" name="createFullName"
						value="${plan_.createFullName}-${plan_.createName}" readonly="true" />
				
				<fd:inputDate title="{com.glaway.ids.pm.project.projecttemplate.createTime}" id="createTime" name="createTime"
						value="${plan_.createTime}" readonly="true" />
				 -->
				
				<fd:combobox id="relayOn" textField="relayOn" title="{com.glaway.ids.pm.project.plan.relayOn}" selectedValue="${plan_.relayOn}" 
						readonly="true"   editable="false" valueField="relayOn"
					 	required="true" url="planController.do?getRelayOns" />
				<fd:combobox id="limitType" textField="limitType" title="{com.glaway.ids.pm.project.plan.limitType}" selectedValue="${plan_.limitType}" 
						readonly="true"   editable="false" valueField="limitType"
					 	required="true" url="planController.do?getLimitTypes" />
					 	
				
				<fd:inputDate title="{com.glaway.ids.pm.project.projecttemplate.limitTime}" id="limitTime" name="limitTime"
						value="${plan_.limitTime}" readonly="true" />
				
				<c:if test="${plan_.bizCurrent == 'EDITING'}">
					<div style="display: none;">
				</c:if>
				<c:if test="${plan_.bizCurrent != 'EDITING'}">
					<div>
				</c:if>
					<c:if test="${plan_.assignerInfo.realName != '' && plan_.assignerInfo.realName != null}">
						<fd:inputText title="{com.glaway.ids.common.lable	.assigner}" id="assignerName" name="assignerName"
								value="${plan_.assignerInfo.realName}-${plan_.assignerInfo.userName}" readonly="true" />
					</c:if>
					<c:if test="${plan_.assignerInfo.realName == '' || plan_.assignerInfo.realName == null}">
						<fd:inputText title="{com.glaway.ids.common.lable.assigner}" id="assignerName" name="assignerName" 
								value="${plan_.assignerInfo.realName}" readonly="true" />
					</c:if>
					<fd:inputDate title="{com.glaway.ids.common.lable.assignTime}" id="assignTime" name="assignTime"
							value="${plan_.assignTime}" readonly="true" />
				</div>
					<fd:inputTextArea id="remark" title="{com.glaway.ids.pm.project.plan.remark}" name="remark" value="${plan_.remark}" />
				 <div style="display:inline-block;width:100%; margin-top:20px;"> 
				</div> 
			</fd:form>
		</div>
		
		<div class="div-msg-btn">
			<div>
				<fd:linkbutton onclick="nextZero()" value="{com.glaway.ids.pm.project.plan.nextone}" classStyle="button_nor" />
				<fd:linkbutton onclick="addSetTimeOut()" value="{com.glaway.ids.pm.project.plan.finish}" classStyle="button_nor" />
				<fd:linkbutton id="addPlanCancelBtn" onclick="closePlan()" value="{com.glaway.ids.common.btn.cancel}" classStyle="button_nor" />
			</div>
		</div>
	</div>
	
	
	<!-- 输入 -->
	<div border="false" class="div-msg-step div-msg" id="inputDiv" fit="true">
	    <fd:toolbar help="helpDoc:EditPlanInput"></fd:toolbar>
		<input id="inList" name="inList" type="hidden">
		<fd:form id="projLibDocAddForm">
			<input id="uploadPowerFlog" name="uploadPowerFlog" type="hidden" value="${uploadPowerFlog}">
			<input id="securityLevelFromFile" name="securityLevelFromFile" type="hidden" value="${securityLevelFromFile}">
			<input id="yanfa" name="yanfa" type="hidden" value="${yanfa}">
			<input id="treeType" name="treeType" type="hidden" value="1">
			<input id="fileId" name="fileId" type="hidden" value="${doc.id}">
			<input id="id" name="id" type="hidden">
			<input id="projectId" name="projectId"  type="hidden" value="${doc.projectId}">
			<input id="type" name="type" type="hidden" value="1">
			<input id="docattachmentName" name="docattachmentName" type="hidden">
			<input id="docattachmentURL" name="docattachmentURL" type="hidden">
			<input id="docAttachmentShowName" name="docAttachmentShowName" type="hidden">
			<input id="docSecurityLevelFrom" name="docSecurityLevelFrom" type="hidden">
			<input name="invalidIds" id="invalidIds" type="hidden">
			<input name="useObjectId" id="useObjectId" type="hidden" value = "${useObjectId}">
			<input name="useObjectType" id="useObjectType" type="hidden" value = "${useObjectType}">
		</fd:form>
		<div class="gla_div">
			<ul>
				<li>
					<div class="gla_step">
						<table class="step_dot">
							<tr>
								<td class="stepFirst"><i></i><div><span>1</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/></div></div></td>
								<td class="stepMiddle"><i></i><div><span>2</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.input"/></div></div></td>										
								<td class="stepEnd"><i></i><div><span>3</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.output"/></div></div></td>
								<td class="stepEnd"><i></i><div><span>4</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.resource"/></div></div></td>
							</tr>
						</table>
					</div>
				</li>
			</ul>
		</div>
		
		<div id="existencePrepose" class="easyui-panel" fit="true">
			<input name="invalidIds" id="invalidIds" type="hidden">			
					<fd:toolbar id="tagListInputs" >
					<div style="background-color:#F4F4F4; width: 100%; height:28px; padding: 0px;">	
						<fd:toolbarGroup align="left" >
							<fd:linkbutton onclick="addInputsNew()" value="新增输入项" id="addInputsNewButton" iconCls="basis ui-icon-plus" />
							<fd:uploadify iconCls="basis ui-icon-plus" buttonText="新增本地文档" width="120"
								name="up_button_import" id="up_button_import" title="新增本地文档"
								afterUploadSuccessMode="multi" 
								uploader="projLibController.do?addFileAttachments&projectId=${projectId}"
								extend="*.*" auto="true" showPanel="false" multi="false" 
								dialog="false" onlyButton="true">
								<fd:eventListener event="onUploadSuccess" listener="afterSuccessCall" />
							</fd:uploadify>
							<fd:linkbutton onclick="deleteSelectionsInputs('inputsList', 'inputsController.do?doBatchDelInputs')" iconCls="basis ui-icon-minus"
									value="{com.glaway.ids.common.btn.remove}" id="deleteInputsButton"/>
						</fd:toolbarGroup>
					</div>
					</fd:toolbar>
		
			<div  style="width: 100%; height:362px; padding: 0px;">
				<fd:datagrid id="inputsList" checkbox="true" pagination="false" checkOnSelect="true" toolbar="" 
						fitColumns="true"  idField="id" fit="true">
					<fd:colOpt title="操作" width = "30">
						<fd:colOptBtn iconCls="basis ui-icon-pddata" tipTitle="项目库关联" onClick="goProjLibLink" hideOption="projLibLinkOpt"/>
						<fd:colOptBtn iconCls="basis ui-icon-search" tipTitle="计划关联" onClick = "goPlanLink" hideOption = "planLinkOpt"/>
					</fd:colOpt>
					<fd:dgCol title="{com.glaway.ids.common.lable.name}" field="name" width="80"  sortable="false" formatterFunName="showAllName"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.docName}" field="docNameShow" width="80" formatterFunName="addLink"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.orgin}" field="originPath" formatterFunName = "showOrigin" width="80"  sortable="false" tipField="originPath"></fd:dgCol>
				</fd:datagrid>
			</div>	
		</div>
		<%-- <div class="easyui-panel" fit="true">
			<div id="inexistencePrepose" style="padding-top:5px;padding-left:20px;padding-right:5px;padding-bottom:400px;">
				<output>如果需要设置计划的输入，请先回到基本信息中选择“前置计划”</output>
			</div>
		</div> --%>
				
		<div class="div-msg-btn">
			<div>
				<fd:linkbutton onclick="beforeOne()" value="{com.glaway.ids.pm.project.plan.beforeone}" classStyle="button_nor" />
				<fd:linkbutton onclick="nextOne()" value="{com.glaway.ids.pm.project.plan.nextone}" classStyle="button_nor" />
				<fd:linkbutton onclick="addPlan()" value="{com.glaway.ids.pm.project.plan.finish}" classStyle="button_nor" />
				<fd:linkbutton id="addInputCancelBtn" onclick="closePlan()" value="{com.glaway.ids.common.btn.cancel}" classStyle="button_nor" />
			</div>
		</div>
	</div>
	<!-- 输入 -->
	
	
	<!-- 输出 -->
	<div border="false" class="div-msg-step div-msg" id="document" fit="true">
	    <fd:toolbar help="helpDoc:EditPlanOutput"></fd:toolbar>
		<input id="deliInfoList" name="deliInfoList" type="hidden">
		<input id="pdName" name="pdName" type="hidden" value="${pdName}">
		<div class="gla_div">
			<ul>
				<li>
					<div class="gla_step">
						<table class="step_dot">
							<tr>
								<td class="stepFirst"><i></i><div><span>1</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/></div></div></td>
								<td class="stepMiddle"><i></i><div><span>2</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.input"/></div></div></td>										
								<td class="stepMiddle"><i></i><div><span>3</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.output"/></div></div></td>
								<td class="stepEnd"><i></i><div><span>4</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.resource"/></div></div></td>
							</tr>
						</table>
					</div>
				</li>
			</ul>
		</div>
		
		<div class="easyui-panel" fit="true">
		<div style="width: 100%; height:28px; padding: 0px; fit="true">
			<fd:toolbar id="tagListDeliverables">
			<div  style="background-color:#F4F4F4">	
				<fd:toolbarGroup align="left">
					<fd:linkbutton value="{com.glaway.ids.pm.project.plan.inheritParent}" onclick="inheritParent()"
							iconCls="basis ui-icon-copy"  id ="inheritParent"/>
					<fd:linkbutton onclick="addDeliverable()" value="{com.glaway.ids.common.btn.create}" id="addDeliverableButton" iconCls="basis ui-icon-plus"/>
					<fd:linkbutton onclick="deleteSelections('deliverablesInfoList', 'deliverablesInfoController.do?doBatchDel')" iconCls="basis ui-icon-minus"
							value="{com.glaway.ids.common.btn.remove}" id="deleteDeliverableButton"/>
				</fd:toolbarGroup>
			</div>	
			</fd:toolbar>
	</div>
	<div style="width: 100%; height:392px; padding: 0px; fit="true">		
			<fd:datagrid id="deliverablesInfoList" checkbox="true" pagination="false"
					fitColumns="true" toolbar="#tagListDeliverables" idField="id" fit="true">
				<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.deliverableName}" field="name" width="120" formatterFunName="viewDocument"  sortable="false"></fd:dgCol>
				<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.docName}" field="file" width="120"  sortable="false"></fd:dgCol>
			</fd:datagrid>
		</div>
	</div>	
		<div class="div-msg-btn">
			<div>
				<fd:linkbutton onclick="beforeTwo()" value="{com.glaway.ids.pm.project.plan.beforeone}" classStyle="button_nor" />
				<fd:linkbutton onclick="nextTwo()" value="{com.glaway.ids.pm.project.plan.nextone}" classStyle="button_nor" />
				<fd:linkbutton onclick="addPlan()" value="{com.glaway.ids.pm.project.plan.finish}" classStyle="button_nor" />
				<fd:linkbutton id="addOutputCancelBtn" onclick="closePlan()" value="{com.glaway.ids.common.btn.cancel}" classStyle="button_nor" />
			</div>
		</div>
	</div>
	<!-- 输出 -->


	<!-- 资源 -->
	<div border="false" class="div-msg-step div-msg" id="resourcet" fit="true">
	    <fd:toolbar help="helpDoc:EditPlanResource"></fd:toolbar>
		<div class="gla_div">
			<ul>
				<li>
					<div class="gla_step">
						<table class="step_dot" >
							<tr>
								<td class="stepFirst"><i></i><div><span>1</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/></div></div></td>	
								<td class="stepMiddle"><i></i><div><span>2</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.input"/></div></div></td>								
								<td class="stepMiddle"><i></i><div><span>3</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.output"/></div></div></td>
								<td class="stepMiddle"><i></i><div><span>4</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.resource"/></div></div></td>
							</tr>
						</table>
					</div>
				</li>
			</ul>
		</div>
	
		<div class="easyui-panel" style="width: 100%; height:28px; padding: 0px; fit="true">
			<fd:toolbar id="tagListResource">
			<div style="background-color:#F4F4F4">	
				<fd:toolbarGroup align="left">
					<fd:linkbutton onclick="addResource()" value="{com.glaway.ids.pm.project.plan.resource.addResource}"  iconCls="basis ui-icon-plus"/>
					<fd:linkbutton iconCls="basis ui-icon-minus" onclick="deleteSelections2('resourceList', 'resourceLinkInfoController.do?doBatchDel')"
							value="{com.glaway.ids.pm.project.plan.resource.removeResource}" />
				</fd:toolbarGroup>
			</div>
			</fd:toolbar>
			
		</div>
		<div class="easyui-panel" style="width: 100%; height:330px; padding: 0px; fit="true">
			<fd:datagrid id="resourceList" checkbox="true" fitColumns="true" pagination="false" onClickFunName="onClickPlanRow" onDblClickRow="onDoubleClickPlanRow"
						toolbar="#tagListResource" idField="id" fit="true">
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.resourceName}" field="resourceName" width="120" formatterFunName="resourceNameLink"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.resourceType}" field="resourceType" width="150"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.common.lable.starttime}" field="startTime" width="80"  editor="'datebox'"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.common.lable.endtime}" field="endTime" width="80" editor="'datebox'"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.useRate}" field="useRate" width="80" formatterFunName="viewUseRate2" editor="'numberbox'"  sortable="false"></fd:dgCol>
				</fd:datagrid>
		</div>
		
		<div class="div-msg-btn">
			<div>
				<fd:linkbutton onclick="beforeThree()" value="{com.glaway.ids.pm.project.plan.beforeone}" classStyle="button_nor" />
				<fd:linkbutton onclick="addplanAndResource2()" value="{com.glaway.ids.pm.project.plan.finish}" classStyle="button_nor" />
				<fd:linkbutton id="addResourceCancelBtn" onclick="closePlan()" value="{com.glaway.ids.common.btn.cancel}" classStyle="button_nor" />
			</div>
		</div>
	</div>
	<!-- 资源 -->
</body>

<fd:dialog id="addInputsDialog" width="800px" height="400px" modal="true" title="新增输入">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addInputsDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="addDeliverableDialog" width="800px" height="450px" modal="true" title="新增输出">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addDeliverableDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="addResourceDialog" width="800px" height="350px" modal="true" title="新增计划资源">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addResourceDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="resourceDialog" width="900px" height="800px" modal="true" title="资源图" maxFun="true">
</fd:dialog>
<fd:dialog id="preposePlanDialog" width="850px" height="580px" modal="true" title="选择前置计划">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="preposePlanDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="showDocDetailChangeFlowDialog" modal="true" width="1000" height="550" title="文档详情">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog" ></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="inheritDialog" width="400px" height="300px" modal="true" title="{com.glaway.ids.pm.project.plan.inheritParentDocument}">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addInheritDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>		
<fd:dialog id="openSelectInputsDialog" width="800px" height="450px"
		modal="true" title="选择输入项">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
			callback="openSelectConfigOkFunction"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
			callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
<fd:dialog id="openSelectLocalDocDialog" width="800px" height="600px"
		modal="true" title="新增本地文档">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
			callback="projLibSubimt"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
			callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
<fd:dialog id="taskDocCheckLineDialog" width="1000px" height="500px" modal="true" title="项目库关联">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="taskDocCheckLineDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="planInputsDialog" width="800px" height="580px" modal="true" title="选择来源计划">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="planInputsDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<script>
		$(function(){
			/* $('#resourceList').datagrid({
				onUnselect: function(index, row){
					$('#resourceList').datagrid('endEdit', index);  
					$('#resourceList').datagrid('beginEdit', index); 
				}
			}); */
			if('${plan_.parentPlanId}' == ''|| '${plan_.parentPlanId}' == null || '${plan_.parentPlanId}' == undefined){
				$("#inheritParent").hide();
			}
		});
		$(document).ready(function (){
			$("#td2").hide();
			$("#td1").show();
			if($("#isModifyPage").val() == 'true'){
				$("#td1").hide();
				$("#td2").show();
			}
			
			$("#deliverablesInfoList").datagrid({
				onCheck:onCheck
			});
			begin();
			$('#addPlanCancelBtn').focus();
		});
		
		function analog(){
			alert(112);
			$('#depart_file_upload').fduploadify('upload', '*');
		}
		
		function onCheck(index,row){
			if(row.origin != undefined){
			$("#deliverablesInfoList").datagrid('uncheckRow',index);
			return false;
			}
		}
		
		var pdNext = 0;
		function begin() {
			$('#plan').panel({closed:false});
			$('#inputDiv').panel({closed:true});
			$('#document').panel({closed:true});
			$('#resourcet').panel({closed:true});
			var userList2 = eval($('#userList2').val());
		}
		
		function nextZero() {
			if($("#isModifyPage").val() == 'true'){
				var id = $('#id').val();
				var planStartTime = $('#planStartTime').datebox('getValue');
				var planEndTime = $('#planEndTime').datebox('getValue');
				$.ajax({
					url : 'resourceLinkInfoController.do?checkChildrenTime',
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
							if (checkPlanWithInput()) {
								planNameChange();
							}
						}else{
							top.tip(msg);
						}
					}
				});
			}else{
				if (checkPlanWithInput()) {
					planNameChange();
				}
			}
		}
		
		function nextOne() {
			$('#plan').panel({closed:true});
			$('#inputDiv').panel({closed:true});
			$('#document').panel({closed:false});
			$('#resourcet').panel({closed:true});
			initDocument();
			$('#addOutputCancelBtn').focus();
		}

		function nextTwo() {
			$('#plan').panel({closed:true});
			$('#inputDiv').panel({closed:true});
			$('#document').panel({closed:true});
			$('#resourcet').panel({closed:false});
			//editFormElementIndex = undefined;
			currentIndex = undefined;
			editIndex = undefined;
			initResource();
			$('#addResourceCancelBtn').focus();
		}
		
		function beforeOne() {
			pdNext = 1;
			$('#plan').panel({closed:false});
			$('#addPlanCancelBtn').focus();
			$('#inputDiv').panel({closed:true});
			$('#document').panel({closed:true});
			$('#resourcet').panel({closed:true});
		}

		function beforeTwo() {
			pdNext = 1;
			$('#plan').panel({closed:true});
			$('#inputDiv').panel({closed:false});
			$('#addInputCancelBtn').focus();
			$('#document').panel({closed:true});
			$('#resourcet').panel({closed:true});
		}

		function beforeThree() {
			$('#plan').panel({closed:true});
			$('#inputDiv').panel({closed:true});
			$('#document').panel({closed:false});
			$('#addOutputCancelBtn').focus();
			$('#resourcet').panel({closed:true});
		}

		function planNameChange(){			 
			var planName = $('#planName').val();
			if(planName == undefined){
				planName = $('#planName2').combobox('getText');
			}
			var preposeIds = $('#preposeIds').val();
			if($("#isModifyPage").val() != 'true'){
				if( planName != undefined && planName != null && planName != '' ){
					$.ajax({
						url : 'deliverablesInfoController.do?pdName&useObjectId='+ $('#useObjectId').val()+ '&pdNext='	+ pdNext + '&useObjectType=' + $('#useObjectType').val(),
						type : 'post',
						data : {
							planName : planName
						},
						cache : false,
						success : function(data) {
							var d = $.parseJSON(data);
							var msg = d.msg;
							var a = msg.indexOf("在项目名称库中");
							var b = msg.indexOf("没有勾选名称库");
							var c = msg.indexOf("可以修改");
							if(a == 0){
								$('#pdName').val(planName);
								$("#addInputsButton").show();
								$("#deleteInputsButton").show();
								$("#addDeliverableButton").show();
								$("#deleteDeliverableButton").show();
								$('#plan').panel({closed:true});
								/* if(preposeIds != null && preposeIds != '' && preposeIds != 'undefined'){
									$('#existencePrepose').show();
									$('#inexistencePrepose').hide();
								}
								else{
									$('#inexistencePrepose').show();
									$('#existencePrepose').hide();
								} */
								$('#inputDiv').panel({closed:false});
								$('#document').panel({closed:true});
								$('#resourcet').panel({closed:true});
							}else if(b == 0){
								$('#pdName').val("");
								$("#addInputsButton").show();
								$("#deleteInputsButton").show();
								$("#addDeliverableButton").show();
								$("#deleteDeliverableButton").show();
								$('#plan').panel({closed:true});
								/* if(preposeIds != null && preposeIds != '' && preposeIds != 'undefined'){
									$('#existencePrepose').show();
									$('#inexistencePrepose').hide();
								}
								else{
									$('#inexistencePrepose').show();
									$('#existencePrepose').hide();
								} */
								$('#inputDiv').panel({closed:false});
								$('#document').panel({closed:true});
								$('#resourcet').panel({closed:true});
							}else if(c == 0){
								$('#pdName').val(planName);
								$("#addInputsButton").show();
								$("#deleteInputsButton").show();
								$("#addDeliverableButton").show();
								$("#deleteDeliverableButton").show();
								$('#plan').panel({closed:true});
								/* if(preposeIds != null && preposeIds != '' && preposeIds != 'undefined'){
									$('#existencePrepose').show();
									$('#inexistencePrepose').hide();
								}
								else{
									$('#inexistencePrepose').show();
									$('#existencePrepose').hide();
								} */
								$('#inputDiv').panel({closed:false});
								$('#document').panel({closed:true});
								$('#resourcet').panel({closed:true});
							}else{
								$('#pdName').val("");
								$("#addInputsButton").attr("style","display:none;");
								$("#deleteInputsButton").attr("style","display:none;");
								$("#addDeliverableButton").attr("style","display:none;");
								$("#deleteDeliverableButton").attr("style","display:none;");
								var win = $.fn.lhgdialog("getSelectParentWin");
								win.tip('<spring:message code="com.glaway.ids.pm.project.plan.nameNotStandard"/>');
							}
							if(d.success){
								initInputs();
								$('#addInputCancelBtn').focus();
							}
						}
					});
				}
			}else{
				$('#pdName').val(planName);
				$("#addInputsButton").show();
				$("#deleteInputsButton").show();
				$("#addDeliverableButton").show();
				$("#deleteDeliverableButton").show();
				$('#plan').panel({closed:true});
				/* if(preposeIds != null && preposeIds != '' && preposeIds != 'undefined'){
					$('#existencePrepose').show();
					$('#inexistencePrepose').hide();
				}
				else{
					$('#inexistencePrepose').show();
					$('#existencePrepose').hide();
				} */
				$('#inputDiv').panel({closed:false});
				$('#document').panel({closed:true});
				$('#resourcet').panel({closed:true});
				initInputs();
				$('#addInputCancelBtn').focus();
			}
		}
		
		function viewResourceType(val, row, value) {
			var resoure = row.resourceInfo;
			if (resoure != undefined && resoure != null && resoure != '') {
				var parent = resoure.parent;
				if (parent != undefined && parent != null && parent != '') {
					return parent.name;
				}
			}
			return val;
		}

		function viewResourceName(val, row, value) {
			var resoure = row.resourceInfo;
			if (resoure != undefined && resoure != null && resoure != '') {
				return resoure.name;
			}
			return val;
		}

		// 维护资源
		function addResource() {
			gridname = 'resourceList';
			
			var rows22 = $('#resourceList').datagrid('getRows');
			var t = $('#resourceList');
			for (var i = 0; i < rows22.length; i++) {
				t.datagrid('endEdit', i);
			}
			currentIndex = undefined;
			editIndex = undefined;
			//editFormElementIndex = undefined;
			modifyResourceMass2();
			$('#resourceList').datagrid('unselectAll');
			
			var planStartTime = $('#planStartTime').datebox('getValue');
			var planEndTime = $('#planEndTime').datebox('getValue');
			
			var dialogUrl = 'resourceLinkInfoController.do?goAdd&useObjectId='+ $('#useObjectId').val()
			+ '&useObjectType='	+ $('#useObjectType').val()
			+ '&planStartTime='	+ planStartTime
			+ '&planEndTime='	+ planEndTime;
			createDialog('addResourceDialog',dialogUrl);
		}

		// 新增输入
		function addInputs() {
			var preposeIds = $('#preposeIds').val();
			if(preposeIds == '' || preposeIds == null || preposeIds == 'undefined' ){
				top.tip('<spring:message code="com.glaway.ids.pm.project.plan.inputs.addInputsCheckPrepose" />');
			}
			else{
				gridname = 'inputsList';
				var dialogUrl = 'inputsController.do?goAdd&projectId='+$("#projectId").val()+'useObjectId='
					+ $('#useObjectId').val()
					+ '&useObjectType='
					+ $('#useObjectType').val()
					+'&preposeIds='+preposeIds;
				createDialog('addInputsDialog',dialogUrl);
			}			
		}
		
		// 新增交付项
		function addDeliverable() {
			gridname = 'deliverablesInfoList';
			var dialogUrl = 'deliverablesInfoController.do?goAdd&useObjectId='
				+ $('#useObjectId').val()
				+ '&useObjectType='
				+ $('#useObjectType').val();
			createDialog('addDeliverableDialog',dialogUrl);
			
		}
	
		/**
		 * 选择前置计划页面
		 */
		function selectPreposePlan(hiddenId, textId) {
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

		/**
		 * 查看时的弹出窗口
		 */
		function createPopupwindow(title, url, width, height, hiddenId, textId) {
			width = width ? width : 800;
			height = height ? height : 750;
			if (width == "100%" || height == "100%") {
				width = document.body.offsetWidth;
				height = document.body.offsetHeight - 100;
			}
			createDialog('preposePlanDialog',url);
		}
		
		
		function preposePlanDialog(iframe){
	    	var preposeIds;
    		var preposePlans;
    		var preposeEndTime;
    		var selectedId = iframe.planPreposeList.getSelectedRowId();
    		if(selectedId != undefined && selectedId != null && selectedId != '' ){
    			var selectedIdArr = selectedId.split(",");
    			if(selectedIdArr.length > 0){
	  				for(var i=0;i<selectedIdArr.length;i++){
	  					var id = selectedIdArr[i];
	    				if(preposeIds != null && preposeIds != '' && preposeIds != undefined){
			    			preposeIds = preposeIds + ',' + id;
			    		}else{
			    			preposeIds = id;
			    		}
	    				var planName = iframe.planPreposeList.getRowAttribute(id,'displayName');
			    		if(preposePlans != null && preposePlans != '' && preposePlans != undefined){
			    			preposePlans = preposePlans + ',' + planName;
			    		}else{
			    			preposePlans = planName;
			    		}
			    		var planEndTime = iframe.planPreposeList.getRowAttribute(id,'planEndTime');
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
			var projectId = $('#projectId').val();
			if (preposeIds != null && preposeIds != ''
					&& preposeIds != undefined) {
				$('#preposeIds').val(preposeIds);
			} else {
				$('#preposeIds').val('');
			}
			if (preposePlans != null && preposePlans != ''
				&& preposePlans != undefined) {
			$('#preposePlans').textbox("setValue",preposePlans);
		} else {
			$('#preposePlans').textbox("setValue","");
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

		function viewStartEndTime(val, row, value) {
			var start = row.startTime;
			var end = row.endTime;
			if((start != undefined && start !=null && start != '') 
				&& (end != undefined && end !=null && end != '')){
				return dateFmtYYYYMMDD(start) + "~" + dateFmtYYYYMMDD(end);
			}
			return "";
		}
		
		// 时间格式化
		function dateFormatter(val,row,value){
			return dateFmtYYYYMMDD(val);
		}
		
		// 使用百分比
		function viewUseRate2(val, row, value){
			return progressRateGreen2(val);
		}
		
		function progressRateGreen2(val){
			if(val == undefined || val == null || val == ''){
				val = 0;
			}
			return val + '%' ;
		}
		
		// 删除交付项
		function deleteSelections(gridname, url) {
		    var rows = $("#"+gridname).datagrid('getSelections');
		    for(var i =0;i<rows.length;i++){
		    	if(rows[i].origin != ''&&rows[i].origin !=null&&rows[i].origin !=undefined){
		    		top.tip('<spring:message code="com.glaway.ids.pm.project.plan.deliveryDelLimit" arguments="' + rows[i].name + '"/>');
		    		return false;
		    	}
		    }
		    
		    var ids = [];
		    if (rows.length > 0) {
		    	top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmDel"/>', 
		    		function(r) {
		    		if (r) {
						for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].id);
						}
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
								if(gridname == 'inputsList'){
									initInputs();
									$('#addInputCancelBtn').focus();
								}
								else{
									initDocument();
									$('#addOutputCancelBtn').focus();
								}
							}
						});
		    		}
		    		});
			} else {
				top.tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
			}
		}
		
		
		// 删除资源
		function deleteSelections2(gridname, url) {
		    var rows = $("#"+gridname).datagrid('getSelections');
		    
			var rows22 = $('#resourceList').datagrid('getRows');
			var t = $('#resourceList');
			for (var i = 0; i < rows22.length; i++) {
				t.datagrid('endEdit', i);
			}
			currentIndex = undefined;
			editIndex = undefined;
			//editFormElementIndex = undefined;
			modifyResourceMass2();
		    var ids = [];
		    if (rows.length > 0) {
		    	top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmDel"/>', 
		    		function(r) {
		    		if (r) {
						for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].id);
						}
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
								initResource();
								$('#addResourceCancelBtn').focus();
							}
						});
		    		}
		    		});
			} else {
				top.tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
			}
		}
		
		var editIndex = undefined;
		var currentIndex = undefined;
		function endFormElementEditing(){
			/* if (editFormElementIndex == undefined){return true}
			if ($('#resourceList').datagrid('validateRow', editFormElementIndex)){
				$('#resourceList').datagrid('endEdit', editFormElementIndex);
				editFormElementIndex = undefined;
				return true;
			} else {
				return false;
			} */
			
			if (editIndex == undefined){
				return true
				}
			if ($('#resourceList').datagrid('validateRow', editIndex)){
				$('#resourceList').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}

		
		function onClickPlanRow(index) {
			/* if (editFormElementIndex != index){
				 if (endFormElementEditing()){
					$('#resourceList').datagrid('selectRow', index).datagrid('beginEdit', index);
					editFormElementIndex = index;
				} else {
					$('#resourceList').datagrid('selectRow', editFormElementIndex);
				currentIndex = index;
				}
			} */
 			$('#resourceList').datagrid('endEdit', currentIndex);
			currentIndex = undefined;
			editIndex = undefined;
		}
		
		function onDoubleClickPlanRow(index) {
			if (currentIndex != undefined){
				onClickPlanRow();
			}
			currentIndex = index;
			if (editIndex != index) {
				if (endFormElementEditing()) {
					$('#resourceList').datagrid('selectRow', index).datagrid('beginEdit', index);
					editIndex = index;
				} else {
					$('#resourceList').datagrid('selectRow', editIndex);
				}
			}
		}
		
		
		
		function addplanAndResource2(){
			 
			var rows = $('#resourceList').datagrid('getRows');
			var planStartTime = $('#planStartTime').datebox('getValue');
			var planEndTime = $('#planEndTime').datebox('getValue');
			var t = $('#resourceList');
			for (var i = 0; i < rows.length; i++) {
				t.datagrid('endEdit', i);
			}
			/* editFormElementIndex = undefined; */
			currentIndex = undefined;
			editIndex = undefined;
			
			for (var i = 0; i < rows.length; i++) {
				var resourceName = rows[i].resourceName;
				if(rows[i].startTime == ""){
					top.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyStartParam" arguments="' + resourceName + '"/>');
					return false;
				}
				
				if(rows[i].endTime == ""){
					top.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyEndParam" arguments="' + resourceName + '"/>');
					return false;
				}
				
				if(rows[i].endTime < rows[i].startTime){
					top.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.endNoEarlierThanStartParam" arguments="' + resourceName + '"/>');
					return false;
				}
				
				if((rows[i].startTime < planStartTime) || (rows[i].startTime > planEndTime)){
					top.tip(resourceName+"的开始时间必须收敛于计划时间 :"+planStartTime+'~'+planEndTime);
					return false;
				}

				if((rows[i].endTime < planStartTime) || (rows[i].endTime > planEndTime)){
					//resourceName+"的结束时间必须收敛于计划时间 :"+planStartTime+'~'+planEndTime
					top.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.endInPlanTimeParam" arguments="' + resourceName + ',' +planStartTime+'~'+planEndTime+ '"/>');
					return false;
				}
				
				
				if(rows[i].useRate == ''){
					top.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyPercentParam" arguments="' + resourceName + '"/>');
					return false;
				}
				
				if(rows[i].useRate > 1000){
					top.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.percentUpperLimitParam" arguments="' + resourceName + '"/>');
					return false;
				} 
				
				if(rows[i].useRate < 0){
					top.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.percentLowerLimitParam" arguments="' + resourceName + '"/>');
					return false;
				} 
			}
			modifyResourceMass();
			
		}
		
		
		//计划名称链接事件
		function viewDocument(val, row, value) {
			if(row.origin != undefined){
				return '<a style="color:gray">' + row.name + '</a>';
			}else{
				return '<a style="color:black">' + row.name + '</a>';
			}
			
		}
		
		
		function modifyResourceMass(){
			var rows = $('#resourceList').datagrid('getRows');
			var ids = [];
			var useRates = [];
			var startTimes = [];
			var endTimes = [];
				
			if(rows.length > 0){
				for(var i = 0; i < rows.length; i++){
		    		ids.push(rows[i].id);
		    		useRates.push(rows[i].useRate);
		    		startTimes.push(rows[i].startTime);
		    		endTimes.push(rows[i].endTime);
				}
				
				$.ajax({
					url : 'resourceLinkInfoController.do?modifyResourceMass',
					type : 'post',
					data : {
						ids : ids.join(','),
						useRates : useRates.join(','),
						startTimes : startTimes.join(','),
						endTimes : endTimes.join(','),
					},
					cache : false,
					success : function(data) {
						addPlan();
					}
				}); 
			}else{
				addPlan();
			}
		}
		
		function modifyResourceMass2(){
			var rows = $('#resourceList').datagrid('getRows');
			var ids = [];
			var useRates = [];
			var startTimes = [];
			var endTimes = [];
				
			if(rows.length > 0){
				for(var i = 0; i < rows.length; i++){
		    		ids.push(rows[i].id);
		    		useRates.push(rows[i].useRate);
		    		startTimes.push(rows[i].startTime);
		    		endTimes.push(rows[i].endTime);
				}
				
				$.ajax({
					url : 'resourceLinkInfoController.do?modifyResourceMass',
					type : 'post',
					data : {
						ids : ids.join(','),
						useRates : useRates.join(','),
						startTimes : startTimes.join(','),
						endTimes : endTimes.join(','),
					},
					cache : false,
					success : function(data) {
					}
				}); 
			}else{
			}
		}
		
		
		// 资源名称链接事件
		function resourceNameLink(val, row, value) {
			return '<a href="#" onclick="viewResourceCharts(\'' + row.id + '\')" style="color:blue">' + val + '</a>';
		}

		// 资源名称链接事件
		function viewResourceCharts(id){
		   	var rows = $("#resourceList").datagrid('getRows');
		    var index = $("#resourceList").datagrid('getRowIndex', id);
		 	var row = rows[index];
		 	
		 	var planStartTime=$('#planStartTime').datebox('getValue');
		 	var planEndTime=$('#planEndTime').datebox('getValue');
			
			if(row.useRate != null && row.useRate != '' && row.startTime != null && row.startTime != '' && row.endTime != null && row.endTime != ''){
 				createDialog('resourceDialog',"resourceLinkInfoController.do?goToToBeUsedRateReport&resourceId="
						+ row.resourceInfo.id + "&resourceLinkId=" + row.id + "&resourceUseRate=" + row.useRate
						+ "&startTime=" + row.startTime + "&endTime=" + row.endTime + "&useObjectId=${plan_.id}"); 
			}
			else{
				top.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyUseInfo"/>');
			}
		}
		
		function addSetTimeOut(){
			window.setTimeout("addPlanAndResource()", 500);//等待0.5秒，让分类树结构加载出来
		}
		
		function addPlanAndResource(){
			debugger;
			if(checkPlanWithInput()){
				var planName = $('#planName').val();
				var taskNameId='';
				if(planName == undefined){
					planName = $('#planName2').combobox('getText');
					taskNameId=$('#planName2').combobox('getValue');
				}
				
				var id = $('#id').val();
				var planNumber = $('#planNumber').val();
				var projectId = $('#projectId').val();
				var parentPlanId = $('#parentPlanId').val();
				var beforePlanId = $('#beforePlanId').val();
				var bizCurrent = $('#bizCurrent').val();
				
				var isStandard = $('#isStandard').val();
				var flowStatus = $('#flowStatus').val();
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
				
				 
				if($("#isModifyPage").val() != 'true'){
				if( planName != undefined && planName != null && planName != '' ){
					$.ajax({
						url : 'deliverablesInfoController.do?pdName&useObjectId='+ $('#useObjectId').val()+ '&pdNext='	+ pdNext,
						type : 'post',
						data : {
							planName : planName
						},
						cache : false,
						success : function(data) {
							var d = $.parseJSON(data);
							var msg = d.msg;
							var a = msg.indexOf("在项目名称库中");
							var b = msg.indexOf("没有勾选名称库");
							var c = msg.indexOf("可以修改");
							
							if(a == 0){
								$.post('planController.do?doSave', {
									'id' : id,
									'planNumber' : planNumber,
									'projectId' : projectId,
									'parentPlanId' : parentPlanId,
									'beforePlanId' : beforePlanId,
									'planStartTime' : planStartTime,
									'planEndTime' : planEndTime,
									'flowStatus' : flowStatus, 
									'bizCurrent' : bizCurrent,
									'remark' : remark,
									'planName' : planName,
									'owner' : owner, 
									'planLevel' : planLevel,
									'workTime' : workTime,
									'milestone'	: milestoneRm,
									'preposeIds' : preposeIds,
									'taskNameId' : taskNameId,
									'taskNameType' : taskNameType,
									'taskType' : taskType
								}, function(data) {
									var win = $.fn.lhgdialog("getSelectParentWin");
									var planListSearch = win.planListSearch();
									$.fn.lhgdialog("closeSelect");
								});
							}else if(b == 0){
								$.post('planController.do?doSave', {
									'id' : id,
									'planNumber' : planNumber,
									'projectId' : projectId,
									'parentPlanId' : parentPlanId,
									'beforePlanId' : beforePlanId,
									'planStartTime' : planStartTime,
									'planEndTime' : planEndTime,
									'bizCurrent' : bizCurrent,
									'flowStatus' : flowStatus, 
									'remark' : remark,
									'planName' : planName,
									'owner' : owner, 
									'planLevel' : planLevel,
									'workTime' : workTime,
									'milestone'	: milestoneRm,
									'preposeIds' : preposeIds,
									'taskNameType' : taskNameType,
									'taskType' : taskType
								}, function(data) {
									var win = $.fn.lhgdialog("getSelectParentWin");
									var planListSearch = win.planListSearch();
									$.fn.lhgdialog("closeSelect");
								});
							}else if(c == 0){
								$.post('planController.do?doSave', {
									'id' : id,
									'planNumber' : planNumber,
									'projectId' : projectId,
									'parentPlanId' : parentPlanId,
									'beforePlanId' : beforePlanId,
									'planStartTime' : planStartTime,
									'planEndTime' : planEndTime,
									'bizCurrent' : bizCurrent,
									'flowStatus' : flowStatus, 
									'remark' : remark,
									'planName' : planName,
									'owner' : owner, 
									'planLevel' : planLevel,
									'workTime' : workTime,
									'milestone'	: milestoneRm,
									'preposeIds' : preposeIds,
									'taskNameType' : taskNameType,
									'taskType' : taskType
								}, function(data) {
									var win = $.fn.lhgdialog("getSelectParentWin");
									var planListSearch = win.planListSearch();
									$.fn.lhgdialog("closeSelect");
								});
							}else{
								var win = $.fn.lhgdialog("getSelectParentWin");
								//win.tip("计划名称不在名称库中");
								top.tip('<spring:message code="com.glaway.ids.pm.project.plan.nameNotStandard"/>');
							}
							if(d.success){
								initDocument();
								$('#addOutputCancelBtn').focus();
							}
						}
					});
				}
			}else{
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
								'flowStatus' : flowStatus, 
								'remark' : remark,
								'planName' : planName,
								'owner' : owner, 
								'planLevel' : planLevel,
								'workTime' : workTime,
								'milestone'	: milestoneRm,
								'preposeIds' : preposeIds,
								'taskNameType' : taskNameType,
								'taskType' : taskType
							}, function(data) {
								var win = $.fn.lhgdialog("getSelectParentWin");
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

		}
		
		
		function addInputsDialog(iframe){
	    	 var flg=iframe.getLoadData();
	    	 if(flg && initInputs(flg)){
	    		 return true;
	    	 }
	    	 return false;
		} 
		
		function addDeliverableDialog(iframe){
	    	 var flg=iframe.getLoadData();
	    	 if(flg && initDocument(flg)){
	    		 return true;
	    	 }
	    	 return false;
		}
		
		function addResourceDialog(iframe){
	    	 var flg=iframe.getLoadData();
	    	 if(flg && initResource(flg)){
	    		 //editFormElementIndex = undefined;
	    		 currentIndex = undefined;
				 editIndex = undefined;
	    		 return true;
	    	 }
			return false;
		}
		
		function addInheritDialog(iframe){
	    	 var flg=iframe.add();
	    	 if(flg && initDocument(flg)){
	    		 return true;
	    	 }
			return false;
		}
		
	/**国际化js转移过来的方法**/
	var inputTypeTotal = "1";
	function workTimeLinkage(inputType) {
	debugger;
	if (inputType == 'planStartTime') {
		inputTypeTotal = inputTypeTotal + "1";
	}
	if (inputType == 'planEndTime') {
		inputTypeTotal = inputTypeTotal + "2";
	}
	if (inputType == 'workTime') {
		inputTypeTotal = inputTypeTotal + "3";
	}
		var win = $.fn.lhgdialog("getSelectParentWin");
		var planStartTime = $('#planStartTime').datebox('getValue');
		var workTime = $('#workTime').val();
		var planEndTime = $('#planEndTime').datebox('getValue');
		var projectId = $('#projectId').val();
		var milestone = $('#milestone').combobox('getValue');
		if(milestone == "否"){
			milestone = "false";
		}else if(milestone == "是"){
			milestone = "true";
		}
		if (inputType == 'planEndTime') {
			inputType1 = inputType;
		}
		if (inputType == 'workTime') {
			inputType2 = inputType;
		}
		var inputTypeTotalIndex = inputTypeTotal.indexOf('123');
		if (inputType1 == 'planEndTime' && inputType2 == 'workTime' && inputTypeTotalIndex < 0) {
			inputType1 = undefined;
			inputType2 = undefined
			return false;
		}
		if (inputType == 'planStartTime') {
			if (planStartTime != null && planStartTime != ''
					&& planStartTime != undefined) {
				if (workTime != null && workTime != '' && workTime != undefined) {
					if (workTime == 0 && milestone != "true") {
						$('#workTime').textbox("setValue", "1");
						win.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
					} else {
						$.ajax({
							url : 'planController.do?getEndTimeByStartTimeAndWorkTime',
							type : 'post',
							data : {
								projectId : projectId,
								planStartTime : planStartTime,
								planEndTime : planEndTime,
								workTime : workTime,
								milestone : milestone
							},
							cache : false,
							success : function(data) {
								if (data != null) {
									var d = $.parseJSON(data);
									if (d.success == true) {
										planEndTime = d.obj;
										$('#planEndTime')
												.datebox("setValue",
														planEndTime);
									} else {
										$('#planStartTime')
												.datebox("setValue",
														planEndTime);
										win.tip(d.msg);
									}
								} else {
									win.tip('<spring:message code="com.glaway.ids.pm.project.plan.endTimeError"/>');
								}
							}
						});
					}
				} else if (planEndTime != null && planEndTime != ''
						&& planEndTime != undefined) {
					if (planEndTime < planStartTime) {
						$('#planEndTime').datebox("setValue", "");
					} else {
						$.ajax({
							url : 'planController.do?getWorkTimeByStartTimeAndEndTime',
							type : 'post',
							data : {
								projectId : projectId,
								planStartTime : planStartTime,
								planEndTime : planEndTime,
								workTime : workTime,
								milestone : milestone
							},
							cache : false,
							success : function(data) {
								if (data != null) {
									var d = $.parseJSON(data);
									if (d.success == true) {
										workTime = d.obj;
										$('#workTime').textbox(
												"setValue", workTime);
									} else {
										win.tip(d.msg);
									}
								} else {
									win.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
								}
							}
						});
					}
				}
			} else {
				win.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
			}
		} else if (inputType == 'workTime') {
			if (workTime != null && workTime != '' && workTime != undefined) {
				if (planStartTime != null && planStartTime != ''
						&& planStartTime != undefined) {
					if (workTime == 0 && milestone != "true") {
						$('#workTime').textbox("setValue", "1");
						win.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
					} else {
						$.ajax({
							url : 'planController.do?getEndTimeByStartTimeAndWorkTime',
							type : 'post',
							data : {
								projectId : projectId,
								planStartTime : planStartTime,
								planEndTime : planEndTime,
								workTime : workTime,
								milestone : milestone
							},
							cache : false,
							success : function(data) {
								if (data != null) {
									var d = $.parseJSON(data);
									if (d.success == true) {
										planEndTime = d.obj;
										$('#planEndTime')
												.datebox("setValue",
														planEndTime);
									} else {
										win.tip(d.msg);
									}
								} else {
									win.tip('<spring:message code="com.glaway.ids.pm.project.plan.endTimeError"/>');
								}
							}
						});
					}
				} else {
					win.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
				}
			}
		} else if (inputType == 'planEndTime') {
			if (planEndTime != null && planEndTime != ''
					&& planEndTime != undefined) {
				if (planStartTime != null && planStartTime != ''
						&& planStartTime != undefined) {
					if (planEndTime < planStartTime) {
						win.tip('<spring:message code="com.glaway.ids.pm.project.plan.startNoLaterThanEnd"/>');
					} else {
						$.ajax({
							url : 'planController.do?getWorkTimeByStartTimeAndEndTime',
							type : 'post',
							data : {
								projectId : projectId,
								planStartTime : planStartTime,
								planEndTime : planEndTime,
								workTime : workTime,
								milestone : milestone
							},
							cache : false,
							success : function(data) {
								if (data != null) {
									var d = $.parseJSON(data);
									if (d.success == true) {
										workTime = d.obj;
										if (workTime == 0 && milestone != "true") {
											$('#workTime').textbox(
													"setValue", "1");
											win.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
										} else {
											$('#workTime').textbox(
													"setValue",
													workTime);
										}
									} else {
										win.tip(d.msg);
									}
								} else {
									win.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
								}
							}
						});
					}
				} else {
					win.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
				}
			}
		}
	}
	
	function checkPlanWithInput() {
		var parentStartTime = $('#parentStartTime').val();
		var parentEndTime = $('#parentEndTime').val();
		var preposeEndTime = $('#preposeEndTime').val();
		var win = $.fn.lhgdialog("getSelectParentWin") ;
	
		var planName = $('#planName').val();
		if(planName == undefined){
			var planName = $('#planName2').combobox('getText');
		}
		if (planName == "") {
			top.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyName"/>');
			return false;
		}
		if(planName.length>30){
			top.tip('<spring:message code="com.glaway.ids.pm.project.plan.basicLine.nameLength"/>')
			return false;
		}
		
		
		var userList = eval($('#userList2').val());
		var owner = $('#owner').combobox('getValue');
		var ownerText = $('#owner').combobox('getText');
		var pdOwner = 0;
		for (var i = 0; i < userList.length; i++) {
			var a = ownerText.indexOf(userList[i].realName);
			if (a == 0) {
				pdOwner = 1;
				break;
			}
		}
		if(pdOwner == 0){
			top.tip('负责人不存在,请重新输入');
			return false;
		}
		
		if (owner == "") {
			top.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyManager"/>');
			return false;
		}
		
		var planStartTime = $('#planStartTime').datebox('getValue');
		if (planStartTime == "") {
			top.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
			return false;
		}
		if((planStartTime < parentStartTime) || (planStartTime > parentEndTime)){
			top.tip('<spring:message code="com.glaway.ids.pm.project.plan.startInParentTime" arguments="' +parentStartTime+'~'+parentEndTime+ '"/>');
			return false;
		}
		
		var planEndTime = $('#planEndTime').datebox('getValue');
		if (planEndTime == "") {
			top.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyEnd"/>');
			return false;
		}

		if(planEndTime < planStartTime){
			top.tip('<spring:message code="com.glaway.ids.pm.project.plan.endNoEarlierThanStart"/>');
			return false;
		}

		if((planEndTime < parentStartTime) || (planEndTime > parentEndTime)){
			//"计划开始时间必须收敛于父级的计划时间或项目时间:"+parentStartTime+'~'+parentEndTime
			top.tip('<spring:message code="com.glaway.ids.pm.project.plan.startInParentTime" arguments="' +parentStartTime+'~'+parentEndTime+ '"/>');
			return false;
		}
		
		if(preposeEndTime != null && preposeEndTime != '' && preposeEndTime != undefined){
			if(preposeEndTime > planStartTime){
				top.tip('<spring:message code="com.glaway.ids.pm.project.plan.startNoLaterThanPrepose" arguments="' +preposeEndTime+ '"/>');
				return false;
			}
		}
		
		var workTime = $('#workTime').val();
		var reg = new RegExp("^[1-9][0-9]{0,3}$");
		if (workTime == "") {
			top.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyPeriod"/>');
			return false;
		}
		
		var milestone = $('#milestone').combobox('getValue');
		if(milestone == 'false') {
			if(!reg.test(workTime)){
	            top.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodLowerToUpper"/>');
	            return false;
	        }
		} else {
			if(workTime != '0') {
				if(!reg.test(workTime)){
	                top.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodLowerToUpper"/>');
	                return false;
	            }
			}
		}
		
		var remark = $('#remark').val();
		if(remark.length>200){
			top.tip('<spring:message code="com.glaway.ids.common.remarkLength"/>')
			return false;
		}
		return true;
	}
	
	function nameTaskTypeChange(isStandard){
		if('true' == isStandard){
			var planName = $('#planName2').combobox('getText');
			$.ajax({
				url : 'planController.do?getTaskNameType',
				type : 'post',
				data : {
					planName : planName
				},
				cache : false,
				success : function(data) {
					if (data != null) {
						var d = $.parseJSON(data);
						if (d.success == true) {
							$('#taskNameType')
									.combobox("setValue",
											d.obj);
						} else {
							$('#taskNameType')
							.combobox("setValue",
									d.obj);
							win.tip(d.msg);
						}
					} else {
						win.tip(d.msg);
					}
				}
			});
		}
	}
	
	function milestoneChange(){
		var workTime = $('#workTime').val();
		var milestone = $('#milestone').combobox('getValue');
		if(milestone == "否"){
			milestone = "false";
		}else if(milestone == "是"){
			milestone = "true";
		}
		if (workTime == 0 && milestone != "true") {
			$('#workTime').textbox(
					"setValue", "1");
		}
	}
	
	function addLink(val, row, value){
		debugger;
		if(val!=null&&val!=''&&row.originType == 'LOCAL'){
			return '<a  href="#" onclick="importDoc(\'' + row.docId + '\',\'' + row.docNameShow + '\')" id="myDoc"  style="color:blue">'+val+'</a>';
		}else if(row.originType == 'PROJECTLIBDOC'){
			var path = "<a  href='#' title='查看' style='color:blue' onclick='openProjectDoc1(\""+ row.docIdShow+ "\",\""+ row.docNameShow+ "\",\""+ row.ext1+"\",\""+ row.ext2+"\")'>"
				+ row.docNameShow
				+ "</a>"
			if (row.ext3 == false || row.ext3 == 'false') {
				path = row.docNameShow;
			}
			return path; 
		}else if(row.originType == 'PLAN'){
			var path = "<a  href='#' title='查看' style='color:blue' onclick='openProjectDoc1(\""+ row.docId+ "\",\""+ row.docNameShow+ "\",\""+ row.ext1+"\",\""+ row.ext2+"\")'>"
			+ row.docNameShow
			+ "</a>"
		if (row.ext3 == false || row.ext3 == 'false') {
			path = row.docNameShow;
		}
		return path; 
		}
		else return ;
		
	}
	

	function openProjectDoc1(id, name,download,history) {
		if (download == false || download == 'false') {
			download = "false";
		}
		if (history == false || history == 'false') {
			history = "false";
		}
		var url = "projLibController.do?viewProjectDocDetail&id=" + id
				+ "&download=" + download + "&history=" + history;
		createdetailwindow("文档详情", url, "870", "580")
	}
	
	function importDoc(filePath,fileName){
		debugger;
		window.location.href = encodeURI('jackrabbitFileController.do?fileDown&fileName='+fileName+'&filePath=' + filePath);
	}
	
	function showDocDetail(id){
		var url="projLibController.do?viewProjectDocDetail&opFlag=1&id="+id;
		createdetailwindow2("文档详情", url, "1000", "550");
	}
	
	
	function showOrigin(val, row, value){
		var curValue= val;
		if(curValue!=null&&curValue!='' && curValue.length>5){
			curValue = "<span title='"+val+"'>"+curValue+"</span>";
		}
		
		if(row.originType == "LOCAL"){
			return "本地文档";
		}else{
			return curValue;
		}
	}
	
	function showAllName(val, row, value){
		var curValue= val;
		if(curValue!=null&&curValue!='' && curValue.length>5){
			curValue = "<span title='"+val+"'>"+curValue+"</span>";
		}
		return curValue;
	}
	
	// 继承父项
	function inheritParent() {
		var dialogUrl = 'deliverablesInfoController.do?goAddInherit&planId=${plan_.id}&parentPlanId=${plan_.parentPlanId}'+'&useObjectId='
			+ $('#useObjectId').val()
			+ '&useObjectType='
			+ $('#useObjectType').val();
		createDialog('inheritDialog', dialogUrl);
	}
	
	function createdetailwindow2(title, url, width, height) {
		width = width ? width : 700;
		height = height ? height : 400;
		if (width == "100%" || height == "100%") {
			width = document.body.offsetWidth;
			height = document.body.offsetHeight - 100;
		}
		
		if (typeof (windowapi) == 'undefined') {
			createDialog('showDocDetailChangeFlowDialog',url);
			
		} else {
			createDialog('showDocDetailChangeFlowDialog',url);
		} 
	}
	
	
	function addInputsNew(){
		var dialogUrl = 'inputsController.do?goAddInputs&projectId='+$("#projectId").val()+'&useObjectId='
			+ $('#useObjectId').val()
			+ '&useObjectType='
			+ $('#useObjectType').val()
			+'&hideMoreShow=false';
		createDialog('openSelectInputsDialog', dialogUrl);
	}
	
	function openSelectConfigOkFunction(iframe){
   	 var flg=iframe.getLoadData();
   	 if(flg && initInputs(flg)){
   		 return true;
   	 }
   	 return false;
	} 
	
	function addLocalDoc(){
		var dialogUrl = 'inputsController.do?goAddLocalDoc&projectId='+$("#projectId").val()+'&useObjectId='
			+ $('#useObjectId').val()
			+ '&useObjectType='
			+ $('#useObjectType').val();
		createDialog('openSelectLocalDocDialog', dialogUrl);
	}
	
	function projLibSubimt(iframe) {
		debugger;
		var result = false;
		var result = iframe.saveFileBasicAndUpload();
		debugger;
		if(result && initInputs('')){
			return true;
		} else {
			return false;
		}
	}
	
	var idRow = '';
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
				//如果上传成功，不需要提示
				//var rst = ajaxRstAct(d);
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
	
	function goProjLibLink(id,index) {
		debugger;
		
		var row;
		$('#inputsList').datagrid('selectRow',index);
		var all = $('#inputsList').datagrid('getRows');
		for(var i=0;i<all.length;i++){
			var inx = $("#inputsList").datagrid('getRowIndex', all[i]);
			if(inx == index){
				row = all[i];
			}
		}
		
		idRow=row.id;
		var dialogUrl = 'projLibController.do?goProjLibLayout0&id='+$("#projectId").val()+'&rowId='+idRow;
		$("#"+'taskDocCheckLineDialog').lhgdialog("open", "url:"+dialogUrl);
	}
	
	function taskDocCheckLineDialog(iframe) {
		debugger;
		//iframe = this.iframe.contentWindow;
		if (iframe.validateSelectedNum()) {
			var docId = iframe.getSelectionsId();
			var folderId = iframe.$("#folderId").val();
			var rowId = iframe.$("#rowId").val();
			var rows=$('#deliverablesInfoList').datagrid('getSelections');
			var row=rows[0];
			$.ajax({
				url : 'deliverablesInfoController.do?updateInputs',
				type : 'post',
				data : {
					fileId : docId,
					folderId : folderId,
					rowId : rowId,
					projectId : $('#projectId').val(),
					useObjectId : $('#useObjectId').val(),
					useObjectType : $('#useObjectType').val()
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						initInputs('');
					}
				}
			});
			return true;
		} else {
			return false;
		}
	}
	
	
	
	function afterSuccessCall(file, data, response){
		debugger;
		top.jeasyui.util.tagMask('close');
		var jsonObj = $.parseJSON(data);
		var fileStr = jsonObj.obj;
		var size = (file.size)/(1024*1024);
		if(size > 50 ){
			top.tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.doc.sizeLimit"/>');
			return false;
		}
		var attachmentShowName = fileStr.split(",")[3];
        var uuid = fileStr.split(",")[2];
		var dowmLoadUrl = fileStr.split(",")[1];
		var attachmentName = fileStr.split(",")[0];
		//隐藏字段，处理冗余数据
		var invalidIds = $('#invalidIds').val();
		if(invalidIds != null && invalidIds != '' && invalidIds != undefined){
			invalidIds = invalidIds +","+ dowmLoadUrl;
			 $('#invalidIds').val(invalidIds);
		}else{
			invalidIds = dowmLoadUrl;
			$('#invalidIds').val(invalidIds);
		}
		
		/* $('#projLibDocList').datagrid('appendRow',{
			dowmLoadUrl: dowmLoadUrl,
			attachmentName: attachmentName,
			attachmentShowName: attachmentShowName,
			uuid : uuid
		}); */
		$('#docattachmentName').val(attachmentName);
		$('#docattachmentURL').val(dowmLoadUrl);
		$('#docAttachmentShowName').val(attachmentShowName);
		
		$.ajax({
			type : "POST", 
			url : "projLibController.do?doAddForLocalDoc&attachmentNames="+ attachmentName+"&dowmLoadUrls="+ attachmentName,
			async : false,
			data :   $('#projLibDocAddForm').serialize(),
			success : function(data) {
				debugger;
				var d = $.parseJSON(data);				
				if (d.success) {
					/* docId = d.obj;
					retOid=d.obj;
					result=true;
					var invalidIds = $('#invalidIds').val();
					var delUrl='projLibController.do?deleteProjLibJackrabbitFile&type='+'add';
					$.ajax({
						type : 'POST',
						url : delUrl,
						async : false,
						data : {
							invalidIds : invalidIds,
							docattachmentURL : dowmLoadUrl
						},
					success : function(data) {
					}
				}); */
					initInputs('');
					return true;		
				}else{
					return false;
				}
				
					 
			}
		});	
		return true;


	}
	
	function saveFileBasicAndUpload()
	{   
		debugger;
		delectFlog = false;
		
		 var retOid;
	     var result=true;
	   
	      
		  var allRows=$('#projLibDocList').datagrid('getRows');
		  var dowmLoadUrls = [];
		  var attachmentNames = [];
		  var docattachmentName = "";
		  var docattachmentURL = "";
		  var docAttachmentShowName = "";
		  if(allRows.length>0){
			  endEditing();
		  for (var i = 0; i < allRows.length; i++) {
			  if(docattachmentName == '' || docattachmentName == null){
				  docattachmentName = allRows[i].attachmentName;
			  }else{
				  docattachmentName =docattachmentName +","+allRows[i].attachmentName;
			  }
			  if(docattachmentURL == '' || docattachmentURL == null){
				  docattachmentURL = allRows[i].dowmLoadUrl;
			  }else{
				  docattachmentURL = docattachmentURL+","+allRows[i].dowmLoadUrl;
			  }
			  if(docAttachmentShowName == '' || docAttachmentShowName == null){
				  docAttachmentShowName = allRows[i].attachmentShowName;
			  }else{
				  docAttachmentShowName = docAttachmentShowName+","+allRows[i].attachmentShowName;
			  }
			  
//			  if(allRows[i].docSecurityLevel != null && allRows[i].docSecurityLevel != '' && allRows[i].docSecurityLevel != undefined){
//			  }else{
//				  var p = parseInt(i)+1;
//				  tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.docSecurityLevelNotEmpty" arguments="' + p + '"/>'); 
//				return false;  
//			  }
			}
//		  workTimeOnChange();
		  
		  $('#docattachmentName').val(docattachmentName);
		  $('#docattachmentURL').val(docattachmentURL);
		  $('#docAttachmentShowName').val(docAttachmentShowName);
		  }
		 
		$.ajax({
			type : "POST", 
			url : "projLibController.do?doAddForLocalDoc&attachmentNames="+ attachmentNames+"&dowmLoadUrls="+ dowmLoadUrls,
			async : false,
			data :  $('#projLibDocAddForm').serialize(),
			success : function(data) {
				debugger;
				var d = $.parseJSON(data);				
				if (d.success) {
					docId = d.obj;
					retOid=d.obj;
					result=true;
					var invalidIds = $('#invalidIds').val();
					var delUrl='projLibController.do?deleteProjLibJackrabbitFile&type='+'add';
					$.ajax({
						type : 'POST',
						url : delUrl,
						async : false,
						data : {
							invalidIds : invalidIds,
							docattachmentURL : docattachmentURL
						},
					success : function(data) {
					}
				});
					if('${yanfa}' == 'yanfa'){
						if(result){
								var win = $.fn.lhgdialog("getSelectParentWin");
								win.uploadFile(retOid);
								return true;
							
						} 
						}
					
					return false;		
				}else{
					result= false;
				}
				
				if('${yanfa}' == 'yanfa'){}else{
				var win = $.fn.lhgdialog("getSelectParentWin");
				$.fn.lhgdialog("closeSelect");
				win.tip(d.msg);
				}
					 
			}
		});	
		return true;
	}
	
	
	
	function deleteSelectionsInputs(griname,url){
		debugger;
		var rows = $('#'+griname).datagrid('getChecked');
		
		var ids = [];
		if(rows.length > 0){
			top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmDel"/>', 
		    		function(r) {
		    		if (r) {
						for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].id);
						}
						$.ajax({
							url : url,
							type : 'post',
							data : {
								ids : ids.join(','),
								useObjectId : $("#useObjectId").val()
							},
							cache : false,
							success : function(data) {
								debugger;
								var d = $.parseJSON(data);
								if (d.success) {
									var msg = d.msg;
									tip(msg);
								}
								if(gridname == 'inputsList'){
									initInputs('');
// 									 $('#'+griname).datagrid('clearSelections');
// 									 $('#'+griname).datagrid('clearChecked');
								}
							
							}
						});
		    		}
		    	});
			
		}else{
			top.tip("请选择需要删除的记录");
		}
	}
	
	
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
					var newData = null;
					if(data!=null && data['rows']!=null && data['rows'].length>0){
						for(var i=0; i<data['rows'].length; i++) {
							newData = data['rows'][i];
							var originPath = newData['originPath'];
							if(originPath==undefined){
								newData['originPath'] = "";
								data['rows'][i] = newData;
							}
						}
					}
					$("#inputsList").datagrid("loadData",data);
					$("#inputsList").datagrid("clearChecked")
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
	
	function goPlanLink(id,index){
		debugger;
		var row;
		$('#inputsList').datagrid('selectRow',index);
		var all = $('#inputsList').datagrid('getRows');
		for(var i=0;i<all.length;i++){
			var inx = $("#inputsList").datagrid('getRowIndex', all[i]);
			if(inx == index){
				row = all[i];
			}
		}
		
		$.ajax({
			type:'POST',
			data:{inputsName : row.name},
			url:'planController.do?setInputsNameToSession',
			cache:false,
			success:function(data){
				var d = $.parseJSON(data);
				if(d.success){
					var url = 'planController.do?goSelectPlanInputs&projectId='+$("#projectId").val()+'&useObjectId='+$("#useObjectId").val()+'&useObjectType='+$("#useObjectType").val()+'&tempId='+row.id;
					//url = encodeURI(encodeURI(url))
					
					createDialog('planInputsDialog',url);
				}
			}
		});
	}
	
	
	function planInputsDialog(iframe){
		if (iframe.validateSelectedBeforeSave()) {
			debugger;
			var row = iframe.$('#planlist').datagrid('getSelections');
			var planId = row[0].id;
			var tempId = iframe.$("#tempId").val();
			var useObjectId = iframe.$("#useObjectId").val();
			var inputsName = iframe.$("#inputsName").val();
			
			$.ajax({
				type:'POST',
				data:{
					planId:planId,
					tempId : tempId,
					useObjectId : useObjectId,
					inputsName : inputsName
				},
				url : 'deliverablesInfoController.do?setPlanInputs',
				cache:false,
				success : function(data){
					initInputs('');
					$('#inputsList').datagrid('clearSelections');
				}
			});
		}else{
			return false;
		}
	}
	
	
	function projLibLinkOpt(r,s){
		if(r.originType == 'LOCAL'){
			return true;
		}else{
			return false;
		}
	}
	
	function planLinkOpt(r,s){
		if(r.originType == 'LOCAL'){
			return true;
		}else{
			return false;
		}
	}
	
	
</script>
