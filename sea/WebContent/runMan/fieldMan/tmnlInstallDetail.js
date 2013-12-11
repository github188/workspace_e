/**
 * author:jiangyike
 */
 var tmnlAddrInfo;
 var flowNodeInfo;
 var tmnlDebugInfoStore;
 var tmnlDebugInfoGrid;
 var findState;
 var appNoRefresh;
 var consTypeRefresh;

// 去左空格;
function ltrim(s) {
	return s.replace(/^\s*/, "");
}

// 去右空格;
function rtrim(s) {
	return s.replace(/\s*$/, "");
}

// 去左右空格;
function trim(s) {
	return rtrim(ltrim(s));
}

Ext.onReady(function() {
	// 开始日期
	var startDate = new Ext.form.DateField({
				fieldLabel : '从',
				name : 'startDate',
				width : 90,
				format : 'Y-m-d',
				allowBlank : false,
				editable:false,
				labelSeparator : '',
				value : new Date().add(Date.DAY, -7)
			});

	// 结束日期
	var endDate = new Ext.form.DateField({
				fieldLabel : '到',
				name : 'endDate',
				width : 90,
				format : 'Y-m-d',
				labelSeparator : '',
				editable:false,
				allowBlank : false,
				value : new Date()
			});
	
	// 定义radio选择组
	var ti_RadioGroup = new Ext.form.RadioGroup({
		width : 350,
		height : 20,
		items : [new Ext.form.Radio({
					boxLabel : '调试失败',
					name : 'lps-radioGroup',
					inputValue : '1',
					checked : true
				}), new Ext.form.Radio({
					boxLabel : '未测试',
					name : 'lps-radioGroup',
					inputValue : '2'
				}), new Ext.form.Radio({
					boxLabel : '建档失败',
					name : 'lps-radioGroup',
					inputValue : '3'
				}), new Ext.form.Radio({
					boxLabel : '装接成功',
					name : 'lps-radioGroup',
					inputValue : '4'
				}), new Ext.form.Radio({
					boxLabel : '处理中',
					name : 'lps-radioGroup',
					inputValue : '5'
				})]	
	});
	
	// 定义checkBox,用于是否选中状态
	var tmnlInstStatus = new Ext.form.Checkbox( {
		boxLabel : '终端装接状态',
		name : 'tmnlInstStatus',
		checked : true,
		inputValue : 1,
		listeners : {
			'check' : function(r, c) {
			    if (c) {
					ti_RadioGroup.setDisabled(false);
				} else {
					ti_RadioGroup.setDisabled(true);
				}
			}
		}
	});
	
	ti_RadioGroup.setValue(ti_tmnlOpeType);
	startDate.setValue(ti_startD);
	endDate.setValue(ti_endD);
	
	//查询按钮
	var tid_queryBtn = new Ext.Button({
		text : '查询',
		name : 'query',
		width : 60,
		labelSeparator : '',
		handler : function() {
		    if(startDate.getValue()-endDate.getValue()>0){
				Ext.MessageBox.alert("提示","截止日期应大于起始日期！");
				return;
			}
		    tidStore.removeAll();
		    var debugStatus;
		    var consNo = trim(tid_consNo.getValue());
			if (Ext.isEmpty(consNo)){
				consNo = "";
			}
			var appNo = tid_appNo.getValue();
			if (Ext.isEmpty(appNo)){
				appNo = "";
			}
			findState=ti_RadioGroup.getValue().inputValue;
			if(findState=="5"){
			retryBtn.show();
			}else{
			retryBtn.hide();
			}
			if(tmnlInstStatus.getValue()){
				if(1 == ti_RadioGroup.getValue().getRawValue()){
					debugStatus = "-9";
				}else if(2 == ti_RadioGroup.getValue().getRawValue()){
					debugStatus = "0";
				}else if(3 == ti_RadioGroup.getValue().getRawValue()){
					debugStatus = "-1";
				}else if(4 == ti_RadioGroup.getValue().getRawValue()){
					debugStatus = "9";
				}else if(5 == ti_RadioGroup.getValue().getRawValue()){
					debugStatus = "1";
				}else{
					debugStatus = "0";
				}
			}else{
				debugStatus = "";
			}
		    tidStore.baseParams = {
					orgNo : ti_detOrgNo,
					interType : ti_interType,
					debugStatus : debugStatus,
					debugStartDate : startDate.getValue(),
					debugEndDate : endDate.getValue(),
					consNo : consNo,
					appNo : appNo
				};
		    tidStore.load();
	    }
	});
	
	// 用户编号
	var tid_consNo = new Ext.form.TextField({
				fieldLabel : "用户编号",
				name : 'tid_consNo',
				labelSeparator : '',
				readOnly : false,
				width : 120
	});
	
	// 申请单号
	var tid_appNo = new Ext.form.NumberField({
				fieldLabel : "申请单号",
				name : 'tid_appNo',
				labelSeparator : '',
				readOnly : false,
				width : 120
	});	
	
	//终端装接调试上部分面板
	var tmnlInstDetTopPanel = new Ext.Panel({
		region : 'north',
		height : 60,
		border : false,
		layout:'form',
		items : [{
			layout : 'column',
			style : "padding-top:5px",
			border : false,
			items : [{
					columnWidth : .13,
					border : false,
					layout : 'form',
					labelAlign : "right",
					labelWidth : 1,
					bodyStyle : 'padding:0px 0px 0px 0px',
					items : [tmnlInstStatus]
				}, {
					columnWidth : .45,
					border : false,
					layout : 'form',
					labelAlign : "right",
					labelWidth : 1,
					bodyStyle : 'padding:0px 0px 0px 0px',
					items : [ti_RadioGroup]
				}, {
					columnWidth : .16,
					layout : 'form',
					labelWidth : 20,
					border : false,
					bodyStyle : 'padding:0px 0px 0px 0px',
					items : [startDate]
				}, {
					columnWidth : .16,
					layout : 'form',
					labelWidth : 20,
					border : false,
					bodyStyle : 'padding:0px 0px 0px 0px',
					items : [endDate]
				},{
					columnWidth : .1,
					layout : 'form',
					labelWidth : 20,
					border : false,
					bodyStyle : 'padding:0px 0px 0px 0px',
					items : [tid_queryBtn]
				}]
		},{
		    layout : 'column',
			style : "padding-top:2px",
			border : false,
			items : [{
				    columnWidth : .35,
					layout : 'form',
					labelWidth : 50,
					border : false,
					bodyStyle : 'padding:0px 0px 0px 10px',
					items : [tid_appNo]
				}, {
				    columnWidth : .25,
					layout : 'form',
					labelWidth : 50,
					border : false,
					bodyStyle : 'padding:0px 0px 0px 0px',
					items : [tid_consNo]
				}, {
					columnWidth : .4,
					layout : 'form',
					labelWidth : 20,
					border : false,
					bodyStyle : 'padding:0px 0px 0px 0px',
					items : []
				}]
			}]
	});
	tidSm = new Ext.grid.CheckboxSelectionModel();		
	
    var tidCm = new Ext.grid.ColumnModel([tidSm,{
			header : '申请单号',
			dataIndex : 'appNo',
			sortable : true,
			align : 'center',
			width:120,
			renderer: function(s, m, rec){
	            return "<a href='javascript:' onclick='tmnlDebugInfoWindowShow(\""
	                + rec.get('appNo') + "\",\""+ rec.get('consType')+ "\");" + "'>" + rec.get('appNo') + "</a>";
	        }
		}, {
			header : '用户编号',
			dataIndex : 'consNo',
			sortable : true,
			align : 'center'
		}, {
			header : '用户名称',
			dataIndex : 'consName',
			sortable : true,
			align : 'center',
			renderer : function(val){
				if(Ext.isEmpty(val)){
					val = "";
				}
				var html = '<div align = "center" ext:qtitle="用户名称" ext:qtip="' + val
					+ '">' + val + '</div>';
				return html;
			}
		}, {
			header : '终端地址',
			dataIndex : 'tmnlAddr',
			sortable : true,
			align : 'center',
			renderer: function(s, m, rec){
			    if(null != rec.get('tmnlAddr')){
			    	return "<a href='javascript:' onclick='tmnlInfoMaintainWindowShow(\""
	                + rec.get('terminalId')+ "\",\""+ rec.get('protocolCode') + 
	                "\",\""+ rec.get('sendUpMode')+ "\");" + "'>" + rec.get('tmnlAddr') + "</a>";
			    }
			    return "";
            }
		}, {
			header : '电能表信息',
			dataIndex : 'meterNo',
			sortable : true,
			align : 'center',
			renderer: function(s, m, rec){
			    if(!Ext.isEmpty(rec.get('meterNo')) && "0" != rec.get('meterNo')){
			    	return "<a href='javascript:' onclick='meterInfoMaintainWindowShow(\""
	                + rec.get('appNo') + "\");" + "'>" + rec.get('meterNo') + "</a>";
			    }
			    return "";
            }
		}, {
			header : '终端类型',
			dataIndex : 'tmnlType',
			sortable : true,
			align : 'center'
		}, {
			header : '终端变更类别',
			dataIndex : 'tmnlTaskType',
			sortable : true,
			align : 'center',
			width:120,
			renderer: function(s, m, rec){
			    if(null != rec.get('tmnlTaskType')){
			    	return "<a href='javascript:' onclick='tmnlChgInfoWindowShow(\""
	                + rec.get('appNo') + "\");" + "'>" + rec.get('tmnlTaskType') + "</a>";
			    }
			    return "";
            }
		}, {
			header : '电能表变更类别',
			dataIndex : 'meterFlagName',
			sortable : true,
			align : 'center',
			renderer: function(s, m, rec){
			    if(null != rec.get('meterFlag')){
			    	if("1" == rec.get('meterFlag')){
				    	return "<a href='javascript:' onclick='meterChgInfoWindowShow(\""
		                    + rec.get('appNo') + "\");" + "'>" + rec.get('meterFlagName') + "</a>";
			    	}else{
			    		return rec.get('meterFlagName');
			    	}
			    }
			    return rec.get('meterFlagName');
            }
		}, {
			header : '用户变更类别',
			dataIndex : 'consChgType',
			sortable : true,
			align : 'center'
		}, {
			header : '操作链接',
			dataIndex : 'operLink',
			sortable : true,
			align : 'center',
			width:300,
			renderer:function(s, m, rec){
				var href = '';
				href = href + "<a href='javascript:' onclick='"
								+ "openOrigFrameQry(\"" + rec.get('consNo')+ "\",\"" + rec.get('consName') + "\");"
								+ "'><font color='green';font-weight:bold>[" + "报文查询"
								+ "]</font></a>" + "&nbsp;&nbsp;";
				href = href + "<a href='javascript:' onclick='"
								+ "openDataFetch(\"" + rec.get('tmnlAssetNo') + "\");"
								+ "'><font color='green';font-weight:bold>[" + "数据召测"
								+ "]</font></a>" + "&nbsp;&nbsp;";	
//				href = href + "<a href='javascript:' onclick='"
//								+ "openTmnlParaSet(\"" + rec.get('tmnlAssetNo') + "\",\""+ rec.get('protocolCode')+"\");"
//								+ "'><font color='green';font-weight:bold>[" + "终端参数"
//								+ "]</font></a>" + "&nbsp;&nbsp;";
				href = href + "<a href='javascript:' onclick='"
								+ "openGatherTaskCompile(\"" + rec.get('tmnlAssetNo') + "\",\""+ rec.get('consNo')+"\");"
								+ "'><font color='green';font-weight:bold>[" + "任务编制"
								+ "]</font></a>" + "&nbsp;&nbsp;";
				return href;
			}
		}]);
	
    //后台store，一次装载
	var tidStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : './runman/feildman/tmnlInstallDetail!loadDetailGridData.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'tmnlInstDetList',
						totalProperty : 'totalCount'
					}, [{
								name : 'appNo'
							}, {
								name : 'consNo'
							}, {
								name : 'consName'
							}, {
								name : 'tmnlAddr'
							}, {
								name : 'terminalId'
							}, {
								name : 'meterNo'
							}, {
								name : 'tmnlType'
							}, {
								name : 'tmnlTaskType'
							}, {
								name : 'meterFlag'
							}, {
								name : 'meterFlagName'
							}, {
								name : 'consChgType'
							}, {
								name : 'protocolCode'
							}, {
								name : 'sendUpMode'
							}, {
								name : 'tmnlAssetNo'
							}, {
								name : 'operLink'
							}, {
								name : 'consType'
							}])
		});
		
	//前台store，用于显示
	var tidShowStore = new Ext.data.Store({
		remoteSort : true,
		reader : new Ext.data.ArrayReader({
				fields : [ 
					     {
								name : 'appNo'
							}, {
								name : 'consNo'
							}, {
								name : 'consName'
							}, {
								name : 'tmnlAddr'
							}, {
								name : 'terminalId'
							}, {
								name : 'meterNo'
							}, {
								name : 'tmnlType'
							}, {
								name : 'tmnlTaskType'
							}, {
								name : 'meterFlag'
							}, {
								name : 'meterFlagName'
							}, {
								name : 'consChgType'
							}, {
								name : 'protocolCode'
							}, {
								name : 'sendUpMode'
							}, {
								name : 'tmnlAssetNo'
							}, {
								name : 'operLink'
							}, {
								name : 'consType'
							}]
				})
	});
	
	function storeToArray(valStore) {
		var data = new Array();
		for (var i = 0; i < valStore.getCount(); i++) {
			data[i] = new Array();
 			data[i][0] = valStore.getAt(i).data.appNo;
			data[i][1] = valStore.getAt(i).data.consNo;
			data[i][2] = valStore.getAt(i).data.consName;
			data[i][3] = valStore.getAt(i).data.tmnlAddr;
			data[i][4] = valStore.getAt(i).data.terminalId;
			data[i][5] = valStore.getAt(i).data.meterNo;
			data[i][6] = valStore.getAt(i).data.tmnlType;
			data[i][7] = valStore.getAt(i).data.tmnlTaskType;
			data[i][8] = valStore.getAt(i).data.meterFlag;
			data[i][9] = valStore.getAt(i).data.meterFlagName;
			data[i][10] = valStore.getAt(i).data.consChgType;
			data[i][11] = valStore.getAt(i).data.protocolCode;
			data[i][12] = valStore.getAt(i).data.sendUpMode;
			data[i][13] = valStore.getAt(i).data.tmnlAssetNo;
			data[i][14] = valStore.getAt(i).data.operLink;
			data[i][15] = valStore.getAt(i).data.consType;
		}
		return data;
	}
	
	// toolbar上的checkbox
	var tiSelectAllcb = new Ext.form.Checkbox({
				boxLabel : '全选',
				name : 'tiSelectAllcb',
				checked : false,
				listeners : {
					'check' : function(r, c) {
						if (c) {
							tidSm.selectAll();
							tidGridPnl.getGridEl().mask();
							tidGridPnl.getBottomToolbar().disable();
						} else {
							tidSm.clearSelections();
							tidGridPnl.getGridEl().unmask();
							tidGridPnl.getBottomToolbar().enable();
						}
					}
				}
			});
	var retryBtn=new Ext.Button({
	text:"重置状态",
	hidden:true,
	iconCls: 'net-terminal',
	handler:function(){
		var rs=tidSm.getSelections();
		if(rs.length==0){
		return !!Ext.Msg.alert("警告","请选择申请单号");
		}
	var params={
	appNos:[]
	};
	Ext.each(rs,function(r){
	params.appNos.push(r.get("appNo"));
	});
	Ext.MessageBox.show({
							title : "警告",
							msg : "是否将当前状态设为失败",
							icon : Ext.MessageBox.WARNING,
							buttons : Ext.MessageBox.YESNO,
							fn : function(msg) {
								if (msg == "yes") {
									Ext.Ajax.request({
										url : './runman/feildman/tmnlInfoMaintain!resetState.action',
										params : params,
										success : function(response) {
//											var o = Ext
//													.decode(response.responseText);
											Ext.Msg.alert("提示", "重置状态成功");
										}
									});
								}
							}
						});
	}
	});
	//终端装接调试Grid
	var tidGridPnl = new Ext.grid.GridPanel({
			region:'center',
			stripeRows : true,
			monitorResize : true,
			viewConfig : {
				forceFit : false
			},
			border : true,
			autoScroll : true,
			cm : tidCm,
			sm: tidSm,
			ds : tidShowStore,
			allStore : tidStore,
			bbar : new Ext.ux.MyToolbar({
				        enableExpAll : true,
						store : tidShowStore
					}),
			tbar : [{
				xtype:'label',
				html : "<font font-weight:bold;>"+"查询结果"+"</font>"
			}, {xtype: 'tbfill'},tiSelectAllcb, '-',
			{
				text : '手工触发',
				iconCls: 'ammeterRunStatus',
				handler : function() {
					var appList = new Array();
			 		if(tiSelectAllcb.checked ){
						if(0==tidStore.getCount()){
							 Ext.MessageBox.alert("提示","请选择数据！");
				     	     return;
						}
						for(var i = 0; i < tidStore.getCount(); i++){
							appList[i] = tidStore.getAt(i).get('appNo');
						}
			 		}
			 		else{
					    var recs = tidSm.getSelections();
				 		if(null==recs||0==recs.length){
				 	    	 Ext.MessageBox.alert("提示","请选择数据！");
				 	    	 return;
				 		}
			  		    for(var i = 0; i < recs.length; i++){
			  		    	if(null != recs[i].get('appNo') && 
			  		    	    recs[i].get('appNo').length >= 0){
			  		    	        appList[i] = recs[i].get('appNo');
			  		    	    }
			  		    }	  	
			 		}
			 		Ext.MessageBox.alert("提示","已经启动手工调试流程，请等待！");
			 		Ext.Ajax.request({
				        url : './runman/feildman/tmnlInfoMaintain!handEvent.action',
				        params : {
				             appList : appList
				        },
				        success : function(response) {
							return;
					    },
					    failure : function() {
						    Ext.MessageBox.alert("提示", "失败");
						    return;
					    }
				    });
				}
			},	
			{
				text : '营销通知',
				iconCls: 'net-terminal',
				handler : function() {
			    }
		    },retryBtn]
		});
		
	tidStore.on('load', function(thisstore, recs, obj) {
		tidShowStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(thisstore));
		tidShowStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
		tidGridPnl.doLayout();
	});
	
	tidShowStore.on('load',function(){
		if(tiSelectAllcb.checked){
			tiSelectAllcb.setValue(false);
		}
	    //tidSm.selectAll();
	});
	
	//初始化，第一次
	if(!Ext.isEmpty(tidStore)){
		var debugStatus;
		if(1 == ti_tmnlOpeType){
				debugStatus = "-9";
			}else if(2 == ti_tmnlOpeType){
				debugStatus = "0";
			}else if(3 == ti_tmnlOpeType){
				debugStatus = "-1";
			}else if(4 == ti_tmnlOpeType){
				debugStatus = "9";
			}else if(5 == ti_tmnlOpeType){
				debugStatus = "1";
				retryBtn.show();
			}else{
				debugStatus = "0";
			}
			
		tidStore.removeAll();
		tidStore.baseParams = {
						orgNo : ti_detOrgNo,
						interType : ti_interType,
						debugStatus : debugStatus,
						debugStartDate : startDate.getValue(),
						debugEndDate : endDate.getValue()
		};
		tidStore.load();
	}

	// 定义整个页面面板
	var viewPanel = new Ext.form.FormPanel({
				layout : 'border',
				border : false,
				items : [tmnlInstDetTopPanel, tidGridPnl]
			});
	renderModel(viewPanel, '终端装接调试');	
	
	//激活的情况下，tab切换调用
	Ext.getCmp('终端装接调试').on('activate', function() {
		ti_RadioGroup.setValue(ti_tmnlOpeType);
		startDate.setValue(ti_startD);
	    endDate.setValue(ti_endD);
		if(!Ext.isEmpty(tidStore)){
			var debugStatus;
			if(tmnlInstStatus.getValue()){
				if(1 == ti_RadioGroup.getValue().getRawValue()){
					debugStatus = "-9";
				}else if(2 == ti_RadioGroup.getValue().getRawValue()){
					debugStatus = "0";
				}else if(3 == ti_RadioGroup.getValue().getRawValue()){
					debugStatus = "-1";
				}else if(4 == ti_RadioGroup.getValue().getRawValue()){
					debugStatus = "9";
				}else if(5 == ti_RadioGroup.getValue().getRawValue()){
					debugStatus = "1";
					retryBtn.show();
				}else{
					debugStatus = "0";
				}
			}else{
				debugStatus = "";
			}
				
			tidStore.removeAll();
			tidStore.baseParams = {
							orgNo : ti_detOrgNo,
							debugStatus : debugStatus,
							interType : ti_interType,
							debugStartDate : startDate.getValue(),
							debugEndDate : endDate.getValue()
			};
			tidStore.load();
		}
	});
});


