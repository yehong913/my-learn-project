<%@ page language="java" import="java.util.*"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <c:if test="${! empty fromType && fromType == 'update'}">
        <t:base type="jquery,easyui,tools,DatePicker"></t:base>
        <script src="webpage/com/glaway/ids/project/plan/planGeneralAdd.js"></script>
    </c:if>

    <script src="webpage/com/glaway/ids/pm/project/plan/planList.js"></script>
    <style>
        .oLine {
            width: 100%;
            margin: auto;
        }
        .jk {
            width: 20px;
            height: 1px;
            margin: 5px;
        }
        .wert{
            float: left;
        }
        .gla_step{
            width: auto;}
        .gla_step .step_dot{
            width: auto; margin:0 auto;}
        .gla_step .step_dot tr td div{white-space: nowrap;
            display: inline-block;
            width: 90px;}
        .gla_step .step_dot .stepFirst{ text-align: center}
        .gla_step .step_dot .stepMiddle{text-align: center}
        .gla_step .step_dot tr td i{
            left: -33px;}
        .gla_step .step_dot .stepEnd{ text-align: center}
        .div-msg-btn{bottom:12px;}
        .lhgdialog_cancle{border: 1px solid #0C60AA;
            cursor: pointer;
            padding: 2px 16px;
            line-height: 22px;
            cursor: pointer;
            margin: 0px 8px 0 0;
            -moz-border-radius: 2px;
            -webkit-border-radius: 2px;
            border-radius: 2px;
            font-family: Microsoft Yahei,Arial,Tahoma;
            BACKGROUND-COLOR: #fff;}
        .lhgdialog_cancle:hover{ padding: 2px 16px;
            line-height: 22px;
            cursor: pointer;
            border: 1px solid #0C60AA;
            margin: 0px 8px 0 0;}
        .div-msg-btn div{right:16px}
    </style>
</head>

<body>

<div id="planAddForm" border="false" class="easyui-panel div-msg" fit="true">
    <%--<div class="easyui-panel" fit="true" id="zhezhao">

    </div>--%>
    <input id="id" name="id" type="hidden"  value="${plan_.id}" />
    <input id="planId" name="planId" type="hidden" value=""/>
    <input id="useObjectId" name="useObjectId" type="hidden" value="${useObjectId}" />
    <input id="useObjectType" name="useObjectType" type="hidden" value="${useObjectType}" />
    <input id="isModifyPage" name="isModifyPage" type="hidden" value="${disabled}" />
    <input id="planNumber" name="planNumber" type="hidden" value="${plan_.planNumber}" />
    <input id="projectId" name="projectId" type="hidden" value="${plan_.projectId}" />
    <input id="parentPlanId" name="parentPlanId" type="hidden" value="${plan_.parentPlanId}" />
    <input id="beforePlanId" name="beforePlanId" type="hidden" value="${plan_.beforePlanId}" />
    <input id="bizCurrent" name="bizCurrent" type="hidden" value="${plan_.bizCurrent}" />
    <input id="preposeList" name="preposeList" type="hidden" value="${plan_.preposeList}" />
    <input id="flowStatus" name="flowStatus" type="hidden" value="${plan_.flowStatus}" />
    <input id="rescLinkInfoList" name="rescLinkInfoList" type="hidden" value="${plan_.rescLinkInfoList}" />
    <input id="parentStartTime" name="parentStartTime" type="hidden" value="${parentStartTime}" />
    <input id="parentEndTime" name="parentEndTime" type="hidden" value="${parentEndTime}" />
    <input id="preposeEndTime" name="preposeEndTime" type="hidden" value="${preposeEndTime}" />
    <input id="departList" name="departList" type="hidden" value="${departList}" />
    <input id="isStandard" name="isStandard" type="hidden" value="${isStandard}" />
    <input id="userList" name="userList" type="hidden" value="${userList}" />
    <input id="userList2" name="userList2" type="hidden" value="${userList2}" />
    <input id="planStartTimeRm" name="planStartTimeRm" type="hidden" value="${planStartTimeRm}" />
    <input id="planEndTimeRm" name="planEndTimeRm" type="hidden" value="${planEndTimeRm}" />
    <input id="ownerShow" name="ownerShow" type="hidden" value="${ownerShow}" />
    <input id="planLevelShow" name="planLevelShow" type="hidden" value="${planLevelShow}" />
    <input id="planLevelList" name="planLevelList" type="hidden" value="${planLevelList}" />
    <input id="departList" name="departList" type="hidden" value="${departList}" />
    <input id="showProjectInfo" name="showProjectInfo" type="hidden" value="" />
    <input id="fromType" name="fromType" type="hidden" value="" />
    <input id="taskNameType" name="taskNameType" type="hidden" value="${plan_.taskNameType}" />
    <input name="preposeIds" id="preposeIds" value="${plan_.preposeIds}" type="hidden" />
    <input name="isPlanChange" id="isPlanChange" value="" type="hidden" />
    <input name="tabCbTemplateId" id="tabCbTemplateId" value="${tabCbTemplateId}" type="hidden" />


    <fd:form id="projLibDocAddForm">
        <input id="uploadPowerFlog" name="uploadPowerFlog" type="hidden" value="${uploadPowerFlog}" />
        <input id="securityLevelFromFile" name="securityLevelFromFile" type="hidden" value="${securityLevelFromFile}" />
        <input id="yanfa" name="yanfa" type="hidden" value="${yanfa}" />
        <input id="treeType" name="treeType" type="hidden" value="1" />
        <input id="fileId" name="fileId" type="hidden" value="${doc.id}" />
        <input id="id" name="id" type="hidden" />
        <input id="projectId" name="projectId"  type="hidden" value="${doc.projectId}" />
        <input id="type" name="type" type="hidden" value="1" />
        <input id="docattachmentName" name="docattachmentName" type="hidden" />
        <input id="docattachmentURL" name="docattachmentURL" type="hidden" />
        <input id="docAttachmentShowName" name="docAttachmentShowName" type="hidden" />
        <input id="docSecurityLevelFrom" name="docSecurityLevelFrom" type="hidden" />
        <input name="invalidIds" id="invalidIds" type="hidden" />
        <input name="useObjectId" id="useObjectId" type="hidden" value = "${useObjectId}" />
        <input name="useObjectType" id="useObjectType" type="hidden" value = "${useObjectType}" />
        <input name="webRoot" id="webRoot" type="hidden" value = "${webRoot}" />
    </fd:form>
<div class="easyui-layout">
    <c:forEach var="lists" items="${listss}" varStatus="cnt">
        <div id="${lists.get(0).get(0).get(0).ext2}"  fit="true">
            <div data-options="region:'north',split:true" style="height:120px;">
                <div class="gla_div" style="position:inherit;margin-bottom: 80px;">
                    <ul>
                        <li>
                            <div class="gla_step">
                                <table class="step_dot">
                                    <tr>
                                        <c:if test="${! empty tabTempList}">
                                            <c:forEach var="ttList" items="${tabTempList}" varStatus="ttCnt">
                                                <c:choose>
                                                    <c:when test="${ttList.ext1 == '1'}">
                                                        <td class="stepFirst"><i></i><div><span>1</span><div class="cont">${ttList.name}</div></div></td>
                                                    </c:when>

                                                    <c:when test="${cnt.index >= ttCnt.index && ttList.ext1 != '1' }">
                                                        <td class="stepMiddle"><i></i><div><span>${ttList.ext1}</span><div class="cont">${ttList.name}</div></div></td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td class="stepEnd"><i></i><div><span>${ttList.ext1}</span><div class="cont">${ttList.name}</div></div></td>
                                                    </c:otherwise>
                                                </c:choose>

                                            </c:forEach>
                                        </c:if>
                                            <%-- <td class="stepFirst"><i></i><div><span>1</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/></div></div></td>
                                             <td class="stepEnd"><i></i><div><span>2</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.input"/></div></div></td>
                                             <td class="stepEnd"><i></i><div><span>3</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.output"/></div></div></td>
                                             <td class="stepEnd"><i></i><div><span>4</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.resource"/></div></div></td>--%>
                                    </tr>
                                </table>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div data-options="region:'center'" fit = "true">
                <c:forEach var="list" items="${lists}">
                    <c:if test="${!empty list.get(0)}">
                        <c:if test="${!empty list.get(0).get(0)}">
                            <c:choose>
                                <c:when test="${list.get(0).get(0).displayUsage == '1'}">
                                    <c:if test="${list.size() == 1 && list.get(0).get(0).control!='8'}">
                                        <c:if test="${list.get(0).get(0).control=='0'}"> <!-- 单行文本 -->

                                            <%-- <c:if test="${list.get(0).get(0).display==enterType || list.get(0).get(0).display=='1'}">--%>
                                            <div id="${list.get(0).get(0).propertyValue}Div">
                                                <c:choose>
                                                    <c:when test="${list.get(0).get(0).readWriteAccess==enterType || list.get(0).get(0).readWriteAccess=='1'}">
                                                        <fd:inputText id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}"
                                                                      required="${list.get(0).get(0).required}" value="${list.get(0).get(0).valueInfo}" ></fd:inputText>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <fd:inputText id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}" readonly="true"
                                                                      required="${list.get(0).get(0).required}" value="${list.get(0).get(0).valueInfo}" ></fd:inputText>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <%-- </c:if>--%>

                                        </c:if>
                                        <c:if test="${list.get(0).get(0).control=='1'}"> <!-- 多行文本 -->
                                            <div id="${list.get(0).get(0).propertyValue}Div">
                                                    <%-- <c:if test="${list.get(0).get(0).display==enterType || list.get(0).get(0).display=='1'}">--%>
                                                <c:choose>
                                                    <c:when test="${list.get(0).get(0).readWriteAccess==enterType || list.get(0).get(0).readWriteAccess=='1'}">
                                                        <fd:inputTextArea id="${list.get(0).get(0).id}"
                                                                          required="${list.get(0).get(0).required}" title="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).valueInfo}" ></fd:inputTextArea>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <fd:inputTextArea id="${list.get(0).get(0).id}" readonly="true"
                                                                          required="${list.get(0).get(0).required}" title="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).valueInfo}" ></fd:inputTextArea>
                                                    </c:otherwise>
                                                </c:choose>
                                                    <%-- </c:if>--%>
                                            </div>
                                        </c:if>
                                        <c:if test="${list.get(0).get(0).control=='2'}"> <!-- 单选 -->
                                            <div id="${list.get(0).get(0).propertyValue}Div">
                                                    <%-- <c:if test="${list.get(0).get(0).display==enterType || list.get(0).get(0).display=='1'}">--%>
                                                <c:choose>
                                                    <c:when test="${list.get(0).get(0).readWriteAccess==enterType || list.get(0).get(0).readWriteAccess=='1'}">
                                                        <input type="radio" name="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).propertyName}" >${list.get(0).get(0).propertyName}</input>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="radio" name="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).propertyName}" readonly="true" >${list.get(0).get(0).propertyName}</input>
                                                    </c:otherwise>
                                                </c:choose>
                                                    <%--</c:if>--%>
                                            </div>

                                        </c:if>
                                        <c:if test="${list.get(0).get(0).control=='3'}"> <!-- 公共选人 -->
                                            <div id="${list.get(0).get(0).propertyValue}Div">
                                                    <%-- <c:if test="${list.get(0).get(0).display==enterType || list.get(0).get(0).display=='1'}">--%>
                                                <c:choose>
                                                    <c:when test="${list.get(0).get(0).readWriteAccess==enterType || list.get(0).get(0).readWriteAccess=='1'}">
                                                        <fd:inputSearchUser id="${list.get(0).get(0).id}" queryMode="in"
                                                                            required="${list.get(0).get(0).required}"
                                                                            title="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).valueInfo}"></fd:inputSearchUser>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <fd:inputSearchUser id="${list.get(0).get(0).id}" queryMode="in"
                                                                            required="${list.get(0).get(0).required}" readonly="true"
                                                                            title="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).valueInfo}"></fd:inputSearchUser>
                                                    </c:otherwise>
                                                </c:choose>
                                                    <%--</c:if>--%>
                                            </div>

                                        </c:if>
                                        <c:if test="${list.get(0).get(0).control=='4'}"> <!-- 日期文本框 -->
                                            <div id="${list.get(0).get(0).propertyValue}Div">
                                                    <%-- <c:if test="${list.get(0).get(0).display==enterType || list.get(0).get(0).display=='1'}">--%>
                                                <c:choose>
                                                    <c:when test="${list.get(0).get(0).readWriteAccess==enterType || list.get(0).get(0).readWriteAccess=='1'}">
                                                        <fd:inputDate id="${list.get(0).get(0).id}" editable="false"
                                                                      required="${list.get(0).get(0).required}" title="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).defaultValue}" ></fd:inputDate>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <fd:inputDate id="${list.get(0).get(0).id}" readonly="true" editable="false"
                                                                      required="${list.get(0).get(0).required}" title="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).defaultValue}" ></fd:inputDate>
                                                    </c:otherwise>
                                                </c:choose>
                                                    <%--</c:if>--%>
                                            </div>

                                        </c:if>

                                        <c:if test="${list.get(0).get(0).control=='9'}"> <!-- 选择框 -->
                                            <div id="${list.get(0).get(0).propertyValue}Div">
                                                <c:if test="${empty list.get(0).get(0).operationEvent}">
                                                    <c:choose>
                                                        <c:when test="${list.get(0).get(0).readWriteAccess==enterType || list.get(0).get(0).readWriteAccess=='1'}">
                                                            <fd:inputSearch id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).valueInfo}" editable="false" searcher="defaul()" />
                                                        </c:when>
                                                        <c:otherwise>
                                                            <fd:inputSearch id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).valueInfo}" readonly="true"
                                                                            editable="false" searcher="defaul()" />
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                                <c:if test="${!empty list.get(0).get(0).operationEvent}">
                                                    <c:choose>
                                                        <c:when test="${list.get(0).get(0).readWriteAccess==enterType || list.get(0).get(0).readWriteAccess=='1'}">
                                                            <fd:inputSearch id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).valueInfo}" editable="false" searcher="${list.get(0).get(0).operationEvent}" />
                                                        </c:when>
                                                        <c:otherwise>
                                                            <fd:inputSearch id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).valueInfo}" readonly="true"
                                                                            editable="false" searcher="${list.get(0).get(0).operationEvent}" />
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                            </div>
                                        </c:if>

                                        <c:if test="${list.get(0).get(0).control=='10'}"> <!-- 下拉框(文本可编辑) -->
                                            <div id="${list.get(0).get(0).propertyValue}Div">
                                                    <%-- <c:if test="${list.get(0).get(0).display==enterType || list.get(0).get(0).display=='1'}">--%>
                                                <c:choose>
                                                    <c:when test="${list.get(0).get(0).readWriteAccess==enterType || list.get(0).get(0).readWriteAccess=='1'}">
                                                        <fd:combobox id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}"  name="${list.get(0).get(0).propertyName}" required="${list.get(0).get(0).required}" url="${list.get(0).get(0).loadUrl}" panelMaxHeight="200"
                                                                     valueField="${list.get(0).get(0).valueField}" textField="${list.get(0).get(0).textField}" value="${list.get(0).get(0).valueInfo}" prompt="" selectedValue="${list.get(0).get(0).valueInfo}">
                                                        </fd:combobox>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <fd:combobox id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}"  name="${list.get(0).get(0).propertyName}" readonly="true"  url="${list.get(0).get(0).loadUrl}" panelMaxHeight="200"
                                                                     valueField="${list.get(0).get(0).valueField}"  textField="${list.get(0).get(0).textField}" value="${list.get(0).get(0).valueInfo}" prompt="" selectedValue="${list.get(0).get(0).valueInfo}">
                                                        </fd:combobox>
                                                    </c:otherwise>
                                                </c:choose>
                                                    <%--</c:if>--%>
                                            </div>
                                        </c:if>

                                        <c:if test="${list.get(0).get(0).control=='5'}"> <!-- 下拉框 -->
                                            <div id="${list.get(0).get(0).propertyValue}Div">
                                                    <%-- <c:if test="${list.get(0).get(0).display==enterType || list.get(0).get(0).display=='1'}">--%>
                                                <c:choose>
                                                    <c:when test="${list.get(0).get(0).readWriteAccess==enterType || list.get(0).get(0).readWriteAccess=='1'}">
                                                        <fd:combobox id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}"  name="${list.get(0).get(0).propertyName}" required="${list.get(0).get(0).required}" url="${list.get(0).get(0).loadUrl}" panelMaxHeight="200"
                                                                     editable="false" valueField="${list.get(0).get(0).valueField}" textField="${list.get(0).get(0).textField}" value="${list.get(0).get(0).valueInfo}" prompt="" selectedValue="${list.get(0).get(0).valueInfo}">
                                                        </fd:combobox>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <fd:combobox id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}"  name="${list.get(0).get(0).propertyName}" readonly="true"  url="${list.get(0).get(0).loadUrl}" panelMaxHeight="200"
                                                                     editable="false" valueField="${list.get(0).get(0).valueField}"  textField="${list.get(0).get(0).textField}" value="${list.get(0).get(0).valueInfo}" prompt="" selectedValue="${list.get(0).get(0).valueInfo}">
                                                        </fd:combobox>
                                                    </c:otherwise>
                                                </c:choose>
                                                    <%--</c:if>--%>
                                            </div>
                                        </c:if>
                                    </c:if>



                                    <c:if test="${list.size() >= 1 && list.get(0).get(0).control=='8'}">
                                        <c:forEach var="jyj" items="${list}">
                                            <div id="${jyj.get(0).propertyName}Div" style="float:left">
                                                <!-- 新增加是否显示按钮判断isShowButton-->
                                                <fd:linkbutton iconCls="${jyj.get(0).format}" value="${jyj.get(0).propertyName}" onclick="${jyj.get(0).operationEvent}"></fd:linkbutton>
                                            </div>
                                        </c:forEach>
                                    </c:if>

                                    <%--                   <c:if test="${list.size() >= 1 && list.get(0).get(0).control=='8'}">
                                                           <div class="jk"></div>
                                                       </c:if>--%>

                                    <c:if test="${list.size() > 1 && list.get(0).get(0).control!='8'}">
                                        <div class="oLine" >
                                            <fd:datagrid id="${list.get(0).get(0).id}" checkbox="true" checked="true"
                                                         checkOnSelect="true" idField="id" fit="false" width="98%" height="380px" url=""
                                                         fitColumns="true" pagination="false">
                                            </fd:datagrid>
                                                <%--<fd:panel  border="false" title="${list.get(0).get(0).propertyName}" fit="true" collapsed="false" width="100%" height="85%" >
                                                    <div>
                                                        <table id="${list.get(0).get(0).id}"></table>
                                                    </div>
                                                </fd:panel>--%>

                                        </div>
                                        <script>

                                            function loadDatagrid_${cnt.index}(){
                                                $('#${list.get(0).get(0).id}').datagrid({
                                                    url: '',
                                                    width: 880,
                                                    height: 380,
                                                    pagination : false,
                                                    //fit:true,
                                                    //fitColumns:true,
                                                    columns: [[
                                                        <c:forEach var="col" items="${list.get(1)}" varStatus="status">
                                                        {
                                                            <%--   <c:choose>
                                                                <c:when test="${empty col.format}">
                                                                   field: '${col.propertyValue}',
                                                                   title: '${col.propertyName}',
                                                                   width: 760/${list.get(1).size()}
                                                                </c:when>
                                                                <c:otherwise>
                                                                   field: '${col.propertyValue}',
                                                                   title: '${col.propertyName}',
                                                                   width: 760/${list.get(1).size()},
                                                                   formatter:function(value,row,index){
                                                                       ${col.format}
                                                                   }
                                                               </c:otherwise>
                                                            </c:choose>--%>

                                                            <c:choose>
                                                            <c:when test="${empty col.format}">
                                                            field: '${col.propertyValue}',
                                                            title: '${col.propertyName}',
                                                            width: 880/${list.get(1).size()}
                                                                </c:when>
                                                                <c:otherwise>
                                                                field: '${col.propertyValue}',
                                                    title: '${col.propertyName}',
                                                    width: 880/${list.get(1).size()},
                                                    formatter:${col.format}
                                                </c:otherwise>
                                                </c:choose>

                                            }<c:if test="${list.get(1)!=null && fn:length(list.get(1))-1 > status.index}">, </c:if>
                                                </c:forEach>
                                            ]]
                                            });
                                            }
                                        </script>

                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <%--<c:if test="${list.get(0).get(0).display==enterType || list.get(0).get(0).display=='1'}">--%>
                                    <iframe id="${list.get(0).get(0).id}" src="${list.get(0).get(0).loadUrl}" width="750" height="380" style="border:medium none"></iframe>
                                    <%-- </c:if>--%>

                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:if>

                </c:forEach>
                    <div class="div-msg-btn">
                    <div class="ui_buttons">
                        <c:choose>
                            <c:when test="${cnt.first && (! empty fromType && fromType == 'update') }">
                                <c:if test="${!cnt.last}">
                                    <fd:linkbutton onclick="nextOne('${lists.get(0).get(0).get(0).ext2}','${cnt.index}')" value="{com.glaway.ids.pm.project.plan.nextone}" classStyle="button_nor" />
                                </c:if>
                                <fd:linkbutton onclick="addSetTimeOut('${lists.get(0).get(0).get(0).ext2}')" value="{com.glaway.ids.pm.project.plan.finish}" classStyle="button_nor" />
                                <fd:linkbutton  onclick="closePlan()" value="{com.glaway.ids.common.btn.cancel}" classStyle="ui_state_highlight lhgdialog_cancle" />
                            </c:when>
                            <c:otherwise>
                                <fd:linkbutton onclick="beforeOne('${lists.get(0).get(0).get(0).ext2}','${cnt.index}')" value="{com.glaway.ids.pm.project.plan.beforeone}" classStyle="button_nor" />
                                <c:if test="${!cnt.last}">
                                    <fd:linkbutton onclick="nextOne('${lists.get(0).get(0).get(0).ext2}','${cnt.index}')" value="{com.glaway.ids.pm.project.plan.nextone}" classStyle="button_nor" />
                                </c:if>
                                <fd:linkbutton onclick="addSetTimeOut('${lists.get(0).get(0).get(0).ext2}')" value="{com.glaway.ids.pm.project.plan.finish}" classStyle="button_nor" />
                                <fd:linkbutton  onclick="closePlan()" value="{com.glaway.ids.common.btn.cancel}" classStyle="ui_state_highlight lhgdialog_cancle"  />
                                <%--<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
                                                 callback="hideDiaLog"></fd:dialogbutton>--%>
                            </c:otherwise>
                        </c:choose>

                    </div>
                </div>
            </div>
        </div>


    </c:forEach>
