function origFrameQryShow(consNo,consName,tmnlAssetAddr){
		staticConsNo = consNo;
		staticConsName = consName;
		staticTmnlAssetAddr = tmnlAssetAddr;
		openTab("原始报文查询","./baseApp/dataGatherMan/origFrameQry.jsp");
	}
Ext.onReady(function(){
	tmplist=[];//选择时段控模板明细变量，在下拉列表选择时赋值
	//curTemplate="";//选中的时段控模板变量，在下拉列表选择时赋值
	constdata = new Array();
	zcDetail = new Array();

	//时段控召测结果明细grid
	function showPwrFetchDetail(){
		 var detailCm = new Ext.grid.ColumnModel([
	 		{header :'时段号',sortable:true, dataIndex:'sectionNo',align:'center'},
		    {header : '开始时间', sortable: true, dataIndex : 'sectionStart',align : 'center'},
		    {header : '结束时间', sortable: true, dataIndex : 'sectionEnd', align : 'center'},
		    {header : '方案定值1', sortable: true, dataIndex : 'const1',renderer:function(value){
			   			if(value==null|| typeof(value)=="undefined"){
				   			return "";
				   		} else {
				   		    return value;
				   		}
					}, align : 'center'}, 
		    {header : '方案定值2', sortable: true, dataIndex : 'const2',renderer:function(value){
			   			if(value==null|| typeof(value)=="undefined"){
				   			return "";
				   		} else {
				   		    return value;
				   		}
					}, align : 'center'}, 
		    {header : '方案定值3', sortable: true, dataIndex : 'const3',renderer:function(value){
			   			if(value==null|| typeof(value)=="undefined"){
				   			return "";
				   		} else {
				   		    return value;
				   		}
					}, align : 'center'}
			]);	
		
	 var detailStore = new Ext.data.Store({
			reader : new Ext.data.ArrayReader({
						idIndex: 0
					}, [{name:'sectionNo'},
					   {name : 'sectionStart'}, 
					   {name : 'sectionEnd'}, 
					   {name : 'const1'}, 
					   {name : 'const2'}, 
					   {name : 'const3'}
					   ])
		});	
		var record = new Array();
		for (var i = 0; i < zcDetail.length; i++) {
			record[i] = new Array();
			record[i][0] = zcDetail[i]['sectionNo'];
			record[i][1] = zcDetail[i]['sectionStart'];
			record[i][2] = zcDetail[i]['sectionEnd'];
			record[i][3] = zcDetail[i]['const1'];
			record[i][4] = zcDetail[i]['const2'];
			record[i][5] = zcDetail[i]['const3'];
		}
		detailStore.loadData(record);
		var detailGrid = new Ext.grid.GridPanel({
		        store : detailStore,
		        cm : detailCm,
		        border: false
	    });
	    
	  var detailWindow = new Ext.Window({
	       title:'时段控参数召测明细',
	       name:'sdkmb',
	       height:260,
	       width:480,
		   resizable:false,
		   layout:'fit',
		   items:[detailGrid]
    });
    
    detailWindow.show();
}


//时段控参数模板store
var templateStore = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
			url : 'baseapp/templateList!queryTemplateList.action'
		}),
	reader : new Ext.data.JsonReader({
			root : 'templateList',
			idProperty: 'templateId'
		}, [{name:'floatValue'},{name:'templateName'},{name:'templateId'},
			{name:'isValid'},{name:'isExec'},{name:'isValid'}])
});
//时段控明细定值store
var conststore = new Ext.data.Store({
	proxy : new Ext.data.MemoryProxy(constdata), // 数据源
	reader : new Ext.data.ArrayReader({}, [{
			name : 'id'
		}, {
			name : 'name'
	}])
});
	

	var sdkTemplateId="";
	// grid解锁
	function unlockGrid(tab) {//取消全选时，解锁Grid
		if(tab=="sdk") {//电压电流Grid
			sdk_cszxSM.unlock();
			sdk_cszxGrid.onEnable();
			sdk_cszxGrid.getBottomToolbar().enable();
			Ext.getCmp('sdkGsselectAllcb').setValue(false);
		}
	}

	// grid锁定
	function lockGrid(tab) {
		if(tab=="sdk") {
			sdk_cszxSM.lock();
			sdk_cszxGrid.onDisable();
			sdk_cszxGrid.getBottomToolbar().disable();
		}
	}	

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

	Ext.apply(Ext.form.VTypes, {
		powerConst: function(val, field) {
	    	if(val<0||val>99999||isNaN(val)){
	    		return false;
	    	}
	    	return true;
	    }, 
	    floatValue: function(val, field) {
	    	if(val<=0||val>=100||isNaN(val)){
	    		return false;
	    	}
	    	return true;
	    }, 
	    factoryConst: function(val, field) {
	    	if(val<0||val>99999){
	    		return false;
	    	}
	    	return true;
	    }, 
	    continueHours: function(val, field) {
	    	if(val<0||val>48){
	    		return false;
	    	}
	    	var zVal = val*10;
	    	if(zVal%5 != 0){
	    		return false;
	    	}
	    	return true;
	    }, 
	    minTime: function(val, field) {
	    	if(val!=0&&val!=30){
	    		return false;
	    	}
	    	return true;
	    }, 
	    hourTime: function(val, field) {
	    	var zVal = val*10;
	    	if(zVal%10 != 0){
	    		return false;
	    	}
	    	return true;
	    },
	    downWTime: function(val, field) {
	    	if(val<1||val>60){
	    		return false;
	    	}
	    	var zVal = val*10;
	    	if(zVal%10 != 0){
	    		return false;
	    	} 
	    	return true;
	    },
	    freezeTime: function(val, field) {
	    	if(val<5||val>60){
	    		return false;
	    	}
	    	var zVal = val*10;
	    	if(zVal%10 != 0){
	    		return false;
	    	} 
	    	return true;
	    },
	   powerConstText:'无效的功率定值',
	   downWTimeText:'该项值必须为1-60之间的整数',
	   freezeTimeText:'该项值必须为5-60之间的整数',
	   hourTimeText:'该项只能填入0-23之间的整数', 
	   minTimeText:'该项只能填入0或30',
	   factoryConstText:'厂休控定值超出实际范围',
	   continueHoursText:'延续时间为0-48内，间隔半小时的数字',
	   floatValueText: '浮动系数必须是0-100之间的数字'
	});
 //------------------------------------时段控   模板信息修改 按钮  开始
	var sdkmbUpdate1 = new Ext.Panel({
	  	layout:'column',
	  	region:'north',
	  	height:50,
	  	bodyStyle:'padding: 15px 0px',
	  	items:[{
				columnWidth:.5,
				labelAlign:'right',
				layout:'form',
				labelSeparator:' ',
				border:false,
				items:[{
					xtype:'textfield',
					fieldLabel:'模板名称',
					id:'powerControl_sdkmbUpdate',
					readOnly:true,
					name : 'wPwrctrlTemplate.templateName',
					emptyText:'请输入模板名称',
					width:180
					}]
			},{
				columnWidth:.15,
				border:false,
				layout:'form',
				items:[{
					xtype:'button',
					handler:function(){
						sdkmbUpdate.hide(); 
					},
					text:'关闭'
			}]
		}]
	});
	
	var sdkmbUpdate2_1 = new Ext.Panel({
				border : false,
				bodyStyle : 'padding:10px 0px 0px 0px',
				layout : 'column',
				items : [{
							columnWidth : .15,
							bodyStyle : 'padding:0px 0px 0px 20px',
							border : false,
							items : [{
										xtype : 'label',
										text : '选择'
									}]
						}, {
							columnWidth : .2,
							border : false,
							items : [{
										xtype : 'label',
										text : '开始时段'
									}]
						}, {
							columnWidth : .16,
							border : false,
							items : [{
										xtype : 'label',
										text : '结束时段'
									}]
						}, {
							columnWidth : .13,
							border : false,
							items : [{
										xtype : 'label',
										text : '功率定值1(KW)'
									}]
						}, {
							columnWidth : .13,
							border : false,
							items : [{
										xtype : 'label',
										text : '功率定值2(KW)'
									}]
						}, {
							columnWidth : .13,
							border : false,
							items : [{
										xtype : 'label',
										text : '功率定值3(KW)'
									}]
						}]
			});

	var sdkmbUpdate2_array = new  Array(8);
	for (var i = 1; i < 9; i++) {
		sdkmbUpdate2_array[i-1] = new Ext.Panel({
			border:false,
			layout:'column',
			bodyStyle:'padding: 10px 0px 0px 10px',
			items:[{
				columnWidth:.08,
				name :'id'+i+'.isValid',
				layout:'form',
				border:false,
				xtype:'checkbox',
				boxLabel:'时段'+i,
				disabled:true
			},{
				xtype:'hidden',
//				id:'updateId'+i+'.sectionStart',
				name :'id'+i+'.sectionStart'
			},{
				xtype:'hidden',
//				id:'updateId'+i+'.sectionEnd',
				name :'id'+i+'.sectionEnd'
			},{
				xtype:'hidden',
				name :'id'+i+'.sectionNo',
				value : i
			},{
				columnWidth:.2,
				layout:'column',
				border:false,
	//			width:80,
				items:[{
						columnWidth:.5,
						border:false,
						labelSeparator:' ',
						layout:'form',
						labelWidth:1,
						items:[
							 new Ext.ux.form.SpinnerField({
									fieldLabel:'',
									minValue: 0,
									maxValue: 23,
									width:50,
									name :'sectionStartH_'+i,
									allowDecimals: true,
									decimalPrecision: 1,
									incrementValue: 1,
									vtype:'hourTime',
//									confirmTo:'updateId'+(i-1)+'.sectionEnd',
									readOnly:true,
									accelerate: true
	            			})
						]
					},{
						columnWidth:.5,
						border:false,
						labelSeparator:' ',
						layout:'form',
						labelWidth:1,
						items:[
							 new Ext.ux.form.SpinnerField({
									fieldLabel:':',
									minValue: 0,
									maxValue: 30,
									width:50,
									name :'sectionStartM_'+i,
									allowDecimals: true,
									decimalPrecision: 1,
									incrementValue: 30,
									vtype:'minTime',
//									confirmTo:'updateId'+(i-1)+'.sectionEnd',
									readOnly:true,
									accelerate: true
	            			})
						]
					}]
			},{
				columnWidth:.2,
				name :'sectionEnd_'+i,
				layout:'column',
				border:false,
	//			width:80,
				items:[{
						columnWidth:.5,
						border:false,
						labelSeparator:' ',
						layout:'form',
						labelWidth:1,
						items:[
							 new Ext.ux.form.SpinnerField({
									fieldLabel:'',
									minValue: 0,
									maxValue: 23,
									width:50,
									name :'sectionEndH_'+i,
									allowDecimals: true,
									decimalPrecision: 1,
									incrementValue: 1,
									vtype:'hourTime',
//									confirmTo:'updateId'+i+'.sectionStart',
									readOnly:true,
									accelerate: true
	            			})
						]
					},{
						columnWidth:.5,
						border:false,
						labelSeparator:' ',
						layout:'form',
						labelWidth:1,
						items:[
							 new Ext.ux.form.SpinnerField({
									fieldLabel:':',
									minValue: 0,
									maxValue: 30,
									width:50,
									name :'sectionEndM_'+i,
									allowDecimals: true,
									decimalPrecision: 1,
									incrementValue: 30,
									vtype:'minTime',
//									confirmTo:'updateId'+i+'.sectionStart',
									readOnly:true,
									accelerate: true
	            			})
						]
					}]
			},{
				columnWidth:.13,
				layout:'form',
				border:false,
				labelWidth:13,
				labelSeparator:' ',
				items:[{
					xtype:'textfield',
					name :'id'+i+'.const1',
					fieldLabel:' ',
					readOnly:true,
					width:80
				}]
			},{
				columnWidth:.13,
				layout:'form',
				border:false,
				labelWidth:13,
				labelSeparator:' ',
				items:[{
					xtype:'textfield',
					name :'id'+i+'.const2',
					fieldLabel:' ',
					readOnly:true,
					width:80
				}]
			},{
				columnWidth:.13,
				layout:'form',
				border:false,
				labelWidth:13,
				labelSeparator:' ',
				items:[{
					xtype:'textfield',
					name :'id'+i+'.const3',
					fieldLabel:' ',
					readOnly:true,
					width:80
				}]
			}]
		});
	}	
	
	var sdkmbUpdate2 = new Ext.Panel({
		layout:'form',
		border:false,
		items:[sdkmbUpdate2_1,sdkmbUpdate2_array]
	});
	
	var sdkRadioGroup = new Ext.form.RadioGroup({
		columnWidth:.15,
		border:false,
		layout:'form',
		width:100,
		name :'radioGroup',
		disabled:true,
		items:[{
				boxLabel:'下浮',
				inputValue:'1',
				name:'condRadio'
			},{
				boxLabel:'上浮',
				inputValue:'2',
				name:'condRadio'
			}]
	});
	var sdkmbUpdate3 = new Ext.Panel({
		border:false,
		layout:'column',
		items:[{
			columnWidth:.2,
			layout:'form',
			bodyStyle:'padding:5px 0px 0px 10px',
			border:false,
			hideLabels:true,
			items:[{
				xtype:'checkbox',
				name:'template.isExec',
				disabled:true,
				boxLabel:'功率定值浮动系数(%)'		
			}]
		},{
			xtype:'hidden',
			name :'template.templateName'
		},{
			columnWidth:.15,
			border:false,
			layout:'form',
			bodyStyle:'padding:5px 0px 0px 0px',
			hideLabels:true,
			items:[sdkRadioGroup]
		},{
			columnWidth:.15,
			layout:'form',
			border:false,
			hideLabels:true,
			bodyStyle:'padding:5px 0px 0px 0px',
			labelSeparator:' ',
			items:[{
				xtype:'textfield',
				name:'template.floatValue',
				fieldLabel:' ',
				width:80,
				readOnly:true,
				vtype:'floatValue'
			}]
		}]
	});
	
	var mbcs_sdkmbUpdate = new Ext.FormPanel({
		region:'center',
		layout:'form',
		title:'模板参数',
		autoScroll : true,
		items:[sdkmbUpdate2,sdkmbUpdate3]
	});
	
	
	var sdkmbUpdate = new Ext.Window({
       title:'时段控模板信息',
       name:'sdkmbUpdate',
       modal:true,
       height:475,
       width:790,
       closeAction:'hide',
	   resizable:false,
	   layout:'border',
	   items:[sdkmbUpdate1,mbcs_sdkmbUpdate]
    });   
