
function origFrameQryShow(consNo,consName,tmnlAssetAddr){
	staticConsNo = consNo;
	staticConsName = consName;
	staticTmnlAssetAddr = tmnlAssetAddr;
	openTab("原始报文查询","./baseApp/dataGatherMan/origFrameQry.jsp");
}
Ext.onReady(function(){

	var overFlat = false;

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
	//--------------------用户下发列表grid----------start
	var sm_eliminate= new Ext.grid.CheckboxSelectionModel(); 
	var rowNum_eliminate = new Ext.grid.RowNumberer({
		renderer : function(v, p, record, rowIndex) {
			var startRow = 0;
			if(ds_eliminate && ds_eliminate.lastOptions && ds_eliminate.lastOptions.params){
				startRow = ds_eliminate.lastOptions.params.start;
			}
			return startRow + rowIndex + 1;
		}
	});
	var cm_eliminate = new Ext.grid.ColumnModel([  
	       rowNum_eliminate,
	       sm_eliminate,
		   {header:'供电单位',dataIndex:'orgName',sortable:true,resizable:true,align:'center',width:150,
			    renderer : function(val) {
		    	   	if(null!=val){
						var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
								+ val + '</div>';
						return html;
				     }
				     else
				    	   return '';
		    	   	 }},
		   {header:'用户编号',dataIndex:'consNo',sortable:true,resizable:true,align:'center'},
		   {header:'用户名称',dataIndex:'consName',sortable:true,resizable:true,align:'center',width:150,
			   renderer : function(val) {
					if(null!=val){
						var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
								+ val + '</div>';
						return html;
					}
					else
						return '';
					}},
		   {header:'终端地址',dataIndex:'terminalAddr',sortable:true,resizable:true,align:'center'},
		   {header:'剔除状态',dataIndex:'eliminateStatus',sortable:true,resizable:true,align:'center',width:80,
				renderer:function(value){
    	    	 if('0'==value){
	    	    	 return "未剔除";
	    	     }else if('1'==value){
	    	    	 return "已剔除";
	    	     }else{
	    	    	 return "";
	    	     }}},
    	  {header:'执行状态',dataIndex:'execStatus',sortable:true,resizable:true,align:'center',
    	    	 renderer:function(value){
 			   		if('1'==value){
 			   			return "<font color='green';font-weight:bold>成功</font>";
 			   		}else if('0'==value){
 			   			return "<font color='red';font-weight:bold>失败</font>";
 			   		}else if('-1'==value){
 			   			return  "未执行";
 			   		}else{
 			   		    return "";
 			   		}}},
		   {header:'报文',dataIndex:'message',sortable:true,resizable:true,align:'center',
			   renderer: function(s, m, rec){
			   return "<a href='javascript:'onclick='origFrameQryShow(\""+rec.get('consNo')+"\",\""+ rec.get('consName')+"\",\""+ rec.get('terminalAddr')+"\");" + "'>查看</a>"; 
		   }}
		 ]);
	
	 var userStore= new Ext.data.Store({
		    url:'baseapp/terminaleliminate!generalGridByTree.action',
		    reader : new Ext.data.JsonReader({
					root : 'wTmnlRejectList',
					idProperty:'tmnlAssetNo'
			}, [
		    {name: 'orgNo'},
		    {name: 'orgName'},
		    {name: 'consNo'},
		    {name: 'consName'},
		    {name: 'terminalAddr'},
		    {name: 'tmnlAssetNo'},
		    {name: 'protocolCode'},
            {name: 'isReject'},
		    {name: 'eliminateStatus'},
		    {name: 'execStatus'},
		    {name: 'message'}
		   ])
	});
	function storeToArray(valStore) {
		var data = new Array();
		for (var i = 0; i < valStore.getCount(); i++) {
			data[i] = new Array();
 			data[i][0] = valStore.getAt(i).data.orgNo;
			data[i][1] = valStore.getAt(i).data.orgName;
			data[i][2] = valStore.getAt(i).data.consNo;
			data[i][3] = valStore.getAt(i).data.consName;
			data[i][4] = valStore.getAt(i).data.terminalAddr;
			data[i][5] = valStore.getAt(i).data.tmnlAssetNo;
			data[i][6] = valStore.getAt(i).data.protocolCode;
			data[i][7] = valStore.getAt(i).data.isReject;
			data[i][8] = valStore.getAt(i).data.eliminateStatus;
			data[i][9] = valStore.getAt(i).data.execStatus;
			data[i][10] = valStore.getAt(i).data.message;
		}
		return data;
	}
	var ds_eliminate = new Ext.data.Store({
		remoteSort : true,
		proxy : new Ext.data.MemoryProxy(),
		reader : new Ext.data.ArrayReader({
			idIndex : 5,
			fields : [ 
				{name: 'orgNo'},
			    {name: 'orgName'},
			    {name: 'consNo'},
			    {name: 'consName'},
			    {name: 'terminalAddr'},
			    {name: 'tmnlAssetNo'},
			    {name: 'protocolCode'},
           		{name: 'isReject'},
			    {name: 'eliminateStatus'},
			    {name: 'execStatus'},
			    {name: 'message'}]
			})
	});
	
	// grid解锁
	function unlockGrid() {
		sm_eliminate.unlock();
		grid_eliminate.onEnable();
		grid_eliminate.getBottomToolbar().enable();
	}

	// grid上锁
	function lockGrid() {
		sm_eliminate.lock();
		grid_eliminate.onDisable();
		grid_eliminate.getBottomToolbar().disable();
	}
	var gsselectAllcb=new Ext.form.Checkbox({
		boxLabel : '全选',
		name : 'gsselectAllcb',
		checked : false,
		listeners : {
			'check' : function(r, c) {
				if (c) {
					sm_eliminate.selectAll();
					lockGrid();
				}else {
					unlockGrid();
					sm_eliminate.clearSelections();
				}
			}
		}	
	});
	var grid_eliminate=new Ext.grid.GridPanel({
		region:'center',
		store: ds_eliminate,
		cm: cm_eliminate,
		sm: sm_eliminate,
		stripeRows: true,
		tbar:[ {
				xtype: 'label',
				html : "<font font-weight:bold;>方案执行列表</font>"
             },
			{xtype: 'tbfill'},
			gsselectAllcb,
			'-',
			{
	             xtype:"button",
	             text:"删除选中用户",
	             iconCls: 'cancel',
	             handler : function() {
						if (gsselectAllcb.checked) {
							userStore.removeAll(true);
						} else {
							var recs = sm_eliminate.getSelections();
							for (var i = 0; i < recs.length; i++) {
								userStore.remove(userStore
										.getById(recs[i].data.tmnlAssetNo));
							}
						}
						ds_eliminate.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
						ds_eliminate.load({
									params : {
										start : 0,
										limit : DEFAULT_PAGE_SIZE
									}
						});
				}
             },
             '-',
             {
                 xtype:"button",
                 text:"加入群组",
                 iconCls: 'plus',
                 handler:function(){
						var groupTmnlArray = new Array();
						if (gsselectAllcb.checked) {
							for (var i = 0; i < userStore.getCount(); i++) {
								var tmnl = userStore.getAt(i).get('consNo')
										+ '`'
										+ userStore.getAt(i).get('tmnlAssetNo');
								groupTmnlArray[i] = tmnl;
							}
						} else {
							var recs = sm_eliminate.getSelections();
							for (var i = 0; i < recs.length; i++) {
								var tmnl = recs[i].get('consNo') + '`'
										+ recs[i].get('tmnlAssetNo');
								groupTmnlArray[i] = tmnl;
							}
						}
						if (groupTmnlArray.length == 0) {
							Ext.Msg.alert('提示', '请选择要加入群组的用户');
						} else {
							saveOrUpdateGroupWindowShow(groupTmnlArray);
							
						}
						if (gsselectAllcb.checked) {
							gsselectAllcb.setValue(false);
							sm_eliminate.selectAll();
						}
	                 }
            },
            '-',
            {
                 xtype:"button",
                 text:"删除成功用户",
                 iconCls: 'minus',
                 handler:function(){
                 		for (var i = userStore.getCount()-1; i >=0; i--) {
                 			if("1"==userStore.getAt(i).get('execStatus'))
								userStore.removeAt(i);
						}
						ds_eliminate.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
						ds_eliminate.load({
									params : {
										start : 0,
										limit : DEFAULT_PAGE_SIZE
									}
						});
                 }
       	 }],
   	   bbar : new Ext.ux.MyToolbar( {
				store : ds_eliminate,
				allStore: userStore,
				enableExpAll : true, // excel导出全部数据
			    expAllText : "全部",
				enableExpPage: true, // excel仅导出当前页
				expPageText : "当前页"
		})  
	});
	//--------------------用户下发列表grid----------end
	
	var jctrRadio = new Ext.form.RadioGroup({
		fieldLabel:'终端剔除',
        width :200,
        items : [new Ext.form.Radio({
                    boxLabel : '解除',
                    name : 'jctr',
                    checked : true,
                    inputValue : '0'
                }),
                 new Ext.form.Radio({
                    boxLabel : '投入',
                    name : 'jctr',
                    inputValue : '1'
                })]
    });
	
	var panel_zdtc= new Ext.Panel({
		height:40,
		region:'north',
		border : false,
		layout:'table',
	  	layoutConfig : {
			 columns :4
		},
		defaults: {height: 35},
		items:[{
            border : false,
            layout:'form',
            width:475,
            labelAlign: 'left',
  	        labelWidth:100,
  	        labelSeparator : '',
  	        bodyStyle : 'padding:10px 0px 0px 20px',
	        items:[jctrRadio]
		},{
    	    border : false,
	    	layout:'form',
	    	width:100,
	    	bodyStyle : 'padding:10px 0px 0px 0px',
    	    items:[{
			     xtype:'button',
	  		     text:'下发'	,
	  		     width:80,
  		         handler:function(){
  		          	 var recs = sm_eliminate.getSelections();
			         if(gsselectAllcb.checked ){
						if(0==userStore.getCount()){
						  Ext.MessageBox.alert("提示","请选择用户！");
		  		     	    return;
						}
			     	 }else{
			     		if(null == recs || 0==recs.length){
		  		     	    Ext.MessageBox.alert("提示","请选择用户！");
		  		     	    return;
		  		     	}
			     	 }
			         if(null==jctrRadio.getValue()){
			             Ext.MessageBox.alert("提示","请选择投入或解除！");
			             return; 
			         }
			         ctrlCheckStaff(eliminateSend, 'ctrl');
  		         }
    	    }]
	    },{
	    	border : false,
	    	layout:'form',
	    	width:100,
	    	bodyStyle : 'padding:10px 0px 0px 0px',
	    	items : [{
			     xtype:'button',
			     text:'方案加载'	,
			     width:80,
                 handler:schemeLoadShow
	  		 }]
	    },{
		     border : false,
	  		 layout:'form',
	  		 width:100,
	  		 bodyStyle : 'padding:10px 0px 0px 0px',
			 items : [{
			     xtype:'button',
	  		     text:'保存为方案',
	  		     width:80,
                 handler:saveSchemeShow
	     	}]
	    }]
	});
	//--------------------------------------保存方案弹出窗口函数start
	function  saveSchemeShow(){
			 var recs = sm_eliminate.getSelections();
	         if(gsselectAllcb.checked ){
				if(0==userStore.getCount()){
				  Ext.MessageBox.alert("提示","请选择用户！");
  		     	    return;
				}
	     	 }else{
	     		if(null == recs || 0==recs.length){
  		     	    Ext.MessageBox.alert("提示","请选择用户！");
  		     	    return;
  		     	}
	     	 }
	         if(null==jctrRadio.getValue()){
	             Ext.MessageBox.alert("提示","请选择投入或解除！");
	             return; 
	         }
	         var eliminateTmnlArray = new Array();
            if (gsselectAllcb.checked){
				for (var i = 0; i < userStore.getCount(); i++) {
					var tmnl = userStore.getAt(i).get('orgNo')+'`'+userStore.getAt(i).get('consNo')+'`'+userStore.getAt(i).get('tmnlAssetNo');
					eliminateTmnlArray[i]=tmnl;
				}
			}
			else{
	 	    	for(var i = 0; i < recs.length; i++){
		     		var tmnl = recs[i].get('orgNo')+'`'+recs[i].get('consNo')+'`'+recs[i].get('tmnlAssetNo');
		     		eliminateTmnlArray[i]=tmnl;
	      		}
			}
		    //保存方案 --start

		    //定义radio选择组
		    var scRadioGroup = new Ext.form.RadioGroup({
		        width : 200,
		        height :20,
		        items : [new Ext.form.Radio({
		            boxLabel : '另存为方案',
		            name : 'sc-radioGroup',
		            inputValue : '0',
		            checked : true,
		            handler:function(checkbox, checked){
				    	if(checked){
				    		eliminateSaveSchemePnl.layout.setActiveItem(0);
				    	}
				    }
		        }), new Ext.form.Radio({
		            boxLabel : '保存方案',
		            name : 'sc-radioGroup',
		            inputValue : '1',
		            handler:function(checkbox, checked){
			    	if(checked){
			    		ds_Scheme1.load();
			    		eliminateSaveSchemePnl.layout.setActiveItem(1);
			    	}
			    }})]
		    });
	        
	        var schemeRedioPanel=new Ext.Panel({
		    	region:'north',
		    	layout:'form',
		        border:false,
		        height:30,
		        bodyStyle : 'padding:10px 0px 0px 40px',
	            hideLabels:true,
		        items:[scRadioGroup]  
		    });
	        
		    //开始日期（另存为方案）
		    var saveSchemeStartDate = new Ext.form.DateField({
		        fieldLabel : '有效期从',
		        name : 'newStartDate',
		        width:108,
		        format:'Y-m-d',
		        value : new Date(),
		        labelSeparator:'',
		        allowBlank:false,
		  		blankText : '请选择开始日期'
		    });

		    //结束日期（另存为方案）
		    var saveSchemeEndDate = new Ext.form.DateField({
		        fieldLabel : '到',
		        name : 'newEndDate',
		        width:108,
		        format:'Y-m-d',
		        value : new Date().add(Date.DAY,30),
		        labelSeparator:'',
		        allowBlank:false,
		  		blankText : '请选择结束日期'
		    });
		    
		    
		    //方案label
		    var saveSchemeLabel = new Ext.form.TextField({
	            fieldLabel:'方案名称',
	            value:'剔除方案',
	            readOnly:true,
	            labelSeparator:'',
	            allowBlank:false,
	            anchor:'98%'
		    });
		    
		    //方案名
		    var saveSchemeName = new Ext.form.TextField({
	            width:145,
	            emptyText : '请输入方案名称',
			    allowBlank:false,
			    blankText : '请输入方案名称'
		    });
		    //方案备注
			var saveSchemeRemark =new Ext.form.TextArea({
		    	    fieldLabel:'备注',
		    		width:255,
		    		height:80
		    });
		    //定义另存为方案card组件
		    var eliminateSaveScheme=new Ext.Panel({
		        border : false,
		        layout:'form',
		        buttonAlign:'center',
		        buttons:[{
		            text:'确定',
		            handler:saveScheme
		        },{
		            text:'退出',
		            handler:function(){
		                w_saveScheme.close(); 
		            }
		        }],
		        items:[{
		            layout:'column',
		            border : false,
		            items:[{
		                columnWidth:.52,
		                layout:'form',
		                border : false,
		                bodyStyle : 'padding:10px 0px 0px 15px',
		                labelAlign: 'right',
		                labelWidth:70,
		                items:[saveSchemeLabel] 
		            }, {
		                columnWidth:.48,
		                layout:'form',
		                border : false,
		                bodyStyle : 'padding:10px 0px 0px 0px',
		                hideLabels:true,
		                items:[saveSchemeName] 
		            }]
		        },{
		            layout:'column',
		            border : false,
		            items:[{
		                columnWidth:.55,
		                layout:'form',
		                border : false,
		                bodyStyle : 'padding:5px 0px 0px 15px',
		                labelAlign: 'right',
		                labelWidth:70,
		                items:[saveSchemeStartDate] 
		            }, {
		                columnWidth:.45,
		                layout:'form',
		                border : false,
		                labelWidth:20,
		                labelAlign: 'right',
		                bodyStyle : 'padding:5px 0px 0px 0px',
		                items:[saveSchemeEndDate] 
		            }]
		        }, {
		                layout:'form',
		                border : false,
		                bodyStyle : 'padding:5px 0px 0px 15px',
		                labelAlign: 'right',
		                labelWidth:70,
		                items:[saveSchemeRemark] 
		        }]
		    });
		    		    
		    //开始日期（保存为方案）
		    var updateSchemeStartDate = new Ext.form.DateField({
		        fieldLabel : '有效期从',
		        name : 'newStartDate',
		        width:108,
		        format:'Y-m-d',
		        value : new Date(),
		        labelSeparator:'',
		        allowBlank:false,
		  		blankText : '请选择开始日期'
		    });

		    //结束日期（保存为方案）
		    var updateSchemeEndDate = new Ext.form.DateField({
		        fieldLabel : '到',
		        name : 'newEndDate',
		        width:108,
		        format:'Y-m-d',
		        value : new Date().add(Date.DAY,30),
		        labelSeparator:'',
		        allowBlank:false,
		    	blankText : '请选择结束日期'
		    });
		    
		    //方案备注
			var updateSchemeRemark =new Ext.form.TextArea({
		    	    fieldLabel:'备注',
		    		width:255,
		    		height:80
		    });
		    //方案名称Store
			var ds_Scheme1 = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
						url:'./baseapp/terminaleliminate!loadScheme.action'
					}),
				reader : new Ext.data.JsonReader({
						root : 'schemeList',
						idProperty: 'ctrlSchemeId'
					}, [{name:'ctrlSchemeId'},
						{name:'ctrlSchemeName'},
						{name:'ctrlSchemeType'},
						{name:'ctrlDateStart'},
						{name:'ctrlDateEnd'},
						{name:'schemeRemark'}])
			});
			
		   //终端剔除方案下拉框
			var updateSchemeName = new Ext.form.ComboBox({
				fieldLabel : '方案名称',
				store : ds_Scheme1,
				displayField : 'ctrlSchemeName',
	            valueField : 'ctrlSchemeId',
	            editable : false,
				bodyStyle: 'padding:10px;',
				triggerAction : 'all',
				mode : 'remote',
				width:255,
				emptyText : '--请选择方案--',
				allowBlank:false,
			    blankText : '请选择方案',
				forceSelection : true,
				selectOnFocus : true,
				labelSeparator:''
			});  
			updateSchemeName.on('select',function(){
			var rec=ds_Scheme1.getById(updateSchemeName.getValue());
					if(null!=rec){
						updateSchemeStartDate.setValue(Date.parseDate(rec.get('ctrlDateStart'),'Y-m-d\\TH:i:s'));
						updateSchemeEndDate.setValue(Date.parseDate(rec.get('ctrlDateEnd'),'Y-m-d\\TH:i:s'));
						updateSchemeRemark.setValue(rec.get('schemeRemark'));
					}
			});
			
		   //定义保存方案card组件
		    var eliminateUpdateScheme=new Ext.Panel({
		        border : false,
		        layout:'form',
		        buttonAlign:'center',
		        buttons:[{
		            text:'确定',
		            handler:updateScheme
		        },{
		            text:'退出',
		            handler:function(){
		                w_saveScheme.close(); 
		            }
		        }],
		        items:[{
		            	layout:'form',
		                border : false,
		                bodyStyle : 'padding:10px 0px 0px 15px',
		                labelAlign: 'right',
		                labelWidth:70,
		                items:[updateSchemeName] 
		        	},{
			            layout:'column',
			            border : false,
			            items:[{
			                columnWidth:.55,
			                layout:'form',
			                border : false,
			                bodyStyle : 'padding:5px 0px 0px 15px',
			                labelAlign: 'right',
			                labelWidth:70,
			                items:[updateSchemeStartDate] 
		            },{
		                columnWidth:.45,
		                layout:'form',
		                border : false,
		                labelWidth:20,
		                labelAlign: 'right',
		                bodyStyle : 'padding:5px 0px 0px 0px',
		                items:[updateSchemeEndDate] 
		            }]
		        },{
		                layout:'form',
		                border : false,
		                bodyStyle : 'padding:5px 0px 0px 15px',
		                labelAlign: 'right',
		                labelWidth:70,
		                items:[updateSchemeRemark] 
	        	}]
		    });
		    
		    var eliminateSaveSchemePnl=new Ext.Panel({
		   	    region:'center',
			   	layout:'card',
		        activeItem : 0,
		        border:false,
		        items:[eliminateSaveScheme, eliminateUpdateScheme]
		   });

		    //定义保存为方案面板组件
		    var saveSchemePnl=new Ext.Panel({
		        layout:'border',
		        border:false,
		        items:[schemeRedioPanel,eliminateSaveSchemePnl]
		    });

		    //保存方案跳出窗口
		    var w_saveScheme = new Ext.Window({
		        name:'w_saveScheme',
		        modal:true,
		        height:260,
		        width:400,
		        resizable:false,
		        layout:'fit',           
		        title:'保存方案',
		        items:[saveSchemePnl]
		    }); 
		    w_saveScheme.show();
		    //保存方案--end	
		    //保存为方案函数
		    function  saveScheme(){
	            if("" == saveSchemeName.getValue().trim() || 0 >=  saveSchemeName.getValue().trim().length){
	                   Ext.MessageBox.alert("提示","请输入方案名！"); 
	                   return false;
	            }
	            if("" ==  saveSchemeStartDate.getValue()){
	                   Ext.MessageBox.alert("提示","请输入开始日期！"); 
	                   return false;
	            }
	            if("" ==  saveSchemeEndDate.getValue()){
	                   Ext.MessageBox.alert("提示","请输入结束日期！"); 
	                   return false;
	            }
	            if( saveSchemeStartDate.getValue() >  saveSchemeEndDate.getValue()){
	                    Ext.MessageBox.alert("提示","开始日期应早于结束日期！"); 
	                    return false;
	            }
	            w_saveScheme.hide();
            	Ext.getBody().mask("保存中...");
                Ext.Ajax.request({
                    url:'./baseapp/terminaleliminate!addEliminateScheme.action',
                    params : {
                       ctrlSchemeName:saveSchemeLabel.getValue()+'-'+saveSchemeName.getValue().trim(),
                       ctrlDateStart:saveSchemeStartDate.getValue(),
                       ctrlDateEnd:saveSchemeEndDate.getValue(),
   					   eliminateTmnlList:eliminateTmnlArray,
                       release:jctrRadio.getValue().getRawValue(),
                       schemeRemark:saveSchemeRemark.getValue().trim()
                    },
                    success : function(response){
         	    		var result = Ext.decode(response.responseText);
         	    		if(null!=result.FLAG){
                            if(result.FLAG==0){
                                Ext.MessageBox.alert("提示","该方案名已被使用！",function(btn){
                                		w_saveScheme.show();
                                });
                                return;
                            }
                         	else if(result.FLAG==1){	
                                if(gsselectAllcb.checked) {
									gsselectAllcb.setValue(false);
									sm_eliminate.selectAll();
								}
								Ext.MessageBox.alert("提示","保存成功！");
                            }
         	    		}
         	    		w_saveScheme.close();
                	},
	            	callback:function(){
	            		Ext.getBody().unmask();
	            	}
                });
		    };
		    function  updateScheme(){
		    	if("" == updateSchemeName.getValue()){
                    Ext.MessageBox.alert("提示","请选择方案！"); 
                    return false;
		    	}
	            if("" == updateSchemeStartDate.getValue()){
	                   Ext.MessageBox.alert("提示","请输入开始日期！"); 
	                   return false;
	            }
	            if("" == updateSchemeEndDate.getValue()){
	                   Ext.MessageBox.alert("提示","请输入结束日期！"); 
	                   return false;
	            }
	            if( updateSchemeStartDate.getValue() >  updateSchemeEndDate.getValue()){
	                    Ext.MessageBox.alert("提示","开始日期应早于结束日期！"); 
	                    return false;
	            }
	            w_saveScheme.hide();
                Ext.getBody().mask("保存中...");
	 	    	Ext.Ajax.request({
	                    url:'./baseapp/terminaleliminate!updateEliminateScheme.action',
	                    params : {
	 	    		 	   schemeId:updateSchemeName.getValue(),
	                       ctrlDateStart:updateSchemeStartDate.getValue(),
	                       ctrlDateEnd:updateSchemeEndDate.getValue(),
	   					   eliminateTmnlList:eliminateTmnlArray,
	                       release:jctrRadio.getValue().getRawValue(),
	                       schemeRemark:updateSchemeRemark.getValue().trim()
	                    },
			 	    	success : function(){
			 	    	   w_saveScheme.close();
                           if(gsselectAllcb.checked) {
								gsselectAllcb.setValue(false);
								sm_eliminate.selectAll();
						   }
						   Ext.MessageBox.alert("提示","保存成功！");
                           return;
		             	},
		            	callback:function(){
		            		Ext.getBody().unmask();
		            	}
	 	    	 });
		    }
	};
	//--------------------------------------保存方案弹出窗口函数end
	
	//--------------------------------------方案加载弹出窗口函数start
	function  schemeLoadShow(){
		   var zpanel1=new Ext.Panel({
		       region:'north',
		       height:50,
		       border : false,
		       layout:'column',
		       items:[
		        {
		         columnWidth:.75,
		         layout:'form',
		         border : false,
		         bodyStyle : 'padding:20px 0px 0px 20px',
		         items:[{
		            xtype:'label',
		            text:'终端剔除方案加载'
		         }] 
		        },{
		         columnWidth:.25,
		         layout:'form',
		         border : false,
		         bodyStyle : 'padding:20px 0px 0px 0px',
		         items:[{
		            xtype:'button',
		            text:'选择',
		            handler:selectScheme 
		         }] 
		        }]
		    });
		   
		    //方案名称Store
			var ds_Scheme = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
						url:'./baseapp/terminaleliminate!loadScheme.action'
					}),
				reader : new Ext.data.JsonReader({
						root : 'schemeList'
					}, [{name:'ctrlSchemeId'},
						{name:'ctrlSchemeName'},
						{name:'ctrlSchemeType'}])
			});
		        
		    var loadSchemeFuncId = new Ext.ux.form.MultiSelect({
		            height:120,
		            width: 220,
		            displayField : 'ctrlSchemeName',
		            valueField : 'ctrlSchemeId',
		            labelSeparator:'',
		            store: ds_Scheme
		    });
		    
		    var zpanel2=new Ext.Panel({
		       border : false,
		       layout:'column',
		       region:'center',
		       items:[
		        {
		           columnWidth:.75,
		            border : false,
		            layout:'fit',
		            hideLabels:true,
		            bodyStyle : 'padding:0px 0px 0px 20px',
		            items:[loadSchemeFuncId] 
		        },{
		         columnWidth:.25,
		         border : false,
		         layout:'form',
		         items:[{
		            xtype:'button',
		            width:80,
		            text:'退出',
		            handler:function(){
		              w_schemeLoad.close();
		            }
		         }] 
		        }]
		    });
		    //保电方案加载窗口
		    var w_schemeLoad = new Ext.Window({
		       name:'w_schemeLoad',
		       modal:true,
		       height:250,
		       width:400,
		       resizable:false,
		       layout:'border',
		       title:'方案加载',
		       items:[zpanel1,zpanel2]
		    }); 	
		    ds_Scheme.load();

            w_schemeLoad.show();
            
            //方案加载函数
            function selectScheme(){
	              var selectionsArray = loadSchemeFuncId.view.getSelectedIndexes();
	              var schemeId;
	              var type = "ctrlScheme";
	              if(0 >= selectionsArray.length){
	                   Ext.MessageBox.alert("提示","请选择方案！");
	                   return;
	              }
	              if(1 < selectionsArray.length){
	                   Ext.MessageBox.alert("提示","只能选择一个方案！");
	                   return;
	              }
	              w_schemeLoad.hide();  
	              schemeId = ds_Scheme.getAt(selectionsArray[selectionsArray.length-1]).get('ctrlSchemeId');
	              userStore.baseParams = {
	                   schemeId:schemeId,
	                   nodeType:type
	              };
	              Ext.getBody().mask("加载中...");
	              userStore.load({
	                    callback: function(records, options, success){
	                    	Ext.getBody().unmask();
	                        if(null != records && 0 < records.length){
	                            if(null==records[0].get("isReject")||""==records[0].get("isReject")){
	                                jctrRadio.setValue(0);
	                            }
	                            else if(false==records[0].get("isReject"))
	                                jctrRadio.setValue(0);
	                            else if(true==records[0].get("isReject"))
	                                jctrRadio.setValue(1);  
	                        }else{
	                        	jctrRadio.setValue(0);
	                        }
	                        w_schemeLoad.close();  
							return;
	                    }
	               });
		    };
	};
	//--------------------------------------方案加载弹出窗口函数end
	function  eliminateSend(){
   	    var recs = sm_eliminate.getSelections();
   	    var eliminateTmnlArray = new Array();	
   	    var release=jctrRadio.getValue().getRawValue();
   	    if("0"==release){
   	    	if (gsselectAllcb.checked){
   	    		for (var i = 0; i < userStore.getCount(); i++){
	     			if("1"==userStore.getAt(i).get('eliminateStatus')){
	     				var tmnl =userStore.getAt(i).get('tmnlAssetNo')+'`'+userStore.getAt(i).get('protocolCode');
		     			eliminateTmnlArray[i]=tmnl;
		     			userStore.getAt(i).set('execStatus',"0");
	     			}
	     			else{
	     				userStore.getAt(i).set('execStatus',"-1");
	     			}
	     		}
   	    	}
   	    	else{
		     	for(var i = 0; i < recs.length; i++){
		     		var rec = userStore.getById(recs[i].get('tmnlAssetNo'));	
		     		if("1"==recs[i].get('eliminateStatus')){
			     		var tmnl = recs[i].get('tmnlAssetNo')+'`'+recs[i].get('protocolCode');
			     		eliminateTmnlArray[i]=tmnl;
			     		if(null!=rec)
			     		  	rec.set('execStatus',"0");
		     		}else{
		     			if(null!=rec)
			     		  	rec.set('execStatus',"-1");
		     		}
		     	}
   	    	}
   	    }else if("1"==release){
   	    	if (gsselectAllcb.checked){
   	    		for (var i = 0; i < userStore.getCount(); i++){
	     			if("0"==userStore.getAt(i).get('eliminateStatus')){
	     				var tmnl =userStore.getAt(i).get('tmnlAssetNo')+'`'+userStore.getAt(i).get('protocolCode');
		     			eliminateTmnlArray[i]=tmnl;
		     			userStore.getAt(i).set('execStatus',"0");
	     			}
	     			else{
	     				userStore.getAt(i).set('execStatus',"-1");
	     			}
	     		}
   	    	}
   	    	else{
	   	    	for(var i = 0; i < recs.length; i++){
		     		var rec = userStore.getById(recs[i].get('tmnlAssetNo'));	
		     		if("0"==recs[i].get('eliminateStatus')){
			     		var tmnl = recs[i].get('tmnlAssetNo')+'`'+recs[i].get('protocolCode');
			     		eliminateTmnlArray[i]=tmnl;
			     		if(null!=rec)
			     		  	rec.set('execStatus',"0");
		     		}else{
		     			if(null!=rec)
			     		  	rec.set('execStatus',"-1");
		     		}
		     	}
   	    	}
   	    }
   	    userStore.commitChanges();
	   	if(0==eliminateTmnlArray.length){
       		ds_eliminate.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
			ds_eliminate.load({
						params : {
							start : ds_eliminate.lastOptions.params.start,
							limit : DEFAULT_PAGE_SIZE
						}
			});
	   		return;
	   	}
	   	var taskSecond=Ext.Ajax.timeout/1000;
	    var ov = true;
        h_taskTime(taskSecond,function(){
       		ov = false;
       	});
        Ext.Ajax.request({
     		url:'./baseapp/terminaleliminate!sendout.action',
     		params : {
				 eliminateTmnlList:eliminateTmnlArray,
     			 release:release,
     			 taskSecond:taskSecond
     		},
   		    success : function(response){
	     		  if (!ov) {
						return;
					}
	              var result = Ext.decode(response.responseText);
	              if(null!=result.cacheAndTmnlStatus&&""!=result.cacheAndTmnlStatus){
		 				Ext.MessageBox.alert("提示",result.cacheAndTmnlStatus);
		 				return;
	 			  }
	              if(null!=result.tmnlExecStatusList||result.tmnlExecStatusList.length > 0){
	              	 for(var j=0;j<result.tmnlExecStatusList.length;j=j+1){
	     				var rec = userStore.getById(result.tmnlExecStatusList[j].keyId);	
	                    if(null!=rec){
		     				 if("1"==result.tmnlExecStatusList[j].execFlag){
		                		 rec.set('eliminateStatus',release);
		 				     	rec.set('execStatus',"1");//修改页面选中记录的状态
	                          }
		     		    }
		             }
	              	 userStore.commitChanges();	          
	             }
	             ds_eliminate.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
				 ds_eliminate.load({
							params : {
								start : ds_eliminate.lastOptions.params.start,
								limit : DEFAULT_PAGE_SIZE
							}
				 });
	             overFlat = true;
	             Ext.Ajax.timeout=30000;
   		    }
   	    }); 
	};
	//监听左边树点击事件
	var treeListener = new LeftTreeListener({ 
		  modelName : '终端剔除',
		  processClick : function(p, node, e){
			  var obj = node.attributes.attributes;
			  var type = node.attributes.type;
			  if(type == 'usr'){
			      userStore.baseParams = {tmnlAssetNo:obj.tmnlAssetNo,nodeType:type};
			      Ext.getBody().mask("正在获取数据...");
			      userStore.load({
								callback : function(recs, options, success) {
//									if (success)
//										grid_eliminate.getSelectionModel()
//												.selectRecords(recs, true);
									Ext.getBody().unmask();				
								},
								add : true
							});
			  }
			  else if(type == 'org'){
				  userStore.baseParams = {orgNo:obj.orgNo,orgType:obj.orgType,nodeType:type};
				  Ext.getBody().mask("正在获取数据...");
				  userStore.load({
								callback : function(recs, options, success) {
//									if (success)
//										grid_eliminate.getSelectionModel()
//												.selectRecords(recs, true);
									Ext.getBody().unmask();				
								},
								add : true
							});
			  }
			  else if(type == 'line'){
				  userStore.baseParams = {lineId:obj.lineId,nodeType:type};
				  Ext.getBody().mask("正在获取数据...");
				  userStore.load({
								callback : function(recs, options, success) {
//									if (success)
//										grid_eliminate.getSelectionModel()
//												.selectRecords(recs, true);
									Ext.getBody().unmask();				
								},
								add : true
							});
			  }
			  else if(type == 'cgp' || type=='ugp'){
				  userStore.baseParams = {groupNo:obj.groupNo,nodeType:type};
				  Ext.getBody().mask("正在获取数据...");
				  userStore.load({
								callback : function(recs, options, success) {
//									if (success)
//										grid_eliminate.getSelectionModel()
//												.selectRecords(recs, true);
									Ext.getBody().unmask();				
								},
								add : true
							});
			  }
			  else if(type == 'sub'){
				  userStore.baseParams = {subsId:obj.subsId,nodeType:type};
				  Ext.getBody().mask("正在获取数据...");
				  userStore.load({
								callback : function(recs, options, success) {
//									if (success)
//										grid_eliminate.getSelectionModel()
//												.selectRecords(recs, true);
									Ext.getBody().unmask();				
								},
								add : true
							});
			  }
			  return true; 
		   },
		   processUserGridSelect:function(cm,row,record){
			  userStore.baseParams = {tmnlAssetNo:record.get('tmnlAssetNo'),nodeType:'usr'};
			  Ext.getBody().mask("正在获取数据...");
			  userStore.load({
								callback : function(recs, options, success) {
//									if (success)
//										grid_eliminate.getSelectionModel()
//												.selectRecords(recs, true);
									Ext.getBody().unmask();				
								},
								add : true
							});
			  return true;
		    }	
	 });
	 
	userStore.on('load', function(thisstore, recs, obj) {
		ds_eliminate.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(thisstore));
		ds_eliminate.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
		sm_eliminate.selectAll();		
		grid_eliminate.doLayout();
	});
	
	ds_eliminate.on('load',function(){
	 if(gsselectAllcb.checked){
		gsselectAllcb.setValue(false);
	 }
	 });

	var viewPanel = new Ext.Panel({
		layout: 'border',
		items: [panel_zdtc,grid_eliminate],
		border : false
	});
	renderModel(viewPanel, '终端剔除');

});