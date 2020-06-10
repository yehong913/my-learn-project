<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,lhgdialog"></t:base>
<script type="text/javascript" src="${pageContext.request.contextPath }/plug-in/js/highcharts7.0.2/highcharts.js"></script>
<head>
</head>
<body>
<div id="containerPlanCompletion" style="width:100%; height: 90%; margin: 0 auto;"></div>
</body>
<script type="text/javascript">
    $(function(){
        var wbs= '${wbs}';
        var wbsStrArray = wbs.split(',');
        var wbsArray=[];
        for (var i=0;i<wbsStrArray.length;i++) {
            wbsArray.push(parseInt(wbsStrArray[i]));
        }

        var flow= '${flow}';
        var flowStrArray = flow.split(',');
        var flowArray=[];
        for (var i=0;i<flowStrArray.length;i++) {
            flowArray.push(parseInt(flowStrArray[i]));
        }
        var task= '${task}';
        var taskStrArray = task.split(',');
        var taskArray=[];
        for (var i=0;i<taskStrArray.length;i++) {
            taskArray.push(parseInt(taskStrArray[i]));
        }

        setTimeout(function(){
            $('#containerPlanCompletion').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: '计划完成情况'
                },
                credits:{
                  enabled:false
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
                    categories: [
                        'WBS计划','流程计划','任务计划'
                    ],
                    crosshair: true
                },
                colors:['#447EC1','#F6AE63','#ED7768'],
                yAxis: {
                    min: 0,
                    title: {
                        text: ''
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:11px">{point.key}</span>',
                    pointFormat: '<div><span style="color:{point.color}">{point.name}</span></div><br/>' +
                        '<div><span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> <br/></div>'
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
                    name: '已完成',
                    data: wbsArray,
                    dataLabels:{
                            enabled:true,
                            crop:false,
                            overflow:'none',
                            color:'black',
                            align:'center'
                        }
                    },{
                    name: '正常未完成',
                    data: flowArray,
                    dataLabels:{
                        enabled:true,
                        crop:false,
                        overflow:'none',
                        color:'black',
                        align:'center'
                    }
                    },{
                    name: '延期未完成',
                    data: taskArray,
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