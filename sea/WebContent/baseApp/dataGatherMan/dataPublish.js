/*
 * query panel
 */
// 构建图形的数据
var keys = new Array("orgName","tbData","liData");
var nameKeys = new Array("突变数据","零数据");
var colors = new Array('AFD8F8','F6BD0F');
var arrayData=[];

// 生成fusionchart图形
var dataChart;
function genDataChart() {
	var xmlData = "<graph baseFont='宋体' baseFontSize='14' rotateYAxisName='0' xaxisname='' yaxisname='' " +
			"hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' numdivlines='9' divLineColor='CCCCCC' "+
			"divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' " +
			"AlternateHGridColor='CCCCCC' caption='' subcaption='' ><categories font='Arial' " +
			"fontSize='12' fontColor='000000'></categories><dataset seriesname='突变数据' color='AFD8F8'> " +
			"</dataset><dataset seriesname='零数据' color='F6BD0F'> </dataset></graph>";
	if(arrayData.length != 0){
		xmlData = getMultiXMLData(arrayData,keys,'','','',colors,nameKeys);
	}
	dataChart = new FusionCharts("./fusionCharts/MSColumn3D.swf",
		"datachart", "750", "270");
	dataChart.setDataXML(xmlData);
	dataChart.setTransparent(true);
	if (dataPublishChartPanel != null)
		dataChart.render(dataPublishChartPanel.getId());
}
var dataPublishRadioGroup = new Ext.form.RadioGroup({
	width : 255,
	items : [new Ext.form.Radio({
				boxLabel : '抄表类',
				name : 'queryType',
				checked : true,
				inputValue : '01'
			}),
			new Ext.form.Radio({
				boxLabel : '负荷类',
				name : 'queryType',
				inputValue : '02'
			}),
			new Ext.form.Radio({
				boxLabel : '电能质量类',
				name : 'queryType',
				inputValue : '03'
			})]
});
var dataPublishStartDate = new Ext.form.DateField({
	name : "dataPublishStartDate",
	format : 'Y-m-d',
	allowBlank : false,
	emptyText : '请选择日期 ...',
	fieldLabel : "从",
	width : "130",
	value : new Date().add(Date.DAY, -6)
});
var dataPublishEndDate = new Ext.form.DateField({
	name : "dataPublishEndDate",
	format : 'Y-m-d',
	allowBlank : false,
	emptyText : '请选择日期 ...',
	fieldLabel : "到",
	width : "130",
	value : new Date()
});
var dataPublishDateField = {
	layout : 'column',
	border : false,
	width:300,
	style : {
		marginTop : '0px',
		marginBottom : '10px',
		marginLeft : '10px'
	},
	items : [{
				layout : 'form',
				border : false,
				labelWidth : 20,
				width : 150,
				items : [dataPublishStartDate]
			}, {
				layout : 'form',
				border : false,
				labelWidth : 15,
				width : 150,
				items : [dataPublishEndDate]
			}]
};

var dataPublishQueryPanel = new Ext.Panel({
			region : 'north',
			bodyStyle:'padding:5px 0px 0px 0px',
			frame : false,
			height : 30,
			layout : 'column',
			border : false,
			items : [{
						columnWidth:.34,
						border : false,
						layout:'form',
						labelWidth:1,
						labelAlign : "right",
						items : [dataPublishRadioGroup]
					}, {
						columnWidth:.21,
                        border : false,
                        layout : 'form',
                        labelWidth : 20,
                        width : 150,
                        labelAlign : "right",
                        items : [dataPublishStartDate]
                    }, {
						columnWidth:.21,
                        border : false,
                        layout : 'form',
                        labelAlign : "right",
                        labelWidth : 20,
                        width : 150,
                        items : [dataPublishEndDate]
                    }, {
                    	columnWidth:.1,
						border : false,
						items : [{
									xtype : 'button',
									text : '查询',
									width : 50,
									handler:function(){
										dataStoreLoad();
									}
								}]
					}]
		});

