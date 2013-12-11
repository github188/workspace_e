

function origFrameQryShow(consNo,consName,tmnlAssetAddr){
	staticConsNo = consNo;
	staticConsName = consName;
	staticTmnlAssetAddr = tmnlAssetAddr;
	openTab("原始报文查询","./baseApp/dataGatherMan/origFrameQry.jsp");
}
	
Ext.onReady(function(){
	//var cxkCurTemplate="";//选中的时段控模板变量，在下拉列表选择时赋值
	//用于设置模板Id
	var cxkTemplateId="";
	
	// grid解锁
	function unlockGrid(tab) {//取消全选时，解锁Grid
		if (tab =="cxk"){//功率Grid
			cxk_cszxSM.unlock();
			cxk_cszxGrid.onEnable();
			cxk_cszxGrid.getBottomToolbar().enable();
			Ext.getCmp('cxkGsselectAllcb').setValue(false);
		}
	}

	// grid锁定
	function lockGrid(tab) {
		if (tab =="cxk"){
			cxk_cszxSM.lock();
			cxk_cszxGrid.onDisable();
			cxk_cszxGrid.getBottomToolbar().disable();
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
	   downWTimeText:'该项值必须为1-60之间的整数',
	   freezeTimeText:'该项值必须为5-60之间的整数',
	   hourTimeText:'该项只能填入0-23之间的整数', 
	   minTimeText:'该项只能填入0或30',
	   factoryConstText:'厂休控定值超出实际范围',
	   continueHoursText:'延续时间为0-48内，间隔半小时的数字',
	   floatValueText: '浮动系数必须是0-100之间的数字'
	});

//---------------------------------------厂休控 panel  开始
	var cxkmb1 = new Ext.Panel({
	  	layout:'column',
	  	region:'north',
	  	height:50,
	  	bodyStyle:'padding: 15px 0px',
	  	items:[{
				columnWidth:.6,
				labelAlign:'right',
				layout:'form',
				labelSeparator:' ',
				border:false,
				items:[{
					xtype:'textfield',
					id:'powerControl_facRestTemplateName',
					fieldLabel:'模板名称',
					//allowBlank:false,
					emptyText:'模板名称',
					readOnly:true,
					width:180
					}]
			},{
				columnWidth:.15,
				border:false,
				layout:'form',
				items:[{
					xtype:'button',
					handler:function(){
						cxkmb.hide(); 
					},
					text:'关闭'
			}]
		}]
	});
	
	var cxkmb2_1 = new Ext.Panel({
				border : false,
				layout : 'column',
				bodyStyle:'padding: 10px 0px',
				items : [{
							columnWidth : .45,
							border : false,
							layout : 'form',
							labelSeparator : ' ',
							allowBlank:false,
							labelAlign : 'right',
							items : [{
										xtype : 'textfield',
										name :'template.factoryConst',
										fieldLabel : '厂休控定值',
										vtype:'factoryConst',
									    readOnly:true,
										width : 150
									}]
						}, {
							columnWidth : .1,
							xtype : 'label',
							text : 'kW'
						},{
							xtype:'hidden',
							name :'template.templateName'
						}]
			});
			
	var cxkmb2_2 = new Ext.Panel({
				border : false,
				layout : 'column',
				bodyStyle:'padding: 5px 0px',
				items : [{
							columnWidth : .45,
							border : false,
							layout : 'form',
							labelSeparator : ' ',
							allowBlank:false,
							labelAlign : 'right',
							items : [{
										xtype : 'textfield',
										name :'template.continueHours',
										fieldLabel : '延续时间',
										vtype:'continueHours',
										readOnly:true,
										width : 150
									}]
						}, {
							columnWidth : .1,
							xtype : 'label',
							text : '小时'
						}]
			});
	
	var weekDaysCheck = new Ext.form.CheckboxGroup({
		fieldLabel : '限电日',
		disabled:true,
		items : [{
					name : '1',
					boxLabel : '周一',
					inputValue:'1'
				}, {
					name : '2',
					boxLabel : '周二',
					inputValue:'2'
				}, {
					name : '3',
					boxLabel : '周三',
					inputValue:'3'
				}, {
					name : '4',
					boxLabel : '周四',
					inputValue:'4'
				}, {
					name : '5',
					boxLabel : '周五',
					inputValue:'5'
				}, {
					name : '6',
					boxLabel : '周六',
					inputValue:'6'
				}, {
					name : '7',
					boxLabel : '周日',
					inputValue:'7'
				}]
	});
	var cxkmb2_3 = new Ext.Panel({
				layout : 'form',
				border : false,
				bodyStyle:'padding: 5px 0px',
                width:500,
				labelAlign : 'right',
				labelSeparator : ' ',
				items : [weekDaysCheck,{
					xtype:'hidden',
					name :'template.weekDays'
				}]
			});
			
	var cxkmb2_4 = new Ext.Panel({               //开始时间增减按钮！！！！！！
		border:false,
		layout:'column',
		items:[{
					xtype:'hidden',
					name :'template.ctrlTime'
				},{
					columnWidth:.42,
					border:false,
					labelSeparator:' ',
					layout:'form',
					labelAlign:'right',
					items:[
						 new Ext.ux.form.SpinnerField({
								fieldLabel:'开始时间',
								name :'ctrlTimeH',
								minValue: 0,
								maxValue: 23,
								allowDecimals: true,
								decimalPrecision: 1,
								incrementValue: 1,
								vtype:'hourTime',
								accelerate: true,
								readOnly:true
            			})
					]
				},{
					columnWidth:.4,
					border:false,
					labelSeparator:' ',
					layout:'form',
					labelWidth:6,
					items:[
						 new Ext.ux.form.SpinnerField({
								fieldLabel:':',
								minValue: 0,
								maxValue: 30,
								name :'ctrlTimeM',
								allowDecimals: true,
								decimalPrecision: 1,
								incrementValue: 30,
								vtype:'minTime',
								accelerate: true,
								readOnly:true
            			})
					]
				}]
	});
	
	var cxkmb2 = new Ext.FormPanel({
		layout:'form',
		title:'模板参数',
		region:'center',
		items:[cxkmb2_1,cxkmb2_2,cxkmb2_3,cxkmb2_4]
	});
	
	var cxkmb = new Ext.Window({
       title:'厂休控模板信息',
       name:'cxkmb',
       modal:true,
       height:400,
       width:650,
       closeAction:'hide',
	   resizable:false,
	   layout:'border',
	   items:[cxkmb1,cxkmb2]
    });
	
//---------------------厂休控   模板信息 按钮   结束
	var cxkTemplateStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url : 'baseapp/cxkTemplateList!queryTemplateList.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'templateList',
				idProperty: 'templateId'
			}, [{name:'ctrlTime'},{name:'templateName'},{name:'templateId'},
				{name:'weekDays'},{name:'factoryConst'},{name:'continueHours'}])
	});
	var cxkTemplateNameComboBox = new  Ext.form.ComboBox({
		store : cxkTemplateStore,
		xtype:'combo',
		name :'combo_templateName',
		fieldLabel:'模板名称',
		valueField : 'templateId',
		editable : false,
		triggerAction : 'all',
		forceSelection : true,
		mode : 'remote',
		selectOnFocus : true,
		displayField : 'templateName',
		emptyText:'功率厂休控',
		anchor:'85%'
	});
	cxkTemplateStore.on('load',function(){
		cxkTemplateNameComboBox.setValue(cxkTemplateId);
	});
	//参数下发，控制下发，控制解除返回结果赋值处理函数
	var cxkSendCtrlResult = function cxkSendCtrlResult(result, users) {
		if (Ext.getCmp('cxkGsselectAllcb').checked) {//如果选中全选按钮
			cxk_cszxStore.each(function(rec){
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
			cxk_cszxStore.commitChanges();//提交修改
			
			splite_cxk_cszxStore.proxy = new Ext.ux.data.PagingMemoryProxy(cxkStoreToArray(cxk_cszxStore));
			splite_cxk_cszxStore.load({
				params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
			});
		} else {
			for(var i = 0; i < users.length; i++){
				var rec = cxk_cszxStore.getById(users[i].get('keyId'));
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
			cxk_cszxStore.commitChanges();//提交修改
			splite_cxk_cszxStore.proxy = new Ext.ux.data.PagingMemoryProxy(cxkStoreToArray(cxk_cszxStore));
			splite_cxk_cszxStore.load({
				params : {
					start : splite_cxk_cszxStore.lastOptions.params.start,
					limit : DEFAULT_PAGE_SIZE
				}
			});
		}
	};
	
	var cxkSend = function cxkSend(){
    	var templateName = cxkTemplateNameComboBox.getRawValue();
        var users = cxk_cszxSM.getSelections();
     	var pwrCtrlList = new Array();//向后台传的参数数组
     	var execNum = 0;//过滤终端的个数
     	var pwrCtrlListNum = 0;//pwrCtrlList的长度 用于自加
     	if (Ext.getCmp('cxkGsselectAllcb').checked) { //如果选中全选按钮
			for (var i = 0; i < cxk_cszxStore.getCount(); i++) {
				if(cxk_cszxStore.getAt(i).data.tmnlPaulPower =='是' || cxk_cszxStore.getAt(i).data.ctrlFlag =="1"){
					 cxk_cszxStore.getAt(i).set('execFlag','-9');
	     			 execNum++;
	     			 continue;
	     		}
				pwrCtrlList[pwrCtrlListNum++] =cxk_cszxStore.getAt(i).data.orgNo +'`' + cxk_cszxStore.getAt(i).data.consNo +
					'`' + cxk_cszxStore.getAt(i).data.tmnlAssetNo + '`' + cxk_cszxStore.getAt(i).data.totalNo + '`' + cxk_cszxStore.getAt(i).data.protocolCode;
			}
			cxk_cszxStore.commitChanges();
     	} else {
     		for(var i = 0; i < users.length; i++){
	     		if(users[i].get('tmnlPaulPower')=='是' || users[i].get('ctrlFlag')=="1"){
	     			 users[i].set('execFlag','-9');
	     			 execNum++;
	     			 continue;
	     		}
				pwrCtrlList[pwrCtrlListNum++] = users[i].get('orgNo')+'`'+users[i].get('consNo')+'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');
       		}
       		cxk_cszxStore.commitChanges();
     	}
        if(execNum!= 0) {
        	Ext.MessageBox.alert("提示",execNum+"个终端的总加组被保电或已投入厂休控, 不能进行参数下发，将被自动过滤");
        }
        if(pwrCtrlList.length<=0){
        	Ext.MessageBox.alert("提示",'没有符合下发条件的终端');
        	return;
        }
        var ov = true;
        var second = Ext.Ajax.timeout/1000;
        h_taskTime(second, function(){
        	ov = false;
        });
    	Ext.Ajax.request({
     		url:'./baseapp/paramSend!cxkParamSend.action',
     		timeout:60000,
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
     			if(!ov) return;
     			var result = Ext.decode(response.responseText).execResultList;
     			if(result==null ||result.length<=0){
     				Ext.MessageBox.alert("提示","厂休控参数下发失败，终端无应答！");
     		   		return;
     			}
     			cxkSendCtrlResult(result, users);//调用厂休控下发结果处理函数
     			
                overFlat = true;
                Ext.Ajax.timeout = 30000;
     		},
     		failure : function(){
     			if(!ov) return;
     			overFlat = true;
     			Ext.Ajax.timeout = 30000;
     			Ext.MessageBox.alert("提示","厂休控参数下发失败");
     		}
     	});
    };
	var cxkFetch = function cxkFetch(){
        var users = cxk_cszxSM.getSelections();
     	var pwrCtrlList = new Array();//向后台传的参数数组
     	var execNum = 0;//过滤终端的个数
     	cxk_zcStore.removeAll();
     	if (Ext.getCmp('cxkGsselectAllcb').checked) { //如果选中全选按钮
			for (var i = 0; i < cxk_cszxStore.getCount(); i++) {
				pwrCtrlList[i] =cxk_cszxStore.getAt(i).data.orgNo +
					'`' + cxk_cszxStore.getAt(i).data.tmnlAssetNo + '`' + cxk_cszxStore.getAt(i).data.totalNo;
			}
     	} else {
     		for(var i = 0; i < users.length; i++){
				pwrCtrlList[i] = users[i].get('orgNo')+'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo');
       		}
     	}
        var ov = true;
        var second = Ext.Ajax.timeout/1000;
        h_taskTime(second,function(){
        	ov = false;
        });
    	Ext.Ajax.request({
     		url:'./baseapp/cxkParamFecth!cxkParamFecth.action',
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
     			var result = Ext.decode(response.responseText).factctrlResultList;
     			var record = new Array();
     			if (Ext.getCmp('cxkGsselectAllcb').checked) { //如果选中全选按钮
					for (var i = 0; i < cxk_cszxStore.getCount(); i++) {
						record[i] = new Array();
			        	record[i][0] =cxk_cszxStore.getAt(i).data.keyId;
			        	record[i][1] = cxk_cszxStore.getAt(i).data.orgName;
			        	record[i][2] = cxk_cszxStore.getAt(i).data.consNo;
			        	record[i][3] = cxk_cszxStore.getAt(i).data.consName;
			        	record[i][4] = cxk_cszxStore.getAt(i).data.terminalAddr;
			        	record[i][5] = cxk_cszxStore.getAt(i).data.totalNo;
			        	
			        	if(result==null || result.length<=0) continue;//如果返回结果为空，则不对召测结果设值
			        	
			        	for(var j = 0; j < result.length; j++){//循环返回结果list 根据keyId匹配记录，设置返回值
			        		if(result[j].keyId==cxk_cszxStore.getAt(i).data.keyId) {
					        	record[i][6] = result[j].factoryConst;
					        	record[i][7] = result[j].ctrlTime;
					        	record[i][8] = result[j].continueHours;
					        	record[i][9] = result[j].weekDays;
					        	break;
			        		}
			        	}
					}
					cxk_zcStore.proxy = new Ext.ux.data.PagingMemoryProxy(record);
					cxk_zcStore.load({
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
                    	record[i][2] = users[i].get('consNo');
                    	record[i][3] = users[i].get('consName');
                    	record[i][4] = users[i].get('terminalAddr');
                    	record[i][5] = users[i].get('totalNo');
			        	
			        	if(result==null || result.length<=0) continue;//如果返回结果为空，则不对召测结果设值
			        	
			        	for(var j = 0; j < result.length; j++){//循环返回结果list 根据keyId匹配记录，设置返回值
			        		if(result[j].keyId==users[i].get('keyId')) {
					        	record[i][6] = result[j].factoryConst;
					        	record[i][7] = result[j].ctrlTime;
					        	record[i][8] = result[j].continueHours;
					        	record[i][9] = result[j].weekDays;
					        	break;
			        		}
			        	}
			        }
			        cxk_zcStore.loadData(record);
				}
                cxkGrid.setActiveTab(1);//切换到召测结果页面
                overFlat =true;
                Ext.Ajax.timeout = 30000;
     		},
     		failure : function(){
     			alert('failure');
     			if(!ov) return;
	     		overFlat =true;
	     		Ext.Ajax.timeout = 30000;
     			Ext.MessageBox.alert("提示","厂休控参数召测失败");
     		}
     	});
    };
	
