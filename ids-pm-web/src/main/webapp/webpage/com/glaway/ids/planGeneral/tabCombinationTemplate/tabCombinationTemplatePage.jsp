<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
   <%-- <input id="currentUserId" name="currentUserId" type="hidden" value="${currentUserId}">--%>
    <div region="center">
        <div id="tabCombinationTemplateList" style="height: auto">
            <fd:searchform id="tabCombinationTag" onClickSearchBtn="searchTabCombination()" onClickResetBtn="tabCombinationReset()" help="helpDoc:TabComboTemplate">
                <fd:inputText title="{com.glaway.ids.common.lable.code}" name="code" id="code" queryMode="like"/>
                <fd:inputText title="{com.glaway.ids.pm.project.plantemplate.name}"  name="name" id="name" queryMode="like"/>
                <fd:combobox id="activityId" url="tabCombinationTemplateController.do?getTemplateTypeCombox" queryMode="in" multiple="true"
                             title="{com.glaway.ids.pm.general.actType}"  textField="name" valueField="id"
                             name="activityId"  editable="false" panelHeight="100"/>
                <fd:combobox title="{com.glaway.ids.common.lable.status}" id="bizCurrent" queryMode="in"
                             url="tabCombinationTemplateController.do?getTabCbTemplateLifeStyle" multiple="true"
                             textField="name" editable="false" valueField="id" name="bizCurrent" prompt="全部"
                             panelHeight="100"></fd:combobox>
            </fd:searchform>
            <fd:toolbar>
                <fd:toolbarGroup align="left">
                    <fd:linkbutton id="addCbTempBtn" onclick="addtabCbTemplate()" value="新增" operationCode = "tabCbTemplateAddCode"
                                   iconCls="basis ui-icon-plus" />
                    <fd:linkbutton
                            onclick="deleteALLSelect('批量删除','tabCombinationTemplateController.do?doBatchDel','tabCbTemplates',null,null)"
                            value="{com.glaway.ids.common.msg.delete}" iconCls="basis ui-icon-minus" operationCode="tabCbTemplateDeleteCode" />
                    <fd:linkbutton
                            onclick="doBatchStatusChange('启用')"
                            value="{com.glaway.ids.common.start}" iconCls="basis ui-icon-enable" operationCode="tabCbTemplateEnableCode" />
                    <fd:linkbutton
                            onclick="doBatchStatusChange('禁用')"
                            value="{com.glaway.ids.common.stop}" iconCls="basis ui-icon-forbidden"  operationCode="tabCbTemplateDisableCode" />
                </fd:toolbarGroup>
            </fd:toolbar>
        </div>

        <fd:datagrid url="tabCombinationTemplateController.do?searchDataGrid"
                     toolbar="#tabCombinationTemplateList" checkbox="true" idField="id"
                     id="tabCbTemplates" style="height: 300px;" fit="true" fitColumns="true">
            <fd:colOpt title="{com.glaway.ids.common.lable.operation}" width="120">
                <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.modify}" iconCls="basis ui-icon-pencil" hideOption="hideUpdate"  onClick="doUpdate" operationCode="tabCbTemplateUpdateCode"  ></fd:colOptBtn>
                <fd:colOptBtn tipTitle="{com.glaway.ids.common.start}" iconCls="basis ui-icon-enable" hideOption="hideEnable"  onClick="statusOperate" operationCode="tabCbTemplateEnableCode"  ></fd:colOptBtn>
                <fd:colOptBtn tipTitle="{com.glaway.ids.common.stop}" iconCls="basis ui-icon-forbidden" hideOption="hideDisable"  onClick="statusOperate" operationCode="tabCbTemplateDisableCode"  ></fd:colOptBtn>
                <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.copy}" iconCls="basis ui-icon-copy" hideOption="hideCopy" onClick="doCopy" operationCode="tabCbTemplateCopyCode"></fd:colOptBtn>
                <fd:colOptBtn tipTitle="{com.glaway.ids.pm.general.view}" iconCls="basis ui-icon-eye" hideOption="hideProjLog"
                        onClick="viewProjectLog" operationCode="tabCbTemplateViewCode"></fd:colOptBtn>
                <fd:colOptBtn tipTitle="{com.glaway.ids.common.msg.delete}" iconCls="basis ui-icon-minus" hideOption="hideDel"  onClick="doDelete" operationCode="tabCbTemplateDeleteCode"  ></fd:colOptBtn>
                <fd:colOptBtn tipTitle="{com.glaway.ids.pm.project.projectmanager.back}" iconCls="basis ui-icon-return" hideOption="hideOperBackMinor" onClick="backMinorLine" operationCode="tabCbTemplateminBackCode"></fd:colOptBtn>
                <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.revoke}" iconCls="basis ui-icon-revoke" hideOption="hideOperBackMajor" onClick="backMajorLine" operationCode="tabCbTemplatemajBackCode"></fd:colOptBtn>
                <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.revise}" iconCls="basis ui-icon-revise" hideOption="hideOperRevise" onClick="doRevise" operationCode="tabCbTemplateReviseCode"></fd:colOptBtn>
                <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.approve}" iconCls="basis ui-icon-submitted_approval" hideOption="hideCommit"  onClick="goProrjSubmitApprove" operationCode="tabCbTemplateApproveCode"></fd:colOptBtn>

            </fd:colOpt>
            <fd:dgCol title="{com.glaway.ids.common.lable.code}" field="code" />
            <fd:dgCol title="{com.glaway.ids.pm.project.plantemplate.name}" field="name"></fd:dgCol>
            <fd:dgCol title="{com.glaway.ids.pm.general.actType}" field="templateName"/>
            <fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.version}" field="bizVersion" width="100" align="left"
                      sortable="true" formatterFunName="formatterVersion" />
            <fd:dgCol field="status" title="{com.glaway.ids.common.lable.status}" formatterFunName="formatStatus" sortable="false"/>
            <fd:dgCol field="remake" title="{com.glaway.ids.common.lable.remark}"  sortable="false" />
        </fd:datagrid>
    </div>

