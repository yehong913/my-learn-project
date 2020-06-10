<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout">
<script type="text/javascript">

$(document).ready(
		function() {
			var checks = '${checks}';
			var val = checks.split(",");
			var boxes = document.getElementsByName("checked");
			for(var i=0;i<boxes.length;i++){
				if(val[i] == 'true'){
					boxes[i].checked = true;
				}else{
					boxes[i].checked = false;
				}
			}
		});
		
function savePower(){
		var boxes = document.getElementsByName("checked");
		var checks = "";
		for(var i=0;i<boxes.length;i++){
				if(boxes[i].checked == true){
					checks = checks + '1';
				}else{
					checks = checks + '0';
				}
		}
		$.post('projLibController.do?doSavePower', {
			'checks' : checks,
			'fileId' : '${fileId}',
			'projectId' : '${projectId}',
		}, function(data) {
			var win = $.fn.lhgdialog("getSelectParentWin");
			win.refshLibList();
			top.tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.auth.saveAuth"/>');
		});
}
		

function check(id){
	var val = id.split(",");
	if(val[1] == 'list'){
		var boxes = document.getElementsByName("checked");
		for(var i=0;i<boxes.length;i++){
			var val2 = boxes[i].id.split("+");
			if(val2[0] == val[0]){
				if(boxes[i].checked == true&&val2[1] != 'list'){
				boxes[i].checked = false;
				}
				
			}
		}
	}
	else if(val[1] == 'detail'){
		var boxes = document.getElementsByName("checked");
		for(var i=0;i<boxes.length;i++){
			var val2 = boxes[i].id.split("+");
			if(val2[0] == val[0]){
				if(boxes[i].checked == false){
					if(val2[1] == 'list'){
						boxes[i].checked = true;
						break;
					}
				}else{
					if(val2[1] != 'list'&&val2[1] != 'detail'){
						boxes[i].checked = false;
					}
				}
				
			}
		}
	}else{
		var boxes = document.getElementsByName("checked");
		for(var i=0;i<boxes.length;i++){
			var val2 = boxes[i].id.split("+");
			if(val2[0] == val[0]){
				if(val2[1] == 'list'){
					boxes[i].checked = true;
				}
				if(val2[1] == 'detail'){
					boxes[i].checked = true;
					break;
				}
			}
		}
	}
	

}

</script>
<fd:linkbutton id="saveBase" onclick="savePower()" value="{com.glaway.ids.pm.project.projectmanager.auth.save}" iconCls="basis ui-icon-save"/>
<div style="width: auto; height: auto; overflow-y: auto; overflow-x: hidden;">
	<table width="100%" id="lifeCycleStatus_table" class="lists">
		<tr>
		<th><spring:message code='com.glaway.ids.pm.project.projectmanager.auth.roleName'/></th>
		<th><spring:message code='com.glaway.ids.pm.project.projectmanager.auth.roleNumber'/></th>
		 <c:forEach items="${docc}" var="doc">
		 <th >${doc.checkName}</th>
		 </c:forEach>
		</tr>
		<tbody id="lifeCycleStatus_table_body">
		 <c:forEach items="${docs}" var="doc">
				<tr>
					<td align="center">${doc.roleName}</td>
					<td align="center">${doc.roleId}</td>
				<c:forEach items="${docc}" var="doc2">
		          <td align="center"><input style="margin:0px;" type="checkbox" name="checked"  checked="false" onclick="check('${doc.id}'+','+'${doc2.id}')" id="${doc.id}+${doc2.id}"/></td>
		         </c:forEach>
				</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</div>
<style>
 .lists{border-left:1px solid #ddd;border-top:1px solid #ddd;border-right:0px solid #ddd;border-bottom:0px solid #ddd; width:100%; }
.lists th{border-bottom:1px dotted #ddd;border-right:1px dotted #ccc;border-top:0px solid #ccc;border-left:0px solid #ddd;font-size:12px; font-weight:bold;height:20px; line-height:20px; vertical-align:middle; overflow:hidden; text-align:left; padding-left:4px; padding-right:4px;background-color: #EFEFEF ;
	background: -webkit-linear-gradient(top, #F9F9F9  0, #EFEFEF  100%);
	background: -moz-linear-gradient(top, #F9F9F9  0, #EFEFEF  100%);
	background: -o-linear-gradient(top, #F9F9F9  0, #EFEFEF  100%);
	background: linear-gradient(to bottom, #F9F9F9  0, #EFEFEF  100%);
	background-repeat: repeat-x;
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#F9F9F9 ,
		endColorstr=#EFEFEF , GradientType=0);}
.lists td{ text-align:left; padding:2px 4px ;border-left:0px solid #ccc;border-top:0px solid #ccc;border-right:1px dotted #ccc;border-bottom:1px dotted #ccc; font-size:12px; height:18px; line-height:18px;}
.lists tr:hover{ background:#eaf2ff;}
</style>


