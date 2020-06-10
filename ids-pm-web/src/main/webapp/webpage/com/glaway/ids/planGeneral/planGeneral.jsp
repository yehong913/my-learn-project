<%@ page language="java" import="java.util.*"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <style>
        .oLine {
            width: 100%;
            height: 400px;
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

<body>
<c:if test="${code != undefined}">
    <script src="webpage/com/glaway/ids/planGeneral/js/${code}.js"></script>
</c:if>
<c:forEach var="item" items="${results}">
    <input  id="${item.key}"  type="hidden" value="${item.value}" />
</c:forEach>
<input  id="riskproblems_Nginx" name="riskproblems_Nginx" type="hidden" value="${riskproblems_Nginx}" />
 <input  id="dataGrideId_${index}" name="dataGrideId" type="hidden" value="${dataGrideId}" />

	<c:forEach var="list" items="${lists}">
        <div style="margin-bottom: 5px;"></div>
	 <c:if test="${!empty list.get(0)}">
	  <c:if test="${!empty list.get(0).get(0)}">
                <c:if test="${list.size() == 1 && list.get(0).get(0).control!='8'}">
                <!--  强制只读 -->					
                    <c:if test="${list.get(0).get(0).control=='0'}"> <!-- 单行文本 -->
             	   		<div id="${list.get(0).get(0).propertyValue}Div">
                          <c:choose>
           			           <c:when test="${(list.get(0).get(0).readWriteAccess==enterType || list.get(0).get(0).readWriteAccess=='1') && empty onlyReadonly && '1'!= isEnableFlag}">
                                  <fd:inputText id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}"
                                      required="${list.get(0).get(0).required}" value="${list.get(0).get(0).valueInfo}" ></fd:inputText>  
                                 </c:when>	                                   
           			           <c:otherwise>
   			                   	  <fd:inputText id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}" readonly="true"
                             		  required="${list.get(0).get(0).required}" value="${list.get(0).get(0).valueInfo}" ></fd:inputText>                         
           				       </c:otherwise>
           			       </c:choose> 
           			   </div>     				         			   
                    </c:if>
                    <c:if test="${list.get(0).get(0).control=='1'}"> <!-- 多行文本 -->
                        <div id="${list.get(0).get(0).propertyValue}Div">
                          <c:choose>
           			           <c:when test="${(list.get(0).get(0).readWriteAccess==enterType || list.get(0).get(0).readWriteAccess=='1') && empty onlyReadonly && '1'!= isEnableFlag}">
                                   <fd:inputTextArea id="${list.get(0).get(0).id}"
                                          required="${list.get(0).get(0).required}" title="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).valueInfo}" ></fd:inputTextArea>   
                                 </c:when>	                                   
           			           <c:otherwise>
   			                   	   <fd:inputTextArea id="${list.get(0).get(0).id}" readonly="true"
                                          required="${list.get(0).get(0).required}" title="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).valueInfo}" ></fd:inputTextArea>                   
           				       </c:otherwise>
           			       </c:choose> 
           			    </div>                    
                    </c:if>
                    <c:if test="${list.get(0).get(0).control=='2'}"> <!-- 单选 -->
                        <div id="${list.get(0).get(0).propertyValue}Div">
                          <c:choose>
           			           <c:when test="${(list.get(0).get(0).readWriteAccess==enterType || list.get(0).get(0).readWriteAccess=='1') && empty onlyReadonly && '1'!= isEnableFlag}">
                                  <input type="radio" name="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).propertyName}" >${list.get(0).get(0).propertyName}</input>
                                 </c:when>	                                   
           			           <c:otherwise>
   			                   	  <input type="radio" name="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).propertyName}" readonly="true" >${list.get(0).get(0).propertyName}</input>   
           				       </c:otherwise>
           			       </c:choose>  
           			    </div>
                    </c:if>
                    <c:if test="${list.get(0).get(0).control=='3'}"> <!-- 公共选人 -->
                       <div id="${list.get(0).get(0).propertyValue}Div">
                          <c:choose>
           			           <c:when test="${(list.get(0).get(0).readWriteAccess==enterType || list.get(0).get(0).readWriteAccess=='1') && empty onlyReadonly && '1'!= isEnableFlag}">
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
           			    </div>
                    </c:if>
                    <c:if test="${list.get(0).get(0).control=='4'}"> <!-- 日期文本框 -->
                        <div id="${list.get(0).get(0).propertyValue}Div">
                          <c:choose>
           			           <c:when test="${(list.get(0).get(0).readWriteAccess==enterType || list.get(0).get(0).readWriteAccess=='1') && empty onlyReadonly && '1'!= isEnableFlag}">
                                 <fd:inputDate id="${list.get(0).get(0).id}" editable="false"
                                      required="${list.get(0).get(0).required}" title="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).defaultValue}" ></fd:inputDate>
                                 </c:when>	                                   
           			           <c:otherwise>
   			                   	  <fd:inputDate id="${list.get(0).get(0).id}" readonly="true" editable="false"
                                      required="${list.get(0).get(0).required}" title="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).defaultValue}" ></fd:inputDate>
           				       </c:otherwise>
           			       </c:choose>
           			    </div>
                    </c:if>
                    <c:if test="${list.get(0).get(0).control=='10'}"> <!-- 下拉框(文本可编辑) -->
                        <div id="${list.get(0).get(0).propertyValue}Div">
                                <%-- <c:if test="${list.get(0).get(0).display==enterType || list.get(0).get(0).display=='1'}">--%>
                            <c:choose>
                                <c:when test="${(list.get(0).get(0).readWriteAccess==enterType || list.get(0).get(0).readWriteAccess=='1') && empty onlyReadonly && '1'!= isEnableFlag}">
                                    <fd:combobox id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}"  name="${list.get(0).get(0).propertyName}" required="${list.get(0).get(0).required}" url="${list.get(0).get(0).loadUrl}"
                                                 valueField="${list.get(0).get(0).valueField}" textField="${list.get(0).get(0).textField}" value="${list.get(0).get(0).valueInfo}" prompt="" selectedValue="${list.get(0).get(0).valueInfo}">
                                    </fd:combobox>
                                </c:when>
                                <c:otherwise>
                                    <fd:combobox id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}"  name="${list.get(0).get(0).propertyName}" readonly="true"  url="${list.get(0).get(0).loadUrl}"
                                                 valueField="${list.get(0).get(0).valueField}"  textField="${list.get(0).get(0).textField}" value="${list.get(0).get(0).valueInfo}" prompt="" selectedValue="${list.get(0).get(0).valueInfo}">
                                    </fd:combobox>
                                </c:otherwise>
                            </c:choose>
                                <%--</c:if>--%>
                        </div>
                    </c:if>

                    <c:if test="${list.get(0).get(0).control=='11'}"> <!-- 数字微调框 -->
                        <div id="${list.get(0).get(0).propertyValue}Div">
                                <%-- <c:if test="${list.get(0).get(0).display==enterType || list.get(0).get(0).display=='1'}">--%>
                            <c:choose>
                                <c:when test="${list.get(0).get(0).readWriteAccess==enterType || list.get(0).get(0).readWriteAccess=='1'}">
                                    <fd:inputNumberSpinner id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}" value="0"  min="-99" max = "99"
                                                           increment="1" suffix = "天"></fd:inputNumberSpinner>
                                </c:when>
                                <c:otherwise>
                                    <fd:inputNumberSpinner id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}"  readonly="true" value="0"  min="-99" max = "99"
                                                           increment="1" suffix = "天"></fd:inputNumberSpinner>
                                </c:otherwise>
                            </c:choose>
                                <%--</c:if>--%>
                        </div>
                    </c:if>

                    <c:if test="${list.get(0).get(0).control=='5'}"> <!-- 下拉框（文本不可编辑） -->
                        <div id="${list.get(0).get(0).propertyValue}Div">
                                <%-- <c:if test="${list.get(0).get(0).display==enterType || list.get(0).get(0).display=='1'}">--%>
                            <c:choose>
                                <c:when test="${(list.get(0).get(0).readWriteAccess==enterType || list.get(0).get(0).readWriteAccess=='1') && empty onlyReadonly && '1'!= isEnableFlag}">
                                    <fd:combobox id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}"  name="${list.get(0).get(0).propertyName}" required="${list.get(0).get(0).required}" url="${list.get(0).get(0).loadUrl}"
                                                 editable="false" valueField="${list.get(0).get(0).valueField}" textField="${list.get(0).get(0).textField}" value="${list.get(0).get(0).valueInfo}" prompt="" selectedValue="${list.get(0).get(0).valueInfo}">
                                    </fd:combobox>
                                </c:when>
                                <c:otherwise>
                                    <fd:combobox id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}"  name="${list.get(0).get(0).propertyName}" readonly="true"  url="${list.get(0).get(0).loadUrl}"
                                                 editable="false" valueField="${list.get(0).get(0).valueField}"  textField="${list.get(0).get(0).textField}" value="${list.get(0).get(0).valueInfo}" prompt="" selectedValue="${list.get(0).get(0).valueInfo}">
                                    </fd:combobox>
                                </c:otherwise>
                            </c:choose>
                                <%--</c:if>--%>
                        </div>
                    </c:if>
                                        
                </c:if>
                
                <c:if test="${list.get(0).get(0).control=='9'}"> <!-- 选择框 -->
				    <div id="${list.get(0).get(0).propertyValue}Div">
				
				            <%-- <c:if test="${list.get(0).get(0).display==enterType || list.get(0).get(0).display=='1'}">--%>
				        <c:choose>
				            <c:when test="${(list.get(0).get(0).readWriteAccess==enterType || list.get(0).get(0).readWriteAccess=='1')  && empty onlyReadonly && '1'!= isEnableFlag}">
				                <fd:inputSearch id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).valueInfo}" editable="false" searcher="${list.get(0).get(0).operationEvent}" />
				            </c:when>
				            <c:otherwise>
				                <fd:inputSearch  readonly="true" id="${list.get(0).get(0).id}" title="${list.get(0).get(0).propertyName}" value="${list.get(0).get(0).valueInfo}"
				                                editable="false" />
				            </c:otherwise>
				        </c:choose>
				            <%--</c:if>--%>
				    </div>
				</c:if>

                <c:if test="${list.size() >= 1 && list.get(0).get(0).control=='8' && '1'!= isEnableFlag && empty onlyReadonly}">
                    <c:forEach var="jyj" items="${list}"> <!-- 按钮 -->
                        <c:if test="${! empty jyj.get(0).buttonDivId}">
                            <div id="${jyj.get(0).buttonDivId}" style="float:left">
                                 <!-- 新增加是否显示按钮判断isShowButton-->
                                 <fd:linkbutton iconCls="${jyj.get(0).format}" value="${jyj.get(0).propertyName}" onclick="${jyj.get(0).operationEvent}"></fd:linkbutton>
                            </div>
                        </c:if>
                        <c:if test="${empty jyj.get(0).buttonDivId}">
                            <div id="${jyj.get(0).propertyName}Div" style="float:left">
                                <!-- 新增加是否显示按钮判断isShowButton-->
                                <fd:linkbutton iconCls="${jyj.get(0).format}" value="${jyj.get(0).propertyName}" onclick="${jyj.get(0).operationEvent}"></fd:linkbutton>
                            </div>
                        </c:if>
                    </c:forEach>                   
                </c:if>

               

                <c:if test="${list.size() > 1 && list.get(0).get(0).control!='8'}">
		                    <div class="oLine">
		                        <fd:datagrid id="${list.get(0).get(0).id}"  checkbox="true"  fitColumns="true"  checked="true" checkOnSelect="true" idField="id" fit="false" width="100%" height="400px" pagination="true">
		               			 </fd:datagrid>
		                    </div>
							<script>
		                    	$('#${list.get(0).get(0).id}').datagrid({
		                            width: '100%',
		                            height: '460px',
		                            columns: [[
		                                <c:forEach var="col" items="${list.get(1)}" varStatus="status">
		                                {
			                             <c:choose>
			             			           <c:when test="${empty col.format}">
				                                    field: '${col.propertyValue}',
				                                    title: '${col.propertyName}',
				                                    width: 860/${list.get(1).size()}
			                                   </c:when>	                                   
			             			           <c:otherwise>
				             			          field: '${col.propertyValue}',
				                                  title: '${col.propertyName}',
				                                  width: 860/${list.get(1).size()},
				                                  formatter:${col.format}
			             				       </c:otherwise>
			             			       </c:choose>  
		                                }<c:if test="${list.get(1)!=null && fn:length(list.get(1))-1 > status.index}">, </c:if>
		                                </c:forEach>
		                            ]]
		                        });
		                    </script>
                </c:if>
                </c:if>
                </c:if>
            </c:forEach> 

	    <c:if test="${showBasicButtom == 'true' && isEnableFlag !=1 }">         
		    <div class="div-msg-btn" id="saveBaseInfoButton" style="display: none">
				<div >
					<fd:linkbutton value="{com.glaway.ids.common.btn.save}" classStyle="button_nor" id="saveBaseInfo" onclick="saveBaseInfo()"/>
				</div>
			</div>
		</c:if>
    <c:if test="${fromType == 'DeliverablesInfo'}"> 
