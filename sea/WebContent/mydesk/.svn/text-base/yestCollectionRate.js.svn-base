//var gqe_keys = new Array("orgName", "succRate1", "succRate2", "succRate3");
//var gqe_nameKeys = new Array("抄表类", "负荷类", "电能质量类");
//var gqe_colors = new Array('AFD8F8', 'F6BD0F', '8BBA00');
var gqe_keys = new Array("orgName", "succRate1");
var gqe_nameKeys = new Array("抄表类");
var gqe_colors = new Array('AFD8F8');
var gqe_arrayData = [];

var rateStore = new Ext.data.JsonStore({
			totalProperty : 'totalCount',
			proxy : new Ext.data.MemoryProxy(),
			fields : ['orgNo', 'orgName', 'succRate1', 'failRate1',
					'succRate2', 'failRate2', 'succRate3', 'failRate3'],
			root : "aTmnlQualityList"
		});
function getBrowser() {
	var oType;
	if (navigator.userAgent.indexOf("MSIE") != -1) {
		oType = "IE"
	} else if (navigator.userAgent.indexOf("Firefox") != -1) {
		oType = "FIREFOX";
	}
	return oType;
}




var startDate = new Date().add(Date.DAY, -1).format('Y-m-d');

// function genQualityData (){
// Ext.Ajax.request({
// url:'baseapp/gatherQualityEvaluate!getGatherQualityInfo.action',
// params:{startDate:startDate,endDate:startDate},
// success:function(response) {
// var result = Ext.decode(response.responseText);
// if(result==null) return true;
// rateStore.loadData(result,false);
// gqe_arrayData = result.aTmnlQualityList;
//			
// ChangeCobo();
// }
// });
// }

var collectionChartPanel = new Ext.fc.FusionChart({
			border : false,
			title : '',
			wmode : 'transparent',
			backgroundColor : '000000',
			url : 'fusionCharts/MSColumn3D.swf?ChartNoDataText=无数据',
			DataXML : ""
		});
var collectionChartPanelCon = new Ext.Panel({
			layout : 'fit',
			items : [collectionChartPanel]
		});
var myChart;

function ChangeCobo() {
	var collectionXmlData = "<graph baseFont='宋体' baseFontSize='14' rotateYAxisName='0' xaxisname='' yaxisname='' "
			+ "hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' numdivlines='9' divLineColor='CCCCCC' "
			+ "divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' "
			+ "AlternateHGridColor='CCCCCC' caption='' subcaption='' ><categories font='Arial' "
			+ "fontSize='12' fontColor='000000'></categories><dataset seriesname='抄表类' color='AFD8F8'> </dataset></graph>"
			/*
			 * 除去标签 ：负荷类、电能质量类
			 */
			//+ "</dataset><dataset seriesname='负荷类' color='F6BD0F'> </dataset><dataset seriesname='电能质量类'"
			//+ " color='8BBA00'> </dataset></graph>";
	if (gqe_arrayData.length != 0) {
		collectionXmlData = getMultiXMLData_1(gqe_arrayData, gqe_keys, '', '',
				'', gqe_colors, gqe_nameKeys, 5, 0, 100);
	}

	// myChart = new FusionCharts("./fusionCharts/MSColumn3D.swf",
	// "myChartId", "320", "265");
	// myChart.setDataXML(xmlData);
	// myChart.setTransparent(true);
	// if (chartPanel != null)
	// myChart.render(chartPanel);
	// renderModel(chartPanel,3);

	renderModel(collectionChartPanelCon, 3);
	collectionChartPanel.changeDataXML(collectionXmlData);
	// myChart.render("yestCollectionRate");
}

Ext.onReady(function() {
	// Ext.QuickTips.init();
	// genQualityData();

	Ext.Ajax.request({
				url : 'baseapp/gatherQualityEvaluate!findGatherQualityInfo.action',
				params : {
					startDate : startDate,
					endDate : startDate
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (result == null)
						return true;
					rateStore.loadData(result, false);
					//处理数据
					gqe_arrayData = result.aTmnlQualityList;

					ChangeCobo();
				}
			});

})