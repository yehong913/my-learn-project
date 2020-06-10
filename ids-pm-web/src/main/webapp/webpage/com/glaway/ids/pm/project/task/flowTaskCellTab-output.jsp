<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-panel" id="cellOutput" fit="true">
	<input type="hidden" id ='isEnableFlag' name='isEnableFlag' value="${isEnableFlag}"/>
	<script >
	$(function($) {
		if("1" == $("#isEnableFlag").val()){
			$("#inheritParent").hide();
			$("#openAddOutputs").hide();
		}
	});

	var gridname;
	function isNecessary(val, row, index) {
		if (val == "true")
			return "是";
		else
			return "否";
	}
	
	function addOutputs(){
		debugger
		
		if("1" == $("#isEnableFlag").val()){
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
			return;
		}
		gridname = 'deliverablesInfoList';
		var dialogUrl = 'taskFlowResolveController.do?goAdd&type=OUTPUT&cellId='+$("#cellId").val()+'&parentPlanId='+$("#parentPlanId").val();
		createDialog('addOutDialog',dialogUrl);
	}
	

		function addOutDialog(iframe) {
			iframe.submitSelectData();
		}

		// 删除输出
		function deleteOutput(index) {

			if ("1" == $("#isEnableFlag").val()) {
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
				return;
			}
			var rows = $("#outputList").datagrid('getRows');
			var id = rows[index].id;
			if (rows[index].required == 'true') {
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.noDelNecessaryOutput"/>');
			} else {
				top.Alert.confirm(
						'<spring:message code="com.glaway.ids.common.confirmDel"/>',
						function(r) {
							if (r) {
								$.ajax({
									url : 'taskFlowResolveController.do?doDeloutput',
									type : 'post',
									data : {
										'id' : id
									},
									cache : false,
									success : function(data) {
										var d = $.parseJSON(data);
										var msg = d.msg.split('<br/>')
										top.tip(msg[0]); // John
										if (d.success) {
											$
													.ajax({
														type : 'POST',
														url : 'taskFlowResolveController.do?outputList',
														async : false,
														data : {
															cellId : $('#cellId').val(),
															parentPlanId : $(
																	'#parentPlanId')
																	.val()
														},
														success : function(data) {
															$("#outputList").datagrid(
																	"loadData", data);
														}
													});
										} else {
											var msg = d.msg.split('<br/>')
											top.tip(msg[0]); // John
										}
									}
								});
							}
						});

			}
		}

		//自定义行显示
		function funNameOutput(val, row, index) {
			var returnStr = '<a class="basis ui-icon-minus" style="display:inline-block;cursor:pointer;" onClick="deleteOutput(\''
	            + index + '\')" title="<spring:message code='com.glaway.ids.common.btn.remove'/>"></a>';
			return returnStr;
		}

		function initOutputs(this_) {
			var datas = "";
			var flg = false;
			
			if (this_ != null && this_ != '' && this_ != undefined) {
				datas = this_;
			} else {
				datas = {
					cellId : $('#cellId').val(),
					parentPlanId : $('#parentPlanId').val()
				}
			}
			$.ajax({
				type : 'POST',
				url : 'taskFlowResolveController.do?outputList',
				async : false,
				data : datas,
				success : function(data) {
					$("#outputList").datagrid("loadData", data);
					flg = true;
				}
			});
			return flg;
		}
		
		function initOutputsFlowTask() {
			$("#outputList").datagrid("reload");
		}

		// 新增计划
		function inheritParent() {
			if ("1" == $("#isEnableFlag").val()) {
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
				return;
			}
			if ($("#parentPlanId").val() != null
					&& $("#parentPlanId").val() != undefined) {
				var dialogUrl = 'taskFlowResolveController.do?goAddInherit&cellId='
						+ $("#cellId").val()
						+ '&parentPlanId='
						+ $("#parentPlanId").val();
				createDialog('inheritParentDialog', dialogUrl);
			} else {
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.deliveryNoParent"/>');
			}

		}

		function inheritParentDialog(iframe) {
			iframe.submitSelectData();
		}

		function addLink(val, row, value) {
			if (val != null && val != '') {
				if((row.havePower == true || row.havePower == 'true') && '${userLevel}' >= row.securityLevel){
					/* return '<a  href="#" onclick="showDocDetail(\'' + row.docId
					+ '\',this)" id="myDoc"  style="color:blue">' + val
					+ '</a>'; */
					return "<a href='#' onclick='showDocDetail(\""
					+ row.docId
					+ "\""
					+ ','
					+ "\""
					+ row.download
					+ "\""
					+ ','
					+ "\""
					+ row.detail
					+ "\")'  id='myDoc'  style='color:blue'>" + val +"</a>";
				}else{
					return val;
				}
			} else
				return;
		}

		function showDocDetail(id,download,detail) {
			var url="projLibController.do?viewProjectDocDetail&opFlag=1&id="+id+ "&download=" + download+ "&detail=" + detail;
			createdetailwindow2("<spring:message code='com.glaway.ids.pm.project.plan.basicLine.showDocDetail'/>", url, "1000", "550");
		}

		function createdetailwindow2(title, url, width, height) {
			width = width ? width : 700;
			height = height ? height : 400;
			if (width == "100%" || height == "100%") {
				width = document.body.offsetWidth;
				height = document.body.offsetHeight - 100;
			}

			createDialogBySize('showDocDetailFlowTaskOutputDialog', url, width, heigth, title);
		}

		function hideOperateAreaColumn() {
			if ("1" == $("#isEnableFlag").val()) {
				$('#outputList').datagrid('hideColumn', 'operateArea');
			}
		}
	</script>
	<fd:toolbar id="outTB">
		<fd:toolbarGroup align="left">
			<fd:linkbutton value="{com.glaway.ids.pm.project.plan.inheritParent}" onclick="inheritParent()"
				iconCls="basis ui-icon-copy"  id ="inheritParent"/>
			<fd:linkbutton value="{com.glaway.ids.common.btn.create}" onclick="addOutputs()"
				iconCls="basis ui-icon-plus"  id ="openAddOutputs"/>
		</fd:toolbarGroup>
	</fd:toolbar>
	<!-- editable datagrid -->
	<fd:datagrid toolbar="#outTB" idField="id" id="outputList"  onLoadSuccess="hideOperateAreaColumn"
		checkbox="false" fitColumns="true"
		url="taskFlowResolveController.do?outputList&cellId=${cellId}&parentPlanId=${parentPlanId}"
		pagination="false" fit="true">
		<fd:dgCol title="{com.glaway.ids.common.lable.operation}" field="operateArea" formatterFunName="funNameOutput"  width="40"/>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.deliverableName}" field="name" width="280"/>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.docName}" field="docName" width="200" formatterFunName="addLink"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.task.required}" field="required" width="30" formatterFunName="isNecessary"/>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.target}" field="result" width="250"/>
	</fd:datagrid>
</div>
	<fd:dialog id="addOutDialog" width="800px" height="530px" zIndex="4300" modal="true" title="{com.glaway.ids.pm.project.plan.deliverables.addOutputs}">
	    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addOutDialog"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="inheritParentDialog" width="400px" height="300px" modal="true" zIndex="4300"  title="{com.glaway.ids.pm.project.plan.deliverables.inheritParentDeliverables}">
	    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="inheritParentDialog"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="showDocDetailFlowTaskOutputDialog" modal="true" zIndex="4300">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
