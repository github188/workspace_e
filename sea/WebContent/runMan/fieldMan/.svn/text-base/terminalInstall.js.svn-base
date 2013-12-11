/**
 * 终端安装
 * 
 * @author jiangweichao
 */

//定义全局变量
var ti_keys = new Array('orgName','totalNum');
var tiArray = [];

var ti_Succkeys = new Array('statusName','statusNum');
var tiSuccArray = [];

Ext.onReady(function() {
	var height = 0;
	var width = 0;
	//开始日期
    var startDate = new Ext.form.DateField({
    	id : 'ti_startDate',
		fieldLabel : '从',
		name : 'startDate',
		width : 150,
	    value : new Date().add(Date.MONTH, -1),
	    editable:false,
	    allowBlank:false,
	    format: 'Y-m-d',
	    labelSeparator:''
    });
    
    //结束日期
    var endDate = new Ext.form.DateField({
    	id : 'ti_endDate',
		fieldLabel : '到',
		name : 'endDate',
		width : 150,
	    value : new Date(),
	    editable:false,
	    allowBlank:false,
	    format: 'Y-m-d',
	    labelSeparator:''
    });
    
    //接口类别数据
	var interTypeStore = new Ext.data.ArrayStore({
				fields : ['interType', 'interTypeName'],
				data : [['1', '专变接口'], ['2', '低压集抄接口'], ['3', '关口接口']]
			});

	//接口类别
	var interTypeComboBox = new Ext.form.ComboBox({
		        id:'ti_interfaceType',
				fieldLabel : '接口类别',
				store : interTypeStore,
				bodyStyle : 'padding:10px;',
				triggerAction : 'all',
				mode : 'local',
				valueField : 'interType',
				displayField : 'interTypeName',
				width : 120,
				value : 1,
				selectOnFocus : true,
				allowBlank : false,
				editable : false,
				labelSeparator : '',
				listeners : {
					'select' : function(c,r,i) {
						if(2 == this.getValue()){
							tiCm.setColumnHeader(3,'用户成功数');
							tiCm.setColumnHeader(5,'用户失败数');
						}else{
							tiCm.setColumnHeader(3,'电能表成功数');
							tiCm.setColumnHeader(5,'电能表失败数');
						}
				    }
				}
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
		        
    	        tiStore.removeAll();
    	        tiStore2.removeAll();
    	        tiArray.length = 0;
    	        tiSuccArray.length = 0;
    	        
    	        Ext.Ajax.request({
    	    		url : "./runman/feildman/terminalInstall!loadGridData.action",
    	    		params : {
    	        		startDate:startDate.getValue(),
    	        		endDate:endDate.getValue(),
    	        		interType:interTypeComboBox.getValue()
    	    		},
    	    		success : function(response) {
    	    			var result = Ext.decode(response.responseText);
    	    			if (result) {
    	    				tiStore.loadData(result);
    	    				tiStore2.loadData(result);
    	    				if(!Ext.isEmpty(result.tiList) || 0 < result.tiList.length){
	    	    				for (i = 0; i < result.tiList.length; i=i+1) {
									var obj = new Object();
									obj.orgName = result.tiList[i].orgName;
									obj.totalNum = result.tiList[i].totalNum;
									tiArray.push(obj);
								}
    	    				}
    	    				if(!Ext.isEmpty(result.tiStatusList) || 0 < result.tiStatusList.length){
	    	    				for (i = 0; i < result.tiStatusList.length; i=i+1) {
									var obj = new Object();
									obj.statusName = result.tiStatusList[i].statusName;
									obj.statusNum = result.tiStatusList[i].statusNum;
									tiSuccArray.push(obj);
								}
    	    				}
    	    				generatePieChart(width, height);
    	    				generateSuccPieChart(width, height);
    	    			}
    	    		}
    	    	});
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
							columnWidth : .25,
							layout : 'form',
							border : false,
							align : 'center',
							labelWidth:60,
							labelAlign : "right",
							bodyStyle:'padding:0px 0px 0px 10px',
							items : [interTypeComboBox]
						}, {
							columnWidth : .25,
							border : false,
							align : 'center',
							bodyStyle:'padding:0px 0px 0px 10px',
							items : [qryBtn]
						}]
	    });
	    return queryPanel;
	}
	
	var pieChartPnl = new Ext.Panel({
		border : false,
		bodyBorder : false,
		layout : 'fit',
		monitorResize : false
	});
	
	var succPieChartPnl = new Ext.Panel({
		border : false,
		bodyBorder : false,
		layout : 'fit',
		monitorResize : false
	});
	
	//曲线panel
	var ti_CurvePnl = new Ext.Panel({
				border : false,
				bodyBorder : false,
				layout:'fit',
				region:'center',
				monitorResize : true,
				items : [{
					layout : 'column',
					border : false,
					items : [ {
						columnWidth : .5,
						border : false,
						layout : 'fit',
						items : [ pieChartPnl ]
					}, {
						columnWidth : .5,
						border : false,
						layout : 'fit',
						items : [ succPieChartPnl ]
					} ]
				}]
	});
	
	//用于实现fusionchart动态变化
	pieChartPnl.on("afterlayout", function(view,layout){
		width = ti_CurvePnl.getWidth() / 2;
		height = ti_CurvePnl.getHeight() - 4;
	    generatePieChart(width, height);
	},pieChartPnl);
	
	succPieChartPnl.on("afterlayout", function(view,layout){
		width = ti_CurvePnl.getWidth() / 2;
		height = ti_CurvePnl.getHeight() - 4;
		generateSuccPieChart(width, height);
    },succPieChartPnl);
	
	//定义chart变量
    var tiPieChart;
    var tiSuccPieChart;
	function generatePieChart(width, height){
		// 地区电量分布饼图
		var tmpH = height ;
		var xmlData = getSingleXMLData(tiArray,ti_keys,'终端安装统计(%)','','');
		tiPieChart = new FusionCharts("fusionCharts/Pie3D.swf",
				"tiPieChart", width, tmpH);
		tiPieChart.setDataXML(xmlData);
		tiPieChart.setTransparent(true);
		if(null != pieChartPnl){
			tiPieChart.render(pieChartPnl.getId());
		}
	}
	
	function generateSuccPieChart(width, height){
		// 地区电量成功率统计分布饼图
		var tmpH = height * 0.9;
		var xmlData = getSingleXMLData(tiSuccArray,ti_Succkeys,'成功率统计(%)','','');
		tiSuccPieChart = new FusionCharts("fusionCharts/Pie3D.swf",
				"tiSuccPieChart", width, tmpH);
		tiSuccPieChart.setDataXML(xmlData);
		tiSuccPieChart.setTransparent(true);
		if(null != succPieChartPnl){
			tiSuccPieChart.render(succPieChartPnl.getId());
		}
	}
	
	pieChartPnl.show();
	succPieChartPnl.show();
	
	 var tiCm = new Ext.grid.ColumnModel([                      
		{header : '单位名称', sortable: true, dataIndex : 'orgName', align : 'center'}, 
		{header : '装接总数', sortable: true, dataIndex : 'totalNum', align : 'center'}, 
		{header : '成功', sortable: true, dataIndex : 'succNum', align : 'center',
			renderer: function(s, m, rec){
			    if(!Ext.isEmpty(rec.get('succNum'))){
			        return "<a href='javascript:' onclick='openDetail(\""
			            + rec.get('orgNo') + "\",\""+4+"\");" + "'>" + s + "</a>";
			    }
			    return "";
		    }			
		}, 
		{header : '电能表成功数', sortable: true, dataIndex : 'succCountNum', align : 'center',
		    renderer: function(s, m, rec){
			    if(!Ext.isEmpty(rec.get('succNum'))){
			        return s;
			    }
			    return "";
		    }		
		}, 
		{header : '失败', sortable: true, dataIndex : 'failedNum', align : 'center',
		    renderer: function(s, m, rec){
			    if(!Ext.isEmpty(rec.get('failedNum'))){
			        return "<a href='javascript:' onclick='openDetail(\""
			            + rec.get('orgNo') + "\",\""+1+ "\");" + "'>" + s + "</a>";
			    }
			    return "";
		    }
		},
		{header : '电能表失败数', sortable: true, dataIndex : 'failCountNum', align : 'center',
		    renderer: function(s, m, rec){
			    if(!Ext.isEmpty(rec.get('failedNum'))){
			        return s;
			    }
			    return "";
		    }		
		}, 
		{header : '未测试', sortable: true, dataIndex : 'initNum', align : 'center',
			renderer: function(s, m, rec){
			    if(!Ext.isEmpty(rec.get('initNum'))){
			        return "<a href='javascript:' onclick='openDetail(\""
			            + rec.get('orgNo') + "\",\""+2+ "\");" + "'>" + s + "</a>";
			    }
			    return "";
		    }	
		}, 
		{header : '处理中', sortable: true, dataIndex : 'dealingNum', align : 'center',
			renderer: function(s, m, rec){
			    if(!Ext.isEmpty(rec.get('dealingNum'))){
			        return "<a href='javascript:' onclick='openDetail(\""
			            + rec.get('orgNo') + "\",\""+5+ "\");" + "'>" + s + "</a>";
			    }
			    return "";
		    }	
		}, 
		{header : '建档失败', sortable: true, dataIndex : 'synFailedNum', align : 'center',
			renderer: function(s, m, rec){
			    if(!Ext.isEmpty(rec.get('synFailedNum'))){
			        return "<a href='javascript:' onclick='openDetail(\""
			            + rec.get('orgNo') + "\",\""+3+ "\");" + "'>" + s + "</a>";
			    }
			    return "";
		    }		
		}
		]);
		
	 var tiStore = new Ext.data.Store({
		    proxy : new Ext.data.MemoryProxy(),
		    reader : new Ext.data.JsonReader({
					root : 'tiList',
					idProperty: 'orgNo'
				    }, [					   
				       {name : 'orgNo'}, 
					   {name : 'orgName'}, 
					   {name : 'totalNum'},
					   {name : 'succNum'}, 
					   {name : 'succCountNum'}, 
					   {name : 'failedNum'}, 
					   {name : 'failCountNum'}, 
					   {name : 'initNum'}, 
					   {name : 'dealingNum'}, 
					   {name : 'synFailedNum'}
					   ]),
			sortInfo: {field: 'totalNum', direction:'DESC'}
	});	
	                            		
    //返回数据Grid面板
    var rateGrid = new Ext.grid.GridPanel({
	    store : tiStore,
	    cm : tiCm,
	    stripeRows : true,
	    autoScroll : true,
	    bbar : new Ext.ux.MyToolbar({
			store : tiStore
		})
    });

    var gridPnl = new Ext.Panel({
			layout:'fit',
			border : false,
			items:[rateGrid]
	});
	
	//终端按拆统计
	 var tiCm2 = new Ext.grid.ColumnModel([                      
		{header : '供电单位', sortable: true, dataIndex : 'orgName', align : 'center'}, 
		{header : '终端新装', sortable: true, dataIndex : 'newTmnlNum', align : 'center'}, 
		{header : '终端拆除', sortable: true, dataIndex : 'removeTmnlNum', align : 'center'}, 
		{header : '终端更换', sortable: true, dataIndex : 'chgTmnlNum', align : 'center'}, 
		{header : '终端换表', sortable: true, dataIndex : 'chgMeterNum', align : 'center'},
		{header : '用户暂停', sortable: true, dataIndex : 'suspUserNum', align : 'center'}, 
		{header : '用户销户', sortable: true, dataIndex : 'delUserNum', align : 'center'}, 
		{header : '终端检修', sortable: true, dataIndex : 'mendTmnlNum', align : 'center'}
		]);
		
	 var tiStore2 = new Ext.data.Store({
		    proxy : new Ext.data.MemoryProxy(),
		    reader : new Ext.data.JsonReader({
					root : 'tiList2',
					idProperty: 'orgNo'
				    }, [					   
				       {name : 'orgNo'}, 
					   {name : 'orgName'}, 
					   {name : 'newTmnlNum'},
					   {name : 'removeTmnlNum'}, 
					   {name : 'chgTmnlNum'}, 
					   {name : 'chgMeterNum'}, 
					   {name : 'suspUserNum'}, 
					   {name : 'delUserNum'}, 
					   {name : 'mendTmnlNum'}
					   ]),
			sortInfo: {field: 'orgNo', direction:'ASC'}
	});	
	                            		
    //返回数据Grid面板
    var rateGrid2 = new Ext.grid.GridPanel({
	    store : tiStore2,
	    cm : tiCm2,
	    stripeRows : true,
	    autoScroll : true,
	    bbar : new Ext.ux.MyToolbar({
			store : tiStore2
		})
    });

    var gridPnl2 = new Ext.Panel({
			layout:'fit',
			border : false,
			items:[rateGrid2]
	});
	
	var tmnlInstallTabPnl = new Ext.TabPanel({
		activeTab : 0,
	    border : false,
	    height : 300,
	    items : [{
				title : '装接调试统计',
				border : false,
				baseCls : "x-plain",
				layout : 'fit',
				autoScroll : true,
				items : [gridPnl]
			},  {
				title : '终端拆装统计',
				border : false,
				baseCls : "x-plain",
				layout : 'fit',
				autoScroll : true,
				items : [gridPnl2]
			}]
	})
			
	//返回提示面板
	function getPromptPnl(){
		var label = new Ext.form.Label({
			style:'font-size: 10pt',
		    text:'注：装接总数＝成功＋失败＋未触发测试＋处理中+建档失败'
		});
		
		var promptPanel = new Ext.Panel({
		    border:false,
		    bodyStyle:'padding:0px 0px 0px 200px',
		    items:[label]
	    });
	    return promptPanel;
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
		    	anchor:'100% 90%',
		    	border: false,
		    	layout : 'fit',
		    	items:[tmnlInstallTabPnl]
		    }, {
		    	anchor:'100% 10%',
		    	border: false,
		    	layout : 'fit',
		    	items:[getPromptPnl()]
		    }]
	});
	
    //定义整个页面面板
	var viewPanel = new Ext.Panel({
		layout:'border',
		border : false,
		items: [northPanel,centerPanel]
	});
	
	renderModel(viewPanel,'终端安装');
	tiPieChart.render(pieChartPnl.getId());
	tiSuccPieChart.render(succPieChartPnl.getId());
});

//切换到tab-----------------------------------------------------------------------------------
function openDetail(orgNo, type){
	ti_detOrgNo = orgNo;
	ti_tmnlOpeType = type;
	ti_interType = Ext.getCmp('ti_interfaceType').getValue();
	ti_startD = Ext.getCmp('ti_startDate').getValue();
	ti_endD = Ext.getCmp('ti_endDate').getValue();
	openTab("终端装接调试","./runMan/fieldMan/tmnlInstallDetail.jsp",false,"tmnlInstallDetail");
}
