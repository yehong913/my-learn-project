//风险清单
window.TC006 = new Object();
var _this =  window.TC006;
var riskproblems_Nginx =  $("#riskproblems_Nginx").val();
var planId = $("#planId").val();
var projectId = $("#projectId").val();
var teamId = $("#teamId").val();
var dataGrideId;
!function(){
    _this.isShowButton = function(){
        if ($("#isOwner").val() !='true') {
            $("#TC006新增Div").hide();
            $("#TC006修改Div").hide();
            $("#TC006删除Div").hide();
            $("#TC006导入Div").hide();
            $("#TC006导出Div").hide();
            $("#TC006评估Div").hide();
            $("#TC006开启Div").hide();
            $("#TC006关闭Div").hide();
        }
    }
}();

$(function() {
    _this.isShowButton();
    var tab = $('#tt').tabs('getSelected');
    var index = $('#tt').tabs('getTabIndex',tab);
    dataGrideId = $("#dataGrideId_"+index).val();
    riskListSearch();
});

function riskListSearch() {
    $('#'+dataGrideId).datagrid(
        {
            url : ''+riskproblems_Nginx+'/riskController.do?searchDatagrid&projectId='+projectId,
            pageNumber : 1
        });
}

//负责人
function formatownerName(val, row, index) {
    return row.ownerName;
}

//评估
function formatAssess(val, row, index) {
    return row.assess;
}

//减缓措施
function formatMeasure(val, row, index) {
    return row.measure;
}

//未关闭问题
function formatUnCloseProblem(val, row, index) {
    return row.unCloseProblem;
}

// 查看风险信息
function viewRisk(id) {
    createDialog('checkRiskDialog',''+riskproblems_Nginx+'/riskModifyController.do?goCheck&teamId='+teamId+'&isCheck=true&id=' + id);
}

// 查看评估信息
function viewAssess(id) {
    var dialogUrl = ''+riskproblems_Nginx+'/riskController.do?goCheckAccess&id='+id;
    createDialog('accessCheckRiskDialog',dialogUrl);
}

// 查看解决措施信息
function viewMeasure(id) {
    var dialogUrl = ''+riskproblems_Nginx+'/riskModifyController.do?goMeasureView&id='+id;
    createDialog('measureCheckRiskDialog',dialogUrl);
}

// 查看未关闭的问题信息
function viewProblem(id) {
    var dialogUrl = ''+riskproblems_Nginx+'/riskProblemController.do?goRiskProblemListCheck&id='+id+'&problemStatus=2';
    createDialog('problemCheckRiskDialog', dialogUrl);
}

//新增风险
function addRiskForPlan() {
    var type = 'pm';
    var dialogUrl = ''+riskproblems_Nginx+'/riskController.do?goAdd&projectId='+projectId+'&type='+type;
    createDialog('addRiskDialog',dialogUrl);
}

//修改风险
function modifyRiskForPlan() {
    var id = '';
    var rows = $('#'+dataGrideId).datagrid('getSelections');
    if(rows.length > 0){
        for(var i = (rows.length-1); i >=0;i-- ){
            id = rows[i].id;
        }
    }else{
        top.tip('请选择需要修改的数据');
        return false;
    }

    var dialogUrl = ''+riskproblems_Nginx+'/riskModifyController.do?goModify&teamId='+teamId+'&id='+id;
    createDialog('modifyRiskDialog',dialogUrl);
}

// 批量删除
function deleteRiskMassForPlan() {
    var rows = $('#'+dataGrideId).datagrid('getSelections');
    var ids = [];
    if(rows.length > 0){
        for(var i = 0; i < rows.length; i++){
            if(rows[i].status == '关闭'){
                top.tip('关闭的风险不能被删除，请重新选择');
                return false;
            }
        }

        for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i].id);
        }

        $.ajax({
            type:'POST',
            data:{
                ids : ids.join(',')
            },
            cache:false,
            url:''+riskproblems_Nginx+'/riskController.do?checkRiskBeforeDel',
            success:function(data){
                var d = $.parseJSON(data);
                if(d.success){
                    top.Alert.confirm('确定删除该条风险？', function(r) {
                        if (r) {

                            $.ajax({
                                url : ''+riskproblems_Nginx+'/riskController.do?doDelRisk&projectId='+projectId,
                                type : 'post',
                                data : {
                                    id : ids.join(',')
                                },
                                cache : false,
                                success : function(data) {
                                    var d = $.parseJSON(data);
                                    var msg = d.msg;
                                    if (d.success) {
                                        tip(msg);
                                        $('#'+dataGrideId)
                                        riskListSearch();
                                    }else{
                                        tip(msg);
                                    }
                                }
                            });
                        }
                    });
                }else{
                    top.tip("风险已经关联问题，删除失败");
                    return false;
                }
            }
        });
    }else{
        top.tip('请选择需要删除的数据');
        return false;
    }
}

