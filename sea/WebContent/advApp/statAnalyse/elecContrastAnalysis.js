// 测量点日冻结总及分相有用功率------------------------------------
var elecorgNo = '';//单位编码
var elecorgType = '';//单位类型
var elecorgName = '';//单位名称
var elecMsg = new Array();//用来显示的值--单位名称
var elecMsgNew = new Array();//用来交互的值--单位编码
var submitStoreName = new Array();//添加按钮框中的显示数据
var submitStoreNo = new Array();//添加按钮框的中后台交互数据
var arrayValue = new Array();
var arrayName = new Array();
Ext.onReady(function(){
var treels = new LeftTreeListener({
			modelName : '电量对比分析',
			processClick : function(p, node, e) {
				var obj = node.attributes.attributes;
				var type = node.attributes.type;
				var hasSecond=0;
				var sameType;
				
				if (!node.isLeaf()) {
					elecorgNo = obj.orgNo;
					elecorgType = obj.orgType;
					
					if(elecorgName!=node.text && Ext.getCmp("elecType").getValue() == '1'){
						
						if(elecMsg.length>3){
//						elecMsg.pop();
//						elecMsgNew.pop();
						   Ext.Msg.alert('提示','只能选择四项进行对比');
					    	return false;
					    } 
					    if(elecorgType=='02'){
					    	Ext.Msg.alert('提示','不支持选择省级单位');
					    	return false;
					    }
					    
					    if(elecorgType=='03'||elecorgType=='04'){
					    
					    var same=1;	
					    for(var i=0;i<elecMsg.length;i++){
					    	//按供电单位对比 中 同级可以对比，不同级不能选择,不是供电单位不能选
					    	
					    	if(elecMsgNew[i].length!=elecorgNo.length)
					    	{
					    	same=0;
					    	Ext.Msg.alert('提示','请选择同一级别的供电单位');
					    	}
					    	//不能选择相同的供电单位
					    	if(elecMsgNew[i]==elecorgNo)
					    	{
					    	same=0;
					    	Ext.Msg.alert('提示','选择了重复的供电单位');
					    	}
					    }
					      if(same==1){	
						  elecorgName = node.text;
						  elecMsg.push(elecorgName);
						  elecMsgNew.push(elecorgNo);
					      Ext.getCmp("elecMessage").setValue(elecMsg.join("\n"));
					      }
					    }
					    else
					    Ext.Msg.alert('提示','请选择县级以上供电单位');
					    
					}					
				} else {
					return true;
				}
			},
			processUserGridSelect : function(cm, row, record) {
			}
		});
		
// =========================================================曲线展示
		//随机获取颜色
		function randomColor() {
	return '' + (Math.random() * 0xffffff << 0).toString(16);
};

//生成对应xml数据
function setelecByorgNo(dateData) {
	var xmlData = '<chart caption="" '
				+ 'subcaption="" lineThickness="1" '
				+ 'showValues="0" formatNumberScale="0" anchorRadius="2" '
				+ 'divLineAlpha="20" divLineColor="CC3300" divLineIsDashed="1" '
				+ 'showAlternateHGridColor="1" alternateHGridAlpha="5" alternateHGridColor="CC3300" '
			+ 'shadowAlpha="40" labelStep="2" numvdivlines="5" chartRightMargin="35" '
			+ 'bgColor="FFFFFF,CC3300" showBorder="0" bgAngle="270" bgAlpha="10,10">';
	xmlData += "<categories>";
			var statDate = '';
	Ext.each(dateData, function(obj) {
		if(statDate != obj['statDate'].substring(0,10)){
				xmlData += "<category label='" + obj['statDate'].substring(0,10) + "' />";
				statDate = obj['statDate'].substring(0,10);
		}
			});
	xmlData += "</categories>";
//遍历dataset元素

	var i = arrayValue.length;
	var j = 0;
	//alert(i);
	for(j=0;j<i;j++){
		xmlData += "<dataset seriesName='"+arrayName[j]+"' color='"+randomColor()+"' anchorBorderColor='"+randomColor()+"' anchorBgColor='"+randomColor()+"'>";	
			Ext.each(dateData, function(obj) {
				if(obj['orgNo'] == arrayValue[j] ){
					var radioValue = Ext.getCmp("elecInfoRadio").getValue().inputValue ;
//					alert(radioValue);
					if(radioValue=='1'){
					xmlData += "<set value='" + obj['papE'] + "'/>";
					}
					else
					if(radioValue=='2'){
					xmlData += "<set value='" + obj['papE1'] + "'/>";	
					}
					else
					if(radioValue=='3'){
					xmlData += "<set value='" + obj['papE2'] + "'/>";	
					}
					else
					if(radioValue=='4'){
					xmlData += "<set value='" + obj['papE3'] + "'/>";	
					}
					else
					if(radioValue=='5'){
					xmlData += "<set value='" + obj['papE4'] + "'/>";	
					}

				}			
			});
			xmlData += "</dataset>";
	}

	xmlData +=  "<styles><definition>"
			+ "<style name='CaptionFont' type='font' size='12' />"
			+ "</definition><application>"
			+ "<apply toObject='CAPTION' styles='CaptionFont' />"
			+ "<apply toObject='SUBCAPTION' styles='CaptionFont' />"
			+ "</application></styles></chart>";
//			alert(xmlData);
	return xmlData;

};
//=========================================曲线展示后台交互方法
function getelecByorgNo() {
	var startDate = Ext.getCmp("elecDateStart").getValue().format('Y-m-d');
	var endDate = Ext.getCmp("elecDateEnd").getValue().format('Y-m-d');
	var radioValue = Ext.getCmp("elecInfoRadio").getValue().inputValue ;
	var comboValue = elecTypeCombo.getValue();
	var orgNo ;
	var orgType ;

	var arrayLength = arrayValue.length;
    if(comboValue==1){
       orgNo = elecorgNo;
	   orgType = elecorgType;
       for(var j=0;j<arrayLength;j++){
    	   arrayValue.pop();
    	   arrayName.pop();
       }
	   for(var i=0;i<elecMsgNew.length;i++){
       arrayValue.push(elecMsgNew[i]);
       arrayName.push(elecMsg[i]);
       }
    }else{
       orgNo = LOGGEDORGNO;
	   if(orgNo.length==5){
	      orgType = '03';
	   }
	   else
	   if(orgNo.length==7){
	      orgType = '04';
	   }
	   else
	   if(orgNo.length==9){
	   	  orgNo = orgNo.substring(0,7);
	   	  orgType = '04';
	   }
       for(var j=0;j<arrayLength;j++){
    	  arrayValue.pop();
    	  arrayName.pop();
       }
	   for(var i=0;i<submitStoreNo.length;i++){
       arrayValue.push(submitStoreNo[i]);
       var stringssn=submitStoreName[i].replace('\,',' ').replace('\>','大于').replace('\=','等于').replace('\<','小于');

       arrayName.push(stringssn);
       }
    }
	Ext.Ajax.request({
				url : './advapp/elecContrastAnalysisAction!queryElecContrastAnalysisOrgNo.action',
				params : {
				    DateStart:startDate,
				    DateEnd:endDate,
				    ValueCombo:comboValue,
				    ValueArray:arrayValue,
				    OrgNo:orgNo,
				    OrgType:orgType
				    },
				success : function(response) {
					var result = Ext.decode(response.responseText);
					var orgNoxmlData = result.elecContrastAnalysisOrgNoList;
					if (orgNoxmlData == null) {
						return true;
					}	
				var elecXml =setelecByorgNo(orgNoxmlData);
				var myelecChart = new FusionCharts(
						"./fusionCharts/ScrollLine2D.swf",
						"elecChart", elecContrastLine.getWidth(),400);
				myelecChart.setDataXML(elecXml);
				myelecChart.setTransparent(true);
				myelecChart.render(lineElec.getId());
//					lineChartElec.changeDataXML(setelecByorgNo(orgNoxmlData));
				}
			});
};
// 弹出的窗体=========================================================
// 创建合同容量对比
var firstGridStore2 = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'advapp/elecContrastAnalysisAction!queryElecContrastAnalysiscapGrade.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'elecContrastAnalysisTypeList',
					totalProperty : 'totalCount'
				}, [{
							name : 'capGradeName'
						},{
							name : 'capGradeNo'
						}])
	});
	var cols2 = new Ext.grid.ColumnModel([{
		header : '合同容量编码',
		dataIndex : 'capGradeNo',
		hidden:true,
		sortable : true,
		align : 'center'
	},{
		header : '合同容量',
		dataIndex : 'capGradeName',
		sortable : true,
		align : 'center'
	}]);
