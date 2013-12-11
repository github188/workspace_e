var onlineConsTitle;
var tipString;
// 加载图像数据 store onlineCons

var onlineConsChartGridStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'runman/queryOnlineMonitor!queryOnOffLineCons.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'onofflineConsList',
						totalProperty : 'totalCount'
					}, [{
								name : 'orgName'
							}, {
								name : 'staffNo'
							}, {
								name : 'staffName'
							}, {
								name : 'isOnline'
							}, {
								name : 'boundIp'
							}])
		});

// 图片onlineConsChart
var onlineConsChart = new Ext.fc.FusionChart({
			border : false,
			region : 'center',
			wmode : 'transparent',
			backgroundColor : '000000',
			url : 'fusionCharts/ScrollStackedColumn2D.swf',
			DataXML : ""
		});

onlineConsChartGridStore.on("load", function(o, arr) {
			var xmlData = getonlineConsXMLData(onlineConsChartGridStore.data);
			onlineConsChart.changeDataXML(xmlData);
			groupPanel.setTitle(onlineConsTitle);
		});

onlineConsChartGridStore.load();

// 列表 onlineConsChartGrid
// cm
var onlineConsCM = new Ext.grid.ColumnModel([{
			header : '供电单位',
			dataIndex : 'orgName',
			width : 120,
			sortable : true,
			align : 'center'
		}, {
			header : '工号',
			dataIndex : 'staffNo',
			width : 100,
			sortable : true,
			align : 'center'
		}, {
			header : '工号名称',
			dataIndex : 'staffName',
			width : 200,
			sortable : true,
			align : 'center'

		}, {
			header : '是否在线',
			dataIndex : 'isOnline',
			align : 'center',
			width : 70,
			renderer : function(value) {
				if (value == "离线") {
					return "<font color='red'>离线</font>";
				} else if (value == "在线") {
					return "<font color='green'>在线</font>";
				}
			}
		}, {
			header : '绑定IP',
			dataIndex : 'boundIp',
			align : 'center',
			width : 200
		}]);

var onlineConsChartGrid = new Ext.grid.GridPanel({
			id : 'onlineConsChartGrid',
			region : 'east',
			width : 600,
			autoScroll : true,
			stripeRows : true,
			viewConfig : {
				forceFit : true
			},
			cm : onlineConsCM,
			ds : onlineConsChartGridStore,
			bbar : new Ext.ux.MyToolbar({
						store : onlineConsChartGridStore,
						enableExpAll : true
					})
		});
var onlineConsPanel = new Ext.Panel({
			region : 'center',
			layout : 'border',
			items : [onlineConsChart, onlineConsChartGrid]
		});

// 图表组合面板
var groupPanel = new Ext.Panel({
			region : 'center',
			title : '在线状态统计',
			border : false,
			layout : 'border',
			items : [onlineConsPanel]
		});

Ext.onReady(function() {
			// 通信信道监测页面
			var viewPanel = new Ext.Panel({
						layout : 'border',
						autoScroll : false,
						border : false,
						items : [groupPanel]
					});

			renderModel(viewPanel, '在线状态监测');

			var timeProgress = setInterval(function() {

						onlineConsChartGridStore.reload();
					}, 1000 * 60 * 2);

			Ext.getCmp('在线状态监测').on('destroy', function() {

						clearInterval(timeProgress);

					})

		});

function getonlineConsXMLData(json) {
	// 计算总上线数
	var UpSum = 0;
	// 计算总离线数
	var DownSum = 0;
	// 总上线率
	var Upscale;
	var xmlData = '<chart palette="2" caption="在线状态统计" shownames="1" showvalues="0" '
			+ ' numberPrefix="" showSum="1" decimals="0" useRoundEdges="1" formatNumberScale="0">';

	var str = "<categories>";
	str += '<category label="盘龙电力" />';
	str += "</categories>";
	for (var i = 0; i < json.length; i++) {
		var name = json.get(i).get('isOnline');
		if (name == "离线") {
			DownSum += 1;
		} else {
			UpSum += 1;
		}

	}

	var str2 = '<dataset seriesName="上线数" color="8BBA00" showValues="0">';

	str2 += "<set value='" + UpSum + "' />";

	str2 += "</dataset>";

	var str1 = '<dataset seriesName="离线数" color="FF0000" showValues="0">';

	str1 += "<set value='" + DownSum + "'/>";

	str1 += "</dataset>";

	xmlData = xmlData + str + str1 + str2 + "</chart>";

	// 计算上线率
	Upscale = UpSum / (UpSum + DownSum);
	Upscale = Upscale * 100;
	Upscale = Upscale.toString();
	Upscale = Upscale.substring(0, 4);

	onlineConsTitle = "在线状态统计  " + "<font color='red'> 在线数[ " + UpSum
			+ " ] 离线数[ " + DownSum + " ] 上线率 [ " + Upscale + "% ]</font>";
	return xmlData;

}