
// 顶部面板 图片循环问题？
var headpanel = new Ext.Panel({
	collapseModel : "mini",
	region : "north",
	layout : "table",
	height : 64,
	bodyStyle : "padding:0 0 0 0; background-image:url(./images/background.png);background-repeat: repeat-x;",
	border : false,
	items : [new Ext.Panel({
						width : 880,
						bodyStyle : "background-image:url(./images/logo.png);",
						border : false

					}), new Ext.ux.ImageButton({
						imgPath : './images/baseApp.png',
						imgWidth : 64,
						imgHeight : 64,
						width : 80,
						height : 64,
						iconAlign : 'top',
						tooltip : '基本应用'
					}), new Ext.ux.ImageButton({
						imgPath : './images/advApp.png',
						imgWidth : 64,
						imgHeight : 64,
						width : 80,
						height : 64,
						iconAlign : 'top',
						tooltip : '高级应用'
					}), new Ext.ux.ImageButton({
						imgPath : './images/runMan.png',
						imgWidth : 64,
						imgHeight : 64,
						width : 80,
						height : 64,
						iconAlign : 'top',
						tooltip : '运行管理'
					}), new Ext.ux.ImageButton({
						imgPath : './images/qryStat.png',
						imgWidth : 64,
						imgHeight : 64,
						width : 80,
						height : 64,
						iconAlign : 'top',
						tooltip : '统计查询'
					}), new Ext.ux.ImageButton({
						imgPath : './images/sysMan.png',
						imgWidth : 64,
						imgHeight : 64,
						width : 80,
						height : 64,
						iconAlign : 'top',
						tooltip : '系统管理'
					})]
});

// 左边树 可以考虑用joson异步获取数据
var leftTreepanel = new Ext.tree.TreePanel({
			border : false,
			id : 'netTree',
			rootVisible : true,
			root : new Ext.tree.AsyncTreeNode({
						text : "用电采集",
						expanded : true,
						children : [{
									id : "department",
									text : "海西供电",
									leaf : true
								}, {
									id : "company",
									text : "海南供电",
									leaf : true
								}, {
									id : "permissions",
									text : "西宁供电",
									children : [{
												id : "permission",
												text : "西宁变电所 ",
												children : [{
															text : '拉宁线',
															leaf : true
														}, {
															text : '宁武线',
															leaf : true
														}, {
															text : '拉宁1线',
															leaf : true
														}, {
															text : '宁武1线',
															leaf : true
														}, {
															text : '拉宁2线',
															leaf : true
														}, {
															text : '宁武2线',
															leaf : true
														}, {
															text : '拉宁3线',
															leaf : true
														}, {
															text : '宁武3线',
															leaf : true
														}, {
															text : '拉宁4线',
															leaf : true
														}, {
															text : '宁武4线',
															leaf : true
														}]
											}]
								}]
					})
		});

// Ext.getCmp('netTree').on('click', function treeclick(node, e) {
//
// var name = node.text;
// Ext.Msg.alert(name);
// Ext.getCmp('origFrameQryName').setValue(name);
//
// });
var leftpanel = new Ext.Panel({
			region : "west",
			width : "200",
			items : [leftTreepanel]
		});

// 查询子模块
// var loadQueryPanel = new Ext.Panel({
// labelAlign : 'right',
// height : '300',
// frame : false,
// border : false,
// style : {
// marginTop : '10px',
// marginBottom : '1px',
// marginLeft : '10px'
// },
// layout : 'column',
//
// items : [{
// columnWidth : .30,
// style : {
// marginTop : '1px',
// marginBottom : '1px',
// marginLeft : '1px'
// },
// border : false,
// items : loadDateField
// }, {
// columnWidth : .2,
// border : false,
// style : {
// marginTop : '1px',
// marginBottom : '1px'
// },
// items : [{
// xtype : 'button',
// text : '查询',
// width : 50
// /*
// * handler : function() { genData(); }
// */
// }]
// }]
// });
// ------ 主面板的搭建

var comboFromArray = new Ext.form.ComboBox({
			fieldLabel : '逻辑地址',
			emptyText : 'Select a state...',
			selectOnFocus : true

		});

