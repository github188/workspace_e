Ext.onReady(function(){

function randomColor() {
	return '' + (Math.random() * 0xffffff << 0).toString(16);
};
var upMonthPSDataXML = "";

var upMonthPSChartPanel = new Ext.fc.FusionChart({
			border : false,
			title : '',
			wmode : 'transparent',
			backgroundColor : '000000',
			url : 'fusionCharts/Pie3D.swf?ChartNoDataText=无数据',
			DataXML : ""
		});
Ext.Ajax.request({
		url:'mydesk/monthPowerStat!queryMonthPowerStat.action',
		params : {
					queryDate : new Date().add(Date.MONTH, -1).format('Y-m')
				},
		success : function(response){
			var result = Ext.decode(response.responseText);
			if(result==null) return true;
			var upMonthPSList = result.upMonthPSList;
			if(upMonthPSList == null){
				return null
			}
			upMonthPSDataXML = createXml(upMonthPSList);
			upMonthPSChartPanel.setDataXML(upMonthPSDataXML);	
			renderModel(upMonthPSChartPanel,'1');
		},
		failure : function(){
 		    Ext.MessageBox.alert("提示","失败");
 		    return;
 		}
	});
function createXml(upMonthPSList) {
		var totle = 0;
		var dss = [];
		Ext.each(upMonthPSList, function(a,i) {
			if(i){
				dss.push('<set toolText="'+a.elecType+" "+ Ext.util.Format.number(a.energyVal,'0,000.0000') +'" label="' + a.elecType + '" value="' + a.energyVal + '" />');
				totle += a.energyVal;
				return;
			}
			dss.push('<set toolText="'+a.elecType+" "+Ext.util.Format.number(a.energyVal,'0,000.0000')+'" label="' + a.elecType + '" value="' + a.energyVal + '"isSliced="1" />');
			totle += a.energyVal;
		});
		var rs = [
				'<chart palette="2" decimals="0" caption="总:'
						+ Ext.util.Format.number(totle,'0,000.00') 
						+ '(单位：万千瓦时)" chartLeftMargin="0" labelDistance="10"  isSmartLineSlanted="0" formatNumberScale="0" enableSmartLabels="1" enableRotation="0" bgColor="FFFFFF" bgAlpha="40,100" bgRatio="0,100" bgAngle="360" showValues="1" showBorder="0" startingAngle="70">'];
		rs = rs.concat(dss);
		rs.push("</chart>");
		return rs.join(""); 
}


});
