var f_attention;
var f_dealWith;
//Ext.onReady(function(){
	excepText = new Ext.form.TextField({
			fieldLabel:'节点名',
			allowBlank:false,
			labelSeparator:'',
			name:'orgNo',
			readOnly:true,
			emptyText:'请选择左边节点',
			anchor:'90%',
			border:false
		});
		// 查询条件值
	var excepTextValue = ({
				nodeType : '',
				nodeValue : ''
			}

	);	
	excepDate1 = new Ext.form.DateField({
			fieldLabel : '从',
			format: 'Y-m-d',
			editable : false,
			allowBlank : false,
			width : 100,
			value: new Date().add(Date.DAY,-6),
			name : 'excepStartDate'
		});
	excepDate2 = new Ext.form.DateField({
			fieldLabel : '到',
			editable : false,
			allowBlank : false,
			format: 'Y-m-d',
			width : 100,
			value: new Date(),
			name : 'excepEndDate'
		});
//	if(excep_startDate!=null){
//		excepDate1.setValue(excep_startDate);
//	}
//	if(excep_endDate!=null){
//		excepDate2.setValue(excep_endDate);
//	}
//	if(excep_compName!=null){
//		excepText.setValue(excep_compName);
//	}
//	var excepRadioForm = new Ext.form.RadioGroup({
//			width : 255,
//			items : [new Ext.form.Radio({
//						boxLabel : '严重',
//						name : 'queryType',
//						inputValue : '1'
//					}),
//					new Ext.form.Radio({
//						boxLabel : '次要',
//						name : 'queryType',
//						inputValue : '2'
//					}),
//					new Ext.form.Radio({
//						boxLabel : '一般',
//						name : 'queryType',
//						inputValue : '3'
//					}),
//					new Ext.form.Radio({
//						boxLabel : '全部',
//						name : 'queryType',
//						checked : true,
//						inputValue : '4'
//					})]
//		});
//	if(excep_seriousLevel!=null&&excep_seriousLevel!=''){
//		excepRadioForm.setValue(excep_seriousLevel);
//	}
		
