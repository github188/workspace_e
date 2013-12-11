Ext.onReady(function(){

function randomColor() {
	return '' + (Math.random() * 0xffffff << 0).toString(16);
};
var YPDxmlData = "";
var papE = "";

function getYPDXMLData(dateData) {
	var xmlData = "";
	if(dateData != null && dateData != ''){
		Ext.each(dateData, function(obj) {
					papE = obj['papE'];
			xmlData = '<chart palette="4" decimals="0" caption="总:'+papE+'(单位：万千瓦时)"  formatNumberScale="0" enableSmartLabels="1" enableRotation="0" bgColor="FFFFFF" bgAlpha="40,100" bgRatio="0,100" bgAngle="360" showBorder="0" startingAngle="70">';
//						xmlData += "<set label='总' value=\""
//								+ obj['papE'] + "\"  isSliced=\"1\"/>";
//								papE = obj['papE']+"千瓦时";
//								xmlData += "<set label='尖' value=\""
//								+ obj['papE1'] + "千瓦时\"  isSliced=\"1\"/>";
//								xmlData += "<set label='峰' value=\""
//								+ obj['papE2'] + "千瓦时\"  isSliced=\"1\"/>";
//								xmlData += "<set label='平' value=\""
//								+ obj['papE3'] + "千瓦时\"  isSliced=\"1\"/>";
//								xmlData += "<set label='谷' value=\""
//								+ obj['papE4'] + "千瓦时\"  isSliced=\"1\"/>";
			//暂时将尖峰电量显示删除开始	
//			xmlData += "<set label='尖' value=\""
//			+ obj['papE1'] + "\"  isSliced=\"1\"/>";
			//暂时将尖峰电量显示删除结束
			xmlData += "<set label='峰' value=\""
			+ obj['papE2'] + "\"  isSliced=\"1\"/>";
			xmlData += "<set label='平' value=\""
			+ obj['papE3'] + "\"  isSliced=\"1\"/>";
			xmlData += "<set label='谷' value=\""
			+ obj['papE4'] + "\"  isSliced=\"1\"/>";
								
				});
		xmlData += "</chart>";
		//yestPDpanel.setTitle('总：'+papE+'(单位：千瓦时)');
	
	}else{
		xmlData = '<chart palette="4" decimals="0" caption=""  formatNumberScale="0" enableSmartLabels="1" enableRotation="0" bgColor="FFFFFF" bgAlpha="40,100" bgRatio="0,100" bgAngle="360" showBorder="0" startingAngle="70"></chart>';;
	}
	yestPDpanel.changeDataXML(xmlData);
};
function getYPD() {
	var url = "qrystat/aOrgStatDAction!queryAOrgStatD.action";
	Ext.Ajax.request({
				url : url,
				params : {},
				success : function(response) {
					// setPanelTitle();
					var result = Ext.decode(response.responseText);
					YPDxmlData = result.aOrgStatDList;
					if (YPDxmlData == null) {
						return true;
					}
					
					getYPDXMLData(YPDxmlData);
				}
			});
}
var yestPDpanel = new Ext.fc.FusionChart({
			border : false,
			title : '',
			wmode : 'transparent',
			backgroundColor : '000000',
			url : 'fusionCharts/Pie3D.swf?ChartNoDataText=无数据',
			DataXML : ""
		});

//var YPDTopPanel = new Ext.Panel({
//			title:'',
//			border : false,
//			height : 270,
//			baseCls : "x-plain",
//			items : [yestPDpanel]
//		});
//
//YPDTopPanel.render("yestPowerDist");
renderModel(yestPDpanel,'1');
getYPD();
});
