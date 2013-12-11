// 测量点日电容器累计补偿的无功电能量------------------------------------

//测量点日电容器累计补偿的无功电能量调用函数
function mpDayCompWindowShow(){
	//终端类别
	var mpDayCompTmnlStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url : 'qrystat/queryConsTmnl!queryConsTmnlInfo.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'consTmnlList'
			}, [{name:'tmnlAssetNumber'},{name:'tmnlAssetNo'}])
	});
	
	var mpDayCompTmnlComboBox = new Ext.form.ComboBox({
		store : mpDayCompTmnlStore,
		name :'mpDayCompTmnlComboBox',
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
	
	var mpDayComp = new Ext.Panel({
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
											labelStyle : "text-align:right;"
										}]
							},{
								columnWidth : .18,
								layout : "form",
								labelWidth : 25,
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											xtype:"datefield",
											format:"Y-m-d",
											value:new Date().add(Date.DAY, -1),
											fieldLabel : "到",
											labelSeparator:'',
											editable:false,
											allowBlank:false,
											name :'dataDateTo',
											labelStyle : "text-align:right;"
										}]
							},{
								columnWidth : .29,
								layout : "form",
								labelWidth : 80,
								baseCls : "x-plain",
								items : [mpDayCompTmnlComboBox]
							},{
								columnWidth : .13,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
											text : "查询",
											listeners : {
												"click": function (){
									                    var start = mpDayComp.find("name","dataDateFrom")[0].getValue();
									                    var end = mpDayComp.find("name","dataDateTo")[0].getValue();
									                    if((start - end) > 0){
									                    	Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
									                    	return;
									                    }
									                    var tmnlType = mpDayCompTmnlComboBox.getValue();
									                    if(Ext.isEmpty(tmnlType)
									                    		|| "" == tmnlType){
									                    	tmnlType = "*";
									                    }
														mpDayCompStore.baseParams['dataDateFrom'] = start;
														mpDayCompStore.baseParams['dataDateTo'] = end;
														mpDayCompStore.baseParams['tmnlType'] = tmnlType;
														mpDayCompStore.load();
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
									        mpDayCompWindow.close();
										}
									},
									width : 80
								
								}]
							}]
				}]
	});
	var mpDayCompStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/mpDayComp!queryMpDayComp.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'mpDayCompList',
						totalProperty : 'totalCount'
					}, [{name:'capUseTime1'},
						{name:'capUseTime2'},
						{name:'capUseTime3'},
						{name:'capUseTime4'},
						{name:'capUseTime5'},
						{name:'capUseTime6'},
						{name:'capUseTime7'},
						{name:'capUseTime8'},
						{name:'capUseTime9'},
						{name:'capUseNum1'},
						{name:'capUseNum2'},
						{name:'capUseNum3'},
						{name:'capUseNum4'},
						{name:'capUseNum5'},
						{name:'capUseNum6'},
						{name:'capUseNum7'},
						{name:'capUseNum8'},
						{name:'capUseNum9'},
						{name:'dayCompE'},
						{name:'monCompE'},
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
	
	var mpDayCompCm = new Ext.grid.ColumnModel([{
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
					header : "第1组投入时间",
					dataIndex : 'capUseTime1',
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
					header : "第2组投入时间",
					dataIndex : 'capUseTime2',
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
					header : "第3组投入时间",
					hidden:true,
					dataIndex : 'capUseTime3',
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
					header : "第4组投入时间",
					hidden:true,
					dataIndex : 'capUseTime4',
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
					header : "第5组投入时间",
					hidden:true,
					dataIndex : 'capUseTime5',
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
					header : "第6组投入时间",
					hidden:true,
					dataIndex : 'capUseTime6',
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
					header : "第7组投入时间",
					hidden:true,
					dataIndex : 'capUseTime7',
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
					header : "第8组投入时间",
					hidden:true,
					dataIndex : 'capUseTime8',
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
					header : "第9组投入时间",
					hidden:true,
					dataIndex : 'capUseTime9',
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
					header : "第1组投入次数",
					dataIndex : 'capUseNum1',
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
					header : "第2组投入次数",
					dataIndex : 'capUseNum2',
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
					header : "第3组投入次数",
					hidden:true,
					dataIndex : 'capUseNum3',
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
					header : "第4组投入次数",
					hidden:true,
					dataIndex : 'capUseNum4',
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
					header : "第5组投入次数",
					hidden:true,
					dataIndex : 'capUseNum5',
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
					header : "第6组投入次数",
					hidden:true,
					dataIndex : 'capUseNum6',
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
					header : "第7组投入次数",
					hidden:true,
					dataIndex : 'capUseNum7',
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
					header : "第8组投入次数",
					hidden:true,
					dataIndex : 'capUseNum8',
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
					header : "第9组投入次数",
					hidden:true,
					dataIndex : 'capUseNum9',
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
					header : "日补偿的无功电能量",
					dataIndex : 'dayCompE',
					width: 120,
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
					header : "月补偿的无功电能量",
					dataIndex : 'monCompE',
					width: 120,
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
	var mpDayCompGrid = new Ext.grid.GridPanel({
		height:345,
		autoScroll:true,
		viewConfig : {
			forceFit : false
		},
		bbar : new Ext.ux.MyToolbar({
			enableExpAll : true,
			store : mpDayCompStore
		}),
		anchor:'100%',
		colModel : mpDayCompCm,
		ds : mpDayCompStore
	});
	var mpDayCompWindow = new Ext.Window({
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
		title:'【日电容器累计补偿的无功电能量】',
		items:[mpDayComp,mpDayCompGrid]
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
			mpDayCompWindow.setTitle((consName==null?"":consName) + "【日电容器累计补偿的无功电能量】");
			mpDayComp.find("name","dataDateFrom")[0].setValue(dataDateFrom);
			mpDayComp.find("name","dataDateTo")[0].setValue(dataDateTo);
			mpDayCompStore.baseParams['dataDateFrom'] = dataDateFrom;
			mpDayCompStore.baseParams['dataDateTo'] = dataDateTo;
			mpDayCompStore.load({//临时变量
				params:{consNo : consNo, 
						dataDateFrom : dataDateFrom, 
						dataDateTo : dataDateTo,
						tmnlType:"*"}
			});
			mpDayCompStore.baseParams['consNo'] = consNo;
			mpDayCompTmnlStore.removeAll();
			mpDayCompTmnlComboBox.clearValue();
			mpDayCompTmnlStore.baseParams = {
					consNo : consNo
 	        };			
			mpDayCompTmnlStore.load();
			mpDayCompWindow.show();
		}
	});
}