//---------------------时段控 查看模板信息 按钮 结束    
	
//---------------------------------------时段控 panel  开始	
	var templateNameComboBox = new  Ext.form.ComboBox({
		store : templateStore,
		xtype:'combo',
		name :'combo_templateName',
		fieldLabel:'选择模板',
		valueField : 'templateId',
		editable : false,
		triggerAction : 'all',
		forceSelection : true,
		mode : 'remote',
		selectOnFocus : true,
		displayField : 'templateName',
		emptyText:'功率时段控',
		anchor:'85%'
	});
	
	//根据模板名称查询模板明细
	function sdkTemplateDetail(loadOrLink) {
		Ext.Ajax.request({
				url : 'baseapp/templateDetails!queryTemplateDetails.action',
					params : {
						templateId : curTemplate.get('templateId')
					},
				success : function(response) {
					tmplist = Ext.decode(response.responseText).detailList;
					
					var length = tmplist.length;//获取模板明细有几个时段
					if(loadOrLink ==1) {
						executeCheckbox.eachItem(function(obj){//循环checkbox将其初始化
							obj.setDisabled(false);
							obj.setValue(false);
						});
						var flag = 0;
						executeCheckbox.eachItem(function(obj){
							if(flag++ >= length){
								obj.setDisabled(true);//将大于模板时段数的复选框灰掉
							}
						});
						executeCheckbox.setValue();//将复选框全部不选中
					}
					conststore.removeAll();
					constdata=[];
					
					var const1 = 0;
					var const2 = 0;
					var const3 = 0;
					var arrayLength = 0;
					Ext.each(tmplist,function(tmp){
							if(tmp != null) {
								if(tmp['id']['const1'] != null && tmp['id']['const1'] != 0 && const1 ==0) {
									const1 =1;
									constdata[arrayLength++] = [1, '功率定值1'];
								}
								if(tmp['id']['const2'] != null && tmp['id']['const2'] != 0 && const2 ==0) {
									const2 =1;
									constdata[arrayLength++] = [2, '功率定值2'];
								}
								if(tmp['id']['const3'] != null && tmp['id']['const3'] != 0 && const3 ==0) {
									const3 =1;
									constdata[arrayLength++] = [3, '功率定值3'];
								}
							}
					});		
					conststore.loadData(constdata);
				}
		});
	}
	
	templateNameComboBox.on("collapse", function (o){
		var store = templateNameComboBox.getStore();
		curTemplate = store.getById(o.getRawValue());
		
		if(curTemplate ==null ||curTemplate==''){
			return;
		}
		sdkTemplateDetail(1);
	});
	
	//参数下发，控制下发，控制解除返回结果赋值处理函数
	var sdkSendCtrlResult = function sdkSendCtrlResult(result, users) {
		if (Ext.getCmp('sdkGsselectAllcb').checked) {//如果选中全选按钮
			sdk_cszxStore.each(function(rec){
				if(rec.get('execFlag') != '-9')
					rec.set('execFlag','-1');//将返回结果默认全设置成失败
	        	for(var j = 0; j < result.length; j++){//循环返回结果list 根据keyId匹配记录，设置返回值
	        		if(result[j].keyId==rec.get('keyId')) {
			        	if(result[j].execFlag !='-1'){
			        		rec.set('ctrlFlag',result[j].execFlag);
			        		rec.set('execFlag',result[j].execFlag);//返回结果匹配上再修改
	        			}
			        	break;
	        		}
	        	}
			});
			sdk_cszxStore.commitChanges();//提交修改
			
			splite_sdk_cszxStore.proxy = new Ext.ux.data.PagingMemoryProxy(sdkStoreToArray(sdk_cszxStore));
			splite_sdk_cszxStore.load({
				params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
			});
		} else {
			for(var i = 0; i < users.length; i++){
				var rec = sdk_cszxStore.getById(users[i].get('keyId'));
                 if(users[i].get('execFlag') != '-9') {
					rec.set('execFlag','-1');//将返回结果默认全设置成失败
                 } else {
                 	rec.set('execFlag','-9');
                 }
				 for(var j = 0; j < result.length; j++){//循环返回结果list 根据keyId匹配记录，设置返回值
		    		if(result[j].keyId==users[i].get('keyId')) {
		    			if(result[j].execFlag !='-1'){
			        		rec.set('ctrlFlag',result[j].execFlag);
			        		rec.set('execFlag',result[j].execFlag);
		    			}
			        	break;
		    		}
		    	}
			}
			sdk_cszxStore.commitChanges();//提交修改
			splite_sdk_cszxStore.proxy = new Ext.ux.data.PagingMemoryProxy(sdkStoreToArray(sdk_cszxStore));
			splite_sdk_cszxStore.load({
				params : {
					start : splite_sdk_cszxStore.lastOptions.params.start,
					limit : DEFAULT_PAGE_SIZE
				}
			});
		}
	};
	
	var sdkSend = function sdkSend(){
		var users = sdk_cszxSM.getSelections();
		var templateName = templateNameComboBox.getRawValue();
     	var pwrCtrlList = new Array();//向后台传的参数数组
     	var execNum = 0;//过滤终端的个数
     	var pwrCtrlListNum = 0;//pwrCtrlList的长度 用于自加
     	if (Ext.getCmp('sdkGsselectAllcb').checked) { //如果选中全选按钮
			for (var i = 0; i < sdk_cszxStore.getCount(); i++) {
				if(sdk_cszxStore.getAt(i).data.ctrlFlag =='1' || sdk_cszxStore.getAt(i).data.tmnlPaulPower =='是'){
					 sdk_cszxStore.getAt(i).set('execFlag','-9');
	     			 execNum++;
	     			 continue;
	     		}
				pwrCtrlList[pwrCtrlListNum++] =sdk_cszxStore.getAt(i).data.orgNo +'`' + sdk_cszxStore.getAt(i).data.consNo +
					'`' + sdk_cszxStore.getAt(i).data.tmnlAssetNo + '`' + sdk_cszxStore.getAt(i).data.totalNo + '`' + sdk_cszxStore.getAt(i).data.protocolCode;
			}
			sdk_cszxStore.commitChanges();
     	} else {
     		for(var i = 0; i < users.length; i++){
	     		if(users[i].get('ctrlFlag')=='1' || users[i].get('tmnlPaulPower')=='是'){
	     			 users[i].set('execFlag','-9');
	     			 execNum++;
	     			 continue;
	     		}
				pwrCtrlList[pwrCtrlListNum++] = users[i].get('orgNo')+'`'+users[i].get('consNo')+
					'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');
       		}
       		sdk_cszxStore.commitChanges();
     	} 
        if(pwrCtrlList.length<=0){
        	Ext.MessageBox.alert("提示",'没有符合下发条件的终端');
        	return;
        }
        if(execNum!= 0) {
        	Ext.MessageBox.alert("提示",execNum+"个终端的总加组被保电或已投入时段控, 不能进行参数下发，将被自动过滤");
        }
        var ov = true;
        var second = Ext.Ajax.timeout/1000;
        h_taskTime(second,function(){
        	ov = false;
        });
    	Ext.Ajax.request({
     		url:'./baseapp/paramSend!sdkParamSend.action',
     		params : {
     			second:second,
     			templateName:templateName,
     			pwrCtrlList:pwrCtrlList
     		},
     		success : function(response){
     			var cacheAndTmnlStatus = Ext.decode(response.responseText).cacheAndTmnlStatus;
     			if(cacheAndTmnlStatus!=""&& cacheAndTmnlStatus!=null){
     				Ext.MessageBox.alert("提示",cacheAndTmnlStatus);
     				return;
     			}
     			if(!ov ) return ;
     			var result = Ext.decode(response.responseText).execResultList;
     			if(result == null || result.length==0){
     				Ext.MessageBox.alert("提示","时段控参数下发失败,全部终端无应答！");
     		  		return;
     			} 
     			sdkSendCtrlResult(result, users);//调用参数下发结果处理函数
     			overFlat = true;
     			Ext.Ajax.timeout = 30000;
     		},
     		failure : function(){
     			if(!ov ){
     				return ;
     			}
     			overFlat = true;
     			Ext.Ajax.timeout = 30000;
     			Ext.MessageBox.alert("提示","时段控参数下发失败");
     		}
     	});
    };
	
    var sdkFetch = function sdkFetch(){
    	var users = sdk_cszxSM.getSelections();
    	var pwrCtrlList = new Array();//向后台传的参数数组
     	var execNum = 0;//过滤终端的个数
     	var pwrCtrlListNum = 0;//pwrCtrlList的长度 用于自加
     	if (Ext.getCmp('sdkGsselectAllcb').checked) { //如果选中全选按钮
			for (var i = 0; i < sdk_cszxStore.getCount(); i++) {
				pwrCtrlList[pwrCtrlListNum++] =sdk_cszxStore.getAt(i).data.orgNo +
					'`' + sdk_cszxStore.getAt(i).data.tmnlAssetNo + '`' + sdk_cszxStore.getAt(i).data.totalNo + '`' + sdk_cszxStore.getAt(i).data.protocolCode;
			}
     	} else {
     		for(var i = 0; i < users.length; i++){
				pwrCtrlList[pwrCtrlListNum++] = users[i].get('orgNo')+'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo');
       		}
     	} 
     	sdk_zcStore.removeAll();
     	//显示进度条
        var ov = true;
        var second = Ext.Ajax.timeout/1000;
        h_taskTime(second,function(){
        	ov = false;
        });
    	Ext.Ajax.request({
     		url:'./baseapp/sdkParamFecth!sdkParamFecth.action',
     		params : {
     			second:second,
     			pwrCtrlList:pwrCtrlList
     		},
     		success : function(response){
     			if(!ov) return;
     			var cacheAndTmnlStatus = Ext.decode(response.responseText).cacheAndTmnlStatus;
     			if(cacheAndTmnlStatus!=""&& cacheAndTmnlStatus!=null){
     				Ext.MessageBox.alert("提示",cacheAndTmnlStatus);
     				return;
     			}
     			var result = Ext.decode(response.responseText).pwrctrlResultList;
     			if(result == null || result.length==0){
     				Ext.MessageBox.alert("提示","时段控参数召测失败,终端无应答！");
     		  		return;
     			}
     			record = new Array();
     			if (Ext.getCmp('sdkGsselectAllcb').checked) { //如果选中全选按钮
					for (var i = 0; i < sdk_cszxStore.getCount(); i++) {
						record[i] = new Array();
			        	record[i][0] =sdk_cszxStore.getAt(i).data.keyId;
			        	record[i][1] = sdk_cszxStore.getAt(i).data.orgName;
			        	record[i][3] = sdk_cszxStore.getAt(i).data.consNo;
			        	record[i][4] = sdk_cszxStore.getAt(i).data.consName;
			        	record[i][5] = sdk_cszxStore.getAt(i).data.terminalAddr;
			        	record[i][7] = sdk_cszxStore.getAt(i).data.totalNo;
			        	
			        	for(var j = 0; j < result.length; j++){//循环返回结果list 根据keyId匹配记录，设置返回值
			        		if(result[j].keyId==sdk_cszxStore.getAt(i).data.keyId) {
			        			record[i][8] = result[j].detailList;
					        	record[i][9] = result[j].floatValue;
					        	break;
			        		}
			        	}
					}
					sdk_zcStore.proxy = new Ext.ux.data.PagingMemoryProxy(record);
					sdk_zcStore.load({
						params : {
							start : 0,
							limit : DEFAULT_PAGE_SIZE
						}
					});
				} else {
			     	for(var i = 0; i < users.length; i++){
			        	record[i] = new Array();
			        	record[i][0] = users[i].get('keyId');
                    	record[i][1] = users[i].get('orgName');
                    	record[i][3] = users[i].get('consNo');
                    	record[i][4] = users[i].get('consName');
                    	record[i][5] = users[i].get('terminalAddr');
                    	record[i][7] = users[i].get('totalNo');
			        	
			        	for(var j = 0; j < result.length; j++){//循环返回结果list 根据keyId匹配记录，设置返回值
			        		if(result[j].keyId==users[i].get('keyId')) {
			        			record[i][8] = result[j].detailList;
					        	record[i][9] = result[j].floatValue;
					        	break;
			        		}
			        	}
			        }
			        sdk_zcStore.loadData(record);
				}
                sdkGrid.setActiveTab(1);
                overFlat = true;
                Ext.Ajax.timeout = 30000;
     		},
     		failure : function(){
     			if(!ov){
					return;	  		     				
     			}
     			overFlat = true;
     			Ext.Ajax.timeout = 30000;
     			Ext.MessageBox.alert("提示","时段控参数召测失败");
     		}
     	});
    };
    