//----------------------------------------------------------厂休控执行方案开始---------------------------------------------------------	
//	定义方案加载弹出页面的上部分面板组件
//	定义方案加载时选中的方案ID
	var cxkSchemeIdValue;
	var cxkLoadScheme1=new Ext.Panel({
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
   	           text:'功率厂休控方案加载'
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
   	           	   var loadFuncSelected = cxkLoadScheme2.find("name","multiselect")[0];
   	           	   var selectionsArray = loadFuncSelected.view.getSelectedIndexes();
   	           	   var type = "schemeId";
   	           	   if(0 < selectionsArray.length){
   	           	   	   for(i = 0 ; i < selectionsArray.length; i++){
   	                       cxkSchemeIdValue = cxkLoadStore.getAt(selectionsArray[selectionsArray.length-1]).get('ctrlSchemeId');
   	           	   	   }
   	           	   }
   	           	   cxk_cszxStore.removeAll();
   	           	   cxk_cszxStore.baseParams = {
   	           	   	   schemeId:cxkSchemeIdValue,
   	           	   	   nodeType:type,
   	           	   	   add:true
   	           	   };
   	           	   cxk_cszxStore.load({
   	           	   	   callback: function(records, options, success){
                           if(null != records && 0 < records.length){
		                       	Ext.Ajax.request({
									url : "./baseapp/factctrlLoadScheme!factctrlLoadScheme.action",
									params : {
										schemeId:cxkSchemeIdValue
									},
									success : function(response) {
										var records = Ext.decode(response.responseText);
		                           		cxkTemplateNameComboBox.setValue(records.templateName);
									}
		                       });
                           }
                       } , 
                       add:true
   	           	   });
   	               cxkLoadSchemeWindow.hide();
  		       }
   	       }] 
   	    }]
    });
    
    /*Json对象*/
    var cxkLoadStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url:'./baseapp/pwrctrlScheme!cxkLoadScheme.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'schemeList'
			}, [{name:'ctrlSchemeId'},
				{name:'ctrlSchemeName'},
				{name:'ctrlSchemeType'}])
	});
