/**
 * 现场巡视
 * 
 * @author jiangweichao
 */
Ext.onReady(function() {
	
	//返回巡视计划制定的上部分panel
	function getEstabTopPnl(){
		//巡视内容
		var inspectDescTA = new Ext.form.TextArea({
				fieldLabel : '巡视内容',
				selectOnFocus : true,
				anchor: '98%',
				autoScroll: true,
				height: 30,
	            labelSeparator:''
		});
		
		//安全措施
		var safetyPrecTA = new Ext.form.TextArea({
				fieldLabel : '安全措施',
				selectOnFocus : true,
				anchor: '98%',
				autoScroll: true,
				height: 30,
	            labelSeparator:''
		});
		
		//计划巡视日期
		var inspectDate = new Ext.form.DateField({
				fieldLabel : '计划巡视日期',
				name : 'date',
				width : 150,
			    value : new Date(),
			    labelSeparator:''
		});
		
		//计划制定日期
		var establishDate = new Ext.form.DateField({
				fieldLabel : '计划制定日期',
				name : 'date',
				width : 150,
			    value : new Date(),
			    labelSeparator:''
		});
		
		//计划巡视人员
		var humanDataStore = new Ext.data.ArrayStore({
					fields : ['humanId', 'humanContent'],
					data : [['humanData1', '张三'], ["humanData2", '李四']]
		});

		//定义购电标志
		var humanComboBox = new Ext.form.ComboBox({
					fieldLabel : '计划巡视人员',
					store : humanDataStore,
					bodyStyle: 'padding:10px;',
					triggerAction : 'all',
					mode : 'local',
					valueField : 'humanId',
					displayField : 'humanContent',
					width: 150,
					emptyText : '请选择人员',
					selectOnFocus : true,
					labelSeparator:''
		});
		
		//定义上部面板
		var estabTopPnl = new Ext.Panel({
					border : false,
					bodyStyle: 'padding:1px;',
					id : 'estabTopPnl',
					height : 125,
					labelSeparator:'',
					layout : 'column',
					buttonAlign : 'center',
					items : [{
						columnWidth : .6,
						layout: 'form',
						border : false,
						items : [{
							layout : 'form',
						    border : false,
						    labelSeparator:'',
						    bodyStyle:'padding:5px 0px 0px 0px',
						    items : [{
						        layout : 'form',
							    border : false,
							    labelWidth : 60,
							    labelAlign : "right",
							    labelSeparator:'',
							    items : [inspectDescTA]
						    }, {
						        layout : 'form',
						        border : false,
						        labelWidth : 60,
						        labelAlign : "right",
						        labelSeparator:'',
						        bodyStyle:'padding:5px 0px 0px 0px',
						        items : [safetyPrecTA]
						    }]
						}]
					}, {
						columnWidth : .4,
						layout : 'form',
					    border : false,
					    bodyStyle: 'padding:1px;',
					    labelAlign : "right",
					    labelWidth : 80,
					    labelSeparator:'',
					    items : [{
					    	layout : 'form',
					    	border : false,
					    	bodyStyle: 'padding:0px 0px 0px 0px',
					    	items : [humanComboBox]
					    },    {
					    	layout : 'form',
					    	border : false,
					    	bodyStyle: 'padding:5px 0px 0px 0px',
					    	items : [inspectDate]
					    }, {
					        layout : 'form',
					        border : false,
					    	bodyStyle:'padding:5px 0px 0px 0px',
					    	items : [establishDate]
					    }]
					}],
					buttons:[
						{text:'新建计划'},
						{text:'撤销计划'},
						{text:'编辑计划'},
						{text:'发布计划'}
		            ]
		});
		return estabTopPnl;
	}
		
	//返回巡视计划制定的中间girdPnl
	function getEstabMidGridPnl(){
		var cm = new Ext.grid.ColumnModel([                      
		    {header : '计划ID', sortable: true, dataIndex : 'planId', align : 'center'}, 
		    {header : '巡视内容', sortable: true, dataIndex : 'inspectDesc', align : 'center'}, 
		    {header : '安全措施', sortable: true, dataIndex : 'safetyPrec', align : 'center'}, 
		    {header : '计划制定人', sortable: true, dataIndex : 'planEstaber', align : 'center'}, 
		    {header : '计划制定时间', sortable: true, dataIndex : 'planEstabDate', align : 'center', renderer: Ext.util.Format.dateRenderer('Y-m-d')}, 
		    {header : '计划巡视人', sortable: true, dataIndex : 'planInspector', align : 'center'}, 
		    {header : '计划巡视日期', sortable: true, dataIndex : 'planInspectDate', align : 'center', renderer: Ext.util.Format.dateRenderer('Y-m-d')},
		    {header : '计划状态', sortable: true, dataIndex : 'planStatus', align : 'center'}
		]);

	    var estabMidStore = new Ext.data.Store({
		    	proxy : new Ext.data.HttpProxy({
					url : './runman/feildman/tmnlInspect!loadDetailGridData.action'
				}),
				reader : new Ext.data.JsonReader({
							root : 'tmnlInstDetList',
							totalProperty : 'totalCount'
						}, [
						   {name : 'planId'}, 
						   {name : 'inspectDesc'}, 
						   {name : 'safetyPrec'}, 
						   {name : 'planEstaber'}, 
						   {name : 'planEstabDate'}, 
						   {name : 'planInspector'}, 
						   {name : 'planInspectDate'},
						   {name : 'planStatus'}
						   ])
		});
		
	    var estabMidGrid = new Ext.grid.GridPanel({
		        id : 'estabMidGrid',
		        store : estabMidStore,
		        cm : cm,
		        stripeRows : true,
		        autoScroll : true,
		        bbar : new Ext.ux.MyToolbar({
					store : estabMidStore
				})
	    });
	    
	  //定义巡视计划制定的中间girdPnl
	    var estabMidGridPnl = new Ext.Panel({
	    	id : 'estabMidGridPnl',
			layout:'fit',
			border : false,
			items:[estabMidGrid]
		});
	    
	    return estabMidGridPnl;
	}
	
	//返回巡视计划制定的底部gridPnl
	function getEstabBottomGridPnl(){
		var cm = new Ext.grid.ColumnModel([                      
		    {header : '供电单位', sortable: true, dataIndex : 'branch', align : 'center'}, 
		    {header : '用户编号', sortable: true, dataIndex : 'custNo', align : 'center',
		        renderer: function(s, m, rec){
			        return "<a href='' target='_blank'>"+s+"</a>"; 
			    }
		    }, 
		    {header : '采集点标识', sortable: true, dataIndex : 'cpflg', align : 'center'}, 
		    {header : '采集点名称', sortable: true, dataIndex : 'cpName', align : 'center'}, 
		    {header : '采集点地址', sortable: true, dataIndex : 'cpAddr', align : 'center'}, 
		    {header : '采集点类型', sortable: true, dataIndex : 'cpType', align : 'center'}, 
		    {header : '终端资产号', sortable: true, dataIndex : 'tmnlAssetNo', align : 'center'},
		    {header : '终端地址', sortable: true, dataIndex : 'terminalAddr', align : 'center'},
		    {header : '通信方式', sortable: true, dataIndex : 'commType', align : 'center'},
		    {header : '终端类型', sortable: true, dataIndex : 'tmnlCode', align : 'center'},
		    {header : '生成厂家', sortable: true, dataIndex : 'factoryCode', align : 'center'},
		    {header : '投运日期', sortable: true, dataIndex : 'runDate', align : 'center', renderer: Ext.util.Format.dateRenderer('Y-m-d')}
		]);

	    var estabBtmStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : './runman/feildman/tmnlInspect!loadDetailGridData.action'
				}),
				reader : new Ext.data.JsonReader({
							root : 'tmnlInstDetList',
							totalProperty : 'totalCount'
						}, [
						   {name : 'branch'}, 
						   {name : 'custNo'}, 
						   {name : 'cpflg'}, 
						   {name : 'cpName'}, 
						   {name : 'cpAddr'}, 
						   {name : 'cpType'}, 
						   {name : 'tmnlAssetNo'},
						   {name : 'terminalAddr'},
						   {name : 'commType'},
						   {name : 'tmnlCode'},
						   {name : 'factoryCode'},
						   {name : 'runDate'}
						   ])
		});
	    
	    var estabBtmGrid = new Ext.grid.GridPanel({
	    	    title : '巡视计划涉及采集点',
		        id : 'estabBtmGrid',
		        store : estabBtmStore,
		        cm : cm,
		        stripeRows : true,
		        autoScroll : true,
		        bbar : new Ext.ux.MyToolbar({
					store : estabBtmStore
				})
	    });
	    
	  //定义巡视计划制定的底部gridPnl
	    var estabBtmGridPnl = new Ext.Panel({
	    	id : 'estabBtmGridPnl',
			layout:'fit',
			border : false,
			items:[estabBtmGrid]
		});
	    
	    return estabBtmGridPnl;
	}
	
	//返回巡视计划执行页面顶部GridPnl
	function getExecTopGridPnl(){
		var cm = new Ext.grid.ColumnModel([                      
		    {header : '计划ID', sortable: true, dataIndex : 'planId', align : 'center'}, 
		    {header : '巡视内容', sortable: true, dataIndex : 'inspectDesc', align : 'center'}, 
		    {header : '安全措施', sortable: true, dataIndex : 'safetyPrec', align : 'center'}, 
		    {header : '计划制定人', sortable: true, dataIndex : 'planEstaber', align : 'center'}, 
		    {header : '计划制定时间', sortable: true, dataIndex : 'planEstabDate', align : 'center', renderer: Ext.util.Format.dateRenderer('Y-m-d')}, 
		    {header : '计划巡视人', sortable: true, dataIndex : 'planInspector', align : 'center'}, 
		    {header : '计划巡视日期', sortable: true, dataIndex : 'planInspectDate', align : 'center', renderer: Ext.util.Format.dateRenderer('Y-m-d')},
		    {header : '计划状态', sortable: true, dataIndex : 'planStatus', align : 'center'}
		]);

	    var execTopStore = new Ext.data.Store({
				url : './runMan/fieldMan/terminalInstall.xml',
				reader : new Ext.data.XmlReader({
							record : 'rate'
						}, [
						   {name : 'planId'}, 
						   {name : 'inspectDesc'}, 
						   {name : 'safetyPrec'}, 
						   {name : 'planEstaber'}, 
						   {name : 'planEstabDate'}, 
						   {name : 'planInspector'}, 
						   {name : 'planInspectDate'},
						   {name : 'planStatus'}
						   ])
		});
	    
	    var execTopGrid = new Ext.grid.GridPanel({
		        id : 'execTopGrid',
		        store : execTopStore,
		        cm : cm,
		        stripeRows : true,
		        autoScroll : true,
		        border: true,
		        bbar : new Ext.ux.MyToolbar({
					store : execTopStore
				})
	    });
	    
	    execTopStore.load();
	    
	  //定义巡视计划执行页面顶部GridPnl
	    var execTopGridPnl = new Ext.Panel({
			layout:'fit',
		    id : 'execTopGridPnl',
		    buttonAlign: 'right',
		    title : '待巡视计划',
			border : false,
			items : [execTopGrid],
			buttons:[
				{text:'全部正常'},
				{text:'导出计划'}
            ]
		});
        return execTopGridPnl;
	}
	
	//返回巡视计划执行页面底部GridPnl
	function getExecBottomGridPnl(){
		var cm = new Ext.grid.ColumnModel([                      
		    {header : '供电单位', sortable: true, dataIndex : 'branch', align : 'center'}, 
		    {header : '用户编号', sortable: true, dataIndex : 'custNo', align : 'center',
		        renderer: function(s, m, rec){
			        return "<a href='' target='_blank'>"+s+"</a>"; 
			    }
		    }, 
		    {header : '采集点名称', sortable: true, dataIndex : 'cpName', align : 'center'}, 
		    {header : '采集点地址', sortable: true, dataIndex : 'cpAddr', align : 'center'}, 
		    {header : '采集点类型', sortable: true, dataIndex : 'cpType', align : 'center'}, 
		    {header : '终端资产号', sortable: true, dataIndex : 'tmnlAssetNo', align : 'center',
		        renderer: function(s, m, rec){
			        return "<a href='./runMan/fieldMan/inspectResult.jsp' target='_blank'>"+s+"</a>"; 
			    }
		    },
		    {header : '终端地址', sortable: true, dataIndex : 'terminalAddr', align : 'center'},
		    {header : '通信方式', sortable: true, dataIndex : 'commType', align : 'center'},
		    {header : '终端类型', sortable: true, dataIndex : 'tmnlCode', align : 'center'},
		    {header : '生成厂家', sortable: true, dataIndex : 'factoryCode', align : 'center'},
		    {header : '投运日期', sortable: true, dataIndex : 'runDate', align : 'center', renderer: Ext.util.Format.dateRenderer('Y-m-d')}
		]);

	    var execBtmStore = new Ext.data.Store({
			    	proxy : new Ext.data.HttpProxy({
						url : './runman/feildman/tmnlInspect!loadDetailGridData.action'
					}),
					reader : new Ext.data.JsonReader({
							root : 'tmnlInstDetList',
							totalProperty : 'totalCount'
						}, [
						   {name : 'branch'}, 
						   {name : 'custNo'}, 
						   {name : 'cpflg'}, 
						   {name : 'cpName'}, 
						   {name : 'cpAddr'}, 
						   {name : 'cpType'}, 
						   {name : 'tmnlAssetNo'},
						   {name : 'terminalAddr'},
						   {name : 'commType'},
						   {name : 'tmnlCode'},
						   {name : 'factoryCode'},
						   {name : 'runDate'}
						   ])
		});
	    
	    var execBtmGrid = new Ext.grid.GridPanel({
		        id : 'execBtmGrid',
		        store : execBtmStore,
		        cm : cm,
		        stripeRows : true,
		        autoScroll : true,
		        border: true,
		        bbar : new Ext.ux.MyToolbar({
					store : execBtmStore
				})
	    });
			    
	  //定义计划执行页面底部GridPnl
	    var execBtmGridPnl = new Ext.Panel({
			layout:'fit',
		    id : 'execBtmGridPnl',
		    border : true,
		    title : '巡视计划执行结果反馈',
			items : [execBtmGrid]
		});
        return execBtmGridPnl;
	}
	
	//定义顶部radio选择组
	var inspectRadioGroup = new Ext.form.RadioGroup({
		id : 'inspectRadioGroup',
		width : 300,
		items : [{
			boxLabel : '巡视计划制定',
			name : 'inspect-radioGroup',
			checked : true,
			listeners : {
				'check' : function(r, c) {
					if (c) {
						Ext.getCmp('inspectCardPanel').layout.setActiveItem(0);
					};
				}
			}
		}, {
			boxLabel : '巡视计划执行',
			name : 'inspect-radioGroup',
			listeners : {
				'check' : function(r, c) {
					if (c) {
						Ext.getCmp('inspectCardPanel').layout.setActiveItem(1);
					}
				}
			}
		}]
	});
	
	//返回上部面板
	function getRadioPnl(){
	    var radioFormPnl = new Ext.Panel({
	    	    layout:'form',
	    	    region:'north',
				labelAlign : 'left',
				border : false,
				bodyStyle: 'padding:8px;',
				id : 'radioFormPnl',
				height : 35,
				labelWidth : 30,
				labelSeparator:'',
				items : [inspectRadioGroup]
		});
	    return radioFormPnl;
	}
	
	//返回巡视计划制定card面板
	function getCardPnl1(){
		var cardPanel1 = new Ext.Panel({
			border: false,
			layout : 'anchor',
			items : [{
				border: false,
				layout : 'fit',
				anchor:'100% 30%',
				items : [getEstabTopPnl()]
			}, {
				border: false,
				layout : 'fit',
				anchor:'100% 35%',
				items : [getEstabMidGridPnl()]
			}, {
				border: false,
				layout : 'fit',
				anchor:'100% 35%',
				items : [getEstabBottomGridPnl()]
			}]
		});
		
		return cardPanel1;
	}
	
	//返回巡视计划执行card面板
	function getCardPnl2(){
		var cardPanel2 = new Ext.Panel({
			border: false,
			layout : 'anchor',
			items : [{
				border: false,
				layout : 'fit',
				anchor:'100% 40%',
				items : [getExecTopGridPnl()]
			}, {
				border: false,
				layout : 'fit',
				anchor:'100% 60%',
				items : [getExecBottomGridPnl()]
			}]
		});
		
		return cardPanel2;
	}
	
	//返回巡视计划card面板
    function getCardPnl(){
    	var inspectCardPanel = new Ext.Panel({
			border: false,
			layout : 'card',
			region : 'center',
			id :'inspectCardPanel',
			activeItem : 0,
			items : [getCardPnl1(),getCardPnl2()]
    	});
    	
    	return inspectCardPanel;
    }
	
    //定义整个页面面板
	var viewPanel = new Ext.form.FormPanel({
		    layout : 'border',
	        id : 'inspectPnl',
		    border : false,
		    items : [getRadioPnl(),getCardPnl()]
	});		
	
	renderModel(viewPanel,'现场巡视');

});