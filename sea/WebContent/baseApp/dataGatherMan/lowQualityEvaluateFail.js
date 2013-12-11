var failDate = '';
var failTmnlNoValue = '';
var ov = true;
						
function detailRecsToArray(valStore) {
	var data = new Array();
	for (var i = 0; i < valStore.getCount(); i++) {
		data[i] = new Array();
		data[i][0] = valStore.getAt(i).data.regSn;
		data[i][1] = valStore.getAt(i).data.consName;
		data[i][2] = valStore.getAt(i).data.tmnlAssetNo;
		data[i][3] = valStore.getAt(i).data.consNo;
		data[i][4] = valStore.getAt(i).data.assetNo;
		data[i][5] = valStore.getAt(i).data.meterData;
		data[i][6] = valStore.getAt(i).data.dataDate;
	}
	return data;
}

function failRecsToArray(recs) {
	var data = new Array();
	for (var i = 0; i < recs.length; i++) {
		data[i] = new Array();
		
		data[i][0] = recs[i].get('regSn');
		data[i][1] = recs[i].get('consName');
		data[i][2] = recs[i].get('consNo');
		data[i][3] = '';
		data[i][4] = '';
		
	}
	return data;
}

function genQualityDataFail(){
	failDate = FailDateField.getValue().format('Y-m-d');
	if(failDate == '' || failTmnlNoValue == ''){
		return true;
	}
	Ext.Ajax.request({
		url:'baseapp/lowQualityEvaluate!queryFailList.action',
		params:{date:failDate,tmnlNo:failTmnlNoValue},
		success:function(response) {
			var result = Ext.decode(response.responseText);
			if(result==null) return true;
			groupStoreFail.loadData(result,false);
		}
	});
}
var FailDateField = new Ext.form.DateField({
		name : "qualityDateFail",
		format : 'Y-m-d',
		allowBlank : false,
		editable:false,
		emptyText : '请选择日期 ...',
		fieldLabel : "查询日期",
		width : "190",
		value : new Date().add(Date.DAY, -1)
	});
FailDateField.on('change',function(){
	lowQEGrid.getGridEl().mask('加载中...');
	genQualityDataFail();
});
	
var QualityDateField = {
	border : false,
	width:190,
	style : {
		marginTop : '0px',
		marginBottom : '10px',
		marginLeft : '2px',
		marginRight : '10px'
	},
	items : [{
				layout : 'form',
				border : false,
				labelWidth : 80,
				width : '190',
				items : [FailDateField]
			}]
};

var taiQuTextField = new Ext.form.TextField({
	fieldLabel : "台区名<font color='red'>*</font>",
	labelStyle : "text-align:right;width:50;",
	readOnly : true,
	labelSeparator : "",
	width : 200
}); 