//---------------------------------终端调试信息弹出窗口-----------------------start
//终端调试信息弹出窗口调用函数
function tmnlDebugInfoWindowShow(appNo,consType){
	var tmnlDebugInfoCm;
	
	var tiArrayCm = new Array();
	
	if(!Ext.isEmpty(consType) && 2 == consType){
		appNoRefresh  = appNo;
		consTypeRefresh =consType;
		if(!Ext.isEmpty(tiArrayCm)){
			tiArrayCm.length = 0;
		}
		tiArrayCm.push({
			header : "终端地址",
			dataIndex : 'tmnlAddr',
			align : "center"
		});
		tiArrayCm.push({
			header : "流程节点",
			dataIndex : 'flowNode',
			width : 120,
			align : 'center'
		});
		tiArrayCm.push({
			header : "流程状态",
			dataIndex : 'flowStatus',
			align : 'center'
		});
		tiArrayCm.push({
			header : "测量点列表",
			dataIndex : 'mpSnList',
			width:250,
			align : 'center'
		});
		tiArrayCm.push({
			header : "",
			dataIndex : 'del',
			width: 50,
			align : 'center',
			renderer : function(s, m, rec){
//				alert(rec.get('tmnlAddr'));
//				alert('<a href="#" onclick="delTmnlDebugInfo(\''+rec.get('tmnlAddr')+'\',\''+rec.get('flowNode')+'\');return false;">删除</a>');
				return '<a href="#" onclick="delTmnlDebugInfo(\''+rec.get('tmnlAddr')+'\',\''+rec.get('flowNo')+'\');return false;">删除</a>'
			}
		});
		tmnlDebugInfoCm = new Ext.grid.ColumnModel(tiArrayCm);
	}else{
		if(!Ext.isEmpty(tiArrayCm)){
			tiArrayCm.length = 0;
		}
		tiArrayCm.push({
			header : "终端地址",
			dataIndex : 'tmnlAddr',
			align : "center"
		});
		tiArrayCm.push({
			header : "电能表资产号",
			dataIndex : 'meterNo',
			align : 'center'
		});
		tiArrayCm.push({
			header : "调试进度",
			width : 100,
			dataIndex : 'debugProcess',
			align : 'center'
		});
		tiArrayCm.push({
			header : "调试时间",
			dataIndex : 'debugTime',
			width : 120,
			align : 'center'
		});
		tiArrayCm.push({
			header : "失败原因",
			dataIndex : 'failCause',
			align : 'center'
		});
		tmnlDebugInfoCm = new Ext.grid.ColumnModel(tiArrayCm);
	}
	
	tmnlDebugInfoStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : './runman/feildman/tmnlDebugInfo!loadTmnlDebugInfoGridData.action'
				}),
		baseParams : {appNo : ''},		
		reader : new Ext.data.JsonReader({
					root : 'tmnlDebugInfoList',
					totalProperty : 'totalCount'
				}, [{name:'tmnlAddr'},
					{name:'meterNo'},
					{name:'debugProcess'},
					{name:'debugTime'},
					{name:'failCause'},
					{name:'flowNode'},
					{name:'flowNo'},
					{name:'flowStatus'},
					{name:'mpSnList'}])
	});
	
	tmnlDebugInfoGrid = new Ext.grid.GridPanel({
		    autoScroll:true,
		    stripeRows : true,
		    height:300,
		    viewConfig : {
		        autoFill : true
			},
		    anchor:'100%',
		    colModel : tmnlDebugInfoCm,
		    ds : tmnlDebugInfoStore,
		    bbar : new Ext.ux.MyToolbar({
			    enableExpAll : true,
			    store : tmnlDebugInfoStore
		    })
	});
	
	var mpListInfoTA = new Ext.form.TextArea({
		value : '',
		width:490,
		height : 80,
		fieldLabel : '测量点列表信息'
	})
	
	var mpListInfoPnl = new Ext.Panel({
		height : 85,
		plain : true,
		border : false,
		layout:'form',
		items : [{
			layout : 'form',
			labelWidth : 85,
			labelSeparator:'',
			bodyStyle : 'padding:3px 0px 0px 2px',
			border : false,
		    items : [mpListInfoTA]
		}]
	});
	
	var tmnlDebugInfoWindow;
	
	if(!Ext.isEmpty(consType) && 2 == consType){
		tmnlDebugInfoWindow = new Ext.Window({
			frame:true,
			width:600,
			height:420,
			layout:"form",
			modal:true,
			closeAction:"close",
			plain:true,//设置背景颜色
			resizable:false,
			draggable:false,//不可移动
			buttonAlign:"center",//按钮的位置
			title:'【终端调试信息】',
			items:[tmnlDebugInfoGrid,mpListInfoPnl]
		});
		
		tmnlDebugInfoGrid.on('rowclick',function(grid, rowIndex, e){
			if(rowIndex >= 0){
				if(!Ext.isEmpty(tmnlDebugInfoStore.getAt(rowIndex))){
					mpListInfoTA.setValue(tmnlDebugInfoStore.getAt(rowIndex).get('mpSnList'));
				}
			}
		})
	}else{
		tmnlDebugInfoWindow = new Ext.Window({
			frame:true,
			width:600,
			height:335,
			layout:"form",
			modal:true,
			closeAction:"close",
			plain:true,//设置背景颜色
			resizable:false,
			draggable:false,//不可移动
			buttonAlign:"center",//按钮的位置
			title:'【终端调试信息】',
			items:[tmnlDebugInfoGrid]
		});
	}
	
	tmnlDebugInfoStore.baseParams = {
		appNo : appNo,
		consType: consType
	};
	
	tmnlDebugInfoStore.load();
	
	tmnlDebugInfoWindow.show();
	

}

	
//终端调试信息弹出窗口constype=2删除
function delTmnlDebugInfo(tmnlAddr,flowNode){
	tmnlAddrInfo = tmnlAddr;
	flowNodeInfo = flowNode;
	Ext.MessageBox.confirm('提示', '确定删除调试信息？', delTmnlFolwInfo);
}
//执行后台删除数据
function delTmnlFolwInfo(btn){
		if (btn == 'no')
			return true;
		Ext.getBody().mask("正在处理...");
		Ext.Ajax.request({
					url : './runman/feildman/tmnlDebugInfo!delTmnlDebugInfo.action',
					params : {
						tmnlAddr : tmnlAddrInfo,
						flowNode : flowNodeInfo
					},
					callback : function(options, success, response) {
						Ext.getBody().unmask();
						if (success) {
							var result = Ext.decode(response.responseText);
							if (null != result.flag) {
								if (result.flag == 0)
									Ext.MessageBox.alert("提示", "删除失败!");
								else if (result.flag == 1) {
									updateTmnlFolwInfo(function() {
												Ext.MessageBox.alert("提示",	"删除完毕!");
											});
								}
							}
						}
					}
				});
	
	}
	//删除成功后更新store数据
	function updateTmnlFolwInfo(){
		tmnlDebugInfoStore.baseParams = {
		appNo : appNoRefresh,
		consType: consTypeRefresh
	};
	
	tmnlDebugInfoStore.load();
	}
