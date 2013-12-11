/**
 * 现场消缺
 * 
 * @author jiangweichao
 */
Ext.onReady(function() {
	
	//返回查询面板
	function getQueryPnl() {
		//开始日期
		var startDate = new Ext.form.DateField({
			id : 'tf_startDate',
			fieldLabel : '从',
			name : 'date',
			width : 150,
		    value : new Date().add(Date.DAY,-5),
		    labelSeparator:''
	    });
	
	    //结束日期
	    var endDate = new Ext.form.DateField({
	    	id : 'tf_endDate',
			fieldLabel : '到',
			name : 'date',
			width : 150,
		    value : new Date(),
		    labelSeparator:''
	    });
		
	    //查询按钮
	    var qryBtn = new Ext.Button({
			    text : '查询',
			    width : 70
	    });
		
	    //导出按钮
	    var exportBtn = new Ext.Button({
			    text : '导出',
			    width : 70
	    });
	
	    //定义查询面板
	    var queryPanel = new Ext.Panel({
				labelAlign : 'right',
				split : true,
				height : 55,
				layout : 'column',
				region : 'north',
				border : false,
				bodyStyle:'padding:15px 0px 0px 0px',
				items : [{
							columnWidth : .25,
							layout : 'form',
						    border : false,
						    labelWidth:18,
						    labelAlign : "right",
							items : [startDate]
						}, {
							columnWidth : .25,
							layout : 'form',
						    border : false,
						    labelWidth:18,
						    labelAlign : "right",
							items : [endDate]
						}, {
							columnWidth : .5,
							border : false,
							align : 'center',
							layout : 'column',
							items : [{
										columnWidth : .3,
										align : 'right',
										border : false,
										bodyStyle:'padding:0px 0px 0px 40px',
										items:[qryBtn]
									}, {
										columnWidth : .7,
										align : 'left',
										border : false,
										bodyStyle:'padding:0px 0px 0px 40px',
										items:[exportBtn]
									}]
						}]
		});
	    return queryPanel;
	}
	
	//返回数据Grid面板
	function getGridPnl(){
		var cm = new Ext.grid.ColumnModel([                      
		    {header : '供电单位', sortable: true, dataIndex : 'branch', align : 'center'}, 
		    {header : '用户编号', sortable: true, dataIndex : 'custNo', align : 'center',
			    renderer: function(s, m, rec){
			        return "<a href='' target='_blank'>"+s+"</a>"; 
			    }
		    }, 
		    {header : '用户名称', sortable: true, dataIndex : 'custName', align : 'center'}, 
		    {header : '终端资产号', sortable: true, dataIndex : 'tmnlAssetNo', align : 'center'}, 
		    {header : '故障现象', sortable: true, dataIndex : 'faultDesc', align : 'center',
			    renderer: function(s, m, rec){
		            return "<a href='./runMan/fieldMan/failureFix.jsp' target='_blank'>"+s+"</a>"; 
		        }
		    }, 
		    {header : '终端地址', sortable: true, dataIndex : 'terminalAddr', align : 'center'}, 
		    {header : '通信方式', sortable: true, dataIndex : 'commType', align : 'center'}, 
		    {header : '终端类型', sortable: true, dataIndex : 'tmnlCode', align : 'center'}, 
		    {header : '生成厂家', sortable: true, dataIndex : 'factoryCode', align : 'center'}, 
		    {header : '投运日期', sortable: true, dataIndex : 'runDate', align : 'center', renderer: Ext.util.Format.dateRenderer('Y-m-d')}, 
		    {header : '故障日期', sortable: true, dataIndex : 'faultDate', align : 'center', renderer: Ext.util.Format.dateRenderer('Y-m-d')},
		    {header : '采集点标识', sortable: true, dataIndex : 'cpTypeCode', align : 'center'},
		    {header : '采集点地址', sortable: true, dataIndex : 'cpAddr', align : 'center'}
		]);

	    var faultStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
				    url : './runman/feildman/tmnlFix!loadDetailGridData.action'
				}),
				reader : new Ext.data.JsonReader({
							root : 'tmnlInstDetList',
							totalProperty : 'totalCount'
						}, [
						   {name : 'branch'}, 
						   {name : 'custNo'}, 
						   {name : 'custName'}, 
						   {name : 'tmnlAssetNo', type : 'int'}, 
						   {name : 'faultDesc'}, 
						   {name : 'terminalAddr', type : 'int'}, 
						   {name : 'commType', type : 'int'},
						   {name : 'tmnlCode', type : 'int'},
						   {name : 'factoryCode', type : 'int'},
						   {name : 'runDate'},
						   {name : 'faultDate'},
						   {name : 'cpTypeCode', type : 'int'},
						   {name : 'cpAddr', type : 'int'}
						   ])
			});
	    
	    var faultGrid = new Ext.grid.GridPanel({
		    store : faultStore,
		    cm : cm,
		    stripeRows : true,
		    autoScroll : true,
		    border : false,
		    bbar : new Ext.ux.MyToolbar({
				store : faultStore
			})
	    });
	    
	  //定义数据Grid面板
	    var gridPnl = new Ext.Panel({
			layout:'fit',
			region : 'center',
			border : false,
			items:[faultGrid]
		});
		return gridPnl;
	}

    //定义整个页面面板
	var viewPanel = new Ext.form.FormPanel({
		layout: 'border' ,
		items: [getQueryPnl(),getGridPnl()],
		border : false
	});
	
	renderModel(viewPanel,'现场消缺');
});