var qualityQueryPanel = new Ext.Panel({
			labelAlign : 'right',
			region : 'north',
			frame : false,
			style : {
				marginTop : '10px',
				marginBottom : '1px',
				marginLeft : '10px'
			},
			autoWidth : true,
			height : 30,
			layout : 'column',
			border : false,
			items : [{
						columnWidth : .3,// ----------------------
						layout : "form",
						labelWidth : 50,
						baseCls : "x-plain",
						items : [taiQuTextField]
					},{
						columnWidth:.3,
						style : {
							marginTop : '1px',
							marginBottom : '1px',
							marginLeft : '10px'
						},
						border : false,
						items : QualityDateField
					}, {
						columnWidth:.1,
						border : false,
						style : {
							marginTop : '1px',
							marginBottom : '1px',
							marginLeft : '10px'
						},
						items : [{
									xtype : 'button',
									text : '查询',
									width : 50,
									handler:function(){
										lowQEGrid.getGridEl().mask('加载中...');
										genQualityDataFail();
									}
								}]
				},{
					columnWidth:.1,
					border : false,
					style : {
						marginTop : '1px',
						marginBottom : '1px',
						marginLeft : '10px'
					},
					items : [{
							xtype : 'button',
							text : '补抄',
							width : 50,
							handler: function(){
								var regSnArray = new Array();
								
								var recs;
								recs = asfail_gridSm.getSelections();
								
								if(recs==null || recs.length<1){
									Ext.Msg.alert('', "请选择用户");
									return true;
								}
							
								var mydays = (new Date() - FailDateField.getValue()) / (1000 * 24 * 3600);
								if (mydays > 30 || mydays <= 0) {
									Ext.Msg.alert('提示','请补抄一个月以内的历史数据');
									return true;
								}
								
								failWindowStore.loadData(failRecsToArray(recs));
								failWindow.show();
								
								ov = true;
								for(var i=0;i<recs.length;i=i+1){
									regSnArray[i] = recs[i].data.regSn;
								}
								getDataByDate(failTmnlNoValue,regSnArray,0);
							}
						}]
			}]
});
var qualityTopPanel = new Ext.form.FormPanel({
						border : false,
						region:'north',
						height:50,
						autoScroll : true,
						items : [qualityQueryPanel]
					});
					
	var failArrayStore = new Ext.data.Store({
			remoteSort : true,
			reader : new Ext.data.ArrayReader({
						idIndex : 0,
						fields : [{
									name : 'regSn'
								}, {
									name : 'consName'
								}, {
									name : 'tmnlAssetNo'
								}, {
									name : 'consNo'
								}, {
									name : 'assetNo'
								}, {
									name : 'meterData'
								}, {
									name : 'dataDate'
								}]
					})
		});
	var groupStoreFail = new Ext.data.JsonStore({
				root : "lowQualityList",
				idProperty : "regSn",
				proxy : new Ext.data.MemoryProxy(),
				fields : ['regSn','consName','tmnlAssetNo','consNo', 'assetNo','meterData','dataDate']
			});
	groupStoreFail.on('load', function(thisstore, recs, obj) {
		failArrayStore.proxy = new Ext.ux.data.PagingMemoryProxy(detailRecsToArray(thisstore));
		failArrayStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
		lowQEGrid.getGridEl().unmask();
		lowQEGrid.doLayout();
	});
	
	var asfail_gridSm = new Ext.grid.CheckboxSelectionModel();
	var groupCMFail = new Ext.grid.ColumnModel([asfail_gridSm,{
				header : '注册序号',
				dataIndex : 'regSn',
				sortable : true,
				align : 'center'
			},{
				header : '台区名称',
				dataIndex : 'consName_tg',
				sortable : true,
				align : 'center',
				renderer : function(val) {
					var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + lowConsNameValue + '">'
							+ lowConsNameValue + '</div>';
					return html;
				}
			},{
				header : '终端资产号',
				dataIndex : 'tmnlAssetNo',
				sortable : true,
				align : 'center',
					renderer : function(value) {
						return failTmnlNoValue;
					}
			},{
				header : '用户名称',
				dataIndex : 'consName',
				sortable : true,
				//hidden : true,
				align : 'center',
				renderer : function(val) {
					var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
							+ val + '</div>';
					return html;
				}
			}, {
				header : '用户编号',
				dataIndex : 'consNo',
				sortable : true,
				align : 'center'
			}, {
				header : '电能表资产号',
				dataIndex : 'assetNo',
				sortable : true,
				align : 'center'
			},{
				header : '抄表示数',
				dataIndex : 'meterData',
				sortable : true,
				align : 'center',
				renderer:function(val){
					if(val && val!=''){
						if(!isNaN(val)){
							return "<span style='color:green'>"+val+"</span>";
						}else return "<span style='color:red'>"+val+"</span>";
					}else return '';
				}
			},{
				header : '抄表时间',
				dataIndex : 'dataDate',
				sortable : true,
				align : 'center',
				renderer : function(val) {
					if(val == null ) val = '';
					var html = '<div style="color:green" align = "center" ext:qtitle="补抄时间" ext:qtip="' + val + '">'
							+ val + '</div>';
					return html;
				}
			}]);

	var lowQEGrid = new Ext.grid.GridPanel({
		title:'供电所明细',
		autoScroll : true,
		hidden : false,
//		loadMask : true,
		region : 'center',
		viewConfig : {
			forceFit : true
		},
		cm : groupCMFail,
		sm : asfail_gridSm,
		store : failArrayStore,
		bbar : new Ext.ux.MyToolbar({
					store : failArrayStore
				})
		});
		
