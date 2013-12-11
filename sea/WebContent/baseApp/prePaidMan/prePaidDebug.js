/**
 * 预付费投入调试
 * @author jiangyike
 */

// 去左空格;
function ltrim(s) {
	return s.replace(/^\s*/, "");
}

// 去右空格;
function rtrim(s) {
	return s.replace(/\s*$/, "");
}

// 去左右空格;
function trim(s) {
	return rtrim(ltrim(s));
}

/**
 * 任务进度条
 */
var overFlat = false;// 控制是否隐藏前台进度条

var h_taskTime = function(second, fn) {
	flag = 0;
	overFlat = false;
	var task_s = (second == "") ? 30 : second;
	Ext.MessageBox.show({
				title : '请等待',
				msg : '任务执行中...',
				progressText : '初始化...',
				width : 300,
				progress : true,
				closable : false,
				buttons : {
					"cancel" : "取消"
				},
				fn : function(e) {// 取消按钮点击调用方法，此fn非入参fn
					flag = 0;
					if (fn && typeof fn == "function") {
						fn();// 设置变量为false，因为是异步执行，所以后台线程一直在运行，而前台如果设置变量为false表示关闭
					}
					clearInterval(aa);// 前台显示线程停止
				}
			});

	var f = function() {
		return function() {
			flag = flag + 1;

			if (flag == task_s + 1 || overFlat) {// 执行完后会调用
				Ext.MessageBox.hide();
				flag = 0;
				if (fn && typeof fn == "function") {
					fn();
				}
				clearInterval(aa);// 关闭前台显示线程
			} else {
				var i = flag;
				Ext.MessageBox.updateProgress(i / (task_s), i + ' 秒');
			}
		};
	};
	var aa = setInterval(f(), 1000);// 1秒钟，调用f一次
};

var userStore;

Ext.onReady(function() {
	// 控制方式radiogroup
	var paraRadioGroup = new Ext.form.RadioGroup({
				fieldLabel : '控制方式',
				labelSeparator : '',
				id : 'ppdRadioGroup',
				columns : [60, 60],
				items : [{
							boxLabel : '电量控',
							name : 'd-rb-auto',
							inputValue : '0',
							checked : true
						}, {
							boxLabel : '电费控',
							name : 'd-rb-auto',
							inputValue : '1'
						}]
			});

	// 开始日期
	var startDate = new Ext.form.DateField({
				labelSeparator : ' ',
				name : 'startDate',
				format : "Y-m-d",
				width : 90,
				fieldLabel : '从',
				allowBlank : false,
				editable : false,
				value : new Date().add(Date.DAY, -7)
			});

	// 结束日期
	var endDate = new Ext.form.DateField({
				name : 'endDate',
				labelSeparator : ' ',
				format : "Y-m-d",
				width : 90,
				fieldLabel : '到',
				allowBlank : false,
				editable : false,
				value : new Date()
			});

	// 购电单号
	var buyOrder = new Ext.form.NumberField({
				fieldLabel : "购电单号",
				labelSeparator : ''
			});

	// 供电单位
	var orgNoTF = new Ext.form.TextField({
				fieldLabel : "供电单位<font color='red'>*</font>",
				name : 'orgNoTF',
				id : 'orgNoTF',
				labelSeparator : '',
				readOnly : true,
				editable : false,
				anchor : '100%'
			});

	// 用户编号
	var consNoTF = new Ext.form.TextField({
				fieldLabel : '用户编号',
				name : 'consNoTF',
				labelSeparator : ''
			});

	// 终端地址
	var tmnlAddrTF = new Ext.form.TextField({
				fieldLabel : '终端地址',
				name : 'tmnlAddrTF',
				labelSeparator : ''
			});

	// 调试状态数据
	var debugStatusStore = new Ext.data.ArrayStore({
				fields : ['statusValue', 'statusName'],
				data : [['0', '全部'], ["1", '调试成功'], ['2', '调试失败'],
						["3", '未调试'], ["4", '调试中']]
			});

	// 调试状态
	var debugStatusComboBox = new Ext.form.ComboBox({
				fieldLabel : '调试状态',
				store : debugStatusStore,
				bodyStyle : 'padding:10px;',
				triggerAction : 'all',
				mode : 'local',
				valueField : 'statusValue',
				displayField : 'statusName',
				width : 90,
				value : 0,
				selectOnFocus : true,
				allowBlank : false,
				editable : false,
				labelSeparator : ''
			});

	// 供电单位选择按钮
	var orgNoBtn = new Ext.Button({
				text : '请选择',
				width : 50,
				handler : function() {
					chooseOrgNo();
				}
			});

	// 查询按钮
	var queryBtn = new Ext.Button({
				text : '查询',
				width : 60,
				listeners : {
					"click" : function(btn) {
						if (startDate.getValue() - endDate.getValue() > 0) {
							Ext.MessageBox.alert("提示", "截止日期应大于起始日期！");
							return;
						}
						var orgNoValue = trim(orgNoTF.getValue());
						if (Ext.isEmpty(orgNoValue) || 0 == orgNoValue.length) {
							Ext.MessageBox.alert("提示", "请选择供电单位！");
							return;
						}
						Ext.getBody().mask("加载中...");
						userStore.baseParams = {
							control : paraRadioGroup.getValue().getRawValue(),
							appNo : buyOrder.getValue(),
							startDate : startDate.getValue(),
							endDate : endDate.getValue(),
							consNo : trim(consNoTF.getValue()),
							tmnlAddr : trim(tmnlAddrTF.getValue()),
							orgNo : orgNoValue,
							debugStatus : debugStatusComboBox.getValue()
						};
						userStore.load({
									callback : function(records, options,
											success) {
										if (success)
											userListGrid.getSelectionModel()
													.selectRecords(records,
															true);// 全选
										Ext.getBody().unmask();
										return;
									}
								});
					}
				}
			});

	// 打印按钮
	var debugBtn = new Ext.Button({
				text : '打印',
				width : 60,
				handler : function() {
					// TODO 预留给陈实现打印报表
				}
			});

	// 顶行查询panel
	var queryPrePaidDebugPanel = new Ext.Panel({
				region : 'north',
				border : true,
				height : 65,
				layout : 'form',
				id : 'queryPrePaidDebug',
				items : [{
							layout : 'column',
							style : "padding-top:5px",
							border : false,
							items : [{
										columnWidth : .25,
										border : false,
										anchor : '100%',
										labelAlign : 'right',
										labelWidth : 60,
										layout : 'form',
										items : [paraRadioGroup]
									}, {
										border : false,
										columnWidth : .21,
										layout : 'form',
										labelAlign : 'right',
										labelWidth : 55,
										layout : 'form',
										items : [debugStatusComboBox]
									}, {
										border : false,
										columnWidth : .17,
										layout : 'form',
										labelAlign : 'right',
										labelWidth : 25,
										layout : 'form',
										items : [startDate]
									}, {
										border : false,
										columnWidth : .17,
										layout : 'form',
										labelAlign : 'right',
										labelWidth : 25,
										layout : 'form',
										items : [endDate]
									}, {
										columnWidth : .1,
										border : false,
										layout : 'form',
										items : [queryBtn]
									}, {
										columnWidth : .1,
										border : false,
										layout : 'form',
										items : [debugBtn]
									}]
						}, {
							layout : 'column',
							style : "padding-top:2px",
							border : false,
							items : [{
										columnWidth : .25,
										border : false,
										anchor : '95%',
										labelAlign : 'right',
										labelWidth : 55,
										layout : 'form',
										items : [tmnlAddrTF]
									}, {
										columnWidth : .25,
										border : false,
										anchor : '95%',
										labelAlign : 'right',
										labelWidth : 55,
										layout : 'form',
										items : [buyOrder]
									}, {
										columnWidth : .17,
										border : false,
										layout : 'form',
										labelWidth : 55,
										labelAlign : 'right',
										items : [orgNoTF]
									}, {
										columnWidth : .08,
										border : false,
										layout : 'form',
										labelWidth : 1,
										labelAlign : 'right',
										bodyStyle : 'padding:0px 0px 0px 5px',
										items : [orgNoBtn]
									}, {
										columnWidth : .25,
										border : false,
										layout : 'form',
										labelAlign : 'right',
										labelWidth : 55,
										items : [consNoTF]
									}]
						}]
			});

	// ----下方grid 开始
	Ext.override(Ext.grid.RowNumberer, {
				renderer : function(v, p, record, rowIndex) {
					if (this.rowspan) {
						p.cellAttr = 'rowspan="' + this.rowspan + '"';
					}
					return rowIndex + 1;
				}
			});

	var rowNum = new Ext.grid.RowNumberer({
				renderer : function(v, p, record, rowIndex) {
					var startRow = 0;
					if (userStore && userStore.lastOptions
							&& userStore.lastOptions.params) {
						startRow = userStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			});
	// 定义check框
	var userSm = new Ext.grid.CheckboxSelectionModel();

	// 定义列模式
	var userCm = new Ext.grid.ColumnModel([rowNum, userSm, {
				header : "供电单位",
				align : 'center',
				dataIndex : 'orgName',
				sortable : true
			}, {
				header : "购电单号",
				align : 'center',
				dataIndex : 'buyOrder',
				sortable : true,
				renderer : function(s, m, rec) {
					return "<a href='javascript:' onclick='openPrepaidParamInfo(\""
							+ rec.get('feeCtrlId')
							+ "\",\""
							+ rec.get('buyOrder')
							+ "\",\""
							+ rec.get('tmnlAssetNo')
							+ "\");'>"
							+ rec.get('buyOrder') + "</a>";
				}
			}, {
				header : "用户编号",
				align : 'center',
				dataIndex : 'consNo',
				sortable : true
			}, {
				header : "用户名称",
				align : 'center',
				dataIndex : 'consName',
				sortable : true,
				renderer : function(v) {
					var html = '<span ext:qtitle="用户名称:" ext:qtip="' + v + '">'
							+ v + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : "终端地址",
				align : 'center',
				dataIndex : 'tmnlAddr',
				sortable : true,
				renderer : function(s, m, rec) {
					return "<a href='javascript:' onclick='openPrepaidTmnlInfo(\""
							+ rec.get('terminalId')
							+ "\",\""
							+ rec.get('meterId')
							+ "\",\""
							+ rec.get('buyOrder')
							+ "\");'>"
							+ rec.get('tmnlAddr') + "</a>";
				}
			}, {
				header : '调试状态',
				align : 'center',
				dataIndex : 'debugStatus',
				sortable : true,
				renderer : function(s, m, rec) {
					return "<a href='javascript:' onclick='openPrepaidDubugInfo(\""
							+ rec.get('terminalId')
							+ "\",\""
							+ rec.get('meterId')
							+ "\",\""
							+ rec.get('buyOrder')
							+ "\");'>"
							+ rec.get('debugStatus') + "</a>";
				}
			}, {
				header : "操作链接",
				align : 'center',
				dataIndex : 'operLink',
				width : 500,
				sortable : true,
				renderer : function(s, m, rec) {
					var href = '';
					href = href + "<a href='javascript:' onclick='"
							+ "callData(\"" + rec.get('tmnlAssetNo') + "\",\""
							+ rec.get('totalNo') + "\",\""
							+ rec.get('terminalId') + "\",\""
							+ rec.get('meterId') + "\",\""
							+ rec.get('buyOrder') + "\");"
							+ "'><font color='green';font-weight:bold>["
							+ "数据召测" + "]</font></a>" + "&nbsp;&nbsp;";
					href = href + "<a href='javascript:' onclick='"
							+ "turnCtrl(\"" + rec.get('orgNo') + "\",\""
							+ rec.get('consNo') + "\",\""
							+ rec.get('tmnlAssetNo') + "\",\""
							+ rec.get('protocolCode') + "\",\""
							+ rec.get('turnFlag') + "\",\""
							+ rec.get('terminalId') + "\",\""
							+ rec.get('meterId') + "\",\""
							+ rec.get('buyOrder') + "\");"
							+ "'><font color='green';font-weight:bold>["
							+ "跳合闸" + "]</font></a>" + "&nbsp;&nbsp;";
					href = href + "<a href='javascript:' onclick='"
							+ "turnCtrl(\"" + rec.get('orgNo') + "\",\""
							+ rec.get('consNo') + "\",\""
							+ rec.get('tmnlAssetNo') + "\",\""
							+ rec.get('protocolCode') + "\",\""
							+ rec.get('turnFlag') + "\",\""
							+ rec.get('terminalId') + "\",\""
							+ rec.get('meterId') + "\",\""
							+ rec.get('buyOrder') + "\");"
							+ "'><font color='green';font-weight:bold>["
							+ "召测开关状态" + "]</font></a>" + "&nbsp;&nbsp;";
					href = href + "<a href='javascript:' onclick='"
							+ "sendPPDParam(\"" + rec.get('orgNo') + "\",\""
							+ rec.get('consNo') + "\",\""
							+ rec.get('tmnlAssetNo') + "\",\""
							+ rec.get('totalNo') + "\",\""
							+ rec.get('protocolCode') + "\",\""
							+ rec.get('refreshFlag') + "\",\""
							+ rec.get('buyValue') + "\",\""
							+ rec.get('buyValueUnit') + "\",\""
							+ rec.get('alarmValue') + "\",\""
							+ rec.get('alarmValueUnit') + "\",\""
							+ rec.get('jumpValue') + "\",\""
							+ rec.get('jumpValueUnit') + "\",\""
							+ rec.get('terminalId') + "\",\""
							+ rec.get('meterId') + "\",\""
							+ rec.get('buyOrder') + "\",\""
							+ rec.get('feeCtrlId') + "\",\""
							+ rec.get('turnFlag') + "\");"
							+ "'><font color='green';font-weight:bold>["
							+ "下发预购电参数" + "]</font></a>" + "&nbsp;&nbsp;";
					href = href + "<a href='javascript:' onclick='"
							+ "sendPPDCmd(\"" + rec.get('orgNo') + "\",\""
							+ rec.get('consNo') + "\",\""
							+ rec.get('tmnlAssetNo') + "\",\""
							+ rec.get('totalNo') + "\",\""
							+ rec.get('protocolCode') + "\",\""
							+ rec.get('terminalId') + "\",\""
							+ rec.get('meterId') + "\",\""
							+ rec.get('buyOrder') + "\",\""
							+ rec.get('dubugStatus') + "\");"
							+ "'><font color='green';font-weight:bold>["
							+ "预购电执行" + "]</font></a>" + "&nbsp;&nbsp;";
					href = href + "<a href='javascript:' onclick='"
							+ "totalConfig(\""+ rec.get('tmnlAssetNo')+ "\",\""
							+ rec.get('consNo') + "\",\""
							+ rec.get('orgNo') + "\",\""
							+ rec.get('pOrgNo') + "\",\""
							+ rec.get('tmnlAddr') + "\",\""
							+ rec.get('meterId') + "\",\""
							+ rec.get('cisTmnlAssetNo') + "\",\""
							+ rec.get('cpNo') + "\",\""
							+ rec.get('protocolCode') + "\");"
							+ "'><font color='green';font-weight:bold>["
							+ "总加组设置" + "]</font></a>" + "&nbsp;&nbsp;";
					href = href + "<a href='javascript:' onclick='"
							+ "frameLink(\"" + rec.get('consNo') + "\",\""
							+ rec.get('consName') + "\");"
							+ "'><font color='green';font-weight:bold>[" + "报文"
							+ "]</font></a>" + "&nbsp;&nbsp;";
					return href;
				}
			}]);

	// 后台store
	userStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'baseapp/prePaidDebug!queryUserInfo.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'userInfoList',
							totalProperty : 'totalCount',
							idProperty : 'feeCtrlId'
						}, [{
									name : 'orgNo'
								}, {
									name : 'orgName'
								}, {
									name : 'buyOrder'
								}, {
									name : 'consNo'
								}, {
									name : 'consName'
								}, {
									name : 'tmnlAddr'
								}, {
									name : 'tmnlAssetNo'
								}, {
									name : 'totalNo'
								}, {
									name : 'debugStatus'
								}, {
									name : 'protocolCode'
								}, {
									name : 'operLink'
								}, {
									name : 'buyValue'
								}, {
									name : 'alarmValue'
								}, {
									name : 'jumpValue'
								}, {
									name : 'feeCtrlId'
								}, {
									name : 'terminalId'
								}, {
									name : 'meterId'
								}, {
									name : 'cisTmnlAssetNo'
								}, {
									name : 'turnFlag'
								}, {
									name : 'buyValueUnit'
								}, {
									name : 'alarmValueUnit'
								}, {
									name : 'jumpValueUnit'
								}, {
									name : 'refreshFlag'
								}, {
									name : 'cpNo'
								}, {
									name : 'pOrgNo'
								}])
			});

	// grid解锁
	function unlockGrid() {
		userSm.unlock();
		userListGrid.onEnable();
		userListGrid.getBottomToolbar().enable();
	}

	// grid上锁
	function lockGrid() {
		userSm.lock();
		userListGrid.onDisable();
		userListGrid.getBottomToolbar().disable();
	}

	// 定义全选checkBox
	var ppdselectAllcb = new Ext.form.Checkbox({
				boxLabel : '全选',
				name : 'ppdselectAllcb',
				checked : false,
				listeners : {
					'check' : function(r, c) {
						if (c) {
							userSm.selectAll();
							lockGrid();
						} else {
							unlockGrid();
							userSm.clearSelections();
						}
					}
				}
			});

	// 短信通知按钮
	var smsBtn = new Ext.Button({
				text : '发送短信通知',
				name : 'smsBtn',
				width : 80,
				handler : function() {
				}
			});

	var userListGrid = new Ext.grid.GridPanel({
				store : userStore,
				sm : userSm,
				cm : userCm,
				stripeRows : true,
				autoScroll : true,
				monitorResize : true,
				viewConfig : {
					forceFit : false
				},
				loadMask : false,// true,加载数据时--"读取中"
				region : 'center',
				tbar : [smsBtn, {
							xtype : 'tbfill'
						}, ppdselectAllcb, '-', {
							icon : '',
							text : '通知营销',
							iconCls : 'net-terminal',
							handler : function() {
								var groupTmnlArray = new Array();
								var checkAll = false;
								if (ppdselectAllcb.checked) {// 如果全选，判断是否后台store选中数据
									checkAll = true;
								} else {
									var recs = userSm.getSelections();
									if (null == recs || recs.length == 0) {// 如果选中当前页，判断是否当前页面选中数据
										Ext.MessageBox.alert("提示", "请选择用户！");
										return;
									}
									for (var i = 0; i < recs.length; i++) {
										var tmnl = recs[i].get('consNo') + '`'
												+ recs[i].get('tmnlAssetNo')
												+ '`' + recs[i].get('appNo')
												+ '`'
												+ recs[i].get('terminalId')
												+ '`' + recs[i].get('meterId');
										groupTmnlArray[i] = tmnl;
									}
								}
								if (groupTmnlArray.length == 0) {
									Ext.Msg.alert('提示', '请选择用户');
									return;
								}

								Ext.Ajax.request({// 异步执行 TODO 全选需要修改
									url : './baseapp/prePaidDebug!send2Sale.action',
									params : {
										checkAll : checkAll,
										terminalList : groupTmnlArray,
										control : paraRadioGroup.getValue()
												.getRawValue(),
										appNo : buyOrder.getValue(),
										startDate : startDate.getValue(),
										endDate : endDate.getValue(),
										consNo : trim(consNoTF.getValue()),
										tmnlAddr : trim(tmnlAddrTF.getValue()),
										orgNo : orgNoValue,
										debugStatus : debugStatusComboBox
												.getValue()
									},
									success : function(response) {
										var result = Ext
												.decode(response.responseText);
										if (!Ext.isEmpty(status)
												&& null != status
												&& 'null' != status) {
											if ('3' == status) {
												Ext.MessageBox.alert("提示",
														'通知营销成功');
												return;
											} else {
												Ext.MessageBox.alert("提示",
														'通知营销失败');
												return;
											}
										} else {
											Ext.MessageBox
													.alert("提示", '通知营销失败');
											return;
										}
									},
									failure : function(response) {
										Ext.MessageBox.alert('提示', '通知营销失败！');
										return;
									}
								});
							}
						}],
				bbar : new Ext.ux.MyToolbar({
							store : userStore
						})
			});

	var ppdFormPanel = new Ext.form.FormPanel({
				border : false,
				id : 'ppdFormPanel',
				layout : 'border',
				items : [queryPrePaidDebugPanel, userListGrid]
			});

	renderModel(ppdFormPanel, '预付费投入调试');
});

