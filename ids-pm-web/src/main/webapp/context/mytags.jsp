<%@ taglib prefix="t" uri="/easyui-tags" %>
<%@ taglib prefix="fd" uri="/fd-easyui-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<c:set var="webRoot" value="<%=basePath%>"/>
<fmt:setLocale value="zh_CN"/>
<fmt:setBundle basename="i18n/message" var="global"/>

<c:if test="${param.afterIframe!=null }">
    <t:base type="jquery,easyui,tools"></t:base>
</c:if>

<fmt:setBundle basename="config" var="config" />
<fmt:message key="rdflowWeb_Nginx" var="rdflowWeb_Nginx" bundle="${config}" />
<fmt:message key="riskproblems_Nginx" var="riskproblems_Nginx" bundle="${config}" />
<fmt:message key="pm_Nginx" var="pm_Nginx" bundle="${config}" />
<fmt:message key="common_Nginx" var="common_Nginx" bundle="${config}" />
<fmt:message key="brsUrl" var="brsUrl" bundle="${config}" />
<fmt:message key="riskproblems_service" var="riskproblems_service" bundle="${config}" />
