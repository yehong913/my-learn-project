<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html id="123">
<head>
    <title>新建计划</title>

    <c:if test="${!empty type && type == 'update'}">
        <t:base type="jquery,easyui_iframe,tools,DatePicker,lhgdialog"></t:base>
    </c:if>
    <script src="webpage/com/glaway/ids/pm/project/plan/planList.js"></script>
    <style>
        .gla_step{
            width: 600px;}
        .div-msg-btn{bottom:20px;float: right;
            right: 8px;

            position: fixed;}
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
<body style="overflow-y: hidden;">

<div id="plan" border="false" class="easyui-panel div-msg" fit="true">
    <div class="easyui-panel" fit="true" id="zhezhao">
        <fd:form id="planAddForm">
            <input id="id" name="id" type="hidden"  value="${plan_.id}" />
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
            <input id="planTemplateId" name="planTemplateId" type="hidden" value="${planTemplateId}" />
            <input id="projectTemplateId" name="projectTemplateId" type="hidden" value="${projectTemplateId}" />
            <input id="planId" name="planId" type="hidden" value="${planId}" />
            <input id="activityId" name="activityId" type="hidden" value="${activityId}" />
            <input id="tabCbTemplateId" name="tabCbTemplateId" type="hidden" value="${tabCbTemplateId}" />
            <div class="gla_div" style="margin-bottom: 80px;">
                <ul>
                    <li>
                        <div class="gla_step">
                            <table class="step_dot">
                                <tr>
                                    <td class="stepFirst"><i></i><div><span>1</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/></div></div></td>
                                    <td class="stepEnd"><i></i><div><span>2</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.input"/></div></div></td>
                                    <td class="stepEnd"><i></i><div><span>3</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.output"/></div></div></td>
                                </tr>
                            </table>
                        </div>
                    </li>
                </ul>
            </div>
            <div id="td1">
                <c:choose>
                    <c:when test="${isStandard}">
                        <fd:combobox id="planName2" textField="name" title="{com.glaway.ids.pm.project.plan.planName}" required="true" selectedValue="${planTemplate.planName}"
                                     editable="true" valueField="id" panelMaxHeight="200" maxLength="50" name="planName2"
                                     url="planController.do?standardValueExceptDesign" onChange="nameTaskTypeChange('true')" />
                    </c:when>
                    <c:otherwise>
                        <fd:inputText id="planName" name="planName" title="{com.glaway.ids.pm.project.plan.planName}" required="true" value="${planTemplate.planName}" onChange="nameTaskTypeChange('false')"/>
                    </c:otherwise>
                </c:choose>
            </div>
            <div id="td2">
                <fd:inputText id="planName3" name="planName3" title="{com.glaway.ids.pm.project.plan.planName}" readonly="true"  required="true" value="${planTemplate.planName}" />
            </div>
            <fd:combobox id="planLevel" textField="name" title="{com.glaway.ids.pm.project.plan.planLevel}" selectedValue="${planTemplate.planLevel}"
                         editable="false" valueField="id" url="planController.do?useablePlanLevelList" />

            <fd:inputText id="workTime" title="参考工期（天）" value='${planTemplate.workTime}' />

            <fd:combobox id="milestone" textField="name" title="{com.glaway.ids.pm.project.plan.milestone}" selectedValue="${planTemplate.milestone}"
                         editable="false" valueField="id" required="true" url="planController.do?milestoneList" onChange="milestoneChange()" />

            <fd:inputSearch id="preposePlans" title="{com.glaway.ids.pm.project.plan.preposePlans}" value="${planTemplate.preposePlans}" editable="false" searcher="selectPreposePlan('#preposeIds','#preposePlans')" />
            <input name="preposeIds" id="preposeIds" value="${planTemplate.preposeIds}" type="hidden">

            <%-- <fd:combobox id="isNecessary" textField="name" title="是否必要" selectedValue=""
                    editable="false" valueField="id" required="false" url="planController.do?milestoneList"  /> --%>

            <fd:inputTextArea id="remark" title="{com.glaway.ids.pm.project.plan.remark}" name="remark" value="${planTemplate.remark}" />
        </fd:form>
    </div>

    <div class="div-msg-btn">
        <div class="ui_buttons">
            <c:if test="${!empty type && type == 'add'}">
                <fd:linkbutton onclick="beforeZero()" value="{com.glaway.ids.pm.project.plan.beforeone}" classStyle="button_nor" />
            </c:if>
            <fd:linkbutton onclick="nextZero()" value="{com.glaway.ids.pm.project.plan.nextone}" classStyle="button_nor" />
            <fd:linkbutton onclick="addTemplatePlan()" value="{com.glaway.ids.pm.project.plan.finish}" classStyle="button_nor" />
            <fd:linkbutton id="addPlanCancelBtn" onclick="closePlan_()" value="{com.glaway.ids.common.btn.cancel}" classStyle="ui_state_highlight lhgdialog_cancle" />
        </div>
    </div>
</div>


<!-- 输入 -->
<div border="false" class="div-msg-step div-msg" id="inputDiv" fit="true" >
    <input id="inList" name="inList" type="hidden">
    <fd:form id="projLibDocAddForm">
        <input id="uploadPowerFlog" name="uploadPowerFlog" type="hidden" value="${uploadPowerFlog}">
        <input id="securityLevelFromFile" name="securityLevelFromFile" type="hidden" value="${securityLevelFromFile}">
        <input id="yanfa" name="yanfa" type="hidden" value="${yanfa}">
        <input id="treeType" name="treeType" type="hidden" value="1">
        <input id="fileId" name="fileId" type="hidden" value="${doc.id}">
        <input id="id" name="id" type="hidden">
        <input id="projectId" name="projectId"  type="hidden" value="${doc.projectId}">
        <input id="type" name="type" type="hidden" value="1">
        <input id="docattachmentName" name="docattachmentName" type="hidden">
        <input id="docattachmentURL" name="docattachmentURL" type="hidden">
        <input id="docAttachmentShowName" name="docAttachmentShowName" type="hidden">
        <input id="docSecurityLevelFrom" name="docSecurityLevelFrom" type="hidden">
        <input name="invalidIds" id="invalidIds" type="hidden">
        <input name="useObjectId" id="useObjectId" type="hidden" value = "${useObjectId}">
        <input name="useObjectType" id="useObjectType" type="hidden" value = "${useObjectType}">
    </fd:form>
    <div class="gla_div">
        <ul>
            <li>
                <div class="gla_step">
                    <table class="step_dot">
                        <tr>
                            <td class="stepFirst"><i></i><div><span>1</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/></div></div></td>
                            <td class="stepMiddle"><i></i><div><span>2</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.input"/></div></div></td>
                            <td class="stepEnd"><i></i><div><span>3</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.output"/></div></div></td>
                        </tr>
                    </table>
                </div>
            </li>
        </ul>
    </div>

    <div id="existencePrepose" class="easyui-panel" fit="true">
        <div style="background-color:#F4F4F4">
            <fd:toolbar id="tagListInputs">
                <fd:toolbarGroup align="left" >
                    <fd:linkbutton onclick="addInputsNew()" value="新增输入项" id="addInputsNewButton" iconCls="basis ui-icon-plus" />
                    <fd:linkbutton onclick="addLocalDoc()" value="新增本地文档" id="up_button_import" iconCls="basis ui-icon-plus" />
                    <%--<fd:uploadify iconCls="basis ui-icon-plus" buttonText="新增本地文档"
                                  name="up_button_import" id="up_button_import" title="新增本地文档"
                                  afterUploadSuccessMode="multi"
                                  uploader="planController.do?addFileAttachments&projectId=${projectId}"
                                  extend="*.*" auto="true" showPanel="false" multi="false"
                                  dialog="false" onlyButton="true">
                        <fd:eventListener event="onUploadSuccess" listener="afterSuccessCall" />
                    </fd:uploadify>--%>
                    <fd:linkbutton onclick="deleteSelectionsInputs('inputsList', 'inputsController.do?doBatchDelInputs')" iconCls="basis ui-icon-minus"
                                   value="{com.glaway.ids.common.btn.remove}" id="deleteInputsButton"/>
                </fd:toolbarGroup>
            </fd:toolbar>
        </div>
        <fd:datagrid id="inputsList" checkbox="true" pagination="false" checkOnSelect="true" toolbar="#tagListInputs"
                     fitColumns="true"  idField="id" fit="true">
            <fd:colOpt title="操作" width = "30">
                <%-- <fd:colOptBtn iconCls="basis ui-icon-pddata" tipTitle="项目库关联" onClick="goProjLibLink" hideOption="projLibLinkOpt"/> --%>
                <fd:colOptBtn iconCls="basis ui-icon-search" tipTitle="计划关联" onClick = "goPlanLink" hideOption = "planLinkOpt"/>
            </fd:colOpt>
            <fd:dgCol title="{com.glaway.ids.common.lable.name}" field="name" width="80"  sortable="false"></fd:dgCol>
            <fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.docName}" field="docNameShow" width="80" formatterFunName="addLink"  sortable="false"></fd:dgCol>
            <fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.orgin}" field="originPath" formatterFunName = "showOrigin" width="80"  sortable="false"></fd:dgCol>
        </fd:datagrid>
    </div>

    <div class="div-msg-btn">
        <div class="ui_buttons">
            <fd:linkbutton onclick="beforeOne()" value="{com.glaway.ids.pm.project.plan.beforeone}" classStyle="button_nor" />
            <fd:linkbutton onclick="nextOne()" value="{com.glaway.ids.pm.project.plan.nextone}" classStyle="button_nor" />
            <fd:linkbutton onclick="addTemplatePlan()" value="{com.glaway.ids.pm.project.plan.finish}" classStyle="button_nor" />
            <fd:linkbutton id="addInputCancelBtn" onclick="closePlan_()" value="{com.glaway.ids.common.btn.cancel}" classStyle="ui_state_highlight lhgdialog_cancle" />
        </div>
    </div>
