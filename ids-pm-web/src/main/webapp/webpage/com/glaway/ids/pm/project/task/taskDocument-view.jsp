<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-panel" fit="true">
 	<input id="documentStatusList" name="documentStatusList" type="hidden" value="${documentStatusList}" />
	<fd:datagrid id="deliverablesInfoList" checkbox="false" fitColumns="true"
		url="taskDetailController.do?getDocRelationList&id=${deliverablesInfo_.useObjectId}" 
		toolbar="#tagListtb" idField="deliverableId" fit="true">
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.deliverableName}" field="deliverableName" width="120"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.rdtask.flowResolve.document}" field="docName" width="120" formatterFunName="addLink"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.bizVersion}" field="version" width="120" ></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.common.lable.status}" field="status" width="100"  />
	</fd:datagrid>	
</div>
<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
<script type="text/javascript">

$(function(){
	
})


	// 生命周期状态name、title转换
	function viewDocBizCurrent(val, row, value) {
		var statusList = eval($('#documentStatusList').val());
		for (var i = 0; i < statusList.length; i++) {
			if (val == statusList[i].name) {
				return statusList[i].title;
			}
		}
	}
	
function addLink(val, row, value){
	if(val!=null&&val!=''){
		if((row.havePower == true || row.havePower == 'true') && '${userLevel}' >= row.securityLevel){
			return "<a href='#' onclick='showDocDetail(\""
			+ row.docId
			+ "\""
			+ ','
			+ "\""
			+ row.download
			+ "\""
			+ ','
			+ "\""
			+ row.detail
			+ "\")'  id='myDoc'  style='color:blue'>" + val +"</a>";
			/* return '<a  href="#" onclick="showDocDetail(\'' + row.docId + '\',this)" id="myDoc"  style="color:blue">'+val+'</a>'; */
		}else{
			return val;
		}
		
	}else return ;
	
}
		
function showDocDetail(id,download,detail){
	var url="projLibController.do?viewProjectDocDetail&opFlag=1&id="+id+ "&download=" + download+ "&detail=" + detail;
	createdetailwindow2('<spring:message code="com.glaway.ids.pm.project.plan.basicLine.showDocDetail"/>', url);
}

function createdetailwindow2(title, url, width, height) {
	width = width ? width : 880;
	height = height ? height : 550;	
	if (width == "100%" || height == "100%") {
		width = document.body.offsetWidth;
		height = document.body.offsetHeight - 100;
	}
	if (typeof (windowapi) == 'undefined') {
		$.fn.lhgdialog({
			content : 'url:' + url,
			lock : true,
			width : width,
			height : height,
			button : [{name:'<spring:message code="com.glaway.ids.common.btn.close"/>',focus : true}],
			title : title,
			opacity : 0.3,
			cache : false/* ,
			cancelVal : '关闭',
			cancel : true, 
			ok : false */
		});
	} else {
		$.fn.lhgdialog({
			content : 'url:' + url,
			lock : true,
			width : width,
			height : height,
			parent : windowapi,
			title : title,
			button : [{name:'<spring:message code="com.glaway.ids.common.btn.close"/>',focus : true}],
			opacity : 0.3,
			cache : false/* ,
			cancelVal : '关闭',
			cancel : true, 
			ok : false */
		});
	}
}
</script>