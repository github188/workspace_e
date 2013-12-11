 /**
  *	预付费控制 
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
					url:'./baseapp/prePaidControl!queryPrePaidControl.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'prePaidControlList',
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
						name : 'tmnlAssetNo'
					}, {
						name : 'realTimeStatus'
					}, {
						name : 'sendStatus'
					}, {
						name : 'useValue'
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
				var cons = Ext.getCmp('prePaidCtrlConsNo');
				var tmnlAddr = Ext.getCmp('prePaidCtrlTmnlAddr');
				cons.setDisabled(false);
				cons.setValue(obj.consNo);
				tmnlAddr.setDisabled(false);
				tmnlAddr.setValue(obj.tmnlAssetNo)
				Ext.getCmp('prePaidCtrlOrgNo').setValue('');
				Ext.getCmp('prePaidCtrlTmnlAssetNo').setValue(obj.tmnlAssetNo);
			}
		}
	});
	
	var queryButton = new Ext.Button({
		text:'查询',
		width:65,
		handler: function (){
			userStore.baseParams = {
				orgNo:Ext.getCmp('prePaidCtrlOrgNo').getValue(),
				appNo:Ext.getCmp('prePaidCtrlAppNo').getValue(),
				consNo:Ext.getCmp('prePaidCtrlConsNo').getValue(),
				tmnlAssetNo:Ext.getCmp('prePaidCtrlTmnlAssetNo').getValue()
			}
			userStore.load();
		}
	});
	
	//供电单位
	var orgNoTF = new Ext.form.TextField({
			fieldLabel : "供电单位<font color='red'>*</font>",
			id:'prePaidCtrlOrgNo',
			labelSeparator:'',
			readOnly:true,
			editable:false,
			anchor:'100%'
	});
	
	orgNoTF.on('focus',function(){
		chooseCtrlOrgNo();
	});
	
	function createDlPanel() {
		var dlPanel = new Ext.Panel({
			border : false,
			id : 'ctrl_pppsdlpanel',
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
			}, {
				layout : 'form',
				border : false,
				labelWidth : 65,
				autoHeight : true,
				labelAlign : 'right',
				width : 200,
				items : [{
							xtype : 'textfield',
							fieldLabel : '购电单号',
							id:'prePaidCtrlAppNo',
							anchor : '80%'
						}]
			},{
				layout : 'form',
				border : false,
				labelWidth : 65,
				autoHeight : true,
				labelAlign : 'right',
				width : 200,
				items : [{
							xtype : 'textfield',
							id : 'prePaidCtrlConsNo',
							fieldLabel : '用户编号',
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
							id : 'prePaidCtrlTmnlAddr',
							fieldLabel : '终端地址',
							anchor : '80%'
						},{
							xtype:'hidden',
							id:'prePaidCtrlTmnlAssetNo'
							}]
					},{
				layout : 'form',
				border : false,
				labelWidth : 65,
				autoHeight : true,
				labelAlign : 'right',
				width : 200,
				items : []
			},{
				layout : 'form',
				border : false,
				labelWidth : 65,
				autoHeight : true,
				labelAlign : 'right',
				bodyStyle : 'padding: 0 0 0 95px;',
				width : 300,
				items : [queryButton]
					}]
				})
		return dlPanel;
	}

	function getDlPanel() {
		return Ext.getCmp('ctrl_pppsdlpanel');
	}

	function getDfPanel() {
		return Ext.getCmp('ctrl_pppsdfpanel');
	}

	var msgcheckBoxGroup = new Ext.form.CheckboxGroup({
				fieldLabel : '',
				labelSeparator : '',
				hideLabel : true,
				columns : [100, 70],
				items : [{
							boxLabel : '短信通知客户',
							name : 'cb-auto-1',
							inputValue : 1,
							checked : false
						}, {
							boxLabel : '短信订阅',
							name : 'cb-auto-2',
							inputValue : 2,
							checked : false
						}]
			})

	var ctPanel = new Ext.Panel({
				title : '预付费控制',
				border : false,
				id : 'ctrl_ppsFormPanel4',
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
							width : 250,
							bodyStyle : 'padding: 15px 0px 10px 15px',
							items : [msgcheckBoxGroup]
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
				header : '供电单位',
				dataIndex : 'orgNo',
				align : 'center',
				hidden:true
			},
			{
				header : '用户编号',
				dataIndex : 'consNo',
				align : 'center',
				hidden:true
			}, {
				header : '用户名称',
				dataIndex : 'consName',renderer : function(s,m,rec) {
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
				hidden:true,
				align : 'center'
			}, {
				header : '总加组号',
				dataIndex : 'totalNo',
				align : 'center'
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
				header : '下发状态',
				dataIndex : 'sendStatus',
				align : 'center'
			},{
				header : '参数下发',
				dataIndex : 'paramSend',
				align : 'center'
			}, {
				header : '执行状态',
				dataIndex : 'execFlag',renderer:function(value){
				   		if(value=="1" || value =="0"){
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

	//处理返回结果函数
	var perPaidCtrlResult = function perPaidCtrlResult(result,users){
		for(var i = 0; i < users.length; i++){
			var rec = userStore.getById(users[i].get('keyId'))
             if(users[i].get('execFlag') != '-9') {
				rec.set('execFlag','-1');//将返回结果默认全设置成失败
             }
			 for(var j = 0; j < result.length; j++){//循环返回结果list 根据keyId匹配记录，设置返回值
	    		if(result[j].tmnlAssetNo==users[i].get('keyId')) {
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
		id : 'ctrl_ppsGridPanel',
		store : userStore,
		cm : userCm,
		sm : userSm,
		tbar : [{
						text : '预购电投入',
						handler : function(){
							var users = userSm.getSelections();
				            if(null == users || users.length<=0){//未选中任何用户，直接返回
					     	    Ext.MessageBox.alert("提示","请选择用户！");
					     	    return;
					     	}
							ctrlCheckStaff(prePaidCtrlView,'ctrl');
						}
					},{
						text : '预购电解除',
						handler : function() {
							var users = userSm.getSelections();
				            if(null == users || users.length<=0){//未选中任何用户，直接返回
					     	    Ext.MessageBox.alert("提示","请选择用户！");
					     	    return;
					     	}
							ctrlCheckStaff(prePaidReleaseView,'ctrl');
						}
					}
//					,{
//						text : '预购电状态召测',
//						handler : ''
//					}
					,{xtype: 'tbfill'},{
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
						handler :  function(){
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
	
	var prePaidCtrlView = function prePaidCtrlView() {
		var users = userSm.getSelections();
        var tmnlPreControlList = new Array();
		var num = 0;//记录remoteCtrlList的长度
	    for(var i = 0; i < users.length; i++){
     		if(users[i].get('tmnlPaulPower') != '是') {
				tmnlPreControlList[num++] = users[i].get('orgNo')+'`'+users[i].get('consNo')+'`'+
					users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');
     		}
        }
		if(tmnlPreControlList ==null ||tmnlPreControlList.length <= 0) {
			for(var i = 0; i < users.length; i++){
				var rec = userStore.getById(users[i].get('keyId'))
             	users[i].set('execFlag', '-9');
			}
			userStore.commitChanges();
			Ext.MessageBox.alert("提示","没有符合下发条件的终端!");
			return;
		}

		//显示进度条
        var ov = true;
        var second = Ext.Ajax.timeout/1000;
        h_taskTime(second,function(){
        	ov = false;
        });
    	Ext.Ajax.request({
     		url:'./baseapp/prePaidControl!prePaidControlView.action',
     		params : {
     			second:second,
     			tmnlPreControlList : tmnlPreControlList
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
     				Ext.MessageBox.alert("提示","预付费控制投入失败，全部终端无应答！");
     		   		return;
     			}
     			perPaidCtrlResult(result, users);//调用处理返回结果函数
     		    overFlat = true;
     		    Ext.Ajax.timeout = 30000;
     		    return;
     		},
     		failure : function(){
     		    Ext.MessageBox.alert("提示","预付费控制投入失败");
     		    if(!ov) return ;
	     		overFlat = true;
     		    return;
     		}
     	});
	}	
	
	var prePaidReleaseView = function prePaidReleaseView() {
		var users = userSm.getSelections();
        var tmnlPreControlList = new Array();
		var num = 0;//记录remoteCtrlList的长度
	    for(var i = 0; i < users.length; i++){
     		if(users[i].get('tmnlPaulPower') != '是') {
				tmnlPreControlList[num++] = users[i].get('orgNo')+'`'+users[i].get('consNo')+'`'+
					users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo')+'`'+users[i].get('protocolCode');
     		}
        }
		if(tmnlPreControlList ==null ||tmnlPreControlList.length <= 0) {
			for(var i = 0; i < users.length; i++){
				var rec = userStore.getById(users[i].get('keyId'))
             	users[i].set('execFlag', '-9');
			}
			userStore.commitChanges();
			Ext.MessageBox.alert("提示","没有符合下发条件的终端!");
			return;
		}

		//显示进度条
        var ov = true;
        var second = Ext.Ajax.timeout/1000;
        h_taskTime(second,function(){
        	ov = false;
        });
    	Ext.Ajax.request({
     		url:'./baseapp/prePaidControl!prePaidReleaseView.action',
     		params : {
     			second:second,
     			tmnlPreControlList : tmnlPreControlList
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
     				Ext.MessageBox.alert("提示","预付费解除失败，全部终端无应答！");
     		   		return;
     			}
     			perPaidCtrlResult(result, users);//调用处理返回结果函数
     		    overFlat = true;
     		    Ext.Ajax.timeout = 30000;
     		    return;
     		},
     		failure : function(){
     		    Ext.MessageBox.alert("提示","预付费解除失败");
     		    if(!ov) return ;
	     		overFlat = true;
     		    return;
     		}
     	});
	}
	
	// 下方的tab页
	var userTab = new Ext.TabPanel({
				activeTab : 0,
				region : 'center',
				id : 'ctrl_ppsUserTab',
				items : [{
							xtype : "panel",
							title : "用户列表",
							layout : 'fit',
							items : [userGrid]
						}, {
							xtype : "panel",
							title : "召测结果",
							layout : 'fit',
							items : []
						}],
				border : false
			});

	var conPanel = new Ext.Panel({
				id : 'ctrl_pppsconpanel',
				border : false,
				region : 'north',
				bodyStyle : 'padding: 10px 0px 10px 10px',
				height : 75,
				items : [createDlPanel()]
			})

	var resultPanel = new Ext.Panel({
				id : 'ctrl_pppsresultpanel',
				border : false,
				layout : 'border',
				autoScroll : true,
				height : 480,
				region : 'center',
				items : [ctPanel, userTab]
			})

	var panel = new Ext.form.FormPanel({
				border : false,
				id : 'ctrl_pppsPanel',
				bodyStyle : 'padding: 10px 0px 10px 65px',
				layout : 'border',
				items : [
//				radioPanel,
				{
							xtype : 'panel',
							region : 'center',
							border : false,
							layout : 'border',
							items : [conPanel, resultPanel]
						}]
			});
	renderModel(panel, '预付费控制');
});
//---------------------------------选中供电单位-----------------------start
function chooseCtrlOrgNo(){
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
			               Ext.getCmp('prePaidCtrlOrgNo').setValue(orgNos.substring(0,orgNos.length-1));
			               Ext.getCmp('prePaidCtrlConsNo').setValue('');
			               Ext.getCmp('prePaidCtrlTmnlAssetNo').setValue('');
			                Ext.getCmp('prePaidCtrlTmnlAddr').setValue('');
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