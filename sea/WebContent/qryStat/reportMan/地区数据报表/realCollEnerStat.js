//实采电量情况统计报表
/**
 * @author 陈国章
 * @2010-08
 */
var reportParamsPanel = new Ext.Panel({
				border : true,
				region : 'north',
				bodyStyle : 'padding:10px 0px',
				// autoHeight:true,
				layout : 'column',
				height : 40,
				items : [{
							border : false,
							columnWidth:.56,
							
							labelWidth : 80,
							layout : 'column',
							items : [{
							     layout:"form",
								 border:false,
								 columnWidth:.50,
								 labelAlign : 'right',
								 items:[{
								       
										id : 'areaDatRepRCESStartTime',
										xtype : 'datefield',
										value : new Date().add(Date.DAY,-6),
										fieldLabel : '起始时间',
										format : "Y-m-d"
									}]
							},{  layout:"form",
								 border:false,
								 columnWidth:.50,
								 labelAlign : 'right',
								 items:[{
										
										id : 'areaDatRepRCESEndTime',
										xtype : 'datefield',
										// labelSeparator : ' ',
										
										value : new Date(),
										fieldLabel : '结束时间',
										format : "Y-m-d"
									}]
									}]
						}]
			});
var defBool = reportTypeGrid.getSelectionModel().getSelected()
.get('defBoolean');

var sefDefType = reportTypeGrid.getSelectionModel().getSelected().get(
		'reportType');
var modName = sefDefType;
var sefDefName = reportTypeGrid.getSelectionModel().getSelected().get(
		'reportName');
var tBarText;
var tBarText;
if (defBool == '0') {
	tBarText = "保存为模板";
	sefDefType = "自定义" + sefDefType;
	sefDefName = "自定义" + sefDefName;
} else if (defBool == '1') {
	tBarText = "更新模板";
}
var params=[];
reportParamStore = new Ext.ux.LocalStore({
	dataKey : "orgNo",
	// url:'./qrystat/reportTypeQueryAction!queryTemplateConsInfo.action',
	fields : ["abc", "bcd", "orgNo", "orgType", "orgName"]
});
reportParamStore.on('load', function() {
	Ext.getCmp('areaDatRepRCESGrid').getSelectionModel().selectAll();
});
if(defBool==1){
	Ext.Ajax.request({
		url : "./qrystat/reportTypeQueryAction!queryOrgNoList.action",
		params : {
			"reportId" : reportTypeGrid.getSelectionModel()
					.getSelected().get('reportId')
		},
		success : function(response) {
			var o = Ext.decode(response.responseText);
			reportParamStore.addDatas(o.treeInfo);
			
		}
	});

}

userTerminal = new Ext.grid.CheckboxSelectionModel( {});
var userTerminaNumber = new Ext.grid.RowNumberer( {
	renderer : function(v, p, record, rowIndex) {
		var startRow = 0;
		if (reportParamStore && reportParamStore.lastOptions
				&& reportParamStore.lastOptions.params) {
			startRow = reportParamStore.lastOptions.params.start;
		}
		return startRow + rowIndex + 1;
	}
});

// 报表参数列模型
var reportParamCm = new Ext.grid.ColumnModel( [ userTerminaNumber,
		userTerminal, {
			header : '供电单位编号',
			dateIndex : 'orgNo',
			align : 'left'
		}, {
			header : '供电单位类型',
			dateIndex : 'orgType',
			align : 'left',
			hidden : true
		}, {
			header : '供电单位名称',
			dateIndex : 'orgName',
			align : 'left'
		} ]);

var reportTypePanel = new Ext.form.TextField( {
	name : 'areaDatRepRCESType',
	readOnly : false,
	fieldLabel : '报表类型',
	anchor : '100%',
	value : sefDefType

});

var reportNamePanel = new Ext.form.TextField( {
	name : 'areaDatRepRCESName',
	readOnly : false,
	fieldLabel : '报表名称',
	anchor : '100%',
	value : sefDefName
});

