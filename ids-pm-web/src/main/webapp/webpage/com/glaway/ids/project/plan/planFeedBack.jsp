<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>计划反馈引擎</title>
    <t:base type="jquery,easyui,tools"></t:base>
</head>
<title></title>
<style>
    .weight {
        display: block;
    }
    .weight .title {
        width: 100%;
        font-weight: normal;
        padding: 3px 0px;
        height: 24px;
        line-height: 24px;
    }

    .weight .foot {
        clear: both;
        width: 100%;
        padding-top: 20px;
    }

    .weight ul {
        width: 30%;
        float: left;
        margin-right: 100px
    }

    .weight ul li {
        list-style: none;
        width: 40%;
        margin: 10px 0
    }
</style>
<script type="text/javascript">
    var num = 0;
    var intervalObj;
    $(function () {
        //初始化页面样式
        setTimeout(function(){
            //获取上一个兄弟节点
            initLoad();
            var deliveryWeight = $('#deliveryWeight_model').prev();
            deliveryWeight.height(0);
            var testWeight = $('#testWeight_model').prev();
            testWeight.height(0);
        },50);
    });

    function start() {
        //校验生命周期权重
        var planPolicyPer = $('#planPolicyPer').text();
        var deliveryPer = $('#deliveryPer').text();
        if (planPolicyPer != 100) {
            top.tip('<spring:message code="com.glaway.ids.pm.plan.checkPolicyWeight"/>');
            return false;
        }
        if (deliveryPer != 100) {
            top.tip('<spring:message code="com.glaway.ids.pm.plan.checkDeliveryWeight"/>');
            return false;
        }
        var policyStatus = $('#PLANPOLICY').combobox('getValue');
        if (policyStatus == "" || policyStatus == undefined) {
            top.tip('<spring:message code="com.glaway.ids.pm.plan.chosePolicyStatus"/>');
            return false;
        }
        //数量校验
        var deliveryNum = $('#deliveryNum').val() == "" ? 0 : $('#deliveryNum').val();
        var nizhiNum = $('#nizhiNum').val() == "" ? 0 : $('#nizhiNum').val();
        var shenpiNum = $('#shenpiNum').val() == "" ? 0 : $('#shenpiNum').val();
        var guidangNum = $('#guidangNum').val() == "" ? 0 : $('#guidangNum').val();
        if (policyStatus == "ORDERED") {//只有执行中进行数量校验
            var ex = /^\d+$/;
            if (ex.test(deliveryNum) && ex.test(nizhiNum) && ex.test(shenpiNum) && ex.test(guidangNum)) {//都是正整数再判断数量相加是否一致
                if (deliveryNum < (Number(nizhiNum) + Number(shenpiNum) + Number(guidangNum))) {
                    top.tip('<spring:message code="com.glaway.ids.pm.plan.checkNumber"/>');
                    return false;
                }
            } else {
                top.tip('<spring:message code="com.glaway.ids.pm.plan.checkNumber"/>');
                return false;
            }
        }
        var infos =  new Array();
        $('#feedBackWeight_config select').each(function () {
            var info = new Object();
            var id = $(this)[0].id;
            var value = $(this).combobox('getValue');
            info['id'] = id;
            info['value'] = value;
            infos.push(info);
        });
        $('#deliveryWeight_model select').each(function () {
            var info = new Object();
            var id = $(this)[0].id;
            var value = $(this).combobox('getValue');
            info['id'] = id;
            info['value'] = value;
            infos.push(info);
        });
        //计算权重获取进度
        $.ajax({
            type : 'get',
            url : 'planFeedBackController.do?calculateWeight',
            data : {
                status : policyStatus,
                deliveryNum : deliveryNum,
                nizhiNum : nizhiNum,
                shenpiNum : shenpiNum,
                guidangNum : guidangNum,
                infos : JSON.stringify(infos)
            },
            cache : false,
            success : function (data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    num = Number(d.obj);
                    intervalObj = window.setInterval(waitProgressBar,10);
                }
            }
        });
    }

    //显示进度条加载过程
    function waitProgressBar() {
        var val = $('#process').progressbar('getValue');
        if (val > 0 && val <=30) { //0-30显示红色
            $('.progressbar-value .progressbar-text').css("background-color","#DF4134");
        }
        if (val > 30 && val <=60) { //30-60显示黄色
            $('.progressbar-value .progressbar-text').css("background-color","#EABA0A");
        }
        if (val > 60 && val <=100) { //60-100显示绿色
            $('.progressbar-value .progressbar-text').css("background-color","#53CA22");
        }
        if (val == num) {
            window.clearInterval(intervalObj);
            return;
        }
        if (val > num) {
            var temp1 = parseInt(val);
            if (temp1 == parseInt(num)) {
                $('#process').progressbar('setValue',num);
                window.clearInterval(intervalObj);
                return;
            } else {
                var temp = parseInt(num + 1);
                if (temp1 == temp) {
                    $('#process').progressbar('setValue',num);
                    window.clearInterval(intervalObj);
                    return;
                }
                $('#process').progressbar('setValue',(val - 1));
            }
        } else if (val < num) {
            var temp = parseInt(num);
            var temp1 = parseInt(val);
            if (temp1 == temp) {
                $('#process').progressbar('setValue',num);
                window.clearInterval(intervalObj);
                return;
            }
            $('#process').progressbar('setValue',(val + 1));
        }
    }

    function addPercent() {
        var total = 0;
        $('#feedBackWeight_config select').each(function () {
            var per = $(this).combobox('getValue');
            total = total + Number(per);
        });
        $('#planPolicyPer').text(total);
    }

    function deliveryAddPercent() {
        var total = 0;
        $('#deliveryWeight_model select').each(function () {
            var per = $(this).combobox('getValue');
            total = total + Number(per);
        });
        $('#deliveryPer').text(total);
    }

    //初始化页面时加载数据
    function initLoad() {
        $.ajax({
            type : 'get',
            cache : false,
            url : 'planFeedBackController.do?queryData',
            success : function (data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    var infos = d.obj;
                    for (var i = 0; i < infos.length; i++) {
                        $('#'+infos[i].lifeCycleStatus+'').combobox('setValue',infos[i].weightPercent);
                    }
                }
            }
        });
    }

    //页面信息保存
    function planFeedBackSave() {
        var planPolicyPer = $('#planPolicyPer').text();
        var deliveryPer = $('#deliveryPer').text();
        if (planPolicyPer != 100) {
            top.tip('<spring:message code="com.glaway.ids.pm.plan.checkPolicyWeight"/>');
            return;
        }
        if (deliveryPer != 100) {
            top.tip('<spring:message code="com.glaway.ids.pm.plan.checkDeliveryWeight"/>');
            return;
        }
        var infos =  new Array();
        $('#feedBackWeight_config select').each(function () {
            var info = new Object();
            var id = $(this)[0].id;
            var value = $(this).combobox('getValue');
            info['id'] = id;
            info['value'] = value;
            infos.push(info);
        });
        $('#deliveryWeight_model select').each(function () {
            var info = new Object();
            var id = $(this)[0].id;
            var value = $(this).combobox('getValue');
            info['id'] = id;
            info['value'] = value;
            infos.push(info);
        });
        $.ajax({
            type : 'post',
            cache : false,
            url : 'planFeedBackController.do?saveFeedBack',
            data : {
                infos : JSON.stringify(infos)
            },
            success : function (data) {
                var d = $.parseJSON(data);
                top.tip(d.msg);
            }
        });
    }
