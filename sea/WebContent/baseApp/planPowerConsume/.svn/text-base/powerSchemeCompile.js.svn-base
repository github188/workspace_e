planTmplist=[];//选择时段控模板明细变量，在下拉列表选择时赋值
planTemplate="";//选中的时段控模板变量，在下拉列表选择时赋值
cxkPlanTemplate="";
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
	       closeAction:'hide',
		   resizable:false,
		   layout:'fit',
		   items:[detailGrid]
    });
    
    detailWindow.show();
}

function origFrameQryShow(consNo,consName,tmnlAssetAddr){
	staticConsNo = consNo;
	staticConsName = consName;
	staticTmnlAssetAddr = tmnlAssetAddr;
	openTab("原始报文查询","./baseApp/dataGatherMan/origFrameQry.jsp");
}

//时段控参数模板store
var planTemplateStore = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
			url : 'baseapp/templateList!queryTemplateList.action'
		}),
	reader : new Ext.data.JsonReader({
			root : 'templateList',
			idProperty: 'templateName'
		}, [{name:'floatValue'},{name:'templateName'},{name:'templateId'},
			{name:'isValid'},{name:'isExec'},{name:'isValid'}])
});

//时段控明细定值store
var planConststore = new Ext.data.Store({
	proxy : new Ext.data.MemoryProxy(constdata), // 数据源
	reader : new Ext.data.ArrayReader({}, [{
			name : 'id'
		}, {
			name : 'name'
	}])
});

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
	    floatValue: function(val, field) {
	    	if(val<=-100||val>=100||isNaN(val)){
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
	})
	
	//************************************************************************顶端radio切换视图   开始
	var topRadio = new Ext.form.RadioGroup({
		width:350,
		items:[new Ext.form.Radio({
			boxLabel:'时段控',	
			name:'condRadio',
			inputValue:'period',
			checked:true,
			listeners:{
				check : function(checkbox, checked){
					if(checked) {
						sdkEcRadioGroup.setValue(1);
						panel_low.layout.setActiveItem(0);
					}
				}
			}
		}),{
			boxLabel:'厂休控',
			name:'condRadio',
			inputValue:'rest',
			listeners:{
				check : function(checkbox, checked){
					if(checked) {
						cxkEcRadioGroup.setValue(1);
						panel_low.layout.setActiveItem(1);
					}
				}
			}
		},{
			boxLabel:'当前功率下浮控',
			name:'condRadio',
			inputValue:'float',
			listeners:{
				check : function(checkbox, checked){
					if(checked) {
						floatEcRadioGroup.setValue(1);
						panel_low.layout.setActiveItem(2);
					}
				}
			}
		}]
	});
	var panel_top = new Ext.FormPanel({
					labelWidth:1,
					region:'north',
					height:40,
					bodyStyle:'padding:10px',
					items:[topRadio]	
	});
	
//*****************************************************************顶端radio切换视图   结束
//------------------------------------时段控   新建模板 按钮  开始
	var sdkmb1 = new Ext.Panel({
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
					id:'plan_powerPlanCompile_periodTemplateName',
					name : 'wPwrctrlTemplate.templateName',
					allowBlank:false,
					emptyText:'时段控-浦口1线控制',
					width:180
					}]
			},{
				columnWidth:.15,
				border:false,
				layout:'form',
				items:[{
					xtype:'button',
					handler:function(){
						var templateName = Ext.getCmp("plan_powerPlanCompile_periodTemplateName").getValue();
						if(templateName == "") {
							Ext.Msg.alert("提示","模板名称不能为空");
							return;
						}
						Ext.Ajax.request({
							url : 'baseapp/isExist!queryTemplateName.action',
							params : {
								templateName : templateName
							},
							success : function(response) {
								var exist = Ext.decode(response.responseText).exist;
								if (exist == true) {
									Ext.Msg.alert("提示","模板名称不能重复");
									return;
								}
								sdkmb3.find('name','template.templateName')[0].setValue(templateName);
								
								var condRadio = sdkmb3.find('name','radioGroup')[0].getValue().getRawValue();
								var floatValue= sdkmb3.find('name','template.floatValue')[0].getValue();
								
								if(condRadio == 1) {
									sdkmb3.find('name','template.floatValue')[0].setValue(-floatValue);
								}
								for (var i = 1; i < 9; i++) {
									var sectionStartH = sdkmb2_array[i-1].find('name','sectionStartH_'+i)[0].getValue();
									var sectionStartM = sdkmb2_array[i-1].find('name','sectionStartM_'+i)[0].getValue();
									
									var sectionEndH = sdkmb2_array[i-1].find('name','sectionEndH_'+i)[0].getValue();
									var sectionEndM = sdkmb2_array[i-1].find('name','sectionEndM_'+i)[0].getValue();
									
									var preSectionEndH;
									var preSectionEndM;
									if(i>1) {
										preSectionEndH = sdkmb2_array[i-2].find('name','sectionEndH_'+(i-1))[0].getValue();
										preSectionEndM = sdkmb2_array[i-2].find('name','sectionEndM_'+(i-1))[0].getValue();
									}
									var flag = 0
									if(sectionStartH=="" && sectionStartM=="" && sectionEndH=="" && sectionEndM==""){
										flag =1;
									}
									if(flag == 0) {
										if(sectionEndH < sectionStartH) {
											Ext.Msg.alert("提示","时段结束时间必须大于时段开始时间");
											return;
										} else if(sectionEndH == sectionStartH && sectionEndM <= sectionStartM) {
											Ext.Msg.alert("提示","时段结束时间必须大于时段开始时间");
											return;
										}
										if(preSectionEndH!=null || preSectionEndM!=null) {
											if(sectionStartH<preSectionEndH) {
												Ext.Msg.alert("提示","时段不能交叉");
												return;
											} else if(sectionStartH==preSectionEndH && sectionStartM<preSectionEndM) {
												Ext.Msg.alert("提示","时段不能交叉");
												return;
											}
										}
									}
									
									var sectionStart = (sectionStartH>9?sectionStartH:("0"+sectionStartH)) +
														":" +(sectionStartM>9?sectionStartM:("0"+sectionStartM));
									sdkmb2_array[i-1].find('name','id'+i+'.sectionStart')[0].setValue(sectionStart);
									
									var sectionEnd = (sectionEndH>9?sectionEndH:("0"+sectionEndH)) + ":" +
																(sectionEndM>9?sectionEndM:("0"+sectionEndM));
									sdkmb2_array[i-1].find('name','id'+i+'.sectionEnd')[0].setValue(sectionEnd);
								}
								
								for (var i = 1; i < 9; i++) {
									var sectionStart = sdkmb2_array[i-1].find('name','id'+i+'.sectionStart')[0].getValue();
									var sectionEnd = sdkmb2_array[i-1].find('name','id'+i+'.sectionEnd')[0].getValue();
									
									var sectionStartNum = 0; 
									var sectionEndNum = 0;
								}
								
								mbcs_sdkmb.getForm().submit({ // 提交表单
									method : 'post',
								    url:'baseapp/saveWPwrctrlTemplate!saveWPwrctrlTemplate.action',
									success : function(form, action) { // 返回成功
										Ext.Msg.alert("提示","模板保存成功");
										sdkmb.hide(); 
										planTemplate = "";//下拉列表当前选中模板对象置空
										templateNameComboBox.setValue();//下拉列表当前选中模板名称置空
										planTemplateStore.load();
									},
									failure : function(form, action) { // 返回失败
										Ext.Msg.alert("提示","模板保存出错");
									}
								});
							}
						});
					},
					text:'保存'
			}]
		}]
	});
	
	var sdkmb2_1 = new Ext.Panel({
				border : false,
				bodyStyle : 'padding:10px 0px 0px 0px',
				layout : 'column',
				items : [{
							columnWidth : .12,
							bodyStyle : 'padding:0px 0px 0px 20px',
							border : false,
							items : [{	xtype : 'label',text : '选择'}]
						}, {
							columnWidth : .22,
							border : false,
							items : [{	xtype : 'label',text : '开始时段'}]
						}, {
							columnWidth : .18,
							border : false,
							items : [{	xtype : 'label',text : '结束时段'}]
						}, {
							columnWidth : .12,
							border : false,
							items : [{xtype : 'label',text : '功率定值1(KW)'}]
						}, {
							columnWidth : .12,
							border : false,
							items : [{	xtype : 'label',text : '功率定值2(KW)'}]
						}, {
							columnWidth : .13,
							border : false,
							items : [{xtype : 'label',text : '功率定值3(KW)'}]
						}]
			});

	var sdkmb2_array = new  Array(8);
	for (var i = 1; i < 9; i++) {
		sdkmb2_array[i-1] = new Ext.Panel({
			border:false,
			layout:'column',
			bodyStyle:'padding: 10px 0px 0px 10px',
			items:[{
				columnWidth:.08,
				name :'id'+i+'.isValid',
				layout:'form',
				border:false,
				xtype:'checkbox',
				boxLabel:'时段'+i
			},{
				xtype:'hidden',
				id:'plan_newId'+i+'.sectionStart',
				name :'id'+i+'.sectionStart'
			},{
				xtype:'hidden',
				id:'plan_newId'+i+'.sectionEnd',
				name :'id'+i+'.sectionEnd'
			},{
				xtype:'hidden',
				name :'id'+i+'.sectionNo',
				value : i
			},{
				columnWidth:.2,
				layout:'column',
				border:false,
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
//									confirmTo:'newId'+(i-1)+'.sectionEnd',
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
//									confirmTo:'newId'+(i-1)+'.sectionEnd',
									accelerate: true
	            			})
						]
					}]
			},{
				columnWidth:.2,
				name :'sectionEnd_'+i,
				layout:'column',
				border:false,
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
//									confirmTo:'newId'+i+'.sectionStart',
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
//									confirmTo:'newId'+i+'.sectionStart',
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
					width:80
				}]
			}]
		});
	}	
	
	var sdkmb2 = new Ext.Panel({
		layout:'form',
		border:false,
		items:[sdkmb2_1,sdkmb2_array]
	});
	
	var sdkmb3 = new Ext.Panel({
		border:false,
		layout:'column',
		items:[{
			columnWidth:.15,
			layout:'form',
			style : "padding-left:10px",
			border:false,
			xtype:'checkbox',
			name:'template.isExec',
			boxLabel:'功率定值浮动系数(%)'
		},{
			xtype:'hidden',
			name :'template.templateName'
		},{
			columnWidth:.15,
			border:false,
			layout:'form',
			width:100,
			name :'radioGroup',
			xtype:'radiogroup',
			items:[{
						boxLabel:'下浮',
						inputValue:'1',
						name:'condRadio'
					},{
						boxLabel:'上浮',
						checked :'checked',
						inputValue:'2',
						name:'condRadio'
					}]
		},{
			columnWidth:.15,
			layout:'form',
			border:false,
			labelWidth:5,
			labelSeparator:' ',
			items:[{
				xtype:'textfield',
				name:'template.floatValue',
				fieldLabel:' ',
				width:80,
				vtype:'floatValue'
			}]
		}]
	});
	
	var mbcs_sdkmb = new Ext.FormPanel({
		region:'center',
		layout:'form',
		title:'模板参数',
		autoScroll : true,
		items:[sdkmb2,sdkmb3]
	});
	
	var sdkmb = new Ext.Window({
       title:'时段控模板信息',
       name:'sdkmb',
       height:475,
       width:790,
       closeAction:'hide',
	   resizable:false,
	   layout:'border',
	   items:[sdkmb1,mbcs_sdkmb]
    });
	
