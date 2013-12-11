Ext.onReady(function(){
	gtText = new Ext.form.TextField({
			fieldLabel:'节点名',
			allowBlank:false,
			labelSeparator:'',
			name:'orgNo',
			emptyText:'请选择左边节点',
			anchor:'90%',
			readOnly : true,
			border:false
		});
		// 查询条件值
	var gtTextValue = ({
				nodeType : '',
				nodeValue : ''
			}

	);	
	gtStartDate = new Ext.form.DateField({
			fieldLabel : '从',
			format: 'Y-m-d',
			allowBlank : false,
			editable : false,
			width : 100,
			value: new Date().add(Date.DAY,-1),
			name : 'taskStartDate'
		});
	gtEndDate = new Ext.form.DateField({
			fieldLabel : '到',
			allowBlank : false,
			format: 'Y-m-d',
			editable : false,
			width : 100,
			value: new Date(),
			name : 'taskEndDate'
		});

	var taskTypeComStore = new Ext.data.ArrayStore({
		fields : ['taskTypeCode', 'taskTypeName'],
		data : [[0, "自动任务"], [1, "手动任务"]]
//		data : [[1, "系统异常"], [2, "终端上报事件"], [3, "主站分析终端故障"],
//						[4, "主站分析用电异常"], [5, "主站分析数据异常"]]
	})
    
    var  taskTypeCombobox = new Ext.form.ComboBox({
				fieldLabel : '任务类型',
				store : taskTypeComStore,
				triggerAction : 'all',
//				editable : false,
				mode : 'local',
				valueField : 'taskTypeCode',
				displayField : 'taskTypeName',
				value : '0',
				anchor : '90%',
				emptyText : '任务类型',
				editable : false,
				selectOnFocus : true
    })
	
	var gtQueryPanel = new Ext.form.FormPanel({
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
							items : [gtText]
						}, {
							width : 150,
							border : false,
							labelWidth : 20,
							layout : 'form',
							labelAlign : 'right',
							items : [gtStartDate]
						}, {
							width : 150,
							border : false,
							labelWidth : 20,
							layout : 'form',
							labelAlign : 'right',
							items : [gtEndDate]
						}, {
							width : 160,
							border : false,
							labelWidth : 60,
							layout : 'form',
							labelAlign : 'right',
							items : [taskTypeCombobox]
						}, {
							width : 150,
							border : false,
							layout : 'form',
							items : [{
								xtype : 'button',
								text : '查询',
								width : 70,
								handler : queryGatherTaskInfo
							}]
		}]});
	function queryGatherTaskInfo(){
		if (!gtQueryPanel.getForm().isValid()) {
			Ext.Msg.alert('提示', '请完整填写查询条件');
			return true;
		}
//		var myMask = new Ext.LoadMask(gtViewPanel.getId(), {
//			msg : "加载中..."
//		});
//		myMask.show();
		gtStore.proxy = new Ext.data.HttpProxy({
			url : './baseapp/gatherTaskQuery!queryGatherTaskInfo.action'
			}),
		gtStore.baseParams = {
			start : 0,
			limit : DEFAULT_PAGE_SIZE,
			// 检索开始时间
			startDate : gtStartDate.getValue(),
			// 检索结束时间
			endDate : gtEndDate.getValue().add(Date.DAY,1),
			// 节点类型
			nodeType : gtTextValue.nodeType,
			// 节点值
			nodeValue : gtTextValue.nodeValue,
			//任务类型
			taskType : taskTypeCombobox.getValue()
		}
		gtStore.load();
//		gtStore.on('load', function() {
//			myMask.hide();
//		});
//		gtStore.on('loadexception', function() {
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
//					gtStore.loadData(action.result);
//				}
//			}
//    	});
    }
    
    
	var leftTreeListener = new LeftTreeListener({
		modelName : '采集任务执行统计',
		processClick : function(p, node, e) {
			var obj = node.attributes.attributes;
			var parentObj = node.parentNode.attributes.attributes;
			var type = node.attributes.type;
			if(type == 'org'){
				if(obj.orgType == '02'){
					return true;
				}
				gtText.setValue(node.text);
				gtTextValue.nodeType = type;
				gtTextValue.nodeValue = obj.orgNo;
				orgType = obj.orgType;
			}else if(type == 'usr'){
				gtText.setValue(obj.consName);
				gtTextValue.nodeType = type;
				gtTextValue.nodeValue = obj.tmnlAssetNo;
			}else if(type == 'line'){
				gtText.setValue(node.text);
				gtTextValue.nodeType = type;
				gtTextValue.nodeValue = obj.lineId;
			}else if(type == 'cgp' || type=='ugp'){
				gtText.setValue(node.text);
				gtTextValue.nodeType = type;
				gtTextValue.nodeValue = obj.groupNo;
			}else if(type == 'sub'){
				gtText.setValue(node.text);
				gtTextValue.nodeType = type;
				gtTextValue.nodeValue = obj.subsId;
			}else {
				return true;
			}
	   	},
	   	processUserGridSelect:function(cm,row,record){
	   		gtText.setValue(record.get('consName'));
	   		gtTextValue.nodeType = 'usr';
			gtTextValue.nodeValue = record.get('consNo');
	    }
	});
	var systemRN = new Ext.grid.RowNumberer({
				// header : '序',
				renderer : function(v, p, record, rowIndex) {
					var startRow = 0;
					if (gtStore && gtStore.lastOptions
							&& gtStore.lastOptions.params) {
						startRow = gtStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			})		
	var gtCm = new Ext.grid.ColumnModel(
		[systemRN,
		{header:'供电单位',dataIndex:'orgName',align:'center',sortable:true,width : 120,
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">' + val
					+ '</div>';
			return html;
		}},
		{header:'用户编号',dataIndex:'consNo',align:'center',sortable:true,width : 100,
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="用户编号" ext:qtip="' + val + '">' + val
					+ '</div>';
			return html;
		}},
		{header:'用户名称',dataIndex:'consName',align:'center',sortable:true,width : 120,
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">' + val
					+ '</div>';
			return html;
		}},
		{header:'终端资产号',dataIndex:'cisAssetNo',align:'center',sortable:true,width : 110,
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="终端资产号" ext:qtip="' + val + '">' + val
					+ '</div>';
			return html;
		}},
		{header:'任务号',dataIndex:'taskNo',align:'center',sortable:true,width : 60},
		{header:'任务ID',dataIndex:'taskId',align:'center',sortable:true,width : 60},
		{header:'任务名称',dataIndex:'templateName',align:'center',sortable:true,width : 120,
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="任务名称" ext:qtip="' + val + '">' + val
					+ '</div>';
			return html;
		}},
