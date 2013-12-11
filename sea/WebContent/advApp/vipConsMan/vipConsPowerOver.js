	
	var vipConsPowerOverQueryRadioGroup = new Ext.form.RadioGroup({
			width:300,
	        columns:[.33,.33,.34] ,
	        items : [
	       		    new Ext.form.Radio({
	                    boxLabel : '按日',
	                    name : 'vipConsPowerOverQueryRadio',
	                    checked : true,
	                    inputValue : '0',
	                    listeners : {
							'check' : function(checkbox, checked) {
										if (checked) {
											vipConsPowerOverQueryPanel.layout.setActiveItem(0);
										}
			       		    		}
	       		    	}
	                }),
	                 new Ext.form.Radio({
	                    boxLabel : '按月',
	                    name : 'vipConsPowerOverQueryRadio',
	                    inputValue : '1',
	                    listeners : {
							'check' : function(checkbox, checked) {
										if (checked) {
											vipConsPowerOverQueryPanel.layout.setActiveItem(1);
										}
			       		    		}
	       		    	}
	                }),
	                 new Ext.form.Radio({
	                    boxLabel : '按年',
	                    name : 'vipConsPowerOverQueryRadio',
	                    inputValue : '2',
	                    listeners : {
							'check' : function(checkbox, checked) {
										if (checked) {
											vipConsPowerOverQueryPanel.layout.setActiveItem(2);
										}
			       		    		}
	       		    	}
	                })
//	                ,
//	                new Ext.form.Radio({
//	                    boxLabel : '自定义',
//	                    name : 'vipConsPowerOverQueryRadio',
//	                    inputValue : '3',
//	                    listeners : {
//							'check' : function(checkbox, checked) {
//										if (checked) {
//											vipConsPowerOverQueryPanel.layout.setActiveItem(3);
//										}
//			       		    		}
//	       		    	}
//	                })
	                ]
	    });
	//--------------------------查询日条件start   
	var  vipConsPowerOverQueryDay=  new Ext.form.DateField({
	     width:90,
	     format:'Y-m-d',
	     value:new Date()
	});
	
	var vipConsPowerOverQueryDayConditionPanel =  new Ext.Panel({
		layout:'form',
		border : false,
		hideLabels:true,
		items:[vipConsPowerOverQueryDay]
		
	});
	//--------------------------查询日条件end
	
	//--------------------------查询月条件start 
	var power_data_month_all = [['01', '1月'], ['02', '2月'], ['03', '3月'],['04', '4月'],['05', '5月'],['06', '6月'],
	['07', '7月'],['08', '8月'],['09', '9月'],['10', '10月'],['11', '11月'],['12', '12月']];
	
	var power_data_month = new Array();
	for (var i = 0; i < new Date().getMonth(); i++) {
		power_data_month[i] = power_data_month_all[i];
	}
	var power_ds_month = new Ext.data.SimpleStore({
		fields : ['monthValue', 'month'],
		data : power_data_month
	});
				
	var vipConsPowerOverQueryMonthComb = new Ext.form.ComboBox({
		store : power_ds_month,
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
	
	var vipConsPowerOverQueryMonthConditionPanel =  new Ext.Panel({
		layout:'form',
		border : false,
		hideLabels:true,
		items:[vipConsPowerOverQueryMonthComb]
		
	});			
	//--------------------------查询月条件end 
	
	//--------------------------查询年条件start 
	var power_data_year=new Array(); 
	for ( var i = 1; i <=5; i++) {
		power_data_year[i-1]=[new Date().add(Date.YEAR,-i).getFullYear(),new Date().add(Date.YEAR,-i).getFullYear()+'年'];
	} 
	
	var power_ds_year = new Ext.data.SimpleStore({
		fields : ['yearValue', 'year'],
		data : power_data_year
	});
				
	var vipConsPowerOverQueryYearComb = new Ext.form.ComboBox({
		store : power_ds_year,
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
	
	var vipConsPowerOverQueryYearConditionPanel =  new Ext.Panel({
		layout:'form',
		border : false,
		hideLabels:true,
		items:[vipConsPowerOverQueryYearComb]
		
	});	
	//--------------------------查询年条件end 
	
	//--------------------------查询自定义条件start 
	var  vipConsPowerOverQueryStartDay=  new Ext.form.DateField({
		fieldLabel : '从',
	    width:100,
	    format:'Y-m-d',
	    value:new Date().add(Date.DAY,-6)
	});
	
	var  vipConsPowerOverQueryEndDay=  new Ext.form.DateField({
		fieldLabel : '到',
	    width:100,
	    format:'Y-m-d',
	    value:new Date()
	});
	
//	var vipConsPowerOverQueryDefineConditionPanel =  new Ext.Panel({
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
//			items:[vipConsPowerOverQueryStartDay]	
//		},{
//			layout : 'form',
//		    border : false,
//		    labelAlign : 'right',
//		    labelWidth:15,
//		    width : 120,
//		    items:[vipConsPowerOverQueryEndDay]   
//		}]
//	});	
	//--------------------------查询自定义条件end 
	var vipConsPowerOverQueryPanel=new Ext.Panel({
	   border : false,
	   width:250,
	   layout : 'card',
	   activeItem : 0,
	   bodyStyle : 'padding:10px 0px 0px 0px',
	   items:[vipConsPowerOverQueryDayConditionPanel,
		   vipConsPowerOverQueryMonthConditionPanel,
		   vipConsPowerOverQueryYearConditionPanel
//		   ,
//		   vipConsPowerOverQueryDefineConditionPanel
		   ]
	});
	
	var vipConsPowerOverQueryButton = new Ext.Button({
		text:'统计',
		handler:function(){
		 	 vipConsPowerOverStore.baseParams = {
					queryType : vipConsPowerOverQueryRadioGroup.getValue().getRawValue(),
					day : vipConsPowerOverQueryDay.getValue().format('Y-m-d'),
					month : new Date().getFullYear() + '-' +vipConsPowerOverQueryMonthComb.getValue(),
					year : vipConsPowerOverQueryYearComb.getValue()
				};
	         vipConsPowerOverStore.load(); 	 					   
		},
		width:70
	});
	
	var northVipConsPowerOverPanel=new Ext.Panel({
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
		        items:[vipConsPowerOverQueryRadioGroup]
			},vipConsPowerOverQueryPanel,{ 
	            layout : 'form',
	            border : false,
	            width : 90,
	            bodyStyle : 'padding:10px 0px 0px 0px',
	            items : [vipConsPowerOverQueryButton]   
		    }]
	});

	var rowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(vipConsPowerOverStore && vipConsPowerOverStore.lastOptions && vipConsPowerOverStore.lastOptions.params){
				startRow = vipConsPowerOverStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	
	var vipConsPowerOverCm = new Ext.grid.ColumnModel([
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
	   {header:'平均负荷(kW)',dataIndex:'avgP',sortable:true,resizable:true,align:'center'},
	   {header:'合同容量(kVA)',dataIndex:'contractCap',sortable:true,resizable:true,align:'center'},
	   {header:'平均负荷超容量(%)',dataIndex:'avgPOver',sortable:true,resizable:true,align:'center'},
	   {header:'最大负荷超容量次数',dataIndex:'upCapCnt',sortable:true,resizable:true,align:'center'}
	 ]);
	 
 	var vipConsPowerOverStore= new Ext.data.Store({
	        url:'./advapp/vipConsPowerOver!statVipConsPowerOver.action',
		    reader : new Ext.data.JsonReader({
		    	root : 'vipConsPowerOverList',
		    	totalProperty : 'totalCount'
			}, [
		    {name: 'orgName'},
		    {name: 'consNo'},
		    {name: 'consName'},
		    {name: 'avgP'},
		    {name: 'contractCap'},
		    {name: 'avgPOver'},
		    {name: 'upCapCnt'}
		   ])
	});
	
	var centerVipConsPowerOverPanel=new Ext.grid.GridPanel({
		region:'center',
		store: vipConsPowerOverStore,
		cm: vipConsPowerOverCm,
		stripeRows: true,
		tbar :[{
			xtype: 'label',
			html : "<font font-weight:bold;>负荷超容量明细</font>"
        }],
		bbar : new Ext.ux.MyToolbar( {
				store : vipConsPowerOverStore
		})
	});