// ---------------------------------选中供电单位-----------------------start
function chooseOrgNo() {
	var orgRootNode = new Ext.tree.AsyncTreeNode({
				id : 'role_root',
				text : '请选择'
			});

	var orgTreeLoader = new Ext.tree.TreeLoader({
				autoScroll : true,
				dataUrl : 'baseapp/prePaidDebug!queryOrgTree.action'
			});

	var orgTree = new Ext.tree.TreePanel({
				width : 250,
				border : false,
				height : 250,
				autoScroll : true,
				title : "",
				root : orgRootNode,
				loader : orgTreeLoader
			});

	var staffOrgWindow = new Ext.Window({
				frame : true,
				width : 250,
				height : 300,
				layout : "form",
				modal : true,
				closeAction : "close",
				plain : true,// 设置背景颜色
				resizable : false,
				draggable : false,// 不可移动
				buttonAlign : "center",// 按钮的位置
				title : '【请选择供电单位】',
				items : [orgTree],
				buttons : [{
					text : "确定",
					handler : function() {
						selNodes = orgTree.getChecked();
						if (Ext.isEmpty(selNodes) || 0 == selNodes.length) {
							Ext.MessageBox.alert("提示", "必须选择一个供电单位！");
							return;
						}
						var orgNos = '';
						Ext.each(selNodes, function(node) {
									orgNos = orgNos + node.id + ',';
								});
						Ext.getCmp('orgNoTF').setValue(orgNos.substring(0,
								orgNos.length - 1));
						staffOrgWindow.close();
					}
				}, {
					text : "退出",
					handler : function() {
						staffOrgWindow.close();
					}
				}]
			});

	staffOrgWindow.show();
}
// ---------------------------------选中供电单位-----------------------end

