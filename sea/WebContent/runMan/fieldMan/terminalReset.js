
function origFrameQryShow(consNo,consName,tmnlAssetAddr){
	staticConsNo = consNo;
	staticConsName = consName;
	staticTmnlAssetAddr = tmnlAssetAddr;
	openTab("原始报文查询","./baseApp/dataGatherMan/origFrameQry.jsp");
}
Ext.onReady(function(){
    
    var resetTypeFlag = 0;   //复位类型激活--为0时仅开放硬件初始化功能；为1时开放所有复位功能
	
	
	var overFlat = false;

	var h_taskTime = function(second, fn) {
		flag = 0;
		overFlat = false;
		var task_s = (second == "") ? 30 : second;
		Ext.MessageBox.show({
					title : '请等待',
					msg : '任务执行中...',
					progressText : '初始化...',
					width : 300,
					progress : true,
					closable : false,
					buttons : {
						"cancel" : "取消"
					},
					fn : function(e) {
						flag = 0;
						if (fn && typeof fn == "function") {
							fn();
						}
						clearInterval(aa);
					}
				});

		var f = function() {
			return function() {
				flag = flag + 1;

				if (flag == task_s + 1 || overFlat) {
					Ext.MessageBox.hide();
					flag = 0;
					if (fn && typeof fn == "function") {
						fn();
					}
					clearInterval(aa);
				} else {
					var i = flag;
					Ext.MessageBox.updateProgress(i / (task_s), i + ' 秒');
				}
			};
		};
		var aa = setInterval(f(), 1000);
	};
	
	//--------------------用户下发列表grid----------start
	var tmnlResetSm= new Ext.grid.CheckboxSelectionModel(); 
	var tmnlResetRowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(tmnlResetStore && tmnlResetStore.lastOptions && tmnlResetStore.lastOptions.params){
				startRow = tmnlResetStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	var tmnlResetCm = new Ext.grid.ColumnModel([  
	       tmnlResetRowNum,
	       tmnlResetSm,
		   {header:'供电单位',dataIndex:'orgName',sortable:true,resizable:true,align:'center',width:150,
			    renderer : function(value) {
		    	    if(null!=value){
						var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + value + '">'
								+ value + '</div>';
						return html;
		    	    }
					else
						return '';
		    	    }},
		   {header:'用户编号',dataIndex:'consNo',sortable:true,resizable:true,align:'center'},
		   {header:'用户名称',dataIndex:'consName',sortable:true,resizable:true,align:'center',width:150,
			   renderer : function(value) {
				    if(null!=value){
						var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + value + '">'
								+ value + '</div>';
						return html;
				    }
					else
						return '';
					}},
		   {header:'终端地址',dataIndex:'terminalAddr',sortable:true,resizable:true,align:'center'},
    	   {header:'执行状态',dataIndex:'execStatus',sortable:true,resizable:true,align:'center',
    	    	 renderer:function(value){
 			   		if('1'==value){
 			   			return "<font color='green';font-weight:bold>成功</font>";
 			   		}else if('0'==value){
 			   			return "<font color='red';font-weight:bold>失败</font>";
 			   		}else{
 			   		    return "";
 			   		}}},
		   {header:'报文',dataIndex:'message',sortable:true,resizable:true,align:'center',
			   renderer: function(s, m, rec){
			   return "<a href='javascript:'onclick='origFrameQryShow(\""+rec.get('consNo')+"\",\""+ rec.get('consName')+"\",\""+ rec.get('terminalAddr')+"\");" + "'>查看</a>"; 
		   }}
		 ]);
	
	 var userStore= new Ext.data.Store({
	        url:'baseapp/generalGridByTree!generalGridByTree.action',
		    reader : new Ext.data.JsonReader({
					root : 'wTmnlDtoList',
					idProperty:'tmnlAssetNo'
			}, [
		    {name: 'orgNo'},
		    {name: 'orgName'},
		    {name: 'consNo'},
		    {name: 'consName'},
		    {name: 'terminalAddr'},
		    {name: 'tmnlAssetNo'},
		    {name: 'protocolCode'},
		    {name: 'execStatus'},
		    {name: 'message'}
		   ])
	});
	function storeToArray(valStore) {
		var data = new Array();
		for (var i = 0; i < valStore.getCount(); i++) {
			data[i] = new Array();
 			data[i][0] = valStore.getAt(i).data.orgNo;
			data[i][1] = valStore.getAt(i).data.orgName;
			data[i][2] = valStore.getAt(i).data.consNo;
			data[i][3] = valStore.getAt(i).data.consName;
			data[i][4] = valStore.getAt(i).data.terminalAddr;
			data[i][5] = valStore.getAt(i).data.tmnlAssetNo;
			data[i][6] = valStore.getAt(i).data.protocolCode;
			data[i][7] = valStore.getAt(i).data.execStatus;
			data[i][8] = valStore.getAt(i).data.message;
		}
		return data;
	}
	var tmnlResetStore = new Ext.data.Store({
		remoteSort : true,
		proxy : new Ext.data.MemoryProxy(),
		reader : new Ext.data.ArrayReader({
			idIndex : 5,
			fields : [ 
				{name: 'orgNo'},
			    {name: 'orgName'},
			    {name: 'consNo'},
			    {name: 'consName'},
			    {name: 'terminalAddr'},
			    {name: 'tmnlAssetNo'},
			    {name: 'protocolCode'},
			    {name: 'execStatus'},
			    {name: 'message'}]
			})
	});
	
	// grid解锁
	function unlockGrid() {
		tmnlResetSm.unlock();
		tmnlResetGrid.onEnable();
		tmnlResetGrid.getBottomToolbar().enable();
	}

	// grid上锁
	function lockGrid() {
		tmnlResetSm.lock();
		tmnlResetGrid.onDisable();
		tmnlResetGrid.getBottomToolbar().disable();
	}
	
	var gsselectAllcb=new Ext.form.Checkbox({
		boxLabel : '全选',
		name : 'gsselectAllcb',
		checked : false,
		listeners : {
			'check' : function(r, c) {
				if (c) {
					tmnlResetSm.selectAll();
					lockGrid();
				}else {
					unlockGrid();
					tmnlResetSm.clearSelections();
				}
			}
		}	
	});
	var tmnlResetGrid=new Ext.grid.GridPanel({
		region:'center',
		store: tmnlResetStore,
		cm: tmnlResetCm,
		sm: tmnlResetSm,
		stripeRows: true,
		tbar:[ {
				xtype : 'label',
				html : "<font font-weight:bold;>备选用户</font>"
			},{xtype: 'tbfill'},
			gsselectAllcb,
			'-',
			{
	             xtype:"button",
	             text:"删除选中用户",
	             iconCls: 'cancel',
	             handler : function() {
						if (gsselectAllcb.checked) {
							userStore.removeAll(true);
						} else {
							var recs = tmnlResetSm.getSelections();
							for (var i = 0; i < recs.length; i = i + 1) {
								userStore.remove(userStore
										.getById(recs[i].data.tmnlAssetNo));
							}
						}
						tmnlResetStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
						tmnlResetStore.load({
									params : {
										start : 0,
										limit : DEFAULT_PAGE_SIZE
									}
						});
				}
             },
             '-',
             {
                 xtype:"button",
                 text:"加入群组",
                 iconCls: 'plus',
                 handler:function(){
						var groupTmnlArray = new Array();
						if (gsselectAllcb.checked) {
							for (var i = 0; i < userStore.getCount(); i++) {
								var tmnl = userStore.getAt(i).get('consNo')
										+ '`'
										+ userStore.getAt(i).get('tmnlAssetNo');
								groupTmnlArray[i] = tmnl;
							}
						} else {
							var recs = tmnlResetSm.getSelections();
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
						if (gsselectAllcb.checked) {
							gsselectAllcb.setValue(false);
							tmnlResetSm.selectAll();
						}
	                 }
            },
            '-',
            {
                 xtype:"button",
                 text:"删除成功用户",
                 iconCls: 'minus',
                 handler:function(){
                 		for (var i = userStore.getCount()-1; i >=0; i--) {
                 			if("1"==userStore.getAt(i).get('execStatus'))
								userStore.removeAt(i);
						}
						tmnlResetStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
						tmnlResetStore.load({
									params : {
										start : 0,
										limit : DEFAULT_PAGE_SIZE
									}
						});
                 }
       	 }],
   	   bbar : new Ext.ux.MyToolbar( {
				store : tmnlResetStore,
				allStore:userStore,
				enableExpAll : true, // excel导出全部数据
			    expAllText : "全部",
				enableExpPage: true, // excel仅导出当前页
				expPageText : "当前页"
		})  
	});
	//监听左边树点击事件
	var treeListener = new LeftTreeListener({ 
		  modelName : '终端复位',
		  processClick : function(p, node, e){
			  var obj = node.attributes.attributes;
			  var type = node.attributes.type;
			  if(type == 'usr'){
			      userStore.baseParams = {tmnlAssetNo:obj.tmnlAssetNo,nodeType:type};
			      Ext.getBody().mask("正在获取数据...");
			      userStore.load({
								callback : function(recs, options, success) {
//									if (success)
//										tmnlResetGrid.getSelectionModel()
//												.selectRecords(recs, true);
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
//									if (success)
//										tmnlResetGrid.getSelectionModel()
//												.selectRecords(recs, true);
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
//									if (success)
//										tmnlResetGrid.getSelectionModel()
//												.selectRecords(recs, true);
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
//									if (success)
//										tmnlResetGrid.getSelectionModel()
//												.selectRecords(recs, true);
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
//									if (success)
//										tmnlResetGrid.getSelectionModel()
//												.selectRecords(recs, true);
									Ext.getBody().unmask();				
								},
								add : true
							});
			  }
			  return true; 
		   },
		   processUserGridSelect:function(cm,row,record){
			  userStore.baseParams = {tmnlAssetNo:record.get('tmnlAssetNo'),nodeType:'usr'};
			  Ext.getBody().mask("正在获取数据...");
			  userStore.load({
								callback : function(recs, options, success) {
//									if (success)
//										tmnlResetGrid.getSelectionModel()
//												.selectRecords(recs, true);
									Ext.getBody().unmask();				
								},
								add : true
							});
			  return true;
		    }	
	 });
	 
	userStore.on('load', function(thisstore, recs, obj) {
		tmnlResetStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(thisstore));
		tmnlResetStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
		tmnlResetSm.selectAll();		
		tmnlResetGrid.doLayout();
	});
	
	tmnlResetStore.on('load',function(){
		if(gsselectAllcb.checked){
			gsselectAllcb.setValue(false);
		}
	});
	
	//--------------------用户下发列表grid----------end
	
	var tmnlResetRadio = new Ext.form.RadioGroup({
		fieldLabel:'选择复位类型',
		width:500,
        columns:[.20,.23,.23,.34] ,
        items : [
       		    new Ext.form.Radio({
       		    	id :'tmnlResetRadio1',
                    boxLabel : '硬件初始化',
                    name : 'tmnlResetRadio',
                    checked : true,
                    inputValue : '1'
                }),
                 new Ext.form.Radio({
                	id :'tmnlResetRadio2',
                    boxLabel : '数据区初始化',
                    name : 'tmnlResetRadio',
                	disabled:true,
                    inputValue : '2'
                }),
                 new Ext.form.Radio({
                	id :'tmnlResetRadio3',
                    boxLabel : '恢复出厂设置',
                    name : 'tmnlResetRadio',
                	disabled:true,
                    inputValue : '3'
                }),
                 new Ext.form.Radio({
                	id :'tmnlResetRadio4',
                    boxLabel : '参数及数据区初始化',
                    name : 'tmnlResetRadio',
                    disabled:true,
                    inputValue : '4'
                })]
    });
	
	if(0==resetTypeFlag){
		Ext.getCmp('tmnlResetRadio2').setDisabled(true);
		Ext.getCmp('tmnlResetRadio3').setDisabled(true);
		Ext.getCmp('tmnlResetRadio4').setDisabled(true);
	}
	else if(1==resetTypeFlag){
		Ext.getCmp('tmnlResetRadio2').setDisabled(false);
		Ext.getCmp('tmnlResetRadio3').setDisabled(false);
		Ext.getCmp('tmnlResetRadio4').setDisabled(false);
	}
	
	var tmnlResetExecPanel= new Ext.Panel({
		border : false,
		height:40,
		region:'north',
		layout:'table',
	    layoutConfig : {
			columns :2
	    },
	    defaults: {height: 35},
		items:[{
            border : false,
            layout:'form',
            width:655,
            labelAlign: 'left',
  	        labelWidth:100,
  	        labelSeparator : '',
  	        bodyStyle : 'padding:10px 0px 0px 20px',
	        items:[tmnlResetRadio]
		},{
    	    border : false,
	    	layout:'form',
	    	width:100,
	    	bodyStyle : 'padding:10px 0px 0px 0px',
    	    items:[{
			     xtype:'button',
	  		     text:'复位'	,
	  		     width:80,
	  		     handler:function(){
			 		var users = tmnlResetSm.getSelections();
					if(gsselectAllcb.checked ){
						if(0==userStore.getCount()){
						  Ext.MessageBox.alert("提示","请选择用户！");
				     	    return;
						}
			     	}else{
			     		if(null == users || users.length==0){
				     	    Ext.MessageBox.alert("提示","请选择用户！");
				     	    return;
				     	}
			     	}
			 		ctrlCheckStaff(tmnlReset, 'ctrl');
			 	}
    	    }]
	    }]
	});
	
	//终端复位函数
	function tmnlReset(){
		var users = tmnlResetSm.getSelections();
		var resetTmnlArray=new Array();
		if (gsselectAllcb.checked) {
			for (var i = 0; i < userStore.getCount(); i++) {
				var tmnl = userStore.getAt(i).get('tmnlAssetNo')
						+ '`'
						+ userStore.getAt(i).get('protocolCode');
				resetTmnlArray[i] = tmnl;
				userStore.getAt(i).set('execStatus',"0");
			}
		} 
		else {
			for (var i = 0; i < users.length; i++) {
				var rec = userStore.getById(users[i].get('tmnlAssetNo'));
				if(null!=rec){
					var tmnl = users[i].get('tmnlAssetNo') + '`'
							+ users[i].get('protocolCode');
					resetTmnlArray[i] = tmnl;
					rec.set('execStatus',"0");
				}
			}
		}
		userStore.commitChanges();
		var taskSecond= Ext.Ajax.timeout/1000;
        var ov = true;
        h_taskTime(taskSecond,function(){
        		ov = false;	
        }); 
        Ext.Ajax.request({
        	url:'./runman/terminalReset!terminalReset.action',
            params :{
     			resetTmnlList:resetTmnlArray,
     			resetType:tmnlResetRadio.getValue().getRawValue(),
     			taskSecond:taskSecond
     		},
     		success : function(response){
    		if (!ov) {
						return;
					}
			var result = Ext.decode(response.responseText);
			if(null!=result.msg&&""!=result.msg){
 				Ext.MessageBox.alert("提示",result.msg);
 				overFlat = true;
 				return;
			}
			if(null!=result.tmnlExecStatusList && 0< result.tmnlExecStatusList.length){
             	for(var j=0;j<result.tmnlExecStatusList.length;j++){
     				var rec = userStore.getById(result.tmnlExecStatusList[j].keyId);	
                    if(null!=rec&&"1"==result.tmnlExecStatusList[j].execFlag)
	 				     rec.set('execStatus',"1");
             	}
            	userStore.commitChanges();
             }
             tmnlResetStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
			 tmnlResetStore.load({
						params : {
							start : tmnlResetStore.lastOptions.params.start,
							limit : DEFAULT_PAGE_SIZE
						}
			 });
    		 overFlat = true;
    		 Ext.Ajax.timeout=30000;
    		}
 		});
	}

	var viewPanel = new Ext.Panel({
		layout: 'border',
		items: [tmnlResetExecPanel,tmnlResetGrid],
		border : false
	});
	renderModel(viewPanel, '终端复位');
});