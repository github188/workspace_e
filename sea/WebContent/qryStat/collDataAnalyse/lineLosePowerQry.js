/**
 * 线路线损查询
 * 
 * @author jiangweichao on 2010-7-21
 */

// 页面执行函数
Ext.onReady(function() {
	var linelpq_nodeId;// 没var 一定是全局变量，在哪儿都可以直接用
	var linelpq_nodeType;// 有var 的，只在最近的一个外层{} 内才有效
	var orgType;

	function checkInitData() {
		if (null == linelpq_nodeId || "" == linelpq_nodeId) {
			Ext.MessageBox.alert("提示", "请从左边树选择节点！");
			return false;
		}
		var start = linelpq_startDate.getValue();
		var end = linelpq_endDate.getValue();
		var startFm = linelpq_startDate.getRawValue();
		var endFm = linelpq_endDate.getRawValue();
		if ((start - end) > 0) {
			Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
			return false;
		}
		if ((end - new Date().clearTime(true)) >= 0) {
			Ext.MessageBox.alert("提示", "结束时间不能大于今天！");
			return false;
		}
		if (daysBetween(endFm, startFm) > 365) {
			Ext.MessageBox.alert("提示", "选择日期不能超过1年！");
			return false;
		}
		return true;
	}

	// 求两个时间的天数差 日期格式为 YYYY-MM-dd
	function daysBetween(DateOne, DateTwo) {
		var OneMonth = DateOne.substring(5, DateOne.lastIndexOf('-'));
		var OneDay = DateOne.substring(DateOne.length, DateOne.lastIndexOf('-')
						+ 1);
		var OneYear = DateOne.substring(0, DateOne.indexOf('-'));
		var TwoMonth = DateTwo.substring(5, DateTwo.lastIndexOf('-'));
		var TwoDay = DateTwo.substring(DateTwo.length, DateTwo.lastIndexOf('-')
						+ 1);
		var TwoYear = DateTwo.substring(0, DateTwo.indexOf('-'));
		var cha = ((Date.parse(OneMonth + '/' + OneDay + '/' + OneYear) - Date
				.parse(TwoMonth + '/' + TwoDay + '/' + TwoYear)) / 86400000);
		return Math.abs(cha);
	}

	// load第二次报错问题
	Ext.override(Ext.data.Store, {
				loadRecords : function(o, options, success) {
					if (!o || success === false) {
						if (success !== false) {
							this.fireEvent("load", this, [], options);
						}
						if (options.callback) {
							options.callback.call(options.scope || this, [],
									options, false);
						}
						return;
					}
					var r = o.records, t = o.totalRecords || r.length;
					if (!options || options.add !== true) {
						if (this.pruneModifiedRecords) {
							this.modified = [];
						}
						for (var i = 0, len = r.length; i < len; i++) {
							r[i].join(this);
						}
						if (this.snapshot) {
							this.data = this.snapshot;
							delete this.snapshot;
						}
						this.data.clear();
						this.data.addAll(r);
						this.totalLength = t;
						this.applySort();
						this.fireEvent("datachanged", this);
					} else {
						var add = [];
						for (var i = 0, len = r.length; i < len; i++) {
							var record = r[i];
							if (this.getById(record.id)) {
								this.data.replace(record.id, record);
								this.fireEvent("update", this, record,
										Ext.data.Record.EDIT);
							} else {
								add.push(record);
							}
						}
						this.totalLength = Math.max(t, this.data.length
										+ add.length);
						this.add(add);
					}
					this.fireEvent("load", this, r, options);
					if (options.callback) {
						options.callback.call(options.scope || this, r,
								options, true);
					}
				}
			});

	// 用电损耗分类统计---------------------------------------------------
	// 定义电损的上部panel
	// 节点名
	var linelpq_nodeName = new Ext.form.TextField({
				fieldLabel : "节点名<font color='red'>*</font>",
				name : 'linelpq_nodeName',
				labelSeparator : '',
				emptyText : '请选择查询节点...',
				readOnly : true,
				labelStyle : "text-align:right;width:50;",
				width : 140,
				tooltip : {
					title : "查询节点",
					text : ''
				},
				setValue : function(v) {
					this.value = v;
					if (this.rendered) {
						this.el.dom.value = (Ext.isEmpty(v) ? '' : v);
						this.validate();
					}
					new Ext.ToolTip({
								target : this.id,
								trackMouse : false,
								draggable : true,
								maxWidth : 200,
								minWidth : 100,
								title : this.tooltip.title,
								html : this.value,
								text : this.value
							});
					return this;
				}
			});

	// 开始日期
	var linelpq_startDate = new Ext.form.DateField({
				fieldLabel : '从',
				name : 'startDate',
				width : 90,
				format : 'Y-m-d',
				allowBlank : false,
				editable : false,
				labelSeparator : '',
				value : new Date().add(Date.DAY, -1)
			});

	// 结束日期
	var linelpq_endDate = new Ext.form.DateField({
				fieldLabel : '到',
				name : 'endDate',
				width : 90,
				format : 'Y-m-d',
				labelSeparator : '',
				editable : false,
				allowBlank : false,
				value : new Date().add(Date.DAY, -1)
			});

	// 查询按钮
	var linelpq_queryBtn = new Ext.Button({
				text : '查询',
				name : 'query',
				width : 80,
				labelSeparator : '',
				handler : function() {
					if (!checkInitData()) {
						return;
					}
					linelpqListStore.removeAll();
					linelpqListStore.baseParams = {
						nodeId : linelpq_nodeId,
						nodeType : linelpq_nodeType,
						orgType : orgType,
						startDate : linelpq_startDate.getValue(),
						endDate : linelpq_endDate.getValue()
					};
					linelpqListStore.load();
				}
			});
	
	var showChartBtn = new Ext.Button({
				text : "显示图形",
				width : 80,
				handler : function() {
				}
			});
	
	var linelpqTopPanel = new Ext.Panel({
				region : 'north',
				height : 30,
				border : false,
				layout : 'table',
				layoutConfig : {
					columns : 5
				},
				items : [{
							width : 200,
							layout : 'form',
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							labelAlign : "right",
							labelWidth : 50,
							items : [linelpq_nodeName]
						}, {
							width : 150,
							layout : 'form',
							labelWidth : 20,
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							items : [linelpq_startDate]
						}, {
							width : 150,
							layout : 'form',
							labelWidth : 20,
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							items : [linelpq_endDate]
						}, {
							width : 150,
							border : false,
							layout : "form",
							bodyStyle : 'padding:0px 0px 0px 0px',
							items : [linelpq_queryBtn]
						}, {
							width : 150,
							border : false,
							layout : "form",
							bodyStyle : 'padding:0px 0px 0px 0px',
							items : [showChartBtn]
						}]
			});
			
	var linelpqSm = new Ext.grid.CheckboxSelectionModel({});
	// -----------------------------定义grid列表-------------------------start
	// 为了便于页面操作，这里抽取出来，不放入fuction中 ,json支持[{"":,"":},{}] 还支持格式 [[,,,,],[]];
	var linelpqCm = new Ext.grid.ColumnModel([linelpqSm, {
		header : '供电单位',
		sortable : true,
		dataIndex : 'orgName',
		width : 140,
		align : 'center',
		renderer : function(val) {
			if (Ext.isEmpty(val)) {
				val = "";
			}
			var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val
					+ '">' + val + '</div>';
			return html;
		}
	}, {
		header : '线路名称',
		sortable : true,
		dataIndex : 'lineName',
		align : 'center',
		renderer : function(val) {
			if (Ext.isEmpty(val)) {
				val = "";
			}
			var html = '<div align = "left" ext:qtitle="台区名称" ext:qtip="' + val
					+ '">' + val + '</div>';
			return html;
		}
	}, {
		header : '统计日期',
		sortable : true,
		dataIndex : 'date',
		align : 'center'
	}, {
		header : '供电量(kWh)',
		sortable : true,
		dataIndex : 'supplypq',
		align : 'center',
		width : 120,
		renderer : function(s, m, rec) {
			if (Ext.isEmpty(rec.get('supplypq')) || 'null' == rec.get('supplypq')) {
				var html = '<div align = "right">' + "" + '</div>';
				return html;
			}
			return "<a href='javascript:' onclick='openReadWindow(\""
					+ rec.get('lineId') + "\",\"" + rec.get('startDate')
					+ "\",\"" + rec.get('endDate') + "\",\"" + rec.get('orgNo') + "\");'>" + rec.get('supplypq')
					+ "</a>";
		}
	}, {
		header : '售电量(kWh)',
		sortable : true,
		dataIndex : 'spq',
		width : 120,
		align : 'center',
		renderer : function(s, m, rec) {
			if (Ext.isEmpty(rec.get('spq')) || 'null' == rec.get('spq')) {
				var html = '<div align = "right">' + "" + '</div>';
				return html;
			}
			return "<a href='javascript:' onclick='openReadWindow(\""
					+ rec.get('lineId') + "\",\"" + rec.get('startDate')
					+ "\",\"" + rec.get('endDate') + "\",\"" + rec.get('orgNo') + "\");'>" + rec.get('spq')
					+ "</a>";
		}
	}, {
		header : '损失电量',
		sortable : true,
		dataIndex : 'lpq',
		align : 'center',
		renderer : function(val) {
			if (Ext.isEmpty(val)) {
				val = "";
			}
			var html = '<div align = "right">' + val + '</div>';
			return html;
		}
	}, {
		header : '线损率(%)',
		sortable : true,
		dataIndex : 'llr',
		align : 'center',
		renderer : function(s, m, rec) {
			var html = '<div align = "right">';
			if (Ext.isEmpty(rec.get('llr'))) {
				html = html + "";
			} else {
				if (!Ext.isEmpty(rec.get('idx'))) {
					if (rec.get('llr') - rec.get('idx') > 0) {
						html = html + '<span style="color:red;">'
								+ rec.get('llr') + '</span>';
					} else {
						html = html + '<span style="color:green;">'
								+ rec.get('llr') + '</span>';
					}
				} else {
					html = html + rec.get('llr');
				}
			}
			html = html + '</div>';
			return html;
		}
	}, {
		header : '线损指标(%)',
		sortable : true,
		dataIndex : 'idx',
		align : 'center',
		renderer : function(val) {
			if (Ext.isEmpty(val)) {
				val = "";
			}
			var html = '<div align = "right">' + val + '</div>';
			return html;
		}
	}, {
		header : '差异值',
		sortable : true,
		dataIndex : 'diff',
		align : 'center',
		renderer : function(val) {
			if (Ext.isEmpty(val)) {
				val = "";
			}
			var html = '<div align = "right">' + val + '</div>';
			return html;
		}
	}, {
		header : '抄表成功率(%)',
		sortable : true,
		dataIndex : 'readSuccRate',
		align : 'center'
	}]);

	// 定义Grid的store
	var linelpqListStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './qrystat/lineLosePowerQry!loadGridData.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'linelpqList',
							totalProperty : 'totalCount'
						}, [{
									name : 'orgName'
								}, {
									name : 'lineNo'
								}, {
									name : 'lineName'
								}, {
									name : 'lineId'
								}, {
									name : 'supplypq'
								}, {
									name : 'spq'
								}, {
									name : 'lpq'
								}, {
									name : 'llr'
								}, {
									name : 'idx'
								}, {
									name : 'diff'
								}, {
									name : 'date'
								}, {
									name : 'startDate'
								}, {
									name : 'endDate'
								}, {
									name : 'readSuccRate'
								}, {
									name : 'orgNo'
								}])
			});

	// 返回用户列表
	var linelpqListGrid = new Ext.grid.GridPanel({
				store : linelpqListStore,
				sm : linelpqSm,
				cm : linelpqCm,
				stripeRows : true,
				autoScroll : true,
				border : false,
				bbar : new Ext.ux.MyToolbar({
							enableExpAll : true,
							store : linelpqListStore
						}),
				tbar : [{
							xtype : 'label',
							html : "<font font-weight:bold;>线损明细</font>"
						}]
			});

	// 定义用户列表
	var linelpqListGridPnl = new Ext.Panel({
				region : 'center',
				layout : 'fit',
				border : false,
				items : [linelpqListGrid]
	});
	// -----------------------------定义grid列表-------------------------end

	// 定义整个页面面板
	var viewPanel = new Ext.form.FormPanel({
				layout : 'border',
				border : false,
				items : [linelpqTopPanel, linelpqListGridPnl]
	});

	renderModel(viewPanel, '线路线损查询');

	// 监听左边树点击事件
	var treeListener = new LeftTreeListener({
				modelName : '线路线损查询',
				processClick : function(p, node, e) {
					var obj = node.attributes.attributes;
					var type = node.attributes.type;
					if (!node.isLeaf()) {
						if (type == 'org') {
							if ("02" == obj.orgType) {
								Ext.MessageBox.alert("提示", "不支持省级单位查询！");
								return false;
							}
							linelpq_nodeId = obj.orgNo;
							linelpq_nodeType = type;
							orgType = obj.orgType;
							linelpq_nodeName.setValue(node.text);
						} else if (type == 'line') {
							linelpq_nodeId = obj.lineId;
							linelpq_nodeType = type;
							linelpq_nodeName.setValue(node.text);
						} else if (type == 'sub') {
							linelpq_nodeId = obj.subId;
							linelpq_nodeType = type;
							linelpq_nodeName.setValue(node.text);
						} else {
							return true;
						}
					} else {
						return true;
					}
				}
			});
	
			
	//激活下调用
	Ext.getCmp('线路线损查询').on('activate', function() {
 		if((typeof(lineGosePowerStat_Flag) != 'undefined') && lineGosePowerStat_Flag){
 			lineGosePowerStat_Flag = false;
			if((typeof(lineGosePowerStat_lineId) != 'undefined') && !Ext.isEmpty(lineGosePowerStat_lineId)
					&& 'null' != lineGosePowerStat_lineId){
				linelpq_nodeName.setValue(lineGosePowerStat_lineName);
				linelpq_nodeId = lineGosePowerStat_lineId;
				linelpq_nodeType = 'line';
				linelpq_startDate.format = 'Y-m-d';
				linelpq_endDate.format = 'Y-m-d';
				linelpq_startDate.setValue(lineGosePowerStat_startD);
				linelpq_endDate.setValue(lineGosePowerStat_endD);
				linelpqListStore.removeAll();
				linelpqListStore.baseParams = {
					nodeId : linelpq_nodeId,
					nodeType : linelpq_nodeType,
					orgType : orgType,
					startDate : linelpq_startDate.getValue(),
					endDate : linelpq_endDate.getValue()
				};
				linelpqListStore.load();
			}
		}
	});
	
	//非激活下调用
	if((typeof(lineGosePowerStat_Flag) != 'undefined') && lineGosePowerStat_Flag){
		lineGosePowerStat_Flag = false;
		if((typeof(lineGosePowerStat_lineId) != 'undefined') && !Ext.isEmpty(lineGosePowerStat_lineId)
				&& 'null' != lineGosePowerStat_lineId){
			linelpq_nodeName.setValue(lineGosePowerStat_lineName);
			linelpq_nodeId = lineGosePowerStat_lineId;
			linelpq_nodeType = 'line';
			linelpq_startDate.format = 'Y-m-d';
			linelpq_endDate.format = 'Y-m-d';
			linelpq_startDate.setValue(lineGosePowerStat_startD);
			linelpq_endDate.setValue(lineGosePowerStat_endD);
			linelpqListStore.removeAll();
			linelpqListStore.baseParams = {
				nodeId : linelpq_nodeId,
				nodeType : linelpq_nodeType,
				orgType : orgType,
				startDate : linelpq_startDate.getValue(),
				endDate : linelpq_endDate.getValue()
			};
			linelpqListStore.load();
		}
	}
});

