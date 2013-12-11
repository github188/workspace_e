// ---------------------------------终端------------------------------------
// 日冻结终端统计------------------------------------

//抄表日冻结需量及需量发生时间调用函数
function mpRdayDemanWindowShow(){

    //终端类别
	var mpRdayDemanTmnlStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url : 'qrystat/queryConsTmnl!queryConsTmnlInfo.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'consTmnlList'
			}, [{name:'tmnlAssetNumber'},{name:'tmnlAssetNo'}])
	});
	
	var mpRdayDemanTmnlComboBox = new Ext.form.ComboBox({
		store : mpRdayDemanTmnlStore,
		name :'mpRdayDemanTmnlComboBox',
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
	
	var mpRdayDeman = new Ext.Panel({
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
								items : [mpRdayDemanTmnlComboBox]
							},{
								columnWidth : .13,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
											text : "查询",
											listeners : {
												"click": function (){
														var start = mpRdayDeman.find("name","dataDateFrom")[0].getValue();
									                    var end = mpRdayDeman.find("name","dataDateTo")[0].getValue();
									                    if((start - end) > 0){
									                    	Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
									                    	return;
									                    }
									                    var tmnlType = mpRdayDemanTmnlComboBox.getValue();
									                    if(Ext.isEmpty(tmnlType)
									                    		|| "" == tmnlType){
									                    	tmnlType = "*";
									                    }
														mpRdayDemanStore.baseParams['dataDateFrom'] = start;
														mpRdayDemanStore.baseParams['dataDateTo'] = end;
														mpRdayDemanStore.baseParams['tmnlType'] = tmnlType;
														mpRdayDemanStore.load();
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
									        mpRdayDemanWindow.close();
										}
									},
									width : 80
								
								}]
							}]
				}]
	});
	var mpRdayDemanStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/mpRdayDemand!queryMpRdayDemand.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'mpRdayDemandList',
						totalProperty : 'totalCount'
					}, [	{name:'colTime'},
						{name:'papDemand'},
						{name:'papDemandTime'},
						{name:'prpDemand'},
						{name:'prpDemandTime'},
						{name:'rapDemand'},
						{name:'rapDemandTime'},
						{name:'rrpDemand'},
						{name:'rrpDemandTime'},
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
	var mpRdayDemanCm = new Ext.grid.ColumnModel([{
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
					header : "正向有功总最大需量",
					width: 120,
					dataIndex : 'papDemand',
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
					header : "正向有功总最大需量发生时间",
					width: 170,
					dataIndex : 'papDemandTime',
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
					header : "正向无功总最大需量",
					width: 120,
					dataIndex : 'prpDemand',
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
					header : "正向无功总最大需量发生时间",
					width: 170,
					dataIndex : 'prpDemandTime',
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
					header : "反向有功总最大需量",
					width: 120,
					dataIndex : 'rapDemand',
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
					header : "反向有功总最大需量发生时间",
					width: 170,
					dataIndex : 'rapDemandTime',
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
					header : "反向无功总最大需量",
					width: 120,
					dataIndex : 'rrpDemand',
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
					header : "反向无功总最大需量发生时间",
					width: 170,
					dataIndex : 'rrpDemandTime',
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
	var mpRdayDemanGrid = new Ext.grid.GridPanel({
		height:345,
		autoScroll:true,
		anchor:'100%',
		colModel : mpRdayDemanCm,
		ds : mpRdayDemanStore,
		viewConfig : {
			forceFit : false
		},
		bbar : new Ext.ux.MyToolbar({
			enableExpAll : true,
			store : mpRdayDemanStore
		})
	});
	var mpRdayDemanWindow = new Ext.Window({
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
		title:'【抄表日冻结需量及需量发生时间】',
		items:[mpRdayDeman,mpRdayDemanGrid]
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
			mpRdayDemanWindow.setTitle((consName==null?"":consName) + "【抄表日冻结需量及需量发生时间】");
			mpRdayDeman.find("name","dataDateFrom")[0].setValue(dataDateFrom);
			mpRdayDeman.find("name","dataDateTo")[0].setValue(dataDateTo);
			mpRdayDemanStore.baseParams['dataDateFrom'] = dataDateFrom;
			mpRdayDemanStore.baseParams['dataDateTo'] = dataDateTo;
			mpRdayDemanStore.load({
				params:{consNo : consNo, 
						dataDateFrom : dataDateFrom, 
						dataDateTo : dataDateTo,
						tmnlType:"*"}
			});
			mpRdayDemanStore.baseParams['consNo'] = consNo;
			mpRdayDemanTmnlStore.removeAll();
			mpRdayDemanTmnlComboBox.clearValue();
			mpRdayDemanTmnlStore.baseParams = {
					consNo : consNo
 	        };			
			mpRdayDemanTmnlStore.load();
			mpRdayDemanWindow.show();
		}
	});
}