var firstGrid2 = new Ext.grid.GridPanel({
		title : "备选项",
		region : 'center',
		autoScroll : true,
		stripeRows : true,
		viewConfig : {
			forceFit : true
		},
		cm : cols2,
		store : firstGridStore2
		});

var secondGridStore2 = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : ''
				}),
		reader : new Ext.data.JsonReader({
					root : 'elecContrastAnalysisTypeList',
					totalProperty : 'totalCount'
				}, [{
							name : 'capGradeName'
						},{
							name : 'capGradeNo'
						}])
	});

var secondGrid2 = new Ext.grid.GridPanel({
		title : "已选项",
		region : 'center',
		autoScroll : true,
		stripeRows : true,
		viewConfig : {
			forceFit : true
		},
		cm : cols2,
		store : secondGridStore2
		});

var displayPanel2 = new Ext.Panel({
			width : 650,
			height : 300,
			layout : 'hbox',
			defaults : {
				flex : 1
			},
			layoutConfig : {
				align : 'stretch'
			},
			items : [firstGrid2, secondGrid2]
		});
	firstGrid2.on('rowdblclick', function (grid, rowIndex) {
		var rec = grid.getSelectionModel().getSelected();
		firstGridStore2.remove(rec);
		secondGridStore2.add(rec);
	})

	secondGrid2.on('rowdblclick', function (grid, rowIndex) {
		var rec = grid.getSelectionModel().getSelected();
		secondGridStore2.remove(rec);
		firstGridStore2.add(rec);
	})
	
