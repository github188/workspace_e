var showDataDetail;

/** ************** */
// 任务进度条 参见f_taskTime
/** ***稍作特别化改动，如果召测结束关闭等待*** */
var overFlat = false;

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
				fn : function(e) {
					flag = 0;
					if (fn && typeof fn == "function") {
						fn();
					}
					clearInterval(aa);
				}
			});

	var f = function() {
		return function() {
			flag = flag + 1;

			if (flag == task_s + 1 || overFlat) {
				Ext.MessageBox.hide();
				flag = 0;
				if (fn && typeof fn == "function") {
					fn();
				}
				clearInterval(aa);
			} else {
				var i = flag;
				Ext.MessageBox.updateProgress(i / (task_s), i + ' 秒');
			}
		};
	};
	var aa = setInterval(f(), 1000);
};

/* 弹出原始报文窗口 */
function origFrameQryShow(consNo, consName, tmnlAssetAddr) {
	staticConsNo = consNo;
	staticConsName = consName;
	staticTmnlAssetAddr = tmnlAssetAddr;
	openTab("原始报文查询", "./baseApp/dataGatherMan/origFrameQry.jsp");
}

Ext.onReady(function() {
	// 翻页用store
	var storeForPage = new Ext.data.ArrayStore({
				idIndex : 6,
				fields : [{
							name : 'bureauNo'
						}, {
							name : 'userNumber'
						}, {
							name : 'userName'
						}, {
							name : 'terminalAddr'
						}, {
							name : 'dataIndex'
						}, {
							name : 'status'
						}, {
							name : 'tmnlAssetNo'
						}, {
							name : 'dataDetails'
						}, {
							name : 'protocolCode'
						}]
			});;
	/* 规约类型 */
	var typeComStore = new Ext.data.JsonStore({
				idProperty : 'protocolCode',
				url : './runman/abnormalhandle/eventManage!queryPortocolCode.action',
				fields : ['protocolCode', 'protocolName'],
				root : "pc",
				// autoLoad: true,
				listeners : {
					load : function() {
						gyComboBox.setValue(gyComboBox.getValue());
					}
				}
			});
	// 规约combobox
	var gyComboBox = new Ext.form.ComboBox({
				id : 'emComboBox',
				fieldLabel : '规约',
				store : typeComStore,
				labelSeparator : '',
				bodyStyle : 'padding:10px;',
				triggerAction : 'all',
				editable : false,
				mode : 'local',
				valueField : 'protocolCode',
				displayField : 'protocolName',
				anchor : '90%',
				emptyText : '请选择规约',
				listeners : {
					select : setTitle
				},
				selectOnFocus : true
			});

	// 规约类型json数据load
	typeComStore.load();
	// 初始化combobox的value值
	typeComStore.on("load", onstoreStoreLoad, typeComStore, true);

	// 设置combobox的初始化value值
	function onstoreStoreLoad() {
		if (typeComStore.getTotalCount() > 0) {
			gyComboBox.setValue(typeComStore.getAt(0).data.protocolCode);
			initRGAjax();
		}
	}

	// 生成规约类型对应的RadioGroup
	function setTitle() {
		Ext.Ajax.request({
					url : './runman/abnormalhandle/eventManage!queryDataType.action',
					params : {
						// 从combobox中获取规约编码
						protocolCode : Ext.getCmp('emComboBox').value
					},
					success : setTitleResponse
				});
	}

	function initRGAjax() {
		Ext.Ajax.request({
					url : './runman/abnormalhandle/eventManage!queryDataType.action',
					params : {
						// 获取缺省的规约项编码
						protocolCode : typeComStore.getAt(0).data.protocolCode
					},
					success : setTitleResponse
				});
	}

	// 根据规约项名称设定规约GirdPanel的title名称
	function setTitleResponse(response) {
		//设置抬头
		Ext.getCmp('emTtLab').setText("<font font-weight:bold;>备选用户</font>", false);
		// 清空备选用户	
		storeForPage.removeAll(true);
		resultStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(storeForPage));
		resultStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
		var result = Ext.decode(response.responseText);
		if (result.dt.length > 0) {
			var protocolNo = result.dt[0].dataType;
			var protocolName = result.dt[0].dataName;
			var grid = Ext.getCmp('pfemEditorGrid');
			var protcolField = Ext.getCmp('protocolCode');
			protcolField.setValue(protocolNo);
			Ext.getCmp('fnLabel').setValue(protocolName);
			loadGridData();
			// grid.setTitle(protocolName);
			grid.doLayout();

		}
	}

	/**
	 * 参数设置editorGridPanel相关
	 */
	// 参数设置编辑store
	var editorStore = new Ext.data.ArrayStore({
				fields : [{
							name : 'erc'
						}, {
							name : 'eventName'
						}, {
							name : 'save'
						}, {
							name : 'level'
						}, {
							name : 'eventNo'
						}]
			});

	var dataComStore1 = new Ext.data.ArrayStore({
				fields : ['dataValue', 'displayText']
			});

	var dataComStore2 = new Ext.data.ArrayStore({
				fields : ['dataValue', 'displayText']
			});

	Ext.ux.MultiEditColumnModel = Ext.extend(Ext.grid.ColumnModel, {
				getCellEditor : function(colIndex, rowIndex) {
					if (colIndex == 2) {
						return new Ext.grid.GridEditor(new Ext.form.Field({
											autoCreate : {
												tag : 'select',
												children : [{
															tag : 'option',
															value : '0',
															html : '屏蔽'
														}, {
															tag : 'option',
															value : '1',
															html : '打开'
														}]
											},
											getValue : function() {
												return this.el.dom.value;
											}
										}), {
									autoSize : 'both'
								});
					} else {
						return this.config[colIndex].getCellEditor(rowIndex);
					}

				}
			});

	var userCm = new Ext.ux.MultiEditColumnModel({
				defaults : {
					sortable : false,
					remoteSort : false
				},
				columns : [{
							header : '事件代码ERC',
							dataIndex : 'erc',
							width : 30,
							align : 'left'
						}, {
							header : '事件项目',
							dataIndex : 'eventName',
							width : 30,
							align : 'left'
						}, {
							header : '事件',
							dataIndex : 'save',
							width : 30,
							align : 'center',
							renderer : function(value, metaData, record,
									rowIndex, colIndex, store) {
								return value == 0 ? '屏蔽' : '打开';

							},
							editor : new Ext.form.TextField({
										allowBlank : false
									})
						}, {
							header : '报警级别',
							dataIndex : 'level',
							width : 30,
							align : 'center',
							renderer : function(value, metaData, record,
									rowIndex, colIndex, store) {
								return value == 1 ? '重要事件' : '一般事件';
							}
							// editor : new Ext.form.TextField({
							// allowBlank : false
							// })
						}, {
							header : '事件代码',
							dataIndex : 'eventNo',
							width : 30,
							align : 'left',
							hidden : true
						}]
			});

	var userGrid = new Ext.grid.EditorGridPanel({
				id : 'emEditorGrid',
				store : editorStore,
				border : false,
				cm : userCm,
				autoScroll : true
			});

	// 获取操作对象用户信息
	new LeftTreeListener({
				modelName : '事件屏蔽与分级',
				processUserGridSelect : getTermnInfo,
				processClick : getNodeInfo
			});

	function getTermnInfo(sm, row, record) {
		var protocolCode = Ext.getCmp('emComboBox').value;
		if (record.data.protocolCode !== protocolCode) {
			var protocolName = Ext.getCmp('emComboBox').getStore()
					.getById(record.data.protocolCode).data.protocolName;
			Ext.Msg.alert('', record.data.tmnlAssetNo + '规约类型（' + protocolName
							+ '）与页面规约不一致');
			return;
		}
		// 获取数据库参数值
		Ext.Ajax.request({
			url : './runman/feildman/terminalparaset!queryTmnlList.action',
			params : {
				nodeType : 'usr',
				nodeValue : record.data.tmnlAssetNo,
				protocolCode : protocolCode
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				if (!result.tmnlList) {
					return;
				}
				var data = new Array();
				for (var i = 0; i < result.tmnlList.length; i++) {
					data[i] = new Array();
					// 供电单位
					data[i][0] = result.tmnlList[i].orgName;
					// 用户编号
					data[i][1] = result.tmnlList[i].consNO;
					// 用户名称
					data[i][2] = result.tmnlList[i].consName;
					// 终端地址
					data[i][3] = result.tmnlList[i].terminalAddr;
					// 数据索引
					data[i][4] = result.tmnlList[i].tmnlAssetNo;
					// 通信状态
					// data[0][5] = '1';
					// 终端资产编号
					data[i][6] = result.tmnlList[i].tmnlAssetNo;
					// 数据明细
					data[i][8] = result.tmnlList[i].protocolCode;
				}
				storeForPage.loadData(data, true);
				var rsStore = Ext.getCmp('emresultGrid').getStore();
				rsStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(storeForPage));
				rsStore.load({
							params : {
								start : 0,
								limit : DEFAULT_PAGE_SIZE
							}
						});
				resultSm.selectAll();
				Ext.getCmp('emresultGrid').doLayout();
			}
		});
	}

	// 获取节点用户信息
	function getNodeInfo(p, node, e) {
		var obj = node.attributes.attributes;
		var nodeType = node.attributes.type;
		var nodeValue = null;
		var protocolCode = Ext.getCmp('emComboBox').value;
		if (nodeType == 'org') {
			nodeValue = obj.orgNo;
		} else if (nodeType == 'usr') {
			nodeValue = obj.tmnlAssetNo;
		} else if (nodeType == 'line') {
			nodeValue = obj.lineId;
		} else if (nodeType == 'cgp' || nodeType == 'ugp') {
			nodeValue = obj.groupNo;
		} else {
			Ext.Msg.alert('提示', '请选择线路、供电所、群组或用户节点');
			return;
		}
		// 获取数据库参数值
		Ext.Ajax.request({
			url : './runman/feildman/terminalparaset!queryTmnlList.action',
			params : {
				nodeType : nodeType,
				nodeValue : nodeValue,
				protocolCode : protocolCode
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				if (!result.tmnlList) {
					return;
				}
				if (result.tmnlList.length == 0) {
					Ext.getCmp('emTtLab').setText("<font font-weight:bold;>备选用户</font><font color='red'>【该节点与页面规约一致的终端个数为0】</font>", false);
					return;
				}
				var data = new Array();
				for (var i = 0; i < result.tmnlList.length; i++) {
					data[i] = new Array();
					// 供电单位
					data[i][0] = result.tmnlList[i].orgName;
					// 用户编号
					data[i][1] = result.tmnlList[i].consNO;
					// 用户名称
					data[i][2] = result.tmnlList[i].consName;
					// 终端地址
					data[i][3] = result.tmnlList[i].terminalAddr;
					// 数据索引
					data[i][4] = result.tmnlList[i].tmnlAssetNo;
					// 通信状态
					// data[0][5] = '1';
					// 终端资产编号
					data[i][6] = result.tmnlList[i].tmnlAssetNo;
					// 数据明细
					data[i][8] = result.tmnlList[i].protocolCode;
				}
				storeForPage.loadData(data, true);
				var rsStore = Ext.getCmp('emresultGrid').getStore();
				rsStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(storeForPage));
				rsStore.load({
							params : {
								start : 0,
								limit : DEFAULT_PAGE_SIZE
							}
						});
				resultSm.selectAll();
				Ext.getCmp('emresultGrid').doLayout();
//				Ext.Msg.alert('提示','只加载规约类型与下发规约一致的终端');
			}
		});
	}

	function storeToArray(val) {
		var data = new Array();
		for (var i = 0; i < val.getCount(); i++) {
			data[i] = new Array();
			// 供电单位
			data[i][0] = val.getAt(i).data.bureauNo;
			// 用户编号
			data[i][1] = val.getAt(i).data.userNumber;
			// 用户名称
			data[i][2] = val.getAt(i).data.userName;
			// 终端地址
			data[i][3] = val.getAt(i).data.terminalAddr;
			// 数据索引
			data[i][4] = val.getAt(i).data.dataIndex;
			// 通信状态
			data[i][5] = val.getAt(i).data.status;
			// 终端资产编号
			data[i][6] = val.getAt(i).data.tmnlAssetNo;
			// 数据明细
			data[i][7] = val.getAt(i).data.dataDetails
			// 规约编码
			data[i][8] = val.getAt(i).data.protocolCode
		}
		return data;
	}

	// grid解锁
	function unlockGrid() {
		resultSm.unlock();
		resultGrid.onEnable();
		resultGrid.getBottomToolbar().enable();
		Ext.getCmp('emselectAllcb').setValue(false);
	}

	// grid解锁
	function lockGrid() {
		resultSm.lock();
		resultGrid.onDisable();
		resultGrid.getBottomToolbar().disable();
	}
	// 参数下发
	function sendInfoAjax() {
		var recs;
		if (Ext.getCmp('emselectAllcb').checked) {
			recs = storeForPage.getRange(0, storeForPage.getCount());
		} else {
			var selectModel = Ext.getCmp('emresultGrid').getSelectionModel();
			recs = selectModel.getSelections();
		}
		// 下发终端集合
		var termialArray = new Array();
		var protocolCode = Ext.getCmp('emComboBox').value;

		// 构造json数据源对象
		var gridData = new Array();
		// 从title中取FN号
		var fnStr = Ext.getCmp('fnLabel').getValue();
		var patt = new RegExp('\\d+', 'g');
		var fn = null;
		while ((result = patt.exec(fnStr)) != null) {
			fn = result[0];
		}
		for (var i = 0; i < recs.length; i++) {
			if (recs[i].data.protocolCode == protocolCode) {
				termialArray[i] = recs[i].data.tmnlAssetNo;
			} else {
				var protocolName = gyComboBox.getStore()
						.getById(recs[i].data.protocolCode).data.protocolName;
				Ext.Msg.alert('', recs[i].data.tmnlAssetNo + '规约类型（'
								+ protocolName + '）与页面规约不一致');
				return;
			}
		}
		var store = Ext.getCmp('emEditorGrid').getStore();
		var protoValue = new Array();
		protoValue[0] = '';
		protoValue[1] = '';
		for (var i = 0; i < 64; i++) {
			protoValue[0] = '0' + protoValue[0];
			protoValue[1] = '0' + protoValue[1];
		}
		for (var i = 0; i < store.getCount(); i++) {
			var eventNo = store.getAt(i).data.eventNo;
			// 默认为屏蔽
			if (store.getAt(i).data.save) {
				protoValue[0] = protoValue[0].substring(0, 64 - eventNo)
						+ store.getAt(i).data.save
						+ protoValue[0].substring(65 - eventNo, 64);
			}
			if (store.getAt(i).data.level) {
				protoValue[1] = protoValue[1].substring(0, 64 - eventNo)
						+ store.getAt(i).data.level
						+ protoValue[1].substring(65 - eventNo, 64);
			}
		};
		var gridData = new Array();
		gridData[0] = new Array();
		for (var j = 0; j < 2; j++) {
			gridData[0][j] = {};
			gridData[0][j].id = {};
			gridData[0][j].id.protItemNo = Ext.getCmp('protocolCode')
					.getValue()
					+ '00' + (j + 1);
			// gridData[0][j].id.tmnlAssetNo = '';
			gridData[0][j].currentValue = protoValue[j];
			gridData[0][j].saveTime = new Date();
			gridData[0][j].id.blockSn = '0';
			gridData[0][j].id.innerBlockSn = j + 1;
			// gridData[i][j].staffNo = pSysUser.staffNo;
			// gridData[0][j].staffNo = 'test';
		};
		var ov = true;
		h_taskTime(20, function() {
					ov = false;
				});
		Ext.Ajax.request({
			url : './runman/abnormalhandle/eventManage!sendPara.action',
			params : {
				fn : fn,
				tmnlAssetNoArr : termialArray,
				jsonData : Ext.encode(gridData)
			},
			success : function(response) {
				if (!ov) {
					Ext.Msg.alert('提示', '请求超时');
					unlockGrid();
					return;
				}
				var result = Ext.decode(response.responseText);
				if (result.mpflg == 4) {
					Ext.Msg.alert('', "前置集群通信中断")
					return;
				}
				if (result.mpflg == 5) {
					Ext.Msg.alert('', "前置集群服务中断")
					return;
				}
				if (result.tmnlflg == 1) {
					Ext.Msg.alert("请选择终端");
					return;
				}
				var store = Ext.getCmp('emresultGrid').getStore();
				for (var i = 0; i < result.tmnlAssetNoArr.length; i++) {
					var rec = storeForPage.getById(result.tmnlAssetNoArr[i]);
					rec.set('status', result.status[i]);
					rec.commit();
					// getSendIfno(result.tmnlAssetNoArr[i]);
				}
				store.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(storeForPage));
				// 更新下发状态
				if (Ext.getCmp('emselectAllcb').checked) {
					unlockGrid();
					store.load({
								params : {
									start : 0,
									limit : DEFAULT_PAGE_SIZE
								}
							});
				} else {
					for (var i = 0; i < result.tmnlAssetNoArr.length; i++) {
						var rec = store.getById(result.tmnlAssetNoArr[i]);
						rec.set('status', result.status[i]);
						rec.commit();
						// getSendIfno(result.tmnlAssetNoArr[i]);
					}
				}
				overFlat = true;
				// Ext.getCmp('tpresultGrid').doLayout();
			},
			failure : function(response) {
				if (!ov) {
					Ext.Msg.alert('提示', '请求超时');
					unlockGrid();
					return;
				}
				Ext.MessageBox.alert('提示', '请求超时或失败！');
				overFlat = true;
				var action = Ext.decode(response.responseText);
				if (action.message && action.message != "") {
					return Ext.Msg.alert(message);
				}
				return;
			}
		});
	}

	function getCallParaAjax() {
		var recs;
		if (Ext.getCmp('emselectAllcb').checked) {
			recs = storeForPage.getRange(0, storeForPage.getCount());
		} else {
			var selectModel = Ext.getCmp('emresultGrid').getSelectionModel();
			recs = selectModel.getSelections();
		}
		// 页面参数规约类型
		var protocolCode = Ext.getCmp('emComboBox').value;
		// 召测终端集合
		var termialArray = new Array();
		for (var i = 0; i < recs.length; i++) {
			if (recs[i].data.protocolCode == protocolCode) {
				termialArray[i] = recs[i].data.tmnlAssetNo;
			} else {
				var protocolName = Ext.getCmp('emComboBox').getStore()
						.getById(recs[i].data.protocolCode).data.protocolName;
				Ext.Msg.alert('', recs[i].data.tmnlAssetNo + '规约类型（'
								+ protocolName + '）与页面规约不一致');
				return;
			}
		}
		if (termialArray.length == 0) {
			Ext.Msg.alert('', "请选择终端");
			return;
		}
		// 从title中取FN号
		var fnStr = Ext.getCmp('fnLabel').getValue();
		var patt = new RegExp('\\d+', 'g');
		var fn = null;
		while ((result = patt.exec(fnStr)) != null) {
			fn = result[0];
		}
		var ov = true;
		h_taskTime(20, function() {
					ov = false;
				});
		Ext.Ajax.request({
			url : './runman/abnormalhandle/eventManage!fetchTerminalPara.action',
			params : {
				// 获取当前table页的ID
				fn : fn,
				tmnlAssetNoArr : termialArray
			},
			success : function(response) {
				if (!ov) {
					Ext.Msg.alert('提示', '请求超时');
					unlockGrid();
					return;
				}
				var result = Ext.decode(response.responseText);
				if (result.mpflg == 4) {
					Ext.Msg.alert('', "前置集群通信中断")
					return;
				}
				if (result.mpflg == 5) {
					Ext.Msg.alert('', "前置集群服务中断")
					return;
				}
				if (result.tmnlflg == 1) {
					Ext.Msg.alert("请选择终端");
					return;
				}
				var stroe = Ext.getCmp('emresultGrid').getStore();

				var store = Ext.getCmp('emresultGrid').getStore();
				for (var i = 0; i < result.tmnlAssetNoArr.length; i++) {
					var rec = storeForPage.getById(result.tmnlAssetNoArr[i]);
					rec.set('status', result.status[i]);
					rec.set('dataDetails', result.fiBeanList[i]);
					rec.commit();
				}
				store.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(storeForPage));
				// 更新下发状态
				if (Ext.getCmp('emselectAllcb').checked) {
					unlockGrid();
					store.load({
								params : {
									start : 0,
									limit : DEFAULT_PAGE_SIZE
								}
							});
				} else {
					for (var i = 0; i < result.fiBeanList.length; i++) {
						var rec = store.getById(result.tmnlAssetNoArr[i]);
						rec.set('status', result.status[i]);
						// var detailData = new Array();
						// detailData[0] = rec.get('dataDetails')[0];
						// detailData[1] = result.fiBeanList[i];
						rec.set('dataDetails', result.fiBeanList[i]);
						rec.commit();
					}
				}
				overFlat = true;
			},
			failure : function(response) {
				if (!ov) {
					Ext.Msg.alert('提示', '请求超时');
					unlockGrid();
					return;
				}
				Ext.MessageBox.alert('提示', '请求超时或失败！');
				overFlat = true;
				var action = Ext.decode(response.responseText);
				if (action.message && action.message != "") {
					return Ext.Msg.alert(message);
				}
				return;
			}
		});
	}

	function loadGridData() {
		Ext.Ajax.request({
					url : './runman/abnormalhandle/eventManage!queryEventInfo.action',
					params : {
						protocolCode : Ext.getCmp('emComboBox').value
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						var gridData = new Array();
						for (var i = 0; i < result.pe.length; i++) {
							gridData[i] = new Array();
							var eventNo = result.pe[i].eventNo;
							var recNo = parseInt(eventNo.substring(3, 5), 16);
							gridData[i][0] = 'ERC' + recNo;
							gridData[i][1] = result.pe[i].eventName;
							gridData[i][2] = '1';
							if (result.pe[i].eventLevel == '03') {
								gridData[i][3] = '1';
							} else {
								gridData[i][3] = '0';
							}
							gridData[i][4] = recNo;
						}
						var editorGrid = Ext.getCmp('emEditorGrid');
						editorGrid.getStore().loadData(gridData);
						editorGrid.doLayout();
					}
				});
	}

	var itempanel = new Ext.Panel({
				// title : '终端事件记录配置设置',
				id : 'pfemEditorGrid',
				// autoScroll : true,
				layout : 'fit',
				region : 'center',
				border : true,
				buttonAlign : 'center',
				tbar : [{
							xtype : 'label',
							id : 'titlelabel',
							text : '终端事件记录配置'
						}, {
							xtype : 'textfield',
							id : 'protocolCode',
							hidden : true
						}, {
							xtype : 'textfield',
							id : 'fnLabel',
							hidden : true
						}],
				buttons : [{
							width : '100',
							text : '下发',
							handler : sendInfoAjax
						}, {
							width : '100',
							text : '召测',
							handler : getCallParaAjax
						}],
				items : [userGrid]
			});
	/**
	 * 终端列表gridpanel相关
	 */
	// 终端列表数据项
	var resultStore = new Ext.data.ArrayStore({
				idIndex : 6,
				fields : [{
							name : 'bureauNo'
						}, {
							name : 'userNumber'
						}, {
							name : 'userName'
						}, {
							name : 'terminalAddr'
						}, {
							name : 'dataIndex'
						}, {
							name : 'status'
						}, {
							name : 'tmnlAssetNo'
						}, {
							name : 'dataDetails'
						}, {
							name : 'protocolCode'
						}, {
							name : 'protocolData'
						}]
			});
	var resultSm = new Ext.grid.CheckboxSelectionModel();

	// 终端列表columnModel
	var resultCm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
				// header : '序',
				renderer : function(v, p, record, rowIndex) {
					var startRow = 0;
					if (resultStore && resultStore.lastOptions
							&& resultStore.lastOptions.params) {
						startRow = resultStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			}), resultSm, {
		header : '供电单位',
		width : 70,
		dataIndex : 'bureauNo',
		align : 'center',
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">' + val
					+ '</div>';
			return html;
		}
	}, {
		header : '用户编号',
		dataIndex : 'userNumber',
		width : 45,
		align : 'center',
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="用户编号" ext:qtip="' + val + '">' + val
					+ '</div>';
			return html;
		}
	}, {
		header : '用户名称',
		dataIndex : 'userName',
		width : 50,
		align : 'center',
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">' + val
					+ '</div>';
			return html;
		}
	}, {
		header : '终端地址',
		width : 45,
		dataIndex : 'terminalAddr',
		align : 'center'
	}, {
		header : '数据明细',
		dataIndex : 'dataIndex',
		width : 40,
		align : 'center',
		renderer : function(val) {
			return "<a href='javascript:' onclick='showDataDetail(\"" + val
					+ "\");'>数据明细</a>";
		}
	}, {
		header : '状态',
		dataIndex : 'status',
		width : 55,
		renderer : function(val) {
			switch (val) {
				case '31' :
				return "<font color='green';font-weight:bold>" + '召测成功'
							+ "</font>";
				case '32' :
				return "<font color='red';font-weight:bold>" + '召测失败'
							+ "</font>";
				case '04' :
				return "<font color='green';font-weight:bold>" + '下发成功'
							+ "</font>";
				case '05' :
				return "<font color='red';font-weight:bold>" + '下发失败'
							+ "</font>";
				case '42' :
				return "<font color='red';font-weight:bold>" + '终端不在线'
							+ "</font>";
				case '43' :
				return "<font color='red';font-weight:bold>" + '终端响应超时'
							+ "</font>";
				case '44' :
				return "<font color='red';font-weight:bold>" + '终端无应答'
							+ "</font>";
				// default :
				// return '等待状态';
			}
		},
		align : 'center'
	}, {
		header : '原始报文',
		dataIndex : 'protocolData',
		width : 40,
		align : 'center',
		renderer : function(s, m, rec) {
			return "<a href='javascript:'onclick='origFrameQryShow(\""
					+ rec.get('userNumber') + "\",\"" + rec.get('userName')
					+ "\",\"" + rec.get('terminalAddr') + "\");" + "'>原始报文</a>";
		}
	}]);

	this.showDataDetail = function(val) {
		if (val) {
			var rec = Ext.getCmp('emresultGrid').getStore().getById(val);
			var detailData = rec.get('dataDetails');
			var store = Ext.getCmp('emEditorGrid').getStore();
			if (detailData.length > 0) {
				for (var i = 0; i < store.getCount(); i++) {
					var commitRec = store.getAt(i);
					var eventNo = commitRec.data.eventNo;
					commitRec.set('save', detailData[0].currentValue.substring(
									64 - eventNo, 65 - eventNo));
					commitRec.set('level', detailData[1].currentValue
									.substring(64 - eventNo, 65 - eventNo));
					commitRec.commit();
				}
			}

		}
	};
	// 创建终端列表gridPanel
	var resultGrid = new Ext.grid.GridPanel({
		// title : '终端列表',
		id : 'emresultGrid',
		store : resultStore,
		cm : resultCm,
		sm : resultSm,
		region : 'center',
		tbar : [{
					xtype : 'label',
					id : 'emTtLab',
					html : "<font font-weight:bold;>备选用户</font>"
				}, {
					xtype : 'tbfill'
				}, '-', {
					xtype : 'checkbox',
					id : 'emselectAllcb',
					boxLabel : '全选',
					name : 'emselectAllcb',
					checked : false,
					listeners : {
						'check' : function(r, c) {
							if (c) {
								resultSm.selectAll();
								lockGrid();
							} else {
								unlockGrid();
								resultSm.clearSelections()
							}
						}
					}

				}, '-', {
					icon : '',
					text : '删除选中用户',
					iconCls : 'minus',
					handler : function() {
						if (Ext.getCmp('emselectAllcb').checked) {
							storeForPage.removeAll(true);
							unlockGrid();
							Ext.getCmp('emselectAllcb').setValue(false);
						} else {
							var recs = resultSm.getSelections();
							for (var i = 0; i < recs.length; i = i + 1) {
								storeForPage.remove(storeForPage
										.getById(recs[i].data.tmnlAssetNo));
							}
						}
						var rsStore = Ext.getCmp('emresultGrid').getStore();
						rsStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(storeForPage));
						rsStore.load({
									params : {
										start : 0,
										limit : DEFAULT_PAGE_SIZE
									}
								});
					}
				}, '-', {
					icon : '',
					text : '删除成功用户',
					iconCls : 'minus',
					handler : function() {
						for (var i = 0; i < storeForPage.getCount(); i++) {
							var statuscode = storeForPage.getAt(i).data.status;
							if (statuscode == '04' || statuscode == '31') {
								storeForPage.remove(storeForPage.getAt(i));
							}
						}
						var rsStore = Ext.getCmp('emresultGrid').getStore();
						rsStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(storeForPage));
						rsStore.load({
									params : {
										start : 0,
										limit : DEFAULT_PAGE_SIZE
									}
								});
					}
				}],
		bbar : new Ext.ux.MyToolbar({
					store : resultStore
				})
	});

	// ComboBox和RadioGroup面板
	var formPanel = new Ext.Panel({
				border : false,
				id : 'emFormPanel',
				region : 'north',
				layout : 'border',
				split : true,
				height : 280,
				items : [{
							xtype : 'panel',
							region : 'north',
							height : 40,
							border : false,
							items : [{
										layout : 'form',
										labelAlign : 'right',
										bodyStyle : 'padding:10px 10px 10px 10px',
										width : 180,
										border : false,
										labelWidth : 30,
										items : [gyComboBox]
									}]
						}, itempanel]

			});

	var panel = new Ext.form.FormPanel({
				// title : '终端参数',
				border : false,
				id : 'emPanel',
				layout : 'border',
				items : [formPanel, resultGrid]
			});
	renderModel(panel, '事件屏蔽与分级')
})