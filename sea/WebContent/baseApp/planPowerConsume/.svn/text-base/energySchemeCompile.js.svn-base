/**
 * 电量定值控方案编制
 * 
 * @author 姜海辉
 */


//页面执行函数
Ext.onReady(function(){
     var schemeNameFlag;//方案名标志
     var shceme_Name;//方案名
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
		    {header : '供电单位', sortable: true, dataIndex : 'orgName', align : 'center',width:120,
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
		    {header : '用户名称', sortable: true, dataIndex : 'consName', align : 'center',width:120,
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
		    {header : '总加组号', sortable: true, dataIndex : 'totalNo', align : 'center',width:70}, 
		    {header : '保电', sortable: true, dataIndex : 'protect', align : 'center',width:40,
		    	  renderer:function(value){
		    	     if("1"==value){
		    	    	 return "是";
		    	     }else if("0"==value){
		    	    	 return "否";
		    	     }else{
		    	    	 return "";}}},
		    {header : '控制状态', sortable: true, dataIndex : 'ctrlStatus', align : 'center',width:80,
		    	  renderer:function(value){
		    	    	 if("0"==value){
			    	    	 return "解除";
			    	     }else if("1"==value){
			    	    	 return "投入";
			    	     }else{
			    	    	 return "";
			    	    }}},	
    	    {header : '月电量定值(kWh)', sortable: true, dataIndex : 'powerConst', align : 'center',
    	    	editor:new Ext.form.NumberField({
    	    	allowNegative:false,
    			allowDecimals:false,
    			allowBlank : false,
    			minValue:1,
    			maxValue:999999
    			})}, 
    	    {header : '定值浮动系数(%)', sortable: true, dataIndex : 'floatValue', align : 'center'}
		]);
		
	//定义Grid的store
	var userStore = new Ext.data.Store({
	 	        proxy : new Ext.data.HttpProxy({
							url:'./baseapp/energyControl!loadGridData.action'
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
						   {name : 'powerConst'},
						   {name : 'floatValue'},
						   {name : 'protocolCode'},
						   {name : 'protect'}, 				      
					       {name : 'ctrlStatus'}
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
			data[i][8] = valStore.getAt(i).data.powerConst;
			data[i][9] = valStore.getAt(i).data.floatValue;
			data[i][10] = valStore.getAt(i).data.protocolCode;
			data[i][11] = valStore.getAt(i).data.protect;
			data[i][12] = valStore.getAt(i).data.ctrlStatus;
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
					   {name : 'powerConst'},
					   {name : 'floatValue'},
					   {name : 'protocolCode'},
					   {name : 'protect'}, 				      
				       {name : 'ctrlStatus'}
					   ]
				})
	});
	// grid解锁
	function unlockGrid(){
		userSm.unlock();
		userListGrid.onEnable();
		userListGrid.getBottomToolbar().enable();
	}
	// grid上锁
	function lockGrid(){
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
	//返回用户列表
    var userListGrid = new Ext.grid.EditorGridPanel({
	        store : userListStore,
	        cm : cm,
	        sm : userSm,
	        stripeRows : true,
	        autoScroll : true,
	        border: false,
	        tbar : [
	        		{xtype: 'tbfill'},
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
						if (gsselectAllcb.checked) {
							for (var i = 0; i < userStore.getCount(); i++) {
								var tmnl = userStore.getAt(i).get('consNo')
										+ '`'
										+ userStore.getAt(i).get('tmnlAssetNo');
								groupTmnlArray[i] = tmnl;
							}
						} else {
							var recs = userSm.getSelections();
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
							userSm.selectAll();
						}
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
	userListGrid.on('afteredit',function(e){
		var rec = userStore.getById(e.record.get('keyId'));
		if(null!=rec){
			rec.set(e.field,e.value);
			userListStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
		}
	});	    

    //定义用户列表
    var userListGridPnl = new Ext.Panel({
	    region:'center',
	    layout:'fit',
	    border : false,
		items : [userListGrid]
	});
	//-----------------------------定义用户列表-------------------------end
	
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
		    {header : '月电量定值(kWh)', sortable: true, dataIndex : 'powerConst', align : 'center'}, 
		    {header : '定值浮动系数(%)', sortable: true, dataIndex : 'floatValue', align : 'center'}, 
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
				   {name : 'powerConst'},
				   {name : 'floatValue'},
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
//				    xtype:'button',
//				    text : '删除选中用户',
//			        iconCls: 'minus',
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
		    
   //定义返回结果列表
    var resultListGridPnl = new Ext.Panel({
	    region:'center',
	    layout:'fit',
	    border : false,
		items : [resultListGrid]
	});

	
	//-----------------------------定义参数召测列表-------------------------end
	//返回页面的上部面板
		
	//定值浮动系数
    var fixFloatRadioGroup = new Ext.form.RadioGroup({
    	id : 'ec_fixFloatScheme',
	    fieldLabel : '定值浮动系数',
	    width : 100,
	    labelSeparator:'',
	    columns : [.54,.46],
	    items : [{
		    boxLabel : '上浮',
		    name : 'fixFloat-radioGroup',
		    checked : true,
		    inputValue:'0',
		    listeners : {
			    'check' : function(r,c) {
					 if(0<userStore.getCount()){
				    	if("" == Ext.getCmp("ec_floatPercentScheme").getValue()&&"0"!= Ext.getCmp("ec_floatPercentScheme").getValue()){
					 	    return;
					    }
					    if(0 >= Ext.getCmp("ec_floatPercentScheme").getValue() || 100 <= Ext.getCmp("ec_floatPercentScheme").getValue()){
						    return;
					    }
					    var floatValue;
					    if(c)
							floatValue = Ext.getCmp('ec_floatPercentScheme').getValue();
						else
						    floatValue = 0- Ext.getCmp('ec_floatPercentScheme').getValue();
				    	for (var i = 0; i < userStore.getCount(); i++){
				    		userStore.getAt(i).set('floatValue',floatValue);
				    	}
				    	userListStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
						userListStore.load({
									params:{
										start : userListStore.lastOptions.params.start,
										limit : DEFAULT_PAGE_SIZE
									}
						});
				    }
				}
		    }
	    }, {
		    boxLabel : '下浮',
		    name : 'fixFloat-radioGroup',
		    inputValue:'1'
	    }]
    });    
    
   //月电量定值
	var monthColoumData = new Ext.form.NumberField({
		    id : 'ec_monthColoumDataScheme',
			fieldLabel : '月电量定值(kWh)',
			width:80,
			allowNegative:false,
    		allowDecimals:false,
			labelSeparator:'',
			minValue:1,
			maxValue:999999
	});
	monthColoumData.on('change',function(){
	    if(0<userStore.getCount()){
	    	if("" == Ext.getCmp("ec_monthColoumDataScheme").getValue()&&"0"!= Ext.getCmp("ec_monthColoumDataScheme").getValue()){
		 	    return;
		    }
	    	if(0 >= Ext.getCmp("ec_monthColoumDataScheme").getValue() || 999999 <= Ext.getCmp("ec_monthColoumDataScheme").getValue()){
		    	return;
		    }
	    	for (var i = 0; i < userStore.getCount(); i++){
	    		userStore.getAt(i).set('powerConst',monthColoumData.getValue());
	    	}
	    	userListStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
			userListStore.load({
						params:{
							start : userListStore.lastOptions.params.start,
							limit : DEFAULT_PAGE_SIZE
						}
			});
	    }
	});        
	//参数召测按钮
	var call = new Ext.Button({
			text : '参数召测',
			name : 'call',
			width : 80,
		    handler:function(){
		    	tb_energyCtrlScheme.setActiveTab(0);
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
  		     	ctrlCheckStaff(energyParamFetch, '');
		    }
	});
	
	function energyParamFetch(){
		resultListStore.loadData('');
		var users = userSm.getSelections();
     	var energyCtrlArray = new Array();
     	if (gsselectAllcb.checked){
     		for (var i = 0; i < userStore.getCount(); i++){
     			var tmnltotal = userStore.getAt(i).get('tmnlAssetNo')+'`'+userStore.getAt(i).get('totalNo');
     			energyCtrlArray[i]=tmnltotal;
     		}
 	    }
 	    else{
 	    	for(var i = 0; i < users.length; i++){
	     		var tmnltotal = users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo');
	     		energyCtrlArray[i]=tmnltotal;
	     	}        	    	
 	    }
        var taskSecond= Ext.Ajax.timeout/1000;
        var ov = true;
      	h_taskTime(taskSecond,function(){
       		ov = false;	
       	});
    	Ext.Ajax.request({
     		url:'./baseapp/energyControl!fetch.action',
     		params : {
     		    energyCtrlList:energyCtrlArray,
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
           	       		record[i] = new Array();
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
     			 if(null!=result.resultList && 0<result.resultList.length){
	     			for(var j = 0; j < result.resultList.length; j++){
     					for(var k=0;k < record.length;k++){
     						if(record[k][0]==result.resultList[j].keyId){
     							record[k][6]=result.resultList[j].powerConst;
     							record[k][7]=result.resultList[j].floatValue;
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
     			 tb_energyCtrlScheme.setActiveTab(1);
     		     overFlat = true;
     		     Ext.Ajax.timeout=30000;
     		}
     	});
	}
	
	//定义%标记
	var percent = new Ext.form.Label({
	    text : "%",
	    width : 10
	});
	
	//浮动系数比
	var floatPercent = new Ext.form.NumberField({
		    id : 'ec_floatPercentScheme',
		    allowNegative:false,
    		allowDecimals:false,
    		minValue:0,
    		maxValue:99,
			width:40
	});
	
	floatPercent.on('change',function(){
	    if(0<userStore.getCount()){
	    	if("" == Ext.getCmp("ec_floatPercentScheme").getValue()&&"0"!= Ext.getCmp("ec_floatPercentScheme").getValue()){
		 	    return;
		    }
		    if(0 > Ext.getCmp("ec_floatPercentScheme").getValue() || 100 <= Ext.getCmp("ec_floatPercentScheme").getValue()){
			    return;
		    }
		    if("0" == Ext.getCmp("ec_floatPercentScheme").getValue()){
		    	for (var i = 0; i < userStore.getCount(); i++){
	    			userStore.getAt(i).set('floatValue',"");
	    		}
		    }
		    else{
			    var floatValue;
			    if ("0" == Ext.getCmp('ec_fixFloatScheme').getValue().getRawValue()) {
					floatValue = Ext.getCmp('ec_floatPercentScheme').getValue();
				}else{
					floatValue = 0- Ext.getCmp('ec_floatPercentScheme').getValue();
				}
		    	for (var i = 0; i < userStore.getCount(); i++){
		    		userStore.getAt(i).set('floatValue',floatValue);
		    	}
		    }
	    	userListStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
			userListStore.load({
						params:{
							start : userListStore.lastOptions.params.start,
							limit : DEFAULT_PAGE_SIZE
						}
			});
	    }
	});  
	
	//返回页面的中间面板
		//方案加载按钮，用于弹出方案加载窗口
		var loadBtn = new Ext.Button({
				text : '方案加载',
				name : 'load',
				width : 80,
			    handler:function(){
			    	tb_energyCtrlScheme.setActiveTab(0); 
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
				   	           text:'电量定值方案加载'
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
					    url:'./baseapp/energyControl!loadScheme.action',
					    fields : ['ctrlSchemeId','ctrlSchemeName','ctrlSchemeType' ],
					    root : "schemeList"
				    });
				    loadFuncStore.load();
				    //多选框
				    var multiSel = new Ext.ux.form.MultiSelect({
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
	  		        loadScheme.show();
	  		    }
		});
		//--------------------------------方案加载----------------------------------end
		
		
/*		//保存为方案按钮，用于弹出保存方案窗口
		var saveBtn = new Ext.Button({
				text : '保存为方案',
				name : 'save',
				width :80,
			    handler:function(){
			    	//--------------------------------方案保存----------------------------------start
			    	tb_energyCtrlScheme.setActiveTab(0);
                    var recs = userSm.getSelections();
                    if(gsselectAllcb.checked ){
						if(0==userStore.getCount()){
							 Ext.MessageBox.alert("提示","请选择方案信息！");
				     	     return;
						}
			 		}
			 		else{
				 		if(null==recs||0==recs.length){
				 	    	 Ext.MessageBox.alert("提示","请选择方案信息！");
				 	    	 return;
				 		}
			 		}
			 		var energyCtrlArray = new Array();
 					if(gsselectAllcb.checked ){
		            	for(var i = 0; i < userStore.getCount(); i++){
		            		if(null==userStore.getAt(i).get('powerConst')||""==userStore.getAt(i).get('powerConst')){
		            		   Ext.MessageBox.alert("提示","存在月电量定值为空的记录！");
		            		   return;
		            		}
				     		var tmnltotal = userStore.getAt(i).get('orgNo')+'`'+userStore.getAt(i).get('consNo')+'`'+userStore.getAt(i).get('tmnlAssetNo')+'`'+userStore.getAt(i).get('totalNo')+'`'+userStore.getAt(i).get('powerConst')+'`'+userStore.getAt(i).get('floatValue');
				     		energyCtrlArray[i]=tmnltotal;
			     		}
 					}
 					else{
 						for(var i = 0; i < recs.length; i++){
 							if(null==recs[i].get('powerConst')||""==recs[i].get('powerConst')){
		            		   Ext.MessageBox.alert("提示","存在月电量定值为空的记录！");
		            		   return;
		            		}
		            		var tmnltotal = recs[i].get('orgNo')+'`'+recs[i].get('consNo')+'`'+recs[i].get('tmnlAssetNo')+'`'+recs[i].get('totalNo')+'`'+recs[i].get('powerConst')+'`'+recs[i].get('floatValue');
				     		energyCtrlArray[i]=tmnltotal;
 						}
 					}
                    //定义radio选择组
				    var ecRadioGroup = new Ext.form.RadioGroup({
					    width : 200,
					    height :20,
					    items : [new Ext.form.Radio({
						    boxLabel : '另存为方案',
						    name : 'ec-radioGroup',
						    inputValue : '0',
						    checked : true,
						    handler:function(checkbox, checked){
						    	if(checked){
						    		saveEnergySchemePnl.layout.setActiveItem(0);
						    	}
					    	}
					    }), new Ext.form.Radio({
						    boxLabel : '保存方案',
						    name : 'ec-radioGroup',
						    inputValue : '1',
						    handler:function(checkbox, checked){
						    	if(checked){
						    		ds_Scheme1.load();
						    		saveEnergySchemePnl.layout.setActiveItem(1);
						    	}
						    }
					    })]
				    });
				    
				    var schemeRedioPanel=new Ext.Panel({
				    	region:'north',
				    	layout:'form',
				        border:false,
				        height:35,
				        bodyStyle : 'padding:10px 0px 0px 40px',
			            hideLabels:true,
				        items:[ecRadioGroup]  
				    });
				    
					//开始日期
					var saveSchemeStartDate = new Ext.form.DateField({
						fieldLabel : '限电日从',
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
					
				    //限电类型Store
					var limitTypeStore = new Ext.data.JsonStore({
					    url : "./baseapp/energyControl!loadLimitType.action",
					    fields : [ 'limitType', 'limitTypeName' ],
					    root : "limitTypeList"
				    });
			
					//定义购电标志
					var saveSchemeLimitType = new Ext.form.ComboBox({
						fieldLabel : '限电类型',
						store : limitTypeStore,
						displayField : 'limitTypeName',
			            valueField : 'limitType',
						bodyStyle: 'padding:10px;',
						triggerAction : 'all',
						mode : 'remote',
						emptyText : '--请选择类型--',
						allowBlank:false,
		  				blankText : '请选择限电类型',
						selectOnFocus : true,
						editable:false,
						width:100,
						labelSeparator:''
					});
					
					//营业报停方案label
					var saveSchemeLabel = new Ext.form.TextField({
					        fieldLabel:'方案名称',
				   	        value:'月电量定值控方案',
				   	        readOnly:true,
				   	        allowBlank:false,
				   	        labelSeparator:'',
			                anchor:'98%' 
					});
					
					//方案名
					var saveSchemeName = new Ext.form.TextField({
			                width:145,
			                emptyText : '请输入方案名称',
						    allowBlank:false,
						    blankText : '请输入方案名称'
					});
				    var saveSchemeRemark =new Ext.form.TextArea({
				    	    fieldLabel:'备注',
				    		width:255,
				    		height:80
				    });
				    var saveCtrlLoad=new Ext.form.NumberField({
				    		fieldLabel:'调控负荷(kW)',
				    		width:62,
				    		allowNegative:false,
				    		decimalPrecision:3,
				    		minValue:1,
				    		maxValue:999999,
						    allowBlank:false,
						    blankText : '请输入调控负荷'
				    });
					//定义另存为方案Card组件
					var saveEnergyScuheme=new Ext.Panel({
			            height:170,
				        border : false,
				        layout:'form',
				        buttonAlign:'center',
				        buttons:[{
			         	    text:'确定',
			         	    handler:function(){
			         	    	if("" == saveSchemeName.getValue() || 0 >=  saveSchemeName.getValue().length){
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
					            if("" == saveSchemeLimitType.getValue()){
					    	        Ext.MessageBox.alert("提示","请选择限电类型！"); 
					 	            return;
					            }
					            if("" == saveCtrlLoad.getValue()&&"0" != saveCtrlLoad.getValue()){
					    	        Ext.MessageBox.alert("提示","请输入调控负荷！"); 
					 	            return;
					            }
					            if(0 >= saveCtrlLoad.getValue() || 999999 <= saveCtrlLoad.getValue()){
							        Ext.MessageBox.alert("提示","调控负荷范围必须在1-999999之间！");
							    	return;
							    }
								var floatValue;
								if(null!= Ext.getCmp('ec_floatPercentScheme').getValue()||""!=Ext.getCmp('ec_floatPercentScheme').getValue()){
									if ("0" == Ext.getCmp('ec_fixFloatScheme').getValue().getRawValue()) {
										floatValue = Ext.getCmp('ec_floatPercentScheme').getValue();
									}else{
										floatValue = 0- Ext.getCmp('ec_floatPercentScheme').getValue();
									}
     							}
     							saveScheme.hide();
                				Ext.getBody().mask("保存中...");
								Ext.Ajax.request({
									url : './baseapp/energyControl!saveEnergyScheme.action',
									params : {
										ctrlSchemeName:saveSchemeLabel.getValue()+'-'+saveSchemeName.getValue(),
										limitType : saveSchemeLimitType.getValue(),
										newStartDate : saveSchemeStartDate.getValue(),
										newEndDate : saveSchemeEndDate.getValue(),
										energyCtrlList:energyCtrlArray,
										ctrlLoad:saveCtrlLoad.getValue(),
										schemeRemark:saveSchemeRemark.getValue()
									},
									success : function(response) {	
			  		     	    		var result = Ext.decode(response.responseText);
			  		     	    		if(null!=result.FLAG){
                                            if(result.FLAG==0){
                                                Ext.MessageBox.alert("提示","该方案名已被使用！",function(btn){
                                               		saveScheme.show();
                                                	});
                                                return;
                                            }
                                         	else if(result.FLAG==1){
                                                if(gsselectAllcb.checked) {
													gsselectAllcb.setValue(false);
													userSm.selectAll();
												}
												Ext.MessageBox.alert("提示","保存成功！");
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
				   	    }],
				        items:[{
				        	layout:'column',
				   	        border : false,
				   	        items:[{
				   	            columnWidth:.52,
				   	            layout:'form',
				   	            border : false,
				   	            bodyStyle : 'padding:5px 0px 0px 15px',
				   	            labelAlign: "right",
				   	            labelWidth:70,
				   	            items:[saveSchemeLabel] 
				   	        }, {
				   	            columnWidth:.48,
				   	            layout:'form',
				   	            border : false,
				   	            bodyStyle : 'padding:5px 0px 0px 0px',
				   	            hideLabels:true,
				   	            items:[saveSchemeName] 
				   	        }]
				   	    },{
				   	        layout:'column',
				   	        border : false,
				   	        items:[{
				   	            columnWidth:.50,
				   	            layout:'form',
				   	            border : false,
				   	            bodyStyle : 'padding:5px 0px 0px 15px',
				   	            labelAlign: "right",
				   	            labelWidth:70,
				   	            items:[saveSchemeLimitType] 
				   	        }, {
				   	            columnWidth:.50,
				   	            layout:'form',
				   	            border : false,
				   	            labelWidth:85,
				   	            labelAlign: "right",
				   	            bodyStyle : 'padding:5px 0px 0px 0px',
				   	            items:[saveCtrlLoad] 
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
				   	    } ]
				    });
				    
				    //开始日期
					var updateSchemeStartDate = new Ext.form.DateField({
						fieldLabel : '限电日从',
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
			
					//定义购电标志
					var updateSchemeLimitType = new Ext.form.ComboBox({
						fieldLabel : '限电类型',
						store : limitTypeStore,
						displayField : 'limitTypeName',
			            valueField : 'limitType',
						bodyStyle: 'padding:10px;',
						triggerAction : 'all',
						mode : 'remote',
						width:100,
						emptyText : '--请选择类型--',
						allowBlank:false,
		  				blankText : '请选择限电类型',
						selectOnFocus : true,
						editable:false,
						labelSeparator:''
					});
					
					//方案名称Store
					var ds_Scheme1 = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
								url:'./baseapp/energyControl!loadScheme.action'
							}),
						reader : new Ext.data.JsonReader({
								root : 'schemeList',
								idProperty: 'ctrlSchemeId'
							}, [
								{name:'ctrlSchemeId'},
								{name:'ctrlSchemeName'},
								{name:'ctrlSchemeType'},
								{name:'ctrlDateStart'},
								{name:'ctrlDateEnd'},
								{name:'limitType'},
								{name:'ctrlLoad'},
								{name:'schemeRemark'}])
					});
					
					//方案备注
					var updateSchemeRemark =new Ext.form.TextArea({
				    	    fieldLabel:'备注',
				    		width:255,
				    		height:80
				    });
				    
				    //调控负荷
				    var updateCtrlLoad=new Ext.form.NumberField({
				    		fieldLabel:'调控负荷(kW)',
				    		width:62,
				    		allowNegative:false,
				    		decimalPrecision:3,
				    		minValue:1,
				    		maxValue:999999,
				    		allowBlank:false,
		  					blankText : '请输入调控负荷'
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
						editable:false,
						labelSeparator:''
					});
					updateSchemeName.on('select',function(){
						var rec=ds_Scheme1.getById(updateSchemeName.getValue());
							if(null!=rec){
								updateSchemeLimitType.setValue(rec.get('limitType'));
								updateCtrlLoad.setValue(rec.get('ctrlLoad'));
								updateSchemeStartDate.setValue(Date.parseDate(rec.get('ctrlDateStart'),'Y-m-d\\TH:i:s'));
								updateSchemeEndDate.setValue(Date.parseDate(rec.get('ctrlDateEnd'),'Y-m-d\\TH:i:s'));
								updateSchemeRemark.setValue(rec.get('schemeRemark'));
							}
					});
					
				    
				    //定义另存为方案Card组件
					var updateEnergyScheme=new Ext.Panel({
			            height:170,
				        border : false,
				        layout:'form',
				        buttonAlign:'center',
				        buttons:[{
			         	    text:'确定',
			         	    handler:function(){
			         	    	if("" == updateSchemeName.getValue()){
						            Ext.MessageBox.alert("提示","请选择方案！"); 
						            return;
						        }
					            if("" == updateSchemeStartDate.getValue()){
						            Ext.MessageBox.alert("提示","请输入开始日期！"); 
					 	            return;
					            }
					            if("" ==  updateSchemeEndDate.getValue()){
						            Ext.MessageBox.alert("提示","请输入结束日期！"); 
					 	            return;
					            }
					            if( updateSchemeStartDate.getValue() >  updateSchemeEndDate.getValue()){
					                Ext.MessageBox.alert("提示","开始日期应早于结束日期！"); 
					 	            return;
					            }
					            if("" == updateSchemeLimitType.getValue()){
					    	        Ext.MessageBox.alert("提示","请选择限电类型！"); 
					 	            return;
					            }
					             if("" == updateCtrlLoad.getValue()&&"0" != updateCtrlLoad.getValue()){
					    	        Ext.MessageBox.alert("提示","请输入调控负荷！"); 
					 	            return;
					            }
					            if(0 >= updateCtrlLoad.getValue() || 999999 <= updateCtrlLoad.getValue()){
							        Ext.MessageBox.alert("提示","调控负荷范围必须在1-999999之间！");
							    	return;
							    }
								var floatValue;
								if(null!= Ext.getCmp('ec_floatPercentScheme').getValue()||""!=Ext.getCmp('ec_floatPercentScheme').getValue()){
									if ("0" == Ext.getCmp('ec_fixFloatScheme').getValue().getRawValue()) {
										floatValue = Ext.getCmp('ec_floatPercentScheme').getValue();
									}else{
										floatValue = 0- Ext.getCmp('ec_floatPercentScheme').getValue();
									}
     							}
     							saveScheme.hide();
                				Ext.getBody().mask("保存中...");
								Ext.Ajax.request({
									url : './baseapp/energyControl!updateEnergyScheme.action',
									params : {
										schemeId: updateSchemeName.getValue(),
										limitType : updateSchemeLimitType.getValue(),
										newStartDate : updateSchemeStartDate.getValue(),
										newEndDate : updateSchemeEndDate.getValue(),
										energyCtrlList:energyCtrlArray,
										ctrlLoad:updateCtrlLoad.getValue(),
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
				   	    }],
				        items:[{
				        	layout:'form',
			   	            border : false,
			   	            bodyStyle : 'padding:5px 0px 0px 15px',
			   	            labelAlign: "right",
			   	            labelWidth:70,
			   	            items:[updateSchemeName] 
				   	    },{
				   	        layout:'column',
				   	        border : false,
				   	        items:[{
				   	            columnWidth:.50,
				   	            layout:'form',
				   	            border : false,
				   	            bodyStyle : 'padding:5px 0px 0px 15px',
				   	            labelAlign: "right",
				   	            labelWidth:70,
				   	            items:[updateSchemeLimitType] 
				   	        }, {
				   	            columnWidth:.45,
				   	            layout:'form',
				   	            border : false,
				   	            labelWidth:85,
				   	            labelAlign: "right",
				   	            bodyStyle : 'padding:5px 0px 0px 0px',
				   	            items:[updateCtrlLoad] 
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
				     var saveEnergySchemePnl=new Ext.Panel({
				   	    region:'center',
					   	layout:'card',
				        activeItem : 0,
				        border:false,
				        items:[saveEnergyScuheme,updateEnergyScheme]
				    });
				    //定义保存为方案面板组件
			        var saveSchemePnl=new Ext.Panel({
			            layout:'border',
			            border:false,
			            items:[schemeRedioPanel,saveEnergySchemePnl]
			        });
			    
			        //保存方案跳出窗口
				    var saveScheme = new Ext.Window({
			            name:'bcfa',
			            modal:true,
			            height:290,
			            width:400,
				        resizable:false,
				        layout:'fit',	        
				        title:'保存方案',
				        items:[saveSchemePnl]
			        }); 
			        limitTypeStore.load();
	  		        saveScheme.show();
	  		    }
		});
	//--------------------------------方案保存----------------------------------end
*/	
		
	//保存为方案按钮，用于弹出保存方案窗口
		var saveBtn = new Ext.Button({
				text : '保存为方案',
				name : 'save',
				width :80,
			    handler:function(){
			    	//--------------------------------方案保存----------------------------------start
                    var recs = userSm.getSelections();
                    if(gsselectAllcb.checked ){
						if(0==userStore.getCount()){
							 Ext.MessageBox.alert("提示","请选择方案信息！");
				     	     return;
						}
			 		}
			 		else{
				 		if(null==recs||0==recs.length){
				 	    	 Ext.MessageBox.alert("提示","请选择方案信息！");
				 	    	 return;
				 		}
			 		}
			 		var energyCtrlArray = new Array();
 					if(gsselectAllcb.checked ){
		            	for(var i = 0; i < userStore.getCount(); i++){
		            		if(null==userStore.getAt(i).get('powerConst')||""==userStore.getAt(i).get('powerConst')){
		            		   Ext.MessageBox.alert("提示","存在月电量定值为空的记录！");
		            		   return;
		            		}
				     		var tmnltotal = userStore.getAt(i).get('orgNo')+'`'+userStore.getAt(i).get('consNo')+'`'+userStore.getAt(i).get('tmnlAssetNo')+'`'+userStore.getAt(i).get('totalNo')+'`'+userStore.getAt(i).get('powerConst')+'`'+userStore.getAt(i).get('floatValue');
				     		energyCtrlArray[i]=tmnltotal;
			     		}
 					}
 					else{
 						for(var i = 0; i < recs.length; i++){
 							if(null==recs[i].get('powerConst')||""==recs[i].get('powerConst')){
		            		   Ext.MessageBox.alert("提示","存在月电量定值为空的记录！");
		            		   return;
		            		}
		            		var tmnltotal = recs[i].get('orgNo')+'`'+recs[i].get('consNo')+'`'+recs[i].get('tmnlAssetNo')+'`'+recs[i].get('totalNo')+'`'+recs[i].get('powerConst')+'`'+recs[i].get('floatValue');
				     		energyCtrlArray[i]=tmnltotal;
 						}
 					}
 					Ext.getBody().mask("保存中...");
					Ext.Ajax.request({
						url : './baseapp/energyControl!saveEnergyScheme.action',
						params : {
							schemeId:staticEnergySchemeId,
							energyCtrlList:energyCtrlArray
						},
						success : function(response) {	
                            if(gsselectAllcb.checked) {
								gsselectAllcb.setValue(false);
								userSm.selectAll();
							}
							Ext.MessageBox.alert("提示","保存成功！");
                    	},
		            	callback:function(){
		            		Ext.getBody().unmask();
		            	}
					});					    
	  		    }
		});
	//--------------------------------方案保存----------------------------------end
	
	var northPnl = new Ext.Panel({
		title:'方案名：',
        region:'north',
        height:65,
		border:false,
		layout:'table',
    	layoutConfig : {
			columns :7
	    },
	    defaults: {height: 35},
		items:[{
	        layout : 'form',
		    border : false,
		    width:230,
		    labelWidth :120,
		    labelAlign : 'right',
		    bodyStyle:'padding:10px 0px 0px 3px',
		    items : [monthColoumData]

		},{
	        layout : 'form',
		    border : false,
		    labelWidth : 80,
		    labelAlign : 'right',
		    width:190,
		    bodyStyle:'padding:10px 0px 0px 0px',
		    items : [fixFloatRadioGroup]
		},{
	    	layout : 'form',
		    border : false,
		    hideLabels:true,
		    width:40,
		    bodyStyle:'padding:10px 0px 0px 0px',
		    items : [floatPercent]
	    },{
	    	layout : 'form',
		    border : false,
		    labelAlign : 'right',
		    width:45,
		    bodyStyle:'padding:12px 0px 0px 1px',
		    style:'font-size: 10pt',
		    items : [percent]
	    },{
			layout : 'form',
		    border : false,
		    width:90,
		    bodyStyle:'padding:10px 0px 0px 0px',
		    items : [call]
		},{
			layout : 'form',
		    border : false,
		    width:90,
		    bodyStyle:'padding:10px 0px 0px 0px',
		    items : [loadBtn]
		},{
			layout : 'form',
		    border : false,
		    width:90,
		    bodyStyle:'padding:10px 0px 0px 0px',
	    	items : [saveBtn]
		}]       
	}); 
		//设置标题
	if(typeof(staticEnergySchemeName) != 'undefined'){
		northPnl.setTitle('方案名：'+staticEnergySchemeName);
	}
		
	//返回center面板
	var tb_energyCtrlScheme = new Ext.TabPanel({
		activeTab: 0,
		region:'center',
		items : [{
			border: false,
			layout : 'fit',
			title : '方案编辑列表',
			anchor:'100% 80%',
			id:'grid_energySchemeExec',
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
        items: [northPnl,tb_energyCtrlScheme]
	});
	
	//监听左边树点击事件
    var treeListener = new LeftTreeListener({
	    modelName : '电量定值控方案',
	    processClick : function(p, node, e) {
	    	if("grid_energySchemeExec"==tb_energyCtrlScheme.getActiveTab().getId()){
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
    		if("grid_energySchemeExec"==tb_energyCtrlScheme.getActiveTab().getId()){
		   	    userStore.baseParams = {tmnlAssetNo:record.get('tmnlAssetNo'),nodeType:'usr'};
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
	
	//加载方案信息
	if(typeof(staticEnergySchemeId) != 'undefined'){
		querySchemeInfo();
	}
	
	staticEnergySchemeActivate=0;
	
	renderModel(viewPanel,'电量定值控方案');
	
	Ext.getCmp('电量定值控方案').on('activate', function() {
		if(staticEnergySchemeActivate==1){
			if(typeof(staticEnergySchemeName) != 'undefined'){
	 			northPnl.setTitle(staticEnergySchemeName);
	 		}
	 		if(typeof(staticEnergySchemeId) != 'undefined'){
				querySchemeInfo();
			}
	 		staticEnergySchemeActivate=0;
		}
	});
	//加载方案信息
	function querySchemeInfo(){
   	   var type = "schemeId";             
   	   userStore.baseParams = {
   	   	   schemeId:staticEnergySchemeId,
   	   	   nodeType:type
   	   };
   	   Ext.getBody().mask("加载中...");
   	   userStore.load({
   	   	   callback: function(records, options, success){ 
               Ext.getBody().unmask();
			   return;
           }  
   	   });
	}
});											