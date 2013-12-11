//grid列表的标题
var dataTitle = '突变数据、零数据';
if(queryTl_dataType!=null){
	var dataType = queryTl_dataType;
	if(dataType == '01') dataTitle = '突变数据';
	else if(dataType == '02') dataTitle = '零数据';
}

var textForm = new Ext.form.TextField({
	fieldLabel:'节点名',
	labelSeparator:'',
	name:'orgName',
	emptyText:'请选择左边节点',
	anchor:'100%'
});
var queryTop = new Ext.Panel({
	labelAlign : 'right',
	height:30,
	bodyStyle : 'padding:0px 0px 0px 0px',
	layout : 'column',
	border : false,
	items : [{
				columnWidth : .26,
				border : false,
				labelAlign : "right",
				layout:'form',
				labelWidth : 50,
				items : [textForm]
			},{
                columnWidth:.2,              //label后面加个红色的"*"
                layout: 'form',
                border:false,
                items: [{
					xtype:"label",
					html:'<font color=#ff0000>&nbsp;*</font>'  
            }]
		}]
});

var date1Form = new Ext.form.DateField({
	fieldLabel : '从',
	value: new Date().add(Date.DAY,-6),
	format: 'Y-m-d',
	name : 'qtStartDate'
});
var date2Form = new Ext.form.DateField({
	fieldLabel : '到',
	value: new Date(),
	format: 'Y-m-d',
	name : 'qtEndDate'
});
var tblRadioGroup = new Ext.form.RadioGroup({
	width : 255,
	items : [new Ext.form.Radio({
				boxLabel : '抄表类',
				name : 'qtQueryType',
				checked : true,
				inputValue : '01'
			}),
			new Ext.form.Radio({
				boxLabel : '负荷类',
				name : 'qtQueryType',
				inputValue : '02'
			}),
			new Ext.form.Radio({
				boxLabel : '电能质量类',
				name : 'qtQueryType',
				inputValue : '03'
			})]
});
if(queryTl_raidoValue!=null){
	tblRadioGroup.setValue(queryTl_raidoValue);
}
if(queryTl_startDate!=null){
	date1Form.setValue(queryTl_startDate);
}
if(queryTl_endDate!=null){
	date2Form.setValue(queryTl_endDate);
}
if(queryTl_compName!=null){
	textForm.setValue(queryTl_compName);
}

var queryBott = new Ext.form.FormPanel({
	border : false,
	height:30,
	layout:'column',
	items:[{
		columnWidth:.36,
		border:false,
		bodyStyle:'padding:2px',
		layout:'form',
		labelWidth:1,
		items:[tblRadioGroup]
	},{
		columnWidth:0.18,
		border:false,
		layout:'form',
		labelWidth : 30,
		labelAlign : "right",
		items : [date1Form]
	},{
		columnWidth:0.23,
		border:false,
		layout:'form',
		labelWidth : 30,
		labelAlign : "right",
		items : [date2Form]
	},{
		columnWidth : .08,
		border : false,
		layout:'form',
		items : [{
					xtype : 'button',
					text : '查询',
					width : 50,
					handler : function(){
						tlStoreLoad();
					}
				}]
	}]
});
var queryPanel = new Ext.Panel({
			labelAlign : 'right',
			frame : false,
			height : 70,
			region:'north',
			bodyStyle : 'padding:5px 0px 0px 0px',
			border : false,
			items : [queryTop,queryBott]
		});

var cm = new Ext.grid.ColumnModel([
	{header:'供电单位',dataIndex:'orgName',align:'center'},
	{header:'用户编号',dataIndex:'consNo',align:'center'},
	{header:'用户名称',dataIndex:'consName',align:'center'},
	{header:'终端地址',dataIndex:'terminalAddr',align:'center'},
	{header:'数据值',dataIndex:'dataValue',align:'center'},
	{header:'数据时间',dataIndex:'dataTime',align:'center'},
	{header:'修正时间',dataIndex:'repairTime',align:'center'},
	{header:'发布时间',dataIndex:'publishTime',align:'center'}
]);
var tlDataStore = new Ext.data.JsonStore({
	url : "baseapp/dataPublish!getTblDataInfo.action",
	fields : [ 'orgName', 'consNo', 'consName', 'terminalAddr', 'dataValue',
	'dataTime',	'repairTime', 'publishTime'],
	root : "tblDataList"
});

//grid store 加载数据
function tlStoreLoad(){
	tlDataStore.load({
		params:{qtQueryType:tblRadioGroup.getValue().getRawValue(),
			qtStartDate:date1Form.getValue().format('Y-m-d'),
			qtEndDate:date2Form.getValue().format('Y-m-d')}
	});	
}

var tlDataGrid = new Ext.grid.GridPanel({
	title : dataTitle,
	cm : cm,
	store : tlDataStore,
	region : 'center',
	autoWidth : true,
	height : 400,
	monitorResize : true,
	stripeRows : true,
	autoScroll : true,
	border : true,
	viewConfig : {
			forceFit:false
		},
	bbar : new Ext.ux.MyToolbar({
				store : tlDataStore
			})
});
Ext.onReady(function(){	
	Ext.QuickTips.init();
	
	var viewPanel = new Ext.Panel({
		bodyStyle:'padding:5px 5px 5px 5px',
		layout: 'border' ,
		monitorResize : true,
		items: [queryPanel,tlDataGrid],
		border : false
	});
	renderModel(viewPanel,'突变数据、零数据查询');
	tlStoreLoad();
});