<%--     	<fd:dialog id="taskDocCheckLineDialog" width="1000px" height="500px" modal="true" title="{com.glaway.ids.pm.project.task.taskdetail.selectDeliverables}" zIndex="4300">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="taskDocCheckLineDialog"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog> --%>
		
		<fd:dialog id="projLibSubimtDialog" width="800px" height="600px" modal="true" title="{com.glaway.ids.pm.project.task.taskdetail.uploadAttachment}">
		  	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="projLibSubimt"></fd:dialogbutton>
		  	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		
		<fd:dialog id="openDocumentInfoDialog" width="1100px" height="650px" modal="true" title="{com.glaway.ids.pm.project.plan.basicLine.showDocDetail}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
				
		<fd:dialog id="submitDialogForDocCheckLine" width="730px" height="260px" modal="true"
			title="{com.glaway.ids.pm.project.projectmanager.submit}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="beOk"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="beCancel"></fd:dialogbutton>
		</fd:dialog>
	</c:if>                   
	<fd:dialog id="resourceDialog" width="900px" height="800px" 
			modal="true" title="{com.glaway.ids.pm.project.plan.resourcePicture}" maxFun="true">
	</fd:dialog>
	<c:if test="${fromType == 'Inputs'}">
		<fd:dialog id="openAddInnerDialogInputResolve" width="800px" height="530px" modal="true" zIndex="4300"
			  title="{com.glaway.ids.pm.project.plan.addInnerinput}">
			  <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="openAddDialogInputSureResolve"></fd:dialogbutton>
			  <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="openAddOutDialogInputResolve" width="800px" height="530px" modal="true" zIndex="4300"
	          title="{com.glaway.ids.pm.project.plan.addOutinput}">
	          <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="openAddDialogInputSureResolve"></fd:dialogbutton>
	          <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="openAddDocDialogInputResolve" width="400px" height="150px" modal="true" zIndex="4300"
	          title="{com.glaway.ids.pm.project.plan.addLocal}">
	          <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="openAddDialogLocalSureResolve"></fd:dialogbutton>
	          <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
	</c:if>
