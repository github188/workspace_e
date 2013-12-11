var lps_columnId = new Array("showDate", "ppq", "spq", "llr");
var lps_columnType = new Array("总表电量", "分表电量", "线损率(%)");
var lps_columnColors = new Array('AFD8F8', 'F6BD0F', '8BBA00');
var lps_columnData = [];
var caption;
	
var lpsCm = new Ext.grid.ColumnModel([{
			header : '统计日期',
			dataIndex : 'date',
			sortable : true,
			align : 'left'
		}, {
			header : '总表电量',
			dataIndex : 'ppq',
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
			header : '分表电量',
			dataIndex : 'spq',
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
			header : '损失电量',
			dataIndex : 'lpq',
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
			header : '线损率(%)',
			dataIndex : 'llr',
			sortable : true,
			align : 'center',
			renderer : function(s, m, rec) {
				var html = '<div align = "right">';
				if(Ext.isEmpty(rec.get('llr'))){
					html = html + "";
				}else{
					if(!Ext.isEmpty(rec.get('idx'))){
				        if(rec.get('llr') - rec.get('idx') > 0){
				    	    html = html + '<span style="color:red;">' + rec.get('llr') + '</span>';
				        }else{
				    	    html = html + '<span style="color:green;">' + rec.get('llr') + '</span>';
				        }
				    }else{
				    	html = html +rec.get('llr');
				    }
				}					    
				html = html + '</div>';
				return html;
			}
		}, {
			header : '线损指标(%)',
			dataIndex : 'idx',
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
			header : '差异值',
			dataIndex : 'diff',
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
		}]);

    var lpsStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : './qrystat/losePowerStat!loadWindowData.action'
					}),
			baseParams : {
				tgNo : '',
				flag : ''
			},
			reader : new Ext.data.JsonReader({
						root : 'lpsWindowList',
						totalProperty : 'totalCount'
					}, [{
								name : 'date'
							}, {
								name : 'ppq'
							}, {
								name : 'spq'
							}, {
								name : 'lpq'
							}, {
								name : 'llr'
							}, {
								name : 'idx'
							}, {
								name : 'diff'
							}])
		});

    var lpsGridPnl = new Ext.grid.GridPanel({
			height : 250,
			region:'south',
			stripeRows : true,
			monitorResize : true,
			viewConfig : {
				forceFit : true
			},
			border : true,
			autoScroll : true,
			cm : lpsCm,
			ds : lpsStore,
			bbar : new Ext.ux.MyToolbar({
				        enableExpAll : true,
						store : lpsStore
					}),
			tbar : [{
				xtype:'label',
				html : "<font font-weight:bold;>"+"线损明细"+"</font>"
			}]
		});

    // 电损中间图表panel
    var lpsLineChartPnl = new Ext.Panel({
			border : false,
			bodyBorder : false,
			layout : 'fit',
			monitorResize : false
		});
    
	//用于实现fusionchart动态变化
    lpsLineChartPnl.on("afterlayout", function(view,layout){
			width = lpsCurvePnl.getWidth();
	        height = lpsCurvePnl.getHeight()-4;
	        generateColumnChart(caption,width,height);
	},lpsLineChartPnl);
    
	//
	var lpsCurvePnl = new Ext.Panel({
				border : false,
				bodyBorder : false,
				layout:'fit',
				region:'center',
				monitorResize : true,
				bodyStyle : 'padding:0px 2px 5px 2px',
				items : [lpsLineChartPnl]
			});

    function generateColumnChart(caption,width,height) {
	    // 地区电量分布柱图
	    var pYAxisName = "供售电量";
	    var sYAxisName = "线损率(%)";
	    var xmlData = getMultiCLXMLData(lps_columnData, lps_columnId, caption,
			pYAxisName, sYAxisName, lps_columnColors, lps_columnType);
	    var columnChart = new FusionCharts("fusionCharts/MSColumn3DLineDY.swf",
			"apdColumnChart", width, height);
	    columnChart.setDataXML(xmlData);
	    columnChart.setTransparent(true);
	    if (null != lpsLineChartPnl) {
		    columnChart.render(lpsLineChartPnl.getId());
	    }
    }
	    
	function initial(){
		//lpsGridPnl.setTitle(tgNamel + "线损明细" + "【" + datel + "】");
		caption = tgNamel + "台区" + datel + "线损率";
		lpsStore.removeAll();
		lps_columnData = 0;
		lpsStore.baseParams = {
			tgNo : tgNol,
			flag : flagl,
			startDate : startDatel,
			endDate : endDatel
		};
		Ext.Ajax.request({
		    url : './qrystat/losePowerStat!loadWindowData.action',
		    params : {
		         tgNo : tgNol,
		         flag : flagl,
		         startDate : startDatel,
		         endDate : endDatel
		    },
		    success : function(response) {
		         var result = Ext.decode(response.responseText);
					 if (result == null || null == result.lpsWindowList)
					     return true;
					 lps_columnData = result.lpsWindowList;
					 lpsStore.loadData(result);
					 generateColumnChart(caption,width,height);
					 return;
			},
			failure : function() {
				 Ext.MessageBox.alert("提示", "失败");
				 return;
			}
		});
	}
	
	Ext.onReady(function() {
		initial();
		// 定义整个页面面板
		var viewPanel = new Ext.form.FormPanel({
					layout : 'border',
					border : false,
					items : [lpsCurvePnl, lpsGridPnl]
				});
		renderModel(viewPanel, '台区用电损耗统计详细信息');
	});
