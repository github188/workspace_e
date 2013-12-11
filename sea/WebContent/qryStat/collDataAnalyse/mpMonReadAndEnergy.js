// 月冻结电能量------------------------------------
	
//测量点调用函数
function mpMonReadAndEnergyWindowShow(){
	//终端类别
	var mpMonEnergyTmnlStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url : 'qrystat/queryConsTmnl!queryConsTmnlInfo.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'consTmnlList'
			}, [{name:'tmnlAssetNumber'},{name:'tmnlAssetNo'}])
	});
	
	var mpMonEnergyTmnlComboBox = new Ext.form.ComboBox({
		store : mpMonEnergyTmnlStore,
		name :'mpMonEnergyTmnlComboBox',
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
	
	var mpMonEnergy = new Ext.Panel({
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
											format:"Y-m",
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
											format:"Y-m",
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
								items : [mpMonEnergyTmnlComboBox]
							},{
								columnWidth : .13,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
											text : "查询",
											listeners : {
												"click": function (){
														var start = mpMonEnergy.find("name","dataDateFrom")[0].getValue();
									                    var end = mpMonEnergy.find("name","dataDateTo")[0].getValue();
									                    if((start - end) > 0){
									                    	Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
									                    	return;
									                    }
									                    var tmnlType = mpMonEnergyTmnlComboBox.getValue();
									                    if(Ext.isEmpty(tmnlType)
									                    		|| "" == tmnlType){
									                    	tmnlType = "*";
									                    }
														mpMonEnergyStore.baseParams['dataDateFrom'] = start;
														mpMonEnergyStore.baseParams['dataDateTo'] = end;
														mpMonEnergyStore.baseParams['tmnlType'] = tmnlType;
														mpMonEnergyStore.load();
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
									        mpMonReadAndEnergyWindow.close();
										}
									},
									width : 80
								
								}]
							}]
				}]
	});
	var mpMonEnergyStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/mpMonEnergy!queryMpMonEnergy.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'mpMonEnergyList',
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
	var mpMonEnergyCm = new Ext.grid.ColumnModel([{
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
					header : "正向有功总电能量",
					width: 110,
					dataIndex : 'papE',
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
					width: 140,
					hidden:true,
					dataIndex : 'papE1',
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
					width: 140,
					hidden:true,
					dataIndex : 'papE2',
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
					width: 140,
					hidden:true,
					dataIndex : 'papE3',
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
					width: 140,
					hidden:true,
					dataIndex : 'papE4',
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
					width: 110,
					dataIndex : 'prpE',
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
					width: 130,
					hidden:true,
					dataIndex : 'prpE1',
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
					width: 130,
					hidden:true,
					dataIndex : 'prpE2',
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
					width: 130,
					hidden:true,
					dataIndex : 'prpE3',
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
					width: 130,
					hidden:true,
					dataIndex : 'prpE4',
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
					width: 110,
					dataIndex : 'rapE',
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
					width: 140,
					hidden:true,
					dataIndex : 'rapE1',
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
					width: 140,
					hidden:true,
					dataIndex : 'rapE2',
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
					width: 140,
					hidden:true,
					dataIndex : 'rapE3',
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
					width: 140,
					hidden:true,
					dataIndex : 'rapE4',
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
					width: 110,
					dataIndex : 'rrpE',
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
					width: 120,
					hidden:true,
					dataIndex : 'rrpE1',
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
					width: 120,
					hidden:true,
					dataIndex : 'rrpE2',
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
					width: 120,
					hidden:true,
					dataIndex : 'rrpE3',
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
					width: 120,
					hidden:true,
					dataIndex : 'rrpE4',
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
	var mpMonEnergyGrid = new Ext.grid.GridPanel({
		height:345,
		autoScroll:true,
		anchor:'100%',
		colModel : mpMonEnergyCm,
		ds : mpMonEnergyStore,
		viewConfig : {
			forceFit : false
		},
		bbar : new Ext.ux.MyToolbar({
			enableExpAll : true,
			store : mpMonEnergyStore
		})
	});
	
// 月冻结电能示值------------------------------------
	
	//终端类别
	var mpMonReadTmnlStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url : 'qrystat/queryConsTmnl!queryConsTmnlInfo.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'consTmnlList'
			}, [{name:'tmnlAssetNumber'},{name:'tmnlAssetNo'}])
	});
	
	var mpMonReadTmnlComboBox = new Ext.form.ComboBox({
		store : mpMonReadTmnlStore,
		name :'mpMonReadTmnlComboBox',
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
	
	var mpMonRead = new Ext.Panel({
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
											format:"Y-m",
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
											format:"Y-m",
											fieldLabel : "到",
											editable:false,
											allowBlank:false,
											labelSeparator:'',
											name :'dataDateTo',
											labelStyle : "text-align:right;width:10px;"
										}]
							},{
								columnWidth : .29,
								layout : "form",
								labelWidth : 80,
								baseCls : "x-plain",
								items : [mpMonReadTmnlComboBox]
							},{
								columnWidth : .13,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
											text : "查询",
											listeners : {
												"click": function (){
														var start = mpMonRead.find("name","dataDateFrom")[0].getValue();
									                    var end = mpMonRead.find("name","dataDateTo")[0].getValue();
									                    if((start - end) > 0){
									                    	Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
									                    	return;
									                    }
									                    var tmnlType = mpMonReadTmnlComboBox.getValue();
									                    if(Ext.isEmpty(tmnlType)
									                    		|| "" == tmnlType){
									                    	tmnlType = "*";
									                    }
														mpMonReadStore.baseParams['dataDateFrom'] = start;
														mpMonReadStore.baseParams['dataDateTo'] = end;
														mpMonReadStore.baseParams['tmnlType'] = tmnlType;
														mpMonReadStore.load();
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
									        mpMonReadAndEnergyWindow.close();
										}
									},
									width : 80
								
								}]
							}]
				}]
	});
	var mpMonReadStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/mpMonRead!queryMpMonRead.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'mpMonReadList',
						totalProperty : 'totalCount'
					}, [{name:'colTime'},
						{name:'ct'},
						{name:'pt'},
						{name:'papR'},
						{name:'papR1'},
						{name:'papR2'},
						{name:'papR3'},
						{name:'papR4'},
						{name:'prpR'},
						{name:'prpR1'},
						{name:'prpR2'},
						{name:'prpR3'},
						{name:'prpR4'},
						{name:'rapR'},
						{name:'rapR1'},
						{name:'rapR3'},
						{name:'rapR2'},
						{name:'rapR4'},
						{name:'rrpR'},
						{name:'rrpR1'},
						{name:'rrpR2'},
						{name:'rrpR3'},
						{name:'rrpR4'},
						{name:'rp1R'},
						{name:'rp4R'},
						{name:'rp2R'},
						{name:'rp3R'},
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
	var mpMonReadCm = new Ext.grid.ColumnModel([{
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
					header : "终端抄表时间",
					width:150,
					dataIndex : 'colTime',
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
					header : "CT",
					width:50,
					dataIndex : 'ct',
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
					width:50,
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
					header : "正向有功总能示值",
					width:105,
					dataIndex : 'papR',
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
					header : "正向有功费率1示值",	
					width:115,
					hidden:true,
					dataIndex : 'papR1',
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
					header : "正向有功费率2示值",
					width:115,
					hidden:true,
					dataIndex : 'papR2',
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
					header : "正向有功费率3示值",	
					width:115,
					hidden:true,
					dataIndex : 'papR3',
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
					header : "正向有功费率4示值",	
					width:115,
					hidden:true,
					dataIndex : 'papR4',
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
					header : "正向无功总能示值",
					width:105,
					dataIndex : 'prpR',
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
					header : "正向无功费率1示值",	
					width:115,
					hidden:true,
					dataIndex : 'prpR1',
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
					header : "正向无功费率2示值",	
					width:115,
					hidden:true,
					dataIndex : 'prpR2',
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
					header : "正向无功费率3示值",	
					width:115,
					hidden:true,
					dataIndex : 'prpR3',
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
					header : "正向无功费率4示值",	
					width:115,
					hidden:true,
					dataIndex : 'prpR4',
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
					header : "反向有功总能示值",
					width:105,
					dataIndex : 'rapR',
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
					header : "反向有功费率1示值",		
					width:115,
					hidden:true,
					dataIndex : 'rapR1',
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
					header : "反向有功费率2示值",					
					hidden:true,
					width:115,
					dataIndex : 'rapR2',
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
					header : "反向有功费率3示值",	
					width:115,
					hidden:true,
					dataIndex : 'rapR3',
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
					header : "反向有功费率4示值",
					width:115,
					hidden:true,
					dataIndex : 'rapR4',
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
					header : "反向无功总能示值",
					width:105,
					dataIndex : 'rrpR',
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
					header : "反向无功费率1示值",	
					width:115,
					hidden:true,
					dataIndex : 'rrpR1',
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
					header : "反向无功费率2示值",		
					width:115,
					hidden:true,
					dataIndex : 'rrpR2',
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
					header : "反向无功费率3示值",	
					width:115,
					hidden:true,
					dataIndex : 'rrpR3',
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
					header : "反向无功费率4示值",	
					width:115,
					hidden:true,
					dataIndex : 'rrpR4',
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
					header : "一象限无功电能示值",	
					width:115,
					hidden:true,
					dataIndex : 'rp1R',
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
					header : "二象限无功电能示值",	
					width:115,
					hidden:true,
					dataIndex : 'rp2R',
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
					header : "三象限无功电能示值",
					width:115,
					hidden:true,
					dataIndex : 'rp3R',
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
					header : "四象限无功电能示值",	
					width:115,
					hidden:true,
					dataIndex : 'rp4R',
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
	var mpMonReadGrid = new Ext.grid.GridPanel({
		height:345,
		autoScroll:true,
		anchor:'100%',
		colModel : mpMonReadCm,
		ds : mpMonReadStore,
		viewConfig : {
			forceFit : false
		},
		bbar : new Ext.ux.MyToolbar({
			enableExpAll : true,
			store : mpMonReadStore
		})
	});
	var mpMonReadAndEnergyPanel = new Ext.TabPanel({
			activeTab : 0,
			region : 'center',
			plain : true,
			border : false,
			defaults : {
				autoScroll : true
			},
			items : [{
						title : '月冻结电能示值',
						border : false,
						baseCls : "x-plain",
						autoScroll : true,
						items : [mpMonRead, mpMonReadGrid]
					},{
						title : "月冻结电能量",
						border : false,
						baseCls : "x-plain",
						autoScroll : true,
						items : [mpMonEnergy, mpMonEnergyGrid]
					}]
		});

	var mpMonReadAndEnergyWindow = new Ext.Window({
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
		title:"月冻结电能示值及电能量",
		items:[mpMonReadAndEnergyPanel]
	});
	
	var consNo = Ext.getCmp('general_consNo').getValue();	
	consNo= trim(consNo);
	if(null == consNo || 0 >= consNo.length){
		Ext.MessageBox.alert('提示','请从左边树选择用户！');
	    return;
    }
	var dataDateTo = Ext.getCmp('general_date').getValue();
	var dataDateFromTmp = Ext.getCmp('general_date').getRawValue();
	var year = dataDateFromTmp.substring(0, dataDateFromTmp.indexOf('-'));
	var month = dataDateFromTmp.substring(5, dataDateFromTmp.indexOf('-'));
	var day = dataDateFromTmp.substring(dataDateFromTmp.length, dataDateFromTmp.lastIndexOf('-')+ 1);
	var dataDateFrom = year+"-01-"+day;
	Ext.Ajax.request({
		url : "qrystat/queryConsName!queryconsName.action",
		params : {
			consNo : consNo
		},
		success : function(response) {
			var consName = Ext.decode(response.responseText).consName;
			mpMonReadAndEnergyWindow.setTitle((consName==null?"":consName) + "【月冻结电能示值及电能量】");
			mpMonRead.find("name","dataDateFrom")[0].setValue(dataDateFrom);
			mpMonRead.find("name","dataDateTo")[0].setValue(dataDateTo);
			mpMonReadStore.baseParams['dataDateFrom'] = dataDateFrom;
			mpMonReadStore.baseParams['dataDateTo'] = dataDateTo;
			mpMonReadStore.load({
				params:{consNo : consNo, 
						dataDateFrom : dataDateFrom, 
						dataDateTo : dataDateTo,
						tmnlType:"*"}
			});
			mpMonEnergy.find("name","dataDateFrom")[0].setValue(dataDateFrom);
			mpMonEnergy.find("name","dataDateTo")[0].setValue(dataDateTo);
			mpMonEnergyStore.baseParams['dataDateFrom'] = dataDateFrom;
			mpMonEnergyStore.baseParams['dataDateTo'] = dataDateTo;
			mpMonEnergyStore.load({
				params:{consNo : consNo, 
						dataDateFrom : dataDateFrom, 
						dataDateTo : dataDateTo,
						tmnlType:"*"}
			});
			mpMonReadStore.baseParams['consNo'] = consNo;
			mpMonEnergyStore.baseParams['consNo'] = consNo;
			mpMonReadTmnlStore.removeAll();
			mpMonReadTmnlComboBox.clearValue();
			mpMonReadTmnlStore.baseParams = {
					consNo : consNo
 	        };			
			mpMonReadTmnlStore.load();
			
			mpMonEnergyTmnlStore.removeAll();
			mpMonEnergyTmnlComboBox.clearValue();
			mpMonEnergyTmnlStore.baseParams = {
					consNo : consNo
 	        };			
			mpMonEnergyTmnlStore.load();
			mpMonReadAndEnergyWindow.show();
		}
	});
}