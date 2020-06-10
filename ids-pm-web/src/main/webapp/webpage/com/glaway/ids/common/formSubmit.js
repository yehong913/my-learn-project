/*页面提交公共JS*/


/**
 * 通过页面fromId提交
 * @param formId
 */
function formSubmit(formId){
	$('#'+formId).form('submit', {
		onSubmit: function(param){
			var isValid = $(this).form('validate');
			if (!isValid){
				$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
			}
			return isValid;	// 返回false终止表单提交
		},
		success: function(data){
			var json=$.evalJSON(data);
			var rst = ajaxRstAct(json);
			if(json.success){
				cancelAdd();
			}
		}
	});
}

/**
 * 取消新增
 */
function cancelAdd() {
	var win = $.fn.lhgdialog("getSelectParentWin");
	win.reloadTable();
	$.fn.lhgdialog("closeSelect");
}