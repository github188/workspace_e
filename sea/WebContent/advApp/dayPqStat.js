/*******************************************************************************
 * @author huangxuan
 ******************************************************************************/
Ext.onReady(function() {
	function itemFromId(panel, itemId) {
		return panel.find("itemId", itemId)[0];
	}
	/**
	 * @param r接受
	 *            record
	 */
	function createChartXml(r, type) {
		var rs = ['<chart palette="2" numberScaleValue="10000" numberScaleUnit="万千瓦时"  shownames="1" showvalues="0" decimals="0" numberPrefix="" useRoundEdges="1" legendBorderAlpha="0">'];
		rs.push('<categories>');
		rs.push(' <category label="' + "上网电量" + '" /> ');
		rs.push(' <category label="' + "供电量" + '" /> ');
		// rs.push(' <category label="' + "售电量" + '" /> ');
		rs.push(' <category label="' + "互供电量" + '" /> ');
		rs.push('</categories>');
		rs.push('<dataset seriesName="' + r.get("orgShortName")
				+ '" color="AFD8F8" showValues="0">');
		// 如果type==true，表示是青海省的

		rs.push('<set value="' + r.get("upEnergy") + '" /> ');
		rs.push('<set value="' + r.get("ppq") + '" /> ');
		// rs.push('<set value="' + r.get("spq") + '" /> ');
		rs.push('<set value="' + r.get("mutEnergy") + '" /> ');

		rs.push('</dataset>');
		rs.push('</chart>')
		return rs.join("");
	}

	/** *生成整个查询结果的xml** */
	function allChartXml(store) {
		var rs = ['<chart palette="2" numberScaleValue="10000" numberScaleUnit="万千瓦时"  shownames="1" showvalues="0" decimals="0" numberPrefix="" useRoundEdges="1" legendBorderAlpha="0">'];
		rs.push('<categories>');
		var cates = [];
		var dataSetUp = ['<dataset seriesName="上网电量" color="609d5c" showValues="0">'];
		var dataSetP = ['<dataset seriesName="供电量" color="57dad7" showValues="0">'];
		// var dataSetS = ['<dataset seriesName="售电量" color="fb2fa6"
		// showValues="0">'];
		var dataSetM = ['<dataset seriesName="互供电量" color="740cdc" showValues="0">'];
		var setUp = [];
		var setP = [];
		var setS = [];
		var setM = [];

		store.each(function(r, index) {
					cates.push(' <category label="' + r.get("orgShortName")
							+ '" /> ');
					setUp.push('<set value="' + r.get("upEnergy") + '" /> ');
					setP.push('<set value="' + r.get("ppq") + '" /> ');
					// setS.push('<set value="' + r.get("spq") + '" /> ');
					setM.push('<set value="' + r.get("mutEnergy") + '" /> ');

				});
		Array.prototype.push.apply(rs, cates);
		rs.push('</categories>');
		Array.prototype.push.apply(rs, dataSetUp);
		Array.prototype.push.apply(rs, setUp);
		rs.push("</dataset>");
		Array.prototype.push.apply(rs, dataSetP);
		Array.prototype.push.apply(rs, setP);
		rs.push("</dataset>");
		// Array.prototype.push.apply(rs, dataSetS);
		// Array.prototype.push.apply(rs, setS);
		// rs.push("</dataset>");
		Array.prototype.push.apply(rs, dataSetM);
		Array.prototype.push.apply(rs, setM);
		rs.push("</dataset>");
		// 609d5c
		// 57dad7
		// fb2fa6
		// ef56ac
		rs.push('</chart>')
		return rs.join("");
	}

	function showFlash(r, type) {
		var father = itemFromId(leftChartPanel, "father");
		var myChart = new FusionCharts("./fusionCharts/MSColumn2D.swf",
				"myChartId", father.getWidth(), father.getHeight());
		var xmlData = null;
		if (r instanceof Ext.data.Record) {
			xmlData = createChartXml(r, type);
		} else {
			xmlData = allChartXml(r);
		}
		myChart.setDataXML(xmlData);
		myChart.setTransparent(true);
		myChart.render(chartPanel.getId());
	}
	// 上面的时间和查询按钮的panel
	var chartTopPanel = new Ext.Panel({
		region : "north",
		height : 50,
		layout : "column",
		padding : "10px 0 0 5px",
		border : false,
		items : [{
					columnWidth : .5,
					layout : "form",
					border : false,
					labelWidth : 65,
					labelAlign : "right",
					items : [{
								xtype : "datefield",
								format : 'Y-m-d',
								itemId : "statDate",
								emptyText : '请选择日期 ...',
								fieldLabel : "查询时间",
								width : 100,
								allowBlank : false,
								editable : false,
								value : new Date().add(Date.DAY, -1)
							}]
				}, {
					border : false,
					columnWidth : .5,
					items : [{
						xtype : "button",
						width : 80,
						text : "查询",
						handler : function() {
							if (!itemFromId(chartTopPanel, "statDate")
									.isValid()) {
								return Ext.Msg.alert("提示", "时间必须填写");
							}
							centerStore.baseParams = {
								statDate : itemFromId(chartTopPanel, "statDate")
										.getValue()
							}
							centerStore.load({
										params : {
											start : 0,
											limit : 50
										}
									});
						}
					}]
				}]
	});
	var chartPanel = new Ext.Panel({
				border : false
			});
	// 左边的图片
	var leftChartPanel = new Ext.Panel({
				border : false,
				layout : "border",
				region : "center",
				items : [chartTopPanel, {
							itemId : "father",
							border : false,
							layout : "fit",
							region : "center",
							items : [chartPanel]
						}]
			});

	var centerStore = new Ext.data.JsonStore({
				url : './qrystat/losePowerMan!queryDayPqStat.action',
				fields : ["orgName", "orgNo", "statDate", "upEnergy", "ppq",
						"spq", "mutEnergy", "orgShortName"],
				root : "resultList",
				autoLoad : true,
				totalProperty : "resultCount"
			});

	centerStore.load({
				params : {
					start : 0,
					limit : 50
				}
			});
	centerStore.on("load", function(me) {
				showFlash(me);

			});
	var centerSm = new Ext.grid.RowSelectionModel({
				width : 20,
				hidden : true,
				listeners : {
					rowselect : function(me, rindex, r) {
						var v = r.get("statDate");
						v = v + "";
						v = v.replace(/-/g, "/").replace("T", " ");
						var date = new Date(v);
						upStore.baseParams = {
							typeCode : "01",
							orgNo : r.get("orgNo"),
							statDate : date
						};
						upStore.load({
									params : {
										start : 0,
										limit : 50
									}
								})
						downStore.baseParams = {
							typeCode : "02",
							orgNo : r.get("orgNo"),
							statDate : date
						}
						downStore.load({
									params : {
										start : 0,
										limit : 50
									}
								})
						spqStore.baseParams = {
							typeCode : "03",
							orgNo : r.get("orgNo"),
							statDate : date
						}

						spqStore.load({
									params : {
										start : 0,
										limit : 50
									}
								})
						mulStore.baseParams = {
							typeCode : "04",
							orgNo : r.get("orgNo"),
							statDate : date
						}
						mulStore.load({
									params : {
										start : 0,
										limit : 50
									}
								})

						showFlash(r, rindex == 0);
					}
				}
			});

	// 数字右对齐的renderer

	function rightRenderer(v) {
		if (!v && typeof v != "number") {
			return "";
		}
		v = v / 10000;

		// 千分加逗号
		return '<div align="right">' + Ext.util.Format.number(v, '0,000.0000')
				+ '</div>'
	}

	var centerCm = new Ext.grid.ColumnModel([centerSm, {
				header : "供电单位编号",
				width : 80,
				hidden : true,
				dataIndex : "orgNo"
			}, {
				header : "供电单位名称",
				width : 80,
				dataIndex : "orgShortName"
			}, {
				header : "统计时间",
				width : 80,
				hidden : true,
				dataIndex : "statDate",
				renderer : function(v) {
					v = v + "";
					v = v.replace(/-/g, "/").replace("T", " ");
					v = new Date(v).format("Y年m月d日 ");
					var html = '<span ext:qtitle="统计时间" ext:qtip="' + v + '">'
							+ v + '</span>';
					return html;
				}
			}, {
				header : "上网电量(万kWh)",
				width : 100,
				dataIndex : "upEnergy",
				renderer : rightRenderer
			}, {
				header : "供电量(万kWh)",
				width : 100,
				dataIndex : "ppq",
				renderer : function(v, m, r, index) {
					if (index == 0) {
						v = 0;
					}
					return rightRenderer(v);
				}
				
			}, {
				header : "售电量(万kWh)",
				width : 100,
				hidden : true,
				dataIndex : "spq",
				renderer : rightRenderer
			}, {
				header : "互供电量(万kWh)",
				width : 100,
				dataIndex : "mutEnergy",
				renderer : rightRenderer
			}]);
	// 下部显示grid的列表
	var pqGrid = new Ext.grid.GridPanel({
				region : "east",
				width : 500,
				autoScroll : true,
				store : centerStore,
				sm : centerSm,
				cm : centerCm,
				bbar : new Ext.ux.MyToolbar({
							store : centerStore
						}),
				viewConfig : {
					forceFit : true
				}
			});
	var topPanel = new Ext.Panel({
				border : false,
				height : 300,
				layout : "border",
				region : "north",
				items : [leftChartPanel, pqGrid]
			});
	// 上网电量对应的
	var upSm = new Ext.grid.RowSelectionModel({
				width : 20,
				hidden : true
			});
	var upCm = new Ext.grid.ColumnModel([upSm, {
				header : "供电单位名称",
				dataIndex : "orgShortName"
			}, {
				header : "供电单位编号",
				hidden : true,
				dataIndex : "orgNo"
			}, {
				header : "详细分类",
				dataIndex : "detTypeName"
			}, {
				header : "统计时间",
				dataIndex : "statDate",
				renderer : function(v) {
					v = v + "";
					v = v.replace(/-/g, "/").replace("T", " ");
					v = new Date(v).format("Y年m月d日");
					var html = '<span ext:qtitle="统计时间" ext:qtip="' + v + '">'
							+ v + '</span>';
					return html;
				}
			}, {
				header : "统计值(万kWh)",
				dataIndex : "value",
				renderer : rightRenderer
			}]);
	var upStore = new Ext.data.JsonStore({
				url : './qrystat/losePowerMan!queryDayPqStatByType.action',
				fields : ["orgName", "orgNo", "statDate", "energyType",
						"energyDetType", "value", "orgShortName", "detTypeName"],
				root : "resultList",
				totalProperty : "resultCount"
			});
	var upGrid = new Ext.grid.GridPanel({
				cm : upCm,
				sm : upSm,
				border : false,
				region : "center",
				store : upStore,
				bbar : new Ext.ux.MyToolbar({
							store : upStore
						})
			});

	// 上网电量面板
	var upWebPanel = new Ext.Panel({
				border : false,
				layout : "border",
				title : "上网电量",
				items : [upGrid]
			});

	// 供电量对应的数据
	var downSm = new Ext.grid.RowSelectionModel({
				width : 20,
				hidden : true
			});
	var downCm = new Ext.grid.ColumnModel([downSm, {
				header : "供电单位名称",
				dataIndex : "orgShortName"
			}, {
				header : "供电单位编号",
				hidden : true,
				dataIndex : "orgNo"
			}, {
				header : "详细分类",
				dataIndex : "detTypeName"
			}, {
				header : "统计时间",
				dataIndex : "statDate",
				renderer : function(v) {
					v = v + "";
					v = v.replace(/-/g, "/").replace("T", " ");
					v = new Date(v).format("Y年m月d日");
					var html = '<span ext:qtitle="统计时间" ext:qtip="' + v + '">'
							+ v + '</span>';
					return html;
				}
			}, {
				header : "统计值(万kWh)",
				dataIndex : "value",
				renderer : rightRenderer
			}]);
	var downStore = new Ext.data.JsonStore({
				url : './qrystat/losePowerMan!queryDayPqStatByType.action',
				fields : ["orgName", "orgNo", "statDate", "energyType",
						"energyDetType", "value", "orgShortName", "detTypeName"],
				root : "resultList",
				totalProperty : "resultCount"
			});
	var downGrid = new Ext.grid.GridPanel({
				cm : downCm,
				sm : downSm,
				border : false,
				region : "center",
				store : downStore,
				bbar : new Ext.ux.MyToolbar({
							store : downStore
						})
			});
	var downWebPanel = new Ext.Panel({
				border : false,
				title : "供电量",
				layout : "border",
				items : [downGrid]
			});
	// 售电量对应数据
	// 供电量对应的数据
	var spqSm = new Ext.grid.RowSelectionModel({
				width : 20,
				hidden : true
			});
	var spqCm = new Ext.grid.ColumnModel([spqSm, {
				header : "供电单位名称",
				dataIndex : "orgShortName"
			}, {
				header : "供电单位编号",
				hidden : true,
				dataIndex : "orgNo"
			}, {
				header : "详细分类",
				dataIndex : "detTypeName"
			}, {
				header : "统计时间",
				dataIndex : "statDate",
				renderer : function(v) {
					v = v + "";
					v = v.replace(/-/g, "/").replace("T", " ");
					v = new Date(v).format("Y年m月d日 ");
					var html = '<span ext:qtitle="统计时间" ext:qtip="' + v + '">'
							+ v + '</span>';
					return html;
				}
			}, {
				header : "统计值(万kWh)",
				dataIndex : "value",
				renderer : rightRenderer
			}]);
	var spqStore = new Ext.data.JsonStore({
				url : './qrystat/losePowerMan!queryDayPqStatByType.action',
				fields : ["orgName", "orgNo", "statDate", "energyType",
						"energyDetType", "value", "orgShortName", "detTypeName"],
				root : "resultList",
				totalProperty : "resultCount"
			});
	var spqGrid = new Ext.grid.GridPanel({
				cm : spqCm,
				sm : spqSm,
				border : false,
				region : "center",
				store : spqStore,
				bbar : new Ext.ux.MyToolbar({
							store : spqStore
						})
			});
	var spqPanel = new Ext.Panel({
				border : false,
				layout : "border",
				title : "售电量",
				items : [spqGrid]
			});
	// 互感电量
	var mulSm = new Ext.grid.RowSelectionModel({
				width : 20,
				hidden : true
			});
	var mulCm = new Ext.grid.ColumnModel([mulSm, {
				header : "供电单位名称",
				dataIndex : "orgShortName"
			}, {
				header : "供电单位编号",
				hidden : true,
				dataIndex : "orgNo"
			}, {
				header : "详细分类",
				dataIndex : "detTypeName"
			}, {
				header : "统计时间",
				dataIndex : "statDate",
				renderer : function(v) {
					v = v + "";
					v = v.replace(/-/g, "/").replace("T", " ");
					v = new Date(v).format("Y年m月d日 ");
					var html = '<span ext:qtitle="统计时间" ext:qtip="' + v + '">'
							+ v + '</span>';
					return html;
				}
			}, {
				header : "统计值(万kWh)",
				dataIndex : "value",
				renderer : rightRenderer
			}]);
	var mulStore = new Ext.data.JsonStore({
				url : './qrystat/losePowerMan!queryDayPqStatByType.action',
				fields : ["orgName", "orgNo", "statDate", "energyType",
						"energyDetType", "value", "orgShortName", "detTypeName"],
				root : "resultList",
				totalProperty : "resultCount"
			});
	// mulStore.load({});
	var mulGrid = new Ext.grid.GridPanel({
				border : false,
				cm : mulCm,
				sm : mulSm,
				region : "center",
				store : mulStore,
				bbar : new Ext.ux.MyToolbar({
							store : mulStore
						})
			});
	var mulPanel = new Ext.Panel({
				border : false,
				title : "互供电量",
				layout : "border",
				items : [mulGrid]
			});
	var centerPanel = new Ext.TabPanel({
				region : "center",
				border : false,
				activeItem : 0,
				items : [upWebPanel, downWebPanel,
						// spqPanel
						// ,
						mulPanel]
			});
	var mainPanel = new Ext.Panel({
				border : false,
				layout : "border",
				items : [topPanel, centerPanel]
			});
	renderModel(mainPanel, "全网电量统计");
});