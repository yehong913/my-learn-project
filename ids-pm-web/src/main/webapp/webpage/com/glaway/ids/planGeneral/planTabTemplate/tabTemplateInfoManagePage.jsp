<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery_iframe,easyui_iframe,tools"></t:base>
<style type="text/css">
    .title-area
    {	width:100%;height:40px;
        font-size:12px;
        line-height:40px;
        border:1px solid #cccccc;
    }
</style>

<div class="easyui-layout" fit="true">
    <div region="center" style="padding: 1px;">
        <div id="tabTemplateManageBar" style="padding: 3px;height: auto">
            <!-- 查询条件栏 -->
            <fd:searchform id="tabTemplateManageSearch" onClickSearchBtn="searchTabTemplateManage();" onClickResetBtn="tabTemplateManageSearchreSet()" help="helpDoc:TabTemplate">
                <fd:inputText title="编号" name="code" id="tabTemplate_code" queryMode="like" maxLength="-1"/>
                <fd:inputText title="名称" name="name" id="tabTemplate_name" queryMode="like" maxLength="-1"/>
                <fd:combobox  title="类型" name="tabType" id="tabTemplate_tabType" textField="text"
                              editable="false" valueField="value" data="_全部,1_工作内容,2_职责权限,3_资源,4_风险控制,5_过程管理,6_过程结果" value="全部" queryMode="eq"></fd:combobox>
                <fd:combobox name="bizCurrent" id="tabTemplate_status" title="状态" editable="false"
                             textField="text"  valueField="id" data="qiyong_启用,jinyong_禁用,nizhi_拟制中,shenhe_审批中,xiuding_修订中" prompt="全部" queryMode="eq"></fd:combobox>
            </fd:searchform>

            <!-- 操作栏 -->
            <fd:toolbar>
                <fd:toolbarGroup align="left">
                    <fd:linkbutton
                                   onclick="createTabTemplate('addPlanTabTemplateDialog','tabTemplateController.do?goAddTabTemplateInfo')"
                                   value="{com.glaway.ids.common.btn.create}" operationCode="addPlanTabTemplate"
                                   iconCls="basis ui-icon-plus"/>
                    <fd:linkbutton
                                   onclick="deleteTabTemplateBatch('批量删除','tabTemplateController.do?doBatchDelete')"
                                   value="{com.glaway.ids.common.btn.remove}" operationCode="deletePlanTabTemplate"
                                   iconCls="basis ui-icon-minus"/>
                    <fd:linkbutton
                                   onclick="statusOperateBatchTabTemplate('启用')"
                                   value="{com.glaway.ids.common.btn.start}" operationCode="starPlanTabTemplate"
                                   iconCls="basis ui-icon-enable"/>
                    <fd:linkbutton
                                   onclick="statusOperateBatchTabTemplate('禁用')"
                                   value="{com.glaway.ids.common.btn.stop}" operationCode="stopPlanTabTemplate"
                                   iconCls="basis ui-icon-forbidden"/>
                </fd:toolbarGroup>
            </fd:toolbar>
        </div>

        <!-- 数据展示区Datagrid -->
        <fd:datagrid url="tabTemplateController.do?searchDatagrid" toolbar="#tabTemplateManageBar" fit="true" fitColumns="true" idField="id" id="tabTemplateList" checkbox="true">
            <fd:colOpt title="{com.glaway.ids.common.lable.operation}" width="100">
                <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.modify}" iconCls="basis ui-icon-pencil" hideOption="hideTabTemplateModify()"  onClick="modifyTabTemplate" operationCode="updatePlanTabTemplate"></fd:colOptBtn>
                <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.start}" iconCls="basis ui-icon-enable" hideOption="hideTabTemplateStart()"  onClick="statusOperateTabTemplate" operationCode="starPlanTabTemplate"></fd:colOptBtn>
                <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.stop}" iconCls="basis ui-icon-forbidden" hideOption="hideTabTemplateStop()"  onClick="statusOperateTabTemplate" operationCode="stopPlanTabTemplate"></fd:colOptBtn>
                <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.remove}" iconCls="basis ui-icon-minus" hideOption="hideTabTemplateRemove()"  onClick="deleteTabTemplate" operationCode="deletePlanTabTemplate"></fd:colOptBtn>
                <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.copy}" iconCls="basis ui-icon-copy" hideOption="hideCopy()" onClick="copyTabTemplate" operationCode="copyPlanTabTemplate"></fd:colOptBtn>
                <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.approve}" iconCls="basis ui-icon-submitted_approval" hideOption="hideCommit"  onClick="goTabTemSubmitApprove" operationCode="approvePlanTabTemplate"></fd:colOptBtn>
                <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.revise}" iconCls="basis ui-icon-revise" hideOption="hideOperRevise" onClick="doRevise" operationCode="revisePlanTabTemplate"></fd:colOptBtn>
                <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.revoke}" iconCls="basis ui-icon-revoke" hideOption="hideOperBackMajor" onClick="doRevoke" operationCode="revokePlanTabTemplate"></fd:colOptBtn>
                <fd:colOptBtn tipTitle="回退" iconCls="basis ui-icon-return" hideOption="hideOperBackMinor" onClick="doBack" operationCode="backPlanTabTemplate"></fd:colOptBtn>
                <fd:colOptBtn tipTitle="预览" iconCls="basis ui-icon-eye"  onClick="showTabDetailAll" ></fd:colOptBtn>
            </fd:colOpt>
            <fd:dgCol title="主键ID" field="id"  hidden="true" />
            <fd:dgCol title="来源" field="source"  hidden="true" />
            <fd:dgCol title="编号" field="code" width="100" sortable="true"/>
            <fd:dgCol title="名称" field="name" width="100" sortable="true"/>
            <fd:dgCol title="类型" field="tabType" width="100" formatterFunName="tabTypeShow" sortable="true"/>
            <fd:dgCol title="版本" field="bizVersion" width="100" sortable="true" formatterFunName="versionFormatter"/>
            <fd:dgCol title="状态" field="bizCurrent" width="50"  sortable="true" formatterFunName="formatStatus"/>
            <fd:dgCol title="备注" field="remake" width="150"  />
        </fd:datagrid>
    </div>
