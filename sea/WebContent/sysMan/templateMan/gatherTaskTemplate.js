/**
 * 模块：采集任务模块
 * 
 * @author 张中伟
 * @version 2009年11月22日
 */
Ext.onReady(gatherTaskTemplate());

function gatherTaskTemplate() {

	// 采集任务区构造
	var gTSM = new Ext.grid.CheckboxSelectionModel();
	var gatherTaskGrid = new Ext.grid.ColumnModel([gTSM, {
				header : "任务号",
				sortable : true,
				resizable : true,
				dataIndex : "data1"
			}, {
				header : "测量点",
				sortable : true,
				resizable : true,
				dataIndex : "data2"
			}, {
				header : "任务模板名称",
				sortable : true,
				resizable : true,
				dataIndex : "data3"
			}, {
				header : "查看任务详情",
				sortable : true,
				resizable : true,
				dataIndex : "data3",
				render : renderButton
			}]);

	var gatherStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './sysman/gatherTaskTemplate!listTemplate.action'
						}),
				// proxy : new Ext.data.MemoryProxy(data),
				reader : new Ext.data.JsonReader({
							root : 'root',
							totalProperty : 'totalCount'
						}, [{
									name : 'empNo'
								}, {
									name : 'name'
								}, {
									name : 'ip'
								}, {
									name : 'mac'
								}, {
									name : 'lockTime'
								}])
			});

	var gatherTaskPanel = new Ext.grid.GridPanel({
				title : "任务",
				region : "north",
				autoScroll : true,
				height : 180,
				cm : gatherTaskGrid,
				sm : gTSM,
				ds : gatherStore
			});

	// 关联用户操作区构造
	var uSM = new Ext.grid.CheckboxSelectionModel();
	var userColumn = new Ext.grid.ColumnModel([uSM, {
				header : "户号",
				sortable : true,
				resizable : true,
				dataIndex : "data1",
				width : 100
			}, {
				header : "户名",
				sortable : true,
				resizable : true,
				dataIndex : "data2",
				width : 100
			}, {
				header : "终端资产编号",
				sortable : true,
				resizable : true,
				dataIndex : "data3",
				width : 100
			}, {
				header : "规约",
				sortable : true,
				resizable : true,
				dataIndex : "",
				width : 100
			}, {
				header : "任务数",
				sortable : true,
				resizable : true,
				dataIndex : "",
				width : 100
			}, {
				header : "启用任务",
				sortable : true,
				resizable : true,
				dataIndex : "",
				width : 100
			}, {
				header : "停用任务",
				sortable : true,
				resizable : true,
				dataIndex : "",
				width : 100
			}, {
				header : "终端厂商",
				sortable : true,
				resizable : true,
				dataIndex : "",
				width : 100
			}]

	);

	var userStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './sysman/gatherTaskTemplate!queryUser.action'
						}),
				// proxy : new Ext.data.MemoryProxy(data),
				reader : new Ext.data.JsonReader({
							root : 'root',
							totalProperty : 'totalCount'
						}, [{
									name : 'empNo'
								}, {
									name : 'name'
								}, {
									name : 'ip'
								}, {
									name : 'mac'
								}, {
									name : 'lockTime'
								}])
			});

	var userToolbar = new Ext.Toolbar([{
				text : "删除选中行"
			}, {
				text : "增加新用户"
			}, {
				text : "删除成功用户"
			}]);

	var userGridPanel = new Ext.grid.GridPanel({
				title : '任务相关用户',
				cm : userColumn,
				sm : uSM,
				ds : userStore,
				tbar : userToolbar,
				autoScroll : true
			});

	var userTabPanel = new Ext.TabPanel({
				activeTab : 0,
				region : "center",
				items : [userGridPanel],
				height : 200,
				monitorSize : true,
                doLayout : function() {                    
                    Ext.Panel.prototype.doLayout.call(this);
                }
			});

	// 渲染到主页面
	var gTTPanel = new Ext.Panel({
				layout : "border",
				autoScroll : true,
				renderTo : "gatherTaskTemplate",
				items : [gatherTaskPanel, userTabPanel],
				monitorSize : true,
				doLayout : function() {
					
					Ext.Panel.prototype.doLayout.call(this);
				},
				height : 600
			});
}
// --以上为界面构造-------//

// 应用函数区

function renderButton(value, cellmeta, record, rowIndex, columnIndex, store) {
	var str = "<input type='button' value='详细信息' onclick='alert(\""
			+ "这个单元格的值是：" + value + "\\n" + "这个单元格的配置是：{cellId:"
			+ cellmeta.cellId + ",id:" + cellmeta.id + ",css:" + cellmeta.css
			+ "}\\n" + "这个单元格对应行的record是：" + record.data["id"]
			+ "，一行的数据都在里边\\n" + "这是第" + rowIndex + "行\\n" + "这是第" + columnIndex
			+ "列\\n" + "这个表格对应的Ext.data.Store在这里：" + store + "，随便用吧。" + "\")'>";
	return str;
}

// the end
