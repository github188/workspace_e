// 测量点日冻结电能量------------------------------------

//测量点日冻结电能量调用函数
function mpDayEnergyWindowShow(){
	//终端类别
	var mpDayEnergyTmnlStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url : 'qrystat/queryConsTmnl!queryConsTmnlInfo.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'consTmnlList'
			}, [{name:'tmnlAssetNumber'},{name:'tmnlAssetNo'}])
	});
	
	var mpDayEnergyTmnlComboBox = new Ext.form.ComboBox({
		store : mpDayEnergyTmnlStore,
		name :'mpDayEnergyTmnlComboBox',
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
	
	var mpDayEnergy = new Ext.Panel({
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
											labelSeparator:'',
											name :'dataDateFrom',
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
								items : [mpDayEnergyTmnlComboBox]
							},{
								columnWidth : .13,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
											text : "查询",
											listeners : {
												"click": function (){
														var start = mpDayEnergy.find("name","dataDateFrom")[0].getValue();
									                    var end = mpDayEnergy.find("name","dataDateTo")[0].getValue();
									                    if((start - end) > 0){
									                    	Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
									                    	return;
									                    }
									                    var tmnlType = mpDayEnergyTmnlComboBox.getValue();
									                    if(Ext.isEmpty(tmnlType)
									                    		|| "" == tmnlType){
									                    	tmnlType = "*";
									                    }
														mpDayEnergyStore.baseParams['dataDateFrom'] = start;
														mpDayEnergyStore.baseParams['dataDateTo'] = end;
														mpDayEnergyStore.baseParams['tmnlType'] = tmnlType;
														mpDayEnergyStore.load();
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
									        mpDayEnergyWindow.close();
										}
									},
									width : 80
								
								}]
							}]
				}]
	});
	var mpDayEnergyStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/mpDayEnergy!queryMpDayEnergy.action'
					}),		
			reader : new Ext.data.JsonReader({
						root : 'mpDayEnergyList',
						totalProperty : 'totalCount'
					}, [{name:'papE'},
						{name:'papE1'},
						{name:'papE2'},
						{name:'papE3'},
						{name:'papE4'},
						{name:'prpE'},
						{name:'prpE1'},
						{name:'prpE2'},
						{name:'prpE3'},
						{name:'prpE4'},
						{name:'rapE'},
						{name:'rapE1'},
						{name:'rapE2'},
						{name:'rapE3'},
						{name:'rapE4'},
						{name:'rrpE'},
						{name:'rrpE1'},
						{name:'rrpE2'},
						{name:'rrpE3'},
						{name:'rrpE4'},
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
	var mpDayEnergyCm = new Ext.grid.ColumnModel([{
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
					format : "Y-m-d",
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
					header : "正向有功总电能量",
					dataIndex : 'papE',
					width:110,
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
					header : "日正向有功费率1电能量",
					hidden:true,
					dataIndex : 'papE1',
					width:140,
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
					header : "日正向有功费率2电能量",
					hidden:true,
					dataIndex : 'papE2',
					width:140,
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
					header : "日正向有功费率3电能量",
					hidden:true,
					dataIndex : 'papE3',
					width:140,
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
					header : "日正向有功费率4电能量",
					hidden:true,
					dataIndex : 'papE4',
					width:140,
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
					header : "正向无功总电能量",
					dataIndex : 'prpE',
					width:110,
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
					header : "正向无功费率1电能量",
					hidden:true,
					dataIndex : 'prpE1',
					width:140,
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
					header : "正向无功费率2电能量",
					hidden:true,
					dataIndex : 'prpE2',
					width:140,
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
					header : "正向无功费率3电能量",
					hidden:true,
					dataIndex : 'prpE3',
					width:140,
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
					header : "正向无功费率4电能量",
					hidden:true,
					dataIndex : 'prpE4',
					width:140,
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
					header : "反向有功总电能量",
					dataIndex : 'rapE',
					width:110,
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
					header : "日反向有功费率1电能量",
					hidden:true,
					dataIndex : 'rapE1',
					width:140,
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
					header : "日反向有功费率2电能量",
					hidden:true,
					dataIndex : 'rapE2',
					width:140,
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
					header : "日反向有功费率3电能量",
					hidden:true,
					dataIndex : 'rapE3',
					width:140,
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
					header : "日反向有功费率4电能量",
					hidden:true,
					dataIndex : 'rapE4',
					width:140,
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
					header : "反向无功总电能量",
					dataIndex : 'rrpE',
					width:110,
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
					header : "反向无功费率1电能量",
					hidden:true,
					dataIndex : 'rrpE1',
					width:140,
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
					header : "反向无功费率2电能量",
					hidden:true,
					dataIndex : 'rrpE2',
					width:140,
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
					header : "反向无功费率3电能量",
					hidden:true,
					dataIndex : 'rrpE3',
					width:140,
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
					header : "反向无功费率4电能量",
					hidden:true,
					dataIndex : 'rrpE4',
					width:140,
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
	var mpDayEnergyGrid = new Ext.grid.GridPanel({
		height:345,
		autoScroll:true,
		anchor:'100%',
		colModel : mpDayEnergyCm,
		ds : mpDayEnergyStore,
		viewConfig : {
			forceFit : false
		},
		bbar : new Ext.ux.MyToolbar({
			enableExpAll : true,
			store : mpDayEnergyStore
		})
	});
	var mpDayEnergyWindow = new Ext.Window({
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
		title:'【日冻结电能量】',
		items:[mpDayEnergy,mpDayEnergyGrid]
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
			mpDayEnergyWindow.setTitle((consName==null?"":consName) + "【日冻结电能量】");
			mpDayEnergy.find("name","dataDateFrom")[0].setValue(dataDateFrom);
			mpDayEnergy.find("name","dataDateTo")[0].setValue(dataDateTo);
			
			mpDayEnergyStore.baseParams['dataDateFrom'] = dataDateFrom;
			mpDayEnergyStore.baseParams['dataDateTo'] = dataDateTo;
														
			mpDayEnergyStore.load({
				params:{consNo : consNo, 
						dataDateFrom : dataDateFrom, 
						dataDateTo : dataDateTo,
						tmnlType:"*"}
			});
			mpDayEnergyStore.baseParams['consNo'] = consNo;
			mpDayEnergyTmnlStore.removeAll();
			mpDayEnergyTmnlComboBox.clearValue();
			mpDayEnergyTmnlStore.baseParams = {
					consNo : consNo
 	        };			
			mpDayEnergyTmnlStore.load();
			mpDayEnergyWindow.show();
		}
	});
}