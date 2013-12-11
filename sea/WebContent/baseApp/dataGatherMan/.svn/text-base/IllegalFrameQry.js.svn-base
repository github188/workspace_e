//非法报文查询id
var illegalFrameQryID;
var width;

var treels = new LeftTreeListener( {
	modelName : '非法报文查询',
	processClick : function(p, node, e) {
		treeclick(node, e);
	},
	processUserGridSelect : function(cm, row, record) {
		Ext.getCmp("illegalFrameQryID").setValue(record.get('consNo'));
		Ext.getCmp("illegalFrameQryName").setValue(record.get('consName'));
		illegalFrameQryID = record.get('consNo');
		getIllegalFrameQryAsset();
		illegalFrameQryGridStore.removeAll();
		// queryIllegalFrameQry();
}
});
// 点击左边树事件
function treeclick(node, e) {
	var obj = node.attributes.attributes;
	var type = node.attributes.type;
	var name = node.text;
	if (node.isLeaf()) {
		Ext.getCmp("illegalFrameQryID").setValue(obj.consNo);
		Ext.getCmp("illegalFrameQryName").setValue(name);
		illegalFrameQryID = obj.consNo;
		getIllegalFrameQryAsset();
		illegalFrameQryGridStore.removeAll();
	} else {
		return true;
	}
};

// 查询客户对应的终端资产号函数
function getIllegalFrameQryAsset() {
	Ext.Ajax.request( {
		url : 'baseapp/illegalFrameQry!queryIllegalFrameQryAsset.action',
		params : {
			illegalFrameQryID : illegalFrameQryID
		},
		success : function(response) {
			var result = Ext.decode(response.responseText);
			tmnlAssetNostore.loadData(result);
			if (tmnlAssetNostore != null) {
				var terminalAddr = tmnlAssetNostore.getAt(0)
						.get('terminalAddr');
				illegalFrameQryCombo.setValue(terminalAddr);
			}
		}
	});
};
// 查询非法报文
function queryIllegalFrameQry() {
	var terminalAddr = Ext.getCmp("illegalFrameQryAsset").getValue();
	DateStart = Ext.getCmp("illegalFrameQryStart").getValue().format(
			'Y-m-d H:i:s');
	DateEnd = Ext.getCmp("illegalFrameQryEnd").getValue().format('Y-m-d H:i:s');
	illegalFrameQryGridStore.baseParams = {
		terminalAddr : terminalAddr,
		DateStart : DateStart,
		DateEnd : DateEnd
	};
	illegalFrameQryGridStore.load( {
		params : {
			start : 0,
			limit : DEFAULT_PAGE_SIZE
		}
	});
}
//复选框值
var tmnlAssetNostore = new Ext.data.Store( {
	proxy : new Ext.data.MemoryProxy(),
	reader : new Ext.data.JsonReader( {
		root : 'illegalFrameQryAssetList'
	}, [ 'terminalAddr' ])
});
//下拉复选框
var illegalFrameQryCombo = new Ext.form.ComboBox( {
	id : 'illegalFrameQryAsset',
	name : 'illegalFrameQryAsset',
	store : tmnlAssetNostore,
	displayField : 'terminalAddr',
	valueField : 'terminalAddr',
	triggerAction : 'all',
	typeAhead : true,
	mode : 'local',
	fieldLabel : '逻辑地址',
	labelSeparator : '',
	emptyText : '--请选择--',
	blankText : '--请选择--',
	selectOnFocus : true,
	width : 100,
	forceSelection : false,
	validator : function(val) {
		if (Ext.isEmpty(val))
			return false;
		else
			return true;
	}
});
//复选框选择事件
illegalFrameQryCombo.on('select', function(combox) {
	var value = combox.getValue();
	var record = tmnlAssetNostore.getById(value);
});
//复选框变焦事件
illegalFrameQryCombo.on('blur', function(combox) {
	illegalFrameQryClean();
});
//报文查询panel
var illegalFrameQryPanel = new Ext.Panel( {
	plain : true,
	border : false,
	region : 'north',
	height : 60,
	items : [ {
		baseCls : "x-plain",
		layout : "table",
		layoutConfig: {
	        columns: 3
	    },
		style : "padding:5px",
		Width : 600,
		border : false,
		items : [ {
//			columnWidth : .3,
			layout : "form",
			labelWidth : 40,
			defaultType : "textfield",
			baseCls : "x-plain",
			labelAlign : 'right',
			items : [ {
				fieldLabel : "户号",
				id : 'illegalFrameQryID',
				readOnly : true,
				emptyText : '请从左边选择...',
				labelSeparator : "",
				width : 150
			} ]
		}, {
//			columnWidth : .3,
			layout : "form",
			labelWidth : 40,
			defaultType : "textfield",
			baseCls : "x-plain",
			labelAlign : 'right',
			items : [ {
				fieldLabel : "户名",
				id : 'illegalFrameQryName',
				readOnly : true,
//				emptyText : '请从左边选择...',
				labelSeparator : "",
				width : 260
			} ]
		}, {
//			columnWidth : .4,
			layout : "form",
			labelWidth : 70,
			defaultType : "textfield",
			baseCls : "x-plain",
			labelAlign : 'right',
			items : [ illegalFrameQryCombo ]
		}, {
//			columnWidth : .3,
			layout : "form",
			labelWidth : 40,
			defaultType : "datetimefield",
			baseCls : "x-plain",
			labelAlign : 'right',
			items : [ {
				fieldLabel : "从",
				id : 'illegalFrameQryStart',
				labelSeparator : "",
				value : new Date().add(Date.DAY, -1).clearTime(true),
				width : 150
			} ]
		}, {
//			columnWidth : .4,
			layout : "form",
			labelWidth : 40,
			defaultType : "datetimefield",
			baseCls : "x-plain",
			labelAlign : 'right',
			items : [ {
				fieldLabel : "到",
				id : 'illegalFrameQryEnd',
				labelSeparator : "",
				value : new Date().add(Date.DAY, 0),
				width : 150
			} ]
		}, {
//			columnWidth : .1,
			layout : "form",
			defaultType : "button",
			baseCls : "x-plain",
			items : [ {
				text : "查询",
				width : 70,
				listeners : {
					"click" : function() {
						var CONS = Ext.getCmp("illegalFrameQryAsset");
						if (!CONS.isValid(true)) {
							CONS.markInvalid('该项不能为空');
							return true;
						}
						;
						queryIllegalFrameQry();
					}
				}
			} ]
		} ]
	} ]
});
/**
 * 验证是否是输入逻辑地址
 */
