Ext.onReady(function(){
//-------------------------------------------------------------厂休控方案模板 begin	
	//限电日
	var xdrForm = new Ext.FormPanel({
		layout:'form',
		border:false,
		labelAlign:'right',
		labelSeparator:' ',
		items:[{
			xtype:'checkboxgroup',
			fieldLabel:'限电日',
			items:[{
				name:'weekcheck',
				boxLabel:'周一'
			},{
				name:'weekcheck',
				boxLabel:'周二'
			},{
				name:'weekcheck',
				boxLabel:'周三'
			},{
				name:'weekcheck',
				boxLabel:'周四'
			},{
				name:'weekcheck',
				boxLabel:'周五'
			},{
				name:'weekcheck',
				boxLabel:'周六'
			},{
				name:'weekcheck',
				boxLabel:'周日'
			}]
		}]
	});
	//延续时间 
	var yxsjPanel = new Ext.Panel({
		border:false,
		layout:'column',
		style:'font-size:12px',
		items:[{
			columnWidth:.23,
			xtype:'panel',
			border:false,
			layout:'form',
			labelSeparator:' ',
			labelAlign:'right',
//			labelStyle:'valign:middle',
			items:[{
				xtype:'textfield',
				fieldLabel:'延续时间',
				width:100
			}]
		},{
			columnWidth:.05,
			xtype:'label',
			text:'小时'
		}]
	});
	//起始时间
	var qssjPanel = new Ext.Panel({
		border:false,
		layout:'column',
		items:[{
			xtype:'panel',
			border:false,
			layout:'form',
			labelSeparator:' ',
			labelAlign:'right',
			items:[{
				xtype:'timefield',
				fieldLabel:'起始时间'
			}]
		}]
	});
	//厂休控定值
	var cxkdzPanel = new Ext.Panel({
		border:false,
		layout:'column',
		style:'font-size:12px',
		items:[{
			columnWidth:.23,
			xtype:'panel',
			border:false,
			layout:'form',
			labelSeparator:' ',
			labelAlign:'right',
//			labelStyle:'valign:middle',
			items:[{
				xtype:'textfield',
				fieldLabel:'厂休控定值',
				width:100
			}]
		},{
			columnWidth:.05,
			xtype:'label',
			text:'kW'
		}]
	});
	//模板名称
	var templatNamePanel = new Ext.Panel({
		border:false,
		layout:'form',
		labelSeparator:' ',
		labelAlign:'right',
		items:[{
			xtype:'combo',
			fieldLabel:'模板名称'
		}]
	});
	var schemeTemplat = new Ext.form.FieldSet({
		title:'厂休控方案模板',
		items:[templatNamePanel,cxkdzPanel,qssjPanel,yxsjPanel,xdrForm],
		buttonAlign:'center',
		buttons:[{
			text:'保存模板'
		},{
			text:'另存模板'
		}]
	});
	schemeTemplat.render('powerControlFactoryRest');
//-------------------------------------------------------------厂休控方案模板 end	
//-------------------------------------------------------------厂休控方案执行 begin
	var schemeExecute = new Ext.form.FieldSet({
		title:'厂休控方案执行',
		style:'font-size:12px',
		layout:'column',
		bodyStyle:'padding:0 15',
		items:[{
			columnWidth:.05,
			xtype:'label',
			text:'厂休控'
		},{
			columnWidth:.1,
			xtype:'radio',
			boxLabel:'投入'
		},{
			columnWidth:.1,
			xtype:'radio',
			boxLabel:'解除'
		},{
			columnWidth:.1,
			xtype:'panel',
			border:false,
			layout:'form',
			items:[{
				xtype:'button',
				text:'参数召测'
			}]
		},{
			columnWidth:.1,
			xtype:'panel',
			border:false,
			layout:'form',
			items:[{
				xtype:'button',
				text:'参数召测'
			}]
		},{
			columnWidth:.1,
			xtype:'panel',
			border:false,
			layout:'form',
			items:[{
				xtype:'button',
				text:'参数召测'
			}]
		}]
	});
	schemeExecute.render('powerControlFactoryRest');
//-------------------------------------------------------------厂休控方案执行 end	
//-------------------------------------------------------------用户列表-召测结果 begin
	var userListData = [{
		yhbh:'用户编号',
		hm:'户名',
		zdzcbh:'终端资产编号',
		zdgy:'终端规约',
		kzzt:'控制状态',
		zxjg:'执行结果',
		bw:'报文'
	}];
	
	var userListStore = new Ext.data.JsonStore({
		data:userListData,
		fields:['yhbh','hm','zdzcbh','zdgy','kzzt','zxjg','bw']
	});
	
	var userListSM = new Ext.grid.CheckboxSelectionModel();
	var userListCM = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		userListSM,
		{header:'用户编号',dataIndex:'yhbh',sortable:true,align:'center'},
		{header:'户名',dataIndex:'hm',sortable:true,align:'center'},
		{header:'终端资产编号',dataIndex:'zdzcbh',sortable:true,align:'center'},
		{header:'终端规约',dataIndex:'zdgy',sortable:true,align:'center'},
		{header:'控制状态',dataIndex:'kzzt',sortable:true,align:'center'},
		{header:'执行结果',dataIndex:'zxjg',sortable:true,align:'center'},
		{header:'报文',dataIndex:'bw',sortable:true,align:'center'}
	]);
	
	var userListGrid = new Ext.grid.GridPanel({
		store:userListStore,
		autoHeight:true,
		title:'用户列表',
		viewConfig:{
			forceFit:true
		},
		cm:userListCM,
		sm:userListSM,
		tbar:[{
			text:'删除选中行'
		},'-',{
			text:'加入群组'
		},'-',{
			text:'删除成功用户'
		},'-',{
			text:'加载用户'
		}]
	});
	
	var callTestResult = new Ext.Panel({
		title:'召测结果'
	});
//	user_callTest.render('powerControlPeriod');
//-------------------------------------------------------------用户列表-召测结果 end	
	var user_callTest = new Ext.TabPanel({
		activeTab:0,
		frame:true,
		border:false,
		autoHeight:true,
		items:[userListGrid,callTestResult]
	});
	user_callTest.render('powerControlFactoryRest');	
});