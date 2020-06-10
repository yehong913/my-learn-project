<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>我的研发任务</title>
	<t:base type="jquery,tools,easyui,lhgdialog"></t:base>
	<c:if test="${!empty outwards}">
		<c:forEach items="${outwards}" var="out">
			<script type="text/javascript" src="webpage/com/glaway/changeFlowTask/changeFlowTask_${out.optionValue}.js"></script>
		</c:forEach>
	</c:if>

</head>
<body id="layout" class="easyui-layout">
<c:if test="${status == ''}">
	<div data-options="region:'north',collapsible:false" style="height: 170px;">
		<input type="hidden" id="taskId0" name="taskId0" value="${plan_.id}" />
		<fd:toolbar id="toolbar" help="helpDoc:FeedbackTaskPerformance">
			<c:if test="${ownerpd == ''}">
				<fd:toolbarGroup align="left">
					<fd:linkbutton id="planResolve" onclick="planResolve()"
								   value="{com.glaway.ids.pm.project.task.taskdetail.planResolve}" iconCls="basis ui-icon-task_decomposition" />
					<fd:linkbutton id="flowResolve" onclick="flowResolve()"
								   value="{com.glaway.ids.pm.project.task.taskdetail.flowResolve}" iconCls="basis ui-icon-task_decomposition" />
					<fd:linkbutton id="changeApply" onclick="goChangeApply()"
								   value="{com.glaway.ids.pm.project.task.taskdetail.changeApply}" iconCls="basis ui-icon-submitted_approval" />
					<fd:linkbutton id="parentSubmit" onclick="beforeSubmit()" value="{com.glaway.ids.common.btn.submit}"
								   iconCls="basis ui-icon-submitted_approval" />
					<fd:linkbutton id="checkFlow" onclick="checkFlow()" value="{com.glaway.ids.pm.project.task.taskdetail.viewProcess}"
								   iconCls="basis ui-icon-eye" />
					<fd:linkbutton id="abolishResolve" onclick="abolishResolve()"
								   value="{com.glaway.ids.pm.project.task.taskdetail.cancleResolve}" iconCls="basis ui-icon-return" />
					<fd:linkbutton id="delegateApply" onclick="delegateSubmit()" value="{com.glaway.ids.pm.project.task.taskdetail.delegateApprove}"
								   iconCls="basis ui-icon-appoint" />
				</fd:toolbarGroup>
			</c:if>
			<%@include file="/webpage/com/glaway/ids/pm/project/task/taskDetail-step.jsp"%>
		</fd:toolbar>
	</div>
</c:if>

<div data-options="region:'center'" style="padding: 5px; background: #eee;" id="qweqweq" >
	<fd:tabs id="tt" tabPosition="top" fit="true" >
	</fd:tabs>
</div>



<fd:dialog id="viewPlanTaskDialog1" width="690px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.viewTaskInfo}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="taskFlowResolve" width="780px" height="500px" modal="true" title="{com.glaway.ids.pm.project.task.taskdetail.flowResolve}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="doFlowResolve"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="editFlowTaskTd" width="1440px" height="600px"
		   modal="true" title="{com.glaway.ids.pm.project.task.taskdetail.editFlowTask}" max="false" min="false" maxFun="true">
</fd:dialog>
<fd:dialog id="goSubmittaskDetailDialog" width="400px" height="200px" modal="true" title="{com.glaway.ids.pm.project.plan.toApprove}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="submitApp"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="orderReviewTaskDialog" width="720px" height="300px" modal="true" title="{com.glaway.ids.pm.project.task.taskdetail.assignReviewTask}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="orderReviewTaskDialogOK"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="orderReviewTaskCheckDialog" width="940px" height="600px" modal="true" title="{com.glaway.ids.pm.project.task.taskdetail.viewReviewTask}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="orderReviewTaskCheckDialogOK"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="orderReviewTaskCheckDialog2" width="840px" height="500px" title="{com.glaway.ids.pm.project.task.taskdetail.reviewTaskDetail}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="goChangeApply" width="800px" height="280px" modal="true" title="{com.glaway.ids.pm.project.task.taskdetail.ownerChangeApply}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="goChangeApplyBtn"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="plmDialog" width="800px" height="580px" modal="true" title="选择交付物">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addPlmDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<!-- 子页面的填出窗口 -->
<fd:dialog id="editFlowTaskFr" width="1440px" height="600px"
		   modal="true" title="{com.glaway.ids.pm.project.task.taskdetail.editFlowTask}" max="false" min="false" maxFun="true"
		   beforClose="reloadAndCloseAll()">
</fd:dialog>
<fd:dialog id="editFlowTaskFr2" width="1440px" height="600px"
		   modal="true" title="{com.glaway.ids.pm.project.task.taskdetail.editFlowTask}" max="false" min="false" maxFun="true"
		   beforClose="reloadAndCloseAll()">
