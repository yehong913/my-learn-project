<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>视图展示条件</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
	<div class="easyui-panel" title="视图展示条件" fit="true" border="false" id="right_page_panel">
		${viewSearchInfo }     
	</div>
</body>
</html>