//	var comDeviceStore = new Ext.data.JsonStore({
//		url : "baseapp/deviceMonitor!getDeviceFactory.action",
//		fields : [ 'factoryCode', 'factoryName' ],
//		root : 'vwFactoryList'
//	});
//	var comboDevice = new Ext.form.ComboBox({
//        store: comDeviceStore,
//        displayField:'factoryName',
//        valueField:'factoryCode',
//        typeAhead: true,
//        anchor : '78% 1%',
//        mode: 'remote',
//        fieldLabel:'制造商',
//        labelSeparator:'',
//        forceSelection: true,
//        triggerAction: 'all',
//        emptyText:'全部',
//        selectOnFocus:true,
//        width:220
//    });
	var excepQueryPanel = new Ext.form.FormPanel({
				labelAlign : 'right',
				frame : false,
				height : 40,
				region:'north',
				bodyStyle : 'padding:10px 0px 0px 0px',
				layout : 'table',
				border : false,
				items : [{
							width : 300,
							border : false,
							labelWidth : 60,
							layout : 'form',
							labelAlign : 'right',
							items : [excepText]
						}, {
							width : 150,
							border : false,
							labelWidth : 20,
							layout : 'form',
							labelAlign : 'right',
							items : [excepDate1]
						}, {
							width : 150,
							border : false,
							labelWidth : 20,
							layout : 'form',
							labelAlign : 'right',
							items : [excepDate2]
						}, {
							width : 150,
							border : false,
							layout : 'form',
							items : [{
								xtype : 'button',
								text : '查询',
								width : 70,
								handler : queryExcepData
							}]
		}]});
	function queryExcepData(){
		if (!excepQueryPanel.getForm().isValid()) {
			Ext.Msg.alert('提示', '请完整填写查询条件');
			return true;
		}
//		var myMask = new Ext.LoadMask(excepViewPanel.getId(), {
//			msg : "加载中..."
//		});
//		myMask.show();
		excepStore.proxy = new Ext.data.HttpProxy({
			url : './runman/abnormalhandle/deviceException!queryDeException.action'
			}),
		excepStore.baseParams = {
			start : 0,
			limit : DEFAULT_PAGE_SIZE,
			// 检索开始时间
			startDate : excepDate1.getValue(),
			// 检索结束时间
			endDate : excepDate2.getValue(),
			// 工单状态
			nodeType : excepTextValue.nodeType,
			// 异常类型
			nodeValue : excepTextValue.nodeValue
		}
		excepStore.load();
//		excepStore.on('load', function() {
//			myMask.hide();
//		});
//		excepStore.on('loadexception', function() {
//			myMask.hide();
//		})
//		if(!excepQueryPanel.getForm().isValid()){
//			Ext.Msg.alert('提示','请完整填写查询条件');
//			return true;
//		}
//    	excepQueryPanel.getForm().submit({
//    		method : 'post',
//    		url:'baseapp/deviceMonitor!getDeviceExcepInfo.action',
//			success:function(form, action) {
//				if(action!=null){
//					excepStore.loadData(action.result);
//				}
//			}
//    	});
    }
    
	function queryExcepDataById(exceptionId){
//		var myMask = new Ext.LoadMask(excepViewPanel.getId(), {
//			msg : "加载中..."
//		});
//		myMask.show();
		excepStore.proxy = new Ext.data.HttpProxy({
			url : './runman/abnormalhandle/deviceException!queryExceptionInfoById.action'
		})
		excepStore.baseParams = {
			// 异常id
			tmnlExceptionId : exceptionId
		}
		excepStore.load();
//		excepStore.on('load', function() {
//			myMask.hide();
//		});
//		excepStore.on('loadexception', function() {
//			myMask.hide();
//		})
//		if(!excepQueryPanel.getForm().isValid()){
//			Ext.Msg.alert('提示','请完整填写查询条件');
//			return true;
//		}
//    	excepQueryPanel.getForm().submit({
//    		method : 'post',
//    		url:'baseapp/deviceMonitor!getDeviceExcepInfo.action',
//			success:function(form, action) {
//				if(action!=null){
//					excepStore.loadData(action.result);
//				}
//			}
//    	});
    }
    
    //左边树监听时点击树节点调用的方法
    function deviceClick(p, node, e) {
			var obj = node.attributes.attributes;
			var parentObj = node.parentNode.attributes.attributes;
			var type = node.attributes.type;
			if(type == 'org'){
				if(obj.orgType == '02'){
					return true;
				}
				excepText.setValue(node.text);
				excepTextValue.nodeType = type;
				excepTextValue.nodeValue = obj.orgNo;
				orgType = obj.orgType;
			}else if(type == 'usr'){
				excepText.setValue(obj.consName);
				excepTextValue.nodeType = type;
				excepTextValue.nodeValue = obj.tmnlAssetNo;
			}else if(type == 'line'){
				excepText.setValue(node.text);
				excepTextValue.nodeType = type;
				excepTextValue.nodeValue = obj.lineId;
			}else if(type == 'cgp' || type=='ugp'){
				excepText.setValue(node.text);
				excepTextValue.nodeType = type;
				excepTextValue.nodeValue = obj.groupNo;
			}else if(type == 'sub'){
				excepText.setValue(node.text);
				excepTextValue.nodeType = type;
				excepTextValue.nodeValue = obj.subsId;
			}else {
				return true;
			}
    }
    
    //左边树监听时选择用户调用的方法
    function deviceUserGridSelect(cm,row,record) {
	   		excepText.setValue(record.get('consName'));
	   		excepTextValue.nodeType = 'usr';
			excepTextValue.nodeValue = record.get('consNo');
    }
