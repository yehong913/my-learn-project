//问题
window.TC005 = new Object();
var _this =  window.TC005;
var riskproblems_Nginx =  $("#riskproblems_Nginx").val();
var planId = $("#planId").val();
var projectId = $("#projectId").val();
var dataGrideId;
!function(){
    _this.isShowButton = function(){
        debugger
        if ($("#isOwner").val() !='true') {
            $("#TC005新增Div").hide();
            $("#TC005修改Div").hide();
            $("#TC005删除Div").hide();
            $("#TC005提交Div").hide();
        }
    };
}();

$(function() {
    _this.isShowButton();
    var tab = $('#tt').tabs('getSelected');
    var index = $('#tt').tabs('getTabIndex',tab);
    dataGrideId = $("#dataGrideId_"+index).val();
    refreshProblem();

});

//列表刷新
function refreshProblem() {
    $('#'+dataGrideId).datagrid(
        {
            url : ''+riskproblems_Nginx+'/riskProblemTaskController.do?searchDatagrid&id='+projectId+'&planId='+planId+'',
            pageNumber : 1
        });
}

//查看详情
function formatProblemName(val, row, index) {
    return '<span style="cursor:hand"><a onclick="showRiskProblemDetail(\''
        + row.id + '\')" style="color:blue;">' + val + '</a></span>';
}

//查看详情
function formatcreateName(val, row, index) {
    return row.createFullName+'-'+row.createName;
}

// 时间格式化
function dateFormatterStr(val, row, value) {
    return val.substr(0, 10);
}

function formatProblemStatus(val, row, index) {
    if(row.procInstId != '' && row.procInstId != undefined){
        if(row.bizCurrent == 'EDITING'){
            val = "已登记";
        }else if(row.bizCurrent == 'REPORTED'){
            val = '分析中';
        }else if(row.bizCurrent == 'SOLVED'){
            val = '处理中';
        }else if(row.bizCurrent == 'CLOSED'){
            val = '已关闭';
        }
        return '<span style="cursor:hand"><a onclick="viewProcessDef(\''
            + row.procInstId + '\',\'' + '查看流程'
            + '\')" style="color:blue;">' + val + '</a></span>';
    }else{
        return '已登记';
    }
}

function createAddTaskDialogForPlan() {
    createDialog('addTaskProblemDialogForPlan', ''+riskproblems_Nginx+'/riskProblemTaskController.do?goAddProblem&projectId='+projectId+'&planId='+planId+'&pm=true');
}

function RiskProblemOkFunction(iframe) {
    saveOrUp(iframe);
    return false;
}

//问题查看
function showRiskProblemDetail(id) {
    url = ''+riskproblems_Nginx+'/riskProblemTaskController.do?goCheckProblem&entrance=plan&id='
        + id;
    $("#riskProblemDeatilDialogForPlan").lhgdialog("open", "url:" + url);
}

// 查看风险信息
function viewRisk(id) {
    var teamId = $("#teamId").val();
    createDialog('showRiskDialogForPlan',''+riskproblems_Nginx+'/riskModifyController.do?goCheck&teamId='+teamId+'&isCheck=true&id=' + id);
}

//流程查看
function viewProcessDef(procInstId, title) {
    createdetailwindow(title,
        'generateFlowDiagramController.do?initDrowFlowActivitiDiagram&procInstId='
        + procInstId, 800, 600);
}

//修改问题
function updateProblemLineForPlan() {
    var id = '';
    var bizCurrent = '';
    var rows = $('#'+dataGrideId).datagrid('getSelections');
    if(rows.length > 0){
        for(var i = (rows.length-1); i >=0;i-- ){
            id = rows[i].id;
            bizCurrent = rows[i].bizCurrent;
        }
    }else{
        top.tip('请选择需要修改的数据');
        return false;
    }

    if (bizCurrent != 'EDITING') {
        top.tip('已在流程中的数据无法修改');
        return false;
    } else {
        createDialog('updateTaskProblemDialogForPlan',''+riskproblems_Nginx+'/riskProblemTaskController.do?goAddProblem&projectId='+projectId+'&planId='+planId+'&enterance=plan&type=update&id='+id);
    }
}

//修改问题确定之后
function updateProblemLineSub(iframe)
{
    iframe.formsubmit();
}

function RiskProblemOkFunction(iframe) {
    saveOrUp(iframe);
    return false;
}

//提交审批按钮
function assignProblemLineForPlan()
{
    var id = '';
    var bizCurrent = '';
    var rows = $('#'+dataGrideId).datagrid('getSelections');
    if(rows.length > 0){
        for(var i = (rows.length-1); i >=0;i-- ){
            id = rows[i].id;
            bizCurrent = rows[i].bizCurrent;
        }
    }else{
        top.tip('请选择需要提交的数据');
        return false;
    }

    if (bizCurrent != 'EDITING') {
        top.tip('已在流程中的数据无法提交');
        return false;
    } else {
        $.ajax({
            url : ''+riskproblems_Nginx+'/riskProblemTaskController.do?startProblemProcess',
            type : 'post',
            data : {
                id : id
            },
            cache : false,
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    $('#selectProbLeaderDialogForPlan').lhgdialog(
                        "open",
                        "url:"
                        + 'commonFlowController.do?getDynamicForm&taskNumber='+ id
                        + '&entityName=RiskProblemInfo'
                        + '&businessType=default');
                } else {
                    top.tip(d.msg);
                }
            }
        });
    }
}

//问题删除
function deleteProblemLineForPlan(id, index) {
    debugger
    var id = '';
    var ids = [];
    var rows = $('#'+dataGrideId).datagrid('getSelections');
    if(rows.length > 0){
        for(var i = (rows.length-1); i >=0;i-- ){
            var procInstId = rows[i].procInstId;
            if (procInstId != undefined && procInstId != '' && procInstId != null) {
                top.tip('已在流程中的数据无法删除');
                return false;
            }
        }
    }
    if(rows.length > 0){
        for(var i = (rows.length-1); i >=0;i-- ){
            ids.push(rows[i].id);
        }
    }else{
        top.tip('请选择需要删除的数据');
        return false;
    }

    top.Alert.confirm('确定删除该问题？', function(r) {
        if (r) {
            $.ajax({
                url : ''+riskproblems_Nginx+'/riskProblemTaskController.do?deleteTaskProblems',
                type : 'post',
                data : {
                    ids : ids.join(",")
                },
                cache : false,
                success : function(data) {
                    var d = $.parseJSON(data);
                    if (d.success) {
                        var msg = d.msg;
                        $('#'+dataGrideId).datagrid('clearSelections');
                        refreshProblem();
                        top.tip(msg);
                    }
                }
            });
        }
    });
}

function assignProb(iframe)
{
    var leader = iframe.$('input[name=leader]').val();
    if(leader == null || leader == ''){
        top.tip('请选择处理人');
        return false;
    } else {
        var options = {
            submitCallback : function() {
                var win = $.fn.lhgdialog("getSelectParentWin");
                refreshProblem();
                setTimeout(function() {
                    $.fn.lhgdialog("closeAll")
                }, 100)
            }
        }
        setTimeout(function() {
            iframe.submitForm(options);
        }, 100)
        return false;
    }
}

function beCancel(iframe) {
    refreshProblem();
    setTimeout(function() {
        $.fn.lhgdialog("closeAll")
    }, 100)
}
