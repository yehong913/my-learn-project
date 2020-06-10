<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-panel" fit="true">
	<fd:datagrid id="inputsList" checkbox="false" fitColumns="true" pagination="false"
		url="planTemplateController.do?inputsList&useObjectId=${useObjectId}&planTemplateId=${planTemplateId}&useObjectType=${useObjectType}" 
		toolbar="#tagListtb" idField="id" fit="true">
		<fd:dgCol title="{com.glaway.ids.common.lable.name}" field="name" width="120"  sortable="false"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.docName}" field="docNameShow" width="120" formatterFunName="addLink"  sortable="false"></fd:dgCol>
		<%--<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.orgin}" field="originObjectName" width="120"  sortable="false"></fd:dgCol>--%>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.orgin}" field="originPath" formatterFunName = "showOrigin" width="120"  sortable="false"></fd:dgCol>
	</fd:datagrid>	
</div>
<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
	<fd:dialog id="openAndSubmitDialog" width="870px" height="600px" modal="true"
			title="{com.glaway.ids.pm.project.plan.basicLine.showDocDetail}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
<script type="text/javascript">
	$(document).ready();
	
	/* function addLink(val, row, value){
		if(val!=null&&val!=''){
			if((row.havePower == true || row.havePower == 'true')&& '${userLevel}' >= row.securityLeve){
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
			}else{
				return val;
			}
		}else return ;
		
	} */
	
	
	function showOrigin(val, row, value){
		if(row.originType == "LOCAL"){
			return "本地文档";
		}else{
			return val;
		}
	}
	
	function addLink(val, row, value){
		if(val != null && val != '' && val != 'undefined') {
			if(row.originType == 'LOCAL'){
	            return '<a  href="#" onclick="importDoc(\'' + row.docId + '\',\'' + row.docNameShow + '\')" id="myDoc"  style="color:blue">'+val+'</a>';
	        }else if(row.originType == 'PROJECTLIBDOC'){
	            var path = "<a  href='#' title='查看' style='color:blue' onclick='openProjectDoc1(\""+ row.docIdShow+ "\",\""+ row.docName+ "\",\""+ row.ext1+"\",\""+ row.ext2+"\")'>"
	                + row.docNameShow
	                + "</a>"
	            if (row.ext3 == false || row.ext3 == 'false') {
	                path = row.docNameShow;
	            }
	            return path; 
	        }else if(row.originType == 'PLAN'){
	            var path = "<a  href='#' title='查看' style='color:blue' onclick='openProjectDoc1(\""+ row.docId+ "\",\""+ row.docNameShow+ "\",\""+ row.ext1+"\",\""+ row.ext2+"\")'>"
	                + row.docNameShow
	                + "</a>";
	            if (row.ext3 == false || row.ext3 == 'false') {
	                path = row.docNameShow;
	            }
	            return path; 
	        }
		}
	}
	
	function importDoc(filePath,fileName){
		window.location.href = encodeURI('jackrabbitFileController.do?fileDown&fileName='+fileName+'&filePath=' + filePath);
	}
	
	function openProjectDoc1(id, name,download,history) {
		if (download == false || download == 'false') {
			download = "false";
		}
		if (history == false || history == 'false') {
			history = "false";
		}
		var url = "projLibController.do?viewProjectDocDetail&id=" + id
				+ "&download=" + download + "&history=" + history;
		createdetailwindow("文档详情", url, "870", "580")
	}
	
	
	function showDocDetail(id,download,detail) {
		var url="projLibController.do?viewProjectDocDetail&opFlag=1&id="+id+ "&download=" + download+ "&detail=" + detail;
		createDialog('openAndSubmitDialog', url);
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