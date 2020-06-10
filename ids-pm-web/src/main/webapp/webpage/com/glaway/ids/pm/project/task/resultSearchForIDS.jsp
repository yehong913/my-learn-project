<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="easyui"></t:base>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link type="text/css" rel="stylesheet"	href="${pageContext.request.contextPath }/plug-in/css/kesIndex/newstyle.css" />
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	font-size: 12px;
}

a:link {
	color: #333;
	text-decoration: none;
}

a:visited {
	color: #333;
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

.know_main {
	position: relative;
	text-align: center
}

.know_main .know_main_left {
	text-align: left;
	padding: 10px
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

.search_main {
	margin: 10px;
}

.search_main .list .summary {
	line-height: 24px;
	height: 48px;
	overflow: hidden
}

.search_main .list {
	border-bottom: 1px dashed #ccc;
	margin: 20px 0
}

.search_main .marginRight {
	margin-right: 30px;
}

.search_main .title {
	margin: 8px 0;
}

.search_main .title a {
	color: #03f;
}

.search_main .title i {
	margin-left: 200px;
	color: #999
}

.search_main .sort {
	margin: 8px 0
}
</style>

<body>
	<script type="text/javascript">

		$('#outputPaginationForHighScoreAward7').pagination({
			pageNumber:'${pageNumber}',
			total: '${total}',
			pageSize: 10,
			layout: ['links'],
			onSelectPage: function (pageNumber, pageSize) {
				$(this).pagination('loading');
				onSelectPage(pageNumber, pageSize);
				$(this).pagination('loaded');
			}
		});
		//排序
		function searchSortBy(sortElement) {
			var updateTimeSortType = $("#updateTimeSortTypeResult").val();
			var authorNameSortType = $("#updateNameSortTypeResult").val();
			var solrQueryStr = '${solrQueryStr}';
			var url = 'taskDetailController.do?searchByElementType&solrQueryStr='
				+ encodeURI(encodeURI(solrQueryStr))+'&isEnableFlag=${isEnableFlag}'+'&taskId=${taskId}';
			if ("default" != sortElement) {
				if ("updatetime" == sortElement) {
					url = url + '&sortElement=' + sortElement + '&sortType='
							+ updateTimeSortType;
				} else {
					url = url + '&sortElement=' + sortElement + '&sortType='
							+ authorNameSortType;
				}
			}
			//$('#searchResult').panel('open').panel('refresh', url);
			$('#searchResult').panel({
				href : url + '&searchType=${searchType}'
			});
		}

		//分页
		$(function() {
			$('#outputPaginationResult').pagination({
				pageNumber : '${sdoc.page}',
				total : '${sdoc.hits}',
				pageSize : '${sdoc.pageSize}',
				layout : [ 'links' ],
				onSelectPage : function(pageNumber, pageSize) {
					$(this).pagination('loading');
					onSelectPage(pageNumber, pageSize);
					$(this).pagination('loaded');
				}
			});
		});

		function onSelectPage(pageNumber, pageSize) {
			//var updateTimeSortType = $("#updateTimeSortTypeResult").val();
			var updateTimeSortType = '${updateTimeSortType}';
			var authorNameSortType = $("#updateNameSortTypeResult").val();
			var sortElement = $("#sortElementResult").val();
			var solrQueryStr = '${solrQueryStr}';
			var url = 'taskDetailController.do?searchByElementType&solrQueryStr='
					+ encodeURI(encodeURI(solrQueryStr))+'&isEnableFlag=${isEnableFlag}'+'&taskId=${taskId}'+'&referenceType=${referenceType}';
			if ("default" != sortElement) {
				if ("updatetime" == sortElement) {
					url = url + '&sortElement=' + sortElement + '&sortType='
							+ updateTimeSortType;
				} else {
					url = url + '&sortElement=' + sortElement + '&sortType='
							+ authorNameSortType;
				}
			}
			url = url + '&pageNumber=' + pageNumber + '&pageSize=' + pageSize;
			//$('#searchResult').panel('open').panel('refresh', url);
			$('#searchResult').panel({
				href : url + '&searchType=${searchType}'
			});
		}

		//查看知识
		function viewKnowledgeItemInfo(id, title) {
			var tabTitle = "知识详情";
			url = '/kes-klm-web/knowledgeNavigationController.do?goKnowledgeItemDetailNewForIDS&id='+ id + '&browser=true&isIframe=true';
			var win = window.open(url, "_blank");
			setTimeout(function () {
				win.document.title = tabTitle;
			}, 500);

			/*
			url = '/kes-klm-web/kesSearchController.do?checkPower&id=' + id;
			$.ajax({
				url : url,
				type : 'post',
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						if(d.obj.detailHidden == 'true' || d.obj.detailHidden){
							var tabTitle = "知识详情";
							var url = '/kes-klm-web/knowledgeNavigationController.do?goKnowledgeItemDetailNew&isIframe=true&id='+id+'&name='+title+'&browser=true'+'&showPower='+d.obj.showHidden+'&downloadPower='+d.obj.downloadHidden;
							var win = window.open(url,"_blank");
							 setTimeout(function(){
								win.document.title = tabTitle;
							},500);	 
						}else{
							top.tip('<spring:message code="com.glaway.ids.knowledge.source.notShowDetail"/>');
						}
					}else{
						top.tip('<spring:message code="com.glaway.ids.knowledge.source.notShowDetail"/>');
					}	
				}
			});*/
			
			//$('#knowledgeItemInfoDialog').lhgdialog("open","url:"+url);
		}

		//选中作为参考
		function selectReference(docId, taskId, type, library, code){
			debugger
			var checked = $('#'+ docId + 'checkbox').attr('checked');
			var url = '/ids-pm-web/knowledgeReferenceForIDSController.do?selectReference';
			if(checked != 'checked'){
				url = '/ids-pm-web/knowledgeReferenceForIDSController.do?cancelReference';
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
			var url = '/ids-pm-web/knowledgeReferenceForIDSController.do?selectReference';
			if(checked != 'checked'){
				url = '/ids-pm-web/knowledgeReferenceForIDSController.do?cancelReference';
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
	<div>
		<input id="updateTimeSortTypeResult" name="updateTimeSortTypeResult"
			type="hidden" value="${updateTimeSortType}"> 
		<input id="updateNameSortTypeResult" name="updateNameSortTypeResult"
			type="hidden" value="${authorNameSortType}">
		<input id="sortElementResult" name="sortElementResult" type="hidden"
			value="${sortElement}"> <input id="flag" name="flag"
			type="hidden" value="${flag}">
			
		<div class="sort_row">
			<span> <spring:message code="com.glaway.ids.knowledge.sort" />：</span> 
			  <span> 
			      <span	 class="sort_f"><a href="javascript:void(0);"	onclick="searchSortBy('default')">默认</a></span>
			      <span  class="sort_f"><a href="javascript:void(0);"	onclick="searchSortBy('updatetime')">发布时间</a></span>
			      <span  class="sort_f"><a href="javascript:void(0);"	onclick="searchSortBy('authorName')">作者</a></span>
			</span>
		</div>		
		<p>		
		
		<div class="sort_result">
			<span>找到约<span class="redtxt">${sdoc.hits}</span>条结果</span>
			<span class="ml10">（用时<span class="redtxt">${sdoc.qTime}</span> 秒）</span>
		</div>
		
		
		</p>
		<div style="margin-left: 10px" class="search_main">
			<c:if test="${!empty docs}">
				<c:forEach items="${docs}" var="doc">
					<div class="list">
						<p  class="title">
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

							<a href="javascript:void(0);" onclick="viewKnowledgeItemInfo('${doc.id}', '${doc.oldTitle}')">${doc.title}</a> 
						</p>
						
						<div>
							<i style="font-style:normal;">
							   <span class="expert1 qanda_person"><img src="plug-in/img/idsicons/user_avatar/24x24male1.png"></span>
							   <span class="marginright"><spring:message	code="com.glaway.ids.knowledge.author" />:${doc.authorName}</span>
							   <span class="marginright"><spring:message code="com.glaway.ids.knowledge.publishTime" />:${doc.updateTime}</span>
							</i>
							<span class="sort">
							      <span class="bluefonts source_know">[<spring:message code="com.glaway.ids.knowledge.category" />]</span> 
							      <c:if
									test="${!empty doc.categorys}">
									<c:forEach items="${doc.categorys}" var="category"
										varStatus="loop">
										<span>${category}</span>
									</c:forEach>
								</c:if> 
							</span>
						   <span class="sort">
							    <span class="bluefonts source_know">[<spring:message code="com.glaway.ids.knowledge.source" />]</span>
							     ${doc.libraryName}
						   </span>
						   													
						</div>
						
						<div class="summary">
							<spring:message code="com.glaway.ids.knowledge.abstruct" />
							：<span id="zhaiYao">${doc.abstruct}</span>
						</div>
						
						
					</div>
					<div class="sline"></div>
				</c:forEach>
			</c:if>
			<div id="outputPaginationForHighScoreAward7" class="easyui-pagination"
				 style="padding: 0 0px;width: auto;"></div>
		</div>
		<fd:dialog id="searchResultDialog" width="800px" height="380px"
			modal="true" title="{com.glaway.ids.knowledge.deatil}">
			<fd:dialogbutton name="{com.glaway.kes.common.btn.close}"
				callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="knowledgeItemInfoDialog" modal="true" height="500px" width="1000" maxFun="true">
		</fd:dialog>
	</div>
</body>
</html>