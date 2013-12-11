/**
 * 页面：Sim 卡安装情况统计 作者：赵亮 修改：zhangzw
 */
var statcode;
// 打开新的tab
function opencsitab(privateTerminalConvalue) {
	privateTerminalCon = privateTerminalConvalue;
	openTab("客户综合查询", "./qryStat/queryCollPoint/consumerInfo.jsp");

}
Ext.onReady(function() {
	var simQueryparam = null;
	var simLeftTreeListener = new LeftTreeListener({
				modelName : '客户综合查询',
				processClick : function(p, node, e) {
					if (node.isLeaf())
						return;
					var id = node.id;
					var infos = id.split('_');
					if (infos[0] == 'org' || infos[0] == 'sub'
						|| infos[0] == 'line'||infos[0] == 'cgp' || infos[0] =='ugp') {
						if (Ext.getCmp('simLeftTreeName')) {
							Ext.getCmp('simLeftTreeName').setValue(node.text);
							simQueryparam = id;
						}
					}
				}
			});
	// -----------------------------------------------
	// SIM卡安装情况统计-----------------------------------------------------------------
	var sIMInstallStat1 = new Ext.Panel({
		region : 'north',
		height : 35,
		plain : true,
		items : [{
			baseCls : "x-plain",
			layout : "column",
//			style : "padding:5px",
			items : [{
//						columnWidth : .4,// ----------------------
						layout : "form",
						labelWidth : 50,
						style : "padding:5px 0px 0px 40px",
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "节点名",
									id : 'simLeftTreeName',
									allowBlank : false,
									readOnly:true,
									labelStyle : "text-align:right;width:40;",
									// emptyText : '请输入统计地区..',
									// value : "合肥市供电公司",
									labelSeparator : "",
									width : 200
								}]
					},{
//						columnWidth : .4,// ----------------------
						layout : "form",
						labelWidth : 60,
						style : "padding:5px 0px 0px 40px",
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "SIM卡号",
									id : 'simInstallStat_simNo',
									labelStyle : "text-align:right;width:40;",
									// emptyText : '请输入统计地区..',
									// value : "合肥市供电公司",
									labelSeparator : "",
									width : 120
								}]
					}, {
//						columnWidth : .2,// --------------------------
						layout : "form",
						defaultType : "button",
						style : "padding:5px 0px 0px 40px",
						baseCls : "x-plain",
						items : [{
							text : "查询",
							listeners : {
								"click" : function() {
									if (Ext.isEmpty(simQueryparam))
										return;
									var simNo = 	Ext.getCmp('simInstallStat_simNo').getValue();
									sisStore.baseParams = {
										'orgNo' :simQueryparam,
										'simNo' :simNo
									};
									sisStore.load();
								}
							},
							width : 50
						}]
					}]
		}]
	});

	// sIMInstallStatGrid-----------------------------------------------------------------------------------

	var sisStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'qrystat/querySIMStat!querySIMInstallStat.action'
						}),
				// proxy : new Ext.data.MemoryProxy(data),
				baseParams : {
					statusCode : ''
				},
				reader : new Ext.data.JsonReader({
							root : 'simList',
							totalProperty : 'totalCount'
						}, [{
									name : 'orgName'
								}, {
									name : 'consNo'
								}, {
									name : 'consName'
								}, {
									name : 'simNo'
								}, {
									name : 'factoryName'
								},{
									name : 'modeName'
								}, {
									name : 'terminalAddr'
								}, {
									name : 'elecAddr'
								}, {
									name : 'isOnline'
								}])
			});

	var sIMInstallStat2 = new Ext.grid.GridPanel({
				title : "SIM卡安装情况统计",
				region : 'center',
				stripeRows : true,
				autoScroll : true,
				enableColumnMove : false,// 设置表头不可拖动
				cm : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
									header : '序号',
									width : 40,
									align : 'center'
								}), {
							header : '供电单位',
							renderer : function(val) {
								var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
										+ val + '</div>';
								return html;
							}, 
							dataIndex : 'orgName',
							align : 'center'
						}, {
							header : '用户编号',
							dataIndex : 'consNo'
						}, {
							header : '用户名称',
							dataIndex : 'consName',
							renderer : function(s,m,rec) {
								var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + s + '">' +
								"<a href='javascript:'onclick='opencsitab(\""+rec.get('consNo')+"\");" + "'>"+s +'</a></div>';
								return html;
							},
							align : 'center'
						}, {
							header : 'SIM卡号',
							dataIndex : 'simNo',
							renderer : function(s,m,rec) {
								return "<a href='javascript:'onclick='openSimDetailInfo(\""+s+"\");" + "'>"+s +'</a>';
							}
						}, {
							header : '终端地址',
							dataIndex : 'terminalAddr',
							align : 'center'
						}, {
							header : '用电地址',
							dataIndex : 'elecAddr'
						}, {
							header : '终端厂商',
							renderer : function(val) {
								var html = '<div align = "left" ext:qtitle="终端厂商" ext:qtip="' + val + '">'
										+ val + '</div>';
								return html;
							}, 
							dataIndex : 'factoryName',
							align : 'center'
						}, {
							header : '终端型号',
							dataIndex : 'modeName',
							align : 'center'
						}, {
							header : '状态',
							dataIndex : 'isOnline',
							renderer : function(val) {
								if (val == '1')
									return '<font color="green">在线</font>';
								else
									return '<font color="red">离线</font>';
							},
							align : 'center'
						}]),
				ds : sisStore,
				bbar : new Ext.ux.MyToolbar({
							store : sisStore,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部",
							enableExpPage : true, // excel仅导出当前页
							expPageText : "当前页"
						})
			});

	// 设置顶层的SIM卡安装情况统计panel
	var sIMInstallStatpanel = new Ext.Panel({
				autoScroll : true,
				layout : 'border',
				border : false,
				items : [sIMInstallStat1, sIMInstallStat2]
			});

	renderModel(sIMInstallStatpanel, 'SIM卡安装情况统计');
});

function openSimDetailInfo(simNo) {
	staticSimNo = simNo;
	openTab("SIM卡流量信息", "./qryStat/sIMQuery/simDetailInfo.jsp");
}