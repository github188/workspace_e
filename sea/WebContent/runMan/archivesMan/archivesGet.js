/*
 * ! Author: Dolphin.Li Date:11/25/2009 Description: 档案维护页面EXTJs程序 Update
 * History: none
 */
// 当前选中的radio
var seleRadio = null;
Ext.ns('Ext.ArchivesGetForm');

// 错误map
var errorMap = {};
// grid解锁
function unlockGrid() {
	smArchiver.unlock();
	gridArchiver.onEnable();
	gridArchiver.getBottomToolbar().enable();
	Ext.getCmp('archives_allSelect').setValue(false);
}

// grid加锁
function lockGrid() {
	smArchiver.lock();
	gridArchiver.onDisable();
	gridArchiver.getBottomToolbar().disable();
}

// 读取XML数据文件方法
var storeArchiver = new Ext.ux.LocalStore({
			// load using HTTP
			dataKey : "tmnlAssetNo",
			fields : [{
						name : "consNo"
					}, {
						name : "consName"
					}, {
						name : "tgId"
					}, {
						name : "tmnlAssetNo"
					}, {
						name : "custId"
					}, {
						name : "consType"
					}, {
						name : "orgName"
					}, {
						name : "protocolName"
					}, {
						name : "factoryName"
					}, {
						name : "terminalId"
					}, {
						name : "cpNo"
					}, {
						name : "consId"
					}, {
						name : "dataState"
					}, {
						name : "terminalAddr"
					}
					
					]
		});

var smArchiver = new Ext.grid.CheckboxSelectionModel({});

var rowNumgridArchiver = new Ext.grid.RowNumberer({
			renderer : function(v, p, record, rowIndex) {
				if (this.rowspan) {
					p.cellAttr = 'rowspan="' + this.rowspan + '"';
				}

				storeArchiver.lastOptions.params = storeArchiver.lastOptions.params
						|| {
							start : 0
						};
				return storeArchiver.lastOptions.params.start + rowIndex + 1;
			}

		});
