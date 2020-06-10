<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<style type="text/css">
.know_search {
	text-align: center
}

.know_search .search_text {
	width: 400px;
	height: 24px;
	font-size: 14px;
	line-height: 24px;
	vertical-align: middle;
	border: 1px solid #0C60AA;
	margin-right: 0;
	padding: 2px 4px;
}

.know_search .search_btn {
	height: 30px;
	line-height: 30px;
	vertical-align: middle;
	font-size: 14px;
	text-align: center;
	width: 80px;
	background: #0C60AA;
	border: 0;
	padding: 0;
	margin-left: 0;
	cursor: pointer;
	color: #fff;
	font-weight: bold
}

</style>

<div class="easyui-panel" fit="true">	 
 	<div class="know_search" id="older" style="padding: 3px; margin:10px; height: auto">
		<p>
			<input type="text" class="search_text" id="taskInput" /><input
				type="button" value="<spring:message code='com.glaway.ids.common.btn.retrieval'/>" class="search_btn" onclick="goSearch()" />
		</p>
	</div>
<c:if test="${isEnableFlag !='1' && plan_.bizCurrent !='FEEDBACKING'}">
	<fd:datagrid id="referenceList" fitColumns="true" pagination="true"
		url="taskDetailController.do?getReferenceList&taskId=${planId}&type=planTask"
		idField="id" fit="true" height="75%">
			<fd:colOpt title="{com.glaway.ids.common.lable.operation}" width="10" >
				<fd:colOptBtn tipTitle="{com.glaway.ids.pm.project.plan.clearreference}" iconCls="basis ui-icon-minus"
					hideOption="deleteCheck" onClick="deleteReferenceLine"></fd:colOptBtn>
			</fd:colOpt>
		<fd:dgCol title="{com.glaway.ids.pm.project.task.knowledgeName}" field="title" width="90"
			formatterFunName="knowledgeNameLink"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.task.attachmentDownloadUrl}" field="filePath" hidden="true"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.task.knowledgeCode}" field="code" hidden="true"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.task.originLibraryCode}" field="libId" hidden="true"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.task.knowledgeOnlyKey}" field="workId" hidden="true"></fd:dgCol>
	</fd:datagrid>
</c:if>
<c:if test="${isEnableFlag =='1' || plan_.bizCurrent =='FEEDBACKING'}">
	<fd:datagrid id="referenceList" fitColumns="true" pagination="true"
				 url="taskDetailController.do?getReferenceList&taskId=${planId}&type=planTask"
				 idField="id" fit="true" height="75%">
		<fd:dgCol title="{com.glaway.ids.pm.project.task.knowledgeName}" field="title"
				  formatterFunName="knowledgeNameLink"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.task.attachmentDownloadUrl}" field="filePath" hidden="true"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.task.knowledgeCode}" field="code" hidden="true"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.task.originLibraryCode}" field="libId" hidden="true"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.task.knowledgeOnlyKey}" field="workId" hidden="true"></fd:dgCol>
	</fd:datagrid>
</c:if>
</div>


	<fd:dialog id="goSearchCftctForReference2" modal="true" height="500px" width="1200px">
	</fd:dialog>

<script type="text/javascript">
function reloadKnowledgeReference(){
	$('#referenceList').datagrid('reload');
}

function searchCftctShow2(dialogUrl) {
	$('#goSearchCftctForReference2').lhgdialog("open","url:"+dialogUrl);
}

function deleteCheck(row, index){
	if('${isOwner}'=='true'
			&&('${plan_.bizCurrent}'== "ORDERED" ||'${plan_.bizCurrent}'== "EDITING")){
		//不隐藏去除按钮
		return false;
	}else{
		//隐藏去除按钮
		return true;
	}
	
	
}

