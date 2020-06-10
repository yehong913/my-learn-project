/**
 * 新增TAB页签
 * 
 * @param subtitle
 * @param url
 * @param icon
 */
function addPortletTab(subtitle, url, icon) {
	top.addTab(subtitle, url, icon);
}

/**
 * 新增或刷新TAB页签
 * 
 * @param subtitle
 * @param url
 * @param icon
 */
function addTabOrRefresh(subtitle, url, icon) {
	if (top.getTabId().tabs('exists', subtitle)) {
		top.getTabId().tabs('select', subtitle);
		var currTab = top.getTabId().tabs('getSelected');
		top.progress("open");
		if (url != undefined && url.indexOf('isIframe') != -1) {
			top.getTabId().tabs('update', {
				tab : currTab,
				options : {
					content : createFrame(url)
				}
			});
		} else {
			currTab.panel('refresh', url);
		}
	}else{
		top.addTab(subtitle, url, icon);
	}
	
	window.setTimeout(function() {
		top.progress("close");
	}, 200);
}

/*******************************************************************************
 * 通过id添加标题 begin
 ******************************************************************************/
/**
 * 根据id新增或刷新TAB页签
 * 
 * @param subtitle
 * @param url
 * @param icon
 */
function addTabByIdOrRefresh(subtitle, url, icon, id) {
	if (id == undefined || id == null || id == '') {
		addTabOrRefresh(subtitle, url, icon);
	} 
	else {
		if (top.isTabExist(subtitle, id)) {
			var currTab = top.getTabById(id);
			top.progress("open");
			if (url != undefined && url.indexOf('isIframe') != -1) {
				top.getTabId().tabs('update', {
					tab : currTab,
					options : {
						content : createFrame(url)
					}
				});
			} else {
				currTab.panel('refresh', url);
			}
		} else {
			top.addTabById(subtitle, url, icon, id);
		}
		
		window.setTimeout(function() {
			top.progress("close");
		}, 200);
	}
}

function addTabById(subtitle, url, icon, id) {
	if (icon == '') {
		icon = 'icon folder';
	}
	if (!isTabExist(subtitle, id)) {
		window.top.progress("open");
		if (url.indexOf('isIframe') != -1) {
			try {
				window.top
						.$('#maintabs')
						.tabs(
								'add',
								{
									title : subtitle,
									content : '<iframe src="'
											+ url
											+ '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
									closable : true,
									icon : icon,
									href : null,
									id : id,
									onLoad : function() {
										window.top.progress("close");
									}
								});
			} catch (e) {
				// TODO: handle exception
			}
		} else {
			window.top.$('#maintabs').tabs('add', {
				title : subtitle,
				href : url,
				closable : true,
				icon : icon,
				id : id,
				onLoad : function() {
					window.top.progress("close");
				}
			});
		}
	}
	window.top.tabClose();
	window.setTimeout(function() {
		window.top.progress("close");
	}, 200);
}

// 通过标题判断tabs是否存在并选中
function isTabExist(subtitle, id) {
	var tabs = window.top.$('#maintabs').tabs('tabs');
	if (id == undefined) {
		var flg = window.top.$('#maintabs').tabs('exists', subtitle);
		window.top.$('#maintabs').tabs('select', subtitle);
		return flg;
	}
	var tabs = window.top.$('#maintabs').tabs('tabs');
	for (var i = 0; i < tabs.length; i++) {
		if (id == tabs[i].panel("options").id) {
			try {
				window.top.$('#maintabs').tabs('select', i);
			} catch (e) {
				// TODO: handle exception
			}
			window.top.progress('close');
			return true;
		}
	}
	window.top.progress('close');
	return false;
}

function getTabById(id) {
	var tabs = window.top.$('#maintabs').tabs('tabs');
	for (var i = 0; i < tabs.length; i++) {
		if (id == tabs[i].panel("options").id) {
			try {
				window.top.$('#maintabs').tabs('select', i);
			} catch (e) {
				// TODO: handle exception
			}
			window.top.progress('close');
			return tabs[i];
		}
	}
	return null;
}

function progress(flag) {
	var msg = '加载中，请稍后...';
	flag = (flag == null ? "open" : flag);

	if (flag == "open") {
		wMask.css({
			display : "block",
			width : "100%",
			zIndex : 3006,
			height : document.body.scrollHeight
		}).appendTo("body");
		wMsg.html(msg).appendTo("body").css({
			display : "block",
			zIndex : 3007,
			paddingBottom : "10px",
			paddingTop : "10px",
			paddingRight : "10px",
			left : ($(document.body).outerWidth(true) - 190) / 2,
			top : ($(window).height() + 8) / 2
		});
	} else {
		wMask.css("display", "none");
		wMsg.css("display", "none");
	}
}
/*******************************************************************************
 * end
 ******************************************************************************/
