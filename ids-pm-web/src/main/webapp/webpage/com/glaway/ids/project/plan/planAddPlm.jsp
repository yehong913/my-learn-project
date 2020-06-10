<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>选择对象</title>
    <t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
<div id="selectObjectsExceptBaselineTools">
    <fd:searchform id="selectObjectsExceptBaselineToolsForm" isMoreShow="false" onClickResetBtn="searchResetGrid('selectObjectsExceptBaseline')"  onClickSearchBtn="searchGrid('selectObjectsExceptBaseline')" >

        <fd:combobox id="objectContextName" textField="title" title="上下文"
                     name="CheckOutInfoVO.contextName" editable="false" valueField="title"
                     multiple="true" url="idsIntegratedPLMController.do?getPrimaryObjectContext"
                     queryMode="like" panelHeight="100"></fd:combobox>

        <fd:combobox title="类型" name="CheckOutInfoVO.objectClass" id="objectObjectClasss" valueField="id" textField="text"
                     data="com.glaway.plm.part.entity.Part_部件,com.glaway.plm.doc.entity.Document_文档,com.glaway.plm.epm.entity.EPMDocument_图档"
                     selectedValue="" editable="false" queryMode="in" multiple="true" prompt="全部" />


        <fd:combobox id="objectStatus" textField="title" title="状态"
                     name="CheckOutInfoVO.status" panelMaxHeight="100" valueField="name"
                     multiple="true" url="idsIntegratedPLMController.do?getStatusListForLib"
                     queryMode="like"></fd:combobox>

        <fd:inputText title="名称" name="CheckOutInfoVO.name" id="objectName" queryMode="like" />

        <fd:inputText title="编号" name="CheckOutInfoVO.code" id="objectCode" queryMode="like" />

    </fd:searchform>
</div>
<fd:datagrid id="selectObjectsExceptBaseline" toolbar="#selectObjectsExceptBaselineTools" checkbox="true" checked="true" pagination="true" searchFormId="#selectObjectsExceptBaselineToolsForm"
             idField="id" checkOnSelect="false" fit="true" fitColumns="true" sortOrder="true" url="idsIntegratedPLMController.do?selectForChangeExceptBaselineList" >
    <fd:dgCol field="id" title="id" hidden="true" sortable="false"/>
    <fd:dgCol field="bizId" title="bizId" hidden="true" sortable="false"/>
    <fd:dgCol field="objectClass" title="类型" formatterFunName="getObjectTypeFlagImg"/>

    <fd:dgCol field="name" title="名称" sortable="false"/>

    <fd:dgCol field="code" title="编号" sortable="false"/>

    <fd:dgCol field="bizVersion" title="版本" formatterFunName="versionFormatter" sortable="false"/>

    <fd:dgCol field="contextName" title="上下文" sortable="false"/>

    <fd:dgCol field="status" title="状态" sortable="false"/>
</fd:datagrid>
<input type="button" id="plm_sub" style="display: none;" onclick="getLoadData();">
<script type="text/javascript">
    function getSelectObjects(){
        var selectObjects =$("#selectObjectsExceptBaseline").datagrid("getSelections");
        return selectObjects;
    }

    //图标展示
    function getObjectTypeFlagImg(value, row, index){
        var img ="";
        img = '<span title="' + row.typename + '" class="' + row.typeicon + '" style="width:16px;height:16px;display:inline-block"></span>';
        return img;
    }

    function versionFormatter(val, row, value) {
        if(row.viewName != ''){
            return row.bizVersion + '(' + row.viewName + ')';
        }
        else{
            return row.bizVersion;
        }
    }

    function getLoadData() {
        var datas;
        var docId = [];
        var name = [];
        var objectClass = [];
        var bizVersion = [];
        var selectObjects = getSelectObjects();
        if (selectObjects.length > 0) {
            for (var i=0;i<selectObjects.length;i++){
                //主键id
                docId.push(selectObjects[i].id);
                //名称
                name.push(selectObjects[i].name);
                //类型
                objectClass.push(selectObjects[i].typename);
                //版本
                bizVersion.push(selectObjects[i].bizVersion.substr(0,1));
            }
            datas= {
                useObjectId : '${useObjectId}',
                useObjectType : '${useObjectType}',
                docId:docId.join(','),
                name:name.join(','),
                fileType:objectClass.join(','),
                versionCode:bizVersion.join(',')
            };
            $.ajax({
                url : 'inputsController.do?setPlmInputs',
                async : false,
                cache : false,
                type : 'post',
                data : datas,
                cache : false,
                success : function(data) {
                }
            });
        }else {
            tip('<spring:message code="com.glaway.ids.pm.project.plan.selectAdd"/>');
        }
        return datas;
    }
    
    function outVaild() {
        var selectObjects = getSelectObjects();
        if (selectObjects.length > 0) {
            if (selectObjects.length!=1){
                tip('当前操作只能选择一条数据');
                return false;
            }else{
                return true;
            }
        }else {
            tip('<spring:message code="com.glaway.ids.pm.project.plan.selectAdd"/>');
            return false;
        }
    }
</script>
</body>
</html>
