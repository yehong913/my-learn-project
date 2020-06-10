<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,lhgdialog"></t:base>
<style>
	.progressbar-value {
		border-radius: 24px;

	}
	.progressbar-value .progressbar-text{
		background-color: #49a9ee;
	}
	.mainpanel_panel{clear:both;}
	.mainpanel_panel .panel_body{ height:500px; }
	.mainpanel_panel .panel_body .panel_body_title span{ float:right; display:inline-block}
	.mainpanel_panel .panel_body .panel_body_content{padding:0 0 6px;}
	.mainpanel_panel .panel_body .panel_body_content_dtl{ font-size:12px; font-weight:bold; margin:8px 0 0px}
	.mainpanel_panel .panel_body .panel_body_content_dtl a{color:#000;margin-right:80px; display:block; height:12px; line-height:12px; overflow:hidden; font-weight:normal; font-size:12px;word-break: break-all;}
	.mainpanel_panel .panel_body .panel_body_content_dtl span{float:right; font-weight:normal; color:#999; margin-top:-12px;line-height:12px;margin-right: 1px;}
    .mainpanel_panel .panel_body .panel_body_content_title{ font-size:12px; font-weight:normal; margin:16px 0 0px;}
    .mainpanel_panel .panel_body .panel_body_content_title a{float:right;z-index: 10;}
</style>
<div class="mainpanel_panel">
	<div class=" col30 " style="width:100%">
		<div class="panel_body" style="width:100%">
			<c:if test="${projectList!=null && fn:length(projectList)>0 }">
				<c:forEach items="${projectList}" var="info">
					<div class="panel_body_content" style="width: 100%;">

						<div class="panel_body_content_title" style="margin-right: 50px;">
							<div id="process" class="easyui-progressbar" style="border-radius: 24px;width: 100%; margin-right:180px;height: 15px;;background-color:#e9e9e9;display: inline-block;" data-options="value:${info.process}"></div>
							<c:if test="${info.procInstId==null}">
								<span class="data" style="display: inline-block; width:50px;color: #333; float: right;margin-right: -64px; margin-top: -16px;">
									拟制中
								</span>
							</c:if>
							<c:if test="${info.procInstId!=null}">
								<span class="data" style="display: inline-block; width:50px; float: right;margin-right: -50px; margin-top: -16px;">
                                    <c:choose>
										<c:when test="${info.bizCurrent=='EDITING'}">
											<a href="javascript:void(0)" onclick="openFlowProjects('${info.id}')" style="color: #0C60AA">拟制中</a>
										</c:when>
										<c:when test="${info.bizCurrent=='STARTING'}">
											<a href="javascript:void(0)" onclick="openFlowProjects('${info.id}')" style="color: #0C60AA">执行中</a>
										</c:when>
										<c:when test="${info.bizCurrent=='PAUSED'}">
											<a href="javascript:void(0)" onclick="openFlowProjects('${info.id}')" style="color: #0C60AA">已暂停</a>
										</c:when>
										<c:when test="${info.bizCurrent=='CLOSED'}">
											<a href="javascript:void(0)" onclick="openFlowProjects('${info.id}')" style="color: #0C60AA">已关闭</a>
										</c:when>
									</c:choose>
                                 </span>
							</c:if>
						</div>

						<div class="panel_body_content_dtl">
							<a href="javascript:void(0)" onclick="viewProjectDetail('${info.id}')" style="color: #0C60AA">${info.name}</a>
							<c:if test="${info.procInstId==null}">
								<span class="data">${info.projectManagerNames}</span>
							</c:if>
							<c:if test="${info.procInstId!=null}">
								<span class="data">${info.projectManagerNames}</span>
							</c:if>
						</div>
					</div>
				</c:forEach>
			</c:if>
		</div>
	</div>
</div>
<input id="loginName"  type="hidden" value="${loginName}">
<fd:dialog id="riskProblemDeatilDialog" width="800px" height="550px"
		   modal="true" title="{com.glaway.ids.riskproblems.project.risk.viewDetail}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="viewReviewDialog" width="940px" height="500px" modal="true" title="{com.glaway.ids.review.body.reviewLook}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="reviewStatusDialog" width="800px" height="400px" modal="true" title="评审管理工作流">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<script>

	// 查看详细事件打开窗口
	function viewProjectDetail(id) {
		var title = '项目计划';
		if (!top.$('#maintabs').tabs('exists', title)) {
			top.addTabById(title,
					"${webRoot}/projectMenuController.do?projectMenu&isIframe=true&projectId=" + id + "",
					'pictures','4028efee4c3617ec014c362eed6c0012');
		} else {
			top.$('#maintabs').tabs('close', title);
			top.addTabById(title,
					"${webRoot}/projectMenuController.do?projectMenu&isIframe=true&projectId=" + id + "",
					'pictures','4028efee4c3617ec014c362eed6c0012');
		}

	}

	// 弹出对象对应的流程
	function openFlowProjects(taskNumber) {
		var tabTitle = '项目工作流';
		var url = '${webRoot}/taskFlowCommonController.do?taskFlowList&taskNumber='
				+ taskNumber;
		createdetailwindow_close(tabTitle, url, 800, 400);
	}

</script>