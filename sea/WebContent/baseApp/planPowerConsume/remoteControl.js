/**
 * 遥控
 * 
 * @author jiangweichao
 */

//定义全局变量 1表示从方案获取 0表示从树点击而来
var checkSchemeNo = 0;
 /**
 * 检查是否为数字 
 * @param {}
 * str
 * @return {Boolean} true：数字，false:<b>不是</b>数字;
*/
this.isNum = function(str) {
    var re = /^[\d]+$/
    return re.test(str);
}

function origFrameQryShow(consNo,consName,tmnlAssetAddr){
	staticConsNo = consNo;
	staticConsName = consName;
	staticTmnlAssetAddr = tmnlAssetAddr;
	openTab("原始报文查询","./baseApp/dataGatherMan/origFrameQry.jsp");
}

//页面执行函数
Ext.onReady(function() {
	var testNum = 2;
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
    
	//load第二次报错问题
	Ext.override(Ext.data.Store, {
        loadRecords : function(o, options, success){
            if(!o || success === false){
                if(success !== false){
                    this.fireEvent("load", this, [], options);
                }
                if(options.callback){
                    options.callback.call(options.scope || this, [], options, false);
                }
                return;
            }
            var r = o.records, t = o.totalRecords || r.length;
            if(!options || options.add !== true){
                if(this.pruneModifiedRecords){
                    this.modified = [];
                }
                for(var i = 0, len = r.length; i < len; i++){
                    r[i].join(this);
                }
                if(this.snapshot){
                    this.data = this.snapshot;
                    delete this.snapshot;
                }
                this.data.clear();
                this.data.addAll(r);
                this.totalLength = t;
                this.applySort();
                this.fireEvent("datachanged", this);
            }else{
                var add = [];
                for(var i = 0, len = r.length; i < len; i++){
                    var record = r[i];
                    if(this.getById(record.id)){
                        this.data.replace(record.id, record);
                        this.fireEvent("update", this, record, Ext.data.Record.EDIT);
                    }else{
                        add.push(record);
                    }
                }
                this.totalLength = Math.max(t, this.data.length + add.length);
                this.add(add);
            }
            this.fireEvent("load", this, r, options);
            if(options.callback){
                options.callback.call(options.scope || this, r, options, true);
            }
        }
    });
	
	//返回遥控的上部分panel
    
	//页面校验函数
	function checkInitData(){
		var rcArray  = roundCheck.getValue();
		if(0 == rcArray.length){
		    Ext.MessageBox.alert("提示","请选择轮次！"); 
			return false;
		}
		if("" == alarmTime.getValue().trim()){
			Ext.MessageBox.alert("提示","请输入告警时间！"); 
            return false;
		}
		if(!isNum(alarmTime.getValue().trim())){
            Ext.MessageBox.alert("提示","告警时间必须输入数字！");
	        return false;
        }
		if(0 > alarmTime.getValue().trim() || 15 < alarmTime.getValue().trim()){
            Ext.MessageBox.alert("提示","告警时间范围必须在0-15之间！");
            return false;
        }
                
        var limitTime = Ext.getCmp("rc_limitTime").getValue();
        if(limitTime*10%10 != 0){
            Ext.MessageBox.alert("提示","限电时间范围必须整数！");
            return false;
        }
        return true;
    }
    //方案弹出页面校验函数
    function checkPopWindowData(){
    	if("" == schemeName.getValue().trim() || 0 >=  schemeName.getValue().trim().length){
            Ext.MessageBox.alert("提示","请输入方案名！"); 
            return false;
        }
        if("" == newStartDate.getValue()){
            Ext.MessageBox.alert("提示","请输入开始时间！"); 
            return false;
        }
        if("" == newEndDate.getValue()){
            Ext.MessageBox.alert("提示","请输入结束时间！"); 
            return false;
        }
        if(newStartDate.getValue() > newEndDate.getValue()){
            Ext.MessageBox.alert("提示","开始时间应早于结束时间！"); 
            return false;
        }
        return true;
    }
    
     //方案弹出页面校验函数
    function checkUpdatePopWindowData(){
        if("" == updateStartDate.getValue()){
            Ext.MessageBox.alert("提示","请输入开始时间！"); 
            return false;
        }
        if("" == updateEndDate.getValue()){
            Ext.MessageBox.alert("提示","请输入结束时间！"); 
            return false;
        }
        if(updateStartDate.getValue() > updateEndDate.getValue()){
            Ext.MessageBox.alert("提示","开始时间应早于结束时间！"); 
            return false;
        }
       /* if("" == limitUpdateTypeComboBox.getValue()){
	        Ext.MessageBox.alert("提示","必须选择限电类型！"); 
            return false;
        }*/
        return true;
    }
	
//	轮次编号
	var roundCheck = new Ext.form.CheckboxGroup({
		width:550,
		fieldLabel:'有效轮次',
		labelSeparator:'',
		items:[{
			boxLabel:'轮次1',inputValue: '1',name:'1'
		},{
			boxLabel:'轮次2',inputValue: '2',name:'2'
		},{
			boxLabel:'轮次3',inputValue: '3',name:'3'
		},{
			boxLabel:'轮次4',inputValue: '4',name:'4'
		},{
			boxLabel:'轮次5',inputValue: '5',name:'5'
		},{
			boxLabel:'轮次6',inputValue: '6',name:'6'
		},{
			boxLabel:'轮次7',inputValue: '7',name:'7'
		},{
			boxLabel:'轮次8',inputValue: '8',name:'8'
		}]
	});
	
	 //告警时间
	var alarmTime = new Ext.form.TextField({
			fieldLabel : '告警时间(分钟)',
			name : 'alarmTime',
			emptyText:'0-15分钟',
			labelSeparator:'',
			anchor : '100%'
	});
	
	//定义分钟
//	var minute = new Ext.form.Label({
//	    text : "分钟",
//	    width : 50
//	});
	
	//定义小时
	var hour = new Ext.form.Label({
	    text : "小时",
	    width : 50
	});
	
	var topRadio = new Ext.form.RadioGroup({
		items:[new Ext.form.Radio({
			boxLabel:'遥测开关拉合闸',	
			name:'condRadio',
			inputValue:'ctrl',
			width:120,
			checked:true,
			listeners:{
				check : function(checkbox, checked){
					if(checked){
						remoteCtrlTabPnl.layout.setActiveItem(0);
					}
				}
			}
		}),{
			boxLabel:'遥测电压电流',
			name:'condRadio',
			inputValue:'ui',
			width:100,
			listeners:{
				check : function(checkbox, checked){
					if(checked) {
						remoteCtrlTabPnl.layout.setActiveItem(1);
					}
				}
			}
		},{
			boxLabel:'遥测功率',
			name:'condRadio',
			inputValue:'power',
			width:100,
			listeners:{
				check : function(checkbox, checked){
					if(checked) {					
						remoteCtrlTabPnl.layout.setActiveItem(2);
					}
				}	
			}
		}]
		
	});
	
	//遥测电压电流按钮
	var pcBtn = new Ext.Button({
			text : '遥测电压电流',
			name : '',
			width : 80,
			handler : function() {
				var users = uiUserSm.getSelections();
		        if(null == users || users.length==0){
		     	    Ext.MessageBox.alert("提示","请选择用户！");
		     	    return;
		     	}
				ctrlCheckStaff(pcFatch,'query');
			}
	});
	
	var pcFatch = function pcFatch(){
		var users = uiUserSm.getSelections();
		var remoteCtrlList = new Array(); //定义向后台传的数组
		rt_pcStore.removeAll();//先将原来的召测结果清除
		var record = new Array();//选中用户记录
		if (Ext.getCmp('uiGsselectAllcb').checked) { //如果选中全选按钮
			for (var i = 0; i < uiUserListStore.getCount(); i++) {
	        	remoteCtrlList[i] = uiUserListStore.getAt(i).data.tmnlAssetNo+'`'+uiUserListStore.getAt(i).data.mpSn;
			}
		} else {
	     	for(var i = 0; i < users.length; i++){
				remoteCtrlList[i] = users[i].get('tmnlAssetNo')+'`'+users[i].get('mpSn');//组织向后台传的字符串数组
	        }
		}
		//显示进度条
        var ov = true;
        var second = Ext.Ajax.timeout/1000;
        h_taskTime(second,function(){
        	ov = false;
        });
        if(remoteCtrlList.length ==0) {//检查到没有选中任何终端，不向后台发送
        	return;
        }
    	Ext.Ajax.request({
     		url:'./baseapp/remoteControl!remoteTestPC.action',
     		params : {
     			second:second,
     			remoteCtrlList:remoteCtrlList
     		},
     		success : function(response){
     			var cacheAndTmnlStatus = Ext.decode(response.responseText).cacheAndTmnlStatus;
     			if(cacheAndTmnlStatus!=""&& cacheAndTmnlStatus!=null){
     				Ext.MessageBox.alert("提示",cacheAndTmnlStatus);
     				return;
     			}
     			if(!ov) return;
     			var result = Ext.decode(response.responseText).rtPCList;
     			
     			if (Ext.getCmp('uiGsselectAllcb').checked) { //如果选中全选按钮
					for (var i = 0; i < uiUserListStore.getCount(); i++) {
						record[i] = new Array();
						record[i][0] = uiUserListStore.getAt(i).data.keyId;
			        	record[i][1] =uiUserListStore.getAt(i).data.orgName;
			        	record[i][2] = uiUserListStore.getAt(i).data.consNo;
			        	record[i][3] = uiUserListStore.getAt(i).data.consName;
			        	record[i][5] = uiUserListStore.getAt(i).data.terminalAddr;
			        	record[i][6] = uiUserListStore.getAt(i).data.mpSn;
			        	record[i][7] = uiUserListStore.getAt(i).data.ct;
			        	record[i][8] = uiUserListStore.getAt(i).data.pt;
			        	
			        	if(result==null || result.length<=0) continue;
			        	
			        	for(var j = 0; j < result.length; j++){//循环返回结果list 根据keyId匹配记录，设置返回值
			        		if(result[j].keyId==uiUserListStore.getAt(i).data.keyId) {
			        			record[i][9] = result[j].ua;
					        	record[i][10] = result[j].ub;
					        	record[i][11] = result[j].uc;
					        	record[i][12] = result[j].ia;
					        	record[i][13] = result[j].ib;
					        	record[i][14] = result[j].ic;
					        	break;
			        		}
			        	}
					}
					rt_pcStore.proxy = new Ext.ux.data.PagingMemoryProxy(record);
					rt_pcStore.load({
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
			        	record[i][5] = users[i].get('terminalAddr');
			        	record[i][6] = users[i].get('mpSn');
			        	record[i][7] = users[i].get('ct');
			        	record[i][8] = users[i].get('pt');
			        	
			        	if(result==null || result.length<=0) continue;//如果返回结果为空，则不对召测结果设值
			        	
			        	for(var j = 0; j < result.length; j++){//循环返回结果list 根据keyId匹配记录，设置返回值
			        		if(result[j].keyId==users[i].get('keyId')) {
			        			record[i][9] = result[j].ua;
					        	record[i][10] = result[j].ub;
					        	record[i][11] = result[j].uc;
					        	record[i][12] = result[j].ia;
					        	record[i][13] = result[j].ib;
					        	record[i][14] = result[j].ic;
					        	break;
			        		}
			        	}
			        }
			        rt_pcStore.loadData(record);
				}
                uiRemoteCtrlTabPnl.setActiveTab(1);
     		    overFlat = true;
     		    Ext.Ajax.timeout = 30000;
     		    return;
     		},
     		failure : function(){
     			uiRemoteCtrlTabPnl.setActiveTab(1);
     			if(!ov) return ;
	     		overFlat = true;
     		    Ext.MessageBox.alert("提示","遥测电压电流失败");
     		    return;
     		}
     	});
     	Ext.Ajax.timeout = 30000;
	};
	
	//遥测功率按钮
	var powerBtn = new Ext.Button({
			text : '遥测功率',
			name : '',
			width : 80,
			handler : function() {
				var users = powerUserSm.getSelections();
	            if(null == users || users.length==0){
		     	    Ext.MessageBox.alert("提示","请选择用户！");
		     	    return;
	            }
				ctrlCheckStaff(powerFatch, 'query');
			}
	});
	
	var powerFatch = function powerFatch(){
		var users = powerUserSm.getSelections();
		var remoteCtrlList = new Array();
		rt_powerStore.removeAll();
		var record = new Array();//选中用户记录
		if (Ext.getCmp('powerGsselectAllcb').checked) {//全选选中
			for (var i = 0; i < powerUserListStore.getCount(); i++) {
				remoteCtrlList[i] = powerUserListStore.getAt(i).data.tmnlAssetNo+
					'`'+powerUserListStore.getAt(i).data.totalNo;
			}
		} else {
	     	for(var i = 0; i < users.length; i++){
				remoteCtrlList[i] =  users[i].get('tmnlAssetNo')+'`'+users[i].get('totalNo');
            }
            if(remoteCtrlList.length ==0) {
            	return;
            }
		}
		//显示进度条
        var ov = true;
        var second = Ext.Ajax.timeout/1000;
        h_taskTime(second,function(){
        	ov = false;
        });
        if(remoteCtrlList.length ==0) {
        	return;
        }
    	Ext.Ajax.request({
     		url:'./baseapp/remoteControl!remoteTestPower.action',
     		params : {
     			second:second,
     			remoteCtrlList : remoteCtrlList
     		},
     		success : function(response){
     			var cacheAndTmnlStatus = Ext.decode(response.responseText).cacheAndTmnlStatus;
     			if(cacheAndTmnlStatus!=""&& cacheAndTmnlStatus!=null){
     				Ext.MessageBox.alert("提示",cacheAndTmnlStatus);
     				return;
     			}
     			if(!ov) return;
     			var result = Ext.decode(response.responseText).rtPowerList;
     			
     			if (Ext.getCmp('powerGsselectAllcb').checked) { //如果选中全选按钮
					for (var i = 0; i < powerUserListStore.getCount(); i++) {
						record[i] = new Array();
						record[i][0] = powerUserListStore.getAt(i).data.keyId;
			        	record[i][1] =powerUserListStore.getAt(i).data.orgName;
			        	record[i][2] = powerUserListStore.getAt(i).data.consNo;
			        	record[i][3] = powerUserListStore.getAt(i).data.consName;
			        	record[i][5] = powerUserListStore.getAt(i).data.terminalAddr;
			        	record[i][6] = powerUserListStore.getAt(i).data.totalNo;
			        	record[i][7] = powerUserListStore.getAt(i).data.runCap;
			        	
			        	if(result==null || result.length<=0) continue;//如果返回结果为空，则不对召测结果设值
			        	
			        	for(var j = 0; j < result.length; j++){//循环返回结果list 根据keyId匹配记录，设置返回值
			        		if(result[j].keyId==powerUserListStore.getAt(i).data.keyId) {
			        			record[i][8] = result[j].totalP;
					        	record[i][9] = result[j].totalQ;
					        	break;
			        		}
			        	}
					}
					rt_powerStore.proxy = new Ext.ux.data.PagingMemoryProxy(record);
					rt_powerStore.load({
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
			        	record[i][5] = users[i].get('terminalAddr');
			        	record[i][6] = users[i].get('totalNo');
			        	record[i][7] = users[i].get('runCap');
			        	
			        	if(result==null || result.length<=0) continue;//如果返回结果为空，则不对召测结果设值
			        	
			        	for(var j = 0; j < result.length; j++){//循环返回结果list 根据keyId匹配记录，设置返回值
			        		if(result[j].keyId==users[i].get('keyId')) {
			        			record[i][8] = result[j].totalP;
					        	record[i][9] = result[j].totalQ;
					        	break;
			        		}
			        	}
			        }
			        rt_powerStore.loadData(record);
				}
                pwrRemoteCtrlTabPnl.setActiveTab(1);
     		    overFlat = true;
     		    Ext.Ajax.timeout = 30000;
     		    return;
     		},
     		failure : function(){
     			pwrRemoteCtrlTabPnl.setActiveTab(1);
     			if(!ov) return ;
	     		overFlat = true;
     		    Ext.MessageBox.alert("提示","遥测功率失败");
     		    return;
     		}
     	});
	};
	
	//遥测开关按钮
	var switchBtn = new Ext.Button({
			text : '遥测开关',
			name : '',
			width : 80,
			handler : function() {
				var users = userSm.getSelections();
	            if(null == users || users.length==0){
		     	    Ext.MessageBox.alert("提示","请选择用户！");
		     	    return;
		     	}
				ctrlCheckStaff(switchFetch,'query');
			}
	});
	
	var switchFetch = function switchFetch(){
		var users = userSm.getSelections();
		rt_switchStore.removeAll();
		var remoteCtrlList = new Array();
		var record = new Array();//选中用户记录
		if (Ext.getCmp('ctrlGsselectAllcb').checked) {//全选选中
			for (var i = 0; i < userListStore.getCount(); i++) {
				remoteCtrlList[i] = userListStore.getAt(i).data.tmnlAssetNo;
			}
		} else {
	     	for(var i = 0; i < users.length; i++){
				remoteCtrlList[i] = users[i].get('tmnlAssetNo');
            }
		}
        //显示进度条
        var ov = true;
        var second = Ext.Ajax.timeout/1000;
        h_taskTime(second,function(){
        	ov = false;
        });
        if(remoteCtrlList.length ==0) {
        	return;
        }
    	Ext.Ajax.request({
     		url:'./baseapp/remoteControl!remoteTestSwitch.action',
     		params : {
     			second:second,
     			remoteCtrlList:remoteCtrlList
     		},
     		success : function(response){
     			var cacheAndTmnlStatus = Ext.decode(response.responseText).cacheAndTmnlStatus;
     			if(cacheAndTmnlStatus!=""&& cacheAndTmnlStatus!=null){
     				Ext.MessageBox.alert("提示",cacheAndTmnlStatus);
     				return;
     			}
     			if(!ov) return;
     			var result = Ext.decode(response.responseText).rtSwitchList;
     			if (Ext.getCmp('ctrlGsselectAllcb').checked) { //如果选中全选按钮
					for (var i = 0; i < userListStore.getCount(); i++) {
						record[i] = new Array();
			        	record[i][0] =userListStore.getAt(i).data.orgName;
			        	record[i][1] = userListStore.getAt(i).data.consNo;
			        	record[i][2] = userListStore.getAt(i).data.consName;
			        	record[i][3] = userListStore.getAt(i).data.tmnlAssetNo;
			        	record[i][4] = userListStore.getAt(i).data.terminalAddr;
			        	
			        	if(result==null || result.length<=0) continue;//如果返回结果为空，则不对召测结果设值
			        	
			        	for(var j = 0; j < result.length; j++){//循环返回结果list 根据keyId匹配记录，设置返回值
			        		if(result[j].tmnlAssetNo==userListStore.getAt(i).data.tmnlAssetNo) {
			        			record[i][5] = result[j].switch1;
					        	record[i][6] = result[j].switch2;
					        	record[i][7] = result[j].switch3;
					        	record[i][8] = result[j].switch4;
					        	record[i][9] = result[j].switch5;
					        	record[i][10] = result[j].switch6;
					        	record[i][11] = result[j].switch7;
					        	record[i][12] = result[j].switch8;
					        	break;
			        		}
			        	}
					}
					rt_switchStore.proxy = new Ext.ux.data.PagingMemoryProxy(record);
					rt_switchStore.load({
						params : {
							start : 0,
							limit : DEFAULT_PAGE_SIZE
						}
					});
				} else {
			     	for(var i = 0; i < users.length; i++){
			        	record[i] = new Array();
			        	record[i][0] = users[i].get('orgName');
			        	record[i][1] = users[i].get('consNo');
			        	record[i][2] = users[i].get('consName');
			        	record[i][3] = users[i].get('tmnlAssetNo');
			        	record[i][4] = users[i].get('terminalAddr');
			        	
			        	if(result==null || result.length<=0) continue;//如果返回结果为空，则不对召测结果设值
			        	
			        	for(var j = 0; j < result.length; j++){//循环返回结果list 根据keyId匹配记录，设置返回值
			        		if(result[j].tmnlAssetNo==users[i].get('tmnlAssetNo')) {
			        			record[i][5] = result[j].switch1;
					        	record[i][6] = result[j].switch2;
					        	record[i][7] = result[j].switch3;
					        	record[i][8] = result[j].switch4;
					        	record[i][9] = result[j].switch5;
					        	record[i][10] = result[j].switch6;
					        	record[i][11] = result[j].switch7;
					        	record[i][12] = result[j].switch8;
					        	break;
			        		}
			        	}
			        }
			        rt_switchStore.loadData(record);
				}
                ctrlRemoteCtrlTabPnl.setActiveTab(1);
     		    overFlat = true;
     		    Ext.Ajax.timeout = 30000;
     		    return;
     		},
     		failure : function(){
     			ctrlRemoteCtrlTabPnl.setActiveTab(1);
     			if(!ov) return ;
	     		overFlat = true;
     		    Ext.MessageBox.alert("提示","遥测开关失败");
     		    return;
     		}
     	});
	};
	//一次接线图按钮 还没做处理哦
//		var lineChartBtn = new Ext.Button({
//				text : '一次接线图',
//				name : '',
//				width : 80,
//				handler : function() {
//					getRemoteCtrlTabPanel().remove(getBlankPanel());
//					getRemoteCtrlTabPanel().remove(getRemoteTestPowerGridPanel());
//					getRemoteCtrlTabPanel().remove(getRemoteTestPCGridPanel());
//					getRemoteCtrlTabPanel().remove(getRemoteTestSwitchGridPanel());
//					getRemoteCtrlTabPanel().add(getBlankPnl());
//				}
//		});
	
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
   	           text:'遥控方案加载'
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
   	           	   var schemeIdValue;
   	           	   var type = "schemeId";
   	           	   if(0 < selectionsArray.length){
   	                   schemeIdValue = loadFuncStore.getAt(selectionsArray[selectionsArray.length-1]).get('ctrlSchemeId');
   	           	   }
   	           	   checkSchemeNo = schemeIdValue;
   	           	   
   	               userListStore.removeAll();
   	           	   userListStore.baseParams = {//遥测开关拉合闸列表
   	           	   	   schemeId:schemeIdValue,
   	           	   	   nodeType:type
   	           	   };
   	           	   userListStore.load({
   	           	   	   callback: function(records, options, success){ 
                           if(null != records && 0 < records.length){
                                alarmTime.setValue(records[0].get("alertDelayHours"));
                                Ext.getCmp("rc_limitTime").setValue(records[0].get("limitMins"));
                           } else {
                           	   alarmTime.setValue();
                           	   Ext.getCmp("rc_limitTime").setValue(0);
                           }
                       }  
   	           	   });
   	           	   //遥测电压电流
   	           	   uiUserListStore.removeAll();
   	           	   uiUserListStore.baseParams = {//遥测开关拉合闸列表
   	           	   	   schemeId:schemeIdValue,
   	           	   	   nodeType:type
   	           	   };
   	           	   uiUserListStore.load();
   	           	   
   	           	   //遥测功率
   	           	   powerUserListStore.removeAll();
   	           	   powerUserListStore.baseParams = {//遥测开关拉合闸列表
   	           	   	   schemeId:schemeIdValue,
   	           	   	   nodeType:type
   	           	   };
   	           	   powerUserListStore.load();
   	           	   
   	               loadScheme.hide();
  		       }
   	       }] 
   	    }]
    });
    
    //Json对象
    var loadFuncStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url:'./baseapp/remoteControl!loadScheme.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'schemeList'
			}, [{name:'ctrlSchemeId'},
				{name:'ctrlSchemeName'},
				{name:'ctrlSchemeType'}])
	});
    
    //多选框
    var multiSel = new Ext.ux.form.MultiSelect({
				labelSeparator : '',
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
   	           	   loadScheme.hide();
  		       }
   	       }] 
   	    }]
    });

    //方案加载窗口
    var loadScheme = new Ext.Window({
        name:'loadScheme',
        height:250,
        width:400,
        closeAction:'hide',
        resizable:false,
        layout:'border',
        title:'方案加载',
        items:[loadSchemeTopPnl,loadSchemeBtmPnl]
    });
	
	//加载方案按钮
	var loadBtn = new Ext.Button({
			text : '加载方案',
			name : '',
			width : 80,
			handler : function() {
  		     	loadFuncStore.load();
  		        loadScheme.show();
			}
	});
	//--------------------------------方案加载----------------------------------end
	
	//--------------------------------方案保存----------------------------------start
	//开始日期
	var newStartDate = new Ext.form.DateField({
		fieldLabel : '限电日从',
		name : 'newStartDate',
		anchor : '90%',
	    value : new Date(),
	    labelSeparator:''
    });

    //结束日期
    var newEndDate = new Ext.form.DateField({
		fieldLabel : '到',
		name : 'newEndDate',
		anchor : '76%',
	    value : new Date().add(Date.DAY,30),
	    labelSeparator:''
    });
    //开始日期
	var updateStartDate = new Ext.form.DateField({
		fieldLabel : '限电日从',
		name : 'newStartDate',
		anchor : '90%',
	    value : new Date(),
	    labelSeparator:''
    });

    //结束日期
    var updateEndDate = new Ext.form.DateField({
		fieldLabel : '到',
		name : 'newEndDate',
		anchor : '76%',
	    value : new Date().add(Date.DAY,30),
	    labelSeparator:''
    });
	
	//方案label
	var schemeLabel = new Ext.form.TextField({
	        fieldLabel:'方案名称',
   	        value:'遥控方案',
   	        readOnly:true,
   	        labelSeparator:'',
            anchor:'95%' 
	});
	//方案名
	var schemeName = new Ext.form.TextField({
            width:142
	});
	//方案备注
    var remoteNewSchemeRemark = new Ext.form.TextArea({
    	width:255,
    	height:80,
        fieldLabel:'备    注'
    });
    var remoteUpdateSchemeRemark = new Ext.form.TextArea({
    	width:255,
    	height:80,
        fieldLabel:'备    注'
    });
	
	//方案名列表
	var remoteSchemeComBox = new Ext.form.ComboBox({
		store : loadFuncStore,
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
    
    //定义radio选择组
    var rcRadioGroup = new Ext.form.RadioGroup({
	    width : 200,
	    height :20,
	    items : [new Ext.form.Radio({
		    boxLabel : '另存为方案',
		    name : 'ec-radioGroup',
		    inputValue : '1',
		    checked : true,
		    handler:function(checkbox, checked){
		    	if(checked){
		    		saveSchemePenl.layout.setActiveItem(0);
		    	}
		    }
	    }), new Ext.form.Radio({
		    boxLabel : '保存方案',
		    name : 'ec-radioGroup',
		    inputValue : '2',
		    handler:function(checkbox, checked){
		    	if(checked){
		    		loadFuncStore.load();
		    		saveSchemePenl.layout.setActiveItem(1);
		    	}
		    }
	    })]
    });
    
      //下浮控radio
     var rcRadioGroupPanel = new Ext.Panel({
    	layout:'form',
        border : false,
        bodyStyle : 'padding:5px 0px 0px 35px',
        labelAlign: "right",
        labelWidth:1,
        items:[rcRadioGroup] 
    });
    
    //保存方案按钮
    var saveSchemeShowButton = new Ext.Button({
    	text:'确定',
 	    handler:function(){
 	        if(!checkInitData()){
                return;
            }
            if(!checkPopWindowData()){
                return;
            }
	     	Ext.Ajax.request({
				url : './baseapp/remoteControl!checkName.action',
				params : {
					schemeName : schemeLabel.getValue() + '-' + schemeName.getValue().trim(),
					operType : rcRadioGroup.getValue().getRawValue()
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (!result.checkSchemeName) {
						var remoteCtrlList = new Array();
						var num = 0;//记录remoteCtrlList的长度
						if (Ext.getCmp('ctrlGsselectAllcb').checked) {//全选选中
							for (var i = 0; i < userListStore.getCount(); i++) {
								if(userListStore.getAt(i).data.tmnlPaulPower !='是') {
									remoteCtrlList[num++] = userListStore.getAt(i).data.orgNo+'`'+userListStore.getAt(i).data.consNo+
										'`'+userListStore.getAt(i).data.tmnlAssetNo+'`'+userListStore.getAt(i).data.protocolCode;
								}
							}
						} else {
							users = userSm.getSelections();
			                if(null == users || users.length<=0){//未选中任何用户，直接返回
			  		     	    Ext.MessageBox.alert("提示","请选择用户！");
			  		     	    return;
			  		     	}
			  		     	for(var i = 0; i < users.length; i++){
			  		     		if(users[i].get('tmnlPaulPower') != '是') {
									remoteCtrlList[num++] = users[i].get('orgNo')+'`'+users[i].get('consNo')+'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('protocolCode');
			  		     		}
			                }
						}
						if(remoteCtrlList.length<=0){
							Ext.MessageBox.alert("提示","没有符合条件的用户！");
		  		     	    return;
						}
						var alartDelayHours = alarmTime.getValue().trim();
						if ('' == alartDelayHours) {
							alartDelayHours = 0;
						}
						Ext.Ajax.request({
							url : './baseapp/remoteControl!addSolution.action',
							params : {
								schemeName : schemeLabel.getValue() + '-' + schemeName.getValue().trim(),
								schemeRemark : remoteNewSchemeRemark.getValue(),
								newStartDate : newStartDate.getValue(),
								newEndDate : newEndDate.getValue(),
								remoteCtrlList : remoteCtrlList,
								limitMins : Ext.getCmp("rc_limitTime").getValue(),
								alertDelayHours : alartDelayHours
							},
							success : function() {
								Ext.MessageBox.alert("提示", "保存成功");
								viewPanel.getEl().unmask();
								return;
							},
							failure : function() {
								Ext.MessageBox.alert("提示", "保存失败");
								viewPanel.getEl().unmask();
								return;
							}
						});
						viewPanel.getEl().mask('保存中...');
						saveScheme.hide();
					} else {
						Ext.MessageBox.alert("提示", "该方案已经存在!");
						viewPanel.getEl().unmask();
						return;
					}
				},
				failure : function() {
					Ext.MessageBox.alert("提示", "校验方案名失败");
					return;
				}
			});
 	    }
    });
    var saveSchemeHideButton = new Ext.Button({ 
   		text:'退出',
 	    handler:function(){
 	        saveScheme.hide();	
 	    }
 	 });
    //修改方案按钮
    var updateSchemeShowButton = new Ext.Button({
    	text:'确定',
 	    handler:function(){
 	        if(!checkInitData()){
                return;
            }
            if(!checkUpdatePopWindowData()){
                return;
            }
	     	
	     	var schemeId= remoteSchemeComBox.getValue();
 	    	var schemeName = remoteSchemeComBox.getRawValue();
	     	if(schemeId==null|| schemeId=="") {
				Ext.MessageBox.alert("提示","请选择方案！");
	     	    return;
			}
			var alartDelayHours = alarmTime.getValue().trim();
			if ('' == alartDelayHours) {
				alartDelayHours = 0;
			}
			var remoteCtrlList = new Array();
			var num = 0;//记录remoteCtrlList的长度
			if (Ext.getCmp('ctrlGsselectAllcb').checked) {//全选选中
				for (var i = 0; i < userListStore.getCount(); i++) {
					if(userListStore.getAt(i).data.tmnlPaulPower !='是') {
						remoteCtrlList[num++] = userListStore.getAt(i).data.orgNo+'`'+userListStore.getAt(i).data.consNo+
							'`'+userListStore.getAt(i).data.tmnlAssetNo+'`'+userListStore.getAt(i).data.protocolCode;
					}
				}
			} else {
				users = userSm.getSelections();
                if(null == users || users.length<=0){//未选中任何用户，直接返回
  		     	    Ext.MessageBox.alert("提示","请选择用户！");
  		     	    return;
  		     	}
  		     	for(var i = 0; i < users.length; i++){
  		     		if(users[i].get('tmnlPaulPower') != '是') {
						remoteCtrlList[num++] = users[i].get('orgNo')+'`'+users[i].get('consNo')+'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('protocolCode');
  		     		}
                }
			}
			if(remoteCtrlList.length<=0){
				Ext.MessageBox.alert("提示","没有符合条件的用户！");
	     	    return;
			}
			Ext.Ajax.request({
				url : './baseapp/remoteControl!addSolution.action',
				params : {
					schemeId:schemeId,
					schemeName : schemeName,
					schemeRemark : remoteUpdateSchemeRemark.getValue(),
					newStartDate : newStartDate.getValue(),
					newEndDate : newEndDate.getValue(),
					remoteCtrlList:remoteCtrlList,
					limitMins : Ext.getCmp("rc_limitTime").getValue(),
					alertDelayHours : alartDelayHours
				},
				success : function() {
					Ext.MessageBox.alert("提示", "成功");
					viewPanel.getEl().unmask();
					return;
				},
				failure : function() {
					Ext.MessageBox.alert("提示", "失败");
					viewPanel.getEl().unmask();
					return;
				}
			});
			viewPanel.getEl().mask('保存中...');
			saveScheme.hide();
 	    }
    });
    var updateSchemeHideButton = new Ext.Button({
    	text:'退出',
 	    handler:function(){
 	        saveScheme.hide();	
 	    }
 	});
    
	//定义保存为方案面板组件
	var saveSchemeTopPnl=new Ext.Panel({
        height:220,
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
   	            items:[schemeLabel] 
   	        }, {
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            hideLabels:true,
   	            items:[schemeName] 
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
   	            items:[newStartDate] 
   	        }, {
   	            columnWidth:.45,
   	            layout:'form',
   	            border : false,
   	            labelWidth:10,
   	            labelAlign: "right",
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            items:[newEndDate] 
   	        }]
   	    },{
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 5px',
   	            labelWidth:70,
   	             labelAlign: "right",
   	            items:[remoteNewSchemeRemark]
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
   	            items:[saveSchemeShowButton] 
   	        }, {
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            hideLabels:true,
   	            items:[saveSchemeHideButton] 
   	        }]
   	    }]
    });
    
    //定义保存为方案面板组件
	var updateSchemeTopPnl=new Ext.Panel({
        height:220,
        border : false,
        layout:'form',
        buttonAlign:'center',
        items:[{
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 5px',
   	            labelAlign: "right",
   	            labelWidth:70,
   	            items:[remoteSchemeComBox] 
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
   	            items:[updateStartDate] 
   	        }, {
   	            columnWidth:.45,
   	            layout:'form',
   	            border : false,
   	            labelWidth:10,
   	            labelAlign: "right",
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            items:[updateEndDate] 
   	        }]
   	    },{
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 5px',
   	            labelWidth:70,
   	             labelAlign: "right",
   	            items:[remoteUpdateSchemeRemark]
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
   	            items:[updateSchemeShowButton] 
   	        }, {
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            hideLabels:true,
   	            items:[updateSchemeHideButton] 
   	        }]
   	    }]
    });
    
    //定义保存为方案面板组件
    var saveSchemePenl=new Ext.Panel({
        layout:'card',
        activeItem : 0,
        border:false,
        items:[saveSchemeTopPnl, updateSchemeTopPnl]
    });
    
    var rcSchemePenal = new Ext.Panel({
    	layout:'form',
    	border:false,
    	items:[rcRadioGroupPanel,saveSchemePenl]
    });
    