// 切换到tab-----------------------------------------------------------------------------------

function openReadWindow(lineId, startDate, endDate, orgNo) {
	var ioValue = "*";
	var flag = false;
	// 远程抄表
	var remoteRead = new Ext.form.RadioGroup({
				fieldLabel : '远程抄表',
				labelSeparator : '',
				width : 100,
				items : [{
							boxLabel : '成功',
							inputValue : '1',
							name : 'remoteRead',
							listeners : {
								'check' : function(r, c) {
									if (c) {
										readStore.removeAll();
									}
								}
							}
						}, {
							boxLabel : '失败',
							inputValue : '2',
							name : 'remoteRead',
							checked : true,
							listeners : {
								'check' : function(r, c) {
									if (c) {
										readStore.removeAll();
									}
								}
							}
						}]
			});

	// 计量点分类
	var mpType = new Ext.form.CheckboxGroup({
				fieldLabel : '计量点分类',
				labelSeparator : '',
				width : 100,
				items : [{
							boxLabel : '入口',
							inputValue : '1',
							name : '1',
							checked : true
						}, {
							boxLabel : '出口',
							inputValue : '0',
							name : '0',
							checked : true
						}]
			});

	// 查询按钮
	var qryBtn = new Ext.Button({
				text : '查询',
				width : 60,
				handler : function() {
					var rg = remoteRead.getValue().getRawValue();
					if (1 == rg) {
						readStore.baseParams['flag'] = true;
						flag = true;
					} else {
						readStore.baseParams['flag'] = false;
						flag = false;
					}
					var mpTypeArray = mpType.getValue();
					if (0 == mpTypeArray.length && 0 == mpTypeArray.length) {
						Ext.MessageBox.alert("提示", '请选择计量点类型');
						return;
					}
					if (2 == mpTypeArray.length) {
						readStore.baseParams['ioValue'] = "*";
						ioValue = "*";
					} else {
						readStore.baseParams['ioValue'] = mpTypeArray[0]
								.getRawValue();
						ioValue = mpTypeArray[0].getRawValue();
					}
					readStore.load();
				}
			});

	// 退出按钮
	var exitBtn = new Ext.Button({
				text : '退出',
				width : 60,
				handler : function() {
					readWindow.close();
				}
			});

	// 数据项面板
	var readPanel = new Ext.Panel({
				layout : "column",
				region : 'north',
				height : 35,
				plain : true,
				items : [{
							columnWidth : .35,
							labelAlign : "right",
							labelWidth : 60,
							labelSeparator : "",
							bodyStyle : 'padding:5px 0px 0px 0px',
							layout : "form",
							border : false,
							items : [remoteRead]
						}, {
							columnWidth : .35,
							labelAlign : "right",
							labelWidth : 70,
							labelSeparator : "",
							bodyStyle : 'padding:5px 0px 0px 0px',
							layout : "form",
							border : false,
							items : [mpType]
						}, {
							columnWidth : .15,
							labelSeparator : "",
							bodyStyle : 'padding:5px 0px 0px 0px',
							layout : "form",
							border : false,
							items : [qryBtn]
						}, {
							columnWidth : .15,
							labelSeparator : "",
							bodyStyle : 'padding:5px 0px 0px 0px',
							layout : "form",
							border : false,
							items : [exitBtn]
						}]
			});

	// 定义check框
	var readSm = new Ext.grid.CheckboxSelectionModel();

	var readCm = new Ext.grid.ColumnModel([readSm, {
				header : "用户编号",
				dataIndex : 'consNo',
				align : "center"
			}, {
				header : "用户名称",
				dataIndex : 'consName',
				align : 'center'
			}, {
				header : "计量点编号",
				dataIndex : 'mpNo',
				align : 'center'
			}, {
				header : "计量点名称",
				dataIndex : 'mpName',
				align : 'center'
			}, {
				header : "数据时间",
				dataIndex : 'date',
				align : 'center'
			}, {
				header : "数据值",
				dataIndex : 'value',
				align : 'center'
			}]);

	var readStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './qrystat/lineLosePowerQry!loadReadData.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'linelpqReadList',
							totalProperty : 'totalCount'

						}, [{
									name : 'consNo'
								}, {
									name : 'consName'
								}, {
									name : 'mpNo'
								}, {
									name : 'mpName'
								}, {
									name : 'date'
								}, {
									name : 'value'
								}, {
									name : 'assetNo'
								}, {
									name : 'tmnlAssetNo'
								}])
			});

	// grid解锁
	function unlockGrid() {
		readSm.unlock();
		readGrid.onEnable();
		readGrid.getBottomToolbar().enable();
	}

	// grid上锁
	function lockGrid() {
		readSm.lock();
		readGrid.onDisable();
		readGrid.getBottomToolbar().disable();
	}

	// 定义全选checkBox
	var readAllcb = new Ext.form.Checkbox({
				boxLabel : '全选',
				name : 'readAllcb',
				checked : false,
				listeners : {
					'check' : function(r, c) {
						if (c) {
							readSm.selectAll();
							lockGrid();
						} else {
							unlockGrid();
							readSm.clearSelections();
						}
					}
				}
			});

	// 定义补召checkBox
	var recallCb = new Ext.form.Checkbox({
				boxLabel : '补召',
				name : 'recallCb',
				checked : false
			});

	// 定义重新计算checkBox
	var recalcCb = new Ext.form.Checkbox({
				boxLabel : '重新计算',
				name : 'recalcCb',
				checked : false
			});

	var readGrid = new Ext.grid.GridPanel({
		autoScroll : true,
		region : 'center',
		stripeRows : true,
		sm : readSm,
		viewConfig : {
			forceFit : false
		},
		anchor : '100%',
		colModel : readCm,
		ds : readStore,
		tbar : [{
					xtype : 'label',
					html : "<font font-weight:bold;>" + "明细列表" + "</font>"
				}, {
					xtype : 'tbfill'
				}, readAllcb, '-', recallCb, '-', recalcCb, {
					text : '执行',
					iconCls : '',
					handler : function() {
						var rg = remoteRead.getValue().getRawValue();
						if (1 == rg) {
							Ext.MessageBox.alert("提示", "成功用户不能执行补召和重新计算操作！");
							return;
						}
						if (!recallCb.checked && !recalcCb.checked) {
							Ext.MessageBox.alert("提示", "请选择操作类型！");
							return;
						}
						if (0 == readStore.getCount()) {
							Ext.MessageBox.alert("提示", "抄表失败用户为空，请重新查询！");
							return;
						}
						var checkAll;
						if (readAllcb.checked) {
							checkAll = true;
						} else {
							checkAll = false;
							var recs = readSm.getSelections();
							if (null == recs || 0 == recs.length) {
								Ext.MessageBox.alert("提示", "请选择用户！");
								return;
							}
							var tmnlAssetNoList = new Array();
							for (var i = 0; i < recs.length; i++) {
								if (null != recs[i].get('tmnlAssetNo')
										&& recs[i].get('tmnlAssetNo').length >= 0) {
									tmnlAssetNoList[i] = recs[i]
											.get('tmnlAssetNo');
								}
							}
						}
						Ext.MessageBox.alert("提示", "已经启动执行流程，请稍后查看结果！");
						//延时任务
						var delayedTask = new Ext.util.DelayedTask();
						Ext.Ajax.request({
									url : './qrystat/lineLosePowerQry!exec.action',
									params : {
										checkAll : checkAll,
										checkRecall : recallCb.checked,
										lineId : lineId,
										//暂定重新计算时全统计
										orgNo : orgNo.substring(0,5),
										ioValue : ioValue,
										flag : flag,
										startDate : startDate,
										endDate : endDate,
										tmnlAssetNoList : tmnlAssetNoList
									},
									success : function(response) {
										return;
									},
									failure : function() {
										Ext.MessageBox.alert("提示", "执行失败");
										return;
									}
								});
						//延时7分钟后执行计算服务
						delayedTask.delay(7*60*1000, function () {
							Ext.Ajax.request({
								url : './qrystat/lineLosePowerQry!recalcData.action',
								params : {
									//暂定重新计算时全统计
									orgNo : orgNo.substring(0,5),
									startDate : startDate,
									endDate : endDate,
									checkRecalc : recalcCb.checked
								},
								success : function(response) {
									return;
								},
								failure : function() {
									Ext.MessageBox.alert("提示", "线损计算");
									return;
								}
							});
						});
					}
				}],
		bbar : new Ext.ux.MyToolbar({
					store : readStore
				})
	});

	var readWindow = new Ext.Window({
				width : 550,
				height : 345,
				layout : "border",
				modal : true,
				closeAction : "close",
				plain : false,// 设置背景颜色
				resizable : false,
				draggable : false,// 不可移动
				buttonAlign : "center",// 按钮的位置
				title : '【抄表统计】',
				items : [readPanel, readGrid]
			});
	readStore.baseParams['flag'] = false;
	readStore.baseParams['ioValue'] = "*";
	readStore.baseParams['lineId'] = lineId;
	readStore.baseParams['startDate'] = startDate;
	readStore.baseParams['endDate'] = endDate;
	readStore.load();

	readWindow.show();
}