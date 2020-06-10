<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="easyui"></t:base>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plug-in/font/css/index.css"/>
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath }/plug-in/css/kesIndex/newstyle.css"/>
    <title></title>
    <!-- 单点登录需要页面样式 -->
    <c:if test="${httpFlag == 1}">
        <t:base type="jquery,easyui_iframe,tools"></t:base>
    </c:if>
</head>
<style type="text/css">
    body {
        margin-left: 0px;
        margin-top: 0px;
        margin-right: 0px;
        margin-bottom: 0px;
        font-size: 12px;
    }

    a:link {
        /*color: #333*/;
        text-decoration: none;
    }

    a:visited {
        /*color: #333*/;
        text-decoration: none;
    }

    .know_search {
        text-align: center
    }

    .know_search .search_text {
        width: 400px;
        height: 24px;
        font-size: 14px;
        line-height: 24px;
        vertical-align: middle;
        border: 2px solid #39c;
        margin-right: 0;
        padding: 2px 4px;
    }

    .know_search .search_btn {
        height: 32px;
        line-height: 32px;
        vertical-align: middle;
        font-size: 14px;
        text-align: center;
        width: 80px;
        background: #39c;
        border: 0;
        padding: 0;
        margin-left: 0;
        cursor: pointer;
        color: #fff;
        font-weight: bold
    }

    .know_search p a {
        margin: 0 5px;
        color: #000;
        font-size: 12px
    }

    .know_search p a:link {
        text-decoration: none;
    }

    .know_search p a:visited {
        color: #03F;
        text-decoration: none;
    }

    .know_search p a:hover {
        color: #F60;
        text-decoration: none;
    }

    .know_search p a:active {
        text-decoration: none;
    }

    .kmlistResult {
        width: 940px;
        margin-top: 20px;
        float: right;
    }

    .search_number {
        padding: 10px 0px 5px 0px;
        color: #999999;
    }

    .search_number .search_box .ml10 a:hover {
        color: #F60;
        text-decoration: none;
    }

    .search_number .kmlistResult .search_box .ml10 a {
        color: #03F;
        font-size: 55px
    }

    .search_main {
        margin: 10px;
    }

    .search_main .marginRight {
        margin-right: 30px;
    }

    .search_main .title i {
        margin-left: 200px;
        color: #999
    }

    .search_main .sort {
        margin: 8px 0
    }


    /*-------------------------------------置顶样式开始-----------------------------------------*/
    .topshow {
        background: #a8a8a8;
        border-radius: 3px;
        background-image: url(plug-in/img/idsicons/arrow_up.png);
        width: 50px;
        height: 50px;
        background-position: -200px -300px;
    }

    .topshow:hover {
        background: #307ed7;
        background-image: url(plug-in/img/idsicons/arrow_up.png);
    }

    .toppos {
        /* left: 58%; */
        position: fixed;
        bottom: 80px;
        margin: 0px 0px 0px 480px;
    }

    /*-------------------------------------置顶样式结束-----------------------------------------*/

