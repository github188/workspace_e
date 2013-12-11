var zfbz;

function openWindow() {
	// ------------------------资费标准 窗口 开始
	//zfbz资费标准
	var form_zfbz = new Ext.form.FormPanel({
				name: 'form_zfbz',
				bodyStyle : 'padding:12px 0px 0px 0px',
				labelAlign : 'right',
				labelWidth : 60,
				border : false,
				height : 50,
				layout : 'column',
				items : [{
							border : false,
							columnWidth : .5,
							bodyStyle : 'padding:0px 0px 0px 60px',
							layout : 'form',
							items : [{
										xtype : 'textfield',
										fieldLabel : 'GPRS包月',
										width : 80,
										name:'gprsStandard',
										labelSeparator : ' ',
										readOnly : true
									}]
						}, {
							border : false,
							columnWidth : .1,
							bodyStyle : 'padding:7px 0px 0px 0px',
							layout : 'form',
							items : [{
										xtype : "label",
										html : 'MB，'
									}]
						}, {
							border : false,
							columnWidth : .3,
							bodyStyle : 'padding:7px 0px 0px 0px',
							layout : 'form',
							items : [{
										xtype : "label",
										html : '超过部分0.001元/KB'
									}]
						}]
			});
	
	var form1_zfbz = new Ext.form.FormPanel({
				name : 'form1_zfbz',
				bodyStyle : 'padding:12px 0px 0px 0px',
				labelAlign : 'right',
				labelWidth : 60,
				height : 50,
				border : false,
				layout : 'column',
				items : [{
							border : false,
							columnWidth : .5,
							bodyStyle : 'padding:0px 0px 0px 60px',
							layout : 'form',
							items : [{
								xtype : 'textfield',
								fieldLabel : 'SMS包月',
								width : 80,
								name:'smsStandard',
								labelSeparator : ' ',
								readOnly : true
							}		
							]
						}, {
							border : false,
							columnWidth : .1,
							layout : 'form',
							items : [{
										xtype : "label",
										html : '条，'
									}]
						}, {
							border : false,
							columnWidth : .3,
							layout : 'form',
							items : [{
										xtype : "label",
										html : '超过部分0.03元/条'
									}]
						}]
			});
	
	this.zfbz = new Ext.Window({
				name : 'zfbz',
				title : '资费标准',
				border : false,
				resizable : false,
				layout : 'form',
				width : 450,
				height : 145,
				closeAction : 'hide',
				items : [form_zfbz, form1_zfbz]
			});
	//???Ajax请求响应
	Ext.Ajax.request({
				url : 'runman/queryATmnlFlowMBeanH!querySimFee.action',
				success : function(response) {
					var result = Ext.decode(response.responseText);
					form_zfbz.find('name', 'gprsStandard')[0].setValue(result.simFee.gprs);
					form1_zfbz.find('name', 'smsStandard')[0].setValue(result.simFee.sms);
					zfbz.show()
				}
			});
};
// ------------------------------------资费标准 窗口 结束