//	    定义保存为方案面板组件
    var rcSaveSchemePnl=new Ext.Panel({
        layout:'form',
        border:false,
        items:[rcSchemePenal]
    });
//    
//    //定义保存为方案面板组件
//    var saveSchemePnl=new Ext.Panel({
//        layout:'form',
//        border:false,
//        items:[rcRadioGroupPanel,saveSchemePenl]
//    });

    //保存方案跳出窗口
    var saveScheme = new Ext.Window({
        name:'remoteScheme',
        height:255,
        width:400,
        closeAction:'hide',
        resizable:false,
        layout:'form',	        
        title:'保存方案',
        items:[rcRadioGroupPanel,saveSchemePenl]
    }); 
	
	//保存为方案按钮
	var saveBtn = new Ext.Button({
			text : '保存为方案',
			name : '',
			width : 80,
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
  		     		},
  		     		failure : function(){
  		     		   Ext.MessageBox.alert("提示","请求失败");
  		     		   return;
  		     		}
  		     	});
  		     	rcRadioGroup.setValue("1");
  		        saveScheme.show();
			}
	});
	//--------------------------------方案保存----------------------------------end
	
	//处理拉合闸返回结果函数
	var remoteCtrlResult = function remoteCtrlResult(result,users){
		if (Ext.getCmp('ctrlGsselectAllcb').checked) {//如果选中全选按钮
			userListStore.each(function(rec){
				if(rec.get('execFlag') != '-9')
					rec.set('execFlag','-1');//将返回结果默认全设置成失败
	        	for(var j = 0; j < result.length; j++){//循环返回结果list 根据keyId匹配记录，设置返回值
	        		if(result[j].tmnlAssetNo==rec.get('tmnlAssetNo')) {
			        	if(result[j].execFlag !='-1'){
			        		rec.set('ctrlFlag',result[j].execFlag);
			        		rec.set('execFlag',result[j].execFlag);//返回结果匹配上再修改
	        			}
			        	break;
	        		}
	        	}
			});
			userListStore.commitChanges();//提交修改
			
			spliteUserListStore.proxy = new Ext.ux.data.PagingMemoryProxy(ctrlStoreToArray(userListStore));
			spliteUserListStore.load({
				params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
			});
		} else {
			for(var i = 0; i < users.length; i++){
				var rec = userListStore.getById(users[i].get('tmnlAssetNo'));
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
			userListStore.commitChanges();//提交修改
			spliteUserListStore.proxy = new Ext.ux.data.PagingMemoryProxy(ctrlStoreToArray(userListStore));
			spliteUserListStore.load({
				params : {
					start : spliteUserListStore.lastOptions.params.start,
					limit : DEFAULT_PAGE_SIZE
				}
			});
		}
	};
	
	//拉闸按钮
	var closeBtn = new Ext.Button({
			text : '遥控拉闸',
			name : '',
			width : 80,
			handler : function() {
				if(!checkInitData()){//校验参数，不通过校验直接返回
		            return;
		        }
		        var users = userSm.getSelections();
	            if(null == users || users.length<=0){//未选中任何用户，直接返回
		     	    Ext.MessageBox.alert("提示","请选择用户！");
		     	    return;
		     	}
				ctrlCheckStaff(closeCtrl,'ctrl');
			}
	});
	
	var closeCtrl = function closeCtrl(){
        var users = userSm.getSelections();
        var remoteCtrlList = new Array();
		var num = 0;//记录remoteCtrlList的长度
		if (Ext.getCmp('ctrlGsselectAllcb').checked) {//全选选中
			for (var i = 0; i < userListStore.getCount(); i++) {
				if(userListStore.getAt(i).data.tmnlPaulPower !='是') {
					remoteCtrlList[num++] = userListStore.getAt(i).data.orgNo+'`'+userListStore.getAt(i).data.consNo+
						'`'+userListStore.getAt(i).data.tmnlAssetNo+'`'+userListStore.getAt(i).data.protocolCode;
				}
			}
		} else {
	     	for(var i = 0; i < users.length; i++){
	     		if(users[i].get('tmnlPaulPower') != '是') {
					remoteCtrlList[num++] = users[i].get('orgNo')+'`'+users[i].get('consNo')+'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('protocolCode');
	     		}
            }
		}
		//alert(remoteCtrlList.length);
		if(remoteCtrlList.length <= 0) {
			if (Ext.getCmp('ctrlGsselectAllcb').checked) {//全选选中
				userListStore.each(function(rec){
					rec.set('execFlag','-9');//将返回结果默认全设置成失败
				});
			} else {
				for(var i = 0; i < users.length; i++){
					var rec = userListStore.getById(users[i].get('tmnlAssetNo'));
                 	users[i].set('execFlag', '-9');
				}
			}
			userListStore.commitChanges();
			return;
		}
		//取得控制轮次数组
        var roundArray  = roundCheck.getValue();
        if(roundArray.length<=0) {
     		Ext.MessageBox.alert("提示","请选择轮次！");
     		return;
     	}
        var round = roundArray[0].getRawValue();
        for(var i = 1; i < roundArray.length; i++){
        	round =round +','+roundArray[i].getRawValue();
        }
        //取得告警时间
        var alartDelayHours = alarmTime.getValue().trim();
        if('' == alartDelayHours){
        	alartDelayHours = 0;
        }
        var ov = true;
        var second = Ext.Ajax.timeout/1000;
        h_taskTime(second,function(){
        	ov = false;
        });
    	Ext.Ajax.request({
     		url:'./baseapp/remoteControl!close.action',
     		params : {
     			second:second,
     			limitMins:Ext.getCmp("rc_limitTime").getValue(),
     			alertDelayHours:alartDelayHours,
     			turnFlag:round,
     			remoteCtrlList:remoteCtrlList
     		},
     		success : function(response){
     			var cacheAndTmnlStatus = Ext.decode(response.responseText).cacheAndTmnlStatus;
     			//与前置通信成功与否校验
     			if(cacheAndTmnlStatus!=""&& cacheAndTmnlStatus!=null){
     				Ext.MessageBox.alert("提示",cacheAndTmnlStatus);
     				return;
     			}
     			if(!ov) return;
     			var result = Ext.decode(response.responseText).resultList;
     			var data = new Array();//选中用户记录
     			
     			if(result==null ||result.length==0){
     				Ext.MessageBox.alert("提示","拉闸失败，全部终端无应答！");
     		   		return;
     			}
     			remoteCtrlResult(result, users);//调用处理返回结果函数
     			
     		    overFlat = true;
     		    Ext.Ajax.timeout = 30000;
     		    return;
     		},
     		failure : function() {
     		    Ext.MessageBox.alert("提示","拉闸失败");
     		    if(!ov) return ;
	     		overFlat = true;
     		    return;
     		}
     	});
	};
	
	//合闸按钮
	var openBtn = new Ext.Button({
			text : '允许合闸',
			name : '',
			width : 80,
			handler : function() {
				var users = userSm.getSelections();
	            if(null == users || users.length<=0){//未选中任何用户，直接返回
		     	    Ext.MessageBox.alert("提示","请选择用户！");
		     	    return;
		     	}
				var roundArray  = roundCheck.getValue();
		     	if(roundArray.length<=0) {
		     		Ext.MessageBox.alert("提示","请选择轮次！");
		     		return;
		     	}
				ctrlCheckStaff(openCtrl, 'ctrl');
			}
	});
	
	var openCtrl = function openCtrl(){
		var users = userSm.getSelections();
        var remoteCtrlList = new Array();
		var num = 0;//记录remoteCtrlList的长度
		if (Ext.getCmp('ctrlGsselectAllcb').checked) {//全选选中
			for (var i = 0; i < userListStore.getCount(); i++) {
				if(userListStore.getAt(i).data.tmnlPaulPower !='是') {
					remoteCtrlList[num++] = userListStore.getAt(i).data.orgNo+'`'+userListStore.getAt(i).data.consNo+
						'`'+userListStore.getAt(i).data.tmnlAssetNo+'`'+userListStore.getAt(i).data.protocolCode;
				}
			}
		} else {
	     	for(var i = 0; i < users.length; i++){
	     		if(users[i].get('tmnlPaulPower') != '是') {
					remoteCtrlList[num++] = users[i].get('orgNo')+'`'+users[i].get('consNo')+'`'+users[i].get('tmnlAssetNo')+'`'+users[i].get('protocolCode');
	     		}
            }
		}
		if(remoteCtrlList.length <= 0) {
			if (Ext.getCmp('ctrlGsselectAllcb').checked) {//全选选中
				userListStore.each(function(rec){
					rec.set('execFlag','-9');//将返回结果默认全设置成失败
				});
			} else {
				for(var i = 0; i < users.length; i++){
					var rec = userListStore.getById(users[i].get('tmnlAssetNo'));
                 	users[i].set('execFlag', '-9');
				}
			}
			userListStore.commitChanges();
			return;
		}
     	//获取控制轮次
     	var roundArray  = roundCheck.getValue();
     	if(roundArray.length<=0) {
     		Ext.MessageBox.alert("提示","请选择轮次！");
     		return;
     	}
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
        if(remoteCtrlList.length ==0) {
        	return;
        }
    	Ext.Ajax.request({
     		url:'./baseapp/remoteControl!open.action',
     		params : {
     			second:second,
     			remoteCtrlList:remoteCtrlList,
     			turnFlag:round
     		},
     		success : function(response){
     			var cacheAndTmnlStatus = Ext.decode(response.responseText).cacheAndTmnlStatus;
     			if(cacheAndTmnlStatus!=""&& cacheAndTmnlStatus!=null){
     				Ext.MessageBox.alert("提示",cacheAndTmnlStatus);
     				return;
     			}
     			if(!ov) return;
     			var result = Ext.decode(response.responseText).resultList;
     			if(result==null ||result.length==0){
     				Ext.MessageBox.alert("提示","合闸失败，全部终端无应答！");
     		   		return;
     			}
     			remoteCtrlResult(result, users);//调用处理返回结果函数
     		    overFlat = true;
     		    Ext.Ajax.timeout = 30000;
     		    return;
     		},
     		failure : function(){
     		    Ext.MessageBox.alert("提示","合闸失败");
     		    if(!ov) return ;
	     		overFlat = true;
     		    return;
     		}
     	});
	};
	//定义遥控的上部面板
	var remoteCtrlTopPnl = new Ext.Panel({
		id:'remoteCtrlTopPnl',
		region:'north',
	    border:false,
	    height:80,
	    layout:'form',
	    labelSeparator:'',
	    labelAlign:'right',
	    items:[
	    	{
	    	     layout:'form',
	             labelSeparator:'',
	             border:false,
	             bodyStyle:'padding:10px 0px 0px 0px',
	             items:[roundCheck]
	        }, 
	        	{
	    	 	labelSeparator:'',
				layout : 'column',
				border:false,
				items:[{
					columnWidth : .25,
				    layout : 'form',
				    border : false,
				    labelWidth : 100,
				    labelAlign : "right",
				    labelSeparator:'',
				    bodyStyle:'padding:10px 0px 0px 33px',
					items : [alarmTime]
				},	{
					columnWidth : .22,
				    layout : 'form',
				    border : false,
				    labelWidth : 100,
				    bodyStyle:'padding:10px 0px 0px 10px',
				    labelAlign : "right",
					items : [{
					    xtype: 'spinnerfield',
					    id:'rc_limitTime',
					    labelSeparator:'',
        	            fieldLabel: '限电时间(半小时)',
        	            width:55,
        	            value:0,
        	            name: '',
        	            emptyText:'1-15之间',
        	            minValue: 0,
        	            maxValue: 15,
        	            allowDecimals: true,
        	            decimalPrecision: 1,
        	            incrementValue: 1,
        	            accelerate: true
				    }]
	            },{columnWidth : .53,
	            	bodyStyle:'padding:10px 0px 0px 20px',
				    layout : 'column',
				    border : false,
				    items :[topRadio]
			   }]
	    }]
    });
	
	//-----------------------------遥测电压电流用户列表-------------------------start
	var uiUserSm = new Ext.grid.CheckboxSelectionModel();
	var uiRowNumber = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(spliteUiUserListStore && spliteUiUserListStore.lastOptions && spliteUiUserListStore.lastOptions.params){
				startRow = spliteUiUserListStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	
	var uiCm = new Ext.grid.ColumnModel([uiRowNumber, uiUserSm, 
			//{header :'主键',sortable:true, dataIndex:'keyId',align:'center',hidden:true},
	        //header :'供电单位编号',sortable:true, dataIndex:'orgNo',align:'center',hidden:true},
		    {header : '供电单位', sortable: true, dataIndex : 'orgName', renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			},align : 'center', defaultWidth:140}, 
		    {header : '用户编号', sortable: true, dataIndex : 'consNo', align : 'center'}, 
		    {header : '用户名称', sortable: true, dataIndex : 'consName', renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			},align : 'center'}, 
//		    {header : '终端资产号', sortable:true,  dataIndex:'tmnlAssetNo',align:'center'},
		    {header : '测量点号', sortable:true,  dataIndex:'mpSn',align:'center'},
		    {header : '互感器电流倍率', sortable:true,  dataIndex:'ct',align:'center',hidden:true},
		    {header : '互感器电压倍率', sortable:true,  dataIndex:'pt',align:'center',hidden:true},
		    //{header : '规约编码', sortable:true,  dataIndex:'protocolCode',align:'center',hidden:true},
		    {header : '终端地址', sortable: true, dataIndex : 'terminalAddr', renderer : function (val) {
						 return "<div align = 'left'>"+ val + "</div>";
						 },align : 'center'},
		    {header : '实时工况', sortable: true, dataIndex : 'realTimeStatus', renderer:function(value){
				   		if(value){
				   			return "<font color='green';font-weight:bold>在线</font>";
				   		}else if(!value&&value!=null){
				   			return "<font color='red';font-weight:bold>离线</font>";
				   		}else 
				   		    return "";
				},align : 'center'}, 
//		    {header : '保电', sortable: true, dataIndex : 'tmnlPaulPower', align : 'center'},
//		    {header : '执行状态', sortable: true, dataIndex : 'ctrlFlag', renderer:function(value){
//				   		if(value){
//				   			return "<font color='green';font-weight:bold>成功</font>";
//				   		}else if(!value&&value!=null){
//				   			return "<font color='red';font-weight:bold>失败</font>";
//				   		}else 
//				   		    return "";
//				}, align : 'center'},
		    {header : '报文', sortable: true, dataIndex : 'message',renderer: function(s, m, rec){
					return "<a href='javascript:'onclick='origFrameQryShow(\""+rec.get('consNo')+"\",\""+ rec.get('consName')+"\",\""+ rec.get('terminalAddr')+"\");" + "'>查看</a>"; 
				}, align : 'center'}
		]);
		
	//定义Grid的store
	var uiUserListStore = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
					url:'./baseapp/remoteControl!loadUiGridData.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'uiUserGridList',
					idProperty: 'keyId'
				}, [{name:'keyId'},
				   {name : 'orgNo'},
				   {name : 'orgName'}, 
				   {name : 'consNo'}, 
				   {name : 'consName'}, 
				   {name : 'tmnlAssetNo'}, 
				   {name : 'mpSn'}, 
				   {name:'ct'},
				   {name:'pt'},
				   {name : 'terminalAddr'}, 
				   {name : 'realTimeStatus'},
				   {name:'protocolCode'},
				   {name : 'tmnlPaulPower'}, 
				   {name : 'ctrlFlag'},
				   {name : 'message'}
				   ])
		});
	
	// grid解锁
	function unlockGrid(tab) {//取消全选时，解锁Grid
		if(tab=="ui") {//电压电流Grid
			uiUserSm.unlock();
			uiUserListGrid.onEnable();
			uiUserListGrid.getBottomToolbar().enable();
			Ext.getCmp('uiGsselectAllcb').setValue(false);
		} else if (tab =="power"){//功率Grid
			powerUserSm.unlock();
			powerUserListGrid.onEnable();
			powerUserListGrid.getBottomToolbar().enable();
			Ext.getCmp('powerGsselectAllcb').setValue(false);
		} else if (tab =="ctrl"){//拉合闸Grid
			userSm.unlock();
			userListGrid.onEnable();
			userListGrid.getBottomToolbar().enable();
			Ext.getCmp('ctrlGsselectAllcb').setValue(false);
		}
	}

	// grid锁定
	function lockGrid(tab) {
		if(tab=="ui") {
			uiUserSm.lock();
			uiUserListGrid.onDisable();
			uiUserListGrid.getBottomToolbar().disable();
		} else if (tab =="power"){
			powerUserSm.lock();
			powerUserListGrid.onDisable();
			powerUserListGrid.getBottomToolbar().disable();
		} else if (tab =="ctrl"){
			userSm.lock();
			userListGrid.onDisable();
			userListGrid.getBottomToolbar().disable();
		}
	}	
		
	function uiStoreToArray(valStore) {
		var data = new Array();
		for (var i = 0; i < valStore.getCount(); i++) {
			data[i] = new Array();
			var index = 0;
			data[i][index++] = valStore.getAt(i).data.keyId;
 			data[i][index++] = valStore.getAt(i).data.orgNo;
			data[i][index++] = valStore.getAt(i).data.orgName;
			data[i][index++] = valStore.getAt(i).data.consNo;
			data[i][index++] = valStore.getAt(i).data.consName;
			data[i][index++] = valStore.getAt(i).data.tmnlAssetNo;
			data[i][index++] = valStore.getAt(i).data.mpSn;
			data[i][index++] = valStore.getAt(i).data.ct;
			data[i][index++] = valStore.getAt(i).data.pt;
			data[i][index++] = valStore.getAt(i).data.terminalAddr;
			data[i][index++] = valStore.getAt(i).data.realTimeStatus;
			data[i][index++] = valStore.getAt(i).data.protocolCode;
			data[i][index++] = valStore.getAt(i).data.tmnlPaulPower;
			data[i][index++] = valStore.getAt(i).data.ctrlFlag;
			data[i][index++] = valStore.getAt(i).data.message;
		}
		return data;
	}
		
	var spliteUiUserListStore = new Ext.data.Store({
		remoteSort : true,
		proxy : new Ext.data.MemoryProxy(),
		reader : new Ext.data.ArrayReader({
			idIndex : 0,
			fields :  [{name:'keyId'},
				   {name : 'orgNo'},
				   {name : 'orgName'}, 
				   {name : 'consNo'}, 
				   {name : 'consName'}, 
				   {name : 'tmnlAssetNo'}, 
				   {name : 'mpSn'}, 
				   {name:'ct'},
				   {name:'pt'},
				   {name : 'terminalAddr'}, 
				   {name : 'realTimeStatus'},
				   {name:'protocolCode'},
				   {name : 'tmnlPaulPower'}, 
				   {name : 'ctrlFlag'},
				   {name : 'message'}]
			})
		});
	
	//返回遥控的数据面板
    var uiUserListGrid = new Ext.grid.GridPanel({
	        store : spliteUiUserListStore,
	        cm : uiCm,
	        sm : uiUserSm,
	        stripeRows : true,
	        loadMask:true,
	        border: false,
	        tbar : [pcBtn, {xtype: 'tbfill'},{
					xtype : 'checkbox',
					id : 'uiGsselectAllcb',
					boxLabel : '全选',
					name : 'gsselectAllcb',
					checked : false,
					listeners : {
						'check' : function(r, c) {
							if (c) {
								uiUserSm.selectAll();
								lockGrid('ui');
							}else {
								unlockGrid('ui');
								uiUserSm.clearSelections();
							}
						}
					}
				}, {
				    text : '删除选中用户',
			        iconCls : 'minus',
			        handler : function() {
						if (Ext.getCmp('uiGsselectAllcb').checked) {
							uiUserListStore.removeAll(true);
							unlockGrid('ui');
							Ext.getCmp('uiGsselectAllcb').setValue(false);
						} else {
							var recs = uiUserSm.getSelections();
							for (var i = 0; i < recs.length; i = i + 1) {
								uiUserListStore.remove(uiUserListStore
										.getById(recs[i].data.keyId));
							}
						}
						spliteUiUserListStore.proxy = new Ext.ux.data.PagingMemoryProxy(uiStoreToArray(uiUserListStore));
						spliteUiUserListStore.load({
									params : {
										start : 0,
										limit : DEFAULT_PAGE_SIZE
									}
						});
				}
				}, {
					icon : '',
					text : '加入群组',
					iconCls : 'plus',
					handler:function(){
						var groupTmnlArray = new Array();
						if (Ext.getCmp('uiGsselectAllcb').checked) {
							for (var i = 0; i < uiUserListStore.getCount(); i++) {
								var tmnl = uiUserListStore.getAt(i).get('consNo')
										+ '`'+ uiUserListStore.getAt(i).get('tmnlAssetNo');
								groupTmnlArray[i] = tmnl;
							}
						} else {
							var recs = uiUserSm.getSelections();
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
							if (Ext.getCmp('uiGsselectAllcb').checked) {
								Ext.getCmp('uiGsselectAllcb').setValue(false);
								uiUserSm.selectAll();
							}
						}
	                 }
				}
//				, {
//					icon : 'minus',
//					iconCls : 'minus',
//					text : '删除成功用户',
//					handler:function(){
//			     		for (var i = floatStore.getCount()-1; i >=0; i--) {
//			     			var execFlag = floatStore.getAt(i).get('execFlag');
//			     			if("2"==execFlag || "1"==execFlag ||"0"==execFlag)
//								floatStore.removeAt(i);
//						}
//						splite_floatStore.proxy = new Ext.ux.data.PagingMemoryProxy(floatStoreToArray(floatStore));
//						splite_floatStore.load({
//									params : {
//										start : 0,
//										limit : DEFAULT_PAGE_SIZE
//									}
//						});
//						if(Ext.getCmp('floatGsselectAllcb').checked) {
//							Ext.getCmp('floatGsselectAllcb').setValue(false);
//							floatSM.selectAll();
//						}
//					 }
//				}
//				, {
//					icon : '',
//					iconCls : 'plus',
//					text : '短信发送',
//					handler : ''
//				}
				],
		bbar : new Ext.ux.MyToolbar({
				store : spliteUiUserListStore,
				allStore : uiUserListStore,
				enableExpAll : true, // excel导出全部数据
				expAllText : "全部",
				enableExpPage : true, // excel仅导出当前页
				expPageText : "当前页"
		})		
    });
		    
  //定义遥控的数据面板
    var uiUserListGridPnl = new Ext.Panel({
		layout:'fit',
	    id : 'uiUserListGridPnl',
	    border : false,
	    title : '遥测电压电流',
		items : [uiUserListGrid]
	});
	
	//返回遥控的数据面板页面组件
	function getUiUserGridPanel(){
		return Ext.getCmp('uiUserListGridPnl');
	}
	//-----------------------------遥测电压电流用户列表-------------------------end
	//-----------------------------遥测功率用户列表-------------------------start
	var powerUserSm = new Ext.grid.CheckboxSelectionModel();
	var powerRowNumber = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(splitePowerUserListStore && splitePowerUserListStore.lastOptions && splitePowerUserListStore.lastOptions.params){
				startRow = splitePowerUserListStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	var powerCm = new Ext.grid.ColumnModel([powerRowNumber,powerUserSm, 
			//{header :'主键',sortable:true, dataIndex:'keyId',align:'center',hidden:true},
	        //{header :'供电单位编号',sortable:true, dataIndex:'orgNo',align:'center',hidden:true},
		    {header : '供电单位', sortable: true, dataIndex : 'orgName', renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			},align : 'center', defaultWidth:140}, 
		    {header : '用户编号', sortable: true, dataIndex : 'consNo', align : 'center'}, 
		    {header : '用户名称', sortable: true, dataIndex : 'consName', renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			},align : 'center'}, 
//		    {header : '终端资产号', sortable:true,  dataIndex:'tmnlAssetNo',align:'center'},
		    {header : '总加组号', sortable:true,  dataIndex:'totalNo',align:'center'},
		    //{header : '规约编码', sortable:true,  dataIndex:'protocolCode',align:'center',hidden:true},
		    {header : '运行容量', sortable:true,  dataIndex:'runCap',align:'center',hidden:true},
		    {header : '终端地址', sortable: true, dataIndex : 'terminalAddr', renderer : function (val) {
						 return "<div align = 'left'>"+ val + "</div>";
						 },align : 'center'},
		    {header : '实时工况', sortable: true, dataIndex : 'realTimeStatus', renderer:function(value){
				   		if(value){
				   			return "<font color='green';font-weight:bold>在线</font>";
				   		}else if(!value&&value!=null){
				   			return "<font color='red';font-weight:bold>离线</font>";
				   		}else 
				   		    return "";
				}, align : 'center'}, 
//		    {header : '保电', sortable: true, dataIndex : 'tmnlPaulPower', align : 'center'},
//		    {header : '执行状态', sortable: true, dataIndex : 'ctrlFlag', renderer:function(value){
//				   		if(value){
//				   			return "<font color='green';font-weight:bold>成功</font>";
//				   		}else if(!value&&value!=null){
//				   			return "<font color='red';font-weight:bold>失败</font>";
//				   		}else 
//				   		    return "";
//				}, align : 'center'},
		    {header : '报文', sortable: true, dataIndex : 'message',renderer: function(s, m, rec){
					return "<a href='javascript:'onclick='origFrameQryShow(\""+rec.get('consNo')+"\",\""+ rec.get('consName')+"\",\""+ rec.get('terminalAddr')+"\");" + "'>查看</a>"; 
				}, align : 'center'}
		]);
		
	//定义Grid的store
	var powerUserListStore = new Ext.data.Store({
	 	        proxy : new Ext.data.HttpProxy({
							url:'./baseapp/remoteControl!loadPowerGridData.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'pwrUserGridList',
							idProperty: 'keyId'
						}, [{name:'keyId'},
						   {name : 'orgNo'},
						   {name : 'orgName'}, 
						   {name : 'consNo'}, 
						   {name : 'consName'}, 
						   {name : 'tmnlAssetNo'}, 
						   {name : 'totalNo'},  
						   {name:'runCap'},
						   {name : 'terminalAddr'}, 
						   {name : 'realTimeStatus'},
						   {name : 'protocolCode'},
						   {name : 'tmnlPaulPower'}, 
						   {name : 'ctrlFlag'},
						   {name : 'message'}
						   ])
		});
		
	function powerStoreToArray(valStore) {
		var data = new Array();
		for (var i = 0; i < valStore.getCount(); i++) {
			data[i] = new Array();
			var index = 0;
			data[i][index++] = valStore.getAt(i).data.keyId;
 			data[i][index++] = valStore.getAt(i).data.orgNo;
			data[i][index++] = valStore.getAt(i).data.orgName;
			data[i][index++] = valStore.getAt(i).data.consNo;
			data[i][index++] = valStore.getAt(i).data.consName;
			data[i][index++] = valStore.getAt(i).data.tmnlAssetNo;
			data[i][index++] = valStore.getAt(i).data.totalNo;
			data[i][index++] = valStore.getAt(i).data.runCap;
			data[i][index++] = valStore.getAt(i).data.terminalAddr;
			data[i][index++] = valStore.getAt(i).data.realTimeStatus;
			data[i][index++] = valStore.getAt(i).data.protocolCode;
			data[i][index++] = valStore.getAt(i).data.tmnlPaulPower;
			data[i][index++] = valStore.getAt(i).data.ctrlFlag;
			data[i][index++] = valStore.getAt(i).data.message;
		}
		return data;
	}
		
	var splitePowerUserListStore = new Ext.data.Store({
		remoteSort : true,
		proxy : new Ext.data.MemoryProxy(),
		reader : new Ext.data.ArrayReader({
					idIndex : 0,
				fields :  [{name:'keyId'},
						   {name : 'orgNo'},
						   {name : 'orgName'}, 
						   {name : 'consNo'}, 
						   {name : 'consName'}, 
						   {name : 'tmnlAssetNo'}, 
						   {name : 'totalNo'},  
						   {name:'runCap'},
						   {name : 'terminalAddr'}, 
						   {name : 'realTimeStatus'},
						   {name : 'protocolCode'},
						   {name : 'tmnlPaulPower'}, 
						   {name : 'ctrlFlag'},
						   {name : 'message'}
				   ]})
		});
	
	//返回遥控的数据面板
    var powerUserListGrid = new Ext.grid.GridPanel({
	        store : splitePowerUserListStore,
	        cm : powerCm,
	        sm : powerUserSm,
	        stripeRows : true,
	        loadMask:true,
	        border: false,
	        tbar : [powerBtn, {xtype: 'tbfill'}, {
					xtype : 'checkbox',
					id : 'powerGsselectAllcb',
					boxLabel : '全选',
					name : 'gsselectAllcb',
					checked : false,
					listeners : {
						'check' : function(r, c) {
							if (c) {
								powerUserSm.selectAll();
								lockGrid('power');
							}else {
								unlockGrid('power');
								powerUserSm.clearSelections();
							}
						}
					}
				},{
				    text : '删除选中用户',
			        iconCls : 'minus',
			        handler : function() {
						if (Ext.getCmp('powerGsselectAllcb').checked) {
							powerUserListStore.removeAll(true);
							unlockGrid('power');
							Ext.getCmp('powerGsselectAllcb').setValue(false);
						} else {
							var recs = powerUserSm.getSelections();
							for (var i = 0; i < recs.length; i = i + 1) {
								powerUserListStore.remove(powerUserListStore
										.getById(recs[i].data.keyId));
							}
						}
						splitePowerUserListStore.proxy = new Ext.ux.data.PagingMemoryProxy(powerStoreToArray(powerUserListStore));
						splitePowerUserListStore.load({
									params : {
										start : 0,
										limit : DEFAULT_PAGE_SIZE
									}
						});
				}
				}, {
					icon : '',
					text : '加入群组',
					iconCls : 'plus',
					handler : function(){
						var groupTmnlArray = new Array();
						if (Ext.getCmp('powerGsselectAllcb').checked) {
							for (var i = 0; i < powerUserListStore.getCount(); i++) {
								var tmnl = powerUserListStore.getAt(i).get('consNo')
										+ '`'+ powerUserListStore.getAt(i).get('tmnlAssetNo');
								groupTmnlArray[i] = tmnl;
							}
						} else {
							var recs = powerUserSm.getSelections();
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
							if (Ext.getCmp('powerGsselectAllcb').checked) {
								Ext.getCmp('powerGsselectAllcb').setValue(false);
								powerUserSm.selectAll();
							}
						}
	                 }
				}
//				, {
//					icon : 'minus',
//					iconCls : 'minus',
//					text : '删除成功用户',
//					handler : ''
//				}
//				, {
//					icon : '',
//					iconCls : 'plus',
//					text : '短信发送',
//					handler : ''
//				}
				],
		bbar : new Ext.ux.MyToolbar( {
				store : splitePowerUserListStore,
				allStore : powerUserListStore,
				enableExpAll : true, // excel导出全部数据
				expAllText : "全部",
				enableExpPage : true, // excel仅导出当前页
				expPageText : "当前页"
		})	
    });
		    
  //定义遥控的数据面板
    var powerUserListGridPnl = new Ext.Panel({
		layout:'fit',
	    id : 'powerUserListGridPnl',
	    border : false,
	    hidden :true,
	    title : '遥测功率',
		items : [powerUserListGrid]
	});
	
	//返回遥控的数据面板页面组件
	function getPowerUserGridPanel(){
		return Ext.getCmp('powerUserListGridPnl');
	}
	//-----------------------------遥测功率用户列表-------------------------end
	
	//-----------------------------遥测开关拉合闸用户列表-------------------------start
	var userSm = new Ext.grid.CheckboxSelectionModel();
	var rowNumber = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(spliteUserListStore && spliteUserListStore.lastOptions && spliteUserListStore.lastOptions.params){
				startRow = spliteUserListStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	
	var cm = new Ext.grid.ColumnModel([rowNumber, userSm, 
	        //{header :'供电单位编号',sortable:true, dataIndex:'orgNo',align:'center',hidden:true},
		    {header : '供电单位', sortable: true, dataIndex : 'orgName', renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			},align : 'center', defaultWidth:140}, 
		    {header : '用户编号', sortable: true, dataIndex : 'consNo', align : 'center'}, 
		    {header : '用户名称', sortable: true, dataIndex : 'consName', renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			},align : 'center'}, 
//		    {header : '终端资产号', sortable:true,  dataIndex:'tmnlAssetNo',align:'center',hidden:true},
		    //{header : '规约编码', sortable:true,  dataIndex:'protocolCode',align:'center',hidden:true},
		    {header : '告警时间', sortable:true,  dataIndex:'alertDelayHours',align:'center',hidden:true},
		    {header : '限电时间', sortable:true,  dataIndex:'limitMins',align:'center',hidden:true},
		    {header : '终端地址', sortable: true, dataIndex : 'terminalAddr', renderer : function (val) {
						 return "<div align = 'left'>"+ val + "</div>";
						 },align : 'center'},
		    {header : '实时工况', sortable: true, dataIndex : 'realTimeStatus', renderer:function(value){
				   		if(value){
				   			return "<font color='green';font-weight:bold>在线</font>";
				   		}else if(!value&&value!=null){
				   			return "<font color='red';font-weight:bold>离线</font>";
				   		}else 
				   		    return "";
				}, align : 'center'}, 
		    {header : '保电', sortable: true, dataIndex : 'tmnlPaulPower', align : 'center'},
		      {header : '控制状态', sortable: true, dataIndex : 'ctrlFlag', renderer:function(value){
		   			if(value=="1"){
			   			return "<font color='red';font-weight:bold>开</font>";
			   		}else if(value=="0") {
			   			return "<font color='green';font-weight:bold>合</font>";
			   		} else {
			   			return "";
			   		}
				}, align : 'center'},
		    {header : '执行状态', sortable: true, dataIndex : 'execFlag', renderer:function(value){
				   		if(value=="1" || value =="0"){
				   			return "<font color='green';font-weight:bold>成功</font>";
				   		}else if(value=="-1"){
				   			return "<font color='red';font-weight:bold>失败</font>";
				   		}else if(value=="-9"){
				   		    return "未执行";
				   		} else {
				   			return "";
				   		}
				}, align : 'center'},
		    {header : '报文', sortable: true, dataIndex : 'message',renderer: function(s, m, rec){
					return "<a href='javascript:'onclick='origFrameQryShow(\""+rec.get('consNo')+"\",\""+ rec.get('consName')+"\",\""+ rec.get('terminalAddr')+"\");" + "'>查看</a>"; 
				}, align : 'center'}
		]);
		
	//定义Grid的store
	var userListStore = new Ext.data.Store({//数据store，存储所有数据
        proxy : new Ext.data.HttpProxy({
					url:'./baseapp/remoteControl!loadGridData.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'userGridList',
					idProperty: 'tmnlAssetNo'
				}, [
				   {name : 'orgNo'},
				   {name : 'orgName'}, 
				   {name : 'consNo'}, 
				   {name : 'consName'}, 
				   {name : 'tmnlAssetNo'}, 
				   {name : 'terminalAddr'}, 
				   {name : 'realTimeStatus'},
				   {name:'protocolCode'},
				   {name:'alertDelayHours'},
		   		   {name:'limitMins'},
				   {name : 'tmnlPaulPower'}, 
				   {name : 'ctrlFlag'},
				   {name : 'execFlag'},
				   {name : 'message'}
				   ])
		});
		
	function ctrlStoreToArray(valStore) {//将userListStore的数据转化为
		var data = new Array();
		for (var i = 0; i < valStore.getCount(); i++) {
			data[i] = new Array();
 			data[i][0] = valStore.getAt(i).data.orgNo;
			data[i][1] = valStore.getAt(i).data.orgName;
			data[i][2] = valStore.getAt(i).data.consNo;
			data[i][3] = valStore.getAt(i).data.consName;
			data[i][4] = valStore.getAt(i).data.tmnlAssetNo;
			data[i][5] = valStore.getAt(i).data.terminalAddr;
			data[i][6] = valStore.getAt(i).data.realTimeStatus;
			data[i][7] = valStore.getAt(i).data.protocolCode;
			data[i][8] = valStore.getAt(i).data.alertDelayHours;
			data[i][9] = valStore.getAt(i).data.limitMins;
			data[i][10] = valStore.getAt(i).data.tmnlPaulPower;
			data[i][11] = valStore.getAt(i).data.ctrlFlag;
			data[i][12] = valStore.getAt(i).data.execFlag;
			data[i][13] = valStore.getAt(i).data.message;
		}
		return data;
	}	
		
	var spliteUserListStore = new Ext.data.Store({
		remoteSort : true,
		proxy : new Ext.data.MemoryProxy(),
		reader : new Ext.data.ArrayReader({
					idIndex : 4,
					fields :[
				   {name : 'orgNo'},
				   {name : 'orgName'}, 
				   {name : 'consNo'}, 
				   {name : 'consName'}, 
				   {name : 'tmnlAssetNo'}, 
				   {name : 'terminalAddr'}, 
				   {name : 'realTimeStatus'},
				   {name:'protocolCode'},
				   {name:'alertDelayHours'},
		   		   {name:'limitMins'},
				   {name : 'tmnlPaulPower'}, 
				   {name : 'ctrlFlag'},
				   {name : 'execFlag'},
				   {name : 'message'}]
			})
		});
	
	//返回遥控的数据面板
    var userListGrid = new Ext.grid.GridPanel({
	        store : spliteUserListStore,
	        cm : cm,
	        sm : userSm,
	        loadMask:true,
	        stripeRows : true,
	        border: false,
	        tbar : [loadBtn, saveBtn, switchBtn, closeBtn, openBtn,{xtype: 'tbfill'},{
					xtype : 'checkbox',
					id : 'ctrlGsselectAllcb',
					boxLabel : '全选',
					name : 'gsselectAllcb',
					checked : false,
					listeners : {
						'check' : function(r, c) {
							if (c) {
								userSm.selectAll();
								lockGrid('ctrl');
							}else {
								unlockGrid('ctrl');
								userSm.clearSelections();
							}
						}
					}
				}, {
				    text : '删除选中用户',
			        iconCls : 'minus',
			        handler : function() {
						if (Ext.getCmp('ctrlGsselectAllcb').checked) {
							userListStore.removeAll(true);
							unlockGrid('ctrl');
							Ext.getCmp('ctrlGsselectAllcb').setValue(false);
						} else {
							var recs = userSm.getSelections();
							for (var i = 0; i < recs.length; i = i + 1) {
								userListStore.remove(userListStore.getById(recs[i].data.tmnlAssetNo));
							}
						}
						spliteUserListStore.proxy = new Ext.ux.data.PagingMemoryProxy(ctrlStoreToArray(userListStore));
						spliteUserListStore.load({
									params : {
										start : 0,
										limit : DEFAULT_PAGE_SIZE
									}
						});
				}
				}, {
					icon : '',
					text : '加入群组',
					iconCls : 'plus',
					handler : function(){
						var groupTmnlArray = new Array();
						if (Ext.getCmp('ctrlGsselectAllcb').checked) {
							for (var i = 0; i < userListStore.getCount(); i++) {
								var tmnl = userListStore.getAt(i).get('consNo')
										+ '`'+ userListStore.getAt(i).get('tmnlAssetNo');
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
							if (Ext.getCmp('ctrlGsselectAllcb').checked) {
								Ext.getCmp('ctrlGsselectAllcb').setValue(false);
								userSm.selectAll();
							}
						}
	                 }
				}, {
					iconCls : 'minus',
					text : '删除成功用户',
					handler:function(){
			     		for (var i = userListStore.getCount()-1; i >=0; i--) {
			     			var execFlag = userListStore.getAt(i).get('execFlag');
			     			if("2"==execFlag || "1"==execFlag ||"0"==execFlag)
								userListStore.removeAt(i);
						}
						spliteUserListStore.proxy = new Ext.ux.data.PagingMemoryProxy(ctrlStoreToArray(userListStore));
						spliteUserListStore.load({
									params : {
										start : 0,
										limit : DEFAULT_PAGE_SIZE
									}
						});
						if(Ext.getCmp('ctrlGsselectAllcb').checked) {
							Ext.getCmp('ctrlGsselectAllcb').setValue(false);
							userSm.selectAll();
						}
					 }
				}
//				, {
//					icon : '',
//					iconCls : 'plus',
//					text : '短信发送',
//					handler : ''
//				}
				],
		bbar : new Ext.ux.MyToolbar( {
				store : spliteUserListStore,
				allStore : userListStore,
				enableExpAll : true, // excel导出全部数据
				expAllText : "全部",
				enableExpPage : true, // excel仅导出当前页
				expPageText : "当前页"
		})
    });
		    
  //定义遥控的数据面板
    var userListGridPnl = new Ext.Panel({
		layout:'fit',
	    id : 'userListGridPnl',
	    border : false,
	    hidden :true,
	    title : '遥测开关拉合闸',
		items : [userListGrid]
	});
	
	//返回遥控的数据面板页面组件
	function getCtrlUserGridPanel(){
		return Ext.getCmp('userListGridPnl');
	}
	//-----------------------------遥测开关拉合闸用户列表-------------------------end
	
	//-----------------------------遥测电压电流的遥测结果----------------start
	//返回遥测电压电流的遥测结果数据面板
//	 var rt_pcsm = new Ext.grid.CheckboxSelectionModel();
	 
	 var rt_pccm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
//	 rt_pcsm, 
	 		//{header :'主键',sortable:true, dataIndex:'keyId',align:'center',hidden:true},
		    {header : '供电单位', sortable: true, dataIndex : 'orgName', renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			},align : 'center',defaultWidth:140}, 
		    {header : '用户编号', sortable: true, dataIndex : 'consNo', align : 'center'}, 
		    {header : '用户名称', sortable: true, dataIndex : 'consName', renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			}, align : 'center'}, 
		    {header : '测量点号', sortable:true,  dataIndex:'mpSn',align:'center'},
		    {header : '互感器电流倍率', sortable:true,  dataIndex:'ct',align:'center',hidden:true},
		    {header : '互感器电压倍率', sortable:true,  dataIndex:'pt',align:'center',hidden:true},
		    {header : '终端地址', sortable: true, dataIndex : 'terminalAddr', renderer : function (val) {
						 return "<div align = 'left'>"+ val + "</div>";
						 }, align : 'center'}, 
		    {header : 'A相电压', sortable: true, dataIndex : 'ua', align : 'center'}, 
		    {header : 'B相电压', sortable: true, dataIndex : 'ub', align : 'center'},
		    {header : 'C相电压', sortable: true, dataIndex : 'uc', align : 'center'},
		    {header : 'A相电流', sortable: true, dataIndex : 'ia', align : 'center'}, 
		    {header : 'B相电流', sortable: true, dataIndex : 'ib', align : 'center'},
		    {header : 'C相电流', sortable: true, dataIndex : 'ic', align : 'center'}
		]);	
		
	 var rt_pcStore = new Ext.data.Store({
				reader : new Ext.data.ArrayReader({
							idIndex: 0
						}, [{name:'keyId'},
						   {name : 'orgName'}, 
						   {name : 'consNo'}, 
						   {name : 'consName'}, 
						   {name : 'tmnlAssetNo'}, 
						   {name : 'terminalAddr'}, 
						   {name:'mpSn'},
						   {name:'ct'},
						   {name:'pt'},
						   {name : 'ua'}, 
						   {name : 'ub'}, 
						   {name : 'uc'},
						   {name : 'ia'}, 
						   {name : 'ib'}, 
						   {name : 'ic'}
						   ])
		});	
	   
	function getRemoteTestPCGridPnl(){
	    var rt_pcGrid = new Ext.grid.GridPanel({
		        store : rt_pcStore,
		        cm : rt_pccm,
//		        sm : rt_pcsm,
		        stripeRows : true,
		        autoScroll : true,
		        border: false,
		        tbar : [{
						icon : '',
						text : '一次侧',
						handler :function(){
							if(testNum ==2){
								rt_pcStore.each(function(rec){	
									if(rec.get('ua')!='')
										rec.set('ua', rec.get('ua')*rec.get('pt'));
									if(rec.get('ub')!='')	
										rec.set('ub', rec.get('ub')*rec.get('pt'));
									if(rec.get('uc')!='')
										rec.set('uc', rec.get('uc')*rec.get('pt'));
									if(rec.get('ia')!='')
										rec.set('ia', rec.get('ia')*rec.get('ct'));
									if(rec.get('ib')!='')
										rec.set('ib', rec.get('ib')*rec.get('ct'));
									if(rec.get('ic')!='')	
										rec.set('ic', rec.get('ic')*rec.get('ct'));
								});
								rt_pcStore.commitChanges();
								testNum =1;
							}
				        }
					}, {
						icon : '',
						text : '二次侧',
						handler :function(){
				            if(testNum ==1){
								rt_pcStore.each(function(rec){
									if(rec.get('ua')!='')
										rec.set('ua', (rec.get('ua')+0.0)/rec.get('pt'));
									if(rec.get('ub')!='')
										rec.set('ub', (rec.get('ub')+0.0)/rec.get('pt'));
									if(rec.get('uc')!='')
										rec.set('uc', (rec.get('uc')+0.0)/rec.get('pt'));
									if(rec.get('ia')!='')
										rec.set('ia', (rec.get('ia')+0.0)/rec.get('ct'));
									if(rec.get('ib')!='')
										rec.set('ib', (rec.get('ib')+0.0)/rec.get('ct'));
									if(rec.get('ic')!='')
										rec.set('ic', (rec.get('ic')+0.0)/rec.get('ct'));
								});
								rt_pcStore.commitChanges();
								testNum =2;
							}
				        }
					}],
		        bbar : new Ext.ux.MyToolbar({
					store : rt_pcStore,
					enableExpAll : true, // excel导出全部数据
					expAllText : "全部",
					enableExpPage : true, // excel仅导出当前页
					expPageText : "当前页"
				})
	    });
			    
	  //定义遥测电压电流的遥测结果数据面板
	    var rt_pcGridPnl = new Ext.Panel({
	    	id:'rt_pcGridPnl',
			layout:'fit',
		    border : false,
		    title : '召测结果',
			items : [rt_pcGrid]
		});
		
        return rt_pcGridPnl;
	}
	
	//返回遥测电压电流的遥测结果数据面板页面组件
	function getRemoteTestPCGridPanel(){
		return Ext.getCmp('rt_pcGridPnl');
	}
	//-----------------------------遥测电压电流的遥测结果----------------end
	
	//-----------------------------遥测功率的遥测结果----------------start
