// 月冻结功率因数区段累计时间------------------------------------


//月冻结功率因数区段累计时间调用函数
function mpMonPftimeWindowShow(){
	//终端类别
	var mpMonPftimeTmnlStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url : 'qrystat/queryConsTmnl!queryConsTmnlInfo.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'consTmnlList'
			}, [{name:'tmnlAssetNumber'},{name:'tmnlAssetNo'}])
	});
	
	var mpMonPftimeTmnlComboBox = new Ext.form.ComboBox({
		store : mpMonPftimeTmnlStore,
		name :'mpMonPftimeTmnlComboBox',
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
	
	var mpMonPftime = new Ext.Panel({
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
											labelSeparator:'',
											fieldLabel : "到",
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
								items : [mpMonPftimeTmnlComboBox]
							},{
								columnWidth : .13,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
											text : "查询",
											listeners : {
												"click": function (){
														var start = mpMonPftime.find("name","dataDateFrom")[0].getValue();
									                    var end = mpMonPftime.find("name","dataDateTo")[0].getValue();
									                    if((start - end) > 0){
									                    	Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
									                    	return;
									                    }
									                    var tmnlType = mpMonPftimeTmnlComboBox.getValue();
									                    if(Ext.isEmpty(tmnlType)
									                    		|| "" == tmnlType){
									                    	tmnlType = "*";
									                    }
														mpMonPftimeStore.baseParams['dataDateFrom'] = start;
														mpMonPftimeStore.baseParams['dataDateTo'] = end;
														mpMonPftimeStore.baseParams['tmnlType'] = tmnlType;
														mpMonPftimeStore.load();
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
									        mpMonPftimeWindow.close();
										}
									},
									width : 80
								
								}]
							}]
				}]
	});
	var mpMonPftimeStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/mpMonPftime!queryMpMonPftime.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'mpMonPftimeList',
						totalProperty : 'totalCount'
					}, [{name:'sumTime1'},
						{name:'sumTime2'},
						{name:'sumTime3'},
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
	var mpMonPftimeCm = new Ext.grid.ColumnModel([{
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
					header : "区段1累计时间",
					dataIndex : 'sumTime1',
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
					header : "区段2累计时间",
					dataIndex : 'sumTime2',
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
					header : "区段3累计时间",
					dataIndex : 'sumTime3',
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
	var mpMonPftimeGrid = new Ext.grid.GridPanel({
		height:345,
		autoScroll:true,
		anchor:'100%',
		colModel : mpMonPftimeCm,
		ds : mpMonPftimeStore,
		viewConfig : {
			forceFit : false
		},
		bbar : new Ext.ux.MyToolbar({
			enableExpAll : true,
			store : mpMonPftimeStore
		})
	});
	
	var mpMonPftimeWindow = new Ext.Window({
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
		title:'【月冻结功率因数区段累计时间】',
		items:[mpMonPftime,mpMonPftimeGrid]
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
			mpMonPftimeWindow.setTitle((consName==null?"":consName) + "【月冻结功率因数区段累计时间】");
			mpMonPftime.find("name","dataDateFrom")[0].setValue(dataDateFrom);
			mpMonPftime.find("name","dataDateTo")[0].setValue(dataDateTo);
			mpMonPftimeStore.baseParams['dataDateFrom'] = dataDateFrom;
			mpMonPftimeStore.baseParams['dataDateTo'] = dataDateTo;
			mpMonPftimeStore.load({
				params:{consNo : consNo, 
						dataDateFrom : dataDateFrom, 
						dataDateTo : dataDateTo,
						tmnlType:"*"}
			});
			mpMonPftimeStore.baseParams['consNo'] = consNo;
			mpMonPftimeTmnlStore.removeAll();
			mpMonPftimeTmnlComboBox.clearValue();
			mpMonPftimeTmnlStore.baseParams = {
					consNo : consNo
 	        };			
			mpMonPftimeTmnlStore.load();
			mpMonPftimeWindow.show();
		}
	});
}