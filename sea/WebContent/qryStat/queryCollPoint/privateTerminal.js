/**
 * 终端状态查询及相关SQL改动、grid选择框、序列及翻页效果、列内容靠左及浮动效果、导出excel(全部)
 */
 
 
//Ext.onReady(function() {
	
	
	var privateTYPE;
	var prstatusName;
	var publicOrgType = "";
	
////	var treels = new LeftTreeListener({
////				modelName : '专变采集点查询',
////				processClick : function(p, node, e) {
////					privateTerminalClick(p, node, e);
////				}
////			});
///*	
//	//左边树监听时点击树节点调用的方法
//	function privateTerminalClick(p, node, e) {
//		var obj = node.attributes.attributes;
//		var type = node.attributes.type;
//		// privateTerminalStore.removeAll();
//		if (!node.isLeaf()) {
//			if (node.attributes.type == 'org') {
//				privateID = obj.orgNo;
//				publicOrgType = obj.orgType;
//				privateTYPE = node.attributes.type;
//				Ext.getCmp("pr_id_text").setValue(node.text);
//			}
//			if (node.attributes.type == 'sub') {
//				privateID = obj.subsId;
//				privateTYPE = node.attributes.type;
//				Ext.getCmp("pr_id_text").setValue(node.text);
//			}
//			if (node.attributes.type == 'cgp') {
//				privateID = obj.groupNo;
//				privateTYPE = node.attributes.type;
//				Ext.getCmp("pr_id_text").setValue(node.text);
//			}
//			if (node.attributes.type == 'ugp') {
//				privateID = obj.groupNo;
//				privateTYPE = node.attributes.type;
//				// alert(publicTYPE);
//				Ext.getCmp("pr_id_text").setValue(node.text);
//			}
//			if (node.attributes.type == 'line') {
//				privateID = obj.lineId;
//				privateTYPE = node.attributes.type;
//				Ext.getCmp("pr_id_text").setValue(node.text);
//			} else {
//				// Ext.getCmp("pb_id_text").setValue("");
//			}
//		} else {
//			return true;
//		}
//	};
	
	
	//左边树监听时选择用户调用的方法
