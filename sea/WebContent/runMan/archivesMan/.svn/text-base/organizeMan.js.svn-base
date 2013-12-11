/**
 * 组织机构维护页面
 * 
 * @author yut
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
	var actionurl = './sysman/dictionary!publicDictionary.action';
	var sql = "sql=SELECT VOLT_CODE, VOLT FROM VW_VOLT_CODE";
	var voltUrl = Ext.urlAppend(actionurl, sql);
	var voltStore = new Ext.data.JsonStore({
			url : voltUrl,
			fields : ['VOLT_CODE', 'VOLT'],
			root : "dicList"
		});
	voltStore.load();
	var areaCode = '';
	// --左边树监听
	var om_LeftTreeListener = new LeftTreeListener({
				modelName : '组织机构维护',
				processClick : function(p, node, e) {
							if (node.isLeaf())
								return;
							var obj = node.attributes.attributes;
							var type = node.attributes.type;

							if (type == 'org' && (obj.orgType == '03'||obj.orgType == '04')) {
								om_nodeField.setValue(node.text);
								om_nodeFieldValue.setValue(obj.orgNo);
								om_nodeTypeValue.setValue(type);
								areaCode = obj.areaCode;
								 
								lineRadio.setDisabled(true);
								subsRadio.setDisabled(false);
								if(obj.orgType == '03'){
									orgRadio.setDisabled(true);
									om_typeRadio.setValue('2');
									loadSubs();
								}else{
									orgRadio.setDisabled(false);
									om_typeRadio.setValue('1');
									loadOrg();
								}
							}else if(type == 'sub'){
								om_nodeField.setValue(node.text);
								om_nodeFieldValue.setValue(obj.subsId);
								om_nodeTypeValue.setValue(type);
								om_lineOrgValue.setValue(obj.orgNo);
								areaCode = '';
								
								orgRadio.setDisabled(true);
								subsRadio.setDisabled(true);
								lineRadio.setDisabled(false);
								om_typeRadio.setValue('3');
								loadLine();
							}else{
								om_nodeField.setValue('');
								om_nodeFieldValue.setValue('');
								om_nodeTypeValue.setValue('');
								om_lineOrgValue.setValue('');
								areaCode = '';
								lineRadio.setDisabled(true);
								orgRadio.setDisabled(true);
								subsRadio.setDisabled(true);
							}
						}
			});

	// 节点显示值
	var om_nodeField = new Ext.form.TextField({
				fieldLabel : '节点名',
				allowBlank : false,
				name : 'nodeName',
				emptyText : '请从左边树选择节点',
				readOnly : true,
				width : 160
			});

	// 节点对应的编号值（orgNo，subsNo）
	var om_nodeFieldValue = new Ext.form.Hidden({
				name : 'nodeValue'
			});
	var om_nodeTypeValue = new Ext.form.Hidden({
				name : 'nodeType'
			});
	var om_lineOrgValue = new Ext.form.Hidden({
				name : 'lineOrg'
			});
	// 查询按钮
	var om_queryButton = new Ext.Button({
				text : '查询',
				handler : function() {
					if(om_typeRadio.getValue() == '1')
						loadOrg();
					else if(om_typeRadio.getValue() == '2')
						loadSubs();
					else if(om_typeRadio.getValue() == '3')
						loadLine();
				}
			});		
	/* 创建类型 */
	var orgRadio = new Ext.form.Radio({   
       	name : "om_type",
       	inputValue : "1",
      	boxLabel : "供电所",
      	checked:true
	});
    var subsRadio = new Ext.form.Radio({   
       	name : "om_type",
       	inputValue : "2",
       	disabled : true,
      	boxLabel : "变电站"  
	});	
	var lineRadio = new Ext.form.Radio({   
       	name : "om_type",
       	inputValue : "3",
       	disabled : true,
      	boxLabel : "线路"  
	});	
	var om_typeRadio = new Ext.ux.RadioGroup({
		anchor : '90%',
		width : 300,
		fieldLabel:'',
		labelWidth:1,
		labelSeparator : '',
		border : false,
		items : [orgRadio,subsRadio,lineRadio]
	});
	om_typeRadio.on('change',function(rg,r){
		if(rg.getValue() == '1'){
			if(om_nodeTypeValue == null || om_nodeTypeValue.getRawValue() == ''){
				Ext.Msg.alert('提示','请从左边树选择需要添加供电所的区县');
				return true;
			}else {
				om_CardPanel.layout.setActiveItem(0);
				loadOrg();
			}
		}else if(rg.getValue() == '2'){
			if(om_nodeTypeValue == null || om_nodeTypeValue.getRawValue() == ''){
				Ext.Msg.alert('提示','请从左边树选择需要添加变电站的单位（地市、区县）');
				return true;
			}else {
				om_CardPanel.layout.setActiveItem(1);
				loadSubs();
			}
		}else if(rg.getValue() == '3'){
			if(om_nodeTypeValue == null || om_nodeTypeValue.getRawValue() == ''){
				Ext.Msg.alert('提示','请从左边树选择需要添加线路的变电站');
				return true;
			}else {
				om_CardPanel.layout.setActiveItem(2);
				loadLine();
			}
		}
		
	});
	
	// 节点查询
	var om_topForm = new Ext.FormPanel({
				bodyStyle : 'padding: 5px 10px 10px 10px',
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
							items : [om_nodeField]
						}, {
							layout : 'form',
							bodyStyle : 'padding: 12px 0px 10px 5px',
							border : false,
							width : 350,
							labelAlign : 'right',
							items : [om_typeRadio]
						}, {
							layout : 'form',
							bodyStyle : 'padding: 5px 0px 10px 20px',
							border : false,
							labelAlign : 'right',
							items : [om_queryButton]
						}]
			});
	
	//供电所--------------------------------------------------------------------------------------------------开始
	var orgStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
							url : './runman/organizeManOrg!queryOrg.action'
						}),
		reader : new Ext.data.JsonReader({
							root : 'orgList'
						}, [{
									name : 'orgNo'
								}, {
									name : 'orgName'
								}, {
									name : 'orgShortName'
								}, {
									name : 'pOrgNo'
								}, {
									name : 'orgType'
								}, {
									name : 'sortNo'
								}, {
									name : 'isBulkSale'
								}, {
									name : 'tmnlCode'
								}, {
									name : 'areaCode'
								}, {
									name : 'isDirect'
								}, {
									name : 'interOrgNo'
								}, {
									name : 'calcOrder'
								}, {
									name : 'isStat'
								}])
	});
	orgStore.on('load',function(){
		orgForm.getForm().clear();
		orgGrid.getSelectionModel().selectFirstRow();
	});
	var orgColumnModel = new Ext.grid.ColumnModel({
		columns : [{
					header : '单位编号',
					align : 'center',
					dataIndex : 'orgNo'
				}, {
					header : '单位名称',
					align : 'center',
					dataIndex : 'orgName'
				}, {
					header : '单位简称',
					align : 'center',
					dataIndex : 'orgShortName'
				}, {
					header : '上级单位编号',
					align : 'center',
					dataIndex : 'pOrgNo'
				}, {
					header : '供电单位类别 ',
					align : 'center',
					dataIndex : 'orgType',
					renderer : function(val) {
						return val;
					}
				}, {
					header : '排序序号 ',
					align : 'center',
					dataIndex : 'sortNo'
				}, {
					header : '是否趸售局',
					align : 'center',
					dataIndex : 'isBulkSale',
					renderer : function(val){
						if(val == '0') return '否';
						else if(val == '1') return '是';
						else return '';
					}
				}, {
					header : '终端区域码',
					align : 'center',
					dataIndex : 'tmnlCode'
				}, {
					header : '存放区域',
					align : 'center',
					dataIndex : 'areaCode'
				}, {
					header : '是否存在直供用户',
					align : 'center',
					dataIndex : 'isDirect',
					renderer : function(val){
						if(val == '0') return '否';
						else if(val == '1') return '是';
						else return '';
					}
				}, {
					header : '接口对应单位编号',
					align : 'center',
					hidden : true,
					dataIndex : 'interOrgNo'
				}, {
					header : '计算次序',
					align : 'center',
					dataIndex : 'calcOrder'
				}, {
					header : '是否属于统计单位',
					align : 'center',
					dataIndex : 'isStat',
					renderer : function(val){
						if(val == '0') return '否';
						else if(val == '1') return '是';
						else return '';
					}
				}],
		defaults : {
			sortable : false,
			menuDisabled : true,
			remoteSort : false
		}
	});
	//供电所record
	var orgRecord = Ext.data.Record.create([{
				name : 'orgNo',
				mapping : 'orgNo'
			}, {
				name : 'orgName',
				mapping : 'orgName'
			}, {
				name : 'orgShortName',
				mapping : 'orgShortName'
			}, {
				name : 'pOrgNo',
				mapping : 'pOrgNo'
			}, {
				name : 'orgType',
				mapping : 'orgType'
			}, {
				name : 'sortNo',
				mapping : 'sortNo'
			}, {
				name : 'isBulkSale',
				mapping : 'isBulkSale'
			}, {
				name : 'tmnlCode',
				mapping : 'tmnlCode'
			}, {
				name : 'areaCode',
				mapping : 'areaCode'
			}, {
				name : 'isDirect',
				mapping : 'isDirect'
			}, {
				name : 'interOrgNo',
				mapping : 'interOrgNo'
			}, {
				name : 'calcOrder',
				mapping : 'calcOrder'
			}, {
				name : 'isStat',
				mapping : 'isStat'
			}]);
			
	var orgGrid = new Ext.grid.GridPanel({
		region : 'center',
		viewConfig : {
			forceFit : false
		},
		autoScroll : true,
		sm : new Ext.grid.RowSelectionModel(),
		cm : orgColumnModel,
		ds : orgStore,
		tbar : ['供电所维护', '->', {
									text : '增加供电所',
									handler : function() {
										orgStore.add(new orgRecord({
													'orgNo' : '',
													'orgName' : '',
													'orgShortName' : '',
													'pOrgNo' : om_nodeFieldValue.getRawValue(),
													'orgType' : '06',
													'sortNo' : '',
													'isBulkSale' : '',
													'tmnlCode' : '',
													'areaCode' : areaCode,
													'isDirect' : '',
													'interOrgNo' : '',
													'calcOrder' : '',
													'isStat' : ''
												}));
										orgGrid.getSelectionModel()
												.selectLastRow();
									}
								}, {
									text : '重新载入',
									handler : function() {
										loadOrg();
									}
								}]
	});
	var loadOrg = function() {
		orgStore.load({
					params : {
						nodeValue : om_nodeFieldValue.getRawValue()
					}
				});
	}
	var orgRowSelect = function(sm, num, rec) {
		if (!Ext.isEmpty(rec)){
			orgForm.getForm().setValues(rec.data);
			hiddenOrg.setValue(rec.get('orgNo'));
		}
	}
	orgGrid.getSelectionModel().on('rowselect', orgRowSelect);
	
	// 在切换form时将form内容保存到 tmnlParamStore
	// 保存前还要将当前form的内容保存到tmnlParamStore 取得选中行并取值设置
	var orgRowDeselect = function(sm, row, rec) {
		var fmval = orgForm.getForm().getFieldValues();
		for (var o in fmval) {
			rec.set(o, fmval[o]);
		}
		rec.commit();
	}
	orgGrid.getSelectionModel().on('rowdeselect', orgRowDeselect);
	
	var hiddenOrg = new Ext.form.NumberField({
									name : 'hiddenOrgNo',
									hidden : true,
									value : ''
								});
	var orgText = new Ext.form.NumberField({
									name : 'orgNo',
									emptyText : '请输入单位编号',
									fieldLabel : '单位编号',
									allowBlank : false
								});
	var orgForm1 = new Ext.Panel({
		layout : 'form',
		columnWidth : 0.33,
		border : false,
		defaults : {
			anchor : '95%'
		},
		labelAlign : 'right',
		items : [orgText,new Ext.form.TextField({
									name : 'orgName',
									emptyText : '请输入单位名称',
									fieldLabel : '单位名称',
									allowBlank : false
								}),new Ext.form.TextField({
									name : 'orgType',
									emptyText : '请输入单位类别',
									fieldLabel : '单位类别',
									readOnly : true,
									allowBlank : false
								}),new Ext.form.TextField({
									name : 'orgShortName',
									emptyText : '请输入单位简称',
									fieldLabel : '单位简称',
									allowBlank : false
								})]
	});
	var orgForm2 = new Ext.Panel({
		layout : 'form',
		columnWidth : 0.33,
		border : false,
		defaults : {
			anchor : '95%'
		},
		labelAlign : 'right',
		items : [new Ext.form.TextField({
									name : 'pOrgNo',
									emptyText : '请输入上级单位编号',
									fieldLabel : '上级单位编号',
									allowBlank : false,
									readOnly : true
								}),new Ext.form.TextField({
									name : 'tmnlCode',
									emptyText : '请输入终端区域码',
									fieldLabel : '终端区域码'
								}),new Ext.ux.RadioGroup({
									fieldLabel : '是否存在直供用户 ',
									name : 'isDirect',
									items : [{
												boxLabel : '是',
												name : 'isDirect',
												inputValue : 1
											}, {
												boxLabel : '否',
												name : 'isDirect',
												inputValue : 0,
												checked : true
											}]
								}), new Ext.ux.RadioGroup({
									fieldLabel : '是否趸售局',
									name : 'isBulkSale',
									items : [{
												boxLabel : '是',
												name : 'isBulkSale',
												inputValue : 1
											}, {
												boxLabel : '否',
												name : 'isBulkSale',
												inputValue : 0,
												checked : true
											}]
								})]
	});
	var orgForm3 = new Ext.Panel({
		layout : 'form',
		columnWidth : 0.33,
		border : false,
		defaults : {
			anchor : '95%'
		},
		labelAlign : 'right',
		items : [new Ext.form.TextField({
									name : 'areaCode',
									emptyText : '请输入存放区域',
									fieldLabel : '存放区域',
									readOnly : true,
									allowBlank : false
								}),
//								new Ext.form.TextField({
//									name : 'interOrgNo',
//									emptyText : '请输入接口对应单位编号',
//									fieldLabel : '接口单位编号'
//								})
								new Ext.form.NumberField({
									name : 'sortNo',
									emptyText : '请输入排序序号',
									fieldLabel : '排序序号'
								}), new Ext.form.NumberField({
									name : 'calcOrder',
									emptyText : '请输入计算次序',
									fieldLabel : '计算次序'
								}), new Ext.ux.RadioGroup({
									fieldLabel : '是否属于统计单位 ',
									name : 'isStat',
									items : [{
												boxLabel : '是',
												name : 'isStat',
												inputValue : 1
											}, {
												boxLabel : '否',
												name : 'isStat',
												inputValue : 0,
												checked : true
											}]
								})]
	});
	
	var orgForm = new Ext.FormPanel({
			region : 'south',
			height : 160,
			border : false,
			bodyStyle : 'padding: 10px 0px 0px 10px',
			title : '供电所详细信息维护',
			layout : 'column',
			autoScroll : true,
			items : [hiddenOrg,orgForm1, orgForm2, orgForm3]
		});

	var orgPanel = new Ext.Panel({
				layout : 'border',
				split : true,
				items : [orgGrid, orgForm]
			});
	//供电所--------------------------------------------------------------------------------------------------结束
	
			
	//变电站--------------------------------------------------------------------------------------------------开始
	var subsStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
							url : './runman/organizeManSubs!querySubs.action'
						}),
		reader : new Ext.data.JsonReader({
							root : 'subsList'
						}, [{ name:'subsId'},{ name:'subsName'},{ name:'subsNo'},
							{ name:'voltCode'},{ name:'subsAddr'},{ name:'mtNum'},
								{ name:'mtCap'},{ name:'orgNo'},{ name:'inlineId'},
									{ name:'chgDate',
									type : 'date',
									dateFormat : 'Y-m-d\\TH:i:s'},{ name:'runStatusCode'}])
	});
	subsStore.on('load',function(){
		subsForm.getForm().clear();
		subsGrid.getSelectionModel().selectFirstRow();
	});
	var subsColumnModel = new Ext.grid.ColumnModel({
		columns : [{
							header : '变电站id',
							dataIndex : 'subsId',
							align : 'center',
							hidden : true
						},{
							header : '变电站名称',
							align : 'center',
							dataIndex : 'subsName'
						}, {
							header : '变电站编号',
							align : 'center',
							dataIndex : 'subsNo'
						}, {
							header : '电压等级',
							align : 'center',
							dataIndex : 'voltCode',
							renderer : function(val){
								var no = voltStore.find('VOLT_CODE',val);
								if(no != -1)
									return voltStore.getAt(no).get('VOLT');
								else return val;
							}
						}, {
							header : '变电站地址',
							align : 'center',
							dataIndex : 'subsAddr'
						}, {
							header : '主变台数',
							align : 'center',
							dataIndex : 'mtNum'
						}, {
							header : '主变容量',
							align : 'center',
							dataIndex : 'mtCap'
						}, {
							header : '管理单位',
							align : 'center',
							dataIndex : 'orgNo'
						}, {
							header : '入线标识',
							align : 'center',
							dataIndex : 'inlineId'
						}, {
							header : '变更时间',
							align : 'center',
							dataIndex : 'chgDate',
							format : 'Y-m-d H:i:s',
							renderer : function(v) {
								var val = Ext.util.Format.date(v, 'Y-m-d H:i:s');
								var html = '<div align = "center" ext:qtitle="变更时间" ext:qtip="'
										+ val + '">' + val + '</div>';
								return html;
							}
						}, {
							header : '运行状态',
							align : 'center',
							dataIndex : 'runStatusCode',
							renderer : function(val){
								var no = runStatusComStore.find('runStatusCode',val);
								if(no != -1)
									return runStatusComStore.getAt(no).get('runStatusName');
								else return val;
							}
						}],
		defaults : {
			sortable : false,
			menuDisabled : true,
			remoteSort : false
		}
	});
	//供电所record
	var subsRecord = Ext.data.Record.create([{
				name : 'subsId',
				mapping : 'subsId'
			}, {
				name : 'subsName',
				mapping : 'subsName'
			}, {
				name : 'subsNo',
				mapping : 'subsNo'
			}, {
				name : 'voltCode',
				mapping : 'voltCode'
			}, {
				name : 'subsAddr',
				mapping : 'subsAddr'
			}, {
				name : 'mtNum',
				mapping : 'mtNum'
			}, {
				name : 'mtCap',
				mapping : 'mtCap'
			}, {
				name : 'orgNo',
				mapping : 'orgNo'
			}, {
				name : 'inlineId',
				mapping : 'inlineId'
			}, {
				name : 'runStatusCode',
				mapping : 'runStatusCode'
			}]);
			
	var subsGrid = new Ext.grid.GridPanel({
		region : 'center',
		viewConfig : {
			forceFit : false
		},
		autoScroll : true,
		sm : new Ext.grid.RowSelectionModel(),
		cm : subsColumnModel,
		ds : subsStore,
		tbar : ['变电站维护', '->', {
									text : '增加变电站',
									handler : function() {
										subsStore.add(new subsRecord({
													'subsId' : '',
													'subsName' : '',
													'subsNo' : '',
													'voltCode' : '',
													'subsAddr' : '',
													'mtNum' : '',
													'mtCap' : '',
													'orgNo' : om_nodeFieldValue.getValue(),
													'inlineId' : '',
													'runStatusCode' : ''
												}));
										subsGrid.getSelectionModel()
												.selectLastRow();
									}
								}, {
									text : '重新载入',
									handler : function() {
										loadSubs();
									}
								}]
	});
	var loadSubs = function() {
		subsStore.load({
					params : {
						nodeValue : om_nodeFieldValue.getRawValue()
					}
				});
	}
	var subsRowSelect = function(sm, num, rec) {
		if (!Ext.isEmpty(rec))
			subsForm.getForm().setValues(rec.data);
	}
	subsGrid.getSelectionModel().on('rowselect', subsRowSelect);
	
	// 在切换form时将form内容保存到 tmnlParamStore
	// 保存前还要将当前form的内容保存到tmnlParamStore 取得选中行并取值设置
	var subsRowDeselect = function(sm, row, rec) {
		var fmval = subsForm.getForm().getFieldValues();
		for (var o in fmval) {
			rec.set(o, fmval[o]);
		}
		rec.commit();
	}
	subsGrid.getSelectionModel().on('rowdeselect', subsRowDeselect);
	
	var hiddenSubsId = new Ext.form.TextField({
									name : 'subsId',
									hidden : true
								});
	/* 运行状态 */
	var runStatusComStore = new Ext.data.JsonStore({
		url : "./runman/organizeManSubs!queryStatusJson.action",
		fields : [ 'runStatusCode', 'runStatusName' ],
		root : "runStatusList"
	});
	runStatusComStore.load();
	var runStatusCombox = new Ext.form.ComboBox({
		store : runStatusComStore,
		name : 'runStatusCode',
		displayField : 'runStatusName',
		hiddenName : 'runStatusCode',
		valueField : 'runStatusCode',
		typeAhead : true,
		editable:false,
		mode : 'remote',
		fieldLabel : '运行状态',
		labelSeparator : '',
		forceSelection : true,
		triggerAction : 'all',
		emptyText : '--请选择运行状态--',
		blankText : '--请选择运行状态--',
		selectOnFocus : true,
		anchor:'100%',
		allowBlank:false
	});
	
	var subVoltCombox = new Ext.form.ComboBox({
				width : 300,
				hideOnSelect : false,
				displayField : 'VOLT',
				valueField : 'VOLT_CODE',
				hiddenName : 'voltCode',
				editable:false,
				maxHeight : 200,
				store : voltStore,
				mode : 'remote',
				fieldLabel : '电压等级',
				triggerAction : 'all',
				emptyText : '--请选择电压等级--',
				blankText : '--请选择电压等级--'

			});
	
	var subsForm1 = new Ext.Panel({
		layout : 'form',
		columnWidth : 0.33,
		border : false,
		defaults : {
			anchor : '95%'
		},
		labelAlign : 'right',
		items : [new Ext.form.TextField({
									name : 'subsName',
									emptyText : '请输入变电站名称',
									fieldLabel : '变电站名称',
									allowBlank : false
								}),new Ext.form.TextField({
									name : 'subsNo',
									emptyText : '请输入变电站编号',
									fieldLabel : '变电站编号',
									allowBlank : false
								}),subVoltCombox]
	});
	var subsForm2 = new Ext.Panel({
		layout : 'form',
		columnWidth : 0.33,
		border : false,
		defaults : {
			anchor : '95%'
		},
		labelAlign : 'right',
		items : [new Ext.form.TextField({
									name : 'subsAddr',
									emptyText : '请输入变电站地址',
									fieldLabel : '变电站地址'
								}),new Ext.form.TextField({
									name : 'mtNum',
									emptyText : '请输入主变台数',
									fieldLabel : '主变台数'
								}),new Ext.form.TextField({
									name : 'mtCap',
									emptyText : '请输入主变容量',
									fieldLabel : '主变容量'
								})]
	});
	var subsForm3 = new Ext.Panel({
		layout : 'form',
		columnWidth : 0.33,
		border : false,
		defaults : {
			anchor : '95%'
		},
		labelAlign : 'right',
		items : [new Ext.form.TextField({
									name : 'orgNo',
									emptyText : '请输入管理单位',
									fieldLabel : '管理单位',
									allowBlank : false,
									readOnly : true
								}), new Ext.form.TextField({
									name : 'inlineId',
									emptyText : '请输入入线标识',
									fieldLabel : '入线标识'
								}), runStatusCombox]
	});
	
	var subsForm = new Ext.FormPanel({
			region : 'south',
			height : 120,
			border : false,
			bodyStyle : 'padding: 10px 0px 0px 10px',
			title : '变电站详细信息维护',
			layout : 'column',
			autoScroll : true,
			items : [hiddenSubsId,subsForm1, subsForm2, subsForm3]
		});

	var subsPanel = new Ext.Panel({
				layout : 'border',
				split : true,
				items : [subsGrid, subsForm]
			});
	//变电站--------------------------------------------------------------------------------------------------结束

	//线路--------------------------------------------------------------------------------------------------开始
	var lineStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
							url : './runman/organizeManLine!queryLine.action'
						}),
		reader : new Ext.data.JsonReader({
							root : 'lineList'
						}, [{ name:'lineSubsId'},{ name:'lineId'},{ name:'lineNo'},{ name:'lineName'},
							{ name:'orgNo'},{ name:'voltCode'},{ name:'wireSpecCode'},
							{ name:'wireLen'},{ name:'sublineFlag'},
							{ name:'chgDate',type : 'date',dateFormat : 'Y-m-d\\TH:i:s'},
							{ name:'lnFlag'},{ name:'ruralGridFlag'},{ name:'runStatusCode'},
							{ name:'llCalcMode'},{ name:'apLlValue'},{ name:'rpLlValue'},
							{ name:'unitResi'},{ name:'unitReac'},{ name:'consId'}])
	});
	lineStore.on('load',function(){
		lineForm.getForm().clear();
		lineGrid.getSelectionModel().selectFirstRow();
	});
	var lineColumnModel = new Ext.grid.ColumnModel({
		columns : [{
					header : '线路id',
					dataIndex : 'lineId',
					align : 'center',
					hidden : true
				}, {
					header : '线路编号',
					align : 'center',
					dataIndex : 'lineNo'
				}, {
					header : '线路名称',
					align : 'center',
					dataIndex : 'lineName'
				}, {
					header : '管理单位',
					align : 'center',
					dataIndex : 'orgNo'
				}, {
					header : '线路电压',
					align : 'center',
					dataIndex : 'voltCode',
					renderer : function(val){
						var no = voltStore.find('VOLT_CODE',val);
						if(no != -1)
							return voltStore.getAt(no).get('VOLT');
						else return val;
					}
				}, {
					header : '导线规格',
					align : 'center',
					dataIndex : 'wireSpecCode'
				}, {
					header : '导线长度',
					align : 'center',
					dataIndex : 'wireLen'
				}, {
					header : '支线标志',
					align : 'center',
					dataIndex : 'sublineFlag',
					renderer : function(val){
						if(val == '0') return '否';
						else if(val == '1') return '是';
						else return '';
					}
				}, {
					header : '变更时间',
					align : 'center',
					dataIndex : 'chgDate',
					renderer : function(v) {
						var val = Ext.util.Format.date(v, 'Y-m-d H:i:s');
						var html = '<div align = "center" ext:qtitle="变更时间" ext:qtip="'
								+ val + '">' + val + '</div>';
						return html;
					}
				}, {
					header : '有损标志',
					align : 'center',
					dataIndex : 'lnFlag',
					renderer : function(val){
						if(val == '0') return '否';
						else if(val == '1') return '是';
						else return '';
					}
				}, {
					header : '农网标志',
					align : 'center',
					dataIndex : 'ruralGridFlag',
					renderer : function(val){
						if(val == '0') return '否';
						else if(val == '1') return '是';
						else return '';
					}
				}, {
					header : '运行状态',
					align : 'center',
					dataIndex : 'runStatusCode',
					renderer : function(val){
						var no = runStatusComStore.find('runStatusCode',val);
						if(no != -1)
							return runStatusComStore.getAt(no).get('runStatusName');
						else return val;
					}
				}, {
					header : '线损计算方式',
					align : 'center',
					dataIndex : 'llCalcMode'
				}, {
					header : '有功线损计算值',
					align : 'center',
					dataIndex : 'apLlValue'
				}, {
					header : '无功线损计算值',
					align : 'center',
					dataIndex : 'rpLlValue'
				}, {
					header : '单位长度线路电阻',
					align : 'center',
					dataIndex : 'unitResi'
				}, {
					header : '单位长度线路电抗',
					align : 'center',
					dataIndex : 'unitReac'
				}, {
					header : '用户标识',
					align : 'center',
					hidden : true,
					dataIndex : 'consId'
				}],
		defaults : {
			sortable : false,
			menuDisabled : true,
			remoteSort : false
		}
	});
	//供电所record
	var lineRecord = Ext.data.Record.create([{
						name : 'lineSubsId',
						mapping : 'lineSubsId'
					}, {
						name : 'lineId',
						mapping : 'lineId'
					}, {
						name : 'lineNo',
						mapping : 'lineNo'
					}, {
						name : 'lineName',
						mapping : 'lineName'
					}, {
						name : 'orgNo',
						mapping : 'orgNo'
					}, {
						name : 'voltCode',
						mapping : 'voltCode'
					}, {
						name : 'wireSpecCode',
						mapping : 'wireSpecCode'
					}, {
						name : 'wireLen',
						mapping : 'wireLen'
					}, {
						name : 'sublineFlag',
						mapping : 'sublineFlag'
					}, {
						name : 'lnFlag',
						mapping : 'lnFlag'
					}, {
						name : 'ruralGridFlag',
						mapping : 'ruralGridFlag'
					}, {
						name : 'runStatusCode',
						mapping : 'runStatusCode'
					}, {
						name : 'llCalcMode',
						mapping : 'llCalcMode'
					}, {
						name : 'apLlValue',
						mapping : 'apLlValue'
					}, {
						name : 'rpLlValue',
						mapping : 'rpLlValue'
					}, {
						name : 'unitResi',
						mapping : 'unitResi'
					}, {
						name : 'unitReac',
						mapping : 'unitReac'
					}]);
			
	var lineGrid = new Ext.grid.GridPanel({
		region : 'center',
		viewConfig : {
			forceFit : false
		},
		autoScroll : true,
		sm : new Ext.grid.RowSelectionModel(),
		cm : lineColumnModel,
		ds : lineStore,
		tbar : ['线路维护', '->', {
									text : '增加线路',
									handler : function() {
										lineStore.add(new lineRecord({
													'lineSubsId' : om_nodeFieldValue.getRawValue(),
													'lineId' : '',
													'lineNo' : '',
													'lineName' : '',
													'orgNo' : om_lineOrgValue.getRawValue(),
													'voltCode' : '',
													'wireSpecCode' : '',
													'wireLen' : '',
													'sublineFlag' : '',
													'lnFlag' : '',
													'ruralGridFlag' : '',
													'runStatusCode' : '',
													'llCalcMode' : '',
													'apLlValue' : '',
													'rpLlValue' : '',
													'unitResi' : '',
													'unitReac' : ''
												}));
										lineGrid.getSelectionModel()
												.selectLastRow();
									}
								}, {
									text : '重新载入',
									handler : function() {
										loadLine();
									}
								}]
	});
	var loadLine = function() {
		lineStore.load({
					params : {
						nodeValue : om_nodeFieldValue.getRawValue()
					}
				});
	}
	var lineRowSelect = function(sm, num, rec) {
		if (!Ext.isEmpty(rec)){
			lineForm.getForm().setValues(rec.data);
			hiddenLineNo.setValue(rec.get('lineNo'));
		}
	}
	lineGrid.getSelectionModel().on('rowselect', lineRowSelect);
	
	var lineRowDeselect = function(sm, row, rec) {
		var fmval = lineForm.getForm().getFieldValues();
		for (var o in fmval) {
			rec.set(o, fmval[o]);
		}
		rec.commit();
	}
	lineGrid.getSelectionModel().on('rowdeselect', lineRowDeselect);
	
	var hiddenLineId = new Ext.form.TextField({
									name : 'lineId',
									hidden : true
								});
	var hiddenLineNo = new Ext.form.TextField({
									name : 'hiddenLineNo',
									hidden : true
								});
	var hiddenLineSub = new Ext.form.TextField({
									name : 'lineSubsId',
									hidden : true
								});
	var lineRunStatusCombox = new Ext.form.ComboBox({
		store : runStatusComStore,
		name : 'runStatusCode',
		displayField : 'runStatusName',
		hiddenName : 'runStatusCode',
		valueField : 'runStatusCode',
		typeAhead : true,
		editable:false,
		mode : 'remote',
		fieldLabel : '运行状态',
		labelSeparator : '',
		forceSelection : true,
		triggerAction : 'all',
		emptyText : '--请选择运行状态--',
		blankText : '--请选择运行状态--',
		selectOnFocus : true,
		anchor:'100%',
		allowBlank:false
	});
	var lineNoTf = new Ext.form.TextField({
									name : 'lineNo',
									emptyText : '请输入线路编号',
									fieldLabel : '线路编号',
									allowBlank : false
								});
	
	var lineVoltCombox = new Ext.form.ComboBox({
		width : 300,
		hideOnSelect : false,
		displayField : 'VOLT',
		valueField : 'VOLT_CODE',
		editable:false,
		hiddenName : 'voltCode',
		maxHeight : 200,
		store : voltStore,
		mode : 'remote',
		fieldLabel : '线路电压',
		triggerAction : 'all',
		emptyText : '--请选择线路电压--',
		blankText : '--请选择线路电压--'

	});
	var lineForm1 = new Ext.Panel({
		layout : 'form',
		columnWidth : 0.33,
		border : false,
		defaults : {
			anchor : '95%'
		},
		labelAlign : 'right',
		items : [lineNoTf,new Ext.form.TextField({
									name : 'lineName',
									emptyText : '请输入线路名称',
									fieldLabel : '线路名称',
									allowBlank : false
								}),new Ext.form.TextField({
									name : 'orgNo',
									emptyText : '请输入管理单位',
									fieldLabel : '管理单位',
									allowBlank : false,
									readOnly : true
								}),lineVoltCombox,new Ext.form.TextField({
									name : 'wireSpecCode',
									emptyText : '请输入导线规格',
									fieldLabel : '导线规格'
								})]
	});
	var lineForm2 = new Ext.Panel({
		layout : 'form',
		columnWidth : 0.33,
		border : false,
		defaults : {
			anchor : '95%'
		},
		labelAlign : 'right',
		items : [new Ext.form.TextField({
									name : 'wireLen',
									emptyText : '请输入导线长度',
									fieldLabel : '导线长度'
								}),new Ext.ux.RadioGroup({
									fieldLabel : '有损标志',
									name : 'lnFlag',
									items : [{
												boxLabel : '是',
												name : 'lnFlag',
												inputValue : 1,
												checked : true
											}, {
												boxLabel : '否',
												name : 'lnFlag',
												inputValue : 0
											}]
								}),new Ext.ux.RadioGroup({
									fieldLabel : '农网标志',
									name : 'ruralGridFlag',
									items : [{
												boxLabel : '是',
												name : 'ruralGridFlag',
												inputValue : 1,
												checked : true
											}, {
												boxLabel : '否',
												name : 'ruralGridFlag',
												inputValue : 0
											}]
								}),new Ext.ux.RadioGroup({
									fieldLabel : '支线标志',
									name : 'sublineFlag',
									items : [{
												boxLabel : '是',
												name : 'sublineFlag',
												inputValue : 1
											}, {
												boxLabel : '否',
												name : 'sublineFlag',
												inputValue : 0,
												checked : true
											}]
								}),lineRunStatusCombox]
	});
	var lineForm3 = new Ext.Panel({
		layout : 'form',
		columnWidth : 0.33,
		border : false,
		defaults : {
			anchor : '95%'
		},
		labelAlign : 'right',
		items : [new Ext.form.TextField({
									name : 'llCalcMode',
									emptyText : '请输入线损计算方式',
									fieldLabel : '线损计算方式'
								}), new Ext.form.TextField({
									name : 'apLlValue',
									emptyText : '请输入有功线损计算值',
									fieldLabel : '有功线损计算值'
								}), new Ext.form.TextField({
									name : 'rpLlValue',
									emptyText : '请输入无功线损计算值',
									fieldLabel : '无功线损计算值'
								}),new Ext.form.TextField({
									name : 'unitResi',
									emptyText : '请输入单位长度线路电阻',
									fieldLabel : '单位长度电阻'
								}),new Ext.form.TextField({
									name : 'unitReac',
									emptyText : '请输入单位长度线路电抗',
									fieldLabel : '单位长度电抗'
								})]
	});
	
	var lineForm = new Ext.FormPanel({
			region : 'south',
			height : 180,
			border : false,
			bodyStyle : 'padding: 10px 0px 0px 10px',
			title : '线路详细信息维护',
			layout : 'column',
			autoScroll : true,
			items : [hiddenLineSub,hiddenLineId, lineForm1, lineForm2, lineForm3]
		});

	var linePanel = new Ext.Panel({
				layout : 'border',
				split : true,
				items : [lineGrid, lineForm]
			});
	//线路--------------------------------------------------------------------------------------------------结束

	
	
	// 删除按钮
	var om_deleteButton = new Ext.Button({
				text : ' 删除  ',
				handler : om_Delete
			});
	// 删除方法
	function om_Delete(btn) {
		if (om_CardPanel.layout.activeItem == orgPanel) {
			Ext.MessageBox.confirm('提示', '是否删除此供电所？',function(btn){
				if(btn == 'no'){
					return true;				
				}
				Ext.Ajax.request({
					url : './runman/organizeManOrg!deleteOrg.action',
					params : orgForm.getForm().getFieldValues(),
					success : function(response) {
						var obj = Ext.decode(response.responseText);
						if(obj && obj.success){
							Ext.MessageBox.alert('提示', '已成功删除！');
							loadOrg();
						}else{
							Ext.MessageBox.alert('提示', '删除失败！');					
						}
					}
				});
			});
		} else if (om_CardPanel.layout.activeItem == subsPanel) {
			Ext.MessageBox.confirm('提示', '是否删除此变电站？',function(btn){
				if(btn == 'no'){
					return true;				
				}
				Ext.Ajax.request({
					url : './runman/organizeManSubs!deleteSubs.action',
					params : subsForm.getForm().getFieldValues(),
					success : function(response) {
						var obj = Ext.decode(response.responseText);
						if(obj && obj.success){
							Ext.MessageBox.alert('提示', '已成功删除！');
							loadSubs();
						}else{
							Ext.MessageBox.alert('提示', '删除失败！');					
						}
					}
				});
			});
		} else if (om_CardPanel.layout.activeItem == linePanel) {
			Ext.MessageBox.confirm('提示', '是否删除此线路？',function(btn){
				if(btn == 'no'){
					return true;				
				}
				Ext.Ajax.request({
					url : './runman/organizeManLine!deleteLine.action',
					params : lineForm.getForm().getFieldValues(),
					success : function(response) {
						var obj = Ext.decode(response.responseText);
						if(obj && obj.success){
							Ext.MessageBox.alert('提示', '已成功删除！');
							loadLine();
						}else{
							Ext.MessageBox.alert('提示', '删除失败！');					
						}
					}
				});
			});
		} else {
		}
	}
	// 业务处理按钮
	var om_buinessButton = new Ext.Button({
				text : '保存',
				handler : om_Process
			});
	// 业务处理方法
	function om_Process(btn) {
		if (om_CardPanel.layout.activeItem == orgPanel) {
			saveOrg();
		} else if (om_CardPanel.layout.activeItem == subsPanel) {
			saveSubs();
		} else if (om_CardPanel.layout.activeItem == linePanel) {
			saveLine();
		} else {
		}
	}
	var saveOrg = function(){
		if(!orgForm.getForm().isValid()){
			Ext.Msg.alert('提示','请完整输入必填选项');
			return true;
		}
		if(hiddenOrg.getValue()!='' && hiddenOrg.getValue() != orgText.getValue()){
			Ext.MessageBox.confirm('提示', '单位编号已更改，是否保存？', function(btn){
				if(btn == 'yes'){
					Ext.Ajax.request({
						url : './runman/organizeManOrg!saveOrg.action',
						params : orgForm.getForm().getFieldValues(),
						success : function(response) {
							var obj = Ext.decode(response.responseText);
							if(obj && obj.success){
								Ext.MessageBox.alert('提示', obj.msg);
								if("保存成功！" == obj.msg){
									loadOrg();
								}
							}else if(obj && obj.error!=''){
								Ext.MessageBox.alert('提示', '保存失败！');					
							}
						}
					});
				}else {
					return true;
				}
			});
		}else{
			Ext.Ajax.request({
						url : './runman/organizeManOrg!saveOrg.action',
						params : orgForm.getForm().getFieldValues(),
						success : function(response) {
							var obj = Ext.decode(response.responseText);
							if(obj && obj.success){
								Ext.MessageBox.alert('提示', obj.msg);
								if("保存成功！" == obj.msg){
									loadOrg();
								}
							}else if(obj && obj.error!=''){
								Ext.MessageBox.alert('提示', '保存失败！');					
							}
							loadOrg();
						}
					});
		}
	}
	
	var saveSubs = function(){
		if(!subsForm.getForm().isValid()){
			Ext.Msg.alert('提示','请完整输入必填选项');
			return true;
		}
//		if(subsStore)
		
		Ext.Ajax.request({
			url : './runman/organizeManSubs!saveSubs.action',
			params : subsForm.getForm().getFieldValues(),
			success : function(response) {
				var obj = Ext.decode(response.responseText);
				if(obj && obj.success){
					Ext.MessageBox.alert('提示', obj.msg);
					if("保存成功！" == obj.msg){
						loadSubs();
					}
				}else if(obj && obj.error!=''){
					Ext.MessageBox.alert('提示', '保存失败！');					
				}
			}
		});
	}
	
	var saveLine = function(){
		if(!lineForm.getForm().isValid()){
			Ext.Msg.alert('提示','请完整输入必填选项');
			return true;
		}
		
		if(hiddenLineId.getValue()!='' && hiddenLineNo.getValue()!=lineNoTf.getValue()){
			lineNoTf.setValue(hiddenLineNo.getValue());
			Ext.Msg.alert('提示','线路编号不允许修改！');
			return true;
		}
		
		Ext.Ajax.request({
			url : './runman/organizeManLine!saveLine.action',
			params : lineForm.getForm().getFieldValues(),
			success : function(response) {
				var obj = Ext.decode(response.responseText);
				if(obj && obj.success){
					Ext.MessageBox.alert('提示', obj.msg);
					if("保存成功！" == obj.msg){
						loadLine();
					}
				}else if(obj && obj.error!=''){
					Ext.MessageBox.alert('提示', '保存失败！');
				}
			}
		});
	}
	// 综合界面
	var om_CardPanel = new Ext.Panel({
				border : false,
				region : 'center',
				activeItem : 0,
				layout : 'card',
				buttonAlign : 'center',
				items : [orgPanel,subsPanel,linePanel],
				buttons : [om_buinessButton,om_deleteButton]
			});

	var viewPanel = new Ext.Panel({
				border : false,
				layout : 'border',
				items : [om_topForm, om_CardPanel]
			});

	// 渲染到主页面
	renderModel(viewPanel, '组织机构维护');
});
