function dateFormatter(val, row, value) {
		return dateFmtYYYYMMDD(val);
}

//人员id、realName转换
function userFormatter(infoObj) {
	var id = infoObj.id;
	if (id != undefined && id != null && id != '') {
		var realName = infoObj.realName;
		var userName = infoObj.userName;
		if (realName != undefined && realName != null && realName != ''&&
			userName != undefined && userName != null && userName != '') {
			return realName + "-" + userName;
		} else {
			return infoObj.id;
		}
	}
}

function viewProjectDoc(val, row, value) {
	var path = "<a  href='#' title='查看' style='color:blue' onclick='openProjectDoc(\""+row.id+"\",\""+row.docName+"\")'>"+row.docName+"</a>"
	return path;
}

function viewProjectDocForTask(val, row, value) {
	return "<a  href='#' title='查看' style='color:blue' onclick='openProjectDocForTask(\""+row.id+"\",\""+row.docName+"\")'>"+row.docName+"</a>";
}

function formatSecurity(val, row, value) {
	if (val != undefined && val != null && val != '') {
		var securityLevel = eval($('#securityLevel').val());
		for (var i = 0; i < securityLevel.length; i++) {
			if (val == securityLevel[i].code) {
				return securityLevel[i].name;
			}
		}
	}

}


//文档状态
function formatStatus(val, row, value) {
	debugger;
	var status;
	var bizCurrent = eval($('#lifeCycleList').val());
    for(var i=0;i<bizCurrent.length;i++){
			if(val == bizCurrent[i].name){
				status = bizCurrent[i].title;
				var resultUrl = status;
				var newVersion = row.version.split(".")[0];
				if(!(row.status == "nizhi")){
					resultUrl = '<a href="#" onclick="openProjDocFlow(\''
						+ row.id + '\')"><font color=blue><div style="text-align:left">' +status+ '</div></font></a>'
				}else if(row.operStatus=='reject'||row.operStatus=='big'){
					resultUrl = '<a href="#" onclick="openProjDocFlow(\''
						+ row.id + '\')"><font color=blue><div style="text-align:left">' +status+ '</div></font></a>'
				}else if(newVersion!="A"){
					resultUrl = '<a href="#" onclick="openProjDocFlow(\''
						+ row.id + '\')"><font color=blue><div style="text-align:left">' +status+ '</div></font></a>'
				}
				
				return resultUrl;
			}
		}
    return status;
}

function openProjDocFlow(taskNumber) {
	var tabTitle = '审批流程查看';
	var url = '${webRoot}/taskFlowCommonController.do?taskFlowList&taskNumber='+taskNumber;
	createdetailwindow_close(tabTitle, url, 800, 400);
}


function openProjectDoc(id,name){
	var url="projLibController.do?viewProjectDocDetail&id="+id;
	createdetailwindow("文档详情", url, "870", "680")
}

function openProjectDocForTask(id,name){
	var url="${pageContext.request.contextPath }/projLibController.do?viewProjectDocDetail&id="+id;
	createdetailwindow("文档详情", url, "870", "680")
}






function doExcute(url,tipMsg,datas){
	Alert.confirm(tipMsg, function(r) {
		   if (r) {
				$.ajax({
					url :url,
					type : 'post',
					data : {
						'datas' : $.toJSON(datas)
					},
					cache : false,
					async : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reload();   
							
						}
					}
				});
			}
		});
}

function reload() {
	$(''+projLibGrid).datagrid('reload');
	$(''+projLibGrid).datagrid('uncheckAll');
	$(''+projLibGrid).datagrid('clearSelections');
	 
}



function createaddwindow(title, url, width, height) {
	width = width ? width : 750;
	height = height ? height : 400;
	if (width == "100%" || height == "100%") {
		width = document.body.offsetWidth;
		height = document.body.offsetHeight - 100;
	}
	if (typeof (windowapi) == 'undefined') {
		
		$.fn.lhgdialog({
			content : 'url:' + url,
			lock : true,
			width : width,
			height : height,
			title : title,
			opacity : 0.3,
			cancelVal : '取消',
			okVal : '确定',
			cancel : true, /*为true等价于function(){}*/
			ok : function() {
				var result=false;
				iframe=this.iframe
				if(title=='修改'||title=='修订'){
					result=saveUpdatDoc(iframe);
					reload();
					return result;

				} else {
					result=saveDoc();
					reload();
					return result;
				}
			   
			}
		});
		 
	}else{
		$.fn.lhgdialog({
			content : 'url:' + url,
			lock : true,
			width : width,
			height : height,
			parent : windowapi,
			title : title,
			opacity : 0.3,
			cache : false,
			cancelVal : '取消',
			okVal : '确定',
			cancel : true, /*为true等价于function(){}*/
			ok : function() {
				var result=false;
				iframe=this.iframe
				if(title=='修改'||title=='修订'){
					result=saveUpdatDoc(iframe);
					reload();
					return result;

				} else {
					result=saveDoc();
					reload();
					return result;
				}
			}
		});
	}

}


function saveUpdatDoc(iframe) {
	if (vidateSaveDoc(iframe,'#projLibDocUpdateForm')) {
		var result=iframe.updateFileBasicAndUpload();
		if(result){
			return true;
		}
	} 
	 
	return false;
}

function saveDoc(iframe) {
	   if(vidateSaveDoc(iframe,'#projLibDocAddForm') ){
		  var result= iframe.saveFileBasicAndUpload();
		  if(result){
			return true;
		 }
	   }
		
		return false;
	 
	 
}



function vidateSaveDoc(iframe,fromName) {
	if (!vidateRemark(iframe.$('#remark').val(), 200)) {
		return false;
	}
	if (!iframe.$(fromName).form('validate')) {
		return false;
	}
	return true;
}

/**
 * 校验IE下remark长度<200
 * 
 * @param formData
 * @returns {Boolean}
 */
function vidateRemark(remark, msg) {
	if (remark != null && remark != '' && remark != 'undefined') {
		if (remark.length > 200) {
			tip(msg);
			return false;
		}
	}
	return true;
}