//	var leftTreeListener = new LeftTreeListener({
//		modelName : '主站分析异常查询',
//		processClick : function(p, node, e) {
//			var obj = node.attributes.attributes;
//			var parentObj = node.parentNode.attributes.attributes;
//			var type = node.attributes.type;
//			if(type == 'org'){
//				if(obj.orgType == '02'){
//					return true;
//				}
//				excepText.setValue(node.text);
//				excepTextValue.nodeType = type;
//				excepTextValue.nodeValue = obj.orgNo;
//				orgType = obj.orgType;
//			}else if(type == 'usr'){
//				excepText.setValue(obj.consName);
//				excepTextValue.nodeType = type;
//				excepTextValue.nodeValue = obj.tmnlAssetNo;
//			}else if(type == 'line'){
//				excepText.setValue(node.text);
//				excepTextValue.nodeType = type;
//				excepTextValue.nodeValue = obj.lineId;
//			}else if(type == 'cgp' || type=='ugp'){
//				excepText.setValue(node.text);
//				excepTextValue.nodeType = type;
//				excepTextValue.nodeValue = obj.groupNo;
//			}else if(type == 'sub'){
//				excepText.setValue(node.text);
//				excepTextValue.nodeType = type;
//				excepTextValue.nodeValue = obj.subsId;
//			}else {
//				return true;
//			}
//	   	},
//	   	processUserGridSelect:function(cm,row,record){
//	   		excepText.setValue(record.get('consName'));
//	   		excepTextValue.nodeType = 'usr';
//			excepTextValue.nodeValue = record.get('consNo');
//	    }
//	});
	var systemRN = new Ext.grid.RowNumberer({
				// header : '序',
				renderer : function(v, p, record, rowIndex) {
					var startRow = 0;
					if (excepStore && excepStore.lastOptions
							&& excepStore.lastOptions.params) {
						startRow = excepStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			})		
	var excepCm = new Ext.grid.ColumnModel(
		[systemRN,
//		{header:'异常ID',dataIndex:'tmnlExceptionId',align:'center',sortable:true,hidden:true},
//		{header:'供电单位编号',dataIndex:'orgNo',align:'center',sortable:true},
//		{header:'用户编号',dataIndex:'consNo',align:'center',sortable:true},
		{header:'供电单位',dataIndex:'orgName',align:'center',sortable:true,width : 100,
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">' + val
					+ '</div>';
			return html;
		}},
		{header:'用户名称',dataIndex:'consName',align:'center',sortable:true,width : 100,
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">' + val
					+ '</div>';
			return html;
		}},
//		{header:'终端资产编号',dataIndex:'tmnlAssetNo',align:'center',sortable:true},
		{header:'终端地址',dataIndex:'terminalAddr',align:'center',width : 60,sortable:true},
		{header:'异常日期',dataIndex:'exceptDate',align:'center',width : 70,sortable:true,
		renderer : function(val) {
			return Date.parseDate(val,'Y-m-dTH:i:s').format('Y-m-d');
		}},
		{header:'异常名称',dataIndex:'eventName',align:'center',sortable:true,
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="异常名称" ext:qtip="' + val + '">' + val
					+ '</div>';
			return html;
		}},
//		{header:'异常类型',dataIndex:'exceptCode',align:'center',sortable:true},
		{header:'异常来源',dataIndex:'exceptOrigin',align:'center',width : 80,sortable:true},
//		{header:'SIM卡号',dataIndex:'simNo',align:'center',sortable:true},
		{header:'生产厂家',dataIndex:'factoryCode',align:'center',sortable:true},
//		{header:'备注',dataIndex:'remark',align:'center',sortable:true},
//		{header:'异常恢复日期',dataIndex:'resumeDate',align:'center',sortable:true},
		{header:'流程状态',dataIndex:'flowStatusCode',align:'center',sortable:true,renderer : function(val) {
			switch (val) {
				case '00' :
				var html = '<div align = "left">' + '新异常'
					+ '</div>';
				return html;
				case '02' :
				var html = '<div align = "left">' + '营销处理中'
					+ '</div>';
				return html;
				case '03' :
				var html = '<div align = "left">' + '正常归档'
					+ '</div>';
				return html;
				case '04' :
				var html = '<div align = "left">' + '误报确认'
					+ '</div>';
				return html;
				case '06' :
				var html = '<div align = "left">' + '挂起'
					+ '</div>';
				return html;
				case '07' :
				var html = '<div align = "left">' + '本地处理中'
					+ '</div>';
				return html;
				case '08' :
				var html = '<div align = "left">' + '强制归档'
					+ '</div>';
				return html;
			}
		}},
		{header :'相关操作',	renderer : function(v, m, rec){
			var html = "<a href='javascript:'onclick='f_attention();"
							+  "'>关注</a>"	+ "&nbsp;&nbsp;&nbsp;"
							+ "<a href='javascript:'onclick='f_dealWith();"
							+ "'>处理</a>";
					return html;
		}
		}
//		{header:'流程状态明细',dataIndex:'flowStatusDetail',align:'center',sortable:true},
//		{header:'异常结论编码',dataIndex:'conclusionNo',align:'center',sortable:true},
//		{header:'流程标记',dataIndex:'flowFlag',align:'center',sortable:true},
//		{header:'处理人',dataIndex:'handlerNo',align:'center',sortable:true},
//		{header:'处理日期',dataIndex:'handleDate',align:'center',sortable:true},
//		{header:'工单号',dataIndex:'jobOrder',align:'center',sortable:true},
//		{header:'派发人',dataIndex:'distributionNo',align:'center',sortable:true},
//		{header:'派发日期',dataIndex:'distributionDate',align:'center',sortable:true},
//		{header:'截止日期',dataIndex:'closingDate',align:'center',sortable:true},
	]);
	var excepStore  = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : './runman/abnormalhandle/deviceException!queryDeException.action'
			}),
		reader : new Ext.data.JsonReader({
			root : 'exceptionInfo',
			totalProperty : 'totalCount'
			}, [{name:'tmnlExceptionId'},
			    {name:'orgNo'},
			    {name:'consNo'},
			    {name:'tmnlAssetNo'},
			    {name:'terminalAddr'},
			    {name:'exceptDate'},
			    {name:'exceptCode'},
			    {name:'exceptOrigin'},
			    {name:'simNo'},
			    {name:'factoryCode'},
			    {name:'remark'},
			    {name:'resumeDate'},
			    {name:'flowStatusCode'},
			    {name:'flowStatusDetail'},
			    {name:'conclusionNo'},
			    {name:'flowFlag'},
			    {name:'handlerNo'},
			    {name:'handleDate'},
			    {name:'jobOrder'},
			    {name:'distributionNo'},
			    {name:'distributionDate'},
			    {name:'closingDate'},
			    {name:'eventName'},
			    {name:'consName'},
			    {name:'orgName'}
			])
		});
	var excepGrid = new Ext.grid.GridPanel({
		store : excepStore,
		region : 'center',
		cm : excepCm,
//		stripeRows : true,
		height:400,
		autoScroll : true,
		bbar : new Ext.ux.MyToolbar({
				store:excepStore
			})
//		viewConfig : {
//				forceFit : false
//			}
	});

