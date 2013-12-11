//链接原始报文查询界面
function origFrameQryShow(consNo,consName,tmnlAssetAddr){
	staticConsNo = consNo;
	staticConsName = consName;
	staticTmnlAssetAddr = tmnlAssetAddr;
	openTab("原始报文查询","./baseApp/dataGatherMan/origFrameQry.jsp");
}
	
Ext.onReady(function(){
	// grid解锁
	function unlockGrid(tab) {//取消全选时，解锁Grid
		if (tab =="float"){//拉合闸Grid
			floatSM.unlock();
			floatGrid.onEnable();
			floatGrid.getBottomToolbar().enable();
			Ext.getCmp('floatGsselectAllcb').setValue(false);
		}
	}
	// grid锁定
	function lockGrid(tab) {
		if (tab =="float"){
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
			bodyStyle:'padding: 0px 0px 0px 10px ',
			items:[{
				xtype : 'label',
				anchor:'95%',
				text : '当前功率下浮控告警时间(分钟)'
			}]
		},{
			columnWidth : .03,
			xtype : 'checkbox',
			name:'box1',
			disabled:true,
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
					readOnly:true,
					anchor:'90%',
					vtype:'alertTime'
				}]
		},{
			columnWidth : .03,
			xtype : 'checkbox',
			name:'box2',
			disabled:true,
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
					readOnly:true,
					anchor:'90%',
					vtype:'alertTime'
				}]
		},{
			columnWidth : .03,
			xtype : 'checkbox',
			name:'box3',
			disabled:true,
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
					readOnly:true,
					anchor:'90%',
					vtype:'alertTime'
				}]
		},{
			columnWidth : .03,
			xtype : 'checkbox',
			name:'box4',
			disabled:true,
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
					readOnly:true,
					anchor:'90%',
					vtype:'alertTime'
				}]
		}]
	});
	
	var floatPanel_2 = new Ext.Panel({
		layout:'column',
		border:false,
		height:40,
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
				readOnly:true,
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
				readOnly:true,
				vtype:'freezeTime'
			}]
		}]
	});
	
	
	var floatPanel_3 = new Ext.Panel({
		layout:'column',
		height:35,
//		bodyStyle:'padding: 5px 0px',
		border:false,
		items:[{
			columnWidth:.5,
			border:false,
			layout:'column',
			items:[{
//				columnWidth:.8,
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
					disabled:true,
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
					readOnly:true,
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
								readOnly:true,
								accelerate: true
            			})
					]
				}]
	});
	
	var floatExePanel = new Ext.Panel({
		layout:'form',
		border:false,
		height:30,
		labelAlign:'right',
		labelWidth : 58,
		labelSeparator:'',
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
	
	var floatPanel = new Ext.FormPanel({
		title:'方案名：',
		region:'north',
		border:false,
		layout:'form',
		height:175,
		items:[floatPanel_1,floatPanel_2,floatPanel_3,floatExePanel]
	});
	//设置方案名为标题
	if(typeof(staticFloatCtrlSchemeName) != 'undefined'){
		floatPanel.setTitle('方案名：'+staticFloatCtrlSchemeName);
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
   	           	   	   schemeId:floatSchemeIdValue,
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
   	       }] 
   	    }]
    });
    
    /*Json对象*/
    var floatLoadStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url:'./baseapp/pwrctrlScheme!floatSchemeList.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'schemeList'
			}, [{name:'ctrlSchemeId'},
				{name:'ctrlSchemeName'},
				{name:'ctrlSchemeType'}])
	});
	
