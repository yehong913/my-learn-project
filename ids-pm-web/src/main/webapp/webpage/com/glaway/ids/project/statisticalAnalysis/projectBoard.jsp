<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
	$(function() {
		//分页
		$('#outputPagination').pagination({
			pageNumber : '${pageNumberForPb}',
			total : '${pageTotalForPb}',
			pageSize : '${pageSizeForPb}',
			layout : [ 'links' ],
			onSelectPage : function(pageNumber, pageSize) {
				$('#outputPagination').pagination('loading');
				onSelectPage(pageNumber, pageSize);
				$('#outputPagination').pagination('loaded');
			}
		});
	});

	function onSelectPage(pageNumber, pageSize) {
		var url = 'statisticalAnalysisController.do?goProjectBoardAfter';
		url = url + '&pageNumber=' + pageNumber + '&pageSize=' + pageSize;
		$('#projectBoardBefore').hide();
		$('#projectBoardAfter').show();
		$('#projectBoardAfter').panel('open').panel('refresh', url);
	}

	function goDetail(projectId) {
		var title = '<spring:message code="com.glaway.ids.pm.project.statisticalAnalysis.projectManage"/>';
		if (!top.$('#maintabs').tabs('exists', title)) {
			top.addTabById(title,
					"projectMenuController.do?projectMenu&projectId=" + projectId + "&fromType=fromProjectBoard",
					'pictures','4028efee4c3617ec014c362eed6c0012');
		} else {
			top.$('#maintabs').tabs('close', title);
			top.addTabById(title,
					"projectMenuController.do?projectMenu&projectId=" + projectId + "&fromType=fromProjectBoard",
					'pictures','4028efee4c3617ec014c362eed6c0012');
		}
	}

	function showMnameTip(id) {
		var mname = $('#' + id + '_tip').val();
		$('#' + id + "_mname").tooltip({
			position : 'right',
			content : '<span style="color:#fff">' + mname + '</span>',
			onShow : function() {
				$(this).tooltip('tip').css({
					backgroundColor : '#666',
					borderColor : '#666'
				});
			}
		});
	}

	function goPictureExample() {
		$("#projectBoardPic").lhgdialog("open", "url:statisticalAnalysisController.do?goProjectBoardPic");
	}
</script>

<style type="text/css">
.projectBoardPortlet{margin:0;padding:0}
.projectBoardPortlet li {
	list-style: none;
	float: left;
	margin: 0px;
	font-size:12px;
	padding:0;
}
.tuli{text-align:right; font-size:12px; margin:5px}
.tuli img{margin:3px 5px 0 10px}
</style>
</head>

<div>

	<div class="tuli"><img src="webpage/com/glaway/ids/project/statisticalAnalysis/img/red.png" width="12px" height="12px">报警  <img src="webpage/com/glaway/ids/project/statisticalAnalysis/img/orange.png" width="12px" height="12px">预警  <img src="webpage/com/glaway/ids/project/statisticalAnalysis/img/green.png" width="12px" height="12px">正常</div>
	<div id="projectBoardBefore" class="easyui-panel" fit="true"  style="border:none">
		<ul class="projectBoardPortlet">
			<c:forEach items="${voList}" var="vo">
				<li style="border:1px solid #ccc;margin:5px">
					<table>
						<tr>
							<td>
								<table width="100%">
									<tr>
										<td>
											<table width="100%">
												<tr>
													<td><a href='#' onclick="goDetail('${vo.pid}')" style='color: blue'>${vo.pname}</td>
												</tr>
												<tr>
													<td>
														<a id="${vo.pid}_mname" title="${vo.mname}" style="color: black"> 
															<c:if test="${fn:length(vo.mname) < 20}">${vo.mname}</c:if> 
															<c:if test="${fn:length(vo.mname) > 20}">${vo.showName}</c:if>
														</a> 
													</td>
													<td>${vo.unum}</td>
												</tr>
												<tr>
													<td>${vo.time}</td>
												</tr>
											</table>
										</td>
										<td align="left">
											<c:if test="${vo.color eq 'red'}">
												<img src="webpage/com/glaway/ids/project/statisticalAnalysis/img/red.png">
											</c:if> 
											<c:if test="${vo.color eq 'green'}">
												<img src="webpage/com/glaway/ids/project/statisticalAnalysis/img/green.png">
											</c:if> 
											<c:if test="${vo.color eq 'orange'}">
												<img src="webpage/com/glaway/ids/project/statisticalAnalysis/img/orange.png">
											</c:if>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<c:if test="${riskPluginValid eq true}">
									<iframe id="project${vo.pid}" width="320px" height="250px" scrolling="auto" frameborder="0" 
										src="/brs/preview?__report=idsreport/projectBoard.rptdesign&__svg=false&projectId=${vo.pid}&projectName=${vo.pname}&manager=${vo.mname}&number=${vo.unum}&time=${vo.time }&planLevel=${vo.planlevel}"></iframe>
								</c:if>
								<c:if test="${riskPluginValid eq false}">
									<iframe id="project${vo.pid}" width="320px" height="250px" scrolling="auto" frameborder="0" 
										src="/brs/preview?__report=idsreport/projectBoardWithoutRisk.rptdesign&__svg=false&projectId=${vo.pid}&projectName=${vo.pname}&manager=${vo.mname}&number=${vo.unum}&time=${vo.time }&planLevel=${vo.planlevel}"></iframe>
								</c:if>
							</td>
						</tr>
					</table>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div id="projectBoardAfter" class="easyui-panel" fit="true" style="display: none"></div>
	<div id="outputPagination" class="easyui-pagination"></div>
</div>

</html>