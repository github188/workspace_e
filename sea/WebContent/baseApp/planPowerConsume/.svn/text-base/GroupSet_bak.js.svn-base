
Ext.onReady(function(){
	
	/**
	 * 群组模拟数据
	 */
	var data = [{
		orgNo:'1',
		custNo:'用户编号',
		tmnlAssetNo:'终端资产编号',
		totalNo:'总加组号',
		tradeCode:'行业',
		lineId:'线路',
		CAId:'客户地址',
		elecTypeCode:'用电类别',
		runCap:'运行容量'
	},{
		orgNo:'2',
		custNo:'用户编号1',
		tmnlAssetNo:'终端资产编号1',
		totalNo:'总加组号1',
		tradeCode:'行业1',
		lineId:'线路1',
		CAId:'客户地址1',
		elecTypeCode:'用电类别1',
		runCap:'运行容量1'
	},{
		orgNo:'3',
		custNo:'用户编号3',
		tmnlAssetNo:'终端资产编号3',
		totalNo:'总加组号3',
		tradeCode:'行业3',
		lineId:'线路3',
		CAId:'客户地址3',
		elecTypeCode:'用电类别3',
		runCap:'运行容量3'
	}];
	
	//数据格式
	var store = new Ext.data.JsonStore({
		data:data,
		fields:['orgNo','custNo','tmnlAssetNo','totalNo','tradeCode','lineId','CAId','elecTypeCode','runCap']
	});
	
	//Grid头
	var colM = new Ext.grid.ColumnModel([{
		header:'供电单位',
		dataIndex:'orgNo'	
	},{
		header:'用户编码',
		dataIndex:'custNo'
	},{
		header:'总加组号 ',
		dataIndex:'totalNo'
	},{
		header:'行业',
		dataIndex:'tradeCode'
	},{
		header:'线路',
		dataIndex:'lineId'
	},{
		header:'客户地址',
		dataIndex:'CAId'
	},{
		header:'用电类别',
		dataIndex:'elecTypeCode'
	},{
		header:'运行容量',
		dataIndex:'runCap'
	}]);
	
	/**
	 * 群组数据
	 */
	var groupData = [{
		groupName:'群组',
		orgNo:'所属单位',
		custName:'创建者',
		createDate:'2009-2-3',
		isShare:'是'
	},{
		groupName:'群组1',
		orgNo:'所属单位1',
		custName:'创建者1',
		createDate:'2009-3-3',
		isShare:'否'
	}];
	
	var groupStore = new Ext.data.JsonStore({
		data:groupData,
		fields:['groupName','orgNo','custName','createDate','isShare']
	});
	
	var groupSM = new Ext.grid.CheckboxSelectionModel();
	var groupColM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),groupSM,{
		header:'群组名称',
		dataIndex:'groupName',
		align:'center'
	},{
		header:'所属单位',
		dataIndex:'orgNo',
		align:'center'
	},{
		header:'创建者',
		dataIndex:'custName',
		align:'center'
	},{
		header:'创建时间',
		dataIndex:'createDate',
		align:'center'
	},{
		header:'是否共享',
		dataIndex:'isShare',
		align:'center'
	}]);
	/**
	 * 主界面
	 */
	new Ext.TabPanel({
		id:'tab',
		renderTo:'GroupSet',
		activeTab:0,
		frame:true,
		border:false,
		//defualts:{autoHeight:true},
		autoHeight:true,
		items:[{
			/**
			 * 群组生成
			 */
			xtype:'panel',
			title:'群组生成',
			autoHeiht:true,
			border:false,
			items:[{
				/**
				 * 查询条件 panel
				 */
				//title:'查询条件',
				xtype:'panel',
				height:180,
				border:false,
				items:[{
					xtype:'panel',
					html:'查询',
					height:140,
					layout:'border',
					border:false,
					items:[{
						/**
						 * 左边查询条件
						 */
						xtype:'panel',
						region:'west',
						bodyStyle:'padding:20px;',
						width:400,
						layout:'form',
						hideLables:false,
						border:false,
						labelAlign:'right',
						items:[{
							fieldLabel:'供电单位1',
							xtype:'combo',
							width:160
						},{
							fieldLabel:'用户编号',
							xtype:'textfield',
							width:160
						},{
							fieldLabel:'运行容量',
							xtype:'combo',
							width:160
						}]
					},{
						/**
						 * 右边查询条件
						 */
						xtype:'panel',
						region:'center',
						width:400,
						bodyStyle:'padding:20px',
						layout:'form',
						hideLables:false,
						border:false,
						labelAlign:'right',
						items:[{
							fieldLabel:'总线路',
							xtype:'combo',
							width:160
						},{
							fieldLabel:'总加组号',
							xtype:'combo',
							width:160
						},{
							fieldLabel:'接负控开关',
							xtype:'checkbox'
						},{
							fieldLabel:'是否共享',
							xtype:'checkbox'
						}]
					}]
				},{
					/**
					 * 查询条件按钮
					 */
					xtype:'panel',
					border:false,
					layout:'column',
					height:30,
					buttons:[{
						text:'查询'
					},{
						text:'新增群组'
					},{
						text:'添加到群组'
					}]
				}]
			},{
				/**
				 * 查询结果 Grid
				 */
				//title:'查询结果',
				xtype:'grid',
				height:400,
				cm:colM,
				store:store
			}]
		},{
			/**
			 * 群组管理
			 */
			xtype:'panel',
			title:'群组管理',
			border:false,
			autoHeight:true,
			items:[{
				xtype:'panel',
				height:'50',
				border:false,
				layout:'column',
				items:[{
					xtype:'panel',
					layout:'form',
					hideLables:false,
					border:false,
					bodyStyle:'padding:6px',
					labelAlign:'right',
					columnWidth:.32,
					items:[{
						fieldLabel:'群组名称',
						xtype:'combo',
						width:160
					}]
				},{
					bodyStyle:'padding:6px',
					columnWidth:.5,
					border:false,
					xtype:'panel',
					items:[{
						xtype:'button',
						text:'查询',
						width:80
					}]
				}]
			},{
				xtype:'grid',
				autoHeight:true,
				viewConfig:{
					forceFit:true
				},
				cm:groupColM,
				sm:groupSM,
				store:groupStore,
				tbar:[{
					text:'更名'
				},'-',{
					text:'共享'
				},'-',{
					text:'删除'
				},'-',{
					text:'用户管理',
					handler:function(){
						window_user.show();	
					}
				}]
			}]
		}]
	});
	
