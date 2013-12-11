// 测量点日冻结电压统计------------------------------------
    
//测量点日冻结电压统计调用函数
function mpDayVstatWindowShow(){
	//终端类别
	var mpDayVstatTmnlStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url : 'qrystat/queryConsTmnl!queryConsTmnlInfo.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'consTmnlList'
			}, [{name:'tmnlAssetNumber'},{name:'tmnlAssetNo'}])
	});
	
	var mpDayVstatTmnlComboBox = new Ext.form.ComboBox({
		store : mpDayVstatTmnlStore,
		name :'mpDayVstatTmnlComboBox',
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
	
	var mpDayVstat = new Ext.Panel({
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
								items : [mpDayVstatTmnlComboBox]
							},{
								columnWidth : .13,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
											text : "查询",
											listeners : {
												"click": function (){	
														var start = mpDayVstat.find("name","dataDateFrom")[0].getValue();
									                    var end = mpDayVstat.find("name","dataDateTo")[0].getValue();
									                    if((start - end) > 0){
									                    	Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
									                    	return;
									                    }
									                    var tmnlType = mpDayVstatTmnlComboBox.getValue();
									                    if(Ext.isEmpty(tmnlType)
									                    		|| "" == tmnlType){
									                    	tmnlType = "*";
									                    }
														mpDayVstatStore.baseParams['dataDateFrom'] = start;
														mpDayVstatStore.baseParams['dataDateTo'] = end;
														mpDayVstatStore.baseParams['tmnlType'] = tmnlType;
														mpDayVstatStore.load();
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
									        mpDayVstatWindow.close();
										}
									},
									width : 80
								
								}]
							}]
				}]
	});
	var mpDayVstatStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/mpDayVstat!queryMpDayVstat.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'mpDayVstatList',
						totalProperty : 'totalCount'
					}, [{name:'pt'},
						{name:'uaUupTime'},
						{name:'uaLlwTime'},
						{name:'uaUpTime'},
						{name:'uaLowTime'},
						{name:'uaNmlTime'},
						{name:'ubUupTime'},
						{name:'ubLlwTime'},
						{name:'ubUpTime'},
						{name:'ubLowTime'},
						{name:'ubNmlTime'},
						{name:'ucUupTime'},
						{name:'ucLlwTime'},
						{name:'ucUpTime'},
						{name:'ucLowTime'},
						{name:'ucNmlTime'},
						{name:'uaMax'},
						{name:'uaMaxTime'},
						{name:'uaMin'},
						{name:'uaMinTime'},
						{name:'ubMax'},
						{name:'ubMaxTime'},
						{name:'ubMin'},
						{name:'ubMinTime'},
						{name:'ucMax'},
						{name:'ucMaxTime'},
						{name:'ucMin'},
						{name:'ucMinTime'},
						{name:'uaAverage'},
						{name:'ubAverage'},
						{name:'ucAverage'},
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
	var mpDayVstatCm = new Ext.grid.ColumnModel([{
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
					header : "A相电压越上上限月累计时间",
					dataIndex : 'uaUupTime',
					width: 160,
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
					header : "A相电压越下下限月累计时间",
					dataIndex : 'uaLlwTime',
					width: 160,
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
					header : "A相电压越上限月累计时间",
					dataIndex : 'uaUpTime',
					width: 160,
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
					header : "A相电压越下限月累计时间",
					dataIndex : 'uaLowTime',
					width: 160,
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
					header : "A相电压合格月累计时间",
					dataIndex : 'uaNmlTime',
					width: 160,
					align : 'center',
		            renderer : function(val) {
			            if(null == val){
				            val = "";
			            }
	                    var html = '<div align = "right">' + val
	                        + '</div>';
	                    return html;
                    }
				},{
					header : "B相电压越上上限月累计时间",
					hidden:true,
					dataIndex : 'ubUupTime',
					width: 160,
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
					header : "B相电压越下下限月累计时间",
					hidden:true,
					dataIndex : 'ubLlwTime',
					width: 160,
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
					header : "B相电压越上限月累计时间",
					width: 160,
					hidden:true,
					dataIndex : 'ubUpTime',
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
					header : "B相电压越下限月累计时间",
					hidden:true,
					dataIndex : 'ubLowTime',
					width: 160,
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
					header : "B相电压合格月累计时间",
					hidden:true,
					dataIndex : 'ubNmlTime',
					width: 160,
					align : 'center',
		            renderer : function(val) {
			            if(null == val){
				            val = "";
			            }
	                    var html = '<div align = "right">' + val
	                        + '</div>';
	                    return html;
                    }
				},{
					header : "C相电压越上上限月累计时间",
					hidden:true,
					width: 160,
					dataIndex : 'ucUupTime',
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
					header : "C相电压越下下限月累计时间",
					hidden:true,
					dataIndex : 'ucLlwTime',
					width: 160,
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
					header : "C相电压越上限月累计时间",
					hidden:true,
					dataIndex : 'ucUpTime',
					width: 160,
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
					header : "C相电压越下限月累计时间",
					hidden:true,
					dataIndex : 'ucLowTime',
					width: 160,
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
					header : "C相电压合格月累计时间",
					hidden:true,
					dataIndex : 'ucNmlTime',
					width: 160,
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
					header : "A相电压最大值",
					dataIndex : 'uaMax',
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
					header : "A相电压最大值发生时间",
					width: 160,
					dataIndex : 'uaMaxTime',
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
					header : "A相电压最小值",
					dataIndex : 'uaMin',
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
					header : "A相电压最小值发生时间",
					width: 160,
					dataIndex : 'uaMinTime',
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
					header : "B相电压最大值",
					hidden:true,
					dataIndex : 'ubMax',
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
					header : "B相电压最大值发生时间",
					width: 160,
					hidden:true,
					dataIndex : 'ubMaxTime',
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
					header : "B相电压最小值",
					hidden:true,
					dataIndex : 'ubMin',
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
					header : "B相电压最小值发生时间",
					width: 160,
					hidden:true,
					dataIndex : 'ubMinTime',
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
					header : "C相电压最大值",
					hidden:true,
					dataIndex : 'ucMax',
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
					header : "C相电压最大值发生时间",
					width: 160,
					hidden:true,
					dataIndex : 'ucMaxTime',
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
					header : "C相电压最小值",
					hidden:true,
					dataIndex : 'ucMin',
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
					header : "C相电压最小值发生时间",
					width: 160,
					hidden:true,
					dataIndex : 'ucMinTime',
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
					header : "A相平均电压",
					dataIndex : 'uaAverage',
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
					header : "B相平均电压",
					hidden:true,
					dataIndex : 'ubAverage',
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
					header : "C相平均电压",
					hidden:true,
					dataIndex : 'ucAverage',
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
	var mpDayVstatGrid = new Ext.grid.GridPanel({
		height:345,
		autoScroll:true,
		stripeRows : true,
		anchor:'100%',
		colModel : mpDayVstatCm,
		ds : mpDayVstatStore,
		viewConfig : {
			forceFit : false
		},
		bbar : new Ext.ux.MyToolbar({
			enableExpAll : true,
			store : mpDayVstatStore
		})
	});
	var mpDayVstatWindow = new Ext.Window({
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
		title:'【日冻结电压统计】',
		items:[mpDayVstat,mpDayVstatGrid]
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
			mpDayVstatWindow.setTitle((consName==null?"":consName) + "【日冻结电压统计】");
			mpDayVstat.find("name","dataDateFrom")[0].setValue(dataDateFrom);
			mpDayVstat.find("name","dataDateTo")[0].setValue(dataDateTo);
			mpDayVstatStore.baseParams['dataDateFrom'] = dataDateFrom;
			mpDayVstatStore.baseParams['dataDateTo'] = dataDateTo;
			mpDayVstatStore.load({
				params:{consNo : consNo, 
						dataDateFrom : dataDateFrom, 
						dataDateTo : dataDateTo,
						tmnlType:"*"}
			});
			mpDayVstatStore.baseParams['consNo'] = consNo;
			mpDayVstatTmnlStore.removeAll();
			mpDayVstatTmnlComboBox.clearValue();
			mpDayVstatTmnlStore.baseParams = {
					consNo : consNo
 	        };			
			mpDayVstatTmnlStore.load();
			mpDayVstatWindow.show();
		}
	});
}