//---------------------------------终端调试信息弹出窗口-----------------------end
//---------------------------------终端信息维护弹出窗口-----------------------start
//终端信息维护弹出窗口调用函数
function tmnlInfoMaintainWindowShow(terminalId,protocolCode,sendUpMode){
	var tmnlId = terminalId;
	//终端规约
	var timProtocolStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url : './runman/feildman/tmnlInfoMaintain!findProtocalCodeInfo.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'protocolList'
			}, [{name:'protocolCode'},{name:'protocolName'}]),
		listeners : {
			load : function() { 
				if(!Ext.isEmpty(protocolCode)){
		            timProtocolComboBox.setValue(protocolCode);
			    }
			}
		}
	});
	
	var timProtocolComboBox = new  Ext.form.ComboBox({
		store : timProtocolStore,
		name :'timProtocolComboBox',
		fieldLabel:'终端规约',
		valueField : 'protocolCode',
		editable : false,
		triggerAction : 'all',
		forceSelection : true,
		mode : 'remote',
		selectOnFocus : true,
		displayField : 'protocolName',
		emptyText:'--请选择终端规约--',
		labelSeparator:'',
		anchor:'85%'
	});
	
	timProtocolStore.load();
	//终端任务上送方式
	var sendModeStore = new Ext.data.ArrayStore({
		fields : ['value', 'name'],
		data : [['0', '主站主动召测'], ["1", '终端自动上送']]
	});
	
	var sendModeComboBox = new  Ext.form.ComboBox({
		store : sendModeStore,
		name :'sendModeComboBox',
		fieldLabel:'终端任务上送方式',
		valueField : 'value',
		editable : false,
		triggerAction : 'all',
		forceSelection : true,
		mode : 'local',
		selectOnFocus : true,
		displayField : 'name',
		emptyText:'--请选择任务上送方式--',
		labelSeparator:'',
		anchor:'85%'
	});

	if(!Ext.isEmpty(sendUpMode)){
		if(1 == sendUpMode || 0 == sendUpMode){
	        sendModeComboBox.setValue(sendUpMode);
		}
	}
	
	//保存按钮
	var tmnlSaveBtn = new Ext.Button({
		    text : '保存',
		    name : 'tmnlSaveBtn',
		    width : 70,
		    handler : function(){
		    	if(Ext.isEmpty(sendModeComboBox.getValue())){
		    		Ext.MessageBox.alert("提示", "请选择任务上送方式！");
					return;
		    	}
		    	if(Ext.isEmpty(timProtocolComboBox.getValue())){
		    		Ext.MessageBox.alert("提示", "请选择终端规约！");
					return;
		    	}
		    	Ext.Ajax.request({
			        url : './runman/feildman/tmnlInfoMaintain!saveTmnlInfo.action',
			        params : {
			             tmnlId : tmnlId,
			             protocolCode : timProtocolComboBox.getValue(),
			             sendUpMode : sendModeComboBox.getValue()
			        },
			        success : function(response) {
			        	tmnlInfoMaintainWindow.close();
						return;
				    },
				    failure : function() {
					    Ext.MessageBox.alert("提示", "失败");
					    return;
				    }
			    })
	        }  
	});
	
	//退出按钮
	var tmnlExitBtn = new Ext.Button({
		    text : '退出',
		    name : 'tmnlExitBtn',
		    width : 70,
		    handler : function(){
		        tmnlInfoMaintainWindow.close();
	        }  
	});
	
	//panel
	var tmnlInfoMaintainPanel = new Ext.Panel({
	  	border:true,
	  	layout:'form',
	  	height:100,
	  	bodyStyle:'padding:15px 0px 0px 0px',
	  	buttonAlign:'center',
	  	items:[{
	  	    bodyStyle:'padding:10px 0px 0px 20px',
	  	    border:false,
	  	    layout:'form',
	  	    labelSeparator:'',
	  	    labelWidth : 100,
	  	    labelAlign : "right",
	  	    items:[sendModeComboBox]
	  	}, {
	  		bodyStyle:'padding:20px 0px 0px 20px',
	   	    border:false,
	   	    layout:'form',
	   	    labelSeparator:'',
	   	    labelWidth : 100,
	   	    labelAlign : "right",
	   	    items:[timProtocolComboBox]
	  	}, {
	  		bodyStyle:'padding:20px 0px 0px 20px',
	   	    border:false,
	   	    layout:'column',
	   	    labelSeparator:'',
	   	    labelWidth : 100,
	   	    labelAlign : "right",
	   	    items:[{
	   	    	columnWidth : .7,
				layout: 'form',
				border : false,
				bodyStyle:'padding:0px 5px 0px 100px',
				items : [tmnlSaveBtn]
	   	    },{
	   	    	columnWidth : .3,
				layout: 'form',
				border : false,
				bodyStyle:'padding:0px 5px 0px 5px',
				items : [tmnlExitBtn]
	   	    }]
	  	}]
	});
	
	//终端信息维护窗口
	var tmnlInfoMaintainWindow = new Ext.Window({
		    name:'bczd',
			width:350,
			height:200,
			layout:"fit",
			modal:true,
			closeAction:"close",
			resizable:false,
			draggable:false,//不可移动
			buttonAlign:"center",//按钮的位置
			title:'【终端信息维护】',
			items:[tmnlInfoMaintainPanel]
	});

	tmnlInfoMaintainWindow.show();
}
//---------------------------------终端信息维护弹出窗口-----------------------end