</div>
    <fd:dialog id="openSelectInputsDialog" width="800px" height="450px"
               modal="true" title="选择输入项">
        <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
                         callback="openSelectConfigOkFunction"></fd:dialogbutton>
        <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
                         callback="hideDiaLog"></fd:dialogbutton>
    </fd:dialog>
    <fd:dialog id="inheritDialog" width="400px" height="300px" modal="true" title="{com.glaway.ids.pm.project.plan.inheritParentDocument}">
        <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addInheritDialog"></fd:dialogbutton>
        <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
    </fd:dialog>
    <fd:dialog id="addDeliverableDialog" width="800px" height="450px" modal="true" title="新增输出">
        <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addDeliverableDialog"></fd:dialogbutton>
        <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
    </fd:dialog>
    <fd:dialog id="addResourceDialog" width="800px" height="350px" modal="true" title="新增计划资源">
        <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addResourceDialog"></fd:dialogbutton>
        <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
    </fd:dialog>
    <fd:dialog id="taskDocCheckLineDialog" width="1000px" height="500px" modal="true" title="项目库关联">
        <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="taskDocCheckLineDialog"></fd:dialogbutton>
        <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
    </fd:dialog>

    <fd:dialog id="planInputsDialog" width="800px" height="580px" modal="true" title="选择来源计划">
        <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="planInputsDialog"></fd:dialogbutton>
        <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
    </fd:dialog>
    <fd:dialog id="addLocalFileDialog" width="400px" height="180px" modal="true" title="新增本地文档">
        <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addLocalFileDialog"></fd:dialogbutton>
        <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
    </fd:dialog>

    <fd:dialog id="resourceDialog" width="900px" height="720px" modal="true" title="资源图" maxFun="addInheritDialogtrue">
    </fd:dialog>
    <fd:dialog id="plmDialog" width="800px" height="580px" modal="true" title="选择交付物">
        <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addPlmDialog"></fd:dialogbutton>
        <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
    </fd:dialog>
    <fd:dialog id="preposePlanDialog" width="860px" height="580px" modal="true" title="选择前置计划">
        <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="preposePlanDialog"></fd:dialogbutton>
        <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
    </fd:dialog>

    <fd:dialog id="modifyResourceDialog" width="780px" height="280px" modal="true" title="{com.glaway.ids.pm.project.plan.resource.editResource}">
        <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addResourceDialog"></fd:dialogbutton>
        <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
    </fd:dialog>

