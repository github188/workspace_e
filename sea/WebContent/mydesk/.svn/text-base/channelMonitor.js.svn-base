Ext.onReady(function(){

	var monitorChartPanel = new Ext.fc.FusionChart({
					border : false,
	                title:'',
					wmode : 'transparent',
					backgroundColor : '000000',
					url : 'fusionCharts/StackedColumn2D.swf',
					DataXML : ""
				});	
	
	var monitorXmlData = ' numberprefix=""' +
			' xaxisName=""' +
			' useRoundEdges="1"' +
			' showValues="0"' +
			' legendBorderAlpha="0"' +
			' baseFont="宋体" ' +
			' showvalues="0" ' +
			' formatNumberScale="0"' +
			' baseFontSize="12"> ';
	var category = '<categories>';
	var dataset = '<dataset seriesName="上线数" color="8BBA00" showValues="0">';
	var dataset1 = '<dataset seriesName="离线数" color="FF0000" showValues="0">';
	
	
	Ext.Ajax.request({
		url:'runman/queryChannelMonitor!queryChannelMonitorOrgNo.action',
		success : function(response){
			var result = Ext.decode(response.responseText);
			if(result==null) return true;
			var channelMonitorTerminaList = result.channelMonitorOrgNoList;
			
			var totalUpTermNum = 0;//总上线数
			var totalOffTermNum = 0;//总离线数
			var upRate = 0;//上线率
			
			for(var i = 0 ; i < channelMonitorTerminaList.length; i++){
				category += '<category label="'+channelMonitorTerminaList[i].orgName+'" />';
				dataset += '<set value="'+channelMonitorTerminaList[i].upTerminalNum+'"  showvalues="0" /> ';
				totalUpTermNum += parseInt(channelMonitorTerminaList[i].upTerminalNum);
				dataset1 += '<set value="'+channelMonitorTerminaList[i].offTerminalNum+'" showvalues="0" /> ';
				totalOffTermNum += parseInt(channelMonitorTerminaList[i].offTerminalNum);
			}
			
			upRate = totalUpTermNum/(totalUpTermNum + totalOffTermNum)*100;
			upRate = upRate.toString().substring(0,5) + "%";
			category += '</categories>';
			dataset += '</dataset>';
			dataset1 += '</dataset>';
			monitorXmlData = monitorXmlData + category + dataset1 + dataset + '</chart>';
			
			monitorXmlData = '<chart palette="2" caption="上线数['+totalUpTermNum+'] 离线数['+totalOffTermNum+'] 上线率['+upRate+']"' + monitorXmlData;
			monitorChartPanel.setDataXML(monitorXmlData);	
			renderModel(monitorChartPanel,'6');
		},
		failure : function(){
 		    Ext.MessageBox.alert("提示","失败");
 		    return;
 		}
	});
});

