<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						debugger;
						//判断是否从KDD跳入到IDS团队中的参数
						var tipDelType = '${param.kddProductTeamType}';
						//从kdd跳入到IDS中团队  判断是否有操作权限的参数
						var kddTeamOptPower = '${kddTeamOptPower}';
						if ('${refreshTree}' == 'true') { // 非审批页面进入才刷新右侧树
							try {
								parent.loadTree("${projectId}", 1);
							} catch (e) {
							}
						}
						if ('${addProjRole}' == 'true' || '${addProjRole}') {
							$('#addRole').show();
						}

						if ('${addProjMember}' == 'true' || '${addProjMember}') {
							$('#addUser').show();
						}

						if ('${removeProjRoleOrMember}' == 'true'
								|| '${removeProjRoleOrMember}') {
							$('#removeRoleOrUser').show();
						}
						//判断当前用户是否有操作权限  前提是从KDD中跳入到IDS得团队页面处理 隐藏操作按钮
						if (tipDelType != null
								&& tipDelType == "kddProduct"
								&& !(kddTeamOptPower != null && kddTeamOptPower == "kddTeamOptPower")) {
							$('#addRole').hide();
							$('#removeRoleOrUser').hide();
						}
					});

	function openRole() {
		getselectRole();
	}

	/**
	 * 刷新页面
	 */
	function reload() {

		//$("#roleListTable").treegrid('reload');
		var templateId = parent.getCurrentId();
		if(undefined == templateId || null == templateId || "" == templateId) {
		 	templateId = $("#tmpId").val();
		}
		if(undefined == templateId || null == templateId || "" == templateId) {
			templateId='${templateId}';
		}
		if(undefined == templateId || null == templateId || "" == templateId) {
			templateId=$("#templateId").val();
		}
		debugger;
		 $.ajax({
			url : "projTemplateController.do?refreshTeamListFromSession&templateId="+templateId,
			type : 'post',
			data : {
				templateId : templateId
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				debugger;
				$("#tmpId").val(templateId);
				$("#templateId").val(templateId);
				mygrid_roleListTable.clearAll();
				mygrid_roleListTable.parse(d.obj, 'js');
			}
		});
	}

	/**
	 * 查看时的弹出窗口
	 *
	 * @param title
	 * @param addurl
	 * @param saveurl
	 */
	function createRolewindow(title, addurl, width, height, id) {
		width = width ? width : 700;
		height = height ? height : 400;
		if (width == "100%" || height == "100%") {
			width = document.body.offsetWidth;
			height = document.body.offsetHeight - 100;
		}
		if (typeof (windowapi) == 'undefined') {
			$.fn
					.lhgdialog({
						content : 'url:' + addurl,
						id : id,
						parent : windowapi,
						lock : true,
						width : width,
						height : height,
						title : title,
						opacity : 0.3,
						cache : false,
						okVal : '<spring:message code="com.glaway.ids.common.btn.confirm"/>',
						cancelVal : '<spring:message code="com.glaway.ids.common.btn.cancel"/>',
						cancel : true,
						ok : function() {
							iframe = this.iframe.contentWindow;
							var codes = [];
							var checkedItems = iframe.getDatas();
							if (checkedItems == null
									|| checkedItems.length == 0) {
								tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.role.selectRole"/>');

								return false;
							}
							$.each(checkedItems, function(index, item) {
								codes.push(item.roleCode);
							});
							codes.join(",");
							doBatchAdd(codes);

						},
					});
		} else {
			$.fn
					.lhgdialog({
						content : 'url:' + addurl,
						lock : true,
						width : width,
						height : height,
						parent : windowapi,
						title : title,
						opacity : 0.3,
						cache : false,
						cancelVal : '<spring:message code="com.glaway.ids.common.btn.cancel"/>',
						okVal : '<spring:message code="com.glaway.ids.common.btn.confirm"/>',
						cancel : true, /*为true等价于function(){}*/
						ok : function() {
							iframe = this.iframe.contentWindow;
							var codes = [];
							var checkedItems = iframe.getDatas();
							if (checkedItems == null
									|| checkedItems.length == 0) {
								tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.role.selectRole"/>');
								return false;
							}
							$.each(checkedItems, function(index, item) {
								codes.push(item.roleCode);
							});
							codes.join(",");
							doBatchAdd(codes);
							//iframe.reload('');
						},
					});
		}
	}
	/**
	 * 批量增加角色
	 *
	 * @param 角色codes
	 */
	function doBatchAddRoles(roles) {
		var templateId = parent.getCurrentId();
		if(undefined == templateId || null == templateId || "" == templateId) {
		 	templateId = $("#tmpId").val();
		}
		if(undefined == templateId || null == templateId || "" == templateId) {
			templateId='${templateId}';
		}
		if(undefined == templateId || null == templateId || "" == templateId) {
			templateId=$("#templateId").val();
		}
		//保存新增加的角色
		debugger;
		$.ajax({
			type : "POST",
			url : 'projTemplateController.do?doBatchAdd',
			data : {
				'roles' : $.toJSON(roles),
				'templateId' : templateId
			},
			dataType : "json",
			success : function(data) {
				debugger;
				$("#tmpId").val(templateId);
				$("#templateId").val(templateId);
				reload();
			}

		});
	}


	/**
	 * 为角色增加人员或组
	 *
	 * @param 角色codes
	 */
	function openUser(type) {
		var selectedId = mygrid_roleListTable.getSelectedRowId();
		if (selectedId != undefined && selectedId != null && selectedId != '') {
			var selectedIdArr = selectedId.split(",");
			if (selectedIdArr.length == 1) {
				var rowId = selectedIdArr[0];
				var roleCode = mygrid_roleListTable.getRowAttribute(rowId, "roleCode");
				if (type == "GROUP") {
					var ss = rowId.split("-");
					if (ss[1] != null) {
						tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.role.selectOne"/>');
						return;
					}
					selectGroup(roleCode);
				} else {
					var ss = rowId.split("-");
					if (ss[1] != null) {
						tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.role.selectOne"/>');
						return;
					}
					selectUser(roleCode, rowId);
				}
			} else if (selectedIdArr.length == 0) {
				tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.role.selectOne"/>');
				return;
			} else {
				tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.role.onlySelectOne"/>');
				return;
			}
		} else {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.role.selectOne"/>');
			return;
		}
	}


	/**
	 *角色的操作
	 */
	function getselectRole() {
		debugger;
		var templateId = $("#tmpId").val();
		if(undefined == templateId || null == templateId || "" == templateId) {
			templateId='${templateId}';
		}
		if(undefined == templateId || null == templateId || "" == templateId) {
			templateId=$("#templateId").val();
		}
		var selects = "";
		var selectMode = 'multi';

		$.ajax({
			type: "POST",
			url: 'projTemplateController.do?getUsedRolesList&templateId=' + templateId,
			data: {
				'templateId': templateId
			},
			dataType: "json",
			success: function (data) {
				selects = data.obj;
				var url = encodeURI('generalSelectRoleController.do?goGetGeneralSelectRole&selectMode='
						+ selectMode + '&selects=' + selects);
				url = encodeURI(url);
				var dialogUrl = url;
				createDialog('openRole', dialogUrl);
			}
		});
	}

	function saveRoleP(iframe) {
		debugger;
		/* if(checkName())
		{	}
		else{} */

		var codes = [];
		var ids = '';
		var roleCodes = '';
		var roleNames = '';
		var selectRoles = iframe.getSelectRoles();
		var item = selectRoles.split(',');
		for (var i = 0; i < item.length; i++) {
			//var str;
			var roleItem = item[i];
			var singleRole = roleItem.split(':');
			if (i == item.length - 1) {
				codes.push(singleRole[1].split('-')[1]);
			} else {
				codes.push(singleRole[1].split('-')[1]);
			}
		}

		if (selectRoles != null && selectRoles.length > 0) {
			codes.join(",");
			doBatchAddRoles(codes);
			return true;
		}
		return false;
	}

	/**
	 *去除角色或人员
	 *
	 * @param 角色codes
	 */
	function removeRoleOrUser() {
		//判断是否从KDD跳入到IDS团队中的参数
		var tipDelType = '${param.kddProductTeamType}';
		//从kdd跳入到IDS中团队  判断是否有操作权限的参数
		var kddTeamOptPower = '${kddTeamOptPower}';

		var templateId = '${templateId}';
		var selectedId = mygrid_roleListTable.getSelectedRowId();
		if (selectedId != undefined && selectedId != null && selectedId != '') {
			var selectedIdArr = selectedId.split(",");
			if (selectedIdArr.length > 0) {
				var roles = [];
				top.Alert.confirm(
								'<spring:message code="com.glaway.ids.pm.project.projectmanager.role.confirmDel"/>',
								function(r) {
									if (r) {
										for (var i = 0; i < selectedIdArr.length; i++) {
											var rowId = selectedIdArr[i];
											var roleCode = mygrid_roleListTable.getRowAttribute(rowId,
															"roleCode");
												if (roleCode == "manager") {
													//改参数用于判断团退项目经理删除时候的提示语句问题 原因KDD中项目经理为总体设计师  用于判断是否要用WEBService去产品的最近访问
													//var tipDelType='${param.kddProductTeamType}';
													if (tipDelType != null
															&& tipDelType == "kddProduct") {
														tip('不能去除总体设计师角色');
													} else {
														tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.role.notDelManager"/>');
													}

													return;
												}
												roles.push(rowId);
										}
										$.ajax({
											type : "POST",
											url : 'projTemplateController.do?doBatchDelRole',
											data : {
												'roles' : roles
														.join(','),
												'templateId' : templateId,
											},
											dataType : "json",
											success : function(data) {
												tip(data.msg);
												$("#tmpId").val(templateId);
												$("#templateId").val(templateId);
												reload();
											}
										});

									}
								});

			} else {
				tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.role.selectRoleOrMember"/>');
				return;
			}
		} else {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.role.selectRoleOrMember"/>');
			return;
		}
	}

	function selectMemberType() {
		if ($('#memberType').combobox("getValue") == "1") {
			$('#searchMemberDiv').css('display', 'none');
			$('#searchDeptDiv').css('display', 'none');
			$('#searchGroupDiv').css('display', 'none');
		} else if ($('#memberType').combobox("getValue") == "2") {
			$('#searchMemberDiv').css('display', 'block');
			$('#searchDeptDiv').css('display', 'block');
			$('#searchGroupDiv').css('display', 'none');
		} else if ($('#memberType').combobox("getValue") == "3") {
			$('#searchMemberDiv').css('display', 'none');
			$('#searchDeptDiv').css('display', 'none');
			$('#searchGroupDiv').css('display', 'block');
		}
	}

	function searchRolesList() {
		var searchRole = $('#searchRole').textbox("getValue");
		var memberType = $('#memberType').combobox("getValue");
		var searchMember = $('#searchMember').textbox("getValue");
		var searchDept = $('#searchDept').textbox("getValue");
		var searchGroup = $('#searchGroup').textbox("getValue");

		$.ajax({
			url : 'projRolesController.do?searchTreegrid',
			type : 'post',
			data : {
				projectId : "${projectId}",
				searchRole : searchRole,
				memberType : memberType,
				searchMember : searchMember,
				searchDept : searchDept,
				searchGroup : searchGroup,
				teamId : "${teamId}"
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				mygrid_roleListTable.clearAll();
				mygrid_roleListTable.parse(d.obj, 'js');
			}
		});
	}

	//重置
	function tagSearchResetRolesList() {
		$('#searchRole').textbox("clear");
		$('#searchMember').textbox("clear");
		$('#searchDept').textbox("clear");
		$('#searchGroup').textbox("clear");
		$('#memberType').combobox("setValue", "1");
		$('#searchMemberDiv').css('display', 'none');
		$('#searchDeptDiv').css('display', 'none');
		$('#searchGroupDiv').css('display', 'none');
	}
