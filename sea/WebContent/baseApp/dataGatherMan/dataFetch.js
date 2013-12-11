/**
 * ***************************************** 初始化数据
 * ******************************************
 */
/** ************** */

/* 弹出原始报文窗口 */
function origFrameQryShow(consNo, consName, tmnlAssetAddr, protocolCode) {
	staticConsNo = consNo;
	staticConsName = consName;
	protocolCode = protocolCode;
	// alert(protocolCode);
	staticTmnlAssetAddr = tmnlAssetAddr;
	tmnlInstllDet_Flag = true;
	tmnlInstllDet_ConsNo = consNo;
	origFrameQryID = consNo;
	if (Ext.isEmpty(consName) || "null" == consName) {
		consName = "";
	}
	tmnlInstllDet_ConsName = consName;
	openTab("原始报文查询", "./baseApp/dataGatherMan/origFrameQry.jsp", false,
			"origFrameQry");
}
Ext.onReady(function() {
	/* 错误提示初始化 */
	/** ************** */
	// Ext.QuickTips.init();
	/** ************** */
	/* 召测结果数据仓库,用来保存所有的召测的结果 */
	var dataResult = {}
	var dataState = {};
	var dateStateDict = {
		0 : "在规定的时间内缓存没有返回",
		1 : "终端不在线",
		3 : "成功",
		4 : "失败"
	};
	// 单独召测时候的项
	// 单独召测时候的数据
	var singData = {};
	var newData;

	// 清空单独召测的项
	function clearSingle() {
		singData = {};
	}
	// 生成一个不相等的字符串
	function randomKey() {
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

	// 一个判断两个时间之差的方法是不是在n天之内
	function dateInN(start, end, n) {
		var start = start.clearTime();
		var end = end.clearTime();
		if (end > start && start.add(Date.DAY, n) >= end) {
			return true;
		}
		return false;
	}

	// 通过这个函数还选择结果显示时候的数据源
	function choiceData(tmnl) {
		if (!tmnl) {
			return dataResult;
		}
		return newData && newData[tmnl] || dataResult;
	}
	// 初始化单独召测列表
	function pushSingle(tmnl, data) {
		if (!newData) {
			newData = {};
		}
		newData[tmnl] = data;
	}

	function buildKeyList(arr, key) {
		var m = {};
		if (!arr) {
			return m;
		}
		for (var i = 0; i < arr.length; i++) {
			var a = arr[i];
			var dataKey = a[key];
			if (!m[dataKey]) {
				m[dataKey] = [];
			}
			m[dataKey].push(a);
		}
		return m;
	}
	function buildSuccess(list) {
		var m = {};
		for (var i = 0; i < list.length; i++) {
			var k = list[i];
			m[k] = [];
		}
		return m;
	}

	// 包装一个对象，监视session
	// setKey
	var sessionListener = function(config) {
		Ext.apply(this, config);
		this.initKey();
	}
	// 注意未修复fn firstFn lastFn方法的this，在这些方法中慎用this
	sessionListener.prototype = (function() {
		// 私有部分
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
		// 公有部分
		return {
			interval : 5000,
			// 持续时间
			lastTime : 100000,
			state : "ready",
			// 调用一次此方法初始化一个一般不会重复的key
			initKey : function() {
				this.key = utils.randomKey();
			},
			// 默认的key
			key : "dataFetch",
			// 在开始监测之前执行的代码,在start的时候只会执行一次
			firstFn : function() {

			},
			// 这里用来存放在每个interval时间内的代码
			fn : function() {
			},
			pause : function() {
				this.state = "pause";
			},
			restart : function() {
				this.state = "starting";
			},
			// 这里用来存放召测结束后的代码
			lastFn : function() {
			},
			// 开始监听,将以前正在进行的定时器去掉，
			start : function() {
				if (this.listener) {
					this.stop();
				}
				var i = 0;
				var me = this;
				try {
					typeof me.firstFn == "function" ? me.firstFn(me) : "";
				} catch (e) {
				}
				me.state = "starting";
				me.listener = setInterval(function() {
							// 如果当前的定时器被挂起
							if (me.state == "pause") {
								return;
							}
							i += me.interval;
							try {
								typeof me.fn == "function" ? me.fn(me) : "";
							} catch (e) {
							}
							if (i >= me.lastTime) {
								me.stop();
							}
						}, me.interval);
			},
			// 停止监听
			stop : function() {
				var me = this;
				try {
					typeof me.lastFn == "function" ? me.lastFn(me) : "";
				} catch (e) {
				}
				clearInterval(me.listener);
				me.state = "stop";
				delete me.listener;
			}
		}
	})();
	/***************************************************************************
	 * 召测是session监视的方法
	 **************************************************************************/
	// 对newData进行线程同步的方式进行修改操作,存在两个线程操作一个对象的情况，虽然几率极小
	var synFlat = false;
	function synModify(data, over) {
		if (!synFlat) {
			synFlat = true;
			newData = newData || {};
			if (over === true) {
				Ext.apply(newData, data);
			} else {
				for (k in data) {
					if (!newData[k]) {
						newData[k] = [];
					}
					Array.prototype.push.apply(newData[k], data[k]);
				}
			}
			synFlat = false;
			return;
		}
		while (synFlat) {
		};
		synModify(data)
	}
	function doFirstFn(params) {
		return function(s) {
			s.initKey();
			s.params = params;
			params.key = s.key;
			var state = Ext.getCmp("dataFetch_state");
			state.setText("正在召测....");
			// dataResult = {};
			newData = newData || {};
			// dataState = {};
			// clearSingle();
			var mp = {};
			// terminalGrid.getStore().each(function(a) {
			// a.set("fetchState", " ");
			// });
			Ext.each(params.tmnlAssetNos, function(p) {
						newData[p] = [];
						mp[p] = true;
					});
			terminalGrid.getStore().each(function(a) {
						if (mp[a.get("tmnl_asset_no")]) {
							a.set("fetchState", "召测进行中");
						}
					})
			Ext.Ajax.request({
						timeout : 300000,
						url : 'baseapp/dataFetch!dataFetch.action',
						params : params,
						success : function(response) {
							var o = Ext.decode(response.responseText);
							if (o.message && o.message.trim() != ""
									&& o.message != "over") {
								Ext.Msg.alert("提示", o.message);
								s.stop();
							}
							Ext.apply(dataState, o.errorMap);
						},
						failure : function() {
							Ext.Msg.alert("提示", "test");
							s.stop();
						}
					});
		}
	}
	var iii = 0;
	var tmnlFetch = {};
	// 取回数据的代码,返回一个闭包
	function doQueryData(single) {
		return function(s) {
			Ext.Ajax.request({
						url : 'baseapp/dataFetch!querySessionData.action',
						params : {
							key : s.key
						},
						success : function(response) {
							var o = Ext.decode(response.responseText);
							var bk = buildKeyList(o.resultMap, "tmnlAssetNo");
							synModify(bk);
							Ext.apply(tmnlFetch, bk);
							var state = Ext.getCmp("dataFetch_state");
							if (o.resultMap && o.resultMap.length > 0) {
								var len = 0;
								Ext.iterate(tmnlFetch, function(k, v) {
											len++;
										})
								single === true ? "" :(state.setText ("有数据返回终端比:"+len+"/"+s.params.tmnlAssetNos.length));
								terminalGrid.getStore().each(function(a) {
											if (bk[a.get("tmnl_asset_no")]) {
												a.set("fetchState", undefined);
											}
										})
								terminalGrid.getStore().loadCurrent();
							}
							// var seles =
							// terminalGrid.getSelectionModel().getSelections();
							if (o.message && o.message == "over") {
								state.setText("召测完成");
								single === true ? "" : Ext.Msg.alert("提示",
										"召测结束");
								iii = 0;
								tmnlFetch = {}
								s.stop();
							}
						}
					});
		}
	}
	/**
	 * 当召测结束后要执行的代码
	 */
	function endDataFetch(single) {
		return function(s) {
			Ext.Ajax.request({
						url : 'baseapp/dataFetch!clearAllSesssionData.action',
						params : {
							key : s.key
						},
						success : function(response) {
							var o = Ext.decode(response.responseText);
						}
					});
			iii = 0;
			tmnlFetch = {};
			var state = Ext.getCmp("dataFetch_state");
			terminalGrid.getStore().each(function(a) {
						a.set("fetchState", undefined);
					})
					state.setText("召测完成");
			//single === true ? "" : state.setText("召测完成");
		}
	}
	var listenerMain = new sessionListener({
				fn : doQueryData(),
				lastFn : endDataFetch(),
				lastTime : 300000
			});
	// 保证key对应的session中的数据及时被消除以及当前定时器关闭
	Ext.getCmp('数据召测').on('destroy', function() {
				listenerMain && listenerMain.stop();
			})
	/***************************************************************************
	 * session监视代码结束
	 **************************************************************************/
	// var sss=new sessionListener();
	// sss.fn=function(){alert("every")};
	// sss.lastFn=function(){alert("over");};
	// sss.start();
	function clearDataState(obj, key) {
		dataResult = {};
		if (Ext.isArray(obj)) {
			Ext.each(obj, function(o) {
						newData && (function() {
							delete newData[o.get(key)];
						})();
						dataState && (function() {
							delete dataState[o.get(key)];
						})();
					});
		} else {
			newData = {};
			dataState = {};
		}
		// clearSingle();
	}
	// 大项和所对应的名称，与dataResult生命周期一样
	var bigCodeToName = {}
	// 当初查询的数据类型，与dataResult生命周期一样
	var dataQueryType;
	/***************************************************************************
	 * 
	 * 修改支持renderer支持的配置项
	 **************************************************************************/
	var rendererConfig = {
		createWorksheet : function(includeHidden, notIncludeData) {
			// Calculate cell data types and extra class names which affect
			// formatting
			var cellType = [];
			var cellTypeClass = [];
			var cm = this.getColumnModel();
			var totalWidthInPixels = 0;
			var colXml = '';
			var headerXml = '';
			var visibleColumnCountReduction = 0;
			var colCount = cm.getColumnCount();
			for (var i = 0; i < colCount; i++) {
				if ((cm.getDataIndex(i) != '')
						&& (includeHidden || !cm.isHidden(i))) {
					var w = cm.getColumnWidth(i)
					totalWidthInPixels += w;
					if (cm.getColumnHeader(i) === "") {
						cellType.push("None");
						cellTypeClass.push("");
						++visibleColumnCountReduction;
					} else {
						colXml += '<ss:Column ss:AutoFitWidth="1" ss:Width="'
								+ w + '" />';
						headerXml += '<ss:Cell ss:StyleID="headercell">'
								+ '<ss:Data ss:Type="String">'
								+ cm.getColumnHeader(i)
								+ '</ss:Data>'
								+ '<ss:NamedCell ss:Name="Print_Titles" /></ss:Cell>';
						var fld = this.store.recordType.prototype.fields.get(cm
								.getDataIndex(i));
						fld = fld || {};
						switch (fld.type) {
							case "int" :
								cellType.push("Number");
								cellTypeClass.push("int");
								break;
							case "float" :
								cellType.push("Number");
								cellTypeClass.push("float");
								break;
							case "bool" :
							case "boolean" :
								cellType.push("String");
								cellTypeClass.push("");
								break;
							case "date" :
								cellType.push("DateTime");
								cellTypeClass.push("date");
								break;
							default :
								cellType.push("String");
								cellTypeClass.push("");
								break;
						}
					}
				}
			}
			var visibleColumnCount = cellType.length
					- visibleColumnCountReduction;

			var result = {
				height : 9000,
				width : Math.floor(totalWidthInPixels * 30) + 50
			};

			var rowCount = 0;
			if (!notIncludeData)
				rowCount = (this.store.getCount() + 2);
			else
				rowCount = "rowCountToreplace";
			// Generate worksheet header details.
			var t = '<ss:Worksheet ss:Name="'
					+ this.title
					+ '">'
					+ '<ss:Names>'
					+ '<ss:NamedRange ss:Name="Print_Titles" ss:RefersTo="=\''
					+ this.title
					+ '\'!R1:R2" />'
					+ '</ss:Names>'
					+ '<ss:Table x:FullRows="1" x:FullColumns="1"'
					+ ' ss:ExpandedColumnCount="'
					+ (visibleColumnCount + 2)
					+ '" ss:ExpandedRowCount="'
					+ rowCount
					+ '">'
					+ colXml
					+ '<ss:Row ss:Height="38">'
					+ '<ss:Cell ss:StyleID="title" ss:MergeAcross="'
					+ (visibleColumnCount - 1)
					+ '">'
					+ '<ss:Data xmlns:html="http://www.w3.org/TR/REC-html40" ss:Type="String">'
					+ '<html:B>Generated by ExtJS</html:B></ss:Data><ss:NamedCell ss:Name="Print_Titles" />'
					+ '</ss:Cell>' + '</ss:Row>'
					+ '<ss:Row ss:AutoFitHeight="1">' + headerXml + '</ss:Row>';

			// Generate the data rows from the data in the Store
			if (!notIncludeData) {
				var it = this.store.data.items;
				if (this.local == true) {
					it = [];
					Ext.iterate(this.store.mainData.baseData, function(k, v) {
								it.push(v);
							});
				}

				for (var i = 0, l = it.length; i < l; i++) {
					t += '<ss:Row>';
					var cellClass = (i & 1) ? 'odd' : 'even';
					r = this.local && it[i] || it[i].data;
					var k = 0;
					for (var j = 0; j < colCount; j++) {
						if ((cm.getDataIndex(j) != '')
								&& (includeHidden || !cm.isHidden(j))) {
							var v = "";
							if (cm.getRenderer(j)) {
								// v = cm.getRenderer(j)(r[cm.getDataIndex(j)],
								// null, new Ext.data.Record(r))
								// + "";
								v = cm.getRenderer(j).call(cm.config[j],
										r[cm.getDataIndex(j)], null,
										new Ext.data.Record(r))
										+ "";
								v = v.replace(/<.*?>/g, "");
							} else {
								v = r[cm.getDataIndex(j)];
							}

							if (cellType[k] !== "None") {
								t += '<ss:Cell ss:StyleID="' + cellClass
										+ cellTypeClass[k]
										+ '"><ss:Data ss:Type="' + cellType[k]
										+ '">';
								if (cellType[k] == 'DateTime') {
									t += v.format('Y-m-d');
								} else {
									t += v;
								}
								t += '</ss:Data></ss:Cell>';
							}
							k++;
						}
					}
					t += '</ss:Row>';
				}
			} else {
				for (var i = 0, it = data.baseData, l = it.length; i < l; i++) {
					t += '<ss:Row>';
					var cellClass = (i & 1) ? 'odd' : 'even';
					r = it[i];
					var k = 0;
					for (var j = 0; j < colCount; j++) {
						if ((cm.getDataIndex(j) != '')
								&& (includeHidden || !cm.isHidden(j))) {
							var v = "";
							if (cm.getRenderer(j)) {
								v = cm.getRenderer(j)(r[cm.getDataIndex(j)],
										null, new Ext.data.Record(r))
										+ "";
								v = v.replace(/<.*?>/g, "");
							} else {
								v = r[cm.getDataIndex(j)];
							}

							if (cellType[k] !== "None") {
								t += '<ss:Cell ss:StyleID="' + cellClass
										+ cellTypeClass[k]
										+ '"><ss:Data ss:Type="' + cellType[k]
										+ '">';
								if (cellType[k] == 'DateTime') {
									t += v.format('Y-m-d');
								} else {
									t += v;
								}
								t += '</ss:Data></ss:Cell>';
							}
							k++;
						}
					}
					t += '</ss:Row>';
				}

			}

			result.xml = t
					+ '</ss:Table>'
					+ '<x:WorksheetOptions>'
					+ '<x:PageSetup>'
					+ '<x:Layout x:CenterHorizontal="1" x:Orientation="Landscape" />'
					+ '<x:Footer x:Data="Page &amp;P of &amp;N" x:Margin="0.5" />'
					+ '<x:PageMargins x:Top="0.5" x:Right="0.5" x:Left="0.5" x:Bottom="0.8" />'
					+ '</x:PageSetup>' + '<x:FitToPage />' + '<x:Print>'
					+ '<x:PrintErrors>Blank</x:PrintErrors>'
					+ '<x:FitWidth>1</x:FitWidth>'
					+ '<x:FitHeight>32767</x:FitHeight>'
					+ '<x:ValidPrinterInfo />'
					+ '<x:VerticalResolution>600</x:VerticalResolution>'
					+ '</x:Print>' + '<x:Selected />'
					+ '<x:DoNotDisplayGridlines />'
					+ '<x:ProtectObjects>False</x:ProtectObjects>'
					+ '<x:ProtectScenarios>False</x:ProtectScenarios>'
					+ '</x:WorksheetOptions>' + '</ss:Worksheet>';
			return result;
		}
	};
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

	/** **显示结果显示的窗口**** */

	Ext.form.Field.prototype.msgTarget = 'side';

	/** ******** */
	/* 空白面板 */
	/** ******** */
	var blank = new Ext.Panel({
				border : false,
				id : "dataFetch_blank",
				width : 30,
				autoWidth : false,
				height : 10,
				bodyStyle : 'padding:10px 10px 10px 10px'
			});

	/** ********************************************************************************************** */

	/**
	 * ****************************************** 召测面板
	 * *******************************************
	 */
	/** ******************* */
	/* 数据类型单选按钮组合 */
	/** ******************* */
	var radioGroup_df = new Ext.form.RadioGroup({
		width : 250,
		id : "queryType_group",
		// anchor : "20%,10%",
		// bodyStyle : "padding:30px 0 0 201px",
		// style : "margin:auto 20px;",
		// padding:'0px 0px 0px 30px',
		colums : [80, 80, 80],
		items : [{
					boxLabel : '实时数据',
					name : 'queryType',
					inputValue : 1,
					checked : true
				}, {
					boxLabel : '历史数据',
					name : 'queryType',
					inputValue : 2
				}, {
					boxLabel : '事件数据',
					name : 'queryType',
					inputValue : 3
				}],
		listeners : {
			'change' : function(othis, checked) {
				var queryType = Ext.getCmp("queryType_group").getValue().inputValue;
				filterCom(queryType, storeProtocolGroup);
				storeProtocol.removeAll();
				smProtocolGroup.fireEvent("rowselect", smProtocolGroup, null,
						smProtocolGroup.getSelected());
				Ext.each(startDate.findByType("datefield"), function(d) {
							d.setValue("");
						});
				Ext.each(endDate.findByType("datefield"), function(d) {
							d.setValue("");
						});
				var sd = startDate.findByType("datefield")[0];
				var ed = endDate.findByType("datefield")[0];
				if (checked.inputValue == 1) {
					startDate.setVisible(false);
					endDate.setVisible(false);
				} else {
					startDate.setVisible(true);
					endDate.setVisible(true);
				}
				// var msg="请选择测量点号/总加组号(默认为终端)";
				// var msg2="请选择事件级别";
				if (queryType == 3) {
					pointCheckBox.hide();
					storeProtocolGroup.filterBy(function() {
								return false;
							});
					storeProtocol.filterBy(function() {
								return false;
							});
					// explain.setText(msg2);
					explain.hide();
					eventCombo.show();
					moreBtn.hide();
				} else {
					// explain.setText(msg);
					eventCombo.hide();
					explain.show();
					moreBtn.show();
					pointCheckBox.show();
				}
			}
		}
	});

	/** **************** */
	/* 历史数据时间选择 */
	/** **************** */
	var startDate = new Ext.Panel({
				border : false,
				layout : 'form',
				labelWidth : 50,
				labelAlign : 'right',
				// bodyStyle : 'padding:0px 10px 0px 0px',
				items : [new Ext.form.DateField({
							name : 'startDate',
							allowBlank : false,
							fieldLabel : '时间从',
							width : 90
						})]
			});
	var endDate = new Ext.Panel({
				border : false,
				layout : 'form',
				labelWidth : 20,
				labelAlign : 'right',
				// bodyStyle : 'padding:0px 10px 0px 0px',
				items : [new Ext.form.DateField({
							name : 'endDate',
							allowBlank : false,
							fieldLabel : '到',
							width : 90

						})]
			});
	var eventCombo = new Ext.Panel({
				layout : "form",
				border : false,
				hidden : true,
				labelAlign : "right",
				labelWidth : 50,
				bodyStyle : "padding:10px 0px 0px 15px;",
				items : [{
							xtype : "combo",
							id : "dataFetch_eventCombo",
							width : 150,
							fieldLabel : "事件级别",
							name : "eventLevel",
							mode : "local",
							hiddenName : "eventLevel",
							triggerAction : "all",
							displayField : "text",
							valueField : "value",
							emptyText : "请选择事件级别",
							store : new Ext.data.SimpleStore({
										fields : ["value", "text"],
										data : [["01", "重要"], ["02", "一般"]]
									})
						}]
			});
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
								var datafetch_point = Ext
										.getCmp("datafetch_point");
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
		autoScroll : true,
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
	/** ************ */
	/* 数据类型面板 */
	/** ************ */
	var typePanel_df = new Ext.Panel({
				layout : 'column',
				border : false,
				padding : '0px 0px 0px 20px',
				// autoEl : {
				// tag : "center"
				// },
				items : [radioGroup_df]
			});

	/** **************** */
	/* 召测组合选择表格 */
	/** **************** */

	var smProtocolGroup = new Ext.grid.RowSelectionModel({
		singleSelect : true,
		listeners : {
			'rowselect' : function(sm, rowIndex, r) {
				if (!r) {
					return;
				}
				var queryType = Ext.getCmp("queryType_group").getValue().inputValue;
				storeProtocol.removeAll();
				for (var key in r.data.combiMap) {
					var protocolObj = new Object();
					protocolObj.protocolName = r.data.combiMap[key];
					protocolObj.protocolCode = key;
					var name = protocolObj.protocolName;

					protocolObj.protocolName = name.replace(/@@\d*$/g, "");
					var protocolRecord = new Ext.data.Record(protocolObj);
					storeProtocol.add(protocolRecord);
				}
			}
		}
	});
	var cmProtocolGroup = new Ext.grid.ColumnModel([{
		dataIndex : 'combiName',
		width : 248,
		align : 'left',
		renderer : function(v, m, r) {
			// if (r && r.get("isShare")) {
			// var staffNo = r.get("staffNo");
			var html = '<span ext:qtitle="组合名称" ext:qtip="' + v + '">' + v
					+ '</span>';
			return html;
			// }
			// return v;
		},
		menuDisabled : true
	}]);
	// 过滤组合参数
	function filterCom(queryType, ds) {
		ds.filterBy(function(r, id) {
					var map = r.get("combiMap");
					var str = Ext.encode(map);
					if (str.indexOf("@@" + queryType) > 0) {
						return true;
					} else
					// 保证既是一类又是二类的正确
					{
						var i = queryType - 1 || 2;
						if (str.indexOf("@@" + i) < 0) {
							return true;
						}
					}

					return false;
				});

	}
	var storeProtocolGroup = new Ext.data.Store({
				// autoLoad : true,
				proxy : new Ext.data.HttpProxy({
							url : 'baseapp/dataFetch!queryProtocolGroup.action'
						}),

				reader : new Ext.data.JsonReader({
							root : 'protocolGroupDTOList'
						}, [{
									name : 'combiName'
								}, {
									name : "combiId"
								}, {
									name : 'staffNo'
								}, {
									name : 'createDate'
								}, {
									name : 'validDays'
								}, {
									name : 'isShare'
								}, {
									name : 'combiMap'
								}])
			});
	var gridProtocolGroup = new Ext.grid.GridPanel({
				border : false,
				hideHeaders : true,
				stripeRows : true,
				store : storeProtocolGroup,
				cm : cmProtocolGroup,
				sm : smProtocolGroup,
				viewConfig : {
					forceFit : true
				}
			});
	var panelProtocolGroup = new Ext.Panel({
				border : true,
				columnWidth : .5,
				// width : 170,
				layout : "fit",
				height : 170,
				items : [gridProtocolGroup],
				tbar : [{
							xtype : 'label',
							text : '召测组合',
							style : 'font-weight:bold;color:#15428b'
						}, '->', {
							text : '管理组合',
							handler : groupManager
						}]
			});

	/** ****************** */
	/* 召测数据项展示表格 */
	/** ****************** */
	var cmProtocol = new Ext.grid.ColumnModel([{
		header : '数据项名称',
		dataIndex : 'protocolName',
		align : 'left',
		renderer : function(v) {
			var html = '<span ext:qtitle="数据项名称" ext:qtip="' + v + '">' + v
					+ '</span>';
			return html;
		},
		menuDisabled : true
	}, {
		header : '编码',
		dataIndex : 'protocolCode',
		align : 'left',
		menuDisabled : true,
		hidden : true
	}]);
	var storeProtocol = new Ext.data.ArrayStore({
				fields : [{
							name : 'protocolName'
						}, {
							name : 'protocolCode'
						}]
			});
	var gridProtocol = new Ext.grid.GridPanel({
				border : false,
				id : "df_gridProtocol",
				hideHeaders : true,
				stripeRows : true,
				store : storeProtocol,
				cm : cmProtocol,
				viewConfig : {
					forceFit : true
				}
			});

	var panelProtocol = new Ext.Panel({
				border : true,
				layout : "fit",
				columnWidth : .5,
				height : 170,
				// autoHeight : true,
				items : [gridProtocol],
				tbar : [{
							xtype : 'label',
							text : '召测数据项',
							style : 'font-weight:bold;color:#15428b'
						}]
			});
	// gridProtocol.remove()
	// var i=new Ext.Container();i.destroy();
	/** ************** */
	/* 召测数据项面板 */
	/** ************** */
	// var protocolGridPanel = new Ext.Panel({
	// border : false,
	// layout : 'column',
	// width : 410,
	// height : 180,
	// items : [panelProtocolGroup, {
	// xtype : "panel",
	// width : 30,
	// border : false,
	// height : 180
	// }, panelProtocol]
	// });
	/** ************ */
	/* 召测按钮面板 */
	/** ************ */
	var buttonPanel = new Ext.Panel({
		border : false,
		layout : 'form',
		items : [{
			xtype : 'button',
			text : '召测',
			handler : function(btn) {

				var queryType = Ext.getCmp("queryType_group").getValue().inputValue;
				if (queryType == 3
						&& eventCombo.findByType("combo")[0].getValue() == "") {
					return !!Ext.Msg.alert("警告", "请选择事件级别");
				}
				var arrCheckBox = Ext.getCmp("datafetch_point").getValue();
				var params = {
					point : []
				};
				var r = gridProtocolGroup.getSelectionModel().getSelected();
				if (!r && queryType != 3) {
					return !!Ext.Msg.alert("警告", "必须要选择一项召测组合");
				}

				// terminalGrid.getStore().loadCurrent();
				params.combiId = (r && r.get("combiId")) || 0;
				// queryType_group
				// gridProtocol 召测数据项
				params.queryType = queryType;
				for (var i = 0; i < arrCheckBox.length; i++) {
					params.point.push(arrCheckBox[i].boxLabel);
				}
				params.point = params.point.concat(morePanel.points);
				params.tmnlAssetNos = [];
				var allCheck = Ext.getCmp("df_selectAll").checked;
				var assetNos;
				if (allCheck) {
					params.tmnlAssetNos = storeTerminalGrid.getAllKeys();
				} else {
					assetNos = terminalGrid.getSelectionModel().getSelections();
					if (!assetNos || assetNos.length == 0) {
						return !!Ext.Msg.alert("警告", "请选择你要查询的终端");
					}
					for (var i = 0; i < assetNos.length; i++) {
						// assetNos[i].set("fetchState", "尚未召测");
						params.tmnlAssetNos.push(assetNos[i]
								.get("tmnl_asset_no"));
					}
				}
				var index = 0;
				var sd = startDate.findByType("datefield")[0];
				var ed = endDate.findByType("datefield")[0];
				// 只对历史数据进行时间的限制
				if (queryType == 2) {
					if (!sd.isValid()) {
						return !!Ext.Msg.alert("警告", "开始时间不能为空");
					}
					if (!ed.isValid()) {
						return !!Ext.Msg.alert("警告", "结束时间不能为空");
					}
					// 当是历史数据的时候，看看是不是时间在7天之内
					// if (queryType == 2) {
					var in7 = dateInN(sd.getValue(), ed.getValue(), 7);
					if (!in7) {
						return !!Ext.Msg.alert("警告", "请确保开始时间和结束在七天之内");
					}
					// }
				}
				// 填充时间
				params.startTime = sd.getValue();
				params.endTime = ed.getValue();

				params.eventLevel = eventCombo.findByType("combo")[0]
						.getValue();
				// protocolCode 透明公约的编码
				// btn.disable();
				// dataResult = {};
				// newData = {};
				// dataState = {};
				// clearSingle();
				// terminalGrid.getStore().eachAll(function(key, o) {
				// this.data.item(key)
				// && this.data.item(key).set("fetchState", " ");
				// });
				var ov = true;
				// h_taskTime(200, function() {
				// ov = false;
				// });

				listenerMain.firstFn = doFirstFn(params);
				listenerMain.start();
				 unlockGrid();
				// Ext.Ajax.request({
				// timeout : 180000,
				// url : 'baseapp/dataFetch!dataFetch.action',
				// method : 'post',
				// params : params,
				// success : function(response) {
				// if (!ov) {
				// return;
				// }
				//
				// var action = Ext.decode(response.responseText);
				// if (action.message && action.message != "") {
				// ov = true;
				// overFlat = true;
				// setTimeout(function() {
				// Ext.Msg.alert("错误",
				// action.message);
				// }, 1500);
				// return;
				// }
				// dataState = action.errorMap;
				// // 对所有的数据进行初始化部分进行清理
				// bigCodeToName = action.bigCodeToName;
				// dataQueryType = action.queryType;
				// // 填充仓库
				// dataResult = action.resultMap;
				// newData = buildKeyList(dataResult,
				// "tmnlAssetNo");
				// Ext.each(params.tmnlAssetNos, function(p) {
				// if (newData[p]) {
				//
				// } else {
				// newData[p] = [];
				// }
				// });
				// var seles = terminalGrid.getSelectionModel()
				// .getSelections();
				//
				// // alert(index);
				// for (var i = 0; i < seles.length; i++) {
				// var r = seles[i];
				// // 得到终端资产号
				// var assetNo = r.get("tmnl_asset_no");
				// if (assetNo && newData[assetNo]
				// && newData[assetNo].length > 0) {
				// r.set("fetchState", "召测成功");
				// } else {
				// r.set("fetchState", "召测失败");
				// }
				// // var flat = 0;
				// // for (var j = 0; j < dataResult.length;
				// // j++) {
				// // var o = dataResult[j];
				// // if (o && o.tmnlAssetNo == assetNo) {
				// // flat = 1;
				// // break;
				// // }
				// // }
				// // if (flat == 1) {
				// // r.set("fetchState", "召测成功");
				// // } else {
				// // r.set("fetchState", "召测失败");
				// // }
				// }
				// // if (allCheck) {
				// // terminalGrid.getStore().eachAll(function(key,
				// // v) {
				// //
				// // var flat = 0;
				// // for (var j = 0; j < dataResult.length; j++) {
				// // var o = dataResult[j];
				// // if (o && key == o.tmnlAssetNo) {
				// // flat = 1;
				// // break;
				// // }
				// // }
				// //
				// // if (flat) {
				// // v && (v["fetchState"] = "召测成功");
				// // } else {
				// // v && (v["fetchState"] = "召测失败");
				// // }
				// // });
				// // }
				//
				// overFlat = true;
				
				//
				// // 让grid禁用
				// // Ext.apply(terminalGrid, {
				// // onDisable : function() {
				// // if (this.rendered && this.maskDisabled) {
				// // this.body.mask();
				// // }
				// // Ext.Panel.superclass.onDisable
				// // .call(this);
				// // }
				// // });
				// // terminalGrid.onDisable();
				//
				// },
				// failure : function(response) {
				//
				// if (!ov) {
				// return;
				// }
				// Ext.MessageBox.alert('提示', '请求超时或失败！');
				// // btn.enable();
				// overFlat = true;
				// var action = Ext.decode(response.responseText);
				// if (action.message && action.message != "") {
				// return Ext.Msg.alert(message);
				// }
				// return;
				// },
				// callback : function() {
				// overFlat = true;
				// }
				// });
			}
		}]
	});

	/** ************** */
	/* 测量点选择面板 */
	/** ************** */
	var moreBtn = new Ext.Button({
				text : "更多",
				width : 50,
				handler : function() {
					morePanel.show();
				}
			});
	var pointCheckBox = new Ext.Panel({
				border : false,
				layout : 'table',
				labelWidth : 50,
				bodyStyle : 'padding:10px 0px 0px 20px;',
				layoutConfig : {
					columns : 2
				},
				// labelAlign:"right",
				items : [{
							xtype : 'checkboxgroup',
							// fieldLabel : '测量点',
							hideLabel : true,
							labelStyle : "text-align:right;width:50;",
							id : "datafetch_point",
							columns : [30, 30, 30, 30, 40],
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
						}, moreBtn]
			});

	/** **********北部最右边的面板相关**************** */

	/** *****解释性文字****** */
	var explain = new Ext.BoxComponent({
				html : "表计编号/总加组号",
				style : {
					"text-align" : "left",
					"font-size" : "11px",
					"padding" : "10px 0px 0px 20px"
				}
			});
	var dateSelect = new Ext.Panel({
				border : false,
				heigth : 30,
				bodyStyle : "padding:10px 0px 0px 10px;",
				layout : "table",
				layConfig : {
					columns : 2
				},
				items : [startDate, endDate]
			});
	var northRight = new Ext.Panel({
				region : 'east',
				layout : "form",
				width : 320,
				// labelSeparator : '',
				border : false,
				items : [{
					layout : "table",
					border : false,
					bodyStyle : "padding:20px 0px 0px 0px;",
					layoutConfig : {
						columns : 1
					},
					items : [typePanel_df, dateSelect, explain, pointCheckBox,
							eventCombo]
				}],
				// buttons : [buttonPanel],
				buttonAlign : "left"
			});

	/** ******** */
	/* 召测面板 */
	/** ******** */
	var fetchPanel = new Ext.Panel({
				border : true,
				// autoEl : {
				// tag : "center"
				// },
				region : 'north',
				// frame : false,
				// width : 900,
				layout : 'border',
				height : 230,
				buttonAlign : "center",
				buttons : [buttonPanel, {
							text : "取消",
							handler : function() {
								if (listenerMain.state == "starting") {
									listenerMain.stop();
								}
							}
						}],
				items : [new Ext.Panel({
									border : false,
									height : 180,
									region : 'center',
									layout : 'column',
									padding : '10px 0px 0px 10px',
									items : [panelProtocolGroup,
											new Ext.Panel({
														border : false,
														width : 10,
														height : 170
													}), panelProtocol]
								}), northRight]
			});

	/**
	 * *************************************** 终端列表面板
	 * ******************************************
	 */
	/** ******** */
	/* 终端列表 */
	/** ******** */
	var smTerminalGrid = new Ext.grid.CheckboxSelectionModel({});
	var rowNumTerminalGrid = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			if (this.rowspan) {
				p.cellAttr = 'rowspan="' + this.rowspan + '"';
			}

			storeTerminalGrid.lastOptions.params = storeTerminalGrid.lastOptions.params
					|| {
						start : 0
					};
			return storeTerminalGrid.lastOptions.params.start + rowIndex + 1;
		}

	});

	var cmTerminalGrid = new Ext.grid.ColumnModel([rowNumTerminalGrid,
			smTerminalGrid, {
				header : '供电单位',
				dataIndex : 'org_name',
				renderer : function(v) {
					var html = '<span ext:qtitle="供电单位" ext:qtip="' + v + '">'
							+ v + '</span>';
					return html;
				},
				align : 'left'
			}, {
				header : '用户编号',
				dataIndex : 'cons_no',
				align : 'left',
				renderer : function(v) {
					return '<span style="text-align:left">' + v + '</span>';
				}
			}, {
				header : '用户名称',
				dataIndex : 'cons_name',
				renderer : function(v) {
					var html = '<span ext:qtitle="用户名称" ext:qtip="' + v + '">'
							+ v + '</span>';
					return html;
				},
				align : 'left'
			}, {
				header : '终端地址',
				dataIndex : 'terminal_addr',
				renderer : function(v, m, r) {
					var html = '<a href="#" ext:qtitle="终端地址:" ' + 'ext:qtip="'
							+ '点击召测此终端' + '" onclick="window.tmnlWin(\''
							+ '终端地址' + v + '的表计信息' + '\');return false" >' + v
							+ '</a>';
					return html;
				},
				align : 'left'
			}, {
				header : '终端资产号',
				dataIndex : 'cis_asset_no',
				renderer : function(v, m, r) {
					var html = '<span ext:qtitle="终端资产号" ext:qtip="' + v + '">'
							+ v + '</span>';
					return html;
				},
				align : 'left'
			}, {
				header : '规约类型',
				dataIndex : 'protocol_name',
				align : 'center'
			}, {
				header : '制造厂商',
				dataIndex : 'factory_name',
				align : 'left'
			}, {
				header : '召测状态',
				dataIndex : 'fetchState',
				renderer : function(v, m, r) {
					if (v) {
						return v;
					}
					// 成功失败的flat
					var flat = false;
					var tmnlNo = r.get('tmnl_asset_no');
					if ((!newData || !newData[tmnlNo])) {
						return "";
					}

					if (newData && newData[tmnlNo]) {
						if (newData && newData[tmnlNo].length > 0) {
							flat = true;
						}
					}
					if (flat) {
						var html = "已返回" + newData[tmnlNo].length + "项结果";
						return '<span ext:qtitle="结果详细" ext:qtip="' + html
								+ '">' + html + '</span>';
					}
					var index = dataState[tmnlNo] == 3 ? 4 : dataState[tmnlNo];
					return '<font color="red">'
							+ (dateStateDict[index] || "失败") + '</font>';
				},
				align : 'center'
			}, {
				header : '召测结果',
				dataIndex : 'fetchResult',
				align : 'center',
				renderer : function(v, m, r) {
					// resultWin("用户:"++"召测结果");
					var name = 'window.resultWin(\'用户:' + r.get("cons_name")
							+ ',终端地址为' + r.get('terminal_addr') + '的召测信息\')';
					var src = '<a href="#" onclick="' + name
							+ ';return false" >查看结果</a>';
					return src;
				}
			}, {
				header : '原始报文',
				dataIndex : 'message',
				align : 'center',
				renderer : function(v, m, r) {
					return "<a href='javascript:'onclick='window.origFrameQryShow(\""
							+ r.get('cons_no')
							+ "\",\""
							+ r.get('cons_name')
							+ "\",\""
							+ r.get('terminal_addr')
							+ "\",\""
							+ r.get('protocol_code') + "\");" + "'>原始报文</a>";
				}
			}]);
	var storeTerminalGrid = new Ext.ux.LocalStore({
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
						}, {
							name : 'fetchState'
						}, {
							name : 'fetchResult'
						}, {
							name : 'message'
						}, {
							name : 'cis_asset_no'
						}, {
							name : 'protocol_code'
						}]
			});

	// grid解锁
	function unlockGrid() {
		smTerminalGrid.unlock();
		terminalGrid.onEnable();
		terminalGrid.getBottomToolbar().enable();
		Ext.getCmp('df_selectAll').setValue(false);
	}

	// grid解锁
	function lockGrid() {
		smTerminalGrid.lock();
		terminalGrid.onDisable();
		terminalGrid.getBottomToolbar().disable();
	}
	var terminalGrid = new Ext.grid.GridPanel({
		region : 'center',
		border : true,
		stripeRows : true,
		autoScroll : true,
		bodyStyle : "padding:0px 0p 0px 0px",
		height : 470,
		sm : smTerminalGrid,
		cm : cmTerminalGrid,
		store : storeTerminalGrid,
		bbar : new Ext.ux.MyToolbar({
					store : storeTerminalGrid,
					enableExpAll : true
				}),
		tbar : [{
					xtype : 'label',
					text : '备选用户',
					style : 'font-weight:bold;color:#15428b'
				}, "-", {
					xtype : "label",
					text : '无召测任务',
					id : "dataFetch_state",
					style : 'font-weight:bold;color:#15428b'
				}, '->', {
					xtype : "checkbox",
					boxLabel : "全选",
					id : "df_selectAll",
					listeners : {
						check : function(c, v) {
							if (v) {
								storeTerminalGrid.setAllSelect(true);
								smTerminalGrid.selectAll();
								lockGrid();
							} else {
								unlockGrid();
								storeTerminalGrid.clearAllSelect();
								smTerminalGrid.clearSelections();
							}
						}
					}
				}, "-", {
					text : '删除选中用户',
					// width : 100,
					iconCls : 'cancel',
					handler : function() {
						if (Ext.getCmp('df_selectAll').checked) {
							storeTerminalGrid.clearAll();
							clearDataState();
							unlockGrid();
						} else {
							var selectTerminal = smTerminalGrid.getSelections();
							clearDataState(selectTerminal, 'tmnl_asset_no');
							storeTerminalGrid.removeDatas(selectTerminal);
						}
					}
				}, "-", {
					text : "加入群组",
					iconCls : 'plus',
					handler : function() {
						var groupTmnlArray = [];

						if (Ext.getCmp('df_selectAll').checked) {
							var alldata = storeTerminalGrid.getBaseData();
							storeTerminalGrid.eachAll(function(k, v) {
										groupTmnlArray.push(v["cons_no"] + "`"
												+ k);
									});
						} else {
							var recs = smTerminalGrid.getSelections();
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
							if (Ext.getCmp('df_selectAll').checked) {
								unlockGrid();
							}
						}
					}
				}, "-", {
					text : "删除成功用户",
					iconCls : 'minus',
					handler : function() {
						// storeTerminalGrid.filterBy(function(r){
						// if(r.get("fetchState")=="召测成功"){
						// return false;
						// }
						// return true;
						// });
						var deleteList = [];
						storeTerminalGrid.eachAll(function(key, o) {
									if (newData && newData[key].length > 0) {
										deleteList.push(key);
										delete newData[key];
									}
								});
						// clearDataState(deleteList);
						storeTerminalGrid.removeDatas(deleteList);
					}
				}]
	});
	storeTerminalGrid.setGrid(terminalGrid);

	/** ********************************************************************************************** */

	/**
	 * *************************************** 组合管理窗口
	 * ******************************************
	 */
	function groupManager(title) {
		var queryTypeSelect = Ext.getCmp("queryType_group").getValue().inputValue;
		if (queryTypeSelect == 3) {
			return;
		}
		/** ************** */
		/* 透明规约编码树 */
		/** ************** */
		var treeRootNode = new Ext.tree.AsyncTreeNode({
					id : 'root',
					text : 'root'
				});
		var treeLoader = new Ext.tree.TreeLoader({
					dataUrl : 'baseapp/dataFetch!queryTreeNode.action',
					baseParams : {
						"queryType" : queryTypeSelect
					}
				});
		var tree = new Ext.tree.TreePanel({
					title : '规约编码',
					border : true,
					columnWidth : .45,
					height : 390,
					autoScroll : true,
					animate : false,
					frame : false,
					rootVisible : false,
					root : treeRootNode,
					loader : treeLoader,
					listeners : {
						'dblclick' : addProtocol
					}
				});
		function addProtocol(eNode, e) {
			var r = gridProtocolGroupWindow.getSelectionModel().getSelected();
			if (!r) {
				return !!Ext.Msg.alert("请先选择一个组合项");
			}
			var node = tree.getSelectionModel().getSelectedNode();
			var parentNode = tree.getSelectionModel().getSelectedNode().parentNode;
			if (!node.isLeaf() && node.id == eNode.id) {
				if (node.isExpanded()) {
					node.collapse();
				} else {
					node.expand();
				}
			}
			if (parentNode.id == 'root') {
				Ext.MessageBox.alert('出错', '不允许添加数据项类型！');
				return;
			}
			var nodeId = node.id;
			var nodeText = node.text;
			var protocolObj = new Object();
			protocolObj.protocolName = nodeText;
			protocolObj.protocolCode = nodeId;
			var protocolRecord = new Ext.data.Record(protocolObj);
			for (var i = 0; i < store.getCount(); i++) {
				if (store.getAt(i).data.protocolCode == nodeId) {
					Ext.MessageBox.alert('出错', '该数据项已存在！');
					return;
				}
				while (parentNode.id != 'root') {
					if (store.getAt(i).data.protocolCode == parentNode.id) {
						Ext.MessageBox.alert('出错', '该数据项父项已存在，不允许添加！');
						return;
					}
					parentNode = parentNode.parentNode;
				}
				parentNode = tree.getSelectionModel().getSelectedNode().parentNode;
				var childNodes = node.childNodes;
				for (var j = 0; j < childNodes.length; j++) {
					if (store.getAt(i).data.protocolCode == childNodes[j].id) {
						Ext.MessageBox.alert('出错', '已存在该数据项子项，不允许添加！');
						return;
					}
					if (childNodes[j].hasChildNodes()) {
						var tmpNodes = childNodes[j].childNodes;
						for (var x = 0; x < tmpNodes.length; x++) {
							if (store.getAt(i).data.protocolCode == tmpNodes[x].id) {
								Ext.MessageBox.alert('出错', '已存在该数据项子项，不允许添加！');
								return;
							}
						}
					}
				}
			}
			addComItem();
			store.add(protocolRecord);
		}

		/** ******************* */
		/* 透明规约编码明细表格 */
		/** ******************* */
		var sm = new Ext.grid.CheckboxSelectionModel({
					singleSelect : false
				});
		var cm = new Ext.grid.ColumnModel([sm, {
					header : '数据项名称',
					dataIndex : 'protocolName',
					align : 'left',
					width : 270,
					menuDisabled : true
				}, {
					header : '编码',
					dataIndex : 'protocolCode',
					align : 'left',
					width : 50,
					menuDisabled : true
				}]);
		var store = new Ext.data.ArrayStore({
					fields : [{
								name : 'protocolName'
							}, {
								name : 'protocolCode'
							}, {
								name : "combiId"
							}, {
								name : "dataGroup"
							}, {
								name : "isShare"
							}]
				});
		var gridPanel = new Ext.grid.GridPanel({
					store : store,
					border : false,
					id : "dataFetch_right",
					cm : cm,
					sm : sm,
					stripeRows : true,
					title : '召测数据项',
					height : 390,
					viewConfig : {
						forceFit : true
					}
				});
		// gridPanel.body.setOverflow("scroll");
		var rightPanel = new Ext.Panel({
					columnWidth : .45,
					autoScroll : true,
					items : [gridPanel]
				})

		/** ************ */
		/* 添加删除按钮 */
		/** ************ */
		var buttonPanel = new Ext.Panel({
			columnWidth : .1,
			border : false,
			layout : 'table',
			layoutConfig : {
				columns : 1
			},
			bodyStyle : 'padding: 160px 0px 0px 0px',
			height : 400,
			items : [{
						layout : 'form',
						border : false,
						autoWidth : true,
						bodyStyle : 'padding: 0px 10px 10px 10px',
						items : [{
									xtype : 'button',
									text : '增加',
									width : 50,
									handler : addProtocol
								}]
					}, {
						layout : 'form',
						border : false,
						autoWidth : true,
						bodyStyle : 'padding: 0px 10px 10px 10px',
						items : [{
									xtype : 'button',
									text : '删除',
									width : 50,
									handler : function() {
										var selectProtocol = sm.getSelections();
										delComItem();
										store.remove(selectProtocol);
									}
								}]
					}]
		})

		/** ******** */
		/* 中心面板 */
		/** ******** */
		var centerPanel = new Ext.Panel({
					layout : 'form',
					region : 'center',
					border : false,
					bodyStyle : 'padding:0px 10px 0px 0px',
					items : [{
								layout : 'column',
								border : false,
								items : [tree, buttonPanel, rightPanel]
							}]
				})

		/** ******** */
		/* 组合名称 */
		/** ******** */
		var groupNameText = new Ext.Panel({
					border : false,
					layout : 'form',
					labelAlign : 'right',
					labelWidth : 80,
					autoScroll : false,
					width : 330,
					hidden : true,
					// labelWidth : 50,
					items : [{
								xtype : 'textfield',
								name : 'groupName',
								id : "dataFetch_groupName",
								fieldLabel : '组合名称',
								allowBlank : false,
								blankText : '请输入组合名称',
								anchor : '90%',
								// labelWidth : 180,
								// width:250,
								regex : /^[\w\u2E80-\u9FFF]+$/,
								regexText : "格式错误，不能包含{,},=空格_-等符号",
								labelSeparator : '',
								listeners : {
									"change" : function(src, newValue, oldValue) {
										editCom(src, newValue, oldValue);
									}
								}
							}]
				})

		/** ******** */
		/* 是否共享 */
		/** ******** */
		var shareCheckbox = new Ext.Panel({
					border : false,
					layout : 'form',
					hideLabels : true,
					autoWidth : true,
					items : [{
								xtype : 'checkbox',
								name : 'shareFlag',
								id : "dataFetch_share",
								boxLabel : '是否共享',
								hidden : true,
								listeners : {
									"change" : function(src) {
										editCom(src);
									}
								}
							}]
				})

		/** ******** */
		/* 有效天数 */
		/** ******** */
		var lifeDayText = new Ext.Panel({
					border : false,
					layout : 'form',
					labelAlign : 'right',
					labelWidth : 80,
					autoScroll : false,
					hidden : true,
					width : 330,
					items : [{
								xtype : 'textfield',
								name : 'lifeDay',
								fieldLabel : '有效天数',
								id : "dataFetch_validays",
								allowBlank : false,
								anchor : '90%',

								blankText : '请输入有效天数',
								regex : /^\d+$/,
								maskRe : /^\d+$/,
								regexText : "请输入数字",
								labelSeparator : '',
								listeners : {
									"change" : function(src, newValue, oldValue) {
										editCom(src, newValue, oldValue);
									}
								}
							}]
				});

		/** ******** */
		/* 创建日期 */
		/** ******** */
		var createDatefield = new Ext.Panel({
					border : false,
					layout : 'form',
					labelAlign : 'right',
					labelWidth : 50,
					autoWidth : true,
					readOnly : true,
					items : [{
								xtype : 'textfield',
								readOnly : true,
								name : 'createDate',
								id : "dataFetch_createDate",
								anchor : '100%',
								hidden : true,
								hideLabel : true,
								fieldLabel : '创建日期',
								width : 90,
								labelSeparator : ''
							}]
				});

		/** ******** */
		/* 上部面板 */
		/** ******** */
		var northPanel = new Ext.Panel({
			layout : 'table',
			height : 100,
			border : false,
			region : 'north',
			layoutConfig : {
				columns : 2
			},
			height : 80,
			bodyStyle : 'padding:15px 0px 20px 0px',
			defaults : {

				listeners : {
					"change" : function() {
						editCom();
					}
				}
			},
			items : [groupNameText, shareCheckbox, lifeDayText, createDatefield]
		});

		/** ******** */
		/* 左部面板 */
		/** ******** */
		var smProtocolGroupWindow = new Ext.grid.RowSelectionModel({
			singleSelect : true,
			listeners : {
				"beforerowselect" : function() {
					Ext.getCmp("dataFetch_createDate").focus();
				},
				'rowselect' : function(sm, rowIndex, r) {
					groupNameText.show();
					lifeDayText.show();
					// Ext.getCmp("dataFetch_groupName").blur();
					loadValues(r);
					for (var key in warehouse) {
						var name = ParseUtils.getFirst(key, "newName");
						var staffNo = ParseUtils.getFirst(key, "staffNo");
						var self = ParseUtils.getFirst(key, "self");
						if (name == r.get("newName")
								&& (staffNo == r.get("staffNo") || self == "yes")) {
							var data = warehouse[key];
							store.removeAll();
							for (var i = 0; i < data.length; i++) {
								var oo = data[i];
								if (oo.status == "itemDel") {
									continue;
								}
								var r = new Ext.data.Record({
											"protocolName" : oo.protocolName,
											"protocolCode" : oo.clearProtNo,
											"combiId" : oo.combiId
										});
								store.add(r);
							}
						}
					}
				}
			}
		});
		/** ********************************** */
		/** *选择之后填充表单的值************** */
		/** ********************************* */
		function loadValues(r) {
			// dataFetch_groupName dataFetch_share dataFetch_validays
			// dataFetch_createDate
			Ext.getCmp("dataFetch_groupName").setValue(r.get("newName"));
			Ext.getCmp("dataFetch_share").setValue(r.get("isShare"));
			Ext.getCmp("dataFetch_validays").setValue(r.get("validDays"));
			Ext.getCmp("dataFetch_createDate").setValue(r.get("createDate"));
			var staffNo = r.get("staffNo");
			// 如果不是自己的
			if (r.get("self") == "no") {
				Ext.getCmp("dataFetch_groupName").disable();
				Ext.getCmp("dataFetch_share").disable();
				Ext.getCmp("dataFetch_validays").disable();
				Ext.getCmp("dataFetch_createDate").disable();
				Ext.getCmp("dataFetch_right").disable();

			} else {
				Ext.getCmp("dataFetch_right").enable();
				Ext.getCmp("dataFetch_groupName").enable();
				Ext.getCmp("dataFetch_share").enable();
				Ext.getCmp("dataFetch_validays").enable();
				Ext.getCmp("dataFetch_createDate").enable();
			}
		}

		/** ***************** */
		/* 管理所涉及到的一些处理方法 */
		// warehouse storeProtocolGroupWindow store gridProtocolGroupWindow
		// gridPanel
		/** ***************** */

		/** ******验证是不是有重名的项目********* */
		function isEcho(name) {
			var key = findKeyByName(name);
			if (key) {
				return true;
			}
			return false;
		}

		// 将一个对象转化为符合提交的数据格式
		function convertCommit(o) {
			var str = ["{"];
			for (var key in o) {

				if (typeof o[key] != "function") {
					// if(o[key].indexOf(" ")>0){
					// continue;
					// }
					if (key == "protocolName") {
						continue;
					}
					str.push(key);
					str.push("=");
					str.push(o[key]);
					str.push(",");
				}
			}
			str.pop();
			str.push("}");
			return str.join("");
		}
		// alert(convertCommit({a:"aa",b:"虎",c:function(){},d:"靠"}));
		/** **编辑组合项*** */
		function editCom(src, newValue, oldValue) {
			var r = gridProtocolGroupWindow.getSelectionModel().getSelected();
			if (!r) {
				return !!Ext.Msg.alert("错误", "请选择编辑项");
			}
			if (!src.validate()) {
				src.setValue(oldValue);
				return Ext.Msg.alert("错误的输入格式");
			}
			// if(r.get("status")=="combiAdd"){
			// r.set("newName", Ext.getCmp("dataFetch_groupName").getValue());
			// r.set("combiName", Ext.getCmp("dataFetch_groupName").getValue());
			// r.set("isShare", Ext.getCmp("dataFetch_share").getValue());
			// r.set("validDays", Ext.getCmp("dataFetch_validays").getValue());
			// return;
			// }
			// r.set("createDate",
			// Ext.getCmp("dataFetch_createDate").getRawValue());
			var newName = Ext.getCmp("dataFetch_groupName").getValue();
			var key = findKeyByName(r.get("newName"));
			if (!key) {
				return !!Ext.Msg.alert("错误", "组合" + r.get("combiName")
								+ "可能已经被删除");
			}
			var status = ParseUtils.getFirst(key, "status");
			if (src == Ext.getCmp("dataFetch_groupName") && isEcho(newName)) {
				src.setValue(oldValue);
				return !!Ext.Msg.alert("警告", "存在重名的组合名称");
			}
			r.set("newName", newName);
			r.set("isShare", Ext.getCmp("dataFetch_share").getValue());
			r.set("validDays", Ext.getCmp("dataFetch_validays").getValue());
			var keyNew = ParseUtils.update(key, "newName", Ext
							.getCmp("dataFetch_groupName").getValue());
			var keyNew = ParseUtils.update(keyNew, "isShare", Ext
							.getCmp("dataFetch_share").getValue());
			var keyNew = ParseUtils.update(keyNew, "validDays", Ext
							.getCmp("dataFetch_validays").getValue());
			var keyNew = ParseUtils.update(keyNew, "status", "combiEdit");
			warehouse[keyNew] = warehouse[key];
			warehouse[key] = null;
			delete warehouse[key];
		}
		/** ******通过名称在仓库里面得到值************** */
		function findKeyByName(name) {
			for (var key in warehouse) {
				var self = ParseUtils.get(key, "self");
				if (self == "no") {
					continue;
				}
				var cname = ParseUtils.get(key, "newName");
				if (cname == name) {
					return key;
				}
			}
			return null;
		}
		/** **增加组合的一个小项*** */
		function addComItem() {
			var node = tree.getSelectionModel().getSelectedNode()
			var clearNo = node.id;
			var r = gridProtocolGroupWindow.getSelectionModel().getSelected();
			if (!r) {
				return;
			}
			var arr = findByComName(r.get("newName"));
			if (arr == null) {
				return !!Ext.Msg.alert("错误", "选择的项可能已经被删除");
			}
			arr.push({
						clearProtNo : clearNo,
						status : "itemAdd",
						protocolName : node.text
					});
		}
		/** **删除组合项中的一个或者多个小项*** */
		function delComItem() {
			var node = Ext.getCmp("dataFetch_right").getSelectionModel()
					.getSelections();
			if (!node) {
				return !!Ext.Msg.alert("错误", "选择的项可能已经被删除");
			}
			var r = gridProtocolGroupWindow.getSelectionModel().getSelected();
			var arr = findByComName(r.get("newName"));
			if (arr == null) {
				return !!Ext.Msg.alert("错误", "选择的项可能已经被删除");
			}

			// storeProtocolGroupWindow
			for (var i = 0; i < arr.length; i++) {
				var o = arr[i];
				for (var j = 0; j < node.length; j++) {
					var n = node[j];
					if (o.clearProtNo == n.get("protocolCode")) {
						if (o.status == "itemAdd") {
							arr.remove(o);
							i--;
						} else {
							o.status = "itemDel";
						}
					}
				}
			}
		}
		/** **删除组合项*** */
		function delCom() {
			var r = gridProtocolGroupWindow.getSelectionModel().getSelected();

			var key = findKeyByName(r.get("newName"));
			var newKey = ParseUtils.update(key, "status", "combiDel");
			warehouse[newKey] = [];
			delete warehouse[key];
			storeProtocolGroupWindow.remove(r)
			gridProtocolGroupWindow.getSelectionModel().selectLastRow();
		}
		/** *****通过组合名称来查找输入自己的小项列表,如果没有找到返回空******* */
		function findByComName(name) {
			for (var key in warehouse) {
				var cname = ParseUtils.get(key, "newName");
				var self = ParseUtils.get(key, "self");
				if (self == "no") {
					continue;
				}
				if (cname == name) {
					return warehouse[key];
				}
			}
			return null;
		}

		/** *******新增一个召测组合项********* */
		function addNewCom() {
			var r = new Ext.data.Record({
						"combiName" : "",
						"staffNo" : "",
						"isShare" : 0,
						"validDays" : 900,
						"self" : "yes",
						"status" : "combiAdd",
						"createDate" : new Date().format("Y-m-d"),
						"newName" : ""
					});
			storeProtocolGroupWindow.add(r);
			// 生成一个key,key的值是个数组
			// {validDays=1234, status=combiAdd, self=yes, combiName=a,
			// newName=a,
			// isShare=1}
			var name = getValidName();
			r.set("newName", name);
			r.set("combiName", name);
			var key = "{combiName=" + name + ",newName=" + name
					+ ",self=yes,status=combiAdd,validDays=900,isShare=0,"
					+ "createDate=" + new Date().format("y-m-d") + "}";
			warehouse[key] = [];
			gridProtocolGroupWindow.getSelectionModel().selectLastRow();

		}
		/** *******得到一个不重复的组合的名称************ */
		function getValidName() {
			var name = 'new1';
			var i = findKeyByName(name);
			while (i) {
				name = name + "1";
				i = findKeyByName(name);
			}
			return name;
		}
		/** ************************************* */
		var cmProtocolGroupWindow = new Ext.grid.ColumnModel([{
					dataIndex : 'newName',
					width : 210,
					align : 'left',
					menuDisabled : true
				}]);
		// Ext.data.ArrayStore;
		var storeProtocolGroupWindow = new Ext.data.ArrayStore({
			fields : [{
						name : "combiName"
					}, {
						name : "staffNo"
					}, {
						name : "validDays"
					}, {
						name : "isShare"
					}, {
						name : "newName"
					}, {
						name : "createDate"
					}, {
						name : "self"
					}]
				// autoLoad : true,
				// proxy : new Ext.data.HttpProxy( {
				// url : 'baseapp/dataFetch!queryProtocolGroup.action'
				// }),
				// reader : new Ext.data.JsonReader( {
				// root : 'protocolGroupDTOList'
				// }, [ {
				// name : 'combiName'
				// }, {
				// name : 'staffNo'
				// }, {
				// name : 'createDate'
				// }, {
				// name : 'validDays'
				// }, {
				// name : 'isShare'
				// }, {
				// name : 'combiMap'
				// } ])

			});
		var gridProtocolGroupWindow = new Ext.grid.GridPanel({
					border : false,
					hideHeaders : true,
					stripeRows : true,
					store : storeProtocolGroupWindow,
					cm : cmProtocolGroupWindow,
					sm : smProtocolGroupWindow,
					// width : 190,
					autoWidth : true,
					height : 489,
					viewConfig : {
						forceFit : true
					}
				});
		var panelProtocolGroupWindow = new Ext.Panel({
					border : true,
					// width : 190,
					autoWidth : true,
					height : 460,
					items : [gridProtocolGroupWindow],
					tbar : [{
								xtype : 'label',
								text : '召测组合',
								style : 'font-weight:bold;color:#15428b'
							}]
				});
		var westPanel = {
			border : false,
			region : 'west',
			width : 210,
			bodyStyle : 'padding:10px 10px 0px 10px',
			items : [panelProtocolGroupWindow]
		}

		/** ******** */
		/* 表单面板 */
		/** ******** */
		var protocolFormPanel = new Ext.form.FormPanel({
			layout : 'border',
			border : false,
			region : 'center',
			items : [westPanel,
					// southPanel,
					{
				layout : 'border',
				border : false,
				region : 'center',
				buttonAlign : 'center',
				footerCfg : {
					// cls : "whiteback",
					id : "df_footer_id",
					style : "background-color:white"
				},
				items : [northPanel, centerPanel],
				buttons : [{
							xtype : 'button',
							text : "增加",
							handler : function() {
								addNewCom();
							}
						}, {
							xtype : 'button',
							text : "删除",
							handler : function() {
								delCom();
							}
						}, {
							xtype : 'button',
							text : '保存',
							width : 80,
							handler : function() {
								// 参数
								var params = [];
								// 处理数据仓库，对数据仓库进行处理，组装成字符串到后台处理
								for (var key in warehouse) {
									var oo = warehouse[key];

									var combiName = ParseUtils.getFirst(key,
											"combiName");
									var self = ParseUtils.getFirst(key, "self");
									var status = ParseUtils.getFirst(key,
											"status");
									var flat = false;
									if (oo) {
										for (var i = 0; i < oo.length; i++) {
											if (oo[i].status == "db"
													|| oo[i].status == "itemAdd") {
												flat = true;
												break;
											}
										}
									}
									if (status != "combiDel" && !flat) {

										return !!Ext.Msg.alert("错误", "组合:"
														+ ParseUtils.getFirst(
																key, "newName")
														+ "未添加项");
									}
									if (status == "combiEdit"
											|| status == "combiDel") {
										params.push(key);
									}

									var dataItems = warehouse[key];
									for (var i = 0; i < dataItems.length; i++) {
										var item = dataItems[i];
										if (item.status == "db") {
											continue;
										}
										var p = convertCommit(item);
										var n = ParseUtils.getFirst(key,
												"newName");
										p = ParseUtils.addDirect(p,
												"combiName", n);
										params.push(p);
									}
								}
								// if (protocolFormPanel.form.isValid()) {
								protocolFormPanel.form.submit({
									url : 'baseapp/dataFetch!doEdit.action',
									method : 'post',
									params : {
										// protocolArray : protocolArray
										cmds : params
									},
									clientValidation : false,
									success : function() {
										storeProtocolGroup.load({
											callback : function() {
												var queryType = Ext
														.getCmp("queryType_group")
														.getValue().inputValue;
												filterCom(queryType,
														storeProtocolGroup);
												groupWindow.close();
											}
										});
									},
									failure : function(response) {
										return;
									},
									waitTitle : '请稍后',
									waitMsg : '正在保存透明规约组合...'
								})

							}
						}, {
							xtype : 'button',
							text : '关闭',
							width : 80,
							handler : function() {
								groupWindow.close();
							}
						}]
			}]
		})

		/** ****************** */
		/** ***************** */
		/* 组合管理窗口初始化 */
		/** ***************** */
		var groupWindow = new Ext.Window({
					title : title || "组合管理",
					width : 900,
					height : 550,
					layout : 'border',
					modal : true,
					resizable : false,
					items : [protocolFormPanel]
				});
		/** ********************** */
		/** *******删除一个召测组合**************** */
		/** ********************** */
		/** ** 一个存放数据的的仓库** */
		/** ********************** */
		var warehouse = {};
		Ext.Ajax.request({
			url : "baseapp/dataFetch!findStructure.action",
			success : function(resp) {
				storeProtocolGroupWindow.removeAll();
				var queryType = Ext.getCmp("queryType_group").getValue().inputValue;
				var o = Ext.decode(resp.responseText);
				for (var key in o.combiMap) {
					var dataGroup = ParseUtils.getFirst(key, "dataGroup");
					var str = Ext.encode(o.combiMap[key]);
					var self = ParseUtils.getFirst(key, "self");
					if (self == "no") {
						continue;
					}
					if (queryType == 1) {
						if (str.indexOf('"dataGroup":2') > 0) {
							continue;
						}

					} else if (queryType == 2) {
						if (str.indexOf('"dataGroup":1') > 0) {
							continue;
						}
					} else {

					}
					// alert(ParseUtils.getFirst("{staffNo=test,combiName=1好}","combiName"));
					var r = new Ext.data.Record({
								"combiName" : ParseUtils.getFirst(key,
										"combiName"),
								"staffNo" : ParseUtils.getFirst(key, "staffNo"),
								"isShare" : ParseUtils.getFirst(key, "isShare"),
								"validDays" : ParseUtils.getFirst(key,
										"validDays"),
								"self" : ParseUtils.getFirst(key, "self"),
								"createDate" : ParseUtils.getFirst(key,
										"createDate"),
								"newName" : ParseUtils.getFirst(key, "newName")
							});
					storeProtocolGroupWindow.add(r);
				};
				warehouse = o.combiMap;
				var queryType = Ext.getCmp("queryType_group").getValue().inputValue;
				if (queryType == 1) {
					groupWindow.setTitle("管理一类数据组合");
				} else if (queryType = 2) {
					groupWindow.setTitle("管理二类数据组合");
				}
				groupWindow.show();
			}
		});
		// storeProtocolGroupWindow 召测组合的store combiName

	}
	/** ********************************************************************************************** */

	/**
	 * ***************************************** 渲染页面
	 * *******************************************
	 */
	/** **** */
	/* 表单 */
	/** **** */
	var formPanel_dataFetch = new Ext.form.FormPanel({
				border : false,
				layout : 'border',
				items : [fetchPanel, terminalGrid]
			});
	/** **************************************************** */
	/* 工具 */
	/** *************************************************** */
	/* 在类似{key1=value1,key1=value1,key4=value3,key2=value2} */
	/* 字符串中取数据** */
	var ParseUtils = {
		get : function(input, key) {
			var p = new RegExp("[\\{\\,\\s]" + key
							+ "=([\\w\u2E80-\u9FFF\\-]+)", "g");
			var r;
			var arr = [];
			while ((r = p.exec(input))) {
				arr.push(RegExp.$1);
			}
			return arr;
		},
		getFirst : function(input, key) {
			var p = new RegExp("[\\{\\,\\s]" + key
							+ "=([\\w\u2E80-\u9FFF\\-]+)", "g");
			p.exec(input);
			return RegExp.$1;
		},
		// 增加一个值如果原来没有
		add : function(input, key, value) {

			if (!this.exists(input, key)) {
				return addDirect(input, key, value);
			}

		},
		exists : function(input, key) {
			var p = new RegExp("[\\{\\,\\s]" + key
							+ "=([\\w\u2E80-\u9FFF\\-]+)", "g");
			return !!p.exec(input);
		},
		addDirect : function(input, key, value) {
			return input.replace(/}/g, "," + key + "=" + value + "}");
		},

		update : function(input, key, value) {
			var p = new RegExp("[\\{\\,\\s]" + key
							+ "=([\\w\u2E80-\u9FFF\\-]+)", "g");
			p.exec(input);
			return input.replace(key + "=" + RegExp.$1, key + "=" + value);
		}
	};
	// alert(ParseUtils.update("{validDays=1234, status=db, staffNo=test,
	// self=yes,
	// combiName=测试组合5, newName=测试组合5, isShare=1,
	// createDate=2010-01-29}","newName","虎"));
	// alert(ParseUtils.getFirst("{a=3黄轩,b=2,a=4,bb=33,g=5}","a"));
	// alert(ParseUtils.addDirect("{a=3,b=2,a=4,bb=33,g=5}","gg","b"));
	/** ************************************************* */
	/* 召测结果的显示 store grid中的store 通过此参数初始化 */
	/** ************************************************* */
	// 对较长的数据进行分行显示
	function createBr(v, l) {
		var rs = [];
		for (var i = 0; i < v.length; i++) {
			rs.push(v[i]);
			if (i % l == l-1) {
				rs.push("<br />");
			}
		}
		return rs.join("");
	}
	window.resultWin = function(msg) {
		// 克隆一个对象
		function clone(obj) {
			var m = {};
			for (k in obj) {
				m[k] = obj[k];
			}
			return m;
		}
		// 通过那个变量的值对数据进行过滤
		function filter(obj, fmrAssetNo, assetNoM) {
			var result = {};
			Ext.iterate(obj, function(k, v) {
						var flat = true;
						if (fmrAssetNo == "终端") {
							if (v["frmAssetNo"] != "终端数据")
								flat == false;
						} else {
							fmrAssetNo && (function() {
								if (v["frmAssetNo"] != fmrAssetNo) {
									flat = false;
								}
							})();
						}
						assetNoM && (function() {
							if (v["assetNoM"] != assetNoM) {
								flat = false;
							}
						})();
						if (flat) {
							result[k] = v;
						}
					})
			return result;
		}

		// storeTerminalGrid fetchState terminalGrid
		// dataResult
		// 得到当前所选项的终端资产号
		var userRc = terminalGrid.getSelectionModel().getSelected();
		if (!userRc) {
			return Ext.Msg.alert("错误", "请选择用户");
		}
		var fs = [{
					name : "org_name"
				}, {
					name : "cons_no"
				}, {
					name : "cons_name"
				}, {
					name : "showName"
				}, {
					name : "value"
				}, {
					name : "pn"
				}, {
					name : "dataTypeName"
				}, {
					name : "unit"
				}, {
					name : "errorCode"
				}, {
					name : "funCode"
				}, {
					name : "time"
				}, {
					name : "consNo"
				}, {
					name : "name"
				}, {
					name : "consName"
				}, {
					name : "bigName"
				}, {
					name : "group"
				}, {
					name : "mpNo"
				}, {
					name : "mpName"
				}, {
					name : "assetNo"
				}, {
					name : "assetNoM"// 电能表资产号
				}, {
					name : "frmAssetNo"
				}];

		// 当前选择项的终端资产号
		var j = 0;
		var assetNo = userRc.get("tmnl_asset_no");
		var showData = choiceData(assetNo) || [];
		if (showData.length == 0) {
			return !!Ext.Msg.alert("提示", "未发现召测结果或者召测失败");
		}
		var cons_name = userRc.get("cons_name");
		var cons_no = userRc.get("cons_no");
		var org_name = userRc.get("org_name");
		// 测试数据

		var count = 0;
		var rs = [];
		var pns = {};
		// var flat = true;
		// rs.push({"pn":5,showName:"F22",value:"444",unit:"%",cons_name:"huangxuan"});
		for (var key in showData) {
			var o = showData[key];
			if (assetNo != o.tmnlAssetNo) {
				continue;
			}
			count++;
			// flat = false;
			o.assetNo = assetNo;
			o.org_name = org_name;
			o.cons_name = cons_name;
			o.cons_no = cons_no;
			rs.push(o);
			pns[o.pn] = true;
		}
		pns.t = [];
		for (k in pns) {
			k != "t" && pns.t.push(k);
		}
		pns = pns.t;

		// if (flat) {
		// return !!Ext.Msg.alert("提示", "未发现召测结果或者召测结果为空");
		// }
		var cm = new Ext.grid.ColumnModel([{
			header : "供电单位",
			dataIndex : "org_name",
			renderer : function(v) {
				var html = '<span ext:qtitle="供电单位" ext:qtip="' + v + '">' + v
						+ '</span>';
				return html;
			}
		}, {
			header : "采集器资产号",
			dataIndex : "frmAssetNo",
			renderer : function(v) {
				var html = '<span ext:qtitle="采集器资产号" ext:qtip="' + v + '">'
						+ v + '</span>';
				return html;
			}
		}, {
			header : "用户编号",
			dataIndex : "cons_no",
			renderer : function(v, m, r) {
				if (r.get("consNo")) {
					v = r.get("consNo");
				}
				var html = '<span ext:qtitle="用户编号" ext:qtip="' + v + '">' + v
						+ '</span>';
				return html;
			}
		}, {
			header : "用户名称",
			dataIndex : "cons_name",
			renderer : function(v, m, r) {
				if (r.get("consName")) {
					v = r.get("consName");
				}
				var html = '<span ext:qtitle="用户名称" ext:qtip="' + v + '">' + v
						+ '</span>';
				return html;
			}
		}, {
			header : "电能表资产号",
			dataIndex : "assetNoM",
			width : 100,
			renderer : function(v) {
				if (!v) {
					return "";
				}
				var html = '<span ext:qtitle="电能表资产号" ext:qtip="' + v + '">'
						+ v + '</span>';
				return html;
			}
		}, {
			header : "计量点编号",
			dataIndex : "mpNo",
			renderer : function(v) {
				if (!v) {
					return "";
				}
				var html = '<span ext:qtitle="计量点编号" ext:qtip="' + v + '">' + v
						+ '</span>';
				return html;
			}
		}, {
			header : "计量点名称",
			dataIndex : "mpName",
			renderer : function(v) {
				if (!v) {
					return "";
				}
				var html = '<span ext:qtitle="计量点名称" ext:qtip="' + v + '">' + v
						+ '</span>';
				return html;
			}
		}, {
			header : "测量点/总加组",
			dataIndex : "pn",
			width : 100,
			renderer : function(v) {
				var html = '<span ext:qtitle="测量点/总加组" ext:qtip="' + v + '">'
						+ v + '</span>';
				return html;
			}
		}, {
			header : "召测项",
			dataIndex : "showName",
			renderer : function(v) {
				var html = '<span ext:qtitle="召测项" ext:qtip="' + v + '">' + v
						+ '</span>';
				return html;
			}
		}, {
			header : "召测值",
			dataIndex : "value",
			renderer : function(v, m, r) {
				if (v.length == 0) {
					v = "数据项为空";
					return '<span ext:qtitle="召测值" ext:qtip="' + v + '">' + v
							+ '</span>';
				}
				if ((v + "").indexOf("-9999") >= 0) {
//					v = "终端无数据";
//					return '<span ext:qtitle="召测值" ext:qtip="' + v + '">' + v
//							+ '</span>';
					return "";
				}
				if (typeof v == "string" && v.indexOf('T') > 0
						&& v.indexOf('-') > 0) {
					v = v.replace(/-/g, "/");
					v = v.replace(/T/g, " ");
					v = new Date(v).format("Y年m月d日 H:i:s");
				} else {
					v = v + (r && r.get("unit") || "");
				}
				//var vv = createBr(v, 30);
				var html = '<div ext:hide="false" ext:qtitle="召测值" ext:qtip="'+ '<textarea style=font-size:12px;>'+ v +'</textarea>'+ '" >' + v
						+ '</div>';
				return html;
			}
		}, {
			header : "数据类型",
			dataIndex : "dataTypeName",
			width : 80,
			hidden : true
		}, {
			header : "数据单位",
			dataIndex : "unit",
			hidden : true,
			width : 80
		}, {
			header : "是否成功",
			dataIndex : "errorCode",
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
			dataIndex : "time",
			renderer : function(v) {
				if (!v) {
					return "结果不含时间数据";
				}
				v = v.replace(/-/g, "/");
				v = v.replace(/T/g, " ");
				v = new Date(v).format("Y年m月d日 H:i:s");
				var html = '<span ext:qtitle="时间" ext:qtip="' + v + '">' + v
						+ '</span>';
				return html;
			}
		}, {
			header : "",
			hidden : true,
			dataIndex : "group",
			width : 300,
			groupRenderer : function(v, unused, r) {
				if (!v) {
					return ""
				}
				v = r.get("bigName") + ":计量点" + r.get("pn") + "的数据";
				var html = '<span ext:qtitle="时间" ext:qtip="' + v + '">' + v
						+ '</span>';
				return html;
			}
		}]);
		function getBigName(str) {
			var str1 = str.substr(0, str.length - 1);
			var str2 = str.substr(0, str.length - 2);
			return bigCodeToName[str1 + "F"] || bigCodeToName[str2 + "FF"]
		};
		var win;
		Ext.Ajax.request({
			url : "baseapp/dataFetch!findTmnlPnCons.action",
			params : {
				tmnlNo : assetNo,
				pns : pns
			},
			success : function(resp) {
				var o = Ext.decode(resp.responseText);
				meterName = o.meterName;
				comboData = o.comboData;
				// 对rs进一步进行处理
				Ext.each(rs, function(r) {
							if (!meterName) {
								r.consNo = "终端数据";
								r.consName = "终端数据";
								r.mpNo = "终端数据";
								r.mpName = "终端数据";
								r.assetNoM = "终端数据";
								r.frmAssetNo = "终端数据"
							} else {
								var temp = meterName[r["assetNo"] + "_"
										+ r["pn"]]
										|| {};
								r.consNo = temp.consNo || "终端数据";
								r.consName = temp.consName || "终端数据";
								r.mpNo = temp.mpNo || "终端数据";
								r.mpName = temp.mpName || "终端数据";
								r.assetNoM = temp.assetNoM || "终端数据";
								r.frmAssetNo = temp.frmAssetNo || "终端数据";
								// r.assetNo = meterName[r["assetNo"] +
								// "_" +
								// r["pn"]].assetNo;
							}
							r.bigName = getBigName(r["name"]);
							// 通过大项的名称和测量点组合在作为分组的条件
							r.group = r.bigName + "_" + r.pn;
						});
				var configGrid = {
					cm : cm,
					border : false,
					region : "center",
					layout : "fit",
					width : 900,
					viewConfig : {
						forceFit : false,
						emptyText : "没有找到相关联的数据"
					},
					stripeRows : true,
					height : 320,
					monitorResize : true
				};

				var storeRs = new Ext.ux.LocalStore({
							autoId : true,
							fields : fs
						});
				configGrid.bbar = new Ext.ux.MyToolbar({
							store : storeRs
						});
				configGrid.store = storeRs;

				var resultGrid = new Ext.grid.GridPanel(configGrid);
				// storeRs.clearAll();
				storeRs.addDatas(rs);

				// 得到当前数据的备份
				var bakData = clone(storeRs.mainData.baseData);
				var comboStoreFrm = [];
				var comboStorePn = [];
				Ext.iterate(comboData, function(a) {
							comboStoreFrm.push(a);
						});
				var comboxOne = new Ext.form.ComboBox({
							border : false,
							displayField : "frmAssetNo",
							valueField : "frmAssetNo",
							mode : "remote",
							triggerAction : 'all',
							selectOnFocus : true,
							emptyText : "请选择采集点资产号",
							store : comboStoreFrm,
							// store:[['1','test'],['2','test2']],
							fieldLabel : "采集器资产号",
							listeners : {
								change : function(cb, now, old) {
									comboStorePn = comboData[this.getValue()]
											|| [];
									comboTwo.setValue("");
									comboTwo.bindStore(comboStorePn);
								}
							}
						});

				var comboTwo = new Ext.form.ComboBox({
							mode : "remote",
							forceSelection : true,
							lazyRender : true,
							triggerAction : 'all',
							selectOnFocus : true,
							hideTrigger : false,
							displayField : "assetNo",
							valueField : "assetNo",
							store : comboStorePn,
							emptyText : "请选择电能表资产号",
							fieldLabel : "电能表资产号"
						});
				var top = new Ext.Panel({
							border : false,
							height : 50,
							region : "north",
							layout : "column",
							bodyStyle : "padding:10px 0 0 5px",
							items : [{
										layout : "form",
										border : false,
										columnWidth : .5,
										labelWidth : 80,
										labelAlign : "rigth",
										items : [comboxOne]
									}, {
										layout : "form",
										border : false,
										columnWidth : .5,
										labelWidth : 85,
										labelAlign : "rigth",
										items : [comboTwo]
									}, {
										border : false,
										width : 200,
										items : [{
											xtype : "button",
											width : 80,
											text : "查询",
											handler : function() {
												var fmrAssetNo = Ext
														.isEmpty(comboxOne
																.getValue())
														? undefined
														: comboxOne.getValue();
												var assetNoM = Ext
														.isEmpty(comboTwo
																.getValue())
														? undefined
														: comboTwo.getValue();
												storeRs.mainData.baseData = filter(
														bakData, fmrAssetNo,
														assetNoM);
												storeRs.loadCurrent();
											}
										}]
									}]
						});
				Ext.apply(resultGrid, rendererConfig);
				win = new Ext.Window({
							title : msg || "召测结果",
							border : false,
							resizable : true,
							closeAction : "close",
							modal : true,
							// height : 400,
							width : 915,
							height : 400,
							layout : "fit",
							viewConfig : {
								forceFit : true
							},
							items : [{
										xtype : "panel",
										border : false,
										layout : "border",
										items : [top, resultGrid]
									}],
							buttons : [{
										text : "关闭",
										handler : function() {
											win.close();
										}
									}]
						});
				win.show();
				// comboStorePn.load({
				// callback:function(){
				// win.show();
				// }
				// });

			}
		});

	};

	/** ******** */
	/* 渲染页面 */
	/** ******** */
	storeProtocolGroup.load({
				callback : function() {
					filterCom(1, storeProtocolGroup);
				}
			});

	startDate.setVisible(false);
	endDate.setVisible(false);
	/** ********** */
	/** *生成一个终端对应的计量点的窗口* */
	window.tmnlWin = function(msg) {
		var t = terminalGrid.getSelectionModel().getSelected()
		var queryType = Ext.getCmp("queryType_group").getValue().inputValue;
		if (queryType == 3) {
			return !!Ext.Msg.alert("警告", "事件数据请直接进行召测");
		}
		var tmnlNo = t.get("tmnl_asset_no");
		var sm = new Ext.grid.CheckboxSelectionModel({});
		var cm = new Ext.grid.ColumnModel([sm, {
			header : "用户名称",
			dataIndex : "consName",
			align : "center",
			renderer : function(v, m, r) {
				if (!v) {
					return "";
				}
				var html = '<span ext:qtitle="用户名称" ext:qtip="' + v + '">' + v
						+ '</span>';
				return html;
			}
		}, {
			header : "用户编号",
			dataIndex : "consNo",
			align : "center",
			renderer : function(v, m, r) {
				if (!v) {
					return "";
				}
				var html = '<span ext:qtitle="用户编号" ext:qtip="' + v + '">' + v
						+ '</span>';
				return html;
			}
		}, {
			header : "计量点编号",
			dataIndex : "mpNo",
			renderer : function(v, m, r) {
				if (!v) {
					return "";
				}
				var html = '<span ext:qtitle="计量点编号" ext:qtip="' + v + '">' + v
						+ '</span>';
				return html;
			}
		}, {
			header : "计量点名称",
			dataIndex : "mpName",
			align : "center",
			renderer : function(v, m, r) {
				if (!v) {
					return "";
				}
				var html = '<span ext:qtitle="计量点名称" ext:qtip="' + v + '">' + v
						+ '</span>';
				return html;
			}
		}, {
			header : "电能表资产号",
			dataIndex : "assetNo",
			renderer : function(v, m, r) {
				if (!v) {
					return "";
				}
				var html = '<span ext:qtitle="电能表资产号" ext:qtip="' + v + '">'
						+ v + '</span>';
				return html;
			}
		}, {
			header : "测量点",
			dataIndex : "mpSn",
			align : "center",
			renderer : function(v, m, r) {
				var html = '<span ext:qtitle="测量点" ext:qtip="' + v + '">' + v
						+ '</span>';
				return html;
			}
		}, {
			header : "采集器资产号",
			dataIndex : "fmrAssetNo",
			align : "center",
			renderer : function(v, m, r) {
				if (!v) {
					return " ";
				}
				var html = '<span ext:qtitle="采集器资产号" ext:qtip="' + v + '">'
						+ v + '</span>';
				return html;
			}
		}]);

		var gstore = new Ext.data.JsonStore({
					url : "baseapp/dataFetch!findTmnlCmp.action",
					root : "resultMap",
					fields : ["mpNo", "mpName", "mpSn", "tmnlAssetNo",
							"assetNo", "consName", "fmrAssetNo", "consNo"],
					totalProperty : "usrCount"
				});

		var grid = new Ext.grid.GridPanel({
					border : false,
					autoScroll : true,
					region : "center",
					cm : cm,
					sm : sm,
					store : gstore,
					bbar : new Ext.ux.MyToolbar({
								store : gstore
							})
				});
		gstore.load({
					params : {
						"edataFinder.tmnlAssetNo" : tmnlNo,
						start : 0,
						limit : 50
					},
					callback : function() {
						var c = setTimeout(function() {
									grid.getSelectionModel().selectFirstRow();
									clearTimeout(c);
								}, 300);
					}
				});
		function doDataFetch(btn) {
			var me = this;
			var params = {
				tmnlAssetNos : [tmnlNo],
				tmnlNo : tmnlNo,
				allPns : this.allPns,
				point : [],
				queryType : queryType
			};
			var r = gridProtocolGroup.getSelectionModel().getSelected();
			if (!r) {
				Ext.Msg.alert("警告", "必须要选择一项召测组合");
				this.ownerCt.ownerCt.close();
				return;
			}
			var sd = startDate.findByType("datefield")[0];
			var ed = endDate.findByType("datefield")[0];
			if (queryType != 1) {
				if (!sd.isValid()) {
					Ext.Msg.alert("警告", "开始时间不能为空");
					this.ownerCt.ownerCt.close();
					return;
				}
				if (!ed.isValid()) {
					Ext.Msg.alert("警告", "结束时间不能为空");
					this.ownerCt.ownerCt.close();
					return;
				}
				// 当是历史数据的时候，看看是不是时间在7天之内
				if (queryType == 2) {
					var in7 = dateInN(sd.getValue(), ed.getValue(), 7);
					if (!in7) {
						Ext.Msg.alert("警告", "请确保开始时间和结束在七天之内");
						this.ownerCt.ownerCt.close();
						return;
					}
				}

			}

			var gets = grid.getSelectionModel().getSelections();
			Ext.each(gets, function(d) {
						params.point.push(d.get("mpSn"));
					});
			if (params.point.length == 0) {
				return !!Ext.Msg.alert("警告", "请选择测量点");
			}
			// 填充时间
			params.startTime = sd.getValue();
			params.endTime = ed.getValue();
			// params.
			params.combiId = r.get("combiId");
			var ov = true;
			h_taskTime(200, function() {
						ov = false;
					});
			Ext.Ajax.request({
						timeout : 180000,
						url : 'baseapp/dataFetch!dataFetch.action',
						params : params,
						success : function(response) {
							if (!ov) {
								return;
							}
							overFlat = true;
							var action = Ext.decode(response.responseText);
							if (action.message && action.message != "") {
								ov = true;

								setTimeout(function() {
											Ext.Msg.alert("错误", action.message);
										}, 1500);
								return;
							}
							me.ownerCt.ownerCt.close();
							Ext.apply(dataState, action.errorMap);
							// 对所有的数据进行初始化部分进行清理
							Ext.apply(bigCodeToName, action.bigCodeToName);
							// dataQueryType = action.queryType;
							// 填充仓库
							// pushSingle(tmnlNo, action.resultMap);
							var mmm = {};
							mmm[tmnlNo] = action.resultMap;
							synModify(mmm, true);
							var showData = action.resultMap;
							// if (!showData || showData.length == 0) {
							// t.set("fetchState", "召测失败");
							// return;
							// }
							t.set("fetchState", undefined);
						}
					});
		}
		Ext.Ajax.request({
			url : "baseapp/dataFetch!findComboData.action",
			params : {
				tmnlNo : tmnlNo
			},
			success : function(response) {
				var o = Ext.decode(response.responseText);
				var comboData = o.comboData;
				var comboStoreFrm = [];
				var comboStorePn = [];
				Ext.iterate(comboData, function(a) {
							comboStoreFrm.push(a);
						});
				var comboxOne = new Ext.form.ComboBox({
							border : false,
							displayField : "frmAssetNo",
							valueField : "frmAssetNo",
							mode : "remote",
							triggerAction : 'all',
							selectOnFocus : true,
							emptyText : "请选择采集点资产号",
							store : comboStoreFrm,
							// store:[['1','test'],['2','test2']],
							fieldLabel : "采集器资产号",
							listeners : {
								change : function(cb, now, old) {
									comboStorePn = comboData[this.getValue()]
											|| [];
									comboTwo.setValue("");
									comboTwo.bindStore(comboStorePn);
								}
							}
						});

				var comboTwo = new Ext.form.ComboBox({
							mode : "remote",
							id : "result_pn_combo",
							forceSelection : true,
							lazyRender : true,
							triggerAction : 'all',
							selectOnFocus : true,
							hideTrigger : false,
							displayField : "assetNo",
							valueField : "assetNo",
							store : comboStorePn,
							emptyText : "请选择电能表资产号",
							fieldLabel : "电能表资产号"
						});
				var top = new Ext.Panel({
					border : false,
					height : 50,
					region : "north",
					layout : "column",
					bodyStyle : "padding:10px 0 0 5px",
					items : [{
								layout : "form",
								border : false,
								columnWidth : .5,
								labelWidth : 80,
								labelAlign : "rigth",
								items : [comboxOne]
							}, {
								layout : "form",
								border : false,
								columnWidth : .5,
								labelWidth : 80,
								labelAlign : "rigth",
								items : [comboTwo]
							}, {
								border : false,
								width : 100,
								items : [{
									xtype : "button",
									width : 80,
									text : "查询",
									handler : function() {
										gstore.baseParams = {
											"edataFinder.tmnlAssetNo" : tmnlNo,
											"edataFinder.assetNo" : comboTwo
													.getValue(),
											"edataFinder.fmrAssetNo" : comboxOne
													.getValue(),
											"edataFinder.isTmnl" : comboxOne
													.getValue() == "终端"
													&& 1
										};
										gstore.load({
													params : {
														start : 0,
														limit : 50
													}
												});
									}
								}]
							}]
				});
				var win = new Ext.Window({
							border : false,
							modal : true,
							layout : "border",
							width : 700,
							title : msg || "结果",
							height : 400,
							items : [top, grid],
							buttonAlign : "center",
							buttons : [{
										text : "召测全部",
										allPns : true,
										handler : doDataFetch
									}, {
										text : "召测",
										allPns : false,
										handler : doDataFetch
									}, {
										text : "关闭",
										handler : function() {
											this.ownerCt.ownerCt.close();
										}
									}]
						});
				win.show();
			}
		});

	}

	/* 左边树监听 */
	/** ********** */
	new LeftTreeListener({
		modelName : '数据召测',
		processClick : function(p, node, e) {
			var obj = node.attributes.attributes;
			var type = node.attributes.type;
			Ext.Ajax.request({
				url : "baseapp/dataFetch!dealTree.action",
				params : {
					start : 0,
					limit : 400,
					"node" : type == "usr"
							? ("tmnl_" + obj.tmnlAssetNo + "_" + obj.tmnlAssetNo)
							: node.id
				},
				success : function(response) {
					var o = Ext.decode(response.responseText);
					storeTerminalGrid.addDatas(o.resultMap);

				}
			});

		},
		processUserGridSelect : function(sm, row, r) {
			var tmnl = r.get("tmnlAssetNo");
			Ext.Ajax.request({
						url : "baseapp/dataFetch!dealTree.action",
						params : {
							start : 0,
							limit : 50,
							"node" : "tmnl_" + tmnl + "_01"
						},
						success : function(response) {
							var o = Ext.decode(response.responseText);
							storeTerminalGrid.addDatas(o.resultMap);
						}
					});
		}
	});

	// showErrorWin();
	/** ****使终端列表支持对使用renderer列的数据正常进行excel导出****** */
	Ext.apply(terminalGrid, rendererConfig);
	renderModel(formPanel_dataFetch, '数据召测');

	Ext.query("#df_gridProtocol div[class$=scroller]")[0].style.overflowY = "scroll";
	Ext.query("#" + gridProtocolGroup.getId() + " div[class$=scroller]")[0].style.overflowY = "scroll";
	function clearDirty() {
		dataResult = {};
		newData = undefined;
		dataState = {};
		clearSingle();
	}
	function openThis() {
		window.dataFetch_tmnl && Ext.Ajax.request({
					url : "baseapp/dataFetch!dealTree.action",
					params : {
						start : 0,
						limit : 50,
						"node" : "tmnl_" + window.dataFetch_tmnl + "_01"
					},
					success : function(response) {
						storeTerminalGrid.clearAll();
						var o = Ext.decode(response.responseText);
						storeTerminalGrid.addDatas(o.resultMap);
						clearDirty();
					}
				});
		window.dataFetch_tmnl = undefined;
	}
	Ext.getCmp('数据召测').on('activate', function() {
				openThis();
			})

	window.dataFetch_tmnl && !!openThis();

});
/** ********************************************************************************************** */
