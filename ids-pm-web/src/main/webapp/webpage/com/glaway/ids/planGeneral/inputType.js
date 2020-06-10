
  $(function() {
	  var tab = $('#tt').tabs('getSelected');
	  var index = $('#tt').tabs('getTabIndex',tab);
	  var dataGrideId = $("#dataGrideId_"+index).val();
    	var planId = $("#planId").val();
    	$('#'+dataGrideId).datagrid(
				{
					url : 'taskDetailController.do?getInputsRelationList&id='
							+ planId,
					pageNumber : 1
				});
	});
  
	function showOrigin(val, row, value){
		if(row.originType == "LOCAL"){
			return "本地文档";
		}else{
			return val;
		}
	}
	
	function addLink(val, row, value){
		if(val!=null&&val!=''&&row.originType == 'LOCAL'){
			return '<a  href="#" onclick="importDoc(\'' + row.docId + '\',\'' + row.docNameShow + '\')" id="myDoc"  style="color:blue">'+val+'</a>';
		}else if(row.originType == 'PROJECTLIBDOC'){
			var path = "<a  href='#' title='查看' style='color:blue' onclick='openProjectDoc1(\""+ row.docIdShow+ "\",\""+ row.docName+ "\",\""+ row.ext1+"\",\""+ row.ext2+"\")'>"
				+ row.docNameShow
				+ "</a>"
			if (row.ext3 == false || row.ext3 == 'false') {
				path = row.docNameShow;
			}
			return path; 
		}else if(row.originType == 'PLAN'){
			var path = "<a  href='#' title='查看' style='color:blue' onclick='openProjectDoc1(\""+ row.docId+ "\",\""+ row.docNameShow+ "\",\""+ row.ext1+"\",\""+ row.ext2+"\")'>"
			+ row.docNameShow
			+ "</a>"
		if (row.ext3 == false || row.ext3 == 'false') {
			path = row.docNameShow;
		}
		return path; 
		}
		else return ;
		
	}
	
	function openProjectDoc1(id, name,download,history) {
		if (download == false || download == 'false') {
			download = "false";
		}
		if (history == false || history == 'false') {
			history = "false";
		}
		var url = "projLibController.do?viewProjectDocDetail&id=" + id
				+ "&download=" + download + "&history=" + history;
		createdetailwindow("文档详情", url, "870", "580")
	}

	
	// 新增输入：
	 function openAddResolve(dgId, url,id) {
		 if ("1" == $("#isEnableFlag").val()) {
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
				return;
			}
/* 		    var allRows = $("#"+dgId).datagrid("getRows"); */
	    var ids = '';
	    gridname = dgId;
	    var dialogUrl = url +'&allPreposeIds='+$("#allPreposeIds").val()
		+'&cellId='+$("#cellId").val()
		+'&parentPlanId='+$("#parentPlanId").val()
		+'&preposeIds='+preposeIds
		+'&useObjectId='+$('#useObjectId').val()
		+'&useObjectType='+$('#useObjectType').val();
	    $("#"+id).lhgdialog("open",'url:' + dialogUrl+"&ids="+ids);   
	}
	 
	function openAddDialogInputSureResolve(iframe){
		iframe.saveSelectedRowsResolve();
		return false;
	}

	function openAddDialogLocalSureResolve(iframe) {
		iframe.addFormsubmitResolve();
	    return false;
	}
	