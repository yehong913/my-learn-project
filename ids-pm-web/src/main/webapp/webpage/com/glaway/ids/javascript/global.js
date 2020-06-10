

/*
 * 根据actionModel取得菜单列表
 */
function getActions(actionModelName, param, oid, containerOid) {
	var actionName = "getActions";
	if (param == undefined) {
		param = {};
	}
	param.actionModelName = actionModelName;
	if (oid) {
		param.oid = oid;
	}
	if (containerOid) {
		param.containerOid = containerOid;
	}
	var retObject = postAction(actionName, param, true);
	if (retObject.retCode == 0) {
		return retObject.retObject;
	}
	return [];
}

function openWindow(url, x, y, width, height) {

	var isIE = (navigator.appName == "Microsoft Internet Explorer");
	var xx;
	var yy;
	var w;
	var h;
	if (x != undefined) {
		xx = x;
	} else {
		xx = screen.width * 0.2;
	}
	if (y != undefined) {
		yy = y;
	} else {
		yy = screen.height * 0.2;
	}
	if (width != undefined) {
		w = width;
	} else {
		w = screen.width * 0.6;
	}
	if (height != undefined) {
		h = height;
	} else {
		h = screen.height * 0.6;
	}

	if (isIE) {
		var nw = window.open("", "_blank", "resizable=true,status=no");
		nw.focus();

		nw.moveTo(xx, yy);
		nw.resizeTo(w, h);
		nw.location = url;
	} else {
		window
				.open(
						url,
						"_blank",
						"top="
								+ yy
								+ ",left="
								+ xx
								+ ",height="
								+ h
								+ ",width="
								+ w
								+ ",location=no,toolbar=no,menubar=no,scrollbars=no,resizable=true,status=no");
	}
}

/**
 * 以ajax同步方式调用服务器端接口
 * 
 * @param url
 * @param param
 * @returns
 */
function postRequestMethod(url, param) {
	var retData;
	$.ajax({
		type : "POST",
		url : url,
		async : false,
		data : param,
		success : function(data, data2) {
			retData = data;
			return retData;
		},
		dataType : "json"
	});
	return retData;
}

/**
 * 获取当前日期
 * 
 * @returns {String}
 */
function getCurrentDate() {
	var now = new Date();
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var day = now.getDate();
	return year + "-" + month + "-" + day;
}

/**
 * 获取当前日期时间
 * 
 * @returns {String}
 */
function getCurrentDateTime() {
	var now = new Date();
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var day = now.getDate();
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":"
			+ second;
}

/**
 * 
 * @param startTime
 * @param endTime
 * @returns 根据开始时间和结束时间计算间隔时间，精确到小时
 */
function dateDiff(startTime, endTime) {
	var startDate = new Date(Date.parse(startTime.replace(/-/g, "/")));
	var endDate = new Date(Date.parse(endTime.replace(/-/g, "/")));
	var startLongTime = startDate.getTime();
	var endLongTime = endDate.getTime();
	var diffTime = endLongTime - startLongTime;
	return diffTime / (60 * 60 * 1000);
}

/**
 * 
 * @param startDate
 * @param endDate
 * @returns 根据开始时间和结束时间计算间隔时间，精确到小时
 */
function calculateDate(startDateTimeStr, endDateTimeStr) {
	var startLongTime = parseDateStr2Date(startDateTimeStr).getTime();
	var endLongTime = parseDateStr2Date(endDateTimeStr).getTime();
	var diffTime = endLongTime - startLongTime;
	return diffTime / (60 * 60 * 1000);
}

function parseDateStr2Date(dateTimeStr) {
	var array = dateTimeStr.split(" ");
	var dateStr = array[0];
	var timeStr = array[1];
	var dateArray = dateStr.split("-");
	var year = dateArray[0];
	var month = dateArray[1] - 1;
	var day = dateArray[2];
	var timeArray = timeStr.split(":");
	var hour = timeArray[0];
	var minute = timeArray[1];
	var second = timeArray[2];
	return new Date(year, month, day, hour, minute, second, 0);
}


