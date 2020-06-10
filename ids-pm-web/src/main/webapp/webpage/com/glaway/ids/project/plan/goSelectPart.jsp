<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="/context/mytags.jsp"%>
<html>
<head>
    <t:base type="jquery,easyui,tools"></t:base>
    <title>现有部件</title>
</head>
<body>
<script type="text/javascript">
    //重置
    function resetParam(){
        $('#partName').textbox('setValue','');
        $('#partNumber').textbox('setValue','');
        $('#context').combobox('clear');
        $('#goods').combobox('clear');
        $('#status').combobox('clear');
        $('#kind').textbox('setValue','');
    }

    //查询
    function queryPartByCondition(){

        var queryParams = $("#plmPartDatagrid").fddatagrid('queryParams', "plmPartSearchForm");
        queryParams['CheckOutInfoVO.contextName'] = '';
        $('#plmPartSearchForm').find('*').each(function() {
            if ($(this).attr('name') == 'CheckOutInfoVO.contextName') {
                if(queryParams['CheckOutInfoVO.contextName'] == null ) {
                    queryParams["CheckOutInfoVO.contextName"] = "'" + $(this).val() + "'";
                } else {
                    queryParams["CheckOutInfoVO.contextName"] += ", '" + $(this).val() + "'";
                }
            }
        });

        $('#plmPartDatagrid').datagrid(
            {
                url : 'idsPartController.do?getAllExistPart&partId=${partId}&productId=${productId}&viewId=${viewId}',
                queryParams : queryParams,
                pageNumber : 1
            });
        $('#plmPartDatagrid').datagrid('unselectAll');
    }


    function endItemShow(val){
        var show = '';
        if(val == 'false'){
            show = "否";
        }else if(val =='true'){
            show = "是";
        }
        return show;
    }


    //新增部件实例选择部件保存
    function selectPartForInstance(){
        var chooseRows = $("#plmPartDatagrid").datagrid('getSelections');
        if(chooseRows.length != 1){
            top.Alert.tip('请选择一个部件');
            return;
        }
        return chooseRows[0];
    }

    //新增现有部件保存
    function saveExistPart(){
        var partId = '${partId}';
        if( partId == "undefined" ){
            top.Alert.tip("此节点不能添加部件");
            return;
        }
        var chooseRows = $("#plmPartDatagrid").datagrid('getSelections');
        if(chooseRows.length == 0){
            top.Alert.tip('请选择部件');
            return;
        }

        //记录选中的需要新建立关系的partId
        var addIdArray=[];
        for(var i=0;i<chooseRows.length;i++){
            addIdArray.push(chooseRows[i].id);
        }

        //ajax请求判断子项是否已添加
        $.ajax({
            url: "idsPartController.do?isSonPartExist",
            data: {
                fatherPartId: partId,
                addIdArray:JSON.stringify(addIdArray)
            },
            type: "POST",
            async: false,
            success: function(data) {
                var result=$.parseJSON(data);
                if(result.attributes.isExist){//子部件已添加
                    top.Alert.confirm('确认:子项已存在。是否再次添加？您所添加的项已经是父项的一个子项。',
                        function(r) {
                            if(r) {
                                insertSonPart(chooseRows,partId);
                            }
                        }
                    );
                }else{//子部件未添加
                    insertSonPart(chooseRows,partId);
                }
            }
        });

    }

    /**
     * 插入子部件
     */
    function insertSonPart(chooseRows,partId){

        //过滤已有替换部件
        var win = $.fn.lhgdialog('getSelectParentWin');
        var partArr = [];
        if('${sub}' == "alt") {
            var altIdsArr = win.doGetFilterAlterPartIds();
            for(var i= 0; i < chooseRows.length; i++) {
                if ($.inArray(chooseRows[i].bizId , altIdsArr) == -1) {
                    partArr.push(chooseRows[i]);
                }
            }
        } else if('${sub}' == "sub"){
            var subIdsArr = win.doGetFilterSubPartIds();
            for(var i= 0; i < chooseRows.length; i++) {
                if ($.inArray(chooseRows[i].bizId , subIdsArr) == -1) {
                    partArr.push(chooseRows[i]);
                }
            }
        }

        var partIds = '';
        var partIdForNew = '';
        for (var i=0; i<chooseRows.length-1; i++) {
            for (var j=i+1; j<chooseRows.length; j++) {
                if (chooseRows[i].bizId == chooseRows[j].bizId) {
                    chooseRows.splice(j,1);
                    break;
                }
            }
        }

        for(var i=0; i<chooseRows.length; i++){
            if(i<chooseRows.length-1){
                partIds += chooseRows[i].bizId + ',';
                partIdForNew += chooseRows[i].id + ',';
            }else{
                partIds += chooseRows[i].bizId;
                partIdForNew += chooseRows[i].id;
            }
        }
        var url;
        if('${sub}' == "new"){
            url = 'idsPartController.do?insertExistPart&sub=${sub}&lazy=${lazy }&partId='+partId;//+'&partIds='+partIds
        }else{
            url = 'idsPartController.do?saveExistPart&sub=${sub}&partId='+partId+'&roleAId=${roleAId}';
        }
        top.progress("open");

        $.ajax({
            url : url,
            type : 'post',
            cache : false,
            data : {
                partArray : JSON.stringify(partArr),
                partIds : partIds,
                partIdForNew : partIdForNew,
            },
            success : function(data) {
                var d = $.parseJSON(data);
                if(d.success){
                    top.tip(d.msg);
                    //特定部件
                    var win = $.fn.lhgdialog('getSelectParentWin');
                    if('${sub}' == "new"){
                        //插入现有的
                        //$.fn.lhgdialog('getSelectParentWin').$("#BOMTree").ztree("refresh");
                        var treeNodes = d.obj;
                        var selectNodes=win.getTreeObject().getSelectedNodes();
                        var selectNode=selectNodes[0];
                        var parentNode = selectNode.getParentNode();
                        var nodeid = treeNodes[0].id;
                        // 全部移除自己，重新给父节点增加节点
                        // 如果该节点是父节点则移除所有子节点
                        if(selectNode.isParent){
                            //移除所有子节点
                            win.getTreeObject().removeChildNodes(selectNode);
                        }
                        //移除自身节点
                        win.getTreeObject().removeNode(selectNode);
                        win.getTreeObject().addNodes(parentNode,treeNodes);
                        var node = win.getTreeObject().getNodeByParam("id",nodeid,parentNode);
                        win.getTreeObject().selectNode(node, true); // 选中指定节点
                        //获取选中的节点，查找出其他的一样的节点
                        var array = win.getTreeObject().getNodesByParamFuzzy("name",selectNode.name);
                        for(var j=0;j<array.length;j++){
                            //判断是否是选中的节点
                            if(treeNodes[0].id != array[j].id){
                                var selectNodeF = array[j];
                                //查找父节点
                                var parentNodeF = selectNodeF.getParentNode();
                                //如果当前的节点是父节点，移除所有的子节点
                                if(selectNodeF.isParent){
                                    //移除所有子节点
                                    win.getTreeObject().removeChildNodes(selectNodeF);
                                }

                                //删除自身
                                win.getTreeObject().removeNode(selectNodeF);
                                //和选中的节点一样的操作
                                win.getTreeObject().addNodes(parentNodeF,treeNodes);
                            }
                        }
                        if (treeNodes[0].dataObject.stateCheckOutInfo == '1') {
                            treeNodes[0].name = treeNodes[0].name + "(工作副本)";
                        }
                        //更新tab
                        win.updateTab(partId, treeNodes[0]);
                        win.loadRightPage(node);
                    }else if('${sub}' == "alt"){
                        //全局替换部件新增刷新页面
                        if('${entry}' == 'managePart') {
                            var alterJson = $.toJSON(d.obj);
                            var alterArr = $.parseJSON(alterJson);
                            win.addReplacePartRow('alternatePartDatagrid', alterArr);
                        } else {
                            win.reloadDatagrid();
                        }
                    }else if('${sub}' == "sub"){
                        //特定替换部件新增刷新页面
                        if('${entry}' == 'managePart') {
                            var subJson = $.toJSON(d.obj);
                            var subArr = $.parseJSON(subJson);
                            win.addReplacePartRow('manageReplacePartDatagrid', subArr);
                        } else {
                            win.reloadDatagrid();
                        }
                    }
                    top.progress("close");
                    $.fn.lhgdialog('closeSelect');
                }else{
                    top.tip(d.msg);
                    top.progress("close");
                    return;
                }
            },
            error:function(){
                top.tip('服务器错误');
                top.progress("close");
            }
        });
    }

    //校验是否为空
    function checkedIsEmpty(obj){
        if(obj==null||obj==undefined||$.trim(obj)==""){
            return true;
        }else{
            return false;
        }
    }

    function getSelectedItems(){
        var chooseRows = $("#plmPartDatagrid").datagrid('getSelections');
        var partArray = [];
        for(var i=0; i<chooseRows.length; i++){
            partArray.push({id:chooseRows[i].id, masterId:chooseRows[i].bizId, name:chooseRows[i].name, code:chooseRows[i].code, viewId:chooseRows[i].viewId});
        }
        return partArray;
    }

    function getObjectTypeFlagImg(value, row, index){
        var img ="";
        img = '<span title="' + row.typename + '" class="' + row.typeicon + '" style="width:16px;height:16px;display:inline-block"></span>';
        return img;
    }


