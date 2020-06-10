<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    .gla_step{
        width: auto;}
</style>
<div class="gla_div">
	<ul>
		<li class="title">流程步骤引导</li>
		<li>

			<div class="gla_step">
				<table class="step_dot" cellspacing="0" cellpadding="0" style="width: 80%">

					<tr>
						<td id="oneStep" class="stepFirst"><i></i>
							<div>
								<span>1</span>
								<div class="cont"><spring:message code="com.glaway.ids.pm.project.task.haveOrdered"/></div>
							</div>
						</td>
						<td id="twoStep" class="stepEnd"><i></i>
							<div>
								<span>2</span>
								<div class="cont"><spring:message code="com.glaway.ids.pm.project.task.feedbacking"/></div>
							</div>
						</td>
						<td id="threeStep" class="stepEnd"><i></i>
							<div>
								<span>3</span>
								<div class="cont"><spring:message code="com.glaway.ids.pm.project.task.haveFinished"/></div>
							</div>
						</td>
					</tr>

				</table>
			</div>

		</li>
	</ul>
</div>
<script type="text/javascript">
	$(function() {
		stepContrall();
	});

	//步骤图控制
	function stepContrall() {
		if ('${plan_.bizCurrent}' == "ORDERED") { //已下达状态
		} else if ('${plan_.bizCurrent}' == "FEEDBACKING") { //完工反馈中状态
			$('#twoStep').attr("class", "stepMiddle");

		} else if ('${plan_.bizCurrent}' == "FINISH") { //已完工状态
			$('#twoStep').attr("class", "stepMiddle");
			$('#threeStep').attr("class", "stepMiddle");
		}
	}
</script>