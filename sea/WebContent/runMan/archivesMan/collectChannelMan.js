/**
 * 采集通道维护页面
 * 
 * @author 张中伟 zhongweizhang@163.com
 */
// 继承radionGroup 解决 在form 中取值的问题
Ext.namespace("Ext.ux");
Ext.ux.RadioGroup = Ext.extend(Ext.form.RadioGroup, {
			getValue : function() {
				var out = null;
				this.eachItem(function(item) {
							if (item.checked) {
								out = item.inputValue;
								return false;
							}
						});
				return out;
			}
		});
		
Ext.onReady(function() {
	// --左边树监听
	var archieveLeftTreeListener = new LeftTreeListener({
				modelName : '采集通道维护',
				processClick : function(p, node, e) {
					if (!node.isLeaf())
						return;
					var obj = node.attributes.attributes;
					var type = node.attributes.type;
					var id = node.id;
					var infos = id.split('_');

					if (infos[0] == 'usr') {
						consField.setValue(node.text);
						consFieldValue.setValue(node.id);
						tmnlField.setValue(obj.tmnlAssetNo);
					}
					
					queryAll();
				},
				processUserGridSelect : function(sm, row, record) {
					consField.setValue(record.get('consName'));
					consFieldValue.setValue('usr_' + record.get('consId') + '_'
							+ record.get('tmnlAssetNo'));
					tmnlField.setValue(record.get('tmnlAssetNo'));
					
					queryAll();
				}
			});

	// 查询条件显示值
	var consField = new Ext.form.TextField({
				fieldLabel : '用户节点',
				allowBlank : false,
				name : 'consName',
				emptyText : '请从左边树选择用户节点',
				readOnly : true,
				width : 160
			});
	var tmnlField = new Ext.form.TextField({
				fieldLabel : '终端资产号',
				allowBlank : false,
				name : 'tmnlAssetNo',
				readOnly : true,
				width : 160
			});

	// 查询条件值
	var consFieldValue = new Ext.form.Hidden({
				name : 'nodeId'
			}

	);

	// form 查询的参数
	var tmnlQueryFormParam = {
		url : './runman/collectchannelman!queryTerminal.action',
		success : function(form, action) {
			if (!action.result)
				return;
//			dialGroupStore.loadData(action.result);
			dialStore.loadData(action.result);
			dnnStore.loadData(action.result);
			netStore.loadData(action.result);
		}
	};

	function queryAll(){
		if(tmnlField.getValue() && tmnlField.getValue() != ''){
			tmnlQueryForm.getForm().submit(tmnlQueryFormParam);
			dailTmnlAssetNo.setValue(tmnlField.getValue());
			netTmnlAssetNo.setValue(tmnlField.getValue());
			dnnTmnlAssetNo.setValue(tmnlField.getValue());
		}else{
			Ext.Msg.alert('提示','请从左边树选择要查询的用户终端！');
			return true;
		}
	}
	
	// 查询按钮
	var queryButton = new Ext.Button({
				text : '查询',
				handler : queryAll
			});

	// 节点查询
	var tmnlQueryForm = new Ext.FormPanel({
				bodyStyle : 'padding: 10px 10px 10px 10px',
				region : 'north',
				border : false,
				height : 50,
				layout : 'table',
				layoutConfig : {
					columns : 3
				},
				items : [{
							layout : 'form',
							border : false,
							labelAlign : 'right',
							items : [consField]
						},{
							layout : 'form',
							border : false,
							labelAlign : 'right',
							items : [tmnlField]
						},{
							layout : 'form',
							bodyStyle : 'padding: 5px 0px 10px 20px',
							border : false,
							labelAlign : 'right',
							items : [queryButton]
						}, consFieldValue]
			});

	// ----------------拨号组-------------------
	var dialGroupStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './runman/dialgroupman!queryDialGroup.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'listDialGroup'
						}, [{
									name : 'dialGroupNo'
								}, {
									name : 'dialGroupName'
								}, {
									name : 'dialType'
								}, {
									name : 'tsIp'
								}, {
									name : 'tsPort'
								}, {
									name : 'isUsed'
								}, {
									name : 'usedTime'
								}])
			});
	dialGroupStore.load();
	var dialGroupCm = new Ext.grid.ColumnModel([{
				header : '拨号组号',
				dataIndex : 'dialGroupNo'
			}, {
				header : '拨号组名称',
				dataIndex : 'dialGroupName'
			}, {
				header : '拨号方式',
				dataIndex : 'dialType',
				renderer : function(v){
					if(v == '1') return '串口方式';
					else if (v == '0') return '网络方式';
					else return '';
				}
			}, {
				header : '终端服务器IP',
				dataIndex : 'tsIp'
			}, {
				header : '终端服务器端口号',
				dataIndex : 'tsPort'
			}, {
				header : '是否使用',
				dataIndex : 'isUsed',
				renderer : function(v){
					if(v == '1') return '是';
					else if (v == '0') return '否';
					else return '';
				}
			}, {
				header : '使用时间',
				dataIndex : 'usedTime'
			}]);

