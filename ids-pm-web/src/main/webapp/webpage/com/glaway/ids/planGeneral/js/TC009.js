//质量检查单js

window.TC009 = new Object();

!function(){
    var _this =  window.TC009;

    //初始化数据
    _this.initData = function () {
        $.ajax({
            url : 'qualityTestController.do?initQualityFormTest',
            type : 'GET',
            data : {
                planId : $('#planId').val()
            },
            cache : false,
            success : function (data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    debugger
                    var vo = d.obj;
                    $('#CofigFormTest-name').textbox('setValue',vo.name);
                    $('#CofigFormTest-checkPerson').textbox('setValue',vo.checkInfo);
                    $('#CofigFormTest-period').combobox('setValue',vo.period);
                    $('#CofigFormTest-approve').textbox('setValue',vo.approve);
                    $('#CofigFormTest-remark').textbox('setValue',vo.remark);
                }
            }
        });
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


    _this.checkData = function () {
        var approve = $('#CofigFormTest-approve').val();
        if (approve == '') {
            top.tip('请反馈结论');
            return false;
        }
        return true;
    }

    //保存结论
    _this.savaData = function () {
        if(_this.checkData()) {
            var approve = $('#CofigFormTest-approve').val();
            $.ajax({
                url : 'qualityTestController.do?updateFormTest',
                type : 'GET',
                data : {
                    approve : approve,
                    planId : $('#planId').val()
                },
                cache : false,
                success : function (data) {
                    var d = $.parseJSON(data);
                    if (d.success) {
                        top.tip('结论反馈成功');
                        _this.initData();
                    } else {
                        top.tip(d.msg);
                    }
                }
            });
        }
    }


}();

//初始化函数
$(function() {
    TC009.initCombobox();
    TC009.initData();
});