//	var rt_powersm = new Ext.grid.CheckboxSelectionModel();
	
	var rt_powercm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
//	rt_powersm,      
			//{header :'主键',sortable:true, dataIndex:'keyId',align:'center',hidden:true},
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
		    {header : '运行容量', sortable: true, dataIndex : 'runCap', align : 'center'}, 
		    {header : '当前总加有功功率', sortable: true, dataIndex : 'totalP', align : 'center'},
		    {header : '当前总加无功功率', sortable: true, dataIndex : 'totalQ', align : 'center'}
		]);
		
    var rt_powerStore = new Ext.data.Store({
				reader : new Ext.data.ArrayReader({
							idIndex: 0
						}, [{name:'keyId'},
						   {name : 'orgName'}, 
						   {name : 'consNo'}, 
						   {name : 'consName'}, 
						   {name : 'tmnlAssetNo'}, 
						   {name : 'terminalAddr'}, 
						   {name:'totalNo'},
						   {name : 'runCap'}, 
						   {name : 'totalP'}, 
						   {name : 'totalQ'}
						   ])
		});
	   
	
	//返回遥测功率的遥测结果数据面板
	function getRemoteTestPowerGridPnl(){
	    var rt_powerGrid = new Ext.grid.GridPanel({
		        store : rt_powerStore,
		        cm : rt_powercm,
//		        sm : rt_powersm,
		        stripeRows : true,
		        autoScroll : true,
		        border: false,
//		        tbar : [{
//						icon : '',
//						text : '一次侧',
//						handler : ''
//					}, {
//						icon : '',
//						text : '二次侧',
//						handler : ''
//					}],
		        bbar : new Ext.ux.MyToolbar({
					store : rt_powerStore,
					enableExpAll : true, // excel导出全部数据
					expAllText : "全部",
					enableExpPage : true, // excel仅导出当前页
					expPageText : "当前页"
				})
	    });
	    
	  //定义遥测功率的遥测结果数据面板
	    var rt_powerGridPnl = new Ext.Panel({
	    	id:'rt_powerGridPnl',
			layout:'fit',
		    border : false,
		    title : '召测结果',
			items : [rt_powerGrid]
		});
		
        return rt_powerGridPnl;
	}
	
	//返回遥测功率的遥测结果数据面板页面组件
	function getRemoteTestPowerGridPanel(){
		return Ext.getCmp('rt_powerGridPnl');
	}
	//-----------------------------遥测功率的遥测结果----------------end
	
	//-----------------------------遥测开关的遥测结果----------------start
