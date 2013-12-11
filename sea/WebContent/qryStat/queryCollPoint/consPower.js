function getcspMessage() {
        Ext.Ajax.request({
                    url : 'qrystat/ccustAction!queryCSP.action',                  
                    params : {
                       consNo : consIdforTerminal
                    },
                    success : function(response) {
                        var result = Ext.decode(response.responseText);
                       cppanel.getForm().setValues(result.cspList[0]);
                        addStr = '';
                        deleteStr = '';
                    }
                });
    };
    
   function getcpsMessage() {
         CSPStore.baseParams = {
            consNo : consIdforTerminal
        };
        CSPStore.load({
                    params : {
                        start : 0,
                        limit : DEFAULT_PAGE_SIZE
                    }
                });
    };
// 受电点电源信息的第一个panel
var cppanel1 = new Ext.Panel({
			// height : 40,
			layout : "column",
			baseCls : "x-plain",
			style : "padding-top:5px",
			items : [{
						columnWidth : .3,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
							fieldLabel : "受电点类型",
                            name:'typeCode',
							labelStyle : "text-align:right;width:80;",
							labelSeparator : "",
								width : 120
							}]
					}, {
						columnWidth : .4,
						layout : "form",
						labelWidth : 100,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "受电点名称",
                                    name:'spName',
									labelStyle : "text-align:right;width:100;",
									labelSeparator : "",
									width : 200
								}]
					}, {
						columnWidth : .3,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
							fieldLabel : "电源连锁方式",
                            name:'interlockMode',
							labelStyle : "text-align:right;width:80;",
							labelSeparator : "",
								 width : 120
							}]
					}]
		});

// 受电点电源信息的第二个panel
var cppanel2 = new Ext.Panel({
			// height : 40,
			layout : "column",
			baseCls : "x-plain",
			// style : "padding:5px",
			items : [{
						columnWidth : .3,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
							fieldLabel : "电源数目",
                            name:'psNumCode',
							labelStyle : "text-align:right;width:80;",
							labelSeparator : "",
								width : 120
							}]
					}, {
						columnWidth : .4,
						layout : "form",
						labelWidth : 100,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "联锁装置位置",
                                    name:'equipLoc',
									labelStyle : "text-align:right;width:100;",
									labelSeparator : "",
									width : 200
								}]
					}, {
						columnWidth : .3,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
							fieldLabel : "电源切换方式",
                            name:'psSwitchMode',
							labelStyle : "text-align:right;width:80;",
							labelSeparator : "",
								 width : 120
							}]
					}]
		});

// 受电点电源信息的第三个panel
var cppanel3 = new Ext.Panel({
			// height : 40,
			labelWidth : 80,
			layout : "column",
			baseCls : "x-plain",
			items : [{
						columnWidth : .7,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "受电点备注",
                                    name:'spRemark',
									labelStyle : "text-align:right;width:80;",
									labelSeparator : "",
									width : 452
								}]
					}, {
						columnWidth : .3,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
							fieldLabel : "自备电源容量",
                            name:'sparePowerCap',
							labelStyle : "text-align:right;width:80;",
							labelSeparator : "",
								 width : 120
							}]
					}]
		});
// 受电点电源信息的第四个panel
var cppanel4 = new Ext.Panel({
			// height : 40,
			layout : "column",
			baseCls : "x-plain",
			// style : "padding:5px",
			items : [{
						columnWidth : .3,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
							fieldLabel : "有无自备电源",
                            name:'sparePowerFlag',
							labelStyle : "text-align:right;width:80;",
							labelSeparator : "",
								 width : 120
							}]
					}, {
						columnWidth : .4,
						layout : "form",
						labelWidth : 100,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "自备电源闭锁方式",
                                    name:'lockMode',
									labelStyle : "text-align:right;width:100;",
									labelSeparator : "",
									width : 200
								}]
					}]
		});
