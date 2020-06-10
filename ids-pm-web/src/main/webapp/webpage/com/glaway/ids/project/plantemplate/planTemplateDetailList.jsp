<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>计划模板详细</title>
<t:base type="jquery,easyui,tools"></t:base>
<!-- <script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script> -->


</head>
<body>
	<div class="easyui-layout">
		<fd:form id="formId">	
			<fd:lazyLoadingTreeGrid id="planTemplateDetailList"
                url="planTemplateDetailController.do?datagrid&id=${planTemplate_.id}"
                height="495px;" width="100%" imgUrl="plug-in/icons_greenfolders/"
                initWidths="80,200,80,100,60,*,*,*,*"
                columnIds="planTmpNumber,name,planLevel,workTimeDisplay,milestone,inputsName,origin,deliverablesName,preposeName"
                header="序号,计划名称,计划等级,参考工期(天),里程碑,输入项名称,来源,交付项名称,前置计划"
                columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;']"
                colAlign="left,left,left,left,left,left,left,left,left"
                colSortings="na,na,na,na,na,na,na,na,na"
                colTypes="ro,tree,ro,ro,ro,ro,ro,ro,ro"
                enableTreeGridLines="true" enableLoadingStatus="false">
            </fd:lazyLoadingTreeGrid>
		</fd:form>
	</div>
</body>
<script src="webpage/com/glaway/ids/project/plantemplate/planTemplateDetailList.js"></script>
</html>

