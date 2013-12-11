/**
 * 线路电量查询
 * 
 * @author chunmingli
 */

this.isNum = function(str) {
	var re = /^[\d]+$/;
	return re.test(str);
};

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


var psa_nodeId;// 左边树选择编号
var psa_nodeType;// 左边树选择类型
var psa_orgType;
//用电用户窗口
var lineUserPanel;



Ext.onReady(function() {
	
	

	// 节点名
	var lineElec_nodeName = new Ext.form.TextField({
				fieldLabel : "节点名<font color='red'>*</font>",
				name : 'lineElec_nodeName',
				id : 'lineElec_nodeName',
				labelSeparator : '',
				readOnly : true,
				labelStyle : "text-align:right;",
				emptyText : '请选择统计节点...',
				tooltip : {
					title : "统计节点",
					text : ''
				},
				width : 140,
				setValue : function(v) {
					this.value = v;
					if (this.rendered) {
						this.el.dom.value = (Ext.isEmpty(v) ? '' : v);
						this.validate();
					}
					new Ext.ToolTip({
								target : this.id,
								trackMouse : false,
								draggable : true,
								maxWidth : 200,
								minWidth : 100,
								title : this.tooltip.title,
								html : this.value,
								text : this.value
							});
					return this;
				}
			});
	// 查询条件值
	var lineTextValue = ({
		nodeType : '',
		nodeValue : ''
	});	

	// 开始日期
	var lineElec_startDate = new Ext.form.DateField({
				id : 'lineElec_startDate',
				fieldLabel : '从',
				name : 'lineElec_startDate',
				labelStyle : "text-align:right;",
				format : 'Y-m-d',
				editable : false,
				labelSeparator : '',
				value : new Date().add(Date.DAY, -7),
				allowBlank : false,
				width : 100
			});

	// 结束日期
	var lineElec_endDate = new Ext.form.DateField({
				id : 'lineElec_endDate',
				fieldLabel : '到',
				name : 'lineElec_endDate',
				labelStyle : "text-align:right;",
				format : 'Y-m-d',
				labelSeparator : '',
				editable : false,
				value : new Date().add(Date.DAY, -1),
				allowBlank : false,
				width : 100
			});


	// 查询按钮
	var lineElec_queryBtn = new Ext.Button({
				text : '查询',
				name : 'lineElec_queryBtn',
				width : 60,
				labelSeparator : '',
				handler : function() {
					if (!checkQueryData()) {
						return;
					}
					// 加载grid数据
					line_gridStore.removeAll();
					line_gridStore.baseParams = {
						startDate : lineElec_startDate.getValue(),
						endDate : lineElec_endDate.getValue(),
						// 工单状态
						nodeType : lineTextValue.nodeType,
						// 异常类型
						nodeValue : lineTextValue.nodeValue
					};
					line_gridStore.load();

				}
			});

	// 校验查询条件
	function checkQueryData() {
		if (Ext.isEmpty(lineElec_nodeName.getValue())) {
			Ext.MessageBox.alert("提示", "请从左边树选择节点！");
			return false;
		}
		var start = lineElec_startDate.getValue();
		var end = lineElec_endDate.getValue();
		if (Ext.isEmpty(start)) {
			Ext.MessageBox.alert("提示", "请输入开始日期！");
			return false;
		}
		if (Ext.isEmpty(end)) {
			Ext.MessageBox.alert("提示", "请输入结束日期！");
			return false;
		}
		if ((start - end) > 0) {
			Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
			return false;
		}
		return true;
	}

	// 求两个时间的天数差 日期格式为 YYYY-MM-dd
	function daysBetween(DateOne, DateTwo) {
		var OneMonth = DateOne.substring(5, DateOne.lastIndexOf('-'));
		var OneDay = DateOne.substring(DateOne.length, DateOne.lastIndexOf('-')
						+ 1);
		var OneYear = DateOne.substring(0, DateOne.indexOf('-'));
		var TwoMonth = DateTwo.substring(5, DateTwo.lastIndexOf('-'));
		var TwoDay = DateTwo.substring(DateTwo.length, DateTwo.lastIndexOf('-')
						+ 1);
		var TwoYear = DateTwo.substring(0, DateTwo.indexOf('-'));
		var cha = ((Date.parse(OneMonth + '/' + OneDay + '/' + OneYear) - Date
				.parse(TwoMonth + '/' + TwoDay + '/' + TwoYear)) / 86400000);
		return Math.abs(cha);
	}

	// 客户用电排名分析panel1
	var lineEcelqueryTopPnl = new Ext.Panel({
				region : 'north',
				border : false,
				height : 40,
				items : [{
							layout : "column",
							style : "padding:5px",
							border : false,
							items : [{
										columnWidth : .3,
										layout : "form",
										labelWidth : 50,
										border : false,
										items : [lineElec_nodeName]
									}, {
										columnWidth : .2,
										layout : "form",
										labelWidth : 20,
										border : false,
										items : [lineElec_startDate]
									}, {
										columnWidth : .2,
										layout : "form",
										labelWidth : 20,
										border : false,
										items : [lineElec_endDate]
									}, {
										columnWidth : .12,
										layout : "form",
										border : false,
										items : [lineElec_queryBtn]
									}]
						}]
			});

	// 节点名
	var psa_remark = new Ext.form.TextField({
				fieldLabel : "",
				name : 'psa_remark',
				id : 'psa_remark',
				labelSeparator : '',
				readOnly : true,
				labelStyle : "text-align:right;",
				emptyText : '',
				width : 200
			});

	psa_rmkPanel = new Ext.Panel({
				border : false,
				bodyBorder : false,
				layout : 'form',
				region : 'south',
				height : 20,
				items : [psa_remark]
			});

	// 定义Grid的store
	var line_gridStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './qrystat/lineEcelQuery!queryLineEcel.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'lineList',
							totalProperty : 'totalCount'
						}, [{
									name : 'orgNo'
								}, {
									name : 'orgName'
								}, {
									name : 'lineId'
								}, {
									name : 'lineName'
								}, {
									name : 'pap_e'
								}, {
									name : 'pap_e1'
								}, {
									name : 'pap_e2'
								}, {
									name : 'pap_e3'
								}, {
									name : 'pap_e4'
								}, {
									name : 'max_p'
								}, {
									name : 'max_p_time'
//									,
//									type : 'date',
//									dateFormat : 'Y-m-d\\ H:i:s' // 2009-04-03T00:00:00
								}, {
									name : 'min_p'
								}, {
									name : 'min_p_time'
//									,
//									type : 'date',
//									dateFormat : 'Y-m-d\\TH:i:s' // 2009-04-03T00:00:00
								}, {
									name : 'max_q'
								}, {
									name : 'max_q_time'
//									,
//									type : 'date',
//									dateFormat : 'Y-m-d\\TH:i:s' // 2009-04-03T00:00:00
								}, {
									name : 'min_q'
								}, {
									name : 'min_q_time'
//									,
//									type : 'date',
//									dateFormat : 'Y-m-d\\TH:i:s' // 2009-04-03T00:00:00
								}, {
									name : 'statDate'
//									,
//									type : 'date',
//									dateFormat : 'Y-m-d\\TH:i:s' // 2009-04-03T00:00:00
								}])
			});

	var psa_gridCm = new Ext.grid.ColumnModel([{
				header : '供电单位编号',
				dataIndex : 'orgNo',
				sortable : true,
				width : 100,
				align : 'center'
			}, {
				header : '供电单位',
				dataIndex : 'orgName',
				width : 120,
				sortable : true,
				align : 'center',
				renderer : function(val) {
					if (Ext.isEmpty(val)) {
						val = "";
					}
					var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="'
							+ val + '">' + val + '</div>';
					return html;
				}
//				function(s, m, rec) {
//				}
			}, {
				header : '线路名称',
				dataIndex : 'lineName',
				sortable : true,
				width : 160,
				align : 'center',
				renderer : function(val) {
					if (Ext.isEmpty(val)) {
						val = "";
					}
					var html = '<div align = "left" ext:qtitle="线路名称" ext:qtip="'
							+ val + '">' + val + '</div>';
					return html;
				}
			},  {
				header : '时间 ',
				dataIndex : 'statDate',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '正向有功总电能量 ',
				dataIndex : 'pap_e',
				sortable : true,
				align : 'center',
				renderer : ''
			}, {
				header : '正向有功费率尖电能量 ',
				dataIndex : 'pap_e1',
				sortable : true,
				width : 160,
				align : 'center',
				renderer : ''
			}, {
				header : '正向有功费率峰电能量 ',
				dataIndex : 'pap_e2',
				sortable : true,
				width : 160,
				align : 'center',
				renderer : ''
			}, {
				header : '正向有功费率评电能量 ',
				dataIndex : 'pap_e3',
				sortable : true,
				width : 160,
				align : 'center',
				renderer : ''
			}, {
				header : '正向有功费率估电能量 ',
				dataIndex : 'pap_e4',
				sortable : true,
				width : 160,
				align : 'center',
				renderer : ''
			}, {
				header : '最大有功功率',
				dataIndex : 'max_p',
				sortable : true,
				align : 'center'
			}, {
				header : '最大有功功率发生时间',
				dataIndex : 'max_p_time',
				sortable : true,
				align : 'center'
			}, {
				header : '最小有功功率',
				dataIndex : 'min_p',
				sortable : true,
				align : 'center'
			}, {
				header : '最小有功功率发生时间',
				dataIndex : 'min_p_time',
				sortable : true,
				align : 'center'
			}, {
				header : '最大无功功率',
				dataIndex : 'max_q',
				sortable : true,
				align : 'center'
			}, {
				header : '最大无功功率发生时间',
				dataIndex : 'max_q_time',
				sortable : true,
				align : 'center'
			}, {
				header : '最小无功功率',
				dataIndex : 'min_q',
				sortable : true,
				align : 'center'
			}, {
				header : '最小无功功率发生时间',
				dataIndex : 'min_q_time',
				sortable : true,
				align : 'center'
			}]);

	var lineEcelquery_gridPanel = new Ext.grid.GridPanel({
				region : 'center',
				autoScroll : true,
				stripeRows : true,
				viewConfig : {
					forceFit : false
				},
				cm : psa_gridCm,
				store : line_gridStore,
				bbar : new Ext.ux.MyToolbar({
							enableExpAll : true,
							store : line_gridStore
						}),
				tbar : [{
							xtype : 'label',
							html : "<font font-weight:bold;>线路电量列表</font>"
						}]
			});
	lineEcelquery_gridPanel.getSelectionModel().on("rowselect",function(s, m, rec) {
		
							lineUserPanel.show();
//					var store = Ext.getCmp("userGrid").getStore();		
							// 加载grid数据
					lineUserStore.baseParams = {
						startDate : lineElec_startDate.getValue(),
						endDate : lineElec_endDate.getValue(),
						// 线路ID
						lineId : rec.get("lineId")
					};
					lineUserStore.load();
							
							});
	
	//弹出用户明细窗口
