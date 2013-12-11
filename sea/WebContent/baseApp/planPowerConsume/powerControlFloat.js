Ext.onReady(function(){
//-------------------------------------------------------------当前功率下浮控参数 begin
	var floatParamPanel = new Ext.form.FieldSet({
		title:'当前功率下浮控参数',
		items:[{
			border:false,
			style:'font-size:12px',
			autoWidth:true,
			layout:'column',
			items:[{
				columnWidth:.18,
				xtype:'label',
				text:'当前功率下浮控定值滑差时间'
			},{
				columnWidth:.2,
				xtype:'textfield'
			},{
				columnWidth:.2,
				xtype:'label',
				text:'分钟（取值范围：1-60分钟）'
			}]
		},{
			border:false,
			style:'font-size:12px',
			autoWidth:true,
			layout:'column',
			bodyStyle:'padding:10px 0px;',
			items:[{
				columnWidth:.22,
				xtype:'label',
				text:'当前功率下浮控定值浮动系数'
			},{
				columnWidth:.1,
				xtype:'radio',
				boxLabel:'上浮'
			},{
				columnWidth:.1,
				xtype:'radio',
				boxLabel:'下浮'
			},{
				xtype:'textfield'
			},{
				xtype:'label',
				text:'取值范围：0-79%'
			}]
		},{
			border:false,
			style:'font-size:12px',
			autoWidth:true,
			layout:'column',
			items:[{
				columnWidth:.18,
				xtype:'label',
				text:'控后总加有功功率冻结延时时间'
			},{
				columnWidth:.2,
				xtype:'textfield'
			},{
				columnWidth:.2,
				xtype:'label',
				text:'分钟（取值范围：5-60分钟）'
			}]
		}]
	});
	floatParamPanel.render('powerControlFloat');
//-------------------------------------------------------------当前功率下浮控参数 end	
//-------------------------------------------------------------方案执行 begin
	var schemeExecute = new Ext.form.FieldSet({
		title:'方案执行',
		style:'font-size:12px',
		layout:'column',
		bodyStyle:'padding:0 15',
		items:[{
			columnWidth:.1,
			xtype:'label',
			text:'当前功率下浮控'
		},{
			columnWidth:.1,
			xtype:'radio',
			boxLabel:'投入'
		},{
			columnWidth:.1,
			xtype:'radio',
			boxLabel:'解除'
		},{
			columnWidth:.2,
			xtype:'panel',
			border:false,
			layout:'form',
			items:[{
				xtype:'button',
				text:'执行',
				width:80
			}]
		}]
	});
	schemeExecute.render('powerControlFloat');
//-------------------------------------------------------------方案执行 end	
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
	user_callTest.render('powerControlFloat');	
});