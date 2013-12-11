// 测量点日冻结电流越限统计------------------------------------

//测量点日冻结电流越限统计调用函数
function mpDayIstatWindowShow(){
	//终端类别
	var mpDayIstatTmnlStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url : 'qrystat/queryConsTmnl!queryConsTmnlInfo.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'consTmnlList'
			}, [{name:'tmnlAssetNumber'},{name:'tmnlAssetNo'}])
	});
	
	var mpDayIstatTmnlComboBox = new Ext.form.ComboBox({
		store : mpDayIstatTmnlStore,
		name :'mpDayIstatTmnlComboBox',
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
	
	var mpDayIstat = new Ext.Panel({
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
								items : [mpDayIstatTmnlComboBox]
							},{
								columnWidth : .13,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
											text : "查询",
											listeners : {
												"click": function (){	
														var start = mpDayIstat.find("name","dataDateFrom")[0].getValue();
									                    var end = mpDayIstat.find("name","dataDateTo")[0].getValue();
									                    if((start - end) > 0){
									                    	Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
									                    	return;
									                    }
									                    var tmnlType = mpDayIstatTmnlComboBox.getValue();
									                    if(Ext.isEmpty(tmnlType)
									                    		|| "" == tmnlType){
									                    	tmnlType = "*";
									                    }
														mpDayIstatStore.baseParams['dataDateFrom'] = start;
														mpDayIstatStore.baseParams['dataDateTo'] = end;
														mpDayIstatStore.baseParams['tmnlType'] = tmnlType;
														mpDayIstatStore.load();
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
									        mpDayIstatWindow.close();
										}
									},
									width : 80
								
								}]
							}]
				}]
	});
	var mpDayIstatStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/mpDayIstat!queryMpDayIstat.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'mpDayIstatList',
						totalProperty : 'totalCount'
					}, [{name:'ct'},
						{name:'iaUupTime'},
						{name:'iaUpTime'},
						{name:'ibUupTime'},
						{name:'ibUpTime'},
						{name:'icUupTime'},
						{name:'icUpTime'},
						{name:'i0UpTime'},
						{name:'iaMax'},
						{name:'iaMaxTime'},
						{name:'ibMax'},
						{name:'ibMaxTime'},
						{name:'icMax'},
						{name:'icMaxTime'},
						{name:'i0Max'},
						{name:'i0MaxTime'},
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
	var mpDayIstatCm = new Ext.grid.ColumnModel([{
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
				},{
					header : "A相电流越上上限月累计时间",
					dataIndex : 'iaUupTime',
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
					header : "A相电流越上限月累计时间",
					dataIndex : 'iaUpTime',
					width: 150,
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
					header : "B相电流越上上限月累计时间",
					width: 160,
					hidden:true,
					dataIndex : 'ibUupTime',
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
					header : "B相电流越上限月累计时间",
					hidden:true,
					dataIndex : 'ibUpTime',
					width: 150,
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
					header : "C相电流越上上限月累计时间",
					hidden:true,
					dataIndex : 'icUupTime',
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
					header : "C相电流越上限月累计时间",
					hidden:true,
					dataIndex : 'icUpTime',
					width: 150,
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
					header : "零序电流越上限月累计时间",
					dataIndex : 'i0UpTime',
					width: 155,
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
					header : "A相电流最大值",
					hidden:true,
					dataIndex : 'iaMax',
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
					header : "A相电流最大值发生时间",
					hidden:true,
					dataIndex : 'iaMaxTime',
					width: 150,
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
					header : "B相电流最大值",
					hidden:true,
					dataIndex : 'ibMax',
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
					header : "B相电流最大值发生时间",
					hidden:true,
					dataIndex : 'ibMaxTime',
					width: 150,
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
					header : "C相电流最大值",
					hidden:true,
					dataIndex : 'icMax',
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
					header : "C相电流最大值发生时间",
					hidden:true,
					dataIndex : 'icMaxTime',
					width: 150,
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
					header : "零序电流最大值",
					dataIndex : 'i0Max',
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
					header : "零序电流最大值发生时间",
					dataIndex : 'i0MaxTime',
					width: 150,
					align : 'center',
		            renderer : function(val) {
			            if(null == val){
				            val = "";
			            }
	                    var html = '<div align = "left">' + val
	                        + '</div>';
	                    return html;
                    }
				}]);	
	
	var mpDayIstatGrid = new Ext.grid.GridPanel({
		height:345,
		autoScroll:true,
		stripeRows : true,
		anchor:'100%',
		colModel : mpDayIstatCm,
		ds : mpDayIstatStore,
		viewConfig : {
			forceFit : false
		},
		bbar : new Ext.ux.MyToolbar({
			enableExpAll : true,
			store : mpDayIstatStore
		})
	});
	var mpDayIstatWindow = new Ext.Window({
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
		title:'【日冻结电流越限统计】',
		items:[mpDayIstat,mpDayIstatGrid]
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
			mpDayIstatWindow.setTitle((consName==null?"":consName) + "【日冻结电流越限统计】");
			mpDayIstat.find("name","dataDateFrom")[0].setValue(dataDateFrom);
			mpDayIstat.find("name","dataDateTo")[0].setValue(dataDateTo);
			mpDayIstatStore.baseParams['dataDateFrom'] = dataDateFrom;
			mpDayIstatStore.baseParams['dataDateTo'] = dataDateTo;
			mpDayIstatStore.load({
				params:{consNo : consNo, 
						dataDateFrom : dataDateFrom, 
						dataDateTo : dataDateTo,
						tmnlType:"*"}
			});
			mpDayIstatStore.baseParams['consNo'] = consNo;
			mpDayIstatTmnlStore.removeAll();
			mpDayIstatTmnlComboBox.clearValue();
			mpDayIstatTmnlStore.baseParams = {
					consNo : consNo
 	        };			
			mpDayIstatTmnlStore.load();
			mpDayIstatWindow.show();
		}
	});
}