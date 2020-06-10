<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<div class="easyui-layout" fit="true">
<script src="webpage/com/glaway/ids/project/projecttemplate/projectTemplateList.js"></script>
<script type="text/javascript">
var projTemplateGrid='#projTemplateList';



function doFunction(id,url,tipMsg){
	debugger;
	var rows=$(''+projTemplateGrid).datagrid('getRows');
	var index=$(''+projTemplateGrid).datagrid('getRowIndex',id);
	var templates = new Array();
	templates.push(rows[index]);
	doExcute(url,tipMsg,templates);
}
 
function doRemove(id){
	doFunction(id,"projTemplateController.do?doBatchDel",'<spring:message code="com.glaway.ids.pm.project.projecttemplate.confirmDel"/>');
}

function doEnable(id){
	debugger;
	doFunction(id,"projTemplateController.do?doOpenOrClose&method=open",'<spring:message code="com.glaway.ids.pm.project.projecttemplate.confirmStart"/>');
}

function doForbidden(id){
	doFunction(id,"projTemplateController.do?doOpenOrClose&method=colse",'<spring:message code="com.glaway.ids.pm.project.projecttemplate.confirmStop"/>');
} 
 
function hideDeleteForProjTemplate(row,index){
	if (row.bizCurrent == 'nizhi'){
		return false;
	} else { 
		return true; 
	}
}

function hideOperReviseForProjTemplate(row,index){
	if (row.bizCurrent == 'qiyong' || row.bizCurrent=="jinyong"){
		return false;
	} else { 
		return true; 
	}
}
function hideOperBackMinor(row,index){
	if (row.bizCurrent == 'xiuding' || row.bizCurrent == 'nizhi'){
		var p = /^[A-Z]{1}([.]1{1})?$/;
		if(!p.test(row.bizVersion)) {
		return false;
		}
		return true;
	} else { 
		return true; 
	}
}
function hideOperBackMajor(row,index){
	if (row.bizCurrent == 'xiuding'){
		var p = /^[A]/;
		if(!p.test(row.bizVersion)) {
		return false;
		}
	} else { 
		return true; 
	}
}

function hideEnableForProjTemplate(row,index){
	if(row.bizCurrent=="jinyong")
	{	
		return false;
	}else{
		return true;
	}
}

function hideForbiddenForProjTemplate(row,index){
	if(row.bizCurrent=="qiyong")
	{	
		return false;
	}else{
		return true;
	}
}

function hideCopyForProjTemplate(row,index){
	if(row.bizCurrent=="qiyong")
	{	
		return false;
	}else{
		return false;
	}
}

function hideUpdateForProjTemplate(row,index){
	if(row.bizCurrent=="nizhi" || row.bizCurrent=="xiuding")
	{	
		return false;
	}else{
		return true;
	}
}

function hideCommitForProjTemplate(row,index){
	if(row.bizCurrent == "nizhi" || row.bizCurrent == "xiuding")
	{	
		return false;
	}else{
		return true;
	}
}

function hideApprovalForProjTemplate(row,index){
	/* if(row.status=="启用")
	{	
		return false;
	}else{
		return true;
	} */
	return true;
}

function searchProjTmpl(){
	var projTmplCreator = $('#projTmplCreator').val();
	
	var queryParams=$("#projTemplateList").fddatagrid('queryParams',"seachProjTmplTag");
	queryParams["projTmplCreator"]=projTmplCreator;
			
	$('#projTemplateList').datagrid({
		url : 'projTemplateController.do?searchDatagrid',
		pageNumber : 1,
		queryParams:queryParams
	});
	
	$('#projTemplateList').datagrid('unselectAll');
	$('#projTemplateList').datagrid('clearSelections');
	$('#projTemplateList').datagrid('clearChecked');
}


function searchProjTmplBackUp(){
	var projTmplCreator = $('#projTmplCreator').val();
	var searchObjArr = $('#searchProjTmplForm').serializeArray();
	var searchParam = new Object();
	if(searchObjArr!=null && searchObjArr.length>0) {
		for(var i=0; i<searchObjArr.length; i++) {
			searchParam[searchObjArr[i].name] = searchObjArr[i].value;
			if(searchObjArr[i].name == 'Project.createByInfo.id') {
				searchParam[searchObjArr[i].name+'_condition'] = 'in';
			}
			if(searchObjArr[i].name == 'Project.epsInfo.id') {
				searchParam[searchObjArr[i].name+'_condition'] = 'in';
			} 
		}
	}
	var url=encodeURI('projTemplateController.do?searchDatagrid&projTmplCreator='+projTmplCreator);
	url=encodeURI(url);
	$('#projTemplateList').datagrid({
		url : url,
		queryParams : searchParam
	});
}

