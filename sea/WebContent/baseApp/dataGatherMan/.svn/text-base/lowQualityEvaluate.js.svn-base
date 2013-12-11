var lowQualityEvaluateType = '';
var lowq_qualityValue = '';

var chartTmnlNo = '';
var lowq_treels = new LeftTreeListener({
			modelName : '低压采集质量',
			processClick : function(p, node, e) {
				lowq_treeClick(node, e);
			}
		});
function lowq_treeClick(node, e) {
	var obj = node.attributes.attributes;
	var type = node.attributes.type;
	var orgType = obj.orgType;
	if (!node.isLeaf()) {
		if (node.attributes.type == 'org' && (orgType == '06' || orgType == '04')) {
			Ext.getCmp("lowQualityEvaluateText").setValue(node.text);
			lowQualityEvaluateType = 'org';
			lowq_qualityValue = obj.orgNo;
		}else if (node.attributes.type == 'line') {
			Ext.getCmp("lowQualityEvaluateText").setValue(obj.lineName);
			lowQualityEvaluateType = 'line';
			lowq_qualityValue = obj.lineId;
//		}else if(type == 'ugp' || type == 'ugp'){
//			Ext.getCmp("lowQualityEvaluateText").setValue(node.text);
//			lowQualityEvaluateType = type;
//			lowq_qualityValue = obj.groupNo;
		}else {
			return true;
		}
	} else {
		Ext.getCmp("lowQualityEvaluateText").setValue(node.text);
		lowQualityEvaluateType = 'usr';
		lowq_qualityValue = obj.consNo + ',' +obj.tmnlAssetNo;
	}
};
//function createLowQualityFusionChartsXmlDate(resultData, width){
//	var num = width/120;
//	var xmlDate = '<chart formatNumberScale="0" lineThickness="2" showValues="0" anchorRadius="3" ' +
//			'anchorBgAlpha="50" showAlternateVGridColor="1" numVisiblePlot="'+num +'" animation="0">';
//	var lebelStr = '<categories>';
//	for (var i = 0; i < resultData.length; i++) {
//		lebelStr += '<category label="' + resultData[i].consName +'" />';
//	}
//	lebelStr += '</categories>';
//	var valueStr1 = '<dataset color="AFD8F8" showValues="0">';
//	for (var i = 0; i < resultData.length; i++) {
//		valueStr1 += '<set value="' + resultData[i].succRate +'" />';
//	}
//	valueStr1 += '</dataset>';
//	xmlDate = xmlDate + lebelStr +valueStr1 +'</chart>';
//
//	return xmlDate;
//}
	// 构建图形的数据
var lowq_keys = new Array("statDate","succRate");
var lowq_arrayData=[];
var lowq_arrayColor = [];

var lowq_startDateField = new Ext.form.DateField({
		name : "qualityStartDate",
		format : 'Y-m-d',
		allowBlank : false,
		editable:false,
		emptyText : '请选择日期 ...',
		fieldLabel : "从",
		width : "110",
		value : new Date().add(Date.DAY, -7)
	});
var lowq_endDateField = new Ext.form.DateField({
		name : "qualityEndDate",
		format : 'Y-m-d',
		allowBlank : false,
		editable:false,
		emptyText : '请选择日期 ...',
		fieldLabel : "到",
		width : "110",
		value : new Date().add(Date.DAY, -1)
	});
lowq_endDateField.on('change',function(df,newValue,oldValue){
	if(newValue < lowq_startDateField.getValue()){
		lowq_endDateField.setValue(oldValue);
		Ext.Msg.alert('提示','结束日期不能小于开始日期');	
	}
//	var mydays = (newValue - lowq_startDateField.getValue()) / (1000 * 24 * 3600) + 1;
//	if (mydays > 15 || mydays <= 0) {
//		alert("建议查询范围在15天之内...");
//	}
});
var lowq_QualityDateField = {
	layout : 'column',
	border : false,
	width:320,
	style : {
		marginTop : '0px',
		marginBottom : '10px',
		marginLeft : '2px',
		marginRight : '10px'
	},
	items : [{
				columnWidth:.42,
				layout : 'form',
				border : false,
				labelWidth : 20,
				width : '140',
				items : [lowq_startDateField]
			}, {
				columnWidth:.5,
				layout : 'form',
				border : false,
				labelWidth : 20,
				width : '140',
				items : [lowq_endDateField]
			}]
};

