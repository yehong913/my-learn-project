<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目启动、暂停、恢复、关闭，文档审批</title>
<t:base type="jquery,easyui,lhgdialog,tools,ckeditor"></t:base>
<script type="text/javascript">
	//编写自定义JS代码
</script>
</head>
<body>

	<fd:form id="projectStartSubmitForm">
		<input id="id" name="id" type="hidden" value="${project.id}" />
		<input id="docId" name="docId" type="hidden" value="${docId}" />
		<div class="form">
			<input type="hidden" id="approvePerson" name="approvePerson" />
			<fd:inputSearchUser id="approvePersonId" name="approvePersonId"
				title="{com.glaway.ids.common.lable.leader}" required="true">
				<fd:eventListener event="beforeAffirmClose"
					listener="setApprovePerson" needReturn="true" />
			</fd:inputSearchUser>
		</div>
		<div class="form">
			<input type="hidden" id="deptApprovePerson" name="deptApprovePerson" />
			<fd:inputSearchUser id="deptApprovePersonId"
				name="deptApprovePersonId"
				title="{com.glaway.ids.common.lable.deptLeader}" required="true">
				<fd:eventListener event="beforeAffirmClose"
					listener="setDeptApprovePerson" needReturn="true" />
			</fd:inputSearchUser>
		</div>
		<div class="form">
			<fd:inputTextArea name="remark" id="remark"
				title="{com.glaway.ids.pm.project.projectmanager.remark}" />
		</div>
	</fd:form>

	<script type="text/javascript">
		$(document).ready(function() {
			parent.loadTree("${project.id}", 1);
			if ('${param.closedProject}' == 1) { // 项目关闭时备注必填
				$('#remark').textbox({
					required : true
				});
			}
		});

		function setApprovePerson(obj) {
			var username = ""; // 工号
			if (obj && obj.length > 0) {
				var singleUser = obj[0].split(':');
				username = singleUser[2];
			}
			$('#approvePerson').val(username);

			return true;
		}

		function setDeptApprovePerson(obj) {
			var username = ""; // 工号
			if (obj && obj.length > 0) {
				var singleUser = obj[0].split(':');
				username = singleUser[2];
			}
			$('#deptApprovePerson').val(username);

			return true;
		}

		function startSubmitProject() {
			if ($('#approvePerson').val() == '') {
				$.fn
						.lhgdialog("getSelectParentWin")
						.tip(
								'<spring:message code="com.glaway.ids.common.emptyOfficeLeader"/>');
				return;
			}
			if ($('#deptApprovePerson').val() == '') {
				$.fn
						.lhgdialog("getSelectParentWin")
						.tip(
								'<spring:message code="com.glaway.ids.common.emptyDepartLeader"/>');
				return;
			}
			$.ajax({
				type : 'POST',
				url : 'projectController.do?doStartProject',
				data : $('#projectStartSubmitForm').serialize(),
				success : function(data) {
					var d = $.parseJSON(data);
					$.fn.lhgdialog("getSelectParentWin").tip(d.msg);
					if (d.success) {
						var detail = "${detail}";
						if (detail != "") {
							$.fn.lhgdialog("getSelectParentWin").hideBtn();
						} else {
							$.fn.lhgdialog("getSelectParentWin")
									.reloadProjectList();
						}
						$.fn.lhgdialog("closeSelect");
					}
				}
			});
		}

		function pauseProject() {
			if ($('#approvePerson').val() == '') {
				$.fn
						.lhgdialog("getSelectParentWin")
						.tip(
								'<spring:message code="com.glaway.ids.common.emptyOfficeLeader"/>');
				return;
			}
			if ($('#deptApprovePerson').val() == '') {
				$.fn
						.lhgdialog("getSelectParentWin")
						.tip(
								'<spring:message code="com.glaway.ids.common.emptyDepartLeader"/>');
				return;
			}
			$.ajax({
				type : 'POST',
				url : 'projectController.do?doPauseOrResumeProject&operation=PAUSE',
				data : $('#projectStartSubmitForm').serialize(),
				success : function(data) {
					var d = $.parseJSON(data);
					$.fn.lhgdialog("getSelectParentWin").tip(d.msg);
					if (d.success) {
						var detail = "${detail}";
						if (detail != "") {
							$.fn.lhgdialog("getSelectParentWin").hideBtn();
						} else {
							$.fn.lhgdialog("getSelectParentWin")
									.reloadProjectList();
						}
						$.fn.lhgdialog("closeSelect");
					}
				}
			});

		}

		function resumeProject() {
			if ($('#approvePerson').val() == '') {
				$.fn.lhgdialog("getSelectParentWin").tip('<spring:message code="com.glaway.ids.common.emptyOfficeLeader"/>');
				return;
			}
			if ($('#deptApprovePerson').val() == '') {
				$.fn.lhgdialog("getSelectParentWin").tip('<spring:message code="com.glaway.ids.common.emptyDepartLeader"/>');
				return;
			}
			$.ajax({
				type : 'POST',
				url : 'projectController.do?doPauseOrResumeProject&operation=RESUME',
				data : $('#projectStartSubmitForm').serialize(),
				success : function(data) {
					var d = $.parseJSON(data);
					$.fn.lhgdialog("getSelectParentWin").tip(d.msg);
					if (d.success) {
						var detail = "${detail}";
						if (detail != "") {
							$.fn.lhgdialog("getSelectParentWin").hideBtn();
						} else {
							$.fn.lhgdialog("getSelectParentWin")
									.reloadProjectList();
						}
						$.fn.lhgdialog("closeSelect");
					}
				}
			});

		}

		function closeProject() {
			if ($('#approvePerson').val() == '') {
				$.fn
						.lhgdialog("getSelectParentWin")
						.tip(
								'<spring:message code="com.glaway.ids.common.emptyOfficeLeader"/>');
				return;
			}
			if ($('#deptApprovePerson').val() == '') {
				$.fn
						.lhgdialog("getSelectParentWin")
						.tip(
								'<spring:message code="com.glaway.ids.common.emptyDepartLeader"/>');
				return;
			}
			if ($('#remark').val() == '') {
				$.fn
						.lhgdialog("getSelectParentWin")
						.tip(
								'<spring:message code="com.glaway.ids.common.emptyRemark"/>');
				return;
			}
			$.ajax({
				type : 'POST',
				url : 'projectController.do?doCloseProject',
				data : $('#projectStartSubmitForm').serialize(),
				success : function(data) {
					var d = $.parseJSON(data);
					$.fn.lhgdialog("getSelectParentWin").tip(d.msg);
					if (d.success) {
						var detail = "${detail}";
						if (detail != "") {
							$.fn.lhgdialog("getSelectParentWin").hideBtn();
						} else {
							$.fn.lhgdialog("getSelectParentWin")
									.reloadProjectList();
						}
						$.fn.lhgdialog("closeSelect");
					}
				}
			});
		}

		function forceCloseProject() {
			if ($('#approvePerson').val() == '') {
				$.fn
						.lhgdialog("getSelectParentWin")
						.tip(
								'<spring:message code="com.glaway.ids.common.emptyOfficeLeader"/>');
				return;
			}
			if ($('#deptApprovePerson').val() == '') {
				$.fn
						.lhgdialog("getSelectParentWin")
						.tip(
								'<spring:message code="com.glaway.ids.common.emptyDepartLeader"/>');
				return;
			}
			if ($('#remark').val() == '') {
				$.fn
						.lhgdialog("getSelectParentWin")
						.tip(
								'<spring:message code="com.glaway.ids.common.emptyRemark"/>');
				return;
			}
			$.ajax({
				type : 'POST',
				url : 'projectController.do?doCloseProject',
				data : $('#projectStartSubmitForm').serialize(),
				success : function(data) {
					var d = $.parseJSON(data);
					$.fn.lhgdialog("getSelectParentWin").tip(d.msg);
					if (d.success) {
						var detail = "${detail}";
						if (detail != "") {
							$.fn.lhgdialog("getSelectParentWin").refreshRightPage();
						} else {
							$.fn.lhgdialog("getSelectParentWin")
									.reloadProjectList();
						}
						$.fn.lhgdialog("closeSelect");
					}
				}
			});
		}

		function sumbitDocApprove() {
			if ($('#approvePerson').val() == '') {
				$.fn
						.lhgdialog("getSelectParentWin")
						.tip(
								'<spring:message code="com.glaway.ids.common.emptyOfficeLeader"/>');
				return;
			}
			if ($('#deptApprovePerson').val() == '') {
				$.fn
						.lhgdialog("getSelectParentWin")
						.tip(
								'<spring:message code="com.glaway.ids.common.emptyDepartLeader"/>');
				return;
			}
			$.ajax({
				type : 'POST',
				url : 'projLibController.do?doSubmitToApprove',
				data : $('#projectStartSubmitForm').serialize(),
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						$.fn.lhgdialog("getSelectParentWin").tip(d.msg);
						$.fn.lhgdialog("getSelectParentWin").reload();
						$.fn.lhgdialog("closeSelect");
					}
				}
			});
		}
	</script>
</body>