 var dataGrideId = "";
  $(function() {
	  var tab = $('#tt').tabs('getSelected');
	  var index = $('#tt').tabs('getTabIndex',tab);
	  dataGrideId = $("#dataGrideId_"+index).val();
    	var planId = $("#planId").val();
    	$('#'+dataGrideId).datagrid(
				{
					url : 'taskDetailController.do?getDocRelationList&id='
						+ planId,
					pageNumber : 1
				});
	});
  
  
	function getDeliverableName(val, row, value){
		return row.deliverableName;
	}
	
	function getVersion(val, row, value){
		return row.version;
	}
	
	function getStatus(val, row, value){
		return row.status;
	}
	
	var idRow='';
	function checkLine() {
		debugger
/*		var tab = $('#tt').tabs('getSelected');
		var index = $('#tt').tabs('getTabIndex',tab);
		var dataGrideId = $("#dataGrideId_"+index).val();*/
		var row = $('#'+dataGrideId).datagrid('getSelected');
		if (row == null || row == '' || row == 'undefined'){
			tip("请选择一记录");
			return false;
		}
		
		if(row.length>1){
			tip("只能选择一条记录");
			return false;
		}
		idRow=row.id;
		var projectId = $("#projectId").val();
		var dialogUrl = 'projLibController.do?goProjLibLayout0&id='+projectId;
		$("#"+'taskDocCheckLineDialog').lhgdialog("open", "url:"+dialogUrl);
	}
	
	function taskDocCheckLineDialog(iframe) {
		//iframe = this.iframe.contentWindow;
		if (iframe.validateSelectedNum()) {
			var docId = iframe.getSelectionsId();		
			var rows=$('#'+dataGrideId).datagrid('getSelections');
			var row=rows[0];
			$.ajax({
				url : 'deliverablesInfoController.do?update',
				type : 'post',
				data : {
					id : row.deliverableId,
					fileId : docId
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var msg = d.msg;
						tip(msg);
						refreshDatagrid();
					}
				}
			});
			return true;
		} else {
			return false;
		}
	}
		
	function uploadLine() {
		debugger
		var row = $('#'+dataGrideId).datagrid('getSelected');
		if (row == null || row == '' || row == 'undefined'){
			tip("请选择一记录");
			return false;
		}
		if(row.length>1){
			tip("只能选择一条记录");
			return false;
		}
		var projectId = $("#projectId").val();
		createaddwindow('projLibController.do?goAdd2&deliverableId=' + row.deliverableId + '&projectId='+projectId+'&opType=LINKDELIVER', row.deliverableId);
	}
	
	function createaddwindow(url,id) {
		idRow=id;
		$("#"+'projLibSubimtDialog').lhgdialog("open","url:"+url);
	}
	
	function projLibSubimt(iframe) {
		var result = false;
		var result = iframe.saveFileBasicAndUpload();
		if(result){
			refreshDatagrid(); 
			return true;
		} else {
			return false;
		}
	}

	
	function uploadFile(docId) {		
		$.ajax({
			url : 'deliverablesInfoController.do?update',
			type : 'post',
			data : {
				id: idRow,
				fileId : docId
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				debugger;
				if (d.success) {
					var msg = d.msg;
					/*if(msg=='交付项文档挂接成功'){
						 $('#planResolve').attr("style","display:none");
				         $('#flowResolve').attr("style","display:none");
					}*/
					refreshDatagrid();
				}
			}
		});
	}
	
	function submitLine() {
		var row = $('#'+dataGrideId).datagrid('getSelected');
		if (row == null || row == '' || row == 'undefined'){
			tip("请选择一记录");
			return false;
		}
		if(row.length>1){
			tip("只能选择一条记录");
			return false;
		}
		if(row.status!="拟制中"){
			tip("只能选择一条拟制中数据提交");
			return false;
		}
		$.ajax({
            url : 'projLibController.do?submitProcess',
            type : 'post',
            data : {
                id : row.docId,
                entityName : 'ProjectFile',
                businessType : 'fileType:'+row.fileTypeId,
                docName : row.docName
            },
            cache : false,
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    $('#submitDialogForDocCheckLine').lhgdialog(
                        "open",
                        "url:"
                            + 'commonFlowController.do?getDynamicForm&taskNumber='+ row.docId
                            + '&entityName=ProjectFile'
                            + '&businessType='+d.obj);
                } else {
                    top.tip(d.msg);
                }
            }
        });
	}
	
	
	function beOk(iframe) {	
        var inputs = iframe.document.getElementsByTagName("input");
        if(inputs.length>0){
        	for(var i=0;i<inputs.length;i++){
        		var curName = inputs[i].name;        		
	         	if(inputs[i].name.indexOf("leader_")>=0 && curName.split("_").length==2) {
	        		var leader = iframe.$('input[name='+inputs[i].name+']').val();
	        		if(leader == null || leader == ''){	        			
	        			top.tip("所有选择不能为空");
	        			return false;
	        		}
	        	} 
	        }
        
        }
    	var options = {
                submitCallback : function() {
                    refreshDatagrid();
                    setTimeout(function() {
                        $.fn.lhgdialog("closeAll")
                    }, 100);
                }
            }
            setTimeout(function() {
                iframe.submitForm(options);
            }, 100)
            return false; 
       
     }

    function beCancel(iframe) {
        refreshDatagrid();
        setTimeout(function() {
            $.fn.lhgdialog("closeAll")
        }, 100)
    }
    
    function beClose() {
        refreshDatagrid()
        setTimeout(function() {
            $.fn.lhgdialog("closeAll")
        }, 100)
        return true;
    }
	
    function refreshDatagrid() {
    	var planId = $("#planId").val();
    	$('#'+dataGrideId).datagrid(
				{
					url : 'taskDetailController.do?getDocRelationList&id='
						+ planId,
					pageNumber : 1
				});
    }
    
    
	function addLink(val, row, value) {
		if (val!=null&&val!='') {
			var userLevel = $("#userLevel").val();
			if ((row.havePower == true || row.havePower == 'true')&& userLevel >= row.securityLevel) {
				return "<a href='#' onclick='showDocDetail(\""
					+ row.docId
					+ "\""
					+ ','
					+ "\""
					+ row.download
					+ "\""
					+ ','
					+ "\""
					+ row.detail
					+ "\")'  id='myDoc'  style='color:blue'>" + val +"</a>";
			} else {
				return val;
			}
		} else 
			return ;
	}
	
	function showDocDetail(id,download,detail) {
		var url="projLibController.do?viewProjectDocDetail&opFlag=1&id="+id+ "&download=" + download+ "&detail=" + detail;
		createDialog('openDocumentInfoDialog',url);
	}
	