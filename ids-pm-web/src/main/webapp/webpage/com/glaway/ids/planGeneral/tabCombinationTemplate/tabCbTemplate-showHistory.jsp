<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>模板详细信息</title>
    <t:base type="jquery,easyui_iframe,tools"></t:base>
    <!-- 页面时间格式化 -->
<body style="overflow-x: hidden;">
<fd:panel  border="false" title="版本详情" collapsible="false" fit="false" width="100%">
    <fd:datagrid url="tabCombinationTemplateController.do?getVersionHistory&bizId=${template.bizId}" width="100%"
                 idField="id" id="procTmplListss" fitColumns="true" fit="false" checkbox="false">
        <fd:dgCol title="{com.glaway.ids.common.lable.operation}" field="operation" width="30" formatterFunName="operation" sortable="false"/>
        <fd:dgCol title="{com.glaway.ids.pm.project.plantemplate.bizVersion}" field="bizVersion" width="30" formatterFunName="operation2" sortable="false"/>
        <fd:dgCol title="{com.glaway.ids.rdflow.plan.modifyPeople}" field="creator" width="48" formatterFunName="formatCreator" sortable="false"/>
        <fd:dgCol title="{com.glaway.ids.rdflow.plan.modifyTime}" field="createTime" width="75" formatterFunName="dateFmtFulltime" sortable="false"/>
        <fd:dgCol title="{com.glaway.ids.common.lable.status}" field="status" width="36"  formatterFunName="formatStatus" sortable="false"/>
        <fd:dgCol title="{com.glaway.ids.common.lable.remark}" field="remake" width="75" sortable="false"/>
    </fd:datagrid>
</fd:panel>
<fd:dialog id="saveAndSubmitDialog" width="530px" height="200px" modal="true"
           title="{com.glaway.ids.rdflow.plan.toApprove}">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="saveAndSubmitOk"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
</body>

<script>
    function formatCreator(val, row, index) {
        var realName=row.createName;
        if (realName != undefined && realName != null && realName != '') {
            return row.createFullName + "-" + row.createName;
        } else {
            return "";
        }
    }
    function dateFormatter(val, row, index) {
        //return dateFmtYYYYMMDD(val);
        return val.substr(0,11);
    }

    function operation(val, row, index){
        var returnStr ='<span style="cursor:hand"><a onclick="showVersionDetail2(\''+row.id+'\',\''+row.bizVersion+'\')" class="basis ui-icon-eye" title="查看" style="display: inline-block;cursor:hand;"></a></span>';
        return returnStr;
    }

    function operation2(val, row, index){
        var returnStr ='<span style="cursor:hand"><a style="color:blue" onclick="showVersionDetail2(\''+row.id+'\',\''+val+'\')">'+val+'</a></span>';
        return returnStr;
    }

    function showVersionDetail2(id,val){
        var url="${webRoot}/tabCombinationTemplateController.do?goTabCbTemplateLayout&viewHistory=viewHistory&id="+id;
        createdetailwindow(val+'版本详情',url, 1000, 500);

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
<fd:dialog id="editFlowPt" height="900px"  width="1440px" title="查看" max="false" min="false" maxFun="true"></fd:dialog>

