function randomColor() {
	return '' + (Math.random() * 0xffffff << 0).toString(16);
};

function getSDQMessageC() {
	var sendDataQueryConsNO = Ext.getCmp("sendDataQueryConsNO").getValue();
	DateStart = Ext.getCmp("sendDataQueryDateStartNext").getValue()
			.format('Y-m-d');
	DateStartX = Ext.getCmp("sendDataQueryDateStartNext").getValue();
	DateEndX = Ext.getCmp("sendDataQueryDateEndNext").getValue();
	DateEnd = Ext.getCmp("sendDataQueryDateEndNext").getValue().format('Y-m-d');
	Ext.Ajax.request({
				url : 'qrystat/sendDataQueryAction!querySendDataQueryC.action',
				params : {
					CONS_NO : sendDataQueryConsNO,
					DateStart : DateStart,
					DateEnd : DateEnd
				},
				success : function(response) {
					var result = Ext.decode(response.responseText).SDQMapD;
					var mydays = (DateEndX - DateStartX) / (1000 * 24 * 3600)
							+ 1;
					if (mydays > 31 || mydays <= 0) {
						alert("您选择的日期范围不合理,建议查询范围在一个月之间...");
					} else {
						var XMLstr = SDQXMLData1(result, DateStartX, DateEndX);
						// alert(XMLstr);
						fpanelChart22.changeDataXML(XMLstr);
						// fpanelChart2.render(fpanel2.getId());
					}
					addStr = '';
					deleteStr = '';

				}
			});
};
function getSDQEXMLData(dateData, step, dataDate) {
	var xmlData = "<chart lineThickness='1' showValues='0' formatNumberScale='0'"
			+ " anchorRadius='1' divLineAlpha='10' divLineColor='CC3300' "
			+ "divLineIsDashed='1' showAlternateHGridColor='1' "
			+ "alternateHGridAlpha='5' alternateHGridColor='CC3300'"
			+ " shadowAlpha='40' labelStep='"
			+ step
			+ "' numvdivlines='22' "
			+ "chartRightMargin='35' bgColor='FFFFFF,CC3300' "
			+ "bgAngle='270' bgAlpha='10,10'>";

	var str = "<categories>";
	Ext.each(dateData, function(obj) {
				str += "<category label='" + obj['time'] + "' />";
			});
	str += "</categories>";

	var str1 = "<dataset seriesName='' color='1D8BD1' anchorBorderColor='1D8BD1' anchorBgColor='1D8BD1'>";
	Ext.each(dateData, function(obj) {
		str1 += "<set value='" + obj['data'] + "'/>";
			// i++;
		});
	str1 += "</dataset>";

	xmlData = xmlData + str + str1 + "<styles><definition>"
			+ "<style name='CaptionFont' type='font' size='12' />"
			+ "</definition><application>"
			+ "<apply toObject='CAPTION' styles='CaptionFont' />"
			+ "<apply toObject='SUBCAPTION' styles='CaptionFont' />"
			+ "</application></styles></chart>";
	return xmlData;

};
function getline(asserNo, dataDate) {
	// alert(asserNo);
	// alert(dataDate);
	var sendDataQueryConsNO = Ext.getCmp("sendDataQueryConsNO").getValue();
	var url = "qrystat/sendDataQueryAction!queryMpEnergyData.action";
	Ext.Ajax.request({
				url : url,
				params : {
					CONS_NO : sendDataQueryConsNO,
					asserNo : asserNo,
					dataDate : dataDate
				},
				success : function(response) {
					// setPanelTitle();
					var result = Ext.decode(response.responseText);
					var dateResult = result.generalData;
					if (dateResult == null) {
						return;
					}
					var step = dateResult.length / 12;
					var dataXml = getSDQEXMLData(dateResult, step, dataDate);
					fpanelChart1.changeDataXML(dataXml);
					// renderModel(fpanelChart,fpanel.getId());
					// fpanelChart.render(fpanel.getId());
				}
			});
}
function SDQXMLData1(dateDataArray, date1, date2) {
	var resata1 = '<chart palette="2" caption="" shownames="1" showvalues="0" numberPrefix="" useRoundEdges="1" legendBorderAlpha="0">';
	var str = "<categories>";
	var days = (date2 - date1) / (1000 * 24 * 3600) + 1;
	for (var i = 0; i < days; i++) {
		str += "<category label='" + date1.add(Date.DAY, i).format("m月d日")
				+ "' />";
	}
	str += "</categories>";
	var str1 = "";
	for (var obj in dateDataArray) {
		str1 += "<dataset seriesName='" + dateDataArray[obj][0]['assetNo']
				+ "' color='" + randomColor() + "'>";
		for (var i = 0; i < days; i++) {
			var curDate = date1.add(Date.DAY, i);
			var flag = 0;
			for (var o in dateDataArray[obj]) {
				var mydataDate = dateDataArray[obj][o]['dataDate'];
				// alert(mydataDate);
				if (curDate.format('Y-m-d') == mydataDate) {
					str1 += "<set value='"
							+ (dateDataArray[obj][o]['papE'] == null
									? 0
									: dateDataArray[obj][o]['papE'])
							+ "'link='javaScript:onclick=getline("
							+ dateDataArray[obj][0]['assetNo'] + ",\""
							+ mydataDate + "\")'/>";
					flag = 1;
					break;
				}
			}
			if (flag != 1) {
				str1 += "<set value='0'/>";
			}
		}

		str1 += "</dataset>";
	}
	resata1 = resata1 + str + str1 + "</chart>";
	// alert(resata1);
	return resata1;
};
// -----------------------------------------------------------------
var sendDataQuerytop1 = new Ext.Panel({// 查询条件的panel
	plain : true,
	border : false,
	region : 'north',
	height : 30,
	items : [{
				baseCls : "x-plain",
				layout : "column",
				border : false,
				style : "padding:5px",
				items : [{
							columnWidth : .3,// -------------------
							layout : "form",
							style : "padding-left:50px",
							labelStyle : "text-align:right;width:80;",
							labelAlign : 'right',
							labelWidth : 60,
							defaultType : "textfield",
							baseCls : "x-plain",
							items : [{
										xtype : "datefield",
										format : "Y-m-d",
										value : new Date(),
										fieldLabel : "分析日期",
										id : 'sendDataQueryDateStartNext',
										value : new Date().add(Date.DAY, -2)
									}]
						}, {
							columnWidth : .3,// -------------------
							layout : "form",
							style : "padding-left:10px",
							labelStyle : "text-align:right;width:80;",
							labelAlign : 'right',
							labelWidth : 20,
							defaultType : "textfield",
							baseCls : "x-plain",
							items : [{
										xtype : "datefield",
										format : "Y-m-d",
										value : new Date(),
										fieldLabel : "到",
										id : 'sendDataQueryDateEndNext',
										value : new Date().add(Date.DAY, -1)
									}]
						}, {
							columnWidth : .3,// ------------------
							layout : "form",
							style : "padding-left:100px",
							defaultType : "button",
							baseCls : "x-plain",
							items : [{
										text : "查询",
										width : 50,
										listeners : {
											"click" : function() {
												getSDQMessageC();
											}
										}
									}]
						}]
			}]
});

