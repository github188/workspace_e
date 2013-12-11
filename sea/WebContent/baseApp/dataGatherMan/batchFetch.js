/*******************************************************************************
 * 2010 8月13日 批量巡测新页面
 * 
 * @author huangxuan
 ******************************************************************************/
Ext.onReady(function() {
	/** *方法区*** */
	/** *组pn* */
	/***************************************************************************
	 * @arr pn 的十进制数组 默认的测量点为0 规则略有不同，其中把第1一位看做是零位,在后台要进行处理(substring(1))
	 **************************************************************************/
	function getPnStr(arr) {

		if (arr.length == 0) {
			return "1";
		}
		var baseStrArr = (function() {
			var arr = new Array(2101);
			for (var i = 0; i < 2101; i++) {
				arr[i] = "0";
			}
			return arr;
		})()
		var mp = {};
		Ext.each(arr, function(d) {
					mp[d] = true;
				});
		for (var i = 0; i < baseStrArr.length; i++) {
			if (mp[i]) {
				baseStrArr[i] = "1";
			}
		}
		return baseStrArr.join("").replace(/0+$/, "");
	}
	/** *将一个id解析为数组** */
	function loadNodeId(id) {
		return id.split("_");
	}
	/** *增加选中的节点*** */
	function addCodes() {
		var cks = configTree.getChecked();
		Ext.each(cks, function(c) {
					var arr = loadNodeId(c.id);
					try {
						selectStore.add(new Ext.data.Record({
									code : arr[1],
									name : c.text
								}, arr[1]));
					} catch (e) {
					}
				});
	}
	function delCodes() {
		selectStore.remove(selectSm.getSelections());
	}
	/** *增加删除的节点*** */
	function filterGui(n, filter) {
		function getArr() {
			return [1, 2, 3];
		}
		var one = filter;
		one.clear();
		var arrCreate = getArr().remove(n);
		one.filterBy(function(tn) {
					var id = tn.id;
					var arr = id.split("_");
					if (arr[0] == "afn" && arr[1] == n) {
						return true;
					} else if (arr[0] == "afn"
							&& (arr[1] == arrCreate[0] || arr[1] == arrCreate[1])) {
						return false;
					}
					return true;
				});
	}
	/** *方法区*** */
	var typeCombox = new Ext.form.ComboBox({
				triggerAction : "all",
				hiddenName : "queryType",
				typeAhead : true,
				itemId : "queryType",
				mode : "local",
				width : 100,
				itemId : "queryType",
				editable : false,
				value : 1,
				displayField : "name",
				valueField : "value",
				store : new Ext.data.ArrayStore({
							fields : ["value", "name"],
							data : [[1, "实时数据"]]
						}),
				selectOnFocus : true,
				listeners : {
					select : function(me, r, index) {
						// var v = r.get("value");
						// filterGui(v);
						// var arr = function() {
						// return [1, 2, 3];
						// };
						// var arr = arr().remove(v);
						// clearNodeCheck(arr[0]);
						// clearNodeCheck(arr[1]);
						// choiceConfig(v);
					}

				}
			});
	// 新建批量巡测任务的页面 组件区
	// grid解锁
	function unlockGrid() {
		userSm.unlock();
		userGrid.onEnable();
		userGrid.getBottomToolbar().enable();
		allCheck.setValue(false);
	}

	// grid解锁
	function lockGrid() {
		userSm.lock();
		userGrid.onDisable();
		userGrid.getBottomToolbar().disable();
	}
	// 全选按钮
	var allCheck = new Ext.form.Checkbox({
				boxLabel : "全选",
				listeners : {
					check : function(c, v) {
						if (v) {
							userStore.setAllSelect(true);
							userSm.selectAll();
							lockGrid();
						} else {
							unlockGrid();
							userStore.clearAllSelect();
							userSm.clearSelections();
						}
					}
				}
			});
	var userStore = new Ext.ux.LocalStore({
				dataKey : "tmnl_asset_no",
				fields : [{
							name : 'org_name'
						}, {
							name : 'cons_name'
						}, {
							name : 'cons_no'
						}, {
							name : 'terminal_addr'
						}, {
							name : 'protocol_name'
						}, {
							name : 'factory_name'
						}, {
							name : 'factory_code'
						}, {
							name : 'tmnl_asset_no'
						}]
			});
	var rowNumUserGrid = new Ext.grid.RowNumberer({
				renderer : function(v, p, record, rowIndex) {
					if (this.rowspan) {
						p.cellAttr = 'rowspan="' + this.rowspan + '"';
					}
					userStore.lastOptions.params = userStore.lastOptions.params
							|| {
								start : 0
							};
					return userStore.lastOptions.params.start + rowIndex + 1;
				}
			});
	var userSm = new Ext.grid.CheckboxSelectionModel();
	var userCm = new Ext.grid.ColumnModel([rowNumUserGrid, userSm, {
		header : '供电单位',
		dataIndex : 'org_name',
		width : 110,
		renderer : function(v) {
			var html = '<span ext:qtitle="供电单位" ext:qtip="' + v + '">' + v
					+ '</span>';
			return html;
		},
		align : 'left'
	}, {
		header : '用户编号',
		dataIndex : 'cons_no',
		width : 110,
		align : 'left',
		renderer : function(v) {
			return '<span style="text-align:left">' + v + '</span>';
		}
	}, {
		header : '用户名称',
		dataIndex : 'cons_name',
		width : 110,
		renderer : function(v) {
			var html = '<span ext:qtitle="用户名称" ext:qtip="' + v + '">' + v
					+ '</span>';
			return html;
		},
		align : 'left'
	}, {
		header : '终端地址',
		width : 110,
		dataIndex : 'terminal_addr',
		align : 'left'
	}, {
		header : '规约类型',
		width : 110,
		dataIndex : 'protocol_name',
		align : 'center'
	}, {
		header : '制造厂商',
		dataIndex : 'factory_name',
		width : 110,
		align : 'left'
	}, {
		header : '原始报文',
		dataIndex : 'message',
		width : 110,
		align : 'center',
		renderer : function(v, m, r) {
			return "<a href='javascript:'onclick='window.origFrameQryShow(\""
					+ r.get('cons_no') + "\",\"" + r.get('cons_name') + "\",\""
					+ r.get('terminal_addr') + "\");" + "'>原始报文</a>";
		}
	}]);
	// 巡测参数配置 组件区
	// 第一行，radio
	var isAutoRadio = new Ext.form.RadioGroup({
				border : false,
				padding : "0 0 0 30px;",
				column : [120, 120],
				defaults : {
					name : "auto"
				},
				items : [{
							boxLabel : "自动启动",
							inputValue : 1,
							checked : true
						}, {
							boxLabel : "手工启动",
							inputValue : 2
						}]

			});
	// 第一行，启动方式
	var firstConfig = new Ext.Panel({
				border : false,
				layout : "column",
				items : [{
							border : false,
							xtype : "label",
							columnWidth : .07,
							style : "text-align:right;font-size:12px",
							text : "启动方式"
						}, {
							border : false,
							layout : "form",
							columnWidth : .40,
							items : [isAutoRadio]
						}]
			});
	// 第二行 组件
	var startDate = new Ext.ux.form.DateTimeField({
				value : new Date().clearTime(),
				editable : false
			});
	var endDate = new Ext.ux.form.DateTimeField({
				value : new Date().add(Date.MONTH, 1).clearTime(),
				editable : false
			});
	// 第二行 起止时间
	var secondConfig = new Ext.Panel({
				border : false,
				layout : "column",
				items : [{
							border : false,
							xtype : "label",
							columnWidth : .07,
							style : "text-align:right;font-size:12px",
							text : "起"
						}, {
							columnWidth : .35,
							padding : "0 0 0 10px",
							border : false,
							layout : "form",
							items : [startDate]
						}, {
							columnWidth : .35,
							layout : "form",
							labelWidth : 20,
							border : false,
							labelAlign : "right",
							items : [endDate]
						}]
			});
	// 第三行 组件
	var timeCombox = new Ext.form.ComboBox({
				triggerAction : "all",
				hiddenName : "time",
				typeAhead : true,
				mode : "local",
				width : 100,
				editable : false,
				value : 1,
				displayField : "name",
				valueField : "value",
				store : new Ext.data.ArrayStore({
							fields : ["value", "name"],
							id : "value",
							data : [[1, "天"], [2, "小时"], [3, "分"]]
						}),
				selectOnFocus : true,
				listeners : {
					select : function(me, r, index) {
						timeLabel.setText(r.get("name"));
					}
				}
			});
	var timeLabel = new Ext.form.Label({
				style : "padding:0 0 0 10px;text-align:right;font-size:12px",
				text : "天"
			});
	var timeConfigText = new Ext.form.NumberField({
				width : 100
			});
	// 第三行 间隔
	var threeConfig = new Ext.Panel({
				border : false,
				layout : "column",
				items : [{
							border : false,
							xtype : "label",
							columnWidth : .07,
							style : "text-align:right;font-size:12px",
							text : "时间间隔"
						}, {
							border : false,
							layout : "form",
							columnWidth : .35,
							padding : "0 0 0 10px",
							items : [timeCombox]
						}, {
							border : false,
							layout : "form",
							labelAlign : "right",
							labelWidth : 20,
							columnWidth : .35,
							items : [timeConfigText]
						}, timeLabel]
			});
	// 第四行 终端对时设置 组件区
	var oneColumn = new Ext.form.CheckboxGroup({
				border : false,
				columns : 1,
				columnWidth : .3,
				defaults : {
					name : "any"
				},
				items : [{
							boxLabel : "终端对时",
							inputValue : 1
						}, {
							boxLabel : "是否下发主站时间",
							inputValue : 1
						}]
			});
	// 时钟误差限值
	var timeLimit = new Ext.form.NumberField({
				fieldLabel : "时钟误差限值",
				width : 100
			});
	var labelLimit = new Ext.form.Label({
				text : "分钟",
				style : "text-align:right;font-size:12px"
			});
	// 第四行 终端对时设置
	var fourConfig = new Ext.form.FieldSet({
				title : "终端对时设置",
				titleCollapse : true,
				border : true,
				collapsible : true,
				layout : "column",
				items : [oneColumn, {
							border : false,
							labelWidth : 80,
							columnWidth : .3,
							layout : "form",
							labelAlign : "right",
							items : [timeLimit]
						}, labelLimit]
			});
	// 巡测参数配置
	var configPanel = new Ext.Panel({
				border : false,
				title : "巡测参数配置",
				layout : "form",
				autoScroll : true,
				items : [firstConfig, secondConfig, threeConfig, fourConfig]
			});
	// 备选用户
	var userGrid = new Ext.grid.GridPanel({
				tbar : ["->", allCheck, "-", {
							text : '删除选中用户',
							iconCls : 'cancel',
							handler : function() {
								if (allCheck.checked) {
									userStore.clearAll();
									unlockGrid();
								} else {
									var smSelects = userSm.getSelections();
									userStore.removeDatas(smSelects);
								}
							}
						}, "-", {
							text : '加入群组',
							iconCls : "plus",
							handler : function() {
								var groupTmnlArray = [];
								if (allCheck.checked) {
									var alldata = userStore.getBaseData();
									userStore.eachAll(function(k, v) {
												groupTmnlArray
														.push(v["cons_no"]
																+ "`" + k);
											});
								} else {
									var recs = userSm.getSelections();
									for (var i = 0; i < recs.length; i++) {
										var tmnl = recs[i].get('cons_no') + '`'
												+ recs[i].get('tmnl_asset_no');
										groupTmnlArray[i] = tmnl;
									}
								}
								if (groupTmnlArray.length == 0) {
									Ext.Msg.alert('提示', '请选择要加入群组的用户');
								} else {
									saveOrUpdateGroupWindowShow(groupTmnlArray);
									if (allCheck.checked) {
										unlockGrid();
									}
								}
							}
						}],
				title : "备选用户",
				cm : userCm,
				border : true,
				stripeRows : true,
				sm : userSm,
				store : userStore,
				bbar : new Ext.ux.MyToolbar({
							store : userStore,
							enableExpAll : true
						})
			});
	// 选择用户类型的radio组
	var consTypeRadio = new Ext.form.RadioGroup({
		height : 30,
		columnWidth : .7,
		columns : [100, 150, 150, 50],
		items : [{
					boxLabel : "公/专变用户",
					name : "userType",
					inputValue : 1,
					checked : true,
					dataIndex : "A"
				}, {
					boxLabel : "低压三相动力用户",
					name : "userType",
					inputValue : 2,
					dataIndex : "C"
				}, {
					boxLabel : "居民和一般商业用户",
					name : "userType",
					inputValue : 3,
					dataIndex : "D"
				}, {
					boxLabel : "关口",
					name : "userType",
					inputValue : 4,
					dataIndex : "G"
				}],
		listeners : {
			change : function(rds, rd) {
				oneRootNode.removeAll();
				var datas = [{
							"attributes" : null,
							"children" : null,
							"cls" : null,
							"iconCls" : null,
							"id" : "afn_1_" + rd.dataIndex,
							"leaf" : false,
							"name" : "实时数据",
							"qtip" : null,
							"text" : "实时数据",
							"type" : null
						}, {
							"attributes" : null,
							"children" : null,
							"cls" : null,
							"dataType" : null,
							"iconCls" : null,
							"id" : "afn_2_" + rd.dataIndex,
							"leaf" : false,
							"name" : "历史数据",
							"text" : "历史数据"
						}];
				Ext.each(datas, function(d) {
							oneRootNode
									.appendChild(new Ext.tree.AsyncTreeNode(d));
						});
				filterGui(1, treeFilter);
				// configTree.setRootNode(oneRootNode);
			}
		}
	});
	// 更多测量点
	/** **更多的测量点** */
	// 复用说明:morePanel.points是个数组，存放在更多面板中的值
	// 一个字符串来记录测量点
	// 选择的checkboxgroup
	var moreGroup = new Ext.form.CheckboxGroup({
				columns : 8,// 每行显示八个数
				border : false,
				autoScroll : true,
				hideLabel : true,
				style : "padding:20px 10px 0px 10px;",
				items : [{
							boxLabel : 5,
							name : "dataFetch_9",
							inputValue : 5
						}, {
							boxLabel : 6,
							name : "dataFetch_10",
							inputValue : 6
						}, {
							boxLabel : 7,
							name : "dataFetch_11",
							inputValue : 7
						}, {
							boxLabel : 8,
							name : "dataFetch_12",
							inputValue : 8
						}, {
							boxLabel : 9,
							name : "dataFetch_13",
							inputValue : 9
						}, {
							boxLabel : 10,
							name : "dataFetch_14",
							inputValue : 10
						}, {
							boxLabel : 11,
							name : "dataFetch_15",
							inputValue : 11
						}, {
							boxLabel : 12,
							name : "dataFetch_16",
							inputValue : 12
						}]
			});

	/** ***清除所有的测量点*** */
	var buttonClearAllPoints = new Ext.Panel({
				border : false,
				layout : "form",
				items : [{
							xtype : "button",
							text : "清除",
							handler : function(btn) {
								var datafetch_point = batchPointCkg;
								Ext.each(datafetch_point.items.items, function(
												i) {
											i.setValue(false);
										});
								Ext.each(moreGroup.items.items, function(i) {
											i.setValue(false);
										});
								morePanel.points = [];
							}
						}]
			});
	// 更多面板
	var morePanel = new Ext.Window({
		title : "测量点/总加组号",
		layout : "fit",
		modal : true,
		items : [moreGroup],
		height : 400,
		width : 400,
		closeAction : "hide",
		buttonAlign : "center",
		buttons : [{
					iconCls : "plus",
					handler : function() {
						addPoints();
					}
				}, {
					text : "全选",
					handler : function() {
						var ps = [];
						var flat = true;
						if (moreGroup.getValue().length == moreGroup.items.length) {
							flat = false;
						}
						// morePanel.points = [];
						Ext.each(moreGroup.items.items, function(c) {
									// if (c.getValue()) {
									// morePanel.points.push(c.boxLabel);
									// }
									ps.push(flat);
								});
						moreGroup.setValue(ps);
					}
				}, {
					text : "确定",
					handler : function() {
						morePanel.hide();
						// morePanel.check(moreGroup.getValue());
						morePanel.points = [];
						Ext.each(moreGroup.items.items, function(c) {
							if (c.getValue()) {
								morePanel.points.push(c.boxLabel);
							}
								// c.setValue(false);
							});
					}
				}, buttonClearAllPoints],
		listeners : {
			"beforeshow" : function() {
				Ext.each(moreGroup.items.items, function(c) {

							if (morePanel.points.indexOf(parseInt(c.boxLabel)) >= 0) {
								c.setValue(true);
							} else {
								c.setValue(false);
							}
						});
			}
		}
	});
	/** **初始化*** */
	morePanel.points = [];
	morePanel.init = function() {
		morePanel.points = [], Ext.each(moreGroup.items.items, function(c) {
					c.setValue(false);
				});
	}
	/** ***** */
	/** *为一个checkgroup中添加一个checkbox,@param label 为添加的值*** */
	var addPoint = function(id, label) {
		id = typeof id == "string" ? Ext.getCmp(id) : id;
		var tNum = id.items.length;
		var items = id.items;
		var columns = id.panel.items;
		var column = columns.itemAt(items.getCount() % columns.getCount());
		var ck = new Ext.form.Checkbox({
					boxLabel : label,
					name : "dataFetch_" + label,
					inputValue : label
				});
		var checkboxItem = column.add(ck);
		id.items.add(checkboxItem);
		// id.doLayout();
	};
	/** *****增加一行********* */
	function addPoints() {
		var start = moreGroup.items.length + 7 - 3;
		for (var i = start + 1; i <= start + 8; i++) {
			addPoint(moreGroup, i);
		}
		moreGroup.doLayout();
	}
	/** ***********更多测量点结束***************** */
	var moreBtn = new Ext.Button({
				text : "更多",
				width : 50,
				handler : function() {
					morePanel.show();
				}
			});
	var batchPointCkg = new Ext.form.CheckboxGroup({
				hideLabel : true,
				columns : [30, 30, 30, 30, 40],
				labelStyle : "text-align:right;width:50;",
				// id : "batchfetch_point",
				items : [{
							boxLabel : '0',
							name : "test_abc0"
						}, {
							boxLabel : '1',
							checked : true,
							name : "test_abc1"
						}, {
							boxLabel : '2',
							name : "test_abc2"
						}, {
							boxLabel : '3',
							name : "test_abc3"
						}, {
							boxLabel : '4',
							name : "test_abc4"
						}]
			});
	var pointCheckBox = new Ext.Panel({
				border : false,
				anchor : "100%",
				layout : 'table',
				columnWidth : 1,
				bodyStyle : 'padding:10px 0px 0px 0px;',
				layoutConfig : {
					columns : 2
				},
				// labelAlign:"right",
				items : [{
							border : false,
							layout : "table",
							labelAlign : "right",
							items : [{
										border : false,
										xtype : "label",
										style : "padding:-5px 0 0 0;font-size:12px;",
										html : "请选择测量点"
									}, batchPointCkg]
						}, moreBtn]
			});

	/** *更多测量点ui部分结束** */

	// 配置左边放树，按钮和grid的位置
	var oneRootNode = new Ext.tree.AsyncTreeNode({
				id : 'root_xxoo_C',
				text : 'root'
			});

	var treeLoader = new Ext.tree.TreeLoader({
				dataUrl : "baseapp/dataFetchLone!createTypeTree.action"
			});
	var configTree = new Ext.tree.TreePanel({
				columnWidth : .6,
				tbar : [typeCombox],
				border : true,
				autoScroll : true,
				animate : false,
				frame : false,
				height : 230,
				rootVisible : false,
				root : oneRootNode,
				loader : treeLoader,
				treeLoader : treeLoader,
				border : false,
				listeners : {
					afterlayout : function() {
						configTree.body.dom.style.overflowY = "scroll";
					},
					load : function() {
						filterGui(1, treeFilter);
					}
				}
			});
	var treeFilter = new Ext.tree.TreeFilter(configTree);
	var addBtn = new Ext.Button({
				width : 40,
				text : "增加",
				handler : function() {
					addCodes();
				}
			});
	var delBtn = new Ext.Button({
				width : 40,
				text : "删除",
				handler : function() {
					delCodes();
				}
			});
	var midBtnPanel = new Ext.Panel({
				border : false,
				padding : "50px 0 0 0",
				columnWidth : .1,
				autoEl : {
					tag : "center"
				},
				layout : "table",
				layoutConfig : {
					columns : 1
				},
				items : [addBtn, {
							border : false,
							height : 20
						}, delBtn]
			});
	var selectStore = new Ext.data.JsonStore({
				fields : ["code", "name"]
			});
	var selectSm = new Ext.grid.CheckboxSelectionModel();
	var selectCm = new Ext.grid.ColumnModel([selectSm, {
		header : "测试",
		dataIndex : "name",
		renderer : function(v) {
			var show = v;
			var html = '<div ext:hide="false" ext:qtitle="数据项名称" ext:qtip="'
					+ '<textarea style=font-size:12px;>' + show + '</textarea>'
					+ '" >' + show + '</div>';
			return html;
		}
	}, {
		header : "测试",
		dataIndex : "code"
	}]);

	var selectedGrid = new Ext.grid.GridPanel({
		border : false,
		hideHeaders : true,
		region : "center",
		sm : selectSm,
		cm : selectCm,
		store : selectStore,
		title : "巡测数据项",
		listeners : {
			render : function() {
				Ext.query("#" + selectedGrid.getId() + " div[class$=scroller]")[0].style.overflowY = "scroll";
			}
		}
	});
	// 解决高度问题
	var heightPanel = new Ext.Panel({
				layout : "border",
				height : 230,
				border : true,
				columnWidth : .3,
				items : [selectedGrid]
			});
	// 选择不同的配置
	var selectConfigPanel = new Ext.Panel({
				border : false,
				columnWidth : .8,
				layout : "column",
				items : [configTree, midBtnPanel, heightPanel]
			});

	var saveBtn = new Ext.Button({
				width : 80,
				text : "保存",
				handler : function() {

					// 有效性验证
					var users = userGridPanel.getSelectionModel()
							.getSelections();
					if (!users || users.length == 0) {
						return !!Ext.Msg.alert("错误", "请选择巡测用户");
					}
					var lms = lMultiselect.getSelectionModel().getSelected();
					if (!lms) {
						return !!Ext.Msg.alert("错误", "请选择一个巡测模板");
					}
					var bfp = Ext.getCmp("batchfetch_point").getValue();
					var arr = [];
					Ext.each(bfp, function(c) {
								arr.push(c.boxLabel);
							});
					Ext.each(morePanel.points, function(a) {
								arr.push(a);
							});
					// 找到所有被选择的的终端地址吗
					var tmnlArr = [];

					var allSelect = allCheck.checked;
					if (allSelect) {
						userStore.eachAll(function(u) {
									tmnlArr.push(u.get("tmnl_asset_no"));
								});
					} else {
						Ext.each(users, function(u) {
									tmnlArr.push(u.get("tmnl_asset_no"));
								});
					}
					Ext.getBody().mask("保存中");
					// Ext.Msg.prompt("提示","请输入名称:",function(msg,v){
					// Ext.getBody().unmask();
					// if(msg=Ext.Msg.OK){}
					//						
					// });
					// return;
					Ext.Ajax.request({
								url : "baseapp/batchFetch!doTask.action",
								params : {
									combiId : lMultiselect.getSelectionModel()
											.getSelected().get("combiId"),
									// queryType :
									// Ext.getCmp("bf_rgp").getValue().inputValue,
									pns : getPnStr(arr),
									// sendTime :
									// Ext.getCmp("bf_sendTime").getValue()
									// || undefined,
									// startTime : startTime.getValue(),
									endTime : endTime.getValue(),
									tmnlAssetNos : tmnlArr
								},
								callback : function() {
									Ext.getBody().unmask();
								},
								success : function(response) {
									var o = Ext.decode(response.responseText);
									if (o && o.message
											&& o.message.trim() != "") {
										return !!Ext.Msg.alert("错误", o.message);
									}
									// resultMainStore.load();
									return !!Ext.Msg.alert("提示", "巡测成功");
								}
							})
				}
			});
	var closeBtn = new Ext.Button({
				width : 80,
				text : "返回上一页",
				handler : function() {
					managerPanel.layout.setActiveItem(0);
				}
			});
	var opBtnPanel = new Ext.Panel({
				border : false,
				padding : "50px 0 0 0",
				columnWidth : .2,
				autoEl : {
					tag : "center"
				},
				layout : "table",
				layoutConfig : {
					columns : 1
				},
				items : [saveBtn, {
							border : false,
							height : 20
						}, closeBtn]
			});
	var newTopMidPanel = new Ext.Panel({
				border : false,
				region : "center",
				layout : "column",
				items : [selectConfigPanel, opBtnPanel]
			});
	// newPanel 北面
	// newPanel 北面 配置
	var taskNameText = new Ext.form.TextField({
				border : false,
				fieldLabel : "任务名称"
			});

	var newTopPanel = new Ext.Panel({
				border : false,
				collapsible : true,
				region : "north",
				height : 300,
				layout : "border",
				items : [{
							region : "north",
							border : false,
							layout : "column",
							padding : "10px 0 0 0",
							height : 73,
							items : [{
										border : false,
										layout : "form",
										columnWidth : .3,
										labelWidth : 50,
										padding : "0 20px 0 0",
										labelAlign : "right",
										items : [taskNameText]
									}, consTypeRadio, pointCheckBox]
						}, newTopMidPanel]
			});
	// 中部的tabpanel
	var newCenterTab = new Ext.TabPanel({
				border : false,
				activeItem : 0,
				region : "center",
				items : [userGrid, configPanel]
			});
	// 用于新建批量巡测任务的页面
	var newPanel = new Ext.Panel({
				title : "新建",
				border : false,
				layout : "border",
				items : [newTopPanel, newCenterTab]
			});
	// 用于管理的组面板 组件区
	var managerOrgText = new Ext.ux.orgTextField({
				fieldLabel : "供电单位"
			});
	// 方案状态的store
	var stateTask = new Ext.data.ArrayStore({
				fields : ["value", "name"],
				idIndex : 0,
				data : [[-1, "---全部----"], [1, "启用"], [2, "停用"], [3, "注销"]]
			});
	var stateTaskCombo = new Ext.form.ComboBox({
				triggerAction : "all",
				hiddenName : "state",
				fieldLabel : "方案状态",
				typeAhead : true,
				itemId : "state",
				mode : "local",
				width : 100,
				editable : false,
				value : 1,
				displayField : "name",
				valueField : "value",
				store : stateTask,
				selectOnFocus : true
			});
	var managerStore = new Ext.data.JsonStore({
				url : "",
				fields : [],
				root : ""
			});
	// manager tbar上面的组件
	var managerEnableBtn = new Ext.Button({
				text : "启用/停用"
			});
	var managerEditBtn = new Ext.Button({
				iconCls : "DataFetch",
				text : "编辑"
			});
	var logoutOffBtn = new Ext.Button({
				iconCls : "gatherByHand",
				text : "注销"
			});
	var managerSm = new Ext.grid.CheckboxSelectionModel();
	var managerCm = new Ext.grid.ColumnModel([managerSm, {
				header : "测试",
				dataIndex : ""
			}]);
	var managerGrid = new Ext.grid.GridPanel({
				region : "center",
				tbar : ["->", managerEnableBtn, managerEditBtn, logoutOffBtn],
				border : false,
				hideHeaders : false,
				store : managerStore,
				sm : managerSm,
				cm : managerCm,
				bbar : new Ext.ux.MyToolbar({
							store : managerStore,
							enableExpAll : true
						})
			});
	var managerFindBtn = new Ext.Button({
				width : 80,
				text : "查询",
				handler : function() {
				}
			});
	var managerNewBtn = new Ext.Button({
				text : "新建",
				width : 80,
				handler : function() {
					managerPanel.layout.setActiveItem(1);
				}
			});
	var managerTopPanel = new Ext.Panel({
				border : false,
				region : "north",
				heigth : 50,
				layout : "table",
				items : [{
							layout : "form",
							border : false,
							labelWidth : 60,
							labelAlign : "right",
							items : [managerOrgText]
						}, {
							layout : "form",
							border : false,
							labelWidth : 60,
							labelAlign : "right",
							items : [stateTaskCombo]
						}, {
							border : false,
							width : 120,
							autoEl : {
								tag : "center"
							},
							items : [managerFindBtn]
						}, {
							border : false,
							width : 120,
							autoEl : {
								tag : "center"
							},
							items : [managerNewBtn]
						}]
			});
	// 用于管理的组面板
	var managerMainPanel = new Ext.Panel({
				border : false,
				layout : "border",
				title : "",
				items : [managerTopPanel, managerGrid]
			});
	// 用于管理批量巡测任务的页面
	var managerPanel = new Ext.Panel({
				title : "管理",
				border : false,
				region : "center",
				layout : "card",
				activeItem : 0,
				items : [managerMainPanel, newPanel]
			});
	// 用于查看批量巡测任务的结果的页面
	// 用于查看批量巡测任务的结果的页面 组件区
	// 方案名称
	var resultNameText = new Ext.form.TextField({
				fieldLabel : "方案名称",
				width : 100
			});

	var resultStartDate = new Ext.form.DateField({
				fieldLabel : "起",
				format : "Y-m-d",
				value : new Date().add(Date.DAY, -1),
				editable : false,
				width : 100
			});
	var resultEndDate = new Ext.form.DateField({
				fieldLabel : "止",
				format : "Y-m-d",
				value : new Date(),
				editable : false,
				width : 100
			});
	var resultFindBtn = new Ext.Button({
				width : 80,
				text : "查询"
			});
	// 上部查询
	var resultTopPanel = new Ext.Panel({
				border : false,
				padding : "10px 0 0 0",
				height : 40,
				layout : "table",
				region : "north",
				items : [{
							border : false,
							layout : "form",
							labelWidth : 50,
							labelAlign : "right",
							items : [resultNameText]
						}, {
							border : false,
							layout : "form",
							labelWidth : 50,
							labelAlign : "right",
							items : [resultStartDate]
						}, {
							border : false,
							layout : "form",
							labelWidth : 50,
							labelAlign : "right",
							items : [resultEndDate]
						}, {
							border : false,
							width : 120,
							autoEl : {
								tag : "center"
							},
							items : [resultFindBtn]
						}]
			});
	// 中部的显示grid
	var resultStore = new Ext.data.JsonStore({
				url : "",
				fields : [],
				root : ""
			});
	var resultSm = new Ext.grid.CheckboxSelectionModel();
	var resultCm = new Ext.grid.ColumnModel([resultSm, {
				header : "测试",
				dataIndex : ""
			}]);
	var resultGrid = new Ext.grid.GridPanel({
				region : "center",
				border : false,
				hideHeaders : false,
				store : resultStore,
				sm : resultSm,
				cm : resultCm,
				bbar : new Ext.ux.MyToolbar({
							store : resultStore,
							enableExpAll : true
						})
			});
	var resultPanel = new Ext.Panel({
				title : "结果",
				border : false,
				layout : "border",
				items : [resultTopPanel, resultGrid]
			});
	// 用于真正显示内容的页面
	var mainPanel = new Ext.TabPanel({
				region : "center",
				border : false,
				activeItem : 0,
				items : [managerPanel, resultPanel]
			});
	// 自适应的主页面
	var mainCenterPanel = new Ext.Panel({
				border : false,
				layout : "border",
				items : [mainPanel]
			});
	// 左边树相关
	new LeftTreeListener({
		modelName : '批量巡测',
		processClick : function(p, node, e) {
			var obj = node.attributes.attributes;
			var type = node.attributes.type;
			Ext.Ajax.request({
				url : "baseapp/dataFetch!dealTree.action",
				params : {
					"node" : type == "usr"
							? ("tmnl_" + obj.tmnlAssetNo + "_" + obj.tmnlAssetNo)
							: node.id
				},
				success : function(response) {
					var o = Ext.decode(response.responseText);
					userStore.addDatas(o.resultMap);
				}
			});
		},
		processUserGridSelect : function(sm, row, r) {
			var tmnl = r.get("tmnlAssetNo");
			Ext.Ajax.request({
						url : "baseapp/dataFetch!dealTree.action",
						params : {
							"node" : "tmnl_" + tmnl + "_01"
						},
						success : function(response) {
							var o = Ext.decode(response.responseText);
							userStore.addDatas(o.resultMap);
						}
					});
		},
		processUserGridAllSelect : function(grid) {
			var tmnls = [];
			grid.getStore().each(function(r) {
						tmnls.push(r.get("tmnlAssetNo"));
					});
			Ext.Ajax.request({
						url : "baseapp/dataFetch!dealTree.action",
						params : {
							"node" : "arr_50_x_" + tmnls.join("_")
						},
						success : function(response) {
							var o = Ext.decode(response.responseText);
							userStore.addDatas(o.resultMap);
						}
					});

		}
	});
	renderModel(mainCenterPanel, '批量巡测');
		// configTree.body.dom.style.overflowY = "scroll";
		// Ext.query("#" + selectedGrid.getId() + "
		// div[class$=scroller]")[0].style.overflowY = "scroll";
});