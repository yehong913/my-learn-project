<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<html>
<head>
	<t:base type="jquery,easyui,tools"></t:base>

	<script type="text/javascript">

		$(function () {
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

			if ('${addProjGroup}' == 'true' || '${addProjGroup}') {
				$('#addGroup').show();
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
				$('#addUser').hide();
				$('#addGroup').hide();
				$('#removeRoleOrUser').hide();
			}
		});

		function openRole() {
			selectRole();
		}

		/**
		 * 刷新页面
		 */
		function reload() {
			$.ajax({
				url : 'projRolesController.do?refreshTeamList',
				type : 'post',
				data : {
					projectId : "${projectId}",
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

		function reload2(expands) {
			$.ajax({
				url : 'projRolesController.do?refreshTeamList',
				type : 'post',
				data : {
					projectId : "${projectId}",
					teamId : "${teamId}"
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					mygrid_roleListTable.clearAll();
					mygrid_roleListTable.parse(d.obj, 'js');

					doExpandSelect(expands);
				}
			});
		}

		function doExpandSelect(expands) {
			addCountClass();
			$(".count_").each(
					function(index) {
						for ( var i in expands) {
							if (index == expands[i]) {
								this.parentNode.parentNode.parentNode.parentNode.parentNode.grid
										.doExpand(this);
							}
						}

					});
		}


		/**
		 * 批量增加角色
		 *
		 * @param 角色codes
		 */
		function doBatchAdd(roles) {
			var teamId = "${teamId}";
			//保存新增加的角色
			$.ajax({
				type : "POST",
				url : 'projRolesController.do?doBatchAdd',
				data : {
					'roles' : $.toJSON(roles),
					'teamId' : teamId
				},
				dataType : "json",
				success : function(data) {
					reload();
				}

			});
		}

		function doBatchAdd2(roles, expands) {
			var teamId = "${teamId}";
			//保存新增加的角色
			$.ajax({
				type : "POST",
				url : 'projRolesController.do?doBatchAdd',
				data : {
					'roles' : $.toJSON(roles),
					'teamId' : teamId
				},
				dataType : "json",
				success : function(data) {
					reload2(expands);
				}

			});
		}

		function addCountClass() {
			var reg = RegExp(/plus|minus/);
			$(".objbox img").each(function() {
				if ($(this).attr("src").match(reg)) {
					$(this).addClass("count_");
				}
			});

		}

		function doBatchAdd(roles) {
			var teamId = "${teamId}";
			var reg = RegExp(/plus|minus/);
			var reg2 = RegExp(/minus/);
			$(".objbox img").each(function() {
				if ($(this).attr("src").match(reg)) {
					$(this).addClass("count_");
				}
			});
			var countArray = new Array();
			var ix = 0;
			var inde = 0;

			$(".count_").each(function() {
				inde = inde + 1;
				if ($(this).attr("src").match(reg2)) {
					countArray[ix++] = inde;
				}
			});

			//保存新增加的角色
			$.ajax({
				type : "POST",
				url : 'projRolesController.do?doBatchAdd',
				data : {
					'roles' : $.toJSON(roles),
					'teamId' : teamId,
					'minusArrays' : $.toJSON(countArray)
				},
				dataType : "json",
				success : function(data) {
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

		function openUser2(type) {
			addCountClass();
			var expands = new Array();
			var ix = 0;
			var inde = 0;
			var reg2 = RegExp(/minus/);
			$(".count_").each(function() {
				inde = inde + 1;
				if ($(this).attr("src").match(reg2)) {
					expands[ix++] = inde - 1;
				}
			});
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
						selectGroup2(roleCode,expands);
					} else {
						var ss = rowId.split("-");
						if (ss[1] != null) {
							tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.role.selectOne"/>');
							return;
						}
						selectUser2(roleCode, rowId,expands);
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
		 成员的操作
		 */
		var id;
		function selectUser(code, selectCode) {
			var projectId = '${projectId}';
			var teamId = "${teamId}";
			var selects = "";
			var selectMode = "multi";

			$.ajax({
				type : "POST",
				url : 'projRolesController.do?getUserList&roleCode=' + code
						+ "&teamId=" + teamId + 'projectId=' + projectId,
				data : {
					'projectId' : projectId,
					'teamId' : teamId,
					'roleCode' : code
				},
				dataType : "json",
				success : function(data) {
					selects = data.obj;
					var url = encodeURI('url:generalSelectUserController.do?goGetGeneralSelectUser&selectMode='
							+ selectMode + '&roleCode=' + selectCode);
					url = encodeURI(url);
					$("#selectUserGroup").lhgdialog(
							{
								title : '<spring:message code="com.glaway.ids.pm.project.projectmanager.role.selectUsers"/>',
								content : url,
								icon : 'succeed',
								height : 500,
								width : 1040,
								data : {
									'selects' : selects,
									'mode' : '121',
									'selectCode' : selectCode
								},
								button : [
									{
										name : "确定",
										callback : function() {
											iframe = this.iframe.contentWindow;
											var ids = '';
											var showNames = '';
											var userNames = '';
											var realNames = '';
											var selectUsers = iframe
													.getSelectUsers();
											if (selectUsers) {
												var item = selectUsers
														.split(',');
												for (var i = 0; i < item.length; i++) {
													var userItem = item[i];
													var singleUser = userItem
															.split(':');
													if (i == item.length - 1) {
														ids = ids
																+ singleUser[0];
														showNames = showNames
																+ singleUser[1];
														userNames = userNames
																+ singleUser[2];
													} else {
														ids = ids
																+ singleUser[0]
																+ ',';
														showNames = showNames
																+ singleUser[1]
																+ ',';
														userNames = userNames
																+ singleUser[2]
																+ ',';
													}
												}
											} else {
												return true;
											}

											if (selectUsers != null
													&& selectUsers.length > 0) {
												$
														.ajax({
															type : "POST",
															url : 'projRoleUsersController.do?doBatchAdd',
															data : {
																'selectUser' : ids,
																'projectId' : projectId,
																'roleCode' : code
															},
															dataType : "json",
															success : function(
																	data) {
																reload();
															}
														});
												return true;
											}
											return false;
										}
									}, {
										name : "取消"
									} ]
							})
				}
			});
		}

		function selectUser2(code, selectCode,expands) {
			debugger;
			var projectId = '${projectId}';
			var teamId = "${teamId}";
			var selects = "";
			var selectMode = "multi";

			$.ajax({
				type : "POST",
				url : 'projRolesController.do?getUserList&roleCode=' + code
						+ "&teamId=" + teamId + 'projectId=' + projectId,
				data : {
					'projectId' : projectId,
					'teamId' : teamId,
					'roleCode' : code
				},
				dataType : "json",
				success : function(data) {
					selects = data.obj;
					var url = encodeURI('generalSelectUserController.do?goGetGeneralSelectUser&selectMode='
							+ selectMode + '&roleCode=' + selectCode + '&selects=' + selects + '&mode=121&selectCode' +selectCode);
					url = encodeURI(url);
					createDialog('selectUserDialog', url);
				}
			});

		}
		/**
		 *组的操作
		 */
		function selectGroup(code) {
			var projectId = '${projectId}';
			var teamId = "${teamId}";
			var selects = "";
			var selectMode = 'multi';

			$.ajax({
				type : "POST",
				url : 'projRolesController.do?getGroupList&roleCode='
						+ code + "&teamId=" + teamId + 'projectId='
						+ projectId,
				data : {
					'projectId' : projectId,
					'teamId' : teamId,
					'roleCode' : code
				},
				dataType : "json",
				success : function(data) {

					selects = data.obj;

					var url = encodeURI('url:generalSelectGroupController.do?goGetGeneralSelectGroup&type=selectGroup&selectMode='
							+ selectMode + '&selects=' + selects);
					url = encodeURI(url);
					$("#selectUserGroup")
							.lhgdialog(
									{
										title : '<spring:message code="com.glaway.ids.pm.project.projectmanager.role.selectGruops"/>',
										content : url,
										icon : 'succeed',
										height : 550,
										width : 1040,
										data : {
											'selectMode' : selectMode
										},
										button : [
											{
												name : "确定",
												callback : function() {
													iframe = this.iframe.contentWindow;
													var ids = '';
													var groupCodes = '';
													var groupNames = '';
													var selectGroups = iframe
															.getSelectGroups();
													var item = selectGroups
															.split(',');
													for (var i = 0; i < item.length; i++) {
														//var str;
														var roleItem = item[i];
														var singleGroup = roleItem
																.split(':');
														if (i == item.length - 1) {
															ids = ids
																	+ singleGroup[0];
															groupCodes = groupCodes
																	+ singleGroup[2];
															groupNames = groupNames
																	+ singleGroup[1];
														} else {
															ids = ids
																	+ singleGroup[0]
																	+ ',';
															groupCodes = groupCodes
																	+ singleGroup[2]
																	+ ',';
															groupNames = groupNames
																	+ singleGroup[1]
																	+ ',';
														}
													}

													if (selectGroups != null
															&& selectGroups.length > 0) {
														$
																.ajax({
																	type : "POST",
																	url : 'projRoleUsersController.do?doBatchAddGroup01',
																	data : {
																		'ids' : ids,
																		'groupCodes' : groupCodes,
																		'groupNames' : groupNames,
																		'projectId' : projectId,
																		'roleCode' : code
																	},
																	dataType : "json",
																	success : function(
																			data) {
																		reload();
																	}
																});
														return true;
													}
													return false;
												}
											}, {
												name : "取消"
											} ]
									})

				}
			});
		}

		function selectGroup2(code,expands) {
			var projectId = '${projectId}';
			var teamId = "${teamId}";
			var selects = "";
			var selectMode = 'multi';

			$.ajax({
				type : "POST",
				url : 'projRolesController.do?getGroupList&roleCode='
						+ code + "&teamId=" + teamId + 'projectId='
						+ projectId,
				data : {
					'projectId' : projectId,
					'teamId' : teamId,
					'roleCode' : code
				},
				dataType : "json",
				success : function(data) {
					selects = data.obj;
					if (selects == null){
						var url = encodeURI('generalSelectGroupController.do?goGetGeneralSelectGroup&type=selectGroup&selectMode='
								+ selectMode);
					} else {
						var url = encodeURI('generalSelectGroupController.do?goGetGeneralSelectGroup&type=selectGroup&selectMode='
								+ selectMode + '&selects=' + selects + '&selectMode='+selectMode);
					}
					createDialog('selectGroupDialog', url);

				}
			});

		}
		/**
		 *角色的操作
		 */
		function selectRole() {
			var projectId = '${projectId}';
			var teamId = "${teamId}";
			var selects = "";
			var selectMode = 'multi';

			$.ajax({
				type : "POST",
				url : 'projRolesController.do?getUsedRolesList&teamId='
						+ teamId + '&projectId=' + projectId,
				data : {
					'projectId' : projectId,
					'teamId' : teamId
				},
				dataType : "json",
				success : function(data) {
					selects = data.obj;
					var url = encodeURI('generalSelectRoleController.do?goGetGeneralSelectRole&selectMode='
							+ selectMode + '&selects=' + selects);
					url = encodeURI(url);
					var dialogUrl = url;
					createDialog('openRole', dialogUrl);
				}
			});
		}

		function saveRoleP2(iframe) {
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
				doBatchAdd(codes);
				return true;
			}
			return false;
		}

		function saveRoleP(iframe) {
			//可以展开的 节点添加 count_类属性
			addCountClass();
			// 统计已经展开节点的索引
			var expands = new Array();
			var ix = 0;
			var inde = 0;
			var reg2 = RegExp(/minus/);
			$(".count_").each(function() {
				inde = inde + 1;
				if ($(this).attr("src").match(reg2)) {
					expands[ix++] = inde - 1;
				}
			});

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
				doBatchAdd2(codes, expands);
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

			debugger
			var projectId = '${projectId}';
			var selectedId = mygrid_roleListTable.getSelectedRowId();
			if (selectedId != undefined && selectedId != null && selectedId != '') {
				var selectedIdArr = selectedId.split(",");
				if (selectedIdArr.length > 0) {
					var roles = [];
					var users = [];
					var groups = [];
					top.Alert.confirm(
							'<spring:message code="com.glaway.ids.pm.project.projectmanager.role.confirmDel"/>',
							function(r) {
								if (r) {
									for (var i = 0; i < selectedIdArr.length; i++) {
										var rowId = selectedIdArr[i];
										var type = mygrid_roleListTable
												.getRowAttribute(rowId,
														"type");
										var roleCode = mygrid_roleListTable
												.getRowAttribute(rowId,
														"roleCode");
										if (type == "USER") {
											users.push(rowId);
										}
										if (type == "ROLE") {
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
										if (type == "GROUP") {
											groups.push(rowId);
										}
									}
									//var delTeamUserType='${param.kddProductTeamType}';
									$.ajax({
										type : "POST",
										url : 'projRolesController.do?doBatchDelRoleAndUsers',
										data : {
											'users' : users
													.join(','),
											'roles' : roles
													.join(','),
											'groups' : groups
													.join(','),
											'projectId' : projectId,
											"kddProductTeamType" : tipDelType
										},
										dataType : "json",
										success : function(data) {
											debugger;
											tip(data.msg);
									//		var d = $.parseJSON(data);
											if (data.success) {
												if(users.length > 0){
													for(var u = 0;u<users.length;u++){
														//reloadAfterDelete();
														mygrid_roleListTable.deleteRow(users[u]);
													}
												}

												if(roles.length > 0){
													for(var r = 0;r<roles.length;r++){
														mygrid_roleListTable.deleteRow(roles[r]);
													}
												}

												if(groups.length > 0){
													for(var g = 0;g<groups.length;g++){
														mygrid_roleListTable.deleteRow(groups[g]);
													}
												}


												//从KDD到IDS团队页面  团队列表修改后刷新KDD左侧项目树
												if (tipDelType != null
														&& tipDelType == "kddProduct") {
													parent
															.loadProductTree(
																	"",
																	1);
												}
											}
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

		function showGroup(id) {
			url = 'projRolesController.do?goGroupUserList&groupId=' + id;
			createDialog('showGroupDetailDialog', url);
		}

		//保存角色
		function saveUser(iframe) {
			addCountClass();
			var expands = new Array();
			var ix = 0;
			var inde = 0;
			var reg2 = RegExp(/minus/);
			$(".count_").each(function() {
				inde = inde + 1;
				if ($(this).attr("src").match(reg2)) {
					expands[ix++] = inde - 1;
				}
			});
			var selectedId = mygrid_roleListTable.getSelectedRowId();
			if (selectedId != undefined && selectedId != null && selectedId != '') {
				var selectedIdArr = selectedId.split(",");
				if (selectedIdArr.length == 1) {
					var rowId = selectedIdArr[0];
					var code = mygrid_roleListTable.getRowAttribute(rowId, "roleCode");
					var projectId = '${projectId}';
					var expandss=expands;
					var ids = '';
					var showNames = '';
					var userNames = '';
					var realNames = '';
					var selectUsers = iframe.getSelectUsers();
					if (selectUsers) {
						var item = selectUsers.split(',');
						for (var i = 0; i < item.length; i++) {
							var userItem = item[i];
							var singleUser = userItem
									.split(':');
							if (i == item.length - 1) {
								ids = ids
										+ singleUser[0];
								showNames = showNames
										+ singleUser[1];
								userNames = userNames
										+ singleUser[2];
							} else {
								ids = ids
										+ singleUser[0]
										+ ',';
								showNames = showNames
										+ singleUser[1]
										+ ',';
								userNames = userNames
										+ singleUser[2]
										+ ',';
							}
						}
					} else {
						return true;
					}

					if (selectUsers != null
							&& selectUsers.length > 0) {
						$.ajax({
							type : "POST",
							url : 'projRoleUsersController.do?doBatchAdd',
							data : {
								'selectUser' : ids,
								'projectId' : projectId,
								'roleCode' : code
							},
							dataType : "json",
							success : function(
									data) {
								reload2(expands);
							}
						});

						return true;
					}
					return false;
				}
			}
		}

		//保存群组
		function saveGroup(iframe) {
			addCountClass();
			var expands = new Array();
			var ix = 0;
			var inde = 0;
			var reg2 = RegExp(/minus/);
			$(".count_").each(function() {
				inde = inde + 1;
				if ($(this).attr("src").match(reg2)) {
					expands[ix++] = inde - 1;
				}
			});
			var selectedId = mygrid_roleListTable.getSelectedRowId();
			if (selectedId != undefined && selectedId != null && selectedId != '') {
				var selectedIdArr = selectedId.split(",");
				if (selectedIdArr.length == 1) {
					var rowId = selectedIdArr[0];
					var code = mygrid_roleListTable.getRowAttribute(rowId, "roleCode");
					var projectId = '${projectId}';
					var ex=expands;
					var ids = '';
					var groupCodes = '';
					var groupNames = '';
					var selectGroups = iframe
							.getSelectGroups();
					var item = selectGroups
							.split(',');
					for (var i = 0; i < item.length; i++) {
						//var str;
						var roleItem = item[i];
						var singleGroup = roleItem
								.split(':');
						if (i == item.length - 1) {
							ids = ids
									+ singleGroup[0];
							groupCodes = groupCodes
									+ singleGroup[2];
							groupNames = groupNames
									+ singleGroup[1];
						} else {
							ids = ids
									+ singleGroup[0]
									+ ',';
							groupCodes = groupCodes
									+ singleGroup[2]
									+ ',';
							groupNames = groupNames
									+ singleGroup[1]
									+ ',';
						}
					}

					if (selectGroups != null
							&& selectGroups.length > 0) {
						$.ajax({
							type : "POST",
							url : 'projRoleUsersController.do?doBatchAddGroup01',
							data : {
								'ids' : ids,
								'groupCodes' : groupCodes,
								'groupNames' : groupNames,
								'projectId' : projectId,
								'roleCode' : code
							},
							dataType : "json",
							success : function(data) {
								reload2(expands);
							}
						});
						return true;
					}
					return false;
				}
			}
		}
	</script>
</head>
<body>
	<input type="hidden" id="projectId" />
	<input type="hidden" id="teamId" />
	<fd:searchform id="seachRolesListTag"
				   onClickSearchBtn="searchRolesList()"
				   onClickResetBtn="tagSearchResetRolesList()" isMoreShow="false" help="helpDoc:BuildProjectTeam">
		<fd:inputText
				title="{com.glaway.ids.pm.project.projectmanager.role.role}"
				name="searchRole" id="searchRole" maxLength="-1" />
		<fd:combobox name="memberType" id="memberType"
					 title="{com.glaway.ids.pm.project.projectmanager.role.memberType}"
					 editable="false" textField="text" onSelect="selectMemberType()"
					 valueField="id" data="1_全部_checked,2_成员,3_组" />
		<div id="searchMemberDiv" style="display: none">
			<fd:inputText
					title="{com.glaway.ids.pm.project.projectmanager.role.member}"
					name="searchMember" id="searchMember" maxLength="-1" />
		</div>
		<div id="searchDeptDiv" style="display: none">
			<fd:inputText
					title="{com.glaway.ids.pm.project.projectmanager.role.dept}"
					name="searchDept" id="searchDept" maxLength="-1" />
		</div>
		<div id="searchGroupDiv" style="display: none">
			<fd:inputText
					title="{com.glaway.ids.pm.project.projectmanager.role.userGroup}"
					name="searchGroup" id="searchGroup" maxLength="-1" />
		</div>
	</fd:searchform>

	<fd:toolbar id="roleTb">
		<fd:toolbarGroup align="left">
			<c:if test="${isViewPage!=1}">
				<fd:linkbutton id="addRole" onclick="openRole()"
							   value="{com.glaway.ids.pm.project.projectmanager.role.addRole}"
							   iconCls="basis ui-icon-plus" />
				<fd:linkbutton id="addUser" onclick="openUser2('USER')"
							   value="{com.glaway.ids.pm.project.projectmanager.role.addMember}"
							   iconCls="basis ui-icon-plus" />
				<fd:linkbutton id="addGroup" onclick="openUser2('GROUP')"
							   value="{com.glaway.ids.pm.project.projectmanager.role.addGruop}"
							   iconCls="basis ui-icon-plus" />
				<fd:linkbutton id="removeRoleOrUser" onclick="removeRoleOrUser()"
							   value="{com.glaway.ids.common.btn.remove}"
							   iconCls="basis ui-icon-minus" />
			</c:if>
		</fd:toolbarGroup>
	</fd:toolbar>
		<%--<fd:lazyLoadingTreeGrid
		url="projRolesController.do?getTeamList&projectId=${projectId}"
		id="roleListTable" width="99%" height="420px;"
		initWidths="*,*,*,*,*"
		columnIds="roleName,memberDisplayName,dept,email,showStatus"
		header="角色,成员,部门,邮箱,状态" imgUrl="plug-in/dhtmlxSuite/imgs/"
		columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;']"
		colAlign="left,left,left,left,left" colSortings="na,na,na,na,na"
		colTypes="tree,ro,ro,ro,ro" enableTreeGridLines="true"
		enableLoadingStatus="false" enableMultiselect="true">
		</fd:lazyLoadingTreeGrid>--%>
	<div id="lazytreegridDiv" style="overflow-y: auto">
		<fd:lazytreegrid id="roleListTable" idField="id" treeField="roleName" url="projRolesController.do?getTeamList&projectId=${projectId}"
						 style="width:99%;height:100%;" imgUrl="plug-in/dhtmlxSuite/imgs/" lazyUrl="projRolesController.do?getTeamList&projectId=${projectId}">
			<fd:columns>
				<fd:column field="roleName" title="角色"></fd:column>
				<fd:column field="memberDisplayName" title="成员 "></fd:column>
				<fd:column field="dept" title="部门"></fd:column>
				<fd:column field="email" title="邮箱"></fd:column>
				<fd:column field="showStatus" title="状态"></fd:column>
			</fd:columns>
		</fd:lazytreegrid>
	</div>
	<fd:dialog id="openRole" width="1040px" height="550px" modal="true"
			   title="{com.glaway.ids.pm.project.projectmanager.role.selectRoles}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
						 callback="saveRoleP"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
						 callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="selectGroupDialog" width="1040px" height="550px" modal="true"
			   title="{com.glaway.ids.pm.project.projectmanager.role.selectGruops}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
						 callback="saveGroup"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
						 callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="selectUserDialog" width="1040px" height="550px" modal="true"
			   title="{com.glaway.ids.pm.project.projectmanager.role.selectUsers}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
						 callback="saveUser"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
						 callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>

	<fd:dialog id="showGroupDetailDialog" width="800px" height="300px"
			   modal="true"
			   title="{com.glaway.ids.pm.project.projectmanager.role.viewMember}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}"
						 callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>

<script type="text/javascript">
	$(function () {
		changeHeight();
	});

	//窗口大小改变时，执行
	$(window).resize(function () {
		changeHeight();
	});

	function changeHeight() {
		debugger;
		var bodyHeight = window.outerHeight;
		var formh = document.getElementById("seachRolesListTag").offsetHeight;
		var toolbarh = document.getElementById("roleTb").offsetHeight;
		var divHight = bodyHeight - formh - toolbarh - 300;
		document.getElementById("lazytreegridDiv").style.height = divHight + "px";
	}

</script>
</body>
</html>

