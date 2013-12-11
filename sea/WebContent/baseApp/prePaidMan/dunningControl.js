/*
 * ! Author: Longkongcao Date:11/27/2009 Description: 催费控制 Update History: none
 */
 
 function origFrameQryShow(consNo,consName,tmnlAssetAddr){
	staticConsNo = consNo;
	staticConsName = consName;
	staticTmnlAssetAddr = tmnlAssetAddr;
	openTab("原始报文查询","./baseApp/dataGatherMan/origFrameQry.jsp");
}

Ext.onReady(function() {
	// grid解锁
	function unlockGrid() {//取消全选时，解锁Grid
		userSm.unlock();
		userGrid.onEnable();
		userGrid.getBottomToolbar().enable();
		Ext.getCmp('gsselectAllcb').setValue(false);
	}
	// grid锁定
	function lockGrid() {
		userSm.lock();
		userGrid.onDisable();
		userGrid.getBottomToolbar().disable();
	}	
	
	// 参数模板
	var totalDataStore = new Ext.data.ArrayStore({
				fields : ['totalID', 'totalText'],
				data : [['totalData1', '10086999'],
						["totalData2", '10086888']]
			})

	var paraComboBox = new Ext.form.ComboBox({
				fieldLabel : '告警参数模板',
				labelSeparator : '',
				store : totalDataStore,
				triggerAction : 'all',
				mode : 'local',
				valueField : 'totalID',
				displayField : 'totalText',
				anchor : '90%',
				emptyText : 'Select a state...',
				selectOnFocus : true
			});

	var ds = new Ext.data.ArrayStore({
				data : [[20, '20:00至21:00'], [21, '21:00至22:00'], 
						[22, '22:00至23:00'],[23, '23:00至00:00'], 
						[0, '00:00至01:00'],[1, '01:00至02:00'],
						[2, '02:00至03:00'], [3, '03:00至04:00'],
						[4, '04:00至05:00'], [5, '05:00至06:00'],
						[6, '06:00至07:00'], [7, '07:00至08:00'],
						[8, '08:00至09:00'], [9, '09:00至10:00'],
						[10, '10:00至11:00'], [11, '11:00至12:00'],
						[12, '12:00至13:00'], [13, '13:00至14:00'],
						[14, '14:00至15:00'], [15, '15:00至16:00'],
						[16, '16:00至17:00'], [17, '17:00至18:00'],
						[18, '18:00至19:00'], [19, '19:00至20:00']],
				fields : ['value', 'text'],
				sortInfo : {
					field : 'value',
					direction : 'ASC'
				}
			});

	var selectPanel = new Ext.Panel({
			id : 'dcSelectPanel',
			bodyStyle : 'padding: 10px 0px 10px 20px',
			border : false,
			width : 400,
			height : 240,
			items : [{
				xtype : 'itemselector',
				name : 'itemselector',
				imagePath : 'images/',
				drawUpIcon : false,
				drawDownIcon : false,
				drawTopIcon : false,
				drawBotIcon : false,
				titleLeft : '备选时段',
				titleRight : '催费告警时段',
				multiselects : [{
						width : 180,
						height : 200,
						store : ds,
						displayField : 'text',
						valueField : 'value'
					}, {
						width : 180,
						height : 200,
						name:'dunningTimes',
						store : []
				}]
			}]
		})

	var outPanel = new Ext.Panel({
				border : false,
				layout : 'table',
				region : 'north',
				items : [{
							width : 150,
							layout : 'form',
							border : false,
							items : []
						}, {
							width : 90,
							layout : 'form',
							border : false,
							bodyStyle : 'padding: 10px 0px 10px 0px',
							items : [{
										xtype : 'button',
										width : '70',
										text : '方案下发'
									}]
						}, {
							width : 90,
							layout : 'form',
							border : false,
							bodyStyle : 'padding: 10px 0px 10px 0px',
							items : [{
										xtype : 'button',
										width : '70',
										text : '方案解除'
									}]
						}, {
							width : 90,
							layout : 'form',
							border : false,
							bodyStyle : 'padding: 10px 0px 10px 0px',
							items : [{
										xtype : 'button',
										width : '70',
										text : '方案加载'
									}]
						}, {
							width : 90,
							layout : 'form',
							border : false,
							bodyStyle : 'padding: 10px 0px 10px 0px',
							items : [{
										xtype : 'button',
										width : '70',
										text : '保存为方案'
									}]
						}]
			})

	var buttonPanel = new Ext.Panel({
				border : false,
				layout : 'table',
				layoutConfig : {
					columns : 1
				},
				width : 200,
				bodyStyle : 'padding: 10px 0px 10px 20px',
				items : [{
							layout : 'form',
							border : false,
							width : 100,
							bodyStyle : 'padding: 0px 0px 15px 0px',
							items : [{
										xtype : 'button',
										width : 70,
										text : '新建模板',
										handler : function() {
											var sd = selectPanel.find('name','itemselector')[0];
											var dunningTimesArray = sd.toMultiselect.store.data;
											alert(dunningTimesArray.length);
											var dunningTimes = '';
											for (var k = 0; k < 24; k++) {
												var flag = 0;
												for (var i = 0; i < dunningTimesArray.length; i++) {
													var val = dunningTimesArray.get(i).get('value');
													if(val ==k) {
														dunningTimes +='1';
														flag = 1;
													}
												}
												if(flag ==0) {
													dunningTimes +='0';
												}
											}
											alert(dunningTimes);
										}
									}]
						}, {
							layout : 'form',
							border : false,
							width : 100,
							bodyStyle : 'padding: 0px 0px 15px 0px',
							items : [{
										xtype : 'button',
										width : 70,
										text : '模板保存'
									}]
						}]

			})

	var boxFormPanel = new Ext.Panel({
				border : false,
				region : 'north',
				height : 250,
				layout : 'table',
				layoutConfig : {
					columns : 2
				},
				items : [{
							layout : 'form',
							border : false,
							labelAlign : 'right',
							labelWidth : 85,
							bodyStyle : 'padding: 10px 0px 0px 10px',
							width : 300,
							items : [paraComboBox]
						}, {
							layout : 'form',
							border : false,
							width : 300,
							items : []
						}, selectPanel, buttonPanel, outPanel]
			})

	var userSm = new Ext.grid.CheckboxSelectionModel();

	var userCm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),userSm, 
			{header : '供电单位编号',dataIndex : 'orgNo',align : 'center',hidden:true}, 
			{header : '供电单位',dataIndex : 'orgName',align : 'center'}, 
			{header : '用户编号',dataIndex : 'consNo',align : 'center'}, 
			{header : '用户名',	dataIndex : 'consName',align : 'center'},
			{header : '终端资产编号',dataIndex : 'tmnlAssetNo',align : 'center',hidden:true}, 
			{header : '终端地址',dataIndex : 'terminalAddr',align : 'center'}, 
			{header : '终端规约',dataIndex : 'protocolCode',	align : 'center',hidden:true}, 
			{header : '控制状态',dataIndex : 'ctrlFlag',align : 'center'	}, 
			{header : '执行结果',dataIndex : 'execFlag',align : 'center'}, 
			{header : '报文',	dataIndex : 'message',sortable : true,	align : 'center',	renderer: function(s, m, rec){
					return "<a href='javascript:'onclick='origFrameQryShow(\""+rec.get('consNo')+"\",\""+ rec.get('consName')+"\",\""+ rec.get('terminalAddr')+"\");" + "'>查看</a>"; 
			}}]);

	var userStore = new Ext.data.Store({
			url : './baseapp/dunningControl!queryDunningControlUser.action',
			reader : new Ext.data.JsonReader({
			root : 'dunningControlList',
				idProperty: 'tmnlAssetNo'
			}, [{name : 'orgName'},
				{name : 'orgNo'},
				{name : 'consNo'},
				{name : 'consName'},
				{name : 'terminalAddr'},
				{name : 'tmnlAssetNo'},
				{name: 'tmnlPaulPower'},
				{name : 'ctrlFlag'},
				{name:'execFlag'},
				{name:'protocolCode'},
				{name : 'message'}])
		});
		
		//时段控分页store		
	var userListStore = new Ext.data.Store({
			remoteSort : true,
			proxy : new Ext.data.MemoryProxy(),
			reader : new Ext.data.ArrayReader({
				idIndex : 5,
				fields :  [
					{name : 'orgName'},
					{name : 'orgNo'},
					{name : 'consNo'},
					{name : 'consName'},
					{name : 'terminalAddr'},
					{name : 'tmnlAssetNo'},
					{name : 'tmnlPaulPower'},
					{name : 'ctrlFlag'},
					{name:'execFlag'},
					{name:'protocolCode'},
					{name : 'message'}]
				})
		});	
		
	function dunningStoreToArray(valStore) {
		var data = new Array();
		for (var i = 0; i < valStore.getCount(); i++) {
			data[i] = new Array();
			var index = 0;
			data[i][index++] = valStore.getAt(i).data.orgName;
			data[i][index++] = valStore.getAt(i).data.orgNo;
			data[i][index++] = valStore.getAt(i).data.consNo;
			data[i][index++] = valStore.getAt(i).data.consName;
			data[i][index++] = valStore.getAt(i).data.terminalAddr;
			data[i][index++] = valStore.getAt(i).data.tmnlAssetNo;
			data[i][index++] = valStore.getAt(i).data.tmnlPaulPower;
			data[i][index++] = valStore.getAt(i).data.ctrlFlag;
			data[i][index++] = valStore.getAt(i).data.execFlag;
			data[i][index++] = valStore.getAt(i).data.protocolCode;
			data[i][index++] = valStore.getAt(i).data.message;
		}
		return data;
	}		

	var userGrid = new Ext.grid.GridPanel({
				id : 'usergrid',
				store : userListStore,
				cm : userCm,
				sm : userSm,
				region : 'center',
				autoExandColumn : 'hh',
				tbar : [{
							xtype : 'button',
							width : 70,
							text : '参数下发'
						}, {
							xtype : 'button',
							width : 70,
							text : '参数召测'
						},{xtype: 'tbfill'},{
							xtype : 'checkbox',
							id : 'gsselectAllcb',
							boxLabel : '全选',
							name : 'gsselectAllcb',
							checked : false,
							listeners : {
									'check' : function(r, c) {
										if (c) {
											userSm.selectAll();
											lockGrid();
										}else {
											unlockGrid();
											userSm.clearSelections()
										}
									}
								}
							},{
							iconCls: 'minus',
							text : '删除选中行',
							handler : function() {
							if (Ext.getCmp('gsselectAllcb').checked) {
								userStore.removeAll(true);
								unlockGrid();
								Ext.getCmp('gsselectAllcb').setValue(false);
							} else {
								var recs = userSm.getSelections();
								for (var i = 0; i < recs.length; i = i + 1) {
									userStore.remove(userStore.getById(recs[i].data.tmnlAssetNo));
								}
							}
							userListStore.proxy = new Ext.ux.data.PagingMemoryProxy(dunningStoreToArray(userStore));
							userListStore.load({
								params : {
									start : 0,
									limit : DEFAULT_PAGE_SIZE
								}
							});
						}
						}, {
							iconCls: 'plus',
							text : '加入群组',
							handler:function(){
								var groupTmnlArray = new Array();
								if (Ext.getCmp('gsselectAllcb').checked) {
									for (var i = 0; i < userStore.getCount(); i++) {
										var tmnl = userStore.getAt(i).get('consNo')
												+ '`'+ userStore.getAt(i).get('tmnlAssetNo');
										groupTmnlArray[i] = tmnl;
									}
								} else {
									var recs = userSm.getSelections();
									for (var i = 0; i < recs.length; i++) {
										var tmnl = recs[i].get('consNo') + '`'+ recs[i].get('tmnlAssetNo');
										groupTmnlArray[i] = tmnl;
									}
								}
								if (groupTmnlArray.length == 0) {
									Ext.Msg.alert('提示', '请选择要加入群组的用户');
								} else {
									saveOrUpdateGroupWindowShow(groupTmnlArray);
									if (Ext.getCmp('gsselectAllcb').checked) {
										Ext.getCmp('gsselectAllcb').setValue(false);
										userSm.selectAll();
									}
								}
			                 }
						}, {
							iconCls: 'minus',
							text : '删除成功用户',
							handler : ''
						}],
				bbar : new Ext.ux.MyToolbar({
							store : userListStore
						})
			});
	//下方的tab页
	var userTab = new Ext.TabPanel({
			activeTab : 0,
			region : 'center',
			id : 'userTab',
			items : [{
						xtype : "panel",
						title : "用户列表",
						layout : 'fit',
						items : [userGrid]
					}, {
						xtype : "panel",
						title : "召测结果",
						layout : 'fit',
						items : []
					}],
			border : false
		})

	var centerPanel = new Ext.Panel({
			border : false,
			layout : 'border',
			region : 'center',
			items : [outPanel, userTab]
		})

	var dcPanel = new Ext.form.FormPanel({
		title : '告警参数',
		border : false,
		layout : 'border',
		items : [boxFormPanel, centerPanel]
	});
	
	userStore.on('load',function(thisstore, recs, obj){
		userListStore.proxy = new Ext.ux.data.PagingMemoryProxy(dunningStoreToArray(thisstore));
		userListStore.load({
				params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
			});
		userSm.selectAll();//默认全选
		userGrid.doLayout();
	});
	
	userListStore.on('load',function() {
		userSm.selectAll();
	});
	
	//左边树点击事件触发函数
	function clickEvent(id) {
		userStore.baseParams = {
			nodeId : id
		};
	    userStore.load({
	    	callback : function(recs, options, success) {
	    		Ext.getBody().unmask();		
				if (success)
					userGrid.getSelectionModel().selectRecords(recs, true);
			},
	  	    add: true
	    });
	}
	
	//  监听左边树点击事件
    var treeListener = new LeftTreeListener({
	    modelName : '功控方案编制',
	    processClick : function(p, node, e) {
	    	var id = node.id;
		    clickEvent(id);
	    },
	    processUserGridSelect: function(cm,row,record){
	    	var id = 'usr' + '_' + record.get('tmnlAssetNo');
   		  clickEvent(id);
		  return true;
   		}
    });
    
	renderModel(dcPanel, '催费控制')
})