//		{header:'任务类型',dataIndex:'taskType',align:'center',sortable:true,width : 101},
		{header:'应执行次数',dataIndex:'masterR',align:'center',sortable:true,width : 100
//		,renderer : function(val) {
//			var val = val * (gtEndDate.getValue().getMilliseconds() - gtStartDate.getValue().getMilliseconds()) / 24*60*60 ;
//			return val;
//		}
		},
		{header:'成功次数',dataIndex:'sucessCount',align:'center',sortable:true,width : 80},
		{header:'失败次数',dataIndex:'failueCount',align:'center',sortable:true,width : 80}
	]);
	var gtStore  = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : './baseapp/gatherTaskQuery!queryGatherTaskInfo.action'
			}),
		reader : new Ext.data.JsonReader({
			root : 'rs',
			totalProperty : 'totalCount'
			}, [{name:'taskId'},
			    {name:'taskType'},
			    {name:'sucessCount'},
			    {name:'failueCount'},
			    {name:'cisAssetNo'},
			    {name:'masterR'},
			    {name:'taskNo'},
			    {name:'templateName'},
			    {name:'consName'},
			    {name:'consNo'},
			    {name:'orgName'}
			])
		});
	var gtGrid = new Ext.grid.GridPanel({
		title : '采集任务执行统计明细',
		store : gtStore,
		region : 'center',
		cm : gtCm,
		autoScroll : true,
		bbar : new Ext.ux.MyToolbar({
				store:gtStore
			})
	});

	var gtViewPanel = new Ext.Panel({
		bodyStyle:'padding:5px 5px 5px 5px',
		layout: 'border' ,
		items: [gtQueryPanel,gtGrid],
		border : false
	});
	renderModel(gtViewPanel,'采集任务执行统计');
});