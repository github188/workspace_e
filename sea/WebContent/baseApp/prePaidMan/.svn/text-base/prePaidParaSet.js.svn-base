/*
 * ! Author: Longkongcao Date:11/27/2009 Description: 预付费控制参数下发 Update History:
 * huangxuan Date:1/18/2010 Description: 树与右边用户列表关联
 * 杨传文 Date:06/25/2010 页面需求布局修改
 */
 
 function origFrameQryShow(consNo,consName,tmnlAssetAddr){
	staticConsNo = consNo;
	staticConsName = consName;
	staticTmnlAssetAddr = tmnlAssetAddr;
	openTab("原始报文查询","./baseApp/dataGatherMan/origFrameQry.jsp");
}

// 打开新的tab
function opencsitab(privateTerminalConvalue) {
	privateTerminalCon = privateTerminalConvalue;
	openTab("客户综合查询", "./qryStat/queryCollPoint/consumerInfo.jsp");

}

Ext.onReady(function() {
	Ext.QuickTips.init();
	//下发、召测进度条
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
	
	var userStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url:'./baseapp/prePaidParaSet!queryPrePaidParaSet.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'prePaidParamSetList',
							idProperty : "keyId",
							totalProperty : "totalCount"
						}, [{
									name:'keyId'
								}, {
									name : 'appNo'
								}, {
									name : 'orgNo'	
								}, {
									name : 'consNo'
								}, {
									name : 'consName'
								}, {
									name : 'terminalAddr'
								}, {
									name : 'protocolCode'
								}, {
									name : 'totalNo'
							    }, {
									name : 'buyFlag'		
								}, {
									name : 'buyValue'
								}, {
									name : 'tmnlAssetNo'
								}, {
									name : 'realTimeStatus'
								}, {
									name : 'sendStatus'
								}, {
									name : 'alarmValue'
								}, {
									name : 'refreshFlag'
								}, {
									name : 'jumpValue'		
								}, {
									name : 'alarmValueUnit'
								}, {
									name : 'buyValueUnit'
								}, {
									name : 'jumpValueUnit'	
								}, {
									name : 'useValue'
								}, {
									name : 'meterId'	
								}, {
									name : 'tmnlPaulPower'
								}, {
									name : 'execFlag'
								}, {
									name : 'message'
								}])
			});

	// 监听左边树点击事件
	var treeListener = new LeftTreeListener({
				modelName : '预付费控制',
				processClick : function(p, node, e) {
					var obj = node.attributes.attributes;
					var type = node.attributes.type;
					if(type =='usr'){
						var consNo = Ext.getCmp('prePaidParamSetConsNo');
						var consName = Ext.getCmp('prePaidParamSetConsName');
						consNo.setDisabled(false);
						consNo.setValue(obj.consNo);
						consName.setDisabled(false);
						consName.setValue(obj.consName);
						Ext.getCmp('prePaidParamSetOrgNo').setValue('');
					}
				}
			});

	// 购电标志combobox数据
	var flgDataStore = new Ext.data.ArrayStore({
				fields : ['flgId', 'flgText'],
				data : [['-1','全部'],['0', '没有下发'], ["1", '下发成功'],['2','下发失败'],['3','调试中']]
			})

	// 购电标志combobox
	var flgComboBox = new Ext.form.ComboBox({
		fieldLabel : '流程状态',
		store : flgDataStore,
		labelSeparator : '',
		triggerAction : 'all',
		mode : 'local',
		value: '00',
		valueField : 'flgId',
		displayField : 'flgText',
		anchor : '80%',
		emptyText : 'Select a state...',
		selectOnFocus : true
	});

	//电控轮次
	var checkBoxGroup = new Ext.form.CheckboxGroup({
			fieldLabel : '电控轮次',
			labelSeparator : '',
			defaults : {
				margins : {
					top : 0,
					right : 50,
					bottom : 0,
					left : 0
				}
			},
			columns : [.125, .125, .125, .125, .125, .125, .125, .125],
			items : [{
						boxLabel : '1',
						name : 'cb-auto-1',
						inputValue : 1,
						checked : true
					}, {
						boxLabel : '2',
						name : 'cb-auto-2',
						inputValue : 2
					}, {
						boxLabel : '3',
						name : 'cb-auto-3',
						inputValue : 3
					}, {
						boxLabel : '4',
						name : 'cb-auto-4',
						inputValue : 4
					}, {
						boxLabel : '5',
						name : 'cb-auto-5',
						inputValue : 5
					}, {
						boxLabel : '6',
						name : 'cb-auto-6',
						inputValue : 6
					}, {
						boxLabel : '7',
						name : 'cb-auto-7',
						inputValue : 7
					}, {
						boxLabel : '8',
						name : 'cb-auto-8',
						inputValue : 8
					}]
		});
	
	var queryButton = new Ext.Button({
		text:'查询',
		width:65,
		handler: function (){
			userStore.baseParams = {
				orgNo:Ext.getCmp('prePaidParamSetOrgNo').getValue(),
				appNo:Ext.getCmp('prePaidParamSetAppNo').getValue(),
				consNo:Ext.getCmp('prePaidParamSetConsNo').getValue(),
				sendStatus:flgComboBox.getValue(),
				buyFlag:paraRadioGroup.getValue().getRawValue()
			}
			userStore.load();
		}
	});
	
		//供电单位
	var orgNoTF = new Ext.form.TextField({
			fieldLabel : "供电单位<font color='red'>*</font>",
			id:'prePaidParamSetOrgNo',
			labelSeparator:'',
			readOnly:true,
			editable:false,
			anchor:'100%'
	});
	
	orgNoTF.on('focus',function (){
		chooseOrgNo();
	});
	
	function createDlPanel() {
		var dlPanel = new Ext.Panel({
					border : false,
					id : 'pppsdlpanel',
					height : 80,
					layout : 'table',
					layoutConfig : {
						columns : 3,
						tableAttrs : {
							width : "100%"
						}
					},
					items : [{
								layout : 'form',
								border : false,
								labelWidth : 65,
								autoHeight : true,
								labelAlign : 'right',
								width : 240,
								items : [orgNoTF]
							},{
								labelAlign : 'right',
								width : 300,
								layout : 'form',
								border : false,
								id:'prePaidParamSetSendStatus',
								labelWidth : 65,
								items : [flgComboBox]
							}, {
								layout : 'form',
								border : false,
								labelWidth : 65,
								autoHeight : true,
								labelAlign : 'right',
								width : 200,
								items : [{
											xtype : 'textfield',
											labelSeparator : '',
											fieldLabel : '购电单号',
											id:'prePaidParamSetAppNo',
											anchor : '80%'
										}]
							},{
								layout : 'form',
								border : false,
								labelWidth : 65,
								autoHeight : true,
								labelAlign : 'right',
								width : 300,
								items : [{
											xtype : 'textfield',
											fieldLabel : '用户编号',
											id : 'prePaidParamSetConsNo',
											anchor : '80%'
										}]
							},{
								layout : 'form',
								border : false,
								labelWidth : 65,
								autoHeight : true,
								labelAlign : 'right',
								width : 300,
								items : [{
											xtype : 'textfield',
											fieldLabel : '用户名称',
											id : 'prePaidParamSetConsName',
											anchor : '80%'
										}]
							},{
								layout : 'column',
								border : false,
								labelWidth : 65,
								autoHeight : true,
								labelAlign : 'right',
								bodyStyle : 'padding: 0 0 0 95px;',
								width : 200,
								items : [queryButton]
							}]
				})
		return dlPanel;
	}

	function getDlPanel() {
		return Ext.getCmp('pppsdlpanel');
	}

	function getDfPanel() {
		return Ext.getCmp('pppsdfpanel');
	}

	var msgcheckBoxGroup = new Ext.form.Checkbox({
		boxLabel : '短信通知客户',
		name : '',
		checked : false
	});

	var ctPanel = new Ext.Panel({
				title : '购电轮次',
				border : false,
				id : 'ppsFormPanel4',
				height : 80,
				width : "100%",
				region : 'north',
				split : true,
				layout : 'table',
				layoutConfig : [{
							columns : 2,
							tableAttrs : {
								width : "100%"
							}
						}],
				items : [{
							layout : 'form',
							border : false,
							width : 420,
							bodyStyle : 'padding: 15px 0px 10px 15px',
							items : [checkBoxGroup]
						}]
			});

	var userSm = new Ext.grid.CheckboxSelectionModel();
	/***************************************************************************
	 * dataIndex代更新
	 **************************************************************************/
	var userCm = new Ext.grid.ColumnModel([userSm, 
			 {
				header : '购电单号',
				dataIndex : 'appNo',
				align : 'center'
			}, {
				header : '供电单位编号',
				dataIndex : 'orgNo',
				align : 'center',
				hidden:true
			}, {
				header : '用户编号',
				dataIndex : 'consNo',
				align : 'center'
			}, {
				header : '用户名称',
				dataIndex : 'consName',
				renderer : function(s,m,rec) {
					var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + s + '">' +
					"<a href='javascript:'onclick='opencsitab(\""+rec.get('consNo')+"\");" + "'>"+s +'</a></div>';
					return html;
				},
				align : 'center'
			}, {
				header : '终端资产编号',
				dataIndex : 'tmnlAssetNo',
				align : 'center',
				hidden:true
			}, {
				header : '终端地址',
				dataIndex : 'terminalAddr',
				align : 'center'	
			}, {
				header : '规约编码',
				dataIndex : 'protocolCode',
				align : 'center',
				hidden:true
			}, {
				header : '总加组号',
				dataIndex : 'totalNo',
				align : 'center'
			}, {
				header : '购电值单位标志',
				dataIndex : 'buyFlag',
				align : 'center',
				hidden:true
			}, {
				header : '追加/刷新标志',
				dataIndex : 'refreshFlag',
				align : 'center',
				hidden:true
			}, {
				header : '购电量（费）值',
				dataIndex : 'buyValue',
				align : 'center',
				hidden:true
			}, {
				header : '报警门阀值',
				dataIndex : 'alarmValue',
				align : 'center',
				hidden:true
			}, {
				header : '跳闸门限值',
				dataIndex : 'jumpValue',
				align : 'center',
				hidden:true
				}, {
				header : '购电量（费）值单位',
				dataIndex : 'buyValueUnit',
				align : 'center',
				hidden:true
			}, {
				header : '报警门阀值单位',
				dataIndex : 'alarmValueUnit',
				align : 'center',
				hidden:true
			}, {
				header : '跳闸门限值单位',
				dataIndex : 'jumpValueUnit',
				align : 'center',
				hidden:true
			}, {
				header : '实时工况',
				dataIndex : 'realTimeStatus',
				renderer:function(value){
				   		if(value){
				   			return "<font color='green';font-weight:bold>在线</font>";
				   		}else if(!value&&value!=null){
				   			return "<font color='red';font-weight:bold>离线</font>";
				   		}else 
				   		    return "";
				},
				align : 'center'
			}, {
				header : '保电',
				dataIndex : 'tmnlPaulPower',renderer:function(value){
				   		if(value=="1"){
				   			return "是";
				   		} else {
				   			return "否";
				   		}
				},
				align : 'center'
			}, {
				header : '剩余值',
				dataIndex : 'useValue',
				align : 'center'
			}, {
				header : '电能表标示',
				dataIndex : 'meterId',
				align : 'center',
				hidden:true
			}, {
				header : '下发状态',
				dataIndex : 'sendStatus',
				align : 'center'
			}, {
				header : '执行状态',
				dataIndex : 'execFlag',renderer:function(value){
				   		if(value=="3"|| value=="2"|| value=="1" || value =="0"){
				   			return "<font color='green';font-weight:bold>成功</font>";
				   		}else if(value=="-1"){
				   			return "<font color='red';font-weight:bold>失败</font>";
				   		}else if(value=="-9"){
				   		    return "未执行";
				   		} else {
				   			return "";
				   		}
				}, 
				align : 'center'
			}, {
				header : '报文',
				dataIndex : 'message',renderer: function(s, m, rec){
					return "<a href='javascript:'onclick='origFrameQryShow(\""+rec.get('consNo')+"\",\""+ rec.get('consName')+"\",\""+ rec.get('terminalAddr')+"\");" + "'>查看</a>"; 
				},
				align : 'center'
			}]);

	var perPaidParamSetResult = function perPaidParamSetResult(result,users){
		for(var i = 0; i < users.length; i++){
			var rec = userStore.getById(users[i].get('tmnlAssetNo'))
             if(users[i].get('execFlag') != '-9') {
				rec.set('execFlag','-1');//将返回结果默认全设置成失败
             }
			 for(var j = 0; j < result.length; j++){//循环返回结果list 根据keyId匹配记录，设置返回值
	    		if(result[j].tmnlAssetNo==users[i].get('tmnlAssetNo')) {
	    			if(result[j].execFlag !='-1'){
		        		rec.set('ctrlFlag',result[j].execFlag);
		        		rec.set('execFlag',result[j].execFlag);
	    			}
		        	break;
	    		}
	    	}
		}
		userStore.commitChanges();//提交修改
	}		
			
	var userGrid = new Ext.grid.GridPanel({
				id : 'ppsGridPanel',
				store : userStore,
				cm : userCm,
				sm : userSm,
				tbar : [{
							text : '参数下发',
							handler : function (){
								var users = userSm.getSelections();
					            if(null == users || users.length<=0){//未选中任何用户，直接返回
						     	    Ext.MessageBox.alert("提示","请选择用户！");
						     	    return;
						     	}
								ctrlCheckStaff(prePaidParamSetView,'ctrl');
							}
						},{
							text : '参数召测',
							handler : function (){
								var users = userSm.getSelections();
					            if(null == users || users.length<=0){//未选中任何用户，直接返回
						     	    Ext.MessageBox.alert("提示","请选择用户！");
						     	    return;
						     	}
								ctrlCheckStaff(prePaidParamFetchView);
							}
						},{
							text : '剩余值召测',
							handler : function (){
								var users = userSm.getSelections();
					            if(null == users || users.length<=0){//未选中任何用户，直接返回
						     	    Ext.MessageBox.alert("提示","请选择用户！");
						     	    return;
						     	}
								ctrlCheckStaff(prePaidParamLeftValue);
							}
						},'-',msgcheckBoxGroup, {xtype: 'tbfill'},{
							iconCls: 'minus',
							text : '删除选中行',
							handler : function() {
								var recs = userSm.getSelections();
								for (var i = 0; i < recs.length; i = i+ 1) {
									userStore.remove(recs[i]);
								}
							}
						}, {
							iconCls: 'plus',
							text : '加入群组',
							handler: function(){
								var groupTmnlArray = new Array();
								
								var recs = userSm.getSelections();
								for (var i = 0; i < recs.length; i++) {
									var tmnl = recs[i].get('consNo') + '`'
											+ recs[i].get('tmnlAssetNo');
									groupTmnlArray[i] = tmnl;
								}
								if (groupTmnlArray.length == 0) {
									Ext.Msg.alert('提示', '请选择要加入群组的用户');
								} else {
									saveOrUpdateGroupWindowShow(groupTmnlArray);
								}
			                 }
						}, {
							iconCls: 'minus',
							text : '删除成功用户',
							handler : ''
						},{
							iconCls: 'plus',
							text : '发送短信',
							handler : ''
						}],
				bbar : new Ext.ux.MyToolbar({
						store : userStore,
						enableExpAll : true, // excel导出全部数据
						expAllText : "全部",
						enableExpPage : true, // excel仅导出当前页
						expPageText : "当前页"
					})
			});
	
	var prePaidParamSetView = function prePaidParamSetView() {
		var users = userSm.getSelections();
        var tmnlPreParamSetList = new Array();
		var num = 0;//记录remoteCtrlList的长度
	    for(var i = 0; i < users.length; i++){
     		if(users[i].get('tmnlPaulPower') != '1') {
     			var buyUnit = users[i].get('buyValueUnit');
     			var alarmUnit = users[i].get('alarmValueUnit');
     			var jumpUnit = users[i].get('jumpValueUnit');
     			buyUnit = buyUnit==0?buyUnit:buyUnit*1000;
     			alarmUnit = alarmUnit==0?alarmUnit:alarmUnit*1000;
     			jumpUnit = jumpUnit==0?jumpUnit:jumpUnit*1000;
				tmnlPreParamSetList[num++] = users[i].get('orgNo')+'`'+users[i].get('consNo')+'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode') +
					'`' + users[i].get('appNo') +'`' + users[i].get('refreshFlag') + '`' + users[i].get('buyValue')*buyUnit + '`' + users[i].get('alarmValue')*alarmUnit + '`' + users[i].get('jumpValue')*jumpUnit;
     		}
        }
		if(tmnlPreParamSetList.length <= 0) {
			for(var i = 0; i < users.length; i++){
				var rec = userStore.getById(users[i].get('keyId'))
             	users[i].set('execFlag', '-9');
			}
			userStore.commitChanges();
			Ext.MessageBox.alert("提示","没有符合下发条件的终端!");
			return;
		}
		
		//取得控制轮次数组
        var roundArray  = checkBoxGroup.getValue();
        var round = roundArray[0].getRawValue();
        for(var i = 1; i < roundArray.length; i++){
        	round =round +','+roundArray[i].getRawValue();
        }
		//显示进度条
        var ov = true;
        var second = Ext.Ajax.timeout/1000;
        h_taskTime(second,function(){
        	ov = false;
        });
        if(tmnlPreParamSetList ==null ||tmnlPreParamSetList.length <=0) {
        	return;
        }
    	Ext.Ajax.request({
     		url:'./baseapp/prePaidParaSet!prePaidParamSetView.action',
     		params : {
     			second:second,
     			tmnlPreParamSetList : tmnlPreParamSetList,
     			turnFlag :round
     		},
     		success : function(response){
     			var cacheAndTmnlStatus = Ext.decode(response.responseText).cacheAndTmnlStatus;
     			if(cacheAndTmnlStatus!=""&& cacheAndTmnlStatus!=null){
     				Ext.MessageBox.alert("提示",cacheAndTmnlStatus);
     				return;
     			}
     			if(!ov) return;
     			var result = Ext.decode(response.responseText).resultList;
     			if(result==null ||result.length<=0){
     				Ext.MessageBox.alert("提示","预付费参数下发失败，全部终端无应答！");
     		   		return;
     			}
     			perPaidParamSetResult(result, users);//调用处理返回结果函数
     		    overFlat = true;
     		    Ext.Ajax.timeout = 30000;
     		    return;
     		},
     		failure : function(){
     		    Ext.MessageBox.alert("提示","预付费参数下发失败");
     		    if(!ov) return ;
	     		overFlat = true;
     		    return;
     		}
     	});
	}
	
	var prePaidParamFetchView = function prePaidParamSetView() {
		var users = userSm.getSelections();
        var tmnlPreParamSetList = new Array();
		var num = 0;//记录remoteCtrlList的长度
	    for(var i = 0; i < users.length; i++){
			tmnlPreParamSetList[num++] = users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');
        }

		//显示进度条
        var ov = true;
        var second = Ext.Ajax.timeout/1000;
        h_taskTime(second,function(){
        	ov = false;
        });
    	Ext.Ajax.request({
     		url:'./baseapp/prePaidParaSet!prePaidParamFetch.action',
     		params : {
     			second:second,
     			tmnlPreParamSetList : tmnlPreParamSetList
     		},
     		success : function(response){
     			var cacheAndTmnlStatus = Ext.decode(response.responseText).cacheAndTmnlStatus;
     			if(cacheAndTmnlStatus!=""&& cacheAndTmnlStatus!=null){
     				Ext.MessageBox.alert("提示",cacheAndTmnlStatus);
     				return;
     			}
     			if(!ov) return;
     			var result = Ext.decode(response.responseText).fetchList;
     			if(result==null ||result.length<=0){
     				Ext.MessageBox.alert("提示","预付费参数召测失败，全部终端无应答！");
     		   		return;
     			}
     			var record = new Array();
		     	for(var i = 0; i < users.length; i++){
		        	record[i] = new Array();
                	record[i][0] = users[i].get('keyId')
                	record[i][1] = users[i].get('appNo')
                	record[i][2] = users[i].get('orgName')
                	record[i][3] = users[i].get('consNo')
                	record[i][4] = users[i].get('consName')
                	record[i][5] = users[i].get('terminalAddr')
                	record[i][6] = users[i].get('totalNo')
		        	
		        	if(result==null || result.length<=0) continue;//如果返回结果为空，则不对召测结果设值
		        	
		        	for(var j = 0; j < result.length; j++){//循环返回结果list 根据keyId匹配记录，设置返回值
		        		if(result[j].keyId==users[i].get('keyId')) {
				        	record[i][7] = result[j].refreshFlag;
				        	record[i][8] = result[j].buyValue;
				        	record[i][9] = result[j].alarmValue;
				        	record[i][10] = result[j].jumpValue;
				        	break;
		        		}
		        	}
		        }
			    prePaidDataFetchStore.loadData(record);
				
     			userTab.setActiveTab(1);
     		    overFlat = true;
     		    Ext.Ajax.timeout = 30000;
     		    return;
     		},
     		failure : function(){
     		    Ext.MessageBox.alert("提示","预付费参数召测失败");
     		    if(!ov) return ;
	     		overFlat = true;
     		    return;
     		}
     	});
	}		
	//剩余值召测
	var prePaidParamLeftValue = function prePaidParamLeftValue() {
		var users = userSm.getSelections();
		var fetchTmnlArray = new Array();
        for(var i = 0; i < users.length; i++){
     		var tmnl =users[i].get('orgNo')+'`'+users[i].get('appNo')+'`'+users[i].get('consNo')+'`'+
     			users[i].get('meterId')+'`'+users[i].get('tmnlAssetNo') +'`'+users[i].get('totalNo');
     		fetchTmnlArray[i]=tmnl;
     		var rec = userStore.getById(users[i].get('keyId'));
     		if(null!=rec){
	     		rec.set('useValue',"");
     		}
        }
 	    userStore.commitChanges();
		var taskSecond= Ext.Ajax.timeout/1000;
        var ov = true;
        h_taskTime(taskSecond,function(){
        		ov = false;	
        }); 
        Ext.Ajax.request({
	     		url : './baseapp/useValueQuery!fetchUseValue.action',
	            params : {
	     			fetchTmnlList:fetchTmnlArray,
     				taskSecond:taskSecond
     			},
     			success : function(response){
     				if (!ov) {
							return;
						}
				    overFlat = true;
        			Ext.Ajax.timeout=30000;
					var result = Ext.decode(response.responseText);
					if(null!=result.msg&&""!=result.msg){
		 				Ext.MessageBox.alert("提示",result.msg);
		 				overFlat = true;
		 				return;
					}
					if(null!=result.fetchResultList && 0< result.fetchResultList.length){
		             	for(var j=0;j<result.fetchResultList.length;j++){
	     					var rec = userStore.getById(result.fetchResultList[j].keyId);	
	                    	if(null!=rec){
		 				     	rec.set('useValue',result.fetchResultList[j].useValue);//修改页面选中记录的状态
	                    	}
	             		}
		            	userStore.commitChanges();
		             }
     			}
        });
	}	
	
	var prePaidDataFetchCm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
		{header :'购电单号',sortable:true, dataIndex:'appNo',align:'center'},
	    {header : '供电单位', sortable: true, dataIndex : 'orgName', renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
					+ val + '</div>';
			return html;
		}, align : 'center', defaultWidth:140}, 
	    {header : '用户编号', sortable: true, dataIndex : 'consNo', align : 'center'}, 
	    {header : '用户名称', sortable: true, dataIndex : 'consName', renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
					+ val + '</div>';
			return html;
		}, align : 'center'}, 
	    {header : '总加组号', sortable:true,  dataIndex:'totalNo',align:'center'},
	    {header : '终端地址', sortable: true, dataIndex : 'terminalAddr', renderer : function (val) {
					 return "<div align = 'left'>"+ val + "</div>";
					 }, align : 'center'}, 
		{header : '追加/刷新标志', sortable: true, dataIndex : 'refreshFlag', align : 'center'},			 
	    {header : '购电量', sortable: true, dataIndex : 'buyValue', align : 'center'}, 
	    {header : '报警门阀值', sortable: true, dataIndex : 'alarmValue', align : 'center'},
	    {header : '跳闸门限值', sortable: true, dataIndex : 'jumpValue', align : 'center'}
	]);
	
    var prePaidDataFetchStore = new Ext.data.Store({
		reader : new Ext.data.ArrayReader({
					idIndex: 0
				}, [{name:'keyId'},
					{name:'appNo'},
				   {name : 'orgName'}, 
				   {name : 'consNo'}, 
				   {name : 'consName'}, 
				   {name : 'terminalAddr'}, 
				   {name:'totalNo'},
				   {name : 'refreshFlag'}, 
				   {name : 'buyValue'}, 
				   {name : 'alarmValue'},
				   {name : 'jumpValue'}
			])
	});
	
	function getPrePaidDataFetchGridPnl(){
    	var prePaidDataFetchGrid = new Ext.grid.GridPanel({
		        store : prePaidDataFetchStore,
		        cm : prePaidDataFetchCm,
	//		        sm : rt_powersm,
		        stripeRows : true,
		        autoScroll : true,
		        border: false,
		        bbar : new Ext.ux.MyToolbar({
					store : prePaidDataFetchStore,
					enableExpAll : true, // excel导出全部数据
					expAllText : "全部",
					enableExpPage : true, // excel仅导出当前页
					expPageText : "当前页"
				})
	    });
    
    	  //定义遥测功率的遥测结果数据面板
	    var prePaidDataFetchGridPnl = new Ext.Panel({
	    	id:'prePaidDataFetchGridPnl',
			layout:'fit',
		    border : false,
		    title : '召测结果',
			items : [prePaidDataFetchGrid]
		});
		
        return prePaidDataFetchGridPnl;
	}
			
	// 下方的tab页
	var userTab = new Ext.TabPanel({
				activeTab : 0,
				region : 'center',
				id : 'ppsUserTab',
				items : [{
							xtype : "panel",
							title : "用户列表",
							layout : 'fit',
							items : [userGrid]
						}, {
							xtype : "panel",
							title : "召测结果",
							layout : 'fit',
							items : [getPrePaidDataFetchGridPnl()]
						}],
				border : false
			});

	var paraRadioGroup = new Ext.form.RadioGroup({
				 id : 'prePaidBuyFlag',
				width : 300,
				columns : [100, 100],
				items : [{
							boxLabel : '电量控参数',
							name : 'buyFlag',
							inputValue:0,
							checked : true
						}, {
							boxLabel : '电费控参数',
							inputValue:1,
							name : 'buyFlag'
						}]
			})

	var conPanel = new Ext.Panel({
				id : 'pppsconpanel',
				border : false,
				region : 'north',
				bodyStyle : 'padding: 10px 0px 10px 10px',
				height : 75,
				items : [createDlPanel()]
			})

	var resultPanel = new Ext.Panel({
				id : 'pppsresultpanel',
				border : false,
				layout : 'border',
				autoScroll : true,
				height : 480,
				region : 'center',
				items : [ctPanel, userTab]
			})

	var radioPanel = new Ext.Panel({
				border : false,
				region : 'north',
				height : 40,
				items : [{
							layout : 'table',
							border : false,
							bodyStyle : 'padding: 10px 0px 10px 65px',
							layoutConfig : {
								columns : 2
							},
							items : [paraRadioGroup]
						}]
			})

	var panel = new Ext.form.FormPanel({
				border : false,
				id : 'pppsPanel',
				bodyStyle : 'padding: 10px 0px 10px 65px',
				layout : 'border',
				items : [
				radioPanel, {
							xtype : 'panel',
							region : 'center',
							border : false,
							layout : 'border',
							items : [conPanel, resultPanel]
						}]
			});
	renderModel(panel, '预付费控制参数下发');
});
//---------------------------------选中供电单位-----------------------start
function chooseOrgNo(){
	var orgRootNode = new Ext.tree.AsyncTreeNode({
		id : 'role_root',
		text : '请选择'
		});

	var orgTreeLoader = new Ext.tree.TreeLoader({
		autoScroll : true,
		dataUrl : 'baseapp/prePaidDebug!queryOrgTree.action'
	});
		
	var orgTree = new Ext.tree.TreePanel({
				width : 250,
				border : false,
				height:250,
				autoScroll : true,
				title : "",
				root : orgRootNode,
				loader : orgTreeLoader
			});
	
	var staffOrgWindow = new Ext.Window({
			frame:true,
			width:250,
			height:300,
			layout:"form",
			modal:true,
			closeAction:"close",
			plain:true,//设置背景颜色
			resizable:false,
			draggable:false,//不可移动
			buttonAlign:"center",//按钮的位置
			title:'【请选择供电单位】',
			items:[orgTree],
			buttons : [ {
					   text : "确定",
					   handler : function() {
					   	   selNodes = orgTree.getChecked();
					   	   if(Ext.isEmpty(selNodes) || 0 == selNodes.length){
					   	       Ext.MessageBox.alert("提示","必须选择一个供电单位！");
							   return;
					   	   }
					   	   var orgNos='';
					   	   Ext.each(selNodes, function(node){
			                    orgNos = orgNos + node.id + ',';
			                });
			               Ext.getCmp('prePaidParamSetOrgNo').setValue(orgNos.substring(0,orgNos.length-1));
			               Ext.getCmp('prePaidParamSetConsNo').setValue('');
			               Ext.getCmp('prePaidParamSetConsName').setValue('');
					   	   staffOrgWindow.close();
					   }
					} ,{
						text : "退出",
						handler : function() {
					   	   staffOrgWindow.close();
					}
			}]
	});
	
	staffOrgWindow.show();
}
//---------------------------------选中供电单位-----------------------end