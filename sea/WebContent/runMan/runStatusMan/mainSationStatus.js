// 全局变量?
var wgjList;
var cjjList;
var webServiceList;
var interfaceServiceList;
var databaseList;
// fusionchart图例右置：<chart legendPosition='RIGHT'
Ext.onReady(function() {
	// 普通随机生成颜色
	function randomColor() {
		var rand = Math.floor(Math.random() * 0xFFFFFF).toString(16);
		if (rand.length == 6) {
			return rand;
		} else {
			return randomColor();
		}
	}
	/**
	 * 将不同的数据按照ip来分离
	 * 
	 * @param arr
	 *            一个数组
	 * @param id
	 *            作为生产对象key的对象
	 */
	function separateIp(arr, id) {
		var m = {};
		if (!arr || arr.length == 0) {
			return m;
		}
		Ext.each(arr, function(a) {
					if (!m[a[id]]) {
						m[a[id]] = [];
					}
					m[a[id]].push(a);
				});
		return m;
		// var result=Ext.decode(str);
		// wgjList = result.wgjList;
		// cjjList = result.cjjList;
		// webServiceList = result.webServiceList;
		// interfaceServiceList = result.interfaceServiceList;
		// databaseList = result.databaseList;
	}

	/**
	 * 根据足够的信息来生成一个panel,此panel没是个数据作为一行显示
	 * 
	 * @mp 一个用来放置数据的mp
	 * @type 类型
	 * @jqlx 集群的类型
	 */
	function createPanel(mp, jqlx, columns) {
		var panel = new Ext.Panel({
					autoScroll : true,
					region:"center",
					border : false,
					bodyCfg : {
						tag : "center"
					},
					layout : "table",
					itemId : "manyChart",
					layoutConfig : {
						columns : columns || 4
					}
				});
		panel.add(new Ext.Panel({
					border : false,
					bodyStyle : 'padding:0px 0px 0px 45px',
					items : [{
								xtype : "label",
								text : "网络使用率"
							}]
				}));
		panel.add(new Ext.Panel({
					border : false,
					bodyStyle : 'padding:0px 0px 0px 45px',
					items : [{
								xtype : "label",
								text : "CPU使用率"
							}]
				}));
		panel.add(new Ext.Panel({
					border : false,
					bodyStyle : 'padding:0px 0px 0px 45px',
					items : [{
								xtype : "label",
								text : "内存使用率"
							}]
				}));
		panel.add(new Ext.Panel({
					border : false,
					bodyStyle : 'padding:0px 0px 0px 45px',
					items : [{
								xtype : "label",
								text : "硬盘使用率"
							}]
				}));

		Ext.iterate(mp, function(k, v) {
					// 添加一个标题
					var titlePanel = new Ext.Panel({
								title : "节点:"+k,
								border : false,
								colspan : 4,
								items : [],
								layout : "table",
								layoutConfig : {
									columns : 4
								}
							});
					var types = ["net", "cpu", "ram", "disk"];
					// 按类型添加
					for (var ii = 0; ii < 4; ii++) {
						var type = types[ii];
						// 一个用来放置flash的面板
						var charP = new Ext.Panel({
									border : true,
									collapsible : true,
									listeners : {
										afterlayout : function() {
											var myChart = new FusionCharts(
													"./fusionCharts/MSArea.swf",// wgjList
													// 1
													"myChartId", "200", "165");
											var xmlData = createDataXml(v,
													this.type, jqlx);
											myChart.setDataXML(xmlData);
											myChart.setTransparent(true);
											myChart.render(this.getId());
											// if(k=="10.215.191.66"){
											// alert(this.type);
											// }
										}
									}
								});

						charP.type = type;
						titlePanel.add(charP);
					}
					titlePanel.doLayout();
					panel.add(titlePanel);
				});
		panel.doLayout();
		return panel;
	}

	// 5个集群中各节点随机生成颜色

	// **************************
	function randomColor(i) {
		if (i == 0) {
			return '#0000FF';
		} else if (i == 1) {
			return '#FF00FF';
		} else if (i == 2) {
			return '#FF0000';
		} else if (i == 3) {
			return '#00FF00';
		} else if (i == 4) {
			return '' + (0.6 * 0xffffff << 0).toString(16);
		} else {
			return '' + ((1 - 1.0 / i) * 0xffffff << 0).toString(16);
		}
	}
	// **************************

	// function randomColor0(i) {
	// if (i==0) {
	// return ''+(0xCC0000).toString(16);
	// }else if(i==1) {
	// return ''+(0x33FFDD).toString(16);
	// } else if (i==2){
	// return ''+(0xA20055).toString(16);
	// } else if (i==3){
	// return ''+(0xFFFF77).toString(16);
	// }else if (i==4){
	// return ''+(0.6*0xffffff<<0).toString(16);
	// }else {
	// return ''+((1-1.0/i)*0xffffff<<0).toString(16);
	// }
	// }
	//	

	function createDataXml(jsonData, type, jqlx) {
		// var xmlData = '<chart bgColor="E9E9E9" outCnvBaseFontColor="666666"
		// showNames="0" ' +
		// 'showValues="0" plotFillAlpha="50" numVDivLines="10"
		// showAlternateVGridColor="1" ' +
		// 'AlternateVGridColor="e1f5ff" divLineColor="e1f5ff"
		// vdivLineColor="e1f5ff" ' +
		// 'baseFontColor="666666" canvasBorderThickness="1" showPlotBorder="1"'
		// +
		// ' plotBorderThickness="0">';

		var xmlData = '<chart bgColor="FFFFFF" outCnvBaseFontColor="666666"  showNames="0" '
				+ 'showValues="0" plotFillAlpha="50" numVDivLines="10"  showBorder="0" showAlternateVGridColor="1" '
				+ 'AlternateVGridColor="e1f5ff" divLineColor="e1f5ff" vdivLineColor="e1f5ff" '
				+ 'baseFontColor="666666" canvasBorderThickness="1" showPlotBorder="1" yAxisMaxValue="100" '
				+ 'plotBorderThickness="0">	';

		var str = "<categories>";
		for (var i = 0; i < 24; i++) {
			str += '<category label="' + i + '点" />';
		}
		str += '</categories>';

		// ???????
		var bean = jsonData[0];
		var num = 0;
		var ipAddrNum = 1;
		Ext.each(jsonData, function(obj) {
					var forBean = jsonData[num];
					if (bean.ipAddr != forBean.ipAddr) {
						ipAddrNum++;
						bean = forBean;
					}
					num++;
				});

		var mainSation = new Array();

		for (var i = 0; i < ipAddrNum; i++) {
			mainSation[i] = new Array(24);
		}

		bean = jsonData[0];
		num = 0;
		var curIpAddr = 0;
		// 各使用率
		Ext.each(jsonData, function(obj) {
					var forBean = jsonData[num];
					var time = forBean.monitorTime;
					if (bean.ipAddr != forBean.ipAddr) {
						curIpAddr++;
						bean = forBean;
					}
					if (type == 'net') {
						mainSation[curIpAddr][time] = forBean.netUseRatio;
					} else if (type == 'cpu') {
						mainSation[curIpAddr][time] = forBean.cpuUseRatio;
					} else if (type == 'ram') {
						mainSation[curIpAddr][time] = forBean.memoryUseRatio;
					} else if (type == 'disk') {
						mainSation[curIpAddr][time] = forBean.diskUseRatio;
					}
					num++;
				});
		// 集群类型jqlx
		for (var i = 0; i < mainSation.length; i++) {
			if (jqlx == 1) {
				str += '<dataset color="' + randomColor(i)
						+ '" showValues="0" anchorBorderColor="808080">';
			} else if (jqlx == 2) {
				str += '<dataset color="' + randomColor(i)
						+ '" showValues="0" plotBorderColor="' + randomColor()
						+ '">';
			} else if (jqlx == 3) {
				str += '<dataset color="' + randomColor(i)
						+ '" showValues="0" plotBorderColor="' + randomColor()
						+ '">';
			}
			if (jqlx == 4) {
				str += '<dataset color="' + randomColor(i)
						+ '" showValues="0" plotBorderColor="' + randomColor()
						+ '">';
			} else if (jqlx == 5) {
				str += '<dataset color="' + randomColor(i)
						+ '" showValues="0" plotBorderColor="' + randomColor()
						+ '">';
			}
			for (var j = 0; j < mainSation[i].length; j++) {
				str += '<set label="'
						+ j
						+ '点" value="'
						+ (mainSation[i][j] == null ? 0 : mainSation[i][j]
								.toPrecision(3)) // .toPrecision(3)取值截取保留3位数
						+ '" /> ';
			}
			str += '</dataset>';
		}
		xmlData += str;
		xmlData += '</chart>';
		return xmlData; // function XX(){}return返回值
	}

	function loadF(result) {
		wgjList = result.wgjList;
		cjjList = result.cjjList;
		webServiceList = result.webServiceList;
		interfaceServiceList = result.interfaceServiceList;
		databaseList = result.databaseList;

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// wgjList 1
				"myChartId", "160", "145");
		var xmlData = createDataXml(wgjList, 'net', 1);
		myChart.setDataXML(xmlData);
		myChart.setTransparent(true);
		myChart.render(chartPanel.getId());

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// wgjList 2
				"myChartId", "160", "145");
		var xmlData1 = createDataXml(wgjList, 'cpu', 1);
		myChart.setDataXML(xmlData1);
		myChart.setTransparent(true);
		myChart.render(chartPanel1.getId());

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// wgjList 3
				"myChartId", "160", "145");
		var xmlData2 = createDataXml(wgjList, 'ram', 1);
		myChart.setDataXML(xmlData2);
		myChart.setTransparent(true);
		myChart.render(chartPanel2.getId());

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// wgjList 4
				"myChartId", "160", "145");
		var xmlData3 = createDataXml(wgjList, 'disk', 1);
		myChart.setDataXML(xmlData3);
		myChart.setTransparent(true);
		myChart.render(chartPanel3.getId());

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// cjjList 1
				"myChartId", "160", "145");
		var xmlData_1 = createDataXml(cjjList, 'net', 2);
		myChart.setDataXML(xmlData_1);
		myChart.setTransparent(true);
		myChart.render(chartPanel_1.getId());

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// cjjList 2
				"myChartId", "160", "145");
		var xmlData1_1 = createDataXml(cjjList, 'cpu', 2);
		myChart.setDataXML(xmlData1_1);
		myChart.setTransparent(true);
		myChart.render(chartPanel1_1.getId());

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// cjjList 3
				"myChartId", "160", "145");
		var xmlData2_1 = createDataXml(cjjList, 'ram', 2);
		myChart.setDataXML(xmlData2_1);
		myChart.setTransparent(true);
		myChart.render(chartPanel2_1.getId());

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// cjjList 4
				"myChartId", "160", "145");
		var xmlData3_1 = createDataXml(cjjList, 'disk', 2);
		myChart.setDataXML(xmlData3_1);
		myChart.setTransparent(true);
		myChart.render(chartPanel3_1.getId());

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// webServiceList
				// 1
				"myChartId", "160", "145");
		var xmlData_2 = createDataXml(webServiceList, 'net', 3);
		myChart.setDataXML(xmlData_2);
		myChart.setTransparent(true);
		myChart.render(chartPanel_2.getId());

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// webServiceList
				// 2
				"myChartId", "160", "145");
		var xmlData1_2 = createDataXml(webServiceList, 'cpu', 3);
		myChart.setDataXML(xmlData1_2);
		myChart.setTransparent(true);
		myChart.render(chartPanel1_2.getId());

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// webServiceList
				// 3
				"myChartId", "160", "145");
		var xmlData2_2 = createDataXml(webServiceList, 'ram', 3);
		myChart.setDataXML(xmlData2_2);
		myChart.setTransparent(true);
		myChart.render(chartPanel2_2.getId());

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// webServiceList
				// 4
				"myChartId", "160", "145");
		var xmlData3_2 = createDataXml(webServiceList, 'disk', 3);
		myChart.setDataXML(xmlData3_2);
		myChart.setTransparent(true);
		myChart.render(chartPanel3_2.getId());

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// interfaceServiceList
				// 1
				"myChartId", "160", "145");
		var xmlData_3 = createDataXml(interfaceServiceList, 'net', 4);
		myChart.setDataXML(xmlData_3);
		myChart.setTransparent(true);
		myChart.render(chartPanel_3.getId());

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// interfaceServiceList
				// 2
				"myChartId", "160", "145");
		var xmlData1_3 = createDataXml(interfaceServiceList, 'cpu', 4);
		myChart.setDataXML(xmlData1_3);
		myChart.setTransparent(true);
		myChart.render(chartPanel1_3.getId());

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// interfaceServiceList
				// 3
				"myChartId", "160", "145");
		var xmlData2_3 = createDataXml(interfaceServiceList, 'ram', 4);
		myChart.setDataXML(xmlData2_3);
		myChart.setTransparent(true);
		myChart.render(chartPanel2_3.getId());

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// interfaceServiceList
				// 4
				"myChartId", "160", "145");
		var xmlData3_3 = createDataXml(interfaceServiceList, 'disk', 4);
		myChart.setDataXML(xmlData3_3);
		myChart.setTransparent(true);
		myChart.render(chartPanel3_3.getId());

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// databaseList
				// 1
				"myChartId", "160", "145");
		var xmlData_4 = createDataXml(databaseList, 'net', 5);
		myChart.setDataXML(xmlData_4);
		myChart.setTransparent(true);
		myChart.render(chartPanel_4.getId());

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// databaseList
				// 2
				"myChartId", "160", "145");
		var xmlData1_4 = createDataXml(databaseList, 'cpu', 5);
		myChart.setDataXML(xmlData1_4);
		myChart.setTransparent(true);
		myChart.render(chartPanel1_4.getId());

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// databaseList
				// 3
				"myChartId", "160", "145");
		var xmlData2_4 = createDataXml(databaseList, 'ram', 5);
		myChart.setDataXML(xmlData2_4);
		myChart.setTransparent(true);
		myChart.render(chartPanel2_4.getId());

		var myChart = new FusionCharts("./fusionCharts/MSArea.swf",// databaseList
				// 4
				"myChartId", "160", "145");
		var xmlData3_4 = createDataXml(databaseList, 'disk', 5);
		myChart.setDataXML(xmlData3_4);
		myChart.setTransparent(true);
		myChart.render(chartPanel3_4.getId());
	}

	// ????下面的 netPanelArray[i]
	var chartPanel = new Ext.Panel({
				border : false
			});

	var chartPanel1 = new Ext.Panel({
				border : false
			});

	var chartPanel2 = new Ext.Panel({
				border : false
			});

	var chartPanel3 = new Ext.Panel({
				border : false
			});

	var chartPanel_1 = new Ext.Panel({
				border : false
			});

	var chartPanel1_1 = new Ext.Panel({
				border : false
			});

	var chartPanel2_1 = new Ext.Panel({
				border : false
			});

	var chartPanel3_1 = new Ext.Panel({
				border : false
			});

	var chartPanel_2 = new Ext.Panel({
				border : false
			});

	var chartPanel1_2 = new Ext.Panel({
				border : false
			});

	var chartPanel2_2 = new Ext.Panel({
				border : false
			});

	var chartPanel3_2 = new Ext.Panel({
				border : false
			});

	var chartPanel_3 = new Ext.Panel({
				border : false
			});

	var chartPanel1_3 = new Ext.Panel({
				border : false
			});

	var chartPanel2_3 = new Ext.Panel({
				border : false
			});

	var chartPanel3_3 = new Ext.Panel({
				border : false
			});

	var chartPanel_4 = new Ext.Panel({
				border : false
			});

	var chartPanel1_4 = new Ext.Panel({
				border : false
			});

	var chartPanel2_4 = new Ext.Panel({
				border : false
			});

	var chartPanel3_4 = new Ext.Panel({
				border : false
			});

	// ******************************************************************点击"实时工况"按钮弹出窗口
	// 开始

	// 分布式缓存调用：TaskHandle taskHandle = TaskHandle.getSharedInstance("wangjun");
	// 网关机信息返回：List<MonitorInfoBean> list = taskHandle .getMonitorInfo();
	// MonitorInfoBean信息：for(MonitorInfoBean mib:list)?????

	function ssgkShowFunc(type,title,btn) {
		var now = new Date().getHours();
		var ipAddrArray = new Array();
		var num = 0;
		Ext.Ajax.request({
								url : "runman/queryLMasterStationRes!queryLMasterStationRes.action",
								params : {
									msDate :new Date()
								},
								success : function(response) {
									btn.enable();
				var result = Ext.decode(response.responseText);
				list=result[type];
			for (var i = 1; i < list.length; i++) {
			var forBean = list[i];
			if (forBean.monitorTime == now) {
				ipAddrArray[num] = forBean;
				num++;
			}
		}
		// 数组??
		var ipAddrPanelArray = new Array(ipAddrArray.length);

		var netXmlDataArray = new Array(ipAddrArray.length);
		var cpuXmlDataArray = new Array(ipAddrArray.length);
		var memoryXmlDataArray = new Array(ipAddrArray.length);
		var diskXmlDataArray = new Array(ipAddrArray.length);

		var netPanelArray = new Array(ipAddrArray.length);
		var cpuPanelArray = new Array(ipAddrArray.length);
		var memoryPanelArray = new Array(ipAddrArray.length);
		var diskPanelArray = new Array(ipAddrArray.length);

		var netChart = new Array(ipAddrArray.length);
		var cpuChart = new Array(ipAddrArray.length);
		var memoryChart = new Array(ipAddrArray.length);
		var diskChart = new Array(ipAddrArray.length);
		if (ipAddrArray[0] == null || ipAddrArray[0].ipAddr == null) {// 当前时刻是否获取节点IP
			Ext.MessageBox.minWidth = 100;// 控制窗口大小
			Ext.Msg.alert("提示", "无数据");
			return;
		}

		for (var i = 0; i < ipAddrArray.length; i++) {
			var ipAddrBean = ipAddrArray[i];

			// 网络使用率
			netPanelArray[i] = new Ext.Panel({
						border : false,
						height : 160,
						layout : 'fit'
					});

			// CPU使用率
			cpuPanelArray[i] = new Ext.Panel({
						border : false,
						height : 160,
						layout : 'fit'
					});

			// 内存使用率
			memoryPanelArray[i] = new Ext.Panel({
						border : false,
						height : 160,
						layout : 'fit'
					});

			// 硬盘使用率
			diskPanelArray[i] = new Ext.Panel({
						border : false,
						height : 160,
						layout : 'fit'
					});

			// 根据取到的节点数显示各节点panel
			ipAddrPanelArray[i] = new Ext.Panel({

				title : '节点：' + ipAddrBean.ipAddr,// 取节点IP
				autoWidth : true,
				autoScroll : true,
				height : 200,
				// stripeRows : true,颜色间隔
				border : false,
				layout : 'column',
				items : [{
							columnWidth : .23,
							border : false,
							bodyStyle : 'padding:8px 0px 0px 10px',
							items : [netPanelArray[i]]
						}, {
							columnWidth : .22,
							border : false,
							bodyStyle : 'padding:8px 0px 0px 0px',
							items : [cpuPanelArray[i]]
						}, {
							columnWidth : .22,
							border : false,
							bodyStyle : 'padding:8px 0px 0px 0px',
							items : [memoryPanelArray[i]]
						}, {
							columnWidth : .2,
							border : false,
							bodyStyle : 'padding:8px 0px 0px 0px',
							items : [diskPanelArray[i]]
						}, {
							border : false,
							columnWidth : .11,
							layout : 'form',
							items : [{
										xtype : 'panel',
										border : false,
										bodyStyle : 'padding:60px 0px 0px 30px',
										html : " <font size=2px color= red>■ 使用</font>"
									}, {
										xtype : 'panel',
										border : false,
										bodyStyle : 'padding:20px 0px 0px 30px',
										html : " <font size=2px color= blue>■ 未使用</font>"
									}]
						}]
			});
		}

		// 实时工况 上层小字
		var panel_ssgk = new Ext.Panel({
					autoWidth : true,
					height : 50,
					border : false,
					bodyStyle : 'padding:20px 0px 0px 0px',
					layout : 'column',
					items : [{
								columnWidth : .23,
								border : false,
								bodyStyle : 'padding:0px 0px 0px 70px',
								items : [{
											xtype : 'label',
											text : '网络使用率'
										}]
							}, {
								columnWidth : .22,
								border : false,
								bodyStyle : 'padding:0px 0px 0px 60px',
								items : [{
											xtype : 'label',
											text : 'CPU使用率'
										}]
							}, {
								columnWidth : .22,
								border : false,
								bodyStyle : 'padding:0px 0px 0px 60px',
								items : [{
											xtype : 'label',
											text : '内存使用率'
										}]
							}, {
								columnWidth : .2,
								border : false,
								bodyStyle : 'padding:0px 0px 0px 60px',
								items : [{
											xtype : 'label',
											text : '硬盘使用率'
										}]
							}]
				});
		var window_ssgk = new Ext.Window({
					name : 'window_ssgk',
					title : title,
					width : 900,
					height : 480,
					resizable : false,// 拖动柄缩放
					autoScroll : true,
					closeAction : 'hide',
					layout : 'form',
					items : [panel_ssgk, ipAddrPanelArray]
				});

		window_ssgk.show();

		for (var i = 0; i < ipAddrArray.length; i++) { // XML 应用在java中：外"内'
			// 或者：外'内"
			var ipAddrBean = ipAddrArray[i];
			// bgColor='FFFFFF' 背景色 showBorder='0'
			netXmlDataArray[i] = "<graph caption='' xAxisName='' yAxisName='' showNames='0' showValues='0' bgColor='FFFFFF' showBorder='0' decimalPrecision='0' formatNumberScale='0'>"
					+ "<set name='使用' value='"
					+ ipAddrBean.netUseRatio
					+ "' color='#FF0000' />"
					+ "<set name='未使用' value='"
					+ (100 - ipAddrBean.netUseRatio)
					+ "' color='#0000FF' />"
					+ "</graph>";
			netChart[i] = new FusionCharts("./fusionCharts/Pie2D.swf",
					"netChartId", "180", "160");
			netChart[i].setDataXML(netXmlDataArray[i]);
			netChart[i].setTransparent(true);
			netChart[i].render(netPanelArray[i].getId());

			cpuXmlDataArray[i] = "<graph caption='' xAxisName='' yAxisName='' showNames='0' showValues='0' bgColor='FFFFFF' showBorder='0' decimalPrecision='0' formatNumberScale='0'>"
					+ "<set name='使用' value='"
					+ ipAddrBean.cpuUseRatio
					+ "' color='#FF0000' />"
					+ "<set name='未使用' value='"
					+ (100 - ipAddrBean.cpuUseRatio)
					+ "' color='#0000FF' />"
					+ "</graph>";
			cpuChart[i] = new FusionCharts("./fusionCharts/Pie2D.swf",
					"cpuChartId", "180", "160");
			cpuChart[i].setDataXML(cpuXmlDataArray[i]);
			cpuChart[i].setTransparent(true);
			cpuChart[i].render(cpuPanelArray[i].getId());

			memoryXmlDataArray[i] = "<graph caption='' xAxisName='' yAxisName='' showNames='0' showValues='0' bgColor='FFFFFF' showBorder='0' decimalPrecision='0' formatNumberScale='0'>"
					+ "<set name='使用' value='"
					+ ipAddrBean.memoryUseRatio
					+ "' color='#FF0000' />"
					+ "<set name='未使用' value='"
					+ (100 - ipAddrBean.memoryUseRatio)
					+ "' color='#0000FF' />" + "</graph>";
			memoryChart[i] = new FusionCharts("./fusionCharts/Pie2D.swf",
					"memoryChartId", "180", "160");
			memoryChart[i].setDataXML(memoryXmlDataArray[i]);
			memoryChart[i].setTransparent(true);
			memoryChart[i].render(memoryPanelArray[i].getId());

			diskXmlDataArray[i] = "<graph caption='' xAxisName='' yAxisName='' showNames='0' showValues='0' bgColor='FFFFFF' showBorder='0' decimalPrecision='0' formatNumberScale='0'>"
					+ "<set name='使用' value='"
					+ ipAddrBean.diskUseRatio
					+ "' color='#FF0000' />"
					+ "<set name='未使用' value='"
					+ (100 - ipAddrBean.diskUseRatio)
					+ "' color='#0000FF' />"
					+ "</graph>";
			diskChart[i] = new FusionCharts("./fusionCharts/Pie2D.swf",
					"diskChartId", "180", "160");
			diskChart[i].setDataXML(diskXmlDataArray[i]);
			diskChart[i].setTransparent(true);
			diskChart[i].render(diskPanelArray[i].getId());
		}
									// var result = Ext
									// .decode(response.responseText);
									// loadF(result);
									
								}
							});
	

		

	}
	// *******************************************************弹出"实时工况"按钮页面窗口 结束

	var panel2 = new Ext.Panel({ // 下方"网关机"集群图panel
		autoWidth : true,
		height : 160,
		layout : 'column',
		border : false,
		items : [{
					columnWidth : .07,
					labelAlign : 'right',
					bodyStyle : 'padding:20px 40px 0px 35px',
					border : false,
					items : [{
								xtype : 'label',
								text : '网关机集群'

							}]
				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel]

				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel1]
				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel2]
				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel3]
				}, {
					columnWidth : .11,
					border : false,
					bodyStyle : 'padding:50px 0px 0px 0px',
					items : [{
								xtype : 'button',
								text : '实时工况',
								handler : function() {
									ssgkShowFunc(wgjList);
								}

							}
					// ,{
					// xtype : 'button',
					// text : '*实时工况*',
					// handler : function() {
					// Ext.Ajax.request({
					// url:'runman/queryLMasterStationRes!querySsgk.action',
					// params : {
					// jqlx:wgjList
					// },
					// success : function(response){
					// var wgjList = Ext.decode(response.responseText).wgjList;
					// ssgkShowFunc(wgjList);
					// }
					// });
					//
					// }
					// }
					]
				}]
	});

	var panel3 = new Ext.Panel({ // 下方"采集机"集群图panel
		autoWidth : true,
		height : 160,
		layout : 'column',
		border : false,
		items : [{
					columnWidth : .07,
					labelAlign : 'right',
					bodyStyle : 'padding:20px 40px 0px 35px',
					border : false,
					items : [{
								xtype : 'label',
								text : '采集机集群'
							}]
				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel_1]
				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel1_1]
				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel2_1]
				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel3_1]
				}, {
					columnWidth : .11,
					border : false,
					bodyStyle : 'padding:50px 0px 0px 0px',
					items : [{
								xtype : 'button',
								text : '实时工况',
								handler : function() {
									ssgkShowFunc(cjjList);
								}

							}]
				}]

	});

	var panel4 = new Ext.Panel({ // 下方"web服务"集群图panel
		autoWidth : true,
		height : 160,
		layout : 'column',
		border : false,
		items : [{
					columnWidth : .07,
					labelAlign : 'right',
					bodyStyle : 'padding:20px 40px 0px 35px',
					border : false,
					items : [{
								xtype : 'label',
								text : 'web服务集群'
							}]
				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel_2]
				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel1_2]
				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel2_2]
				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel3_2]
				}, {
					columnWidth : .11,
					border : false,
					bodyStyle : 'padding:50px 0px 0px 0px',
					items : [{
								xtype : 'button',
								text : '实时工况',
								handler : function() {
									ssgkShowFunc(webServiceList);
								}

							}]
				}]
	});

	var panel5 = new Ext.Panel({ // 下方"接口服务"集群图panel
		autoWidth : true,
		height : 160,
		layout : 'column',
		border : false,
		items : [{
					columnWidth : .07,
					labelAlign : 'right',
					bodyStyle : 'padding:10px 40px 0px 35px',
					border : false,
					items : [{
								xtype : 'label',
								text : '接口服务集群'
							}]
				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel_3]
				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel1_3]
				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel2_3]
				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel3_3]
				}, {
					columnWidth : .11,
					border : false,
					bodyStyle : 'padding:50px 0px 0px 0px',
					items : [{
								xtype : 'button',
								text : '实时工况',
								handler : function() {
									ssgkShowFunc(interfaceServiceList);
								}

							}]
				}]
	});

	var panel6 = new Ext.Panel({ // 下方"数据库"集群图panel
		autoWidth : true,
		height : 160,
		layout : 'column',
		border : false,
		items : [{
					columnWidth : .07,
					labelAlign : 'right',
					bodyStyle : 'padding:20px 40px 0px 35px',
					border : false,
					items : [{
								xtype : 'label',
								text : '数据库集群'
							}]
				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel_4]
				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel1_4]
				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel2_4]
				}, {
					columnWidth : .2,
					border : false,
					items : [chartPanel3_4]
				}, {
					columnWidth : .11,
					border : false,
					bodyStyle : 'padding:50px 0px 0px 0px',
					items : [{
								xtype : 'button',
								text : '实时工况',
								handler : function() {
									ssgkShowFunc(databaseList);
								}

							}]
				}]
	});

	// --------------------------------------------------------------------顶层panel
	// 开始

	var panel = new Ext.Panel({
		region : 'north',
		bodyStyle : 'padding:10px 0px 0px 0px',
		height : 45,
		layout : 'column',
		items : [{
					columnWidth : .3,
					border : false,
					labelSeparator : ' ',
					labelAlign : 'right',
					labelWidth : 50,
					layout : 'form',
					items : [{
								xtype : 'datefield',
								format : "Y-m-d",
								editable:false,
								bodyStyle : 'padding:0px 70px 0px 0px',
								value : new Date(),
								maxValue : new Date(),
								width : 100,
								id : 'main_startDate',// id名称通用，定义特殊一些，name作用于items内
								// Ext.getCmp("main_startDate").getValue()
								fieldLabel : '日期'
							}]
				},
				// {
				// columnWidth : .08,
				// border : false,
				// items : [{
				// xtype : 'button',
				// text : '←',
				// handler : function() {
				// var date = Ext.getCmp('main_startDate').getValue();
				// Ext.getCmp('main_startDate').setValue(date.add(Date.DAY,-1));
				// },
				// width : 40
				// }]
				// }, {
				// columnWidth : .15,
				// border : false,
				// items : [{
				// xtype : 'button',
				// text : '→',
				// handler : function() {
				// var date = Ext.getCmp('main_startDate').getValue();
				// Ext.getCmp('main_startDate').setValue(date.add(Date.DAY,1));
				// },
				// width : 40
				// }]
				// },
				{
					columnWidth : .2,
					border : false,
					items : [{
						xtype : 'button',
						text : '查询',
						handler : function() {
							doFind();
						},
						width : 80
					}]
				}, {
					columnWidth : .2,
					border : false,
					items : [{
								xtype : "button",
								text : "实时工况",
								handler : function() {
							this.disable();
//						wgjList = result.wgjList;
//						cjjList = result.cjjList;
//						webServiceList = result.webServiceList;
//						interfaceServiceList = result.interfaceServiceList;
//						databaseList = result.databaseList;
									var mt = masterTabPanel.getActiveTab();
									var type = "";
									if (mt == netPanel) {
										type = "wgjList";
										ssgkShowFunc(type,"网关机集群实时工况",this)
									} else if (mt == collectPanel) {
										type = "cjjList";
										ssgkShowFunc(type,"采集器集群实时工况",this)
									} else if (mt == webPanel) {
										type = "webServiceList";
										ssgkShowFunc(type,"web服务器集群实时工况",this)
									} else if (mt == interfacePanel) {
										type = "interfaceServiceList";
										ssgkShowFunc(type,"接口服务器集群实时工况",this)
									} else if (mt == dataBasePanel) {
										type = "databaseList";
										ssgkShowFunc(type,"数据库集群实时工况",this)
									}
									// netPanel,collectPanel,webPanel,
									// interfacePanel,dataBasePanel
								}
							}]
				}]
	});

	// -----------------------------------------------------------------顶层panel
	// 结束

	var panel1 = new Ext.Panel({ // 下方panel第一行 小文字
		autoWidth : true,
		height : 40,
		border : false,
		bodyStyle : 'padding:10px 0px 0px 0px',
		layout : 'column',
		items : [{
					columnWidth : .07, // -----隐藏 、添加空白 layout：form
					border : false,
					baseCls : "x-plain",
					layout : 'form',
					style : 'padding:0px 0px 0px 25px',
					items : [{
								xtype : 'textfield',
								name : 'templateId',
								fieldLabel : '',
								hidden : true,
								labelSeparator : '',
								anchor : '100%'
							}]
				}, {
					columnWidth : .2,
					border : false,
					bodyStyle : 'padding:0px 0px 0px 45px',
					items : [{
								xtype : 'label',
								text : '网络使用率'
							}]
				}, {
					columnWidth : .2,
					border : false,
					bodyStyle : 'padding:0px 0px 0px 45px',
					items : [{
								xtype : 'label',
								text : 'CPU使用率'
							}]
				}, {
					columnWidth : .2,
					border : false,
					bodyStyle : 'padding:0px 0px 0px 45px',
					items : [{
								xtype : 'label',
								text : '内存使用率'
							}]
				}, {
					columnWidth : .2,
					border : false,
					bodyStyle : 'padding:0px 0px 0px 45px',
					items : [{
								xtype : 'label',
								text : '硬盘使用率'
							}]
				}]

	});

	// 黄轩加入
	// 网关机集群
	var netPanel = new Ext.Panel({
				border : false,
				layout : "fit",
				items : [new Ext.Panel({border:false,height:800})],
				title : "网关机集群"
			});
	// 采集机集群
	var collectPanel = new Ext.Panel({
				border : false,
				layout : "fit",
				items : [new Ext.Panel({border:false})],
				title : "采集机集群"
			});
	// web服务集群

	var webPanel = new Ext.Panel({
				border : false,
				layout : "fit",
				title : "web服务集群",
				items : [new Ext.Panel({border:false})]
			});
	// 接口服务集群
	var interfacePanel = new Ext.Panel({
				border : false,
				layout : "fit",
				items : [new Ext.Panel({border:false})],
				title : "接口服务集群"
			});
	// 数据库集群
	var dataBasePanel = new Ext.Panel({
				border : false,
				layout : "fit",
				items : [new Ext.Panel({border:false})],
				title : "数据库集群"
			});

	var masterTabPanel = new Ext.TabPanel({
				border : false,
				activeItem : 0,
				region : "center",
				items : [netPanel, collectPanel, webPanel, interfacePanel,
						dataBasePanel],
				listeners : {
					afterrender : function() {
						doFind();
					}
				}

			});
	var panel_low = new Ext.Panel({ // 下方很多图的大panel集合
		autoScroll : true,
		region : 'center',
		layout : 'form',
		items : [panel1, panel2, panel3, panel4, panel5, panel6]
	});

	var viewPanel = new Ext.Panel({ // 整个视图
		// layout : 'form',
		layout : 'border',
		border : false,
		items : [panel, masterTabPanel]
	});
	function onload() {
		Ext.Ajax.request({
			url : "runman/queryLMasterStationRes!queryLMasterStationRes.action",
			params : {
				msDate : Ext.getCmp("main_startDate").getValue()
			},
			success : function(response) {

				var result = Ext.decode(response.responseText);

				loadF(result);
			}
		});
	};
	// onload();
	
	function doFind() {
		masterTabPanel.getActiveTab().getEl().mask("数据加载中...");
		Ext.Ajax.request({
			url : "runman/queryLMasterStationRes!queryLMasterStationRes.action",
			params : {
				msDate : Ext.getCmp("main_startDate").getValue()
			},
			success : function(response) {
				masterTabPanel.getActiveTab().getEl().unmask();
				var result = Ext.decode(response.responseText);
				// 得到原始的数据
				wgjList = result.wgjList;
				cjjList = result.cjjList;
				webServiceList = result.webServiceList;
				interfaceServiceList = result.interfaceServiceList;
				databaseList = result.databaseList;
				// 对数据进行处理，来组合按ip来分组的形式的数据

				wgjMap = separateIp(wgjList, "ipAddr");
				cjjMap = separateIp(cjjList, "ipAddr");
				webServiceMap = separateIp(webServiceList, "ipAddr");
				interfaceServiceMap = separateIp(interfaceServiceList, "ipAddr");
				databaseMap = separateIp(databaseList, "ipAddr");
				showPanel(netPanel, wgjMap, 1);
				showPanel(collectPanel, cjjMap, 2);
				showPanel(webPanel, webServiceMap, 3);
				showPanel(interfacePanel, interfaceServiceMap, 4);
				showPanel(dataBasePanel, databaseMap, 5);
				// 通过数据来组装结果
				// var wgjP=createPanel(wgjMap,1);
				// createPanel(wgjMap,1);
				// createPanel(wgjMap,1);
				// createPanel(wgjMap,1);
				// createPanel(cjjMap,"cpu",1);
				// createPanel(webServiceMap,"ram",1);
				// createPanel(interfaceServiceMap,"disk",1);
				// createPanel(databaseMap,"net",1);

			}
		});
	}
	/***************************************************************************
	 * 通过数据和某个tab面板来生成面板的内容
	 **************************************************************************/
	function showPanel(panel, data, jqlx) {
		panel.removeAll();
//		var p = panel.getComponent("manyChart");
//		if (p) {
//			panel.remove(p, true);
//		}
		var flat = false;
		Ext.iterate(data, function(k, v) {
					flat = true;
					return false;
				});
		if (flat) {
			panel.add(createPanel(data, jqlx));
		} else {
			panel.add(new Ext.Panel({
						border : false,
						bodyCfg : {
							tag : "center"
						},
						bodyStyle : "margin:auto 50px;",
						html : "没有找到对应的集群"
					}));
		}
		panel.doLayout();
	}

	// ****实时刷新数据
	// var updateAlert = function() {
	// Ext.Ajax.request({
	// url : "runman/queryLMasterStationRes!queryLMasterStationRes.action",
	// params : {
	// msDate: Ext.getCmp("main_startDate").setValue(new Date())
	// },
	// success : function(response) {
	//				
	// var result = Ext.decode(response.responseText);
	//				
	// loadF(result);
	// }
	// });
	// }
	// var task = {
	// run : updateAlert,
	// interval : 1000 * 60 //刷新时间
	// }
	//
	// var runner = new Ext.util.TaskRunner();
	// runner.start(task);

	renderModel(viewPanel, '主站运行状态');
	var time=setInterval(function(){
		var d=Ext.getCmp("main_startDate");
	if((d.getValue()+"")==(new Date().clearTime()+"")){
		doFind();
	}
	},900000);
	Ext.getCmp('主站运行状态').on('destroy', function() {
				time && clearInterval(time);
			})
	
});