<%-- 风险相关--%>
 <fd:dialog id="addTaskProblemDialogForPlan" width="800px" height="600px"
            modal="true" title="{com.glaway.ids.common.btn.create}">
     <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="RiskProblemOkFunction"></fd:dialogbutton>
     <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>
 <fd:dialog id="showRiskDialogForPlan" width="735px" height="560px" modal="true" title="{com.glaway.ids.pm.general.project.risk.assessRiskView}">
     <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>
 <fd:dialog id="riskProblemDeatilDialogForPlan" width="800px" height="600px"
            modal="true" title="{com.glaway.ids.pm.general.project.risk.viewDetail}">
     <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>
 <fd:dialog id="updateTaskProblemDialogForPlan" width="800px" height="600px"
            modal="true" title="修改">
     <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="RiskProblemOkFunction"></fd:dialogbutton>
     <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>
 <fd:dialog id="selectProbLeaderDialogForPlan" width="800px" height="250px"
            beforClose="beClose" modal="true" title="提交">
     <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="assignProb"></fd:dialogbutton>
     <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="beCancel"></fd:dialogbutton>
 </fd:dialog>

<%--风险清单--%>
 <fd:dialog id="checkRiskDialog" width="1100px" height="560px" modal="true" title="{com.glaway.ids.pm.general.project.risk.viewRisk}">
     <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>
 <fd:dialog id="accessCheckRiskDialog" width="735px" height="560px" modal="true" title="{com.glaway.ids.pm.general.project.risk.viewRisk}">
     <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>
 <fd:dialog id="measureCheckRiskDialog" width="1100px" height="560px" modal="true" title="{com.glaway.ids.pm.general.project.risk.viewRiskMeasure}">
     <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>
 <fd:dialog id="problemCheckRiskDialog" width="1100px" height="560px" modal="true" title="{com.glaway.ids.pm.general.project.risk.viewRiskProblem}">
     <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>
 <fd:dialog id="addRiskDialog" width="735px" height="560px" modal="true" title="{com.glaway.ids.pm.general.project.risk.addRisk}">
 </fd:dialog>
 <fd:dialog id="modifyRiskDialog" width="1100px" height="560px" modal="true" title="{com.glaway.ids.pm.general.project.risk.modifyRisk}">
     <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>
 <fd:dialog id="importDialog" width="400px" height="150px" modal="true" title="{com.glaway.ids.pm.general.project.risk.riskImport}">
     <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="importDialog"></fd:dialogbutton>
     <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>
 <fd:dialog id="accessRiskDialog" width="735px" height="560px" modal="true" title="{com.glaway.ids.pm.general.project.risk.assessRisk}">
     <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="accessRiskDialog"></fd:dialogbutton>
     <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>
  <%--流程分解--%>
 <fd:dialog id="preposePlanDialog" width="860px" height="580px" modal="true" title="选择前置计划" zIndex="4300">
     <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="preposePlanDialog"></fd:dialogbutton>
     <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>
 <fd:dialog id="addResourceDialog" width="600px" height="400px" modal="true" title="{com.glaway.ids.pm.project.plan.resource.addResource}" zIndex="4300">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addResourceDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>
 <%--流程分解输入--%>
 <fd:dialog id="openAddInnerDialogInput" width="800px" height="530px" modal="true" zIndex="4300"
  title="{com.glaway.ids.pm.project.plan.addInnerinput}">
  <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="openAddDialogInputSure"></fd:dialogbutton>
  <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>
 <fd:dialog id="openAddOutDialogInput" width="800px" height="530px" modal="true" zIndex="4300"
          title="{com.glaway.ids.pm.project.plan.addOutinput}">
          <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="openAddDialogInputSure"></fd:dialogbutton>
          <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>
 <fd:dialog id="openAddDialogLocalSure" width="400px" height="150px" modal="true" zIndex="4300"
          title="{com.glaway.ids.pm.project.plan.addLocal}">
          <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="openAddDialogLocalSure"></fd:dialogbutton>
          <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>
 <fd:dialog id="taskDocCheckLineDialog" width="1000px" height="500px" modal="true" title="项目库关联" zIndex="4300">
      <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="taskDocCheckLineDialog"></fd:dialogbutton>
      <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>

 <fd:dialog id="planInputsDialog" width="800px" height="580px" modal="true" title="选择来源计划" zIndex="4300">
      <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="planInputsDialog"></fd:dialogbutton>
      <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>
 <%--流程分解输出--%>
 <fd:dialog id="inheritDialog" width="400px" height="300px" modal="true" title="{com.glaway.ids.pm.project.plan.inheritParentDocument}" zIndex="4300">
      <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addInheritDialog"></fd:dialogbutton>
      <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>
	<fd:dialog id="addOutDialog" width="800px" height="530px" zIndex="4300" modal="true" title="{com.glaway.ids.pm.project.plan.deliverables.addOutputs}">
	    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addOutDialog"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>

