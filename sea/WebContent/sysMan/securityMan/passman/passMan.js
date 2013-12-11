// 自定义Vtype验证----验证密码是否一致
Ext.apply(Ext.form.VTypes, {
			// 验证方法
			password : function(val, field) {// val指这里的文本框值，field指这个文本框组件
				if (field.password.password_id) {
					// password是自定义的配置参数，一般用来保存另外的组件的id值
					var pwd = Ext.get(field.password.password_id);// 取得user_password的那个id的值
					return (val == pwd.getValue());// 验证
				}
				return true;
			},
			// 验证提示错误提示信息(注意：方法名+Text)
			passwordText : "两次密码输入不一致!"

		});

Ext.onReady(function() {

			var form = new Ext.form.FormPanel({
						defaultType : 'textfield',
						labelAlign : "right",
						border : false,
						// bodyStyle : 'padding:100px 20px 10px 10px',
						defaults : {
							width : 200
						},
						bodyStyle : 'margin-left:33%',
						frame : true,// 圆角和浅蓝色背景
						id : 'passmanForm',
						region : 'center',
						items : [{
									fieldLabel : '旧的密码:',
									labelSeparator : '',
									id : 'oldPass',
									name : 'oldPass',
									inputType : 'password'
								}, {
									fieldLabel : '新的密码:',
									labelSeparator : '',
									id : 'pass1',
									inputType : 'password',
									blankText : '不能为空!'
								}, {
									fieldLabel : '确认新的密码:',
									labelSeparator : '',
									id : 'pass2',
									inputType : 'password',
									blankText : '不能为空!',
									password : {
										password_id : 'pass1'
									},
									vtype : 'password'
								}],
						buttons : [{
									text : '确定',
									// id : 'addButton_user',
									handler : updatePass
								}, {
									text : '重置',
									// id : 'addButton_user',
									handler : setPass
								}]
					});

			function updatePass() {
				if (form.getForm().isValid()) {
					form.getForm().submit({
						method : 'post',
						url : './sysman/passman!updatePass.action',
						success : function(form, action) {
							Ext.MessageBox.alert('提示信息',
									'修改密码成功 ！                                ');
						}
					});
				} else {
					Ext.Msg.alert('信息', '请填写完成再提交!');
				}
			}

			function setPass() {
				form.getForm().findField('pass1').setValue();
				form.getForm().findField('pass2').setValue();
			}

			renderModel(form, '密码管理');
		});