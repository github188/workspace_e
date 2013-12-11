//定义群组类型全局变量，0代表普通群组，1代表控制群组
var groupType;
Ext.onReady(function() {
	//全局变量   记录最近一次成功查询的条件
	var lastGroupType;
    var lastGroupName;
    var lastStartDate;
    var lastFinishDate;
    
	var opTypePanel = new Ext.Panel({
			region:'north',
			height:35,
			layout : 'table',
			bodyStyle : 'padding:10px 0px 0px 0px',
			border : false,
			items : [{
						border : false,
						width : 200,
						layout : 'form',
						items : [{
									xtype : 'radio',
									name : 'opType',
									boxLabel : '加入群组',
									checked : true
								}]
					}, {
						border : false,
						width : 200,
						layout : 'form',
						items : [{
									xtype : 'radio',
									name : 'opType',
									boxLabel : '管理群组',
									listeners : {
										check : function(checkbox, checked) {
											if (checked) {
												secondPanel.layout.setActiveItem(1);
											} else {
												secondPanel.layout.setActiveItem(0);
											}
										}
									}
								}]
					}]
			});
				
// ------------------------------------------------------------------------------------------新增群组-用户列表
// begin
	
	var rowNum_addGroupUserCM = new Ext.grid.RowNumberer({
			renderer : function(v, p, record, rowIndex) {
				var startRow = 0;
				if(addGroupUserStore && addGroupUserStore.lastOptions && addGroupUserStore.lastOptions.params){
					startRow = addGroupUserStore.lastOptions.params.start;
				}
				return startRow + rowIndex + 1;
			}
		});
	var sm_addGroupUserCM = new Ext.grid.CheckboxSelectionModel(); 
	var addGroupUserCM = new Ext.grid.ColumnModel([
		rowNum_addGroupUserCM,
	    sm_addGroupUserCM,
		{header:'供电单位',sortable:true,align:'center',dataIndex:'orgName',width:130,
			renderer : function (val) {
				    if(null!=val){
						var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">' + val + '</div>';
						return html;
				    }
					else
						return '';
					}},
		{header:'用户编号',sortable:true,align:'center',dataIndex:'consNo',width:80},
		{header:'用户名称',sortable:true,align:'center',dataIndex:'consName',width:100,
			renderer : function (val) {
					if(null!=val){
						var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">' + val + '</div>';
						return html;
					}
					else
						return '';
					}},
		{header:'用户地址',sortable:true,align:'center',dataIndex:'consAddr',width:100,
			renderer : function (val) {
					if(null!=val){
						var html = '<div align = "left" ext:qtitle="用户地址" ext:qtip="' + val + '">' + val + '</div>';
						return html;
					}
					else
						return '';
					}},
		{header:'终端地址',sortable:true,align:'center',dataIndex:'tmnlAddr',width:100},
		{header:'线路',sortable:true,align:'center',dataIndex:'line',width:60,
			renderer : function (val) {
					if(null!=val){
						var html = '<div align = "left" ext:qtitle="线路" ext:qtip="' + val + '">' + val + '</div>';
						return html;
					}
					else
						return '';
					}},
		{header:'行业',sortable:true,align:'center',dataIndex:'trade',width:100,
			renderer : function (val) {
				if(null!=val){
					var html = '<div align = "left" ext:qtitle="行业" ext:qtip="' + val + '">' + val + '</div>';
					return html;
				}
				else
					return '';
				}},
		{header:'运行容量',sortable:true,align:'center',dataIndex:'runCap',width:80}
	]);
	
	var userStore = new Ext.data.Store({
		url:'baseapp/groupSet!generalGridByTree.action',
		reader:new Ext.data.JsonReader({
			root : 'tTmnlGroupUserDtoList',
			idProperty:'tmnlAssetNo'
		},[
		   {name:'orgName'},
		   {name:'consNo'},
		   {name:'consName'},
		   {name:'consAddr'},
		   {name:'tmnlAddr'},
		   {name:'tmnlAssetNo'},
		   {name:'line'},
		   {name:'trade'},
		   {name:'runCap'}])
	});

	function storeToArray(valStore) {
	var data = new Array();
	for (var i = 0; i < valStore.getCount(); i++) {
		data[i] = new Array();
		data[i][0] = valStore.getAt(i).data.orgName;
		data[i][1] = valStore.getAt(i).data.consNo;
		data[i][2] = valStore.getAt(i).data.consName;
		data[i][3] = valStore.getAt(i).data.consAddr;
		data[i][4] = valStore.getAt(i).data.tmnlAddr;
		data[i][5] = valStore.getAt(i).data.tmnlAssetNo;
		data[i][6] = valStore.getAt(i).data.line;
		data[i][7] = valStore.getAt(i).data.trade;
		data[i][8] = valStore.getAt(i).data.runCap;
	}
	return data;
}
	
	var addGroupUserStore = new Ext.data.Store({
			remoteSort : true,
			proxy : new Ext.data.MemoryProxy(),
			reader : new Ext.data.ArrayReader({
						idIndex : 5,
						fields : [{
									name : 'orgName'
								}, {
									name : 'consNo'
								}, {
									name : 'consName'
								}, {
									name : 'consAddr'
								}, {
									name : 'tmnlAddr'
								}, {
									name : 'tmnlAssetNo'
								}, {
									name : 'line'
								}, {
									name : 'trade'
								}, {
									name : 'runCap'
								}]
					})
		});
	// grid解锁
	function unlockGrid() {
		sm_addGroupUserCM.unlock();
		addGroupUserGrid.onEnable();
		addGroupUserGrid.getBottomToolbar().enable();
	}

	// grid解锁
	function lockGrid() {
		sm_addGroupUserCM.lock();
		addGroupUserGrid.onDisable();
		addGroupUserGrid.getBottomToolbar().disable();
	}
	var gsselectAllcb=new Ext.form.Checkbox({
		boxLabel : '全选',
		name : 'gsselectAllcb',
		checked : false,
		listeners : {
			'check' : function(r, c) {
				if (c) {
					sm_addGroupUserCM.selectAll();
					lockGrid();
				} else {
					unlockGrid();
					sm_addGroupUserCM.clearSelections();
				}
			}
		}
	});
	var addGroupUserGrid = new Ext.grid.GridPanel({
		id:'addGroupUserGrid',
		region:'center',
		cm:addGroupUserCM,
		store:addGroupUserStore,
		sm:sm_addGroupUserCM ,
		stripeRows: true,
		tbar : [{
					xtype : 'label',
					html : "<font font-weight:bold;>备选用户</font>"
				}, {
					xtype : 'tbfill'
				}, '-', 
				gsselectAllcb,
				   '-', {
					text : '加入群组',
					iconCls : 'plus',
					handler : function() {
						var groupTmnlArray = new Array();
						if (gsselectAllcb.checked) {
							for (var i = 0; i < userStore.getCount(); i++) {
								var tmnl = userStore.getAt(i).get('consNo')
										+ '`'
										+ userStore.getAt(i).get('tmnlAssetNo');
								groupTmnlArray[i] = tmnl;
							}
						} else {
							var recs = sm_addGroupUserCM.getSelections();
							for (var i = 0; i < recs.length; i++) {
								var tmnl = recs[i].get('consNo') + '`'
										+ recs[i].get('tmnlAssetNo');
								groupTmnlArray[i] = tmnl;
							}
						}
						if (groupTmnlArray.length == 0) {
							Ext.Msg.alert('提示', '请选择要加入群组的用户');
						} else {
							saveOrUpdateGroupWindowShow(groupTmnlArray);
						}
						if(gsselectAllcb.checked){
							gsselectAllcb.setValue(false);
							sm_addGroupUserCM.selectAll();
						}
					}
				}, '-', {
					text : '删除选中用户',
					iconCls : 'cancel',
					handler : function() {
						if (gsselectAllcb.checked) {
							userStore.removeAll(true);
						} else {
							var recs = sm_addGroupUserCM.getSelections();
							for (var i = 0; i < recs.length; i = i + 1) {
								userStore.remove(userStore
										.getById(recs[i].data.tmnlAssetNo));
							}
						}
						addGroupUserStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
						addGroupUserStore.load({
									params : {
										start : 0,
										limit : DEFAULT_PAGE_SIZE
									}
								});
					}
				}],
			bbar : new Ext.ux.MyToolbar({
				store : addGroupUserStore,
				allStore: userStore,
				enableExpAll : true, // excel导出全部数据
			    expAllText : "全部",
				enableExpPage: true, // excel仅导出当前页
				expPageText : "当前页"
			})
	});
//-------------------------------------------------------------------------------------------新增群组-主界面
//end	

	//监听左边树点击事件
	var treeListener = new LeftTreeListener({
		 modelName : '群组设置',
		 processClick : function(p, node, e) {
		 	if("addGroupUserGrid"==secondPanel.getLayout().activeItem.getId()){
				  var obj = node.attributes.attributes;
				  var type = node.attributes.type;
				  if(type == 'usr'){
				  	userStore.baseParams = {tmnlAssetNo:obj.tmnlAssetNo,nodeType:type};
				  	Ext.getBody().mask("正在获取数据...");
				    userStore.load({
									callback : function(recs, options, success) {
//										if (success)
//											addGroupUserGrid.getSelectionModel()
//													.selectRecords(recs, true);
										Ext.getBody().unmask();			
									},
									add : true
								});
				  }
				  else if(type == 'org'){
				  	userStore.baseParams = {orgNo:obj.orgNo,orgType:obj.orgType,nodeType:type};
				  	Ext.getBody().mask("正在获取数据...");
				    userStore.load({
									callback : function(recs, options, success) {
//										if (success)
//											addGroupUserGrid.getSelectionModel()
//													.selectRecords(recs, true);
										Ext.getBody().unmask();			
									},
									add : true
								});
				  }
				  else if(type == 'line'){
				    userStore.baseParams = {lineId:obj.lineId,nodeType:type};
				    Ext.getBody().mask("正在获取数据...");
				    userStore.load({
									callback : function(recs, options, success) {
//										if (success)
//											addGroupUserGrid.getSelectionModel()
//													.selectRecords(recs, true);
										Ext.getBody().unmask();			
									},
									add : true
								});
				  }
				  else if(type == 'cgp' || type=='ugp'){
				  	userStore.baseParams = {groupNo:obj.groupNo,nodeType:type};
				  	Ext.getBody().mask("正在获取数据...");
				    userStore.load({
									callback : function(recs, options, success) {
//										if (success)
//											addGroupUserGrid.getSelectionModel()
//													.selectRecords(recs, true);
										Ext.getBody().unmask();
									},
									add : true
								});
				  }
				  else if(type == 'sub'){
				    userStore.baseParams = {subsId:obj.subsId,nodeType:type};
				    Ext.getBody().mask("正在获取数据...");
				    userStore.load({
									callback : function(recs, options, success) {
//										if (success)
//											addGroupUserGrid.getSelectionModel()
//													.selectRecords(recs, true);
										Ext.getBody().unmask();			
									},
									add : true
								});
				  }	  
		 	}
				  return true;
	   	},
	   	processUserGridSelect:function(cm,row,record){
   			 if("addGroupUserGrid"==secondPanel.getLayout().activeItem.getId()){
		          userStore.baseParams = {tmnlAssetNo:record.get('tmnlAssetNo'),nodeType:'usr'};
				  Ext.getBody().mask("正在获取数据...");
		          userStore.load({
									callback : function(recs, options, success) {
//										if (success)
//											addGroupUserGrid.getSelectionModel()
//													.selectRecords(recs, true);
										Ext.getBody().unmask();			
									},
									add : true
								});
   			 }
			  return true;
	    }
	});

	userStore.on('load', function(thisstore, recs, obj) {
		addGroupUserStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(thisstore));
		addGroupUserStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
		sm_addGroupUserCM.selectAll();		
		addGroupUserGrid.doLayout();
	});
	
	addGroupUserStore.on('load',function(){
		if(gsselectAllcb.checked){
			gsselectAllcb.setValue(false);
		}
	});
	
// ------------------------------------------------------------------------------------------新增群组-用户列表
// end
					
//-------------------------------------------------------------------------------------------新增群组-主界面
//begin	

		var rowNum_groupList= new Ext.grid.RowNumberer({
			renderer : function(v, p, record, rowIndex) {
				var startRow = 0;
				if(ds_groupList && ds_groupList.lastOptions && ds_groupList.lastOptions.params){
					startRow = ds_groupList.lastOptions.params.start;
				}
				return startRow + rowIndex + 1;
			}
		});
		var sm_groupList = new Ext.grid.CheckboxSelectionModel();     
	    var cm_groupList = new Ext.grid.ColumnModel([
	    rowNum_groupList,
	    sm_groupList,
		{header:'供电单位',sortable:true,align:'center',resizable:true,dataIndex:'orgName',width:130,
			renderer : function (val) {
					if(null!=val){
						var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">' + val + '</div>';
						return html;
					}
					else
						return '';
					}},
		{header:'群组名称',sortable:true,align:'center',resizable:true,dataIndex:'groupName',width:130,
			renderer : function (val) {
					if(null!=val){
						var html = '<div align = "left" ext:qtitle="群组名称" ext:qtip="' + val + '">' + val + '</div>';
						return html;
					}
					else
						return '';
					}},
		{header:'控制类别',sortable:true,align:'center',resizable:true,dataIndex:'ctrlSchemeType',hidden:true,width:160,
			renderer : function (val) {
					if(null!=val)
						return "<div align = 'left'>"+ val + "</div>";
					else
					  return '';
					}},
		{header:'创建者',sortable:true,align:'center',resizable:true,dataIndex:'staffNo',width:80,
			renderer : function (val) {
					if(null!=val)
						return "<div align = 'left'>"+ val + "</div>";
					else
					    return '';
				    }},
    	{header:'控制开始日期',sortable:true,align:'center',resizable:true,dataIndex:'startDate',width:70},
		{header:'控制结束日期',sortable:true,align:'center',resizable:true,dataIndex:'finishDate',width:70},
		{header:'组员管理',sortable:true,align:'center',resizable:true,dataIndex:'change',width:60,
			renderer : function() {
				return "<a href='javascript:' onclick='window.groupConManageShow();'>组员管理</a>";
			} },
		{header:'更名',sortable:true,align:'center',resizable:true,dataIndex:'delete',width:60,
			renderer : function() {
				return "<a href='javascript:' onclick='window.groupReNameShow();'>更名</a>";
			} }
		]);		
	    var ds_groupList=new Ext.data.Store({
	    	url:'baseapp/groupSet!queryGroupBy.action',
		    reader:new Ext.data.JsonReader({
		    	root : 'tTmnlGroupDtoList',
		    	idProperty:'groupNo',
	    		totalProperty : 'groupTotalCount'
		     }, [
		    {name: 'groupNo'},
		    {name: 'orgName'},
		    {name: 'groupName'},
		    //{name: 'isShare'},
		    {name: 'ctrlSchemeType'},
		    {name: 'staffNo'},
		    {name: 'startDate'},
		    {name: 'finishDate'},
		    {name: 'change'},
		    {name: 'delete'}
		   ])	
	 });

	 var panel_groupList= new Ext.grid.GridPanel({
	 	   region:'center',
		   store: ds_groupList,
		   cm: cm_groupList,
		   sm: sm_groupList,
		   loadMask:false,
		   stripeRows: true,
		   tbar : [ {
				xtype : 'label',
				html : "<font font-weight:bold;>群组列表</font>"
			}, {
				xtype : 'tbfill'
			},{
				text : '删除选中群组',
				iconCls : 'cancel',
				handler : function() {
					var recs = sm_groupList.getSelections();
					if (null == recs || recs.length == 0) {
						Ext.MessageBox.alert("提示", "请选择群组！");
						return;
					}
					Ext.MessageBox.confirm('提示', '确定删除吗？', deleteGroup);
				}
			}],
			bbar : new Ext.ux.MyToolbar({
					store : ds_groupList,
					enableExpAll : true, // excel导出全部数据
				    expAllText : "全部",
					enableExpPage: true, // excel仅导出当前页
					expPageText : "当前页"
				})
	    });
        window.groupConManageShow = function(){
        	  //全局终端列表
        	  var consResult;
              var groupRecs = sm_groupList.getSelections();
             ///----------------------------------------------------------------------群组管理-群组用户管理
             // begin	
             var groupConsNo=new  Ext.form.TextField({
                fieldLabel:'用户编号',
                emptyText : '请输入用户编号',
                allowBlank: false,
			    blankText :'请输入用户编号',
                width:150
             });
             
             var panel_conNo=new Ext.Panel({
             	region:'north',
             	height:50,
             	layout:'column',
             	border : false,
             	bodyStyle : 'padding:15px 0px 0px 5px',
             	items:[{
             	        border : false,
						columnWidth : .7,
						layout : 'form',
						labelAlign : 'right',
						labelSeparator : ' ',
						items:[groupConsNo]
						},{
							border : false,
						    columnWidth : .15,
							items:[{
								xtype:'button',
								text:'查询',
								width:80,
                                handler:function(){
									queryGroupDetailByCons();
								}
							}]
							
						},{
							border : false,
						    columnWidth : .15,
							items:[{
								xtype:'button',
								text:'删除',
								width:80,
                                handler:function(){
                                    var recs = sm_consList.getSelections();
                                    if(null==recs||recs.length==0){
                                        Ext.MessageBox.alert("提示","请选择要删除的终端！");
                                        return;
                                    }
                                    Ext.MessageBox.confirm('提示', '确定删除吗？', deleteGroupDetail);
                                } 
							}]
						}]
               });
               
               function queryGroupDetailByCons(){
                    if(null==groupConsNo||""==groupConsNo.getValue().trim())
                    {
                        Ext.MessageBox.alert("提示","请输入用户编号！");
                        return;
                    }
                    var groupNo=groupRecs[0].get('groupNo');
                    ds_consList.baseParams={
    	            		groupType:groupType,
    	                    groupNo:groupNo,
    	                    consNo:groupConsNo.getValue().trim()
    	            };
    	            Ext.getBody().mask("正在获取数据...");
    	            ds_consList.load({
    					params : {
    						start : 0,
    						limit : DEFAULT_PAGE_SIZE
    					},
    					callback:function(){
    	            		Ext.getBody().unmask();
    	            	} 
    	            });
               };
               
               function deleteGroupDetail(btn){
	                if(btn=='no') 
	                   return true;
                    var recs2 = sm_consList.getSelections(); 
                    var groupTmnlArray= new Array();
                    for(var i = 0; i < recs2.length; i++){
    	   	     		var tmnl = recs2[i].get('consNo')+'`'+recs2[i].get('tmnlAssetNo');
    	   	     		groupTmnlArray[i]=tmnl;
             		}
                    Ext.getBody().mask("正在处理...");
                    Ext.Ajax.request({
                        url:'baseapp/groupSet!deletegroupDetailBy.action',
                        params:{
                             groupType:groupType,
                             groupNo:groupRecs[0].get('groupNo'),
                             groupTmnlList:groupTmnlArray
                        },
                        callback : function(options,success,response){
                        	Ext.getBody().unmask();
                        	if(success){
                        		var result = Ext.decode(response.responseText);
                        		if(result.success){
	                				queryGroupDetail(function(){
	                					var commTree = Ext.getCmp('backTree');
                						commTree.root.reload();
                						var controlTree = Ext.getCmp('controlTree');
                						controlTree.root.reload();
	                					Ext.MessageBox.alert("提示","删除成功！");
	                				});
                        		}
                            		
                            	else{ 
									var msg;
									if (!result.errors.msg)
										msg = '未知错误！';
									else
										msg = result.errors.msg;
									Ext.MessageBox.alert('后台错误', msg);
								}
                        	}
	                    }
                    });
               };
	               
               var rowNum_consList= new Ext.grid.RowNumberer({
					renderer : function(v, p, record, rowIndex) {
						var startRow = 0;
						if(ds_consList && ds_consList.lastOptions && ds_consList.lastOptions.params){
							startRow = ds_consList.lastOptions.params.start;
						}
						return startRow + rowIndex + 1;
					}
				});
                var sm_consList = new Ext.grid.CheckboxSelectionModel();     
			    var cm_consList = new Ext.grid.ColumnModel([
			    rowNum_consList,
			    sm_consList,
				{header:'供电单位',sortable:true,align:'center',dataIndex:'orgName',width:130,
					renderer : function (val) {
						if(null!=val){
							var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">' + val + '</div>';
							return html;
						}
						else
							return '';
						}},
				{header:'用户编号',sortable:true,align:'center',dataIndex:'consNo',width:80},
				{header:'用户名称',sortable:true,align:'center',dataIndex:'consName',width:130,
					renderer : function (val) {
						if(null!=val){
							var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">' + val + '</div>';
							return html;
						}
						else
							return '';
						}},
				{header:'地址',sortable:true,align:'center',dataIndex:'consAddr',width:130,
					renderer : function (val) {
						if(null!=val){
							var html = '<div align = "left" ext:qtitle="地址" ext:qtip="' + val + '">' + val + '</div>';
							return html;
						}
						else
							return '';
						}},
				{header:'线路',sortable:true,align:'center',dataIndex:'line',width:60,
					renderer : function (val) {
						if(null!=val){
							return "<div align = 'left'>"+ val + "</div>";
						}
						else
							return '';
						}},
				{header:'行业',sortable:true,align:'center',dataIndex:'trade',width:100,
					renderer : function (val) {
						if(null!=val){
							var html = '<div align = "left" ext:qtitle="行业" ext:qtip="' + val + '">' + val + '</div>';
							return html;
						}
						else
							return '';
						}},
		    	{header:'控制开始日期',sortable:true,align:'center',dataIndex:'startDate',width:70},
				{header:'控制结束日期',sortable:true,align:'center',dataIndex:'finishDate',width:70}
			  ]);		
			  var ds_consList=new Ext.data.Store({
				    url:'baseapp/groupSet!queryGroupUser.action',
	                reader : new Ext.data.JsonReader({
						    root:'tTmnlGroupDetailDtoList',
						    totalProperty : 'groupDetailTotalCount',
					        idIndex: 6
						},[
						    {name: 'orgName'},
						    {name: 'consNo'},
						    {name: 'consName'},
						    {name: 'consAddr'},
						    {name: 'line'},
						    {name: 'trade'},
						    {name: 'tmnlAssetNo'},
						    {name: 'startDate'},
						    {name: 'finishDate'}
						   ])	
			 });

			 var panel_conList= new Ext.grid.GridPanel({
			 	   region:'center',
				   store: ds_consList,
				   cm: cm_consList,
				   sm: sm_consList,
				   stripeRows: true,
				   loadMask:false,
			       tbar :[{
						xtype: 'label',
						html : "<font font-weight:bold;>用户列表</font>"
		            }, {
					xtype : 'tbfill'
					}],
				   bbar : new Ext.ux.MyToolbar({
						store : ds_consList,
						enableExpAll : true, // excel导出全部数据
					    expAllText : "全部",
						enableExpPage: true, // excel仅导出当前页
						expPageText : "当前页"
					}),
				   viewConfig:{forceFit : true}
		     });	
			 
	        function queryGroupDetail(fn){
	            var groupNo=groupRecs[0].get('groupNo');
	            ds_consList.baseParams={
	            		groupType:groupType,
	                    groupNo:groupNo,
	                    consNo:''
	            };
	            Ext.getBody().mask("正在获取数据...");
	            ds_consList.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					},
					callback:function(recs, options, success){
	            		Ext.getBody().unmask();
			   			fn();
	            	} 
	            });
	       	  }; 	  
              var w_groupConManage=new  Ext.Window({
               	    layout:'border',
                    modal:true,
                 	title:'群组用户管理',
                 	maximizable:true, 
					width:800,
					height:550,
					minWidth:600,
					minHeight:412,
                 	items:[panel_conNo,panel_conList]
               });
			//-----------------------------------------------------------------群组管理-群组用户管理
			// end
            w_groupConManage.show();
            queryGroupDetail(function(){});           
        };
       	
       window.groupReNameShow = function(){
       		var groupRecs = sm_groupList.getSelections();
			///---------------------------------------------------------群组管理-群组更名
			// begin  
            var originalGroupName1=new Ext.form.TextField({
                fieldLabel:'原群组名',
                anchor:'99%',
                readOnly:true
            });
            var originalGroupName2=new Ext.form.TextField({
                anchor:'90%',
                readOnly:true
            }); 
            var newName1=new Ext.form.TextField({
                fieldLabel:'新群组名<font color="red">*</font>',
                anchor:'99%',
                readOnly:true
            });
            var newName2=new Ext.form.TextField({
            	emptyText : '请输入新群组名称',
			    allowBlank:false,
			    blankText : '请输入新群组名称',
                anchor:'90%'
            });
            var panel_groupReName1 =new Ext.Panel({
            	anchor:'100% 45%',
            	border : false,
            	layout:'column',
            	items:[{
            		    border : false,
						columnWidth : .5,
						layout : 'form',
						labelAlign : 'right',
						labelSeparator : ' ',
						labelWidth:60,
						bodyStyle : 'padding:25px 0px 0px 10px',
						items:[originalGroupName1]
	            	},{
	            		border : false,
						columnWidth : .5,
						layout : 'form',
						hideLabels:true,
						bodyStyle : 'padding:25px 0px 0px 0px',
						items:[originalGroupName2]
	            	}]
            });
            
            var panel_groupReName2 =new Ext.Panel({
            	anchor:'100% 50%',
            	border : false,
            	layout:'column',
            	buttonAlign:'center',
            	items:[{
            		    border : false,
						columnWidth : .5,
						layout : 'form',
						labelAlign : 'right',
						labelSeparator : ' ',
						labelWidth:60,
						bodyStyle : 'padding:0px 0px 0px 10px',
						items:[newName1]
	            	},{
	            		border : false,
						columnWidth : .5,
						layout : 'form',
						hideLabels:true,
						items:[newName2]
	            	}],
	            	buttons:[{
						text:'确认',
						handler:function(){
  		     	            if(null==newName2||""==newName2.getValue().trim()){
  		     	            	Ext.MessageBox.alert("提示","请输入新群组名称！");
		  		     	    	return;
  		     	            }
  		     	            var groupNo=groupRecs[0].get('groupNo');
                            var groupName;
                            if(groupType=="0")
                               groupName=newName2.getValue().trim();
                            else if(groupType=="1")
                               groupName=newName1.getValue()+'-'+newName2.getValue().trim();
                            else 
                               return;
                            w_groupReName.hide();
                            Ext.getBody().mask("正在处理...");
  		     	            Ext.Ajax.request({
		  		     	        url:'baseapp/groupSet!rejiggerGroupName.action',
		  		     	        params:{
                                    groupType:groupType,
		  		     	        	groupNo:groupNo,
		  		     	        	groupName:groupName
		  		     	        },
                           		callback:function(options,success,response){
                           			Ext.getBody().unmask();
		  		     	    		if(success){
		  		     	    			var result = Ext.decode(response.responseText);
		  		     	    			if(result.success){
				  		     	    		if(null!=result.FLAG){
		                                        if(result.FLAG==0){
		                                            Ext.MessageBox.alert("提示","该群组名已被使用！",function(){
		                                            		 w_groupReName.show();
		                                            });
		                                            return;
		                                        }
		                                     	else if(result.FLAG==1){
		                                            getLastSuccessQueryData(function(){
		                                            Ext.MessageBox.alert("提示","更名成功！");                                            
		                                            });
		                                        }
				  		     	    		}
		  		     	    			}
		  		     	    			else{ 
											var msg;
											if (!result.errors.msg)
												msg = '未知错误！';
											else
												msg = result.errors.msg;
											Ext.MessageBox.alert('后台错误', msg);
		  		     	    			}
		  		     	    		}
		  		     	    		w_groupReName.close();
	                            }
                            });
						}
            	},{
            		text:'返回',
                            handler:function(){
                                w_groupReName.close();
                            }
            	}]
            });
             
           var  panel_groupReName =new Ext.Panel({
            	layout:'anchor',
            	border : false,
            	items:[panel_groupReName1,panel_groupReName2]
           });
            var w_groupReName=new Ext.Window({	
            	resizable : false,
             	title:'群组更名',
             	modal:true,
				width:350,
				height:200,
				layout:'fit',
				items:[panel_groupReName]
            });
			/// -------------------------------------------------------群组管理-群组更名
			// end	
            var groupName=groupRecs[0].get('groupName');
            if(groupType=="0"){
                 originalGroupName1.setValue("");
                 originalGroupName2.setValue(groupName); 
                 newName1.setValue("");
            }
            else if(groupType=="1"){
                var groupName1 = groupName.substring(0,groupName.indexOf('-'));
                var groupName2 = groupName.substring(groupName.indexOf('-')+1,groupName.length);
                originalGroupName1.setValue(groupName1);
                originalGroupName2.setValue(groupName2);
                newName1.setValue(groupName1);
            }
            w_groupReName.show();
       };
       
      /* function  share(){
       	   var groupRecs = sm_groupList.getSelections();
	 	   if(groupRecs==null||groupRecs.length==0){
	 	    	Ext.MessageBox.alert("提示","请选择群组！");
	 	    	return;
	       }
       	  /// ----------------------------------------------------------------------群组管理-设置共享
          // begin
          var isShareRadio = new Ext.form.RadioGroup({
                anchor:'80%',
                items : [new Ext.form.Radio({
                            boxLabel : '共享',
                            name : 'isShareRadio',
                            checked : true,
                            inputValue : '1'
                        }),
                         new Ext.form.Radio({
                            boxLabel : '不共享',
                            name : 'isShareRadio',
                            inputValue : '0'
                        })]
          }); 
          var panle_share1=new Ext.Panel({
               region:'north',
               height:100,
               border : false,
               layout : 'form',
               bodyStyle : 'padding:35px 0px 0px 0px',
               items:[isShareRadio]
          });
            
          var panle_share2=new Ext.Panel({
          	   region:'center',
               border : false,
               layout : 'column',
               items:[
            		{
            		border : false,
					columnWidth : .75,
					bodyStyle : 'padding:0px 0px 0px 200px',
					items:[{
							xtype:'button',
							text:'确认',
							handler:function(){
                                //var groupType=groupRecs[0].get('groupType');
	  		     	            var groupNos=groupRecs[0].get('groupNo');
	  		     	            for(var i = 1;i < groupRecs.length; i++){
			  		     	    	 groupNos = groupNos +','+ groupRecs[i].get('groupNo');
		                        }	  		     	  
	  		     	            Ext.Ajax.request({
			  		     	        url:'baseapp/groupSet!setShare.action',
			  		     	        params:{
                                        groupType:groupType,
			  		     	        	groupNos:groupNos
			  		     	        	//isShare:isShareRadio.getValue().getRawValue()
			  		     	        },
                                    success:function(){
                                        w_share.close();
                                    	Ext.MessageBox.alert("提示","成功！");
                                    	queryGroup();
                                    }
	  		     	            });
							}
						}]
            	},{
            		border : false,
					columnWidth : .25,
					items:[{
							xtype:'button',
							text:'返回',
                            handler:function(){
                              w_share.close();
                            }
						}]
            	}]
           });  
           var w_share=new Ext.Window({
             	resizable : false,
             	title:'设置共享',
                modal:true,
				width:400,
				height:250,
           	    layout:'border',
           	    items:[panle_share1,panle_share2]	
           });
			/// --------------------------------------------------------群组管理-设置共享
			// end
           w_share.show();
       };*/
       	
	   function deleteGroup(btn){
			if(btn=='no') 
			  return true; 
			var recs = sm_groupList.getSelections();
			var groupNoArray = new Array();
			//var creatorArray = new Array();
		    for(var i = 0; i < recs.length; i++){	  		    
     	    	groupNoArray[i]=recs[i].get('groupNo');
     	    	//creatorArray[i]=recs[i].get('staffNo');
		    }
		    Ext.getBody().mask("正在处理...");
            Ext.Ajax.request({
     	        url:'baseapp/groupSet!deleteGroup.action',
     	        params:{
                    groupType:groupType,
     	        	groupNoList:groupNoArray
     	        	//creatorList:creatorArray
     	        },
                callback:function(options,success,response){
                	Ext.getBody().unmask();
                	if(success){
	                	var result = Ext.decode(response.responseText);
	                	if(result.success){
	     	    		    getLastSuccessQueryData(function(){
//	     	    		    	alert(Ext.getCmp('commGroupTypeRadio').checked);
	                			var commTree = Ext.getCmp('backTree');
	                			commTree.root.reload();
	                			var controlTree = Ext.getCmp('controlTree');
	                			controlTree.root.reload();
	     	    		    	Ext.MessageBox.alert("提示","删除成功！");
	     	    		    });
	                	}
						else{ 
							var msg;
							if (!result.errors.msg)
								msg = '未知错误！';
							else
								msg = result.errors.msg;
							Ext.MessageBox.alert('后台错误', msg);
	 	    			}
               		 }
                }
            }); 
		};