//	var bookTplMarkup = [
//		'Title: <a href="{DetailPageURL}" target="_blank">{Title}</a><br/>',
//		'异常ID:{tmnlExceptionId}<br/>',
//		'供电单位编号:{orgNo}<br/>',
//		'用户编号:{consNo}<br/>',
//		'终端资产编号:{tmnlAssetNo}<br/>',
//		'终端地址码:{terminalAddr}<br/>',
//		'异常日期:{exceptDate}<br/>',
//		'异常类型:{exceptCode}<br/>',
//		'异常来源:{exceptOrigin}<br/>',
//		'SIM卡号:{simNo}<br/>',
//		'生产厂家:{factoryCode}<br/>',
//		'备注:{remark}<br/>',
//		'异常恢复日期:{resumeDate}<br/>',
//		'流程状态:{flowStatusCode}<br/>',
//		'流程状态明细:{flowStatusDetail}<br/>',
//		'异常结论编码:{conclusionNo}<br/>',
//		'流程标记:{flowFlag}<br/>',
//		'处理人:{handlerNo}<br/>',
//		'处理日期:{handleDate}<br/>',
//		'工单号:{jobOrder}<br/>',
//		'派发人:{distributionNo}<br/>',
//		'派发日期:{distributionDate}<br/>',
//		'截止日期:{closingDate}<br/>',
//		'异常名称:{eventName}<br/>',
//		'用户名称:{consName}<br/>',
//		'供电单位:{orgName}<br/>'
//	];
//	var bookTpl = new Ext.Template(bookTplMarkup);
	var consFormField1 = new Ext.Panel({
		layout : 'form',
//		region : 'south',
//		height : 300,
		columnWidth : 0.33,
		border : false,
		defaults : {
		anchor : '95%'
		},
		labelAlign : 'right',
		items : [new Ext.form.TextField({
				fieldLabel : '异常ID',
				name : 'tmnlExceptionId',
				readOnly : true
//				emptyText : '请输入tmnlExceptionId '
			}),  new Ext.form.TextField({
				fieldLabel : '用户编号',
				name : 'consNo',
				readOnly : true
//				emptyText : '请输入consNo '
			}), new Ext.form.TextField({
				fieldLabel : '异常类型',
				name : 'exceptCode',
				emptyText : '请输入异常类型'
			}), new Ext.form.TextField({
				fieldLabel : '终端地址码',
				readOnly : true,
				name : 'terminalAddr'
//				emptyText : '请输入terminalAddr '
			}), new Ext.form.TextField({
				fieldLabel : 'SIM卡号',
				name : 'simNo',
				readOnly : true
//				emptyText : '请输入simNo '
			})
//			new Ext.form.TextField({
//				fieldLabel : '流程状态明细',
//				name : 'flowStatusDetail',
//				readOnly : true
////				emptyText : '请输入flowStatusDetail'
//			}),  new Ext.form.TextField({
//				fieldLabel : '处理人',
//				name : 'handlerNo',
//				readOnly : true
////				emptyText : '请输入handlerNo '
//			}),  new Ext.form.TextField({
//				fieldLabel : '派发人',
//				name : 'distributionNo',
//				readOnly : true
////				emptyText : '请输入distributionNo '
//			})
			]});
	
	var consFormField2 = new Ext.Panel({
		layout : 'form',
//		region : 'south',
//		height : 300,
		columnWidth : 0.33,
		border : false,
		defaults : {
		anchor : '95%'
		},
		labelAlign : 'right',
		items : [new Ext.form.TextField({
				fieldLabel : '供电单位编号',
				name : 'orgNo',
				readOnly : true
//				emptyText : '请输入orgNo '
			}),  new Ext.form.TextField({
				fieldLabel : '用户名称',
				name : 'consName',
				readOnly : true
//				emptyText : '请输入consName '
			}), new Ext.form.TextField({
				fieldLabel : '异常来源',
				name : 'exceptOrigin',
				emptyText : '请输入异常来源'
			}), new Ext.form.TextField({
				fieldLabel : '终端资产编号',
				name : 'tmnlAssetNo',
				readOnly : true
//				emptyText : '请输入tmnlAssetNo '
			}),new Ext.form.TextField({
				fieldLabel : '流程状态',
				name : 'flowStatusCode',
				readOnly : true
//				emptyText : '请输入flowStatusCode '
			})
//			, new Ext.form.TextField({
//				fieldLabel : '工单号',
//				name : 'jobOrder',
//				readOnly : true
////				emptyText : '请输入jobOrder '
//			}), new Ext.form.DateField({
//				fieldLabel : '派发日期',
//				name : 'distributionDate',
//				format : 'Y-m-d',
//				editable : false
////				emptyText : '请输入distributionDate '
//			}), new Ext.form.DateField({
//				fieldLabel : '异常恢复日期',
//				name : 'resumeDate',
//				format : 'Y-m-d',
//				editable : false
////				emptyText : '请输入resumeDate '
//			}), new Ext.form.TextField({
//				fieldLabel : '异常结论编码',
//				name : 'conclusionNo',
//				readOnly : true
////				emptyText : '请输入conclusionNo '
//			})
			]
		})
		
	var consFormField3 = new Ext.Panel({
		layout : 'form',
//		region : 'south',
//		height : 300,
		columnWidth : 0.33,
		border : false,
		defaults : {
		anchor : '95%'
		},
		labelAlign : 'right',
		items : [new Ext.form.TextField({
				fieldLabel : '供电单位',
				name : 'orgName',
				readOnly : true
//				emptyText : '请输入orgName '
			}),  new Ext.form.TextField({
				fieldLabel : '异常名称',
				name : 'eventName',
				emptyText : '请输入异常名称'
			}), new Ext.form.DateField({
				fieldLabel : '异常日期',
				name : 'exceptDate',
				format : 'Y-m-d',
				emptyText : '请输入异常日期'
			}), new Ext.form.TextField({
				fieldLabel : '生产厂家',
				name : 'factoryCode',
				readOnly : true
//				emptyText : '请输入factoryCode '
			}),
			new Ext.form.TextField({
				fieldLabel : '备注',
				name : 'remark',
				emptyText : '请输入备注'
			})
//			, new Ext.form.DateField({
//				fieldLabel : '截止日期',
//				name : 'closingDate',
//				format : 'Y-m-d',
//				editable : false
////				emptyText : '请输入closingDate '
//			})
//			, new Ext.form.TextField({
//				fieldLabel : '流程标记',
//				name : 'flowFlag',
//				readOnly : true
////				emptyText : '请输入flowFlag '
//			})
//			, new Ext.form.DateField({
//				fieldLabel : '处理日期',
//				name : 'handleDate',
//				format : 'Y-m-d',
//				editable : false
////				emptyText : '请输入handleDate '
//			})
			]
		})
	
	var consForm = new Ext.FormPanel({
				border : false,
				height : 230,
				region : 'south',
				bodyStyle : 'padding: 10px 0px 0px 10px',
				title : '主站分析异常明细',
				layout : 'column',
				items : [consFormField1, consFormField2, consFormField3]
			});
	
	var ct = new Ext.Panel({
		region : 'center',
		tbar : [{
			xtype:'label',
			html : "<font font-weight:bold;>主站分析异常</font>"
		}],
		layout: 'border',
		items: [
			excepGrid,
			consForm
		]
	})

	//点击grid行，显示单条异常明细
	var cpRowSelect = function(sm, num, rec) {
		var record = excepStore.getAt(num);
		if (!Ext.isEmpty(record))
			consForm.getForm().setValues(record.data);

	}
	excepGrid.getSelectionModel().on('rowselect', cpRowSelect);
	
	f_attention = function() {
		var record = excepGrid.getSelectionModel().getSelected();
		if (!!record.get('flowStatusCode')) {
			Ext.MessageBox.alert("提示", "该异常已在处理中");
			return;
		}
		Ext.getBody().mask("正在处理...");
		Ext.Ajax.request({
			url : './runman/abnormalhandle/deviceException!attentionEvent.action',
			params : {
				tmnlExceptionId : record.get('tmnlExceptionId'),
				eventCode : record.get('exceptCode'),
				eventName : record.get('eventName')
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				var isError = result.success;
				Ext.getBody().unmask();
				if (!isError) {
					Ext.MessageBox.alert("提示", "关注失败！");
				} else {
					//成功时改变异常状态
					record.set('flowStatusCode', '00');
//					record.set('jobOrder', result.eventNo);
					record.commit();
					Ext.MessageBox.alert("提示", "关注成功！");
				}
			}
		});
}

	f_dealWith = function() {
		var record = excepGrid.getSelectionModel().getSelected();
		if (record.get('flowStatusCode')) {
			window.fromId = record.get('tmnlExceptionId');
			window.eventType = '3';
			openTab("现场设备异常", "./runMan/abnormalHandle/deviceAbnormal.jsp",
						false, "deviceAbnormal");
			return;
		}
		
		Ext.getBody().mask("正在处理...");
		Ext.Ajax.request({
			url : './runman/abnormalhandle/deviceException!attentionEvent.action',
			params : {
				tmnlExceptionId : record.get('tmnlExceptionId'),
				eventCode : record.get('exceptCode'),
				eventName : record.get('eventName')
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				window.fromId = record.get('tmnlExceptionId');
				window.eventType = '3';
				record.set('flowStatusCode', '00');
				// record.set('jobOrder', result.eventNo);
				record.commit();
				openTab("现场设备异常", "./runMan/abnormalHandle/deviceAbnormal.jsp",
						false, "deviceAbnormal");
				Ext.getBody().unmask();
				if (!isError) {
					Ext.MessageBox.alert("提示", "处理失败！");
				} else {
					Ext.MessageBox.alert("提示", "处理成功！");
				}
			}
		});
}

	//主站分析异常查询
	function searchByExceptionId() {
		queryExcepDataById(window.exceptionId);
		excepStore.on('datachanged', function() {
			if (excepStore.getCount() == 1) {
				var rec = excepStore.getAt(0);
				excepText.setValue(rec.get('consName'));
				excepTextValue.nodeType = 'usr';
				excepTextValue.nodeValue = rec.get('tmnlAssetNo');
				excepDate1.setValue(rec.get('exceptDate'));
				excepDate2.setValue(rec.get('exceptDate'));
			}
		});
		window.exceptionId = '';
	}