</div>
<!-- 输入 -->


<!-- 输出 -->
<div border="false" class="div-msg-step div-msg" id="document" fit="true">
    <input id="deliInfoList" name="deliInfoList" type="hidden">
    <input id="pdName" name="pdName" type="hidden" value="${pdName}">
    <div class="gla_div">
        <ul>
            <li>
                <div class="gla_step">
                    <table class="step_dot">
                        <tr>
                            <td class="stepFirst"><i></i><div><span>1</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/></div></div></td>
                            <td class="stepMiddle"><i></i><div><span>2</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.input"/></div></div></td>
                            <td class="stepMiddle"><i></i><div><span>3</span><div class="cont"><spring:message code="com.glaway.ids.pm.project.plan.output"/></div></div></td>
                        </tr>
                    </table>
                </div>
            </li>
        </ul>
    </div>

    <div class="easyui-panel" fit="true">
        <fd:toolbar id="tagListDeliverables">
            <fd:toolbarGroup align="left">
                <fd:linkbutton value="{com.glaway.ids.pm.project.plan.inheritParent}" onclick="inheritParent()"
                               iconCls="basis ui-icon-copy"  id ="inheritParent"/>
                <fd:linkbutton onclick="addDeliverable()" value="{com.glaway.ids.common.btn.create}" id="addDeliverableButton" iconCls="basis ui-icon-plus"/>
                <fd:linkbutton onclick="deleteSelections('deliverablesInfoList', 'deliverablesInfoController.do?doBatchDel')" iconCls="basis ui-icon-minus"
                               value="{com.glaway.ids.common.btn.remove}" id="deleteDeliverableButton"/>
            </fd:toolbarGroup>
        </fd:toolbar>

        <fd:datagrid id="deliverablesInfoList" checkbox="true" pagination="false"
                     fitColumns="true" toolbar="" idField="id" fit="true">
            <fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.deliverableName}" field="name" width="120" formatterFunName="viewDocument"  sortable="false"></fd:dgCol>
            <fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.docName}" field="file" width="120"  sortable="false"></fd:dgCol>
        </fd:datagrid>
    </div>

    <div class="div-msg-btn">
        <div class="ui_buttons">
            <fd:linkbutton onclick="beforeTwo()" value="{com.glaway.ids.pm.project.plan.beforeone}" classStyle="button_nor" />
            <%-- <fd:linkbutton onclick="nextTwo()" value="{com.glaway.ids.pm.project.plan.nextone}" classStyle="button_nor" /> --%>
            <fd:linkbutton onclick="addTemplatePlan()" value="{com.glaway.ids.pm.project.plan.finish}" classStyle="button_nor" />
            <fd:linkbutton id="addOutputCancelBtn" onclick="closePlan_()" value="{com.glaway.ids.common.btn.cancel}" classStyle="ui_state_highlight lhgdialog_cancle" />
        </div>
    </div>
</div>
<!-- 输出 -->

