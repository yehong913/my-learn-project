<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>计划反馈</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

</head>
<body>
	 <fd:form id="feedBackForm" url="taskFeedbackController.do?saveTaskFeedback&taskId=${taskId}">	
		<div style="display:none;">
			<input type="checkbox" id="checkboxP" name="checkboxP" checked="checked" onclick="disPlan()" />
			<spring:message code="com.glaway.ids.pm.project.task.feedbackProgress"/>
		</div>
		<c:choose>
			<c:when
				test="${!((planType==0||planType==3)&&plan_bizcurrent=='ORDERED')&&((isAllHasFile == '' || isAllHasFile == null ) || (isAllHasFile == '' && isAllHasFile == null && isAllHasFile == 1) )||((planType==0||planType==3)&&plan_bizcurrent=='ORDERED')}">
				<fd:inputNumber id="progressRate" name="progressRate" validType="lessOneHundred[5]" 
					title="{com.glaway.ids.pm.project.task.haveFinished}" value="${progressRate}" precision="2" suffix="%">
				</fd:inputNumber>
			</c:when>
			<c:otherwise>
				<fd:inputNumber id="progressRate" name="progressRate" validType="equalOneHundred[6]" 
					title="{com.glaway.ids.pm.project.task.haveFinished}" value="${progressRate}" precision="2" suffix="%">
				</fd:inputNumber>
			</c:otherwise>
		</c:choose> 
		<input id="progressRateFlag" name="progressRateFlag" value="0" type="hidden" readonly="readonly" />
		<fd:inputTextArea id="progressRateRemark" title="{com.glaway.ids.pm.project.task.progressDetail}" name="progressRateRemark"></fd:inputTextArea>
		<input id="fileNameP" name="fileNameP" type="hidden"/>
		<input id="filePathP" name="filePathP" type="hidden"/> 
		<fd:uploadify name="files" id="file_upload" title="{com.glaway.ids.common.lable.uploadAttachment}" 
				uploader="taskFeedbackController.do?addFileAttachments" 
				auto="false" multi="false" showPanel="false" dialog="false"
				extend="**.xml;*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;*.mpp;*.zip;*.rar" 
				formData="documentTitle">
			<fd:eventListener event="onUploadSuccess" listener="afterSubmitForm" />
		</fd:uploadify>
	</fd:form>
	
	<script type="text/javascript">
	
	function execValue(value){
		//if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 || //./d/d$/.test(value))event.returnValue=false
		var regx = "/\.\d\d$/";
		//top.tip(value);
		//top.tip(regx.test(value));
		if((event.keyCode<48 || event.keyCode>57)&& event.keyCode!=46||regx.test(value)){
			event.returnValue=false;
		}
	}
	
		$(
		//根据计划是否有交付项，1表示为叶子节点且无交付项 3表示有子计划并且子计划未完工 并且计划状态为已下达（可以写100%），默认只能到99.99
		function(){
			if(('${planType}'==0 || '${planType}'==3)&&'${plan_bizcurrent}'=='ORDERED'){
				$("#progressRate").attr("onBlur","validonblur1(this)");
				$("#progressRateFlag").val("1");
			}
			
		}	
		)
		function closeTaskFeedback() {
			$.fn.lhgdialog("closeSelect");
		}
		function isNotExitFile(){
			return !isExitFile();
		}
		
		function isExitFile() {
			debugger;
			if($("#filePathP").val()==''){
				return false;
			}else {
				return true;
			}
		}
		
		function getProgressRate(){
			return $("#progressRate").val();
		}
		

		function afterSubmitForm(file, data, response) {
			top.jeasyui.util.tagMask('close');
			var data1 = $.parseJSON(data);
			$('#fileNameP').val(file.name);
			$('#filePathP').val(data1.obj);
			var checkP= $("input[name='checkboxP']:checked").val();
			if(checkP==undefined){
				$("#progressRate").textbox("setValue","${progressRate}");
				$("#progressRateRemark").textbox("setValue","");
			}
			var flag=true ;
			$.ajax({
				type : "POST",
				url : 'taskFeedbackController.do?saveTaskFeedback&taskId=${taskId}',
				async : false,
				data :  $('#feedBackForm').serialize(),
				success : function(data) {
					var data1 =$.parseJSON(data);
					var win = $.fn.lhgdialog("getSelectParentWin");
					win.tip(data1.msg);
					var obj=data1.obj;
					var infos=obj.split(',');
					if(infos.length>=1&&infos[1]=="doSubmit"){
						goSubmit(infos[0]);
						flag= false;
					}
					else{
						var currTab=top.getTabById(infos[0]);
						top.tabRefresh(currTab);
					}
				}
			});
			if(flag){
				return true;
			}else{
				return false;
			}
		}
		
		function clearDocumentP(){
			$('#fileNameP').textbox("setValue","");
			$('#filePathP').val("");
		}
		
		function saveAndSubmittt(){
			debugger;
			//进度反馈校验
			var pass= rateCheck();
			if(!pass){
				return false;
			}
			var t1=isNotExitFile();
			var t2=getProgressRate();
/* 			if(t1){	//没有附件
				if(pass!=false&&(getProgressRate()=='100.00' || getProgressRate()=='100')){
					goSubmit();
					return false;
				}else{
					$('#mm-tabupdate').click();
					//return repTaskFileUpload();
				}
				
			} */
			return repTaskFileUpload();
		}
		
		
		function repTaskFileUpload()
		{
			debugger
			var fileSize = $('#file_upload').data('uploadify').queueData.queueLength;
	  		if (fileSize > 0) {
				$('#file_upload').fduploadify('upload');
	  		}
	  		else{
	  			var checkP= $("input[name='checkboxP']:checked").val();
				if(checkP==undefined){
					$("#progressRate").textbox("setValue","${progressRate}");
					$("#progressRateRemark").textbox("setValue","");
				}
				var flag=true ;
				var currTab = '';
				$.ajax({
					type : "POST",
					url : 'taskFeedbackController.do?saveTaskFeedback&taskId=${taskId}',
					async : false,
					data :  $('#feedBackForm').serialize(),
					success : function(data) {
						debugger;
						var data1 =$.parseJSON(data);
						var win = $.fn.lhgdialog("getSelectParentWin");
						top.tip(data1.msg);
						var obj=data1.obj;
						var infos=obj.split(',');
						debugger;
						if(infos.length>=1&&infos[1]=="doSubmit"){
							goSubmit(infos[0]);
							flag= false;
						}
						else{
							currTab=top.getTabById(infos[0]);
						//	top.tabRefresh(currTab);
					
						}
					}
				});

				if(flag){
					return currTab;
				}else{
					return false;
				}
	  		}
		}
		
		function rateCheck(){
			var f = $("#progressRate").val();
			if(isNaN(f)){
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.emptyNumber"/>');
				//value.val('${progressRate}');
				return false;
			}
			if($("#progressRateFlag").val()=="0"){
				var planType='${planType}';
				if(planType=='1'){
					if(f>100.00||f<0){
						top.tip('<spring:message code="com.glaway.ids.pm.project.task.rateUpper"/>');
						//value.val('${progressRate}');
						return false;
					}
				}else{
					var isflag = '${isAllHasFile}';
					if(isflag=='0'){
						if(f>100.00||f<0){
							top.tip('<spring:message code="com.glaway.ids.pm.project.task.rateUpper"/>');
							//value.val('${progressRate}');
							return false;
						}
					}else if(isflag=='1'){
						if(f>=100||f<0){
							top.tip('<spring:message code="com.glaway.ids.pm.project.task.reteNumberUpper"/>');
							//value.val('${progressRate}');
							return false;
						}
					}
				}
			}else{
				if(f>=100||f<0){
					top.tip('<spring:message code="com.glaway.ids.pm.project.task.reteNumberUpper"/>');
					//value.val('${progressRate}');
					return false;
				}
			}
			return true;
		}
		function goSubmit(tabId) {
			 
			var win = $.fn.lhgdialog("getSelectParentWin");
			var planType=win.getPlanType();		
			var dialogUrl = 'taskDetailController.do?goSubmit&taskId=${plan_.id}&planType='+ planType+'&tabId='+tabId;
			$("#"+'goSubmittaskDetailDialog').lhgdialog("open","url:"+dialogUrl);
			//createDialog('goSubmittaskDetailDialog',dialogUrl);
			
		}
		
		/* function afterSubmit(file,data,response){
			var data1 =$.parseJSON(data);
			if(data1.obj=="doSubmit"){
				var win = $.fn.lhgdialog("getSelectParentWin");
				win.tip(data1.msg);
				win.goSubmit();
			}else{
				//win.tip(data1.msg);
				win.alerttip(data1.msg);
				win.opener.$('#mm-tabupdate').click();	
			}			
		} */
		function submittt(iframe){
			iframe.submittt();
			return false;
		} 
	</script>
	<fd:dialog id="goSubmittaskDetailDialog" width="400px" height="200px" modal="true" title="{com.glaway.ids.pm.project.plan.toApprove}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="submittt"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
</body>
</html>