function privateTerminalSelect(cm, row, record) {
				privateID = record.get('consId');
				privateNo = record.get('consNo');
				var consType = record.get('consType');
				Ext.getCmp("pr_id_text").setValue(record.get('consName'));
				if (consType == '1') {
					var zdzt = Ext.getCmp('prstatusName').getValue();
					privateTerminalStore.baseParams = {
						pbid : privateID,
						pbtype : privateTYPE,
						consType : consType,
						publicOrgType : publicOrgType,
						statusName : zdzt
					};
					privateTerminalStore.load({
								params : {
									start : 0,
									limit : DEFAULT_PAGE_SIZE
								}
							});
				}
				// Ext.getCmp("pb_id_text").disable();

			}
	
	// prtcombo
	//************my*************
	var prtstore = new Ext.data.Store({
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
	prtstore.load();
	
	var prtstorecombo = new Ext.form.ComboBox({
				store : prtstore,
				xtype : 'combo',
				id : 'prstatusName',
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
	prtstore.on('load', function() {
				prtstorecombo.setValue(prtstore.getAt(0).get('statusCode'));
			});
	//**************my***************
	

	/*var prtdata = [['全部'], ['待投'], ['运行'], ['停运'], ['待检修']];
	var prtstore = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(prtdata), // 数据源
				reader : new Ext.data.ArrayReader({}, [ // 如何解析
						{
									name : 'name'
								}])
			});
	prtstore.load();
	var prtstorecombo = new Ext.form.ComboBox({
				id : 'prstatusName',
				store : prtstore,
				fieldLabel : '终端状态',
				labelStyle : "text-align:right;width:30%;",
				labelSeparator : "",
				typeAhead : true,
				editable : false,
				mode : 'local',
				forceSelection : true,
				triggerAction : 'all',
				width : 80,
				resizable : true,
				valueField : 'name',
				displayField : 'name',
				// emptyText : '全部',
				// blankText : '全部',
				value : '全部',
				selectOnFocus : true
			});*/
/*			
			
	// 专变采集点查询panel1
	var privateTerminal1 = new Ext.Panel({
		plain : true,
		border : false,
		region : 'north',
		layout:'fit',
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
						labelAlign : 'right',
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "节点名",
									allowBlank : false,//红星，必选
									labelStyle : "text-align:right;width:50;",
									// emptyText : '请输入...',
									readOnly : true,
									id : 'pr_id_text',
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
						labelWidth : 50,
						labelAlign : 'right',
						defaultType : "combo",
						baseCls : "x-plain",
						items : [prtstorecombo]
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
									var prtext = Ext.getCmp("pr_id_text");
									var zdzt = Ext.getCmp('prstatusName').getValue()//zdzt:终端状态
									if (!prtext.isValid(true)) {
										prtext.markInvalid('不能为空');
										return true;
									}
									privateTerminalStore.baseParams = {
										prid : privateID,
										pbtype : privateTYPE,
										publicOrgType : publicOrgType,
										statusName : zdzt
									};
									privateTerminalStore.load({	//参数可不写
												params : {
													start : 0,
													limit : DEFAULT_PAGE_SIZE
												}
											});
								}
							}
						}]
					}]
		}]
	});
	// ------------------------------------------------------------------------------------------
	*/
	//运行store
	function privateTerminalStoreQuery(nodeType,cpqCombo,orgType,nodeValue,consType,tmnlAssetNo){
		//清除grid数据
		privateTerminalStore.removeAll();
		privateTerminalStore.baseParams = {
										prid : nodeValue,
										pbtype : nodeType,
										publicOrgType : orgType,
										statusName : cpqCombo,
										consType : consType,
										tmnlAssetNo : tmnlAssetNo
									};
									privateTerminalStore.load({	//参数可不写
												params : {
													start : 0,
													limit : DEFAULT_PAGE_SIZE
												}
											});
	}
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
					if (privateTerminalStore && privateTerminalStore.lastOptions
							&& privateTerminalStore.lastOptions.params) {
						startRow = privateTerminalStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			});
	// 专变采集点查询的grid
	var privateTerminalSm = new Ext.grid.CheckboxSelectionModel();
	var privateTerminalStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'qrystat/publicTerminalAction!queryPrivateTerminal.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'privateTerminalList',
					totalProperty : 'totalCount'
				}, [{
							name : 'orgName'
						}, {
							name : 'consNo'
						}, {
							name : 'consId'
						}, {
							name : 'consName'
						}, {
							name : 'elecAddr'
						}, {
							name : 'capTop'
						}, {
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
						}, {
							name : 'consId'
						}])
	});
	var groupCM = new Ext.grid.ColumnModel([rowNum, privateTerminalSm, {
				header : '供电单位',
				width : 160,
				dataIndex : 'orgName',
				sortable : true,
				align : 'center',
				renderer : function(val, metaData, record) {
					var s = '供电单位: ' + record.get('orgName');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : '用户编号',
				dataIndex : 'consNo',
				sortable : true,
				align : 'center'
				,
				renderer : function(val) {
					return "<a href='javascript:' onclick=''>" + val + "</a>";
				}
			}, {
				header : '用户ID',
				dataIndex : 'consId',
				sortable : true,
				align : 'center',
				hidden:true
			}, {
				header : '用户名称',
				dataIndex : 'consName',
				sortable : true,
				align : 'center',
				renderer : function(val, metaData, record) {
					var s = '用户名称: ' + record.get('consName');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : '安装地址',
				dataIndex : 'elecAddr',
				sortable : true,
				align : 'center',
				width : 120,
				renderer : function(val, metaData, record) {
					var s = '安装地址: ' + record.get('elecAddr');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : '合同/运行容量',
				width : 100,
				dataIndex : 'capTop',
				sortable : true,
				align : 'center',
				renderer : function(val) {
					return "<div align = 'left'>" + val + "</div>";
				}
			}, {
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
				width : 60,
				dataIndex : 'statusName',
				sortable : true,
				align : 'center',
				renderer : function(val) {
					if (val == '运行') {
						return '<font color="green">' + val + '</font>';
					} else {
						return '<font color="red">' + val + '</font>';
					}
				}
			}, {
				header : '通信方式',
				width : 60,
				dataIndex : 'commMode',
				sortable : true,
				align : 'center',
				renderer : function(val, metaData, record) {
					var s = '通信方式:' + record.get('commMode');
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
				align : 'center',
				renderer : function(val, metaData, record) {
					var s = '终端类型:' + record.get('tmnlType');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : '生产厂家',
				dataIndex : 'factoryName',
				sortable : true,
				align : 'center',
				renderer : function(val, metaData, record) {
					var s = '生产厂家:' + record.get('factoryName');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : '投运日期',
				dataIndex : 'runDate',
				sortable : true,
				align : 'center'
			}]);

	var privateTerminal2 = new Ext.grid.GridPanel({
				title : "专变用户采集点明细",
				region : 'center',
				height : 420,
				autoScroll : true,
				stripeRows : true,
				viewConfig : {
					forceFit : false
				},
				cm : groupCM,
				sm : privateTerminalSm,
				ds : privateTerminalStore,
				bbar : new Ext.ux.MyToolbar({
							store : privateTerminalStore,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部"
//							,
//							enableExpPage : true, // excel仅导出当前页
//							expPageText : "当前页"
						})

			});

					privateTerminal2.addListener('cellclick', prcellclick);
	
function prcellclick(privateTerminal2, rowIndex, columnIndex, e){
    if(rowIndex >= 0 && columnIndex==3){
    	  var record = privateTerminal2.getStore().getAt(rowIndex);  // Get the Record
    var cellconsId = privateTerminal2.getColumnModel().getDataIndex(columnIndex+1); // Get field name
    var cellconsNo= privateTerminal2.getColumnModel().getDataIndex(columnIndex); // Get field name
     messageconsId = record.get(cellconsId);
     messageconsNo = record.get(cellconsNo);
		privateIDNew = messageconsId;
		privateTerminalConvalue = messageconsNo;
		opencsitab(privateTerminalConvalue);
		
    }
};
	// 设置顶层的专变采集点查询panel
	var privateTerminalpanel = new Ext.Panel({
				// title : "专变用户采集点明细",
				autoScroll : true,
				border : false,
				layout : 'border',
				items : [/*privateTerminal1,*/ privateTerminal2]
			});
			
			
//	renderModel(privateTerminalpanel, '专变采集点查询');
//});


// 打开新的tab
function opencsitab(privateTerminalConvalue) {
	privateTerminalCon = privateTerminalConvalue;
	privateIDCon = privateIDNew;
	openTab("客户综合查询", "./qryStat/queryCollPoint/consumerInfo.jsp");

}