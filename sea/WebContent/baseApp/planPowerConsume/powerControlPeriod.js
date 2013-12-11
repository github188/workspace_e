Ext.onReady(function(){
//-------------------------------------------------------------功控方案模板 begin	
	var schemeTempletHandlePanel = new Ext.Panel({
		border:false,
		layout:'column',
		bodyStyle:'padding:10',
			style:'font-size:13px',
		items:[{
			columnWidth:.1,
			xtype:'label',
			text:'模板名称'
		},{
			columnWidth:.24,
			xtype:'combo',
			boxLabel:'dddd'
		},{
			xtype:'label',
			columnWidth:.1
		},{
			xtype:'button',
			text:'保存模板'
		},{
			xtype:'label',
			columnWidth:.05
		},{
			xtype:'button',
			text:'另存模板'
		}]
	}); 
	
	var schemeTempletData = [{
		kssj:'2009-4-5',
		jssj:'20096-6',
		dz1:'444',
		dz2:'566',
		dz3:'246'
	}]; 
	
	var schemeTemplatStore = new Ext.data.JsonStore({
		data:schemeTempletData,
		fields:['kssj','jssj','dz1','dz2','dz3']
	});
	
	var schemeTemplatCM = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header:'开始时间',dataIndex:'kssj',sortable:true,align:'center'},
		{header:'结束时间',dataIndex:'jssj',sortable:true,align:'center'},
		{header:'定值1',dataIndex:'dz1',sortable:true,align:'center'},
		{header:'定值2',dataIndex:'dz2',sortable:true,align:'center'},
		{header:'定值3',dataIndex:'dz3',sortable:true,align:'center'}
	]);
	
	var schemeTemplatGrid = new Ext.grid.GridPanel({
		autoHeight:true,
		cm:schemeTemplatCM,
		store:schemeTemplatStore,
		viewConfig:{
			forceFit:true
		}
	});
	
	var schemeTemplatpanel = new Ext.Panel({
		border:false,
		title:'功控方案模板',
		items:[schemeTempletHandlePanel,schemeTemplatGrid]
	}); 
//	schemeTemplatpanel.render('powerControlPeriod');
//-------------------------------------------------------------功控方案模板 end
//-------------------------------------------------------------功控方案执行 begin
	//执行方案
	var excuteScheme = new Ext.form.TextField({
		fieldLabel:'执行方案',
		width:200
	});
	//执行状态
	var excuteState = new Ext.form.RadioGroup({
		fieldLabel:'执行状态',
		items:[{
			boxLabel:'方案投入',
			name:'excuteState'
		},{
			boxLabel:'方案解除',
			name:'excuteState'
		}]
	});
	
	//有效轮次
	var isRound = new Ext.form.CheckboxGroup({
		fieldLabel:'有效轮次',
		items:[{
			boxLabel:'1'
		},{
			boxLabel:'2'
		},{
			boxLabel:'3'
		},{
			boxLabel:'4'
		},{
			boxLabel:'5'
		},{
			boxLabel:'6'
		},{
			boxLabel:'7'
		},{
			boxLabel:'8'
		}]
	});
	
	//通知方式
	var informType = new Ext.form.CheckboxGroup({
		items:[{
			boxLabel:'下发成功后短信通知客户'
		},{
			boxLabel:'方案到期后自动通知'
		},{
			boxLabel:'下发成功后自动短信订阅'
		}]
	});
	
	var panel = new Ext.Panel({
		border:false,
		bodyStyle:'padding:10;',
		layout:'form',
		labelSeparator:' ',
		labelAlign:'right',
		items:[excuteScheme,excuteState,isRound,informType]
	});

	var schemeExecuteHandle = new Ext.Panel({
		border:false,
		title:'title',
		autoHeight:true,
		layout:'border',
		items:[{
			//columnWidth:.8,
			region:'west',
			border:false,
			title:'dddd',
			items:[panel]
		},{
			region:'center',
			//columnWidth:.2,
			border:false,
			title:'dddff',
			layout:'form',
			items:[{
				border:false,
				bodyStyle:'padding:10 0;',
				items:[{
					xtype:'button',
					text:'参数召测'
				}]
			},{
				border:false,
				items:[{
					xtype:'button',
					text:'参数下发'
				},{
					border:false,
					bodyStyle:'padding:10 0;',
					items:[{
						xtype:'button',
						text:'方案下发'
					}]
				}]
			}]
		}]
	});
	
	
	schemeExecuteHandle.render('powerControlPeriod');
//-------------------------------------------------------------功控方案执行 end
});