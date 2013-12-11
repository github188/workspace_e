Ext.onReady(function() {
	// ////////////////////上传文件Panel///////////////////////////

	var uploadField = new Ext.ux.form.FileUploadField({
				emptyText : '请选择要上传的EXCEL',
				fieldLabel : '专变档案文件',
				border : false,
				name : 'upload',
				buttonText : '浏览...',
				buttonCfg : {
				// iconCls : 'upload-icon'
				}
			});
	var uploadForm = new Ext.FormPanel({
		standardSubmit : false,
		fileUpload : true,
		width : 500,
		autoHeight : true,
		border : false,
		bodyStyle : 'padding: 10px 10px 0 10px;',
		labelWidth : 80,
		defaults : {
			anchor : '95%',
			allowBlank : false,
			msgTarget : 'qtip'
		},
		items : [uploadField],
		buttonAlign : 'center',
		buttons : [{
					text : '预览',
					handler : function() {
						uploadForm.getForm().reset();
					}
				}, {
					text : '导入',
					handler : function() {
						if (uploadForm.getForm().isValid()) {
							uploadForm.getForm().submit({
								url : './runman/importprivacy!importPrivacy.action',
								waitMsg : '正在上传...',
								success : function(fp, o) {
									var obj=o.result.errors;
									var msg = o.result.msg;
									if (msg != null && msg != '')
										Ext.MessageBox.alert('提示', msg);
									else if (obj != null)
										Ext.MessageBox.alert(obj.title,obj.msg);
								}
							});
						}
					}
				}]
	});

	var uploadPanel = new Ext.Panel({
				region : 'north',
				height : 100,
				bodyStyle : 'padding: 10px auto;margin:0 auto;',
				// border : false,
				layout : 'column',
				layoutConfig : {
					columns : 3
				},
				items : [new Ext.Panel({
									border : false,
									columnWidth : .5
								}), uploadForm, new Ext.Panel({
									border : false,
									columnWidth : .5
								})]
			});

	// /////////////////////////////////////////////////////////

	// /////////////////////////////数据预览窗口/////////////////
	var previewPanel = new Ext.Panel({
				region : 'center'
			});

	// //////////////////////////////////////////////////////////

	// ////////////// 渲染到主页面 ///////////////////////////////
	var viewPanel = new Ext.Panel({
				border : false,
				layout : 'border',
				items : [uploadPanel, previewPanel]
			});

	// 渲染到主页面
	renderModel(viewPanel, '专变用户档案导入');

});