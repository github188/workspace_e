/**
 * 作者：
 * 修改人：ChunMingLi
 * 时间：2010/07/02
 */
 //弹出窗口
 var ammeterHWindowPanel;
Ext.onReady(function(){	
	
	
			
	//开始日期
	var ammeterStartDate = new Ext.form.DateField({
		id : 'ammeterStartDate',
		fieldLabel : '从',
		editable : false,
		name : 'ammeterStartDate',
		format: 'Y-m-d',
	    allowBlank:false,
	    value : new Date().add(Date.DAY, -6)
    });
	
	//结束日期
	var ammeterEndDate = new Ext.form.DateField({
		id : 'ammeterEndDate',
		name : 'ammeterEndDate',
		fieldLabel : '到',
		editable : false,
		format: 'Y-m-d',
	    allowBlank:false,
	    value:new Date()
    });
    
	//节点名
	var ammeterTextField = new Ext.form.TextField({
				fieldLabel : '节点名',// <font color=#ff0000>*</font>',
				id : 'ammeterTextField',
				name : 'ammeterTextField',
				allowBlank : false,
				width : 150,
				readOnly : true,
				emptyText : '请选择'
			})
	
	//下拉框
	var ammeterComBox = new Ext.form.ComboBox({
				id : 'ammeterComBox',
				width : 120,
				readOnly : true,
				emptyText : '由上表带过来',
				fieldLabel : '工况'

			})
	var ammeterTextForm = new Ext.form.FormPanel({
				labelAlign : 'right',
				labelWidth : 50,
				border : false,
				autoWidth : true,
				bodyStyle : 'padding:8px 0px 0px 0px',
				layout : 'column',
				items : [{
							columnWidth : .4,
							layout : 'form',
							border : false,
							items : [ammeterTextField]
						}, {
							columnWidth : .36,
							border : false,
							layout : 'form',
							items : [ammeterComBox]
						}]
			});
	
	
	// 顶部的查询panel
	var topPanel = new Ext.Panel({
				labelAlign : 'right',
				region : 'north',
				height : 30,
				layout : 'table',
				items : [{
							// columnWidth : .25,
							width : 220,
							layout : 'form',
							labelAlign : 'right',
							labelWidth : 50,
							border : false,
							items : [ammeterTextField, {
										xtype : 'hidden',// 隐藏panel，用于选取左边树节点信息
										id : 'ammeter_orgNo'
									}, {
										xtype : 'hidden',
										id : 'ammeter_tmnlAssetNo'
									}, {
										xtype : 'hidden',
										id : 'ammeter_lineId'
									}, {
										xtype : 'hidden',
										id : 'ammeter_groupNo'
									}, {
										xtype : 'hidden',
										id : 'ammeter_nodeType'
									}, {
										xtype : 'hidden',
										id : 'ammeter_orgType'
									}, {
										xtype : 'hidden',
										id : 'ammeter_subsId'
									}, {
										xtype : 'hidden',
										id : 'ammeterOrgNo'
									}, {
										xtype : 'hidden',
										id : 'ammeterEventNo'
									}]
						}, {
							// columnWidth : .25,
							width : 150,
							labelAlign : 'right',
							layout : 'form',
							labelWidth : 20,
							border : false,
							items : [ammeterStartDate]
						}, {
							// columnWidth : .25,
							width : 150,
							layout : 'form',
							labelAlign : 'right',
							labelWidth : 20,
							border : false,
							items : [ammeterEndDate]
						}, {
							// columnWidth : .1,
							width : 80,
							fieldLabel : '',
							labelWidth : 10,
							border : false,
							// bodyStyle : 'padding:25px 0px 0px 0px',
							items : [{
										xtype : 'button',
										text : '查询',
										width : 70,
										handler : queryAmmeterData
									}]
						}]
			});
			
	//查询参数设置
	function queryAmmeterData() {
		var start =  Ext.getCmp('ammeterStartDate').getValue();
		var end = Ext.getCmp('ammeterEndDate').getValue();
		if (Ext.isEmpty(start)) {
			Ext.MessageBox.alert("提示", "请输入开始日期！");
			return false;
		}
		if (Ext.isEmpty(end)) {
			Ext.MessageBox.alert("提示", "请输入结束日期！");
			return false;
		}
		if ((start - end) > 0) {
			Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
			return false;
		}
		var myMask = new Ext.LoadMask(viewPanel.getId(), {
					msg : "加载中..."
				});
		myMask.show();
		
		ammeterStore.baseParams = {
			start : 0,
			limit : DEFAULT_PAGE_SIZE,
			// 检索开始时间
				startDate : Ext.getCmp("ammeterStartDate").getValue(),
			// 检索结束时间
			endDate : Ext.getCmp("ammeterEndDate").getValue().add(Date.DAY, 1),
			orgNo : Ext.getCmp("ammeterOrgNo").getValue(),
			eventNo : Ext.getCmp("ammeterEventNo").getValue()
		}
		ammeterStore.load();
		ammeterStore.on('load', function() {
					myMask.hide();
				});
		ammeterStore.on('loadexception', function() {
					myMask.hide();
				})
	}
	// 翻页 页码递增
	Ext.override(Ext.grid.RowNumberer, {
				renderer : function(v, p, record, rowIndex) {
					if (this.rowspan) {
						p.cellAttr = 'rowspan="' + this.rowspan + '"';
					}
					return rowIndex + 1;
				}
			});
	//显示行数
	var rowNum = new Ext.grid.RowNumberer({
				renderer : function(v, p, record, rowIndex) {
					var startRow = 0;
					if (ammeterStore && ammeterStore.lastOptions
							&& ammeterStore.lastOptions.params) {
						startRow = ammeterStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			});
	//gird 表格列标题
	var cm = new Ext.grid.ColumnModel([rowNum,{
			header : '供电单位',
			dataIndex : 'orgName',
			width : 150,
			align : 'center'
		}, {
			header : '用户编号',
			dataIndex : 'consNo',
			align : 'center'
		}, {
			header : '用户姓名',
			width : 150,
			dataIndex : 'consName',
			align : 'center'
		},{
			header : '发生时间 ',
			width : 120,
			dataIndex : 'eventTime',
			align : 'center'
		}, {
			header : '电表资产号',
			dataIndex : 'assetNo',
			width : 150,
			align : 'center'
		}, {
			header : '终端资产号',
			dataIndex : 'tmnlAssetNo',
			align : 'center'
		}, {
			header : '事件编码',
			dataIndex : 'eventNo',
			align : 'center'
		}, {
			header : '事件名称',
			dataIndex : 'eventName',
			align : 'center'
		},{
			header : '接收时间 ',
			dataIndex : 'receiveTime',
			align : 'center'
		}
//		, {
//			header : '计量点编号',
//			dataIndex : 'mpNo',
//			align : 'center'
//		},{
//			header : '计量点名称',
//			dataIndex : 'mpName',
//			align : 'mpName'
//		}
		
//		{
//			header : '最近编程时间',
//			dataIndex : 'recentlyCompileTime',
//			align : 'center'
//		}, {
//			header : '最大需量最近清零时间',
//			dataIndex : 'maxNeedRecentlyClearTime',
//			align : 'center'
//		}, {
//			header : '编程次数',
//			dataIndex : 'compileNum',
//			align : 'center'
//		}, {
//			header : '最大需量清零次数',
//			dataIndex : 'maxNeedClearNum',
//			align : 'center'
//		}, {
//			header : '电池工作时间',
//			dataIndex : 'batteryWorkTime',
//			align : 'center'
//		}
		]);
	
    var ammeterStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'runman/queryAEventStatDBean!queryAmmeterException.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'ammeterExceptionList',
					totalProperty : 'totalCount'
				}, [{
							name : 'orgName'
						}, {
							name : 'consNo'
						}, {
							name : 'consName'
						}, {
							name : 'assetNo'
						}, {
							name : 'tmnlAssetNo'
						}, {
							name : 'eventNo'
						}, {
							name : 'eventName'
						}
//						, {
//							name : 'mpNo'
//						}, {
//							name : 'mpName'
//						}
						, {
							name : 'eventTime'
//							,
//							type : Date,
//							dateFormat : 'Y-m-d\\TH:i:s' // 2010-01-01T00:00:00
						}, {
							name : 'receiveTime'
//							,
//							type : Date,
//							dateFormat : 'Y-m-d\\TH:i:s' // 2010-01-01T00:00:00
						}])
	});
