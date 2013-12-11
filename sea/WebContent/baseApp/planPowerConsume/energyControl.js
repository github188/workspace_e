/**
 * 电量定值
 * 
 * @author 姜海辉
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
		    {header : '供电单位', sortable: true, dataIndex : 'orgName', align : 'center',width:150,
			      renderer : function(val) {
			      	if(null!=val){
						var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
								+ val + '</div>';
						return html;
					}
					else
						return '';}}, 
		    {header : '用户编号', sortable: true, dataIndex : 'consNo', align : 'center'}, 
		    {header : '用户名称', sortable: true, dataIndex : 'consName', align : 'center',width:110,
			     renderer : function(val) {
		     	    	if(null!=val){
							var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
									+ val + '</div>';
							return html;
						}
						else
						return '';}}, 
		    {header : '终端地址', sortable: true, dataIndex : 'terminalAddr', align : 'center',width:70}, 
		    {header : '总加组号', sortable: true, dataIndex : 'totalNo', align : 'center',width:70}, 
		    {header : '保电', sortable: true, dataIndex : 'protect', align : 'center',width:40,
		    	  renderer:function(value){
		    	     if("1"==value){
		    	    	 return "是";
		    	     }else if("0"==value){
		    	    	 return "否";
		    	     }else{
		    	    	 return "";}}},
			{header : '月电量定值(kWh)', sortable: true, dataIndex : 'powerConst', align : 'center'},
			{header : '定值浮动系数(%)', sortable: true, dataIndex : 'floatValue', align : 'center'},
	        {header : '控制状态', sortable: true, dataIndex : 'ctrlStatus', align : 'center',width:90,
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
		    {header : '执行状态', sortable: true, dataIndex : 'execStatus', align : 'center',width:70,
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
					       {name : 'ctrlStatus'},
					       {name : 'execStatus'},
					       //{name : 'isSendSms'}
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
			data[i][8] = valStore.getAt(i).data.powerConst;
			data[i][9] = valStore.getAt(i).data.floatValue;
			data[i][10] = valStore.getAt(i).data.protocolCode;
			data[i][11] = valStore.getAt(i).data.protect;
			data[i][12] = valStore.getAt(i).data.ctrlStatus;
			data[i][13] = valStore.getAt(i).data.execStatus;
			data[i][14] = valStore.getAt(i).data.message;
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
				       {name : 'ctrlStatus'},
				       {name : 'execStatus'},
				       //{name : 'isSendSms'}
				        {name : 'message'}
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
	//参数下发按钮
	var send = new Ext.Button({
			text : '参数下发',
			name : 'send',
			width :80,
		    handler:function(){
		    	Ext.getCmp('tb_energyCtrl').setActiveTab(0);
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
                ctrlCheckStaff(energyParamSend, '');
		    }
	 });
	
	 function energyParamSend(){
	 	var users = userSm.getSelections();
     	var energyCtrlArray= new Array();
 	    if (gsselectAllcb.checked){
     		for (var i = 0; i < userStore.getCount(); i++){
            	if("0"==userStore.getAt(i).get('protect')&&("-1"==userStore.getAt(i).get('ctrlStatus')||"0"==userStore.getAt(i).get('ctrlStatus')||"2"==userStore.getAt(i).get('ctrlStatus'))){
		     		var tmnltotal = userStore.getAt(i).get('tmnlAssetNo')+'`'+userStore.getAt(i).get('totalNo')+'`'+userStore.getAt(i).get('protocolCode')+'`'+userStore.getAt(i).get('powerConst')+'`'+userStore.getAt(i).get('floatValue');
		     		energyCtrlArray[i]=tmnltotal;
		     		userStore.getAt(i).set('execStatus',"0");
            	}
            	else
            		userStore.getAt(i).set('execStatus',"-1"); 
     		}
        }
     	else{
	     	for(var i = 0; i < users.length; i++){
	     		var rec = userStore.getById(users[i].get('keyId'));
	     		if(null!=rec){
		     		if("0"==users[i].get('protect')&&("-1"==users[i].get('ctrlStatus')||"0"==users[i].get('ctrlStatus')||"2"==users[i].get('ctrlStatus'))){
			     		var tmnltotal = users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode')+'`'+users[i].get('powerConst')+'`'+users[i].get('floatValue');
			     		energyCtrlArray[i]=tmnltotal;
						rec.set('execStatus',"0"); 
		     		}
		     		else
		     			rec.set('execStatus',"-1");
	     		}
	     	}	
     	}
     	userStore.commitChanges();
     	if(0==energyCtrlArray.length){
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
     		url:'./baseapp/energyControl!sendout.action',
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
     			if(null != result.tmnlExecStatusList && 0< result.tmnlExecStatusList.length ){
     				for(var j=0;j<result.tmnlExecStatusList.length;j++){
	     				var rec = userStore.getById(result.tmnlExecStatusList[j].keyId);	
	                    if(null!=rec){
	                    	if("1"==result.tmnlExecStatusList[j].execFlag){
	                		 rec.set('ctrlStatus',"2");
	 				     	 rec.set('execStatus',"1");//修改页面选中记录的状态
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
	var call = new Ext.Button({
			text : '参数召测',
			name : 'call',
			width : 80,
		    handler:function(){
		    	Ext.getCmp('tb_energyCtrl').setActiveTab(0);
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
     			 Ext.getCmp('tb_energyCtrl').setActiveTab(1);
     		     overFlat = true;
     		     Ext.Ajax.timeout=30000;
     		}
     	});
	}
	//月电控执行按钮
		var executeBtn = new Ext.Button({
				text : '月电控执行',
				name : 'execute',
				width : 80,
			    handler:function(){
			    	Ext.getCmp('tb_energyCtrl').setActiveTab(0);
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
	  		     	ctrlCheckStaff(energyExecute, 'ctrl');
			    }
		});
		function energyExecute(){
			var users = userSm.getSelections();
		    var energyCtrlArray = new Array();
	        if (gsselectAllcb.checked){
         		for (var i = 0; i < userStore.getCount(); i++){
		     		if("0"==userStore.getAt(i).get('protect')&&"2"==userStore.getAt(i).get('ctrlStatus')){
			     		var tmnltotal = userStore.getAt(i).get('tmnlAssetNo')+'`'+userStore.getAt(i).get('totalNo')+'`'+userStore.getAt(i).get('protocolCode');
			     		energyCtrlArray[i]=tmnltotal;
			     		userStore.getAt(i).set('execStatus',"0");
		     		}
		     		else
		     			userStore.getAt(i).set('execStatus',"-1");
         		}
            }
	     	else{
		     	for(var i = 0; i < users.length; i++){
		     		var rec = userStore.getById(users[i].get('keyId'));
		     		if(null!=rec){
			     		if("0"==users[i].get('protect')&&"2"==users[i].get('ctrlStatus')){
				     		var tmnltotal =users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');
				     		energyCtrlArray[i]=tmnltotal;
			     			rec.set('execStatus',"0");	
			     		}
			     		else
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
	     	if(0==energyCtrlArray.length){
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
	     		url:'./baseapp/energyControl!act.action',
	     		params : {
	     			schemeId:staticEnergyCtrlSchemeId,
	     			energyCtrlList:energyCtrlArray,
	     			taskSecond:taskSecond
	     			//isSendSms:isSendSms,
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
		
		//月电控解除按钮
		var unlockBtn = new Ext.Button({
				text : '月电控解除',
				name : 'unlock',
				width : 80,
			    handler:function(){
			    	Ext.getCmp('tb_energyCtrl').setActiveTab(0);
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
	  		     	ctrlCheckStaff(energyRelease, 'ctrl');
			    }
		});
		
		function energyRelease(){
			var users = userSm.getSelections();
		    var energyCtrlArray = new Array();
     		if (gsselectAllcb.checked){
         		for (var i = 0; i < userStore.getCount(); i++){
		     		if("1"==userStore.getAt(i).get('ctrlStatus')){
			     		var tmnltotal = userStore.getAt(i).get('tmnlAssetNo')+'`'+userStore.getAt(i).get('totalNo')+'`'+userStore.getAt(i).get('protocolCode');
			     		energyCtrlArray[i]=tmnltotal;
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
		     		if(null!=rec){
			     		if("1"==users[i].get('ctrlStatus')){
				     		var tmnltotal = users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');;
				     		energyCtrlArray[i]=tmnltotal;
			     			rec.set('execStatus',"0");
			     		}
			     		else{
			     			rec.set('execStatus',"-1");
			     		}
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
	     	if(0==energyCtrlArray.length){
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
	     		url:'./baseapp/energyControl!release.action',
	     		params : {
	     			schemeId:staticEnergyCtrlSchemeId,
	     			energyCtrlList:energyCtrlArray,
	     			//isSendSms:isSendSms,
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
	    hideLabel  : true,
        boxLabel : '下发成功后短信通知客户',
        width : 150
    });
	//返回用户列表
    var userListGrid = new Ext.grid.GridPanel({
	        store : userListStore,
	        cm : cm,
	        sm : userSm,
	        stripeRows : true,
	        autoScroll : true,
	        border: false,
	        tbar : [
	               send,
	               call,
	               executeBtn,
	               unlockBtn,'-',
	               smsCheckBox,
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
			bbar : new Ext.ux.MyToolbar({
				store : userListStore,
				allStore : userStore,
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
						return '';}}, 
		    {header : '用户编号', sortable: true, dataIndex : 'consNo', align : 'center'}, 
		    {header : '用户名称', sortable: true, dataIndex : 'consName', align : 'center',width:150,
		    	 renderer : function(val) {
		    	 	if(null!=val){
						var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
								+ val + '</div>';
						return html;
					}
					else
						return '';}},  
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
		remoteSort:true,
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

	//返回center面板
	var centerPnl = new Ext.TabPanel({
		id:'tb_energyCtrl',

		border: false,
		activeTab: 0,
		items : [{
			border: false,
			layout : 'fit',
			title : '方案执行列表',
			anchor:'100% 80%',
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
		title:'方案名：',
		layout: 'fit',
		border : false,
        items: [centerPnl]
	});
	
	//设置方案名为标题
	if(typeof(staticEnergyCtrlSchemeName) != 'undefined'){
		viewPanel.setTitle('方案名：'+staticEnergyCtrlSchemeName);
	}
	
	if(typeof(staticEnergyExeRelFlag) != 'undefined'){
		if(staticEnergyExeRelFlag==0){
			executeBtn.setVisible(true);
			unlockBtn.setVisible(false);
		}
		else if(staticEnergyExeRelFlag==1){
			executeBtn.setVisible(false);
			unlockBtn.setVisible(true);
		}	
	}
	
	renderModel(viewPanel,'电量定值控制');
    
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
	
	if(typeof(staticEnergyCtrlSchemeId) != 'undefined'){
		querySchemeInfo();
	}
	//设置激活标志，为0不加载数据，为1加载数据
	staticEnergyExeRelActivate=0;
	
	Ext.getCmp('电量定值控制').on('activate', function() {
		if(staticEnergyExeRelActivate==1){
			//设置方案名为标题
			if(typeof(staticEnergyCtrlSchemeName) != 'undefined'){
				viewPanel.setTitle('方案名：'+staticEnergyCtrlSchemeName);
			}
			if(typeof(staticEnergyExeRelFlag) != 'undefined'){
				if(staticEnergyExeRelFlag==0){
					executeBtn.setVisible(true);
					unlockBtn.setVisible(false);
				}
				else if(staticEnergyExeRelFlag==1){
					executeBtn.setVisible(false);
					unlockBtn.setVisible(true);
				}	
			}
			//Ext.getCmp('mainwest').collapse();
	 		if(typeof(staticEnergyCtrlSchemeId) != 'undefined'){
				querySchemeInfo();
			}
			staticEnergyExeRelActivate=0;
		}
	});
	//加载方案信息
	function querySchemeInfo(){
   	   var type = "schemeId";             
   	   userStore.baseParams = {
   	   	   schemeId:staticEnergyCtrlSchemeId,
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
