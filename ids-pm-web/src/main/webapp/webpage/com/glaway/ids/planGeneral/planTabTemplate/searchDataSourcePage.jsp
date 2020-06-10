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
        <div id="dataSourceObjectManageBar" style="padding: 3px;height: auto">
            <!-- 查询条件栏 -->
            <fd:searchform id="dataSourceObjecManageSearch" onClickSearchBtn="searchDataSourceObjecManage();" onClickResetBtn="dataSourceObjecManageSearchreSet()" isMoreShow="false">
                <fd:combobox  title="应用标识" name="projectModel" id="dataSourceObject_projectModel" textField="text"
                              editable="false" valueField="value" data="ids-pm-service_ids-pm-service,ids-common-service_ids-common-service,ids-quality-service_ids-quality-service,ids-rdflow-service_ids-rdflow-service,ids-review-service_ids-review-service,ids-riskproblems-service_ids-riskproblems-service" value="ids-pm-service" queryMode="eq"></fd:combobox>
                <fd:inputText title="对象类路径" name="objectPath" id="dataSourceObject_objectPath" queryMode="like" maxLength="-1"/>
            </fd:searchform>
        </div>
        <input type="button" id="btn_sub" style="display: none;" onclick="formSubmit();">

        <!-- 数据展示区Datagrid -->
        <fd:datagrid url="dataSourceObjectController.do?searchDatagrid" toolbar="#dataSourceObjectManageBar" fit="true" fitColumns="true" idField="id" id="dataSourceObjectList">
            <fd:dgCol title="对象类路径" field="objectPath" width="100"  sortable="true" formatterFunName="objectPathValue"/>
            <fd:dgCol title="表名" field="tableName" width="100"   sortable="true"/>
            <fd:dgCol title="对象字段集合" field="objectModelProperty"  hidden="true" />
        </fd:datagrid>
    </div>
</div>

<script>
    function objectPathValue(value,row,index){
        var objValueArray = value.split("com.glaway.");
        return objValueArray[1];
    }

    //查询事件
    function searchDataSourceObjecManage() {
        //获取查询条件值
        var queryParams = $("#dataSourceObjectList").fddatagrid('queryParams', "dataSourceObjecManageSearch");
        //数据查询
        $('#dataSourceObjectList').datagrid({
            url : 'dataSourceObjectController.do?searchDatagrid',
            queryParams : queryParams,
            pageNumber : 1
        });
    }

    //重置按钮事件
    function dataSourceObjecManageSearchreSet(){
        //把所有查询条件值置空
        $("#dataSourceObject_objectPath").textbox("setValue","");
        $("#dataSourceObject_projectModel").combobox("setValue", "ids-pm-service");
    }

    //数据提交
    function formSubmit() {
        //获取选中行数据
        var rows = $("#dataSourceObjectList").datagrid('getSelections');
        //判断是否选择行
        if(rows.length > 0){
            //判断是否指选择一条数据
            if (rows.length==1){
                //弹出确认框
                top.Alert.confirm('是否选择当前数据？', function(r) {
                    if (r) {
                        //获取选择的数据
                        var objectPath = rows[0].objectPath;
                        var tableName = rows[0].tableName;
                        var projectModel = $("#dataSourceObject_projectModel").combobox("getValue");
                        var objectModelProperty = rows[0].objectModelProperty;
                        var win = $.fn.lhgdialog("getSelectParentWin");
                        var isRepeat = win.repeatRemove(objectPath);
                        if (isRepeat){
                            top.tip("该数据已经存在，请重新选择数据");
                            return false;
                        }
                        var tabId = win.tabId;
                        //后台执行保存数据源对象
                        $.ajax({
                            url : 'dataSourceObjectController.do?doSave',
                            type : 'post',
                            data : {
                                tabId: tabId,
                                objectPath: objectPath,
                                tableName : tableName,
                                projectModel: projectModel,
                                objectModelProperty: objectModelProperty
                            },
                            cache : false,
                            success : function(data) {
                                var d = $.parseJSON(data);
                                if (d.success) {
                                    var obj = d.obj;
                                    //查找对象数据进行保存赋值
                                    win.addDataSourceObjectInfo(obj.id, objectPath, obj.objectModelProperty, projectModel);
                                    //关闭窗口
                                    $.fn.lhgdialog("closeSelect");
                                }else {
                                    top.tip("数据选择失败");
                                    return false;
                                }
                            }
                        });
                    }
                });
            } else {
                top.tip('当前操作只能选择一条数据');
                return false;
            }
        }else{
            top.tip('请选择一条查询的数据');
            return false;
        }
    }
</script>