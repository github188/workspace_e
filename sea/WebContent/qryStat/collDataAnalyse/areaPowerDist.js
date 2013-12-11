/**
 * 地区电量分布
 * @author modified by jiangweichao on 2010-3-3 for bug1
 */

//定义全局变量
var apd_keys = new Array('orgName','papEBasic');
var apdArray = [];

var apd_columnId = new Array("orgName","papEBasic","papEComp","par");
var apd_columnType = new Array("基准日电量","对比日电量","电量增长率(%)");
var apd_columnColors = new Array('AFD8F8','F6BD0F','8BBA00');
var apd_columnData=[];
var width = 0;
var height = 0;

Ext.onReady(function() {
	
	// 开始日期
	var apd_startDate = new Ext.form.DateField( {
		fieldLabel : '从',
		name : 'apd_startDate',
		labelStyle : "text-align:right;",
		format : 'Y-m-d',
		editable : false,
		labelSeparator : '',
		value : new Date().add(Date.DAY, -8),
		allowBlank : false,
		width : 100
	});

	// 结束日期
	var apd_endDate = new Ext.form.DateField( {
		fieldLabel : '到',
		name : 'apd_endDate',
		labelStyle : "text-align:right;",
		format : 'Y-m-d',
		labelSeparator : '',
		editable : false,
		value : new Date().add(Date.DAY, -1),
		allowBlank : false,
		width : 100
	});
	
	//对比日期
	var apd_compareDate = new Ext.form.DateField({
		fieldLabel : '对比起始日',
		editable:false,
		name : 'apd_compareDate',
		width : 120,
		format: 'Y-m-d',
	    labelSeparator:'',
	    allowBlank:false,
	    value:new Date().add(Date.DAY, -15)
    });
	
	//定义radio选择组
    var apd_RadioGroup = new Ext.form.RadioGroup({
	    width : 150,
	    height :20,
	    items : [new Ext.form.Radio({
		    boxLabel : '饼图',
		    name : 'apd-radioGroup',
		    inputValue : '1',
		    checked: true,
		    listeners : {
		    'check' : function(r, c) {
				if (c) {
					columnChartPnl.hide();
					pieChartPnl.show();
					generatePieChart();
				};
			}}
	    }), new Ext.form.Radio({
		    boxLabel : '柱图',
		    name : 'apd-radioGroup',
		    inputValue : '2',
		    listeners : {
		    'check' : function(r, c) {
				if (c) {
					pieChartPnl.hide();
					columnChartPnl.show();
					generateColumnChart(width, height);
				};
			}}
	    })]
    });

    //校验
    function checkAPDInitData(){
        var start = apd_startDate.getValue();
		var end = apd_endDate.getValue();
		if ((start - end) > 0) {
			Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
			return false;
		}
        return true;
    }
    
	//查询按钮
	var apd_queryBtn = new Ext.Button({
			text : '查询',
			name : 'apd_queryBtn',
			width : 60,
		    labelSeparator:'',
		    handler:function(){
		        if(!checkAPDInitData()){
                    return;
                }
		        apdListStore.removeAll();
		        apdArray.length = 0;
		        apd_columnData.length = 0;		        
		        apdListStore.baseParams = {
	   	            startDate:apd_startDate.getValue(),
	   	            endDate:apd_endDate.getValue(),
	   	            compareDate:apd_compareDate.getValue()
	   	        };
		        apdListStore.load({
		        	callback: function(records, options, success){  
		        	    if(null != records && 0 < records.length){
		        	    	for (i = 0; i < records.length; i=i+1) {
		        	    		apd_columnData.push(records[i].data);
								var obj = new Object();
								obj.orgName = records[i].get("orgName");
								obj.papEBasic = records[i].get("papEBasic");
                                apdArray.push(obj);
							}
		        	    }
		        	    if(apd_RadioGroup.getValue().getRawValue() == 1){
		        	        generatePieChart();
		        	    }else{
		        	    	generateColumnChart(width, height);
		        	    }
		            }
		        });
	        }
	});

	//导出按钮
	var apd_exportBtn = new Ext.Button({
			text : '导出',
			name : 'apd_exportBtn',
			width : 80,
		    labelSeparator:'',
		    handler:function(){
	        }
	});
	
	// 地区电量分布查询条件面板
	var areaPowerDistTopPnl = new Ext.Panel({
				plain : true,
				border : false,
				region:'north',
				layout : "column",
				height:30,
				items : [{
					columnWidth : .2,
					layout : "form",
					labelWidth : 25,
					border : false,
					labelAlign: "right",
					bodyStyle : 'padding:5px 0px 0px 5px',
					items : [apd_startDate]
				}, {
					columnWidth : .2,
					layout : "form",
					border : false,
					labelWidth : 25,
					labelAlign: "right",
					bodyStyle : 'padding:5px 0px 0px 5px',
					items : [apd_endDate]

				}, {
					columnWidth : .3,
					layout : "form",
					border : false,
					labelWidth : 70,
					labelAlign: "right",
					bodyStyle : 'padding:5px 0px 0px 5px',
					items : [apd_compareDate]

				}, {
					columnWidth : .2,
					border : false,
					layout : "form",
					labelWidth : 5,
					bodyStyle : 'padding:5px 0px 0px 0px',
					items : [apd_RadioGroup]
				}, {
					columnWidth : .1,
					border : false,
					layout : "form",
					bodyStyle : 'padding:5px 0px 0px 0px',
					items : [apd_queryBtn]
				}]
			});
	
	var columnChartPnl = new Ext.Panel({
				border : false,
				bodyBorder : false,
				layout : 'fit',
				monitorResize : false
			});
			
    //用于实现fusionchart动态变化
	columnChartPnl.on("afterlayout", function(view,layout){
			width = areaPowerDistMidPnl.getWidth();
	        height = areaPowerDistMidPnl.getHeight()-4;
	        generateColumnChart(width,height);
	},columnChartPnl);
			
	var pieChartPnl = new Ext.Panel({
				border : false,
				bodyBorder : false,
				layout : 'fit',
				monitorResize : true
			});
	
	//用于实现fusionchart动态变化
	pieChartPnl.on("afterlayout", function(view,layout){
	        generatePieChart();
	},pieChartPnl);
	
	//构建图的函数
	var pieChart;
	function generatePieChart(){
		// 地区电量分布饼图
		var xmlData = getSingleXMLData(apdArray,apd_keys,'','','');
		pieChart = new FusionCharts("fusionCharts/Pie3D.swf",
				"apdPieChart", "600", "250");
		pieChart.setDataXML(xmlData);
		pieChart.setTransparent(true);
		if(!Ext.isEmpty(pieChartPnl)){
			pieChart.render(pieChartPnl.getId());
		}
	}
	pieChartPnl.show();
	
	var columnChart;
	function generateColumnChart(width, height){
		// 地区电量分布柱图
		var xmlData = getMultiCLXMLData(apd_columnData,apd_columnId,'','','',apd_columnColors,apd_columnType);
//		columnChart = new FusionCharts("fusionCharts/MSColumn3DLineDY.swf",
//				"apdColumnChart", "100%", "100%", "0", "0", "", "exactFit");
		columnChart = new FusionCharts("fusionCharts/MSColumn3DLineDY.swf",
				"apdColumnChart", width, height);
		columnChart.setDataXML(xmlData);
		columnChart.setTransparent(true);
		if(!Ext.isEmpty(columnChartPnl)){
			columnChart.render(columnChartPnl.getId());
		}
	}
				
	// 地区电量分布图面板
	var areaPowerDistMidPnl = new Ext.Panel({
				border : false,
				bodyBorder : false,
				monitorResize : true,
				region:'center',
				layout : 'fit',
				items : [pieChartPnl, columnChartPnl]
			});
	
    //地区电量分布Grid 
	var apdSm = new Ext.grid.CheckboxSelectionModel();
	
	var apdCm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), apdSm, {
				header : '供电单位',
				sortable : true,
				dataIndex : 'orgName',
				align : 'center'
			}, {
				header : '基准日电量',
				sortable : true,
				dataIndex : 'papEBasic',
				align : 'center',
				renderer : function(val) {
					if(Ext.isEmpty(val)){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : '电量占比(%)',
				sortable : true,
				dataIndex : 'pr',
				align : 'center',
				renderer : function(val) {
					if(Ext.isEmpty(val)){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : '对比日电量',
				sortable : true,
				dataIndex : 'papEComp',
				align : 'center',
				renderer : function(val) {
					if(Ext.isEmpty(val)){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : '电量增长率(%)',
				sortable : true,
				dataIndex : 'par',
				align : 'center',
				renderer : function(val) {
					if(Ext.isEmpty(val)){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : '电量增长量',
				sortable : true,
				dataIndex : 'paq',
				align : 'center',
				renderer : function(val) {
					if(Ext.isEmpty(val)){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}]);
	
	//定义Grid的store
	var apdListStore = new Ext.data.Store({
	 	        proxy : new Ext.data.HttpProxy({
							url:'./qrystat/areaPowerDist!loadGridData.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'apdList',
							totalProperty : 'totalCount',
							idProperty: 'orgNo'
						}, [
						   {name : 'orgName'},
						   {name : 'orgNo'},
						   {name : 'papEBasic'},
						   {name : 'pr'},
						   {name : 'papEComp'}, 
						   {name : 'par'}, 
						   {name : 'paq'}
						   ])
		});
	
	var apdCenterPanel = new Ext.grid.GridPanel({
				region:'center',
				autoScroll : true,
				stripeRows : true,
				viewConfig : {
					forceFit :true
				},
				cm : apdCm,
				sm : apdSm,
				store : apdListStore,
				bbar : new Ext.ux.MyToolbar({
					enableExpAll : true,
					store : apdListStore
				}),
				tbar : [{
					xtype:'label',
					html : "<font font-weight:bold;>各地区用电量情况</font>"
			    }]
			});
	
	var apdNorthPanel = new Ext.Panel({
				autoScroll : true,
				region:'north',
				height:300,
				layout:'border',
				border : false,
				items : [areaPowerDistTopPnl, areaPowerDistMidPnl]
			});
			
	// 设置地区电量分布panel
	var areaPowerDistpanel = new Ext.Panel({
				autoScroll : true,
				layout:'border',
				border : false,
				items : [apdNorthPanel, apdCenterPanel]
			});
	 renderModel(areaPowerDistpanel,'地区电量分布');
	 
	 
	/**
	 * 响应主页“昨日地区电量分布”事件
	 */ 
    if (paraDay != 'null') {
		paraDay = paraDay.substr(1, paraDay.length);
		var startDate = new Date().add(Date.DAY, -parseInt(paraDay));
		var endDate = new Date().add(Date.DAY, -parseInt(paraDay));
		var compareDate = new Date().add(Date.DAY, -(parseInt(paraDay) + 1));
		apd_startDate.setValue(startDate);
		apd_endDate.setValue(endDate);
		apd_compareDate.setValue(compareDate);
		apdListStore.removeAll();
		apdArray.length = 0;
		apd_columnData.length = 0;
		apdListStore.baseParams = {
				startDate:apd_startDate.getValue(),
   	            endDate:apd_endDate.getValue(),
   	            compareDate:apd_compareDate.getValue()
		};
		apdListStore.load({
					callback : function(records, options, success) {
						if (null != records && 0 < records.length) {
							for (i = 0; i < records.length; i = i + 1) {
								apd_columnData.push(records[i].data);
								var obj = new Object();
								obj.orgName = records[i].get("orgName");
								obj.papEBasic = records[i].get("papEBasic");
								apdArray.push(obj);
							}
						}
						if (apd_RadioGroup.getValue().getRawValue() == 1) {
							generatePieChart();
						} else {
							generateColumnChart(width, height);
						}
					}
				});
	}
	 
});
