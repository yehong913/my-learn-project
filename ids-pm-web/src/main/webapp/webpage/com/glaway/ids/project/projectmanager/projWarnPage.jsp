<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
<script type="text/javascript">
	function viewPlanPage(url) {
		$.fn.lhgdialog({
			content: 'url:'+url,
			width : 800,
			height : 490,
			title : '<spring:message code="com.glaway.ids.pm.project.projStatistics.planInfo"/>',
		    button : [
			{
				name:'<spring:message code="com.glaway.ids.common.btn.close"/>',
				focus : true
			}]
		});
	}
	
	$(function(){
        var documentHeight = $(window).height()-200;
		var curWidth = $("#projectwarnBrs").css("height",documentHeight +'px');
	})
</script>

<div >
<fd:panel id="projectwarnBrs" border="false" collapsible="false" style="overflow-x:hidden;"
		width="100%">
	<iframe id="projectRiskId" style="width:100%;overflow-x:hidden;height:92%;" scrolling="auto" 
		src="${brsUrl}/brs/frameset?__report=idsreport/projectwarn.rptdesign&__showtitle=false&toc=false&parameter=false&export=false&print=false&printServer=false&projectId=${param.projectId}"></iframe>
</fd:panel>
</div>