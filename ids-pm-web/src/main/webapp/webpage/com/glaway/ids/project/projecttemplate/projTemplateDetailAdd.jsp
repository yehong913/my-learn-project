<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>模板详情</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
<script type="text/javascript">
	$(document).ready(function() {
		window.setTimeout('initData()',400);
	});

	function checkNameRepeat(){
		//  todo
		var name = $("#projTmplName").val();
		$.ajax({
			url: 'projTemplateController.do?checkTemplateNameRepeat&type=add&name='+name,
			type : 'post',
			data : {
				name : name
			},
			success : function(data){
				var d= $.parseJSON(data)
				if(d.success){
					return true;
				}else{
					top.tip(d.msg)
					return false;
				}
			}
		})
	}

	function submitBaseInfo()
		{
			debugger;
			var name=$("#projTmplName").val();
			if(undefined == name || null == name || "" == name) {
				name=$("#projTmplName1").val();
			}
			if(undefined == name || null == name || "" == name) {
				name=$("#tmpName").val();
			}
			var remark=$("#remark").val();
			if(undefined == remark || null == remark || "" == remark) {
				remark=$("#tmpRemark").val();
			}
			var id=id=$("#tmpId").val();
			if(undefined == id || null == id || "" == id) {
				id='${templateId}';
			}
			if(undefined == id || null == id || "" == id) {
				id=$("#templateId").val();
			}
			if($("#step").val() == "1") {
				if(undefined == name || null == name || "" == name) {
					top.tip('<spring:message code="com.glaway.ids.pm.project.projecttemplate.nameNoEmpty"/>')
					return false;
				}
			}
			$.ajax({
				url: 'projTemplateController.do?checkTemplateNameRepeat&type=add&name='+name,
				type : 'post',
				data : {
					name : name,
					templateId : id
				},
				success : function(data){
					var d= $.parseJSON(data)
					debugger;
					if(d.success){
						$.ajax({
							url : 'projTemplateController.do?doSaveNewTemplate',
							type : 'post',
							data : {
								name : name,
								remark : remark,
								templateId : id,
								method : '${method}'
							},
							cache : false,
							success : function(data) {
								var d = $.parseJSON(data);
								if (d.success) {
									var msg = d.msg;
									tip(msg);
									$("#tmpName").val(d.obj.projTmplName);
									$("#name2").val(d.obj.projTmplName);
									$("#tmpRemark").val(d.obj.remark);
									$("#remark2").val(d.obj.remark);
									$("#tmpId").val(d.obj.persientId);
									$("#tmpBiz").val(d.obj.bizCurrent);

									$("#templateId").val(d.obj.persientId);
									$('#projTemplateAddForm').form('load',{
										projTmplName : d.obj.projTmplName,
											remark :	d.obj.remark
									});
									var win = $.fn.lhgdialog("getSelectParentWin");
									win.$("#projTemplateList").datagrid('clearSelections');
									win.$("#projTemplateList").datagrid('reload');
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
	function initData() {
		var name = '${template.projTmplName}';
		var remark = '${template.remark}';
		var templateId = '${templateId}';
		if(isNullStr(name)) {
			name = $("#tmpName").val();
		}
		if(isNullStr(remark)) {
			remark = $("#tmpRemark").val();
		}
		if(isNullStr(templateId)) {
			remark = $("#tmpId").val();
		}
		$("#projTmplName").val(name);
		$("#remark").val(remark);
		$("#templateId").val(templateId);
		$("#saved").val('${init}');
	}
	function isNullStr(s) {
		if(undefined == s || null == s || "" == s.trim()) {
			return true;
		}else { return false}
	}
</script>
	<c:if test="${copy !='copy' }">
		<div style="padding-top: 5px;padding-left: 5px;">
			<fd:linkbutton onclick="submitBaseInfo()" value="保存" classStyle="" iconAlign="left" iconCls="basis ui-icon-save"  />
		</div>
	</c:if>
	<fd:form id="projTemplateAddForm"
		method="projTemplateController.do?doSaveNewTemplate&templateId=${templateId}">
		<input id="templateId" name="templateId" type="hidden" value="${templateId}" />
		<input id="saved" name="saved" value="saved" type="hidden" />
		<input id="step" name="step" type="hidden" value="1" />
		<input id="name2" name="name2" type="hidden" value="${template.projTmplName}" />
		<input id="remark2" name="remark2" type="hidden" value="${template.remark}" />
		<input id="bizCurrent" name="bizCurrent" type="hidden" value="${template.bizCurrent}" />
		<c:choose>
			<c:when test="${method eq 'miner'|| method eq 'revise' || (templateId ne undenfied && copy ne 'copy')}">
				<fd:inputText id="projTmplName" readonly="true" editable="false"  name="projTmplName" required="true"
					type="text" title="{com.glaway.ids.pm.projecttemplate.name}"
					value='${template.projTmplName}' />
			</c:when>
			<c:otherwise>
				<fd:inputText id="projTmplName" readonly="false" editable="true"  name="projTmplName" required="true"
					type="text" title="{com.glaway.ids.pm.projecttemplate.name}"
					value='${template.projTmplName}' />
			</c:otherwise>
		</c:choose>
		<fd:inputTextArea name="remark" id="remark" title="{com.glaway.ids.pm.project.projecttemplate.remark}" readonly="false" value="${template.remark}" />
	</fd:form>
	<fd:dialog id="modifyTemplateCopyDialog" width="1000px" height="500px" modal="true" title="编辑项目模板">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addTemplateSubmit"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
</body>
