/*
 * ! Author: jiangweichao Date:11/27/2009 Description: 预付费工况信息 Update
 * History:none
 */
Ext.onReady(function() {
	//全局变量
	var sNodeId;// 左边树选择编号
	var sNodeType; 
	var cNodeId;// 左边树选择编号
	var cNodeType; 
//------------------------------------------当日购电执行情况统计-----------------start
	//定义预付费工况信息的上部面板
//	var checkBoxGroup = new Ext.form.RadioGroup({
//		fieldLabel : '控制方式',
//		width : 170,
//		labelSeparator : '',
//		items : [{
//					boxLabel : '终端预付费',
//					name : 'prePaidType',
//					inputValue : 1,
//					checked : true
//				}, {
//					boxLabel : '主站预付费',
//					name : 'prePaidType',
//					inputValue : 2,
//					checked : false
//				}]
//	});
	
	var todayBuyExecStatusNode = new Ext.form.TextField({
	    id : 'node',
		labelSeparator : '',
		fieldLabel : '节点名',
		emptyText : '请从左边树选择地市或区县的供电单位',
	    allowBlank : false,
	    blankText : '请从左边树选择地市或区县的供电单位',
		readOnly : true,
		width:230
	});
	
	/*//开始日期
	var queryStartDate = new Ext.form.DateField({
		fieldLabel : '从',
		width:90,
		format: 'Y-m-d',
		value : new Date(),
	    labelSeparator:''
    });

    //结束日期
    var queryEndDate = new Ext.form.DateField({
		fieldLabel : '到',
		width:90,
		format: 'Y-m-d',
	    value : new Date().add(Date.DAY,30),
	    labelSeparator:''
    });*/
    
	var sTodayDateField = new Ext.form.TextField({
		fieldLabel : '日期',
		width:90,
	    value : new Date().format('Y-m-d'),
	    labelSeparator:'',
	    readOnly:true
    });
    
	var qryBtn = new Ext.Button({
		width : 70,
		text : '查询',
		handler:function(){
			//sTodayDateField.setValue(new Date().format('Y-m-d'));
			sTodayDateField.setValue(new Date().format('Y-m-d'));
			tmnlListStore.removeAll();
			if(''==todayBuyExecStatusNode.getValue().trim()){
				Ext.MessageBox.alert("提示","请从左边树选择地市或区县的供电单位！");
			    return;
			}
			tmnlListStore.baseParams = {
						orgNo : sNodeId,
						orgType:sNodeType,
						execDate:sTodayDateField.getValue()
					};
			Ext.getBody().mask("正在获取数据...");
			tmnlListStore.load({
									callback : function(){
										Ext.getBody().unmask();			
									}
								});
		/*	else if(checkBoxGroup.getValue().getRawValue()==2){
				stationListStore.baseParams = {
							orgNo : sNodeId,
							busiType:checkBoxGroup.getValue().getRawValue(),
							execDate:sTodayDateField.getValue()
						};
				Ext.getBody().mask("正在获取数据...");
				stationListStore.load({
										callback : function(){
											Ext.getBody().unmask();			
										}
									});
			}*/
		}
	});
	
	var topPanel = new Ext.Panel({
	    labelAlign : 'right',
		border : false,
		height : 40,
		region : 'north',
		layout : 'table',
		layoutConfig : {
			columns : 3
		},
		defaults:{height: 35},
		bodyStyle : 'padding:10px 0px 0px 0px;',
		items : [{
			layout : 'form',
			border : false,
			labelWidth : 50,
			labelAlign : 'right',
			width : 365,
			items : [todayBuyExecStatusNode]
		}/*, {
			layout : 'form',
			border : false,
			labelWidth : 50,
			width : 250,
			items : [checkBoxGroup]
		}*/,{
			layout : 'form',
			border : false,
			labelWidth : 35,
			labelAlign : 'right',
			width : 225,
			items:[sTodayDateField]	
		},{
			layout : 'form',
			border : false,
			width : 100,
			items : [qryBtn]
		}]
	});
	
	var tmnlQueryResultRowNum = new Ext.grid.RowNumberer({
			renderer : function(v, p, record, rowIndex) {
				var startRow = 0;
				if(tmnlListStore && tmnlListStore.lastOptions && tmnlListStore.lastOptions.params){
					startRow = tmnlListStore.lastOptions.params.start;
				}
				return startRow + rowIndex + 1;
			}
		});
		
	var  tmnlQueryResultCm = new Ext.grid.ColumnModel([ 
	    tmnlQueryResultRowNum,
	    {header : '供电单位', sortable: true, dataIndex : 'orgName', align : 'center'}, 
	    {header : '购电单总数', sortable: true, dataIndex : 'buyOrderNum', align : 'center'}, 
	    {header : '执行成功购电记录统计', sortable: true, dataIndex : 'succFeeCnt', align : 'center',
    		  renderer: function(s, m, rec){
    		  	        if(s>0){
				 			return "<a href='javascript:'onclick='tmnlPrePaidQueryShow(\""
								+ rec.get('orgNo')
								+ "\",\""
								+ rec.get('orgType')
								+ "\",\""
								+ sTodayDateField.getValue()
								+ "\",\"1\",\"执行成功购电记录\");'>" + s + "</a>"; 
    		  	        }
			 			else
			 			    return s;
			  }}, 
	    {header : '执行成功回复营销记录统计', sortable: true, dataIndex : 'succReCnt', align : 'center', 
	    	  renderer: function(s, m, rec){
    	  			 if(s>0){
				 		return "<a href='javascript:'onclick='tmnlPrePaidQueryShow(\""
							+ rec.get('orgNo')
							+ "\",\""
							+ rec.get('orgType')
							+ "\",\""
							+ sTodayDateField.getValue()
							+ "\",\"2\",\"执行成功回复营销记录\");'>" + s + "</a>"; 
    	  			 }
    	  			 else
		 			    return s;
			  }}, 
	    {header : '执行失败购电记录统计', sortable: true, dataIndex : 'failFeeCnt', align : 'center', 
			  renderer: function(s, m, rec){
			  		if(s>0){
						return "<a href='javascript:'onclick='tmnlPrePaidQueryShow(\""
							+ rec.get('orgNo')
							+ "\",\""
							+ rec.get('orgType')
							+ "\",\""
							+ sTodayDateField.getValue()
							+ "\",\"3\",\"执行失败购电记录\");'>" + s + "</a>"; 
			  		}
			  		else
		 			    return s;
			  }},   
	    {header : '执行失败回复营销记录统计', sortable: true, dataIndex : 'failReCnt', align : 'center',
	  		  renderer: function(s, m, rec){
	  		  		if(s>0){
						return "<a href='javascript:'onclick='tmnlPrePaidQueryShow(\""
							+ rec.get('orgNo')
							+ "\",\""
							+ rec.get('orgType')
							+ "\",\""
							+ sTodayDateField.getValue()
							+ "\",\"4\",\"执行失败回复营销记录\");'>" + s + "</a>"; 
	  		  		}
	  		  		else
		 			    return s;
			  }}, 
    	{header : '执行结果未回复营销统计', sortable: true, dataIndex : 'notReCnt', align : 'center',
   			  renderer: function(s, m, rec){
	   			  	if(s>0){
					 		return "<a href='javascript:'onclick='tmnlPrePaidQueryShow(\""
								+ rec.get('orgNo')
								+ "\",\""
								+ rec.get('orgType')
								+ "\",\""
								+ sTodayDateField.getValue()
								+ "\",\"5\",\"执行结果未回复营销记录\");'>" + s + "</a>"; 
					}
	  		  		else
		 			    return s;
			  }}, 
    	{header : '正在执行购电记录统计', sortable: true, dataIndex : 'actCtrlCnt', align : 'center',
			  renderer: function(s, m, rec){
			  		if(s>0){
						return "<a href='javascript:'onclick='tmnlPrePaidQueryShow(\""
							+ rec.get('orgNo')
							+ "\",\""
							+ rec.get('orgType')
							+ "\",\""
							+ sTodayDateField.getValue()
							+ "\",\"6\",\"正在执行购电记录\");'>" + s + "</a>"; 
			  		}
			  		else
			  		  	return s;
			  }},  	
	 	{header : '待执行购电记录统计', sortable: true, dataIndex : 'waitactCtrlCnt', align : 'center',
		      renderer: function(s, m, rec){
		      		if(s>0){
						return "<a href='javascript:'onclick='tmnlPrePaidQueryShow(\""
							+ rec.get('orgNo')
							+ "\",\""
							+ rec.get('orgType')
							+ "\",\""
							+ sTodayDateField.getValue()
							+ "\",\"7\",\"待执行购电记录\");'>" + s + "</a>"; 
					}
			  		else
			  		  	return s;
			  }}
	]);
		
    var tmnlListStore = new Ext.data.Store({
		url : './baseapp/prePaidStatus!todayBuyExecStatusStatistics.action',
		reader : new Ext.data.JsonReader({
					root : 'buyExecStatusStatBeanList',
					idProperty:'orgNo'
				}, [
				   {name : 'orgType'}, 
				   {name : 'orgNo'}, 
				   {name : 'orgName'}, 
				   {name : 'buyOrderNum'}, 
				   {name : 'succFeeCnt'}, 
				   {name : 'succReCnt'}, 
				   {name : 'failFeeCnt'}, 
				   {name : 'failReCnt'}, 
				   {name : 'notReCnt'},
				   {name : 'actCtrlCnt'},
				   {name : 'waitactCtrlCnt'}
				   ])
	});
	    //终端预付费查询结果
    var tmnlQueryResultGrid = new Ext.grid.GridPanel({
    	    region:'center',
    	    loadMask:false,
	        store : tmnlListStore,
	        cm : tmnlQueryResultCm,
	        stripeRows : true,	
	        autoScroll : true,
            tbar:  [{
				xtype: 'label',
				html : "<font font-weight:bold;>查询结果</font>"
             	}]
    });
    
	/*var stationQueryResulRowNum = new Ext.grid.RowNumberer({
			renderer : function(v, p, record, rowIndex) {
				var startRow = 0;
				if(stationListStore && stationListStore.lastOptions && stationListStore.lastOptions.params){
					startRow = stationListStore.lastOptions.params.start;
				}
				return startRow + rowIndex + 1;
			}
		});
	var stationQueryResultCm = new Ext.grid.ColumnModel([
		stationQueryResulRowNum,
	    {header : '供电单位', sortable: true, dataIndex : 'orgName', align : 'center'}, 
	    {header : '购电单总数', sortable: true, dataIndex : 'buyOrderNum', align : 'center'}, 
	    {header : '催费记录统计', sortable: true, dataIndex : 'actUrgefeeCnt', align : 'center'}, 
	    {header : '执行催费回复营销记录统计', sortable: true, dataIndex : 'cisUrgefeeCnt', align : 'center'}, 
	    {header : '执行催费停电记录统计', sortable: true, dataIndex : 'stopUrgefeeCnt', align : 'center'}, 
	    {header : '执行催费停电回复营销记录统计', sortable: true, dataIndex : 'cisStopCnt', align : 'center'}
	]);
	

	
	var stationListStore = new Ext.data.Store({
		url : './baseapp/prePaidStatus!todayBuyExecStatusStatistics.action',
		reader : new Ext.data.JsonReader({
					root : 'buyExecStatusStatBeanList',
					idProperty:'orgNo'
				}, [
				   {name : 'orgNo'}, 
				   {name : 'orgName'}, 
				   {name : 'buyOrderNum'}, 
				   {name : 'actUrgefeeCnt'},
				   {name : 'cisUrgefeeCnt'},
				   {name : 'stopUrgefeeCnt'},
				   {name : 'cisStopCnt'}
				   ])
	});
	
    //主站预付费查询结果
    var stationQueryResultGrid = new Ext.grid.GridPanel({
	    title : '查询结果',
	    loadMask:false,
        store : stationListStore,
        cm : stationQueryResultCm,
        stripeRows : true,	
        autoScroll : true,
        border: false
    });
    
	var queryResultPanel = new Ext.Panel({
		region:'center',
		layout : 'card',
		activeItem : 0,
		items : [tmnlQueryResultGrid, stationQueryResultGrid]
	});*/
	
	//定义整个页面面板
	var dayPurchasePanel = new Ext.Panel({
    	id:'dayPurchasePanel',
		title:'当日终端预购电执行情况统计',
        layout: 'border',
	    items : [topPanel, tmnlQueryResultGrid]
	});
//------------------------------------------当日购电执行情况统计-------------------end	
//------------------------------------------当日预付费控制执行统计-----------------start
	
   var todayCtrlExecStatNode = new Ext.form.TextField({
		labelSeparator : '',
		fieldLabel : '节点名',
		emptyText : '请从左边树选择地市或区县的供电单位',
		allowBlank : false,
		blankText : '请从左边树选择地市或区县的供电单位',
		readOnly : true,
		width:230
	});
	
	/*//开始日期
	var prePaidQueryStartDate = new Ext.form.DateField({
		fieldLabel : '从',
		width:90,
		format: 'Y-m-d',
		value : new Date(),
	    labelSeparator:''
    });

    //结束日期
    var prePaidQueryEndDate = new Ext.form.DateField({
		fieldLabel : '到',
		width:90,
		format: 'Y-m-d',
	    value : new Date().add(Date.DAY,30),
	    labelSeparator:''
    });*/

    var cTodayDateField = new Ext.form.TextField({
		fieldLabel : '日期',
		width:90,
	    value : new Date().format('Y-m-d'),
	    labelSeparator:'',
	    readOnly:true
    });
    
	var todayCtrlExecStatBtn = new Ext.Button({
		width : 70,
		text : '查询',
		handler:function(){
			cTodayDateField.setValue(new Date().format('Y-m-d'));
			todayCtrlExecStatStore.removeAll();
			if(''==todayCtrlExecStatNode.getValue()){
				Ext.MessageBox.alert("提示","请从左边树选择地市或区县的供电单位！");
			    return;
			}
			todayCtrlExecStatStore.baseParams = {
						orgNo : cNodeId,
						execDate : cTodayDateField.getValue(),
						orgType : cNodeType
					};
			Ext.getBody().mask("正在获取数据...");
			todayCtrlExecStatStore.load({
									callback : function(){
										Ext.getBody().unmask();			
									}
								});
		}
	});
	
	var prePaidTopPanel = new Ext.Panel({
		border : false,
		height : 40,
		region : 'north',
		layout : 'table',
		layoutConfig : {
			columns : 3
		},
		defaults:{height: 35},
		bodyStyle : 'padding:10px 0px 0px 0px;',
		items : [{
			layout : 'form',
			border : false,
			labelWidth : 50,
			labelAlign : 'right',
			width : 365,
			items : [todayCtrlExecStatNode]
		},{
			layout : 'form',
			border : false,
			labelWidth : 35,
			labelAlign : 'right',
			width : 225,
			items:[cTodayDateField]	
		}, {
			layout : 'form',
			border : false,
			width : 100,
			items : [todayCtrlExecStatBtn]
		}]
	});
	
	var  todayCtrlExecStatRowNum = new Ext.grid.RowNumberer({
			renderer : function(v, p, record, rowIndex) {
				var startRow = 0;
				if(todayCtrlExecStatStore && todayCtrlExecStatStore.lastOptions && todayCtrlExecStatStore.lastOptions.params){
					startRow = todayCtrlExecStatStore.lastOptions.params.start;
				}
				return startRow + rowIndex + 1;
			}
		});
		
	var todayCtrlExecStatCm = new Ext.grid.ColumnModel([
		todayCtrlExecStatRowNum,
	    {header : '供电单位', sortable: true, dataIndex : 'orgName', align : 'center'}, 
	    {header : '预付费控制总数', sortable: true, dataIndex : 'prePaidCtrlNum', align : 'center'},	
	    {header : '执行停电户数', sortable: true, dataIndex : 'succPoweroffCnt', align : 'center',
	     	renderer: function(s, m, rec){
	     				if(s>0)
				 			return "<a href='javascript:'onclick='prePaidCtrlExecQueryShow(\""+rec.get('orgNo')+"\",\""+rec.get('orgType')+"\",\""+ cTodayDateField.getValue()+"\",\"2\",\"1\",\"停电成功用户\");'>"+s+"</a>";
				 		else
				 		   return s;
		    }}, 
	    {header : '执行停电失败户数', sortable: true, dataIndex : 'failPoweroffCnt', align : 'center',
	    	renderer: function(s, m, rec){
	     				if(s>0)
				 			return "<a href='javascript:'onclick='prePaidCtrlExecQueryShow(\""+rec.get('orgNo')+"\",\""+rec.get('orgType')+"\",\""+ cTodayDateField.getValue()+"\",\"2\",\"0\",\"停电失败用户\");'>"+s+"</a>";
				 		else
				 		   return s;
		    }}, 
	    {header : '执行复电户数', sortable: true, dataIndex : 'succPowerresCnt', align : 'center',
	    	renderer: function(s, m, rec){
	     				if(s>0)
				 			return "<a href='javascript:'onclick='prePaidCtrlExecQueryShow(\""+rec.get('orgNo')+"\",\""+rec.get('orgType')+"\",\""+ cTodayDateField.getValue()+"\",\"3\",\"1\",\"复电成功用户\");'>"+s+"</a>";
				 		else
				 		   return s;
		    }}, 
	    {header : '执行复电失败户数', sortable: true, dataIndex : 'failPowerresCnt', align : 'center',
	    	renderer: function(s, m, rec){
	     				if(s>0)
				 			return "<a href='javascript:'onclick='prePaidCtrlExecQueryShow(\""+rec.get('orgNo')+"\",\""+rec.get('orgType')+"\",\""+ cTodayDateField.getValue()+"\",\"3\",\"0\",\"复电失败用户\");'>"+s+"</a>";
				 		else
				 		   return s;
		    }}, 
	    {header : '执行催费告警户数', sortable: true, dataIndex : 'succUrgefeeCnt', align : 'center',
	    	renderer: function(s, m, rec){
	     				if(s>0)
				 			return "<a href='javascript:'onclick='prePaidCtrlExecQueryShow(\""+rec.get('orgNo')+"\",\""+rec.get('orgType')+"\",\""+ cTodayDateField.getValue()+"\",\"1\",\"1\",\"催费成功用户\");'>"+s+"</a>";
				 		else
				 		   return s;
		    }}, 
	    {header : '执行催费告警失败', sortable: true, dataIndex : 'failUrgefeeCnt', align : 'center',
	    	renderer: function(s, m, rec){
	     				if(s>0)
				 			return "<a href='javascript:'onclick='prePaidCtrlExecQueryShow(\""+rec.get('orgNo')+"\",\""+rec.get('orgType')+"\",\""+ cTodayDateField.getValue()+"\",\"1\",\"0\",\"催费失败用户\");'>"+s+"</a>";
				 		else
				 		   return s;
		    }} 
	]);
	
	//var data=[['001','西宁市供电公司','6','1','1','1','1','1','1']];
    var todayCtrlExecStatStore = new Ext.data.Store({
		url : './baseapp/prePaidStatus!todayPerPaidCtrlExecStatistics.action',
		reader : new Ext.data.JsonReader({
				root:'prePaidCtrlExecStatBeanList',
				idProperty:'orgNo'
			}, [
			   {name : 'orgType'},
			   {name : 'orgNo'},
			   {name : 'orgName'}, 
			   {name : 'prePaidCtrlNum'}, 
			   {name : 'succPoweroffCnt'}, 
			   {name : 'failPoweroffCnt'}, 
			   {name : 'succPowerresCnt'}, 
			   {name : 'failPowerresCnt'}, 
			   {name : 'succUrgefeeCnt'},
			   {name : 'failUrgefeeCnt'}
			   ])
	});
 
    var todayCtrlExecStatGrid = new Ext.grid.GridPanel({
    	    region:'center',
	        store : todayCtrlExecStatStore,
	        cm : todayCtrlExecStatCm,
	        stripeRows : true,	
	        autoScroll : true,
	        loadMask:false,
	        tbar:  [{
				xtype: 'label',
				html : "<font font-weight:bold;>查询结果</font>"
             	}]
    });
    
//    todayCtrlExecStatGrid.on('rowclick',function(){
//				openTab("预付费控制执行查询","baseApp/prePaidMan/prePaidExecQuery.jsp");
//	});
    
	var dayPrePaidPanel = new Ext.Panel({
		id:'dayPrePaidPanel',
		title:'当日预付费控制执行统计',
		layout:'border',
		items:[prePaidTopPanel,todayCtrlExecStatGrid]
	});
//------------------------------------------当日预付费控制执行统计-----------------start
	var viewPanel =  new Ext.TabPanel({
	   activeTab: 0,
	   border : false,
	   items:[dayPurchasePanel,dayPrePaidPanel]
	});
	
	renderModel(viewPanel,'预付费工况信息');
	
	// 监听左边树点击事件
	var pStatusTreeListener = new LeftTreeListener({
		modelName : '预付费工况信息',
		processClick : function(p, node, e) {
			var obj = node.attributes.attributes;
			var type = node.attributes.type;
			if (type == 'org'&&('03'==obj.orgType||'04'==obj.orgType)){
				if("dayPurchasePanel"==viewPanel.getActiveTab().getId()){
					sNodeId = obj.orgNo;
					sNodeType=obj.orgType;
					todayBuyExecStatusNode.setValue(node.text);
				}
				else if("dayPrePaidPanel"==viewPanel.getActiveTab().getId()){
					cNodeId = obj.orgNo;
					cNodeType=obj.orgType;
				    todayCtrlExecStatNode.setValue(node.text);
				}
			}
		}
	});
});
	
	//当日终端预付费执行情况统计明细窗口显示
	function tmnlPrePaidQueryShow(orgNo,orgType,sendTime,statType,title){
		var queryResultRowNum = new Ext.grid.RowNumberer({
			renderer : function(v, p, record, rowIndex) {
				var startRow = 0;
				if(queryResultStore && queryResultStore.lastOptions && queryResultStore.lastOptions.params){
					startRow = queryResultStore.lastOptions.params.start;
				}
				return startRow + rowIndex + 1;
			}
		});
		
		var queryResultCm = new Ext.grid.ColumnModel([  
	        queryResultRowNum,                                         
		    {header : '供电单位', sortable: true, dataIndex : 'orgName', align : 'center',
		   		 renderer : function(value) {
		   		 	if(null!=value){
						var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + value + '">'
								+ value + '</div>';
						return html;
			   		}
			   		else
			   		    return '';
		   		    }}, 
		    {header : '购电单号', sortable: true, dataIndex : 'appNo', align : 'center'},
		    {header : '用户编号', sortable: true, dataIndex : 'consNo', align : 'center'}, 
		    {header : '用户名称', sortable: true, dataIndex : 'consName', align : 'center',
		    	 renderer : function(value) {
		    	 	if(null!=value){
						var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + value + '">'
								+ value + '</div>';
						return html;
			    	}
			    	else
			    	     return '';
		    	    }},
		    {header : '终端地址', sortable: true, dataIndex : 'terminalAddr', align : 'center'}, 
		    {header : '电能表资产号', sortable: true, dataIndex : 'meterAssetNo', align : 'center',
		    	 renderer : function(value) {
		    	 	if(null!=value){
						var html = '<div align = "left" ext:qtitle="电能表资产号" ext:qtip="' + value + '">'
									+ value + '</div>';
						return html;
			    	}
			    	else
			    	 	return '';
			    	}},
		    {header : '执行日期', sortable: true, dataIndex : 'executeDate', align : 'center',
			    renderer : function(value) {
		    	 	if(null!=value){
						var html = '<div align = "center" ext:qtitle="执行日期" ext:qtip="' + value + '">'
									+ value + '</div>';
						return html;
			    	}
			    	else
			    	 	return '';
			    	}},
		    {header : '执行状态', sortable: true, dataIndex : 'executeStatus', align : 'center'}, 
		    {header : '流程状态', sortable: true, dataIndex : 'flowStatus', align : 'center',
		    renderer : function(value) {
		    	 	if(null!=value){
						var html = '<div align = "left" ext:qtitle="流程状态" ext:qtip="' + value + '">'
									+ value + '</div>';
						return html;
			    	}
			    	else
			    	 	return '';
			    	}}
		]);
	
	    var queryResultStore = new Ext.data.Store({
			   	url : './baseapp/prePaidStatus!tmnlPrePaidQuery.action',
			    reader : new Ext.data.JsonReader({
						root : 'tmnlPrePaidQueryBeanList',
						totalProperty : 'totalCount'
					}, [
				    {name: 'orgName'},
				    {name: 'appNo'},
				    {name: 'consNo'},
				    {name: 'consName'},
				    {name: 'terminalAddr'},
				    {name: 'meterAssetNo'},
				    {name: 'executeDate'},
				    {name: 'executeStatus'},
				    {name: 'flowStatus'}
			   ])
		});
		
		queryResultStore.baseParams={
			           orgNo:orgNo,
			           orgType:orgType,
			           execDate:sendTime,
			           statType:statType
		           }; 
        queryResultStore.load({
     		params : {
				start : 0,
				limit : DEFAULT_PAGE_SIZE
			}
        });
        
		var queryResultGrid = new Ext.grid.GridPanel({
			   region:'center',
			   store : queryResultStore,
		       cm : queryResultCm,
		       stripeRows : true,	
		       autoScroll : true,
		       bbar : new Ext.ux.MyToolbar({
					store : queryResultStore,
					enableExpAll : true, // excel导出全部数据
				    expAllText : "全部",
					enableExpPage: true, // excel仅导出当前页
					expPageText : "当前页"
			   })
		});
		
		var tmnlprePaidExecStatusQueryWindow =new Ext.Window({
			layout:'fit',
			title:title,
	        modal:true,
	     	maximizable:true, 
			width:800,
			height:550,
			minWidth:600,
			minHeight:412,
	     	items:[queryResultGrid]
		});
		
	    tmnlprePaidExecStatusQueryWindow.show();
	}	
	
	//当日预付费控制执行统计明细窗口显示
	function prePaidCtrlExecQueryShow(orgNo,orgType,execDate,ctrlType,execStatus,title){
		var queryResultRowNum = new Ext.grid.RowNumberer({
			renderer : function(v, p, record, rowIndex) {
				var startRow = 0;
				if(queryResultStore && queryResultStore.lastOptions && queryResultStore.lastOptions.params){
					startRow = queryResultStore.lastOptions.params.start;
				}
				return startRow + rowIndex + 1;
			}
		});
	
		var queryResultCm = new Ext.grid.ColumnModel([  
	        queryResultRowNum,                                         
		    {header : '供电单位', sortable: true, dataIndex : 'orgName', align : 'center',
			    renderer : function(value) {
			   		 	if(null!=value){
							var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + value + '">'
									+ value + '</div>';
							return html;
				   		}
				   		else
				   		    return '';
			   		    }}, 
		    {header : '购电单号', sortable: true, dataIndex : 'appNo', align : 'center'},
		    {header : '用户编号', sortable: true, dataIndex : 'consNo', align : 'center'}, 
		    {header : '用户名称', sortable: true, dataIndex : 'consName', align : 'center',
			     renderer : function(value) {
			    	 	if(null!=value){
							var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + value + '">'
									+ value + '</div>';
							return html;
				    	}
				    	else
				    	     return '';
			    	    }},
		    {header : '终端地址', sortable: true, dataIndex : 'terminalAddr', align : 'center'}, 
		    {header : '控制类别', sortable: true, dataIndex : 'ctrlType', align : 'center'}, 
		    {header : '控制状态', sortable: true, dataIndex : 'ctrlStatus', align : 'center'},
		    {header : '控制时间', sortable: true, dataIndex : 'ctrlTime', align : 'center',
			     renderer : function(value) {
				    	 	if(null!=value){
								var html = '<div align = "center" ext:qtitle="控制时间" ext:qtip="' + value + '">'
										+ value + '</div>';
								return html;
					    	}
					    	else
					    	     return '';
				    	    }}
		]);
	
	    var queryResultStore = new Ext.data.Store({
	    	    url : './baseapp/prePaidStatus!buyCtrlExecQuery.action',
			    reader : new Ext.data.JsonReader({
						root : 'urgeFeeBeanList',
						totalProperty : 'totalCount'
					}, [
				    {name: 'orgName'},
				    {name: 'appNo'},
				    {name: 'consNo'},
				    {name: 'consName'},
				    {name: 'terminalAddr'},
				    {name: 'ctrlType'},
				    {name: 'ctrlStatus'},
				    {name: 'ctrlTime'}
			   ])
		});
	    queryResultStore.removeAll();
	    queryResultStore.baseParams={
		    orgNo:orgNo,
		    orgType:orgType,
		    execDate:execDate,
		    ctrlType:ctrlType,
		    execStatus:execStatus
	    }; 
	    queryResultStore.load({
	  		params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
	    });
	    
		var queryResultGrid = new Ext.grid.GridPanel({
			   region:'center',
			   store : queryResultStore,
		       cm : queryResultCm,
		       stripeRows : true,	
		       autoScroll : true,
		       bbar : new Ext.ux.MyToolbar({
					store : queryResultStore,
					enableExpAll : true, // excel导出全部数据
				    expAllText : "全部",
					enableExpPage: true, // excel仅导出当前页
					expPageText : "当前页"
			   })
		}); 
	
		var  prePaidCtrlExecQueryWindow =new Ext.Window({
			layout:'fit',
			title:title,
	        modal:true,
	     	maximizable:true, 
			width:800,
			height:550,
			minWidth:600,
			minHeight:412,
	     	items:[queryResultGrid]
		});
	    prePaidCtrlExecQueryWindow.show();
	}