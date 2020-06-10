<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<script type="text/javascript">
  $(document).ready(
		function() {
		
		});  
		
  
  function aa(){
	  var url = 'riskController.do?getRiskJson&projectId='+'${projectId}';
		$.ajax({
			type : 'POST',
			url : url,
			async : false,
			data : {},
			success : function(data) {
				var d = $.parseJSON(data);
				var riskJson = d.obj.riskJson;
				$('#riskJson').val(riskJson);
				/* $("#myForm").attr("action","/brs/frameset?__report=idsreport/riskScore.rptdesig&__showtitle=false");
				$("#myForm").submit(); */
				var url = "/brs/frameset?__report=idsreport/riskScore.rptdesign&__showtitle=false&__navigationbar=false"
						+ "&toc=false&parameter=false&export=false&print=false&printServer=false" //去除无效的工具栏按钮，只保留导出报表按钮
						+ "&riskJson="+encodeURI(encodeURI(riskJson));
				document.getElementById("riskId").src = url;
			/* 	document.frames[0].location.href = "/brs/frameset?__report=idsreport/riskScore.rptdesign&__showtitle=false&riskJson="+riskJson; */
				//$("#riskId").attr("action","/brs/frameset?__report=idsreport/riskScore.rptdesign&__showtitle=false&riskJson="+riskJson);
			} 
		});
	//$("#myForm").attr("action","/brs/frameset?__report=idsreport/projectwarn.rptdesign&__showtitle=false");
  }
	function viewRisk(url) {
		url = url + '&projectId='+'${projectId}&isCheck=true';
		url = encodeURI(encodeURI(url));
		$.fn.lhgdialog({
			content: 'url:'+url,
			width : 1100,
			height : 560,
			title : '查看风险',
		    button : [
			{
				name:'关闭',
				focus : true
			}]
		});
	}
</script>
<div class="easyui-layout" fit="true">
<form id="myForm" action="" method="post">
<input id="riskJson" name="riskJson" type="hidden">
<div>
<iframe id="riskId" width="100%" height="650px" scrolling="auto" src="/brs/frameset?__report=idsreport/riskScore.rptdesign&__showtitle=false&toc=false&parameter=false&export=false&print=false&printServer=false
&projectId=${projectId}&t1=${t1}&t2=${t2}&t3=${t3}&t4=${t4}&t5=${t5}&t6=${t6}&t7=${t7}&t8=${t8}&t9=${t9}&t10=${t10}&t11=${t11}&t12=${t12}&t13=${t13}"></iframe> 
<!-- <iframe id="riskId" width="100%" height="650px" scrolling="auto" ></iframe>  -->
</div>
</form>