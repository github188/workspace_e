Ext.onReady(function() {
	var exceptionTabPanel = new Ext.TabPanel({
		   activeTab: 0,
		   border : false,
		   items:[{
		      title:'系统异常',
		      border : false
//		      layout:'border',
//		      items:[northReqRatioAnalysePanel,centerReqRatioAnalysePanel]
		   },{
		      title:'数据异常',
		      border : false
//		      layout:'border',
//		      items:[northVipConsPowerOverPanel,centerVipConsPowerOverPanel]
		   },{
		      title:'主站分析异常',
		      border : false,
		      layout:'border',
		      items:[excepQueryPanel,ct]
		   },{
		      title:'事件报警',
		      border : false,
		      layout:'border',
		      items:[center, east],
		      listeners : {
		      	activate : function () {
		      		terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(false);
					consTypeCombox.getEl().up('.x-form-item').setDisplayed(true);
		      	}
		      }
		   }]
	})
	
	var leftTreeListener = new LeftTreeListener({
		modelName : '异常查询',
		processClick : function(p, node, e) {
			//主站异常加载左边树
			if (exceptionTabPanel.activeTab.title == '主站分析异常') {
				deviceClick(p, node, e);
			}
			if (exceptionTabPanel.activeTab.title == '事件报警') {
				alertClick(p, node, e);
			}

	   	},
	   	processUserGridSelect:function(cm,row,record){
	   		if (exceptionTabPanel.activeTab.title == '主站分析异常') {
	   			deviceUserGridSelect(cm,row,record);
	   		}
	   		if (exceptionTabPanel.activeTab.title == '事件报警') {
				alertUserGridSelect(cm,row,record);
			}
	    }
	})
	
	renderModel(exceptionTabPanel,'异常查询');

	if(Ext.getCmp('alertButton1')!=null) {
		exceptionTabPanel.setActiveTab(3);
		Ext.getCmp('alertButton1').on('click', loadAlertEventStore);
	}
		
	if(Ext.getCmp('alertButton2')!=null) {
		exceptionTabPanel.setActiveTab(3);
		Ext.getCmp('alertButton2').on('click', loadAlertEventStore);
	}
				
	if(Ext.getCmp('alertButton3')!=null) {
		exceptionTabPanel.setActiveTab(3);
		Ext.getCmp('alertButton3').on('click', loadAlertEventStore);
	}
			
	if(typeof(alertWindowParam) != 'undefined'){
		exceptionTabPanel.setActiveTab(3);
		if(alertWindowParam == 'ok'){
			getAlertMessage();
		}
		alertWindowParam = null;
	}

	if (typeof(ammeterRunStatus) != 'undefined'&&ammeterRunStatus=='ammeterRunStatus') {
		exceptionTabPanel.setActiveTab(3);
		searchAmmeterH();
		ammeterRunStatus = null;
	}
	
	if(typeof(deviceMonitorParam) != 'undefined'&&deviceMonitorParam=='deviceMonitor'){
		exceptionTabPanel.setActiveTab(3);
		DeviceMonitorAlertMessage();
		deviceMonitorParam = null;
	}
				
	// added by 姜炜超 for 重点户监测数据------------start
	if ((typeof(vipConsMonitorData_Flag1) != 'undefined')
			&& vipConsMonitorData_Flag1) {
		exceptionTabPanel.setActiveTab(3);
		searchVipMoitorData();
	}
			// added by 姜炜超 for 重点户监测数据------------end

	// 页面非激活状态下调用方法
	if (typeof(window.rowId) != 'undefined' && !Ext.isEmpty(window.rowId)) {
		exceptionTabPanel.setActiveTab(3);
		searchByRowID();
	}

	
	//页面非激活状态下调用方法
	if (typeof(window.exceptionId) != 'undefined' && !Ext.isEmpty(window.exceptionId)) {
		exceptionTabPanel.setActiveTab(2);
		searchByExceptionId();
	}
	
	//页面激活状态下调用方法
	Ext.getCmp('异常查询').on('activate', function() {
		if(Ext.getCmp('alertButton1')!=null) {
			exceptionTabPanel.setActiveTab(3);
			Ext.getCmp('alertButton1').on('click', loadAlertEventStore);
		}
			
		if(Ext.getCmp('alertButton2')!=null) {
			exceptionTabPanel.setActiveTab(3);
			Ext.getCmp('alertButton2').on('click', loadAlertEventStore);
		}
					
		if(Ext.getCmp('alertButton3')!=null) {
			exceptionTabPanel.setActiveTab(3);
			Ext.getCmp('alertButton3').on('click', loadAlertEventStore);
		}
		
		if (typeof(ammeterRunStatus) != 'undefined'&&ammeterRunStatus=='ammeterRunStatus') {
			exceptionTabPanel.setActiveTab(3);
			searchAmmeterH();
			ammeterRunStatus = null;
		}
		
		if(typeof(alertWindowParam) != 'undefined'){
			exceptionTabPanel.setActiveTab(3);
			if(alertWindowParam == 'ok'){
				getAlertMessage();
			}
			alertWindowParam = null;
		}
	
		if(typeof(deviceMonitorParam) != 'undefined'&&deviceMonitorParam=='deviceMonitor'){
			exceptionTabPanel.setActiveTab(3);
			DeviceMonitorAlertMessage();
			deviceMonitorParam = null;
		}
					
		if ((typeof(vipConsMonitorData_Flag1) != 'undefined')
				&& vipConsMonitorData_Flag1) {
			exceptionTabPanel.setActiveTab(3);
			searchVipMoitorData();
		}
	
		if (typeof(window.rowId) != 'undefined' && !Ext.isEmpty(window.rowId)) {
			exceptionTabPanel.setActiveTab(3);
			searchByRowID();
		}
		
		if (typeof(window.exceptionId) != 'undefined' && !Ext.isEmpty(window.exceptionId)) {
			exceptionTabPanel.setActiveTab(2);
			searchByExceptionId();
		}
	});
})