</fd:dialog>

<fd:dialog id="delegatePlanDialog" width="800px" height="300px" modal="true" title="委派申请" >
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="delegatePlanConfirmDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<script type="text/javascript">
	function orderReviewTaskDialogOK(iframe) {
		iframe.submit();
		return false;
	}

	function openOrderReviewTaskDialog() {
		var dialogUrl = '/ids-pm-web/taskDetailController.do?goOrderReviewTaskDialog&taskId=${plan_.id}';
		$("#" + 'orderReviewTaskDialog').lhgdialog("open", "url:" + dialogUrl);
	}

	function openOrderReviewTaskCheckDialog() {
		var url = '/ids-pm-web/taskDetailController.do?goOrderReviewTaskCheck&taskId=${plan_.id}';

		$.ajax({
			type : 'GET',
			url : url,
			success : function(data) {
				var d = JSON.parse(data);
				var taskNumber = d.obj.id;
				var url = d.obj.url;
				url = url + '&taskNumber=' + taskNumber;
				$("#" + 'orderReviewTaskCheckDialog2').lhgdialog("open", "url:" + url);
			}
		});
	}

	function submitApp(iframe) {
		iframe.submittt();
		return false;
	}

	//编写自定义JS代码
	var planType = '';
	var isAllHasFile = '';

	// 页面初始化
	$(function() {
		debugger
		$('#reviewResolve').attr("style", "display:none"); // 下发评审任务
		$('#reviewResolveCheck').attr("style", "display:none"); // 查看评审任务
		$('#planResolve').attr("style", "display:none"); // 计划分解
		$('#flowResolve').attr("style", "display:none"); // 流程分解
		$('#changeApply').attr("style", "display:none"); // 变更申请
		$('#parentSubmit').attr("style", "display:none"); // 提交
		$('#checkFlow').attr("style", "display:none"); // 查看流程
		$('#abolishResolve').attr("style", "display:none"); // 撤消分解
		$('#delegateApply').attr("style", "display:none"); // 委派申请

		beginFun();
		setTimeout(function() {
			openTab();
		}, 500);

		/* 		var loadUrl = 'tabCombinationTemplateController.do?goTabView2&id='+'4028f00b6d1df353016d1e58fcc40002';
                $("#qweqweq").empty("");
                $("#qweqweq").load(loadUrl); */

	});


	function createFrame(url) {
		var s = '<div class="portalFrameResize" style="position:absolute;height:99%; width:0px; z-index:2;"></div><iframe frameborder="0" scrolling="no" src="'
				+ url
				+ '" style="width:100%;height:99%;position:relative;"></iframe>';
		return s;
	}

	//参考判断js
	var isReference = true;
	function showReferenceTab(){
		if(isReference){
			return true;
		}else{
			return false;
		}
	}

	//风险清单判断js
	var isRiskList = true;
	function showRiskListTab(){
		if(isRiskList){
			return true;
		}else{
			return false;
		}
	}

	//问题判断js
	var isProblem = true;
	function showProblemTab(){
		if(isProblem){
			return true;
		}else{
			return false;
		}
	}

	//评审判断js
	var isReview = true;
	function showReviewTab(){
		if(isReview){
			return true;
		}else{
			return false;
		}
	}

	var tabUrlObj = new Object();
	function openTab() {
		debugger
		if('${isOut}' == '1'){
			$.ajax({
				url: '/ids-pm-web/taskFlowResolveController.do?getTabsByTaskNameType',
				data: {
					useCode : 'taskCategory'
				},
				type: "POST",
				success: function(data) {
					var jsonResult = $.parseJSON(data);
					if(jsonResult.success) {
						if(jsonResult.obj!=null && jsonResult.obj.length>0) {
							for(var i=0; i<jsonResult.obj.length; i++) {
								tabUrlObj[jsonResult.obj[i].id] = jsonResult.obj[i].href + '&planId='+'${plan_.id}&projectId=${plan_.projectId}'+ '&id='+'${plan_.id}' + '&nameStandardId='+'${nameStandardId}' + '&templateId='+'${plan_.parentPlanId}' + '&planStatus='+'${plan_.bizCurrent}'+ '&isOwner=${isOwner}';
								if(jsonResult.obj[i].title == '参考' && '${isKlmPluginValid}' == 'true'){
									$('#tt').tabs('add',{
										title: '<spring:message code="com.glaway.ids.pm.project.plan.reference"/>',
										id: 'reference',
										selected: 0,
										onLoad : 1,
										href: '/ids-pm-web/taskDetailController.do?goReferenceCheck&useObjectType=PLAN&isIframe=true&useObjectId=${plan_.id}'
									});
								}else{
									$('#tt').tabs('add',{
										title: jsonResult.obj[i].title,
										id: jsonResult.obj[i].id,
										selected: 0,
										onLoad : 1,
										//href: jsonResult.obj[i].href + '&planId='+'${plan_.id}' + '&planStatus='+'${plan_.bizCurrent}'
										href: '/ids-pm-web/taskFlowTemplateController.do?goFlowTempNodeTabPage&id='+jsonResult.obj[i].id
									});
								}
							}
							//如果安装risk插件，则显示问题页签
							if('${isRiskPluginValid}' == 'true')
							{
								$('#tt').tabs('add',{
									title: '<spring:message code="com.glaway.ids.pm.project.task.taskdetail.problem"/>',
									id: 'riskProblem',
									selected: 0,
									onLoad : 1,
									href: '${riskproblems_Nginx}/riskProblemTaskController.do?goProblemListForPlan&isIframe=true&planId=${plan_.id}&bizCurrent=${plan_.bizCurrent}&id=${plan_.projectId}&isOwner=${isOwner}'
								});
							}

						}
						$('#tt').tabs('select', '<spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/>');
					}
				}
			});

		}else{
			$.ajax({
				url: '/ids-pm-web/tabCombinationTemplateController.do?goTabView2',
				data: {
					planId : '${plan_.id}'/* ,
					displayAccess:'2' */
				},
				type: "POST",
				success: function(data) {
					var json = $.parseJSON(data);
					if(json.success) {
						var obj = json.obj;
						var selected = 1 ;
						for(var i=0;i<obj.length;i++){
							var endFlag = true;
							if(obj[i].displayAccess != "" && obj[i].displayAccess != null){
								try {
									endFlag = eval(obj[i].displayAccess);
								} catch (e) {
									endFlag = true;
								}
							}
							if(endFlag){
								if(obj[i].displayUsage!='1'){
									var curUrl = obj[i].url;
									var planId= "{plan_.id}";
									var pladIdValue = "${plan_.id}";
									var projectId = "{plan_.projectId}";
									var projectIdValue = "${plan_.projectId}";
									curUrl = curUrl.replace(/{plan_.id}/g,pladIdValue).replace(/{plan_.projectId}/g,projectIdValue);
									curUrl = curUrl +"&isEnableFlag=1";
									$('#tt').tabs('add',{
										title: obj[i].name,
										id: obj[i].typeId,
										selected: selected,
										onLoad : 1,
										href: curUrl
									});
									selected = 0;
								}else{
									if(obj[i].projectModel == "${riskproblems_service}"){
										if('${isRiskPluginValid}' == 'true'){
											$('#tt').tabs('add',{
												title: obj[i].name,
												id: obj[i].typeId,
												selected: selected,
												onLoad : 1,
												href: '/ids-pm-web/tabCombinationTemplateController.do?goTabCommonDetail&id=${plan_.projectId}&planId=${plan_.id}&showLog=true&enterType=2&showProjectInfo=true&tabId='+obj[i].typeId+'&title='+encodeURI(obj[i].name)+'&index='+i+''
											});
											selected = 0;
										}
									}
									else{
										$('#tt').tabs('add',{
											title: obj[i].name,
											id: obj[i].typeId,
											selected: selected,
											onLoad : 1,
											href: '/ids-pm-web/tabCombinationTemplateController.do?goTabCommonDetail&id=${plan_.projectId}&planId=${plan_.id}&showLog=true&enterType=2&showProjectInfo=true&tabId='+obj[i].typeId+'&title='+encodeURI(obj[i].name)+'&index='+i+''
										});
										selected = 0;
									}
								}
							}
						}
					}
				}
			});


			/* $('#tt').tabs('add',{
				title: '<spring:message code="com.glaway.ids.pm.project.plan.input"/>',
				id: 'inputs',
				selected: 0,
				href: 'taskDetailController.do?goInputCheck&useObjectType=PLAN&isIframe=true&useObjectId=${plan_.id}&projectId=${plan_.projectId}'
			});

			 $('#tt').tabs('add',{
				title: '<spring:message code="com.glaway.ids.pm.project.plan.output"/>',
				id: 'document',
				selected: 0,
				href: 'taskDetailController.do?goDocumentCheck&useObjectType=PLAN&isIframe=true&useObjectId=${plan_.id}&projectId=${plan_.projectId}'
			});

			if('${isKlmPluginValid}' == 'true' ) {
				$('#tt').tabs('add',{
		            title: '<spring:message code="com.glaway.ids.pm.project.plan.reference"/>',
		            id: 'reference',
		            selected: 0,
		            href: 'taskDetailController.do?goReferenceCheck&useObjectType=PLAN&isIframe=true&useObjectId=${plan_.id}'
		        });
			}

			$('#tt').tabs('add',{
				title: '<spring:message code="com.glaway.ids.pm.project.plan.resource"/>',
				id: 'resource',
				selected: 0,
				href: 'taskDetailController.do?goResourceCheck&useObjectType=PLAN&isIframe=true&useObjectId=${plan_.id}'
			});

			 //如果安装risk插件，则显示问题页签
			if('${isRiskPluginValid}' == 'true')
			{
				$('#tt').tabs('add',{
					title: '<spring:message code="com.glaway.ids.pm.project.task.taskdetail.problem"/>',
					id: 'riskProblem',
					selected: 0,

					href: '${riskproblems_Nginx}/riskProblemTaskController.do?goProblemListForPlan&isIframe=true&planId=${plan_.id}&bizCurrent=${plan_.bizCurrent }&id=${plan_.projectId}&isOwner=${isOwner}'
				});
			} */

			/* $('#tt').tabs('select', '<spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/>');  */
		}
	}


	function beginFun() {
		var params = {
			'id' : '${plan_.id}'
		}
		$.post('/ids-pm-web/planController.do?getPlanFeedbackJudge', params,
				function(data) {
					if (data != null) {
						planType = data;
						buttonControll(planType);
					}
				}, "JSON");
	};

	// 按钮显示状态控制
	function buttonControll(planType) {
		debugger;

		if ('${plan_.bizCurrent}' == "INVALID") { //已废弃
			$("#layout").layout("remove", "north");
		}

		var taskNameType = "${plan_.taskNameType}";
		var taskNameTypeReview = "${taskNameTypeReview}";
		if ('${isOwner}' == 'true') {
			//如果无子有交与无子无交 有反馈、提交与计划分解与流程分解按钮
			if ('${plan_.bizCurrent}' == "ORDERED") {//已下达状态，只有提交 评审分解 计划分解 流程分解 委派申请与反馈按钮
				if (taskNameType != null && taskNameType != '' && taskNameType != 'undefined' && taskNameType == taskNameTypeReview) {
					$('#reviewResolve').attr("style", "display:");
				} else {
					$('#reviewResolve').attr("style", "display:none");
				}
				if('${plan_.planDelegateProcInstId}' == "" || '${plan_.planDelegateProcInstId}' == null ||  '${plan_.planDelegateProcInstId}' == "null" ){
					$('#delegateApply').attr("style", "display:");
				}else{
					if('${plan_.isDelegateComplete}' != 'false'){
						$('#delegateApply').attr("style", "display:");
					}else{
						$('#parentSubmit').attr("style","display:none");
					}
				}


				$('#planResolve').attr("style", "display:");
				$('#flowResolve').attr("style", "display:");
				$('#changeApply').attr("style", "display:");
				$('#checkFlow').attr("style", "display:none");
				$('#abolishResolve').attr("style", "display:none");


				if (planType == '4') {//无子计划时，无撤消分解按钮
					$('#planResolve').attr("style", "display:");
					$('#flowResolve').attr("style", "display:");
					$('#checkFlow').attr("style", "display:none");
					$('#abolishResolve').attr("style", "display:none");
					getAllDocRelationList();

				} else if (planType == '0' || planType == '1') {//无子计划时，无撤消分解按钮
					$('#planResolve').attr("style", "display:");
					$('#flowResolve').attr("style", "display:");
					$('#parentSubmit').attr("style", "display:none");
					$('#checkFlow').attr("style", "display:none");
					$('#abolishResolve').attr("style", "display:none");
					getPlanOption();
					//if (planType == '0') {//无子计划有交付物的时候判断交付项是否已全部挂好文档，全挂就放开提交按钮
					getAllDocRelationList();
					//}

				} else if (planType == '2') {//如果有子，并且已完工时，有提交与计划分解按钮与反馈
					$('#planResolve').attr("style", "display:");
					$('#flowResolve').attr("style", "display:none");
					$('#reviewResolve').attr("style", "display:none");
					parentPlanOutCover();//提交按钮显示
					$('#checkFlow').attr("style", "display:none");
					hasNotEdtingSubPlan();
// 					hasOrderedSubPlan();//撤消分解按钮显示；  //zsx注释（只要存在子计划就有撤消分解按钮，缺陷13361）
					getPlanOption();

				} else if (planType == '3') {//如果有子有未完工的时间，只有计划分解与反馈
					$('#planResolve').attr("style", "display:");
					$('#flowResolve').attr("style", "display:none");
					$('#reviewResolve').attr("style", "display:none");
					$('#parentSubmit').attr("style", "display:none");
					$('#checkFlow').attr("style", "display:none");
					hasNotEdtingSubPlan();
// 					hasOrderedSubPlan();//撤消分解按钮显示；   //zsx注释（只要存在子计划就有撤消分解按钮，缺陷13361）
					getPlanOption();
				}

				if ('${plan_.progressRate}' >= ${ORDEREDWeight} && planType != '3') { //如果进度达到执行中权重，则有提交按钮
					$('#reviewResolve').attr("style", "display:none");
					if ('${plan_.flowStatus}' == "NORMAL" || ('${plan_.flowStatus }' == 'FEEDBACKING' && ('${plan_.feedbackProcInstId}'!='' || '${plan_.feedbackProcInstId}'!= null))) {//不在流程中的放开提交按钮
						//$('#parentSubmit').attr("style", "display:");
						planSeqApproveBtnControl();
					}
					$('#abolishResolve').attr("style", "display:none");
					getPlanOption();
				}

				//委派申请流程结束
				if('${plan_.isDelegateComplete}' == 'false'&&'${plan_.planDelegateProcInstId}' != ""){
					$('#parentSubmit').attr("style","display:none");
				}

			}
			else if ('${plan_.bizCurrent}' == "FEEDBACKING") {
				//完工反馈中状态，只有查看流程按钮
				$('#planResolve').attr("style", "display:none");
				$('#flowResolve').attr("style", "display:none");
				$('#reviewResolve').attr("style", "display:none");
				$('#parentSubmit').attr("style", "display:none");
				$('#checkFlow').attr("style", "display:");
				$('#abolishResolve').attr("style", "display:none");
				$('#changeApply').attr("style", "display:none");
				$('#delegateApply').attr("style", "display:none");
			}
			else if ('${plan_.bizCurrent}' == "FINISH") {
				$('#reviewResolve').attr("style", "display:none");
				$('#reviewResolveCheck').attr("style", "display:none");
				$('#delegateApply').attr("style", "display:none");
				//完工反馈中状态，只有查看流程按钮
				$('#planResolve').attr("style", "display:none");
				$('#parentSubmit').attr("style", "display:none");
				$('#checkFlow').attr("style", "display:");
				$('#abolishResolve').attr("style", "display:none");
				$('#changeApply').attr("style", "display:none");
				/* if ('${plan_.opContent}' == '流程分解') {
					$('#flowResolve').attr("style", "display:");
				} else { */
				$('#flowResolve').attr("style", "display:none");
				/* } */
			}
		}

		else if ('${isOwner}' == 'false') {
			$('#changeApply').attr("style", "display:none");
			$('#planResolve').attr("style", "display:none");
			$('#flowResolve').attr("style", "display:none");
			$('#reviewResolve').attr("style", "display:none");
			$('#parentSubmit').attr("style", "display:none");
			$('#abolishResolve').attr("style", "display:none");
			$('#delegateApply').attr("style", "display:none");
			$('#checkFlow').attr("style", "display:none");
			if ('${plan_.bizCurrent}' == "FINISH") {
				$('#checkFlow').attr("style", "display:");
			}
			if ('${plan_.bizCurrent}' == "FEEDBACKING") {
				$('#checkFlow').attr("style", "display:");
			}
		}

		//评审任务下发/计划分解/流程分解按钮互斥
		if ('${plan_.planType}' == '评审任务') {
			$('#reviewResolveCheck').attr("style", "display:");
			$('#reviewResolve').attr("style", "display:none");
			/* 			$('#planResolve').attr("style", "display:none");
                        $('#flowResolve').attr("style", "display:none"); */
		} else {
			$('#reviewResolveCheck').attr("style", "display:none");
		}


		if('${isOut}' == '1'){
			$('#planResolve').attr("style", "display:none");
			$('#flowResolve').attr("style", "display:none");
		}

		//未安装rdflow插件时无流程分解按钮
		if('${isRDFlowPluginValid}' == 'false'){
			$('#flowResolve').attr("style", "display:none");
		}

	}

	//查询存在非拟制中的子计划 true:都是拟制中的，可以撤消 ;false:不可以撤消
	function hasOrderedSubPlan() {
		debugger;
		if ('${isOwner}' == 'true') {
			var params = {
				'id' : '${plan_.id}'
			}
			$.post('/ids-pm-web/planController.do?judgePlanStatusByPlan', params, function(
					data) {
				if (data != null) {
					if (data.obj == false) {
						$('#abolishResolve').attr("style", "display:none");
						return false;
					} else {
						$('#abolishResolve').attr("style", "display:");
						return true;
					}
				}
			}, "JSON");
		}
	}

	//查询存在非拟制中的子计划 false:都是拟制中的，可以撤消 ;true:不可以撤消
	function hasNotEdtingSubPlan() {
		debugger;
		if ('${isOwner}' == 'true') {
			var params = {
				'planId' : '${plan_.id}'
			}
			$.post('/ids-pm-web/planController.do?isHaveChildrenNotEditing', params, function(
					data) {
				if (data != null) {
					if (data.success == true) {
						$('#abolishResolve').attr("style", "display:none");
						return true;
					} else {
						$('#abolishResolve').attr("style", "display:");
						return true;
					}
				}
			}, "JSON");
		}
	}

	//判断子计划是否已经包含了父计划的所有交付项
	function parentPlanOutCover() {
		if ('${isOwner}' == 'true') {
			var params = {
				'id' : '${plan_.id}'
			}
			$.post('/ids-pm-web/planController.do?parentPlanOutCover', params, function(
					data) {
				if (data != null) {
					if (data.obj == true) {
						$('#parentSubmit').attr("style", "display:");
					} else {
						//如果父计划的输出没有被子计划全部覆盖 相当于有子，本身有交付项。子交付项没有全部挂接
						isAllHasFile = 4
						$('#parentSubmit').attr("style", "display:none");
					}
					return data.obj;
				}
			}, "JSON");
		}
	}

	//计划时序校验
	function planSeqApproveBtnControl() {
		if ('${isOwner}' == 'true') {
			var params = {
				'id' : '${plan_.id}'
			}
			$.post('/ids-pm-web/planController.do?planSeqApproveBtnCheck', params, function(
					data) {
				if (data != null) {
					if (data.obj == true) {
						$('#parentSubmit').attr("style", "display:");
					} else {
						//如果父计划的输出没有被子计划全部覆盖 相当于有子，本身有交付项。子交付项没有全部挂接
						$('#parentSubmit').attr("style", "display:none");
					}
				}
			}, "JSON");
		}
	}

	//判断当前计划操作
	function getPlanOption() {
		if ('${isOwner}' == 'true') {
			var params = {
				'id' : '${plan_.id}'
			}
			$.post('/ids-pm-web/planController.do?getPlanOption', params, function(data) {
				if (data != null) {
					debugger;
					if (data == 0) {
						$('#flowResolve').attr("style", "display:none");
						$('#reviewResolve').attr("style", "display:none");
						$('#abolishResolve').attr("style", "display:");
					} else if (data == 1) {
						if('${isRDFlowPluginValid}' == 'false'){
							$('#flowResolve').attr("style", "display:none");
						}else{
							$('#flowResolve').attr("style", "display:");
						}
						$('#abolishResolve').attr("style", "display:none");
					} else if (data == 2) {
						$('#planResolve').attr("style", "display:none");
						$('#reviewResolve').attr("style", "display:none");
						if('${isRDFlowPluginValid}' == 'false'){
							$('#flowResolve').attr("style", "display:none");
						}else{
							$('#flowResolve').attr("style", "display:");
						}
						$('#abolishResolve').attr("style", "display:");
					}
				}
			}, "JSON");
		}
	}

	//判断是否已挂接文档
	function getDocRelationList() {
		if ('${isOwner}' == 'true') {
			var params = {
				'id' : '${plan_.id}'
			}
			$
					.post('/ids-pm-web/taskDetailController.do?judgePlanDocumant', params,
							function(data) {
								if (data != null) {
									if (data.obj == true) {
										$('#planResolve').attr("style",
												"display:none");
										$('#flowResolve').attr("style",
												"display:none");
										$('#reviewResolve').attr("style",
												"display:none");
									}
								}
							}, "JSON");
		}
	}

	//判断交付项是否都已挂接文档
	function getAllDocRelationList() {
		if ('${isOwner}' == 'true') {
			var params = {
				'id' : '${plan_.id}'
			}
			setTimeout(function(){
				$.post('/ids-pm-web/taskDetailController.do?judgePlanAllDocumant&isOut='+'${isOut}', params,
						function(data) {
							if (data != null) {
								if (data.obj == 0 || data.obj == null || data.obj == "") {
									$('#parentSubmit').attr("style", "display:");
								}
								if (isAllHasFile == "") {
									isAllHasFile = data.obj;
								}
							}
						}, "JSON");
			}, 1000)

		}
	}

	function getPlanType() {
		return planType;
	}

	function myFeedBackTaskDialog(iframe) {
		var result = iframe.saveAndSubmittt();
		debugger
		if (!result) {
			return false;
		} else {
			top.tabRefresh(result)
			return true;
		}
	}

	//提交前校验子计划是否完工
	function beforeSubmit() {
		$.ajax({
			url : '/ids-pm-web/planController.do?checkChildPlanStatusFinish',
			type : 'get',
			data : {
				id : '${plan_.id}'
			},
			cache : false,
			success : function (data) {
				var d = $.parseJSON(data);
				if (d.success) {
					goSubmit();
				} else {
					top.Alert.confirm("存在未完工的子计划，是否确认完工",function(r){
						if (r) {
							goSubmit();
						}
					});
				}
			}
		});
	}

	//
	function goSubmit() {
		var dialogUrl = '/ids-pm-web/taskDetailController.do?goSubmit&tabId=${plan_.id}&taskId=${plan_.id}&planType=' + planType;
		$('#' + 'goSubmittaskDetailDialog').lhgdialog('open', 'url:' + dialogUrl);
	}

	function checkFlow() {
		if ('${plan_.feedbackProcInstId}' == "") {
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.noCompleteFlow"/>');
			return;
		}
		createdetailwindow(
				'${plan_.planName}'+'<spring:message code="com.glaway.ids.pm.rdtask.flowResolve.feedbackProcess"/>',
				'generateFlowDiagramController.do?initDrowFlowActivitiDiagram&procInstId=${plan_.feedbackProcInstId}',
				800, 600);
	}

	function planResolve() {
		var params = {
			'planId' : '${plan_.id}'
		}
		//点击分解按钮就新增log日志
		//$.post('planLogController.do?savePlanSplitLog', params, function(
		//		data) {
		//}, "JSON");
		$.ajax({
			type : 'POST',
			url : '/ids-pm-web/planLogController.do?savePlanSplitLog',
			async : false,
			data : params
		})
		var title = '<spring:message code="com.glaway.ids.pm.project.task.taskdetail.projectManageNew"/>';
		if (!top.$('#maintabs').tabs('exists', title)) {
			top.addTabOrRefresh(title,
					"/ids-pm-web/projectMenuController.do?projectMenu&isIframe=true&projectId=${plan_.projectId}&taskDetailGetPlanId=${plan_.id}&afterIframe=true",
					'pictures');
		} else {
			top.$('#maintabs').tabs('close', title);
			top.addTabOrRefresh(title,
					"/ids-pm-web/projectMenuController.do?projectMenu&isIframe=true&projectId=${plan_.projectId}&taskDetailGetPlanId=${plan_.id}&afterIframe=true",
					'pictures');
		}
	}

	function afterPlanResolve() {
		debugger;
		if ('${isOwner}' == 'true') {
			//只有存在子计划 并且不都是拟制中的情况才隐藏撤消分解按钮
// 			hasOrderedSubPlan();    //zsx注释（只要存在子计划就有撤消分解按钮，缺陷13361）
			$('#flowResolve').attr("style", "display:none");
			$('#reviewResolve').attr("style", "display:none");
			var tab = $("#tt").tabs('getTab', '<spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/>');
			tab.panel('refresh', '/ids-pm-web/taskDetailController.do?goBasicCheck&id=${plan_.id}');
			tab = $("#tt").tabs('getTab', '<spring:message code="com.glaway.ids.pm.project.plan.output"/>');
			tab.panel('refresh', '/ids-pm-web/taskDetailController.do?goDocumentCheck&useObjectType=PLAN&useObjectId=${plan_.id}');
			beginFun();
		}
	}

	function abolishResolve() {
		var params = {
			'id' : '${plan_.id}'
		}
		$.ajax({
			type : "POST",
			data:{
				'id' : '${plan_.id}'
			},
			url : '/ids-pm-web/planController.do?judgeBeforeAbolishResolve',
			cache:false,
			success:function(data){
				var d = $.parseJSON(data);
				if(d.success){
					top.Alert.confirm("撤消分解将删除该计划的所有子计划，是否确定？",function(r){
						if(r){
							$.post(
									'/ids-pm-web/planController.do?deleteChildPlan', params,
									function(data) {
										if (data != null) {
											var tab = $("#tt").tabs('getTab', '<spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/>');
											tab.panel('refresh', '/ids-pm-web/taskDetailController.do?goBasicCheck&id=${plan_.id}');
											tab = $("#tt").tabs('getTab', '<spring:message code="com.glaway.ids.pm.project.plan.output"/>');
											tab.panel('refresh', '/ids-pm-web/taskDetailController.do?goDocumentCheck&useObjectType=PLAN&useObjectId=${plan_.id}');
											if (data.success) {
												tip(data.msg);
												beginFun();
											}/*  else {
												tip(data.msg);
											} */
										}
									}, "JSON");
						}
					});
				}else{
					tip(d.msg);
				}
			}
		});

	}

	// 流程分解
	function flowResolve() {
		debugger;
		var newdate = new Date().getTime();
		var planId = $('#taskId0').val();
		var userId = '';
		var params = {
			'id' : planId
		}
		$.post('/ids-pm-web/planController.do?getPlanOptionNew', params, function(data) {
			debugger;
			if (data == 0) {
				tip("已有子计划产生，不能再进行流程分解");
				return false;
			}else{
				debugger;
				$.ajax({
					url : '/ids-pm-web/applyFlowResolveForChangeController.do?flowResolveJudge&isIframe=true',
					type : 'post',
					data : {
						planId : planId
					},
					cache : false,
					success : function(data) {
						debugger;
						if (data != null) {
							var d = $.parseJSON(data);
							userId = d.msg.split('<br/>')[0];
							if (d.success == true) {
								if(d.obj == "existsRdTask"){
									tip("研发流程任务已有任务产生，不能再进行流程分解");
									return false;
								}

								if (d.obj == "prepare") {
									var dialogUrl = '/ids-pm-web/taskFlowResolveController.do?goPlanResolve&isIframe=true&id=' + $('#taskId0').val();
									$("#" + 'taskFlowResolve').lhgdialog("open", "url:" + dialogUrl);
								} else {
									var x = d.obj.split(',');
									var isEnableFlag = x[0];
									var status = x[1];
									var url;
									if ("ORDERED" == status) {
										// 流程变更分支
										url = "/ids-pm-web/applyFlowResolveForChangeController.do?flowResolveEditorForOrdered&rdflowWeb_Nginx=${rdflowWeb_Nginx}&type=plan&isIframe=true&newDate=" + newdate;
									} else {
										// 流程分解分支
										url = "/ids-pm-web/taskFlowResolveController.do?flowResolveEditor&rdflowWeb_Nginx=${rdflowWeb_Nginx}&isIframe=true&newDate=" + newdate;
									}
									// isEnableFlag为1表示只能查看、不能编辑或者变更
									if ('${isOwner}' == 'false') {
										isEnableFlag = 1;
									}
									$.ajax({
										type : 'POST',
										url : url,
										data : {
											id : planId
										},
										success : function(data) {
											debugger;
											if('ORDERED' == status){
												<c:if test="${!empty outwards}">
												<c:forEach items="${outwards}" var="out">
												var outType = '${out.optionValue}';
												eval("outSystemDataInit_" + outType + "(planId)");
												</c:forEach>
												</c:if>
											}
											/*var furl = "${rdflowWeb_Nginx}/webpage/com/glaway/ids/rdtask/workFlow/examples/editors/workfloweditor.jsp?isEnableFlag="*/
											var furl = "${rdflowWeb_Nginx}/rdflowCommonController.do?goOpenEditorPage&isEnableFlag="
													+ isEnableFlag
													+ "&parentPlanId="
													+ $('#taskId0').val()
													+ "&status="
													+ status
													+ "&fromType="
													+ "PLAN"
													+ "&enterType="
													+ "planChangeStart"
													+ "&newDate="
													+ newdate
													+ "&userId="
													+ userId
											$("#" + 'editFlowTaskTd').lhgdialog("open", "url:" + furl);
										}
									});
								}
							} else {
								tip(d.msg);
							}
						} else {
							tip('<spring:message code="com.glaway.ids.pm.project.task.flowResolveFailure"/>');
						}
					}
				});

			}
		}, "JSON");
	}

	function goChangeApply(plantemplateid) {
		var dialogUrl = '/ids-pm-web/taskFlowResolveController.do?goChangeApply&planId=' + $('#taskId0').val();
		createDialog('goChangeApply', dialogUrl);
	}

	function goChangeApplyBtn(iframe) {
		var leader = iframe.$('#leader').val();
		var changeType = iframe.$('#changeType').combobox('getValue');
		var changeRemark = iframe.$('#changeRemark').val();
		if (changeType == "") {
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.changeReasonExistCheck"/>');
			return false;
		}
		if (leader == "") {
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.selectLeaderPlease"/>');
			return false;
		}
		$.post('/ids-pm-web/taskFlowResolveController.do?startChangeApplyProcess', {
			'leader' : leader,
			'changeType' : changeType,
			'changeRemark' : changeRemark,
			'planId' : iframe.$('#planId').val()
		}, function(data) {//刷新
			top.tip('<spring:message code="com.glaway.ids.pm.project.plan.submitSuccess"/>');
			return true;
		});
	}

	function reloadTask() {
		reloadTable();
	}

	function doFlowResolve(iframe) {
		iframe.doFlowResolve();
		return false;
	}

	function createDia(id, furl) {
		$("#" + id).lhgdialog("open", "url:" + furl);
	}

	function reloadAndCloseAll() {
		beginFun();
	}

	function warn11() {
		top.Alert.confirm('<spring:message code="com.glaway.ids.pm.project.task.taskdetail.makeSureLeave"/>', function(r) {
			if (r) {
				$.fn.lhgdialog("getSelectParentWin").closeAllDialog();
			}else{
				return false;
			}
		});
		return false;
	}

	function closeAllDialog() {
		setTimeout(function() {
			$.fn.lhgdialog("closeAll");
		},1000)
	}


	function delegateSubmit(){
		var dialogUrl = '/ids-pm-web/planController.do?goDelegatePlanPage&planId=${plan_.id}';
		$("#delegatePlanDialog").lhgdialog("open", "url:" + dialogUrl);
	}

	function delegatePlanConfirmDialog(iframe){
		iframe.startPlanDelegateProcessSubmit();
		return false;
	}

</script>

</body>
</html>
