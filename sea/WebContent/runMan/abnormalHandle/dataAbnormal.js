Ext.onReady(function(){
//-------------------------------------------------------------查询 begin	
	
	//供电区域
	var da_nodeName = new Ext.form.TextField({
		    fieldLabel : "供电区域<font color='red'>*</font>",
			name : 'da_nodeName',
			id:'da_nodeName',
			readOnly:true,
			labelStyle : "text-align:right;",
			emptyText : '请输入供电区域',
			width :150,
			anchor: '95%'
	});
	
	//定义radio选择组
    var da_radioGroup = new Ext.form.RadioGroup({
	    width : 400,
	    height :20,
	    columns : [100, 100,100],
	    fieldLabel:'数据异常类型',
	    items : [new Ext.form.Radio({
		    boxLabel : '负荷数据',
		    name : 'da-radioGroup',
		    inputValue : '1',
		    checked: true,
		    listeners : {
		    'check' : function(r, c) {
				if (c) {
				};
			}}
	    }), new Ext.form.Radio({
		    boxLabel : '电量数据',
		    name : 'da-radioGroup',
		    inputValue : '2',
		    listeners : {
		    'check' : function(r, c) {
				if (c) {
				};
			}}
	    }), new Ext.form.Radio({
		    boxLabel : '抄表数据',
		    name : 'da-radioGroup',
		    inputValue : '3',
		    listeners : {
		    'check' : function(r, c) {
				if (c) {
				};
			}}
	    })]
    });
    
    //查询按钮
	var da_queryBtn = new Ext.Button({
			text : '查询',
			name : 'da_queryBtn',
			width : 80,
		    labelSeparator:'',
		    handler:function(){
	        }
	});
	
	var daTopPanel = new Ext.Panel({
		border:false,
		bodyStyle:'padding:10px 0px',
		labelWidth:10,
		layout:'column',
		region : 'north',
		height: 40,
		items:[{
			columnWidth:.3,
			border:false,
			layout:'form',
			labelAlign:'right',
			labelWidth : 60,
			items:[da_nodeName]
		},{
			columnWidth:.5,
			border:false,
			layout:'form',
			labelSeparator:'',
			labelAlign:'right',
			labelWidth:80,
			items:[da_radioGroup]
		},{
			columnWidth:.2,
			border:false,
			layout:'form',
			items:[da_queryBtn]
		}]
	});
//-------------------------------------------------------------查询 end
	
//-------------------------------------------------------------数据异常 begin
	var da_abnormalData = [
//		{
//		ycbh:'异常编号',
//		yhbh:'用户编号',
//		zcbh:'资产编号',
//		zclb:'资产类别',
//		dnbbh:'电能表编号',
//		fssj:'发生时间',
//		ycsj:'修正数据',
//		xzsjian:'修正时间',
//		xzsj:'修正数据',
//		xzr:'修正人'
//	}
	];
	
	var da_abnormalStore = new Ext.data.JsonStore({
		data:da_abnormalData,
		fields:['ycbh','yhbh','zcbh','zclb','dnbbh','fssj','ycsj','xzsjian','xzsj','xzr']
	});
		
	var da_abnormalCM = new Ext.grid.ColumnModel([
		{header:'异常编号',dataIndex:'ycbh',sortable:true,align:'center'},
		{header:'用户编号',dataIndex:'yhbh',sortable:true,align:'center'},
		{header:'资产编号',dataIndex:'zcbh',sortable:true,align:'center'},
		{header:'资产类别',dataIndex:'zclb',sortable:true,align:'center'},
		{header:'电能表编号',dataIndex:'dnbbh',sortable:true,align:'center'},
		{header:'发生时间',dataIndex:'fssj',sortable:true,align:'center'},
		{header:'修正数据',dataIndex:'ycsj',sortable:true,align:'center'},
		{header:'修正时间',dataIndex:'xzsjian',sortable:true,align:'center'},
		{header:'修正数据',dataIndex:'xzsj',sortable:true,align:'center'},
		{header:'修正人',dataIndex:'xzr',sortable:true,align:'center'}
	]);
	
	var da_abnormalGrid = new Ext.grid.GridPanel({
		layout:'fit',
		cm:da_abnormalCM,
		store:da_abnormalStore,
		viewConfig:{
			forceFit:true
		},
		tbar : [{
			xtype:'label',
			html : "<font font-weight:bold;>数据异常</font>"
		}]
	});
//-------------------------------------------------------------数据异常 end	
	
//-------------------------------------------------------------异常处理 begin
	var da_abnormalHandleData = [
//		{
//		sj:'时间',
//		Axdy:'A相电压',
//		Bxdy:'B相电压',
//		Cxdy:'C相电压',
//		Axdl:'A相电流',
//		Bxdl:'B相电流',
//		Cxdl:'C相电流',
//		yggl:'有功功率',
//		wggl:'无功功率',
//		glys:'功率因素'
//	}
	];
	
	var da_abnormalHandleStore = new Ext.data.JsonStore({
		data:da_abnormalHandleData,
		fields:['sj','Axdy','Bxdy','Cxdy','Axdl','Bxdl','Cxdl','yggl','wggl','glys']
	});
	
	var da_abnormalHandleSM = new Ext.grid.CheckboxSelectionModel();
	
	var da_abnormalHandleCM = new Ext.grid.ColumnModel([
		da_abnormalHandleSM,
		{header:'时间',dataIndex:'sj',sortable:true,align:'center'},
		{header:'A相电压q',dataIndex:'Axdy',sortable:true,align:'center'},
		{header:'B相电压',dataIndex:'Axdy',sortable:true,align:'center'},
		{header:'C相电压',dataIndex:'Cxdy',sortable:true,align:'center'},
		{header:'A相电流',dataIndex:'Axdl',sortable:true,align:'center'},
		{header:'B相电流',dataIndex:'Bxdl',sortable:true,align:'center'},
		{header:'C相电流',dataIndex:'Cxdl',sortable:true,align:'center'},
		{header:'有功功率',dataIndex:'yggl',sortable:true,align:'center'},
		{header:'无功功率',dataIndex:'wggl',sortable:true,align:'center'},
		{header:'功率因素',dataIndex:'glys',sortable:true,align:'center'}
	]);
	
	var da_abnormalHandleGrid = new Ext.grid.GridPanel({
		layout : 'fit',
		cm:da_abnormalHandleCM,
		sm:da_abnormalHandleSM,
		store:da_abnormalHandleStore,
		viewConfig:{
			forceFit:true
		},
		tbar : [{
			xtype:'label',
			html : "<font font-weight:bold;>数据修改</font>"
		}]
	});
//-------------------------------------------------------------异常处理 end	
	
//-------------------------------------------------------------处理 begin
	//异常原因
	var daTextArea = new Ext.form.TextArea({
	    border:false,
	    fieldLabel:'异常原因',
	    height:60,
	    labelSeparator:'',
		width:640
	});
	
	var handlePanel = new Ext.Panel({
		region : 'south',
		layout:'form',
		border:false,
		height:150,
		style : "padding:5px",
		items:[{
			border:false,
		    layout:'form',
		    height:65,
		    labelWidth : 60,
			labelAlign : "right",
			items:[daTextArea]
		}, {
			border:false,
		    layout:'column',
		    bodyStyle:'padding:10px 0px;',
		    items:[{
			    columnWidth:.25,
			    border:false,
			    layout:'form',
			    labelSeparator:'',
			    labelAlign:'right',
			    labelWidth : 60,
			    items:[{
				    xtype:'textfield',
				    fieldLabel:'处理人',
				    width:140,
				    anchor : '95%'
			    }]
		    },{
			    columnWidth:.17,
			    border:false,
			    layout:'form',
			    labelAlign:'right',
			    items:[{
				    xtype:'button',
				    text:'查找',
				    width:80
			    }]
		    },{
			    columnWidth:.25,
			    border:false,
			    layout:'form',
			    labelSeparator:' ',
			    labelWidth:55,
			    labelAlign:'right',
			    items:[{
			      	xtype:'combo',
				    fieldLabel:'异常状态',
			     	width:140,
			     	anchor :'95%'
			    }]
		    },{
			    columnWidth:.25,
			    border:false,
			    labelWidth:55,
			    layout:'form',
			    labelSeparator:' ',
			    labelAlign:'right',
			    items:[{
				    xtype:'datefield',
				    fieldLabel:'处理日期',
				    format: 'Y-m-d',
				    value: new Date(),
				    width:140,
				    anchor :'95%'
			    }]
		    }]
		}],
		buttonAlign:'center',
		buttons:[{
			text:'确认处理'
		},{
			text:'退出'
		}]
	});
	
	//
	var daCenterPanel = new Ext.Panel({
				border : false,
				layout : 'anchor',
				region : 'center',
				items : [{
				    border: false,
				    layout : 'fit',
				    anchor:'100% 50%',
				    items:[da_abnormalGrid]
				}, {
					 border: false,
				    layout : 'fit',
				    anchor:'100% 50%',
				    items:[da_abnormalHandleGrid]
				}]
			});
	
	//
	var dataAbnormalPanel = new Ext.Panel({
				autoScroll : true,
				border : false,
				layout:'border',
				items : [daTopPanel,daCenterPanel,handlePanel]
			});
	
	renderModel(dataAbnormalPanel,'数据异常');
//-------------------------------------------------------------处理 end			
});