var reportTemplatInfo = new Ext.form.FormPanel( {
	title : '报表属性修改',
	region : 'center',
	frame : false,
	layout : 'table',
	labelAlign : 'right',
	monitorResize : true,
	border : false,
	bodyStyle : 'padding:5px',
	layoutConfig : {
		columns : 1
	},
	defaults : {
		width : 450,
		height : 35,
		labelWidth : 100
	},
	items : [
			{
				border : false,
				layout : 'form',
				items : [ reportTypePanel ]
			},
			{
				border : false,
				layout : 'form',
				items : [ reportNamePanel ]
			},
			{
				border : false,
				layout : 'form',
				width : 450,
				height : 100,
				colspan : 2,
				items : [ {
					xtype : 'textarea',
					width : 150,
					height : 60,
					fieldLabel : '报表描述',
					readOnly : false,
					labelSeparator : '',
					name : 'areaDatRepRCESDescription',
					anchor : '100%',
					value : reportTypeGrid.getSelectionModel().getSelected()
							.get('reportParam')

				} ]
			} ]
});
var templateInfoWindow = new Ext.Window( {
	title : '',
	frame : true,
	width : 525,
	height : 250,
	layout : "border",
	autoScroll : true,
	modal : true,
	plain : true,// 设置背景颜色
	resizable : false,// 不可移动
	buttonAlign : "center",// 按钮的位置
	closeAction : "hide",
	items : [ reportTemplatInfo ],
	buttons : [ {
		text : '确定',
		handler : function() {
			// 将窗体隐藏而并不销毁
		templateManager();
		this.ownerCt.ownerCt.close();
	}
	}, {
		text : '取消',
		handler : function() {
			this.ownerCt.ownerCt.close();
		}
	} ]
});
function templateManager() {
	Ext.getCmp('areaDatRepRCESGrid').getStore().eachAll(function(key,v){
	    params.push(v["orgNo"]);});
	var consNoList = '';
	for ( var i = 0; i < params.length; i++) {
		var record = params[i];
		consNoList = consNoList + record;
		if (i < (params.length - 1)) {
			consNoList = consNoList + ',';
		}
	}

	Ext.Ajax.request( {
		url : './qrystat/reportTypeQueryAction!saveConsNoList.action',
		method : 'post',
		params : {
			'reportId' : reportTypeGrid.getSelectionModel().getSelected().get(
					'reportId'),
			'reportType' : reportTemplatInfo.getForm().findField(
					"areaDatRepRCESType").getValue(),
			'reportName' : reportTemplatInfo.getForm().findField(
					"areaDatRepRCESName").getValue(),
			'consNoList' : consNoList,
			'reportParam' : reportTemplatInfo.getForm().findField(
					"areaDatRepRCESDescription").getValue()
		},
		success : function(response, opts) {
			Ext.Msg.alert('提示信息', '保存成功');
		},
		waitTitle : '请稍后',
		waitMsg : '正在保存用户列表...'
	});
}

