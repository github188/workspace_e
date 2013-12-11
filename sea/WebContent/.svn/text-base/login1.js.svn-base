Ext.onReady(init);

function init() {
	Ext.BLANK_IMAGE_URL = './ext3/resources/images/default/s.gif';
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	Ext.QuickTips.init();// 支持tips提示
	Ext.form.Field.prototype.msgTarget = 'qtip';// 提示的方式，枚举值为"qtip","title","under","side",id(元素id)

	var logo = new Ext.Panel({
				region : "north",
				height : 60,
				border : false

			});
	var loginForm = new Ext.FormPanel({
				labelWidth : 100,
				border : false,
				region : "center",
				labelAlign : "right",
				layout : "form",
				labelWidth : 70,
				height : 93,
				modal : true,
				bodyStyle : "padding:10px 10px 10px 10px",
				autoScroll : false,
				items : [{
							xtype : "textfield",
							fieldLabel : "用户工号",
							anchor : "90%",
							name : "staffNo",
							value : "test",
							allowBlank : false,
							blankText : "用户工号不能为空",
							emptyText : "请输入用户工号"
						}, {
							xtype : "textfield",
							fieldLabel : "用户密码",
							anchor : "90%",
							name : "password",
							value : "123",
							allowBlank : false,
							blankText : "用户密码不能为空",
							emptyText : "请输入用户密码",
							inputType : "password"
						}]
			});

	win = new Ext.Window({
				title : "用电信息采集系统用户登录",
				id : "loginWindow",
				width : 300,
				height : 200,
				layout : "border",
				buttonAlign : "center",
				resizable : false,
				closable : false,
				draggable : false,
				plain : false,
				modal : true,
				items : [logo, loginForm],
				buttons : [{
							text : "登录",
							handler : function() {
								var fm = loginForm.getForm();
								fm.submit({
											url : "./sysman/ajaxlogin.action",
											success : function(response, form) {
												Ext.MessageBox.alert("提示",
														"登录成功！");
												win.close();
												window.location = "index.jsp";

											}
										});
							}
						}]
			});

	win.show();

}