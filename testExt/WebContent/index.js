var areaRootNode = new Ext.tree.AsyncTreeNode({
			id : 'area_root',
			text : '区域导航',
			iconCls : 'net-02'
		});


var areaTreeLoader = new Ext.ux.tree.PagingTreeLoader({
			autoScroll : true,
			dataUrl : 'areaTree.action'
		});
var areaTree = new Ext.tree.TreePanel({
			width : 300,
			id : 'areaTree', // 其它模块可以直接用 Ext.get('areaTree')取得
			border : false,
			autoScroll : true,
			rootVisible : false,
			root : areaRootNode,
			loader : areaTreeLoader
		});
		