// ---------------------------------显示预付费参数信息-----------------------start
function openPrepaidParamInfo(feeCtrlId, buyOrder, tmnlAssetNo) {
	var oldJumpValue;
	var oldAlarmValue;
	var oldBuyValue;
	var oldTurnFlag;
	var oldTotalNo;
	Ext.Ajax.request({// 异步执行
		url : './baseapp/prePaidDebug!queryOrderParamInfo.action',
		params : {
			feeCtrlId : feeCtrlId,
			appNo : buyOrder
		},
		success : function(response) {
			var result = Ext.decode(response.responseText);
			var ppdParamInfoBean = result.ppdParamInfoBean;
			if (Ext.isEmpty(ppdParamInfoBean) || null == ppdParamInfoBean) {
				return;
			}

			ppdParamBuyOrder.setValue(ppdParamInfoBean.buyOrder);
			ppdParamConsNo.setValue(ppdParamInfoBean.consNo);
			ppdParamConsName.setValue(ppdParamInfoBean.consName);
			ppdParamTmnlAssetNo.setValue(ppdParamInfoBean.cisTmnlAssetNo);
			ppdParamAssetNo.setValue(ppdParamInfoBean.meterId);

			oldJumpValue = ppdParamInfoBean.jumpValue;
			oldAlarmValue = ppdParamInfoBean.alarmValue;
			oldBuyValue = ppdParamInfoBean.buyValue;
			oldTurnFlag = ppdParamInfoBean.turnFlag;
			oldTotalNo = ppdParamInfoBean.totalNo;

			ppdParamJumpValue.setValue(oldJumpValue);
			ppdParamBuyValue.setValue(oldBuyValue);
			ppdParamAlarmValue.setValue(oldAlarmValue);

			var ppd_TurnFlag = new Array(false, false, false, false, false,
					false, false, false);
			if (!Ext.isEmpty(oldTurnFlag) && "null" != oldTurnFlag
					&& 0 < oldTurnFlag.length) {
				var tf = oldTurnFlag.split(",");
				for (var i = 1; i <= 8; i = i + 1) {
					for (var j = 0; j < tf.length; j = j + 1) {
						if (tf[j] == i) {
							ppd_TurnFlag[i - 1] = true;
							break;
						}
					}
				}
			}
			ppdParamTurnFlag.setValue(ppd_TurnFlag);

			ppdParamTotalStore.removeAll();
			ppdParamTotalComboBox.clearValue();
			ppdParamTotalStore.baseParams = {
				tmnlAssetNo : tmnlAssetNo
			};
			ppdParamTotalStore.load({
						callback : function(records, options, success) {
							ppdParamTotalComboBox.setValue(oldTotalNo);
						}
					});
		}
	});

	// 工单编号
	var ppdParamBuyOrder = new Ext.form.TextField({
				fieldLabel : "购电单号",
				name : 'ppdParamBuyOrder',
				labelSeparator : '',
				readOnly : true,
				editable : false,
				width : 120
			});

	// 用户编号
	var ppdParamConsNo = new Ext.form.TextField({
				fieldLabel : '用户编号',
				name : 'ppdParamConsNo',
				readOnly : true,
				editable : false,
				labelSeparator : '',
				width : 120
			});

	// 用户名称
	var ppdParamConsName = new Ext.form.TextField({
				fieldLabel : '用户名称',
				name : 'ppdParamConsName',
				readOnly : true,
				editable : false,
				labelSeparator : '',
				width : 300
			});

	// 终端资产号
	var ppdParamTmnlAssetNo = new Ext.form.TextField({
				fieldLabel : '终端资产号',
				name : 'ppdParamTmnlAssetNo',
				readOnly : true,
				editable : false,
				labelSeparator : '',
				width : 120
			});

	// 电能表号
	var ppdParamAssetNo = new Ext.form.TextField({
				fieldLabel : '电能表号',
				name : 'ppdParamAssetNo',
				readOnly : true,
				editable : false,
				labelSeparator : '',
				width : 120
			});

	// 跳闸电量
	var ppdParamJumpValue = new Ext.form.TextField({
				fieldLabel : '跳闸电量',
				name : 'ppdParamJumpValue',
				regex : /^\d+$/,
				maskRe : /^\d+$/,
				regexText : "请输入数字",
				labelSeparator : '',
				width : 120
			});

	// 预购电量
	var ppdParamBuyValue = new Ext.form.TextField({
				fieldLabel : '预购电量',
				name : 'ppdParamBuyValue',
				regex : /^\d+$/,
				maskRe : /^\d+$/,
				regexText : "请输入数字",
				labelSeparator : '',
				width : 120
			});

	// 告警电量
	var ppdParamAlarmValue = new Ext.form.TextField({
				fieldLabel : '告警电量',
				name : 'ppdParamAlarmValue',
				regex : /^\d+$/,
				maskRe : /^\d+$/,
				regexText : "请输入数字",
				labelSeparator : '',
				width : 120
			});

	// 控制轮次
	var ppdParamTurnFlag = new Ext.form.CheckboxGroup({
				width : 450,
				fieldLabel : '控制轮次',
				labelSeparator : '',
				items : [{
							boxLabel : '轮次1',
							inputValue : '1',
							name : '1'
						}, {
							boxLabel : '轮次2',
							inputValue : '2',
							name : '2'
						}, {
							boxLabel : '轮次3',
							inputValue : '3',
							name : '3'
						}, {
							boxLabel : '轮次4',
							inputValue : '4',
							name : '4'
						}, {
							boxLabel : '轮次5',
							inputValue : '5',
							name : '5'
						}, {
							boxLabel : '轮次6',
							inputValue : '6',
							name : '6'
						}, {
							boxLabel : '轮次7',
							inputValue : '7',
							name : '7'
						}, {
							boxLabel : '轮次8',
							inputValue : '8',
							name : '8'
						}]
			});

	// 总加组store
	var ppdParamTotalStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './baseapp/prePaidDebug!queryParamTotalInfo.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'ppdParamTotalList'
						}, [{
									name : 'totalNo'
								}])
			});

	var ppdParamTotalComboBox = new Ext.form.ComboBox({
				store : ppdParamTotalStore,
				name : 'ppdParamTotalComboBox',
				fieldLabel : '总加组号',
				valueField : 'totalNo',
				editable : false,
				triggerAction : 'all',
				forceSelection : true,
				mode : 'local',
				selectOnFocus : true,
				emptyText : '请选择',
				displayField : 'totalNo',
				labelSeparator : '',
				width : 120
			});

	// 预付费参数详细信息面板
	var ppdParamPanel = new Ext.Panel({
				layout : "form",
				border : false,
				items : [{
							layout : 'column',
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							items : [{
										columnWidth : .5,
										labelAlign : "right",
										labelWidth : 70,
										labelSeparator : "",
										layout : "form",
										border : false,
										items : [ppdParamBuyOrder]
									}, {
										columnWidth : .5,
										labelAlign : "right",
										labelWidth : 70,
										labelSeparator : "",
										layout : "form",
										border : false,
										items : [ppdParamConsNo]
									}]
						}, {
							labelAlign : "right",
							labelWidth : 70,
							labelSeparator : "",
							layout : "form",
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							items : [ppdParamConsName]
						}, {
							layout : 'column',
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							items : [{
										columnWidth : .5,
										labelAlign : "right",
										labelWidth : 70,
										labelSeparator : "",
										layout : "form",
										border : false,
										items : [ppdParamTmnlAssetNo]
									}, {
										columnWidth : .5,
										labelAlign : "right",
										labelWidth : 70,
										labelSeparator : "",
										layout : "form",
										border : false,
										items : [ppdParamAssetNo]
									}]
						}, {
							layout : 'column',
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							items : [{
										columnWidth : .5,
										labelAlign : "right",
										labelWidth : 70,
										labelSeparator : "",
										layout : "form",
										border : false,
										items : [ppdParamAlarmValue]
									}, {
										columnWidth : .5,
										labelAlign : "right",
										labelWidth : 70,
										labelSeparator : "",
										layout : "form",
										border : false,
										items : [ppdParamJumpValue]
									}]
						}, {
							layout : 'column',
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							items : [{
										columnWidth : .5,
										labelAlign : "right",
										labelWidth : 70,
										labelSeparator : "",
										layout : "form",
										border : false,
										items : [ppdParamBuyValue]
									}, {
										columnWidth : .5,
										labelAlign : "right",
										labelWidth : 70,
										labelSeparator : "",
										layout : "form",
										border : false,
										items : [ppdParamTotalComboBox]
									}]
						}, {
							labelAlign : "right",
							labelWidth : 70,
							labelSeparator : "",
							layout : "form",
							border : false,
							items : [ppdParamTurnFlag]
						}]
			});

	var ppdParamWindow = new Ext.Window({
		html : '<iframe />',
		width : 550,
		height : 250,
		layout : "form",
		modal : true,
		closeAction : "close",
		plain : false,// 设置背景颜色
		resizable : false,
		draggable : false,// 不可移动
		buttonAlign : "center",// 按钮的位置
		title : '【预付费参数信息】',
		items : [ppdParamPanel],
		buttons : [{
			text : "确定",
			handler : function() {
				var roundArray = ppdParamTurnFlag.getValue();
				var round = "";
				if (0 == roundArray.length) {
					Ext.MessageBox.alert("提示", "轮次不能为空，请选择！");
					return;
				}
				if (0 < roundArray.length) {
					round = roundArray[0].getRawValue();
					for (var i = 1; i < roundArray.length; i++) {
						round = round + ',' + roundArray[i].getRawValue();
					}
				}
				var newBuyValue = ppdParamBuyValue.getValue();
				var newAlarmValue = ppdParamAlarmValue.getValue();
				var newJumpValue = ppdParamJumpValue.getValue();
				var newTotalNo = ppdParamTotalComboBox.getValue();
				// 因为都是数字，所以如果为'',则必须设置为null，和数据库保持一致
				if ('' == newBuyValue) {
					newBuyValue = null;
				}
				if ('' == newAlarmValue) {
					newAlarmValue = null;
				}
				if ('' == newJumpValue) {
					newJumpValue = null;
				}
				if (Ext.isEmpty(newTotalNo)) {
					newTotalNo = null;
				}
				if (oldBuyValue != newBuyValue
						|| oldAlarmValue != newAlarmValue
						|| oldJumpValue != newJumpValue || oldTurnFlag != round
						|| oldTotalNo != newTotalNo) {
					Ext.MessageBox.show({
						title : '提示',
						msg : '数据已经修改，是否保存数据？',
						width : 300,
						buttons : Ext.MessageBox.OKCANCEL,
						fn : function(e) {
							if ("ok" == e) {
								var rec = userStore.getById(feeCtrlId);
								if (null != rec) {
									rec.set('totalNo', newTotalNo);
									userStore.commitChanges();// 去掉红色标记
								}
								Ext.Ajax.request({
									url : './baseapp/prePaidDebug!updatePPDPara.action',
									params : {
										feeCtrlId : feeCtrlId,
										appNo : buyOrder,
										buyValue : ppdParamBuyValue.getValue(),
										jumpValue : ppdParamJumpValue
												.getValue(),
										alarmValue : ppdParamAlarmValue
												.getValue(),
										turnFlag : round,
										totalNo : newTotalNo
									},
									success : function(response) {
										ppdParamWindow.close();
									},
									failure : function(response) {
										Ext.MessageBox.alert('提示', '保存数据失败！');
										return;
									}
								});
							}
						}
					});
				} else {
					ppdParamWindow.close();
				}
			}
		}, {
			text : "退出",
			handler : function() {
				ppdParamWindow.close();
			}
		}, {	// 打印按钮功能实现
					text : "打印",
					handler : function() {
						var c = ppdParamWindow.body.first().dom.innerHTML;
						var printer = ppdParamWindow.body.last().dom.contentWindow;
						printer.document.body.innerHTML = c;
						printer.focus();
						printer.print();
					}
				}]
	});

	ppdParamWindow.show();
}
// ---------------------------------显示预付费参数信息-----------------------end