//---------------------------------终端变更信息弹出窗口-----------------------start
//终端变更信息弹出窗口调用函数
function tmnlChgInfoWindowShow(appNo){
	var tmnlChgInfoCm = new Ext.grid.ColumnModel([{
			header : "终端地址",
			dataIndex : 'tmnlAddr',
			align : "center"
		},{
			header : "状态",
			dataIndex : 'status',
			align : 'center'
		}]);	

	var tmnlChgInfoStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : './runman/feildman/tmnlChgInfo!loadTmnlChgInfoGridData.action'
				}),
		baseParams : {appNo : ''},		
		reader : new Ext.data.JsonReader({
					root : 'tmnlChgInfoList'
				}, [{name:'tmnlAddr'},
					{name:'status'}])
	});
	
	var tmnlChgInfoGrid = new Ext.grid.GridPanel({
		    autoScroll:true,
		    stripeRows : true,
		    viewConfig : {
					forceFit : true
				},
		    anchor:'100%',
		    colModel : tmnlChgInfoCm,
		    ds : tmnlChgInfoStore
	});
	
	var tmnlChgInfoWindow = new Ext.Window({
			frame:true,
			width:300,
			height:200,
			layout:"fit",
			modal:true,
			closeAction:"close",
			plain:true,//设置背景颜色
			resizable:false,
			draggable:false,//不可移动
			buttonAlign:"center",//按钮的位置
			title:'【终端变更信息】',
			items:[tmnlChgInfoGrid]
	});

	tmnlChgInfoStore.baseParams = {
		appNo : appNo
	};
	tmnlChgInfoStore.load();
	tmnlChgInfoWindow.show();
}
//---------------------------------终端变更信息弹出窗口-----------------------end

