<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>变更详情</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>	
<div style=" float:left;  width:100%; overflow-x:hidden" >
	<c:if test="${!empty recordList}">
		<c:forEach items="${recordList}" var="record" step="1" begin="0" varStatus="panelIndex">
		
			<fd:panel id="recordPanel${record.id}" title="变更时间:${record.changeTime}" collapsible="true" collapsed="${panelIndex.index>0}" fit="false"  width="100%"  >
		<div style="width:99%;height:350px ">
				<input id="useObjectId" name="useObjectId" type="hidden" value="${useObjectId}">
				
				<input id="useObjectType" name="useObjectType" type="hidden" value="${useObjectType}">
				
				
				<fd:inputText id="changeType${record.id}"  title="变更类型"  name="changeType" readonly="true"  
		                	value="${record.changeType}">
		         </fd:inputText>
		           
				<fd:inputText id="changeOwner${record.id}"  title="变更人"  name="changeOwner" readonly="true"
					value="${record.changeOwner}">
				</fd:inputText>
			
				<fd:inputTextArea id="changeRemark${record.id}" title="{com.glaway.ids.pm.project.plan.remark}" name="changeRemark" value="${record.changeRemark}" 
					readonly="true">
				</fd:inputTextArea>
				
				<fd:datagrid idField="id" id="changeRecordList${record.id}" checkbox="false" pagination="false"  width="100%"  fitColumns="true"
					url="planChangeController.do?recordDatagrid&planId=${record.changePlanId}&formId=${record.changeFormId}">
					<fd:dgCol title="变更项" field="field"    sortable="false"/>
					<fd:dgCol title="类型" field="type"   sortable="false"></fd:dgCol>
					<fd:dgCol title="变更前" field="changeBefore"   sortable="false"></fd:dgCol>
					<fd:dgCol title="变更后" field="changeAfter" width="120"  sortable="false"></fd:dgCol>
				</fd:datagrid>
				</div>
			</fd:panel>
			
		</c:forEach>
	</c:if>
	</div>
	
	<%-- <fd:panel id="four" title="{com.glaway.ids.pm.project.plan.input}" collapsible="true" collapsed="true" fit="false" width="100%">
		<fd:datagrid  idField="id" id="inputList" checkbox="false" pagination="false" fit="false" width="100%" fitColumns="true"
			url="planChangeController.do?inputListView&formId=${formId}">
			<fd:dgCol title="id" field="id" hidden="true"  sortable="false"/>
			<fd:dgCol title="inputId" field="inputId" hidden="true"  sortable="false"/>
			<fd:dgCol title="{com.glaway.ids.common.lable.name}" field="name" width="300"   sortable="false"/>
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.docName}" field="docName" width="120" formatterFunName="addLink"  sortable="false"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.orgin}"  field="origin"  width="300"  sortable="false"/>
		</fd:datagrid>
	</fd:panel> --%>
	
	
	
	<fd:dialog id="openAndSubmitDialog" width="870px" height="680px" modal="true" 
		title="{com.glaway.ids.pm.project.plan.basicLine.showDocDetail}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="resourceDialog" width="900px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.resourcePicture}" maxFun="true">
	</fd:dialog>
<script type="text/javascript">
	
</script>
</body>
</html>