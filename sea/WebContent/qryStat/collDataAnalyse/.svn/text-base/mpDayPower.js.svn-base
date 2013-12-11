// 测量点日冻结总及分相有用功率------------------------------------
    
//测量点日冻结总及分相有用功率调用函数
function mpDayPowerWindowShow(){
	//终端类别
	var mpDayPowerTmnlStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url : 'qrystat/queryConsTmnl!queryConsTmnlInfo.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'consTmnlList'
			}, [{name:'tmnlAssetNumber'},{name:'tmnlAssetNo'}])
	});
	
	var mpDayPowerTmnlComboBox = new Ext.form.ComboBox({
		store : mpDayPowerTmnlStore,
		name :'mpDayPowerTmnlComboBox',
		fieldLabel:'终端资产编号',
		valueField : 'tmnlAssetNumber',
		editable : false,
		triggerAction : 'all',
		forceSelection : true,
		mode : 'local',
		selectOnFocus : true,
		emptyText:'请选择',
		displayField : 'tmnlAssetNo',
		labelSeparator:'',
		width : 120
	});
	
	var mpDayPower = new Ext.Panel({
		height : 40,
		plain : true,
		items : [{
					baseCls : "x-plain",
					layout : "column",
					style : "padding:8px",
					items : [ {
								columnWidth : .25,
								layout : "form",
								labelStyle : "text-align:right;width:80;",
								labelWidth : 80,
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											xtype:"datefield",
											format:"Y-m-d",
											value:new Date().add(Date.DAY, -8),
											fieldLabel : "查询日期从",
											editable:false,
											allowBlank:false,
											name :'dataDateFrom',
											labelSeparator:'',
											labelStyle : "text-align:right;width:80px;"
										}]
							},{
								columnWidth : .18,
								layout : "form",
								labelWidth : 20,
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											xtype:"datefield",
											format:"Y-m-d",
											value:new Date().add(Date.DAY, -1),
											fieldLabel : "到",
											editable:false,
											allowBlank:false,
											labelSeparator:'',
											name :'dataDateTo',
											labelStyle : "text-align:right;"
										}]
							},{
								columnWidth : .29,
								layout : "form",
								labelWidth : 80,
								baseCls : "x-plain",
								items : [mpDayPowerTmnlComboBox]
							},{
								columnWidth : .13,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
											text : "查询",
											listeners : {
												"click": function (){
														var start = mpDayPower.find("name","dataDateFrom")[0].getValue();
									                    var end = mpDayPower.find("name","dataDateTo")[0].getValue();
									                    if((start - end) > 0){
									                    	Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
									                    	return;
									                    }
									                    var tmnlType = mpDayPowerTmnlComboBox.getValue();
									                    if(Ext.isEmpty(tmnlType)
									                    		|| "" == tmnlType){
									                    	tmnlType = "*";
									                    }
														mpDayPowerStore.baseParams['dataDateFrom'] = start;
														mpDayPowerStore.baseParams['dataDateTo'] = end;
														mpDayPowerStore.baseParams['tmnlType'] = tmnlType;
														mpDayPowerStore.load();
												}
											},
											width : 80
										}]
							},{
								columnWidth : .15,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
									text : "退出",
									listeners : {
										"click": function (){
									        mpDayPowerWindow.close();
										}
									},
									width : 80
								
								}]
							}]
				}]
	});
	var mpDayPowerStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/mpDayPower!queryMpDayPower.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'mpDayPowerList',
						totalProperty : 'totalCount'
					}, [{name:'ct'},
						{name:'pt'},
						{name:'totalMaxP'},
						{name:'totalMaxPTime'},
						{name:'totalMaxPa'},
						{name:'totalMaxPaTime'},
						{name:'totalMaxPb'},
						{name:'totalMaxPbTime'},
						{name:'totalMaxPc'},
						{name:'totalMaxPcTime'},
						{name:'totalPZero'},
						{name:'totalPaZero'},
						{name:'totalPbZero'},
						{name:'totalPcZero'},
						{name:'dataDate'},
						{name:'tmnlAssetNo'},
						{name:'assetNo'},
						{name:'mpNo'},
						{name:'tmnlAddr'},
						{name:'mpName'}]),
			sortInfo : {
	  			field : 'tmnlAssetNo',
	  			direction : 'ASC'
			}
		});
	
	var mpDayPowerCm = new Ext.grid.ColumnModel([{
					header : "终端资产号",
					dataIndex : 'tmnlAssetNo',
					align : "center"
				},{
					header : "终端地址",
					width : 100,
					dataIndex : 'tmnlAddr',
					sortable : true,
					align : "center"
				} ,{
					header : "表计资产号",
					dataIndex : 'assetNo',
					align : "center",
					renderer : function(val) {
						if(Ext.isEmpty(val)){
							val = "";
						}
						var html = '<div align = "center" ext:qtitle="表计资产号" ext:qtip="' + val
						    + '">' + val + '</div>';
						return html;
			        }
				},{
					header : "计量点编号",
					width : 70,
					dataIndex : 'mpNo',
					sortable : true,
					align : "center"
				} ,
				{
					header : "计量点名称",
					width : 70,
					dataIndex : 'mpName',
					sortable : true,
					align : "center"
				} ,{
					header : "冻结日期",
					dataIndex : 'dataDate',
					align : 'center',
		            renderer : function(val) {
			            if(null == val){
				            val = "";
			            }
	                    var html = '<div align = "left">' + val
	                        + '</div>';
	                    return html;
                    }
				},{
					header : "CT",
					dataIndex : 'ct',
					width: 50,
					align : 'center',
		            renderer : function(val) {
			            if(null == val){
				            val = "";
			            }
	                    var html = '<div align = "right">' + val
	                        + '</div>';
	                    return html;
                    }
				}, {
					header : "PT",
					dataIndex : 'pt',
					width: 50,
					align : 'center',
		            renderer : function(val) {
			            if(null == val){
				            val = "";
			            }
	                    var html = '<div align = "right">' + val
	                        + '</div>';
	                    return html;
                    }
				}, {
					header : "三相总最大有功功率",
					width: 120,
					dataIndex : 'totalMaxP',
					align : 'center',
		            renderer : function(val) {
			            if(null == val){
				            val = "";
			            }
	                    var html = '<div align = "right">' + val
	                        + '</div>';
	                    return html;
                    }
				}, {
					header : "三相总最大有功功率发生时间",
					width: 170,
					dataIndex : 'totalMaxPTime',
					align : 'center',
		            renderer : function(val) {
			            if(null == val){
				            val = "";
			            }
	                    var html = '<div align = "left">' + val
	                        + '</div>';
	                    return html;
                    }
				}, {
					header : "A相最大有功功率",
					width: 105,
					hidden:true,
					dataIndex : 'totalMaxPa',
					align : 'center',
		            renderer : function(val) {
			            if(null == val){
				            val = "";
			            }
	                    var html = '<div align = "right">' + val
	                        + '</div>';
	                    return html;
                    }
				}, {
					header : "A相最大有功功率发生时间",
					width: 150,
					hidden:true,
					dataIndex : 'totalMaxPaTime',
					align : 'center',
		            renderer : function(val) {
			            if(null == val){
				            val = "";
			            }
	                    var html = '<div align = "left">' + val
	                        + '</div>';
	                    return html;
                    }
				}, {
					header : "B相最大有功功率",
					width: 105,
					hidden:true,
					dataIndex : 'totalMaxPb',
					align : 'center',
		            renderer : function(val) {
			            if(null == val){
				            val = "";
			            }
	                    var html = '<div align = "right">' + val
	                        + '</div>';
	                    return html;
                    }
				}, {
					header : "B相最大有功功率发生时间",
					width: 150,
					hidden:true,
					dataIndex : 'totalMaxPbTime',
					align : 'center',
		            renderer : function(val) {
			            if(null == val){
				            val = "";
			            }
	                    var html = '<div align = "left">' + val
	                        + '</div>';
	                    return html;
                    }
				}, {
					header : "C相最大有功功率",
					width: 105,
					hidden:true,
					dataIndex : 'totalMaxPc',
					align : 'center',
		            renderer : function(val) {
			            if(null == val){
				            val = "";
			            }
	                    var html = '<div align = "right">' + val
	                        + '</div>';
	                    return html;
                    }
				}, {
					header : "C相最大有功功率发生时间",
					width: 150,
					hidden:true,
					dataIndex : 'totalMaxPcTime',
					align : 'center',
		            renderer : function(val) {
			            if(null == val){
				            val = "";
			            }
	                    var html = '<div align = "left">' + val
	                        + '</div>';
	                    return html;
                    }
				}, {
					header : "三相总有功功率为零时间",
					width: 150,
					dataIndex : 'totalPZero',
					align : 'center',
		            renderer : function(val) {
			            if(null == val){
				            val = "";
			            }
	                    var html = '<div align = "right">' + val
	                        + '</div>';
	                    return html;
                    }
				}, {
					header : "A相有功功率为零时间",
					width: 140,
					hidden:true,
					dataIndex : 'totalPaZero',
					align : 'center',
		            renderer : function(val) {
			            if(null == val){
				            val = "";
			            }
	                    var html = '<div align = "right">' + val
	                        + '</div>';
	                    return html;
                    }
				}, {
					header : "B相有功功率为零时间",
					width: 140,
					hidden:true,
					dataIndex : 'totalPbZero',
					align : 'center',
		            renderer : function(val) {
			            if(null == val){
				            val = "";
			            }
	                    var html = '<div align = "right">' + val
	                        + '</div>';
	                    return html;
                    }
				}, {
					header : "C相有功功率为零时间",
					width: 140,
					hidden:true,
					dataIndex : 'totalPcZero',
					align : 'center',
		            renderer : function(val) {
			            if(null == val){
				            val = "";
			            }
	                    var html = '<div align = "right">' + val
	                        + '</div>';
	                    return html;
                    }
				}]);		
	var mpDayPowerGrid = new Ext.grid.GridPanel({
		height:345,
		autoScroll:true,
		stripeRows : true,
		anchor:'100%',
		colModel : mpDayPowerCm,
		ds : mpDayPowerStore,
		viewConfig : {
			forceFit : false
		},
		bbar : new Ext.ux.MyToolbar({
			enableExpAll : true,
			store : mpDayPowerStore
		})
	});
	var mpDayPowerWindow = new Ext.Window({
		frame:true,
		width:800,
		height:420,
		layout:"form",
		modal:true,
		plain:true,//设置背景颜色
		resizable:false,
		draggable:false,//不可移动
		buttonAlign:"center",//按钮的位置
		closeAction:"close",//将窗体隐藏而并不销毁
		title:'【日冻结总及分相有功功率】',
		items:[mpDayPower,mpDayPowerGrid]
	});

	var consNo = Ext.getCmp('general_consNo').getValue();
	consNo= trim(consNo);
	if(null == consNo || 0 >= consNo.length){
		Ext.MessageBox.alert('提示','请从左边树选择用户！');
	    return;
    }
	var dataDateTo = Ext.getCmp('general_date').getValue();
	var dataDateFrom = dataDateTo.add(Date.DAY, -7);
	Ext.Ajax.request({
		url : "qrystat/queryConsName!queryconsName.action",
		params : {
			consNo : consNo
		},
		success : function(response) {
			var consName = Ext.decode(response.responseText).consName;
			mpDayPowerWindow.setTitle((consName==null?"":consName) + "【日冻结总及分相有功功率】");
			mpDayPower.find("name","dataDateFrom")[0].setValue(dataDateFrom);
			mpDayPower.find("name","dataDateTo")[0].setValue(dataDateTo);
			mpDayPowerStore.baseParams['dataDateFrom'] = dataDateFrom;
			mpDayPowerStore.baseParams['dataDateTo'] = dataDateTo;
			mpDayPowerStore.load({
				params:{consNo : consNo, 
						dataDateFrom : dataDateFrom, 
						dataDateTo : dataDateTo,
						tmnlType:"*"}
			});
			mpDayPowerStore.baseParams['consNo'] = consNo;
			mpDayPowerTmnlStore.removeAll();
			mpDayPowerTmnlComboBox.clearValue();
			mpDayPowerTmnlStore.baseParams = {
					consNo : consNo
 	        };			
			mpDayPowerTmnlStore.load();
			mpDayPowerWindow.show();
		}
	});
}