//---------------------------------电能表变更信息弹出窗口-----------------------start
//电能表变更信息弹出窗口调用函数
function meterChgInfoWindowShow(appNo){
	var meterChgInfoCm = new Ext.grid.ColumnModel([{
			header : "原电能表资产号",
			dataIndex : 'oldAssetNo',
			align : "center"
		},{
			header : "新电能表资产号",
			dataIndex : 'newAssetNo',
			align : 'center'
		},{
			header : "原抄见示数 ",
			dataIndex : 'oldMRNum',
			align : 'center'
		},{
			header : "新抄见示数 ",
			dataIndex : 'newMRNum',
			align : 'center'
		}]);	

	var meterChgInfoStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : './runman/feildman/meterChgInfo!loadMeterChgInfoGridData.action'
				}),
		baseParams : {appNo : ''},		
		reader : new Ext.data.JsonReader({
					root : 'meterChgInfoList'
				}, [{name:'oldAssetNo'},
					{name:'newAssetNo'},
					{name:'oldMRNum'},
					{name:'newMRNum'}])
	});
	
	var meterChgInfoGrid = new Ext.grid.GridPanel({
		    autoScroll:true,
		    stripeRows : true,
		    viewConfig : {
					forceFit : true
				},
		    anchor:'100%',
		    colModel : meterChgInfoCm,
		    ds : meterChgInfoStore
	});
	
	var meterChgInfoWindow = new Ext.Window({
			frame:true,
			width:400,
			height:200,
			layout:"fit",
			modal:true,
			closeAction:"close",
			plain:true,//设置背景颜色
			resizable:false,
			draggable:false,//不可移动
			buttonAlign:"center",//按钮的位置
			title:'【电能表变更信息】',
			items:[meterChgInfoGrid]
	});
	meterChgInfoStore.baseParams = {
		appNo : appNo
	};
	meterChgInfoStore.load();
	meterChgInfoWindow.show();
}
//---------------------------------电能表变更信息弹出窗口-----------------------end

