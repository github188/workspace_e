/*
 * ! Author: Longkongcao Date:11/27/2009 Description: 用户余额查看 Update History:
 * none
 */
Ext.onReady(function() {
	var overFlat = false;
    //执行进度条
	var h_taskTime = function(second, fn) {
		flag = 0;
		overFlat = false;
		var task_s = (second == "") ? 30 : second;
		Ext.MessageBox.show({
					title : '请等待',
					msg : '任务执行中...',
					progressText : '初始化...',
					width : 300,
					progress : true,
					closable : false,
					buttons : {
						"cancel" : "取消"
					},
					fn : function(e) {
						flag = 0;
						if (fn && typeof fn == "function") {
							fn();
						}
						clearInterval(aa);
					}
				});

		var f = function() {
			return function() {
				flag = flag + 1;

				if (flag == task_s + 1 || overFlat) {
					Ext.MessageBox.hide();
					flag = 0;
					if (fn && typeof fn == "function") {
						fn();
					}
					clearInterval(aa);
				} else {
					var i = flag;
					Ext.MessageBox.updateProgress(i / (task_s), i + ' 秒');
				}
			};
		};
		var aa = setInterval(f(), 1000);
	};
	// var dwDataStore = new Ext.data.ArrayStore({
	// fields : ['flgID', 'flgText'],
	// data : [['flgData1', '浦口'], ["flgData2", '雨花台']]
	// })
	//
	// // 供电单位combobox
	// var dwComboBox = new Ext.form.ComboBox({
	// fieldLabel : '供电单位',
	// labelSeparator : '',
	// store : dwDataStore,
	// bodyStyle : 'padding:10px;',
	// triggerAction : 'all',
	// mode : 'local',
	// valueField : 'flgID',
	// displayField : 'flgText',
	// anchor : '90%',
	// emptyText : '请选择供电单位',
	// selectOnFocus : true
	// });

	// var zjzDataStore = new Ext.data.ArrayStore({
	// fields : ['flgID', 'flgText'],
	// data : [['flgData1', '1234'], ["flgData2", '2345']]
	// })
	//
	// // 总加组combobox
	// var zjzComboBox = new Ext.form.ComboBox({
	// fieldLabel : '总加组',
	// store : zjzDataStore,
	// labelSeparator : '',
	// bodyStyle : 'padding:10px;',
	// triggerAction : 'all',
	// mode : 'local',
	// valueField : 'flgID',
	// displayField : 'flgText',
	// anchor : '90%',
	// emptyText : 'Select a state...',
	// selectOnFocus : true
	// });

	// var qzDataStore = new Ext.data.ArrayStore({
	// fields : ['flgID', 'flgText'],
	// data : [['flgData1', '1234'], ["flgData2", '2345']]
	// })
	//
	// // 群组combobox
	// var qzComboBox = new Ext.form.ComboBox({
	// fieldLabel : '群组',
	// store : qzDataStore,
	// labelSeparator : '',
	// bodyStyle : 'padding:10px;',
	// triggerAction : 'all',
	// mode : 'local',
	// valueField : 'flgID',
	// displayField : 'flgText',
	// anchor : '90%',
	// emptyText : '请选择群组',
	// selectOnFocus : true
	// });
	
	//全局变量   记录最近一次成功查询的条件
    var gOrgName;
    var gConsNo;
    var gAppNo;
    var gTerminalAddr;
    var gStartDate;
    var gEndDate;
	
	var orgNameField = new Ext.form.TextField({
		labelSeparator : '',
		fieldLabel : '供电单位',
		emptyText : '请输入供电单位',
		width:120
	});
	
	var consNoField = new Ext.form.TextField({
		labelSeparator : '',
		fieldLabel : '用户编号',
		emptyText : '请输入用户编号',
		width:120
	});
	
	var appNoField = new Ext.form.TextField({
		labelSeparator : '',
		fieldLabel : '购电单号',
		emptyText : '请输入购电单号',
		width:120
	});
	
	var abPanel1 = new Ext.Panel({
		border : false,
		height : 35,
		region : 'north',
		layout : 'table',
		defaults: {height: 35},
		layoutConfig : {
			columns : 3
		},
		//id : 'abformPanel',
		bodyStyle : 'padding:10px 0px 0px 0px;',
		items : [{
			layout : 'form',
			border : false,
			labelAlign : 'right',
			labelWidth : 70,
			width:270,
			items : [orgNameField]
		},{
			layout : 'form',
			border : false,
			labelAlign : 'right',
			labelWidth : 70,
			width:270,
			items : [consNoField]
		},{
			layout : 'form',
			border : false,
			labelAlign : 'right',
			labelWidth : 70,
			width:270,
			items : [appNoField]
		}]
	});
	
	var terminalAddrField = new Ext.form.TextField({
		labelSeparator : '',
		fieldLabel : '终端地址',
		emptyText : '请输入终端地址',
		width:120
	});
	
	//开始日期
	var queryStartDate = new Ext.form.DateField({
		fieldLabel : '购电日期',
		width:100,
		format: 'Y-m-d',
		value : new Date().add(Date.DAY,-7),
	    labelSeparator:'',
	    editable:false
    });

    //结束日期
    var queryEndDate = new Ext.form.DateField({
		fieldLabel : '到',
		width:100,
		format: 'Y-m-d',
	    value : new Date(),
	    labelSeparator:'',
	    editable:false
    });
    
    var queryButton= new Ext.Button({
	    text:'查询',
	    width:70,
	    handler:function(){
	       resultPanel.setActiveTab(0);
   	       var startDate=queryStartDate.getValue().format('Y-m-d');
  		   var endDate=queryEndDate.getValue().format('Y-m-d');
		   if(startDate>endDate){
		        Ext.MessageBox.alert("提示","开始日期不能大于结束日期！");
		        return;
		   }
           queryResultStore.removeAll();
	       queryResultStore.baseParams={
	           orgName:orgNameField.getValue().trim(),
	           consNo:consNoField.getValue().trim(),
	           appNo:appNoField.getValue().trim(),
	           terminalAddr:terminalAddrField.getValue().trim(),
	           startDate:startDate,
	           endDate:endDate
           }; 
           Ext.getBody().mask("正在获取数据...");
           queryResultStore.load({
         		params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				},
				callback:function(options,success,response){
					Ext.getBody().unmask();
					if(success){
						   gOrgName=orgNameField.getValue().trim();
				           gConsNo=consNoField.getValue().trim();
				           gAppNo=appNoField.getValue().trim();
				           gTerminalAddr=terminalAddrField.getValue().trim();
				           gStartDate=startDate;
				           gEndDate=endDate;
					}
				}
	       });
    	}
	});
    
    var fetchButton= new Ext.Button({
	    text:'召测剩余值',
	    width:70,
	    handler:function(){
	    		resultPanel.setActiveTab(0);
	     		if(null == queryResultSm.getSelections() || 0==queryResultSm.getSelections().length){
  		     	    Ext.MessageBox.alert("提示","请选择用户！");
  		     	    return;
  		     	}
  		     	ctrlCheckStaff(fetchUseValue, '');
	     	 }
	});
	
	/*//召测全选终端
	function fetchAllUseValue(){
        queryResultStore.removeAll();
        var taskSecond= Ext.Ajax.timeout/1000;
        var ov = true;
        h_taskTime(taskSecond,function(){
        		ov = false;	
        }); 
        Ext.Ajax.request({
	     		url : './baseapp/useValueQuery!fetchAllUseValue.action',
	            params : {
	     			orgName:orgName,
		            consNo:consNo,
		            appNo:appNo,
		            terminalAddr:terminalAddr,
		            startDate:gStartDate,
		            endDate:gEndDate,
     				taskSecond:taskSecond
	            },
	            success : function(response){
     				if (!ov) {
							return;
						}
				    overFlat = true;
        			Ext.Ajax.timeout=30000;
					var result = Ext.decode(response.responseText);
					if(null!=result.msg&&""!=result.msg){
		 				Ext.MessageBox.alert("提示",result.msg);
		 				overFlat = true;
		 				return;
					}
					if(result.success){
						   queryResultStore.baseParams={
					           orgName:orgName,
					           consNo:consNo,
					           appNo:appNo,
					           terminalAddr:terminalAddr,
					           startDate:gStartDate,
					           endDate:gEndDate
				           }; 
				           Ext.getBody().mask("正在获取数据...");
				           queryResultStore.load({
				         		params : {
									start : 0,
									limit : DEFAULT_PAGE_SIZE
								},
								callback:function(options,success,response){
									Ext.getBody().unmask();
								}
					       });
					}
					gsselectAllcb.setValue(false);
	            }
        });
	}*/
	
	//召测选中终端
	function fetchUseValue(){
		fetchResultStore.removeAll();
		var users = queryResultSm.getSelections();
		var fetchTmnlTotalArray = new Array();
        for(var i = 0; i < users.length; i++){
     		var tmnlTotal = users[i].get('orgNo') + '`' + users[i].get('consNo')
					+ '`' + users[i].get('appNo') + '`'
					+ users[i].get('terminalId') + '`'
					+ users[i].get('meterId') + '`'
					+ users[i].get('tmnlAssetNo') + '`'
					+ users[i].get('totalNo');
     		fetchTmnlTotalArray[i]=tmnlTotal;
        }
		var taskSecond= Ext.Ajax.timeout/1000;
        var ov = true;
        h_taskTime(taskSecond,function(){
        		ov = false;	
        }); 
        Ext.Ajax.request({
	     		url : './baseapp/useValueQuery!fetchUseValue.action',
	            params : {
	     			fetchTmnlTotalList:fetchTmnlTotalArray,
     				taskSecond:taskSecond
     			},
     			success : function(response){
     				if (!ov) {
							return;
						}
				    overFlat = true;
        			Ext.Ajax.timeout=30000;
					var result = Ext.decode(response.responseText);
					if(null!=result.msg&&""!=result.msg){
		 				Ext.MessageBox.alert("提示",result.msg);
		 				overFlat = true;
		 				return;
					}
					var record = new Array();
					for(var i = 0; i < users.length; i++){
	                	record[i] = new Array();
	                	record[i][0] = users[i].get('tmnlAssetNo')+users[i].get('totalNo');
	                	record[i][1] = users[i].get('orgName');
	                	record[i][2] = users[i].get('consNo');
	                	record[i][3] = users[i].get('consName');
	                	record[i][4] = users[i].get('appNo');
	                	record[i][5] = users[i].get('buyFlag');
	                	record[i][6] = users[i].get('terminalAddr');
	                	record[i][7] = users[i].get('totalNo');
	                }
					if(null!=result.fetchResultList && 0< result.fetchResultList.length){
		             	for(var j=0;j<result.fetchResultList.length;j++){
	     					for(var k=0;k < record.length;k++){
	     						if(record[k][0]==result.resultList[j].keyId){
	     							if(record[i][5]=='0')
	     								record[k][8]=result.resultList[j].useValue+'kwh';
	     							else if(record[i][5]=='1')	
	     								record[k][8]=result.resultList[j].useValue+'元';
	     							break;
	     						}
	     					}
	     				 }
		             }
		             fetchResultStore.proxy = new Ext.ux.data.PagingMemoryProxy(record);
	     			 fetchResultStore.load({
								params : {
									start : 0,
									limit : DEFAULT_PAGE_SIZE
								}
					 });
	     			 resultPanel.setActiveTab(1);
	     		     overFlat = true;
	     		     Ext.Ajax.timeout=30000;
 				}
        });
	}
	
	var abPanel2 = new Ext.Panel({	
		border : false,
		height : 35,
		layout : 'table',
		layoutConfig : {
			columns :6
		},
		defaults:{height: 35},
		items:[{
			layout : 'form',
			border : false,
			labelAlign : 'right',
			labelWidth : 70,
			width:240,
			bodyStyle : 'padding:5px 0px 0px 0px;',
			items : [terminalAddrField]
		},{
			layout : 'form',
			border : false,
			labelWidth : 50,
			labelAlign : 'right',
			width : 160,
			bodyStyle : 'padding:5px 0px 0px 0px;',
			items:[queryStartDate]	
		},{
			layout : 'form',
            border : false,
            labelAlign : 'right',
            labelWidth:15,
            width :175,
            bodyStyle : 'padding:5px 0px 0px 0px;',
            items:[queryEndDate]   
        },{
			layout : 'form',
			border : false,
			width : 90,
			bodyStyle : 'padding:5px 0px 0px 0px;',
			items : [queryButton]
		},{
			layout : 'form',
			border : false,
			width : 90,
			bodyStyle : 'padding:5px 0px 0px 0px;',
			items : [fetchButton]
		}]
	});
	
	var abPanel =new Ext.Panel({
		border : false,
		region : 'north',
		height:70,
        layout:'form',
        items:[abPanel1,abPanel2]
	});
	var queryResultSm= new Ext.grid.CheckboxSelectionModel(); 
	var queryResultRowNum = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(queryResultStore && queryResultStore.lastOptions && queryResultStore.lastOptions.params){
				startRow = queryResultStore.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	
	var queryResultCm = new Ext.grid.ColumnModel([  
        queryResultRowNum,  
        queryResultSm,
	    {header : '供电单位', sortable: true, dataIndex : 'orgName', align : 'center',
				renderer : function(value) {
					 if(null!=value){
						var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + value + '">'
								+ value + '</div>';
						return html;
					 }
					 else
						return '';
					 }},
	    {header : '用户编号', sortable: true, dataIndex : 'consNo', align : 'center'},
	    {header : '用户名称', sortable: true, dataIndex : 'consName', align : 'center',hidden:true,
	    		 renderer : function(value){
		   			 	if(null!=value){
							var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + value + '">'
									+ value + '</div>';
							return html;
						}
						else
							return '';
						}},
	    {header : '购电单号', sortable: true, dataIndex : 'appNo', align : 'center'},
	    {header : '终端地址', sortable: true, dataIndex : 'terminalAddr', align : 'center'}, 
	    {header : '总加组号', sortable: true, dataIndex : 'totalNo', align : 'center'}, 
	    {header : '电能表资产号', sortable: true, dataIndex : 'meterAssetNo', align : 'center',
	   			 renderer : function(value) {
	   			 	if(null!=value){
						var html = '<div align = "left" ext:qtitle="电能表资产号" ext:qtip="' + value + '">'
								+ value + '</div>';
						return html;
					}
					else 
					    return '';}},
	    {header : '购电日期', sortable: true, dataIndex : 'buyDate', align : 'center'},
	    {header : '购电值', sortable: true, dataIndex : 'buyValue', align : 'center',
		    renderer: function(s, m, rec){
						if('0'==rec.get('buyValueUnit'))
							return s+'kWh';
						else if('1'==rec.get('buyValueUnit'))
							return s+'元';
						else
							return '';
	  		  		}},
	    {header : '投入日期', sortable: true, dataIndex : 'executeDate', align : 'center'},
	    {header : '剩余值(kWh/元)', sortable: true, dataIndex : 'useValue', align : 'center',
	       renderer: function(s, m, rec){
						if('0'==rec.get('buyFlag'))
							return s+'kWh';
						else if('1'==rec.get('buyFlag'))
							return s+'元';
						else
							return '';
	  		  		}},	    
	    {header : '召测日期', sortable: true, dataIndex : 'opTime', align : 'center',
	    		renderer : function(value) {
	    			if(null!=value){
						var html = '<div align = "center" ext:qtitle="召测日期" ext:qtip="' + value + '">'
								+ value + '</div>';
						return html;
				    }
				    else
				    	return '';
	    			}},
	    {header : '剩余值明细', sortable: true, dataIndex : 'opLink', align : 'center',
	      		renderer: function(s, m, rec){
						return "<a href='javascript:'onclick='useValueDetailQueryShow(\""
							+ rec.get('tmnlAssetNo')
							+ "\",\""
							+ rec.get('totalNo')
							+ "\");'>查看</a>"; 
  		  		}}
	]);
	
	var queryResultStore = new Ext.data.Store({
		url : './baseapp/useValueQuery!useValueQuery.action',
		reader : new Ext.data.JsonReader({
			root : 'useValueResultList',
			idProperty:'tmnlAssetNo', 
			totalProperty : 'totalCount'
			}, [{
					name : 'orgNo'
				},{
					name : 'orgName'
				}, {
					name : 'consNo'
				}, {
					name : 'consName'
				}, {
					name : 'appNo'
				}, {
					name : 'buyFlag'
				},{
					name : 'terminalId'
				}, {
					name : 'tmnlAssetNo'
				}, {
					name : 'terminalAddr'
				}, {
					name : 'totalNo'
				}, {
					name : 'meterId'
				}, {
					name : 'meterAssetNo'
				}, {
					name : 'buyDate'
				}, {
					name : 'buyValue'
				},{
					name : 'buyValueUnit'
				},{
					name : 'executeDate'
				}, {
					name : 'useValue'
				},{
					name : 'opTime'
				}, {
					name : 'opLink'
				}])
	});
	
/*	var gsselectAllcb=new Ext.form.Checkbox({
			boxLabel : '全选',
			name : 'gsselectAllcb',
			checked : false,
			listeners : {
				'check' : function(r, c) {
					if (c) {
						 queryResultSm.selectAll();
						 queryResultSm.lock();
						 queryResultGrid.onDisable();
						 queryResultGrid.getBottomToolbar().disable();
						 
					}else {
						 queryResultSm.unlock();
						 queryResultGrid.onEnable();
						 queryResultGrid.getBottomToolbar().enable();
						 queryResultSm.clearSelections()
					}
				}
			}	
	});*/
	
	var queryResultGrid = new Ext.grid.GridPanel({
		        title:'查询结果',
				store : queryResultStore,
				cm : queryResultCm,
				sm:queryResultSm,
		        stripeRows : true,	
		        autoScroll : true,
				loadMask:false,
				bbar : new Ext.ux.MyToolbar({
					store : queryResultStore,
					enableExpAll : true, // excel导出全部数据
				    expAllText : "全部",
					enableExpPage: true, // excel仅导出当前页
					expPageText : "当前页"
				})
			});
	//--------------------------------------------------------------------------------剩余值召测列表
	var fetchResultRowNum = new Ext.grid.RowNumberer({
			renderer : function(v, p, record, rowIndex) {
				var startRow = 0;
				if(fetchResultStore && fetchResultStore.lastOptions && fetchResultStore.lastOptions.params){
					startRow = fetchResultStore.lastOptions.params.start;
				}
				return startRow + rowIndex + 1;
			}
		});
		
	var fetchResultCm = new Ext.grid.ColumnModel([  
		   fetchResultRowNum,
		   {header:'供电单位',dataIndex:'orgName',sortable:true,resizable:true,align:'center',width:150,
			   renderer : function(value) {
			   		if(null!=value){
						var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + value + '">'
								+ value + '</div>';
						return html;
			   		}
			   		else
			   			return '';
					}},
		   {header:'用户编号',dataIndex:'consNo',sortable:true,resizable:true,align:'center'},
		   {header:'用户名称',dataIndex:'consName',sortable:true,resizable:true,align:'center',width:150,
			  renderer : function(value) {
				  	if(null!=value){
						var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + value + '">'
								+ value + '</div>';
						return html;
				  	}
				  	else
			   			return '';
					}},
		   {header:'购电单号',dataIndex:'appNo',sortable:true,resizable:true,align:'center'},		
		   {header:'终端地址',dataIndex:'terminalAddr',sortable:true,resizable:true,align:'center'},
		   {header:'总加组号',dataIndex:'totalNo',sortable:true,resizable:true,align:'center'},
		   {header:'剩余值(kWh/元)',dataIndex:'useValue',sortable:true,resizable:true,align:'center'},
		   {header:'报文',dataIndex:'message',sortable:true,resizable:true,align:'center',
		    renderer: function(s, m, rec){
					return "<a href='javascript:'onclick='origFrameQryShow(\""+rec.get('consNo')+"\",\""+ rec.get('consName')+"\",\""+ rec.get('terminalAddr')+"\");" + "'>查看</a>"; 
			}}
		 ]);
		 
	 var fetchResultStore = new Ext.data.Store({
	 	 remoteSort : true,
	 	 proxy : new Ext.data.MemoryProxy(),
		 reader : new Ext.data.ArrayReader({
			 idIndex:0
		 }, [
		    {name: 'keyId'},
		    {name: 'orgName'},
		    {name: 'consNo'},
		    {name: 'consName'},
		    {name: 'appNo'},
		    {name: 'buyFlag'},
		    {name: 'terminalAddr'},
		    {name: 'totalNo'},
		    {name: 'useValue'},
		    {name: 'message'}
		   ])	
	});
	
	var fetchResultgrid =new Ext.grid.GridPanel({
		title:'剩余值召测结果',
		border : false,
		store: fetchResultStore ,
		cm: fetchResultCm ,
		stripeRows: true,
	    bbar : new Ext.ux.MyToolbar( {
					store : fetchResultStore, 
					enableExpAll : true, // excel导出全部数据
				    expAllText : "全部",
					enableExpPage: true, // excel仅导出当前页
					expPageText : "当前页"
			})           
	});
	
    var resultPanel=  new Ext.TabPanel({
       region : 'center',
	   activeTab: 0,
	   items:[queryResultGrid,fetchResultgrid]
	});
	
	var abFormPanel = new Ext.Panel({
				border : false,
				layout : 'border',
				items : [abPanel, resultPanel]
			});
	renderModel(abFormPanel, '用户余额查看');
	
	useValueDetailQueryShow= function(tmnlAssetNo, totalNo) {
	    var detailQueryResultRowNum = new Ext.grid.RowNumberer({
			renderer : function(v, p, record, rowIndex) {
				var startRow = 0;
				if(detailQueryResultStore && detailQueryResultStore.lastOptions && detailQueryResultStore.lastOptions.params){
					startRow = detailQueryResultStore.lastOptions.params.start;
				}
				return startRow + rowIndex + 1;
			}
		});
	
		var detailQueryResultCm = new Ext.grid.ColumnModel([  
	        detailQueryResultRowNum,                                         
		     {header : '供电单位', sortable: true, dataIndex : 'orgName', align : 'center',
				renderer : function(value) {
					if(null!=value){
						var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + value + '">'
								+ value + '</div>';
						return html;
					}
					else
						return '';
					}},
		    {header : '用户编号', sortable: true, dataIndex : 'consNo', align : 'center'},
	        {header : '用户名称', sortable: true, dataIndex : 'consName', align : 'center',hidden:true,
	        	 renderer : function(value){
		   			 	if(null!=value){
							var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + value + '">'
									+ value + '</div>';
							return html;
						}
						else
							return '';
						}},
		    {header : '购电单号', sortable: true, dataIndex : 'appNo', align : 'center'},
		    {header : '终端地址', sortable: true, dataIndex : 'terminalAddr', align : 'center'}, 
		    {header : '总加组号', sortable: true, dataIndex : 'totalNo', align : 'center'}, 
		    {header : '电能表资产号', sortable: true, dataIndex : 'meterAssetNo', align : 'center',
		   			 renderer : function(value){
		   			 	if(null!=value){
							var html = '<div align = "left" ext:qtitle="电能表资产号" ext:qtip="' + value + '">'
									+ value + '</div>';
							return html;
						}
						else
							return '';
						}},
		    {header : '购电日期', sortable: true, dataIndex : 'buyDate', align : 'center'},
		    {header : '购电值', sortable: true, dataIndex : 'buyValue', align : 'center',
			        renderer: function(s, m, rec){
					 		if('0'==rec.get('buyValueUnit'))
								return s+'kWh';
							else if('1'==rec.get('buyValueUnit'))
								return s+'元';
							else
								return '';
		  		  		}},
		    {header : '投入日期', sortable: true, dataIndex : 'executeDate', align : 'center'},
		    {header : '剩余值(kWh/元)', sortable: true, dataIndex : 'useValue', align : 'center',
			        renderer: function(s, m, rec){
							if('0'==rec.get('buyFlag'))
								return s+'kWh';
							else if('1'==rec.get('buyFlag'))
								return s+'元';
							else
								return '';
		  		  		}},	    
		    {header : '召测日期', sortable: true, dataIndex : 'opTime', align : 'center',
		    		renderer : function(value) {
		    			if(null!=value){
							var html = '<div align = "center" ext:qtitle="召测日期" ext:qtip="' + value + '">'
									+ value + '</div>';
							return html;
					    }
					    else
					   		return '';
				   		}}
		]);
	
	    var detailQueryResultStore = new Ext.data.Store({
	    	    url : './baseapp/useValueQuery!useValueDetailQuery.action',
			    reader : new Ext.data.JsonReader({
						root : 'useValueResultList',
						totalProperty : 'totalCount'
					}, [{
						name : 'orgName'
					}, {
						name : 'consNo'
					}, {
						name : 'consName'
					}, {
						name : 'appNo'
					}, {
						name : 'buyFlag'
				    }, {
						name : 'terminalAddr'
					}, {
						name : 'totalNo'
					}, {
						name : 'meterAssetNo'
					}, {
						name : 'buyDate'
					}, {
						name : 'buyValue'
					}, {
						name : 'buyValueUnit'
					},{
						name : 'executeDate'
					},{
						name : 'useValue'
					}, {
						name : 'opTime'
					}])
		});
		
	    detailQueryResultStore.removeAll();
	    detailQueryResultStore.baseParams={
		    tmnlAssetNo:tmnlAssetNo,
		    totalNo:totalNo,
		    orgName:gOrgName,
		    consNo:gConsNo,
		    appNo:gAppNo,
		    terminalAddr:gTerminalAddr,
		    startDate:gStartDate,
		    endDate:gEndDate
	    }; 
	    detailQueryResultStore.load({
	  		params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
	    });
	    
		var detailQueryResultGrid = new Ext.grid.GridPanel({
			   region:'center',
			   store : detailQueryResultStore,
		       cm : detailQueryResultCm,
		       stripeRows : true,	
		       autoScroll : true,
		       bbar : new Ext.ux.MyToolbar({
					store : detailQueryResultStore,
					enableExpAll : true, // excel导出全部数据
				    expAllText : "全部",
					enableExpPage: true, // excel仅导出当前页
					expPageText : "当前页"
			   })
		}); 
	
		var  useValueDetailQueryWindow =new Ext.Window({
			layout:'fit',
			title:'剩余值明细',
	        modal:true,
	     	maximizable:true, 
			width:800,
			height:550,
			minWidth:600,
			minHeight:412,
	     	items:[detailQueryResultGrid]
		});
		
	    useValueDetailQueryWindow.show();
	}
	
});

function origFrameQryShow(consNo,consName,tmnlAssetAddr){
	staticConsNo = consNo;
	staticConsName = consName;
	staticTmnlAssetAddr = tmnlAssetAddr;
	openTab("原始报文查询","./baseApp/dataGatherMan/origFrameQry.jsp");
}
	