function knowledgeNameLink(val,row,value){
	return '<a href="#" onclick="viewKnowledgeDetail(\'' + row.workId + '\')" style="color:blue">' + val + '</a>';
}
function viewKnowledgeDetail(itemId){
	var tabTitle = "知识详情";
	url = '/kes-klm-web/knowledgeNavigationController.do?goKnowledgeItemDetailNewForIDS&id='+ itemId + '&browser=true&isIframe=true';
	var win = window.open(url, "_blank");
	setTimeout(function () {
		win.document.title = tabTitle;
	}, 500);

/*    url = '/kes-klm-web/knowledgeNavigationController.do?goKnowledgeItemDetailNew&id='+itemId;*/
	// url = 'http://192.168.112.171/kes-klm-web/knowledgeNavigationController.do?goKnowledgeItemDetailNewForIDS&id='+ '4028f0056eedbf94016eede0d6f60008' + '&browser=true&isIframe=true';
	// var url = '/kes-klm-web/knowledgeNavigationController.do?goKnowledgeItemDetailNewForIDS&id='+ '4028f0056eedbf94016eede0d6f60008' + '&browser=true&isIframe=true';
	// top.addTabById("详情", url, 'pictures', '4028f0056eedbf94016eede0d6f60008')
/*	$("#"+'viewKnowledgeDetailDialog').lhgdialog("open","url:"+url);*/
/*     window.open( 
          url, 
          '详情', 
          'height=380,width=800,top=10,left=0,toolbar=no,menubar=no,scrollbars=yes,location=no,status=no'); */

}
function goSearch(){
	 
	var inPut = $("#taskInput").val();
	//var aa = inPut.replace(/\s/g,",");
	var inPut01 = encodeURI(encodeURI(inPut));
	if($.trim(inPut).length == 0) {
		top.tip('<spring:message code="com.glaway.ids.pm.project.task.knowledge.emptyKeyword"/>');
		return false;
	}
	else if($.trim(inPut).length > 300){
		top.tip('<spring:message code="com.glaway.ids.pm.project.task.knowledge.keywordLength"/>');
		return false;
	}			
	// url ='http://192.168.112.171/kes-klm-web/kesSearchController.do?goSearchForIDS&currentInput='+inPut01+'&httpFlag=1';
	url ='taskDetailController.do?goSearchForIDS&isEnableFlag=${isEnableFlag}&taskId=${planId}&type=planTask&searchType=knowledge&currentInput='+inPut01+'&httpFlag=1';
	searchCftctShow2(url);
	/* $("#"+'goSearchDialog').lhgdialog("open","url:"+url); */
/* 	window.open(url); */ 
}

function deleteReferenceLine(a,index){
	debugger
	$('#referenceList').datagrid('selectRow',index);
	var row = $('#referenceList').datagrid('getSelected');
	var comeFromlibId=row.libId;
	var knowledgeCode=row.code;

	url = 'taskDetailController.do?deleteReference';
	top.Alert.confirm('<spring:message code="com.glaway.ids.pm.project.task.knowledge.confirmDel"/>', function(r) {
		   if (r) {
				$.ajax({
					url : url,
					type : 'post',
					data : {
						libId :comeFromlibId,
						code :knowledgeCode,
						taskId : '${planId}',
						opType : 'filter'
					},
					cache : false,
					success : function(data) {
						debugger
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							top.tip(msg);
							$('#referenceList').datagrid('reload');
						}
					}
				});
			}
	});
}
//var url= "http://localhost:7088/ids-knowledge/knowledgeSearchController.do?goSearch&inPut=";

//formatterFunName="addLink"
function addLink(val, row, value){
	if(val!=null&&val!=''){
		return '<a  href="#" onclick="showDocDetail(\'' + row.docId + '\',this)" id="myDoc"  style="color:blue">'+val+'</a>';
	}else return ;
}

function showDocDetail(id){
		var url="projLibController.do?viewProjectDocDetail&opFlag=1&id="+id;
		createdetailwindow2('<spring:message code="com.glaway.ids.pm.project.plan.basicLine.showDocDetail"/>', url, "1000", "550");
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
					cancelVal : '<spring:message code="com.glaway.ids.common.btn.close"/>',
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
					cancelVal : '<spring:message code="com.glaway.ids.common.btn.close"/>',
					cancel : true,  
					ok : false
				});
			}
		}
	}
</script>
<fd:dialog id="viewKnowledgeDetailDialog" maxFun="true" height="500px" width="800" title="{com.glaway.ids.pm.project.plantemplate.viewDetail}" ></fd:dialog>
<fd:dialog id="goSearchDialog"></fd:dialog>