</script>

<div id="plmPartDatagridTools">
    <fd:searchform id="plmPartSearchForm"  onClickResetBtn="resetParam()" onClickSearchBtn="queryPartByCondition()" isMoreShow="false">
        <fd:inputText title="名称" id="partName" name="name" queryMode="like" />
        <fd:inputText title="编号" id="partNumber" name="code" queryMode="like" />
        <fd:combobox id="context" title="上下文" name="CheckOutInfoVO.contextName" textField="title" panelMaxHeight="100" panelHeight="100"  queryMode="in" multiple="true"
                     valueField="id" editable="false" url="idsPartController.do?getContextList"></fd:combobox>
        <fd:combotree name="kind" id="kind"
                      title="类型"
                      url="idsIntegratedPLMController.do?getTypeCombotreeList&typeId=4028f00c549ecc8401549eea71659524"
                      editable="false" treePidKey="pid" treeIdKey="id"
                      onChange="documentTypeAttrShow()"
                      panelMaxHeight="100" panelHeight="100" multiple="false" value="" queryMode="like" />
        <fd:combobox id="goods" title="是否成品" textField="text" name="goods"
                     valueField="value" data="true_是,false_否" editable="false" queryMode="like"></fd:combobox>
        <fd:combobox id="status" title="状态" textField="text" name="status"
                     valueField="value" data="publish_已发布,working_正在工作" editable="false" queryMode="like"></fd:combobox>
    </fd:searchform>
</div>
<fd:datagrid id="plmPartDatagrid" toolbar="plmPartDatagridTools" searchFormId="#plmPartSearchForm" checked="true" idField="id"
             checkOnSelect="false" fit="true" fitColumns="true" sortOrder="true" url="idsPartController.do?getAllExistPart&partId=${partId}&productId=${productId}&viewId=${viewId}" checkbox="${entry == 'effectivity' ? false : true}">
    <fd:dgCol field="id" title="id" hidden="true" />
    <fd:dgCol field="bizId" title="bizId"  hidden="true"/>
    <fd:dgCol field="objectTypeIdentify" width="10" title="类型" formatterFunName="getObjectTypeFlagImg" sortable="false" />
    <fd:dgCol field="code" title="编号"  sortable="false"   />
    <fd:dgCol field="name" title="名称"   sortable="false"  />
    <fd:dgCol field="bizVersion" title="版本"  sortable="false"   />
    <fd:dgCol field="status" title="状态"  sortable="false"   />
    <fd:dgCol field="remark" title="是否成品"  formatterFunName="endItemShow"  sortable="false" />
    <fd:dgCol field="contextName" title="上下文"  sortable="false" />
</fd:datagrid>
</body>
</html>