//---------------------------------电能表信息维护弹出窗口-----------------------start
//电能表信息维护弹出窗口调用函数
function meterInfoMaintainWindowShow(appNo){
	var sValue="";//用于修改store中的通讯规约编码
	var sName="";//用于页面显示通讯规约编码值
	var initName="";//青海使用，用于显示
	var meterMaintainStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : './runman/feildman/meterInfoMaintain!loadMeterMaintainInfoData.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'meterMaintainInfoList',
					    totalProperty : 'totalCount'
					}, [{name:'tmnlAssetNo'},
						{name:'meterNo'},
						{name:'mpNo'},
						{name:'mpName'},
						{name:'commNo'},
						{name:'commAddr'},
						{name:'mpSn'},
						{name:'mrFlag'},
						{name:'mrTime'},
						{name:'mrNum'},
						{name:'consNo'},
						{name:'consName'},
						{name:'commNum'}])
		});
		
    //青海屏蔽		
	var modelComStore = new Ext.data.Store({
		proxy : new Ext.data.MemoryProxy(),
		reader : new Ext.data.JsonReader({
				root : 'meterCommInfoList'
			}, [{name:'name'},{name:'value'}])
	});
	
	var meterMaintainCm = new Ext.grid.ColumnModel([{
					header : "终端资产号",
					dataIndex : 'tmnlAssetNo',
					align : "center"
				}, {
					header : '用户编号',
					dataIndex : 'consNo',
					sortable : true,
					align : 'center'
				}, {
					header : '用户名称',
					dataIndex : 'consName',
					sortable : true,
					align : 'center',
					renderer : function(val){
						if(Ext.isEmpty(val)){
							val = "";
						}
						var html = '<div align = "center" ext:qtitle="用户名称" ext:qtip="' + val
							+ '">' + val + '</div>';
						return html;
					}
				},{
					header : "电能表资产号",
					dataIndex : 'meterNo',
					align : "center",
					renderer : function(val) {
						if(Ext.isEmpty(val)){
							val = "";
						}
						var html = '<div align = "center" ext:qtitle="表计资产号" ext:qtip="' + val
						    + '">' + val + '</div>';
						return html;
			        }
				},{
					header : "计量点编号",
					width : 70,
					dataIndex : 'mpNo',
					sortable : true,
					align : "center"
				} ,
				{
					header : "计量点名称",
					width : 120,
					dataIndex : 'mpName',
					sortable : true,
					align : "center"
				} ,{
					header : "通讯规约",
					dataIndex : 'commNum',
					sortable : true,
					align : 'center',
					editor : new Ext.form.ComboBox({
						triggerAction : 'all',
						store : modelComStore,
						editable : false,
						displayField : 'name',
						valueField : 'value',
						mode : 'local',
						selectOnFocus : true,
						listeners : {
							'select' : function(c,r,i) {
								sValue = this.getValue();
								sName = this.getRawValue();
							}
						}
					}),
					renderer : function(val,s,rec) {
						//青海特殊，不需要修改，如果要修改，下面语句生效//if(!Ext.isEmpty(val)) 等价于 val&&rec.set("commNo",val);
//						var value = sValue;
//						var name = sName;
//						sValue ="";
//						sName = "";
//						if(Ext.isEmpty(name)){
//							return rec.get('commNum');
//						}else{
//							rec.set("commNo",value);
//							rec.set("commNum",name);
//							return "<div align = 'left'>" + name + "</div>";
//						}
						
						//以下是青海代码，如果上面代码生效，则下面代码失效，需要注释
					    if(rec.get('commNum') == '-请选择-'){
					    	return '-请选择-';
					    }
						if(Ext.isEmpty(rec.get('commNo')) || 'null' == rec.get('commNo')){
							rec.set("commNum",'-请选择-');
							return '-请选择-';
						}else{
							var mc = modelComStore.query('value',rec.get('commNo'));
							if(!Ext.isEmpty(mc) && mc.length > 0){
								rec.set("commNum",mc.get(0).get('name'));
								return "<div align = 'center'>" + mc.get(0).get('name') + "</div>";
							}else{
								rec.set("commNum",'-请选择-');
								return '-请选择-';
							}
						}
					}
				},{
					header : "通讯地址",
					dataIndex : 'commAddr',
					sortable : true,
					align : 'center',	
					editor : new Ext.form.TextField({
						allowBlank : true,
						readOnly:true//青海特殊，不需要修改，如果要修改，去掉该属性
					})
				}, {
					header : "测量点",
					dataIndex : 'mpSn',
					sortable : true,
					align : 'center'
				}, {
					header : "是否成功",
					dataIndex : 'mrFlag',
					sortable : true,
					align : 'center'
				}, {
					header : "抄表时间",
					width:120,
					dataIndex : 'mrTime',
					sortable : true,
					align : 'center'
				}, {
					header : "抄见示数",
					dataIndex : 'mrNum',
					sortable : true,
					align : 'center'
				}]);	
				
	var meterMaintainGrid = new Ext.grid.EditorGridPanel({
		height:345,
		autoScroll:true,
		anchor:'100%',
		colModel : meterMaintainCm,
		ds : meterMaintainStore,
		viewConfig : {
			forceFit : false
		},
		bbar : new Ext.ux.MyToolbar({
			enableExpAll : true,
			store : meterMaintainStore
		})
	});
	
	// 监听editGrid单元格被单击的事件
    meterMaintainGrid.on('afteredit', function(en) {
	    if (en.field == 'commNum') {
	    	en.record.commit();
	    	//青海注释掉，其他地方放开
//	    	Ext.MessageBox.show({
//                        title: '提示',
//           				msg: '是否更新电能表的通讯规约？',
//           				width:300,
//           				buttons: Ext.MessageBox.OKCANCEL,
//           				fn: function(e){
//           					if("ok" == e){
//           					    //TODO 调用后台咯
//           						alert(en.value);
//           						if(!Ext.isEmpty(meterMaintainStore) && !Ext.isEmpty(meterMaintainStore.getAt(en.row))
//           						    && !Ext.isEmpty(meterMaintainStore.getAt(en.row).data )){
//           						    alert(meterMaintainStore.getAt(en.row).data.meterNo);
//           						}
//           					}
//           				}
//      		});
	    }
	    if (en.field == 'commAddr') {
	    	//青海注释掉，其他地方放开
//	    	Ext.MessageBox.show({
//                        title: '提示',
//           				msg: '是否更新电能表的通讯地址？',
//           				width:300,
//           				buttons: Ext.MessageBox.OKCANCEL,
//           				fn: function(e){
//           					if("ok" == e){
//           					    //TODO 调用后台咯
//           						alert(en.value);
//           						if(!Ext.isEmpty(meterMaintainStore) && !Ext.isEmpty(meterMaintainStore.getAt(en.row))
//           						    && !Ext.isEmpty(meterMaintainStore.getAt(en.row).data )){
//           						    alert(meterMaintainStore.getAt(en.row).data.meterNo);
//           						}
//           					}
//           				}
//      		});
	    }
	});
	
	//是否成功store
	var isSuccStore = new Ext.data.ArrayStore({
			fields : ['value', 'flagName'],
			data : [['2', '全部'], ['1', '成功'], ['0', '失败']]
	});

	//是否成功combobox
	var isSuccComboBox = new Ext.form.ComboBox({
					fieldLabel : '是否成功',
					store : isSuccStore,
					bodyStyle: 'padding:10px;',
					triggerAction : 'all',
					mode : 'local',
					valueField : 'value',
					displayField : 'flagName',
					width: 150,
					value:2,
					selectOnFocus : true,
					labelSeparator:''
	});
		
	var tmnlSuccFlagPanel = new Ext.Panel({
		height : 40,
		plain : true,
		items : [{
					baseCls : "x-plain",
					layout : "column",
					style : "padding:8px",
					items : [{
								columnWidth : .3,
								layout : "form",
								labelWidth : 50,
								baseCls : "x-plain",
								items : [isSuccComboBox]
							},{
								columnWidth : .15,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
											text : "查询",
											listeners : {
												"click": function (){
									                    var flag = isSuccComboBox.getValue();
														meterMaintainStore.baseParams = {
																appNo : appNo,
																succFlag : flag
														};
														meterMaintainStore.load();
												}
											},
											width : 80
										}]
							},{
								columnWidth : .55,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
									text : "退出",
									listeners : {
										"click": function (){
									        meterInfoMaintainWindow.close();
										}
									},
									width : 80
								
								}]
							}]
				}]
	});
		
	var meterInfoMaintainWindow = new Ext.Window({
		    name:'meterInfoMaintainWindow',
			width:800,
			height:420,
			layout:"form",
			modal:true,
			closeAction:"close",
			resizable:false,
			draggable:false,//不可移动
			buttonAlign:"center",//按钮的位置
			title:'【电能表信息维护】',
			items:[tmnlSuccFlagPanel,meterMaintainGrid]
	});
	
	Ext.Ajax.request({
		url : "./runman/feildman/meterInfoMaintain!loadMeterCommInfoData.action",
		params : {
		},
		success : function(response) {
			var result = Ext.decode(response.responseText);
			if (result) {
			    modelComStore.loadData(result);
			}
		}
	});
	meterMaintainStore.removeAll();
	var flag = isSuccComboBox.getValue();
	meterMaintainStore.baseParams = {
			appNo : appNo,
			succFlag : flag
	};
	meterMaintainStore.load();
	meterInfoMaintainWindow.show();
}
//---------------------------------电能表信息维护弹出窗口-----------------------end
//---------------------------------链接到原始报文查询-----------------------start
function openOrigFrameQry(consNo,consName){
	tmnlInstllDet_Flag = true;
	tmnlInstllDet_ConsNo = consNo;
	if(Ext.isEmpty(consName) || "null" == consName){
		consName = "";
	}
	tmnlInstllDet_ConsName = consName;
	openTab("原始报文查询", "./baseApp/dataGatherMan/origFrameQry.jsp",false,"origFrameQry");
}
//---------------------------------链接到原始报文查询-----------------------end
//---------------------------------链接到数据召测查询-----------------------start
function openDataFetch(tmnlAssetNo){
	window.dataFetch_tmnl = tmnlAssetNo;
	openTab("数据召测", "./baseApp/dataGatherMan/dataFetch.jsp",false,"DataFetch");
}
//---------------------------------链接到数据召测查询-----------------------end
//---------------------------------链接到采集任务编制查询-----------------------start
function openGatherTaskCompile(tmnlAssetNo,consNo){
	tid_GatherTaskCompile_Flag = true;
	tid_GatherTaskCompile_TmnlAssetNo = tmnlAssetNo;
	tid_GatherTaskCompile_consNo = consNo;
	openTab("采集任务编制", "./baseApp/dataGatherMan/gatherTaskCompile.jsp",false,"GatherTaskCompile");
}
//---------------------------------链接到采集任务编制查询-----------------------end