</div>

<input type="hidden" id = "tabTemplateList" value = "${tabTemplateList}" />
</body>

<%--<c:if test="${! empty objectPathList}">
<c:forEach var="pathList" items="${objectPathList}">
<script src="${pathList}"></script>
</c:forEach>
</c:if>--%>

<script type="text/javascript">

    var save_flag = true;
    function beforeOne(curIndexId,curIndex){
        if(curIndex == 0 || curIndex == "0"){
            $('#page1').css('display','');
            $('#page2').css('display','none');

        }else{
            var beforePageIndex ;
            var tabTemplateList = ${tabTemplateList};
            for(var i in tabTemplateList){
                if(i ==parseInt(curIndex)-1 ){
                    beforePageIndex = tabTemplateList[parseInt(curIndex)-1].id;
                    break;
                }
            }
            $("#"+curIndexId).css('display','none');
            $("#"+beforePageIndex).css('display','');
        }

    }

    function nextOne(curIndexId,curIndex){

        var nextPageIndex ;
        var tabTemplateList = ${tabTemplateList};
        var currentNameSpace;
        var nextNameSpace;
        for(var i in tabTemplateList){
            if(i ==parseInt(curIndex)+1 ){
                debugger;
                nextPageIndex = tabTemplateList[parseInt(curIndex)+1].id;
                currentNameSpace = tabTemplateList[parseInt(curIndex)].ext2;
                currentNameSpace = window[currentNameSpace];
                nextNameSpace = tabTemplateList[parseInt(curIndex)+1].ext2;
                nextNameSpace = window[nextNameSpace];
                break;
            }
        }

    //    var aa=  currentNameSpace.goNextPage.bind( currentNameSpace,curIndexId ,nextPageIndex,parseInt(curIndex)+1);
        var flg = currentNameSpace.goNextPage(curIndexId ,nextPageIndex,parseInt(curIndex)+1);
        // var flg = aa();
        if(flg){
            $("#"+curIndexId).css('display','none');
            $("#"+nextPageIndex).css('display','');
            evalLoadDatagrid(parseInt(curIndex)+1);
            setTimeout(function(){
                nextNameSpace.loadData('');
               /* var bb=  nextNameSpace.loadData.bind( nextNameSpace,'');
                bb();*/
            },50);

        }

    }


    function initPages(){
        <c:if test="${! empty objectPathList}">
            <c:forEach var="pathList" items="${objectPathList}">
                loadjs('${pathList}');
            </c:forEach>
        </c:if>


        var tabTemplateList = ${tabTemplateList};
        for(var i in tabTemplateList){
            if(i != "0" || i != 0){
                $('#'+tabTemplateList[i].id).css('display','none')
            }
        }
    }

    var map ={};

    function initData(){
        var operation = ${operation};
        for(var i in operation){
            try{
                for(var j in operation[i]){
                    if($("#"+i).hasClass("glaway-combo  combobox-f combo-f textbox-f")){
                        if(j == 'onChange'){
                            map[i] = operation[i][j];
                            $("#"+i).combobox({ onChange:function(newVal, oldVal){
                                    eval(map[$(this).attr("id")]);
                                }});
                        }
                    }else if($("#"+i).hasClass("glaway-input    textbox-f")){   //样式中间多余的空格勿删
                        if(j == 'onChange'){
                            map[i] = operation[i][j];
                            $("#"+i).textbox({ onChange:function(newVal, oldVal){
                                    eval(map[$(this).attr("id")]);
                                }});
                        }
                    }else if($("#"+i).hasClass("glaway-input_datebox   datebox-f combo-f textbox-f")){  //样式中间多余的空格勿删
                        if(j == 'onChange'){
                            map[i] = operation[i][j];
                            $("#"+i).datebox({ onChange:function(newVal, oldVal){
                                    eval(map[$(this).attr("id")]);
                                }});
                        }
                    }
                }

            }catch(e){

            }
        }


    }

    function setDefaultItem() {
        var planDefalut = '${planDefault}';
        planDefalut = $.parseJSON(planDefalut);
        for(var p in planDefalut){
            try{
                $("#Plan-"+p).combobox('setText',planDefalut[p]);
            }catch(e){

            }
            try{
                $("#Plan-"+p).textbox('setValue',planDefalut[p]);
            }catch(e){

            }
            try{
                $("#Plan-"+p).datebox('setText',planDefalut[p]);
            }catch(e){

            }
        }
    }

    function addSetTimeOut(){
        if(save_flag){
            doSaveData();//等待0.5秒，让分类树结构加载出来
        }

    }

    //基本信息，内置页签信息保存
    function doSaveData(){
        save_flag = false;
        var objectPathStr = ${objectPathStr};
        debugger
        //数据校验
        for(var i in objectPathStr){
            var usePath = objectPathStr[i];
            usePath = window[usePath];
            try{
                if (!usePath.checkData()) {
                    window.top.progress("close");
                    return false;
                }
            }catch (e) {

            }
        }
       if (objectPathStr.length > 0) {
           var usePath = objectPathStr[0];
           usePath = window[usePath];
           try {
               usePath.doSaveData();
           } catch (e) {

           }
       }
    }

    //自定义页签信息保存
    function saveDefData(){
        var objectPathStr = ${objectPathStr};
        if (objectPathStr.length > 1) {
            for (var i = 1; i < objectPathStr.length; i++) {
                var usePath = objectPathStr[i];
                usePath = window[usePath];
                try {
                    usePath.doSaveData();
                } catch (e) {

                }
            }
        }
    }


    function openSelectConfigOkFunction(iframe){
        var flg=iframe.getLoadData();
        if(flg && TC002.loadData(flg)){
            return true;
        }
        return false;
    }

    function addInheritDialog(iframe){
        var flg=iframe.add();
        if(flg && TC003.loadData(flg)){
            return true;
        }
        return false;
    }

    function addPlmDialog(iframe){
        var flag =iframe.getLoadData();
        if(flag && TC002.loadData(flag)){
            return true;
        }
        return false;
    }

    function addDeliverableDialog(iframe){
        var flg=iframe.getLoadData();
        if(flg && TC003.loadData(flg)){
            return true;
        }
        return false;
    }

    function addResourceDialog(iframe){
        var flg=iframe.getLoadData();
        if(flg && TC004.loadData(flg)){
            currentIndex = undefined;
            editIndex = undefined;
            return true;
        }
        return false;
    }


    function evalLoadDatagrid(curIndex){
        try{
            eval("loadDatagrid_"+curIndex+"();");
            this["loadDatagrid_"+curIndex+""] = function(){};
        }catch(e){

        }
    }

    function taskDocCheckLineDialog(iframe) {
        //iframe = this.iframe.contentWindow;
        if (iframe.validateSelectedNum()) {
            var docId = iframe.getSelectionsId();
            var folderId = iframe.$("#folderId").val();
            var rowId = iframe.$("#rowId").val();
            var rows=$('#deliverablesInfoList').datagrid('getSelections');
            var row=rows[0];
            $.ajax({
                url : 'deliverablesInfoController.do?updateInputs',
                type : 'post',
                data : {
                    fileId : docId,
                    folderId : folderId,
                    rowId : rowId,
                    projectId : $('#projectId').val(),
                    useObjectId : $('#useObjectId').val(),
                    useObjectType : $('#useObjectType').val()
                },
                cache : false,
                success : function(data) {
                    var d = $.parseJSON(data);
                    if (d.success) {
                        TC002.loadData('');
                    }
                }
            });
            return true;
        } else {
            return false;
        }
    }


    function planInputsDialog(iframe){
        if (iframe.validateSelectedBeforeSave()) {
            var row = iframe.$('#planlist').datagrid('getSelections');
            var planId = row[0].id;
            var tempId = iframe.$("#tempId").val();
            var useObjectId = iframe.$("#useObjectId").val();
            var inputsName = iframe.$("#inputsName").val();

            $.ajax({
                type:'POST',
                data:{
                    planId:planId,
                    tempId : tempId,
                    useObjectId : useObjectId,
                    inputsName : inputsName
                },
                url : 'deliverablesInfoController.do?setPlanInputs',
                cache:false,
                success : function(data){
                    TC002.loadData('');
                    $('#inputsList').datagrid('clearSelections');
                }
            });
        }else{
            return false;
        }
    }


    function addLocalFileDialog(iframe){
        var attachmentName = iframe.$("#docattachmentName1").val();
        var dowmLoadUrl = iframe.$("#docattachmentURL1").val();
        var attachmentShowName = iframe.$("#docAttachmentShowName1").val();
        var invalidIds = iframe.$("#invalidIds1").val();

        $("#docattachmentName").val(attachmentName);
        $("#docattachmentURL").val(dowmLoadUrl);
        $("#docAttachmentShowName").val(attachmentShowName);
        $("#invalidIds").val(invalidIds);

        $.ajax({
            type : "POST",
            url : "projLibController.do?doAddForLocalDoc&attachmentNames="+ attachmentName+"&dowmLoadUrls="+ attachmentName,
            async : false,
            data :   $('#projLibDocAddForm').serialize(),
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    TC002.loadData('');
                    return true;
                }else{
                    return false;
                }


            }
        });
        return true;
    }

