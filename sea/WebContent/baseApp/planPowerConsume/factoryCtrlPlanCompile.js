function origFrameQryShow(consNo,consName,tmnlAssetAddr){
	staticConsNo = consNo;
	staticConsName = consName;
	staticTmnlAssetAddr = tmnlAssetAddr;
	openTab("原始报文查询","./baseApp/dataGatherMan/origFrameQry.jsp");
}

Ext.onReady(function(){
	var cxkPlanTemplate="";
	// grid解锁
	function unlockGrid(tab) {//取消全选时，解锁Grid
		if(tab=="sdk") {//电压电流Grid
			sdk_cszxSM.unlock();
			sdk_cszxGrid.onEnable();
			sdk_cszxGrid.getBottomToolbar().enable();
			Ext.getCmp('plan_sdkGsselectAllcb').setValue(false);
		} else if (tab =="cxk"){//功率Grid
			cxk_cszxSM.unlock();
			cxk_cszxGrid.onEnable();
			cxk_cszxGrid.getBottomToolbar().enable();
			Ext.getCmp('plan_cxkGsselectAllcb').setValue(false);
		} else if (tab =="float"){//拉合闸Grid
			floatSM.unlock();
			floatGrid.onEnable();
			floatGrid.getBottomToolbar().enable();
			Ext.getCmp('plan_floatGsselectAllcb').setValue(false);
		}
	}
	// grid锁定
	function lockGrid(tab) {
		if(tab=="sdk") {
			sdk_cszxSM.lock();
			sdk_cszxGrid.onDisable();
			sdk_cszxGrid.getBottomToolbar().disable();
		} else if (tab =="cxk"){
			cxk_cszxSM.lock();
			cxk_cszxGrid.onDisable();
			cxk_cszxGrid.getBottomToolbar().disable();
		} else if (tab =="float"){
			floatSM.lock();
			floatGrid.onDisable();
			floatGrid.getBottomToolbar().disable();
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
	    	if(val<0||val>99999||isNaN(val)){
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
	   powerConstText:'无效的功率定值',
	   factoryConstText:'厂休控定值超出实际范围',
	   continueHoursText:'延续时间为0-48内，间隔半小时的数字',
	   floatValueText: '浮动系数必须是0-100之间的数字'
	});
	
	//模板信息窗口中的保存按钮用于新建/修改模板信息	
	var sOruTemplateFlag;
    //用于修改模板时判断模板名称
	var oldTemplateName;
	//用于设置模板Id
	var cxkTemplateId="";
	
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
					id:'plan_powerControl_facRestTemplateName',
					fieldLabel:'模板名称',
					allowBlank:false,
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
						var templateNameNode = Ext.getCmp("plan_powerControl_facRestTemplateName");
						var templateName = templateNameNode.getValue();
						if(templateName == "") {
							Ext.Msg.alert("提示","模板名称不能为空");
							return;
						}
						if(!cxkmb2.getForm().isValid()){
							Ext.MessageBox.alert("提示","存在无效参数！");
				     	     return;
				     	}
						cxkmb2_1.find('name','template.templateName')[0].setValue(templateName);				
						var ctrlTimeH = cxkmb2_4.find('name','ctrlTimeH')[0].getValue();
						var ctrlTimeM = cxkmb2_4.find('name','ctrlTimeM')[0].getValue();
						var ctrlTime = (ctrlTimeH>9?ctrlTimeH:("0"+ctrlTimeH)) + ":" +
														(ctrlTimeM>9?ctrlTimeM:("0"+ctrlTimeM));
						
						cxkmb2_4.find('name','template.ctrlTime')[0].setValue(ctrlTime);
						var weekDays='';
						
						var weekDayArray = weekDaysCheck.getValue();
						for (var i = 0; i < weekDayArray.length; i++) {
							weekDays += weekDayArray[i].getRawValue();
						}
						if(weekDays == '') {
							weekDays = '0';
						}
						cxkmb2_3.find('name','template.weekDays')[0].setValue(weekDays);
						
						if(sOruTemplateFlag=0){
							Ext.Ajax.request({
								url : 'baseapp/cxkTemplateList!checkTemplateName.action',
								params : {
									templateName : templateName
								},
								success : function(response) {
									var exist = Ext.decode(response.responseText).exist;
									if (exist == true) {
										Ext.Msg.alert("提示","模板名称不能重复");
										return;
									}
									cxkmb2.getForm().submit({ // 提交表单
										method : 'post',
									    url:'baseapp/saveWFactctrlTemplate!saveWFactctrlTemplate.action',
										success : function(form, action) { // 返回成功
											Ext.Msg.alert("提示","模板保存成功");
											cxkmb.hide(); 
											cxkTemplateNameComboBox.setValue();
											cxkTemplateId=""
											cxkTemplateStore.load();
										},
										failure : function(form, action) { // 返回失败
											Ext.Msg.alert("提示","模板保存出错");
										}
									});
								}
							});
						}
						else if(sOruTemplateFlag=1){
							cxkmb2_1.find('name','template.templateId')[0].setValue(cxkTemplateNameComboBox.getValue());	
							if(oldTemplateName==templateName){
								cxkmb2.getForm().submit({ // 提交表单
										method : 'post',
									    url:'baseapp/updateWFactctrlTemplate!updateWFactctrlTemplate.action',
										success : function(form, action) { // 返回成功
											Ext.Msg.alert("提示","模板修改成功");
											cxkmb.hide(); 
											cxkTemplateNameComboBox.setValue();
											cxkTemplateId=""
											cxkTemplateStore.load();
										},
										failure : function(form, action) { // 返回失败
											Ext.Msg.alert("提示","模板修改出错");
										}
								});
							}
							else{
								Ext.Ajax.request({
									url : 'baseapp/cxkTemplateList!checkTemplateName.action',
									params : {
										templateName : templateName
									},
									success : function(response) {
										var exist = Ext.decode(response.responseText).exist;
										if (exist == true) {
											Ext.Msg.alert("提示","模板名称不能重复");
											return;
										}
										cxkmb2.getForm().submit({ // 提交表单
											method : 'post',
										    url:'baseapp/updateWFactctrlTemplate!updateWFactctrlTemplate.action',
											success : function(form, action) { // 返回成功
												Ext.Msg.alert("提示","模板保存成功");
												cxkmb.hide(); 
												cxkTemplateNameComboBox.setValue();
												cxkTemplateId=""
												cxkTemplateStore.load();
											},
											failure : function(form, action) { // 返回失败
												Ext.Msg.alert("提示","模板保存出错");
											}
										});
									}
								});
							}
						}
					},
					text:'保存'
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
										width : 150
									}]
						}, {
							columnWidth : .1,
							xtype : 'label',
							text : 'kW'
						},{
							xtype:'hidden',
							name :'template.templateId'
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
		items : [{name : '1',boxLabel : '周一',inputValue:'1'},
					{name : '2',	boxLabel : '周二',inputValue:'2'}, 
					{name : '3',	boxLabel : '周三',inputValue:'3'}, 
					{name : '4',	boxLabel : '周四',inputValue:'4'}, 
					{name : '5',	boxLabel : '周五',inputValue:'5'}, 
					{name : '6',boxLabel : '周六',inputValue:'6'}, 
					{name : '7',	boxLabel : '周日',inputValue:'7'}]
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
			
	var cxkmb2_4 = new Ext.Panel({//开始时间增减按钮！！！！！！
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
								accelerate: true
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
								accelerate: true
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
       border:false,
       modal:true,
       height:400,
       width:650,
       closeAction:'hide',
	   resizable:false,
	   layout:'border',
	   items:[cxkmb1,cxkmb2]
    });
//---------------------厂休控   模板信息 按钮   结束-----------------------------------------------------
    
	var cxkTemplateStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url : 'baseapp/cxkTemplateList!queryTemplateList.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'templateList'
			}, [{name:'templateId'},{name:'ctrlTime'},{name:'templateName'},
				{name:'weekDays'},{name:'factoryConst'},{name:'continueHours'}])
	});
	var cxkTemplateNameComboBox = new  Ext.form.ComboBox({
		store : cxkTemplateStore,
		//xtype:'combo',
		name :'combo_templateName',
		fieldLabel:'模板名称',
		valueField : 'templateId',
		editable : false,
		triggerAction : 'all',
		forceSelection : true,
		mode : 'remote',
		selectOnFocus : true,
		displayField : 'templateName',
		emptyText:'--请选择模板--',
		anchor:'85%'
	});
	
	cxkTemplateStore.on('load',function(){
		cxkTemplateNameComboBox.setValue(cxkTemplateId);
	});
	
	 //厂休控参数下发
	var facRestPanel = new Ext.Panel({
	  	title:'方案名：',
	  	layout:'column',
	  	region:'north',
	  	height:75,
	  	bodyStyle:'padding:15px 0px 0px 0px',
	  	buttonAlign:'center',
	  	items:[{
				columnWidth:.35,
				labelAlign:'right',
				layout:'form',
				labelSeparator:' ',
				border:false,
				items:[cxkTemplateNameComboBox]
			},{
				columnWidth:.12,
				border:false,
				layout:'form',
				items:[{
					xtype:'button',
					text:'模板信息',
					anchor:'90%',
					handler:function(){
						var templateId= cxkTemplateNameComboBox.getValue();
						var store = cxkTemplateStore;
						store.each(function(obj){
							if(obj.get('templateId') == templateId){
								cxkPlanTemplate = obj;
							}
						});
//						设置页面template值
						if(cxkPlanTemplate == null || typeof(cxkPlanTemplate) == 'undefined'||cxkPlanTemplate=="") {
							Ext.Msg.alert('提示','请选择模板');
							return;
						}
						sOruTemplateFlag=1;
						oldTemplateName=cxkPlanTemplate.get('templateName');
						var tmpNameNode = Ext.getCmp('plan_powerControl_facRestTemplateName');
	        			tmpNameNode.setValue(cxkPlanTemplate.get('templateName'));
	        			var weekDays ="";
	        			var weekDaysString = cxkPlanTemplate.get('weekDays');
	        			for (var i = 0; i < weekDaysString.length-1; i++) {
	        				weekDays += (weekDaysString.substr(i,1) + ',');
	        			}
	        			weekDays += weekDaysString.substr(weekDaysString.length-1,1);
	        			weekDaysCheck.reset();//设置前先重置
	        			if(weekDays !="") {
	        				weekDaysCheck.setValue(weekDays);
	        			}
	        			cxkmb2_1.find('name','template.factoryConst')[0].setValue(cxkPlanTemplate.get('factoryConst'));
	        			cxkmb2_2.find('name','template.continueHours')[0].setValue(cxkPlanTemplate.get('continueHours'));
	        			cxkmb2_4.find('name','ctrlTimeH')[0].setValue(cxkPlanTemplate.get('ctrlTime').substr(0,2));
	        			cxkmb2_4.find('name','ctrlTimeM')[0].setValue(cxkPlanTemplate.get('ctrlTime').substr(3,2));
	        			
	        			cxkmb.show(); 
	        		}
				}]
			},{columnWidth:.12,
				border:false,
				layout:'form',
				items:[{
					xtype:'button',
					text:'新建模板',
					anchor:'90%',
					handler:function(){
					    sOruTemplateFlag=0;
						var tmpNameNode = Ext.getCmp('plan_powerControl_facRestTemplateName');
	        			tmpNameNode.setValue('');
	        			weekDaysCheck.reset();
	        			cxkmb2_1.find('name','template.factoryConst')[0].setValue('');
	        			cxkmb2_2.find('name','template.continueHours')[0].setValue('');
	        			cxkmb2_4.find('name','ctrlTimeH')[0].setValue('');
	        			cxkmb2_4.find('name','ctrlTimeM')[0].setValue('');
	        			
	        			cxkmb.show(); 
	        		}
			}]
		},{columnWidth:.12,
				border:false,
				layout:'form',
				items:[{
				text:'参数召测',//厂休控参数召测
				xtype:'button',
				anchor:'90%',
	  			handler:function(){
	  				var users = cxk_cszxSM.getSelections();
			    	if(null == users || users.length==0){
			     	    Ext.MessageBox.alert("提示","请选择用户！");
			     	    return;
			     	}
	  				ctrlCheckStaff(cxkFetch,'query');
	  			}
	  		}]
	  	}]
	});
	
	//设置标题
	if(typeof(staticFactorySchemeName) != 'undefined'){
		facRestPanel.setTitle('方案名：'+staticFactorySchemeName);
	}
	
	var cxkFetch = function cxkFetch(){
        var users = cxk_cszxSM.getSelections();
     	var pwrCtrlList = new Array();//向后台传的参数数组
     	var execNum = 0;//过滤终端的个数
     	cxk_zcStore.removeAll();
     	if (Ext.getCmp('plan_cxkGsselectAllcb').checked) { //如果选中全选按钮
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
     			if (Ext.getCmp('plan_cxkGsselectAllcb').checked) { //如果选中全选按钮
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
   	           		cxkSchemeLoad();
   	           }
   	       }] 
   	    }]
    });
    
    var cxkSchemeLoad = function cxkSchemeLoad(){
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
   	   	   schemeId : cxkSchemeIdValue,
   	   	   nodeType:type,
   	   	   add:true
   	   };
       Ext.getBody().mask("加载中...");
   	   cxk_cszxStore.load({
   	   	   callback: function(records, options, success){
               if(null != records && 0 < records.length){
                   	Ext.Ajax.request({
						url : "./baseapp/factctrlLoadScheme!factctrlLoadScheme.action",
						params : {
							schemeId: cxkSchemeIdValue
						},
						success : function(response) {
							var records = Ext.decode(response.responseText);
                       		cxkTemplateId=records.templateId;
                       		cxkTemplateStore.load({ 
	                       		callback: function(){
	                       			 Ext.getBody().unmask();
	                       		}
	                       	});
						}
                   });
               } else {
               	   cxkTemplateId="";
            	   cxkTemplateNameComboBox.setValue('');
            	   Ext.getBody().unmask();
			   }
           } , 
           add:true
   	   });
       cxkLoadSchemeWindow.hide();
    };
    
    /*厂休控方案加载*/
    var cxkLoadStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url:'./baseapp/pwrctrlScheme!cxkLoadScheme.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'schemeList'
			}, [{name:'ctrlSchemeId'},
				{name:'ctrlLoad'},
				{name:'ctrlDateStart'},
				{name:'ctrlDateEnd'},
				{name:'limitType'},
				{name:'schemeRemark'},
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
        modal:true,
        height:250,
        width:400,
        closeAction:'hide',
        resizable:false,
        layout:'border',
        title:'方案加载',
        items:[cxkLoadScheme1,cxkLoadScheme2]
    }); 
