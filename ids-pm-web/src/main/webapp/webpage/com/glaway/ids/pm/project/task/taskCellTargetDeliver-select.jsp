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
	var selects = '${selects}';
	var selectMode = '${selectMode}';
	var departName = '${departName}';
	var handler = '${handler}';
	var key = '${key}';

	$(function() {

		$('#btn_north_search').click(function() {
			//搜索按钮
			tip('<spring:message code="com.glaway.ids.pm.project.task.searching"/>');
		});

		$('#deliver_list_select_to')
				.datalist(
						{
							url : 'taskFlowTemplateController.do?doCellRelationSearch&side=right&templateId=${templateId}&fromCellId=${fromCellId}&toCellId=${toCellId}',
							//title : '输出交付项',
							textField : 'deliverName',
							valueField : 'deliverName',
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
							url : 'taskFlowTemplateController.do?doCellRelationSearch&side=left&templateId=${templateId}&fromCellId=${fromCellId}&toCellId=${toCellId}',
							//title : '输入交付项',
							textField : 'deliverName',
							valueField : 'deliverName',
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
			var idSrc = rowSrc.deliverName;
			var rows = $('#deliver_list_select_to').datalist('getRows');
			for (var j = 0; j < rows.length; j++) {
				var row = rows[j];
				var id = row.deliverName;
				if (idSrc == id) {
					var index = $('#deliver_list_select_from').datalist(
							'getRowIndex', rowSrc);
					if (-1 != index) {
						$('#deliver_list_select_from').datalist('updateRow', {
							index : index,
							row : {
								deliverName : rowSrc.deliverName,
								deliverName : rowSrc.deliverName,
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
	function northEnterKeyEvent(event) {
		if (event.keyCode == 13) {
			$("#btn_north_search").focus();
		}
	}

	function left_to_right(row) {
		$('#deliver_list_select_to').datalist('appendRow', {
			id : row.id,
			deliverName : row.deliverName,
			deliverName : row.deliverName
		});

		var index2 = $('#deliver_list_select_from')
				.datalist('getRowIndex', row);
		if (-1 != index2) {
			$('#deliver_list_select_from').datalist('updateRow', {
				index : index2,
				row : {
					id : row.id,
					deliverName : row.deliverName,
					deliverName : row.deliverName,
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
		var deliverName = row.deliverName;
		var rows = $('#deliver_list_select_from').datalist('getRows');
		for (var i = 0; i < rows.length; i++) {
			var rowTemp = rows[i];
			if (deliverName == rowTemp.deliverName) {
				var index2 = $('#deliver_list_select_from').datalist(
						'getRowIndex', rowTemp);
				if (-1 != index2) {
					$('#deliver_list_select_from').datalist('updateRow', {
						index : index2,
						row : {
							deliverName : deliverName,
							deliverName : deliverName,
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

	function getSelectDeparts() {

	}
	//获取ids
	function getIds(rows) {
		if (rows.length <= 0) {
			return '';
		}
		var ids = [];
		for (var i = 0; i < rows.length; ++i) {
			ids.push(rows[i].id);
		}
		return ids.join(",");
	}
	//获取新增的行的id
	function getInsertedIds(chR) {
		var oldR = toRows;
		if(toRows == undefined){
			toRows = $('#deliver_list_select_to').datalist('getRows');
		}
		var len = chR.length;
		var hasFind = new Array(len);
		for (var i = 0; i < len; ++i) {
			hasFind[i] = false;
			if (oldR != undefined)
				for (var j = 0; j < oldR.length; ++j) {
					if (chR[i].id == oldR[j].id) {
						hasFind[i] = true;
						break;
					}
				}
		}
		var insIds = [];
		for (var k = 0; k < len; ++k) {
			if (!hasFind[k]) {
				insIds.push(chR[k].id);
			}
		}
		if (insIds.length <= 0)
			return '';
		var ids = insIds.join(",");
		return ids;
	}
	//判断是否存在
	function isNotExist(id, rows) {
		var isFind = false;
		for (var i = 0; i < rows.length; ++i) {
			if (rows[i].id == id) {
				isFind = true;
				return false;
			}
		}
		return !isFind;
	}
	//获取删除的行的id
	function getDeletedIds(chR) {
		var delIds = [];
		var oldR = toRows;
		if (oldR != undefined)
			for (var i = 0; i < oldR.length; ++i) {
				if (isNotExist(oldR[i].id, chR)) {
					delIds.push(oldR[i].id);
				}
			}
		if (delIds.length <= 0)
			return '';
		return delIds.join(',');
	}
	//确认选择并保存
	function confirmSelections() {
		var rows = $('#deliver_list_select_to').datalist('getRows');
		var rows2 = $('#deliver_list_select_from').datalist('getRows');
		if(rows2.length != 0){
			var addIds = getInsertedIds(rows);
			var deleteIds = getDeletedIds(rows);
			$.post(
							"${pageContext.request.contextPath }/taskFlowTemplateController.do?doCellRelationChange",
							{
								'addIds' : addIds,
								'deleteIds' : deleteIds,
								'taskId' : '${templateId}',
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
		}else{
			var win = $.fn.lhgdialog("getSelectParentWin");
			win.cleanSelectCells();
			$.fn.lhgdialog("closeSelect");
		}
	}
	
	function close1(){
		window.parent.closeDiv("win");
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
			<div  region="center" style="padding: 1px; width: 4%; text-align: center;" title="">
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