// ------------------------------------------------------------时段控方案加载保存开始------------------------------------------------------------	
//	定义方案加载弹出页面的上部分面板组件
//	定义方案加载时选中的方案ID
	var sdkSchemeIdValue;
	var sdkLoadScheme1=new Ext.Panel({
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
   	           text:'功率时段控方案加载'
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
   	           	   var loadFuncSelected = sdkLoadScheme2.find("name","multiselect")[0];
   	           	   var selectionsArray = loadFuncSelected.view.getSelectedIndexes();
   	           	   var type = "schemeId";
   	           	   if(0 < selectionsArray.length){
   	           	   	   for(i = 0 ; i < selectionsArray.length; i++){
   	                       sdkSchemeIdValue = sdkLoadStore.getAt(selectionsArray[selectionsArray.length-1]).get('ctrlSchemeId');
   	           	   	   }
   	           	   }
   	           	   sdk_cszxStore.removeAll();
   	           	   sdk_cszxStore.baseParams = {
   	           	   	   schemeId:sdkSchemeIdValue,
   	           	   	   nodeType:type,
   	           	   	   add:true
   	           	   };
   	           	   sdk_cszxStore.load({
						params:{start:0,limit:20},
						callback: function(records, options, success){
                           if(null != records && 0 < records.length){
                           		Ext.Ajax.request({
									url : "./baseapp/pwrctrlLoadScheme!pwrctrlLoadScheme.action",
									params : {
										schemeId:sdkSchemeIdValue
									},
									success : function(response) {
										var records = Ext.decode(response.responseText);
										var schemeCurveNo = records.schemeCurveNo;
										var validSectionNo = records.validSectionNo;
		                           		
										var length = records.secLength;//获取模板明细有几个时段
										executeCheckbox.eachItem(function(obj){//循环checkbox将其初始化
											obj.setDisabled(false);
											obj.setValue(false);
										});
										var flag = 0;
										executeCheckbox.eachItem(function(obj){
											if(flag++ >= length){
												obj.setDisabled(true);//将大于模板时段数的复选框灰掉
											}
										});
		                           		executeCheckbox.setValue(validSectionNo);
		                           		
										constdata=[];
		                           		templateNameComboBox.setValue(records.templateName);
		                           		constdata[0] = [schemeCurveNo , "功率定值"+schemeCurveNo];
		                           		constcombo.getStore().loadData(constdata);
		                           		constcombo.setValue(schemeCurveNo);
									}
								});
                           }
                           else{
                           		executeCheckbox.setValue("");
                           		templateNameComboBox.setValue("");
                           }
                       },
                       add:true
   	           	   });
   	               sdkLoadSchemeWindow.hide();
  		       }
   	       }] 
   	    }]
    });
    
    var sdkLoadStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url:'./baseapp/pwrctrlScheme!sdkLoadScheme.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'schemeList'
			}, [{name:'ctrlSchemeId'},
				{name:'ctrlSchemeName'},
				{name:'ctrlSchemeType'}])
	});
