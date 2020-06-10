//输出DeliverableInfo

window.TC003 = new Object();

!function(){

    var _this =  window.TC003;
    var enterType = "0";
    var onlyReadonly;
    var fromType;
    var fromType2;
    var fromType3;
    var isPlanChange;
    try{
        fromType2 = $("#taskNameType").val();


    }catch (e) {

    }

    try{
        fromType3 = $('#Plan-taskNameType').combobox('getValue');
    }catch (e) {

    }

    try{
        fromType = $("#taskNameType").combobox('getValue');
    }catch (e) {

    }

    try{
        isPlanChange = $("#isPlanChange").val();
    }catch (e) {

    }

    if (fromType==undefined){
        fromType = fromType2;
    }

    var parentPlanId;
    try{
        parentPlanId = $('#parentPlanId').val();
    }catch (e) {

    }

    var parentPlanId;
    try{
        parentPlanId = $('#parentPlanId').val();
    }catch (e) {

    }

    _this.loadData = function(this_){
        debugger;
        _this.isShowColumn();
        _this.isEditor();
        _this.isShowButton();
        var datas = new Object();
        var flg=false;
        if(this_ != null && this_ != '' && this_ != undefined){
            datas=this_;
        }else{
            //datas={useObjectId : $('#useObjectId').val(),useObjectType : $('#useObjectType').val()}
            datas.useObjectId = $('#useObjectId').val();
            datas.useObjectType = $('#useObjectType').val();
        }

        var url = 'deliverablesInfoController.do?list';
        if(isPlanChange != "" && isPlanChange != undefined && isPlanChange != "undefined"){
            if(isPlanChange == "planChange"){
                url = 'planChangeController.do?documentList';
            }else if(isPlanChange == "planChangeView"){
                url = 'planChangeController.do?documentListView';
            }

        }

        //alert($.toJSON(datas))
        $.ajax({
            type : 'POST',
            url : url,
            async : false,
            data : datas,
            success : function(result) {
                try {
                    $("#deliverablesInfoList").datagrid("loadData",result['rows']);
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

// 继承父项
    _this.inheritParent = function() {
        var planId = $("#id").val();
        var parentPlanId = $("#parentPlanId").val();
        var dialogUrl = 'deliverablesInfoController.do?goAddInherit&planId='+planId+'&parentPlanId='+parentPlanId+'&useObjectId='
            + $('#useObjectId').val()
            + '&useObjectType='
            + $('#useObjectType').val();
        if(isPlanChange == "planChange"){
            planId = $("#planId").val();
            dialogUrl = 'planChangeController.do?goAddTempInherit&planId='+planId+'&parentPlanId=1'
        }
        createDialog('inheritDialog', dialogUrl);
    }

// 新增交付项
    _this.addDeliverable = function () {
        debugger;
        gridname = 'deliverablesInfoList';
        var dialogUrl = 'deliverablesInfoController.do?goAdd&useObjectId='
            + $('#useObjectId').val()
            + '&useObjectType='
            + $('#useObjectType').val();
        if(isPlanChange != "" && isPlanChange != undefined && isPlanChange != "undefined"){
            dialogUrl = 'planChangeController.do?goAddDocumentTemp&useObjectId='
                + $('#useObjectId').val()
                + '&useObjectType='
                + $('#useObjectType').val();
        }
        createDialog('addDeliverableDialog',dialogUrl);

    }

// 删除交付项
    _this.deleteSelections = function(gridname, url) {
        debugger;
        if(isPlanChange != "" && isPlanChange != undefined && isPlanChange != "undefined"){
            if(isPlanChange == 'planChange'){
                url = 'planChangeController.do?doDelDocument';
            }else if(isPlanChange == 'planChangeView'){
                url = 'planChangeController.do?doDelDocumentEdit';
            }
        }
        var rows = $("#"+gridname).datagrid('getChecked');
        for(var i =0;i<rows.length;i++){
            if(rows[i].origin != ''&&rows[i].origin !=null&&rows[i].origin !=undefined){
                top.tip('"' + rows[i].name + '"为标准交付项，不能删除，请重新选择');
                return false;
            }
        }

        var ids = [];
        if (rows.length > 0) {
            top.Alert.confirm('确定删除该记录？',
                function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            if(isPlanChange != "" && isPlanChange != undefined && isPlanChange != "undefined"){
                                if(isPlanChange == 'planChange'){
                                    ids.push(rows[i].deliverablesId);
                                }else if(isPlanChange == 'planChangeView'){
                                    ids.push(rows[i].id);
                                }
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
                                if(isPlanChange == 'planChangeView'){
                                    _this.initDocument2('');
                                }else {
                                    _this.loadData();
                                }

                                $('#addOutputCancelBtn').focus();
                            }
                        });
                    }
                });
        } else {
            top.tip('请选择需要删除的记录');
        }
    }

    _this.initDocument2 = function(this_) {
        var datas="";
        var flg=false;
        if(this_ != null && this_ != '' && this_ != undefined){
            datas=this_;
        }else{
            datas={useObjectId : $('#useObjectId').val(),useObjectType : $('#useObjectType').val()}
        }
        $.ajax({
            type : 'POST',
            url : 'planChangeController.do?documentList',
            async : false,
            data : datas,
            success : function(data) {
                try {
                    $("#deliverablesInfoList").datagrid('unselectAll');
                    $("#deliverablesInfoList").datagrid("loadData",data);
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

//计划名称链接事件
    _this.getDeliverableName = function(val, row, value) {
        if(row.origin != undefined){
            return '<a style="color:gray">' + row.name + '</a>';
        }else{
            return '<a style="color:black">' + row.name + '</a>';
        }
    }

    _this.goNextPage = function(){
        return true;
    }

    _this.addLink = function (val, row, value) {
        debugger
        if (row.docId!=null&&row.docId!='' && row.docName != '' && row.docName != null) {
            return "<a href='#' onclick='TC003.showDocDetail(\""
                + row.docId
                + "\""
                + ','
                + "\""
                + true
                + "\""
                + ','
                + "\""
                + true
                + "\")'  id='myDoc'  style='color:blue'>" + row.docName +"</a>";
        } else
            return ;
    }

    _this.showDocDetail = function (id,download,detail) {
        var url="projLibController.do?viewProjectDocDetail&opFlag=1&id="+id+ "&download=" + download+ "&detail=" + detail;
        createDialog('openDocumentInfoDialog',url);
    }

    _this.checkData = function () {
        return true;
    }

    _this.doSaveData = function(){
        return true;
    }

    _this.isShowColumn = function(){

        if(fromType=='4028f00d6db34426016db365b27c0000'||fromType3=='PLM计划类'){
            setTimeout(function(){
                $('#deliverablesInfoList').datagrid('hideColumn', 'docName');
                $('#deliverablesInfoList').datagrid('hideColumn', 'deleteFlag');
                $('#deliverablesInfoList').datagrid('hideColumn', 'ext2');
                $('#deliverablesInfoList').datagrid('hideColumn', 'ext3');
                $('#deliverablesInfoList').datagrid('hideColumn', 'required');
                $('#deliverablesInfoList').datagrid('hideColumn', 'orginType');
            }, 10);
        }else{
            if(enterType=='2'){
                $('#deliverablesInfoList').datagrid('hideColumn', 'deleteFlag');
            }else{
                setTimeout(function(){
                    $('#deliverablesInfoList').datagrid('hideColumn', 'deleteFlag');
                    $('#deliverablesInfoList').datagrid('hideColumn', 'ext2');
                    $('#deliverablesInfoList').datagrid('hideColumn', 'ext3');
                    $('#deliverablesInfoList').datagrid('hideColumn', 'required');
                    $('#deliverablesInfoList').datagrid('hideColumn', 'orginType');
                }, 10);
            }
        }
    }

    _this.isEditor = function(){
        if(enterType=='2' || onlyReadonly=='1'){
            $("#新增Div").hide();
            $("#删除Div").hide();
            $("#PLM系统关联Div").hide();
            $("#继承父项Div").hide();
        }else if(enterType=='0'){
            $("#PLM系统关联Div").hide();
        }
        else{
            $("#新增Div").show();
            $("#删除Div").show();
            $("#PLM系统关联Div").show();
            $("#继承父项Div").show();
        }
    }

    _this.isShowButton = function(){
        if(enterType=='2'){
            $("#提交Div").show();
            $("#本地上传挂接Div").show();
            $("#从项目库查找挂接Div").show();
        }else{
            $("#提交Div").hide();
            $("#本地上传挂接Div").hide();
            $("#从项目库查找挂接Div").hide();
        }
        if(parentPlanId == "" ){
            $("#继承父项Div").hide();
        }
    }


}();





