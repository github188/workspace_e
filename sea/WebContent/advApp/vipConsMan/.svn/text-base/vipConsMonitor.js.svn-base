Ext.onReady(function() {
	var reqRatioQueryRadioGroup = new Ext.form.RadioGroup({
		width:300,
        columns:[.33,.33,.34] ,
        items : [
       		    new Ext.form.Radio({
                    boxLabel : '按日',
                    name : 'reqRatioQueryRadio',
                    checked : true,
                    inputValue : '0',
                    listeners : {
						'check' : function(checkbox, checked) {
									if (checked) {
										reqRatioQueryPanel.layout.setActiveItem(0);
									}
		       		    		}
       		    	}
                }),
                 new Ext.form.Radio({
                    boxLabel : '按月',
                    name : 'reqRatioQueryRadio',
                    inputValue : '1',
                    listeners : {
						'check' : function(checkbox, checked) {
									if (checked) {
										reqRatioQueryPanel.layout.setActiveItem(1);
									}
		       		    		}
       		    	}
                }),
                 new Ext.form.Radio({
                    boxLabel : '按年',
                    name : 'reqRatioQueryRadio',
                    inputValue : '2',
                    listeners : {
						'check' : function(checkbox, checked) {
									if (checked) {
										reqRatioQueryPanel.layout.setActiveItem(2);
									}
		       		    		}
       		    	}
                })/*,
                new Ext.form.Radio({
                    boxLabel : '自定义',
                    name : 'reqRatioQueryRadio',
                    inputValue : '3',
                    listeners : {
						'check' : function(checkbox, checked) {
									if (checked) {
										reqRatioQueryPanel.layout.setActiveItem(3);
									}
		       		    		}
       		    	}
                })*/]
    });
	//--------------------------查询日条件start   
	var  reqRatioQueryDay=  new Ext.form.DateField({
	     width:100,
	     format:'Y-m-d',
	     editable : false,
	     value:new Date()
	});
	
	var reqRatioQueryDayConditionPanel =  new Ext.Panel({
		layout:'form',
		border : false,
		hideLabels:true,
		items:[reqRatioQueryDay]
		
	});
	//--------------------------查询日条件end
	
	//--------------------------查询月条件start 
	
	var data_month=new Array(); 
	for ( var i = 0; i < 12; i++) {
		if(i<9)
			data_month[i]=[new Date().getFullYear()+'-0'+(i+1),new Date().getFullYear()+'年'+(i+1)+'月'];
		else
			data_month[i]=[new Date().getFullYear()+'-'+(i+1),new Date().getFullYear()+'年'+(i+1)+'月'];
	}
	
	var ds_month = new Ext.data.SimpleStore({
		fields : ['monthValue', 'month'],
		data : data_month
	});
				
	var reqRatioQueryMonthComb = new Ext.form.ComboBox({
		store : ds_month,
		displayField : 'month',
		valueField : 'monthValue',
		typeAhead : true,
		mode : 'local',
		forceSelection : true,
		triggerAction : 'all',
		selectOnFocus : true,
		editable : false,
		value:new Date().getFullYear()+'-0'+(new Date().getMonth()+1),
		width : 100
	});
	
	var reqRatioQueryMonthConditionPanel =  new Ext.Panel({
		layout:'form',
		border : false,
		hideLabels:true,
		items:[reqRatioQueryMonthComb]
	});			
	//--------------------------查询月条件end 
	
	//--------------------------查询年条件start 
	var data_year=new Array(); 
	for ( var i = 0; i <=5; i++) {
		data_year[i]=[new Date().add(Date.YEAR,-i).getFullYear(),new Date().add(Date.YEAR,-i).getFullYear()+'年'];
	} 
	
	var ds_year = new Ext.data.SimpleStore({
		fields : ['yearValue', 'year'],
		data : data_year
	});
				
	var reqRatioQueryYearComb = new Ext.form.ComboBox({
		store : ds_year,
		displayField : 'year',
		valueField : 'yearValue',
		typeAhead : true,
		mode : 'local',
		forceSelection : true,
		triggerAction : 'all',
		selectOnFocus : true,
		editable : false,
		value:new Date().getFullYear(),
		width : 100
	});
	
	var reqRatioQueryYearConditionPanel =  new Ext.Panel({
		layout:'form',
		border : false,
		hideLabels:true,
		items:[reqRatioQueryYearComb]
	});	
	//--------------------------查询年条件end 
	
	//--------------------------查询自定义条件start 
	/*var  reqRatioQueryStartDay=  new Ext.form.DateField({
		fieldLabel : '从',
	    width:90,
	    format:'Y-m-d',
	    editable : false,
	    value:new Date().add(Date.DAY,-6)
	});
	
	var  reqRatioQueryEndDay=  new Ext.form.DateField({
		fieldLabel : '到',
	    width:90,
	    format:'Y-m-d',
	    editable : false,
	    value:new Date()
	});
	
	var reqRatioQueryDefineConditionPanel =  new Ext.Panel({
		border : false,
		layout:'table',
		layoutConfig : {
			columns :2
	    },
		items:[{
			layout : 'form',
			border : false,
			labelWidth : 15,
			labelAlign : 'right',
			width :110,
			items:[reqRatioQueryStartDay]	
		},{
			layout : 'form',
		    border : false,
		    labelAlign : 'right',
		    labelWidth:15,
		    width : 110,
		    items:[reqRatioQueryEndDay]   
		}]
	});	*/
	//--------------------------查询自定义条件end 
	var reqRatioQueryPanel=new Ext.Panel({
	   border : false,
	   width:255,
	   layout : 'card',
	   activeItem : 0,
	   bodyStyle : 'padding:10px 0px 0px 0px',
	   items:[reqRatioQueryDayConditionPanel,reqRatioQueryMonthConditionPanel,reqRatioQueryYearConditionPanel]
	});
	
	var reqRatioQueryButton = new Ext.Button({
		text:'统计',
		width:70,
		handler:function(){
		 	 reqRatioStore.baseParams={queryType:reqRatioQueryRadioGroup.getValue().getRawValue(),
		 	 					   queryDay:reqRatioQueryDay.getValue().format('Y-m-d'),
		 	 					   queryMonth:reqRatioQueryMonthComb.getValue(),
		 	 					   queryYear:reqRatioQueryYearComb.getValue()
		 	 					   };			   
	         reqRatioStore.load({
	         		params : {
									start : 0,
									limit : DEFAULT_PAGE_SIZE
								}
		     }); 	 					   
		}
	});
	
	var northReqRatioAnalysePanel=new Ext.Panel({
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
	            width:415,
	            hideLabels:true,
	  	        bodyStyle : 'padding:10px 0px 0px 20px',
		        items:[reqRatioQueryRadioGroup]
			},reqRatioQueryPanel,{ 
	            layout : 'form',
	            border : false,
	            width : 90,
	            bodyStyle : 'padding:10px 0px 0px 0px',
	            items : [reqRatioQueryButton]   
		    }]
	});
	
	var reqRatioRowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(reqRatioStore && reqRatioStore.lastOptions && reqRatioStore.lastOptions.params){
				startRow = reqRatioStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	
	var reqRatioCm = new Ext.grid.ColumnModel([
	   reqRatioRowNum,
	  {header:'供电单位',dataIndex:'orgName',sortable:true,resizable:true,align:'center',width:150,
		    renderer : function(val) {
		       if(null!=val){
					var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
							+ val + '</div>';
					return html;
				}
		       else
					return '';
		       }},
	   {header:'用户编号',dataIndex:'consNo',sortable:true,resizable:true,align:'center'},
	   {header:'用户名称',dataIndex:'consName',sortable:true,resizable:true,align:'center',width:150,
		   renderer : function(val) {
					   if(null!=val){
							var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
									+ val + '</div>';
							return html;
					   }
					   else
							return '';
		   }},			
	   {header:'最大负荷(kW)',dataIndex:'maxP',sortable:true,resizable:true,align:'center'},
	   {header:'合同容量(kVA)',dataIndex:'contractCap',sortable:true,resizable:true,align:'center'},
	   {header:'需用系数(%)',dataIndex:'reqRatio',sortable:true,resizable:true,align:'center'},
	   {header:'发生时间',dataIndex:'statDate',sortable:true,resizable:true,align:'center'}
	 ]);
 
	var reqRatioStore= new Ext.data.Store({
	        url:'./advapp/vipConsMonitor!queryReqRatio.action',
		    reader : new Ext.data.JsonReader({
		    	root : 'vipConsMonitorDtoList',
		    	totalProperty : 'totalCount'
			}, [
		    {name: 'orgName'},
		    {name: 'consNo'},
		    {name: 'consName'},
		    {name: 'maxP'},
		    {name: 'contractCap'},
		    {name: 'reqRatio'},
		    {name: 'statDate'}])
	});

	var centerReqRatioAnalysePanel=new Ext.grid.GridPanel({
		region:'center',
		cm: reqRatioCm,
		store: reqRatioStore,
		stripeRows: true,
		loadMask: true,
	    tbar :[{
			xtype: 'label',
			html : "<font font-weight:bold;>需用系数明细</font>"
        }],
		bbar : new Ext.ux.MyToolbar({
			store : reqRatioStore,
			enableExpAll : true, // excel导出全部数据
		    expAllText : "全部",
			enableExpPage: true, // excel仅导出当前页
			expPageText : "当前页"
		})
	});
	
	var vipConsMonitorPanel =  new Ext.TabPanel({
		   activeTab: 0,
		   border : false,
		   items:[{
		      title:'需用系数分析',
		      border : false,
		      layout:'border',
		      items:[northReqRatioAnalysePanel,centerReqRatioAnalysePanel]
		   },{
		      title:'负荷超容量统计',
		      border : false,
		      layout:'border',
		      items:[northVipConsPowerOverPanel,centerVipConsPowerOverPanel]
		   },{
		      title:'电压合格率',
		      border : false,
		      layout:'border',
		      items:[northVipConsPowerVstatPanel,centerVipConsPowerVstatPanel]
		   },{
		      title:'功率因数越限',
		      border : false,
		      layout:'border',
		      items:[northVipConsPowerCurvePanel,centerVipConsPowerCurvePanel]
		   },{
		      title:'重点户排名分析',
		      border : false,
		      layout:'border',
		      items:[vcSortAnalyse_northPanel,vcSortAnalyse_gridPanel]
		   },{
		      title:'重点用户监测',
		      border : false,
		      layout:'fit',
		      items:[vcMonitorData_gridPanel]
		   }]
	});
	renderModel(vipConsMonitorPanel,'重点用户监测');
});