//-------------------------------------时段控   新建 按钮  结束-----------------------------------------------------------------
//-------------------------------------时段控   模板信息修改 按钮开始---------------------------------------------------------
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
					id:'plan_powerControl_sdkmbUpdate',
					readOnly:true,
					name : 'wPwrctrlTemplate.templateName',
					emptyText:'时段控-浦口1线控制',
					width:180
					}]
			},{
				columnWidth:.15,
				border:false,
				layout:'form',
				items:[{
					xtype:'button',
					handler:function(){
						var templateName = Ext.getCmp("plan_powerControl_sdkmbUpdate").getValue();
						
						sdkmbUpdate3.find('name','template.templateName')[0].setValue(templateName);
						
						var condRadio = sdkmbUpdate3.find('name','radioGroup')[0].getValue().getRawValue();
						var floatValue= sdkmbUpdate3.find('name','template.floatValue')[0].getValue();
						if(condRadio == 1) {
							sdkmbUpdate3.find('name','template.floatValue')[0].setValue(-floatValue);
						}
						for (var i = 1; i < 9; i++) {
							var sectionStartH = sdkmbUpdate2_array[i-1].find('name','sectionStartH_'+i)[0].getValue();
							var sectionStartM = sdkmbUpdate2_array[i-1].find('name','sectionStartM_'+i)[0].getValue();
							
							var sectionEndH = sdkmbUpdate2_array[i-1].find('name','sectionEndH_'+i)[0].getValue();
							var sectionEndM = sdkmbUpdate2_array[i-1].find('name','sectionEndM_'+i)[0].getValue();
							
							var preSectionEndH;
							var preSectionEndM;
							if(i>1) {
								preSectionEndH = sdkmbUpdate2_array[i-2].find('name','sectionEndH_'+(i-1))[0].getValue();
								preSectionEndM = sdkmbUpdate2_array[i-2].find('name','sectionEndM_'+(i-1))[0].getValue();
							}
							var flag = 0
							if(sectionStartH=="" && sectionStartM=="" && sectionEndH=="" && sectionEndM==""){
								flag =1;
							}
							if(flag == 0) {
								if(sectionEndH < sectionStartH) {
									Ext.Msg.alert("提示","时段结束时间必须大于时段开始时间");
									return;
								} else if(sectionEndH == sectionStartH && sectionEndM <= sectionStartM) {
									Ext.Msg.alert("提示","时段结束时间必须大于时段开始时间");
									return;
								}
								if(preSectionEndH!=null || preSectionEndM!=null) {
									if(sectionStartH<preSectionEndH) {
										Ext.Msg.alert("提示","时段不能交叉");
										return;
									} else if(sectionStartH==preSectionEndH && sectionStartM<preSectionEndM) {
										Ext.Msg.alert("提示","时段不能交叉");
										return;
									}
								}
							}
							var sectionStart = (sectionStartH>9?sectionStartH:("0"+sectionStartH)) + ":" +
														(sectionStartM>9?sectionStartM:("0"+sectionStartM));
							sdkmbUpdate2_array[i-1].find('name','id'+i+'.sectionStart')[0].setValue(sectionStart);
							
							var sectionEnd = (sectionEndH>9?sectionEndH:("0"+sectionEndH)) + ":" +
														(sectionEndM>9?sectionEndM:("0"+sectionEndM));
							sdkmbUpdate2_array[i-1].find('name','id'+i+'.sectionEnd')[0].setValue(sectionEnd);
						}
						
						mbcs_sdkmbUpdate.getForm().submit({ // 提交表单
							method : 'post',
						    url:'baseapp/updateWPwrctrlTemplate!updateWPwrctrlTemplate.action',
							success : function(form, action) { // 返回成功
								Ext.Msg.alert("提示","模板修改成功");
								sdkmbUpdate.hide(); 
								planTemplate = "";
								templateNameComboBox.setValue();
								planTemplateStore.load();
							},
							failure : function(form, action) { // 返回失败
								Ext.Msg.alert("提示","模板修改出错");
							}
						});
					},
					text:'保存'
			}]
		}]
	});
	
	var sdkmbUpdate2_1 = new Ext.Panel({
				border : false,
				bodyStyle : 'padding:10px 0px 0px 0px',
				layout : 'column',
				items : [{
							columnWidth : .12,
							bodyStyle : 'padding:0px 0px 0px 20px',
							border : false,
							items : [{xtype : 'label',text : '选择'}]
						}, {
							columnWidth : .2,
							border : false,
							items : [{xtype : 'label',text : '开始时段'}]
						}, {
							columnWidth : .18,
							border : false,
							items : [{xtype : 'label',text : '结束时段'}]
						}, {
							columnWidth : .12,
							border : false,
							items : [{xtype : 'label',text : '功率定值1(KW)'}]
						}, {
							columnWidth : .12,
							border : false,
							items : [{xtype : 'label',text : '功率定值2(KW)'}]
						}, {
							columnWidth : .12,
							border : false,
							items : [{xtype : 'label',text : '功率定值3(KW)'}]
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
				boxLabel:'时段'+i
			},{
				xtype:'hidden',
//				id:'plan_updateId'+i+'.sectionStart',
				name :'id'+i+'.sectionStart'
			},{
				xtype:'hidden',
//				id:'plan_updateId'+i+'.sectionEnd',
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
									accelerate: true
	            			})
						]
					}]
			},{
				columnWidth:.2,
				name :'sectionEnd_'+i,
				layout:'column',
				border:false,
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
		xtype:'radiogroup',
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
			columnWidth:.15,
			layout:'form',
			style : "padding-left:10px",
			border:false,
			xtype:'checkbox',
			name:'template.isExec',
			boxLabel:'功率定值浮动系数(%)'
		},{
			xtype:'hidden',
			name :'template.templateName'
		},sdkRadioGroup,{
			columnWidth:.15,
			layout:'form',
			border:false,
			labelWidth:5,
			labelSeparator:' ',
			items:[{
				xtype:'textfield',
				name:'template.floatValue',
				fieldLabel:' ',
				width:80,
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
       height:475,
       width:790,
       closeAction:'hide',
	   resizable:false,
	   layout:'border',
	   items:[sdkmbUpdate1,mbcs_sdkmbUpdate]
    });   
//---------------------------------------时段控 修改模板信息 按钮 结束---------------------------------------
//---------------------------------------时段控 panel 开始-----------------------------------------------------
	var templateNameComboBox = new  Ext.form.ComboBox({
		store : planTemplateStore,
		xtype:'combo',
		name :'combo_templateName',
		fieldLabel:'选择模板',
		valueField : 'templateName',
		editable : false,
		triggerAction : 'all',
		forceSelection : true,
		mode : 'remote',
		selectOnFocus : true,
		displayField : 'templateName',
		emptyText:'功率时段控',
		anchor:'85%'
	});
	
	function planTemplateDetail(loadOrLink) {
		Ext.Ajax.request({
				url : 'baseapp/templateDetails!queryTemplateDetails.action',
					params : {
						templateId : planTemplate.get('templateId')
					},
				success : function(response) {
					planTmplist = Ext.decode(response.responseText).detailList;
					
					var length = planTmplist.length;//获取模板明细有几个时段
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
					planConststore.removeAll();
					constdata=[];
					var const1 = 0;
					var const2 = 0;
					var const3 = 0;
					var arrayLength = 0;
					Ext.each(planTmplist,function(tmp){
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
					planConststore.loadData(constdata);
				}
		});
	}
	
	templateNameComboBox.on("collapse", function (o){
		var store = templateNameComboBox.getStore();
		
		planTemplate = store.getById(o.getRawValue());
		if(planTemplate ==null ||planTemplate==''){
			return;
		}
		planTemplateDetail(1);
	});
	

	//时段控参数召测panel
	var periodPanel = new Ext.Panel({
	  	title:'时段控',
	  	border:true,
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
				items:[templateNameComboBox]
			},{
				columnWidth:.12,
				border:false,
				layout:'form',
				items:[{
					xtype:'button',
					text:'模板信息',
					anchor:'90%',
					handler:function(){
						if(planTemplate == null || typeof(planTemplate) == 'undefined'||planTemplate=="") {
							Ext.Msg.alert('提示','请选择模板');
							return;
						}
						if(planTmplist == null) {
							Ext.Msg.alert('提示','选中模板没有明细信息，为无效模板');
							return;
						}
						Ext.getCmp("plan_powerControl_sdkmbUpdate").setValue(planTemplate.get('templateName'));
						var floatValue = planTemplate.get('floatValue');
						if(floatValue >= 0){
							sdkmbUpdate3.find('name','template.floatValue')[0].setValue(floatValue);
							sdkRadioGroup.setValue(2);
						} else {
							sdkmbUpdate3.find('name','template.floatValue')[0].setValue(-floatValue);
							sdkRadioGroup.setValue(1);
						}
						sdkmbUpdate3.find('name','template.isExec')[0].setValue(planTemplate.get('isExec'));
						
						Ext.each(planTmplist,function(tmp){
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
						if(planTmplist.length<8) {
							for(var i=planTmplist.length+1; i <9 ; i++) {
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
				}]
			},{
				columnWidth:.12,
				border:false,
				layout:'form',
				items:[{
					xtype:'button',
					anchor:'90%',
					text:'新建模板',
					handler:function(){
	        			sdkmb.show(); 
	        		}
				}]
			},{
				columnWidth:.12,
				border:false,
				layout:'form',
				items:[{
					xtype:'button',
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
	  		}]}]
	});
	
    var sdkFetch = function sdkFetch(){
    	var users = sdk_cszxSM.getSelections();
    	var pwrCtrlList = new Array();//向后台传的参数数组
     	var execNum = 0;//过滤终端的个数
     	var pwrCtrlListNum = 0;//pwrCtrlList的长度 用于自加
     	if (Ext.getCmp('plan_sdkGsselectAllcb').checked) { //如果选中全选按钮
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
     			if (Ext.getCmp('plan_sdkGsselectAllcb').checked) { //如果选中全选按钮
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
			        	record[i][0] = users[i].get('keyId')
                    	record[i][1] = users[i].get('orgName')
                    	record[i][3] = users[i].get('consNo')
                    	record[i][4] = users[i].get('consName')
                    	record[i][5] = users[i].get('terminalAddr')
                    	record[i][7] = users[i].get('totalNo')
			        	
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
    }
    
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
   	           		sdkSchemeLoad();
   	           }
   	       }] 
   	    }]
    });
    
    var sdkSchemeLoad = function sdkSchemeLoad(){
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
	   	   	   schemeId : sdkSchemeIdValue,
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
								schemeId : sdkSchemeIdValue
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
    //时段控方案加载
    var sdkLoadStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url:'./baseapp/pwrctrlScheme!sdkLoadScheme.action'
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
    
//定义保存为方案面板组件
//开始日期
	var sdkNewStartDate = new Ext.form.DateField({
		fieldLabel : '限电日从',
		name : 'newStartDate',
		anchor : '90%',
	    value : new Date(),
	    labelSeparator:''
    });
    //结束日期
    var sdkNewEndDate = new Ext.form.DateField({
		fieldLabel : '到',
		name : 'newEndDate',
		anchor : '76%',
	    value : new Date().add(Date.DAY,30),
	    labelSeparator:''
    });
    //	开始日期
	var sdkUpdateStartDate = new Ext.form.DateField({
		fieldLabel : '限电日从',
		name : 'newStartDate',
		anchor : '90%',
	    value : new Date(),
	    labelSeparator:''
    });
	//时段控方案备注
    var sdkNewSchemeRemark = new Ext.form.TextArea({
    	width:255,
    	height:80,
        fieldLabel:'备    注'
    });
    var sdkUpdateSchemeRemark = new Ext.form.TextArea({
    	width:255,
    	height:80,
        fieldLabel:'备    注'
    });
    
    //时段控调控负荷
	var sdkNewSchemeCtrlLoad = new Ext.form.TextField({
            width:60,
            fieldLabel:'调控负荷(KW)'
	});
	var sdkUpdateSchemeCtrlLoad = new Ext.form.TextField({
            width:60,
            fieldLabel:'调控负荷(KW)'
	});
    //结束日期
    var sdkUpdateEndDate = new Ext.form.DateField({
		fieldLabel : '到',
		name : 'newEndDate',
		anchor : '76%',
	    value : new Date().add(Date.DAY,30),
	    labelSeparator:''
    });
    //限电类型Store
	var sdkLimitTypeStore = new Ext.data.JsonStore({
	    url : "./baseapp/energyControl!loadLimitType.action",
	    fields : [ 'limitType', 'limitTypeName' ],
	    root : "limitTypeList"
    });
    //限电类型Store
	var sdkUpdateLimitTypeStore = new Ext.data.JsonStore({
	    url : "./baseapp/energyControl!loadLimitType.action",
	    fields : [ 'limitType', 'limitTypeName' ],
	    root : "limitTypeList"
    });
    //定义购电标志
	var sdkLimitTypeComboBox = new Ext.form.ComboBox({
				fieldLabel : '限电类型',
				store : sdkLimitTypeStore,
				typeAhead : true,
				editable : false,
				displayField : 'limitTypeName',
	            valueField : 'limitType',
				bodyStyle: 'padding:10px;',
				triggerAction : 'all',
				mode : 'remote',
				width: 103,
				forceSelection : true,
				emptyText : '--请选择类型--',
				selectOnFocus : true
	});
    
	//定义购电标志
	var sdkUpdateLimitTypeComboBox = new Ext.form.ComboBox({
				fieldLabel : '限电类型',
				store : sdkUpdateLimitTypeStore,
				typeAhead : true,
				editable : false,
				displayField : 'limitTypeName',
	            valueField : 'limitType',
				bodyStyle: 'padding:10px;',
				triggerAction : 'all',
				mode : 'remote',
				width: 103,
				forceSelection : true,
				emptyText : '--请选择类型--',
				selectOnFocus : true
	});
	//时段控方案label
	var sdkSchemeLabel = new Ext.form.TextField({
	        fieldLabel:'方案名称',
   	        value:'功率时段控',
   	        readOnly:true,
   	        labelSeparator:'',
            anchor:'95%' 
	});
	//方案名
	var sdkSchemeName = new Ext.form.TextField({
            width:142
	});
	//时段控名称下拉列表
	var sdkSchemeComBox = new Ext.form.ComboBox({
			store : sdkLoadStore,
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
	sdkSchemeComBox.on('select',function(){
		var scheme = sdkLoadStore.getById(sdkSchemeComBox.getValue());
		
		sdkUpdateLimitTypeComboBox.setValue(scheme.get('limitType'));
		sdkUpdateSchemeCtrlLoad.setValue(scheme.get('ctrlLoad'));
		sdkUpdateStartDate.setValue(Date.parseDate(scheme.get('ctrlDateStart'),'Y-m-d\\TH:i:s'));
		sdkUpdateEndDate.setValue(Date.parseDate(scheme.get('ctrlDateEnd'),'Y-m-d\\TH:i:s'));
		sdkUpdateSchemeRemark.setValue(scheme.get('schemeRemark'));
	});	
	
	//定义radio选择组
    var sdkEcRadioGroup = new Ext.form.RadioGroup({
	    width : 200,
	    height :20,
	    items : [new Ext.form.Radio({
		    boxLabel : '另存为方案',
		    name : 'ec-radioGroup',
		    inputValue : '1',
		    checked : true,
		    handler:function(checkbox, checked){
		    	if(checked){
		    		sdkSaveSchemePenl.layout.setActiveItem(0);
		    	}
		    }
	    }), new Ext.form.Radio({
		    boxLabel : '保存方案',
		    name : 'ec-radioGroup',
		    inputValue : '2',
		    handler:function(checkbox, checked){
		    	if(checked) {
		    		sdkSaveSchemePenl.layout.setActiveItem(1);
		    	}
		    }
	    })]
    });
	
    var schemeRedioPanel = new Ext.Panel({
    	layout:'form',
        border : false,
        bodyStyle : 'padding:5px 0px 0px 35px',
        labelAlign: "right",
        labelWidth:1,
        items:[sdkEcRadioGroup] 
    });
    
    //时段控方案保存按钮
    var sdkSaveSchemeButton = new Ext.Button({
    	text:'确定',
 	    handler:function(){
 	    	var templateName = templateNameComboBox.getRawValue();
			if(templateName==null || templateName==""){
				 Ext.MessageBox.alert("提示","请选择参数模板！");
	     	    return;
			}
            var users = sdk_cszxSM.getSelections();
            
            var pwrCtrlList = new Array();//向后台传的参数数组
	     	if (Ext.getCmp('plan_sdkGsselectAllcb').checked) {//如果选中全选按钮
				for (var i = 0; i < sdk_cszxStore.getCount(); i++) {
					pwrCtrlList[i] =sdk_cszxStore.getAt(i).data.orgNo +'`' + sdk_cszxStore.getAt(i).data.consNo +
						'`' + sdk_cszxStore.getAt(i).data.tmnlAssetNo + '`' + sdk_cszxStore.getAt(i).data.totalNo + '`' + sdk_cszxStore.getAt(i).data.protocolCode;
				}
	     	} else {
	     		if(null == users || users.length<=0){
		     	    Ext.MessageBox.alert("提示","请选择用户！");
		     	    return;
		     	}
		     	for(var i = 0; i < users.length; i++){
					pwrCtrlList[i] = users[i].get('orgNo')+'`'+users[i].get('consNo')+'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');
		     	}
	     	}
            
	     	var curveNo = constcombo.getValue();
	     	if(curveNo ==null || curveNo == ''){
	     		Ext.MessageBox.alert("提示","请选择方案定值号！");
	     	    return;
	     	}
	     	
			var sectionNoArray = executeCheckbox.getValue();
			
			if(sectionNoArray==null ||sectionNoArray.length<=0){
				Ext.MessageBox.alert("提示","请选择有效时段！");
	     	    return;
			}
			
			var sectionNo='';
			for (var i = 0; i < sectionNoArray.length; i++) {
				sectionNo += sectionNoArray[i].getRawValue();
			}
			//校验方案名
			var schemeName = sdkSchemeName.getValue();
			if(schemeName =='') {
				Ext.MessageBox.alert("提示","方案名不能为空");
	     	    return;
			}
			var ctrlSchemeName = sdkSchemeLabel.getValue() +'-'+ schemeName;
			
			var limitType = sdkLimitTypeComboBox.getValue();
			if(limitType ==null || limitType==''){
				Ext.MessageBox.alert("提示", "请选择限电类型！");
	     	    return;
			}
			var ctrlLoad = sdkNewSchemeCtrlLoad.getValue();
			if(ctrlLoad ==null || ctrlLoad=='') {
				Ext.MessageBox.alert("提示", "请输入调控负荷！");
	     	    return;
			}
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
	  		     		url:'./baseapp/addPwrCtrlScheme!addSdkPwrCtrlScheme.action',
	  		     		params : {
	  		     			curveNo:curveNo,
	  		     			sectionNo:sectionNo,
	  		     			templateName:templateName,
	  		     			pwrCtrlList:pwrCtrlList,
	  		     			'scheme.ctrlSchemeName':ctrlSchemeName,
	  		     			'scheme.limitType':limitType,
	  		     			'scheme.schemeRemark':sdkNewSchemeRemark.getValue(),
	  		     			'scheme.ctrlLoad':ctrlLoad,
	  		     			'scheme.ctrlDateStart':sdkNewStartDate.getValue(),
	  		     			'scheme.ctrlDateEnd':sdkNewEndDate.getValue()
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
 	        sdkSaveScheme.hide();	
 	    }
    });
    
    var sdkSchemeHideButton = new Ext.Button({
    	text:'退出',
 	    handler:function(){
 	        sdkSaveScheme.hide();	
 	    }
    });
    
    //时段控方案修改按钮
    var sdkUpdateSchemeButton = new Ext.Button({
    	text:'确定',
 	    handler:function(){
 	    	var ctrlSchemeId= sdkSchemeComBox.getValue();
 	    	var ctrlSchemeName = sdkSchemeComBox.getRawValue();
			if(ctrlSchemeId==null|| ctrlSchemeId=="") {
				Ext.MessageBox.alert("提示","请选择方案！");
	     	    return;
			}
 	    	var templateName = templateNameComboBox.getRawValue();
			if(templateName==null || templateName==""){
				Ext.MessageBox.alert("提示","请选择参数模板！");
	     	    return;
			}
            var users = sdk_cszxSM.getSelections();
            if(null == users || users.length==0){
	     	    Ext.MessageBox.alert("提示","请选择用户！");
	     	    return;
	     	}
	     	
	     	var curveNo = constcombo.getValue();
	     	if(curveNo ==null || curveNo == ''){
	     		Ext.MessageBox.alert("提示","请选择方案定值号！");
	     	    return;
	     	}
	     	
	     	var pwrCtrlList = new Array();//向后台传的参数数组
	     	if (Ext.getCmp('plan_sdkGsselectAllcb').checked) { //如果选中全选按钮
				for (var i = 0; i < sdk_cszxStore.getCount(); i++) {
					pwrCtrlList[i] =sdk_cszxStore.getAt(i).data.orgNo +'`' + sdk_cszxStore.getAt(i).data.consNo +
						'`' + sdk_cszxStore.getAt(i).data.tmnlAssetNo + '`' + sdk_cszxStore.getAt(i).data.totalNo + '`' + sdk_cszxStore.getAt(i).data.protocolCode;
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
            
			var sectionNoArray = executeCheckbox.getValue();
			
			if(sectionNoArray==null ||sectionNoArray.length<=0){
				Ext.MessageBox.alert("提示","请选择有效时段！");
	     	    return;
			}
			var sectionNo='';
			for (var i = 0; i < sectionNoArray.length; i++) {
				sectionNo += sectionNoArray[i].getRawValue();
			}
			
			var limitType = sdkUpdateLimitTypeComboBox.getValue();
			if(limitType ==null || limitType==''){
				Ext.MessageBox.alert("提示", "请选择限电类型！");
	     	    return;
			}
			var ctrlLoad = sdkUpdateSchemeCtrlLoad.getValue();
			if(ctrlLoad ==null || ctrlLoad=='') {
				Ext.MessageBox.alert("提示", "请输入调控负荷！");
	     	    return;
			}
			//修改时段控方案
 	    	Ext.Ajax.request({
	     		url:'./baseapp/addPwrCtrlScheme!addSdkPwrCtrlScheme.action',
	     		params : {
	     			curveNo:curveNo,
	     			sectionNo:sectionNo,
	     			templateName:templateName,
	     			pwrCtrlList:pwrCtrlList,
	     			'scheme.ctrlSchemeId':ctrlSchemeId,
	     			'scheme.ctrlSchemeName':ctrlSchemeName,
	     			'scheme.limitType':limitType,
	     			'scheme.schemeRemark':sdkUpdateSchemeRemark.getValue(),
	  		     	'scheme.ctrlLoad':ctrlLoad,
	     			'scheme.ctrlDateStart':sdkUpdateStartDate.getValue(),
	     			'scheme.ctrlDateEnd':sdkUpdateEndDate.getValue()
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
        	sdkSaveScheme.hide();	
        	
 	    }
    });
    
    var sdkUpdateSchemeHideButton = new Ext.Button({
    	text:'退出',
 	    handler:function(){
 	        sdkSaveScheme.hide();	
 	    }
    });
    
	var sdkSaveSchemePnl1=new Ext.Panel({
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
   	            items:[sdkSchemeLabel] 
   	        }, {
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:10px 0px 0px 0px',
   	            hideLabels:true,
   	            items:[sdkSchemeName]
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
   	            items:[sdkLimitTypeComboBox] 
   	        }, {
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            labelWidth:77,
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            items:[sdkNewSchemeCtrlLoad] 
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
   	            items:[sdkNewStartDate] 
   	        }, {
   	            columnWidth:.45,
   	            layout:'form',
   	            border : false,
   	            labelWidth:10,
   	            labelAlign: "right",
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            items:[sdkNewEndDate] 
   	        }]
   	    },{
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 5px',
   	            labelWidth:70,
   	             labelAlign: "right",
   	            items:[sdkNewSchemeRemark]
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
   	            items:[sdkSaveSchemeButton] 
   	        }, {
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            hideLabels:true,
   	            items:[sdkSchemeHideButton] 
   	        }]
   	    }]
    });
    
    var sdkUpdateSchemePnl1=new Ext.Panel({
        height:250,
        border : false,
        layout:'form',
        buttonAlign:'center',
        items:[{
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:10px 0px 0px 5px',
   	            labelWidth:70,
   	             labelAlign: "right",
   	            items:[sdkSchemeComBox]
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
   	            items:[sdkUpdateLimitTypeComboBox] 
   	        }, {
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            labelWidth:77,
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            items:[sdkUpdateSchemeCtrlLoad] 
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
   	            items:[sdkUpdateStartDate] 
   	        }, {
   	            columnWidth:.45,
   	            layout:'form',
   	            border : false,
   	            labelWidth:10,
   	            labelAlign: "right",
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            items:[sdkUpdateEndDate] 
   	        }]
   	    },{
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 5px',
   	            labelWidth:70,
   	             labelAlign: "right",
   	            items:[sdkUpdateSchemeRemark]
   	    },{
   	        layout:'column',
   	        border : false,
   	        items:[{
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 95px',
   	            labelAlign: "right",
   	            labelWidth:70,
   	            items:[sdkUpdateSchemeButton] 
   	        }, {
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            labelWidth:10,
   	            labelAlign: "right",
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            items:[sdkUpdateSchemeHideButton] 
   	        }]
   	    }]
    });
    
//	    定义保存为方案面板组件
    var sdkSaveSchemePenl=new Ext.Panel({
        layout:'card',
        activeItem : 0,
        border:false,
        items:[sdkSaveSchemePnl1, sdkUpdateSchemePnl1]
    });
    
    var sdkSchemePenal = new Ext.Panel({
    	layout:'form',
    	border:false,
    	items:[schemeRedioPanel,sdkSaveSchemePenl]
    });
    //保存方案跳出窗口
    var sdkSaveScheme = new Ext.Window({
        name:'sdkSaveScheme',
        height:290,
        width:400,
        closeAction:'hide',
        resizable:false,
        layout:'fit',	        
        title:'保存方案',
        items:[sdkSchemePenal]
    });
    
//    -------------------------------------------------时段控-执行方案   开始   		
	var executeCheckbox = new Ext.form.CheckboxGroup({
				fieldLabel : '有效时段:',
				labelSeparator:' ',
				width:260,
				items : [{	name : '1',inputValue:'1', boxLabel : '1'}, 
						{name : '2',	inputValue:'2', boxLabel : '2'}, 
						{name : '3',	inputValue:'3', boxLabel : '3'}, 
						{name : '4',	inputValue:'4', boxLabel : '4'}, 
						{name : '5',	inputValue:'5', boxLabel : '5'}, 
						{name : '6',	inputValue:'6', boxLabel : '6'}, 
						{name : '7',	inputValue:'7', boxLabel : '7'}, 
						{name : '8',	inputValue:'8', boxLabel : '8'}]
			});		
	templateNameComboBox.on("change",function(templateName,newValue){
			executeCheckbox.setValue();
	});		
	
	var constcombo = new Ext.form.ComboBox({
			store : planConststore,
			fieldLabel : '功率定值方案:',
			typeAhead : true,
			editable : false,
			mode : 'local',
			forceSelection : true,
			triggerAction : 'all',
			width:95,
			valueField : 'id',
			emptyText:'--请选择群组--',
			displayField : 'name',
			selectOnFocus : true
		});
	var executePanel_1 = new Ext.Panel({
			layout:'column',
			bodyStyle:'padding: 10px 0px 0px 0px',
			border:false,
			items:[{
				columnWidth:.3,
				labelSeparator:' ',
				layout:'form',
				labelAlign:'right',
				labelWidth : 124,
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
	
	var periodExePanel = new Ext.Panel({//时段控执行方案panel
	   title:'时段控方案执行参数',
	   layout:'form',
	   region:'north',
	   height:75,
	   items:[executePanel_1]
	});
	
	//----------------------------时段控参数执行-召测列表    开始----------------------------------------------
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
	var sdkRowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(sdk_cszxStore && sdk_cszxStore.lastOptions && sdk_cszxStore.lastOptions.params){
				startRow = sdk_cszxStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	var sdk_cszxCM = new Ext.grid.ColumnModel([
			sdkRowNum, 
			sdk_cszxSM, 
			{header : '选择',	 dataIndex : 'keyId', sortable : true,	hidden:true, align : 'center'}, 
			{header : '供电单位', dataIndex : 'orgName', sortable : true, renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			},align : 'center',defaultWidth:140}, 
			{header : '供电单位编号', sortable:true, dataIndex:'orgNo', align:'center', hidden:true}, 
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
			{header : '规约编码',	dataIndex : 'protocolCode',	sortable : true,	hidden:true,	align : 'center'}, 
			{header : '报文',	dataIndex : 'message',sortable : true,	align : 'center',	renderer: function(s, m, rec){
					return "<a href='javascript:'onclick='origFrameQryShow(\""+rec.get('consNo')+"\",\""+ rec.get('consName')+"\",\""+ rec.get('terminalAddr')+"\");" + "'>查看</a>"; 
			}}
		]);
			
	var sdk_cszxGrid = new Ext.grid.GridPanel({
			ds : splite_sdk_cszxStore,
			title : '时段控方案用户列表',
			loadMask:true,
			cm : sdk_cszxCM,
			sm : sdk_cszxSM,
			tbar : [{
		   		text : '加载方案',
				name : '',
				width : 80,
				handler : function() {
	  		     	sdkLoadStore.load();
	  		        sdkLoadSchemeWindow.show();
				}
		   },{
	   		text : '保存为方案',
			name : '',
			width : 80,
			handler : function() {
				Ext.Ajax.request({
  		     		url:'./baseapp/remoteControl!loadSchemeData.action',
  		     		params : {
  		     			staffNo:'001'
  		     		},
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
				        sdkLimitTypeStore.loadData(result);
  		     		},
  		     		failure : function(){
  		     		   Ext.MessageBox.alert("提示","失败");
  		     		   return;
  		     		}
  		     	});
  		        sdkSaveScheme.show();
			}
	   },{xtype: 'tbfill'},{
					xtype : 'checkbox',
					id : 'plan_sdkGsselectAllcb',
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
								sdk_cszxSM.clearSelections()
							}
						}
					}
				}, {
					text : '删除选中行',
					iconCls: 'minus',
					handler : function() {
							if (Ext.getCmp('plan_sdkGsselectAllcb').checked) {
								sdk_cszxStore.removeAll(true);
								unlockGrid('sdk');
								Ext.getCmp('plan_sdkGsselectAllcb').setValue(false);
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
								if (Ext.getCmp('plan_sdkGsselectAllcb').checked) {
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
									if (Ext.getCmp('plan_sdkGsselectAllcb').checked) {
										Ext.getCmp('plan_sdkGsselectAllcb').setValue(false);
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
								if(Ext.getCmp('plan_sdkGsselectAllcb').checked) {
									Ext.getCmp('plan_sdkGsselectAllcb').setValue(false);
									sdk_cszxSM.selectAll();
								}
							 }
						}],
			bbar : new Ext.ux.MyToolbar({
					store : splite_sdk_cszxStore,
					allStore: sdk_cszxStore,
					enableExpAll : true, // excel导出全部数据
					expAllText : "全部",
					enableExpPage : true, // excel仅导出当前页
					expPageText : "当前页"
			})	
	});
	
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
	var sdkzcRowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(sdk_zcStore && sdk_zcStore.lastOptions && sdk_zcStore.lastOptions.params){
				startRow = sdk_zcStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	var sdk_zcCM = new Ext.grid.ColumnModel([sdkzcRowNum, sdk_zcSM, 
			{header : '选择',	dataIndex : 'keyId',sortable : true,hidden:true,	align : 'center'}, 	
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
					enableExpPage : true, // excel仅导出当前页
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
		id:'plan_pwrctrl_sdk',
		border:false,
		layout:'border',
		items:[periodPanel,periodRadio_down]
	});		

//**********************************************************************************************时段控结束***********************************************************************************************************************
//**********************************************************************************************厂休控开始***********************************************************************************************************************
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
					id:'plan_powerControl_facRestTemplateName',
					fieldLabel:'模板名称',
					allowBlank:false,
					emptyText:'模板名称',
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
						Ext.Ajax.request({
							url : 'baseapp/isExist!queryTemplateName.action',
							params : {
								templateName : templateName
							},
							success : function(response) {
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
								
								var url="";
								if(templateNameNode.disabled){
									url = 'baseapp/updateWFactctrlTemplate!updateWFactctrlTemplate.action';
								} else {
									url = 'baseapp/saveWFactctrlTemplate!saveWFactctrlTemplate.action';
								}
								
								cxkmb2.getForm().submit({ // 提交表单
									method : 'post',
								    url:url,
									success : function(form, action) { // 返回成功
										Ext.Msg.alert("提示","模板保存成功");
										cxkmb.hide(); 
										cxkTemplateNameComboBox.setValue();
										cxkTemplateStore.load();
									},
									failure : function(form, action) { // 返回失败
										Ext.Msg.alert("提示","模板保存出错");
									}
								});
							}
						});
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
			}, [{name:'ctrlTime'},{name:'templateName'},
				{name:'weekDays'},{name:'factoryConst'},{name:'continueHours'}])
	});
	var cxkTemplateNameComboBox = new  Ext.form.ComboBox({
		store : cxkTemplateStore,
		xtype:'combo',
		name :'combo_templateName',
		fieldLabel:'模板名称',
		valueField : 'templateName',
		editable : false,
		triggerAction : 'all',
		forceSelection : true,
		mode : 'remote',
		selectOnFocus : true,
		displayField : 'templateName',
		emptyText:'功率厂休控',
		anchor:'85%'
	});
	
	 //厂休控参数下发
	var facRestPanel = new Ext.Panel({
	  	title:'厂休控',
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
						var templateName = cxkTemplateNameComboBox.getRawValue();
						var store = cxkTemplateNameComboBox.getStore();
						store.each(function(obj){
							if(obj.get('templateName') == templateName){
								cxkPlanTemplate = obj;
							}
						});
//						设置页面template值
						if(cxkPlanTemplate == null || typeof(cxkPlanTemplate) == 'undefined'||cxkPlanTemplate=="") {
							Ext.Msg.alert('提示','请选择模板');
							return;
						}
						var tmpNameNode = Ext.getCmp('plan_powerControl_facRestTemplateName');
	        			tmpNameNode.setDisabled(true);
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
						var tmpNameNode = Ext.getCmp('plan_powerControl_facRestTemplateName');
	        			tmpNameNode.setDisabled(false);
	        			
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
                    	record[i][0] = users[i].get('keyId')
                    	record[i][1] = users[i].get('orgName')
                    	record[i][2] = users[i].get('consNo')
                    	record[i][3] = users[i].get('consName')
                    	record[i][4] = users[i].get('terminalAddr')
                    	record[i][5] = users[i].get('totalNo')
			        	
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
    }
	
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
                       		cxkTemplateNameComboBox.setValue(records.templateName);
						}
                   });
               } else {
					templateNameComboBox.setValue('');
			   }
			   Ext.getBody().unmask();
           } , 
           add:true
   	   });
       cxkLoadSchemeWindow.hide();
    }
    
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
        height:250,
        width:400,
        closeAction:'hide',
        resizable:false,
        layout:'border',
        title:'方案加载',
        items:[cxkLoadScheme1,cxkLoadScheme2]
    }); 