// 创建电压等级对比
var firstGridStore3 = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'advapp/elecContrastAnalysisAction!queryElecContrastAnalysisvoltageGrade.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'elecContrastAnalysisTypeList',
					totalProperty : 'totalCount'
				}, [{
							name : 'capGradeName'
						},{
							name : 'capGradeNo'
						}])
	});
	var cols3 = new Ext.grid.ColumnModel([{
		header : '电压等级编码',
		dataIndex : 'capGradeNo',
		hidden:true,
		sortable : true,
		align : 'center'
	},{
		header : '电压等级',
		dataIndex : 'capGradeName',
		sortable : true,
		align : 'center'
	}]);
var firstGrid3 = new Ext.grid.GridPanel({
		title : "备选项",
		region : 'center',
		autoScroll : true,
		stripeRows : true,
		viewConfig : {
			forceFit : true
		},
		cm : cols3,
		store : firstGridStore3
		});

var secondGridStore3 = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : ''
				}),
		reader : new Ext.data.JsonReader({
					root : 'elecContrastAnalysisTypeList',
					totalProperty : 'totalCount'
				}, [{
							name : 'capGradeName'
						},{
							name : 'capGradeNo'
						}])
	});

var secondGrid3 = new Ext.grid.GridPanel({
		title : "已选项",
		region : 'center',
		autoScroll : true,
		stripeRows : true,
		viewConfig : {
			forceFit : true
		},
		cm : cols3,
		store : secondGridStore3
		});

var displayPanel3 = new Ext.Panel({
			width : 650,
			height : 300,
			layout : 'hbox',
			defaults : {
				flex : 1
			},
			layoutConfig : {
				align : 'stretch'
			},
			items : [firstGrid3, secondGrid3]
		});
	firstGrid3.on('rowdblclick', function (grid, rowIndex) {
		var rec = grid.getSelectionModel().getSelected();
		firstGridStore3.remove(rec);
		secondGridStore3.add(rec);
	})

	secondGrid3.on('rowdblclick', function (grid, rowIndex) {
		var rec = grid.getSelectionModel().getSelected();
		secondGridStore3.remove(rec);
		firstGridStore3.add(rec);
	})
	
// 创建行业类别对比
	
//	var firstGridStore4 = new Ext.data.JsonStore({
//				fields : ["capGradeName", "capGradeNo"],
//				root : "elecContrastAnalysisTypeList",
//				totalProperty : "totalCount",
//				url : "advapp/elecContrastAnalysisAction!queryElecContrastAnalysisbussCategory.action"
//			});	
	
