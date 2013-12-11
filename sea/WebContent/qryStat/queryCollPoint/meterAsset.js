function getDMeterMessage(value) {
         //ASSET_NO = Ext.getCmp("ASSET_NO").getValue();
        Ext.Ajax.request({
                  url : 'qrystat/ccustAction!queryDMeter.action',
                    params : {
                       ASSET_NO : value
                    },
                    success : function(response) {
                        var result = Ext.decode(response.responseText);
                        mapanel1.getForm().setValues(result.DMeterList[0]);
                        addStr = '';
                        deleteStr = '';
                    }
                });
    };
//受电点电源信息的第一个panel
	var mapanel1 = new Ext.FormPanel({
		//height : 40,
		layout : "column",
		baseCls : "x-plain",
		style : "padding:5px",
		items : [{
					columnWidth : .25,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					buttonAlign : 'center',
					items : [{
								fieldLabel : "资产编号",
                                name:'assetNo',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							},{
								fieldLabel : "电能表类别",
                                name:'sortCode',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							},{
								fieldLabel : "电压",
                                name:'voltCode',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							},{
								fieldLabel : "生产厂家",
                                name:'',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							},{
								fieldLabel : "脉冲常数",
                                name:'pulseConstantCode',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							}]
				}, {
					columnWidth : .25,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "条形码",
                                name:'barCode',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							},{
								fieldLabel : "电能表类型",
                                name:'typeCode',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							},{
								fieldLabel : "标定电流",
                                name:'ratedCurrent',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							},{
								fieldLabel : "出厂日期",
                                name:'madeDate',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							},{
								fieldLabel : "测量原理",
                                name:'measTheory',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							}]
				}, {
					columnWidth : .25,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "生产批次",
                                name:'lotNo',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							},{
								fieldLabel : "电能表的型号",
                                name:'modelCode',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							},{
								fieldLabel : "过载倍数",
                                name:'overloadFactor',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							},{
								fieldLabel : "使用用途",
                                name:'meterUsage',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							},{
								fieldLabel : "通讯接口",
                                name:'ci',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							}]
				}, {
					columnWidth : .25,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "出厂编号",
                                name:'madeNo',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							},{
								fieldLabel : "接线方式",
                                name:'wiringMode',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width :80
							},{
								fieldLabel : "电能表常数",
                                name:'constCode',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							},{
								fieldLabel : "频率",
                                name:'freqCode',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							},{
								fieldLabel : "当前状态",
                                name:'',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							}]
				}]
			
				
	});
	

// 电能表资产信息window--------------------------
var mtapanel = new Ext.Panel({
			// title:"客户资料",
			items : [mapanel1]
		});
var meterAssetWindow = new Ext.Window({
	title : "电能表资产信息",
	frame : true,
	width : 800,
	height : 220,
	layout : "form",
	modal : true,
	// labelWidth:45,
	plain : true,// 设置背景颜色
	resizable : false,// 不可移动
	bodyStyle : "padding:2px",
	draggable:false,
	buttonAlign : "center",// 按钮的位置
//	closable:false,//设置窗体关闭按钮
	closeAction:"hide",//将窗体隐藏而并不销毁
	items : [mtapanel],
	buttons : [{
				text : '关闭',    //窗口隐藏，设置关闭有误?
				width : 60,
				handler : function() {
					meterAssetWindow.hide();
				}
			}]
		// });
		// simTrafficWindow.show();
	})
function meterAssetWindowShow(value) {
	meterAssetWindow.show();
	getDMeterMessage(value);
}