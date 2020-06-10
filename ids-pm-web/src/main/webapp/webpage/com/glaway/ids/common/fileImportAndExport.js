
/*
	利用window.open()实现post方式的参数传递,将错误信息传递到后台,生成excel下载文件
	dataListAndErrorMap  Object
*/
function downloadErrorReport(url, dataListAndErrorMap) {
    debugger;
	//利用window.open()实现post方式的参数传递
	var form = document.createElement('form');
	form.id = 'formTemp';
	form.method = 'post';
	form.action = url;
	//不打开一个新的窗口,在原有窗口下载文件
	form.target = '_self';

	var input = document.createElement('input');
	input.type = 'hidden';
	input.name = 'dataListAndErrorMap';
	//object --> jsonString
	var objStr = JSON.stringify(dataListAndErrorMap);
	input.value = objStr;
	form.appendChild(input);
	//IE
	if (form.all) {
		form.attachEvent('onsubmit', function() {
			window.open('', '_self');
		});
	} else {//谷歌,firefox...
/*		form.submit(function() {
			window.open('', '_self');
		});*/
		form.addEventListener('onsubmit', function() {
			
			window.open('','_self');
		});
	}
	document.body.appendChild(form);
/*	form.fireEvent('onsubmit');*/
	form.submit();
	document.body.removeChild(form);
	return true;
}



