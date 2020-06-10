<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<style type="text/css">
.lists {
	border-left: 1px solid #ddd;
	border-top: 1px solid #ddd;
	border-right: 0px solid #ddd;
	border-bottom: 0px solid #ddd;
	width: 100%;
}

.lists th {
	border-bottom: 1px dotted #ddd;
	border-right: 1px solid #ddd;
	border-top: 0px solid #ccc;
	border-left: 0px solid #ddd;
	font-size: 12px;
	font-weight: bold;
	height: 20px;
	line-height: 20px;
	vertical-align: middle;
	overflow: hidden;
	text-align: left;
	padding-left: 4px;
	padding-right: 4px;
	background-color: #EFEFEF;

}

.lists td {
	text-align: left;
	padding: 2px 4px;
	border-left: 0px solid #ccc;
	border-top: 0px solid #ccc;
	border-right: 1px dotted #ccc;
	border-bottom: 1px dotted #ccc;
	font-size: 12px;
	height: 18px;
	line-height: 18px;
	word-space:nowrap;
}

.lists tr:hover {
	background: #eaf2ff;
}
</style>
<div class="easyui-layout">
	<script type="text/javascript">
		$(document).ready(function() {
			debugger;
			var isView = '${isView}';
			var checks = '${checks}';
			var val = checks.split(",");
			var boxes = document.getElementsByName("checked");
			if (isView == 1) {
				for (var i = 0; i < boxes.length; i++) {
					boxes[i].disabled = true;
					if (val[i] == 'true') {
						boxes[i].checked = true;
					} else {
						boxes[i].checked = false;
					}
				}
			} else {
				for (var i = 0; i < boxes.length; i++) {
					if (val[i] == 'true') {
						boxes[i].checked = true;
					} else {
						boxes[i].checked = false;
					}
				}
			}
		});

		function check(id) {
			var val = id.split(",");
			if (val[1] == 'list') {
				var boxes = document.getElementsByName("checked");
				for (var i = 0; i < boxes.length; i++) {
					var val2 = boxes[i].id.split("+");
					if (val2[0] == val[0]) {
						if (boxes[i].checked == true && val2[1] != 'list') {
							boxes[i].checked = false;
						}

					}
				}
			} else if (val[1] == 'detail') {
				var boxes = document.getElementsByName("checked");
				for (var i = 0; i < boxes.length; i++) {
					var val2 = boxes[i].id.split("+");
					if (val2[0] == val[0]) {
						if (boxes[i].checked == false) {
							if (val2[1] == 'list') {
								boxes[i].checked = true;
								break;
							}
						} else {
							if (val2[1] != 'list' && val2[1] != 'detail') {
								boxes[i].checked = false;
							}
						}

					}
				}
			} else {
				var boxes = document.getElementsByName("checked");
				for (var i = 0; i < boxes.length; i++) {
					var val2 = boxes[i].id.split("+");
					if (val2[0] == val[0]) {
						if (val2[1] == 'list') {
							boxes[i].checked = true;
						}
						if (val2[1] == 'detail') {
							boxes[i].checked = true;
							break;
						}
					}
				}
			}
		}

		function saveBaseInfo() {
			var splitFlag = '${splitFlag}';
			var resultStr = '';
			var boxes = document.getElementsByName("checked");
			for (var i = 0; i < boxes.length; i++) {
				if (boxes[i].checked == true) {
					resultStr += '1';
				} else {
					resultStr += '0';
				}
				if ((i + 1) % splitFlag == 0 && i != 0) {
					resultStr += ',';
				}
			}
			$.ajax({
				url : 'projectLibTemplateController.do?doSave',
				type : 'post',
				data : {
					templateId : '${templateId}',
					fileId : '${fileId}',
					resultStr : resultStr
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					top.tip(d.msg);
				}
			});
		}
	</script>
	<c:if test="${!(isView eq 1)}">
		<fd:linkbutton id="saveBase" onclick="saveBaseInfo()" value="保存"
			iconCls="basis ui-icon-save" />
	</c:if>
	<div
		style="width: auto; height: auto; overflow-y: auto; overflow-x: hidden;">
		<table width="100%" id="lifeCycleStatus_table" class="lists">
			<tr>
				<th>角色名称</th>
				<th>角色编码</th>
				<c:forEach items="${docc}" var="doc">
					<th>${doc.checkName}</th>
				</c:forEach>
			</tr>
			<tbody id="lifeCycleStatus_table_body">
				<c:forEach items="${docs}" var="doc">
					<tr>
						<td align="center">${doc.roleName}</td>
						<td align="center">${doc.roleCode}</td>
						<c:forEach items="${docc}" var="doc2">
							<td align="center">
									<input style="margin: 0px;" type="checkbox" name="checked"
										checked="false"
										onclick="check('${doc.id}'+','+'${doc2.checkValue}')"
										id="${doc.id}+${doc2.checkValue}" />
								</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>