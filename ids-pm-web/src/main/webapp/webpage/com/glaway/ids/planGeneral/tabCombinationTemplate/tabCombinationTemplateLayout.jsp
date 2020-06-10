<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>页签组合模板历史信息查看</title>
    <t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow: hidden;">

<div class="easyui-layout" fit="true">
    <input type = "hidden" id = "tabCbTemplateId" name = "tabCbTemplateId" value = "${tabCbTemplateId}"/>
    <input id="acessList" name="acessList" type="hidden" value="${acessList}">
    <input id="tabTemplates" name="tabTemplates" type="hidden" value="${tabTemplates}">
    <input id="alltabTemplates" name="alltabTemplates" type="hidden" value="${alltabTemplates}">
    <input id="type" name="type" type="hidden" value="${type}">

    <div region="north" style="width: 100%;height:29%;" title="" id="north_page_panel">
        <fd:inputText id="cbName" name="cbName" title="{com.glaway.ids.pm.project.plantemplate.name}" readonly="true" value = "${template_.name}"/>
        <fd:combobox id="templateName" title="{com.glaway.ids.pm.general.actType}"  textField="name" valueField="id"
                     name="templateName" selectedValue="${template_.activityId}" readonly="true" editable="false"/>
        <fd:inputTextArea id="remake" name="remake" title="{com.glaway.ids.common.lable.remark}" readonly="true" value="${template_.remake}"/>
    </div>

    <div region="south" style="padding: 1px; width: 100%;height:66%;" title="" id="south_page_panel">
        <fd:datagrid id="CbTemplateInfoList" pagination="false" checkOnSelect="true" toolbar="" url="" height="400"  checkbox="false"
                     fitColumns="true" idField="id" fit="true">
            <fd:dgCol title="{com.glaway.ids.pm.general.name}" field="name" width="80"  sortable="false" align="center" ></fd:dgCol>
            <fd:dgCol title="{com.glaway.ids.pm.general.tabName}" field="tabName" width="80" align="center" sortable="false" formatterFunName="formatterTab"></fd:dgCol>
            <fd:dgCol title="{com.glaway.ids.pm.general.tabType}" field="tabType" width="80" align="center" sortable="false"></fd:dgCol>
            <fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.version}" field="bizVersion" width="80" align="center" sortable="false"></fd:dgCol>
            <fd:dgCol title="{com.glaway.ids.pm.general.displayAccess}" field="displayAccess" width="80" align="center" sortable="false" ></fd:dgCol>
        </fd:datagrid>
    </div>

    <script type="text/javascript">
        var CbTemplateInfo = new Object();
        var CbTemplateInfoArr = new Array();   //页面加载时组合模板信息
        var CbTemplateInfos = new Array();   //组合模板信息保存
        var editingindex;//正在修改的行

        $(document).ready(function (){
            var type = $('#type').val();
            if (type == "copy") {
                setTimeout(function(){
                    $('input', $('#cbName').next('span')).focus();
                },600);
            }
            setTimeout(function(){
                $("#templateName").combobox('setValue','${template_.activityId}');
                $("#templateName").combobox('setText','${template_.templateName}');
            },500);
            var tabCbTemplateId = $("#tabCbTemplateId").val();
            //根据id获取组合模板信息
            if (tabCbTemplateId != '') {
                $.ajax({
                    type : 'get',
                    url : 'tabCombinationTemplateController.do?getCombTemplateInfos&tabCbTemplateId='+tabCbTemplateId,
                    cache : false,
                    success : function(data) {
                        var d = $.parseJSON(data);
                        if (d.success) {
                            //将组合模板信息放置集合遍历
                            var infos = d.obj;
                            for (var i = 0; i < infos.length; i++) {
                                var info = infos[i];
                                var CbTemplateInfo = new Object();
                                CbTemplateInfo['id']=info.id;
                                CbTemplateInfo['name']=info.name;
                                CbTemplateInfo['typeId']=info.typeId;
                                CbTemplateInfo['tabName']=info.tabName;
                                CbTemplateInfo['tabType']=info.tabType;
                                CbTemplateInfo['displayAccess']=info.displayAccess;
                                CbTemplateInfo['bizVersion']=info.bizVersion;
                                CbTemplateInfoArr.push(CbTemplateInfo);
                            }
                            reoload();
                        }
                    }
                });
            } else {
                reoload();
            }
        });

        function reoload(){
            if (CbTemplateInfoArr.length > 0) {
                $("#CbTemplateInfoList").datagrid('loadData',CbTemplateInfoArr);
            } else {
                $("#CbTemplateInfoList").datagrid('loadData',new Array());
            }

        }

        //页签模板显示
        function formatterTab(val, row, index) {
            var tabTemplates = eval('${alltabTemplates}');
            for (var i = 0; i < tabTemplates.length; i++) {
                if (val == tabTemplates[i].id) {
                    return tabTemplates[i].name;
                }
                if (val == tabTemplates[i].name) {
                    return tabTemplates[i].name;
                }
            }
        }

    </script>
</div>
</body>
</html>
