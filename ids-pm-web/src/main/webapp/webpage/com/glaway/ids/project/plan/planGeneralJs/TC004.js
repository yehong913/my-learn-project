//资源ResourceLinkInfo
window.TC004 = new Object();

!function(){
    var _this =  window.TC004;

    var isPlanChange;

    try{
        isPlanChange = $("#isPlanChange").val();
    }catch (e) {

    }

    _this.loadData = function(this_){
        debugger;
        var datas="";
        var flg=false;
        if(this_ != null && this_ != '' && this_ != undefined){
            datas=this_;
        }else{
            datas={useObjectId : $('#useObjectId').val(),useObjectType : $('#useObjectType').val()}
        }

        var url = 'resourceLinkInfoController.do?listForPlan';
        if(isPlanChange != "" && isPlanChange != undefined && isPlanChange != "undefined"){
            if(isPlanChange == "planChange"){
                url = 'planChangeController.do?resourceList';
            }else if(isPlanChange == "planChangeView"){
                url = 'planChangeController.do?resourceListView';
            }
        }
        $.ajax({
            type : 'POST',
            url : url,
            async : false,
            data : datas,
            success : function(data) {
                try {
                    $("#resourceList").datagrid("loadData",data);
                    $('#resourceList').datagrid('unselectAll');
                }
                catch(e) {
                    //alert('3e:'+e)
                }
                finally {
                    flg= true;
                }
            }
        });
        return flg;
    }

// 维护资源
    _this.addResource = function() {
        var gridname = 'resourceList';

        var rows22 = $('#resourceList').datagrid('getRows');
        var t = $('#resourceList');
        for (var i = 0; i < rows22.length; i++) {
            t.datagrid('endEdit', i);
        }
        currentIndex = undefined;
        editIndex = undefined;
        //editFormElementIndex = undefined;
        _this.modifyResourceMass2();
        $('#resourceList').datagrid('unselectAll');

        var planStartTime = $('#Plan-planStartTime').datebox('getValue');
        var planEndTime = $('#Plan-planEndTime').datebox('getValue');

        var dialogUrl = 'resourceLinkInfoController.do?goAdd&useObjectId='+ $('#useObjectId').val()
            + '&useObjectType='	+ $('#useObjectType').val()
            + '&planStartTime='	+ planStartTime
            + '&planEndTime='	+ planEndTime;
        if(isPlanChange != "" && isPlanChange != undefined && isPlanChange != "undefined"){
            try{
                dialogUrl = 'planChangeController.do?goAddResourceTemp&useObjectId='
                    + $('#useObjectId').val()
                    + '&useObjectType='
                    + $('#useObjectType').val()
                    + '&planStartTime='
                    + $('#Plan-planStartTime').datebox('getValue')
                    + '&planEndTime='
                    + $('#Plan-planEndTime').datebox('getValue');
            }catch (e) {

            }

        }
        createDialog('addResourceDialog',dialogUrl);
    }


// 删除资源
    _this.deleteSelections2 = function (gridname, url) {
        if(isPlanChange != "" && isPlanChange != undefined && isPlanChange != "undefined"){
            if(isPlanChange == "planChange"){
                url = 'planChangeController.do?doDelResource';
            }else if(isPlanChange == "planChangeView"){
                url = 'planChangeController.do?doDelResourceEdit';
            }
        }
        var rows = $("#"+gridname).datagrid('getSelections');

        var rows22 = $('#resourceList').datagrid('getRows');
        var t = $('#resourceList');
        for (var i = 0; i < rows22.length; i++) {
            t.datagrid('endEdit', i);
        }
        currentIndex = undefined;
        editIndex = undefined;
        //editFormElementIndex = undefined;
        _this.modifyResourceMass2();
        var ids = [];
        if (rows.length > 0) {
            top.Alert.confirm('确定删除该记录？',
                function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            if(isPlanChange == "planChangeView" || isPlanChange == "planChange"){
                                ids.push(rows[i].resourceId);
                            }else{
                                ids.push(rows[i].id);
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
                                for(var i=rows.length-1;i>-1;i--){
                                    var a = $("#"+gridname).datagrid('getRowIndex',rows[i]);
                                    $("#"+gridname).datagrid('deleteRow',$("#"+gridname).datagrid('getRowIndex',rows[i]));

                                }
                                $("#"+gridname).datagrid('clearSelections');
                                if(isPlanChange == "planChangeView"){
                                    _this.initResource2('');
                                }else{
                                    _this.loadData();
                                }

                                $('#addResourceCancelBtn').focus();
                            }
                        });
                    }
                });
        } else {
            top.tip('请选择需要删除的记录');
        }
    }

    _this.initResource2 = function(this_) {
        var datas="";
        var flg=false;
        if(this_ != null && this_ != '' && this_ != undefined){
            datas=this_;
        }else{
            datas={useObjectId : $('#useObjectId').val(),useObjectType : $('#useObjectType').val()}
        }
        $.ajax({
            type : 'POST',
            url : 'planChangeController.do?resourceList',
            async : false,
            data : datas,
            success : function(data) {
                try {
                    $("#resourceList").datagrid('unselectAll');
                    $("#resourceList").datagrid("loadData",data);
                }
                catch(e) {
                    //alert('3e:'+e)
                }
                finally {
                    flg= true;
                }
            }
        });
        return flg;
    }

    _this.modifyResourceMass2 = function(){
        var rows = $('#resourceList').datagrid('getRows');
        var ids = [];
        var useRates = [];
        var startTimes = [];
        var endTimes = [];

        if(rows.length > 0){
            for(var i = 0; i < rows.length; i++){
                ids.push(rows[i].id);
                useRates.push(rows[i].useRate);
                startTimes.push(rows[i].startTime);
                endTimes.push(rows[i].endTime);
            }

            $.ajax({
                url : 'resourceLinkInfoController.do?modifyResourceMass',
                type : 'post',
                data : {
                    ids : ids.join(','),
                    useRates : useRates.join(','),
                    startTimes : startTimes.join(','),
                    endTimes : endTimes.join(','),
                },
                cache : false,
                success : function(data) {
                }
            });
        }else{
        }
    }

