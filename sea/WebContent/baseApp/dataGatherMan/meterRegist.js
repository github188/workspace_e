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


Ext.onReady(function(){
	var mr_mr_consId='';	//需要传到后台的用户id
	var mr_TqNameField = new Ext.form.TextField({
		fieldLabel:'台区名称',
		allowBlank : false,
		labelSeparator:'',
		readOnly : true,
		name:'tqName',
		emptyText:'请从左边树选择台区用户',
		anchor:'80%',
		border:false
	});
	
	/*页面上方的查询条件和按钮------------------------------------------------------------*/
	var mr_queryPanel = new Ext.form.FormPanel({
		labelAlign : 'right',
		region:'north',
		height:30,
		bodyStyle:'padding:5px 0px 5px 0px',
		layout : 'column',
		border : false,
		items : [{
					columnWidth : .33,
					border : false,
					layout:'form',
					labelWidth:60,
					items : [mr_TqNameField]
				},{
	                columnWidth:.28,              //label后面加个红色的"*"
	                border:false,
	                layout: 'form',
	                items: [{
						xtype:"label",
						html:'<font color=#ff0000></font>'  
						}]
                },{
	                columnWidth:.1,
	                border:false,
	                layout: 'form',
	                items: [{
							xtype:"button",
							text : '查询',
							width: 60,
							handler:function(){
								if(mr_consId!=null&&mr_consId!='')
									getASMessage();
								else
									Ext.Msg.alert('提示','请从左边树选择要查询的台区用户');
									return true;
							}
						}]
                },{
	                columnWidth:.1,
	                border:false,
	                layout: 'form',
	                items: [{
							xtype:"button",
							text : '远程抄表',
							width: 50,
							hidden : true,
							handler:function(){
								var seconds = 20;
								var ov = true;
								
								h_taskTime(seconds, function() {
									ov = false;
								});
								
								Ext.Ajax.request({
									url : 'baseapp/gatherTaskCompile!callTaskPara.action',
									params : {
										consNo : ''
									},
									success : function(response) {
										if (!ov) {
											return;
										}
										var result = Ext.decode(response.responseText);
										if (!result) {
											Ext.Msg.alert('提示', '后台无数据返回');
											return true;
										}
										if (result.msg && result.msg != '') {
											Ext.Msg.alert('提示', result.msg);
											return true;
										}
										
			
										overFlat = true;
									}
								});
							}
						}]
                },{
	                columnWidth:.1,
	                border:false,
	                hidden:true,
	                layout: 'form',
	                items: [{
							xtype:"button",
							text : '重点监测',
							width: 50,
							handler:function(){
								alert('重点监测');
							}
						}]
                }]
	});
	
	/*居民集抄户查询结果Grid-----------------------------------------------------------*/
	var mr_gridSm = new Ext.grid.CheckboxSelectionModel();
	var mr_gridCm = new Ext.grid.ColumnModel([mr_gridSm,
		{header:'供电单位',dataIndex:'orgName',align:'center',sortable:true},
		{header:'用户编号',dataIndex:'consNo',align:'center',sortable:true},
		{header:'用户名称',dataIndex:'consName',align:'center',sortable:true},
		{header:'抄表段号',dataIndex:'mrSectNo',align:'center',sortable:true},
		{header:'集中器资产',dataIndex:'tmnlAssetNo',align:'center',sortable:true},
//		{header:'采集器资产',dataIndex:'assetNo',align:'center',sortable:true},
		{header:'表计资产号',dataIndex:'assetNo',align:'center',sortable:true},
		{header:'注册状态',dataIndex:'regStatus',align:'center',sortable:true,
			renderer : function(val){
               if(val == 1){
                 return '已注册';
               }else{
                 return '未注册';               
               }}
         },
		{header:'通信方式',dataIndex:'commMode',align:'center',sortable:true},
		{header:'生产厂家',dataIndex:'tmnlFactory',align:'center',sortable:true},
		{header:'投运日期',dataIndex:'instDate',align:'center',sortable:true,
			fomart:'Y-m-d H:i:s',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
		{header:'用电地址',dataIndex:'elecAddr',align:'center',sortable:true}
	]);
	var mr_gridStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'baseapp/autoSend!queryAutoSendInfo.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'asQueryList',
					totalProperty : 'totalCount'
				}, [{name : 'orgName'}, {name : 'consNo'},
					{name : 'consName'}, {name : 'mrSectNo'},
					{name : 'tmnlAssetNo'},{name : 'assetNo'},
					{name : 'regStatus'}, {name : 'commMode'},
					{name : 'tmnlFactory'}, {name : 'instDate',type: 'date',dateFormat:'Y-m-d\\TH:i:s' },
					{name : 'elecAddr'}
				])
	});
	var mr_recordGrid = new Ext.grid.GridPanel({
		title:'居民集抄户查询',
		cm : mr_gridCm,
		sm : mr_gridSm,
		store : mr_gridStore,
		stripeRows : true,
		autoWidth : true,	
		autoScroll : true,
		viewConfig : {
				forceFit : false
			},
		bbar : new Ext.ux.MyToolbar({
					store : mr_gridStore
				})
	});
	var mr_gridPanel = new Ext.Panel({
		region:'center',
		monitorResize : true,
		layout:'fit',
		items : [mr_recordGrid],
		border : false
	});
	
	/*监听左边树点击事件------------------------------------------------------------*/
	var mr_treels = new LeftTreeListener({
				modelName : '电表信息管理',
				processClick : function(p, node, e) {
					var obj = node.attributes.attributes;
      				var type = node.attributes.type;
			        if (node.isLeaf()) {
			           	mr_TqNameField.setValue(node.text);
			            mr_gridStore.removeAll();
			            mr_consId = obj.consId;
			        } else {
			            return true;
			        }
				},
				processUserGridSelect : function(cm, row, record) {
					mr_TqNameField.setValue(record.get('consName'));
					mr_gridStore.removeAll();
					mr_consId = record.get('custId');
				},
				processDblClick : function(p, node, e) {
					mrTreeDBClick(node, e);
				}
			});
	//双击左边树事件
	function mrTreeDBClick(node, e){
		var obj = node.attributes.attributes;
        var type = node.attributes.type;
        if (node.isLeaf()) {
            mr_TqNameField.setValue(node.text);
            mr_consId = obj.consId;
           	getASMessage();
        } else {
            return true;
        }
	}
	//查询集抄户结果
	function getASMessage() {
		mr_gridStore.baseParams = {
			consId : mr_consId
		};
		mr_gridStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
	}
	//页面总体渲染------------------------------------------------------------------------------			
	var mr_ViewPanel = new Ext.Panel({
		bodyStyle:'padding:5px 5px 5px 5px',
		layout: 'border' ,
		items: [mr_queryPanel,mr_gridPanel],
		border : false
	});
	renderModel(mr_ViewPanel,'电表信息管理');
});