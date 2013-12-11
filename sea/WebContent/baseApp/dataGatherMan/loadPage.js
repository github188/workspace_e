/** ***主体数据结构*** */
/*******************************************************************************
 * 
 * 使用方法 使用下面的方法新建store，只用填写dataKey和fields new Ext.ux.LocalStore({ dataKey:"key",
 * fields:[] });
 * 
 * 此实现会丢失一些store的高级应用,如commit方法可能不准确。 不要指定scope <br>
 * 前台分页的api removeDatas 未来记录状态 在后面加如下代码
 * 你的LocalStore.setGrid(显示的grid);见datafetch.js<br>
 * 约1187行 代码storeTerminalGrid.setGrid(terminalGrid);
 * 
 * <br>
 * 可在分页后记录状态 <br>
 * isAllSelect 判断是不是所有的项都被选择了 <br>
 * addDatas 增加数据<br>
 * getSelectKeys 得到所有项的key<br>
 * clearAll 清除所有的数据<br>
 * getAllKeys 得到所有的key<br>
 * clearAllSelect 清空所有的选择的项 getAllKeys 得到所有的key<br>
 * eachSelect(rs,回调) 遍历rs中的所有的选择的项<br>
 * eachAll 遍历所有的项<br>
 * getBaseData 得到所有的数据<br>
 * setAllSelect 设置是否全选<br>
 * 在调用store的原始的api 如add remove 时， 这个store的原始的数据也会跟着发生变化 下面有详细注释
 ******************************************************************************/
