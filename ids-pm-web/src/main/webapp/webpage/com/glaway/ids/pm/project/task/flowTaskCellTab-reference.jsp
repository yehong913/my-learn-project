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
	border: 2px solid #39c;
	margin-right: 0;
	padding: 2px 4px;
}

.know_search .search_btn {
	height: 32px;
	line-height: 32px;
	vertical-align: middle;
	font-size: 14px;
	text-align: center;
	width: 80px;
	background: #39c;
	border: 0;
	padding: 0;
	margin-left: 0;
	cursor: pointer;
	color: #fff;
	font-weight: bold
}

</style>
<div class="easyui-layout" id="document" fit="true">
	<input type="hidden" id ='isEnableFlag' name='isEnableFlag' value="${isEnableFlag}"/>
	<div class="know_search" id="older">
		<p>
			<input type="text" class="search_text" id="input" />
			<input type="button" value="<spring:message code='com.glaway.ids.common.btn.retrieval'/>" class="search_btn" onclick="goSearch()"/>
		</p>
	</div>
	<div style="height:230px">
		<fd:datagrid id="referenceList" fitColumns="true"  pagination="true" onLoadSuccess="hideOperateAreaColumn11"
			url="taskDetailController.do?getReferenceList&taskId=${taskId}&type=flowResolve" idField="id" fit="true">
        	<fd:dgCol title="{com.glaway.ids.common.lable.operation}" field="deleteReferenceOperate" formatterFunName="deleteReferencefun" width="60"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.task.knowledgeName}" field="title" width="120" formatterFunName="knowledgeNameLink"></fd:dgCol>
        	<fd:dgCol title="{com.glaway.ids.pm.project.task.attachmentDownloadUrl}" field="filePath" hidden="true"></fd:dgCol>
        	<fd:dgCol title="{com.glaway.ids.pm.project.task.knowledgeCode}" field="code" hidden="true"></fd:dgCol>
        	<fd:dgCol title="{com.glaway.ids.pm.project.task.originLibraryCode}" field="libId" hidden="true"></fd:dgCol>
        	<fd:dgCol title="{com.glaway.ids.pm.project.task.knowledgeOnlyKey}" field="workId" hidden="true"></fd:dgCol>
		</fd:datagrid>		
	</div>
</div>
     
	<fd:dialog id="goSearchCftctForReference2" modal="true" height="500px" width="1200px" zIndex="9999" title="检索">
	</fd:dialog>
<script type="text/javascript">
function searchCftctShow2(dialogUrl) {
	$('#goSearchCftctForReference2').lhgdialog("open","url:"+dialogUrl);
}
function knowledgeNameLink(val,row,value){
	return '<a href="#" onclick="viewKnowledgeDetail(\'' + row.libId + '\',\'' + row.code + '\')" style="color:blue">' + val + '</a>';
}
function viewKnowledgeDetail(library,code){
	/* top.tip('${openUrlView}'); */
	url = '${openUrlView}&library='+library+'&code='+code + '&isEnableFlag='+ $("#isEnableFlag").val(); 
/*  	$("#"+'viewKnowledgeDetailFtct').lhgdialog("open","url:"+url); */
	parent.knowledgeDetailCftctShow(url);  
}
function goSearch(){
	
	var inPut = $("#input").val();
	//var aa = inPut.replaceAll(" ",",");
	inPut01 = encodeURI(encodeURI(inPut));
	if($.trim(inPut).length == 0) {
		top.tip('<spring:message code="com.glaway.ids.pm.project.task.knowledge.emptyKeyword"/>');
		return false;
	}
	else if($.trim(inPut).length > 300){
		top.tip('<spring:message code="com.glaway.ids.pm.project.task.knowledge.keywordLength"/>');
		return false;
	}
	url ='${openUrl}&inPut='+inPut01+'&httpFlag=1&type=flowResolve&taskId=${taskId}' + '&isEnableFlag='+ $("#isEnableFlag").val();
/* 	$("#"+'goSearchFtct').lhgdialog("open","url:"+url); */
	 /* window.open(url);  */ 

	searchCftctShow2(url);
}

function reloadKnowledgeReference(){
	$('#referenceList').datagrid('reload');
}

//自定义行显示
function deleteReferencefun(val, row, index) {
	var returnStr = '';
	 if(row.type == 'flowTemplate'){
		
	}else{
		returnStr = '<a class="basis ui-icon-minus" style="display:inline-block;cursor:pointer;" onClick="deleteReferenceLine(\''
	        + index + '\')" title="<spring:message code='com.glaway.ids.common.btn.remove'/>"></a>';
	}
	return returnStr;
}


function deleteReferenceLine(index){
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
						taskId : '${taskId}'
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							top.tip(msg);
							reloadKnowledgeReference();
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
		createdetailwindow2("<spring:message code='com.glaway.ids.pm.project.plan.basicLine.showDocDetail'/>", url, "1000", "550");
}
	
function createdetailwindow2(title, url, width, height) {
	width = width ? width : 700;
	height = height ? height : 400;
	if (width == "100%" || height == "100%") {
		width = document.body.offsetWidth;
		height = document.body.offsetHeight - 100;
	}
	createDialogBySize('showDocDetailFlowTaskReferenceDialog',url,width,heigth,title);
}
	
function hideOperateAreaColumn11(){
	if("1" == $("#isEnableFlag").val()){
		$('#referenceList').datagrid('hideColumn', 'deleteReferenceOperate');
	}
}
</script>
	<fd:dialog id="showDocDetailFlowTaskReferenceDialog" modal="true">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>