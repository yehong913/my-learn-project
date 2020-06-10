<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<t:base type="jquery,easyui,tools"></t:base>

<script type="text/javascript">

	function showSelectSNGeneratorDialog() {
		var url = 'url:serialNumberController.do?goSelectGenerator';
		$("#snGeneratorSelectDialog").lhgdialog("open", url);
	}
	function snGeneratorSelectCallBack(iframe){
		debugger;
		var row = iframe.$("#selectSNgen_datagrid").datagrid("getSelected");
		if(row)
		{
			$("#generatorInfoId").val(row.id);
			$("#generateRuleName").searchbox("setValue",row.name);
			$.ajax({
				url : 'repFileTypeConfigController.do?applyObjFormat&id='+row.id ,
				type : 'POST',
				dataType : 'json',
				success : function(d){
					$("#generatorRule").textbox("setValue",d.description);
					//生成器类型为规则时，有初始值
					if(d && d.generatorType && d.generatorType == "1"){
						$("#initValue").numberbox("setValue",d.startValue);
					}
				}
			});
		}
		else
		{
			$("#generatorInfoId").val("");
			$("#generateRuleName").searchbox("setValue","");
			$("#generatorRule").textbox("setValue","");
			/* top.tip('<spring:message code="com.glaway.foundation.repFileType.pleaseSelectCodeRule"/>');
			return false; */
		}
	}

	
	function submitRepFileTypeAdd(){
		debugger;
		var repFileTypeId = $("#repFileTypeId").val();
		var fileTypeCode = $("#fileTypeCode").val();
		var fileTypeName = $("#fileTypeName").textbox('getValue');
		var generatorInfoId = $("#generatorInfoId").val();
		var description = $("#description").textbox('getValue');
		if(fileTypeCode ==  "" || fileTypeCode == null){
			top.tip('<spring:message code="com.glaway.ids.pm.config.repFileTypeConfig.fileTypeCodeIsNull"/>');
			return false;
		}
		
		if(fileTypeName == "" || fileTypeName == null){
			top.tip('<spring:message code="com.glaway.ids.pm.config.repFileTypeConfig.fileTypeNameIsNull"/>');
			return false;
		}
		
		$.ajax({
			type : 'POST',
			data : {
				repFileTypeId : repFileTypeId,
				fileTypeCode : fileTypeCode
			},
			url : 'repFileTypeConfigController.do?checkFileTypeCodeBeforeSave',
			cache : false,
			success : function(data){
				var d = $.parseJSON(data);
				if(d.success){
					$.ajax({
						type:'POST',
						data:{
							repFileTypeId : repFileTypeId,
							fileTypeCode : fileTypeCode,
							fileTypeName : fileTypeName,
							generatorInfoId : generatorInfoId,
							description : description
						},
						url:'repFileTypeConfigController.do?saveRepFileTypeConfig',
						cache: false,
						success:function(data){
							var dat = $.parseJSON(data);
							if(dat.success){
								top.tip(dat.msg);
								var win = $.fn.lhgdialog("getSelectParentWin");
								win.reloadTable();
								$.fn.lhgdialog("closeSelect");
							}else{
								top.tip(dat.msg);
								return false;
							}
						}
						
					});
				}else{
					top.tip(d.msg);
					return false;
				}
			}
		});
	}
	


