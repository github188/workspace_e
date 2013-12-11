/**
 * 终端状态查询及相关SQL改动、grid选择框、序列及翻页效果、列内容靠左及浮动效果、导出excel(全部)
 */
 
var gateID;
var gateTYPE;
var gatepustatusName;
var gateOrgType = "";


//var treels = new LeftTreeListener({
//			modelName : '公变采集点查询',
//			processClick : function(p, node, e) {
//				gateTerminalClick(p, node, e);
//			},
//			processUserGridSelect : function(cm, row, record) {
//				// Ext.getCmp("CONS_NO").setValue(record.get('consNo'));
//				gateID = record.get('consId');
//				gateNo = record.get('consNo');
//				if (gateNo.toString().substring(0, 1) == 'T') {
//					gateID = 'T' + gateID;
//					var zdzt = Ext.getCgatepustatusNameName').getValue();
//					gateTerminalStore.baseParams = {
//						pbid : gateID,
//						pbtype : gateTYPE,
//						publicOrgType : gateOrgType,
//						statusName : zdzt
//					};
//					gateTerminalStore.load({
//								params : {
//									start : 0,
//									limit : DEFAULT_PAGE_SIZE
//								}
//							});
//				}
//				// Ext.getCmp("gate_id_text").disable();
//
//			}
//		});
/*		
//左边树监听时点击树节点调用的方法		
function gateTerminalClick(p, node, e) {
	var obj = node.attributes.attributes;
	var type = node.attributes.type;
	//gateTerminalStore.removeAll();
	if (!node.isLeaf()) {
		if (node.attributes.type == 'org') {
			gateID = obj.orgNo;
			gateOrgType = obj.orgType;
			gateTYPE = node.attributes.type;
			Ext.getCmp("gate_id_text").setValue(node.text);
		}
		if (node.attributes.type == 'sub') {
			gateID = obj.subsId;
			gateTYPE = node.attributes.type;
			Ext.getCmp("gate_id_text").setValue(node.text);
		}if (node.attributes.type == 'cgp') {
			gateID = obj.groupNo;
			gateTYPE = node.attributes.type;
			Ext.getCmp("gate_id_text").setValue(node.text);
		}if (node.attributes.type == 'ugp') {
			gateID = obj.groupNo;
			gateTYPE = node.attributes.type;
			//alert(gateTYPE);
			Ext.getCmp("gate_id_text").setValue(node.text);
		}
		if (node.attributes.type == 'line') {
			gateID = obj.lineId;
			gateTYPE = node.attributes.type;
			Ext.getCmp("gate_id_text").setValue(node.text);
		} else {
			// Ext.getCmp("gate_id_text").setValue("");
		}
	} else {
		return true;
	}
};

//左边树监听时选择用户调用的方法
function gateTerminalSelect(cm, row, record) {
				// Ext.getCmp("CONS_NO").setValue(record.get('consNo'));
				gateID = record.get('consId');
				gateNo = record.get('consNo');
				var consType = record.get('consType');
				Ext.getCmp("gate_id_text").setValue(record.get('consName'));
				if (consType == '3') {
					var zdzt = Ext.getCmp('gatepustatusName').getValue();
					gateTerminalStore.baseParams = {
						pbid : gateID,
						pbtype : gateTYPE,
						consType : consType,
						publicOrgType : gateOrgType,
						statusName : zdzt
					};
					gateTerminalStore.load({
								params : {
									start : 0,
									limit : DEFAULT_PAGE_SIZE
								}
							});
				}
				// Ext.getCmp("gate_id_text").disable();

			}
//putcombo	

//************my*************
	var gateputstore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'qrystat/publicTerminalAction!queryTmnlStatus.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'privateTerminalList'
						}, [{
									name : 'statusCode'
								}, {
									name : 'statusName'
								}])
			});
	gateputstore.load();
	
	var gateputstorecombo = new Ext.form.ComboBox({
				store : gateputstore,
				xtype : 'combo',
				id : 'gatepustatusName',
				fieldLabel : '终端状态',
				width : 100,
				valueField : 'statusCode',
				editable : false,
				resizable : true,//拖动柄缩放
				triggerAction : 'all',
				labelSeparator : ' ',
				forceSelection : true,
				selectOnFocus : true,//值为 ture 时表示字段获取焦点时自动选择字段既有文本(默认为 false)
				displayField : 'statusName',//组合框用以展示的数据的字段名（如果mode='remote'则默认为 undefined，如果mode = 'local' 则默认为 'text'）
				mode : 'remote',//如果ComboBox读取本地数据则将值设为'local'（默认为 'remote' 表示从服务器读取数据）
				emptyText : '--请选择--'
			});
			
	//combo 默认		
	gateputstore.on('load', function() {
				gateputstorecombo.setValue(gateputstore.getAt(0).get('statusCode'));
			});
	//**************my***************

*/
//	var putdata = [['全部'], ['待投'], ['运行'],
//			['停运'],['待检修']];
//	var gateputstore = new Ext.data.Store({
//				proxy : new Ext.data.MemoryProxy(putdata), // 数据源
//				reader : new Ext.data.ArrayReader({}, [ // 如何解析
//						{
//									name : 'name'
//								}])
//			});
//	gateputstore.load();
/*			
// 公变采集点查询panel1
var gateTerminal1 = new Ext.Panel({
	plain : true,
	border : false,
	layout:'fit',
	region : 'north',
	height : 35,
	items : [{
				baseCls : "x-plain",
				layout : "column",
				style : "padding:5px",
				border : false,
				items : [{
							columnWidth : .35,// ----------------------
							layout : "form",
							labelWidth : 50,
							defaultType : "textfield",
							baseCls : "x-plain",
							items : [{
										fieldLabel : "节点名",//<font color='red'>*</font>",
										allowBlank : false,//红星，必选
										id : 'gate_id_text',
										readOnly : true,
										labelStyle : "text-align:right;width:50;",
										// emptyText : '请输入...',
										labelSeparator : "",
										width : 200,
										validator : function(val) {
											if (Ext.isEmpty(val))
												return false;
											else
												return true;
										}
									}]
						}, {
							columnWidth : .3,// ------------------
							layout : "form",
							defaultType : "combo",
							labelWidth : 50,
							labelAlign : 'right',
							baseCls : "x-plain",
							items : [gateputstorecombo]
						}, {
							columnWidth : .1,// ------------------
							layout : "form",
							defaultType : "button",
							baseCls : "x-plain",
							items : [{
										text : "查询",
										width : 50,
										listeners : {
											"click" : function() {
//												//gateTerminalStore.removeAll();
//												var pbtext = Ext.getCmp("gate_id_text");
//												var zdzt = Ext.getCmp('gatepustatusName').getValue();
//												if (!pbtext.isValid(true)) {
//													pbtext.markInvalid('不能为空');
//													return true;
//												}
//												gateTerminalStore.baseParams = {
//													pbid : gateID,
//													pbtype : gateTYPE,
//													publicOrgType:gateOrgType,
//													statusName:zdzt
//												};
//												gateTerminalStore.load({
//															params : {
//																start : 0,
//																limit : DEFAULT_PAGE_SIZE
//															}
//														});
											}
										}
									}]
						}]
			}]
});

*/

