Ext.onReady(function() {
	
	//页面顶部选择类型pannel
var opTypeLosePowerPanel = new Ext.Panel( {
	region : 'north',
	height : 30,
//	width : 400,
//	bodyStyle : 'padding:5px 0px 0px 50px',
	border : false,
	items : [{
		xtype : 'radiogroup',
		id : 'opTypeLosePowerRadio',
		columns : [80, 80],
		items : [ {
			name : 'opTypeLoseP',
			boxLabel : '台区线损',
			checked : true,
			listeners : {
				check : function(checkbox, checked) {
					if (checked) {
						groupPanel.layout.setActiveItem(0);
//						groupPanel.setTitle( orgNoTitle);
					}
				}
			}
		}, {
			name : 'opTypeLoseP',
			boxLabel : '线路线损',
			listeners : {
				check : function(checkbox, checked) {
					if (checked) {
						groupPanel.layout.setActiveItem(1);
//						groupPanel.setTitle( factoryTitle);
					}
				}
			}
		}]
	}]
});
	//线路模型
	var lineCM = new Ext.grid.ColumnModel([{
		header : '线路名称',
		sortable : true,
		align : 'center',
		dataIndex : 'name',
		width : 160,
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="线路名称" ext:qtip="' + val
					+ '">' + val + '</div>';
			return html;
		}
	}, {
		header : '线损率',
		sortable : true,
		width : 80,
		align : 'center',
		dataIndex : 'losePower',
		renderer : function(val) {
			val = val * 100;
			var html = '<div align = "left" ext:qtitle="线损率" ext:qtip="'
					+ Ext.util.Format.number(val, '0,000.00') + '%' + '">'
					+ Ext.util.Format.number(val, '0,000.00') + '%' +'</div>';
			return html;
		}
	}, {
		header : '抄表成功率',
		sortable : true,
		align : 'center',
		dataIndex : 'readSuccRate',
		width : 80,
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="抄表成功率" ext:qtip="'
					+ Ext.util.Format.number(val, '0,000.00') +'%'+ '">' 
					+ Ext.util.Format.number(val, '0,000.00') +'%'+ '</div>';
			return html;
		}
	}, {
		header : '单位名称',
		sortable : true,
		align : 'center',
		dataIndex : 'orgName',
		hidden : true,
		width : 120,
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="单位名称" ext:qtip="'
					+ val + '">' + val + '</div>';
			return html;
		}
	}]);
	//线路线损store
	var lineStore = new Ext.data.Store({
//				autoLoad : true,
				url : 'mydesk/losePowerStatA!queryLineLosePowerStatA.action',
				params : {
					startDate : new Date().add(Date.DAY, -1).format('ymd')
				},
				reader : new Ext.data.JsonReader({
							root : 'losePowerStatALineList'
						}, [{
									name : 'name'
								},{
									name : 'losePower'
								},{
									name : 'readSuccRate'
								}, {
									name : 'readSuccCnt'
								}, {
									name : 'orgNo'
								},{
									name : 'orgName'
								}, {
									name : 'losePId'
								},{
									name : 'readCnt'
								}, {
									name : 'readSuccCnt'
								}])
			});
	//线路线损grid
		var losePowerLineGrid = new Ext.grid.GridPanel({
				cm : lineCM,
				height : 290,
				store : lineStore,
				monitorResize : true,
				autoScroll : true,
				viewConfig : {
					forceFit : false
				}
			})
	//加载线路线损数据		
	lineStore.load({
				params : {
					queryDate : new Date().add(Date.DAY, -1).format('Ymd')
				}
			});
		//台区模型
		var tgCM = new Ext.grid.ColumnModel([{
		header : '台区名称',
		sortable : true,
		align : 'center',
		dataIndex : 'name',
		width : 160,
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="台区名称" ext:qtip="' + val
					+ '">' + val + '</div>';
			return html;
		}
	}, {
		header : '线损率',
		sortable : true,
		width : 80,
		align : 'center',
		dataIndex : 'losePower',
		renderer : function(val) {
			val = val * 100;
			var html = '<div align = "left" ext:qtitle="线损率" ext:qtip="'
					+ Ext.util.Format.number(val, '0,000.00') +'%' + '">'
					+ Ext.util.Format.number(val, '0,000.00') +'%' + '</div>';
			return html;
		}
	}, {
		header : '抄表成功率',
		sortable : true,
		align : 'center',
		dataIndex : 'readSuccRate',
		width : 80,
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="抄表成功率" ext:qtip="'
					+ Ext.util.Format.number(val, '0,000.00') +'%'+ '">' 
					+ Ext.util.Format.number(val, '0,000.00') +'%'+ '</div>';
			return html;
		}
	}, {
		header : '单位名称',
		sortable : true,
		align : 'center',
		dataIndex : 'orgName',
		hidden : true,
		width : 120,
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="单位名称" ext:qtip="'
					+ val + '">' + val + '</div>';
			return html;
		}
	}]);
	
	var tgStore = new Ext.data.Store({
//				autoLoad : true,
				url : 'mydesk/losePowerStatA!queryTGLosePowerStatA.action',
				params : {
					startDate : new Date().add(Date.DAY, -1),
					endDate :  new Date()
				},
				reader : new Ext.data.JsonReader({
							root : 'losePowerStatATGList'
						}, [{
									name : 'name'
								},{
									name : 'losePower'
								},{
									name : 'readSuccRate'
								}, {
									name : 'readSuccCnt'
								}, {
									name : 'orgNo'
								},{
									name : 'orgName'
								}, {
									name : 'losePId'
								},{
									name : 'readCnt'
								}, {
									name : 'readSuccCnt'
								}])
			});
	
		var losePowerTgGrid = new Ext.grid.GridPanel({
				cm : tgCM,
				height : 290,
				store : tgStore,
				monitorResize : true,
				autoScroll : true,
				viewConfig : {
					forceFit : false
				}
			})
	tgStore.load({
				params : {
					queryDate : new Date().add(Date.DAY, -1).format('Ymd')
				}
			});
			
	//使用card样式设置组合grid
	var groupPanel = new Ext.Panel({
		region : 'center',
//		title : '线损率统计明细',
		layout : 'card',
		activeItem : 0,
		border : false,
    	items:[losePowerTgGrid, losePowerLineGrid]
	});
	//线损率列表panel
var viewPanel = new Ext.Panel({
	autoScroll : true,
	region : 'center',
	border : false,
	layout : 'border',
	items : [opTypeLosePowerPanel, groupPanel]
});

	//渲染
	renderModel(viewPanel, '7');
	
		});
