<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
	
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
</style>
</head>

<div>
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
												<td>${vo.time }</td>
											</tr>
										</table>
									</td>
									<td align="right">
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
								<iframe id="project${vo.pid }" width="320px" height="250px" scrolling="auto" frameborder="0"
									src="/brs/preview?__report=idsreport/projectBoard.rptdesign&__svg=false&projectId=${vo.pid}&projectName=${vo.pname}&manager=${vo.mname}&number=${vo.unum}&time=${vo.time}&planLevel=${vo.planlevel}"></iframe>
							</c:if>
							<c:if test="${riskPluginValid eq false}">
								<iframe id="project${vo.pid }" width="320px" height="250px" scrolling="auto" frameborder="0"
									src="/brs/preview?__report=idsreport/projectBoardWithoutRisk.rptdesign&__svg=false&projectId=${vo.pid}&projectName=${vo.pname}&manager=${vo.mname}&number=${vo.unum}&time=${vo.time}&planLevel=${vo.planlevel}"></iframe>
							</c:if>
						</td>
					</tr>
				</table>
			</li>
		</c:forEach>
	</ul>
</div>

</html>