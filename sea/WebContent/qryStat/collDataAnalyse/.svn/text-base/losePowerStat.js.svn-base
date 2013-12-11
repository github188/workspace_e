/**
 * 台区线损查询
 * 
 * @author modified by jiangweichao on 2010-3-2 for bug1
 */

// 页面执行函数
Ext.onReady(function() {
	var lps_nodeId;// 没var 一定是全局变量，在哪儿都可以直接用
	var lps_nodeType;// 有var 的，只在最近的一个外层{} 内才有效
	var orgType;

	// 通过itemId取第一个个值
	function itemId(panel, id) {
		return panel.find("itemId", id)[0];
	}

	// 用于图形的显示，对json对象进行处理，使其生成xml
	/**
	 * @param arr
	 *            json 对象数组
	 * @param cata
	 *            x轴的名称
	 * @param set
	 *            set表示的值的名称
	 */
	function createChartXml(arr, cata, standard, current, title) {
		var rs = [
				'<chart caption="'
						+ title
						+ '"  lineThickness="1" showValues="0"  anchorRadius="2" '
						+ 'divLineAlpha="20" numberScaleValue="0.01" numberScaleUnit="%" divLineColor="CC3300" divLineIsDashed="1" showAlternateHGridColor="1" alternateHGridAlpha="5" '
						+ 'alternateHGridColor="CC3300" decimals="2" shadowAlpha="40" labelStep="1"   numvdivlines="5" '
						+ 'chartRightMargin="35" bgColor="FFFFFF,CC3300" bgAngle="270" bgAlpha="10,10">',
				'<categories>'];
		var cgs = [];
		// 参考指标的sets
		var setsStandard = [];
		// 当前的线损率的sets
		var setsCurrent = [];
		Ext.each(arr, function(a) {
					cgs.push('<category label="' + a[cata] + '" />');
					setsStandard.push('<set value="' + a[standard] + '" />');
					setsCurrent.push('<set value="' + a[current] + '" />');
				});
		// 对生成的数据进行组装
		Array.prototype.push.apply(rs, cgs);
		rs.push("</categories>");
		rs
				.push('<dataset seriesName="参考值" color="1D8BD1" anchorBorderColor="1D8BD1" anchorBgColor="1D8BD1">');
		Array.prototype.push.apply(rs, setsStandard);
		rs.push("</dataset>");
		rs
				.push('<dataset seriesName="线损率" color="DBDC25" anchorBorderColor="DBDC25" anchorBgColor="DBDC25">');
		Array.prototype.push.apply(rs, setsCurrent);
		rs.push("</dataset>");
		rs.push("</chart>");
		return rs.join("");
	}

	// 一个得到得到显示台区线损情况的grid的方法
	function getCenterGrid() {
		return Ext.getCmp(viewPanel.getItemId("centerGrid")).find("itemId",
				"grid")[0];
	}
	// 弹出一个显示显示图形的window
	lps_showChartWin = function() {
		// 中间的grid
		var centerGrid = getCenterGrid();

		// 一个台区的combox
		// 初始化数据
		var initData = [];
		var ss = centerGrid.getSelectionModel().getSelections();
		if (ss.length == 0) {
			return !!Ext.Msg.alert("提示", "请选择你要查看的台区");
		}
		var map = {};
		Ext.each(ss, function(s) {
					var rs = [];
					var tgId = s.get('tgId');
					if (!map[tgId]) {
						map[tgId] = true;
						rs.push(tgId);
						rs.push(s.get('tgName'));
						initData.push(rs);
					}

				});
		var combox = new Ext.form.ComboBox({
					width : 80,
					mode : "local",
					hiddenName : "tgId",
					triggerAction : "all",
					typeAhead : true,
					itemId : "tgId",
					allowBlank : false,
					fieldLabel : "台区",
					selectOnFocus : true,
					emptyText : "请选择台区",
					displayField : "name",
					value : initData[0][0],
					valueField : "value",
					store : new Ext.data.ArrayStore({
								fields : ["value", "name"],
								data : initData
							})
				});

		// 上部的panel
		var topPanel = new Ext.Panel({
			border : false,
			region : "north",
			height : 120,
			padding : "10px 0 0 5px",
			layout : "table",
			items : [{
						border : false,
						width : 150,
						labelWidth : 60,
						layout : "form",
						labelAlign : "right",
						items : [combox]
					}, {
						border : false,
						labelWidth : 20,
						width : 150,
						layout : "form",
						labelAlign : "right",
						items : [{
									xtype : "datefield",
									format : 'Y-m-d',
									emptyText : '请选择日期 ...',
									fieldLabel : "从",
									width : 100,
									itemId : "from",
									value : new Date().add(Date.DAY, -5)
								}]
					}, {
						border : false,
						labelWidth : 20,
						width : 150,
						layout : "form",
						labelAlign : "right",
						items : [{
									xtype : "datefield",
									format : 'Y-m-d',
									emptyText : '请选择日期 ...',
									fieldLabel : "到",
									width : 100,
									itemId : "to",
									value : new Date()
								}]
					}, {
						border : false,
						width : 150,
						items : [{
							xtype : "button",
							text : "显示",
							itemId : "show",
							width : 50,
							handler : function() {
								if (!combox.isValid()) {
									return !!Ext.Msg.alert("提示", "请选择台区");
								}
								var params = {
									intTgId : combox.getValue(),
									startDate : itemId(topPanel, "from")
											.getValue(),
									endDate : itemId(topPanel, "to").getValue()
								};
								// 对数据进行处理
								Ext.Ajax.request({
									url : './qrystat/losePowerStat!findChartData.action',
									params : params,
									success : function(response) {
										var o = Ext
												.decode(response.responseText);
										var title = "";
										combox.getStore().findBy(function(r) {
											if (r.get("value") == combox
													.getValue()) {
												title = r.get("name");
											}
										});
										var resultList = o.resultList;
										var xmlData = createChartXml(
												resultList, "statDate",
												"llIdxValue", "llPer",title);
										var myChart = new FusionCharts(
												"./fusionCharts/MSLine.swf",
												"lose_ll_chart", panel
														.getWidth(), panel
														.getHeight());
										myChart.setDataXML(xmlData);
										myChart.setTransparent(true);
										myChart.render(chartPanel.getId());
									}
								});
							}
						}]
					}]
		});

		// 显示图形的panel
		var chartPanel = new Ext.Panel({
					border : false,
					item : []
				});
		// 中间panel
		var panel = new Ext.Panel({
					border : false,
					region : "center",
					layout : "fit",
					items : [chartPanel]
				});
		var win = new Ext.Window({
					title : "图形显示",
					border : false,
					width : 650,
					modal : true,
					height : 450,
					padding : "10px 0 0 5px",
					layout : "border",
					items : [topPanel, panel],
					buttons : [{
								text : "退出",
								handler : function() {
									win.close();
								}
							}]
				});
		win.show();
		itemId(topPanel, "show").handler();
	};

	function checkInitData() {
		if (null == lps_nodeId || "" == lps_nodeId) {
			Ext.MessageBox.alert("提示", "请从左边树选择节点！");
			return false;
		}
		var start = lps_startDate.getValue();
		var end = lps_endDate.getValue();
		var startFm = lps_startDate.getRawValue();
		var endFm = lps_endDate.getRawValue();
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
	var lps_nodeName = new Ext.form.TextField({
				fieldLabel : "节点名<font color='red'>*</font>",
				name : 'lps_nodeName',
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
	var lps_startDate = new Ext.form.DateField({
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
	var lps_endDate = new Ext.form.DateField({
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
	var lps_queryBtn = new Ext.Button({
				text : '查询',
				name : 'query',
				width : 80,
				labelSeparator : '',
				handler : function() {
					if (!checkInitData()) {
						return;
					}
					lpsListStore.removeAll();
					lpsListStore.baseParams = {
						nodeId : lps_nodeId,
						nodeType : lps_nodeType,
						orgType : orgType,
						startDate : lps_startDate.getValue(),
						endDate : lps_endDate.getValue()
					};
					lpsListStore.load();
				}
			});
	var showChartBtn = new Ext.Button({
				text : "显示图形",
				width : 80,
				handler : function() {
					lps_showChartWin();
				}
			});
	var losePowerStatTopPanel = new Ext.Panel({
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
							items : [lps_nodeName]
						}, {
							width : 150,
							layout : 'form',
							labelWidth : 20,
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							items : [lps_startDate]
						}, {
							width : 150,
							layout : 'form',
							labelWidth : 20,
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							items : [lps_endDate]
						}, {
							width : 150,
							border : false,
							layout : "form",
							bodyStyle : 'padding:0px 0px 0px 0px',
							items : [lps_queryBtn]
						}, {
							width : 150,
							border : false,
							layout : "form",
							bodyStyle : 'padding:0px 0px 0px 0px',
							items : [showChartBtn]
						}]
			});
			
	var lpsSm = new Ext.grid.CheckboxSelectionModel({});
	// -----------------------------定义grid列表-------------------------start
	// 为了便于页面操作，这里抽取出来，不放入fuction中 ,json支持[{"":,"":},{}] 还支持格式 [[,,,,],[]];
	var lpsCm = new Ext.grid.ColumnModel([lpsSm, {
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
		header : '台区名称',
		sortable : true,
		dataIndex : 'tgName',
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
		dataIndex : 'ppq',
		align : 'center',
		width : 120,
		renderer : function(s, m, rec) {
			if (Ext.isEmpty(rec.get('ppq')) || 'null' == rec.get('ppq')) {
				var html = '<div align = "right">' + "" + '</div>';
				return html;
			}
			return "<a href='javascript:' onclick='openReadWindow(\""
					+ rec.get('tgId') + "\",\"" + rec.get('startDate')
					+ "\",\"" + rec.get('endDate') + "\",\"" + rec.get('orgNo')
					+ "\");'>" + rec.get('ppq')
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
					+ rec.get('tgId') + "\",\"" + rec.get('startDate')
					+ "\",\"" + rec.get('endDate') + "\",\"" + rec.get('orgNo')
					+ "\");'>" + rec.get('spq')
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
	var lpsListStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './qrystat/losePowerStat!loadGridData.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'lpsList',
							totalProperty : 'totalCount'
						}, [{
									name : 'orgName'
								},{
									name : 'orgNo'
								}, {
									name : 'tgNo'
								}, {
									name : 'tgName'
								}, {
									name : 'tgId'
								}, {
									name : 'ppq',
									type: 'float'
								}, {
									name : 'spq',
									type: 'float'
								}, {
									name : 'lpq',
									type: 'float'
								}, {
									name : 'llr',
									type: 'float'
								}, {
									name : 'idx',
									type: 'float'
								}, {
									name : 'diff',
									type: 'float'
								}, {
									name : 'date'
								}, {
									name : 'startDate'
								}, {
									name : 'endDate'
								}, {
									name : 'readSuccRate',
									type: 'float'
								}])
			});

	// 返回用户列表
	function getLPSListGridPnl() {

		var lpsListGrid = new Ext.grid.GridPanel({
					store : lpsListStore,
					itemId : "grid",
					sm : lpsSm,
					cm : lpsCm,
					stripeRows : true,
					autoScroll : true,
					border : false,
					bbar : new Ext.ux.MyToolbar({
								enableExpAll : true,
								store : lpsListStore
							}),
					tbar : [{
								xtype : 'label',
								html : "<font font-weight:bold;>线损明细</font>"
							}]
				});

		// 定义用户列表
		var lpsListGridPnl = new Ext.Panel({
					region : 'center',
					layout : 'fit',
					border : false,
					itemId : "centerGrid",
					items : [lpsListGrid]
				});

		return lpsListGridPnl;
	}
	// -----------------------------定义grid列表-------------------------end

	// 定义整个页面面板
	var viewPanel = new Ext.form.FormPanel({
				layout : 'border',
				border : false,
				items : [losePowerStatTopPanel, getLPSListGridPnl()]
			});

	renderModel(viewPanel, '台区线损查询');

	// 监听左边树点击事件
	var treeListener = new LeftTreeListener({
				modelName : '台区线损查询',
				processClick : function(p, node, e) {
					var obj = node.attributes.attributes;
					var type = node.attributes.type;
					if (!node.isLeaf()) {
						if (type == 'org') {
							if ("02" == obj.orgType) {
								Ext.MessageBox.alert("提示", "不支持省级单位查询！");
								return false;
							}
							lps_nodeId = obj.orgNo;
							lps_nodeType = type;
							orgType = obj.orgType;
							lps_nodeName.setValue(node.text);
						} else if (type == 'line') {
							lps_nodeId = obj.lineId;
							lps_nodeType = type;
							lps_nodeName.setValue(node.text);
						} else if (type == 'ugp' || type == 'cgp') {
							lps_nodeId = obj.groupNo;
							lps_nodeType = type;
							lps_nodeName.setValue(node.text);
						} else {
							return true;
						}
					} else {
						return true;
					}
				},
			    processUserGridSelect : function(cm, row, record) {
				    if(!Ext.isEmpty(record.get('consType')) && '2' == record.get('consType')){//台区用户
				    	lps_nodeId = record.get('consId');
						lps_nodeType = 'tg';
						lps_nodeName.setValue(record.get('consName'));
				    }
			    }
			});
	
			
	//激活下调用
	Ext.getCmp('台区线损查询').on('activate', function() {
 		if((typeof(tgGosePowerStat_Flag) != 'undefined') && tgGosePowerStat_Flag){
			tgGosePowerStat_Flag = false;
			if((typeof(tgGosePowerStat_tgId) != 'undefined') && !Ext.isEmpty(tgGosePowerStat_tgId)
					&& 'null' != tgGosePowerStat_tgId){
				lps_nodeName.setValue(tgGosePowerStat_tgName);
				lps_nodeId = tgGosePowerStat_tgId;
				lps_nodeType = 'tg';
				lps_startDate.format = 'Y-m-d';
				lps_endDate.format = 'Y-m-d';
				lps_startDate.setValue(tgGosePowerStat_startD);
				lps_endDate.setValue(tgGosePowerStat_endD);
				lpsListStore.removeAll();
				lpsListStore.baseParams = {
					nodeId : lps_nodeId,
					nodeType : lps_nodeType,
					orgType : orgType,
					startDate : lps_startDate.getValue(),
					endDate : lps_endDate.getValue()
				};
				lpsListStore.load();
			}
		}
	});
	
	//非激活下调用
	if((typeof(tgGosePowerStat_Flag) != 'undefined') && tgGosePowerStat_Flag){
		tgGosePowerStat_Flag = false;
		if((typeof(tgGosePowerStat_tgId) != 'undefined') && !Ext.isEmpty(tgGosePowerStat_tgId)
				&& 'null' != tgGosePowerStat_tgId){
			lps_nodeName.setValue(tgGosePowerStat_tgName);
			lps_nodeId = tgGosePowerStat_tgId;
			lps_nodeType = 'tg';
			lps_startDate.format = 'Y-m-d';
			lps_endDate.format = 'Y-m-d';
			lps_startDate.setValue(tgGosePowerStat_startD);
			lps_endDate.setValue(tgGosePowerStat_endD);
			lpsListStore.removeAll();
			lpsListStore.baseParams = {
				nodeId : lps_nodeId,
				nodeType : lps_nodeType,
				orgType : orgType,
				startDate : lps_startDate.getValue(),
				endDate : lps_endDate.getValue()
			};
			lpsListStore.load();
		}
	}
});

// 切换到tab-----------------------------------------------------------------------------------

function openReadWindow(tgId, startDate, endDate, orgNo) {
	var ioValue = "*";
	var flag = false;
	// 远程抄表
	var remoteRead = new Ext.form.CheckboxGroup({
		fieldLabel : '远程抄表',
		labelSeparator : '',
		width : 140,
		items : [{
					boxLabel : '成功',
					inputValue : '1',
					name : '1',
					checked : true
				}, {
					boxLabel : '失败',
					inputValue : '0',
					name : '0',
					checked : true
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
					var rg = remoteRead.getValue();
					if (0 == rg.length && 0 == rg.length) {
						Ext.MessageBox.alert("提示", '请选择远程抄表类型');
						return;
					}
					if (2 == rg.length) {
						readStore.baseParams['succFalg'] = "*";
						succFalg = "*";
					} else {
						readStore.baseParams['succFalg'] = rg[0]
								.getRawValue();
						succFalg = rg[0].getRawValue();
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
				header : "数据时间",
				dataIndex : 'date',
				align : 'center'
			}, {
				header : "起码值",
				dataIndex : 'papRS',
				align : 'center'
			}, {
				header : "止码值",
				dataIndex : 'papRE',
				align : 'center'
			}, {
				header : "电量值",
				dataIndex : 'papE',
				align : 'center'
			}, {
				header : "综合倍率",
				dataIndex : 'tFactor',
				align : 'center'
			}, {
				header : "计量点编号",
				dataIndex : 'mpNo',
				align : 'center'
			}, {
				header : "计量点名称",
				dataIndex : 'mpName',
				align : 'center'
			}]);

	var readStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './qrystat/losePowerStat!loadReadData.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'lpsReadList',
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
									name : 'papRS',
									type: 'float'
								}, {
									name : 'papRE',
									type: 'float'
								}, {
									name : 'papE',
									type: 'float'
								}, {
									name : 'assetNo'
								}, {
									name : 'tmnlAssetNo'
								}, {
									name : 'tFactor'
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
									url : './qrystat/losePowerStat!exec.action',
									params : {
										checkAll : checkAll,
										checkRecall : recallCb.checked,
										tgId : tgId,
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
								url : './qrystat/losePowerStat!recalcData.action',
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
					enableExpAll : true,
					store : readStore
				})
	});

	var readWindow = new Ext.Window({
				width : 760,
				height : 500,
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
	readStore.baseParams['tgId'] = tgId;
	readStore.baseParams['startDate'] = startDate;
	readStore.baseParams['endDate'] = endDate;
	readStore.load();

	readWindow.show();
}