//	//页面非激活状态下调用方法
//	if (!Ext.isEmpty(window.exceptionId)) {
//		queryExcepDataById(window.exceptionId);
//		excepStore.on('datachanged', function() {
//			if (excepStore.getCount() == 1) {
//				var rec = excepStore.getAt(0);
//				excepText.setValue(rec.get('consName'));
//				excepTextValue.nodeType = 'usr';
//				excepTextValue.nodeValue = rec.get('tmnlAssetNo');
//				excepDate1.setValue(rec.get('exceptDate'));
//				excepDate2.setValue(rec.get('exceptDate'));
//			}
//		});
//		window.exceptionId = '';
//	}
//	//页面为激活状态时调用此方法
//	Ext.getCmp('主站分析异常查询').on('activate', function() {
//		if (!Ext.isEmpty(window.exceptionId)) {
//			queryExcepDataById(window.exceptionId);
//			excepStore.on('datachanged', function() {
//				if (excepStore.getCount() == 1) {
//					var rec = excepStore.getAt(0);
//					excepText.setValue(rec.get('consName'));
//					excepTextValue.nodeType = 'usr';
//					excepTextValue.nodeValue = rec.get('tmnlAssetNo');
//					excepDate1.setValue(rec.get('exceptDate'));
//					excepDate2.setValue(rec.get('exceptDate'));
//				}
//			});
//		}
//		window.exceptionId = '';
//	});
//	
//	var excepViewPanel = new Ext.Panel({
//		bodyStyle:'padding:5px 5px 5px 5px',
//		layout: 'border' ,
//		items: [excepQueryPanel,ct],
//		border : false
//	});
//	renderModel(excepViewPanel,'主站分析异常查询');
//	queryExcepData();
//});