// Ext.util.Observable;
// Ext.util.MixedCollection;
// Ext.XTemplate;
// Ext.form.DateField;
// Ext.form.ComboBox;
Ext.namespace("Ext.ux");
Ext.ux.LocalStore = Ext.extend(Ext.data.JsonStore, {
	grid : null,
	// 是否自动增长
	autoId : false,
	// 自动增长的值
	identity : 0,
	mem : false,// 是否记录状态
	/***************************************************************************
	 * **@param 设置关联的grid 通过设置这个可以对完全的接管<br>
	 * grid中的Ext.grid.RowSelectionModel 中的事件<br>
	 * 本实现中只对checkboxrowselectionmodel进行处理
	 * 
	 * @return 依然返回这个store
	 **************************************************************************/
	setGrid : function(g) {
		if (arguments.length == 0) {
			return !!alert("参数不能为空");
		}
		if (!(g instanceof Ext.grid.GridPanel)) {
			return !!alert("请确保setGrid的参数为GridPanel的实例");
		}
		this.grid = g;
		return this;
	},
	constructor : function(config) {
		this.mm = {};
		this.mainData = {
			baseData : {},
			/** *全选标识* */
			allSelect : true,
			/** ***记录所有的选择的元素***** */
			selectKeys : [],
			// 使用json来记录选中的key
			selectJsonKeys : {},
			count : 0,
			start : 0,
			limit : 50,
			showData : []
		};
		this.dataKey = config.dataKey;
		if (!config.fields) {
			config.fields = [];
		}
		config.autoId && (this.autoId = config.autoId);
		this.autoId
				&& (this.dataKey = "autoId" && (config.dataKey = "autoId")
						&& config.fields.push("autoId"));
		this.mem = config.mem || false;
		this.defaults = config.defaults || this.defaults;

		config.idProperty = this.dataKey;
		config.totalProperty = "count";
		config.root = "showData";
		config.proxy = new Ext.data.MemoryProxy(this.mainData);
		Ext.ux.LocalStore.superclass.constructor.call(this, config);

	},
	defaults : {
		start : 0,
		limit : 50
	},// 记录默认的一些值
	dataKey : "",
	/** *处理选择的方式的开关,为true按照已经选择的列表进行显示选择的方式**** */
	selectConfig : true,
	// mm : {},// 记录分页的start和limit
	listeners : {
		"beforeload" : function(s, options) {
			// 对结果进行处理 ，使分页的各个因素正常
			var bData = this.mainData;
			options.params.start = options.params.start || this.defaults.start;
			options.params.limit = options.params.limit || this.defaults.limit;
			var start = options.params.start;
			this.mm.start = start;
			this.mm.limit = options.params.limit;
			bData.showData = [];
			var temp = [];
			var end = options.params.start + options.params.limit;
			var allCount = this.getAllCount();
			var index = 0;
			if (allCount == 0) {
				bData.showData = [];
				options.params.start = 0;
				this.mm.start = 0;
				bData.count = 0;
				return;
			}
			if (start > allCount - 1) {
				options.params.start = start = allCount - options.params.limit;
				end = options.params.start + options.params.limit;
				this.mm.start = start;
				Ext.iterate(bData.baseData, function(key, value) {
							if (index >= start && index < end) {
								bData.showData.push(value);
							}
							index++;
						});
			} else {
				Ext.iterate(bData.baseData, function(key, value) {
							if (index >= start && index < end) {
								bData.showData.push(value);
							}
							index++;
						});
			}
			bData.count = allCount;
			/** ***如果设置了grid，接管grid的checkboxrowselectionmodel的事件***** */
			var sm = this.grid && this.grid.getSelectionModel();
			if (this.grid && sm instanceof Ext.grid.CheckboxSelectionModel) {
				this.mem && sm.on("rowselect", function(sm, idx, r) {

							this.addSelect(r.get(this.dataKey));
						}, this);
				this.mem && sm.on("rowdeselect", function(sm, idx, r) {
							this.removeSelect(r.get(this.dataKey))
							this.mainData.allSelect = false;
						}, this);
				var j = 0;
				var me = this;
				// 为了使callback在外部修改不影响load的逻辑，可以使用方法拦截来使callback也能修改
				options.callback = options.callback || function() {
				};
				options.callback = options.callback.createInterceptor(
						function() {
							if (!me.mem) {
								sm.selectAll();
								return;
							}
							if (me.mainData.allSelect) {
								sm.selectAll();
								return;
							}
							Ext.each(bData.showData, function(d) {
										if (me.mainData.selectJsonKeys[d[me.dataKey]]) {
											sm.selectRow(j, true);
										}
										j++;
									}, me);
						});
			}
		},
		/** ***下面的事件确保对于store的元素的api进行支持*** */
		"update" : function(m, r) {
			var key = r.get(this.dataKey);
			this.mainData.baseData[key] = r.data;
		},
		"add" : function(s, rs) {
			for (var i = 0; i < rs.length; i++) {
				var r = rs[i];
				var key = r.get(this.dataKey);
				this.mainData.baseData[key] = r.data;
				this.mem && this.addSelect(key);
			}
		},
		"remove" : function(s, r, i) {
			var key = r.get(this.dataKey);
			this.mem && this.removeSelect(key);
			delete this.mainData.baseData[key];
		}
	},
	/** **清空所有的东西，并且清空所有的选择的项**** */
	clearAll : function(load) {
		var bData = this.mainData;
		bData.baseData = {};
		bData.count = 0;
		bData.selectJsonKeys = {};
		// bData.allSelect = false;
		bData.start = 0;
		bData.showData = [];
		if (load === false) {
		} else {
			this.load();
		}
	},
	getAllCount : function() {
		var bData = this.mainData;
		var index = 0;
		Ext.iterate(bData.baseData, function(key, value) {
					index++;
				});
		return index;
	},

	/***************************************************************************
	 * 
	 * 向store里面增加元素 datas 可以接受对象数组<br>
	 * 还可以接受record数组
	 * 
	 * @param load
	 *            是否在添加完数据后reload 这个Store 默认为加载
	 **************************************************************************/
	addDatas : function(datas, load) {
		var bData = this.mainData;
		if (!datas) {
			return;
		}
		if (!Ext.isArray(datas)) {
			datas = [datas];
		}
		if (!datas[0]) {
			return;
		}
		// 为了提高效率，使用鸭子法则进行简单判断,鸭子样本
		var duck = datas[0];
		if (duck instanceof Ext.data.Record) {
			for (var i = 0; i < datas.length; i++) {
				var t = datas[i];
				this.autoId && t.set(this.dataKey, this.identity++);
				var key = t.get(this.dataKey);
				(!bData.baseData[key]) && (bData.baseData[key] = t.data);
				this.mem && this.addSelect(key);
			}
		} else {
			for (var i = 0; i < datas.length; i++) {
				var t = datas[i];
				this.autoId && (t[this.dataKey] = this.identity++)
				var key = t[this.dataKey];
				(!bData.baseData[key]) && (bData.baseData[key] = t);
				this.mem && this.addSelect(key);
			}
		}

		if (load === false) {
			return;
		}
		this.load({
					callback : function() {
						this.grid && this.grid.getGridEl().unmask();
					}
				});
	},
	/***************************************************************************
	 * 向store里面删除元素 datas可以为3中结构， 1字符串数组 <br>
	 * 2 datas为字符串,删除字符串所对应的元素 3 record 数组 <br>
	 * 4 对象数组 <br>
	 * 
	 * @param load
	 *            是否load
	 **************************************************************************/
	removeDatas : function(datas, load) {
		var bData = this.mainData.baseData;
		if (typeof datas == "string") {
			delete bData[datas];
			this.mem && this.removeSelect(datas);
		} else {
			var flat = 0;
			if (!Ext.isArray(datas)) {
				datas = [datas];
			}
			if (!datas[0]) {
				return;
			}
			for (var i = 0; i < datas.length; i++) {
				var t = datas[i];
				if (flat) {
					var key = t.get(this.dataKey);
					this.mem && this.removeSelect(key);
					delete bData[key];
				}
				if (t instanceof Ext.data.Record && flat == 0) {
					flat = 1;
					i--;
					continue;
				}
				if (typeof t == "string") {
					this.mem && this.removeSelect(t);
					delete bData[t];
				} else {
					var key = t[this.dataKey];
					this.mem && this.removeSelect(key);
					delete bData[key];
				}
			}
		}
		if (load === false) {
			return;
		}

		this.load({
					params : {
						start : this.mm.start,
						limit : this.mm.limit
					}
				});
	},
	/***************************************************************************
	 * 
	 * 得到所有的key
	 **************************************************************************/
	getAllKeys : function() {
		var bData = this.mainData.baseData;
		var rs = [];
		for (var key in bData) {
			rs.push(key);
		}
		return rs;
	},
	/** ****判断当前的store是不是处于全选的状态****** */
	isAllSelect : function() {
		if (this.mainData.allSelect) {
			return true;
		}
		this.mainData.allSelect = true;
		Ext.iterate(this.mainData.baseData, function(k, v) {
					if (!this.mainData.selectJsonKeys[k]) {
						this.mainData.allSelect = false;
						return false;
					}
				}, this);
		return this.mainData.allSelect;
	},
	/***************************************************************************
	 * 遍历所有的用户
	 * 
	 * @param e
	 *            回调 e 的参数 function(key,obj){} key 主键的值 obj 主键所对应的对象
	 **************************************************************************/
	eachAll : function(e) {
		if (typeof e != "function") {
			alert("参数必须为函数");
			return;
		}
		var me = this;
		Ext.iterate(this.mainData.baseData, function(key, obj) {
					e.createDelegate(me, [key, obj])();
				})
	},
	/***************************************************************************
	 * 遍历所有的选择的用户 当rs为一个record数组的时候，通过第二个函数回调遍历record中的每一项<br>
	 * 当rs为一个回调行数时，这个行数遍历所有选择的项
	 * 
	 * @param rs
	 *            record的数组或者是一个回调
	 * @param e
	 *            回调 e 的参数 function(key,obj){} key为主键的键值 右边为主键所对应的对象
	 **************************************************************************/
	eachSelect : function(rs, e) {
		var me = this;
		return (Ext.isFunction(rs) ? function() {
			Ext.iterate(me.mainData.selectJsonKeys, function(k) {
						rs.createDelegate(me, [k, me.mainData.baseData[k]])();
					})
		} : function() {
			if (!Ext.isFunction(e)) {
				alert("第二一个行数必须为Function");
			}
			Ext.each(rs, function(r) {
						var key;
						if (typeof r == "string") {
							e
									.createDelegate(me, key,
											me.mainData.baseData[key])();
						} else if (r instanceof Ext.data.Record) {
							key = r.get(me.dataKey);
						} else {
							key = r[me.dataKey];
						}
						if (key) {
							e.createDelegate(me, key, r)();
						}
					}, me);
		})()
	},
	/** ***得到所有的数据的引用*** */
	getBaseData : function() {
		return this.mainData.baseData;
	},
	/** ******与选择有关操作的api******* */
	/** ***得到所有的被选择的项的key******** */
	getSelectKeys : function() {
		var rs = [];
		if (this.mainData.allSelect) {
			Ext.iterate(this.mainData.baseData, function(k, v) {
						rs.push(k);
					})
			return rs;
		}
		Ext.iterate(this.mainData.selectJsonKeys, function(k) {
					rs.push(k);
				});
		return rs;
	},
	/**
	 * ***将某个项或者某个项的列表加入都选择的项中<br>
	 * 如果有添加，如果没有不添加
	 * 
	 * @param key
	 *            key可以是一个字符串，一个字符串数组，或者是一个record数组,一个对象数组 ****
	 */
	addSelect : function(key) {
		if (typeof key == "string" || typeof key == "number") {
			this.mainData.selectJsonKeys[key] = true;
			return;
		}
		if (Ext.isArray(key)) {
			if (key.length == 0) {
				return;
			} else if (typeof key[0] == "string") {
				Ext.each(key, function(k) {
							this.mainData.selectJsonKeys[k] = true;
						}, this);
			} else if (typeof key[0] instanceof Ext.data.Record) {
				Ext.each(key, function(r) {
							this.mainData.selectJsonKeys[r.get(this.dataKey)] = true;
						}, this);
			} else {
				Ext.each(key, function(r) {
							this.mainData.selectJsonKeys[r[this.dataKey]] = true;
						}, this);
			}
		}
	},
	/***************************************************************************
	 * ****从当期选择中去掉选择到的key<br>
	 * 
	 * @param key
	 *            key可以是一个字符串，一个字符串数组，或者是一个record数组,对象数组 ****
	 **************************************************************************/
	removeSelect : function(key) {
		if (typeof key == "string" || typeof key == "number") {
			delete this.mainData.selectJsonKeys[key];
		} else if (Ext.isArray(key)) {
			if (key.length == 0) {
				return;
			} else if (typeof key[0] == "string") {
				Ext.each(key, function(k) {
							delete this.mainData.selectJsonKeys[k];
						}, this);
			} else if (key[0] instanceof Ext.data.Record) {
				Ext.each(key, function(r) {
							delete this.mainData.selectJsonKeys[r
									.get(this.dataKey)];
						}, this);
			} else {
				Ext.each(key, function(r) {
							delete this.mainData.selectJsonKeys[r[this.dataKey]]
						}, this);
			}
		}

	},
	/***************************************************************************
	 * **设置为全部选择,可以在外部设个变量来标识全选， <br>
	 * 这里提供了一个内部变量 <br>
	 * 免得在外部在进行设置
	 * 
	 * @param s
	 *            设为true为全选， 设置为false 为不全选
	 **************************************************************************/
	setAllSelect : function(s) {
		this.mainData.allSelect = !!s;

	},
	/** **清空所有选择的项的状态*** */
	clearAllSelect : function() {
		this.mainData.selectJsonKeys = {};
		this.mainData.allSelect = false;

	},
	/** *加载当前页面** */
	loadCurrent : function() {
		this.load({
					params : {
						start : this.mm.start,
						limit : this.mm.limit
					}
				});
	},
	/** ***工具方法**** */
	/** ****只有没有的时候才push key***** */
	// private
	/***************************************************************************
	 * 如果key在数组中存在，不插入 private方法 在外部不调用
	 * 
	 * @param key
	 *            key为string
	 **************************************************************************/
	pushKey : function(arr, key) {
		if (arr.indexOf(key) < 0) {
			arr.push(key);
		}
	},
	/** **去掉一个数组中所有的key，*** */
	// private
	removeAllKey : function(arr, key) {
		while (arr.indexOf(key) > 0) {
			arr.remove(key);
		}
	},
	// private 删除重复的key
	deleteEchoKeys : function() {
		var m = {};
		var keys = this.mainData.selectKeys;
		Ext.each(keys, function(k) {
					m[k] = null;
				});
		var rs = [];
		Ext.iterate(m, function(k) {
					rs.push(k);
				});
		return rs;
	}
		// ,
		// /** *****从key中****** */
		// /** **数组存取结构** */
		// mainData : {
		// baseData : {},
		// /** *全选标识* */
		// allSelect : true,
		// /** ***记录所有的选择的元素***** */
		// selectKeys : [],
		// // 使用json来记录选中的key
		// selectJsonKeys : {},
		// count : 0,
		// start : 0,
		// limit : 50,
		// showData : []
		// }
});