//重置
function tagSearchResetProjTmpl() {
	$('#searchProjTmplName').textbox("clear");
	$('#projTmplCreator').textbox("clear");
	//$('#searchProjTmplCreateTime').datebox("clear");
	$("#searchProjTmplCreateTime_BeginDate").datebox("clear");
	$("#searchProjTmplCreateTime_EndDate").datebox("clear");
	$('#projTmplStatus').combobox("clear");
}

function createdetailwindowfortemplate(title, url, width, height) {
	width = width ? width : 700;
	height = height ? height : 400;
	if (width == "100%" || height == "100%") {
		width = document.body.offsetWidth;
		height = document.body.offsetHeight - 100;
	}
	if (typeof (windowapi) == 'undefined') {
		
		createDialog('showDetialDialog',url);
	} else {
	}

}
function dateFormatterStr(val, row, value){
	return val.substr(0,19);
}
function doControllerTemplate(title, url, tipMsg) {
	var templates = $('#projTemplateList').datagrid('getSelections');
	if(title=="新增"){
	}
	if(title=="启用"){
		if (templates.length > 0) {
			for(var i=0;i<templates.length;i++){
				if(templates[i].bizCurrent != "jinyong"){
					tip('<spring:message code="com.glaway.ids.common.onlyStartStopped"/>');
					return;
				}
			}
		}
		else {
			tip('<spring:message code="com.glaway.ids.pm.project.projecttemplate.selectTemplate"/>');
			return;
		}
	}
	if(title=="禁用"){
		if (templates.length > 0) {
			for(var i=0;i<templates.length;i++){
				if(templates[i].bizCurrent !="qiyong"){
					tip('<spring:message code="com.glaway.ids.common.onlyStopStarted"/>');
					return;
				}
			}
		}
		else {
			tip('<spring:message code="com.glaway.ids.pm.project.projecttemplate.selectTemplate"/>');
			return;
		}
	}
	if(title=="删除"){
		var rows = $('#projTemplateList').datagrid('getSelections');
		if (rows.length > 0) { 
			for (var i = 0; i < rows.length; i++) {
				if(rows[i].bizCurrent != 'nizhi'){
					tip('<spring:message code="com.glaway.ids.pm.project.projecttemplate.onlyDelEditing"/>');
					return;
				};
			}
		}
		else {
			tip('<spring:message code="com.glaway.ids.pm.project.projecttemplate.selectTemplate"/>');
			return;
		}
	}
	if (templates == null || templates == 'undefined') {
		return templates;
	}else{
		doExcute(url, tipMsg,templates);
	}

}
function getSelectionData(){
	var chs = $(projTemplateGrid).datagrid("getChecked");
	var templates = new Array();
	if (chs == null || chs.length == 0) {
		top.tip('<spring:message code="com.glaway.ids.pm.project.projecttemplate.selectTemplate"/>');
		return;
	} 
	for ( var i = 0; i < chs.length; i++) {
		var template = chs[i];
		templates.push(template);
	}
	return templates;
}
function addTemplate(){
	var url='projTemplateController.do?goAddProjTemplate';
	$("#addTemplateDialog").lhgdialog("open", "url:"+url);
}
function addTemplateSubmit(iframe){
	debugger;
	if(iframe.saveAll("add")){
		
	}else{
		return false
	}
}
function modifyTemplateSubmit(iframe){
	debugger;
	if(iframe.saveAll("miner")){
		
	}else{
		return false
	}
}
function reviseTemplateSubmit(iframe){
	debugger;
	if(iframe.saveAll("revise")){
		
	}else{
		return false
	}
}

function modifyProjectTemplate(id, index) {
	debugger;
	var url='projTemplateController.do?goAddProjTemplate&method=miner&id='+id;
	$("#modifyTemplateDialog").lhgdialog("open", "url:"+url);
}
//修订
function reviseProjTemplate(id, index) {
	debugger;
	$('#projTemplateList').datagrid('unselectAll');
	$('#projTemplateList').datagrid('selectRow',index);
	var url='projTemplateController.do?goAddProjTemplate&method=revise&id='+id;
	$("#reviseTemplateDialog").lhgdialog("open", "url:"+url);
}

