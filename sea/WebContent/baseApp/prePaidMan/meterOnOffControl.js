
/**
 * @author sungang 2010-10-30 电能表通断控制，只支持四川盘龙晓程规约
 */

// 需要传到后台的全局数据
var consId = '';
var queryTmnlNo = '';
var staticMpsn = '';
var staticCombiId = '';
var staticMeterId = '';
var staticDueStatus = '';
var staticSwitchStatus = '';
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
// ---------------电表通断控制--------------------
function meterOnOffControl(mpsn, combiId, meterId, dueStatus, switchStatus) {
	staticMpsn = mpsn;
	staticCombiId = combiId;
	staticMeterId = meterId;
	staticDueStatus = dueStatus;
	staticSwitchStatus = switchStatus;

	Ext.Msg.confirm('警告', '请在操作前通知用户，您确定继续吗?', function(id) {
				if (id == 'yes') {
					ctrlCheckStaff(sendInfoAjax, '');
				}
			});
}
// 控制命令下发Ajax事件
function sendInfoAjax() {

	// 测量点号，虽然目前只支持一个测量点
	var params = {
		point : []
	};

	// 默认实时召测
	params.queryType = 1;
	// 测量点
	params.point.push(staticMpsn);
	// 透明编码id
	params.combiId = staticCombiId;
	// 表计号
	params.origMeterId = staticMeterId;
	// 表计费控状态
	params.oriDueStatus = staticDueStatus;
	// 表计开关状态
	params.onoffStatus = staticSwitchStatus;
	// 终端资产号
	params.tmnlAssetNos = [];
	params.tmnlAssetNos.push(consId);

	var utils = {
		// 生成一个不相等的字符串
		randomKey : function() {
			// 时间戳级别差异
			var d = new Date().getTime();
			var ran = Math.random(Math.random(55));
			var i = 0;
			// cpu，客户端当前电脑的状况使下面的循环执行时间差异而使第二个
			// 时间戳不同
			while (true) {
				i++;
				if (i > 2000) {
					break;
				}
			}
			var dd = new Date().getTime();
			var ran2 = Math.random(Math.random(55));
			return (d * ran + dd * ran2) * Math.random(new Date());
		}
	};
	params.key = utils.randomKey();

	// 任务条显示
	var taskSecond = Ext.Ajax.timeout / 1000;
	Ext.Ajax.timeout = (taskSecond + 20) * 1000
	var ov = true;
	h_taskTime(taskSecond, function() {
				ov = false;
			});
	params.overTime = taskSecond;
	// 提交ajax请求，参照一类数据召测
	Ext.Ajax.request({
		timeout : 300000,
		url : 'baseapp/dataFetch!meterDataFetch.action',
		params : params,
		success : function(response) {
			if (!ov) {
				Ext.Msg.alert('提示', '请求超时');
				Ext.getCmp('meter_tpresultGrid').getStore().each(function(a) {
							a.set("status", undefined);
						})
				return;
			}
			// 更新下发状态
			var result = Ext.decode(response.responseText);
			var store = Ext.getCmp('meter_tpresultGrid').getStore();
			for (var i = 0; i < result.meterAssetNoArr.length; i++) {
				var rec = meterControlStoreForPage
						.getById(result.meterAssetNoArr[i]);
				rec.set('status', result.status[0]);
				rec.set('switchStatus', result.status[1]);
				rec.commit();
			}
			store.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(meterControlStoreForPage));

			for (var i = 0; i < result.meterAssetNoArr.length; i++) {
				var rec = store.getById(result.meterAssetNoArr[i]);
				rec.set('status', result.status[i]);
				rec.set('switchStatus', result.status[1]);
				rec.commit();
			}

			overFlat = true;
			Ext.Ajax.timeout = 30000;
		},
		failure : function() {
			Ext.Msg.alert("提示", "控制失败！");

		}
	});

}

var meterControlStoreForPage = new Ext.data.ArrayStore({
			idIndex : 8,
			proxy : new Ext.data.MemoryProxy(),
			fields : [{
						name : 'keyId'
					}, {
						name : 'orgName'
					}, {
						name : 'consNo'
					}, {
						name : 'consName'
					}, {
						name : 'terminalAddr'
					}, {
						name : 'tmnlAssetNo'
					}, {
						name : 'meterId'
					}, {
						name : 'commAddr'
					}, {
						name : 'mpSn'
					}, {
						name : 'status'
					}, {
						name : 'protocolCode'
					}, {
						name : 'switchStatus'
					}, {
						name : 'dueStatus'
					}, {
						name : 'dueDate'
					}]
		});
