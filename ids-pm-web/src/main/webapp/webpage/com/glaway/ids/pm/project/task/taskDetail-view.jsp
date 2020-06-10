<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">

	// 页面初始化
	$(function() {
		setTimeout(function() {
			openTab();
		}, 500);
		
	});

	var tabUrlObj = new Object();
	function openTab() {
		if('${isOut}' == '1'){
			$.ajax({
				url: 'taskFlowResolveController.do?getTabsByTaskNameType',
				data: {
					useCode : 'viewPlanInfo'
				},
				type: "POST",
				success: function(data) {
					var jsonResult = $.parseJSON(data);
					if(jsonResult.success) {
						if(jsonResult.obj!=null && jsonResult.obj.length>0) {
							for(var i=0; i<jsonResult.obj.length; i++) {
								if(jsonResult.obj[i].title == '参考'){
									$('#tt').tabs('add',{
										title: '<spring:message code="com.glaway.ids.pm.project.plan.reference"/>',
										id: 'reference',
										selected: 0,
										onLoad : 1,
										href: 'taskDetailController.do?goReferenceCheck&useObjectType=PLAN&isIframe=true&useObjectId=${plan_.id}'
									});
								}else{
									$('#tt').tabs('add',{
										title: jsonResult.obj[i].title,
										id: jsonResult.obj[i].id,
										selected: 0,
										onLoad : 1,
										href: jsonResult.obj[i].href +'&planId='+'${plan_.id}&projectId=${plan_.projectId}'+ '&id='+'${plan_.id}' + '&nameStandardId='+'${nameStandardId}' + '&templateId='+'${plan_.parentPlanId}' + '&planStatus='+'${plan_.bizCurrent}'+ '&isOwner=${isOwner}'
									});
								}
							}
						}
						$('#tt').tabs('select', '<spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/>');
					}
				}
			});
			
		}else{
			$('#tt').tabs('add',{
				title: '<spring:message code="com.glaway.ids.pm.project.plan.input"/>',
				id: 'inputs',
				selected: 0,
				onLoad : 1,
				href: 'taskDetailController.do?goInputCheck&useObjectType=PLAN&isIframe=true&useObjectId=${plan_.id}'
			});
			
			 $('#tt').tabs('add',{
				title: '<spring:message code="com.glaway.ids.pm.project.plan.output"/>',
				id: 'document',
				selected: 0,
				 onLoad : 1,
				href: 'taskDetailController.do?goDocumentCheck&viewType=VIEW&useObjectType=PLAN&isIframe=true&useObjectId=${plan_.id}'
			});
			
			 //只上ids-pm插件时不展示参考页签，先注释，后续根据插件状态判断展示与否
			/* $('#tt').tabs('add',{
				title: '<spring:message code="com.glaway.ids.pm.project.plan.reference"/>',
				id: 'reference',
				selected: 0,
				href: 'taskDetailController.do?goReferenceCheck&useObjectType=PLAN&isIframe=true&useObjectId=${plan_.id}'
			}); */
			
			$('#tt').tabs('add',{
				title: '<spring:message code="com.glaway.ids.pm.project.plan.resource"/>',
				id: 'resource',
				selected: 0,
				onLoad : 1,
				href: 'taskDetailController.do?goResourceCheck&useObjectType=PLAN&isIframe=true&useObjectId=${plan_.id}'
			});
			
			 //只上ids-pm插件时不展示问题页签，先注释，后续根据插件状态判断展示与否
			/* $('#tt').tabs('add',{
				title: '<spring:message code="com.glaway.ids.pm.project.task.taskdetail.problem"/>',
				id: 'riskProblem',
				selected: 0,
				href: 'riskProblemTaskController.do?goProblemList&isIframe=true&planId=${plan_.id}&bizCurrent=${plan_.bizCurrent }&id=${plan_.projectId}'
			}); */
			
			$('#tt').tabs('select', '<spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/>'); 
		}
	}
	

</script>
</head>

<body>
	<fd:tabs id="tt" tabPosition="top" fit="true" >
		<fd:tab title="{com.glaway.ids.pm.project.plan.baseinfo}" id="planBasic" href="taskDetailController.do?goBasicCheck&id=${plan_.id}"></fd:tab>
	</fd:tabs>
</body>
</html>