var gridArchiver = new Ext.grid.GridPanel({
	store : storeArchiver,
	tbar : [{
				xtype:"label",
				text : "用户列表"
			}, "-", {
				text : "同步",
				xtype : "button",
				handler : function() {
					if (!smArchiver.getSelected()) {
						return Ext.Msg.alert("错误", "请选择用户");
					}
					// 初始化
					storeArchiver.eachAll(function(o) {
								o["dataState"] = "";
							});
					var consNos = [];
					var consIds = [];
					var cpNos = [];
					var tmnlIds = [];
					var tgIds = [];
					var allCheck = Ext.getCmp("archives_allSelect").getValue();
					if (allCheck) {
						storeArchiver.eachAll(function(o) {
									consNos.push(o["consNo"]);
									consIds.push(o["consId"]);
									cpNos.push(o["cpNo"]);
									tmnlIds.push(o["terminalId"]);
									tgIds.push(o["tgId"]);
								});
					} else {

						Ext.each(smArchiver.getSelections(), function(r) {
									consNos.push(r.get("consNo"));
									consIds.push(r.get("consId"));
									cpNos.push(r.get("cpNo"));
									tmnlIds.push(r.get("terminalId"));
									tgIds.push(r.get("tgId"));
								});
					};

					Ext.Ajax.request({
								url : "runman/archives!doArchive.action",
								success : function(response) {
									var oo = Ext.decode(response.responseText);
									if (!oo.errorMap && oo.message) {
										return Ext.Msg.alert("提示", oo.message);
									}
									if (!oo.errorMap) {
										Ext.Msg.alert("提示", "同步全部成功");
									}
									if (allCheck) {
										storeArchiver.eachAll(function(k, o) {
													if (!oo.errorMap) {
														o["dataState"] = "同步成功";
														return;
													}
													if (oo.errorMap[o["consId"]]) {
														o["dataState"] = "同步失败";
													} else {
														o["dataState"] = "同步成功";
													}
												});

									} else {
										Ext.each(smArchiver.getSelections(),
												function(r) {
													if (!oo.errorMap) {
														r.set("dataState",
																"同步成功");
														return;
													}
													if (oo.errorMap[r
															.get("consId")]) {
														r.set("dataState",
																"同步失败");
													} else {
														r.set("dataState",
																"同步成功");
													}
												});
									}
									storeArchiver.loadCurrent();
									unlockGrid();
								},
								failure : function() {
									return Ext.Msg.alert("提示", "网络错误");
								},
								params : {
									consNos : consNos,
									consIds : consIds,
									cpNos : cpNos,
									tmnlIds : tmnlIds,
									tgIds : tgIds
								}
							});

				}
			}, "->", {
				xtype : "checkbox",
				boxLabel : "全选",
				id : "archives_allSelect",
				listeners : {
					check : function(c, v) {
						if (v) {
							storeArchiver.setAllSelect(true);
							smArchiver.selectAll();
							lockGrid();
						} else {
							unlockGrid();
							storeArchiver.clearAllSelect();
							smArchiver.clearSelections();
						}
					}
				}
			}, "-", {
				text : "删除选中用户",
				iconCls : 'cancel',
				handler : function() {
					if (Ext.getCmp('archives_allSelect').checked) {
						storeArchiver.clearAll();
						unlockGrid();
					} else {
						var selectTerminal = smArchiver.getSelections();
						storeArchiver.removeDatas(selectTerminal);
					}
				}

			}, "-", {
				text : "加入群组",
				iconCls : 'plus',
				handler : function() {
					var groupTmnlArray = [];

					if (Ext.getCmp('archives_allSelect').checked) {
						var alldata = storeArchiver.getBaseData();
						storeArchiver.eachAll(function(k, v) {
									groupTmnlArray.push(v["consNo"] + "`" + k);
								});
					} else {
						var recs = smArchiver.getSelections();
						for (var i = 0; i < recs.length; i++) {
							var tmnl = recs[i].get('consNo') + '`'
									+ recs[i].get('tmnlAssetNo');
							groupTmnlArray[i] = tmnl;
						}
					}
					if (groupTmnlArray.length == 0) {
						Ext.Msg.alert('提示', '请选择要加入群组的用户');
					} else {
						saveOrUpdateGroupWindowShow(groupTmnlArray);
						if (Ext.getCmp('archives_allSelect').checked) {
							unlockGrid();
						}
					}

				}
			}, "-", {
				text : "删除成功用户",
				iconCls : 'minus',
				handler : function() {
				}
			}],
	border : false,
	frame : true,
	sm : smArchiver,
	bbar : new Ext.ux.MyToolbar({
				store : storeArchiver
			}),
	columns : [rowNumgridArchiver, smArchiver, {
				header : "供电单位",
				width : 80,
				dataIndex : 'orgName',
				sortable : true,
				align : 'center'
			}, {
				header : "用户编号",
				width : 100,
				dataIndex : 'consNo',
				sortable : true,
				align : 'left'
			}, {
				header : "用户名称",
				width : 180,
				sortable : true,
				dataIndex : "consName",
				align : 'left'
			}, {
				header : "终端地址",
				width : 80,
				dataIndex : 'terminalAddr',
				sortable : true,
				align : 'center'

			}, {
				header : "规约类型",
				width : 80,
				dataIndex : 'protocolName',
				sortable : true,
				align : 'center'
			}, {
				header : "制造厂商",
				width : 80,
				dataIndex : 'factoryName',
				sortable : true,
				align : 'center'
			}, {
				header : "查看详细",
				dataIndex : "lookDetail",
				renderer : function() {
					return "<a href='#' onclick='return false'>查看详细</a>"
				}
			}, {
				header : "同步状态",
				width : 80,
				dataIndex : 'dataState',
				align : 'center'
			}],
	listeners : {
		"cellclick" : function(g, ri, ci) {
			var r = g.getStore().getAt(ri);
			var dx = g.getColumnModel().getDataIndex(ci);
			window.doShowWin(dx, r);
		}
	}
});
storeArchiver.setGrid(gridArchiver);
var archiverPanel = new Ext.Panel({
			layout : "fit",
			region : "center",
			border : false,
			items : [gridArchiver]
		});

