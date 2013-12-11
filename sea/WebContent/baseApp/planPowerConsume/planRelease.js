Ext.onReady(function() {
	Ext.apply(Ext.form.VTypes, {
		daterange : function(val, field) {
			var date = field.parseDate(val);
			if (!date) {
				return;
			}
			if (field.startDateField
					&& (!this.dateRangeMax || (date.getTime() != this.dateRangeMax
							.getTime()))) {
				var start = Ext.getCmp(field.startDateField);
				start.setMaxValue(date);
				start.validate();
				this.dateRangeMax = date;
			} else if (field.endDateField
					&& (!this.dateRangeMin || (date.getTime() != this.dateRangeMin
							.getTime()))) {
				var end = Ext.getCmp(field.endDateField);
				end.setMinValue(date);
				end.validate();
				this.dateRangeMin = date;
			}
			/*
			 * Always return true since we're only using this vtype to set the
			 * min/max allowed values (these are tested for after the vtype
			 * test)
			 */
			return true;
		}
	});
	// // 控制类别store
	// var ctrlTypeStore = new Ext.data.ArrayStore({
	// fields : ['value', 'text'],
	// data : ctrlTypeData
	// });
	//
	// Ext.form.ComboBox
	// 数据字典
	// 功控类型
	var gongkongType = {
		"01" : "时段控",
		"02" : "厂休控",
		"03" : "营业报停控",
		"04" : "当前功率下浮控",
		"05" : "遥控",
		"06" : "月电量定值控",
		"07" : "购电控"
	};
	// 限电类型字典
	var xiandianType = {
		"001" : "错峰群组",
		"002" : "避峰群组",
		"003" : "负控限电群组"
	};
	// 机构名称的字典
	var jigouName;
	var limitTypeJson = new Ext.data.JsonStore({
			url : "./baseapp/planCompile!findLimitTypes.action",
			root : "limitTypes",
			fields : [{
						name : "value",
						mapping : "limitType"
					}, {
						name : "text",
						mapping : "limitTypeName"
					}]
		});

	/*var ctrlTypeJson = new Ext.data.JsonStore({
				url : "./baseapp/planCompile!findCtrlSchemeType.action",
				fields : [{
						name : "value",
						mapping : "id.ctrlSchemeNo"
					}, {
						name : "text",
						mapping : "id.ctrlSchemeType"
					}],
				idProperty : "id.ctrlSchemeNo",
				root : "schemeTypes"
			});*/
	var data_ctrlType = [['01', '时段控'], ['02', '厂休控'], ['04', '当前功率下浮控'], ['06', '月电量定值控']];
	var ds_ctrlType = new Ext.data.SimpleStore({
			fields : ['ctrlTypeCode', 'ctrlType'],
			data : data_ctrlType
		});
	var releaseTypeCombo = new Ext.form.ComboBox({
				fieldLabel : '功控类型',
				width : 120,
				id : "releaseSchemeType",
				// typeAhead : true,
				mode : "local",
				forceSelection : true,
				triggerAction : 'all',
				selectOnFocus : true,
				store : ds_ctrlType,
				emptyText : '请选择功控类型',
				displayField : 'ctrlType',
	            valueField : 'ctrlTypeCode'
			});

	var releaseCondPanel2 = new Ext.Panel({
				border : false,
				layout : 'column',
				items : [{
							columnWidth : .55,
							border : false,
							layout : 'form',
							labelSeparator : ' ',
							labelAlign : 'right',
							items : [{
										xtype : 'datefield',
										id : "ctrlReleaseDateStart",
										fieldLabel : '控制日期',
										format : 'Y-m-d',
//										vtype : "daterange",
//										endDateField : 'ctrlDateEnd',
										value:new Date()
									}]
						}, {
							columnWidth : .45,
							border : false,
							layout : 'form',
							labelWidth : 10,
							labelSeparator : ' ',
							labelAlign : 'right',
							items : [{
										xtype : 'datefield',
										fieldLabel : '到  ',
										id : "ctrlReleaseDateEnd",
										format : 'Y-m-d',
//										vtype : "daterange",
//										startDateField : 'ctrlDateStart',
										value:new Date().add(Date.DAY,30)
									}]
						}]
			});
			
	var releaseButton = new Ext.Button({
		text : '查询',
		id : "release_find",
		handler : function() {
			findAction();
		}
	});
	
	var disableSchemeButton = new Ext.Button({
		text : "启/停方案",
		handler : function() {
			// 用来记录要修改的id
			var ids = [];
			var selects = wCtrlSchemeReleaseGrid.getSelectionModel()
					.getSelections();
			// 出现修改提示
			if (selects.length == 0) {
				return !!Ext.Msg.alert("警告", "你没有选择要启/停用的方案");
			}
			Ext.each(selects, function(r) {
						ids.push(r.get("ctrlSchemeId"));
					});
			Ext.Ajax.request({
				url : "./baseapp/planCompile!deleteCtrlScheme.action",
				params : {
					ctrlSchemeIds : ids
				},
				success : function(response) {
					var o = Ext
							.decode(response.responseText);
					if (o.message && o.message != "") {
						Ext.Msg.alert("警告", o.message);
						return;
					}
					wCtrlSchemeReleaseStore.reload();
				}
			});
		}
	});
	
	var releaseCondPanel1 = new Ext.Panel({
		border : false,
		bodyStyle : 'padding:10px 0px',
		layout : 'column',
		buttonAlign : 'center',
		items : [{
					columnWidth : .3,
					border : false,
					labelSeparator : ' ',
					labelAlign : 'right',
					layout : 'form',
					items : [releaseTypeCombo]
				},{
					columnWidth : .5,
					border : false,
					labelSeparator : ' ',
					labelAlign : 'right',
					layout : 'form',
					items : [releaseCondPanel2]
				},{
					columnWidth : .12,
					border : false,
					labelSeparator : ' ',
					labelAlign : 'right',
					layout : 'form',
					items : [releaseButton]
				}]
	});

	// 查询条件
	var releaseCondPanel = new Ext.Panel({
				region : 'north',
				height : 45,
				border : false,
				items : [releaseCondPanel1]
			});

	//var wCtrlSchemeReleaseSM = new Ext.grid.CheckboxSelectionModel();
	var wCtrlSchemeReleaseRowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(wCtrlSchemeReleaseStore && wCtrlSchemeReleaseStore.lastOptions && wCtrlSchemeReleaseStore.lastOptions.params){
				startRow = wCtrlSchemeReleaseStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	// 任务列表 ColumnModel
	var wCtrlSchemeReleaseCM = new Ext.grid.ColumnModel([wCtrlSchemeReleaseRowNum,/*wCtrlSchemeReleaseSM,*/ {
				hidden : true,
				dataIndex : "ctrlSchemeId"
			}, {
				header : '供电单位',
				align : 'center',
				dataIndex : 'orgNo',
				renderer : function(v) {
				   if(null!=v){
						var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + jigouName[v] + '">'
								+ jigouName[v] + '</div>';
						return html;
		    	   }
		    	   else
		    		    return '';
			   }
			}, {
				header : '限电方案',
				align : 'center',
				dataIndex : 'ctrlSchemeName',
				renderer : function(v) {
				   if(null!=v){
						var html = '<div align = "left" ext:qtitle="限电方案" ext:qtip="' + v + '">'
								+ v + '</div>';
						return html;
		    	   }
		    	   else
		    		    return '';
				   }
			}, {
				header : '限电类型',
				align : 'center',
				dataIndex : 'limitType',
				renderer : function(v) {
					return xiandianType[v];
				}
			}, {
				header : '功控类型',
				align : 'center',
				dataIndex : 'ctrlSchemeType',
				renderer : function(v) {
					return gongkongType[v];
				}
			}, {
				header : '调控负荷',
				align : 'center',
				dataIndex : 'ctrlLoad'
			}, {
				header : '开始日期',
				align : 'center',
				dataIndex : 'ctrlDateStart',
				renderer : function(v) {
					if (v) {
						v = v.replace(/-/g, "/");
						v = v.replace(/T.*/g, "");
						return new Date(v).format("Y年m月d日 ");
						// return v;
					}
				}
			}, {
				header : '结束日期',
				align : 'center',
				dataIndex : 'ctrlDateEnd',
				renderer : function(v) {
					if (v) {
						v = v.replace(/-/g, "/");
						v = v.replace(/T.*/g, "");
						return new Date(v).format("Y年m月d日 ");
						// return v;
					}
				}
			}, {
				header : '操作员编号',
				align : 'center',
				dataIndex : 'staffNo'
			}, {
				header : '备注',
				align : 'center',
				dataIndex : 'schemeRemark',
				renderer : function(v) {
				   if(null!=v){
						var html = '<div align = "left" ext:qtitle="备注" ext:qtip="' + v + '">'
								+ v + '</div>';
						return html;
		    	   }
		    	   else
		    		    return '';
			   }
			}, {
				header : '方案解除',
				align : 'center',
				dataIndex : 'eidt',
				sortable : false,
				renderer : function(s,m,rec) {
					var type = rec.get('ctrlSchemeType');
					if(type=='01'||type=='02' ||type=='03' ||type=='04'||type=='06')
						return "<a href='javascript:'onclick='openReleaseScheme(\""
							+ type + "\",\""
							+ rec.get('ctrlSchemeId') + "\",\""
							+ rec.get('ctrlSchemeName') + "\");'>解除</a>";
					
					return '';	
				}
			}]);

	// 任务列表Store
	var wCtrlSchemeReleaseStore = new Ext.data.Store({
				url : './baseapp/planCompile!findWCtrlScheme.action',
				baseParams : {"wctrlScheme.isValid":'1'},
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'root'
						}, ["ctrlSchemeId", {
									name : 'orgNo'
								}, {
									name : 'ctrlSchemeName'
								}, {
									name : 'limitType'
								}, {
									name : 'ctrlSchemeType'
								}, {
									name : 'ctrlLoad'
								}, {
									name : 'ctrlDateStart'
								}, {
									name : 'ctrlDateEnd'
								}, {
									name : 'staffNo'
								}, {
									name : 'schemeRemark'
								}])
			});

	// 任务列表Grid
	var wCtrlSchemeReleaseGrid = new Ext.grid.GridPanel({
				region : 'center',
				cm : wCtrlSchemeReleaseCM,
				//sm : wCtrlSchemeReleaseSM,
				store : wCtrlSchemeReleaseStore,
				// tbar : [{
				// text : '新建'
				// }, '-', {
				// text : '启用'
				// }, '-', {
				// text : '停用'
				// }, '-', {
				// text : '删除'
				// }, '-', {
				// text : '打印'
				// }],
				bbar : new Ext.ux.MyToolbar({
							store : wCtrlSchemeReleaseStore,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部",
							enableExpPage : true, // excel仅导出当前页
							expPageText : "当前页"
						})
			});
	// 得到原始的responseText对象
	Ext.apply(wCtrlSchemeReleaseStore.reader, {
				read : function(response) {
					var json = response.responseText;
					var o = Ext.decode(json);
					if (!o) {
						throw {
							message : "JsonReader.read: Json object not found"
						};
					}
					jigouName = o.noToOrgName;
					return this.readRecords(o);
				}
			});
	// 从后台读取出机构的信息
	wCtrlSchemeReleaseStore.load({
			params : {
				start : 0,
				limit : DEFAULT_PAGE_SIZE
			}
		});

	// 任务编制主界面
	var planReleasePanel = new Ext.Panel({
		layout : 'border',
		border : false,
		items : [releaseCondPanel, wCtrlSchemeReleaseGrid]
	});

	// 点击查询按钮以后，通过此方法来执行action
	function findAction() {
		// wCtrlSchemeReleaseStore.proxy.url="./baseapp/PlanCompile!findWCtrlScheme.action";
		// wCtrlSchemeReleaseStore.url="./baseapp/PlanCompile!findWCtrlScheme.action";
		// 必须使用此方法来重设url 上面的两个方法都不行
		wCtrlSchemeReleaseStore.proxy = new Ext.data.HttpProxy({
					url : "./baseapp/planCompile!findWCtrlScheme.action"
				});
		wCtrlSchemeReleaseStore.baseParams = {
			"wctrlScheme.isValid":'1',
			"wctrlScheme.ctrlSchemeType" : Ext.getCmp("releaseSchemeType")
					.getValue()
					|| "",
			"wctrlScheme.ctrlDateStart" : Ext.getCmp("ctrlReleaseDateStart")
					.getRawValue()
					|| "",
			"wctrlScheme.ctrlDateEnd" : Ext.getCmp("ctrlReleaseDateEnd").getRawValue()
					|| ""

		};
		wCtrlSchemeReleaseStore.load({
			params : {
				start : 0,
				limit : DEFAULT_PAGE_SIZE
			}
		});
	}
	renderModel(planReleasePanel, '有序用电任务解除');
	Ext.getBody().on("keydown", function(e) {
			if (!Ext.get("pc_find")) return;
			if (e.getCharCode() == Ext.EventObject.ENTER) {
				findAction();
			}
		});
});