//定义保存为方案面板组件
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
            //校验方案名
			var schemeName = cxkSchemeName.getValue();
			if(schemeName =='') {
				Ext.MessageBox.alert("提示","方案名不能为空");
	     	    return;
			}
			var ctrlSchemeName = cxkSchemeLabel.getValue() + '-' +schemeName;
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
			{header : '选择',	 dataIndex : 'keyId', sortable : true,	hidden:true, align : 'center'}, 
			{header : '供电单位', dataIndex : 'orgName', sortable : true, renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			}, align : 'center',defaultWidth:140}, 
			{header : '供电单位编号', sortable:true, dataIndex:'orgNo', align:'center', hidden:true}, 
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
			{header : '规约编码',	dataIndex : 'protocolCode',	sortable : true,	hidden:true,	align : 'center'}, 
			{header : '报文',	dataIndex : 'message',sortable : true,	align : 'center',	renderer: function(s, m, rec){
					return "<a href='javascript:'onclick='origFrameQryShow(\""+rec.get('consNo')+"\",\""+ rec.get('consName')+"\",\""+ rec.get('terminalAddr')+"\");" + "'>查看</a>"; 
			}}]);

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
						Ext.Ajax.request({
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
		  		        cxkSaveScheme.show();
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
									cxk_cszxSM.clearSelections()
								}
							}
						}
					},{	
						text : '删除选中行',
						iconCls: 'minus',
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
								if(Ext.getCmp('plan_cxkGsselectAllcb').checked) {
									Ext.getCmp('plan_cxkGsselectAllcb').setValue(false);
									cxk_cszxSM.selectAll();
								}
							 }
						}],
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
			{header : '选择',	dataIndex : 'keyId',sortable : true,hidden:true,align : 'center'}, 
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
	
