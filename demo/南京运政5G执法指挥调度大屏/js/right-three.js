$(function() {
	var length = 24; //此处为动态数据的长度
	var hei = '70%'; //动态获取图表高度

	$("#rightThree").css('height', hei); //动态设置图表高度
	showchart1(); //图表执行

	function showchart1() { //console.log(dbcnumb)
		var myChart = echarts.init(document.getElementById('rightThree'));

		var colorList = [
			'#f3dc10', '#fe7a11', '#00eefe', '#23e872', '#1b99ff'
		];

		// 总和
		var total = {
			name: '总车辆',
			value: '107万辆'
		}

		var originalData = [{
				value: 25,
				name: '南京50万辆'
			}, {
				value: 35,
				name: '苏州30辆'
			}, {
				value: 85,
				name: '盐城10万'
			}, {
				value: 10,
				name: '宿迁10万'
			},
			{
				value: 20,
				name: '连云港10万'
			}
		];

		echarts.util.each(originalData, function(item, index) {
			item.itemStyle = {
				normal: {
					color: colorList[index]
				}
			};
		});
		if (screen.height <= 900) {
			option = {
				title: [{
					text: total.name,
					left: '49%',
					top: '43%',
					textAlign: 'center',
					textBaseline: 'middle',
					textStyle: {
						color: '#fff',
						fontWeight: 'normal',
						fontSize: 12
					}
				}, {
					text: total.value,
					left: '49%',
					top: '54%',
					textAlign: 'center',
					textBaseline: 'middle',
					textStyle: {
						color: '#00eefe',
						fontWeight: 'normal',
						fontSize: 20
					}
				}],
				series: [{
					hoverAnimation: true, //设置饼图默认的展开样式
					radius: [46, 60],
					name: 'pie',
					type: 'pie',
					selectedMode: 'single',
					selectedOffset: 10, //选中是扇区偏移量
					clockwise: true,
					startAngle: 100,
					label: {
						normal: {
							textStyle: {
								fontSize: 12,
								color: '#fff'
							}
						}
					},
					labelLine: {
						normal: {
							lineStyle: {
								color: '#fff',

							}
						}
					},
					data: originalData
				}]
			};
		}
		else
		{
			option = {
				title: [{
					text: total.name,
					left: '49%',
					top: '43%',
					textAlign: 'center',
					textBaseline: 'middle',
					textStyle: {
						color: '#fff',
						fontWeight: 'normal',
						fontSize: 14
					}
				}, {
					text: total.value,
					left: '49%',
					top: '54%',
					textAlign: 'center',
					textBaseline: 'middle',
					textStyle: {
						color: '#00eefe',
						fontWeight: 'normal',
						fontSize: 26
					}
				}],
				series: [{
					hoverAnimation: true, //设置饼图默认的展开样式
					radius: [60, 80],
					name: 'pie',
					type: 'pie',
					selectedMode: 'single',
					selectedOffset: 10, //选中是扇区偏移量
					clockwise: true,
					startAngle: 100,
					label: {
						normal: {
							textStyle: {
								fontSize: 16,
								color: '#fff'
							}
						}
					},
					labelLine: {
						normal: {
							lineStyle: {
								color: '#fff',
			
							}
						}
					},
					data: originalData
				}]
			};
		}





		myChart.setOption(option, true);
		window.onresize = function() {
			//适应页面

			myChart.resize();
		}
	}


});
