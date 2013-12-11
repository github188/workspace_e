function OFQSubWindowShow(messageData,protocolCode){
	//报文解析
function getOrigFrameQryMessage(messageData,protocolCode) {
		Ext.Ajax.request({
					url : 'baseapp/origFrameQry!queryOrigFrameQryMessage.action',
					params : {
						protocolCode : protocolCode,
						message	: messageData
 					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
								Ext.getCmp("origSUBMessage").setValue(messageData);
								Ext.getCmp("origSUBMessageInfo").setValue(result.messageInfo);
						}
					})
	};
	
//受电点电源信息的第一个panel
	var mapanel1 = new Ext.Panel({
		height:120,
		layout : "column",
		baseCls : "x-plain",
		style : "padding:5px",	
		items : [{
					columnWidth :.9,
					layout : "form",
					labelWidth : 100,
					defaultType : "textarea",
					baseCls : "x-plain",
					labelAlign:'right',
					items : [{
								fieldLabel : "<font color = 'blue'>报文内容</font>",
                                name:'origSUBMessage',
                                id:'origSUBMessage',
                                labelSeparator : "",
                                width:500,
                                height:100
							}]
				}]
	});
	var mapanel2 = new Ext.Panel({
		layout : "form",
		title:'报文信息',
		defaultType : "textarea",
		items : [{
			//fieldLabel : "报文内容",
								labelWidth : 1,
								style : "padding-top:5px",	
                                name:'origSUBMessageInfo',
                                id:'origSUBMessageInfo',
                                labelSeparator : "",
                                width:500,
                                height:305
				}]
	});
//电能表资产信息window--------------------------	
	var mtapanel = new Ext.FormPanel({
		//title:"客户资料",
		items:[mapanel1,mapanel2]
	});
	var OFQSubWindow = new Ext.Window({
		title:"报文解析",
		frame:true,
		width:720,
		height:490,
		layout:"form",
		draggable:false,
		modal:true,
		//labelWidth:45,
		plain:true,//设置背景颜色
		resizable:false,//不可移动
		bodyStyle:"padding:2px",
		buttonAlign:"center",//按钮的位置
		//closable:false,//设置窗体关闭按钮
		closeAction:"close",//将窗体隐藏而并不销毁
		items:[mtapanel]
	//});
		//simTrafficWindow.show();
})
	//OFQSubWindow.close();
OFQSubWindow.show();
getOrigFrameQryMessage(messageData,protocolCode);
}