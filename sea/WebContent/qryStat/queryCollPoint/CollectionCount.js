/**
 * 采集点统计
 * 
 * @author zhaoliang
 */
 var orgNo = '';
 var treeType = '';
 	var treels = new LeftTreeListener({
    modelName : '采集点统计',
    processClick : function(p, node, e) {
    treeClick(node, e);
    }
   });
 function treeClick(node, e) {
	var obj = node.attributes.attributes;
	var type = node.attributes.type;
	orgNo = node.attributes.attributes.orgNo;
	treeType = node.attributes.attributes.orgType;
	orgNo = node.attributes.attributes.orgNo;
	treeType = node.attributes.attributes.orgType;
	//alert(treeType);
	//alert(orgNo);
	if (!node.isLeaf()) {
		if (node.attributes.type == 'org' && treeType!='02') {
			Ext.getCmp("collectionCountOrgNo").setValue(node.text);
		}
		if (node.attributes.type == 'sub') {
			Ext.getCmp("collectionCountOrgNo").setValue(node.text);
		}if (node.attributes.type == 'cgp') {
			Ext.getCmp("collectionCountOrgNo").setValue(node.text);
		}if (node.attributes.type == 'ugp') {
			Ext.getCmp("collectionCountOrgNo").setValue(node.text);
		}
		if (node.attributes.type == 'line') {
			Ext.getCmp("collectionCountOrgNo").setValue(node.text);
		} else {
			// Ext.getCmp("pb_id_text").setValue("");
		}
	} else {
		return true;
	}
};
function getccXMLData(dateData) {
	var xmlData = "";
	Ext.each(dateData, function(obj) {
	xmlData = '<chart palette="4" decimals="0" formatNumberScale="0" enableSmartLabels="1" enableRotation="0" bgColor="FFFFFF" bgAlpha="40,100" bgRatio="0,100" bgAngle="360" showBorder="0" startingAngle="70">';
					xmlData += "<set label='终端安装' value=\""
							+ obj['tmnlTaskType1'] + "\"  isSliced=\"1\"/>";
							xmlData += "<set label='终端拆除' value=\""
							+ obj['tmnlTaskType2'] + "\"  isSliced=\"1\"/>";
							xmlData += "<set label='终端更换' value=\""
							+ obj['tmnlTaskType3'] + "\"  isSliced=\"1\"/>";
							xmlData += "<set label='终端迁移' value=\""
							+ obj['tmnlTaskType4'] + "\"  isSliced=\"1\"/>";
							xmlData += "<set label='终端杂项调试' value=\""
							+ obj['tmnlTaskType5'] + "\"  isSliced=\"1\"/>";
							xmlData += "<set label='终端检修' value=\""
							+ obj['tmnlTaskType6'] + "\"  isSliced=\"1\"/>";
	xmlData += "</chart>";
	//alert(xmlData);
	pieChartPnl.changeDataXML(xmlData);
	});
};	

