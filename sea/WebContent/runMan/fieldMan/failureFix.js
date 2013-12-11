Ext.onReady(function() {
	
	//返回上部面板
	function getTopPnl(){
		//用户编号
	    var custNoTF = new Ext.form.TextField({
				fieldLabel : '用户编号',
				name : '',
				labelSeparator:'',
				anchor : '70%'
		});
		
		//用户名称
		var custNameTF = new Ext.form.TextField({
				fieldLabel : '用户名称',
				labelSeparator:'',
				name : '',
				anchor : '96%'
		});
		
		//用电地址
		var powerAddrTF = new Ext.form.TextField({
				fieldLabel : '用电地址',
				name : '',
				labelSeparator:'',
				anchor : '98%'
		});
		
		//故障现象
		var faultDescTA = new Ext.form.TextArea({
				fieldLabel : '故障现象',
				selectOnFocus : true,
				anchor: '98%',
				autoScroll: true,
	            labelSeparator:''
		});
		
		//定义上部面板
		var topPnl = new Ext.Panel({
					border : false,
					bodyStyle: 'padding:1px;',
					id : 'topPnl',
					height : 90,
					labelSeparator:'',
					layout : 'column',
					items : [{
						columnWidth : .55,
						layout: 'form',
						border : false,
						items : [{
							layout : 'column',
						    border : false,
						    labelSeparator:'',
						    bodyStyle:'padding:5px 0px 0px 0px',
						    items : [{
						        columnWidth : .5,
						        layout : 'form',
							    border : false,
							    labelWidth : 60,
							    labelAlign : "right",
							    labelSeparator:'',
							    items : [custNoTF]
						    }, {
							    columnWidth : .5,
						        layout : 'form',
							    border : false,
							    labelWidth : 60,
							    labelAlign : "right",
							    labelSeparator:'',
							    items : [custNameTF]	
						    }]
						}, {
						    layout : 'form',
						    border : false,
						    labelWidth : 60,
						    labelAlign : "right",
						    labelSeparator:'',
						    bodyStyle:'padding:10px 0px 0px 0px',
						    items : [powerAddrTF]
						}]
					}, {
						columnWidth : .45,
						layout : 'form',
					    border : false,
					    bodyStyle: 'padding:1px;',
					    labelAlign : "right",
					    labelWidth : 60,
					    labelSeparator:'',
					    items : [faultDescTA]
					}]
				});
			return topPnl;
	}
	
	//返回底部面板
	function getBottomPnl(){
		
		//处理方式
		var dealTypeTF = new Ext.form.TextField({
				fieldLabel : '处理方式',
				name : '',
				labelSeparator:'',
				anchor : '98%'
		});
		
		//故障原因
		var faultReasonTA = new Ext.form.TextArea({
				fieldLabel : '故障原因',
				selectOnFocus : true,
				anchor: '98%',
				autoScroll: true,
	            labelSeparator:''
		});
		
		//处理日期
		var dealDate = new Ext.form.DateField({
				fieldLabel : '处理日期',
				name : 'date',
				width : 150,
			    value : new Date(),
			    labelSeparator:''
		});
		
		//处理人员，暂时定义
		var humanDataStore = new Ext.data.ArrayStore({
					fields : ['humanId', 'humanContent'],
					data : [['humanData1', '张三'], ["humanData2", '李四']]
		});

		//定义购电标志
		var humanComboBox = new Ext.form.ComboBox({
					fieldLabel : '处理人员',
					store : humanDataStore,
					bodyStyle: 'padding:10px;',
					triggerAction : 'all',
					mode : 'local',
					valueField : 'humanId',
					displayField : 'humanContent',
					width: 150,
					emptyText : '请选择人员',
					selectOnFocus : true,
					labelSeparator:''
		});
		
		//定义底部面板
		var bottomPnl = new Ext.Panel({
					border : false,
					bodyStyle: 'padding:1px;',
					id : 'bottomPnl',
					height : 200,
					labelSeparator:'',
					layout : 'column',
					buttonAlign : 'right',
					items : [{
						columnWidth : .55,
						layout: 'form',
						border : false,
						items : [{
							layout : 'form',
						    border : false,
						    labelSeparator:'',
						    bodyStyle:'padding:5px 0px 0px 0px',
						    items : [{
						        layout : 'form',
							    border : false,
							    labelWidth : 60,
							    labelAlign : "right",
							    labelSeparator:'',
							    items : [faultReasonTA]
						    }, {
						        layout : 'form',
						        border : false,
						        labelWidth : 60,
						        labelAlign : "right",
						        labelSeparator:'',
						        bodyStyle:'padding:10px 0px 0px 0px',
						        items : [dealTypeTF]
						    }]
						}]
					}, {
						columnWidth : .45,
						layout : 'form',
					    border : false,
					    bodyStyle: 'padding:1px;',
					    labelAlign : "right",
					    labelWidth : 60,
					    labelSeparator:'',
					    items : [{
					    	layout : 'form',
					    	border : false,
					    	bodyStyle: 'padding:5px 0px 0px 0px',
					    	items : [humanComboBox]
					    }, {
					        layout : 'form',
					        border : false,
					    	bodyStyle:'padding:10px 0px 0px 0px',
					    	items : [dealDate]
					    }]
					}],
					buttons:[
						{text:'保存返回'}
		            ]
				});
			return bottomPnl;
	}
	
	//定义整个页面面板
	var panel=new Ext.form.FormPanel({
		border : false,
        items: [
        	getTopPnl(), getBottomPnl()
        ],
		monitorResize : true,
		applyTo: 'failureFix'
	});

});