/**补抄弹出界面-----------------------------------------------------------*/
var failWindowCm = new Ext.grid.ColumnModel([{
				header : '注册序号',
				dataIndex : 'regSn',
				sortable : true,
				align : 'center'
			},{
				header : '用户名称',
				dataIndex : 'consName',
				sortable : true,
				//hidden : true,
				align : 'center',
				renderer : function(val) {
					var html = '<div align = "left" ext:qtitle="补抄时间" ext:qtip="' + val + '">'
							+ val + '</div>';
					return html;
				}
			}, {
				header : '用户编号',
				dataIndex : 'consNo',
				sortable : true,
				align : 'center'
			},{
				header : '抄表示数',
				dataIndex : 'meterData',
				sortable : true,
				align : 'center',
				renderer:function(val){
					if(val && val!=''){
						if(!isNaN(val)){
							return "<span style='color:green'>"+val+"</span>";
						}else return "<span style='color:red'>"+val+"</span>";
					}else return '';
				}
			},{
				header : '抄表时间',
				dataIndex : 'dataDate',
				sortable : true,
				align : 'center',
				renderer : function(val) {
					if(val == null ) val = '';
					var html = '<div style="color:green" align = "center" ext:qtitle="补抄时间" ext:qtip="' + val + '">'
							+ val + '</div>';
					return html;
				}
			}]);
var failWindowStore = new Ext.data.Store({
		proxy : new Ext.data.MemoryProxy(),
		reader : new Ext.data.ArrayReader({
			idIndex : 0,
			fields : [{name : 'regSn'},{name : 'consName'},
					{name : 'consNo'},{name : 'meterData'},{name : 'dataDate'}
				]})
	});
	failWindowStore.on('load',function(){
		failWindowStore.sort('regSn','ASC');		
	});
var failWindowGrid = new Ext.grid.GridPanel({
		cm : failWindowCm,
		store : failWindowStore,
		stripeRows : true,
		autoWidth : true,	
		autoScroll : true,
		tbar : [{
			xtype : 'label',
			id : 'failLabel',
			html : "<font color='red'>任务执行中...</font>"
		}]
	});
var failWindow = new Ext.Window({
		title : '补抄数据',
		frame : true,
		width : 750,
		height : 450,
		layout : "fit",
		autoScroll : true,
		modal : true,
		plain : true, // 设置背景颜色
		resizable : false, // 不可移动
		buttonAlign : "center", // 按钮的位置
		closeAction : "hide", // 将窗体隐藏而并不销毁
		items:[failWindowGrid],
		buttons:[{
			text:'停止抄表',
			handler : function(){
				ov = false;
				Ext.getCmp('failLabel').setText("<font color='red'>任务执行中止</font>",false);
			}
		},{
			text:'关闭',
			handler : function(){
				failWindow.hide();
			}
		}]
	});
	failWindow.on('beforehide',function(w){
		ov = false;
		failWindowGrid.removeAll();
		Ext.getCmp('failLabel').setText("<font color='red'>任务执行中...</font>",false);
	});	
