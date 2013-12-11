

/**
 * author huangxuan
 */
Ext.onReady(function() {
	// 权限控制对象
	var powerObjs = [];
	// 当前激活的数据项
	function activateQueryData() {
		var tab = leftTabPanel.getActiveTab();
		if (tab == tabOne) {
			return {
				combox : typeCombox,
				radio : undefined,
				tab : tab,
				tree : treeOne,
				filter : oneFilter
			}
		} else if (tab == tabTwo) {
			return {
				combox : typeComboxTwo,
				radio : treeTwoRadio,
				tab : tab,
				tree : treeTwo,
				filter : twoFilter
			}
		} else if (tab == tabThree) {
			return {
				combox : typeComboxThree,
				radio : treeThreeRadio,
				tab : tab,
				tree : treeThree,
				filter : threeFilter
			}
		} else if (tab == tabFour) {
			return {
				combox : typeComboxFour,
				radio : treeFourRadio,
				tab : tab,
				tree : treeFour,
				filter : fourFilter
			}
		}
	}
	// 通过不同的activate tab 来过滤规约
	function activateFilter() {
		function getArr() {
			return [1, 2, 3];
		}
		var data = activateQueryData();
		var n = data.combox.getValue();
		var one = data.filter;
		one.clear();
		var inputValue = data.radio.getValue().inputValue;
		var arrCreate = getArr().remove(n);
		one.filterBy(function(tn) {
					var id = tn.id;
					var arr = id.split("_");
					var flat = true;
					if (arr[0] == "afn" && arr[1] == n) {
						flat = true;
					} else if (arr[0] == "afn"
							&& (arr[1] == arrCreate[0] || arr[1] == arrCreate[1])) {
						return false;
					}
					if (flat) {
						if (inputValue == -1) {
							return true;
						}

						if (arr[0] == "big") {
							var obj = tn.attributes.attributes;
							if (obj.isUsuaUse == inputValue || !obj.isUsuaUse) {
								return true;
							} else {
								return false;
							}
						}
					}
					return flat;
				});
	}
	// 六类用户的定义
	var userType = (function() {
		var dic = {
			"A" : "大型专变用户",
			"B" : "中小型专变用户",
			"C" : "三相一般工商业用户",
			"D" : "单相一般工商业用户",
			"E" : "居民用户",
			"F" : "公用配变考核计量点",
			"G" : "关口计量点用户"
		}
		return {
			get : function(str) {
				str = str.toUpperCase();
				if (!dic[str]) {
					return "";
				}
				return dic[str];
			}
		}
	})();

	var leftUserGrid = Ext.getCmp("leftUserGrid");
	// 属性区
	// 用于存放召测的数据
	var fetchData = [];
	var dateStateDict = {
		0 : "在规定的时间内缓存没有返回",
		1 : "终端不在线",
		3 : "成功",
		4 : "失败",
		8 : "终端返回为空值，被过滤"
	};
	// 当前激活的tree

	// 当前召测项的终端资产号内码
	// 当前选择的用户的对象,是一个record对象
	var currentUser = undefined;
	var emptyBean = {
		"typeName" : "",
		"meterId" : "",
		"consNo" : "",
		"tFactor" : "",
		"pt" : "",
		"usageTypeName" : "",
		"consType" : "",
		"consName" : "",
		"consTypeName" : "",
		"ct" : "",
		"mpSn" : "",
		"statusName" : "",
		"assetNo" : ""
	};

	// 一个原始报文的闭包
	var baowen = undefined;
	/** *方法区* */
	/** 得到总数** */
	function cnt(tmnlAssetNo) {
		Ext.Ajax.request({
					params : {
						tmnlAssetNo : tmnlAssetNo
					},
					url : "baseapp/dataFetchLone!findUserCnt.action",
					success : function(response) {
						var o = Ext.decode(response.responseText);
						userCntText.setValue(o.resultCount + "户");
					}
				});
	}
	// 通过不同的用户类型选择不同的召测tab页
	function activateType(type) {
		if (!type) {
			return;
		}
		var type = (type + "").toUpperCase();
		var a = {
			"A" : true,
			"B" : true,
			"F" : true
		};
		if (a[type]) {
			leftTabPanel.setActiveTab(tabTwo);
			return tabTwo;
		}
		a = {
			"C" : true
		};
		if (a[type]) {
			leftTabPanel.setActiveTab(tabThree);
			return tabThree;
		}
		a = {
			"D" : true,
			"E" : true
		};
		if (a[type]) {
			leftTabPanel.setActiveTab(tabFour);
			return tabFour;
		}
	}
	// 一个判断两个时间之差的方法是不是在n天之内
	function dateInN(start, end, n) {
		var start = start.clearTime();
		var end = end.clearTime().add(Date.DAY, 1);
		if (end >= start && start.add(Date.DAY, n) >= end) {
			return true;
		}
		return false;
	}
	/** **原始报文查询** */
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
	// 使用datafetch中的异步的框架进行数据召测
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
	// 数据处理
	// 发起召测任务
	function doFirstFn(params) {
		return function(s) {
			s.initKey();
			// s.params = params;
			params.key = s.key;
			// dataResult = {};
			fetchData = [];
			callBtn.disable();
			// dataState = {};
			// clearSingle();
			stateLabel.setText("<font color='red'>召测中....</font>", false);
			Ext.Ajax.un('requestexception', requestException);
			Ext.Ajax.request({
				timeout : 1500000,
				url : 'baseapp/dataFetch!dataFetch.action',
				params : params,
				success : function(response) {
					callBtn.enable();
					Ext.Ajax.on('requestexception', requestException);
					var o = Ext.decode(response.responseText);
					if (o.message && o.message.trim() != ""
							&& o.message != "over") {
						Ext.Msg.alert("提示", o.message);
						if (bk) {
							bk.stop();
						};
						stateLabel.setText('<font color="red">' + o.message
										+ '</font>', false);
						s.stop();
					}
					var flat = 0;

					Ext.iterate(o.errorMap, function(k, v) {
								flat = 1;
							});
					if (flat) {
						var str = o.errorMap[currentUser.get("tmnlAssetNo")];
						// if (index == 3) {
						// stateLabel.setText(
						// "<font color='red'>召测成功，等待结果返回...</font>",
						// false);
						// return;
						// }
						// var str = dateStateDict[index] || "未发现结果";
						stateLabel.setText('<font color="red">' + str
										+ '</font>', false);
					}
					var key = s.key;
					// 如果召测结束了在5秒中没有收到终止消息，5秒后结束取数据
					var tt = setTimeout(function() {
						var flat = 0;
						if (params.protocolCode == 'C') {

							var mmm = {};
							Ext.each(o.point, function(a) {
										mmm[a] = true;
									});
							Ext.each(fetchData, function(f) {
										delete mmm[f["pn"]];
									});
							// 构造一些没有返回的pn
							for (k in mmm) {
								var obj = {
									pn : k,
									name : "",
									value : "无数据",
									tmnlAssetNo : currentUser
											.get("tmnlAssetNo"),
									errorCode : "error"
								};
								fetchData.push(obj);
							}
						} else {
							for (var i = 0; i < fetchData && fetchData.length
									&& params.userCodes.length || 0; i++) {
								if (params.userCodes
										&& params.userCodes[i].indexOf("4") < 0) {
									flat = 1;
									break;
								}
							}

							if (flat) {
								var mmm = {};
								Ext.each(o.point, function(a) {
											mmm[a] = true;
										});
								Ext.each(fetchData, function(f) {
											delete mmm[f["pn"]];
										});
								// 构造一些没有返回的pn
								for (k in mmm) {
									var obj = {
										pn : k,
										name : "",
										value : "无数据",
										tmnlAssetNo : currentUser
												.get("tmnlAssetNo"),
										errorCode : "error"
									};
									fetchData.push(obj);
								}
							}
						}
						Ext.Ajax.request({
							url : 'baseapp/dataFetch!clearAllSesssionData.action',
							params : {
								key : key
							},
							success : function(response) {
								if (bk) {
									bk.stop();
								};
								// var o = Ext.decode(response.responseText);
							},
							failure : function() {
								if (bk) {
									bk.stop();
								};
							}
						});
						s.stop();
						clearTimeout(tt);
					}, 5000);

					// Ext.apply(dataState, o.errorMap);
				},
				failure : function() {
					callBtn.enable();
					Ext.Msg.alert("提示", "召测超时");
					Ext.Ajax.on('requestexception', requestException);
					if (bk) {
						bk.stop();
					};
					stateLabel.setText("<font color='red'>召测超时</font>", false);
					s.stop();
				}
			});
		}
	}
	// 取数据
	function doQueryData(args) {
		return function(s) {
			Ext.Ajax.request({
				url : 'baseapp/dataFetch!querySessionData.action',
				params : {
					key : s.key
				},
				success : function(response) {
					var o = Ext.decode(response.responseText);
					fetchData = fetchData.concat(o.resultMap);
					if (fetchData.length > 0) {
						stateLabel.setText("<font color='red'>已经返回结果"
										+ fetchData.length + "项</font>", false);
					}

					if (o.message && o.message == "over" && fetchData.length) {
						// state.setText("召测完成");
						Ext.Msg.alert("提示", "召测结束");
						if (bk) {
							bk.stop();
						};
						s.stop();
					}

				}
			});
		}
	}
	// 结束召测任务
	function endDataFetch(single) {
		return function(s) {
			if (s.state == "starting") {
				Ext.Ajax.request({
							url : 'baseapp/dataFetch!clearAllSesssionData.action',
							params : {
								key : s.key
							},
							success : function(response) {
								// var o = Ext.decode(response.responseText);
							}
						});

			}
			// single === true ? "" : state.setText("召测完成");
		}
	};
	var listenerMain = new sessionListener({
				fn : doQueryData(),
				lastFn : endDataFetch(),
				lastTime : 1500000
			});
	// 保证key对应的session中的数据及时被消除以及当前定时器关闭
	Ext.getCmp('单户召测').on('destroy', function() {
				listenerMain && listenerMain.stop();
			})

	// 使用datafetch中的异步的框架进行数据召测结束
	/** *生成显示结果的窗口* */
	function showDatas() {

		// 克隆一个对象
		function clone(obj) {
			var m = {};
			for (k in obj) {
				m[k] = obj[k];
			}
			return m;
		}
		// 通过那个变量的值对数据进行过滤
		function filter(obj, fmrAssetNo, assetNoM, consName, filterNull) {
			var result = {};
			Ext.iterate(obj, function(k, v) {
						var flat = true;
						if (fmrAssetNo == "终端") {
							if (v["frmAssetNo"] != "终端数据")
								flat = false;
						} else {
							fmrAssetNo && (function() {
								if (v["frmAssetNo"].indexOf(fmrAssetNo) < 0) {
									flat = false;
								}
							})();
						}
						assetNoM && (function() {
							if (v["assetNoM"].indexOf(assetNoM) < 0) {
								flat = false;
							}
						})();
						consName && (function() {
							if (v["consName"].indexOf(consName) < 0) {
								flat = false;
							}
						})();
						filterNull && (function() {
							var val = v["value"];
							if (filterNull == -1) {
								flat = false;
								if (!Ext.isArray(val)) {
									if ((val + "").indexOf("-9999") > -1) {
										flat = true;
									}
								} else {
									if (val.length == 0) {
										flat = true;
									} else {
										flat = true;
										Ext.each(val, function(a) {
													if ((a + "")
															.indexOf("-9999") < 0) {
														flat = false;
													}
												});
									}
								}
							} else if (filterNull == 2) {
								if (val == "无数据") {
									flat = true;
								} else {
									flat = false;
								}
							} else {
								if (!Ext.isArray(val)) {
									if ((val + "").indexOf("-9999") > -1
											|| val == "无数据") {
										flat = false;
									}
								} else {
									flat = false;
									Ext.each(val, function(a) {
												if ((a + "").indexOf("-9999") < 0) {
													flat = true;
													return false;
												}
											})
								}
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
		if (!currentUser) {
			return !!Ext.Msg.alert("提示", "请先选择用户");
		}
		var userRc = currentUser;
		var msg = "用户:" + userRc.get("consName") + ",终端:"
				+ userRc.get("terminalAddr");
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
		var assetNo = userRc.get("tmnlAssetNo");
		var showData = fetchData;
		if (showData.length == 0) {
			return !!Ext.Msg.alert("提示", "未发现召测结果或者召测失败");
		}
		var cons_name = userRc.get("consName");
		var cons_no = userRc.get("consNo");
		var org_name = userRc.get("orgName");
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
			// o.cons_name = cons_name;
			// o.cons_no = cons_no;
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
			hidden : true,
			dataIndex : "org_name",
			renderer : function(v) {
				var show = v;
				var html = '<div ext:hide="false" ext:qtitle="供电单位" ext:qtip="'
						+ '<textarea style=font-size:12px;>' + show
						+ '</textarea>' + '" >' + show + '</div>';
				return html;
			}
		}, {
			header : "用户编号",
			dataIndex : "consNo",
			renderer : function(v, m, r) {
				// if (r.get("consNo")) {
				// v = r.get("consNo");
				// }
				var show = v;
				var html = '<div ext:hide="false" ext:qtitle="用户编号" ext:qtip="'
						+ '<textarea style=font-size:12px;>' + show
						+ '</textarea>' + '" >' + show + '</div>';
				return html;
			}
		}, {
			header : "用户名称",
			dataIndex : "consName",
			renderer : function(v, m, r) {
				// if (r.get("consName")) {
				// v = r.get("consName");
				// }
				var show = v;
				var html = '<div ext:hide="false" ext:qtitle="用户名称" ext:qtip="'
						+ '<textarea style=font-size:12px;>' + show
						+ '</textarea>' + '" >' + show + '</div>';
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
				var show = v;
				var html = '<div ext:hide="false" ext:qtitle="电能表资产号" ext:qtip="'
						+ '<textarea style=font-size:12px;>'
						+ show
						+ '</textarea>' + '" >' + show + '</div>';
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
				// if (Ext.isEmpty()) {
				// return "";
				// }
				var show = v;
				if (v.length == 0) {
					show = "数据项为空";
					return '<span ext:qtitle="召测值" ext:qtip="' + show + '">'
							+ v + '</span>';
				}
				if (!Ext.isArray(v) && (v + "").indexOf("-9999") >= 0) {
					// v = "终端无数据";
					// return '<span ext:qtitle="召测值" ext:qtip="' + v + '">' + v
					// + '</span>';
					return "空值";
				}
				if (Ext.isArray(v)) {
					// var len = 0;
					// for (var i = 0; i < v.length; i++) {
					// if ((v[i] + "").indexOf("-9999") > -1) {
					// // v[i] = "空值";
					// len++;
					// }
					// }
					//
					// // 如果全为空值时返回空字符串
					// if (v.length == len) {
					// return "";
					// }
					show = v.join(",").replace(/-9999/g, "空值");
				}
				if (typeof v == "string" && v.indexOf('T') > 0
						&& v.indexOf('-') > 0) {
					show = v.replace(/-/g, "/");
					show = show.replace(/T/g, " ");
					show = new Date(show).format("Y年m月d日 H:i:s");
				} else {
					// v = v + (r && r.get("unit") || "");
				}
				// var vv = createBr(v, 30);
				var html = '<div ext:hide="false" ext:qtitle="召测值" ext:qtip="'
						+ '<textarea style=font-size:12px;>' + show
						+ '</textarea>' + '" >' + show + '</div>';
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
			renderer : function(v, m, r) {
				// if (v == "" || !v) {
				// return "成功"
				// }
				if (v == "error") {
					var html = "失败，失败原因：未返回此用户任何结果";
					return '<div ext:qtip="' + html + '">' + html + '</div>'
				}
				var value = r.get("value");
				if (Ext.isArray(value)) {
					var flat = 0;
					Ext.each(value, function(a) {
								if ((a + "").indexOf("-9999") >= 0) {
									flat++;
								}
							});
					if (flat == value.length) {
						return flat + "点数值全为空值";
					}
				} else {
					if (value != 0 && !value
							|| (value + "").indexOf('-9999') >= 0) {
						return "失败,失败原因:空值";
					}
				}
				return "成功";
				// var html = (v + "").toLowerCase();
				// if (html.indexOf("ok") >= 0) {
				// return "成功";
				// }
				// return "失败,错误代码:" + v;
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
			header : "采集器资产号",
			dataIndex : "frmAssetNo",
			renderer : function(v) {
				if (!v) {
					return "";
				}
				var show = v;
				var html = '<div ext:hide="false" ext:qtitle="采集器资产号" ext:qtip="'
						+ '<textarea style=font-size:12px;>'
						+ show
						+ '</textarea>' + '" >' + show + '</div>';
				return html;
			}
		}, {
			header : "计量点编号",
			dataIndex : "mpNo",
			renderer : function(v) {
				if (!v) {
					return "";
				}
				var show = v;
				var html = '<div ext:hide="false" ext:qtitle="计量点编号" ext:qtip="'
						+ '<textarea style=font-size:12px;>'
						+ show
						+ '</textarea>' + '" >' + show + '</div>';
				return html;
			}
		}, {
			header : "计量点名称",
			dataIndex : "mpName",
			renderer : function(v) {
				if (!v) {
					return "";
				}
				var show = v;
				var html = '<div ext:hide="false" ext:qtitle="计量点名称" ext:qtip="'
						+ '<textarea style=font-size:12px;>'
						+ show
						+ '</textarea>' + '" >' + show + '</div>';
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
		// function getBigName(str) {
		// var str1 = str.substr(0, str.length - 1);
		// var str2 = str.substr(0, str.length - 2);
		// return bigCodeToName[str1 + "F"] || bigCodeToName[str2 + "FF"]
		// };
		var win;
		Ext.Ajax.request({
			url : "baseapp/dataFetch!findTmnlPnCons.action",
			params : {
				tmnlNo : assetNo,
				pns : pns
			},
			success : function(resp) {
				var o = Ext.decode(resp.responseText);
				var meterName = o.meterName;
				var comboData = o.comboData;
				// 对rs进一步进行处理
				Ext.each(rs, function(r) {
					if (!meterName) {
						r.consNo = "终端数据";
						r.consName = "终端数据";
						r.mpNo = "终端数据";
						r.mpName = "终端数据";
						r.assetNoM = "终端数据";
						r.frmAssetNo = "终端数据"
						r.pt = 1;
						r.ct = 1;
					} else {
						var temp = meterName[r["assetNo"] + "_" + r["pn"]]
								|| {};
						r.consNo = temp.consNo || "终端数据";
						r.consName = temp.consName || "终端数据";
						r.mpNo = temp.mpNo || "终端数据";
						r.mpName = temp.mpName || "终端数据";
						r.assetNoM = temp.assetNoM || "终端数据";
						r.frmAssetNo = temp.frmAssetNo || "终端数据";
						r.pt = temp.pt;
						r.ct = temp.ct;
						// r.assetNo = meterName[r["assetNo"] +
						// "_" +
						// r["pn"]].assetNo;
					}
						// r.bigName = getBigName(r["name"]);
						// 通过大项的名称和测量点组合在作为分组的条件
						// r.group = r.bigName + "_" + r.pn;
					});
				rs.sort(function(a, b) {
							var one = a.pt * a.ct || 1;
							var two = b.pt * b.ct || 1;
							if (one > 1) {
								return -1;
							}
							if (two > 1) {
								return -1;
							}
							one = a.pn;
							two = b.pn;
							if (one > two) {
								return 1;
							} else {
								return -1;
							}
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
							store : storeRs,
							enableExpAll : true
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
					width : 100,
					mode : "remote",
					tpl : '<tpl for="."><div class="x-combo-list-item" ext:qtip="{field1}">{field1}</div></tpl>',
					triggerAction : 'all',
					selectOnFocus : true,
					emptyText : "请选择采集点资产号",
					store : comboStoreFrm,
					// store:[['1','test'],['2','test2']],
					fieldLabel : "采集器资产号",
					listeners : {
						change : function(cb, now, old) {
							comboStorePn = comboData[this.getValue()] || [];
							comboTwo.setValue("");
							comboTwo.bindStore(comboStorePn);
						}
					}
				});

				var comboTwo = new Ext.form.ComboBox({
					mode : "local",
					forceSelection : false,
					triggerAction : 'all',
					width : 100,
					selectOnFocus : true,
					hideTrigger : false,
					displayField : "assetNo",
					valueField : "assetNo",
					editable : true,
					store : comboStorePn,
					tpl : '<tpl for="."><div class="x-combo-list-item" ext:qtip="{field1}">{field1}</div></tpl>',
					emptyText : "请选择电能表资产号",
					fieldLabel : "电能表资产号"
				});
				var consName = new Ext.form.TextField({
							fieldLabel : "用户名称",
							width : 100
						});
				var nullCombox = new Ext.form.ComboBox({
							triggerAction : "all",
							hiddenName : "queryType",
							typeAhead : true,
							mode : "local",
							width : 100,
							fieldLabel : "是否成功",
							editable : false,
							value : 0,
							displayField : "name",
							valueField : "value",
							store : new Ext.data.ArrayStore({
										fields : ["value", "name"],
										data : [[0, "全部"], [1, "成功"],
												[-1, "失败"], [2, "未返回结果"]]
									}),
							selectOnFocus : true
						});
				var top = new Ext.Panel({
							border : false,
							height : 50,
							region : "north",
							layout : "table",
							bodyStyle : "padding:10px 0 0 5px",
							items : [{
										layout : "form",
										border : false,
										columnWidth : .20,
										labelWidth : 80,
										labelAlign : "rigth",
										items : [comboxOne]
									}, {
										layout : "form",
										border : false,
										columnWidth : .20,
										labelWidth : 85,
										labelAlign : "rigth",
										items : [comboTwo]
									}, {
										layout : "form",
										border : false,
										columnWidth : .20,
										labelWidth : 60,
										labelAlign : "rigth",
										items : [consName]
									}, {
										layout : "form",
										border : false,
										columnWidth : .20,
										labelWidth : 60,
										labelAlign : "rigth",
										items : [nullCombox]
									}, {
										border : false,
										width : 100,
										autoEl : {
											tag : "center"
										},
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
												var name = Ext.isEmpty(consName
														.getValue())
														? undefined
														: consName.getValue();
												storeRs.mainData.baseData = filter(
														bakData, fmrAssetNo,
														assetNoM, name,
														nullCombox.getValue());
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
							maximizable : true,
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
	/** *生成显示结果的窗口end* */
	/** 通过测量点数来找到选中的测量点* */
	function initPns(params) {
		params = params || {};
		var act = leftPnTabPanel.getActiveTab();
		if (act == leftPointTree) {
			var allPns = pnRootNode.getUI().isChecked();
			// 假设所有的测量点全部被选择
			if (allPns) {
				params.allPns = true;
				return;
			}
			params["point"] = [];
			var arr = leftPointTree.getChecked();
			Ext.each(arr, function(n) {
						var arr = loadNodeId(n.id);
						// 如果选择的节点时测量点节点,选择添加到参数中去
						if (arr[0] == "meter") {
							params.point.push(arr[2]);
						}
					});
		} else {
			var rs = pnCenterSm.getSelections();
			params["point"] = [];
			Ext.each(rs, function(r) {
						params.point.push(r.get("mpSn"));
					});
		}
	}
	/** **找到某个节点上面所有的选择的值 用于在规约树时选择**** */
	function getGuiParam(params, v, tree) {
		var node = undefined;
		if (leftTabPanel.getActiveTab() == tabOne) {
			node = treeOne.getNodeById("afn_" + v + "_xxoo");
		} else if (tabOne == tree) {
			node = treeOne.getNodeById("afn_" + v + "_xxoo");
		} else {
			var root = tree.getRootNode();
			node = tree.getNodeById("afn_" + v + "_" + loadNodeId(root.id)[2]);
		}
		params = params || {};
		params.userCodes = [];
		if (v == 2) {
			params.startTime = startDate.getValue();
			params.endTime = endDate.getValue().add(Date.DAY, 1);
		}
		get(node);
		function get(n) {
			// var arr=loadNodeId(n.id);
			var childs = n.childNodes;
			for (var i = 0; i < childs.length; i++) {
				var c = childs[i];
				var arr = loadNodeId(c.id);
				if (c.ui.isChecked()) {
					if (v == 3) {
						params.eventLevel = arr[2];
					} else {
						params.userCodes.push(arr[1]);
					}
				} else {
					get(c);
				}
			}
		}
	}
	/***************************************************************************
	 * 用于在非规约树的情况下初始化选择的项
	 */
	function initTypeCode(params, v, tree) {
		// var arr = ["afn_" + v + "_A", "afn_" + v + "_B", "afn_" + v + "_C",
		// "afn_" + v + "_D", "afn_" + v + "_E", "afn_" + v + "_F"];
		//
		// var node = (function() {
		// var r = undefined;
		// Ext.each(arr, function(a) {
		// if (tree.getNodeById(a)) {
		// r=tree.getNodeById(a);
		// return false;
		// }
		// });
		// return r;
		// })();
		params = params || {};
		params.userCodes = [];
		if (v == 2) {
			params.startTime = startDate.getValue();
			params.endTime = endDate.getValue();
		}
		var cks = tree.getChecked();
		Ext.each(cks, function(c) {
					var arr = loadNodeId(c.id);
					params.userCodes.push(arr[1]);
				});
	}

	/** *将一个id解析为数组** */
	function loadNodeId(id) {
		return id.split("_");
	}
	/** **清除某个树节点下的所有选择项*** */
	function clearNodeCheck(v, tree) {
		tree = tree || treeOne;
		var arr = ["afn_" + v + "_xxoo", "afn_" + v + "_A", "afn_" + v + "_B",
				"afn_" + v + "_C", "afn_" + v + "_D", "afn_" + v + "_E",
				"afn_" + v + "_F"];
		var node = (function() {
			var r = undefined;
			Ext.each(arr, function(a) {
						if (tree.getNodeById(a)) {
							r = tree.getNodeById(a);
							return false;
						}
					});
			return r;
		})();
		if (!node) {
			return;
		}
		// var node = treeOne.getNodeById("afn_" + v + "_xxoo");
		node.cascade(function(tn) {
					tn.ui.toggleCheck(false);
				});
	}
	/** *隐藏某个在form布局中的组件*** */
	function hideFormCom(com) {
		Ext.get("x-form-el-" + com.id).prev().hide();
		com.hide();
	}
	function showFormCom(com) {
		Ext.get("x-form-el-" + com.id).prev().show();
		com.show();
	}
	/** *通过e_data_mp里面的id来找到相关数据的方法 */
	function loadDataById(id) {
		if (!(typeof id == "string")) {
			rightDetailPanel.getForm().setValues(id);
			return;
		}
		Ext.Ajax.request({
					params : {
						id : id
					},
					url : "baseapp/dataFetchLone!findDataById.action",
					success : function(response) {
						var o = Ext.decode(response.responseText);
						var bean = o.beanMap;
						// alert(bean);
						if (!bean) {
							loadDataById(emptyBean);
							return;
						}
						rightDetailPanel.getForm().setValues(bean);
					}
				});
	}
	/** *方法区* */
	// 通过选择不同的项目来过滤配置
	function choiceConfig(v) {
		if (v == 1) {
			startText.ownerCt.hide();
			endText.ownerCt.hide();
			startDate.ownerCt.show();
			endDate.ownerCt.show();
			startDate.ownerCt.disable();
			endDate.ownerCt.disable();
		} else if (v == 2) {
			startText.ownerCt.hide();
			endText.ownerCt.hide();
			startDate.ownerCt.show();
			endDate.ownerCt.show();
			startDate.ownerCt.enable();
			endDate.ownerCt.enable();
		} else if (v == 3) {
			startText.ownerCt.show();
			endText.ownerCt.show();
			startDate.ownerCt.hide();
			endDate.ownerCt.hide();
		}
	}
	// 通过此方法对规约树进行过滤，如果参数中有filter，通过filter进行过滤
	function filterGui(n, filter) {
		function getArr() {
			return [1, 2, 3];
		}
		var one = filter || oneFilter;
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
	/** ****得到当前激活tab页的afn选择值（实时，历史，事件）***** */
	function activateQueryType() {
		var tab = leftTabPanel.getActiveTab();
		if (tab == tabOne) {
			return typeCombox.getValue();
		} else if (tab == tabTwo) {
			return typeComboxTwo.getValue();
		} else if (tab == tabThree) {
			return typeComboxThree.getValue();
		} else if (tab == tabFour) {
			return typeComboxFour.getValue();
		}
	}
	/** *方法区结束* */
	// 专公变面板
	// 专公变面板组件区
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
							data : [[1, "实时数据"], [2, "历史数据"], [3, "事件"]]
						}),
				selectOnFocus : true,
				listeners : {
					select : function(me, r, index) {
						var v = r.get("value");
						filterGui(v);
						var arr = function() {
							return [1, 2, 3];
						};
						var arr = arr().remove(v);
						clearNodeCheck(arr[0]);
						clearNodeCheck(arr[1]);
						choiceConfig(v);
					}

				}
			});

	var oneRootNode = new Ext.tree.AsyncTreeNode({
				id : 'root_xxoo_xxoo',
				text : 'root'
			});
	var treeLoader = new Ext.tree.TreeLoader({
				dataUrl : "baseapp/dataFetchLone!dealClearTree.action"
			});
	var i = 0;
	function treeCheckChange(tn, checked) {
		var arr = tn.id.split("_");
		var pn = tn.parentNode;
		// 如果为事件数据
		if (arr[0] == "dataType" && arr[1] == "3") {
			Ext.each(pn.childNodes, function(n) {
						n.ui.checkbox.checked = false;
					});
			tn.ui.checkbox.checked = true;
			return false;
		}
		// 如果当前的节点被选中了，他的字节点会全被选中
		if (checked) {
			var childs = tn.childNodes;
			Ext.each(childs, function(n) {
						n.ui.checkbox.checked = true;
					});
			// 假设它的同级节点全被选择，他的父节点自动选中
			var flat = 1;
			Ext.each(pn.childNodes, function(n) {
						if (!n.ui.isChecked()) {
							flat = 0;
							return false;
						}
					});
			if (flat) {
				pn.ui.checkbox && (pn.ui.checkbox.checked = true);
			}

		} else {
			pn.ui.checkbox && (pn.ui.checkbox.checked = false);
			var childs = tn.childNodes;
			Ext.each(childs, function(n) {
						n.ui.checkbox && (n.ui.checkbox.checked = true);
					});
			// 在不选中的情况下，假设它的字节点为全部选中，将子节点全选
			var flat = 1;
			Ext.each(childs, function(n) {
						if (!n.ui.isChecked()) {
							flat = 0;
							return false;
						}
					});
			if (flat) {
				Ext.each(childs, function(n) {
							n.ui.checkbox.checked = false;
						});
			}
		}
		// /如果当前的节点去掉选中，他的父节点如果被选中状态也变成未选中
	}
	var treeOne = new Ext.tree.TreePanel({
				tbar : [typeCombox
				// , "->", {
				// xtype : "label",
				// html : "规约编码"
				// }
				],
				border : true,
				autoScroll : true,
				heigth : 200,
				region : "center",
				animate : false,
				frame : false,
				rootVisible : false,
				root : oneRootNode,
				loader : treeLoader,
				listeners : {
					load : function(tn) {
						if (i == 0) {
							i = 1;
							filterGui(1);
							choiceConfig(1);
						}
					},
					afterlayout : function() {
						treeOne.body.setOverflow('scroll');
					},
					checkchange : treeCheckChange
				}
			});
	// 当前激活的树
	var activeTree = treeOne;
	var oneFilter = new Ext.tree.TreeFilter(treeOne);
	var tabOne = new Ext.Panel({
				border : false,
				title : "按规约项召测",
				layout : "border",
				height : 200,
				items : [treeOne],
				listeners : {
					activate : function(p) {
						activeTree = treeOne;
						choiceConfig(activateQueryType());
					}
				}
			});

	// 公变用户树
	var codeBtnTwo = new Ext.Button({
				text : "编码定义",
				iconCls : "taskModel",
				handler : function() {
					window.defineCode = 'A';
					openTab("编码管理", "./sysMan/templateMan/codeManage.jsp")
				}
			});
	var codeBtnThree = new Ext.Button({
				text : "编码定义",
				iconCls : "taskModel",
				handler : function() {
					window.defineCode = 'C';
					openTab("编码管理", "./sysMan/templateMan/codeManage.jsp")
				}
			});
	var codeBtnFour = new Ext.Button({
				text : "编码定义",
				iconCls : "taskModel",
				handler : function() {
					window.defineCode = 'D';
					openTab("编码管理", "./sysMan/templateMan/codeManage.jsp")
				}
			});
	powerObjs.push(codeBtnTwo, codeBtnThree, codeBtnFour);
	var typeComboxTwo = new Ext.form.ComboBox({
				triggerAction : "all",
				hiddenName : "queryType",
				typeAhead : true,
				itemId : "queryType",
				mode : "local",
				width : 100,
				editable : false,
				value : 1,
				displayField : "name",
				valueField : "value",
				store : new Ext.data.ArrayStore({
							fields : ["value", "name"],
							data : [[1, "实时数据"], [2, "历史数据"]]
						}),
				selectOnFocus : true,
				listeners : {
					select : function(me, r, index) {
						var v = this.getValue();
						var arr = function() {
							return [1, 2, 3];
						};
						var arr = arr().remove(v);
						clearNodeCheck(arr[0], treeTwo);
						clearNodeCheck(arr[1], treeTwo);
						// filterGui(this.getValue(), twoFilter);
						activateFilter();
						choiceConfig(this.getValue());
					}
				}
			});
	var rootNodeTwo = new Ext.tree.AsyncTreeNode({
				id : 'root_xxoo_A',
				text : 'root'
			});

	function doNode(node) {
		Ext.apply(node, {
					expandChildNodes : function(deep) {
						var cs = this.childNodes;
						for (var i = 0, len = cs.length; i < len; i++) {
							var tn = cs[i];
							doNode(tn);
							var arr = loadNodeId(tn.id);
							if (arr[0] == "dataType" || arr[0] == "afn"
									|| arr[0] == "big") {
								tn.on("expand", function() {
											activateFilter();
										});
							}
							if (arr[0] == "big") {
								// tn.on("append", function(tree, now,
								// newNode,i) {
								// if (now.ui.isChecked()) {
								// var ckb=now.childNodes[i].ui.checkbox;
								// ckb&& (ckb.checked = true)
								// }
								// });
								continue;
							}
							tn.expand(deep);
						}
					}
				});
	}
	doNode(rootNodeTwo);
	var treeLoaderTwo = new Ext.tree.TreeLoader({
				dataUrl : "baseapp/dataFetchLone!createTypeTree.action"
			});
	var treeTwoRadio = new Ext.form.RadioGroup({
				columns : [150, 150],
				width : 320,
				defaults : {
					name : "any1"
				},
				items : [{
							boxLabel : "常用",
							checked : true,
							inputValue : 1
						}, {
							boxLabel : "全部",
							inputValue : -1
						}],
				listeners : {
					change : function(rds, rd) {
						activateFilter();
					}
				}
			});
	var treeTwo = new Ext.tree.TreePanel({
				tbar : [typeComboxTwo, "->", treeTwoRadio, codeBtnTwo
				// , "->", {
				// xtype : "label",
				// html : "规约编码"
				// }
				],
				border : true,
				autoScroll : true,
				heigth : 200,
				region : "center",
				animate : false,
				frame : false,
				rootVisible : false,
				root : rootNodeTwo,
				loader : treeLoaderTwo,
				listeners : {
					load : function(tn) {
						typeComboxTwo.fireEvent("select");
					},
					afterlayout : function() {
						treeTwo.body.setOverflow('scroll');
					},
					checkchange : treeCheckChange
				}
			});
	var twoFilter = new Ext.tree.TreeFilter(treeTwo);

	var tabTwo = new Ext.Panel({
				border : false,
				layout : "border",
				title : "公专变用户",
				height : 200,
				items : [treeTwo],
				listeners : {
					activate : function(p) {
						activeTree = treeTwo;
						var root = treeTwo.getRootNode();
						if (!root.isExpanded()) {
							root.expand(true);
						}
						choiceConfig(activateQueryType());
					}
				}
			});
	// 低压三相用户
	var typeComboxThree = new Ext.form.ComboBox({
				triggerAction : "all",
				hiddenName : "queryType",
				typeAhead : true,
				itemId : "queryType",
				mode : "local",
				width : 100,
				editable : false,
				value : 1,
				displayField : "name",
				valueField : "value",
				store : new Ext.data.ArrayStore({
							fields : ["value", "name"],
							data : [[1, "实时数据"], [2, "历史数据"]]
						}),
				selectOnFocus : true,
				listeners : {
					select : function(me, r, index) {
						var v = this.getValue();
						var arr = function() {
							return [1, 2, 3];
						};
						var arr = arr().remove(v);
						clearNodeCheck(arr[0], treeThree);
						clearNodeCheck(arr[1], treeThree);
						activateFilter();
						// filterGui(this.getValue(), threeFilter);
						choiceConfig(this.getValue());
					}
				}
			});
	var rootNodeThree = new Ext.tree.AsyncTreeNode({
				id : 'root_xxoo_C',
				text : 'root'
			});
	var treeLoaderThree = new Ext.tree.TreeLoader({
				dataUrl : "baseapp/dataFetchLone!createTypeTree.action"
			});
	doNode(rootNodeThree);
	var treeThreeRadio = new Ext.form.RadioGroup({
				columns : [150, 150],
				width : 320,
				defaults : {
					name : "any2"
				},
				items : [{
							boxLabel : "常用",
							checked : true,
							inputValue : 1
						}, {
							boxLabel : "全部",
							inputValue : -1
						}],
				listeners : {
					change : function(rds, rd) {
						activateFilter();
					}
				}
			});
	var treeThree = new Ext.tree.TreePanel({
				tbar : [typeComboxThree, "->", treeThreeRadio, codeBtnThree
				// , "->", {
				// xtype : "label",
				// html : "规约编码"
				// }
				],
				border : true,
				autoScroll : true,
				heigth : 200,
				region : "center",
				animate : false,
				frame : false,
				rootVisible : false,
				root : rootNodeThree,
				loader : treeLoaderThree,
				listeners : {
					load : function(tn) {
						typeComboxThree.fireEvent("select");
					},
					afterlayout : function() {
						treeThree.body.setOverflow('scroll');
					},
					checkchange : treeCheckChange
				}
			});
	var threeFilter = new Ext.tree.TreeFilter(treeThree);
	var tabThree = new Ext.Panel({
				border : false,
				layout : "border",
				title : "低压三相动力用户",
				height : 200,
				items : [treeThree],
				listeners : {
					activate : function(p) {
						activeTree = treeThree;
						var root = treeThree.getRootNode();
						if (!root.isExpanded()) {
							root.expand(true);
						}
						choiceConfig(activateQueryType());
					}
				}
			});
	// 单相工商业和居民用户
	var typeComboxFour = new Ext.form.ComboBox({
				triggerAction : "all",
				hiddenName : "queryType",
				typeAhead : true,
				itemId : "queryType",
				mode : "local",
				width : 100,
				editable : false,
				value : 1,
				displayField : "name",
				valueField : "value",
				store : new Ext.data.ArrayStore({
							fields : ["value", "name"],
							data : [[1, "实时数据"], [2, "历史数据"]]
						}),
				selectOnFocus : true,
				listeners : {
					select : function(me, r, index) {
						var v = this.getValue();
						var arr = function() {
							return [1, 2, 3];
						};
						var arr = arr().remove(v);
						clearNodeCheck(arr[0], treeFour);
						clearNodeCheck(arr[1], treeFour);
						activateFilter();
						// filterGui(this.getValue(), fourFilter);
						choiceConfig(this.getValue());
					}
				}
			});
	var rootNodeFour = new Ext.tree.AsyncTreeNode({
				id : 'root_xxoo_D',
				text : 'root'
			});
	var treeLoaderFour = new Ext.tree.TreeLoader({
				dataUrl : "baseapp/dataFetchLone!createTypeTree.action"
			});
	doNode(rootNodeFour);
	var treeFourRadio = new Ext.form.RadioGroup({
				columns : [150, 150],
				width : 320,
				defaults : {
					name : "any3"
				},
				items : [{
							boxLabel : "常用",
							checked : true,
							inputValue : 1
						}, {
							boxLabel : "全部",
							inputValue : -1
						}],
				listeners : {
					change : function(rds, rd) {
						activateFilter();
					}
				}
			});
	var treeFour = new Ext.tree.TreePanel({
				tbar : [typeComboxFour, "->", treeFourRadio, codeBtnFour
				// , "->", {
				// xtype : "label",
				// html : "规约编码"
				// }
				],
				border : true,
				autoScroll : true,
				heigth : 200,
				region : "center",
				animate : false,
				frame : false,
				rootVisible : false,
				root : rootNodeFour,
				loader : treeLoaderFour,
				listeners : {
					load : function(tn) {
						typeComboxFour.fireEvent("select");
					},
					afterlayout : function() {
						treeFour.body.setOverflow('scroll');
					},
					checkchange : treeCheckChange
				}
			});
	var fourFilter = new Ext.tree.TreeFilter(treeFour);
	var tabFour = new Ext.Panel({
				border : false,
				layout : "border",
				height : 200,
				title : "单相工商业和居民用户",
				items : [treeFour],
				listeners : {
					activate : function(p) {
						activeTree = treeFour;
						var root = treeFour.getRootNode();
						if (!root.isExpanded()) {
							root.expand(true);
						}
						choiceConfig(activateQueryType());
					}
				}
			});
	// 规约面板
	//
	// 上部左边的tab页
	var leftTabPanel = new Ext.TabPanel({
				columnWidth : .7,
				border : false,
				// activeItem : 3,
				items : [tabTwo, tabThree, tabFour, tabOne]
			});
	// 上部右边的组件定义区
	var startText = new Ext.form.NumberField({
				itemId : "startText",
				width : 100,
				minValue : 0,
				maxValue : 255,
				allowBlank : false,
				fieldLabel : "起始码"
			});
	var endText = new Ext.form.NumberField({
				width : 100,
				allowBlank : false,
				minValue : 0,
				maxValue : 255,
				itemId : "endText",
				fieldLabel : "终止码"
			});
	var startDate = new Ext.form.DateField({
				value : new Date(),
				editable : false,
				format : "Y-m-d",
				fieldLabel : '从',
				width : 100
			});

	var endDate = new Ext.form.DateField({
				value : new Date(),
				format : "Y-m-d",
				editable : false,
				fieldLabel : '到',
				width : 100
			});
	var bk = undefined;
	var callBtn = new Ext.Button({
				text : "召测",
				width : 80,
				handler : function() {
					if (!currentUser) {
						return !!Ext.Msg.alert("提示", "请先从左边树选择一个终端");
					}
					var params = {};
					var queryType = activateQueryType();
					if (queryType == 3) {
						if (!startText.isValid() || !endText.isValid()) {
							return !!Ext.Msg.alert("提示", "起始码或者终止码为空");
						}
					}
					if (queryType == 2) {
						var in7 = dateInN(startDate.getValue(), endDate
										.getValue(), 7);
						if (!in7) {
							return !!Ext.Msg.alert("警告", "请确保开始时间和结束在七天之内");
						}
					}
					params.queryType = queryType;
					params.tmnlAssetNos = [currentUser.get("tmnlAssetNo")];
					params.tmnlNo = currentUser.get("tmnlAssetNo");
					params.protocolCode = currentUser.get("protocolCode");
					getGuiParam(params, queryType, activeTree);
					// if (leftTabPanel.getActiveTab() == tabOne) {
					// getGuiParam(params, queryType);
					// } else {
					// initTypeCode(params, queryType, activeTree);
					// }
					if (params.userCodes.length == 0) {
						return !!Ext.Msg.alert("提示", "请选择召测项");
					}
					if (configTimeoutChk.checked) {
						if (!configTimeoutText.isValid()) {
							return !!Ext.Msg.alert("提示", "超时时间不能为空");
						}
						params.timeOut = configTimeoutText.getValue();
					} else {
						params.timeOut = 300;
					}
					initPns(params);
					listenerMain.firstFn = doFirstFn(params);
					listenerMain.start();
					if (bk) {
						bk.stop();
						bk = undefined;
					}
					bk = new blink();
				}
			});
	var exitBtn = new Ext.Button({
				text : "取消",
				width : 80,
				handler : function() {
					callBtn.enable();
					if (listenerMain.state == "starting") {
						stateLabel.setText('<font color="red">用户终止</font>',
								false);
						listenerMain.stop();
					}
					if (bk)
						bk.stop();
					bk = undefined;
				}
			});
	// 下部的按钮面板
	var buttonPanel = new Ext.Panel({
				border : false,
				layout : "table",
				items : [{
							border : false,
							width : 100,
							items : [callBtn]
						}, {
							border : false,
							width : 100,
							items : [exitBtn]
						}]
			});
	var configTimeoutText = new Ext.form.NumberField({
				width : 100,
				allowBlank : false,
				value : 300,
				hidden : true,
				maxValue : 500
				//maxValue : listenerMain.lastTime / 1000
			});
	var configTimeoutChk = new Ext.form.Checkbox({
				boxLabel : "配置超时时间",
				style : "background:transparent;",
				listeners : {
					check : function(me, checked) {
						configTimeoutText.setVisible(checked);
						configTimeoutLabel.setVisible(checked);
					}
				}
			});
	var configTimeoutLabel = new Ext.form.Label({
				hidden : true,
				html : "秒"
			});
	var configTimeoutPanel = new Ext.Panel({
				layout : "table",
				padding : "10px 0px 0px 20px",
				border : false,
				items : [configTimeoutChk, configTimeoutText,
						configTimeoutLabel]
			});
	// 上部右边的配置用于放置各个配置项的面板
	var configPanel = new Ext.Panel({
				labelWidth : 60,
				labelAlign : "right",
				border : false,
				layout : "form",
				height : 100,
				items : [{
							border : false,
							layout : "form",
							labelWidth : 60,
							hidden : true,
							labelAlign : "right",
							items : [startText]
						}, {
							border : false,
							layout : "form",
							labelWidth : 60,
							hidden : true,
							labelAlign : "right",
							items : [endText]
						}, {
							border : false,
							layout : "form",
							labelWidth : 60,
							labelAlign : "right",
							items : [startDate]
						}, {
							border : false,
							layout : "form",
							labelWidth : 60,
							labelAlign : "right",
							items : [endDate]
						}, configTimeoutPanel]
			});
	// 上部右边的配置页
	var rightConfigPanel = new Ext.Panel({
				columnWidth : .3,
				padding : "20px 0 0 10px",
				border : false,
				layout : "form",
				labelWidth : 60,
				labelAlign : "right",
				items : [configPanel
						// , {
						// border : false,
						// height : 80
						// }
						, buttonPanel]
			});
	// 北面的大面板
	var northPanel = new Ext.Panel({
				height : 210,
				border : false,
				layout : "column",
				region : "north",
				items : [leftTabPanel, rightConfigPanel]
			});
	// 中部面板的上部 组件定义区
	var userNameText = new Ext.form.TextField({
				fieldLabel : "用户名称",
				readOnly : true,
				allowBlank : false
			});
	var userAddrText = new Ext.form.TextField({
				fieldLabel : "终端地址",
				readOnly : true,
				allowBlank : false
			});
	var userCntText = new Ext.form.TextField({
				fieldLabel : "用户总数",
				readOnly : true,
				width : 80,
				allowBlank : false
			});
	// 规约名称
	var protocolNameText = new Ext.form.TextField({
				fieldLabel : "规约类型",
				readOnly : true,
				allowBlank : false
			});
	// 中部面板的上部
	var centerTopPanel = new Ext.Panel({
				border : false,
				height : 50,
				padding : "10px 0 0 0",
				layout : "column",
				region : "north",
				items : [{
							columnWidth : .25,
							border : false,
							layout : "form",
							labelAlign : "right",
							padding : "0 0 0 20px",
							labelWidth : 60,
							items : [userNameText]
						}, {
							border : false,
							columnWidth : .25,
							layout : "form",
							padding : "0 0 0 20px",
							labelAlign : "right",
							labelWidth : 60,
							items : [userAddrText]
						}, {
							border : false,
							columnWidth : .25,
							layout : "form",
							padding : "0 0 0 20px",
							labelAlign : "right",
							labelWidth : 60,
							items : [protocolNameText]
						}, {
							border : false,
							columnWidth : .25,
							layout : "form",
							padding : "0 0 0 20px",
							labelAlign : "right",
							labelWidth : 60,
							items : [userCntText]
						}

				]
			});
	// 中部面板的中部面板 组件定义区
	// 左边测量点的树
	/** ************** */
	/* 透明规约编码树 */
	/** ************** */
	var pnRootNode = new Ext.tree.AsyncTreeNode({
				id : 'tmnl_52507989_xxoo',
				text : '终端',
				checked : false,
				iconCls : 'net-02',
				listeners : {
					beforeload : function(tn) {
						Ext.Msg.alert("警告", "请从左边树选择终端");
						return false;
					}
				}

			});

	// pn测量点panel 以及其相关的组件
	// 采集器搜索
	var pnFrmText = new Ext.form.TextField({
				fieldLabel : "采集器资产号",
				width : 100
			});
	// 筛选条件选择框
	var selectCombox = new Ext.form.ComboBox({
				triggerAction : "all",
				hiddenName : "queryType",
				typeAhead : true,
				itemId : "queryType",
				mode : "local",
				width : 100,
				fieldLabel : "选择查询条件",
				editable : false,
				value : "fmrAssetNo",
				displayField : "name",
				valueField : "value",
				store : new Ext.data.ArrayStore({
							fields : ["value", "name"],
							data : [["fmrAssetNo", "采集器资产号"],
									["consNo", "用户编号"], ["consName", "用户名称"],
									["assetNo", "电能表资产号"]]
						}),
				selectOnFocus : true,
				listeners : {
					select : function() {
						pnMeterText.setValue("");
					}
				}
			});

	// 电能表搜索
	var pnMeterText = new Ext.form.TextField({
				width : 100
			});
	var pnBtn = new Ext.Button({
				text : "查询",
				width : 40,
				handler : function() {
					var key = ("edataFinder." + selectCombox.getValue());
					pnCenterStore.baseParams = {
						"edataFinder.tmnlAssetNo" : currentUser
								.get("tmnlAssetNo")
					};
					pnCenterStore.baseParams[key] = pnMeterText.getValue();
					pnCenterStore.load({
								params : {
									start : 0,
									limit : 50
								}
							});
				}
			});
	var pnTopPanel = new Ext.Panel({
				border : false,
				layout : "table",
				padding : "10px 0 0 5px",
				height : 40,
				items : [{
							border : false,
							layout : "form",
							labelAlign : "right",
							labelWidth : 80,
							items : [selectCombox]
						}, {
							border : false,
							layout : "form",
							labelAlign : "right",
							labelWidth : 1,
							items : [pnMeterText]
						}, {
							border : false,
							width : 80,
							autoEl : {
								tag : "center"
							},
							items : [pnBtn]
						}],
				region : "north"
			});

	// 中部的pn显示grid 以及相关组件
	// var pnCenterSm = new Ext.grid.checkNoClearRowModel({
	// flat : true,
	// listeners : {
	// rowselect : function(me, index, r) {
	// var pn = r.get("mpSn");
	// if (pn == 0) {
	// loadDataById(emptyBean);
	// userBigTypeText.setValue("");
	// } else {
	// loadDataById(r.get("id") + "");
	// userBigTypeText.setValue(userType.get(r
	// .get("consSort")));
	// }
	// }
	// }
	// });
	var pnCenterSm = new Ext.grid.CheckboxSelectionModel()
	var pnCenterCm = new Ext.grid.ColumnModel([pnCenterSm, {
		header : "用户名称",
		dataIndex : "consName",
		align : "center",
		renderer : function(v, m, r) {
			if (!v) {
				return "";
			}
			var html = '<span ext:hide="false" ext:qtitle="用户名称" ext:qtip="'
					+ v + '">' + v + '</span>';
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
			var show = v;
			var html = '<div ext:hide="false" ext:qtitle="用户编号" ext:qtip="'
					+ '<textarea style=font-size:12px;>' + show + '</textarea>'
					+ '" >' + show + '</div>';
			return html;
		}
	}, {
		header : "计量点编号",
		dataIndex : "mpNo",
		renderer : function(v, m, r) {
			if (!v) {
				return "";
			}
			var show = v;
			var html = '<div ext:hide="false" ext:qtitle="计量点编号" ext:qtip="'
					+ '<textarea style=font-size:12px;>' + show + '</textarea>'
					+ '" >' + show + '</div>';
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
			var html = '<span ext:hide="false" ext:qtitle="计量点名称" ext:qtip="'
					+ v + '">' + v + '</span>';
			return html;
		}
	}, {
		header : "电能表资产号",
		dataIndex : "assetNo",
		renderer : function(v, m, r) {
			if (!v) {
				return "";
			}
			var show = v;
			var html = '<div ext:hide="false" ext:qtitle="电能表资产号" ext:qtip="'
					+ '<textarea style=font-size:12px;>' + show + '</textarea>'
					+ '" >' + show + '</div>';
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
			var show = v;
			var html = '<div ext:hide="false" ext:qtitle="采集器资产号" ext:qtip="'
					+ '<textarea style=font-size:12px;>' + show + '</textarea>'
					+ '" >' + show + '</div>';
			return html;
		}
	}]);
	var pnCenterStore = new Ext.data.JsonStore({
				url : "baseapp/dataFetch!findTmnlCmp.action",
				root : "resultMap",
				fields : ["mpNo", "mpName", "mpSn", "tmnlAssetNo", "assetNo",
						"consName", "consSort", "fmrAssetNo", "consNo", "id"],
				totalProperty : "usrCount"
			});
	var pnCenterGrid = new Ext.grid.GridPanel({
				sm : pnCenterSm,
				cm : pnCenterCm,
				autoScroll : true,
				region : "center",
				viewConfig : {
					forceFit : false
				},
				store : pnCenterStore,
				bbar : new Ext.ux.MyToolbar({
							store : pnCenterStore,
							enableExpAll : true
						})
			});

	var iii = 1;
	var pnPanel = new Ext.Panel({
				title : "精确查找",
				border : false,
				layout : "border",
				items : [pnTopPanel, pnCenterGrid],
				listeners : {
					activate : function() {
						if (!currentUser) {
							leftPnTabPanel.setActiveTab(0);
							return !!Ext.Msg.alert("提示", "你还没有选择终端");
						} else {
							iii && (pnBtn.handler());
							iii = 0;
						}
					}
				}
			});
	var pnBar = new Ext.ux.tree.PagingTreeToolbar();
	var pnTreeLoader = new Ext.ux.tree.PagingTreeLoader({
				autoScroll : true,
				toolbar : pnBar,
				dataUrl : "baseapp/dataFetchLone!pointRefTree.action"
			});
	var leftPointTree = new Ext.tree.TreePanel({
				border : true,
				title : "测量点树",
				region : "center",
				autoScroll : true,
				root : pnRootNode,
				rootVisible : true,
				loader : pnTreeLoader,
				bbar : pnBar,
				listeners : {
					beforeclick : function(tn) {
						var arr = tn.id.split("_");
						if (tn.text == "终端") {
							loadDataById(emptyBean);
							// activateType(arr[3]);
							userBigTypeText.setValue("");
						} else if (tn == leftPointTree.getRootNode()) {
							loadDataById(emptyBean);
							userBigTypeText.setValue("");
							return true;
						} else if (arr[0] == "frm") {
							loadDataById(emptyBean);
							userBigTypeText.setValue("");
						} else {
							// activateType(arr[3]);
							loadDataById(arr[1]);
							userBigTypeText.setValue(userType.get(arr[3]));
						}

						return true;
					},
					checkchange : function(tn, checked) {
						// 当为根节点时
						if (pnRootNode == tn) {
							return;
						}
						var arr = loadNodeId(tn.id);
						if (checked && arr[0] == "meter") {
							loadDataById(arr[1]);
							userBigTypeText.setValue(userType.get(arr[3]));
						}
						// pnRootNode.ui.checkbox.checked=false;

						// 当为采集器节点时,其子节点全部选择
						if (arr[0] == "frm") {
							loadDataById(emptyBean);
							var childs = tn.childNodes;
							Ext.each(childs, function(n) {
										n.ui.checkbox
												&& (n.ui.checkbox.checked = checked)
									});
						}
					}
				}
			});
	// 改变一下getChecked方法选择值的方式。
	Ext.apply(leftPointTree, {
				getChecked : function(a, startNode) {
					startNode = startNode || this.root;
					var r = [];
					var f = function() {
						if (this.attributes.checked
								|| (this.ui.checkbox && this.ui.checkbox.checked)) {
							r.push(!a ? this : (a == 'id'
									? this.id
									: this.attributes[a]));
						}
					};
					startNode.cascade(f);
					return r;
				}

			});
	pnBar.bind(pnRootNode);
	var leftPnTabPanel = new Ext.TabPanel({
				border : false,
				region : "center",
				activeItem : 0,
				items : [leftPointTree, pnPanel]
			});
	var userBigTypeText = new Ext.form.TextField({
				xtype : "textfield",
				fieldLabel : "用户所属大类",
				readOnly : true,
				width : 200
			});
	// 右边信息的详细展示panel
	var rightDetailPanel = new Ext.FormPanel({
				border : false,
				region : "east",
				split : true,
				width : 300,
				layout : "column",
				items : [{
							border : false,
							columnWidth : 1,
							layout : "form",
							labelAlign : "right",
							labelWidth : 80,
							items : [{
										xtype : "textfield",
										fieldLabel : "电能表资产号",
										readOnly : true,
										name : "assetNo",
										width : 200
									}]
						}, {
							border : false,
							columnWidth : .5,
							layout : "form",
							labelAlign : "right",
							labelWidth : 80,
							items : [{
										xtype : "textfield",
										fieldLabel : "综合倍率",
										readOnly : true,
										name : "tFactor",
										width : 70
									}]
						}, {
							border : false,
							columnWidth : .5,
							layout : "form",
							labelAlign : "right",
							labelWidth : 80,
							items : [{
										xtype : "textfield",
										fieldLabel : "测量点号",
										readOnly : true,
										name : "mpSn",
										width : 70
									}]
						}, {
							border : false,
							columnWidth : .5,
							layout : "form",
							labelAlign : "right",
							labelWidth : 80,
							items : [{
										xtype : "textfield",
										fieldLabel : "CT",
										width : 70,
										name : "ct",
										readOnly : true
									}]
						}, {
							border : false,
							columnWidth : .5,
							layout : "form",
							labelAlign : "right",
							labelWidth : 80,
							items : [{
										xtype : "textfield",
										fieldLabel : "PT",
										width : 70,
										name : "pt",
										readOnly : true
									}]
						}, {
							border : false,
							columnWidth : .5,
							layout : "form",
							labelAlign : "right",
							labelWidth : 80,
							items : [{
										xtype : "textfield",
										fieldLabel : "计量点类型",
										readOnly : true,
										name : "typeName",
										width : 70
									}]
						}, {
							border : false,
							columnWidth : .5,
							layout : "form",
							labelAlign : "right",
							labelWidth : 80,
							items : [{
										xtype : "textfield",
										fieldLabel : "计量点用途",
										readOnly : true,
										name : "usageTypeName",
										width : 70
									}]
						}, {
							border : false,
							columnWidth : .5,
							layout : "form",
							labelAlign : "right",
							labelWidth : 80,
							items : [{
										xtype : "textfield",
										fieldLabel : "用户状态",
										readOnly : true,
										name : "statusName",
										width : 70
									}]
						}, {
							border : false,
							columnWidth : .5,
							layout : "form",
							labelAlign : "right",
							labelWidth : 80,
							items : [{
										xtype : "textfield",
										fieldLabel : "用户类别",
										readOnly : true,
										name : "consTypeName",
										width : 70
									}]
						}, {
							border : false,
							columnWidth : .95,
							layout : "form",
							labelAlign : "right",
							labelWidth : 80,
							items : [userBigTypeText]
						}]
			});

	// 中部面板的中部面板
	var centerCenterPanel = new Ext.Panel({
				border : false,
				layout : "border",
				region : "center",
				// autoScroll : true,
				items : [
						// treeOne,
						leftPnTabPanel, rightDetailPanel]
			});
	var stateLabel = new Ext.form.Label({
				style : 'font-weight:bold;',
				xtype : "label",
				html : '<font color="red">召测状态</font>'
			});

	// 中部客户面板
	var centerPanel = new Ext.Panel({
				tbar : [stateLabel, "->", {
							xtype : "button",
							text : "查看结果",
							handler : function() {
								showDatas();
							}
						}, "-", {
							xtype : "button",
							text : "查看报文",
							handler : function() {
								if (!baowen) {
									return !!Ext.Msg.alert("提示", "请先选择终端");
								} else {
									baowen();
								}
							}
						}],
				border : false,
				layout : "border",
				region : "center",
				// autoScroll : true,
				items : [centerTopPanel, centerCenterPanel]
			});
	// 当前页面的主panel
	var mainPanel = new Ext.Panel({
				border : false,
				region : "center",
				layout : "border",
				items : [northPanel, centerPanel]
			});
	// 用于自适应的中心panel
	var mainCenterPanel = new Ext.Panel({
				border : false,
				layout : "border",
				autoScroll : true,
				items : [mainPanel]
			});
	/* 左边树监听 */
	/** ********** */
	new LeftTreeListener({
				modelName : '单户召测',
				processClick : function(p, node, e) {
					var obj = node.attributes.attributes;
					var type = node.attributes.type;
					if (type == "usr") {
						var tmnl = obj.tmnlAssetNo;
						if (listenerMain.state == "starting") {
							return !!Ext.Msg.alert("提示", "有召测任务在执行，请稍后");
						}
						baowen = function() {
							origFrameQryShow(obj.consNo, obj.consName,
									obj.terminalAddr, obj.protocolCode);
						};
						currentUser = new Ext.data.Record(obj);
						pnCenterGrid.getStore().baseParams = {
							"edataFinder.tmnlAssetNo" : tmnl
						};
						pnCenterGrid.getStore().load({
									params : {
										start : 0,
										limit : 50
									}
								});
						var pnId = "tmnl_" + tmnl + "_xxoo";
						pnRootNode = new Ext.tree.AsyncTreeNode({
									id : pnId,
									checked : false,
									text : "终端:" + obj.terminalAddr,
									iconCls : 'net-02',
									listeners : {
										checkchange : function(tn, c) {
											var childs = tn.childNodes;
											Ext.each(childs, function(n) {
														n.ui.checkbox.checked = c;
													});
										}
									}
								});

						iii = 1;
						var cms = leftUserGrid.getColumnModel();
						var num = cms.findColumnIndex("protocolCode");
						var renderer = cms.getRenderer(num);
						leftPointTree.setRootNode(pnRootNode);
						pnRootNode.expand();
						// leftPointTree.expandAll()
						loadDataById(emptyBean);
						userNameText.setValue(obj.consName);
						userAddrText.setValue(obj.terminalAddr);
						cnt(tmnl);
						protocolNameText.setValue(renderer(obj.protocolCode));
						pnRootNode.on('beforeclick', function(n) {
									pnBar.bind(pnRootNode);
									return true;
								});
					}
				},
				processUserGridSelect : function(sm, row, r) {
					var tmnl = r.get("tmnlAssetNo");
					if (listenerMain.state == "starting") {
						return !!Ext.Msg.alert("提示", "有召测任务在执行，请稍后");
					}
					baowen = function() {
						origFrameQryShow(r.get("consNo"), r.get("consName"), r
										.get("terminalAddr"), r
										.get("protocolCode"));
					};
					currentUser = r;
					pnCenterGrid.getStore().baseParams = {
						"edataFinder.tmnlAssetNo" : r.get("tmnlAssetNo")
					};
					pnCenterGrid.getStore().load({
								params : {
									start : 0,
									limit : 50
								}
							});
					var pnId = "tmnl_" + tmnl + "_xxoo";
					pnRootNode = new Ext.tree.AsyncTreeNode({
								id : pnId,
								checked : false,
								text : "终端:" + r.get("terminalAddr"),
								iconCls : 'net-02',
								listeners : {
									checkchange : function(tn, c) {
										var childs = tn.childNodes;
										Ext.each(childs, function(n) {
													n.ui.checkbox.checked = c;
												});
									}
								}
							});

					iii = 1;
					var cms = leftUserGrid.getColumnModel();
					var num = cms.findColumnIndex("protocolCode");
					var renderer = cms.getRenderer(num);
					leftPointTree.setRootNode(pnRootNode);
					pnRootNode.expand();
					// leftPointTree.expandAll()
					loadDataById(emptyBean);
					userNameText.setValue(r.get("consName"));
					userAddrText.setValue(r.get("terminalAddr"));
					cnt(tmnl);
					protocolNameText.setValue(renderer(r.get("protocolCode")));
					pnRootNode.on('beforeclick', function(n) {
								pnBar.bind(pnRootNode);
								return true;
							});
				}
			});
	renderModel(mainCenterPanel, '单户召测');

	Ext.getCmp('单户召测').on('activate', function() {
		if (!window.tmnlAssetNo) {
			return;
		}

		if (listenerMain.state == "starting") {
			return !!Ext.Msg.alert("提示", "有召测任务在执行，请稍后");
		}
		Ext.Ajax.request({
					url : "baseapp/dataFetch!dealTree.action",
					params : {
						start : 0,
						limit : 50,
						"node" : "tmnl_" + window.tmnlAssetNo + "_01"
					},
					success : function(response) {
						var o = Ext.decode(response.responseText);
						cnt(window.tmnlAssetNo);
						var bean = o.resultMap[0];
						loadDataById(emptyBean);
						var cms = leftUserGrid.getColumnModel();
						var num = cms.findColumnIndex("protocolCode");
						var renderer = cms.getRenderer(num);
						userNameText.setValue(bean["cons_name"]);
						userAddrText.setValue(bean["terminal_addr"]);
						protocolNameText
								.setValue(renderer(bean["protocol_code"]));
						baowen = function() {
							origFrameQryShow(bean["cons_no"],
									bean["cons_name"], bean["terminal_addr"],
									bean["protocol_code"]);
						};
						var m = {
							tmnlAssetNo : bean["tmnl_asset_no"],
							consName : bean["cons_name"],
							consNo : bean["cons_no"],
							orgName : bean["org_name"],
							terminalAddr : bean["terminal_addr"]
						};
						currentUser = new Ext.data.Record(m);
						var pnId = "tmnl_" + m.tmnlAssetNo + "_xxoo";
						pnRootNode = new Ext.tree.AsyncTreeNode({
									id : pnId,
									checked : false,
									text : "终端:" + m.terminalAddr,
									iconCls : 'net-02',
									listeners : {
										checkchange : function(tn, c) {
											var childs = tn.childNodes;
											Ext.each(childs, function(n) {
														n.ui.checkbox.checked = c;
													});
										}
									}
								});
						leftPointTree.setRootNode(pnRootNode);
						pnCenterGrid.getStore().baseParams = {
							"edataFinder.tmnlAssetNo" : window.tmnlAssetNo
						};
						pnCenterGrid.getStore().load({
									params : {
										start : 0,
										limit : 50
									}
								});
						pnRootNode.expand();
						pnRootNode.on('beforeclick', function(n) {
									pnBar.bind(pnRootNode);
									return true;
								});
						window.tmnlAssetNo = undefined;
					}
				});

			// window.dataType
	})
	window.tmnlAssetNo
			&& Ext.getCmp('单户召测').fireEvent("activate", Ext.getCmp('单户召测'));
	// treeOne.body.setOverflow('scroll');
	// treeTwo.body.setOverflow('scroll');
	// treeThree.body.setOverflow('scroll');
	// treeFour.body.setOverflow('scroll');
	leftPointTree.body.setOverflow('scroll');
	// leftPointTree.body.ghost("r", {
	// duration : 2
	// });

	// leftPointTree.body.slideIn('t', {
	// duration : 2
	// });
	function blink() {
		var f = setInterval(function() {
					stateLabel.getEl().slideIn('t', {
								duration : 0.5
							});
				}, 1100);
		this.stop = function() {
			clearInterval(f);
		}
		return f;
	}

	/** 与动画有关的方法** */
	leftPointTree.body.slideIn('t', {
				duration : 2
			});
	leftTabPanel.setActiveTab(tabTwo);
	// 权限控制
	Ext.Ajax.request({
				url : "./qrystat/losePowerMan!findHasPower.action",
				params : {
					url : "./sysMan/templateMan/codeManage.jsp"
				},
				success : function(response) {
					var o = Ext.decode(response.responseText);
					if (!o.hasPower) {
						Ext.each(powerObjs, function(p) {
									p.hide();
								});
					}
				}

			});
		// leftPointTree.body.stopFx();
});