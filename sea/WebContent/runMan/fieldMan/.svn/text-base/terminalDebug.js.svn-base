/**
 * 现场调试
 * 
 * @author jiangweichao
 */
Ext.onReady(function() {

	//返回上部面板的右边panel
	function getRightPnl() {
		//购电标志数据
		var flgDataStore = new Ext.data.ArrayStore({
					fields : ['flgID', 'flgText'],
					data : [['flgData1', '传单时间'], ["flgData2", '操作时间']]
		});

		//定义购电标志
		var flgComboBox = new Ext.form.ComboBox({
			        id : 'td_flgComboBox',
					fieldLabel : '购电标志',
					store : flgDataStore,
					bodyStyle: 'padding:10px;',
					triggerAction : 'all',
					mode : 'local',
					valueField : 'flgID',
					displayField : 'flgText',
					width: 90,
					emptyText : '传单时间',
					selectOnFocus : true
		});
				
		//传单编号		
		var leafletTF = new Ext.form.TextField({
			    id : 'td_leafletTF',
				fieldLabel : '传单编号',
				name : '',
				labelSeparator:'',
				allowBlank : false,
				blankText : '传单编号不能为空',
				anchor : '90%'
		});
		
		//终端局号
		var tmnlTF = new Ext.form.TextField({
			    id : 'td_tmnlTF',
				fieldLabel : '终端局号',
				name : '',
				labelSeparator:'',
				anchor : '90%'
		});
		
		//表计局号
		var consMeterTF = new Ext.form.TextField({
			    id : 'td_consMeterTF',
				fieldLabel : '表计局号',
				name : '',
				labelSeparator:'',
				anchor : '80%'
		});
		
		//户号
		var consNoTF = new Ext.form.TextField({
			    id : 'td_consNoTF',
				fieldLabel : '户号',
				name : '',
				labelSeparator:'',
				anchor : '90%'
		});
		
		//开始日期
		var startDate = new Ext.form.DateField({
			id : 'td_startDate',
			fieldLabel : '从',
			name : 'startdate',
			anchor : '90%',
		    value : new Date().add(Date.DAY,-5),
		    labelSeparator:''
	    });
	
	    //结束日期
	    var endDate = new Ext.form.DateField({
	    	id : 'td_endDate',
			fieldLabel : '到',
			name : 'enddate',
			anchor : '90%',
		    value : new Date(),
		    labelSeparator:''
	    });
		
	    //定义上部面板的右边panel
		var rightFormPnl = new Ext.Panel({
					labelAlign : 'left',
					border : false,
					bodyStyle: 'padding:1px;',
					buttonAlign: 'center',
					height : 120,
					layout : 'form',
					items : [{
						layout : 'column',
						border : false,
						bodyStyle:'padding:10px 0px 0px 0px',
						items : [{
							columnWidth : .25,
							layout : 'form',
							border : false,
							labelWidth : 60,
							labelAlign : "right",
							items : [leafletTF]
						}, {
							columnWidth : .25,
							layout : 'form',
							border : false,
							labelWidth : 60,
							labelAlign : "right",
							items : [tmnlTF]
						}, {
							columnWidth : .5,
							layout : 'form',
							border : false,
							labelWidth : 60,
							labelAlign : "right",
							items : [consMeterTF]
						}]
					}, {
						layout : 'column',
						border : false,
						items : [{
							columnWidth : .25,
							layout : 'form',
							border : false,
							labelWidth : 60,
							bodyStyle:'padding:10px 0px 0px 0px',
							labelAlign : "right",
							items : [consNoTF]
						}, {
							columnWidth : .75,
							layout : 'column',
							bodyStyle:'padding:10px 0px 0px 0px',
							border : false,
							items : [{
								columnWidth : .28,
								border : false,
								bodyStyle:'padding:0px 0px 0px 15px',
							    items : [flgComboBox]
							}, {
								columnWidth : .36,
								layout : 'form',
							    border : false,
							    labelWidth:18,
							    labelAlign : "right",
							    items : [startDate] 
							}, {
								columnWidth : .36,
								layout : 'form',
							    border : false,
							    labelWidth:18,
							    labelAlign : "right",
							    items : [endDate] 
							}]
						}]
					}],
					buttons:[
						{text:'查询'},
			            {text:'手工触发'},
			            {text:'安装确认'},
			            {text:'安装反馈'},
			            {text:'加入群组'}
		            ]
		});
		return rightFormPnl;
	}
	
	//返回数据Grid面板
	function getInstListPnl() {
		var instListSm = new Ext.grid.CheckboxSelectionModel();

		var instListCm = new Ext.grid.ColumnModel([instListSm, 
		    {header : '传单编号', sortable: true, dataIndex : 'leafletCode', align : 'center'}, 
		    {header : '供电单位', sortable: true, dataIndex : 'branch', align : 'center'}, 
		    {header : '户号', sortable: true, dataIndex : 'consNo', align : 'center'}, 
		    {header : '终端编号', sortable: true, dataIndex : 'terminalNo', align : 'center'}, 
		    {header : '表计局号', sortable: true, dataIndex : 'assetNo', align : 'center'}, 
		    {header : '流程进度', sortable: true, dataIndex : 'flowSchedule', align : 'center'}, 
		    {header : '传单时间', sortable: true, dataIndex : 'leafletTime', align : 'center', renderer: Ext.util.Format.dateRenderer('Y-m-d')}, 
		    {header : '操作时间', sortable: true, dataIndex : 'operateTime', align : 'center', renderer: Ext.util.Format.dateRenderer('Y-m-d')}, 
		    {header : '备注', sortable: true, dataIndex : 'remark', align : 'center'}, 
		    {header : '完成时间', sortable: true, dataIndex : 'finishTime', align : 'center', renderer: Ext.util.Format.dateRenderer('Y-m-d')}, 
		    {header : '处理', sortable: true, dataIndex : 'dealing', align : 'center'}
		]);

		var instListStore = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
						url : './runman/feildman/tmnlDebug!loadDetailGridData.action'
					}),
					reader : new Ext.data.JsonReader({
								root : 'tmnlInstDetList',
								totalProperty : 'totalCount'
							}, [{
										name : 'leafletCode'
									}, {
										name : 'branch'
									}, {
										name : 'terminalNo'
									}, {
										name : 'assetNo'
									}, {
										name : 'flowSchedule'
									}, {
										name : 'leafletTime'
									}, {
										name : 'operateTime'
									}, {
										name : 'remark'
									}, {
										name : 'finishTime'
									}, {
										name : 'dealing'
									}])
				});

		var instListGrid = new Ext.grid.GridPanel({
					store : instListStore,
					cm : instListCm,
					sm : instListSm,
					autoExandColumn : 'hm',
					stripeRows : true,
					autoScroll : true,
					bbar : new Ext.ux.MyToolbar({
						store : instListStore
					})
				});
		
		// 下方的tab页
		var instListPnl = new Ext.Panel({
			        id :'instListPnl',
					layout:'fit',
					region:'center',
				    border: false,
					items : [instListGrid]
		});
		return instListPnl;
	}
	
	//返回上部面板的左边panel，采用multiselect控件
	function getMultiSelPnl(){
		//定义上部面板的左边panel
		var multiSelPnl = new Ext.Panel({
			        id :'td_multiSelPnl',
					labelAlign : 'right',
					border : false,
					bodyStyle: 'padding:1px;',
					height : 120,
					labelWidth : 8,
					buttonAlign: 'right',
					labelSeparator:'',
					autoWidth:true,
					bodyStyle:'padding:5px 0px 0px 5px',
					layout:'form',
					items : [{
						xtype: 'multiselect',
					    fieldLabel: '流程进度',
					    labelSeparator:'',
                        name: 'multiselect',
                        width: 160,
                        height: 75,
                        store: [['1','下发规约/地址失败'],
                            ['2', '与终端通讯失败(网络表)'], ['3', '中继抄表失败'], ['4', '广播抄表失败'], ['5', '下发地址/规约失败(广播后)'], 
                            ['6', '下发测量点参数失败'],['7', '下发任务失败']]
					}],
					buttons:[
					    {text:'清空'},
			            {text:'选择'}
					]
		});
		return multiSelPnl;
	}

	//返回上部面板
	function getTopPnl(){
	    var topPnl = new Ext.Panel({
				border : false,
				layout : 'column',
				region : 'north',
				height:120,
				items : [{
				    columnWidth : .26,
				    border : false,
					items : [getMultiSelPnl()]
				}, {
					columnWidth : .74,
					border : false,
					items : [getRightPnl()]
				}]
	    });
	    return topPnl;
	}
			
	//定义整个页面面板
	var viewPanel=new Ext.form.FormPanel({
		layout: 'border',
		border : false,
        items: [
        	getTopPnl(),getInstListPnl()
        ]
	});
	
	renderModel(viewPanel,'现场调试');
});