// 将store的数据转化为数组
function storeToArray(val) {
	var data = new Array();
	for (var i = 0; i < val.getCount(); i++) {
		data[i] = new Array();
		data[i][0] = val.getAt(i).data.keyId;
		// 供电单位
		data[i][1] = val.getAt(i).data.orgName;
		// 用户编号
		data[i][2] = val.getAt(i).data.consNo;
		// 用户名称
		data[i][3] = val.getAt(i).data.consName;
		// 终端地址
		data[i][4] = val.getAt(i).data.terminalAddr;
		// 终端地址
		data[i][5] = val.getAt(i).data.tmnlAssetNo;
		// 电能表ID
		data[i][6] = val.getAt(i).data.meterId;
		// 数据明细
		data[i][7] = val.getAt(i).data.commAddr;
		// 通信状态
		data[i][8] = val.getAt(i).data.mpSn;
		// 规约编码
		data[i][9] = val.getAt(i).data.status;
		// 测量点号
		data[i][10] = val.getAt(i).data.protocolCode;
		// 开关状态
		data[i][11] = val.getAt(i).data.switchStatus;
		// 费控状态
		data[i][12] = val.getAt(i).data.dueStatus;
		// 费控时间
		data[i][13] = val.getAt(i).data.dueDate;
	}
	return data;
}

