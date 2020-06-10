<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<style type="text/css">
.page { width:100%;background:#f0f0f0 }
/* page */
li{list-style:none}
.about{box-shadow:0;-webkit-box-shadow:0;-moz-box-shadow:0;}
.header{width:940px;height:90px;margin:0 auto;z-index:8;}
.link{margin-top:30px;float:right;text-align:right;_width:718px;}
.link li{float:left;display:inline;margin-left:60px;}
.link li a{color:#4F4E4E;font-size:16px;font-weight:500;padding-bottom:6px;display:block;}
.link li.active{border-bottom:2px solid #0066ff;}
.link li.active a{color:#0066FF  }
.link li:hover{border-bottom:2px solid #0066ff;color:#0066FF  }
.link li a:hover{color:#0066FF  }.box{margin:18px auto 0 30px;display:inline-block}
.event_year{text-align:center;float:left;margin-top:10px;}
.event_year li{height:40px;line-height:40px;background:#FFF;margin-bottom:1px;font-size:18px;color:#828282;cursor:pointer;}
.event_year li div{display:block;}
.event_year li.current{width:61px;background:#0066ff url('webpage/com/glaway/ids/project/statisticalAnalysis/img/jian.png') 60px 0 no-repeat;color:#FFF;text-align:left;padding-left:9px;}
.event_list{padding:0;width:850px;float:right;background:url('webpage/com/glaway/ids/project/statisticalAnalysis/img/dian3.png') 139px 0 repeat-y #f0f0f0;margin:10px 0 20px 0;}
.event_list h3{margin:0 0 10px 132px;font-size:16px; line-height:20px;font-family:Georgia;padding-left:25px;background:url('webpage/com/glaway/ids/project/statisticalAnalysis/img/jian.png') 0 -55px no-repeat;font-style:italic;}
.event_list .finish{color:#0066ff;}
.event_list .unfinish{color:#999;}
.event_list li{background:url('webpage/com/glaway/ids/project/statisticalAnalysis/img/jian.png') 136px -80px no-repeat;}
.event_list li span{width:127px;text-align:right;display:block;float:left;margin-top:14px;}
.event_list li p{width:680px;margin-left:24px; margin-top:0;display:inline-block;padding-left:10px;background:url('webpage/com/glaway/ids/project/statisticalAnalysis/img/jian.png') -21px 0 no-repeat;line-height:25px;_float:left;}
.event_list li p span{width:650px;text-align:left;border-bottom:2px solid #DDD;padding:10px 15px;background:#FFF;margin:0;}
</style>

 	<div class="page">
		<div class="box">
			<ul class="event_year">
				<c:forEach items="${voList}" var="vo">
					<c:if test="${vo.milestone eq 1 }">
						<c:if test="${vo.mcount eq 1 }">
							<li class="current"><label for="${vo.mcount}"><div>M${vo.mcount}</div></label></li>
						</c:if>
						<c:if test="${!(vo.mcount eq 1) }">
							<li><label for="${vo.mcount}"><div>M${vo.mcount}</div></label></li>
						</c:if>
					</c:if>
				</c:forEach>
			</ul>

			<ul class="event_list">
				<c:forEach items="${voList}" var="vo">
						<c:if test="${vo.milestone eq 1 }">
							<c:if test = "${vo.divflag eq 0 }">
								<c:if test = "${vo.rate eq '100.00%' }">
									<div>
										<h3 id="${vo.mcount}" class="finish">M${vo.mcount}: ${vo.endtime }; ${vo.pname}; ${vo.aname }; ${vo.rate }</h3>
									</div>
								</c:if>
								<c:if test = "${!(vo.rate eq '100.00%') }">
									<div>
										<h3 id="${vo.mcount}" class="unfinish">M${vo.mcount}: ${vo.endtime }; ${vo.pname}; ${vo.aname }; ${vo.rate }</h3>
									</div>
								</c:if>
							</c:if>
						    <c:if test="${vo.divflag eq 1 }">
						    	<c:if test = "${vo.rate eq '100.00%' }">
									<div>
										<h3 id="${vo.mcount}" class="finish">M${vo.mcount}: ${vo.endtime }; ${vo.pname}; ${vo.aname }; ${vo.rate }</h3>
									</div>
								</c:if>
							    <c:if test = "${!(vo.rate eq '100.00%') }">
									<div>
										<h3 id="${vo.mcount}" class="unfinish">M${vo.mcount}: ${vo.endtime }; ${vo.pname}; ${vo.aname }; ${vo.rate }</h3>
									</div>
								</c:if>
							</c:if>
						</c:if>
						<c:if test="${!(vo.milestone eq 1) }">
							<c:if test = "${vo.rate eq '100.00%' }">
								<li><span class="finish">${vo.endtime }</span>
									<p>
										<span class="finish">${vo.pname }; ${vo.aname }; ${vo.rate }</span>
									</p></li>
							</c:if>
							<c:if test = "${!(vo.rate eq '100.00%') }">
								<li><span class="unfinish">${vo.endtime }</span>
									<p>
										<span class="unfinish">${vo.pname }; ${vo.aname }; ${vo.rate }</span>
									</p></li>
							</c:if>
						</c:if>
				</c:forEach>
			</ul>
			<div class="clearfix"></div>
		</div>
	</div>
	
	<script type="text/javascript">
		$(function() {
			$('label').click(
					function() {
						$('.event_year>li').removeClass('current');
						$(this).parent('li').addClass('current');
						var year = $(this).attr('for');
						$('#' + year).parent().prevAll('div').slideUp(50);
						$('#' + year).parent().slideDown(100).nextAll('div')
								.slideDown(50);
					});
		});
	</script>

</html>