<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>文档类型设置列表</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
    <script type="text/javascript">
    
        function addRepFileTypeConfig(){
            var dialogUrl = 'repFileTypeConfigController.do?goAddRepFileTypeConfig&type=add';
            createDialog('addRepFileTypeConfigDialog', dialogUrl);
        }
        
        function doRepFileTypeConfigAdd(iframe){
            iframe.submitRepFileTypeAdd();
            return false;
        }
        
        function viewRepFileTypeConfigDetail(val,row,value){
            return '<a href="#" onclick="repFileTypeConfigDetailShow(\''
                + row.id + '\')"><font color=blue>'+row.fileTypeName+'</font></a>';
        }
                
        function repFileTypeConfigDetailShow(id){
        	var dialogUrl = 'repFileTypeConfigController.do?goCheckRepFileTypeConfig&type=view&id='+id;
            createDialog('viewRepFileTypeConfigDialog', dialogUrl);
        }
        
        function repFileTypeConfigUpdate(id,index){
            var row = $("#repFileTypeConfigList").datagrid("getRows")[index];
            var dialogUrl = 'repFileTypeConfigController.do?goAddRepFileTypeConfig&type=update&id='+row.id;
            createDialog('updateRepFileTypeConfigDialog', dialogUrl);
        }
        
        
        function batchDelRepFileTypeConfig(tmp,index){
            debugger;
            var rows = $("#repFileTypeConfigList").datagrid('getSelections');
            var flag=true;
            var ids = [];
            if (rows.length > 0) {      
                for (var i = 0; i < rows.length; i++) {
                    if(rows[i].status!="-1")
                    {
                        flag=false;
                        break;
                    }
                    ids.push(rows[i].id);
                }
                if(flag==false){
                    tip('<spring:message code="com.glaway.ids.pm.config.repFileTypeConfig.deleteCheck"/>');
                    return false;
                }
                
                top.Alert.confirm('<spring:message code="com.glaway.ids.pm.config.repFileTypeConfig.confirmBatchDel"/>', function(r) {           
                    if (r) {
                        $.ajax({
                            url : 'repFileTypeConfigController.do?doDelRepFileTypeConfig',
                            type : 'post',
                            data : {
                                ids : ids.join(',')
                            },
                            cache : false,
                            success : function(data) {  
                                var d = $.parseJSON(data);
                                if (d.success) {
                                    var msg = d.msg;
                                    tip(msg);
                                    reloadTable();
                                    $("#repFileTypeConfigList").datagrid('unselectAll');
                                    ids = '';
                                }
                            }
                        });
                    }
                });
                
            }else{
                top.tip('<spring:message code="com.glaway.ids.pm.config.repFileTypeConfig.selectDeleteData" />');
            }
        }
        
        function delRepFileTypeConfig(tmp,index){
            var row = $("#repFileTypeConfigList").datagrid("getRows")[index];
            top.Alert.confirm('<spring:message code="com.glaway.ids.pm.config.repFileTypeConfig.confirmBatchDel"/>', function(r) {           
                if (r) {
                    $.ajax({
                        url : 'repFileTypeConfigController.do?doDelRepFileTypeConfig',
                        type : 'post',
                        data : {
                            ids : row.id
                        },
                        cache : false,
                        success : function(data) {  
                            var d = $.parseJSON(data);
                            if (d.success) {
                                var msg = d.msg;
                                tip(msg);
                                reloadTable();
                                $("#repFileTypeConfigList").datagrid('unselectAll');
                                ids = '';
                            }
                        }
                    });
                }
            });
        }
        
        function batchRepFileTypeConfigStatusChange(type){
            debugger;
            var rows = $("#repFileTypeConfigList").datagrid('getSelections');
            var flag=true;
            var ids = [];
            if (rows.length > 0) {
                if(type == 'enable'){
                    for (var i = 0; i < rows.length; i++) {
                        if(rows[i].status=="1")
                        {
                            flag=false;
                            break;
                        }
                        ids.push(rows[i].id);
                    }
                    if(flag==false){
                        tip('<spring:message code="com.glaway.ids.pm.config.repFileTypeConfig.enableCheck"/>');
                        return false;
                    }
                    
                    top.Alert.confirm('<spring:message code="com.glaway.ids.pm.config.repFileTypeConfig.confirmEnable"/>', function(r) {             
                        if (r) {
                            $.ajax({
                                url : 'repFileTypeConfigController.do?doChangeRepFileTypeConfigStatus',
                                type : 'post',
                                data : {
                                    ids : ids.join(','),
                                    type : type
                                },
                                cache : false,
                                success : function(data) {  
                                    var d = $.parseJSON(data);
                                    if (d.success) {
                                        var msg = d.msg;
                                        tip(msg);
                                        reloadTable();
                                        $("#repFileTypeConfigList").datagrid('unselectAll');
                                        ids = '';
                                    }
                                }
                            });
                        }
                    });
                    
                }else if(type == 'disable'){
                    
                    for (var i = 0; i < rows.length; i++) {
                        if(rows[i].status!="1")
                        {
                            flag=false;
                            break;
                        }
                        ids.push(rows[i].id);
                    }
                    if(flag==false){
                        tip('<spring:message code="com.glaway.ids.pm.config.repFileTypeConfig.disableCheck"/>');
                        return false;
                    }
                    
                    top.Alert.confirm('<spring:message code="com.glaway.ids.pm.config.repFileTypeConfig.confirmDisable"/>', function(r) {            
                        if (r) {
                            $.ajax({
                                url : 'repFileTypeConfigController.do?doChangeRepFileTypeConfigStatus',
                                type : 'post',
                                data : {
                                    ids : ids.join(','),
                                    type : type
                                },
                                cache : false,
                                success : function(data) {  
                                    var d = $.parseJSON(data);
                                    if (d.success) {
                                        var msg = d.msg;
                                        tip(msg);
                                        reloadTable();
                                        $("#repFileTypeConfigList").datagrid('unselectAll');
                                        ids = '';
                                    }
                                }
                            });
                        }
                    });
                }
            }else{
                if(type == 'enable'){
                    top.tip('<spring:message code="com.glaway.ids.pm.config.repFileTypeConfig.selectEnableData" />');
                }else if(type == 'disable'){
                    top.tip('<spring:message code="com.glaway.ids.pm.config.repFileTypeConfig.selectDisableData" />');
                }
                
            }
        }
        
        function repFileTypeConfigStatusChange(tmp,index,type){
            var row = $("#repFileTypeConfigList").datagrid("getRows")[index];
            
            
            if(type == 'enable'){
                
                top.Alert.confirm('<spring:message code="com.glaway.ids.pm.config.repFileTypeConfig.confirmEnable"/>', function(r) {             
                    if (r) {
                        $.ajax({
                            url : 'repFileTypeConfigController.do?doChangeRepFileTypeConfigStatus',
                            type : 'post',
                            data : {
                                ids : row.id,
                                type : type
                            },
                            cache : false,
                            success : function(data) {  
                                var d = $.parseJSON(data);
                                if (d.success) {
                                    var msg = d.msg;
                                    tip(msg);
                                    reloadTable();
                                    $("#repFileTypeConfigList").datagrid('unselectAll');
                                    ids = '';
                                }
                            }
                        });
                    }
                });
                
            }else if(type == 'disable'){
                
                top.Alert.confirm('<spring:message code="com.glaway.ids.pm.config.repFileTypeConfig.confirmDisable"/>', function(r) {            
                    if (r) {
                        $.ajax({
                            url : 'repFileTypeConfigController.do?doChangeRepFileTypeConfigStatus',
                            type : 'post',
                            data : {
                                ids : row.id,
                                type : type
                            },
                            cache : false,
                            success : function(data) {  
                                var d = $.parseJSON(data);
                                if (d.success) {
                                    var msg = d.msg;
                                    tip(msg);
                                    reloadTable();
                                    $("#repFileTypeConfigList").datagrid('unselectAll');
                                    ids = '';
                                }
                            }
                        });
                    }
                });
            }           
        }
        
        function repFileTypeConfigSet(id,index){
            var row = $("#repFileTypeConfigList").datagrid("getRows")[index];
            var fileTypeName = row.fileTypeName;
            var dialogUrl = 'repFileTypeConfigController.do?goSetRepFileTypeConfig&id='+row.id+'&fileTypeName='+fileTypeName;
            createDialog('setRepFileTypeConfigDialog', dialogUrl);
            
        }
        
        function doRepFileTypeConfigSet(iframe) {
            iframe.closeCurDialog();
            return false;
        }
        
        function hideRepFileTypeConfigUpdateBtn(row,index){
            if(row.status == "0"){
                return true;
            }
            return false;
        }
        
        function hideRepFileTypeConfigDelBtn(row,index){
            if(row.status == "-1"){
                return false;
            }
            return true;
        }
        
        function hideRepFileTypeConfigSetBtn(row,index){
            if(row.status == "0"){
                return true;
            }
            return false;
        }
        
        function hideRepFileTypeConfigEnableBtn(row,index){
            if(row.status == "1"){
                return true;
            }
            return false;
        }
        
        function hideRepFileTypeConfigDisableBtn(row,index){
            if(row.status == "1"){
                return false;
            }
            return true;
        }

        function statusFormat(val,row,index){
            debugger
            if(val == '1'){
                return '启用';
            }else if(val == '0'){
                return '禁用';
            }else if(val == '-1'){
                return '拟制中';
            }
        }
        
        /*
        * 交付项选择文档类型
        */
        function getSelectedDocRow() {
            var ids = [];
            var names = [];
            var rows = $("#repFileTypeConfigList").datagrid('getSelections');
            if (rows.length == 0) {
                top.tip('<spring:message code="com.glaway.ids.common.selectOperate"/>');
                return;
            }
            if (rows.length > 0) {
                for (var i = 0; i < rows.length; i++) {
                    ids.push(rows[i].id);
                    names.push(rows[i].fileTypeName);
                }
            }
            var win =$.fn.lhgdialog("getSelectParentWin");
            win.$("#addDeliveryStandard_docTypeId").val(ids);
            win.$('#addDeliveryStandard_docTypeName').textbox("setValue", names);
            $.fn.lhgdialog("closeSelect");
        }
        
        //导出Excel
        function exportRepFileTypeConfig(url) {
        	var fileTypeCode = $("#fileTypeCode").val();
        	var fileTypeName = $("#fileTypeName").val();
        	url = url + "&fileTypeCode=" + fileTypeCode + "&fileTypeName=" + fileTypeName;
        	window.location.href = url;
        }
        
        /*
         * 交付项选择文档类型
         */
         function getSelectedDocRowAndShowInfo() {
             var ids = [];
             var names = [];
             var rows = $("#repFileTypeConfigList").datagrid('getSelections');
             if (rows.length == 0) {
                 top.tip('<spring:message code="com.glaway.ids.common.selectOperate"/>');
                 return;
             }
             if (rows.length > 0) {
                 for (var i = 0; i < rows.length; i++) {
                     ids.push(rows[i].id);
                     names.push(rows[i].fileTypeName);
                 }
             }
             var win =$.fn.lhgdialog("getSelectParentWin");
             win.$("#addDeliveryStandard_docTypeId").val(ids);
             win.$('#addDeliveryStandard_docTypeName').textbox("setValue", names);
             debugger;
             win.userDefinedInfo(ids);
     /*         $.fn.lhgdialog("closeSelect"); */
         }
        
        function colseSelectForPage(){
        	var win =$.fn.lhgdialog("getSelectParentWin");
        	$.fn.lhgdialog("closeSelect");
        }
        
        function reloadRepFileTypeConfigList(){
        	$('#repFileTypeConfigList').datagrid('unselectAll');
        	$('#repFileTypeConfigList').datagrid("reload");
        }
        
    </script>
    <div id = "repFileTypeConfigInfo">
	    <div style="float: right; margin-right: 10px;">
		   <fd:helpButton help="helpDoc:DocumentType"></fd:helpButton>
	    </div>
        <fd:searchform id="repFileTypeConfigSearchForm" onClickSearchBtn="searchGrid('repFileTypeConfigList')" 
        onClickResetBtn="searchResetGrid('repFileTypeConfigList')" isMoreShow="false">
            <fd:inputText title="{com.glaway.ids.pm.config.repFileTypeConfig.fileTypeCode}" name="fileTypeCode" id="fileTypeCode" queryMode="like"/>
            <fd:inputText title="{com.glaway.ids.pm.config.repFileTypeConfig.fileTypeName}" name="fileTypeName" id="fileTypeName" queryMode="like"/>
        </fd:searchform>
        <c:if test="${entrance ne 'delivery'}">
            <fd:toolbar id="repFileTypeConfigTool">
                <fd:linkbutton id="addRepFileTypeConfigBtn" onclick="addRepFileTypeConfig()" operationCode="addRepFileTypeConfigCode"
                    value="{com.glaway.ids.pm.config.repFileTypeConfig.add}"  iconCls="basis ui-icon-plus" />
                <fd:linkbutton id="batchDelRepFileTypeConfigBtn" onclick="batchDelRepFileTypeConfig()" operationCode="batchDelRepFileTypeConfigCode"
                    value="{com.glaway.ids.common.msg.delete}" iconCls="basis ui-icon-minus" />     
                <fd:linkbutton id="batchEnableRepFileTypeBtn" onclick="batchRepFileTypeConfigStatusChange('enable')" operationCode="batchEnableRepFileTypeConfigCode"
                    value="{com.glaway.ids.common.start}" iconCls="basis ui-icon-enable"  />
                <fd:linkbutton id="batchDisableRepFileTypeBtn" onclick="batchRepFileTypeConfigStatusChange('disable')" operationCode="batchDisableRepFileTypeConfigCode"
                    value="{com.glaway.ids.common.stop}" iconCls="basis ui-icon-forbidden"   /> 
                <fd:linkbutton id="exportRepFileTypeConfigBtn" onclick="exportRepFileTypeConfig('repFileTypeConfigController.do?doExportXls')" operationCode="exportRepFileTypeConfigCode"
                    value="{com.glaway.ids.common.msg.export}" iconCls="basis ui-icon-export" />
            </fd:toolbar>
        </c:if>
        
    </div>
    <fd:datagrid url="repFileTypeConfigController.do?repFileTypeConfigList&entrance=${entrance}&docTypeId=${docTypeId}"
        toolbar="repFileTypeConfigInfo" checkbox="${entrance ne 'delivery'}" idField="id" searchFormId="#repFileTypeConfigSearchForm"
        id="repFileTypeConfigList" fit="true" fitColumns="true">
        <fd:dgCol field="id" title="id" hidden="true" />
        <fd:colOpt title="{com.glaway.ids.common.lable.operation}" width="50" hidden="${entrance eq 'delivery'}">
            <fd:colOptBtn tipTitle="{com.glaway.ids.pm.config.repFileTypeConfig.update}" iconCls="basis ui-icon-pencil" operationCode="updateRepFileTypeConfigCode" hideOption="hideRepFileTypeConfigUpdateBtn()"  onClick="repFileTypeConfigUpdate" ></fd:colOptBtn>
            <fd:colOptBtn tipTitle="{com.glaway.ids.pm.config.repFileTypeConfig.delete}" iconCls="basis ui-icon-minus" operationCode="delRepFileTypeConfigCode" hideOption="hideRepFileTypeConfigDelBtn"  onClick="delRepFileTypeConfig"  ></fd:colOptBtn>
            <fd:colOptBtn tipTitle="{com.glaway.ids.pm.config.repFileTypeConfig.set}" iconCls="basis ui-icon-set" operationCode="setRepFileTypeConfigCode" hideOption="hideRepFileTypeConfigSetBtn"  onClick="repFileTypeConfigSet"  ></fd:colOptBtn>
            <fd:colOptBtn tipTitle="{com.glaway.ids.common.start}" iconCls="basis ui-icon-enable" hideOption="hideRepFileTypeConfigEnableBtn" operationCode="enableRepFileTypeConfigCode" onClick="repFileTypeConfigStatusChange('enable')"  ></fd:colOptBtn>
            <fd:colOptBtn tipTitle="{com.glaway.ids.common.stop}" iconCls="basis ui-icon-forbidden" hideOption="hideRepFileTypeConfigDisableBtn" operationCode="disableRepFileTypeConfigCode" onClick="repFileTypeConfigStatusChange('disable')" ></fd:colOptBtn>
           </fd:colOpt>
        <fd:dgCol title="{com.glaway.ids.pm.config.repFileTypeConfig.fileTypeCode}" field="fileTypeCode" width="50" ></fd:dgCol>
        <fd:dgCol title="{com.glaway.ids.pm.config.repFileTypeConfig.fileTypeName}" field="fileTypeName" width="200" formatterFunName="viewRepFileTypeConfigDetail"></</fd:dgCol>
        <fd:dgCol title="{com.glaway.ids.pm.config.repFileTypeConfig.generateRuleName}" field="generateRuleName" width="100" ></fd:dgCol>
        <fd:dgCol title="{com.glaway.ids.pm.config.repFileTypeConfig.generateRuleDesc}" field="generateRuleDesc" width="100" ></fd:dgCol>
        <fd:dgCol title="{com.glaway.ids.pm.config.repFileTypeConfig.status}" field="status" width="100" formatterFunName="statusFormat"></fd:dgCol>
        <fd:dgCol title="{com.glaway.ids.pm.config.repFileTypeConfig.remark}" field="remark" width="100" tipField="remark"></fd:dgCol>
    </fd:datagrid>
    <fd:dialog id="addRepFileTypeConfigDialog" title="{com.glaway.ids.pm.config.repFileTypeConfig.add}" width="780" height="300">
        <fd:dialogbutton name="{com.glaway.foundation.common.ok}" callback="doRepFileTypeConfigAdd"></fd:dialogbutton>
        <fd:dialogbutton name="{com.glaway.foundation.common.cancel}" callback="hideDiaLog"></fd:dialogbutton>
    </fd:dialog>
    <fd:dialog id="updateRepFileTypeConfigDialog" title="{com.glaway.ids.pm.config.repFileTypeConfig.update}" width="780" height="300">
        <fd:dialogbutton name="{com.glaway.foundation.common.ok}" callback="doRepFileTypeConfigAdd"></fd:dialogbutton>
        <fd:dialogbutton name="{com.glaway.foundation.common.cancel}" callback="hideDiaLog"></fd:dialogbutton>
    </fd:dialog>
    <fd:dialog id="viewRepFileTypeConfigDialog" title="{com.glaway.ids.pm.config.repFileTypeConfig.view}" width="780" height="300">
        <fd:dialogbutton name="{com.glaway.ids.pm.config.repFileTypeConfig.close}" callback="hideDiaLog"></fd:dialogbutton>
    </fd:dialog>
    <fd:dialog id="setRepFileTypeConfigDialog" title="{com.glaway.ids.pm.config.repFileTypeConfig.set}" width="780" height="300">
        <fd:dialogbutton name="{com.glaway.foundation.common.ok}" callback="doRepFileTypeConfigSet"></fd:dialogbutton>
        <fd:dialogbutton name="{com.glaway.foundation.common.cancel}" callback="hideDiaLog"></fd:dialogbutton>
    </fd:dialog>

</body>
</html>
