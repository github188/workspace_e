

Ext.onReady(mainInit);

function mainInit() {

	var swfPanel = new Ext.FlashComponent({
				region : 'center',
				url : 'homepage.swf'

			});

	var mainPanel = new Ext.Panel({
				layout : 'border',
				items : [swfPanel]

			});

	renderModel(mainPanel, '主页',null,null);

}