//回退：
function backMinorLine(id, index) {
	top.Alert.confirm(
        '<spring:message code="com.glaway.ids.pm.plantemplate.confirmBack"/>',
        function(r) {
            if (r) {
            	$('#projTemplateList').datagrid('unselectAll');
                $('#projTemplateList').datagrid('selectRow',index);
                var rows = $("#projTemplateList").datagrid('getSelections');
                if(rows.length >0){
                    var row  = rows[0];
					var url = 'projTemplateController.do?doBackVersion';
					$.ajax({
						url : url,
						type : 'post',
						data : {
							id : row.id,
							bizId : row.bizId,
							bizVersion : row.bizVersion,
							type : 'Min'
						},
						cache : false,
						success : function(data) {
							var d = $.parseJSON(data);
							var msg = d.msg;
							tip(msg);
							if (d.success) {
								window.setTimeout(function(){
									$("#projTemplateList").datagrid('clearSelections');
									$("#projTemplateList").datagrid('reload');
								},
										500);//等待0.5秒，让分类树结构加载出来
							}
						}
					});
                }
           }
	 });
}

//撤消：
function backMajorLine(tmp, index) {
	top.Alert.confirm(
        '<spring:message code="com.glaway.ids.pm.plantemplate.confirmRevoke"/>',
        function(r) {
            if (r) {
            	$('#projTemplateList').datagrid('unselectAll');
                $('#projTemplateList').datagrid('selectRow',index);
                var row = $('#projTemplateList').datagrid('getSelected');
                var url = 'projTemplateController.do?doBackVersion';
                $.ajax({
                    url : url,
                    type : 'post',
                    data : {
                        id : row.id,
                        bizId : row.bizId,
                        bizVersion : row.bizVersion,
                        type : 'Maj'
                    },
                    cache : false,
                    success : function(data) {
                        var d = $.parseJSON(data);
                        var msg = d.msg;
                        tip(msg);
                        if (d.success) {
                            window.setTimeout(function(){
                                $("#projTemplateList").datagrid('clearSelections');
                                $("#projTemplateList").datagrid('reload');
                            }, 500);//等待0.5秒，让分类树结构加载出来
                        }
                    }
                });
           }
    });
}

function copyProjTmp(id, index) {
	debugger;
	$('#projTemplateList').datagrid('unselectAll');
	$('#projTemplateList').datagrid('selectRow',index);
	var row = $('#projTemplateList').datagrid('getSelected');
	var rowIndex=$('#projTemplateList').datagrid('getRowIndex',row);
	var url='projTemplateController.do?goTemplateDetailAdd&templateId='+id+'&copy=copy';
	$("#copyTemplateDialog").lhgdialog("open", "url:"+url);
}

function goProrjSubmitApprove(id, index) {
	debugger;
	$('#projTemplateList').datagrid('unselectAll');
	$('#projTemplateList').datagrid('selectRow',index);
	var row = $('#projTemplateList').datagrid('getSelected');
	var rowIndex=$('#projTemplateList').datagrid('getRowIndex',row);
	var url='projTemplateController.do?goSubmitApprove&projTmpId='+id;
	$("#goProjTmpSubmitApprove").lhgdialog("open", "url:"+url);
}

function startPTmpProcess(iframe){
	debugger;
	iframe.startProjProcess();
	return false;
}

function copyTemplateSubmit(iframe){
	var name = iframe.$("#projTmplName").val();
	var remark = iframe.$("#remark").val();
	var id = iframe.$("#templateId").val();
	if(undefined == name || null == name || "" == name) {
		top.tip('<spring:message code="com.glaway.ids.pm.project.projecttemplate.nameNoEmpty"/>');
		return false;
	}
	if(undefined == remark || null == remark || "" == remark) {
		remark = "";
	}
	if(!vidateLength(name,30)){
		top.tip('<spring:message code="com.glaway.ids.pm.project.projecttemplate.nameLength"/>');
		return false;
	}
	if(!vidateLength(remark,200)){
		top.tip('<spring:message code="com.glaway.ids.pm.project.projecttemplate.remarkLength"/>');
		return false;
	}
	$.ajax({
		url: 'projTemplateController.do?checkTemplateNameRepeat&type=copy&name='+name,
		type : 'post',
		data : {
			name : name,
			templateId : id
		},
		success : function(data){
			var d= $.parseJSON(data)
			debugger;
			if(d.success){
				$.ajax({
					url : 'projTemplateController.do?doCopyProjectTemplate',
					type : 'post',
					data : {
						name : name,
						remark : remark,
						templateId : id
					},
					cache : false,
					success : function(data) {
						debugger;
						var d = $.parseJSON(data);
						if (d.success) {
							tip(d.msg);
							var win = iframe.$.fn.lhgdialog("getSelectParentWin");
							win.$("#projTemplateList").datagrid('reload');
							var url='projTemplateController.do?goAddProjTemplate&id='+d.obj.persientId;
							iframe.$("#modifyTemplateCopyDialog").lhgdialog("open","url:"+url)
							iframe.$.fn.lhgdialog("closeAll");
						}
					}
				});
			}else {
				top.tip(d.msg);
			}
		}
	});		
	return false;
}
function formatterVersion(val, row, index) {
		return '<span style="cursor:hand"><a onclick="showBizVersion(\''
		+ row.id + '\')" style="color:blue;">' + val
		+ '</a></span>';
}
function showBizVersion(id) {
	url = 'projTemplateController.do?goVersionHistory&id=' + id;
	createDialog('viewHistoryDialog', url);
}