// ------------------------------------------------------------------------------------------
// ***************翻页 页码递增
	Ext.override(Ext.grid.RowNumberer, {
				renderer : function(v, p, record, rowIndex) {
					if (this.rowspan) {
						p.cellAttr = 'rowspan="' + this.rowspan + '"';
					}
					return rowIndex + 1;
				}
			});

	// rowNum、rowNum_1:分别定义与2个grid
	var rowNum = new Ext.grid.RowNumberer({
				renderer : function(v, p, record, rowIndex) {
					var startRow = 0;
					if (gateTerminalStore && gateTerminalStore.lastOptions
							&& gateTerminalStore.lastOptions.params) {
						startRow = gateTerminalStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			});
	var gateTerminalSm = new Ext.grid.CheckboxSelectionModel();
	var gateTerminalStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/publicTerminalAction!queryGateTerminal.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'gateTerminalList',
						totalProperty : 'totalCount'
					}, [{
								name : 'orgName'
							}, {
								name : 'consNo'
							}, {
								name : 'consName'
							}, {
								name : 'consId'
							}, {
								name : 'elecAddr'
							}, {
								name : 'capTop'
							},{
								name : 'tmnlAssetNo'
							}, {
								name : 'terminalAddr'
							}, {
								name : 'commMode'
							}, {
								name : 'simNo'
							}, {
								name : 'tmnlType'
							}, {
								name : 'statusName'
							}, {
								name : 'factoryName'
							}, {
								name : 'runDate'
							}])
		});
