<%@ page language="java" import="java.util.*"
		 contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
	<t:base type="jquery,tools,easyui,lhgdialog"></t:base>
    <script type="text/javascript">
    </script>
    <style>
        .oLine {
            width: 100%;
            height: 200px;
            margin: auto;
        }
        .jk {
            width: 20px;
            height: 1px;
            margin: 3px;
        }
        .wert{
            float: left;
        }
    </style>
</head>
<body class="easyui-layout" fit="true">

<fd:tabs id="tt" tabPosition="top" fit="true">
    <c:forEach var="lists" items="${listss}">
    	<c:if  test="${lists.get(0).get(0).get(0).displayUsage=='2'}">
    		 <c:if  test="${!empty lists.get(0).get(0).get(0).ext1}">	
					<fd:tab title="${lists.get(0).get(0).get(0).ext1}" href="${lists.get(0).get(0).get(0).loadUrl}"></fd:tab>
			</c:if>	
    	</c:if>
    
        <c:if  test="${lists.get(0).get(0).get(0).displayUsage!='2'}">
	        <fd:tab title="${lists.get(0).get(0).get(0).ext1}">
	            <c:forEach var="list" items="${lists}">
	                <c:if test="${list.size() == 1 && list.get(0).get(0).control!='8'}">
	                    <c:if test="${list.get(0).get(0).control=='0'}"> <!-- 单行文本 -->
	                        <%--<c:if test="${list.get(0).get(0).display}"><div style="display:none"></c:if>--%>
	                        <fd:inputText id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}"
	                                      required="${list.get(0).get(0).required}"></fd:inputText>
	                        <%--<c:if test="${plugin.hidden}"></div></c:if>--%>
	                    </c:if>
	                    <c:if test="${list.get(0).get(0).control=='1'}"> <!-- 多行文本 -->
	                        <%--<c:if test="${list.get(0).get(0).display}"><div style="display:none"></c:if>--%>
	                        <fd:inputTextArea id="${list.get(0).get(0).id}"
	                                          required="${list.get(0).get(0).required}" title="${list.get(0).get(0).propertyName}"
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
							<fd:combobox id="${list.get(0).get(0).id}" editable="false" required="${list.get(0).get(0).required}" title="${list.get(0).get(0).propertyName}">
							</fd:combobox>
							<%--<c:if test="${plugin.hidden}"></div></c:if>--%>
						</c:if>
	                    <c:if test="${list.get(0).get(0).control=='5'}"> <!-- 下拉框 -->
	                        <%--<c:if test="${list.get(0).get(0).display}"><div style="display:none"></c:if>--%>
	                        <fd:combobox id="${list.get(0).get(0).id}" required="${list.get(0).get(0).required}" title="${list.get(0).get(0).propertyName}">	                            
	                        </fd:combobox>
	                        <%--<c:if test="${plugin.hidden}"></div></c:if>--%>
	                    </c:if>
	                </c:if>

					<c:if test="${list.get(0).get(0).control=='9'}"> <!-- 选择框 -->
						<fd:inputSearch id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).valueInfo}" editable="false" searcher="${list.get(0).get(0).operationEvent}" />
					</c:if>

	                <c:if test="${list.size() >= 1 && list.get(0).get(0).control=='8'}">
	                    <c:forEach var="jyj" items="${list}">
							<fd:linkbutton iconCls="${jyj.get(0).format}" value="${jyj.get(0).propertyName}"/>
	                    </c:forEach>
	                </c:if>

	                <c:if test="${list.size() > 1 && list.get(0).get(0).control!='8'}">
	                    <div class="oLine">
							<fd:datagrid id="${list.get(0).get(0).id}" checkbox="false" checked="true"
										 checkOnSelect="true" idField="id" fit="false" width="100%" height="100px"
										 fitColumns="true" pagination="false" url="">
							</fd:datagrid>
							</br>
	                        <%--<fd:panel border="false" title="${list.get(0).get(0).propertyName}" fit="true" collapsed="false" width="100%" height="100px" >
	                            <div>
	                                <table id="${list.get(0).get(0).id}"></table>
	                            </div>
	                        </fd:panel>--%>
	
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
	        </fd:tab>
        </c:if>
    </c:forEach>
</fd:tabs>

</body>


</html>