Ext.onReady(function() {
	var orgType;	//???
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'runman/queryATmnlFlowMBeanH!queryChannelH.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'aTmnlFlowMBeanHList',
							totalProperty : 'totalCount'
						}, [{
									name : 'orgName'
								}, {
									name : 'consNo'
								}, {
									name : 'consName'
								}, {
									name : 'terminalAddr'
								}, {
									name : 'sumGprs'
								}, {
									name : 'sumSms'
								}, {
									name : 'fee'
								}])
			});
	store.load();
	
	var form_DXcyhs = new Ext.form.FormPanel({
		bodyStyle : 'padding:8px 0px 0px 0px',
		labelAlign : 'right',
		labelWidth : 50,
		border : false,
		layout : 'column',
		items : [{
					border : false,
					columnWidth : .25,
					layout : 'form',
					items : [{
								xtype : 'textfield',
								name : 'orgName',
								width : 120,
								fieldLabel : '节点名 <font color=#ff0000>*</font>',
								labelSeparator : ' ',
								readOnly:true,
								// value:orgNo,
								emptyText : '节点名'
							}]
				}, {
					border : false,
					columnWidth : .25,
					layout : 'form',
					items : [{
								name : 'sDay',
								xtype : 'datefield',
								labelSeparator : ' ',
								fieldLabel : '月份',
								format : "Y-m",
								value : new Date()
							}]
				}, {
					border : false,
					columnWidth : .2,
					border : false,
					items : [{
						xtype : 'button',
						text : '查询',
						listeners : {
							"click" : function() {
								var jdm = form_DXcyhs.find('name', 'orgName')[0].getValue();
								var rq = form_DXcyhs.find('name', 'sDay')[0].getValue();
								var cy = form1_DXcyhs.find('name', 'over')[0].getValue();
								var zzcs = form1_DXcyhs.find('name','manufacture')[0].getValue();

								store.baseParams = {
									'orgName' : jdm,
									'sDay' : rq,
									'over' : cy,
									'manufacture' : zzcs,
									'orgType' : orgType
								}
								store.load();
							}
						},
						width : 80
					}]
				}]
	});
	
	//给节点名赋值
	if(channelHTabOrgNo != null) {
		form_DXcyhs.find('name','orgName')[0].setValue(channelHTabOrgName);
		store.baseParams = {
			'orgName' : channelHTabOrgNo,//根据OrgNo找OrgName??
			'sDay' : new Date(),
			'over' : true,
			'manufacture' : '01',
			'orgType' : '06'
		}
		store.load();
	}
	//制造厂商zzcs
	var zzcsStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'runman/queryATmnlFlowMBeanH!queryTmnlFactory.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'tmnlFactoryList'
						}, [{
									name : 'factoryCode'
								}, {
									name : 'factoryName'
								}])
			});
	zzcsStore.load();
	
	var zzcsComboBox = new Ext.form.ComboBox({
				store : zzcsStore,
				xtype : 'combo',
				name : 'manufacture',
				fieldLabel : '制造厂商',
				width : 120,
				valueField : 'factoryCode',
				editable : false,
				triggerAction : 'all',
				labelSeparator : ' ',
				forceSelection : true,
				selectOnFocus : true,//值为 ture 时表示字段获取焦点时自动选择字段既有文本(默认为 false)
				displayField : 'factoryName',//组合框用以展示的数据的字段名（如果mode='remote'则默认为 undefined，如果mode = 'local' 则默认为 'text'）
				mode : 'remote',//如果ComboBox读取本地数据则将值设为'local'（默认为 'remote' 表示从服务器读取数据）
				emptyText : '--请选择--'
			});
			
	//combo 默认		
	zzcsStore.on('load', function() {
				zzcsComboBox.setValue(zzcsStore.getAt(0).get('factoryCode'));
			});
	
	var form1_DXcyhs = new Ext.form.FormPanel({
		bodyStyle : 'padding:5px 0px 0px 0px',
		labelAlign : 'right',
		labelWidth : 50,
		border : false,
		layout : 'column',
		items : [{
					border : false,
					columnWidth : .27,
					layout : 'form',
					items : [zzcsComboBox]
				}, {
					columnWidth : .08,
					layout : 'form',
					border : false,
					labelWidth : 1,
					items : [{
								xtype : 'checkbox',
								name:'over',
								checked : true,
								boxLabel : '超用'
							}]
				}, {
					border : false,
					columnWidth : .1,
					layout : 'form',
					items : [{
						xtype : 'label',
						html : "<a href='javascript:' onclick='openWindow();'>资费标准</a>"
					}]
				}]
	});

	var cm = new Ext.grid.ColumnModel([{
				header : '供电单位',
				dataIndex : 'orgName',
				align : 'center'
			}, {
				header : '用户编号',
				dataIndex : 'consNo',
				align : 'center'
			}, {
				header : '用户名称',
				dataIndex : 'consName',
				align : 'center'
			}, {
				header : '终端地址',
				dataIndex : 'terminalAddr',
				align : 'center'
			}, {
				header : 'GPRS月流量(KB)',
				dataIndex : 'sumGprs',
				align : 'center'
			}, {
				header : 'SMS月累加(条)',
				dataIndex : 'sumSms',
				align : 'center'
			}, {
				header : '月资费(元)',
				dataIndex : 'fee',
				align : 'center'
			}]);
	
	var grid_DXcyhs = new Ext.grid.GridPanel({
				ds : store,
				height : 300,
				title : '漏点记录表',
				region : 'center',
				cm : cm,
				stripeRows : true,
				autoScroll : true,
				autoWidth : true,
				bbar : new Ext.ux.MyToolbar({
							store : store
						})
			});
	
	var panelup_DXcyhs = new Ext.Panel({
				height : 70,
				region : 'north',
				border : false,
				layout : 'form',
				items : [form_DXcyhs, form1_DXcyhs]
			});

	var viewPanel = new Ext.Panel({
				name: 'panel_DXcyhs',
				autoScroll : true,
				layout : 'border',
				items : [panelup_DXcyhs, grid_DXcyhs]
			});
			
	
	//监听左边树点击事件    ssh xxx
	var treeListener = new LeftTreeListener({
				processClick : function(p, node, e) {
					var obj = node.attributes.attributes;
					var type = node.attributes.type;

					var rq = form_DXcyhs.find('name', 'sDay')[0].getValue();
					var cy = form1_DXcyhs.find('name', 'over')[0].getValue();
					var zzcs = form1_DXcyhs.find('name', 'manufacture')[0].getValue();

					if (type == 'org') {
						form_DXcyhs.find('name', 'orgName')[0].setValue(obj.orgName);
						store.baseParams = {
							'orgName' : obj.orgNo,
							'sDay' : rq,
							'over' : cy,
							'manufacture' : zzcs,
							'orgType' : obj.orgType,
							'nodeType' : type
						}
						store.load();

					} else if (type == 'usr') {
						form_DXcyhs.find('name', 'orgName')[0].setValue(obj.consName);
						store.baseParams = {
							'consNo' : obj.consNo,
							'sDay' : rq,
							'over' : cy,
							'manufacture' : zzcs,
							'nodeType' : type
						}
						store.load();

					} else if (type == 'line') {
						form_DXcyhs.find('name', 'orgName')[0].setValue(obj.lineId);
						store.baseParams = {
							'lineId' : obj.lineId,
							'sDay' : rq,
							'over' : cy,
							'manufacture' : zzcs,
							'nodeType' : type
						}
						store.load();

					} else if (type == 'cgp' || type == 'ugp') {
						form_DXcyhs.find('name', 'orgName')[0].setValue(obj.groupNo);
						store.baseParams = {
							'groupNo' : obj.groupNo,
							'sDay' : rq,
							'over' : cy,
							'manufacture' : zzcs,
							'nodeType' : type
						}
						store.load();

					} else if (type == 'sub') {
						form_DXcyhs.find('name', 'orgName')[0].setValue(obj.subsId);
						store.baseParams = {
							'subsId' : obj.subsId,
							'sDay' : rq,
							'over' : cy,
							'manufacture' : zzcs,
							'nodeType' : type
						}
						store.load();

					} else {
						return true;
					}
				},
				// ????????????????????????????????????
				processUserGridSelect : function(cm, row, record) {
					handText.setValue(record.get('consName'));
					hideTypeText.setValue('usr');
					hideValueText.setValue(record.get('consNo'));
				}
			});
		
	renderModel(viewPanel, '流量超用明细');
});