// 定义方案加载弹出页面的下部分面板组件
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
 //-----------------------------------------------方案加载结束-----------------------------------------------
    


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

	//下浮控分页store		
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
	var rowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(floatStore && floatStore.lastOptions && floatStore.lastOptions.params){
				startRow = floatStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	var floatCM = new Ext.grid.ColumnModel([rowNum, floatSM, 
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

	//参数下发，控制下发，控制解除返回结果赋值处理函数		
	var floatSendCtrlResult = function floatSendCtrlResult(result, users) {
		if (Ext.getCmp('floatGsselectAllcb').checked) {//如果选中全选按钮
			floatStore.each(function(rec){
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
			floatStore.commitChanges();//提交修改
			
			splite_floatStore.proxy = new Ext.ux.data.PagingMemoryProxy(floatStoreToArray(floatStore));
			splite_floatStore.load({
				params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
			});
		} else {
			for(var i = 0; i < users.length; i++){
				var rec = floatStore.getById(users[i].get('keyId'));
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
			floatStore.commitChanges();//提交修改
			splite_floatStore.proxy = new Ext.ux.data.PagingMemoryProxy(floatStoreToArray(floatStore));
			splite_floatStore.load({
				params : {
					start : splite_floatStore.lastOptions.params.start,
					limit : DEFAULT_PAGE_SIZE
				}
			});
		}
	};		
	
	//校验下浮控参数
	function floatExecCheck(){
		var floatDownWtime = floatPanel_2.find('name','floatDownWtime')[0].getValue();
        var powerFreezetime = floatPanel_2.find('name','powerFreezetime')[0].getValue();
        var floatDownPercent = floatPanel_3.find('name','floatDownPercent')[0].getValue();
        var ctrlTime = floatPanel_3.find('name','ctrlTime')[0].getValue();
        if(floatDownWtime==null|| floatDownWtime==""){
        	Ext.MessageBox.alert("提示","当前功率下浮控定值滑差时间不能为空");
        	//floatDownWtime.validateValue(false);
        	return false;
        }
        if(powerFreezetime==null ||powerFreezetime=="") {
        	Ext.MessageBox.alert("提示","控后有功功率冻结延时时间不能为空");
        	//powerFreezetime.validateValue(false);
        	return false;
        }
        if(floatDownPercent==null ||floatDownPercent=="") {
        	Ext.MessageBox.alert("提示","当前功率下浮控定值浮动系数不能为空");
        	//floatDownPercent.validateValue(false);
        	return false;
        }
        if(ctrlTime==null ||ctrlTime=="") {
        	Ext.MessageBox.alert("提示","当前功率下浮控控制时间不能为空");
        	//ctrlTime.validateValue(false);
        	return false;
        }
        return true;
	}
	
	var floatSchemeExecButton = new Ext.Button({
  		text:'方案执行',
  		handler:function(){
  			if(!floatExecCheck()) return;
  			
  			var users = floatSM.getSelections();
  			if(null == users || users.length==0){
	     	    Ext.MessageBox.alert("提示","请选择用户！");
	     	    return;
     		}
  			ctrlCheckStaff(floatExec, 'ctrl');
  		}
	});
	
	var floatSchemeReleaseButton = new Ext.Button({
	  		text:'方案解除',
	  		handler:function(){
	  			var users = floatSM.getSelections();
	  			if(null == users || users.length==0){
		     	    Ext.MessageBox.alert("提示","请选择用户！");
		     	    return;
	     		}
	  			ctrlCheckStaff(floatRelease, 'ctrl');
	  		}
	  	});
	
	var floatGrid = new Ext.grid.GridPanel({
				store : splite_floatStore,
				//title : '下浮控方案执行列表',
				region:'center',
				loadMask:true,
				cm : floatCM,
				sm : floatSM,
				tbar : [floatSchemeExecButton, floatSchemeReleaseButton, {xtype: 'tbfill'},{
							xtype : 'checkbox',
							id : 'floatGsselectAllcb',
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
						}, '-', {
					  		text : '删除选中用户',
							iconCls: 'cancel',
							handler : function() {
							if (Ext.getCmp('floatGsselectAllcb').checked) {
								floatStore.removeAll(true);
								unlockGrid('float');
								Ext.getCmp('floatGsselectAllcb').setValue(false);
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
								if (Ext.getCmp('floatGsselectAllcb').checked) {
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
									if (Ext.getCmp('floatGsselectAllcb').checked) {
										Ext.getCmp('floatGsselectAllcb').setValue(false);
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
								if(Ext.getCmp('floatGsselectAllcb').checked) {
									Ext.getCmp('floatGsselectAllcb').setValue(false);
									floatSM.selectAll();
								}
							 }
						}],
					bbar : new Ext.ux.MyToolbar({
						store : splite_floatStore,
						allStore:floatStore,
						enableExpAll : true, // excel导出全部数据
						expAllText : "全部",
						enableExpPage : true, // excel仅导出当前页
						expPageText : "当前页"
					})
			});		
			
	var floatExec = function (){
		var users = floatSM.getSelections();
     	var pwrCtrlList = new Array();//向后台传的参数数组
     	var execNum = 0;//过滤终端的个数
     	var pwrCtrlListNum = 0;//pwrCtrlList的长度 用于自加
     	if (Ext.getCmp('floatGsselectAllcb').checked) { //如果选中全选按钮
			for (var i = 0; i < floatStore.getCount(); i++) {
				if(floatStore.getAt(i).data.ctrlFlag =='1' || floatStore.getAt(i).data.tmnlPaulPower =='是'){
					 floatStore.getAt(i).set('execFlag','-9');
	     			 execNum++;
	     			 continue;
	     		}
				pwrCtrlList[pwrCtrlListNum++] =floatStore.getAt(i).data.orgNo +'`' + floatStore.getAt(i).data.consNo +
					'`' + floatStore.getAt(i).data.tmnlAssetNo + '`' + floatStore.getAt(i).data.totalNo + '`' + floatStore.getAt(i).data.protocolCode;
			}
			floatStore.commitChanges();
     	} else {
     		for(var i = 0; i < users.length; i++){
	     		if(users[i].get('ctrlFlag')=='1' || users[i].get('tmnlPaulPower')=='是'){
	     			 users[i].set('execFlag','-9');
	     			 execNum++;
	     			 continue;
	     		}
				pwrCtrlList[pwrCtrlListNum++] = users[i].get('orgNo')+'`'+users[i].get('consNo')+'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');
       		}
       		floatStore.commitChanges();
     	}
        if(pwrCtrlList.length<=0) {
        	Ext.MessageBox.alert("提示",'没有符合执行条件的终端');
        	return;
        }
        
        if(execNum!= 0) {
        	Ext.MessageBox.alert("提示",execNum+"个终端的总加组被保电或已投入下浮控, 不能投入，将被自动过滤");
        }
        
        var alertTime1 = floatPanel_1.find('name','alertTime1')[0].getValue();
        var alertTime2 = floatPanel_1.find('name','alertTime2')[0].getValue();
        var alertTime3 = floatPanel_1.find('name','alertTime3')[0].getValue();
        var alertTime4 = floatPanel_1.find('name','alertTime4')[0].getValue();
        
        var box1 = floatPanel_1.find('name','box1')[0].getValue();
        var box2 = floatPanel_1.find('name','box2')[0].getValue();
        var box3 = floatPanel_1.find('name','box3')[0].getValue();
        var box4 = floatPanel_1.find('name','box4')[0].getValue();
        
        var floatDownWtime = floatPanel_2.find('name','floatDownWtime')[0].getValue();
        var powerFreezetime = floatPanel_2.find('name','powerFreezetime')[0].getValue();
        var floatDownPercent = floatPanel_3.find('name','floatDownPercent')[0].getValue();
        var radioGroup = floatPanel_3.find('name','radioGroup')[0].getValue().getRawValue();
       	if(radioGroup == "down") {
       		floatDownPercent = -floatDownPercent;
       	}
        var ctrlTime = floatPanel_3.find('name','ctrlTime')[0].getValue();
        
     	var ov = true;
     	var second = Ext.Ajax.timeout/1000;
        h_taskTime(second,function(){
        	ov = false;
        });
        if(pwrCtrlList.length<=0){
        	Ext.MessageBox.alert("提示",'没有符合执行条件的终端');
        	return;
        }
    	Ext.Ajax.request({
     		url:'./baseapp/powerControl!floatExec.action',
     		params : {
     			second:second,
				pwrCtrlList:pwrCtrlList,
				alertTime1:alertTime1,
     			alertTime2:alertTime2,
     			alertTime3:alertTime3,
     			alertTime4:alertTime4,
     			box1:box1,
     			box2:box2,
     			box3:box3,
     			box4:box4,
     			floatDownWtime:floatDownWtime,
     			powerFreezetime:powerFreezetime,
     			floatDownPercent:floatDownPercent,
     			ctrlTime:ctrlTime
     		},
     		success : function(response){
     			if(!ov) return ;
     			var cacheAndTmnlStatus = Ext.decode(response.responseText).cacheAndTmnlStatus;
     			if(cacheAndTmnlStatus!=""&& cacheAndTmnlStatus!=null){
     				Ext.MessageBox.alert("提示",cacheAndTmnlStatus);
     				return;
     			}
     			var result = Ext.decode(response.responseText).execResultList;
     			if(result==null ||result.length<=0) {
     				Ext.MessageBox.alert("提示","下浮控执行失败，全部终端无应答！");
     				return;
     			}
     			floatSendCtrlResult(result, users);//调用下发结果处理函数
     			
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
  		 
  	var floatRelease = function (){
		var users = floatSM.getSelections();
     	var pwrCtrlList = new Array();//向后台传的参数数组
     	var execNum = 0;//过滤终端的个数
     	var pwrCtrlListNum = 0;//pwrCtrlList的长度 用于自加
     	if (Ext.getCmp('floatGsselectAllcb').checked) { //如果选中全选按钮
			for (var i = 0; i < floatStore.getCount(); i++) {
				if(floatStore.getAt(i).data.ctrlFlag !='1'){
					 floatStore.getAt(i).set('execFlag','-9');
	     			 execNum++;
	     			 continue;
	     		}
				pwrCtrlList[pwrCtrlListNum++] =floatStore.getAt(i).data.orgNo +'`' + floatStore.getAt(i).data.consNo +
					'`' + floatStore.getAt(i).data.tmnlAssetNo + '`' + floatStore.getAt(i).data.totalNo + '`' + floatStore.getAt(i).data.protocolCode;
			}
			floatStore.commitChanges();
     	} else {
     		for(var i = 0; i < users.length; i++){
	     		if(users[i].get('ctrlFlag')!='1'){
	     			 users[i].set('execFlag','-9');
	     			 execNum++;
	     			 continue;
	     		}
				pwrCtrlList[pwrCtrlListNum++] = users[i].get('orgNo')+'`'+users[i].get('consNo')+'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');
       		}
       		floatStore.commitChanges();
     	}
        if(pwrCtrlList.length<=0) {
        	Ext.MessageBox.alert("提示",'没有符合解除条件的终端');
        	return;
        }
        if(execNum!= 0) {
        	Ext.MessageBox.alert("提示",execNum+"个终端的总加组不在控制状态，不能解除，将被自动过滤");
        }
        //显示进度条
     	var ov = true;
     	var second = Ext.Ajax.timeout/1000;
        h_taskTime(second,function(){
        	ov = false;
        });
    	Ext.Ajax.request({
     		url:'./baseapp/powerControl!floatRelease.action',
     		params : {
     			second:second,
     			pwrCtrlList:pwrCtrlList
     		},
     		success : function(response){
     			if(!ov) return ;
     			var cacheAndTmnlStatus = Ext.decode(response.responseText).cacheAndTmnlStatus;
     			if(cacheAndTmnlStatus!=""&& cacheAndTmnlStatus!=null){
     				Ext.MessageBox.alert("提示",cacheAndTmnlStatus);
     				return;
     			}
     			var result = Ext.decode(response.responseText).execResultList;
     			if(result==null ||result.length<=0) {
     				Ext.MessageBox.alert("提示","下浮控解除失败，全部终端无应答！");
     				return;
     			}
     			floatSendCtrlResult(result, users);//调用下发结果处理函数
     			
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
//-----------------------------------------下浮控 列表   结束			
			
	var floatRadio = new Ext.Panel({     				//下浮控radio内容
		id:'pwrctrl_float',
		border:false,
		layout:'border',
		items:[floatPanel,floatGrid]
	});			

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
				items : [floatRadio]
	});
	
	var viewPanel = new Ext.Panel({
		layout:'border',
		border:false,
		items:[panel_low]
	});
	
	if(typeof(staticFloatExeRelFlag) != 'undefined'){
		if(staticFloatExeRelFlag==0){
			floatSchemeExecButton.setVisible(true);
			floatSchemeReleaseButton.setVisible(false);
		}
		else if(staticFloatExeRelFlag==1){
			floatSchemeExecButton.setVisible(false);
			floatSchemeReleaseButton.setVisible(true);
		}	
	}
	renderModel(viewPanel,'当前功率下浮控');
	
	if(typeof(staticFloatCtrlSchemeId) != 'undefined') {
		floatStore.removeAll();//加载方案时，先清除列表原有记录
   	    floatStore.baseParams = {
   	   	   schemeId : staticFloatCtrlSchemeId,
   	   	   nodeType:'schemeId',
   	   	   add:true
   	   };
   	   Ext.getBody().mask("加载中..."); 
   	   floatStore.load({
   	   		params:{start:0,limit:DEFAULT_PAGE_SIZE},
   	   	    callback: function(records, options, success){
               if(null != records && 0 < records.length){
              	 	Ext.Ajax.request({
						url : "./baseapp/floatLoadScheme!floatLoadScheme.action",
						params : {
							schemeId:staticFloatCtrlSchemeId
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
	
	//设置激活标志，为0不加载数据，为1加载数据
	staticFloatExeRelActivate=0;
	
	Ext.getCmp('当前功率下浮控').on('activate', function() {
		if(staticFloatExeRelActivate==1){
			//设置方案名为标题
			if(typeof(staticFloatCtrlSchemeName) != 'undefined'){
				floatPanel.setTitle('方案名：'+staticFloatCtrlSchemeName);
			}
			if(typeof(staticFloatExeRelFlag) != 'undefined'){
				if(staticFloatExeRelFlag==0){
					floatSchemeExecButton.setVisible(true);
					floatSchemeReleaseButton.setVisible(false);
				}
				else if(staticFloatExeRelFlag==1){
					floatSchemeExecButton.setVisible(false);
					floatSchemeReleaseButton.setVisible(true);
				}	
			}
	 		if(typeof(staticFloatCtrlSchemeId) != 'undefined'){
				floatStore.removeAll();//加载方案时，先清除列表原有记录
		   	    floatStore.baseParams = {
		   	   	   schemeId : staticFloatCtrlSchemeId,
		   	   	   nodeType:'schemeId',
		   	   	   add:true
		   	   };
		   	 Ext.getBody().mask("加载中..."); 
		     floatStore.load({
		   	   		params:{start:0,limit:DEFAULT_PAGE_SIZE},
		   	   	    callback: function(records, options, success){
		               if(null != records && 0 < records.length){
		              	 	Ext.Ajax.request({
								url : "./baseapp/floatLoadScheme!floatLoadScheme.action",
								params : {
									schemeId:staticFloatCtrlSchemeId
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
		           }
		   	   });
	 		}
		}
		staticFloatExeRelActivate=0;
	});
});