// ---------------------------------显示预付费终端工况信息-----------------------start
function openPrepaidTmnlInfo(terminalId, meterId, buyOrder) {
	Ext.Ajax.request({// 异步执行
		url : './baseapp/prePaidDebug!queryTmnlInfo.action',
		params : {
			terminalId : terminalId,
			meterId : meterId,
			appNo : buyOrder
		},
		success : function(response) {
			var result = Ext.decode(response.responseText);
			var ppdTmnlInfoBean = result.ppdTmnlInfoBean;
			if (Ext.isEmpty(ppdTmnlInfoBean) || null == ppdTmnlInfoBean) {
				return;
			}
			ppdTmnlCpNo.setValue(ppdTmnlInfoBean.cpNo);
			ppdTmnlCpAddr.setValue(ppdTmnlInfoBean.cpAddr);
			ppdTmnlAssetNo.setValue(ppdTmnlInfoBean.tmnlAssetNo);
			ppdTmnlIsOnline.setValue(ppdTmnlInfoBean.isOnline);
			ppdCommStatus.setValue(ppdTmnlInfoBean.commStauts);
			ppdTmnlPaulPower.setValue(ppdTmnlInfoBean.powerPaulStatus);
		}
	});

	// 采集点编号
	var ppdTmnlCpNo = new Ext.form.TextField({
				fieldLabel : "采集点编号",
				name : 'ppdTmnlCpNo',
				labelSeparator : '',
				readOnly : true,
				editable : false,
				width : 150
			});

	// 采集点地址
	var ppdTmnlCpAddr = new Ext.form.TextField({
				fieldLabel : '采集点地址',
				name : 'ppdTmnlCpAddr',
				readOnly : true,
				editable : false,
				labelSeparator : '',
				width : 150
			});

	// 终端资产编码
	var ppdTmnlAssetNo = new Ext.form.TextField({
				fieldLabel : '终端资产编码',
				name : 'ppdTmnlAssetNo',
				readOnly : true,
				editable : false,
				labelSeparator : '',
				width : 150
			});

	// 终端在线radiogroup
	var ppdTmnlIsOnline = new Ext.form.RadioGroup({
				fieldLabel : '终端是否在线',
				labelSeparator : '',
				width : 200,
				items : [{
							boxLabel : '否',
							name : 'd-rb-auto',
							inputValue : '0',
							checked : true
						}, {
							boxLabel : '是',
							name : 'd-rb-auto',
							inputValue : '1'
						}, {
							boxLabel : '状态未知',
							name : 'd-rb-auto',
							inputValue : '2'
						}]
			});

	// 与电能表通讯
	var ppdCommStatus = new Ext.form.TextField({
				fieldLabel : '与电能表通讯状态',
				name : 'ppdCommStatus',
				readOnly : true,
				editable : false,
				labelSeparator : '',
				width : 150
			});

	// 保电投入
	var ppdTmnlPaulPower = new Ext.form.TextField({
				fieldLabel : '保电投入',
				name : 'ppdTmnlPaulPower',
				readOnly : true,
				editable : false,
				labelSeparator : '',
				width : 150
			});

	// 预付费参数详细信息面板
	var ppdTmnlPanel = new Ext.Panel({
				layout : "form",
				border : false,
				items : [{
							labelAlign : "right",
							labelWidth : 100,
							labelSeparator : "",
							layout : "form",
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							items : [ppdTmnlCpNo]
						}, {
							labelAlign : "right",
							labelWidth : 100,
							labelSeparator : "",
							layout : "form",
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							items : [ppdTmnlCpAddr]
						}, {
							labelAlign : "right",
							labelWidth : 100,
							labelSeparator : "",
							layout : "form",
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							items : [ppdTmnlAssetNo]
						}, {
							labelAlign : "right",
							labelWidth : 100,
							labelSeparator : "",
							layout : "form",
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							items : [ppdTmnlIsOnline]
						}, {
							labelAlign : "right",
							labelWidth : 100,
							labelSeparator : "",
							layout : "form",
							bodyStyle : 'padding:5px 0px 0px 0px',
							border : false,
							items : [ppdCommStatus]
						}, {
							labelAlign : "right",
							labelWidth : 100,
							labelSeparator : "",
							layout : "form",
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							items : [ppdTmnlPaulPower]
						}]
			});

	var ppdTmnlWindow = new Ext.Window({
				width : 350,
				height : 255,
				layout : "form",
				modal : true,
				closeAction : "close",
				plain : false,// 设置背景颜色
				resizable : false,
				draggable : false,// 不可移动
				buttonAlign : "center",// 按钮的位置
				title : '【终端工况信息】',
				items : [ppdTmnlPanel],
				buttons : [{
							text : "退出",
							handler : function() {
								ppdTmnlWindow.close();
							}
						}]
			});

	ppdTmnlWindow.show();
}
// ---------------------------------显示预付费终端工况信息-----------------------end

// ---------------------------------显示调试状态信息-----------------------start
function openPrepaidDubugInfo(terminalId, meterId, buyOrder) {
	var ppdDebugInfoCm = new Ext.grid.ColumnModel([{
				header : "终端地址",
				dataIndex : 'tmnlAddr',
				align : "center"
			}, {
				header : "电能表资产号",
				dataIndex : 'assetNo',
				width : 150,
				align : 'center',
				renderer : function(v) {
					var html = '<span ext:qtitle="电能表资产号:" ext:qtip="' + v
							+ '">' + v + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : "终端是否在线",
				dataIndex : 'isOnline',
				align : 'center'
			}, {
				header : "流程节点",
				width : 150,
				dataIndex : 'flowNodeName',
				align : 'center'
			}, {
				header : "调试状态",
				dataIndex : 'flowNodeStatus',
				align : 'center'
			}, {
				header : "失败原因",
				dataIndex : 'errCause',
				align : 'center'
			}, {
				header : "操作时间",
				width : 120,
				dataIndex : 'opTime',
				align : 'center'
			}]);

	var ppdDebugInfoStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './baseapp/prePaidDebug!queryUserDebugInfo.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'ppdUserStatusInfoList'
						}, [{
									name : 'terminalId'
								}, {
									name : 'tmnlAssetNo'
								}, {
									name : 'tmnlAddr'
								}, {
									name : 'meterId'
								}, {
									name : 'assetNo'
								}, {
									name : 'isOnline'
								}, {
									name : 'flowNoCode'
								}, {
									name : 'flowNodeName'
								}, {
									name : 'flowNodeStatus'
								}, {
									name : 'errCause'
								}, {
									name : 'opTime'
								}])
			});

	var ppdDebugInfoGrid = new Ext.grid.GridPanel({
				autoScroll : true,
				stripeRows : true,
				viewConfig : {
					forceFit : false
				},
				anchor : '100%',
				colModel : ppdDebugInfoCm,
				ds : ppdDebugInfoStore
			});

	var ppdDebugInfoWindow = new Ext.Window({
				frame : true,
				width : 700,
				height : 400,
				layout : "fit",
				modal : true,
				closeAction : "close",
				plain : true,// 设置背景颜色
				resizable : false,
				draggable : false,// 不可移动
				buttonAlign : "center",// 按钮的位置
				title : '【调试流程信息】',
				items : [ppdDebugInfoGrid]
			});

	ppdDebugInfoStore.baseParams = {
		terminalId : terminalId,
		meterId : meterId,
		appNo : buyOrder
	};
	ppdDebugInfoStore.load();
	ppdDebugInfoWindow.show();
}
// ---------------------------------显示调试状态信息-----------------------end

// ---------------------------------数据召测链接----------------------start
function callData(tmnlAssetNo, totalNo, terminalId, meterId, appNo) {
	// 实时数据
	var ppdCallRealFlag = new Ext.form.CheckboxGroup({
				width : 100,
				fieldLabel : '实时数据',
				labelSeparator : '',
				items : [{
							boxLabel : 'F25',
							inputValue : '1',
							name : '1'
						}, {
							boxLabel : 'F33',
							inputValue : '2',
							name : '2'
						}]
			});

	// 冻结数据
	var ppdCallStatFlag = new Ext.form.CheckboxGroup({
				width : 100,
				fieldLabel : '冻结数据',
				labelSeparator : '',
				items : [{
							boxLabel : 'F1',
							inputValue : '1',
							name : '1'
						}, {
							boxLabel : 'F3',
							inputValue : '2',
							name : '2'
						}]
			});

	// 召测按钮
	var callBtn = new Ext.Button({
				text : '召测',
				width : 60,
				handler : function() {
					var realArray = ppdCallRealFlag.getValue();
					var statArray = ppdCallStatFlag.getValue();
					var realFns = "";
					var statFns = "";
					if (0 == realArray.length && 0 == statArray.length) {
						Ext.MessageBox.alert("提示", '请选中召测项');
						return;
					}
					if (0 < realArray.length) {
						realFns = realArray[0].getRawValue();
						for (var i = 1; i < realArray.length; i++) {
							realFns = realFns + ','
									+ realArray[i].getRawValue();
						}
					}
					if (0 < statArray.length) {
						statFns = statArray[0].getRawValue();
						for (var i = 1; i < statArray.length; i++) {
							statFns = statFns + ','
									+ statArray[i].getRawValue();
						}
					}
					alert(realFns);
					Ext.Ajax.request({// 异步执行
						url : './baseapp/prePaidDebug!fetchRead.action',
						params : {
							tmnlAssetNo : tmnlAssetNo,
							totalNo : totalNo,
							terminalId : terminalId,
							meterId : meterId,
							appNo : appNo,
							realFns : realFns,
							statFns : statFns
						},
						success : function(response) {
							var result = Ext.decode(response.responseText);
							if (null != result.cacheAndTmnlStatus
									&& "" != result.cacheAndTmnlStatus) {
								Ext.MessageBox.alert("提示",
										result.cacheAndTmnlStatus);
								return;
							}
							var result = Ext.decode(response.responseText);
							if (!Ext.isEmpty(status) && null != status
									&& 'null' != status) {
								if ('3' == status) {
									if (!Ext.isEmpty(result.readList)) {
										ppdCallStore.loadData(result);
									}
									Ext.MessageBox.alert("提示", '召测成功');
									return;
								} else {
									Ext.MessageBox.alert("提示", '召测失败');
									return;
								}
							} else {
								Ext.MessageBox.alert("提示", '召测失败');
								return;
							}
						}
					});
				}
			});

	// 退出按钮
	var exitBtn = new Ext.Button({
				text : '退出',
				width : 60,
				handler : function() {
					ppdCallWindow.close();
				}
			});

	// 数据项面板
	var ppdCallPanel = new Ext.Panel({
				layout : "column",
				region : 'north',
				height : 35,
				plain : true,
				items : [{
							columnWidth : .35,
							labelAlign : "right",
							labelWidth : 70,
							labelSeparator : "",
							bodyStyle : 'padding:5px 0px 0px 0px',
							layout : "form",
							border : false,
							items : [ppdCallRealFlag]
						}, {
							columnWidth : .35,
							labelAlign : "right",
							labelWidth : 70,
							labelSeparator : "",
							bodyStyle : 'padding:5px 0px 0px 0px',
							layout : "form",
							border : false,
							items : [ppdCallStatFlag]
						}, {
							columnWidth : .15,
							labelSeparator : "",
							bodyStyle : 'padding:5px 0px 0px 0px',
							layout : "form",
							border : false,
							items : [callBtn]
						}, {
							columnWidth : .15,
							labelSeparator : "",
							bodyStyle : 'padding:5px 0px 0px 0px',
							layout : "form",
							border : false,
							items : [exitBtn]
						}]
			});

	var ppdCallCm = new Ext.grid.ColumnModel([{
				header : "总加组",
				dataIndex : 'pn',
				align : "center"
			}, {
				header : "召测项",
				dataIndex : 'itemName',
				align : 'center'
			}, {
				header : "召测值",
				dataIndex : 'value',
				align : 'center'
			}, {
				header : "是否成功",
				dataIndex : 'errorCode',
				align : 'center',
				renderer : function(v) {
					if (v == "" || !v) {
						return "成功"
					}
					var html = (v + "").toLowerCase();
					if (html.indexOf("ok") >= 0) {
						return "成功";
					}
					return "失败,错误代码:" + v;
				}
			}, {
				header : "时间",
				dataIndex : 'time',
				align : 'center'
			}]);

	var ppdCallStore = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(),
				reader : new Ext.data.JsonReader({
							root : 'readList'
						}, [{
									name : 'pn'
								}, {
									name : 'item'
								}, {
									name : 'value'
								}, {
									name : 'tmnlAssetNo'
								}, {
									name : 'time'
								}, {
									name : 'itemName'
								}, {
									name : 'errorCode'
								}])
			});

	var ppdCallGrid = new Ext.grid.GridPanel({
				autoScroll : true,
				region : 'center',
				stripeRows : true,
				viewConfig : {
					forceFit : false
				},
				anchor : '100%',
				colModel : ppdCallCm,
				ds : ppdCallStore
			});

	var ppdCallWindow = new Ext.Window({
				width : 550,
				height : 345,
				layout : "border",
				modal : true,
				closeAction : "close",
				plain : false,// 设置背景颜色
				resizable : false,
				draggable : false,// 不可移动
				buttonAlign : "center",// 按钮的位置
				title : '【数据召测】',
				items : [ppdCallPanel, ppdCallGrid]
			});
	ppdCallWindow.show();
}
// ---------------------------------数据召测链接-----------------------end

