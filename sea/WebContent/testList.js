Ext.onReady(function() {
	var xmlData = '<chart palette="2" caption="Unit Sales" xAxisName="Month" yAxisName="Units" showValues="0" decimals="0" formatNumberScale="0" useRoundEdges="1">'
			+ '<set label="Jan" value="462" />'
			+ '<set label="Feb" value="857" />'
			+ '<set label="Mar" value="671" />'
			+ '<set label="Apr" value="494" />'
			+ '<set label="May" value="761" />'
			+ '<set label="Jun" value="960" />' + '</chart>';

	var northchart = new Ext.fc.FusionChart({
				wmode : 'transparent',
				backgroundColor : '000000',
				url : 'fusionCharts/Column3D.swf',
				DataXML : xmlData
			});
	var north = new Ext.Panel({
				title : '二维图',
				layout : 'fit',
				region : 'north',
				height : 200,
				items : [northchart]
			});

	var southchart = new Ext.fc.FusionChart({
				wmode : 'transparent',
				backgroundColor : '000000',
				url : 'fusionCharts/MSColumn3D.swf',
				// url : 'fusionCharts/Column3D.swf',
				// url : 'fusionCharts/StackedColumn3D.swf',
				// url : 'fusionCharts/ScrollColumn2D.swf',
				// DataXML : xmlData2
				// DataURL : 'YhData.xml'
				JsonData : // new Ext.fc.data.SingleSeriesData({
				new Ext.fc.data.MultiSeriesData({
							chartBack : new Ext.fc.xmlel.ChartBack({
										bgColor : 'dfe8f6'
									}),
							chartTrendLine : [new Ext.fc.xmlel.TrendLine({
												startValue : '600',
												endValue : '900',
												color : 'ff0000',
												displayvalue : '平均线'
											}), new Ext.fc.xmlel.TrendLine({
												startValue : '900',
												endValue : '600',
												color : '00ff00',
												displayvalue : '平均线'
											})],
							chartVLine : [new Ext.fc.xmlel.VLine({
												color : 'ffbb00',
												position : '1'
											}), new Ext.fc.xmlel.VLine({
												color : '00bbff',
												position : '0'
											})],
							chartStyles : [new Ext.fc.xmlel.ChartStyle({
										name : 'myBgAnim',
										type : 'Animation',
										param : '_alpha',
										start : '0',
										duration : '1',
										toObject : 'Background'
									})],
							dataJson : [{
										month : 'Feb',
										income : '671',
										payment : '784'
									}, {
										month : 'Mar',
										income : 854,
										payment : '654'
									}, {
										month : "Apr",
										income : 965,
										payment : '835'
									}],
							xData : 'month',
							// yData : 'income'
							yData : ['income', 'payment'],
							yDataLabel : ['收入', '支出']
						})

			});
	var south = new Ext.Panel({
				title : '多维图Json数据源',
				region : "south",
				height : 200,
				items : [southchart]
			});
	var xmlData2 = "<chart  bgColor='dfe8f6' > <set label='Feb' value='671' /><vLine  color='00bbff'  position='0'  /> <set label='Mar' value='854' /><vLine  color='ffbb00'  position='1'  /> <set label='Apr' value='965' /> <trendLines><line  startValue='600'  endValue='900'  color='ff0000'  displayvalue='平均线'  /><line  startValue='900'  endValue='600'  color='00ff00'  displayvalue='平均线'  /> </trendLines><styles>\n"
			+ "<definition>\n"
			+ "<style  name='myBgAnim'  type='Animation'  param='_alpha'  start='0'  duration='1' />\n"
			+ "</definition>\n"
			+ "<application>\n"
			+ "<apply toObject='Background' styles='myBgAnim' />\n"
			+ "</application>\n" + "</styles>\n" + "</chart>";

	var eastchart = new Ext.fc.FusionChart({
		wmode : 'transparent',
		backgroundColor : '000000',
		url : 'fusionCharts/Column2D.swf',
		// url : 'fusionCharts/Column3D.swf',
		// url : 'fusionCharts/StackedColumn3D.swf',
		// url : 'fusionCharts/ScrollColumn2D.swf',
		//DataXML : xmlData2
			// DataURL : 'YhData.xml'  //DataURL优先s
			 JsonData : new Ext.fc.data.SingleSeriesData({
					// new Ext.fc.data.MultiSeriesData({
					chartBack : new Ext.fc.xmlel.ChartBack({
								bgColor : 'dfe8f6'
							}),
					chartTrendLine : [new Ext.fc.xmlel.TrendLine({
										startValue : '600',
										endValue : '900',
										color : 'ff0000',
										displayvalue : '平均线'
									}), new Ext.fc.xmlel.TrendLine({
										startValue : '900',
										endValue : '600',
										color : '00ff00',
										displayvalue : '平均线'
									})],
					chartVLine : [new Ext.fc.xmlel.VLine({
										color : 'ffbb00',
										position : '1'
									}), new Ext.fc.xmlel.VLine({
										color : '00bbff',
										position : '0'
									})],
					chartStyles : [new Ext.fc.xmlel.ChartStyle({
								name : 'myBgAnim',
								type : 'Animation',
								param : '_alpha',
								start : '0',
								duration : '1',
								toObject : 'Background'
							})],
					dataJson : [{
								month : 'Feb',
								income : 671,
								payment : '784'
							}, {
								month : 'Mar',
								income : 854,
								payment : '654'
							}, {
								month : "Apr",
								income : 965,
								payment : '835'
							}],
					xData : 'month',
					yData : 'income'
						//yData : 'income'
					})

		});

	var east = new Ext.Panel({
				region : 'east',
				width : 200,
				title : '二维单列JSON数据源',
				items : [eastchart]
			});

	var west = new Ext.Panel({
				region : 'west',
				width : 200
			});

	var center = new Ext.Panel({
				region : 'center'
			});

	var panel = new Ext.Panel({
				layout : 'border',
				items : [north, south, east, west, center]
			})

	renderModel(panel, '测试模块');

});
