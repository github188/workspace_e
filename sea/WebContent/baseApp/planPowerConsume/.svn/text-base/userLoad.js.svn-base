Ext.onReady(function(){

//------------------------------------------------------------加载类别 begin
	var condPanel = new Ext.Panel({
		layout:'column',
		border:false,
		bodyStyle:'padding:10px 10px;',
		style:'font-size:12px',
		items:[{
			columnWidth:.08,
			xtype:'label',
			text:'加载类别'
		},{
			columnWidth:.1,
			name:"condRadio",
			inputValue:'group',
			xtype:'radio',
			boxLabel:'群组加载'
			//checked:true
		},{
			columnWidth:.2,
			name:"condRadio",
			inputValue:'user',
			xtype:'radio',
			boxLabel:'用户加载',
			checked:true,
			listeners:{
				check:function(checkbox,checked){
					if(checked){
						Ext.getCmp('bodyPanel').layout.setActiveItem(1);
					}else{
						Ext.getCmp('bodyPanel').layout.setActiveItem(0);
					}
				}
			}
		}]
	});
	
//------------------------------------------------------------加载类别 end
	
//------------------------------------------------------------群组加载 begin

	var groupCond = new Ext.Panel({
		layout:'column',
		border:false,
		style:'font-size:12px',
		bodyStyle:'padding:10px 10px;',
		items:[{
			columnWidth:.1,
			xtype:'label',
			text:'群组名称'
//			html:"<a href='#' onclick='test_f();'>群</a>"
		},{
			columnWidth:.2,
			xtype:'textfield'
		},{
			columnWidth:.5,
			xtype:'label'
		},{
			xtype:'button',
			text:'查询',
			width:80
		},{
			columnWidth:.04,
			xtype:'label'
		},{
			xtype:'button',
			text:'加载',
			width:80
		}]
	});

	
	var groupData = [{
		groupName:'群组名称',
		groupId:'群组ID',
		unit:'所属单位',
		creator:'创建者',
		createTime:'2009-3-3'
	}];
	
	var groupStore = new Ext.data.JsonStore({
		data:groupData,
		fields:['groupName','groupId','unit','creator','createTime']
	});
	
	var groupSM = new Ext.grid.CheckboxSelectionModel();
//	var dd = new Ext.grid.
	
	var groupCM = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		groupSM,
		{header:'群组名称',dataIndex:'groupName',sortable:true,align:'center'},
		{header:'群组ID',dataIndex:'groupId',sortable:true,align:'center'},
		{header:'所属单位',dataIndex:'unit',sortable:true,align:'center'},
		{header:'创建者',dataIndex:'creator',sortable:true,align:'center'},
		{header:'创建时间',dataIndex:'createTime',sortable:true,align:'center'}
	]);
	
	var groupGrid = new Ext.grid.GridPanel({
		title:'群组查询结果',
		autoHeight:true,
		autoScroll:true,
		viewConfig:{
			forceFit:true
		},
		cm:groupCM,
		sm:groupSM,
		store:groupStore
	});
	
	groupBody = new Ext.Panel({
		border:false,
		items:[groupCond,groupGrid]
	});
//	groupBody.render("userload");
//------------------------------------------------------------群组加载 end

//------------------------------------------------------------用户加载 begin

	var userCondLeft = new Ext.FormPanel({
		labelAlign:'right',
		bodyStyle:'padding:10px',
		border:false,
		items:[{
			xtype:'combo',
			width:160,
			fieldLabel:'供电单位'
		},{
			xtype:'textfield',
			width:160,
			fieldLabel:'用户编号'
		}]
	});
	
	var userCondRight = new Ext.FormPanel({
		labelAlign:'right',
		bodyStyle:'padding:10px',
		border:false,
		items:[{
			xtype:'combo',
			width:160,
			fieldLabel:'总加组'
		},{
			xtype:'textfield',
			width:160,
			fieldLabel:'用户名'
		}]
	});
	
	var userCondBottom = new Ext.Panel({
		border:false,
		bodyStyle:'padding:10px',
		layout:'column',
		style:'font-size:12px',
		items:[{
			columnWidth:.05,
			xtype:'label'
		},{
			columnWidth:.23,
			xtype:'checkbox',
			checked:true,
			boxLabel:'接负控开关'
		},{
			columnWidth:.31,
			xtype:'label'
		},{
			columnWidth:.1,
			xtype:'button',
			text:'查询'
		},{
			columnWidth:.02,
			xtype:'label'
		},{
			columnWidth:.1,
			xtype:'button',
			text:'加载'
		}]
	});
//	userCondBottom.render('userload');
	
	var userCond = new Ext.Panel({
		border:false,
		layout:'column',
		items:[{
			border:false,
			columnWidth:.5,
			items:[userCondLeft]
		},{
			border:false,
			columnWidth:.5,
			items:[userCondRight]
		}]
	});
	
	var userAllCond = new Ext.Panel({
		border:false,
		items:[userCond,userCondBottom]
	});
//	userAllCond.render('userload');
	
	var userData = [{
		gddw:'供电单位',
		yhbh:'用户编号',
		zdzcbh:'终端资产编号',
		zjzh:'总加组号',
		hy:'行业',
		xl:'线路',
		khdz:'客户地址',
		ydlb:'用电类别',
		yxrl:'运行容量'
	}];
	
	var userStore = new Ext.data.JsonStore({
		data:userData,
		fields:['gddw','yhbh','zdzcbh','zdzcbh','zjzh','hy','xl','khdz','ydlb','yxrl']
	});
	
	var userSM = new Ext.grid.CheckboxSelectionModel();
	
	var userCM = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		userSM,
		{header:'供电单位',dataIndex:'gddw',sortable:true,align:'center'},
		{header:'用户编号',dataIndex:'yhbh',sortable:true,align:'center'},
		{header:'终端资产编号',dataIndex:'zdzcbh',sortable:true,align:'center'},
		{header:'总加组号',dataIndex:'zjzh',sortable:true,align:'center'},
		{header:'行业',dataIndex:'hy',sortable:true,align:'center'},
		{header:'线路',dataIndex:'xl',sortable:true,align:'center'},
		{header:'客户地址',dataIndex:'khdz',sortable:true,align:'center'},
		{header:'用电类别',dataIndex:'ydlb',sortable:true,align:'center'},
		{header:'运行容量',dataIndex:'yxrl',sortable:true,align:'center'}
	]);
	
	var userGrid = new Ext.grid.GridPanel({
		title:'用户查询结果',
		autoHeight:true,
		viewConfig:{
			forceFit:true
		},
		cm:userCM,
		sm:userSM,
		store:userStore
	});
	
	var userBody = new Ext.Panel({
		border:false,
		items:[userAllCond,userGrid]
	});
//	userBody.render('userload');
//------------------------------------------------------------用户加载 end
	
	var bodyPanel = new Ext.Panel({
		id:'bodyPanel',
		layout:'card',
		activeItem:1,
		border:false,
		items:[groupBody,userBody]
	});
//	bodyPanel.render("userload");
	
	var mainPanel = new Ext.Panel({
		border:false,
		items:[condPanel,bodyPanel]
	});
	
	mainPanel.render("userload");
});