function formatterRemarkProjectTemp(value, row, index) {
	if (value == undefined)
        value = '';
    var shtml;
    shtml = '<span title="'+value+'">' + value + '</span>';
    return shtml;
}
</script>
	<div region="center" style="padding: 1px;">
	 <div id="projTemplateTb">
			<fd:searchform id="seachProjTmplTag" onClickSearchBtn="searchProjTmpl()" onClickResetBtn="tagSearchResetProjTmpl()" help="helpDoc:ProjectTemplateManagement">
                <fd:inputText title="{com.glaway.ids.pm.projecttemplate.name}" name="ProjTemplate.projTmplName" id="searchProjTmplName" queryMode="like"/>
                <fd:inputText title="{com.glaway.ids.pm.project.projecttemplate.creator}" id="projTmplCreator" />
                <%--<fd:inputDateTime name="ProjTemplate.createTime" title="创建时间" id="searchProjTmplCreateTime" queryMode="le&&ge"></fd:inputDateTime>--%>
                <fd:inputDateRange id="searchProjTmplCreateTime" interval="1" title="{com.glaway.ids.pm.project.projecttemplate.createTime}" name="ProjTemplate.createTime" opened="0"></fd:inputDateRange>
                <fd:combobox name="ProjTemplate.bizCurrent" id="projTmplStatus" title="{com.glaway.ids.pm.project.projecttemplate.status}" editable="false" textField="text"  valueField="id" data="qiyong_启用,jinyong_禁用,nizhi_拟制中,shenhe_审批中,xiuding_修订中" prompt="全部" queryMode="eq"></fd:combobox>
			</fd:searchform>
			
			<fd:toolbar >
				<fd:toolbarGroup align="left">
				<!-- operationCode="addProjTemplate" -->
					<fd:linkbutton id ="addProjTemplate" operationCode="addProjTemplate"
						onclick="addTemplate()"
						value="{com.glaway.ids.common.btn.create}" iconCls="basis ui-icon-plus"/>
					<fd:linkbutton id ="deleteProjTemplate" operationCode="deleteProjTemplate"
						onclick="doControllerTemplate('删除','projTemplateController.do?doBatchDel','确定删除选中的模板吗?')"
						value="{com.glaway.ids.common.btn.remove}" iconCls="basis ui-icon-minus"/>
					<fd:linkbutton id ="startProjTemplate" operationCode="enableProjTemplate"
						onclick="doControllerTemplate('启用','projTemplateController.do?doOpenOrClose&method=open','确定启用选中的模板吗?')"
						value="{com.glaway.ids.common.btn.start}" iconCls="basis ui-icon-enable" />	
					<fd:linkbutton id ="stopProjTemplate" operationCode="forbiddenProjTemplate"
						onclick="doControllerTemplate('禁用','projTemplateController.do?doOpenOrClose&method=close','确定禁用选中的模板吗?')"
						value="{com.glaway.ids.common.btn.stop}" iconCls="basis ui-icon-forbidden" />	
				</fd:toolbarGroup>
			</fd:toolbar>
		</div>
		
		<fd:datagrid checkbox="true" checked="true" idField="id"  fit="true" fitColumns="true"
			checkOnSelect="true" id="projTemplateList" pagination="true"
			url="projTemplateController.do?searchDatagrid&field=id" toolbar="projTemplateTb">
			<fd:colOpt title="{com.glaway.ids.common.lable.operation}" width="100">
	             <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.remove}" iconCls="basis ui-icon-minus" hideOption="hideDeleteForProjTemplate"  onClick="doRemove" operationCode="deleteProjTemplate"></fd:colOptBtn> 
	             <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.modify}" iconCls="basis ui-icon-pencil" hideOption="hideUpdateForProjTemplate"  onClick="modifyProjectTemplate" operationCode="updateProjTemplate"></fd:colOptBtn>
	             <fd:colOptBtn tipTitle="com.glaway.ids.common.btn.copy" iconCls="basis ui-icon-copy" hideOption="hideCopyForProjTemplate"  onClick="copyProjTmp" operationCode="copyProjTemplate"></fd:colOptBtn>
	             <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.start}" iconCls="basis ui-icon-enable" hideOption="hideEnableForProjTemplate"  onClick="doEnable" operationCode="enableProjTemplate"></fd:colOptBtn> 
	             <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.stop}" iconCls="basis ui-icon-forbidden" hideOption="hideForbiddenForProjTemplate"  onClick="doForbidden" operationCode="forbiddenProjTemplate"></fd:colOptBtn> 
	             <fd:colOptBtn tipTitle="回退" iconCls="basis ui-icon-return" hideOption="hideOperBackMinor" onClick="backMinorLine" operationCode="minBackProjTemplate"></fd:colOptBtn>
				 <fd:colOptBtn tipTitle="撤消" iconCls="basis ui-icon-revoke" hideOption="hideOperBackMajor()" onClick="backMajorLine" operationCode="majBackProjTemplate"></fd:colOptBtn>
	             <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.revise}" iconCls="basis ui-icon-revise" hideOption="hideOperReviseForProjTemplate" onClick="reviseProjTemplate" operationCode="reviseProjTemplate"></fd:colOptBtn>
	             <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.approve}" iconCls="basis ui-icon-submitted_approval" hideOption="hideCommitForProjTemplate"  onClick="goProrjSubmitApprove" operationCode="approveProjTemplate"></fd:colOptBtn>
	            <%--  <fd:colOptBtn tipTitle="废弃" iconCls="basis ui-icon-trash" hideOption="hideApproval"  onClick="goPlanSubmitApprove" operationCode=""></fd:colOptBtn>
	             <fd:colOptBtn tipTitle="变更" iconCls="ui-icon-planchange" hideOption="hideApprovalForProjTemplate"  onClick="goPlanSubmitApprove" operationCode=""></fd:colOptBtn> --%>
            </fd:colOpt>
			<fd:dgCol title="{com.glaway.ids.pm.projecttemplate.name}" field="projTmplName" formatterFunName="viewTemplate"/>
			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.version}" field="bizVersion" width="100" align="left"
				sortable="true" formatterFunName="formatterVersion" />
			<fd:dgCol title="{com.glaway.ids.pm.project.projecttemplate.remark}" field="remark" width="200" formatterFunName="formatterRemarkProjectTemp"/>
			<fd:dgCol title="{com.glaway.ids.pm.project.projecttemplate.creator}" field="creator" formatterFunName="userFormatter" />
			<fd:dgCol title="{com.glaway.ids.pm.project.projecttemplate.createTime}" field="createTime" formatterFunName="dateFormatterStr" />
			<fd:dgCol title="{com.glaway.ids.pm.project.projecttemplate.status}" field="status" formatterFunName="formatOp"/>
		</fd:datagrid>
		
		<fd:dialog id="showDetialDialog" width="1000px" height="500px" modal="true"
		title="{com.glaway.ids.pm.project.projecttemplate.templateDetail}">
		<fd:dialogbutton name="关闭" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="addTemplateDialog" width="1000px" height="500px"
			modal="true" title="新增项目模板">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addTemplateSubmit"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="modifyTemplateDialog" width="1000px" height="500px"
			modal="true" title="修改项目模板">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="modifyTemplateSubmit"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="reviseTemplateDialog" width="1000px" height="500px"
			modal="true" title="修订项目模板">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="reviseTemplateSubmit"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="copyTemplateDialog" width="800px" height="340px"
			modal="true" title="复制项目模板">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="copyTemplateSubmit"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="viewHistoryDialog" width="730px" height="530px" modal="true" title="{com.glaway.ids.pm.projtemplate.viewHistoryVersion}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.close}"
				callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="goProjTmpSubmitApprove" width="490px" height="120px" modal="true" title="{com.glaway.ids.pm.project.plan.toApprove}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="startPTmpProcess"></fd:dialogbutton>
	 		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
	</div>
</div>
 
<style>
.myDisableImg{
filter:alpha(opacity=40);
opacity:0.4;
margin-right:10px
}
.myAbleImg{
margin-right:10px;
}
</style>


