<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划</title>
<t:base type="jquery,tools,easyui,lhgdialog"></t:base>
</head>


<body>
	<div border="false" class="easyui-panel div-msg" fit="true">
		<input type="hidden" id="planId" value="${planIdSave}"> 
		<input type="hidden" id="taskId" value="${taskIdSave}">
		<fd:tabs id="tt" tabPosition="top" fit="true" >
			<%--<fd:tab title="{com.glaway.ids.pm.project.plan.baseinfo}" id="planBasic"
				href="taskDetailController.do?goBasicCheck&id=${plan_.id}"></fd:tab>
			<fd:tab title="{com.glaway.ids.pm.project.plan.input}" id="inputs"
				href="taskDetailController.do?goInputCheck&useObjectType=PLAN&isIframe=true&useObjectId=${plan_.id}"></fd:tab>
			<fd:tab title="{com.glaway.ids.pm.project.plan.output}" id="document"
				href="taskDetailController.do?goDocumentCheck&useObjectType=PLAN&isIframe=true&useObjectId=${plan_.id}"></fd:tab>
				<!--  只上ids-pm插件时不展示参考页签，先注释，后续根据插件状态判断展示与否 -->
			&lt;%&ndash; <fd:tab title="{com.glaway.ids.pm.project.plan.reference}" id="reference"
				href="taskDetailController.do?goReferenceCheck&useObjectType=PLAN&isIframe=true&useObjectId=${plan_.id}"></fd:tab> &ndash;%&gt;
			<fd:tab title="{com.glaway.ids.pm.project.plan.resource}" id="resource"
				href="taskDetailController.do?goResourceCheck&useObjectType=PLAN&isIframe=true&useObjectId=${plan_.id}"></fd:tab>--%>
		</fd:tabs>
		
		<div class="div-msg-btn">
			<div>
				<fd:linkbutton onclick="taskDetailBackSubmit()" value="{com.glaway.ids.pm.project.plan.toApprove}"
					classStyle="button_nor" />
				<fd:linkbutton id="cancelBtn" onclick="taskDetailCancel()" value="{com.glaway.ids.common.btn.cancel}"
					classStyle="button_nor" />
			</div>
		</div>
		<fd:dialog id="submitTaskFeedbackDialog" width="420px" height="160px"
			modal="true" title="提交">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="taskFeedbackSubmit"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
	</div>
	<script type="text/javascript">

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

		//编写自定义JS代码
		var planType = '';
		$(function() {
			$('#cancelBtn').focus();
			var params = {
				'id' : '${planIdSave}'
			};
			$.post('planController.do?getPlanFeedbackJudge', params, function(
					data) {
				if (data != null) {
					planType = data;
				}
			}, "JSON");


		$.ajax({
			url: 'tabCombinationTemplateController.do?goTabView2',
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
											href: 'tabCombinationTemplateController.do?goTabCommonDetail&id=${plan_.projectId}&planId=${plan_.id}&showLog=true&enterType=2&onlyReadonly=1&showProjectInfo=true&tabId='+obj[i].typeId+'&title='+encodeURI(obj[i].name)+'&index='+i+''
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
										href: 'tabCombinationTemplateController.do?goTabCommonDetail&id=${plan_.projectId}&planId=${plan_.id}&showLog=true&enterType=2&onlyReadonly=1&showProjectInfo=true&tabId='+obj[i].typeId+'&title='+encodeURI(obj[i].name)+'&index='+i+''
									});
									selected = 0;
								}
							}
						}
					}
				}
			}
		});
				}

		)

		//暂未被调用的方法
		function myFeedBack(id) {
			$.fn
					.lhgdialog({
						content : 'url:taskDetailController.do?goFeedBack&taskId=${plan_.id}&planType='
								+ planType
								+ '&progressRate=${plan_.progressRate}',
						lock : true,
						width : 600,
						height : 500,
						title : '<spring:message code="com.glaway.ids.pm.project.task.planFeedbackProgress"/>',
						opacity : 0.3,
						cache : false,
						ok : function(data) {
							iframe = this.iframe.contentWindow;
							saveObj();
							return false;
						},
						okVal : '<spring:message code="com.glaway.ids.common.btn.submit"/>',
						cancelVal : '<spring:message code="com.glaway.ids.common.btn.cancel"/>',
						cancel : true
					/*为true等价于function(){}*/
					});

		}

		//暂未被调用的方法
		function goSubmit() {
			$.fn
					.lhgdialog({
						content : 'url:taskDetailController.do?goSubmit&taskId=${plan_.id}&planType='
								+ planType,
						width : 400,
						height : 200,
						title : '<spring:message code="com.glaway.ids.pm.project.plan.toApprove"/>',
						opacity : 0.3,
						cache : false,
						ok : function(data) {
							iframe = this.iframe.contentWindow;
							saveObj();
							return false;
						},
						okVal : '<spring:message code="com.glaway.ids.common.btn.submit"/>',
						cancelVal : '<spring:message code="com.glaway.ids.common.btn.cancel"/>',
						cancel : true
					/*为true等价于function(){}*/
					});
		}

		function taskDetailBackSubmit() {
			var leaderId = '${leaderId}';
			var leaderUserName = '${leaderUserName}';
			var leaderRealName = '${leaderRealName}';
			var url = 'taskDetailController.do?goSubmitTaskFeedback&leaderId='
				+ leaderId+'&leaderUserName='+leaderUserName+'&leaderRealName='+leaderRealName;
			$("#submitTaskFeedbackDialog").lhgdialog("open", "url:" + url);
			
			
		}
		
		
		function taskFeedbackSubmit(iframe){
			var leader = iframe.$("#leader").val();
			var planId = $('#planId').val();
			var taskId = $('#taskId').val();
			var taskNumber = $('#taskTest').val();
			$
					.ajax({
						type : 'POST',
						url : 'taskFeedbackController.do?doSubmitApprove&planId='
								+ planId,
						data : {
							taskId : taskId,
							taskNumber : taskNumber,
							leader : leader
						},
						success : function(data) {
							 
// 							W.getData();
							try{
			                	$.fn.lhgdialog("getSelectParentWin").parent.flashTaskShow();
			                }catch(e){
			                	
			                }
			                
			                try{
			                	$.fn.lhgdialog("getSelectParentWin").parent.$("#startTaskListMore_wait").datagrid('reload');
			                }catch(e){
			                	
			                }
							var d=JSON.parse(data);
							top.tip(d.msg);
							$.fn.lhgdialog("closeSelect");
						}
					});
		}

		function taskDetailCancel() {
			$.fn.lhgdialog("closeSelect");
		}
	</script>
</body>
</html>