<%@ page language="java" import="java.util.*"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <t:base type="jquery,tools,easyui,lhgdialog"></t:base>
    <style>
        .oLine {
            width: 100%;
            height: 200px;
            margin: inherit;
        }
        .jk {
            width: 50px;
            height: 1px;
            margin: 3px;
        }
        .wert{
          float: left;
        }
    </style>
</head>
<body>
<div class="easyui-layout" fit="true">
<c:if  test="${displayUsage=='2'}">
	<c:if  test="${!empty tabTemplateDto}">
			<fd:tabs id="tt" tabPosition="top" fit="true" >
				<fd:tab title="${tabTemplateDto.name}"
					href="${tabTemplateDto.externalURL}" selected="true" ></fd:tab>	
			</fd:tabs>
	</c:if>	
</c:if>

<c:if  test="${displayUsage!='2'}">
<c:forEach var="list" items="${lists}">
    <c:if test="${list.size() == 1 && list.get(0).get(0).control!='8'}">
        <c:if test="${list.get(0).get(0).control=='0'}"> <!-- 单行文本 -->
            <%--<c:if test="${list.get(0).get(0).display}"><div style="display:none"></c:if>--%>
            <fd:inputText id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}" name="${list.get(0).get(0).propertyValue}"
                          required="${list.get(0).get(0).required}"></fd:inputText>
            <%--<c:if test="${plugin.hidden}"></div></c:if>--%>
        </c:if>
        <c:if test="${list.get(0).get(0).control=='1'}"> <!-- 多行文本 -->
            <%--<c:if test="${list.get(0).get(0).display}"><div style="display:none"></c:if>--%>
            <fd:inputTextArea id="${list.get(0).get(0).id}"
                              required="${list.get(0).get(0).required}" title="${list.get(0).get(0).propertyName}" name="${list.get(0).get(0).propertyValue}"
            ></fd:inputTextArea>
            <%--<c:if test="${plugin.hidden}"></div></c:if>--%>
        </c:if>
        <c:if test="${list.get(0).get(0).control=='2'}"> <!-- 单选 -->
            <%--<c:if test="${list.get(0).get(0).display}"><div style="display:none"></c:if>--%>
            <input type="radio" name="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).propertyName}">${list.get(0).get(0).propertyName}</input>
            <%--<c:if test="${plugin.hidden}"></div></c:if>--%>
        </c:if>
        <c:if test="${list.get(0).get(0).control=='3'}"> <!-- 公共选人 -->
            <%--<c:if test="${list.get(0).get(0).display}"><div style="display:none"></c:if>--%>
            <fd:inputSearchUser id="${list.get(0).get(0).id}" queryMode="in"
                                required="${list.get(0).get(0).required}"
                                title="${list.get(0).get(0).propertyName}"></fd:inputSearchUser>
            <%--<c:if test="${plugin.hidden}"></div></c:if>--%>
        </c:if>
        <c:if test="${list.get(0).get(0).control=='4'}"> <!-- 日期文本框 -->
            <%--<c:if test="${list.get(0).get(0).display}"><div style="display:none"></c:if>--%>
            <fd:inputDate id="${list.get(0).get(0).id}" editable="false"
                          required="${list.get(0).get(0).required}" title="${list.get(0).get(0).propertyName}"
            ></fd:inputDate>
            <%--<c:if test="${plugin.hidden}"></div></c:if>--%>
        </c:if>
        <c:if test="${list.get(0).get(0).control=='10'}"> <!-- 下拉框(文本可编辑) -->
            <%--<c:if test="${list.get(0).get(0).display}"><div style="display:none"></c:if>--%>
            <fd:combobox id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}" editable="false" required="${list.get(0).get(0).required}">
            </fd:combobox>
            <%--<c:if test="${plugin.hidden}"></div></c:if>--%>
        </c:if>

        <c:if test="${list.get(0).get(0).control=='5'}"> <!-- 下拉框 -->
            <%--<c:if test="${list.get(0).get(0).display}"><div style="display:none"></c:if>--%>
            <fd:combobox id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}" required="${list.get(0).get(0).required}">
            </fd:combobox>
            <%--<c:if test="${plugin.hidden}"></div></c:if>--%>
        </c:if>
        <%--<c:if test="${list.get(var.index)}"></c:if>--%>
    </c:if>

    <c:if test="${list.size() >= 1 && list.get(0).get(0).control=='8'}">  <!-- 按钮 -->
        <c:forEach var="jyj" items="${list}">
            <fd:linkbutton iconCls="${jyj.get(0).format}" value="${jyj.get(0).propertyName}"/>
        </c:forEach>
        <%--<button>${list.get(0).get(0).propertyName}</button>--%>
    </c:if>

    <c:if test="${list.get(0).get(0).control=='9'}"> <!-- 选择框 -->
        <fd:inputSearch id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).valueInfo}" editable="false" searcher="${list.get(0).get(0).operationEvent}" />
    </c:if>

    <c:if test="${list.size() > 1 && list.get(0).get(0).control!='8'}">
        <div class="oLine"  style="clear: both;">
            <fd:datagrid id="${list.get(0).get(0).id}" checkbox="false" checked="true"
                         checkOnSelect="true" idField="id" fit="false" width="100%" height="100px"
                         fitColumns="true" pagination="false" url="">
            </fd:datagrid>
            </br>
        </div>
        <script>
            setTimeout(function(){
                $('#${list.get(0).get(0).id}').datagrid({
                    url: '',
                    width: '100%',
                    height: '160px',
                    checkbox: true,
                    columns: [[
                        <c:forEach var="col" items="${list.get(1)}" varStatus="status">
                        {
                            field: '${col.propertyValue}',
                            title: '${col.propertyName}',
                            width: 740/${list.get(1).size()}
                        }<c:if test="${list.get(1)!=null && fn:length(list.get(1))-1 > status.index}">, </c:if>
                        </c:forEach>
                    ]]
                });
            },1);
        </script>
    </c:if>
</c:forEach>
</c:if>
</div>
</body>


</html>