var lowq_qualityQueryPanel = new Ext.Panel({
			labelAlign : 'right',
			region : 'north',
			frame : false,
			padding : '10px 0px 5px 10px',
			height : 40,
			layout : 'column',
			border : false,
			items : [{
						columnWidth : .3,// ----------------------
						layout : "form",
						labelWidth : 50,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "节点名<font color='red'>*</font>",
									labelStyle : "text-align:right;width:50;",
									// emptyText : '请输入...',
									readOnly : true,
									emptyText : '请从左边树选择供电所或线路',
									id : 'lowQualityEvaluateText',
									labelSeparator : "",
									width : 200,
									validator : function(val) {
										if (Ext.isEmpty(val))
											return false;
										else
											return true;
									}
								}]
					}, {
				columnWidth:.42,
				style : {
					marginTop : '1px',
					marginBottom : '1px',
					marginLeft : '10px'
				},
				border : false,
				items : lowq_QualityDateField
			}, {
				columnWidth:.1,
				border : false,
				style : {
					marginTop : '1px',
					marginBottom : '1px',
					marginLeft : '10px'
				},
				items : [{
							xtype : 'button',
							text : '查询',
							width : 50,
							handler:function(){
								var lowtext = Ext.getCmp("lowQualityEvaluateText");
									if (!lowtext.isValid(true)) {
										Ext.Msg.alert('提示','请选择要查询的线路或供电所');
										lowtext.markInvalid('不能为空');
										return true;
									}
								lowq_genQualityData();
							}
						}]
			}]
});
function lowq_genQualityData (){
	if(lowq_endDateField.getValue() < lowq_startDateField.getValue()){
		Ext.Msg.alert('提示','结束日期不能小于开始日期');	
		return true;
	}
	var mydays = (lowq_endDateField.getValue() - lowq_startDateField.getValue()) / (1000 * 24 * 3600) + 1;
//	if (mydays > 15 || mydays <= 0) {
//		alert("建议查询范围在15天之内...");
//		return true;
//	}
	if(lowq_qualityValue=='' || lowQualityEvaluateType==''){
		Ext.Msg.alert('提示','请选择要查询的线路或供电所');	
		return true;
	}
	
	Ext.Ajax.request({
		url:'baseapp/lowQualityEvaluate!querySuccRate.action',
		params:{startDate:lowq_startDateField.getValue().format('Y-m-d'),endDate:lowq_endDateField.getValue().format('Y-m-d'),
		qualityValue:lowq_qualityValue,type:lowQualityEvaluateType,myDays:mydays},
		success:function(response) {
			var result = Ext.decode(response.responseText);
			if(result==null){
				lowq_ChangeCobo();
				return true;
			}
			groupStore1.loadData(result,false);
			
			lowq_arrayData = result.chartList;
			if(lowq_arrayData!=null && lowq_arrayData.length>0){
				chartTmnlNo = lowq_arrayData[0].tmnlAssetNo;
			}else {
				chartTmnlNo = '';
			}
			lowq_ChangeCobo();
		}
	});
}
// 定义图形渲染的panel
var lowq_chartPanel = new Ext.fc.FusionChart({
	border : false,
	wmode : 'transparent',
	backgroundColor : '000000',
	url : 'fusionCharts/Line.swf',
	DataXML : "<graph baseFont='宋体' baseFontSize='14' rotateYAxisName='0'  caption=''" +
			" xAxisName='' yAxisName='' decimalPrecision='0' decimals = '2' yAxisMinValue = '0' " +
			"yAxisMaxValue = '100' formatNumberScale='0'></graph>"
});

var lowq_qualityTopPanel = new Ext.form.FormPanel({
	border : false,
	region:'north',
	height:340,
	layout:'border',
	items : [lowq_qualityQueryPanel, new Ext.Panel({
				title : '采集成功率【单位：%】',
				labelAlign : 'right',
				region:'center',
				border:false,
				items : [lowq_chartPanel]
			})]
});
					
