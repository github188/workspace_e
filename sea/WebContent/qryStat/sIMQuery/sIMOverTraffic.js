	var querySimOverTrafficInfo = function (){
		var dateFrom = Ext.getCmp("simChargeDateFrom").getValue();
		var dateTo = Ext.getCmp("simChargeDateTo").getValue();
		if(dateFrom >dateTo) {
			Ext.MessageBox.alert("提示","开始时间不能大于结束时间！");
			return;
		}
		dateFrom = dateFrom.format('Y-m');
		dateTo = dateTo.add(Date.MONTH, 1).format('Y-m');
		var orgNo = Ext.getCmp("sIMOverTrafficOrgNo").getValue();
		
		smtgroupStore.baseParams = {
			chargeDateFrom : dateFrom,
			chargeDateTo : dateTo,
			orgNo : orgNo
		}
		smtgroupStore.load();
	}
	function openSimDetailInfo(simNo) {
		staticSimNo = simNo;
		openTab("SIM卡流量信息", "./qryStat/sIMQuery/simDetailInfo.jsp");
	}
// SIM卡流量分析-----------------------------------------------------------------
	var sIMOverTraffic1 = new Ext.Panel({
		border : false,
		plain : true,
		items : [{
			baseCls : "x-plain",
			layout : "column",
			style : "padding:5px",
			items : [{
						columnWidth : .34,// ----------------------
						layout : "form",
						labelWidth : 50,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "节点名<font color='red'>*</font>",
									labelStyle : "text-align:right;width:50;",
									id:'sIMOverTrafficOrgName',
									readOnly:true,
									emptyText : '请选择统计地区...',
									//value:'合肥供电公司',
									labelSeparator : "",
									width : 190
								},{xtype:'hidden',id:'sIMOverTrafficOrgNo'}]
					}, {
						columnWidth : .3,// ----------------------
						layout : "form",
						labelWidth : 80,
						baseCls : "x-plain",
						items : [{
									xtype : "datefield",
									format : "Y-m",
									id : 'simChargeDateFrom',
									editable:false,
									fieldLabel : "统计日期从",
									value : new Date().add(Date.MONTH, -1),
									labelStyle : "text-align:right;width:80px;"
								}]
					}, {
						columnWidth : .25,
						layout : "form",
						labelWidth : 15,
						baseCls : "x-plain",
						items : [{
									xtype : "datefield",
									format : "Y-m",
									editable:false,
									id : 'simChargeDateTo',
									fieldLabel : "到",
									value : new Date(),
									labelStyle : "text-align:right;width:15px;"
								}]
					}, {
						columnWidth : .1,// --------------------------
						layout : "form",
						defaultType : "button",
						baseCls : "x-plain",
						items : [{
									text : "查询",
									listeners : {
										"click": function (){									
												querySimOverTrafficInfo();
										}
									},
									width : 50
								}]
					}]
		}]
	});
	// ------------------------------------------------------------------------------------------
	// SIM卡流量分析grid
	var smtgroupStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/querySIMStat!querySIMOverTraffic.action'
					}),
			baseParams : {chargeDateFrom : '', chargeDateTo : '',orgNo:''},
			reader : new Ext.data.JsonReader({
						root : 'simOverList',
						totalProperty : 'totalCount'
					}, [{name:'orgNo'},
						{name:'flowDate'},
						{name:'consNo'},
						{name:'consName'},
						{name:'simNo'},
						{name:'cisAssetNo'},
						{name:'terminalAddr'},
						{name:'factoryName'},
						{name:'allFlow'},
						{name:'overFlow'},
						{name:'overFee'}])
			});	

	var groupCM = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer({header : '序号',width : 40,align : 'center'}),
			{header : '单位编号',dataIndex : 'orgNo',sortable : true,hidden:true,align : 'center'}, 
			{header : '统计日期',dataIndex : 'flowDate',sortable : true,	align : 'center'},
			{header : '用户编号',dataIndex : 'consNo',sortable : true,align : 'center'	},
			{header : '用户名称',dataIndex : 'consName',sortable : true,renderer : function(val) {
					var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">'
							+ val + '</div>';
					return html;
				},align : 'center'},
			{header : 'SIM卡号',dataIndex : 'simNo',sortable : true,renderer : function(s,m,rec) {
					return "<a href='javascript:'onclick='openSimDetailInfo(\""+s+"\");" + "'>"+s +'</a>';
				},align : 'center'}, 
			{header : '终端资产编号',dataIndex : 'cisAssetNo',	sortable : true,align : 'center'}, 
			{header : '终端地址',dataIndex : 'terminalAddr',sortable : true,align : 'center'},
			{header : '终端厂商',dataIndex : 'factoryName',sortable : true,align : 'center'},
			{header : '总流量(M)',dataIndex : 'allFlow',sortable : true,renderer : function(val) {
					return '<div align = "right">'+ val + '</div>';
				},align : 'center'},
			{header : '超流量(M)',dataIndex : 'overFlow',sortable : true,renderer : function(val) {
					return '<div align = "right">'+ val + '</div>';
				},align : 'center'}, 
			{header : '超流量费(元)',dataIndex : 'overFee',sortable : true,renderer : function(val) {
					return '<div align = "right">'+ val + '</div>';
				},align : 'center'}]);
	var sIMOverTraffic4 = new Ext.grid.GridPanel({
				//height : 120,
				region:'center',
				autoScroll : true,
				stripeRows : true,
				cm : groupCM,
				ds : smtgroupStore,
				bbar : new Ext.ux.MyToolbar({
							store : smtgroupStore,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部",
							enableExpPage : true, // excel仅导出当前页
							expPageText : "当前页"
						})
			});
		var sotpanel = new Ext.Panel({
				autoScroll : true,
				region:'north',
				height:41,
				items : [sIMOverTraffic1]
			});
			
