//计划基本信息Plan

window.TC001 = new Object();

!function(){
    var _this =  window.TC001;
    _this.loadData = function(){
        return true;
    }

    _this.planNameChange = function(curIndexId,nextPageIndex,curIndex){
        var planName = $('#Plan-planName').val();
        if($("#isModifyPage").val() != 'true'){
            if( planName != undefined && planName != null && planName != '' ){
                $.ajax({
                    url : 'deliverablesInfoController.do?pdName&useObjectId='+ $('#useObjectId').val()+ '&pdNext='	+ pdNext + '&useObjectType=' + $('#useObjectType').val(),
                    type : 'post',
                    data : {
                        planName : planName
                    },
                    cache : false,
                    async:false,
                    success : function(data) {
                        var d = $.parseJSON(data);
                        var msg = d.msg;
                        var a = msg.indexOf("在项目名称库中");
                        var b = msg.indexOf("没有勾选名称库");
                        var c = msg.indexOf("可以修改");
                        if(a == 0){
                            $('#pdName').val(planName);

                            /*$("#"+curIndexId).css('display','none');
                            $("#"+nextPageIndex).css('display','');
                            evalLoadDatagrid(curIndex);*/
                        }else if(b == 0){
                            $('#pdName').val("");

                            /*$("#"+curIndexId).css('display','none');
                            $("#"+nextPageIndex).css('display','');
                            evalLoadDatagrid(curIndex);*/
                        }else if(c == 0){
                            $('#pdName').val(planName);

                            /*$("#"+curIndexId).css('display','none');
                            $("#"+nextPageIndex).css('display','');
                            evalLoadDatagrid(curIndex);*/
                        }else{
                            $('#pdName').val("");

                            var win = $.fn.lhgdialog("getSelectParentWin");
                            win.tip('计划名称不在名称库中');
                        }
                        if(d.success){
                           /* initInputs();
                            $('#addInputCancelBtn').focus();*/
                        }
                    }
                });
            }
        }else{
            $('#pdName').val(planName);
           /* $("#"+curIndexId).css('display','none');
            $("#"+nextPageIndex).css('display','');
            evalLoadDatagrid(curIndex);
            initInputs();
            $('#addInputCancelBtn').focus();*/
        }
    }

    _this.ownerChange = function(){
        debugger;
        var departInfo = $("#departList").val();
        var departList = eval(departInfo);
        var owner = $('#Plan-owner').combobox('getValue');
        for(var i=0;i<departList.length;i++){
            if(owner == ''){
                $('#Plan-ownerDept').textbox("setValue","");
            }else if( owner == departList[i].userId){
                $("#Plan-ownerDept").textbox("setValue",departList[i].departname);
            }
        }
    }


    /**国际化js转移过来的方法**/
    var inputTypeTotal = "1";
    _this.workTimeLinkage = function(inputType){
        debugger;
        if (inputType == 'planStartTime') {
            inputTypeTotal = inputTypeTotal + "1";
        }
        if (inputType == 'planEndTime') {
            inputTypeTotal = inputTypeTotal + "2";
        }
        if (inputType == 'workTime') {
            inputTypeTotal = inputTypeTotal + "3";
        }
        var win = $.fn.lhgdialog("getSelectParentWin");
        var planStartTime = $('#Plan-planStartTime').datebox('getValue');
        var workTime = $('#Plan-workTime').val();
        var planEndTime = $('#Plan-planEndTime').datebox('getValue');
        var projectId = $("#projectId").val();
        var milestone = $('#Plan-milestone').combobox('getValue');
        if(milestone == "否"){
            milestone = "false";
        }else if(milestone == "是"){
            milestone = "true";
        }
        if (inputType == 'planEndTime') {
            inputType1 = inputType;
        }
        if (inputType == 'workTime') {
            inputType2 = inputType;
        }
        var inputTypeTotalIndex = inputTypeTotal.indexOf('123');
        if (inputType1 == 'planEndTime' && inputType2 == 'workTime' && inputTypeTotalIndex < 0) {
            inputType1 = undefined;
            inputType2 = undefined
            return false;
        }
        if (inputType == 'planStartTime') {
            if (planStartTime != null && planStartTime != ''
                && planStartTime != undefined) {
                if (workTime != null && workTime != '' && workTime != undefined) {
                    if (workTime == 0 && milestone != "true") {
                        $('#Plan-workTime').textbox("setValue", "1");
                        win.tip('工期不能为0');
                    } else {
                        $.ajax({
                            url : 'planController.do?getEndTimeByStartTimeAndWorkTime',
                            type : 'post',
                            data : {
                                projectId : projectId,
                                planStartTime : planStartTime,
                                planEndTime : planEndTime,
                                workTime : workTime,
                                milestone : milestone
                            },
                            cache : false,
                            success : function(data) {
                                if (data != null) {
                                    var d = $.parseJSON(data);
                                    if (d.success == true) {
                                        planEndTime = d.obj;
                                        var planEndTimeStamp = d.msg.split('<br/>')[0];
                                        $('#Plan-planEndTime').attr('tmpDate', planEndTimeStamp);
                                        $('#Plan-planEndTime')
                                            .datebox("setValue",
                                                planEndTime);
                                        $('#Plan-planEndTime')
                                            .datebox("setText",
                                                planEndTime);
                                    } else {
                                        $('#Plan-planStartTime')
                                            .datebox("setValue",
                                                planEndTime);
                                        $('#Plan-planStartTime')
                                            .datebox("setText",
                                                planEndTime);
                                     //   win.tip(d.msg);
                                    }
                                } else {
                                    win.tip('计划结束时间计算失败');
                                }
                            }
                        });
                    }
                } else if (planEndTime != null && planEndTime != ''
                    && planEndTime != undefined) {
                    if (planEndTime < planStartTime) {
                        $('#Plan-planEndTime').datebox("setValue", "");
                    } else {
                        $.ajax({
                            url : 'planController.do?getWorkTimeByStartTimeAndEndTime',
                            type : 'post',
                            data : {
                                projectId : projectId,
                                planStartTime : planStartTime,
                                planEndTime : planEndTime,
                                workTime : workTime,
                                milestone : milestone
                            },
                            cache : false,
                            success : function(data) {
                                if (data != null) {
                                    var d = $.parseJSON(data);
                                    if (d.success == true) {
                                        workTime = d.obj;
                                        $('#Plan-workTime').textbox(
                                            "setValue", workTime);
                                    } else {
                                        win.tip(d.msg);
                                    }
                                } else {
                                    win.tip('计划工期计算失败');
                                }
                            }
                        });
                    }
                }
            } else {
                win.tip('计划开始时间不能为空');
            }
        } else if (inputType == 'workTime') {
            if (workTime != null && workTime != '' && workTime != undefined) {
                if (planStartTime != null && planStartTime != ''
                    && planStartTime != undefined) {
                    if (workTime == 0 && milestone != "true") {
                        $('#Plan-workTime').textbox("setValue", "1");
                        win.tip('工期不能为0');
                    } else {
                        $.ajax({
                            url : 'planController.do?getEndTimeByStartTimeAndWorkTime',
                            type : 'post',
                            data : {
                                projectId : projectId,
                                planStartTime : planStartTime,
                                planEndTime : planEndTime,
                                workTime : workTime,
                                milestone : milestone
                            },
                            cache : false,
                            success : function(data) {
                                if (data != null) {
                                    var d = $.parseJSON(data);
                                    if (d.success == true) {
                                        planEndTime = d.obj;
                                        var planEndTimeStamp = d.msg.split('<br/>')[0];
                                        $('#Plan-planEndTime').attr('tmpDate', planEndTimeStamp);
                                        $('#Plan-planEndTime')
                                            .datebox("setValue",
                                                planEndTime);
                                        $('#Plan-planEndTime')
                                            .datebox("setText",
                                                planEndTime);


                                        //     win.tip(d.msg);
                                    }
                                } else {
                                    win.tip('计划结束时间计算失败');
                                }
                            }
                        });
                    }
                } else {
                    win.tip('计划开始时间不能为空');
                }
            }
        } else if (inputType == 'planEndTime') {
            if (planEndTime != null && planEndTime != ''
                && planEndTime != undefined) {
                if (planStartTime != null && planStartTime != ''
                    && planStartTime != undefined) {
                    if (planEndTime < planStartTime) {
                        win.tip('计划开始时间不能晚于结束时间');
                    } else {
                        $.ajax({
                            url : 'planController.do?getWorkTimeByStartTimeAndEndTime',
                            type : 'post',
                            data : {
                                projectId : projectId,
                                planStartTime : planStartTime,
                                planEndTime : planEndTime,
                                workTime : workTime,
                                milestone : milestone
                            },
                            cache : false,
                            success : function(data) {
                                if (data != null) {
                                    var d = $.parseJSON(data);
                                    if (d.success == true) {
                                        workTime = d.obj;
                                        if (workTime == 0 && milestone != "true") {
                                            $('#Plan-workTime').textbox(
                                                "setValue", "1");
                                            win.tip('工期不能为0');
                                        } else {
                                            $('#Plan-workTime').textbox(
                                                "setValue",
                                                workTime);
                                        }
                                    } else {
                                        win.tip(d.msg);
                                    }
                                } else {
                                    win.tip('计划工期计算失败');
                                }
                            }
                        });
                    }
                } else {
                    win.tip('计划开始时间不能为空');
                }
            }
        }
    }

    _this.checkData = function () {
        return true;
    }

    var pdNext = 0;
    _this.doSaveData = function(){
        if(_this.checkPlanWithInput()){
            var planName = $('#planNameHid').val();
            var taskNameId='';
            /*if(planName == undefined){
                planName = $('#planName2').combobox('getText');
                taskNameId=$('#planName2').combobox('getValue');
            }*/

            var tabCbTemplateId = $('#tabCbTemplateId').val();

            var id = $('#id').val();
            var planNumber = $('#planNumber').val();
            var projectId = $('#projectId').val();
            var parentPlanId = $('#parentPlanId').val();
            var beforePlanId = $('#beforePlanId').val();
            var bizCurrent = $('#bizCurrent').val();

            var isStandard = $('#isStandard').val();
            var flowStatus = $('#flowStatus').val();
            var remark = $('#Plan-remark').val();
            var owner = $('#Plan-owner').combobox('getValue');
            var userList = eval($('#userList2').val());
            for (var i = 0; i < userList.length; i++) {
                var a = owner.indexOf(userList[i].realName);
                if (a == 0) {
                    owner = userList[i].id;
                }
            }

            var planLevel = $('#Plan-planLevel').combobox('getValue');
            var planLevelList = eval($('#planLevelList').val());
            for (var i = 0; i < planLevelList.length; i++) {
                if (planLevel == planLevelList[i].name) {
                    planLevel = planLevelList[i].id;
                }
            }
            var planStartTime = $('#Plan-planStartTime').datebox('getValue');
            var workTime = $('#Plan-workTime').val();
            var planEndTime = $('#Plan-planEndTime').datebox('getValue');
            var milestone = $('#Plan-milestone').combobox('getValue');
            var milestoneRm;
            var preposeIds = $('#preposeIds').val();
            if(milestone == "否"){
                milestoneRm = "false";
            }else if(milestone == "false"){
                milestoneRm = "false";
            }else if(milestone == "true"){
                milestoneRm = "true";
            }
debugger;
            var taskNameType = $('#taskNameTypeHid').val();
            var taskType = $('#Plan-taskType').combobox('getValue');


            if($("#isModifyPage").val() != 'true'){
                if( planName != undefined && planName != null && planName != '' ){
                    $.ajax({
                        url : 'deliverablesInfoController.do?pdName&useObjectId='+ $('#useObjectId').val()+ '&pdNext='	+ pdNext,
                        type : 'post',
                        data : {
                            planName : planName
                        },
                        cache : false,
                        success : function(data) {
                            var d = $.parseJSON(data);
                            var msg = d.msg;
                            var a = msg.indexOf("在项目名称库中");
                            var b = msg.indexOf("没有勾选名称库");
                            var c = msg.indexOf("可以修改");
                            if(a == 0){
                                $.post('planController.do?doSave', {
                                    'id' : id,
                                    'planNumber' : planNumber,
                                    'projectId' : projectId,
                                    'parentPlanId' : parentPlanId,
                                    'beforePlanId' : beforePlanId,
                                    'planStartTime' : planStartTime,
                                    'planEndTime' : planEndTime,
                                    'flowStatus' : flowStatus,
                                    'bizCurrent' : bizCurrent,
                                    'remark' : remark,
                                    'planName' : planName,
                                    'owner' : owner,
                                    'planLevel' : planLevel,
                                    'workTime' : workTime,
                                    'milestone'	: milestoneRm,
                                    'preposeIds' : preposeIds,
                                    'taskNameId' : taskNameId,
                                    'taskNameType' : taskNameType,
                                    'taskType' : taskType,
                                    'tabCbTemplateId' : tabCbTemplateId
                                }, function(data) {
                                    var d = $.parseJSON(data);
                                    if (d.success) {
                                        $('#planId').val(d.obj);
                                        saveDefData();
                                    }
                                    top.tip("新增成功");
                                    var win = $.fn.lhgdialog("getSelectParentWin");
                                    win.planListSearch();
                                    $.fn.lhgdialog("closeSelect");
                                });
                            }else if(b == 0){
                                $.post('planController.do?doSave', {
                                    'id' : id,
                                    'planNumber' : planNumber,
                                    'projectId' : projectId,
                                    'parentPlanId' : parentPlanId,
                                    'beforePlanId' : beforePlanId,
                                    'planStartTime' : planStartTime,
                                    'planEndTime' : planEndTime,
                                    'bizCurrent' : bizCurrent,
                                    'flowStatus' : flowStatus,
                                    'remark' : remark,
                                    'planName' : planName,
                                    'owner' : owner,
                                    'planLevel' : planLevel,
                                    'workTime' : workTime,
                                    'milestone'	: milestoneRm,
                                    'preposeIds' : preposeIds,
                                    'taskNameType' : taskNameType,
                                    'taskType' : taskType,
                                    'tabCbTemplateId' : tabCbTemplateId
                                }, function(data) {
                                    var d = $.parseJSON(data);
                                    if (d.success) {
                                        $('#planId').val(d.obj);
                                        saveDefData();
                                    }
                                    top.tip("新增成功");
                                    var win = $.fn.lhgdialog("getSelectParentWin");
                                    win.planListSearch();
                                    $.fn.lhgdialog("closeSelect");
                                });
                            }else if(c == 0){
                                $.post('planController.do?doSave', {
                                    'id' : id,
                                    'planNumber' : planNumber,
                                    'projectId' : projectId,
                                    'parentPlanId' : parentPlanId,
                                    'beforePlanId' : beforePlanId,
                                    'planStartTime' : planStartTime,
                                    'planEndTime' : planEndTime,
                                    'bizCurrent' : bizCurrent,
                                    'flowStatus' : flowStatus,
                                    'remark' : remark,
                                    'planName' : planName,
                                    'owner' : owner,
                                    'planLevel' : planLevel,
                                    'workTime' : workTime,
                                    'milestone'	: milestoneRm,
                                    'preposeIds' : preposeIds,
                                    'taskNameType' : taskNameType,
                                    'taskType' : taskType,
                                    'tabCbTemplateId' : tabCbTemplateId
                                }, function(data) {
                                    var d = $.parseJSON(data);
                                    if (d.success) {
                                        $('#planId').val(d.obj);
                                        saveDefData();
                                    }
                                    top.tip("新增成功");
                                    var win = $.fn.lhgdialog("getSelectParentWin");
                                    win.planListSearch();
                                    $.fn.lhgdialog("closeSelect");
                                });
                            }else{
                                var win = $.fn.lhgdialog("getSelectParentWin");
                                //win.tip("计划名称不在名称库中");
                                top.tip('<spring:message code="com.glaway.ids.pm.project.plan.nameNotStandard"/>');
                            }
                            if(d.success){
                                initDocument();
                                $('#addOutputCancelBtn').focus();
                            }
                        }
                    });
                }
            }else{
                var id = $('#id').val();
                var planStartTime = $('#Plan-planStartTime').datebox('getValue');
                var planEndTime = $('#Plan-planEndTime').datebox('getValue');
                $.ajax({
                    url : 'resourceLinkInfoController.do?pdResource',
                    type : 'post',
                    data : {
                        id : id,
                        planStartTime : planStartTime,
                        planEndTime : planEndTime
                    },
                    cache : false,
                    success : function(data) {
                        var d = $.parseJSON(data);
                        var msg = d.msg;
                        if(d.success){
                            $.post('planController.do?doSave', {
                                'id' : id,
                                'planNumber' : planNumber,
                                'projectId' : projectId,
                                'parentPlanId' : parentPlanId,
                                'beforePlanId' : beforePlanId,
                                'planStartTime' : planStartTime,
                                'planEndTime' : planEndTime,
                                'bizCurrent' : bizCurrent,
                                'flowStatus' : flowStatus,
                                'remark' : remark,
                                'planName' : planName,
                                'owner' : owner,
                                'planLevel' : planLevel,
                                'workTime' : workTime,
                                'milestone'	: milestoneRm,
                                'preposeIds' : preposeIds,
                                'taskNameType' : taskNameType,
                                'taskType' : taskType
                            }, function(data) {
                                var d = $.parseJSON(data);
                                if (d.success) {
                                    var planId = $('#id').val();
                                    $('#planId').val(planId);
                                    saveDefData();
                                }
                                top.tip("修改成功");
                                var win = $.fn.lhgdialog("getSelectParentWin");
                                var planListSearch = win.planListSearch();
                                $.fn.lhgdialog("closeSelect");
                            });
                        }else{
                            top.tip(msg);
                        }
                    }
                });
            }
        }
    }

    _this.checkPlanWithInput = function(){
        var parentStartTime = $('#parentStartTime').val();
        var parentEndTime = $('#parentEndTime').val();
        var preposeEndTime = $('#preposeEndTime').val();
        var win = $.fn.lhgdialog("getSelectParentWin") ;

        var planName = $('#Plan-planName').val();

        if (planName == "") {
            top.tip('名称不能为空');
            return false;
        }
        if(planName.length>30){
            top.tip('名称不能超过30个字符')
            return false;
        }

        var userList = eval($('#userList2').val());
        var owner = $('#Plan-owner').combobox('getValue');
        var ownerText = $('#Plan-owner').combobox('getText');
        var pdOwner = 0;
        for (var i = 0; i < userList.length; i++) {
            var a = ownerText.indexOf(userList[i].realName);
            if (a == 0) {
                pdOwner = 1;
                break;
            }
        }

        if (owner == "") {
            top.tip('负责人不能为空');
            return false;
        }

        if(pdOwner == 0){
            top.tip('负责人不存在,请重新输入');
            return false;
        }

        var planStartTime = $('#Plan-planStartTime').datebox('getValue');
        if (planStartTime == "") {
            top.tip('计划开始时间不能为空');
            return false;
        }
        if((planStartTime < parentStartTime) || (planStartTime > parentEndTime)){
            top.tip('计划时间必须收敛于父计划【' +parentStartTime+'~'+parentEndTime+ '】的时间');
            return false;
        }

        var planEndTime = $('#Plan-planEndTime').datebox('getValue');
        if (planEndTime == "") {
            top.tip('计划结束时间不能为空');
            return false;
        }

        if(planEndTime < planStartTime){
            top.tip('计划结束时间不能早于计划开始时间');
            return false;
        }

        if((planEndTime < parentStartTime) || (planEndTime > parentEndTime)){
            //"计划开始时间必须收敛于父级的计划时间或项目时间:"+parentStartTime+'~'+parentEndTime
            top.tip('计划时间必须收敛于父计划【' +parentStartTime+'~'+parentEndTime+ '】的时间');
            return false;
        }

        if(preposeEndTime != null && preposeEndTime != '' && preposeEndTime != undefined){
            if(preposeEndTime >= planStartTime){
                top.tip('计划开始时间不能早于其前置计划的结束时间 :' +preposeEndTime+ '');
                return false;
            }
        }

        var workTime = $('#Plan-workTime').val();
        var reg = new RegExp("^[1-9][0-9]{0,3}$");
        if (workTime == "") {
            top.tip('工期不能为空');
            return false;
        }

        var milestone = $('#Plan-milestone').combobox('getValue');
        if(milestone == 'false') {
            if(!reg.test(workTime)){
                top.tip('工期必须为1~9999的整数');
                return false;
            }
        } else {
            if(workTime != '0') {
                if(!reg.test(workTime)){
                    top.tip('工期必须为1~9999的整数');
                    return false;
                }
            }
        }

        var remark = $('#Plan-remark').val();
        if(remark.length>200){
            top.tip('备注不能超过200个字符')
            return false;
        }
        return true;
    }

    _this.milestoneChange = function(){
        var workTime = $('#Plan-workTime').val();
        var milestone = $('#Plan-milestone').combobox('getValue');
        if(milestone == "否"){
            milestone = "false";
        }else if(milestone == "是"){
            milestone = "true";
        }
        if (workTime == 0 && milestone != "true") {
            $('#Plan-workTime').textbox(
                "setValue", "1");
        }
    }

    _this.goNextPage = function(curIndexId,nextPageIndex,curIndex){
        debugger;
        var flag = false;
        if($("#isModifyPage").val() == 'true'){
            var id = $('#id').val();
            var planStartTime = $('#Plan-planStartTime').datebox('getValue');
            var planEndTime = $('#Plan-planEndTime').datebox('getValue');
            $.ajax({
                url : 'resourceLinkInfoController.do?checkChildrenTime',
                type : 'post',
                data : {
                    id : id,
                    planStartTime : planStartTime,
                    planEndTime : planEndTime
                },
                cache : false,
                async:false,
                success : function(data) {
                    var d = $.parseJSON(data);
                    var msg = d.msg;
                    if(d.success){
                        if (_this.checkPlanWithInput()) {
                            _this.planNameChange(curIndexId,nextPageIndex,curIndex);
                            flag = true;
                        }
                    }else{
                        top.tip(msg);
                    }
                }
            });
        }else{
            if (_this.checkPlanWithInput()) {
                var flag = _this.planNameChange(curIndexId,nextPageIndex,curIndex);
                flag = true
            }
        }
        return flag;
    }

    _this.projectName = function (){
        debugger
        if(showProjectInfo=='true'){
            $("#projectNameDiv").show();
        }else{
            $('#projectNameDiv').hide();
        }
    }

    _this.projectManagerNames = function (){
        if(showProjectInfo=='true'){
            $("#projectManagerNamesDiv").show();
        }else{
            $("#projectManagerNamesDiv").hide();
        }
    }

    _this.startProjectTime = function (){
        if(showProjectInfo=='true'){
            $("#startProjectTimeDiv").show();
        }else{
            $("#startProjectTimeDiv").hide();
        }
    }

    _this.endProjectTime = function (){
        if(showProjectInfo=='true'){
            $("#endProjectTimeDiv").show();
        }else{
            $("#endProjectTimeDiv").hide();
        }
    }

    _this.eps = function (){
        if(showProjectInfo=='true'){
            $("#epsDiv").show();
        }else{
            $("#epsDiv").hide();
        }
    }

    _this.process = function (){
        if(showProjectInfo=='true'){
            $("#processDiv").show();
        }else{
            $("#processDiv").hide();
        }
    }

    _this.assigner = function (){
        var assigner;

        try{
            assigner = $("#assigner").val();
        }catch (e) {

        }
        if(showProjectInfo=='true' || (assigner != "" && assigner != null && assigner != undefined && assigner != 'undefined') ){
            $("#assignerDiv").show();
        }else{
            $("#assignerDiv").hide();
        }
    }

    _this.assignTime = function (){
        var assignTime;
        try{
            assignTime = $("#assignTime").val();
        }catch (e) {

        }
        if(showProjectInfo=='true'   || (assignTime != "" && assignTime != null && assignTime != undefined && assignTime != 'undefined')){
            $("#assignTimeDiv").show();
        }else{
            $("#assignTimeDiv").hide();
        }
    }

    _this.progressRate = function (){
        if(showProjectInfo=='true'){
            $("#progressRateDiv").show();
        }else{
            $("#progressRateDiv").hide();
        }
    }

/*    _this.preposeIds = function (){
        if(showProjectInfo=='true'){
            $("#preposeIdsDiv").show();
        }else{
            $("#preposeIdsDiv").hide();
        }
    }*/

    _this.parentPlanId = function (){
        if(showProjectInfo=='true'){
            $("#parentPlanIdDiv").show();
        }else{
            $("#parentPlanIdDiv").hide();
        }
    }

    /**
     * 选择前置计划页面
     */
    _this.selectPreposePlan = function() {
        var url = 'planController.do?goPlanPreposeTree';
        if ($('#projectId').val() != "") {
            url = url + '&projectId=' + $('#projectId').val();
        }
        if ($('#parentPlanId').val() != "") {
            url = url + '&parentPlanId=' + $('#parentPlanId').val();
        }
        if ($('#id').val() != "") {
            url = url + '&id=' + $('#id').val();
        }
        if($('#preposeIds').val() != "") {
            url = url + '&preposeIds=' + $('#preposeIds').val();
        }
        //createPopupwindow('项目计划', url, '', '', hiddenId, textId);
        createDialog('preposePlanDialog',url);
    }


}();


var showProjectInfo;
$(function() {
    showProjectInfo = $("#showProjectInfo").val();
    TC001.projectName();
    TC001.projectManagerNames();
    TC001.startProjectTime();
    TC001.endProjectTime();
    TC001.eps();
    TC001.process();
    TC001.assigner();
    TC001.assignTime();
    TC001.progressRate();
    /*TC001.preposeIds();*/
    TC001.parentPlanId();
});




