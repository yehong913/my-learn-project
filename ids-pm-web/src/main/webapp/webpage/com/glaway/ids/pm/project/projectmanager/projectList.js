/**
 * 校验是否重复：如重复，返回false
 * 
 * @param formData
 * @returns {Boolean}
 */
function vidateRepeat(iframe, formName, url) {
	var result = false;
	var formData = iframe.$('' + formName).serialize();
	//校验通过
	$.ajax({
		type : 'POST',
		url : url,
		async : false,
		cache : false,
		data : formData,
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				result = true;
				return true;
			} else {
				tip(d.msg);
				result = false;
				return false;
			}
		}
	});
	return result;
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

function postBasic(iframe, url, formName) {
	var result = "";
	var formData = iframe.$(formName).serialize();
	$.ajax({
		type : 'POST',
		url : url,
		data : formData,
		async : false,
		cache : false,
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
			    result = d.obj;
			    var win = iframe.$.fn.lhgdialog("getSelectParentWin");
			    //iframe.$.fn.lhgdialog("closeSelect");
				win.reloadProjectList();
		    } 
			try{
				parent.loadTree(null, 1)}catch(e){}; // 刷新左侧最近访问树
			tip(d.msg);
		}
	});
	return result;
}

