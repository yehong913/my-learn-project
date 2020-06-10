<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,easyui,tools"></t:base>

<style>
	.disable_backcolor {
		color: #AAAAB0 !important;
	}
	
	.datagrid-body table{
		width:100%
	}
</style>

<script type="text/javascript">

	$(function() {

		$('#deliver_list_select_to')
				.datalist(
						{
							url : 'taskFlowResolveController.do?doChangeCellRelationSearch&side=right&parentPlanId=${parentPlanId}&fromCellId=${fromCellId}&toCellId=${toCellId}',
							//title : '输出交付项',
							textField : 'name',
							valueField : 'name',
							idField : 'id',
							checkbox : false,
							lines : true,
							singleSelect : true,
							onDblClickRow : function(index, row) {
								right_to_left(index, row);
							}
						});

		$('#deliver_list_select_from')
				.datalist(
						{
							url : 'taskFlowResolveController.do?doChangeCellRelationSearch&side=left&parentPlanId=${parentPlanId}&fromCellId=${fromCellId}&toCellId=${toCellId}',
							//title : '输入交付项',
							textField : 'name',
							valueField : 'name',
							idField : 'id',
							checkbox : false,
							lines : true,
							singleSelect : true,
							onDblClickRow : function(index, row) {
								if ('unchecked' == row.checked) {
									left_to_right(row);
								}
								$('#deliver_list_select_from').datalist(
										'clearChecked');
							},
							onLoadSuccess : function(data) {
								var rowsSrc = data.rows;
								checkList(rowsSrc);
							}
						});

		$('#left_to_right').click(function() {
			var rows = $('#deliver_list_select_from').datalist('getChecked');
			for (var i = 0; i < rows.length; i++) {
				var row = rows[i];
				if ('unchecked' == row.checked) {
					left_to_right(row);
				}
			}
			$('#deliver_list_select_from').datalist('clearChecked');
		});

		$('#right_to_left').click(
			function() {
				var rows = $('#deliver_list_select_to').datalist(
						'getChecked');
				for (var i = rows.length - 1; i >= 0; i--) {
					var row = rows[i];
					var index = $('#deliver_list_select_to').datalist(
							'getRowIndex', row);
					right_to_left(index, row);
				}
		});

	});

	function checkList(rowsSrc) {
		for (var i = 0; i < rowsSrc.length; i++) {
			var rowSrc = rowsSrc[i];
			var idSrc = rowSrc.name;
			var rows = $('#deliver_list_select_to').datalist('getRows');
			for (var j = 0; j < rows.length; j++) {
				var row = rows[j];
				var id = row.name;
				if (idSrc == id) {
					var index = $('#deliver_list_select_from').datalist(
							'getRowIndex', rowSrc);
					if (-1 != index) {
						$('#deliver_list_select_from').datalist('updateRow', {
							index : index,
							row : {
								name : rowSrc.name,
								id : rowSrc.id,
								checked : 'checked'
							}
						});
						$('#deliver_list_select_from').parent().find(
								'div[class=datagrid-body]').find(
								'tr[datagrid-row-index=' + index + ']')
								.addClass('disable_backcolor');
					}
					break;
				}
			}
		}
	}

	var toRows = $('#deliver_list_select_to').datalist('getRows');

	function left_to_right(row) {
		$('#deliver_list_select_to').datalist('appendRow', {
			id : row.id,
			name : row.name,
			useObjectId : row.useObjectId,
			useObjectName : row.useObjectName
		});

		var index2 = $('#deliver_list_select_from')
				.datalist('getRowIndex', row);
		if (-1 != index2) {
			$('#deliver_list_select_from').datalist('updateRow', {
				index : index2,
				row : {
					id : row.id,
					name : row.name,
					checked : 'checked'
				}
			});

			var trObj = $('#deliver_list_select_from').parent().find(
					'div[class=datagrid-body]').find(
					'tr[datagrid-row-index=' + index2 + ']');
			$(trObj).addClass('disable_backcolor');
		}

	}

	function right_to_left(index, row) {
		var name = row.name;
		var rows = $('#deliver_list_select_from').datalist('getRows');
		for (var i = 0; i < rows.length; i++) {
			var rowTemp = rows[i];
			if (name == rowTemp.name) {
				var index2 = $('#deliver_list_select_from').datalist(
						'getRowIndex', rowTemp);
				if (-1 != index2) {
					$('#deliver_list_select_from').datalist('updateRow', {
						index : index2,
						row : {
							name : row.name,
							id : row.id,
							checked : 'unchecked'
						}
					});
					$('#deliver_list_select_from').parent().find(
							'div[class=datagrid-body]').find(
							'tr[datagrid-row-index=' + index2 + ']')
							.removeClass('disable_backcolor');
					break;
				}
			}
		}

		$('#deliver_list_select_to').datalist('deleteRow', index);
	}

	//确认选择并保存
	function confirmSelections() {
		var rows = $('#deliver_list_select_to').datalist('getRows');
			    
		var originObjectIds = [];
		var originObjectNames = [];
		var originDeliverablesInfoIds = [];
		var originDeliverablesInfoNames = [];
		var names = [];
		var ids = [];
		for (var i = 0; i < rows.length; i++) {
			names.push(rows[i].name);
			ids.push(rows[i].id);
			originObjectIds.push(rows[i].useObjectId);
			originObjectNames.push(rows[i].useObjectName);
			originDeliverablesInfoIds.push(rows[i].id);
			originDeliverablesInfoNames.push(rows[i].name);
		}
		$.post(
				"${pageContext.request.contextPath }/taskFlowResolveController.do?doChangeCellRelationChange",
				{
					'names' : names.join(','),
					'ids' : ids.join(','),
					'originObjectIds' : originObjectIds.join(','),
					'originObjectNames' : originObjectNames.join(','),
					'originDeliverablesInfoIds' : originDeliverablesInfoIds.join(','),
					'originDeliverablesInfoNames' : originDeliverablesInfoNames.join(','),
					'parentPlanId' : '${parentPlanId}',
					'fromCellId' : '${fromCellId}',
					'toCellId' : '${toCellId}'
				}, function(data) {
					var result = $.parseJSON(data);
					if (result.success) {
						var win = $.fn.lhgdialog("getSelectParentWin");
						win.cleanSelectCells();
						$.fn.lhgdialog("closeSelect");
					}
				});
	}
	
	function closeThisDialog() {
		$.fn.lhgdialog("closeSelect");
	}
	</script>
	</head>