function illegalFrameQryClean() {
	Ext.getCmp("illegalFrameQryID").setValue("");
	Ext.getCmp("illegalFrameQryName").setValue("");
}
// 报文查询列表装载数据格式
var illegalFrameQryGridStore = new Ext.data.Store( {
	proxy : new Ext.data.HttpProxy( {
		url : 'baseapp/illegalFrameQry!queryIllegalFrameQry.action'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'illegalFrameQryList',
		totalProperty : 'totalCount'
	}, [ {
		name : 'commTime',
		type : 'date',
		dateFormat : 'Y-m-d\\TH:i:s' // 2009-04-03T00:00:00
	}, {
		name : 'terminalAddr'
	}, {
		name : 'tmnlCode'
	}, {
		name : 'ctrlCode'
	}, {
		name : 'message'
	} ])
});
// 报文查询列表装载模型
var groupCM = new Ext.grid.ColumnModel( [
		{
			header : '时间',
			// width:120,
			dataIndex : 'commTime',
			sortable : true,
			align : 'center',
			fomart : 'Y-m-d H:i:s',
			renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
		},
		{
			header : '逻辑地址',
			// width:80,
			dataIndex : 'terminalAddr',
			sortable : true,
			align : 'center'
		},
		{
			header : '终端区域码',
			// width:80,
			dataIndex : 'tmnlCode',
			sortable : true,
			align : 'center'
		},
		{
			header : '控制码',
			// width:80,
			dataIndex : 'ctrlCode',
			sortable : true,
			align : 'center'
		},
		{
			header : '报文内容',
			width : 400,
			// editor:new Ext.form.TextField(),
			dataIndex : 'message',
			sortable : true,
			align : 'center',
			renderer : function(v) {
				var s = "";
				var vlength = v.length;
				var x = 0;
				while (vlength > 0) {
					s += v.substring(x, x + 45) + "<br>";
					x = x + 45;
					vlength = vlength - 45;
				}
				;
				var html = '<span ext:qtitle="报文内容" ext:qtip="' + s + '">' + v
						+ '</span>';
				return html;
			}
		} ]);

// 报文查询列表Panel
var illegalFrameQryGrid = new Ext.grid.GridPanel( {
	region : 'center',
	title : '报文查询',
	autoScroll : true,
	stripeRows : true,
	viewConfig : {
		forceFit : true
	},
	cm : groupCM,
	ds : illegalFrameQryGridStore,
	bbar : new Ext.ux.MyToolbar( {
		store : illegalFrameQryGridStore,
		enableExpAll : true
	})
});

Ext.onReady(function() {
	// 设置顶层的公变采集点查询panel
		var illegalFrameQryAllPanel = new Ext.Panel( {
			autoScroll : true,
			border : false,
			layout : 'border',
			items : [ illegalFrameQryPanel, illegalFrameQryGrid ]
		});
		renderModel(illegalFrameQryAllPanel, '非法报文查询');

		/*
		 * if(typeof(staticConsNo) != 'undefined' && typeof(staticConsName) !=
		 * 'undefined' && typeof(staticTmnlAssetAddr) != 'undefined'){
		 * Ext.getCmp("illegalFrameQryID").setValue(staticConsNo);
		 * Ext.getCmp("illegalFrameQryName").setValue(staticConsName);
		 * Ext.getCmp("illegalFrameQryAsset").setValue(staticTmnlAssetAddr);
		 * queryIllegalFrameQry(); } Ext.getCmp('非法报文查询').on('activate',
		 * function() { if(typeof(staticConsNo) != 'undefined' &&
		 * typeof(staticConsName) != 'undefined' && typeof(staticTmnlAssetAddr) !=
		 * 'undefined'){ Ext.getCmp("illegalFrameQryID").setValue(staticConsNo);
		 * Ext.getCmp("illegalFrameQryName").setValue(staticConsName);
		 * Ext.getCmp("illegalFrameQryAsset").setValue(staticTmnlAssetAddr);
		 * queryIllegalFrameQry(); } });
		 */

	});
