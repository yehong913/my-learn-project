<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>资源预估使用率报表</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
</script>
<style type="text/css">
.search_private {
	overflow: hidden;
	width: 99%;
}
</style>
</head>
<body>
	<iframe id="reportFrame" name="report" scrolling="auto" frameborder="0" width="99%" height="650px"
		src="${url}&startDate=${startDate}&endDate=${endDate}&resourceId=${resourceId}">
	</iframe>
</body>
</html>
