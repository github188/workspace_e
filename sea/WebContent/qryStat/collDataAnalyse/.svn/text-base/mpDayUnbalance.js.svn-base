// 日不平衡度越限累计时间------------------------------------

//日不平衡度越限累计时间调用函数
function mpDayUnbalanceWindowShow(){
	//终端类别
	var mpDayUnbalanceTmnlStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url : 'qrystat/queryConsTmnl!queryConsTmnlInfo.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'consTmnlList'
			}, [{name:'tmnlAssetNumber'},{name:'tmnlAssetNo'}])
	});
	
	var mpDayUnbalanceTmnlComboBox = new Ext.form.ComboBox({
		store : mpDayUnbalanceTmnlStore,
		name :'mpDayUnbalanceTmnlComboBox',
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
	
	var mpDayUnbalance = new Ext.Panel({
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
											labelSeparator:'',
											editable:false,
											allowBlank:false,
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
								items : [mpDayUnbalanceTmnlComboBox]
							},{
								columnWidth : .13,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
											text : "查询",
											listeners : {
												"click": function (){	
														var start = mpDayUnbalance.find("name","dataDateFrom")[0].getValue();
									                    var end = mpDayUnbalance.find("name","dataDateTo")[0].getValue();
									                    if((start - end) > 0){
									                    	Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
									                    	return;
									                    }
									                    var tmnlType = mpDayUnbalanceTmnlComboBox.getValue();
									                    if(Ext.isEmpty(tmnlType)
									                    		|| "" == tmnlType){
									                    	tmnlType = "*";
									                    }
														mpDayUnbalanceStore.baseParams['dataDateFrom'] = start;
														mpDayUnbalanceStore.baseParams['dataDateTo'] = end;
														mpDayUnbalanceStore.baseParams['tmnlType'] = tmnlType;
														mpDayUnbalanceStore.load();
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
									        mpDayUnbalanceWindow.close();
										}
									},
									width : 80
								
								}]
							}]
				}]
	});
	var mpDayUnbalanceStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/mpDayUnbalance!queryMpDayUnbalance.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'mpDayUnbalanceList',
						totalProperty : 'totalCount'
					}, [{name:'IUbTime'},
						{name:'UUbTime'},
						{name:'IUbMaxVal'},
						{name:'IUbMaxTime'},
						{name:'UUbMaxVal'},
						{name:'UUbMaxTime'},
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
	var mpDayUnbalanceCm = new Ext.grid.ColumnModel([{
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
					header : "电流不平衡度越限累计时间",
					width:150,
					dataIndex : 'IUbTime',
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
					header : "电压不平衡度越限累计时间",
					width:150,
					dataIndex : 'UUbTime',
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
					header : "电流不平衡最大值",
					width:110,
					dataIndex : 'IUbMaxVal',
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
					header : "电流不平衡最大值发生时间",
					width:150,
					dataIndex : 'IUbMaxTime',
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
					header : "电压不平衡最大值",
					width:110,
					dataIndex : 'UUbMaxVal',
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
					header : "电压不平衡最大值发生时间",
					width:150,
					dataIndex : 'UUbMaxTime',
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
	var mpDayUnbalanceGrid = new Ext.grid.GridPanel({
		height:345,
		autoScroll:true,
		stripeRows : true,
		anchor:'100%',
		colModel : mpDayUnbalanceCm,
		ds : mpDayUnbalanceStore,
		viewConfig : {
			forceFit : false
		},
		bbar : new Ext.ux.MyToolbar({
			enableExpAll : true,
			store : mpDayUnbalanceStore
		})
	});
	var mpDayUnbalanceWindow = new Ext.Window({
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
		title:'【日不平衡度越限累计时间】',
		items:[mpDayUnbalance,mpDayUnbalanceGrid]
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
			mpDayUnbalanceWindow.setTitle((consName==null?"":consName) + "【日不平衡度越限累计时间】");
			mpDayUnbalance.find("name","dataDateFrom")[0].setValue(dataDateFrom);
			mpDayUnbalance.find("name","dataDateTo")[0].setValue(dataDateTo);
			mpDayUnbalanceStore.baseParams['dataDateFrom'] = dataDateFrom;
			mpDayUnbalanceStore.baseParams['dataDateTo'] = dataDateTo;
			mpDayUnbalanceStore.load({
				params:{consNo : consNo, 
						dataDateFrom : dataDateFrom, 
						dataDateTo : dataDateTo,
						tmnlType:"*"}
			});
			mpDayUnbalanceStore.baseParams['consNo'] = consNo;
			mpDayUnbalanceTmnlStore.removeAll();
			mpDayUnbalanceTmnlComboBox.clearValue();
			mpDayUnbalanceTmnlStore.baseParams = {
					consNo : consNo
 	        };			
			mpDayUnbalanceTmnlStore.load();
			mpDayUnbalanceWindow.show();
		}
	});
}