// 资源名称链接事件
    _this.resourceNameLink = function(val, row, value) {
        return '<a href="#" onclick="TC004.viewResourceCharts(\'' + row.id + '\')" style="color:blue">' + val + '</a>';
    }

// 资源名称链接事件
    _this.viewResourceCharts = function(id){
        var rows = $("#resourceList").datagrid('getRows');
        var index = $("#resourceList").datagrid('getRowIndex', id);
        var row = rows[index];
        var id = $("#id").val();

        var planStartTime=$('#Plan-planStartTime').datebox('getValue');
        var planEndTime=$('#Plan-planEndTime').datebox('getValue');

        if(row.useRate != null && row.useRate != '' && row.startTime != null && row.startTime != '' && row.endTime != null && row.endTime != ''){
            createDialog('resourceDialog',"resourceLinkInfoController.do?goToToBeUsedRateReport&resourceId="
                + row.resourceInfo.id + "&resourceLinkId=" + row.id + "&resourceUseRate=" + row.useRate
                + "&startTime=" + row.startTime + "&endTime=" + row.endTime + "&useObjectId="+id+"");
        }
        else{
            top.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyUseInfo"/>');
        }
    }

// 使用百分比
    _this.viewUseRate2 = function (val, row, value){
        return _this.progressRateGreen2(val);
    }

    _this.progressRateGreen2 = function (val){
        if(val == undefined || val == null || val == ''){
            val = 0;
        }
        return val + '%' ;
    }

    function getVersion(val, row, value){
        return "";
    }

    function getStatus(val, row, value){
        return "";
    }

    _this.goNextPage = function(){
        return true;
    }

    _this.checkData = function () {
        return true;
    }

    _this.doSaveData = function(){
        return true;
    }

    _this.isEditor = function(){
        debugger
        if(enterType=='2' || onlyReadonly=='1'){
            $("#新增Div").hide();
            $("#删除Div").hide();
            $("#修改Div").hide();
        }else{
            $("#新增Div").show();
            $("#删除Div").show();
            $("#修改Div").show();
        }
    }


    // 维护资源
    _this.modifyResource = function(){
        var dataGrideId = "resourceList";
        var rows = $('#'+dataGrideId).datagrid('getSelections');
        if (rows.length == 0) {
            top.tip("请选择一条数据");
            return false;
        }
        if (rows.length > 1) {
            top.tip("当前操作只能选择一条数据");
            return false;
        }

        var row;
        index = $('#'+dataGrideId).datagrid('getRowIndex',
            $('#'+dataGrideId).datagrid('getSelected'));
        var all = $('#'+dataGrideId).datagrid('getRows');
        for (var i = 0; i < all.length; i++) {
            var inx = $('#'+dataGrideId).datagrid('getRowIndex', all[i]);
            if (inx == index) {
                row = all[i];
            }
        }
        var planStartTime = $('#Plan-planStartTime').datebox('getValue');
        var planEndTime = $('#Plan-planEndTime').datebox('getValue');


        var dialogUrl = 'resourceLinkInfoController.do?goModifyResourceForPlan' + '&id=' + row.id;
        dialogUrl = dialogUrl + '&useObjectId='+$('#useObjectId').val();
        dialogUrl = dialogUrl + '&startTime='+planStartTime;
        dialogUrl = dialogUrl + '&endTime='+planEndTime;
        if(isPlanChange != "" && isPlanChange != undefined && isPlanChange != "undefined"){
            dialogUrl = dialogUrl + '&isPlanChange='+isPlanChange;
        }
        createDialog('modifyResourceDialog',dialogUrl);
    }

}();
var enterType = "0";
var onlyReadonly;

$(function() {

    TC004.isEditor();

});

