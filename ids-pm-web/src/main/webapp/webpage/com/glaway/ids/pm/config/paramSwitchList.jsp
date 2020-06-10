<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<style>
#params .datagrid-header-row {
	display: none;
}
</style>
<div class="easyui-layout" fit="true">
	<div id="switchtool" region="center" style="padding: 1px;">
		<fd:toolbar help="helpDoc:Projectparameter">
			<fd:toolbarGroup align="left">
				<fd:linkbutton onclick="saveParamSwitch('paramSwitchController.do?doSave')"
					value="{com.glaway.ids.common.btn.save}" iconCls="basis ui-icon-save" operationCode="paramSwitchSave"
					id="paramSaveButton" />
			</fd:toolbarGroup>
		</fd:toolbar>
	</div>

	<div id='params'>
		<fd:datagrid checkbox="false" checked="false" checkOnSelect="false"
			idField="id" toolbar="#switchtool" id="paramSwitchList" fit="false"
			pagination="false" rownumbers="false">
			<fd:dgCol title="" field="name" width="180" />
			<fd:dgCol title="" field="status" formatterFunName="initAllData" width="500" />
		</fd:datagrid>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		reloadTable();
	});

	function reloadTable() {
		$('#paramSwitchList').datagrid({
			url : 'paramSwitchController.do?datagridlist&field=id'
		});
	}

	function initAllData(value, row, index) {
		var btn = $('#paramSaveButton');
		var shtml;
		if (btn.length == 1) {
			if (row.switchType == 'RADIO') {
				if (value == '<spring:message code="com.glaway.ids.pm.config.true"/>') {
					shtml = '<input type="radio" name="status'
							+ index
							+ '" value="是" checked="checked" onclick="changeStatus('
							+ index + ',\'是\',\'RADIO\',' + i
							+ ')">是<input type="radio" name="status' + index
							+ '" value="否" onclick="changeStatus(' + index
							+ ',\'否\',\'RADIO\',' + i + ')">否';
				} else {
					shtml = '<input type="radio" name="status'
							+ index
							+ '" value="是" onclick="changeStatus('
							+ index
							+ ',\'是\',\'RADIO\','
							+ i
							+ ')">是<input type="radio" name="status'
							+ index
							+ '" value="否" checked="checked" onclick="changeStatus('
							+ index + ',\'否\',\'RADIO\',' + i + ')">否';
				}
			} else if (row.switchType == 'TEXT') {
				shtml = '<input id="status'
						+ index
						+ '" name="status'
						+ index
						+ '" type="text" style="width: 80px" class="inputxt" value="'
						+ row.status + '" onchange="changeStatus(' + index
						+ ',' + row.status + ',\'TEXT\',' + i + ')">天';
			} else if (row.switchType == 'CHECKBOX') {
				shtml = '';

				var statusList = row.statusList;
				var status = row.status;
				if (status == undefined) {
					status = '';
					row.status = '';
				}

				var statusValue = status.split('+');
				var boxValue = statusList.split(',');

				if (row.name == '活动名称库') {
					var last = '';
					for (var i = 0; i < boxValue.length; i++) {
						var flag = 0;
						for (var j = 0; j < statusValue.length; j++) {
							if (statusValue[j] == boxValue[i]) {
								flag = 1;
								last = last + i;
								shtml = shtml
										+ '<input type="checkbox" checked="checked" id="status'
										+ index + i + '" name="status' + index
										+ '" value="' + boxValue[i]
										+ '" onclick="levelCheckboxChange('
										+ index + ',\'' + boxValue[i]
										+ '\',\'CHECKBOX\',' + i + ')">'
										+ boxValue[i] + '</br>';
							}
						}
						if (flag == 0) {
							if (i == 0) {
								shtml = shtml
										+ '<input type="checkbox" id="status'
										+ index + i + '" name="status' + index
										+ '" value="' + boxValue[i]
										+ '" onclick="levelCheckboxChange('
										+ index + ',\'' + boxValue[i]
										+ '\',\'CHECKBOX\',' + i + ')">'
										+ boxValue[i] + '</br>';
							} else {
								if (last.indexOf((i - 1)) != -1) {
									shtml = shtml
											+ '<input type="checkbox" id="status'
											+ index + i + '" name="status'
											+ index + '" value="' + boxValue[i]
											+ '" onclick="levelCheckboxChange('
											+ index + ',\'' + boxValue[i]
											+ '\',\'CHECKBOX\',' + i + ')">'
											+ boxValue[i] + '</br>';
								} else {
									shtml = shtml
											+ '<input type="checkbox" disabled="disabled" id="status'
											+ index + i + '" name="status'
											+ index + '" value="' + boxValue[i]
											+ '" onclick="levelCheckboxChange('
											+ index + ',\'' + boxValue[i]
											+ '\',\'CHECKBOX\',' + i + ')">'
											+ boxValue[i] + '</br>';
								}
							}
						}
					}
				} else if (row.name == '关键计划对应计划等级'){
					if (boxValue.length > 0) {
						for (var i = 0; i < boxValue.length; i++) {
							var flag = 0;
							for (var j = 0; j < statusValue.length; j++) {
								if (statusValue[j] == boxValue[i]) {
									flag = 1;
									shtml = shtml
											+ '<input type="checkbox" checked="checked" id="status'+index+i+'" name="status'+index+'" value="'+boxValue[i]+'">'
											+ boxValue[i] + '</br>';
								}
							}
							if (flag == 0) {
								shtml = shtml
										+ '<input type="checkbox" id="status'+index+i+'" name="status'+index+'" value="'+boxValue[i]+'">'
										+ boxValue[i] + '</br>';
							}
						}
					}
				} else {
					for (var i = 0; i < boxValue.length; i++) {
						var flag = 0;
						for (var j = 0; j < statusValue.length; j++) {
							if (statusValue[j] == boxValue[i]) {
								flag = 1;
								shtml = shtml
										+ '<input type="checkbox" checked="checked" id="status'+index+i+'" name="status'+index+'" value="'+boxValue[i]+'">'
										+ boxValue[i] + '</br>';
							}
						}
						if (flag == 0) {
							shtml = shtml
									+ '<input type="checkbox" id="status'+index+i+'" name="status'+index+'" value="'+boxValue[i]+'">'
									+ boxValue[i] + '</br>';
						}
					}
				}
			}
		} else {
			if (row.switchType == 'TEXT') {
				shtml = value + '天';
			} else if (row.switchType == 'COMBOBOX') {

			} else {
				shtml = value;
			}
		}

		return shtml;
	}

	function saveParamSwitch(url) {
		var statusString = '';
		var all = $("#paramSwitchList").datagrid('getRows');
		for (var i = 0; i < all.length; i++) {
			if (all[i].switchType == 'TEXT') {
				if (all[i].status.replace(/(^\s*)|(\s*$)/g, "") == "") {
					tip('<spring:message code="com.glaway.ids.pm.config.paramSwitch.mustNumber" arguments="' + all[i].name + '"/>');
					return;
				} else {
					var testStr = all[i].status;
					if (all[i].status.indexOf('-') != -1) {
						testStr = all[i].status.replace('-', '');
					}

					var re = /^[0-9]+[0-9]*]*$/;
					if (!re.test(testStr)) {
						tip('<spring:message code="com.glaway.ids.pm.config.paramSwitch.mustInteger" arguments="' + all[i].name + '"/>');
						return false;
					}
				}
			}
			if (all[i].switchType == 'CHECKBOX') {
				var destArray = new Array();
				var cid = 'status' + i;
				var checkbox = $('input[id^=' + cid + ']');
				for (var f = 0; f < checkbox.length; f++) {
					if ($(checkbox[f]).is(':checked')) {
						destArray.push($(checkbox[f]).val());
					}
				}

				all[i].status = destArray.join('+');
			}

			if (i == (all.length - 1)) {
				statusString = statusString + all[i].id + ',' + all[i].status;
			} else {
				statusString = statusString + all[i].id + ',' + all[i].status + '#';
			}
		}

		top.Alert.confirm(
			'<spring:message code="com.glaway.ids.pm.config.paramSwitch.confirmSave"/>',
			function(r) {
				if (r) {
					$.ajax({
						url : url,
						type : 'post',
						data : {
							statusString : statusString
						},
						cache : false,
						success : function(data) {
							var d = $.parseJSON(data);
							if (d.success) {
								var msg = d.msg;
								tip(msg);
								window.setTimeout("reloadTable()", 500); // 等待0.5秒，让分类树结构加载出来
								statusString = '';
							}
						}
					});
				}
			});

	}

	function changeStatus(index, status, type, id) {
		if (type == 'TEXT') {
			status = $('#status' + index).val();
		}
		var all = $("#paramSwitchList").datagrid('getRows');
		for (var i = 0; i < all.length; i++) {
			var inx = $("#paramSwitchList").datagrid('getRowIndex', all[i]);
			if (inx == index) {
				all[i].status = status;
			}
		}
	}

	function levelCheckboxChange(index, status, type, id) {
		var boxId = 'status' + index + '' + id;
		var nextId = 'status' + index + '' + (id + 1);
		var all = $("#paramSwitchList").datagrid('getRows');
		for (var i = 0; i < all.length; i++) {
			var inx = $("#paramSwitchList").datagrid('getRowIndex', all[i]);
			if (inx == index) {
				var box = $("#" + boxId);
				if (box.is(':checked')) {
					$("#" + nextId).removeAttr('disabled');
				} else {
					var nextall = box.nextAll();
					for (var k = 0; k < nextall.length; k++) {
						if ($(nextall[k]).is(':checked')) {
							$(nextall[k]).click();
						}
						$(nextall[k]).attr('disabled', 'disabled');
					}
				}

			}
		}
	}
</script>