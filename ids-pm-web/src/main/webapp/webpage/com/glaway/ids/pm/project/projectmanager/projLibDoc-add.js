function selectDocPath(parentId,pathName,projectId,node)
{
	debugger;
	var id = $('#projectId').val();
	var node = $('#node').val();
	var yanfa = $('#yanfa').val();
	var treeType = $('#treeType').val();
	if(yanfa == 'yanfa'){
		yanfa = "1";
	}
	createRolewindow('路径','/ids-pm-web/projLibController.do?goProjLibTree&projectId='+id+'&treeType='+treeType+'&havePower='+yanfa,450,'',parentId,pathName,node);
}

/**
 * 查看时的弹出窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function createRolewindow(title, url, width, height ,parentId,pathName,node) {
	width = width ? width : 700;
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
			parent : windowapi,
			title : title,
			opacity : 0.3,
			cache : false,
		      button : [{
					name:"确定",
					callback:function(){
						iframe = this.iframe.contentWindow;
						var selectNode = iframe.getSelectNode();
						 if(selectNode!=null&&selectNode.id!=null){
							 $(parentId).val(selectNode.id);
							 $(node).val(selectNode.id);
								$.ajax({
									type : 'POST',
									url : 'projLibController.do?getDocNamePath',
									dataType : "text",
									async : false,
									cache : false,
									data : {'id' : selectNode.id},
									success : function(result) {
									  //$(pathName).val(result);
									  $(pathName).textbox("setValue",result);
										$.ajax({
											type : 'POST',
											url : 'projLibController.do?checkUpload',
											dataType : "text",
											async : false,
											cache : false,
											data : {'id' : selectNode.id},
											success : function(data) {
												var d = $.parseJSON(data);
												if (d.success) {
													$('#uploadPowerFlog').val("1");
													$('#knowledgeFile_uploadspan').show();
												}else{
													$('#uploadPowerFlog').val("0");
													$('#knowledgeFile_uploadspan').hide();
												}
											}
										});
									}
								});
							 return true;
							 
						 }
						 return false;
					}
				},
				{
					name:'取消',
					focus : true
				}],
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
		      button : [{
					name:"确定",
					callback:function(){
						iframe = this.iframe.contentWindow;
						var selectNode = iframe.getSelectNode();
						 if(selectNode!=null&&selectNode.id!=null){
							 $(parentId).val(selectNode.id);
							 $(node).val(selectNode.id);
								$.ajax({
									type : 'POST',
									url : 'projLibController.do?getDocNamePath',
									dataType : "text",
									async : false,
									cache : false,
									data : {'id' : selectNode.id},
									success : function(result) {
									  //$(pathName).val(result); 
									  $(pathName).textbox("setValue",result);
										$.ajax({
											type : 'POST',
											url : 'projLibController.do?checkUpload',
											dataType : "text",
											async : false,
											cache : false,
											data : {'id' : selectNode.id},
											success : function(data) {
												var d = $.parseJSON(data);
												if (d.success) {
													$('#knowledgeFile_uploadspan').show();
													$('#uploadPowerFlog').val("1");
												}else{
													$('#knowledgeFile_uploadspan').hide();
													$('#uploadPowerFlog').val("0");
												}
											}
										});
									}
								});
							 return true;
							 
						 }
						 return false;
					}
				},
				{
					name:'取消',
					focus : true
				}],
		});
	}	 
}
