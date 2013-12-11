/**
 * 数据发布
 * longkc
 */
Ext.onReady(function(){

	var dataIssueRadio = new Ext.form.RadioGroup({
				id : 'dataIssueRadio',
				columns : [100, 100, 100],
				autoWidth : true,
				items : [{
				name : 'userType',
				boxLabel : '专变',
				inputValue : '1',
				checked : true,
//				id : 'userTypeZB',
				listeners : {
					check : function(checkbox, checked) {
						if (checked) {
							store.load({
						    	params : {
						    		consType : dataIssueRadio.getValue().inputValue
						    	}
							});
						}
					}
				}
			}, {
				name : 'userType',
				boxLabel : '公变',
				inputValue : '2',
//				id : 'userTypeGB',
				listeners : {
					check : function(checkbox, checked) {
						if (checked) {
							store.load({
						    	params : {
						    		consType : dataIssueRadio.getValue().inputValue
						    	}
							});
						}
					}
				}
			}, {
				name : 'userType',
				boxLabel : '居民',
				inputValue : '5',
//				id : 'userTypeJM',
				listeners : {
					check : function(checkbox, checked) {
							store.load({
						    	params : {
						    		consType : dataIssueRadio.getValue().inputValue
						    	}
							});
						}
				}
			}]
			});
	// 数据发布面板
	var dataIssueTop = new Ext.Panel({
				height : 35,
				region : 'north',
				border : false,
				padding : '5px 0px 0px 20px',
//				width : 800,
				plain : true,
				items : [dataIssueRadio]
			});
	// ======================================================
    function formatDate(value){
    	if (value == null) {
    		return value;
    	}
    	if (Ext.type(value) == 'date') {
    		return value ? new Date(value).dateFormat('Y-m-d H:i:s') : '';
    	} else {
	      	value = value.replace(/-/g, "/")
	        return value ? new Date(value).dateFormat('Y-m-d H:i:s') : '';  		
    	}
    }

    var pubCycleStore = new Ext.data.ArrayStore({
		fields : ['pubCycleValue', 'pubCycleName'],
		data : [[1, "1"],[2, "2"],[3, "3"],[4, "4"],
				[5, "5"],[6, "6"],[7, "7"],[8, "8"],
				[9, "9"],[10, "10"],[11, "11"],[12, "12"],
				[13, "13"],	[14, "14"],	[15, "15"],	[16, "16"],
				[17, "17"],	[18, "18"],	[19, "19"],	[20, "20"],
				[21, "21"],	[22, "22"],	[23, "23"], [24, "24"],
				[25, "25"],	[26, "26"],	[27, "27"],	[28, "28"],
				[29, "29"],	[30, "30"]]
		});
    
    var pubUnitStore = new Ext.data.ArrayStore({
		fields : ['pubUnitValue', 'pubUnitName'],
		data : [[1, "小时"], [2, "日"]]
	});
	
	var flgStore = new Ext.data.ArrayStore({
		fields : ['onFlagValue', 'onFlagName'],
		data : [[1, "启用"], [0, "停用"]]
	});
	var pubSm = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			header : ""
		});
	var cm = new Ext.grid.ColumnModel({
        // specify any defaults for each column
        defaults: {
            sortable: true // columns are not sortable by default           
        },
        columns: [
//        pubSm,
//        new Ext.grid.RowNumberer({
//			// header : '序',
//			renderer : function(v, p, record, rowIndex) {
//				var startRow = 0;
//				if (store && store.lastOptions && store.lastOptions.params) {
//					startRow = store.lastOptions.params.start;
//				}
//				return startRow + rowIndex + 1;
//			}
//		}),
            {
                header: '数据类型',
                dataIndex: 'dataType',
                width: 220,
                // use shorthand alias defined above
                renderer: function(val) {
		        	switch (val) {
						case '1' :
							return "零点抄表数据";
						case '2' :
							return "需量抄表数据";
						case '3' :
							return "负荷数据";
						case '4' :
							return "事件数据";
            }}}, {
                header: '发布基准时间',
                dataIndex: 'pubBaseTime',
                width: 130,
                renderer : formatDate,
                editor: new Ext.form.DateField({
                	 format: 'Y-m-d H:i:s',
                     minValue: '01/01/06',
                     disabledDays: [0, 6],
                     value : new Date()
                })
            }, {
                header: '发布周期',
                dataIndex: 'pubCycle',
                width: 70,
                align: 'right',
                editor: new Ext.form.ComboBox({
                	store : pubCycleStore,
                   	typeAhead : true,
    				triggerAction : 'all',
    				editable : false,
    				displayField : 'pubCycleName',
    				valueField : 'pubCycleValue',
    				mode : 'local',
    				value : '1',
    				selectOnFocus : true
                }),
				renderer : function(val) {
					if (val === null || val === '')
						return '-请选择-';
					var mc = pubCycleStore.query('pubCycleValue', val);
					if (mc != null && mc.length > 0)
						return "<div align = 'left'>"
								+ mc.get(0).get('pubCycleName') + "</div>";
					else
						return '-请选择-';
				}
            }, {
                header: '发布周期单位',
                dataIndex: 'pubCycleUnit',
                width: 95,
                editor: new Ext.form.ComboBox({
                   	store : pubUnitStore,
                   	typeAhead : true,
    				triggerAction : 'all',
    				editable : false,
    				displayField : 'pubUnitName',
    				valueField : 'pubUnitValue',
    				mode : 'local',
    				value : '1',
    				selectOnFocus : true
                }),
                renderer : function(val) {
					if (val === null || val === '')
						return '-请选择-';
					var mc = pubUnitStore.query('pubUnitValue', val);
					if (mc != null && mc.length > 0)
						return "<div align = 'left'>"
								+ mc.get(0).get('pubUnitName') + "</div>";
					else
						return '-请选择-';
				}
            }, {
                header: '是否启用',
                dataIndex: 'isvalid',
                width: 95,
                editor: new Ext.form.ComboBox({
	               	store : flgStore,
                	typeAhead : true,
					triggerAction : 'all',
					editable : false,
					displayField : 'onFlagName',
					valueField : 'onFlagValue',
					mode : 'local',
					value : '1',
					selectOnFocus : true
                }),
				renderer : function(val) {
					if (val === null || val === '')
						return '-请选择-';
					var mc = flgStore.query('onFlagValue', val);
					if (mc != null && mc.length > 0)
						return "<div align = 'left'>"
								+ mc.get(0).get('onFlagName') + "</div>";
					else
						return '-请选择-';
				}
            }
        ]
    });

    var store = new Ext.data.Store({
        // destroy the store if the grid is destroyed
        autoDestroy: true,
        
        url: './advapp/vipconsman/dataPub!loadPubSchema.action',

        reader: new Ext.data.JsonReader({
            idProperty : 'dataType',
            root : 'iPubSchemaList',
            fields: [
                {name: 'dataType'},
                {name: 'consType'},
                {name: 'pubBaseTime'},
                {name: 'pubCycle'},      
                {name: 'pubCycleUnit'},                      
                {name: 'isvalid'},
                {name: 'pubSchemaId'}
            ]
        }),

        sortInfo: {field:'dataType', direction:'ASC'}
    });
	
    //保存编辑的数据发布机制
    function savePubInfo () {
    	//用户类型
    	var userType = dataIssueRadio.getValue().inputValue;
    	//编辑的模板信息
    	var pubRec = pubEditorGridPanel.getSelectionModel().getSelected();
    	var iPubSchemaList = new Array();
    	iPubSchemaList[0] = pubRec.data;
    	if (typeof(pubRec) == 'undefined') {
			Ext.Msg.alert('提示', '请选择要发布的数据类型');
			return;
		}
    	//发布数据项
    	var dateRecs = resultStore.getRange();
    	var iPubDetailList = new Array();
    	for (var j = 0; j < dateRecs.length; j++) {
    		iPubDetailList[j] = dateRecs[j].data;
    	}
    	Ext.Ajax.request({
    		url : './advapp/vipconsman/dataPub!savePubSchema.action',
			params : {
				schemaJsonStr : Ext.encode(iPubSchemaList),
				detailJsonStr : Ext.encode(iPubDetailList)
			},
			success : function(response) {
				var result = Ext.decode(response.responseText)
				if (result.success) {
					Ext.Msg.alert("", "模板保存成功");
				}
			}
    	})
    }
    
    //检索所选类型的数据发布机制
    function searchPubInfo () {
    	//用户类型
    	var userType = dataIssueRadio.getValue().inputValue;
    	//编辑的模板信息
    	var pubRec = pubEditorGridPanel.getSelectionModel().getSelected();
    	//数据类型
    	var dataType = pubRec.get('dataType');
    }
    
    var pubEditorGridPanel = new Ext.grid.EditorGridPanel({
    		region : 'center',
			store : store,
			cm : cm,
			sm : pubSm,
			height : 90,
			tbar : [{
				xtype : 'label',
				html : "<font font-weight:bold;>数据发布机制</font>"
			}, {
				xtype : 'tbfill'
			},'-',{
				icon : '',
				text : '保存',
//				iconCls : 'cancel',
				handler : savePubInfo
			},'-',{
				icon : '',
				text : '查询',
//				iconCls : 'cancel',
				handler : searchPubInfo
			}],
//			autoExandColumn : 'taskModel',
			stripeRows : true,
			clicksToEdit : 1,
			autoWidth : true,
			autoScroll : true,
			monitorResize : true
    })
    
