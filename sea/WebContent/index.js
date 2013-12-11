/**
 * 常量配置 （部分参数从数据库中取得）
 */
DEFAULT_PAGE_SIZE = 50;
DEFAULT_TIMEOUT = 3 * 60 * 1000; // 默认超时

// 请求超时设置为3分钟
Ext.Ajax.timeout = DEFAULT_TIMEOUT;

ALERT_LEVEL = '00';
/**
 * 常量配置结束
 */

Ext.apply(Ext.form.VTypes, {
	alphanum : function(val, field) {
		try {
			if (!/\W/.test(val))
				return true;
			return false;
		} catch (e) {
			return false;
		}
	},
	alphanumText : '请输入英文字母或数字,其它字符不允许的.',

	ip : function(val, field) {
		try {
			if ((/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/
					.test(val)))
				return true;
			return false;
		} catch (e) {
			return false;
		}
	},
	ipText : '请输入正确的IP地址:xx.xx.xx.xx',

	mac : function(val, field) {
		try {
			if ((/^((([a-zA-z0-9]){2})\-){5}([a-zA-z0-9]){2}$/.test(val)))
				return true;
			return false;
		} catch (e) {
			return false;
		}
	},
	macText : '请输入正确的MAC地址:xx-xx-xx-xx-xx-xx',

	validChar : function(val, field) {
		var chars = /[~`'!@#$?%^*<>\[\{\]\}%&\-+=|:;,.\/\\"]/;
		try {
			if (!(chars.test(val))) {
				return true;
			}
			return false;
		} catch (e) {
			return false;
		}
	},
	validCharText : '输入的字符是非法字符.'
})

Ext.onReady(init);

function init() {
	Ext.BLANK_IMAGE_URL = './ext3/resources/images/default/s.gif';
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	Ext.QuickTips.init();// 支持tips提示
	Ext.form.Field.prototype.msgTarget = 'qtip';// 提示的方式，枚举值为"qtip","title","under","side",id(元素id)
	themecombo = Ext.extend(Ext.form.ComboBox, {
				typeAhead : false,
				width : 60,
				triggerAction : 'all',
				lazyRender : true,
				listEmptyText : '无数据',
				listWidth : 100,
				mode : 'local',
				store : new Ext.data.ArrayStore({
							id : 0,
							fields : ['theme', 'color'],
							data : [
									['./ext3/resources/css/ext-all.css', '默认'],
									['./exttheme/1/resources/css/ext-all.css',
											'水蓝色'],
									['./exttheme/2/resources/css/ext-all.css',
											'雪青色'],
									['./exttheme/3/resources/css/ext-all.css',
											'宝蓝色'],
									['./exttheme/4/resources/css/ext-all.css',
											'丁香色'],
									['./exttheme/5/resources/css/ext-all.css',
											'青莲色'],
									['./exttheme/6/resources/css/ext-all.css',
											'水红色'],
									['./exttheme/7/resources/css/ext-all.css',
											'桃红色'],
									['./exttheme/8/resources/css/ext-all.css',
											'妃红色'],
									['./exttheme/9/resources/css/ext-all.css',
											'银红色'],
									['./exttheme/10/resources/css/ext-all.css',
											'绛红色'],
									['./exttheme/11/resources/css/ext-all.css',
											'杏黄色'],
									['./exttheme/12/resources/css/ext-all.css',
											'樱草色'],
									['./exttheme/13/resources/css/ext-all.css',
											'嫩绿色'],
									['./exttheme/14/resources/css/ext-all.css',
											'柳绿色'],
									['./exttheme/15/resources/css/ext-all.css',
											'葱绿色'],
									['./exttheme/16/resources/css/ext-all.css',
											'翡翠色'],
									['./exttheme/17/resources/css/ext-all.css',
											'碧绿色'],
									['./exttheme/18/resources/css/ext-all.css',
											'石青色'],
									['./exttheme/19/resources/css/ext-all.css',
											'蔚蓝色'],
									['./exttheme/20/resources/css/ext-all.css',
											'青白色']]
						}),
				valueField : 'theme',
				displayField : 'color',
				listeners : {
					select : function(cb, rc, id) {
						Ext.util.CSS.swapStyleSheet('theme', rc.data.theme);
						// maintab.syncSize();
						Ext.util.Cookies.set('theme', rc.data.theme);
					}
				}

			});

	var theme = new themecombo({
				id : 'themecombo',
				value : Ext.util.Cookies.get('theme') ? Ext.util.Cookies
						.get('theme') : './ext3/resources/css/ext-all.css'
			});

	toggleButton = Ext.extend(Ext.Button, {
				tooltip : '隐藏LOGO栏',
				iconCls : 'top_collapse',
				handler : function() {
					north.toggleCollapse();
					this.setIconClass(north.collapsed
							? 'top_collapse'
							: 'top_expand');

					this.setTooltip(north.collapsed ? '显示LOGO栏' : '隐藏LOGO栏');
				}
			});

	AlertWindow = function(b) {

		/*
		 * var win = Ext.getCmp('alertWindow'); if (!win) var win = new
		 * Ext.Window({ title : '报警信息', id : 'alertWindow', width : 800, height :
		 * 500, layout : 'fit', resizable : false, draggable : false, autoLoad : {
		 * border : false, url : "alertWindow.jsp", nocache : true, text :
		 * 'Loading...', scripts : true } }); win.show();
		 */
		if (b.getId() == 'alertButton1')
			ALERT_LEVEL = '01';
		if (b.getId() == 'alertButton2')
			ALERT_LEVEL = '02';
		if (b.getId() == 'alertButton3')
			ALERT_LEVEL = '03';

		openTab("异常查询", "./runMan/abnormalHandle/exceptionSearch.jsp", false,
				"deviceAbnormal");
	}

	AlertButton = Ext.extend(Ext.Button, {
				tooltip : '报警信息',
				iconCls : 'top_collapse',
				handler : AlertWindow
			});

	// 判断是否隐藏省级用户的事件告警
	hideAlertButton = false;

	function handleAction(upid) {
		Ext.Ajax.request({
					url : './sysman/listmenu.action',
					params : {
						upid : upid
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						smenu.removeAll();
						// smenu.addItem('皮肤:',theme, '-');
						smenu.addItem({
									iconCls : 'loguser'
								}, LOGGEDUSER, '-', {
									iconCls : 'logout',
									text : '退出',
									width : '60',
									handler : function() {
										window.location = "logout.jsp";
									}
								});
						// if (!hideAlertButton) {
						// smenu.addItem({
						// text : '报警信息:',
						// id : 'alertInfo'
						// }, new AlertButton({
						// id : 'alertButton1',
						// iconCls : 'serious',
						// text : '严重 0条'
						// }), new AlertButton({
						// id : 'alertButton2',
						// iconCls : 'subordination',
						// text : '次要 0条'
						// }), new AlertButton({
						// id : 'alertButton3',
						// iconCls : 'information',
						// text : '一般 0条'
						// }));
						// }
						smenu.addItem('->');

						if (result.list) {
							for (var i = 0; i < result.list.length; i++)
								addItems(result.list[i]);
						}
						smenu.addItem(new toggleButton());
						smenu.doLayout();

					}
				});
	}

	function addItems(item) {

		if (item.menuFolderFlag == '1') {
			var second = new Ext.SplitButton({
						text : item.title,
						// iconCls : 'base',
						iconCls : item.iconName,
						id : item.menuNo,
						// arrowHandler : function(btn) {
						// addListMenu(b, second);
						//
						// },
						// handler : function(b) {
						// // b.showMenu();// 取得菜单项并显示
						// addListMenu(b, second);
						// },
						menu : new Ext.menu.Menu([]),
						onClick : function(e, t) {
							e.preventDefault();
							if (this.disabled)
								return;
							addListMenu(null, this);
						}

					});

			smenu.addItem(second);
		} else {
			var second = new Ext.Button({
						text : item.title,
						id : item.menuNo,
						iconCls : item.iconName,
						// icoCls : 'base',
						handler : function(btn) {

							// 菜单打开新页面 rightMenuFlag=2
							// 菜单打开新页面 rightMenuFlag=2,向兵2010-07-29修改
							if (item.rightMenuFlag == '2') {
								window.open(item.url + '&orgNo=' + LOGGEDORGNO
										+ '&name=' + LOGGEDUSER);
								return;
							}

							if (item.isOpenTree == 0)
								west.collapse();
							else
								west.expand();
							openTabB({
										title : item.title,
										url : item.url,
										maintab : maintab,
										iconCls : item.iconName,
										isOpenTree : item.isOpenTree
									});
						}
					});
			smenu.addButton(second);
		}
	}

	// 添加三级菜单
	function addListMenu(btn, pbtn) {

		if (pbtn.menu.items.length > 0) {
			pbtn.showMenu();
			pbtn.enable();
			return;
		}
		pbtn.disable();
		Ext.Ajax.request({
			url : './sysman/listmenu.action',
			params : {
				upid : pbtn.getId()
			},
			success : function(response) {
				pbtn.enable();
				var result = Ext.decode(response.responseText);
				if (!result.list)
					return;
				Ext.each(result.list, function(item) {
					// alert(item.menuFolderFlag);
					if (item.menuFolderFlag == 0) {
						pbtn.menu.addItem(new Ext.menu.Item({
									text : item.title,
									id : item.menuNo,
									iconCls : item.iconName,
									handler : function(btn) {

										// 菜单打开新页面
										// rightMenuFlag=2,向兵2010-07-29修改
										if (item.rightMenuFlag == '2') {
											window.open(item.url + '&orgNo='
													+ LOGGEDORGNO + '&name='
													+ LOGGEDUSER);
											return;
										}

										if (item.isOpenTree == 0)
											west.collapse();
										else
											west.expand();
										openTabB({
													title : item.title,
													url : item.url,
													maintab : maintab,
													iconCls : item.iconName,
													isOpenTree : item.isOpenTree
												});
									}
								}));
					} else if (item.menuFolderFlag == 1) {// 四级菜单

						Ext.Ajax.request({
							url : './sysman/listmenu.action',
							params : {
								upid : item.menuNo
							},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								if (!result.list)
									return;

								var menu = new Ext.menu.Menu([]);

								Ext.each(result.list, function(item) {
									menu.addItem(new Ext.menu.Item({
										text : item.title,
										id : item.menuNo,
										iconCls : item.iconName,
										handler : function(btn) {

											// 菜单打开新页面
											// rightMenuFlag=2,向兵2010-07-29修改
											if (item.rightMenuFlag == '2') {
												window
														.open(item.url
																+ '&orgNo='
																+ LOGGEDORGNO
																+ '&name='
																+ LOGGEDUSER);
												return;
											}

											if (item.isOpenTree == 0)
												west.collapse();
											else
												west.expand();
											openTabB({
														title : item.title,
														url : item.url,
														maintab : maintab,
														iconCls : item.iconName,
														isOpenTree : item.isOpenTree
													});
										}
									}));
								});

								pbtn.menu.addItem(new Ext.menu.Item({
											text : item.title,
											id : item.menuNo,
											name : item.menuNo,
											iconCls : item.iconName,
											menu : menu
										}));
							}
						});

					}
				});
				pbtn.showMenu();

			},
			callback : function() {
				pbtn.enable();
			}
		});

	}

	// 一级菜单点击事件
	/*
	 * function handleAction(upid, grade) { // publicLoadMask.disable();
	 * Ext.Ajax.request({ url : './sysman/listmenu.action', params : { upid :
	 * upid // 前边的upid是参数名，后边的upid 是参数值 }, success : function(response) { var
	 * result = Ext.decode(response.responseText); if (grade == '1') {
	 * smenu.removeAll(); smenu.addItem('皮肤:', new themecombo({ id :
	 * 'themecombo', value : Ext.util.Cookies .get('theme') }), '-'); if
	 * (!hideAlertButton) { smenu.addItem({ text : '报警信息:', id : 'alertInfo' },
	 * new AlertButton({ id : 'alertButton1', iconCls : 'serious', text : '严重
	 * 0条' }), new AlertButton({ id : 'alertButton2', iconCls : 'subordination',
	 * text : '次要 0条' }), new AlertButton({ id : 'alertButton3', iconCls :
	 * 'information', text : '一般 0条' })); } smenu.addItem('->'); }
	 * 
	 * if (result.list) { for (var i = 0; i < result.list.length; i++)
	 * addItems(result.list[i], grade); } if (grade == '1') { smenu.addItem(new
	 * toggleButton()); } smenu.doLayout(); // publicLoadMask.enable(); } } ); }
	 */

	// 添加二级菜单和三级菜单的方法
	// function addItems(item, grade) {
	// if (item.menuFolderFlag == '1') {
	// if (grade == '1') { // 二级菜单是文件夹的
	//
	// var second = new Ext.SplitButton({
	// text : item.title,
	// iconCls : 'base',
	// iconCls : item.iconName,
	// id : item.menuNo,
	// handler : function(b) {
	// b.showMenu();
	// },
	// menu : new Ext.menu.Menu([])
	// });
	//
	// smenu.addItem(second);
	// handleAction(item.menuNo, '2');
	// }
	// } else {
	// if (grade == '1') {
	// var second = new Ext.Button({
	// text : item.title,
	// id : item.menuNo,
	// iconCls : item.iconName,
	// icoCls : 'base',
	// handler : function(btn) {
	// if (item.isOpenTree == 0)
	// west.collapse();
	// else
	// west.expand();
	// openTab(item.title, item.url, maintab,
	// item.iconName);
	// }
	// });
	// smenu.addButton(second);
	// } else {
	// var upmenu = Ext.getCmp(item.PMenuNo);
	// if (upmenu)
	// upmenu.menu.addItem(new Ext.menu.Item({
	// text : item.title,
	// id : item.menuNo,
	// iconCls : item.iconName,
	// handler : function(btn) {
	// if (item.isOpenTree == 0)
	// west.collapse();
	// else
	// west.expand();
	// openTab(item.title, item.url, maintab,
	// item.iconName);
	// }
	// }));
	// }
	// }
	// }
	// 工作区
	var maintab = new Ext.TabPanel({
		id : 'maintab',
		layoutOnTabChange : true,
		activeTab : 1,
		defaults : {
			hideMode : 'offsets'
		},
		border : 0,
		enableTabScroll : true,
		deferredRender : false,
		autoScroll : true,
		region : "center",
		listeners : {
			// 传进去的三个参数分别为:这个tabpanel(tabsDemo),当前标签页,事件对象e
			contextmenu : function(tdemo, myitem, e) {
				menu = new Ext.menu.Menu([{
							text : "关闭当前页",
							handler : function() {
								tdemo.remove(myitem);
							}
						}, {
							text : "关闭其他所有页",
							handler : function() { // 循环遍历
								tdemo.items.each(function(item) {
											if (item.closable && item != myitem) {
												// 可以关闭的其他所有标签页全部关掉
												tdemo.remove(item);
											}
										});
							}
						}]);
				// 显示在当前位置
				menu.showAt(e.getPoint());
			}
		},
		items : [new Ext.Panel({
							title : '主页',
							id : '主页',
							iconCls : 'mainpage',
							layout : 'fit',
							autoLoad : {
								url : "main.jsp",
								nocache : true,
								text : 'Loading...',
								scripts : true
							}
						}), new Ext.Panel({
							title : '统计主页',
							id : '统计主页',
							iconCls : 'favor',
							layout : 'fit',
							autoLoad : {
								url : "myfavor.jsp",
								nocache : true,
								text : 'Loading...',
								scripts : true
							}
						})]
	});

	// 左边树(用户树示例)
	/*
	 * var rootNode = new Ext.tree.AsyncTreeNode({ id : '0', text : '河南省电力公司'
	 * });
	 * 
	 * var ptbar = new Ext.ux.tree.PagingTreeToolbar(); var treeLoader = new
	 * Ext.ux.tree.PagingTreeLoader({ autoScroll : true, toolbar : ptbar,
	 * dataUrl : './sysman/generaltree!userTree.action' });
	 * 
	 * var tree = new Ext.tree.TreePanel({ width : 300, id : 'sysusertree',
	 * border : false, autoScroll : true, root : rootNode, loader : treeLoader,
	 * bbar : ptbar });
	 * 
	 * tree.on('click', function(node) { alert(node.text); });
	 */

	// 用户列表
	var bureauComStore = new Ext.data.JsonStore({
				url : "./sysman/generaltree!bureauList.action",
				fields : ['orgNo', 'orgName'],
				root : "bureauList"
			});

	var bureauCombox = new Ext.form.ComboBox({
				store : bureauComStore,
				displayField : 'orgName',
				valueField : 'orgNo',
				typeAhead : true,
				mode : 'remote',
				fieldLabel : '供电所',
				name : 'bureauNo',
				triggerAction : 'all',
				emptyText : '--请选择供电所名称--',
				selectOnFocus : true
			});

	var protocolComStore = new Ext.data.JsonStore({
				url : "./sysman/generaltree!protocolList.action",
				fields : ['protocolCode', 'protocolName'],
				root : "protocolList"
			});
	// 预载入 以便在不使用规约查询的情况对规约进行 render
	protocolComStore.load();

	var protocolCombox = new Ext.form.ComboBox({
				store : protocolComStore,
				displayField : 'protocolName',
				valueField : 'protocolCode',
				typeAhead : true,
				mode : 'remote',
				fieldLabel : '规约类型',
				name : 'protocolCode',
				triggerAction : 'all',
				emptyText : '--请选择规约类型--',
				selectOnFocus : true
			});

	var protocolComStore1 = new Ext.data.JsonStore({
				url : "./sysman/generaltree!protocolList.action",
				fields : ['protocolCode', 'protocolName'],
				root : "protocolList"
			});

	var protocolCombox1 = new Ext.form.ComboBox({
				store : protocolComStore1,
				displayField : 'protocolName',
				valueField : 'protocolCode',
				typeAhead : true,
				mode : 'remote',
				fieldLabel : '规约类型',
				name : 'protocolCode',
				triggerAction : 'all',
				emptyText : '--请选择规约类型--',
				selectOnFocus : true
			});

	var renderProtocol = function(val) {
		var no = protocolComStore.find('protocolCode', val);
		if (no > -1)
			return protocolComStore.getAt(no).get('protocolName');
		else
			return '未知';
	}

	var userSelectModel = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true,
				header : ''
			});
	var userStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './sysman/generaltree!userGrid.action'
						}),
				// proxy : new Ext.data.MemoryProxy(data),
				reader : new Ext.data.JsonReader({
							root : 'userGrid',
							totalProperty : 'totalCount'
						}, [{
									name : 'consId'
								}, {
									name : 'consNo'
								}, {
									name : 'custId'
								}, {
									name : 'consId'
								}, {
									name : 'cisAssetNo'
								}, {
									name : 'consName'
								}, {
									name : 'assetNo'
								}, {
									name : 'terminalId'
								}, {
									name : 'terminalTypeCode'
								}, {
									name : 'protocolCode'
								}, {
									name : 'cpname'
								}, {
									name : 'cpNo'
								}, {
									name : 'tmnlAssetNo'
								}, {
									name : 'consType'
								}, {
									name : 'consSortCode'
								}, {
									name : 'terminalAddr'
								}, {
									name : 'orgNo'
								}, {
									name : 'orgName'
								}])
			});

	var userColumnModel = new Ext.grid.ColumnModel([userSelectModel, {
				header : '用户名称',
				dataIndex : 'consName'
			}, {
				header : '用户编号',
				dataIndex : 'consNo',
				renderer : function(value, metaData, record, rowIndex,
						colIndex, store) {
					var s = '用户名称:' + record.get('consName');
					var html = '<span  ext:qtip="' + s + '">' + value
							+ '</span>';
					return html;
				}
			}, {
				header : '用户标识',
				dataIndex : 'consId',
				hidden : true
			}, {
				header : '供电单位编号',
				dataIndex : 'orgNo'
			}, {
				header : '供电单位名称',
				dataIndex : 'orgName'
			}, {
				header : '终端地址',
				dataIndex : 'terminalAddr',
				renderer : function(value, metaData, record, rowIndex,
						colIndex, store) {
					var s = '用户名称:' + record.get('consName');
					if (value == null)
						value = "";
					var html = '<span  ext:qtip="' + s + '">' + value
							+ '</span>';
					return html;
				}
			}, {
				header : '电表资产号',
				dataIndex : 'assetNo',
				renderer : function(value, metaData, record, rowIndex,
						colIndex, store) {
					if (value == null)
						value = "";
					var html = '<span  ext:qtip="' + value + '">' + value
							+ '</span>';
					return html;
				}
			}, {
				header : '营销资产编号',
				dataIndex : 'cisAssetNo',
				hidden : true
			}, {
				header : '客户标识',
				dataIndex : 'terminalId',
				hidden : true
			}, {
				header : '终端类别',
				dataIndex : 'terminalTypeCode',
				hidden : true
			}, {
				header : '采集点名',
				dataIndex : 'cpName',
				hidden : true
			}, {
				header : '终端资产号',
				dataIndex : 'tmnlAssetNo',
				hidden : true
			}, {
				header : '规约类型',
				dataIndex : 'protocolCode',
				hidden : true,
				renderer : renderProtocol
			}, {
				header : '用电类型',
				dataIndex : 'consType',
				hidden : true,
				renderer : function(val) {
					// 暂时不从数据字典表取值 修改时取 vw_cons_type值
					if (val == '1')
						return '专变用户';
					else if (val == '2')
						return '台区用户';
					else if (val == '7')
						return '变电站';
					else if (val == '5')
						return '居民用户';
					else
						return '其它';
				}
			}, {
				header : '用户分类',
				dataIndex : 'consSortCode',
				hidden : true,
				renderer : function(val) {
					if (val == '01')
						return '高压用户';
					else if (val == '02')
						return '低压非居民';
					else if (val == '03')
						return '低压居民';
					else
						return '其它';
				}
			}

	]);
	var selectAllCheckBox = new Ext.form.Checkbox({
				boxLabel : '全选',
				name : 'selectAllCheckBox',
				id : 'selectAllCheckBox'
			});
	var totalNumBtn = new Ext.Button({
				text : '没有数据',
				name : 'totalNumBtn',
				id : 'totalNumBtn'
			});
	var userGrid = new Ext.grid.GridPanel({
				id : 'leftUserGrid',
				border : false,
				region : 'center',
				autoScroll : true,
				cm : userColumnModel,
				viewConfig : {
					forceFit : false
				},
				ds : userStore,
				sm : userSelectModel,
				tbar : [selectAllCheckBox, {
							xtype : 'tbfill'
						}, totalNumBtn],
				bbar : new Ext.ux.MyToolbar({
							enableExpAll : true,
							store : userStore
						})

			});
	userGrid.on('render', function(grid) {
				var gridEl = grid.getEl();
				gridEl.select('div.x-grid3-hd-checker')
						.removeClass('x-grid3-hd-checker');
			});

	advQuery = function() {

		var win = Ext.getCmp('advQueryWindow');
		if (!win)
			var win = new Ext.Window({
						title : '用户高级查询',
						id : 'advQueryWindow',
						width : 800,
						height : 500,
						layout : 'fit',
						resizable : false,
						draggable : false,
						autoLoad : {
							border : false,
							url : "advQuery.jsp",
							nocache : true,
							text : 'Loading...',
							scripts : true,
							callback : function() {
							}
						}
					});
		win.show();
	}

	var userForm = new Ext.form.FormPanel({
		// region : 'north',
		collapsible : true,
		// collapseMode : 'mini',
		// split : true,
		border : false,
		title : '专变用户',
		// titleCollapse : true,
		bodyStyle : 'padding:5px 0px 0px 5px',
		buttonAlign : 'center',
		labelWidth : 70,
		defaults : {
			anchor : '95%'
		},
		// autoHeight : true,
		labelAlign : 'right',
		items : [new Ext.form.TextField({
							name : 'consName',
							emptyText : '请输入用户名称',
							fieldLabel : '用户名称'
						}), new Ext.form.TextField({
							name : 'consNo',
							emptyText : '请输入用户编号',
							fieldLabel : '用户编号'
						}), new Ext.form.TextField({
					name : 'terminalAddr',
					emptyText : '请输入终端地址',
					fieldLabel : '终端地址'
						// }), new Ext.form.TextField({
						// name : 'cisAssetNo',
						// emptyText : '请输入终端资产号',
						// fieldLabel : '终端资产号'
					}), new Ext.form.TextField({
							name : 'cp_name',
							emptyText : '请输入采集点名称',
							fieldLabel : '采集点名称'
						}), new Ext.form.TextField({
							name : 'meterAssetNo',
							emptyText : '请输入电能表资产号',
							fieldLabel : '电表资产号'
						}), new Ext.form.TextField({
							name : 'mrSectNo',
							emptyText : '请输入抄表段编号',
							fieldLabel : '抄表段编号'
						}), protocolCombox],
		bbar : new Ext.Toolbar(['->', {
			text : '查询',
			iconCls : 'query',
			handler : function() {
				// publicLoadMask.disable();
				var myMask = new Ext.LoadMask(userForm.getId(), {
							msg : "加载中..."
						});
				myMask.show();
				var values = userForm.getForm().getFieldValues();
				userStore.proxy.conn.url = './sysman/generaltree!userGrid.action';
				userStore.baseParams = {
					start : 0,
					limit : DEFAULT_PAGE_SIZE,
					consName : values.consName,
					consNo : values.consNo,
					// terminalId : values.terminalId,
					protocolCode : values.protocolCode,
					bureauNo : values.bureauNo,
					cisAssetNo : values.cisAssetNo,
					terminalAddr : values.terminalAddr,
					meterAssetNo : values.meterAssetNo,
					mrSectNo : values.mrSectNo,
					cpName : values.cp_name,
					consType : '1'

				};
				userStore.load({
							callback : function() {
								myMask.hide();
								totalNumBtn.setText("总记录："
										+ userStore.getTotalCount() + "条");
							}
						});
			}
		}, {
			text : '高级',
			iconCls : 'advance',
			handler : advQuery
		}])
	});

	var tgForm = new Ext.form.FormPanel({
		// region : 'north',
		collapsible : true,
		// collapseMode : 'mini',
		// split : true,
		border : false,
		title : '台区',
		// titleCollapse : true,
		bodyStyle : 'padding:5px 0px 0px 5px',
		buttonAlign : 'center',
		labelWidth : 80,
		defaults : {
			anchor : '95%'
		},
		// autoHeight : true,
		labelAlign : 'right',
		items : [new Ext.form.TextField({
							name : 'tgName',
							emptyText : '请输入台区名称',
							fieldLabel : '台区名称'
						}), new Ext.form.TextField({
							name : 'tgNo',
							emptyText : '请输入台区编号',
							fieldLabel : '台区编号'
						}), new Ext.form.TextField({
					name : 'terminalId', // 对应后台terminalAddr
					emptyText : '请输入集中器地址',
					fieldLabel : '集中器地址'
						// }), new Ext.form.TextField({
						// name : 'tmnlAssetNo',
						// emptyText : '请输入集中器资产号',
						// fieldLabel : '集中器资产号'
					}), new Ext.form.TextField({
							name : 'cp_name',
							emptyText : '请输入采集点名称',
							fieldLabel : '采集点名称'
						}), new Ext.form.TextField({
							name : 'cisAssetNo',
							emptyText : '请输入采集器资产号',
							fieldLabel : '采集器资产号'
						}), new Ext.form.TextField({
							name : 'meterAssetNo',
							emptyText : '请输入电能表资产号',
							fieldLabel : '电表资产号'
						}), protocolCombox1],
		bbar : new Ext.Toolbar(['->', {
			text : '查询',
			iconCls : 'query',
			handler : function() {
				// publicLoadMask.disable();
				var myMask = new Ext.LoadMask(tgForm.getId(), {
							msg : "加载中..."
						});
				myMask.show();
				var values = tgForm.getForm().getFieldValues();
				userStore.proxy.conn.url = './sysman/generaltree!userGrid.action';
				userStore.baseParams = {
					start : 0,
					limit : DEFAULT_PAGE_SIZE,
					tgName : values.tgName,
					tgId : values.tgNo,
					terminalId : values.terminalId,
					protocolCode : values.protocolCode,
					bureauNo : values.bureauNo,
					tmnlAssetNo : values.tmnlAssetNo,
					consType : '2',
					cisAssetNo : values.cisAssetNo,
					meterAssetNo : values.meterAssetNo,
					cpName : values.cp_name
				};
				userStore.load({
							callback : function() {
								myMask.hide();
								totalNumBtn.setText("总记录："
										+ userStore.getTotalCount() + "条");
							}
						});

			}
		}, {
			text : '高级',
			iconCls : 'advance',
			handler : advQuery
		}])
	});

	var subForm = new Ext.form.FormPanel({
		// region : 'north',
		collapsible : true,
		// collapseMode : 'mini',
		// split : true,
		border : false,
		title : '变电站',
		// titleCollapse : true,
		bodyStyle : 'padding:5px 0px 0px 5px',
		buttonAlign : 'center',
		labelWidth : 70,
		defaults : {
			anchor : '95%'
		},
		// autoHeight : true,
		labelAlign : 'right',
		items : [new Ext.form.TextField({
							name : 'subName',
							emptyText : '请输入变电让名称',
							fieldLabel : '变电站名称'
						}), new Ext.form.TextField({
							name : 'lineName',
							emptyText : '请输入线路名称',
							fieldLabel : '线路名称'
						}), new Ext.form.TextField({
							name : 'cisAssetNo',
							emptyText : '请输入终端资产号',
							fieldLabel : '终端资产号'
						}), new Ext.form.TextField({
							name : 'meterAssetNo',
							emptyText : '请输入电能表资产号',
							fieldLabel : '电表资产号'
						})],
		bbar : new Ext.Toolbar(['->', {
			text : '查询',
			iconCls : 'query',
			handler : function() {
				// publicLoadMask.disable();
				var myMask = new Ext.LoadMask(subForm.getId(), {
							msg : "加载中..."
						});
				myMask.show();
				var values = subForm.getForm().getFieldValues();
				userStore.proxy.conn.url = './sysman/generaltree!userGrid.action';
				userStore.baseParams = {
					start : 0,
					limit : DEFAULT_PAGE_SIZE,
					subName : values.subName,
					lineName : values.lineName,
					cisAssetNo : values.cisAssetNo,
					meterAssetNo : values.meterAssetNo,
					consType : '7'
					// consName : values.consName,
					// consNo : values.consNo,
					// terminalId : values.terminalId,
					// protocolCode : values.protocolCode,
					// bureauNo : values.bureauNo,
					// tmnlAssetNo : values.tmnlAssetNo
				};
				userStore.load({
							callback : function() {
								myMask.hide();
								totalNumBtn.setText("总记录："
										+ userStore.getTotalCount() + "条");
							}
						});
			}
		}, {
			text : '高级',
			iconCls : 'advance',
			handler : advQuery
		}])
	});

	var citizenForm = new Ext.form.FormPanel({
		// region : 'north',
		collapsible : true,
		// collapseMode : 'mini',
		// split : true,
		border : false,
		title : '低压集抄',
		// titleCollapse : true,
		bodyStyle : 'padding:5px 0px 0px 5px',
		buttonAlign : 'center',
		labelWidth : 60,
		defaults : {
			anchor : '95%'
		},
		// autoHeight : true,
		labelAlign : 'right',
		items : [new Ext.form.TextField({
							name : 'consName',
							emptyText : '请输入用户名称',
							fieldLabel : '用户名称'
						}), new Ext.form.TextField({
							name : 'consNo',
							emptyText : '请输入用户编号',
							fieldLabel : '用户编号'
						}), new Ext.form.TextField({
							name : 'tgNo',
							emptyText : '请输入台区编号',
							fieldLabel : '台区编号'
						}), new Ext.form.TextField({
							name : 'tgName',
							emptyText : '请输入台区名称',
							fieldLabel : '台区名称'
						}), new Ext.form.TextField({
							name : 'mrSectNo',
							emptyText : '请输入抄表段编号',
							fieldLabel : '抄表段编号'
						}), new Ext.form.TextField({
							name : 'meterAssetNo',
							emptyText : '请输入电能表资产号',
							fieldLabel : '电表资产号'
						})],
		bbar : new Ext.Toolbar(['->', {
			text : '查询',
			iconCls : 'query',
			handler : function() {
				// publicLoadMask.disable();
				var myMask = new Ext.LoadMask(citizenForm.getId(), {
							msg : "正在查询用户..."
						});
				myMask.show();
				var values = citizenForm.getForm().getFieldValues();
				userStore.proxy.conn.url = './sysman/generaltree!userGrid.action';
				userStore.baseParams = {
					consName : values.consName,
					consNo : values.consNo,
					tgNo : values.tgNo,
					tgName : values.tgName,
					// protocolCode : values.protocolCode,
					mrSectNo : values.mrSectNo,
					consType : '5',
					meterAssetNo : values.meterAssetNo
				};
				userStore.load({
							callback : function() {
								myMask.hide();
								totalNumBtn.setText("总记录："
										+ userStore.getTotalCount() + "条");
							}
						});

			}
		}, {
			text : '高级',
			iconCls : 'advance',
			handler : advQuery
		}])
	});

	var cpTypeCodeStore = new Ext.data.JsonStore({
				url : "./sysman/generaltree!queryCpTypeCode.action",
				fields : ['cpTypeCode', 'cpType'],
				root : "cpTypeCodeList"
			});

	var cpTypeCodeCombox = new Ext.form.ComboBox({
				store : cpTypeCodeStore,
				displayField : 'cpType',
				valueField : 'cpTypeCode',
				typeAhead : true,
				mode : 'remote',
				fieldLabel : '采集点类型',
				name : 'cpTypeCodeCombox',
				triggerAction : 'all',
				emptyText : '请选择采集点类型',
				selectOnFocus : true
			});

	// 采集点查询视图
	var collectPointForm = new Ext.form.FormPanel({
		collapsible : true,
		border : false,
		title : '采集点',
		bodyStyle : 'padding:5px 0px 0px 5px',
		buttonAlign : 'center',
		labelWidth : 60,
		defaults : {
			anchor : '95%'
		},
		labelAlign : 'right',
		items : [new Ext.form.TextField({
							name : 'cp_no',
							emptyText : '请输入采集点编号',
							fieldLabel : '采集点编号'
						}), new Ext.form.TextField({
							name : 'cp_name',
							emptyText : '请输入采集点名称',
							fieldLabel : '采集点名称'
						}), new Ext.form.TextField({
							name : 'cp_addr',
							emptyText : '请输入采集点地址',
							fieldLabel : '采集点地址'
						}), cpTypeCodeCombox],
		bbar : new Ext.Toolbar(['->', {
			text : '查询',
			iconCls : 'query',
			handler : function() {
				var myMask = new Ext.LoadMask(collectPointForm.getId(), {
							msg : '正在查询用户...'
						});
				myMask.show();

				var values = collectPointForm.getForm().getFieldValues();
				userStore.proxy.conn.url = './sysman/generaltree!userGrid.action';

				userStore.baseParams = {
					cpNo : values.cp_no,
					cpName : values.cp_name,
					cpAddr : values.cp_addr,
					cpTypeCode : values.cpTypeCodeCombox,
					consType : '1'
				};

				userStore.load({
							callback : function() {
								myMask.hide();
								totalNumBtn.setText("总记录："
										+ userStore.getTotalCount() + "条");
							}
						});
			}
		}, {
			text : '高级',
			iconCls : 'advance',
			handler : advQuery
		}])
	});

	// 继承accordion
	Ext.namespace("Ext.ux");
	Ext.ux.AccordionLayout = Ext.extend(Ext.layout.AccordionLayout, {
				// private
				setActive : function(item, expand) {
					var ai = this.activeItem;
					item = this.container.getComponent(item);
					if (ai != item) {
						if (item.rendered && item.collapsed && expand) {
							item.expand();
						} else {
							if (ai) {
								ai.fireEvent('deactivate', ai);
							}
							this.activeItem = item;
							changeHeaderByUserType(this.activeItem.title);
							item.fireEvent('activate', item);
						}
					}
				}
			});

	Ext.reg("AccordionLayout", Ext.ux.AccordionLayout);

	// 变更名称
	function changeHeaderByUserType(title) {
		if ('台区' == title) {
			userColumnModel.setColumnHeader(1, '台区名称');
			userColumnModel.setColumnHeader(2, '台区编号');
			userColumnModel.setHidden(2, true);
		} else if ('采集点' == title) {
			userColumnModel.setColumnHeader(1, '采集点名称');
			userColumnModel.setColumnHeader(2, '采集点编号');
			userColumnModel.setHidden(2, false);
		} else {
			userColumnModel.setColumnHeader(1, '用户名称');
			userColumnModel.setColumnHeader(2, '用户编号');
			userColumnModel.setHidden(2, false);
		}
	}

	// subForm 变电站先隐掉
	var userCordinPanel = new Ext.Panel({
				height : 334,
				region : 'north',
				layout : new Ext.ux.AccordionLayout(),
				autoScroll : true,
				items : [userForm, tgForm, citizenForm, collectPointForm]

			});

	var userPanel = new Ext.Panel({
				border : false,
				layout : 'border',
				items : [userCordinPanel, userGrid]
			});

	// 电网树
	var netRootNode = new Ext.tree.AsyncTreeNode({
				id : 'net_root',
				text : '电网导航',
				iconCls : 'net-02'
			});

	var netPtbar = new Ext.ux.tree.PagingTreeToolbar();
	var netTreeLoader = new Ext.ux.tree.PagingTreeLoader({
				autoScroll : true,
				toolbar : netPtbar,
				dataUrl : './sysman/generaltree!netTree.action'
			});

	var netTree = new Ext.tree.TreePanel({
				width : 300,
				id : 'netTree', // 其它模块可以直接用 Ext.get('netTree')取得
				border : false,
				autoScroll : true,
				root : netRootNode,
				rootVisible : false,
				loader : netTreeLoader,
				bbar : netPtbar
			});

	// 行业树
	var tradeRootNode = new Ext.tree.AsyncTreeNode({
				id : 'trade_root',
				text : '行业导航',
				iconCls : 'net-02'
			});

	var tradePtbar = new Ext.ux.tree.PagingTreeToolbar();
	var tradeTreeLoader = new Ext.ux.tree.PagingTreeLoader({
				autoScroll : true,
				toolbar : tradePtbar,
				dataUrl : './sysman/generaltree!tradeTree.action'
			});
	var tradeTree = new Ext.tree.TreePanel({
				width : 300,
				id : 'tradeTree', // 其它模块可以直接用 Ext.get('tradeTree')取得
				border : false,
				autoScroll : true,
				root : tradeRootNode,
				rootVisible : false,
				loader : tradeTreeLoader,
				bbar : tradePtbar
			});

	// 区域树
	var areaRootNode = new Ext.tree.AsyncTreeNode({
				id : 'area_root',
				text : '区域导航',
				iconCls : 'net-02'
			});

	var areaPtbar = new Ext.ux.tree.PagingTreeToolbar();
	var areaTreeLoader = new Ext.ux.tree.PagingTreeLoader({
				autoScroll : true,
				toolbar : areaPtbar,
				dataUrl : './sysman/generaltree!areaTree.action'
			});
	var areaTree = new Ext.tree.TreePanel({
				width : 300,
				id : 'areaTree', // 其它模块可以直接用 Ext.get('areaTree')取得
				border : false,
				autoScroll : true,
				rootVisible : false,
				root : areaRootNode,
				loader : areaTreeLoader,
				bbar : areaPtbar
			});

	// userTree.getRootNode().expand();

	// 群组
	// 备选用户群组
	var backUserGroupRootNode = new Ext.tree.AsyncTreeNode({
				id : 'backuser_root',
				text : '普通群组',
				iconCls : 'net-02'
			});

	var backUserGroupPtbar = new Ext.ux.tree.PagingTreeToolbar();
	var backUserGroupTreeLoader = new Ext.ux.tree.PagingTreeLoader({
				autoScroll : true,
				toolbar : backUserGroupPtbar,
				dataUrl : './sysman/generaltree!backTree.action'
			});
	var backUserGroupTree = new Ext.tree.TreePanel({
				width : 300,
				title : '普通群组',
				id : 'backTree', // 其它模块可以直接用 Ext.get('areaTree')取得
				border : false,
				rootVisible : false,
				autoScroll : true,
				root : backUserGroupRootNode,
				loader : backUserGroupTreeLoader,
				bbar : backUserGroupPtbar
			});

	// 控制用户群组
	var controlUserGroupRootNode = new Ext.tree.AsyncTreeNode({
				id : 'controluser_root',
				text : '控制用户群组',
				iconCls : 'net-02'
			});

	var controlUserGroupPtbar = new Ext.ux.tree.PagingTreeToolbar();
	var controlUserGroupTreeLoader = new Ext.ux.tree.PagingTreeLoader({
				autoScroll : true,
				toolbar : controlUserGroupPtbar,
				dataUrl : './sysman/generaltree!controlTree.action'
			});
	var controlUserGroupTree = new Ext.tree.TreePanel({
				width : 300,
				title : '控制群组',
				id : 'controlTree', // 其它模块可以直接用 Ext.get('areaTree')取得
				border : false,
				autoScroll : true,
				rootVisible : false,
				root : controlUserGroupRootNode,
				loader : controlUserGroupTreeLoader,
				bbar : controlUserGroupPtbar
			});

	// 操作区tab页
	var operate = new Ext.ux.TabPanel({
				tabPosition : 'left',
				autoScroll : true,
				border : false,
				activeTab : 3,
				enableTabScroll : true,
				items : [new Ext.Panel({
									border : false,
									layout : 'fit',
									title : '查询',
									items : [userPanel]
								}), new Ext.Panel({
									layout : 'fit',
									title : '行业',
									items : [tradeTree]
								}), new Ext.Panel({
									border : false,
									layout : 'fit',
									title : '电网',
									items : [netTree]
								}), new Ext.Panel({
									border : false,
									layout : 'fit',
									title : '区域',
									items : [areaTree]
								}), new Ext.Panel({
									border : false,
									layout : 'accordion',
									title : '群组',
									items : [backUserGroupTree,
											controlUserGroupTree]
								})]
			});

	// 操作对象选区
	var west = new Ext.Panel({
				title : "操作对象选择区",
				id : 'mainwest',
				region : "west",
				width : 240,
				layout : 'fit',
				split : true,
				titleCollapse : false,
				collapseMode : 'mini',
				minWidth : 100,
				maxWidth : 400,
				collapsible : true,
				items : [operate]
			});

	// 二级菜单栏
	smenu = new Ext.Toolbar({
				height : 25,
				id : 'smenu',
				items : [
						// '皮肤:', new themecombo({
						// id : 'themecombo'
						// }), '-',
						{
					iconCls : 'loguser'
				}, LOGGEDUSER, '-', {
					iconCls : 'logout',
					text : '退出',
					width : '60',
					handler : function() {
						window.location = "logout.jsp";
					}
				},
						// {
						// text : '报警信息:',
						// id : 'alertInfo'
						// }, new AlertButton({
						// id : 'alertButton1',
						// iconCls : 'serious',
						// text : '严重 0条'
						// }), new AlertButton({
						// id : 'alertButton2',
						// iconCls : 'subordination',
						// text : '次要 0条'
						// }), new AlertButton({
						// id : 'alertButton3',
						// iconCls : 'information',
						// text : '一般 0条'
						// }),
						'->', new toggleButton()]

			});

	// 一级菜单区
	var north = new Ext.Panel({
		collapseMode : 'mini',
		region : "north",
		layout : "table",
		height : 64,
		bodyStyle : "padding:0 0 0 0;background-image:url(./images/background.png); background-repeat: repeat-x;",
		border : false,
		items : [new Ext.Panel({
							el : "logo",
							border : false
						}), new Ext.ux.ImageButton({
							imgPath : './images/baseApp.png',
							imgWidth : 64,
							imgHeight : 64,
							width : 80,
							height : 64,
							iconAlign : 'top',
							tooltip : '基本应用',// 鼠标放上去的提示
							handler : (function(btn) {
								handleAction('10000', '1');
								// openTab('loadpanel',
								// './testList.jsp');
							})
						}), new Ext.ux.ImageButton({
							imgPath : './images/advApp.png',
							imgWidth : 64,
							imgHeight : 64,
							width : 80,
							height : 64,
							iconAlign : 'top',
							tooltip : '高级应用',// 鼠标放上去的提示
							handler : function(btn) {
								handleAction('20000', '1');
							}
						}), new Ext.ux.ImageButton({
							imgPath : './images/runMan.png',
							imgWidth : 64,
							imgHeight : 64,
							width : 80,
							height : 64,
							iconAlign : 'top',
							tooltip : '运行管理',// 鼠标放上去的提示
							handler : function(btn) {
								handleAction('30000', '1');
							}
						}), new Ext.ux.ImageButton({
							imgPath : './images/qryStat.png',
							imgWidth : 64,
							imgHeight : 64,
							width : 80,
							height : 64,
							iconAlign : 'top',
							tooltip : '统计查询',// 鼠标放上去的提示
							handler : function(btn) {
								handleAction('40000', '1');
							}
						}), new Ext.ux.ImageButton({
							imgPath : './images/sysMan.png',
							imgWidth : 64,
							imgHeight : 64,
							width : 80,
							height : 64,
							iconAlign : 'top',
							tooltip : '系统管理',// 鼠标放上去的提示
							handler : function(btn) {
								handleAction('50000', '1');
							}
						})]
	});

	// // 标题栏
	// var north = {
	// xtype : "panel",
	// // title:"标题栏和一级菜单",
	// region : "north",
	// border : false,
	// layout : "fit",
	// height : 64,
	// collapsible : true,
	// // bbar : smenu,
	// items : [ico]
	//
	// };

	var main = new Ext.Panel({
				region : "center",
				layout : 'border',
				items : [west, maintab],
				tbar : smenu
			});

	// 主页面构造
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [north, main]
			});
	var theme = Ext.util.Cookies.get('theme');
	if (theme) {
		Ext.getCmp('themecombo').setValue(theme);
		Ext.util.CSS.swapStyleSheet('theme', theme);
		// maintab.syncSize();

	}

	// 定时更新

	// var updateAlertInfo = function() {
	// var ab1 = Ext.getCmp('alertButton1');
	// var ab2 = Ext.getCmp('alertButton2');
	// var ab3 = Ext.getCmp('alertButton3');
	// var ai1 = Ext.getCmp('alertInfo');
	// Ext.Ajax.request({
	// url : './sysman/alertevent!alertEventCount.action',
	// success : function(response) {
	// var obj = Ext.decode(response.responseText);
	// var countObj = obj.alertEventCount;
	//
	// if (countObj && countObj[0]) {
	// ab1.setText('严重：'
	// + (countObj[0].LEVEL1COUNT
	// ? countObj[0].LEVEL1COUNT
	// : 0) + '条');
	// ab2.setText('次要：'
	// + (countObj[0].LEVEL2COUNT
	// ? countObj[0].LEVEL2COUNT
	// : 0) + '条');
	// ab3.setText('一般：'
	// + (countObj[0].LEVEL3COUNT
	// ? countObj[0].LEVEL3COUNT
	// : 0) + '条');
	// } else {
	// ab1.setVisible(false);
	// ab2.setVisible(false);
	// ab3.setVisible(false);
	// ai1.setVisible(false);
	// hideAlertButton = true;
	// runner.stopAll();
	// }
	//
	// }
	//
	// });
	// }
	// var task = {
	// run : updateAlertInfo,
	// interval : 1000 * 60 * 5
	// // 1分钟
	// }

	// var runner = new Ext.util.TaskRunner();
	// runner.start(task);

	// 初始不载入用户列表
	// userStore.load();

}

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
				/*
				 * if (!Ext.isEmpty(tabpanel)) { this.modelPage =
				 * tabpanel.getComponent(this.modelName); } // 在模块activate 和
				 * deactiveate 时对事件进行设置 if (!Ext.isEmpty(this.modelPage)) {
				 * tabpanel.on('activate', function(p) { alert(p.getId()); if
				 * (p.getId() == this.modelPage.getId()) { this.enableEvent =
				 * true; alert(this.enableEvent); } });
				 * tabpanel.on('deactivate', function(p) { alert(p.getId()); if
				 * (p.getId() == this.modelPage.getId()) { this.enableEvent =
				 * false; alert(this.enableEvent); } });
				 * this.modelPage.on('beforeclose', function(p) { // 移除所有监听 }); }
				 */

			},

			listenLeftTree : function() {
				Ext.getCmp('netTree').on('click', this.netTreeNodeClick, this);
				Ext.getCmp('areaTree')
						.on('click', this.areaTreeNodeClick, this);
				Ext.getCmp('tradeTree').on('click', this.tradeTreeNodeClick,
						this);
				Ext.getCmp('backTree')
						.on('click', this.backTreeNodeClick, this);
				Ext.getCmp('controlTree').on('click',
						this.controlTreeNodeClick, this);
				Ext.getCmp('leftUserGrid').getSelectionModel().on('rowselect',
						this.userGridSelect, this);

				Ext.getCmp('netTree').on('dblclick', this.dblnetTreeNodeClick,
						this);
				Ext.getCmp('areaTree')
						.on('click', this.areaTreeNodeClick, this);
				Ext.getCmp('tradeTree').on('dblclick',
						this.dbltradeTreeNodeClick, this);
				Ext.getCmp('backTree').on('dblclick',
						this.dblbackTreeNodeClick, this);
				Ext.getCmp('controlTree').on('dblclick',
						this.dblcontrolTreeNodeClick, this);
				Ext.getCmp('leftUserGrid').on('rowdblclick',
						this.dblUserGridRowClick);

				// Ext.fly("checkBoxAllUser").dom.onclick=function(){
				// if(Ext.fly("checkBoxAllUser").dom.checked){
				// var grid = Ext.getCmp('leftUserGrid');
				// }
				// }

				Ext.getCmp('selectAllCheckBox').on('check', this.checkBoxClick,
						this);
			},
			dblnetTreeNodeClick : function(node, e) {
				this.nodedblClick(Ext.getCmp('netTree'), node, e);
			},
			dblareaTreeNodeClick : function(node, e) {
				this.nodedblClick(Ext.getCmp('areaTree'), node, e);
			},
			dbltradeTreeNodeClick : function(node, e) {
				this.nodedblClick(Ext.getCmp('tradeTree'), node, e);
			},
			dblbackTreeNodeClick : function(node, e) {
				this.nodedblClick(Ext.getCmp('backTree'), node, e);
			},
			dblcontrolTreeNodeClick : function(node, e) {
				this.nodedblClick(Ext.getCmp('controlTree'), node, e);
			},
			dblUserGridRowClick : function(grid, row, e) {
				// this.nodedblClick(grid, row, e);
			},
			netTreeNodeClick : function(node, e) {
				this.nodeClick(Ext.getCmp('netTree'), node, e);
			},
			areaTreeNodeClick : function(node, e) {
				this.nodeClick(Ext.getCmp('areaTree'), node, e);
			},
			tradeTreeNodeClick : function(node, e) {
				this.nodeClick(Ext.getCmp('tradeTree'), node, e);
			},
			backTreeNodeClick : function(node, e) {
				this.nodeClick(Ext.getCmp('backTree'), node, e);
			},
			controlTreeNodeClick : function(node, e) {
				this.nodeClick(Ext.getCmp('controlTree'), node, e);
			},
			userGridSelect : function(sm, row, record) {
				if (this.enableEvent)
					this.processUserGridSelect(sm, row, record);
			},
			nodeClick : function(p, node, e) {
				if (this.enableEvent)
					this.processClick(p, node, e);
			},
			nodedblClick : function(p, node, e) {
				if (this.enableEvent)
					this.processDblClick(p, node, e);
			},
			processClick : function(p, node, e) {
				var a = p;
				var b = node;
			},
			processDblClick : function(sourceObject, eventObject) {
				var a = sourceObject;
				var b = eventObject;

			},
			processUserGridSelect : function(sm, row, record) {
			},
			checkBoxClick : function(r, c) {
				if (this.enableEvent && c) {
					this.processUserGridAllSelect(Ext.getCmp('leftUserGrid'));
				}
			},
			processUserGridAllSelect : function(grid) {
			},
			removeListener : function() {
				Ext.getCmp('leftUserGrid').getSelectionModel().un('rowselect',
						this.userGridSelect, this);
				Ext.getCmp('netTree').un('click', this.netTreeNodeClick, this);
				Ext.getCmp('areaTree')
						.un('click', this.areaTreeNodeClick, this);
				Ext.getCmp('tradeTree').un('click', this.tradeTreeNodeClick,
						this);
				Ext.getCmp('backTree')
						.un('click', this.backTreeNodeClick, this);
				Ext.getCmp('controlTree').un('click',
						this.controlTreeNodeClick, this);

				Ext.getCmp('netTree').un('dblclick', this.dblnetTreeNodeClick,
						this);
				Ext.getCmp('areaTree')
						.un('click', this.areaTreeNodeClick, this);
				Ext.getCmp('tradeTree').un('dblclick',
						this.dbltradeTreeNodeClick, this);
				Ext.getCmp('backTree').un('dblclick',
						this.dblbackTreeNodeClick, this);
				Ext.getCmp('controlTree').un('dblclick',
						this.dblcontrolTreeNodeClick, this);
				Ext.getCmp('controlTree').un('dblclick',
						this.dblcontrolTreeNodeClick, this);
				Ext.getCmp('selectAllCheckBox').un('check', this.checkBoxClick,
						this);
			}
		});

Ext.override(Ext.Panel, {
			isOpenTree : '1'
		});
// 点击菜单打开页面的方法
function openTab(title, url, tabpanel, iconCls) {

	openTabB({
				title : title,
				url : url,
				tabpanel : tabpanel,
				iconCls : iconCls
			});
}

function openTabB(config) {
	var tabpanel = config.tabpanel;
	if (!tabpanel)
		tabpanel = Ext.getCmp('maintab');

	var tab = tabpanel.getComponent(config.title);
	if (tab) {
		tabpanel.setActiveTab(tab);
	} else {
		var p = new Ext.Panel({
					title : config.title,
					closable : true,
					isOpenTree : config.isOpenTree,
					iconCls : config.iconCls,
					// autoHeight : true,
					layout : 'fit',
					id : config.title,
					autoLoad : {
						url : config.url,
						nocache : true,
						text : 'Loading...',
						scripts : true,
						scope : p
					}
				});
		tabpanel.add(p);
		tabpanel.setActiveTab(p);
		p.on('activate', function(p) {
					if (p.isOpenTree == '0')
						Ext.getCmp('mainwest').collapse();
					else
						Ext.getCmp('mainwest').expand();

				});

		// p.removeAll(true);
		/*
		 * p.load({ //与上面autoLoad 效果一样 // url : 'testList.jsp', // nocache :
		 * true, // text : 'Loading...', // scripts : true, // scope : p // }); //
		 * p.doLayout();
		 */
	}
}