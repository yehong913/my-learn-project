<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<head>
<script type="text/javascript">
</script>
</head>
<fd:additionalAttrValRender
	entityUri="com.glaway.foundation.rep.entity.RepFileType" mode="add"
	id="RepFileType_generalAddAttr" attrName="${id}" attrVal="${id}">
</fd:additionalAttrValRender>
<input id="attributeIds" name="attributeIds" type="hidden" value="${attributeIds}">
<%--  <c:if test="${!empty userDefinedTypeList}">
	<c:forEach items="${userDefinedTypeList}" var="userDefinedTypeList">
		<c:if test = "${userDefinedTypeList.type =='0'}">
		<!-- 0:为非数字型，1:为文本类型，2：为如期类型-->
			<fd:inputNumber id="${userDefinedTypeList.id}"
				name="${userDefinedTypeList.idName}"
				required="${userDefinedTypeList.required}"
				precision="${userDefinedTypeList.precision}"
				max="${userDefinedTypeList.max}"
				title="${userDefinedTypeList.title}" value="${userDefinedTypeList.defaultValue}">			
			</fd:inputNumber> 
		</c:if>
		<c:if test = "${userDefinedTypeList.type =='1'}">
		<!-- 0:为非数字型，1:为文本类型，2：为如期类型-->
		<fd:inputText id="${userDefinedTypeList.id}" name="${userDefinedTypeList.idName}" required="${userDefinedTypeList.required}" maxLength="${userDefinedTypeList.maxLength}" title="${userDefinedTypeList.title}"  value="${userDefinedTypeList.defaultValue}"></fd:inputText>
		</c:if>
		<c:if test = "${userDefinedTypeList.type =='2'}">
		<!-- 0:为非数字型，1:为文本类型，2：为如期类型-->
		<fd:inputDate  id="${userDefinedTypeList.id}" editable="false" name="${userDefinedTypeList.idName}" required="${userDefinedTypeList.required}" title="${userDefinedTypeList.title}" formatter="${userDefinedTypeList.dateFormat}"  value="${userDefinedTypeList.defaultValue}"/>
		</c:if> 
	</c:forEach>
</c:if> --%>