// 	定义方案加载弹出页面的下部分面板组件
    var sdkLoadScheme2 =new Ext.Panel({
        border : false,
        layout:'column',
        region:'center',
        items:[{
   	        columnWidth:.75,
   	        border : false,
   	        layout:'fit',
   	        hideLabels:true,
   	        bodyStyle : 'padding:0px 0px 0px 20px',
   	        items:[{
   	            xtype: 'multiselect',
				labelSeparator:'',
                name: 'multiselect',
   	            height:120,
   	            width: 220,
   	            displayField : 'ctrlSchemeName',
			    valueField : 'ctrlSchemeId',
   	            labelSeparator:'',
                anchor:'90%',
                store: sdkLoadStore
   	        }] 
   	    },{
   	       columnWidth:.25,
   	       border : false,
   	       layout:'form',
   	       items:[{
   	           xtype:'button',
   	           width:80,
   	           text:'退出',
   	           handler:function(){
   	           	   sdkLoadSchemeWindow.hide();
  		       }
   	       }] 
   	    }]
    });

//  方案加载窗口
    var sdkLoadSchemeWindow = new Ext.Window({
        name:'sdkLoadSchemeWindow',
        height:250,
        width:400,
        closeAction:'hide',
        resizable:false,
        layout:'border',
        title:'方案加载',
        items:[sdkLoadScheme1,sdkLoadScheme2]
    }); 

//    -------------------------------------------------时段控-执行方案   开始   
	var executePanel = new Ext.Panel({
		border:false,
		labelAlign:'right',
		labelWidth : 70,
		layout:'form',
		bodyStyle:'padding: 10px 0px 0px 0px',
		labelSeparator:' ',
		items:[{
				xtype:'checkboxgroup',
				fieldLabel : '消息发送',
				width:500,
				items : [{
							boxLabel : '下发成功后短信通知客户'
						}, {
							boxLabel : '方案到期后自动通知'
						}, {
							boxLabel : '下发成功后自动短信订阅'
						}]
			}]
		});
			
	var executeCheckbox = new Ext.form.CheckboxGroup({
		fieldLabel : '有效时段',
		labelSeparator:' ',
		width:260,
		disabled:true,
		items : [{	
					name : '1',
					inputValue:'1',
					boxLabel : '1'
				}, {
					name : '2',
					inputValue:'2',
					boxLabel : '2'
				}, {
					name : '3',
					inputValue:'3',
					boxLabel : '3'
				}, {
					name : '4',
					inputValue:'4',
					boxLabel : '4'
				}, {
					name : '5',
					inputValue:'5',
					boxLabel : '5'
				}, {
					name : '6',
					inputValue:'6',
					boxLabel : '6'
				}, {
					name : '7',
					inputValue:'7',
					boxLabel : '7'
				}, {
					name : '8',
					inputValue:'8',
					boxLabel : '8'
				}]
	});		
	templateNameComboBox.on("change",function(templateName,newValue){
			executeCheckbox.setValue();
	});		
	