//	function getLineUserData() {
		// 定义Grid的store
	var lineUserStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './qrystat/lineEcelQuery!queryLineEcelUser.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'lineUserList',
							totalProperty : 'totalCount'
						}, [{
									name : 'orgNo'
								}, {
									name : 'orgName'
								}, {
									name : 'consNo'
								}, {
									name : 'consName'
								},{
									name : 'tmnlAssetNo'
								},{
									name : 'assetNo'
								},{
									name : 'mpType'
								},{
									name : 'dataSrc'
								},{
									name : 'pap_e'
								}, {
									name : 'pap_e1'
								}, {
									name : 'pap_e2'
								}, {
									name : 'pap_e3'
								}, {
									name : 'pap_e4'
								}, {
									name : 'max_p'
								}, {
									name : 'max_p_time'
								}, {
									name : 'min_p'
								}, {
									name : 'min_p_time'
								}, {
									name : 'max_q'
								}, {
									name : 'max_q_time'
								}, {
									name : 'min_q'
								}, {
									name : 'min_q_time'
								}, {
									name : 'statDate'
								}, {
									name : 'avg_q'
								}, {
									name : 'avg_p'
								}])
			});

	var lineUserCM = new Ext.grid.ColumnModel([{
				header : '供电单位',
				dataIndex : 'orgName',
				sortable : true,
				width : 100,
				align : 'center',
				renderer : function(val) {
					if (Ext.isEmpty(val)) {
						val = "";
					}
					var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="'
							+ val + '">' + val + '</div>';
					return html;
				}
			}, {
				header : '用电用户',
				dataIndex : 'consName',
				width : 100,
				sortable : true,
				align : 'center',
				renderer : function(val) {
					if (Ext.isEmpty(val)) {
						val = "";
					}
					var html = '<div align = "left" ext:qtitle="用电用户" ext:qtip="'
							+ val + '">' + val + '</div>';
					return html;
				}
//				function(s, m, rec) {
//				}
			}, {
				header : '终端资产号',
				dataIndex : 'tmnlAssetNo',
				sortable : true,
				width : 80,
				align : 'center',
				renderer : function(val) {
					if (Ext.isEmpty(val)) {
						val = "";
					}
					var html = '<div align = "left" ext:qtitle="终端资产号" ext:qtip="'
							+ val + '">' + val + '</div>';
					return html;
				}
			}, {
				header : '资产编号',
				dataIndex : 'assetNo',
				sortable : true,
				width : 80,
				align : 'center',
				renderer : function(val) {
					if (Ext.isEmpty(val)) {
						val = "";
					}
					var html = '<div align = "left" ext:qtitle="资产编号" ext:qtip="'
							+ val + '">' + val + '</div>';
					return html;
				}
			}, {
				header : '数据来源',
				dataIndex : 'dataSrc',
				sortable : true,
				width : 60,
				align : 'center',
				renderer : function(val) {
					if (Ext.isEmpty(val)) {
						val = "";
					}
					var html = '<div align = "left" ext:qtitle="数据来源" ext:qtip="'
							+ val + '">' + val + '</div>';
					return html;
				}
			},{
				header : '测量点类型',
				dataIndex : 'mpType',
				sortable : true,
				width : 60,
				align : 'center',
				renderer : function(val) {
					if (Ext.isEmpty(val)) {
						val = "";
					}else if(val == 0){
						val = "终端";
					}else if(val == 1){
						val = "电能表";
					}else if(val == 9){
						val = "其他";
					}
					var html = '<div align = "left" ext:qtitle="测量点类型" ext:qtip="'
							+ val + '">' + val + '</div>';
					return html;
				}
			},{
				header : '时间 ',
				dataIndex : 'statDate',
				sortable : true,
				align : 'center',
				renderer : ''
			},{
				header : '正向有功总电能量 ',
				dataIndex : 'pap_e',
				sortable : true,
				align : 'center',
				renderer : ''
			}, {
				header : '正向有功费率尖电能量 ',
				dataIndex : 'pap_e1',
				sortable : true,
				width : 160,
				align : 'center',
				renderer : ''
			}, {
				header : '正向有功费率峰电能量 ',
				dataIndex : 'pap_e2',
				sortable : true,
				width : 160,
				align : 'center',
				renderer : ''
			}, {
				header : '正向有功费率评电能量 ',
				dataIndex : 'pap_e3',
				sortable : true,
				width : 160,
				align : 'center',
				renderer : ''
			}, {
				header : '正向有功费率估电能量 ',
				dataIndex : 'pap_e4',
				sortable : true,
				width : 160,
				align : 'center',
				renderer : ''
			}, {
				header : '最大有功功率',
				dataIndex : 'max_p',
				sortable : true,
				align : 'center'
			}, {
				header : '最大有功功率发生时间',
				dataIndex : 'max_p_time',
				sortable : true,
				align : 'center'
			}, {
				header : '最小有功功率',
				dataIndex : 'min_p',
				sortable : true,
				align : 'center'
			}, {
				header : '最小有功功率发生时间',
				dataIndex : 'min_p_time',
				sortable : true,
				align : 'center'
			},  {
				header : '平均有功功率',
				dataIndex : 'avg_p',
				sortable : true,
				align : 'center'
			},{
				header : '最大无功功率',
				dataIndex : 'max_q',
				sortable : true,
				align : 'center'
			}, {
				header : '最大无功功率发生时间',
				dataIndex : 'max_q_time',
				sortable : true,
				align : 'center'
			}, {
				header : '最小无功功率',
				dataIndex : 'min_q',
				sortable : true,
				align : 'center'
			}, {
				header : '最小无功功率发生时间',
				dataIndex : 'min_q_time',
				sortable : true,
				align : 'center'
			},{
				header : '平均无功功率',
				dataIndex : 'avg_q',
				sortable : true,
				align : 'center'
			}]);

		// 创建gridPanel
	var userGrid = new Ext.grid.GridPanel({
				id : 'lineUserGrid',
				autoScroll : true,
				stripeRows : true,
				viewConfig : {
					forceFit : false
				},
				cm : lineUserCM,
				store : lineUserStore,
				bbar : new Ext.ux.MyToolbar({
							enableExpAll : true,
							store : lineUserStore
						}),
				tbar : [{
							xtype : 'label',
							html : "<font font-weight:bold;>线路电量用户列表</font>"
						}]
			});
		var itempanel = new Ext.Panel({
					autoScroll : true,
					layout : 'fit',
					border : true,
					items : [userGrid]
				});
