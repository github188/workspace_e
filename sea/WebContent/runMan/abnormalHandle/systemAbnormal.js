Ext.onReady(function(){
//-------------------------------------------------------------查询 begin	
	var systemParaPanel = new Ext.FormPanel({
		border:false,
		region : 'north',
		layout:'column',
		height : 40,
		bodyStyle:'padding:10px 0px',
		items:[{
			columnWidth:.32,
			border:false,
			labelAlign:'right',
			labelSeparator:' ',
			layout:'form',
			items:[{
				xtype:'datefield',
				fieldLabel:'从',
				format: 'Y-m-d',
				value: new Date(),
				width:120
			}]
		},{
			columnWidth:.56,
			border:false,
			labelWidth:8,
			labelAlign:'right',
			labelSeparator:' ',
			layout:'form',
			items:[{
				xtype:'datefield',
				fieldLabel:'到',
				format: 'Y-m-d',
				value: new Date(),
				width:120
			}]
		},{
			border:false,
			labelAlign:'right',
			labelSeparator:' ',
			layout:'form',
			items:[{
				xtype:'button',
				text:'查询',
				width:80
			}]
		}]
	});
//-------------------------------------------------------------查询 end	
//-------------------------------------------------------------系统异常 begin
	var systemData = [
//		{
//		ycbh:'异常编号',
//		xtsb:'系统设备',
//		yclx:'异常类型',
//		pgsj:'2009-2-2',
//		clzt:'处理状态',
//		jjsj:'2009-2-3',
//		clr:'处理人',
//		sbdqzt:'设备当前状态'
//	}
	];
	
	var systemStore = new Ext.data.JsonStore({
		data:systemData,
		fields:['ycbh','xtsb','yclx','pgsj','clzt','jjsj','clr','sbdqzt']
	});
	
	var systemSM = new Ext.grid.RowSelectionModel();
	var systemCM = new Ext.grid.ColumnModel([
		{header:'异常编号',dataIndex:'ycbh',sortable:true,align:'center'},
		{header:'系统设备',dataIndex:'xtsb',sortable:true,align:'center'},
		{header:'异常类型',dataIndex:'yclx',sortable:true,align:'center'},
		{header:'派工时间',dataIndex:'pgsj',sortable:true,align:'center'},
		{header:'处理状态',dataIndex:'clzt',sortable:true,align:'center'},
		{header:'解决时间',dataIndex:'jjsj',sortable:true,align:'center'},
		{header:'处理人',dataIndex:'clr',sortable:true,align:'center'},
		{header:'设备当前状态',dataIndex:'sbdqzt',sortable:true,align:'center'}
	]);
	
	var systemGrid = new Ext.grid.GridPanel({
		region : 'center',
		layout:'fit',
		cm:systemCM,
		title:'系统异常',
		sm:systemSM,
		store:systemStore,
		viewConfig:{
			forceFit:true
		}
	});
	
//-------------------------------------------------------------系统异常 end
//-------------------------------------------------------------异常处理 begin
	//异常工单号
	var workOrder = new Ext.Panel({
		border:false,
		layout:'column',
		bodyStyle:'padding:10px 0px;',
		items:[{
			columnWidth:.31,
			border:false,
			layout:'form',
			labelSeparator:' ',
			labelAlign:'right',
			items:[{
				xtype:'textfield',
				fieldLabel:'异常工单号'
			}]
		},{
			columnWidth:.1,
			border:false,
			layout:'form',
			labelSeparator:' ',
			labelAlign:'right',
			items:[{
				xtype:'button',
				text:'处理记录',
				width:80
			}]
		},{
			columnWidth:.25,
			border:false,
			layout:'form',
			labelSeparator:' ',
			labelWidth:55,
			labelAlign:'right',
			items:[{
				xtype:'textfield',
				fieldLabel:'系统设备'
			}]
		},{
			columnWidth:.25,
			border:false,
			layout:'form',
			labelSeparator:' ',
			labelWidth:55,
			labelAlign:'right',
			items:[{
				xtype:'textfield',
				fieldLabel:'异常名称'
			}]
		}]
	});
	
	//异常原因
	var reasonPanel = new Ext.Panel({
		border:false,
		layout:'form',
		labelSeparator:' ',
		autoWidth:true,
		labelAlign:'right',
		items:[{
			xtype:'textarea',
			fieldLabel:'异常原因',
			autoScroll:true,
			height : 30,
			width:630
		}]
	});
	
	//处理人
	var handlerPanel = new Ext.Panel({
		border:false,
		layout:'column',
		bodyStyle:'padding:10px 0px;',
		items:[{
			columnWidth:.31,
			border:false,
			layout:'form',
			labelSeparator:' ',
			labelAlign:'right',
			items:[{
				xtype:'combo',
				fieldLabel:'处理人',
				width:140
			}]
		},{
			columnWidth:.1,
			border:false,
			layout:'form',
			labelSeparator:' ',
			labelAlign:'right',
			items:[{
				xtype:'button',
				text:'查找',
				width:80
			}]
		},{
			columnWidth:.25,
			border:false,
			layout:'form',
			labelSeparator:' ',
			labelWidth:55,
			labelAlign:'right',
			items:[{
				xtype:'combo',
				fieldLabel:'异常状态',
				width:140
			}]
		},{
			columnWidth:.25,
			border:false,
			layout:'form',
			labelSeparator:' ',
			labelWidth:55,
			labelAlign:'right',
			items:[{
				xtype:'datefield',
				fieldLabel:'处理日期',
				format: 'Y-m-d',
				value: new Date(),
				width:140
			}]
		}]
	});
	
	//处理说明
	var handleShow = new Ext.Panel({
		border:false,
		layout:'form',
		labelSeparator:' ',
		autoWidth:true,
		labelAlign:'right',
		items:[{
			xtype:'textarea',
			fieldLabel:'处理说明',
			autoScroll:true,
			height : 30,
			width:630
		}]
	});
	
	var systemHandle = new Ext.Panel({
		title:'异常处理',
		height:200,
		border:false,
		region : 'south',
		items:[workOrder,reasonPanel,handlerPanel,handleShow]
	});
	
	var panel = new Ext.Panel({
		border : false,
		layout : 'border',
		items : [systemParaPanel, systemGrid, systemHandle]
	})
	
	renderModel(panel,'系统异常');
//-------------------------------------------------------------异常处理 end			
});