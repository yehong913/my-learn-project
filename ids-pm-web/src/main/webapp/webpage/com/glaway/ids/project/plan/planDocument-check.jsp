<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-panel" fit="true">
	<fd:datagrid id="deliverablesInfoList" checkbox="false" fitColumns="true" pagination="false"
			url="deliverablesInfoController.do?listView&useObjectId=${deliverablesInfo_.useObjectId}&useObjectType=${deliverablesInfo_.useObjectType}" 
			toolbar="#tagListtb" idField="id" fit="true">
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.deliverableName}" field="deliverableName" width="120" sortable="false"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.docName}" field="docName" width="120" formatterFunName="addLink"  sortable="false"></fd:dgCol>
	</fd:datagrid>	
</div>
<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
	<fd:dialog id="openAndSubmitDialog" width="870px" height="600px" modal="true"
			title="{com.glaway.ids.pm.project.plan.deliverables.docName}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
<script type="text/javascript">
	$(document).ready();
	
	function addLink(val, row, value){
		if(val!=null&&val!=''){
			if((row.havePower == true || row.havePower == 'true')&& '${userLevel}' >= row.securityLevel){
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
		createDialog('openAndSubmitDialog',url);
		//createdetailwindow2("文档详情", url, "1000", "550");
	}
	
	
	function createdetailwindow2(title, url, width, height) {
		width = width ? width : 700;
		height = height ? height : 400;
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
				title : title,
				opacity : 0.3,
				cache : false,
				cancelVal : '关闭',
				cancel : true, 
				ok : false
			});
		} else {
			$.fn.lhgdialog({
				content : 'url:' + url,
				lock : true,
				width : width,
				height : height,
				parent : windowapi,
				title : title,
				opacity : 0.3,
				cache : false,
				cancelVal : '关闭',
				cancel : true,
				ok : false
			});
		}
	}
	
	
	
</script>