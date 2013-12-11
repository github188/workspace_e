var flowStatusCode;

//-------------------------------------------------------------查询 begin	
	//开始日期
	var taskList_startDate = new Ext.form.DateField({
		id : 'taskList_startDate',
		fieldLabel : '从',
		name : 'taskList_startDate',
//		width : 100,
		editable : false,
		format: 'Y-m-d',
	    labelSeparator:'',
	    allowBlank:false,
	    value : new Date().add(Date.DAY, -6)
    });
	
	//结束日期
	var taskList_endDate = new Ext.form.DateField({
		id : 'taskList_endDate',
		fieldLabel : '到',
		name : 'taskList_endDate',
//		width : 100,
		editable : false,
		format: 'Y-m-d',
	    labelSeparator:'',
	    allowBlank:false,
	    value:new Date()
    });
    
    	var taskDetailsOrgNo = new Ext.form.TextField({
				    fieldLabel:'供电单位编号',
				    id :'detailsOrgNo',
				    hidden : false,
				    hideLabel : true,
				    anchor : '95%'
			    })
	
    /* 查询异常 类型*/
			
	var typeComStore = new Ext.data.ArrayStore({
		fields : ['eventTypeCode', 'eventTypeName'],
		data : [[1, "系统异常"], [2, "终端上报事件"], [3, "主站分析终端故障"],
						[4, "主站分析用电异常"], [5, "主站分析数据异常"]]
	})
    
    var taskListCombobox = new Ext.form.ComboBox({
    			id : 'event_type',
				fieldLabel : '异常类型',
				store : typeComStore,
				triggerAction : 'all',
				editable : false,
				mode : 'local',
				valueField : 'eventTypeCode',
				displayField : 'eventTypeName',
				anchor : '90%',
				emptyText : '终端事件类型',
				selectOnFocus : true
    })
 
        /* 查询流程状态 */

    var statusComStore = new Ext.data.ArrayStore({
		fields : ['statusCode', 'statusName'],
		data : [["00", "新工单"], ["02", "营销处理中"], ["03", "正常归档"],
						["04", "误报确认"], ["06", "挂起"], ["07", "本地处理中"],
						["08", "强制归档"]]
    })
	var statusCombobox = new Ext.form.ComboBox({
		id :'event_status',
		fieldLabel : '流程状态',
		store : statusComStore,
		triggerAction : 'all',
		editable : false,
		mode : 'local',
		valueField : 'statusCode',
		displayField : 'statusName',
		anchor : '90%',
		emptyText : '流程状态',
		selectOnFocus : true
    })
    
	
	var taskList_DetailsParaPanel = new Ext.Panel({
		border:false,
		layout:'table',
		region : 'north',
		height:40,
		bodyStyle:'padding:10px 0px',
		items:[{
			border:false,
			labelAlign:'right',
			labelSeparator:'',
			labelWidth : 30,
			padding : '0px 0px 0px 10px',
			layout:'form',
			width : 145,
			items:[taskList_startDate]
		},{
			border:false,
			labelAlign:'right',
			labelSeparator:'',
			labelWidth : 30,
			layout:'form',
			width : 145,
			items:[taskList_endDate]
		},{
			border:false,
			padding : '0px 0px 0px 10px',
			layout:'form',
			labelWidth : 50,
			labelAlign:'right',
			width : 220,
			items:[taskListCombobox]
		},{
			border:false,
			padding : '0px 0px 0px 10px',
			layout:'form',
			labelWidth : 50,
			hidden : true,
			labelAlign:'right',
			width : 180,
			items:[taskDetailsOrgNo]
		},{
			border:false,
			padding : '0px 0px 0px 10px',
			layout:'form',
			labelWidth : 50,
			labelAlign:'right',
			width : 180,
			items:[statusCombobox]
		},{
			border:false,
			layout:'form',
			padding : '0px 0px 0px 10px',
			items:[{
				xtype:'button',
				text:'查询',
				width:70,
				handler : function(){
					var dateFrom = taskList_startDate.getValue();
							var dateTo = taskList_endDate.getValue();
							if (dateFrom > dateTo) {
								Ext.MessageBox.alert("提示", "开始时间不能大于结束时间！");
								return;
							}
					Ext.getCmp("detailsOrgNo").setValue("");
					searchEventinfo();
				}
			}]
		}]
	});

	//查询工单列表
	function searchEventinfo() {
//		var myMask = new Ext.LoadMask(taskStatPanel.getId(), {
//			msg : "加载中..."
//		});
//		myMask.show();
		listStore.baseParams = {
			start : 0,
			limit : DEFAULT_PAGE_SIZE,
			// 检索开始时间
			startDate : taskList_startDate.getValue(),
			// 检索结束时间
			endDate : taskList_endDate.getValue(),
			// 工单状态
			eventStatus : statusCombobox.getValue(),
			// 工单类型
			eventType : taskListCombobox.getValue(),
			// 供电单位
			orgNo : taskDetailsOrgNo.getValue()
		}
		listStore.load();
//		listStore.on('load', function() {
//			myMask.hide();
//		});
//		listStore.on('loadexception', function() {
//			myMask.hide();
//		})
	}
