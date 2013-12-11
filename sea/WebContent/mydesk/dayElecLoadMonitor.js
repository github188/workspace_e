Ext.onReady(function(){

var date = new Date();
var hour = date.getHours();
	
function getDELM() {
	Ext.Ajax.request({
		url : './qrystat/powerSortAnalyse!getOOrgById.action',
		success : function(response){
			var result = Ext.decode(response.responseText);
			var org = result.org;
			orgList = new Array();
			orgList[0] = org.orgNo+ "!" + org.orgType + "!" + org.orgName;
			
			Ext.Ajax.request({
				url:'./qrystat/currLoadMonitor!loadGridData.action',
				params : {
					queryDate:new Date(),
					orgList:orgList,
					realCheckValue:true,
					statCheckValue:false
				},
				success : function(response){
					var result = Ext.decode(response.responseText);
					if(result == null) return true;
					curveRealList = result.curveRealList;
					getDELMXMLData(curveRealList);
				},
				failure : function(){
					Ext.MessageBox.alert('提示','获取实时数据失败！');
					return ;
				}
			});
		},
		failure : function(){
			Ext.MessageBox.alert('提示','获取组织机构信息失败！');
			return ;
		}
	});
}	
function randomColor() {
	return '' + (Math.random() * 0xffffff << 0).toString(16);
};

var chartPanel = new Ext.fc.FusionChart({
			border : false,
            title:'',
			wmode : 'transparent',
			backgroundColor : '000000',
			url : './fusionCharts/ScrollLine2D.swf',
			DataXML : ""
		});

function getDELMXMLData(dateData) {
			
//	var chart = new FusionCharts("./fusionCharts/ScrollLine2D.swf", "ChartId", "375", "275");
	var xmlData = '<chart  numdivlines="9" numberSuffix="" showBorder="0" lineThickness="2" showValues="0" anchorRadius="3" ' +
			'anchorBgAlpha="50" showAlternateVGridColor="1" numVisiblePlot="12" animation="0">';
	var category = '<categories>';
	var dataSet = '';
			
  +'</categories>';
  	if(!Ext.isEmpty(dateData)){
		Ext.each(dateData,function(obj){
	  		dataSet = '<dataset seriesName="'+obj['orgName']+'" color="800080" anchorBorderColor="800080">'
	  		var j = 1;
			Ext.each(obj['list'],function(list){
				if(j <=　hour && !Ext.isEmpty(list['data'])){
					category += '<category label="'+list['time']+'" />';
					dataSet += '<set value="'+list['data']+'" tooltext="'+list['time']+'时,'+list['data']+'"/>';
				}
				j += 1;
			});
		});  
  	}
  	category += '</categories>';
  	dataSet += '</dataset>';
  	
	xmlData = xmlData + category + dataSet + '</chart>';
//	chart.setTransparent(true);
	chartPanel.changeDataXML(xmlData);
	//chart.setDataXML(xmlData);
	//chart.render(dELMTOPPanel.getId());	
	renderModel(chartPanel,'2');
};
	


var dELMTOPPanel = new Ext.Panel({
//			baseCls : "x-plain",
			layout : 'fit',
//			region : "center",
//			border : false,
			bodyBorder : false,
			monitorResize : true
		});

		
var width = 0;
var height = 0; 
//	dELMTOPPanel.on('afterlayout',function(){
//		width = dELMTOPPanel.getWidth();
//		height = dELMTOPPanel.getHeight();
////		alert("width:" + width + '\n height:' + height);
//	},dELMTOPPanel);
	getDELM();
});