</div>
<fd:dialog id="addCbTemplateDialog" width="950px" height="600px"
           modal="true" title="{com.glaway.ids.pm.general.addtabCbtemplate}">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addPlanTemplateConfirm"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="updateCbTemplateDialog" width="950px" height="600px"
           modal="true" title="{com.glaway.ids.pm.general.modifytabCbtemplate}">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="updatePlanTemplateConfirm"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="reviseCbTemplateDialog" width="950px" height="600px"
           modal="true" title="{com.glaway.ids.pm.general.revisetabCbtemplate}">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="revisePlanTemplateConfirm"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="updatePlanTemplateDialog" width="950px" height="600px"
           modal="true" title="com.glaway.ids.pm.general.modifytabCbtemplate">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addPlanTemplateConfirm"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="copyDialog" width="950px" height="600px"
           modal="true" title="{com.glaway.ids.pm.general.copytabCbtemplate}">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="copyConfirm"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="viewDialog" width="950px" height="600px"
           modal="true" title="{com.glaway.ids.common.lable.preView}">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="viewHistoryDialog" width="730px" height="530px" modal="true" title="{com.glaway.ids.pm.projtemplate.viewHistoryVersion}">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.close}"
                     callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="goProjTmpSubmitApprove" width="490px" height="120px" modal="true" title="{com.glaway.ids.pm.project.plan.toApprove}">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="startPTmpProcess"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<script>
    // 查看页签
    function viewProjectLog(id,index) {
        var row = $("#tabCbTemplates").datagrid("getRows")[index];
        //获取ID值
        var ids=row.id;
        var dialogUrl = 'tabCombinationTemplateController.do?goTabView&id='+ids;
        createDialog('viewDialog', dialogUrl);
    }

    /* 国际化转移的方法*/
    function deleteALLSelect(title, url, gname) {
        var flag=true;
        gridname = gname;
        var ids = [];
        //var names = [];
        var rows = $("#" + gname).datagrid('getSelections');
        if (rows.length > 0) {
            for (var i = 0; i < rows.length; i++) {
                if(rows[i].bizCurrent != 'nizhi'){
                    tip('<spring:message code="com.glaway.ids.pm.project.projecttemplate.onlyDelEditing"/>');
                    return;
                };
            }

            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i].id);
            }

            top.Alert.confirm('<spring:message code="com.glaway.ids.pm.general.confirmBatchDel"/>', function(r) {
                if (r) {
                    $.ajax({
                        url : url,
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
                                $("#" + gname).datagrid('unselectAll');
                                ids = '';
                            }
                        }
                    });
                }
            });
        } else {
            tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
        }
    }
    //启用禁用点击事件
    function statusOperate(tmp,index){

        $('#tabCbTemplates').datagrid('selectRow',index);
        var row = $("#tabCbTemplates").datagrid("getRows")[index];
        var status = row.bizCurrent;
        var uType = "qiyong";
        var changeToStatus="启用";
        if(status=="qiyong"){
            uType = "jinyong";
            changeToStatus="禁用";
            top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmBatchOperate" arguments="' + changeToStatus + '"/>', function(r) {
                if (r) {
                    $.ajax({
                        //&planTemplateId='+id+'&status='+encodeURI(changeToStatus)
                        url: 'tabCombinationTemplateController.do?doStatusChange&status='+uType+'&ids='+row.id,
                        type : "POST",
                        dataType : "text",
                        async : false,
                        cache : false,
                        success : function(data) {
                            var d = $.parseJSON(data);
                            if(d.success){
                                tip(d.msg);
                                reloadTable();
                            }else{
                                return;
                            }

                        }
                    });
                }
            });
        } else {
            //启用时先判断是否存在已启用的活动类型
            var activityId = row.activityId;
            var templateId = row.id;
            top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmBatchOperate" arguments="' + changeToStatus + '"/>', function(r) {
                if (r) {
                    $.ajax({
                        //&planTemplateId='+id+'&status='+encodeURI(changeToStatus)
                        url: 'tabCombinationTemplateController.do?doStatusChange&status='+uType+'&ids='+row.id,
                        type : "POST",
                        dataType : "text",
                        async : false,
                        cache : false,
                        success : function(data) {
                            var d = $.parseJSON(data);
                            if(d.success){
                                tip(d.msg);
                                reloadTable();
                            }else{
                                return;
                            }

                        }
                    });
                }
            });
        }
    }

    function doDelete(tmp,index){
        var row = $("#tabCbTemplates").datagrid("getRows")[index];
        top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmDel"/>', function(r) {
            if(r){
                $.ajax({
                    url : 'tabCombinationTemplateController.do?doBatchDel',
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
                            $("#tabCbTemplates").datagrid('unselectAll');
                            ids='';
                        }
                    }
                });
            }

        });
    }
    //批量启用禁用事件
    function doBatchStatusChange(type){
        debugger

        var rows = $("#tabCbTemplates").datagrid('getSelections');
        if(rows.length==0){
            top.tip('请选择需要操作的模板');
            return;
        }

        var ids = [];
        if(type=="禁用"){//要禁用
            for ( var i = 0; i < rows.length; i++) {
                if(rows[i].bizCurrent != "qiyong"){
                    top.tip('<spring:message code="com.glaway.ids.common.onlyStopStarted"/>');
                    return;
                }
            }
        }
        if(type=="启用"){
            for ( var i = 0; i < rows.length; i++) {
                if(rows[i].bizCurrent !="jinyong"){
                    top.tip('<spring:message code="com.glaway.ids.common.onlyStartStopped"/>');
                    return;
                }
            }
        }

        for ( var i = 0; i < rows.length; i++) {
            ids.push(rows[i].id);
        }

        var uType = "jinyong";
        if(type=="禁用"){
            top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmBatchOperate" arguments="' + type + '"/>', function(r) {
                if(r){
                    $.ajax({
                        url : 'tabCombinationTemplateController.do?doStatusChange',
                        type : 'post',
                        data : {
                            ids : ids.join(","),
                            status:uType
                        },
                        cache : false,
                        success : function(data) {
                            var d = $.parseJSON(data);
                            if (d.success) {
                                var msg = d.msg;
                                top.tip(msg);
                                reloadTable();
                                $("#tabCbTemplates").datagrid('unselectAll');
                                ids='';
                            }
                        }
                    });
                }
            });
        }

        if(type=="启用"){
            uType = "qiyong";
            var actIds = [];
            for ( var i = 0; i < rows.length; i++) {
                actIds.push(rows[i].activityId);
            }
            $.ajax({
                type : 'get',
                url : 'tabCombinationTemplateController.do?isActivityTypeManageUse',
                data : {
                    id : actIds.join(",")
                },
                cache : false,
                success : function(data) {
                    var d = $.parseJSON(data);
                    if (d.success) {
                        top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmBatchOperate" arguments="' + type + '"/>', function(r) {
                            if(r){
                                $.ajax({
                                    url : 'tabCombinationTemplateController.do?doStatusChange',
                                    type : 'post',
                                    data : {
                                        ids : ids.join(","),
                                        status:uType
                                    },
                                    cache : false,
                                    success : function(data) {
                                        var d = $.parseJSON(data);
                                        if (d.success) {
                                            var msg = d.msg;
                                            top.tip(msg);
                                            reloadTable();
                                            $("#tabCbTemplates").datagrid('unselectAll');
                                            ids='';
                                        }
                                    }
                                });
                            }
                        });
                    } else {
                        top.tip(d.msg);
                        return;
                    }
                }
            });
        }
    }

    //禁用按钮显示
    function hideDisable(row,index){
        if(row.bizCurrent=="qiyong")
        {
            return false;
        }else{
            return true;
        }
    }

    function hideEnable(row,index){
        if(row.bizCurrent=="jinyong")
        {
            return false;
        }else{
            return true;
        }
    }

    function hideCopy(row,index){
        if(row.bizCurrent=="qiyong")
        {
            return false;
        }else{
            return false;
        }
    }

    function hideDel(row,index){
        if (row.bizCurrent == 'nizhi'){
            return false;
        } else {
            return true;
        }
    }

    function hideUpdate(row,index){
        if(row.bizCurrent=="nizhi" || row.bizCurrent=="xiuding")
        {
            return false;
        }else{
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
    //修订按钮显示控制
    function hideOperRevise(row,index){
        if (row.bizCurrent == 'qiyong' || row.bizCurrent=="jinyong"){
            return false;
        } else {
            return true;
        }
    }
    //拟制中或修订中显示审批按钮
    function hideCommit(row,index){
        if(row.bizCurrent == "nizhi" || row.bizCurrent == "xiuding")
        {
            return false;
        }else{
            return true;
        }
    }

    //点击查询按钮
    function searchTabCombination(){
        var templateName = $("#activityId").combobox('getValue');
        var queryParams=$("#tabCbTemplates").fddatagrid('queryParams',"tabCombinationTag");
        $('#tabCombinationTag').find('*').each(function() {

            if(templateName == undefined){
                queryParams['activityId'] = '';
            }
        });
        $('#tabCbTemplates').datagrid({
            url : 'tabCombinationTemplateController.do?searchDataGrid',
            queryParams : queryParams,
            pageNumber : 1
        });
        $('#tabCbTemplates').datagrid('unselectAll');
        $('#tabCbTemplates').datagrid('clearSelections');
        $('#tabCbTemplates').datagrid('clearChecked');

    }

    function tabCombinationReset(){
        $("#code").textbox("clear");
        $("#name").textbox("clear");
        $("#activityId").combobox("clear");
        $("#status").combobox("setValue"," ");
    }


    function addtabCbTemplate(){
        var dialogUrl = 'tabCombinationTemplateController.do?goAddCbTemplate&type=add';
        createDialog('addCbTemplateDialog', dialogUrl);
    }

    function addPlanTemplateConfirm(iframe){
        iframe.addtabCbTemplate();
        return false;
    }

    function updatePlanTemplateConfirm(iframe){
        iframe.updatetabCbTemplate('miner');
        return false;
    }

    function revisePlanTemplateConfirm(iframe){
        iframe.updatetabCbTemplate('revise');
        return false;
    }

    /**
     * 修改页签组合信息
     */
    function doUpdate(id,index){
        var row = $("#tabCbTemplates").datagrid("getRows")[index];
        var dialogUrl = 'tabCombinationTemplateController.do?goAddCbTemplate&type=update&tabCbTemplateId='+row.id;
        createDialog('updateCbTemplateDialog', dialogUrl);
    }

    //修订页签组合模板
    function doRevise(id,index){
        var row = $("#tabCbTemplates").datagrid("getRows")[index];
        var dialogUrl = 'tabCombinationTemplateController.do?goAddCbTemplate&type=update&tabCbTemplateId='+id;
        createDialog('reviseCbTemplateDialog', dialogUrl);
    }

    /**
     * 复制模板
     */
    function doCopy(id, index) {
        var row = $("#tabCbTemplates").datagrid("getRows")[index];
        var dialogUrl = 'tabCombinationTemplateController.do?goAddCbTemplate&type=copy&tabCbTemplateId='+row.id;
        createDialog('copyDialog', dialogUrl);
    }

    function copyConfirm(iframe){
        iframe.doCopyTemplate();
        return false;
    }

    /*版本字段格式化*/
    function formatterVersion(val, row, index) {
        return '<span style="cursor:hand"><a onclick="showBizVersion(\''
            + row.id + '\')" style="color:blue;">' + val
            + '</a></span>';
    }
    function showBizVersion(id) {
        url = 'tabCombinationTemplateController.do?goVersionHistory&id=' + id;
        createDialog('viewHistoryDialog', url);
    }

    //回退：
    function backMinorLine(id, index) {
        top.Alert.confirm(
            '<spring:message code="com.glaway.ids.pm.plantemplate.confirmBack"/>',
            function(r) {
                if (r) {
                    $('#tabCbTemplates').datagrid('unselectAll');
                    $('#tabCbTemplates').datagrid('selectRow',index);
                    //有问题，获取的数据不是最新的
                    //var row = $("#tabCbTemplates").datagrid('getSelections');
                    var row = $("#tabCbTemplates").datagrid("getRows")[index];
                    if (row.bizId != '' && row.bizVersion != '') {
                        var url = 'tabCombinationTemplateController.do?doBackVersion';
                        $.ajax({
                            url : url,
                            type : 'post',
                            data : {
                                id : id,
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
                                            $("#tabCbTemplates").datagrid('clearSelections');
                                            $("#tabCbTemplates").datagrid('reload');
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
    function backMajorLine(id, index) {
        top.Alert.confirm(
            '<spring:message code="com.glaway.ids.pm.plantemplate.confirmRevoke"/>',
            function(r) {
                if (r) {
                    $('#tabCbTemplates').datagrid('unselectAll');
                    $('#tabCbTemplates').datagrid('selectRow',index);
                    var row = $("#tabCbTemplates").datagrid("getRows")[index];
                    var url = 'tabCombinationTemplateController.do?doBackVersion';
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
                                    $("#tabCbTemplates").datagrid('clearSelections');
                                    $("#tabCbTemplates").datagrid('reload');
                                }, 500);//等待0.5秒，让分类树结构加载出来
                            }
                        }
                    });
                }
            });
    }

    //提交审批
    function goProrjSubmitApprove(id, index) {
        /*debugger;
        $('#projTemplateList').datagrid('unselectAll');
        $('#projTemplateList').datagrid('selectRow',index);
        var row = $('#projTemplateList').datagrid('getSelected');
        var rowIndex=$('#projTemplateList').datagrid('getRowIndex',row);*/
        var url='tabCombinationTemplateController.do?goSubmitApprove&tabCbTemplateId='+id;
        $("#goProjTmpSubmitApprove").lhgdialog("open", "url:"+url);
    }

    function startPTmpProcess(iframe){
        iframe.startProjProcess();
        return false;
    }

    function formatStatus(value, row, index) {
        if(row.processInstanceId != '' && row.processInstanceId != null && row.processInstanceId != undefined){
            return '<a href=\'javascript:void(0)\' onclick="viewProcessDef(\''+row.processInstanceId +'\',\''+row.name+'\')" title=\'查看流程图\' ><font color=blue>'+value+'</font></a>';
        }
        return value;
    }

    function viewProcessDef(procInstId,title)
    {
        createdetailwindow(title+'-页签组合模板工作流','generateFlowDiagramController.do?initDrowFlowActivitiDiagram&procInstId='+procInstId,800,600);
    }

</script>
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
