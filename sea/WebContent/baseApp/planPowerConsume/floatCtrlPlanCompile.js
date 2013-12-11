function origFrameQryShow(consNo,consName,tmnlAssetAddr){
	staticConsNo = consNo;
	staticConsName = consName;
	staticTmnlAssetAddr = tmnlAssetAddr;
	openTab("原始报文查询","./baseApp/dataGatherMan/origFrameQry.jsp");
}

Ext.onReady(function(){
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
	    alertTime:function(val, field) {
	    	if(isNaN(val)){
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
	    floatValueText: '浮动系数必须是0-100之间的数字',
	    alertTimeText:'无效的当前功率下浮控告警时间'
	});
	
	//--------------------------------------------------下浮控 panel   开始
	var floatPanel_1 = new Ext.Panel({
		layout:'column',
		border:false,
		height:45,
		bodyStyle:'padding: 10px 0px ',
		style : 'font-size:12px',
		items:[{
			columnWidth:.19,
			border:false,
			labelAlign : 'right',
			labelWidth : 178,
			labelSeparator:' ',
			layout:'form',
			style : "padding-left:10px",
			items:[{
				xtype : 'label',
				anchor:'95%',
				text : '当前功率下浮控告警时间(分钟)'
			}]
		},{
			columnWidth : .03,
			xtype : 'checkbox',
			name:'box1',
			boxLabel : ''
		},{
			columnWidth:.12,
			layout:'form',
			labelAlign : 'right',
			border:false,
			labelWidth : 40,
			labelSeparator:' ',
			items:[{
					xtype:'textfield',
					name:'alertTime1',
					fieldLabel:'第一轮',
					anchor:'90%',
					vtype:'alertTime'
				}]
		},{
			columnWidth : .03,
			xtype : 'checkbox',
			name:'box2',
			boxLabel : ''
		},{
			columnWidth:.12,
			layout:'form',
			labelAlign : 'right',
			border:false,
			labelWidth : 40,
			labelSeparator:' ',
			items:[{
					xtype:'textfield',
					name:'alertTime2',
					fieldLabel:'第二轮',
					anchor:'90%',
					vtype:'alertTime'
				}]
		},{
			columnWidth : .03,
			xtype : 'checkbox',
			name:'box3',
			boxLabel : ''
		},{
			columnWidth:.12,
			layout:'form',
			labelAlign : 'right',
			border:false,
			labelWidth : 40,
			labelSeparator:' ',
			items:[{
					xtype:'textfield',
					name:'alertTime3',
					fieldLabel:'第三轮',
					anchor:'90%',
					vtype:'alertTime'
				}]
		},{
			columnWidth : .03,
			xtype : 'checkbox',
			name:'box4',
			boxLabel : ''
		},{
			columnWidth:.12,
			layout:'form',
			labelAlign : 'right',
			border:false,
			labelWidth : 40,
			labelSeparator:' ',
			items:[{
					xtype:'textfield',
					name:'alertTime4',
					fieldLabel:'第四轮',
					anchor:'90%',
					vtype:'alertTime'
				}]
		}]
	});
	
	var floatPanel_2 = new Ext.Panel({
		layout:'column',
		border:false,
		height:38,
		items:[{
			columnWidth:.5,
			layout:'form',
			labelAlign:'right',
			border:false,
			labelSeparator:' ',
			labelWidth:198,
			items:[{
				xtype:'textfield',
				fieldLabel:'当前功率下浮控定值滑差时间(分钟)',
				name :'floatDownWtime',
				width:120,
				emptyText:'取值范围：1-60分钟',
				vtype:'downWTime'
			}]
		},{
			columnWidth:.45,
			layout:'form',
			labelAlign:'right',
			border:false,
			labelSeparator:' ',
			labelWidth:200,
			items:[{
				xtype:'textfield',
				fieldLabel:'控后有功功率冻结延时时间(分钟)',
				name:'powerFreezetime',
				width:120,
				emptyText:'取值范围：5-60分钟',
				vtype:'freezeTime'
			}]
		}]
	});
	
	
	var floatPanel_3 = new Ext.Panel({
		layout:'column',
		height:30,
		border:false,
		items:[{
			columnWidth:.5,
			border:false,
			layout:'column',
			items:[{
				border:false,
				layout:'form',
				labelSeparator:' ',
				labelWidth:185,
				labelAlign:'right',
				items:[{
					xtype:'radiogroup',
					name:'radioGroup',
					width:100,
					fieldLabel:'当前功率下浮控定值浮动系数(%)',
					items:[{
						boxLabel:'下浮',
						width:50,
						checked:true,
						inputValue:'down',
						name:'condRadio'
					},{
						boxLabel:'上浮',
						width:40,
						inputValue:'up',
						name:'condRadio'
					}]
				}]
			},{
				layout:'form',
				border:false,
				labelWidth:15,
				labelWidth:6,
				labelSeparator:' ',
				items:[{
					xtype:'textfield',
					name:'floatDownPercent',
					fieldLabel:' ',
					width:60,
					vtype:'floatValue'
				}]
			}]
		},{
				columnWidth:.45,
				border:false,
				labelSeparator:' ',
				layout:'form',
				labelWidth:188,
				labelAlign:'right',
				items:[
					 new Ext.ux.form.SpinnerField({
							fieldLabel:'当前功率下浮控控制时间(小时)    ',
							minValue: 0,
							maxValue: 23,
							allowDecimals: true,
							name :'ctrlTime',
							width:80,
							decimalPrecision: 1,
							incrementValue: 1,
							vtype:'hourTime',
							accelerate: true
        			})
				]
			}]
	});
	
	var floatPanel = new Ext.FormPanel({
		title:'方案名：',
		region:'north',
		border: false,
		layout:'form',
		height:140,
		items:[floatPanel_1,floatPanel_2,floatPanel_3]
	});
	
	if(typeof(staticFloatSchemeName) != 'undefined'){
		floatPanel.setTitle('方案名：'+staticFloatSchemeName);
	}		
//--------------------------------------------------下浮控 panel   结束	
//----------------------------------------------------------下浮控-执行方案   开始-------------------------------------------------------
//	定义方案加载弹出页面的上部分面板组件
//	定义方案加载时选中的方案ID
	var floatSchemeIdValue;
	var floatLoadScheme1=new Ext.Panel({
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
   	           text:'功率下浮控方案加载'
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
   	          		floatSchemeLoad(); 
   	          }
   	       }] 
   	    }]
    });
    
    var floatSchemeLoad = function floatSchemeLoad(){
   	   var loadFuncSelected = floatLoadScheme2.find("name","multiselect")[0];
   	   var selectionsArray = loadFuncSelected.view.getSelectedIndexes();
   	   var type = "schemeId";
   	   if(0 < selectionsArray.length){
   	   	   for(i = 0 ; i < selectionsArray.length; i++){
               floatSchemeIdValue = floatLoadStore.getAt(selectionsArray[selectionsArray.length-1]).get('ctrlSchemeId');
   	   	   }
   	   }
   	   floatStore.removeAll();//加载方案时，先清除列表原有记录
   	   floatStore.baseParams = {
   	   	   schemeId : floatSchemeIdValue,
   	   	   nodeType:type,
   	   	   add:true
   	   };
       Ext.getBody().mask("加载中...");
   	   floatStore.load({
   	   		params:{start:0,limit:20},
   	   	    callback: function(records, options, success){
               if(null != records && 0 < records.length){
              	 	Ext.Ajax.request({
						url : "./baseapp/floatLoadScheme!floatLoadScheme.action",
						params : {
							schemeId:floatSchemeIdValue
						},
						success : function(response) {
							var records = Ext.decode(response.responseText);
							if(records.floatDownCtrl.floatDownPercent <0){
								records.floatDownCtrl.floatDownPercent = -records.floatDownCtrl.floatDownPercent;
								floatPanel_3.find('name','radioGroup')[0].setValue('down');
							} else {
								floatPanel_3.find('name','radioGroup')[0].setValue('up');
							}
                       		floatPanel.getForm().setValues(records.floatDownCtrl);
                       		var floatDownCtrl = records.floatDownCtrl;//方案明细列表
                       		if(floatDownCtrl.alertTime1!=""&& floatDownCtrl.alertTime1 !=0){
                       			floatPanel_1.find('name','box1')[0].setValue(true);
                       		} else {
                       			floatPanel_1.find('name','box1')[0].setValue(false);
                       		}
                       		if(floatDownCtrl.alertTime2 !=""&&floatDownCtrl.alertTime2 !=0){
                       			floatPanel_1.find('name','box2')[0].setValue(true);
                       		} else {
                       			floatPanel_1.find('name','box2')[0].setValue(false);
                       		}
                       		if(floatDownCtrl.alertTime3!=""&& floatDownCtrl.alertTime3 !=0){
                       			floatPanel_1.find('name','box3')[0].setValue(true);
                       		} else {
                       			floatPanel_1.find('name','box3')[0].setValue(false);
                       		}
                       		if(floatDownCtrl.alertTime4!=""&& floatDownCtrl.alertTime4 !=0){
                       			floatPanel_1.find('name','box4')[0].setValue(true);
                       		} else {
                       			floatPanel_1.find('name','box4')[0].setValue(false);
                       		}
                       		Ext.getBody().unmask();
						}
                   });
               } else {
					floatPanel.getForm().reset();	
					Ext.getBody().unmask();
               }
           },
           add:true
   	   });
       floatLoadSchemeWindow.hide();
    };
    /*下浮控方案加载store*/
    var floatLoadStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url:'./baseapp/pwrctrlScheme!floatSchemeList.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'schemeList',
				idProperty: 'ctrlSchemeId'
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
    var floatLoadScheme2 =new Ext.Panel({
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
                store: floatLoadStore
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
   	           	   floatLoadSchemeWindow.hide();
  		       }
   	       }] 
   	    }]
    });

