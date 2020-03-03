$(function() {

	
	var map = new AMap.Map("allmap", {
		// center: [118.796623, 32.059352],118.727533
				resizeEnable: true,
				center: [118.727533, 31.995434],
				zoom: 15,
	});

	AMap.plugin([
		'AMap.ToolBar',
	], function() {
		// 在图面添加工具条控件，工具条控件集成了缩放、平移、定位等功能按钮在内的组合控件
		map.addControl(new AMap.ToolBar({
			// 简易缩放模式，默认为 false
			liteStyle: true
		}));
	});
/* 
	var marker = new AMap.Marker({
				position: new AMap.LngLat(118.727633, 31.995434),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
				icon: './img/zfsxt.png',
				title: '北京'
			});
	var marker1 = new AMap.Marker({
				position: new AMap.LngLat(118.727633, 31.991434),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
				icon: './img/zfsxt.png',
				title: '北京'
			});
			
	map.add(marker);
	map.add(marker1)
	map.setFitView();
	

	
	var interval3=setInterval(function(){
	     updateLocation();
	},5000);
	 */



	
	
	
	
	
	var choseCaeramList;
	/* 用户选择的ids ,全局*/
	var ids;
	var markerList=[];
	$('#all').change(function(){
		choseCaeramList=$('#all').selectpicker('val');
		ids=parseToIDS(choseCaeramList);
		updateCameraMessage(ids);
	});
			
	/* 后台请求接口,更新数据 */		
	function  updateCameraMessage(paramIds){
		$.ajax({
					type : "POST",
					dataType : "json",
					url : 'http://192.168.1.140:8080/njygc-admin/CameraNotice/noticelist',
					data : {
						ids : paramIds
					},
					success:function(data){
						/* 用于转换图标 */
						var makerMessage;
						if(data.code =='0'){
							if(data.data.length>1){
								for(var i=0;i<data.data.length;i++){
									var lnglats ;
									var iii=new AMap.LngLat(data.data[i].locationX,data.data[i].locationY);
									
									if(data.data[i].cameraNoticeList.length>0){
										lnglats=[data.data[i].locationX,data.data[i].locationX];
										makerMessage=new AMap.Marker({
																		position: new AMap.LngLat(data.data[i].locationX,data.data[i].locationY),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
																		icon: './img/notice.png',
																		title:data.data[i].cameraName,
																		offset: new AMap.Pixel(-5,-38),
																		clickable: true,
																	/* 	 content: '<div class="marker-route marker-marker-bus-from">1111</div>' */
																		/* animation:'AMAP_ANIMATION_DROP', */
																		
																	});
																	
									openWindow(makerMessage,data.data[i]);		
															
									}else if(data.data[i].cameraNoticeList.length<1){
										makerMessage=new AMap.Marker({
																		position: new AMap.LngLat(data.data[i].locationX,data.data[i].locationY),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
																		icon: './img/gdsxt.png',
																		title:data.data[i].cameraName,
																		offset: new AMap.Pixel(-5,-38),
																	/* 	clickable: true,
																		content: '<div class="marker-route marker-marker-bus-from">11113232</div>' */
																		/* animation:'AMAP_ANIMATION_BOUNCE' */
																	});
										openWindow(makerMessage,data.data[i]);						 					
									}
									
									
									
									/* 设置文本 */
								/* 	makerMessage.setLabel({
									                   offset: new AMap.Pixel(-10, -30),  //设置文本标注偏移量
									                   content: "设备在线", //设置文本标注内容
									                   direction: 'left' //设置文本标注方位
									}); */
									
									
								
									
																	
									markerList.push(makerMessage);
									
								}
							}
							map.add(markerList);
						}else{
							layer.msg("后台接口返回信息错误");
						}

					},error:function(){
						layer.msg("请求消息错误error");
					}
				})
	}	
		
		
		
	
	/* 更新地图坐标 */
	function parseToMapMarker(cameraList){
		
	}
	
	
	/* 数组拼接String */
	function parseToIDS(cameraids){
		if(cameraids==null){
			return ''
		}else{
			var jsonids='';
			for(var x=0;x<cameraids.length;x++){
				jsonids+=','+cameraids[x];
			}
		}
		return  jsonids;
	}
	
	/* 定时任务 */
	function updateLocation(a,b){
		map.remove(a);
		a.splice(0);
		updateCameraMessage(b);
	}
	
	
	
	 setInterval(function(){
		     updateLocation(markerList,ids);
	},5000);
	
	
	
	//多个markers点击事件
	
	 /*   function markerrender(markers,datas) {
	       
	        for (let i=0;i<markers.length;i++){
	            AMap.event.addListener(markers[i],"click",function () {
	               //markers的点击事件就看大家是想定义什么事件了，我这里是有进行一个弹窗的展示，出现的弹窗展示当前marker的信息，那么这就不是讨论的重点了，如果有疑问大家可以发信息给我
	              console.log(datas);
	            });
	
	        }
	    } */
	

	function  openWindow(msessage,thisCameraData){
			AMap.event.addListener(msessage, 'click', function () {
				index(thisCameraData);
				//创建信息窗口
				/* var infoWindow = new AMap.InfoWindow({
					isCustom: true,  //	使用自定义窗体
					content: '<div onclick='+index()+' style="width:180px;height:200px;background:red" class="myname"<111111</div>',
					offset: new AMap.Pixel(-15, -25)
				});
				//打开信息窗口
				infoWindow.open(map, [118.727633, 31.991434]); //后面的参数指的是经纬度，在此显示窗口 */
			});
	}
	
	/* 点击图标显示 告警信息和视频连接 */
	function index(thisJsonData){
		
		$('.windowMessage').show();
	}
	
	/* 隐藏点击事件的窗体 */
	$('.closeSpan').click(function(){
		$('.windowMessage').hide();
	});
	
	/* 打开新的视频窗口 */
	function opneNewCamera(url){
			window.open(url,'addFileWindow','toolbar=no,location=no,resizable=no, height=500, width=680,,scrollbars=yes ,left=380,top=100');
	}
	
	
});function opneNewCamera(){
			window.open("http://localhost:8080/njygc-admin/resources/njygc/index.html?rtmpStr=rtmp://58.200.131.2:1935/livetv/hunantv&type=rtmp/flv",
						"newwindow",
						'addFileWindow','toolbar=no,location=no,resizable=no, height=500, width=680,,scrollbars=yes ,left=380,top=100');
	}