</script>
<body>
<%
    String datas = "0_0%,5_5%,10_10%,15_15%,20_20%,25_25%,30_30%,35_35%,40_40%,45_45%,50_50%,55_55%,60_60%,65_65%,70_70%,75_75%,80_80%,85_85%,90_90%,95_95%,100_100%";
    request.setAttribute("datas", datas);
%>
<div class="easyui-layout" fit="true">
    <div region="center">
        <div style="overflow: hidden">
            <fd:linkbutton onclick="planFeedBackSave()" value="{com.glaway.ids.common.btn.save}"
                           operationCode="planFeedBackSave" iconCls="basis ui-icon-save" />
            <div style="float:right;margin-right: 5px;margin-top: 5px">
                <fd:helpButton help="helpDoc:PlanRateFeedback"></fd:helpButton>
            </div>
            <fd:panel id="feedBackWeight_config" collapsed="false"
                      collapsible="false" closable="false" title="反馈权重占比"
                      style="width:100%;overflow: hidden;">
                <div class="weight">
                    <ul class="weightleft">
                        <li class="title">计划生命周期权重占比</li>
                        <li>
                            <fd:combobox id="EDITING" title="拟制中" textField="name" valueField="id" editable="false"
                                         data="${datas}" panelHeight="100px" onChange="addPercent()"></fd:combobox>
                        </li>
                        <li>
                            <fd:combobox id="TOBERECEIVED" title="待接收" textField="name" valueField="id" editable="false"
                                         data="${datas}" panelHeight="100px" onChange="addPercent()"></fd:combobox>
                        </li>
                        <li>
                            <fd:combobox id="FEEDBACKING" title="完工确认" textField="name" valueField="id" editable="false"
                                         data="${datas}" panelHeight="100px" onChange="addPercent()"></fd:combobox>
                        </li>
                        <li class="foot"><span>各生命周期权重总和相加要等于100%，当前权重总和为<font id="planPolicyPer" color="red">0</font><font color="red">%</font></span></li>
                    </ul>
                    <ul class="weightRight">
                        <li class="title"></li>
                        <li>
                            <fd:combobox id="LAUNCHED" title="已发布" name="combox" textField="name" valueField="id" editable="false"
                                         data="${datas}" panelHeight="100px" onChange="addPercent()"></fd:combobox>
                        </li>
                        <li>
                            <fd:combobox id="ORDERED" title="执行中" textField="name" valueField="id" editable="false"
                                         data="${datas}" panelHeight="100px" onChange="addPercent()">
                            </fd:combobox></li>
                        <li>
                            <fd:combobox id="FINISH" title="已完工" textField="name" valueField="id" editable="false"
                                         data="${datas}" panelHeight="100px" onChange="addPercent()"></fd:combobox>
                        </li>
                    </ul>
                </div>
            </fd:panel>
            <fd:panel id="deliveryWeight_model" collapsed="false"
                      collapsible="false" closable="false" title=" "
                      style="width:100%;overflow: hidden;">
                <div class="weight">
                    <ul class="weightleft">
                        <li class="title">执行中：交付物生命周期权重占比</li>
                        <li>
                            <fd:combobox id="nizhi" title="拟制中" textField="name" valueField="id" editable="false"
                                         data="${datas}" panelHeight="100px" onChange="deliveryAddPercent()"></fd:combobox>
                        </li>
                        <li>
                            <fd:combobox id="guidang" title="已归档" textField="name" valueField="id" editable="false"
                                         data="${datas}" panelHeight="100px" onChange="deliveryAddPercent()"></fd:combobox>
                        </li>
                        <li class="foot"><span>各生命周期权重总和相加要等于100%，当前权重总和为<font id="deliveryPer" color="red">0</font><font color="red">%</font></span></li>
                    </ul>
                    <ul class="weightRight">
                        <li class="title"></li>
                        <li>
                            <fd:combobox id="shenpi" title="审批中" textField="name" valueField="id" editable="false"
                                         data="${datas}" panelHeight="100px" onChange="deliveryAddPercent()"></fd:combobox>
                        </li>
                    </ul>
                </div>
            </fd:panel>
            <fd:panel id="testWeight_model" collapsed="false"
                      collapsible="false" closable="false" title=" "
                      style="width:100%;overflow: hidden;">
                <div class="weight">
                    <ul class="weightleft">
                        <li class="title">测试自动反馈</li>
                        <li><fd:combobox id="PLANPOLICY" url="planFeedBackController.do?initCombobox"
                                     title="计划生命周期"  textField="name" valueField="id" editable="false"></fd:combobox></li>
                        <li><fd:inputNumber id="deliveryNum" min="0" title="交付物数量"></fd:inputNumber></li>
                        <li><fd:inputNumber id="nizhiNum" min="0" title="拟制中"></fd:inputNumber></li>
                        <li><fd:inputNumber id="guidangNum" min="0" title="已归档"></fd:inputNumber></li>
                    </ul>
                    <ul class="weightRight">
                        <li class="title"></li><li class="title"></li><li class="title"></li>
                        <li><fd:inputNumber id="shenpiNum" min="0" title="审批中"></fd:inputNumber></li>
                    </ul>
                </div>
            </fd:panel>
            <fd:panel id="process_model" collapsed="false"
                      collapsible="false" closable="false" title="" style="width:100%;overflow: hidden;">
                <div style="margin-left: 30px;">
                    <fd:linkbutton value="{com.glaway.ids.pm.plan.testProgress}" onclick="start()"  iconCls="basis ui-icon-test-progress"/>
                    <div id="process" class="easyui-progressbar" style="top:5px;width: 55%;height: 15px;;background-color: #C0C0C0;display: inline-block;" data-options="value:0"></div>
                </div>
            </fd:panel>
        </div>
    </div>
</div>
</body>
</html>