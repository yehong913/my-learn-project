<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划单条变更审批流程中查看</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	//编写自定义JS代码
	$(document).ready(function() {
		setTimeout(function() {
			openTab();
		}, 500);
		$('#tt').tabs({
			onSelect : function(title) {
				$('#planChangeTabs .panel-body').css('width', 'auto');
			}
		});
		$(".tabs-wrap").css('width', '100%');

	});



	function openTab() {
		debugger;
		var tabList = '${tabList}';
		tabList = eval(tabList);
		var selected = 1 ;
		for(var i=0;i<tabList.length;i++){
			var endFlag = true;
			if(tabList[i].displayAccess != "" && tabList[i].displayAccess != null){
				try {
					endFlag = eval(tabList[i].displayAccess);
				} catch (e) {
					endFlag = true;
				}
			}
			if(endFlag){
				$('#tt').tabs('add',{
					title: tabList[i].title,
					id: tabList[i].id,
					selected: selected,
					onLoad : 1,
					href: tabList[i].href
				});
			}

			selected = 0;
		}

	}



	//参考判断js
	var isReference = false;
	function showReferenceTab(){
		if(isReference && '${plan_.bizCurrent}'!="EDITING"){
			return true;
		}else{
			return false;
		}
	}

	//风险清单判断js
	var isRiskList = false;
	function showRiskListTab(){
		if(isRiskList && '${plan_.bizCurrent}'!="EDITING"){
			return true;
		}else{
			return false;
		}
	}

	//问题判断js
	var isProblem = false;
	function showProblemTab(){
		if(isProblem && '${plan_.bizCurrent}'!="EDITING"){
			return true;
		}else{
			return false;
		}
	}

	//评审判断js
	var isReview = false;
	function showReviewTab(){
		if(isReview && '${plan_.bizCurrent}'!="EDITING"){
			return true;
		}else{
			return false;
		}
	}
	


</script>
</head>
<body>
	<fd:tabs id="tt" tabPosition="top" fit="true">

	</fd:tabs>
</body>