// ---------------------------------合闸跳闸以及召测开关状态链接----------------------start
function turnCtrl(orgNo, consNo, tmnlAssetNo, protocolCode, turnFlag,
		terminalId, meterId, appNo) {
	// 控制轮次
	var ppdTurnCtrlFlag = new Ext.form.CheckboxGroup({
				width : 450,
				fieldLabel : '控制轮次',
				labelSeparator : '',
				items : [{
							boxLabel : '轮次1',
							inputValue : '1',
							name : '1'
						}, {
							boxLabel : '轮次2',
							inputValue : '2',
							name : '2'
						}, {
							boxLabel : '轮次3',
							inputValue : '3',
							name : '3'
						}, {
							boxLabel : '轮次4',
							inputValue : '4',
							name : '4'
						}, {
							boxLabel : '轮次5',
							inputValue : '5',
							name : '5'
						}, {
							boxLabel : '轮次6',
							inputValue : '6',
							name : '6'
						}, {
							boxLabel : '轮次7',
							inputValue : '7',
							name : '7'
						}, {
							boxLabel : '轮次8',
							inputValue : '8',
							name : '8'
						}]
			});
	var ppdTurnFlag = new Array(false, false, false, false, false, false,
			false, false);
	if (!Ext.isEmpty(turnFlag) && "null" != turnFlag && 0 < turnFlag.length) {
		var tf = turnFlag.split(",");
		for (var i = 1; i <= 8; i = i + 1) {
			for (var j = 0; j < tf.length; j = j + 1) {
				if (tf[j] == i) {
					ppdTurnFlag[i - 1] = true;
					break;
				}
			}
		}
	}
	ppdTurnCtrlFlag.setValue(ppdTurnFlag);

	// 轮次信息面板
	var ppdTurnCtrlPanel = new Ext.Panel({
				layout : "form",
				height : 30,
				plain : true,
				items : [{
							labelAlign : "right",
							labelWidth : 70,
							labelSeparator : "",
							bodyStyle : 'padding:5px 0px 0px 0px',
							layout : "form",
							border : false,
							items : [ppdTurnCtrlFlag]
						}]
			});

	var ppdTurnCtrlCm = new Ext.grid.ColumnModel([{
				header : "轮次一",
				dataIndex : 'switch1',
				width : 80,
				align : "center"
			}, {
				header : "轮次二",
				dataIndex : 'switch2',
				width : 80,
				align : 'center'
			}, {
				header : "轮次三",
				dataIndex : 'switch3',
				width : 80,
				align : 'center'
			}, {
				header : "轮次四",
				dataIndex : 'switch4',
				width : 80,
				align : 'center'
			}, {
				header : "轮次五",
				dataIndex : 'switch5',
				width : 80,
				align : 'center'
			}, {
				header : "轮次六",
				dataIndex : 'switch6',
				width : 80,
				align : 'center'
			}, {
				header : "轮次七",
				dataIndex : 'switch7',
				width : 80,
				align : 'center'
			}, {
				header : "轮次八",
				dataIndex : 'switch8',
				width : 80,
				align : 'center'
			}]);

	var ppdTurnCtrlStore = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(),
				reader : new Ext.data.JsonReader({
							root : 'turnStatusList'
						}, [{
									name : 'switch1'
								}, {
									name : 'switch2'
								}, {
									name : 'switch3'
								}, {
									name : 'switch4'
								}, {
									name : 'switch5'
								}, {
									name : 'switch6'
								}, {
									name : 'switch7'
								}, {
									name : 'switch8'
								}])
			});

	var ppdTurnCtrlGrid = new Ext.grid.GridPanel({
				autoScroll : true,
				height : 160,
				stripeRows : true,
				viewConfig : {
					forceFit : false
				},
				anchor : '100%',
				colModel : ppdTurnCtrlCm,
				ds : ppdTurnCtrlStore
			});

	var ppdTurnCtrlWindow = new Ext.Window({
				width : 550,
				height : 255,
				layout : "form",
				modal : true,
				closeAction : "close",
				plain : false,// 设置背景颜色
				resizable : false,
				draggable : false,// 不可移动
				buttonAlign : "center",// 按钮的位置
				title : '【开关控制页面】',
				items : [ppdTurnCtrlPanel, ppdTurnCtrlGrid],
				buttons : [{
							text : "跳闸",
							handler : function() {
								ctrlCheckStaff(closeTurn, 'ctrl');
							}
						}, {
							text : "合闸",
							handler : function() {
								ctrlCheckStaff(openTurn, 'ctrl');
							}
						}, {
							text : "召测开关状态",
							handler : function() {
								ctrlCheckStaff(fetchTurnStatus, 'ctrl');
							}
						}, {
							text : "退出",
							handler : function() {
								ppdTurnCtrlWindow.close();
							}
						}]
			});
	ppdTurnCtrlWindow.show();

	// 跳闸
	function closeTurn() {
		var taskSecond = Ext.Ajax.timeout / 1000;
		var ov = true;
		h_taskTime(taskSecond, function() {
					ov = false;
				});

		Ext.Ajax.request({// 异步执行
			url : './baseapp/prePaidDebug!closeTurn.action',
			params : {
				orgNo : orgNo,
				consNo : consNo,
				tmnlAssetNo : tmnlAssetNo,
				protocolCode : protocolCode,
				turnFlag : turnFlag,
				terminalId : terminalId,
				meterId : meterId,
				appNo : appNo,
				taskSecond : taskSecond
			},
			success : function(response) {
				if (!ov) {
					return;
				}
				var result = Ext.decode(response.responseText);
				if (null != result.cacheAndTmnlStatus
						&& "" != result.cacheAndTmnlStatus) {
					Ext.MessageBox.alert("提示", result.cacheAndTmnlStatus);
					return;
				}
				if (!Ext.isEmpty(status) && null != status && 'null' != status) {
					if ('3' == status) {
						Ext.MessageBox.alert("提示", '跳闸成功');
						return;
					} else {
						Ext.MessageBox.alert("提示", '跳闸失败');
						return;
					}
				} else {
					Ext.MessageBox.alert("提示", '跳闸失败');
					return;
				}
				overFlat = true;
				Ext.Ajax.timeout = 30000;
			}
		});
	}

	// 合闸
	function openTurn() {
		var taskSecond = Ext.Ajax.timeout / 1000;
		var ov = true;
		h_taskTime(taskSecond, function() {
					ov = false;
				});

		Ext.Ajax.request({// 异步执行
			url : './baseapp/prePaidDebug!openTurn.action',
			params : {
				orgNo : orgNo,
				consNo : consNo,
				tmnlAssetNo : tmnlAssetNo,
				protocolCode : protocolCode,
				turnFlag : turnFlag,
				terminalId : terminalId,
				meterId : meterId,
				appNo : appNo,
				taskSecond : taskSecond
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				if (null != result.cacheAndTmnlStatus
						&& "" != result.cacheAndTmnlStatus) {
					Ext.MessageBox.alert("提示", result.cacheAndTmnlStatus);
					return;
				}
				if (!Ext.isEmpty(status) && null != status && 'null' != status) {
					if ('3' == status) {
						Ext.MessageBox.alert("提示", '合闸成功');
						return;
					} else {
						Ext.MessageBox.alert("提示", '合闸失败');
						return;
					}
				} else {
					Ext.MessageBox.alert("提示", '合闸失败');
					return;
				}
				overFlat = true;
				Ext.Ajax.timeout = 30000;
			}
		});
	}

	// 召测开关状态
	function fetchTurnStatus() {
		var taskSecond = Ext.Ajax.timeout / 1000;
		var ov = true;
		h_taskTime(taskSecond, function() {
					ov = false;
				});
		Ext.Ajax.request({// 异步执行
			url : './baseapp/prePaidDebug!fetchTurnStatus.action',
			params : {
				tmnlAssetNo : tmnlAssetNo,
				taskSecond : taskSecond
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				if (null != result.cacheAndTmnlStatus
						&& "" != result.cacheAndTmnlStatus) {
					Ext.MessageBox.alert("提示", result.cacheAndTmnlStatus);
					return;
				}
				if (!Ext.isEmpty(status) && null != status && 'null' != status) {
					if ('3' == status) {
						Ext.MessageBox.alert("提示", '召测开关状态成功');
						ppdTurnCtrlStore.loadData(result);
						return;
					} else {
						Ext.MessageBox.alert("提示", '召测开关状态失败');
						return;
					}
				} else {
					Ext.MessageBox.alert("提示", '召测开关状态失败');
					return;
				}
			}
		});
	}
}
// ---------------------------------合闸跳闸以及召测开关状态链接-----------------------end

