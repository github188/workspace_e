// 月冻结总及分相有功功率数据------------------------------------


//月冻结总及分相有功功率数据调用函数
function mpMonPowerWindowShow(){
	//终端类别
	var mpMonPowerTmnlStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url : 'qrystat/queryConsTmnl!queryConsTmnlInfo.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'consTmnlList'
			}, [{name:'tmnlAssetNumber'},{name:'tmnlAssetNo'}])
	});
	
	var mpMonPowerTmnlComboBox = new Ext.form.ComboBox({
		store : mpMonPowerTmnlStore,
		name :'mpMonPowerTmnlComboBox',
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
	
	var mpMonPower = new Ext.Panel({
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
								items : [mpMonPowerTmnlComboBox]
							},{
								columnWidth : .13,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
											text : "查询",
											listeners : {
												"click": function (){	
														var start = mpMonPower.find("name","dataDateFrom")[0].getValue();
									                    var end = mpMonPower.find("name","dataDateTo")[0].getValue();
									                    if((start - end) > 0){
									                    	Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
									                    	return;
									                    }
									                    var tmnlType = mpMonPowerTmnlComboBox.getValue();
									                    if(Ext.isEmpty(tmnlType)
									                    		|| "" == tmnlType){
									                    	tmnlType = "*";
									                    }
														mpMonPowerStore.baseParams['dataDateFrom'] = start;
														mpMonPowerStore.baseParams['dataDateTo'] = end;
														mpMonPowerStore.baseParams['tmnlType'] = tmnlType;
														mpMonPowerStore.load();
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
									        mpMonPowerWindow.close();
										}
									},
									width : 80
								
								}]
							}]
				}]
	});
	var mpMonPowerStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/mpMonPower!queryMpMonPower.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'mpMonPowerList',
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
	var mpMonPowerCm = new Ext.grid.ColumnModel([{
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
					width: 115,
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
					width: 165,
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
					width: 145,
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
					hidden:true,
					width: 105,
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
					hidden:true,
					width: 145,
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
					hidden:true,
					width: 105,
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
					width: 145,
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
					width: 140,
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
					width: 120,
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
					width: 120,
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
					width: 120,
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
	var mpMonPowerGrid = new Ext.grid.GridPanel({
		height:345,
		autoScroll:true,
		anchor:'100%',
		colModel : mpMonPowerCm,
		ds : mpMonPowerStore,
		viewConfig : {
			forceFit : false
		},
		bbar : new Ext.ux.MyToolbar({
			enableExpAll : true,
			store : mpMonPowerStore
		})
	});
	var mpMonPowerWindow = new Ext.Window({
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
		title:'【月冻结总及分相有功功率数据】',
		items:[mpMonPower,mpMonPowerGrid]
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
			mpMonPower.setTitle((consName==null?"":consName) + "【月冻结总及分相有功功率数据】");
			mpMonPower.find("name","dataDateFrom")[0].setValue(dataDateFrom);
			mpMonPower.find("name","dataDateTo")[0].setValue(dataDateTo);
			mpMonPowerStore.baseParams['dataDateFrom'] = dataDateFrom;
			mpMonPowerStore.baseParams['dataDateTo'] = dataDateTo;
			mpMonPowerStore.load({
				params:{consNo : consNo, 
						dataDateFrom : dataDateFrom, 
						dataDateTo : dataDateTo,
						tmnlType:"*"}
			});
			mpMonPowerStore.baseParams['consNo'] = consNo;
			mpMonPowerTmnlStore.removeAll();
			mpMonPowerTmnlComboBox.clearValue();
			mpMonPowerTmnlStore.baseParams = {
					consNo : consNo
 	        };			
			mpMonPowerTmnlStore.load();
			mpMonPowerWindow.show();
		}
	});
}