//   var pubGridPanel = new Ext.Panel({
//			region:'center',
//			monitorResize : true,
//			layout:'fit',
//			items : [pubEditorGridPanel],
//			border : false
//		});

	var resourceStore = new Ext.data.JsonStore({
				fields : ['itemName', 'seaItemCode'],
				idProperty : 'seaItemCode',
				root : 'itemCodeList'
			});	
			
	var resourceCm = new Ext.grid.ColumnModel({
		 defaults: {
            sortable: true // columns are not sortable by default           
        },
        columns: [
//        pubSm,
            {
                header: '数据项编码',
                dataIndex: 'itemName',
                width: 220
             },
             {
                header: '采集系统编码',
                dataIndex: 'seaItemCode',
                width: 220
             }]
	})
	var resoureGridPanel = new Ext.grid.GridPanel({
		title : '可选数据项',
		columnWidth : .45,
		height : 300,
		store : resourceStore,
		cm : resourceCm,
		stripeRows : true,
		viewConfig : {
			forceFit : true
		}
	})

	var resultStore = new Ext.data.JsonStore({
				fields : ['itemName', 'seaItemCode'],
				idProperty : 'seaItemCode',
				root : 'iPubDetailList'
			});	
			
	var resultCm = new Ext.grid.ColumnModel({
		 defaults: {
            sortable: true // columns are not sortable by default           
        },
        columns: [
            {
                header: '数据项编码',
                dataIndex: 'itemName',
                width: 220
             },
             {
                header: '采集系统编码',
                dataIndex: 'seaItemCode',
                width: 220
             }]
	})
	var resultGridPanel = new Ext.grid.GridPanel({
		columnWidth : .45,
		height : 300,
		title : '发布数据项',
		store : resultStore,
		stripeRows : true,
		viewConfig : {
			forceFit : true
		},
		cm : resultCm
	})

	//支持双击数据项时将数据项添加到发布数据项中
	resoureGridPanel.on('rowdblclick', function (grid, rowIndex) {
		var rec = grid.getSelectionModel().getSelected();
		resourceStore.remove(rec);
		resultStore.add(rec);
	})

	//支持双击数据项时将数据从发布数据项中移除
	resultGridPanel.on('rowdblclick', function (grid, rowIndex) {
		var rec = grid.getSelectionModel().getSelected();
		resultStore.remove(rec);
		resourceStore.add(rec);
	})
	
	//选中该数据行时，显示相关信息
	pubEditorGridPanel.on('rowclick', function (grid, rowIndex) {
		var rec = grid.getSelectionModel().getSelected();
		loadData(rec);
	})
	
	function loadData(rec) {
		Ext.Ajax.request({
			params : {
				dataType : rec.get('dataType'),
				consType : rec.get('consType')
			},
			url : './advapp/vipconsman/dataPub!queryDataItem.action',
			success : function(response) {
				var result = Ext.decode(response.responseText);
				if (result.itemCodeList == null || result.itemCodeList.length == 0) {
					resoureGridPanel.getStore().removeAll();
//					Ext.Msg.alert("", "该数据类型不存在数据项");
//					return;
				} else {
					resourceStore.loadData(result);
				}
//				if (result.iPubSchemaList != null && result.iPubSchemaList.length != 0) {
//					Ext.each(result.iPubSchemaList, function (val, idx) {
//						var rec = store.getById(val.dataType);
//						rec.set('pubTime',val.pubBaseTime);
//						rec.set('pubCycle',val.pubCycle);
//						rec.set('pubUnit',val.pubCycleUnit);
//						rec.set('onFlag',val.booleanisvalid);
//						rec.set('pubSchemaId', val.pubSchemaId);
//						rec.commit();
//					})
//				}
				if (result.iPubDetailList == null || result.iPubDetailList.length == 0) {
					resultGridPanel.getStore().removeAll();
				} else {
					resultStore.loadData(result);
					//从源数据集合中删除已发布的数据集合
					Ext.each(resultStore.getRange(), function(v, index) {
						resourceStore.remove(resourceStore.getById(v.data.seaItemCode));
					});
				}								
			}
		})
	}
	
	//增加按钮添加数据项
	function addDataItem() {
		var rec = resoureGridPanel.getSelectionModel().getSelected();
		if (typeof(rec) == 'undefined') {
			Ext.Msg.alert('提示', '请选择要增加的数据项');
			return;
		}
		resourceStore.remove(rec);
		resultStore.add(rec);
	}
	//删除按钮删除数据项
	function removeDataItem() {
		var rec = resultGridPanel.getSelectionModel().getSelected();
		if (typeof(rec) == 'undefined') {
			Ext.Msg.alert('提示', '请选择要删除的数据项');
			return;
		}
		resultStore.remove(rec);
		resourceStore.add(rec);		
	}
	var buttonPanel = new Ext.Panel({
			columnWidth : .1,
//			border : false,
			layout : 'table',
			layoutConfig : {
				columns : 1
			},
			bodyStyle : 'padding: 160px 0px 0px 0px',
			height : 300,
			items : [{
						layout : 'form',
						border : false,
						autoWidth : true,
						bodyStyle : 'padding: 0px 10px 10px 10px',
						items : [{
									xtype : 'button',
									text : '增加',
									width : 50,
									handler : addDataItem
								}]
					}, {
						layout : 'form',
						border : false,
						autoWidth : true,
						bodyStyle : 'padding: 0px 10px 10px 10px',
						items : [{
									xtype : 'button',
									text : '删除',
									width : 50,
									handler : removeDataItem
								}]
					}]
		})
	
	var southPanel = new Ext.Panel({
//					title : '发布数据明细',
//					layout : 'form',
					region : 'south',
					height : 300,
					layout : 'column',
					border : false,
					bodyStyle : 'padding:0px 10px 0px 0px',
					items : [resoureGridPanel,
								buttonPanel,
								new Ext.Panel({
									columnWidth : .45,
									autoScroll : true,
									border : false,
									items : [resultGridPanel]
								})
								
							]
				})
	
	var centerPanel = new Ext.Panel({
		bodyStyle:'padding:5px 5px 5px 5px',
		layout: 'border' ,
		region : 'center',
		items: [dataIssueTop,pubEditorGridPanel],
		border : false
	});
	
	var pubPanel = new Ext.Panel({
		bodyStyle:'padding:5px 5px 5px 5px',
		layout: 'border' ,
		items: [centerPanel, southPanel],
		border : false
	});
	renderModel(pubPanel,'数据发布模板编制');

	store.load({
    	params : {
    		consType : dataIssueRadio.getValue().inputValue
    	}
    });
});