// 导入
function importRiskForPlan() {
    var dialogUrl = ''+riskproblems_Nginx+'/riskController.do?goImport&projectId='+ projectId;
    createDialog('importDialog',dialogUrl);
}

function importDialog(iframe) {
    saveOrUp(iframe);
    window.setTimeout("riskListSearch()", 500);
    return false;
}

function risk_downErrorReport(dataListAndErrorMap) {
    top.Alert.confirm(
        '确定下载错误报告？',
        function(r) {
            if (r) {
                var url=''+riskproblems_Nginx+'/riskImAndExController.do?downErrorReport';
                downloadErrorReport(url,dataListAndErrorMap);
            }
        });
}

//导出
function exportRiskForPlan() {
    // 导出计划Excel
    postFormDataToAction(''+riskproblems_Nginx+'/riskImAndExController.do?doDownloadRiskForPM&isExport=true'+'&projectId='+ projectId);
}

/**
 * Jeecg Excel 导出
 * 代入查询条件
 */
function postFormDataToAction(url) {
    var params = '';
    window.location.href = url + encodeURI(params);
}

// 批量关闭
function closeRiskForPlan() {
    var ids = [];
    var rows = $('#'+dataGrideId).datagrid('getSelections');
    if(rows.length > 0){
        for(var i = 0; i < rows.length; i++){
            if(rows[i].status == '关闭'){
                top.tip("选择的数据中不应该存在关闭状态的数据，请重新选择！");
                return false;
            }
        }

        top.Alert.confirm('确定关闭这些风险？', function(r) {
            if (r) {
                for (var i = 0; i < rows.length; i++) {
                    ids.push(rows[i].id);
                }
                $.ajax({
                    url : ''+riskproblems_Nginx+'/riskController.do?doCloseRisk',
                    type : 'post',
                    data : {
                        id : ids.join(',')
                    },
                    cache : false,
                    success : function(data) {
                        var d = $.parseJSON(data);
                        if (d.success) {
                            var msg = d.msg;
                            tip(msg);
                            riskListSearch();
                        }
                    }
                });
            }
        });
    }else{
        top.tip('请选择一条风险');
    }
}

// 批量重启
function openRiskForPlan() {
    var rows = $('#'+dataGrideId).datagrid('getSelections');
    if(rows.length > 0){
        for(var i = 0; i < rows.length; i++){
            if(rows[i].status == '激活'){
                top.tip('激活状态的风险不能被开启，请重新选择');
                return false;
            }
        }

        var ids = [];
        top.Alert.confirm('确定开启这些风险？', function(r) {
            if (r) {
                for (var i = 0; i < rows.length; i++) {
                    ids.push(rows[i].id);
                }
                $.ajax({
                    url : ''+riskproblems_Nginx+'/riskController.do?doOpenRisk',
                    type : 'post',
                    data : {
                        id : ids.join(',')
                    },
                    cache : false,
                    success : function(data) {
                        var d = $.parseJSON(data);
                        if (d.success) {
                            var msg = d.msg;
                            tip(msg);
                            riskListSearch();
                        }
                    }
                });
            }
        });
    }else{
        top.tip('请选择一条风险');
    }
}

// 批量评估
function acccessRiskForPlan() {
    var rows = $('#'+dataGrideId).datagrid('getSelections');
    var ids = [];
    if(rows.length > 0){
        for(var i = 0; i < rows.length; i++){
            if(rows[i].status == '关闭'){
                top.tip('不能对已关闭的风险进行评估，请重新选择');
                return false;
            }
        }

        for(var i = 0; i < rows.length; i++){
            ids.push(rows[i].id);
        }
    }
    ids.join(',');
    var dialogUrl = ''+riskproblems_Nginx+'/riskController.do?goAccess&ids='+ids+'&projectId='+ projectId;
    createDialog('accessRiskDialog',dialogUrl);
}

function accessRiskDialog(iframe){
    iframe.saveChanges();
}