//		return itempanel;
//	}
				
	//弹出窗口
	this.lineUserPanel = new Ext.Window({
				title : '线路用电用户',
				width : 750,
				height : 460,
				layout : 'fit',
				closeAction : 'hide',
				resizable : false,
				items : [itempanel]
			});

	var psa_northPanel = new Ext.Panel({
				// title : "线路电量列表",
				border : false,
				autoScroll : true,
				bodyBorder : false,
				region : 'north',
				layout : 'border',
				height : 35,
				items : [lineEcelqueryTopPnl]
			});
	// 线路电量查询panel
	var lineEcelqueryPanel = new Ext.Panel({
				// title : "线路电量列表",
				autoScroll : true,
				border : false,
				layout : 'border',
				items : [lineEcelqueryTopPnl, lineEcelquery_gridPanel]
			});
	renderModel(lineEcelqueryPanel, '线路电量查询');

	// 监听左边树点击事件
	var leftTreeListener = new LeftTreeListener({
		modelName : '线路电量查询',
		processClick : function(p, node, e) {
			var obj = node.attributes.attributes;
			var parentObj = node.parentNode.attributes.attributes;
			var type = node.attributes.type;
			if(type == 'org'){
				if(obj.orgType == '02'){
					return true;
				}
				lineElec_nodeName.setValue(node.text);
				lineTextValue.nodeType = type;
				lineTextValue.nodeValue = obj.orgNo;
				orgType = obj.orgType;
			}else if(type == 'line'){
				lineElec_nodeName.setValue(node.text);
				lineTextValue.nodeType = type;
				lineTextValue.nodeValue = obj.lineId;
			}
//			else if(type == 'usr'){
//				lineElec_nodeName.setValue(obj.consName);
//				lineTextValue.nodeType = type;
//				lineTextValue.nodeValue = obj.tmnlAssetNo;
//			}else if(type == 'cgp' || type=='ugp'){
//				lineElec_nodeName.setValue(node.text);
//				lineTextValue.nodeType = type;
//				lineTextValue.nodeValue = obj.groupNo;
//			}
			else if(type == 'sub'){
				lineElec_nodeName.setValue(node.text);
				lineTextValue.nodeType = type;
				lineTextValue.nodeValue = obj.subsId;
			}
			else {
				return true;
			}
	   	}
//	   	,
//	   	processUserGridSelect:function(cm,row,record){
//	   		lineElec_nodeName.setValue(record.get('consName'));
//	   		lineTextValue.nodeType = 'usr';
//			lineTextValue.nodeValue = record.get('consNo');
//	    }
	});
});
