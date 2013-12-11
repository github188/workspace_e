/**
 * 客户用电排名分析
 * 
 * @author modified by jiangweichao on 2010-3-8 for bug1
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

// 定义全局变量
var psa_keys1 = new Array('label', 'rate'); // chatr1参数
var psa_Array1 = [];// chatr1参数
var psa_rate = 0;// chart2参数
var psa_nodeId;// 左边树选择编号
var psa_nodeType;// 左边树选择类型
var psa_orgType;
var powerSortAnalyseChart1;
var powerSortAnalyseChart2;
var height = 0;
var width = 0;
Ext.onReady(function() {
//	Ext.namespace('My.Form');
//	My.Form.TextField = Ext.extend(Ext.form.TextField, {
//		tooltip : {},
//		setValue : function(v){
//	        this.value = v;
//	        if(this.rendered){
//	            this.el.dom.value = (Ext.isEmpty(v) ? '' : v);
//	            this.validate();
//	        }
//	        new Ext.ToolTip( {
//				target : this.id,
//				trackMouse : false,
//				draggable : true,
//				maxWidth : 200,
//				minWidth : 100,
//				title : this.tooltip.title,
//				html : this.value,
//				text : this.value
//			});
//	        return this;
//	    }
//
//	});
	// Ext.reg('myTextField', My.Form.TextField);

		// 节点名
		var psa_nodeName = new Ext.form.TextField( {
			fieldLabel : "节点名<font color='red'>*</font>",
			name : 'psa_nodeName',
			id : 'psa_nodeName',
			labelSeparator : '',
			readOnly : true,
			labelStyle : "text-align:right;",
			emptyText : '请选择统计节点...',
			tooltip : {
				title : "统计节点",
				text : ''
			},
			width : 140,
			setValue : function(v){
		        this.value = v;
		        if(this.rendered){
		            this.el.dom.value = (Ext.isEmpty(v) ? '' : v);
		            this.validate();
		        }
		        new Ext.ToolTip( {
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
		var psa_startDate = new Ext.form.DateField( {
			id : 'psa_startDate',
			fieldLabel : '从',
			name : 'psa_startDate',
			labelStyle : "text-align:right;",
			format : 'Y-m-d',
			editable : false,
			labelSeparator : '',
			value : new Date().add(Date.MONTH, -1),
			allowBlank : false,
			width : 100
		});

		// 结束日期
		var psa_endDate = new Ext.form.DateField( {
			id : 'psa_endDate',
			fieldLabel : '到',
			name : 'psa_endDate',
			labelStyle : "text-align:right;",
			format : 'Y-m-d',
			labelSeparator : '',
			editable : false,
			value : new Date().add(Date.DAY, -1),
			allowBlank : false,
			width : 100
		});

		// 排名靠前
		var psa_sort = new Ext.form.TextField( {
			fieldLabel : "排名靠前",
			name : 'psa_sort',
			id : 'psa_sort',
			labelSeparator : '',
			readOnly : false,
			value : '10',
			labelStyle : "text-align:right;",
			width : 50
		});

		// 查询按钮
		var tt_queryBtn = new Ext.Button( {
			text : '查询',
			name : 'tt_queryBtn',
			width : 60,
			labelSeparator : '',
			handler : function() {
				if (!checkPSAInitData()) {
					return;
				}
				// 加载grid数据
			psa_gridStore.removeAll();
			psa_gridStore.baseParams = {
				startDate : psa_startDate.getValue(),
				endDate : psa_endDate.getValue(),
				nodeId : psa_nodeId,
				nodeType : psa_nodeType,
				orgType : psa_orgType,
				sort : trim(psa_sort.getValue())
			};
			psa_gridStore.load();

			// 生成左边曲线
			psa_Array1.length = 0;
			Ext.Ajax.request( {
				url : './qrystat/powerSortAnalyse!loadChartData.action',
				params : {
					startDate : psa_startDate.getValue(),
					endDate : psa_endDate.getValue(),
					nodeId : psa_nodeId,
					nodeType : psa_nodeType,
					orgType : psa_orgType,
					sort : trim(psa_sort.getValue())
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (result == null)
						return true;
					var obj = new Object();
					obj.label = "其他用户电量";
					obj.rate = result.totalPapE - result.sortPapE;
					psa_Array1.push(obj);

					obj = new Object();
					obj.label = "重点户电量";
					obj.rate = result.sortPapE;
					psa_Array1.push(obj);

					// 生成右边曲线
				psa_rate = 0;
				generateChart1(width, height);
				generateChart1(width, height);
				return;
			},
			failure : function() {
				Ext.MessageBox.alert("提示", "失败");
				return;
			}
			});
		}
		});

		// 校验函数
		function checkPSAInitData() {
			if (Ext.isEmpty(psa_nodeName.getValue())) {
				Ext.MessageBox.alert("提示", "请从左边树选择节点！");
				return false;
			}
			var start = psa_startDate.getValue();
			var end = psa_endDate.getValue();
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
			var ps = psa_sort.getValue();
			if (Ext.isEmpty(ps)) {
				Ext.MessageBox.alert("提示", "请输入排名！");
				return false;
			} else {
				if (!this.isNum(trim(ps))) {
					Ext.MessageBox.alert("提示", "排名必须输入数字！");
					return false;
				}
			}
			return true;
		}

		// 求两个时间的天数差 日期格式为 YYYY-MM-dd
		function daysBetween(DateOne, DateTwo) {
			var OneMonth = DateOne.substring(5, DateOne.lastIndexOf('-'));
			var OneDay = DateOne.substring(DateOne.length, DateOne
					.lastIndexOf('-') + 1);
			var OneYear = DateOne.substring(0, DateOne.indexOf('-'));
			var TwoMonth = DateTwo.substring(5, DateTwo.lastIndexOf('-'));
			var TwoDay = DateTwo.substring(DateTwo.length, DateTwo
					.lastIndexOf('-') + 1);
			var TwoYear = DateTwo.substring(0, DateTwo.indexOf('-'));
			var cha = ((Date.parse(OneMonth + '/' + OneDay + '/' + OneYear) - Date
					.parse(TwoMonth + '/' + TwoDay + '/' + TwoYear)) / 86400000);
			return Math.abs(cha);
		}

		// 客户用电排名分析panel1
		var powerSortAnalyseTopPnl = new Ext.Panel( {
			region : 'north',
			border : false,
			height : 30,
			items : [ {
				layout : "column",
				style : "padding:5px",
				border : false,
				items : [ {
					columnWidth : .3,
					layout : "form",
					labelWidth : 50,
					border : false,
					items : [ psa_nodeName ]
				}, {
					columnWidth : .2,
					layout : "form",
					labelWidth : 20,
					border : false,
					items : [ psa_startDate ]
				}, {
					columnWidth : .2,
					layout : "form",
					labelWidth : 20,
					border : false,
					items : [ psa_endDate ]
				}, {
					columnWidth : .18,
					layout : "form",
					labelWidth : 50,
					border : false,
					items : [ psa_sort ]
				}, {
					columnWidth : .12,
					layout : "form",
					border : false,
					items : [ tt_queryBtn ]
				} ]
			} ]
		});

		// -----------------------------------------------------------------------------
		// 客户用电排名情况panel21
		var powerSortAnalyse21 = new Ext.Panel( {
			border : false,
			bodyBorder : false,
			layout : 'fit',
			monitorResize : false
		});

		// 客户用电排名情况panel22
		powerSortAnalyse22 = new Ext.Panel( {
			border : false,
			bodyBorder : false,
			layout : 'fit',
			monitorResize : false
		});

		// 用于实现fusionchart动态变化
		powerSortAnalyse21.on("afterlayout", function(view, layout) {
			width = powerSortAnalyseMidPnl.getWidth() / 2;
			height = powerSortAnalyseMidPnl.getHeight() - 4;
			generateChart1(width, height);
		}, powerSortAnalyse21);

		// 用于实现fusionchart动态变化
		powerSortAnalyse22.on("afterlayout", function(view, layout) {
			width = powerSortAnalyseMidPnl.getWidth() / 2;
			height = powerSortAnalyseMidPnl.getHeight() - 4;
			generateChart2(width, height);
		}, powerSortAnalyse22);

		// 节点名
		var psa_remark = new Ext.form.TextField( {
			fieldLabel : "",
			name : 'psa_remark',
			id : 'psa_remark',
			labelSeparator : '',
			readOnly : true,
			labelStyle : "text-align:right;",
			emptyText : '该图显示的是具体某个客户用电量在查询结果中的比例',
			width : 200
		});

		psa_rmkPanel = new Ext.Panel( {
			border : false,
			bodyBorder : false,
			layout : 'form',
			region : 'south',
			height : 20,
			items : [ psa_remark ]
		});

		// 动态生成左边chart1
		function generateChart1(width, height) {
			var tmpH = height ;
			var xmlData1 = getSingleXMLData(psa_Array1, psa_keys1, '', '', '');
			powerSortAnalyseChart1 = new FusionCharts("fusionCharts/Pie2D.swf",
					"powerSortAnalyseChartId", width, tmpH);
			powerSortAnalyseChart1.setDataXML(xmlData1);
			powerSortAnalyseChart1.setTransparent(true);
			powerSortAnalyseChart1.render(powerSortAnalyse21.getId());
		}
		// 动态生成右边chart2
		function generateChart2(width, height) {
			var tmpH = height ;
			powerSortAnalyseChart2 = new FusionCharts(
					"fusionCharts/AngularGauge.swf", "powerSortAnalyseChartId",
					width, tmpH);
			var xmlData2 = getAngularChartXmlData(psa_rate, width, tmpH);
			powerSortAnalyseChart2.setDataXML(xmlData2);
			powerSortAnalyseChart2.setTransparent(true);
			powerSortAnalyseChart2.render(powerSortAnalyse22.getId());
		}

		// 客户用电排名情况panel2
		var powerSortAnalyseMidPnl = new Ext.Panel( {
			region : 'center',
			border : false,
			bodyBorder : false,
			layout : 'fit',
			monitorResize : true,
			items : [ {
				layout : 'column',
				border : false,
				items : [ {
					columnWidth : .5,
					border : false,
					layout : 'fit',
					items : [ powerSortAnalyse21 ]
				}, {
					columnWidth : .5,
					border : false,
					layout : 'fit',
					items : [ powerSortAnalyse22 ]
				} ]
			} ]
		});

		// 定义Grid的store（全局变量）
		psa_gridStore = new Ext.data.Store( {
			proxy : new Ext.data.HttpProxy( {
				url : './qrystat/powerSortAnalyse!loadGridData.action'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'psaList',
				totalProperty : 'totalCount',
				idProperty : 'sort'
			}, [ {
				name : 'sort'
			}, {
				name : 'consNo'
			}, {
				name : 'consName'
			}, {
				name : 'runCap'
			}, {
				name : 'tradeName'
			}, {
				name : 'voltGrade'
			}, {
				name : 'bqyd'
			}, {
				name : 'tqyd'
			}, {
				name : 'bqtb'
			}, {
				name : 'powerRate'
			} ]),
			sortInfo : {
				field : 'sort',
				direction : 'ASC'
			}
		});

		var psa_gridCm = new Ext.grid.ColumnModel(
				[
						{
							header : '排名',
							dataIndex : 'sort',
							sortable : true,
							width : 50,
							align : 'center'
						},
						{
							header : '用户编号',
							dataIndex : 'consNo',
							width : 120,
							sortable : true,
							align : 'center',
							renderer : function(s, m, rec) {
								return "<a href='javascript:' onclick='generateLinkChart("
										+ rec.get('powerRate')
										+ ","
										+ width
										+ ","
										+ height
										+ ")' title='点击连接在右上图中显示该客户用电量在查询结果中所占的比例'>"
										+ s + "</a>";
							}
						},
						{
							header : '用户名称',
							dataIndex : 'consName',
							sortable : true,
							width : 160,
							align : 'center',
							renderer : function(val) {
								if (Ext.isEmpty(val)) {
									val = "";
								}
								var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="'
										+ val + '">' + val + '</div>';
								return html;
							}
						},
						{
							header : '运行容量',
							dataIndex : 'runCap',
							sortable : true,
							align : 'center',
							renderer : function(val) {
								if (Ext.isEmpty(val)) {
									val = "";
								}
								var html = '<div align = "right">' + val + '</div>';
								return html;
							}
						},
						{
							header : '行业名称',
							dataIndex : 'tradeName',
							sortable : true,
							width : 160,
							align : 'center',
							renderer : function(val) {
								if (Ext.isEmpty(val)) {
									val = "";
								}
								var html = '<div align = "left" ext:qtitle="行业名称" ext:qtip="'
										+ val + '">' + val + '</div>';
								return html;
							}
						},
						{
							header : '电压等级',
							dataIndex : 'voltGrade',
							sortable : true,
							align : 'center'
						},
						{
							header : '本期用电',
							dataIndex : 'bqyd',
							sortable : true,
							align : 'center',
							renderer : function(val) {
								if (Ext.isEmpty(val)) {
									val = "";
								}
								var html = '<div align = "right">' + val + '</div>';
								return html;
							}
						},
						{
							header : '同期用电',
							dataIndex : 'tqyd',
							sortable : true,
							align : 'center',
							renderer : function(val) {
								if (Ext.isEmpty(val)) {
									val = "";
								}
								var html = '<div align = "right">' + val + '</div>';
								return html;
							}
						},
						{
							header : '本期同比(%)',
							dataIndex : 'bqtb',
							sortable : true,
							align : 'center',
							renderer : function(val) {
								if (Ext.isEmpty(val)) {
									val = "";
								}
								var html = '<div align = "right">' + val + '</div>';
								return html;
							}
						} ]);

		var psa_gridPanel = new Ext.grid.GridPanel( {
			region : 'center',
			autoScroll : true,
			stripeRows : true,
			viewConfig : {
				forceFit : false
			},
			cm : psa_gridCm,
			store : psa_gridStore,
			bbar : new Ext.ux.MyToolbar( {
				enableExpAll : true,
				store : psa_gridStore
			}),
			tbar : [ {
				xtype : 'label',
				html : "<font font-weight:bold;>客户用电排名情况</font>"
			} ]
		});

		var psa_northPanel = new Ext.Panel( {
			// title : "客户用电排名情况",
			border : false,
			autoScroll : true,
			bodyBorder : false,
			region : 'north',
			layout : 'border',
			height : 290,
			items : [ powerSortAnalyseTopPnl, powerSortAnalyseMidPnl ]
		});
		// 设置顶层的客户用电排名分析panel
		var powerSortAnalysepanel = new Ext.Panel( {
			// title : "客户用电排名情况",
			autoScroll : true,
			border : false,
			layout : 'border',
			items : [ psa_northPanel, psa_gridPanel ]
		});
		renderModel(powerSortAnalysepanel, '客户用电排名分析');

		/**
		 * chenjianguo 响应主页事件
		 */

		if (paraDay != 'null') {
			if (paraDay == 0) {
				psa_startDate.setValue(new Date().add(Date.MONTH, -1));
				psa_endDate.setValue(new Date());
			} else {
				paraDay.substr(1, paraDay.length);
				psa_startDate.setValue(new Date().add(Date.DAY,
						-parseInt(paraDay)));
				psa_endDate.setValue(new Date());
			}

			// 获取组织机构信息
			Ext.Ajax.request( {
				url : './qrystat/powerSortAnalyse!getOOrgById.action',
				success : function(response) {
					var result = Ext.decode(response.responseText);
					var org = result.org;
					psa_nodeId = org.orgNo;
					psa_nodeType = 'org';
					psa_orgType = org.orgType;

					psa_nodeName.setValue(org.orgName);

					// 加载grid数据
				psa_gridStore.removeAll();
				psa_gridStore.baseParams = {
					startDate : psa_startDate.getValue(),
					endDate : psa_endDate.getValue(),
					nodeId : psa_nodeId,
					nodeType : psa_nodeType,
					orgType : psa_orgType,
					sort : trim(psa_sort.getValue())
				};
				psa_gridStore.load();

				// 生成左边曲线
				psa_Array1.length = 0;
				Ext.Ajax.request( {
					url : './qrystat/powerSortAnalyse!loadChartData.action',
					params : {
						startDate : psa_startDate.getValue(),
						endDate : psa_endDate.getValue(),
						nodeId : psa_nodeId,
						nodeType : psa_nodeType,
						orgType : psa_orgType,
						sort : trim(psa_sort.getValue())
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						if (result == null)
							return true;
						var obj = new Object();
						obj.label = "其他用户电量";
						obj.rate = result.totalPapE - result.sortPapE;
						psa_Array1.push(obj);

						obj = new Object();
						obj.label = "重点户电量";
						obj.rate = result.sortPapE;
						psa_Array1.push(obj);

						// 生成右边曲线
					psa_rate = 0;
					generateChart1(width, height);
					generateChart1(width, height);
					return;
				},
				failure : function() {
					Ext.MessageBox.alert("提示", "失败");
					return;
				}
				});

			},
			failure : function() {
				Ext.MessageBox.alert('错误', '获取组织机构信息失败！');
			}
			});

			// 加载grid数据
			psa_gridStore.removeAll();
			psa_gridStore.baseParams = {
				startDate : psa_startDate.getValue(),
				endDate : psa_endDate.getValue(),
				nodeId : psa_nodeId,
				nodeType : psa_nodeType,
				orgType : psa_orgType,
				sort : trim(psa_sort.getValue())
			};
			psa_gridStore.load();

			// 生成左边曲线
			psa_Array1.length = 0;
			Ext.Ajax.request( {
				url : './qrystat/powerSortAnalyse!loadChartData.action',
				params : {
					startDate : psa_startDate.getValue(),
					endDate : psa_endDate.getValue(),
					nodeId : psa_nodeId,
					nodeType : psa_nodeType,
					orgType : psa_orgType,
					sort : trim(psa_sort.getValue())
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (result == null)
						return true;
					var obj = new Object();
					obj.label = "其他用户电量";
					obj.rate = result.totalPapE - result.sortPapE;
					psa_Array1.push(obj);

					obj = new Object();
					obj.label = "重点户电量";
					obj.rate = result.sortPapE;
					psa_Array1.push(obj);

					// 生成右边曲线
				psa_rate = 0;
				generateChart1(width, height);
				generateChart1(width, height);
				return;
			},
			failure : function() {
				Ext.MessageBox.alert("提示", "失败");
				return;
			}
			});
		}

		// 监听左边树点击事件
		var treeListener = new LeftTreeListener( {
			modelName : '客户用电排名分析',
			processClick : function(p, node, e) {
				var obj = node.attributes.attributes;
				var type = node.attributes.type;
				if (type == 'org') {
					psa_nodeId = obj.orgNo;
					psa_orgType = obj.orgType;
					psa_nodeType = type;
					Ext.getCmp("psa_nodeName").setValue(node.text);
				} else if (type == 'sub') {
					psa_nodeId = obj.subsId;
					psa_nodeType = type;
					Ext.getCmp("psa_nodeName").setValue(node.text);
				} else if (type == 'line') {
					psa_nodeId = obj.lineId;
					psa_nodeType = type;
					Ext.getCmp("psa_nodeName").setValue(node.text);
				} else if (type == 'trade') {
					psa_nodeId = obj.tradeNo;
					psa_nodeType = type;
					Ext.getCmp("psa_nodeName").setValue(node.text);
				} else if (type == 'tg') {
					psa_nodeId = obj.tgId;
					psa_nodeType = type;
					Ext.getCmp("psa_nodeName").setValue(node.text);
				} else {
					return true;
				}
			}
		});
	});

