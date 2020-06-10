function initToolbar() {
	myToolbar = new dhtmlXToolbarObject("toolbarObj");
	myToolbar.setIconsPath("./images/");
	var resultData = postAction("showGanttViewPlanActivityToolbar", {'projectOid':projectOid},true);
	var isProjectManager = resultData.retObject.isProjectManager;
	var isDisplayTopPlanApprove = resultData.retObject.isDisplayTopPlanApprove;
	var isDisplayImportPlanActivity = resultData.retObject.isDisplayImportPlanActivity;
	var isDisplayModifyPlanFinishDate = resultData.retObject.isDisplayModifyPlanFinishDate;
	/*myToolbar.addButton("saveAll", 1, "保存", "save.gif", "save.gif");*/
	if(isDisplayImportPlanActivity)
	{
	   myToolbar.addButton("importPlanActivity", 2, "导入MPP", "plan_import.gif", "plan_import.gif");
	}
	if(isProjectManager)
	{
	   myToolbar.addButton("createPlanActivity", 3, "创建计划", "activity_new.gif", "activity_new.gif");
	}
	myToolbar.addButton("deletePlanActivity", 4, "删除", "delete.gif", "delete.gif");
	myToolbar.addButton("batchTransmitPlanActivity", 5, "下达计划", "designation_edit.gif", "designation_edit.gif");
	if(isDisplayTopPlanApprove)
	{
	   myToolbar.addButton("approveTopPlanActivity", 6, "一级计划批量下达", "topPlanBatchApprove.gif", "topPlanBatchApprove.gif");
	}
	if(isDisplayModifyPlanFinishDate)
	{
	   myToolbar.addButton("showGanttEditView", 7, "调整计划完成时间", "generate_change_actions.gif", "generate_change_actions.gif");
	}
	var scaleOpts = Array(Array('day', 'obj', '天','day.png'), Array('week',
			'obj', '周','week.png'), Array('month', 'obj', '月','month.png'), Array('year', 'obj', '年','year.png'));

	myToolbar.addButtonSelect("scaleDateFormat", 9, "时间刻度", scaleOpts,
			"timerobt.gif", "timerobt.gif");
	
	var viewOpts = Array(Array('standardView','obj','标准','openin_gantt.gif'),Array('defaultView','obj','默认','gantt.gif'));
	myToolbar.addButtonSelect("ganttView", 10, "视图", viewOpts,
			"show.gif", "show.gif");

	myToolbar.attachEvent("onclick", function(id) {
		if (id == "importPlanActivity") {
			openImportPlanActivityWindow(projectOid,'','import2Project');
		} else if (id == "deletePlanActivity") {
			var selectArray = $("input[type='checkbox']:checked");
			if(selectArray.length==0)
			{
			   alert("请先选中要删除的计划！");
			   return;
			}
			var oids = "";
			var isContainerProject = false;
			selectArray.each(function(i) {
				var taskId = $(this).val();
				if(taskId=='' || taskId=='1')
				{
					return true;
				}
				var currentTask = gantt.getTask(taskId);
				if(currentTask.lineNumber==0)
				{
					isContainerProject = true;
				}
				if(i==selectArray.length-1)
				{
					oids += currentTask.oid;
				}
				else
				{
				    oids += currentTask.oid+",";	
				}
			});
			if(isContainerProject)
			{
			   alert('非计划不能删除!');	
			}
			else
			{
			   openDeletePlanActivityWindow(oids);
			}
		} else if (id == "createPlanActivity") {
			
			var selectArray = $("input[type='checkbox']:checked");
			if(selectArray.length==0)
			{
			   alert("请先选择父计划！");
			   return;
			}
			if(selectArray.length>1)
			{
			    alert("只能选择一个父计划！");
			    return;
			}
			
			var taskId = selectArray[0].value;
			var currentTask = gantt.getTask(taskId);
			var lineNumber = currentTask.lineNumber;
			var oid = currentTask.oid;
			if(lineNumber=='0')
			{
				openCreatePlanActivityWindow(projectOid,"","createPlanActivity");
			}
			else
			{
				var resultObj = postAction("validateCreateSubActivityAuthorization", {'oid':oid}, true);
				if(resultObj.success)
				{
				   openCreatePlanActivityWindow(projectOid,oid,'createSubPlanActivity');
				}
				else
				{
				   alert(resultObj.message);	
				}
			}
		} else if (id == "batchTransmitPlanActivity") {
			if (confirm("您确定要批量下达计划吗?")) {
				
				var selectArray = $("input[type='checkbox']:checked");
				
				if(selectArray.length==0)
				{
					alert("请先选中要下达的计划！");
					return;
				}
				
				var oids = "";
				var flag = false;
				selectArray.each(function(i) {
					var taskId = $(this).val();
					if(taskId=='' || taskId=='1')
					{
						return true;
					}
					var currentTask = gantt.getTask(taskId);
					var status = currentTask.status;
					if(status!='拟制中')
					{
					   flag = true;	
					}
					else {
						oids += currentTask.oid + ",";
					}
				});

				if(flag)
				{
				    alert('非拟制中状态的计划不能重复下达！');
				    return;
				}
				
				if (oids.length > 0) {
					$.post("/lrds/app/gantt/batchTransmitPlanActivity", {
						oids : oids
					}, function(result) {
						if (result.success) {
							alert(result.message);
							history.go(0);
						} else {
							alert(result.message);
						}
					}, "json");
				}
			}

		} else if (id == "approveTopPlanActivity") {
			window.open('/lrds/glmpp/planActivity/batchTransmitPlan.jsp?projectOid='+projectOid,'','height=400,width=600,top=200,left=600');
		}else if (id=="showGanttEditView")
		{
			openEditGanttView(projectOid);	
		}
		else if (id == "saveAll") {
			var tasks = gantt._get_tasks_data();
			var links = gantt._get_links_data();

			var newTasks = [];
			var dateToStr = gantt.date.date_to_str("%Y-%m-%d");
			for ( var i = 0; i < tasks.length; i++) {
				var tmpTask = tasks[i];
				var newTask = {};
				newTask.department = tmpTask.department;
				newTask.duration = tmpTask.duration;
				newTask.end_date = dateToStr(tmpTask.end_date);
				newTask.start_date = dateToStr(tmpTask.start_date);
				newTask.id = tmpTask.id;
				newTask.open = tmpTask.open;
				newTask.parent = tmpTask.parent;
				newTask.principal = tmpTask.principal;
				newTask.progress = tmpTask.progress;
				newTask.status = tmpTask.status;
				newTask.text = tmpTask.text;
				newTasks.push(newTask);
			}

			var dataContent = {};
			dataContent.data = newTasks;
			dataContent.links = links;
			alert($.toJSON(dataContent));
			$.post("/lrds/app/gantt/saveTaskGanttInfo", {
				dataContent : $.toJSON(dataContent)
			}, function(result) {
				if ("success" == result) {
					dhtmlx.alert({
						text : "保存成功!"
					});
				} else {
					dhtmlx.alert({
						text : "保存失败!"
					});
				}
			});
		} else if (id == "day") {
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

function getAllTasks(taskArray, id) {
	var task = gantt.getTask(id);
	taskArray.push(task);
	var children = gantt.getChildren(id);
	if (children != null && children.length > 0) {
		for ( var i = 0; i < children.length; i++) {
			var child = gantt.getTask(children[i]);
			getAllTasks(taskArray, child.id);
		}
	}
}

function initGanttConfig() {
	gantt.config.date_scale = "%m-%d";
	//gantt.config.scale_unit = "year";
	
	gantt.config.task_date = "%m-%d";
	gantt.config.xml_date = "%Y-%m-%d %H:%i";
	
	gantt.config.min_column_width = 40;
	gantt.config.scale_height = 50;

	gantt.config.grid_width = 500;
	gantt.config.autosize = false;
	gantt.config.autofit = false;
	gantt.config.work_time = true;
	// gantt.config.sort = true;
	// gantt.config.duration_unit = 'hour';
	// gantt.config.duration_step = 0;
	// gantt.config.work_time = true; 
	// gantt.config.skip_off_time = true;
	// gantt.config.fit_tasks = true;
	// gantt.config.touch = true;
	// gantt.config.initial_scroll = false;
	// gantt.config.readonly=true;
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
				name : "id",
				label : "<input type='checkbox' id='selectAll' name='selection' onclick='handleCheckAll(this)' value=''/>",
				width : 30,
				align : "center",
				template : function(item) {
					return "<input type='checkbox' value='" + item.id
							+ "' name='taskSelection'/>";
				}
			}, {
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
			},{
				name : "end_date",
				label : "结束时间",
				width : 80,
				align : "center",
				hide: true
			}, 
			{
				name : "ganttEndDate",
				label : "结束时间",
				width : 80,
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
				name : "durationDay",
				label : "计划工期",
				width : 60,
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
			}/*,  {
				name : "taskExport",
				label : "任务输出",
				width : 140,
				align : "left"
			}*/];
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

	/*
	 * gantt.templates.progress_text = function(start, end, task) { return "<span
	 * style='text-align:left;'>"+Math.round(task.progress*100)+ "% </span>"; };
	 */

	gantt.templates.tooltip_text = function(start, end, event) {
		return "<b>计划名称:</b> " + event.text + "<br/><b>开始时间:</b> "
				+ gantt.templates.tooltip_date_format(start)
				+ "<br/><b>结束时间:</b> "
				+ event.ganttEndDate
				+ " <br/><b>计划工期:</b> " + event.durationDay
				+ " <br/><b>任务输出:</b> " + event.taskExport
				+ "<br/><b>技术负责人:</b> " + event.principal
				+ "<br/><b>责任部门:</b> " + event.department
				+ "<br/><b>部门计划员:</b> " + event.planner + "<br/><b>计划状态:</b> "
				+ event.status + "<br/><b>计划级别:</b> " + event.level
				+ "<br/><b>计划类型:</b> " + event.planBigCatalog
				+ "<br/><b>完成进度:</b> " + Math.round(event.progress) + "%";
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
	menu.loadXML("dhtmlxgantt/dhtmlxMenu/planActivityContextMenu.xml?e="
			+ new Date().getTime());
	
	gantt.attachEvent("onContextMenu", function(taskId, linkId, event) {
		
		var x = event.clientX + document.body.scrollLeft
				+ document.documentElement.scrollLeft, y = event.clientY
				+ document.body.scrollTop + document.documentElement.scrollTop;
		
		if (taskId) {
			
			 //默认隐藏所有的右键菜单
			 menu.forEachItem(function(id){
			        menu.hideItem(id);
			 });
			
			var currentTask = gantt.getTask(taskId);
			var oid = currentTask.oid;
			
			//缓存菜单权限数据
			if(menuMap.containsKey(oid))
			{
				var menuArray = menuMap.get(oid);
				for(var i=0;i<menuArray.length;i++)
				{  
					var menuId = menuArray[i].name;
					menu.showItem(menuId);
				}
				if(menuArray.length>0)
				{
				   menu.showContextMenu(x, y);
				}
				else
				{
				   menu.hideContextMenu();	
				}
			}
			else
			{
				var menuArray = postRequestMethod("/lrds/app/gantt/showPlanActivityMenu",{'oid':oid});
				for(var i=0;i<menuArray.length;i++)
				{  
				   var menuId = menuArray[i].name;
				   menu.showItem(menuId);
				}
				menuMap.put(oid,menuArray);
				if(menuArray.length>0)
				{
				   menu.showContextMenu(x, y);
				}
				else
				{
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
		if (id == "importPlanActivityFromWBSTemplate") {
			window.open("/lrds/glmpp/planActivity/importPlanActivityFromWBSTemplate.jsp?projectOid="+projectOid+"&planActivityOid="+oid,
					'importPlanActivityFromWBSTemplate',
					'height=400,width=830,top=300,left=300,toolbar=no,menubar=no,scrollbars=yes,location=no,status=no');
		} else if (id == "importPlanActivity") {
			openImportPlanActivityWindow(projectOid,oid,'import2PlanActivity');
		} else if (id == "transmitPlanActivity") {
			if (confirm("您确定要下达计划吗?")) {
				$.post("/lrds/app/gantt/transmitPlanActivity", {
					oid : oid
				}, function(result) {
					if (result.success) {
						alert(result.message);
						history.go(0);
					} else {
						alert(result.message);
					}
				}, "json");
			}
		}else if (id == "deletePlanActivity") {
				openDeletePlanActivityWindow(oid);
		}else if(id=="createSubPlanActivity"){
				openCreatePlanActivityWindow(projectOid,oid,'createSubPlanActivity');
		}else if(id=="insertSubPlanActivity"){
				openCreatePlanActivityWindow(projectOid,oid,'insertSubPlanActivity');
		}else if(id=="copyAndInsertPlanActivity"){
				openCreatePlanActivityWindow(projectOid,oid,'copyAndInsertPlanActivity');
		}else if(id=="applyChangePlanActivityFinishDate"){
			openApplyChangePlanActivityFinishDateWindow(projectOid,oid);
		}else if(id=="changePlanActivityFinishDate"){
			openChangePlanActivityFinishDateWindow(projectOid,oid);
		}else if(id=="editPlanActivityStartDate"){
			openEditPlanActivityStartDateWindow(projectOid,oid);
		}else if(id=="editPlan"){
			openEditPlanWindow(oid);
		}else if(id=="editPlanActivity"){
			openEditPlanActivityWindow(projectOid,oid);
		}else if(id=="relateDPBOM"){
			relateDPBOM(oid);
		}else if(id=="relateParentDeliverable"){
			relateParentDeliverable(oid);
		}else if(id=="updateDeliverableName"){
			updateDeliverableName(oid);
		}else if(id=="deleteDeliverable"){
			deleteDeliverable(oid);
		}else if(id=="getUnDecomposedDeliverables"){
			getUnDecomposedDeliverables(oid);
		}
	});

}

var selectTaskId = null;

function initGanttEvent() {

	function limitMoveLeft(task, limit) {
		var dur = task.end_date - task.start_date;
		task.end_date = new Date(limit.end_date);
		task.start_date = new Date(+task.end_date - dur);
	}

	function limitMoveRight(task, limit) {
		var dur = task.end_date - task.start_date;
		task.start_date = new Date(limit.start_date);
		task.end_date = new Date(+task.start_date + dur);
	}

	function limitResizeLeft(task, limit) {
		task.end_date = new Date(limit.end_date);
	}

	function limitResizeRight(task, limit) {
		task.start_date = new Date(limit.start_date);
	}

	// 移动任务时触发，保证在子任务移动时不超过父任务的最小时间和最大时间
	gantt
			.attachEvent(
					"onTaskDrag",
					function(id, mode, task, original, e) {
						var parent = task.parent ? gantt.getTask(task.parent)
								: null, children = gantt.getChildren(id), modes = gantt.config.drag_mode;

						var limitLeft = null, limitRight = null;

						if (!(mode == modes.move || mode == modes.resize))
							return;

						if (mode == modes.move) {
							limitLeft = limitMoveLeft;
							limitRight = limitMoveRight;
						} else if (mode == modes.resize) {
							limitLeft = limitResizeLeft;
							limitRight = limitResizeRight;
						}

						// check parents constraints
						if (parent && +parent.end_date < +task.end_date) {
							limitLeft(task, parent);
						}

						if (parent && +parent.start_date > +task.start_date) {
							limitRight(task, parent);
						}

						// check children constraints
						for ( var i = 0; i < children.length; i++) {
							var child = gantt.getTask(children[i]);
							if (+task.end_date < +child.end_date) {
								limitLeft(task, child);
							} else if (+task.start_date > +child.start_date) {
								limitRight(task, child);
							}
						}

					});

	// 任务被移动后触发的事件
	gantt.attachEvent("onAfterTaskDrag", function(id, mode) {
		var task = gantt.getTask(id);
		if (mode == gantt.config.drag_mode.progress) {
			var pr = Math.floor(task.progress * 100 * 10) / 10;
			dhtmlx.message(task.text + " is now " + pr + "% completed!");
		} else {
			var convert = gantt.date.date_to_str("%Y-%m-%d");
			var s = convert(task.start_date);
			var e = convert(task.end_date);
			dhtmlx.message(task.text + " 开始于 " + s + " 结束于 " + e);
		}
	});

	// 当任务的进度被修改时触发的事件
	gantt.attachEvent("onBeforeTaskChanged", function(id, mode, old_event) {
		var task = gantt.getTask(id);
		if (mode == gantt.config.drag_mode.progress) {
			if (task.progress < old_event.progress) {
				dhtmlx.message(task.text + " progress can't be undone!");
				return false;
			}
		}
		return true;
	});

	// 当任务要被移动前触发的事件
	gantt.attachEvent("onBeforeTaskDrag", function(id, mode) {
		var task = gantt.getTask(id);
		var message = task.text + " ";

		if (mode == gantt.config.drag_mode.progress) {
			message += "工程进度被修改";
		} else {
			message += "将要被 ";
			if (mode == gantt.config.drag_mode.move)
				message += "移动";
			else if (mode == gantt.config.drag_mode.resize)
				message += "扩展";
		}

		dhtmlx.message(message);
		return true;
	});

	// 当任务的链接被双击时触发的事件
	gantt
			.attachEvent(
					"onLinkClick",
					function(id) {
						var link = this.getLink(id), src = this
								.getTask(link.source), trg = this
								.getTask(link.target), types = this.config.links;

						var first = "", second = "";
						switch (link.type) {
						case types.finish_to_start:
							first = "finish";
							second = "start";
							break;
						case types.start_to_start:
							first = "start";
							second = "start";
							break;
						case types.finish_to_finish:
							first = "finish";
							second = "finish";
							break;
						}
					});

	// 修改计划后保存时时候触发
	gantt.attachEvent("onLightboxSave", function(id, task) {
		alert(id + " " + task.text);
		return true;
	});

	// 删除计划时触发
	gantt.attachEvent("onLightboxDelete", function(id) {
		alert("delete id is " + id);
		gantt.$click.buttons["delete"](id);
		return true;
	});
	
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

	gantt.attachEvent("onBeforeAdd", function(id, item) {

	});

}

function customModifyLightBox() {
	gantt.locale.labels["section_id"] = "任务ID";
	gantt.locale.labels["section_text"] = "任务名称";
	gantt.locale.labels["section_start_date"] = "开始时间";
	gantt.locale.labels["section_end_date"] = "结束时间";
	gantt.locale.labels["section_parent"] = "父任务";
	gantt.locale.labels["section_principal"] = "责任人";
	gantt.locale.labels["section_department"] = "部门";
	gantt.locale.labels["section_status"] = "任务状态";
	gantt.config.lightbox.sections = [ {
		name : "text",
		height : 22,
		map_to : "text",
		type : "textarea",
		focus : true
	}, {
		name : "start_date",
		height : 72,
		type : "duration",
		map_to : "start_date",
		time_format : [ "%Y", "%m", "%d" ]
	}, {
		name : "end_date",
		height : 72,
		type : "duration",
		map_to : "end_date",
		single_date : true,
		time_format : [ "%Y", "%m", "%d" ]
	}, {
		name : "parent",
		type : "parent",
		allow_root : "true",
		filter : function(id, task) {
			return true;
		}
	}, {
		name : "principal",
		height : 22,
		map_to : "principal",
		type : "textarea"
	}, {
		name : "department",
		height : 22,
		map_to : "department",
		type : "textarea"
	}, {
		name : "status",
		height : 22,
		map_to : "status",
		type : "select",
		options : [ {
			key : "start",
			label : "未开始"
		}, {
			key : "finish",
			label : "已完成"
		} ]
	} ];

}

function modSampleHeight()
{
   var headHeight = 100;
   var sch = document.getElementById("gantt_here");
   sch.style.height = (parseInt(document.body.offsetHeight)-headHeight)+"px";
   gantt.setSizes();
}

$(function() {
	
	// 显示加载进度条
	var executeId = showLoadingDiv();
	var src = window.location.href;
	var oid = src.substr(src.indexOf("?oid=") + 5);
	projectOid = oid;
	$.post("/lrds/app/gantt/getTaskGanttInfo?oid=" + oid, function(data) {

		// 隐藏加载进度条
		hideLoadingDiv(executeId);
		initGanttConfig();
		initToolbar();
		initGanttColumnContent();
		initGanttScales();
		initGanttTemplate();
		initGanttContextMenu();
		initGanttEvent();
		setScaleConfig('0');
		initAddMarker(data)
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

// 打开创建计划的页面
function openCreatePlanActivityWindow(projectOid,planActivityOid,actionName) {
	window.open(
					"/lrds/glmpp/planActivity/createActivityWindow.jsp?projectOid="
							+ projectOid+"&planActivityOid="+planActivityOid+"&actionName="+actionName,
					'createPlanActivityWindow',
					'width=800,height=585,top=150,left=600,toolbar=no,menubar=no,scrollbars=yes,location=no,status=no');
}

// 全选和反选
function handleCheckAll(obj) {
	if (obj.checked) {
		$(":checkbox").each(function(i) {
			$(this).attr("checked", "true");
		});
	} else {
		$(":checkbox").each(function(i) {
			$(this).removeAttr("checked");
		});
	}
}

function openDeletePlanActivityWindow(oids) {
	window
			.open(
					"/lrds/glmpp/planActivity/deletePlanActivity.jsp?oids="
							+ oids,
					'deletePlanActivityWindow',
					'height=400,width=600,top=300,left=300,toolbar=no,menubar=no,scrollbars=yes,location=no,status=no');
}

function openImportPlanActivityWindow(projectOid,planActivityOid,actionName) {
	window
			.open(
					"/lrds/glmpp/planActivity/importPlanActivity.jsp?projectOid="
							+ projectOid+"&planActivityOid="+planActivityOid+"&actionName="+actionName,
					'importPlanActivity',
					'height=255,width=520,top=300,left=300,toolbar=no,menubar=no,scrollbars=yes,location=no,status=no');
}

function openApplyChangePlanActivityFinishDateWindow(projectOid,planActivityOid) {
	window
			.open(
					"/lrds/glmpp/planActivity/applyChangePlanActivityFinishDate.jsp?projectOid="+projectOid+"&planActivityOid="+planActivityOid,
					'openApplyPlanChangeWindow',
					'height=360,width=600,top=300,left=300,toolbar=no,menubar=no,scrollbars=yes,location=no,status=no');
}

function openChangePlanActivityFinishDateWindow(projectOid,planActivityOid) {
	window
			.open(
					"/lrds/glmpp/planActivity/changePlanActivityFinishDate.jsp?projectOid="+projectOid+"&planActivityOid="+planActivityOid,
					'openApplyPlanChangeWindow',
					'height=260,width=450,top=300,left=600,toolbar=no,menubar=no,scrollbars=yes,location=no,status=no');
}

function openEditPlanActivityStartDateWindow(projectOid,planActivityOid) {
	window
			.open(
					"/lrds/glmpp/planActivity/editPlanActivityStartDate.jsp?projectOid="+projectOid+"&planActivityOid="+planActivityOid,
					'editPlanActivityStartDate',
					'height=320,width=450,top=300,left=600,toolbar=no,menubar=no,scrollbars=yes,location=no,status=no');
}

function openEditGanttView(oid) {
  window.open(
					"ffps-edit.html?oid=" + oid,
					'editGanttView',
					'height=900,width=1440,top=150,left=200,toolbar=no,menubar=no,scrollbars=yes,location=no,status=no'); 
}

function openEditPlanWindow(oid) {
	  window.open(
						"/lrds/glmpp/planActivity/editPlan.jsp?oid="+oid,
						'editPlan',
						'height=400,width=600,top=300,left=600,toolbar=no,menubar=no,scrollbars=yes,location=no,status=no'); 
}

function openEditPlanActivityWindow(projectOid,planActivityOid) {
	window.open(
					"/lrds/glmpp/planActivity/editActivityWindow.jsp?projectOid="
							+ projectOid+"&planActivityOid="+planActivityOid,
					'editPlanActivity',
					'width=800,height=585,top=150,left=600,toolbar=no,menubar=no,scrollbars=yes,location=no,status=no');
}

function relateDPBOM(planActivityOid)
{	
	window.open('/lrds/glmpp/planActivity/relateDPBOM.jsp?planactivity_oid='+planActivityOid,'','height=530,width=800,top=200,left=600');
}

function relateParentDeliverable(planActivityOid)
{	
	window.open('/lrds/glmpp/planActivity/relateParentDeliverable.jsp?planOid='+planActivityOid,'','height=380,width=600,top=200,left=600');
}

function updateDeliverableName(planActivityOid)
{
	
	var param = {'planOid':planActivityOid};
	
	var data = postAction('updateDeliverableName',param,true);
	
	if(data.success)
	{
		alert('更新成功'); 
	}else
	{
		alert(data.message);    
	}
	
}

function deleteDeliverable(planActivityOid)
{	
	window.open('/lrds/glmpp/planActivity/deleteDeliverable.jsp?planOid='+planActivityOid,'','height=380,width=600,top=200,left=600');
}

function getUnDecomposedDeliverables(planActivityOid)
{	
	window.open('/lrds/glmpp/planActivity/viewDecomposedDeliverables.jsp?planOid='+planActivityOid,'','height=380,width=600,top=200,left=600');
}