//	conststore.load();		
	var constcombo = new Ext.form.ComboBox({
			store : conststore,
			fieldLabel : '功率定值方案',
			typeAhead : true,
			editable : false,
			mode : 'local',
			forceSelection : true,
			triggerAction : 'all',
			width:160,
//			value : '1',
//			resizable : true,
			valueField : 'id',
			emptyText:'--请选择功率定值方案--',
			displayField : 'name',
			selectOnFocus : true
		});
	var executePanel_1 = new Ext.Panel({
//			labelAlign:'right',
			layout:'column',
			border:false,
			items:[{
				columnWidth:.3,
				labelSeparator:' ',
				layout:'form',
				labelAlign:'right',
				labelWidth : 94,
				border:false,
				items:[constcombo]
			},{
				columnWidth:.5,
				border:false,
				items:[{
					xtype:'panel',
					layout:'form',
					labelAlign:'right',
					border:false,
					items:[executeCheckbox]
				}]
			}]
	});
	
	var periodExePanel = new Ext.Panel({      //时段控执行方案panel
	   title:'方案名：',
	   layout:'form',
	   region:'north',
	   height:100,
	   items:[executePanel,executePanel_1]
	});
	
	//设置方案名为标题
	if(typeof(staticPeriodCtrlSchemeName) != 'undefined'){
		periodExePanel.setTitle('方案名：'+staticPeriodCtrlSchemeName);
	}
	//----------------------------时段控参数执行-召测列表    开始	

	var sdk_cszxStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url:'./baseapp/pwrctrlSchemeExec!queryPwrctrlSchemeExec.action'
				}),
				reader : new Ext.data.JsonReader({
					root : 'pwrctrlSchemeExecList',
					idProperty: 'keyId'
				}, [{name : 'orgName'},
					{name : 'keyId'},
					{name : 'orgNo'},
					{name : 'consNo'},
					{name : 'consName'},
					{name : 'terminalAddr'},
					{name : 'tmnlAssetNo'},
					{name : 'totalNo'},
					{name : 'tmnlPaulPower'},
					{name : 'ctrlFlag'},
					{name:'execFlag'},
					{name:'protocolCode'},
					{name : 'message'}])
			});
	//时段控分页store		
	var splite_sdk_cszxStore = new Ext.data.Store({
			remoteSort : true,
			proxy : new Ext.data.MemoryProxy(),
			reader : new Ext.data.ArrayReader({
				idIndex : 1,
				fields :  [
					{name : 'orgName'},
					{name : 'keyId'},
					{name : 'orgNo'},
					{name : 'consNo'},
					{name : 'consName'},
					{name : 'terminalAddr'},
					{name : 'tmnlAssetNo'},
					{name : 'totalNo'},
					{name : 'tmnlPaulPower'},
					{name : 'ctrlFlag'},
					{name:'execFlag'},
					{name:'protocolCode'},
					{name : 'message'}]
				})
		});		

	function sdkStoreToArray(valStore) {
		var data = new Array();
		for (var i = 0; i < valStore.getCount(); i++) {
			data[i] = new Array();
			var index = 0;
			data[i][index++] = valStore.getAt(i).data.orgName;
			data[i][index++] = valStore.getAt(i).data.keyId;
 			data[i][index++] = valStore.getAt(i).data.orgNo;
			data[i][index++] = valStore.getAt(i).data.consNo;
			data[i][index++] = valStore.getAt(i).data.consName;
			data[i][index++] = valStore.getAt(i).data.terminalAddr;
			data[i][index++] = valStore.getAt(i).data.tmnlAssetNo;
			data[i][index++] = valStore.getAt(i).data.totalNo;
			data[i][index++] = valStore.getAt(i).data.tmnlPaulPower;
			data[i][index++] = valStore.getAt(i).data.ctrlFlag;
			data[i][index++] = valStore.getAt(i).data.execFlag;
			data[i][index++] = valStore.getAt(i).data.protocolCode;
			data[i][index++] = valStore.getAt(i).data.message;
		}
		return data;
	}	
		
	var sdk_cszxSM = new Ext.grid.CheckboxSelectionModel();
	var rowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(sdk_cszxStore && sdk_cszxStore.lastOptions && sdk_cszxStore.lastOptions.params){
				startRow = sdk_cszxStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	var sdk_cszxCM = new Ext.grid.ColumnModel([
			rowNum, 
			sdk_cszxSM, 
			//{header : '选择',	 dataIndex : 'keyId', sortable : true,	hidden:true, align : 'center'}, 
			{header : '供电单位', dataIndex : 'orgName', sortable : true, renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			},align : 'center',defaultWidth:140}, 
			//{header : '供电单位编号', sortable:true, dataIndex:'orgNo', align:'center', hidden:true}, 
//			{header : '终端资产号', 	sortable:true, dataIndex:'tmnlAssetNo', align:'center',hidden:true},
			{header : '用户编号', dataIndex : 'consNo',	sortable : true,	align : 'center'}, 
			{header : '用户名称',	dataIndex : 'consName',	sortable : true,	renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			},align : 'center'}, 
			{header : '终端地址',	dataIndex : 'terminalAddr', sortable : true,renderer : function (val) {
						 return "<div align = 'left'>"+ val + "</div>";
						 }, align : 'center'}, 
			{header : '总加组号', dataIndex : 'totalNo',	sortable : true,align : 'center'}, 
			{header : '保电',	dataIndex : 'tmnlPaulPower',	sortable : true,	align : 'center'}, 
			{header : '控制状态',	dataIndex : 'ctrlFlag',sortable : true,renderer:function(value){
		   			if(value=="1"){
			   			return "投入";
			   		}else if(value=="0") {
			   			return "解除";
			   		} else if(value == "2") {
			   			return "参数下发成功";
			   		} else {
			   		    return "";
			   		}
				},align : 'center'}, 
			{header : '执行状态',dataIndex : 'execFlag',sortable : true,renderer:function(value){
		   			if(value=="2"|| value=="1" || value =="0"){
			   			return "<font color='green';font-weight:bold>成功</font>";
			   		}else if(value=="-1"){
			   			return "<font color='red';font-weight:bold>失败</font>";
			   		}else if(value=="-9"){
			   		    return "未执行";
			   		} else {
			   			return "";
			   		}
				}, 	align : 'center'}, 
			//{header : '规约编码',	dataIndex : 'protocolCode',	sortable : true,	hidden:true,	align : 'center'}, 
			{header : '报文',	dataIndex : 'message',sortable : true,	align : 'center',	renderer: function(s, m, rec){
					return "<a href='javascript:'onclick='origFrameQryShow(\""+rec.get('consNo')+"\",\""+ rec.get('consName')+"\",\""+ rec.get('terminalAddr')+"\");" + "'>查看</a>"; 
			}}
		]);
	
	var periodSchemeExecButton = new Ext.Button({
  		text:'方案执行',//时段控
  		handler:function(){
  			var users = sdk_cszxSM.getSelections();
	        if(null == users || users.length==0){
		     	    Ext.MessageBox.alert("提示","请选择用户！");
		     	    return;
		     	}
	        //获取方案号，并校验
	     	var schemeCurveNo = constcombo.getValue();
	     	if(schemeCurveNo =='' ||schemeCurveNo ==null || typeof(schemeCurveNo) =='undefined'){
	     		Ext.MessageBox.alert("提示","请选择方案号！");
	     	    return;
	     	}
	     	// 获取有效时段，并校验
	     	var validSection = executeCheckbox.getValue();
	     	if(validSection =='' ||validSection ==null){
	     		Ext.MessageBox.alert("提示","请选择有效时段！");
	     	    return;
	     	}
	     	ctrlCheckStaff(sdkExec, 'ctrl');
  		}
	});
	
	var periodSchemeReleaseButton = new Ext.Button({
  		text:'方案解除',//时段控
  		handler:function(){
  			var users = sdk_cszxSM.getSelections();
     		if(null == users || users.length==0){
	     	    Ext.MessageBox.alert("提示","请选择用户！");
	     	    return;
	     	}
	     	ctrlCheckStaff(sdkRelease, 'ctrl');
  		}
	});
		
	var sdk_cszxGrid = new Ext.grid.GridPanel({
			ds : splite_sdk_cszxStore,
			title : '时段控方案执行列表',
//				height:300,
			loadMask:true,
			cm : sdk_cszxCM,
			sm : sdk_cszxSM,
			tbar : [{
				text:'参数信息',
				anchor:'90%',
				handler:function(){
				    var curTemplate = templateStore.getById(sdkTemplateId);
					if(curTemplate == null || typeof(curTemplate) == 'undefined'||curTemplate=="") {
						Ext.Msg.alert('提示','请编制参数模板信息');
						return;
					}
					tmplist="";
					Ext.Ajax.request({
						url : 'baseapp/templateDetails!queryTemplateDetails.action',
						params : {
							templateId : curTemplate.get('templateId')
						},
						success : function(response) {
							tmplist = Ext.decode(response.responseText).detailList;
							if(tmplist == null||tmplist=="") {
								Ext.Msg.alert('提示','选中模板没有明细信息，为无效模板');
								return;
							}
							Ext.getCmp("powerControl_sdkmbUpdate").setValue(curTemplate.get('templateName'));
							var floatValue = curTemplate.get('floatValue');
							if(floatValue >= 0){
								sdkmbUpdate3.find('name','template.floatValue')[0].setValue(floatValue);
								sdkRadioGroup.setValue(2);
							} else {
								sdkmbUpdate3.find('name','template.floatValue')[0].setValue(-floatValue);
								sdkRadioGroup.setValue(1);
							}
							sdkmbUpdate3.find('name','template.isExec')[0].setValue(curTemplate.get('isExec'));
							
							Ext.each(tmplist,function(tmp){
								if(tmp != null) {
									var i = tmp['id']['sectionNo'];
									var startH = tmp['id']['sectionStart'].substr(0,2);
									var startM = tmp['id']['sectionStart'].substr(3,2);
									var endH = tmp['id']['sectionEnd'].substr(0,2);
									var endM = tmp['id']['sectionEnd'].substr(3,2);
									
									sdkmbUpdate2_array[i-1].find('name','sectionStartH_'+i)[0].setValue(startH);
									sdkmbUpdate2_array[i-1].find('name','sectionStartM_'+i)[0].setValue(startM);
									sdkmbUpdate2_array[i-1].find('name','sectionEndH_'+i)[0].setValue(endH);
									sdkmbUpdate2_array[i-1].find('name','sectionEndM_'+i)[0].setValue(endM);
									sdkmbUpdate2_array[i-1].find('name','id'+i+'.const1')[0].setValue(tmp['id']['const1']=='0'?'':tmp['id']['const1']);
									sdkmbUpdate2_array[i-1].find('name','id'+i+'.const2')[0].setValue(tmp['id']['const2']=='0'?'':tmp['id']['const2']);
									sdkmbUpdate2_array[i-1].find('name','id'+i+'.const3')[0].setValue(tmp['id']['const3']=='0'?'':tmp['id']['const3']);
									sdkmbUpdate2_array[i-1].find('name','id'+i+'.isValid')[0].setValue(tmp['id']['isValid']);
								}
							});
							if(tmplist.length<8) {
								for(var i=tmplist.length+1; i <9 ; i++) {
									sdkmbUpdate2_array[i-1].find('name','sectionStartH_'+i)[0].setValue("");
									sdkmbUpdate2_array[i-1].find('name','sectionStartM_'+i)[0].setValue("");
									sdkmbUpdate2_array[i-1].find('name','sectionEndH_'+i)[0].setValue("");
									sdkmbUpdate2_array[i-1].find('name','sectionEndM_'+i)[0].setValue("");
									sdkmbUpdate2_array[i-1].find('name','id'+i+'.const1')[0].setValue("");
									sdkmbUpdate2_array[i-1].find('name','id'+i+'.const2')[0].setValue("");
									sdkmbUpdate2_array[i-1].find('name','id'+i+'.const3')[0].setValue("");
									sdkmbUpdate2_array[i-1].find('name','id'+i+'.isValid')[0].setValue( false);
								}
							}
							sdkmbUpdate.show();
						}
					});
        		}
			},{
				anchor:'90%',
	  			text:'参数下发',
	  			handler:function(){
				 	var curTemplate = templateStore.getById(sdkTemplateId);
					if(curTemplate == null || typeof(curTemplate) == 'undefined'||curTemplate=="") {
						Ext.Msg.alert('提示','请编制参数模板信息');
						return;
					}
					var users = sdk_cszxSM.getSelections();
		     		if(null == users || users.length==0){
			     	    Ext.MessageBox.alert("提示","请选择用户！");
			     	    return;
			     	}
			     	ctrlCheckStaff(sdkSend,'query');
	  			}
	  		},{
				anchor:'90%',
	  			text:'参数召测',
	  			handler:function(){
	  				var users = sdk_cszxSM.getSelections();
  		     		if(null == users || users.length==0){
	  		     	    Ext.MessageBox.alert("提示","请选择用户！");
	  		     	    return;
	  		     	}
	  		     	ctrlCheckStaff(sdkFetch,'query');
	  		     }
	  	},periodSchemeExecButton,periodSchemeReleaseButton,{xtype: 'tbfill'},{
					xtype : 'checkbox',
					id : 'sdkGsselectAllcb',
					boxLabel : '全选',
					name : 'gsselectAllcb',
					checked : false,
					listeners : {
						'check' : function(r, c) {
							if (c) {
								sdk_cszxSM.selectAll();
								lockGrid('sdk');
							}else {
								unlockGrid('sdk');
								sdk_cszxSM.clearSelections();
							}
						}
					}
				}, '-',{
					text : '删除选中用户',
					iconCls: 'cancel',
					handler : function() {
							if (Ext.getCmp('sdkGsselectAllcb').checked) {
								sdk_cszxStore.removeAll(true);
								unlockGrid('sdk');
								Ext.getCmp('sdkGsselectAllcb').setValue(false);
							} else {
								var recs = sdk_cszxSM.getSelections();
								for (var i = 0; i < recs.length; i = i + 1) {
									sdk_cszxStore.remove(sdk_cszxStore.getById(recs[i].data.keyId));
								}
							}
							splite_sdk_cszxStore.proxy = new Ext.ux.data.PagingMemoryProxy(sdkStoreToArray(sdk_cszxStore));
							splite_sdk_cszxStore.load({
										params : {
											start : 0,
											limit : DEFAULT_PAGE_SIZE
										}
							});
							}
						}, '-', {
							iconCls: 'plus',
							text : '加入群组',
							handler:function(){
								var groupTmnlArray = new Array();
								if (Ext.getCmp('sdkGsselectAllcb').checked) {
									for (var i = 0; i < sdk_cszxStore.getCount(); i++) {
										var tmnl = sdk_cszxStore.getAt(i).get('consNo')
												+ '`'+ sdk_cszxStore.getAt(i).get('tmnlAssetNo');
										groupTmnlArray[i] = tmnl;
									}
								} else {
									var recs = sdk_cszxSM.getSelections();
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
									if (Ext.getCmp('sdkGsselectAllcb').checked) {
										Ext.getCmp('sdkGsselectAllcb').setValue(false);
										sdk_cszxSM.selectAll();
									}
								}
			                 }
						}, '-', {
							 iconCls: 'minus',
							 text : '删除成功用户',
							 handler:function(){
					     		for (var i = sdk_cszxStore.getCount()-1; i >=0; i--) {
					     			var execFlag = sdk_cszxStore.getAt(i).get('execFlag');
					     			if("2"==execFlag || "1"==execFlag ||"0"==execFlag)
										sdk_cszxStore.removeAt(i);
								}
								splite_sdk_cszxStore.proxy = new Ext.ux.data.PagingMemoryProxy(sdkStoreToArray(sdk_cszxStore));
								splite_sdk_cszxStore.load({
											params : {
												start : 0,
												limit : DEFAULT_PAGE_SIZE
											}
								});
								if(Ext.getCmp('sdkGsselectAllcb').checked) {
									Ext.getCmp('sdkGsselectAllcb').setValue(false);
									sdk_cszxSM.selectAll();
								}
							 }
						}],
			bbar : new Ext.ux.MyToolbar( {
					store : splite_sdk_cszxStore,
					allStore : sdk_cszxStore,
					enableExpAll : true, // excel导出全部数据
					expAllText : "全部",
					enableExpPage : true, // excel仅导出当前页
					expPageText : "当前页"
			})	
	});
	
	var sdkExec = function sdkExec(){
        var users = sdk_cszxSM.getSelections();
        //获取方案号
     	var schemeCurveNo = constcombo.getValue();
     	// 获取有效时段
     	var validSection = executeCheckbox.getValue();
     	
//  	将有效时段号组成逗号分隔的字符串
     	var validSectionNo = validSection[0].getRawValue();
        for(var i = 1; i < validSection.length; i++){
        	validSectionNo =validSectionNo +','+validSection[i].getRawValue();
        }
     	
        var pwrCtrlList = new Array();//向后台传的参数数组
     	var execNum = 0;//过滤终端的个数
     	var pwrCtrlListNum = 0;//pwrCtrlList的长度 用于自加
     	if (Ext.getCmp('sdkGsselectAllcb').checked) { //如果选中全选按钮
			for (var i = 0; i < sdk_cszxStore.getCount(); i++) {
				if(sdk_cszxStore.getAt(i).data.ctrlFlag !='2' || sdk_cszxStore.getAt(i).data.tmnlPaulPower =='是'){
					 sdk_cszxStore.getAt(i).set('execFlag','-9');
	     			 execNum++;
	     			 continue;
	     		}
				pwrCtrlList[pwrCtrlListNum++] =sdk_cszxStore.getAt(i).data.orgNo +'`' + sdk_cszxStore.getAt(i).data.consNo +
					'`' + sdk_cszxStore.getAt(i).data.tmnlAssetNo + '`' + sdk_cszxStore.getAt(i).data.totalNo + '`' + sdk_cszxStore.getAt(i).data.protocolCode;
			}
			sdk_cszxStore.commitChanges();
     	} else {
     		for(var i = 0; i < users.length; i++){
	     		if(users[i].get('ctrlFlag')!='2' || users[i].get('tmnlPaulPower')=='是'){
	     			 users[i].set('execFlag','-9');
	     			 execNum++;
	     			 continue;
	     		}
				pwrCtrlList[pwrCtrlListNum++] = users[i].get('orgNo')+'`'+users[i].get('consNo')+'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');
       		}
       		sdk_cszxStore.commitChanges();
     	} 
        if(pwrCtrlList.length==0){
        	Ext.MessageBox.alert("提示",'没有符合执行条件的终端');
        	return;
        }
        if(execNum!= 0) {
        	Ext.MessageBox.alert("提示",execNum+"个终端的总加组未下发参数或被保电或已投入时段控, 不能投入，将被自动过滤");
        }
       var ov = true;
       var second = Ext.Ajax.timeout/1000;
        h_taskTime(second,function(){
        	ov = false;
        });
		Ext.Ajax.request({
	     		url:'./baseapp/powerControl!sdkExec.action',
	     		params : {
	     			second:second,
	     			pwrCtrlList: pwrCtrlList,
	     			schemeCurveNo:schemeCurveNo,
	     			validSectionNo:validSectionNo
	     		},
	     		success : function(response){
	     			var cacheAndTmnlStatus = Ext.decode(response.responseText).cacheAndTmnlStatus;
	     			if(cacheAndTmnlStatus!=""&& cacheAndTmnlStatus!=null){
	     				Ext.MessageBox.alert("提示",cacheAndTmnlStatus);
	     				return;
	     			}
	     			if(!ov) return;
	     			var result = Ext.decode(response.responseText).pwrctrlResultList;
	     			if(result== null || result.length<=0) {
	     				Ext.MessageBox.alert("提示","时段控执行失败，全部终端无应答");
	     		  		return;
	     			}
	     			sdkSendCtrlResult(result, users);//调用下发结果处理函数
	     			
	     			overFlat = true;
	     			Ext.Ajax.timeout = 30000;
	     		},
	     		failure : function(){
	     			if(!ov) return ;
	     			overFlat = true;
	     			Ext.Ajax.timeout = 30000;
	     			Ext.MessageBox.alert("提示","失败");
	     		}
	     	});
	};
	
	var sdkRelease = function sdkRelease(){
        var users = sdk_cszxSM.getSelections();
     	var pwrCtrlList = new Array();//向后台传的参数数组
     	var execNum = 0;//过滤终端的个数
     	var pwrCtrlListNum = 0;//pwrCtrlList的长度 用于自加
     	if (Ext.getCmp('sdkGsselectAllcb').checked) { //如果选中全选按钮
			for (var i = 0; i < sdk_cszxStore.getCount(); i++) {
				if(sdk_cszxStore.getAt(i).data.ctrlFlag !="1"){
					 sdk_cszxStore.getAt(i).set('execFlag','-9');
	     			 execNum++;
	     			 continue;
	     		}
				pwrCtrlList[pwrCtrlListNum++] =sdk_cszxStore.getAt(i).data.orgNo +'`' + sdk_cszxStore.getAt(i).data.consNo +
					'`' + sdk_cszxStore.getAt(i).data.tmnlAssetNo + '`' + sdk_cszxStore.getAt(i).data.totalNo + '`' + sdk_cszxStore.getAt(i).data.protocolCode;
			}
			sdk_cszxStore.commitChanges();
     	} else {
     		for(var i = 0; i < users.length; i++){
	     		if(users[i].get('ctrlFlag') !="1"){
	     			 users[i].set('execFlag','-9');
	     			 execNum++;
	     			 continue;
	     		}
				pwrCtrlList[pwrCtrlListNum++] = users[i].get('orgNo')+'`'+users[i].get('consNo')+'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');
       		}
       		sdk_cszxStore.commitChanges();
     	}
     	if(pwrCtrlList.length <=0) {
     		Ext.MessageBox.alert("提示",'没有符合解除条件的终端');
     		return ;
     	}
        if(execNum!= 0) {
        	Ext.MessageBox.alert("提示",execNum+"个终端的总加组不在控制状态，不能解除，将被自动过滤");
        }
     	var ov = true;
     	var second = Ext.Ajax.timeout/1000;
        h_taskTime(second,function(){
        	ov = false;
        });
    	Ext.Ajax.request({
     		url:'./baseapp/powerControl!sdkRelease.action',
     		params : {
     			second:second,
     			pwrCtrlList:pwrCtrlList
     		},
     		success : function(response){
     			var cacheAndTmnlStatus = Ext.decode(response.responseText).cacheAndTmnlStatus;
     			if(cacheAndTmnlStatus!=""&& cacheAndTmnlStatus!=null){
     				Ext.MessageBox.alert("提示",cacheAndTmnlStatus);
     				return;
     			}
     			if(!ov) return;
     			var result = Ext.decode(response.responseText).pwrctrlResultList;
     			if(result== null || result.length<=0) {
     				Ext.MessageBox.alert("提示","时段控执行失败，全部终端无应答");
     		  		return;
     			}
     			sdkSendCtrlResult(result,users);//调用下发结果处理函数
     			Ext.Ajax.timeout = 30000;
     			overFlat = true;
     		},
     		failure : function(){
     			if(!ov) return;
     			overFlat = true;
     			Ext.Ajax.timeout = 30000;
     			Ext.MessageBox.alert("提示","失败");
     		}
     	});
    };
	
		var sdk_zcStore = new Ext.data.Store({
				reader : new Ext.data.ArrayReader({
					        idIndex: 0
					}, [{name : 'keyId'},
					{name : 'orgName'},
					{name : 'orgNo'},
					{name : 'consNo'},
					{name : 'consName'},
					{name : 'terminalAddr'},
					{name : 'tmnlAssetNo'},
					{name : 'totalNo'},
					{name : 'pwrDetail'},
					{name:'floatValue'},
					{name : 'message'}])
			});

	var sdk_zcSM = new Ext.grid.CheckboxSelectionModel();
	var rowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(sdk_zcStore && sdk_zcStore.lastOptions && sdk_zcStore.lastOptions.params){
				startRow = sdk_zcStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	var sdk_zcCM = new Ext.grid.ColumnModel([rowNum, sdk_zcSM, 
			//{header : '选择',	dataIndex : 'keyId',sortable : true,hidden:true,	align : 'center'}, 	
			{header : '供电单位',dataIndex : 'orgName',sortable : true,renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			},align : 'center',defaultWidth:140}, 
			{header : '用户编号',dataIndex : 'consNo',sortable : true,	align : 'center'}, 
			{header : '用户名称',dataIndex : 'consName',sortable : true,rrenderer : function(val) {
				var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			},align : 'center'}, 
			{header : '终端地址',dataIndex : 'terminalAddr',sortable : true,renderer : function (val) {
						 return "<div align = 'left'>"+ val + "</div>";
						 },align : 'center'	}, 
			{header : '总加组号',dataIndex : 'totalNo',sortable : true,	align : 'center'}, 
			{header : '浮动系数',dataIndex : 'floatValue',sortable : true,	align : 'center'}, 
			{header : '时段定值',dataIndex : 'pwrDetail',sortable : true,renderer: function(s, m, rec){
				    zcDetail = rec.get('pwrDetail');
					return "<a href='javascript:'onclick='showPwrFetchDetail();" + "'>查看明细</a>"; 
				},align : 'center'	},
			{header : '报文',dataIndex : 'message',	sortable : true,align : 'center',renderer: function(s, m, rec){
					return "<a href='javascript:'onclick='origFrameQryShow(\""+rec.get('consNo')+"\",\""+ rec.get('consName')+"\",\""+ rec.get('terminalAddr')+"\");" + "'>查看</a>"; 
			}}]);
	
	var sdk_zcGrid = new Ext.grid.GridPanel({
				store : sdk_zcStore,
				title : '时段控参数召测列表',
				cm : sdk_zcCM,
				sm : sdk_zcSM,
//				tbar : [{xtype: 'tbfill'},{
//							handler : function() {
//							    var recs = sdk_zcSM.getSelections();
//							    for (var i = 0; i < recs.length; i = i + 1) {
//								    sdk_zcStore.remove(recs[i]);
//							    }
//						    },
//							text : '删除选中行',
//							  iconCls: 'minus'
//						}, '-', {
//							iconCls: 'plus',
//							text : '加入群组',
//							handler:function(){
//		                 	saveOrUpdateGroupWindowShow(sdk_zcSM);
//		                 }
//						}, '-', {
//							 iconCls: 'minus',
//							text : '删除成功用户'
//						}],
				bbar : new Ext.ux.MyToolbar({
					store : sdk_zcStore,
					enableExpAll : true, // excel导出全部数据
				    expAllText : "全部",
					enableExpPage: true, // excel仅导出当前页
					expPageText : "当前页"
				})
			});		
			
			
	var sdkGrid = new Ext.TabPanel({
				activeTab : 0,
				region : 'center',
				frame : true,
				border : false,
				items : [sdk_cszxGrid, sdk_zcGrid]
			});		
			
	var periodRadio_down = new Ext.Panel({
		border:false,
		layout:'border',
		region:'center',
		items:[periodExePanel,sdkGrid]
	});
			
	var periodRadio = new Ext.Panel({     				//时段控radio内容
		id:'pwrctrl_sdk',
		border:false,
		layout:'border',
//		items:[periodPanel,periodRadio_down]
		items:[periodRadio_down]
	});		
	sdk_cszxStore.on('load',function(thisstore, recs, obj){
		splite_sdk_cszxStore.proxy = new Ext.ux.data.PagingMemoryProxy(sdkStoreToArray(thisstore));
		splite_sdk_cszxStore.load({
				params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
			});
		sdk_cszxSM.selectAll();//默认全选
		sdk_cszxGrid.doLayout();
	});
	
	splite_sdk_cszxStore.on('load',function(){
		sdk_cszxSM.selectAll();
	});
	
	var panel_low = new Ext.Panel({
				region : 'center',
				layout : 'card',
				activeItem : 0,
				border : false,
				items : [periodRadio]
	});
	
	var viewPanel = new Ext.Panel({
		layout:'border',
		border:false,
		items:[panel_low]
//		items:[floatPanel]
	});
	
	if(typeof(staticPeriodExeRelFlag) != 'undefined'){
		if(staticPeriodExeRelFlag==0){
			periodSchemeExecButton.setVisible(true);
			periodSchemeReleaseButton.setVisible(false);
		}
		else if(staticPeriodExeRelFlag==1){
			periodSchemeExecButton.setVisible(false);
			periodSchemeReleaseButton.setVisible(true);
		}	
	}
	
	renderModel(viewPanel,'功率时段控');
	
	if(typeof(staticPeriodCtrlSchemeId) != 'undefined') {
		templateStore.load();
		sdk_cszxStore.removeAll();
   	    sdk_cszxStore.baseParams = {
   	   	   schemeId : staticPeriodCtrlSchemeId,
   	   	   nodeType:'schemeId',
   	   	   add:true
   	   };
   	   Ext.getBody().mask("加载中..."); 
   	   sdk_cszxStore.load({
			params:{start:0,limit:DEFAULT_PAGE_SIZE},
			callback: function(records, options, success){
               if(null != records && 0 < records.length){
               		Ext.Ajax.request({
						url : "./baseapp/pwrctrlLoadScheme!pwrctrlLoadScheme.action",
						params : {
							schemeId : staticPeriodCtrlSchemeId
						},
						success : function(response) {
							var records = Ext.decode(response.responseText);
                       		sdkTemplateId=records.templateId;
							var schemeCurveNo = records.schemeCurveNo;
							var validSectionNo = records.validSectionNo;
                       		
							var length = records.secLength;//获取模板明细有几个时段
							executeCheckbox.eachItem(function(obj){//循环checkbox将其初始化
								//obj.setDisabled(false);
								obj.setValue(false);
							});
							/*var flag = 0;
							executeCheckbox.eachItem(function(obj){
								if(flag++ >= length){
									obj.setDisabled(true);//将大于模板时段数的复选框灰掉
								}
							});*/
                       		executeCheckbox.setValue(validSectionNo);
							constdata=[];
							//var templateName = records.templateName;
                       		//templateNameComboBox.setValue(templateName);
                       		constdata[0] = [schemeCurveNo , "功率定值"+schemeCurveNo];
                       		constcombo.getStore().loadData(constdata);
                       		constcombo.setValue(schemeCurveNo);

//							curTemplate = templateStore.getById(records.templateName);
//							if(curTemplate ==null ||curTemplate==''){
//								return;
//							}
//							sdkTemplateDetail();
                       		Ext.getBody().unmask();
						}
					});
               }
               else{
            	    executeCheckbox.setValue();
              		executeCheckbox.setDisabled(true);
              		//templateNameComboBox.setValue("");
              		constdata=[];
					constcombo.getStore().loadData(constdata);
              		constcombo.setValue("");
              		sdkTemplateId="";
              		Ext.getBody().unmask();
               }
           },
           add:true
   	   });
	}
	
	//设置激活标志，为0不加载数据，为1加载数据
	staticPeriodExeRelActivate=0;
	
	Ext.getCmp('功率时段控').on('activate', function() {
		if(staticPeriodExeRelActivate==1){
			//设置方案名为标题
			if(typeof(staticPeriodCtrlSchemeName) != 'undefined'){
				periodExePanel.setTitle('方案名：'+staticPeriodCtrlSchemeName);
			}
			if(staticPeriodExeRelFlag==0){
				periodSchemeExecButton.setVisible(true);
				periodSchemeReleaseButton.setVisible(false);
			}
			else if(staticPeriodExeRelFlag==1){
				periodSchemeExecButton.setVisible(false);
				periodSchemeReleaseButton.setVisible(true);
			}
	 		if(typeof(staticPeriodCtrlSchemeId) != 'undefined'){
				templateStore.load();
				sdk_cszxStore.removeAll();
		   	    sdk_cszxStore.baseParams = {
		   	   	   schemeId : staticPeriodCtrlSchemeId,
		   	   	   nodeType:'schemeId',
		   	   	   add:true
		   	   };
		   	   Ext.getBody().mask("加载中..."); 
		   	   sdk_cszxStore.load({
					params:{start:0,limit:DEFAULT_PAGE_SIZE},
					callback: function(records, options, success){
		               if(null != records && 0 < records.length){
		               		Ext.Ajax.request({
								url : "./baseapp/pwrctrlLoadScheme!pwrctrlLoadScheme.action",
								params : {
									schemeId : staticPeriodCtrlSchemeId
								},
								success : function(response) {
									var records = Ext.decode(response.responseText);
									sdkTemplateId=records.templateId;
									var schemeCurveNo = records.schemeCurveNo;
									var validSectionNo = records.validSectionNo;
		                       		
									var length = records.secLength;//获取模板明细有几个时段
									executeCheckbox.eachItem(function(obj){//循环checkbox将其初始化
										//obj.setDisabled(true);
										obj.setValue(false);
									});
									/*var flag = 0;
									executeCheckbox.eachItem(function(obj){
										if(flag++ >= length){
											obj.setDisabled(true);//将大于模板时段数的复选框灰掉
										}
									});*/
		                       		executeCheckbox.setValue(validSectionNo);
		                       		
									constdata=[];
		                       		//templateNameComboBox.setValue(records.templateName);
		                       		constdata[0] = [schemeCurveNo , "功率定值"+schemeCurveNo];
		                       		constcombo.getStore().loadData(constdata);
		                       		constcombo.setValue(schemeCurveNo);
		                       		
//									curTemplate = templateStore.getById(records.templateName);
//									if(curTemplate ==null ||curTemplate==''){
//										return;
//									}
//									sdkTemplateDetail();
		                       		Ext.getBody().unmask();
								}
							});
		               }
		               else{
		               		executeCheckbox.setValue();
		               		executeCheckbox.setDisabled(true);
		               		//templateNameComboBox.setValue("");
		               		constdata=[];
							constcombo.getStore().loadData(constdata);
                       		constcombo.setValue("");
                       		sdkTemplateId="";
                       		Ext.getBody().unmask();
		               }
		           },
		           add:true
		   	   });
	 		}
		}
		staticPeriodExeRelActivate=0;
 	});
});
