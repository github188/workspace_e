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
				anchor : '100%'
		});
		
		//用电地址
		var powerAddrTF = new Ext.form.TextField({
				fieldLabel : '用电地址',
				name : '',
				labelSeparator:'',
				anchor : '100%'
		});
		
		//巡视日期
		var inspectDate = new Ext.form.DateField({
			fieldLabel : '巡视日期',
			name : 'date',
			width : 150,
		    value : new Date(),
		    labelSeparator:''
	    });
	
	    //巡视人员，暂时定义
		//处理人员，暂时定义
		var inspectManDataStore = new Ext.data.ArrayStore({
					fields : ['humanId', 'humanContent'],
					data : [['humanData1', '张三'], ["humanData2", '李四']]
		});

		//定义购电标志
		var inspectManComboBox = new Ext.form.ComboBox({
					fieldLabel : '巡视人员',
					store : inspectManDataStore,
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
		
	    //定义上部面板
		var topPnl = new Ext.Panel({
					border : false,
					bodyStyle: 'padding:1px;',
					id : 'topPnl',
					height : 90,
					labelSeparator:'',
					layout : 'column',
					items : [{
						columnWidth : .65,
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
						columnWidth : .35,
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
					    	items : [inspectManComboBox]
					    }, {
					        layout : 'form',
					        border : false,
					    	bodyStyle:'padding:10px 0px 0px 0px',
					    	items : [inspectDate]
					    }]
					}]
				});
			return topPnl;
	}
	
	 //返回中部面板
	function getMidPnl(){
		//数据正确标志
		var dataFlg = new Ext.form.Checkbox({
		        boxLabel: '数据正确标志'
		});
		
		//档案正确标志
		var deviceFlg = new Ext.form.Checkbox({
		        boxLabel: '档案正确标志'
		});
		
		//终端完好标志
		var tmnlFlg = new Ext.form.Checkbox({
		        boxLabel: '终端完好标志'
		});
		
		//通话正常标志
		var commFlg = new Ext.form.Checkbox({
		        boxLabel: '通话正常标志'
		});
		
		//接线完好标志
		var lineFlg = new Ext.form.Checkbox({
		        boxLabel: '接线完好标志'
		});
		
		//天线正确标志
		var outlineFlg = new Ext.form.Checkbox({
		        boxLabel: '天线正确标志'
		});
		
		//封闭完好标志
		var closeFlg = new Ext.form.Checkbox({
		        boxLabel: '封闭完好标志'
		});
		
		//计量装置完好标志
		var cpFlg = new Ext.form.Checkbox({
		        boxLabel: '计量装置完好标志'
		});
		
		//定义中部面板
		var midPnl = new Ext.Panel({
					border : false,
					bodyStyle: 'padding:1px;',
					id : 'midPnl',
					height : 100,
					labelSeparator:'',
					layout : 'form',
					items : [{
						layout: 'column',
			            labelWidth: 1,
			            border : false,
						items : [{
							layout : 'form',
							columnWidth : .25,
						    border : false,
						    labelSeparator:'',
						    bodyStyle:'padding:10px 0px 0px 0px',
						    items : [dataFlg]
						}, {
						   layout : 'form',
							columnWidth : .25,
						    border : false,
						    labelSeparator:'',
						    bodyStyle:'padding:10px 0px 0px 0px',
						    items : [deviceFlg]
						}, {
						   layout : 'form',
							columnWidth : .25,
						    border : false,
						    labelSeparator:'',
						    bodyStyle:'padding:10px 0px 0px 0px',
						    items : [tmnlFlg]
						}, {
						   layout : 'form',
							columnWidth : .25,
						    border : false,
						    labelSeparator:'',
						    bodyStyle:'padding:10px 0px 0px 0px',
						    items : [commFlg]
						}]
					} , {
					    layout: 'column',
			            labelWidth: 1,
			            border : false,
						items : [{
							layout : 'form',
							columnWidth : .25,
						    border : false,
						    labelSeparator:'',
						    bodyStyle:'padding:10px 0px 0px 0px',
						    items : [lineFlg]
						}, {
						   layout : 'form',
							columnWidth : .25,
						    border : false,
						    labelSeparator:'',
						    bodyStyle:'padding:10px 0px 0px 0px',
						    items : [outlineFlg]
						}, {
						   layout : 'form',
							columnWidth : .25,
						    border : false,
						    labelSeparator:'',
						    bodyStyle:'padding:10px 0px 0px 0px',
						    items : [closeFlg]
						}, {
						   layout : 'form',
							columnWidth : .25,
						    border : false,
						    labelSeparator:'',
						    bodyStyle:'padding:10px 0px 0px 0px',
						    items : [cpFlg]
						}]
					}]
				});
			return midPnl;
	}
	
	//返回底部面板
	function getBottomPnl(){
				
		//故障现象
		var faultReasonTA = new Ext.form.TextArea({
				fieldLabel : '故障现象',
				selectOnFocus : true,
				anchor: '98%',
				autoScroll: true,
	            labelSeparator:''
		});
		
		//上报缺陷
		var sendBtn = new Ext.Button({
				text : '上报缺陷',
				name : 'send',
				width : 100,
			    labelSeparator:''
		});
		
		//保存返回按钮
		var saveBtn = new Ext.Button({
				text : '保存返回',
				name : 'save',
				width : 100,
			    labelSeparator:''
		});
		
		//定义底部面板
		var bottomPnl = new Ext.Panel({
					border : false,
					bodyStyle: 'padding:1px;',
					id : 'bottomPnl',
					height : 150,
					labelSeparator:'',
					layout : 'column',
					items : [{
						columnWidth : .75,
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
						    }]
						}]
					}, {
						columnWidth : .25,
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
					    	items : [sendBtn]
					    }, {
					        layout : 'form',
					        border : false,
					    	bodyStyle:'padding:20px 0px 0px 0px',
					    	items : [saveBtn]
					    }]
					}]
				});
			return bottomPnl;
	}
	
	//定义整个页面面板
	var panel=new Ext.form.FormPanel({
		border : false,
        items: [
        	getTopPnl(), getMidPnl(), getBottomPnl()
        ],
		monitorResize : true,
		applyTo: 'inspectResult'
	});

});