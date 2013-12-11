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
						// convert:function(v){
						// return v.ctrlSchemeNo;
						// }
					}, {
					name : "text",
					mapping : "id.ctrlSchemeType"
						// convert:function(v){
						// return v.ctrlSchemeType;
						// }
					}],
				idProperty : "id.ctrlSchemeNo",
				root : "schemeTypes"
			});
	// 名称设为ctrlSchemeType
	// 控制类别combox
	var ctrlTypeCombo = new Ext.form.ComboBox({
				fieldLabel : '功控类型',
				width : 200,
				id : "ctrlSchemeType",
				// typeAhead : true,
				mode : "remote",
				forceSelection : true,
				triggerAction : 'all',
				selectOnFocus : true,
				store : ctrlTypeJson,
				emptyText : '请选择功控类型',
				displayField : 'text',
				valueField : 'value'
			});*/

	var queryCondPanel2 = new Ext.Panel({
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
										id : "ctrlDateStart",
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
										id : "ctrlDateEnd",
										format : 'Y-m-d',
//										vtype : "daterange",
//										startDateField : 'ctrlDateStart'
										value:new Date().add(Date.DAY,30)
									}]
						}]
			});
			
	var queryButton = new Ext.Button({
		text : '查询',
		id : "pc_find",
		handler : function() {
			findAction();
		}
	});
	
	var disableSchemeButton = new Ext.Button({
		text : "启/停方案",
		handler : function() {
			// 用来记录要删除的id
			var ids = [];
			var selects = wCtrlSchemeGrid.getSelectionModel()
					.getSelections();
			// 出现删除提示
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
					wCtrlSchemeStore.reload();
				}
			});
		}
	});

	var newSchemeButton = new Ext.Button({
		text : "新建方案",
		handler:function(){
			//--------------------------------方案保存----------------------------------start		    
			var data_ctrlType = [['01', '时段控'], ['02', '厂休控'], ['04', '当前功率下浮控'], ['06', '月电量定值控']];
			var ds_ctrlType = new Ext.data.SimpleStore({
					fields : ['ctrlTypeCode', 'ctrlType'],
					data : data_ctrlType
				});
			
			//控制类型下拉框
			var ctrlTypeCombo = new Ext.form.ComboBox({
				fieldLabel : '功控类型',
				store : ds_ctrlType,
				displayField : 'ctrlType',
	            valueField : 'ctrlTypeCode',
	            typeAhead : true,
				triggerAction : 'all',
				mode : 'local',
				width:255,
				emptyText : '--请选择功控类型--',
				allowBlank:false,
  				blankText : '请选择功控类型',
				forceSelection : true,
				selectOnFocus : true,
				editable:false,
				labelSeparator:''
			});
			
			ctrlTypeCombo.on('select',function(c){
				saveSchemeLabel.setValue(c.getRawValue()+'方案');
			});
			
			
			//开始日期
			var saveSchemeStartDate = new Ext.form.DateField({
				fieldLabel : '限电日从',
				name : 'saveSchemeStartDate',
				width:108,
				format:'Y-m-d',
			    value : new Date(),
			    labelSeparator:'',
			    allowBlank:false,
					blankText : '请选择开始日期'
		    });
	
		    //结束日期
		    var saveSchemeEndDate = new Ext.form.DateField({
				fieldLabel : '到',
				name : 'saveSchemeEndDate',
				width:108,
				format:'Y-m-d',
			    value : new Date().add(Date.DAY,30),
			    labelSeparator:'',
			    allowBlank:false,
					blankText : '请选择结束日期'
		    });
			
		    //限电类型Store
			var limitTypeStore = new Ext.data.JsonStore({
			    url : "./baseapp/energyControl!loadLimitType.action",
			    fields : [ 'limitType', 'limitTypeName' ],
			    root : "limitTypeList"
		    });
	
			//限电类型下拉框
			var saveSchemeLimitType = new Ext.form.ComboBox({
				fieldLabel : '限电类型',
				store : limitTypeStore,
				displayField : 'limitTypeName',
		        valueField : 'limitType',
				bodyStyle: 'padding:10px;',
				triggerAction : 'all',
				mode : 'remote',
				emptyText : '--请选择类型--',
				allowBlank:false,
				blankText : '请选择限电类型',
				selectOnFocus : true,
				editable:false,
				width:100,
				labelSeparator:''
			});
			
			
			var saveSchemeLabel = new Ext.form.TextField({
			        fieldLabel:'方案名称',
		   	        readOnly:true,
		   	        allowBlank:false,
		   	        labelSeparator:'',
		            anchor:'98%' 
			});
			
			//方案名
			var saveSchemeName = new Ext.form.TextField({
		            width:145,
		            emptyText : '请输入方案名称',
				    allowBlank:false,
				    blankText : '请输入方案名称'
			});
			
		    var saveSchemeRemark =new Ext.form.TextArea({
		    	    fieldLabel:'备注',
		    		width:255,
		    		height:70
		    });
		    
		    var saveCtrlLoad=new Ext.form.NumberField({
		    		fieldLabel:'调控负荷(kW)',
		    		width:62,
		    		allowNegative:false,
		    		decimalPrecision:3,
		    		minValue:1,
		    		maxValue:999999,
				    allowBlank:false,
				    blankText : '请输入调控负荷'
		    });
		    
			//定义另存为方案Card组件
			var saveSchemePanel=new Ext.Panel({
		        height:170,
		        border : false,
		        layout:'form',
		        buttonAlign:'center',
		        buttons:[{
		     	    text:'确定',
		     	    handler:function(){
		     	    	if("" == ctrlTypeCombo.getValue()){
				            Ext.MessageBox.alert("提示","请选择功控类型！"); 
				            return;
				        }
		     	    	if("" == saveSchemeName.getValue().trim() || 0 >=  saveSchemeName.getValue().trim().length){
				            Ext.MessageBox.alert("提示","请输入方案名！"); 
			 	            return;
			            }
			            if("" == saveSchemeStartDate.getValue()){
				            Ext.MessageBox.alert("提示","请输入开始日期！"); 
			 	            return;
			            }
			            if("" == saveSchemeEndDate.getValue()){
				            Ext.MessageBox.alert("提示","请输入结束日期！"); 
			 	            return;
			            }
			            if(saveSchemeStartDate.getValue() > saveSchemeEndDate.getValue()){
			                Ext.MessageBox.alert("提示","开始日期应早于结束日期！"); 
			 	            return;
			            }
			            if("" == saveSchemeLimitType.getValue()){
			    	        Ext.MessageBox.alert("提示","请选择限电类型！"); 
			 	            return;
			            }
			            if("" == saveCtrlLoad.getValue()&&"0" != saveCtrlLoad.getValue()){
			    	        Ext.MessageBox.alert("提示","请输入调控负荷！"); 
			 	            return;
			            }
			            if(0 >= saveCtrlLoad.getValue() || 999999 <= saveCtrlLoad.getValue()){
					        Ext.MessageBox.alert("提示","调控负荷范围必须在1-999999之间！");
					    	return;
					    }
						saveScheme.setDisabled(true);
						Ext.getBody().mask("保存中...");
						Ext.Ajax.request({
							url : './baseapp/planCompile!newCtrlScheme.action',
							params : {
								ctrlSchemeName:saveSchemeLabel.getValue()+'-'+saveSchemeName.getValue().trim(),
								ctrlType:ctrlTypeCombo.getValue(),
								limitType: saveSchemeLimitType.getValue(),
								newStartDate : saveSchemeStartDate.getValue(),
								newEndDate: saveSchemeEndDate.getValue(),
								ctrlLoad:saveCtrlLoad.getValue(),
								schemeRemark:saveSchemeRemark.getValue().trim()
							},
							success : function(response) {	
				     	    		var result = Ext.decode(response.responseText);
				     	    		if(null!=result.FLAG){
			                            if(result.FLAG==0){
			                            	Ext.getBody().unmask();
			                                Ext.MessageBox.alert("提示","该方案名已被使用！",function(btn){
			                               		saveScheme.setDisabled(false);
			                                	});
			                                return;
			                            }
			                         	else if(result.FLAG==1){
			                         		wCtrlSchemeStore.reload();
			                         		Ext.getBody().unmask();
											Ext.MessageBox.alert("提示","保存成功！");
			                            }
				     	    		}
				     	    		saveScheme.close();
		                	}
						});	
		     	    }
		   	    },{
		     	    text:'退出',
		     	    handler:function(){
		     	        saveScheme.close();	
		     	    }
		   	    }],
		        items:[{
		   	           layout:'form',
		   	           border : false,
		   	           bodyStyle : 'padding:15px 0px 0px 15px',
		   	           labelAlign: "right",
		   	           labelWidth:70,
		   	           items:[ctrlTypeCombo] 
		        	},{
		        	layout:'column',
		   	        border : false,
		   	        items:[{
		   	            columnWidth:.52,
		   	            layout:'form',
		   	            border : false,
		   	            bodyStyle : 'padding:5px 0px 0px 15px',
		   	            labelAlign: "right",
		   	            labelWidth:70,
		   	            items:[saveSchemeLabel] 
		   	        },{
		   	            columnWidth:.48,
		   	            layout:'form',
		   	            border : false,
		   	            bodyStyle : 'padding:5px 0px 0px 0px',
		   	            hideLabels:true,
		   	            items:[saveSchemeName] 
		   	        }]
		   	    },{
		   	        layout:'column',
		   	        border : false,
		   	        items:[{
		   	            columnWidth:.50,
		   	            layout:'form',
		   	            border : false,
		   	            bodyStyle : 'padding:5px 0px 0px 15px',
		   	            labelAlign: "right",
		   	            labelWidth:70,
		   	            items:[saveSchemeLimitType] 
		   	        }, {
		   	            columnWidth:.50,
		   	            layout:'form',
		   	            border : false,
		   	            labelWidth:85,
		   	            labelAlign: "right",
		   	            bodyStyle : 'padding:5px 0px 0px 0px',
		   	            items:[saveCtrlLoad] 
		   	        }]
		   	    },{
		   	        layout:'column',
		   	        border : false,
		   	        items:[{
		   	            columnWidth:.55,
		   	            layout:'form',
		   	            border : false,
		   	            bodyStyle : 'padding:5px 0px 0px 15px',
		   	            labelAlign: "right",
		   	            labelWidth:70,
		   	            items:[saveSchemeStartDate] 
		   	        }, {
		   	            columnWidth:.45,
		   	            layout:'form',
		   	            border : false,
		   	            labelWidth:20,
		   	            labelAlign: "right",
		   	            bodyStyle : 'padding:5px 0px 0px 0px',
		   	            items:[saveSchemeEndDate] 
		   	        }]
		   	    },{
		   	            layout:'form',
		   	            border : false,
		   	            bodyStyle : 'padding:5px 0px 0px 15px',
		   	            labelAlign: "right",
		   	            labelWidth:70,
		   	            items:[saveSchemeRemark]
		   	    }]
		    });
			
	        var saveSchemeFitPanel =new Ext.Panel({
	        	 border : false,
	        	 layout:'fit',
	        	 items:[saveSchemePanel]
	        });
	        
		    //保存方案跳出窗口
		    var saveScheme = new Ext.Window({
		        name:'bcfa',
		        modal:true,
		        height:290,
		        width:400,
		        resizable:false,
		        layout:'fit',	        
		        title:'保存方案',
		        closeAction:'hide',  
		        items:[saveSchemeFitPanel]
		    }); 
		    limitTypeStore.load();
			saveScheme.show();
		}
	});
	
	var queryCondPanel1 = new Ext.Panel({
		border : false,
		bodyStyle : 'padding:10px 0px',
		layout : 'column',
		buttonAlign : 'center',
		items : [{
					columnWidth : .5,
					border : false,
					labelSeparator : ' ',
					labelAlign : 'right',
					layout : 'form',
					items : [queryCondPanel2]
				},{
					columnWidth : .12,
					border : false,
					labelSeparator : ' ',
					labelAlign : 'right',
					layout : 'form',
					items : [queryButton]
				},{
					columnWidth : .12,
					border : false,
					labelSeparator : ' ',
					labelAlign : 'right',
					layout : 'form',
					items : [disableSchemeButton]
				},{
					columnWidth : .12,
					border : false,
					layout : 'form',
					items : [newSchemeButton]
				}]
	});

	// 查询条件
	var queryCondPanel = new Ext.Panel({
				region : 'north',
				height : 45,
				border : false,
				items : [queryCondPanel1]
			});

	//var wCtrlSchemeSM = new Ext.grid.CheckboxSelectionModel();
	var wCtrlSchemeRowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(wCtrlSchemeStore && wCtrlSchemeStore.lastOptions && wCtrlSchemeStore.lastOptions.params){
				startRow = wCtrlSchemeStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	// 任务列表 ColumnModel
	var wCtrlSchemeCM = new Ext.grid.ColumnModel([wCtrlSchemeRowNum,/*wCtrlSchemeSM,*/ {
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
				header : '状态',
				align : 'center',
				dataIndex : 'isValid',
				renderer : function(v) {
					return !!v ? "<font color='green';font-weight:bold>启用</font>" : "<font color='red';font-weight:bold>禁用</font>";
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
			},{
				header : '编辑方案',
				align : 'center',
				dataIndex : 'eidtScheme',
				renderer : function(s,m,rec) {
					var schemeRemark;
					if(null==rec.get('schemeRemark'))
						schemeRemark='';
					else
					    schemeRemark=rec.get('schemeRemark');
					return "<a href='javascript:'onclick='openUpdateScheme(\""
							+ rec.get('ctrlSchemeType') + "\",\""
							+ rec.get('ctrlSchemeId') + "\",\""
							+ rec.get('ctrlSchemeName') + "\",\""
							+ rec.get('limitType') + "\",\""
							+ rec.get('ctrlLoad') + "\",\""
							+ rec.get('ctrlDateStart') + "\",\""
							+ rec.get('ctrlDateEnd') + "\",\""
							+ schemeRemark + "\");'>编辑方案</a>";
				}
			},{
				header : '编辑明细',
				align : 'center',
				dataIndex : 'editSchemeDeal',
				sortable : false,
				renderer : function(s,m,rec) {
					return "<a href='javascript:'onclick='openEditScheme(\""
							+ rec.get('ctrlSchemeType') + "\",\""
							+ rec.get('ctrlSchemeId') + "\",\""
							+ rec.get('ctrlSchemeName') + "\");'>编辑明细</a>";
				}
			}]);

	// 任务列表Store
	var wCtrlSchemeStore = new Ext.data.Store({
				url : './baseapp/planCompile!findWCtrlScheme.action',
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
									name : 'isValid'
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
	var wCtrlSchemeGrid = new Ext.grid.GridPanel({
				region : 'center',
				cm : wCtrlSchemeCM,
				//sm : wCtrlSchemeSM,
				store : wCtrlSchemeStore,
				loadMask:false,
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
							store : wCtrlSchemeStore,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部",
							enableExpPage : true, // excel仅导出当前页
							expPageText : "当前页"
						})
			});
	// 得到原始的responseText对象
	Ext.apply(wCtrlSchemeStore.reader, {
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
	wCtrlSchemeStore.load({
				params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
			});

	// 任务编制主界面
	var planCompilePanel = new Ext.Panel({
			layout : 'border',
			border : false,
			items : [queryCondPanel, wCtrlSchemeGrid]
		});

	// 点击查询按钮以后，通过此方法来执行action
	function findAction() {
		// wCtrlSchemeStore.proxy.url="./baseapp/PlanCompile!findWCtrlScheme.action";
		// wCtrlSchemeStore.url="./baseapp/PlanCompile!findWCtrlScheme.action";
		// 必须使用此方法来重设url 上面的两个方法都不行
		wCtrlSchemeStore.proxy = new Ext.data.HttpProxy({
					url : "./baseapp/planCompile!findWCtrlScheme.action"
				});
		wCtrlSchemeStore.baseParams = {
//			"wctrlScheme.ctrlSchemeType" : Ext.getCmp("ctrlSchemeType")
//					.getValue()
//					|| "",
			"wctrlScheme.ctrlDateStart" : Ext.getCmp("ctrlDateStart")
					.getRawValue()
					|| "",
			"wctrlScheme.ctrlDateEnd" : Ext.getCmp("ctrlDateEnd").getRawValue()
					|| ""

		};
		wCtrlSchemeStore.load({
			params : {
				start : 0,
				limit : DEFAULT_PAGE_SIZE
			}
		});
	}
	renderModel(planCompilePanel, '有序用电任务编制');
	Ext.getBody().on("keydown", function(e) {
			if (!Ext.get("pc_find")) {
				return;
			}
			if (e.getCharCode() == Ext.EventObject.ENTER) {
				findAction();
			}
		});
   //修改方案信息
   openUpdateScheme=function(ctrlSchemeType,ctrlSchemeId,ctrlSchemeName,limitType,
						ctrlLoad,ctrlDateStart,ctrlDateEnd,schemeRemark){
	//--------------------------------方案保存----------------------------------start		    
		
		var ctrlTypeField = new Ext.form.TextField({
	        fieldLabel:'功控类型',
   	        disabled:true,
   	        labelSeparator:'',
            width:255
		});
		
		//开始日期
		var updateSchemeStartDate = new Ext.form.DateField({
			fieldLabel : '限电日从',
			name : 'saveSchemeStartDate',
			width:108,
			format:'Y-m-d',
		    value : new Date(),
		    labelSeparator:'',
		    allowBlank:false,
				blankText : '请选择开始日期'
	    });
	
	    //结束日期
	    var updateSchemeEndDate = new Ext.form.DateField({
			fieldLabel : '到',
			name : 'saveSchemeEndDate',
			width:108,
			format:'Y-m-d',
		    value : new Date().add(Date.DAY,30),
		    labelSeparator:'',
		    allowBlank:false,
			blankText : '请选择结束日期'
	    });
		
	    //限电类型Store
		var us_limitTypeStore = new Ext.data.JsonStore({
		    url : "./baseapp/energyControl!loadLimitType.action",
		    fields : [ 'limitType', 'limitTypeName' ],
		    root : "limitTypeList"
	    });
	
		//限电类型下拉框
		var updateSchemeLimitType = new Ext.form.ComboBox({
			fieldLabel : '限电类型',
			store : us_limitTypeStore,
			displayField : 'limitTypeName',
	        valueField : 'limitType',
			bodyStyle: 'padding:10px;',
			triggerAction : 'all',
			mode : 'remote',
			emptyText : '--请选择类型--',
			allowBlank:false,
			blankText : '请选择限电类型',
			selectOnFocus : true,
			editable:false,
			width:100,
			labelSeparator:''
		});
		
		
		var updateSchemeLabel = new Ext.form.TextField({
	        fieldLabel:'方案名称',
   	        readOnly:true,
   	        allowBlank:false,
   	        labelSeparator:'',
            anchor:'98%' 
		});
		
		//方案名
		var updateSchemeName = new Ext.form.TextField({
            width:145,
            emptyText : '请输入方案名称',
		    allowBlank:false,
		    blankText : '请输入方案名称'
		});
		
	    var updateSchemeRemark =new Ext.form.TextArea({
    	    fieldLabel:'备注',
    		width:255,
    		height:70
	    });
	    
	    var updateCtrlLoad=new Ext.form.NumberField({
    		fieldLabel:'调控负荷(kW)',
    		width:62,
    		allowNegative:false,
    		decimalPrecision:3,
    		minValue:1,
    		maxValue:999999,
		    allowBlank:false,
		    blankText : '请输入调控负荷'
	    });
	    
		//定义另存为方案Card组件
		var updateSchemePanel=new Ext.Panel({
	        height:170,
	        border : false,
	        layout:'form',
	        buttonAlign:'center',
	        buttons:[{
	     	    text:'确定',
	     	    handler:function(){
	     	    	if("" == updateSchemeName.getValue().trim() || 0 >=  updateSchemeName.getValue().trim().length){
			            Ext.MessageBox.alert("提示","请输入方案名！"); 
		 	            return;
		            }
		            if("" == updateSchemeStartDate.getValue()){
			            Ext.MessageBox.alert("提示","请输入开始日期！"); 
		 	            return;
		            }
		            if("" == updateSchemeEndDate.getValue()){
			            Ext.MessageBox.alert("提示","请输入结束日期！"); 
		 	            return;
		            }
		            if(updateSchemeStartDate.getValue() > updateSchemeEndDate.getValue()){
		                Ext.MessageBox.alert("提示","开始日期应早于结束日期！"); 
		 	            return;
		            }
		            if("" == updateSchemeLimitType.getValue()){
		    	        Ext.MessageBox.alert("提示","请选择限电类型！"); 
		 	            return;
		            }
		            if("" == updateCtrlLoad.getValue()&&"0" != updateCtrlLoad.getValue()){
		    	        Ext.MessageBox.alert("提示","请输入调控负荷！"); 
		 	            return;
		            }
		            if(0 >= updateCtrlLoad.getValue() || 999999 <= updateCtrlLoad.getValue()){
				        Ext.MessageBox.alert("提示","调控负荷范围必须在1-999999之间！");
				    	return;
				    }
				    var schemeNameFlag=0;
				    if(updateSchemeName.getValue().trim()!=schemeName2)
				    	schemeNameFlag=1;
					updateScheme.setDisabled(true);
					Ext.getBody().mask("保存中...");
					Ext.Ajax.request({
						url : './baseapp/planCompile!updateCtrlScheme.action',
						params : {
							ctrlSchemeId:ctrlSchemeId,
							ctrlSchemeName:updateSchemeLabel.getValue()+'-'+updateSchemeName.getValue().trim(),
							schemeNameFlag:schemeNameFlag,
							limitType: updateSchemeLimitType.getValue(),
							newStartDate : updateSchemeStartDate.getValue(),
							newEndDate: updateSchemeEndDate.getValue(),
							ctrlLoad:updateCtrlLoad.getValue(),
							schemeRemark:updateSchemeRemark.getValue().trim()
						},
						success : function(response) {	
			     	    		var result = Ext.decode(response.responseText);
			     	    		if(null!=result.FLAG){
		                            if(result.FLAG==0){
		                                Ext.MessageBox.alert("提示","该方案名已被使用！",function(btn){
		                               		updateScheme.setDisabled(false);
		                                	});
		                                return;
		                            }
		                         	else if(result.FLAG==1){
		                         		wCtrlSchemeStore.reload();
										Ext.MessageBox.alert("提示","保存成功！");
		                            }
		                            else{
										Ext.MessageBox.alert("提示","保存失败！");
		                            }  
			     	    		}
			     	    		updateScheme.close();
	                	},
	                	callback:function(){
	                		Ext.getBody().unmask();
	                	}
					});	
	     	    }
	   	    },{
	     	    text:'退出',
	     	    handler:function(){
	     	        updateScheme.close();	
	     	    }
	   	    }],
	        items:[{
	   	           layout:'form',
	   	           border : false,
	   	           bodyStyle : 'padding:15px 0px 0px 15px',
	   	           labelAlign: "right",
	   	           labelWidth:70,
	   	           items:[ctrlTypeField] 
	        	},{
	        	layout:'column',
	   	        border : false,
	   	        items:[{
	   	            columnWidth:.52,
	   	            layout:'form',
	   	            border : false,
	   	            bodyStyle : 'padding:5px 0px 0px 15px',
	   	            labelAlign: "right",
	   	            labelWidth:70,
	   	            items:[updateSchemeLabel] 
	   	        },{
	   	            columnWidth:.48,
	   	            layout:'form',
	   	            border : false,
	   	            bodyStyle : 'padding:5px 0px 0px 0px',
	   	            hideLabels:true,
	   	            items:[updateSchemeName] 
	   	        }]
	   	    },{
	   	        layout:'column',
	   	        border : false,
	   	        items:[{
	   	            columnWidth:.50,
	   	            layout:'form',
	   	            border : false,
	   	            bodyStyle : 'padding:5px 0px 0px 15px',
	   	            labelAlign: "right",
	   	            labelWidth:70,
	   	            items:[updateSchemeLimitType] 
	   	        }, {
	   	            columnWidth:.50,
	   	            layout:'form',
	   	            border : false,
	   	            labelWidth:85,
	   	            labelAlign: "right",
	   	            bodyStyle : 'padding:5px 0px 0px 0px',
	   	            items:[updateCtrlLoad] 
	   	        }]
	   	    },{
	   	        layout:'column',
	   	        border : false,
	   	        items:[{
	   	            columnWidth:.55,
	   	            layout:'form',
	   	            border : false,
	   	            bodyStyle : 'padding:5px 0px 0px 15px',
	   	            labelAlign: "right",
	   	            labelWidth:70,
	   	            items:[updateSchemeStartDate] 
	   	        }, {
	   	            columnWidth:.45,
	   	            layout:'form',
	   	            border : false,
	   	            labelWidth:20,
	   	            labelAlign: "right",
	   	            bodyStyle : 'padding:5px 0px 0px 0px',
	   	            items:[updateSchemeEndDate] 
	   	        }]
	   	    },{
	   	            layout:'form',
	   	            border : false,
	   	            bodyStyle : 'padding:5px 0px 0px 15px',
	   	            labelAlign: "right",
	   	            labelWidth:70,
	   	            items:[updateSchemeRemark]
	   	    }]
	    });
		
	    var updateSchemeFitPanel =new Ext.Panel({
	    	 border : false,
	    	 layout:'fit',
	    	 items:[updateSchemePanel]
	    });
	    
	    //保存方案跳出窗口
	    var updateScheme = new Ext.Window({
	        name:'bcfa',
	        modal:true,
	        height:290,
	        width:400,
	        resizable:false,
	        layout:'fit',	        
	        title:'编辑方案',
	        closeAction:'hide',  
	        items:[updateSchemeFitPanel]
	    }); 
	    
	    us_limitTypeStore.on('load',function(){
	    	 updateSchemeLimitType.setValue(limitType);
	    });
	    
	    us_limitTypeStore.load();
	    
	    //显示方案信息
	    ctrlTypeField.setValue(gongkongType[ctrlSchemeType]);
	    var schemeName1 = ctrlSchemeName.substring(0,ctrlSchemeName.indexOf('-'));
	    var schemeName2 = ctrlSchemeName.substring(ctrlSchemeName.indexOf('-')+1,ctrlSchemeName.length);
	    updateSchemeLabel.setValue(schemeName1);
	    updateSchemeName.setValue(schemeName2);
		updateCtrlLoad.setValue(ctrlLoad);
		updateSchemeStartDate.setValue(ctrlDateStart);
		updateSchemeEndDate.setValue(ctrlDateEnd);
		updateSchemeRemark.setValue(schemeRemark);
		updateScheme.show();
	}
});

//链接到各方案明细编制界面
function openEditScheme(ctrlSchemeType,ctrlSchemeId,ctrlSchemeName){
	//staticCtrlSchemeId = ctrlSchemeId;
	if(ctrlSchemeType =='01') {
	    staticPeriodSchemeActivate=1;
		staticPeriodSchemeId=ctrlSchemeId;
		staticPeriodSchemeName=ctrlSchemeName;
		openTab("时段控方案编制", "./baseApp/planPowerConsume/periodCtrlPlanCompile.jsp");
	}else if(ctrlSchemeType =='02') {
		staticFactorySchemeActivate=1;
		staticFactorySchemeId=ctrlSchemeId;
		staticFactorySchemeName=ctrlSchemeName;
		openTab("厂休控方案编制", "./baseApp/planPowerConsume/factoryCtrlPlanCompile.jsp");
	}else if(ctrlSchemeType =='04') {
		staticFloatSchemeActivate=1;
		staticFloatSchemeId=ctrlSchemeId;
		staticFloatSchemeName=ctrlSchemeName;
		openTab("当前功率下浮控方案编制", "./baseApp/planPowerConsume/floatCtrlPlanCompile.jsp");
	}else if (ctrlSchemeType =='06') {
		staticEnergySchemeActivate=1;
		staticEnergySchemeId=ctrlSchemeId;
		staticEnergySchemeName=ctrlSchemeName;
		openTab("电量定值控方案", "./baseApp/planPowerConsume/energySchemeCompile.jsp");
	}
}
