/**
 * 用户列表高级查询页面
 */

/**
 * 自定义控件 List of ComboBox
 */
if ('function' !== typeof RegExp.escape) {
	RegExp.escape = function(s) {
		if ('string' !== typeof s) {
			return s;
		}
		// Note: if pasting from forum, precede ]/\ with backslash manually
		return s.replace(/([.*+?^=!:${}()|[\]\/\\])/g, '\\$1');
	}; // eo function escape
}

// create namespace
Ext.ns('Ext.ux.form');

/**
 * 
 * @class Ext.ux.form.LovCombo
 * @extends Ext.form.ComboBox
 */
Ext.ux.form.LovCombo = Ext.extend(Ext.form.ComboBox, {

			// {{{
			// configuration options
			/**
			 * @cfg {String} checkField name of field used to store checked
			 *      state. It is automatically added to existing fields. Change
			 *      it only if it collides with your normal field.
			 */
			checkField : 'checked'

			/**
			 * @cfg {String} separator separator to use between values and texts
			 */
			,
			separator : ','

			/**
			 * @cfg {String/Array} tpl Template for items. Change it only if you
			 *      know what you are doing.
			 */
			// }}}
			// {{{
			,
			initComponent : function() {

				// template with checkbox
				// if (!this.tpl) {
				// this.tpl = '<tpl for=".">'
				// + '<div class="x-combo-list-item">' + '<img src="'
				// + Ext.BLANK_IMAGE_URL + '" '
				// + 'class="ux-lovcombo-icon ux-lovcombo-icon-'
				// + '{[values.' + this.checkField
				// + '?"checked":"unchecked"' + ']}">'
				// + '<div class="ux-lovcombo-item-text">{'
				// + (this.displayField || 'text') + '}</div>'
				// + '</div>' + '</tpl>';
				// }
				imgCheck = "<img src='./images/checked.gif' />";
				imgUncheck = "<img src='./images/unchecked.gif' />";
				if (!this.tpl) {
					this.tpl = '<tpl for=".">'
							+ '<div class="x-combo-list-item">' + '<img src="'
							+ Ext.BLANK_IMAGE_URL + '" '
							+ 'class="ux-lovcombo-icon ux-lovcombo-icon-'
							+ '{[values.'
							+ this.checkField
							+ '?"checked":"unchecked"'
							+ ']}">'
							+ '<div class="ux-lovcombo-item-text">'
							// + '{[values.' + this.checkField + '?"√":" " ]}'
							+ '{[values.' + this.checkField + '?"' + imgCheck
							+ '":"' + imgUncheck + '"]}' + '{'
							+ (this.displayField || 'text') + '}</div>'
							+ '</div>' + '</tpl>';
				}

				// call parent
				Ext.ux.form.LovCombo.superclass.initComponent.apply(this,
						arguments);

				// install internal event handlers
				this.on({
							scope : this,
							beforequery : this.onBeforeQuery,
							blur : this.onRealBlur
						});

				// remove selection from input field
				this.onLoad = this.onLoad.createSequence(function() {
							if (this.el) {
								var v = this.el.dom.value;
								this.el.dom.value = '';
								this.el.dom.value = v;
							}
						});

			} // e/o function initComponent
			// }}}
			// {{{
			/**
			 * Disables default tab key bahavior
			 * 
			 * @private
			 */
			,
			initEvents : function() {
				Ext.ux.form.LovCombo.superclass.initEvents.apply(this,
						arguments);

				// disable default tab handling - does no good
				this.keyNav.tab = false;

			} // eo function initEvents
			// }}}
			// {{{
			/**
			 * clears value
			 */
			,
			clearValue : function() {
				this.value = '';
				this.setRawValue(this.value);
				this.store.clearFilter();
				this.store.each(function(r) {
							r.set(this.checkField, false);
						}, this);
				if (this.hiddenField) {
					this.hiddenField.value = '';
				}
				this.applyEmptyText();
			} // eo function clearValue
			// }}}
			// {{{
			/**
			 * @return {String} separator (plus space) separated list of
			 *         selected displayFields
			 * @private
			 */
			,
			getCheckedDisplay : function() {
				var re = new RegExp(this.separator, "g");
				return this.getCheckedValue(this.displayField).replace(re,
						this.separator + ' ');
			} // eo function getCheckedDisplay
			// }}}
			// {{{
			/**
			 * @return {String} separator separated list of selected valueFields
			 * @private
			 */
			,
			getCheckedValue : function(field) {
				field = field || this.valueField;
				var c = [];

				// store may be filtered so get all records
				var snapshot = this.store.snapshot || this.store.data;

				snapshot.each(function(r) {
							if (r.get(this.checkField)) {
								c.push(r.get(field));
							}
						}, this);

				return c.join(this.separator);
			} // eo function getCheckedValue
			// }}}
			// {{{
			/**
			 * beforequery event handler - handles multiple selections
			 * 
			 * @param {Object}
			 *            qe query event
			 * @private
			 */
			,
			onBeforeQuery : function(qe) {
				qe.query = qe.query.replace(new RegExp(this.getCheckedDisplay()
								+ '[ ' + this.separator + ']*'), '');
			} // eo function onBeforeQuery
			// }}}
			// {{{
			/**
			 * blur event handler - runs only when real blur event is fired
			 */
			,
			onRealBlur : function() {
				this.list.hide();
				var rv = this.getRawValue();
				var rva = rv.split(new RegExp(RegExp.escape(this.separator)
						+ ' *'));
				var va = [];
				var snapshot = this.store.snapshot || this.store.data;

				// iterate through raw values and records and check/uncheck
				// items
				Ext.each(rva, function(v) {
							snapshot.each(function(r) {
										if (v === r.get(this.displayField)) {
											va.push(r.get(this.valueField));
										}
									}, this);
						}, this);
				this.setValue(va.join(this.separator));
				this.store.clearFilter();
			} // eo function onRealBlur
			// }}}
			// {{{
			/**
			 * Combo's onSelect override
			 * 
			 * @private
			 * @param {Ext.data.Record}
			 *            record record that has been selected in the list
			 * @param {Number}
			 *            index index of selected (clicked) record
			 */
			,
			onSelect : function(record, index) {
				if (this.fireEvent('beforeselect', this, record, index) !== false) {

					// toggle checked field
					record.set(this.checkField, !record.get(this.checkField));

					// display full list
					if (this.store.isFiltered()) {
						this.doQuery(this.allQuery);
					}

					// set (update) value and fire event
					this.setValue(this.getCheckedValue());
					this.fireEvent('select', this, record, index);
				}
			} // eo function onSelect
			// }}}
			// {{{
			/**
			 * Sets the value of the LovCombo
			 * 
			 * @param {Mixed}
			 *            v value
			 */
			,
			setValue : function(v) {
				if (v) {
					v = '' + v;
					if (this.valueField) {
						this.store.clearFilter();
						this.store.each(function(r) {
									var checked = !(!v.match('(^|'
											+ this.separator
											+ ')'
											+ RegExp.escape(r
													.get(this.valueField))
											+ '(' + this.separator + '|$)'));

									r.set(this.checkField, checked);
								}, this);
						this.value = this.getCheckedValue();
						this.setRawValue(this.getCheckedDisplay());
						if (this.hiddenField) {
							this.hiddenField.value = this.value;
						}
					} else {
						this.value = v;
						this.setRawValue(v);
						if (this.hiddenField) {
							this.hiddenField.value = v;
						}
					}
					if (this.el) {
						this.el.removeClass(this.emptyClass);
					}
				} else {
					this.clearValue();
				}
			} // eo function setValue
			// }}}
			// {{{
			/**
			 * Selects all items
			 */
			,
			selectAll : function() {
				this.store.each(function(record) {
							// toggle checked field
							record.set(this.checkField, true);
						}, this);

				// display full list
				this.doQuery(this.allQuery);
				this.setValue(this.getCheckedValue());
			} // eo full selectAll
			// }}}
			// {{{
			/**
			 * Deselects all items. Synonym for clearValue
			 */
			,
			deselectAll : function() {
				this.clearValue();
			} // eo full deselectAll
			// }}}
			,
			beforeBlur : function() {
			}
		}); // eo extend

