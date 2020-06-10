<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>BOM节点</title>
    <t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
        <fd:lazytreegrid id="bomList" idField="id" treeField="code" singleSelect="false"  url="idsPartController.do?getPartBomTree&id=${partId}">
            <fd:columns>
                <fd:column field="code"  title="编号" width="400"></fd:column>
                <fd:column field="name"  title="名称"  width="400"></fd:column>
            </fd:columns>
        </fd:lazytreegrid>
</body>
<script type="text/javascript">

    function sumbit() {
        var flag = true;
        var chooseRows = mygrid_bomList.selectedRows;
        if (chooseRows.length==0){
            tip('<spring:message code="com.glaway.ids.pm.project.plan.selectAdd"/>');
            flag = false;
            return false;
        }
        var planId = '${planId}';
        var projectId = '${projectId}';
        var idPush = [];
        var codePush = [];
        var namePush = [];
        for (var i=0;i<chooseRows.length;i++){
            var obj = chooseRows[i]._attrs;
            if (obj.pid==""){
                tip('不能选择根节点数据');
                flag = false;
                return false;
            }
            idPush.push(obj.id);
            codePush.push(obj.code);
            namePush.push(obj.name);
        }
        var datas;
        datas= {
            planId : planId,
            projectId : projectId,
            bomId:idPush.join(','),
            name:namePush.join(','),
            code:codePush.join(',')
        };
        $.ajax({
            url : 'planController.do?doSaveBom',
            async : false,
            cache : false,
            type : 'post',
            data : datas,
            cache : false,
            success : function(data) {
                tip('数据增加成功');
            }
        });
        return flag;
    }
</script>
</html>