<fd:dialog id="planDocViewDetail" width="870px" height="580px" modal="true" title="文档详情" zIndex="999999">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="modifyResourceDialog" width="780px" height="250px" modal="true" title="{com.glaway.ids.pm.project.plan.resource.editResource}" zIndex="4300">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="modifyResourceDialog"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
 
 <c:if test="${fromType == 'Plan' && !empty showLog}">                      
	<fd:panel id="panel2" border="false" title="操作日志" collapsible="false" width="99%" fit="false">
		<!-- 顶部存放 end -->
		<!-- 底部存放  begin -->
		<fd:datagrid pagination="false" idField="taskDetailBasicOperation"
			id="taskDetailBasicOperation" fit="false" fitColumns="true" width="100%">
			<fd:dgCol title="{com.glaway.ids.pm.project.task.attachmentDownload}" field="filePath"
				formatterFunName="TC001.downLoadFile" sortable="false"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.logInfo}" field="logInfo"
				formatterFunName="TC001.taskDescription" sortable="false"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.common.lable.operator}" field="createName" sortable="false"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.common.lable.operateTime}" field="createTimeStr" sortable="false"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.common.lable.remark}" field="remark" hidden="true"></fd:dgCol>
		</fd:datagrid>
	</fd:panel>
 </c:if>
</body>


</html>