// ------------------------------------------------------------------------------------------群组管理-查询条件
// beginvar         
            var groupTypeRadio = new Ext.form.RadioGroup({
                fieldLabel:'类别',
			    width:110,
			    columns : [.5,.5],
			    items : [new Ext.form.Radio({
			                boxLabel : '普通',
			                name : 'groupTypeRadio',
			                id : 'commGroupTypeRadio',
			                checked : true,
			                inputValue : '0'
			            }),
			             new Ext.form.Radio({
			                boxLabel : '控制',
			                name : 'groupTypeRadio',
			                id : 'ctrlGroupTypeRadio',
			                inputValue : '1'
			            })]
			    }); 
           
            var text_groupName = new Ext.form.TextField({
            	fieldLabel:'名称',
            	emptyText : '请输入群组名称',
            	width:120            	
            });
            
            var  startDate3=new Ext.form.DateField({
                 fieldLabel : '有效日期',
                 width:100,
                 format:'Y-m-d',
                 value:new Date()
            });
            var  finishDate3=  new Ext.form.DateField({
                 fieldLabel : '至',
                 width:100,
                 format:'Y-m-d',
                 value:new Date().add(Date.DAY,30)
            });
         
           
           var check_selectDate=new Ext.form.Checkbox({
         		checked :true	
           });
           
           check_selectDate.on('check',function(check_selectDate,checked){
	           if(checked==true){
		          startDate3.enable();
		          finishDate3.enable();
		          startDate3.setValue(new Date());
		          finishDate3.setValue(new Date().add(Date.DAY,30));
		       }
		       else if(checked==false){
		       	  startDate3.setValue("");
		       	  finishDate3.setValue("");
		       	  startDate3.disable();
		       	  finishDate3.disable();
	           }
           });
           
           var queryGroupButton=new Ext.Button({
          		  text : '查询',
                  width:70,
                  handler:function (){
                  	queryGroup();
                  }
           });
           
           //查询群组函数;
           function queryGroup(){
                var startDate=startDate3.getValue();
                var finishDate=finishDate3.getValue();
                if(check_selectDate.checked==true){
	                 if(""==startDate){
	                    Ext.MessageBox.alert("提示","请选择开始日期！");
	                    return; 
	                 }
	                 if(""==finishDate){
	                    Ext.MessageBox.alert("提示","请选择结束日期！");
	                    return; 
	                 }
	                 if(startDate>finishDate){
	                    Ext.MessageBox.alert("提示","开始日期不能大于结束日期！");
	                    return;
	                 }
               }
               if(groupTypeRadio.getValue().getRawValue()=="0")
                    panel_groupList.getColumnModel().setHidden(4,true);
               if(groupTypeRadio.getValue().getRawValue()=="1")
                    panel_groupList.getColumnModel().setHidden(4,false);   
               groupType=groupTypeRadio.getValue().getRawValue();
               ds_groupList.baseParams={
		                   groupType:groupType,
		                   groupName : text_groupName.getValue().trim(),
		                   startDate:startDate,
		                   finishDate:finishDate
               };
               Ext.getBody().mask("正在获取数据...");
        	   ds_groupList.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					},
	        	    callback:function(options,success,response){
						Ext.getBody().unmask();
						if(success){
							lastGroupType=groupType;
		                    lastGroupName=text_groupName.getValue().trim();
		                    lastStartDate=startDate;
		                    lastFinishDate=finishDate;
						}
					}
        	   }); 
          };
          
          //获取最近一次成功查询出的数据
          function getLastSuccessQueryData(fn){ 	
               ds_groupList.baseParams={
		                   groupType:lastGroupType,
		                   groupName:lastGroupName,
		                   startDate:lastStartDate,
		                   finishDate:lastFinishDate
               };
               Ext.getBody().mask("正在获取数据...");
        	   ds_groupList.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					},
	        	    callback:function(options,success,response){
						Ext.getBody().unmask();
						fn();
                    }
            	});
          };
          
          var groupManageQuery = new Ext.Panel({
                region:'north',
                height:30,
                border : false,
				layout:'table',
				layoutConfig: {columns:6},
				defaults: {height: 30},
				items:[{
		                 border : false,
	                     width : 170,
		                 layout:'form',
		                 labelWidth : 40,
		                 labelAlign : 'right',
		                 items:[text_groupName]
		            },{
	                    border : false,
	                    width : 175,
	                    layout : 'form',
	                    labelWidth : 55,
	                    labelAlign : 'right',
	                    items:[groupTypeRadio]
					},{
						layout : 'form',
	                    border : false,
	                    width:30,
	           	    	hideLabels:true,
	           	    	bodyStyle : 'padding:0px 0px 0px 15px',
	                    items:[check_selectDate]    
	                },{
						layout : 'form',
						border : false,
						labelWidth : 50,
						labelAlign : 'right',
						width : 160,
						items:[startDate3]	
					},{
						layout : 'form',
	                    border : false,
	                    labelAlign : 'right',
	                    labelWidth:15,
	                    width : 125,
	                    items:[finishDate3]   
	                },{ 
	                    layout : 'form',
	                    border : false,
	                    width : 100,
	                    bodyStyle : 'padding:0px 0px 0px 25px',
	                    items : [queryGroupButton]   
	        	}]
            });
        
		   var groupManageQueryPanel=new Ext.Panel({
                border : false,
                layout:'border',
                items:[groupManageQuery,panel_groupList]
            });        
// ------------------------------------------------------------------------------------------群组管理-查询条件
// end
			var secondPanel = new Ext.Panel({
						border : false,
						region:'center',
						name:'bodyPanel',
						layout : 'card',
						activeItem : 0,
						bodyStyle : 'padding:10px 0px 0px 0px',
						items : [addGroupUserGrid, groupManageQueryPanel]
					});

			var mainPanel = new Ext.Panel({
						layout:'border',
						border : false,
						items : [opTypePanel, secondPanel]
					});
			renderModel(mainPanel, '群组设置');
		});
		