// 	定义方案加载弹出页面的下部分面板组件
    var cxkLoadScheme2 =new Ext.Panel({
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
                store: cxkLoadStore
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
   	           	   cxkLoadSchemeWindow.hide();
  		       }
   	       }] 
   	    }]
    });

//  方案加载窗口
    var cxkLoadSchemeWindow = new Ext.Window({
        name:'cxkLoadSchemeWindow',
        height:250,
        width:400,
        closeAction:'hide',
        resizable:false,
        layout:'border',
        title:'方案加载',
        items:[cxkLoadScheme1,cxkLoadScheme2]
    }); 
 //------------------------------------------方案加载结束----------------------------------------------
    
	var executePanel1 = new Ext.FormPanel({
		border:false,
		labelAlign:'right',
		labelWidth:70,
		bodyStyle:'padding: 10px 0px 0px 0px',
		labelSeparator:'',
		items:[{
			xtype:'checkboxgroup',
			fieldLabel : '消息发送',
			width:500,
			items:[{
					boxLabel : '下发成功后短信通知客户'
				}, {
					boxLabel : '方案到期后自动通知'
				}, {
					boxLabel : '下发成功后自动短信订阅'
			}]
		}]
	});
	
	//厂休执行方案 panel
	var facRestExePanel = new Ext.Panel({
		   title:'方案名：',
		   layout:'form',
		   region:'north',
		   height:70,
		   items:[executePanel1]
	});
	//设置方案名为标题
	if(typeof(staticFactoryCtrlSchemeName) != 'undefined'){
		facRestExePanel.setTitle('方案名：'+staticFactoryCtrlSchemeName);
	}