var firstGridStore4 = new Ext.data.Store({
	   
		proxy : new Ext.data.HttpProxy({
					url : 'advapp/elecContrastAnalysisAction!queryElecContrastAnalysisbussCategory.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'elecContrastAnalysisTypeList',
					totalProperty : 'totalCount'
				}, [{
							name : 'capGradeName'
						},{
							name : 'capGradeNo'
						}])
	});
	var cols4 = new Ext.grid.ColumnModel([{
		header : '行业类别编码',
		dataIndex : 'capGradeNo',
		hidden:true,
		sortable : true,
		align : 'center'
	},{
		header : '行业类别',
		dataIndex : 'capGradeName',
		sortable : true,
		align : 'center'
	}]);
var myPageSize = 20;
var firstGrid4 = new Ext.grid.GridPanel({
		title : "备选项",
		region : 'center',
		autoScroll : true,
		stripeRows : true,
		viewConfig : {
			forceFit : true
		},
		cm : cols4,
		store: firstGridStore4,   
//		bbar: new Ext.PagingToolbar({
//        store: firstGridStore4,   
//        displayInfo: true,
//        pageSize: myPageSize,
//        prependButtons: true
//    })
		bbar : new Ext.ux.MyToolbar({
			 store : firstGridStore4,
			 pageSize: myPageSize,
			 enableExpAll : true
		})

		});

var secondGridStore4 = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : ''
				}),
		reader : new Ext.data.JsonReader({
					root : 'elecContrastAnalysisTypeList',
					totalProperty : 'totalCount'
				}, [{
							name : 'capGradeName'
						},{
							name : 'capGradeNo'
						}])
	});

var secondGrid4 = new Ext.grid.GridPanel({
		title : "已选项",
		region : 'center',
		autoScroll : true,
		stripeRows : true,
		viewConfig : {
			forceFit : true
		},
		cm : cols4,
		store : secondGridStore4
		});

var displayPanel4 = new Ext.Panel({
			width : 650,
			height : 300,
			layout : 'hbox',
			defaults : {
				flex : 1
			},
			layoutConfig : {
				align : 'stretch'
			},
			items : [firstGrid4, secondGrid4]
		});
	firstGrid4.on('rowdblclick', function (grid, rowIndex) {
		var rec = grid.getSelectionModel().getSelected();
		firstGridStore4.remove(rec);
		secondGridStore4.add(rec);
	})

	secondGrid4.on('rowdblclick', function (grid, rowIndex) {
		var rec = grid.getSelectionModel().getSelected();
		secondGridStore4.remove(rec);
		firstGridStore4.add(rec);
	})
// =================================================================================
// 分类对比的combo
var elecTypeStore = new Ext.data.ArrayStore({
			fields : ['elecTypeValue', 'elecTypeName'],
			data : [['1', '按供电单位对比'], ["2", '按合同容量对比'], ['3', '按电压等级对比'],
					["4", '按行业类别对比']]
		});

var elecTypeCombo = new Ext.form.ComboBox({
			name : 'elecType',
			id : 'elecType',
			editable : false,
			store : elecTypeStore,
			displayField : 'elecTypeName',
			labelStyle : "text-align:right;width:80px;",
			valueField : 'elecTypeValue',
			triggerAction : 'all',
//			typeAhead : true,
			mode : 'local',
			// resizable : true,
			fieldLabel : '电量对比',
			labelSeparator : '',
			// forceSelection : true,
			emptyText : '--请选择--',
			blankText : '--请选择--',
//			selectOnFocus : true,
			width : 180,
			forceSelection : true
		});
elecTypeCombo.on('select', function(combox) {
			 var value = combox.getValue();
			if(value == 1){
				Ext.getCmp("addButton").disable();
			}else{
				Ext.getCmp("addButton").enable();
			}
			Ext.getCmp("elecMessage").setValue("");
			elecMsg.length = 0;
			elecMsgNew.length = 0;
			
		});

