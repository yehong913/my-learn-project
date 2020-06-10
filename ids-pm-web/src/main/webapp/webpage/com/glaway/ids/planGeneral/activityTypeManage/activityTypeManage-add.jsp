<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>专家新增</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  	function setExpName(obj) {
		var showname = ""; // 姓名-工号
		if (obj && obj.length > 0) {
			var singleUser = obj[0].split(':');
			showname = singleUser[1];
		}
		$('#name').val(showname);
		
		return true;
	}	
  	
	function formsubmit() {
 		var params = '';
 		var name = $('#name').val();
		var remark = $('#remark').textbox("getValue");
		var id = $('#id').val();
		if (name == null || name == '' || name == undefined) {			
			top.tip('<spring:message code="com.glaway.ids.activityTypeManage.nameNotEmpty"/>');
			return false;
		}

		params = params + '&name=' + name;
		params = params + '&remark=' + remark;
		params = params + '&id=' + id;

	 	var url = encodeURI('activityTypeManageController.do?doAdd' + params);
		 
 		$('#addForm').form('submit', {
			url : url,
			onSubmit : function(param) {
			},
			success : function(data) {
				var json = $.evalJSON(data);
				top.tip(json.msg);
				var win = $.fn.lhgdialog("getSelectParentWin");
				if (json.success) {
					$.fn.lhgdialog("closeSelect");
					win.reloadActivityTable();
				}
			}
		});
	}
	
	function selectExpertType() {
		if($('#type').combobox('getValue') == "2") {
			$('#inputExpert').css('display', 'none');
			$('#selExpert').css('display', 'block');
			$('#name1').textbox('setValue', '');
		}
		
		if($('#type').combobox('getValue') == "1") {   
			$('#selExpert').css('display', 'none');
			$('#inputExpert').css('display', 'block');
			$('#userId').textbox('setValue', '');
			$('#name').val('');
		}	
	}
  </script>
 </head>
 
 <body>
	<fd:form id="addForm">
		<input id="id" name="id" type="hidden" value="${activity.id}" />
		<div>
			<div id="inputExpert">
				<fd:inputText id="name"
					title="{com.glaway.ids.common.lable.name}" required="true" value="${activity.name}"></fd:inputText>
			</div>
			<fd:inputTextArea id="remark" value="${activity.remark}"
				title="{com.glaway.ids.common.lable.remark}"></fd:inputTextArea>
		</div>
		<input type="button" id="btn_sub" style="display: none;"
			onclick="formsubmit();">
	</fd:form>
</body>