/*
    var loadjs = function (path,callback){
        var script=  document.createElement('script');
        var head = window.document.head || window.document.getElementsByTagName("head");

        script.type ="text/javascript";
        script.onload = script.onreadystatechange = function() {
            if (!this.readyState || this.readyState === "loaded" ||this.readyState === "complete" ) {
                if("function" ==  $.type(callback)){
                    callback();
                }
            } };
        script.src =path;
        head.appendChild(script);
    }*/

    var loadjs = function (path){
        $.getScript(""+path+"",function(){
            //alert("加载js成功！");
        })
    }

    function setPlanName(planName){
        $("#Plan-planName").textbox('setText',planName);
    }



    //勿删
    function getVersion(val, row, value){
        return val;
    }

    //勿删
    function getStatus(val, row, value){
        return val;
    }


    function preposePlanDialog(iframe){
        debugger;
        var preposeIds;
        var preposePlans;
        var preposeEndTime;
        var selectedId = iframe.mygrid_planPreposeList.getSelectedRowId();
        if(selectedId != undefined && selectedId != null && selectedId != '' ){
            var selectedIdArr = selectedId.split(",");
            if(selectedIdArr.length > 0){
                for(var i=0;i<selectedIdArr.length;i++){
                    var id = selectedIdArr[i];
                    if(preposeIds != null && preposeIds != '' && preposeIds != undefined){
                        preposeIds = preposeIds + ',' + id;
                    }else{
                        preposeIds = id;
                    }
                    var planName = iframe.mygrid_planPreposeList.getRowAttribute(id,'displayName');
                    if(preposePlans != null && preposePlans != '' && preposePlans != undefined){
                        preposePlans = preposePlans + ',' + planName;
                    }else{
                        preposePlans = planName;
                    }
                    var planEndTime = iframe.mygrid_planPreposeList.getRowAttribute(id,'planEndTime');
                    if(preposeEndTime != null && preposeEndTime != '' && preposeEndTime != undefined){
                        if(preposeEndTime < planEndTime){
                            preposeEndTime = planEndTime;
                        }
                    }else{
                        preposeEndTime = planEndTime;
                    }
                }
            }
        }
        savePreposePlan(preposeIds, preposePlans, preposeEndTime);
    }


    /**
     * 前置计划选择后，更新页面值（每次均覆盖）
     */
    function savePreposePlan(preposeIds, preposePlans, preposeEndTime) {
        debugger;
        var projectId = $('#projectId').val();
        if (preposeIds != null && preposeIds != ''
            && preposeIds != undefined) {
            $('#preposeIds').val(preposeIds);
        } else {
            $('#preposeIds').val('');
        }
        if (preposePlans != null && preposePlans != ''
            && preposePlans != undefined) {
            $('#Plan-preposeIds').textbox("setValue",preposePlans);
        } else {
            $('#Plan-preposeIds').textbox("setValue","");
        }
        // 前置计划的最晚完成时间
        if (preposeEndTime != null && preposeEndTime != ''
            && preposeEndTime != undefined) {
            $('#preposeEndTime').val(preposeEndTime);

            $.ajax({
                url : 'planController.do?getNextDay',
                type : 'post',
                data : {
                    preposeEndTime : preposeEndTime,
                    projectId :projectId
                },
                cache : false,
                success : function(data) {
                    if (data != null) {
                        var d = $.parseJSON(data);
                        if(d.success == true){
                            preposeEndTime = d.obj;
                            $('#planStartTime').datebox("setValue",preposeEndTime);
                        }
                        else{
                        }
                    }
                    else{
                    }
                }
            });


        } else {
            $('#preposeEndTime').val('');
            $('#planStartTime').datebox("setValue",$("#planStartTimeRm").val());
        }
    }

</script>



</html>