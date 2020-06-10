<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>计划拒绝</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<script type="text/javascript">
	function refusePlan(){
		var refuseReason = $("#refuseReason").combobox('getValue');
		var refuseRemark = $("#refuseRemark").textbox('getValue');
		if(refuseReason == ""){
			top.tip("驳回原因为空");
			return false;
		}
		$.ajax({
			type : 'POST',
			data : {
				planId : '${planId}',
				refuseReason : refuseReason,
				refuseRemark : refuseRemark
			},
			url : 'planController.do?refusePlan',
			async : false,
			cache :false,
			success : function(data){
				var d = $.parseJSON(data);
				if(d.success){
				    debugger;
					top.tip("拒收申请提交成功");
                    var win = $.fn.lhgdialog("getSelectParentWin");
                 //   win.refreshMainPage();

                    setTimeout(function(){
                        //刷新主页面
                        refreshTabsByTabsName('待办任务,首页');
                        $.fn.lhgdialog("closeAll");
                    },500);

                }else{
					top.tip("拒收申请提交失败");
					return false;
				}
			}
		});
	}

</script>
</head>
<body>
<fd:form id="planRefuseForm">
    <c:choose>
        <c:when test="${empty refuseId}">
            <fd:combobox id="refuseReason" textField="name" title="驳回原因"
                         editable="false" valueField="id" required="true" panelMaxHeight="200"
                         url="planController.do?planRefuseReasonList" />
            <fd:inputTextArea id="refuseRemark" title="{com.glaway.ids.common.lable.remark}" name="refuseRemark"/>
        </c:when>
        <c:otherwise>
            <fd:combobox id="refuseReason" textField="name" title="驳回原因"
                         editable="false" valueField="id" required="true" panelMaxHeight="200"
                         url="planController.do?planRefuseReasonList" readonly="true" selectedValue="${planRefuseInfo_.refuseReason}" value="${planRefuseInfo_.refuseReason}" />
            <fd:inputText id="planName" name = "planName" title="计划名称" readonly="true" value="${plan_.planName}" ></fd:inputText>
            <fd:inputSearchUser id="owner" name="owner" title="计划负责人"  required="true" value="${plan_.owner}" readonly="true">
            </fd:inputSearchUser>
            <fd:inputTextArea id="refuseRemark" title="{com.glaway.ids.common.lable.remark}" name="refuseRemark" value="${planRefuseInfo_.remark}" readonly="true"/>
        </c:otherwise>
    </c:choose>

</fd:form>

</body>