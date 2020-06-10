<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,lhgdialog"></t:base>
<script type="text/javascript" src="${pageContext.request.contextPath }/plug-in/js/highcharts7.0.2/highcharts.js"></script>
<head>
</head>
<body>
<div id="containerPlanLifeCycle" style="width:100%; height: 90%; margin: 0 auto;"></div>
</body>
<script type="text/javascript">
    $(function(){
        var lifeCycleStr = '${lifeCycleArray}';
        var lifeCycleArray = lifeCycleStr.split(',');
        var array=[];
        for (var i=0;i<lifeCycleArray.length;i++) {
            array.push(parseInt(lifeCycleArray[i]));
        }
        setTimeout(function(){
            $('#containerPlanLifeCycle').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: '计划生命周期状态统计'
                },
                credits:{
                    enabled:false
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
                    categories: [
                        '拟制中','已发布','待接收','执行中','完工确认','已完工'
                    ],
                    crosshair: true
                },
                colors:['#447EC1'],
                yAxis: {
                    min: 0,
                    title: {
                        text: ''
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:11px">{point.key}</span>',
                    pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b> <br/>'
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0,
                        dataLabels:{
                            enabled:true
                        }
                    }
                },
                series: [{
                    name: '计划生命周期状态统计',
                    data: array,
                    dataLabels:{
                        enabled:true,
                        crop:false,
                        overflow:'none',
                        color:'black',
                        align:'center'
                    }
                }]
            });
            $(".highcharts-legend").css('top','20px');
        },1000);
    });
</script>
<script type="text/javascript" src="js/slide.js?v=1"></script>

