/**
 * 
 */
function openDocHisoryFlow(taskNumber){
	var tabTitle = '历史版本审批流程查看';
	var url = '${webRoot}/taskFlowCommonController.do?taskFlowList&taskNumber='+taskNumber;
	createdetailwindow_close(tabTitle, url, 800, 400);

}