// register xtype
Ext.reg('lovcombo', Ext.ux.form.LovCombo);

// eof

/**
 * 自定义控件 LOV 结束
 */

/**
 * 自定义编辑器的EditableGridPanel
 */
Ext.onReady(function() {

	var bfield = new Ext.form.Field({
				autoCreate : {
					tag : 'select',
					children : [{
								tag : 'option',
								value : 'true',
								html : 'true'
							}, {
								tag : 'option',
								value : 'false',
								html : 'false'
							}]
				},
				getValue : function() {
					return this.el.dom.value == 'true';
				}
			});

	var sffield = new Ext.form.Field({
				autoCreate : {
					tag : 'select',
					children : [{
								tag : 'option',
								value : '1',
								html : '是'
							}, {
								tag : 'option',
								value : '0',
								html : '否'
							}]
				},
				getValue : function() {
					return this.el.dom.value;
				}
			});

	var defaultEditors = {
		'date' : new Ext.form.DateField({
					selectOnFocus : true,
					format : 'Y-m-d',
					value : new Date()
				}),
		'string' : new Ext.form.TextField({
					selectOnFocus : true
				}),
		'number' : new Ext.form.NumberField({
					selectOnFocus : true,
					style : 'text-align:left;'
				}),
		'boolean' : bfield,
		'sffield' : sffield
	};

	// 定义各个字段的编辑器（combo)
	/**
	 * var lcfields; var lcstr = '[[\'=\',\'=\'],[\'>\',\'>\'],[\'<\',\'<\'],[\'>=\',\'>=\'],[\'<=\',\'<=\'],[\'in\',\'包括\'],[\'like\',\'包含\']]';
	 * 
	 * eval('lcfields=' + lcstr);
	 * 
	 * var lcstore = new Ext.data.SimpleStore({ fields : ['value', 'disp'], data :
	 * lcfields });
	 * 
	 * var lc = new Ext.ux.form.LovCombo({ width : 300, hideOnSelect : false,
	 * displayField : 'disp', valueField : 'value', maxHeight : 200, store :
	 * lcstore, mode : 'local' }); // lc.on()
	 * 
	 */

	/**
	 * var bureauComStore = new Ext.data.JsonStore({ url :
	 * "./sysman/generaltree!bureauList.action", fields : ['orgNo', 'orgName'],
	 * root : "bureauList" });
	 * 
	 * var bureauCombox = new Ext.ux.form.LovCombo({ width : 300, hideOnSelect :
	 * false, displayField : 'orgName', valueField : 'orgNo', maxHeight : 200,
	 * store : bureauComStore, mode : 'remote', fieldLabel : '供电所',
	 * triggerAction : 'all', emptyText : '--请选择供电所名称--'
	 * 
	 * });
	 */

	var orgNoComStore = new Ext.data.JsonStore({
				url : "./sysman/lefttreeuserlist!orgNoList.action",
				fields : ['orgNo', 'orgName'],
				root : "bureauList"
			});

	var orgNoCombox = new Ext.ux.form.LovCombo({
				width : 300,
				hideOnSelect : false,
				displayField : 'orgName',
				valueField : 'orgNo',
				maxHeight : 200,
				store : orgNoComStore,
				mode : 'remote',
				fieldLabel : '供电单位',
				triggerAction : 'all',
				emptyText : '--请选择供电单位名称--'

			});

	// 后台返回变量直接使用List 类型的，前台 json 变量名为大写
	// 变电站下拉选择 subIdList
	var subIdStore = new Ext.data.JsonStore({
				url : "./sysman/lefttreeuserlist!subIdList.action",
				fields : ['VALUE', 'DISP'],
				root : "valueList"
			});

	var subIdCombox = new Ext.ux.form.LovCombo({
				width : 300,
				hideOnSelect : false,
				displayField : 'DISP',
				valueField : 'VALUE',
				maxHeight : 200,
				store : subIdStore,
				mode : 'remote',
				fieldLabel : '变电站',
				triggerAction : 'all',
				emptyText : '--请选择供电站--'

			});

	// 线路ID 下拉选择 lineIdList
	var lineIdStore = new Ext.data.JsonStore({
				url : "./sysman/lefttreeuserlist!lineIdList.action",
				fields : ['VALUE', 'DISP'],
				root : "valueList"
			});

	var lineIdCombox = new Ext.ux.form.LovCombo({
				width : 300,
				hideOnSelect : false,
				displayField : 'DISP',
				valueField : 'VALUE',
				maxHeight : 200,
				store : lineIdStore,
				mode : 'remote',
				fieldLabel : '线路',
				triggerAction : 'all',
				emptyText : '--请选择线路--'

			});

	// 使用简单查询列表中的规约类型
	var protocolComStore = new Ext.data.JsonStore({
				url : "./sysman/generaltree!protocolList.action",
				fields : ['protocolCode', 'protocolName'],
				root : "protocolList"
			});

	var protocolCombox = new Ext.ux.form.LovCombo({
				width : 300,
				hideOnSelect : false,
				displayField : 'protocolName',
				valueField : 'protocolCode',
				maxHeight : 200,
				store : protocolComStore,
				mode : 'remote',
				fieldLabel : '规约类型',
				triggerAction : 'all',
				emptyText : '--请选择规约类型--'

			});

	var tradeCodeStore = new Ext.data.JsonStore({
				url : "./sysman/lefttreeuserlist!tradeCodeList.action",
				fields : ['VALUE', 'DISP'],
				root : "valueList"
			});

	var tradeCodeCombox = new Ext.ux.form.LovCombo({
				width : 300,
				hideOnSelect : false,
				displayField : 'DISP',
				valueField : 'VALUE',
				maxHeight : 200,
				store : tradeCodeStore,
				mode : 'remote',
				fieldLabel : '行业',
				triggerAction : 'all',
				emptyText : '--请选择行业--'

			});

	var consTypeStore = new Ext.data.JsonStore({
				url : "./sysman/lefttreeuserlist!consTypeList.action",
				fields : ['VALUE', 'DISP'],
				root : "valueList"
			});

	var consTypeCombox = new Ext.ux.form.LovCombo({
				width : 300,
				hideOnSelect : false,
				displayField : 'DISP',
				valueField : 'VALUE',
				maxHeight : 200,
				store : consTypeStore,
				mode : 'remote',
				fieldLabel : '用户类型',
				triggerAction : 'all',
				emptyText : '--请选择用户类型--'

			});

	var elecTypeStore = new Ext.data.JsonStore({
				url : "./sysman/lefttreeuserlist!elecTypeList.action",
				fields : ['VALUE', 'DISP'],
				root : "valueList"
			});

	var elecTypeCombox = new Ext.ux.form.LovCombo({
				width : 300,
				hideOnSelect : false,
				displayField : 'DISP',
				valueField : 'VALUE',
				maxHeight : 200,
				store : elecTypeStore,
				mode : 'remote',
				fieldLabel : '用电类别',
				triggerAction : 'all',
				emptyText : '--请选择用电类别--'

			});

	var capGradeStore = new Ext.data.JsonStore({
				url : "./sysman/lefttreeuserlist!capGradeList.action",
				fields : ['VALUE', 'DISP'],
				root : "valueList"
			});

	var capGradeCombox = new Ext.ux.form.LovCombo({
				width : 300,
				hideOnSelect : false,
				displayField : 'DISP',
				valueField : 'VALUE',
				maxHeight : 200,
				store : elecTypeStore,
				mode : 'remote',
				fieldLabel : '容量等级',
				triggerAction : 'all',
				emptyText : '--请选择容量等级--'

			});

	var shiftNoStore = new Ext.data.JsonStore({
				url : "./sysman/lefttreeuserlist!shiftNoList.action",
				fields : ['VALUE', 'DISP'],
				root : "valueList"
			});

	var shiftNoCombox = new Ext.ux.form.LovCombo({
				width : 300,
				hideOnSelect : false,
				displayField : 'DISP',
				valueField : 'VALUE',
				maxHeight : 200,
				store : shiftNoStore,
				mode : 'remote',
				fieldLabel : '生产班次',
				triggerAction : 'all',
				emptyText : '--请选择生产班次--'

			});

	var lodeAttrStore = new Ext.data.JsonStore({
				url : "./sysman/lefttreeuserlist!lodeAttrList.action",
				fields : ['VALUE', 'DISP'],
				root : "valueList"
			});

	var lodeAttrCombox = new Ext.ux.form.LovCombo({
				width : 300,
				hideOnSelect : false,
				displayField : 'DISP',
				valueField : 'VALUE',
				maxHeight : 200,
				store : lodeAttrStore,
				mode : 'remote',
				fieldLabel : '负荷性质',
				triggerAction : 'all',
				emptyText : '--请选择负荷性质--'

			});

	var voltCodeStore = new Ext.data.JsonStore({
				url : "./sysman/lefttreeuserlist!voltCodeList.action",
				fields : ['value', 'disp'],
				root : "valueList"
			});

	var voltCodeCombox = new Ext.ux.form.LovCombo({
				width : 300,
				hideOnSelect : false,
				displayField : 'DISP',
				valueField : 'VALUE',
				maxHeight : 200,
				store : voltCodeStore,
				mode : 'remote',
				fieldLabel : '供电电压',
				triggerAction : 'all',
				emptyText : '--请选择供电电压--'

			});

	var statusCodeStore = new Ext.data.JsonStore({
				url : "./sysman/lefttreeuserlist!statusCodeList.action",
				fields : ['value', 'disp'],
				root : "valueList"
			});

	var statusCodeCombox = new Ext.ux.form.LovCombo({
				width : 300,
				hideOnSelect : false,
				displayField : 'DISP',
				valueField : 'VALUE',
				maxHeight : 200,
				store : statusCodeStore,
				mode : 'remote',
				fieldLabel : '用户状态',
				triggerAction : 'all',
				emptyText : '--请选择用户状态--'

			});

	// 生产厂家 完成
	var factoryStore = new Ext.data.JsonStore({
				url : "baseapp/deviceMonitor!getDeviceFactory.action",
				fields : ['factoryCode', 'factoryName'],
				root : 'vwFactoryList'
			});

	var factoryCombox = new Ext.ux.form.LovCombo({
				width : 300,
				hideOnSelect : false,
				displayField : 'factoryName',
				valueField : 'factoryCode',
				maxHeight : 200,
				store : factoryStore,
				mode : 'remote',
				fieldLabel : '生产厂家',
				triggerAction : 'all',
				emptyText : '--请选择生产厂家--'

			});

	var tmnlTypeStore = new Ext.data.JsonStore({
				url : "./sysman/lefttreeuserlist!tmnlTypeList.action",
				fields : ['VALUE', 'DISP'],
				root : "valueList"
			});

	var tmnlTypeCombox = new Ext.ux.form.LovCombo({
				width : 300,
				hideOnSelect : false,
				displayField : 'DISP',
				valueField : 'VALUE',
				maxHeight : 200,
				store : tmnlTypeStore,
				mode : 'remote',
				fieldLabel : '终端类型',
				triggerAction : 'all',
				emptyText : '--请选择终端类型--'

			});

	var collTypeStore = new Ext.data.JsonStore({
				url : "./sysman/lefttreeuserlist!collTypeList.action",
				fields : ['VALUE', 'DISP'],
				root : "valueList"
			});

	var collTypeCombox = new Ext.ux.form.LovCombo({
				width : 300,
				hideOnSelect : false,
				displayField : 'DISP',
				valueField : 'VALUE',
				maxHeight : 200,
				store : collTypeStore,
				mode : 'remote',
				fieldLabel : '采集方式',
				triggerAction : 'all',
				emptyText : '--请选择采集方式--'

			});

	var editors = {
		'CONS.ORG_NO' : orgNoCombox, // 下拉
		'CONS.CONS_NO' : defaultEditors['string'], // 输入
		'CONS.CONS_NAME' : defaultEditors['string'], // 输入
		'CONS.SUBS_ID' : subIdCombox, // 下拉
		'CONS.LINE_ID' : lineIdCombox, // 下拉
		'CONS.TG_ID' : defaultEditors['string'], // 输入
		'CONS.ELEC_ADDR' : defaultEditors['string'], // 输入
		'CONS.TRADE_CODE' : tradeCodeCombox, // 下拉
		'CONS.CONS_TYPE' : consTypeCombox, // 下拉
		'CONS.ELEC_TYPE_CODE' : elecTypeCombox, // 下拉
		'CONS.CAP_GRADE_NO' : capGradeCombox, // 下拉
		'CONS.SHIFT_NO' : shiftNoCombox, // 下拉
		'CONS.LODE_ATTR_CODE' : lodeAttrCombox, // 下拉
		'CONS.VOLT_CODE' : voltCodeCombox, // 下拉
		'CONS.HOLIDAY' : defaultEditors['string'], // 输入
		'CONS.STATUS_CODE' : statusCodeCombox, // 下拉
		'CONS.POWEROFF_CODE' : defaultEditors['string'], // 下拉
		'CONS.MR_SECT_NO' : defaultEditors['string'], // 输入
		'TMNL.TMNL_ASSET_NO' : defaultEditors['string'], // 输入
		'TMNL.TERMINAL_ADDR' : defaultEditors['string'], // 输入
		'TMNL.SIM_NO' : defaultEditors['string'], // 输入
		'TMNL.FACTORY_CODE' : factoryCombox, // 下拉
		'TMNL.TERMINAL_TYPE_CODE' : tmnlTypeCombox, // 下拉
		'TMNL.COLL_MODE' : collTypeCombox, // 下拉
		'TMNL.PROTOCOL_CODE' : protocolCombox, // 下拉
		'TMNL.RUN_DATE' : defaultEditors['date'], // 日期选择
		'TMNL.PS_ENSURE_FLAG' : defaultEditors['sffield'], // 下拉
		'TMNL.AC_SAMPLING_FLAG' : defaultEditors['sffield'], // 下拉
		'TMNL.ELIMINATE_FLAG' : defaultEditors['sffield']
		// 下拉

	};

	Ext.ux.MultiEditColumnModel = Ext.extend(Ext.grid.ColumnModel, {
				dateFormat : 'Y-m-d',

				getCellEditor : function(colIndex, rowIndex) {
					// var a = this.config[colIndex];
					// var b = a.getCellEditor(rowIndex);
					if (colIndex !== 2)
						return this.config[colIndex].getCellEditor(rowIndex);
					var ed = fieldstore.getAt(rowIndex).get('fieldname');
					var editor = editors[ed];
					if (editor) {
						if (!editor.startEdit) {
							if (!editor.gridEditor) {
								editor.gridEditor = new Ext.grid.GridEditor(editor);
							}
							return editor.gridEditor;
						} else if (editor.startEdit) {
							return editor;
						}
					}
					return null;

					/*
					 * var rec = fieldstore.getAt(i); var fname =
					 * rec.get('fieldname'); var cc = rec.get('condition'); var
					 * fvalue = rec.get('fieldvalue')
					 */

				}

			});

	// 条件组合
	var fieldobj;
	var fielddata = {
		"list" : [{
					"fieldname" : "",
					"condition" : "=",
					"fieldvalue" : ""
				}]
	};

	var afield = Ext.data.Record.create([{
				name : 'fieldname',
				mapping : 'fieldname'
			}, {
				name : 'condition',
				mapping : 'condition'
			}, {
				name : 'fieldvalue',
				mapping : 'fieldvalue'

			}]);
	//
	// var newField = new afield({
	// "fieldname" : "名称",
	// "fieldvalue" : "name",
	// "fieldtype" : "string"
	// });

	var fieldstore = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(fielddata),
				reader : new Ext.data.JsonReader({
							root : 'list'
						}, [{
									name : 'fieldname'
								}, {
									name : 'condition'
								}, {
									name : 'fieldvalue'
								}])
			});

	fieldstore.loadData(fielddata);
	// var confields;
	// var constr =
	// '[[\'=\',\'=\'],[\'>\',\'>\'],[\'<\',\'<\'],[\'>=\',\'>=\'],[\'<=\',\'<=\'],[\'in\',\'包括\'],[\'like\',\'包含\']]';
	//
	// eval('confields=' + constr);

	var confields = [['=', '等于'], ['>', '大于'], ['<', '小于'], ['>=', '大于等于'],
			['<=', '小于等于'], ['in', '包括'], ['like', '包含']];

	var constore = new Ext.data.SimpleStore({
				fields : ['value', 'disp'],
				data : confields
			});

	var concombo = new Ext.form.ComboBox({
				store : constore,
				// id : 'comboField',
				displayField : 'disp',
				valueField : 'value',
				typeAhead : true,
				editable : false,
				mode : 'local',
				forceSelection : true,
				width : 80,
				triggerAction : 'all',
				emptyText : '请选择条件...',
				selectOnFocus : true
			});

	var seleFields = [["CONS.ORG_NO", "供电单位"], ["CONS.CONS_NO", "用户编号"],
			["CONS.CONS_NAME", "用户名称"], ["CONS.SUBS_ID", "变电站名称"],
			["CONS.LINE_ID", "供电线路"], ["CONS.TG_ID", "台区名称"],
			["CONS.ELEC_ADDR", "用电地址"], ["CONS.TRADE_CODE", "所属行业"],
			["CONS.CONS_TYPE", "用户类别"], ["CONS.ELEC_TYPE_CODE", "用电类别"],
			["CONS.CAP_GRADE_NO", "容量等级"], ["CONS.SHIFT_NO", "生产班次"],
			["CONS.LODE_ATTR_CODE", "负荷性质"], ["CONS.VOLT_CODE", "供电电压"],
			["CONS.HOLIDAY", "厂休日"], ["CONS.STATUS_CODE", "用户状态"],
			["CONS.POWEROFF_CODE", "停电标志"], ["CONS.MR_SECT_NO", "抄表段编号"],
			["TMNL.TMNL_ASSET_NO", "终端资产编号"], ["TMNL.TERMINAL_ADDR", "终端逻辑地址"],
			["TMNL.SIM_NO", "SIM卡号"], ["TMNL.FACTORY_CODE", "终端生产厂家"],
			["TMNL.TERMINAL_TYPE_CODE", "终端类型"], ["TMNL.COLL_MODE", "采集方式"],
			["TMNL.PROTOCOL_CODE", "规约类型"], ["TMNL.RUN_DATE", "投运日期"],
			["TMNL.PS_ENSURE_FLAG", "是否保电"],
			["TMNL.AC_SAMPLING_FLAG", "是否交流采样"],
			["TMNL.ELIMINATE_FLAG", "是否剔除"]];
	var store = new Ext.data.SimpleStore({
				fields : ['value', 'disp'],
				data : seleFields
			});

	var combo = new Ext.form.ComboBox({
				store : store,
				// id : 'comboField',
				displayField : 'disp',
				valueField : 'value',
				typeAhead : true,
				editable : false,
				mode : 'local',
				forceSelection : true,
				width : 80,
				triggerAction : 'all',
				emptyText : '请选择字段...',
				selectOnFocus : true
			});

	var renderFieldsName = function(val) {
		var rec = store.getAt(store.find('value', val));
		if (!rec)
			return val;
		var rtn = rec.get('disp');
		return rtn ? rtn : val;
	}

	var renderCondition = function(val) {
		var rec = constore.getAt(constore.find('value', val));
		if (!rec)
			return val;
		var rtn = rec.get('disp');
		return rtn ? rtn : val;
	}

	var cm = new Ext.ux.MultiEditColumnModel([{
				header : '字段名称',
				dataIndex : 'fieldname',
				allowBlank : false,
				editor : combo,
				blankText : '字段名不能为空！',
				renderer : renderFieldsName
			}, {
				header : "字段条件",
				dataIndex : 'condition',
				editor : concombo,
				allowBlank : false,
				blankText : '字段条件不能为空！',
				renderer : renderCondition
			}, {
				header : "条件值",
				dataIndex : 'fieldvalue',
				allowBlank : false,
				editor : new Ext.form.TextField(),
				blankText : '条件值不能为空！',
				renderer : function(val, meta, rec, row, col, store) {
					var rv = val;
					if (Ext.isDate(val)) {
						rv = val.dateFormat('Y-m-d');
					} else if (typeof val == 'boolean') {
						rv = val ? 'true' : 'false';
					}

					var ed = fieldstore.getAt(row).get('fieldname');
					var editor = editors[ed];
					if (editor) {

						editor.setValue(val);
						if (Ext.isFunction(editor.getCheckedDisplay))
							rv = editor.getCheckedDisplay();
					}

					return Ext.util.Format.htmlEncode(rv);

				}
			}]);

	var congrid = new Ext.grid.EditorGridPanel({
				border : false,
				store : fieldstore,
				clicksToEdit : 1,
				frame : true,
				buttonAlign : "center",
				cm : cm,
				tbar : [{
							text : '增加行',
							handler : function() {

								fieldstore.add(new afield({
											"fieldname" : "",
											"condition" : "=",
											"fieldvalue" : ""
										}));
							}
						}, {
							text : '删除末行',
							handler : function() {
								fieldstore.removeAt(fieldstore.getCount() - 1);
							}
						}, {
							text : '清除条件',
							handler : function() {
								fieldstore.removeAll();
							}
						}],
				buttons : [{
							text : '查询',
							handler : queryListUser
						}, {
							text : '关闭',
							handler : function() {
								Ext.getCmp('advQueryWindow').close();
							}
						}]
			});

	congrid.on('afteredit', function(o) {
				if (o.field == 'fieldname') {
					o.record.set('fieldvalue', '');
					o.record.commit();
				}
			});

	function queryListUser() {
		var con = '';
		for (var i = 0; i < fieldstore.getCount(); i++) {
			var rec = fieldstore.getAt(i);
			var fname = rec.get('fieldname');
			var cc = rec.get('condition');
			var fvalue = rec.get('fieldvalue');

			// 由于字段没有储存数据类型，只能以输入值做为判断依据，在某些特殊情况下可能产生错误
			if (Ext.isEmpty(fvalue)) {
				Ext.MessageBox.alert('提示', '第' + (i + 1) + '行条件值不能为空！');
				return;
			}

			if (Ext.isDate(fvalue))
				con += ' and trunc(' + fname + ') ';
			else
				con += ' and ' + fname + ' ';

			con += cc + ' ';

			if (cc == "like" && Ext.isDate(fvalue)) {
				Ext.MessageBox.alert('提示', '第' + (i + 1) + '行日期类型数据不能使用包含条件！');
				return;
			}

			if (cc == "in" && Ext.isDate(fvalue)) {
				Ext.MessageBox.alert('提示', '第' + (i + 1) + '行日期类型数据不能使用包括条件！');
				return;
			}

			if (cc !== "in" && !Ext.isDate(fvalue) && fvalue.indexOf(",") > 0) {
				Ext.MessageBox.alert('提示', '第' + (i + 1) + '行包含","的只能使用包括条件！');
				return;
			}

			if (cc == 'like')
				fvalue = ' \'%' + fvalue + '%\' ';
			else if (cc == 'in') {
				// 拆分字符串并组合条件值
				var b = fvalue.split(",");
				var a = ' ';
				a += '\'' + b[0].trim() + '\'';
				for (var i = 1; i < b.length; i++) {
					a += ',\'' + b[i].trim() + '\'';
				}
				fvalue = ' (' + a + ' )';

			} else {
				if (Ext.isDate(fvalue)) {
					fvalue = ' to_date(\'' + fvalue.format('Y-m-d')
							+ '\',\'yyyy-mm-dd\') ';
				} else
					fvalue = ' \'' + fvalue + '\' ';
			}
			con += fvalue;

		}

		// alert(con);
		var leftUserGrid = Ext.getCmp('leftUserGrid');
		var store = leftUserGrid.getStore();
		store.proxy.conn.url = './sysman/lefttreeuserlist!userlist.action';
		store.baseParams = {
			con : con
		};
		store.load();

		// Ext.getCmp('advQueryWindow').close();
	}

	var panel = new Ext.Panel({
				layout : 'fit',
				border : false,
				items : [congrid]
			});
	var parent = Ext.getCmp('advQueryWindow');
	parent.insert(0, panel);
	parent.doLayout();

});
