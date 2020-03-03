$(function()
{

 var echartsWarpbottom= document.getElementById('echartsWarpbottom');

  var resizeWorldMapContainer = function () 
  {//用于使chart自适应高度和宽度,通过窗体高宽计算容器高宽
      echartsWarpbottom.style.width = echartsWarpbottom.innerWidth+'px';
      echartsWarpbottom.style.height = echartsWarpbottom.innerHeight+'px';
  };
   
  resizeWorldMapContainer ();//设置容器高宽
   
  window.onresize = function () {//用于使chart自适应高度和宽度
      
      resizeWorldMapContainer();//重置容器高宽
      rightBott.resize();
  };


  var rightBott = echarts.init(echartsWarpbottom);
  var XIdx = [];
  var data = ['0','4','8','10','12','14'];

  for(var i = 1; i <= data.length; i++) {
      XIdx.push(i);
  }

  option = {
      tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'line'
        },
        formatter: function(params)
        {
          return params[0].name+"<br>"
          +params[0].seriesName+":"+params[0].data+"辆"+"<br>"
          +params[1].seriesName+":"+params[1].data+"辆"+"<br>"
          +params[2].seriesName+":"+params[2].data+"辆"+"<br>"
          +params[3].seriesName+":"+params[3].data+"辆";
        },
        backgroundColor:'#018ff5'
      },
      // legend: {
      //   icon:'stack',
      //   data: ['国道', '省道','县道','农村道路'],
      //   right:'4%',
      //   top:'20px',
      //   itemWidth: 11,  // 设置宽度
      //   itemHeight:11, // 设置高度
      //   textStyle:{
      //       color:'#fff'
      //   }
      // },
      grid: 
      {
        top:'24%',
        left: '1%',
        right: '8%',
        bottom: '0%',
        containLabel: true
      },
      xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['危险品车','普通货车','客运包车','客运班车'],
          axisTick: 
          {
              alignWithLabel: true
          },
          axisLine:
          {
              lineStyle:
              {
                  color:'#fff',
              }
          },
          axisTick: 
          {
            show: false
          }
      },
      yAxis: {
          type: 'value',
          name:'(km)',
          splitLine: 
          {
              show: false,
              lineStyle: {
                  color: ['#fafafa']
              }
          },
          axisLine:{
              lineStyle:{
                  color:'#fff',
              }
          },
          axisTick: {
            show: false
          }
      },
	  // '#f3dc10', '#fe7a11', '#00eefe', '#23e872'
      series: [
          {
              name:'危险品车',
              type:'line',
			  symbol: "none",
              color:'#f3dc10',
              data:[1023, 2100, 1200, 3000]
          },
          {
              name:'普通货车',
              type:'line',
			  symbol: "none",
              color:'#fe7a11',
              data:[200, 300, 400, 302]
          },
          {
              name:'客运包车',
              type:'line',
			  symbol: "none",
              color:'#00eefe',
              data:[100, 888, 6305, 1020]
          },
          {
              name:'客运班车',
              type:'line',
			  symbol: "none",
              color:'#23e872',
              data:[1003, 2400, 3500, 4000]
          }
      ]
  };

  rightBott.setOption(option);
  var index = 0; //播放所在下标
  var mTime = setInterval(function() 
  {
      rightBott.dispatchAction(
      {
          type: 'showTip',
          seriesIndex: 0,
          dataIndex: index
      });
      index++;
      if(index > data.length) 
      {
          index = 0;
      }
  }, 2500);
});