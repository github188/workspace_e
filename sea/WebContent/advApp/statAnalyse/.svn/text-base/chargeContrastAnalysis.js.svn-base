// 测量点日冻结总及分相有用功率------------------------------------
var chargeorgNo = '';//单位编码
var chargeorgType = '';//单位类型
var chargeorgName = '';//单位名称
var chargeMsg = new Array();//用来显示的值--单位名称
var chargeMsgNew = new Array();//用来交互的值--单位编码
var submitStoreNameCharge = new Array();//添加按钮框中的显示数据
var submitStoreNoCharge = new Array();//添加按钮框的中后台交互数据
var arrayValueCharge = new Array();
var arrayNameCharge = new Array();
Ext.onReady(function(){
var treecharge = new LeftTreeListener({
			modelName : '负荷对比分析',
			processClick : function(p, node, e) {
				var obj = node.attributes.attributes;
				var type = node.attributes.type;
				var hasSecond=0;
				var sameType;
				
				if (!node.isLeaf()) {
					chargeorgNo = obj.orgNo;
					chargeorgType = obj.orgType;
					
					if(chargeorgName!=node.text && Ext.getCmp("chargeType").getValue() == '1'){
						
						if(chargeMsg.length>3){
//						elecMsg.pop();
//						elecMsgNew.pop();
						   Ext.Msg.alert('提示','只能选择四项进行对比');
					    	return false;
					    } 
					    if(chargeorgType=='02'){
					    	Ext.Msg.alert('提示','不支持选择省级单位');
					    	return false;
					    }
					    
					    if(chargeorgType=='03'||chargeorgType=='04'){
					    
					    var same=1;	
					    for(var i=0;i<chargeMsg.length;i++){
					    	//按供电单位对比 中 同级可以对比，不同级不能选择,不是供电单位不能选
					    	
					    	if(chargeMsgNew[i].length!=chargeorgNo.length)
					    	{
					    	same=0;
					    	Ext.Msg.alert('提示','请选择同一级别的供电单位');
					    	}
					    	//不能选择相同的供电单位
					    	if(chargeMsgNew[i]==chargeorgNo)
					    	{
					    	same=0;
					    	Ext.Msg.alert('提示','选择了重复的供电单位');
					    	}
					    }
					      if(same==1){	
						  chargeorgName = node.text;
						  chargeMsg.push(chargeorgName);
						  chargeMsgNew.push(chargeorgNo);
					      Ext.getCmp("chargeMessage").setValue(chargeMsg.join("\n"));
					      }
					    }
					    else
					    Ext.Msg.alert('提示','请选择县级以上供电单位');
					    
					}					
				} else {
					return true;
				}
			}
			
		});
		
// =========================================================曲线展示
		
var chargeFusionChart = new Ext.Panel({
   border:false
});
		
var chargeContrastFusionChart = new Ext.Panel({
   autoScroll:true,
   title:'负荷对比曲线',
   border:false,
   region:'center',
   items:[chargeFusionChart]
});
		
		//随机获取颜色
		function randomColor() {
	return '' + (Math.random() * 0xffffff << 0).toString(16);
};

//生成对应xml数据
function setelecByorgNo2(dateData2) {
	var xmlData2 = '<chart caption="" '
				+ 'subcaption="" lineThickness="1" '
				+ 'showValues="0" formatNumberScale="0" anchorRadius="2" '
				+ 'divLineAlpha="20" divLineColor="CC3300" divLineIsDashed="1" '
				+ 'showAlternateHGridColor="1" alternateHGridAlpha="5" alternateHGridColor="CC3300" '
			+ 'shadowAlpha="40" labelStep="2" numvdivlines="5" chartRightMargin="35" '
			+ 'bgColor="FFFFFF,CC3300" showBorder="0" bgAngle="270" bgAlpha="10,10">';
	xmlData2 += "<categories>";
			var statDate2 = '';
	Ext.each(dateData2, function(obj2) {
		if(statDate2 != obj2['statDate'].substring(0,10)){
				xmlData2 += "<category label='" + obj2['statDate'].substring(0,10) + "' />";
				statDate2 = obj2['statDate'].substring(0,10);
		}
			});
	xmlData2 += "</categories>";
//遍历dataset元素

	var i = arrayValueCharge.length;
	var j = 0;
	//alert(i);
	for(j=0;j<i;j++){
		xmlData2 += "<dataset seriesName='"+arrayNameCharge[j]+"' color='"+randomColor()+"' anchorBorderColor='"+randomColor()+"' anchorBgColor='"+randomColor()+"'>";	
			Ext.each(dateData2, function(obj2) {
				if(obj2['orgNo'] == arrayValueCharge[j] ){
					xmlData2 += "<set value='" + obj2['avgP'] + "'/>";

				}			
			});
			xmlData2 += "</dataset>";
	}

	xmlData2 +=  "<styles><definition>"
			+ "<style name='CaptionFont' type='font' size='12' />"
			+ "</definition><application>"
			+ "<apply toObject='CAPTION' styles='CaptionFont' />"
			+ "<apply toObject='SUBCAPTION' styles='CaptionFont' />"
			+ "</application></styles></chart>";
//			alert(xmlData2);
	return xmlData2;

};
//=========================================曲线展示后台交互方法


function getelecByorgNo2() {
	var startDate = Ext.getCmp("chargeDateStart").getValue().format('Y-m-d');
	var endDate = Ext.getCmp("chargeDateEnd").getValue().format('Y-m-d');
	
	var comboValue = chargeTypeCombo.getValue();
	var orgNo ;
	var orgType ;

	var arrayLength = arrayValueCharge.length;
    if(comboValue==1){
       orgNo = chargeorgNo;
	   orgType = chargeorgType;
       for(var j=0;j<arrayLength;j++){
    	   arrayValueCharge.pop();
    	   arrayNameCharge.pop();
       }
	   for(var i=0;i<chargeMsgNew.length;i++){
       arrayValueCharge.push(chargeMsgNew[i]);
       arrayNameCharge.push(chargeMsg[i]);
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
    	  arrayValueCharge.pop();
    	  arrayNameCharge.pop();
       }
	   for(var i=0;i<submitStoreNoCharge.length;i++){
       arrayValueCharge.push(submitStoreNoCharge[i]);
       var stringssn=submitStoreNameCharge[i].replace('\,',' ').replace('\>','大于').replace('\=','等于').replace('\<','小于');

       arrayNameCharge.push(stringssn);
       }
    }
	Ext.Ajax.request({
				url : './advapp/elecContrastAnalysisAction!queryChargeContrastAnalysisOrgNo.action',
				params : {
				    DateStart:startDate,
				    DateEnd:endDate,
				    ValueCombo:comboValue,
				    ValueArray:arrayValueCharge,
				    OrgNo:orgNo,
				    OrgType:orgType
				    },
				success : function(response) {
					var result2 = Ext.decode(response.responseText);
					var orgNoxmlData2 = result2.chargeContrastAnalysisList;
					if (orgNoxmlData2 == null) {
						return true;
					}		
				var chargeXml = setelecByorgNo2(orgNoxmlData2);
				var mychargeChart = new FusionCharts(
						"./fusionCharts/ScrollLine2D.swf",
						"chargeChart", chargeContrastFusionChart.getWidth(), 400);
					mychargeChart.setDataXML(chargeXml);
					mychargeChart.setTransparent(true);
					mychargeChart.render(chargeFusionChart.getId());
//					lineChartCharge.changeDataXML();
				}
			});
};
// 弹出的窗体=========================================================
// 创建合同容量对比
var firstGridStore2Charge = new Ext.data.Store({
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
	var cols2Charge = new Ext.grid.ColumnModel([{
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
var firstGrid2Charge = new Ext.grid.GridPanel({
		title : "备选项",
		region : 'center',
		autoScroll : true,
		stripeRows : true,
		viewConfig : {
			forceFit : true
		},
		cm : cols2Charge,
		store : firstGridStore2Charge
		});

var secondGridStore2Charge = new Ext.data.Store({
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

var secondGrid2Charge = new Ext.grid.GridPanel({
		title : "已选项",
		region : 'center',
		autoScroll : true,
		stripeRows : true,
		viewConfig : {
			forceFit : true
		},
		cm : cols2Charge,
		store : secondGridStore2Charge
		});

var displayPanel2Charge = new Ext.Panel({
			width : 650,
			height : 300,
			layout : 'hbox',
			defaults : {
				flex : 1
			},
			layoutConfig : {
				align : 'stretch'
			},
			items : [firstGrid2Charge, secondGrid2Charge]
		});
	firstGrid2Charge.on('rowdblclick', function (grid, rowIndex) {
		var rec = grid.getSelectionModel().getSelected();
		firstGridStore2Charge.remove(rec);
		secondGridStore2Charge.add(rec);
	})

	secondGrid2Charge.on('rowdblclick', function (grid, rowIndex) {
		var rec = grid.getSelectionModel().getSelected();
		secondGridStore2Charge.remove(rec);
		firstGridStore2Charge.add(rec);
	})
	
// 创建电压等级对比
var firstGridStore3Charge = new Ext.data.Store({
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
	var cols3Charge = new Ext.grid.ColumnModel([{
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
var firstGrid3Charge = new Ext.grid.GridPanel({
		title : "备选项",
		region : 'center',
		autoScroll : true,
		stripeRows : true,
		viewConfig : {
			forceFit : true
		},
		cm : cols3Charge,
		store : firstGridStore3Charge
		});

var secondGridStore3Charge = new Ext.data.Store({
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

var secondGrid3Charge = new Ext.grid.GridPanel({
		title : "已选项",
		region : 'center',
		autoScroll : true,
		stripeRows : true,
		viewConfig : {
			forceFit : true
		},
		cm : cols3Charge,
		store : secondGridStore3Charge
		});

var displayPanel3Charge = new Ext.Panel({
			width : 650,
			height : 300,
			layout : 'hbox',
			defaults : {
				flex : 1
			},
			layoutConfig : {
				align : 'stretch'
			},
			items : [firstGrid3Charge, secondGrid3Charge]
		});
	firstGrid3Charge.on('rowdblclick', function (grid, rowIndex) {
		var rec = grid.getSelectionModel().getSelected();
		firstGridStore3Charge.remove(rec);
		secondGridStore3Charge.add(rec);
	})

	secondGrid3Charge.on('rowdblclick', function (grid, rowIndex) {
		var rec = grid.getSelectionModel().getSelected();
		secondGridStore3Charge.remove(rec);
		firstGridStore3Charge.add(rec);
	})
	
// 创建行业类别对比
	
var firstGridStore4Charge = new Ext.data.Store({
	   
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
	var cols4Charge = new Ext.grid.ColumnModel([{
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
var firstGrid4Charge = new Ext.grid.GridPanel({
		title : "备选项",
		region : 'center',
		autoScroll : true,
		stripeRows : true,
		viewConfig : {
			forceFit : true
		},
		cm : cols4Charge,
		store: firstGridStore4Charge,   
//		bbar: new Ext.PagingToolbar({
//        store: firstGridStore4,   
//        displayInfo: true,
//        pageSize: myPageSize,
//        prependButtons: true
//    })
		bbar : new Ext.ux.MyToolbar({
			 store : firstGridStore4Charge,
			 pageSize: myPageSize,
			 enableExpAll : true
		})

		});

var secondGridStore4Charge = new Ext.data.Store({
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

var secondGrid4Charge = new Ext.grid.GridPanel({
		title : "已选项",
		region : 'center',
		autoScroll : true,
		stripeRows : true,
		viewConfig : {
			forceFit : true
		},
		cm : cols4Charge,
		store : secondGridStore4Charge
		});

var displayPanel4Charge = new Ext.Panel({
			width : 650,
			height : 300,
			layout : 'hbox',
			defaults : {
				flex : 1
			},
			layoutConfig : {
				align : 'stretch'
			},
			items : [firstGrid4Charge, secondGrid4Charge]
		});
	firstGrid4Charge.on('rowdblclick', function (grid, rowIndex) {
		var rec = grid.getSelectionModel().getSelected();
		firstGridStore4Charge.remove(rec);
		secondGridStore4Charge.add(rec);
	})

	secondGrid4Charge.on('rowdblclick', function (grid, rowIndex) {
		var rec = grid.getSelectionModel().getSelected();
		secondGridStore4Charge.remove(rec);
		firstGridStore4Charge.add(rec);
	})
// =================================================================================
// 分类对比的combo
var chargeTypeStore = new Ext.data.ArrayStore({
			fields : ['chargeTypeValue', 'chargeTypeName'],
			data : [['1', '按供电单位对比'], ["2", '按合同容量对比'], ['3', '按电压等级对比'],
					["4", '按行业类别对比']]
		});

var chargeTypeCombo = new Ext.form.ComboBox({
			name : 'chargeType',
			id : 'chargeType',
			editable : false,
			store : chargeTypeStore,
			displayField : 'chargeTypeName',
			labelStyle : "text-align:right;width:80px;",
			valueField : 'chargeTypeValue',
			triggerAction : 'all',
//			typeAhead : true,
			mode : 'local',
			// resizable : true,
			fieldLabel : '负荷对比',
			labelSeparator : '',
			// forceSelection : true,
			emptyText : '--请选择--',
			blankText : '--请选择--',
//			selectOnFocus : true,
			width : 180,
			forceSelection : true
		});
chargeTypeCombo.on('select', function(combox) {
			 var value = combox.getValue();
			if(value == 1){
				Ext.getCmp("addButton2").disable();
			}else{
				Ext.getCmp("addButton2").enable();
			}
			Ext.getCmp("chargeMessage").setValue("");
			chargeMsg.length = 0;
			chargeMsgNew.length = 0;
			
		});

// -------------------------------------------------------------------------------------------

var win2Charge = new Ext.Window({  
	    title:'按合同容量对比',
		resizable:false,
		draggable:false,
		modal:true,
		layout : 'fit',
		width : 500,
		height : 300,
		closeAction : 'hide',
		plain : true,
		items : displayPanel2Charge,
		buttons : [{
		      text : '确定',
			  handler : function() {
			  var submitStoreCharge = secondGridStore2Charge.getRange();                     
			  var i = 0;
			  if (submitStoreCharge.length > 4) {
			  Ext.Msg.alert('提示：','最多支持四条加载项比较,请修正！');
			  } else {
			  submitStoreNameCharge.length=0;
			  submitStoreNoCharge.length=0;
			 
			  for (i > 0; i < submitStoreCharge.length; i++) {
			  if (i < 4) {
			  submitStoreNameCharge.push(submitStoreCharge[i].get('capGradeName')
			  );
			  submitStoreNoCharge.push(submitStoreCharge[i].get('capGradeNo'));
			  }
			  }
			  Ext.getCmp("chargeMessage").setValue(submitStoreNameCharge.join("\n"));	
			  win2Charge.hide();
			  secondGridStore2Charge.removeAll();
			  }
			  }
			}, {
				text : '取消',
				handler : function() {
				win2Charge.hide();
				}
			}]
});
var win3Charge = new Ext.Window({  
	    title:'按电压等级对比',
		resizable:false,
		draggable:false,
		modal:true,
		layout : 'fit',
		width : 500,
		height : 300,
		closeAction : 'hide',
		plain : true,
		items : displayPanel3Charge,
		buttons : [{
		      text : '确定',
			  handler : function() {
			  var submitStoreCharge = secondGridStore3Charge.getRange();                     
			  var i = 0;
			  if (submitStoreCharge.length > 4) {
			  Ext.Msg.alert('提示：','最多支持四条加载项比较,请修正！');
			  } else {
			  submitStoreNameCharge.length=0;
			  submitStoreNoCharge.length=0;
			  
			  for (i > 0; i < submitStoreCharge.length; i++) {
			  if (i < 4) {
			  submitStoreNameCharge.push(submitStoreCharge[i].get('capGradeName')
			  );
			  submitStoreNoCharge.push(submitStoreCharge[i].get('capGradeNo'));
			  }
			  }
			  Ext.getCmp("chargeMessage").setValue(submitStoreNameCharge.join("\n"));	
			  win3Charge.hide();
			  secondGridStore3Charge.removeAll();
			  }
			  }
			}, {
				text : '取消',
				handler : function() {
				win3Charge.hide();
				}
			}]
});
var win4Charge = new Ext.Window({  
	    title:'按行业类别对比',
		resizable:false,
		draggable:false,
		modal:true,
		layout : 'fit',
		width : 500,
		height : 300,
		closeAction : 'hide',
		plain : true,
		items : displayPanel4Charge,
		buttons : [{
		      text : '确定',
			  handler : function() {
			  var submitStoreCharge = secondGridStore4Charge.getRange();                     
			  var i = 0;
			  if (submitStoreCharge.length > 4) {
			  Ext.Msg.alert('提示：','最多支持四条加载项比较,请修正！');
			  } else {
			  submitStoreNameCharge.length=0;
			  submitStoreNoCharge.length=0;
			  
			  for (i > 0; i < submitStoreCharge.length; i++) {
			  if (i < 4) {
			  submitStoreNameCharge.push(submitStoreCharge[i].get('capGradeName')
			  );
			  submitStoreNoCharge.push(submitStoreCharge[i].get('capGradeNo'));
			  }
			  }
			  Ext.getCmp("chargeMessage").setValue(submitStoreNameCharge.join("\n"));	
			  win4Charge.hide();
			  secondGridStore4Charge.removeAll();
			  }
			  }
			}, {
				text : '取消',
				handler : function() {
				win4Charge.hide();
				}
			}]
});
var chargeContrastLinePanel = new Ext.Panel({
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
					items : [chargeTypeCombo]
				}, {
					columnWidth : .1,
					layout : "form",
					defaultType : "button",
					baseCls : "x-plain",
					items : [{
						text : "添加",
						id:'addButton2',
						disabled:true,
						width : 50,
						listeners : {
							"click" : function() {								
							Ext.getCmp("chargeMessage").setValue("");
							var comboValue = chargeTypeCombo.getValue();
							    if(comboValue==2){
							    	win2Charge.show();
							    	firstGridStore2Charge.load();
							    }
							    else
							    if(comboValue==3){
							    	win3Charge.show();
							    	firstGridStore3Charge.load();
							    }
							    else
							    if(comboValue==4){
							    	win4Charge.show();
//							    	gtStore.baseParams = {
//			                        start : 0,
//			                        limit : 20
//		                            }
							    	firstGridStore4Charge.load({params:{start:0,limit:20}});
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
						id:'cleanButton2',
						//disabled:true,
						width : 50,
						listeners : {
							"click" : function() {
							Ext.getCmp("chargeMessage").setValue("");
							chargeMsg.length = 0;
							chargeMsgNew.length = 0;
							
							}
						}
					}]
				}, {
					columnWidth : .2,
					layout : "form",
//					labelStyle : "text-align:right;width:80;",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								xtype : "datefield",
								format : "Y-m-d",
								id : 'chargeDateStart',
								value : new Date().add(Date.DAY, -8),
								fieldLabel : "查询日期从",
								name : 'dataDateFrom',
								labelStyle : "text-align:right;width:80px;"
							}]
				}, {
					columnWidth : .2,
					layout : "form",
					labelWidth : 10,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								xtype : "datefield",
								format : "Y-m-d",
								id : 'chargeDateEnd',
								value : new Date().add(Date.DAY, -1),
								fieldLabel : "到",
								name : 'dataDateTo',
								labelStyle : "text-align:right;width:10px;"
							}]
				},{
					columnWidth : .1,
					layout : "form",
					defaultType : "button",
					baseCls : "x-plain",
					items : [{
						text : "查询",
						listeners : {
							"click" : function() {
					        getelecByorgNo2();			
							}
						},
						width : 50
					}]
				},
					{
					columnWidth : .5,
					layout : "form",
					labelWidth : 100,
					defaultType : "textarea",
					
					baseCls : "x-plain",
					labelAlign : 'right',
					items : [{
								name : 'chargeMessage',
								readOnly:true,
								id : 'chargeMessage',
								labelSeparator : "",
								emptyText : '--最多支持加载四项进行比对--',
								blankText : '--最多支持加载四项进行比对--',
								width : 300,
								height : 120
							}]
				}]
	}]
});


//var lineChartCharge = new Ext.fc.FusionChart({
//	        id:'lineChartCharge',
//			border : false,
//			title : '',
////			wmode : 'transparent',
//			backgroundColor : 'ffffff',
//			url : 'fusionCharts/MSLine.swf'
//			
//		});
//var lineCharge = new Ext.Panel({
//	        id:'lineCharge',
//			border : false,
//			bodyBorder : false,
//			layout : 'fit',
//			region : 'center',
//			height : 300,
//			monitorResize : true,
//			bodyStyle : 'padding:0px 2px 5px 2px',
//			items : [lineChartCharge]
//		});
//var chargeContrastLine = new Ext.Panel({
//	        id:'chargeContrastLine',
//			autoScroll : true,
//			border : false,
//			region : 'center',
//			height : 300,
//			layout:'border',
//			title : '曲线展示',
//			items : [lineCharge]
//		});

var chargeContrastWindow = new Ext.Panel({
			layout : 'border',
			items : [chargeContrastLinePanel, chargeContrastFusionChart]
		});
renderModel(chargeContrastWindow, '负荷对比分析');
});