//	var rt_switchsm = new Ext.grid.CheckboxSelectionModel();
	
	var rt_switchcm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
//	rt_switchsm,      
		    {header : '供电单位', sortable: true, dataIndex : 'orgName',renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			}, align : 'center', defaultWidth:140}, 
		    {header : '用户编号', sortable: true, dataIndex : 'consNo', align : 'center'}, 
		    {header : '用户名称', sortable: true, dataIndex : 'consName',renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
						+ val + '</div>';
				return html;
			}, align : 'center'}, 
//		    {header : '终端资产号', sortable:true,  dataIndex:'tmnlAssetNo',align:'center',hidden:true},
		    {header : '终端地址', sortable: true, dataIndex : 'terminalAddr',renderer : function (val) {
						 return "<div align = 'left'>"+ val + "</div>";
						 }, align : 'center'}, 
		    {header : '开关1', sortable: true, dataIndex : 'switch1', align : 'center'}, 
		    {header : '开关2', sortable: true, dataIndex : 'switch2', align : 'center'},
		    {header : '开关3', sortable: true, dataIndex : 'switch3', align : 'center'},
		    {header : '开关4', sortable: true, dataIndex : 'switch4', align : 'center'}, 
		    {header : '开关5', sortable: true, dataIndex : 'switch5', align : 'center'},
		    {header : '开关6', sortable: true, dataIndex : 'switch6', align : 'center'},
		    {header : '开关7', sortable: true, dataIndex : 'switch7', align : 'center'},
		    {header : '开关8', sortable: true, dataIndex : 'switch8', align : 'center'}
		]);

	    var rt_switchStore = new Ext.data.Store({
	    	proxy : new Ext.data.MemoryProxy(),
			reader : new Ext.data.ArrayReader({
						idIndex: 3
					}, [
					   {name : 'orgName'}, 
					   {name : 'consNo'}, 
					   {name : 'consName'}, 
					   {name : 'tmnlAssetNo'}, 
					   {name : 'terminalAddr'}, 
					   {name : 'switch1'}, 
					   {name : 'switch2'}, 
					   {name : 'switch3'},
					   {name : 'switch4'}, 
					   {name : 'switch5'}, 
					   {name : 'switch6'},
					   {name : 'switch7'}, 
					   {name : 'switch8'}
					  ])
		});
		
	//返回遥测开关的遥测结果数据面板
	function getRemoteTestSwitchGridPnl(){
	    var rt_switchGrid = new Ext.grid.GridPanel({
		        store : rt_switchStore,
		        cm : rt_switchcm,
//		        sm : rt_switchsm,
		        stripeRows : true,	
		        autoScroll : true,
		        border: false,
//		        tbar : [{
//						icon : '',
//						text : '一次侧',
//						handler : ''
//					}, {
//						icon : '',
//						text : '二次侧',
//						handler : ''
//					}],
		        bbar : new Ext.ux.MyToolbar({
					store : rt_switchStore,
					enableExpAll : true, // excel导出全部数据
					expAllText : "全部",
					enableExpPage : true, // excel仅导出当前页
					expPageText : "当前页"
				})
	    });
	    
	  //定义遥测开关的遥测结果数据面板
	    var rt_switchGridPnl = new Ext.Panel({
	    	id:'rt_switchGridPnl',
			layout:'fit',
		    border : false,
		    title : '召测结果',
			items : [rt_switchGrid]
		});
		
        return rt_switchGridPnl;
	}
	
	//返回遥测开关的遥测结果数据面板页面组件
	function getRemoteTestSwitchGridPanel(){
		return Ext.getCmp('rt_switchGridPnl');
	}
	//-----------------------------遥测开关的遥测结果----------------end
	
