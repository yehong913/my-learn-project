<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<title>文档类型详情</title>
<t:base type="jquery,easyui,tools"></t:base>
<script>
	
</script>
<body style="overflow-x: hidden;">
	<div class="easyui-layout" fit="true"> 
		 <div data-options="region:'north',title:''" style="height:95%;">
	    	<fd:tabs id="repFileTypeConfigDetailTabs" tabPosition="top" fit="true" >
	    	    <fd:tab href="repFileTypeConfigController.do?goAddRepFileTypeConfig&type=${type}&id=${repFileTypeId}" 
                    title="{com.glaway.ids.pm.config.repFileTypeConfig.baseInfo}" id="baseInfoTab"></fd:tab>
	    		<fd:tab href="repFileTypeConfigController.do?goApproveProcessTab&type=${type}&repFileTypeId=${repFileTypeId}&view=view" 
					title="{com.glaway.ids.pm.config.repFileTypeConfig.approveProcess}" id="approveProcessTab"></fd:tab>
				<fd:tab href="repFileTypeConfigController.do?goCustomAttributeTab&repFileTypeId=${repFileTypeId}&entityUri=${entityUri}&fieldName=${repFileTypeId}&fieldValue=${repFileTypeId}&type=${type}" 
					title="{com.glaway.ids.pm.config.repFileTypeConfig.customAttribute}" id="customAttributeTab"></fd:tab>
	    	</fd:tabs>
    	</div>   
	</div>
</body>