/*//定义保存为方案面板组件
//开始日期
	var cxkNewStartDate = new Ext.form.DateField({
		fieldLabel : '限电日从',
		name : 'newStartDate',
		anchor : '90%',
	    value : new Date(),
	    labelSeparator:''
    });
    //结束日期
    var cxkNewEndDate = new Ext.form.DateField({
		fieldLabel : '到',
		name : 'newEndDate',
		anchor : '76%',
	    value : new Date().add(Date.DAY,30),
	    labelSeparator:''
    });
    //限电类型Store
	var cxkLimitTypeStore = new Ext.data.JsonStore({
	    url : "./baseapp/energyControl!loadLimitType.action",
	    fields : [ 'limitType', 'limitTypeName' ],
	    root : "limitTypeList"
    });
     //限电类型Store
	var cxkUpdateLimitTypeStore = new Ext.data.JsonStore({
	    url : "./baseapp/energyControl!loadLimitType.action",
	    fields : [ 'limitType', 'limitTypeName' ],
	    root : "limitTypeList"
    });
    //定义购电标志
	var cxkLimitTypeComboBox = new Ext.form.ComboBox({
				fieldLabel : '限电类型',
				store : cxkLimitTypeStore,
				displayField : 'limitTypeName',
	            valueField : 'limitType',
				bodyStyle: 'padding:10px;',
				triggerAction : 'all',
				mode : 'remote',
				editable : false,
				width: 103,
				forceSelection : true,
				emptyText : '请选择类型',
				selectOnFocus : true,
				labelSeparator:''
	});
	//厂休控方案备注
    var cxkNewSchemeRemark = new Ext.form.TextArea({
    	width:255,
    	height:80,
        fieldLabel:'备    注'
    });
    var cxkUpdateSchemeRemark = new Ext.form.TextArea({
    	width:255,
    	height:80,
        fieldLabel:'备    注'
    });
    //厂休控调控负荷
	var cxkNewSchemeCtrlLoad = new Ext.form.TextField({
            width:60,
            fieldLabel:'调控负荷(KW)'
	});
	var cxkUpdateSchemeCtrlLoad = new Ext.form.TextField({
            width:60,
            fieldLabel:'调控负荷(KW)'
	});
    //	开始日期
	var cxkUpdateStartDate = new Ext.form.DateField({
		fieldLabel : '限电日从',
		name : 'newStartDate',
		anchor : '90%',
	    value : new Date(),
	    labelSeparator:''
    });

    //结束日期
    var cxkUpdateEndDate = new Ext.form.DateField({
		fieldLabel : '到',
		name : 'newEndDate',
		anchor : '76%',
	    value : new Date().add(Date.DAY,30),
	    labelSeparator:''
    });
	//定义购电标志
	var cxkUpdateLimitTypeComboBox = new Ext.form.ComboBox({
				fieldLabel : '限电类型',
				store : cxkUpdateLimitTypeStore,
				displayField : 'limitTypeName',
	            valueField : 'limitType',
				bodyStyle: 'padding:10px;',
				triggerAction : 'all',
				mode : 'remote',
				width: 103,
				editable : false,
				forceSelection : true,
				emptyText : '请选择类型',
				selectOnFocus : true,
				labelSeparator:''
	});
	//厂休控方案label
	var cxkSchemeLabel = new Ext.form.TextField({
	        fieldLabel:'方案名称',
   	        value:'功率厂休控',
   	        readOnly:true,
   	        labelSeparator:'',
            anchor:'95%' 
	});
	
	//方案名
	var cxkSchemeName = new Ext.form.TextField({
            width:142
	});
	//方案名列表
	var cxkSchemeComBox = new Ext.form.ComboBox({
		store : cxkLoadStore,
		fieldLabel : '方案名称',
		typeAhead : true,
		editable : false,
		mode : 'remote',
		forceSelection : true,
		bodyStyle: 'padding:10px;',
		triggerAction : 'all',
		width:255,
		valueField : 'ctrlSchemeId',
		emptyText:'--请选择方案--',
		displayField : 'ctrlSchemeName',
		selectOnFocus : true
	});
	//给修改方案界面textField赋值
	cxkSchemeComBox.on('select', function(){
		var scheme = cxkLoadStore.getById(cxkSchemeComBox.getValue());
		
		cxkUpdateLimitTypeComboBox.setValue(scheme.get('limitType'));
		cxkUpdateSchemeCtrlLoad.setValue(scheme.get('ctrlLoad'));
		cxkUpdateStartDate.setValue(Date.parseDate(scheme.get('ctrlDateStart'),'Y-m-d\\TH:i:s'));
		cxkUpdateEndDate.setValue(Date.parseDate(scheme.get('ctrlDateEnd'),'Y-m-d\\TH:i:s'));
		cxkUpdateSchemeRemark.setValue(scheme.get('schemeRemark'));
	});
	
    //定义radio选择组
    var cxkEcRadioGroup = new Ext.form.RadioGroup({
	    width : 200,
	    height :20,
	    items : [new Ext.form.Radio({
		    boxLabel : '另存为方案',
		    name : 'ec-radioGroup',
		    inputValue : '1',
		    checked : true,
		    handler:function(checkbox, checked){
		    	if(checked){
		    		cxkSaveSchemePenl.layout.setActiveItem(0);
		    	}
		    }
	    }), new Ext.form.Radio({
		    boxLabel : '保存方案',
		    name : 'ec-radioGroup',
		    inputValue : '2',
		    handler:function(checkbox, checked){
		    	if(checked) {
		    		cxkSaveSchemePenl.layout.setActiveItem(1);
		    	}
		    }
	    })]
    });
    
//    保存方案按钮
    var cxkSaveSchemeButton = new Ext.Button({
    	text:'确定',
 	    handler:function(){
            var users = cxk_cszxSM.getSelections();
            if(null == users || users.length==0){
	     	    Ext.MessageBox.alert("提示","请选择用户！");
	     	    return;
	     	}
	     	var templateName = cxkTemplateNameComboBox.getRawValue();
	     	if(templateName==null || templateName==''){
	     		 Ext.MessageBox.alert("提示","请选择参数模板！");
	     	    return;
	     	}
	     	
	     	var limitType = cxkLimitTypeComboBox.getValue();
	     	if(limitType==null || limitType==''){
	     		 Ext.MessageBox.alert("提示","请选择限电类型！");
	     	    return;
	     	}
	     	var ctrlLoad = cxkNewSchemeCtrlLoad.getValue();
			if(ctrlLoad ==null || ctrlLoad=='') {
				Ext.MessageBox.alert("提示", "请输入调控负荷！");
	     	    return;
			}
	     	var pwrCtrlList = new Array();//向后台传的参数数组
	     	if (Ext.getCmp('plan_cxkGsselectAllcb').checked) { //如果选中全选按钮
				for (var i = 0; i < cxk_cszxStore.getCount(); i++) {
					pwrCtrlList[i] =cxk_cszxStore.getAt(i).data.orgNo +'`' + cxk_cszxStore.getAt(i).data.consNo +
						'`' + cxk_cszxStore.getAt(i).data.tmnlAssetNo + '`' + cxk_cszxStore.getAt(i).data.totalNo + '`' + cxk_cszxStore.getAt(i).data.protocolCode;
				}
	     	} else {
	     		if(null == users || users.length==0){
		     	    Ext.MessageBox.alert("提示","请选择用户！");
		     	    return;
		     	}
		     	for(var i = 0; i < users.length; i++){
					pwrCtrlList[i] = users[i].get('orgNo')+'`'+users[i].get('consNo')+'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');
		     	}
	     	}
            
			var ctrlSchemeName = cxkSchemeLabel.getValue() + '-' +cxkSchemeName.getValue();
			//校验方案名是否已存在
			Ext.Ajax.request({
	     		url:'./baseapp/ckeckCtrlSchemeName!ckeckCtrlSchemeName.action',
	     		params : {
	     			ctrlSchemeName:ctrlSchemeName
	     		},
	     		success : function(response){
	     			var checkResult = Ext.decode(response.responseText).checkResult;
	     			if(checkResult) {
	     				Ext.MessageBox.alert("提示","方案名已存在");
	     				viewPanel.getEl().unmask();
	  		     		return;
	     			}
	     	    	Ext.Ajax.request({
	  		     		url:'./baseapp/addPwrCtrlScheme!addCxkPwrCtrlScheme.action',
	  		     		params : {
	  		     			curveNo:constcombo.getValue(),
	  		     			templateName:templateName,
	  		     			pwrCtrlList:pwrCtrlList,
	  		     			'scheme.ctrlSchemeName':ctrlSchemeName,
	  		     			'scheme.limitType':limitType,
	  		     			'scheme.schemeRemark':cxkNewSchemeRemark.getValue(),
	  		     			'scheme.ctrlLoad':ctrlLoad,
	  		     			'scheme.ctrlDateStart':cxkNewStartDate.getValue(),
	  		     			'scheme.ctrlDateEnd':cxkNewEndDate.getValue()
	  		     		},
	  		     		success : function(){
		  		     		Ext.MessageBox.alert("提示","方案保存成功");
		  		     		viewPanel.getEl().unmask();
	  		     		   return;
	  		     		},
	  		     		failure : function(){
		  		     		Ext.MessageBox.alert("提示","方案保存失败");
		  		     		viewPanel.getEl().unmask();
	  		     		   return;
	  		     		}
	  		     	});
  		     	},
  		     	failure : function(){
	     			Ext.MessageBox.alert("提示","请求失败");
	     		   return;
	     		}
			});
			viewPanel.getEl().mask('保存中...');
 	        cxkSaveScheme.hide();	
 	    }
    });
   var cxkSchemeHideButton = new Ext.Button({
    	text:'退出',
 	    handler:function(){
 	        cxkSaveScheme.hide();	
 	    }
    });
//    保存方案panel
	var cxkSaveSchemePnl1=new Ext.Panel({
        height:250,
        border : false,
        layout:'form',
        buttonAlign:'center',
        items:[{
        	layout:'column',
   	        border : false,
   	        items:[{
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:10px 0px 0px 5px',
   	            labelAlign: "right",
   	            labelWidth:70,
   	            items:[cxkSchemeLabel] 
   	        }, {
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:10px 0px 0px 0px',
   	            hideLabels:true,
   	            items:[cxkSchemeName] 
   	        }]
   	    },{
        	layout:'column',
   	        border : false,
   	        items:[{
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 5px',
   	            labelAlign: "right",
   	            labelWidth:70,
   	            items:[cxkLimitTypeComboBox] 
   	        }, {
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            labelWidth:77,
   	            items:[cxkNewSchemeCtrlLoad] 
   	        }]
   	    },{
   	        layout:'column',
   	        border : false,
   	        items:[{
   	            columnWidth:.53,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 4px',
   	            labelAlign: "right",
   	            labelWidth:70,
   	            items:[cxkNewStartDate] 
   	        }, {
   	            columnWidth:.45,
   	            layout:'form',
   	            border : false,
   	            labelWidth:10,
   	            labelAlign: "right",
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            items:[cxkNewEndDate] 
   	        }]
   	    },{
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 5px',
   	            labelWidth:70,
   	             labelAlign: "right",
   	            items:[cxkNewSchemeRemark]
   	    },{
        	layout:'column',
   	        border : false,
   	        items:[{
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 95px',
   	            labelAlign: "right",
   	            hideLabels:true,
   	            items:[cxkSaveSchemeButton] 
   	        }, {
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            hideLabels:true,
   	            items:[cxkSchemeHideButton] 
   	        }]
   	    }]
    });
    
    //    保存方案按钮
    var cxkUpdateSchemeButton = new Ext.Button({
    	text:'确定',
 	    handler:function(){
            var users = cxk_cszxSM.getSelections();
            if(null == users || users.length==0){
	     	    Ext.MessageBox.alert("提示","请选择用户！");
	     	    return;
	     	}
	     	var templateName = cxkTemplateNameComboBox.getRawValue();
	     	if(templateName==null || templateName==''){
	     		 Ext.MessageBox.alert("提示","请选择参数模板！");
	     	    return;
	     	}
//	     	校验方案
	     	var ctrlSchemeId = cxkSchemeComBox.getValue();
	     	var ctrlSchemeName = cxkSchemeComBox.getRawValue();
	     	if(ctrlSchemeId ==null || ctrlSchemeId=="") {
	     		Ext.MessageBox.alert("提示","请选择方案！");
	     	    return;
	     	}
//	     	校验限电类型
	     	var limitType = cxkUpdateLimitTypeComboBox.getValue();
	     	if(limitType==null || limitType==''){
	     		Ext.MessageBox.alert("提示","请选择限电类型！");
	     	    return;
	     	}
	     	var ctrlLoad = cxkUpdateSchemeCtrlLoad.getValue();
			if(ctrlLoad ==null || ctrlLoad=='') {
				Ext.MessageBox.alert("提示", "请输入调控负荷！");
	     	    return;
			}
	     	var pwrCtrlList = new Array();//向后台传的参数数组
	     	if (Ext.getCmp('plan_cxkGsselectAllcb').checked) { //如果选中全选按钮
				for (var i = 0; i < cxk_cszxStore.getCount(); i++) {
					pwrCtrlList[i] =cxk_cszxStore.getAt(i).data.orgNo +'`' + cxk_cszxStore.getAt(i).data.consNo +
						'`' + cxk_cszxStore.getAt(i).data.tmnlAssetNo + '`' + cxk_cszxStore.getAt(i).data.totalNo + '`' + cxk_cszxStore.getAt(i).data.protocolCode;
				}
	     	} else {
	     		if(null == users || users.length==0){
		     	    Ext.MessageBox.alert("提示","请选择用户！");
		     	    return;
		     	}
		     	for(var i = 0; i < users.length; i++){
					pwrCtrlList[i] = users[i].get('orgNo')+'`'+users[i].get('consNo')+'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');
		     	}
			}
            
			var ctrlSchemeName = cxkSchemeLabel.getValue() + '-' +cxkSchemeName.getValue();
 	    	Ext.Ajax.request({
	     		url:'./baseapp/addPwrCtrlScheme!addCxkPwrCtrlScheme.action',
	     		params : {
	     			curveNo:constcombo.getValue(),
	     			templateName:templateName,
	     			pwrCtrlList:pwrCtrlList,
	     			'scheme.ctrlSchemeId':ctrlSchemeId,
	     			'scheme.ctrlSchemeName':ctrlSchemeName,
	     			'scheme.limitType':limitType,
	     			'scheme.schemeRemark':cxkUpdateSchemeRemark.getValue(),
	  		     	'scheme.ctrlLoad':ctrlLoad,
	     			'scheme.ctrlDateStart':cxkUpdateStartDate.getValue(),
	     			'scheme.ctrlDateEnd':cxkUpdateEndDate.getValue()
	     		},
	     		success : function(){
	     			Ext.MessageBox.alert("提示","方案修改成功");
	     			viewPanel.getEl().unmask();
	     		   return;
	     		},
	     		failure : function(){
	     			Ext.MessageBox.alert("提示","方案修改失败");
	     			viewPanel.getEl().unmask();
	     		   return;
	     		}
	     	});
	     	viewPanel.getEl().mask('保存中...');
 	        cxkSaveScheme.hide();	
 	    }
    });
   var cxkUpdateSchemeHideButton = new Ext.Button({
    	text:'退出',
 	    handler:function(){
 	        cxkSaveScheme.hide();	
 	    }
    });
    //    保存方案panel
	var cxkUpdateSchemePnl1=new Ext.Panel({
        height:250,
        border : false,
        layout:'form',
        buttonAlign:'center',
        items:[{
   	            layout:'form',
   	            border : false,
   	            labelWidth:70,
   	            labelAlign: "right",
   	            bodyStyle : 'padding:10px 0px 0px 5px',
   	            items:[cxkSchemeComBox] 
   	    }, {
        	layout:'column',
   	        border : false,
   	        items:[{
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 5px',
   	            labelAlign: "right",
   	            labelWidth:70,
   	            items:[cxkUpdateLimitTypeComboBox] 
   	        }, {
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            labelWidth:77,
   	            items:[cxkUpdateSchemeCtrlLoad] 
   	        }]
   	    }, {
   	        layout:'column',
   	        border : false,
   	        items:[{
   	            columnWidth:.53,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 4px',
   	            labelAlign: "right",
   	            labelWidth:70,
   	            items:[cxkUpdateStartDate] 
   	        }, {
   	            columnWidth:.45,
   	            layout:'form',
   	            border : false,
   	            labelWidth:10,
   	            labelAlign: "right",
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            items:[cxkUpdateEndDate] 
   	        }]
   	    },{
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 5px',
   	            labelWidth:70,
   	             labelAlign: "right",
   	            items:[cxkUpdateSchemeRemark]
   	    },{
        	layout:'column',
   	        border : false,
   	        items:[{
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 95px',
   	            labelAlign: "right",
   	            hideLabels:true,
   	            items:[cxkUpdateSchemeButton] 
   	        }, {
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            hideLabels:true,
   	            items:[cxkUpdateSchemeHideButton] 
   	        }]
   	    }]
    });
    //厂休控redio
     var cxkSchemeRedioPanel = new Ext.Panel({
    	layout:'form',
        border : false,
        bodyStyle : 'padding:5px 0px 0px 35px',
        labelAlign: "right",
        labelWidth:1,
        items:[cxkEcRadioGroup] 
    });
    
   //	    定义保存为方案面板组件
    var cxkSaveSchemePenl=new Ext.Panel({
        layout:'card',
        activeItem : 0,
        border:false,
        items:[cxkSaveSchemePnl1, cxkUpdateSchemePnl1]
    });
    
    var cxkSchemePenal = new Ext.Panel({
    	layout:'form',
    	border:false,
    	items:[cxkSchemeRedioPanel,cxkSaveSchemePenl]
    });
    
//	    定义保存为方案面板组件
    var cxkSaveSchemePnl=new Ext.Panel({
        layout:'form',
        border:false,
        items:[cxkSchemePenal]
    });

    //保存方案跳出窗口
    var cxkSaveScheme = new Ext.Window({
        name:'cxkSaveScheme',
        height:290,
        width:400,
        closeAction:'hide',
        resizable:false,
        layout:'fit',	        
        title:'保存方案',
        items:[cxkSaveSchemePnl]
    });

//----------------------------------------------------------厂休控-执行方案   结束-------------------------------------------------------	
*///------------------------------------------------------厂休控参数执行-召测列表    开始	
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
				{name:'protocolCode'}])
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
					{name:'protocolCode'}]
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
			data[i][index++] = valStore.getAt(i).data.protocolCode;
		}
		return data;
	}	
	
	var cxk_cszxSM = new Ext.grid.CheckboxSelectionModel();
	var cxkzxRowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(cxk_cszxStore && cxk_cszxStore.lastOptions && cxk_cszxStore.lastOptions.params){
				startRow = cxk_cszxStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	var cxk_cszxCM = new Ext.grid.ColumnModel([cxkzxRowNum, cxk_cszxSM, 
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
				},align : 'center'}
			//{header : '规约编码',	dataIndex : 'protocolCode',	sortable : true,	hidden:true,	align : 'center'}, 
			]);

	var cxk_cszxGrid = new Ext.grid.GridPanel({
				store : splite_cxk_cszxStore,
				title : '厂休控方案用户列表',
				loadMask:true,
				cm : cxk_cszxCM,
				sm : cxk_cszxSM,
				tbar : [{
			  		text:'加载方案',
			  		handler : function() {
		  		     	cxkLoadStore.load();
		  		        cxkLoadSchemeWindow.show();
					}
			  	},{
			  		text:'保存为方案',
			  		handler : function() {
						/*Ext.Ajax.request({
		  		     		url:'./baseapp/remoteControl!loadSchemeData.action',
		  		     		success : function(response){
		  		     			var result = Ext.decode(response.responseText);
						        if (!result.success) {
							        var msg;
							        if (!result.errors.msg)
								        msg = '未知错误！';
							        else
								        msg = result.errors.msg;
							        Ext.MessageBox.alert('后台错误', msg);
		  		     		        return;
						        }
						        cxkLimitTypeStore.loadData(result);
		  		     		},
		  		     		failure : function(){
		  		     		   Ext.MessageBox.alert("提示","失败");
		  		     		   return;
		  		     		}
		  		     	});
		  		        cxkSaveScheme.show();*/
			  		    var users = cxk_cszxSM.getSelections();
			            if(null == users || users.length==0){
				     	    Ext.MessageBox.alert("提示","请选择用户！");
				     	    return;
				     	}
				     	var templateName = cxkTemplateNameComboBox.getRawValue();
				     	if(templateName==null || templateName==''){
				     		 Ext.MessageBox.alert("提示","请选择参数模板！");
				     	    return;
				     	}
				     	
				     	var pwrCtrlList = new Array();//向后台传的参数数组
				     	if (Ext.getCmp('plan_cxkGsselectAllcb').checked) { //如果选中全选按钮
							for (var i = 0; i < cxk_cszxStore.getCount(); i++) {
								pwrCtrlList[i] =cxk_cszxStore.getAt(i).data.orgNo +'`' + cxk_cszxStore.getAt(i).data.consNo +
									'`' + cxk_cszxStore.getAt(i).data.tmnlAssetNo + '`' + cxk_cszxStore.getAt(i).data.totalNo + '`' + cxk_cszxStore.getAt(i).data.protocolCode;
							}
				     	} else {
				     		if(null == users || users.length==0){
					     	    Ext.MessageBox.alert("提示","请选择用户！");
					     	    return;
					     	}
					     	for(var i = 0; i < users.length; i++){
								pwrCtrlList[i] = users[i].get('orgNo')+'`'+users[i].get('consNo')+'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');
					     	}
				     	}
				     	Ext.getBody().mask("保存中...");
		     	    	Ext.Ajax.request({
		  		     		url:'./baseapp/addPwrCtrlScheme!addFactoryCtrlScheme.action',
		  		     		params : {
		  		     			//curveNo:constcombo.getValue(),
		  		     			templateName:templateName,
		  		     			pwrCtrlList:pwrCtrlList,
		  		     			schemeId:staticFactorySchemeId
		  		     		},
		  		     		success : function(){
			  		     		Ext.MessageBox.alert("提示","方案保存成功");
			  		     		Ext.getBody().unmask();
		  		     		    return;
		  		     		},
		  		     		failure : function(){
			  		     		Ext.MessageBox.alert("提示","方案保存失败");
			  		     		Ext.getBody().unmask();
		  		     		    return;
		  		     		}
		  		     	});
					}
			  	},{xtype: 'tbfill'},{
					xtype : 'checkbox',
					id : 'plan_cxkGsselectAllcb',
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
					}, '-',{	
						text : '删除选中用户',
						iconCls: 'cancel',
						handler : function() {
							if (Ext.getCmp('plan_cxkGsselectAllcb').checked) {
								cxk_cszxStore.removeAll(true);
								unlockGrid('cxk');
								Ext.getCmp('plan_cxkGsselectAllcb').setValue(false);
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
								if (Ext.getCmp('plan_cxkGsselectAllcb').checked) {
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
									if (Ext.getCmp('plan_cxkGsselectAllcb').checked) {
										Ext.getCmp('plan_cxkGsselectAllcb').setValue(false);
										cxk_cszxSM.selectAll();
									}
								}
			                 }
						}/*, '-', {
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
								if(Ext.getCmp('plan_cxkGsselectAllcb').checked) {
									Ext.getCmp('plan_cxkGsselectAllcb').setValue(false);
									cxk_cszxSM.selectAll();
								}
							 }
						}*/],
					bbar : new Ext.ux.MyToolbar({
						store : splite_cxk_cszxStore,
						allStore : cxk_cszxStore,
						enableExpAll : true, // excel导出全部数据
						expAllText : "全部",
						enableExpPage : true, // excel仅导出当前页
						expPageText : "当前页"
					})	
			});
	
	//厂休控召测结果Store
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
	var cxkzcRowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(cxk_zcStore && cxk_zcStore.lastOptions && cxk_zcStore.lastOptions.params){
				startRow = cxk_zcStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	var cxk_zcCM = new Ext.grid.ColumnModel([cxkzcRowNum, cxk_zcSM, 
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
					enableExpPage : true, // excel仅导出当前页
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
		items:[cxkGrid]
	});
			
	var facRestRadio = new Ext.Panel({//厂休控radio内容
		id:'plan_pwrctrl_cxk',
		border:false,
		layout:'border',
		items:[facRestPanel,facRestRadio_down]
	});
	
	//用户列表默认选中	

	//厂休控========================	
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

	renderModel(facRestRadio,'厂休控方案编制');		
		
	function clickEvent(obj,type){
	   	Ext.getBody().mask('数据加载中...');	
	    cxk_cszxStore.baseParams = {
			subsId : obj.subsId,
			groupNo : obj.groupNo,
			lineId : obj.lineId,
			tmnlAssetNo : obj.tmnlAssetNo,
			nodeType : type,
			orgNo : obj.orgNo,
			orgType : obj.orgType
		};
	    cxk_cszxStore.load({
			callback : function(recs, options, success) {
				Ext.getBody().unmask();	
				if (success)
					cxk_cszxGrid.getSelectionModel().selectRecords(recs, true);
			},
	    	add: true
	    });
	}					
	//  监听左边树点击事件
    var treeListener = new LeftTreeListener({
	    modelName : '厂休控方案编制',
	    processClick : function(p, node, e) {
	    	var obj = node.attributes.attributes;
		    var type = node.attributes.type;
		    clickEvent(obj,type);
	    },
	    processUserGridSelect:function(cm,row,record){
   		  clickEvent({tmnlAssetNo:record.get('tmnlAssetNo')},'usr');
		  return true;
   		}
    });
		
	//由有序用电任务编制链接而来，全局变量不为空时，自动加载传入的方案
	if(typeof(staticFactorySchemeId) != 'undefined') {
			cxk_cszxStore.removeAll();
	   	    cxk_cszxStore.baseParams = {
	   	   	   schemeId : staticFactorySchemeId,
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
								schemeId: staticFactorySchemeId
							},
							success : function(response) {
								var records = Ext.decode(response.responseText);
								cxkTemplateId=records.templateId;
		                       	cxkTemplateStore.load({ 
		                       		callback: function(){
		                       			 Ext.getBody().unmask();
		                       		}
		                       	});
							}
	                   });
	               } else {
	               	   cxkTemplateId="";
	            	   cxkTemplateNameComboBox.setValue('');
	            	   Ext.getBody().unmask();
				   }
	           } , 
	           add:true
	   	   });
	}
	
	staticFactorySchemeActivate=0;
	
	Ext.getCmp('厂休控方案编制').on('activate', function() {
		if (staticFactorySchemeActivate == 1) {
			//设置标题
			if(typeof(staticFactorySchemeName) != 'undefined'){
				facRestPanel.setTitle('方案名：'+staticFactorySchemeName);
			}
			if(typeof(staticFactorySchemeId) != 'undefined'){
				cxk_cszxStore.removeAll();
		   	    cxk_cszxStore.baseParams = {
		   	   	   schemeId : staticFactorySchemeId,
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
									schemeId: staticFactorySchemeId
								},
								success : function(response) {
									var records = Ext.decode(response.responseText);
									cxkTemplateId=records.templateId;
	                       			cxkTemplateStore.load({ 
			                       		callback: function(){
			                       			 Ext.getBody().unmask();
			                       		}
		                       		});
								}
		                   });
		               } else {
		               	   cxkTemplateId="";
		            	   cxkTemplateNameComboBox.setValue('');
		            	   Ext.getBody().unmask();
					   }
		           } , 
		           add:true
		   	   });
			}
			staticFactorySchemeActivate=0; 
		}
	});
});
	