//---------------------------------------------------------厂休控参数执行-召测列表   结束---------------------------------		
	
//**********************************************************************************************厂休控结束********************************************************************************
//**********************************************************************************************下浮控开始********************************************************************************
//--------------------------------------------------下浮控 panel   开始
	var floatPanel_1 = new Ext.Panel({
		layout:'column',
		border:false,
		height:42,
		bodyStyle:'padding: 10px 0px ',
		style : 'font-size:12px',
		items:[{
			columnWidth:.24,
			border:false,
			labelAlign : 'right',
			labelWidth : 178,
			labelSeparator:' ',
			layout:'form',
			style : "padding-left:10px",
			layout:'table',
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
					anchor:'90%'
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
					anchor:'90%'
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
					anchor:'90%'
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
					anchor:'90%'
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
		title:'紧急下浮控',
		region:'north',
		layout:'form',
		height:140,
		items:[floatPanel_1,floatPanel_2,floatPanel_3]
	});
			
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
						}
                   });
               } else {
					floatPanel.getForm().reset();		
              }
           },
           add:true
   	   });
       floatLoadSchemeWindow.hide();
    }
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
        height:250,
        width:400,
        closeAction:'hide',
        resizable:false,
        layout:'border',
        title:'方案加载',
        items:[floatLoadScheme1,floatLoadScheme2]
    }); 
    
