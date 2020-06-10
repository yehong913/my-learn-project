/*公共处理JS 1.0*/
/**
 * 将yyyy-MM-dd、yyyy-MM-dd hh:mm:ss格式的字符串转化为Date类型
 * 
 * @param dateStrYYYYMMDD
 * @returns
 */
function getDateByFmtStr(dateStrYYYYMMDD) {
	if (dateStrYYYYMMDD != undefined && dateStrYYYYMMDD != null
			&& dateStrYYYYMMDD != '') {
		var date = dateStrYYYYMMDD.replace(/-/g,"/");
		return new Date(date);
	}
	return new Date();
}

function dateFormatter(val, row, index) {
	return dateFmtYYYYMMDD(val);
}

/**
 * 时间格式化 yyyy-MM-dd
 * 
 * @param date
 * @returns
 */
function dateFmtYYYYMMDD(val) {
	if (val != undefined && val != null && val != '') {
		var now = new Date(val);
		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		if (month < 10) {
			month = "0" + month;
		}
		var day = now.getDate();
		if (day < 10) {
			day = "0" + day;
		}
		return year + '-' + month + '-' + day;
	}
	return val;
}

/**
 * 时间格式化 yyyy-MM-dd hh:mm:ss
 * 
 * @param date
 * @returns
 */
function dateFmtFulltime(val) {
	if (val != undefined && val != null && val != '') {
		var now = new Date(val);
		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		if (month < 10) {
			month = "0" + month;
		}
		var day = now.getDate();
		if (day < 10) {
			day = "0" + day;
		}
		var hour = now.getHours()
		if (hour < 10) {
			hour = "0" + hour;
		}

		var minute = now.getMinutes();
		if (minute < 10) {
			minute = "0" + minute;
		}

		var second = now.getSeconds();
		if (second < 10) {
			second = "0" + second;
		}
		return year + '-' + month + '-' + day + " " + hour + ":" + minute + ":"
				+ second;
	}
	return val;
}

/**
 * 时间变更
 * 
 * @param date
 *            基数时间
 * @param day
 *            变更天数
 * @param beforOrAfter
 *            向前还是向后
 * @returns {Date}
 */
function dateChange(date, day, beforOrAfter) {
	var dateTime = date.getTime();
	var dayTime = day * 24 * 60 * 60 * 1000;
	var newDateTime;
	if (beforOrAfter == 'after') {
		newDateTime = dateTime + dayTime;
	} else {
		newDateTime = dateTime - dayTime;
	}
	return new Date(newDateTime);
}

/**
 * 时间比较、返回差距天数
 * 
 * @param startDate
 * @param endDate
 * @returns {Number}
 */
function dateCompare(startDate, endDate) {
	var startDateTime = startDate.getTime();
	var endDateTime = endDate.getTime();
	var dayNum = (endDateTime - startDateTime) / (24 * 60 * 60 * 1000)
	return dayNum;
}

/**
 *  比较两个时间
 *  validType:'compareTime[\'startProjectTime\',\'endProjectTime\']'
 * @param 开始时间id，结束时间id
 * @returns
 */
$.extend($.fn.validatebox.defaults.rules, {
	compareTime: {
		validator: function(value, param){
		var	t1 = $('#'+param[0]).datebox('getValue');
		var t2 = $('#'+param[1]).datebox('getValue');
			
			var start= $.fn.datebox.defaults.parser(t1);
			var end= $.fn.datebox.defaults.parser(t2);
			if(start>end){
					return false;
			}
		  return true;
		},
		message: '开始时间不能大于结束时间'
	},
	validateProcess: {
		validator: function(value, param){		
		var	t1 = $('#'+param[0]).numberbox('getValue');   		    
			if(t1>100 || t1<0){
					return false;
			}
		  return true;
		},
		message: '进度必须在0-100之间'
	}
});

/**
 * id=panelId的panel加载laod url
 * @param panelId
 * @param url
 * @param title 标题
 */
function loadPage(panelId,url, title) {
		if(null!=url&&""!=url){
			/*
			 * 此处为判断该节点是是否需要添加ifarme通过url参数读取满足不同需求指标
			 * 
			 * 								--朱聪 2016/6/13
			 * */
			if (url.indexOf('isIframe') != -1) {
				try {
				$(panelId).panel({
					href : "",
					title : title,
					content:createFrameForIDS(url)
				});
				} catch (e) {
					// TODO: handle exception
				}
			}else{
				$(panelId).panel({
					href : url,
					title : title
				});
			}
			
		}
		
}

