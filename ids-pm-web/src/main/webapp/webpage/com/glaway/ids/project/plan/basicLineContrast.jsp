<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" charset="UTF-8">
	<title>基线对比</title>
	<t:base type="jquery,easyui,tools"></t:base>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" style="height:80px">
		<table>
			<tr>
				<td>
					<fd:combobox id="dataCombox" name="data" textField="name"  onClickButton="contrastBasicLine" buttonIcon="icon-ok" title="基线对比" valueField="id" url="${pageContext.request.contextPath }/basicLineCompareController.do?dataCombox" style="width:200px;" multiple="true" panelMaxHeight="200"></fd:combobox>
				</td>
			</tr>
			<tr>
				<td>
					<span style="height:5px;width:15px;margin-right:5px;">&nbsp;&nbsp;&nbsp;</span>
					<span style="height:5px;width:15px;margin-right:5px;">&nbsp;&nbsp;&nbsp;</span>
					<font size="2"><spring:message code="com.glaway.ids.common.btn.remove"/></font>
					<span style="height:5px;width:15px;background:#DCDCDC;margin-right:5px;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<font size="2"><spring:message code="com.glaway.ids.common.btn.create"/></font>
					<span style="height:5px;width:15px;background:#BDFCC9;margin-right:5px;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<font size="2"><spring:message code="com.glaway.ids.common.btn.modify"/></font>
					<span style="height:5px;width:15px;background:#FFC0CB;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
				</td>
			</tr>
		</table> 
	</div>   
    <div data-options="region:'center'">   
        <div class="easyui-layout" data-options="fit:true" title="基线对比">   
            <div data-options="region:'west'" id="left" style="width:50%;height:100%" >
				 <fd:lazytreegrid id="table1" idField="planId" treeField="name" style="width:100%;height:100%;" imgUrl="plug-in/dhtmlxSuite/imgs/" >
					<fd:columns>	
						<fd:column field="rownumber" title="序号"></fd:column>
						<fd:column field="name" title="计划名称 " width="150"></fd:column>
						<fd:column field="planLevel" title="计划等级"></fd:column>
						<fd:column field="bizCurrent" title="状态"></fd:column>
						<fd:column field="owner" title="负责人"></fd:column>
						<fd:column field="planStartTime" title="开始时间"></fd:column>
						<fd:column field="planEndTime" title="结束时间"></fd:column>
						<fd:column field="workTime" title="工期"></fd:column>
						<fd:column field="deliverables" title="输出"></fd:column>
					</fd:columns>
					<fd:eventListener event="onLoadSuccess" listener="onloadSuccess1"></fd:eventListener>
					<fd:eventListener event="onRowSelect" listener="onRowSelect"></fd:eventListener>
				</fd:lazytreegrid> 
            </div>   
            <div data-options="region:'center'" id='right' style="width:50%;height:100%">
           		<fd:lazytreegrid id="table2" idField="planId" treeField="name" style="width:100%;height:100%;" imgUrl="plug-in/dhtmlxSuite/imgs/">
					<fd:columns>	
						<fd:column field="rownumber" title="序号"></fd:column>
						<fd:column field="name" title="计划名称 " width="150"></fd:column>
						<fd:column field="planLevel" title="计划等级"></fd:column>
						<fd:column field="bizCurrent" title="状态"></fd:column>
						<fd:column field="owner" title="负责人"></fd:column>
						<fd:column field="planStartTime" title="开始时间"></fd:column>
						<fd:column field="planEndTime" title="结束时间"></fd:column>
						<fd:column field="workTime" title="工期"></fd:column>
						<fd:column field="deliverables" title="输出"></fd:column>
					</fd:columns>
					<fd:eventListener event="onLoadSuccess" listener="onloadSuccess2"></fd:eventListener>
					<fd:eventListener event="onRowSelect" listener="onRowSelect"></fd:eventListener>
				</fd:lazytreegrid> 
            </div>   
        </div>   
    </div>   
	

	<script type="text/javascript">
	
	/* $(document).ready(
			function() {
				$('#left').scroll(function(){
					$('#right').scroll($(this).scrollTop());
					$('#right').scroll($(this).scrollLeft());
				});
				
				$('#right').scroll(function(){
					$('#left').scroll($('#right').scrollTop());
					$('#left').scroll($('#right').scrollLeft());
				});
			}); */
			function scrollLeft(){
				
				
				$('#right').scrollTop($('#left').scrollTop());
				$('#right').scrollLeft($('#left').scrollLeft());
			}
			
			function scrollRight(){
				$('#left').scrollTop($('#right').scrollTop());
				$('#left').scrollLeft($('#right').scrollLeft());
			}
	
		var mygrid_left=null,mygrid_right=null,datas=null,map_left=new Map(),map_right=new Map(),arr_left=new Array(),arr_right=new Array();
	
		function onloadSuccess1(mygrid){

			mygrid.parse(ajaxLoadData().leftTree,'js');
			
			mygrid.expandAll();
			
			mygrid_left=mygrid; 
			
		}
		
		function onloadSuccess2(mygrid){
			
			
			mygrid.parse(ajaxLoadData().rightTree,'js')
			
			mygrid.expandAll();
			
			mygrid_right=mygrid;
			
			init_data();
			
			setTimeout(function(){
				 processData()
			},500);
			
			//修改BUG 滚动条联动问题
			
			
			$('#table1 .objbox').scroll(function(){
				$('#table2 .objbox').scrollTop($('#table1 .objbox').scrollTop());
				$('#table2 .objbox').scrollLeft($('#table1 .objbox').scrollLeft());
			});
			
			$('#table2 .objbox').scroll(function(){
				$('#table1 .objbox').scrollTop($('#table2 .objbox').scrollTop());
				$('#table1 .objbox').scrollLeft($('#table2 .objbox').scrollLeft());
			});

			
		}
			
		
		/**
		*初始化数据
		**/
		function init_data(){
	
			var data=ajaxLoadData();
			
			var data_left=data.leftTree;
			
			var data_right=data.rightTree;
			
			var index=0;
			
			//递归左侧数据
			init_tree(map_left,arr_left,data_left,index);
			//递归右侧数据
			init_tree(map_right,arr_right,data_right,index);

		}
		function init_tree(map,arr,data,index){
			
			for(var i=0;i<data.length;i++){
				
				map.put(data[i].planId,data[i]);
				
				arr.push(data[i]);
				
				++index;
				
				if(data[i].rows.length > 0){

					init_tree(map,arr,data[i].rows,index);
				
				}
			}
			 
		}
		
		function onRowSelect(id){
			   //   mygrid_left.setRowTextStyle(id,'color:red;font-family:verdana;font-size:14px;');
			  //    mygrid_left.setRowTextStyle(id, "background-color: red; font-family: arial;");
			   //mygrid_left.setCellTextStyle(id,1,"color:red");
			   
			  //    mygrid_left.setStyle("background-color:navy;color:white; font-weight:bold;", "","color:red;", "");
			 
			  
		}
		
		
		function ajaxLoadData(){
			if(datas==null){
				$.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath }/basicLineCompareController.do?constructTree',
					async : false,
					data : {'id1':'${id1}','id2':'${id2}','columns':'all'},
					dataType : "json",
					success : function(data) {
						datas = data;
						$('#left').panel({title:data.leftName});
						$('#right').panel({title:data.rightName});
					},
					error : function() {
						alert('<spring:message code="com.glaway.ids.pm.project.plan.basicLine.requestDataError"/>');
					}
				});
				
				
			}
			return datas;
			
		}
		
		
		
		
	
		//
		function processData(){
			//循环左侧与右侧比较
			setStyleRow(arr_left,map_right,mygrid_left,mygrid_right);
			//循环右侧
			setStyleRow(arr_right,map_left,mygrid_right,mygrid_left);

		}
		//设置行的样式
		function setStyleRow(arr_a,map_b,a_tree,b_tree){
			
			for(var i=0;i<arr_a.length;i++){
				
				var row_a=arr_a[i];
				
				var row_b=map_b.get(row_a.planId);
				
			
			
				if(row_a.belong =='leftTree'){
				
					a_tree.setRowTextStyle(row_a.id, "background-color: #DCDCDC;");
					
				}else if(row_a.belong=='rightTree'){
					
					a_tree.setRowTextStyle(row_a.id, "background-color: #BDFCC9;");
					
				}else{
					if(row_b !=null){
						
						setStyleCell(row_a,row_b,b_tree);
						
					}
					
				}
			
				
			}
		}
		//设置每单元格的样式
		function setStyleCell(data_a,data_b,mygrid){
			
			var arr=mygrid.columnIds;
			
			var temp=false;
			
			for(var i=0;i<arr.length;i++)if(temp=($.trim(data_a[arr[i]]) != $.trim(data_b[arr[i]]) && i>0))break;
			
			if(!temp)return;
			for(var i=0;i<arr.length;i++){
			
				var  a= $.trim(data_a[arr[i]]);
			
				var  b= $.trim(data_b[arr[i]]);
				
				
				if(a !=b && i>0){
					mygrid.setCellTextStyle(data_b.id,i,"background-color: #FFC0CB;color:red");
				}
				else{
					mygrid.setCellTextStyle(data_b.id,i, "background-color: #FFC0CB;color:break");
				
				}
			}

			
		}
		//隐藏每个单元格
		function contrastBasicLine(){
			
			var datacombox=$('#dataCombox').combobox('getValues');
			
			var arr=$('#dataCombox').combobox('getData');
			
			var map=new Map();
			
			for(var j=0;j<datacombox.length;j++){
				map.put(datacombox[j],datacombox[j]);
			} 
			var index=2;
			for(var i=0;i<arr.length;i++){
				
				if(map.get(arr[i].id) ||datacombox.length==0){
					$('#table1').lazytreegrid('setColumnHidden',[index,false]);
					$('#table2').lazytreegrid('setColumnHidden',[index,false]);
				}else{
					$('#table1').lazytreegrid('setColumnHidden',[index,true]);
					$('#table2').lazytreegrid('setColumnHidden',[index,true]);
				}
				++index;
			}
		}
		
	</script> 
	
</body>
</html>