/** **控制弹出的窗口，用来监听cellclick事件** */

window.doShowWin = function(dx, data) {
	var flat = false;
	switch (dx) {
		case "lookDetail" :
			flat = true;
			break;
		case "collectArchives" :
			flat = true;
			break;
		case "meterArchives" :
			flat = true;
			break;
		case "changeArchives" :
			flat = true;
			break;
		case "switchArchives" :
			flat = true;
			break;
		case "userArchives" :
			flat = true;
			break;
	}
	if (flat) {
		window.archivesData = {
			cno : data.get("consNo"),
			tid : data.get("custId")
		};
		openTab("客户综合查询", "./qryStat/queryCollPoint/consumerInfo.jsp");
	}

}

// 终端档案窗口
var tmnlWindow = function() {
	var formPanel = new Ext.FormPanel({
				border : false,
				bodyStyle : "padding:20px 0px 0 px 0px",
				items : [{// 数据显示地一行
					bodyStyle : 'padding:3px 0px 0px 0px',
					layout : 'column',
					labelAlign : 'right',
					border : false,
					items : [{
								layout : 'form',
								columnWidth : .5,
								labelAlign : "right",
								border : false,
								items : [{
											fieldLabel : "终端局号",
											name : "",
											readOnly : true,
											width : 20,
											border : false,
											xtype : "textfield",
											anchor : '90%'
										}]
							}, {
								layout : 'form',
								columnWidth : .5,
								border : false,
								items : [{
											xtype : 'textfield',
											fieldLabel : "终端逻辑地址",
											readOnly : true,
											border : false,
											name : 'TeArLogicalAddress',
											anchor : '90%'
										}]
							}]
				}, {	// 数据显示第2行
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										layout : 'form',
										columnWidth : .5,
										border : false,
										items : [{
													fieldLabel : "制造厂家编码",
													name : "",
													border : false,
													xtype : "textfield",
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "终端型号",
													name : 'TeArModel',
													readOnly : true,
													border : false,
													// blankText:this.TeArModel+this.BlankTextValue,
													// invalidText
													// :this.TeArModel+this.InvalidTextValue,
													anchor : '90%'
												}]
									}]
						}, {// 数据显示第3行
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										layout : 'form',
										columnWidth : .5,
										border : false,
										items : [{
													xtype : 'textfield',
													border : false,
													readOnly : true,
													fieldLabel : "终端类型",
													name : "",
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "SIM卡号",
													name : 'TeArSIMNo',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {// 定义第4行
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "主通信地址",
													name : 'TeArMainAddress',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "短信地址",
													border : false,
													readOnly : true,
													name : 'TeArMessageAddress',
													anchor : '90%'
												}]
									}]
						}, {// 定义第5行
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "APN",
													name : 'TeArAPN',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													fieldLabel : "接线方式",
													xtype : 'textfield',
													name : 'TeArAPN',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {// 定义Form第6行
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													fieldLabel : "规约类型",
													xtype : 'textfield',
													name : 'TeArAPN',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]

									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													fieldLabel : "数据采集方式",
													name : 'TeArAPN',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {// 定义第7行
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "高权限密码",
													name : 'TeArHightPassword',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "低权限密码",
													name : 'TeArLowPassword',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {// 定义第8行
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "CT",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "PT",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {// 定义第9行
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{// 定义一个日期控件
											fieldLabel : "终端安装日期",
											name : 'TeArInstallDate',
											format : 'Y-m-d',
											xtype : "datefield",
											border : false,
											readOnly : true,
											anchor : '90%'
										}]
									}]
						}]
			});
	var win = new Ext.Window({
				border : false,
				title : "终端资产档案",
				layout : "fit",
				width : 500,
				height : 300,
				items : [formPanel]
			});
	win.show();
	return win;
}
// 采集器档案接口
var collectionWin = function() {
	var formPanel = new Ext.FormPanel({
				border : false,
				items : [{
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "采集器局号",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "单位编码",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "终端局号",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "用户编号",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "采集器型号",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "出场日期",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "出场编号",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "厂家编码",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "入库日期",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "安装日期",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "通讯地址",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "安装地址",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "采集器类型",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "运行状态",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "通讯状态",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "通讯端口号",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}]
			});

	var win = new Ext.Window({
				border : false,
				layout : "fit",
				width : 500,
				title : "采集器档案",
				height : 320,
				items : [formPanel]
			});
	win.show();
	return win;

}
// 表计档案
var meterWin = function() {
	var formPanel = new Ext.FormPanel({
				border : false,
				bodyStyle : "padding:20px 0px 0 px 0px",
				items : [{
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "表计局号",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "厂商编码",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "表类型",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "接线方式",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "主规约",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "主规约通信地址",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "主规约通信密码",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "CT",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "PT",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "脉冲常数",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "计量点编号",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "自身倍率",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "位数",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "精度",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "装出日期",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}]
			});

	var win = new Ext.Window({
				border : false,
				title : "表计档案",
				layout : "fit",
				width : 500,
				frame : true,
				height : 320,
				items : [formPanel]
			});
	win.show();
	return win;

}
// 变压器档案
var changeWin = function() {
	var formPanel = new Ext.FormPanel({
				border : false,
				items : [{
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "变压器组号",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "编号",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "型号",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "类型",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "制造厂家",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "铭牌容量",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}]
			});

	var win = new Ext.Window({
				border : false,
				title : "变压器档案",
				layout : "fit",
				width : 500,
				height : 200,
				items : [formPanel]
			});
	win.show();
	return win;

}
// 开关档案
var switchWin = function() {
	var formPanel = new Ext.FormPanel({
				border : false,
				items : [{
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "轮次参数标识",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "采集点编号",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "轮次编号",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "开关类型",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "开关接入标志",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "可控负荷",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}]
			});

	var win = new Ext.Window({
				border : false,
				title : "开关档案",
				layout : "fit",
				width : 500,
				height : 200,
				items : [formPanel]
			});
	win.show();
	return win;

}

