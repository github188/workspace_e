/**
 * 线路线损统计
 * 
 * @author jiangweichao 
 */

// 页面执行函数
Ext.onReady(function() {
	var lineLps_nodeId;
	var lineLps_nodeType;
	var orgType;
	var startD;//查询明细使用
	var endD;//查询明细使用

	function checkInitData() {
		if (Ext.isEmpty(lineLps_nodeId)) {
			Ext.MessageBox.alert("提示", "请从左边树选择节点！");
			return false;
		}
		var start = lineLps_startDate.getValue();
		var end = lineLps_endDate.getValue();
		var startFm = lineLps_startDate.getRawValue();
		var endFm = lineLps_endDate.getRawValue();
		var rg = lineLps_RadioGroup.getValue().getRawValue();
		if ((start - end) > 0) {
			Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
			return false;
		}
		if(2 == rg){
			var syear = startFm.substring(0, 4);
			var eyear = endFm.substring(0, 4);
			var sseason = getSeason(startFm.substring(5, 7));
			var eseason = getSeason(endFm.substring(5, 7));
			var info = '您查询是'+syear+'第'+sseason+'季度至'+eyear+'第'+eseason+'季度的统计信息';
			Ext.MessageBox.show({
                title: '提示',
   				msg: info,
   				width:300,
   				buttons: Ext.MessageBox.OK,
   				fn: function(e){
   					if("ok" == e){
   					    return true;
   					}
   				}
		    });
		}
		return true;
	}

	//返回季度
	function getSeason(month){
		if("01" == month || "02" == month || "03" == month){
			return 1;
		}else if("04" == month || "05" == month || "06" == month){
			return 2;
		}else if("07" == month || "08" == month || "09" == month){
			return 3;
		}else if("10" == month || "11" == month || "12" == month){
			return 4;
		}else{
			return 1;
		}
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

	// load第二次报错问题
	Ext.override(Ext.data.Store, {
				loadRecords : function(o, options, success) {
					if (!o || success === false) {
						if (success !== false) {
							this.fireEvent("load", this, [], options);
						}
						if (options.callback) {
							options.callback.call(options.scope || this, [],
									options, false);
						}
						return;
					}
					var r = o.records, t = o.totalRecords || r.length;
					if (!options || options.add !== true) {
						if (this.pruneModifiedRecords) {
							this.modified = [];
						}
						for (var i = 0, len = r.length; i < len; i++) {
							r[i].join(this);
						}
						if (this.snapshot) {
							this.data = this.snapshot;
							delete this.snapshot;
						}
						this.data.clear();
						this.data.addAll(r);
						this.totalLength = t;
						this.applySort();
						this.fireEvent("datachanged", this);
					} else {
						var add = [];
						for (var i = 0, len = r.length; i < len; i++) {
							var record = r[i];
							if (this.getById(record.id)) {
								this.data.replace(record.id, record);
								this.fireEvent("update", this, record,
										Ext.data.Record.EDIT);
							} else {
								add.push(record);
							}
						}
						this.totalLength = Math.max(t, this.data.length
										+ add.length);
						this.add(add);
					}
					this.fireEvent("load", this, r, options);
					if (options.callback) {
						options.callback.call(options.scope || this, r,
								options, true);
					}
				}
			});

	// 用电损耗分类统计---------------------------------------------------
	// 定义电损的上部panel
	// 节点名
	var lineLps_nodeName = new Ext.form.TextField({
				fieldLabel : "节点名<font color='red'>*</font>",
				name : 'lineLps_nodeName',
				id : 'lineLps_nodeName',
				labelSeparator : '',
				emptyText : '请选择统计节点...',
				readOnly : true,
				labelStyle : "text-align:right;width:50;",
				width : 140,
				tooltip : {
					title : "统计节点",
					text : ''
				},
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

	// 开始日期
	var lineLps_startDate = new Ext.form.DateField({
				fieldLabel : '从',
				name : 'lineLps_startDate',
				width : 90,
				format : 'Y-m',
				allowBlank : false,
				editable : false,
				labelSeparator : '',
				value : new Date()
			});

	// 结束日期
	var lineLps_endDate = new Ext.form.DateField({
				fieldLabel : '到',
				name : 'lineLps_endDate',
				width : 90,
				format : 'Y-m',
				labelSeparator : '',
				editable : false,
				allowBlank : false,
				value : new Date()
			});
	
	// 定义radio选择组
	var lineLps_RadioGroup = new Ext.form.RadioGroup({
		width : 120,
		height : 20,
		id : 'lineLps_RadioGroup',
		items : [new Ext.form.Radio({
					boxLabel : '月',
					name : 'line-radioGroup',
					inputValue : '1',
					checked : true,
					listeners : {
						'check' : function(r, c) {
							if (c) {
								lineLps_startDate.format = 'Y-m';
								lineLps_endDate.format = 'Y-m';
								lineLps_startDate.setValue(new Date());
								lineLps_endDate.setValue(new Date());
							};
						}
					}
				}), new Ext.form.Radio({
					boxLabel : '季',
					name : 'line-radioGroup',
					inputValue : '2',
					listeners : {
						'check' : function(r, c) {
							if (c) {
								lineLps_startDate.format = 'Y-m';
								lineLps_endDate.format = 'Y-m';
								var nowmonth = new Date().getMonth();
								var i = nowmonth % 3;
								lineLps_startDate.setValue(new Date().add(Date.MONTH, -i));
								lineLps_endDate.setValue(new Date().add(Date.MONTH, -i+2));
							};
						}
					}
				}), new Ext.form.Radio({
					boxLabel : '年',
					name : 'line-radioGroup',
					inputValue : '3',
					listeners : {
						'check' : function(r, c) {
							if (c) {
								lineLps_startDate.format = 'Y';
								lineLps_endDate.format = 'Y';
								lineLps_startDate.setValue(new Date());
								lineLps_endDate.setValue(new Date());
							};
						}
					}
				})]
	});
	
	// 查询按钮
	var lineLps_queryBtn = new Ext.Button({
				text : '查询',
				name : 'lineLps_queryBtn',
				width : 80,
				labelSeparator : '',
				handler : function() {
					if (!checkInitData()) {
						return;
					}
					var statFlag;
					if(Ext.isEmpty(lineLps_RadioGroup.getValue().getRawValue())){
						return;
					}else{
						if("1" == lineLps_RadioGroup.getValue().getRawValue()){
							statFlag = "02";
							startD = lineLps_startDate.getRawValue()+'-01';
							endD = lineLps_endDate.getRawValue()+'-01';
						}else if("2" == lineLps_RadioGroup.getValue().getRawValue()){
							statFlag = "03";
							startD = lineLps_startDate.getRawValue()+'-01';
							endD = lineLps_endDate.getRawValue()+'-01';
						}else{
							statFlag = "04";
							startD = lineLps_startDate.getRawValue()+'-01-01';
							endD = lineLps_endDate.getRawValue()+'-01-01';
						}
					}
					lineLpsListStore.removeAll();
					lineLpsListStore.baseParams = {
						nodeId : lineLps_nodeId,
						nodeType : lineLps_nodeType,
						orgType : orgType,
						statFlag : statFlag,
						startDate : lineLps_startDate.getValue(),
						endDate : lineLps_endDate.getValue()
					};
					lineLpsListStore.load();
				}
			});	
	
	var lineLosePowerStatTopPanel = new Ext.Panel({
				region : 'north',
				height : 30,
				border : false,
				layout : 'table',
				layoutConfig : {
					columns : 5
				},
				items : [{
							width : 200,
							layout : 'form',
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							labelAlign : "right",
							labelWidth : 50,
							items : [lineLps_nodeName]
						}, {
							width : 130,
							layout : 'form',
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							labelAlign : "right",
							labelWidth : 1,
							items : [lineLps_RadioGroup]
						}, {
							width : 150,
							layout : 'form',
							labelWidth : 20,
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							items : [lineLps_startDate]
						}, {
							width : 150,
							layout : 'form',
							labelWidth : 20,
							border : false,
							bodyStyle : 'padding:5px 0px 0px 0px',
							items : [lineLps_endDate]
						}, {
							width : 150,
							border : false,
							layout : "form",
							bodyStyle : 'padding:0px 0px 0px 0px',
							items : [lineLps_queryBtn]
						}]
			});
	
	// -----------------------------定义grid列表-------------------------start
	// 为了便于页面操作，这里抽取出来，不放入fuction中 ,json支持[{"":,"":},{}] 还支持格式 [[,,,,],[]];
	var lineLpsCm = new Ext.grid.ColumnModel([{
		header : '管理单位',
		sortable : true,
		dataIndex : 'orgName',
		width : 140,
		align : 'center',
		renderer : function(val) {
			if (Ext.isEmpty(val)) {
				val = "";
			}
			var html = '<div align = "left" ext:qtitle="管理单位" ext:qtip="' + val
					+ '">' + val + '</div>';
			return html;
		}
	}, {
		header : '台区名称',
		sortable : true,
		dataIndex : 'lineName',
		align : 'center',
		renderer : function(val) {
			if (Ext.isEmpty(val)) {
				val = "";
			}
			var html = '<div align = "left" ext:qtitle="台区名称" ext:qtip="' + val
					+ '">' + val + '</div>';
			return html;
		}
	}, {
		header : '线路名称',
		sortable : true,
		dataIndex : 'lineName',
		align : 'center',
		renderer : function(val) {
			if (Ext.isEmpty(val)) {
				val = "";
			}
			var html = '<div align = "left" ext:qtitle="线路名称" ext:qtip="' + val
					+ '">' + val + '</div>';
			return html;
		}
	}, {
		header : '统计时间',
		sortable : true,
		dataIndex : 'date',
		align : 'center'
	}, {
		header : '供电量(kWh)',
		sortable : true,
		dataIndex : 'readPpq',
		align : 'center',
		width : 120,
		renderer : function(val) {
			if (Ext.isEmpty(val)) {
				val = "";
			}
			var html = '<div align = "left">' + val
	            + '</div>';
	        return html;
	    }
	}, {
		header : '售电量(kWh)',
		sortable : true,
		dataIndex : 'settleSpq',
		width : 120,
		align : 'center',
		renderer : function(val) {
			if (Ext.isEmpty(val)) {
				val = "";
			}
			var html = '<div align = "left">' + val
	            + '</div>';
	        return html;
	    }
	}, {
		header : '合计电量(kWh)',
		sortable : true,
		dataIndex : 'supplypq',
		align : 'center',
		renderer : function(val) {
			if (Ext.isEmpty(val)) {
				val = "";
			}
			var html = '<div align = "right">' + val + '</div>';
			return html;
		}
	}, {
		header : '损失电量(kWh)',
		sortable : true,
		dataIndex : 'lpq',
		align : 'center',
		renderer : function(val) {
			if (Ext.isEmpty(val)) {
				val = "";
			}
			var html = '<div align = "right">' + val + '</div>';
			return html;
		}
	}, {
		header : '线损率(%)',
		sortable : true,
		dataIndex : 'llr',
		align : 'center',
		renderer : function(val) {
			if (Ext.isEmpty(val)) {
				val = "";
			}
			var html = '<div align = "right">' + val + '</div>';
			return html;
		}
	}, {
		header : '线损指标(%)',
		sortable : true,
		dataIndex : 'idx',
		align : 'center',
		renderer : function(val) {
			if (Ext.isEmpty(val)) {
				val = "";
			}
			var html = '<div align = "right">' + val + '</div>';
			return html;
		}
	}, {
		header : '操作链接',
		sortable : true,
		dataIndex : 'operLink',
		align : 'center',
		renderer : function(s, m, rec) {
			return "<a href='javascript:' onclick='openLpsDetail(\""
					+ rec.get('lineId') + "\",\""
					+ rec.get('lineName') + "\",\""
					+ startD + "\",\""
					+ endD + "\");'>" + '线损明细'
					+ "</a>";
		}
	}]);

	// 定义Grid的store
	var lineLpsListStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './qrystat/lineLosePowerStat!loadGridData.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'linelpsList',
							totalProperty : 'totalCount'
						}, [{
									name : 'orgName'
								}, {
									name : 'lineNo'
								}, {
									name : 'lineName'
								}, {
									name : 'lineId'
								}, {
									name : 'supplypq'
								}, {
									name : 'lpq'
								}, {
									name : 'llr'
								}, {
									name : 'idx'
								}, {
									name : 'date'
								}, {
									name : 'readPpq'
								}, {
									name : 'settleSpq'
								}, {
									name : 'lineName'
								}])
			});

	// 返回用户列表
	var lineLpsListGrid = new Ext.grid.GridPanel({
				store : lineLpsListStore,
				itemId : "grid",
				cm : lineLpsCm,
				stripeRows : true,
				autoScroll : true,
				border : false,
				bbar : new Ext.ux.MyToolbar({
							enableExpAll : true,
							store : lineLpsListStore
						}),
				tbar : [{
							xtype : 'label',
							html : "<font font-weight:bold;>线损统计明细</font>"
						}]
			});

	// 定义用户列表
	var lineLpsListGridPnl = new Ext.Panel({
				region : 'center',
				layout : 'fit',
				border : false,
				itemId : "centerGrid",
				items : [lineLpsListGrid]
			});
	// -----------------------------定义grid列表-------------------------end

	// 定义整个页面面板
	var viewPanel = new Ext.form.FormPanel({
				layout : 'border',
				border : false,
				items : [lineLosePowerStatTopPanel, lineLpsListGridPnl]
			});

	renderModel(viewPanel, '线路线损统计');

	// 监听左边树点击事件
	var treeListener = new LeftTreeListener({
				modelName : '线路线损统计',
				processClick : function(p, node, e) {
					var obj = node.attributes.attributes;
					var type = node.attributes.type;
					if (!node.isLeaf()) {
						if (type == 'org') {
							if ("02" == obj.orgType) {
								Ext.MessageBox.alert("提示", "不支持省级单位查询！");
								return false;
							}
							lineLps_nodeId = obj.orgNo;
							lineLps_nodeType = type;
							orgType = obj.orgType;
							lineLps_nodeName.setValue(node.text);
						} else if (type == 'line') {
							lineLps_nodeId = obj.lineId;
							lineLps_nodeType = type;
							lineLps_nodeName.setValue(node.text);
						} else if (type == 'sub') {
							lineLps_nodeId = obj.subsId;
							lineLps_nodeType = type;
							lineLps_nodeName.setValue(node.text);
						} else {
							return true;
						}
					} else {
						return true;
					}
				}
			});
});

//进入查询详细页面
function openLpsDetail(lineId,lineName,startD,endD){
	lineGosePowerStat_Flag = true;
	lineGosePowerStat_lineId = lineId;
	lineGosePowerStat_lineName = lineName;
	lineGosePowerStat_startD = startD;
	lineGosePowerStat_endD = endD;
	openTab("线路线损查询", "./qryStat/collDataAnalyse/lineLosePowerQry.jsp",false,"lineLosePowerQry");
}