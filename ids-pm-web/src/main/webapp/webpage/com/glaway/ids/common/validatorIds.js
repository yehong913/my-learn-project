$.extend($.fn.validatebox.defaults.rules,
		{
			lessOneHundred : {
				validator : function(value, param) {
					if(value>=100){
						$(this).val('');
					}else if(value<100){
						var valueChange = value.substr(0, 5);
						$(this).val(valueChange);
						var len = $.trim(valueChange).length;
						if (len >= param[0]) {
							$(this).val(valueChange.substr(0, param[0]));
						}
					}
					return true;
				},
				message : "输入小于100两位小数"
			},
			equalOneHundred : {
				validator : function(value, param) {
					if(value>100){
						$(this).val('');
					}else if(value<100){
						var valueChange = value.substr(0, 5);
						$(this).val(valueChange);
						var len = $.trim(valueChange).length;
						if (len >= param[0]) {
							$(this).val(valueChange.substr(0, param[0]));
						}
					}
					return true;
				},
				message : "输入小于等于100两位小数"
			},
			comboboxDistinctValidate : {
				validator : function(value, param) {
					var dataVals = $("#"+param[0]).combobox("getData");
					var count = 0;
					for(var i in dataVals){
						var dataValue = dataVals[i].name;
						if(dataValue==value){//如果输入值==名称
							break;}
							
						if(dataValue.indexOf(value)==0){//如果输入值能匹配到
								break;
					}
						count++;
				}
					if(dataVals.length!= 0&& count == dataVals.length){
						$("#"+param[0]).combobox('reset');//如果匹配不到
						return false;
					}
					return true;
				},
				message : "没有找到匹配项，请重新输入"
			}
		});