function createFrameForIDS(url) {
	var s = '<iframe name="tabiframe" id="tabiframe"  scrolling="no" frameborder="0"  src="'+url+'" style="width:100%;height:98%;overflow-y:hidden;"></iframe>';
	return s;
}


/**
 * 进度条显示
 * @param value
 * @returns {String}
 */
function processFunction(value) {
	if (value) {
		var s = '<div style="width:100%;border:1px solid #dee5e7">' +
        '<div style="width:' + value + '%;background:#92cd18;color:#000000;padding-left:10px; word-wrap: normal; word-break: initial">' + value + '%' + '</div>'
        '</div>';
		return s;
	} else {
		var value1 = '0.00';
		var s1 = '<div style="width:100%;border:1px solid #dee5e7">' +
        '<div style="width:' + value1 + '%;background:#92cd18;color:#000000;padding-left:10px;word-wrap: normal; word-break: initial">' + value1 + '%' + '</div>'
        '</div>';
		return s1;
	}

}

function progressRateGreen(val){
	if(val == undefined || val == null || val == ''){
		val = 0;
	}
	return	'<div style="width:100%;border:1px solid #dee5e7">' +
    			'<div style="width:' + val + '%;background:#92cd18;color:#000000;padding-left:10px;">' + val + '%' + '</div>'
    		'</div>';
}

function progressRateRed(val){
	if(val == undefined || val == null || val == ''){
		val = 0;
	}
	return '<div style="width:100%;border:1px solid #dee5e7">' +
	'<div style="width:' + val + '%;background:#cc0000;color:#000000;padding-left:10px;">' + val + '%' + '</div>'
	'</div>';
}

/**
 * 关闭当前TAB页签并刷新父TAB页签，如果有多个父页签，用","隔开，父页签越靠前优先级越高
 * @param parentTabNames
 */
function closeCurrentTabAndRefreshParentTab(parentTabNames) {
	var tab = window.top.$('#maintabs').tabs('getSelected');
	var index = window.top.$('#maintabs').tabs('getTabIndex',tab);
	var parentTabArr = parentTabNames.split(',');
	var parentTab = '';
	for (var i=0; i<parentTabArr.length; i++) {
		try {
			parentTab = window.top.$('#maintabs').tabs('getTab', parentTabArr[i]);
			parentTab.panel('refresh');
		}catch(e){
			continue;
		}
	}
	window.top.closetTabAndSelect(index, parentTab);
}

/**
 * 首页待办修改后，刷新新的待办任务和首页待办列表：
 * @param parentTabNames
 */
function refreshTabsByTabsName(parentTabNames) {
		var tab = window.top.$('#maintabs').tabs('getSelected');
		var index = window.top.$('#maintabs').tabs('getTabIndex',tab);
		var parentTabArr = parentTabNames.split(',');
		var parentTab = '';
		for (var i=0; i<parentTabArr.length; i++) {
			try {
				parentTab = window.top.$('#maintabs').tabs('getTab', parentTabArr[i]);
				parentTab.panel('refresh');
			}catch(e){
				continue;
			}
		}
/*	var tabs = window.top.$('#maintabs').tabs('tabs');
	for (var i = 0; i < tabs.length; i++) {
		if ("待办任务" == tabs[i].panel("options").title) {
			try {
				window.top.$('#maintabs').tabs('tabs')[i].panel('refresh');
				break;
			} catch (e) {
				continue;
			}
			
		}		
	}
	
	for (var i = 0; i < tabs.length; i++) {
		if ("首页" == tabs[i].panel("options").title) {
			try {
				window.top.$('#maintabs').tabs('tabs')[i].panel('refresh');
			} catch (e) {
				continue;
			}
			
		}
	}*/	
}

/**
 * 刷新父TAB页签，如果有多个父页签，用","隔开，父页签越靠前优先级越高
 * @param parentTabNames
 */
function refreshParentTab(parentTabNames) {
	var parentTabArr = parentTabNames.split(',');
	var parentTab = '';
	for (var i=0; i<parentTabArr.length; i++) {
		try {
			parentTab = window.top.$('#maintabs').tabs('getTab', parentTabArr[i]);
			parentTab.panel('refresh');
		}catch(e){
			continue;
		}
	}
}