// ---------------------------------下发参数链接-----------------------start
function sendPPDParam(orgNo, consNo, tmnlAssetNo, totalNo, protocolCode,
		refreshFlag, buyValue, buyValueUnit, alarmValue, alarmValueUnit,
		jumpValue, jumpValueUnit, terminalId, meterId, appNo, feeCtrlId,
		turnFlag) {

	// 工单编号
	var sendParamBuyOrder = new Ext.form.TextField({
				fieldLabel : "购电单号",
				name : 'sendParamBuyOrder',
				labelSeparator : '',
				readOnly : true,
				editable : false,
				width : 120
			});

	// 跳闸电量
	var sendParamJumpValue = new Ext.form.TextField({
				fieldLabel : '跳闸电量',
				name : 'sendParamJumpValue',
				regex : /^\d+$/,
				maskRe : /^\d+$/,
				regexText : "请输入数字",
				labelSeparator : '',
				width : 120
			});

	// 终端资产号
	var sendParamTmnlAssetNo = new Ext.form.TextField({
				fieldLabel : '终端资产号',
				name : 'sendParamTmnlAssetNo',
				readOnly : true,
				editable : false,
				labelSeparator : '',
				width : 120
			});

	// 预购电量
	var sendParamBuyValue = new Ext.form.TextField({
				fieldLabel : '预购电量',
				name : 'sendParamBuyValue',
				regex : /^\d+$/,
				maskRe : /^\d+$/,
				regexText : "请输入数字",
				labelSeparator : '',
				width : 120
			});

	// 告警电量
	var sendParamAlarmValue = new Ext.form.TextField({
				fieldLabel : '告警电量',
				name : 'sendParamAlarmValue',
				regex : /^\d+$/,
				maskRe : /^\d+$/,
				regexText : "请输入数字",
				labelSeparator : '',
				width : 120
			});

	// 控制轮次
	var sendParamTurnFlag = new Ext.form.CheckboxGroup({
				width : 450,
				fieldLabel : '控制轮次',
				labelSeparator : '',
				items : [{
							boxLabel : '轮次1',
							inputValue : '1',
							name : '1'
						}, {
							boxLabel : '轮次2',
							inputValue : '2',
							name : '2'
						}, {
							boxLabel : '轮次3',
							inputValue : '3',
							name : '3'
						}, {
							boxLabel : '轮次4',
							inputValue : '4',
							name : '4'
						}, {
							boxLabel : '轮次5',
							inputValue : '5',
							name : '5'
						}, {
							boxLabel : '轮次6',
							inputValue : '6',
							name : '6'
						}, {
							boxLabel : '轮次7',
							inputValue : '7',
							name : '7'
						}, {
							boxLabel : '轮次8',
							inputValue : '8',
							name : '8'
						}]
			});

	// 总加组store
	var sendParamTotalStore = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(),
				reader : new Ext.data.JsonReader({
							root : 'ppdParamTotalList'
						}, [{
									name : 'totalNo'
								}])
			});

	var sendParamTotalComboBox = new Ext.form.ComboBox({
				store : sendParamTotalStore,
				name : 'sendParamTotalComboBox',
				fieldLabel : '总加组号',
				valueField : 'totalNo',
				editable : false,
				triggerAction : 'all',
				forceSelection : true,
				mode : 'local',
				selectOnFocus : true,
				emptyText : '请选择',
				displayField : 'totalNo',
				labelSeparator : '',
				width : 120
			});

	// 设置参数值
	sendParamBuyOrder.setValue(appNo);
	sendParamTmnlAssetNo.setValue(tmnlAssetNo);
	sendParamBuyValue.setValue(buyValue);
	sendParamAlarmValue.setValue(alarmValue);
	sendParamJumpValue.setValue(jumpValue);

	var ppdTurnFlag = new Array(false, false, false, false, false, false,
			false, false);
	if (!Ext.isEmpty(turnFlag) && "null" != turnFlag && 0 < turnFlag.length) {
		var tf = turnFlag.split(",");
		for (var i = 1; i <= 8; i = i + 1) {
			for (var j = 0; j < tf.length; j = j + 1) {
				if (tf[j] == i) {
					ppdTurnFlag[i - 1] = true;
					break;
				}
			}
		}
	}
	sendParamTurnFlag.setValue(ppdTurnFlag);

	Ext.Ajax.request({
				url : './baseapp/prePaidDebug!queryParamTotalInfo.action',
				params : {
					tmnlAssetNo : tmnlAssetNo
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					sendParamTotalStore.loadData(result);
					sendParamTotalComboBox.setValue(totalNo);
				}
			});

	// 预付费参数详细信息面板
	var sendParamPanel = new Ext.Panel({
				layout : "form",
				height : 125,
				items : [{
							layout : 'column',
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							items : [{
										columnWidth : .5,
										labelAlign : "right",
										labelWidth : 70,
										labelSeparator : "",
										layout : "form",
										border : false,
										items : [sendParamBuyOrder]
									}, {
										columnWidth : .5,
										labelAlign : "right",
										labelWidth : 70,
										labelSeparator : "",
										layout : "form",
										border : false,
										items : [sendParamTmnlAssetNo]
									}]
						}, {
							layout : 'column',
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							items : [{
										columnWidth : .5,
										labelAlign : "right",
										labelWidth : 70,
										labelSeparator : "",
										layout : "form",
										border : false,
										items : [sendParamBuyValue]
									}, {
										columnWidth : .5,
										labelAlign : "right",
										labelWidth : 70,
										labelSeparator : "",
										layout : "form",
										border : false,
										items : [sendParamAlarmValue]
									}]
						}, {
							layout : 'column',
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							items : [{
										columnWidth : .5,
										labelAlign : "right",
										labelWidth : 70,
										labelSeparator : "",
										layout : "form",
										border : false,
										items : [sendParamJumpValue]
									}, {
										columnWidth : .5,
										labelAlign : "right",
										labelWidth : 70,
										labelSeparator : "",
										layout : "form",
										border : false,
										items : [sendParamTotalComboBox]
									}]
						}, {
							labelAlign : "right",
							labelWidth : 70,
							labelSeparator : "",
							layout : "form",
							border : false,
							items : [sendParamTurnFlag]
						}]
			});

	var sendParamCm = new Ext.grid.ColumnModel([{
				header : "购电单号",
				dataIndex : 'appNo',
				align : "center"
			}, {
				header : "终端资产编号",
				dataIndex : 'tmnlAssetNo',
				align : 'center'
			}, {
				header : "总加组",
				dataIndex : 'totalNo',
				align : 'center'
			}, {
				header : "购电电量",
				dataIndex : 'buyValue',
				align : 'center'
			}, {
				header : "告警电量",
				dataIndex : 'alarmValue',
				align : 'center'
			}, {
				header : "跳闸电量",
				dataIndex : 'jumpValue',
				align : 'center'
			}]);

	var sendParamStore = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(),
				reader : new Ext.data.JsonReader({
							root : 'ppdParamList'
						}, [{
									name : 'appNo'
								}, {
									name : 'tmnlAssetNo'
								}, {
									name : 'totalNo'
								}, {
									name : 'alarmValue'
								}, {
									name : 'buyValue'
								}, {
									name : 'jumpValue'
								}])
			});

	var sendParamGrid = new Ext.grid.GridPanel({
				autoScroll : true,
				height : 160,
				stripeRows : true,
				viewConfig : {
					forceFit : false
				},
				anchor : '100%',
				colModel : sendParamCm,
				ds : sendParamStore
			});

	var sendParamWindow = new Ext.Window({
		html : '<iframe />',
		width : 550,
		height : 350,
		layout : "form",
		modal : true,
		closeAction : "close",
		plain : false,// 设置背景颜色
		resizable : false,
		draggable : false,// 不可移动
		buttonAlign : "center",// 按钮的位置
		title : '【预付费详细参数】',
		items : [sendParamPanel, sendParamGrid],
		buttons : [{
			text : "参数下发",
			handler : function() {
				var roundArray = sendParamTurnFlag.getValue();
				var round = "";
				if (0 == roundArray.length) {
					Ext.MessageBox.alert("提示", "轮次不能为空，请选择！");
					return;
				}
				if (0 < roundArray.length) {
					round = roundArray[0].getRawValue();
					for (var i = 1; i < roundArray.length; i++) {
						round = round + ',' + roundArray[i].getRawValue();
					}
				}
				var newBuyValue = sendParamBuyValue.getValue();
				var newAlarmValue = sendParamAlarmValue.getValue();
				var newJumpValue = sendParamJumpValue.getValue();
				var newTotalNo = sendParamTotalComboBox.getValue();
				// 因为都是数字，所以如果为'',则必须设置为null，和数据库保持一致
				if ('' == newBuyValue) {
					newBuyValue = null;
				}
				if ('' == newAlarmValue) {
					newAlarmValue = null;
				}
				if ('' == newJumpValue) {
					newJumpValue = null;
				}
				if (Ext.isEmpty(newTotalNo)) {
					newTotalNo = null;
				}
				Ext.Ajax.request({
							url : './baseapp/prePaidDebug!sendPPDParam.action',
							params : {
								orgNo : orgNo,
								consNo : consNo,
								tmnlAssetNo : tmnlAssetNo,
								totalNo : newTotalNo,
								protocolCode : protocolCode,
								refreshFlag : refreshFlag,
								buyValue : sendParamBuyValue.getValue(),
								jumpValue : sendParamJumpValue.getValue(),
								alarmValue : sendParamAlarmValue.getValue(),
								buyValueUnit : buyValueUnit,
								alarmValueUnit : alarmValueUnit,
								jumpValueUnit : jumpValueUnit,
								terminalId : terminalId,
								meterId : meterId,
								appNo : appNo,
								feeCtrlId : feeCtrlId,
								turnFlag : round
							},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								if (null != result.cacheAndTmnlStatus
										&& "" != result.cacheAndTmnlStatus) {
									Ext.MessageBox.alert("提示",
											result.cacheAndTmnlStatus);
									return;
								}
								if (!Ext.isEmpty(status) && null != status
										&& 'null' != status) {
									if ('3' == status) {
										Ext.MessageBox.alert("提示", '下发参数成功');
										sendParamStore.loadData(result);
										return;
									} else {
										Ext.MessageBox.alert("提示", '下发参数失败');
										return;
									}
								} else {
									Ext.MessageBox.alert("提示", '下发参数失败');
									return;
								}
							}
						});
			}
		}, {
			text : "参数召测",
			handler : function() {
				Ext.Ajax.request({
							url : './baseapp/prePaidDebug!fetchPPDParam.action',
							params : {
								orgNo : orgNo,
								consNo : consNo,
								tmnlAssetNo : tmnlAssetNo,
								totalNo : newTotalNo,
								protocolCode : protocolCode,
								terminalId : terminalId,
								meterId : meterId,
								appNo : appNo
							},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								if (null != result.cacheAndTmnlStatus
										&& "" != result.cacheAndTmnlStatus) {
									Ext.MessageBox.alert("提示",
											result.cacheAndTmnlStatus);
									return;
								}
								if (!Ext.isEmpty(status) && null != status
										&& 'null' != status) {
									if ('3' == status) {
										Ext.MessageBox.alert("提示", '召测参数成功');

										return;
									} else {
										Ext.MessageBox.alert("提示", '召测参数失败');
										return;
									}
								} else {
									Ext.MessageBox.alert("提示", '召测参数失败');
									return;
								}
							}
						});
			}
		}, {
			text : "退出",
			handler : function() {
				sendParamWindow.close();
			}
		}]
	});

	sendParamWindow.show();
}
// ---------------------------------下发参数链接-----------------------end

