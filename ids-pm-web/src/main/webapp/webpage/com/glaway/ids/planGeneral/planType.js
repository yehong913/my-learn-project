	function taskDescription(val,row,value){
		//return '<a href="javascript:void(0)"  class="tips_taskDetailBasicOperation"  title="'+row.remark+'">' + row.logInfo + '</a>';
		var str='<a href="javascript:void(0)" style="color:black" class="tips_resourceList"  title="'+row.remark+'" >' + row.logInfo + '</a>'
		return str ;
	}
	
	function downLoadFile(val, row, value){
		if(row.filePath!=""){
			return '<a href="planLogController.do?downFile&id='+ row.id +'">' +
				   '<span class="basis ui-icon-attachment" title="下载">&nbsp;&nbsp;&nbsp;&nbsp;</span>' + 
				   '</a>';
		}
	}