<body>
<div class="easyui-layout" fit="true">
	<div region="west" style="padding: 1px; width: 48%;" collapsible="false"
		title="<spring:message code='com.glaway.ids.pm.project.task.tobeSelect'/>" >
		<ul id="deliver_list_select_from" lines="true" fit="true"></ul>
	</div>
	<div region="center" style="padding: 1px; width: 4%; text-align: center;" title="">
		<div style="height: 45%;"></div>
		<div>
			<a href="javascript:void(0)" class="l-btn l-btn-small l-btn-plain" group="" id="right_to_left"> 
				<span class="l-btn-left l-btn-icon-left"> 
					<span class="l-btn-text l-btn-empty">&nbsp;</span>
					<span class="l-btn-icon pagination-prev">&nbsp;</span>
				</span>
			</a>
		</div>
		<div>
			<a href="javascript:void(0)" class="l-btn l-btn-small l-btn-plain" group="" id="left_to_right"> 
				<span class="l-btn-left l-btn-icon-left">
					<span class="l-btn-text l-btn-empty">&nbsp;</span>
					<span class="l-btn-icon pagination-next">&nbsp;</span>
				</span>
			</a>
		</div>
	</div>
	<div region="east" style="padding: 1px; width: 48%;" collapsible="false"
		title="<spring:message code='com.glaway.ids.pm.project.task.doneSelect'/>">
		<ul id="deliver_list_select_to" lines="true" fit="true"></ul>
	</div>
</div>
</body>
</html>