//-----------------------------------------------------------------------------群组生成 begin
//-----------------------------------------------------------------------------群组生成 end
	
//-----------------------------------------------------------------------------用户管理 begin
	
	var group_user_cond = new Ext.Panel({
		border:false,
		layout:'column',
		bodyStyle:'padding:10px',
		style:'font-size:12px',
		labelAlign:'right',
		autoHeight:true,
		items:[{
			columnWidth:.06,
			xtype:'label',
			text:'群组名'
		},{
			columnWidth:.25,
			xtype:'textfield'
		},{
			columnWidth:.02,
			xtype:'label'
		},{
			columnWidth:.08,
			xtype:'label',
			text:'用户编号'
		},{
			columnWidth:.25,
			xtype:'textfield'
		},{
			columnWidth:.04,
			xtype:'label'
		},{
			xtype:'button',
			text:'查询',
			width:80
		}]
	});
	
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
		title:'用户管理',
		autoHeight:true,
		viewConfig:{
			forceFit:true
		},
		cm:userCM,
		sm:userSM,
		store:userStore,
		tbar:[{
			text:'删除用户'
		}]
	});
	
	var window_user = new Ext.Window({
		name:'window_user',
		title:'用户管理',
		width:800,
		height:600,
		modal:true,
		resizable:false,
		autoScroll:true,
		//autoHeight:true,
		closeAction:'hide',
		items:[group_user_cond,userGrid]
	});
//-----------------------------------------------------------------------------用户管理 end	
});