// -------------------------------------------------------------查询 end
//-------------------------------------------------------------系统异常 begin
	var listStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : './qrystat/taskQuery/taskDetails!searchEventDetailsInfo.action'
			}),
		reader : new Ext.data.JsonReader({
			root : 'eventInfoList',
			totalProperty : 'totalCount'
			}, [{
				name : 'eventID'
			}, {
				name : 'orgName'
			}, {
				name : 'eventType'
			}, {
				name : 'eventCode'
			}, {
				name : 'fromId'
			}, {
				name : 'evnetTime'
			}, {
				name : 'flowStatusCode'
			}
			])
		})
	//列表选中框
	var systemSM = new Ext.grid.CheckboxSelectionModel( {
//True表示为同时只能选单行
//		singleSelect : true
	});
	//列表序列号
	var systemRN = new Ext.grid.RowNumberer({
				// header : '序',
				renderer : function(v, p, record, rowIndex) {
					var startRow = 0;
					if (listStore && listStore.lastOptions
							&& listStore.lastOptions.params) {
						startRow = listStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			})
	var systemCM = new Ext.grid.ColumnModel([systemRN,
		{
	header : '工单号',
	dataIndex : 'eventID',
	sortable : true,
	align : 'center'
}, {
	header : '供电单位',
	dataIndex : 'orgName',
	sortable : true,
	align : 'center'
},
// {header:'资产编号',dataIndex:'zcbh',sortable:true,align:'center'},
		{
			header : '工单类型',
			dataIndex : 'eventType',
			sortable : true,
			align : 'center',
			renderer : function(val) {
				var html = '<div align = "left">' + getEventType(val)
						+ '</div>';
				return html;
			}
		}, {
			header : '告警编码',
			dataIndex : 'eventCode',
			sortable : true,
			align : 'center'
		}, {
			header : '来源',
			dataIndex : 'fromId',
			sortable : true,
			align : 'center'
		}, {
			header : '工单处理状态',
			dataIndex : 'flowStatusCode',
			sortable : true,
			align : 'center',
			renderer : function(val) {
				var html = '<div align = "left">' + getFlowStatusCode(val)
						+ '</div>';
				return html;
			}
		}, {
			header : '发生时间',
			dataIndex : 'evnetTime',
			sortable : true,
			align : 'center'
		}
// {header:'派工时间',dataIndex:'pgsj',sortable:true,align:'center'},
//		{header:'解决时间',dataIndex:'jjsj',sortable:true,align:'center'},
//		{header:'处理人',dataIndex:'clr',sortable:true,align:'center'}
]);
	
	var taskList_DetailsGrid = new Ext.grid.GridPanel({
		cm:systemCM,
		region : 'center',
		layout:'fit',
		store:listStore,
		viewConfig:{
			forceFit:true
		},
		tbar : [{
			xtype:'label',
			html : "<font font-weight:bold;>工单列表</font>"
		}],
		bbar : new Ext.ux.MyToolbar( {
			enableExpAll : true,
			store : listStore
		})
	});
	
Ext.onReady(function(){
	
	var taskStatPanel = new Ext.Panel({
		autoScroll : true,
		border : false,
		layout:'border',
		items : [taskList_DetailsParaPanel,taskList_DetailsGrid]
	});

	
	renderModel(taskStatPanel,'工单明细');
//-------------------------------------------------------------异常处理 end
	if(typeof(staticTaskDetailsOrgNo) != 'undefined' || typeof(staticTaskDetailsEventType) != 'undefined'){
		Ext.getCmp("detailsOrgNo").setValue(staticTaskDetailsOrgNo);
		Ext.getCmp("event_type").setValue(staticTaskDetailsEventType);
		Ext.getCmp("taskList_startDate").setValue(staticTaskDetailsDateFrom);
		Ext.getCmp("taskList_endDate").setValue(staticTaskDetailsDateTo);
		searchEventinfo();
	}
	
	//触发事件加载数据
	Ext.getCmp('工单明细').on('activate', function() {
 		if(typeof(staticTaskDetailsOrgNo) != 'undefined' || typeof(staticTaskDetailsEventType) != 'undefined'){
		Ext.getCmp("detailsOrgNo").setValue(staticTaskDetailsOrgNo);
		Ext.getCmp("event_type").setValue(staticTaskDetailsEventType);
		Ext.getCmp("taskList_startDate").setValue(staticTaskDetailsDateFrom);
		Ext.getCmp("taskList_endDate").setValue(staticTaskDetailsDateTo);
		searchEventinfo();
	}
	});
	
});

//根据标识获得业务类型名称
function getEventType(val) {
		if(val == 1){
			return "系统异常" ;
		}
		else if(val == 2){
			return " 终端上报事件";
		}
		else if(val == 3){
			return "主站分析终端故障";
		}
		else if(val == 4){
			return "主站分析用电异常";
		}
		else if(val == 5){
			return "主站分析数据异常";
		}
	}
//根据标识获得业务流程状态名称	
 function getFlowStatusCode(val) {
	switch (val) {
		case '00' :
			return "新异常";
		case '02' :
			return "营销处理中";
		case '03' :
			return "正常归档";
		case '04' :
			return "误报确认";
		case '06' :
			return "挂起";
		case '07' :
			return "本地处理中";
		case '08' :
			return "强制归档";
	}
}