<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,lhgdialog"></t:base>
<script type="text/javascript" src="${pageContext.request.contextPath }/plug-in/js/highcharts7.0.2/highcharts.js"></script>
<head>
    <style type="text/css">
        *{margin:0; padding:0; list-style:none;}
        body{padding:0; margin:0; font-size:12px;background: #fff;}
        .font-img{ margin:12px;}
        .font-img .mainTop{position:relative; display:block; height:110px}
        .font-img .mainTop .img{position:absolute;}
        .font-img .mainTop .title{ margin-left:210px;}
        .font-img .mainTop .title p{font-weight:bold;}
        .font-img .mainTop .title span{ display:block; line-height:18px; color:#999}
        .font-img .mainContent{ clear:both}
        .font-img .mainContent .new{ position:relative; border-bottom:1px dashed #CCC; height:24px; line-height:24px;}
        .font-img .mainContent .new .title{ display:block; margin-right:100px}
        .font-img .mainContent .new .date{ display:inline-block; float:right; margin-top:-22px; color:#999;}

        .ranking{ clear:both; margin:12px}
        .ranking .new{ position:relative; border-bottom:1px dashed #CCC; height:24px; line-height:24px;}
        .ranking .new .title{ display:block; margin-right:100px}
        .ranking .new .date{ display:inline-block; float:right; margin-top:-22px; color:#999;}

        #wrapper{

            margin:10px auto;
            height:400px;
            position:relative;
            color:#333;
            text-shadow:rgba(0,0,0,0.1) 2px 2px 0px;
        }

        #slider-wrap{
            width:100%;
            height:380px;
            position:relative;
            overflow:hidden;
        }

        #slider-wrap ul#slider{
            height:100%;

            position:absolute;
            top:0;
            left:0;
        }

        #slider-wrap ul#slider li{
            float:left;
            position:relative;
            /* width:800px; */
            height:400px;
        }

        #slider-wrap ul#slider li  div{
            position:absolute;
            top:20px;
            /* 	left:35px; */
            color:#333;

        }

        #slider-wrap ul#slider li  div h3{
            font-size:24px;
            text-transform:uppercase;


            height:36px;
        }

        #slider-wrap ul#slider li  div span a{

            font-size:16px;
        }

        #slider-wrap ul#slider li i{
            text-align:center;
            line-height:400px;
            display:block;
            width:100%;
            font-size:90px;
        }
        /*btns*/
        .btns{
            position:absolute;
            width:50px;
            height:60px;
            top:40%;
            margin-top:-25px;
            line-height:57px;
            text-align:center;
            cursor:pointer;

            z-index:100;
            -webkit-user-select: none;
            -moz-user-select: none;
            -khtml-user-select: none;
            -ms-user-select: none;
            background-color:#000;
            opacity: 0.3;
            filter: alpha(opacity=30);
            -webkit-transition: all 0.1s ease;
            -moz-transition: all 0.1s ease;
            -o-transition: all 0.1s ease;
            -ms-transition: all 0.1s ease;
            transition: all 0.1s ease;
        }
        .btns:hover{
            background:rgba(0,0,0,1);
        }
        .btns i{ display:inline-block; height:36px; width:36px; margin-top:12px}
        #next{right:-50px; border-radius:7px 0px 0px 7px;}
        #previous{left:-50px; border-radius:0px 7px 7px 7px;}
        #counter{
            top: 30px;
            right:35px;
            width:auto;
            position:absolute;
            color:#333;
        }
        #slider-wrap.active #next{right:0px;}
        #slider-wrap.active #previous{left:0px;}
        /*bar*/
        #pagination-wrap{

            bottom: 10px;
            right:10px;
            height:100px;
            position:absolute;
            text-align:center;
        }
        #pagination-wrap ul {
            width:100%;
        }
        #pagination-wrap ul li{
            margin: 8px 2px;
            display: inline-block;
            width:14px;
            height:2px;

            background:#0C60AA;
            opacity: 0.3;
            filter: alpha(opacity=30);
            position:relative;
            top:0;
            float:left;
            overflow:hidden;
        }
        #pagination-wrap ul li.active{
            width:14px;
            height:2px;
            top:0px;
            opacity: 1;
            filter: alpha(opacity=100);
            box-shadow:rgba(0,0,0,0.1) 1px 1px 0px;
        }
        .arrow-right{ background:url(images/right.png) no-repeat;}
        .arrow-left{ background:url(images/left.png) no-repeat;}
        /*Header*/
        h1{text-shadow:none; text-align:left;}
        h1{	color: #666; text-transform:uppercase;	font-size:16px;}
        /*ANIMATION*/
        #slider-wrap ul, #pagination-wrap ul li{
            -webkit-transition: all 0.3s cubic-bezier(1,.01,.32,1);
            -moz-transition: all 0.3s cubic-bezier(1,.01,.32,1);
            -o-transition: all 0.3s cubic-bezier(1,.01,.32,1);
            -ms-transition: all 0.3s cubic-bezier(1,.01,.32,1);
            transition: all 0.3s cubic-bezier(1,.01,.32,1);
        }
    </style>
