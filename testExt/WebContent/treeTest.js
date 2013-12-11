
Ext.onReady(function() {
			// var leftpanel = new Ext.Panel({
			// renderTo : "hello",
			// items : [netTree]
			// });

			var netRootNode = new Ext.tree.AsyncTreeNode({
						id : 'net_root',
						text : '电网导航'
					});

//			var treeLoader = new Ext.tree.TreeLoader({
//						dataUrl : 'http://localhost/testExt/treeData.jsp'
//					})
			var netTree = new Ext.tree.TreePanel({
						renderTo : "helloTree",
						width : 300,
						//id : 'netTree', // 其它模块可以直接用 Ext.get('netTree')取得
						border : false,
						autoScroll : true,
						root : netRootNode,
						rootVisible : false,
						loader: new Ext.tree.TreeLoader({url:"treeData.jsp"})

					});
		});