</style>
<script type="text/javascript">


    $(function () {
        $('#outputPaginationForHighScoreAward6').pagination({
            pageNumber: '${pageNumber}',
            total: '${total}',
            pageSize: 10,
            layout: ['links'],
            onSelectPage: function (pageNumber, pageSize) {
                $(this).pagination('loading');
                onSelectPage(pageNumber, pageSize);
                $(this).pagination('loaded');
            }
        });
    })


    //回车进入检索
    function tabEnterSearch() {
        if (event.keyCode == 13) {
            document.getElementById("tabSearch").click();
        }
    }

    //普通检索
    function goSearch() {
        debugger;
        $('#begin').hide();
        $('#after').show();
        var inPut = $("#correct #searchInput").val();
        inPut01 = encodeURI(encodeURI(inPut));
        if ($.trim(inPut).length == 0) {
            tip("<spring:message code="com.glaway.ids.knowledge.emptyKeyword"/>");
            return false;
        } else if ($.trim(inPut).length > 300) {
            tip("<spring:message code="com.glaway.ids.knowledge.keywordLength"/>");
            return false;
        } else {
            remInput = $("#searchInput").val();
            $('#searchResult').panel({
                href: 'taskDetailController.do?goSearchAgain&inPut=' + inPut01 + '&searchType=${searchType}'+'&isEnableFlag=${isEnableFlag}'+'&taskId=${taskId}'+'&referenceType=${referenceType}'
            });
        }
    }

    //热词检索
    function goHotSearch(input) {
        $('#begin').hide();
        $('#after').show();
        $("#correct #searchInput").val(input);
        remInput = $("#searchInput").val();
        var inPut01 = encodeURI(encodeURI(input));
        $('#searchResult').panel({
            href: 'taskDetailController.do?goSearchAgain&inPut=' + inPut01 + '&searchQuery=true&searchType=${searchType}'+'&isEnableFlag=${isEnableFlag}'+'&taskId=${taskId}'+'&referenceType=${referenceType}'
        });
    }


    var remInput = $("#searchInput").val();

    //结果检索
    function resultSearch() {
        $('#begin').hide();
        $('#after').show();
        var beforeInput = remInput;
        var input11 = $("#searchInput").val();
        if ($.trim(input11).length == 0) {
            tip("<spring:message code="com.glaway.ids.knowledge.emptyKeyword"/>");
            return false;
        }
        if ($.trim(beforeInput).length > 0 && $.trim(beforeInput) != 'undefined') {
            remInput = input11 + ' AND ' + beforeInput;
        } else {
            beforeInput = '';
            remInput = input11;
        }
        var resultInput = $("#correct #searchInput").val();
        beforeInput = encodeURI(encodeURI(beforeInput));
        resultInput = encodeURI(encodeURI(resultInput));
        if ($.trim(resultInput).length == 0) {
            tip("<spring:message code="com.glaway.ids.knowledge.emptyKeyword"/>");
            return false;
        } else if ($.trim(resultInput).length > 300) {
            tip("<spring:message code="com.glaway.ids.knowledge.keywordLength"/>");
            return false;
        } else {
            $('#searchResult').panel({
                href: 'taskDetailController.do?goResultSearch&beforeInput='
                    + beforeInput + '&resultInput=' + resultInput + '&searchType=${searchType}'+'&isEnableFlag=${isEnableFlag}'+'&taskId=${taskId}'+'&referenceType=${referenceType}'
            });
        }
    }

    //高级检索--弹出检索条件窗口
    function goDetailSearch() {
        $("#correct #searchInput").val(" ");
        var url = '/kes-klm-web/kesSearchController.do?goDetailSearch&flag=search' + '&searchType=${searchType}';
        createDialog('knowledgeDetailSearchDialog', url);
    }

    //执行高级检索
    function doDetailSearch() {
        $('#searchResult').panel({
            href: 'taskDetailController.do?goDetailSearchDeal&flag=' + 2 + '&searchType=${searchType}'
        });
        $('#begin').hide();
        $('#after').show();
    }

    //排序
    function searchSortBy(sortElement) {
        $('#begin').hide();
        $('#after').show();
        var updateTimeSortType = $("#updateTimeSortType").val();
        var authorNameSortType = $("#authorNameSortType").val();
        var solrQueryStr = $("#searchInput").val();
        var url = '/kes-klm-web/kesSearchController.do?searchByElementType&solrQueryStr='
            + encodeURI(encodeURI(solrQueryStr));
        if ("default" != sortElement) {
            if ("updatetime" == sortElement) {
                url = url + '&sortElement=' + sortElement + '&sortType='
                    + updateTimeSortType;
            } else {
                url = url + '&sortElement=' + sortElement + '&sortType='
                    + authorNameSortType;
            }
        }
        url = url + '&searchType=${searchType}';
        $('#searchResult').load(encodeURI(url));
    }

    //查看知识
    function viewKnowledgeItemInfo(id, title) {
        debugger;
        var tabTitle = "知识详情";
        url = '/kes-klm-web/knowledgeNavigationController.do?goKnowledgeItemDetailNewForIDS&id='+ id + '&browser=true&isIframe=true';
        var win = window.open(url, "_blank");
        setTimeout(function () {
            win.document.title = tabTitle;
        }, 500);
        /*$.ajax({
            url: '/kes-klm-web/kesSearchController.do?checkPower&id=' + id,
            type: 'post',
            cache: false,
            success: function (data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    if (d.obj.detailHidden == 'true' || d.obj.detailHidden) {
                        var tabTitle = "知识详情";
                        var url = '/kes-klm-web/knowledgeNavigationController.do?goKnowledgeItemDetailNew&isIframe=true&id=' + id + '&name=' + title + '&browser=true' + '&showPower=' + d.obj.showHidden + '&downloadPower=' + d.obj.downloadHidden;
                        var win = window.open(url, "_blank");
                        setTimeout(function () {
                            win.document.title = tabTitle;
                        }, 500);

                    } else {
                        top.tip('<spring:message code="com.glaway.ids.knowledge.source.notShowDetail"/>');
                    }
                } else {
                    top.tip('<spring:message code="com.glaway.ids.knowledge.source.notShowDetail"/>');
                }
            }
        });*/
        //$('#knowledgeItemInfoDialog').lhgdialog("open","url:"+url);
    }

    $(function ($) {
        //置顶
        topscroll("topid");
        var pageHits = '${sdoc.hits}';
        if (pageHits == "") {
            pageHits = 0;
        }
        //分页
        $('#outputPagination').pagination({
            pageNumber: '${sdoc.page}',
            total: pageHits,
            pageSize: '${sdoc.pageSize}',
            layout: ['links'],
            onSelectPage: function (pageNumber, pageSize) {
                $(this).pagination('loading');
                onSelectPage(pageNumber, pageSize);
                $(this).pagination('loaded');
            }
        });
    });

    function onSelectPage(pageNumber, pageSize) {
        $('#begin').hide();
        $('#after').show();
        var updateTimeSortType = '${updateTimeSortType}';
        var authorNameSortType = $("#authorNameSortType").val();
        var sortElement = $("#sortElement").val();
        var solrQueryStr = '${solrQueryStr}';
        var url = 'taskDetailController.do?searchByElementType&solrQueryStr='
            + encodeURI(encodeURI(solrQueryStr))+'&isEnableFlag=${isEnableFlag}'+'&taskId=${taskId}'+'&referenceType=${referenceType}';
        if ("updatetime" == sortElement) {
            url = url + '&sortElement=' + sortElement + '&sortType='
                + updateTimeSortType;
        } else {
            url = url + '&sortElement=' + sortElement + '&sortType='
                + authorNameSortType;
        }
        url = url + '&searchType=${searchType}';
        $('#searchResult').load(encodeURI(url));
    }


    function inputAuto() {
        var input = $("#searchInput").val();
        var input01 = encodeURI(encodeURI(input));
        url = '/kes-klm-web/kesSearchController.do?inputAutoSuggest&input=' + input01;
        $.ajax({
            url: url + '&searchType=${searchType}',
            type: 'post',
            cache: false,
            success: function (data) {
                var d = $.parseJSON(data);
                alert(d);
                return;
            }
        });

    }

    //联想词关联
    $(function () {

        using('./jquery-autocomplete/jquery.autocomplete.css');
        using('./jquery-autocomplete/jquery.autocomplete.js', function () {
            $('#searchInput').autocomplete(
                "/kes-klm-web/kesSearchController.do?inputAutoSuggest", {
                    max: 10,
                    minChars: 1,
                    width: 412,
                    scrollHeight: 100,
                    matchContains: true,
                    autoFill: false,
                    extraParams: {
                        featureClass: "P",
                        style: "full",
                        maxRows: 10,
                        textField: "userName",
                        valueField: "userName",
                        searchField: "userName",
                        entityName: "",
                        param: function () {
                            return $('#searchInput').val();
                        }
                    },
                    parse: function (data) {

                        var parsed = [];
                        $.each(data.rows, function (index, row) {
                            parsed.push({
                                data: row,
                                result: row,
                                value: row.id
                            });
                        });
                        return parsed;
                    },
                    formatItem: function (row, i, max) {
                        return row.userName;
                    }
                }).result(function (event, row, formatted) {
                $('#searchInput').val(row.userName);
            });
        });
    });
    /* top置顶结束 */

    //选中作为参考
    function selectReference(docId, taskId, type, library, code){
        debugger
        var checked = $('#'+ docId + 'checkbox').attr('checked');
        var url = 'knowledgeReferenceForIDSController.do?selectReference';
        if(checked != 'checked'){
            url = 'knowledgeReferenceForIDSController.do?cancelReference';
        }
        $.ajax({
            url : url,
            cache : false,
            type : 'post',
            data : {
                taskId : taskId,
                type : type,
                libId : library,
                code : code
            },
            success : function(data) {
                var d=$.parseJSON(data);
                if(d.success){
                    debugger;
                    var win = $.fn.lhgdialog("getSelectParentWin");
                    //延迟200毫秒
                    setTimeout(function(){ win.reloadKnowledgeReference(); },200);

                    top.tip(d.msg);

                }
            }
        });
    }

    //取消选择的参考
    function cancelReference(docId, taskId, type, library, code){
        var checked = $('#'+ docId + 'checkbox').attr('checked');
        var url = 'knowledgeReferenceForIDSController.do?selectReference';
        if(checked != 'checked'){
            url = 'knowledgeReferenceForIDSController.do?cancelReference';
        }
        $.ajax({
            url : url,
            cache : false,
            type : 'post',
            data : {
                taskId : taskId,
                type : type,
                libId : library,
                code : code
            },
            success : function(data) {
                var d=$.parseJSON(data);
                if(d.success){
                    var win = $.fn.lhgdialog("getSelectParentWin");
                    //延迟200毫秒
                    setTimeout(function(){ win.reloadKnowledgeReference(); },200);
                    top.tip(d.msg);

                }
            }
        });
    }