</script>

<div class="easyui-layout" fit="true">
		<input type="hidden" id="templateId" value="${templateId }"/>
		<input type="hidden" id="step" name="step" value="3"/>
		<input type="hidden" id="teamId" />

		<fd:linkbutton id="addRole" onclick="openRole()"
			value="{com.glaway.ids.pm.project.projectmanager.role.addRole}"
			iconCls="basis ui-icon-plus" />
		<fd:linkbutton id="addUser" onclick="removeRoleOrUser('USER')"
			value="删除"
			iconCls="basis ui-icon-minus" />

		<%--<fd:lazyLoadingTreeGrid
			url="projTemplateController.do?getTeamList&templateId=${templateId}"
			id="roleListTable" width="100%" height="420px;"
			initWidths="820"
			columnIds="roleName"
			header="角色" imgUrl="plug-in/icons_greenfolders/"
			columnStyles="['font-weight: bold;']"
			colAlign="left" colSortings="na"
			colTypes="ro" enableTreeGridLines="true"
			enableLoadingStatus="false" enableMultiselect="true">
		</fd:lazyLoadingTreeGrid>--%>

		<fd:lazytreegrid id="roleListTable" idField="id" treeField="roleName" singleSelect="false"
						 url="projTemplateController.do?getTeamListFromSession&templateId=${templateId}"  style="width:100%;height:100%;" imgUrl="plug-in/dhtmlxSuite/imgs/">
			<fd:columns>
				<fd:column field="roleName" title="角色" />
			</fd:columns>
			<fd:eventListener event="onRightClick" listener="onClickAdd"></fd:eventListener>
		</fd:lazytreegrid>

</div>

<fd:dialog id="openRole" width="1040px" height="550px" modal="true"
	title="{com.glaway.ids.pm.project.projectmanager.role.selectRoles}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
		callback="saveRoleP"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
		callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="showGroupDetailDialog" width="800px" height="300px"
	modal="true"
	title="{com.glaway.ids.pm.project.projectmanager.role.viewMember}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.close}"
		callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
</body>
</html>