// 生成fusionchart图形
function lowq_ChangeCobo() {
	var xmlData = "<graph baseFont='宋体' baseFontSize='14' rotateYAxisName='0'  caption=''" +
			" xAxisName='' yAxisName='' decimalPrecision='0' decimals = '2' yAxisMinValue = '0' " +
			"yAxisMaxValue = '100' formatNumberScale='0'></graph>";
	if(lowq_arrayData!=null && lowq_arrayData.length>0){
		lowq_arrayColor.length = 0;
		var color = randomColor();
		for(var i=0;i<lowq_arrayData.length;i=i+1){
			lowq_arrayColor.push(color);
		}
		xmlData = getSingleXMLData(lowq_arrayData,lowq_keys,lowq_arrayData[0].consName,'','',lowq_arrayColor,2,0,125);
	}
	lowq_chartPanel.changeDataXML(xmlData);
}

	var groupStore1 = new Ext.data.JsonStore({
				root : "lowQualityList",
				proxy : new Ext.data.MemoryProxy(),
				fields : ['consName','tmnlAssetNo','consNo', 'scount', 'failCount','succRate','tcount']
			});
	var groupCM1 = new Ext.grid.ColumnModel([{
				header : '台区名称',
				dataIndex : 'consName',
				sortable : true,
				align : 'center'
			},{
				header : '终端资产号',
				dataIndex : 'tmnlAssetNo',
				sortable : true,
				align : 'center'
			},{
				header : '台区编号',
				dataIndex : 'consNo',
				sortable : true,
				hidden : true,
				align : 'center'
			}, {
				header : '成功',
				dataIndex : 'scount',
				sortable : true,
				align : 'center'
			}, {
				header : '失败',
				dataIndex : 'failCount',
				sortable : true,
				align : 'center',
					renderer : function(value,m,rec) {
						if(value!=null && value!='')
							return "<a href='javascript:' onclick='openFailDetailInfo(\""
								+ rec.get('consName') + "\",\""
								+ rec.get('tmnlAssetNo') + "\");'>" + value + "</a>";
					}
			}, {
				header : '成功率%',
				dataIndex : 'succRate',
				sortable : true,
				align : 'center'
			}]);
	var lowQECm = new Ext.grid.CheckboxSelectionModel();
	var lowQEGrid = new Ext.grid.GridPanel({
		title:'供电所明细',
		autoScroll : true,
		hidden : false,
		region : 'center',
		viewConfig : {
			forceFit : true
		},
		cm : groupCM1,
		sm : lowQECm,
		store : groupStore1
		});
	lowQEGrid.on('cellclick',function(g,r,c,e){
//		var tmnlRecord = lowQEGrid.getSelectionModel().getSelected();
		var tmnlRecord = g.getStore().getAt(r);
//		alert(tmnlRecord);
		if(tmnlRecord == null) return true;
		
		var gridTmnlNo = tmnlRecord.get('tmnlAssetNo');
		var gridConsNo = tmnlRecord.get('consNo');
		var gridConsName = tmnlRecord.get('consName');
		var gridTotal = tmnlRecord.get('tcount');
		
		var gridValue = gridTmnlNo + ',' + gridConsNo + ',' + gridConsName
						+ ',' + gridTotal
		
		if(gridTmnlNo != chartTmnlNo){
			if(lowq_endDateField.getValue() < lowq_startDateField.getValue()){
				Ext.Msg.alert('提示','结束日期不能小于开始日期');	
				return true;
			}
			var mydays = (lowq_endDateField.getValue() - lowq_startDateField.getValue()) / (1000 * 24 * 3600) + 1;
			
			
			Ext.Ajax.request({
				url:'baseapp/lowQualityEvaluate!queryTmnlSuccRate.action',
				params:{startDate:lowq_startDateField.getValue().format('Y-m-d'),endDate:lowq_endDateField.getValue().format('Y-m-d'),
				qualityValue:gridValue,myDays:mydays},
				success:function(response) {
					var result = Ext.decode(response.responseText);
					if(result==null) return true;
					
					lowq_arrayData = result.chartList;
					
					chartTmnlNo = lowq_arrayData[0].tmnlAssetNo;
					lowq_ChangeCobo();
				}
			});	
			
			chartTmnlNo = gridTmnlNo;
		}
	});
Ext.onReady(function(){
			Ext.QuickTips.init();
			var qualityViewPanel = new Ext.Panel({
				border:false,
				frame:false,
				layout:'border',
				items:[lowq_qualityTopPanel,lowQEGrid]
			});
			renderModel(qualityViewPanel, '低压采集质量');
		});

function openFailDetailInfo(consNameValue,tmnlAssetNoValue) {
//	var record = lowQEGrid.getSelectionModel().getSelected();
	
	lowConsNameValue = consNameValue;
	lowTmnlAssetNoValue = tmnlAssetNoValue;
	
	lowqeEndDate = lowq_endDateField.getValue();
	lowSign = 'lowQualityEvaluate';
	
	openTab("失败明细", "./baseApp/dataGatherMan/lowQualityEvaluateFail.jsp");
};