</head>
<body onresize="listenWidth()">
<div style="float: left;position: relative;z-index: 1;margin-left: -17px;margin-top: -8px; ">
    <fd:combobox id="project" title="项目选择" panelMaxHeight="200" textField="id" valueField="text"
                 url="projStatisticsController.do?getSelectData&type=1" onChange="changeProjectValue" selectedValue="${selectedValue}"></fd:combobox>
</div>
<div>
    <div id="wrapper">
        <div id="slider-wrap">
            <ul id="slider">
                <li style="width:100%" id="li_containerPlanLifeCycle">
                    <iframe id="ifm1" name="ifm1" style="width:100%;height:330px;margin-top: -6px;" frameborder=0 src="planController.do?goPlanLifeCycle&projId=${selectedValue}"></iframe>
                </li>
                <li style="width:100%" id="li_containerPlanExecutCondition">
                    <iframe id="ifm2" name="ifm2" style="width:100%;height:330px;margin-top: -6px;" frameborder=0 src="planController.do?goPlanExecutCondition&projId=${selectedValue}"></iframe>
                </li>
                <li style="width:100%" id="li_containerPlanCompletion">
                    <iframe id="ifm3" name="ifm3" style="width:100%;height:330px;margin-top: -6px;" frameborder=0 src="planController.do?goPlanCompletion&projId=${selectedValue}"></iframe>
                </li>
            </ul>
            <div class="btns" id="next"><i class="arrow-right"></i></div>
            <div class="btns" id="previous"><i class="arrow-left"></i></div>
            <div id="pagination-wrap">
                <ul>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    $(function(){
        $("#slider-wrap ul#slider li").width($("#slider-wrap").width());
    });

    //改变项目值
    function changeProjectValue() {
        var projectId = $('#project').combobox('getValues');
        var src1= 'planController.do?goPlanLifeCycle&projId='+projectId;
        var src2= 'planController.do?goPlanExecutCondition&projId='+projectId;
        var src3= 'planController.do?goPlanCompletion&projId='+projectId;
        $('#ifm1').attr('src', src1);
        $('#ifm2').attr('src', src2);
        $('#ifm3').attr('src', src3);
    }
</script>
<script type="text/javascript" src="js/slide.js?v=1"></script>
<script>
    function listenWidth(){
        $('#slider-wrap ul#slider').width($('#slider-wrap').width()*$('#slider-wrap ul li').length);
        $("#slider-wrap ul#slider li").width($("#slider-wrap").width());

        var appName = navigator.appName;
        if(appName == 'Microsoft Internet Explorer') {
            var appVersion = navigator.appVersion.split(';')[1].replace(/[ ]/g,'');
            if(appVersion=='MSIE6.0' || appVersion=='MSIE7.0' || appVersion=='MSIE8.0') {
                $("#pagination-wrap").css("margin-left","-"+($("#pagination-wrap").width()/3-150));
                $('#ifm1').attr('src', $('#ifm1').attr('src'));
                $('#ifm2').attr('src', $('#ifm2').attr('src'));
                $('#ifm3').attr('src', $('#ifm3').attr('src'));
            }
        }else{
            $("#pagination-wrap").css("margin-left","-"+$("#pagination-wrap").width()/3);
        }
        $('#ifm1').width($("#slider-wrap").width());
        $('#ifm2').width($("#slider-wrap").width());
        $('#ifm3').width($("#slider-wrap").width());

        if(navigator.userAgent.indexOf("Firefox")>0){
            setTimeout(function() {
                var dialogList = $.fn.lhgdialog('getAll');
                if($.toJSON($(dialogList)[0])=="{}") {}
                else {
                    return;
                }
                $('#ifm1').attr('src', $('#ifm1').attr('src'));
                $('#ifm2').attr('src', $('#ifm2').attr('src'));
                $('#ifm3').attr('src', $('#ifm3').attr('src'));
            }, 1000);
        }
    }
</script>