var groupCM = new Ext.grid.ColumnModel([rowNum,gateTerminalSm,{
			header : '台区管理单位',
			dataIndex : 'orgName',
			width:160,
			sortable : true,
			align : 'center',
			renderer : function(val, metaData, record) {
					var s = '台区管理单位: ' + record.get('orgName');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
		}, {
			header : '台区编号',
			dataIndex : 'consNo',
			sortable : true,
			align : 'center',
			renderer : function(val, metaData, record) {
					var s = '台区编号: ' + record.get('consNo');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
		}, {
			header : '台区名称',
			dataIndex : 'consName',
			width:160,
			sortable : true,
			align : 'center',
			renderer : function(val) {		//超链接暂无法浮动显示!!!
				var sss = "<a href='javascript:' onclick='gateTerminalWindowShow(\""
						+ val + "\")'>" + val + "</a>";
				return '<div align = "left">' + sss + '</div>';
			}
		}, {
			header : '台区ID',
			dataIndex : 'consId',
			sortable : true,
			align : 'center',
			hidden : true
		}, {
			header : '安装地址',
			dataIndex : 'elecAddr',
			sortable : true,
			align : 'center',
			renderer : function(val, metaData, record) {
					var s = '安装地址: ' + record.get('elecAddr');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
		}, {
			header : '合同/运行容量',
            width:100,
			dataIndex : 'capTop',
			sortable : true,
			align : 'center'
		},{
			header : '终端资产号',
			dataIndex : 'tmnlAssetNo',
			sortable : true,
			align : 'center'
		}, {
			header : '终端地址',
			dataIndex : 'terminalAddr',
			sortable : true,
			align : 'center'
		}, {
			header : '终端状态',
            width:60,
			dataIndex : 'statusName',
			sortable : true,
			align : 'center',
                renderer : function(val) {
               		if(val == '运行'){
               			return '<font color="green">'+val+'</font>';
               		}else{
               			return '<font color="red">'+val+'</font>';
               		}
            }
		}, {
			header : '通信方式',
            width:60,
			dataIndex : 'commMode',
			sortable : true,
			align : 'center',
			renderer : function(val, metaData, record) {
					var s = '通信方式: ' + record.get('commMode');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
		}, {
			header : 'SIM卡号',
			dataIndex : 'simNo',
			sortable : true,
			align : 'center'
		}, {
			header : '终端类型',
			dataIndex : 'tmnlType',
			sortable : true,
			align : 'center'
		}, {
			header : '生产厂家',
			dataIndex : 'factoryName',
			sortable : true,
			align : 'center',
			renderer : function(val, metaData, record) {
					var s = '生产厂家: ' + record.get('factoryName');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
		}, {
			header : '投运日期',
			dataIndex : 'runDate',
			sortable : true,
			align : 'center'
		}]);

var gateTerminal2 = new Ext.grid.GridPanel({
			title : "公变用户采集点明细",
			region : 'center',
			// height : 420,
			autoScroll : true,
			stripeRows : true,
			viewConfig : {
				forceFit : false
			},
			cm : groupCM,
			sm : gateTerminalSm,
			ds : gateTerminalStore,
			bbar : new Ext.ux.MyToolbar({
							store : gateTerminalStore,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部"
//							,
//							enableExpPage : true, // excel仅导出当前页
//							expPageText : "当前页"
						})
		});
//Ext.onReady(function() {
			// 设置顶层的公变采集点查询panel
			var gateTerminalpanel = new Ext.Panel({
						// title : "专变用户采集点明细",
						autoScroll : true,
						border : false,
						layout : 'border',
						items : [/*gateTerminal1,*/ gateTerminal2]
					});
					
//			renderModel(gateTerminalpanel, '公变采集点查询');
//		});
					
					
//function gateTerminalWindowShow(gateTerminalConvalue, consId) {
//	var record = gateTerminal2.getSelectionModel().getSelected();
//	var consId = '';
//	if (record != null)
//		consId = record.get('consId');
//	gateTerminalconsId = consId;
//	gateTerminalCon = gateTerminalConvalue;
//	//下钻低压集抄
//	touchLinkEventCPQ();
////	openTab("居民集抄户查询", "./qryStat/queryCollPoint/autoSendQuery.jsp");
//}