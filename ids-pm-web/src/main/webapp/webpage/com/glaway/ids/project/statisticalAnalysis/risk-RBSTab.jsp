<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	function searchRiskTarget() {
		var categoryId = $('#riskPath').combotree('getValue');
		var riskName = $('#riskName').val();
		var assess = $('#assess').combobox('getValues');
		var status = $('#status').combobox('getValues');
		var projectId = '${projectId}';

		$.ajax({
			url : 'riskController.do?searchRisk',
			type : 'post',
			data : {
				categoryId : categoryId,
				riskName : riskName,
				assess : assess.toString(),
				status : status.toString(),
				projectId : projectId
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					riskTreeReport.clearAll();
					riskTreeReport.parse(d.obj, 'js');
				}
			}
		});
	}

	function tagSearchReset() {
		$('#riskName').textbox("clear");
		$('#riskPath').comboztree("clear");
		$('#assess').combobox("clear");
		$('#status').combobox("clear");
	}

	function viewRiskForRbs(id) {
		createDialog('checkRiskDialog1', 'riskModifyController.do?goCheck&isCheck=true&id=' + id);
	}

	//导出
	function exportRisk() {
		debugger
		var categoryId = $('#riskPath').combotree('getValue');
		var riskName = $('#riskName').val();
		var assess = $('#assess').combobox('getValues');
		var status = $('#status').combobox('getValues');
		
		var sAssess,sStatus;
		if(categoryId==undefined){
			categoryId="";
		}
		if(assess.length<=0){
			sAssess="";
		}else{
			sAssess=assess.toString();
		}
		if(status.length<=0){
			sStatus="";
		}else{
			sStatus=status.toString();
		}
		
		// 导出计划Excel
		postFormDataToAction("riskController.do?doDownloadRisk&isExport=true"
				+ "&projectId=${projectId}&categoryId=" + categoryId
				+ "&riskName=" + riskName + "&assess=" + sAssess + "&status="
				+ sStatus);
	}

	/**
	 * Jeecg Excel 导出
	 * 代入查询条件
	 */
	function postFormDataToAction(url) {
		var params = '';
		window.location.href = url + encodeURI(params);
	}
</script>
	<div  style="padding: 1px; overflow-x: hidden">
		<div id="planListtb">
			<fd:searchform id="riskTargetSearchForm"
				onClickSearchBtn="searchRiskTarget()"
				onClickResetBtn="tagSearchReset()">

				<fd:combotree title="{com.glaway.ids.pm.project.statisticalAnalysis.riskCategory}" treeIdKey="id" name="categoryId"
					url="riskTargetController.do?getFromRiskCategoryCombotree"
					id="riskPath" treePidKey="parentId" editable="false"
					multiple="true" panelHeight="200" treeName="name" prompt="全部"></fd:combotree>
				<fd:inputText title="风险" id="riskName" queryMode="like" />
				<fd:combobox id="assess" title="{com.glaway.ids.pm.project.statisticalAnalysis.assess}" name="assess" queryMode="eq" textField="assess" valueField=""
					editable="false" multiple="true" prompt="{com.glaway.ids.common.lable.selectall}" data="1_低,2_中,3_高">
				</fd:combobox>
				<fd:combobox id="status" title="{com.glaway.ids.common.lable.status}" name="status" queryMode="eq" textField="status" valueField=""
					editable="false" multiple="true" prompt="{com.glaway.ids.common.lable.selectall}" data="0_关闭,1_激活">
				</fd:combobox>

			</fd:searchform>
			
			<fd:toolbar id="toolbar">
				<fd:toolbarGroup align="left">
					<fd:linkbutton id="exportRisk" onclick="exportRisk()" value="导出"
						iconCls="basis ui-icon-export" />
				</fd:toolbarGroup>
			</fd:toolbar>
			
			<div style="height: 800px">
				<fd:lazyLoadingTreeGrid
					url="riskController.do?getRiskTree&projectId=${projectId}"
					id="riskTreeReport" width="99%" height="80%"
					imgUrl="plug-in/icons_greenfolders/" initWidths="200,100,*"
					columnIds="name,assess,status" header="风险,评估,状态"
					columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;']"
					colAlign="left,left,left" colSortings="na,na,na"
					colTypes="tree,ro,ro" enableTreeGridLines="false"
					enableLoadingStatus="true" enableMultiselect="true">
				</fd:lazyLoadingTreeGrid>
			</div>
		</div>
	</div>
<fd:dialog id="checkRiskDialog1" width="1100px" height="560px"
	modal="true" title="{com.glaway.ids.pm.project.statisticalAnalysis.checkRisk1}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>