function openReleaseScheme(ctrlSchemeType,ctrlSchemeId,ctrlSchemeName){
	if(ctrlSchemeType =='01' ) {
		staticPeriodExeRelFlag = 1;
		staticPeriodExeRelActivate=1;
		staticPeriodCtrlSchemeName=ctrlSchemeName;
		staticPeriodCtrlSchemeId=ctrlSchemeId;
		openTab("功率时段控", "./baseApp/planPowerConsume/periodControl.jsp");
	} else if (ctrlSchemeType=='02') {
		staticFactoryExeRelFlag = 1;
		staticFactoryExeRelActivate=1;
		staticFactoryCtrlSchemeName=ctrlSchemeName;
		staticFactoryCtrlSchemeId=ctrlSchemeId;
		openTab("功率厂休控", "./baseApp/planPowerConsume/factoryControl.jsp");
	} else if (ctrlSchemeType=='04') {
		staticFloatExeRelFlag = 1;
		staticFloatExeRelActivate=1;
		staticFloatCtrlSchemeName=ctrlSchemeName;
		staticFloatCtrlSchemeId=ctrlSchemeId;
		openTab("当前功率下浮控", "./baseApp/planPowerConsume/floatControl.jsp");
	} else if (ctrlSchemeType =='06') {
		//Ext.getCmp('mainwest').collapse();
		staticEnergyExeRelFlag = 1;
		staticEnergyExeRelActivate=1;
		staticEnergyCtrlSchemeName=ctrlSchemeName;
		staticEnergyCtrlSchemeId=ctrlSchemeId;
		openTab("电量定值控制", "./baseApp/planPowerConsume/energyControl.jsp");
	}
}
