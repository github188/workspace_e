/*******************************************************************************
 * 
 * @author huangxuan
 ******************************************************************************/

var nodeStr = undefined;
var typeNew = undefined;
var selectChkunit = undefined;
// 当前页面的编辑状态,当为台区电量页面时
var stateTq = undefined;
Ext.onReady(function() {
	// 公共方法区
	function itemFromId(panel, itemId) {
		return panel.find("itemId", itemId)[0];
	}
	// 不同类型的信息
	function getTypeInfo(isNew) {
		var cgs = centerGridSM.getSelected();
		var type = cgs.get("chkunitTypeCode");
		if (isNew)
			type = typeNew || cgs.get("chkunitTypeCode");
		return (function() {
			return type == "01" && {
				type : "01",
				no : "line",
				name : "线路"
			} || type && {
				type : "02",
				no : "tg",
				name : "台区"
			}
		})();
	}
	function getTypeCodeArr(type) {
		return type == "01" && ["01", "线路"] || type == "02" && ["02", "台区"];
	}
	// 策略生成器,不同的策略，对变化进行不同的展示
	// 如果要进行扩展，请根据名称约定来进行
	var uiChange = (function() {
		// 私有部分,数据区
		// 进行名称格式化约定
		// store初始化
		var tgStore = new Ext.data.JsonStore({
					url : './qrystat/losePowerMan!findPageTg.action',
					idProterty : "tmnlAssetNo",
					totalProperty : "resultCount",
					root : "resultList",
					fields : ['orgNo', 'tgId', 'orgName', 'cisAssetNo',
							'tmnlAssetNo', 'tgNo', 'tgName', 'tgCap',
							'pubPrivFlag', 'runStatusCode', "isReg"]
				});
		var tgExistStore = new Ext.data.JsonStore({
					url : './qrystat/losePowerMan!findPageTgsInGchkunit.action',
					fields : ['orgNo', 'tgId', 'orgName', 'cisAssetNo',
							'tmnlAssetNo', 'tgNo', 'tgName', 'tgCap',
							'pubPrivFlag', 'runStatusCode', "isReg"],
					root : "resultList",
					totalProperty : "resultCount"
				});

		var lineStore = new Ext.data.JsonStore({
					url : './qrystat/losePowerMan!findLineByLeft.action',
					fields : ['orgNo', 'lineId', 'orgName', 'lineName',
							'lineNo', "father", 'voltCode', 'volt',
							'linkLineId', 'isReg', 'runStatusCode'],
					root : "resultList",
					totalProperty : "resultCount"
				});

		var lineExistStore = new Ext.data.JsonStore({
					url : './qrystat/losePowerMan!findExistLine.action',
					fields : ['orgNo', 'lineId', 'orgName', 'lineName',
							'lineNo', "father", 'voltCode', 'volt',
							'linkLineId', 'isReg', 'runStatusCode'],
					root : "resultList",
					totalProperty : "resultCount"
				});
		// 行选择模式
		var rowCm = new Ext.grid.CheckboxSelectionModel({});
		// cm初始化
		var tgCm = new Ext.grid.ColumnModel([rowCm, {
					header : "供电单位",
					dataIndex : "orgName"
				}, {
					header : "终端资产号内码",
					hidden : true,
					dataIndex : "tmnlAssetNo"
				}, {
					header : "台区名称",
					dataIndex : "tgName"
				}, {
					header : "供电单位编号",
					hidden : true,
					dataIndex : "orgNo"
				}, {
					header : "台区编号",
					dataIndex : "tgNo"
				}, {
					header : "台区容量",
					dataIndex : "tgCap"
				}, {
					header : "公专变标志",
					dataIndex : "pubPrivFlag",
					renderer : function(v) {
						var html = v == "01" && "公变" || v == "02" && "专变";
						return html;
					}
				}, {
					header : "运行状态",
					dataIndex : "runStatusCode",
					renderer : function(v) {
						var html = v == "01" && "正在运行" || "未运行";
						return html;
					}
				}, {
					header : "终端资产号",
					dataIndex : "cisAssetNo"
				}, {
					header : "注册标志",
					dataIndex : "isReg",
					renderer : function(v) {
						var html = v == 1 && "是" || v == 0 && "否";
						return html;
					}
				}]);
		var lineCm = new Ext.grid.ColumnModel([rowCm, {
					header : "供电单位",
					dataIndex : "orgName"
				}, {
					header : "线路名称",
					dataIndex : "lineName"
				}, {
					header : "线路编号",
					dataIndex : "lineNo"
				}, {
					header : "线路电压",
					dataIndex : "volt"
				}, {
					header : "运行状态",
					dataIndex : "runStatusCode",
					renderer : function(v) {
						var html = v == "01" && "正在运行" || "未运行";
						return html;
					}
				}, {
					header : "上级线路",
					dataIndex : "father"
				}, {
					header : "注册标志",
					dataIndex : "isReg",
					renderer : function(v) {
						var html = v == 1 && "是" || v == 0 && "否";
						return html;
					}
				}]);

		return {
			// 配置项
			storeData : {
				tg : tgStore,
				tgExist : tgExistStore,
				line : lineStore,
				lineExist : lineExistStore
			},
			// 配置项
			cmData : {
				tg : tgCm,
				line : lineCm
			},
			smData : {
				"default" : rowCm
			},
			/** *得到默认的行选择模式** */
			getDefaultSm : function() {
				return this.smData["default"];
			},
			/** 用于记录当前所做的选中* */
			activeData : {
				store : tgExistStore,
				cm : tgCm
			},
			activeName : "tgExist",
			// 改变
			change : function(name, fn) {
				// 改变来自两个方面的变化，一个是store和cm，通过fn来灵活的对ui的变化进行处理
				this.activeData.store = this.storeData[name];
				// 清楚以前的查询条件
				this.activeData.store
						&& (this.activeData.store.baseParams = {});
				if (name.lastIndexOf("Exist") > -1) {
					this.activeData.cm = this.cmData[name.substring(0,
							name.length - 5)];
				} else {
					this.activeData.cm = this.cmData[name];
				}
				this.uiConfig[name] && (this.uiConfig[name](name));
				this.activeName = name;
				(typeof fn == "function") && (fn());
			},

			// 切换，在查找已经存在数据的store和当前的查找store之间切换
			// 注意配置是的名称约定
			toggle : function() {
				var name = "";
				if (this.activeName.lastIndexOf("Exist") > -1) {
					name = this.activeName.substring(0, this.activeName.length
									- 5);
				} else {
					name = this.activeName + "Exist";
				}
				this.change(name);
			},
			// 得到当前选择的store
			getStore : function() {
				return this.activeData.store;
			},
			// 得到当前选的列选择模式,
			getCm : function() {
				return this.activeData.cm;
			},
			// 配置在查询的时候要执行的代码，主要是直接store的参数的初始化
			initParams : {
				tg : function() {
				},
				line : function() {
					uiChange.getStore().baseParams = {
						node : nodeStr
					};
				}
			},
			// ui相关的配置方法
			uiConfig : {
				tg : function(name) {
					// var second = new Ext.Panel();
					// var three = new Ext.Panel();
					// newTg_panel newTg_topPanel

					topLine.hide();
					topTq.show();

				},
				tgExist : function(name) {
					this.tg(name);
				},
				line : function(name) {
					// newTg_panel.remove(itemFromId(newTg_panel,"north"),false);
					topLine.show();
					topTq.hide();
				},
				lineExist : function(name) {
					this.line(name);
				}
			}
		}
	})();

	// 一个方法，用来决定在新建和修改考核单元时的异同
	function chooseFn(msg) {
		function itemId(id) {
			return newTg_centerUnitGrid.topToolbar.get(id);
		}
		// findCurrent, addCurrent, register
		if (msg == "new") {
			itemId("findCurrent").hide();
			itemId("addCurrent").hide();
			itemId("register").show();
			itemId("ioWin").hide();
			itemId("ref").hide();
			stateTq = "new";
			selectChkunit = undefined;
			itemFromId(newTg_panel, "type").show();
		} else if (msg == "edit") {
			itemId("findCurrent").show();
			itemId("addCurrent").show();
			itemId("register").hide();
			itemId("ioWin").show();
			itemId("ref").show();
			itemFromId(newTg_panel, "type").hide();
			stateTq = "edit";
		}
	}
	// 转化bs格式时间为普通时间
	function converDataFromBs(bs) {
		if (bs.length != 5) {
			return "无效考核单元时间格式";
		}
		bs = bs + "";
		var rs = [];
		var i = 0;
		if (bs[i++] == "1") {
			rs.push("日");
		}
		if (bs[i++] == "1") {
			rs.push("月");
		}
		if (bs[i++] == "1") {
			rs.push("季");
		}
		if (bs[i++] == "1") {
			rs.push("半年");
		}
		if (bs[i++] == "1") {
			rs.push("年");
		}
		return str = rs.join(",");

	}
	// 用户判断当前的考核单元是不是自己的
	function isSelf() {
		if (stateTq == "new") {
			return true;
		}
		var r = centerGridSM.getSelected();
		if (!r) {
			return !!Ext.Msg.alert("提示", "还没有选择考核单元");
		}
		if (r.get("isSelf") == "1") {
			return true;
		}
		return false;
	}
	
	// 一个用来访问当前的考核单元下面的所有的台区的store

	// 第一行
	var firstRow = new Ext.Panel({
				border : false,
				layout : "column",
				items : [{
							columnWidth : .35,
							border : false,
							layout : "form",
							labelAlign : "right",
							labelWidth : 80,
							items : [{
										xtype : "orgtextfield",
										anchor : "80%",
										itemId : "unitFinder.orgNo",
										fieldLabel : "管理单位名称"
									}]
						}, {
							columnWidth : .30,
							border : false,
							labelAlign : "right",
							labelWidth : 80,
							layout : "form",
							items : [{
										xtype : "textfield",
										width : 100,
										itemId : "unitFinder.chkUnitName",
										fieldLabel : "考核单元名称"
									}]
						}, {
							border : false,
							columnWidth : .30,
							layout : "form",
							labelAlign : "right",
							labelWidth : 80,
							items : [{
										xtype : "combo",
										fieldLabel : "&nbsp;&nbsp;考核单元分类",
										triggerAction : "all",
										hiddenName : "unitFinder.chkUnitType",
										typeAhead : true,
										mode : "local",
										width : 100,
										itemId : "unitFinder.chkUnitType",
										emptyText : "考核单元分类",
										displayField : "name",
										valueField : "value",
										store : new Ext.data.ArrayStore({
													fields : ["value", "name"],
													data : [["01", "线路"],
															["02", "台区"]]
												}),
										selectOnFocus : true
									}]
						}]
			})
	// 第二行
	var secondRow = new Ext.Panel({
				border : false,
				layout : "column",
				items : [{
					columnWidth : .35,
					border : false,
					layout : "form",
					labelAlign : "right",
					labelWidth : 80,
					items : [{
						xtype : "combo",
						anchor : "80%",
						itemId : "unitFinder.chkCycle",
						mode : "local",
						hiddenName : "unitFinder.chkCycle",
						triggerAction : "all",
						typeAhead : true,
						fieldLabel : "考核周期",
						selectOnFocus : true,
						emptyText : "请选择考核周期",
						displayField : "name",
						valueField : "value",
						store : new Ext.data.ArrayStore({
									fields : ["value", "name"],
									data : [["1", "日"], ["2", "月"], ["3", "季"],
											["4", "半年"], ["5", "年"]]
								})
					}]
				}, {
					columnWidth : .30,
					border : false,
					layout : "form",
					labelAlign : "right",
					labelWidth : 80,
					items : [{
								xtype : "combo",
								fieldLabel : "&nbsp;&nbsp;组合标志",
								triggerAction : "all",
								hiddenName : "unitFinder.linkFlag",
								typeAhead : true,
								mode : "local",
								width : 100,
								itemId : "unitFinder.linkFlag",
								emptyText : "请选择组合标志",
								displayField : "name",
								valueField : "value",
								store : new Ext.data.ArrayStore({
											fields : ["value", "name"],
											data : [["1", "是"], ["0", "否"]]
										}),
								selectOnFocus : true
							}]
				}, {
					columnWidth : .15,
					border : false,
					layout : "form",
					items : [{
						xtype : "button",
						text : "查询",
						handler : function() {
							// function tg_id(id) {
							// return Ext.getCmp("tg_" + id).getValue();
							// }
							var params = {
								"unitFinder.orgNo" : itemFromId(firstRow,
										"unitFinder.orgNo").getValue(),
								"unitFinder.chkCycle" : itemFromId(secondRow,
										"unitFinder.chkCycle").getValue(),
								"unitFinder.chkUnitName" : itemFromId(firstRow,
										"unitFinder.chkUnitName").getValue(),
								"unitFinder.linkFlag" : itemFromId(secondRow,
										"unitFinder.linkFlag").getValue(),
								"unitFinder.chkUnitType" : itemFromId(firstRow,
										"unitFinder.chkUnitType").getValue()
							};
							centerGridStore.baseParams = params;
							centerGridStore.load();
						}
					}]
				}, {
					columnWidth : .20,
					border : false,
					layout : "form",
					items : [{
						border : false,
						width : 80,
						items : [{
							xtype : "button",
							text : "新建",
							handler : function() {
								cardPanel.layout.setActiveItem(1);
								chooseFn("new");
								// topTq.show();

								itemFromId(newTg_panel, "typeRadio").setValue(
										"tg", true);
								uiChange.getStore().removeAll();
								itemFromId(topTq, "nodeName").setValue("");
								newTg_panel.setTitle("创建台区考核单元");
								typeNew = "02";
								uiChange.change("tg");
								newTg_panel.doLayout();
							}
						}]
					}]
				}]
			});
	// 上部的查询面板
	var topFindPanel = new Ext.Panel({
				border : false,
				bodyStyle : "padding:10px 0 0 10px;",
				height : 60,
				region : "north",
				layout : "form",
				items : [firstRow, secondRow]
			})
	var centerGridSM = new Ext.grid.CheckboxSelectionModel({
				listeners : {
					rowselect : function(sm, index, r) {
						var c = Ext.getCmp("losepower_edit");
						var del = Ext.getCmp("losePower_del");
						selectChkunit = this.getSelected().get("chkunitId");
						var d = Ext.getCmp("losePower_define");
						if (r.get("isSelf") == "1") {
							// c.enable();
							// d.enable();
							c.setText("修改");
							del.enable();
						} else {
							// c.disable();
							// d.disable();
							c.setText("查看");
							del.disable();
						}
					}
				}
			});
	var centerGridCM = new Ext.grid.ColumnModel([centerGridSM, {
				header : "管理单位",
				dataIndex : "orgName"
			}, {
				header : "考核单元名称",
				dataIndex : "chkunitName"
			}, {
				header : "考核单元分类",
				dataIndex : "chkunitTypeCode",
				renderer : function(v) {
					return getTypeCodeArr(v)[1];
				}

			}, {
				header : "考核周期",
				dataIndex : "chkCycle",
				renderer : function(v) {
					return converDataFromBs(v);
				}
			}, {
				header : "考核标志",
				dataIndex : "examFlag",
				renderer : function(v) {
					var html = v == 1 && "是" || v == 2 && "否";
					return html;
				}
			}, {
				header : "负责人",
				dataIndex : "respEmpNo"
			}, {
				header : "组合标志",
				dataIndex : "linkFlag",
				renderer : function(v) {
					var html = v == 1 && "是" || v == 0 && "否";
					return html;
				}
			}]);
	var centerGridStore = new Ext.data.JsonStore({
				url : './qrystat/losePowerMan!findChkUnit.action',
				fields : ["chkunitId", "chkunitTypeCode", "chkunitName",
						"orgName", "examFlag", "linkFlag", "respEmpNo",
						"chkCycle", "detSortCode", "orgNo", "isSelf"],
				root : "resultList",
				autoLoad : true,
				totalProperty : "resultCount"
			});

	// 中间的grid
	var centerGrid = new Ext.grid.GridPanel({
		border : false,
		tbar : [{
					xtype : "label",
					text : "考核单元",
					style : 'font-weight:bold;color:#15428b'
				}, "->", {
					xtype : "button",
					text : "定义",
					id : "losePower_define",
					iconCls : "DataFetch",
					handler : function() {
						var cgs = centerGridSM.getSelections();
						if (cgs.length != 1) {
							return !!Ext.Msg.alert("提示", "所选考核单元只能有一个");
						}

						selectChkunit = cgs[0].get("chkunitId");
						var type = cgs[0].get("chkunitTypeCode");
						uiChange.change((function() {
									return type == "01" && "lineExist"
											|| type == "02" && "tgExist"
								})());
						cardPanel.layout.setActiveItem(1);
						uiChange.getStore().baseParams = {
							gid : selectChkunit
						};
						chooseFn("edit");
						newTg_centerUnitGrid.reconfigure(uiChange.getStore(),
								uiChange.getCm());
						newTg_centerUnitGrid.getBottomToolbar()
								.bindStore(uiChange.getStore());
						uiChange.getStore().load({
									params : {
										start : 0,
										limit : 50
									}
								});
						newTg_panel.setTitle("定义考核单元:"
								+ cgs[0].get("chkunitName"));
						nodeStr = undefined;
						if (type == "01") {
							itemFromId(topLine, "nodeName").setValue("");
						} else if (type == "02") {
							itemFromId(topTq, "nodeName").setValue("");
						}

					}
				}, {
					xtype : "button",
					text : "修改",
					id : "losepower_edit",
					iconCls : "gatherByHand",
					handler : function() {
						var cgs = centerGridSM.getSelections();
						if (cgs.length != 1) {
							return !!Ext.Msg.alert("提示", "所选考核单元只能有一个");
						}
						var gid = cgs[0].get("chkunitId");
						params = {
							gid : gid
						};
						Ext.Ajax.request({
							url : './qrystat/losePowerMan!findDetailChkunit.action',
							params : params,
							success : function(response) {
								var o = Ext.decode(response.responseText);
								if (o && o.message && o.message.trim() != "") {
									return !!Ext.Msg.alert("提示", o.message);
								}

								var m = {};
								Ext.iterate(o.map, function(k, v) {
											m["gk." + k] = v;
										});
								var win = tg.register(function() {
									var ps = {
										"gk.chkunitId" : gid,
										"cycles" : []
									};
									var flat = true;
									var fl = "";
									win.findBy(function(w) {
										if (w.itemId) {
											if (w.xtype == "checkboxgroup") {
												Ext.each(w.getValue(),
														function(ww) {
															ps.cycles
																	.push(ww.boxLabel);
														});
											} else {
												if (!w.isValid()) {
													flat = false;
													fl = w.fieldLabel;
													return false;
												}
												ps[w.itemId] = w.getValue();
											}
										}
									});
									if (ps.cycles.length == 0) {
										return !!Ext.Msg.alert("提示", "考核周期不能为空");
									}
									if(!flat){
									return !!Ext.Msg.alert("提示", fl+"不能为空");
									}
									Ext.Ajax.request({
										url : './qrystat/losePowerMan!updateChkunit.action',
										params : ps,
										success : function(response) {
											var o = Ext
													.decode(response.responseText);
											if (o && o.message
													&& o.message.trim() != "") {
												return !!Ext.Msg.alert("提示",
														o.message);
											}
											Ext.Msg.alert("提示", "修改成功");
											centerGridStore.reload();
											win.close();
										}
									});

								}, cgs[0].get("chkunitTypeCode"));

								win.findBy(function(w) {
									if (w.itemId) {

										if (w.xtype == "checkboxgroup") {
											// var mm = {
											// "日" : false,
											// "月" : false,
											// "季" : false,
											// "年" : false
											// };
											var arr = m['gk.chkCycle']
													.split("");
											for (var i = 0; i < arr.length; i++) {
												arr[i] = new Number(arr[i]);
											}
											w.setValue(arr);
											// alert(Ext.encode(mm));
											// w.setValue(mm);
										} else {
											if (w.itemId == "gk.orgNo") {
												w.setValue(m["gk.orgName"]);
												w.nodeValue = m["gk.orgNo"];
											} else {
												w.setValue(m[w.itemId]);
											}
										}
									}
								});

							}
						});

					}
				}, {
					xtype : "button",
					text : "删除选中",
					id : "losePower_del",
					iconCls : "cancel",
					handler : function() {
						// check 提示是否删除
						var ss = centerGridSM.getSelections();

						if (ss.length == 0) {
							return !!Ext.Msg.alert("提示", "请选择一个考核单元");
						}
						var params = {
							ints : []
						};
						Ext.each(ss, function(r) {
									params.ints.push(r.get("chkunitId"));
								});
						Ext.MessageBox.show({
							title : "警告",
							msg : "是否删除选中考核单元",
							icon : Ext.MessageBox.WARNING,
							buttons : Ext.MessageBox.YESNO,
							fn : function(msg) {
								if (msg == "yes") {
									Ext.Ajax.request({
										url : './qrystat/losePowerMan!deleteGChkunitRef.action',
										params : params,
										success : function(response) {
											var o = Ext
													.decode(response.responseText);
											if (o && o.message
													&& o.message.trim() != "") {
												return !!Ext.Msg.alert("提示",
														o.message);
											}
											Ext.Msg.alert("提示", "删除成功");
											centerGridStore.reload();
										}
									});
								}
							}
						});
					}
				}],
		region : "center",
		sm : centerGridSM,
		cm : centerGridCM,
		store : centerGridStore,
		bbar : new Ext.ux.MyToolbar({
					store : centerGridStore,
					enableExpAll : true
				})
	});
	// 台区考核单元界面

	var newTg_topPanel = new Ext.Panel({
				border : false,
				itemId : "north",
				region : "north",
				layout : "table",
				padding : "10px 0px 0px 5px",
				height : 50,
				items : []
			});

	var newTg_sm = new Ext.grid.CheckboxSelectionModel({});
	var newTg_cm = new Ext.grid.ColumnModel([newTg_sm, {
				header : "供电单位",
				dataIndex : "orgName"
			}, {
				header : "终端资产号内码",
				hidden : true,
				dataIndex : "tmnlAssetNo"
			}, {
				header : "台区名称",
				dataIndex : "tgName"
			}, {
				header : "供电单位编号",
				hidden : true,
				dataIndex : "orgNo"
			}, {
				header : "台区编号",
				dataIndex : "tgNo"
			}, {
				header : "台区容量",
				dataIndex : "tgCap"
			}, {
				header : "公专变标志",
				dataIndex : "pubPrivFlag",
				renderer : function(v) {
					var html = v == "01" && "公变" || v == "02" && "专变";
					return html;
				}
			}, {
				header : "运行状态",
				dataIndex : "runStatusCode",
				renderer : function(v) {
					var html = v == "01" && "正在运行" || "未运行";
					return html;
				}
			}, {
				header : "终端资产号",
				dataIndex : "cisAssetNo"
			}, {
				header : "注册标志",
				dataIndex : "isReg",
				renderer : function(v) {
					var html = v == 1 && "是" || v == 0 && "否";
					return html;
				}
			}]);

	var newTg_centerUnitGrid = new Ext.grid.GridPanel({
		region : "center",
		// height:300,
		cm : uiChange.getCm(),
		sm : uiChange.getDefaultSm(),
		tbar : [{
					xtype : "label",
					html : '<font color="red">' + '点击左边树添加列表信息' + '</font>'
				}, "->", {
					xtype : "button",
					itemId : "ioWin",
					iconCls : "modelManage",
					text : "流入流出",
					handler : function() {
						tg.ioWindow();
					}
				}, {
					xtype : "button",
					itemId : "ref",
					iconCls : "modelManage",
					text : "参考指标",
					// hidden : true,
					handler : function() {
						managerRefWin(selectChkunit);

						// tg.ioWindow();
					}
				}, {
					xtype : "button",
					itemId : "findCurrent",
					iconCls : "modelManage",
					text : "管理",
					handler : function() {
						tg.managerExistsWin();
					}
				}, {
					xtype : "button",
					iconCls : "plus",
					id : "losePower_bbar_store",
					itemId : "addCurrent",
					text : "添加",
					handler : function() {
						var params = {
							tgIds : [],
							gid : selectChkunit
						}
						var cgs = centerGridSM.getSelected();
						var type = cgs.get("chkunitTypeCode");
						var seles = newTg_centerUnitGrid.getSelectionModel()
								.getSelections();
						if (seles.length == 0) {
							return !!Ext.Msg.alert("提示", "至少选择一条数据");
						}
						if (type == "02") {
							Ext.each(seles, function(r) {
										params.tgIds.push(r.get("tgId"));
									});
						} else if (type == "01") {
							Ext.each(seles, function(r) {
										params.tgIds.push(r.get("lineId"));
									});
						}

						showEchoGrid({
									params : params
								}, function(showWin) {
									showWin && showWin.close();
									Ext.Ajax.request({
										url : './qrystat/losePowerMan!addTgs.action',
										params : params,
										success : function(response) {
											var o = Ext
													.decode(response.responseText);
											if (o && o.message
													&& o.message.trim() != "") {
												return !!Ext.Msg.alert("提示",
														o.message);
											}
											Ext.Msg.alert("提示", "添加成功");
										}
									});

								});
					}
				}, {
					xtype : "button",
					iconCls : "plus",
					itemId : "register",
					text : "注册考核单元",
					handler : function() {
						var seles = newTg_centerUnitGrid.getSelectionModel()
								.getSelections();
						if (seles.length == 0) {
							return !!Ext.Msg.alert("提示", "请至少选择一条数据");
						}
						var params = {
							cycles : [],
							// 历史原因，tgIds用于记录obj_id，可能是台区，也可能是线路等等
							tgIds : []
						}
						if (typeNew == "01") {
							Ext.each(seles, function(r) {
										params.tgIds.push(r.get("lineId"));
									});
						} else if (typeNew == "02") {
							Ext.each(seles, function(r) {
										params.tgIds.push(r.get("tgId"));
									});

						}

						var cgs = centerGridSM.getSelections();
						showEchoGrid({
									params : params
								}, function(showWin) {
									showWin && showWin.close();
									var win = tg.register(
											function() {
												var flat = true;
												var fl = null;
												win.findBy(function(w) {
													if (w.itemId) {
														if (w.xtype == "checkboxgroup") {
															Ext
																	.each(
																			w
																					.getValue(),
																			function(
																					ww) {
																				params.cycles
																						.push(ww.boxLabel);
																			});
														} else {
															if (!w.isValid()) {
																flat = false;
																fl = w.fieldLabel;
																return false;
															}
															params[w.itemId] = w
																	.getValue();
														}
													}
												});
												if (params.cycles.length == 0) {
													return !!Ext.Msg.alert(
															"提示", "考核单元周期不能为空");
												}
												if (!flat) {
													return !!Ext.Msg.alert(
															"提示", fl + "不能为空")
												}

												Ext.Ajax.request({
													url : './qrystat/losePowerMan!register.action',
													params : params,
													success : function(response) {
														var o = Ext
																.decode(response.responseText);
														if (o
																&& o.message
																&& o.message
																		.trim() != "") {
															return !!Ext.Msg
																	.alert(
																			"提示",
																			o.message);
														}
														Ext.Msg.alert("提示",
																"注册成功");
														centerGridStore
																.reload();
														win.close();
													}
												});
											},
											typeNew
													|| cgs[0]
															.get("chkunitTypeCode"),
											function(win) {
												function itemId(id) {
													return win.find("itemId",
															id)[0];
												}
												var name = "";
												if (typeNew == "01") {
													name = seles[0]
															.get("lineName");
												} else if (typeNew == "02") {
													name = seles[0]
															.get("tgName");
												}
												itemId("gk.chkunitName")
														.setValue(name);
												itemId("gk.examFlag")
														.setValue(1);
												if (seles.length > 1) {
													itemId("gk.linkFlag")
															.setValue(1);
												} else {
													itemId("gk.linkFlag")
															.setValue(0);
												}
												itemId("gk.statusCode")
														.setValue(1);
												itemId("cycles").setValue([
														false, true, false,
														false]);

											});

								});
					}
				}, {
					xtype : "button",
					text : "清除选中",
					id : "losePower_clear_select",
					iconCls : "cancel",
					handler : function() {
						var seles = newTg_centerUnitGrid.getSelectionModel()
								.getSelections();
						newTg_centerUnitGrid.getStore().remove(seles);
					}
				}],
		store : uiChange.getStore(),
		bbar : new Ext.ux.MyToolbar({
					store : uiChange.getStore(),
					enableExpAll : true
				}),
		listeners : {
			reconfigure : function(g, store, cm) {
				var c = Ext.getCmp("losePower_clear_select");
				var d = Ext.getCmp("losePower_bbar_store");
				if (uiChange.activeName.lastIndexOf("Exist") < 0) {
					c.enable();
					d.enable();
				} else {
					c.disable();
					d.disable();
				}
			}
		}

	});

	var topLine = new Ext.Panel({
		border : false,
		itemId : "topLine",
		region : "north",
		layout : "table",
		padding : "10px 0px 0px 5px",
		height : 50,
		items : [{
					border : false,
					layout : "form",
					itemId : "first",
					labelAlign : "right",
					labelWidth : 60,
					items : [{
								xtype : "textfield",
								itemId : "nodeName",
								allowBlank : false,
								readOnly : true,
								fieldLabel : "节点名称"
							}]
				}, {
					border : false,
					layout : "form",
					labelAlign : "right",
					itemId : "second",
					labelWidth : 90,
					items : [{
								xtype : "textfield",
								fieldLabel : "线路名称",
								width : 100,
								itemId : "lineName"
							}]
				}, {
					border : false,
					layout : "form",
					itemId : "volt",
					labelAlign : "right",
					labelWidth : 50,
					items : [{
						xtype : "combo",
						fieldLabel : "电压等级",
						triggerAction : "all",
						typeAhead : true,
						mode : "remote",
						width : 100,
						itemId : "voltCode",
						emptyText : "请选择电压等级",
						displayField : "volt",
						valueField : "voltCode",
						store : new Ext.data.JsonStore({
									url : './qrystat/losePowerMan!findVoltType.action',
									fields : ["voltCode", "volt"],
									root : "resultList"
								}),
						selectOnFocus : true
					}]
				}, {
					border : false,
					layout : "form",
					itemId : "three",
					labelAlign : "right",
					labelWidth : 50,
					items : [{
								xtype : "combo",
								fieldLabel : "是否注册",
								triggerAction : "all",
								typeAhead : true,
								mode : "local",
								width : 100,
								itemId : "isReg",
								emptyText : "请选择是否注册",
								displayField : "name",
								valueField : "value",
								store : new Ext.data.ArrayStore({
											fields : ["value", "name"],
											data : [["1", "是"], ["0", "否"]]
										}),
								selectOnFocus : true
							}]
				}, {
					border : false,
					itemId : "find",
					layout : "table",
					items : [{
								border : false,
								width : 20,
								// html:"&nbsp;",
								xtype : "panel"
							}, {
								xtype : "button",
								width : 50,
								text : "查询",
								handler : function() {
									// var tgNode =
									// itemFromId(topLine,"nodeName");
									if (!nodeStr) {
										return Ext.Msg.alert("提示", "请从左边树选择节点");
									}
									// uiChange.toggle();
									uiChange.change("line")
									newTg_centerUnitGrid.reconfigure(uiChange
													.getStore(), uiChange
													.getCm());
									newTg_centerUnitGrid.getBottomToolbar()
											.bindStore(uiChange.getStore());
									uiChange.getStore().baseParams = {
										"lineFinder.lineName" : itemFromId(
												topLine, "lineName").getValue(),
										"lineFinder.isReg" : itemFromId(
												topLine, "isReg").getValue(),
										"lineFinder.voltCode" : itemFromId(
												topLine, "voltCode").getValue(),
										node : nodeStr
									}
									uiChange.getStore().load({
										params : {
											start : 0,
											limit : 50
										},
										callback : function() {
											uiChange.getDefaultSm().selectAll();
										}
									});
								}
							}]
				}, {
					border : false,
					itemId : "pre",
					layout : "table",
					items : [{
								border : false,
								width : 50,
								// html:"&nbsp;",
								xtype : "panel"
							}, {
								xtype : "button",
								width : 60,
								text : "返回上一步",
								handler : function() {
									cardPanel.layout.setActiveItem(0);
									selectChkunit = undefined;
									typeNew = undefined;
									nodeStr = undefined;
									newTg_centerUnitGrid.getStore().removeAll();
									itemFromId(topLine, "nodeName")
											.setValue("");
								}
							}]
				}]
	});
	var topTq = new Ext.Panel({
		border : false,
		itemId : "topTq",
		region : "north",
		layout : "table",
		padding : "10px 0px 0px 5px",
		height : 50,
		items : [{
					border : false,
					layout : "form",
					itemId : "first",
					labelAlign : "right",
					labelWidth : 60,
					items : [{
								xtype : "textfield",
								itemId : "nodeName",
								allowBlank : false,
								readOnly : true,
								fieldLabel : "节点名称"
							}]
				}, {
					border : false,
					layout : "form",
					labelAlign : "right",
					itemId : "second",
					labelWidth : 90,
					items : [{
								xtype : "combo",
								fieldLabel : "公变专变标志",
								triggerAction : "all",
								typeAhead : true,
								hiddenName : "tgFinder.pubPrivFlag",
								mode : "local",
								width : 100,
								itemId : "pubPrivFlag",
								emptyText : "请选择公变专变标志",
								displayField : "name",
								valueField : "value",
								store : new Ext.data.ArrayStore({
											fields : ["value", "name"],
											data : [["01", "公变"], ["02", "专变"]]
										}),
								selectOnFocus : true

							}]
				}, {
					border : false,
					layout : "form",
					itemId : "three",
					labelAlign : "right",
					labelWidth : 50,
					items : [{
								xtype : "combo",
								fieldLabel : "是否注册",
								triggerAction : "all",
								typeAhead : true,
								mode : "local",
								width : 100,
								itemId : "isReg",
								emptyText : "请选择是否注册",
								displayField : "name",
								valueField : "value",
								store : new Ext.data.ArrayStore({
											fields : ["value", "name"],
											data : [["1", "是"], ["0", "否"]]
										}),
								selectOnFocus : true
							}]
				}, {
					border : false,
					itemId : "find",
					layout : "table",
					items : [{
								border : false,
								width : 20,
								// html:"&nbsp;",
								xtype : "panel"
							}, {
								xtype : "button",
								width : 50,
								text : "查询",
								handler : function() {
									// var tgNode = Ext.getCmp("tg_nodeName");
									if (!nodeStr) {
										return Ext.Msg.alert("提示", "请从左边树选择节点");
									}
									// uiChange.toggle();
									uiChange.change("tg");
									newTg_centerUnitGrid.reconfigure(uiChange
													.getStore(), uiChange
													.getCm());
									newTg_centerUnitGrid.getBottomToolbar()
											.bindStore(uiChange.getStore());
									uiChange.getStore().baseParams = {
										"tgFinder.pubPrivFlag" : itemFromId(
												topTq, "pubPrivFlag")
												.getValue(),
										"tgFinder.isReg" : itemFromId(topTq,
												"isReg").getValue(),
										node : nodeStr
									}
									uiChange.getStore().load({
										params : {
											start : 0,
											limit : 50
										},
										callback : function() {
											uiChange.getDefaultSm().selectAll();
										}
									});
								}
							}]
				}, {
					border : false,
					itemId : "pre",
					layout : "table",
					items : [{
								border : false,
								width : 50,
								// html:"&nbsp;",
								xtype : "panel"
							}, {
								xtype : "button",
								width : 60,
								text : "返回上一步",
								handler : function() {
									cardPanel.layout.setActiveItem(0);
									selectChkunit = undefined;
									nodeStr = undefined;
									typeNew = undefined;
									newTg_centerUnitGrid.getStore().removeAll();
									itemFromId(topTq, "nodeName").setValue("");
								}
							}]
				}]
	});
	// alert(newTg_centerUnitGrid.topToolbar.get("register"));
	// newTg_unitStore.setGrid(newTg_centerUnitGrid);
	// 选择线路和台区
	var newTg_panel = new Ext.Panel({
				border : false,
				title : "创建台区考核单元",
				region : "center",
				layout : "border",
				items : [{
					itemId : "north",
					region : "north",
					padding : "10px 0px 0px 5px",
					height : 80,
					layout : "form",
					border : false,
					items : [{
						border : false,
						itemId : "type",
						labelAlign : "right",
						layout : "form",
						labelWidth : 80,
						items : [{
							xtype : "radiogroup",
							itemId : "typeRadio",
							fieldLabel : "考核单元分类",
							columns : [100, 100, 100, 100],
							items : [{
										boxLabel : "线路类",
										inputValue : "01",
										name : "type",
										dataIndex : "line"
									}, {
										boxLabel : "台区类",
										checked : true,
										inputValue : "02",
										name : "type",
										dataIndex : "tg"
									}, {
										boxLabel : "分压类",
										disabled : true,
										inputValue : "02",
										name : "type"
									}, {
										boxLabel : "供电单位类",
										disabled : true,
										inputValue : "02",
										name : "type"
									}],
							listeners : {
								change : function(rg, r) {

									chooseFn("new");
									uiChange.getStore().removeAll();
									// itemFromId(topTq,
									// "nodeName").setValue("");
									typeNew = r.inputValue;
									if (r.inputValue == "01") {
										uiChange.change("line");
										itemFromId(topLine, "nodeName")
												.setValue("");
										newTg_panel.setTitle("创建线路考核单元");
									} else if (r.inputValue == "02") {
										uiChange.change("tg");
										itemFromId(topTq, "nodeName")
												.setValue("");
										newTg_panel.setTitle("创建台区考核单元");
									}
									newTg_centerUnitGrid.reconfigure(uiChange
													.getStore(), uiChange
													.getCm());
								}
							}
						}]
					}, topLine, topTq]
				}, newTg_centerUnitGrid]
			});
	// 台区考核单元界面结束

	// 用于显示的card之一的panel
	var showPanel = new Ext.Panel({
				border : false,
				title : "考核单元管理",
				layout : "border",
				autoScroll : true,
				items : [topFindPanel, centerGrid]
			})

	var cardPanel = new Ext.Panel({
				border : false,
				activeItem : 0,
				region : "center",
				layout : "card",
				items : [showPanel, newTg_panel]
			});
	// 最外层panel，可以实现自适应
	var mainPanel = new Ext.Panel({
				border : false,
				layout : "border",
				items : [cardPanel]
			});
	// 生成注册考核单元的窗体
	/**
	 * @param fn
	 *            fn为在保存时执行的代码
	 */
	tg.register = function(fn, type, init) {
		// 三行
		var first = new Ext.Panel({
					border : false,
					layout : "column",
					items : [{
								border : false,
								columnWidth : .33,
								layout : "form",
								labelAlign : "right",
								labelWidth : 80,
								items : [{
											xtype : "textfield",
											width : 100,
											allowBlank : false,
											itemId : "gk.chkunitName",
											fieldLabel : "考核单元名称"
										}]
							}, {
								border : false,
								columnWidth : .33,
								layout : "form",
								labelAlign : "right",
								labelWidth : 60,
								items : [{
											xtype : "orgtextfield",
											width : 100,
											allowBlank : false,
											itemId : "gk.orgNo",
											fieldLabel : "管理单位"
										}]
							}, {
								border : false,
								columnWidth : .33,
								layout : "form",
								labelAlign : "right",
								labelWidth : 40,
								items : [{
											xtype : "textfield",
											width : 100,
											readOnly : true,
											itemId : "staffNo",
											fieldLabel : "负责人"
										}]
							}]
				});

		var two = new Ext.Panel({
			border : false,
			layout : "column",
			items : [{
						border : false,
						columnWidth : .33,
						layout : "form",
						labelAlign : "right",
						labelWidth : 80,
						items : [{
									xtype : "combo",
									fieldLabel : "&nbsp;&nbsp;考核标志",
									triggerAction : "all",
									typeAhead : true,
									allowBlank : false,
									mode : "local",
									itemId : "gk.examFlag",
									hiddenName : "examFlag",
									width : 100,
									emptyText : "请选择考核标志",
									displayField : "name",
									valueField : "value",
									store : new Ext.data.ArrayStore({
												fields : ["value", "name"],
												data : [["1", "考核"],
														["2", "不考核"]]
											}),
									selectOnFocus : true
								}]
					}, {
						border : false,
						columnWidth : .60,
						layout : "column",
						labelAlign : "right",
						labelWidth : 60,
						items : [{
									columnWidth : .25,
									xtype : "panel",
									border : false,
									style : {
										font : "12px tahoma,arial,helvetica,sans-serif"
									},
									html : "&nbsp;&nbsp;&nbsp;&nbsp;考核周期&nbsp;&nbsp;"
								}, {
									columnWidth : .75,
									xtype : "checkboxgroup",
									itemId : "cycles",
									items : [{
												boxLabel : "日",
												name : "time"
											}, {
												boxLabel : "月",
												name : "time"
											}, {
												boxLabel : "季",
												name : "time"
											}, {
												boxLabel : "半年",
												name : "time"
											}, {
												boxLabel : "年",
												name : "time"
											}]
								}]
					}]
		});

		var three = new Ext.Panel({
					border : false,
					layout : "column",
					items : [{
								border : false,
								columnWidth : .33,
								layout : "form",
								labelAlign : "right",
								labelWidth : 80,
								items : [{
											xtype : "combo",
											triggerAction : "all",
											width : 100,
											displayField : "name",
											valueField : "value",
											itemId : "gk.chkunitTypeCode",
											hiddenName : "chkunitTypeCode",
											mode : "local",
											typeAhead : true,
											allowBlank : false,
											value : getTypeCodeArr(type)[0],
											editable : false,
											readOnly : true,
											emptyText : "考核单元分类",
											fieldLabel : "考核单元分类",
											store : new Ext.data.ArrayStore({
														fields : ["value",
																"name"],
														data : [getTypeCodeArr(type)]
													}),
											selectOnFocus : true
										}]
							}, {
								border : false,
								columnWidth : .33,
								layout : "form",
								labelAlign : "right",
								labelWidth : 60,
								items : [{
											xtype : "combo",
											fieldLabel : "&nbsp;&nbsp;组合标志",
											triggerAction : "all",
											typeAhead : true,
											allowBlank : false,
											itemId : "gk.linkFlag",
											hiddenName : "linkFlag",
											mode : "local",
											width : 100,
											emptyText : "请选择组合标志",
											displayField : "name",
											valueField : "value",
											store : new Ext.data.ArrayStore({
														fields : ["value",
																"name"],
														data : [["1", "是"],
																["0", "否"]]
													}),
											selectOnFocus : true
										}]
							}, {
								border : false,
								columnWidth : .33,
								layout : "form",
								labelAlign : "right",
								labelWidth : 60,
								items : [{
									xtype : "combo",
									fieldLabel : "&nbsp;&nbsp;是否有效",
									triggerAction : "all",
									typeAhead : true,
									itemId : "gk.statusCode",
									hiddenName : "statusCode",
									mode : "local",
									width : 100,
									editable : false,
									displayField : "name",
									valueField : "value",
									store : new Ext.data.ArrayStore({
												fields : ["value", "name"],
												data : [["0", "停用"],
														["1", "启用"],
														["2", "注销"]]
											}),
									selectOnFocus : true
								}]
							}]
				});
		var cgs = centerGridSM.getSelected();
		var win = new Ext.Window({
					title : (typeNew || cgs.get("chkunitTypeCode")) == "01"
							&& "线路考核单元"
							|| (typeNew || cgs.get("chkunitTypeCode")) == "02"
							&& "台区考核单元",
					border : false,
					width : 580,
					height : 163,
					modal : true,
					layout : "border",
					items : [{
								region : "center",
								layout : "form",
								padding : "10px 0 0 0",
								items : [first, two, three]
							}],
					buttons : [{
								text : "保存",
								hidden : !isSelf(),
								handler : function() {
									if (isSelf()) {
										typeof fn == "function" ? fn() : "";
									}

								}
							}, {
								text : "退出",
								handler : function() {
									win.close();
								}
							}]
				});
		win.show();
		Ext.Ajax.request({
					url : './qrystat/losePowerMan!findCurrentStaff.action',
					success : function(response) {
						var o = Ext.decode(response.responseText);
						var w = win.find("itemId", "staffNo")[0];
						w && (w.setValue(o.staff.staffNo || ""));
					}
				});
		typeof init == "function" ? init(win) : "";
		return win;
	}

	// tg.register();
	// 生成定义入口和出口的界面
	tg.ioWindow = function() {
		var cgs = centerGridSM.getSelected();
		var typeMp = {
			"01" : "用电客户",
			"02" : "关口",
			"03" : "台区关口",
			"04" : "变电站关口",
			"05" : "电厂关口"
		};
		var attrMp = {
			"01" : "结算",
			"02" : "考核"
		};
		var userMp = {
			"01" : "售电侧结算",
			"02" : "台区供电考核",
			"03" : "线路供电考核",
			"04" : "指标分析",
			"05" : "趸售供电关口",
			"06" : "地市供电关口",
			"07" : "省级供电关口",
			"08" : "跨省输电关口",
			"09" : "跨区输电关口",
			"10" : "跨国输电关口",
			"11" : "发电上网关口"
		};
		var dataSrcMp = {
			"1" : "485",
			"2" : "脉冲",
			"3" : "交采",
			"4" : "直流模拟量"
		};
		// 第一行
		var first = new Ext.Panel({
					border : false,
					layout : "column",
					items : [{
						columnWidth : .33,
						border : false,
						layout : "form",
						labelAlign : "right",
						labelWidth : 80,
						items : [{
							xtype : "combo",
							fieldLabel : "计量点方向",
							triggerAction : "all",
							typeAhead : true,
							itemId : "inOut",
							hiddenName : "inOut",
							mode : "local",
							width : 100,
							emptyText : "请选择计量点方向",
							displayField : "name",
							valueField : "value",
							store : new Ext.data.ArrayStore({
										fields : ["value", "name"],
										data : [[1, "流入"], [0, "流出"],
												[-1, "未定义"]]
									}),
							selectOnFocus : true
						}]
					}, {
						columnWidth : .33,
						layout : "form",
						labelAlign : "right",
						labelWidth : 80,
						items : [{
							xtype : "combo",
							fieldLabel : "计量点类型",
							triggerAction : "all",
							typeAhead : true,
							itemId : "typeCode",
							hiddenName : "typeCode",
							mode : "local",
							width : 100,
							emptyText : "请选择计量点类型",
							displayField : "name",
							valueField : "value",
							store : new Ext.data.ArrayStore({
										fields : ["value", "name"],
										data : [["01", "用电客户"], ["02", "关口"],
												["03", "台区关口"],
												["04", "变电站关口"], ["05", "电厂关口"]]
									}),
							selectOnFocus : true

						}],
						border : false
					}, {
						columnWidth : .34,
						border : false,
						labelAlign : "right",
						labelWidth : 60,
						layout : "form",
						items : [{
									xtype : "combo",
									fieldLabel : "计量点性质",
									triggerAction : "all",
									typeAhead : true,
									mode : "local",
									width : 100,
									itemId : "mpAttrCode",
									hiddenName : "mpAttrCode",
									emptyText : "请选择计量点性质",
									displayField : "name",
									valueField : "value",
									store : new Ext.data.ArrayStore({
												fields : ["value", "name"],
												data : [["01", "结算"],
														["02", "考核"]]
											}),
									selectOnFocus : true
								}]
					}]
				});

		function itemId(panel, id) {
			return panel.find("itemId", id)[0];
		}
		var show = (function() {
			if (cgs.get("chkunitTypeCode") == "01")
				return false;
			if (cgs.get("chkunitTypeCode") == "02")
				return true;
		})()
		var tmnlAddrCombo = new Ext.form.ComboBox({
			fieldLabel : "终端地址",
			triggerAction : "all",
			typeAhead : true,
			mode : "remote",
			hiddenName : "tmnlAddr",
			itemId : "tmnlAddr",
			width : 100,
			emptyText : "请选择终端地址",
			displayField : "terminalAddr",
			valueField : "terminalAddr",
			store : new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : './qrystat/losePowerMan!findTmnlAddr.action'
								}),
						baseParams : {
							gid : selectChkunit,
							typeCode : cgs.get("chkunitTypeCode")
						},
						reader : new Ext.data.JsonReader({
									root : 'resultList'
								}, [{
											name : 'terminalAddr'
										}]),
						listeners : {
							"callback" : function() {
								// var v=null;
								// tmnlAddrCombo.getStore().each(function(r){
								// v=r.get("value");
								// return false;
								// });
								// tmnlAddrCombo.collapse();
								// tmnlAddrCombo.setValue(v);
							}
						}
					}),
			selectOnFocus : true,
			listeners : {
				change : function(me, now, old) {
					frmCombo.getStore().load({
								params : {
									tmnlAddr : now
								}
							});
				}
				// ,
				// render : function() {
				// tmnlAddrCombo.onTriggerClick();
				// }
			}
		});

		var frmCombo = new Ext.form.ComboBox({
			fieldLabel : "采集器资产号",
			triggerAction : "all",
			typeAhead : true,
			mode : "local",
			hiddenName : "fmrAssetNo",
			itemId : "fmrAssetNo",
			width : 100,
			emptyText : "请选择采集器资产号",
			displayField : "fmrAssetNo",
			valueField : "fmrAssetNo",
			store : new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : './qrystat/losePowerMan!findFrmAsset.action'
								}),
						reader : new Ext.data.JsonReader({
									root : 'resultList'
								}, [{
											name : 'fmrAssetNo'
										}])

					}),
			selectOnFocus : true
		});

		// 第二行
		var second = new Ext.Panel({
					border : false,
					layout : "column",
					items : [{
								columnWidth : .33,
								border : false,
								items : [{
											border : false,
											layout : "form",
											labelAlign : "right",
											labelWidth : 85,
											items : [tmnlAddrCombo]
										}]
							}, {
								columnWidth : .33,
								border : false,
								labelAlign : "right",
								labelWidth : 80,
								border : false,
								layout : "form",
								hidden : !show,
								items : [frmCombo]
							}, {
								columnWidth : .25,
								border : false,
								labelAlign : "right",
								labelWidth : 80,
								border : false,
								hidden : show,
								layout : "form",
								items : [{
									xtype : "combo",
									fieldLabel : "用户类型",
									triggerAction : "all",
									typeAhead : true,
									mode : "local",
									width : 80,
									itemId : "consType",
									hiddenName : "consType",
									emptyText : "请选择用户类型",
									displayField : "name",
									valueField : "value",
									store : new Ext.data.ArrayStore({
												fields : ["value", "name"],
												data : [["1", "专变"],
														["2", "公变"],
														["4", "线路"],
														["7", "变电站"]]
											}),
									selectOnFocus : true
								}]
							}, {
								border : false,
								columnWidth : .09,
								items : [{
									xtype : "button",
									text : "查询",
									width : 50,
									handler : function() {
										ioStore.baseParams = {
											typeCode : cgs
													.get("chkunitTypeCode"),
											gid : selectChkunit,
											"mmFinder.typeCode" : itemId(first,
													"typeCode").getValue(),
											"mmFinder.mpAttrCode" : itemId(
													first, "mpAttrCode")
													.getValue(),
											"mmFinder.fmrAssetNo" : itemId(
													second, "fmrAssetNo")
													.getValue(),
											"mmFinder.tmnlAddr" : tmnlAddrCombo
													.getValue(),
											"mmFinder.inOut" : itemId(first,
													"inOut").getValue(),
											"mmFinder.consType" : itemId(
													second, "consType")
													.getValue()
										};

										ioStore.load();
									}
								}]
							}]

				});
		// 上部的查询panel
		var topPanel = new Ext.Panel({
					border : false,
					region : "north",
					layout : "form",
					padding : "5px 0 0 0",
					height : 60,
					items : [first, second]
				});
		var comboxPqFlag = new Ext.form.ComboBox({
					width : 60,
					mode : "local",
					itemId : "pqFlag",
					hiddenName : "pqFlag",
					triggerAction : "all",
					typeAhead : true,
					editable : false,
					selectOnFocus : true,
					displayField : "name",
					valueField : "value",
					store : new Ext.data.ArrayStore({
								fields : ["value", "name"],
								data : [["81", "正向有功总"], ["82", "反向有功总"]]
							})
				});
		var sm = new Ext.grid.CheckboxSelectionModel({});
		var cm = new Ext.grid.ColumnModel([sm, {
					header : "用户编号",
					tooltip : "用户编号",
					dataIndex : "consNo"
				}, {
					header : "用户名称",
					tooltip : "用户名称",
					dataIndex : "consName"
				}, {
					header : "计量点编号",
					tooltip : "计量点编号",
					dataIndex : "mpNo"
				}, {
					header : "计量点名称",
					tooltip : "计量点名称",
					dataIndex : "mpName"
				}, {
					header : "计量点主要分类",
					tooltip : "计量点主要分类",
					dataIndex : "typeCode",
					renderer : function(v) {
						return typeMp[v]
					}
				}, {
					header : "计量点性质",
					tooltip : "计量点性质",
					dataIndex : "mpAttrCode",
					renderer : function(v) {
						return attrMp[v];
					}
				}, {
					header : "计量点用途",
					tooltip : "计量点用途",
					dataIndex : "usageTypeCode",
					renderer : function(v) {
						return userMp[v];
					}
				}, {
					header : "电量标志",
					tooltip : "电量标志",
					editor : comboxPqFlag,
					dataIndex : "pqFlag",
					renderer : function(v, m, r) {
						if (v == -1) {
							return "正向有功总";
						}
						if (v == 81) {
							return "正向有功总"
						}
						if (v == 82) {
							return "反向有功总";
						}

					}
				}, {
					header : "计量点方向",
					tooltip : "计量点方向",
					dataIndex : "isIn",
					renderer : function(v) {
						if (v == "1") {
							return "流入";
						}
						if (v == "0") {
							return "流出";
						}
						return "未定义";
					}
				}, {
					header : "数据来源",
					tooltip : "数据来源",
					dataIndex : "dataSrc",
					renderer : function(v) {
						return dataSrcMp[v];
					}
				}, {
					header : "是否有效",
					tooltip : "是否有效",
					dataIndex : "ioValid",
					renderer : function(v) {
						if (v == -1) {
							return "未定义";
						}
						if (v == 0) {
							return "无效";
						}
						if (v == 1) {
							return "有效";
						}

					}
				}, {
					header : "电能表资产",
					tooltip : "电能表资产",
					dataIndex : "assetNo"
				}, {
					header : "综合倍率",
					tooltip : "综合倍率",
					dataIndex : "",
					hidden : true
				}]);

		var ioStore = new Ext.data.JsonStore({
					url : './qrystat/losePowerMan!findMeterMp.action',
					fields : ["mpNo", "mpName", "typeCode", "mpAttrCode",
							"pqFlag", "usageTypeCode", "consNo", "consName",
							"assetNo", "tmnlAssetNo", "terminalAddr",
							"meterId", "mpId", "dataId", "dataSrc", "tgId",
							"isIn", "ioValid", "lineId"],
					root : "resultList",
					totalProperty : "resultCount",
					baseParams : {
						gid : selectChkunit,
						typeCode : cgs.get("chkunitTypeCode")
					},
					autoLoad : true
				});
		var centerIoGrid = new Ext.grid.EditorGridPanel({
					region : "center",
					// height:300,
					cm : cm,
					autoScroll : true,
					sm : sm,
					store : ioStore,
					viewConfig : {
						forceFit : false
					},
					bbar : new Ext.ux.MyToolbar({
								store : ioStore,
								enableExpAll : true
							}),
					listeners : {
						beforeedit : function(e) {
							var column = cm.findColumnIndex("pqFlag");
							// 得到当前的行
							if (e.column == column) {
								// var r = e.record;
								// var isIn = r.get("isIn");
								//
								// if (r.get("pqFlag") == "-1") {
								// r.set("pqFlag", "未定义");
								// }
							}
						}
					}

				});
		// 中部的grid
		// 大panel
		var panel = new Ext.Panel({
					border : false,
					region : "center",
					layout : "border",
					items : [topPanel, centerIoGrid]
				});

		var win = new Ext.Window({
			title : "入口出口",
			border : false,
			width : 900,
			height : 550,
			modal : true,
			layout : "border",
			items : [panel],
			buttons : [{
						xtype : "label",
						hidden : !isSelf(),
						html : '<font color="red">流入流出</font>',
						style : "font:12px tahoma,arial,helvetica,sans-serif;"
					}, {
						xtype : "combo",
						width : 60,
						mode : "local",
						hidden : !isSelf(),
						hiddenName : "unitFinder.io",
						triggerAction : "all",
						typeAhead : true,
						selectOnFocus : true,
						editable : false,
						itemId : "defined_io",
						emptyText : "请选择考核方向",
						displayField : "name",
						valueField : "value",
						value : -1,
						store : new Ext.data.ArrayStore({
									fields : ["value", "name"],
									data : [[-1, "请选择"], [0, "流出"], [1, "流入"]]
								})
					}, {
						xtype : "label",
						hidden : !isSelf(),
						html : '是否有效'
					}, {
						xtype : "combo",
						width : 60,
						mode : "local",
						hidden : !isSelf(),
						itemId : "defined_isValid",
						hiddenName : "unitFinder.isValid",
						triggerAction : "all",
						typeAhead : true,
						editable : false,
						selectOnFocus : true,
						emptyText : "请选择是否有效",
						displayField : "name",
						valueField : "value",
						value : 1,
						store : new Ext.data.ArrayStore({
									fields : ["value", "name"],
									data : [[1, "有效"], [0, "无效"]]
								})
					}, {
						xtype : "button",
						text : "保存选择",
						hidden : !isSelf(),
						handler : function(btn) {
							var bbar = btn.ownerCt;
							var ioCk = bbar.find("itemId", "defined_io")[0];
							var ck = bbar.find("itemId", "defined_io")[0]
									.getValue();

							if (ck == -1) {
								return !!Ext.Msg.alert("提示", "请配置计量点方向");
							}

							var typeCode = cgs.get("chkunitTypeCode");
							var objIdName = typeCode == "01" && "lineId"
									|| typeCode == "02" && "tgId";
							var url = './qrystat/losePowerMan!#method.action';
							url = url.replace("#method", ck && "mergeInMeter"
											|| "mergeOutMeter");
							var params = {
								mpIds : [],
								meterIds : [],
								dataIds : [],
								objIds : [],
								pqFlags : [],
								typeCode : typeCode,
								isVaild : bbar
										.find("itemId", "defined_isValid")[0]
										.getValue(),
								gid : selectChkunit
							};

							var ss = sm.getSelections();
							if (ss.length == 0) {
								return !!Ext.Msg.alert("提示", "请选择计量点");
							}
							Ext.each(ss, function(r) {
										params.mpIds.push(r.get("mpId"));
										params.meterIds.push(r.get("meterId"));
										params.objIds.push(r.get(objIdName));
										params.dataIds.push(r.get("dataId"));
										params.pqFlags.push(r.get("pqFlag"));
									});

							Ext.Ajax.request({
								url : url,
								params : params,
								success : function(response) {
									var o = Ext.decode(response.responseText);
									if (o && o.message
											&& o.message.trim() != "") {
										return !!Ext.Msg.alert("提示", o.message);
									}
									Ext.Msg.alert("提示", "定义成功");
									ioCk.setValue(-1);
									centerIoGrid.getStore().reload();
								}
							});
						}
					}, {
						text : "保存所有",
						hidden : !isSelf(),
						handler : function(btn) {
							var arr = first.find("itemId", "inOut");
							var bbar = btn.ownerCt;
							var ioCk = bbar.find("itemId", "defined_io")[0];
							var ck = bbar.find("itemId", "defined_io")[0]
									.getValue();
							if (ck == -1) {
								return !!Ext.Msg.alert("提示", "请配置计量点方向");
							}
							var url = './qrystat/losePowerMan!#method.action';
							url = url.replace("#method", ck && "mergeInMeter"
											|| "mergeOutMeter");
							var typeCode = cgs.get("chkunitTypeCode");
							var params = {
								allSave : true,
								typeCode : typeCode,
								isVaild : bbar
										.find("itemId", "defined_isValid")[0]
										.getValue(),
								gid : selectChkunit
							};
							Ext.apply(params,
									centerIoGrid.getStore().baseParams);

							// Ext.each(ss, function(r) {
							// params.mpIds.push(r.get("mpId"));
							// params.meterIds.push(r.get("meterId"));
							// });
							Ext.Ajax.request({
								url : url,
								params : params,
								success : function(response) {
									var o = Ext.decode(response.responseText);
									if (o && o.message
											&& o.message.trim() != "") {
										return !!Ext.Msg.alert("提示", o.message);
									}
									Ext.Msg.alert("提示", "定义成功");
									ioCk.setValue(-1);
									centerIoGrid.getStore().reload();
								}
							});
						}
					}, {
						text : "退出",
						handler : function() {
							win.close();
						}
					}]
		});
		win.show();
		return win;

	}
	// tg.ioWindow();

	/* 左边树监听 */
	/** ********** */
	new LeftTreeListener({
				modelName : '考核单元管理',
				processClick : function(p, node, e) {
					if (cardPanel.layout.activeItem != newTg_panel) {
						return;
					}

					var obj = node.attributes.attributes;
					var type = node.attributes.type;
					var cgs = centerGridSM.getSelections();
					var typeCode = typeNew || cgs[0].get("chkunitTypeCode");
					if (typeCode == "01") {
						if (type != "org" && type != "sub" && type != "line"
								|| obj.orgType == "02") {
							return !!Ext.Msg.alert("提示", "不支持的节点");
						}
						itemFromId(topLine, "nodeName").setValue(obj.orgName
								|| obj.lineName || obj.subsName);
					} else if (typeCode == "02") {
						if (type != "org" && type != "line"
								|| obj.orgType == "02") {
							return !!Ext.Msg.alert("提示", "不支持的节点");
						}
						itemFromId(topTq, "nodeName").setValue(obj.orgName
								|| obj.lineName || obj.subsName);
					}
					nodeStr = node.id;

					// Ext.Ajax.request({
					// url : './qrystat/losePowerMan!leftTreeClick.action',
					// params : {
					// start : 0,
					// limit : 400,
					// "node" : type == "usr"
					// ? ("tmnl_" + obj.tmnlAssetNo + "_" + obj.tmnlAssetNo)
					// : node.id
					// },
					// success : function(response) {
					// var o = Ext.decode(response.responseText);
					// o && o.message && o.message.trim() != "" && (function() {
					// Ext.Msg.alert("提示", o.message);
					// })();
					// newTg_unitStore.addDatas(o.resultList);
					//
					// }
					// });

				},
				processUserGridSelect : function(sm, row, r) {
					if (cardPanel.layout.activeItem != newTg_panel) {
						return;
					}
					var tmnl = r.get("tmnlAssetNo");
					if (r.get("consType") != "2") {
						return !!Ext.Msg.alert("提示", "请在台区下查询");
					}
					nodeStr = "tmnl_" + tmnl + "_01";
					itemFromId(topTq, "nodeName").setValue(r.get("consName"));
					// Ext.Ajax.request({
					// url : './qrystat/losePowerMan!leftTreeClick.action',
					// params : {
					// start : 0,
					// limit : 50,
					// "node" : "tmnl_" + tmnl + "_01"
					// },
					// success : function(response) {
					// var o = Ext.decode(response.responseText);
					// o && o.message && o.message.trim() != ""
					// && (function() {
					// Ext.Msg.alert("提示", o.message);
					// })();
					// newTg_unitStore.addDatas(o.resultList);
					// }
					// });
				}
			});
	// 调整汇总电量和调整计量点电量的窗口
	/**
	 * @param title标题
	 * @param fn
	 *            在点击保存的时候执行的闭包
	 */
	tg.adjustWin = function(title, fn) {
		// 第一行
		var first = new Ext.Panel({
					border : false,
					layout : "column",
					items : [{
								columnWidth : .25,
								border : false,
								labelAlign : "right",
								layout : "form"
							}]
				});
		// 第二行
		// 第三行
		// 第四行
		var formPanel = new Ext.FormPanel({
					border : false,
					region : "center",
					layout : "form",
					items : []
				});
		var win = new Ext.Window({
					title : "入口出口",
					border : false,
					width : 650,
					height : 450,
					layout : "border",
					items : [formPanel],
					buttons : [{
								text : "保存"
							}, {
								text : "退出",
								handler : function() {
									win.close();
								}
							}]
				});
	}
	// 管理考核单元已经台区
	tg.managerExistsWin = function() {
		var obj = getTypeInfo();
		if (obj.type == "02") {
			obj.url = './qrystat/losePowerMan!findTgs.action';
		} else if (obj.type == "01") {
			obj.url = './qrystat/losePowerMan!findExistLine.action';
		}
		var sm = new Ext.grid.CheckboxSelectionModel({});
		var store = new Ext.data.JsonStore({
					url : obj.url,
					idProterty : "compId",
					totalProperty : "resultCount",
					root : "resultList",
					fields : [{
								name : 'compId'
							}, {
								name : 'chkunitId'
							}, {
								name : 'objId'
							}, {
								name : 'objTypeCode'
							}, {
								name : 'regTime'
							}, {
								name : obj.no + 'Name'
							}, {
								name : "terminalAddr"
							}, {
								name : obj.no + 'No'
							}]
				});
		var cm = new Ext.grid.ColumnModel([sm, {
					header : obj.name + "id",
					dataIndex : "objId"
				}, {
					header : obj.name + "名称",
					dataIndex : obj.no + "Name"
				}, {
					header : "注册时间",
					dataIndex : "regTime",
					hidden : true,
					renderer : function(v) {
						v = v.replace(/-/g, "/");
						v = v.replace(/T/g, " ");
						v = new Date(v).format("Y年m月d日 H:i:s");
						return v;
					}
				}, {
					header : "终端地址",
					dataIndex : "terminalAddr",
					hidden : obj.type == "01" || obj.type == "02"
				}]);

		var grid = new Ext.grid.GridPanel({
			border : false,
			region : "center",
			sm : sm,
			cm : cm,
			viewConfig : {
				forceFit : true
			},
			store : store,
			tbar : ["->", {
				xtype : "button",
				text : "删除选中",
				hidden : !isSelf(),
				iconCls : "cancel",
				handler : function() {
					// check 提示是否删除
					var ss = sm.getSelections();
					if (ss.length == 0) {
						return !!Ext.Msg.alert("提示", "请选择一条数据");
					}
					var params = {
						gid : selectChkunit,
						tgIds : []
					};
					Ext.each(ss, function(r) {
								params.tgIds.push(r.get("objId"));
							});
					Ext.MessageBox.show({
						title : "警告",
						msg : "是否删除选中" + obj.name,
						icon : Ext.MessageBox.WARNING,
						buttons : Ext.MessageBox.YESNO,
						fn : function(msg) {
							if (msg == "yes") {
								Ext.Ajax.request({
									url : './qrystat/losePowerMan!deleteGchkunit.action',
									params : params,
									success : function(response) {
										var o = Ext
												.decode(response.responseText);
										if (o && o.message
												&& o.message.trim() != "") {
											return !!Ext.Msg.alert("提示",
													o.message);
										}
										store.reload();
										uiChange.getStore().reload();
									}
								});
							}
						}
					});

				}
			}],
			bbar : new Ext.ux.MyToolbar({
						store : store,
						enableExpAll : true
					})
		});
		store.baseParams = {
			gid : selectChkunit
		}
		store.load({
					params : {
						start : 0,
						limit : 50
					}
				});
		var win = new Ext.Window({
					title : "管理此考核单元已经存在的" + obj.name,
					border : false,
					width : 650,
					modal : true,
					height : 450,
					layout : "border",
					items : [grid],
					buttons : [{
								text : "退出",
								handler : function() {
									win.close();
								}
							}]
				});
		win.show();
		return win;
	}
	// 生成一个管理考核指标的界面
	tg.refWindow = function(name) {

		// first
		var first = new Ext.Panel({
					border : false,
					layout : "column",
					items : [{
								border : false,
								columnWidth : .5,
								layout : "form",
								labelWidth : 72,
								labelAlign : "right",
								items : [{
											fieldLabel : "参考指标名称",
											width : 100,
											readOnly : !isSelf(),
											itemId : "gr.llIdxName",
											xtype : "textfield"
										}]
							}, {
								border : false,
								columnWidth : .5,
								layout : "form",
								labelAlign : "right",
								items : [{
											xtype : "numberfield",
											width : 100,
											readOnly : !isSelf(),
											itemId : "gr.llIdxValue",
											fieldLabel : "参考指标值"
										}]
							}]
				});
		var second = new Ext.Panel({
					border : false,
					layout : "column",
					items : [{
								columnWidth : .15,
								xtype : "label",
								html : "考核周期"
							}, {
								columnWidth : .6,
								xtype : "checkboxgroup",
								itemId : "chkCycle",
								readOnly : !isSelf(),
								border : false,
								items : [{
											boxLabel : "日",
											name : "time"
										}, {
											boxLabel : "月",
											name : "time",
											checked : true
										}, {
											boxLabel : "季",
											name : "time"
										}, {
											boxLabel : "半年",
											name : "time"
										}, {
											boxLabel : "年",
											name : "time"
										}]
							}]
				});
		var panel = new Ext.FormPanel({
					border : false,
					region : "center",
					padding : "10px 0 0 10px",
					layout : "form",
					items : [first, second]
				});
		var win = new Ext.Window({
			title : "管理考核指标",
			border : false,
			width : 450,
			modal : true,
			height : 150,
			layout : "border",
			items : [panel],
			buttons : [{
				text : "保存",
				handler : function() {
					var ps = {};
					ps["gr.llIdxName"] = win.it("gr.llIdxName").getValue();
					ps["gr.llIdxValue"] = win.it("gr.llIdxValue").getValue();
					ps["gr.chkunitId"] = win.chkunitId;
					var cs = win.it("chkCycle").getValue();
					// 使用数组来处理字符串能显著的提高效率
					var rs = ["0", "0", "0", "0", "0"];
					Ext.each(cs, function(c) {
								if (c.boxLabel == "日") {
									rs.splice(0, 1, "1");
								} else if (c.boxLabel == "月") {
									rs.splice(1, 1, "1");
								} else if (c.boxLabel == "季") {
									rs.splice(2, 1, "1");
								} else if (c.boxLabel == "半年") {
									rs.splice(3, 1, "1");
								} else if (c.boxLabel == "年") {
									rs.splice(4, 1, "1");
								}
							});
					ps["gr.chkCycle"] = rs.join("");
					Ext.Ajax.request({
						url : './qrystat/losePowerMan!saveOrUpdateRefByGid.action',
						params : ps,
						success : function(response) {
							var o = Ext.decode(response.responseText);
							if (o && o.message && o.message.trim() != "") {
								return !!Ext.Msg.alert("提示", o.message);
							}
							Ext.Msg.alert("提示", "处理成功");
							win.close();
							// win.close();
						}
					});
				}
			}, {
				text : "删除",
				itemId : "del",
				hidden : true,
				handler : function() {
					Ext.MessageBox.show({
						title : "警告",
						msg : "是否删除考核指标",
						icon : Ext.MessageBox.WARNING,
						buttons : Ext.MessageBox.YESNO,
						fn : function(msg) {
							if (msg == "yes") {
								Ext.Ajax.request({
									url : './qrystat/losePowerMan!delRefByGid.action',
									params : {
										gid : selectChkunit
									},
									success : function(response) {
										var o = Ext
												.decode(response.responseText);
										if (o && o.message
												&& o.message.trim() != "") {
											return !!Ext.Msg.alert("提示",
													o.message);
										}
										Ext.Msg.alert("提示", "删除成功");
										win.close();
										// win.close();
									}
								});
								// win.chkunitId = undefined;
							}
						}
					});

				}
			}, {
				text : "退出",
				handler : function() {
					win.close();
				}
			}]
		});

		win.it = function(id) {
			return win.find("itemId", id)[0];
		}

		// 进行一次ajax请求，将后台的数据取回来，如果取到了值，就将值填到窗体中
		Ext.Ajax.request({
					url : './qrystat/losePowerMan!findRefByGid.action',
					params : {
						gid : selectChkunit
					},
					success : function(response) {
						var o = Ext.decode(response.responseText);
						if (o && o.message && o.message.trim() != "") {
							return !!Ext.Msg.alert("提示", o.message);
						}
						var m = o.map;

						win.chkunitId = selectChkunit;
						if (!m) {
							if (!isSelf()) {
								return Ext.Msg.alert("提示", "创建者还未添加参考指标");
							}
							win.setTitle("新建参考指标");
							Ext.MessageBox.show({
										title : "警告",
										msg : "当前考核单元不存在参考指标，是否新建",
										icon : Ext.MessageBox.WARNING,
										buttons : Ext.MessageBox.YESNO,
										fn : function(msg) {
											if (msg == "yes") {

												win.show();
												// win.chkunitId = undefined;
											} else {
												win.close();
											}
										}
									});

							return;
						}
						win.show();
						Ext.each(win.buttons, function(b) {
									if (b.itemId == "del") {
										b.show();
									}
								});
						win.it("gr.llIdxName").setValue(m["llIdxName"]);
						win.it("gr.llIdxValue").setValue(m["llIdxValue"]);
						var strs = m.chkCycle;
						win.it("chkCycle").setValue(strs.split(""));
						// win.close();
					}
				});
		return win;

	}
	// 生成一个提示有已经注册台区信息的grid,
	// 注意这个方法的异步性，win不是立即返回的
	function showEchoGrid(config, fn) {
		var cgs = centerGridSM.getSelected();
		function type() {
			var a = typeNew || cgs.get("chkunitTypeCode");
			if (a == "01") {
				return {
					no : "line",
					name : "线路",
					url : './qrystat/losePowerMan!checkExistLine.action',
					fields : ["lineId", "lineNo", "lineName", "runStatusCode",
							"chkunitName", "orgName", "respEmpNo", "chkCycle",
							"detSortCode", "chkunitId"]
				}
			} else if (a == "02") {
				return {
					no : "tg",
					name : "台区",
					url : './qrystat/losePowerMan!checkNewTgs.action',
					fields : ["tgId", "tgNo", "tgName", "runStatusCode",
							"chkunitName", "orgName", "respEmpNo", "chkCycle",
							"detSortCode", "orgNo", "chkunitId"]
				}
			}
		}
		var obj = type();
		var cm = new Ext.grid.ColumnModel([{
					header : obj.name + "编号",
					dataIndex : obj.no + "No"
				}, {
					header : obj.name + "名称",
					dataIndex : obj.no + "Name"
				}, {
					header : "运行状态",
					dataIndex : "runStatusCode",
					renderer : function(v) {
						return v == "01" && "正在运行" || "未运行";
					}
				}, {
					header : "所属考核单元",
					dataIndex : "chkunitName"
				}, {
					header : "考核单元ID",
					dataIndex : "chkunitId"
				}]);
		var store = new Ext.data.JsonStore({
					url : obj.url,
					fields : obj.fields,
					root : "resultList",
					totalProperty : "resultCount"
				});

		store.baseParams = config.params;
		var grid = new Ext.grid.GridPanel({
					border : false,
					region : "center",
					cm : cm,
					store : store
				});
		store.load({
					params : {
						start : 0,
						limit : 50
					},
					callback : function() {
						// 如果没有注册过
						if (store.getTotalCount() == 0) {
							(typeof fn == "function") && (fn());
							return;
						}
						// 如果注册过
						var win = new Ext.Window({
									title : "下列" + obj.name + "已经注册，请选择是否继续注册",
									border : false,
									width : 650,
									modal : true,
									height : 450,
									layout : "border",
									items : [grid],
									buttons : [{
										text : "继续注册",
										handler : function() {
											(typeof fn == "function")
													&& (fn(win))
										}
									}, {
										text : "取消",
										handler : function() {
											win.close();
										}
									}]
								});
						win.show();

					}
				});

	}

	// 生成一个编辑参考指标的页面
	function managerRefWin(chkunitId) {
		// chkunitId = 600000028;
		var store = new Ext.data.JsonStore({
					url : './qrystat/losePowerMan!findRefs.action',
					fields : ["llIdxId", "chkunitName", "llIdxName", "dataSrc",
							"llIdxValue", "chkCycle", "chkunitId", "msg"],
					root : "list"
				});
		function selectEditor(index) {
			var rindex = store.indexOf(sm.getSelected());
			return cm.getCellEditor(rindex, index);
		}
		var sm = new Ext.grid.CheckboxSelectionModel({});
		var cm = new Ext.grid.ColumnModel({
			columns : [sm, {
				header : "编辑状态",
				dataIndex : "msg",
				hidden : true,
				renderer : function(v) {
					return v == "db" && "数据库" || v == "edit" && "已经编辑"
							|| v == "new" && "新建" || v == "del" && "删除";
				}
			}, {
				header : "参考指标名称",
				dataIndex : "llIdxName",
				editor : new Ext.form.TextField({
							allowBlank : false,
							maskRe : /[^~]/,
							listeners : {
								change : function() {
									var r = sm.getSelected();
									if (r.get("msg") != "new") {
										r.set("msg", "edit");
									}
								}
							}
						})
			}, {
				header : "参考指标值",
				dataIndex : "llIdxValue",
				editor : new Ext.form.NumberField({
							allowBlank : false,
							maskRe : /[^~]/,
							listeners : {
								change : function() {
									var r = sm.getSelected();
									if (r.get("msg") != "new") {
										r.set("msg", "edit");
									}
								}
							}
						})
			}, {
				header : "参考指标周期",
				dataIndex : "chkCycle",
				editor : new Ext.form.ComboBox({
							width : 60,
							mode : "local",
							itemId : "cycle",
							hiddenName : "cycle",
							triggerAction : "all",
							typeAhead : true,
							editable : false,
							selectOnFocus : true,
							emptyText : "请选择周期",
							displayField : "name",
							valueField : "value",
							value : 1,
							maskRe : /[^~]/,
							store : new Ext.data.ArrayStore({
										fields : ["value", "name"],
										data : [["10000", "日"], ["01000", "月"],
												["00100", "季"],
												["00010", "半年"], ["00001", "年"]]
									}),
							allowBlank : false,
							listeners : {
								change : function() {
									var r = sm.getSelected();
									if (r.get("msg") != "new") {
										r.set("msg", "edit");
									}
								},
								beforeselect : function(me, record) {
									var map = {
										"10000" : false,
										"01000" : false,
										"00100" : false,
										"00010" : false,
										"00001" : false
									};
									store.each(function(r) {
												(map[r.get("chkCycle")] = true);
											});
									map[this.value] = false;
									if (map[record.get(this.valueField)]) {
										Ext.Msg.alert("提示",
												converDataFromBs(record
														.get(this.valueField))
														+ "考核单元已经存在");
										// this.setValue(this);
										return false;
									}
									return true;
								}
							}
						}),
				renderer : function(v) {
					return converDataFromBs(v);
				}
			}, {
				header : "数据来源",
				dataIndex : "dataSrc",
				renderer : function(v) {
					return v == "01" && "营销系统" || v == "02" && "采集系统"
				}
			}],
			isCellEditable : function() {
				return isSelf();
			}
		});
		var grid = new Ext.grid.EditorGridPanel({
					border : false,
					cm : cm,
					sm : sm,
					region : "center",
					tbar : [{
								xtype : "button",
								text : "添加",
								hidden : !isSelf(),
								iconCls : "plus",
								handler : function() {
									add();
								}
							}, {
								xtype : "button",
								text : "删除",
								hidden : !isSelf(),
								iconCls : "cancel",
								handler : function() {
									var ss = sm.getSelections();
									Ext.each(ss, function(r) {
												if (r.get("msg") != "new") {
													r.set("msg", "del");
												}
											});
									store.filterBy(function(r) {
												if (r.get("msg") == "del") {
													return false;
												}
												return true;
											});
									var rm = [];
									Ext.each(ss, function(r) {
												if (r.get("msg") != "del") {
													rm.push(r);
												}
											});
									store.remove(rm);
									// add();
								}
							}],
					store : store,
					listeners : {
						beforeedit : function(ele) {
							var r = ele.record;
							if (ele.column == cm.findColumnIndex("chkCycle")
									&& r.get("msg") != "new") {
								return false;
							}
						}
					}
				});
		store.load({
					params : {
						gid : chkunitId
					}
				});

		// 一个方法，向这个grid里面新添加一条数据
		function add() {
			var chkCycle = undefined;
			var map = {
				"10000" : true,
				"01000" : true,
				"00100" : true,
				"00010" : true,
				"00001" : true
			};
			store.each(function(r) {
						map[r.get("chkCycle")] = false;
					});
			for (k in map) {
				if (map[k] === true) {
					chkCycle = k;
					break;
				}
			}
			if (!chkCycle) {
				return !!Ext.Msg.alert("提示", "无法添加，所有类型考核周期都已经添加");
			}
			var data = {
				"llIdxId" : "",
				"llIdxName" : "",
				"chkunitId" : chkunitId,
				"llIdxValue" : "",
				"chkCycle" : chkCycle,
				"dataSrc" : "02"
			};
			data.msg = "new";
			var r = new Ext.data.Record(data);
			store.add(r);
		}

		var win = new Ext.Window({
			title : "参考指标",
			border : false,
			width : 650,
			modal : true,
			height : 450,
			layout : "border",
			items : [grid],
			buttons : [{
				text : "保存",
				hidden : !isSelf(),
				handler : function() {
					// 验证
					var isOk = true;
					var header = "";
					var rindex = 0;
					var len = cm.getColumnCount();
					store.each(function(r, index) {
								for (var i = 1; i < len; i++) {
									var dx = cm.getDataIndex(i);
									if (dx == "llIdxId" || dx == "chkunitName") {
										continue;
									}
									var v = r.get(dx);
									if (Ext.isEmpty(v)) {
										rindex = index + 1;
										header = cm.getColumnHeader(i);
										isOk = false;
										return false;
									}
								}
							});
					if (!isOk) {
						return !!Ext.Msg.alert("提示", "第" + rindex + "行的列:"
										+ header + "不能为空");
					}
					var params = {
						nodes : []
					};
					store.clearFilter();
					store.each(function(r) {
								if (r.get("msg") != "db") {
									params.nodes.push(r.get("msg") + "~"
											+ r.get("llIdxId") + "~"
											+ chkunitId + "~"
											+ r.get("llIdxName") + "~"
											+ r.get("llIdxValue") + "~"
											+ r.get("chkCycle"));
								}
							});
					if (params.nodes.length == 0) {
						return !!Ext.Msg.alert("提示", "你还没有进行更新操作");
					}
					store.filterBy(function(r) {
								if (r.get("msg") == "del") {
									return false;
								}
								return true;
							});
					Ext.Ajax.request({
								params : params,
								url : './qrystat/losePowerMan!updateRefs.action',
								success : function(response) {
									var o = Ext.decode(response.responseText);
									if (o && o.message
											&& o.message.trim() != "") {
										return !!Ext.Msg.alert("提示", o.message);
									}
									store.reload();
								}
							});
				}
			}, {
				text : "取消",
				handler : function() {
					win.close();
				}
			}]
		});
		win.show();
	}
	// 生成管理
	renderModel(mainPanel, "考核单元管理");

});