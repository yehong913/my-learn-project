//工具栏
function initToolbar() {
	myToolbar = new dhtmlXToolbarObject("toolbarObj");
	myToolbar.setIconsPath("./images/");
	var scaleOpts = Array(Array('day', 'obj', '天', 'day.png'), Array('week',
			'obj', '周', 'week.png'), Array('month', 'obj', '月', 'month.png'),
			Array('year', 'obj', '年', 'year.png'));

	myToolbar.addButtonSelect("scaleDateFormat", 1, "时间刻度", scaleOpts,
			"timerobt.gif", "timerobt.gif");

	var viewOpts = Array(
			Array('standardView', 'obj', '标准', 'openin_gantt.gif'), Array(
					'defaultView', 'obj', '默认', 'gantt.gif'));
	myToolbar.addButtonSelect("ganttView", 9, "视图", viewOpts, "show.gif",
			"show.gif");

	myToolbar.attachEvent("onclick", function(id) {
		if (id == "day") {
			setScaleConfig("1");
			gantt.render();
			reloadRenderGantt();
		} else if (id == "week") {
			setScaleConfig("2");
			gantt.render();
			reloadRenderGantt();
		} else if (id == "month") {
			setScaleConfig("3");
			gantt.render();
			reloadRenderGantt();
		} else if (id == "year") {
			setScaleConfig("4");
			gantt.render();
			reloadRenderGantt();
		} else if (id == "default") {
			setScaleConfig("0");
			gantt.render();
			reloadRenderGantt();
		} else if (id == 'standardView') {
			gantt.config.grid_width = 0;
			gantt.render();
			reloadRenderGantt();
		} else if (id == 'defaultView') {
			gantt.config.grid_width = 500;
			gantt.render();
			reloadRenderGantt();
		}
	});

}

//时间刻度设置
function setScaleConfig(value) {
	switch (value) {
	case "0":
		initGanttScales();
		break;
	case "1":
		gantt.config.scale_unit = "month";
		gantt.config.step = 1;
		gantt.config.date_scale = "%M";
		gantt.config.min_column_width = 50;
		gantt.config.subscales = [ {
			unit : "day",
			step : 1,
			date : "%d"
		} ];
		gantt.config.scale_height = 50;
		gantt.templates.date_scale = null;
		break;
	case "2":
		var weekScaleTemplate = function(date) {
			var dateToStr = gantt.date.date_to_str("%m-%d");
			var endDate = gantt.date.add(gantt.date.add(date, 1, "week"), -1,
					"day");
			return dateToStr(date) + "至" + dateToStr(endDate);
		};
		gantt.config.scale_unit = "month";
		gantt.config.step = 1;
		gantt.config.date_scale = "%M";
		gantt.config.min_column_width = 50;
		gantt.config.subscales = [ {
			unit : "week",
			step : 1,
			date : "%D",
			template : weekScaleTemplate
		} ];

		gantt.config.scale_height = 50;
		gantt.templates.date_scale = null;

		break;
	case "3":
		gantt.config.scale_unit = "year";
		gantt.config.step = 1;
		gantt.config.date_scale = "%Y";
		gantt.config.min_column_width = 50;
		gantt.config.subscales = [ {
			unit : "month",
			step : 1,
			date : "%M"
		} ];
		gantt.config.scale_height = 50;
		gantt.templates.date_scale = null;
		break;
	case "4":
		gantt.config.scale_unit = "year";
		gantt.config.step = 1;
		gantt.config.date_scale = "%Y";
		gantt.config.min_column_width = 50;
		gantt.config.subscales = [];
		gantt.config.scale_height = 25;
		gantt.templates.date_scale = null;
		break;
	}
}

//初始甘特图的配置
function initGanttConfig() {
	gantt.config.date_scale = "%m-%d";
	gantt.config.task_date = "%m-%d";
	gantt.config.xml_date = "%Y-%m-%d %H:%i";
	gantt.config.min_column_width = 40;
	gantt.config.scale_height = 50;
	gantt.config.grid_width = 550;
	gantt.config.autosize = false;
	gantt.config.autofit = false;
	gantt.config.work_time = true;
	gantt.config.drag_links = false;
	gantt.config.drag_move = false;
	gantt.config.drag_progress = false;
	gantt.config.drag_resize = false;
	gantt.config.select_task = false;
	gantt.config.grid_resize = true;

	gantt.config.static_background = true;
	
	gantt.config.highlight_critical_path = true;

	//gantt.config.show_progress = false;
	//gantt.config.branch_loading = true;
}

//初始甘特图的内容
function initGanttColumnContent() {
	gantt.config.columns = [ {
		name : "lineNumber",
		label : "序号",
		width : 40,
		align : "center"
	}, {
		name : "text",
		label : "计划名称",
		width : 250,
		tree : true
	}, {
		name : "start_date",
		label : "开始时间",
		width : 80,
		align : "center"
	}, {
		name : "end_date",
		label : "结束时间",
		width : 80,
		align : "center",
		hide : true
	}, {
		name : "ganttEndDate",
		label : "结束时间",
		width : 80,
		align : "center"
	},{
		name : "owner",
		label : "负责人",
		width : 80,
		align : "center"
	}];
}

//初始化时间刻度
function initGanttScales() {
	gantt.config.subscales = [ {
		unit : "day",
		step : 1,
		date : "%D"
	} ];

	gantt.templates.scale_cell_class = function(date) {
		if (date.getDay() == 0 || date.getDay() == 6) {
			return "weekend";
		}
	};

	gantt.templates.task_cell_class = function(item, date) {
		if (date.getDay() == 0 || date.getDay() == 6) {
			return "weekend";
		}
	};

}