</script>
<div id="result" class="w" fit="true"
     style="height: 90%; overflow: auto;">
    <div class="k_search" id="correct">
        <div class="input_wrap_new">
            <input id="searchInput" type="text" value="${input01}" onkeydown="tabEnterSearch()"/>
        </div>
        <div class="btn_search">
            <a id="tabSearch" href="#" onclick="goSearch()"><img
                    src="plug-in/img/idsicons/btn_search.png"><spring:message
                    code='com.glaway.ids.knowledge.search.index'/></a>
        </div>

        <span class="Search_r">
    	<a id="resultSearch" href="#" onclick="resultSearch()"><spring:message
                code='com.glaway.ids.knowledge.search.resultIndex'/></a>
		<%--<a id="lg" href="#" onclick="goDetailSearch()" ><spring:message code='com.glaway.ids.knowledge.detailIndex'/></a>--%>
		</span>

        <c:if test="${!empty hotWordList}">
            <div class="hot">
                <spring:message code='com.glaway.ids.knowledge.knowIndex.hotIndex'/>：
                <c:forEach items="${hotWordList}" var="rs">
                    <a href="javascript:goHotSearch('${rs}');">${rs} </a>
                </c:forEach>
            </div>
        </c:if>
    </div>
    <div id="begin">
        <input id="updateTimeSortType" name="updateTimeSortType" type="hidden"
               value="${updateTimeSortType}"> <input id="authorNameSortType"
                                                     name="authorNameSortType" type="hidden"
                                                     value="${authorNameSortType}">
        <input id="sortElement" name="sortElement" type="hidden"
               value="${sortElement}">
        <div class="sort_row">
			<span>
			<spring:message code="com.glaway.ids.knowledge.sort"/>：</span>
            <span>
			    <span  class="sort_f"><a href="javascript:void(0);"
                                                  onclick="searchSortBy('default')">默认</a></span>
			    <span  class="sort_f"><a href="javascript:void(0);"
                                                  onclick="searchSortBy('updatetime')">发布时间</a></span>
			    <span  class="sort_f"><a href="javascript:void(0);" onclick="searchSortBy('authorName')">作者</a></span>
			</span>
        </div>
        <p>

        <div class="sort_result">
            <c:if test="${!empty sdoc.hits}">
                <span>找到<span class="redtxt">${sdoc.hits}</span>条结果</span>
                <span class="ml10">（用时 <span class="redtxt">${sdoc.qTime}</span> 秒）</span>
            </c:if>
            <c:if test="${empty sdoc.hits}">
                <span>找到0条结果</span>
                <span class="ml10">（用时 0.000 秒）</span>
            </c:if>
        </div>
        </p>
        <div style="margin-left: 10px" class="search_main">
            <c:if test="${!empty docs}">
                <c:forEach items="${docs}" var="doc">
                    <div class="list">
                        <p class="title">
                            <c:if test="${empty isEnableFlag}">
                                <c:if test="${!empty taskId}">
                                    <c:if test="${empty doc.checked}">
                                        <c:if test="${!empty doc.fromTemplate}">
                                            <input id="${doc.id}checkbox"
                                                   onclick="selectReference('${doc.id}','${taskId}','${referenceType}','${doc.library}','${doc.code}')"
                                                   type="checkbox" readonly="readonly"/>
                                        </c:if>
                                        <c:if test="${empty doc.fromTemplate}">
                                            <input id="${doc.id}checkbox"
                                                   onclick="selectReference('${doc.id}','${taskId}','${referenceType}','${doc.library}','${doc.code}')"
                                                   type="checkbox"/>
                                        </c:if>
                                    </c:if>

                                    <c:if test="${!empty doc.checked}">
                                        <input id="${doc.id}checkbox"
                                               onclick="cancelReference('${doc.id}','${taskId}','${referenceType}','${doc.library}','${doc.code}')"
                                               type="checkbox" checked="checked"/>
                                    </c:if>
                                </c:if>
                            </c:if>
                            <a href="javascript:void(0);"
                               onclick="viewKnowledgeItemInfo('${doc.id}', '${doc.oldTitle}')">${doc.title}</a>
                        </p>
                        <div>
                            <i style="font-style:normal;">
                                <span class="expert1 qanda_person"><img
                                        src="plug-in/img/idsicons/user_avatar/24x24male1.png"></span>
                                <span class="marginright"><spring:message
                                        code="com.glaway.ids.knowledge.author"/>:${doc.authorName}</span>
                                <span class="marginright"><spring:message
                                        code="com.glaway.ids.knowledge.publishTime"/>:${doc.updateTime}</span>
                            </i>

                            <span class="sort">
							<span class="bluefonts source_know">[<spring:message
                                    code="com.glaway.ids.knowledge.category"/>]</span>
									<c:if
                                            test="${!empty doc.categorys}">
                                        <c:forEach items="${doc.categorys}" var="category"
                                                   varStatus="loop">
                                            <span>${category}</span>
                                        </c:forEach>
                                    </c:if>
						</span>

                            <span class="sort">
							<span class="bluefonts source_know">
							[<spring:message code="com.glaway.ids.knowledge.source"/>]</span>${doc.libraryName}

						</span>


                        </div>
                        <div class="summary">
                            <spring:message code="com.glaway.ids.knowledge.abstruct"/>
                            ：<span id="zhaiYao">${doc.abstruct}</span>
                        </div>
                    </div>
                    <div class="sline"></div>
                </c:forEach>
            </c:if>
        </div>
        <div id="outputPaginationForHighScoreAward6" class="easyui-pagination"
             style="padding: 0 0px;width: auto;"></div>
    </div>
    <div id="after" style="display: none">
        <div id="searchResult"></div>
        <%-- <fd:panel id="searchResult" border="0">
        </fd:panel> --%>
    </div>
    <fd:dialog id="knowledgeDetailSearchDialog" width="750px"
               height="450px" modal="true"
               title="{com.glaway.ids.knowledge.detailIndex}">
    </fd:dialog>
    <fd:dialog id="knowledgeItemInfoDialog" modal="true" height="500px" width="1000" maxFun="true">
    </fd:dialog>
</div>