// ---------------------------------------------------------------
// 加入：一次侧，二次侧
var elecInfoRadios = new Ext.form.RadioGroup({
	        id:"elecInfoRadio",
			plain : true,
			border : false,
			height : 180,
			layout : 'fit',
			columns : [100, 100, 100, 100, 100],
			items : [{
						checked : true,
						style : "padding:5px",
						boxLabel : '总电量',
						name : 'unique',
						inputValue : '1',
						id : 'elecInfoRadio1'
					}, {
						boxLabel : '尖电量',
						style : "padding:5px",
						name : 'unique',
						inputValue : '2',
						id : 'elecInfoRadio2'
					}, {
						boxLabel : '峰电量',
						style : "padding:5px",
						name : 'unique',
						inputValue : '3',
						id : 'elecInfoRadio3'
					}, {
						boxLabel : '平电量',
						style : "padding:5px",
						name : 'unique',
						inputValue : '4',
						id : 'elecInfoRadio4'
					}, {
						boxLabel : '谷电量',
						style : "padding:5px",
						name : 'unique',
						inputValue : '5',
						id : 'elecInfoRadio5'
					}]
		});
// -------------------------------------------------------------------------------------------

var win2 = new Ext.Window({  
	    title:'按合同容量对比',
		resizable:false,
		draggable:false,
		modal:true,
		layout : 'fit',
		width : 500,
		height : 300,
		closeAction : 'hide',
		plain : true,
		items : displayPanel2,
		buttons : [{
		      text : '确定',
			  handler : function() {
			  var submitStore = secondGridStore2.getRange();                     
			  var i = 0;
			  if (submitStore.length > 4) {
			  Ext.Msg.alert('提示：','最多支持四条加载项比较,请修正！');
			  } else {
			  submitStoreName.length=0;
			  submitStoreNo.length=0;
			 
			  for (i > 0; i < submitStore.length; i++) {
			  if (i < 4) {
			  submitStoreName.push(submitStore[i].get('capGradeName')
			  );
			  submitStoreNo.push(submitStore[i].get('capGradeNo'));
			  }
			  }
			  Ext.getCmp("elecMessage").setValue(submitStoreName.join("\n"));	
			  win2.hide();
			  secondGridStore2.removeAll();
			  }
			  }
			}, {
				text : '取消',
				handler : function() {
				win2.hide();
				}
			}]
});
var win3 = new Ext.Window({  
	    title:'按电压等级对比',
		resizable:false,
		draggable:false,
		modal:true,
		layout : 'fit',
		width : 500,
		height : 300,
		closeAction : 'hide',
		plain : true,
		items : displayPanel3,
		buttons : [{
		      text : '确定',
			  handler : function() {
			  var submitStore = secondGridStore3.getRange();                     
			  var i = 0;
			  if (submitStore.length > 4) {
			  Ext.Msg.alert('提示：','最多支持四条加载项比较,请修正！');
			  } else {
			  submitStoreName.length=0;
			  submitStoreNo.length=0;
			  
			  for (i > 0; i < submitStore.length; i++) {
			  if (i < 4) {
			  submitStoreName.push(submitStore[i].get('capGradeName')
			  );
			  submitStoreNo.push(submitStore[i].get('capGradeNo'));
			  }
			  }
			  Ext.getCmp("elecMessage").setValue(submitStoreName.join("\n"));	
			  win3.hide();
			  secondGridStore3.removeAll();
			  }
			  }
			}, {
				text : '取消',
				handler : function() {
				win3.hide();
				}
			}]
});
var win4 = new Ext.Window({  
	    title:'按行业类别对比',
		resizable:false,
		draggable:false,
		modal:true,
		layout : 'fit',
		width : 500,
		height : 300,
		closeAction : 'hide',
		plain : true,
		items : displayPanel4,
		buttons : [{
		      text : '确定',
			  handler : function() {
			  var submitStore = secondGridStore4.getRange();                     
			  var i = 0;
			  if (submitStore.length > 4) {
			  Ext.Msg.alert('提示：','最多支持四条加载项比较,请修正！');
			  } else {
			  submitStoreName.length=0;
			  submitStoreNo.length=0;
			  
			  for (i > 0; i < submitStore.length; i++) {
			  if (i < 4) {
			  submitStoreName.push(submitStore[i].get('capGradeName')
			  );
			  submitStoreNo.push(submitStore[i].get('capGradeNo'));
			  }
			  }
			  Ext.getCmp("elecMessage").setValue(submitStoreName.join("\n"));	
			  win4.hide();
			  secondGridStore4.removeAll();
			  }
			  }
			}, {
				text : '取消',
				handler : function() {
				win4.hide();
				}
			}]
});
var elecContrastLinePanel = new Ext.Panel({
	region : 'north',
	height : 170,
	layout : 'fit',
	plain : true,
	items : [{
		baseCls : "x-plain",
		layout : "column",
		style : "padding:8px",
		items : [{
					columnWidth : .3,
					layout : "form",
//					labelStyle : "text-align:right;width:80;",
					baseCls : "x-plain",
					items : [elecTypeCombo]
				}, {
					columnWidth : .1,
					layout : "form",
					defaultType : "button",
					baseCls : "x-plain",
					items : [{
						text : "添加",
						id:'addButton',
						disabled:true,
						width : 50,
						listeners : {
							"click" : function() {								
							Ext.getCmp("elecMessage").setValue("");
							var comboValue = elecTypeCombo.getValue();
							    if(comboValue==2){
							    	win2.show();
							    	firstGridStore2.load();
							    }
							    else
							    if(comboValue==3){
							    	win3.show();
							    	firstGridStore3.load();
							    }
							    else
							    if(comboValue==4){
							    	win4.show();
//							    	gtStore.baseParams = {
//			                        start : 0,
//			                        limit : 20
//		                            }
							    	firstGridStore4.load({params:{start:0,limit:20}});
							    }
							    
//								win.show();
//								firstGridStore.load();
							}
						}
					}]
				},{
					columnWidth : .1,
					layout : "form",
					defaultType : "button",
					baseCls : "x-plain",
					items : [{
						text : "重置",
						id:'cleanButton',
						//disabled:true,
						width : 50,
						listeners : {
							"click" : function() {
							Ext.getCmp("elecMessage").setValue("");
							elecMsg.length = 0;
							elecMsgNew.length = 0;
							
							}
						}
					}]
				}, {
					columnWidth : .25,
					layout : "form",
//					labelStyle : "text-align:right;width:80;",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								xtype : "datefield",
								format : "Y-m-d",
								id : 'elecDateStart',
								value : new Date().add(Date.DAY, -8),
								fieldLabel : "查询日期从",
								name : 'dataDateFrom',
								labelStyle : "text-align:right;width:80px;"
							}]
				}, {
					columnWidth : .25,
					layout : "form",
					labelWidth : 10,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								xtype : "datefield",
								format : "Y-m-d",
								id : 'elecDateEnd',
								value : new Date().add(Date.DAY, -1),
								fieldLabel : "到",
								name : 'dataDateTo',
								labelStyle : "text-align:right;width:10px;"
							}]
				}, {
					columnWidth : .5,
					layout : "form",
					labelWidth : 100,
					defaultType : "textarea",
					
					baseCls : "x-plain",
					labelAlign : 'right',
					items : [{
								name : 'elecMessage',
								readOnly:true,
								id : 'elecMessage',
								labelSeparator : "",
								emptyText : '--最多支持加载四项进行比对--',
								blankText : '--最多支持加载四项进行比对--',
								width : 300,
								height : 120
							}]
				}, {
					columnWidth : .25,
					layout : "form",
					labelWidth : 100,
					defaultType : "textarea",
					baseCls : "x-plain",
					labelAlign : 'right',
					items : [elecInfoRadios]
				}, {
					columnWidth : .15,
					layout : "form",
					defaultType : "button",
					baseCls : "x-plain",
					items : [{
						text : "查询",
						listeners : {
							"click" : function() {
					        getelecByorgNo();			
							}
						},
						width : 50
					}]
				}]
	}]
});


//var lineChartElec = new Ext.fc.FusionChart({
//			border : false,
//			id:'lineChartElec',
//			title : '',
//			wmode : 'transparent',
//			backgroundColor : 'ffffff',
//			url : 'fusionCharts/MSLine.swf',
//			DataXML : ""
//		});
var lineElec = new Ext.Panel({
	        
			border : false
			
		});
var elecContrastLine = new Ext.Panel({
	        
	        autoScroll:true,
			border : false,
			region : 'center',
			title : '电量对比曲线',
			items : [lineElec]
		});

var elecContrastWindow = new Ext.Panel({
			layout : 'border',
			items : [elecContrastLinePanel, elecContrastLine]
		});
renderModel(elecContrastWindow, '电量对比分析');
});
