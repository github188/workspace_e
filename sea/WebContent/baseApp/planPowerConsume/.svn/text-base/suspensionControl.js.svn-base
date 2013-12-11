/**
 * 营业报停控
 * 
 * @author jiangweichao
 */
 	
function origFrameQryShow(consNo,consName,tmnlAssetAddr){
	staticConsNo = consNo;
	staticConsName = consName;
	staticTmnlAssetAddr = tmnlAssetAddr;
	openTab("原始报文查询","./baseApp/dataGatherMan/origFrameQry.jsp");
}

//页面执行函数
Ext.onReady(function(){
    var schemeNameFlag;//方案名标志
    var shceme_Name;//方案名
    var overFlat = false;
	
	//进度条
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
	
    //-----------------------------定义用户列表-------------------------start
	//为了便于页面操作，这里抽取出来，不放入fuction中
	var rowNum_user= new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(userListStore && userListStore.lastOptions && userListStore.lastOptions.params){
				startRow = userListStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	var userSm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([
	        rowNum_user,                          
            userSm, 
		    {header : '供电单位', sortable: true, dataIndex : 'orgName', align : 'center',width:150,
	        	    renderer : function(val) {
	        	       if(null!=val){
							var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
									+ val + '</div>';
							return html;
	        	       }
	        	       else
	        	    	   return '';
	        	       }}, 
		    {header : '用户编号', sortable: true, dataIndex : 'consNo', align : 'center'}, 
		    {header : '用户名称', sortable: true, dataIndex : 'consName', align : 'center',width:150,
		    		renderer : function(val) {
			    		if(null!=val){
							var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
									+ val + '</div>';
							return html;
			    		}
			    		else
			    			return '';
			    		}}, 
		    {header : '终端地址', sortable: true, dataIndex : 'terminalAddr', align : 'center'},
		    {header : '总加组号', sortable: true, dataIndex : 'totalNo', align : 'center',width:80}, 
		    {header : '保电', sortable: true, dataIndex : 'protect', align : 'center',width:40,
		    	  renderer:function(value){
		    	     if("1"==value){
		    	    	 return "是";
		    	     }else if("0"==value){
		    	    	 return "否";
		    	     }else{
		    	    	 return "";}}},
		    {header : '控制状态', sortable: true, dataIndex : 'ctrlStatus', align : 'center',
		    	  renderer:function(value){
		    	    	 if("0"==value){
			    	    	 return "解除";
			    	     }else if("1"==value){
			    	    	 return "投入";
			    	     }else if("2"==value){
			    	    	 return "参数下发成功";
			    	     }else{
			    	    	 return "";
			    	    	 }}},	
		    {header : '执行状态', sortable: true, dataIndex : 'execStatus', align : 'center',
			     renderer:function(value){
			   		if("1"==value){
			   			return "<font color='green';font-weight:bold>成功</font>";
			   		}else if("0"==value){
			   			return "<font color='red';font-weight:bold>失败</font>";
			   		}else if("-1"==value){
			   			return  "未执行";
			   		}else{
			   		    return "";
			   		}}},
		    {header : '报文', sortable: true, dataIndex : 'message', align : 'center',width:50,
	    		renderer: function(s, m, rec){
					return "<a href='javascript:'onclick='origFrameQryShow(\""+rec.get('consNo')+"\",\""+ rec.get('consName')+"\",\""+ rec.get('terminalAddr')+"\");" + "'>查看</a>"; 
			}}
		]);
		
	//定义Grid的store
	var userStore = new Ext.data.Store({
 	        proxy : new Ext.data.HttpProxy({
						url:'./baseapp/suspensionControl!loadGridData.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'userGridList',
						idProperty: 'keyId'
					}, [
					   {name : 'keyId'},
					   {name : 'orgNo'},
					   {name : 'orgName'}, 
					   {name : 'consNo'}, 
					   {name : 'consName'}, 
					   {name : 'terminalAddr'},
					   {name : 'tmnlAssetNo'}, 
					   {name : 'totalNo'}, 
					   {name : 'protocolCode'},
					   {name : 'protect'},
					   {name : 'ctrlStatus'},
					   {name : 'execStatus'},
					   {name : 'stopStart'},
					   {name : 'stopEnd'},
					   {name : 'stopConst'},
					   {name : 'message'}
					   ])
		});
	
	function storeToArray(valStore) {
		var data = new Array();
		for (var i = 0; i < valStore.getCount(); i++) {
			data[i] = new Array();
			data[i][0] = valStore.getAt(i).data.keyId;
 			data[i][1] = valStore.getAt(i).data.orgNo;
			data[i][2] = valStore.getAt(i).data.orgName;
			data[i][3] = valStore.getAt(i).data.consNo;
			data[i][4] = valStore.getAt(i).data.consName;
			data[i][5] = valStore.getAt(i).data.terminalAddr;
			data[i][6] = valStore.getAt(i).data.tmnlAssetNo;
			data[i][7] = valStore.getAt(i).data.totalNo;
			data[i][8] = valStore.getAt(i).data.protocolCode;
			data[i][9] = valStore.getAt(i).data.protect;
			data[i][10] = valStore.getAt(i).data.ctrlStatus;
			data[i][11] = valStore.getAt(i).data.execStatus;
			data[i][12] = valStore.getAt(i).data.stopStart;
			data[i][13] = valStore.getAt(i).data.stopEnd;
			data[i][14] = valStore.getAt(i).data.stopConst;
			data[i][15] = valStore.getAt(i).data.message;
		}
		return data;
	}
	
	var userListStore = new Ext.data.Store({
		remoteSort : true,
		proxy : new Ext.data.MemoryProxy(),
		reader : new Ext.data.ArrayReader({
				idIndex : 0,
				fields : [ 
					   {name : 'keyId'},
					   {name : 'orgNo'},
					   {name : 'orgName'}, 
					   {name : 'consNo'}, 
					   {name : 'consName'}, 
					   {name : 'terminalAddr'},
					   {name : 'tmnlAssetNo'}, 
					   {name : 'totalNo'}, 
					   {name : 'protocolCode'},
					   {name : 'protect'},
					   {name : 'ctrlStatus'},
					   {name : 'execStatus'},
					   {name : 'stopStart'},
					   {name : 'stopEnd'},
					   {name : 'stopConst'},
					   {name : 'message'}
					   ]
				})
		});

	// grid解锁
	function unlockGrid() {
		userSm.unlock();
		userListGrid.onEnable();
		userListGrid.getBottomToolbar().enable();
	}

	// grid上锁
	function lockGrid() {
		userSm.lock();
		userListGrid.onDisable();
		userListGrid.getBottomToolbar().disable();
	}
	
	var gsselectAllcb=new Ext.form.Checkbox({
		boxLabel : '全选',
		name : 'gsselectAllcb',
		checked : false,
		listeners : {
			'check' : function(r, c) {
				if (c) {
					userSm.selectAll();
					lockGrid();
				} else {
					unlockGrid();
					userSm.clearSelections();
				}
			}
		}
	});
    var userListGrid = new Ext.grid.GridPanel({
	        store : userListStore,
	        cm : cm,
	        sm : userSm,
	        stripeRows : true,
	        autoScroll : true,
	        border: false,
	        tbar : [{xtype: 'tbfill'},
	                gsselectAllcb,
	              '-',{
	        		xtype:'button',
				    text : '删除选中用户',
				    iconCls: 'cancel',
				    handler : function() {
						if (gsselectAllcb.checked) {
							userStore.removeAll(true);
						} else {
							var recs = userSm.getSelections();
							for (var i = 0; i < recs.length; i = i + 1) {
								userStore.remove(userStore
										.getById(recs[i].data.keyId));
							}
						}
						userListStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
						userListStore.load({
									params : {
										start : 0,
										limit : DEFAULT_PAGE_SIZE
									}
						});
					}
				},'-',{
					xtype:'button',
					text : '加入群组',
					iconCls: 'plus',
					handler:function(){
						var groupTmnlArray = new Array();
						if(gsselectAllcb.checked) {
							for (var i = 0; i < userStore.getCount(); i++) {
								var tmnl = userStore.getAt(i).get('consNo')
										+ '`'
										+ userStore.getAt(i).get('tmnlAssetNo');
								groupTmnlArray[i] = tmnl;
							}
						} 
						else{
							var recs = userSm.getSelections();
							for (var i = 0; i < recs.length; i++) {
								var tmnl = recs[i].get('consNo') + '`'
										+ recs[i].get('tmnlAssetNo');
								groupTmnlArray[i] = tmnl;
							}
						}
						if(groupTmnlArray.length == 0) {
							Ext.Msg.alert('提示', '请选择要加入群组的用户');
						} 
						else{
							saveOrUpdateGroupWindowShow(groupTmnlArray);							
						}
						if (gsselectAllcb.checked) {
							gsselectAllcb.setValue(false);
							userSm.selectAll();
						}
					}
				},'-',{
					xtype:'button',
					text : '删除成功用户',
					iconCls: 'minus',
	                handler:function(){
	                 		for (var i = userStore.getCount()-1; i >=0; i--) {
	                 			if("1"==userStore.getAt(i).get('execStatus'))
									userStore.removeAt(i);
							}
							userListStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
							userListStore.load({
										params : {
											start : 0,
											limit : DEFAULT_PAGE_SIZE
										}
							});
	                 }
				}],
			bbar : new Ext.ux.MyToolbar( {
				store : userListStore,
				allStore: userStore,
				enableExpAll : true, // excel导出全部数据
			    expAllText : "全部",
				enableExpPage: true, // excel仅导出当前页
				expPageText : "当前页"
			})    
    });
	//定义用户列表
    var userListGridPnl = new Ext.Panel({
	    region:'center',
	    layout:'fit',
	    border : false,
		items : [userListGrid]
	});    
   
	//-----------------------------定义参数召测列表-------------------------start
	//定义参数召测列表
	var rowNum_result= new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(resultListStore && resultListStore.lastOptions && resultListStore.lastOptions.params){
				startRow = resultListStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	
	var rm = new Ext.grid.ColumnModel([
           rowNum_result,
		    {header : '供电单位', sortable: true, dataIndex : 'orgName', align : 'center',width:150,
        	   renderer : function(val) {
	        	   if(null!=val){
						var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
								+ val + '</div>';
						return html;
	        	   }
	        	   else
	        		   return '';
	        	   }}, 
		    {header : '用户编号', sortable: true, dataIndex : 'consNo', align : 'center'}, 
		    {header : '用户名称', sortable: true, dataIndex : 'consName', align : 'center',width:150,
		    	renderer : function(val) {
			       if(null!=val){
						var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
								+ val + '</div>';
						return html;
			       }
			       else
			    	   return '';
			       }}, 
		    {header : '终端地址', sortable: true, dataIndex : 'terminalAddr', align : 'center'},
		    {header : '总加组号', sortable: true, dataIndex : 'totalNo', align : 'center',width:80}, 
		    {header : '功控定值(kW)', sortable: true, dataIndex : 'stopConst', align : 'center'}, 
		    {header : '开始时间', sortable: true, dataIndex : 'stopStart', align : 'center'}, 
		    {header : '结束时间', sortable: true, dataIndex : 'stopEnd', align : 'center'},
		    {header : '报文', sortable: true, dataIndex : 'message', align : 'center',width:50,
		    	renderer: function(s, m, rec){
					return "<a href='javascript:'onclick='origFrameQryShow(\""+rec.get('consNo')+"\",\""+ rec.get('consName')+"\",\""+ rec.get('terminalAddr')+"\");" + "'>查看</a>"; 
			}}
		]);
		
	//定义Grid的store
	var resultListStore = new Ext.data.Store({
		remoteSort : true,
		proxy : new Ext.data.MemoryProxy(),
		reader : new Ext.data.ArrayReader({
			        idIndex: 0
				}, [
				   {name : 'keyId'},
				   {name : 'orgName'}, 
				   {name : 'consNo'}, 
				   {name : 'consName'}, 
				   {name : 'terminalAddr'},
				   {name : 'totalNo'},
				   {name : 'stopConst'},
				   {name : 'stopStart'},
				   {name : 'stopEnd'},
				   {name : 'message'}
				   ])
	});
	
	//返回参数召测列表面板
    var resultListGrid = new Ext.grid.GridPanel({
	        store : resultListStore,
	        cm : rm,
	        stripeRows : true,
	        autoScroll : true,
	        border: false,
//	        tbar : [{xtype: 'tbfill'},{
//	        		xtype:'button',
//				    text : '删除选中用户',
//				    iconCls: 'minus',
//			        handler : function() {
//					    var recs = resultSm.getSelections();
//					    for (var i = 0; i < recs.length; i = i + 1) {
//						    resultListStore.remove(recs[i]);
//					    }
//				    }
//				}]
	        bbar : new Ext.ux.MyToolbar({
				store : resultListStore, 
				enableExpAll : true, // excel导出全部数据
			    expAllText : "全部",
				enableExpPage: true, // excel仅导出当前页
				expPageText : "当前页"
	        })  
    });
		    
    var resultListGridPnl = new Ext.Panel({
	    region:'center',
	    layout:'fit',
	    border : false,
		items : [resultListGrid]
	});
	//-----------------------------定义参数召测列表-------------------------end
	  //-------------------营业报停控参数panel---------------------start
	  //功控定值
		var powerCtrlData = new Ext.form.NumberField({
			    id : 'sc_powerCtrlData',
				fieldLabel : '功控定值(kW)',
				emptyText : '请输入功控定值',
				name : 'powerCtrlData',
				allowNegative:false,
   	    		decimalPrecision:3,
				labelSeparator:'',
				width:100,
				allowBlank : false,
				blankText : '请输入功控定值'
		});
		
		//开始日期
		var startSusDate = new Ext.form.DateField({
			id : 'sc_startSusDate',
			fieldLabel : '从',
			name : 'startSusDate',
			width:100,
			format: 'Y-m-d',
			value : new Date(),
		    labelSeparator:'',
		    allowBlank : false,
			blankText : '请选择开始日期'
	    });
	
	    //结束日期
	    var endSusDate = new Ext.form.DateField({
	    	id : 'sc_endSusDate',
			fieldLabel : '到',
			name : 'endSusDate',
			width:100,
			format: 'Y-m-d',
		    value : new Date().add(Date.DAY,30),
		    labelSeparator:'',
		    allowBlank : false,
			blankText : '请选择结束日期'
	    });

		
		//参数下发按钮
		var sendBtn = new Ext.Button({
				text : '参数下发',
				name : 'send',
				width:80,
			    labelSeparator:'',
			    handler:function(){			
					tb_suspCtrl.setActiveTab(0);
					var users = userSm.getSelections();
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
			    	if(!checkSusInitData()){
                        return;
                    }
                    ctrlCheckStaff(susParamSend, '');
			    }
		});
		
		function susParamSend(){
			var users = userSm.getSelections();
			var suspensionCtrlArray = new Array();
            if (gsselectAllcb.checked){
         		for (var i = 0; i < userStore.getCount(); i++){
                	if("0"==userStore.getAt(i).get('protect')&&("-1"==userStore.getAt(i).get('ctrlStatus')||"0"==userStore.getAt(i).get('ctrlStatus')||"2"==userStore.getAt(i).get('ctrlStatus'))){
			     		var tmnltotal = userStore.getAt(i).get('tmnlAssetNo')+'`'+userStore.getAt(i).get('totalNo')+'`'+userStore.getAt(i).get('protocolCode');
			     		suspensionCtrlArray[i]=tmnltotal;
			     		userStore.getAt(i).set('execStatus',"0");
                	}
                	else
                		userStore.getAt(i).set('execStatus',"-1"); 
         		}
            }
            else{
                for(var i = 0; i < users.length; i++){
            		var rec = userStore.getById(users[i].get('keyId'));	
                	if("0"==users[i].get('protect')&&("-1"==users[i].get('ctrlStatus')||"0"==users[i].get('ctrlStatus')||"2"==users[i].get('ctrlStatus'))){
			     		var tmnltotal = users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');
			     		suspensionCtrlArray[i]=tmnltotal;
			     		if(null!=rec)
			     		  	rec.set('execStatus',"0");
                	}
                	else{
                		if(null!=rec)
			     		  	rec.set('execStatus',"-1"); 
                	}
		     	}
            }
            userStore.commitChanges();
            if(0==suspensionCtrlArray.length){
    			userListStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
    			userListStore.load({
							params : {
								start : userListStore.lastOptions.params.start,
								limit : DEFAULT_PAGE_SIZE
							}
				});
    	   		return;
            }
            var taskSecond=Ext.Ajax.timeout/1000;
            var ov = true;
            h_taskTime(taskSecond,function(){
            		ov = false;	
            });  
	    	Ext.Ajax.request({
	     		url:'./baseapp/suspensionControl!sendout.action',
	     		params : {
	    			suspensionCtrlList:suspensionCtrlArray,
	     			powerCtrlData:powerCtrlData.getValue(),
	     			startSusDate:startSusDate.getValue(),
	     			endSusDate:endSusDate.getValue(),
	     			taskSecond:taskSecond
	     		},
	     		success : function(response){
	     			if (!ov){
						return;
	     			}
	     			var result = Ext.decode(response.responseText);
	     			if(null!=result.cacheAndTmnlStatus&&""!=result.cacheAndTmnlStatus){
			 				Ext.MessageBox.alert("提示",result.cacheAndTmnlStatus);
			 				return;
		 			 }
	     			if(null != result.tmnlExecStatusList && 0< result.tmnlExecStatusList.length ){
  		     			for(var j=0;j<result.tmnlExecStatusList.length;j++){
  		     				var rec = userStore.getById(result.tmnlExecStatusList[j].keyId);	
  		                    if(null!=rec){
  			     				 if("1"==result.tmnlExecStatusList[j].execFlag){
  			                		 rec.set('ctrlStatus',"2");
  			 				     	 rec.set('execStatus',"1");
  		                   		 }
	                    	}
  			            }
  		     			userStore.commitChanges();
                    }
                    userListStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
				         userListStore.load({
									params : {
										start : userListStore.lastOptions.params.start,
										limit : DEFAULT_PAGE_SIZE
									}
					});
	     			overFlat = true;
	     			Ext.Ajax.timeout=30000;
	     		}
	     	});
		}
		//参数召测按钮
		var callBtn = new Ext.Button({
				text : '参数召测',
				name : 'call',
				width:80,
			    labelSeparator:'',
			    handler:function(){
					tb_suspCtrl.setActiveTab(0);
                    var users = userSm.getSelections();
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
	  		     	 ctrlCheckStaff(susParamFetch, '');
			    }
		});
		function susParamFetch(){
			resultListStore.loadData('');
			var users = userSm.getSelections();
			var suspensionCtrlArray = new Array();
            if (gsselectAllcb.checked){
         		for (var i = 0; i < userStore.getCount(); i++){
         			var tmnltotal = userStore.getAt(i).get('tmnlAssetNo')+'`'+userStore.getAt(i).get('totalNo');
         			suspensionCtrlArray[i]=tmnltotal;
         		}
     	    }
            else{
		     	for(var i = 0; i < users.length; i++){
		     		var tmnltotal = users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo');
		     		suspensionCtrlArray[i]=tmnltotal;
		     	}
            }
            var taskSecond=Ext.Ajax.timeout/1000;
            var ov = true;
            h_taskTime(taskSecond,function(){
            		ov = false;	
            });
	    	Ext.Ajax.request({
	     		url:'./baseapp/suspensionControl!fetch.action',
	     		params : {
	    			suspensionCtrlList:suspensionCtrlArray,
	    			taskSecond:taskSecond
	     		},
	     		success : function(response){
	     			 if (!ov) {
						return;
					 }
					 var result = Ext.decode(response.responseText);
	     			 if(null!=result.cacheAndTmnlStatus&&""!=result.cacheAndTmnlStatus){
			 				Ext.MessageBox.alert("提示",result.cacheAndTmnlStatus);
			 				return;
		 			 }
  		     		 var record = new Array();
                     if (gsselectAllcb.checked){
 	           	       	for(var i = 0; i < userStore.getCount(); i++){
 	           	    	   record[i]=new Array();
 		           	       record[i][0] = userStore.getAt(i).get('tmnlAssetNo')+users[i].get('totalNo');
 		                   record[i][1] = userStore.getAt(i).get('orgName');
 		                   record[i][2] = userStore.getAt(i).get('consNo');
 		                   record[i][3] = userStore.getAt(i).get('consName');
 		                   record[i][4] = userStore.getAt(i).get('terminalAddr');
 		                   record[i][5] = userStore.getAt(i).get('totalNo');
 	           	       	 }
                     }
                     else{
                     	for(var i = 0; i < users.length; i++){
                            record[i] = new Array();
                            record[i][0] = users[i].get('tmnlAssetNo')+users[i].get('totalNo');
                            record[i][1] = users[i].get('orgName');
                            record[i][2] = users[i].get('consNo');
                            record[i][3] = users[i].get('consName');
                            record[i][4] = users[i].get('terminalAddr');
                            record[i][5] = users[i].get('totalNo');
                         }
                     }
	     			
	     			 if(null != result.resultList && 0< result.resultList.length){
  		     			for(var j = 0; j < result.resultList.length; j++){
  		     				for(var k = 0; k < record.length; k++){
  		     					if(record[k][0]==result.resultList[j].keyId){
  		     						record[k][6]=result.resultList[j].stopConst;
  		     						record[k][7]=result.resultList[j].stopStart;
  		     						record[k][8]=result.resultList[j].stopEnd;
  		     						break;
  		     					}
  		     				}	
  		     			}
	     			 }
	     			 resultListStore.proxy = new Ext.ux.data.PagingMemoryProxy(record);
	     			 resultListStore.load({
  								params : {
  									start : 0,
  									limit : DEFAULT_PAGE_SIZE
  								}
  					 });
	     			if (gsselectAllcb.checked){
				    	 gsselectAllcb.setValue(false);
				    	 userSm.selectAll();
				    }
                     tb_suspCtrl.setActiveTab(1);
                     overFlat = true;
             		 Ext.Ajax.timeout=30000;
	     		}
	     	});
		}
		var topPnl = new Ext.Panel({
			title : '营业报停控参数',
			border : false,
			anchor:'100% 50%',
			layout:'table',
	    	layoutConfig : {
				columns :5
		    },
		    defaults: {height: 35},
			items:[{
				layout: 'form',
				border : false,
				width:258,
				items : [{
					layout : 'form',
				    border : false,
				    labelWidth : 90,
				    labelAlign : 'right',
				    bodyStyle:'padding:10px 0px 0px 15px',
					items : [powerCtrlData]
				    }]
			},{ layout : 'form',
		        border : false,
		        width:130,
		        labelWidth : 20,
		        labelAlign : 'right',
		        bodyStyle:'padding:10px 0px 0px 0px',
		        items : [startSusDate]
             },{
		    	layout : 'form',
		        border : false,
		        width:187,
		        labelWidth : 30,
		        labelAlign : 'right',
		        bodyStyle:'padding:10px 0px 0px 0px',
		        items : [endSusDate]
		    },{
		    	layout : 'form',
		    	border : false,
		    	width  :100,
		    	bodyStyle:'padding:10px 0px 0px 0px',
		    	items : [sendBtn]
		    },{

		    	layout : 'form',
		    	border : false,
		    	width:100,
		    	bodyStyle:'padding:10px 0px 0px 0px',
		    	items : [callBtn]
		    }]
		}); 
	   //-------------------营业报停控参数panel---------------------end
		//-------------------营业报停执行panel---------------------start
		//方案加载按钮，用于弹出方案加载窗口
		var loadBtn = new Ext.Button({
				text : '方案加载',
				name : 'load',
				width:80,
			    handler:function(){
				//--------------------------------方案加载----------------------------------start
				//定义方案加载弹出页面的上部分面板组件
				var loadSchemeTopPnl=new Ext.Panel({
					region:'north',
			        height:50,
			        border : false,
			        layout:'column',
			        items:[{
			   	       columnWidth:.75,
			   	       layout:'form',
			   	       border : false,
			   	       bodyStyle : 'padding:20px 0px 0px 20px',
			   	       items:[{
			   	           xtype:'label',
			   	           text:'营业报停方案加载'
			   	       }] 
			   	    },{
			   	       columnWidth:.25,
			   	       layout:'form',
			   	       border : false,
			   	       bodyStyle : 'padding:20px 0px 0px 0px',
			   	       items:[{
			   	           xtype:'button',
			   	           text:'选择',
			   	           handler:function(){
			   	           	   var selectionsArray = multiSel.view.getSelectedIndexes();
			   	           	   if(0 == selectionsArray.length){
			   	           	   		Ext.MessageBox.alert("提示","请选择一个方案！");
			 	    				return;
			   	           	   }
		   	           	       if(1 < selectionsArray.length){
				   	           	   	Ext.MessageBox.alert("提示","只能选择一个方案！");
			 	    				return;
		   	           	       }
		   	           	       loadScheme.hide();
			   	           	   var schemeIdValue;
			   	           	   var type = "schemeId";
		   	                   schemeIdValue = loadFuncStore.getAt(selectionsArray[0]).get('ctrlSchemeId');	               
			   	           	   userStore.baseParams = {
			   	           	   	   schemeId:schemeIdValue,
			   	           	   	   nodeType:type
			   	           	   };
			   	           	   Ext.getBody().mask("加载中...");
			   	           	   userStore.load({
			   	           	   	   callback: function(records, options, success){ 
 									   Ext.getBody().unmask(); 
		                               if(null != records && 0 < records.length){
		                                   Ext.getCmp('sc_powerCtrlData').setValue(records[0].get("stopConst"));
		                                   Ext.getCmp('sc_startSusDate').setValue(Date.parseDate(records[0].get("stopStart"),'Y-m-d H:i:s.0'));
		                                   Ext.getCmp('sc_endSusDate').setValue(Date.parseDate(records[0].get("stopEnd"),'Y-m-d H:i:s.0'));
		                               }
		                               else{
		                               	   Ext.getCmp('sc_powerCtrlData').setValue("");
		                               	   Ext.getCmp('sc_startSusDate').setValue(new Date());
		                                   Ext.getCmp('sc_endSusDate').setValue(new Date().add(Date.DAY,30));
		                               }
		                               loadScheme.close();
		   	                           return;
		                           }  
			   	           	   });
			  		       }
			   	       }] 
			   	    }]
		        });
		        
		        //Json对象
			    var loadFuncStore = new Ext.data.JsonStore({
				    proxy : new Ext.data.MemoryProxy(),
				    fields : ['ctrlSchemeId','ctrlSchemeName','ctrlSchemeType' ],
				    root : "schemeList"
			    });
			    
			    //多选框
			    var multiSel = new Ext.ux.form.MultiSelect({
							labelSeparator : '',
							name : 'multiselect',
							height : 120,
							width : 220,
							displayField : 'ctrlSchemeName',
							valueField : 'ctrlSchemeId',
							labelSeparator : '',
							anchor : '90%',
							store : loadFuncStore
				});
		        
		        //定义方案加载弹出页面的下部分面板组件
		        var loadSchemeBtmPnl =new Ext.Panel({
			        border : false,
			        layout:'column',
			        region:'center',
			        items:[{
			   	        columnWidth:.75,
			   	        border : false,
			   	        layout:'fit',
			   	        hideLabels:true,
			   	        bodyStyle : 'padding:0px 0px 0px 20px',
			   	        items:[multiSel] 
			   	    },{
			   	       columnWidth:.25,
			   	       border : false,
			   	       layout:'form',
			   	       items:[{
			   	           xtype:'button',
			   	           width:80,
			   	           text:'退出',
			   	           handler:function(){
			   	           	   loadScheme.close();
			  		       }
			   	       	}] 
			   	     }]
			     });
		
		        //方案加载窗口
		        var loadScheme = new Ext.Window({
		            name:'loadScheme',
		            modal:true,
		            height:250,
		            width:400,
			        resizable:false,
			        layout:'border',
			        title:'方案加载',
			        items:[loadSchemeTopPnl,loadSchemeBtmPnl]
		        }); 
		    	Ext.Ajax.request({
  		     		url:'./baseapp/suspensionControl!loadScheme.action',
  		     		success : function(response){
  		     			var result = Ext.decode(response.responseText);
				        loadFuncStore.loadData(result);
  		     		}
  		     	});
  		        loadScheme.show();
	  		  }
		});
		//--------------------------------方案加载----------------------------------end

        //--------------------------------方案保存----------------------------------start
		var saveBtn = new Ext.Button({
				text : '保存为方案',
				name : 'save',
				width:80,
			    handler:function(){
					tb_suspCtrl.setActiveTab(0);
					var users = userSm.getSelections();
                    if(gsselectAllcb.checked ){
						if(0==userStore.getCount()){
						  Ext.MessageBox.alert("提示","请选择用户！");
		  		     	    return;
						}
	  		     	}else{
	  		     		if(null == users || 0==users.length){
		  		     	    Ext.MessageBox.alert("提示","请选择用户！");
		  		     	    return;
		  		     	}
	  		     	}
                    if(!checkSusInitData()){
                        return;
                    }
                    var suspensionCtrlArray = new Array();
		            if (gsselectAllcb.checked){
		            	for(var i = 0; i < userStore.getCount(); i++){
			     		var tmnltotal = userStore.getAt(i).get('orgNo')+'`'+userStore.getAt(i).get('consNo')+'`'+userStore.getAt(i).get('tmnlAssetNo')+'`'+userStore.getAt(i).get('totalNo');
			     		suspensionCtrlArray[i]=tmnltotal;
			     		}
		            }
			     	else{
	                    for(var i = 0; i < users.length; i++){
				     		var tmnltotal = users[i].get('orgNo')+'`'+users[i].get('consNo')+'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo');
				     		suspensionCtrlArray[i]=tmnltotal;
				     	}
			     	}
                    //定义radio选择组
				    var scRadioGroup = new Ext.form.RadioGroup({
					    width : 200,
					    height :20,
					    items : [new Ext.form.Radio({
						    boxLabel : '另存为方案',
						    name : 'sc-radioGroup',
						    inputValue : '0',
						    checked : true,
						    handler:function(checkbox, checked){
						    	if(checked){
						    		saveSusSchemePnl.layout.setActiveItem(0);
						    	}
					    	}
					    }), new Ext.form.Radio({
						    boxLabel : '保存方案',
						    name : 'sc-radioGroup',
						    inputValue : '1',
						    handler:function(checkbox, checked){
						    	if(checked){
						    		ds_Scheme1.load();
						    		saveSusSchemePnl.layout.setActiveItem(1);
						    	}
						    }
					    })]
				    });
				    var schemeRedioPanel=new Ext.Panel({
				    	region:'north',
				    	layout:'form',
				        border:false,
				        height:30,
				        bodyStyle : 'padding:10px 0px 0px 40px',
			            hideLabels:true,
				        items:[scRadioGroup]  
				    });
					//开始日期
					var saveSchemeStartDate = new Ext.form.DateField({
						fieldLabel : '有效期从',
						name : 'saveSchemeStartDate',
						format:'Y-m-d',
						width:108,
						value : new Date(),
					    labelSeparator:'',
					    allowBlank:false,
		  				blankText : '请选择开始日期'
				    });
				
				    //结束日期
				    var saveSchemeEndDate = new Ext.form.DateField({
						fieldLabel : '到',
						name : 'saveSchemeEndDate',
						format:'Y-m-d',
						width:108,
						value : new Date().add(Date.DAY,30),
					    labelSeparator:'',
					    allowBlank:false,
		  				blankText : '请选择结束日期'
				    });
					
					//营业报停方案label
					var saveSchemeLabel = new Ext.form.TextField({
				        fieldLabel:'方案名称',
			   	        value:'营业报停方案',
			   	        readOnly:true,
			   	        labelSeparator:'',
			   	        allowBlank:false,
		                anchor:'98%' 
					});
					
					//方案名
					var saveSchemeName = new Ext.form.TextField({
						width:145,
						emptyText : '请输入方案名称',
					    allowBlank:false,
					    blankText : '请输入方案名称'
					});
					 //方案备注
					var saveSchemeRemark =new Ext.form.TextArea({
				    	    fieldLabel:'备注',
				    		width:255,
				    		height:80
				    });
					//定义另存为方案Card组件
					var saveSusScuheme=new Ext.Panel({
			            height:170,
				        border : false,
				        layout:'form',
				        buttonAlign:'center',
				        items:[{
				        	layout:'column',
				   	        border : false,
				   	        items:[{
					   	            columnWidth:.52,
					   	            layout:'form',
					   	            border : false,
					   	            bodyStyle : 'padding:10px 0px 0px 15px',
					   	            labelAlign: "right",
					   	            labelWidth:70,
					   	            items:[saveSchemeLabel] 
					   	        }, {
					   	            columnWidth:.48,
					   	            layout:'form',
					   	            border : false,
					   	            bodyStyle : 'padding:10px 0px 0px 0px',
					   	            hideLabels:true,
					   	            items:[saveSchemeName] 
					   	        }]
				        },{
				   	        layout:'column',
				   	        border : false,
				   	        items:[{
				   	            columnWidth:.55,
				   	            layout:'form',
				   	            border : false,
				   	            bodyStyle : 'padding:5px 0px 0px 15px',
				   	            labelAlign: "right",
				   	            labelWidth:70,
				   	            items:[saveSchemeStartDate] 
			   	        }, {
			   	            columnWidth:.45,
			   	            layout:'form',
			   	            border : false,
			   	            labelWidth:20,
			   	            labelAlign: "right",
			   	            bodyStyle : 'padding:5px 0px 0px 0px',
			   	            items:[saveSchemeEndDate] 
			   	        }]
			   	    },{
			   	            layout:'form',
			   	            border : false,
			   	            bodyStyle : 'padding:5px 0px 0px 15px',
			   	            labelAlign: "right",
			   	            labelWidth:70,
			   	            items:[saveSchemeRemark] 
			        }],
				    buttons:[{
		         	    text:'确定',
		         	    handler:function(){
						    	 if("" == saveSchemeName.getValue().trim() || 0 >=  saveSchemeName.getValue().trim().length){
							            Ext.MessageBox.alert("提示","请输入方案名！"); 
							            return;
						         }
						         if("" == saveSchemeStartDate.getValue()){
							            Ext.MessageBox.alert("提示","请输入开始日期！"); 
							            return;
						         }
						         if("" == saveSchemeEndDate.getValue()){
							            Ext.MessageBox.alert("提示","请输入结束日期！"); 
							            return;
						         }
						         if(saveSchemeStartDate.getValue() > saveSchemeEndDate.getValue()){
							             Ext.MessageBox.alert("提示","开始日期应早于结束日期！"); 
								         return;
						         }
						         saveScheme.hide();
					             Ext.getBody().mask("保存中...");
					             Ext.Ajax.request({
					            	url : './baseapp/suspensionControl!saveSusScheme.action',
									params : {
										powerCtrlData : Ext.getCmp('sc_powerCtrlData').getValue(),
										ctrlSchemeName: saveSchemeLabel.getValue()+'-'+saveSchemeName.getValue().trim(),
										startSusDate:Ext.getCmp("sc_startSusDate").getValue(),
										endSusDate:Ext.getCmp("sc_endSusDate").getValue(),
										newStartDate:saveSchemeStartDate.getValue(),
										newEndDate:saveSchemeEndDate.getValue(),
										suspensionCtrlList:suspensionCtrlArray,
										schemeRemark:saveSchemeRemark.getValue().trim()
									},
									success : function(response) {
										var result = Ext.decode(response.responseText);
			  		     	    		if(null!=result.FLAG){
			  		     	    			if(0==result.FLAG){
                                                Ext.MessageBox.alert("提示","该方案名已被使用！",function(btn){
                                                	saveScheme.show();
                                                });
                                                return;
                                            }else if(1==result.FLAG){
    	            	  		     			if(gsselectAllcb.checked) {
													gsselectAllcb.setValue(false);
													userSm.selectAll();
												}
    	            	  		     			Ext.MessageBox.alert("提示","保存方案成功！");
    			  		     	    		}	
			  		     	    		}
			  		     	    		saveScheme.close();
                                	},
					            	callback:function(){
					            		Ext.getBody().unmask();
					            	}
					            });
					            
			         	    }
				   	    },{
			         	    text:'退出',
			         	    handler:function(){
			         	        saveScheme.close();	
			         	    }
				   	    }]
					});
					
					//开始日期
					var updateSchemeStartDate = new Ext.form.DateField({
						fieldLabel : '有效期从',
						name : 'updateSchemeStartDate',
						width:108,
						format:'Y-m-d',
						value : new Date(),
					    labelSeparator:'',
					    allowBlank:false,
		  				blankText : '请选择开始日期'
				    });
				
				    //结束日期
				    var updateSchemeEndDate = new Ext.form.DateField({
						fieldLabel : '到',
						name : 'updateSchemeEndDate',
						width:108,
						format:'Y-m-d',
						value : new Date().add(Date.DAY,30),
					    labelSeparator:'',
					    allowBlank:false,
		  				blankText : '请选择结束日期'
				    });
					//方案备注
					var updateSchemeRemark =new Ext.form.TextArea({
				    	    fieldLabel:'备注',
				    		width:255,
				    		height:80
				    });
					//方案名称Store
					var ds_Scheme1 = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
								url:'./baseapp/suspensionControl!loadScheme.action'
							}),
						reader : new Ext.data.JsonReader({
								root : 'schemeList',
								idProperty: 'ctrlSchemeId'
							}, [{name:'ctrlSchemeId'},
								{name:'ctrlSchemeName'},
								{name:'ctrlSchemeType'},
								{name:'ctrlDateStart'},
								{name:'ctrlDateEnd'},
								{name:'schemeRemark'}])
					});
					
					//方案下拉框
					var updateSchemeName = new Ext.form.ComboBox({
						fieldLabel : '方案名称',
						store : ds_Scheme1,
						displayField : 'ctrlSchemeName',
			            valueField : 'ctrlSchemeId',
			            editable : false,
						bodyStyle: 'padding:10px;',
						triggerAction : 'all',
						mode : 'remote',
						width:255,
						emptyText : '--请选择方案--',
						allowBlank:false,
		  				blankText : '请选择方案',
						forceSelection : true,
						selectOnFocus : true,
						labelSeparator:''
					});
					updateSchemeName.on('select',function(){
						var rec=ds_Scheme1.getById(updateSchemeName.getValue());
							if(null!=rec){
								updateSchemeStartDate.setValue(Date.parseDate(rec.get('ctrlDateStart'),'Y-m-d\\TH:i:s'));
								updateSchemeEndDate.setValue(Date.parseDate(rec.get('ctrlDateEnd'),'Y-m-d\\TH:i:s'));
								updateSchemeRemark.setValue(rec.get('schemeRemark'));
							}
					});
					//定义保存方案Card组件
					var updateSusScheme=new Ext.Panel({
			            height:170,
				        border : false,
				        layout:'form',
				        buttonAlign:'center',
				        items:[{ 
					        	layout:'form',
				   	            border : false,
				   	            bodyStyle : 'padding:10px 0px 0px 15px',
				   	            labelAlign: "right",
				   	            labelWidth:70,
				   	            items:[updateSchemeName]
			   	        },{
					   	        layout:'column',
					   	        border : false,
					   	        items:[{
					   	            columnWidth:.55,
					   	            layout:'form',
					   	            border : false,
					   	            bodyStyle : 'padding:5px 0px 0px 15px',
					   	            labelAlign: "right",
					   	            labelWidth:70,
					   	            items:[updateSchemeStartDate] 
					   	        }, {
					   	            columnWidth:.45,
					   	            layout:'form',
					   	            border : false,
					   	            labelWidth:20,
					   	            labelAlign: "right",
					   	            bodyStyle : 'padding:5px 0px 0px 0px',
					   	            items:[updateSchemeEndDate] 
					   	        }]
				    },{
			   	            layout:'form',
			   	            border : false,
			   	            bodyStyle : 'padding:5px 0px 0px 15px',
			   	            labelAlign: "right",
			   	            labelWidth:70,
			   	            items:[updateSchemeRemark] 
			   	    }],
				    buttons:[{
		         	    text:'确定',
		         	    handler:function(){
						    	 if("" == updateSchemeName.getValue()){
							            Ext.MessageBox.alert("提示","请选择方案！"); 
							            return;
						         }
						         if("" == updateSchemeStartDate.getValue()){
							            Ext.MessageBox.alert("提示","请选择开始日期！"); 
							            return;
						         }
						         if("" == updateSchemeEndDate.getValue()){
							            Ext.MessageBox.alert("提示","请选择结束日期！"); 
							            return;
						         }
						         if(updateSchemeStartDate.getValue() > updateSchemeEndDate.getValue()){
							             Ext.MessageBox.alert("提示","开始日期应早于结束日期！"); 
								         return;
						         }
						         saveScheme.hide();
					             Ext.getBody().mask("保存中...");
					             Ext.Ajax.request({
					            	url : './baseapp/suspensionControl!updateSusScheme.action',
									params : {
										powerCtrlData : Ext.getCmp('sc_powerCtrlData').getValue(),
										schemeId: updateSchemeName.getValue(),
										startSusDate:Ext.getCmp("sc_startSusDate").getValue(),
										endSusDate:Ext.getCmp("sc_endSusDate").getValue(),
										newStartDate:updateSchemeStartDate.getValue(),
										newEndDate:updateSchemeEndDate.getValue(),
										suspensionCtrlList:suspensionCtrlArray,
										schemeRemark:updateSchemeRemark.getValue()
									},
									success : function() {
										saveScheme.close();
                                        if(gsselectAllcb.checked) {
											gsselectAllcb.setValue(false);
											userSm.selectAll();
										}
                                        Ext.MessageBox.alert("提示","保存成功！");
                                        return;
                                	},
					            	callback:function(){
					            		Ext.getBody().unmask();
					            	}
					            });   
			         	    }
				   	    },{
			         	    text:'退出',
			         	    handler:function(){
			         	        saveScheme.close();	
			         	    }
				   	    }]
					});
					
				    var saveSusSchemePnl=new Ext.Panel({
				   	    region:'center',
					   	layout:'card',
				        activeItem : 0,
				        border:false,
				        items:[saveSusScuheme, updateSusScheme]
				    });

			        var saveSchemePnl=new Ext.Panel({
			            layout:'border',
			            border:false,
			            items:[schemeRedioPanel,saveSusSchemePnl]
			        });
			    
			        //保存方案跳出窗口
				    var saveScheme = new Ext.Window({
			            name:'bcfa',
			            modal:true,
			            height:260,
			            width:400,
			            closeAction:'hide',
				        resizable:false,
				        layout:'fit',	        
				        title:'保存方案',
				        items:[saveSchemePnl]
			        }); 
				    saveScheme.show();
				}
		});
        //--------------------------------方案保存----------------------------------end
		
		//营业报停执行按钮
		var executeBtn = new Ext.Button({
				text : '报停执行',
				name : 'execute',
				width:80,
			    handler:function(){
					tb_suspCtrl.setActiveTab(0);
                    var users = userSm.getSelections();
					if(gsselectAllcb.checked ){
						if(0==userStore.getCount()){
						  Ext.MessageBox.alert("提示","请选择用户！");
		  		     	    return;
						}
	  		     	}else{
	  		     		if(null == users || 0==users.length){
		  		     	    Ext.MessageBox.alert("提示","请选择用户！");
		  		     	    return;
		  		     	}
	  		     	}
	  		     	ctrlCheckStaff(susExecute, 'ctrl');
			    }
		});
		
		function susExecute(){
			var users = userSm.getSelections();
			var suspensionCtrlArray = new Array();
            if (gsselectAllcb.checked){
         		for (var i = 0; i < userStore.getCount(); i++){
		     		if("0"==userStore.getAt(i).get('protect')&&"2"==userStore.getAt(i).get('ctrlStatus')){
			     		var tmnltotal = userStore.getAt(i).get('tmnlAssetNo')+'`'+userStore.getAt(i).get('totalNo')+'`'+userStore.getAt(i).get('protocolCode');
			     		suspensionCtrlArray[i]=tmnltotal;
			     		userStore.getAt(i).set('execStatus',"0");
		     		}
		     		else
		     			userStore.getAt(i).set('execStatus',"-1");
         		}
            }
            else{
		     	for(var i = 0; i < users.length; i++){
	     			var rec = userStore.getById(users[i].get('keyId'));	
		     		if("0"==users[i].get('protect')&&"2"==users[i].get('ctrlStatus')){
			     		var tmnltotal = users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');
			     		suspensionCtrlArray[i]=tmnltotal;
			     		if(null!=rec)
			     			rec.set('execStatus',"0");
		     		}
		     		else{
		     			if(null!=rec)
			     			rec.set('execStatus',"-1");
		     		}
		     	}
            }
            userStore.commitChanges();
//			     	var isSendSms;
//			     	if(smsCheckBox.checked==true){
//			     		isSendSms='1';
//			     	}
//			     	else if(smsCheckBox.checked==false){
//			     		isSendSms='0';
//			     	}
	     	if(0==suspensionCtrlArray.length){
     	  		userListStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
     	  		userListStore.load({
							params : {
								start : userListStore.lastOptions.params.start,
								limit : DEFAULT_PAGE_SIZE
							}
				});
    	   		return;
	     	}
	     	var taskSecond=Ext.Ajax.timeout/1000;
	     	var ov = true;
	        h_taskTime(taskSecond,function(){
	        		ov = false;	
	        }); 
	    	Ext.Ajax.request({
	     		url:'./baseapp/suspensionControl!act.action',
	     		params : {
	    			suspensionCtrlList:suspensionCtrlArray,
	    			taskSecond:taskSecond
	    			//isSendSms:isSendSms
	     		},
	     		success : function(response){
	     			if (!ov) {
						return;
					}
	     			var result = Ext.decode(response.responseText);
	     			if(null!=result.cacheAndTmnlStatus&&""!=result.cacheAndTmnlStatus){
			 				Ext.MessageBox.alert("提示",result.cacheAndTmnlStatus);
			 				return;
		 			}
	     			if(null != result.tmnlExecStatusList && 0< result.tmnlExecStatusList.length){
	     				for(var j = 0; j < result.tmnlExecStatusList.length; j++){
  		     				var rec = userStore.getById(result.tmnlExecStatusList[j].keyId);
                            if(null != rec){
                            	if("1"==result.tmnlExecStatusList[j].execFlag){
                            		rec.set('ctrlStatus',"1");
  		     				    	rec.set('execStatus',"1");//修改页面选中记录的状态
                            	}
                            }
  		     			}
	     			}
     				userStore.commitChanges();
     				userListStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
		            userListStore.load({
								params : {
									start : userListStore.lastOptions.params.start,
									limit : DEFAULT_PAGE_SIZE
								}
					});
		     		overFlat = true;
		     		Ext.Ajax.timeout=30000;
	     		}
	     	});
		}
		
		//营业报停解除按钮
		var unlockBtn = new Ext.Button({
				text : '报停解除',
				name : 'unlock',
				width:80,
			    handler:function(){
					tb_suspCtrl.setActiveTab(0);
					var users = userSm.getSelections();
					if(gsselectAllcb.checked ){
						if(0==userStore.getCount()){
							 Ext.MessageBox.alert("提示","请选择用户！");
		  		     	     return;
						}
	  		     	}else{
		  		     	if(null == users || 0==users.length){
		  		     	    Ext.MessageBox.alert("提示","请选择用户！");
		  		     	    return;
		  		     	}
	  		     	}
	  		     	ctrlCheckStaff(susRelease, 'ctrl');
			    }
		});
		
		function susRelease(){
			var users = userSm.getSelections();
            var suspensionCtrlArray = new Array();
            if (gsselectAllcb.checked){
         		for (var i = 0; i < userStore.getCount(); i++){
		     		if("1"==userStore.getAt(i).get('ctrlStatus')){
			     		var tmnltotal = userStore.getAt(i).get('tmnlAssetNo')+'`'+userStore.getAt(i).get('totalNo')+'`'+userStore.getAt(i).get('protocolCode');
			     		suspensionCtrlArray[i]=tmnltotal;
			     		userStore.getAt(i).set('execStatus',"0");
		     		}
		     		else{
		     			userStore.getAt(i).set('execStatus',"-1");
		     		}
         		}
            }
            else{
		     	for(var i = 0; i < users.length; i++){
		     		var rec = userStore.getById(users[i].get('keyId'));	
		     		if("1"==users[i].get('ctrlStatus')){
			     		var tmnltotal = users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');
			     		suspensionCtrlArray[i]=tmnltotal;
			     		if(null!=rec)
			     			rec.set('execStatus',"0");
		     		}
		     		else{
		     			if(null!=rec)
			     			rec.set('execStatus',"-1");
		     		}
		     	}
            }
            userStore.commitChanges();
//			     	var isSendSms;
//			     	if(smsCheckBox.checked==true){
//			     		isSendSms='1';
//			     	}
//			     	else if(smsCheckBox.checked==false){
//			     		isSendSms='0';
//			     	}
	     	if(0==suspensionCtrlArray.length){
     	  		userListStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
     	  		userListStore.load({
							params : {
								start : userListStore.lastOptions.params.start,
								limit : DEFAULT_PAGE_SIZE
							}
				});
    	   		return;
	     	}
	     	var taskSecond=Ext.Ajax.timeout/1000;
	     	var ov = true;
	        h_taskTime(taskSecond,function(){
	        		ov = false;	
	        }); 
	    	Ext.Ajax.request({
	     		url:'./baseapp/suspensionControl!release.action',
	     		params : {
		    		suspensionCtrlList:suspensionCtrlArray,
		    		taskSecond:taskSecond
	    			//isSendSms:isSendSms	
	     		},
	     		success : function(response){
	     			if (!ov){
						return;
					}
	     			var result = Ext.decode(response.responseText);
	     			if(null!=result.cacheAndTmnlStatus&&""!=result.cacheAndTmnlStatus){
			 				Ext.MessageBox.alert("提示",result.cacheAndTmnlStatus);
			 				return;
		 			}
	     			if(null != result.tmnlExecStatusList && 0 < result.tmnlExecStatusList.length){
	     				for(var j = 0; j < result.tmnlExecStatusList.length; j++){
  		     				var rec = userStore.getById(result.tmnlExecStatusList[j].keyId);
                            if(null != rec){
                            	if("1"==result.tmnlExecStatusList[j].execFlag){
                            		rec.set('ctrlStatus',"0");
  		     				  	    rec.set('execStatus',"1");//修改页面选中记录的状态
	     						}
	     					}  
  		     			}
	     				userStore.commitChanges();
                    }
                    userStore.commitChanges();
  				         userListStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
  				         userListStore.load({
  									params : {
  										start : userListStore.lastOptions.params.start,
  										limit : DEFAULT_PAGE_SIZE
  									}
					 });
	     			overFlat = true;
	     			Ext.Ajax.timeout=30000;
	     		}
	     	});
		}
		//定义发送sms
		var smsCheckBox = new Ext.form.Checkbox({
	            border : false,
	            boxLabel : '下发成功后短信通知客户',
	            hideLabel  : true,
	            width : 250
        });
        
		//定义中间面板
		var midPnl = new Ext.Panel({
					anchor:'100% 50%',
					title : '营业报停控执行',
					layout:'table',
			  	    layoutConfig : {
						columns :5
				    },
				    defaults: {height: 35},
					items : [{
						layout : 'form',
					    border : false,
					    width:375,
					    bodyStyle : 'padding:10px 0px 0px 20px',
					    items : [smsCheckBox]
					}, {
						layout : 'form',
					    border : false,
					    width:100,
					    bodyStyle:'padding:10px 0px 0px 0px',
					    items : [loadBtn]
					}, {
						layout : 'form',
					    border : false,
					    width:100,
					    bodyStyle:'padding:10px 0px 0px 0px',
					    items : [saveBtn]
					}, {
						layout : 'form',
					    border : false,
					    width:100,
					    bodyStyle:'padding:10px 0px 0px 0px',
					    items : [executeBtn]
					}, {
						layout : 'form',
					    border : false,
					    width:100,
					    bodyStyle:'padding:10px 0px 0px 0px',
					    items : [unlockBtn]
					}]
		});
	//-------------------营业报停执行panel---------------------end
 
	//-----------------------------定义用户列表-------------------------end

	//返回north面板
	var northPnl = new Ext.Panel({
	    region:'north',
        height:135,
        layout:'anchor',
        border : false,
        items:[topPnl,midPnl]
	});
	//返回center面板
	var tb_suspCtrl = new Ext.TabPanel({
		border: false,
		activeTab: 0,
		region:'center',
		items : [{
			border: false,
			layout : 'fit',
			title : '方案执行列表',
			anchor:'100% 80%',
			id:'grid_susSchemeExec',
			items : [userListGridPnl]
		}, {
			border: false,
			title : '参数召测列表',
			layout : 'fit',
			anchor:'100% 80%',
			items : [resultListGridPnl]
		}]
	});

	//定义整个页面面板
	var viewPanel=new Ext.form.FormPanel({
		layout: 'border',
		border : false,
        items: [northPnl,tb_suspCtrl]
	});
	
	renderModel(viewPanel,'营业报停控制');
	
	//监听左边树点击事件
    var treeListener = new LeftTreeListener({
	    modelName : '营业报停控制',
	    processClick : function(p, node, e) {
	    	if("grid_susSchemeExec"==tb_suspCtrl.getActiveTab().getId()){
			    var obj = node.attributes.attributes;
			    var type = node.attributes.type;
			    if(type == 'org'){
			    	userStore.baseParams = {orgNo:obj.orgNo,orgType:obj.orgType,nodeType:type};
			    	Ext.getBody().mask("正在获取数据...");
			    	userStore.load({
						callback : function(recs, options, success) {
//							if (success)
//								userListGrid.getSelectionModel()
//										.selectRecords(recs, true);
							Ext.getBody().unmask();				
						},
						add : true
					});
			    }else if(type == 'usr'){
			    	userStore.baseParams = {tmnlAssetNo:obj.tmnlAssetNo,nodeType:type};
			    	Ext.getBody().mask("正在获取数据...");
			    	userStore.load({
						callback : function(recs, options, success) {
//							if (success)
//								userListGrid.getSelectionModel()
//										.selectRecords(recs, true);
							Ext.getBody().unmask();				
						},
						add : true
					});
			    }else if(type == 'line'){
			    	userStore.baseParams = {lineId:obj.lineId,nodeType:type};
			    	Ext.getBody().mask("正在获取数据...");
			    	userStore.load({
						callback : function(recs, options, success) {
//							if (success)
//								userListGrid.getSelectionModel()
//										.selectRecords(recs, true);
							Ext.getBody().unmask();				
						},
						add : true
					});
			    }else if(type == 'cgp' || type=='ugp'){
			    	userStore.baseParams = {groupNo:obj.groupNo,nodeType:type};
			    	Ext.getBody().mask("正在获取数据...");
			    	userStore.load({
						callback : function(recs, options, success) {
//							if (success)
//								userListGrid.getSelectionModel()
//										.selectRecords(recs, true);
						Ext.getBody().unmask();				
					},
						add : true
					});
			    }else if(type == 'sub'){
			    	userStore.baseParams = {subsId:obj.subsId,nodeType:type};
			    	Ext.getBody().mask("正在获取数据...");
			    	userStore.load({
						callback : function(recs, options, success) {
//							if (success)
//								userListGrid.getSelectionModel()
//										.selectRecords(recs, true);
							Ext.getBody().unmask();				
						},
						add : true
					});
			    }
	    	}
		    return true;
   	    },
   	    processUserGridSelect:function(cm,row,record){
   		    if("grid_susSchemeExec"==tb_suspCtrl.getActiveTab().getId()){
		   	    userListStore.baseParams = {tmnlAssetNo:record.get('tmnlAssetNo'),nodeType:'usr'};
			   	Ext.getBody().mask("正在获取数据...");
			   	userStore.load({
					callback : function(recs, options, success) {
//							if (success)
//								userListGrid.getSelectionModel()
//										.selectRecords(recs, true);
							Ext.getBody().unmask();				
					},
					add : true
				});
	        }
	        return true;
   	    }
    });
    
    userStore.on('load', function(thisstore, recs, obj) {
    	userListStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(thisstore));
    	userListStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
    	userSm.selectAll();
    	userListGrid.doLayout();
	});
	
    userListStore.on('load',function(){
	    if(gsselectAllcb.checked){
			gsselectAllcb.setValue(false);
		}
	});
	
   //界面组件校验	    
    function checkSusInitData(){
        if("" == Ext.getCmp("sc_powerCtrlData").getValue()&&"0"!= Ext.getCmp("sc_powerCtrlData").getValue()){
    	    Ext.MessageBox.alert("提示","请输入功控定值！"); 
     	    return false;
        }
        if(0 >= Ext.getCmp("sc_powerCtrlData").getValue() || 999999 <= Ext.getCmp("sc_powerCtrlData").getValue()){
        	Ext.MessageBox.alert("提示","功控定值范围必须在1-999999之间！");
        	return false;
        }
        if("" == Ext.getCmp("sc_startSusDate").getValue()){
    	    Ext.MessageBox.alert("提示","请输入开始时间！"); 
     	    return false;
        }
        if("" == Ext.getCmp("sc_endSusDate").getValue()){
    	    Ext.MessageBox.alert("提示","请输入结束时间！"); 
     	    return false;
        }
        if(Ext.getCmp("sc_startSusDate").getValue() > Ext.getCmp("sc_endSusDate").getValue()){
            Ext.MessageBox.alert("提示","开始时间应早于结束时间！"); 
     	    return false;
        }
        return true;
    }
});
