/**
 * @author陈国章
 * @description 地区数据报表查询
 * @version 1.00
 */
Ext.onReady(function() {
	var treeListener;
	var reportParamsPanelSecond = new Ext.Panel({});
	var userTerminal;//=  new Ext.grid.CheckboxSelectionModel({});
	var reportParamStore={};
	var reportParamsPanel={};
	var reportPanel={};
	var nextProcess;
	var reportTypeGrid={};
	var reportManCard={};
	var reportId;
	var getParamsUrl=function(){};
	var dataSelect=true;
	var timeSelect=true;
	
	var ParamsUrl='';
					
	/** -------------------------第一个页面---------------------------- */
var sm = new Ext.grid.RowSelectionModel( {
			singleSelect : true
		});

		var reportTypeCm = new Ext.grid.ColumnModel( [ {
			header : '模板ID',
			dateIndex : 'templateId',
			align : 'left',
			hidden : true

		},{
			header : '报表ID',
			dateIndex : 'reportId',
			align : 'left',
     		hidden : true

		}, {
			header : '原生报表类型',
			dateIndex : 'rawReportType',
			align : 'left',
			hidden : true

		}, {
			header : '原生报表名称',
			dateIndex : 'rawReportName',
			align : 'left',
			hidden : true

		}, {
			header : '自定义报表类型',
			dateIndex : 'reportType',
			align : 'left'
			
		}, {
			header : '自定义报表名称',
			dateIndex : 'reportName',
			align : 'left'

		}, {
			header : '用户列表',
			dateIndex : 'orgNoList',
			align : 'left',
			hidden : true

		}, {
			header : '报表描述',
			dateIndex : 'reportParam',
			align : 'left'

		} ]);
		/** -----------------------存放报表信息的表格Store，第一个页面--------------- */
		reportStore = new Ext.data.JsonStore({
					url : './qrystat/reportTypeQueryAction!queryReportTemplateByType.action',
					fields : [ 'templateId','reportId', 'rawReportType', 'rawReportName',
							'reportType', 'reportName', 'orgNoList',
							'reportParam' ],
					root : 'repTemList',
					totalProperty : "reportCount"
				});
		reportStore.baseParams = {
			reportType : "自定义地区数据报表"
		};
		reportStore.load();

			
	// 报表类型和名称等信息的Grid
	reportTypeGrid = new Ext.grid.GridPanel({
				mode : 'remote',
				id : 'areaDataReportTemplateGrid',
				// ds : reportStore,
				region : 'south',
				cm : reportTypeCm,
				store : reportStore,
				sm :sm,
				loadMask : false,// true,加载数据时--"读取中"
				autoScroll : true,
				autoWidth : true,
				stripeRows : true,
				viewConfig : {
					forceFit :true
				},
				tbar : [{
								xtype : 'label',
								html : "<font font-weight:bold;>地区数据报表</font>"
						},'->',
					{
						text : '删除模板',
						iconCls : 'minus',
						width : 30,
						handler : function() {
							var record = reportTypeGrid.getSelectionModel()
									.getSelected();
							if ((null == record) || Ext.isEmpty(record)
									|| record.length == 0) {
								Ext.Msg.alert("提示", "请选报表模板!");
								return;
							} else {

								Ext.MessageBox.show( {
									title : "删除模板",
									msg : "你确定要删除此模板？",
									buttons : Ext.Msg.YESNOCANCEL,
									fn : deleteTemplate,
									icon : Ext.MessageBox.QUESTION
								});
							}
						}
					} ]
			});
	function deleteTemplate() {
			var record = reportTypeGrid.getSelectionModel().getSelected();
			Ext.Ajax.request({
				url : './qrystat/reportTypeQueryAction!deleteConsNoListTemplate.action',
				method : 'post',
				params : {
					'templateId' : reportTypeGrid.getSelectionModel().getSelected()
							.get('templateId')
				}
			});
			reportStore.remove(record);
			reportTypeGrid.reconfigure(reportStore, reportTypeCm);
		}
			
	// 利用Panel来存储GRID
	var bottomGridPanel = new Ext.Panel({
				layout : 'fit',
				region : 'center',
				autoScroll : true,
				border : false,
				plain : true,
				items : [reportTypeGrid]
			});
	// 第一个页面的综合Panel
	var reportTypePanel = new Ext.Panel({
				layout : 'border',
				border : false,
				items : [bottomGridPanel]

			});
	

	    
/**------------------------------------综合操作----------------------------------- */


	// 上一步操作
	var previousProcess = function(btn) {
		if (reportManCard.layout.activeItem == reportTypePanel) {
			} else if (reportManCard.layout.activeItem == reportParamsPanelSecond) {
			 treeListener.removeListener();	
			 reportManCard.remove(reportParamsPanelSecond);
			 reportManCard.layout.setActiveItem(0);
			 previousButton.hide();
		}
	};
	// 下一步按钮处理方法
	nextProcess = function() {
		if (reportManCard.layout.activeItem == reportTypePanel) {
			if((Ext.isEmpty(reportTypeGrid.getSelectionModel().getSelected()))){
				Ext.MessageBox.alert('提示','请选择报表');
				return;
			}
			 var reportType = reportTypeGrid.getSelectionModel().getSelected()
				.get('rawReportType');
		     var reportName = reportTypeGrid.getSelectionModel().getSelected()
				.get('rawReportName');
			reportId=reportTypeGrid.getSelectionModel().getSelected().get('reportId');
//			if((Ext.isEmpty(reportName))||0==reportName.length){
//				Ext.MessageBox.alert('提示','请选择报表');
//				return;
//			}
			reportManCard.remove(reportParamsPanelSecond);
			Ext.Ajax.request({
					url : './qrystat/reportTypeQueryAction!queryTemplateJSFile.action',
					params : {
						reportType : reportType,
						reportName:  reportName
						},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						var scriptConent = result.scriptConent;
						eval(scriptConent);
//						getParamsUrl();
						reportManCard.add(reportParamsPanelSecond);
						reportManCard.doLayout();
						reportManCard.layout.setActiveItem(1);
						previousButton.show();
					}
				});
				
				
		}
		 else if (reportManCard.layout.activeItem == reportParamsPanelSecond) {
//		 	var reportId=reportTypeGrid.getSelectionModel().getSelected().get('reportId');
//		 	var time=Ext.getCmp("time_tmnl").getRawValue();
		 	getParamsUrl();
//		 	alert(reportId);
		 	
		 	ParamsUrl=ParamsUrl+"&reportId="+reportId;
//		 	alert(ParamsUrl);
		 	if(dataSelect&&timeSelect)
		 	{
		 		window.open("/sea/Report.servlet?"+ParamsUrl);

		 	}
		}

	};

	// 上一步按钮
	var previousButton = new Ext.Button({
				text : '上一步',
				handler : previousProcess

			});
	// 下一步按钮
	var nextButton = new Ext.Button({
				text : '下一步',
				handler:nextProcess
			});
//	nextButton.addEvents('click',nextProcess);

	// 综合界面
	reportManCard = new Ext.Panel({
				border : false,
				region : 'center',
				activeItem : 0,
				layout : 'card',
				buttonAlign : 'center',
				id:'areaDataReportTemplate',
				items : [reportTypePanel, reportParamsPanelSecond],
				buttons : [previousButton, nextButton]
			});
	previousButton.hide();
	renderModel(reportManCard, '自定义地区数据报表');
});