<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
<iframe id="flowTempNodeTab_${param.id }" src="" style="width:100%;height:260px" frameborder="0"></iframe>
<script>
$(function(){
	if(tabUrlObj!=null) {
		$('#flowTempNodeTab_${param.id }').attr('src', tabUrlObj['${param.id }']+'&isEnableFlag=${param.isEnableFlag}&isChangeProcessView=${param.isChangeProcessView}&tempTemplateId=${param.tempTemplateId}&tempTemplateNodeId=${param.tempTemplateNodeId}');
	}
	else {
		top.tip('加载失败，请联系管理员！');
	}
});
</script>
</body>
</html>
