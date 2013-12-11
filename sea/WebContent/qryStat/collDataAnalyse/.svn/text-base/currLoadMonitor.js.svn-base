/**
 * 日用电负荷监测
 * @author modified by jiangweichao on 2010-3-8 for bug1
 */
//alert("paraDay:" +paraDay);
var clm_lineData=[];
var width = 0;
var height = 0;
var xmlData = "";

Ext.onReady(function() {
	var curveChgFlag = false;//如果曲线有值，则变化

	var curveRealList = '';//实时曲线列表
	var curveStatList = '';//冻结曲线列表
	var clmTimeModelList = '';//时间模

	xmlData = getMultiCLMXMLData('','','');
	//节点名
	var clm_nodeName = new Ext.form.TextField({
		    fieldLabel : "节点名<font color='red'>*</font>",
			name : 'clm_nodeName',
			id:'clm_nodeName',
			labelSeparator:'',
			readOnly:true,
			labelStyle : "text-align:right;",
			emptyText : '请选择供电单位',
			width :200
	});
	
	//查询日期
	var clm_queryDate = new Ext.form.DateField({
		id : 'clm_queryDate',
		fieldLabel : '查询日期',
		editable:false,
		name : 'clm_queryDate',
		labelStyle : "text-align:right;width:60px;",
		width : 120,
		format: 'Y-m-d',
	    labelSeparator:'',
	    allowBlank:false,
	    value:new Date()
    });
	
	//查询按钮
	var clm_queryBtn = new Ext.Button({
			text : '查询',
			name : 'clm_queryBtn',
			width : 80,
		    labelSeparator:'',
		    handler:function(){
		        if(!checkCLMInitData()){
                    return;
                }
                
                var recs = clm_gridSm.getSelections();
                if(Ext.isEmpty(recs)){
	  		     	Ext.MessageBox.alert("提示","请选择供电单位！");
	  		     	return;
	  		    }
		        
	  		    var orgList = new Array();
	  		    for(var i = 0; i < recs.length; i++){
	  		    	if(!Ext.isEmpty( recs[i].get('orgNo')) &&
	  		    			!Ext.isEmpty(recs[i].get('orgType'))){
	  		    	        orgList[i] = recs[i].get('orgNo')+ "!" + recs[i].get('orgType') + "!" + recs[i].get('orgName');
	  		    	    }
	  		    }	  		    
	  		    
		        Ext.Ajax.request({
  		     		url:'./qrystat/currLoadMonitor!loadGridData.action',
  		     		params : {
		        	    queryDate:clm_queryDate.getValue(),
		   	            orgList:orgList,
		   	            realCheckValue:realCheckbox.getValue(),
		   	            statCheckValue:statCheckbox.getValue()
  		     		},
  		     		success : function(response){
  		     			var result = Ext.decode(response.responseText);
  		     			if(result){
	                        clm_gridStore.removeAll();
	  		     			clm_gridStore.loadData(result);
	  		     			clm_Grid.getSelectionModel().selectAll();
	  		     			clm_lineData = result.clmList;
	  		     			curveRealList = result.curveRealList;
	  		     			curveStatList = result.curveStatList;
	  		     			
	  		     			clmTimeModelList = result.clmTimeModelList;
	  		     			xmlData = getMultiCLMXMLData(clmTimeModelList,curveRealList,curveStatList);
			        	    generateClmLine(xmlData,width,height);
	  		     		    return;
  		     			}
  		     		},
  		     		failure : function(){
  		     		    Ext.MessageBox.alert("提示","失败");
  		     		    return;
  		     		}
  		     	});
	        }
	});
	
	//导出按钮
	var clm_exportBtn = new Ext.Button({
			text : '导出',
			name : 'clm_exportBtn',
			width : 80,
		    labelSeparator:'',
		    handler:function(){
	        }
	});
	
	// 定义checkBox,用于是否显示冻结数据
	var statCheckbox = new Ext.form.Checkbox( {
		boxLabel : '冻结曲线',
		name : 'statCurve',
		inputValue : 1
	});
	
	// 定义checkBox,用于是否显示冻结数据
	var realCheckbox = new Ext.form.Checkbox( {
		boxLabel : '实时曲线',
		checked : true,
		name : 'realCurve',
		inputValue : 1
	});
	
	//校验函数
	function checkCLMInitData(){
        if("" == clm_queryDate.getValue()){
	        Ext.MessageBox.alert("提示","请输入日期！"); 
 	        return false;
        }
        return true;
    }
	
	// 当日用电负荷监测panel1
	var currLoadMonitorTopPnl = new Ext.Panel({
				border : false,
				height:30,
				region:'north',
				layout : 'column',
				items : [{
					columnWidth : .26,
					layout : "form",
					labelWidth : 60,
					style : "padding:5px",
					border : false,
					items : [clm_queryDate]
				}, {
					columnWidth : .1,
					layout : "form",
					labelWidth : 1,
					border : false,
					bodyStyle : 'padding:3px 0px 0px 0px',
					items : [ realCheckbox ]
				}, {
					columnWidth : .1,
					layout : "form",
					labelWidth : 1,
					border : false,
					bodyStyle : 'padding:3px 0px 0px 0px',
					items : [ statCheckbox ]
				}, {
					columnWidth : .54,
					layout : "form",
					style : "padding:5px",
					border : false,
					items : [clm_queryBtn]
				}]
			});
	// -----------------------------------------------------------------------------

	// 当日用电负荷监测曲线
	var clmLineChartPnl = new Ext.Panel({
				border : false,
				bodyBorder : false,
				layout : 'fit',
				monitorResize : false
			});
	
	//用于实现fusionchart动态变化
	clmLineChartPnl.on("afterlayout", function(view,layout){
			width = clm_CurvePnl.getWidth();
	        height = clm_CurvePnl.getHeight()-4;
	        generateClmLine(xmlData,width,height);
	},clmLineChartPnl);
	
	//生成曲线
	var clmLineChart;
	function generateClmLine(xmlData,width,height){
		clmLineChart = new FusionCharts("fusionCharts/MSLine.swf",
				"currLoadMonitorChartId", width, height);
		clmLineChart.setDataXML(xmlData);
		clmLineChart.setTransparent(true);
		if(!Ext.isEmpty(clmLineChartPnl)){
			clmLineChart.render(clmLineChartPnl.getId());
		}
	}

	// 当日用电负荷监测
	var clm_CurvePnl = new Ext.Panel({
				border : false,
				bodyBorder : false,
				layout:'fit',
				region:'center',
				monitorResize : true,
				bodyStyle : 'padding:0px 2px 5px 2px',
				items : [clmLineChartPnl]
			});
	
	var clm_gridStore = new Ext.data.Store({
		    proxy : new Ext.data.HttpProxy({
				url:'./qrystat/currLoadMonitor!loadOrgInfo.action'
			}),
			reader : new Ext.data.JsonReader({
				root : 'clmList',
				idProperty: 'orgNo'
			}, [
			   {name : 'orgNo'},
			   {name : 'orgType'},
			   {name : 'orgName'},
			   {name : 'maxp'},
			   {name : 'maxpTime'},
			   {name : 'minp'}, 
			   {name : 'minpTime'}, 
			   {name : 'mmr'}, 
			   {name : 'midp'}, 
			   {name : 'pr'}, 
			   {name : 'mmsr'}
			   ])
			});

	clm_gridSm = new Ext.grid.CheckboxSelectionModel({});	
	
	var rowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if (clm_gridStore && clm_gridStore.lastOptions
					&& clm_gridStore.lastOptions.params) {
				startRow = clm_gridStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
			
	var clm_gridCm = new Ext.grid.ColumnModel([rowNum, clm_gridSm,
			 {
				header : '供电单位',
				dataIndex : 'orgName',
				width: 140,
				sortable : true,
				align : 'center'
			}, {
				header : '当日负荷峰值',
				dataIndex : 'maxp',
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
			}, {
				header : '峰值时间',
				dataIndex : 'maxpTime',
				sortable : true,
				width:80,
				align : 'center'
			}, {
				header : '当日负荷谷值',
				dataIndex : 'minp',
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
			}, {
				header : '谷值时间',
				dataIndex : 'minpTime',
				sortable : true,
				width:80,
				align : 'center'
			}, {
				header : '峰谷比',
				dataIndex : 'mmr',
				width:80,
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
			}, {
				header : '平均负荷',
				dataIndex : 'midp',
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
			}, {
				header : '负荷率(%)',
				dataIndex : 'pr',
				sortable : true,
				width:80,
				align : 'center',
				renderer : function(val) {
				    if(Ext.isEmpty(val)){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : '峰谷差率(%)',
				dataIndex : 'mmsr',
				sortable : true,
				width:80,
				align : 'center',
				renderer : function(val) {
				    if(Ext.isEmpty(val)){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}]);

	var clm_Grid = new Ext.grid.GridPanel({
				region:'center',
				autoScroll : true,
				stripeRows : true,
				viewConfig : {
					forceFit : false
				},
				cm : clm_gridCm,
				sm : clm_gridSm,
				store : clm_gridStore,
				tbar : [{
					xtype:'label',
					html : "<font font-weight:bold;>日用电负荷监测明细【单位：万千瓦】</font>"
				}]
			});
	
	clm_Grid.getSelectionModel().on('rowdeselect', 
			function(sm,rowIndex,r){
				if(!Ext.isEmpty(curveRealList)){
		        	Ext.each(curveRealList,function(obj){
		        		if(!Ext.isEmpty(obj['orgNo'])){
		        			if(!Ext.isEmpty(r.get('orgNo')) 
		        					&&obj['orgNo'] == r.get('orgNo')){
		        				if(!Ext.isEmpty(obj['name'])){
		        					obj['name'] = '';
		        					if(!curveChgFlag){
		        						curveChgFlag = true;
		        					}
		        				}
		        			}
		        		}
		            });
		        }
				if(!Ext.isEmpty(curveStatList)){
		        	Ext.each(curveStatList,function(obj){
		        		if(!Ext.isEmpty(obj['orgNo'])){
		        			if(!Ext.isEmpty(r.get('orgNo')) 
		        					&&obj['orgNo'] == r.get('orgNo')){
		        				if(!Ext.isEmpty(obj['name'])){
		        					obj['name'] = '';
		        					if(!curveChgFlag){
		        						curveChgFlag = true;
		        					}
		        				}
		        			}
		        		}
		            });
		        }
		        if(curveChgFlag){
				    xmlData = getMultiCLMXMLData(clmTimeModelList,curveRealList,curveStatList);
				    generateClmLine(xmlData,width,height);
				    curveChgFlag = false;
		        }
			});
	
	clm_Grid.getSelectionModel().on('rowselect', 
			function(sm,rowIndex,r){
				if(!Ext.isEmpty(curveRealList)){
		        	Ext.each(curveRealList,function(obj){
		        		if(!Ext.isEmpty(obj['orgNo'])){
		        			if(!Ext.isEmpty(r.get('orgNo')) 
		        					&&obj['orgNo'] == r.get('orgNo')){
		        				if(Ext.isEmpty(obj['name'])){
		        					obj['name'] = obj['orgName']+"实时曲线";
		        					if(!curveChgFlag){
		        						curveChgFlag = true;
		        					}
		        				}
		        			}
		        		}
		            });
		        }
				if(!Ext.isEmpty(curveStatList)){
		        	Ext.each(curveStatList,function(obj){
		        		if(!Ext.isEmpty(obj['orgNo'])){
		        			if(!Ext.isEmpty(r.get('orgNo')) 
		        					&&obj['orgNo'] == r.get('orgNo')){
		        				if(Ext.isEmpty(obj['name'])){
		        					obj['name'] = obj['orgName']+"冻结曲线";
		        					if(!curveChgFlag){
		        						curveChgFlag = true;
		        					}
		        				}
		        			}
		        		}
		            });
		        }
		        if(curveChgFlag){
				    xmlData = getMultiCLMXMLData(clmTimeModelList,curveRealList,curveStatList);
				    generateClmLine(xmlData,width,height);
				    curveChgFlag = false;
		        }
			});
	
		var clmpanel = new Ext.Panel({
				region:'north',
				height:300,
				autoScroll : true,
				layout:'border',
				border : false,
				items : [currLoadMonitorTopPnl, clm_CurvePnl]
			});
	// 设置顶层的当日用电负荷监测panel
	var currLoadMonitorpanel = new Ext.Panel({
				autoScroll : true,
				border : false,
				layout:'border',
				items : [clmpanel,clm_Grid]
			});
	
	 renderModel(currLoadMonitorpanel,'日用电负荷监测');
	
	 /**
	  * 响应首页“日用电负荷监测”事件
	  */
	 if(paraDay == 0){
	 	clm_queryDate.setValue(new Date());
//	 	statCheckbox.setValue(true);
	 	
	 	var orgType;
	 	var orgNo;
	 	var recs;
	 	var orgList;
	 	//获取组织机构信息
	 	Ext.Ajax.request({
	 		url:'./qrystat/powerSortAnalyse!getOOrgById.action',
	 		success : function(response){
	 			var result = Ext.decode(response.responseText);
	 			var org = result.org;
	 			orgNo = org.orgNo;
	 			orgType=org.orgType
	 			clm_gridStore.removeAll();
				if (orgType == '04') {
					var object = new Object();
					object.orgNo = orgNo;
					object.orgName = node.text;
					object.orgType = orgType;
					var records = new Ext.data.Record(object);
					clm_gridStore.add(records);
					var array = new Array(records);
					clm_Grid.getSelectionModel().selectRecords(array,
							true);
				} else if (orgType == '02' || orgType == '03') {
					clm_gridStore.baseParams = {
						orgNo : orgNo,
						orgType : orgType
					};
					clm_gridStore.load({
								callback : function(records, options,
										success) {
									clm_Grid.getSelectionModel()
											.selectAll();
								
							       	recs = clm_gridSm.getSelections();
							        if(null == recs || recs.length==0){
								     	Ext.MessageBox.alert("提示","请选择供电单位！");
								     	return;
								    }
							        
								    orgList = new Array();
								    for(var i = 0; i < recs.length; i++){
								    	if(null != recs[i].get('orgNo') && 
								    	    recs[i].get('orgNo').length >= 0 &&
								    	    null != recs[i].get('orgType')&&
								    	    recs[i].get('orgType').length >= 0){
								    	        orgList[i] = recs[i].get('orgNo')+ "!" + recs[i].get('orgType') + "!" + recs[i].get('orgName');
								    	    }
								    }	  		    
									
								    //
							        Ext.Ajax.request({
							     		url:'./qrystat/currLoadMonitor!loadGridData.action',
							     		params : {
							        	    queryDate:clm_queryDate.getValue(),
							   	            orgList:orgList,
							   	            realCheckValue:realCheckbox.getValue(),
							   	            statCheckValue:statCheckbox.getValue()
							     		},
							     		success : function(response){
							     			var result = Ext.decode(response.responseText);
							     			flag = true;
							     			if(result==null || null == result.clmList) return true;
							                clm_gridStore.removeAll();
							     			clm_gridStore.loadData(result);
							     			clm_Grid.getSelectionModel().selectAll();
							     			clm_lineData = result.clmList;
							     			curveRealList = result.curveRealList;
							     			curveStatList = result.curveStatList;
							     			
							     			clmTimeModelList = result.clmTimeModelList;
							     			xmlData = getMultiCLMXMLData(clmTimeModelList,curveRealList,curveStatList);
							        	    generateClmLine(xmlData,width,height);
							     		    return;
							     		},
							     		failure : function(){
							     		    Ext.MessageBox.alert("提示","失败");
							     		    return;
							     		}
							     	});
								    
								}
							});

				}
	 		},
	 		failure : function(){
	 			Ext.MessageBox.alert('错误','获取组织机构信息失败！');
	 		}
	 	});
	 }
	 
	// 监听左边树点击事件
    var treeListener = new LeftTreeListener({
	    modelName : '日用电负荷监测',
	    processClick : function(p, node, e) {
		    var obj = node.attributes.attributes;
		    var type = node.attributes.type;
			if (type == 'org') {
				clm_gridStore.removeAll();
				
	   	        var orgType = obj.orgType;
	   	        var orgNo = obj.orgNo;
				if(orgType == '04'){
					curveRealList = '';//实时曲线列表
					curveStatList = '';//冻结曲线列表
					clmTimeModelList = '';//时间模
					xmlData = getMultiCLMXMLData(clmTimeModelList,curveRealList,curveStatList);
					generateClmLine(xmlData,width,height);
					var object = new Object();
					object.orgNo = orgNo;
					object.orgName = node.text;
					object.orgType = orgType;
					var records = new Ext.data.Record(object);
					clm_gridStore.add(records);
					var array = new Array(records);
					clm_Grid.getSelectionModel().selectRecords(array,true);
				}else if(orgType == '02' || orgType == '03'){
					curveRealList = '';//实时曲线列表
					curveStatList = '';//冻结曲线列表
					clmTimeModelList = '';//时间模
					xmlData = getMultiCLMXMLData(clmTimeModelList,curveRealList,curveStatList);
					generateClmLine(xmlData,width,height);
				    clm_gridStore.baseParams = {orgNo:orgNo, orgType:orgType};
	                clm_gridStore.load({
	                    callback: function(records, options, success){
	                	clm_Grid.getSelectionModel().selectAll();
//	                	    if(clm_gridStore.getCount() >= 4){
//	                	        var array = new Array();
//	                	        array[0] = 0;
//	                	        array[1] = 1;
//	                	        array[2] = 2;
//	                	        array[3] = 3;
//	                	        clm_Grid.getSelectionModel().selectRows(array, true);
//	                        }else{
//	                	        clm_Grid.getSelectionModel().selectAll();
//	                        }
	                    }
	                });
				}
			}else {
				return true;
			}
   	    }
    });
});

/**
 * @描述: 返回曲线，针对日用电负荷监测页面的曲线
 * @param {} powerResult 功率曲线
 * @param {} curResult 电流曲线
 * @param {} voltResult 电压曲线
 * @param {} factorResult 功率因素曲线
 * @param {} curveType 曲线类型
 * @author 姜炜超
 */
function getMultiCLMXMLData(clmTimeModelList,curveRealList,curveStatList){
	
	var xmlData = "<chart legendPosition='BOTTOM' lineThickness='1' showValues='0' formatNumberScale='0'" +
					" anchorRadius='1' divLineAlpha='10' divLineColor='CC3300' " +
					"divLineIsDashed='1' showAlternateHGridColor='1' " +
					"alternateHGridAlpha='5' alternateHGridColor='CC3300'" +
					" shadowAlpha='40' labelStep='1' numvdivlines='22' " +
					"chartRightMargin='15' bgColor='FFFFFF,CC3300' " +
					"bgAngle='270' bgAlpha='10,10'>";
	
    var str = "<categories>";
    Ext.each(clmTimeModelList,function(obj){
	    str += "<category label='"+obj['maxpTime']+"' />";
    });
    str += "</categories>";

    xmlData = xmlData+str;
    
    if(!Ext.isEmpty(curveRealList )){
    	 Ext.each(curveRealList,function(obj){
    		var color = randomColor();
    		if(!Ext.isEmpty(obj['name'])){
    			 var str1 = "<dataset seriesName='" + obj['name'] +"'"+" color='"+color+"' anchorBorderColor='"+color+"' anchorBgColor='"+color+"'>";
    		     Ext.each(obj['list'],function(obj1){
    		          if(obj1['flag']){
    		               str1 += "<set />";
    		          }else{
    		        	   str1 += "<set value='"+obj1['data']+"' tooltext='"+obj['name']+","+obj1['data']+"'/>";
    		          }
    		     });
    		     str1 += "</dataset>";
    		     xmlData = xmlData+str1;
    		 }
         });
    }
    
    if(!Ext.isEmpty(curveStatList )){
   	 Ext.each(curveStatList,function(obj){
   		var color = randomColor();
   		if(!Ext.isEmpty(obj['name'])){
   			 var str1 = "<dataset seriesName='" + obj['name'] +"'"+" color='"+color+"' anchorBorderColor='"+color+"' anchorBgColor='"+color+"'>";
   		     Ext.each(obj['list'],function(obj1){
   		          if(obj1['flag']){
   		               str1 += "<set />";
   		          }else{
   		        	   str1 += "<set value='"+obj1['data']+"' tooltext='"+obj['name']+","+obj1['data']+"'/>";
   		          }
   		     });
   		     str1 += "</dataset>";
   		     xmlData = xmlData+str1;
   		 }
        });
   }
    
    xmlData = xmlData+"<styles><definition>"+
		"<style name='CaptionFont' type='font' size='12' />"+
		"</definition><application>"+
		"<apply toObject='CAPTION' styles='CaptionFont' />"+ 
		"<apply toObject='SUBCAPTION' styles='CaptionFont' />"+
		"</application></styles></chart>";

	return xmlData;
}

/**
 * @描述 生成随机的颜色
 * @return {}
 */
function randomColor() {
	return ''+(Math.random()*0xffffff<<0).toString(16); 
}
