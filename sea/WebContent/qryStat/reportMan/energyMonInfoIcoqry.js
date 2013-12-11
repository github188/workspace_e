//查询条件panel
var reportManMonQueryPanel = new Ext.Panel({
		region : 'center',
		height : 35,
		plain : true,
		items : [{
			baseCls : "x-plain",
			layout : "column",
			items : [{
						layout : "form",
						labelWidth : 70,
						style : "padding:5px 0px 0px 10px",
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "供电单位",
									allowBlank : false,
									readOnly:true,
									labelStyle : "text-align:right;width:40;",
									labelSeparator : "",
									width : 180
								}]
					},{
						layout : "form",
						labelWidth : 40,
						style : "padding:5px 0px 0px 40px",
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "年度",
									labelStyle : "text-align:right;width:40;",
									labelSeparator : "",
									width : 120
								}]
					}, {
						layout : "form",
						defaultType : "button",
						style : "padding:5px 0px 0px 40px",
						baseCls : "x-plain",
						items : [{
							text : "查询",
							listeners : {
								"click" : function() {alert('haha');}
							},
							width : 50
						}]
					}]
		}]
	});