// ---------------------------------下发方案链接-----------------------start
function sendPPDCmd(orgNo, consNo, tmnlAssetNo, totalNo, protocolCode,
		terminalId, meterId, appNo, debugStatus) {
	// 只有下发参数成功，才能执行方案，而方案表，只有下发参数和下发方案会修改状态
	if (!Ext.isEmpty(debugStatus) && '调试成功' == debugStatus) {
		Ext.Ajax.request({// 异步执行
			url : './baseapp/prePaidDebug!sendPPDCmd.action',
			params : {
				orgNo : orgNo,
				consNo : consNo,
				tmnlAssetNo : tmnlAssetNo,
				totalNo : totalNo,
				protocolCode : protocolCode,
				terminalId : terminalId,
				meterId : meterId,
				appNo : appNo
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				if (null != result.cacheAndTmnlStatus
						&& "" != result.cacheAndTmnlStatus) {
					Ext.MessageBox.alert("提示", result.cacheAndTmnlStatus);
					return;
				}
				if (!Ext.isEmpty(status) && null != status && 'null' != status) {
					if ('3' == status) {
						Ext.MessageBox.alert("提示", '下发方案成功');
						return;
					} else {
						Ext.MessageBox.alert("提示", '下发方案失败');
						return;
					}
				} else {
					Ext.MessageBox.alert("提示", '下发方案失败');
					return;
				}
			},
			failure : function(response) {
				Ext.MessageBox.alert('提示', '下发方案失败！');
				return;
			}
		});
	} else {
		Ext.MessageBox.alert("提示", '下发参数失败，无法进行预购电执行操作');
		return;
	}
}
// ---------------------------------下发方案链接-----------------------end

// ---------------------------------链接到原始报文查询-----------------------start
function frameLink(consNo, consName) {
	ppd_Frame_Flag = true;
	ppd_Frame_ConsNo = consNo;
	if (Ext.isEmpty(consName) || "null" == consName) {
		consName = "";
	}
	ppd_Frame_ConsName = consName;
	openTab("原始报文查询", "./baseApp/dataGatherMan/origFrameQry.jsp", false,
			"origFrameQry");
}
// ---------------------------------链接到原始报文查询-----------------------end

