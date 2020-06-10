//弹出只有关闭按钮的窗口
function createdetailwindow_close(title, url, width, height) { 
	width = width ? width : 800;
	height = height ? height : 400;	
	if (width == "100%" || height == "100%") {
		width = document.body.offsetWidth;
		height = document.body.offsetHeight - 100;
	}

	$.fn.lhgdialog({
		title : title,
		content : 'url:' + url,
		width : width + 'px',
		height : height + 'px',
		cache : false,
		button : [
		          {name:'关闭',focus:true}
				]
	});
}

//弹出窗口 - 尺寸、标题等信息在fd:dialog中已经描述
function createDialog(id, url) {
	$("#" + id).lhgdialog("open", "url:" + url);
}

//弹出给定尺寸的窗口
function createDialogBySize(id, title, url, width, heigth) {
 	$("#" + id).lhgdialog({
 		title : title,
 		content : "url:" + url,
 		width : width + 'px',
		heigth : heigth + 'px'
	});
}