/** **重写序号生成器,先new store 再在这个config中设置*** */
Ext.ux.AutoRowNumberer = Ext.extend(Ext.grid.RowNumberer, {
			constructor : function(config) {
				Ext.ux.AutoRowNumberer.superclass.constructor
						.call(this, config);
				if (!config.store) {
					alert("store必须设置且不能为空");
				}
				this.store = config.store;
			},
			renderer : function(v, p, record, rowIndex) {
				if (this.rowspan) {
					p.cellAttr = 'rowspan="' + this.rowspan + '"';
				}

				this.store.lastOptions.params = storeTerminalGrid.lastOptions.params
						|| {
							start : 0
						};
				return this.store.lastOptions.params.start + rowIndex + 1;
			}
		});

/** *自定义一个月份选择控件* */
Ext.ux.MonthField = Ext.extend(Ext.form.DateField, {
			format : "Y-m",
			// constructor : function(config) {
			//
			// },
			onTriggerClick : function() {
				if (this.disabled) {
					return;
				}
				var me = this;
				if (this.menu == null) {
					this.menu = new Ext.menu.DateMenu({
								hideOnClick : false
							});
					Ext.apply(this.menu.picker, {
								onMonthDblClick : function(e, t) {
									e.stopEvent();
									var el = new Ext.Element(t), pn;
									if ((pn = el.up('td.x-date-mp-month', 2))) {
										this.update(new Date(this.mpSelYear,
												pn.dom.xmonth,
												(this.activeDate || this.value)
														.getDate()));
										var d = new Date(this.mpSelYear,
												this.mpSelMonth,
												(this.activeDate || this.value)
														.getDate());
										me.setValue(d);
										me.menu.hide();
									} else if ((pn = el.up('td.x-date-mp-year',
											2))) {
										this.update(new Date(pn.dom.xyear,
												this.mpSelMonth,
												(this.activeDate || this.value)
														.getDate()));
										var d = new Date(this.mpSelYear,
												this.mpSelMonth,
												(this.activeDate || this.value)
														.getDate());
										me.setValue(d);
										me.menu.hide();
									}
								},
								onMonthClick : function(e, t) {
									e.stopEvent();
									var el = new Ext.Element(t), pn;
									if (el.is('button.x-date-mp-cancel')) {
										me.menu.hide();
									} else if (el.is('button.x-date-mp-ok')) {
										var d = new Date(this.mpSelYear,
												this.mpSelMonth,
												(this.activeDate || this.value)
														.getDate());
										if (d.getMonth() != this.mpSelMonth) {
											// 'fix' the JS rolling date
											// conversion if needed
											d = new Date(this.mpSelYear,
													this.mpSelMonth, 1)
													.getLastDateOfMonth();
										}
										me.setValue(d);
										me.menu.hide();
									} else if ((pn = el.up(
											'td.x-date-mp-month', 2))) {
										this.mpMonths
												.removeClass('x-date-mp-sel');
										pn.addClass('x-date-mp-sel');
										this.mpSelMonth = pn.dom.xmonth;
									} else if ((pn = el.up('td.x-date-mp-year',
											2))) {
										this.mpYears
												.removeClass('x-date-mp-sel');
										pn.addClass('x-date-mp-sel');
										this.mpSelYear = pn.dom.xyear;
									} else if (el.is('a.x-date-mp-prev')) {
										this.updateMPYear(this.mpyear - 10);
									} else if (el.is('a.x-date-mp-next')) {
										this.updateMPYear(this.mpyear + 10);
									}
								}
							});
				}
				this.onFocus();
				Ext.apply(this.menu.picker, {
							minDate : this.minValue,
							maxDate : this.maxValue,
							disabledDatesRE : this.disabledDatesRE,
							disabledDatesText : this.disabledDatesText,
							disabledDays : this.disabledDays,
							disabledDaysText : this.disabledDaysText,
							format : this.format,
							showToday : this.showToday,
							minText : String.format(this.minText, this
											.formatDate(this.minValue)),
							maxText : String.format(this.maxText, this
											.formatDate(this.maxValue))
						});
				this.menu.picker.setValue(this.getValue() || new Date());
				this.menu.show(this.el, "tl-bl?");
				this.menuEvents('on');
				this.menu.picker.showMonthPicker();
			}
		});
