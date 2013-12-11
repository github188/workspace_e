tree.on('click', function(node, e) {
	if (node.isLeaf()) {
		var centerContainer = Ext.getCmp('center-container');
		var container = Ext.getCmp('manager-center-container');
		if (container) {
			container = new Ext.Panel({
				autoScroll : false,
				id : 'manager-center-container',
				border : false,
				html : '<iframe id="content-iframe" frameborder="no" style="width:100%;height:99.9%"></iframe>'
			});
			centerContainer.add(container);
			centerContainer.doLayout();
		}

		if (node.attributes.url.indexOf('.js') != -1) {
			var url = node.attributes.url;
			Ext.Ajax.request({
						url : url,
						params : {
							id : node.id,
							name : node.text
						},
						success : function(response, options) {
							var source = response.responseText;
							var headerDom = document
									.getElementsByTagName('head').item(0);
							var jsDom = document.createElement('script');
							jsDom.type = 'text/javascript';
							jsDom.language = 'javascript';
							jsDom.defer = true;
							jsDom.text = source;
							headerDom.appendChild(jsDom);
							var portlet;
							portlet = com.easou.portal.portlet.SinglePortlet
									.init(options.params.id,
											options.params.name);
							if (portlet.draggable) {
								portlet.draggable = false;
							}
							if (portlet.tools) {
								portlet.tools = null;
							}
							if (portlet.collapsible) {
								portlet.collapsible = false;
							}
							var vH = document.body.clientHeight;
							portlet.setHeight(vH - 30);
							// portlet.doLayout();
							container.add(portlet);
							centerContainer.doLayout();
						}
					});
		} else {
			Ext.get('content-iframe').dom.src = node.attributes.link + '&node='
					+ node.id;

		}
		return true;
	} else {
		/**
		 * open node by single click,not double click.
		 */
		node.toggle();
	}
});