// 	定义保存为方案面板组件**********************************************************************************************************
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
            
            //校验方案名
			var schemeName = floatSchemeName.getValue();
			if(schemeName =='') {
				Ext.MessageBox.alert("提示","方案名不能为空");
	     	    return;
			}
			var ctrlSchemeName = floatSchemeLabel.getValue() + '-' + schemeName;
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
//-----------------------------下浮控 列表   开始				
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
			{name:'execFlag'},
			{name:'protocolCode'},
			{name : 'message'}])
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
					{name:'execFlag'},
					{name:'protocolCode'},
					{name : 'message'}]
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
			data[i][index++] = valStore.getAt(i).data.execFlag;
			data[i][index++] = valStore.getAt(i).data.protocolCode;
			data[i][index++] = valStore.getAt(i).data.message;
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
			{header : '选择',	 dataIndex : 'keyId', sortable : true,	hidden:true, align : 'center'}, 
			{header : '供电单位', dataIndex : 'orgName', sortable : true, renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			},align : 'center',defaultWidth:140}, 
//			{header : '供电单位编号', sortable:true, dataIndex:'orgNo', align:'center', hidden:true}, 
			{header : '终端资产号', 	sortable:true, dataIndex:'tmnlAssetNo', align:'center',hidden:true},
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
			{header : '规约编码',	dataIndex : 'protocolCode',	sortable : true,	hidden:true,	align : 'center'}, 
			{header : '报文',	dataIndex : 'message',sortable : true,	align : 'center',	renderer: function(s, m, rec){
					return "<a href='javascript:'onclick='origFrameQryShow(\""+rec.get('consNo')+"\",\""+ rec.get('consName')+"\",\""+ rec.get('terminalAddr')+"\");" + "'>查看</a>"; 
			}}]);

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
		title : '下浮控方案用户列表',
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
						Ext.Ajax.request({
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
		  		        floatSaveScheme.show();
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
									floatSM.clearSelections()
								}
							}
						}
					},{
			  		text : '删除选中行',
					iconCls: 'minus',
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
				}, '-', {
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
				}],
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