var origFrameQryPanel = new Ext.Panel({
	id : 'maintab',
	height : 80,
	width : 600,
	layout : "table",
	layoutConfig : {
		columns : 3
	},
	style : "padding:5px",
	items : [{

				layout : "form",
				labelWidth : 40,

				defaultType : "textfield",
				baseCls : "x-plain",
				labelAlign : 'right',
				items : [{
							fieldLabel : "户号<font color='red'>*</font>",
							id : 'origFrameQryID',
							readOnly : true,
							emptyText : '请从左边选择...',
							labelSeparator : "",
							width : 150,
							validator : function(val) {
								if (Ext.isEmpty(val))
									return false;
								else
									return true;
							}
						}]
			}, {
				// colspan : 2,
				layout : "form",

				labelWidth : 40,
				defaultType : "textfield",
				baseCls : "x-plain",
				labelAlign : 'right',
				items : [{
							fieldLabel : "户名",
							readOnly : true,
							id : 'origFrameQryName',
							labelSeparator : "",
							width : 260
						}]
			}, {
				layout : "form",
				labelWidth : 70,
				defaultType : "textfield",
				baseCls : "x-plain",
				labelAlign : 'right',
				items : [comboFromArray]
			}, {
				layout : "form",
				labelWidth : 40,
				// defaultType : "datetimefield",
				baseCls : "x-plain",
				labelAlign : 'right',
				items : [{
							xtype : 'datefield',
							fieldLabel : "从",
							editable : false,
							labelSeparator : "",
							format : 'y-m-d H:i:s',
							value : new Date().clearTime(true),
							width : 150
						}]
			}, {
				layout : "form",
				labelWidth : 40,
				// defaultType : "datetimefield",
				baseCls : "x-plain",
				labelAlign : 'right',
				items : [{
							xtype : 'datefield',
							fieldLabel : "到",
							editable : false,
							labelSeparator : "",
							format : 'y-m-d H:i:s',
							value : new Date().add(Date.DAY, 1).clearTime(true),
							// anchor:'100%'
							width : 150
						}]
			}, {
				layout : "form",
				defaultType : "button",
				baseCls : "x-plain",
				items : [{
							text : "查询",
							width : 70,
							listeners : {
								"click" : function() {
									var CONS = Ext.getCmp("origFrameQryID");
									if (!CONS.isValid(true)) {
										CONS.markInvalid('不能为空');
										return true;
									};

								}
							}
						}]
			}]

});
var queryPanel = new Ext.Panel({
			border : false,
			layout : 'fit',
			region : 'north',
			height : 80,
			items : [origFrameQryPanel]
		});

var cm1 = new Ext.grid.ColumnModel([{
			id : 'orgName',
			header : '供电单位',
			dataIndex : 'orgName',
			align : 'center',
			sortable : true
		}, {
			header : '单位编码',
			dataIndex : 'orgNo',
			align : 'center',
			sortable : true

		}, {
			header : '严重',
			dataIndex : 'serisEvents',
			align : 'center',
			sortable : true

		}, {
			header : '次要',
			dataIndex : 'minorEvents',
			align : 'center',
			sortable : true

		}, {
			header : '一般',
			dataIndex : 'generEvents',
			align : 'center',
			sortable : true
		}]);

var grid1 = new Ext.grid.GridPanel({

			title : '严重级别',
			region : 'center',
			border : false,
			cm : cm1
		});

var mainTablepanel = new Ext.TabPanel({
			border : false,
			region : 'center',
			activeTab : 0,
			items : [grid1]

		});

// 中右主面板
var mainpanel = new Ext.Panel({
			autoScroll : true,
			border : false,
			region : 'center',
			layout : 'border',
			items : [queryPanel, mainTablepanel]
		});
//

// 包装左边树监听
LeftTreeListener = function(config) {

	Ext.apply(this, config);
	LeftTreeListener.superclass.constructor.call(this);
	this.initComponent();
	this.listenLeftTree();
}

Ext.extend(LeftTreeListener, Ext.util.Observable, {
			modelPage : null,
			modelName : null,
			enableEvent : true,
			initComponent : function() {
				var tabpanel = Ext.getCmp('maintab');

				tabpanel.items.each(function(p) {
							p.on('activate', function(p) {
										if (p.getId() == this.modelName) {
											this.enableEvent = true;
										}
									}, this);
							p.on('deactivate', function(p) {
										if (p.getId() == this.modelName) {
											this.enableEvent = false;
										}
									}, this);
							p.on('beforeclose', function(p) {
										if (p.getId() == this.modelName) {
											this.removeListener();
										}
									}, this);
						}, this);

			},

			listenLeftTree : function() {
				Ext.getCmp('netTree').on('click', this.netTreeNodeClick, this);
			},

			netTreeNodeClick : function(node, e) {
				this.nodeClick(Ext.getCmp('netTree'), node, e);
			},

			nodeClick : function(p, node, e) {
				if (this.enableEvent)
					this.processClick(p, node, e);
			},
			processClick : function(p, node, e) {
				var a = p;
				var b = node;
			},
			removeListener : function() {

				Ext.getCmp('netTree').un('click', this.netTreeNodeClick, this);
			}
		});
var treels = new LeftTreeListener({
			modelName : '用户查询',
			processClick : function(p, node, e) {
				treeclick(node, e);
			}
		});
// 点击左边树事件
function treeclick(node, e) {
	var obj = node.attributes.attributes;
	var type = node.attributes.type;
	var name = node.text;
	if (node.isLeaf()) {
		// Ext.getCmp("origFrameQryID").setValue(obj.consNo);
		Ext.getCmp("origFrameQryName").setValue(name);
	} else {
		return true;
	}
};

// ------------

Ext.onReady(function() {
			// 设置顶层的panel
			new Ext.Viewport({
						layout : "border",
						items : [headpanel, leftpanel, mainpanel]
					});
		});