function outSystemFlowTaskDeliverSynchronization(parentPlanId, flowTaskId, deliverArr){
	$.ajax({
		url : 'taskFlowChangeController.do?updateFlowTaskRelationInfo',
		type : "POST",
		data : {
			parentPlanId : parentPlanId, 
			flowTaskId : flowTaskId, 
			deliverArrStr : $.toJSON(deliverArr)
		},
		async : false,
		cache : false,
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				
			}
		}
	});
}

function outSystemSynchronizationInput(type, parentPlanId){
	
}