//  方案加载窗口
    var floatLoadSchemeWindow = new Ext.Window({
        name:'floatLoadSchemeWindow',
        modal:true,
        height:250,
        width:400,
        closeAction:'hide',
        resizable:false,
        layout:'border',
        title:'方案加载',
        items:[floatLoadScheme1,floatLoadScheme2]
    }); 
    
/*// 	定义保存为方案面板组件**********************************************************************************************************
//	开始日期
	var floatNewStartDate = new Ext.form.DateField({
		fieldLabel : '限电日从',
		name : 'newStartDate',
		anchor : '90%',
	    value : new Date(),
	    labelSeparator:''
    });
    //结束日期
    var floatNewEndDate = new Ext.form.DateField({
		fieldLabel : '到',
		name : 'newEndDate',
		anchor : '76%',
	    value : new Date().add(Date.DAY,30),
	    labelSeparator:''
    });
    //修改开始日期
    var floatUpdateStartDate = new Ext.form.DateField({
		fieldLabel : '限电日从',
		name : 'newStartDate',
		anchor : '90%',
	    value : new Date(),
	    labelSeparator:''
    });
    //修改结束日期
    var floatUpdateEndDate = new Ext.form.DateField({
		fieldLabel : '到',
		name : 'newEndDate',
		anchor : '76%',
	    value : new Date().add(Date.DAY,30),
	    labelSeparator:''
    });
    //厂休控方案备注
    var floatNewSchemeRemark = new Ext.form.TextArea({
    	width:255,
    	height:80,
        fieldLabel:'备    注'
    });
    var floatUpdateSchemeRemark = new Ext.form.TextArea({
    	width:255,
    	height:80,
        fieldLabel:'备    注'
    });
    //厂休控调控负荷
	var floatNewSchemeCtrlLoad = new Ext.form.TextField({
            width:60,
            fieldLabel:'调控负荷(KW)'
	});
	var floatUpdateSchemeCtrlLoad = new Ext.form.TextField({
            width:60,
            fieldLabel:'调控负荷(KW)'
	});
    //限电类型Store
	var floatLimitTypeStore = new Ext.data.JsonStore({
	    url : "./baseapp/energyControl!loadLimitType.action",
	    fields : [ 'limitType', 'limitTypeName' ],
	    root : "limitTypeList"
    });
    //限电类型Store
	var floatUpdateLimitTypeStore = new Ext.data.JsonStore({
	    url : "./baseapp/energyControl!loadLimitType.action",
	    fields : [ 'limitType', 'limitTypeName' ],
	    root : "limitTypeList"
    });
	//定义购电标志
	var floatLimitTypeComboBox = new Ext.form.ComboBox({
				fieldLabel : '限电类型',
				store : floatLimitTypeStore,
				displayField : 'limitTypeName',
	            valueField : 'limitType',
				bodyStyle: 'padding:10px;',
				triggerAction : 'all',
				mode : 'remote',
				editable:false,
				width: 103,
				forceSelection : true,
				emptyText : '请选择类型',
				selectOnFocus : true,
				labelSeparator:''
	});
	//下浮控控名称下拉列表
	var floatSchemeComBox = new Ext.form.ComboBox({
			store : floatLoadStore,
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
	floatSchemeComBox.on('select',function(){
		var scheme = floatLoadStore.getById(floatSchemeComBox.getValue());
		
		floatUpdateLimitTypeComboBox.setValue(scheme.get('limitType'));
		floatUpdateSchemeCtrlLoad.setValue(scheme.get('ctrlLoad'));
		floatUpdateStartDate.setValue(Date.parseDate(scheme.get('ctrlDateStart'),'Y-m-d\\TH:i:s'));
		floatUpdateEndDate.setValue(Date.parseDate(scheme.get('ctrlDateEnd'),'Y-m-d\\TH:i:s'));
		floatUpdateSchemeRemark.setValue(scheme.get('schemeRemark'));
	});
	
	var floatUpdateLimitTypeComboBox = new Ext.form.ComboBox({
				fieldLabel : '限电类型',
				store : floatUpdateLimitTypeStore,
				displayField : 'limitTypeName',
	            valueField : 'limitType',
				bodyStyle: 'padding:10px;',
				triggerAction : 'all',
				mode : 'remote',
				editable:false,
				width: 103,
				forceSelection : true,
				emptyText : '请选择类型',
				selectOnFocus : true,
				labelSeparator:''
	});
	
	//下浮控方案label
	var floatSchemeLabel = new Ext.form.TextField({
	        fieldLabel:'方案名称',
   	        value:'功率下浮控',
   	        readOnly:true,
   	        labelSeparator:'',
            anchor:'95%' 
	});
	//方案名
	var floatSchemeName = new Ext.form.TextField({
           whdth:142
	});
	
	//定义radio选择组
    var floatEcRadioGroup = new Ext.form.RadioGroup({
	    width : 200,
	    height :20,
	    items : [new Ext.form.Radio({
		    boxLabel : '另存为方案',
		    name : 'ec-radioGroup',
		    inputValue : '1',
		    checked : true,
		    handler:function(checkbox, checked){
		    	if(checked){
		    		floatSaveSchemePenl.layout.setActiveItem(0);
		    	}
		    }
	    }), new Ext.form.Radio({
		    boxLabel : '保存方案',
		    name : 'ec-radioGroup',
		    inputValue : '2',
		    handler:function(checkbox, checked){
		    	if(checked){
		    		floatSaveSchemePenl.layout.setActiveItem(1);
		    	}
		    }
	    })]
    });
    
      //下浮控radio
     var floatSchemeRedioPanel = new Ext.Panel({
    	layout:'form',
        border : false,
        bodyStyle : 'padding:5px 0px 0px 35px',
        labelAlign: "right",
        labelWidth:1,
        items:[floatEcRadioGroup] 
    });
    
	var floatSchemeSaveShowButton = new Ext.Button({
		text:'确定',
 	    handler:function(){
     	 	var limitType = floatLimitTypeComboBox.getValue();
 	    	if(limitType==null || limitType=='') {
 	    		Ext.MessageBox.alert("提示", "请选择限电类型！");
	     	    return;
 	    	}
 	    	var ctrlLoad = floatNewSchemeCtrlLoad.getValue();
			if(ctrlLoad ==null || ctrlLoad=='') {
				Ext.MessageBox.alert("提示", "请输入调控负荷！");
	     	    return;
			}
            var users = floatSM.getSelections();
           
	     	var pwrCtrlList = new Array();//向后台传的参数数组
	     	if (Ext.getCmp('plan_floatGsselectAllcb').checked) { //如果选中全选按钮
				for (var i = 0; i < floatStore.getCount(); i++) {
					pwrCtrlList[i] =floatStore.getAt(i).data.orgNo +'`' + floatStore.getAt(i).data.consNo +
						'`' + floatStore.getAt(i).data.tmnlAssetNo + '`' + floatStore.getAt(i).data.totalNo + '`' + floatStore.getAt(i).data.protocolCode;
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
            var alertTime1 = floatPanel_1.find('name','alertTime1')[0].getValue();
            var alertTime2 = floatPanel_1.find('name','alertTime2')[0].getValue();
            var alertTime3 = floatPanel_1.find('name','alertTime3')[0].getValue();
            var alertTime4 = floatPanel_1.find('name','alertTime4')[0].getValue();
            
            var floatDownWtime = floatPanel_2.find('name','floatDownWtime')[0].getValue();
            var powerFreezetime = floatPanel_2.find('name','powerFreezetime')[0].getValue();
            var floatDownPercent = floatPanel_3.find('name','floatDownPercent')[0].getValue();
            var radioGroup = floatPanel_3.find('name','radioGroup')[0].getValue().getRawValue();
           	if(radioGroup == "down") {
           		floatDownPercent = -floatDownPercent;
           	}
            var ctrlTime = floatPanel_3.find('name','ctrlTime')[0].getValue();
            
			var ctrlSchemeName = floatSchemeLabel.getValue() + '-' + floatSchemeName.getValue();
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
	  		     		url:'./baseapp/addPwrCtrlScheme!addFloatPwrCtrlScheme.action',
	  		     		params : {
	  		     			curveNo:constcombo.getValue(),
	  		     			pwrCtrlList:pwrCtrlList,
	  		     			alertTime1:alertTime1,
	  		     			alertTime2:alertTime2,
	  		     			alertTime3:alertTime3,
	  		     			alertTime4:alertTime4,
	  		     			floatDownWtime:floatDownWtime,
	  		     			powerFreezetime:powerFreezetime,
	  		     			floatDownPercent:floatDownPercent,
	  		     			ctrlTime:ctrlTime,
	  		     			'scheme.ctrlSchemeName':ctrlSchemeName,
	  		     			'scheme.limitType':limitType,
	  		     			'scheme.schemeRemark':floatNewSchemeRemark.getValue(),
	  		     			'scheme.ctrlLoad':ctrlLoad,
	  		     			'scheme.ctrlDateStart':floatNewStartDate.getValue(),
	  		     			'scheme.ctrlDateEnd':floatNewEndDate.getValue()
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
 	        floatSaveScheme.hide();	
 	    }
	});
	
	//下浮控方案保存取消按钮
	var floatSchemeSaveHideButton = new Ext.Button({
		text:'退出',
 	    handler:function(){
 	        floatSaveScheme.hide();	
 	    }
	});
	//下浮控方案修改确定按钮
	var floatSchemeUpdateShowButton = new Ext.Button({
		text:'确定',
 	    handler:function(){
 	    	var ctrlSchemeId= floatSchemeComBox.getValue();
 	    	var ctrlSchemeName = floatSchemeComBox.getRawValue();
			if(ctrlSchemeId==null|| ctrlSchemeId=="") {
				Ext.MessageBox.alert("提示","请选择方案！");
	     	    return;
			}
 	    	var limitType = floatUpdateLimitTypeComboBox.getValue();
 	    	if(limitType==null || limitType=='') {
 	    		Ext.MessageBox.alert("提示", "请选择限电类型！");
	     	    return;
 	    	}
 	    	var ctrlLoad = floatUpdateSchemeCtrlLoad.getValue();
			if(ctrlLoad ==null || ctrlLoad=='') {
				Ext.MessageBox.alert("提示", "请输入调控负荷！");
	     	    return;
			}
            var users = floatSM.getSelections();
           var pwrCtrlList = new Array();//向后台传的参数数组
	     	if (Ext.getCmp('plan_floatGsselectAllcb').checked) { //如果选中全选按钮
				for (var i = 0; i < floatStore.getCount(); i++) {
					pwrCtrlList[i] =floatStore.getAt(i).data.orgNo +'`' + floatStore.getAt(i).data.consNo +
						'`' + floatStore.getAt(i).data.tmnlAssetNo + '`' + floatStore.getAt(i).data.totalNo + '`' + floatStore.getAt(i).data.protocolCode;
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
            
            var alertTime1 = floatPanel_1.find('name','alertTime1')[0].getValue();
            var alertTime2 = floatPanel_1.find('name','alertTime2')[0].getValue();
            var alertTime3 = floatPanel_1.find('name','alertTime3')[0].getValue();
            var alertTime4 = floatPanel_1.find('name','alertTime4')[0].getValue();
            
            var floatDownWtime = floatPanel_2.find('name','floatDownWtime')[0].getValue();
            var powerFreezetime = floatPanel_2.find('name','powerFreezetime')[0].getValue();
            var floatDownPercent = floatPanel_3.find('name','floatDownPercent')[0].getValue();
            var radioGroup = floatPanel_3.find('name','radioGroup')[0].getValue().getRawValue();
           	if(radioGroup == "down") {
           		floatDownPercent = -floatDownPercent;
           	}
            var ctrlTime = floatPanel_3.find('name','ctrlTime')[0].getValue();
            
 	    	Ext.Ajax.request({
	     		url:'./baseapp/addPwrCtrlScheme!addFloatPwrCtrlScheme.action',
	     		params : {
	     			curveNo:constcombo.getValue(),
	     			pwrCtrlList:pwrCtrlList,
	     			alertTime1:alertTime1,
	     			alertTime2:alertTime2,
	     			alertTime3:alertTime3,
	     			alertTime4:alertTime4,
	     			floatDownWtime:floatDownWtime,
	     			powerFreezetime:powerFreezetime,
	     			floatDownPercent:floatDownPercent,
	     			ctrlTime:ctrlTime,
	     			'scheme.ctrlSchemeId':ctrlSchemeId,
	     			'scheme.ctrlSchemeName':ctrlSchemeName,
	     			'scheme.limitType':limitType,
	     			'scheme.schemeRemark':floatUpdateSchemeRemark.getValue(),
	  		     	'scheme.ctrlLoad':ctrlLoad,
	     			'scheme.ctrlDateStart':floatUpdateStartDate.getValue(),
	     			'scheme.ctrlDateEnd':floatUpdateEndDate.getValue()
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
	     	viewPanel.getEl().mask('保存中...');
 	        floatSaveScheme.hide();	
 	    }
	});
	
	//下浮控方案保存取消按钮
	var floatSchemeUpdateHideButton = new Ext.Button({
		text:'退出',
 	    handler:function(){
 	        floatSaveScheme.hide();	
 	    }
	});
	
	var floatUpdateSchemePnl1=new Ext.Panel({
        height:250,
        border : false,
        layout:'form',
        buttonAlign:'center',
        items:[{
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 5px',
   	            labelAlign: "right",
   	            labelWidth:70,
   	            items:[floatSchemeComBox] 
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
   	            items:[floatUpdateLimitTypeComboBox] 
   	        }, {
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            labelWidth:77,
   	            items:[floatUpdateSchemeCtrlLoad] 
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
   	            items:[floatUpdateStartDate] 
   	        }, {
   	            columnWidth:.45,
   	            layout:'form',
   	            border : false,
   	            labelWidth:10,
   	            labelAlign: "right",
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            items:[floatUpdateEndDate] 
   	        }]
   	    },{
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 5px',
   	            labelWidth:70,
   	             labelAlign: "right",
   	            items:[floatUpdateSchemeRemark]
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
   	            items:[floatSchemeUpdateShowButton] 
   	        }, {
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            hideLabels:true,
   	            items:[floatSchemeUpdateHideButton] 
   	        }]
   	    }]
    });
    
    //方案修改panel
    var floatSaveSchemePnl1=new Ext.Panel({
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
   	            bodyStyle : 'padding:5px 0px 0px 5px',
   	            labelAlign: "right",
   	            labelWidth:70,
   	            items:[floatSchemeLabel] 
   	        }, {
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            hideLabels:true,
   	            items:[floatSchemeName] 
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
   	            items:[floatLimitTypeComboBox] 
   	        }, {
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            labelWidth:77,
   	            items:[floatNewSchemeCtrlLoad] 
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
   	            items:[floatNewStartDate] 
   	        }, {
   	            columnWidth:.45,
   	            layout:'form',
   	            border : false,
   	            labelWidth:10,
   	            labelAlign: "right",
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            items:[floatNewEndDate] 
   	        }]
   	    	},{
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 5px',
   	            labelWidth:70,
   	             labelAlign: "right",
   	            items:[floatNewSchemeRemark]
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
   	            items:[floatSchemeSaveShowButton] 
   	        }, {
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            hideLabels:true,
   	            items:[floatSchemeSaveHideButton] 
   	        }]
   	    }]
    });
    
       //	    定义保存为方案面板组件
    var floatSaveSchemePenl=new Ext.Panel({
        layout:'card',
        activeItem : 0,
        border:false,
        items:[floatSaveSchemePnl1,floatUpdateSchemePnl1]
    });
    
    var floatSchemePenal = new Ext.Panel({
    	layout:'form',
    	border:false,
    	items:[floatSchemeRedioPanel, floatSaveSchemePenl]
    });
    
//	    定义保存为方案面板组件
    var floatSaveSchemePnl=new Ext.Panel({
        layout:'form',
        border:false,
        items:[floatSchemePenal]
    });

    //保存方案跳出窗口
    var floatSaveScheme = new Ext.Window({
        name:'floatSaveScheme',
        height:285,
        width:400,
        closeAction:'hide',
        resizable:false,
        layout:'fit',	        
        title:'保存方案',
        items:[floatSaveSchemePnl]
    });

//----------------------------------------------------------下浮控-执行方案   结束	
*///-----------------------------下浮控 列表   开始				
	var floatStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url:'./baseapp/pwrctrlSchemeExec!queryFloatSchemeExec.action'
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
	var splite_floatStore = new Ext.data.Store({
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

	function floatStoreToArray(valStore) {
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
	
	var floatSM = new Ext.grid.CheckboxSelectionModel();
	var floatRowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(floatStore && floatStore.lastOptions && floatStore.lastOptions.params){
				startRow = floatStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	var floatCM = new Ext.grid.ColumnModel([floatRowNum, floatSM, 
			//{header : '选择',	 dataIndex : 'keyId', sortable : true,	hidden:true, align : 'center'}, 
			{header : '供电单位', dataIndex : 'orgName', sortable : true, renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			},align : 'center',defaultWidth:140}, 
//			{header : '供电单位编号', sortable:true, dataIndex:'orgNo', align:'center', hidden:true}, 
			//{header : '终端资产号', 	sortable:true, dataIndex:'tmnlAssetNo', align:'center',hidden:true},
			{header : '用户编号', dataIndex : 'consNo',	sortable : true,align : 'center'}, 
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
			   		} else {
			   		    return "";
			   		}
				},align : 'center'} 
			//{header : '规约编码',	dataIndex : 'protocolCode',	sortable : true,	hidden:true,	align : 'center'}, 
			]);

	//校验下浮控参数
	function floatExecCheck(){
		var floatDownWtime = floatPanel_2.find('name','floatDownWtime')[0].getValue();
        var powerFreezetime = floatPanel_2.find('name','powerFreezetime')[0].getValue();
        var floatDownPercent = floatPanel_3.find('name','floatDownPercent')[0].getValue();
        var ctrlTime = floatPanel_3.find('name','ctrlTime')[0].getValue();
        if(floatDownWtime==null|| floatDownWtime==""){
        	Ext.MessageBox.alert("提示","当前功率下浮控定值滑差时间不能为空");
        	floatDownWtime.validateValue(false);
        	return false;
        }
        if(powerFreezetime==null ||powerFreezetime=="") {
        	Ext.MessageBox.alert("提示","控后有功功率冻结延时时间不能为空");
        	powerFreezetime.validateValue(false);
        	return false;
        }
        if(floatDownPercent==null ||floatDownPercent=="") {
        	Ext.MessageBox.alert("提示","当前功率下浮控定值浮动系数不能为空");
        	floatDownPercent.validateValue(false);
        	return false;
        }
        if(ctrlTime==null ||ctrlTime=="") {
        	Ext.MessageBox.alert("提示","当前功率下浮控控制时间不能为空");
        	ctrlTime.validateValue(false);
        	return false;
        }
        return true;
	}
	
	var floatGrid = new Ext.grid.GridPanel({
		store : splite_floatStore,
		region:'center',
		loadMask:true,
		cm : floatCM,
		sm : floatSM,
		tbar : [{
			  		text:'加载方案',
			  		handler : function() {
		  		     	floatLoadStore.load();
		  		       	floatLoadSchemeWindow.show();
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
						        floatLimitTypeStore.loadData(result);
		  		     		},
		  		     		failure : function(){
		  		     		   Ext.MessageBox.alert("提示","失败");
		  		     		   return;
		  		     		}
		  		     	});
		  		        floatSaveScheme.show();*/
			            var users = floatSM.getSelections();
			           
				     	var pwrCtrlList = new Array();//向后台传的参数数组
				     	if (Ext.getCmp('plan_floatGsselectAllcb').checked) { //如果选中全选按钮
							for (var i = 0; i < floatStore.getCount(); i++) {
								pwrCtrlList[i] =floatStore.getAt(i).data.orgNo +'`' + floatStore.getAt(i).data.consNo +
									'`' + floatStore.getAt(i).data.tmnlAssetNo + '`' + floatStore.getAt(i).data.totalNo + '`' + floatStore.getAt(i).data.protocolCode;
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
				     	if(!floatPanel.getForm().isValid()){
				     		 Ext.MessageBox.alert("提示","存在无效参数！");
				     	     return;
				     	}
			            var alertTime1 = floatPanel_1.find('name','alertTime1')[0].getValue();
			            var alertTime2 = floatPanel_1.find('name','alertTime2')[0].getValue();
			            var alertTime3 = floatPanel_1.find('name','alertTime3')[0].getValue();
			            var alertTime4 = floatPanel_1.find('name','alertTime4')[0].getValue();
			            
			            var floatDownWtime = floatPanel_2.find('name','floatDownWtime')[0].getValue();
			            var powerFreezetime = floatPanel_2.find('name','powerFreezetime')[0].getValue();
			            var floatDownPercent = floatPanel_3.find('name','floatDownPercent')[0].getValue();
			            var radioGroup = floatPanel_3.find('name','radioGroup')[0].getValue().getRawValue();
			           	if(radioGroup == "down") {
			           		floatDownPercent = -floatDownPercent;
			           	}
			            var ctrlTime = floatPanel_3.find('name','ctrlTime')[0].getValue();
			            
			            Ext.getBody().mask("保存中...");
						//保存当前功率下浮控方案明细
		     	    	Ext.Ajax.request({
		  		     		url:'./baseapp/addPwrCtrlScheme!addFloatCtrlScheme.action',
		  		     		params : {
		  		     			//curveNo:constcombo.getValue(),
		  		     			pwrCtrlList:pwrCtrlList,
		  		     			alertTime1:alertTime1,
		  		     			alertTime2:alertTime2,
		  		     			alertTime3:alertTime3,
		  		     			alertTime4:alertTime4,
		  		     			floatDownWtime:floatDownWtime,
		  		     			powerFreezetime:powerFreezetime,
		  		     			floatDownPercent:floatDownPercent,
		  		     			ctrlTime:ctrlTime,
		  		     			schemeId:staticFloatSchemeId
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
					id : 'plan_floatGsselectAllcb',
					boxLabel : '全选',
					name : 'gsselectAllcb',
					checked : false,
					listeners : {
							'check' : function(r, c) {
								if (c) {
									floatSM.selectAll();
									lockGrid('float');
								}else {
									unlockGrid('float');
									floatSM.clearSelections();
								}
							}
						}
					}, '-',{
			  		text : '删除选中用户',
					iconCls: 'cancel',
					handler : function() {
					if (Ext.getCmp('plan_floatGsselectAllcb').checked) {
						floatStore.removeAll(true);
						unlockGrid('float');
						Ext.getCmp('plan_floatGsselectAllcb').setValue(false);
					} else {
						var recs = floatSM.getSelections();
						for (var i = 0; i < recs.length; i = i + 1) {
							floatStore.remove(floatStore.getById(recs[i].data.keyId));
						}
					}
					splite_floatStore.proxy = new Ext.ux.data.PagingMemoryProxy(floatStoreToArray(floatStore));
					splite_floatStore.load({
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
						if (Ext.getCmp('plan_floatGsselectAllcb').checked) {
							for (var i = 0; i < floatStore.getCount(); i++) {
								var tmnl = floatStore.getAt(i).get('consNo')
										+ '`'+ floatStore.getAt(i).get('tmnlAssetNo');
								groupTmnlArray[i] = tmnl;
							}
						} else {
							var recs = floatSM.getSelections();
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
							if (Ext.getCmp('plan_floatGsselectAllcb').checked) {
								Ext.getCmp('plan_floatGsselectAllcb').setValue(false);
								floatSM.selectAll();
							}
						}
	                 }
				}/*, '-', {
					 iconCls: 'minus',
					text : '删除成功用户',
					handler:function(){
			     		for (var i = floatStore.getCount()-1; i >=0; i--) {
			     			var execFlag = floatStore.getAt(i).get('execFlag');
			     			if("2"==execFlag || "1"==execFlag ||"0"==execFlag)
								floatStore.removeAt(i);
						}
						splite_floatStore.proxy = new Ext.ux.data.PagingMemoryProxy(floatStoreToArray(floatStore));
						splite_floatStore.load({
									params : {
										start : 0,
										limit : DEFAULT_PAGE_SIZE
									}
						});
						if(Ext.getCmp('plan_floatGsselectAllcb').checked) {
							Ext.getCmp('plan_floatGsselectAllcb').setValue(false);
							floatSM.selectAll();
						}
					 }
				}*/],
			bbar : new Ext.ux.MyToolbar({
				store : splite_floatStore,
				allStore : floatStore,
				enableExpAll : true, // excel导出全部数据
				expAllText : "全部",
				enableExpPage : true, // excel仅导出当前页
				expPageText : "当前页"
			})
	});		
  		 
//-----------------------------------------下浮控 列表   结束			
	var floatRadio_down = new Ext.Panel({
		border:false,
		layout:'border',
		region:'center',
		items:[floatGrid]
	});
			
	var floatRadio = new Ext.Panel({//下浮控radio内容
		id:'plan_pwrctrl_float',
		border:false,
		layout:'border',
		items:[floatPanel, floatRadio_down]
	});			
	//用户列表默认选中	
	//下浮控========================	
	floatStore.on('load',function(thisstore, recs, obj){
		splite_floatStore.proxy = new Ext.ux.data.PagingMemoryProxy(floatStoreToArray(thisstore));
		splite_floatStore.load({
				params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
			});
		floatSM.selectAll();
		floatGrid.doLayout();
	});
	
	splite_floatStore.on('load',function(){
		floatSM.selectAll();
	});
	
	renderModel(floatRadio,'当前功率下浮控方案编制');
	
	function clickEvent(obj,type){
	   Ext.getBody().mask('数据加载中...');	
		//下浮控
	    floatStore.baseParams = {
				subsId : obj.subsId,
				groupNo : obj.groupNo,
				lineId : obj.lineId,
				tmnlAssetNo : obj.tmnlAssetNo,
				nodeType : type,
				orgNo : obj.orgNo,
				orgType : obj.orgType
			};
	    floatStore.load({
	    	callback : function(recs, options, success) {
	    		Ext.getBody().unmask();	
				if (success)
					floatGrid.getSelectionModel().selectRecords(recs, true);
			},
	  	    add: true
	    });
	}
	
//  监听左边树点击事件
    var treeListener = new LeftTreeListener({
	    modelName : '当前功率下浮控方案编制',
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
	if(typeof(staticFloatSchemeId) != 'undefined') {
			floatStore.removeAll();//加载方案时，先清除列表原有记录
	   	    floatStore.baseParams = {
	   	   	   schemeId : staticFloatSchemeId,
	   	   	   nodeType:'schemeId',
	   	   	   add:true
	   	   };
	   	   Ext.getBody().mask("加载中...");
	   	   floatStore.load({
	   	   		params:{start:0,limit:20},
	   	   	    callback: function(records, options, success){
	               if(null != records && 0 < records.length){
	              	 	Ext.Ajax.request({
							url : "./baseapp/floatLoadScheme!floatLoadScheme.action",
							params : {
								schemeId:staticFloatSchemeId
							},
							success : function(response) {
								var records = Ext.decode(response.responseText);
								if(records.floatDownCtrl.floatDownPercent <0){
									records.floatDownCtrl.floatDownPercent = -records.floatDownCtrl.floatDownPercent;
									floatPanel_3.find('name','radioGroup')[0].setValue('down');
								} else {
									floatPanel_3.find('name','radioGroup')[0].setValue('up');
								}
	                       		floatPanel.getForm().setValues(records.floatDownCtrl);
	                       		var floatDownCtrl = records.floatDownCtrl;//方案明细列表
	                       		if(floatDownCtrl.alertTime1!=""&& floatDownCtrl.alertTime1 !=0){
	                       			floatPanel_1.find('name','box1')[0].setValue(true);
	                       		} else {
	                       			floatPanel_1.find('name','box1')[0].setValue(false);
	                       		}
	                       		if(floatDownCtrl.alertTime2 !=""&&floatDownCtrl.alertTime2 !=0){
	                       			floatPanel_1.find('name','box2')[0].setValue(true);
	                       		} else {
	                       			floatPanel_1.find('name','box2')[0].setValue(false);
	                       		}
	                       		if(floatDownCtrl.alertTime3!=""&& floatDownCtrl.alertTime3 !=0){
	                       			floatPanel_1.find('name','box3')[0].setValue(true);
	                       		} else {
	                       			floatPanel_1.find('name','box3')[0].setValue(false);
	                       		}
	                       		if(floatDownCtrl.alertTime4!=""&& floatDownCtrl.alertTime4 !=0){
	                       			floatPanel_1.find('name','box4')[0].setValue(true);
	                       		} else {
	                       			floatPanel_1.find('name','box4')[0].setValue(false);
	                       		}
	                       		Ext.getBody().unmask();
							}
	                   });
	               } else {
						floatPanel.getForm().reset();	
						Ext.getBody().unmask();
	              }
	           },
	           add:true
	   	   });
	}
	
	staticFloatSchemeActivate = 0;
	
	Ext.getCmp('当前功率下浮控方案编制').on('activate', function() {
		if (staticFloatSchemeActivate == 1) {
			if(typeof(staticFloatSchemeName) != 'undefined'){
				floatPanel.setTitle('方案名：'+staticFloatSchemeName);
	 		}
	 		if(typeof(staticFloatSchemeId) != 'undefined'){
				floatStore.removeAll();//加载方案时，先清除列表原有记录
		   	    floatStore.baseParams = {
		   	   	   schemeId : staticFloatSchemeId,
		   	   	   nodeType:'schemeId',
		   	   	   add:true
		   	   };
		   	   Ext.getBody().mask("加载中...");
		   	   floatStore.load({
		   	   		params:{start:0,limit:20},
		   	   	    callback: function(records, options, success){
		               if(null != records && 0 < records.length){
		              	 	Ext.Ajax.request({
								url : "./baseapp/floatLoadScheme!floatLoadScheme.action",
								params : {
									schemeId:staticFloatSchemeId
								},
								success : function(response) {
									var records = Ext.decode(response.responseText);
									if(records.floatDownCtrl.floatDownPercent <0){
										records.floatDownCtrl.floatDownPercent = -records.floatDownCtrl.floatDownPercent;
										floatPanel_3.find('name','radioGroup')[0].setValue('down');
									} else {
										floatPanel_3.find('name','radioGroup')[0].setValue('up');
									}
		                       		floatPanel.getForm().setValues(records.floatDownCtrl);
		                       		var floatDownCtrl = records.floatDownCtrl;//方案明细列表
		                       		if(floatDownCtrl.alertTime1!=""&& floatDownCtrl.alertTime1 !=0){
		                       			floatPanel_1.find('name','box1')[0].setValue(true);
		                       		} else {
		                       			floatPanel_1.find('name','box1')[0].setValue(false);
		                       		}
		                       		if(floatDownCtrl.alertTime2 !=""&&floatDownCtrl.alertTime2 !=0){
		                       			floatPanel_1.find('name','box2')[0].setValue(true);
		                       		} else {
		                       			floatPanel_1.find('name','box2')[0].setValue(false);
		                       		}
		                       		if(floatDownCtrl.alertTime3!=""&& floatDownCtrl.alertTime3 !=0){
		                       			floatPanel_1.find('name','box3')[0].setValue(true);
		                       		} else {
		                       			floatPanel_1.find('name','box3')[0].setValue(false);
		                       		}
		                       		if(floatDownCtrl.alertTime4!=""&& floatDownCtrl.alertTime4 !=0){
		                       			floatPanel_1.find('name','box4')[0].setValue(true);
		                       		} else {
		                       			floatPanel_1.find('name','box4')[0].setValue(false);
		                       		}
		                       		Ext.getBody().unmask();
								}
		                   });
		               } else {
							floatPanel.getForm().reset();	
							Ext.getBody().unmask();
		               }
		           },
		           add:true
		   	   });
			}
	 		staticFloatSchemeActivate = 0;
		}
	});
});