// 自定义实现map
function HashMap() {
	var size = 0;
	var entry = new Object();

	this.put = function(key, value) {
		if (!this.containsKey(key)) {
			size++;
		}
		entry[key] = value;
	}

	this.get = function(key) {
		return this.containsKey(key) ? entry[key] : null;
	}

	this.remove = function(key) {
		if (this.containsKey(key) && (delete entry[key])) {
			size--;
		}
	}

	this.containsKey = function(key) {
		return (key in entry);
	}

	this.containsValue = function(value) {
		for ( var prop in entry) {
			if (entry[prop] == value) {
				return true;
			}
		}
		return false;
	}

	this.size = function() {
		return size;
	}

	this.clear = function() {
		size = 0;
		entry = new Object();
	}
	
	this.keySet = function(){
		var keys = new Array();
		for(var key in entry)
		{
			keys.push(key);
		}
		return keys;
	}
	
	this.values = function(){
		var values = new Array();
		for(var key in entry)
		{
		    values.push(entry[key]);	
		}
		return values;
	}
	
	this.each = function(cb){
		for(var key in entry)
		{
			cb.call(this,key,entry[key]);   
		}
	}

	this.toJSONString=function(){
		var entryArray = new Array();
		for(var key in entry)
		{
			var tmpEntry = new Entry(key,entry[key]);
			entryArray.push(tmpEntry);
		}
		return $.toJSON(entryArray);
	}
	
	this.toArray=function(){
	    var entryArray = new Array();
	    for(var key in entry)
        {
            var tmpEntry = new Entry(key,entry[key]);
            entryArray.push(tmpEntry);
        }
	    return entryArray;
	}
	
	function Entry(key,value){
	   this.key = key;
	   this.value = value;
    }
}


// 展现加载进度条
function showLoadingDiv() {
	var loadingDiv = $("#loadingDiv");
	var loadingImg = $("#loadingImg");
	loadingDiv.show();
	var imgArray = [ "../../images/ajaxstatus/01.png",
			"../../images/ajaxstatus/02.png", "../../images/ajaxstatus/03.png",
			"../../images/ajaxstatus/04.png", "../../images/ajaxstatus/05.png",
			"../../images/ajaxstatus/06.png", "../../images/ajaxstatus/07.png",
			"../../images/ajaxstatus/08.png" ];
	var loadingCount = 0;
	return setInterval(function() {
		loadingImg.attr("src", imgArray[loadingCount]);
		if (loadingCount == 8) {
			loadingCount = 0;
		}
		loadingCount++;
	}, 100);
}

// 隐藏加载进度条
function hideLoadingDiv(executeId) {
	clearInterval(executeId);
	var loadingDiv = $("#loadingDiv");
	loadingDiv.hide();
}



var divMask = $("<div class=\"datagrid-mask\"></div>");
var divMaskMsg = $("<div class=\"datagrid-mask-msg\"></div>");
/**
 * 
 * @param flag true表示显示进度条，false表示隐藏进度条
 * @param msg 进度条转动显示的提示信息
 */
function showWaitProgressDiv(msg,flag)
{
	msg=(msg==null?'正在加载，请稍候...':msg);
	flag= (flag== null?true:flag); 
	if(flag)
	{
	   divMask.css({display:"block",width:"100%",height:document.body.scrollHeight}).appendTo("body");
	   divMaskMsg.html(msg).appendTo("body").css({display:"block",left:($(document.body).outerWidth(true)-190)/2,top:($(window).height()-45)/2});
	}
	else
	{
		divMask.css({display:"none",width:"100%",height:document.body.scrollHeight});
		divMaskMsg.css({display:"none",left:($(document.body).outerWidth(true)-190)/2,top:($(window).height()-45)/2});
	}
}

/**
 * easyui分页
 * @param data
 * @returns {___anonymous7400_7468}
 */

function pagerFilter(data){
    if (typeof data.length == 'number' && typeof data.splice == 'function'){    // is array
        data = {
            total: data.length,
            rows: data
        }
    }
    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        onSelectPage:function(pageNum, pageSize){
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh',{
                pageNumber:pageNum,
                pageSize:pageSize
            });
            dg.datagrid('loadData',data);
        }
    });
    if (!data.originalRows){
        data.originalRows = (data.rows);
    }
    var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
}

/**
 * 
 * @param url
 */
function parseURL(url)
{
	 var a = document.createElement('a');
	 a.href= url;
	 return {
		 source:url,
		 rotocol:a.protocol.replace(':',''),
		 host:a.hostname,
		 port:a.port,
		 query:a.search,
		 params:(function(){
			 var ret = {};
			 var seg = a.search.replace(/^\?/,'').split('&');
			 for(var i=0;i<seg.length;i++)
			 {
				 if(!seg[i])
				 {
				    continue;	 
				 }
				 paramArray = seg[i].split('=');
				 ret[paramArray[0]]=paramArray[1];
			 }
			 return ret;
		 })()
	 };
}

/**
 * 系统全局变量属性配置
 */
var systemConfig = new HashMap();
//甘特图显示模式(同步模式:sync,异步模式:async)
systemConfig.put('ganttViewMode','sync');
