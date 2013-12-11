
/**
 * 模块名称： 操作员管理
 * 
 * @author 王维
 * @description last modied by zhangzhw
 */
Ext.onReady(function() {
	// 供电单位comboBox 用于userGrid的renderer和 userForm的ComboBox
	var orgStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './sysman/operatorman!orgList.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'orgList'
						}, [{
									name : 'orgNo'
								}, {
									name : 'orgName'
								}])
			});
	//var orgCombo = new Ext.form.ComboBox();

	// 部门 ComboBox 用于userGrid的renderer和 userForm的ComboBox
	var depStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './sysman/operatorman!depList.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'depList'
						}, [{
									name : 'deptNo'
								}, {
									name : 'name'
								}])
			});

	// var depCombo = new Ext.form.ComboBox();
	// 角色树
	var roleRootNode = new Ext.tree.AsyncTreeNode({
		id : 'role_root',
		expanded : true,
		text : '系统角色'
			// checked : '',
			// uiProvider:Ext.ux.TreeCheckNodeUI
		});

	var roleTreeLoader = new Ext.tree.TreeLoader({
		autoScroll : true,
		// 取选中的角色id
		dataUrl : './sysman/roleman!roleTree.action'
			// baseAttrs: { uiProvider: Ext.ux.TreeCheckNodeUI } //添加 uiProvider
			// 属性
		});

	var roleTree = new Ext.tree.TreePanel({
				width : 300,
				border : false,
				autoScroll : true,
				title : "系统角色权限",
				// checkModel: 'cascade', //对树的级联多选
				// onlyLeafCheckable: false,//对树所有结点都可选
				root : roleRootNode,
				loader : roleTreeLoader
			});
	// roleTree.getRootNode().expand(true);

	// 单位树
	var orgRootNode = new Ext.tree.AsyncTreeNode({
		id : 'org_root',
		expanded : true,
		text : '供电单位'
			// checked : 'checked'
			// uiProvider:Ext.ux.TreeCheckNodeUI
		});

	var orgTreeLoader = new Ext.tree.TreeLoader({
		autoScroll : true,
		// 取选中的单位id
		dataUrl : './sysman/operatorman!orgTree.action'
			// baseAttrs: { uiProvider: Ext.ux.TreeCheckNodeUI } //添加 uiProvider
			// 属性
		});

	var orgTree = new Ext.tree.TreePanel({
				width : 300,
				border : false,
				title : "访问用电单位",
				autoScroll : true,
				// checkModel: 'cascade', //对树的级联多选
				// onlyLeafCheckable: false,//对树所有结点都可选
				root : orgRootNode,
				loader : orgTreeLoader
			});
	// orgTree.getRootNode().expand(true);

	// 用电用户类型树
	var consTypeRootNode = new Ext.tree.AsyncTreeNode({
		id : 'consType_root',
		expanded : true,
		text : '用电用户类型'
			// checked : '',
			// uiProvider:Ext.ux.TreeCheckNodeUI
		});

	var consTypeTreeLoader = new Ext.tree.TreeLoader({
		autoScroll : true,
		// 取选中的角色id
		dataUrl : './sysman/operatorman!consTypeTree.action'
			// baseAttrs: { uiProvider: Ext.ux.TreeCheckNodeUI } //添加 uiProvider
			// 属性
		});

	var consTypeTree = new Ext.tree.TreePanel({
				width : 300,
				border : false,
				autoScroll : true,
				title : "用电用户类型",
				// checkModel: 'cascade', //对树的级联多选
				// onlyLeafCheckable: false,//对树所有结点都可选
				root : consTypeRootNode,
				loader : consTypeTreeLoader
			});
	// consTypeTree.getRootNode().expand(true);

	// 厂家树
	var facRootNode = new Ext.tree.AsyncTreeNode({
		id : 'fac_root',
		expanded : true,
		text : '终端厂家'
			// checked : '',
			// uiProvider:Ext.ux.TreeCheckNodeUI
		});

	var facTreeLoader = new Ext.tree.TreeLoader({
		autoScroll : true,
		// 取选中的角色id
		dataUrl : './sysman/operatorman!facTree.action'
			// baseAttrs: { uiProvider: Ext.ux.TreeCheckNodeUI } //添加 uiProvider
			// 属性
		});

	var facTree = new Ext.tree.TreePanel({
				width : 300,
				border : false,
				autoScroll : true,
				title : "终端厂家权限",
				// checkModel: 'cascade', //对树的级联多选
				// onlyLeafCheckable: false,//对树所有结点都可选
				root : facRootNode,
				loader : facTreeLoader
			});

	// facTree.getRootNode().expand(true);
	// 当前状态
	var data_statusCode = [['01', '正常'], ['00', '锁定'], ['02', '删除']];
	var store_statusCode = new Ext.data.SimpleStore({
				fields : ['value', 'text'],
				data : data_statusCode
			});

	// 访问级别
	var data_accessLevel = [['02', '省级'], ['03', '地市级'], ['04', '区县级'],
			['06', '供电所级']];
	var store_accessLevel = new Ext.data.SimpleStore({
				fields : ['value', 'text'],
				data : data_accessLevel
			});

	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
						width : 20
					}), {
				id : 'staffNo',
				header : '工号',
				sortable : true,
				dataIndex : 'staffNo'
			}, {
				header : "用户名",
				sortable : true,
				dataIndex : 'name'
			}, {
				header : "人员编号",
				sortable : true,
				dataIndex : 'empNo',
				hidden : true
			}, {
				header : '绑定IP',
				sortable : true,
				dataIndex : 'ip',
				hidden : true
			}, {
				header : '部门',
				sortable : true,
				dataIndex : 'deptNo',
				renderer : function(val) {
					var rtn = val;
					if (depStore.getCount() > 0) {
						var rec = depStore.getAt(depStore.find('deptNo', val));
						if (rec)
							rtn = rec.get('name');
					}
					return rtn;
				}
			}, {
				header : '供电单位',
				sortable : true,
				dataIndex : 'orgNo',
				hidden : true,
				renderer : function(val) {
					var rtn = val;
					if (orgStore.getCount() > 0) {
						var rec = orgStore.getAt(orgStore.find('orgNo', val));
						if (rec)
							rtn = rec.get('orgName');
					}
					return rtn;
				}
			}, {
				header : '绑定物理地址 ',
				sortable : true,
				dataIndex : 'mac',
				hidden : true
			}, {
				header : '访问级别 ',
				sortable : true,
				dataIndex : 'accessLevel',
				hidden : true
			}, {
				header : '当前状态 ',
				sortable : true,
				dataIndex : 'curStatusCode',
				hidden : true
			}, {
				header : '密码强度提醒时间 ',
				sortable : true,
				renderer : Ext.util.Format.dateRenderer('Y-m-d'),
				dataIndex : 'pwdRemindTime',
				hidden : true
			}, {
				header : '锁定时间   ',
				sortable : true,
				renderer : Ext.util.Format.dateRenderer('Y-m-d'),
				dataIndex : 'lockTime',
				hidden : true
			}, {
				header : '计划解锁时间 ',
				sortable : true,
				renderer : Ext.util.Format.dateRenderer('Y-m-d'),
				dataIndex : 'planUnlockTime',
				hidden : true
			}, {
				header : '实际解锁时间',
				sortable : true,
				renderer : Ext.util.Format.dateRenderer('Y-m-d'),
				dataIndex : 'unlockTime',
				hidden : true
			}, {
				header : '锁定IP ',
				sortable : true,
				dataIndex : 'lockIp',
				hidden : true
			}, {
				header : '自动解锁标志',
				sortable : true,
				dataIndex : 'autoUnlockFlag',
				hidden : true
			}, {
				header : '锁定原因 ',
				sortable : true,
				dataIndex : 'lockReason',
				hidden : true
			}, {
				header : '解锁人员',
				sortable : true,
				dataIndex : 'unlockEmpNo',
				hidden : true
			}]);

	// 存储系统用户数据
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './sysman/operatorman!queryAllPSysUser.action'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'root'
						}, [{
									name : 'staffNo'
								}, {
									name : 'name'
								}, {
									name : 'empNo'
								}, {
									name : 'orgNo'
								}, {
									name : 'deptNo'
								}, {
									name : 'ip'
								}, {
									name : 'mac'
								}, {
									name : 'accessLevel'
								}, {
									name : 'curStatusCode'
								}, {
									name : 'pwdRemindTime',
									type : 'date',
									dateFormat : 'Y-m-dTH:i:s'
								}, {
									name : 'lockTime',
									type : 'date',
									dateFormat : 'Y-m-dTH:i:s'
								}, {
									name : 'planUnlockTime',
									type : 'date',
									dateFormat : 'Y-m-dTH:i:s'
								}, {
									name : 'unlockTime',
									type : 'date',
									dateFormat : 'Y-m-dTH:i:s'
								}, {
									name : 'lockIp'
								}, {
									name : 'autoUnlockFlag'
								}, {
									name : 'lockReason'
								}, {
									name : 'unlockEmpNo'
								}])
			});

	var selectedRow = null;
	// 系统用户的列表
	var userGrid = new Ext.grid.GridPanel({
		store : store,
		cm : cm,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true,
			listeners : {
				rowselect : function(sm, row, rec) {
					selectedRow = row;
					userForm.getForm().loadRecord(rec);
					// 按钮控制
					// Ext.getCmp('addButton_user').setDisabled(true);
					// Ext.getCmp('queryButton_user').setDisabled(true);
					// Ext.getCmp('delButton_user').setDisabled(false);
					// Ext.getCmp('updateButton_user').setDisabled(false);
					// Ext.getCmp('backButton_user').setDisabled(false);
					// 加载角色树上的节点
					if (roleTree) {
						roleTree.loader.dataUrl = './sysman/roleman!roleTree.action?staffNo='
								+ rec.data.staffNo;
						roleTree.root.reload();
					}
					// 加载厂家树上的节点
					if (facTree) {
						facTree.loader.dataUrl = './sysman/operatorman!facTree.action?staffNo='
								+ rec.data.staffNo;
						facTree.root.reload();
					}
					// 加载用电类型树上的节点
					if (consTypeTree) {
						consTypeTree.loader.dataUrl = './sysman/operatorman!consTypeTree.action?staffNo='
								+ rec.data.staffNo;
						consTypeTree.root.reload();
					}
					// 加载单位节点
					// if (orgTree) {
					// orgTree.loader.dataUrl =
					// './sysman/operatorman!orgTree.action?orgNo='
					// + rec.data.orgNo
					// + '&staffNo='
					// + rec.data.staffNo;
					// orgTree.root.reload();
					// }
				}
			}
		}),
		bbar : new Ext.ux.MyToolbar({
					store : store
				}),
		stripeRows : true,
		// height : 240,
		autoExpandColumn : 'staffNo',
		region : 'center'
	});

	// 构造完成后第一次载入 异步
	orgStore.load();
	depStore.load();
	store.load({
				params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
			});// 加载用户信息

	// var modiRecord = null;

	store.on('load', function(store, recs) {
		// if (!modiRecord)
		userGrid.getSelectionModel().selectRow(0);
			// else {
			// var selectrow = null;
			// for (var i = 0; i < userGrid.getStore().getCount(); i++)
			// if (userGrid.getStore().getAt(i).get('empNo') == modiRecord) {
			// // this.grid.getSelectionModel().selectRow(i); //
			// // 选中找到的行 单选
			// selectrow = i;
			// }
			//
			// if (selectrow)
			// userGrid.getSelectionModel().selectRow(selectrow);
			//
			// }

		});
	// 右方的form面板
	var userForm = new Ext.form.FormPanel({
				id : 'userform',
				labelAlign : 'right',
				split : true,
				region : 'north',
				labelWidth : 60,
				frame : true,
				width : 450,
				height : 290,
				items : [{
					xtype : 'tabpanel',
					plain : true,
					activeTab : 0,
					height : 285,
					defaults : {
						bodyStyle : 'padding:10px',
						ahchor : '95%',
						labelWidth : 100

					},
					items : [{
								xtype : 'panel',
								title : '基本设置',
								layout : 'form',
								border : false,
								bodyStyle : 'padding:5px 5px 5px 5px',
								// autoScroll : true,
								defaults : {
									labelWidth : 100,
									anchor : '95%'
								},
								items : [{
									xtype : 'textfield',
									fieldLabel : '人员编号',
									id : 'empNo',
									hidden : true,
									hideLabel : true
										// allowBlank : false,
										// blankText : '人员编号不能为空'
									}, {
									xtype : 'textfield',
									fieldLabel : '工号',
									name : 'staffNo',
									id : 'staffNo'
										// readOnly : true
									}, {
									xtype : 'textfield',
									fieldLabel : '用户名',
									id : 'name',
									name : 'name',
									allowBlank : false,
									blankText : '用户名不能为空'
								},
										// {
										// xtype : 'textfield',
										// fieldLabel : '供电单位',
										// id : 'orgNo',
										// allowBlank : false,
										// readOnly : true,
										// blankText : '供电单位不能为空',
										// listeners : {
										// 'focus' : function() {
										// orgTreeWindow.show();
										// }
										// }
										//
										// },
										{
											xtype : 'combo',
											fieldLabel : '供电单位',
											id : 'orgNo',
											name : 'orgNo',
											hiddenName : 'orgNo',
											store : orgStore,
											mode : 'remote',
											allowBlank : false,
											valueField : 'orgNo',
											displayField : 'orgName',
											triggerAction : 'all',
											// readOnly : true,
											emptyText : '--点击选择供电单位--',
											blankText : '--供电单位不能为空--',
											listeners : {
												'focus' : function() {
													orgTreeWindow.show();
												}
											}

										},
										// {
										// xtype : 'textfield',
										// fieldLabel : '部门',
										// id : 'deptNo',
										// allowBlank : false,
										// readOnly : true,
										// blankText : '部门不能为空',
										// listeners : {
										// 'focus' : function() {
										// deptTreeWindow.show();
										// }
										// }
										// },
										{
											xtype : 'combo',
											fieldLabel : '部门',
											name : 'deptNo',
											id : 'deptNo',
											hiddenName : 'deptNo',
											store : depStore,
											valueField : 'deptNo',
											displayField : 'name',
											mode : 'remote',
											allowBlank : false,
											triggerAction : 'all',
											// readOnly : true,
											emptyText : '--点击选择部门--',
											blankText : '--部门不能为空--',
											listeners : {
												'focus' : function() {
													deptTreeWindow.show();
												}
											}
										}, {
											xtype : 'combo',
											fieldLabel : '访问级别',
											name : 'accessLevel',
											hiddenName : 'accessLevel',
											mode : 'local',
											store : store_accessLevel,
											valueField : 'value',
											displayField : 'text',
											triggerAction : 'all',
											emptyText : '--请选择访问级别--',
											blankText : '--请选择访问级别--'
										}, {
											xtype : 'combo',
											fieldLabel : '当前状态',
											name : 'curStatusCode',
											hiddenName : 'curStatusCode',
											mode : 'local',
											store : store_statusCode,
											valueField : 'value',
											displayField : 'text',
											triggerAction : 'all',
											emptyText : '--请选择当前状态--',
											blankText : '--请选择当前状态--'
										}]
							}, {
								xtype : 'panel',
								title : '高级设置',
								layout : 'form',
								autoScroll : true,
								defaults : {
									labelWidth : 100,
									anchor : '95%'
								},
								defaultType : 'textfield',
								items : [{
											xtype : 'textfield',
											fieldLabel : '绑定IP',
											id : 'ip'
										}, {
											xtype : 'textfield',
											fieldLabel : '绑定物理地址',
											id : 'mac'

										}, {
											fieldLabel : '锁定IP',
											id : 'lockIp'
										}, {
											xtype : 'datefield',
											fieldLabel : '锁定时间',
											format : 'Y-m-d',
											id : 'lockTime'
										}, {
											xtype : 'datefield',
											fieldLabel : '密码强度提醒时间',
											format : 'Y-m-d',
											id : 'pwdRemindtime'
										}, {
											xtype : 'datefield',
											fieldLabel : '计划解锁时间',
											format : 'Y-m-d',
											id : 'planUnlockTime'
										}, {
											xtype : 'datefield',
											fieldLabel : '实际解锁时间',
											format : 'Y-m-d',
											id : 'unlockTime'
										}, {
											fieldLabel : '自动解锁标志',
											id : 'autoUnlockFlag'
										}, {
											fieldLabel : '锁定原因',
											id : 'lockReason'
										}, {
											fieldLabel : '解锁人员',
											id : 'unlockEmpNo'
										}]
							}],
					buttonAlign : "center",
					buttons : [{
						text : '添加',
						id : 'addButton_user',
						handler : backInfo
							// addInfo
						}, {
						text : '保存',
						id : 'updateButton_user',
						disabled : true,
						handler : saveInfo
					}, {
						text : '删除',
						id : 'delButton_user',
						disabled : true,
						handler : function() {
							var staffNo = userForm.getForm()
									.findField('staffNo').getValue().trim();
							if (staffNo == null || staffNo == '') {
								Ext.Msg.alert('信息', '请选择要删除的用户!');
								return;
							}
							Ext.MessageBox
									.confirm('删除', '是否删除该用户！', deleteUser);
						}
					}, {
						text : '查询',
						id : 'queryButton_user',
						handler : queryUser
					}
					// , {
					// text : '取消',
					// id : 'backButton_user',
					// disabled : true,
					// handler : backInfo
					// }
					]
				}]
			});

	// 根据用户名查询用户
	function queryUser() {
		// if (!userForm.getForm().isValid()) {
		// alert();
		// return;
		// }
		store.proxy.conn.url = './sysman/operatorman!queryAllPSysUserByName.action';
		store.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE,
						name : userForm.getForm().findField('name').getValue()
					}
				});
	}

	// 删除系统用户
	function deleteUser(btn) {
		if (btn == 'no')
			return;
		else
			Ext.Ajax.request({
				url : './sysman/operatorman!deletePSysUser.action',
				params : {
					staffNo : Ext.getCmp('userform').getForm()
							.findField('staffNo').getValue()
				},
				success : function(response) {
					// var result = Ext.decode(response.responseText);
					Ext.MessageBox.alert('提示', '用户删除成功');
					// modiRecord = null;

					store.proxy.conn.url = './sysman/operatorman!queryAllPSysUser.action';
					store.load({
								params : {
									start : 0,
									limit : DEFAULT_PAGE_SIZE
								}
							});
					// userGrid.getSelectionModel().selectFirstRow();

				}

			});

	}

	// 保存系统用户修改信息
	function saveInfo() {
		// 组成角色树选中节点字符串
		var roles = '', selNodes = roleTree.getChecked();
		Ext.each(selNodes, function(node) {
					if (roles.length > 0) {
						roles += ',';
					}
					roles += node.id;
				});
		// 组成厂家树选中节点字符串
		var facs = '', selNodes = facTree.getChecked();
		Ext.each(selNodes, function(node) {
					if (facs.length > 0) {
						facs += ',';
					}
					facs += node.id;
				});
		// 组成用电用户类型树选中节点字符串
		var consTypes = '', selNodes = consTypeTree.getChecked();
		Ext.each(selNodes, function(node) {
					if (consTypes.length > 0) {
						consTypes += ',';
					}
					consTypes += node.id;
				});
		// 组成单位权限树选中节点字符串
		var orgs = '', selNodes = orgTree.getChecked();
		Ext.each(selNodes, function(node) {
					if (orgs.length > 0) {
						orgs += ',';
					}
					orgs += node.id;
				});
		if (userForm.getForm().isValid()) {
			userForm.getForm().submit({
				method : 'post',
				url : './sysman/operatorman!updatePSysUser.action',
				params : {
					// curStatusCode :
					// userForm.getForm().findField('curStatusCode').getValue(),
					roles : roles,
					orgs : orgs,
					facs : facs,
					consTypes : consTypes
				},
				success : function(form, action) {
					Ext.MessageBox.alert('信息', '保存成功   ');
					store.proxy.conn.url = './sysman/operatorman!queryAllPSysUser.action';
					store.load({
								params : {
									start : 0,
									limit : DEFAULT_PAGE_SIZE
								}
							});
				}
			});
		} else {
			Ext.Msg.alert('信息', '请填写完成再提交!');
		}
	}

	// 添加用户信息
	// function addInfo() {
	// // 组成角色树选中节点字符串
	// var roles = '', selNodes = roleTree.getChecked();
	// Ext.each(selNodes, function(node) {
	// if (roles.length > 0) {
	// roles += ',';
	// }
	// roles += node.id;
	// });
	// // 组成厂家树选中节点字符串
	// var facs = '', selNodes = facTree.getChecked();
	// Ext.each(selNodes, function(node) {
	// if (facs.length > 0) {
	// facs += ',';
	// }
	// facs += node.id;
	// });
	// // 组成用电用户类型树选中节点字符串
	// var consTypes = '', selNodes = consTypeTree.getChecked();
	// Ext.each(selNodes, function(node) {
	// if (consTypes.length > 0) {
	// consTypes += ',';
	// }
	// consTypes += node.id;
	// });
	// // 组成单位权限树选中节点字符串
	// var orgs = '', selNodes = orgTree.getChecked();
	// Ext.each(selNodes, function(node) {
	// if (orgs.length > 0) {
	// orgs += ',';
	// }
	// orgs += node.id;
	// });
	// if (userForm.getForm().isValid()) {
	// userForm.getForm().submit({
	// method : 'post',
	// url : './sysman/operatorman!addPSysUser.action',
	// params : {
	// roles : roles,
	// orgs : orgs,
	// facs : facs,
	// consTypes : consTypes
	// },
	// success : function(form, action) {
	// Ext.MessageBox.alert('提示', '添加成功 ');
	// store.proxy.conn.url = './sysman/operatorman!queryAllPSysUser.action';
	// store.load({
	// params : {
	// start : 0,
	// limit : DEFAULT_PAGE_SIZE
	// }
	// });
	//
	// }
	// });
	// } else {
	// Ext.Msg.alert('信息', '请填写完成再提交!');
	// }
	// }

	// 返回按钮
	function backInfo() {
		// Ext.getCmp('addButton_user').setDisabled(false);
		// Ext.getCmp('queryButton_user').setDisabled(false);
		// Ext.getCmp('delButton_user').setDisabled(true);
		// Ext.getCmp('updateButton_user').setDisabled(true);
		// Ext.getCmp('backButton_user').setDisabled(true);
		// Ext.getCmp("staffNo").setValue();
		// Ext.getCmp("name").setValue();
		// Ext.getCmp("empNo").setValue();
		// Ext.getCmp("orgNo").setValue();
		// Ext.getCmp("deptNo").setValue();
		// userForm.getForm().findField('curStatusCode').setValue();
		// Ext.getCmp("ip").setValue();
		// Ext.getCmp("mac").setValue();
		// Ext.getCmp("lockIp").setValue();
		// Ext.getCmp("lockTime").setValue();
		// Ext.getCmp("pwdRemindtime").setValue();
		// Ext.getCmp("planUnlockTime").setValue();
		// Ext.getCmp("unlockTime").setValue();
		// Ext.getCmp("autoUnlockFlag").setValue();
		// Ext.getCmp("lockReason").setValue();
		// Ext.getCmp("unlockEmpNo").setValue();
		Ext.getCmp('userform').getForm().reset(); // 清空表单数据
		// 清空树上的选择节点
		roleTree.loader.dataUrl = './sysman/roleman!roleTree.action';
		roleTree.root.reload();
		facTree.loader.dataUrl = './sysman/operatorman!facTree.action';
		facTree.root.reload();
		consTypeTree.loader.dataUrl = './sysman/operatorman!consTypeTree.action';
		consTypeTree.root.reload();
		orgTree.loader.dataUrl = './sysman/operatorman!orgTree.action';
		orgTree.root.reload();
	}
	// tab页
	// var tabPanel = new Ext.TabPanel({
	// activeTab : 0,
	// // xtype : 'tabpanel',
	// region : 'center',
	// autoScroll : true,
	// enableTabScroll : true,
	// items : [new Ext.Panel({
	// xtype : "panel",
	// title : "用户角色管理",
	// autoScroll : true,
	// items : roleTree
	// }), new Ext.Panel({
	// xtype : "panel",
	// title : "访问用电单位",
	// autoScroll : true,
	// items : orgTree
	// }), new Ext.Panel({
	// xtype : "panel",
	// title : "用电用户类型",
	// autoScroll : true,
	// items : consTypeTree
	// }), new Ext.Panel({
	// xtype : "panel",
	// title : "终端厂家权限",
	// autoScroll : true,
	// items : facTree
	// })]
	// });

	var tabPanel = new Ext.Panel({
				activeTab : 1,
				// xtype : 'tabpanel',
				region : 'center',
				// autoScroll : true,
				layout : 'accordion',
				// enableTabScroll : true,
				items : [roleTree, orgTree, consTypeTree, facTree]
			});

	/* 单位窗口树------------------------------------------------------------------------------- */
	var orgTreeWinRootNode = new Ext.tree.AsyncTreeNode({
				id : '63101',
				text : '单位'
			});
	var orgTreeWinTreeLoader = new Ext.tree.TreeLoader({
				autoScroll : true,
				dataUrl : './sysman/basetree!orgTreeWin.action'
			});
	var orgTreeWin = new Ext.tree.TreePanel({
				width : 330,
				height : 370,
				border : false,
				autoScroll : true,
				root : orgTreeWinRootNode,
				loader : orgTreeWinTreeLoader
			});
	orgTreeWin.on('dblclick', function(node) {
				var obj = new Object();
				obj.orgName = node.text;
				obj.orgNo = node.id;
				Ext.getCmp("orgNo").setValue(obj.orgName + "," + obj.orgNo);
				orgTreeWindow.hide();
			});

	/* 部门窗口树------------------------------------------------------------------------------- */
	var deptTreeWinRootNode = new Ext.tree.AsyncTreeNode({
				id : '63101',
				text : '部门'
			});
	var deptTreeWinTreeLoader = new Ext.tree.TreeLoader({
				autoScroll : true,
				dataUrl : './sysman/basetree!deptTreeWin.action'
			});
	var deptTreeWin = new Ext.tree.TreePanel({
				width : 330,
				height : 370,
				border : false,
				autoScroll : true,
				root : deptTreeWinRootNode,
				loader : deptTreeWinTreeLoader
			});
	deptTreeWin.on('dblclick', function(node) {
				var obj = new Object();
				obj.deptName = node.text;
				obj.deptNo = node.id;

				Ext.getCmp("deptNo").setValue(obj.deptName + "," + obj.deptNo);
				deptTreeWindow.hide();
			});

	/* 单位弹出窗口------------------------------------------------------------------------------- */
	var orgTreeWindow = new Ext.Window({
		title : '选择单位',
		frame : true,
		width : 350,
		height : 450,
		layout : "form",
		modal : true,
		plain : true,// 设置背景颜色
		resizable : false,// 不可移动
		bodyStyle : "padding:2px",
		buttonAlign : "center",// 按钮的位置
		closeAction : "hide",// 将窗体隐藏而并不销毁
		items : [orgTreeWin],
		buttons : [{
			text : '确定',
			handler : function() {
				var obj = new Object();
				if (orgTreeWin.getSelectionModel().getSelectedNode() == null) {
					Ext.MessageBox.alert('提示', '请选择要添加的数据项！');
					return;
				}
				obj.orgName = orgTreeWin.getSelectionModel().getSelectedNode().text;
				obj.orgNo = orgTreeWin.getSelectionModel().getSelectedNode().id;
				Ext.getCmp("orgNo").setValue(obj.orgName + "," + obj.orgNo);
				orgTreeWindow.hide();
			}
		}, {
			text : '关闭',
			handler : function() {
				orgTreeWindow.hide();
			}
		}]
	});

	/* 部门弹出窗口------------------------------------------------------------------------------- */
	var deptTreeWindow = new Ext.Window({
		title : '选择部门',
		frame : true,
		width : 350,
		height : 450,
		layout : "form",
		modal : true,
		plain : true,// 设置背景颜色
		resizable : false,// 不可移动
		bodyStyle : "padding:2px",
		buttonAlign : "center",// 按钮的位置
		closeAction : "hide",// 将窗体隐藏而并不销毁
		items : [deptTreeWin],
		buttons : [{
			text : '确定',
			handler : function() {
				var obj = new Object();
				if (deptTreeWin.getSelectionModel().getSelectedNode() == null) {
					Ext.MessageBox.alert('提示', '请选择要添加的数据项！');
					return;
				}
				obj.deptName = deptTreeWin.getSelectionModel()
						.getSelectedNode().text;
				obj.deptNo = deptTreeWin.getSelectionModel().getSelectedNode().id;
				Ext.getCmp("deptNo").setValue(obj.deptName + "," + obj.deptNo);
				deptTreeWindow.hide();
			}
		}, {
			text : '关闭',
			handler : function() {
				deptTreeWindow.hide();
			}
		}]
	});

	// 包含列表和form的视图
	var rightPanel = new Ext.Panel({
				layout : 'border',
				region : 'east',
				split : true,
				width : 450,
				items : [userForm, tabPanel]
			});

	// 总视图显示到主页
	var viewPanel = new Ext.Panel({
				layout : 'border',
				items : [userGrid, rightPanel],
				border : false
			});

	renderModel(viewPanel, '操作员管理');

		// tabPanel.items.get(1).expand(true);
		// tabPanel.items.get(1).expand(false);
		// tabPanel.items.get(2).expand(true);
		// tabPanel.items.get(2).expand(false);
		// tabPanel.items.get(3).expand(true);
		// tabPanel.items.get(3).expand(false);
		// tabPanel.items.get(0).expand(true);
});