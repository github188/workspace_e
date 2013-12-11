Ext.onReady(function() {
	var dataResult = {};
	function createChartXml(arr, title, map) {
		// 随机图形中显示的颜色
		function randomColor() {
			var rand = Math.floor(Math.random() * 0xFFFFFF).toString(16);
			if (rand.length == 6) {
				return rand;
			} else {
				return randomColor();
			}
		}
		var header = '<chart caption="" useroundedges="1" numberScaleValue="0.01" numberScaleUnit="%" '
				+ 'legendBorderAlpha="0" shownames="1" showvalues="0" decimals="3" numberPrefix="">';
		var root = document.createElement("chart");
		var categories = document.createElement("categories");
		root.appendChild(categories)
		for (var i = 0; i < arr.length; i++) {
			var category = document.createElement("category");
			category.setAttribute("label", arr[i][title]);
			categories.appendChild(category);
		}
		for (k in map) {
			var dataset = document.createElement("dataset");
			dataset.setAttribute("seriesName", k);
			dataset.setAttribute("color", randomColor());
			dataset.setAttribute("showValues", "0");
			for (var i = 0; i < arr.length; i++) {
				var set = document.createElement("set");
				set.setAttribute("value", arr[i][map[k]]);
				dataset.appendChild(set);
			}
			root.appendChild(dataset);
		}
		var xmlData = [header, root.innerHTML, "</chart>"].join("");
		var TerminalCoverageChart1 = new FusionCharts(
				"fusionCharts/ScrollColumn2D.swf", "TerminalCoverageChartId",
				charPanel.getWidth(), charPanel.getHeight());

		// TerminalCoverageChart1.setDataURL("./qryStat/queryCollPoint/tcDate.xml");
		// myChart.setDataXML(xmlData);
		TerminalCoverageChart1.setDataXML(xmlData);
		TerminalCoverageChart1.setTransparent(true);
		TerminalCoverageChart1.render(TerminalCoverage21.getId());// 渲染
		return xmlData;
	}
	// createChartXml();
	// 采集点覆盖情况panel1
	var TerminalCoverage1 = new Ext.form.RadioGroup({
				plain : true,
				border : false,
				height : 35,
				layout : 'fit',
				columns : [200, 200],
				items : [{
							checked : true,
							style : "padding:5px",
							boxLabel : '按地区',
							name : 'fav-color',
							inputValue : '1',
							id : 'terminalCoverageimage1',
							handler : function(e, checked) {
								if (checked) {
									TerminalCoverage3.layout.setActiveItem(0);
									loadDataArea();
								}
								// TerminalCoverage31.render(TerminalCoverage3.getId());
							}
						}, {
							boxLabel : '按容量',
							style : "padding:5px",
							name : 'fav-color',
							inputValue : '2',
							id : 'terminalCoverageimage3',// 定义ID
							handler : function(e, checked) {
								if (checked) {
									TerminalCoverage3.layout.setActiveItem(2);
									getLoadData();
								}
							}
						}]
			});
	// -----------------------------------------------------------------------------
	// 采集点覆盖情况panel21
	var TerminalCoverage21 = new Ext.Panel({
				border : true,
				bodyBorder : false
			});
	var charPanel = new Ext.Panel({
				border : false,
				height : 220,
				items : [TerminalCoverage21],
				listeners : {
					"afterlayout" : function() {
						if (TerminalCoverage1.getValue().inputValue == "1") {
							loadDataArea();
							return;
						}
						getLoadData();
					}
				}
			});

	// 采集点覆盖情况panel2
	// var TerminalCoverage2 = new Ext.Panel({
	// border : false,
	// layout:"form",
	// bodyBorder : false,
	// height : 280,
	// items : [TerminalCoverage1, TerminalCoverage21]
	// });
	// ------------------------------------------------------------------------------------------
	// 采集点覆盖情况的grid
	var groupData1 = [{
				gdqy : '西宁供电公司',
				zbhs : '<a href="javascript:" onclick="tcWindowShow1()">5033</a>',
				zb_yzzd : '3900',
				zb_fgl : '76%',
				pbts : '<a href="javascript:" onclick="tcWindowShow2()">17390</a>',
				pb_yzzd : '5808',
				pb_fgl : '35%',
				jmhs : '250340',
				jchs : '120911',
				jc_fgl : '55%'
			}, {
				gdqy : '海南供电公司',
				zbhs : '<a href="javascript:" onclick="tcWindowShow1()">3421</a>',
				zb_yzzd : '3101',
				zb_fgl : '81%',
				pbts : '<a href="javascript:" onclick="tcWindowShow2()">12310</a>',
				pb_yzzd : '4810',
				pb_fgl : '37%',
				jmhs : '250340',
				jchs : '120911',
				jc_fgl : '40%'
			}, {
				gdqy : '黄化供电公司',
				zbhs : '<a href="javascript:">4098</a>',
				zb_yzzd : '3590',
				zb_fgl : '78%',
				pbts : '<a href="javascript:">11300</a>',
				pb_yzzd : '3801',
				pb_fgl : '45%',
				jmhs : '250340',
				jchs : '120911',
				jc_fgl : '47%'
			}, {
				gdqy : '海东供电公司',
				zbhs : '<a href="javascript:">2876</a>',
				zb_yzzd : '2502',
				zb_fgl : '83%',
				pbts : '<a href="javascript:">13391</a>',
				pb_yzzd : '2500',
				pb_fgl : '24%',
				jmhs : '250340',
				jchs : '120911',
				jc_fgl : '54%'
			}, {
				gdqy : '海北供电公司',
				zbhs : '<a href="javascript:">5345</a>',
				zb_yzzd : '3809',
				zb_fgl : '70%',
				pbts : '<a href="javascript:">10370</a>',
				pb_yzzd : '4840',
				pb_fgl : '37%',
				jmhs : '250340',
				jchs : '120911',
				jc_fgl : '58%'
			}, {
				gdqy : '海西供电公司',
				zbhs : '<a href="javascript:">5078</a>',
				zb_yzzd : '3506',
				zb_fgl : '60%',
				pbts : '<a href="javascript:">12350</a>',
				pb_yzzd : '5201',
				pb_fgl : '35%',
				jmhs : '250340',
				jchs : '120911',
				jc_fgl : '50%'
			}, {
				gdqy : '果洛供电公司',
				zbhs : '<a href="javascript:">5025</a>',
				zb_yzzd : '3507',
				zb_fgl : '65%',
				pbts : '<a href="javascript:">7390</a>',
				pb_yzzd : '4833',
				pb_fgl : '35%',
				jmhs : '250340',
				jchs : '120911',
				jc_fgl : '50%'
			}];

	var groupStore1 = new Ext.data.JsonStore({
				url : "./qrystat/terminalCoverageAction!queryCoverageArea.action",
				root : "resultList",
				fields : ['rate1', 'rate2', 'rate5', 'demandcnt1',
						'demandcnt2', 'demandcnt5', 'demandcntrest',
						'conscntrest', 'rateRest', 'conscnt1', 'conscnt2',
						'conscnt5', 'orgNo', 'orgShortName']
			});
	var groupCM1 = new Ext.grid.ColumnModel([{
				header : '供电区域',
				dataIndex : 'orgShortName',
				sortable : true,
				align : 'center'
			}, {
				header : '专变户数',
				dataIndex : 'demandcnt1',
				sortable : true,
				align : 'center',
				renderer : rightAlignRenderer
			}, {
				header : '已装终端',
				dataIndex : 'conscnt1',
				sortable : true,
				align : 'center',
				renderer : rightAlignRenderer
			}, {
				header : '覆盖率',
				dataIndex : 'rate1',
				renderer : createBaiRenderer(2, '<div align="right">'),
				sortable : true,
				align : 'center'
			}, {
				header : '公变台数',
				dataIndex : 'demandcnt2',
				sortable : true,
				align : 'center',
				renderer : rightAlignRenderer
			}, {
				header : '已装终端',
				dataIndex : 'conscnt2',
				sortable : true,
				align : 'center',
				renderer : rightAlignRenderer
			}, {
				header : '覆盖率',
				dataIndex : 'rate2',
				renderer : createBaiRenderer(2, '<div align="right">'),
				sortable : true,
				align : 'center'
			}, {
				header : '居民户数',
				dataIndex : 'demandcnt5',
				sortable : true,
				align : 'center',
				renderer : rightAlignRenderer
			}, {
				header : '应装户数',
				dataIndex : 'conscnt5',
				sortable : true,
				align : 'center',
				renderer : rightAlignRenderer
			}, {
				header : '居民覆盖率',
				dataIndex : 'rate5',
				sortable : true,
				renderer : createBaiRenderer(2, '<div align="right">'),
				align : 'center'
			}]);

	var TerminalCoverage31 = new Ext.grid.GridPanel({
				autoScroll : true,
				hidden : false,
				viewConfig : {
					forceFit : false
				},
				cm : groupCM1,
				store : groupStore1
			});
	// 采集点覆盖情况的grid2-----------------------------------------------------------------
	var groupData2 = [{
				gddw : '西宁供电公司',
				dydj : '500kV',
				zbhs : '333',
				zb_yzzd : '444',
				zb_fgl : '88%',
				pbts : '99',
				pb_yzzd : '222',
				pb_fgl : '11%',
				jmhs : '3334',
				jchs : '900',
				jc_fgl : '45%'
			}, {
				gddw : '海东供电公司',
				dydj : '500kV',
				zbhs : '333',
				zb_yzzd : '444',
				zb_fgl : '88%',
				pbts : '99',
				pb_yzzd : '222',
				pb_fgl : '11%',
				jmhs : '3334',
				jchs : '900',
				jc_fgl : '45%'
			}, {
				gddw : '海西供电公司',
				dydj : '500kV',
				zbhs : '333',
				zb_yzzd : '444',
				zb_fgl : '88%',
				pbts : '99',
				pb_yzzd : '222',
				pb_fgl : '11%',
				jmhs : '3334',
				jchs : '900',
				jc_fgl : '45%'
			}];
	var groupStore2 = new Ext.data.JsonStore({
				data : groupData2,
				fields : ['gddw', 'dydj', 'zbhs', 'zb_yzzd', 'zb_fgl', 'pbts',
						'pb_yzzd', 'pb_fgl', 'jmhs', 'jchs', 'jc_fgl']
			});
	var groupCM2 = new Ext.grid.ColumnModel([{
				header : '供电单位',
				dataIndex : 'gddw',
				sortable : true,
				align : 'center'
			}, {
				header : '电压等级',
				dataIndex : 'dydj',
				sortable : true,
				align : 'center'
			}, {
				header : '专变户数',
				dataIndex : 'zbhs',
				sortable : true,
				align : 'center'
			}, {
				header : '已装终端',
				dataIndex : 'zb_yzzd',
				sortable : true,
				align : 'center'
			}, {
				header : '覆盖率',
				dataIndex : 'zb_fgl',
				sortable : true,
				align : 'center'
			}, {
				header : '配变台数',
				dataIndex : 'pbts',
				sortable : true,
				align : 'center'
			}, {
				header : '已装终端',
				dataIndex : 'pb_yzzd',
				sortable : true,
				align : 'center'
			}, {
				header : '覆盖率',
				dataIndex : 'pb_fgl',
				sortable : true,
				align : 'center'
			}, {
				header : '居民户数',
				dataIndex : 'jmhs',
				sortable : true,
				align : 'center'
			}, {
				header : '集抄户数',
				dataIndex : 'jchs',
				sortable : true,
				align : 'center'
			}, {
				header : '集抄覆盖率',
				dataIndex : 'jc_fgl',
				sortable : true,
				align : 'center'
			}]);

	var TerminalCoverage32 = new Ext.grid.GridPanel({
				hidden : true,
				autoScroll : true,
				cm : groupCM2,
				viewConfig : {
					forceFit : false
				},
				store : groupStore2,
				bbar : new Ext.ux.MyToolbar({
							store : groupStore2
						})
			});

	var groupStore3 = new Ext.data.JsonStore({
				url : "./qrystat/terminalCoverageAction!queryCoverageCap.action",
				root : "resultList",
				fields : ['orgNo', 'orgShortName', 'consCnt0', 'instConsCnt0',
						'consCnt1', 'instConsCnt1', 'consCnt2', 'instConsCnt2',
						'consCnt3', 'instConsCnt3', 'instConsRate0',
						'instConsRate1', 'instConsRate2', 'instConsRate3']
			});
	Ext.apply(groupStore3.reader, {
				read : function(response) {
					var json = response.responseText;
					var o = Ext.decode(json);
					if (!o) {
						throw {
							message : "JsonReader.read: Json object not found"
						};
					}
					dataResult = o.resultList[0] && o.resultList || {};
					return this.readRecords(o);
				}
			});

	// 一个百度币renderer的生产器
	/***************************************************************************
	 * @param dic
	 *            精度
	 * @param wrap
	 *            封装结果的标签以及他的属性
	 **************************************************************************/
	function createBaiRenderer(dic, wrap) {
		return function(v) {
			var tag = "";
			tag = wrap.match(/[a-zA-Z]+/)[0];
			if (!v || v == "0") {
				return [wrap,"0","</",tag,">"].join("");
			}
			v = new Number(v / 0.01).toFixed(dic);
			// v=new Number(v).toFixed(5)+"";
			var rs = [];
			rs.push(v);
			rs.push("%")
			if (!tag || tag.trim() == "") {
				return rs.join("");
			}
			rs.splice(0, 0, wrap);
			rs.push("</");
			rs.push(tag);
			rs.push(">");
			return rs.join("");
		}
	}
	function rightAlignRenderer(v) {
		return '<div align="right">' + (v||"0") + '</div>';
	}
	var groupCM3 = new Ext.grid.ColumnModel([{
				header : ' ',
				dataIndex : 'orgShortName',
				align : 'center'
			}, {
				header : '应装数',
				dataIndex : 'consCnt3',
				align : 'center',
				renderer : rightAlignRenderer
			}, {
				header : '已装数',
				dataIndex : 'instConsCnt3',
				align : 'center',
				renderer : rightAlignRenderer
			}, {
				header : '安装率',
				dataIndex : 'instConsRate3',
				align : 'center',
				renderer : createBaiRenderer(2, '<div align="right">')
			}, {
				header : '应装数',
				dataIndex : 'consCnt2',
				align : 'center',
				renderer : rightAlignRenderer
			}, {
				header : '已装数',
				dataIndex : 'instConsCnt2',
				align : 'center',
				renderer : rightAlignRenderer
			}, {
				header : '安装率',
				dataIndex : 'instConsRate2',
				align : 'center',
				renderer : createBaiRenderer(2, '<div align="right">')
			}, {
				header : '应装数',
				dataIndex : 'consCnt1',
				align : 'center',
				renderer : rightAlignRenderer
			}, {
				header : '已装数',
				dataIndex : 'instConsCnt1',
				align : 'center',
				renderer : rightAlignRenderer
			}, {
				header : '安装率',
				dataIndex : 'instConsRate1',
				align : 'center',
				renderer : createBaiRenderer(2, '<div align="right">')
			}, {
				header : '应装数',
				dataIndex : 'consCnt0',
				align : 'center',
				renderer : rightAlignRenderer
			}, {
				header : '已装数',
				dataIndex : 'instConsCnt0',
				align : 'center',
				renderer : rightAlignRenderer
			}, {
				header : '安装率',
				dataIndex : 'instConsRate0',
				align : 'center',
				renderer : createBaiRenderer(2, '<div align="right">')
			}]);

	var terminalPlugins = new Ext.ux.grid.ColumnHeaderGroup({
				rows : [[{
							header : "供电单位",
							align : "center",
							colspan : 1,
							dataIndex : ""
						}, {
							header : "315KVA以上",
							align : "center",
							colspan : 3,
							dataIndex : ""
						}, {
							header : "100KVA-315KVA",
							align : "center",
							colspan : 3,
							dataIndex : ""
						}, {
							header : "50KVA-100KVA",
							align : "center",
							colspan : 3,
							dataIndex : ""
						}, {
							header : "50KVA以下",
							align : "center",
							colspan : 3,
							dataIndex : ""
						}]]
			});
	var TerminalCoverage33 = new Ext.grid.GridPanel({
				autoScroll : true,
				hidden : true,
				cm : groupCM3,
				viewConfig : {
					forceFit : false
				},
				// region:'center',
				store : groupStore3,
				plugins : terminalPlugins

			});

	function getLoadData(nodeStr) {
		var firstNode = Ext.getCmp("areaTree").root.childNodes[0];
		groupStore3.load({
					params : {
						node : nodeStr || firstNode.id || "org_63101_02"
					},
					callback : function() {
						var fb = new Flash.Born({
									width : charPanel.getWidth(),
									height : charPanel.getHeight(),
									id : "fb_chart",
									chart : {
										numberScaleValue : "0.01",
										numberScaleUnit : "%",
										decimals : "2"
									},
									transparent : true,
									legend : "orgShortName",
									nameData : {
										"350KVA以上" : "instConsRate3",
										"100~350KVA" : "instConsRate2",
										"50~100KVA" : "instConsRate1",
										"50以下" : "instConsRate0"
									},
									data : this,
									url : "fusionCharts/ScrollColumn2D.swf"
								});
						fb.render(TerminalCoverage21.getId(), true);

					}
				});
	}
	// 按地区来统计
	function loadDataArea(nodeStr) {
		var firstNode = Ext.getCmp("areaTree").root.childNodes[0];
		groupStore1.load({
					params : {
						node : nodeStr || firstNode.id || "org_63101_02"
					},
					callback : function() {
						var fb = new Flash.Born({
									width : charPanel.getWidth(),
									id : "fb_chart",
									chart : {
										numberScaleValue : "0.01",
										numberScaleUnit : "%",
										decimals : "2"
									},
									legend : "orgShortName",
									nameData : {
										"专变" : "rate1",
										"公变" : "rate2",
										"居民" : "rate5"
									},
									transparent : true,
									height : charPanel.getHeight(),
									data : this,
									url : "fusionCharts/ScrollColumn2D.swf"
								});
						fb.render(TerminalCoverage21.getId(), true);
					}
				});
	}

	// ----------------------------------------------------------------
	var TerminalCoverage3 = new Ext.Panel({
		//autoScroll : true,
		region:"center",
		border : false,
		layout : 'card',
		activeItem : 0,
		// region : 'south',
		items : [TerminalCoverage31, TerminalCoverage32, TerminalCoverage33]
			// 
		});
	// 设置顶层的采集点覆盖情况panel
	var TerminalCoveragepanel = new Ext.Panel({
				// title : "采集点覆盖情况",
				region : "center",
				autoScroll : true,
				layout : 'border',
				border : false,
				items : [{
							region : 'north',
							layout : 'form',
							height : 250,
							items : [TerminalCoverage1, charPanel]
						}, TerminalCoverage3]
			});

	var mainPanel = new Ext.Panel({
				autoScroll : true,
				border : false,
				layout : "border",
				items : [TerminalCoveragepanel]
			});
	renderModel(mainPanel, '采集点覆盖情况');
	// var fb = new Flash.Born({
	// width : charPanel.getWidth(),
	// id : "fb_chart",
	// transparent : true,
	// height : charPanel.getHeight(),
	// data : "url:./qryStat/queryCollPoint/tcDate.xml",
	// url : "fusionCharts/MSColumn3D.swf"
	// });
	// fb.render(TerminalCoverage21.getId());
	loadDataArea();
	new LeftTreeListener({
				modelName : '采集点覆盖情况',
				processClick : function(p, node, e) {
					var obj = node.attributes.attributes;
					var type = node.attributes.type;
					if (TerminalCoverage1.getValue().inputValue == "1") {
						loadDataArea(node.id);
					} else {
						getLoadData(node.id);
					}
				}
			});

})
function tcWindowShow1() {
	openTab("专变采集点查询", "./qryStat/queryCollPoint/privateTerminal.jsp");
}
function tcWindowShow2() {
	openTab("公变采集点查询", "./qryStat/queryCollPoint/publicTerminal.jsp");
}
