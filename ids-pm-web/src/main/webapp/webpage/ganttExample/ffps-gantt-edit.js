function initToolbar() {
	myToolbar = new dhtmlXToolbarObject("toolbarObj");
	myToolbar.setIconsPath("./images/");
	var scaleOpts = Array(Array('day', 'obj', '天','day.png'), Array('week',
			'obj', '周','week.png'), Array('month', 'obj', '月','month.png'), Array('year', 'obj', '年','year.png'));

	myToolbar.addButtonSelect("scaleDateFormat", 1, "时间刻度", scaleOpts,
			"timerobt.gif", "timerobt.gif");
	
	var viewOpts = Array(Array('standardView','obj','标准','openin_gantt.gif'),Array('defaultView','obj','默认','gantt.gif'));
	myToolbar.addButtonSelect("ganttView", 9, "视图", viewOpts,
			"show.gif", "show.gif");
	
	var resultData = postAction("showGanttEditViewPlanActivityToolbar", {'projectOid':projectOid},true);
	if(resultData.retObject.isProjectManager)
	{
	   myToolbar.addButton("applyModifyPlanActivityFinishDate", 2, "调整后计划生效", "action_items_import.gif", "action_items_import.gif");
	}
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
		} else if (id == 'defaultView'){
			gantt.config.grid_width = 500;
			gantt.render();
			reloadRenderGantt();
		}
	});

}

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
		gantt.config.scale_height =50;
		gantt.templates.date_scale = null;
		break;
	case "2":
		var weekScaleTemplate = function(date){
		    var dateToStr = gantt.date.date_to_str("%m-%d");
		    var endDate = gantt.date.add(gantt.date.add(date, 1, "week"), -1, "day");
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
			template:weekScaleTemplate
		} ];
		
		gantt.config.scale_height =50;
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
		gantt.config.scale_height =50;
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
	//gantt.config.show_progress = false;
	//gantt.config.branch_loading = true;
}		

function initGanttColumnContent() {
	gantt.config.columns = [
			 {
				name : "lineNumber",
				label : "ID",
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
				hide: true
			}, {
				name : "ganttEndDate",
				label : "结束时间",
				width : 80,
				align : "center"
			}, {
				name : "endTime",
				label : "调整前时间",
				width : 80,
				align : "center"
			}, {
				name : "delayDay",
				label : "调整后时间偏差",
				width : 120,
				align : "center"
			}, {
				name : "planBigCatalog",
				label : "计划类型",
				width : 80,
				align : "center"
			}, {
				name : "status",
				label : "计划状态",
				width : 80,
				align : "center"
			}, {
				name : "principal",
				label : "技术负责人",
				width : 120,
				align : "center"
			}, {
				name : "department",
				label : "责任部门",
				width : 80,
				align : "center"
			}, {
				name : "planner",
				label : "部门计划员",
				width : 120,
				align : "center"
			}];
}