<fd:dialog id="addInputsDialog" width="800px" height="400px" modal="true" title="新增输入">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addInputsDialog"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="addDeliverableDialog" width="800px" height="400px" modal="true" title="新增输出">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addDeliverableDialog"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="addResourceDialog" width="800px" height="350px" modal="true" title="新增计划资源">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addResourceDialog"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="resourceDialog" width="900px" height="800px" modal="true" title="资源图" maxFun="true">
</fd:dialog>
<fd:dialog id="preposePlanDialog" width="800px" height="580px" modal="true" title="选择前置计划">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="preposePlanDialog"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="showDocDetailChangeFlowDialog" modal="true" width="1000" height="550" title="文档详情">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog" ></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="inheritDialog" width="400px" height="300px" modal="true" title="{com.glaway.ids.pm.project.plan.inheritParentDocument}">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addInheritDialog"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="openSelectInputsDialog" width="800px" height="400px"
           modal="true" title="选择输入项">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
                     callback="openSelectConfigOkFunction"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
                     callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="openSelectLocalDocDialog" width="800px" height="600px"
           modal="true" title="新增本地文档">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
                     callback="projLibSubimt"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
                     callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="openAddDialogLocalSure" width="400px" height="150px" modal="true"
           title="{com.glaway.ids.pm.project.plan.addLocal}">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="projLibSubimt"></fd:dialogbutton>
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
</body>
<script>
    $(function(){
        /* $('#resourceList').datagrid({
            onUnselect: function(index, row){
                $('#resourceList').datagrid('endEdit', index);
                $('#resourceList').datagrid('beginEdit', index);
            }
        }); */
        if('${plan_.parentPlanId}' == ''|| '${plan_.parentPlanId}' == null || '${plan_.parentPlanId}' == undefined){
            $("#inheritParent").hide();
        }
    });
    $(document).ready(function (){
        $("#td2").hide();
        $("#td1").show();
        if($("#isModifyPage").val() == 'true'){
            $("#td1").hide();
            $("#td2").show();
        }

        $("#deliverablesInfoList").datagrid({
            onCheck:onCheck
        });
        begin();
        $('#addPlanCancelBtn').focus();
    });

    function analog(){
        $('#depart_file_upload').fduploadify('upload', '*');
    }

    function onCheck(index,row){
        if(row.origin != undefined){
            $("#deliverablesInfoList").datagrid('uncheckRow',index);
            return false;
        }
    }

    var pdNext = 0;
    function begin() {
        $('#plan').panel({closed:false});
        $('#inputDiv').panel({closed:true});
        $('#document').panel({closed:true});
        $('#resourcet').panel({closed:true});
        var userList2 = eval($('#userList2').val());
    }

    function beforeZero(){
        $('#page1').css('display','');
        $('#page2').css('display','none');
    }

    function nextZero() {

        if (checkPlanWithInput()) {
            planNameChange();
        }
        $('#addInputCancelBtn').focus();

        /* if($("#isModifyPage").val() == 'true'){
            var id = $('#id').val();
            var planStartTime = $('#planStartTime').datebox('getValue');
            var planEndTime = $('#planEndTime').datebox('getValue');
            $.ajax({
                url : 'resourceLinkInfoController.do?checkChildrenTime',
                type : 'post',
                data : {
                    id : id,
                    planStartTime : planStartTime,
                    planEndTime : planEndTime
                },
                cache : false,
                success : function(data) {
                    var d = $.parseJSON(data);
                    var msg = d.msg;
                    if(d.success){
                        if (checkPlanWithInput()) {
                            planNameChange();
                        }
                    }else{
                        top.tip(msg);
                    }
                }
            });
        }else{
            if (checkPlanWithInput()) {
                planNameChange();
            }
        } */
    }

    function nextOne() {
        $('#plan').panel({closed:true});
        $('#inputDiv').panel({closed:true});
        $('#document').panel({closed:false});
        $('#resourcet').panel({closed:true});
        initDocument();
        $('#addOutputCancelBtn').focus();
    }

    function nextTwo() {
        $('#plan').panel({closed:true});
        $('#inputDiv').panel({closed:true});
        $('#document').panel({closed:true});
        $('#resourcet').panel({closed:false});
        //editFormElementIndex = undefined;
        currentIndex = undefined;
        editIndex = undefined;
        initResource();
        $('#addResourceCancelBtn').focus();
    }

    function beforeOne() {
        pdNext = 1;
        $('#plan').panel({closed:false});
        $('#addPlanCancelBtn').focus();
        $('#inputDiv').panel({closed:true});
        $('#document').panel({closed:true});
        $('#resourcet').panel({closed:true});
    }

    function beforeTwo() {
        pdNext = 1;
        $('#plan').panel({closed:true});
        $('#inputDiv').panel({closed:false});
        $('#addInputCancelBtn').focus();
        $('#document').panel({closed:true});
        $('#resourcet').panel({closed:true});
    }

    function beforeThree() {
        $('#plan').panel({closed:true});
        $('#inputDiv').panel({closed:true});
        $('#document').panel({closed:false});
        $('#addOutputCancelBtn').focus();
        $('#resourcet').panel({closed:true});
    }

    function planNameChange(){
        var planName = $('#planName').val();
        if(planName == undefined){
            planName = $('#planName2').combobox('getText');
        }
        var preposeIds = $('#preposeIds').val();
        if($("#isModifyPage").val() != 'true'){
            if( planName != undefined && planName != null && planName != '' ){
                $.ajax({
                    url : 'deliverablesInfoController.do?pdName&useObjectId='+ $('#useObjectId').val()+ '&pdNext='	+ pdNext + '&useObjectType=' + $('#useObjectType').val(),
                    type : 'post',
                    data : {
                        planName : planName
                    },
                    cache : false,
                    success : function(data) {
                        var d = $.parseJSON(data);
                        var msg = d.msg;
                        var a = msg.indexOf("在项目名称库中");
                        var b = msg.indexOf("没有勾选名称库");
                        var c = msg.indexOf("可以修改");
                        if(a == 0){
                            $('#pdName').val(planName);
                            $("#addInputsButton").show();
                            $("#deleteInputsButton").show();
                            $("#addDeliverableButton").show();
                            $("#deleteDeliverableButton").show();
                            $('#plan').panel({closed:true});
                            /* if(preposeIds != null && preposeIds != '' && preposeIds != 'undefined'){
                                $('#existencePrepose').show();
                                $('#inexistencePrepose').hide();
                            }
                            else{
                                $('#inexistencePrepose').show();
                                $('#existencePrepose').hide();
                            } */
                            $('#inputDiv').panel({closed:false});
                            $('#document').panel({closed:true});
                            $('#resourcet').panel({closed:true});
                        }else if(b == 0){
                            $('#pdName').val("");
                            $("#addInputsButton").show();
                            $("#deleteInputsButton").show();
                            $("#addDeliverableButton").show();
                            $("#deleteDeliverableButton").show();
                            $('#plan').panel({closed:true});
                            /* if(preposeIds != null && preposeIds != '' && preposeIds != 'undefined'){
                                $('#existencePrepose').show();
                                $('#inexistencePrepose').hide();
                            }
                            else{
                                $('#inexistencePrepose').show();
                                $('#existencePrepose').hide();
                            } */
                            $('#inputDiv').panel({closed:false});
                            $('#document').panel({closed:true});
                            $('#resourcet').panel({closed:true});
                        }else if(c == 0){
                            $('#pdName').val(planName);
                            $("#addInputsButton").show();
                            $("#deleteInputsButton").show();
                            $("#addDeliverableButton").show();
                            $("#deleteDeliverableButton").show();
                            $('#plan').panel({closed:true});
                            /* if(preposeIds != null && preposeIds != '' && preposeIds != 'undefined'){
                                $('#existencePrepose').show();
                                $('#inexistencePrepose').hide();
                            }
                            else{
                                $('#inexistencePrepose').show();
                                $('#existencePrepose').hide();
                            } */
                            $('#inputDiv').panel({closed:false});
                            $('#document').panel({closed:true});
                            $('#resourcet').panel({closed:true});
                        }else{
                            $('#pdName').val("");
                            $("#addInputsButton").attr("style","display:none;");
                            $("#deleteInputsButton").attr("style","display:none;");
                            $("#addDeliverableButton").attr("style","display:none;");
                            $("#deleteDeliverableButton").attr("style","display:none;");
                            var win = $.fn.lhgdialog("getSelectParentWin");
                            win.tip('<spring:message code="com.glaway.ids.pm.project.plan.nameNotStandard"/>');
                        }
                        if(d.success){
                            initInputs_();
                            $('#addInputCancelBtn').focus();
                        }
                    }
                });
            }
        }else{
            $('#pdName').val(planName);
            $("#addInputsButton").show();
            $("#deleteInputsButton").show();
            $("#addDeliverableButton").show();
            $("#deleteDeliverableButton").show();
            $('#plan').panel({closed:true});
            /* if(preposeIds != null && preposeIds != '' && preposeIds != 'undefined'){
                $('#existencePrepose').show();
                $('#inexistencePrepose').hide();
            }
            else{
                $('#inexistencePrepose').show();
                $('#existencePrepose').hide();
            } */
            $('#inputDiv').panel({closed:false});
            $('#document').panel({closed:true});
            $('#resourcet').panel({closed:true});
            initInputs_();
            $('#addInputCancelBtn').focus();
        }
    }

    function viewResourceType(val, row, value) {
        var resoure = row.resourceInfo;
        if (resoure != undefined && resoure != null && resoure != '') {
            var parent = resoure.parent;
            if (parent != undefined && parent != null && parent != '') {
                return parent.name;
            }
        }
        return val;
    }

    function viewResourceName(val, row, value) {
        var resoure = row.resourceInfo;
        if (resoure != undefined && resoure != null && resoure != '') {
            return resoure.name;
        }
        return val;
    }

    // 维护资源
    function addResource() {
        gridname = 'resourceList';

        var rows22 = $('#resourceList').datagrid('getRows');
        var t = $('#resourceList');
        for (var i = 0; i < rows22.length; i++) {
            t.datagrid('endEdit', i);
        }
        currentIndex = undefined;
        editIndex = undefined;
        //editFormElementIndex = undefined;
        modifyResourceMass2();
        $('#resourceList').datagrid('unselectAll');

        var planStartTime = $('#planStartTime').datebox('getValue');
        var planEndTime = $('#planEndTime').datebox('getValue');

        var dialogUrl = 'resourceLinkInfoController.do?goAdd&useObjectId='+ $('#useObjectId').val()
            + '&useObjectType='	+ $('#useObjectType').val()
            + '&planStartTime='	+ planStartTime
            + '&planEndTime='	+ planEndTime;
        createDialog('addResourceDialog',dialogUrl);
    }

    // 新增输入
    function addInputs() {
        var preposeIds = $('#preposeIds').val();
        if(preposeIds == '' || preposeIds == null || preposeIds == 'undefined' ){
            top.tip('<spring:message code="com.glaway.ids.pm.project.plan.inputs.addInputsCheckPrepose" />');
        }
        else{
            gridname = 'inputsList';
            var dialogUrl = 'inputsController.do?goAdd&projectId='+$("#projectId").val()+'useObjectId='
                + $('#useObjectId').val()
                + '&useObjectType='
                + $('#useObjectType').val()
                +'&preposeIds='+preposeIds;
            createDialog('addInputsDialog',dialogUrl);
        }
    }

    // 新增交付项
    function addDeliverable() {
        gridname = 'deliverablesInfoList';
        var dialogUrl = 'deliverablesInfoController.do?goAdd&useObjectId='
            + $('#useObjectId').val()
            + '&useObjectType='
            + $('#useObjectType').val();
        createDialog('addDeliverableDialog',dialogUrl);

    }

    /**
     * 选择前置计划页面
     */
    function selectPreposePlan(hiddenId, textId) {
        debugger;
        var url = 'planTemplateController.do?goPlanPreposeTree&planTemplateId=${planTemplateId}&planId='+$("#planId").val()+'&parentPlanId='+$("#parentPlanId").val()
            +'&projectTemplateId='+$("#projectTemplateId").val();
        if ($('#projectId').val() != "") {
            url = url + '&projectId=' + $('#projectId').val();
        }
        if ($('#parentPlanId').val() != "") {
            url = url + '&parentPlanId=' + $('#parentPlanId').val();
        }
        if ($('#id').val() != "") {
            url = url + '&id=' + $('#id').val();
        }
        if($('#preposeIds').val() != "") {
            url = url + '&preposeIds=' + $('#preposeIds').val();
        }
        //createPopupwindow('项目计划', url, '', '', hiddenId, textId);
        createDialog('preposePlanDialog',url);
    }

    /**
     * 查看时的弹出窗口
     */
    function createPopupwindow(title, url, width, height, hiddenId, textId) {
        width = width ? width : 800;
        height = height ? height : 750;
        if (width == "100%" || height == "100%") {
            width = document.body.offsetWidth;
            height = document.body.offsetHeight - 100;
        }
        createDialog('preposePlanDialog',url);
    }


    function preposePlanDialog(iframe){
        debugger;
        var preposeIds;
        var preposePlans;
        var preposeEndTime;
        var selectedId = iframe.planPreposeList.getSelectedRowId();
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
                    var planName = iframe.planPreposeList.getRowAttribute(id,'planName');
                    if(planName.value != undefined && planName.value != "undefined"){
                        planName = planName.value;
                    }
                    if(preposePlans != null && preposePlans != '' && preposePlans != undefined){
                        preposePlans = preposePlans + ',' + planName;
                    }else{
                        preposePlans = planName;
                    }
                    var planEndTime = iframe.planPreposeList.getRowAttribute(id,'planEndTime');
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
            $('#preposePlans').textbox("setValue",preposePlans);
        } else {
            $('#preposePlans').textbox("setValue","");
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

    function viewStartEndTime(val, row, value) {
        var start = row.startTime;
        var end = row.endTime;
        if((start != undefined && start !=null && start != '')
            && (end != undefined && end !=null && end != '')){
            return dateFmtYYYYMMDD(start) + "~" + dateFmtYYYYMMDD(end);
        }
        return "";
    }

    // 时间格式化
    function dateFormatter(val,row,value){
        return dateFmtYYYYMMDD(val);
    }

    // 使用百分比
    function viewUseRate2(val, row, value){
        return progressRateGreen2(val);
    }

    function progressRateGreen2(val){
        if(val == undefined || val == null || val == ''){
            val = 0;
        }
        return val + '%' ;
    }

    // 删除交付项
    function deleteSelections(gridname, url) {
        var rows = $("#"+gridname).datagrid('getSelections');
        for(var i =0;i<rows.length;i++){
            if(rows[i].origin != ''&&rows[i].origin !=null&&rows[i].origin !=undefined){
                top.tip('<spring:message code="com.glaway.ids.pm.project.plan.deliveryDelLimit" arguments="' + rows[i].name + '"/>');
                return false;
            }
        }

        var ids = [];
        if (rows.length > 0) {
            top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmDel"/>',
                function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url : url,
                            type : 'post',
                            data : {
                                ids : ids.join(',')
                            },
                            cache : false,
                            success : function(data) {
                                for(var i=rows.length-1;i>-1;i--){
                                    var a = $("#"+gridname).datagrid('getRowIndex',rows[i]);
                                    $("#"+gridname).datagrid('deleteRow',$("#"+gridname).datagrid('getRowIndex',rows[i]));

                                }
                                $("#"+gridname).datagrid('clearSelections');
                                if(gridname == 'inputsList'){
                                    initInputs_();
                                    $('#addInputCancelBtn').focus();
                                }
                                else{
                                    initDocument();
                                    $('#addOutputCancelBtn').focus();
                                }
                            }
                        });
                    }
                });
        } else {
            top.tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
        }
    }


    // 删除资源
    function deleteSelections2(gridname, url) {
        var rows = $("#"+gridname).datagrid('getSelections');

        var rows22 = $('#resourceList').datagrid('getRows');
        var t = $('#resourceList');
        for (var i = 0; i < rows22.length; i++) {
            t.datagrid('endEdit', i);
        }
        currentIndex = undefined;
        editIndex = undefined;
        //editFormElementIndex = undefined;
        modifyResourceMass2();
        var ids = [];
        if (rows.length > 0) {
            top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmDel"/>',
                function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url : url,
                            type : 'post',
                            data : {
                                ids : ids.join(',')
                            },
                            cache : false,
                            success : function(data) {
                                for(var i=rows.length-1;i>-1;i--){
                                    var a = $("#"+gridname).datagrid('getRowIndex',rows[i]);
                                    $("#"+gridname).datagrid('deleteRow',$("#"+gridname).datagrid('getRowIndex',rows[i]));

                                }
                                $("#"+gridname).datagrid('clearSelections');
                                initResource();
                                $('#addResourceCancelBtn').focus();
                            }
                        });
                    }
                });
        } else {
            top.tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
        }
    }

    var editIndex = undefined;
    var currentIndex = undefined;
    function endFormElementEditing(){
        /* if (editFormElementIndex == undefined){return true}
        if ($('#resourceList').datagrid('validateRow', editFormElementIndex)){
            $('#resourceList').datagrid('endEdit', editFormElementIndex);
            editFormElementIndex = undefined;
            return true;
        } else {
            return false;
        } */

        if (editIndex == undefined){
            return true
        }
        if ($('#resourceList').datagrid('validateRow', editIndex)){
            $('#resourceList').datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }


    function onClickPlanRow(index) {
        /* if (editFormElementIndex != index){
             if (endFormElementEditing()){
                $('#resourceList').datagrid('selectRow', index).datagrid('beginEdit', index);
                editFormElementIndex = index;
            } else {
                $('#resourceList').datagrid('selectRow', editFormElementIndex);
            currentIndex = index;
            }
        } */
        $('#resourceList').datagrid('endEdit', currentIndex);
        currentIndex = undefined;
        editIndex = undefined;
    }

    function onDoubleClickPlanRow(index) {
        if (currentIndex != undefined){
            onClickPlanRow();
        }
        currentIndex = index;
        if (editIndex != index) {
            if (endFormElementEditing()) {
                $('#resourceList').datagrid('selectRow', index).datagrid('beginEdit', index);
                editIndex = index;
            } else {
                $('#resourceList').datagrid('selectRow', editIndex);
            }
        }
    }



    function addplanAndResource2(){

        var rows = $('#resourceList').datagrid('getRows');
        var planStartTime = $('#planStartTime').datebox('getValue');
        var planEndTime = $('#planEndTime').datebox('getValue');
        var t = $('#resourceList');
        for (var i = 0; i < rows.length; i++) {
            t.datagrid('endEdit', i);
        }
        /* editFormElementIndex = undefined; */
        currentIndex = undefined;
        editIndex = undefined;

        for (var i = 0; i < rows.length; i++) {
            var resourceName = rows[i].resourceName;
            if(rows[i].startTime == ""){
                top.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyStartParam" arguments="' + resourceName + '"/>');
                return false;
            }

            if(rows[i].endTime == ""){
                top.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyEndParam" arguments="' + resourceName + '"/>');
                return false;
            }

            if(rows[i].endTime < rows[i].startTime){
                top.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.endNoEarlierThanStartParam" arguments="' + resourceName + '"/>');
                return false;
            }

            if((rows[i].startTime < planStartTime) || (rows[i].startTime > planEndTime)){
                top.tip(resourceName+"的开始时间必须收敛于计划时间 :"+planStartTime+'~'+planEndTime);
                return false;
            }

            if((rows[i].endTime < planStartTime) || (rows[i].endTime > planEndTime)){
                //resourceName+"的结束时间必须收敛于计划时间 :"+planStartTime+'~'+planEndTime
                top.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.endInPlanTimeParam" arguments="' + resourceName + ',' +planStartTime+'~'+planEndTime+ '"/>');
                return false;
            }


            if(rows[i].useRate == ''){
                top.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyPercentParam" arguments="' + resourceName + '"/>');
                return false;
            }

            if(rows[i].useRate > 1000){
                top.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.percentUpperLimitParam" arguments="' + resourceName + '"/>');
                return false;
            }

            if(rows[i].useRate < 0){
                top.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.percentLowerLimitParam" arguments="' + resourceName + '"/>');
                return false;
            }
        }
        modifyResourceMass();

    }


    //计划名称链接事件
    function viewDocument(val, row, value) {
        if(row.origin != undefined){
            return '<a style="color:gray">' + row.name + '</a>';
        }else{
            return '<a style="color:black">' + row.name + '</a>';
        }

    }


    function modifyResourceMass(){
        var rows = $('#resourceList').datagrid('getRows');
        var ids = [];
        var useRates = [];
        var startTimes = [];
        var endTimes = [];

        if(rows.length > 0){
            for(var i = 0; i < rows.length; i++){
                ids.push(rows[i].id);
                useRates.push(rows[i].useRate);
                startTimes.push(rows[i].startTime);
                endTimes.push(rows[i].endTime);
            }

            $.ajax({
                url : 'resourceLinkInfoController.do?modifyResourceMass',
                type : 'post',
                data : {
                    ids : ids.join(','),
                    useRates : useRates.join(','),
                    startTimes : startTimes.join(','),
                    endTimes : endTimes.join(','),
                },
                cache : false,
                success : function(data) {
                    addTemplatePlan();
                }
            });
        }else{
            addTemplatePlan();
        }
    }

    function modifyResourceMass2(){
        var rows = $('#resourceList').datagrid('getRows');
        var ids = [];
        var useRates = [];
        var startTimes = [];
        var endTimes = [];

        if(rows.length > 0){
            for(var i = 0; i < rows.length; i++){
                ids.push(rows[i].id);
                useRates.push(rows[i].useRate);
                startTimes.push(rows[i].startTime);
                endTimes.push(rows[i].endTime);
            }

            $.ajax({
                url : 'resourceLinkInfoController.do?modifyResourceMass',
                type : 'post',
                data : {
                    ids : ids.join(','),
                    useRates : useRates.join(','),
                    startTimes : startTimes.join(','),
                    endTimes : endTimes.join(','),
                },
                cache : false,
                success : function(data) {
                }
            });
        }else{
        }
    }


    // 资源名称链接事件
    function resourceNameLink(val, row, value) {
        return '<a href="#" onclick="viewResourceCharts(\'' + row.id + '\')" style="color:blue">' + val + '</a>';
    }

    // 资源名称链接事件
    function viewResourceCharts(id){
        var rows = $("#resourceList").datagrid('getRows');
        var index = $("#resourceList").datagrid('getRowIndex', id);
        var row = rows[index];

        var planStartTime=$('#planStartTime').datebox('getValue');
        var planEndTime=$('#planEndTime').datebox('getValue');

        if(row.useRate != null && row.useRate != '' && row.startTime != null && row.startTime != '' && row.endTime != null && row.endTime != ''){
            createDialog('resourceDialog',"resourceLinkInfoController.do?goToToBeUsedRateReport&resourceId="
                + row.resourceInfo.id + "&resourceLinkId=" + row.id + "&resourceUseRate=" + row.useRate
                + "&startTime=" + row.startTime + "&endTime=" + row.endTime + "&useObjectId=${plan_.id}");
        }
        else{
            top.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyUseInfo"/>');
        }
    }

    function addSetTimeOut(){
        window.setTimeout("addPlanAndResource()", 500);//等待0.5秒，让分类树结构加载出来
    }

    function addPlanAndResource(){
        if(checkPlanWithInput()){
            var planName = $('#planName').val();
            var taskNameId='';
            if(planName == undefined){
                planName = $('#planName2').combobox('getText');
                taskNameId=$('#planName2').combobox('getValue');
            }

            var id = $('#id').val();
            var planNumber = $('#planNumber').val();
            var projectId = $('#projectId').val();
            var parentPlanId = $('#parentPlanId').val();
            var beforePlanId = $('#beforePlanId').val();
            var bizCurrent = $('#bizCurrent').val();

            var isStandard = $('#isStandard').val();
            var flowStatus = $('#flowStatus').val();
            var remark = $('#remark').val();
            var owner = $('#owner').combobox('getValue');
            var userList = eval($('#userList2').val());
            for (var i = 0; i < userList.length; i++) {
                var a = owner.indexOf(userList[i].realName);
                if (a == 0) {
                    owner = userList[i].id;
                }
            }

            var planLevel = $('#planLevel').combobox('getValue');
            var planLevelList = eval($('#planLevelList').val());
            for (var i = 0; i < planLevelList.length; i++) {
                if (planLevel == planLevelList[i].name) {
                    planLevel = planLevelList[i].id;
                }
            }
            var planStartTime = $('#planStartTime').datebox('getValue');
            var workTime = $('#workTime').val();
            var planEndTime = $('#planEndTime').datebox('getValue');
            var milestone = $('#milestone').combobox('getValue');
            var milestoneRm;
            var preposeIds = $('#preposeIds').val();
            if(milestone == "否"){
                milestoneRm = "false";
            }else if(milestone == "false"){
                milestoneRm = "false";
            }else if(milestone == "true"){
                milestoneRm = "true";
            }

            var taskNameType = $('#taskNameType').combobox('getValue');
            var taskType = $('#taskType').combobox('getValue');


            if($("#isModifyPage").val() != 'true'){
                if( planName != undefined && planName != null && planName != '' ){
                    $.ajax({
                        url : 'deliverablesInfoController.do?pdName&useObjectId='+ $('#useObjectId').val()+ '&pdNext='	+ pdNext,
                        type : 'post',
                        data : {
                            planName : planName
                        },
                        cache : false,
                        success : function(data) {
                            var d = $.parseJSON(data);
                            var msg = d.msg;
                            var a = msg.indexOf("在项目名称库中");
                            var b = msg.indexOf("没有勾选名称库");
                            var c = msg.indexOf("可以修改");

                            if(a == 0){
                                $.post('planController.do?doSave', {
                                    'id' : id,
                                    'planNumber' : planNumber,
                                    'projectId' : projectId,
                                    'parentPlanId' : parentPlanId,
                                    'beforePlanId' : beforePlanId,
                                    'planStartTime' : planStartTime,
                                    'planEndTime' : planEndTime,
                                    'flowStatus' : flowStatus,
                                    'bizCurrent' : bizCurrent,
                                    'remark' : remark,
                                    'planName' : planName,
                                    'owner' : owner,
                                    'planLevel' : planLevel,
                                    'workTime' : workTime,
                                    'milestone'	: milestoneRm,
                                    'preposeIds' : preposeIds,
                                    'taskNameId' : taskNameId,
                                    'taskNameType' : taskNameType,
                                    'taskType' : taskType
                                }, function(data) {
                                    var win = $.fn.lhgdialog("getSelectParentWin");
                                    var planListSearch = win.planListSearch();
                                    $.fn.lhgdialog("closeSelect");
                                });
                            }else if(b == 0){
                                $.post('planController.do?doSave', {
                                    'id' : id,
                                    'planNumber' : planNumber,
                                    'projectId' : projectId,
                                    'parentPlanId' : parentPlanId,
                                    'beforePlanId' : beforePlanId,
                                    'planStartTime' : planStartTime,
                                    'planEndTime' : planEndTime,
                                    'bizCurrent' : bizCurrent,
                                    'flowStatus' : flowStatus,
                                    'remark' : remark,
                                    'planName' : planName,
                                    'owner' : owner,
                                    'planLevel' : planLevel,
                                    'workTime' : workTime,
                                    'milestone'	: milestoneRm,
                                    'preposeIds' : preposeIds,
                                    'taskNameType' : taskNameType,
                                    'taskType' : taskType
                                }, function(data) {
                                    var win = $.fn.lhgdialog("getSelectParentWin");
                                    var planListSearch = win.planListSearch();
                                    $.fn.lhgdialog("closeSelect");
                                });
                            }else if(c == 0){
                                $.post('planController.do?doSave', {
                                    'id' : id,
                                    'planNumber' : planNumber,
                                    'projectId' : projectId,
                                    'parentPlanId' : parentPlanId,
                                    'beforePlanId' : beforePlanId,
                                    'planStartTime' : planStartTime,
                                    'planEndTime' : planEndTime,
                                    'bizCurrent' : bizCurrent,
                                    'flowStatus' : flowStatus,
                                    'remark' : remark,
                                    'planName' : planName,
                                    'owner' : owner,
                                    'planLevel' : planLevel,
                                    'workTime' : workTime,
                                    'milestone'	: milestoneRm,
                                    'preposeIds' : preposeIds,
                                    'taskNameType' : taskNameType,
                                    'taskType' : taskType
                                }, function(data) {
                                    var win = $.fn.lhgdialog("getSelectParentWin");
                                    var planListSearch = win.planListSearch();
                                    $.fn.lhgdialog("closeSelect");
                                });
                            }else{
                                var win = $.fn.lhgdialog("getSelectParentWin");
                                //win.tip("计划名称不在名称库中");
                                top.tip('<spring:message code="com.glaway.ids.pm.project.plan.nameNotStandard"/>');
                            }
                            if(d.success){
                                initDocument();
                                $('#addOutputCancelBtn').focus();
                            }
                        }
                    });
                }
            }else{
                var id = $('#id').val();
                var planStartTime = $('#planStartTime').datebox('getValue');
                var planEndTime = $('#planEndTime').datebox('getValue');
                $.ajax({
                    url : 'resourceLinkInfoController.do?pdResource',
                    type : 'post',
                    data : {
                        id : id,
                        planStartTime : planStartTime,
                        planEndTime : planEndTime
                    },
                    cache : false,
                    success : function(data) {
                        var d = $.parseJSON(data);
                        var msg = d.msg;
                        if(d.success){
                            $.post('planController.do?doSave', {
                                'id' : id,
                                'planNumber' : planNumber,
                                'projectId' : projectId,
                                'parentPlanId' : parentPlanId,
                                'beforePlanId' : beforePlanId,
                                'planStartTime' : planStartTime,
                                'planEndTime' : planEndTime,
                                'bizCurrent' : bizCurrent,
                                'flowStatus' : flowStatus,
                                'remark' : remark,
                                'planName' : planName,
                                'owner' : owner,
                                'planLevel' : planLevel,
                                'workTime' : workTime,
                                'milestone'	: milestoneRm,
                                'preposeIds' : preposeIds,
                                'taskNameType' : taskNameType,
                                'taskType' : taskType
                            }, function(data) {
                                var win = $.fn.lhgdialog("getSelectParentWin");
                                var planListSearch = win.planListSearch();
                                $.fn.lhgdialog("closeSelect");
                            });
                        }else{
                            top.tip(msg);
                        }
                    }
                });
            }
        }

    }


    function addInputsDialog(iframe){
        var flg=iframe.getLoadData();
        if(flg && initInputs_(flg)){
            return true;
        }
        return false;
    }

    function addDeliverableDialog(iframe){
        var flg=iframe.getLoadData();
        if(flg && initDocument(flg)){
            return true;
        }
        return false;
    }

    function addResourceDialog(iframe){
        var flg=iframe.getLoadData();
        if(flg && initResource(flg)){
            //editFormElementIndex = undefined;
            currentIndex = undefined;
            editIndex = undefined;
            return true;
        }
        return false;
    }

    function addInheritDialog(iframe){
        var flg=iframe.add();
        if(flg && initDocument(flg)){
            return true;
        }
        return false;
    }

    /**国际化js转移过来的方法**/
    var inputTypeTotal = "1";
    function workTimeLinkage(inputType) {
        if (inputType == 'planStartTime') {
            inputTypeTotal = inputTypeTotal + "1";
        }
        if (inputType == 'planEndTime') {
            inputTypeTotal = inputTypeTotal + "2";
        }
        if (inputType == 'workTime') {
            inputTypeTotal = inputTypeTotal + "3";
        }
        var win = $.fn.lhgdialog("getSelectParentWin");
        var planStartTime = $('#planStartTime').datebox('getValue');
        var workTime = $('#workTime').val();
        var planEndTime = $('#planEndTime').datebox('getValue');
        var projectId = $('#projectId').val();
        var milestone = $('#milestone').combobox('getValue');
        if(milestone == "否"){
            milestone = "false";
        }else if(milestone == "是"){
            milestone = "true";
        }
        if (inputType == 'planEndTime') {
            inputType1 = inputType;
        }
        if (inputType == 'workTime') {
            inputType2 = inputType;
        }
        var inputTypeTotalIndex = inputTypeTotal.indexOf('123');
        if (inputType1 == 'planEndTime' && inputType2 == 'workTime' && inputTypeTotalIndex < 0) {
            inputType1 = undefined;
            inputType2 = undefined
            return false;
        }
        if (inputType == 'planStartTime') {
            if (planStartTime != null && planStartTime != ''
                && planStartTime != undefined) {
                if (workTime != null && workTime != '' && workTime != undefined) {
                    if (workTime == 0 && milestone != "true") {
                        $('#workTime').textbox("setValue", "1");
                        win.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
                    } else {
                        $.ajax({
                            url : 'planController.do?getEndTimeByStartTimeAndWorkTime',
                            type : 'post',
                            data : {
                                projectId : projectId,
                                planStartTime : planStartTime,
                                planEndTime : planEndTime,
                                workTime : workTime,
                                milestone : milestone
                            },
                            cache : false,
                            success : function(data) {
                                if (data != null) {
                                    var d = $.parseJSON(data);
                                    if (d.success == true) {
                                        planEndTime = d.obj;
                                        $('#planEndTime')
                                            .datebox("setValue",
                                                planEndTime);
                                    } else {
                                        $('#planStartTime')
                                            .datebox("setValue",
                                                planEndTime);
                                        win.tip(d.msg);
                                    }
                                } else {
                                    win.tip('<spring:message code="com.glaway.ids.pm.project.plan.endTimeError"/>');
                                }
                            }
                        });
                    }
                } else if (planEndTime != null && planEndTime != ''
                    && planEndTime != undefined) {
                    if (planEndTime < planStartTime) {
                        $('#planEndTime').datebox("setValue", "");
                    } else {
                        $.ajax({
                            url : 'planController.do?getWorkTimeByStartTimeAndEndTime',
                            type : 'post',
                            data : {
                                projectId : projectId,
                                planStartTime : planStartTime,
                                planEndTime : planEndTime,
                                workTime : workTime,
                                milestone : milestone
                            },
                            cache : false,
                            success : function(data) {
                                if (data != null) {
                                    var d = $.parseJSON(data);
                                    if (d.success == true) {
                                        workTime = d.obj;
                                        $('#workTime').textbox(
                                            "setValue", workTime);
                                    } else {
                                        win.tip(d.msg);
                                    }
                                } else {
                                    win.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
                                }
                            }
                        });
                    }
                }
            } else {
                win.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
            }
        } else if (inputType == 'workTime') {
            if (workTime != null && workTime != '' && workTime != undefined) {
                if (planStartTime != null && planStartTime != ''
                    && planStartTime != undefined) {
                    if (workTime == 0 && milestone != "true") {
                        $('#workTime').textbox("setValue", "1");
                        win.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
                    } else {
                        $.ajax({
                            url : 'planController.do?getEndTimeByStartTimeAndWorkTime',
                            type : 'post',
                            data : {
                                projectId : projectId,
                                planStartTime : planStartTime,
                                planEndTime : planEndTime,
                                workTime : workTime,
                                milestone : milestone
                            },
                            cache : false,
                            success : function(data) {
                                if (data != null) {
                                    var d = $.parseJSON(data);
                                    if (d.success == true) {
                                        planEndTime = d.obj;
                                        $('#planEndTime')
                                            .datebox("setValue",
                                                planEndTime);
                                    } else {
                                        win.tip(d.msg);
                                    }
                                } else {
                                    win.tip('<spring:message code="com.glaway.ids.pm.project.plan.endTimeError"/>');
                                }
                            }
                        });
                    }
                } else {
                    win.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
                }
            }
        } else if (inputType == 'planEndTime') {
            if (planEndTime != null && planEndTime != ''
                && planEndTime != undefined) {
                if (planStartTime != null && planStartTime != ''
                    && planStartTime != undefined) {
                    if (planEndTime < planStartTime) {
                        win.tip('<spring:message code="com.glaway.ids.pm.project.plan.startNoLaterThanEnd"/>');
                    } else {
                        $.ajax({
                            url : 'planController.do?getWorkTimeByStartTimeAndEndTime',
                            type : 'post',
                            data : {
                                projectId : projectId,
                                planStartTime : planStartTime,
                                planEndTime : planEndTime,
                                workTime : workTime,
                                milestone : milestone
                            },
                            cache : false,
                            success : function(data) {
                                if (data != null) {
                                    var d = $.parseJSON(data);
                                    if (d.success == true) {
                                        workTime = d.obj;
                                        if (workTime == 0 && milestone != "true") {
                                            $('#workTime').textbox(
                                                "setValue", "1");
                                            win.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
                                        } else {
                                            $('#workTime').textbox(
                                                "setValue",
                                                workTime);
                                        }
                                    } else {
                                        win.tip(d.msg);
                                    }
                                } else {
                                    win.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
                                }
                            }
                        });
                    }
                } else {
                    win.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
                }
            }
        }
    }

    function checkPlanWithInput() {
        debugger;
        var parentStartTime = $('#parentStartTime').val();
        var parentEndTime = $('#parentEndTime').val();
        var preposeEndTime = $('#preposeEndTime').val();
        var win = $.fn.lhgdialog("getSelectParentWin") ;
        var milestone = $("#milestone").combobox('getValue');
        var workTime = $('#workTime').val();

        var planName = $('#planName').val();
        if(planName == undefined){
            var planName = $('#planName2').combobox('getText');
        }
        if (planName == "") {
            top.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyName"/>');
            return false;
        }
        if(planName.length>30){
            top.tip('<spring:message code="com.glaway.ids.pm.project.plan.basicLine.nameLength"/>')
            return false;
        }

        if('${childrenPlanList}' != '' && '${childrenPlanList}' != null && '${childrenPlanList}' !=undefined){
            var childrenPlanList = $.evalJSON('${childrenPlanList}');
            if(childrenPlanList.length > 0){
                for(var i = 0;i<childrenPlanList.length;i++){
                    if(childrenPlanList[i].workTime > parseInt(workTime)){
                        top.tip('工期不能小于子计划工期');
                        return false;
                    }
                }
            }
        }


        var reg = new RegExp("^[1-9][0-9]{0,3}$");
        var reg1 = new RegExp("^[0-9][0-9]{0,3}$");
        if (workTime == "") {
            top.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyPeriod"/>');
            return false;
        }

        if(milestone == "true"){
            if(!reg1.test(workTime)){
                top.tip("工期必须是0~9999的整数");
                return false;
            }
        }else{
            if(!reg.test(workTime)){
                top.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodLowerToUpper"/>');
                return false;
            }
        }


        debugger;

        if($("#parentPlanId").val() != "" && $("#parentPlanId").val() != null && $("#parentPlanId").val() != '') {
            if('${workTime}' != '' && '${workTime}' != null){
                if(parseInt(workTime) > parseInt('${workTime}')){
                    top.tip("工期不能超过父计划工期");
                    return false;
                }
            }
        }



        var remark = $('#remark').val();
        if(remark.length>200){
            top.tip('<spring:message code="com.glaway.ids.common.remarkLength"/>')
            return false;
        }
        return true;
    }

    function nameTaskTypeChange(isStandard){
        if('true' == isStandard){
            var planName = $('#planName2').combobox('getText');
            $.ajax({
                url : 'planController.do?getTaskNameType',
                type : 'post',
                data : {
                    planName : planName
                },
                cache : false,
                success : function(data) {
                    if (data != null) {
                        var d = $.parseJSON(data);
                        if (d.success == true) {
                            $('#taskNameType')
                                .combobox("setValue",
                                    d.obj);
                        } else {
                            $('#taskNameType')
                                .combobox("setValue",
                                    d.obj);
                            win.tip(d.msg);
                        }
                    } else {
                        win.tip(d.msg);
                    }
                }
            });
        }
    }

    function milestoneChange(){
        var workTime = $('#workTime').val();
        var milestone = $('#milestone').combobox('getValue');
        if(milestone == "否"){
            milestone = "false";
        }else if(milestone == "是"){
            milestone = "true";
        }
        if (workTime == 0 && milestone != "true") {
            $('#workTime').textbox(
                "setValue", "1");
        }
    }

    function addLink(val, row, value){
        debugger;
        if(val!=null&&val!=''&&row.originType == 'LOCAL'){
            return '<a  href="#" onclick="importDoc(\'' + row.docId + '\',\'' + row.docNameShow + '\')" id="myDoc"  style="color:blue">'+val+'</a>';
        }else if(row.originType == 'PROJECTLIBDOC'){
            var path = "<a  href='#' title='查看' style='color:blue' onclick='openProjectDoc1(\""+ row.docIdShow+ "\",\""+ row.docNameShow+ "\",\""+ row.ext1+"\",\""+ row.ext2+"\")'>"
                + row.docNameShow
                + "</a>"
            if (row.ext3 == false || row.ext3 == 'false') {
                path = row.docNameShow;
            }
            return path;
        }else if(row.originType == 'PLAN'){
            var path = "<a  href='#' title='查看' style='color:blue' onclick='openProjectDoc1(\""+ row.docId+ "\",\""+ row.docNameShow+ "\",\""+ row.ext1+"\",\""+ row.ext2+"\")'>"
                + row.docNameShow
                + "</a>"
            if (row.ext3 == false || row.ext3 == 'false') {
                path = row.docNameShow;
            }
            return path;
        }
        else return ;

    }


    function openProjectDoc1(id, name,download,history) {
        if (download == false || download == 'false') {
            download = "false";
        }
        if (history == false || history == 'false') {
            history = "false";
        }
        var url = "projLibController.do?viewProjectDocDetail&id=" + id
            + "&download=" + download + "&history=" + history;
        createdetailwindow("文档详情", url, "870", "580")
    }

    function importDoc(filePath,fileName){
        debugger;
        window.location.href = encodeURI('jackrabbitFileController.do?fileDown&fileName='+fileName+'&filePath=' + filePath);
    }

    function showDocDetail(id){
        var url="projLibController.do?viewProjectDocDetail&opFlag=1&id="+id;
        createdetailwindow2("文档详情", url, "1000", "550");
    }


    function showOrigin(val, row, value){
        if(row.originType == "LOCAL"){
            return "本地文档";
        }else{
            return val;
        }
    }

    // 继承父项
    function inheritParent() {
        debugger;
        var dialogUrl = 'deliverablesInfoController.do?goAddInheritForTemplate&planTemplateId=${planTemplateId}&projectTemplateId=${projectTemplateId}&planId=${plan_.id}&parentPlanId=${plan_.parentPlanId}'+'&useObjectId='
            + $('#useObjectId').val()
            + '&useObjectType='
            + $('#useObjectType').val();
        createDialog('inheritDialog', dialogUrl);
    }

    function createdetailwindow2(title, url, width, height) {
        width = width ? width : 700;
        height = height ? height : 400;
        if (width == "100%" || height == "100%") {
            width = document.body.offsetWidth;
            height = document.body.offsetHeight - 100;
        }

        if (typeof (windowapi) == 'undefined') {
            createDialog('showDocDetailChangeFlowDialog',url);

        } else {
            createDialog('showDocDetailChangeFlowDialog',url);
        }
    }


    function addInputsNew(){
        var dialogUrl = 'inputsController.do?goAddInputs&projectId='+$("#projectId").val()+'&useObjectId='
            + $('#useObjectId').val()
            + '&useObjectType='
            + $('#useObjectType').val()
            +'&hideMoreShow=false';
        createDialog('openSelectInputsDialog', dialogUrl);
    }

    function openSelectConfigOkFunction(iframe){
        var flg=iframe.getLoadData();
        if(flg && initInputs_(flg)){
            return true;
        }
        return false;
    }

    function addLocalDoc(){
        var dialogUrl = 'inputsController.do?goProjTempAddDoc&projectId=${projectId}';
        $("#openAddDialogLocalSure").lhgdialog("open", 'url:' + dialogUrl + "");
    }

    /*function projLibSubimt(iframe) {
        debugger;
        var result = false;
        var result = iframe.saveFileBasicAndUpload();
        debugger;
        if(result && initInputs_('')){
            return true;
        } else {
            return false;
        }
    }*/

    function projLibSubimt(iframe) {
        debugger;
        iframe.afterSubmitChange();
        return false;
    }

    var idRow = '';
    function uploadFile(docId) {
        $.ajax({
            url : 'deliverablesInfoController.do?update',
            type : 'post',
            data : {
                id: idRow,
                fileId : docId
            },
            cache : false,
            success : function(data) {
                var d = $.parseJSON(data);
                //如果上传成功，不需要提示
                //var rst = ajaxRstAct(d);
                if (d.success) {
                    var msg = d.msg;
                    if(msg=='<spring:message code="com.glaway.ids.pm.project.plan.deliverables.updateSuccess"/>'){
                        $('#planResolve').attr("style","display:none");
                        $('#flowResolve').attr("style","display:none");
                    }
                    reloadTable();
                }
            }
        });
    }

    function goProjLibLink(id,index) {
        debugger;

        var row;
        $('#inputsList').datagrid('selectRow',index);
        var all = $('#inputsList').datagrid('getRows');
        for(var i=0;i<all.length;i++){
            var inx = $("#inputsList").datagrid('getRowIndex', all[i]);
            if(inx == index){
                row = all[i];
            }
        }

        idRow=row.id;
        var dialogUrl = 'projLibController.do?goProjLibLayout0&id='+$("#projectId").val()+'&rowId='+idRow;
        $("#"+'taskDocCheckLineDialog').lhgdialog("open", "url:"+dialogUrl);
    }

    function taskDocCheckLineDialog(iframe) {
        debugger;
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
                        initInputs_('');
                    }
                }
            });
            return true;
        } else {
            return false;
        }
    }

    function saveFileBasicAndUpload()
    {
        debugger;
        delectFlog = false;

        var retOid;
        var result=true;


        var allRows=$('#projLibDocList').datagrid('getRows');
        var dowmLoadUrls = [];
        var attachmentNames = [];
        var docattachmentName = "";
        var docattachmentURL = "";
        var docAttachmentShowName = "";
        if(allRows.length>0){
            endEditing();
            for (var i = 0; i < allRows.length; i++) {
                if(docattachmentName == '' || docattachmentName == null){
                    docattachmentName = allRows[i].attachmentName;
                }else{
                    docattachmentName =docattachmentName +","+allRows[i].attachmentName;
                }
                if(docattachmentURL == '' || docattachmentURL == null){
                    docattachmentURL = allRows[i].dowmLoadUrl;
                }else{
                    docattachmentURL = docattachmentURL+","+allRows[i].dowmLoadUrl;
                }
                if(docAttachmentShowName == '' || docAttachmentShowName == null){
                    docAttachmentShowName = allRows[i].attachmentShowName;
                }else{
                    docAttachmentShowName = docAttachmentShowName+","+allRows[i].attachmentShowName;
                }

//			  if(allRows[i].docSecurityLevel != null && allRows[i].docSecurityLevel != '' && allRows[i].docSecurityLevel != undefined){
//			  }else{
//				  var p = parseInt(i)+1;
//				  tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.docSecurityLevelNotEmpty" arguments="' + p + '"/>');
//				return false;
//			  }
            }
//		  workTimeOnChange();

            $('#docattachmentName').val(docattachmentName);
            $('#docattachmentURL').val(docattachmentURL);
            $('#docAttachmentShowName').val(docAttachmentShowName);
        }

        $.ajax({
            type : "POST",
            url : "projLibController.do?doAddForLocalDoc&attachmentNames="+ attachmentNames+"&dowmLoadUrls="+ dowmLoadUrls,
            async : false,
            data :  $('#projLibDocAddForm').serialize(),
            success : function(data) {
                debugger;
                var d = $.parseJSON(data);
                if (d.success) {
                    docId = d.obj;
                    retOid=d.obj;
                    result=true;
                    var invalidIds = $('#invalidIds').val();
                    var delUrl='projLibController.do?deleteProjLibJackrabbitFile&type='+'add';
                    $.ajax({
                        type : 'POST',
                        url : delUrl,
                        async : false,
                        data : {
                            invalidIds : invalidIds,
                            docattachmentURL : docattachmentURL
                        },
                        success : function(data) {
                        }
                    });
                    if('${yanfa}' == 'yanfa'){
                        if(result){
                            var win = $.fn.lhgdialog("getSelectParentWin");
                            win.uploadFile(retOid);
                            return true;

                        }
                    }

                    return false;
                }else{
                    result= false;
                }

                if('${yanfa}' == 'yanfa'){}else{
                    var win = $.fn.lhgdialog("getSelectParentWin");
                    $.fn.lhgdialog("closeSelect");
                    win.tip(d.msg);
                }

            }
        });
        return true;
    }



    function deleteSelectionsInputs(griname,url){
        debugger;
        var rows = $('#'+griname).datagrid('getChecked');

        var ids = [];
        if(rows.length > 0){
            top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmDel"/>',
                function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url : url,
                            type : 'post',
                            data : {
                                ids : ids.join(','),
                                useObjectId : $("#useObjectId").val()
                            },
                            cache : false,
                            success : function(data) {
                                debugger;
                                if(griname == 'inputsList'){
                                    initInputs_('');
                                    $('#'+griname).datagrid('clearSelections');
                                }

                            }
                        });
                    }
                });

        }else{
            top.tip("请选择需要删除的记录");
        }
    }


    function goPlanLink(id,index){
        debugger;
        var row;
        $('#inputsList').datagrid('selectRow',index);
        var all = $('#inputsList').datagrid('getRows');
        for(var i=0;i<all.length;i++){
            var inx = $("#inputsList").datagrid('getRowIndex', all[i]);
            if(inx == index){
                row = all[i];
            }
        }

        $.ajax({
            type:'POST',
            data:{inputsName : row.name},
            url:'planController.do?setInputsNameToSession',
            cache:false,
            success:function(data){
                var d = $.parseJSON(data);
                if(d.success){
                    var url = 'planTemplateController.do?goSelectPlanInputs&planTemplateId=${planTemplateId}&projectId='+$("#projectId").val()+'&useObjectId='+$("#useObjectId").val()+'&useObjectType='+$("#useObjectType").val()+'&tempId='+row.id;
                    //url = encodeURI(encodeURI(url))

                    createDialog('planInputsDialog',url);
                }
            }
        });
    }


    function planInputsDialog(iframe){
        if (iframe.validateSelectedBeforeSave()) {
            debugger;
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
                    inputsName : inputsName,
                    planTemplateId : '${planTemplateId}'
                },
                url : 'planTemplateController.do?setPlanInputs',
                cache:false,
                success : function(data){
                    initInputs_('');
                    $('#inputsList').datagrid('clearSelections');
                }
            });
        }else{
            return false;
        }
    }


    function projLibLinkOpt(r,s){
        if(r.originType == 'LOCAL'){
            return true;
        }else{
            return false;
        }
    }

    function planLinkOpt(r,s){
        if(r.originType == 'LOCAL'){
            return true;
        }else{
            return false;
        }
    }


    // 保存计划
    function addTemplatePlan() {
        if(checkPlanWithInput()){
            var id = $('#id').val();
            var planTemplateId = $('#planTemplateId').val();
            var projectTemplateId = $('#projectTemplateId').val();
            var planNumber = $('#planNumber').val();
            var projectId = $('#projectId').val();
            var parentPlanId = $('#parentPlanId').val();
            var beforePlanId = $('#beforePlanId').val();
            var bizCurrent = $('#bizCurrent').val();
            var flowStatus = $('#flowStatus').val();
            var isStandard = $('#isStandard').val();
            var planName = $('#planName').val();
            var planId = $('#planId').val();
            var activityId = $('#activityId').val();
            var tabCbTemplateId = $('#tabCbTemplateId').val();
            var taskNameId='';
            if(planName == undefined){
                planName = $('#planName2').combobox('getText');
                taskNameId=$('#planName2').combobox('getValue');
            }

            var remark = $('#remark').val();


            var planLevel = $('#planLevel').combobox('getValue');
            var planLevelList = eval($('#planLevelList').val());
            for (var i = 0; i < planLevelList.length; i++) {
                if (planLevel == planLevelList[i].name) {
                    planLevel = planLevelList[i].id;
                }
            }

            var workTime = $('#workTime').val();

            var milestone = $('#milestone').combobox('getValue');
            var milestoneRm;
            var preposeIds = $('#preposeIds').val();
            if(milestone == "否"){
                milestoneRm = "false";
            }else if(milestone == "false"){
                milestoneRm = "false";
            }else if(milestone == "true"){
                milestoneRm = "true";
            }

            /* var isNecessary = $('#isNecessary').combobox('getValue'); */

            var useObjectId = $('#useObjectId').val();
            var useObjectType = $('#useObjectType').val();


            $.post('planTemplateController.do?doSave', {
                'id' : id,
                'planId' : planId,
                'planNumber' : planNumber,
                'planTemplateId' : planTemplateId,
                'projectTemplateId' : projectTemplateId,
                'type' : '${type}',
                'useObjectId' : useObjectId,
                'useObjectType' : useObjectType,
                'projectId' : projectId,
                'parentPlanId' : parentPlanId,
                'beforePlanId' : beforePlanId,
                'remark' : remark,
                'planName' : planName,
                'flowStatus' : flowStatus,
                'planLevel' : planLevel,
                'workTime' : workTime,
                'milestone'	: milestoneRm,
                'preposeIds' : preposeIds,
                'taskNameId':taskNameId,
                'activityId' : activityId,
                'tabCbTemplateId':tabCbTemplateId
            }, function(data) {
                var d = $.parseJSON(data);
                debugger;
                if (d.success) {
                    var win = $.fn.lhgdialog("getSelectParentWin");
                    try{
                        win.iframeFlush();
                    }catch (e) {

                    }

                    try{
                        win.planListSearch();
                    }catch (e) {

                    }

                    $.fn.lhgdialog("closeSelect");
                } else {
                    top.tip(d.msg);
                }
            });

        }
    }


    function initInputs_(this_) {
        var useObjectType = "";
        if($('#projectId').val() == "" || $('#projectId').val() == '' || $('#projectId').val() == null){
            //useObjectType = "PLANTEMPLATE";
            useObjectType = "PROJECTTEMPLATE";
        }else{
            useObjectType = "PLAN";
        }
        var datas="";
        var flg=false;
        if(this_ != null && this_ != '' && this_ != undefined){
            datas=this_;
        }else{
            datas={useObjectId : $('#useObjectId').val(),useObjectType : useObjectType,projectId:$('#projectId').val(),planTemplateId:'${planTemplateId}'}
        }
        $.ajax({
            type : 'POST',
            url : 'planTemplateController.do?inputsList',
            async : false,
            data : datas,
            success : function(data) {
                try {
                    /*var newDataObj = new Object();
                    var newDataArr = new Array();
                    var newData = null;
                    if(data!=null && data['rows']!=null && data['rows'].length>0) {
                        for(var i=0; i<data['rows'].length; i++) {
                            newData = data['rows'][i];
                            newData['id'] = newData['tempId'];
                            newDataArr.push(newData);
                        }
                        newDataObj['rows'] = newDataArr;
                        newDataObj['total'] = data['total'];
                    }*/
                    $("#inputsList").datagrid("loadData",data);
                }
                catch(e) {
                    //alert('3e:'+e)
                }
                finally {
                    flg= true;
                }
            }
        });
        return flg;
    }

    function closePlan_() {
        /* 	$.ajax({
                type : 'POST',
                data : {useObjectId : $('#useObjectId').val()},
                url : 'inputsController.do?clearRedis',
                cache : false,
                success:function(data){

                }
            }); */
        $.fn.lhgdialog("closeSelect");
    }

    function initDocument(this_) {
        var datas = new Object();
        var flg=false;
        if(this_ != null && this_ != '' && this_ != undefined){
            datas=this_;
        } else {
            datas.useObjectId = $('#useObjectId').val();
            var useObjectType = $('#useObjectType').val();
            if (!useObjectType) {
                useObjectType = "PLANTEMPLATE";
            }
            datas.useObjectType = useObjectType;
        }
        $.ajax({
            type : 'POST',
            url : 'deliverablesInfoController.do?list',
            async : false,
            data : datas,
            success : function(result) {
                try {
                    $("#deliverablesInfoList").datagrid({data: result.rows});
                }
                catch(e) {
                    //alert('3e:'+e)
                }
                finally {
                    flg= true;
                }
            }
        });
        return flg;
    }
</script>
</html>