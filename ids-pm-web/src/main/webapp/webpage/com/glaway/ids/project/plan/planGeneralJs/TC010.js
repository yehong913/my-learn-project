//质量检查单js

window.TC010 = new Object();
var btnShowFlag;

!function(){
    var _this =  window.TC010;

    //控制结论字段的显示
    _this.btnShow = function (){
        if(btnShowFlag=='true'){
            $("#addBtn").show();
        }else{
            $('#addBtn').hide();
        }
    };

    //新增按钮点击事件
    _this.addAction = function () {
        $.ajax({
            url : 'qualityTestController.do?addQualityDataGrid',
            type : 'GET',
            data : {
                useObjectId : $('#useObjectId').val()
            },
            cache : false,
            success : function (data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    top.tip("新增成功");
                    _this.loadData();
                }
            }
        });
    };

    _this.loadData = function () {
        $.ajax({
            url : 'qualityTestController.do?searchDataGrid',
            type : 'GET',
            data : {
                useObjectId : $('#useObjectId').val()
            },
            cache : false,
            success : function (data) {
                debugger
                if(data == null){
                    data = new Array();
                }

                $("#CofigDataGridTest").datagrid("clearSelections");
                $("#CofigDataGridTest").datagrid("loadData",data);
            }
        });

    };

    _this.checkData = function () {
        return true;
    }

    _this.doSaveData = function(){
        $.ajax({
            url : 'qualityTestController.do?saveQualityDataGrid',
            type : 'GET',
            data : {
                planId : $('#planId').val(),
                useObjectId : $('#useObjectId').val()
            },
            cache : false,
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
    btnShowFlag = $("#btnShowFlag").val();
    TC010.btnShow();
});