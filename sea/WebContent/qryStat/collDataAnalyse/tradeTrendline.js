/**
 * 行业用电趋势分析
 * @author modified by jiangweichao on 2010-3-4 for bug1
 */
var ttArrayCm = new Array();
var colors = new Array('800080','FF8040','FFFF00','FF0080');
var ttCm;
var ttSm;

Ext.onReady(function() {
	var curveChgFlag = false;//如果曲线有值，则变化
	var tradeTimeModelList = '';
	var tradeTrendlineChartList = '';
	
	// 得到月份的天数
	function DayNumOfMonth(Year, Month) {
		var d = new Date(Year, Month, 0);
		return d.getDate();
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
	
	// 求两个时间的月份差 日期格式为 YYYY-MM
	function monthsBetween(DateOne, DateTwo) {
		var OneMonth = DateOne.substring(5);
		var OneYear = DateOne.substring(0, DateOne.indexOf('-'));
		var TwoMonth = DateTwo.substring(5);
		var TwoYear = DateTwo.substring(0, DateTwo.indexOf('-'));
		return Math.abs((Math.abs(OneYear - TwoYear)) * 12
				+ (OneMonth - TwoMonth));
	}
	
	function checkInitData(){
        var start = tt_startDate.getValue();
		var end = tt_endDate.getValue();
		var startFm = tt_startDate.getRawValue();
		var endFm = tt_endDate.getRawValue();
		var rg = tt_RadioGroup.getValue().getRawValue();
        if ((start - end) > 0) {
			Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
			return false;
		}
		if (!checkValidField(rg, startFm, endFm)) {
			return false;
		}
        return true;
	}
	
	// 校验，如果选择日期，不能超过1个月，如果选择月份，不能超过1年
	function checkValidField(t, s, e) {
		if (t == 1) {
			var tmp1 = daysBetween(e, s);
			var tmp2 = DayNumOfMonth(s.substring(0, 4), s.substring(5, 7));
			if ((tmp1 - tmp2) >= 0) {
				Ext.MessageBox.alert("提示", "选择日期不能超过1个月！");
				return false;
			}
		} else {
			if (monthsBetween(e, s) >= 12) {
				Ext.MessageBox.alert("提示", "选择日期不能超过1年！");
				return false;
			}
		}
		return true;
	}
	
	// 定义radio选择组
	var tt_RadioGroup = new Ext.form.RadioGroup({
		width : 100,
		height : 20,
		id : 'tt_RadioGroup',
		items : [new Ext.form.Radio({
					boxLabel : '分日',
					name : 'tt-radioGroup',
					inputValue : '1',
					checked : true,
					listeners : {
						'check' : function(r, c) {
							if (c) {
								tt_startDate.format = 'Y-m-d';
								tt_endDate.format = 'Y-m-d';
								tt_startDate.setValue(new Date().add(Date.DAY,
												-8));
								tt_endDate.setValue(new Date().add(Date.DAY,
												-1));
							};
						}
					}
				}), new Ext.form.Radio({
					boxLabel : '分月',
					name : 'tt-radioGroup',
					inputValue : '2',
					listeners : {
						'check' : function(r, c) {
							if (c) {
								tt_startDate.format = 'Y-m';
								tt_endDate.format = 'Y-m';
								tt_startDate
										.setValue(new Date().add(
												Date.MONTH, -2));
								tt_endDate
										.setValue(new Date().add(
												Date.MONTH, -1));
							};
						}
					}
				})]
	});

    //开始日期
	var tt_startDate = new Ext.form.DateField({
		id:'tt_startDate',
		fieldLabel : '从',
		name : 'tt_startDate',
		labelStyle : "text-align:right;",
		format: 'Y-m-d',
		editable:false,
	    labelSeparator:'',
	    value:new Date().add(Date.DAY, -8),
	    allowBlank:false,
	    width :100
    });
	
	//结束日期
	var tt_endDate = new Ext.form.DateField({
		id:'tt_endDate',
		fieldLabel : '到',
		name : 'tt_endDate',
		labelStyle : "text-align:right;",
		format: 'Y-m-d',
	    labelSeparator:'',
	    editable:false,
	    value:new Date().add(Date.DAY, -1),
	    allowBlank:false,
	    width :100
    });
    
	//查询按钮
	var tt_queryBtn = new Ext.Button({
			text : '查询',
			name : 'tt_query',
			width : 80,
		    labelSeparator:'',
		    handler:function(){
		        if(!checkInitData()){
                    return;
                }
                var rg = tt_RadioGroup.getValue().getRawValue();
                var interval; 
                if(1 == rg){
                	interval = daysBetween(tt_endDate.getRawValue(), tt_startDate.getRawValue());
                }else if(2 == rg){
                	interval = monthsBetween(tt_endDate.getRawValue(), tt_startDate.getRawValue());;
                }else{
                	return;
                }
                generateTtGridData(rg,interval);
                
		        var trades = ttSm.getSelections();
                if(Ext.isEmpty(trades)){
	  		     	Ext.MessageBox.alert("提示","请选择行业！");
	  		     	return;
	  		    }
                 
	  		    if(trades.length > 4){
					Ext.MessageBox.alert("提示","最多可以选择4个行业对比");
					return;
	  		    }
  
		        //循环组装查询数据列表
                var tradeList = new Array();
                for(var i = 0; i < trades.length; i++){
	  		    	if(!Ext.isEmpty(trades[i].get('tradeNo')) &&
	  		    	        !Ext.isEmpty(trades[i].get('tradeName'))){
	  		    	    tradeList[i] = trades[i].get('tradeNo')+ "!" + trades[i].get('tradeName');
                    }
                }
			    Ext.Ajax.request({
						url : 'qrystat/tradeTrendline!queryTradeTrendline.action',
						params : {
							startDate : tt_startDate.getValue(),
							endDate : tt_endDate.getValue(),
							rg:rg,
							interval:interval,
							tradeList : tradeList
						},
						success : function(response) {
							var result = Ext.decode(response.responseText);
							if (result) {
								tt_GridStore.removeAll();
								tt_GridStore.loadData(result);
								tt_Grid.getSelectionModel().selectAll();
								tradeTrendlineChartList = result.tradeTrendlineChartList;
								tradeTimeModelList = result.tradeTimeModelList;
		        	    	    generateTtLine(width,height);//生成曲线
							}
						}
					});
	            tt_Grid.reconfigure(tt_GridStore,ttCm);
	        }
	});
	
	//导出按钮
	var tt_exportBtn = new Ext.Button({
			text : '导出',
			name : 'tt_export',
			width : 80,
		    labelSeparator:'',
		    handler:function(){
	        }
	});
		
	// 行业用电趋势分析panel1
	var tradeTrendlineTopPnl = new Ext.Panel({
		border : false,
        layout:'table',
        region:'north',
        height: 40,
        layoutConfig : {
			columns :4
	    },
	    defaults: {height: 35},
        items : [{
			    layout : "form",
			    width:100,
			    labelWidth : 1,
			    border : false,
			    labelAlign: "right",
			    bodyStyle : 'padding:5px 0px 0px 5px',
			    items : [tt_RadioGroup]
			}, {
			    layout : "form",
			    width:150,
			    labelWidth : 25,
			    border : false,
			    labelAlign: "right",
			    bodyStyle : 'padding:5px 0px 0px 5px',
			    items : [tt_startDate]
			}, {
				layout : "form",
				border : false,
				width:150,
				labelWidth : 25,
				labelAlign: "right",
				bodyStyle : 'padding:5px 0px 0px 10px',
				items : [tt_endDate]
            },{
			    border : false,
				layout : "form",
				width:100,
				bodyStyle : 'padding:5px 0px 0px 20px',
				items : [tt_queryBtn]
		    }]		
	});

// 行业用电趋势分析panel22
	var ttlLineChartPnl = new Ext.Panel({
				border : false,
				bodyBorder : false,
				layout : 'fit',
				monitorResize : false
			});
			
    //用于实现fusionchart动态变化
	ttlLineChartPnl.on("afterlayout", function(view,layout){
			width = tradeTrendline2.getWidth();
	        height = tradeTrendline2.getHeight()-4;
	        generateTtLine(width,height);
	},ttlLineChartPnl);
	
    //曲线
	var ttlLineChart;
	function generateTtLine(width,height){
//		var xmlData = getMultiTradeXMLData(tradeTimeModelList,tradeTrendlineChartList,'','','','',20,20);
//		ttlLineChart = new FusionCharts("fusionCharts/MSLine.swf",
//				"tradeTrendlineChartId", width, height);
		var xmlData = getMultiTradeScrollXMLData(tradeTimeModelList,tradeTrendlineChartList,'','','',colors,20,20);
		ttlLineChart = new FusionCharts("fusionCharts/ScrollLine2D.swf",
				"tradeTrendlineChartId", width, height);		
		ttlLineChart.setDataXML(xmlData);
		ttlLineChart.setTransparent(true);
		if(!Ext.isEmpty(ttlLineChartPnl)){
			ttlLineChart.render(ttlLineChartPnl.getId());
		}
	}
	
// 行业用电趋势分析panel2
	var tradeTrendline2 = new Ext.Panel({
				border : false,
				region:'center',
				layout:'fit',
				bodyBorder : false,
				monitorResize : true,
				bodyStyle : 'padding:0px 2px 5px 2px',
				items:[ttlLineChartPnl]
			});

	// 行业用电趋势分析north面板
	var ttlpanel = new Ext.Panel({
				autoScroll : true,
				region:'north',
				layout:'border',
				height:300,
				border : false,
				items : [tradeTrendlineTopPnl,tradeTrendline2]
			});		
			
    //定义Grid的store
	var tt_GridStore = new Ext.data.Store({
	 	        proxy : new Ext.data.MemoryProxy(),
				reader : new Ext.data.JsonReader({
							root : 'tradeTrendlineList',
							idProperty: 'tradeNo'
						}, [
						   {name : 'tradeNo' },
						   {name : 'tradeName'},
						   {name : 'date0'},
						   {name : 'date1'}, 
						   {name : 'date2'}, 
						   {name : 'date3'}, 
						   {name : 'date4'}, 
						   {name : 'date5'}, 
						   {name : 'date6'},
						   {name : 'date7'},
						   {name : 'date8'},
						   {name : 'date9'},
						   {name : 'date10'},
						   {name : 'date11'}, 
						   {name : 'date12'}, 
						   {name : 'date13'}, 
						   {name : 'date14'}, 
						   {name : 'date15'}, 
						   {name : 'date16'},
						   {name : 'date17'},
						   {name : 'date18'},
						   {name : 'date19'},
						   {name : 'date20'},
						   {name : 'date21'}, 
						   {name : 'date22'}, 
						   {name : 'date23'}, 
						   {name : 'date24'}, 
						   {name : 'date25'}, 
						   {name : 'date26'},
						   {name : 'date27'},
						   {name : 'date28'},
						   {name : 'date29'},
						   {name : 'date30'},
						   {name : 'count'}
						   ])
		});			
		
	ttSm = new Ext.grid.CheckboxSelectionModel({});	
	
	rowNum = new Ext.grid.RowNumberer();
	
	generateTtGridData(1,7);
		
	var tt_Grid = new Ext.grid.GridPanel({
			store : tt_GridStore,
			border : false,
			region:'center',
			autoWidth : true,
			cm : ttCm,
			sm : ttSm, 
			stripeRows : true,
			autoScroll : true,
			viewConfig : {
					forceFit : false
				},
			tbar : [{
					xtype:'label',
					html : "<font font-weight:bold;>选择重点行业对比分析</font>"
			}, {xtype: 'tbfill'},
				{
					text : '删除选中行',
					iconCls: 'minus',
					handler : function() {//这里是一个取巧的方法，为了保证序号显示无问题，每次把所有列取出来，然后把没选中的重新组装写入store
				        var total = tt_GridStore.getCount();
				        var recordList = new Array();
				        for(var i = 0; i < total ; i= i+1){
				            if(!ttSm.isSelected(i)){
				            	var object = new Object();
				            	object.tradeNo = tt_GridStore.getAt(i).get('tradeNo');
					            object.tradeName = tt_GridStore.getAt(i).get('tradeName');
					            object.date0 = tt_GridStore.getAt(i).get('date0');
					            object.date1 = tt_GridStore.getAt(i).get('date1');
					            object.date2 = tt_GridStore.getAt(i).get('date2');
					            object.date3 = tt_GridStore.getAt(i).get('date3');
					            object.date4 = tt_GridStore.getAt(i).get('date4');
					            object.date5 = tt_GridStore.getAt(i).get('date5');
					            object.date6 = tt_GridStore.getAt(i).get('date6');
					            object.date7 = tt_GridStore.getAt(i).get('date7');
					            object.date8 = tt_GridStore.getAt(i).get('date8');
					            object.date9 = tt_GridStore.getAt(i).get('date9');
					            object.date10 = tt_GridStore.getAt(i).get('date10');
					            object.date11 = tt_GridStore.getAt(i).get('date11');
					            object.date12 = tt_GridStore.getAt(i).get('date12');
					            object.date13 = tt_GridStore.getAt(i).get('date13');
					            object.date14 = tt_GridStore.getAt(i).get('date14');
					            object.date15 = tt_GridStore.getAt(i).get('date15');
					            object.date16 = tt_GridStore.getAt(i).get('date16');
					            object.date17 = tt_GridStore.getAt(i).get('date17');
					            object.date18 = tt_GridStore.getAt(i).get('date18');
					            object.date19 = tt_GridStore.getAt(i).get('date19');
					            object.date20 = tt_GridStore.getAt(i).get('date20');
					            object.date21 = tt_GridStore.getAt(i).get('date21');
					            object.date22 = tt_GridStore.getAt(i).get('date22');
					            object.date23 = tt_GridStore.getAt(i).get('date23');
					            object.date24 = tt_GridStore.getAt(i).get('date24');
					            object.date25 = tt_GridStore.getAt(i).get('date25');
					            object.date26 = tt_GridStore.getAt(i).get('date26');
					            object.date27 = tt_GridStore.getAt(i).get('date27');
					            object.date28 = tt_GridStore.getAt(i).get('date28');
					            object.date29 = tt_GridStore.getAt(i).get('date29');
					            object.date30 = tt_GridStore.getAt(i).get('date30');
					            object.count = tt_GridStore.getAt(i).get('count');
				            	var records = new Ext.data.Record(object); 
				            	recordList.push(records);
				            }else{//如果被选中，则需要变动曲线
				            	var tradeTmpNo = tt_GridStore.getAt(i).get('tradeNo');
				            	if(!Ext.isEmpty(tradeTrendlineChartList)){
						        	Ext.each(tradeTrendlineChartList,function(obj){
						        		if(!Ext.isEmpty(obj['tradeNo'])){
						        			if(!Ext.isEmpty(tradeTmpNo)
						        					&&obj['tradeNo'] == tradeTmpNo){
						        				obj['list'] = '';
						        				obj['flag'] = false;
						        				obj['tradeName'] = '';
						        				obj['tradeNo'] = '';
						        				if(!curveChgFlag){
						        					curveChgFlag = true;
						        				}
						        			}
						        		}
						            });
						        }
				            }
				        }
				        tt_GridStore.removeAll();
				        tt_GridStore.add(recordList);
				        if(curveChgFlag){
						    generateTtLine(width,height);
						    curveChgFlag = false;
				        }
					}
			 }]
		});
		
		tt_Grid.getSelectionModel().on('rowdeselect', 
			function(sm,rowIndex,r){
			       if(!Ext.isEmpty(tradeTrendlineChartList)){
				        	Ext.each(tradeTrendlineChartList,function(obj){
				        		if(!Ext.isEmpty(obj['tradeNo'])){
				        			if(!Ext.isEmpty(r.get('tradeNo')) 
				        					&&obj['tradeNo'] == r.get('tradeNo')){
				        				obj['flag'] = false;//false表示不显示
				        				if(!curveChgFlag){
				        					curveChgFlag = true;
				        				}
				        			}
				        		}
				            });
				        }
				        if(curveChgFlag){
						    generateTtLine(width,height);
						    curveChgFlag = false;
				        }
				});
	
		tt_Grid.getSelectionModel().on('rowselect', 
			function(sm,rowIndex,r){
			        if(!Ext.isEmpty(tradeTrendlineChartList)){
				        	Ext.each(tradeTrendlineChartList,function(obj){
				        		if(!Ext.isEmpty(obj['tradeNo'])){
				        			if(!Ext.isEmpty(r.get('tradeNo')) 
				        					&&obj['tradeNo'] == r.get('tradeNo')){
				        				obj['flag'] = true;//true表示显示
				        				if(!curveChgFlag){
				        					curveChgFlag = true;
				        				}
				        			}
				        		}
				            });
				        }
				        if(curveChgFlag){
						    generateTtLine(width,height);
						    curveChgFlag = false;
				        }
				});
	// 设置顶层的panel
	var tradeTrendlinepanel = new Ext.Panel({
				autoScroll : true,
				layout:'border',
				border : false,
				items : [ttlpanel,tt_Grid]
			});
	renderModel(tradeTrendlinepanel,'行业用电趋势分析');
	
	//监听左边树点击事件
    var treeListener = new LeftTreeListener({
	    modelName : '行业用电趋势分析',
	    processClick : function(p, node, e) {
		    var obj = node.attributes.attributes;
		    var type = node.attributes.type;
	   	    if (!node.isLeaf()) {
				if (type == 'trade') {
					publicType = type;
					var object = new Object();
					object.tradeNo = obj.tradeNo;
					object.tradeName = node.text;
					var records = new Ext.data.Record(object); 
					if(tt_GridStore.getCount() == 0){
						//如果为0，则清空曲线数据
						tradeTimeModelList = '';
	                    tradeTrendlineChartList = '';
					}
					if(tt_GridStore.getCount() >= 10){
						Ext.MessageBox.alert('提示','数据项已经超过10条');
					}
					else{
					    if(tt_GridStore.query('tradeNo',obj.tradeNo,false).getCount() > 0){
						    Ext.MessageBox.alert('提示','该数据项已经添加');
					    }else{
						    tt_GridStore.add(records);
						    var array = new Array(records);
						    if(tt_GridStore.getCount() <= 4){
						        tt_Grid.getSelectionModel().selectRecords(array,true);
						    }
					    }
					}
				}else {
				    return true;
			    }
			} else {
				return true;
			}
   	    }
    });
});

//动态构建cm，为了标题
function generateTtGridData(rg,n) {
	if(!Ext.isEmpty(ttCm)){
		ttArrayCm.length = 0;
	}
	ttArrayCm.push(rowNum);
	ttArrayCm.push(ttSm);
	ttArrayCm.push({
			header : '行业名称',
			dataIndex : 'tradeName',
			width : 150,
			sortable : true,
			align : 'center'
		});
	if(1 == rg){
		for(var i = 0 ; i <= n ; i=i+1){
		    ttArrayCm.push({
					header : Ext.getCmp("tt_startDate").getValue().add(Date.DAY,+i).format("m月d日"),
					dataIndex : 'date' + i,
					align : 'right',
					width : 90,
					sortable : true,
					align : 'center',
					renderer : function(val) {
						if(Ext.isEmpty(val)){
							val = "";
						}
				        var html = '<div align = "right">' + val
						    + '</div>';
				        return html;
			        }
				});
		}
		ttArrayCm.push({
					header : '<div align=center>合计</div>',
					dataIndex : 'count',
					sortable : true,
					align : 'center',
					renderer : function(val) {
						if(Ext.isEmpty(val)){
							val = "";
						}
				        var html = '<div align = "right">' + val
						    + '</div>';
				        return html;
			        }
				});
	}else if(2 == rg){
		for(var i = 0 ; i <= n ; i=i+1){
		    ttArrayCm.push({
					header : Ext.getCmp("tt_startDate").getValue().add(Date.MONTH,+i).format("m月"),
					dataIndex : 'date' + i,
					align : 'right',
					width : 90,
					sortable : true,
					align : 'center',
					renderer : function(val) {
						if(Ext.isEmpty(val)){
							val = "";
						}
				        var html = '<div align = "right">' + val
						    + '</div>';
				        return html;
			        }
				});
		}
		ttArrayCm.push({
					header : '<div align=center>合计</div>',
					dataIndex : 'count',
					sortable : true,
					align : 'center',
					renderer : function(val) {
						if(Ext.isEmpty(val)){
							val = "";
						}
				        var html = '<div align = "right">' + val
						    + '</div>';
				        return html;
			        }
				});
	}
    ttCm = new Ext.grid.ColumnModel(ttArrayCm);
}

/**
 * @描述: 返回多系列图表数据，针对曲线不显示value，同时调整曲线长度和宽度用于显示行业曲线
 * @param {} tradeTimeModelList
 * @param {} tradeTrendlineChartList
 * @param {} caption 图表标题
 * @param {} xAxisName x轴坐标名
 * @param {} yAxisName y轴坐标名
 * @param {} colors 图表颜色
 * @param {} leftWidth 图表显示部分左边空白宽度
 * @param {} rightWidth 图表显示部分右边空白宽度
 * @author 姜炜超
 */
function getMultiTradeXMLData(tradeTimeModelList,tradeTrendlineChartList,caption,xAxisName,yAxisName,colors,leftWidth,rightWidth){
	var xmlData = "<graph baseFont='宋体' baseFontSize='14' rotateYAxisName='0'  xaxisname='"+xAxisName+"' " +
			      "yaxisname='"+yAxisName+"' " +
			      "hovercapbg='DEDEBE' " +
			      "hovercapborder='889E6D' " +
			      "rotateNames='0' " +
			      "showValues='0' " +
			      "numdivlines='9' " +
			      "divLineColor='CCCCCC' " +
			      "formatNumberScale='0'"+
			      "divLineAlpha='80' " +
			      "decimalPrecision='0' " +
			      "legendPosition='BOTTOM'" +
			      "showAlternateHGridColor='1' " +
			      "AlternateHGridAlpha='30' " +
			      "AlternateHGridColor='CCCCCC' " +
			      " chartRightMargin='" + rightWidth + "' " +
			      " chartLeftMargin='" + leftWidth + "' " +
			      "caption='"+caption+"' " +
			      "subcaption='' >";

    var str = "<categories>";
    Ext.each(tradeTimeModelList,function(obj){
        str += "<category label='"+obj['time']+"' />";
    });
    str += "</categories>";

    xmlData = xmlData+str;
	
    if(!Ext.isEmpty(tradeTrendlineChartList)){
   	 Ext.each(tradeTrendlineChartList,function(obj){
   		var color = randomColor();
   		if(!Ext.isEmpty(obj['flag'])&& !Ext.isEmpty(obj['tradeName']) && obj['flag']){
   			 var str1 = "<dataset seriesName='" + obj['tradeName'] +"'"+" color='"+color+"' anchorBorderColor='"+color+"' anchorBgColor='"+color+"'>";
   		     
   		     Ext.each(obj['list'],function(obj1){
   		          if(obj1['flag']){
   		               str1 += "<set />";
   		          }else{
   		        	   str1 += "<set value='"+obj1['data']+"'/>";
   		          }
   		     });
   		     str1 += "</dataset>";
   		     xmlData = xmlData+str1;
   		 }
        });
    }

	xmlData = xmlData+'</graph>';
	return xmlData;
}

/**
 * @描述 生成随机的颜色
 * @return {}
 */
function randomColor() {
	return ''+(Math.random()*0xffffff<<0).toString(16); 
}

/**
 * @描述: 返回多系列图表数据，针对曲线不显示value，同时调整曲线长度和宽度用于显示行业的滚动曲线
 * @PS: formatNumberScale表示不显示k，m而是直接显示数字，legendPosition表示标签显示在底部还是右边
 * @param {} tradeTimeModelList
 * @param {} tradeTrendlineChartList
 * @param {} caption 图表标题
 * @param {} xAxisName x轴坐标名
 * @param {} yAxisName y轴坐标名
 * @param {} colors 图表颜色
 * @param {} leftWidth 图表显示部分左边空白宽度
 * @param {} rightWidth 图表显示部分右边空白宽度
 * @author 姜炜超
 */
function getMultiTradeScrollXMLData(tradeTimeModelList,tradeTrendlineChartList,caption,xAxisName,yAxisName,colors,leftWidth,rightWidth){
	var xmlData = "<graph baseFont='宋体' baseFontSize='14' rotateYAxisName='0'  xaxisname='"+xAxisName+"' " +
			      "yaxisname='"+yAxisName+"' " +
			      "hovercapbg='DEDEBE' " +
			      "hovercapborder='889E6D' " +
			      "rotateNames='0' " +
			      "showValues='0' " +
			      "numdivlines='9' " +
			      "divLineColor='CCCCCC' " +
			      "formatNumberScale='0'"+
			      "divLineAlpha='80' " +
//			      "labelDisplay='Rotate' slantLabels='1' "+
			      "decimalPrecision='0' " +
			      "legendPosition='BOTTOM'" +
			      "showAlternateHGridColor='1' " +
			      "AlternateHGridAlpha='30' " +
			      "AlternateHGridColor='CCCCCC' " +
			      " chartRightMargin='" + rightWidth + "' " +
			      " chartLeftMargin='" + leftWidth + "' " +
			      "caption='"+caption+"' " +
			      "numVisiblePlot='12'"+
			      "subcaption=''>";

    var str = "<categories>";
    Ext.each(tradeTimeModelList,function(obj){
        str += "<category label='"+obj['time']+"'/>";
    });
    str += "</categories>";

    xmlData = xmlData+str;
	var i = 0;
    if(!Ext.isEmpty(tradeTrendlineChartList) ){
   	Ext.each(tradeTrendlineChartList,function(obj){
   		var color ;
		if(colors == null){
			color = randomColor();
		}else{
			if(i < colors.length){
			    color = colors[i];
			}else{
			    color = randomColor();
			}
		}
   		if(!Ext.isEmpty(obj['flag']) && !Ext.isEmpty(obj['tradeName']) && obj['flag']){
   			 var str1 = "<dataset seriesName='" + obj['tradeName'] +"'"+" color='"+color+"' anchorBorderColor='"+color+"' anchorBgColor='"+color+"'>";
   		     Ext.each(obj['list'],function(obj1){
   		          if(obj1['flag']){
   		               str1 += "<set />";
   		          }else{
   		        	   str1 += "<set value='"+obj1['data']+"'/>";
   		          }
   		     });
   		     str1 += "</dataset>";
   		     xmlData = xmlData+str1;
   		 }
   		 i=i+1;
        });
    }
//	xmlData = xmlData+"<styles><definition><style name='myLabelsFont' type='font' font='宋体' size='12' color='666666' bold='0'/>"+
//        "</definition><application><apply toObject='DataLabels' styles='myLabelsFont' /></application></styles>"+
//        "</graph>";
    xmlData = xmlData+"</graph>";
	return xmlData;
}