// linkmanGrid-----------------------------------------------------------------------------------
               var CSPStore = new Ext.data.Store({
                proxy : new Ext.data.HttpProxy({
                            url : 'qrystat/ccustAction!queryCPS.action'
                        }),
                reader : new Ext.data.JsonReader({
                            root : 'cpsList',
                            totalProperty : 'totalCount'
                        }, [
                                    {
                                    name : 'psNo'
                                }, {
                                    name : 'psCap'
                                }, {
                                    name : 'typeCode'
                                }, {
                                    name : 'phaseCode'
                                }, {
                                    name : 'subsId'
                                }, {
                                    name : 'lineId'
                                }, {
                                    name : 'psVolt'
                                }, {
                                    name : 'lineinMode'
                                }, {
                                    name : 'psAttr'
                                }, {
                                    name : 'relayProtectMode'
                                }, {
                                    name : 'runMode'
                                }, {
                                    name : 'remark'
                                }, {
                                    name : 'prPoint'
                                }])
            });
            var cpsNum = 1;
var cplinkmanGrid = new Ext.grid.GridPanel({
			height : 150,
            viewConfig : {
            forceFit : false
        },
			stripeRows : true,
			autoScroll : true,
			enableColumnMove : false,// 设置不可表头不可拖动
			colModel : new Ext.grid.ColumnModel([{
						header : "序号",
						menuDisabled : true,
						align : "center",
                         renderer : function() {
                        return cpsNum++;
                    }
					},// 使下拉框消失
					{
						header : "电源编号",
                        dataIndex : 'psNo',
						menuDisabled : true,
						align : "center"
					},// 设置标签文字居中
					{
						header : "供电容量",
                        dataIndex : 'psCap',
						menuDisabled : true,
						align : "center"
					}, {
						header : "电源类型",
                        dataIndex : 'typeCode',
						menuDisabled : true,
						align : "center"
					}, {
						header : "电源相数",
                        dataIndex : 'phaseCode',
						menuDisabled : true,
						align : "center"
					}, {
						header : "所属变电站",
                        dataIndex : 'subsId',
						menuDisabled : true,
						align : "center"
					}, {
						header : "所属线路",
                        dataIndex : 'lineId',
						menuDisabled : true,
						align : "center"
					}, {
						header : "供电电压",
                        dataIndex : 'psVolt',
						menuDisabled : true,
						align : "center"
					}, {
						header : "进线方式",
                        dataIndex : 'lineinMode',
						menuDisabled : true,
						align : "center"
					}, {
						header : "电源性质",
                        dataIndex : 'psAttr',
						menuDisabled : true,
						align : "center"
					}, {
						header : "继电保护类型",
                        dataIndex : 'relayProtectMode',
						menuDisabled : true,
						align : "center"
					}, {
						header : "运行方式",
                        dataIndex : 'runMode',
						menuDisabled : true,
						align : "center"
					}, {
						header : "电源备注",
                        dataIndex : 'remark',
						menuDisabled : true,
						align : "center"
					}, {
						header : "产权分界点",
                        dataIndex : 'prPoint',
						menuDisabled : true,
						align : "center"
					}]),
			ds :CSPStore,
             bbar : new Ext.ux.MyToolbar({
                    store : CSPStore
                })
                });
// --------------------------
var cppanel = new Ext.FormPanel({
			// title:"受电点/供电电源",
			items : [cppanel1, cppanel2, cppanel3, cppanel4]
		});
var consPowerWindow = new Ext.Window({
			title : "受电点与供电电源",
			frame : true,
			width : 800,
			height : 338,
			layout : "form",
			modal : true,
			// labelWidth:45,
			plain : true,// 设置背景颜色
			resizable : false,// 不可移动
			draggable:false,
			bodyStyle : "padding:2px",
			buttonAlign : "center",// 按钮的位置
			// closable:false,//设置窗体关闭按钮
			closeAction : "hide",// 将窗体隐藏而并不销毁
			items : [cppanel,cplinkmanGrid],
			buttons : [{
				text : '关闭',    //窗口隐藏，设置关闭有误?
				width : 60,
				handler : function() {
					consPowerWindow.hide();
				}
			}]
		});
function consPowerWindowShow() {
	consPowerWindow.show();
    getcspMessage();
    getcpsMessage();
};