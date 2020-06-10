
	
	// 生命周期状态name、title转换
	function viewPlanBizCurrent(val, row, value) {
		var statusList = eval($('#statusList').val());
		for (var i = 0; i < statusList.length; i++) {
			if (row.bizCurrent == statusList[i].name) {
				return statusList[i].title;
			}
		}
	}
	
	// 项目名称-项目编号
	function viewProjectName(val, row, value) {
		return row.project.name+"-"+row.project.projectNumber;
	}
	
	// 进度
	function viewTaskProgressRate(val, row, value){
		var val = row.progressRate;
		var warningDay = $("#warningDay").val();
		if(warningDay != '0'){
			var warningDayFlag = $("#warningDayFlag").val();
			var currentDate = $("#currentDate").val();
			var planEndTime = row.planEndTime;
			if(planEndTime != undefined && planEndTime != null && planEndTime != ''){
				var warningDate = dateChange(getDateByFmtStr(planEndTime), warningDay, warningDayFlag);
				var nowDate = getDateByFmtStr(currentDate);
				if((warningDate.getTime() <= nowDate.getTime()) && row.bizCurrent != 'FINISH'){
					if(val == undefined || val == null || val == ''){
						val = '<div style="width:100%;border:1px solid #ff0000;">'
							+'<div style="width:0.00%;background:#00ff00;color:#000000">0.00%</div>'
							+'</div>';
					}
					else{
						val = '<div style="width:100%;border:1px solid #ccc;">' +
				        '<div style="width:' + val + '%;background:#ff0000;color:#000000">' + val + '%' + '</div>'
				        '</div>';
					}
				}
				else{
					if(val == undefined || val == null || val == ''){
						val = '<div style="width:100%;border:1px solid #ccc">'
							+'<div style="width:0.00%;background:#00ff00;color:#000000">0.00%</div>'
							+'</div>';
					}
					else{
						val = '<div style="width:100%;border:1px solid #ccc">' +
				        '<div style="width:' + val + '%;background:#00ff00;color:#000000">' + val + '%' + '</div>'
				        '</div>';
					}
				}
			}
			else{
				if(val == undefined || val == null || val == ''){
					val = '<div style="width:100%;border:1px solid #ccc">'
						+'<div style="width:0.00%;background:#00ff00;color:#000000">0.00%</div>'
						+'</div>';
				}
				else{
					val = '<div style="width:100%;border:1px solid #ccc">' +
			        '<div style="width:' + val + '%;background:#00ff00;color:#000000">' + val + '%' + '</div>'
			        '</div>';
				}
			}
		}
		return val;
	}