//************************************************************************************************下浮控结束******************************************************************************
//用户列表默认选中	
//时段控========================
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
	
	var panel_low = new Ext.Panel({
				region : 'center',
				layout : 'card',
				activeItem : 0,
				border : false,
				items : [periodRadio, facRestRadio, floatRadio]
	});
	
	var viewPanel = new Ext.Panel({
		layout:'border',
		border:false,
		items:[panel_top,panel_low]
//		items:[floatPanel]
	});
	
	function clickEvent(obj,type){
//	   	取得当前活动页ID
   	    var curPage = panel_low.getLayout().activeItem.getId();
	   	if(curPage==''|| curPage==null || typeof(curPage)=='undefined'){
	   		return;
	   	}
	   	Ext.getBody().mask('数据加载中...');	
	   	if(curPage=='plan_pwrctrl_sdk'){
			//时段空
		    sdk_cszxStore.baseParams = {
				subsId : obj.subsId,
				groupNo : obj.groupNo,
				lineId : obj.lineId,
				tmnlAssetNo : obj.tmnlAssetNo,
				nodeType : type,
				orgNo : obj.orgNo,
				orgType : obj.orgType
			};
		    sdk_cszxStore.load({
		    	callback : function(recs, options, success) {
		    		Ext.getBody().unmask();		
					if (success)
						sdk_cszxGrid.getSelectionModel().selectRecords(recs, true);
				},
		  	    add: true
		    });
	   	}
		
	   	if(curPage=='plan_pwrctrl_cxk'){
			//厂休控
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
	   	
	if(curPage=='plan_pwrctrl_float'){
		//下浮空
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
}					
//  监听左边树点击事件
    var treeListener = new LeftTreeListener({
	    modelName : '功控方案编制',
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
	
	renderModel(viewPanel,'功控方案编制');
	
	//由有序用电任务编制链接而来，全局变量不为空时，自动加载传入的方案
	if(typeof(staticCtrlSchemeId) != 'undefined') {
		if(staticCtrlSchemeType =='01') {
			planTemplateStore.load();
			topRadio.setValue('period');
			sdk_cszxStore.removeAll();
	   	    sdk_cszxStore.baseParams = {
	   	   	   schemeId : staticCtrlSchemeId,
	   	   	   nodeType:'schemeId',
	   	   	   add:true
	   	   };
	   	   sdk_cszxStore.load({
				params:{start:0,limit:20},
				callback: function(records, options, success){
	               if(null != records && 0 < records.length){
	               		Ext.Ajax.request({
							url : "./baseapp/pwrctrlLoadScheme!pwrctrlLoadScheme.action",
							params : {
								schemeId : staticCtrlSchemeId
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
	                       		
	                       		planTemplate = planTemplateStore.getById(records.templateName);
								if(planTemplate ==null ||planTemplate==''){
									return;
								}
								planTemplateDetail();
							}
						});
	               }
	               else{
	               		executeCheckbox.setValue("");
	               		templateNameComboBox.setValue("");
	               }
	               Ext.getBody().unmask();
	           },
	           add:true
	   	   });
		} else if (staticCtrlSchemeType =='02') {
			topRadio.setValue('rest');
			cxk_cszxStore.removeAll();
	   	    cxk_cszxStore.baseParams = {
	   	   	   schemeId : staticCtrlSchemeId,
	   	   	   nodeType:'schemeId',
	   	   	   add:true
	   	    };
	   	    cxk_cszxStore.load({
	   	   	   callback: function(records, options, success){
	               if(null != records && 0 < records.length){
	                   	Ext.Ajax.request({
							url : "./baseapp/factctrlLoadScheme!factctrlLoadScheme.action",
							params : {
								schemeId: staticCtrlSchemeId
							},
							success : function(response) {
								var records = Ext.decode(response.responseText);
	                       		cxkTemplateNameComboBox.setValue(records.templateName);
	                       		
	                       		cxkTemplateNameComboBox.getStore().load();
							}
	                   });
	               } else {
						templateNameComboBox.setValue('');
				   }
				   Ext.getBody().unmask();
	           } , 
	           add:true
	   	   });
		} else {
			topRadio.setValue('float');
			floatStore.removeAll();//加载方案时，先清除列表原有记录
	   	    floatStore.baseParams = {
	   	   	   schemeId : staticCtrlSchemeId,
	   	   	   nodeType:'schemeId',
	   	   	   add:true
	   	   };
	   	   floatStore.load({
	   	   		params:{start:0,limit:20},
	   	   	    callback: function(records, options, success){
	               if(null != records && 0 < records.length){
	              	 	Ext.Ajax.request({
							url : "./baseapp/floatLoadScheme!floatLoadScheme.action",
							params : {
								schemeId:staticCtrlSchemeId
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
							}
	                   });
	               } else {
						floatPanel.getForm().reset();		
	              }
	              Ext.getBody().unmask();
	           },
	           add:true
	   	   });
		}
	}
	Ext.getCmp('功控方案编制').on('activate', function() {
 		if(typeof(staticCtrlSchemeId) != 'undefined'){
			if(staticCtrlSchemeType =='01') {
				planTemplateStore.load();
				topRadio.setValue('period');
				sdk_cszxStore.removeAll();
		   	    sdk_cszxStore.baseParams = {
		   	   	   schemeId : staticCtrlSchemeId,
		   	   	   nodeType:'schemeId',
		   	   	   add:true
		   	   };
		   	   sdk_cszxStore.load({
					params:{start:0,limit:20},
					callback: function(records, options, success){
		               if(null != records && 0 < records.length){
		               		Ext.Ajax.request({
								url : "./baseapp/pwrctrlLoadScheme!pwrctrlLoadScheme.action",
								params : {
									schemeId : staticCtrlSchemeId
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
		                       		
		                       		planTemplate = planTemplateStore.getById(records.templateName);
									if(planTemplate ==null ||planTemplate==''){
										return;
									}
									planTemplateDetail();
								}
							});
		               }
		               else{
		               		executeCheckbox.setValue("");
		               		templateNameComboBox.setValue("");
		               }
		               Ext.getBody().unmask();
		           },
		           add:true
		   	   });
			} else if (staticCtrlSchemeType =='02') {
				topRadio.setValue('rest');
				cxk_cszxStore.removeAll();
		   	    cxk_cszxStore.baseParams = {
		   	   	   schemeId : staticCtrlSchemeId,
		   	   	   nodeType:'schemeId',
		   	   	   add:true
		   	    };
		   	    cxk_cszxStore.load({
		   	   	   callback: function(records, options, success){
		               if(null != records && 0 < records.length){
		                   	Ext.Ajax.request({
								url : "./baseapp/factctrlLoadScheme!factctrlLoadScheme.action",
								params : {
									schemeId: staticCtrlSchemeId
								},
								success : function(response) {
									var records = Ext.decode(response.responseText);
		                       		cxkTemplateNameComboBox.setValue(records.templateName);
		                       		
		                       		cxkTemplateNameComboBox.getStore().load();
								}
		                   });
		               } else {
							templateNameComboBox.setValue('');
					   }
					   Ext.getBody().unmask();
		           } , 
		           add:true
		   	   });
			} else {
				topRadio.setValue('float');
				floatStore.removeAll();//加载方案时，先清除列表原有记录
		   	    floatStore.baseParams = {
		   	   	   schemeId : staticCtrlSchemeId,
		   	   	   nodeType:'schemeId',
		   	   	   add:true
		   	   };
		   	   floatStore.load({
		   	   		params:{start:0,limit:20},
		   	   	    callback: function(records, options, success){
		               if(null != records && 0 < records.length){
		              	 	Ext.Ajax.request({
								url : "./baseapp/floatLoadScheme!floatLoadScheme.action",
								params : {
									schemeId:staticCtrlSchemeId
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
								}
		                   });
		               } else {
							floatPanel.getForm().reset();		
		              }
		              Ext.getBody().unmask();
		           },
		           add:true
		   	   });
			}
		}
	});
});