</script>
</head>
<body style="overflow-x: hidden;">
<fd:form id="repFileTypeConfigAddForm">

	<div region="center" style="padding: 0px;" fit="true">
		<input type = "hidden" id = "repFileTypeId" name ="repFileTypeId" value = "${fileType.id}" />
		<c:choose>
			<c:when test = "${type=='add'}">
				<fd:inputText name="fileTypeCode" id="fileTypeCode" title="{com.glaway.ids.pm.config.repFileTypeConfig.fileTypeCode}" required="true" />
			</c:when>
			<c:when test = "${type=='update'}">
				<fd:inputText name="fileTypeCode" id="fileTypeCode" title="{com.glaway.ids.pm.config.repFileTypeConfig.fileTypeCode}" value="${fileType.fileTypeCode}" required="true" />
			</c:when>
			<c:when test = "${type=='view'}">
				<fd:inputText name="fileTypeCode" id="fileTypeCode" title="{com.glaway.ids.pm.config.repFileTypeConfig.fileTypeCode}" value="${fileType.fileTypeCode}" readonly="true" />
			</c:when>
		</c:choose>
		
		<c:choose>
			<c:when test = "${type=='add'}">
				<fd:inputText name="fileTypeName" id="fileTypeName" title="{com.glaway.ids.pm.config.repFileTypeConfig.fileTypeName}" required="true" />
			</c:when>
			<c:when test = "${type=='update'}">
				<fd:inputText name="fileTypeName" id="fileTypeName" title="{com.glaway.ids.pm.config.repFileTypeConfig.fileTypeName}" value="${fileType.fileTypeName}" required="true" />
			</c:when>
			<c:when test = "${type=='view'}">
				<fd:inputText name="fileTypeName" id="fileTypeName" title="{com.glaway.ids.pm.config.repFileTypeConfig.fileTypeName}" value="${fileType.fileTypeName}" readonly="true" />
			</c:when>
		</c:choose>
		
		<c:choose>
			<c:when test = "${type=='add'}">
				<fd:inputSearch id="generateRuleName" name="generateRuleName"  onClickIcon="showSelectSNGeneratorDialog()" 
					missingMessage="{com.glaway.ids.pm.config.repFileTypeConfig.generateRuleMiss}"
					editable="false" title="{com.glaway.ids.pm.config.repFileTypeConfig.generateRuleName}"></fd:inputSearch>
				<input id="generatorInfoId" name="generatorInfoId" type="hidden" />
			</c:when>
			<c:when test = "${type=='update'}">
				<fd:inputSearch id="generateRuleName" name="generateRuleName"  onClickIcon="showSelectSNGeneratorDialog()" 
					missingMessage="{com.glaway.ids.pm.config.repFileTypeConfig.generateRuleMiss}" value="${generateRuleName}"
					editable="false" title="{com.glaway.ids.pm.config.repFileTypeConfig.generateRuleName}"></fd:inputSearch>
				<input id="generatorInfoId" name="generatorInfoId" type="hidden" value="${fileType.generateRuleId}"/>
			</c:when>
			<c:when test = "${type=='view'}">
				<fd:inputSearch id="generateRuleName" name="generateRuleName"  onClickIcon="showSelectSNGeneratorDialog()" readonly="true"
					missingMessage="{com.glaway.ids.pm.config.repFileTypeConfig.generateRuleMiss}" value="${generateRuleName}"
					editable="false" title="{com.glaway.ids.pm.config.repFileTypeConfig.generateRuleName}"></fd:inputSearch>
				<input id="generatorInfoId" name="generatorInfoId" type="hidden" value="${fileType.generateRuleId}" />
			</c:when>
		</c:choose>
		
		<c:choose>
			<c:when test = "${type=='add'}">
				<fd:inputText name="generatorRule" id="generatorRule" title="{com.glaway.foundation.snGenerator.ruleDesc}" readonly="true"/>
			</c:when>
			<c:when test = "${type=='update'}">
				<fd:inputText name="generatorRule" id="generatorRule" title="{com.glaway.foundation.snGenerator.ruleDesc}"  value="${generateRuleDesc}" readonly="true"/>
			</c:when>
			<c:when test = "${type=='view'}">
				<fd:inputText name="generatorRule" id="generatorRule" title="{com.glaway.foundation.snGenerator.ruleDesc}"  value="${generateRuleDesc}" readonly="true"/>
			</c:when>
		</c:choose>
		
		<c:choose>
			<c:when test = "${type=='add'}">
				<fd:inputTextArea name="description" id="description" title="{com.glaway.foundation.common.description}" maxLength="200"/>
			</c:when>
			<c:when test = "${type=='update'}">
				<fd:inputTextArea name="description" id="description" title="{com.glaway.foundation.common.description}" value="${description}" maxLength="200"/>
			</c:when>
			<c:when test = "${type=='view'}">
				<fd:inputTextArea name="description" id="description" title="{com.glaway.foundation.common.description}" value="${description}" maxLength="200" readonly="true"/>
			</c:when>
		</c:choose>
		
		
	</div>
</fd:form>
<fd:dialog id="snGeneratorSelectDialog"
	title="{com.glaway.foundation.snGenerator.snGenerator}" width="800"
	height="500" modal="true">
	<fd:dialogbutton name="{com.glaway.foundation.common.ok}"
		id="snGeneratorSelect_submitBtn" callback="snGeneratorSelectCallBack"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.foundation.common.cancel}"
		id="snGeneratorSelect_closeBtn" callback="hideDialog"></fd:dialogbutton>
</fd:dialog>

</body>