Ext.onReady(function() {			
	// 设置顶层的地区电量分布panel
	var sIMOverTrafficpanel = new Ext.Panel({
				autoScroll : true,
				layout:'border',
				items : [sotpanel, sIMOverTraffic4]
			});
	renderModel(sIMOverTrafficpanel,'SIM卡超流量统计');
	
	if(typeof(staticOverTrafficOrgNo) != 'undefined'){
		Ext.getCmp("sIMOverTrafficOrgNo").setValue(staticOverTrafficOrgNo);
		Ext.getCmp("sIMOverTrafficOrgName").setValue(staticOverTrafficOrgName);
		Ext.getCmp("simChargeDateFrom").setValue(staticOverTrafficDateFrom);
		Ext.getCmp("simChargeDateTo").setValue(staticOverTrafficDateTo);
		querySimOverTrafficInfo();
	}
	Ext.getCmp('SIM卡超流量统计').on('activate', function() {
 		if(typeof(staticOverTrafficOrgNo) != 'undefined'){
			Ext.getCmp("sIMOverTrafficOrgNo").setValue(staticOverTrafficOrgNo);
			Ext.getCmp("sIMOverTrafficOrgName").setValue(staticOverTrafficOrgName);
			Ext.getCmp("simChargeDateFrom").setValue(staticOverTrafficDateFrom);
			Ext.getCmp("simChargeDateTo").setValue(staticOverTrafficDateTo);
			querySimOverTrafficInfo();
		}
	});
	
	var simLeftTreeListener = new LeftTreeListener({
		modelName : 'SIM卡超流量统计',
		processClick : function(p, node, e) {
			if (node.isLeaf())
				return;
			var obj = node.attributes.attributes;
			var type = node.attributes.type;
			Ext.getCmp("sIMOverTrafficOrgName").setValue(obj.orgName);
			Ext.getCmp("sIMOverTrafficOrgNo").setValue(obj.orgNo);
		}
	});
});