//----------------------------------------------------------厂休控-执行方案   结束-------------------------------------------------------	
		
//------------------------------------------------------厂休控参数执行-召测列表    开始	
	var cxk_cszxStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url:'./baseapp/pwrctrlSchemeExec!queryFactctrlSchemeExec.action'
			}),
			reader : new Ext.data.JsonReader({
				root : 'pwrctrlSchemeExecList',
				idProperty: 'keyId'
			}, [{name : 'orgName'},
				{name : 'orgNo'},
				{name : 'keyId'},
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
	var splite_cxk_cszxStore = new Ext.data.Store({
			remoteSort : true,
			proxy : new Ext.data.MemoryProxy(),
			reader : new Ext.data.ArrayReader({
				idIndex : 2,
				fields :  [
					{name : 'orgName'},
					{name : 'orgNo'},
					{name : 'keyId'},
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

	function cxkStoreToArray(valStore) {
		var data = new Array();
		for (var i = 0; i < valStore.getCount(); i++) {
			data[i] = new Array();
			var index = 0;
			data[i][index++] = valStore.getAt(i).data.orgName;
			data[i][index++] = valStore.getAt(i).data.orgNo;
			data[i][index++] = valStore.getAt(i).data.keyId;
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
	
	var cxk_cszxSM = new Ext.grid.CheckboxSelectionModel();
	var rowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(cxk_cszxStore && cxk_cszxStore.lastOptions && cxk_cszxStore.lastOptions.params){
				startRow = cxk_cszxStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	var cxk_cszxCM = new Ext.grid.ColumnModel([rowNum, cxk_cszxSM, 
			//{header : '选择',	 dataIndex : 'keyId', sortable : true,	hidden:true, align : 'center'}, 
			{header : '供电单位', dataIndex : 'orgName', sortable : true, renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			}, align : 'center',defaultWidth:140}, 
			//{header : '供电单位编号', sortable:true, dataIndex:'orgNo', align:'center', hidden:true}, 
//			{header : '终端资产号', 	sortable:true, dataIndex:'tmnlAssetNo', align:'center',hidden:true},
			{header : '用户编号', dataIndex : 'consNo',	sortable : true,	align : 'center'}, 
			{header : '用户名称',	dataIndex : 'consName',	sortable : true,renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			},	align : 'center'}, 
			{header : '终端地址',	dataIndex : 'terminalAddr', sortable : true,renderer : function (val) {
						 return "<div align = 'left'>"+ val + "</div>";
						 }, align : 'center'}, 
			{header : '总加组号', dataIndex : 'totalNo',	sortable : true,align : 'center'}, 
			{header : '保电',	dataIndex : 'tmnlPaulPower',	sortable : true,	align : 'center'}, 
			{header : '控制状态',	dataIndex : 'ctrlFlag',sortable : true, renderer:function(value){
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
			}}]);

	var factorySchemeExecButton = new Ext.Button({
  		text:'方案执行',//厂休控
  		handler:function(){
  			var users = cxk_cszxSM.getSelections();
  			if(null == users || users.length==0){
	     	    Ext.MessageBox.alert("提示","请选择用户！");
	     	    return;
     		}
  			ctrlCheckStaff(cxkExec,'ctrl');
  		}
	});		
	var factorySchemeReleaseButton = new Ext.Button({
	  		text:'方案解除',//厂休控
	  		id:'factorySchemeRelease',
	  		handler:function(){
	  			var users = cxk_cszxSM.getSelections();
	  			if(null == users || users.length==0){
		     	    Ext.MessageBox.alert("提示","请选择用户！");
		     	    return;
	     		}
	  			ctrlCheckStaff(cxkRelease,'ctrl');
	  		}
	});
	var cxk_cszxGrid = new Ext.grid.GridPanel({
				store : splite_cxk_cszxStore,
				title : '厂休控方案执行列表',
//				height:300,
				loadMask:true,
				cm : cxk_cszxCM,
				sm : cxk_cszxSM,
				tbar : [{
					text:'参数信息',
					handler:function(){
						var cxkCurTemplate = cxkTemplateStore.getById(cxkTemplateId);
		
//						设置页面template值
						if(cxkCurTemplate == null ||typeof(cxkCurTemplate) == 'undefined' ||cxkCurTemplate=="") {
							Ext.Msg.alert('提示','请编制参数模板信息');
							return;
						}
						var tmpNameNode = Ext.getCmp('powerControl_facRestTemplateName');
	        			tmpNameNode.setValue(cxkCurTemplate.get('templateName'));
	        			var weekDays ="";
	        			var weekDaysString = cxkCurTemplate.get('weekDays');
	        			for (var i = 0; i < weekDaysString.length-1; i++) {
	        				weekDays += (weekDaysString.substr(i,1) + ',');
	        			}
	        			weekDays += weekDaysString.substr(weekDaysString.length-1,1);
	        			weekDaysCheck.reset();//设置前先重置
	        			if(weekDays !="") {
	        				weekDaysCheck.setValue(weekDays);
	        			}
	        			cxkmb2_1.find('name','template.factoryConst')[0].setValue(cxkCurTemplate.get('factoryConst'));
	        			cxkmb2_2.find('name','template.continueHours')[0].setValue(cxkCurTemplate.get('continueHours'));
	        			cxkmb2_4.find('name','ctrlTimeH')[0].setValue(cxkCurTemplate.get('ctrlTime').substr(0,2));
	        			cxkmb2_4.find('name','ctrlTimeM')[0].setValue(cxkCurTemplate.get('ctrlTime').substr(3,2));
	        			
	        			cxkmb.show(); 
	        		}
			},{
				text:'参数下发',//厂休控参数下发
	  			handler:function(){
					var cxkCurTemplate = cxkTemplateStore.getById(cxkTemplateId);
				
					if(cxkCurTemplate == null ||typeof(cxkCurTemplate) == 'undefined' ||cxkCurTemplate=="") {
						Ext.Msg.alert('提示','请编制参数模板信息');
						return;
					}
			    	var users = cxk_cszxSM.getSelections();
			    	if(null == users || users.length==0){
			     	    Ext.MessageBox.alert("提示","请选择用户！");
			     	    return;
			     	}
	  				ctrlCheckStaff(cxkSend,'query');
	  			}
	  		},{
				text:'参数召测',//厂休控参数召测
	  			handler:function(){
	  				var users = cxk_cszxSM.getSelections();
			    	if(null == users || users.length==0){
			     	    Ext.MessageBox.alert("提示","请选择用户！");
			     	    return;
			     	}
	  				ctrlCheckStaff(cxkFetch,'query');
	  			}
	  		},factorySchemeExecButton,factorySchemeReleaseButton,{xtype: 'tbfill'},{
					xtype : 'checkbox',
					id : 'cxkGsselectAllcb',
					boxLabel : '全选',
					name : 'gsselectAllcb',
					checked : false,
					listeners : {
							'check' : function(r, c) {
								if (c) {
									cxk_cszxSM.selectAll();
									lockGrid('cxk');
								}else {
									unlockGrid('cxk');
									cxk_cszxSM.clearSelections();
								}
							}
						}
					}, '-', {	
						text : '删除选中用户',
						iconCls: 'cancel',
						handler : function() {
							if (Ext.getCmp('cxkGsselectAllcb').checked) {
								cxk_cszxStore.removeAll(true);
								unlockGrid('cxk');
								Ext.getCmp('cxkGsselectAllcb').setValue(false);
							} else {
								var recs = cxk_cszxSM.getSelections();
								for (var i = 0; i < recs.length; i = i + 1) {
									cxk_cszxStore.remove(cxk_cszxStore.getById(recs[i].data.keyId));
								}
							}
							splite_cxk_cszxStore.proxy = new Ext.ux.data.PagingMemoryProxy(cxkStoreToArray(cxk_cszxStore));
							splite_cxk_cszxStore.load({
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
								if (Ext.getCmp('cxkGsselectAllcb').checked) {
									for (var i = 0; i < cxk_cszxStore.getCount(); i++) {
										var tmnl = cxk_cszxStore.getAt(i).get('consNo')
												+ '`'+ cxk_cszxStore.getAt(i).get('tmnlAssetNo');
										groupTmnlArray[i] = tmnl;
									}
								} else {
									var recs = cxk_cszxSM.getSelections();
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
									if (Ext.getCmp('cxkGsselectAllcb').checked) {
										Ext.getCmp('cxkGsselectAllcb').setValue(false);
										cxk_cszxSM.selectAll();
									}
								}
			                 }
						}, '-', {
							iconCls: 'minus',
							text : '删除成功用户',
							handler:function(){
					     		for (var i = cxk_cszxStore.getCount()-1; i >=0; i--) {
					     			var execFlag = cxk_cszxStore.getAt(i).get('execFlag');
					     			if("2"==execFlag || "1"==execFlag ||"0"==execFlag)
										cxk_cszxStore.removeAt(i);
								}
								splite_cxk_cszxStore.proxy = new Ext.ux.data.PagingMemoryProxy(cxkStoreToArray(cxk_cszxStore));
								splite_cxk_cszxStore.load({
											params : {
												start : 0,
												limit : DEFAULT_PAGE_SIZE
											}
								});
								if(Ext.getCmp('cxkGsselectAllcb').checked) {
									Ext.getCmp('cxkGsselectAllcb').setValue(false);
									cxk_cszxSM.selectAll();
								}
							 }
						}],
					bbar : new Ext.ux.MyToolbar({
						store : splite_cxk_cszxStore,
						allStore:cxk_cszxStore,
						enableExpAll : true, // excel导出全部数据
						expAllText : "全部",
						enableExpPage : true, // excel仅导出当前页
						expPageText : "当前页"
					})	
			});
	var cxkExec = function cxkExec(){
        var users = cxk_cszxSM.getSelections();
     	var pwrCtrlList = new Array();//向后台传的参数数组
     	var execNum = 0;//过滤终端的个数
     	var pwrCtrlListNum = 0;//pwrCtrlList的长度 用于自加
     	if (Ext.getCmp('cxkGsselectAllcb').checked) { //如果选中全选按钮
			for (var i = 0; i < cxk_cszxStore.getCount(); i++) {
				if(cxk_cszxStore.getAt(i).data.ctrlFlag !='2' || cxk_cszxStore.getAt(i).data.tmnlPaulPower =='是'){
					 cxk_cszxStore.getAt(i).set('execFlag','-9');
	     			 execNum++;
	     			 continue;
	     		}
				pwrCtrlList[pwrCtrlListNum++] =cxk_cszxStore.getAt(i).data.orgNo +'`' + cxk_cszxStore.getAt(i).data.consNo +
					'`' + cxk_cszxStore.getAt(i).data.tmnlAssetNo + '`' + cxk_cszxStore.getAt(i).data.totalNo + '`' + cxk_cszxStore.getAt(i).data.protocolCode;
			}
			cxk_cszxStore.commitChanges();
     	} else {
     		for(var i = 0; i < users.length; i++){
	     		if(users[i].get('ctrlFlag')!='2' || users[i].get('tmnlPaulPower')=='是'){
	     			 users[i].set('execFlag','-9');
	     			 execNum++;
	     			 continue;
	     		}
				pwrCtrlList[pwrCtrlListNum++] = users[i].get('orgNo')+'`'+users[i].get('consNo')+'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');
       		}
       		cxk_cszxStore.commitChanges();
     	}
        if(pwrCtrlList.length<=0){
        	Ext.MessageBox.alert("提示",'没有符合执行条件的终端');
        	return;
        }
        if(execNum!= 0) {
        	Ext.MessageBox.alert("提示",execNum+"个终端的总加组未下发参数或被保电或已投入厂休控, 不能投入，将被自动过滤");
        }
        var ov = true;
        var second = Ext.Ajax.timeout/1000;
        h_taskTime(second,function(){
        	ov = false;
        });
        
    	Ext.Ajax.request({
     		url:'./baseapp/powerControl!cxkExec.action',
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
     			var result = Ext.decode(response.responseText).execResultList;
     			if(result==null ||result.length<=0){
     				Ext.MessageBox.alert("提示","厂休控执行失败，全部终端无应答！");
     		   		return;
     			}
     			cxkSendCtrlResult(result, users);//调用厂休控下发结果处理函数
     			
     			overFlat =true;
     			Ext.Ajax.timeout = 30000;
     		},
     		failure : function(){
     			if(!ov) return;
     			overFlat =true;
     			Ext.Ajax.timeout = 30000;
     			Ext.MessageBox.alert("提示","失败");
     		}
 		});
	};
	
	var cxkRelease = function (){
        var users= cxk_cszxSM.getSelections();
     	var pwrCtrlList = new Array();//向后台传的参数数组
     	var execNum = 0;//过滤终端的个数
     	var pwrCtrlListNum = 0;//pwrCtrlList的长度 用于自加
     	if (Ext.getCmp('cxkGsselectAllcb').checked) { //如果选中全选按钮
			for (var i = 0; i < cxk_cszxStore.getCount(); i++) {
				if(cxk_cszxStore.getAt(i).data.ctrlFlag !="1"){
					 cxk_cszxStore.getAt(i).set('execFlag','-9');
	     			 execNum++;
	     			 continue;
	     		}
				pwrCtrlList[pwrCtrlListNum++] =cxk_cszxStore.getAt(i).data.orgNo +'`' + cxk_cszxStore.getAt(i).data.consNo +
					'`' + cxk_cszxStore.getAt(i).data.tmnlAssetNo + '`' + cxk_cszxStore.getAt(i).data.totalNo + '`' + cxk_cszxStore.getAt(i).data.protocolCode;
			}
			cxk_cszxStore.commitChanges();
     	} else {
     		for(var i = 0; i < users.length; i++){
	     		if(users[i].get('ctrlFlag') !="1"){
	     			 users[i].set('execFlag','-9');
	     			 execNum++;
	     			 continue;
	     		}
				pwrCtrlList[pwrCtrlListNum++] = users[i].get('orgNo')+'`'+users[i].get('consNo')+'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');
       		}
       		cxk_cszxStore.commitChanges();
     	}
        if(execNum!= 0) {
        	Ext.MessageBox.alert("提示",execNum+"个终端的总加组不在控制状态，不能解除，将被自动过滤");
        }
        var ov = true;
        var second = Ext.Ajax.timeout/1000;
        h_taskTime(second,function(){
        	ov = false;
        });
        if(pwrCtrlList.length<=0){
        	Ext.MessageBox.alert("提示",'没有符合解除条件的终端');
        	return;
        }
    	Ext.Ajax.request({
     		url:'./baseapp/powerControl!cxkRelease.action',
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
     			if(!ov) return ;
     			var result = Ext.decode(response.responseText).execResultList;
     			if(result==null ||result.length<=0){
     				Ext.MessageBox.alert("提示","厂休控解除失败，全部终端无应答！");
     		   		return;
     			}
     			cxkSendCtrlResult(result, users);//调用厂休控下发结果处理函数

     			overFlat =true;
     			Ext.Ajax.timeout = 30000;
     		},
     		failure : function(){
     			if(!ov) return ;
	     		overFlat =true;
	     		Ext.Ajax.timeout = 30000;
     		   Ext.MessageBox.alert("提示","失败");
     		}
     	});
    };
	var cxk_zcStore = new Ext.data.Store({
				reader : new Ext.data.ArrayReader({
					        idIndex: 0
						},[{name:'keyId'},
						{name:'orgName'},
						{name:'consNo'},
						{name:'consName'}, 
						{name:'terminalAddr'}, 
						{name:'totalNo'}, 
						{name:'factoryConst'},
						{name:'ctrlTime'},
						{name:'continueHours'},
						{name:'weekDays'},
						{name:'message'}])
			});

	var cxk_zcSM = new Ext.grid.CheckboxSelectionModel();
	var rowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(cxk_zcStore && cxk_zcStore.lastOptions && cxk_zcStore.lastOptions.params){
				startRow = cxk_zcStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	var cxk_zcCM = new Ext.grid.ColumnModel([rowNum, cxk_zcSM, 
			//{header : '选择',	dataIndex : 'keyId',sortable : true,hidden:true,align : 'center'}, 
			{header : '供电单位',dataIndex : 'orgName',sortable : true,renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			},align : 'center',defaultWidth:140}, 
			{header : '用户编号',dataIndex : 'consNo',sortable : true,	align : 'center'}, 
			{header : '用户名称',dataIndex : 'consName',sortable : true,renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			},align : 'center'}, 
			{header : '终端地址',dataIndex : 'terminalAddr',sortable : true,renderer : function (val) {
						 return "<div align = 'left'>"+ val + "</div>";
						 },align : 'center'}, 
			{header : '总加组号',dataIndex : 'totalNo',sortable : true,	align : 'center'}, 
			{header : '厂休控定值',dataIndex : 'factoryConst',sortable : true,align : 'center'},
			{header : '超始时间',dataIndex : 'ctrlTime',sortable : true,align : 'center'},
			{header : '延续时间',dataIndex : 'continueHours',sortable : true,align : 'center'},
			{header : '限电日',dataIndex : 'weekDays',sortable : true,align : 'center'}, 
			{header : '报文',	dataIndex : 'message',sortable : true,align : 'center',renderer: function(s, m, rec){
					return "<a href='javascript:'onclick='origFrameQryShow(\""+rec.get('consNo')+"\",\""+ rec.get('consName')+"\",\""+ rec.get('terminalAddr')+"\");" + "'>查看</a>"; 
			}}]);

	var cxk_zcGrid = new Ext.grid.GridPanel({
				store : cxk_zcStore,
				title : '厂休控参数召测列表',
//				height:300,
				cm : cxk_zcCM,
				sm : cxk_zcSM,
//				tbar : [{xtype: 'tbfill'},{
//							handler : function() {
//							    var recs = cxk_zcSM.getSelections();
//							    for (var i = 0; i < recs.length; i = i + 1) {
//								    cxk_zcStore.remove(recs[i]);
//							    }
//						    },
//							text : '删除选中行',
//							  iconCls: 'minus'
//						}, '-', {
//							iconCls: 'plus',
//							text : '加入群组',
//							handler:function(){
//		                 	saveOrUpdateGroupWindowShow(cxk_zcSM);
//		                 }
//						}, '-', {
//							 iconCls: 'minus',
//							text : '删除成功用户'
//						}],
				bbar : new Ext.ux.MyToolbar({
					store : cxk_zcStore,
					enableExpAll : true, // excel导出全部数据
				    expAllText : "全部",
					enableExpPage: true, // excel仅导出当前页
					expPageText : "当前页"
				})
			});		
			
	var cxkGrid = new Ext.TabPanel({
				activeTab : 0,
				region : 'center',
				frame : true,
				border : false,
				items : [cxk_cszxGrid, cxk_zcGrid]
	});		
	
	var facRestRadio_down = new Ext.Panel({
		border:false,
		layout:'border',
		region:'center',
		items:[facRestExePanel,cxkGrid]
	});
			
	var facRestRadio = new Ext.Panel({     				//厂休控radio内容
		id:'pwrctrl_cxk',
		border:false,
		layout:'border',
		items:[facRestRadio_down]
	});		
	
//---------------------------------------------------------厂休控参数执行-召测列表    结束			
	cxk_cszxStore.on('load',function(thisstore, recs, obj){
		splite_cxk_cszxStore.proxy = new Ext.ux.data.PagingMemoryProxy(cxkStoreToArray(thisstore));
		splite_cxk_cszxStore.load({
				params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
			});
		cxk_cszxSM.selectAll();//默认全选
		cxk_cszxGrid.doLayout();
	});
	
	splite_cxk_cszxStore.on('load',function(){
		cxk_cszxSM.selectAll();
	});
	
	var panel_low = new Ext.Panel({
				region : 'center',
				layout : 'card',
				activeItem : 0,
				border : false,
				items : [facRestRadio]
	});
	
	var viewPanel = new Ext.Panel({
		layout:'border',
		border:false,
		items:[panel_low]
	});
	
	if(typeof(staticFactoryExeRelFlag) != 'undefined'){
		if(staticFactoryExeRelFlag==0){
			factorySchemeExecButton.setVisible(true);
			factorySchemeReleaseButton.setVisible(false);
		}
		else if(staticFactoryExeRelFlag==1){
			factorySchemeExecButton.setVisible(false);
			factorySchemeReleaseButton.setVisible(true);
		}	
	}
	renderModel(viewPanel,'功率厂休控');
	
	if(typeof(staticFactoryCtrlSchemeId) != 'undefined') {
		cxk_cszxStore.removeAll();
   	    cxk_cszxStore.baseParams = {
   	   	   schemeId : staticFactoryCtrlSchemeId,
   	   	   nodeType:'schemeId',
   	   	   add:true
   	    };
   	    Ext.getBody().mask("加载中...");
   	    cxk_cszxStore.load({
   	   	   callback: function(records, options, success){
               if(null != records && 0 < records.length){
                   	Ext.Ajax.request({
						url : "./baseapp/factctrlLoadScheme!factctrlLoadScheme.action",
						params : {
							schemeId: staticFactoryCtrlSchemeId
						},
						success : function(response) {
							var records = Ext.decode(response.responseText);
							//加载模板信息
							cxkTemplateId=records.templateId;
                       		cxkTemplateStore.load({ 
	                       		callback: function(){
	                       			 Ext.getBody().unmask();
	                       		}
	                       	});
						}
                   });
               }
               else{
            	    cxkTemplateId="";
            	    Ext.getBody().unmask();
               }
           } , 
           add:true
   	   });
	}
	//设置激活标志，为0不加载数据，为1加载数据
	staticFactoryExeRelActivate=0;
	
	Ext.getCmp('功率厂休控').on('activate', function() {
		if(staticFactoryExeRelActivate==1){
			//设置方案名为标题
			if(typeof(staticFactoryCtrlSchemeName) != 'undefined'){
				facRestExePanel.setTitle('方案名：'+staticFactoryCtrlSchemeName);
			}
	 		if(typeof(staticFactoryCtrlSchemeId) != 'undefined'){
	 			if(staticFactoryExeRelFlag==0){
					factorySchemeExecButton.setVisible(true);
					factorySchemeReleaseButton.setVisible(false);
				} else if(staticFactoryExeRelFlag==1){
					factorySchemeExecButton.setVisible(false);
					factorySchemeReleaseButton.setVisible(true);
				}			
				cxk_cszxStore.removeAll();
		   	    cxk_cszxStore.baseParams = {
		   	   	   schemeId : staticFactoryCtrlSchemeId,
		   	   	   nodeType:'schemeId',
		   	   	   add:true
		   	    };
		   	    Ext.getBody().mask("加载中...");
		   	    cxk_cszxStore.load({
		   	   	   callback: function(records, options, success){
		               if(null != records && 0 < records.length){
		                   	Ext.Ajax.request({
								url : "./baseapp/factctrlLoadScheme!factctrlLoadScheme.action",
								params : {
									schemeId: staticFactoryCtrlSchemeId
								},
								success : function(response) {
									var records = Ext.decode(response.responseText);
									//加载模板信息
									cxkTemplateId=records.templateId;
		                       		cxkTemplateStore.load({ 
			                       		callback: function(){
			                       			 Ext.getBody().unmask();
			                       		}
			                       	});
								}
		                   });
		               }
		               else{
		            	   cxkTemplateId="";
		            	   Ext.getBody().unmask();
		               }
		           } , 
		           add:true
		   	   });
			}
	 		staticFactoryExeRelActivate=0;
		}
	});
});