/** 补抄数据，提交的action处理 */
function getDataByDate(tmnlAssetNo,regSnArray,i){
	if (!ov) {
		return;
	}
	Ext.Ajax.request({
		url : 'baseapp/lowQualityEvaluate!queryMeterDataByDate.action',
		params : {
			tmnlAssetNo : tmnlAssetNo,
			regSn : regSnArray[i],
			queryDate : FailDateField.getValue().format('Y-m-d')
		},
		success : function(response) {
			if (!ov) {
				return;
			}
			var result = Ext.decode(response.responseText);
			if (!result || !result.success) {
				ov = false;
				return true;
			}
			if (result.msg && result.msg != '') {
				ov = false;
				Ext.Msg.alert('提示', result.msg);
				return true;
			}
			
			for (var l = 0; l < result.lowqeList.length; l = l + 1) {
				var rec = groupStoreFail.getById(result.lowqeList[l].regSn);
				var resultRec = failWindowStore.getById(result.lowqeList[l].regSn);
				if (rec != null) {
					rec.set('meterData',result.lowqeList[l].meterData);
					rec.set('dataDate',result.lowqeList[l].dataDate);
					rec.commit();
				}
				if (resultRec != null) {
					resultRec.set('meterData',result.lowqeList[l].meterData);
					resultRec.set('dataDate',result.lowqeList[l].dataDate);
					resultRec.commit();
					
					failArrayStore.proxy = new Ext.ux.data.PagingMemoryProxy(detailRecsToArray(groupStoreFail));
					
					for (var l = 0; l < result.lowqeList.length; l++) {
						var failArrayRec = failArrayStore
								.getById(result.lowqeList[l].regSn);
						failArrayRec.set('meterData',
								result.lowqeList[l].meterData);
						failArrayRec.set('dataDate',
								result.lowqeList[l].dataDate);
						failArrayRec.commit();
					}
				}
			}
		},
		callback: function(){
			var j= i+1;
			if(j<regSnArray.length){
				getDataByDate(tmnlAssetNo,regSnArray,j);
			}else{
				Ext.getCmp('failLabel').setText("<font color='red'>任务执行结束</font>",false);
			}
		}
	});
}		

/** */
if(typeof(lowSign) != 'undefined' && lowSign != null && lowSign == 'lowQualityEvaluate'){
	lowSign = '';
	taiQuTextField.setValue(lowConsNameValue);
	failTmnlNoValue = lowTmnlAssetNoValue;
	FailDateField.setValue(lowqeEndDate);
	
	genQualityDataFail();
}
//监听左边树点击事件
var treeListener = new LeftTreeListener({
	 modelName : '失败明细',
	 processClick : function(p, node, e) {
		var obj = node.attributes.attributes;
		var type = node.attributes.type;
		if(type == 'usr'){
			if(obj.consType != '2' || taiQuTextField.getValue() == obj.consName){
				return true;
			}
			taiQuTextField.setValue(obj.consName);
			failTmnlNoValue = obj.tmnlAssetNo;
			lowQEGrid.getGridEl().mask('加载中...');
			genQualityDataFail();
		}else {
			return true;
		}
   	},
   	processUserGridSelect:function(cm,row,record){
   		if(taiQuTextField.getValue() == record.get('consName')){
   			return true;
   		}
   		taiQuTextField.setValue(record.get('consName'));
		failTmnlNoValue = record.get('tmnlAssetNo');
		lowQEGrid.getGridEl().mask('加载中...');
		genQualityDataFail();
    }
});
Ext.onReady(function(){
			Ext.QuickTips.init();
			var qualityViewPanel = new Ext.Panel({
				border:false,
				frame:false,
				layout:'border',
				items:[qualityTopPanel,lowQEGrid]
			});
			renderModel(qualityViewPanel, '失败明细');
		});
		
Ext.getCmp('失败明细').on('activate', function() {	
//	alert(lowSign);
	if(typeof(lowSign) == 'undefined' || lowSign == null || lowSign != 'lowQualityEvaluate'){
		return true;
	}
	lowSign = '';
//	alert(consNameValue);
	
	taiQuTextField.setValue(lowConsNameValue);
	failTmnlNoValue = lowTmnlAssetNoValue;
	FailDateField.setValue(lowqeEndDate);
	
	lowQEGrid.getGridEl().mask('加载中...');
	genQualityDataFail();
});