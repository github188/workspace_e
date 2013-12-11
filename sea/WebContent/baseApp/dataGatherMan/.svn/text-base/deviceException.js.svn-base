Ext.onReady(function(){
	excepText = new Ext.form.TextField({
			fieldLabel:'节点名',
			allowBlank:false,
			labelSeparator:'',
			name:'orgNo',
			emptyText:'请选择左边节点',
			anchor:'100%',
			border:false
		});
		
	excepDate1 = new Ext.form.DateField({
			fieldLabel : '从',
			format: 'Y-m-d',
			allowBlank : false,
			width : 100,
			value: new Date().add(Date.DAY,-6),
			name : 'excepStartDate'
		});
	excepDate2 = new Ext.form.DateField({
			fieldLabel : '到',
			allowBlank : false,
			format: 'Y-m-d',
			width : 100,
			value: new Date(),
			name : 'excepEndDate'
		});
	if(excep_startDate!=null){
		excepDate1.setValue(excep_startDate);
	}
	if(excep_endDate!=null){
		excepDate2.setValue(excep_endDate);
	}
	if(excep_compName!=null){
		excepText.setValue(excep_compName);
	}
	var excepRadioForm = new Ext.form.RadioGroup({
			width : 255,
			items : [new Ext.form.Radio({
						boxLabel : '严重',
						name : 'queryType',
						inputValue : '1'
					}),
					new Ext.form.Radio({
						boxLabel : '次要',
						name : 'queryType',
						inputValue : '2'
					}),
					new Ext.form.Radio({
						boxLabel : '一般',
						name : 'queryType',
						inputValue : '3'
					}),
					new Ext.form.Radio({
						boxLabel : '全部',
						name : 'queryType',
						checked : true,
						inputValue : '4'
					})]
		});
	if(excep_seriousLevel!=null&&excep_seriousLevel!=''){
		excepRadioForm.setValue(excep_seriousLevel);
	}
		
	var comDeviceStore = new Ext.data.JsonStore({
		url : "baseapp/deviceMonitor!getDeviceFactory.action",
		fields : [ 'factoryCode', 'factoryName' ],
		root : 'vwFactoryList'
	});
	var comboDevice = new Ext.form.ComboBox({
        store: comDeviceStore,
        displayField:'factoryName',
        valueField:'factoryCode',
        typeAhead: true,
        anchor : '78% 1%',
        mode: 'remote',
        fieldLabel:'制造商',
        labelSeparator:'',
        forceSelection: true,
        triggerAction: 'all',
        emptyText:'全部',
        selectOnFocus:true,
        width:220
    });
    var excepQueryTop = new Ext.Panel({
    	labelAlign : 'right',
		height: 33,
		region:'north',
		bodyStyle:'padding:5px 0px 5px 0px',
		layout : 'column',
		border : false,
		items : [{
					columnWidth : .3,
					border : false,
					layout:'form',
					labelWidth:50,
					items : [excepText]
				},{
	                columnWidth:.4,              //label后面加个红色的"*"
	                border:false,
	                layout: 'form',
	                items: [{
						xtype:"label",
						html:'<font color=#ff0000>&nbsp;*</font>'  
                }]
			},{
					columnWidth : .3,
					border : false,
					layout:'form',
					labelWidth:50,
					items : [comboDevice]
			}]
    });
    var excepQueryBott = new Ext.Panel({
    	region:'center',
		border : false,
		layout:'column',
		items:[{
			columnWidth:.4,
			border:false,
			layout:'form',
			labelWidth:1,
			items:[excepRadioForm]
		},{
			columnWidth:0.18,
			border:false,
			labelWidth : 20,
			layout:'form',
			items : [excepDate1]
		},{
			columnWidth:0.29,
			border:false,
			labelWidth : 20,
			layout:'form',
			items : [excepDate2]
		},{
			columnWidth : .08,
			border : false,
			layout:'form',
			items : [{
						xtype : 'button',
						text : '查询',
						width : 50,
						handler:queryExcepData
					}]
		}]
    });
	var excepQueryPanel = new Ext.form.FormPanel({
				labelAlign : 'right',
				frame : false,
				height : 66,
				region:'north',
				bodyStyle : 'padding:5px 0px 0px 0px',
				layout : 'border',
				border : false,
				items : [excepQueryTop,excepQueryBott]
			});
	function queryExcepData(){
		if(!excepQueryPanel.getForm().isValid()){
			Ext.Msg.alert('提示','请完整填写查询条件');
			return true;
		}
    	excepQueryPanel.getForm().submit({
    		method : 'post',
    		url:'baseapp/deviceMonitor!getDeviceExcepInfo.action',
			success:function(form, action) {
				if(action!=null){
					excepStore.loadData(action.result);
				}
			}
    	});
    }
    
	var excepCm = new Ext.grid.ColumnModel([
		{header:'供电单位',dataIndex:'orgName',align:'center',sortable:true},
		{header:'用户编号',dataIndex:'consNo',align:'center',sortable:true},
		{header:'用户名称',dataIndex:'consName',align:'center',sortable:true},
		{header:'终端地址',dataIndex:'terminalAddr',align:'center',sortable:true},
		{header:'事件名称',dataIndex:'eventName',align:'center',sortable:true},
		{header:'发生时间',dataIndex:'happTime',align:'center',sortable:true},
		{header:'附带数据',dataIndex:'psData',align:'center',sortable:true},
		{header:'恢复时间',dataIndex:'repairTime',align:'center',sortable:true},
		{header:'状态',dataIndex:'status',align:'center',sortable:true,
			renderer: function(s, m, rec){
					return "<a href='' target='_blank'>"+s+"</a>"; 
				}}
	]);
	var excepStore = new Ext.data.JsonStore({
		proxy : new Ext.data.MemoryProxy(),
		fields : [ 'orgName', 'consNo','consName','terminalAddr', 
				'eventName','happTime','psData','repairTime','status'],
		root : "deviceExcepList"
	});
	var excepGrid = new Ext.grid.GridPanel({
		store : excepStore,
		title : '终端告警事件',
		region : 'center',
		cm : excepCm,
		stripeRows : true,
		autoWidth : true,	
		autoScroll : true,
		bbar : new Ext.ux.MyToolbar({
				store:excepStore
			}),
		viewConfig : {
				forceFit : false
			}
	});
	var excepViewPanel = new Ext.Panel({
		bodyStyle:'padding:5px 5px 5px 5px',
		layout: 'border' ,
		items: [excepQueryPanel,excepGrid],
		border : false
	});
	renderModel(excepViewPanel,'终端异常告警查询');
	queryExcepData();
});