Ext.onReady(function() {

	Ext.QuickTips.init();// 支持tips提示

	var tqNameField = new Ext.form.TextField({
				fieldLabel : '台区名称',
				allowBlank : false,
				labelSeparator : '',
				readOnly : true,
				name : 'tqName',
				emptyText : '请从左边树选择台区用户',
				width : 200,
				border : false
			});
	// ---------------------页面上方的查询条件和按钮-------------
	var overDue_queryPanel = new Ext.Panel({
				border : false,
				region : 'north',
				id : 'overDue_qyPanel',
				height : 70,
				bodyStyle : 'padding:10px 0px 10px 0px',
				layout : 'table',
				layoutConfig : {
					columns : 2,
					tableAttrs : {
						width : "100%"
					}
				},
				items : [{
							layout : 'form',
							border : false,
							labelWidth : 65,
							autoHeight : true,
							labelAlign : 'right',
							width : 300,
							items : [tqNameField]
						}, {
							layout : 'form',
							border : false,
							width : 300,
							bodyStyle : 'padding: 0px 0px 5px 0px;',
							items : [{
										xtype : "button",
										text : '查询',
										id : 'overDue_query',
										handler : function() {
											if (consId != null && consId != '') {
												getOverDueMessage();
											} else {
												Ext.Msg.alert('提示',
														'请从左边树选择要查询的台区用户');
												return true;
											}
										}
									}]
						}, {
							layout : 'form',
							border : false,
							labelWidth : 65,
							autoHeight : true,
							labelAlign : 'right',
							width : 300,
							items : [{
										xtype : 'textfield',
										readOnly : true,
										id : 'prePaidCtrlConsNo',
										fieldLabel : '终端编号',
										anchor : '90%'
									}]
						}, {
							layout : 'form',
							border : false,
							labelWidth : 60,
							autoHeight : true,
							labelAlign : 'right',
							width : 300,
							items : [{
										xtype : 'textfield',
										readOnly : true,
										id : 'prePaidCtrlTmnlAddr',
										fieldLabel : '终端地址',
										anchor : '90%'
									}]
						}]

			});

	// -------------------费控用户grid-----------------------

	var overDue_gridStore = new Ext.data.Store({
				// remoteSort : true,
				proxy : new Ext.data.MemoryProxy(),
				reader : new Ext.data.ArrayReader({
							idIndex : 8,
							fields : [{
										name : 'keyId'
									}, {
										name : 'orgName'
									}, {
										name : 'consNo'
									}, {
										name : 'consName'
									}, {
										name : 'terminalAddr'
									}, {
										name : 'tmnlAssetNo'
									}, {
										name : 'meterId'
									}, {
										name : 'commAddr'
									}, {
										name : 'mpSn'
									}, {
										name : 'status'
									}, {
										name : 'protocolCode'
									}, {
										name : 'switchStatus'
									}, {
										name : 'dueStatus'
									}, {
										name : 'dueDate'
									}, {
										name : 'priPaidCtl'
									}]
						})
			});

	var overDue_gridCm = new Ext.grid.ColumnModel([{
				header : '用户编号',
				dataIndex : 'consNo',
				// width : 40,
				align : 'center'
			}, {
				header : '用户名称',
				dataIndex : 'consName',
				// width : 40,
				align : 'center'
			}, {
				header : '表计编号',
				dataIndex : 'meterId',
				// width : 40,
				align : 'center'
			}, {
				header : '测量点号',
				dataIndex : 'mpSn',
				// width : 40,
				align : 'center'
			}, {
				header : '开关状态',
				dataIndex : 'switchStatus',
				align : 'center',
				// width : 40,
				renderer : function(value) {
					if (value == "OF") {
						return "<font color='red'>拉闸状态</font>";
					} else if (value == "ON") {
						return "<font color='green'>合闸状态</font>";
					}
				}
			}, {
				header : '费控状态',
				dataIndex : 'dueStatus',
				align : 'center',
				// width : 40,
				renderer : function(value) {
					if (value == "QF") {
						return "<font color='red'>欠费</font>";
					} else if (value == "JF") {
						return "<font color='green'>已缴费</font>";
					} else if (value == "ZC") {
						return "正常";
					} else {
						return "未知状态";
					}
				}
			}, {
				header : '费控时间',
				dataIndex : 'dueDate',
				// width : 40,
				align : 'center',
				renderer : function(value) {
					if (value && value != '') {
						return "<font color='red'>" + value + "</font>";
					} else {
						return '';
					}
				}
			}, {
				header : '费控操作',
				dataIndex : 'priPaidCtl',
				// width : 40,
				align : 'center',
				renderer : function(s, m, rec) {
					return "<a href='javascript:'onclick='meterOnOffControl(\""
							+ rec.get('mpSn')
							+ '\",\"'
							+ 283
							+ '\",\"'
							+ rec.get('meterId')
							+ '\",\"'
							+ rec.get('dueStatus')
							+ '\",\"'
							+ rec.get('switchStatus')
							+ "\");"
							+ "'>拉闸</a>"
							+ "&nbsp"
							+ "&nbsp"
							+ "<a href='javascript:'onclick='meterOnOffControl(\""
							+ rec.get('mpSn') + '\",\"' + 284 + '\",\"'
							+ rec.get('meterId') + '\",\"'
							+ rec.get('dueStatus') + '\",\"'
							+ rec.get('switchStatus') + "\");" + "'>合闸</a>"
				}
			}, {
				header : '费控結果',
				dataIndex : 'status',
				align : 'center',
				width : 100,
				renderer : function(val) {
					switch (val) {
						case '11' :
							return "<font color='green';font-weight:bold>"
									+ '拉闸成功,更新开关状态成功' + "</font>";
						case '10' :
							return "<font color='green';font-weight:bold>"
									+ '拉闸成功,' + "</font>"
									+ "<font color='red';font-weight:bold>"
									+ '更新开关状态失败' + "</font>";
						case '-1' :
							return "<font color='red';font-weight:bold>"
									+ '无响应数据' + "</font>";
						case '-27' :
							return "<font color='red';font-weight:bold>" + '失败'
									+ "</font>";
						case '21' :
							return "<font color='green';font-weight:bold>"
									+ '合闸成功,更新开关状态成功' + "</font>";
						case '20' :
							return "<font color='green';font-weight:bold>"
									+ '合闸成功,' + "</font>"
									+ "<font color='red';font-weight:bold>"
									+ '更新开关状态失败' + "</font>";
						case '3' :
							return "<font color='red';font-weight:bold>"
									+ '终端无响应' + "</font>";
						default :
							return val;
					}
				}
			}]);

	var overDue_recordGrid = new Ext.grid.GridPanel({
				id : 'meter_tpresultGrid',
				border : false,
				region : 'center',
				autoScroll : true,
				autoWidth : true,
				stripeRows : true,
				viewConfig : {
					forceFit : false
				},
				cm : overDue_gridCm,
				ds : overDue_gridStore,
				tbar : [{
							xtype : 'label',
							id : 'meter_tpTtLab',
							html : "<font font-weight:bold;>用户费控列表</font>"
						}],
				bbar : new Ext.ux.MyToolbar({
							store : overDue_gridStore,
							enableExpAll : true,
							enableExpPage : true,
							allStore : meterControlStoreForPage

						})
			});
	// --------------监听左边树点击事件-----------------------
	var overDue_treels = new LeftTreeListener({
				modelName : '电能表通断控制',
				processClick : function(p, node, e) {
					var obj = node.attributes.attributes;
					var type = node.attributes.type;
					if (node.isLeaf() && obj.consType == '2') {
						if (queryTmnlNo == obj.tmnlAssetNo) {
							return true;
						}
						overDue_gridStore.removeAll();
						tqNameField.setValue(obj.consName);
						consId = obj.consId;
						queryTmnlNo = obj.tmnlAssetNo;
						Ext.getCmp('prePaidCtrlTmnlAddr')
								.setValue(obj.terminalAddr);
						Ext.getCmp('prePaidCtrlConsNo').setValue(obj.consNo);
					} else {
						return true;
					}
				},
				processUserGridSelect : function(cm, row, record) {
					if (tqNameField.getValue() == record.get('consName')) {
						return true;
					}
					tqNameField.setValue(record.get('consName'));
					overDue_gridStore.removeAll();
					consId = record.get('consId');
					queryTmnlNo = record.get('tmnlAssetNo');
				}
			});

	// 查询欠费用户信息

	function getOverDueMessage() {

		Ext.Ajax.request({
			url : './baseapp/meterControl!queryMeterList.action',
			params : {
				nodeType : 'user',
				nodeValue : queryTmnlNo,
				protocolCode : 'C'
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				if (!result.meterList) {
					return;
				}
				var data = new Array();
				if (result.meterList.length == 0) {
					Ext
							.getCmp('meter_tpTtLab')
							.setText(
									"<font font-weight:bold;>用户费控列表</font><font color='red'>【该台区费控用户数目为0】</font>",
									false);
					return;
				}
				Ext.getCmp('meter_tpTtLab').setText(
						"<font font-weight:bold;>用户费控列表</font>", false);
				for (var i = 0; i < result.meterList.length; i++) {
					data[i] = new Array();
					data[i][0] = result.meterList[i].keyId;
					// 供电单位
					data[i][1] = result.meterList[i].orgName;
					// 用户编号
					data[i][2] = result.meterList[i].consNO;
					// 用户名称
					data[i][3] = result.meterList[i].consName;
					// 终端地址
					data[i][4] = result.meterList[i].terminalAddr;
					// 数据索引
					data[i][5] = result.meterList[i].tmnlAssetNo;
					// 终端资产编号
					data[i][6] = result.meterList[i].meterId;
					// 电表地址
					data[i][7] = result.meterList[i].commAddr;
					// 测量点号
					data[i][8] = result.meterList[i].mpSn;
					// 规约号
					data[i][10] = result.meterList[i].protocolCode;
					// 开关状态
					data[i][11] = result.meterList[i].switchStatus;
					// 费控状态
					data[i][12] = result.meterList[i].dueStatus;
					// 费控时间
					data[i][13] = result.meterList[i].dueDate;

				}
				meterControlStoreForPage.removeAll();
				meterControlStoreForPage.loadData(data, true);
				var rsStore = Ext.getCmp('meter_tpresultGrid').getStore();
				rsStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(meterControlStoreForPage));
				rsStore.load({
							params : {
								start : 0,
								limit : DEFAULT_PAGE_SIZE
							}
						});
			}
		});
	}
	// -------------------- 电能表通断控制面板渲染--------------------
	var overDue_Panel = new Ext.Panel({
				bodyStyle : 'padding:5px 5px 5px 5px',
				layout : 'border',
				items : [overDue_queryPanel, overDue_recordGrid],
				border : false
			});
	renderModel(overDue_Panel, '电能表通断控制');

});