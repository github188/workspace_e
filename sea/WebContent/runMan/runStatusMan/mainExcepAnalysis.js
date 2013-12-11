// 异常类型
var mainExcepStore = new Ext.data.JsonStore({
				idProperty : 'mainExcepCode',
				url : './runman/mainExceptionAnalysis!queryMainExceptionCode.action',
				fields : ['mainExcepCode', 'mainExcepName'],
				root : "mec",
                listeners : {
					load : function() {
						mainExcepCombobox.setValue(mainExcepCombobox.getValue());
					}
				}
			});
    mainExcepStore.load();
	
// 异常类型选择框
var mainExcepCombobox = new Ext.form.ComboBox({
			id : 'mainExcepCombobox',
			fieldLabel : '异常类型',
			store : mainExcepStore,
			triggerAction : 'all',
			editable : false,
			mode : 'local',
			valueField : 'mainExcepCode',
			displayField : 'mainExcepName',
			anchor : '98%',
			emptyText : '请选择',
			selectOnFocus : true
		})
function opencsitab(privateTerminalConvalue) {
	privateTerminalCon = privateTerminalConvalue;
	privateIDCon = privateIDNew;
	openTab("客户综合查询", "./qryStat/queryCollPoint/consumerInfo.jsp");

}
		
Ext.onReady(function() {
	var tmnlForm = new Ext.Panel({
				border : true,
				region : 'north',
				bodyStyle : 'padding:10px 0px',
				layout : 'column',
				height : 45,
				items : [{
							columnWidth : .25,
							border : false,
							labelAlign : 'right',
							labelWidth : 50,
							layout : 'form',
							items : [{
										xtype : 'textfield',
										id : 'mainE_orgName',
										allowBlank : false,
										width : 120,
										readOnly : true,
										labelSeparator : ' ',
										fieldLabel : '节点名 ',
										emptyText : '请选择'
									}, {
										xtype : 'hidden',// **********增加隐藏组件，对应各左边树选取的节点赋值，是否有更简便的方法?
										id : 'mainE_orgNo'//单位编号
									}, {
										xtype : 'hidden',
										id : 'mainE_usrNo'//用户编号
									}, {
										xtype : 'hidden',
										id : 'mainE_lineId'//线路编号
									}, {
										xtype : 'hidden',
										id : 'mainE_orgType'//单位类别
									}, {
										xtype : 'hidden',
										id : 'mainE_nodeType'//org单位,usr用户,line线路,sub台区
									},  {
										xtype : 'hidden',
										id : 'mainE_subsId'//台区编号
									}]
						}, {
						   columnWidth:.06,
						   border:false,
						   html:'<font size="-1">发生时间</font>'
							         
						},{
							border : false,
							columnWidth : .16,
							labelAlign : 'right',
							labelWidth : 15,
							layout : 'form',
							items : [{
										id : 'timeS_mainE',
										xtype : 'datefield',
										labelSeparator : ' ',
										editable : false,
										value : new Date().add(Date.DAY, -6),
										maxValue : new Date(),
										fieldLabel : '从',
										format : 'Y-m-d'
									}]
						}, {
							columnWidth : .16,
							border : false,
							labelAlign : 'right',
							labelWidth : 15,
							layout : 'form',
							items : [{
										id : 'timeE_mainE',
										xtype : 'datefield',
										labelSeparator : ' ',
										width : 90,
										editable : false,
										value : new Date(),
										maxValue : new Date(),
										fieldLabel : '到',
										format : 'Y-m-d'
									}]
						}, {
							columnWidth : .23,
							border : false,
							layout : 'form',
							labelWidth : 50,
							labelAlign : 'right',
							items : [mainExcepCombobox]
						}, {
							columnWidth : .14,
							border : false,
							layout : 'form',
							items : [{
								xtype : 'button',
								text : '查询',
								width : 60,
								listeners : {
									"click" : function() {
										var panelMask = new Ext.LoadMask(panel
														.getId(), {// 加载数据时，防止重复提交
													msg : "加载中,请稍后..."
												});
										panelMask.show();
										var jdm = Ext.getCmp("mainE_orgName")
												.getValue();
										var type = Ext
												.getCmp("mainE_nodeType")
												.getValue();
										var startSj = Ext
												.getCmp("timeS_mainE")
												.getValue().format('Y-m-d');
										var endSj = Ext
												.getCmp("timeE_mainE")
												.getValue().format('Y-m-d');
                                        var alarmType = Ext.getCmp("mainExcepCombobox").getValue();
										if (jdm == null || jdm.trim() == "") {
											Ext.MessageBox.minWidth = 120;// 控制窗口大小
											Ext.MessageBox.alert("提示",
													"请选择节点名！");
											panelMask.hide();// 提示后关闭或隐藏
											return;
										}
										if (type == 'org') {
											storeTe.baseParams = {
												orgNo : Ext
														.getCmp("mainE_orgNo")
														.getValue(),
												orgType : Ext
														.getCmp("mainE_orgType")
														.getValue(),
												mainExcepCode : alarmType,
												nodeType : type,
												startDate : startSj,
												endDate : endSj
											}
											storeTe.load();

										} else if (type == 'usr') {
											storeTe.baseParams = {
												consNo : Ext
														.getCmp("mainE_usrNo")
														.getValue(),
												mainExcepCode : alarmType,
												nodeType : type,
												startDate : startSj,
												endDate : endSj
											}
											storeTe.load();
										}else if (type == 'line') {
											storeTe.baseParams = {
												lineId : Ext
														.getCmp("mainE_lineId")
														.getValue(),
												mainExcepCode : alarmType,
												nodeType : type,
												startDate : startSj,
												endDate : endSj
											}
											storeTe.load();
										}else if (type == 'sub') {
											storeTe.baseParams = {
												subsId : Ext
														.getCmp("mainE_subsId")
														.getValue(),
												mainExcepCode : alarmType,
												nodeType : type,
												startDate : startSj,
												endDate : endSj
											}
											storeTe.load();

										}
										// loadMask.......
										storeTe.on('load', function() {
													panelMask.hide();
												}); // 由于是异步，必须在请求返回数据处理完成后才关闭LoadMask
										
									}
								}
							}]
						}
						]
			});

	var rowNum = new Ext.grid.RowNumberer({
			renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if (storeTe && storeTe.lastOptions
				&& storeTe.lastOptions.params) {
			    startRow = storeTe.lastOptions.params.start;
				}
				return startRow + rowIndex + 1;
				}
			});
	var cm = new Ext.grid.ColumnModel([rowNum,{
				header : '供电单位',
				dataIndex : 'orgName',
				align : 'center',
				width : 150,
				renderer : function(val) {
					return '<div align = "left">' + val + '</div>';
				}
			}, {
				header : '用户编号',
				dataIndex : 'consNo',
				align : 'center',
				width : 100,
				renderer : function(val) {
					return "<a href='javascript:' onclick=''>" + val + "</a>";
				}
			}, {
				header : '用户名称',
				dataIndex : 'consName',
				align : 'center',
				width : 150,
				renderer : function(val) {
					return '<div align = "left">' + val + '</div>';
				}
			}, {
				header : '终端地址',
				dataIndex : 'terminalAddr',
				align : 'center',
				width : 100,
				renderer : function(val) {
					return '<div align = "left">' + val + '</div>';
				}
			}, {
				header : '故障名称',
				dataIndex : 'exceptName',
				align : 'center',
				width : 100,
				renderer : function(val) {
					return "<div align = 'left'>" + val + "</div>";
				}
			}, {
				header : '发生时间',
				dataIndex : 'exceptDate',
				align : 'center',
				width : 150,
				renderer : function(val) {
					return "<div align = 'left'>" + val + "</div>";
				}
			}, {
				header : '终端厂商',
				dataIndex : 'factoryName',
				align : 'center',
				width : 100,
				renderer : function(val) {
					return "<div align = 'left'>" + val + "</div>";
				}
			},{
				header : '合同容量',
				dataIndex : 'contractCap',
				align : 'center',
				width : 100,
				renderer : function(val) {
					return "<div align = 'left'>" + val + "</div>";
				}
			},{
				header : '电压等级',
				dataIndex : 'voltageGrade',
				align : 'center',
				width : 100,
				renderer : function(val) {
					return "<div align = 'left'>" + val + "</div>";
				}
			}, {
				header : '接线方式',
				dataIndex : 'wiringMode',
				align : 'center',
				width : 100,
				renderer : function(val) {
					return "<div align = 'left'>" + val + "</div>";
				}
			},{
				header : '异常说明',
				dataIndex : 'remark',
				align : 'center',
				width : 250,
				renderer : function(val) { 
					return '<font color="red">' + val + '</font>';
				}
			}]);
			

	var storeTe = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'runman/mainExceptionAnalysis!queryMainExcepAnalysis.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'mainExcepAnalyList',
					totalProperty : 'totalCount'
				}, [    {
							name : 'orgName'
						}, {
							name : 'consNo'
						}, {
							name : 'consName'
						},{
							name : 'terminalAddr'
						}, {
							name : 'exceptName'
						}, {
							name : 'exceptDate'
							
						}, {
							name : 'factoryName'
						}, {
							name:'contractCap'
						},{
							name:'voltageGrade'
						},{
							name:'wiringMode'
						},{
							name : 'remark'
						}])
	});

	var teGrid = new Ext.grid.GridPanel({ // 异常工况表格
		ds : storeTe,
		region : 'center',
		cm : cm,
		loadMask : false,// true,加载数据时--"读取中"
		autoScroll : true,
		autoWidth : true,
		stripeRows : true,// 显示行分隔
		viewConfig : {// 视图配置项
			forceFit : false
			// 整个column宽度自适应大小
		},
		bbar : new Ext.ux.MyToolbar({
					store : storeTe,
					enableExpAll : true, // excel导出全部数据
					expAllText : "全部",
					enableExpPage : true, // excel仅导出当前页
					expPageText : "当前页"
				})
	});
	
	teGrid.addListener('cellclick', prcellclick);
	
	function prcellclick(teGrid, rowIndex, columnIndex, e){
    if(rowIndex >= 0 && columnIndex==2){
     var record = teGrid.getStore().getAt(rowIndex);  // Get the Record
    var cellconsId = teGrid.getColumnModel().getDataIndex(columnIndex+1); // Get field name
    var cellconsNo= teGrid.getColumnModel().getDataIndex(columnIndex); // Get field name
     messageconsId = record.get(cellconsId);
     messageconsNo = record.get(cellconsNo);
		privateIDNew = messageconsId;
		privateTerminalConvalue = messageconsNo;
		opencsitab(privateTerminalConvalue);
		
    }
};

	var panel = new Ext.Panel({ // 终端异常工况radio页面
		border : false,
		layout : 'border',
		items : [tmnlForm, teGrid]
	});



	// 监听左边树点击 暂无行业 **
	var treeListener = new LeftTreeListener({
		modelName : '主站异常分析',// !
		processClick : function(p, node, e) {
			var obj = node.attributes.attributes;
			var type = node.attributes.type;

			if (type == 'org') {// obj.orgType != '02' 省节点不可点选、查询
				Ext.getCmp("mainE_orgType").setValue(obj.orgType);
				Ext.getCmp("mainE_nodeType").setValue(type);
				Ext.getCmp("mainE_orgName").setValue(obj.orgName);
				Ext.getCmp("mainE_orgNo").setValue(obj.orgNo);

			} 
			else if (type == 'usr') {
				Ext.getCmp("mainE_nodeType").setValue(type);
				Ext.getCmp("mainE_orgName").setValue(obj.consName);
				Ext.getCmp("mainE_usrNo").setValue(obj.consNo);

			} 
			else if (type == 'line') {
				Ext.getCmp("mainE_nodeType").setValue(type);
				Ext.getCmp("mainE_orgName").setValue(obj.lineName);
				Ext.getCmp("mainE_lineId").setValue(obj.lineId);
			}else if (type == 'sub') {
				Ext.getCmp("mainE_nodeType").setValue(type);
				Ext.getCmp("mainE_orgName").setValue(obj.subsName);
				Ext.getCmp("mainE_subsId").setValue(obj.subsId);
			} else {
				return true;
			}
		}

	});

	renderModel(panel, '主站异常分析');
	
    var openAnotherPage = function(){
    Ext.Ajax.request({
    	url:'./runman/mainExceptionAnalysis!queryOrgName.action',
    	params:{
    		orgNo:userInfoOrgNo
    	},
    	success:function(response)
    	{
    		var o=Ext.decode(response.responseText);
    		Ext.getCmp("mainE_orgName").setValue(o.orgName);
    		
    	}
    })
	Ext.getCmp("mainExcepCombobox").setValue(excepCategoryNo);
	Ext.getCmp("timeS_mainE").setValue(new Date().add(Date.DAY, -30));
	Ext.getCmp("timeE_mainE").setValue(new Date());
	Ext.getCmp("mainE_orgNo").setValue(userInfoOrgNo);
	Ext.getCmp("mainE_nodeType").setValue('org');
	Ext.getCmp("mainE_orgType").setValue('03');
	storeTe.baseParams = {
			orgNo : Ext.getCmp("mainE_orgNo").getValue(),
		    
			orgType : '03',
			mainExcepCode : Ext.getCmp("mainExcepCombobox").getValue(),
			nodeType : 'org',
			startDate : Ext.getCmp("timeS_mainE").getValue().format('Y-m-d'),
			endDate : Ext.getCmp("timeE_mainE").getValue().format('Y-m-d')
			}
	storeTe.load();	
	
    }
	
    if (typeof(excepCategoryNo) != 'undefined'&&!Ext.isEmpty(excepCategoryNo)) {
    	openAnotherPage();
    }
//	userInfoOrgNo=undefined;
	excepCategoryNo=undefined;
	Ext.getCmp('主站异常分析').on('activate', function() {
	    openAnotherPage();
	});
});