// ---------------------------------总加组设置-----------------------start
function totalConfig(tmnlAssetNo,consNo,orgNo,pOrgNo,tmnlAddr,meterId,
              cisTmnlAssetNo,cpNo,protocolCode) {
	var totalList;//终端下总加组列表信息
	var newFlag = false;//是否新建
	
	var totalNoTextField = new Ext.form.TextField({
				fieldLabel : '总加组号',
				allowBlank : false,
				labelSeparator : '',
				name : 'totalNoTextField',
				emptyText : '请输入',
				regex : /^\d+$/,
				maskRe : /^\d+$/,
				anchor : '85%',
				border : false
			});

	// 总加组store
	var totalNoStore = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(),
				reader : new Ext.data.JsonReader({
							root : 'ppdTotalSetList'
						}, [{name : 'totalNo'}])
			});

	var totalNoComboBox = new Ext.form.ComboBox({
				store : totalNoStore,
				name : 'totalNoComboBox',
				fieldLabel : '总加组号',
				valueField : 'totalNo',
				editable : false,
				triggerAction : 'all',
				forceSelection : true,
				mode : 'local',
				selectOnFocus : true,
				emptyText : '请选择',
				displayField : 'totalNo',
				labelSeparator : '',
				anchor : '90%',
				listeners : {
					'select' : function(c,r,i) {
						totalCalGridStore.removeAll();
			   			totalCalGridStore.baseParams["totalNo"] = this.getValue();
			   			totalCalGridStore.baseParams["totalNoAll"] = false;
			   			totalCalGridStore.load({
						    callback: function(records, options, success){						    	
						    	if(!Ext.isEmpty(records) && 0 < records.length){
						    		totalNameTextField.setValue(records[0].get('totalName'));
						    	}
	                	        totalCalGrid.getSelectionModel().selectAll();
						    }
			        	});
					}
				}
			});

	var totalNameTextField = new Ext.form.TextField({
				fieldLabel : '总加组名称',
				allowBlank : false,
				labelSeparator : '',
				name : 'totalNameTextField',
				emptyText : '请输入',
				anchor : '95%',
				border : false
			});

	var totalParameterPnl = new Ext.Panel({
				frame : false,
				height : 30,
				region : 'north',
				bodyStyle : 'padding:5px 0px 0px 0px',
				layout : 'column',
				border : false,
				items : [{
							columnWidth : .22,
							layout : 'table',
							border : false,
							layoutConfig : {
								columns : 2
							},
							items : [{
										width:140,
										border : false,
										labelWidth : 50,
										layout : 'form',
										labelAlign : 'right',
										items : [totalNoComboBox]
									}, {
										width:145,
										border : false,
										labelWidth : 60,
										layout : 'form',
										labelAlign : 'right',
										items : [totalNoTextField]
									}]
						}, {
							columnWidth : .28,
							border : false,
							labelWidth : 70,
							layout : 'form',
							labelAlign : 'right',
							items : [totalNameTextField]
						}, {
							columnWidth : .1,
							border : false,
							layout : 'form',
							items : [{
										xtype : 'button',
										text : '新建',
										width : 60,
										handler : function() {
											newFlag = true;
											totalNoComboBox.ownerCt.setVisible(false);//把上层隐掉
											totalNoTextField.ownerCt.setVisible(true);
											totalNameTextField.setValue("");
											totalNoTextField.setValue("");
											totalCalGridStore.removeAll();
											totalCalGridStore.baseParams["totalNoAll"] = true;
			        						totalCalGridStore.baseParams["tmnlAssetNo"] = tmnlAssetNo;
											totalCalGridStore.load({
											    callback: function(records, options, success){
						                	        totalCalGrid.getSelectionModel().selectAll();
											    }
								        	});
								        	Ext.getCmp('delBtn').setDisabled(true);
										}
									}]
						}, {
							columnWidth : .1,
							border : false,
							layout : 'form',
							items : [{
										xtype : 'button',
										text : '删除',
										id:'delBtn',
										width : 60,
										handler : function() {
											var totalNo;
											if(newFlag){
												totalNo = totalNoTextField.getValue();
												if(Ext.isEmpty(totalNo) || 'null' == totalNo){
													Ext.MessageBox.alert("提示", '总加组号不能为空!');
													return;
												}
											}else{
												var totalNo = totalNoComboBox.getValue();
												if(Ext.isEmpty(totalNo) || 'null' == totalNo){
													Ext.MessageBox.alert("提示", '总加组号不能为空!');
													return;
												}
											}
											if(checkTmnlTotal(totalNo)){//存在下发记录不能删除
												Ext.MessageBox.alert("提示", '总加组号已经下发，不能删除!');
												return;
											}
											dltTmnlTotal(totalNo);
										}
									}]
						}, {
							columnWidth : .1,
							border : false,
							layout : 'form',
							items : [{
										xtype : 'button',
										text : '保存',
										width : 60,										
										handler : function() {
											var totalNo;
											if(newFlag){
												totalNo = totalNoTextField.getValue();
												if(Ext.isEmpty(totalNo) || 'null' == totalNo){
													Ext.MessageBox.alert("提示", '总加组号不能为空!');
													return;
												}
												var mc = totalNoStore.query('totalNo',totalNo);
												if(!Ext.isEmpty(mc) && mc.length > 0){
													Ext.MessageBox.alert("提示", '总加组号已经存在，请重新输入!');
													return;
												}
											}else{
												var totalNo = totalNoComboBox.getValue();
												if(Ext.isEmpty(totalNo) || 'null' == totalNo){
													Ext.MessageBox.alert("提示", '总加组号不能为空!');
													return;
												}
											}
											var totalName = totalNameTextField.getValue();
											if(Ext.isEmpty(trim(totalName)) || (trim(totalName)).length == 0){
												Ext.MessageBox.alert("提示", '总加组名称不能为空!');
												return;
											}
											save(totalNo,totalName);
										}
									}]
						}, {
							columnWidth : .1,
							border : false,
							layout : 'form',
							items : [{
										xtype : 'button',
										id: 'sendBtn',
										text : '下发',
										disabled : true,
										width : 60,
										handler : function() {
											var totalNo;
											if(newFlag){
												totalNo = totalNoTextField.getValue();
												if(Ext.isEmpty(totalNo) || 'null' == totalNo){
													Ext.MessageBox.alert("提示", '总加组号不能为空!');
													return;
												}
											}else{
												var totalNo = totalNoComboBox.getValue();
												if(Ext.isEmpty(totalNo) || 'null' == totalNo){
													Ext.MessageBox.alert("提示", '总加组号不能为空!');
													return;
												}
											}
											sendTotalToTmnl(totalNo);
										}
									}]
						}, {
							columnWidth : .1,
							border : false,
							layout : 'form',
							items : [{
										xtype : 'button',
										text : '退出',
										width : 60,
										handler : function() {
											totalWindow.close();
										}
									}]
						}]
			});

	//初始显示combobox
	totalNoComboBox.ownerCt.setVisible(true);
	totalNoTextField.ownerCt.setVisible(false);
			
	var totalSm = new Ext.grid.CheckboxSelectionModel({});
	
	var posOppStore = new Ext.data.ArrayStore({
		fields : ['value', 'name'],
		data : [['1', '正向'], ['2', '反向']]
	});
	
	var totalCalStore = new Ext.data.ArrayStore({
		fields : ['value', 'name'],
		data : [['0', '加'], ['1', '减']]
	});
	
	var poValue="";//用于修改store中的编码
	var poName="";//用于页面显示值
	var toValue="";//用于修改store中的编码
	var toName="";//用于页面显示值
	
	var cm = new Ext.grid.ColumnModel([totalSm,{
				header : '测量点号',
				width : 50,
				dataIndex : 'mpSn',
				align : 'left'
			}, {
				header : '测量点类型',
				width : 60,
				dataIndex : 'mpType',
				align : 'left'
			}, {
				header : '正/反向',
				dataIndex : 'pulseAttrName',
				align : 'center',
				width : 120,
				editor : new Ext.form.ComboBox({
							typeAhead : true,
							triggerAction : 'all',
							store : posOppStore,
							editable : false,
							mode:'local',
							displayField : 'name',
							valueField : 'value',
							emptyText : '-请选择-',
							blankText : '-请选择-',
							selectOnFocus : true,
							listeners : {
								'select' : function(c,r,i) {
									poValue = this.getValue();
									poName = this.getRawValue();
								}
							}
						}),
				renderer : function(val,s,rec) {
					var name = poName;
					var value = poValue;
					poName = "";
					poValue = "";
					if(Ext.isEmpty(name)){
						if(Ext.isEmpty(rec.get('pulseAttr'))){
							return '-请选择-';
						}else{
							var mc = posOppStore.query('value',rec.get('pulseAttr'));
							if(!Ext.isEmpty(mc) && mc.length > 0){
								return "<div align = 'center'>" + mc.get(0).get('name') + "</div>";
							}else{
								return '-请选择-';
							}
						}
					}else{
						rec.set('pulseAttr', value);
						return "<div align = 'center'>" + name + "</div>";
					}
				}
			}, {
				header : '总加计算',
				dataIndex : 'calcFlagName',
				align : 'center',
				width : 120,
				editor : new Ext.form.ComboBox({
							typeAhead : true,
							triggerAction : 'all',
							store : totalCalStore,
							editable : false,
							mode:'local',
							displayField : 'name',
							valueField : 'value',
							emptyText : '-请选择-',
							blankText : '-请选择-',
							selectOnFocus : true,
							listeners : {
								'select' : function(c,r,i) {
									toValue = this.getValue();
									toName = this.getRawValue();
								}
							}
						}),
				renderer : function(val,s,rec) {
					var name = toName;
					var value = toValue;
					toName = "";
					toValue = "";
					if(Ext.isEmpty(name)){
						if(Ext.isEmpty(rec.get('calcFlag'))){
							return '-请选择-';
						}else{
							var mc = totalCalStore.query('value',rec.get('calcFlag'));
							if(!Ext.isEmpty(mc) && mc.length > 0){
								return "<div align = 'center'>" + mc.get(0).get('name') + "</div>";
							}else{
								return '-请选择-';
							}
						}
					}else{
						rec.set('calcFlag', value);
						return "<div align = 'center'>" + name + "</div>";
					}
				}
			}, {
				header : '计量点编号',
				width : 60,
				dataIndex : 'cmpNo',
				align : 'left'
			}, {
				header : '计量点类型',
				width : 60,
				dataIndex : 'cmpName',
				align : 'left'
			}]);

	var totalCalGridStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : './baseapp/prePaidDebug!queryTotalMpInfo.action'
				}),
				reader : new Ext.data.JsonReader({
							root : 'ppdTotalMpSetList'
						}, [{name : 'mpSn'},
						    {name : 'mpType'},
						    {name : 'pulseAttr'},
						    {name : 'calcFlag'},
						    {name : 'cmpNo'},
						    {name : 'cmpName'},
						    {name : 'calcFlagName'},
						    {name : 'pulseAttrName'},
						    {name : 'totalName'}
						])
			});

	var totalCalGrid = new Ext.grid.EditorGridPanel({
				store : totalCalGridStore,
				loadMask : true,
				cm : cm,
				sm : totalSm,
				stripeRows : true,
				monitorResize : true,
				autoWidth : true,
				region : 'center',
				border : false,
				autoScroll : true,
				tbar : [{
							xtype : 'label',
							html : "<font font-weight:bold;>总加组设置</font>"
						}],
				viewConfig : {
					forceFit : true
				}
			});

	var totalWindow = new Ext.Window({
				width : 650,
				height : 300,
				layout : "border",
				modal : true,
				closeAction : "close",
				plain : false,// 设置背景颜色
				resizable : false,
				draggable : false,// 不可移动
				buttonAlign : "center",// 按钮的位置
				title : '【总加组设置】',
				items : [totalParameterPnl, totalCalGrid]
			});
			
    Ext.Ajax.request({
		url : "./baseapp/prePaidDebug!queryTotalInfo.action",
		params : {
			tmnlAssetNo : tmnlAssetNo
		},
		success : function(response) {
			var result = Ext.decode(response.responseText);
			if (result) {
			    totalNoStore.loadData(result);
			    totalList = result.ppdTotalSetList;
			    if(totalList.length > 0){
			        totalNoComboBox.setValue(totalList[0].totalNo);
			        totalNameTextField.setValue(totalList[0].totalName);
			        totalCalGridStore.removeAll();
			        totalCalGridStore.baseParams["totalNo"] = totalNoComboBox.getValue();
			        totalCalGridStore.baseParams["totalNoAll"] = false;
			        totalCalGridStore.baseParams["tmnlAssetNo"] = tmnlAssetNo;
			        totalCalGridStore.load({
			        	callback: function(records, options, success){
	                	    totalCalGrid.getSelectionModel().selectAll();
			        	}
			        });
			    }
			}
		}
	});
	
	totalWindow.show();
	
	//修改或保存
	function save(totalNo,totalName){
		var recs = totalSm.getSelections();
		if (null == recs || recs.length == 0) {
			Ext.MessageBox.alert("提示", "请选择测量点！");
			return;
		}
		var mpArray = new Array();
		for (var i = 0; i < recs.length; i++) {
			if(Ext.isEmpty(recs[i].get('pulseAttr')) || 'null' == recs[i].get('pulseAttr')){
				Ext.MessageBox.alert("提示", "请选择正向或反向！");
			    return;
			}
			if(Ext.isEmpty(recs[i].get('calcFlag')) || 'null' == recs[i].get('calcFlag')){
				Ext.MessageBox.alert("提示", "请选择加或减！");
			    return;
			}
			var tmnl = recs[i].get('mpSn') + '`'
					+ recs[i].get('pulseAttr')
					+ '`' + recs[i].get('calcFlag');
			mpArray[i] = tmnl;
		}
		Ext.Ajax.request({
			url : "./baseapp/prePaidDebug!addOrUpdateTotalInfo.action",
			params : {
				tmnlAssetNo : tmnlAssetNo,
				consNo : consNo,
				orgNo : orgNo,
				areaNo : pOrgNo,
				tmnlAddr : tmnlAddr,
                cisTmnlAssetNo : cisTmnlAssetNo,
                cpNo : cpNo,
                newFlag : newFlag,
                totalNo : totalNo,
                meterId : meterId,
                totalName : totalName,
                mpList : mpArray
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
			    if (result) {
			    	if(1 == result.operCode){
			    		Ext.getCmp('sendBtn').setDisabled(false);
			    		Ext.MessageBox.alert('提示','数据操作成功！')
			    		
			    		//刷新store
			    		totalCalGridStore.removeAll();
			    		totalCalGridStore.baseParams["totalNo"] = totalNo;
				        totalCalGridStore.baseParams["totalNoAll"] = false;
				        totalCalGridStore.baseParams["tmnlAssetNo"] = tmnlAssetNo;
				        totalCalGridStore.load({
				        	callback: function(records, options, success){
		                	    totalCalGrid.getSelectionModel().selectAll();
				        	}
				        });
				        
				        Ext.Ajax.request({
							url : "./baseapp/prePaidDebug!queryTotalInfo.action",
							params : {
								tmnlAssetNo : tmnlAssetNo
							},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								if (result) {
								    totalNoStore.loadData(result);
								}
							}
				        });
				        
			    		return;
			    	}else{
			    		Ext.MessageBox.alert('提示','数据操作失败！')
			    		return; 
			    	}
			    }
			    Ext.MessageBox.alert('提示','数据操作失败！')
				return;
			},
			failure : function(response) {
				Ext.MessageBox.alert('提示','数据操作失败！');
				return;
			}
		});
	}
	
	//校验是否可以删除，如果存在下发记录，则返回false
	function checkTmnlTotal(totalNo){
		Ext.Ajax.request({
			url : "./baseapp/prePaidDebug!checkTmnlTotal.action",
			params : {
				tmnlAssetNo : tmnlAssetNo,
                totalNo : totalNo
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
			    if (result) {
			    	if(0 == result.totalRecord){
			    		return false;
			    	}else{
			    		return true;
			    	}
			    }
			    return true;
			},
			failure : function(response) {
				return false;
			}
		});
	}
	
	//删除总加组
	function dltTmnlTotal(totalNo){
		Ext.Ajax.request({
			url : "./baseapp/prePaidDebug!dltTmnlTotal.action",
			params : {
				tmnlAssetNo : tmnlAssetNo,
                totalNo : totalNo
			},
			success : function(response) {
			    var result = Ext.decode(response.responseText);
			    if (result) {
			    	if(1 == result.operCode){
			    		Ext.MessageBox.alert('提示','数据删除成功！');
			    		
			    		Ext.Ajax.request({
							url : "./baseapp/prePaidDebug!queryTotalInfo.action",
							params : {
								tmnlAssetNo : tmnlAssetNo
							},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								if (result) {
								    totalNoStore.loadData(result);
								    totalList = result.ppdTotalSetList;
								    if(totalList.length > 0){
								        totalNoComboBox.setValue(totalList[0].totalNo);
								        totalNameTextField.setValue(totalList[0].totalName);
								        totalCalGridStore.removeAll();
								        totalCalGridStore.baseParams["totalNo"] = totalNoComboBox.getValue();
								        totalCalGridStore.baseParams["totalNoAll"] = false;
								        totalCalGridStore.baseParams["tmnlAssetNo"] = tmnlAssetNo;
								        totalCalGridStore.load({
								        	callback: function(records, options, success){
						                	    totalCalGrid.getSelectionModel().selectAll();
								        	}
								        });
								    }else{
								    	totalNoStore.removeAll();
										totalNoComboBox.clearValue();
								    	totalNameTextField.setValue("");
								    	totalCalGridStore.removeAll();
								    }
								}
							}
						});
			    		return;
			    	}else{
			    		Ext.MessageBox.alert('提示','数据删除失败！');
			    		return;
			    	}
			    }
			    Ext.MessageBox.alert('提示','数据删除失败！');
			    return;
			},
			failure : function(response) {
				Ext.MessageBox.alert('提示','数据删除失败！');
				return;
			}
		});
	}
	
	//下发总加组参数到终端
	function sendTotalToTmnl(totalNo){
		var recs = totalSm.getSelections();
		if (null == recs || recs.length == 0) {
			Ext.MessageBox.alert("提示", "请选择测量点！");
			return;
		}
		var mpArray = new Array();
		for (var i = 0; i < recs.length; i++) {
			if(Ext.isEmpty(recs[i].get('pulseAttr')) || 'null' == recs[i].get('pulseAttr')){
				Ext.MessageBox.alert("提示", "请选择正向或反向！");
			    return;
			}
			if(Ext.isEmpty(recs[i].get('calcFlag')) || 'null' == recs[i].get('calcFlag')){
				Ext.MessageBox.alert("提示", "请选择加或减！");
			    return;
			}
			var tmnl = recs[i].get('mpSn') + '`'
					+ recs[i].get('pulseAttr')
					+ '`' + recs[i].get('calcFlag');
			mpArray[i] = tmnl;
		}
		Ext.Ajax.request({
			url : "./baseapp/prePaidDebug!sendTotalToTmnl.action",
			params : {
				tmnlAssetNo : tmnlAssetNo,
                totalNo : totalNo,
                protocolCode:protocolCode,
                mpList : mpArray
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				if (!Ext.isEmpty(status) && null != status
						&& 'null' != status) {
					if ('3' == status) {
						Ext.MessageBox.alert("提示", '下发成功');
						return;
					} else {
						Ext.MessageBox.alert("提示", '下发失败');
						return;
					}
				} else {
					Ext.MessageBox.alert("提示", '下发失败');
					return;
				}
			},
			failure : function(response) {
				Ext.MessageBox.alert('提示','下发失败！');
				return;
			}
		});
	}
}
// ---------------------------------总加组设置-----------------------end
