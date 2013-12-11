/**
 *预付费控制执行查询
 */
Ext.onReady(function(){
	var activeFlag=0;
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
	
	//开始日期
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
    });
    
	var ctrlTypeData = [['01', '停电'], ['02', '复电'], ['03', '催费']];
	
	var ctrlTypeStore = new Ext.data.SimpleStore({
				fields : ['ctrlTypeCode', 'ctrlType'],
				data : ctrlTypeData
			});
	
	var ctrlTypeField = new Ext.form.ComboBox({
		store : ctrlTypeStore,
		displayField : 'ctrlType',
		valueField : 'ctrlTypeCode',
		typeAhead : true,
		mode : 'local',
		forceSelection : true,
		triggerAction : 'all',
		fieldLabel : '控制类别',
		emptyText : '请选择控制类别',
		labelSeparator : '',
		width:120
	});
	
	var queryButton= new Ext.Button({
	    text:'查询',
	    width:70
	});
	
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
			items : [ctrlTypeField]
		}]
	});
	
	//查询条件panel
	var queryConditionPanel2 =new Ext.Panel({
		border : false,
		height : 35,
		layout : 'table',
		layoutConfig : {
			columns : 4
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
			labelWidth : 50,
			labelAlign : 'right',
			width : 150,
			bodyStyle : 'padding:5px 0px 0px 0px;',
			items:[queryStartDate]	
		},{
			layout : 'form',
            border : false,
            labelAlign : 'right',
            labelWidth:15,
            width :135,
            bodyStyle : 'padding:5px 0px 0px 0px;',
            items:[queryEndDate]   
        }, {
			layout : 'form',
			border : false,
			width : 100,
			bodyStyle : 'padding:5px 0px 0px 20px;',
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
	    {header : '购电单号', sortable: true, dataIndex : 'orderNo', align : 'center'},
	    {header : '用户编号', sortable: true, dataIndex : 'consNo', align : 'center'}, 
	    {header : '用户名称', sortable: true, dataIndex : 'consName', align : 'center'}, 
	    {header : '终端地址', sortable: true, dataIndex : 'terminalAddr', align : 'center'}, 
	    {header : '控制类别', sortable: true, dataIndex : 'ctrlType', align : 'center'}, 
	    {header : '控制状态', sortable: true, dataIndex : 'ctrlStatus', align : 'center'},
	    {header : '控制时间', sortable: true, dataIndex : 'ctrlTime', align : 'center'}
	]);

    var queryResultStore = new Ext.data.Store({
    	    url : './baseapp/prePaidStatus!buyCtrlExecQuery.action',
		    reader : new Ext.data.JsonReader({
					root : 'urgeFeeBeanList',
					totalProperty : 'totalCount'
				}, [
			    {name: 'orgName'},
			    {name: 'orderNo'},
			    {name: 'consNo'},
			    {name: 'consName'},
			    {name: 'terminalAddr'},
			    {name: 'ctrlType'},
			    {name: 'ctrlStatus'},
			    {name: 'ctrlTime'}
		   ])
	});
    
    queryResultStore.removeAll();
    queryResultStore.baseParams={
	    orgNo:prePaidExecOrgNo,
	    orgType:prePaidExecOrgType,
	    execDate:prePaidExecDate,
	    ctrlType:prePaidExecCtrlType,
	    execStatus:prePaidExecStatus
    }; 
    queryResultStore.load({
  		params : {
				start : 0,
				limit : DEFAULT_PAGE_SIZE
			}
    });
    prePaidExecNew=0;
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
	
	renderModel(viewPanel,'预付费控制执行查询');
	
	viewPanel.on('active',function(){
		if(1==prePaidExecNew){
		    queryResultStore.removeAll();
		    queryResultStore.baseParams={
			    orgNo:prePaidExecOrgNo,
			    orgType:prePaidExecOrgType,
			    execDate:prePaidExecDate,
			    ctrlType:prePaidExecCtrlType,
			    execStatus:prePaidExecStatus
		    }; 
		    queryResultStore.load({
		  		params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
		    });
			
			prePaidExecNew=0;
		}
	});
});