var fpanelChart22 = new Ext.fc.FusionChart({

			border : false,
			title : '',
			wmode : 'transparent',
			backgroundColor : '000000',
			url : 'fusionCharts/ScrollColumn2D.swf',
			DataXML : ""
		});

var fpanelChart2 = new Ext.Panel({
			region : 'center',
			// height:200,
			items : [fpanelChart22]
		});
// var fpanel = new Ext.Panel({
// //region : 'center',
// plain : true,
// border : false,
// height:200,
// bodyBorder : false,
// // layout : 'fit',
// monitorResize : true
// });
var fpanelChart1 = new Ext.fc.FusionChart({
			wmode : 'transparent',
			backgroundColor : '000000',
			url : 'fusionCharts/MSLine.swf',
			DataXML : ""
		});

var fpanelChart = new Ext.Panel({
			height : 250,
			monitorResize : true,
			region : 'south',
			items : [fpanelChart1]
		});
// fChart.setDataURL("./qryStat/queryCollPoint/fDate.xml");
// fChart.setDataXML(xmlData);
// sendDataQuerywindow--------------------------
// var fpanel2 = new Ext.Panel({
// height:200,
// plain : true,
// border : false,
// //region : 'west',
// bodyBorder : false,
// // layout : 'fit',
// monitorResize : true
// });

// 设置顶层的抄表数据查询panel
var sendDataQueryTableNopanel = new Ext.Panel({
			autoScroll : true,
			border : false,
			layout : "border",
			items : [sendDataQuerytop1, fpanelChart2, fpanelChart]
		});
renderModel(sendDataQueryTableNopanel, '冻结电能量分析');
Ext.getCmp("sendDataQueryDateStartNext").setValue(Ext
		.getCmp("sendDataQueryDateStart").getValue());
Ext.getCmp("sendDataQueryDateEndNext").setValue(Ext
		.getCmp("sendDataQueryDateEnd").getValue());
getSDQMessageC();
// fpanelChart.render(fpanel.getId());
// renderModel(fpanelChart,fpanel.getId());
