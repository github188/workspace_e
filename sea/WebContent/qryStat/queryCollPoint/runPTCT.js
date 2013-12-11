
function getCITRunMessage(value) {
		CITRunStore.baseParams = {
			tFactor : value
		};
		CITRunStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
	};
// runPTCTGrid-----------------------------------------------------------------------------------
	var CITRunStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'qrystat/ccustAction!queryCITRun.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'CITRunList',
					totalProperty : 'totalCount'
				}, [{
							name : 'sortCode'
						}, {
							name : 'phaseCode'
						}, {
							name : 'instLoc'
						}, {
							name : 'instDate'
						}, {
							name : 'currentRatioCode'
						}, {
							name : 'voltRatioCode'
						}, {
							name : 'privFlag'
						}])
	});
	var runPTCTGrid = new Ext.grid.GridPanel({
		 height:215,
		  stripeRows: true,
		//autoHeight : true,
		autoScroll:true,
		enableColumnMove : false,// 设置表头不可拖动
		colModel : new Ext.grid.ColumnModel([{
					header : "序号",
					menuDisabled : true,
					align : "center"
				},// 使下拉框消失
				{
					header : "互感器类别",
					dataIndex : 'sortCode',
					menuDisabled : true,
					align : "center"
				},// 设置标签文字居中
				{
					header : "相别",
					dataIndex : 'phaseCode',
					menuDisabled : true,
					align : "center"
				}, {
					header : "安装位置",
					dataIndex : 'instLoc',
					menuDisabled : true,
					align : "center"
				}, {
					header : "安装日期",
					dataIndex : 'instDate',
					menuDisabled : true,
					align : "center"
				}, {
					header : "电流变化",
					dataIndex : 'currentRatioCode',
					menuDisabled : true,
					align : "center"
				}, {
					header : "电压变比",
					dataIndex : 'voltRatioCode',
					menuDisabled : true,
					align : "center"
				}, {
					header : "是否计量专用",
					dataIndex : 'privFlag',
					menuDisabled : true,
					align : "center"
				}]),
		ds : CITRunStore,
		bbar : new Ext.ux.MyToolbar({
					store : CITRunStore
				})
			// grid分页控件
		}); 
//--------------------------	
	var runpanel = new Ext.Panel({
		//title:"客户资料",
		items:[runPTCTGrid]
	});
	var runPTCTWindow = new Ext.Window({
		title:"运行互感器",
		frame:true,
		width:800,
		height:250,
		layout:"form",
		modal:true,
		closeAction:"hide",
		//labelWidth:45,
		plain:true,//设置背景颜色
		resizable:false,//不可移动
		bodyStyle:"padding:2px",
		draggable:false,
		buttonAlign:"center",//按钮的位置
		//closable:false,//设置窗体关闭按钮
		closeAction:"hide",//将窗体隐藏而并不销毁
		items:[runpanel]
	});
	
	function runPTCTWindowShow(value){
runPTCTWindow.show();
getCITRunMessage(value);
}