	
	var vipConsPowerCurveQueryRadioGroup = new Ext.form.RadioGroup({
			width:300,
	        columns:[.33,.33,.34] ,
	        items : [
	       		    new Ext.form.Radio({
	                    boxLabel : '按日',
	                    name : 'vipConsPowerCurveQueryRadio',
	                    checked : true,
	                    inputValue : '0',
	                    listeners : {
							'check' : function(checkbox, checked) {
										if (checked) {
											vipConsPowerCurveQueryPanel.layout.setActiveItem(0);
										}
			       		    		}
	       		    	}
	                }),
	                 new Ext.form.Radio({
	                    boxLabel : '按月',
	                    name : 'vipConsPowerCurveQueryRadio',
	                    inputValue : '1',
	                    listeners : {
							'check' : function(checkbox, checked) {
										if (checked) {
											vipConsPowerCurveQueryPanel.layout.setActiveItem(1);
										}
			       		    		}
	       		    	}
	                }),
	                 new Ext.form.Radio({
	                    boxLabel : '按年',
	                    name : 'vipConsPowerCurveQueryRadio',
	                    inputValue : '2',
	                    listeners : {
							'check' : function(checkbox, checked) {
										if (checked) {
											vipConsPowerCurveQueryPanel.layout.setActiveItem(2);
										}
			       		    		}
	       		    	}
	                })
//	                ,
//	                new Ext.form.Radio({
//	                    boxLabel : '自定义',
//	                    name : 'vipConsPowerCurveQueryRadio',
//	                    inputValue : '3',
//	                    listeners : {
//							'check' : function(checkbox, checked) {
//										if (checked) {
//											vipConsPowerCurveQueryPanel.layout.setActiveItem(3);
//										}
//			       		    		}
//	       		    	}
//	                })
	                ]
	    });
	//--------------------------查询日条件start   
	var  vipConsPowerCurveQueryDay=  new Ext.form.DateField({
	     width:90,
	     format:'Y-m-d',
	     value:new Date()
	});
	
	var vipConsPowerCurveQueryDayConditionPanel =  new Ext.Panel({
		layout:'form',
		border : false,
		hideLabels:true,
		items:[vipConsPowerCurveQueryDay]
		
	});
	//--------------------------查询日条件end
	
	//--------------------------查询月条件start 
	var power_curve_month_all = [['01', '1月'], ['02', '2月'], ['03', '3月'],['04', '4月'],['05', '5月'],['06', '6月'],
	['07', '7月'],['08', '8月'],['09', '9月'],['10', '10月'],['11', '11月'],['12', '12月']];
	
	var power_curve_month = new Array();
	for (var i = 0; i < new Date().getMonth(); i++) {
		power_curve_month[i] = power_curve_month_all[i];
	}
	var power_curve_ds_month = new Ext.data.SimpleStore({
		fields : ['monthValue', 'month'],
		data : power_curve_month
	});
				
	var vipConsPowerCurveQueryMonthComb = new Ext.form.ComboBox({
		store : power_curve_ds_month,
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
	
	var vipConsPowerCurveQueryMonthConditionPanel =  new Ext.Panel({
		layout:'form',
		border : false,
		hideLabels:true,
		items:[vipConsPowerCurveQueryMonthComb]
		
	});			
	//--------------------------查询月条件end 
	
	//--------------------------查询年条件start 
	var power_curve_year=new Array(); 
	for ( var i = 1; i <=5; i++) {
		power_curve_year[i-1]=[new Date().add(Date.YEAR,-i).getFullYear(),new Date().add(Date.YEAR,-i).getFullYear()+'年'];
	} 
	
	var power_curve_ds_year = new Ext.data.SimpleStore({
		fields : ['yearValue', 'year'],
		data : power_curve_year
	});
				
	var vipConsPowerCurveQueryYearComb = new Ext.form.ComboBox({
		store : power_curve_ds_year,
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
	
	var vipConsPowerCurveQueryYearConditionPanel =  new Ext.Panel({
		layout:'form',
		border : false,
		hideLabels:true,
		items:[vipConsPowerCurveQueryYearComb]
		
	});	
	//--------------------------查询年条件end 
	
	//--------------------------查询自定义条件start 
	var  vipConsPowerCurveQueryStartDay=  new Ext.form.DateField({
		fieldLabel : '从',
	    width:100,
	    format:'Y-m-d',
	    value:new Date().add(Date.DAY,-6)
	});
	
	var  vipConsPowerCurveQueryEndDay=  new Ext.form.DateField({
		fieldLabel : '到',
	    width:100,
	    format:'Y-m-d',
	    value:new Date()
	});
	
//	var vipConsPowerCurveQueryDefineConditionPanel =  new Ext.Panel({
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
//			items:[vipConsPowerCurveQueryStartDay]	
//		},{
//			layout : 'form',
//		    border : false,
//		    labelAlign : 'right',
//		    labelWidth:15,
//		    width : 120,
//		    items:[vipConsPowerCurveQueryEndDay]   
//		}]
//	});	
	//--------------------------查询自定义条件end 
	var vipConsPowerCurveQueryPanel=new Ext.Panel({
	   border : false,
	   width:250,
	   layout : 'card',
	   activeItem : 0,
	   bodyStyle : 'padding:10px 0px 0px 0px',
	   items:[vipConsPowerCurveQueryDayConditionPanel,
		   vipConsPowerCurveQueryMonthConditionPanel,
		   vipConsPowerCurveQueryYearConditionPanel
//		   ,
//		   vipConsPowerCurveQueryDefineConditionPanel
		   ]
	});
	
	var vipConsPowerCurveQueryButton = new Ext.Button({
		text:'统计',
		handler:function(){
		 	 vipConsPowerCurveStore.baseParams = {
					queryType : vipConsPowerCurveQueryRadioGroup.getValue().getRawValue(),
					day : vipConsPowerCurveQueryDay.getValue().format('Y-m-d'),
					month : new Date().getFullYear() + '-' +vipConsPowerCurveQueryMonthComb.getValue(),
					year : vipConsPowerCurveQueryYearComb.getValue()
				};
	         vipConsPowerCurveStore.load(); 	 					   
		},
		width:70
	});
	
	var northVipConsPowerCurvePanel=new Ext.Panel({
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
		        items:[vipConsPowerCurveQueryRadioGroup]
			},vipConsPowerCurveQueryPanel,{ 
	            layout : 'form',
	            border : false,
	            width : 90,
	            bodyStyle : 'padding:10px 0px 0px 0px',
	            items : [vipConsPowerCurveQueryButton]   
		    }]
	});

	var rowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(vipConsPowerCurveStore && vipConsPowerCurveStore.lastOptions && vipConsPowerCurveStore.lastOptions.params){
				startRow = vipConsPowerCurveStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	
	var vipConsPowerCurveCm = new Ext.grid.ColumnModel([
	   rowNum,
	  {header:'供电单位',dataIndex:'orgName',sortable:true,resizable:true,align:'center',width:200,
		    renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;}},
	   {header:'用户编号',dataIndex:'consNo',sortable:true,resizable:true,align:'center'},
	   {header:'用户名称',dataIndex:'consName',sortable:true,resizable:true,align:'center',width:200,
		   renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;}},
	   {header:'功率因数MAX',dataIndex:'maxCurve',sortable:true,resizable:true,align:'center'},
	   {header:'功率因数MIN',dataIndex:'minCurve',sortable:true,resizable:true,align:'center'}
	 ]);
	 
 	var vipConsPowerCurveStore= new Ext.data.Store({
	        url:'./advapp/vipConsPowerCurve!statVipConsPowerCurve.action',
		    reader : new Ext.data.JsonReader({
		    	root : 'vipConsPowerCurveList',
		    	totalProperty : 'totalCount'
			}, [
		    {name: 'orgName'},
		    {name: 'consNo'},
		    {name: 'consName'},
		    {name: 'maxCurve'},
		    {name: 'minCurve'}
		   ])
	});
	
	var centerVipConsPowerCurvePanel=new Ext.grid.GridPanel({
		region:'center',
		store: vipConsPowerCurveStore,
		cm: vipConsPowerCurveCm,
		stripeRows: true,
	    tbar :[{
			xtype: 'label',
			html : "<font font-weight:bold;>功率因数明细</font>"
        }],
		bbar : new Ext.ux.MyToolbar( {
				store : vipConsPowerCurveStore
		})
	});