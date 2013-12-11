function openDialog(url){
	var  iWidth=800; //模态窗口宽度
	var  iHeight=500;//模态窗口高度
	var  iTop=(window.screen.height-iHeight)/2;
	var  iLeft=(window.screen.width-iWidth)/2;
	window.showModalDialog(url,'','resizable:no;dialogHeight:'+iHeight+';dialogWidth:'+iWidth+';dialogTop:'+iTop+';dialogLeft:'+iLeft);
}
Ext.onReady(function(){
//-------------------------------------------------------------营业报停控参数 begin
	var businessParamPanel = new Ext.form.FieldSet({
		title:'营业报停控参数',
		buttonAlign:'center',
		items:[{
			border:false,
			layout:'column',
			items:[{
				columnWidth:.32,
				border:false,
				layout:'form',
				labelSeparator:' ',
				labelAlign:'right',
				items:[{
					xtype:'textfield',
					fieldLabel:'功控定值'
				}]
			},{
				xtype:'label',
				text:'kW'
			}]
		},{
			border:false,
			layout:'column',
			items:[{
				columnWidth:.32,
				border:false,
			    layout:'form',
				labelSeparator:' ',
				labelAlign:'right',
				items:[{
					xtype:'datefield',
					fieldLabel:'从',
					format: 'Y-m-d',
					value: new Date(),
					width:136
				}]
			},{
				columnWidth:.25,
				border:false,
			    layout:'form',
				labelSeparator:' ',
				labelAlign:'right',
				labelWidth:10,
				items:[{
					xtype:'datefield',
					fieldLabel:'到',
					format: 'Y-m-d',
					value: new Date(),
					width:136
				}]
			},{
				columnWidth:.14,
				border:false,
				layout:'form',
				items:[{
					xtype:'button',
					text:'召测',
					width:80
				}]
			},{
				columnWidth:.12,
				border:false,
				layout:'form',
				items:[{
					xtype:'button',
					text:'下发',
					width:80
				}]
			}]
		}]
		
	});
	businessParamPanel.render('powerControlBusiness');
//-------------------------------------------------------------营业报停控参数 end
	
//-------------------------------------------------------------方案执行 begin
	var schemeExecute = new Ext.form.FieldSet({
		title:'方案执行',
		style:'font-size:12px',
		layout:'column',
		bodyStyle:'padding:0px 20px',
		items:[{
			columnWidth:.14,
			xtype:'label',
			text:'营业报停控执行'
		},{
			columnWidth:.1,
			xtype:'radio',
			boxLabel:'投入'
		},{
			columnWidth:.33,
			xtype:'radio',
			boxLabel:'解除'
		},{
			columnWidth:.15,
			xtype:'panel',
			border:false,
			layout:'form',
			items:[{
				xtype:'button',
				text:'执行',
				width:80
			}]
		},{
			columnWidth:.2,
			xtype:'panel',
			border:false,
			layout:'form',
			items:[{
				xtype:'button',
				text:'加载用户',
				width:80,
				handler:function(){
					openDialog('./baseApp/planPowerConsume/userLoad.jsp');
				}
			}]
		}]
	});
	schemeExecute.render('powerControlBusiness');
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
		},'-']
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
	user_callTest.render('powerControlBusiness');	
})