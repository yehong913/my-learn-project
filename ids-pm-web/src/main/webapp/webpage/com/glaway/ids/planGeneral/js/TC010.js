//质量检查单js

window.TC010 = new Object();

!function(){
    var _this =  window.TC010;

    //控制结论字段的显示
    _this.btnShow = function (){
        $('#新增Div').hide();
    };


    _this.initDataGrid = function () {
        var planId = $('#planId').val();
        $('#'+dataGrideId).datagrid(
            {
                url : 'qualityTestController.do?searchList&planId='+planId,
                pageNumber : 1
            });

    };

}();

var dataGrideId;
//初始化函数
$(function() {
    var tab = $('#tt').tabs('getSelected');
    var index = $('#tt').tabs('getTabIndex',tab);
    dataGrideId = $("#dataGrideId_"+index).val();
    TC010.btnShow();
    TC010.initDataGrid();
});