function getccSUM() {
	var url = "qrystat/collectionCountAction!queryCollectionCountSUM.action";
	Ext.Ajax.request({
				url : url,
				params : { treeType:treeType,orgNo:orgNo,dateStart:Ext.getCmp("cc_startDate").getValue().format('Y-m-d'),
    	   	        		dateEnd:Ext.getCmp("cc_endDate").getValue().format('Y-m-d')},
				success : function(response) {
					// setPanelTitle();
					var result = Ext.decode(response.responseText);
					var ccxmlData = result.collectionCountSUMList;
					if (ccxmlData == null) {
						return;
					}
					getccXMLData(ccxmlData);
				}
			});
}
Ext.onReady(function() {
	
	//开始日期
    var startDate = new Ext.form.DateField({
    	id : 'cc_startDate',
		fieldLabel : '从',
		name : 'cc_startDate',
		width : 150,
	    value : new Date().add(Date.MONTH, -1),
	    editable:false,
	    allowBlank:false,
	    format: 'Y-m-d',
	    labelSeparator:''
    });
    
    //结束日期
    var endDate = new Ext.form.DateField({
    	id : 'cc_endDate',
		fieldLabel : '到',
		name : 'cc_endDate',
		width : 150,
	    value : new Date(),
	    editable:false,
	    allowBlank:false,
	    format: 'Y-m-d',
	    labelSeparator:''
    });
    	
    //查询按钮
    var qryBtn = new Ext.Button({
		    text : '查询',
		    width : 70,
		    handler : function(){
    	
    	        var start = startDate.getValue();
		        var end = endDate.getValue();
		        if ((start - end) > 0) {
					Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
					return false;
				}
		        
    	        ccStore.baseParams = {
    	        	 treeType:treeType,orgNo:orgNo,
    	   	        dateStart:Ext.getCmp("cc_startDate").getValue().format('Y-m-d'),
    	   	        dateEnd:Ext.getCmp("cc_endDate").getValue().format('Y-m-d')
    	   	    };
    	   	    ccStore.load();
    	   	    getccSUM();
//    	        ccStore.load({
//		        	callback: function(records, options, success){  
//		        	    generatePieChart();
//		            }
//		        });
		    }
    });
	
	//返回查询面板
	function getQryPnl(){
	    //定义查询面板
	    var queryPanel = new Ext.Panel({
				labelAlign : 'right',
				height : 30,
				layout : 'column',
				region:'north',
				border : false,
				bodyStyle:'padding:5px 0px 0px 0px',
				items : [{
							columnWidth : .25,
							layout : 'form',
							border : false,
							labelWidth:80,
							labelAlign : "right",
							defaultType : "textfield",
							baseCls : "x-plain",
							items : [{
									fieldLabel : "供电单位",
									//disabled : true,
									labelStyle : "text-align:right;width:65;",
									id : 'collectionCountOrgNo',
									labelSeparator : "",
									width : 150
								}]
						},{
							columnWidth : .25,
							layout : 'form',
							border : false,
							labelWidth:20,
							labelAlign : "right",
							bodyStyle:'padding:0px 0px 0px 10px',
							items : [startDate]
						}, {
							columnWidth : .25,
							layout : 'form',
							border : false,
							labelWidth:20,
							labelAlign : "right",
							bodyStyle:'padding:0px 0px 0px 10px',
							items : [endDate]
						}, {
							columnWidth : .1,
							border : false,
							align : 'center',
							bodyStyle:'padding:0px 0px 0px 10px',
							items : [qryBtn]
						}]
	    });
	    return queryPanel;
	}
	
	 pieChartPnl = new Ext.fc.FusionChart({
			border : false,
			title : '',
			wmode : 'transparent',
			backgroundColor : '000000',
			url : 'fusionCharts/Pie3D.swf',
			DataXML : ""
		});
	// 当日用电负荷监测
	var ti_CurvePnl = new Ext.Panel({
				border : false,
				bodyBorder : false,
				layout:'fit',
				region:'center',
				monitorResize : true,
				bodyStyle : 'padding:0px 2px 5px 2px',
				items : [pieChartPnl]
	});
	
//	//用于实现fusionchart动态变化
//	pieChartPnl.on("afterlayout", function(view,layout){
//	        getccSUM();
//	},pieChartPnl);

	
//	pieChartPnl.show();
	
	 var tiCm = new Ext.grid.ColumnModel([                      
		{header : '单位名称', sortable: true, dataIndex : 'orgName', align : 'center'}, 
		{header : '终端安装', sortable: true, dataIndex : 'tmnlTaskType1', align : 'center'}, 
		{header : '终端拆除', sortable: true, dataIndex : 'tmnlTaskType2', align : 'center'}, 
		{header : '终端更换', sortable: true, dataIndex : 'tmnlTaskType3', align : 'center'}, 
		{header : '终端迁移', sortable: true, dataIndex : 'tmnlTaskType4', align : 'center'}, 
		{header : '终端杂项调试', sortable: true, dataIndex : 'tmnlTaskType5', align : 'center'}, 
		{header : '终端检修', sortable: true, dataIndex : 'tmnlTaskType6', align : 'center'}
		]);
		
	 var ccStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
					url:'qrystat/collectionCountAction!queryCollectionCount.action'
				}),
		    reader : new Ext.data.JsonReader({
					root : 'collectionCountList',
					idProperty: 'orgName'
				    }, [					   
				       {name : 'orgName'}, 
					   {name : 'tmnlTaskType1'}, 
					   {name : 'tmnlTaskType2'},
					   {name : 'tmnlTaskType3'}, 
					   {name : 'tmnlTaskType4'}, 
					   {name : 'tmnlTaskType5'}, 
					   {name : 'tmnlTaskType6'}, 
					   {name : 'tmnlTaskType7'}
					   ])
	});	
	                            		
    //返回数据Grid面板
    function getGridPnl(){
	    var rateGrid = new Ext.grid.GridPanel({
		    store : ccStore,
		    cm : tiCm,
		    stripeRows : true,
		    autoScroll : true,
				bbar : new Ext.ux.MyToolbar({
							store : ccStore,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部",
							enableExpPage : true, // excel仅导出当前页
							expPageText : "当前页"
						})
	    });
	
	    var gridPnl = new Ext.Panel({
				layout:'fit',
				border : false,
				items:[rateGrid]
		});
		return gridPnl;
    }
			
	
	//定义border的上部面板
	var northPanel = new Ext.Panel({
	    region:'north',
	    height:300,
	    layout:'border',
		border : false,
	    items:[getQryPnl(),ti_CurvePnl]
	});
	
	//定义border的中间面板
	var centerPanel = new Ext.Panel({
		layout: 'anchor' ,
	    region:'center',  
	    border : false,
	    items : [{
		    	anchor:'100% 100%',
		    	border: false,
		    	layout : 'fit',
		    	items:[getGridPnl()]
		    }]
	});
	
    //定义整个页面面板
	var viewPanel = new Ext.Panel({
		layout:'border',
		border : false,
		items: [northPanel,centerPanel]
	});
	
	renderModel(viewPanel,'采集点统计');
});
