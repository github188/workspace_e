	
	var vipConsPowerVstatQueryRadioGroup = new Ext.form.RadioGroup({
			width:300,
	        columns:[.33,.33,.34] ,
	        items : [
	       		    new Ext.form.Radio({
	                    boxLabel : '按日',
	                    name : 'vipConsPowerVstatQueryRadio',
	                    checked : true,
	                    inputValue : '0',
	                    listeners : {
							'check' : function(checkbox, checked) {
										if (checked) {
											vipConsPowerVstatQueryPanel.layout.setActiveItem(0);
										}
			       		    		}
	       		    	}
	                }),
	                 new Ext.form.Radio({
	                    boxLabel : '按月',
	                    name : 'vipConsPowerVstatQueryRadio',
	                    inputValue : '1',
	                    listeners : {
							'check' : function(checkbox, checked) {
										if (checked) {
											vipConsPowerVstatQueryPanel.layout.setActiveItem(1);
										}
			       		    		}
	       		    	}
	                }),
	                 new Ext.form.Radio({
	                    boxLabel : '按年',
	                    name : 'vipConsPowerVstatQueryRadio',
	                    inputValue : '2',
	                    listeners : {
							'check' : function(checkbox, checked) {
										if (checked) {
											vipConsPowerVstatQueryPanel.layout.setActiveItem(2);
										}
			       		    		}
	       		    	}
	                })
//	                ,
//	                new Ext.form.Radio({
//	                    boxLabel : '自定义',
//	                    name : 'vipConsPowerVstatQueryRadio',
//	                    inputValue : '3',
//	                    listeners : {
//							'check' : function(checkbox, checked) {
//										if (checked) {
//											vipConsPowerVstatQueryPanel.layout.setActiveItem(3);
//										}
//			       		    		}
//	       		    	}
//	                })
	                ]
	    });
	//--------------------------查询日条件start   
	var  vipConsPowerVstatQueryDay=  new Ext.form.DateField({
	     width:90,
	     format:'Y-m-d',
	     value:new Date()
	});
	
	var vipConsPowerVstatQueryDayConditionPanel =  new Ext.Panel({
		layout:'form',
		border : false,
		hideLabels:true,
		items:[vipConsPowerVstatQueryDay]
		
	});
	//--------------------------查询日条件end
	
	//--------------------------查询月条件start 
	var power_vstat_month_all = [['01', '1月'], ['02', '2月'], ['03', '3月'],['04', '4月'],['05', '5月'],['06', '6月'],
	['07', '7月'],['08', '8月'],['09', '9月'],['10', '10月'],['11', '11月'],['12', '12月']];
	
	var power_vstat_month = new Array();
	for (var i = 0; i < new Date().getMonth(); i++) {
		power_vstat_month[i] = power_vstat_month_all[i];
	}
	var power_vstat_ds_month = new Ext.data.SimpleStore({
		fields : ['monthValue', 'month'],
		data : power_vstat_month
	});
				
	var vipConsPowerVstatQueryMonthComb = new Ext.form.ComboBox({
		store : power_vstat_ds_month,
		displayField : 'month',
		valueField : 'monthValue',
		typeAhead : true,
		mode : 'local',
		forceSelection : true,
		triggerAction : 'all',
		selectOnFocus : true,
		editable : false,
		value:'01',
		width : 90
	});
	
	var vipConsPowerVstatQueryMonthConditionPanel =  new Ext.Panel({
		layout:'form',
		border : false,
		hideLabels:true,
		items:[vipConsPowerVstatQueryMonthComb]
		
	});			
	//--------------------------查询月条件end 
	
	//--------------------------查询年条件start 
	var power_vstat_year=new Array(); 
	for ( var i = 1; i <=5; i++) {
		power_vstat_year[i-1]=[new Date().add(Date.YEAR,-i).getFullYear(),new Date().add(Date.YEAR,-i).getFullYear()+'年'];
	} 
	
	var power_vstat_ds_year = new Ext.data.SimpleStore({
		fields : ['yearValue', 'year'],
		data : power_vstat_year
	});
				
	var vipConsPowerVstatQueryYearComb = new Ext.form.ComboBox({
		store : power_vstat_ds_year,
		displayField : 'year',
		valueField : 'yearValue',
		typeAhead : true,
		mode : 'local',
		forceSelection : true,
		triggerAction : 'all',
		selectOnFocus : true,
		editable : false,
		value:new Date().add(Date.YEAR,-1).getFullYear(),
		width : 90
	});
	
	var vipConsPowerVstatQueryYearConditionPanel =  new Ext.Panel({
		layout:'form',
		border : false,
		hideLabels:true,
		items:[vipConsPowerVstatQueryYearComb]
		
	});	
	//--------------------------查询年条件end 
	
	//--------------------------查询自定义条件start 
	var  vipConsPowerVstatQueryStartDay=  new Ext.form.DateField({
		fieldLabel : '从',
	    width:100,
	    format:'Y-m-d',
	    value:new Date().add(Date.DAY,-6)
	});
	
	var  vipConsPowerVstatQueryEndDay=  new Ext.form.DateField({
		fieldLabel : '到',
	    width:100,
	    format:'Y-m-d',
	    value:new Date()
	});
	