//    ammeterStore.load();
    
    var ammeterGrid = new Ext.grid.GridPanel({       
		title:'电能表运行状态',
		layout:'form',
		region:'center',
		store : ammeterStore,
		cm : cm,
		border:false,
		stripeRows : true,
		autoWidth : true,	
		autoScroll : true,
		viewConfig : {
					sortAscText : '升序',
					sortDescText : '降序',
					columnsText : '显示列',
					deferEmptyText : '请等待...',
					emptyText : '没有数据'
				},
		bbar : new Ext.ux.MyToolbar({
							store : ammeterStore,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部",
							enableExpPage : true, // excel仅导出当前页
							expPageText : "当前页"
						})
	});
	//点击行触发事件弹出窗口
//	ammeterGrid.getSelectionModel().on("rowselect", function(s, m, rec) {
//
//				ammeterHWindowPanel.show();
//				// 加载grid数据
//				ammHWindowStore.baseParams = {
//					eventId : rec.get("eventId"),
//					areaCode : rec.get("areaCode")
//				};
//				ammHWindowStore.load();
//
//			});
	// 弹出用户明细窗口
	
	var ammHWindowStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'runman/queryAEventStatDBean!queryAmmeterExceptionEvent.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'ammHWindowList',
							totalProperty : 'totalCount'
						}, [{
									name : 'orgNo'
								}, {
									name : 'orgName'
								}, {
									name : 'consNo'
								}, {
									name : 'consName'
								}, {
									name : 'tmnlAssetNo'
								}, {
									name : 'assetNo'
								}, {
									name : 'eventNo'
								}, {
									name : 'eventTime'
								}, {
									name : 'eventName'
								}, {
									name : 'store_type'
								}, {
									name : 'fromType'
								}, {
									name : 'eventData'
								}, {
									name : 'data1'
								}, {
									name : 'data2'
								}, {
									name : 'data3'
								}, {
									name : 'data4'
								}, {
									name : 'data5'
								}, {
									name : 'data6'
								}, {
									name : 'data7'
								}, {
									name : 'data8'
								}, {
									name : 'data9'
								}, {
									name : 'data10'
								}, {
									name : 'data11'
								}, {
									name : 'data12'
								}, {
									name : 'data13'
								}, {
									name : 'data14'
								}, {
									name : 'data15'
								}, {
									name : 'data16'
								}, {
									name : 'data17'
								}, {
									name : 'data18'
								}, {
									name : 'data19'
								}, {
									name : 'data20'
								}, {
									name : 'data21'
								}, {
									name : 'data22'
								}, {
									name : 'data23'
								}, {
									name : 'data24'
								}, {
									name : 'data25'
								}, {
									name : 'data26'
								}, {
									name : 'data27'
								}, {
									name : 'data28'
								}, {
									name : 'data29'
								}, {
									name : 'data30'
								}, {
									name : 'data31'
								}, {
									name : 'data32'
								}, {
									name : 'data33'
								}, {
									name : 'data34'
								}, {
									name : 'data35'
								}, {
									name : 'data36'
								}, {
									name : 'data37'
								}, {
									name : 'data38'
								}, {
									name : 'data39'
								}, {
									name : 'data40'
								}, {
									name : 'data41'
								}, {
									name : 'data42'
								}, {
									name : 'data43'
								}, {
									name : 'data44'
								}, {
									name : 'data45'
								}, {
									name : 'data46'
								}, {
									name : 'data47'
								}, {
									name : 'data48'
								}, {
									name : 'data49'
								}, {
									name : 'data50'
								}])
			});

	var ammHWindowCM = new Ext.grid.ColumnModel([{
				header : '供电单位',
				dataIndex : 'orgName',
				sortable : true,
				width : 100,
				align : 'center',
				renderer : function(val) {
					if (Ext.isEmpty(val)) {
						val = "";
					}
					var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="'
							+ val + '">' + val + '</div>';
					return html;
				}
			}, {
				header : '用电用户',
				dataIndex : 'consName',
				width : 100,
				sortable : true,
				align : 'center',
				renderer : function(val) {
					if (Ext.isEmpty(val)) {
						val = "";
					}
					var html = '<div align = "left" ext:qtitle="用电用户" ext:qtip="'
							+ val + '">' + val + '</div>';
					return html;
				}
//				function(s, m, rec) {
//				}
			}, {
				header : '终端资产号',
				dataIndex : 'tmnlAssetNo',
				sortable : true,
				width : 80,
				align : 'center',
				renderer : function(val) {
					if (Ext.isEmpty(val)) {
						val = "";
					}
					var html = '<div align = "left" ext:qtitle="终端资产号" ext:qtip="'
							+ val + '">' + val + '</div>';
					return html;
				}
			}, {
				header : '资产编号',
				dataIndex : 'assetNo',
				sortable : true,
				width : 80,
				align : 'center',
				renderer : function(val) {
					if (Ext.isEmpty(val)) {
						val = "";
					}
					var html = '<div align = "left" ext:qtitle="资产编号" ext:qtip="'
							+ val + '">' + val + '</div>';
					return html;
				}
			}, {
				header : '来源类型',
				dataIndex : 'fromType',
				sortable : true,
				width : 60,
				align : 'center',
				renderer : function(val) {
					if (Ext.isEmpty(val)) {
						val = "";
					}
					var html = '<div align = "left" ext:qtitle="数据来源" ext:qtip="'
							+ val + '">' + val + '</div>';
					return html;
				}
			},{
				header : '发生时间 ',
				dataIndex : 'eventTime',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '事件名称 ',
				dataIndex : 'eventName',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '参数存储方式 ',
				dataIndex : 'storeType',
				sortable : true,
				align : 'center',
				renderer : ''
			}, {
				header : '上报参数 ',
				dataIndex : 'eventData',
				sortable : true,
				width : 160,
				align : 'center',
				renderer : ''
			}, {
				header : '数据1 ',
				dataIndex : 'data1',
				sortable : true,
				align : 'center',
				renderer : ''
			}, {
				header : '数据2 ',
				dataIndex : 'data2',
				sortable : true,
				align : 'center',
				renderer : ''
			}, {
				header : '数据3',
				dataIndex : 'data3',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据4',
				dataIndex : 'data4',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据5',
				dataIndex : 'data5',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据6',
				dataIndex : 'data6',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据7',
				dataIndex : 'data7',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据8',
				dataIndex : 'data8',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据9',
				dataIndex : 'data9',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据10',
				dataIndex : 'data10',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据11',
				dataIndex : 'data11',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据12',
				dataIndex : 'data12',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据13',
				dataIndex : 'data13',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据14',
				dataIndex : 'data14',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据15',
				dataIndex : 'data15',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据16',
				dataIndex : 'data16',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据17',
				dataIndex : 'data17',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据18',
				dataIndex : 'data18',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据 19',
				dataIndex : 'data19',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据20',
				dataIndex : 'data20',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据21',
				dataIndex : 'data21',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据22',
				dataIndex : 'data22',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据23',
				dataIndex : 'data23',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据24',
				dataIndex : 'data24',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据25',
				dataIndex : 'data25',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据26',
				dataIndex : 'data26',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据27',
				dataIndex : 'data27',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据28',
				dataIndex : 'data28',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据29',
				dataIndex : 'data29',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据30',
				dataIndex : 'data30',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据31',
				dataIndex : 'data31',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据32',
				dataIndex : 'data32',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据33',
				dataIndex : 'data33',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据34',
				dataIndex : 'data34',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据35',
				dataIndex : 'data35',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据36',
				dataIndex : 'data36',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据37',
				dataIndex : 'data37',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据38',
				dataIndex : 'data38',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据39',
				dataIndex : 'data39',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据40',
				dataIndex : 'data40',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据41',
				dataIndex : 'data41',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据42',
				dataIndex : 'data42',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据43',
				dataIndex : 'data43',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据44',
				dataIndex : 'data44',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据45',
				dataIndex : 'data45',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据46',
				dataIndex : 'data46',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据47',
				dataIndex : 'data47',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据48',
				dataIndex : 'data48',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据49',
				dataIndex : 'data49',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '数据 50',
				dataIndex : 'data50',
				sortable : true,
				align : 'center',
				renderer : ''
			}]);
		// 创建gridPanel
	var ammHWindowGrid = new Ext.grid.GridPanel({
				id : 'ammHWindowGrid',
				autoScroll : true,
				stripeRows : true,
				viewConfig : {
					forceFit : false
				},
				cm : ammHWindowCM,
				store : ammHWindowStore,
				bbar : new Ext.ux.MyToolbar({
							enableExpAll : true,
							store : ammHWindowStore
						}),
				tbar : [{
							xtype : 'label',
							html : "<font font-weight:bold;>接口异常事件</font>"
						}]
			});
		var windowPanel = new Ext.Panel({
					autoScroll : true,
					layout : 'fit',
					border : true,
					items : [ammHWindowGrid]
				});
	
		//弹出窗口
	this.ammeterHWindowPanel = new Ext.Window({
				title : '接口异常事件',
				width : 750,
				height : 460,
				layout : 'fit',
				closeAction : 'hide',
				resizable : false,
				items : [windowPanel]
			});
			
			
			
	var viewPanel = new Ext.Panel({
//		title : '电能表运行状态查询',
		layout: 'border',
		bodyStyle:'padding:5px 5px 5px 5px',
		border : false,
		items: [topPanel,ammeterGrid]
	});
	
	renderModel(viewPanel,'异常');
	
	if (typeof(staticAmmeterRunStatusOrgNo) != 'undefined'
			&& typeof(staticAmmeterRunStatusStartDate) != 'undefined' 
			&& typeof(staticAmmeterRunStatusFlag) != 'undefined') {
		
		Ext.getCmp("ammeterStartDate").setValue(staticAmmeterRunStatusStartDate);
		Ext.getCmp("ammeterEndDate").setValue(staticAmmeterRunStatusEndDate);
		Ext.getCmp("ammeterOrgNo").setValue(staticAmmeterRunStatusOrgNo);
		Ext.getCmp("ammeterTextField").setValue(staticAmmeterRunStatusOrgName);
		Ext.getCmp("ammeterEventNo").setValue(staticAmmeterRunStatusFlag);
		
		
		//==============================  
		//===================================
		ammeterStore.baseParams = {
				orgNo : Ext.getCmp("ammeterOrgNo").getValue(),
				startDate : Ext.getCmp("ammeterStartDate").getValue(),
				endDate : Ext.getCmp("ammeterEndDate").getValue().add(Date.DAY, 1),
				eventNo : Ext.getCmp("ammeterEventNo").getValue()
			}
			ammeterStore.load();
	}
	
	// 触发事件加载数据
	Ext.getCmp('异常').on('activate', function() {
		if (typeof(staticAmmeterRunStatusOrgNo) != 'undefined'
				&& typeof(staticAmmeterRunStatusStartDate) != 'undefined'
				&& typeof(staticAmmeterRunStatusFlag) != 'undefined') {

			Ext.getCmp("ammeterStartDate").setValue(staticAmmeterRunStatusStartDate);
			Ext.getCmp("ammeterEndDate").setValue(staticAmmeterRunStatusEndDate);
			Ext.getCmp("ammeterOrgNo").setValue(staticAmmeterRunStatusOrgNo);
			Ext.getCmp("ammeterTextField").setValue(staticAmmeterRunStatusOrgName);
			Ext.getCmp("ammeterEventNo").setValue(staticAmmeterRunStatusFlag);
			// 设置异常类型 默认为空

			// ==============================
			// ===================================
			ammeterStore.baseParams = {
				orgNo : Ext.getCmp("ammeterOrgNo").getValue(),
				startDate : Ext.getCmp("ammeterStartDate").getValue(),
				endDate : Ext.getCmp("ammeterEndDate").getValue().add(Date.DAY, 1),
				eventNo : Ext.getCmp("ammeterEventNo").getValue()
			}
			ammeterStore.load();
		}

	});
});	
	
