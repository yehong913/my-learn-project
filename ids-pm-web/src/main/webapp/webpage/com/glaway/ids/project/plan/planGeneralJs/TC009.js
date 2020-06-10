//质量检查单js

window.TC009 = new Object();
var showProjectInfo;

!function(){
    var _this =  window.TC009;

    //控制结论字段的显示
    _this.approveShow = function (){
        if(showProjectInfo=='true'){
            $("#approveDiv").show();
        }else{
            $('#approveDiv').hide();
        }
    };

    //控制结论字段的显示
    _this.btnShow = function (){
        if(showProjectInfo=='true'){
            $("#保存Div").show();
        }else{
            $('#保存Div').hide();
        }
    };

    //初始化下拉框
    _this.initCombobox = function () {
        $("#CofigFormTest-period").combobox({
            valueField:'id',
            textField:'text',
            data:[{
                id : 'week',
                text : '每周'
            },{
                id : 'mouth',
                text : '每月'
            },{
                id : 'year',
                text : '每年'
            }]
        } );
    };

    _this.goNextPage = function(){
        return true;
    };

    _this.checkData = function () {
        var checkPerson = $('#CofigFormTest-checkPerson').val();
        if (checkPerson == '') {
            top.tip('检查人不能为空');
            return false;
        }
        return true;
    };

    _this.doSaveData = function(){
        var planId = $('#planId').val();
        var name = $('#CofigFormTest-name').val();
        var period = $('#CofigFormTest-period').combobox('getValue');
        var checkPerson = $('#CofigFormTest-checkPerson').val();
        var remark = $('#CofigFormTest-remark').val();
        $.ajax({
            url : 'qualityTestController.do?saveFormTest',
            type : 'POST',
            data : {
                planId : planId,
                name : name,
                period : period,
                checkPerson : checkPerson,
                remark : remark
            },
            cache : false,
            async : false,
            success : function (data) {
                var d = $.parseJSON(data);
                if (!d.success) {
                    return false;
                }
            }
        });
    }
}();

//初始化函数
$(function() {
    showProjectInfo = $("#showProjectInfo").val();
    TC009.approveShow();
    TC009.btnShow();
    TC009.initCombobox();
});