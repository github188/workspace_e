var loadStartDate = new Ext.form.DateField({
			name : "monitorStartDate",
			format : 'Y-m-d',
			allowBlank : false,
			editable : false,
			emptyText : '请选择日期 ...',
			fieldLabel : "从",
			width : "110",
			value : new Date().add(Date.DAY, -6)
		});
var loadEndDate = new Ext.form.DateField({
			name : "monitorEndDate",
			format : 'Y-m-d',
			allowBlank : false,
			editable : false,
			emptyText : '请选择日期 ...',
			fieldLabel : "到",
			width : "110",
			value : new Date()
		});
loadEndDate.on('change', function(df, newValue, oldValue) {
			if (newValue < monitorStartDate.getValue()) {
				monitorEndDate.setValue(oldValue);
				Ext.Msg.alert('提示', '结束日期不能小于开始日期');
			}
		});

var loadDateField = {
	layout : 'column',
	border : false,
	width : 320,
	style : {
		marginTop : '0px',
		marginBottom : '10px',
		marginLeft : '2px',
		marginRight : '10px'
	},
	items : [{
				layout : 'form',
				border : false,
				labelWidth : 20,
				width : '140',
				items : [loadStartDate]
			}, {
				layout : 'form',
				border : false,
				labelWidth : 20,
				width : '140',
				items : [loadEndDate]
			}]
};
// 日期子模块
var loadQueryPanel = new Ext.Panel({
			labelAlign : 'right',
			region : 'north',
			height : '300',
			frame : false,
			border : false,
			style : {
				marginTop : '10px',
				marginBottom : '1px',
				marginLeft : '10px'
			},
			layout : 'column',

			items : [{
						columnWidth : .30,
						style : {
							marginTop : '1px',
							marginBottom : '1px',
							marginLeft : '1px'
						},
						border : false,
						items : loadDateField
					}, {
						columnWidth : .2,
						border : false,
						style : {
							marginTop : '1px',
							marginBottom : '1px'
						},
						items : [{
							xtype : 'button',
							text : '查询',
							width : 50
								/*
								 * handler : function() { genData(); }
								 */
							}]
					}]
		});
var queryPanel1 = new Ext.Panel({
			border : false,
			layout : 'fit',
			region : 'north',
			height : 400,
			items : [loadQueryPanel]
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
			border : false,
			cm : cm1
		});
var mainTablepanel = new Ext.TabPanel({
			border : false,
			region : 'center',
			// activeTab : 0,
			items : [grid1]

		});

Ext.onReady(function() {
			var mainPanel = new Ext.Panel({
						renderTo : "hello",
						layout : "border",
						height : "700",
						border : false,
						items : [queryPanel1,mainTablepanel]

					});

		});