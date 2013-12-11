var Gridresult;
function getGtranMessage() {
	gtranGridStore.baseParams = {
			CONS_ID : consIdNew
		};
		gtranGridStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
        Ext.Ajax.request({
                    url : 'qrystat/ccustAction!queryGtran.action',                  
                    params : {
                      CONS_ID:consIdNew
                    },
                    success : function(response) {
                       Gridresult = Ext.decode(response.responseText);
                       ctfFrompanel.getForm().setValues(Gridresult.gtranList[0]);
                    }
                });
    };
//用户变压器信息的第一个panel
	var ctpanel1 = new Ext.Panel({
		//height : 40,
		layout : "column",
		baseCls : "x-plain",
		style : "padding:5px",
		items : [{
					columnWidth : .3,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "管理单位",
								name:'orgNo',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 120
							}]
				}, {
					columnWidth : .4,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "设备名称",
								name:'tranName',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 200
							}]
				}, {
					style : "padding-left:3px",
					columnWidth : .3,
					layout : "form",
					labelWidth : 50,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "铭牌容量",
								name:'plateCap',
								labelStyle : "text-align:right;width:50;",
								labelSeparator : "",
								width : 124
							}]
				}]
	});
	
//用户变压器信息的第二个panel
	var ctpanel2 = new Ext.Panel({
		//height : 40,
		layout : "column",
		baseCls : "x-plain",
		style : "padding-left:5px",
		items : [{
					columnWidth : .3,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "设备类型",
								name:'typeCode',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 120
							}]
				}, {
					columnWidth : .4,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "安装地址",
								name:'instAddr',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 199
							}]
				}, {
					columnWidth : .3,
					layout : "form",
					labelWidth : 50,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "安装日期",
								name:'instDate',
								labelStyle : "text-align:right;width:50;",
								labelSeparator : "",
								width : 124
							}]
				}]
	});
	
//用户变压器信息的第三个panel
	var ctpanel3 = new Ext.Panel({
		//height : 40,
		layout : "column",
		baseCls : "x-plain",
		style : "padding-left:5px",
		items : [{
					columnWidth : .3,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "变更说明",
								name:'chgRemark',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 120
							}]
				}, {
					columnWidth : .2,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "运行状态",
								name:'runStatusCode',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 50
							}]
				},{
					columnWidth : .2,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "公/专变标志",
								name:'pubPrivFlag',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 45
							}]
				},
				
				{
					columnWidth : .3,
					layout : "form",
					labelWidth : 50,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "出厂日期",
								name:'madeDate',
								labelStyle : "text-align:right;width:50;",
								labelSeparator : "",
								width :125
							}]
				}]
	});
//用户变压器信息的第四个panel
	var ctpanel4 = new Ext.Panel({
		//height : 40,
		layout : "column",
		baseCls : "x-plain",
		style : "padding-left:5px",
		items : [{
					columnWidth : .3,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "设备型号",
								name:'modelNo',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 120
							}]
				}, {
					columnWidth : .4,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "厂家名称",
								name:'factoryName',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 199
							}]
				}, {
					columnWidth : .3,
					layout : "form",
					labelWidth : 50,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "出厂编号",
								name:'madeNo',
								labelStyle : "text-align:right;width:50;",
								labelSeparator : "",
								width : 125
							}]
				}]
	});
	
//用户变压器信息的第五个panel
	var ctpanel5 = new Ext.Panel({
		//height : 40,
		layout : "column",
		baseCls : "x-plain",
		style : "padding-left:5px",
		items : [{
					columnWidth : .3,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "保护方式",
								name:'protectMode',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 120
							}]
				}, {
					columnWidth : .2,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "一次侧电压",
								name:'frstsideVoltCode',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 45
							}]
				},{
					columnWidth : .2,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "二次侧电压",
								name:'sndsideVoltCode',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 45
							}]
				},
				
				{
					columnWidth : .3,
					layout : "form",
					labelWidth : 50,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "产权",
								name:'prCode',
								labelStyle : "text-align:right;width:50;",
								labelSeparator : "",
								width : 125
							}]
				}]
	});


// linkmanGrid-----------------------------------------------------------------------------------
		var gtranGridStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'qrystat/ccustAction!queryGtranGrid.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'gtranList',
							totalProperty : 'totalCount'
						}, [{
									name : 'consId'
								}, {
									name : 'tranName'
								}, {
									name : 'typeCode'
								}, {
									name : 'instAddr'
								}, {
									name : 'instDate'
								}, {
									name : 'plateCap'
								}, {
									name : 'chgRemark'
								}, {
									name : 'runStatusCode'
								}])
			});
			
	var ctlinkmanGrid = new Ext.grid.GridPanel({
		 height:200,
		  stripeRows: true,
		autoScroll:true,
		enableColumnMove : false,// 设置不可表头不可拖动
		colModel : new Ext.grid.ColumnModel([{
					header : "序号",
					menuDisabled : true,
					align : "center"
				},// 使下拉框消失
				{
					header : "设备名称",
					dataIndex : 'tranName',
					menuDisabled : true,
					align : "center"
				},// 设置标签文字居中
				{
					header : "设备型号",
					dataIndex : 'typeCode',
					menuDisabled : true,
					align : "center"
				}, {
					header : "安装地址",
					dataIndex : 'instAddr',
					menuDisabled : true,
					align : "center"
				}, {
					header : "安装日期",
					dataIndex : 'instDate',
					menuDisabled : true,
					align : "center"
				}, {
					header : "铭牌容量",
					dataIndex : 'plateCap',
					menuDisabled : true,
					align : "center"
				}, {
					header : "变更说明",
					dataIndex : 'chgRemark',
					menuDisabled : true,
					align : "center"
				}, {
					header : "运行状态",
					dataIndex : 'runStatusCode',
					menuDisabled : true,
					align : "center"
				}]),
		ds : gtranGridStore,
		bbar : new Ext.ux.MyToolbar({
					store : gtranGridStore
				})
	});
	
	ctlinkmanGrid.addListener('rowclick', rowclick);
	
function rowclick(grid,rowIndex,e) {
    if(rowIndex >= 0){
    	var row = rowIndex;
    	ctfFrompanel.getForm().reset();
    	 ctfFrompanel.getForm().setValues(Gridresult.gtranList[row]);
    	// alert(row + '行选择事件');
    }
};
//--------------------------	
	var ctfFrompanel = new Ext.FormPanel({
	items:[ctpanel1,ctpanel2,ctpanel3,ctpanel4,ctpanel5]
	})
	var ctfpanel = new Ext.Panel({
		//title:"客户资料",
		items:[ctfFrompanel,ctlinkmanGrid]
	});
  
	var consTransformerWindow = new Ext.Window({
		//id:consTransformerWindow,
		title:"用户变压器信息",
		frame:true,
		width:800,
		height:385,
		layout:"form",
		modal:true,
		//labelWidth:45,
		plain:true,//设置背景颜色
		resizable:false,//不可移动
		bodyStyle:"padding:2px",
		draggable:false,
		buttonAlign:"center",//按钮的位置
		//closable:false,//设置窗体关闭按钮
		closeAction:"hide",//将窗体隐藏而并不销毁
		items:[ctfpanel],
		buttons : [{
				text : '关闭',    //窗口隐藏，设置关闭有误?
				width : 60,
				handler : function() {
					consTransformerWindow.hide();
				}
			}]
	});
    
function consTransformerWindowShow(){
consTransformerWindow.show();
getGtranMessage();
};