//选择用户默认选中		
	uiUserListStore.on('load',function(){
		uiUserSm.selectAll();
	});	
		
	powerUserListStore.on('load',function(){
		powerUserSm.selectAll();
	});	
		
	userListStore.on('load',function(){
		userSm.selectAll();
	});	
	
	//返回空页面
	function getBlankPnl(){
		var remoteCtrlBlankPnl = new Ext.Panel({
			id:'remoteCtrlBlankPnl',
			title:'召测结果'
		});
		return remoteCtrlBlankPnl;
	}
	
	//返回空页面组件
	function getBlankPanel(){
		return Ext.getCmp('remoteCtrlBlankPnl');
	}
	
	//返回遥测电压电流tab面板
	var uiRemoteCtrlTabPnl = new Ext.TabPanel({			
		id:'remote_ui',
		activeTab:0,
		region:'center',
		border:false,
		items:[uiUserListGridPnl, getRemoteTestPCGridPnl()]
	});
	
	//返回遥测功率tab面板
	var pwrRemoteCtrlTabPnl = new Ext.TabPanel({
		id:'remote_power',
		activeTab:0,
		region:'center',
		border:false,
		items:[powerUserListGridPnl, getRemoteTestPowerGridPnl()]
	});	
	
	//返回遥测开关拉合闸tab面板
	var ctrlRemoteCtrlTabPnl = new Ext.TabPanel({
		id:'remote_ctrl',
		activeTab:0,
		region:'center',
		border:false,
		items:[userListGridPnl, getRemoteTestSwitchGridPnl()]
	});
	
	var remoteCtrlTabPnl = new Ext.Panel({
		region : 'center',
		layout : 'card',
		activeItem : 0,
		//border : false,
    	items:[ctrlRemoteCtrlTabPnl, uiRemoteCtrlTabPnl, pwrRemoteCtrlTabPnl]
	});
	
	//返回tab面板组件
	function getRemoteCtrlTabPanel(){
		return Ext.getCmp('tb_remoteCtrl');
	}

    //定义整个页面面板
	var viewPanel = new Ext.form.FormPanel({
	        id : 'remoteCtrlPnl',
	        layout: 'border',
		    border : false,
		    items : [remoteCtrlTopPnl,remoteCtrlTabPnl]
	});		
	
	renderModel(viewPanel,'遥控');
	
	//监听左边树点击事件
    var treeListener = new LeftTreeListener({
	    modelName : '遥控',
	    processClick : function(p, node, e) {
		    var obj = node.attributes.attributes;
		    var type = node.attributes.type;
//	   	    取得当前活动页ID
	   	    var curPage = remoteCtrlTabPnl.getLayout().activeItem.getId();
		   	if(curPage==''|| curPage==null || typeof(curPage)=='undefined'){
		   		return;
		   	}
		   	Ext.getBody().mask('数据加载中...');
		    if(type != null){
		    	 if(curPage == 'remote_ui') {
				    uiUserListStore.baseParams = {
								subsId : obj.subsId,
								groupNo : obj.groupNo,
								lineId : obj.lineId,
								tmnlAssetNo : obj.tmnlAssetNo,
								orgNo : obj.orgNo,
								nodeType : type,
								orgType : obj.orgType
							};
		            uiUserListStore.load({
		            	callback : function(recs, options, success) {
		            		Ext.getBody().unmask();	
							if (success)
								uiUserListGrid.getSelectionModel().selectRecords(recs, true);
						},
		  	            add: true
		            });
		    	 } else if (curPage == 'remote_power') {
				    powerUserListStore.baseParams = {
				    			subsId:obj.subsId,
								groupNo : obj.groupNo,
								lineId : obj.lineId,
								tmnlAssetNo : obj.tmnlAssetNo,
								orgNo : obj.orgNo,
								nodeType : type,
								orgType : obj.orgType
							};
		            powerUserListStore.load({
		            	callback : function(recs, options, success) {
		            		Ext.getBody().unmask();	
							if (success)
								powerUserListGrid.getSelectionModel().selectRecords(recs, true);
						},
		  	            add: true
		            });
		    	 } else {
				    userListStore.baseParams = {
								subsId : obj.subsId,
								groupNo : obj.groupNo,
								lineId : obj.lineId,
								tmnlAssetNo : obj.tmnlAssetNo,
								orgNo : obj.orgNo,
								nodeType : type,
								orgType : obj.orgType
							};
		            userListStore.load({
		            	callback : function(recs, options, success) {
		            		Ext.getBody().unmask();	
							if (success)
								userListGrid.getSelectionModel().selectRecords(recs, true);
						},
		  	            add: true
		            });
		    	 }
//		    }else if(type == 'usr'){
//		    	if(curPage == 'remote_ui') {
//				    uiUserListStore.baseParams = {tmnlAssetNo:obj.tmnlAssetNo,nodeType:type};
//		            uiUserListStore.load({	 
//		            	callback : function(recs, options, success) {
//		            		Ext.getBody().unmask();	
//							if (success)
//								uiUserListGrid.getSelectionModel().selectRecords(recs, true);
//						},  	    
//		  	            add: true
//		            });
//		    	 } else if (curPage == 'remote_power') {
//				    powerUserListStore.baseParams = {tmnlAssetNo:obj.tmnlAssetNo,nodeType:type};
//		            powerUserListStore.load({	  	
//		            	callback : function(recs, options, success) {
//		            		Ext.getBody().unmask();	
//							if (success)
//								powerUserListGrid.getSelectionModel().selectRecords(recs, true);
//						},
//		  	            add: true
//		            });
//		    	 } else {
//				    userListStore.baseParams = {tmnlAssetNo:obj.tmnlAssetNo,nodeType:type};
//		            userListStore.load({	
//		            	callback : function(recs, options, success) {
//		            		Ext.getBody().unmask();	
//							if (success)
//								userListGrid.getSelectionModel().selectRecords(recs, true);
//						},
//		  	            add: true
//		            });
//		    	 }
//		    }else if(type == 'line'){
//		    	if(curPage == 'remote_ui') {
//				    uiUserListStore.baseParams = {lineId:obj.lineId,nodeType:type};
//		            uiUserListStore.load({	  	  
//		            	callback : function(recs, options, success) {
//		            		Ext.getBody().unmask();	
//							if (success)
//								uiUserListGrid.getSelectionModel().selectRecords(recs, true);
//						},   
//		  	            add: true
//		            });
//		    	 } else if (curPage == 'remote_power') {
//				    powerUserListStore.baseParams = {lineId:obj.lineId,nodeType:type};
//		            powerUserListStore.load({	  	
//		            	callback : function(recs, options, success) {
//		            		Ext.getBody().unmask();	
//							if (success)
//								powerUserListGrid.getSelectionModel().selectRecords(recs, true);
//						},
//		  	            add: true
//		            });
//		    	 } else {
//				    userListStore.baseParams = {lineId:obj.lineId,nodeType:type};
//		            userListStore.load({	
//		            	callback : function(recs, options, success) {
//		            		Ext.getBody().unmask();	
//							if (success)
//								userListGrid.getSelectionModel().selectRecords(recs, true);
//						},
//		  	            add: true
//		            });
//		    	 }
//		    }else if(type == 'cgp' || type=='ugp'){
//		    	if(curPage == 'remote_ui') {
//				    uiUserListStore.baseParams = {groupNo:obj.groupNo,nodeType:type};
//		            uiUserListStore.load({	  	     
//		            	callback : function(recs, options, success) {
//		            		Ext.getBody().unmask();	
//							if (success)
//								uiUserListGrid.getSelectionModel().selectRecords(recs, true);
//						},
//		  	            add: true
//		            });
//		    	 } else if (curPage == 'remote_power') {
//				    powerUserListStore.baseParams = {groupNo:obj.groupNo,nodeType:type};
//		            powerUserListStore.load({	  
//		            	callback : function(recs, options, success) {
//		            		Ext.getBody().unmask();	
//							if (success)
//								powerUserListGrid.getSelectionModel().selectRecords(recs, true);
//						},
//		  	            add: true
//		            });
//		    	 } else {
//				    userListStore.baseParams = {groupNo:obj.groupNo,nodeType:type};
//		            userListStore.load({	
//		            	callback : function(recs, options, success) {
//		            		Ext.getBody().unmask();	
//							if (success)
//								userListGrid.getSelectionModel().selectRecords(recs, true);
//						},
//		  	            add: true
//		            });
//		    	}
//		    }else if(type == 'sub'){
//		    	if(curPage == 'remote_ui') {
//				    uiUserListStore.baseParams = {subsId:obj.subsId,nodeType:type};
//		            uiUserListStore.load({	  	 
//		            	callback : function(recs, options, success) {
//		            		Ext.getBody().unmask();	
//							if (success)
//								uiUserListGrid.getSelectionModel().selectRecords(recs, true);
//						},    
//		  	            add: true
//		            });
//		    	 } else if (curPage == 'remote_power') {
//				    powerUserListStore.baseParams = {subsId:obj.subsId,nodeType:type};
//		            powerUserListStore.load({	 
//		            	callback : function(recs, options, success) {
//		            		Ext.getBody().unmask();	
//							if (success)
//								powerUserListGrid.getSelectionModel().selectRecords(recs, true);
//						},
//		  	            add: true
//		            });
//		    	 } else {
//				    userListStore.baseParams = {subsId:obj.subsId,nodeType:type};
//		            userListStore.load({	
//		            	callback : function(recs, options, success) {
//		            		Ext.getBody().unmask();	
//							if (success)
//								userListGrid.getSelectionModel().selectRecords(recs, true);
//						},
//		  	            add: true
//		            });
//		   		}
		    }else {
			    return true;
		    }
   	    },
   	    processUserGridSelect: function(cm,row,record) {
//	   	    取得当前活动页ID
	   	    var curPage = remoteCtrlTabPnl.getLayout().activeItem.getId();
		   	if(curPage==''|| curPage==null || typeof(curPage)=='undefined'){
		   		return;
		   	}
//		   	根据ID加载不同store
	   	    if(curPage == 'remote_ui') {
			    uiUserListStore.baseParams = {tmnlAssetNo:record.get('tmnlAssetNo'),nodeType:'usr'};
	            uiUserListStore.load({	  	 
	            	callback : function(recs, options, success) {
	            		Ext.getBody().unmask();	
						if (success)
							uiUserListGrid.getSelectionModel().selectRecords(recs, true);
					},    
	  	            add: true
	            });
	    	 } else if (curPage == 'remote_power') {
			    powerUserListStore.baseParams = {tmnlAssetNo:record.get('tmnlAssetNo'),nodeType:'usr'};
	            powerUserListStore.load({	
	            	callback : function(recs, options, success) {
	            		Ext.getBody().unmask();	
						if (success)
							powerUserListGrid.getSelectionModel().selectRecords(recs, true);
					},
	  	            add: true
	            });
	    	 } else {
		   	    userListStore.baseParams = {tmnlAssetNo:record.get('tmnlAssetNo'),nodeType:'usr'};
		        userListStore.load({	
		        	callback : function(recs, options, success) {
		        		Ext.getBody().unmask();	
						if (success)
							userListGrid.getSelectionModel().selectRecords(recs, true);
					},
		  	        add: true
		        });
	        }
        }
    });
    
    userListStore.on('load', function(thisstore, recs, obj) {//当加载拉合闸用户数据时，将分页的的数据store赋值
		spliteUserListStore.proxy = new Ext.ux.data.PagingMemoryProxy(ctrlStoreToArray(thisstore));
		spliteUserListStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
		userSm.selectAll();//默认全选
		userListGrid.doLayout();
	});
	
	spliteUserListStore.on('load', function(thisstore, recs, obj) {//点击翻页时，还要默认全选
		userSm.selectAll();
	});
	
	uiUserListStore.on('load', function(thisstore, recs, obj) {//当加载电压电流用户数据时，将分页的的数据store赋值
		spliteUiUserListStore.proxy = new Ext.ux.data.PagingMemoryProxy(uiStoreToArray(thisstore));
		spliteUiUserListStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
		uiUserSm.selectAll();//默认全选
		uiUserListGrid.doLayout();
	});
	
	spliteUiUserListStore.on('load', function(thisstore, recs, obj) {//点击翻页时，还要默认全选
		uiUserSm.selectAll();
	});
	
	powerUserListStore.on('load', function(thisstore, recs, obj) {
		splitePowerUserListStore.proxy = new Ext.ux.data.PagingMemoryProxy(powerStoreToArray(thisstore));
		splitePowerUserListStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
		powerUserSm.selectAll();
		powerUserListGrid.doLayout();
	});
	
	splitePowerUserListStore.on('load', function(thisstore, recs, obj) {
		powerUserSm.selectAll();
	});
	
	rt_pcStore.on('load', function(){
		testNum = 2;
	});	
});