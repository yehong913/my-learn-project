$(function() {

	var echartsWarptop = document.getElementById('echartsWarptop');

	var resizeWorldMapContainer = function() { //用于使chart自适应高度和宽度,通过窗体高宽计算容器高宽
		echartsWarptop.style.width = echartsWarptop.innerWidth + 'px';
		echartsWarptop.style.height = echartsWarptop.innerHeight + 'px';
	};

	resizeWorldMapContainer(); //设置容器高宽

	window.onresize = function() { //用于使chart自适应高度和宽度

		resizeWorldMapContainer(); //重置容器高宽
		leftTop.resize();
	};

	var leftTop = echarts.init(echartsWarptop);

	var XIdx = [];
	var data = ['1月', '2月', '3月', '4月'];

	for (var i = 1; i <= data.length; i++) {
		XIdx.push(i);
	}

	option = {
		 color: ['#29eefc'],
		tooltip: {
			trigger: 'axis',
			backgroundColor: '#018ff5',
			axisPointer: {
				type: 'shadow'
			},
			formatter: function(params) {
				return params[0].name + ":" + params[0].data + "条";
			}
		},
		grid: {
			top: '15%',
			left: '1%',
			right: '1%',
			bottom: '0%',
			containLabel: true
		},
		xAxis: [{
			type: 'category',
			axisLabel: {
				'interval': 0,
			},
			data: ['1月份', '2月份', '3月份', '4月份'],
			axisTick: {
				alignWithLabel: true
			},
			axisLine: {
				lineStyle: {
					color: '#fff',
				}
			},
			axisTick: {
				show: false
			}
		}],
		yAxis: [{
			type: 'value',
			name: '条',
			splitLine: {
				show: false,
				lineStyle: {
					color: ['#fff']
				}
			},
			axisLine: {
				lineStyle: {
					color: '#fff',
				}
			},
			axisTick: {
				show: false
			}
		}],
		series: [{
			type: 'bar',
			barWidth: '40%',
			label: {
				normal: {
					show: true,
					color:'#fff',
					position: 'top'
				}
			},
			itemStyle: {
				normal: {
					// color: function(params) {
					// 	var colorList = ['#f3dc10', '#fe7a11', '#00eefe', '#23e872'];
					// 	return colorList[params.dataIndex]
					// },
					label: {
						show: true,
						position: 'top',
						formatter: '{c}条'
					}
				}
			},
			data: [5220, 5620, 7702, 9202]
		}]
	};

	leftTop.setOption(option);

	var index = 0; //播放所在下标
	var mTime = setInterval(function() {
		leftTop.dispatchAction({
			type: 'showTip',
			seriesIndex: 0,
			dataIndex: index
		});
		index++;
		if (index > data.length) {
			index = 0;
		}
	}, 2000);


});