// 点击Grid链接到图形
function generateLinkChart(rate, width, height) {
	if (Ext.isEmpty(rate)) {
		psa_rate = 0;
	} else {
		psa_rate = rate;
	}
	var tmpH = height * 0.9;
	var xmlData2 = getAngularChartXmlData(psa_rate, width, tmpH);
	powerSortAnalyseChart2 = new FusionCharts("fusionCharts/AngularGauge.swf",
			"powerSortAnalyseChartId", width, tmpH);
	powerSortAnalyseChart2.setDataXML(xmlData2);
	powerSortAnalyseChart2.setTransparent(true);
	powerSortAnalyseChart2.render(powerSortAnalyse22.getId());
}

/**
 * @描述: 返回AngularGauge数据
 * @param rate 比例
 * @author 姜炜超
 */
function getAngularChartXmlData(rate, width, height){
	var xmlData = " <chart caption='' palette='2' bgAlpha='0' bgColor='FFFFFF' lowerLimit='0' "+
	              " upperLimit='100' numberSuffix='%25' showBorder='0' basefontColor='000000' "+
	              " chartTopMargin='25' chartBottomMargin='55' chartLeftMargin='25' chartRightMargin='25' "+ 
	              " toolTipBgColor='009999' gaugeFillMix='{dark-10},{light-70},{dark-10}' gaugeFillRatio='3'" +
	              " pivotRadius='8' gaugeInnerRadius='50%' tickValueDistance='20'> <colorRange> "+
	              " <color minValue='0' maxValue='45' code='FF654F' /> "+
	              " <color minValue='0' maxValue='45' code='FF654F' />  "+
	              " <color minValue='45' maxValue='80' code='F6BD0F' /> "+
	              " <color minValue='80' maxValue='100' code='8BBA00' />"+
	              " </colorRange> <dials> <dial value='" + rate +"'"+
	              " rearExtension='10' baseWidth='10' /> "+
	              " </dials> <trendpoints> <point value='62' displayValue='Average' useMarker='0' markerRadius='8' dashed='1' dashLen='2' dashGap='2' /> "+
	              " </trendpoints> <annotations> <annotationGroup id='Grp1' showBelow='1'>"+
	              " <annotation type='rectangle' x='5' y='5' toX='" + width + "'" + " toY='" + height + "'" + " radius='10' color='#FDF6EC,#FDF6EC' showBorder='1' /> "+
	              " </annotationGroup> </annotations> <styles> <definition>"+
	              " <style name='RectShadow' type='shadow' strength='3' /> "+
	              " <style name='trendvaluefont' type='font' bold='1' borderColor='FFFFDD' /> "+
	              " </definition> <application> <apply toObject='Grp1' styles='RectShadow' /> "+
	              " <apply toObject='Trendvalues' styles='trendvaluefont' />  </application> </styles> </chart>";
	return xmlData;
}
