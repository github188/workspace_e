// 定义群组类型全局变量，0代表普通群组，1代表控制群组
//var groupType;
Ext.onReady(function() {
	// grid序号
	Ext.override(Ext.grid.RowNumberer, {
				renderer : function(v, p, record, rowIndex) {
					if (this.rowspan) {
						p.cellAttr = 'rowspan="' + this.rowspan + '"';
					}
					return rowIndex + 1;
				}
			});

	var opTypePanel = new Ext.Panel({
		region : 'north',
		height : 35,
		layout : 'table',
		bodyStyle : 'padding:10px 0px 0px 0px',
		border : false,
		items : [{
					border : false,
					width : 200,
					layout : 'form',
					items : [{
								xtype : 'radio',
								name : 'opType',
								boxLabel : '添加重点用户',
								checked : true
							}]
				}, {
					border : false,
					width : 200,
					layout : 'form',
					items : [{
								xtype : 'radio',
								name : 'opType',
								boxLabel : '查看重点用户',
								listeners : {
									check : function(checkbox, checked) {
										if (checked) {
											secondPanel.layout.setActiveItem(1);
											// autoDelStoreList();
											delVipUserStore.load({
														params : {
															start : 0,
															limit : DEFAULT_PAGE_SIZE
														}
													});

										} else {
											secondPanel.layout.setActiveItem(0);
										}
									}
								}
							}]
				}]
	});

	// 新增重点用户列表
	// begin

	// 列表序列
	var rowNum_addVipUserCM = new Ext.grid.RowNumberer({
				renderer : function(v, p, record, rowIndex) {
					var startRow = 0;
					if (userStore && userStore.lastOptions
							&& userStore.lastOptions.params) {
						startRow = userStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			});
	var sm_addVipUserCM = new Ext.grid.CheckboxSelectionModel();
	var addVipUserCM = new Ext.grid.ColumnModel([rowNum_addVipUserCM,
			sm_addVipUserCM, {
				header : '供电单位',
				sortable : true,
				align : 'center',
				dataIndex : 'orgName',
				width : 130,
				renderer : function(val) {
					if (null != val && "" != val)
						var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="'
								+ val + '">' + val + '</div>';
					return html;
				}
			}, {
				header : '用户编号',
				sortable : true,
				align : 'center',
				dataIndex : 'consNo',
				width : 80
			}, {
				header : '用户名称',
				sortable : true,
				align : 'center',
				dataIndex : 'consName',
				width : 100,
				renderer : function(val) {
					if (null != val && "" != val)
						var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="'
								+ val + '">' + val + '</div>';
					return html;
				}
			}, {
				header : '用户地址',
				sortable : true,
				align : 'center',
				dataIndex : 'consAddr',
				width : 100,
				renderer : function(val) {
					if (null != val && "" != val)
						var html = '<div align = "left" ext:qtitle="用户地址" ext:qtip="'
								+ val + '">' + val + '</div>';
					return html;
				}
			}, {
				header : '终端地址',
				sortable : true,
				align : 'center',
				dataIndex : 'terminalAdd',
				width : 100
			}, {
				header : '终端资产编号',
				sortable : true,
				align : 'center',
				resizable : true,
				dataIndex : 'tmnlAssetNo'
			}, {
				header : '线路',
				sortable : true,
				align : 'center',
				dataIndex : 'line',
				width : 60,
				renderer : function(val) {
					if (null != val && "" != val)
						var html = '<div align = "left" ext:qtitle="线路" ext:qtip="'
								+ val + '">' + val + '</div>';
					return html;
				}
			}, {
				header : '行业',
				sortable : true,
				align : 'center',
				dataIndex : 'trade',
				width : 100,
				renderer : function(val) {
					if (null != val && "" != val)
						var html = '<div align = "left" ext:qtitle="行业" ext:qtip="'
								+ val + '">' + val + '</div>';
					return html;
				}
			}, {
				header : '运行容量',
				sortable : true,
				align : 'center',
				dataIndex : 'runCap',
				width : 80
			}]);

	var userStore = new Ext.data.Store({
				url : './advapp/vipconsman/vipconsmanager!generalGridByTree.action',
				reader : new Ext.data.JsonReader({
							root : 'managerDtoList',
							totalProperty : 'totalCount'
						}, [	{
									name : 'orgNo'
								},{
									name : 'orgType'
								},{
									name : 'consId'
								},{
									name : 'orgName'
								}, {
									name : 'consNo'
								}, {
									name : 'consName'
								},{
									name : 'consAddr'
								}, {
									name : 'terminalAdd'
								}, {
									name : 'tmnlAssetNo'
								}, {
									name : 'line'
								}, {
									name : 'trade'
								}, {
									name : 'runCap'
								}])
			});

//	function storeToArray(valStore) {
//		var data = new Array();
//		for (var i = 0; i < valStore.getCount(); i++) {
//			data[i] = new Array();
//			data[i][0] = valStore.getAt(i).data.orgName;
//			data[i][1] = valStore.getAt(i).data.consNo;
//			data[i][2] = valStore.getAt(i).data.consName;
//			data[i][3] = valStore.getAt(i).data.consAddr;
//			data[i][4] = valStore.getAt(i).data.tmnlAddr;
//			data[i][5] = valStore.getAt(i).data.tmnlAssetNo;
//			data[i][6] = valStore.getAt(i).data.line;
//			data[i][7] = valStore.getAt(i).data.trade;
//			data[i][8] = valStore.getAt(i).data.runCap;
//		}
//		return data;
//	}

	var addVipUserStore = new Ext.data.Store({
				remoteSort : true,
				proxy : new Ext.data.MemoryProxy(),
				reader : new Ext.data.ArrayReader({
							idIndex : 5,
							fields : [{
										name : 'orgNo'
									},{
										name : 'orgType'
									}, {
										name : 'consId'
									}, {
										name : 'orgName'
									}, {
										name : 'consNo'
									}, {
										name : 'consName'
									}, {
										name : 'consAddr'
									}, {
										name : 'tmnlAddr'
									}, {
										name : 'tmnlAssetNo'
									}, {
										name : 'line'
									}, {
										name : 'trade'
									}, {
										name : 'runCap'
									}, {
										name : 'consId'
									}]
						})
			});
	// grid解锁
//	function unlockGrid() {
//		sm_addVipUserCM.unlock();
//		addVipUserGrid.onEnable();
//		addVipUserGrid.getBottomToolbar().enable();
//	}
//
//	// grid解锁
//	function lockGrid() {
//		sm_addVipUserCM.lock();
//		addVipUserGrid.onDisable();
//		addVipUserGrid.getBottomToolbar().disable();
//	}
//	var gsselectAllcb = new Ext.form.Checkbox({
//				boxLabel : '全选',
//				name : 'gsselectAllcb',
//				checked : false,
//				listeners : {
//					'check' : function(r, c) {
//						if (c) {
//							sm_addVipUserCM.selectAll();
//							lockGrid();
//						} else {
//							unlockGrid();
//							sm_addVipUserCM.clearSelections();
//						}
//					}
//				}
//			});
	var addVipUserGrid = new Ext.grid.GridPanel({
		id : 'addVipUserGrid',
		region : 'center',
		cm : addVipUserCM,
		store : userStore,
		sm : sm_addVipUserCM,
		stripeRows : true,
		loadMask :false,
		tbar : [{
					xtype : 'label',
					html : "<font font-weight:bold;>备选用户</font>"
				}, {
					xtype : 'tbfill'
				}, '-', {
					text : '加入重点用户',
					iconCls : 'plus',
					handler : function() {
//								var vipConsArray = new Array();
								var recs = sm_addVipUserCM.getSelections();
								if (recs.length < 1) {
									Ext.Msg.alert('提示', '请选择要加入重点用户的用户');
									return;
								} 
								Ext.MessageBox.confirm('提示', '确定加入重点用户吗？', saveOrUpdateVipCons);
								// }
								// if (gsselectAllcb.checked) {
								//							gsselectAllcb.setValue(false);
								//							sm_addVipUserCM.selectAll();
								//						}
							}
				}, '-'
//				, {
//					text : '删除选中用户',
//					iconCls : 'cancel',
//					handler : function() {
////						if (gsselectAllcb.checked) {
////							userStore.removeAll(true);
////						} else {
//							var recs = sm_addVipUserCM.getSelections();
//							for (var i = 0; i < recs.length; i = i + 1) {
//								userStore.remove(userStore
//										.getById(recs[i].data.tmnlAssetNo));
//							}
////						}
//						userStore.proxy = new Ext.ux.data.PagingMemoryProxy(userStore);
//						userStore.load({
//									params : {
//										start : 0,
//										limit : DEFAULT_PAGE_SIZE
//									}
//								});
//					}
//				}
				
				],
		bbar : new Ext.ux.MyToolbar({
					store : userStore
				})
	});
	// -------------------------------------------------------------------------------------------新增群组-主界面
	// end

	// 监听左边树点击事件
	var treeListener = 	new LeftTreeListener({
				modelName : '重点用户管理',
				processClick : function(p, node, e) {
					if ("addVipUserGrid" == secondPanel.getLayout().activeItem.getId()) {
						var obj = node.attributes.attributes;
						var type = node.attributes.type;
						if (type == 'usr') {
							Ext.getBody().mask("正在获取数据...");
							userStore.clearData();
							userStore.baseParams = {
								tmnlAssetNo : obj.tmnlAssetNo,
								nodeType : type,
								start : 0,
								limit : DEFAULT_PAGE_SIZE
							};
							userStore.load({
										callback : function(recs, options, success) {
											Ext.getBody().unmask();
										},
										add : true
									});
						} else if (type == 'org') {
							Ext.getBody().mask("正在获取数据...");
							userStore.clearData();
							userStore.baseParams = {
								orgNo : obj.orgNo,
								orgType : obj.orgType,
								nodeType : type,
								start : 0,
								limit : DEFAULT_PAGE_SIZE
							};
							userStore.load({
										callback : function(recs, options, success) {
											Ext.getBody().unmask();
										},
										add : true
									});
						} else if (type == 'line') {
							Ext.getBody().mask("正在获取数据...");
							userStore.clearData();
							userStore.baseParams = {
								lineId : obj.lineId,
								nodeType : type,
								start : 0,
								limit : DEFAULT_PAGE_SIZE
							};
							userStore.load({
										callback : function(recs, options,success) {
											Ext.getBody().unmask();
										},
										add : true
									});
						} else if (type == 'cgp' || type == 'ugp') {
							Ext.getBody().mask("正在获取数据...");
							userStore.clearData();
							userStore.baseParams = {
								groupNo : obj.groupNo,
								nodeType : type,
								start : 0,
								limit : DEFAULT_PAGE_SIZE
							};
							userStore.load({
										callback : function(recs, options,success) {
											Ext.getBody().unmask();
										},
										add : true
									});
						} else if (type == 'sub') {
							Ext.getBody().mask("正在获取数据...");
							userStore.clearData();
							userStore.baseParams = {
								subsId : obj.subsId,
								nodeType : type,
								start : 0,
								limit : DEFAULT_PAGE_SIZE
							};
							userStore.load({
										callback : function(recs, options,success) {
											Ext.getBody().unmask();
										},
										add : true
									});
						}
					}
					return true;
				},
				processUserGridSelect : function(cm, row, record) {
					if ("addVipUserGrid" == secondPanel.getLayout().activeItem.getId()) {
						Ext.getBody().mask("正在获取数据...");
						userStore.clearData();
						userStore.baseParams = {
							tmnlAssetNo : record.get('tmnlAssetNo'),
							nodeType : 'usr',
							start : 0,
							limit : DEFAULT_PAGE_SIZE
						};
						userStore.load({
									callback : function(recs, options, success) {
										Ext.getBody().unmask();
									},
									add : true
								});
					}
					return true;
				}
			});

	userStore.on('load', function(thisstore, recs, obj) {
//		addVipUserStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(thisstore));
//		addVipUserStore.load({
//					params : {
//						start : 0,
//						limit : DEFAULT_PAGE_SIZE
//					}
//				});
		sm_addVipUserCM.selectAll();
//		addVipUserGrid.doLayout();
	});
//	addVipUserStore.on('load', function() {
//		if (gsselectAllcb.checked) {
//			gsselectAllcb.setValue(false);
//		}
//			 sm_addVipUserCM.selectAll();
//		});



	var delVipUser_rowNumList = new Ext.grid.RowNumberer({
				renderer : function(v, p, record, rowIndex) {
					var startRow = 0;
					if (delVipUserStore && delVipUserStore.lastOptions
							&& delVipUserStore.lastOptions.params) {
						startRow = delVipUserStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			});
	var del_vipuser_checkbox = new Ext.grid.CheckboxSelectionModel();
	var del_vipuser_cm = new Ext.grid.ColumnModel([delVipUser_rowNumList,
			del_vipuser_checkbox,
			// {header:'群组编号',dataIndex:'groupNo'},
			{
				header : '供电单位',
				sortable : true,
				align : 'center',
				dataIndex : 'orgName',
				width : 130,
				renderer : function(val) {
					if (null != val && "" != val)
						var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="'
								+ val + '">' + val + '</div>';
					return html;
				}
			}, {
				header : '用户编号',
				sortable : true,
				align : 'center',
				dataIndex : 'consNo',
				width : 80
			}, {
				header : '用户名称',
				sortable : true,
				align : 'center',
				dataIndex : 'consName',
				width : 100,
				renderer : function(val) {
					if (null != val && "" != val)
						var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="'
								+ val + '">' + val + '</div>';
					return html;
				}
			}, {
				header : '用户地址',
				sortable : true,
				align : 'center',
				dataIndex : 'consAddr',
				width : 100,
				renderer : function(val) {
					if (null != val && "" != val)
						var html = '<div align = "left" ext:qtitle="用户地址" ext:qtip="'
								+ val + '">' + val + '</div>';
					return html;
				}
			}, {
				header : '终端地址',
				sortable : true,
				align : 'center',
				dataIndex : 'terminalAdd',
				width : 100
			}, {
				header : '终端资产编号',
				sortable : true,
				align : 'center',
				resizable : true,
				dataIndex : 'tmnlAssetNo'
			}, {
				header : '线路',
				sortable : true,
				align : 'center',
				dataIndex : 'line',
				width : 60,
				renderer : function(val) {
					if (null != val && "" != val)
						var html = '<div align = "left" ext:qtitle="线路" ext:qtip="'
								+ val + '">' + val + '</div>';
					return html;
				}
			}, {
				header : '行业',
				sortable : true,
				align : 'center',
				dataIndex : 'trade',
				width : 100,
				renderer : function(val) {
					if (null != val && "" != val)
						var html = '<div align = "left" ext:qtitle="行业" ext:qtip="'
								+ val + '">' + val + '</div>';
					return html;
				}
			}, {
				header : '运行容量',
				sortable : true,
				align : 'center',
				dataIndex : 'runCap',
				width : 80
			}
			, {
				header : '用户类别',
				sortable : true,
				align : 'center',
				dataIndex : 'consSort',
				renderer : function(val) {
					if (null != val && "" != val) {
						if (val == 1) {
							val = 'VIP';
						} else if (val == 2) {
							val = '高耗能';
						} else if (val == 3) {
							val = '高危';
						}
						var html = '<div align = "left" ext:qtitle="用户类别" ext:qtip="'
								+ val + '">' + val + '</div>';
					}
					return html;
				}
			}, {
				header : '监视级别',
				sortable : true,
				align : 'center',
				dataIndex : 'orgType',
				renderer : function(val) {
					if (null != val && "" != val) {
						var html = '<div align = "left" ext:qtitle="行业" ext:qtip="'
								+ val + '">' + val + '</div>';
					}
					return html;
				}
			}, {
				header : '监视频度',
				sortable : true,
				align : 'center',
				dataIndex : 'monitorFreq',
				renderer : function(val) {
					if (null != val && "" != val) {
						if (val == 0) {
							val = '小时';
						} else if (val == 1) {
							val = '天';
						} else if (val == 2) {
							val = '月';
						}
						var html = '<div align = "left" ext:qtitle="行业" ext:qtip="'
								+ val + '">' + val + '</div>';
					}
					return html;
				}
			}, {
				header : '后台监视',
				sortable : true,
				align : 'center',
				dataIndex : 'monitorFlag',
				renderer : function(val) {
					if (null != val && "" != val) {
						if (null != val && "" != val) {
							if (val == 0) {
								val = '后台不监视';
							} else if (val == 1) {
								val = '后台监视';
							}
							var html = '<div align = "left" ext:qtitle="行业" ext:qtip="'
									+ val + '">' + val + '</div>';
						}
						return html;
					}
				}
			}, {
				header : '监视终端事件',
				sortable : true,
				align : 'center',
				dataIndex : 'monitorEvent',
				renderer : function(val) {
					if (null != val && "" != val) {
						if (val == 0) {
							val = '否';
						} else if (val == 1) {
							val = '是';
						}
						var html = '<div align = "left" ext:qtitle="行业" ext:qtip="'
								+ val + '">' + val + '</div>';

					}
					return html;
				}
			}, {
				header : '监视工况',
				sortable : true,
				align : 'center',
				dataIndex : 'monitorRun',
				renderer : function(val) {
					if (null != val && "" != val) {
						if (val == 0) {
							val = '否';
						} else if (val == 1) {
							val = '是';
						}
						var html = '<div align = "left" ext:qtitle="行业" ext:qtip="'
								+ val + '">' + val + '</div>';
					}
					return html;
				}
			}, {
				header : '监视用电异常',
				sortable : true,
				align : 'center',
				dataIndex : 'monitorEc',
				renderer : function(val) {
					if (null != val && "" != val) {
						if (val == 0) {
							val = '否';
						} else if (val == 1) {
							val = '是';
						}
						var html = '<div align = "left" ext:qtitle="行业" ext:qtip="'
								+ val + '">' + val + '</div>';
					}
					return html;
				}
			}]);
	var delVipUserStore = new Ext.data.Store({
				url : './advapp/vipconsman/vipconsmanager!findVipCons.action',
				reader : new Ext.data.JsonReader({
							root : 'vipUserDtoList',
							totalProperty : 'totalCount'
						}, [{
									name : 'orgNo'
								}, {
									name : 'consId'
								}, {
									name : 'orgName'
								}, {
									name : 'consNo'
								}, {
									name : 'consName'
								}, {
									name : 'consAddr'
								}, {
									name : 'terminalAdd'
								}, {
									name : 'tmnlAssetNo'
								}, {
									name : 'line'
								}, {
									name : 'trade'
								}, {
									name : 'runCap'
								}, {
									name : 'consSort'
								}, {
									name : 'orgType'
								}, {
									name : 'monitorFreq'
								}, {
									name : 'monitorFlag'
								}, {
									name : 'monitorEvent'
								}, {
									name : 'monitorRun'
								}, {
									name : 'monitorEc'
								}])
			});

	
	
	delVipUserStore.on('load', function() {
		del_vipuser_checkbox.selectAll();
	});
	
	var del_vipuser_gridpanel = new Ext.grid.GridPanel({
		region : 'center',
		store : delVipUserStore,
		cm : del_vipuser_cm,
		sm : del_vipuser_checkbox,
		stripeRows : true,
		viewConfig : {
			 forceFit: false
				},
		tbar : [{
					xtype : 'label',
					html : "<font font-weight:bold; color:#15428b>重点用户列表</font>"
				}, {
					xtype : 'tbfill'
				}, '-',{
					text : '删除所选重点用户',
					iconCls : 'cancel',
					handler : function() {
						var recs = del_vipuser_checkbox.getSelections();
						if (null == recs || recs.length == 0) {
							Ext.MessageBox.alert("提示", "请选择重点用户！");
							return;
						}
						Ext.MessageBox.confirm('提示', '确定删除重点用户吗？', deleteVipUser);
					}
				},'-'],
		bbar : new Ext.ux.MyToolbar({
					store : delVipUserStore
				})
	});
	
	
//	window.groupConManageShow = function() {
//		// 全局终端列表
//		var consResult;
//		var groupRecs = del_vipuser_checkbox.getSelections();
//		var groupConsNo = new Ext.form.TextField({
//					fieldLabel : '用户编号',
//					emptyText : '请输入用户编号',
//					allowBlank : false,
//					blankText : '请输入用户编号',
//					width : 150
//				});
//
//		var panel_conNo = new Ext.Panel({
//					region : 'north',
//					height : 50,
//					layout : 'column',
//					border : false,
//					bodyStyle : 'padding:15px 0px 0px 5px',
//					items : [{
//								border : false,
//								columnWidth : .7,
//								layout : 'form',
//								labelAlign : 'right',
//								labelSeparator : ' ',
//								items : [groupConsNo]
//							}, {
//								border : false,
//								columnWidth : .15,
//								items : [{
//											xtype : 'button',
//											text : '查询',
//											width : 80,
//											handler : queryGroupDetailByCons
//										}]
//
//							}, {
//								border : false,
//								columnWidth : .15,
//								items : [{
//									xtype : 'button',
//									text : '删除',
//									width : 80,
//									handler : function() {
//										var recs = sm_consList.getSelections();
//										if (consGsselectAllcb.checked) {
//											if (null == consResult
//													|| 0 == consResult.length) {
//												Ext.MessageBox.alert("提示",
//														"请选择要删除的终端！");
//												return;
//											}
//										} else {
//											if (null == recs
//													|| recs.length == 0) {
//												Ext.MessageBox.alert("提示",
//														"请选择要删除的终端！");
//												return;
//											}
//										}
//										Ext.MessageBox.confirm('提示', '确定删除吗？',
//												deleteGroupDetail);
//									}
//								}]
//							}]
//				});
//
//		function queryGroupDetailByCons() {
//			if (null == groupConsNo || "" == groupConsNo.getValue()) {
//				Ext.MessageBox.alert("提示", "请输入用户编号！");
//				return;
//			}
//			Ext.getBody().mask("正在获取数据...");
//			var groupNo = groupRecs[0].get('groupNo');
//			// var groupType=groupRecs[0].get('groupType');
//			Ext.Ajax.request({
//				url : './advapp/vipconsman/vipconsmanager!queryGroupUserByCons.action',
//				params : {
//					groupType : groupType,
//					groupNo : groupNo,
//					consNo : groupConsNo.getValue()
//				},
//				success : function(response) {
//					var result = Ext.decode(response.responseText);
//					consResult = result.tTmnlGroupDetailDtoList;
//					ds_consList.proxy = new Ext.ux.data.PagingMemoryProxy(consResult);
//					ds_consList.load({
//								params : {
//									start : 0,
//									limit : DEFAULT_PAGE_SIZE
//								}
//							});
//				},
//				callback : function() {
//					Ext.getBody().unmask();
//				}
//			});
//		};
//
//		function deleteGroupDetail(btn) {
//			if (btn == 'no')
//				return true;
//			Ext.getBody().mask("正在处理...");
//			var recs2 = sm_consList.getSelections();
//			var vipConsArray = new Array();
//			if (consGsselectAllcb.checked) {
//				for (var i = 0; i < consResult.length; i++) {
//					var tmnl = consResult[i].consNo + '`'
//							+ consResult[i].tmnlAssetNo;
//					vipConsArray[i] = tmnl;
//				}
//			} else {
//				for (var i = 0; i < recs2.length; i++) {
//					var tmnl = recs2[i].get('consNo') + '`'
//							+ recs2[i].get('tmnlAssetNo');
//					vipConsArray[i] = tmnl;
//				}
//			}
//			Ext.Ajax.request({
//						url : 'baseapp/groupSet!deletegroupDetailBy.action',
//						params : {
//							groupType : groupType,
//							groupNo : groupRecs[0].get('groupNo'),
//							groupTmnlList : vipConsArray
//						},
//						callback : function(options, success, response) {
//							Ext.getBody().unmask();
//							if (success)
//								queryGroupDetail(function() {
//											Ext.MessageBox.alert("提示", "删除完毕！");
//										});
//						}
//					});
//		};
//
//		var rowNum_consList = new Ext.grid.RowNumberer({
//					renderer : function(v, p, record, rowIndex) {
//						var startRow = 0;
//						if (ds_consList && ds_consList.lastOptions
//								&& ds_consList.lastOptions.params) {
//							startRow = ds_consList.lastOptions.params.start;
//						}
//						return startRow + rowIndex + 1;
//					}
//				});
//		var sm_consList = new Ext.grid.CheckboxSelectionModel();
//		var cm_consList = new Ext.grid.ColumnModel([rowNum_consList,
//				sm_consList, {
//					header : '供电单位',
//					sortable : true,
//					align : 'center',
//					dataIndex : 'orgName',
//					width : 130,
//					renderer : function(val) {
//						if (null != val && "" != val)
//							return "<div align = 'left'>" + val + "</div>";
//					}
//				}, {
//					header : '用户编号',
//					sortable : true,
//					align : 'center',
//					dataIndex : 'consNo',
//					width : 80
//				}, {
//					header : '用户名称',
//					sortable : true,
//					align : 'center',
//					dataIndex : 'consName',
//					width : 130,
//					renderer : function(val) {
//						if (null != val && "" != val)
//							return "<div align = 'left'>" + val + "</div>";
//					}
//				}, {
//					header : '地址',
//					sortable : true,
//					align : 'center',
//					dataIndex : 'consAddr',
//					width : 130,
//					renderer : function(val) {
//						if (null != val && "" != val)
//							return "<div align = 'left'>" + val + "</div>";
//					}
//				}, {
//					header : '线路',
//					sortable : true,
//					align : 'center',
//					dataIndex : 'line',
//					width : 60,
//					renderer : function(val) {
//						if (null != val && "" != val)
//							return "<div align = 'left'>" + val + "</div>";
//					}
//				}, {
//					header : '行业',
//					sortable : true,
//					align : 'center',
//					dataIndex : 'trade',
//					width : 100,
//					renderer : function(val) {
//						if (null != val && "" != val)
//							return "<div align = 'left'>" + val + "</div>";
//					}
//				},
//				// {header:'终端资产编号',sortable:true,align:'center',dataIndex:'tmnlAssetNo'},
//				{
//					header : '控制开始日期',
//					sortable : true,
//					align : 'center',
//					dataIndex : 'startDate',
//					width : 70
//				}, {
//					header : '控制结束日期',
//					sortable : true,
//					align : 'center',
//					dataIndex : 'finishDate',
//					width : 70
//				}]);
//		var ds_consList = new Ext.data.Store({
//					remoteSort : true,
//					proxy : new Ext.data.MemoryProxy(),
//					reader : new Ext.data.JsonReader({
//								idIndex : 6
//							}, [{
//										name : 'orgName'
//									}, {
//										name : 'consNo'
//									}, {
//										name : 'consName'
//									}, {
//										name : 'consAddr'
//									}, {
//										name : 'line'
//									}, {
//										name : 'trade'
//									}, {
//										name : 'tmnlAssetNo'
//									}, {
//										name : 'startDate'
//									}, {
//										name : 'finishDate'
//									}])
//				});
//
//		var consGsselectAllcb = new Ext.form.Checkbox({
//					boxLabel : '全选',
//					name : 'consGsselectAllcb',
//					checked : false,
//					listeners : {
//						'check' : function(r, c) {
//							if (c) {
//								sm_consList.selectAll();
//								sm_consList.lock();
//								panel_conList.onDisable();
//								panel_conList.getBottomToolbar().disable();
//							} else {
//								sm_consList.unlock();
//								panel_conList.onEnable();
//								panel_conList.getBottomToolbar().enable();
//								sm_consList.clearSelections();
//							}
//						}
//					}
//				});
//		var panel_conList = new Ext.grid.GridPanel({
//					region : 'center',
//					store : ds_consList,
//					cm : cm_consList,
//					sm : sm_consList,
//					stripeRows : true,
//					tbar : [{
//								xtype : 'label',
//								html : "<font font-weight:bold;>用户列表</font>"
//							}, {
//								xtype : 'tbfill'
//							}, '-', consGsselectAllcb],
//					bbar : new Ext.ux.MyToolbar({
//								store : ds_consList
//							}),
//					viewConfig : {
//						forceFit : true
//					}
//				});
//
//		function queryGroupDetail(fn) {
//			Ext.getBody().mask("正在获取数据...");
//			var groupNo = groupRecs[0].get('groupNo');
//			// var groupType=groupRecs[0].get('groupType');
//			Ext.Ajax.request({
//				url : './advapp/vipconsman/vipconsmanager!!queryGroupUser.action',
//				params : {
//					groupType : groupType,
//					groupNo : groupNo
//				},
//				success : function(response) {
//					var result = Ext.decode(response.responseText);
//					consResult = result.tTmnlGroupDetailDtoList;
//					ds_consList.proxy = new Ext.ux.data.PagingMemoryProxy(consResult);
//					ds_consList.load({
//								params : {
//									start : 0,
//									limit : DEFAULT_PAGE_SIZE
//								}
//							});
//					if (fn)
//						fn();
//				},
//				callback : function() {
//					Ext.getBody().unmask();
//				}
//			});
//		};
//		ds_consList.on('load', function() {
//			if (consGsselectAllcb.checked) {
//				consGsselectAllcb.setValue(false);
//			}
//				// sm_consList.selectAll();
//			});
//		var w_groupConManage = new Ext.Window({
//					layout : 'border',
//					modal : true,
//					// resizable : false,
//					title : '群组用户管理',
//					maximizable : true,
//					width : 800,
//					height : 550,
//					minWidth : 600,
//					minHeight : 412,
//					items : [panel_conNo, panel_conList]
//				});
//		// -----------------------------------------------------------------群组管理-群组用户管理
//		// end
//		w_groupConManage.show();
//		queryGroupDetail();
//	};

//	window.groupReNameShow = function() {
//		var groupRecs = del_vipuser_checkbox.getSelections();
//		// if(groupRecs==null||groupRecs.length==0){
//		// Ext.MessageBox.alert("提示","请选择群组！");
//		// return;
//		// }
//		// if(groupRecs.length>1){
//		// Ext.MessageBox.alert("提示","只能选择一个群组！");
//		// return;
//		// }
//		// /---------------------------------------------------------群组管理-群组更名
//		// begin
//		var originalGroupName1 = new Ext.form.TextField({
//					fieldLabel : '原群组名',
//					anchor : '99%',
//					readOnly : true
//				});
//		var originalGroupName2 = new Ext.form.TextField({
//					anchor : '90%',
//					readOnly : true
//				});
//		var newName1 = new Ext.form.TextField({
//					fieldLabel : '新群组名<font color="red">*</font>',
//					anchor : '99%',
//					readOnly : true
//				});
//		var newName2 = new Ext.form.TextField({
//					emptyText : '请输入新群组名称',
//					allowBlank : false,
//					blankText : '请输入新群组名称',
//					anchor : '90%'
//				});
//		var panel_groupReName1 = new Ext.Panel({
//					anchor : '100% 45%',
//					border : false,
//					layout : 'column',
//					items : [{
//								border : false,
//								columnWidth : .5,
//								layout : 'form',
//								labelAlign : 'right',
//								labelSeparator : ' ',
//								labelWidth : 60,
//								bodyStyle : 'padding:25px 0px 0px 10px',
//								items : [originalGroupName1]
//							}, {
//								border : false,
//								columnWidth : .5,
//								layout : 'form',
//								hideLabels : true,
//								bodyStyle : 'padding:25px 0px 0px 0px',
//								items : [originalGroupName2]
//							}]
//				});
//
//		var panel_groupReName2 = new Ext.Panel({
//			anchor : '100% 50%',
//			border : false,
//			layout : 'column',
//			buttonAlign : 'center',
//			items : [{
//						border : false,
//						columnWidth : .5,
//						layout : 'form',
//						labelAlign : 'right',
//						labelSeparator : ' ',
//						labelWidth : 60,
//						bodyStyle : 'padding:0px 0px 0px 10px',
//						items : [newName1]
//					}, {
//						border : false,
//						columnWidth : .5,
//						layout : 'form',
//						hideLabels : true,
//						items : [newName2]
//					}],
//			buttons : [{
//				text : '确认',
//				handler : function() {
//					if (null == newName2.getValue()
//							|| "" == newName2.getValue()) {
//						Ext.MessageBox.alert("提示", "请输入新群组名称！");
//						return;
//					}
//					// var
//					// groupType=groupRecs[0].get('groupType');
//					var groupNo = groupRecs[0].get('groupNo');
//					var groupName;
//					if (groupType == "0")
//						groupName = newName2.getValue();
//					else if (groupType == "1")
//						groupName = newName1.getValue() + '-'
//								+ newName2.getValue();
//					else
//						return;
//					w_groupReName.hide();
//					Ext.getBody().mask("正在处理...");
//					Ext.Ajax.request({
//								url : './advapp/vipconsman/vipconsmanager!rejiggerGroupName.action',
//								params : {
//									groupType : groupType,
//									groupNo : groupNo,
//									groupName : groupName
//								},
//								callback : function(options, success, response) {
//									Ext.getBody().unmask();
//									var result = Ext
//											.decode(response.responseText);
//									if (success) {
//										if (null != result.FLAG) {
//											if (result.FLAG == 0) {
//												Ext.MessageBox.alert("提示",
//														"该群组名已被使用！",
//														function() {
//															w_groupReName
//																	.show();
//														});
//												return;
//											} else if (result.FLAG == 1) {
//												queryVipUser(function() {
//															Ext.MessageBox.alert("提示","成功！");
//														});
//											}
//										}
//									}
//									w_groupReName.close();
//								}
//							});
//				}
//			}, {
//				text : '返回',
//				handler : function() {
//					w_groupReName.close();
//				}
//			}]
//		});
//
//		var panel_groupReName = new Ext.Panel({
//					layout : 'anchor',
//					border : false,
//					items : [panel_groupReName1, panel_groupReName2]
//				});
//		var w_groupReName = new Ext.Window({
//					resizable : false,
//					title : '群组更名',
//					modal : true,
//					width : 350,
//					height : 200,
//					layout : 'fit',
//					items : [panel_groupReName]
//				});
//		// /
//		// -------------------------------------------------------群组管理-群组更名
//		// end
//		// var groupType=groupRecs[0].get('groupType');
//		var groupName = groupRecs[0].get('groupName');
//		if (groupType == "0") {
//			originalGroupName1.setValue("");
//			originalGroupName2.setValue(groupName);
//			newName1.setValue("");
//		} else if (groupType == "1") {
//			var groupName1 = groupName.substring(0, groupName.indexOf('-'));
//			var groupName2 = groupName.substring(groupName.indexOf('-') + 1,
//					groupName.length);
//			originalGroupName1.setValue(groupName1);
//			originalGroupName2.setValue(groupName2);
//			newName1.setValue(groupName1);
//		}
//		w_groupReName.show();
//	};
/**
 * 方法 saveOrUpdateVipCons
 * 
 * @param {}
 *            vipConsArray  保存重点列表
 * @description 保存为重点用户
 */
function saveOrUpdateVipCons(btn){
	if (btn == 'no')
			return true;
	var recs = sm_addVipUserCM.getSelections();
	var vipConsArray = new Array();
	for (var i = 0; i < recs.length; i++) {
										var tmnl = recs[i].get('orgNo') + ',';
										tmnl += recs[i].get('consId') + ',';
										tmnl += recs[i].get('consNo') + ',';
										tmnl += recs[i].get('orgType');

										vipConsArray[i] = tmnl;
									}
	Ext.getBody().mask("正在处理...");
		Ext.Ajax.request({
					url : './advapp/vipconsman/vipconsmanager!saveVipCons.action',
					params : {
						addVipPageList : vipConsArray
					},
					success : function(response) {
						Ext.getBody().unmask();
						var result = Ext.decode(response.responseText);
						if (null != result.flag) {
							if (result.flag == 0) {
								Ext.MessageBox.alert("提示", "操作错误！");
								return;
							} else if (result.flag == 1) {
								Ext.MessageBox.alert("提示", "操作成功！");
							}
						}
					}
				});
	
}


/**
 * 方法 deleteVipUser
 * 
 * @param {}
 *            btn 判断是否操作
 * @description 删除选中项触发后台删除
 */
function deleteVipUser(btn) {
		if (btn == 'no')
			return true;
		Ext.getBody().mask("正在处理...");
		var recs = del_vipuser_checkbox.getSelections();
		var delVipUserArray = new Array();
		for (var i = 0; i < recs.length; i++) {
			var src = recs[i].get('orgNo') + ',';
			src += recs[i].get('consId');
			delVipUserArray[i] = src;
		}
		Ext.Ajax.request({
					url : './advapp/vipconsman/vipconsmanager!deleteVipCons.action',
					params : {
						delVipPageList : delVipUserArray,
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					},
					callback : function(options, success, response) {
						Ext.getBody().unmask();
						if (success) {
							var result = Ext.decode(response.responseText);
							if (null != result.flag) {
								if (result.flag == 0)
									Ext.MessageBox.alert("提示", "删除失败！");
								else if (result.flag == 1) {
									queryVipUser(function() {
												Ext.MessageBox.alert("提示",
														"删除完毕！");
											});
								}
							}
						}
					}
				});
	};
	// ------------------------------------------------------------------------------------------群组管理-查询条件
	// beginvar
//	var groupTypeRadio = new Ext.form.RadioGroup({
//				fieldLabel : '类别',
//				width : 110,
//				columns : [.5, .5],
//				items : [new Ext.form.Radio({
//									boxLabel : '普通',
//									name : 'groupTypeRadio',
//									checked : true,
//									inputValue : '0'
//								}), new Ext.form.Radio({
//									boxLabel : '控制',
//									name : 'groupTypeRadio',
//									inputValue : '1'
//								})]
//			});

//	var ds_groupName = new Ext.data.JsonStore({
//				url : './advapp/vipconsman/vipconsmanager!queryGroup2.action',
//				fields : ['groupNo', 'groupName'],
//				idProperty : 'groupNo',
//				root : 'tTmnlGroupDtoList'
//			});
//	var text_groupName = new Ext.form.TextField({
//				fieldLabel : '名称',
//				emptyText : '请输入群组名称',
//				width : 120
//			});
//	groupTypeRadio.on('afterrender', function() {
//				ds_groupName.load({
//							params : {
//								groupType : groupTypeRadio.getValue()
//										.getRawValue()
//							}
//						});
//			});
//	groupTypeRadio.on('change', function() {
//				text_groupName.setValue("");
//				ds_groupName.load({
//							params : {
//								groupType : groupTypeRadio.getValue()
//										.getRawValue()
//							}
//						});
//			});
//	var startDate3 = new Ext.form.DateField({
//				fieldLabel : '有效日期',
//				width : 90,
//				format : 'Y-m-d',
//				value : new Date()
//			});
//	var finishDate3 = new Ext.form.DateField({
//				fieldLabel : '至',
//				width : 90,
//				format : 'Y-m-d',
//				value : new Date().add(Date.DAY, 30)
//			});

//	var check_selectDate = new Ext.form.Checkbox({
//				checked : true
//			});

//	check_selectDate.on('check', function(check_selectDate, checked) {
//				if (checked == true) {
//					startDate3.enable();
//					finishDate3.enable();
//					startDate3.setValue(new Date());
//					finishDate3.setValue(new Date().add(Date.DAY, 30));
//				} else if (checked == false) {
//					startDate3.setValue("");
//					finishDate3.setValue("");
//					startDate3.disable();
//					finishDate3.disable();
//				}
//			});

//	var queryGroupButton = new Ext.Button({
//				text : '查询',
//				width : 70,
//				handler : function() {
//					queryVipUser(function() {
//							});
//				}
//			});
	// 查询的群组结果
//	var gruopResult;
/**
 * 方法 queryVipUser
 * 
 * @param {}
 *            fn  函数
 * @description 查询重点用户
 */
	function queryVipUser(fn) {
//		var startDate = startDate3.getValue();
//		var finishDate = finishDate3.getValue();
//		if (check_selectDate.checked == true) {
//			if (startDate == "" || finishDate == "") {
//				Ext.MessageBox.alert("提示", "请选择起止日期！");
//				return;
//			}
//			if (startDate > finishDate) {
//				Ext.MessageBox.alert("提示", "开始日期不能大于结束日期！");
//				return;
//			}
//		}
//		if (groupTypeRadio.getValue().getRawValue() == "0")
//			del_vipuser_gridpanel.getColumnModel().setHidden(4, true);
//		if (groupTypeRadio.getValue().getRawValue() == "1")
//			del_vipuser_gridpanel.getColumnModel().setHidden(4, false);
//		groupType = groupTypeRadio.getValue().getRawValue();
		Ext.getBody().mask("正在获取数据...");
		Ext.Ajax.request({
			url : './advapp/vipconsman/vipconsmanager!findVipCons.action',
			success : function(response) {
				var result = Ext.decode(response.responseText);
//				delVipUserStore.proxy = new Ext.ux.data.PagingMemoryProxy(result.vipUserDtoList);
				delVipUserStore.load({
							params : {
								start : 0,
								limit : DEFAULT_PAGE_SIZE
							}
						});
				fn();
			},
			callback : function() {
				Ext.getBody().unmask();
			}
		});
	};
//	var groupManageQuery = new Ext.Panel({
//				region : 'north',
//				height : 30,
//				border : false,
//				layout : 'table',
//				layoutConfig : {
//					columns : 6
//				},
//				defaults : {
//					height : 30
//				},
//				items : [{
//							border : false,
//							width : 180,
//							layout : 'form',
//							labelWidth : 40,
//							labelAlign : 'right',
//							items : [text_groupName]
//						}, {
//							border : false,
//							width : 185,
//							layout : 'form',
//							labelWidth : 55,
//							labelAlign : 'right',
//							items : [groupTypeRadio]
//						}, {
//							layout : 'form',
//							border : false,
//							width : 30,
//							hideLabels : true,
//							bodyStyle : 'padding:0px 0px 0px 15px',
//							items : [check_selectDate]
//						}, {
//							layout : 'form',
//							border : false,
//							labelWidth : 50,
//							labelAlign : 'right',
//							width : 150,
//							items : [startDate3]
//						}, {
//							layout : 'form',
//							border : false,
//							labelAlign : 'right',
//							labelWidth : 15,
//							width : 115,
//							items : [finishDate3]
//						}, {
//							layout : 'form',
//							border : false,
//							width : 100,
//							// hideLabels:true,
//							bodyStyle : 'padding:0px 0px 0px 25px',
//							items : [queryGroupButton]
//						}]
//			});

	var groupManageQueryPanel = new Ext.Panel({
				border : false,
				layout : 'border',
				items : [del_vipuser_gridpanel]
			});
	// end
	var secondPanel = new Ext.Panel({
				border : false,
				region : 'center',
				name : 'bodyPanel',
				layout : 'card',
				activeItem : 0,
				bodyStyle : 'padding:10px 0px 0px 0px',
				items : [addVipUserGrid, del_vipuser_gridpanel]
			});

	var mainPanel = new Ext.Panel({
				layout : 'border',
				border : false,
				items : [opTypePanel, secondPanel]
			});
	renderModel(mainPanel, '重点用户管理');
});