reportParamsPanelSecond = new Ext.Panel( {
	layout : 'border',
	border : false,
	items : [ // reportParamsPanel, bottomParamGridPanel]//,

			reportParamsPanel,
			{
				xtype : 'panel',
				height : 120,
				region : 'center',
				layout : "fit",
				autoScroll : true,
				border : false,
				width : 800,
				plain : true,
				items : [ {
					xtype : 'grid',
					mode : 'remote',
					id : 'areaDatRepRCESGrid',
					name : 'areaDatRepRCESGrid',
					// ds : reportParamStore,
					region : 'south',
					cm : reportParamCm,
					store : reportParamStore,
					sm : userTerminal,
					loadMask : false,
					autoScroll : true,
					autoWidth : true,
					stripeRows : true,
					bbar : new Ext.ux.MyToolbar( {
						store : reportParamStore
					}),
					viewConfig : {
						forceFit : true
					}

				} ],
				tbar : [
						{
							xtype : 'label',
							html : "<font font-weight:bold;>实采电量情况统计报表</font>"
						},'-',{
							xtype : "checkbox",
							boxLabel : "全选",
							id : "areaDatRepRCES_selectAll",
							listeners : {
								check : function(c, v) {
									if (v) {
										reportParamStore.setAllSelect(true);
										userTerminal.selectAll();
									} else {
										reportParamStore.clearAllSelect();
										userTerminal.clearSelections();										
									}
								}
							}
						},'->',
						{
							text : tBarText,
							iconCls : 'plus',
							handler : function() {
								var recs = userTerminal.getSelections();
								if (recs.length = 0 || Ext.isEmpty(recs)) {
									Ext.MessageBox.alert("提示", "请选择数据!");
									return;
								} else {
									templateInfoWindow.show();
								}
							}
						},
						{
							text : '删除所选用户',
							iconCls : 'minus',
							width : 30,
							handler : function() {
							if (Ext.getCmp('areaDatRepRCES_selectAll').checked) {
								reportParamStore.clearAll();
//								clearDataState();
//								unlockGrid();
							} else {
								var selectTerminal =userTerminal.getSelections();
//								clearDataState(selectTerminal, 'cons_no');
								reportParamStore.removeDatas(selectTerminal);
							}}
						} ]
			} ]
});
//reportParamStore.proxy = new Ext.data.HttpProxy( {
//	url : './qrystat/reportTypeQueryAction!queryLeftTreeInfo.action'
//});

// 监听左边树点击事件
treeListener = new LeftTreeListener( {
	modelName : modName,
	processClick : function(p, node, e) {
		var obj = node.attributes.attributes;
		var type = node.attributes.type;
		if (type == 'org') {
			var orgType = obj.orgType;
			var orgNo = obj.orgNo;
			if (orgType == '04') {
				var object = new Object();
				object.orgNo = orgNo;
				object.orgName = node.text;
				object.orgType = orgType;
				var records = new Ext.data.Record(object);
				reportParamStore.addDatas(records);
				var array = new Array(records);
				Ext.getCmp('areaDatRepRCESGrid').getSelectionModel()
						.selectRecords(array, true);
			} else if (orgType == '02' || orgType == '03') {
				
				Ext.Ajax.request({
					url : "./qrystat/reportTypeQueryAction!queryLeftTreeInfo.action",
					params : {
					         orgNo : orgNo,
					         orgType : orgType
					},
					success : function(response) {
						var o = Ext.decode(response.responseText);
						reportParamStore.addDatas(o.treeInfo);
//						Ext.getCmp('metDatRepMERGrid')
//								.getSelectionModel().selectAll();
					}
				});
	
			}
		} else {
			return true;
		}
	}
});
getParamsUrl = function() {

	var startTime = Ext.getCmp("areaDatRepRCESStartTime").getRawValue();
	var endTime = Ext.getCmp("areaDatRepRCESEndTime").getRawValue();
	var consList = new Array();
	var recs = userTerminal.getSelections();
	ParamsUrl = "&startDate='" + startTime + "'&endDate='" + endTime
			+ "'&orgNoList=";
	if (startTime <= endTime) {
		timeSelect = true;
	} else {
		Ext.MessageBox.alert("提示", "起始时间必须小于截止时间!");
		timeSelect = false
		return;
	}
	var consList = new Array();
	var recs = userTerminal.getSelections();
	if (null == recs || 0 == recs.length) {
		Ext.MessageBox.alert("提示", "请选择数据!");
		dataSelect = false;
		return;
	} else {
		dataSelect = true;

	}
	for ( var i = 0; i < recs.length; i++) {
		consList[i] = recs[i].get('orgNo');
		ParamsUrl = ParamsUrl + "'" + consList[i] + "'";
		if (i < (recs.length - 1))
			ParamsUrl = ParamsUrl + ",";
	}
};