//	var vipConsPowerVstatQueryDefineConditionPanel =  new Ext.Panel({
//		border : false,
//		layout:'table',
//		layoutConfig : {
//			columns :2
//	    },
//		items:[{
//			layout : 'form',
//			border : false,
//			labelWidth : 15,
//			labelAlign : 'right',
//			width :120,
//			items:[vipConsPowerVstatQueryStartDay]	
//		},{
//			layout : 'form',
//		    border : false,
//		    labelAlign : 'right',
//		    labelWidth:15,
//		    width : 120,
//		    items:[vipConsPowerVstatQueryEndDay]   
//		}]
//	});	
	//--------------------------查询自定义条件end 
	var vipConsPowerVstatQueryPanel=new Ext.Panel({
	   border : false,
	   width:250,
	   layout : 'card',
	   activeItem : 0,
	   bodyStyle : 'padding:10px 0px 0px 0px',
	   items:[vipConsPowerVstatQueryDayConditionPanel,
		   vipConsPowerVstatQueryMonthConditionPanel,
		   vipConsPowerVstatQueryYearConditionPanel
//		   ,
//		   vipConsPowerVstatQueryDefineConditionPanel
		   ]
	});
	
	var vipConsPowerVstatQueryButton = new Ext.Button({
		text:'统计',
		handler:function(){
		 	 vipConsPowerVstatStore.baseParams = {
					queryType : vipConsPowerVstatQueryRadioGroup.getValue().getRawValue(),
					day : vipConsPowerVstatQueryDay.getValue().format('Y-m-d'),
					month : new Date().getFullYear() + '-' +vipConsPowerVstatQueryMonthComb.getValue(),
					year : vipConsPowerVstatQueryYearComb.getValue()
				};
	         vipConsPowerVstatStore.load(); 	 					   
		},
		width:70
	});
	
	var northVipConsPowerVstatPanel=new Ext.Panel({
		border:false,
		region:'north',
		height:40,
		layout:'table',
		layoutConfig : {
			columns :3
	    },
	    defaults: {height: 35},
		items:[{
	            border : false,
	            layout:'form',
	            width:420,
	            hideLabels:true,
	  	        bodyStyle : 'padding:10px 0px 0px 20px',
		        items:[vipConsPowerVstatQueryRadioGroup]
			},vipConsPowerVstatQueryPanel,{ 
	            layout : 'form',
	            border : false,
	            width : 90,
	            bodyStyle : 'padding:10px 0px 0px 0px',
	            items : [vipConsPowerVstatQueryButton]   
		    }]
	});

	var rowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(vipConsPowerVstatStore && vipConsPowerVstatStore.lastOptions && vipConsPowerVstatStore.lastOptions.params){
				startRow = vipConsPowerVstatStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	
	var vipConsPowerVstatCm = new Ext.grid.ColumnModel([
	   rowNum,
	  {header:'供电单位',dataIndex:'orgName',sortable:true,resizable:true,align:'center',width:150,
		    renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;}},
	   {header:'用户编号',dataIndex:'consNo',sortable:true,resizable:true,align:'center'},
	   {header:'用户名称',dataIndex:'consName',sortable:true,resizable:true,align:'center',width:150,
		   renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;}},
	   {header:'越上限时间(min)',dataIndex:'uaUpTime',sortable:true,resizable:true,align:'center'},
	   {header:'越下限时间(min)',dataIndex:'uaLowTime',sortable:true,resizable:true,align:'center'},
	   {header:'越上限率(%)',dataIndex:'uaUpRatio',sortable:true,resizable:true,align:'center'},
	   {header:'越下限率(%)',dataIndex:'uaLowRatio',sortable:true,resizable:true,align:'center'},
	   {header:'合格率(%)',dataIndex:'elRatio',sortable:true,resizable:true,align:'center'}
	 ]);
	 
 	var vipConsPowerVstatStore= new Ext.data.Store({
	        url:'./advapp/vipConsPowerVstat!statVipConsPowerVstat.action',
		    reader : new Ext.data.JsonReader({
		    	root : 'vipConsPowerVstatList',
		    	totalProperty : 'totalCount'
			}, [
		    {name: 'orgName'},
		    {name: 'consNo'},
		    {name: 'consName'},
		    {name: 'uaUpTime'},
		    {name: 'uaLowTime'},
		    {name: 'uaUpRatio'},
		    {name: 'uaLowRatio'},
		     {name: 'elRatio'}
		   ])
	});
	
	var centerVipConsPowerVstatPanel=new Ext.grid.GridPanel({
		region:'center',
		store: vipConsPowerVstatStore,
		cm: vipConsPowerVstatCm,
		stripeRows: true,
	    tbar :[{
			xtype: 'label',
			html : "<font font-weight:bold;>电压合格率明细</font>"
        }],
		bbar : new Ext.ux.MyToolbar( {
				store : vipConsPowerVstatStore
		})
	});