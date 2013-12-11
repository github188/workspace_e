Ext.onReady(function() {

	// 测试数据
	/*
	 * var data = [
	 * 
	 * ['1','male','name1','descn1','2','female','name2','descn2'],
	 * ['2','female','name2','descn2','2','female','name2','descn2'],
	 * ['3','male','name3','descn3','2','female','name2','descn2'],
	 * ['4','female','name4','descn4','2','female','name2','descn2'],
	 * ['5','male','name5','descn5','2','female','name2','descn2'] ];
	 */
	/* 专业类别 */
	var data_zylb = [['01', '计量'], ['02', '电费'], ['03', '95598'], ['04', '抄表'],
			['05', '用检']];
	var store_zylb = new Ext.data.SimpleStore({
				fields : ['value', 'text'],
				data : data_zylb
			});

	var data_dxzz = [['0', '无此权限'], ['1', '读'], ['2', '写']];
	var store_dxzz = new Ext.data.SimpleStore({
				fields : ['value', 'text'],
				data : data_dxzz
			});
	var data_dxzd = [['0', '无此权限'], ['1', '读'], ['2', '写'], ['3', '可写，但需密码确认']];
	var store_dxzd = new Ext.data.SimpleStore({
				fields : ['value', 'text'],
				data : data_dxzd
			});
	/* 角色类型 */
	var data_jslx = [['02', '网省'], ['03', '地市'], ['04', '区县'], ['06', '供电所']];
	var store_jslx = new Ext.data.SimpleStore({
				fields : ['value', 'text'],
				data : data_jslx
			});
	var cm_role = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
						header : '序号',
						width : 35
					}), {
				header : '角色标识',
				dataIndex : 'roleId',
				hidden : true
			}, {
				id : 'roleDesc',
				header : '角色名称',
				dataIndex : 'roleDesc'
			}, {
				header : '专业类别',
				dataIndex : 'fieldTypeCode'
			}, {
				header : '授权角色标志',
				dataIndex : 'roleGrantFlag',
				hidden : true
			}, {
				header : '读写主站数据',
				dataIndex : 'rwStation',
				hidden : true
			}, {
				header : '读写终端数据',
				dataIndex : 'rwTmnl',
				hidden : true
			}, {
				header : '角色级别',
				dataIndex : 'roleLevel'
			}]);

	var ds_role = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './sysman/roleman.action'
						}),
				// proxy: new Ext.data.MemoryProxy(data),
				reader : new Ext.data.JsonReader({
							root : 'root',
							totalProperty : 'totalCount'
						}, [{
									name : 'roleId'
								}, {
									name : 'roleDesc'
								}, {
									name : 'fieldTypeCode'
								}, {
									name : 'roleGrantFlag'
								}, {
									name : 'rwStation'
								}, {
									name : 'rwTmnl'
								}, {
									name : 'roleLevel'
								}])
			});

	/*
	 * grid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
	 * 
	 * Ext.MessageBox.alert("提示","您选择的角色是：" + r.data.roleDesc); });
	 */
	var selectedRow = null;
	var grid = new Ext.grid.GridPanel({
		id : 'grid',
		ds : ds_role,
		cm : cm_role,
		border : false,
		stripeRows : true,
		autoExpandColumn : 'roleDesc',
		region : 'center',
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true,
			listeners : {
				rowselect : function(sm, row, rec) {
					selectedRow = row;
					Ext.getCmp("role-form").getForm().loadRecord(rec);
					var panelMask = new Ext.LoadMask(grid.getId(), {
								msg : "权限菜单加载中..."
							});
					panelMask.show();
					// 加载菜单树上的节点
					menuTree.loader.dataUrl = 'sysman/listmenu!menuTree.action?roleId='
							+ rec.data.roleId;
					menuTree.root.reload();
					menuTree.getRootNode().expand(true, true, function() {
								panelMask.hide();
							});
					// 按钮控制
					Ext.getCmp('addButton_role').setDisabled(true);
					Ext.getCmp('queryButton_role').setDisabled(true);
					Ext.getCmp('delButton_role').setDisabled(false);
					Ext.getCmp('updateButton_role').setDisabled(false);
					Ext.getCmp('backButton_role').setDisabled(false);
				}
			}
		}),
		bbar : new Ext.ux.MyToolbar({
					store : ds_role
				}),
		viewConfig : {
			forceFit : false
		}
	});

	ds_role.load({
				params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
			});
	ds_role.on('load', function(ds_role, rec) {
		grid.getSelectionModel().selectRow(0);
			// Ext.getCmp("role-form").getForm().loadRecord(rec[0]);
		});

	var actionurl = './sysman/dictionary!publicDictionary.action';
	var sql = "sql=SELECT FIELD_TYPE_CODE HIDEVALUE,  FIELD_TYPE_NAME DISPVALUE  FROM VW_FIELD_TYPE_CODE";

	fieldtypeUrl = Ext.urlAppend(actionurl, sql);
	// alert(fieldtypeUrl);
	var fieldTypeStore = new Ext.data.JsonStore({
				url : fieldtypeUrl,
				fields : ['HIDEVALUE', 'DISPVALUE'],
				root : "dicList"
			});

	var fieldTypeCombox = new Ext.form.ComboBox({
				width : 300,
				hideOnSelect : false,
				displayField : 'DISPVALUE',
				valueField : 'HIDEVALUE',
				hiddenName : 'fieldTypeCode',
				maxHeight : 200,
				store : fieldTypeStore,
				mode : 'remote',
				fieldLabel : '专业类别',
				triggerAction : 'all',
				emptyText : '--请选择专业类别--'

			});

	var roleIdSql = "sql= SELECT ORG_TYPE   HIDEVALUE,  ORG_TYPE_NAME DISPVALUE  FROM VW_ORG_TYPE WHERE org_type<> '01' AND org_type <>'05' ";

	var roleIdUrl = Ext.urlAppend(actionurl, roleIdSql);
	// roleLevel
	// 角色级别store

	var roleLevelStore = new Ext.data.JsonStore({
				url : roleIdUrl,
				fields : ['HIDEVALUE', 'DISPVALUE'],
				root : "dicList"
			});

	var roleLevelCombox = new Ext.form.ComboBox({
				width : 300,
				hideOnSelect : false,
				displayField : 'DISPVALUE',
				valueField : 'HIDEVALUE',
				hiddenName : 'roleLevel',
				maxHeight : 200,
				store : roleLevelStore,
				mode : 'remote',
				fieldLabel : '角色级别',
				triggerAction : 'all',
				emptyText : '--角色级别--'

			});

	var form = new Ext.form.FormPanel({
				defaultType : 'textfield',
				labelAlign : "right",
				border : false,
				bodyStyle : 'padding:10px 20px 10px ',
				defaults : {
					width : 200
				},
				// width:300,
				// frame : false,
				id : 'role-form',
				items : [{
							fieldLabel : '角色名称',
							allowBlank : false,
							// anchor:"100%"
							labelSeparator : '',
							id : 'roleDesc',
							emptyText : '--请输入角色名称--',
							blankText : '--请输入角色名称--',
							blankText : '角色名称不能为空!'
						}, fieldTypeCombox,
						/**
						 * { fieldLabel : '专业类别', // allowBlank:false, xtype :
						 * 'combo', labelSeparator : '', id : 'fieldTypeCode',
						 * mode : 'local', store : store_zylb, valueField :
						 * 'value', displayField : 'text', triggerAction :
						 * 'all', emptyText : '--请选择专业类别--', blankText :
						 * '--请选择专业类别--' },
						 */
						{
							fieldLabel : '授权角色标志',
							labelSeparator : '',
							id : 'roleGrantFlag'

						}, {
							xtype : 'combo',
							fieldLabel : '读写主站数据',
							labelSeparator : '',
							id : 'rwStation',
							mode : 'local',
							store : store_dxzz,
							valueField : 'value',
							displayField : 'text',
							triggerAction : 'all',
							emptyText : '--请选择主站数据读写权限--',
							blankText : '--请选择主站数据读写权限--'
						}, {
							fieldLabel : '读写终端数据',
							labelSeparator : '',
							xtype : 'combo',
							id : 'rwTmnl',
							mode : 'local',
							store : store_dxzd,
							valueField : 'value',
							displayField : 'text',
							triggerAction : 'all',
							emptyText : '--请选择终端数据读写权限--',
							blankText : '--请选择终端数据读写权限--'
						}, roleLevelCombox, /**
											 * { fieldLabel : '角色级别',
											 * labelSeparator : '', xtype :
											 * 'combo', id : 'roleLevel', mode :
											 * 'local', store : store_jslx,
											 * valueField : 'value',
											 * displayField : 'text',
											 * triggerAction : 'all', emptyText :
											 * '--请选择角色级别--', blankText :
											 * '--请选择角色级别--' },
											 */
						{
							fieldLabel : '',
							labelSeparator : '',
							// allowBlank : false,
							// anchor:"100%"
							hidden : true,
							id : 'roleId'
							// blankText : '不能为空!'
					}],
				labelWidth : 80
			});

	// 系统菜单树
	var menuRootNode = new Ext.tree.AsyncTreeNode({
		id : '0',
		text : '系统菜单'
			// uiProvider:Ext.ux.TreeCheckNodeUI
		});

	var menuTreeLoader = new Ext.tree.TreeLoader({
		autoScroll : true,
		// 取选中的角色id
		dataUrl : 'sysman/listmenu!menuTree.action'
			// ,
			// baseAttrs : {
			// uiProvider : Ext.ux.TreeCheckNodeUI
			// } // 添加 uiProvider 属性
		});

	var menuTree = new Ext.tree.TreePanel({
				width : 300,
				title : '菜单权限',
				animate : false,
				border : false,
				autoScroll : true,
				checkModel : 'cascade', // 对树的级联多选
				onlyLeafCheckable : false,// 对树所有结点都可选
				root : menuRootNode,
				loader : menuTreeLoader

			});

	var tabs = new Ext.TabPanel({
				activeTab : 0,
				items : [menuTree]
			});

	// 右半部panel
	var panel2 = new Ext.Panel({
				layout : 'border',
				region : 'east',
				width : 350,
				border : false,
				split : true,
				items : [{
							layout : 'fit',
							region : 'center',
							items : [tabs]
						}]
			});

	// 左半部panel
	var panel3 = new Ext.Panel({
		layout : 'border',
		region : 'center',
		border : false,
		items : [{
			xtype : "button",
			text : "新增",
			id : "addButton_role",
			handler : function() {
				var roleName = Ext.getCmp("roleDesc").getValue();
				if (roleName == null || roleName == '') {
					Ext.MessageBox.alert('提示', '请输入角色名！');
					return false;
				}
				// 组成角色菜单字符串
				var menus = '', selNodes = menuTree.getChecked();
				Ext.each(selNodes, function(node) {
							if (menus.length > 0) {
								menus += ',';
							}
							menus += node.id;
						});
				Ext.Ajax.request({
							url : './sysman/roleman!addRole.action',
							params : {
								roleDesc : Ext.getCmp('roleDesc').getValue(),
								fieldTypeCode : Ext.getCmp('fieldTypeCode')
										.getValue(),
								roleGrantFlag : Ext.getCmp('roleGrantFlag')
										.getValue(),
								rwStation : Ext.getCmp('rwStation').getValue(),
								rwTmnl : Ext.getCmp('rwTmnl').getValue(),
								roleLevel : Ext.getCmp('roleLevel').getValue(),
								roleMenus : menus
							},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								if (result.success) {
									if (result.msg) {
										Ext.MessageBox.alert('提示', result.msg);
										return;
									}
									// Ext.MessageBox.alert("提示","添加成功");

									return;
								} else {
									Ext.MessageBox.alert("提示", "添加失败");
									return;
								}
							},
							failure : Failure

						});
			}
		}, {
			region : 'center',
			layout : 'fit',
			items : [grid]

		}, {
			region : 'south',
			layout : 'fit',
			split : true,
			height : 210,
			items : [form],
			buttons : [{
				xtype : "button",
				id : "backButton_role",
				text : "添加",
				disabled : true,
				handler : function() {
					Ext.getCmp('role-form').getForm().reset();

					// 清空树上的选择节点
					menuTree.loader.dataUrl = 'sysman/listmenu!menuTree.action';
					menuTree.root.reload(function(node) {
								node.expand(true);
							});
				}
			}, {
				xtype : "button",
				id : "updateButton_role",
				disabled : true,
				text : "保存",
				handler : function() {
					var menus = '', selNodes = menuTree.getChecked();
					Ext.each(selNodes, function(node) {
								if (menus.length > 0) {
									menus += ',';
								}
								menus += node.id;
							});
					Ext.Ajax.request({
								url : './sysman/roleman!updateRole.action',
								params : {
									roleId : Ext.getCmp('roleId').getValue(),
									roleDesc : Ext.getCmp('roleDesc')
											.getValue(),
									fieldTypeCode : form.getForm()
											.findField('fieldTypeCode')
											.getValue(),
									roleGrantFlag : Ext.getCmp('roleGrantFlag')
											.getValue(),
									rwStation : Ext.getCmp('rwStation')
											.getValue(),
									rwTmnl : Ext.getCmp('rwTmnl').getValue(),
									roleLevel : form.getForm().findField('roleLevel')
											.getValue(),
									roleMenus : menus
								},
								success : function(response) {
									var result = Ext
											.decode(response.responseText);
									if (result.success == false) {
										Ext.MessageBox.alert(
												result.errors.title,
												result.errors.msg);
										return;
									}
									Ext.MessageBox.alert("提示", "保存成功");
									ds_role.load();
									return;

								},
								failure : Failure

							});
				}
			}, '-', {
				xtype : "button",
				id : "delButton_role",
				disabled : true,
				text : "删除",
				handler : function() {
					Ext.Ajax.request({
								url : './sysman/roleman!deleteRole.action',
								params : {
									roleId : Ext.getCmp('roleId').getValue()
								},
								success : function(response) {
									var result = Ext
											.decode(response.responseText);
									if (result.success) {
										Ext.MessageBox.alert("提示", "删除成功");
										ds_role.load();
										return;
									} else {
										Ext.MessageBox.alert("提示", "删除失败");
										return;
									}
								},
								failure : Failure

							});
				}
			}, '-', {
				xtype : "button",
				id : "queryButton_role",
				text : "查询",
				handler : function() {
					ds_role.proxy.conn.url = './sysman/roleman!queryRole.action';
					ds_role.load({
								params : {
									start : 0,
									limit : DEFAULT_PAGE_SIZE,
									roleDesc : Ext.getCmp('roleDesc')
											.getValue()
								}
							});
				}
					/*
					 * function() { Ext.Ajax.request({
					 * url:'../../sysman/roleman!queryRole.action', params : {
					 * roleDesc : Ext.getCmp('roleDesc').getValue(), start : 0,
					 * limit : DEFAULT_PAGE_SIZE }, success : function(response) {
					 * var result = Ext.decode(response.responseText); if
					 * (result.success) { Ext.MessageBox.alert("查询成功");
					 * 
					 * ds_role.load({ params : { start : 0, limit :
					 * DEFAULT_PAGE_SIZE }});
					 * 
					 * return; } else { Ext.MessageBox.alert("查询失败"); return; } },
					 * failure : Failure } )}
					 */
			}],
			buttonAlign : "center"

		}]
	});

	var viewPanel = new Ext.Panel({
				layout : 'border',
				border : false,
				items : [panel2, panel3]
			});

	renderModel(viewPanel, '角色管理');

	Failure = function() {
		Ext.MessageBox.alert("添加失败");
	};

	fieldTypeStore.load();
	roleLevelStore.load();
});