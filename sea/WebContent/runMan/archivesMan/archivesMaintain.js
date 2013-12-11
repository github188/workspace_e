/**
 * 档案维护页面
 * 
 * @author zhangzhw
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
	/**
	 * 仅监听左边树的用户节点点击，通过用户节点进行查询 监听左边树UserGrid 选择事件，对选中用户进行查询
	 */
	// --左边树监听
	var archieveLeftTreeListener = new LeftTreeListener({
				modelName : '档案维护',
				processClick : function(p, node, e) {
					var obj = node.attributes.attributes;
					var type = node.attributes.type;
					var id = node.id;
					var infos = id.split('_');

					// 通过用户节点进行查询
					if (infos[0] == 'usr') {
						consField.setValue(node.text);
						consFieldValue.setValue(node.id);
					}
					// 填写表单数据
					else {
						// alert("infos[0]="+infos[0]+"
						// infos[2]="+infos[2]+"info="+id
						// +"org_Name="+node.text);
						if (infos[0] == 'trade') {
							consForm.getForm().findField('tradeCode')
									.setValue(infos[1]);
							consForm.getForm().findField('tradeName')
									.setValue(node.text);
							consForm.getForm().findField('hecIndustryCode')
									.setValue(infos[1]);

						} else if (infos[0] == 'org') {// && infos[2] == '06')
														// {
							// 供电单位不可维护
							/*
							 * consForm.getForm().findField('orgNo')
							 * .setValue(infos[1]);
							 * consForm.getForm().findField("orgName").setValue(node.text);
							 * consForm.getForm().findField('areaNo')
							 * .setValue(infos[1].substring(0,infos[1].length-2));
							 */
						} else if (infos[0] == 'sub') {
							// 变电站不可维护
							consForm.getForm().findField('subsId')
									.setValue(infos[1]);
							consForm.getForm().findField('subsName')
									.setValue(node.text);
						} else if (infos[0] = 'line') {
							consForm.getForm().findField('lineId')
									.setValue(infos[1]);
							consForm.getForm().findField('lineIdText')
									.setValue(node.text);
						} else {

						}
					}
					// queryButton.click();
				},
				processUserGridSelect : function(sm, row, record) {
					consField.setValue(record.get('consName'));
					consFieldValue.setValue('usr_' + record.get('consId') + '_'
							+ record.get('tmnlAssetNo'));
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

	// 查询条件值
	var consFieldValue = new Ext.form.Hidden({
				name : 'nodeId'
			}

	);

	// form 查询的参数
	var archiveQueryFormParam = {
		url : './runman/archiveman!queryArchive.action',
		success : function(form, action) {
			if (!action.result)
				return;
			// alert(action.result.ccons.consId);
			consForm.getForm().trackResetOnLoad = true;
			consForm.getForm().setValues(action.result.ccons);
			archiveManCard.layout.setActiveItem(0);

			nextButton.setText("下一步");

			// 后面的Grid全部清空
			rcpStore.removeAll();
			cpParamStore.removeAll();
			tmnlStore.removeAll();
			cmpStore.removeAll();
			cmeterStore.removeAll();
		}
	};

	// 查询按钮
	var queryButton = new Ext.Button({
				text : '查询',
				handler : function() {
					archiveQueryForm.getForm().submit(archiveQueryFormParam);
				}
			});

	var fgf = new Ext.Panel({
				border : false,
				width : 100
			});

	var radioGroupPanel = new Ext.form.RadioGroup({
				layout : 'form',
				border : false,
				labelAlign : 'right',
				id : 'rgp',
				hidden : true,
				items : [{
							boxLabel : '用户',
							name : 'selectType',
							inputValue : '0',
							checked : true,
							listeners : {
								'focus' : function(r, t) {
									var p = inputTextPanel
											.findById('constValue');
									p.emptyText = "请输入用户编号";
									p.reset();
								}
							}
						}, {
							boxLabel : '台区',
							name : 'selectType',
							inputValue : '1',
							listeners : {
								'focus' : function(r, t) {
									var p = inputTextPanel
											.findById('constValue');
									p.emptyText = "请输入台区编号";
									p.reset();
								}
							}
						}]
			});

	var inputTextPanel = new Ext.Panel({
				layout : 'form',
				border : false,
				labelAlign : 'right',
				id : 'inputTextPanel',
				hidden : true,
				items : [new Ext.form.TextField({
							id : 'constValue',
							fieldLabel : '请输入查询数据',
							emptyText : "请输入用户编号",
							name : 'constValue',
							maxLength : 16
						})]
			});
	// 同步ButtonPanel
	var synButton = new Ext.Panel({
		layout : 'form',
		bodyStyle : 'padding: 5px 0px 10px 20px',
		border : false,
		labelAlign : 'right',
		items : [new Ext.Button({
			text : '同步档案',
			hidden : true,
			handler : function() {
				var consType;
				var consValue = inputTextPanel.findById('constValue')
						.getValue();
				var gp = archiveQueryForm.getForm().findField('rgp').getValue().inputValue;
				if (gp == '0') {// 用户选择
					consType = 1;
				} else {// 台区选择
					consType = 2;
				}
				Ext.Ajax.request({
							url : './myactivesyn/activesyn!Syn.action',

							success : function(response) {
								var result = Ext.decode(response.responseText);
								if (result.success == true) {
									Ext.Msg.alert("提示", "档案同步成功！");
									// var tempValue = "0_"+consValue+"_";
									// consField.setValue(tempValue);
									// consFieldValue.setValue(tempValue);
									// queryButton.click();
								} else {
									Ext.Msg.alert("警告", "档案同步失败！");
								}
							},
							params : {
								consType : consType,
								consValue : consValue
							}
						});
			}
		})]
	});

	// 节点查询
	var archiveQueryForm = new Ext.FormPanel({
				bodyStyle : 'padding: 10px 10px 10px 10px',
				region : 'north',
				border : false,
				height : 50,
				layout : 'table',
				layoutConfig : {
					columns : 7
				},
				items : [{
							layout : 'form',
							border : false,
							labelAlign : 'right',
							items : [consField]
						}, consFieldValue, {
							layout : 'form',
							bodyStyle : 'padding: 5px 0px 10px 20px',
							border : false,
							labelAlign : 'right',
							items : [queryButton]
						}, fgf, radioGroupPanel, inputTextPanel, synButton]
			});

	// 用户form字段定义，三列区分
	/* 生产班次 */
	// var shiftNoData = [['1', '单班'], ['2', '二班'], ['3', '三班'], ['4', '连续生产']];
	// var shiftNoStore = new Ext.data.SimpleStore({
	// fields : ['shiftNovalue', 'shiftNotext'],
	// data : shiftNoData
	// });
	var shiftNoCombox = new Ext.ux.ComboBox({
				sql : 'sql=SELECT SHIFT_NO, SHIFT_NAME FROM VW_SHIFT',
				displayField : 'SHIFT_NAME',
				valueField : 'SHIFT_NO',
				editable : false,
				name : 'shiftNo',
				hiddenName : 'shiftNo',
				fieldLabel : '生产班次',
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--',
				selectOnFocus : true,
				anchor : '100%'
			});

	/* 重要性等级 */
	// var rrioCodeData = [['1', '特级'], ['2', '一级'], ['3', '二级']];
	// var rrioCodeStore = new Ext.data.SimpleStore({
	// fields : ['rrioCodevalue', 'rrioCodetext'],
	// data : rrioCodeData
	// });
	var rrioCodeCombox = new Ext.ux.ComboBox({
		sql : "sql=SELECT VALUE,NAME FROM P_CODE WHERE CODE_TYPE = 'custPrioLevel'",
		displayField : 'NAME',
		valueField : 'VALUE',
		editable : false,
		name : 'rrioCode',
		hiddenName : 'rrioCode',
		fieldLabel : '重要性等级',
		forceSelection : true,
		triggerAction : 'all',
		emptyText : '--请选择--',
		blankText : '--请选择--',
		selectOnFocus : true,
		anchor : '100%'
	});
	/* 临时用电标志 */
	// var tmpFlagData = [['01', '装表临时用电'], ['02', '无表临时用电'], ['03', '非临时用电']];
	// var tmpFlagStore = new Ext.data.SimpleStore({
	// fields : ['tmpFlagvalue', 'tmpFlagtext'],
	// data : tmpFlagData
	// });
	var tmpFlagCombox = new Ext.ux.ComboBox({
		sql : "sql = SELECT VALUE, NAME FROM P_CODE WHERE CODE_TYPE = 'tmpFlag'",
		displayField : 'NAME',
		valueField : 'VALUE',
		editable : false,
		name : 'tmpFlag',
		hiddenName : 'tmpFlag',
		fieldLabel : '临时用电标志',
		forceSelection : true,
		triggerAction : 'all',
		emptyText : '--请选择--',
		blankText : '--请选择--',
		selectOnFocus : true,
		anchor : '100%'
	});

	var actionurl = './sysman/dictionary!publicDictionary.action';
	/* TODO: 台区选择，从左边树？ 或是下拉 ，下拉需要后台加权限 */

	var consFormField1 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.33,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [new Ext.form.TextField({
									name : 'consNo',
									emptyText : '请输入用户编号',
									fieldLabel : '用户编号',
									maxLength : 16,
									allowBlank : false
								}), new Ext.form.TextField({
									name : 'orgName',
									emptyText : '请从左边树选择供电单位 ',
									fieldLabel : '供电单位',
									readOnly : true,
									allowBlank : false
								}), new Ext.form.TextField({
									name : 'tgId',
									emptyText : '请输入台区编号 ',
									maxLength : 16,
									fieldLabel : '台区编号'
								}), new Ext.form.TextField({
									name : 'elecAddr',
									emptyText : '请输入用电地址 ',
									fieldLabel : '用电地址',
									allowBlank : false
								}), new Ext.form.TextField({
									name : 'tradeName',
									emptyText : '请从左边树选择行业分类',
									fieldLabel : '行业分类'
								}), new Ext.form.NumberField({
									name : 'contractCap',
									maxValue : 9999999999,
									emptyText : '请输入合同容量 ',
									fieldLabel : '合同容量',
									allowBlank : false
								}), {
							border : false,
							layout : 'form',
							width : 50,
							colspan : 1,
							labelWidth : 100,
							items : [shiftNoCombox]
						}, new Ext.form.TextField({
									name : 'hecIndustryCode',
									emptyText : '请选择高耗能行业类别',
									fieldLabel : '高耗能行业'
								}), new Ext.form.DateField({
									name : 'psDate',
									format : 'Y-m-d',
									emptyText : '请输入送电日期 ',
									fieldLabel : '送电日期'
								}), {
							border : false,
							layout : 'form',
							width : 50,
							colspan : 1,
							labelWidth : 100,
							items : [rrioCodeCombox]
						}, {
							border : false,
							layout : 'form',
							width : 50,
							colspan : 1,
							labelWidth : 100,
							items : [tmpFlagCombox]
						}, new Ext.form.TextField({
									name : 'consId',
									hidden : true,
									hideLabel : true,
									maxLength : 16,
									fieldLabel : '用户标识'
								}), new Ext.form.TextField({
									name : 'custId',
									hidden : true,
									maxLength : 16,
									hideLabel : true,
									fieldLabel : '客户标识'
								}), new Ext.form.TextField({
									name : 'tradeCode',
									hidden : true,
									hideLabel : true,
									emptyText : '请从左边树选择行业分类',
									fieldLabel : '行业分类'
								}), new Ext.form.TextField({// 供电区域标识 隐藏显示
							name : 'orgNo',
							hidden : true,
							maxLength : 16,
							hideLabel : true,
							fieldLabel : '供电单位标识'
						})]
			});
	// 中间控件
	/* 负荷性质 */
	// var loadAttrData = [['1', '一类'], ['2', '二类'], ['3', '三类']];
	// var loadAttrStore = new Ext.data.SimpleStore({
	// fields : ['loadAttrvalue', 'loadAttrtext'],
	// data : loadAttrData
	// });
	var loadAttrCombox = new Ext.ux.ComboBox({
				sql : 'sql = SELECT LODE_ATTR_CODE, LODE_ATTR_NAME FROM VW_LODE_ATTR',
				displayField : 'LODE_ATTR_NAME',
				valueField : 'LODE_ATTR_CODE',
				editable : false,
				name : 'lodeAttrCode',
				hiddenName : 'lodeAttrCode',
				fieldLabel : '负荷性质',
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--',
				selectOnFocus : true,
				anchor : '100%',
				allowBlank : false
			});
	/* 用户分类 */
	// var consSortData = [['01', '高压'], ['02', '低压非居民'], ['03', '低压居民']];
	// var consSortStore = new Ext.data.SimpleStore({
	// fields : ['consSortvalue', 'consSorttext'],
	// data : consSortData
	// });
	var consSortCombox = new Ext.ux.ComboBox({
		sql : 'sql = SELECT CONS_SORT_CODE, CONS_SORT_NAME FROM VW_CONS_SORT_CODE',
		displayField : 'CONS_SORT_NAME',
		valueField : 'CONS_SORT_CODE',
		editable : false,
		name : 'consSortCode',
		hiddenName : 'consSortCode',
		fieldLabel : '用户分类',
		forceSelection : true,
		triggerAction : 'all',
		emptyText : '--请选择--',
		blankText : '--请选择--',
		selectOnFocus : true,
		anchor : '100%',
		allowBlank : false
	});
	/* 用户类型 */
	// var consTypeData = [['1', '专变'], ['2', '公变'], ['3', '关口'], ['4', '线路'],
	// ['5', '居民'], ['6', '电厂'], ['7', '变电站'], ['10', '其它']];
	// var consTypeStore = new Ext.data.SimpleStore({
	// fields : ['consTypevalue', 'consTypetext'],
	// data : consTypeData
	// });
	var consTypeCombox = new Ext.ux.ComboBox({
				sql : 'sql=SELECT CONS_TYPE, CONS_TYPE_NAME FROM VW_CONS_TYPE',
				displayField : 'CONS_TYPE_NAME',
				valueField : 'CONS_TYPE',
				hiddenName : 'consType',
				editable : false,
				disabled : true,
				name : 'consType',
				fieldLabel : '用户类型',
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--',
				selectOnFocus : true,
				anchor : '100%',
				allowBlank : false
			});
	/* 停电标志 */
	var poweroffCodeData = [['01', '已停电'], ['02', '未停电']];
	var poweroffCodeStore = new Ext.data.SimpleStore({
				fields : ['poweroffCodevalue', 'poweroffCodetext'],
				data : poweroffCodeData
			});
	var poweroffCodeCombox = new Ext.form.ComboBox({
				store : poweroffCodeStore,
				displayField : 'poweroffCodetext',
				valueField : 'poweroffCodevalue',
				editable : false,
				hiddenName : 'poweroffCode',
				name : 'poweroffCode',
				mode : 'local',
				fieldLabel : '停电标志',
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--',
				selectOnFocus : true,
				anchor : '100%'
			});
	var consFormField2 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.33,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [new Ext.form.TextField({
									name : 'consName',
									emptyText : '请输入用户名称',
									fieldLabel : '用户名称',
									allowBlank : false
								}), new Ext.form.TextField({
									name : 'areaNoText',
									readOnly : true,
									emptyText : '请选择供电区域 ',
									fieldLabel : '供电区域',
									allowBlank : false
								}), new Ext.form.TextField({
									name : 'lineIdText',
									readOnly : true,
									emptyText : '请选择线路 ',
									fieldLabel : '线路',
									allowBlank : false
								}), {
							border : false,
							layout : 'form',
							width : 50,
							colspan : 1,
							labelWidth : 100,
							items : [consSortCombox],
							allowBlank : false
						}, {
							border : false,
							layout : 'form',
							width : 50,
							colspan : 1,
							labelWidth : 100,
							items : [consTypeCombox],
							allowBlank : false
						}, new Ext.form.NumberField({
									name : 'runCap',
									emptyText : '请输入运行容量 ',
									fieldLabel : '运行容量',
									maxValue : 9999999999,
									allowBlank : false
								}), {
							border : false,
							layout : 'form',
							width : 50,
							colspan : 1,
							labelWidth : 100,
							items : [loadAttrCombox]
						}, new Ext.form.TextField({
									name : 'holiday',
									emptyText : '请输入厂休日',
									fieldLabel : '厂休日'
								}), new Ext.form.DateField({
									name : 'cancelDate',
									format : 'Y-m-d',
									emptyText : '请输入销户日期 ',
									fieldLabel : '销户日期'
								}), {
							border : false,
							layout : 'form',
							width : 50,
							colspan : 1,
							labelWidth : 100,
							items : [poweroffCodeCombox]
						}, new Ext.form.TextField({
									name : 'applyNo',
									emptyText : '请输入用电业务受理编号',
									maxLength : 8,
									fieldLabel : '受理编号'
								}), new Ext.form.TextField({// 线路标识 隐藏显示
							name : 'lineId',
							hidden : true,
							maxLength : 16,
							hideLabel : true,
							fieldLabel : '线路标识'
						}), new Ext.form.TextField({// 供电区域标识 隐藏显示
							name : 'areaNo',
							hidden : true,
							maxLength : 16,
							hideLabel : true,
							fieldLabel : '供电区域代码'
						})]
			});
	// 右边空间
	/** 用电类别 */
	// var elecTypeData = [['1', '大工业用电'], ['2', '中小化肥'], ['3', '居民生活用电'],
	// ['4', '农业生产用电'], ['5', '贫困县农业排灌用电']];
	// var elecTypeStore = new Ext.data.SimpleStore({
	// fields : ['elecTypevalue', 'elecTypetext'],
	// data : elecTypeData
	// });
	var elecTypeCombox = new Ext.ux.ComboBox({
				sql : 'sql=SELECT ELEC_TYPE_CODE, ELEC_TYPE FROM VW_ELEC_TYPE_CODE',
				displayField : 'ELEC_TYPE',
				valueField : 'ELEC_TYPE_CODE',
				editable : false,
				name : 'elecType',
				hiddenName : 'elecType',
				fieldLabel : '用电类别',
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--',
				selectOnFocus : true,
				anchor : '100%'
			});

	var subVoltCombox = new Ext.ux.ComboBox({
				sql : 'sql=SELECT VOLT_CODE, VOLT FROM VW_VOLT_CODE',
				width : 300,
				hideOnSelect : false,
				displayField : 'VOLT',
				valueField : 'VOLT_CODE',
				hiddenName : 'voltCode',
				maxHeight : 200,
				mode : 'remote',
				fieldLabel : '电压等级',
				triggerAction : 'all',
				emptyText : '--请选择电压等级--',
				blankText : '--请选择电压等级--'

			});

	var consStatusCombox = new Ext.ux.ComboBox({
				sql : 'sql=SELECT STATUS_CODE, STATUS_NAME FROM VW_CONS_STATUS_CODE',
				width : 300,
				hideOnSelect : false,
				displayField : 'STATUS_NAME',
				valueField : 'STATUS_CODE',
				hiddenName : 'statusCode',
				maxHeight : 200,
				mode : 'remote',
				fieldLabel : '用户状态',
				triggerAction : 'all',
				emptyText : '--请选择用户状态--',
				blankText : '--请选择用户状态--'
			});

	var consCapGradeNo = new Ext.ux.ComboBox({
				sql : 'sql=SELECT CAP_GRADE_NO, CAP_GRADE_NAME FROM VW_CAP_GRADE',
				width : 300,
				hideOnSelect : false,
				displayField : 'CAP_GRADE_NAME',
				valueField : 'CAP_GRADE_NO',
				hiddenName : 'capGradeNo',
				maxHeight : 200,
				fieldLabel : '容量等级',
				triggerAction : 'all',
				emptyText : '--请选择容量等级--',
				blankText : '--请选择容量等级--'
			});

	var consFormField3 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.33,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [new Ext.form.TextField({
									name : 'custNo',
									emptyText : '请输入客户编号',
									maxLength : 16,
									fieldLabel : '客户编号'
								}), new Ext.form.TextField({
									name : 'subsName',
									emptyText : '请选择变电站 ',
									readOnly : true,
									fieldLabel : '变电站',
									allowBlank : false
								}), new Ext.form.TextField({
									name : 'custQueryNo',
									maxLength : 32,
									emptyText : '请输入自定义查询码 ',
									fieldLabel : '查询码'
								}), new Ext.form.TextField({
									name : 'orgnConsNo',
									emptyText : '请输入原用户编号',
									maxLength : 16,
									fieldLabel : '原用户编号'
								}), {
							border : false,
							colspan : 1,
							items : [elecTypeCombox],
							labelWidth : 100,
							width : 10,
							layout : 'form'
						}, consCapGradeNo, subVoltCombox,
						new Ext.form.DateField({
									name : 'buildDate',
									format : 'Y-m-d',
									emptyText : '请输入立户日期',
									fieldLabel : '立户日期'
								}), consStatusCombox, new Ext.form.TextField({
									name : 'mrSectNo',
									emptyText : '请输入抄表段编号 ',
									maxLength : 16,
									fieldLabel : '抄表段编号'
								}), new Ext.form.DateField({
									name : 'applyDate',
									format : 'Y-m-d',
									emptyText : '请输入用电业务受理日期 ',
									fieldLabel : '受理日期'
								}), new Ext.form.TextField({
									name : 'subsId',
									hidden : true,
									maxLength : 16,
									hideLabel : true,
									fieldLabel : '变电站标识'
								})]
			});

	// 用户信息管理页面
	var consForm = new Ext.FormPanel({
				border : false,
				bodyStyle : 'padding: 10px 0px 0px 10px',
				title : '用户信息管理  --行业及电网结构相关数据直接点击左边树节点填充',
				layout : 'column',
				items : [consFormField1, consFormField2, consFormField3]
			});

	// -------------------------------------用户信息管理完成

	// -----------------------------采集点设置
	var rcpStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './runman/archivercpman!queryRcp.action'
						}),
				// proxy : new Ext.data.MemoryProxy(data),
				reader : new Ext.data.JsonReader({
							root : 'rcpList'
						}, [{
									name : 'cpNo'
								}, {
									name : 'name'
								}, {
									name : 'cpTypeCode'
								}, {
									name : 'statusCode'
								}, {
									name : 'cpAddr'
								}, {
									name : 'gpsLongitude'
								}, {
									name : 'gpsLatitude'
								}])
			});

	// 采集点类别
	var cptypesql = "sql=SELECT CP_TYPE_CODE,  CP_TYPE  FROM VW_CP_TYPE_CODE";

	var cptypesqlUrl = Ext.urlAppend(actionurl, cptypesql);
	// alert(fieldtypeUrl);
	var cptypeStore = new Ext.data.JsonStore({
				url : cptypesqlUrl,
				fields : ['CP_TYPE_CODE', 'CP_TYPE'],
				root : "dicList"
			});
	cptypeStore.load(); // 初始就载入
	var cptypeCombox = new Ext.form.ComboBox({
				store : cptypeStore,
				width : 300,
				hideOnSelect : false,
				displayField : 'CP_TYPE',
				valueField : 'CP_TYPE_CODE',
				hiddenName : 'cpTypeCode',
				maxHeight : 200,
				mode : 'remote',
				fieldLabel : '采集点类型',
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--'

			});
	// 采集点状态
	var actionurl = './sysman/dictionary!publicDictionary.action';
	var sql = "sql=SELECT CP_STATUS_CODE, CP_STATUS FROM VW_CP_STATUS_CODE";
	var cpStatusCodeUrl = Ext.urlAppend(actionurl, sql);
	var cpStatusStore = new Ext.data.JsonStore({
				url : cpStatusCodeUrl,
				fields : ['CP_STATUS_CODE', 'CP_STATUS'],
				root : "dicList"
			});
	cpStatusStore.load();
	var cpStatusCombox = new Ext.form.ComboBox({
				store : cpStatusStore,
				displayField : 'CP_STATUS',
				valueField : 'CP_STATUS_CODE',
				editable : false,
				name : 'cpTypeCode',
				mode : 'remote',
				hiddenName : 'cpTypeCode',
				fieldLabel : '采集点状态',
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--',
				selectOnFocus : true,
				anchor : '100%'
			});

	var cpColumnModel = new Ext.grid.ColumnModel({
				columns : [{
							header : '采集点编号',
							dataIndex : 'cpNo',
							renderer : function(val) {
								if (Ext.isEmpty(val))
									return '<font color=red>系统自动</font>';
								else
									return val;
							}
						}, {
							header : '采集点名称',
							dataIndex : 'name',
							editor : new Ext.form.TextField({
										allowBlank : false
									})
						}, {
							header : '采集类型',
							dataIndex : 'cpTypeCode',
							renderer : function(val) {
								var row = cptypeStore.find('CP_TYPE_CODE', val);
								if (row > -1)
									return cptypeStore.getAt(row)
											.get('CP_TYPE');
								return val;
							},
							editor : cptypeCombox
						}, {
							header : '状态',
							dataIndex : 'statusCode',
							renderer : function(val) {
								var row = cpStatusStore.find('CP_STATUS_CODE',
										val);
								if (row > -1)
									return cpStatusStore.getAt(row)
											.get('CP_STATUS');
								return val;
							},
							editor : cpStatusCombox
						}, {
							header : '采集点地址',
							dataIndex : 'cpAddr',
							editor : new Ext.form.TextField()
						}, {
							header : 'GPS经度',
							dataIndex : 'gpsLongitude',
							editor : new Ext.form.TextField()
						}, {
							header : 'GPS纬度',
							dataIndex : 'gpsLatitude',
							editor : new Ext.form.TextField()
						}],
				defaults : {
					sortable : false,
					menuDisabled : true,
					remoteSort : false
				}
			});

	// 采集点记录类型
	var cpRecord = Ext.data.Record.create([{
				name : 'cpNo',
				mapping : 'cpNo'
			}, {
				name : 'name',
				mapping : 'name'
			}, {
				name : 'cpTypeCode',
				mapping : 'cpTypeCode'
			}, {
				name : 'statusCode',
				mapping : 'statusCode'
			}, {
				name : 'cpAddr',
				mapping : 'cpAddr'
			}, {
				name : 'gpsLongitude',
				mapping : 'gpsLongitude'
			}, {
				name : 'gpsLatitude',
				mapping : 'gpsLatitude'
			}]);

	var cpEditGrid = new Ext.grid.EditorGridPanel({
				height : 160,
				sm : new Ext.grid.RowSelectionModel(),
				region : 'north',
				autoScroll : true,
				clicksToEdit : 1,
				cm : cpColumnModel,
				ds : rcpStore,
				tbar : ['采集点维护', '->', {
							text : '增加采集点',
							handler : function() {
								rcpStore.add(new cpRecord({
											'cpNo' : '',
											'name' : '采集点',
											'cpTypeCode' : '2',
											'statusCode' : '04',
											'cpAddr' : '采集点地址',
											'gpsLongitude' : '',
											'gpsLatitude' : ''

										}));
							}
						}, {
							text : '保存新增采集点',
							handler : function() {
								saveRcpsMan();
							}
						}, {
							text : '删除选定行',
							handler : function() {
								deleteSelectRow();
							}
						}, {
							text : '重新载入',
							handler : function() {
								loadRcp();
							}
						}]
			});

	var cpRowSelect = function(sm, num, rec) {
		var cpNoValue = rec.get('cpNo');
		if (cpNoValue == "" || cpNoValue == null) {// 如果cpNoValue为空 禁用下d
			ReoDevGrid.disable();
		} else {
			ReoDevGrid.enable();
			selectCpNo = cpNoValue;
			ReoDevStore.load({
						params : {
							cpNo : cpNoValue
						}
					});
			/*
			 * var record = cpParamStore.getAt(num); if (!Ext.isEmpty(record))
			 * cpParamForm.getForm().setValues(record.data);
			 */
		}

	};

	/**
	 * 删除选定的采集点行
	 */
	var deleteSelectRow = function() {
		var selectRow = cpEditGrid.getSelectionModel().selections.items;
		if (selectRow.length <= 0) {
			Ext.Msg.alert("提示", "请选择要进行删除的采集点行！");
			return;
		} else {
			var cpno = selectRow[0].get("cpNo");
			if (cpno == null || cpno == "") {
				Ext.Msg.alert("提示", "此行数据并非入库");
				return;
			} else {
				Ext.Ajax.request({
							url : './runman/archivercpman!deleteRcp.action',
							success : function(response) {
								var result = Ext.decode(response.responseText);
								if (result.success == true) {
									Ext.Msg.alert("提示", "成功删除此采集点[" + cpno
													+ "]信息！");
									loadRcp();
								} else {
									Ext.Msg.alert("警告", "删除采集点[" + cpno
													+ "]失败！请检查此采集点下是否含有相关数据！");
								}
							},
							params : {
								cpNo : cpno
							}
						});
			}
		}
	};
	cpEditGrid.getSelectionModel().on('rowselect', cpRowSelect);

	// 在切换form时将form内容保存到 cpParamStore
	// 保存前还要将当前form的内容保存到cpParamStore 取得选中行并取值设置
	// var cpRowDeselect = function(sm, row, rec) {
	// if(!cpParamForm.getForm().isValid()){
	// Ext.Msg.alert('提示','请完整输入必填选项');
	// }

	/*
	 * var fmval = cpParamForm.getForm().getFieldValues(); var cpParam =
	 * cpParamStore.getAt(row);
	 * 
	 * for (var o in fmval) { cpParam.set(o, fmval[o]); }
	 * 
	 * cpParam.commit();
	 */

	// };
	// cpEditGrid.getSelectionModel().on('rowdeselect', cpRowDeselect);
	// var cpParamGrid=new Ext.grid.GridPanel();
	// 
	var cpParamGrid = new Ext.Panel({
				region : 'west'
			});

	var cpParamRecord = Ext.data.Record.create([{
				name : 'commParaId',
				mapping : 'commParaId'
			}, {
				name : 'cpNo',
				mapping : 'cpNo'
			}, {
				name : 'terminalAddr',
				mapping : 'terminalAddr'
			}, {
				name : 'protocolCode',
				mapping : 'protocolCode'
			}, {
				name : 'channelNo',
				mapping : 'channelNo'
			}, {
				name : 'rtsOn',
				mapping : 'rtsOn'
			}, {
				name : 'rtsOff',
				mapping : 'rtsOff'
			}, {
				name : 'transmitDelay',
				mapping : 'transmitDelay'
			}, {
				name : 'respTimeout',
				mapping : 'respTimeout'
			}, {
				name : 'masterIp',
				mapping : 'masterIp'
			}, {
				name : 'masterPort',
				mapping : 'masterPort'
			}, {
				name : 'spareIpAddr',
				mapping : 'spareIpAddr'
			}, {
				name : 'sparePort',
				mapping : 'sparePort'
			}, {
				name : 'gatewayIp',
				mapping : 'gatewayIp'
			}, {
				name : 'gatewayPort',
				mapping : 'gatewayPort'
			}, {
				name : 'proxyIpAddr',
				mapping : 'proxyIpAddr'
			}, {
				name : 'proxyPort',
				mapping : 'proxyPort'
			}, {
				name : 'gprsCode',
				mapping : 'gprsCode'
			}, {
				name : 'smsNo',
				mapping : 'smsNo'
			}, {
				name : 'apn',
				mapping : 'apn'
			}, {
				name : 'heartbeatCycle',
				mapping : 'heartbeatCycle'
			}, {
				name : 'startDate',
				mapping : 'startDate'
			}, {
				name : 'algNo',
				mapping : 'algNo'
			}, {
				name : 'algKey',
				mapping : 'algKey'
			}]);

	var cpParamStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './runman/archivercpman!queryRcp.action'
						}),
				// proxy : new Ext.data.MemoryProxy(data),
				reader : new Ext.data.JsonReader({
							root : 'rcpParaList'
						}, [{
									name : 'commParaId'
								}, {
									name : 'cpNo'
								}, {
									name : 'terminalAddr'
								}, {
									name : 'protocolCode'
								}, {
									name : 'channelNo'
								}, {
									name : 'rtsOn'
								}, {
									name : 'rtsOff'
								}, {
									name : 'transmitDelay'
								}, {
									name : 'respTimeout'
								}, {
									name : 'masterIp'
								}, {
									name : 'masterPort'
								}, {
									name : 'spareIpAddr'
								}, {
									name : 'sparePort'
								}, {
									name : 'gatewayIp'
								}, {
									name : 'gatewayPort'
								}, {
									name : 'proxyIpAddr'
								}, {
									name : 'proxyPort'
								}, {
									name : 'gprsCode'
								}, {
									name : 'smsNo'
								}, {
									name : 'apn'
								}, {
									name : 'heartbeatCycle'
								}, {
									name : 'startDate',
									type : 'date'
								}, {
									name : 'algNo'
								}, {
									name : 'algKey'
								}])
			});
	cpParamStore.on('load', function() {
				cpEditGrid.getSelectionModel().selectFirstRow();
			});
	cpParamStore.load();

	var protocolComStore = new Ext.data.JsonStore({
				url : "./sysman/generaltree!protocolList.action",
				fields : ['protocolCode', 'protocolName'],
				root : "protocolList"
			});
	protocolComStore.load();
	var protocolCombox = new Ext.form.ComboBox({
				store : protocolComStore,
				displayField : 'protocolName',
				valueField : 'protocolCode',
				hiddenName : 'protocolCode',
				typeAhead : true,
				mode : 'remote',
				fieldLabel : '规约类型',
				name : 'protocolCode',
				triggerAction : 'all',
				emptyText : '--请选择规约类型--',
				selectOnFocus : true
			});

	var cpParamForm1 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.33,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [new Ext.form.TextField({
									name : 'commParaId',
									fieldLabel : '通信参数标识',
									hidden : true,
									hideLabel : true
								}), new Ext.form.TextField({
									name : 'terminalAddr',
									fieldLabel : '终端地址码',
									value : '',
									hideLabel : true,
									hidden : true,
									allowBlank : false
								}), new Ext.form.TextField({
									name : 'cpNo',
									fieldLabel : '采集点标识',
									hideLabel : true,
									hidden : true,
									allowBlank : false
								}), protocolCombox, new Ext.form.TextField({
									name : 'channelNo',
									maxLength : 16,
									// emptyText : '请输入信道编号',
									fieldLabel : '信道编号'
								}), new Ext.form.TextField({
									name : 'rtsOn',
									// emptyText : '请输入RTS开 ',
									fieldLabel : 'RTS开',
									value : '0',
									maxLength : 5
								}), new Ext.form.TextField({
									name : 'rtsOff',
									// emptyText : '请输入RTS关',
									fieldLabel : 'RTS关',
									value : '0',
									maxLength : 5
								}), new Ext.form.TextField({
									name : 'transmitDelay',
									// emptyText : '请输入传输延时',
									fieldLabel : '传输延时',
									maxLength : 5,
									value : '0'
								}), new Ext.form.TextField({
									name : 'algNo',
									allowBlank : false,
									// emptyText : '请输入算法编号 ',
									maxLength : 16,
									fieldLabel : '算法编号',
									value : 99
								}), new Ext.form.TextField({
									name : 'algKey',
									allowBlank : false,
									// emptyText : '请输入算法密钥',
									maxLength : 32,
									fieldLabel : '算法密钥',
									value : 0
								})]
			});

	var cpParamForm2 = new Ext.Panel({
		layout : 'form',
		columnWidth : 0.33,
		border : false,
		defaults : {
			anchor : '95%'
		},
		labelAlign : 'right',
		items : [new Ext.form.TextField({
							name : 'respTimeout',
							// emptyText : '请输入响应超时 ',
							fieldLabel : '响应超时',
							value : '0',
							maxLength : 5
						}), new Ext.form.TextField({
					name : 'masterIp',
					// emptyText : '请输入主用IP地址',
					fieldLabel : '主用IP地址',
					validator : function(str) {
						if (str == null || str == '')
							return true;
						var time = str
								.match(/^(((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))\.){3}((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))$/);
						if (time != null)
							return true;
						else
							return false;
					},
					invalidText : '请输入有效的ip地址'
				}), new Ext.form.TextField({
							name : 'masterPort',
							// emptyText : '请输入主用端口 ',
							fieldLabel : '主用端口',
							value : '0',
							maxLength : 5
						}), new Ext.form.TextField({
					name : 'spareIpAddr',
					// emptyText : '请输入备用IP地址 ',
					fieldLabel : '备用IP地址',
					validator : function(str) {
						if (str == null || str == '')
							return true;
						var time = str
								.match(/^(((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))\.){3}((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))$/);
						if (time != null)
							return true;
						else
							return false;
					},
					invalidText : '请输入有效的ip地址'
				}), new Ext.form.TextField({
							name : 'sparePort',
							// emptyText : '请输入备用端口',
							fieldLabel : '备用端口',
							value : '0',
							maxLength : 5
						}), new Ext.form.TextField({
					name : 'gatewayIp',
					// emptyText : '请输入网关IP地址',
					fieldLabel : '网关IP地址',
					validator : function(str) {
						if (str == null || str == '')
							return true;
						var time = str
								.match(/^(((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))\.){3}((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))$/);
						if (time != null)
							return true;
						else
							return false;
					},
					invalidText : '请输入有效的ip地址'
				}), new Ext.form.TextField({
							name : 'gatewayPort',
							// emptyText : '请输入网关端口',
							fieldLabel : '网关端口',
							value : '0',
							maxLength : 5
						})]
	});
	// 心跳周期
	var heartBeatCycleData = [['1', '1'], ['5', '5'], ['10', '10'],
			['15', '15'], ['30', '30']];
	var heartBeatCycleStore = new Ext.data.SimpleStore({
				fields : ['heartBeatCyclevalue', 'heartBeatCycletext'],
				data : heartBeatCycleData
			});
	var heartBeatCycleCombox = new Ext.form.ComboBox({
				store : heartBeatCycleStore,
				displayField : 'heartBeatCycletext',
				valueField : 'heartBeatCyclevalue',
				editable : false,
				name : 'heartBeatCycle',
				hiddenName : 'heartBeatCycle',
				mode : 'local',
				fieldLabel : '心跳周期',
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--',
				selectOnFocus : true,
				anchor : '100%'
			});
	var cpParamForm3 = new Ext.Panel({
		layout : 'form',
		columnWidth : 0.33,
		border : false,
		defaults : {
			anchor : '95%'
		},
		labelAlign : 'right',
		items : [new Ext.form.TextField({
			name : 'proxyIpAddr',
			// emptyText : '请输入代理IP地址',
			fieldLabel : '代理IP地址',
			validator : function(str) {
				if (str == null || str == '')
					return true;
				var time = str
						.match(/^(((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))\.){3}((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))$/);
				if (time != null)
					return true;
				else
					return false;
			},
			invalidText : '请输入有效的ip地址'
		}), new Ext.form.TextField({
					name : 'proxyPort',
					// emptyText : '请输入代理端口',
					fieldLabel : '代理端口',
					value : '0',
					maxLength : 5
				}), new Ext.form.TextField({
					name : 'gprsCode',
					// emptyText : '请输入GPRS号码 ',
					fieldLabel : 'GPRS号码',
					maxLength : 32
				}), new Ext.form.TextField({
					name : 'smsNo',
					// emptyText : '请输入短信号码',
					fieldLabel : '短信号码',
					maxLength : 32
				}), new Ext.form.TextField({
					name : 'apn',
					// emptyText : '请输入apn ',
					maxLength : 20,
					fieldLabel : 'apn'
				}), {
			border : false,
			colspan : 1,
			items : [heartBeatCycleCombox],
			labelWidth : 100,
			layout : 'form'
		}, new Ext.form.DateField({
					name : 'startDate',
					format : 'Y-m-d',
					// emptyText : '请输入启用日期',
					fieldLabel : '启用日期',
					value : new Date()
				})]
	});
	var cpParamForm = new Ext.form.FormPanel({
				region : 'center',
				border : false,
				bodyStyle : 'padding: 10px 0px 0px 10px',
				title : '终端参数维护',
				layout : 'column',
				autoScroll : true,
				items : [cpParamForm1, cpParamForm2, cpParamForm3]
			});

	// -----------------------------------采集器-------------------------------------------------//
	// 采集器GridPanel
	var ReoDevGrid = new Ext.grid.GridPanel({
				region : 'center',
				border : false,
				// title : '采集器列表',
				tbar : ['采集器列表', '->', {
							text : '新增采集器',
							handler : function() {
								addNewReoDev();
							}
						}, {
							text : '删除选定采集器',
							handler : function() {
								deleteSelectReoDev();
							}
						}],
				height : 160,
				autoScroll : true,
				viewConfig : {
					forceFit : false
				},
				columns : [{
							header : '资产标识',
							dataIndex : 'collectorid'
						}, {
							header : '采集点编号',
							dataIndex : 'cpno'
						}, {
							header : '通信方式',
							dataIndex : 'collmode'
						}, {
							header : '通讯地址',
							dataIndex : 'commaddr'
						}, {
							header : '端口号',
							dataIndex : 'portno'
						}, {
							header : '型号 ',
							dataIndex : 'devmode'
						}, {
							header : '终端类型  ',
							dataIndex : 'devtypecode'
						}, {
							header : '运行状态  ',
							dataIndex : 'statuscode'
						}, {
							header : '安装日期  ',
							dataIndex : 'instdate'
						}, {
							header : '安装地址  ',
							dataIndex : 'installloc'
						}, {
							header : '安装楼号  ',
							dataIndex : 'houseno'
						}, {
							header : '安装楼层  ',
							dataIndex : 'floorno'
						}, {
							header : '安装门洞  ',
							dataIndex : 'doorno'
						}, {
							header : 'IP地址  ',
							dataIndex : 'ipaddr'
						}, {
							header : 'IP端口号  ',
							dataIndex : 'ipport'
						}],
				// cm : ReoDevClumn,
				store : ReoDevStore
			});

	// 注册行双击事件
	ReoDevGrid.on('rowdblclick', function(row, index, data) {
				var rowrecord = ReoDevStore.getAt(index);
				updataReoDev(rowrecord);
			});

	// 删除选定采集器
	var deleteSelectReoDev = function() {
		var selectRow = cpEditGrid.getSelectionModel().selections.items;
		var cpno = selectRow[0].get("cpNo");

		var selectRow = ReoDevGrid.getSelectionModel().selections.items;
		if (selectRow.length <= 0 || selectRow[0].get("collectorid") == null) {
			Ext.Msg.alert("提示", "请在下列列表中选择要进行删除的采集器！");
			return;
		}
		var collectorid = selectRow[0].get("collectorid");
		Ext.Ajax.request({
					url : './runman/archivercpman!deleteREODev.action',
					success : function(response) {
						var result = Ext.decode(response.responseText);
						if (result.success == true) {
							Ext.Msg.alert("提示", "成功删除此采集器[" + collectorid
											+ "]信息！");
							ReoDevStore.load({
										params : {
											cpNo : cpno
										}
									});
						} else {
							Ext.Msg
									.alert("警告", "删除采集器[" + collectorid
													+ "]失败！");
						}
					},
					params : {
						collId : collectorid
					}
				});

	};

	// 新增采集器
	var addNewReoDev = function() {
		var selectRow = cpEditGrid.getSelectionModel().selections.items;
		if (selectRow.length <= 0 || selectRow[0].get("cpNo") == null
				|| selectRow[0].get("cpNo").trim() == "") {
			Ext.Msg.alert("提示", "请先选择新增采集器所属的采集点");
			return;
		}
		var cpno = selectRow[0].get("cpNo");
		// reoForm2.getForm().findField("cpno").setValue(cpno);

		// 采集器运行状态
		var ReoDevStatusCodeCombox = new Ext.ux.ComboBox({
			sql : 'sql=SELECT STATUS_CODE, STATUS_NAME FROM VW_TMNL_STATUS_RUN',
			displayField : 'STATUS_NAME',
			valueField : 'STATUS_CODE',
			editable : false,
			name : 'reodev.statuscode',
			hiddenName : 'reodev.statuscode',
			fieldLabel : '采集器运行状态',
			forceSelection : true,
			triggerAction : 'all',
			emptyText : '--请选择--',
			blankText : '--请选择--',
			selectOnFocus : true,
			anchor : '100%',
			allowBlank : false
		});

		var reoForm1 = new Ext.Panel({
			layout : 'form',
			columnWidth : 0.4,
			border : false,
			defaults : {
				anchor : '95%'
			},
			labelAlign : 'right',
			items : [/*
						 * new Ext.form.TextField({ fieldLabel:'资产标识',
						 * name:'collectorid', emptyText:'请输入资产（采集器标识）,新增可以为空'
						 * }),
						 */	new Ext.form.TextField({
								fieldLabel : '上级ID',
								name : 'reodev.preid',
								value : '11111111',
								emptyText : '请输入上级ID',
								hideLabel : true,
								hidden : true,
								allowBlank : false
							}), new Ext.form.TextField({
								fieldLabel : '采集点编号',
								value : cpno,
								name : 'reodev.cpno',
								emptyText : '请输入采集点编号',
								readOnly : true,
								allowBlank : false
							}), new Ext.form.TextField({
								fieldLabel : '型号',
								name : 'reodev.devmode',
								emptyText : '请输入型号',
								allowBlank : false
							}), ReoDevStatusCodeCombox,
					new Ext.form.TextField({
								fieldLabel : '安装地址',
								name : 'reodev.installloc',
								emptyText : '请输入安装地址',
								allowBlank : false
							}), new Ext.form.TextField({
								fieldLabel : '安装楼层',
								name : 'reodev.floorno',
								emptyText : '请输入安装楼层',
								allowBlank : false
							}), new Ext.form.TextField({
						fieldLabel : 'IP地址',
						name : 'reodev.ipaddr',
						validator : function(str) {
							if (str == null || str == '')
								return true;
							var time = str
									.match(/^(((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))\.){3}((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))$/);
							if (time != null)
								return true;
							else
								return false;
						},
						invalidText : '请输入有效的ip地址',
						emptyText : '请输入IP地址',
						allowBlank : false
					}), new Ext.form.TextField({
								fieldLabel : 'IP端口号',
								name : 'reodev.ipport',
								emptyText : '请输入IP端口号',
								allowBlank : false
							})]
		});
		var reoForm2 = new Ext.Panel({
					layout : 'form',
					columnWidth : 0.4,
					border : false,
					defaults : {
						anchor : '95%'
					},
					labelAlign : 'right',
					items : [new Ext.form.TextField({
										fieldLabel : '通讯地址',
										name : 'reodev.commaddr',
										emptyText : '请输入通讯地址',
										allowBlank : false
									}), new Ext.form.TextField({
										fieldLabel : '通信方式',
										name : 'reodev.collmode',
										emptyText : '请输入通信方式',
										allowBlank : false
									}), new Ext.form.TextField({
										fieldLabel : '端口号',
										name : 'reodev.portno',
										emptyText : '请输入端口号',
										allowBlank : false
									}), new Ext.form.TextField({
										fieldLabel : '终端类型',
										name : 'reodev.devtypecode',
										emptyText : '请输入终端类型',
										allowBlank : false
									}), new Ext.form.DateField({
										fieldLabel : '安装日期',
										format : 'Y-m-d',
										name : 'reodev.instdate',
										emptyText : '请输入安装日期',
										readonly : true,
										allowBlank : false
									}), new Ext.form.TextField({
										fieldLabel : '安装楼号',
										name : 'reodev.houseno',
										emptyText : '请输入安装楼号',
										allowBlank : false
									}), new Ext.form.TextField({
										fieldLabel : '安装门洞',
										name : 'reodev.doorno',
										emptyText : '请输入安装门洞',
										allowBlank : false
									}), new Ext.form.TextField({
										hideMode : 'visibility',// 'offsets',
										hideLabel : true,
										hidden : true,
										name : 'remark'
									})]
				});

		var addReoDevForm = new Ext.form.FormPanel({
					region : 'center',
					border : false,
					bodyStyle : 'padding: 10px 0px 0px 10px',
					layout : 'column',
					autoScroll : true,
					items : [reoForm1, reoForm2]
				});

		// 新增窗口
		var addwin = new Ext.Window({
			width : 700,
			height : 290,
			modal : true,
			title : '新增采集器',
			items : [addReoDevForm],
			buttons : [{
				text : '提交',
				handler : function() {
					var form = addReoDevForm.getForm();
					if (!form.isValid()) {
						Ext.Msg.alert('提示', '必填选项不能为空！');
						return;
					}
					form.submit({// 提交FORM表单
						waitMsg : "正在提交数据……",
						waitTitle : "提示",
						method : "post",//
						url : './runman/archivercpman!saveREODev.action',
						success : function(response, action) {
							var result = Ext
									.decode(action.response.responseText);
							if (result.success == true) {
								ReoDevStore.load({
											params : {
												cpNo : cpno
											}
										});
								addwin.close();
							} else {
								Ext.Msg.alert("警告", "增加采集器失败！请检查数据库连接或与管理员联系！");
							}
						}
					});
				}
			}, {
				text : '取消',
				handler : function() {
					addwin.close();
				}
			}],
			buttonAlign : 'center'
		});
		addwin.show();
	};

	// 修改采集器
	var updataReoDev = function(rowrecord) {
		var cpno = rowrecord.get("cpno");
		// 采集器运行状态
		var ReoDevStatusCodeCombox = new Ext.ux.ComboBox({
			sql : 'sql=SELECT STATUS_CODE, STATUS_NAME FROM VW_TMNL_STATUS_RUN',
			displayField : 'STATUS_NAME',
			valueField : 'STATUS_CODE',
			editable : false,
			name : 'statuscode',
			hiddenName : 'statuscode',
			fieldLabel : '采集器运行状态',
			forceSelection : true,
			triggerAction : 'all',
			emptyText : '--请选择--',
			blankText : '--请选择--',
			selectOnFocus : true,
			anchor : '100%',
			allowBlank : false
		});

		var reoForm1 = new Ext.Panel({
			layout : 'form',
			columnWidth : 0.4,
			border : false,
			defaults : {
				anchor : '95%'
			},
			labelAlign : 'right',
			items : [new Ext.form.TextField({
								fieldLabel : '资产标识',
								name : 'collectorid',
								emptyText : '请输入资产（采集器标识）,新增可以为空'
							}), new Ext.form.TextField({
								fieldLabel : '上级ID',
								name : 'preid',
								value : '11111111',
								emptyText : '请输入上级ID',
								hideLabel : true,
								hidden : true,
								allowBlank : false
							}), new Ext.form.TextField({
								fieldLabel : '采集点编号',
								value : cpno,
								name : 'cpno',
								emptyText : '请输入采集点编号',
								readOnly : true,
								allowBlank : false
							}), new Ext.form.TextField({
								fieldLabel : '型号',
								name : 'devmode',
								emptyText : '请输入型号',
								allowBlank : false
							}), ReoDevStatusCodeCombox,
					new Ext.form.TextField({
								fieldLabel : '安装地址',
								name : 'installloc',
								emptyText : '请输入安装地址',
								allowBlank : false
							}), new Ext.form.TextField({
								fieldLabel : '安装楼层',
								name : 'floorno',
								emptyText : '请输入安装楼层',
								allowBlank : false
							}), new Ext.form.TextField({
						fieldLabel : 'IP地址',
						name : 'ipaddr',
						validator : function(str) {
							if (str == null || str == '')
								return true;
							var time = str
									.match(/^(((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))\.){3}((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))$/);
							if (time != null)
								return true;
							else
								return false;
						},
						invalidText : '请输入有效的ip地址',
						emptyText : '请输入IP地址',
						allowBlank : false
					}), new Ext.form.TextField({
								fieldLabel : 'IP端口号',
								name : 'ipport',
								emptyText : '请输入IP端口号',
								allowBlank : false
							})]
		});
		var reoForm2 = new Ext.Panel({
					layout : 'form',
					columnWidth : 0.4,
					border : false,
					defaults : {
						anchor : '95%'
					},
					labelAlign : 'right',
					items : [new Ext.form.TextField({
										fieldLabel : '通讯地址',
										name : 'commaddr',
										emptyText : '请输入通讯地址',
										allowBlank : false
									}), new Ext.form.TextField({
										fieldLabel : '通信方式',
										name : 'collmode',
										emptyText : '请输入通信方式',
										allowBlank : false
									}), new Ext.form.TextField({
										fieldLabel : '端口号',
										name : 'portno',
										emptyText : '请输入端口号',
										allowBlank : false
									}), new Ext.form.TextField({
										fieldLabel : '终端类型',
										name : 'devtypecode',
										emptyText : '请输入终端类型',
										allowBlank : false
									}), new Ext.form.DateField({
										fieldLabel : '安装日期',
										format : 'Y-m-d',
										name : 'instdate',
										emptyText : '请输入安装日期',
										readonly : true,
										allowBlank : false
									}), new Ext.form.TextField({
										fieldLabel : '安装楼号',
										name : 'houseno',
										emptyText : '请输入安装楼号',
										allowBlank : false
									}), new Ext.form.TextField({
										fieldLabel : '安装门洞',
										name : 'doorno',
										emptyText : '请输入安装门洞',
										allowBlank : false
									}), new Ext.form.TextField({
										hideMode : 'visibility',// 'offsets',
										hideLabel : true,
										hidden : true,
										name : 'remark'
									})]
				});

		var updateReoDevForm = new Ext.form.FormPanel({
					region : 'center',
					border : false,
					bodyStyle : 'padding: 10px 0px 0px 10px',
					layout : 'column',
					autoScroll : true,
					items : [reoForm1, reoForm2]
				});

		updateReoDevForm.getForm().loadRecord(rowrecord);

		// 修改窗口
		var updatewin = new Ext.Window({
			width : 700,
			height : 290,
			modal : true,
			title : '修改采集器',
			items : [updateReoDevForm],
			buttons : [{
				text : '提交',
				handler : function() {
					var form = updateReoDevForm.getForm();
					if (!form.isValid()) {
						Ext.Msg.alert('提示', '必填选项不能为空！');
						return;
					}
					Ext.Ajax.request({
						url : './runman/archivercpman!updateREODev.action',
						success : function(response) {
							var result = Ext.decode(response.responseText);
							if (result.success == true) {
								ReoDevStore.load({
											params : {
												cpNo : cpno
											}
										});
								updatewin.close();
							} else {
								Ext.Msg.alert("警告", "修改采集器失败！请检查数据库连接或与管理员联系！");
							}
						},
						params : {
							reodevstr : Ext.encode(form.getValues(false))
						}
					});

				}
			}, {
				text : '取消',
				handler : function() {
					updatewin.close();
				}
			}],
			buttonAlign : 'center'
		});
		updatewin.show();
	};

	// ----------------------------------------采集器结束标识----------------------------------------------------//

	var cpParamMainPanel = new Ext.Panel({
		region : 'center',
		layout : 'border',
		items : [cpParamGrid, ReoDevGrid]
			// cpParamForm]
		});

	var cpManPanel = new Ext.Panel({
				// title : '采集点设置',
				layout : 'border',
				items : [cpEditGrid, cpParamMainPanel]

			});

	var loadRcp = function() {
		Ext.Ajax.request({
					url : './runman/archivercpman!queryRcp.action',
					params : {
						consId : consForm.getForm().findField('consId')
								.getValue()
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						rcpStore.loadData(result);
						cpParamStore.loadData(result);
					}
				});
	};

	var loadTmnl = function() {
		tmnlStore.load({
					params : {
						consNo : consForm.getForm().findField("consNo")
								.getValue()
					}
				});
	}

	var tmnlRecord = new Ext.data.Record.create([{
				name : 'terminalId',
				mapping : 'terminalId'
			}, {
				name : 'cpNo',
				mapping : 'cpNo'
			}, {
				name : 'tmnlAssetNo',
				mapping : 'tmnlAssetNo'
			}, {
				name : 'terminalAddr',
				mapping : 'terminalAddr'
			}, {
				name : 'cisAssetNo',
				mapping : 'cisAssetNo'
			}, {
				name : 'simNo',
				mapping : 'simNo'
			}, {
				name : 'id',
				mapping : 'id'
			}, {
				name : 'factoryCode',
				mapping : 'factoryCode'
			}, {
				name : 'attachMeterFlag',
				mapping : 'attachMeterFlag'
			}, {
				name : 'terminalTypeCode',
				mapping : 'terminalTypeCode'
			}, {
				name : 'collMode',
				mapping : 'collMode'
			}, {
				name : 'protocolCode',
				mapping : 'protocolCode'
			}, {
				name : 'commPassword',
				mapping : 'commPassword'
			}, {
				name : 'runDate',
				mapping : 'runDate'
			}, {
				name : 'statusCode',
				mapping : 'statusCode'
			}, {
				name : 'harmonicDevFlag',
				mapping : 'harmonicDevFlag'
			}, {
				name : 'psEnsureFlag',
				mapping : 'psEnsureFlag'
			}, {
				name : 'acSamplingFlag',
				mapping : 'acSamplingFlag'
			}, {
				name : 'eliminateFlag',
				mapping : 'eliminateFlag'
			}, {
				name : 'gateAttrFlag',
				mapping : 'gateAttrFlag'
			}, {
				name : 'prioPsMode',
				mapping : 'prioPsMode'
			}, {
				name : 'freezeMode',
				mapping : 'freezeMode'
			}, {
				name : 'freezeCycleNum',
				mapping : 'freezeCycleNum'
			}, {
				name : 'maxLoadCurveDays',
				mapping : 'maxLoadCurveDays'
			}, {
				name : 'psLineLen',
				mapping : 'psLineLen'
			}, {
				name : 'workPs',
				mapping : 'workPs'
			}, {
				name : 'speakerFlag',
				mapping : 'speakerFlag'
			}, {
				name : 'speakerDist',
				mapping : 'speakerDist'
			}, {
				name : 'sendUpMode',
				mapping : 'sendUpMode'
			}, {
				name : 'commMode',
				mapping : 'commMode'
			}, {
				name : 'frameNumber',
				mapping : 'frameNumber'
			}, {
				name : 'powerCutDate',
				mapping : 'powerCutDate'
			}]);

	// 终端维护
	var tmnlStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './runman/archivetmnlman!queryTmnls.action'
						}),
				// proxy : new Ext.data.MemoryProxy(data),
				reader : new Ext.data.JsonReader({
							root : 'list'
						}, [{
									name : 'terminalId'
								}, {
									name : 'cpNo'
								}, {
									name : 'tmnlAssetNo'
								}, {
									name : 'terminalAddr'
								}, {
									name : 'cisAssetNo'
								}, {
									name : 'simNo'
								}, {
									name : 'id'
								}, {
									name : 'factoryCode'
								}, {
									name : 'attachMeterFlag'
								}, {
									name : 'terminalTypeCode'
								}, {
									name : 'collMode'
								}, {
									name : 'protocolCode'
								}, {
									name : 'commPassword'
								}, {
									name : 'runDate'
								}, {
									name : 'statusCode'
								}, {
									name : 'harmonicDevFlag'
								}, {
									name : 'psEnsureFlag'
								}, {
									name : 'acSamplingFlag'
								}, {
									name : 'eliminateFlag'
								}, {
									name : 'gateAttrFlag'
								}, {
									name : 'prioPsMode'
								}, {
									name : 'freezeMode'
								}, {
									name : 'freezeCycleNum'
								}, {
									name : 'maxLoadCurveDays'
								}, {
									name : 'psLineLen'
								}, {
									name : 'workPs'
								}, {
									name : 'speakerFlag'
								}, {
									name : 'speakerDist'
								}, {
									name : 'sendUpMode'
								}, {
									name : 'commMode'
								}, {
									name : 'frameNumber'
								}, {
									name : 'powerCutDate'
								}])
			});
	tmnlStore.on('load', function() {
				tmnlGrid.getSelectionModel().selectFirstRow();
			});
	var tmnlColumnModel = new Ext.grid.ColumnModel([{
				header : '终端编号',
				dataIndex : 'terminalId',
				renderer : function(val) {
					if (Ext.isEmpty(val)) {
						return '<font color="ff0000"> 自动生成</font>';
					}
					return val;
				}
			}, {
				header : '采集点编号',
				dataIndex : 'cpNo'
			}, {
				header : '终端资产编号',
				dataIndex : 'tmnlAssetNo'
			}, {
				header : '终端地址码',
				dataIndex : 'terminalAddr'
			}, {
				header : '营销资产编号',
				dataIndex : 'cisAssetNo'
			}, {
				header : 'SIM卡号',
				dataIndex : 'simNo'
			}, {
				header : '终端型号',
				dataIndex : 'id'
			}, {
				header : '生产厂家',
				dataIndex : 'factoryCode'
			}, {
				header : '是否附属于电能表',
				dataIndex : 'attachMeterFlag'
			}, {
				header : '终端类型',
				dataIndex : 'terminalTypeCode'
			}, {
				header : '采集方式',
				dataIndex : 'collMode'
			}, {
				header : '通信规约类型',
				dataIndex : 'protocolCode'
			}, {
				header : '通信密码',
				dataIndex : 'commPassword'
			}, {
				header : '投运日期',
				dataIndex : 'runDate'
			}, {
				header : '终端运行状态',
				dataIndex : 'statusCode'
			}, {
				header : '是否接谐波装置',
				dataIndex : 'harmonicDevFlag'
			}, {
				header : '是否保电',
				dataIndex : 'psEnsureFlag'
			}, {
				header : '是否交流采样',
				dataIndex : 'acSamplingFlag'
			}, {
				header : '是否剔除',
				dataIndex : 'eliminateFlag'
			}, {
				header : '门接点属性',
				dataIndex : 'gateAttrFlag'
			}, {
				header : '优先供电方式',
				dataIndex : 'prioPsMode'
			}, {
				header : '冻结方式',
				dataIndex : 'freezeMode'
			}, {
				header : '冻结周期数',
				dataIndex : 'freezeCycleNum'
			}, {
				header : '负荷曲线最大天数',
				dataIndex : 'maxLoadCurveDays'
			}, {
				header : '电源线长度',
				dataIndex : 'psLineLen'
			}, {
				header : '工作电源',
				dataIndex : 'workPs'
			}, {
				header : '外接扬声器标志',
				dataIndex : 'speakerFlag'
			}, {
				header : '外接扬声器距离',
				dataIndex : 'speakerDist'
			}, {
				header : '任务上送方式',
				dataIndex : 'sendUpMode'
			}, {
				header : '终端实际通信方式',
				dataIndex : 'commMode'
			}, {
				header : '终端帧序号',
				dataIndex : 'frameNumber'
			}, {
				header : '终端停电时间',
				dataIndex : 'powerCutDate'
			}]);

	// 终端维护GridPanel
	var tmnlGrid = new Ext.grid.GridPanel({
				region : 'center',
				viewConfig : {
					forceFit : false
				},
				autoScroll : true,
				sm : new Ext.grid.RowSelectionModel(),
				cm : tmnlColumnModel,
				ds : tmnlStore,
				tbar : ['终端维护', '->', {
							text : '增加终端',
							handler : function() {

								if (!tmnlForm.getForm().isValid()) {
									Ext.Msg.alert('提示', '请完整输入终端信息的必填选项');
									return true;
								}

								tmnlStore.add(new tmnlRecord({

											'terminalId' : '',
											'cpNo' : '',
											'tmnlAssetNo' : '',
											'terminalAddr' : '',
											'cisAssetNo' : '',
											'simNo' : '',
											'id' : '',
											'factoryCode' : '',
											'attachMeterFlag' : '',
											'terminalTypeCode' : '',
											'collMode' : '',
											'protocolCode' : '',
											'commPassword' : '',
											'runDate' : '',
											'statusCode' : '',
											'harmonicDevFlag' : '',
											'psEnsureFlag' : '',
											'acSamplingFlag' : '',
											'eliminateFlag' : '',
											'gateAttrFlag' : '',
											'prioPsMode' : '',
											'freezeMode' : '',
											'freezeCycleNum' : '',
											'maxLoadCurveDays' : '',
											'psLineLen' : '',
											'workPs' : '',
											'speakerFlag' : '',
											'speakerDist' : '',
											'sendUpMode' : '',
											'commMode' : '',
											'frameNumber' : '',
											'powerCutDate' : new Date()
										}));
								tmnlGrid.getSelectionModel().selectLastRow();
							}
						}, {
							text : '删除选定行',
							handler : function() {
								deleteSelectTmnl();
							}
						}, {
							text : '重新载入',
							handler : function() {
								loadTmnl();
							}
						}]
			});

	// 删除选定终端
	var deleteSelectTmnl = function() {
		var selectRow = tmnlGrid.getSelectionModel().selections.items;

		if (selectRow.length <= 0) {
			Ext.Msg.alert("提示", "请选择要进行删除的采集点行！");
			return;
		} else {
			var tmnladdr = selectRow[0].get("terminalAddr");
			if (tmnladdr == null || tmnladdr == "") {
				Ext.Msg.alert("提示", "此行数据并非入库");
				return;
			} else {
				Ext.Ajax.request({
							url : './runman/archivetmnlman!deleteTmnl.action',
							success : function(response) {
								var result = Ext.decode(response.responseText);
								if (result.success == true) {
									Ext.Msg.alert("提示", "成功删除终端[" + tmnladdr
													+ "]信息！");
									loadTmnl();
								} else {
									Ext.Msg.alert("警告", "删除除终端[" + tmnladdr
													+ "]失败！");
								}
							},
							params : {
								tmnladdr : tmnladdr
							}
						});
			}
		}
	};

	var tmnlParaStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './runman/archivetmnlman!queryTmnlParams.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'paramlist'
						}, [{
									name : 'commParaId'
								}, {
									name : 'cpNo'
								}, {
									name : 'terminalAddr'
								}, {
									name : 'protocolCode'
								}, {
									name : 'channelNo'
								}, {
									name : 'rtsOn'
								}, {
									name : 'rtsOff'
								}, {
									name : 'transmitDelay'
								}, {
									name : 'respTimeout'
								}, {
									name : 'masterIp'
								}, {
									name : 'masterPort'
								}, {
									name : 'spareIpAddr'
								}, {
									name : 'sparePort'
								}, {
									name : 'gatewayIp'
								}, {
									name : 'gatewayPort'
								}, {
									name : 'proxyIpAddr'
								}, {
									name : 'proxyPort'
								}, {
									name : 'gprsCode'
								}, {
									name : 'smsNo'
								}, {
									name : 'apn'
								}, {
									name : 'heartbeatCycle'
								}, {
									name : 'startDate',
									type : 'date'
								}, {
									name : 'algNo'
								}, {
									name : 'algKey'
								}])
			});

	function findParams() {
		tmnlTabPanel.setActiveTab(cpParamForm);
		var selectRow = tmnlGrid.getSelectionModel().selections.items;
		var terminaladdr = selectRow[0].get("terminalAddr");
		var cpNo = selectRow[0].get("cpNo");
		tmnlParaStore.load({
					params : {
						taddr : terminaladdr
					},
					callback : function(r, options, success) {
						if (success && r.length > 0) {
							cpParamForm.getForm().loadRecord(r[0]);
						} else {
							cpParamForm.getForm().reset();
							// cpParamForm.getForm().findField('terminalAddr').setValue(terminaladdr);
							cpParamForm.getForm().findField('cpNo')
									.setValue(cpNo);
						}
					}
				});
		tmnlTabPanel.setActiveTab(tmnlForm);
	};

	var tmnlRowSelect = function(sm, num, rec) {

		if (null == rec.get('terminalId') || rec.get('terminalId').trim() == "") {
			cpParamForm.disable();
		} else {
			cpParamForm.enable();
		}
		if (!Ext.isEmpty(rec)) {
			tmnlForm.getForm().setValues(rec.data);
			findParams();
		}
	};
	tmnlGrid.getSelectionModel().on('rowselect', tmnlRowSelect);

	// 在切换form时将form内容保存到 tmnlParamStore
	// 保存前还要将当前form的内容保存到tmnlParamStore 取得选中行并取值设置
	var tmnlRowDeselect = function(sm, row, rec) {
		if (!tmnlForm.getForm().isValid()) {
			Ext.Msg.alert('提示', '请完整输入终端信息的必填选项');
		}

		var fmval = tmnlForm.getForm().getFieldValues();
		for (var o in fmval) {
			rec.set(o, fmval[o]);
		}
		rec.commit();

	}

	tmnlGrid.getSelectionModel().on('rowdeselect', tmnlRowDeselect);

	/* 终端类型 */
	// var terminalTypeCodeData = [['1', '负荷管理终端'], ['2', '客户电能量终端'],
	// ['3', '配变终端'], ['4', '配变监控终端'], ['5', '低压集中器'], ['6', '低压采集终端'],
	// ['7', '采集模块']];
	//
	// var terminalTypeCodeStore = new Ext.data.SimpleStore({
	// fields : ['terminalTypeCodevalue', 'terminalTypeCodetext'],
	// data : terminalTypeCodeData
	// });
	var terminalTypeCodeCombox = new Ext.ux.ComboBox({
				sql : 'sql=SELECT TMNL_TYPE_CODE, TMNL_TYPE FROM VW_TMNL_TYPE_CODE',
				displayField : 'TMNL_TYPE',
				valueField : 'TMNL_TYPE_CODE',
				editable : false,
				name : 'terminalTypeCode',
				hiddenName : 'terminalTypeCode',
				fieldLabel : '终端类型',
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--',
				selectOnFocus : true,
				anchor : '100%',
				allowBlank : false
			});
	/* 采集方式 */
	// var collModeCodeData = [['1', '230MHz'], ['2', 'GPRS'], ['3', 'CDMA'],
	// ['4', '电话网'], ['5', '电力线'], ['6', '微功率电台'], ['7', '光纤'],
	// ['8', '其它']];
	//
	// var collModeCodeStore = new Ext.data.SimpleStore({
	// fields : ['collModeCodevalue', 'collModeCodetext'],
	// data : collModeCodeData
	// });
	var collModeCodeCombox = new Ext.ux.ComboBox({
		sql : "sql=SELECT VALUE, NAME FROM P_CODE WHERE CODE_TYPE='cpTypeCode'",
		displayField : 'NAME',
		valueField : 'VALUE',
		editable : false,
		name : 'collMode',
		hiddenName : 'collMode',
		fieldLabel : '采集方式',
		forceSelection : true,
		triggerAction : 'all',
		emptyText : '--请选择--',
		blankText : '--请选择--',
		selectOnFocus : true,
		anchor : '100%',
		allowBlank : false
	});

	/* 终端运行状态 */
	// var statusCodeData = [['1', '待装'], ['2', '已装未投运'], ['3', '运行'],
	// ['4', '停运'], ['5', '待拆'], ['6', '已拆']];
	//
	// var statusCodeStore = new Ext.data.SimpleStore({
	// fields : ['statusCodevalue', 'statusCodetext'],
	// data : statusCodeData
	// });
	var tmnlStatusCodeCombox = new Ext.ux.ComboBox({
				sql : 'sql=SELECT STATUS_CODE, STATUS_NAME FROM VW_TMNL_STATUS_RUN',
				displayField : 'STATUS_NAME',
				valueField : 'STATUS_CODE',
				editable : false,
				name : 'statusCode',
				hiddenName : 'statusCode',
				fieldLabel : '终端运行状态',
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--',
				selectOnFocus : true,
				anchor : '100%',
				allowBlank : false
			});
	/* 优先供电方式 */
	// var prioPsModeData = [['11', '单路公用线'], ['12', '单路专线'],
	// ['21', '双路电源，一主供（公用线）一备用（公用线）'], ['22', '双路电源，一主供（专线）一备用（公用线）'],
	// ['23', '双路电源，一主供（专线）一备用（专线）'], ['24', '双路电源，独立常供、低压切换'],
	// ['25', '双路电源，独立常供、不切换（公用线）'], ['26', '双路电源，独立常供、不切换（专线）'],
	// ['31', '两路以上电源供电、互为备用（公用线）'], ['32', '两路以上电源供电、互为备用（专线）']];
	//
	// var prioPsModeStore = new Ext.data.SimpleStore({
	// fields : ['prioPsModevalue', 'prioPsModetext'],
	// data : prioPsModeData
	// });
	var prioPsModeCombox = new Ext.ux.ComboBox({
				sql : 'sql=SELECT PS_MODE_NO, PS_MODE_NAME FROM VW_PS_MODE',
				displayField : 'PS_MODE_NAME',
				valueField : 'PS_MODE_NO',
				editable : false,
				name : 'prioPsMode',
				hiddenName : 'prioPsMode',
				minListWidth : 300,
				fieldLabel : '优先供电方式',
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--',
				selectOnFocus : true,
				anchor : '100%'
			});
	/* 冻结方式 */
	// var freezeModeData = [['01', '定时冻结'], ['02', '立即冻结'],
	// ['03', '出、入冬与出、入夏电量冻结']];
	// var freezeModeStore = new Ext.data.SimpleStore({
	// fields : ['freezeModevalue', 'freezeModetext'],
	// data : freezeModeData
	// });
	var freezeModeCombox = new Ext.ux.ComboBox({
		sql : "sql=SELECT VALUE, NAME FROM P_CODE WHERE CODE_TYPE = 'freezeMode'",
		displayField : 'NAME',
		valueField : 'VALUE',
		editable : false,
		name : 'freezeMode',
		hiddenName : 'freezeMode',
		fieldLabel : '冻结方式',
		forceSelection : true,
		triggerAction : 'all',
		minListWidth : 300,
		emptyText : '--请选择--',
		blankText : '--请选择--',
		selectOnFocus : true,
		anchor : '100%'
	});

	// var factSql = "sql=SELECT FACTORY_CODE FACTORYCODE, FACTORY_NAME
	// FACTORYNAME FROM VW_TMNL_FACTORY ";
	// var factUrl = Ext.urlAppend(actionurl, factSql);
	// var factStore = new Ext.data.JsonStore({
	// url : factUrl,
	// fields : ['FACTORYCODE', 'FACTORYNAME'],
	// root : "dicList"
	// });

	var subfactCombox = new Ext.ux.ComboBox({
		sql : 'sql=SELECT FACTORY_CODE FACTORYCODE,  FACTORY_NAME FACTORYNAME  FROM VW_TMNL_FACTORY ',
		width : 300,
		hideOnSelect : false,
		displayField : 'FACTORYNAME',
		valueField : 'FACTORYCODE',
		hiddenName : 'factoryCode',
		maxHeight : 200,
		mode : 'remote',
		fieldLabel : '生产厂家',
		triggerAction : 'all',
		emptyText : '--请选择生产厂家--',
		blankText : '--请选择生产厂家--',
		allowBlank : false

	});

	var rcpStore1 = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './runman/archivercpman!queryRcp.action'
						}),
				// proxy : new Ext.data.MemoryProxy(data),
				reader : new Ext.data.JsonReader({
							root : 'rcpList'
						}, [{
									name : 'cpNo'
								}, {
									name : 'name'
								}])
			});

	rcpStore1.on('beforeload', function(s) {
				s.baseParams = {
					consId : consForm.getForm().findField('consId').getValue()
				};

			});
	rcpStore1.load();
	var subCpCombox = new Ext.form.ComboBox({
				width : 300,
				hideOnSelect : false,
				displayField : 'name',
				valueField : 'cpNo',
				hiddenName : 'cpNo',
				maxHeight : 200,
				store : rcpStore1,
				mode : 'remote',
				fieldLabel : '采集点',
				triggerAction : 'all',
				emptyText : '--请选择采集点--',
				blankText : '--请选择采集点--'

			});
	/* 任务上送方式 */
	var sendUpModeData = [['0', '主站主动召测'], ['1', '终端自动上送']];
	var sendUpModeStore = new Ext.data.SimpleStore({
				fields : ['sendUpModevalue', 'sendUpModetext'],
				data : sendUpModeData
			});
	var sendUpModeCombox = new Ext.form.ComboBox({
				store : sendUpModeStore,
				displayField : 'sendUpModetext',
				valueField : 'sendUpModevalue',
				editable : false,
				name : 'sendUpMode',
				hiddenName : 'sendUpMode',
				mode : 'local',
				fieldLabel : '任务上送方式',
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--',
				selectOnFocus : true,
				anchor : '100%'
			});
	var tmnlForm1 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.33,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [subCpCombox, new Ext.form.TextField({
									name : 'tmnlAssetNo',
									emptyText : '请输入终端资产编号',
									fieldLabel : '终端资产编号',
									maxLength : 32,
									allowBlank : false
								}), new Ext.form.TextField({
									name : 'terminalAddr',
									maxLength : 32,
									emptyText : '请输入终端地址码',
									fieldLabel : '终端地址码',
									allowBlank : false
								}), new Ext.form.TextField({
									name : 'cisAssetNo',
									emptyText : '请输入营销资产编号',
									maxLength : 32,
									fieldLabel : '营销资产编号',
									allowBlank : false
								}), new Ext.form.TextField({
									name : 'simNo',
									emptyText : '请输入SIM卡号 ',
									maxLength : 32,
									fieldLabel : 'SIM卡号'
								}), new Ext.form.TextField({
									name : 'id',
									emptyText : '请输入终端型号 ',
									maxLength : 8,
									fieldLabel : '终端型号',
									allowBlank : false
								}), subfactCombox, terminalTypeCodeCombox,
						collModeCodeCombox, prioPsModeCombox, sendUpModeCombox,
						new Ext.form.TextField({
									name : 'terminalId',
									emptyText : '请输入终端编号',
									fieldLabel : '终端编号',
									hidden : true,
									hideLabel : true
								})]
			});

	var protocolComStore1 = new Ext.data.JsonStore({
				url : "./sysman/generaltree!protocolList.action",
				fields : ['protocolCode', 'protocolName'],
				root : "protocolList"
			});

	var protocolCombox1 = new Ext.form.ComboBox({
				store : protocolComStore1,
				displayField : 'protocolName',
				valueField : 'protocolCode',
				typeAhead : true,
				mode : 'remote',
				fieldLabel : '规约类型',
				name : 'protocolCode',
				triggerAction : 'all',
				emptyText : '--请选择规约类型--',
				selectOnFocus : true
			});

	var tmnlForm2 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.33,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [protocolCombox1, new Ext.form.TextField({
									name : 'commPassword',
									emptyText : '请输入通信密码',
									maxLength : 16,
									fieldLabel : '通信密码'
								}), new Ext.form.DateField({
									name : 'runDate',
									format : 'Y-m-d',
									emptyText : '请输入投运日期',
									fieldLabel : '投运日期',
									allowBlank : false
								}), tmnlStatusCodeCombox,
						new Ext.ux.RadioGroup({
									fieldLabel : '是否接谐波装置',
									name : 'harmonicDevFlag',
									items : [{
												boxLabel : '是',
												name : 'harmonicDevFlag',
												inputValue : 1,
												checked : true
											}, {
												boxLabel : '否',
												name : 'harmonicDevFlag',
												inputValue : 0
											}]
								}), new Ext.ux.RadioGroup({
									fieldLabel : '是否保电',
									name : 'psEnsureFlag',
									items : [{
												boxLabel : '是',
												name : 'psEnsureFlag',
												inputValue : 1,
												checked : true
											}, {
												boxLabel : '否',
												name : 'psEnsureFlag',
												inputValue : 0
											}]
								}), new Ext.ux.RadioGroup({
									fieldLabel : '是否交流采样',
									name : 'acSamplingFlag',
									items : [{
												boxLabel : '是',
												name : 'acSamplingFlag',
												inputValue : 1,
												checked : true
											}, {
												boxLabel : '否',
												name : 'acSamplingFlag',
												inputValue : 0
											}]
								}), new Ext.ux.RadioGroup({
									fieldLabel : '是否剔除',
									name : 'eliminateFlag',
									width : 115,
									items : [{
												boxLabel : '是',
												name : 'eliminateFlag',
												inputValue : 1,
												checked : true
											}, {
												boxLabel : '否',
												name : 'eliminateFlag',
												inputValue : 2
											}]
								}), new Ext.ux.RadioGroup({
									fieldLabel : '门接点属性',
									name : 'gateAttrFlag',
									items : [{
												boxLabel : '常开',
												name : 'gateAttrFlag',
												inputValue : 1,
												checked : true
											}, {
												boxLabel : '常闭',
												name : 'gateAttrFlag',
												inputValue : 2
											}]
								}), new Ext.ux.RadioGroup({
									fieldLabel : '是否附属于电能表',
									name : 'attachMeterFlag',
									items : [{
												boxLabel : '是',
												name : 'attachMeterFlag',
												inputValue : 1,
												checked : true
											}, {
												boxLabel : '否',
												name : 'attachMeterFlag',
												inputValue : 0
											}]
								})]
			});

	var tmnlForm3 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.33,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [freezeModeCombox, new Ext.form.TextField({
									name : 'freezeCycleNum',
									emptyText : '请输入冻结周期数',
									maxLength : 5,
									fieldLabel : '冻结周期数'
								}), new Ext.form.TextField({
									name : 'maxLoadCurveDays',
									emptyText : '请输入负荷曲线最大天数',
									maxLength : 5,
									fieldLabel : '负荷曲线最大天数'
								}), new Ext.form.TextField({
									name : 'psLineLen',
									emptyText : '请输入电源线长度',
									maxLength : 5,
									fieldLabel : '电源线长度'
								}), new Ext.form.TextField({
									name : 'workPs',
									emptyText : '请输入工作电源来源',
									maxLength : 256,
									fieldLabel : '工作电源'
								}), new Ext.form.TextField({
									name : 'speakerFlag',
									emptyText : '请选择 ',
									maxLength : 8,
									fieldLabel : '外接扬声器标志'
								}), new Ext.form.TextField({
									name : 'speakerDist',
									emptyText : '请输入外接扬声器距离',
									maxLength : 5,
									fieldLabel : '外接扬声器距离'
								}), new Ext.form.TextField({
									name : 'commMode',
									emptyText : '请输入终端实际通信方式',
									maxLength : 8,
									fieldLabel : '终端实际通信方式'
								}), new Ext.form.TextField({
									name : 'frameNumber',
									emptyText : '请输入终端帧序号',
									maxLength : 4,
									fieldLabel : '终端帧序号'
								}), new Ext.form.DateField({
									name : 'powerCutDate',
									format : 'Y-m-d',
									emptyText : '请输入',
									fieldLabel : '终端停电时间',
									width : 127
								})]
			});

	var tmnlForm = new Ext.FormPanel({
				// region : 'south',
				height : 260,
				border : false,
				bodyStyle : 'padding: 10px 0px 0px 10px',
				title : '终端详细信息维护',
				layout : 'column',
				autoScroll : true,
				items : [tmnlForm1, tmnlForm2, tmnlForm3]
			});

	var tmnlTabPanel = new Ext.TabPanel({// 终端参数维护的TabPanel
		region : 'south',
		height : 260,
		border : false,
		bodyStyle : 'padding: 10px 0px 0px 10px',
		autoScroll : true,
		activeTab : 0,
		items : [tmnlForm, cpParamForm]
	});

	var tmnlPanel = new Ext.Panel({
				// title : '采集点设置',
				layout : 'border',
				split : true,
				items : [tmnlGrid, tmnlTabPanel]

			});

	var cmpRecord = new Ext.data.Record.create([{
				name : 'mpId',
				mapping : 'mpId'
			}, {
				name : 'mpNo',
				mapping : 'mpNo'
			}, {
				name : 'mpName',
				mapping : 'mpName'
			}, {
				name : 'orgNo',
				mapping : 'orgNo'
			}, {
				name : 'consId',
				mapping : 'consId'
			}, {
				name : 'consNo',
				mapping : 'consNo'
			}, {
				name : 'tgId',
				mapping : 'tgId'
			}, {
				name : 'lineId',
				mapping : 'lineId'
			}, {
				name : 'mrSectNo',
				mapping : 'mrSectNo'
			}, {
				name : 'mpAddr',
				mapping : 'mpAddr'
			}, {
				name : 'typeCode',
				mapping : 'typeCode'
			}, {
				name : 'mpAttrCode',
				mapping : 'mpAttrCode'
			}, {
				name : 'usageTypeCode',
				mapping : 'usageTypeCode'
			}, {
				name : 'sideCode',
				mapping : 'sideCode'
			}, {
				name : 'voltCode',
				mapping : 'voltCode'
			}, {
				name : 'appDate',
				mapping : 'appDate'
			}, {
				name : 'runDate',
				mapping : 'runDate'
			}, {
				name : 'wiringMode',
				mapping : 'wiringMode'
			}, {
				name : 'measMode',
				mapping : 'measMode'
			}, {
				name : 'switchNo',
				mapping : 'switchNo'
			}, {
				name : 'exchgTypeCode',
				mapping : 'exchgTypeCode'
			}, {
				name : 'mdTypeCode',
				mapping : 'mdTypeCode'
			}, {
				name : 'mrSn',
				mapping : 'mrSn'
			}, {
				name : 'mpSn',
				mapping : 'mpSn'
			}, {
				name : 'meterFlag',
				mapping : 'meterFlag'
			}, {
				name : 'statusCode',
				mapping : 'statusCode'
			}, {
				name : 'lcFlag',
				mapping : 'lcFlag'
			}, {
				name : 'earthMode',
				mapping : 'earthMode'
			}]);

	var cmpStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './runman/archivecmpman!queryCmp.action'
						}),
				// proxy : new Ext.data.MemoryProxy(data),
				reader : new Ext.data.JsonReader({
							root : 'cmpList'
						}, [{
									name : 'mpId'
								}, {
									name : 'mpNo'
								}, {
									name : 'mpName'
								}, {
									name : 'orgNo'
								}, {
									name : 'consId'
								}, {
									name : 'consNo'
								}, {
									name : 'tgId'
								}, {
									name : 'lineId'
								}, {
									name : 'mrSectNo'
								}, {
									name : 'mpAddr'
								}, {
									name : 'typeCode'
								}, {
									name : 'mpAttrCode'
								}, {
									name : 'usageTypeCode'
								}, {
									name : 'sideCode'
								}, {
									name : 'voltCode'
								}, {
									name : 'appDate'
								}, {
									name : 'runDate'
								}, {
									name : 'wiringMode'
								}, {
									name : 'measMode'
								}, {
									name : 'switchNo'
								}, {
									name : 'exchgTypeCode'
								}, {
									name : 'mdTypeCode'
								}, {
									name : 'mrSn'
								}, {
									name : 'mpSn'
								}, {
									name : 'meterFlag'
								}, {
									name : 'statusCode'
								}, {
									name : 'lcFlag'
								}, {
									name : 'earthMode'
								}])
			});
	cmpStore.on('load', function() {
				cmpGrid.getSelectionModel()
			});
	var cmpColumnModel = new Ext.grid.ColumnModel([{
				header : '计量点标识',
				dataIndex : 'mpId'
			}, {
				header : '计量点编号',
				dataIndex : 'mpNo'
			}, {
				header : '计量点名称',
				dataIndex : 'mpName'
			}, {
				header : '供电单位编号',
				dataIndex : 'orgNo'
			}, {
				header : '用户标识',
				dataIndex : 'consId'
			}, {
				header : '用户编号',
				dataIndex : 'consNo'
			}, {
				header : '台区标识',
				dataIndex : 'tgId'
			}, {
				header : '线路标识',
				dataIndex : 'lineId'
			}, {
				header : '抄表段编号',
				dataIndex : 'mrSectNo'
			}, {
				header : '计量点地址',
				dataIndex : 'mpAddr'
			}, {
				header : '计量点分类',
				dataIndex : 'typeCode'
			}, {
				header : '计量点性质',
				dataIndex : 'mpAttrCode'
			}, {
				header : '主用途类型',
				dataIndex : 'usageTypeCode'
			}, {
				header : '计量点所属侧',
				dataIndex : 'sideCode'
			}, {
				header : '电压等级',
				dataIndex : 'voltCode'
			}, {
				header : '申请日期',
				dataIndex : 'appDate'
			}, {
				header : '投运日期',
				dataIndex : 'runDate'
			}, {
				header : '接线方式',
				dataIndex : 'wiringMode'
			}, {
				header : '计量方式',
				dataIndex : 'measMode'
			}, {
				header : '开关编号',
				dataIndex : 'switchNo'
			}, {
				header : '电量交换点分类',
				dataIndex : 'exchgTypeCode'
			}, {
				header : '电能计量装置分类',
				dataIndex : 'mdTypeCode'
			}, {
				header : '抄表顺序号',
				dataIndex : 'mrSn'
			}, {
				header : '计量管理顺序号',
				dataIndex : 'mpSn'
			}, {
				header : '是否装表',
				dataIndex : 'meterFlag'
			}, {
				header : '计量点状态',
				dataIndex : 'statusCode'
			}, {
				header : '是否安装负控',
				dataIndex : 'lcFlag'
			}, {
				header : '中性点接地方式',
				dataIndex : 'earthMode'
			}]);

	var loadCmp = function() {
		cmpStore.load({
					params : {
						consNo : consForm.getForm().findField("consNo")
								.getValue()
					}
				});

	};

	var cmpGrid = new Ext.grid.GridPanel({
				region : 'center',
				viewConfig : {
					forceFit : false
				},
				autoScroll : true,
				cm : cmpColumnModel,
				ds : cmpStore,
				tbar : ['计量点维护', '->', {
					text : '增加计量点',
					handler : function() {

						if (!cmpForm.getForm().isValid()) {
							Ext.Msg.alert('提示', '请完整输入计量点参数必填选项');
							return true;
						}

						cmpStore.add(new cmpRecord({
									'mpId' : '',
									'mpNo' : '',
									'mpName' : '',
									'orgNo' : consForm.getForm()
											.findField('orgNo').getValue(),
									'consId' : consForm.getForm()
											.findField('consId').getValue(),
									'consNo' : consForm.getForm()
											.findField('consNo').getValue(),
									'tgId' : consForm.getForm()
											.findField('tgId').getValue(),
									'lineId' : consForm.getForm()
											.findField('lineId').getValue(),
									'mrSectNo' : '',
									'mpAddr' : '',
									'typeCode' : '',
									'mpAttrCode' : '',
									'usageTypeCode' : '',
									'sideCode' : '',
									'voltCode' : '',
									'appDate' : new Date(),
									'runDate' : new Date(),
									'wiringMode' : '',
									'measMode' : '',
									'switchNo' : '',
									'exchgTypeCode' : '',
									'mdTypeCode' : '',
									'mrSn' : '',
									'mpSn' : '',
									'meterFlag' : '',
									'statusCode' : '',
									'lcFlag' : '',
									'earthMode' : ''
								}));
						cmpGrid.getSelectionModel().selectLastRow();

					}
				}, {
					text : '删除最后一行',
					handler : function() {
						// var selectRow;
						// for (var i = 0; i <
						// cpEditGrid.getStore().getCount(); i++) {
						// if
						// (cpEditGrid.getSelectionModel().isSelected(i))
						// ;
						// {
						// selectRow = i;
						// break;
						// }
						// }
						// if (selectRow > -1) {
						// cpEditGrid.getStore().removeAt(selectRow);
						// }
						cmpGrid.getStore().removeAt(cmpGrid.getStore()
								.getCount()
								- 1);

					}
				}, {
					text : '重新载入',
					handler : function() {
						loadCmp();
					}
				}]
			});

	var cmpRowSelect = function(sm, num, rec) {

		if (!Ext.isEmpty(rec))
			cmpForm.getForm().setValues(rec.data);

	}
	cmpGrid.getSelectionModel().on('rowselect', cmpRowSelect);

	// 在切换form时将form内容保存到 tmnlParamStore
	// 保存前还要将当前form的内容保存到tmnlParamStore 取得选中行并取值设置
	var cmpRowDeselect = function(sm, row, rec) {

		if (!cmpForm.getForm().isValid()) {
			Ext.Msg.alert('提示', '请完整输入计量点参数必填选项');
		}

		var fmval = cmpForm.getForm().getFieldValues();
		for (var o in fmval) {
			rec.set(o, fmval[o]);
		}
		rec.commit();

	}

	cmpGrid.getSelectionModel().on('rowdeselect', cmpRowDeselect);

	var cmpForm1 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.33,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [new Ext.form.TextField({
									name : 'mpNo',
									emptyText : '请输入计量点编号',
									fieldLabel : '计量点编号',
									maxLength : 16,
									allowBlank : false
								}), new Ext.form.TextField({
									name : 'mpName',
									emptyText : '请输入计量点名称',
									fieldLabel : '计量点名称',
									maxLength : 256,
									allowBlank : false
								}), new Ext.form.TextField({
									name : 'orgNo',
									emptyText : '请输入供电单位编号',
									fieldLabel : '供电单位编号',
									readOnly : true,
									allowBlank : false
								}), new Ext.form.TextField({
									name : 'tgId',
									readOnly : true,
									emptyText : '请输入台区标识',
									fieldLabel : '台区标识'
								}), new Ext.form.TextField({
									name : 'lineId',
									readOnly : true,
									emptyText : '请输入线路标识',
									fieldLabel : '线路标识'
								}), new Ext.form.TextField({
									name : 'mrSectNo',
									maxLength : 16,
									emptyText : '请输入抄表段编号',
									fieldLabel : '抄表段编号'
								}), new Ext.form.TextField({
									name : 'mpAddr',
									maxLength : 256,
									emptyText : '请输入计量点地址',
									fieldLabel : '计量点地址',
									allowBlank : false
								}), new Ext.form.TextField({
									name : 'switchNo',
									maxLength : 32,
									emptyText : '请输入开关编号',
									fieldLabel : '开关编号'
								}), new Ext.form.TextField({
									name : 'consId',
									emptyText : '请输入用户标识',
									fieldLabel : '用户标识',
									hidden : true,
									hideLabel : true
								}), new Ext.form.TextField({
									name : 'consNo',
									emptyText : '请输入用户编号',
									fieldLabel : '用户编号',
									hidden : true,
									hideLabel : true
								}), new Ext.form.TextField({
									name : 'mpId',
									emptyText : '请输入计量点标识 ',
									fieldLabel : '计量点标识',
									hidden : true,
									hideLabel : true
								})]
			});
	/* 计量点分类 */
	// var typeCodeData = [['01', '用电客户'], ['02', '关口']];
	// var typeCodeStore = new Ext.data.SimpleStore({
	// fields : ['typeCodevalue', 'typeCodetext'],
	// data : typeCodeData
	// });
	var typeCodeCombox = new Ext.ux.ComboBox({
				sql : 'sql=SELECT TYPE_CODE, TYPE_NAME FROM VW_MP_TYPE_CODE',
				displayField : 'TYPE_NAME',
				valueField : 'TYPE_CODE',
				editable : false,
				name : 'typeCode',
				hiddenName : 'typeCode',
				fieldLabel : '计量点分类',
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--',
				selectOnFocus : true,
				anchor : '100%',
				allowBlank : false
			});
	/* 计量点性质 */
	// var mpAttrCodeData = [['01', '结算'], ['02', '考核']];
	// var mpAttrCodeStore = new Ext.data.SimpleStore({
	// fields : ['mpAttrCodevalue', 'mpAttrCodetext'],
	// data : mpAttrCodeData
	// });
	var mpAttrCodeCombox = new Ext.ux.ComboBox({
				sql : 'sql=SELECT MP_ATTR_CODE, MP_ATTR_NAME FROM VW_MP_ATTR',
				displayField : 'MP_ATTR_NAME',
				valueField : 'MP_ATTR_CODE',
				editable : false,
				name : 'mpAttrCode',
				hiddenName : 'mpAttrCode',
				fieldLabel : '计量点性质',
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--',
				selectOnFocus : true,
				anchor : '100%',
				allowBlank : false
			});
	/* 主用途类型 */
	// var usageTypeCodeData = [['01', '售电侧结算'], ['02', '台区供电考核'],
	// ['03', '线路供电考核'], ['04', '指标分析'], ['05', '趸售供电关口'],
	// ['06', '地市供电关口'], ['07', '省级供电关口'], ['08', '跨省输电关口'],
	// ['09', '跨区输电关口'], ['10', '跨国输电关口'], ['11', '发电上网关口']];
	// var usageTypeCodeStore = new Ext.data.SimpleStore({
	// fields : ['usageTypeCodevalue', 'usageTypeCodetext'],
	// data : usageTypeCodeData
	// });
	var usageTypeCodeCombox = new Ext.ux.ComboBox({
		sql : "sql=SELECT VALUE, NAME FROM P_CODE WHERE CODE_TYPE = 'usageTypeCode'",
		displayField : 'NAME',
		valueField : 'VALUE',
		editable : false,
		name : 'usageTypeCode',
		hiddenName : 'usageTypeCode',
		fieldLabel : '主用途类型',
		forceSelection : true,
		triggerAction : 'all',
		emptyText : '--请选择--',
		blankText : '--请选择--',
		selectOnFocus : true,
		anchor : '100%'
	});
	/* 计量点所属侧 */
	// var sideCodeData = [['01', '变电站内'], ['02', '变电站外'], ['03', '高压侧'],
	// ['04', '低压侧']];
	// var sideCodeStore = new Ext.data.SimpleStore({
	// fields : ['sideCodevalue', 'sideCodetext'],
	// data : sideCodeData
	// });
	var sideCodeCombox = new Ext.ux.ComboBox({
		sql : "sql=SELECT VALUE, NAME FROM P_CODE WHERE CODE_TYPE = 'sideCode'",
		displayField : 'NAME',
		valueField : 'VALUE',
		editable : false,
		name : 'sideCode',
		hiddenName : 'sideCode',
		fieldLabel : '计量点所属侧',
		forceSelection : true,
		triggerAction : 'all',
		emptyText : '--请选择--',
		blankText : '--请选择--',
		selectOnFocus : true,
		anchor : '100%'
	});
	/* 电压等级 */
	// var voltCodeData = [['01', '10KV'], ['02', '110KV'], ['03', '220KV'],
	// ['04', '35KV'], ['05', '220V'], ['06', '6KV'], ['07', '380V'],
	// ['08', '500KV']];
	// var voltCodeStore = new Ext.data.SimpleStore({
	// fields : ['voltCodevalue', 'voltCodetext'],
	// data : voltCodeData
	// });
	var voltCodeCombox = new Ext.ux.ComboBox({
				sql : 'sql=SELECT VOLT_CODE, VOLT FROM VW_VOLT_CODE',
				displayField : 'VOLT',
				valueField : 'VOLT_CODE',
				editable : false,
				name : 'voltCode',
				hiddenName : 'voltCode',
				fieldLabel : '电压等级',
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--',
				selectOnFocus : true,
				anchor : '100%'
			});
	/* 接线方式 */
	// var wiringModeData = [['1', '单相'], ['2', '三相三线'], ['3', '三相四线']];
	// var wiringModeStore = new Ext.data.SimpleStore({
	// fields : ['wiringModevalue', 'wiringModetext'],
	// data : wiringModeData
	// });
	var wiringModeCombox = new Ext.ux.ComboBox({
				sql : 'sql=SELECT WIRING_MODE, WIRING_MODE_NAME FROM VW_WIRING_MODE',
				displayField : 'WIRING_MODE_NAME',
				valueField : 'WIRING_MODE',
				editable : false,
				name : 'wiringMode',
				hiddenName : 'wiringMode',
				fieldLabel : '接线方式',
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--',
				selectOnFocus : true,
				anchor : '100%'
			});
	/* 计量方式 */
	// var measModeData = [['1', '高供高计'], ['2', '高供低计'], ['3', '低供低计']];
	// var measModeStore = new Ext.data.SimpleStore({
	// fields : ['measModevalue', 'measModetext'],
	// data : measModeData
	// });
	var measModeCombox = new Ext.ux.ComboBox({
				sql : 'sql=SELECT MEAS_NO, MEAS_MODE FROM VW_MEAS_MODE',
				displayField : 'MEAS_MODE',
				valueField : 'MEAS_NO',
				editable : false,
				name : 'measMode',
				hiddenName : 'measMode',
				fieldLabel : '计量方式',
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--',
				selectOnFocus : true,
				anchor : '100%'
			});
	var cmpForm2 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.33,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [{
							border : false,
							layout : 'form',
							colspan : 1,
							labelWidth : 100,
							width : 230,
							items : [typeCodeCombox]
						}, {
							border : false,
							layout : 'form',
							colspan : 1,
							labelWidth : 100,
							width : 230,
							items : [mpAttrCodeCombox]
						}, {
							border : false,
							layout : 'form',
							colspan : 1,
							labelWidth : 100,
							width : 230,
							items : [usageTypeCodeCombox]
						}, {
							border : false,
							layout : 'form',
							colspan : 1,
							labelWidth : 100,
							width : 230,
							items : [sideCodeCombox]
						}, {
							border : false,
							layout : 'form',
							colspan : 1,
							labelWidth : 100,
							width : 230,
							items : [voltCodeCombox]
						}, new Ext.form.DateField({
									name : 'appDate',
									format : 'Y-m-d',
									emptyText : '请输入申请日期',
									fieldLabel : '申请日期',
									width : 127
								}), new Ext.form.DateField({
									name : 'runDate',
									format : 'Y-m-d',
									emptyText : '请输入投运日期',
									fieldLabel : '投运日期',
									width : 127
								}), {
							border : false,
							layout : 'form',
							colspan : 1,
							labelWidth : 100,
							width : 230,
							items : [wiringModeCombox]
						}, {
							border : false,
							layout : 'form',
							colspan : 1,
							labelWidth : 100,
							width : 230,
							items : [measModeCombox]
						}]
			});
	/* 电量交换点分类 */
	// var exchgTypeCodeData = [['01', '发电企业'], ['02', '区域电网'], ['03', '省级企业'],
	// ['04', '地市企业'], ['05', '趸售单位']];
	// var exchgTypeCodeStore = new Ext.data.SimpleStore({
	// fields : ['exchgTypeCodevalue', 'exchgTypeCodetext'],
	// data : exchgTypeCodeData
	// });
	var exchgTypeCodeCombox = new Ext.ux.ComboBox({
		sql : "sql=SELECT VALUE, NAME FROM P_CODE WHERE CODE_TYPE = 'exchgSortCode'",
		displayField : 'NAME',
		valueField : 'VALUE',
		editable : false,
		name : 'exchgTypeCode',
		hiddenName : 'exchgTypeCode',
		fieldLabel : '电量交换点分类',
		forceSelection : true,
		triggerAction : 'all',
		emptyText : '--请选择--',
		blankText : '--请选择--',
		selectOnFocus : true,
		anchor : '100%'
	});
	/* 电能计量装置分类 */
	// var mdTypeCodeData = [['1', 'Ⅰ类计量装置'], ['2', 'Ⅱ类计量装置'], ['3', 'Ⅲ类计量装置'],
	// ['4', 'Ⅳ类计量装置'], ['5', 'Ⅴ类计量装置']];
	// var mdTypeCodeStore = new Ext.data.SimpleStore({
	// fields : ['mdTypeCodevalue', 'mdTypeCodetext'],
	// data : mdTypeCodeData
	// });
	var mdTypeCodeCombox = new Ext.ux.ComboBox({
		sql : "sql=SELECT VALUE, NAME FROM P_CODE WHERE CODE_TYPE = 'mdTypeCode'",
		displayField : 'NAME',
		valueField : 'VALUE',
		editable : false,
		name : 'mdTypeCode',
		hiddenName : 'mdTypeCode',
		fieldLabel : '电能计量装置分类',
		forceSelection : true,
		triggerAction : 'all',
		emptyText : '--请选择--',
		blankText : '--请选择--',
		selectOnFocus : true,
		anchor : '100%'
	});
	/* 计量点状态 */
	// var statusCodeData = [['01', '设立'], ['02', '在用'], ['03', '停用'],
	// ['04', '撤销']];
	// var statusCodeStore = new Ext.data.SimpleStore({
	// fields : ['statusCodevalue', 'statusCodetext'],
	// data : statusCodeData
	// });
	var jldStatusCodeCombox = new Ext.ux.ComboBox({
				sql : 'sql=SELECT STATUS_CODE, STATUS_NAME FROM VW_MP_STATUS',
				displayField : 'STATUS_NAME',
				valueField : 'STATUS_CODE',
				editable : false,
				name : 'statusCode',
				hiddenName : 'statusCode',
				fieldLabel : '计量点状态',
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--',
				selectOnFocus : true,
				anchor : '100%'
			});
	/* 系统中性点接地方式 */
	// var earthModeData = [['01', '中性点直接接地'], ['02', '中性点经消弧线圈接地'],
	// ['03', '中性点不接地']];
	// var earthModeStore = new Ext.data.SimpleStore({
	// fields : ['earthModevalue', 'earthModetext'],
	// data : earthModeData
	// });
	var earthModeCombox = new Ext.ux.ComboBox({
		sql : "sql=SELECT VALUE, NAME FROM P_CODE WHERE CODE_TYPE = 'earthMode'",
		displayField : 'NAME',
		valueField : 'VALUE',
		editable : false,
		name : 'earthMode',
		hiddenName : 'earthMode',
		fieldLabel : '系统中性点接地方式',
		forceSelection : true,
		triggerAction : 'all',
		emptyText : '--请选择--',
		blankText : '--请选择--',
		selectOnFocus : true,
		anchor : '100%',
		minListWidth : 250
	});

	var cmpForm3 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.33,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [{
							border : false,
							layout : 'form',
							colspan : 1,
							labelWidth : 100,
							width : 230,
							items : [exchgTypeCodeCombox]
						}, {
							border : false,
							layout : 'form',
							colspan : 1,
							labelWidth : 100,
							width : 230,
							items : [mdTypeCodeCombox]
						}, new Ext.form.TextField({
									name : 'mrSn',
									emptyText : '请输入抄表顺序号',
									maxLength : 5,
									fieldLabel : '抄表顺序号'
								}), new Ext.form.TextField({
									name : 'mpSn',
									maxLength : 5,
									emptyText : '请输入计量管理顺序号',
									fieldLabel : '计量管理顺序号'
								}), new Ext.ux.RadioGroup({
									fieldLabel : '是否装表',
									width : 115,
									items : [{
												boxLabel : '是',
												name : 'meterFlag',
												inputValue : 01,
												checked : true
											}, {
												boxLabel : '否',
												name : 'meterFlag',
												inputValue : 02
											}]
								}), jldStatusCodeCombox,
						new Ext.ux.RadioGroup({
									fieldLabel : '注册状态',
									width : 115,
									items : [{
												boxLabel : '是',
												name : 'lcFlag',
												inputValue : 01,
												checked : true
											}, {
												boxLabel : '否',
												name : 'lcFlag',
												inputValue : 02
											}]
								}), earthModeCombox]
			});
	var cmpForm = new Ext.FormPanel({
				region : 'south',
				height : 260,
				border : false,
				bodyStyle : 'padding: 10px 0px 0px 10px',
				title : '详细信息',
				layout : 'column',
				items : [cmpForm1, cmpForm2, cmpForm3]
			});

	var cmpPanel = new Ext.Panel({
				layout : 'border',
				border : false,
				items : [cmpGrid, cmpForm]
			});

	var cmeterRecord = new Ext.data.Record.create([{
				name : 'meterId',
				mapping : 'meterId'
			}, {
				name : 'assetNo',
				mapping : 'assetNo'
			}, {
				name : 'mpId',
				mapping : 'mpId'
			}, {
				name : 'orgNo',
				mapping : 'orgNo'
			}, {
				name : 'areaNo',
				mapping : 'areaNo'
			}, {
				name : 'consNo',
				mapping : 'consNo'
			}, {
				name : 'baudrate',
				mapping : 'baudrate'
			}, {
				name : 'commNo',
				mapping : 'commNo'
			}, {
				name : 'commAddr1',
				mapping : 'commAddr1'
			}, {
				name : 'commAddr2',
				mapping : 'commAddr2'
			}, {
				name : 'commMode',
				mapping : 'commMode'
			}, {
				name : 'instLoc',
				mapping : 'instLoc'
			}, {
				name : 'instDate',
				mapping : 'instDate'
			}, {
				name : 'tFactor',
				mapping : 'tFactor'
			}, {
				name : 'refMeterFlag',
				mapping : 'refMeterFlag'
			}, {
				name : 'refMeterId',
				mapping : 'refMeterId'
			}, {
				name : 'validateCode',
				mapping : 'validateCode'
			}, {
				name : 'moduleNo',
				mapping : 'moduleNo'
			}, {
				name : 'mrFactor',
				mapping : 'mrFactor'
			}, {
				name : 'lastChkDate',
				mapping : 'lastChkDate'
			}, {
				name : 'rotateCycle',
				mapping : 'rotateCycle'
			}, {
				name : 'rotateValidDate',
				mapping : 'rotateValidDate'
			}, {
				name : 'chkCycle',
				mapping : 'chkCycle'
			}, {
				name : 'tmnlAssetNo',
				mapping : 'tmnlAssetNo'
			}, {
				name : 'fmrAssetNo',
				mapping : 'fmrAssetNo'
			}, {
				name : 'regStatus',
				mapping : 'regStatus'
			}, {
				name : 'regSn',
				mapping : 'regSn'
			}]);
	var cmeterStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './runman/archivecmeterman!queryCmeter.action'
						}),
				// proxy : new Ext.data.MemoryProxy(data),
				reader : new Ext.data.JsonReader({
							root : 'cmeterList'
						}, [{
									name : 'meterId'
								}, {
									name : 'assetNo'
								}, {
									name : 'mpId'
								}, {
									name : 'orgNo'
								}, {
									name : 'areaNo'
								}, {
									name : 'consNo'
								}, {
									name : 'baudrate'
								}, {
									name : 'commNo'
								}, {
									name : 'commAddr1'
								}, {
									name : 'commAddr2'
								}, {
									name : 'commMode'
								}, {
									name : 'instLoc'
								}, {
									name : 'instDate'
								}, {
									name : 'tFactor'
								}, {
									name : 'refMeterFlag'
								}, {
									name : 'refMeterId'
								}, {
									name : 'validateCode'
								}, {
									name : 'moduleNo'
								}, {
									name : 'mrFactor'
								}, {
									name : 'lastChkDate'
								}, {
									name : 'rotateCycle'
								}, {
									name : 'rotateValidDate'
								}, {
									name : 'chkCycle'
								}, {
									name : 'tmnlAssetNo'
								}, {
									name : 'fmrAssetNo'
								}, {
									name : 'regStatus'
								}, {
									name : 'regSn'
								}])
			});

	var cmeterColumnModel = new Ext.grid.ColumnModel([{
				header : '电能表标识',
				dataIndex : 'meterId'
			}, {
				header : '资产编号',
				dataIndex : 'assetNo'
			}, {
				header : '计量点标识',
				dataIndex : 'mpId'
			}, {
				header : '供电单位编号',
				dataIndex : 'orgNo'
			}, {
				header : '供电区域编码',
				dataIndex : 'areaNo'
			}, {
				header : '用户编号',
				dataIndex : 'consNo'
			}, {
				header : '波特率',
				dataIndex : 'baudrate'
			}, {
				header : '通讯规约',
				dataIndex : 'commNo'
			}, {
				header : '通讯地址1',
				dataIndex : 'commAddr1'
			}, {
				header : '通讯地址2',
				dataIndex : 'commAddr2'
			}, {
				header : '通讯方式',
				dataIndex : 'commMode'
			}, {
				header : '安装位置',
				dataIndex : 'instLoc'
			}, {
				header : '安装日期',
				dataIndex : 'instDate'
			}, {
				header : '综合倍率',
				dataIndex : 'tFactor'
			}, {
				header : '集抄验证码',
				dataIndex : 'validateCode'
			}, {
				header : '模块编号',
				dataIndex : 'moduleNo'
			}, {
				header : '抄表系数',
				dataIndex : 'mrFactor'
			}, {
				header : '上次检验日期',
				dataIndex : 'lastChkDate'
			}, {
				header : '轮换周期',
				dataIndex : 'rotateCycle'
			}, {
				header : '轮换有效日期',
				dataIndex : 'rotateValidDate'
			}, {
				header : '检验周期',
				dataIndex : 'chkCycle'
			}, {
				header : '终端资产编号',
				dataIndex : 'tmnlAssetNo'
			}, {
				header : '集抄资产编号',
				dataIndex : 'fmrAssetNo'
			}, {
				header : '注册状态',
				dataIndex : 'regStatus'
			}, {
				header : '注册序号',
				dataIndex : 'regSn'
			}]);

	var loadCmeter = function() {
		cmeterStore.load({
					params : {
						consNo : consForm.getForm().findField('consNo')
								.getValue()
					}
				});
	}

	var cmeterGrid = new Ext.grid.GridPanel({
				region : 'center',
				viewConfig : {
					forceFit : false
				},
				autoScroll : true,
				sm : new Ext.grid.RowSelectionModel(),
				cm : cmeterColumnModel,
				ds : cmeterStore,
				tbar : ['电能表维护', '->', {
							text : '增加电能表',
							handler : function() {

								if (!cmeterForm.getForm().isValid()) {
									Ext.Msg.alert('提示', '请完整输入电表参数必填选项');
									return true;
								}

								cmeterStore.add(new cmeterRecord({
											'meterId' : '',
											'assetNo' : '',
											'mpId' : '',
											'orgNo' : '',
											'areaNo' : '',
											'consNo' : '',
											'baudrate' : '',
											'commNo' : '',
											'commAddr1' : '',
											'commAddr2' : '',
											'commMode' : '',
											'instLoc' : '',
											'instDate' : '',
											'tFactor' : '',
											'refMeterFlag' : '',
											'refMeterId' : '',
											'validateCode' : '',
											'moduleNo' : '',
											'mrFactor' : '',
											'lastChkDate' : '',
											'rotateCycle' : '',
											'rotateValidDate' : '',
											'chkCycle' : '',
											'tmnlAssetNo' : '',
											'fmrAssetNo' : '',
											'regStatus' : '',
											'regSn' : ''
										}));
								cmeterGrid.getSelectionModel().selectLastRow();

							}
						}, {
							text : '删除最后一行',
							handler : function() {
								// var selectRow;
								// for (var i = 0; i <
								// cpEditGrid.getStore().getCount(); i++) {
								// if
								// (cpEditGrid.getSelectionModel().isSelected(i))
								// ;
								// {
								// selectRow = i;
								// break;
								// }
								// }
								// if (selectRow > -1) {
								// cpEditGrid.getStore().removeAt(selectRow);
								// }
								cmeterGrid.getStore().removeAt(cmeterGrid
										.getStore().getCount()
										- 1);

							}
						}, {
							text : '重新载入',
							handler : function() {
								loadCmeter();
							}
						}]
			});

	var cmeterRowSelect = function(sm, num, rec) {

		if (!Ext.isEmpty(rec))
			cmeterForm.getForm().setValues(rec.data);

	}
	cmeterGrid.getSelectionModel().on('rowselect', cmeterRowSelect);

	var cmeterRowDeselect = function(sm, row, rec) {

		if (!cmeterForm.getForm().isValid()) {
			Ext.Msg.alert('提示', '请完整输入电表参数必填选项');
		}

		var fmval = cmeterForm.getForm().getFieldValues();
		for (var o in fmval) {
			rec.set(o, fmval[o]);
		}
		rec.commit();

	}

	cmeterGrid.getSelectionModel().on('rowdeselect', cmeterRowDeselect);

	var meterMpStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './runman/archivecmpman!queryCmp.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'cmpList'
						}, [{
									name : 'mpId'
								}, {
									name : 'mpName'
								}])
			});
	meterMpStore.on('beforeload', function(s) {
				s.baseParams = {
					consId : consForm.getForm().findField('consId').getValue()
				};
			});
	meterMpStore.load();
	var meterMpCombox = new Ext.form.ComboBox({
				width : 300,
				hideOnSelect : false,
				displayField : 'mpName',
				editable : false,
				valueField : 'mpId',
				hiddenName : 'mpId',
				maxHeight : 200,
				store : meterMpStore,
				mode : 'remote',
				fieldLabel : '计量点标识',
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--'
			});

	var cmeterForm1 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.33,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [new Ext.form.TextField({
									name : 'meterId',
									emptyText : '请输入电能表标识',
									fieldLabel : '电能表标识',
									hidden : true,
									hideLabel : true
								}), new Ext.form.TextField({
									name : 'assetNo',
									maxLength : 32,
									emptyText : '请输入资产编号',
									fieldLabel : '资产编号'
								}), meterMpCombox, new Ext.form.TextField({
									name : 'orgNo',
									readOnly : true,
									emptyText : '请输入供电单位编号',
									fieldLabel : '供电单位编号'
								}), new Ext.form.TextField({
									name : 'areaNo',
									readOnly : true,
									emptyText : '请输入供电区域编码',
									fieldLabel : '供电区域编码'
								}), new Ext.form.TextField({
									name : 'consNo',
									readOnly : true,
									emptyText : '请输入用户编号',
									fieldLabel : '用户编号'
								}), new Ext.form.TextField({
									name : 'baudrate',
									maxLength : 16,
									emptyText : '请输入波特率',
									fieldLabel : '波特率'
								}), new Ext.form.TextField({
									name : 'commNo',
									maxLength : 8,
									emptyText : '请输入通讯规约',
									fieldLabel : '通讯规约'
								}), new Ext.form.TextField({
									name : 'commAddr1',
									maxLength : 16,
									emptyText : '请输入通讯地址1',
									fieldLabel : '通讯地址1'
								})]

			});
	var cmeterForm2 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.33,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [new Ext.form.TextField({
									name : 'commAddr2',
									emptyText : '请输入通讯地址2',
									maxLength : 16,
									fieldLabel : '通讯地址2'
								}), new Ext.form.TextField({
									name : 'commMode',
									emptyText : '请输入通讯方式',
									maxLength : 8,
									fieldLabel : '通讯方式'
								}), new Ext.form.TextField({
									name : 'instLoc',
									maxLength : 256,
									emptyText : '请输入安装位置',
									fieldLabel : '安装位置'
								}), new Ext.form.DateField({
									name : 'instDate',
									format : 'Y-m-d',
									emptyText : '请输入安装日期',
									fieldLabel : '安装日期'
								}), new Ext.form.NumberField({
									name : 'tFactor',
									emptyText : '请输入综合倍率',
									maxValue : 9999999999.99,
									fieldLabel : '综合倍率'
								}), new Ext.form.TextField({
									name : 'validateCode',
									maxLength : 32,
									emptyText : '请输入集抄验证码',
									fieldLabel : '集抄验证码'
								}), new Ext.form.TextField({
									name : 'moduleNo',
									maxLength : 32,
									emptyText : '请输入模块编号',
									fieldLabel : '模块编号'
								}), new Ext.form.TextField({
									name : 'mrFactor',
									maxLength : 8,
									emptyText : '请输入抄表系数',
									fieldLabel : '抄表系数'
								})]

			});

	var meterTmnlStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './runman/archivetmnlman!queryTmnls.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'list'
						}, [{
									name : 'tmnlAssetNo'
								}])
			});

	meterTmnlStore.on('beforeload', function(s) {
				s.baseParams = {
					consId : consForm.getForm().findField('consId').getValue()
				};

			});
	meterTmnlStore.load();
	var meterTmnlCombox = new Ext.form.ComboBox({
				width : 300,
				hideOnSelect : false,
				displayField : 'tmnlAssetNo',
				editable : false,
				valueField : 'tmnlAssetNo',
				hiddenName : 'tmnlAssetNo',
				maxHeight : 200,
				store : meterTmnlStore,
				mode : 'remote',
				fieldLabel : '终端资产编号',
				triggerAction : 'all',
				emptyText : '--请选择--',
				blankText : '--请选择--'
			});

	var cmeterForm3 = new Ext.Panel({
				layout : 'form',
				columnWidth : 0.33,
				border : false,
				defaults : {
					anchor : '95%'
				},
				labelAlign : 'right',
				items : [new Ext.form.DateField({
									name : 'lastChkDate',
									format : 'Y-m-d',
									emptyText : '请输入上次检验日期',
									fieldLabel : '上次检验日期'
								}), new Ext.form.TextField({
									name : 'rotateCycle',
									emptyText : '请输入轮换周期',
									maxLength : 5,
									fieldLabel : '轮换周期'
								}), new Ext.form.DateField({
									name : 'rotateValidDate',
									format : 'Y-m-d',
									emptyText : '请输入轮换有效日期',
									fieldLabel : '轮换有效日期'
								}), new Ext.form.TextField({
									name : 'chkCycle',
									maxLength : 5,
									emptyText : '请输入检验周期',
									fieldLabel : '检验周期'
								}), meterTmnlCombox, new Ext.form.TextField({
									name : 'fmrAssetNo',
									maxLength : 32,
									emptyText : '请输入集抄资产编号',
									fieldLabel : '集抄资产编号'
								}), new Ext.ux.RadioGroup({
									fieldLabel : '注册状态',
									columns : 2,
									width : 120,
									items : [{
												boxLabel : '未注册',
												name : 'regStatus',
												inputValue : 0
											}, {
												boxLabel : '已注册',
												name : 'regStatus',
												inputValue : 1,
												checked : true
											}]
								}), new Ext.form.TextField({
									name : 'regSn',
									maxLength : 5,
									emptyText : '请输入注册序号',
									fieldLabel : '注册序号'
								})]

			});

	var cmeterForm = new Ext.FormPanel({
				region : 'south',
				height : 260,
				border : false,
				bodyStyle : 'padding: 10px 0px 0px 10px',
				title : '详细信息',
				layout : 'column',
				items : [cmeterForm1, cmeterForm2, cmeterForm3]
			});

	var cmeterPanel = new Ext.Panel({
				layout : 'border',
				border : false,
				items : [cmeterGrid, cmeterForm]
			});;

	// consMan CCons保存
	var afterConsMan = function(btn) {
		// 判断是新增或是查询的
		var form = consForm.getForm();
		var custId = form.findField('custId').getValue();
		// if(!Ext.isEmpty()&&consId!='-1')

		// if (Ext.isEmpty(custId) || custId == '-1') {
		// DAO层使用了Hinbernate的SaveOrUpdate方式进行新增或更改

		if (!form.isValid()) {
			Ext.Msg.alert('提示', '请输入必须字段值!');
			return;
		}
		Ext.Ajax.request({
					url : './runman/archiveman!saveArchiveCons.action',
					params : consForm.getForm().getFieldValues(),
					success : function(response) {
						var obj = Ext.decode(response.responseText);
						form.trackResetOnLoad = true;
						form.setValues(obj.ccons);
						Ext.MessageBox.alert('提示', '已成功保存！');
					}
				});
		// }
		// if (Ext.isEmpty(form.findField('consId').getValue())) {
		// // if (!consForm.getForm().isValid()) {
		// // Ext.MessageBox.alert('提示', '部分数据不符合规则，请按提示修改');
		// // return;
		// // }

	};

	// conMan 页面的下一步按钮事件
	var consNextProcess = function(btn) {
		// 开发时不判断是否已修改
		// if (consForm.getForm().isDirty()) {
		// Ext.MessageBox.alert('提示', '请先保存数据！');
		// return;
		// }
		var consfield = consForm.getForm().findField('consId');
		if (Ext.isEmpty(consfield.getValue())) {
			Ext.MessageBox.alert('提示', '请先从左边树点选用户节点并查询数据或录入并保存数据！');
			return;
		}
		archiveManCard.layout.setActiveItem(1);
		loadRcp();
	};

	// 重置方法
	var businessReset = function(btn) {
		if (archiveManCard.layout.activeItem == consForm) {
			// consForm.getForm().reset();
			consForm.getForm().clear();
		} else if (archiveManCard.layout.activeItem == cpManPanel) {
			; // 需要同时保存R_cp 和 r_cp_comm_param 不需要清空
		} else if (archiveManCard.layout.activeItem == tmnlPanel) {
			tmnlForm.getForm().clear();
		} else if (archiveManCard.layout.activeItem == cmpPanel) {
			cmpForm.getForm().clear();
		} else if (archiveManCard.layout.activeItem == cmeterPanel) {
			cmeterForm.getForm().clear();
		} else {

		}
	}

	// 业务处理方法
	var businessProcess = function(btn) {
		if (archiveManCard.layout.activeItem == consForm) {
			afterConsMan(btn);
		} else if (archiveManCard.layout.activeItem == cpManPanel) {
			saveRcpsMan();

		} else if (archiveManCard.layout.activeItem == tmnlPanel) {
			saveTmnlsMan();

		} else if (archiveManCard.layout.activeItem == cmpPanel) {
			saveCmpsMan();

		} else if (archiveManCard.layout.activeItem == cmeterPanel) {
			saveCmemtersMan();

		} else {
		}

	};

	var saveRcpsMan = function() {
		// cpEditGrid.getSelectionModel().clearSelections();
		var rcpRec = rcpStore.getRange();
		var rcpParaRec = cpParamStore.getRange();
		var rcpJsonData = [];
		var rcpParaJsonData = [];
		for (var i = 0; i < rcpRec.length; i++) {
			// alert(rcpParaRec[i].data);
			rcpJsonData[i] = rcpRec[i].data;
			// rcpParaJsonData[i] = rcpParaRec[i].data;
		}
		/*
		 * if(!cpParamForm.getForm().isValid()){
		 * Ext.Msg.alert('提示','请完整输入必填选项'); return ; }
		 */
		Ext.Ajax.request({
					url : './runman/archivercpman!saveRcps.action',
					params : {
						consId : consForm.getForm().findField('consId')
								.getValue(),
						rcpStore : Ext.encode(rcpJsonData)
						// ,
						// rcpParaStore : Ext.encode(rcpParaJsonData)
					},
					success : function(response) {
						Ext.MessageBox.alert('提示', '保存采集点成功');
						loadRcp();
					}
				});
	};

	var saveTmnlsMan = function() {
		tmnlGrid.getSelectionModel().clearSelections();
		var tmnlRec = tmnlStore.getRange();

		var tmnlJsonData = [];

		for (var i = 0; i < tmnlRec.length; i++) {
			tmnlJsonData[i] = tmnlRec[i].data;

		}

		var tform = tmnlForm.getForm();
		if (!tmnlForm.getForm().isValid()) {
			Ext.Msg.alert('提示', '请完整输入终端信息的必填选项');
			return;
		}

		var cpParamFlag = cpParamForm.disabled;
		// return;
		if (!cpParamFlag) {
			var rcpParamForm = cpParamForm.getForm();
			tmnlTabPanel.setActiveTab(cpParamForm);// 激活终端参数Panel
			rcpParamForm.findField("terminalAddr").setValue(tform
					.findField("terminalAddr").value);
			var f = subCpCombox.getValue();
			rcpParamForm.findField("cpNo").setValue(f);// findField("cpNo").getValue());
			var values = rcpParamForm.getValues(false);
			tmnlTabPanel.setActiveTab(tmnlForm);
			Ext.Ajax.request({
						url : './runman/archivetmnlman!saveTmnls.action',
						params : {
							consNo : consForm.getForm().findField('consNo')
									.getValue(),
							tmnlStore : Ext.encode(tmnlJsonData),
							tmnlParams : Ext.encode(values)
						},
						success : function(response) {
							Ext.MessageBox.alert('提示', '保存终端成功');
							meterTmnlStore.load();
						}
					});
		} else {
			Ext.Ajax.request({
						url : './runman/archivetmnlman!saveTmnls.action',
						params : {
							consNo : consForm.getForm().findField('consNo')
									.getValue(),
							tmnlStore : Ext.encode(tmnlJsonData)
						},
						success : function(response) {
							Ext.MessageBox.alert('提示', '保存终端成功');
							meterTmnlStore.load();
						}
					});
		}

	};

	var saveCmpsMan = function() {
		cmpGrid.getSelectionModel().clearSelections();
		var cmpRec = cmpStore.getRange();

		var cmpJsonData = [];

		for (var i = 0; i < cmpRec.length; i++) {
			cmpJsonData[i] = cmpRec[i].data;

		}

		if (!cmpForm.getForm().isValid()) {
			Ext.Msg.alert('提示', '请完整输入计量点信息的必填选项');
		}

		Ext.Ajax.request({
					url : './runman/archivecmpman!saveCmp.action',
					params : {
						consNo : consForm.getForm().findField('consNo')
								.getValue(),
						cmpStore : Ext.encode(cmpJsonData)
					},
					success : function(response) {
						Ext.MessageBox.alert('提示', '保存计量点成功');
						meterMpStore.load();
					}
				});
	}

	var saveCmemtersMan = function() {
		cmeterGrid.getSelectionModel().clearSelections();
		var cmeterRec = cmeterStore.getRange();

		var cmeterJsonData = [];

		for (var i = 0; i < cmeterRec.length; i++) {
			cmeterJsonData[i] = cmeterRec[i].data;

		}

		if (!cmeterForm.getForm().isValid()) {
			Ext.Msg.alert('提示', '请完整输入电表参数必填选项');
		}

		Ext.Ajax.request({
					url : './runman/archivecmeterman!saveCmeter.action',
					params : {
						consNo : consForm.getForm().findField('consNo')
								.getValue(),
						cmeterStore : Ext.encode(cmeterJsonData)

					},
					success : function(response) {
						Ext.MessageBox.alert('提示', '保存电能表成功');
					}
				});
	};

	var previousProcess = function(btn) {
		if (archiveManCard.layout.activeItem == consForm) {

		} else if (archiveManCard.layout.activeItem == cpManPanel) {
			// archiveManCard.layout.setActiveItem(0);
			archiveManCard.layout.setActiveItem(consForm);
			previousButton.disable();
		} else if (archiveManCard.layout.activeItem == tmnlPanel) {
			// archiveManCard.layout.setActiveItem(1);
			archiveManCard.layout.setActiveItem(cpManPanel);
		} else if (archiveManCard.layout.activeItem == cmpPanel) {
			// archiveManCard.layout.setActiveItem(2);
			archiveManCard.layout.setActiveItem(tmnlPanel);
		} else if (archiveManCard.layout.activeItem == cmeterPanel) {
			// archiveManCard.layout.setActiveItem(3);
			archiveManCard.layout.setActiveItem(cmpPanel);
		} else if (archiveManCard.layout.activeItem == cplinkcq) {
			// archiveManCard.layout.setActiveItem(4);
			archiveManCard.layout.setActiveItem(cmeterPanel);
		} else if (archiveManCard.layout.activeItem == remoteDebugPanel) {
			if (consTypeCombox.getValue() == 1) {// 当前用户为专变
				archiveManCard.layout.setActiveItem(cmeterPanel);
				nextButton.setText("下一步");
			} else {
				archiveManCard.layout.setActiveItem(cplinkcq);
				nextButton.setText("下一步");
			}
			// archiveManCard.layout.setActiveItem(5);
		} else {

		}
	};

	// 下一步按钮处理方法
	var nextProcess = function() {
		if (archiveManCard.layout.activeItem == consForm) {
			consNextProcess();
		} else if (archiveManCard.layout.activeItem == cpManPanel) {
			archiveManCard.layout.setActiveItem(tmnlPanel);
			loadTmnl();
		} else if (archiveManCard.layout.activeItem == tmnlPanel) {
			archiveManCard.layout.setActiveItem(cmpPanel);
			loadCmp();
		} else if (archiveManCard.layout.activeItem == cmpPanel) {
			archiveManCard.layout.setActiveItem(cmeterPanel);
			loadCmeter();
		} else if (archiveManCard.layout.activeItem == cmeterPanel) {
			// 得到当前用户是专变还是其它类型,如果为专变类型不显示cplinkcq
			if (consTypeCombox.getValue() == 1) {// 当前用户为专变
				archiveManCard.layout.setActiveItem(remoteDebugPanel);
				nextButton.setText("发起远程调试");
			} else {
				archiveManCard.layout.setActiveItem(cplinkcq);
			}
		} else if (archiveManCard.layout.activeItem == cplinkcq) {
			archiveManCard.layout.setActiveItem(remoteDebugPanel);
			nextButton.setText("发起远程调试");
		} else if (nextButton.getText() == "发起远程调试") {
			remoteDebug();
		}
		previousButton.enable();
	};

	// 上一步按钮
	var previousButton = new Ext.Button({
				text : '上一步',
				handler : previousProcess,
				disabled : true
			});

	// 重置按钮
	/*
	 * var resetButton = new Ext.Button({ text : ' 清空 ', handler : businessReset
	 * });
	 */

	// 业务处理按钮
	var buinessButton = new Ext.Button({
				text : '保存',
				handler : businessProcess
			});

	// 下一步按钮
	var nextButton = new Ext.Button({
				text : '下一步',
				handler : nextProcess
			});

	// 综合界面
	var archiveManCard = new Ext.Panel({
				border : false,
				region : 'center',
				activeItem : 0,
				layout : 'card',
				buttonAlign : 'center',
				items : [consForm, cpManPanel, tmnlPanel, cmpPanel,
						cmeterPanel, cplinkcq, remoteDebugPanel],
				buttons : [previousButton, buinessButton, nextButton]
			});

	var viewPanel = new Ext.Panel({
				border : false,
				layout : 'border',
				items : [archiveQueryForm, archiveManCard]
			});

	// 渲染到主页面
	renderModel(viewPanel, '档案维护');
});