//	var dialGroupRecord = Ext.data.Record.create([{
//				name : 'dialGroupNo',
//				mapping : 'dialGroupNo'
//			}, {
//				name : 'dialGroupName',
//				mapping : 'dialGroupName'
//			}, {
//				name : 'dialType',
//				mapping : 'dialType'
//			}, {
//				name : 'tsIp',
//				mapping : 'tsIp'
//			}, {
//				name : 'tsPort',
//				mapping : 'tsPort'
//			}, {
//				name : 'isUsed',
//				mapping : 'isUsed'
//			}, {
//				name : 'usedTime',
//				mapping : 'usedTime'
//			}]);

	var dialGroupGrid = new Ext.grid.GridPanel({
				sm : new Ext.grid.RowSelectionModel(),
				region : 'center',
				autoScroll : true,
				cm : dialGroupCm,
				ds : dialGroupStore
			});
			
	var dialGroupRowSelect = function(sm, num, rec) {
		if (!Ext.isEmpty(rec)){
			dialGroupForm.getForm().setValues(rec.data);
		}
	}
	dialGroupGrid.getSelectionModel().on('rowselect', dialGroupRowSelect);
	
	var dialGroupNoTf = new Ext.form.TextField({
									name : 'dialGroupNo',
									hidden : true 
								});
	var dialTypeData = [['0', '网络方式'], ['1', '串口方式']];
	var dialTypeStore = new Ext.data.SimpleStore({
				fields : ['dialTypeValue', 'dialTypeText'],
				data : dialTypeData
			});
	var dialGroupTsIp = new Ext.form.TextField({
									name : 'tsIp',
									emptyText : '请输入终端服务器IP ',
									validator: function(str){   
											var time = str.match(/^(((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))\.){3}((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))$/);   
											if(time != null)  
												return true;   
											else return false;
										},
									invalidText : '请输入有效的IP地址',
									fieldLabel : '终端服务器IP'
								});
	var dialTypeCombox = new Ext.form.ComboBox({
				store : dialTypeStore,
				displayField : 'dialTypeText',
				valueField : 'dialTypeValue',
				editable : false,
				name : 'dialType',
				hiddenName : 'dialType',
				mode : 'local',
				fieldLabel : '拨号方式',
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--',
				selectOnFocus : true,
				anchor : '100%'
			});
	dialTypeCombox.on('change',function(c,nv,ov){
		if(nv == '1'){
			dialGroupTsIp.setValue('0.0.0.0');
			dialGroupTsIp.setReadOnly(true);
		}else if(nv == '0'){
			dialGroupTsIp.setReadOnly(false);
			dialGroupTsIp.setValue('');
		}
	});
	var dialGroupForm1 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.5,
				border : false, 
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [dialTypeCombox, new Ext.form.NumberField({
									name : 'tsPort',
									emptyText : '请输入终端服务器端口 ',
									fieldLabel : '终端服务器端口'
								}), new Ext.ux.RadioGroup({
									fieldLabel : '是否使用',
									name : 'isUsed',
									items : [{
												boxLabel : '是',
												name : 'isUsed',
												inputValue : 1,
												checked : true
											}, {
												boxLabel : '否',
												name : 'isUsed',
												inputValue : 0
											}]
								})]
			});

	var dialGroupForm2 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.5,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [new Ext.form.TextField({
									name : 'dialGroupName',
									emptyText : '请输入拨号组名称 ',
									fieldLabel : '拨号组名称'
								}), dialGroupTsIp, new Ext.form.DateField({
									name : 'usedTime',
									format : 'Y-m-d',
									editable : false,
									emptyText : '请输入使用时间 ',
									fieldLabel : '使用时间'
								})]
			});

	var dialGroupForm = new Ext.form.FormPanel({
				region : 'south',
				height : 160,
				border : false,
				bodyStyle : 'padding: 10px 0px 0px 10px',
				title : '拨号组详细信息',
				layout : 'column',
				autoScroll : true,
				buttonAlign : 'center',
				items : [dialGroupNoTf,dialGroupForm1, dialGroupForm2],
				buttons : [{
							text : '增加',
							handler : function() {
								dialGroupForm.getForm().clear();
							}
						}, {
							text : '保存',
							handler : saveDialGroup
						}, {
							text : '删除',
							handler : function() {
								if(dialGroupNoTf.getValue() == ''){
									Ext.Msg.alert('提示','请选择要删除的拨号组');
									return true;
								}
								Ext.MessageBox.confirm('删除确认',
											'确定要删除该通道组吗？', deleteDialGroup);
							}
						}

				]
			});

	var dialGroupPanel = new Ext.Panel({
				title : '拨号组维护',
				layout : 'border',
				items : [dialGroupGrid, dialGroupForm]

			});
	function saveDialGroup(){
		if(!dialGroupForm.getForm().isValid()){
			Ext.Msg.alert('提示','请完整输入必填选项');
			return true;
		}
		Ext.Ajax.request({
			url : './runman/dialgroupman!saveDialGroup.action',
			params : dialGroupForm.getForm().getFieldValues(),
			success : function(response) {
				var obj = Ext.decode(response.responseText);
				if(obj && obj.success){
					Ext.MessageBox.alert('提示', obj.msg);
					if("保存成功！" == obj.msg){
						dialGroupStore.load();
					}
				}else if(obj && obj.error!=''){
					Ext.MessageBox.alert('提示', '保存失败！');					
				}
			}
		});
	}
	function deleteDialGroup(btn) {
		if(!btn || btn == 'no'){
			return true; 
		}
		Ext.Ajax.request({
			url : './runman/dialgroupman!deleteDialGroup.action',
			params : dialGroupForm.getForm().getFieldValues(),
			success : function(response) {
				var obj = Ext.decode(response.responseText);
				if(obj && obj.success){
					Ext.MessageBox.alert('提示', '已成功删除！');
					dialGroupStore.load();
				}else{
					Ext.MessageBox.alert('提示', '删除失败！');					
				}
			}
		});
	}
	// -----------------------------------------

	// ---------------拨号通道------------------
	var dailTmnlAssetNo = new Ext.form.TextField({
								name : 'tmnlAssetNo',
								emptyText : '请输入终端资产号 ',
								fieldLabel : '终端资产号',
								allowBlank : false,
								readOnly : true
							});
	var dialStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './runman/dialman!queryDial.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'listDial'
						}, [{
									name : 'dialChannelId'
								}, {
									name : 'tmnlAssetNo'
								}, {
									name : 'baudRate'
								}, {
									name : 'parityBit'
								}, {
									name : 'stopBit'
								}, {
									name : 'carrierCtrl'
								}, {
									name : 'txfifo'
								}, {
									name : 'phoneCode'
								}, {
									name : 'tryTimes'
								}, {
									name : 'dialGroupNo'
								}, {
									name : 'tsPort'
								}, {
									name : 'serialPort'
								}, {
									name : 'tsIp'
								}, {
									name : 'protocolCode'
								}, {
									name : 'pri'
								}])
			});
	function loadDialStore(){
		dialStore.load({
					params : {
						tmnlAssetNo : dailTmnlAssetNo.getValue()
					}
				});
	}
	var dialCm = new Ext.grid.ColumnModel([{
				header : '拨号通道ID',
				dataIndex : 'dialChannelId',
				hidden : true
			}, {
				header : '终端资产编号',
				dataIndex : 'tmnlAssetNo'
			}, {
				header : '波特率',
				dataIndex : 'baudRate'
			}, {
				header : '校验位',
				dataIndex : 'parityBit'
			}, {
				header : '停止位',
				dataIndex : 'stopBit'
			}, {
				header : '载波控制',
				dataIndex : 'carrierCtrl'
			}, {
				header : '流控',
				dataIndex : 'txfifo'
			}, {
				header : '电话号码',
				dataIndex : 'phoneCode'
			}, {
				header : '重试次数',
				dataIndex : 'tryTimes'
			}, {
				header : '拨号组号',
				dataIndex : 'dialGroupNo'
			}, {
				header : '终端服务器端口',
				dataIndex : 'tsPort'
			}, {
				header : '串口端口号',
				dataIndex : 'serialPort'
			}, {
				header : '终端服务器IP',
				dataIndex : 'tsIp'
			}, {
				header : '通信规约类型',
				dataIndex : 'protocolCode'
			}, {
				header : '优先级',
				dataIndex : 'pri'
			}]);

	var dialGrid = new Ext.grid.GridPanel({
				height : 160,
				sm : new Ext.grid.RowSelectionModel(),
				region : 'north',
				autoScroll : true,
				cm : dialCm,
				ds : dialStore
			});
	var dialRowSelect = function(sm, num, rec) {
		if (!Ext.isEmpty(rec)){
			dialForm.getForm().setValues(rec.data);
		}
	}
	dialGrid.getSelectionModel().on('rowselect', dialRowSelect);
	
	var hiddenDialId = 	new Ext.form.TextField({
									name : 'dialChannelId',
									hidden : true
								});
	/* 运行状态 */
	var dialGroupCombox = new Ext.form.ComboBox({
		store : dialGroupStore,
		name : 'dialGroupNo',
		displayField : 'dialGroupName',
		hiddenName : 'dialGroupNo',
		valueField : 'dialGroupNo',
		typeAhead : true,
		editable:false,
		mode : 'remote',
		fieldLabel : '拨号组',
		labelSeparator : '',
		forceSelection : true,
		triggerAction : 'all',
		emptyText : '--请选择拨号组--',
		blankText : '--请选择拨号组--',
		selectOnFocus : true,
		anchor:'100%',
		allowBlank:false
	});
	var dialForm1 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.5,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [dailTmnlAssetNo, dialGroupCombox, new Ext.form.TextField({
									name : 'txfifo',
									emptyText : '请输入流控 ',
									fieldLabel : '流控'
								}), new Ext.form.TextField({
									name : 'tryTimes',
									emptyText : '请输入重试次数',
									fieldLabel : '重试次数'
								}), new Ext.form.TextField({
									name : 'tsPort',
									emptyText : '请输入终端端口号 ',
									fieldLabel : '终端端口号'
								}), new Ext.form.TextField({
									name : 'tsIp',
									emptyText : '请输入终端IP',
									fieldLabel : '终端IP',
									validator: function(str){   
											var time = str.match(/^(((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))\.){3}((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))$/);   
											if(time != null)  
												return true;   
											else return false;
										},
									invalidText : '请输入有效的IP地址'
								}), new Ext.form.TextField({
									name : 'pri',
									emptyText : '请输入优先级 ',
									fieldLabel : '优先级'
								})]
			});
	var dialForm2 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.5,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [new Ext.form.TextField({
									name : 'baudRate',
									emptyText : '请输入波特率 ',
									fieldLabel : '波特率'
								}), new Ext.form.TextField({
									name : 'parityBit',
									emptyText : '请输入校验位 ',
									fieldLabel : '校验位'
								}), new Ext.form.TextField({
									name : 'stopBit',
									emptyText : '请输入停止位 ',
									fieldLabel : '停止位'
								}), new Ext.form.TextField({
									name : 'carrierCtrl',
									emptyText : '请输入载波控制 ',
									fieldLabel : '载波控制'
								}), new Ext.form.TextField({
									name : 'phoneCode',
									emptyText : '请输入电话号码 ',
									fieldLabel : '电话号码'
								}),  new Ext.form.TextField({
									name : 'serialPort',
									emptyText : '请输入串口端口号 ',
									fieldLabel : '串口端口号'
								}), new Ext.form.TextField({
									name : 'protocolCode',
									emptyText : '请输入规约类型 ',
									fieldLabel : '规约类型'
								})]
			});

	var dialForm = new Ext.form.FormPanel({
				region : 'center',
				border : false,
				bodyStyle : 'padding: 10px 0px 0px 10px',
				layout : 'column',
				buttonAlign : 'center',
				title : '拨号通道详细信息',
				items : [hiddenDialId,dialForm1, dialForm2],
				autoScroll : true,
				buttons : [{
							text : '增加',
							handler : function() {
								dialForm.getForm().clear();
								dailTmnlAssetNo.setValue(tmnlField.getValue());
							}
						}, {
							text : '保存',
							handler : saveDial
						}, {
							text : '删除',
							handler : function() {
								if(hiddenDialId.getValue() == ''){
									Ext.Msg.alert('提示','请选择要删除的拨号通道');
									return true;
								}
								Ext.MessageBox.confirm('删除确认',
											'确定要删除该拨号通道吗？', deleteDial);
							}
						}

				]
			});
	function saveDial(){
		if(!dialForm.getForm().isValid()){
			Ext.Msg.alert('提示','请完整输入必填选项');
			return true;
		}
		Ext.Ajax.request({
			url : './runman/dialman!saveDial.action',
			params : dialForm.getForm().getFieldValues(),
			success : function(response) {
				var obj = Ext.decode(response.responseText);
				if(obj && obj.success){
					Ext.MessageBox.alert('提示', obj.msg);
					if("保存成功！" == obj.msg){
						loadDialStore();
					}
				}else if(obj && obj.error!=''){
					Ext.MessageBox.alert('提示', '保存失败！');					
				}
			}
		});
	}
	
	function deleteDial(btn){
		if(!btn || btn == 'no'){
			return true; 
		}
		
		Ext.Ajax.request({
			url : './runman/dialman!deleteDial.action',
			params : dialForm.getForm().getFieldValues(),
			success : function(response) {
				var obj = Ext.decode(response.responseText);
				if(obj && obj.success){
					Ext.MessageBox.alert('提示', '已成功删除！');
					loadDialStore();
				}else{
					Ext.MessageBox.alert('提示', '删除失败！');					
				}
			}
		});
	}
	var dialPanel = new Ext.Panel({
				title : '拨号通道维护',
				layout : 'border',
				items : [dialGrid, dialForm]

			});

	// -----------------------------------------

	// ---------------网络通道------------------
	var netTmnlAssetNo = new Ext.form.TextField({
								name : 'tmnlAssetNo',
								emptyText : '请输入终端资产号 ',
								fieldLabel : '终端资产号',
								allowBlank : false,
								readOnly : true
							});
	var netStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './runman/netman!queryNet.action'
						}),
				// proxy : new Ext.data.MemoryProxy(data),
				reader : new Ext.data.JsonReader({
							root : 'listNet'
						}, [{
									name : 'netChannelId'
								}, {
									name : 'tmnlAssetNo'
								}, {
									name : 'ip'
								}, {
									name : 'port'
								}, {
									name : 'protocolCode'
								}, {
									name : 'pri'
								}])
			});
	function loadNetStore(){
		netStore.load({
					params : {
						tmnlAssetNo : netTmnlAssetNo.getValue()
					}
				});
	}
	var netCm = new Ext.grid.ColumnModel([{
				header : '网络通道ID',
				dataIndex : 'netChannelId',
				hidden : true
			}, {
				header : '终端资产编号',
				dataIndex : 'tmnlAssetNo'
			}, {
				header : 'IP地址',
				dataIndex : 'ip'
			}, {
				header : '端口号',
				dataIndex : 'port'
			}, {
				header : '规约类型',
				dataIndex : 'protocolCode'
			}, {
				header : '优先级',
				dataIndex : 'pri'
			}]);

	var netGrid = new Ext.grid.GridPanel({
				sm : new Ext.grid.RowSelectionModel(),
				region : 'center',
				autoScroll : true,
				cm : netCm,
				ds : netStore
			});

	var netRowSelect = function(sm, num, rec) {
		if (!Ext.isEmpty(rec)){
			netForm.getForm().setValues(rec.data);
		}
	}
	netGrid.getSelectionModel().on('rowselect', netRowSelect);
	
	var hiddenNetId = new Ext.form.TextField({
									name : 'netChannelId',
									hidden : true
								});
	var netForm1 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.5,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [netTmnlAssetNo, new Ext.form.TextField({
									name : 'ip',
									emptyText : '请输入IP地址 ',
									fieldLabel : 'IP地址',
									validator: function(str){   
											var time = str.match(/^(((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))\.){3}((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))$/);   
											if(time != null)  
												return true;   
											else return false;
										},
									invalidText : '请输入有效的IP地址'
								}), new Ext.form.TextField({
									name : 'protocolCode',
									emptyText : '请输入规约类型',
									fieldLabel : '规约类型'
								})]
			});
	var netForm2 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.5,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [new Ext.form.TextField({
									name : 'port',
									emptyText : '请输入端口号 ',
									fieldLabel : '端口号'
								}), new Ext.form.TextField({
									name : 'pri',
									emptyText : '请输入优先级 ',
									fieldLabel : '优先级'
								})]
			});
	var netForm = new Ext.form.FormPanel({
				region : 'south',
				border : false,
				height : 160,
				bodyStyle : 'padding: 10px 0px 0px 10px',
				layout : 'column',
				autoScroll : true,
				title : '网络通道详细信息',
				items : [hiddenNetId, netForm1, netForm2],
				buttonAlign : 'center',
				buttons : [{
							text : '增加',
							handler : function() {
								netForm.getForm().clear();
								netTmnlAssetNo.setValue(tmnlField.getValue());
							}
						}, {
							text : '保存',
							handler : saveNet
						}, {
							text : '删除',
							handler : function() {
								if(hiddenNetId.getValue() == ''){
									Ext.Msg.alert('提示','请选择要删除的网络通道');
									return true;
								}
								Ext.MessageBox.confirm('删除确认',
											'确定要删除该网络通道吗？', deleteNet);
							}
						}

				]
			});

	function saveNet(){
		if(!netForm.getForm().isValid()){
			Ext.Msg.alert('提示','请完整输入必填选项');
			return true;
		}
		Ext.Ajax.request({
			url : './runman/netman!saveNet.action',
			params : netForm.getForm().getFieldValues(),
			success : function(response) {
				var obj = Ext.decode(response.responseText);
				if(obj && obj.success){
					Ext.MessageBox.alert('提示', obj.msg);
					if("保存成功！" == obj.msg){
						loadNetStore();
					}
				}else if(obj && obj.error!=''){
					Ext.MessageBox.alert('提示', '保存失败！');					
				}
			}
		});
	}
	
	function deleteNet(btn){
		if(!btn || btn == 'no'){
			return true; 
		}
		
		Ext.Ajax.request({
			url : './runman/netman!deleteNet.action',
			params : netForm.getForm().getFieldValues(),
			success : function(response) {
				var obj = Ext.decode(response.responseText);
				if(obj && obj.success){
					Ext.MessageBox.alert('提示', '已成功删除！');
					loadNetStore();
				}else{
					Ext.MessageBox.alert('提示', '删除失败！');					
				}
			}
		});
	}
			
	var netPanel = new Ext.Panel({
				title : '网络通道维护',
				layout : 'border',
				items : [netGrid, netForm]

			});

	// -----------------------------------------

	// ---------------专线通道------------------
	var dnnTmnlAssetNo = new Ext.form.TextField({
								name : 'tmnlAssetNo',
								emptyText : '请输入终端资产号 ',
								fieldLabel : '终端资产号',
								allowBlank : false,
								readOnly : true
							});
	var dnnStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './runman/dnnman!queryDnn.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'listDnn'
						}, [{
									name : 'dnnChannelId'
								}, {
									name : 'tmnlAssetNo'
								}, {
									name : 'command'
								}, {
									name : 'port'
								}, {
									name : 'protocolCode'
								}, {
									name : 'pri'
								}])
			});
	function loadDnnStore(){
		dnnStore.load({
					params : {
						tmnlAssetNo : dnnTmnlAssetNo.getValue()
					}
				});
	}
	var dnnCm = new Ext.grid.ColumnModel([{
				header : '专线通道ID',
				dataIndex : 'dnnChannelId',
				hidden : true
			}, {
				header : '终端资产号',
				dataIndex : 'tmnlAssetNo'
			}, {
				header : 'AT命令',
				dataIndex : 'command'
			}, {
				header : '端口号',
				dataIndex : 'port'
			}, {
				header : '规约类型',
				dataIndex : 'protocolCode'
			}, {
				header : '优先级',
				dataIndex : 'pri'
			}]);

	var dnnGrid = new Ext.grid.GridPanel({
				sm : new Ext.grid.RowSelectionModel(),
				region : 'center',
				autoScroll : true,
				cm : dnnCm,
				ds : dnnStore
			});
	var dnnRowSelect = function(sm, num, rec) {
		if (!Ext.isEmpty(rec)){
			dnnForm.getForm().setValues(rec.data);
		}
	}
	dnnGrid.getSelectionModel().on('rowselect', dnnRowSelect);
	
	var hiddenDnnId = new Ext.form.TextField({
									name : 'dnnChannelId',
									hidden : true
								});
	var dnnForm1 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.5,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [dnnTmnlAssetNo, new Ext.form.TextField({
									name : 'command',
									emptyText : '请输入AT命令 ',
									fieldLabel : 'AT命令'
								}), new Ext.form.TextField({
									name : 'pri',
									emptyText : '请输入优先级',
									fieldLabel : '优先级'
								})]
			});

	var dnnForm2 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.5,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [new Ext.form.TextField({
									name : 'port',
									emptyText : '请输入端口号 ',
									fieldLabel : '端口号'
								}), new Ext.form.TextField({
									name : 'protocolCode',
									emptyText : '请输入规约类型 ',
									fieldLabel : '规约类型'
								})]
			});
	var dnnForm = new Ext.form.FormPanel({
				region : 'south',
				height : 160,
				border : false,
				bodyStyle : 'padding: 10px 0px 0px 10px',
				layout : 'column',
				autoScroll : true,
				title : '专线通道详细信息',
				items : [hiddenDnnId, dnnForm1, dnnForm2],
				buttonAlign : 'center',
				buttons : [{
							text : '增加',
							handler : function() {
								dnnForm.getForm().clear();
								dnnTmnlAssetNo.setValue(tmnlField.getValue());
							}
						}, {
							text : '保存',
							handler : saveDnn
						}, {
							text : '删除',
							handler : function() {
								if(hiddenDnnId.getValue() == ''){
									Ext.Msg.alert('提示','请选择要删除的拨号通道');
									return true;
								}
								Ext.MessageBox.confirm('删除确认',
											'确定要删除该拨号通道吗？', deleteDnn);
							}
						}
				]
			});
	function saveDnn(){
		if(!dnnForm.getForm().isValid()){
			Ext.Msg.alert('提示','请完整输入必填选项');
			return true;
		}
		Ext.Ajax.request({
			url : './runman/dnnman!saveDnn.action',
			params : dnnForm.getForm().getFieldValues(),
			success : function(response) {
				var obj = Ext.decode(response.responseText);
				if(obj && obj.success){
					Ext.MessageBox.alert('提示', obj.msg);
					if("保存成功！" == obj.msg){
						loadDnnStore();
					}
				}else if(obj && obj.error!=''){
					Ext.MessageBox.alert('提示', '保存失败！');					
				}
			}
		});
	}
	
	function deleteDnn(btn){
		if(!btn || btn == 'no'){
			return true; 
		}

		Ext.Ajax.request({
			url : './runman/dnnman!deleteDnn.action',
			params : dnnForm.getForm().getFieldValues(),
			success : function(response) {
				var obj = Ext.decode(response.responseText);
				if(obj && obj.success){
					Ext.MessageBox.alert('提示', '已成功删除！');
					loadDnnStore();
				}else{
					Ext.MessageBox.alert('提示', '删除失败！');					
				}
			}
		});
	}
	var dnnPanel = new Ext.Panel({
				title : '专线通道维护',
				layout : 'border',
				items : [dnnGrid, dnnForm]

			});
	// -----------------------------------------

	// 采集通道录入
	var channelPanel = new Ext.TabPanel({
				region : 'center',
				activeTab : 0,
				items : [dialGroupPanel, dialPanel, netPanel, dnnPanel]
			});

	// 总布局页面
	var panel = new Ext.Panel({
				border : false,
				layout : 'border',
				items : [tmnlQueryForm, channelPanel]
			});

	// 渲染到页面
	renderModel(panel, '采集通道维护');
});