</div>

<!-- 新增Dialog -->
<fd:dialog id="addPlanTabTemplateDialog" width="1200px" height="610px" modal="true" title="新增页签">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="saveTabTemplate"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="tabTemplatehideDiaLog"></fd:dialogbutton>
</fd:dialog>

<!-- 修改Dialog -->
<fd:dialog id="editPlanTabTemplateDialog" width="1200px" height="610px" modal="true" title="修改">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="updateTemplate"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="tabTemplateUpdatehideDiaLog"></fd:dialogbutton>
</fd:dialog>

<!-- 复制Dialog -->
<fd:dialog id="copyPlanTabTemplateDialog" width="1200px" height="610px" modal="true" title="复制">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="updateTemplate"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="tabTemplatehideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="viewDialog" width="950px" height="600px"
           modal="true" title="{com.glaway.ids.common.lable.preView}">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<!-- 审批Dialog -->
<fd:dialog id="goTabTemSubmitApprove" width="420px" height="120px" modal="true" title="{com.glaway.ids.pm.project.plan.toApprove}">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="startPlanTemProcess"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<!-- 修订Dialog -->
<fd:dialog id="revisePlanTabTemplateDialog" width="1200px" height="610px" modal="true" title="修订">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="updateTemplate"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="tabTemplatehideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="versionDetailList" width="900px" height="500px" modal="true" title="历史版本信息查看">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<script>
    function startPlanTemProcess(iframe){
        iframe.startTabTemProcess();
        return false;
    }

    //查询事件
    function searchTabTemplateManage() {
        //获取查询条件值
        var queryParams = $("#tabTemplateList").fddatagrid('queryParams', "tabTemplateManageSearch");
        //获取查询类型值，查询方式为等值
        queryParams.tabType=$("#tabTemplate_tabType").combobox('getValue');
        //数据查询
        $('#tabTemplateList').datagrid({
            url : 'tabTemplateController.do?searchDatagrid',
            queryParams : queryParams,
            pageNumber : 1
        });
        //清除Datagrid选择
        $('#tabTemplateList').datagrid("clearSelections");
        //清除Datagrid单选框按钮
        $('#tabTemplateList').datagrid("clearChecked");
    }

    //重置按钮事件
    function tabTemplateManageSearchreSet(){
        //把所有查询条件值置空
        $("#tabTemplate_code").textbox("setValue","");
        $("#tabTemplate_name").textbox("setValue","");
        $("#tabTemplate_tabType").combobox("setValue", "全部");
        $("#tabTemplate_status").textbox("clear");
    }

    //新增事件
    function createTabTemplate(id,url){
        //打开新增Dialog
        createDialog(id, url);
    }

    //批量删除事件
    function deleteTabTemplateBatch(title,url) {
        var ids = [];
        //获取Datagrid选择Rows
        var rows = $("#tabTemplateList").datagrid('getSelections');
        if (rows.length > 0) {
            //再次确认事件
            ${param.isIframe=='true'?'parent.':''}Alert.confirm('<spring:message code="com.glaway.ids.common.confirmBatchDel"/>', function(r) {
                if (r) {
                    for ( var i = 0; i < rows.length; i++) {
                        ids.push(rows[i].id);
                        //判断是否为系统拟制状态
                        if (rows[i].bizCurrent!="nizhi"){
                            top.tip('非拟制中的页签模板不可以删除');
                            return;
                        }
                    }
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
                                top.tip(msg);
                                // 重新加载页签模版列表
                                reloadTabTemplateList();
                                //清空数组值，防止再次删除数据出错
                                ids='';
                            }
                        }
                    });
                }
            });
        } else {
            top.tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
        }
    }
    //批量启用禁用事件
    function statusOperateBatchTabTemplate(type){
        var uType = "";
        //禁用转启用，启用转禁用
        if(type=='<spring:message code="com.glaway.ids.common.start"/>'){
            uType='<spring:message code="com.glaway.ids.common.stop"/>';
        }else{
            uType='<spring:message code="com.glaway.ids.common.start"/>';
        }
        //获取选择的Rows
        var rows = $("#tabTemplateList").datagrid('getSelections');

        //判断是否有选择的值
        if(rows.length==0){
            top.tip('<spring:message code="com.glaway.ids.common.selectOperateParam" arguments="' + type + '"/>');
            return;
        }

        var ids = [];
        //要禁用
        if(type=='<spring:message code="com.glaway.ids.common.stop"/>'){
            for ( var i = 0; i < rows.length; i++) {
                //当前为禁用操作，只能选择启用状态数据
                if(rows[i].bizCurrent!='qiyong'){
                    top.tip('<spring:message code="com.glaway.ids.common.onlyStopStarted"/>');
                    return;
                }
                if(rows[i].source==0){
                    top.tip('不允许禁用内置模版');
                    return;
                }
            }
        }
        //要启用
        if(type=='<spring:message code="com.glaway.ids.common.start"/>'){
            for ( var i = 0; i < rows.length; i++) {
                //当前为启用操作，只能选择禁用状态数据
                if(rows[i].bizCurrent!='jinyong'){
                    top.tip('<spring:message code="com.glaway.ids.common.onlyStartStopped"/>');
                    return;
                }
                if(rows[i].source==0){
                    top.tip('不允许选择内置模版');
                    return;
                }
            }
        }

        //再次确认事件
        ${param.isIframe=='true'?'parent.':''}Alert.confirm('<spring:message code="com.glaway.ids.common.confirmOperate" arguments="' + type + '"/>', function(r) {
            if (r) {
                if (type=='<spring:message code="com.glaway.ids.common.stop"/>') {
                    type="jinyong";
                }else{
                    type="qiyong";
                }
                for ( var i = 0; i < rows.length; i++) {
                    ids.push(rows[i].id);
                }
                $.ajax({
                    url : 'tabTemplateController.do?doStartOrStop',
                    type : 'post',
                    data : {
                        //参数ids为id集合字符串，status为要更新的状态值
                        ids : ids.join(","),
                        status:type
                    },
                    cache : false,
                    success : function(data) {
                        var d = $.parseJSON(data);
                        if (d.success) {
                            var msg = "操作成功";
                            if (type=="0"){
                                msg = "禁用"+msg;
                            } else {
                                msg = "启用"+msg;
                            }
                            top.tip(msg);
                            // 重新加载页签模版列表
                            reloadTabTemplateList();
                        }
                    }
                });
            }
        });
    }

    //修改按钮显示条件
    function hideTabTemplateModify(row,index){
        if(row.bizCurrent=="nizhi" || row.bizCurrent=="xiuding")
        {
            return false;
        }else{
            return true;
        }
    }

    //修改模板事件
    function modifyTabTemplate(id,index){
        //获取选中单条数据
        var row = $("#tabTemplateList").datagrid("getRows")[index];
        //获取ID值
        var ids=row.id;
        //打开修改Dialog
        createDialog('editPlanTabTemplateDialog','tabTemplateController.do?goTebTemplateUpdate&id='+ids);
    }

    //启用按钮显示条件
    function hideTabTemplateStart(row,index){
        //获取状态值
        var status = 'jinyong';
        //状态为禁用页签模板则显示
        if(row.bizCurrent==status){
            return false;
        } else{
            return true;
        }
    }

    //禁用按钮显示条件
    function hideTabTemplateStop(row,index){
        //获取状态值
        var status = 'qiyong';
        //状态为启用且为系统非内置页签模板则显示
        if(row.bizCurrent==status){
            return false;
        } else{
            return true;
        }
    }



    // 查看页签
    function showTabDetailAll(row,index) {
        var row = $("#tabTemplateList").datagrid("getRows")[index];
        //获取ID值
        var ids=row.id;
        var displayUsage=row.displayUsage;
        var dialogUrl = 'tabTemplateController.do?goTabView&id='+ids;
        dialogUrl = dialogUrl +'&displayUsage='+displayUsage;
        
        createDialog('viewDialog', dialogUrl);


    }

    //启用禁用点击事件
    function statusOperateTabTemplate(id,index){
        $('#tabTemplateList').datagrid('selectRow',index);
        //获取选中行的数据
        var rows = $('#tabTemplateList').datagrid('getSelections');
        var row=rows[rows.length-1];
        //获取当前状态值
        var status = row.bizCurrent;
        var changeToStatus="jinyong";
        var stopFlag = "禁用";
        //操作期望值
        if(status=="jinyong"){
            changeToStatus="qiyong";
            stopFlag = "启用";
        }
        //再次确认操作
        top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmBatchOperate" arguments="' + stopFlag + '"/>', function(r) {
            if (r) {
                $.ajax({
                    url: 'tabTemplateController.do?doStartOrStop&status='+changeToStatus+'&ids='+row.id,
                    type : "POST",
                    dataType : "text",
                    async : false,
                    cache : false,
                    success : function(data) {
                        var d = $.parseJSON(data);
                        if(d.success){
                            var msg = stopFlag+"操作成功";
                            top.tip(msg);
                            // 重新加载页签模版列表
                            reloadTabTemplateList();
                        }else{
                            var msg = stopFlag+"操作失败";
                            //提示信息
                            top.tip(msg);
                            return;
                        }
                    }
                });
            }
        });
    }

    // 删除按钮显示条件
    function hideTabTemplateRemove(row,index){
        if (row.bizCurrent == 'nizhi'){
            return false;
        } else {
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

    //单条删除事件
    function deleteTabTemplate(id,index){
        //获取选中的数据
        var row = $("#tabTemplateList").datagrid("getRows")[index];
        //再次确认操作
        top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmBatchDel"/>', function(r) {
            if(r){
                $.ajax({
                    url : 'tabTemplateController.do?doBatchDelete',
                    type : 'post',
                    data : {
                        ids : row.id
                    },
                    cache : false,
                    success : function(data) {
                        var d = $.parseJSON(data);
                        if (d.success) {
                            top.tip(d.msg);
                            // 重新加载页签模版列表
                            reloadTabTemplateList();
                        }
                    }
                });
            }
        });
    }

    //复制事件
    function copyTabTemplate(id, index) {
        //获取单条数据
        var row = $("#tabTemplateList").datagrid("getRows")[index];
        //打开复制Dialog
        createDialog('copyPlanTabTemplateDialog', 'tabTemplateController.do?goTebTemplateCopy&id='+row.id);
    }

    // 重新加载页签模版列表
    function reloadTabTemplateList() {
        //选择全部置空
        $('#tabTemplateList').datagrid('unselectAll');
        //清除选择值
        $('#tabTemplateList').datagrid('clearSelections');
        //清除单选框
        $('#tabTemplateList').datagrid('clearChecked');
        //数据进行重新加载
        $('#tabTemplateList').datagrid('reload');
    }

    //触发保存提交按钮
    function saveTabTemplate(iframe) {
        //触发新增页面提交
        saveOrUp(iframe);
        return false;
    }

    //触发更新提交按钮
    function updateTemplate(iframe) {
        //触发新增页面提交
        saveOrUp(iframe);
        return false;
    }

    //取消按钮
    function tabTemplatehideDiaLog(){
        //关闭窗口
        hideDiaLog();
    }

    //更新取消按钮
    function tabTemplateUpdatehideDiaLog(iframe){
        iframe.$("#updateBtn_sub").click();
    }

    //类型页面显示值
    function tabTypeShow(value,row,index) {
        var tabTypeArray  = new Array();
        tabTypeArray.push("工作内容");
        tabTypeArray.push("职责权限");
        tabTypeArray.push("资源");
        tabTypeArray.push("风险控制");
        tabTypeArray.push("过程管理");
        tabTypeArray.push("过程结果");
        return tabTypeArray[value-1];
    }

    function goTabTemSubmitApprove(tmp,index) {
        var row = $("#tabTemplateList").datagrid("getRows")[index];
        var dialogUrl='tabTemplateController.do?goTabTemSubmitApprove&tabId=' + row.id;
        createDialog('goTabTemSubmitApprove',dialogUrl);
    }

    //修订事件
    function doRevise(id, index) {
        //获取单条数据
        var row = $("#tabTemplateList").datagrid("getRows")[index];
        //打开复制Dialog
        createDialog('revisePlanTabTemplateDialog', 'tabTemplateController.do?goTebTemplateRevise&id='+row.id);
    }

    /**
     * 撤销模板
     */
    function doRevoke(id, index) {
        top.Alert.confirm(
            '<spring:message code="com.glaway.ids.pm.plantemplate.confirmRevoke"/>',
            function(r) {
                if (r) {
                    var row = $("#tabTemplateList").datagrid("getRows")[index];
                    var url = 'tabTemplateController.do?doRevoke';
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
                                reloadTabTemplateList();
                            }
                        }
                    });
                }
            });
    }

    /**
     * 回退模板
     */
    function doBack(id, index) {
        top.Alert.confirm(
            '<spring:message code="com.glaway.ids.pm.plantemplate.confirmBack"/>',
            function(r) {
                if (r) {
                    var row = $("#tabTemplateList").datagrid("getRows")[index];
                    var url = 'tabTemplateController.do?doBack';
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
                                window.setTimeout("reloadTable()", 500);//等待0.5秒，让分类树结构加载出来
                            }
                        }
                    });
                }
            });
    }

    function versionFormatter(val, row, value) {
        return '<span style="cursor:hand"><a onclick="showBizVersion(\'' + row.id + '\')" style="color:blue;">' + val + '</a></span>';
    }

    function showBizVersion(id) {
        var dialogUrl = 'tabTemplateController.do?goVersionDetail&tabId='+ id;
        createDialog('versionDetailList',dialogUrl);
    }

    function formatStatus(value, row, index) {
        var status = "";
        if(value=='nizhi'){
            status = '拟制中';
        }else if(value=='qiyong'){
            status = '启用';
        }else if(value=='jinyong'){
            status = '禁用';
        }else if(value=='shenhe'){
            status = '审批中';
        }else {
            status = '修订中';
        }
        if(row.processInstanceId != '' && row.processInstanceId != null && row.processInstanceId != undefined){
            return '<a href=\'javascript:void(0)\' onclick="viewProcessDef(\''+row.processInstanceId +'\',\''+row.name+'\')" title=\'查看流程图\' ><font color=blue>'+status+'</font></a>';
        }
        return status;
    }

    function viewProcessDef(procInstId,title)
    {
        createdetailwindow(title+'-项目模板工作流','generateFlowDiagramController.do?initDrowFlowActivitiDiagram&procInstId='+procInstId,800,600);
    }
</script>