function initGanttScales() {

	gantt.config.subscales = [
	{
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

function initGanttTemplate() {

	gantt.templates.task_class = function(st, end, item) {
		return gantt.getChildren(item.id).length ? "gantt_project" : "";
	};

	gantt.templates.link_class = function(link) {
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
	};


	gantt.templates.tooltip_text = function(start, end, event) {
		return "<b>计划名称:</b> " + event.text + "<br/><b>开始时间:</b> "
				+ gantt.templates.tooltip_date_format(start)
				+ "<br/><b>结束时间:</b> "
				+ event.ganttEndDate
				+ " <br/><b>调整前时间:</b> " + event.endTime
				+ " <br/><b>调整后时间偏差:</b> " + event.delayDay
				+ "<br/><b>技术负责人:</b> " + event.principal
				+ "<br/><b>责任部门:</b> " + event.department
				+ "<br/><b>部门计划员:</b> " + event.planner 
				+ "<br/><b>计划类型:</b> " + event.planBigCatalog
				+ "<br/><b>计划级别:</b> " + event.level
		        + "<br/><b>计划状态:</b> " + event.status ;
				
	};
	
	gantt.templates.grid_row_class = function(start_date, end_date, task) {
	    if(task.delayDay != "" && task.executeStatus=='changeFinishTime') 
	    {
	    	return "red";
	    }
	    else if(task.delayDay != "" && task.executeStatus=='modifyFinishTime')
	    {
	       return "green";	
	    }
	};
	
	
	gantt.templates.task_row_class = function(start_date, end_date, task) {
		if(task.delayDay != "" && task.executeStatus=='changeFinishTime') 
	    {
	    	return "red";
	    }
	    else if(task.delayDay != "" && task.executeStatus=='modifyFinishTime')
	    {
	       return "green";	
	    }
	};
	
	gantt.templates.grid_folder=function(item){
		if(item.lineNumber==0)
		{
		   return "<div class='gantt_tree_icon tree_root'></div>";	
		}
		else
		{
		   return "<div class='gantt_tree_icon gantt_folder_"+(item.$open?"open":"closed")+"'></div>";	
		}
	};
	
	gantt.templates.grid_file=function(item){
		if(item.lineNumber==0)
		{
		   return "<div class='gantt_tree_icon tree_root'></div>";	
		}
		else
		{
		   return "<div class='gantt_tree_icon gantt_file'></div>";	
		}
	};
	
}

var selectTaskLinkObject = {};
selectTaskLinkObject.selectId = null;
selectTaskLinkObject.selectType = null;

var menuMap = new HashMap();

var projectOid = null;

function initGanttContextMenu() {
	
	var menu = new dhtmlXMenuObject();
	menu.setIconsPath("images/");
	menu.renderAsContextMenu();
	menu.setSkin("dhx_terrace");
	menu.loadXML("dhtmlxgantt/dhtmlxMenu/planActivityEditContextMenu.xml?e="
			+ new Date().getTime());
	
	gantt.attachEvent("onContextMenu", function(taskId, linkId, event) {
		
		var x = event.clientX + document.body.scrollLeft
				+ document.documentElement.scrollLeft, y = event.clientY
				+ document.body.scrollTop + document.documentElement.scrollTop;
		
		if (taskId) {
			
			var currentTask = gantt.getTask(taskId);
			var oid = currentTask.oid;
			
			//缓存菜单权限数据
			if(menuMap.containsKey(oid) && menuMap.get(oid))
			{
				   menu.showContextMenu(x, y);
			}
			else
			{
				var result = postAction("isAllowModifyPlanActivityFinishDate",{'oid':oid},true);
				if(result.retObject)
				{
				   menuMap.put(oid,true);
				   menu.showContextMenu(x, y);
				}
				else
				{
				   menuMap.put(oid,false);
				   menu.hideContextMenu();
				}
			}
			
			selectTaskLinkObject.selectId = taskId;
			selectTaskLinkObject.selectType = "task";
		} else if (linkId) {
			menu.showContextMenu(x, y);
			selectTaskLinkObject.selectId = linkId;
			selectTaskLinkObject.selectType = "link";
		}
		
		if (taskId || linkId) {
			return false;
		}
		
		return true;
	});

	menu.attachEvent("onclick", function(id) {
		var currentTask = gantt.getTask(selectTaskLinkObject.selectId);
		var oid = currentTask.oid;
		if (id == "modifyPlanActivityFinishDate") {
			window.open("/lrds/glmpp/planActivity/modifyPlanActivityFinishDate.jsp?projectOid="+projectOid+"&planActivityOid="+oid,
					'modifyPlanActivityFinishDate',
					'height=320,width=450,top=300,left=600,toolbar=no,menubar=no,scrollbars=yes,location=no,status=no');
		}
	});

}

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
	// 显示加载进度条
	var executeId = showLoadingDiv();
	var src = window.location.href;
	var oid = src.substr(src.indexOf("?oid=") + 5);
	projectOid = oid;
	$.post("/lrds/app/gantt/getEditTaskGanttInfo?oid=" + oid, function(data) {

		// 隐藏加载进度条
		hideLoadingDiv(executeId);
		initToolbar();
		initGanttConfig();
		initGanttColumnContent();
		initGanttScales();
		initGanttTemplate();
		initGanttContextMenu();
		initGanttEvent();
		setScaleConfig('0');

		gantt.init("gantt_here");
		gantt.parse(data);

		reloadRenderGantt();
	});
});

function reloadRenderGantt()
{
	$(".gantt_grid").each(function(i) {
		$(this).height($(this).height() + 16);
	});

	$(".gantt_task").each(function(i) {
		$(this).height($(this).height() + 16);
	});

	$(".gantt_task_content").each(function(i){
		 $(this).empty();
	});
	
	$(".gantt_hor_scroll").hide();
}

// 展现加载进度条
function showLoadingDiv() {
	var loadingDiv = $("#loadingDiv");
	var loadingImg = $("#loadingImg");
	loadingDiv.show();
	var imgArray = [ "../images/ajaxstatus/01.png",
			"../images/ajaxstatus/02.png", "../images/ajaxstatus/03.png",
			"../images/ajaxstatus/04.png", "../images/ajaxstatus/05.png",
			"../images/ajaxstatus/06.png", "../images/ajaxstatus/07.png",
			"../images/ajaxstatus/08.png" ];
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