//初始甘特图的模板
function initGanttTemplate() {

	gantt.templates.task_class = function(st, end, item) {
		return gantt.getChildren(item.id).length ? "gantt_project" : "";
	};

	/*gantt.templates.link_class = function(link) {
		var types = gantt.config.links;
		switch (link.type) {
		case types.finish_to_start:
			return "finish_to_start";
			break;
		case types.start_to_start:
			return "start_to_start";
			break;
		case types.finish_to_finish:
			return "finish_to_finish";
			break;
		}
	};*/

	gantt.templates.tooltip_text = function(start, end, event) {
		return "<b>计划名称:</b> " + event.text + "<br/><b>开始时间:</b> "
		+ gantt.templates.tooltip_date_format(start)
		+ "<br/><b>结束时间:</b> " + event.ganttEndDate
		+ "<br/><b>工期:</b> " + event.workTime + "（天）"
		+ "<br/><b>输出:</b> " + event.deliInfo
		+ "<br/><b>负责人:</b> " + event.owner
		+ "<br/><b>责任部门:</b> " +event.department
		+ "<br/><b>进度:</b> " + event.progress + "%"
		+ "<br/><b>计划等级:</b> " + event.planLevel
		+ "<br/><b>状态:</b> " + event.status;

	};

	gantt.templates.grid_folder = function(item) {
		if (item.lineNumber == 0) {
			return "<div class='gantt_tree_icon tree_root'></div>";
		} else {
			return "<div class='gantt_tree_icon gantt_folder_"
					+ (item.$open ? "open" : "closed") + "'></div>";
		}
	};

	gantt.templates.grid_file = function(item) {
		if (item.lineNumber == 0) {
			return "<div class='gantt_tree_icon tree_root'></div>";
		} else {
			return "<div class='gantt_tree_icon gantt_file'></div>";
		}
	};

}

var selectTaskLinkObject = {};
selectTaskLinkObject.selectId = null;
selectTaskLinkObject.selectType = null;

var menuMap = new HashMap();

var projectOid = null;

var selectTaskId = null;

function initGanttEvent() {
	// 任务双击
	gantt.attachEvent("onTaskDblClick", function(id, event) {
		return false;
	});

	// 任务被选中
	gantt.attachEvent("onTaskClick", function(id, event) {
		selectTaskId = id;
		gantt.selectTask(id);
		return true;
	});
}

$(function() {
	debugger;
	var projectId = parseURL(document.URL).params.paramArr.split('_')[0];
	var url = "/ganttController.do?getGanttResponse&projectId="+projectId+"&planViewInfoId="+parseURL(document.URL).params.paramArr.split('_')[2];
	url = '/'+parseURL(document.URL).params.paramArr.split('_')[1]+url;
	$.post(url, function(
			data) {

		// 隐藏加载进度条
		//hideLoadingDiv(executeId);
		initToolbar();
		initGanttConfig();
		initGanttColumnContent();
		initGanttScales();
		initGanttTemplate();
		initGanttEvent();
		setScaleConfig('0');
		initAddMarker(data)
		gantt.init("gantt_here");
		var s = gantt.parse(data);
		reloadRenderGantt();
	});
});
/**
 * 初始化画虚线
 * */
function initAddMarker(data) {
	var month = [ '01', '02', '03', '04', '05', '06', '07', '08', '09', '10',
			'11', '12' ];
	var year=new Array();
	try {
	
		var datas = $.evalJSON(data).data;
		//查询开始年份
		for (var i = 0; i < datas.length; i++) {
			if (datas[i].lineNumber == '0') {
				var start_year= datas[i].start_date.split('-')[0];
				//取年份向前后分别推算两年
				if(start_year){
					year.push(Number(start_year)-2);
					year.push(Number(start_year)-1);
					year.push(Number(start_year));
					year.push(Number(start_year)+1);
					year.push(Number(start_year)+2);
				}
				break;
			}
		}
		var date_to_str = gantt.date.date_to_str(gantt.config.task_date);
		for (var j = 0; j < year.length; j++) {
			for (var i = 0; i < month.length; i++) {
				var start = new Date(year[j], month[i], 01);
				gantt.addMarker({
					start_date : start,
					css : 'status_line_marker',
					text : '  ',
					title : '    '
				});
			}
		}
		

	} catch (e) {
		// TODO: handle exception
	}
}
function reloadRenderGantt() {
	$(".gantt_grid").each(function(i) {
		$(this).height($(this).height() + 16);
	});

	$(".gantt_task").each(function(i) {
		$(this).height($(this).height() + 16);
	});

	$(".gantt_hor_scroll").hide();

}

// 展现加载进度条
function showLoadingDiv() {
	var loadingDiv = $("#loadingDiv");
	var loadingImg = $("#loadingImg");
	loadingDiv.show();
	var imgArray = [ "webpage/com/glaway/ids/gantt/images/ajaxstatus/01.png",
			"webpage/com/glaway/ids/gantt/images/ajaxstatus/02.png", "webpage/com/glaway/ids/gantt/images/ajaxstatus/03.png",
			"webpage/com/glaway/ids/gantt/images/ajaxstatus/04.png", "webpage/com/glaway/ids/gantt/images/ajaxstatus/05.png",
			"webpage/com/glaway/ids/gantt/images/ajaxstatus/06.png", "webpage/com/glaway/ids/gantt/images/ajaxstatus/07.png",
			"webpage/com/glaway/ids/gantt/images/ajaxstatus/08.png" ];
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

function updateCriticalPath(toggle){
	gantt.config.highlight_critical_path=!gantt.config.highlight_critical_path;
	if(gantt.config.highlight_critical_path){
		gantt.config.highlight_critical_path = true;
	}else{
		gantt.config.highlight_critical_path = false;
	}
	gantt.render();
}