function openTabMainExcepAnalysis(excepCategory) {
    excepCategoryNo = excepCategory;
	openTab("主站异常分析", "./runMan/runStatusMan/mainExcepAnalysis.jsp");
	return false;
}
function openTabTmnlRunStatus(tmnlExcepCategory){
	tmnlExceptionCategory = tmnlExcepCategory;
	openTab("终端设备运行状态","./runMan/runStatusMan/terminalRunStatus.jsp");
	return false;
}
Ext.onReady(function(){
	var store21 = new Ext.data.JsonStore({
		url:'./runman/abnormalhandle/exceptionMonitor!queryData.action',
		root : 'tewb',
		fields:['terminalMainNotCon',
		        'terminalNotReport',
		        'terminalAmmeterNotCon',
		        'terminalSimNotSame',
		        'terminalNotSend',
		        'simFlowOver',
		        'terminalWarn',
		        'stopTerminalHasFlow'
		        ]
	});
	var store22 = new Ext.data.JsonStore({
		url:'./runman/abnormalhandle/exceptionMonitor!queryTerminalEvent.action',
		root:'telc',
		fields:[
		        'level1count',
		        'level2count',
		        'level3count'
		        ]
	});
    
    var storeFailure = new Ext.data.JsonStore({
    	url:'./runman/abnormalhandle/exceptionMonitor!queryFailureStat.action',
    	root:'failureStat',
    	fields:['fkTmnNewF',
    	        'jcTmnNewF',
    	        'tmnChangeF',
    	        'recordSynF',
    	        'tmnBackoutF',
    	        'backoutAmmeterF',
    	        'changeAmmeterF']
    });

    var gstqstore = new Ext.data.Store({

			proxy : new Ext.data.HttpProxy({
						url : './runman/abnormalhandle/exceptionMonitor!queryGstq.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'lmb',
						fields:[
							    'tqName',
							    'tqLostRate', 
							    'tqSuccRate'
							]
					})
       });

   	var gstqColumn = new Ext.grid.ColumnModel([
   	       {
           header : '台区名称',
           sortable : true,
           dataIndex : 'tqName'
        }, {
           header : "线损率",
           sortable : true,
           dataIndex : 'tqLostRate'
        }, {
	       header : "抄表成功率",
	       sortable : true,
	       dataIndex : 'tqSuccRate'
	    }]);

//	    var grid1 = new Ext.grid.GridPanel({
////	    	border:false,
//	    	columnWidth:.5,
//	    	height:146,
//	    	store: gstqstore,
//	        cm:gstqColumn,
//	        stripeRows: true,
//	        autoExpandColumn: 'tqName',
//	        title : '高损台区',
////	        region:'east',
//	        stateful: true,
//	        stateId: 'grid1'
//	    });
	    gstqstore.load();
	      
	    var gsxlstore = new Ext.data.Store({
	    	proxy : new Ext.data.HttpProxy({
				url : './runman/abnormalhandle/exceptionMonitor!queryGsxl.action'
			}),
	        reader : new Ext.data.JsonReader({
				root : 'lmb2',
				fields:[
					    'xlName',
					    'xlLostRate', 
					    'xlSuccRate'
					]
			})
	    });
	    var gsxlColumn = new Ext.grid.ColumnModel([
	        {
	        header : '线路名称',
	        sortable : true,
	        dataIndex : 'xlName'
	        }, {
	        header : "线损率",
	        sortable : true,
	        dataIndex : 'xlLostRate'
	        }, {
	        header : "抄表成功率",
	        sortable : true,
	        dataIndex : 'xlSuccRate'
	        }]);



//		 var grid2 = new Ext.grid.GridPanel({
////		 border:false,
//		 columnWidth:.5,
//		 height:146,
//		 store: gsxlstore,
//		 cm:gsxlColumn,
//		 stripeRows: true,
//		 autoExpandColumn: 'xlName',
//		 title : '高损线路',
////		 region:'center',
//		 stateful: true,
//		 stateId: 'grid2'
//		 });
	    gsxlstore.load();
	    
    	var storeElecExcep = new Ext.data.JsonStore({
		url:'./runman/abnormalhandle/exceptionMonitor!queryStoreElecExcep.action',
		root : 'storeElecExcep',
		fields:['voltageLost',
		        'ammeterStop',
		        'ctShortAnalysis',
		        'voltageCut',
		        'dataExcep',
		        'overLoadRun',
		        'powerExcep',
		        'dayUseChange',
		        'eCurrentExcep',
		        'voltageOver'
		        ]
	});
	
//var abc='openTab(\'主站异常分析\', \'./runMan/runStatusMan/mainExcepAnalysis.jsp\');return false;';
var s0='00100';
var s1='00101';
var s2='00102';
var s3='00103';
var s4='00104';
var s6='00106';
var s7='00107';
var s8='00108';
var s='';

//var str=	[]
//	    	alert(str.join(""));
	var ceem = new Ext.Template(
	
	
			'<table align="center">',
	    	'<tr>',
	    	'<td width="300"><font size="-1">电压缺相</font></td>',
	    	'<td width="100"><font size="-1"><a href="#" onclick="return openTabMainExcepAnalysis(\''+s0+'\');"><font color="red">{voltageLost}</font></a>条</font></td>',
	    	'<td width="300"><font size="-1">电能表停走</font></td>',
	    	'<td width="100"><font size="-1"><a href="#" onclick="return openTabMainExcepAnalysis(\''+s6+'\');"><font color="red">{ammeterStop}</font></a>条</font></td>',
	    	'<td width="300"><font size="-1">CT二次侧短路可信度分析</font></td>',
	    	'<td width="100"><font size="-1"><a href="#" onclick="return openTabMainExcepAnalysis(\''+s+'\');"><font color="red">{ctShortAnalysis}</font></a>条</font></td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">电压断相</font></td>',
	    	'<td><font size="-1"><a href="#" onclick="return openTabMainExcepAnalysis(\''+s1+'\');"><font color="red">{voltageCut}</font></a>条</font></td>',
	    	'<td><font size="-1">抄表数据异常</font></td>',
	    	'<td><font size="-1"><a href="#" onclick="return openTabMainExcepAnalysis(\''+s7+'\');"><font color="red">{dataExcep}</font></a>条</font></td>',
	    	'<td><font size="-1">变压器超负荷运行</font></td>',
	    	'<td><font size="-1"><a href="#" onclick="return openTabMainExcepAnalysis(\''+s+'\');"><font color="red">{overLoadRun}</font></a>条</font></td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">功率异常</font></td>',
	    	'<td><font size="-1"><a href="#" onclick="return openTabMainExcepAnalysis(\''+s3+'\');"><font color="red">{powerExcep}</font></a>条</font></td>',
	    	'<td><font size="-1">用户日电量突变</font></td>',
	    	'<td><font size="-1"><a href="#" onclick="return openTabMainExcepAnalysis(\''+s8+'\');"><font color="red">{dayUseChange}</font></a>条</font></td>',
	    	'<td>&nbsp;</td>',
	    	'<td>&nbsp;</td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">电流异常</font></td>',
	    	'<td><font size="-1"><a href="#" onclick="return openTabMainExcepAnalysis(\''+s4+'\');"><font color="red">{eCurrentExcep}</font></a>条</font></td>',
	    	'<td><font size="-1">电压超上限</font></td>',
	    	'<td><font size="-1"><a href="#" onclick="return openTabMainExcepAnalysis(\''+s2+'\');"><font color="red">{voltageOver}</font></a>条</font></td>',
	    	'<td>&nbsp;</td>',
	    	'<td>&nbsp;</td>',
	    	'</tr>',
	    	'</table>'		
	
	
	);
	

	
	var panelElecExcep = new Ext.Panel({
            	
            	border:false,
            	title:'用电异常监测'
//            	items:[ceem]
            });
    var gridpanel1 = new Ext.grid.GridPanel({       	
	    	            height:146,
	    	            store: gstqstore,
	                    cm:gstqColumn,
	                    stripeRows: true,
	                    autoExpandColumn: 'tqName',
	                    title : '高损台区',
	                    stateful: true,
	                    stateId: 'grid1'
	             });
	var gridpanel2 = new Ext.grid.GridPanel({
                 	    title : '高损线路',
		                height:146,
		                store: gsxlstore,
		                cm:gsxlColumn,
		                stripeRows: true,
		                autoExpandColumn: 'xlName',
		                stateful: true,
		                stateId: 'grid2'
		          });
	    
    var gridPanel = new Ext.Panel({
            	height:200,
            	border:false,
            	layout:'column',
                title: '线损监测',
                items:[
                    new Ext.Panel({
                       border:false,
                       columnWidth:.5,
                       items:[gridpanel1]
                    })
                ,
                    new Ext.Panel({
                       border:false,
                       columnWidth:.5,
                       items:[gridpanel2]
                    })
                ]
            });
            
    var tabs1 = new Ext.TabPanel({
    	border:false,
    	height:200,
        activeTab:0,
        columnWidth:1,
        items:[  
            panelElecExcep,
            
            gridPanel 
            
        ]
    });
    
    storeElecExcep.load();
    storeElecExcep.on('load', loadstoreElecExcep);
    
	function loadstoreElecExcep() {
		ceem.overwrite(panelElecExcep.body, storeElecExcep.getAt(storeElecExcep.getTotalCount()-1).data,true);
	}
	
    var first_level = new Ext.Panel({
    	border:false,
    	height:200,
    	columnWidth:1,
    	tbar:[ 
                       {xtype: 'label',html:'用电异常及线损监控'}, 
                       {xtype:'tbfill'},  
                        new Ext.Button({
                        text: '刷新'})
                    ],
    	items:[
                    tabs1
    	       ]
    });

    var tmnlExcep0 = '00201';
    var tmnlExcep1 = '00208';
    var tmnlExcep2 = '00202';
    var tmnlExcep3 = '00207';
    var tmnlExcep4 = '00206';
    var tmnlExcep5 = '-1';
    var tmnlExcep6 = '00203';
    var tmnlExcep7 = '00210';
    
    var bookTpl = new Ext.Template(
			'<table  align="center">',
	    	'<tr>',
	    	'<td width="200"><font size="-1">终端与主站不通讯</font></td>',
	    	'<td width="80"><font size="-1"><a href="#" onclick="return openTabTmnlRunStatus(\''+tmnlExcep0+'\');"><font color="red">{terminalMainNotCon}</font></a>条</font></td>',
	    	'<td width="200"><font size="-1">终端没有建档</font></td>',
	    	'<td width="80"><font size="-1"><a href="#" onclick="return openTabTmnlRunStatus(\''+tmnlExcep1+'\');"><font color="red">{terminalNotReport}</font></a>条</font></td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">终端与电表不通讯</font></td>',
	    	'<td><font size="-1"><a href="#" onclick="return openTabTmnlRunStatus(\''+tmnlExcep2+'\');"><font color="red">{terminalAmmeterNotCon}</font></a>条</font></td>',
	    	'<td><font size="-1">终端SIM卡不一致</font></td>',
	    	'<td><font size="-1"><a href="#" onclick="return openTabTmnlRunStatus(\''+tmnlExcep3+'\');"><font color="red">{terminalSimNotSame}</font></a>条</font></td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">终端无任务上送</font></td>',
	    	'<td><font size="-1"><a href="#" onclick="return openTabTmnlRunStatus(\''+tmnlExcep4+'\');"><font color="red">{terminalNotSend}</font></a>条</font></td>',
	    	'<td><font size="-1">SIM卡流量超标</font></td>',
	    	'<td><font size="-1"><a href="#" onclick="return openTabTmnlRunStatus(\''+tmnlExcep5+'\');"><font color="red">{simFlowOver}</font></a>条</font></td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">终端告警过多</font></td>',
	    	'<td><font size="-1"><a href="#" onclick="return openTabTmnlRunStatus(\''+tmnlExcep6+'\');"><font color="red">{terminalWarn}</font></a>条</font></td>',
	    	'<td><font size="-1">停运终端有流量</font></td>',
	    	'<td><font size="-1"><a href="#" onclick="return openTabTmnlRunStatus(\''+tmnlExcep7+'\');"><font color="red">{stopTerminalHasFlow}</font></a>条</font></td>',
	    	'</tr>',
	    	'</table>'
	);
    var panel21 = new Ext.Panel({
    	title:'终端异常工况'
    });
    store21.load();
    store21.on('load', loadBookTplData);
    
	function loadBookTplData() {
		bookTpl.overwrite(panel21.body, store21.getAt(store21.getTotalCount()-1).data,true);
	}
    
	var levelCount = new Ext.Template(
			'<table  align="center">',
	    	'<tr>',
	    	'<td width="200"><font size="-1">严重事件</font></td>',
	    	'<td width="80"><font size="-1"><font color="red">{level1count}</font>条</font></td>',
	    	'<td width="200">&nbsp;</td>',
	    	'<td width="80">&nbsp;</td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">次要事件</font></td>',
	    	'<td><font size="-1"><font color="red">{level2count}</font>条</font></td>',
	    	'<td>&nbsp;</td>',
	    	'<td>&nbsp;</td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">一般事件</font></td>',
	    	'<td><font size="-1"><font color="red">{level3count}</font>条</font></td>',
	    	'<td>&nbsp;</td>',
	    	'<td>&nbsp;</td>',
	    	'</tr>',
	    	'</table>'		
    );
  

	var panel22 = new Ext.Panel({
		title:'终端事件',
        listeners:{activate: loadLevelCount}
	});
	store22.load();
	function loadLevelCount(tab){
		levelCount.overwrite(panel22.body,store22.getAt(0).data,true);
	}
	
    var panelSecondLeft = new Ext.Panel({
    	height:500,
//    	border:false,
    	columnWidth:.5,
    	layout:"border",
    	tbar:[ 
                       {xtype: 'label',html:'终端异常工况及事件'}, 
                       {xtype:'tbfill'},  
                        new Ext.Button({
                        text: '刷新'})
                    ],
    	items:[    
                    new Ext.TabPanel({
                    	border:false,
                        activeTab: 0,
                        region:"center",
                        items:[panel21,panel22]
                    })
                    
    	       ]
    });
    var falureStatistic = new Ext.Template(

			'<table  align="center">',
	    	'<tr>',
	    	'<td width="200"><font size="-1">负控终端新装失败</font></td>',
	    	'<td width="80"><font size="-1"><font color="red">{fkTmnNewF}</font>条</font></td>',
	    	'<td width="200"><font size="-1">集抄终端新装失败</font></td>',
	    	'<td width="80"><font size="-1"><font color="red">{jcTmnNewF}</font>条</font></td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">终端更换失败</font></td>',
	    	'<td><font size="-1"><font color="red">{tmnChangeF}</font>条</font></td>',
	    	'<td><font size="-1">档案同步失败</font></td>',
	    	'<td><font size="-1"><font color="red">{recordSynF}</font>条</font></td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">终端拆除失败</font></td>',
	    	'<td><font size="-1"><font color="red">{tmnBackoutF}</font>条</font></td>',
	    	'<td><font size="-1">拆电能表失败</font></td>',
	    	'<td><font size="-1"><font color="red">{backoutAmmeterF}</font>条</font></td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">换电能表失败</font></td>',
	    	'<td><font size="-1"><font color="red">{changeAmmeterF}</font>条</font></td>',
	    	'<td></td>',
	    	'<td></td>',
	    	'</tr>',
	    	'</table>'
	);
    
    var panel23 = new Ext.Panel({
    	border:false,
    	title:'终端装接调试'
    	
    });
    storeFailure.load();
    storeFailure.on('load', loadF);
	function loadF() {
		falureStatistic.overwrite(panel23.body, storeFailure.getAt(0).data,true);
	}
	
    var panel24 = new Ext.Panel({
    	border:false,
    	title:'数据发布'
    });
    
    var panelSecondRight = new Ext.Panel({
    	height:500,
//    	border:false,
    	columnWidth:.5,
    	layout:"border",
    	tbar:[ 
                       {xtype: 'label',html:'终端装接及数据发布异常'}, 
                       {xtype:'tbfill'},  
                        new Ext.Button({
                        text: '刷新'})
                    ],
    	items:[     
                    new Ext.TabPanel({
                        activeTab: 0,
                        border:false,
                        region:"center",
                        items:[panel23,panel24]
                    })
                    
    	       ]
    });
    
    var second_level = new Ext.Panel({
    	border:false,
    	layout:'column',
    	height:200,
    	items:[panelSecondLeft,panelSecondRight]
    });

    var bookTmp31 = new Ext.XTemplate(
			'<table  align="center">',
	    	'<tr>',
	    	'<td width="200"><font size="-1">存在尖电量</font></td>',
	    	'<td width="80"><font size="-1">0条</font></td>',
	    	'<td width="200"><font size="-1">有负荷无抄表数据</font></td>',
	    	'<td width="80"><font size="-1">0条</font></td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">电流数据异常</font></td>',
	    	'<td><font size="-1">0条</font></td>',
	    	'<td>&nbsp;</td>',
	    	'<td>&nbsp;</td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">电压数据异常</font></td>',
	    	'<td><font size="-1">0条</font></td>',
	    	'<td>&nbsp;</td>',
	    	'<td>&nbsp;</td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">功率数据异常</font></td>',
	    	'<td><font size="-1">0条</font></td>',
	    	'<td>&nbsp;</td>',
	    	'<td>&nbsp;</td>',
	    	'</tr>',
	    	'</table>'
	    	
	);
	
    
    var panel31 = new Ext.Panel({
    	border:false,
    	title:'数据异常'
    });
    
    var bookTmp32 = new Ext.Template(
			'<table  align="center">',
	    	'<tr>',
	    	'<td width="200"><font size="-1">CPU超负荷告警</font></td>',
	    	'<td width="80"><font size="-1">0条</font></td>',
	    	'<td width="200">&nbsp;</td>',
	    	'<td width="80">&nbsp;</td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">磁盘容量告警</font></td>',
	    	'<td><font size="-1">0条</font></td>',
	    	'<td>&nbsp;</td>',
	    	'<td>&nbsp;</td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">内存使用告警</font></td>',
	    	'<td><font size="-1">0条</font></td>',
	    	'<td>&nbsp;</td>',
	    	'<td>&nbsp;</td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">网络流量告警</font></td>',
	    	'<td><font size="-1">0条</font></td>',
	    	'<td>&nbsp;</td>',
	    	'<td>&nbsp;</td>',
	    	'</tr>',
	    	'</table>'
    );
    var panel32 = new Ext.Panel({
    	border:false,
        title: '系统异常'
    });
    
    var panelThirdLeft = new Ext.Panel({
    	height:500,
    	columnWidth:.5,
//    	border:false,
    	activeTab:0,
    	tbar:[ 
                       {xtype: 'label',html:'采集数据及系统异常监控'}, 
                       {xtype:'tbfill'},  
                        new Ext.Button({
                        text: '刷新'})
                    ],
    	items:[
                    new Ext.TabPanel({
                        activeTab: 0,
                        border:false,
                        items:[panel31,panel32]
                    })
                    
    	       ]
    });
    
    var bookTmp33 = new Ext.Template(
			'<table  align="center">',
	    	'<tr>',
	    	'<td width="200"><font size="-1">电表采集异常</font></td>',
	    	'<td width="80"><font size="-1">0条</font></td>',
	    	'<td width="200"><font size="-1">数据缺点</font></td>',
	    	'<td width="80"><font size="-1">0条</font></td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">电压失压</font></td>',
	    	'<td><font size="-1">0条</font></td>',
	    	'<td>&nbsp;</td>',
	    	'<td>&nbsp;</td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">电压断相</font></td>',
	    	'<td><font size="-1">0条</font></td>',
	    	'<td>&nbsp;</td>',
	    	'<td>&nbsp;</td>',
	    	'</tr>',
	    	'<tr>',
	    	'<td><font size="-1">数据跳变</font></td>',
	    	'<td><font size="-1">0条</font></td>',
	    	'<td>&nbsp;</td>',
	    	'<td>&nbsp;</td>',
	    	'</tr>',
	    
	    	'</table>'
	    	
	
	);

    var panel33 = new Ext.Panel({	
        border:false,
        title:'电表异常'
     
        
    });
    var panel34 = new Ext.Panel({ 
    	border:false,
    	title:'终端异常'
    });
    var panel35 = new Ext.Panel({
    	border:false,
    	title:'通道异常'
    });
    
    var panelThirdRight = new Ext.Panel({
        height:500,
//    	border:false,
    	columnWidth:.5,
    	activeTab:0,
    	tbar:[ 
    	               
                       {xtype: 'label',html:'关口异常监测'}, 
                       {xtype:'tbfill'},  
                        new Ext.Button({
                        text: '刷新'})
                    ],
    	items:[  
                    new Ext.TabPanel({
                        activeTab: 0,
                        border:false,
                        items:[panel33,panel34,panel35]
                    })
                    
    	       ]
    });
    
    var third_level = new Ext.Panel({
    	border:false,
    	layout:'column',
    	height:200,
    	items:[panelThirdLeft,panelThirdRight]
    });
    
    var panel = new Ext.Panel({
    	autoScroll:true,
    	height:600,
    	border:false,
    	items:[first_level,second_level,third_level]
    });
    renderModel(panel, '异常监测')
});