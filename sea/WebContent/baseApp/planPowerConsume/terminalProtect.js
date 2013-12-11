
var schemeId;
function origFrameQryShow(consNo,consName,tmnlAssetAddr){
	staticConsNo = consNo;
	staticConsName = consName;
	staticTmnlAssetAddr = tmnlAssetAddr;
	openTab("原始报文查询","./baseApp/dataGatherMan/origFrameQry.jsp");
}

Ext.onReady(function(){

	/**
	 * 任务进度条
	 */
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
	//--------------------------------------------------------------------------------方案执行列表
	var sm_bd= new Ext.grid.CheckboxSelectionModel();
	var rowNum_bd = new Ext.grid.RowNumberer({
			renderer : function(v, p, record, rowIndex) {
				var startRow = 0;
				if(ds_bd && ds_bd.lastOptions && ds_bd.lastOptions.params){
					startRow = ds_bd.lastOptions.params.start;
				}
				return startRow + rowIndex + 1;
			}
		});
	var cm_bd = new Ext.grid.ColumnModel([ 
	       rowNum_bd,
	       sm_bd,
		   {header:'供电单位',dataIndex:'orgName',sortable:true,resizable:true,align:'center',width:150,
			   renderer : function(val) {
		    	   if(null!=val){
						var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
								+ val + '</div>';
						return html;
		    	   }
		    	   else
		    		    return '';
					}},
		   {header:'用户编号',dataIndex:'consNo',sortable:true,resizable:true,align:'center'},
		   {header:'用户名称',dataIndex:'consName',sortable:true,resizable:true,align:'center',width:150,
			    renderer : function(val) {
			   		if(null!=val){
						var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
								+ val + '</div>';
						return html;
				    }
				    else
					   return '';
				    }},
		   {header:'终端地址',dataIndex:'terminalAddr',sortable:true,resizable:true,align:'center'},
		   {header:'保电状态',dataIndex:'protectStatus',sortable:true,resizable:true,align:'center',width:80,
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
		   {header:'执行状态',dataIndex:'execStatus',sortable:true,resizable:true,align:'center',
			   renderer:function(value){
			   		if("1"==value){
			   			return "<font color='green';font-weight:bold>成功</font>";
			   		}else if("0"==value){
			   			return "<font color='red';font-weight:bold>失败</font>";
			   		}else if("-1"==value){
			   			return  "未执行";
			   		}else{
			   		    return "";
			   		}
			}},
		   {header:'报文',dataIndex:'message',sortable:true,resizable:true,align:'center',
		    renderer: function(s, m, rec){
					return "<a href='javascript:'onclick='origFrameQryShow(\""+rec.get('consNo')+"\",\""+ rec.get('consName')+"\",\""+ rec.get('terminalAddr')+"\");" + "'>查看</a>"; 
			}}
		 ]);
		 
	 var userStore= new Ext.data.Store({
		    url:'baseapp/terminalprotect!generalGridByTree.action',
		    reader : new Ext.data.JsonReader({
					root : 'wTmnlPaulPowerList',
					idProperty:'tmnlAssetNo'
				}, [
			    {name: 'orgNo'},
			    {name: 'orgName'},
			    {name: 'consNo'},
			    {name: 'consName'},
			    {name: 'terminalAddr'},
			    {name: 'tmnlAssetNo'},
				{name: 'autoPaulPower'},
			    {name: 'securityValue'},
			    {name: 'duration'},
			    {name: 'protocolCode'},
			    {name: 'protectStatus'},
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
			data[i][6] = valStore.getAt(i).data.autoPaulPower;
			data[i][7] = valStore.getAt(i).data.securityValue;
			data[i][8] = valStore.getAt(i).data.duration;
			data[i][9] = valStore.getAt(i).data.protocolCode;
			data[i][10] = valStore.getAt(i).data.protectStatus;
			data[i][11] = valStore.getAt(i).data.execStatus;
			data[i][12] = valStore.getAt(i).data.message;
		}
		return data;
	}
	
	var ds_bd = new Ext.data.Store({
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
					{name: 'autoPaulPower'},
				    {name: 'securityValue'},
				    {name: 'duration'},
				    {name: 'protocolCode'},
				    {name: 'protectStatus'},
				    {name: 'execStatus'},
				    {name: 'message'}]
				})
	});
	// grid解锁
	function unlockGrid() {
		sm_bd.unlock();
		grid_bd.onEnable();
		grid_bd.getBottomToolbar().enable();
	}

	// grid上锁
	function lockGrid() {
		sm_bd.lock();
		grid_bd.onDisable();
		grid_bd.getBottomToolbar().disable();
	}
	
	var gsselectAllcb=new Ext.form.Checkbox({
		boxLabel : '全选',
		name : 'gsselectAllcb',
		checked : false,
		listeners : {
			'check' : function(r, c) {
				if (c) {
					sm_bd.selectAll();
					lockGrid();
				} else {
					unlockGrid();
					sm_bd.clearSelections();
				}
			}
		}			
	});
	
	var grid_bd=new Ext.grid.GridPanel({
		border : false,
		store: ds_bd,
		cm: cm_bd,
		sm: sm_bd,
		stripeRows: true,
		tbar:[{xtype: 'tbfill'},
					   gsselectAllcb,
					   '-',
					  {
		                xtype:'button',
		                text:"删除选中用户",
		                iconCls: 'cancel',
	                    handler : function() {
							if (gsselectAllcb.checked) {
								userStore.removeAll(true);
							}else {
								var recs = sm_bd.getSelections();
								for (var i = 0; i < recs.length; i++) {
									userStore.remove(userStore
											.getById(recs[i].data.tmnlAssetNo));
								}
							}
							ds_bd.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
							ds_bd.load({
										params : {
											start : 0,
											limit : DEFAULT_PAGE_SIZE
										}
							});
						}
		             },
                    '-',
                    {
		                 xtype:'button',
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
								var recs = sm_bd.getSelections();
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
								sm_bd.selectAll();
							}
		                 }
                    },
                    '-',
                    {
		                 xtype:'button',
		                 text:"删除成功用户",
		                 iconCls: 'minus',
		                 handler:function(){
		                 		for (var i = userStore.getCount()-1; i >=0; i--) {
		                 			if("1"==userStore.getAt(i).get('execStatus'))
										userStore.removeAt(i);
								}
								ds_bd.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
								ds_bd.load({
											params : {
												start : 0,
												limit : DEFAULT_PAGE_SIZE
											}
								});
		                 }
                }],
	    bbar: new Ext.ux.MyToolbar({
				store : ds_bd,
				allStore: userStore,
				enableExpAll : true, // excel导出全部数据
			    expAllText : "全部",
				enableExpPage: true, // excel仅导出当前页
				expPageText : "当前页"
			})           
	});

	//--------------------------------------------------------------------------------参数召测列表
	var rowNum_paramDevote = new Ext.grid.RowNumberer({
			renderer : function(v, p, record, rowIndex) {
				var startRow = 0;
				if(ds_paramDevote && ds_paramDevote.lastOptions && ds_paramDevote.lastOptions.params){
					startRow = ds_paramDevote.lastOptions.params.start;
				}
				return startRow + rowIndex + 1;
			}
		});
	var cm_paramDevote = new Ext.grid.ColumnModel([  
		   rowNum_paramDevote,
		   {header:'供电单位',dataIndex:'orgName',sortable:true,resizable:true,align:'center',width:150,
			   renderer : function(val) {
				   if(null!=val){
						var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
								+ val + '</div>';
						return html;
				   }
				   else
					   return '';
				   }},
		   {header:'用户编号',dataIndex:'consNo',sortable:true,resizable:true,align:'center'},
		   {header:'用户名称',dataIndex:'consName',sortable:true,resizable:true,align:'center',width:150,
			  renderer : function(val) {
				   if(null!=val){
						var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
								+ val + '</div>';
						return html;
				   }
				   else
						return '';
				   }},
		   {header:'终端地址',dataIndex:'terminalAddr',sortable:true,resizable:true,align:'center',
		  		renderer : function (val) {
							return "<div align = 'left'>"+ val + "</div>";}},
		   {header:'自动保电参数(小时)',dataIndex:'autoPaulPower',sortable:true,resizable:true,align:'center'},
		   {header:'保安定值(kW)',dataIndex:'securityValue',sortable:true,resizable:true,align:'center'},
		   {header:'报文',dataIndex:'message',sortable:true,resizable:true,align:'center',
		    renderer: function(s, m, rec){
					return "<a href='javascript:'onclick='origFrameQryShow(\""+rec.get('consNo')+"\",\""+ rec.get('consName')+"\",\""+ rec.get('terminalAddr')+"\");" + "'>查看</a>"; 
			}}
		 ]);
	 var ds_paramDevote = new Ext.data.Store({
	 	 remoteSort : true,
	 	 proxy : new Ext.data.MemoryProxy(),
		 reader : new Ext.data.ArrayReader({
			 idIndex:4
		 }, [
		    {name: 'orgName'},
		    {name: 'consNo'},
		    {name: 'consName'},
		    {name: 'terminalAddr'},
		    {name: 'tmnlAssetNo'},
		    {name: 'autoPaulPower'},
		    {name: 'securityValue'},
		    {name: 'message'}
		   ])	
	});
	var grid_paramDevote =new Ext.grid.GridPanel({
		border : false,
		store: ds_paramDevote ,
		cm: cm_paramDevote ,
		stripeRows: true,
		/*tbar:[ {xtype: 'tbfill'},{
		                xtype:'button',
		                text:"删除选中用户",
		                iconCls: 'minus',
                        handler:function(){
	                        var recs = sm_paramDevote .getSelections();
						    for (var i = 0; i < recs.length; i++) {
							   ds_paramDevote.remove(recs[i]);
	                        }
                        }
	             }],*/
	    bbar : new Ext.ux.MyToolbar( {
					store : ds_paramDevote, 
					enableExpAll : true, // excel导出全部数据
				    expAllText : "全部",
					enableExpPage: true, // excel仅导出当前页
					expPageText : "当前页"
			})           
	});
	var tab_tmnlPanlPower = new Ext.TabPanel({
	   region:'center',
	   activeTab: 0,
	   border : false,
	   items:[{
	      title:'方案执行列表',
	      id:'grid_protectSchemeExec',
	      layout:'fit',
	      items:[grid_bd]
	      
	   },{
	      title:'参数召测列表',
	      layout:'fit',
	      items:[grid_paramDevote]
	   
	   }]
	});
	
	//监听左边树点击事件
	var treeListener = new LeftTreeListener({ 
	  modelName : '终端保电',
	  processClick : function(p, node, e){
	  	 if("grid_protectSchemeExec"==tab_tmnlPanlPower.getActiveTab().getId()){
			  var obj = node.attributes.attributes;
			  var type = node.attributes.type;
			  if(type == 'usr'){
			  	userStore.baseParams = {tmnlAssetNo:obj.tmnlAssetNo,nodeType:type};
			  	Ext.getBody().mask("正在获取数据...");
			    userStore.load({
									callback : function(recs, options, success) {
//										if (success)
//											grid_bd.getSelectionModel()
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
//											grid_bd.getSelectionModel()
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
//											grid_bd.getSelectionModel()
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
//											grid_bd.getSelectionModel()
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
//											grid_bd.getSelectionModel()
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
	   	 if("grid_protectSchemeExec"==tab_tmnlPanlPower.getActiveTab().getId()){
	          userStore.baseParams = {tmnlAssetNo:record.get('tmnlAssetNo'),nodeType:'usr'};
	          Ext.getBody().mask("正在获取数据...");
			  userStore.load({
								callback : function(recs, options, success) {
//									if (success)
//											grid_bd.getSelectionModel()
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
		ds_bd.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(thisstore));
		ds_bd.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
		sm_bd.selectAll();
		grid_bd.doLayout();
	});
	
	ds_bd.on('load',function(){
		if(gsselectAllcb.checked){
			gsselectAllcb.setValue(false);
		}
	});
	
   	var zdbdcs=new Ext.form.Checkbox({
		id:'zdbdcs',
		boxLabel:'自动保电(小时)',
		width:115	
	});
   var bd_value=new Ext.form.NumberField({
   	     disabled:true,
   	     allowNegative:false,
   	     allowDecimals:false,
		 id:'bd_value',
	  	 labelSeparator:'',
         width:100	
    });
	zdbdcs.on('check',function(zdbdcs,checked){
	     if(checked==true)
	     {
	        bd_value.enable();
	     }
	     else if(checked==false)
	     {
	     	bd_value.setValue("");
	        bd_value.disable();
	     }
	});
	
	  var badz=new Ext.form.Checkbox({
	     boxLabel:'保安定值(kW)',
	     width:95
	  });
	  
	  var ba_value=new Ext.form.NumberField({
   	     disabled:true,
   	     allowNegative:false,
   	     decimalPrecision:3,
		 id:'ba_value',
	  	 labelSeparator:'',
         width:100
     });
     
     badz.on('check',function(badz,checked){
	     if(checked==true)
	     {
	        ba_value.enable();
	     }
	     else if(checked==false)
	     {
	     	ba_value.setValue('');
	        ba_value.disable();
	     }
	});

    var panel_bd=new Ext.Panel({
    	title:'保电参数',
    	anchor:'100% 50%',
    	border : false,
    	layout:'table',
    	layoutConfig : {
			columns :6
	    },
	    defaults: {height: 35},
	    items:[{
		    border : false,
		    width:122,
	  		layout:'form',
	 	    hideLabels:true,
	 	    bodyStyle : 'padding:10px 0px 0px 20px',
	 	    items:[zdbdcs]
   		},{
	        border : false,
	        width:178,
	  		layout:'form',
	  		hideLabels:true,
	  		bodyStyle : 'padding:10px 0px 0px 5px',
			items : [bd_value]
		},{
	   	    border : false,
	  		layout:'form',
	  		width:96,
	 	    hideLabels:true,
	 	    bodyStyle : 'padding:10px 0px 0px 0px',
	 	    items:[badz]
		 },{
	   	    border : false,
	  		layout:'form',
	  		width:179,
	  		hideLabels:true,
	  		bodyStyle : 'padding:10px 0px 0px 5px',
			items : [ba_value]
	     },{
	    	width:100,
	    	border : false,
			layout:'form',
			bodyStyle : 'padding:10px 0px 0px 0px',
			items : [{
			     xtype:'button',
			     text:'参数下发'	,
			     width:80,
			     handler:function(){
			      	tab_tmnlPanlPower.setActiveTab(0);
			 		var recs = sm_bd.getSelections();
			 		if(gsselectAllcb.checked ){
						if(0==userStore.getCount()){
							 Ext.MessageBox.alert("提示","请选择用户！");
				     	     return;
						}
			 		}
			 		else{
				 		if(null==recs||0==recs.length){
				 	    	 Ext.MessageBox.alert("提示","请选择用户！");
				 	    	 return;
				 		}
			 		}
			 	    if(zdbdcs.checked==false&&badz.checked==false)
			     	{
					      Ext.MessageBox.alert("提示","请选择并输入下发参数！");
				          return; 
			     	}
			     	if(zdbdcs.checked==true&&bd_value.getValue()==""&&bd_value.getValue()!="0")
			     	{
			 	          Ext.MessageBox.alert("提示","请输入自动保电参数！");
			 	          return;
			     	}
			     	if(badz.checked==true&&ba_value.getValue()==""&&ba_value.getValue()!="0")
			     	{
			 	          Ext.MessageBox.alert("提示","请输入保安定值！"); 
			 	          return;
			     	}
			     	ctrlCheckStaff(paramSend, '');
			     }
				}]
	    	 },{
	    		width:100,
		    	border : false,
				layout:'form',
				bodyStyle : 'padding:10px 0px 0px 0px',
				items : [{
				     xtype:'button',
				     text:'参数召测',
				     width:80,
				     handler:function(){
				       tab_tmnlPanlPower.setActiveTab(0);
					    var recs = sm_bd.getSelections();
				        if(gsselectAllcb.checked){
							if(0==userStore.getCount()){
								 Ext.MessageBox.alert("提示","请选择用户！");
					     	     return;
							}
				 		}
				 		else{
					 		if(null==recs||0==recs.length){
					 	    	 Ext.MessageBox.alert("提示","请选择用户！");
					 	    	 return;
					 		}
				 		}
				 		if(zdbdcs.checked==false&&badz.checked==false) {
				           Ext.MessageBox.alert("提示","请选择需要召测的参数！");
				           return; 
				        }
				 		ctrlCheckStaff(paramFetch, '');
				     }
				}]
	    	 }]
    });
    
    function paramSend(){
 		var recs = sm_bd.getSelections();
     	var protectTmnlArray = new Array();
     	if(gsselectAllcb.checked){
	 		for (var i = 0; i < userStore.getCount(); i++){
	 			if("0"==userStore.getAt(i).get('protectStatus')||"2"==userStore.getAt(i).get('protectStatus')){
	 				var tmnl =userStore.getAt(i).get('tmnlAssetNo')+'`'+userStore.getAt(i).get('protocolCode');
	     			protectTmnlArray[i]=tmnl;
	     			userStore.getAt(i).set('execStatus',"0");
	 			}
	 			else{
	 				userStore.getAt(i).set('execStatus',"-1");
	 			}
	     	}
     	}
     	else{
     		for(var i = 0; i < recs.length; i++){
     			var rec = userStore.getById(recs[i].get('tmnlAssetNo'));
	     		if("0"==recs[i].get('protectStatus')||"2"==recs[i].get('protectStatus')){
		     		var tmnl = recs[i].get('tmnlAssetNo')+'`'+recs[i].get('protocolCode');
		     		protectTmnlArray[i]=tmnl;
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
     	if(0==protectTmnlArray.length){
       		ds_bd.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
			ds_bd.load({
						params : {
							start : ds_bd.lastOptions.params.start,
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
     		url:'./baseapp/terminalprotect!sendout.action',
     		params : {
                protectTmnlList:protectTmnlArray, 
     			autoPaulPower:bd_value.getValue(),
     			securityValue:ba_value.getValue(),
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
	 			//如果查询有结果，则执行如下流程
	            if(null!=result.tmnlExecStatusList && result.tmnlExecStatusList.length>0){
	            	//只要返回有结果，就需要处理，把后台store更新
		     		 for(var j=0;j<result.tmnlExecStatusList.length;j=j+1){
	     				var rec = userStore.getById(result.tmnlExecStatusList[j].keyId);	
	                    if(null!=rec){
		     				 if("1"==result.tmnlExecStatusList[j].execFlag){
		                		 rec.set('protectStatus',"2");
	 				         	 rec.set('execStatus',result.tmnlExecStatusList[j].execFlag);//修改页面选中记录的状态
		     				 }
		     		    }
		             }
		             userStore.commitChanges();//去掉红色标记
 		     	 }
		         //重新load
		         ds_bd.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
				 ds_bd.load({
							params : {
								start : ds_bd.lastOptions.params.start,
								limit : DEFAULT_PAGE_SIZE
							}
				 });
 		         overFlat = true;
 		         Ext.Ajax.timeout=30000;
     		}
     	 });
    };

	 function paramFetch(){
	   ds_paramDevote.loadData('');
	   var recs = sm_bd.getSelections();
	   var protectTmnlArray = new Array();
	   if (gsselectAllcb.checked){
     		for (var i = 0; i < userStore.getCount(); i++){
     			protectTmnlArray[i]=userStore.getAt(i).get('tmnlAssetNo');
     		}
 	   }
 	   else{
	       for(var i = 0; i < recs.length; i++){
	     		protectTmnlArray[i]=recs[i].get('tmnlAssetNo');
	     	}
 	   }
       var isprotect='0';
       var isfixed='0';
       if(zdbdcs.checked==true)
       	   isprotect='1';
       else if(zdbdcs.checked==false)
           isprotect='0';
       if(badz.checked==true)
         	isfixed='1';
       else if(badz.checked==false)
         	isfixed='0';  
       var taskSecond=Ext.Ajax.timeout/1000; 
 	   var ov = true;
       h_taskTime(taskSecond,function(){
       		ov = false;	
       });  
       Ext.Ajax.request({
     		url:'./baseapp/terminalprotect!fetch.action',
     		params : {
     			protectTmnlList:protectTmnlArray,
     			isProtect:isprotect,
     			isFixed:isfixed,
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
			       	 for(var i = 0; i < userStore.getCount(); i=i+1){
			    	   record[i]=new Array();
			    	   record[i][0]= userStore.getAt(i).get('orgName');
			    	   record[i][1]= userStore.getAt(i).get('consNo');
			    	   record[i][2]= userStore.getAt(i).get('consName');
			    	   record[i][3]= userStore.getAt(i).get('terminalAddr');
			    	   record[i][4]= userStore.getAt(i).get('tmnlAssetNo');
		        	 }
		        }
		        else{
			       	for(var i = 0; i < recs.length; i=i+1){
			    	   record[i]=new Array();
			    	   record[i][0]= recs[i].get('orgName');
			    	   record[i][1]= recs[i].get('consNo');
			    	   record[i][2]= recs[i].get('consName');
			    	   record[i][3]= recs[i].get('terminalAddr');
			    	   record[i][4]= recs[i].get('tmnlAssetNo');
			       }
		        }
     			if(null!=result.fetchResultList && result.fetchResultList.length>0){
     				for(var j = 0; j < result.fetchResultList.length; j++){
     					for(var k= 0;k< record.length;k++){
     						if(record[k][4]==result.fetchResultList[j].tmnlAssetNo){
     						   record[k][5]= result.fetchResultList[j].autoPaulPower;
     						   record[k][6]= result.fetchResultList[j].securityValue;
     						   break;
     						}
     					}	
     				}	
     			}
     		    ds_paramDevote.proxy = new Ext.ux.data.PagingMemoryProxy(record);
			    ds_paramDevote.load({
							params : {
								start : 0,
								limit : DEFAULT_PAGE_SIZE
							}
				});
				if (gsselectAllcb.checked){
			    	 gsselectAllcb.setValue(false);
					 sm_bd.selectAll();
			    }
     			tab_tmnlPanlPower.setActiveTab(1);
     			overFlat = true;
			    Ext.Ajax.timeout=30000;
			   
	     	}
     	});
	};
    
	var zdbdsc=new Ext.ux.form.SpinnerField({
		fieldLabel:'终端保电投入时长(小时)',
	    id: 'zdbdsc',
    	labelSeparator:'',
    	minValue: 0,
    	maxValue: 24,
    	allowDecimals: true,
    	decimalPrecision: 1,
    	incrementValue: 0.5,
    	accelerate: true,
    	width:60
	});
    
	var panel_bdzx=new Ext.Panel({
	  title:'保电执行',
	  anchor:'100% 50%',
  	  layout:'table',
  	  layoutConfig : {
			columns :5
	   },
	  defaults: {height: 35},
  	  items:[{
  		  	    border : false,
  		  	    width:375,
  		  		layout:'form',
  		  		labelAlign: 'right',
  		  		labelWidth:130,
  		  		bodyStyle : 'padding:10px 0px 0px 20px',
  				items : [zdbdsc]
  			 },{
  			     border : false,
  			     width:100,
  			  	 layout:'form',
  			  	 bodyStyle : 'padding:10px 0px 0px 0px',
  				 items : [{
  				     xtype:'button',
  		  		     text:'方案加载'	,
  		  		     width:80,
  		  		     handler:schemeLoadShow
  				 }]
  			 },{
  		        border : false,
  		  	    layout:'form',
  		  	    width:100,
  		  	    bodyStyle : 'padding:10px 0px 0px 0px',
  			    items : [{
  				     xtype:'button',
  		  		     text:'保存为方案',
  		  		     width:80,
  		  		     handler:saveSchemeShow
  			   }]
  			 },{
  		         border : false,
  		  	     layout:'form',
  		  	     width:100,
  		  		 bodyStyle : 'padding:10px 0px 0px 0px',
  				 items : [{
  				     xtype:'button',
  		  		     text:'保电投入',
  		  		     width:80,
  		  		     handler:function(){
  		  		     tab_tmnlPanlPower.setActiveTab(0);
  				       var recs = sm_bd.getSelections();
  				       if(gsselectAllcb.checked ){
  							if(0==userStore.getCount()){
  								 Ext.MessageBox.alert("提示","请选择用户！");
  					     	     return;
  							}
  				 		}
  				 		else{
  					 		if(null==recs||0==recs.length){
  					 	    	 Ext.MessageBox.alert("提示","请选择用户！");
  					 	    	 return;
  					 		}
  				 	   }
  				 	   if(Ext.getCmp('zdbdsc').getValue()==""&&Ext.getCmp('zdbdsc').getValue()!="0")
  				 	   {
  				 	   	   Ext.MessageBox.alert("提示","请输入终端保电投入时长！");
  				 		   return;
  				 	   }
  				 	   if(0>Ext.getCmp('zdbdsc').getValue()||24<Ext.getCmp('zdbdsc').getValue())
  				 	   {
  				 	   	   Ext.MessageBox.alert("提示","终端保电投入时长范围必须在0~24之间！");
  				 		   return;
  				 	   }
  				 	   ctrlCheckStaff(paulDevote, 'ctrl');
  		  		     }
  				 }]
  			 
  			 },{
  		         border : false,
  		  		 layout:'form',
  		  		 width:100,
  		  		 bodyStyle : 'padding:10px 0px 0px 0px',
  				 items : [{
  				     xtype:'button',
  		  		     text:'保电解除'	,
  		  		     width:80,
  		  		     handler:function(){
  		  		     	tab_tmnlPanlPower.setActiveTab(0);
  					    var recs = sm_bd.getSelections();
  					    if(gsselectAllcb.checked ){
  							if(0==userStore.getCount()){
  								 Ext.MessageBox.alert("提示","请选择用户！");
  					     	     return;
  							}
  				 		}
  				 		else{
  					 		if(null==recs||0==recs.length){
  					 	    	 Ext.MessageBox.alert("提示","请选择用户！");
  					 	    	 return;
  					 		}
  	 					}
  	 					 ctrlCheckStaff(paulRelease, 'ctrl');
  		  		     }
  				}]
  			 }]
	});
 
    //------------------------------------------------方案加载窗口start
    function  schemeLoadShow(){	
		var zpanel1=new Ext.Panel({
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
			   	    text:'保电方案加载'
			   	 }] 
			   	},{
			   	 columnWidth:.25,
			   	 layout:'form',
			   	 border : false,
			   	 bodyStyle : 'padding:20px 0px 0px 0px',
			   	 items:[{
			   	    xtype:'button',
			   	    text:'选择',
			   	    handler:schemeLoad
			   	 }] 
			   	}]
		});
	
		function schemeLoad(){
	   	      var selectionsArray = loadSchemeFuncId.view.getSelectedIndexes();
	      	  var schemeId;
	      	  var type = "ctrlScheme";
	      	  if(0 >= selectionsArray.length){
	      		   Ext.MessageBox.alert("提示","请选择方案！");
	               return;
	      	  }
	      	  if(1 < selectionsArray.length){
   	           	   	Ext.MessageBox.alert("提示","只能选择一个方案！");
    				return;
       	       }
       	      w_schemeLoad.hide();
	          schemeId = ds_Scheme.getAt(selectionsArray[0]).get('ctrlSchemeId');
	          userStore.baseParams = {
	       	   	   schemeId:schemeId,
	       	   	   nodeType:type
	          };
	          Ext.getBody().mask("加载中...");
	          userStore.load({
	            	callback: function(records, options, success){  
	            		Ext.getBody().unmask();
	                    if(null != records && 0 < records.length){
	                    	if(records[0].get("autoPaulPower")!=null){
	                    		zdbdcs.setValue(true);
	                    	    bd_value.setValue(records[0].get("autoPaulPower"));
	                    	}else{
	                    		zdbdcs.setValue(false);
	                    	}
	                    	if(records[0].get("securityValue")!=null){
	                    	    badz.setValue(true);
	                    		ba_value.setValue(records[0].get("securityValue"));
	                    	}else{
	                    		badz.setValue(false);
	                    	}
	                    	if(records[0].get("duration")!=null){
	                    	    Ext.getCmp('zdbdsc').setValue((records[0].get("duration")+0.0)/2);
	                    	}else{
	                    		Ext.getCmp('zdbdsc').setValue("");
	                    	}
	                    }else{
	                    	zdbdcs.setValue(false);
	                    	badz.setValue(false);
	                   		Ext.getCmp('zdbdsc').setValue("");
	                    }
						w_schemeLoad.close();	  
	                }  
	       	   });
			};
		    var ds_Scheme = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
						url:'./baseapp/terminalprotect!loadScheme.action'
					}),
				reader : new Ext.data.JsonReader({
						root : 'schemeList'
					}, [{name:'ctrlSchemeId'},
						{name:'ctrlSchemeName'},
						{name:'ctrlSchemeType'}])
			});
		    var loadSchemeFuncId = new Ext.ux.form.MultiSelect({
		            height:120,
		            width: 220,
		            displayField : 'ctrlSchemeName',
		            valueField : 'ctrlSchemeId',
		            labelSeparator:'',
		            store: ds_Scheme
		    });
		    var zpanel2=new Ext.Panel({
			   border : false,
			   layout:'column',
			   region:'center',
			   items:[
			   	{
			   	   columnWidth:.75,
			        border : false,
			        layout:'fit',
			        hideLabels:true,
			        bodyStyle : 'padding:0px 0px 0px 20px',
			        items:[loadSchemeFuncId] 
			   	},{
			   	 columnWidth:.25,
			   	 border : false,
			   	 layout:'form',
			   	 items:[{
			   	    xtype:'button',
			   	    width:80,
			   	    text:'退出',
		            handler:function(){
		                w_schemeLoad.close();
		            }
			   	 }] 
			   	}]
		    });
		    //保电方案加载窗口
			var w_schemeLoad = new Ext.Window({
		       name:'w_schemeLoad',
		       modal:true,
		       height:250,
		       width:400,
			   resizable:false,
			   layout:'border',
		       title:'方案加载',
			   items:[zpanel1,zpanel2]
		    }); 
		    ds_Scheme.load();
	       tab_tmnlPanlPower.setActiveTab(0);
			w_schemeLoad.show();	
	};
	//------------------------------------------------方案加载窗口end
	
	//弹出保存为方案窗口
	function saveSchemeShow(){
		tab_tmnlPanlPower.setActiveTab(0);
		var recs = sm_bd.getSelections();
 	    if(gsselectAllcb.checked ){
			if(0==userStore.getCount()){
				 Ext.MessageBox.alert("提示","请选择用户！");
	     	     return;
			}
 		}
 		else{
	 		if(null==recs||0==recs.length){
	 	    	 Ext.MessageBox.alert("提示","请选择用户！");
	 	    	 return;
	 		}
 		}
 	    if(zdbdcs.checked==false&&badz.checked==false){
			Ext.MessageBox.alert("提示","请输入需要保存的参数！"); 
	        return false;
		}
		if(zdbdcs.checked==true&&bd_value.getValue()==""&&bd_value.getValue()!="0")
	    {
	         Ext.MessageBox.alert("提示","请输入自动保电参数！");
	         return;
	    }
	    if(badz.checked==true&&ba_value.getValue()==""&&ba_value.getValue()!="0")
	    {
 	          Ext.MessageBox.alert("提示","请输入保安定值！"); 
 	          return;
	    }
		if(bd_value.getValue()!=""&&(bd_value.getValue()<0||bd_value.getValue()>999999)){
			  Ext.MessageBox.alert("提示","自动保电参数范围必须在0-999999之间！");
  	          return;
		}
		if(ba_value.getValue()!=""&&(ba_value.getValue()<0||ba_value.getValue()>999999)){
			  Ext.MessageBox.alert("提示","保安定值范围必须在0-999999之间！");
	  	      return;
		}
		var protectTmnlArray = new Array();
		if (gsselectAllcb.checked){
			for (var i = 0; i < userStore.getCount(); i++) {
				var tmnl = userStore.getAt(i).get('orgNo')+'`'+userStore.getAt(i).get('consNo')+'`'+userStore.getAt(i).get('tmnlAssetNo');
				protectTmnlArray[i]=tmnl;
			}
		}
		else{
			for(var i = 0; i < recs.length; i++){
	     		var tmnl = recs[i].get('orgNo')+'`'+recs[i].get('consNo')+'`'+recs[i].get('tmnlAssetNo');
	     		protectTmnlArray[i]=tmnl;
  			}				
		}
	//---------------------------------------------------保存为方案弹出窗口start
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
			    		protectSaveSchemePnl.layout.setActiveItem(0);
			    	}
			    }
		    }), new Ext.form.Radio({
			    boxLabel : '保存方案',
			    name : 'sc-radioGroup',
			    inputValue : '1',
			    handler:function(checkbox, checked){
			    	if(checked){
			    		ds_Scheme1.load();
			    		protectSaveSchemePnl.layout.setActiveItem(1);
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
			width:108,
			format:'Y-m-d',
		    value : new Date(),
		    labelSeparator:'',
		    allowBlank:false,
		    blankText : '请选择开始日期'
	    });
	
	    //结束日期
	    var saveSchemeEndDate = new Ext.form.DateField({
			fieldLabel : '到',
			name : 'saveSchemeEndDate',
			width:108,
			format:'Y-m-d',
		    value : new Date().add(Date.DAY,30),
		    labelSeparator:'',
		    allowBlank:false,
		    blankText : '请选择结束日期'
	    });
		
		
		//方案label
		var saveSchemeLabel = new Ext.form.TextField({
	        fieldLabel:'方案名称',
   	        value:'保电方案',
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
		//定义另存为方案面板组件
		var protectSaveScheme=new Ext.Panel({
	        border : false,
	        layout:'form',
	        buttonAlign:'center',
	        buttons:[{
	     	    text:'确定',
	     	    handler:saveScheme
	   	    },{
	     	    text:'退出',
	     	    handler:function(){
	     	        w_saveScheme.close();	
	     	    }
	   	    }],
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
	   	    }, {
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
	   	   		}]
	    });
	    //另存为方案函数
	    function saveScheme(){
 			if(!saveSchemecheckInitData()){
               return;
            }
            w_saveScheme.hide();
            Ext.getBody().mask("保存中...");
 	    	Ext.Ajax.request({
	     		url:'./baseapp/terminalprotect!saveProtectScheme.action',
	     		params : {
 	    		   ctrlSchemeName:saveSchemeLabel.getValue()+'-'+saveSchemeName.getValue().trim(),
	     	       ctrlDateStart:saveSchemeStartDate.getValue(),
	     	       ctrlDateEnd:saveSchemeEndDate.getValue(),
				   protectTmnlList:protectTmnlArray,
	     		   autoPaulPower:bd_value.getValue(),
	     		   securityValue:ba_value.getValue(),
	     		   duration:Ext.getCmp('zdbdsc').getValue(),
	     		   schemeRemark:saveSchemeRemark.getValue().trim()
	     		},
	     		success : function(response){
     	    		var result = Ext.decode(response.responseText);
     	    		if(null!=result.FLAG){
                        if(result.FLAG==0){
                            Ext.MessageBox.alert("提示","该方案名已被使用！",function(btn){
                                	w_saveScheme.show();
                            });
                            return;
                        }
                     	else if(result.FLAG==1){
                     	    if(gsselectAllcb.checked) {
								gsselectAllcb.setValue(false);
								sm_bd.selectAll();
							}
                            Ext.MessageBox.alert("提示","保存成功！");
                        }
     	    		}
     	    		w_saveScheme.close();
            	},
            	callback:function(){
            		Ext.getBody().unmask();
            	}
	     	});	
	    };
	    function saveSchemecheckInitData(){
			 if("" == saveSchemeName.getValue().trim() || 0 >=  saveSchemeName.getValue().trim().length){
		            Ext.MessageBox.alert("提示","请输入方案名！"); 
		            return false;
	         }
	         if("" == saveSchemeStartDate.getValue()){
		            Ext.MessageBox.alert("提示","请输入开始日期！"); 
		            return false;
	         }
	         if("" == saveSchemeEndDate.getValue()){
		            Ext.MessageBox.alert("提示","请输入结束时间！"); 
		            return false;
	         }
	         if(saveSchemeStartDate.getValue() > saveSchemeEndDate.getValue()){
		             Ext.MessageBox.alert("提示","开始日期应早于结束日期！"); 
			         return false;
	         }
	         return true;	
        };
	     
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
					url:'./baseapp/terminalprotect!loadScheme.action'
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
		
		//终端保电方案下拉框
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
			forceSelection : true,
			selectOnFocus : true,
			allowBlank:false,
		    blankText : '请选择方案',
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
					
	    //定义保存为方案面板组件
		var protectUpdateScheme=new Ext.Panel({
	        border : false,
	        layout:'form',
	        buttonAlign:'center',
	        buttons:[{
	     	    text:'确定',
	     	    handler:updateScheme
	   	    },{
	     	    text:'退出',
	     	    handler:function(){
	     	        w_saveScheme.close();	
	     	    }
	   	    }],
	        items:[{
	   	            layout:'form',
	   	            border : false,
	   	            bodyStyle : 'padding:10px 0px 0px 15px',
	   	            labelAlign: "right",
	   	            labelWidth:70,
	   	            items:[updateSchemeName] 
		   	   }, {
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
	   	   		}]
		 });
	    //保存方案函数
	    function updateScheme(){
	    	if(!updateSchemecheckInitData()){
               return;
            }
            w_saveScheme.hide();
            Ext.getBody().mask("保存中...");
            Ext.Ajax.request({
	            url:'./baseapp/terminalprotect!updateProtectScheme.action',
		     		params : {
	 	    		   schemeId:updateSchemeName.getValue(),
		     	       ctrlDateStart:updateSchemeStartDate.getValue(),
		     	       ctrlDateEnd:updateSchemeEndDate.getValue(),
					   protectTmnlList:protectTmnlArray,
		     		   autoPaulPower:bd_value.getValue(),
		     		   securityValue:ba_value.getValue(),
		     		   duration:Ext.getCmp('zdbdsc').getValue(),
		     		   schemeRemark:updateSchemeRemark.getValue().trim()
		     		},
		     		success : function(response){
		     			 w_saveScheme.close();
	     	    		 if(gsselectAllcb.checked) {
							gsselectAllcb.setValue(false);
							sm_bd.selectAll();
						 }
						 Ext.MessageBox.alert("提示","保存成功！");
	                     return;         
	            	},
	            	callback:function(){
	            		Ext.getBody().unmask();
	            	}
            });
            
	     }
         function updateSchemecheckInitData(){
			 if("" == updateSchemeName.getValue()){
		            Ext.MessageBox.alert("提示","请选择方案！"); 
		            return false;
	         }
	         if("" == updateSchemeStartDate.getValue()){
		            Ext.MessageBox.alert("提示","请选择开始日期！"); 
		            return false;
	         }
	         if("" == updateSchemeEndDate.getValue()){
		            Ext.MessageBox.alert("提示","请选择结束日期！"); 
		            return false;
	         }
	         if(updateSchemeStartDate.getValue() > updateSchemeEndDate.getValue()){
		             Ext.MessageBox.alert("提示","开始日期应早于结束日期！"); 
			         return false;
	         }
	         return true;	
       };
	   var protectSaveSchemePnl=new Ext.Panel({
	   	    region:'center',
		   	layout:'card',
	        activeItem : 0,
	        border:false,
	        items:[protectSaveScheme, protectUpdateScheme]
	   });

	    //定义保存为方案面板组件
	   var saveSchemePnl=new Ext.Panel({
	        layout:'border',
	        border:false,
	        items:[schemeRedioPanel,protectSaveSchemePnl]
	   });
	
	    //保存方案跳出窗口
	    var w_saveScheme = new Ext.Window({
	        name:'bcfa',
	        modal:true,
	        height:260,
	        width:400,
	        resizable:false,
	        layout:'fit',	        
	        title:'保存方案',
	        items:[saveSchemePnl]
	    });
	    w_saveScheme.show();  
	};
	//---------------------------------------------------保存为方案弹出窗口end    
	
	function paulDevote(){
       var recs = sm_bd.getSelections();
       var protectTmnlArray = new Array();
       if (gsselectAllcb.checked){
     		for (var i = 0; i < userStore.getCount(); i++){
 				var tmnl =userStore.getAt(i).get('tmnlAssetNo')+'`'+userStore.getAt(i).get('protocolCode');
     			protectTmnlArray[i]=tmnl;
     			userStore.getAt(i).set('execStatus',"0");
     		}

     	}
     	else{
	       for(var i = 0; i < recs.length; i++){
	       		var rec = userStore.getById(recs[i].get('tmnlAssetNo'));
	     		var tmnl =recs[i].get('tmnlAssetNo')+'`'+recs[i].get('protocolCode');
	     		protectTmnlArray[i]=tmnl;
	     		if(null!=rec)
	     		    rec.set('execStatus',"0");
	       }
        }
 	    userStore.commitChanges();
        if(0==protectTmnlArray.length){
       		ds_bd.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
			ds_bd.load({
						params : {
							start : ds_bd.lastOptions.params.start,
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
	 	   url:'./baseapp/terminalprotect!devote.action',
	 	   params : {
                 protectTmnlList:protectTmnlArray,
	 			 duration:Ext.getCmp('zdbdsc').getValue(),
	 			 taskSecond:taskSecond
	 		},
	 	   success : function(response){
	 	   	 if (!ov) {
					return;
			 }
	 		 var result = Ext.decode(response.responseText);
	 		 if(null!=result.cacheAndTmnlStatus&&""!=result.cacheAndTmnlStatus){
	 				Ext.MessageBox.alert("提示",result.cacheAndTmnlStatus);
	 				overFlat = true;
	 				return;
 			 }
             if(null!=result.tmnlExecStatusList && result.tmnlExecStatusList.length>0){
             	for(var j=0;j<result.tmnlExecStatusList.length;j=j+1){
     				var rec = userStore.getById(result.tmnlExecStatusList[j].keyId);	
                    if(null!=rec){
	     				 if("1"==result.tmnlExecStatusList[j].execFlag){
	                		rec.set('protectStatus',"1");
	 				     	rec.set('execStatus',"1");//修改页面选中记录的状态
                		 }
	     		    }
	             }
	             userStore.commitChanges();
             }
	         ds_bd.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
			 ds_bd.load({
						params : {
							start : ds_bd.lastOptions.params.start,
							limit : DEFAULT_PAGE_SIZE
						}
			 });
		     overFlat = true;
		     Ext.Ajax.timeout=30000;
           }
       });             
    };
    
    function paulRelease(){
	    var recs = sm_bd.getSelections();
      	var protectTmnlArray = new Array();
      	if (gsselectAllcb.checked){
     		for (var i = 0; i < userStore.getCount(); i++){
 				var tmnl =userStore.getAt(i).get('tmnlAssetNo')+'`'+userStore.getAt(i).get('protocolCode');
     			protectTmnlArray[i]=tmnl;
     			userStore.getAt(i).set('execStatus',"0");
     		}
     	}
     	else{
	        for(var i = 0; i < recs.length; i++){
	  			var rec = userStore.getById(recs[i].get('tmnlAssetNo'));	
	     		var tmnl = recs[i].get('tmnlAssetNo')+'`'+recs[i].get('protocolCode');
	     		protectTmnlArray[i]=tmnl;
	     		if(null!=rec)
	     			rec.set('execStatus',"0");
	        }
     	}
     	userStore.commitChanges();
        if(0==protectTmnlArray.length){
       		ds_bd.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
			ds_bd.load({
						params : {
							start : ds_bd.lastOptions.params.start,
							limit : DEFAULT_PAGE_SIZE
						}
			}); 
	       	return;
        }
        var taskSecond=Ext.Ajax.timeout/1000;;
        var ov = true;
        h_taskTime(taskSecond,function(){
        	ov = false;
        });
        Ext.Ajax.request({
     		url:'./baseapp/terminalprotect!release.action',
     		params : {
				 protectTmnlList:protectTmnlArray,
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
                if(null!=result.tmnlExecStatusList && result.tmnlExecStatusList.length>0){
                	for(var j=0;j<result.tmnlExecStatusList.length;j=j+1){
	     				var rec = userStore.getById(result.tmnlExecStatusList[j].keyId);	
	                    if(null!=rec){
		     				 if("1"==result.tmnlExecStatusList[j].execFlag){
		                		rec.set('protectStatus',"0");
		 				     	rec.set('execStatus',"1");//修改页面选中记录的状态
	                    	}
		     		    }
		            }
	            	userStore.commitChanges();               
     			}
     			ds_bd.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
			    ds_bd.load({
								params : {
									start : ds_bd.lastOptions.params.start,
									limit : DEFAULT_PAGE_SIZE
								}
				});
		        overFlat = true;
		        Ext.Ajax.timeout=30000;
     		}
     	}); 
    };	
	
	var panel1= new Ext.Panel({ 
	   region:'north',
	   height:135,
	   layout:'anchor',
	   border : false,
	   items:[panel_bd,panel_bdzx]
	   
	});
	
	var viewPanel = new Ext.Panel({
		layout: 'border',
		items: [panel1,tab_tmnlPanlPower],
		border : false
	});
	renderModel(viewPanel, '终端保电');
 
	
});