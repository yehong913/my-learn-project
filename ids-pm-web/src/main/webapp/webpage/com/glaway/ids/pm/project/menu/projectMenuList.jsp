<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--<c:if test="${iframeFlag eq 1}">--%>
<!DOCTYPE html>
<%@include file="/context/mytags.jsp"%>
<head>
	<meta http-equiv="X-UA-Compatible" content="edge"></meta>
<%--</c:if>--%>

	<script src="webpage/com/glaway/ids/pm/project/projectmanager/common.js"></script>
	<script src="${pageContext.request.contextPath}/plug-in/js/highcharts7.0.2/highcharts.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/plug-in/js/highcharts7.0.2/oldie.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	//获取当前iframe高度
	function getTabiframeHeight(){

		return $("#tabiframe").height();
	}

	// 名称链接事件
	function loadUrl(event, treeId, treeNode) {

		setTimeout(function() {
			loadPage("#right_page_panel", treeNode.dataObject.url + '&refreshTree=false', treeNode.name);
			jeasyui.util.panelMask('right_page_panel','open','');
		}, 500);
		setTimeout(function() {
			jeasyui.util.panelMask('right_page_panel','close');
		}, 1000);
	}

	function refreshRightPage() {
		$("#right_page_panel").panel("open").panel("refresh");
	}


	/**
	* id=panelId的panel加载laod url
	* @param panelId
	* @param url
	* @param title 标题
	*/
	function loadPage(panelId,url, title) {
        if(null!=url&&""!=url){
            /*
            * 此处为判断该节点是是否需要添加ifarme通过url参数读取满足不同需求指标
            *
            * 								--朱聪 2016/6/13
            * */
            if (url.indexOf('isIframe') != -1) {
                try {
                    $(panelId).panel({
                    href : "",
                    title : title,
                    content:createFrameForIDS(url)
                    });
                } catch (e) {
                // TODO: handle exception
                }
            }else{
                $(panelId).panel({
                    href : url,
                    title : title
                });
            }

        }

	}

	function createFrameForIDS(url) {
		var s = '<iframe name="tabiframe" id="tabiframe"   frameborder="0"  src="'+url+'" style="width:100%;height:98%;overflow-x:hidden; overflow-y: auto;"></iframe>';
		return s;
	}


	// 更新最近操作记录
	// 已无效，在进入每个页面后再刷新，而不再统一刷新
	function updateRecentlyList(event, treeNode, flag) {
		/*var insertFlag = treeNode.dataObject.insertRecently;
		var projectId = treeNode.dataObject.projectId;
		if (insertFlag == "1" && projectId != null && projectId != "") {
			$.ajax({
				url : "projectMenuController.do?updateRecentlyList",
				data : {
					projectId : treeNode.dataObject.projectId,
					insertFlag : insertFlag
				},
				type : 'post',
				cache : false,
				success : function(result) {
					loadTree(null, "1");
				}
			});
		}*/
	}

	//重新加载树
	function loadTree(insertProjectId, insertRecent, detailFlag) {
		var url = "/ids-pm-web/projectMenuController.do?list";
		if (insertRecent != "1") {
			return;
		}
		if ("" != insertProjectId) {
			url += "&insertProjectId=" + insertProjectId;
		}

		$.ajax({
			url : url,
			type : 'post',
			cache : false,
			success : function(result) {
				$.fn.zTree.init($("#menuList"), settingmenuList, result);
				if(detailFlag == "detail"){
					$.fn.zTree.getZTreeObj("menuList").selectNode($.fn.zTree.getZTreeObj("menuList").getNodes()[0].children[1].children[0].children[0]);
				}
				else if(detailFlag == "plan"){
					$.fn.zTree.getZTreeObj("menuList").selectNode($.fn.zTree.getZTreeObj("menuList").getNodes()[0].children[1].children[0].children[1]);
				}
				else if(detailFlag == "projRoles"){
					$.fn.zTree.getZTreeObj("menuList").selectNode($.fn.zTree.getZTreeObj("menuList").getNodes()[0].children[1].children[0].children[2]);
				}
				else if(detailFlag == "projLib"){
					$.fn.zTree.getZTreeObj("menuList").selectNode($.fn.zTree.getZTreeObj("menuList").getNodes()[0].children[1].children[0].children[3]);
				}
				else if(detailFlag == "planRisk"){
					$.fn.zTree.getZTreeObj("menuList").selectNode($.fn.zTree.getZTreeObj("menuList").getNodes()[0].children[1].children[0].children[4]);
				}
				else if(detailFlag == "projStastistics"){
					$.fn.zTree.getZTreeObj("menuList").selectNode($.fn.zTree.getZTreeObj("menuList").getNodes()[0].children[1].children[0].children[5]);
				}
				else if(detailFlag == "planProblem"){
					$.fn.zTree.getZTreeObj("menuList").selectNode($.fn.zTree.getZTreeObj("menuList").getNodes()[0].children[1].children[0].children[6]);
				}

			}
		})
	}

	$(document).ready(
		function() {
			var fromType = '${fromType}'
			var projectId = '${projectId}';
			var taskDetailGetPlanId = '${taskDetailGetPlanId}';

			var loadUrl = "";
			var loadTitle = "";
			if (fromType == 'fromProjectPortlet' || fromType == 'fromLaborLoad') { // 人员负载分析
				loadUrl = '/ids-pm-web/projectController.do?viewDetailBaseInfo&id=' + projectId;
				loadTitle = '详细信息';
			}
			else if (fromType == 'fromProjectAnalysis') { // 项目分析
				loadUrl = '/ids-pm-web/planController.do?plan&isIframe=true&afterIframe=true&id=' + projectId;
				loadTitle = '计划';
			}
			else if (fromType == 'fromProjectBoard') { // 项目看板
				loadUrl = '/ids-pm-web/projStatisticsController.do?goStatistics&isIframe=true&id=' + projectId;
				loadTitle = '统计分析';
			}
			else if (fromType == 'fromTaskOpenRisk') { // 风险类研发任务
				loadUrl = '${riskproblems_Nginx}/riskController.do?goPlanRisk&clickFunctionId=4028f006549dc2ca01549dd6f911001f&id=' + projectId;
				loadTitle = '风险清单';
			}
			else if (fromType == 'fromTaskOpenProblem') { // 风险类研发任务
				loadUrl = '${riskproblems_Nginx}/riskProblemTaskController.do?goProblemsForPlan&id=' + projectId;
				loadTitle = '问题';
			}
			else if (taskDetailGetPlanId != "") { // 当计划不为空时，指向计划列表，使计划高亮展示
				loadUrl = '/ids-pm-web/planController.do?plan&isIframe=true&afterIframe=true&id=' + projectId
						+ '&taskDetailGetPlanId=' + taskDetailGetPlanId;
				loadTitle = '计划';
			}
			else { // 消息通知、项目检索查看
				if (projectId != "") {
					loadUrl = '/ids-pm-web/projectController.do?viewDetailBaseInfo&id=' + projectId;
					loadTitle = '详细信息';
				}
			}

			if (projectId != "") {
				setTimeout(function() {
					loadPage("#right_page_panel", loadUrl + '&refreshTree=true', loadTitle);
				}, 1000);
			} else {
				setTimeout(function(){
					setDefaultSelectedNode();
				},200);
			}
		});

	//页面加载默认选中项目列表
	function setDefaultSelectedNode() {
		var treeObj = $.fn.zTree.getZTreeObj("menuList");
		// 获取全部节点
		var nodes = treeObj.transformToArray(treeObj.getNodes());
		// 查找设置的value
		if (nodes.length >= 1) {
			var node = nodes[1];
			treeObj.selectNode(node);
			treeObj.setting.callback.onClick(null, node.id, node);
		}
	}

</script>

<div class="easyui-layout" fit="true">
	<div region="west" style="padding: 0px; width: 230px" title="项目列表" id="left_page_panel">
		<fd:tree treeIdKey="id" url="/ids-pm-web/projectMenuController.do?list"
			treeName="name" treeTitle="" id="menuList" treePidKey="pid"
			beforeClickFunName="updateRecentlyList" onClickFunName="loadUrl" />
	</div>
	<div region="center" style="padding-left: 5px;background: #E6EEF8;">
		<div  title="&nbsp;" fit="true" border="false" id="right_page_panel"></div>
	</div>
</div>
