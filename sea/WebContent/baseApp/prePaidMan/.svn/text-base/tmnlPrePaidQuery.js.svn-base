/**
 * 终端预付费查询
 */

Ext.onReady(function(){
	
	var orderNoField = new Ext.form.TextField({
		labelSeparator : '',
		fieldLabel : '购电单号',
		emptyText : '请输入购电单号',
		width:120
	});
	
	var consNoField = new Ext.form.TextField({
		labelSeparator : '',
		fieldLabel : '用户编号',
		emptyText : '请输入用户编号',
		width:120
	});
	
	/*//开始日期
	var queryStartDate = new Ext.form.DateField({
		fieldLabel : '从',
		width:90,
		format: 'Y-m-d',
		value : new Date(),
	    labelSeparator:''
    });

    //结束日期
    var queryEndDate = new Ext.form.DateField({
		fieldLabel : '到',
		width:90,
		format: 'Y-m-d',
	    value : new Date().add(Date.DAY,30),
	    labelSeparator:''
    });*/
    
	var executeStatusField = new Ext.form.ComboBox({
		labelSeparator : '',
		fieldLabel : '执行状态',
		emptyText : '请选择执行状态',
		width:120
	});
	
	var queryButton= new Ext.Button({
	    text:'查询',
	    width:70,
	    handler:function(){
		    if(""==orderNoField.getValue()&&""==consNoField.getValue()){
				Ext.MessageBox.alert("提示","请输入或选择查询条件！");
			    return;
			}
			queryTmnlPrePaid(tmnlPrePaidOrgNo,
									tmnlPrePaidExecDate, tmnlPrePaidExecStatus,
									orderNoField.getValue(), consNoField
											.getValue());
		}
	});
	
    function  queryTmnlPrePaid(orgNo,execDate,execStatus,appNo,consNo){
    	queryResultStore.baseParams={
			           orgNo:orgNo,
			           execDate:execDate,
			           execStatus:execStatus,
			           appNo:appNo,
			           consNo:consNo
		           }; 
        Ext.getBody().mask("正在获取数据...");
        queryResultStore.load({
     		params : {
				start : 0,
				limit : DEFAULT_PAGE_SIZE
			},
			callback:function(options,success,response){
				Ext.getBody().unmask();
			}
        });
    }
    
	//查询条件panel
	var queryConditionPanel1 =new Ext.Panel({
		border : false,
		height : 35,
		layout : 'table',
		layoutConfig : {
			columns : 2
		},
		defaults:{height: 35},
		bodyStyle : 'padding:10px 0px 0px 0px;',
		items : [{
			layout : 'form',
			border : false,
			labelAlign : 'right',
			labelWidth : 70,
			width:370,
			items : [orderNoField]
		},{
			layout : 'form',
			border : false,
			labelWidth : 50,
			labelAlign : 'right',
			width : 285,
			items : [executeStatusField]
		}]
	});
	
	//查询条件panel
	var queryConditionPanel2 =new Ext.Panel({
		border : false,
		height : 35,
		layout : 'table',
		layoutConfig : {
			columns :2
		},
		defaults:{height: 35},
		items:[{
			layout : 'form',
			border : false,
			labelWidth : 70,
			labelAlign : 'right',
			width:370,
			bodyStyle : 'padding:5px 0px 0px 0px;',
			items : [consNoField]
		},{
			layout : 'form',
			border : false,
			width : 200,
			bodyStyle : 'padding:5px 0px 0px 105px;',
			items : [queryButton]
		}]
	});
	
	var queryConditionPanel =new Ext.Panel({
			border : false,
			region : 'north',
			height:70,
	        layout:'form',
	        items:[queryConditionPanel1,queryConditionPanel2]
	});
	
	var queryResultRowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(queryResultStore && queryResultStore.lastOptions && queryResultStore.lastOptions.params){
				startRow = queryResultStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	
	var queryResultCm = new Ext.grid.ColumnModel([  
        queryResultRowNum,                                         
	    {header : '供电单位', sortable: true, dataIndex : 'orgName', align : 'center'}, 
	    {header : '购电单号', sortable: true, dataIndex : 'appNo', align : 'center'},
	    {header : '用户编号', sortable: true, dataIndex : 'consNo', align : 'center'}, 
	    {header : '用户名称', sortable: true, dataIndex : 'consName', align : 'center'}, 
	    {header : '终端地址', sortable: true, dataIndex : 'terminalAddr', align : 'center'}, 
	    {header : '电能表资产号', sortable: true, dataIndex : 'meterAssetNo', align : 'center'}, 
	    {header : '执行日期', sortable: true, dataIndex : 'executeDate', align : 'center'},
	    {header : '执行状态', sortable: true, dataIndex : 'executeStatus', align : 'center'}, 
	    {header : '流程状态', sortable: true, dataIndex : 'flowStatus', align : 'center'}
	]);

    var queryResultStore = new Ext.data.Store({
		   	url : './baseapp/prePaidStatus!tmnlPrePaidQuery.action',
		    reader : new Ext.data.JsonReader({
					root : 'tmnlPrePaidQueryBeanList',
					totalProperty : 'totalCount'
				}, [
			    {name: 'orgName'},
			    {name: 'appNo'},
			    {name: 'consNo'},
			    {name: 'consName'},
			    {name: 'terminalAddr'},
			    {name: 'meterAssetNo'},
			    {name: 'executeDate'},
			    {name: 'executeStatus'},
			    {name: 'flowStatus'}
		   ])
	});
	
   queryTmnlPrePaid(tmnlPrePaidOrgNo,tmnlPrePaidExecDate, tmnlPrePaidExecStatus,'', '');
	        
	var queryResultGrid = new Ext.grid.GridPanel({
		   region:'center',
		   store : queryResultStore,
	       cm : queryResultCm,
	       stripeRows : true,	
	       autoScroll : true,
	       bbar : new Ext.ux.MyToolbar({
				store : queryResultStore,
				enableExpAll : true, // excel导出全部数据
			    expAllText : "全部",
				enableExpPage: true, // excel仅导出当前页
				expPageText : "当前页"
		   })
	});
	
	//定义整个页面面板
	var viewPanel = new Ext.Panel({
	        layout: 'border',
		    border : false,
		    items : [queryConditionPanel,queryResultGrid]
	});		
	
	renderModel(viewPanel,'终端预付费查询');
	
	
});