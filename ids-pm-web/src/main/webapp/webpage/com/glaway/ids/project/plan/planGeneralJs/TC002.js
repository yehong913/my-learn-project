//输入Inputs

window.TC002 = new Object();

!function(){

    var _this =  window.TC002;
    var fromType;
    var fromType2;
    var fromType3;
    var isPlanChange;
    //var webRoot;
    try{
        fromType2 = $("#taskNameType").val();
    }catch (e) {

    }

    try{
        fromType3 = $('#Plan-taskNameType').combobox('getValue');
    }catch (e) {

    }

    try{
        isPlanChange = $("#isPlanChange").val();
    }catch (e) {

    }
    try{
        fromType = $("#taskNameType").combobox('getValue');
    }catch (e) {

    }
/*    try{
        webRoot = $("#webRoot").val();
    }catch (e) {

    }*/

    if (fromType==undefined){
        fromType = fromType2;
    }
   
    _this.loadData = function(this_){
        _this.isEditor();
        _this.isShowColumn();
        var datas="";
        var flg=false;
        if(this_ != null && this_ != '' && this_ != undefined){
            datas=this_;
        }else{
            datas={useObjectId : $('#useObjectId').val(),useObjectType : $('#useObjectType').val(),projectId:$('#projectId').val(),planId:$("#planId").val()}
        }
        var url = 'inputsController.do?list';
        if(isPlanChange != "" && isPlanChange != undefined && isPlanChange != "undefined"){
            if(isPlanChange == 'planChange'){
                url = 'planChangeController.do?inputList';
            }else if(isPlanChange == "planChangeView"){
                url = 'planChangeController.do?inputListView';
            }
        }
        $.ajax({
            type : 'POST',
            url : url,
            async : false,
            data : datas,
            success : function(data) {
                try {
                    if(data == null){
                        data = new Array();
                    }

                    $("#inputsList").datagrid("clearSelections");
                    $("#inputsList").datagrid("loadData",data);
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


    _this.addInputsNew = function(){
        var source;
        if(isPlanChange == 'planChange'){
            source = 'planChange';
        }else{
            source = 'plan';
        }
        var dialogUrl = 'inputsController.do?goAddInputs&projectId='+$("#projectId").val()+'&useObjectId='
            + $('#useObjectId').val()
            + '&useObjectType='
            + $('#useObjectType').val()
            +'&hideMoreShow=false'+'&source='+source;
        createDialog('openSelectInputsDialog', dialogUrl);
    }

    _this.addLink = function(val, row, value){
        if(val!=null&&val!=''&&row.originType == 'LOCAL'){
            return '<a  href="#" onclick="TC002.importDoc(\'' + row.docId + '\',\'' + row.docNameShow + '\')" id="myDoc"  style="color:blue">'+val+'</a>';
        }else if(row.originType == 'PROJECTLIBDOC'){
            var path = "<a  href='#' title='查看' style='color:blue' onclick='TC002.openProjectDoc1(\""+ row.docIdShow+ "\",\""+ row.docNameShow+ "\",\""+ row.ext1+"\",\""+ row.ext2+"\")'>"
                + row.docNameShow
                + "</a>"
            if (row.ext3 == false || row.ext3 == 'false') {
                path = row.docNameShow;
            }
            return path;
        }else if(row.originType == 'PLAN'){
            var path = "<a  href='#' title='查看' style='color:blue' onclick='TC002.openProjectDoc1(\""+ row.docId+ "\",\""+ row.docNameShow+ "\",\""+ row.ext1+"\",\""+ row.ext2+"\")'>"
                + row.docNameShow
                + "</a>"
            if (row.ext3 == false || row.ext3 == 'false') {
                path = row.docNameShow;
            }
            return path;
        }
        else {
            return "";
        }
    }

    _this.importDoc = function (filePath,fileName){
        window.location.href = encodeURI('jackrabbitFileController.do?fileDown&fileName='+fileName+'&filePath=' + filePath);
    }


    _this.openProjectDoc1 = function (id, name,download,history) {
        if (download == false || download == 'false') {
            download = "false";
        }
        if (history == false || history == 'false') {
            history = "false";
        }
        var url = "/ids-pm-web/projLibController.do?viewProjectDocDetail&id=" + id
            + "&download=" + download + "&history=" + history;
        createdetailwindow("文档详情", url, "870", "580")
    }


    _this.showOrigin = function(val, row, value){
        var curValue= val;
        if(curValue!=null&&curValue!='' && curValue.length>5){
            curValue = "<span title='"+val+"'>"+curValue+"</span>";
        }else if(curValue == undefined || curValue == 'undefined'){
            curValue = '';
        }

        if(row.originType == "LOCAL"){
            return "本地文档";
        }else if (row.originType == "PLM"){
            return "PLM系统";
        } else{
            return curValue;
        }
    }

    _this.showFileType = function(val, row, value){
        if(row.originType != "PLM"){
            return "文档";
        }else{
            return val;
        }
    }

    _this.deleteSelectionsInputs = function(griname,url){
        if(isPlanChange != "" && isPlanChange != undefined && isPlanChange != "undefined"){
            url = 'planChangeController.do?doDelInput';
        }
        var rows = $('#'+griname).datagrid('getChecked');
        var ids = [];
        if(rows.length > 0){
            top.Alert.confirm('确定删除该记录？',
                function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url : url,
                            type : 'post',
                            data : {
                                ids : ids.join(','),
                                useObjectId : $("#useObjectId").val()
                            },
                            cache : false,
                            success : function(data) {
                                var d = $.parseJSON(data);
                                if (d.success) {
                                /*    var msg = d.msg;
                                    tip(msg);*/
                                }
                                if(gridname == 'inputsList'){
                                    if(isPlanChange == 'planChangeView'){
                                        _this.initInput2('');
                                    }else{
                                        _this.loadData('');
                                    }

                                    // 									 $('#'+griname).datagrid('clearSelections');
                                    // 									 $('#'+griname).datagrid('clearChecked');
                                }

                            }
                        });
                    }
                });

        }else{
            top.tip("请选择需要删除的记录");
        }
    }

    _this.initInput2 = function(this_) {
        var datas="";
        var flg=false;
        if(this_ != null && this_ != '' && this_ != undefined){
            datas=this_;
        }else{
            datas={useObjectId : $('#useObjectId').val(),useObjectType : $('#useObjectType').val()}
        }
        $.ajax({
            type : 'POST',
            url : 'planChangeController.do?inputList',
            async : false,
            data : datas,
            success : function(data) {
                try {
                    $("#inputsList").datagrid('unselectAll');
                    $("#inputsList").datagrid("loadData",data);
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

    _this.addLocalFile = function(){
        var projectId = $("#projectId").val();
        var dialogUrl = 'planController.do?goAddLocalFile&projectId='+projectId;
        createDialog('addLocalFileDialog', dialogUrl);
    }

    _this.addPLM = function(){
        var useObjectId = $('#useObjectId').val();
        var useObjectType = $('#useObjectType').val();
        $.ajax({
            type:'POST',
            url:'planController.do?checkPlm',
            cache:false,
            success:function(data){
                var d = $.parseJSON(data);
                if(d.success){
                    var dialogUrl = '/ids-pm-web/planController.do?goAddPlm&useObjectId='+useObjectId+'&useObjectType='+useObjectType;
                    createDialog('plmDialog', dialogUrl);
                }else{
                    top.tip("PLM系统异常，请联系管理员");
                }
            }
        });
    }


    _this.goProjLibLink = function(id,index) {
        var rows = $('#inputsList').datagrid('getSelections');
        if(rows.length == 0){
            top.tip("请至少选择一条数据");
            return false;
        }
        if(rows.length > 1){
            top.tip("当前操作只能选择一条数据");
            return false;
        }
        var row;
        index = $('#inputsList').datagrid('getRowIndex',$('#inputsList').datagrid('getSelected'));
        //   $('#inputsList').datagrid('selectRow',index);
        var all = $('#inputsList').datagrid('getRows');
        for(var i=0;i<all.length;i++){
            var inx = $("#inputsList").datagrid('getRowIndex', all[i]);
            if(inx == index){
                row = all[i];
            }
        }

        idRow=row.id;
        var dialogUrl = 'projLibController.do?goProjLibLayout0&id='+$("#projectId").val()+'&rowId='+idRow;
        $("#"+'taskDocCheckLineDialog').lhgdialog("open", "url:"+dialogUrl);
    }

    _this.goPlanLink = function(id,index){
        var rows = $('#inputsList').datagrid('getSelections');
        if(rows.length == 0){
            top.tip("请至少选择一条数据");
            return false;
        }
        if(rows.length > 1){
            top.tip("当前操作只能选择一条数据");
            return false;
        }

        var row;
        //    $('#inputsList').datagrid('selectRow',index);
        index = $('#inputsList').datagrid('getRowIndex',$('#inputsList').datagrid('getSelected'));
        var all = $('#inputsList').datagrid('getRows');
        for(var i=0;i<all.length;i++){
            var inx = $("#inputsList").datagrid('getRowIndex', all[i]);
            if(inx == index){
                row = all[i];
            }
        }

        $.ajax({
            type:'POST',
            data:{inputsName : row.name},
            url:'planController.do?setInputsNameToSession',
            cache:false,
            success:function(data){
                var d = $.parseJSON(data);
                if(d.success){
                    var url = 'planController.do?goSelectPlanInputs&projectId='+$("#projectId").val()+'&useObjectId='+$("#useObjectId").val()+'&useObjectType='+$("#useObjectType").val()+'&tempId='+row.id;
                    //url = encodeURI(encodeURI(url))

                    createDialog('planInputsDialog',url);
                }
            }
        });
    }


    _this.showAllName = function (val, row, value){
        var curValue= val;
        if(curValue!=null&&curValue!='' && curValue.length>5){
            curValue = "<span title='"+val+"'>"+curValue+"</span>";
        }
        return curValue;
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

    _this.isEditor = function (){
        debugger
        var enterType = "0";
        var onlyReadonly;
        //4028f00d6db34426016db365b27c0000为PLM计划类型
        if(fromType=='4028f00d6db34426016db365b27c0000'||fromType3=='PLM计划类'){
            $("#关联PLM系统Div").show();
        }else{
            $("#关联PLM系统Div").hide();
        }

        if(enterType=='2' || onlyReadonly=='1'){
            $("#新增输入项Div").hide();
            $("#新增本地文档Div").hide();
            $("#删除Div").hide();
            $("#项目库关联Div").hide();
            $("#计划关联Div").hide();
        }else{
            $("#新增内部输入Div").hide();
            $("#新增外部输入Div").hide();
            $("#新增输入项Div").show();
            $("#新增本地文档Div").show();
            $("#删除Div").show();
            $("#项目库关联Div").show();
            $("#计划关联Div").show();

        }
    }

    _this.isShowColumn = function(){
        //4028f00d6db34426016db365b27c0000为PLM计划类型
        debugger;
        if(fromType=='4028f00d6db34426016db365b27c0000'){
            debugger;
            setTimeout(function(){
                $('#inputsList').datagrid('hideColumn', 'docNameShow');
                $('#inputsList').datagrid('hideColumn', 'versionCode');
            }, 10);
        }else{
            setTimeout(function(){
                $('#inputsList').datagrid('hideColumn', 'fileType');
                $('#inputsList').datagrid('hideColumn', 'versionCode');
            }, 10);
        }

    }

}();






