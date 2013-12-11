// 当前电能示值统计------------------------------------
	
//当前电能示值调用函数
function mpRealDataReadWindowShow(){
	//终端类别
	var mpRealDataReadTmnlStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url : 'qrystat/queryConsTmnl!queryConsTmnlInfo.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'consTmnlList'
			}, [{name:'tmnlAssetNumber'},{name:'tmnlAssetNo'}])
	});
	
	var mpRealDataReadTmnlComboBox = new Ext.form.ComboBox({
		store : mpRealDataReadTmnlStore,
		name :'mpRealDataReadTmnlComboBox',
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
	
	var mpRealDataRead = new Ext.Panel({
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
								items : [mpRealDataReadTmnlComboBox]
							},{
								columnWidth : .13,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
											text : "查询",
											listeners : {
												"click": function (){
													var start = mpRealDataRead.find("name","dataDateFrom")[0].getValue();
								                    var end = mpRealDataRead.find("name","dataDateTo")[0].getValue();
								                    if((start - end) > 0){
								                    	Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
								                    	return;
								                    }
								                    var tmnlType = mpRealDataReadTmnlComboBox.getValue();
								                    if(Ext.isEmpty(tmnlType)
								                    		|| "" == tmnlType){
								                    	tmnlType = "*";
								                    }
									                mpRealDataReadStore.baseParams['dataDateFrom'] = start;
									                mpRealDataReadStore.baseParams['dataDateTo'] = end;
									                mpRealDataReadStore.baseParams['tmnlType'] = tmnlType;
									                mpRealDataReadStore.load();
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
									        mpRealDataReadWindow.close();
										}
									},
									width : 80
								
								}]
							}]
				}]
	});
	var mpRealDataReadStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/mpRealDataRead!queryMpRealDataRead.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'mpRealDataReadList',
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
						{name:'mpName'}
						]),
				sortInfo : {
							field : 'tmnlAssetNo',
							direction : 'ASC'
						}
		});
	
	var mpRealDataReadCm = new Ext.grid.ColumnModel([{
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
					header : "终端抄表时间",
					dataIndex : 'dataDate',
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
					header : "正向有功总能示值",
					width:110,
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
					width:120,
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
					width:120,
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
					width:120,
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
					width:120,
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
					width:110,
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
					width:120,
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
					width:120,
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
					width:120,
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
					width:120,
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
					width:110,
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
					width:120,
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
					width:120,
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
					width:120,
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
					width:120,
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
					width:110,
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
					width:120,
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
					width:120,
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
					width:120,
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
					width:120,
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
					width:130,
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
					width:130,
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
					width:130,
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
					width:130,
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
	
	var mpRealDataReadGrid = new Ext.grid.GridPanel({
		height:345,
		autoScroll:true,
		anchor:'100%',
		colModel : mpRealDataReadCm,
		ds : mpRealDataReadStore,
		viewConfig : {
			forceFit : false
		},
		bbar : new Ext.ux.MyToolbar({
			enableExpAll : true,
			store : mpRealDataReadStore
		})
	});
	
	var mpRealDataReadWindow = new Ext.Window({
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
		title:'【当前电能示值】',
		items:[mpRealDataRead,mpRealDataReadGrid]
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
			mpRealDataReadWindow.setTitle((consName==null?"":consName) + "【当前电能示值】");
			mpRealDataRead.find("name","dataDateFrom")[0].setValue(dataDateFrom);
			mpRealDataRead.find("name","dataDateTo")[0].setValue(dataDateTo);
			mpRealDataReadStore.baseParams['dataDateFrom'] = dataDateFrom;
			mpRealDataReadStore.baseParams['dataDateTo'] = dataDateTo;
			mpRealDataReadStore.baseParams['consNo'] = consNo;
			mpRealDataReadStore.baseParams['tmnlType'] = "*";
			mpRealDataReadStore.load();
			mpRealDataReadTmnlStore.removeAll();
			mpRealDataReadTmnlComboBox.reset();
			mpRealDataReadTmnlStore.baseParams = {
					consNo : consNo
 	        };			
			mpRealDataReadTmnlStore.load();
			mpRealDataReadWindow.show();
		}
	});
}