// 用户档案
var userWin = function() {

	var formPanel = new Ext.FormPanel({
				border : false,
				items : [{
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "用户编号",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "单位",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "用户名称",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "用户类型",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "供电所",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "供电线路",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "行业",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "受电容量",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "用电属性",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "计量方式",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "供电电压",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "供电方式",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "抄表区码",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "抄表日",
													name : 'TeArPT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}, {
							bodyStyle : 'padding:3px 0px 0px 0px',
							layout : 'column',
							labelAlign : 'right',
							border : false,
							items : [{
										columnWidth : .5,
										layout : 'form',
										border : false,
										items : [{
													xtype : 'textfield',
													fieldLabel : "开户时间",
													name : 'TeArCT',
													border : false,
													readOnly : true,
													anchor : '90%'
												}]
									}]
						}]
			});

	var win = new Ext.Window({
				border : false,
				title : "用户档案",
				layout : "fit",
				width : 500,
				height : 310,
				items : [formPanel]
			});
	win.show();
	return win;
}

// 联系人管理
var contactWin = function() {

	var contactGrid = new Ext.grid.GridPanel({
				title : "查看联系人",
				columns : [{
							header : "联系人姓名",
							dataIndex : ""
						}],
				border : false
			});
	var win = new Ext.Window({
				border : false,
				title : "",
				layout : "fit",
				width : 500,
				height : 400,
				items : [contactGrid]
			});
	win.show();
	return win;
}
function createGridWin(config, cms) {
	var grid = new Ext.grid.GridPanel({
				border : false,
				columns : cms,
				store : config.store
			});
	var win = new Ext.Window({
				border : false,
				title : config.title || "",
				layout : "fit",
				height : 300,
				width : 400,
				items : [grid]
			});
	win.show();
	return win;
}
Ext.onReady(function() {
	new LeftTreeListener({
		modelName : '档案同步',
		processClick : function(p, node, e) {
			var obj = node.attributes.attributes;
			var type = node.attributes.type;
			// var out = Ext.getCmp("archive_type");
			Ext.Ajax.request({
				url : "runman/archives!findClick.action",
				params : {
					"node" : type == "usr"
							? ("tmnl_" + obj.tmnlAssetNo + "_" + obj.tmnlAssetNo
							// + "_" + out.getValue().inputValue
							)
							: node.id
					// + "_"+ out.getValue().inputValue
				},
				success : function(response) {
					var o = Ext.decode(response.responseText);
					storeArchiver.addDatas(o.resultList);
				}
			});

		},
		processUserGridSelect : function(sm, row, r) {
			var tmnl = r.get("tmnlAssetNo");
			// var out = Ext.getCmp("archive_type");

			Ext.Ajax.request({
						url : "runman/archives!findClick.action",
						params : {
							"node" : "tmnl_" + tmnl + "_01"
							// + "_"
							// + out.getValue().inputValue
						},
						success : function(response) {
							var o = Ext.decode(response.responseText);
							storeArchiver.addDatas(o.resultList);
						}
					});
		}
	});

	/**
	 * ========创建档案同步页面================ Note:原OO方式的方法中存在多对象加载在IE浏览器中存在对象丢失现象
	 * 暂时未找到解决方法，重构新方法
	 * 
	 */

	// var archivesGetGrid = new Ext.ArchivesGetForm.ArchivesGetGrid();
	/*
	 * var archivesGridForm = new Ext.Panel({
	 * 
	 * //monitorResize: true, items: [
	 * 
	 * archivesGetGrid ] })
	 */
	var archivesQueryForm = new Ext.FormPanel({
				labelAlign : 'right',
				// width:780,
				border : false,
				monitorResize : true,
				items : [{
							border : false,
							layout : 'column',
							items : [{
										width : 500,
										layout : 'form',
										border : false,
										bodyStyle : 'padding:10px 0px 0px 0px',// 定义容器内元素距离容器边框距离
										// 上 右 下 左
										items : [{
													layout : 'column',
													xtype : 'radiogroup',
													hidden : true,
													labelStyle : "padding:12px 0 0 0",
													style : "padding:10px 0 0 0",
													id : "archive_type",
													fieldLabel : '用户类型',
													hideLabel : true,
													border : false,
													listeners : {
														change : function(rgp,
																r) {
															// 清空所有的数据，但是不load();
															storeArchiver
																	.clearAll();
														}
													},
													name : 'archivesType',
													items : [{
																boxLabel : '专变',
																name : 'rb-auto',
																inputValue : 1,
																checked : true
															}, {
																boxLabel : '公变',
																name : 'rb-auto',
																inputValue : 2
															}, {
																boxLabel : '居民',
																name : 'rb-auto',
																inputValue : 5
															}, {
																boxLabel : '关口',
																name : 'rb-auto',
																inputValue : 3
															}],
													anchor : '100%'
												}]
									}, {
										style : "padding:20px 0 0 50px",
										width : 130,
										xtype : "button",
										text : "&nbsp;&nbsp;同步&nbsp;&nbsp;",
										border : false,
										handler : function() {
										}
									}]
						}]
			});

	// archivesQueryForm.render('archivesGet');

	// archivesGetForm.render('archivesGet');
	var archivseManagerPanel = new Ext.Panel({
				border : false,
				height : 50,
				region : "north",
				// frame:true,
				items : [archivesQueryForm]
			});
	var mainPanel = new Ext.Panel({
				border : false,
				layout : "border",
				bodyStyle : "padding:15",
				items : [{
							xtype : "panel",
							border : false,
							layout : "border",
							region : "center",
							items : [
							// archivseManagerPanel,
							archiverPanel]
						}]
			});
	/***************************************************************************
	 * @param config
	 *            必要的配置项
	 **************************************************************************/

	// var arr=[{header:"hx"},{header:"hx1"},{header:"hx2"}];
	// createGridWin({store:storeArchiver},arr);
	// 一个通用的window显示列表的窗口
	// archivseManagerPanel.render('archivesGet');
	renderModel(mainPanel, '档案同步');
});