Ext.reg('monthfield', Ext.ux.MonthField);

var tg = tg || {};
// 生成一个选中供电单位的窗口
tg.choiceOrgWin = function() {
	var treeLoader = new Ext.tree.TreeLoader({
				dataUrl : './qrystat/losePowerMan!netTreeClick.action'
			});
	// 区域树
	var rootNode = new Ext.tree.AsyncTreeNode({
				id : 'area_root',
				text : '区域导航',
				iconCls : 'net-02'
			});
	Ext.apply(treeLoader, {
				processResponse : function(response, node, callback) {
					var json = Ext.decode(response.responseText);

					var pi = json.pageInfo;
					if (pi && pi.result.length > 0) {
						var nodes = pi.result;
						node.beginUpdate();
						// node.attributes.type = nodes.type;
						node.attributes.totalCount = pi.totalCount;
						node.attributes.totalPages = pi.totalPages;
						node.attributes.activepage = pi.prePage + 1;
						node.attributes.count = nodes.length;
						for (var i = 0; i < nodes.length; i++) {
							var n = this.createNode(nodes[i]);
							if (n) {
								n.attributes.others = nodes[i].attributes; // 附加其它属性(自定义属性)
								n.attributes.type = nodes[i].type;
								if (n.attributes.attributes.orgType == "06") {
									n.leaf = true;
								}
								// n.attributes.apply(node[i].attributes);
								// //附加其它属性(自定义属性)
								node.appendChild(n);
								// var tb = node.getOwnerTree()
								// .getBottomToolbar();
								// n.on('beforeclick', function(n) {
								// tb.bind(n);
								// return true;
								// });
							}

						}
						node.endUpdate();
					}
					if (typeof callback == "function") {
						callback(this, node);
					}
				}
			});
	var treePanel = new Ext.tree.TreePanel({
		loader : treeLoader,
		border : false,
		animate : true,
		autoScroll : true,
		root : rootNode,
		rootVisible : false
			// ,
			// listeners : {
			// beforeload : function(node) {
			// treeLoader.dataUrl="./qrystat/losePowerMan!netTreeClick.action?node="+node.id;
			// }
			// }
		});

	var win = new Ext.Window({
		title : "请选择供电单位",
		border : false,
		width : 300,
		height : 480,
		modal : true,
		layout : "border",
		items : [{
					region : "center",
					layout : "fit",
					padding : "10px 0 0 0",
					items : [treePanel]
				}],
		buttons : [{
			text : "确定",
			handler : function() {
				var node = treePanel.selModel.getSelectedNode();
				if (!node) {
					return !!Ext.Msg.alert("提示", "请选择节点");
				}
				treePanel.refTextfield
						.setValue(node.attributes.attributes.orgName);
				treePanel.refTextfield.nodeValue = node.attributes.attributes.orgNo;
				treePanel.refTextfield.nodeId = node.id;
				win.close();
			}
		}, {
			text : "清除",
			handler : function() {
				treePanel.refTextfield.setValue("");
				treePanel.refTextfield.nodeValue = "";
				treePanel.refTextfield.nodeId = "";
				win.close();
			}
		}, {
			text : "退出",
			handler : function() {
				win.close();
			}
		}]
	});
	win.show();
	return treePanel;
}
// tg.choiceOrgWin();
// 重写一个单位选择的控件
Ext.ux.orgTextField = Ext.extend(Ext.form.TriggerField, {
			triggerClass : 'x-form-date-trigger',
			getValue : function(show) {
				if (!this.rendered) {
					return this.value;
				}
				if (!show) {
					return this.nodeValue;
				}
				var v = this.el.getValue();
				if (v === this.emptyText || v === undefined) {
					v = '';
				}
				return v;
			},
			setValue : function(v) {
				if (this.emptyText && this.el && !Ext.isEmpty(v)) {
					this.el.removeClass(this.emptyClass);
				}
				Ext.form.TextField.superclass.setValue.apply(this, arguments);
				this.applyEmptyText();
				this.autoSize();
				return this;
			},
			onTriggerClick : function() {
				this.focus();
			},
			listeners : {
				focus : function() {
					var t = tg.choiceOrgWin();
					var me = this;
					t.refTextfield = this;
					t.on("dblclick", function(node) {
								me.setValue(node.attributes.attributes.orgName);
								me.nodeValue = node.attributes.attributes.orgNo;
								me.nodeId = node.id;
								t.ownerCt.ownerCt.close();
								return false;
							});
				}
			}
		});
Ext.reg('orgtextfield', Ext.ux.orgTextField);