var dataPublishChartPanel = new Ext.Panel({
			title : '突变数据、零数据',
			region:'north',
			height:280,
			frame:false
		});

var dataPublishCm = new Ext.grid.ColumnModel([{
			header : '供电单位',
			dataIndex : 'orgName',
			align : 'center'
		}, {
			header : '突变数据',
			dataIndex : 'tbData',
			align : 'center',
			renderer : function(s, m, rec) {
				if(s==null || s=='') return "";
				return "<a href='javascript:' onclick='queryTlWindowShow(\"01\")'>"+s+"</a>";
			}
		}, {
			header : '零数据',
			dataIndex : 'liData',
			align : 'center',
			renderer : function(s, m, rec) {
				if(s==null || s=='') return "";
				return "<a href='javascript:' onclick='queryTlWindowShow(\"02\")'>"+s+"</a>";
			}
		}]);
var dataPublishStore = new Ext.data.JsonStore({
		url : "baseapp/dataPublish!getDataPublishInfo.action",
		fields : [ 'orgName', 'tbData', 'liData' ],
		root : "dataPublishInfoList"
	});
function dataStoreLoad(){
	dataPublishStore.load({
		params :{dataPublishStartDate:dataPublishStartDate.getValue().format('Y-m-d'),
					dataPublishEndDate:dataPublishEndDate.getValue().format('Y-m-d'),
					queryType:dataPublishRadioGroup.getValue().getRawValue()},
		callback: function(recs,obj,success){
			if(success && recs!=null){
				for (var i = 0; i < recs.length; i=i+1) {
					arrayData.push({
						orgName : recs[i].get("orgName"),
						tbData : recs[i].get("tbData"),
						liData : recs[i].get("liData")
					})
				}
				genDataChart();
			}
		}
	});
}

var dataPublishBottGrid = new Ext.grid.GridPanel({
			header : false,
			region:'center',
			store : dataPublishStore,
			cm : dataPublishCm,
			autoWidth:true,
			border : false,
			stripeRows : true,
			autoScroll : true,
			viewConfig : {
				sortAscText : '升序',
				sortDescText : '降序',
				columnsText : '显示列',
				deferEmptyText : '请等待...',
				emptyText : '没有数据',
				forceFit:true
			},
			bbar : new Ext.ux.MyToolbar({
						store : dataPublishStore
					})
		});
		
var dataPublishBottomPanel = new Ext.Panel({
	title : '突变数据、零数据',
	region:'center',
	layout:'border',
	frame:false,
	border : false,
	items : [{
		xtype:'panel',
		border:false,
		frame:false,
		region:'north',
		height:280,
		layout:'fit',
		items:[dataPublishChartPanel]
	}, dataPublishBottGrid]
});
var dataPublishQueryPanel1 = new Ext.Panel({
	border:false,
	layout:'fit',
	region:'north',
	height:40,
	items:[dataPublishQueryPanel]
});	

/** 弹出查询突变数据、零数据的窗口**/
function queryTlWindowShow(value){
	var record =  dataPublishBottGrid.getSelectionModel().getSelected();
	if(record!=null){
		queryTl_compName = record.get('orgName');
	}
	queryTl_raidoValue = dataPublishRadioGroup.getValue().getRawValue();
	queryTl_startDate = dataPublishStartDate.getValue();
	queryTl_endDate = dataPublishEndDate.getValue();
	queryTl_dataType = value;
	openTab("突变数据、零数据查询","./baseApp/dataGatherMan/queryTlData.jsp");
}
Ext.onReady(function() {
			Ext.QuickTips.init();
			var dataPublishViewPanel = new Ext.form.FormPanel({
						border : false,
						frame:false,
						layout:'border',
						items : [dataPublishQueryPanel1, dataPublishBottomPanel]
					});
			renderModel(dataPublishViewPanel,'数据发布管理');
			dataStoreLoad();
			genDataChart();
		});