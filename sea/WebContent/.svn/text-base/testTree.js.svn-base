Ext.onReady(function() {

			var rootNode = new Ext.tree.AsyncTreeNode({
						id : '0',
						text : '树根'
					});

			var ptbar = new Ext.ux.tree.PagingTreeToolbar();
			var treeLoader = new Ext.ux.tree.PagingTreeLoader({
						autoScroll : true,
						toolbar : ptbar,
						dataUrl : './sysman/generaltree!userTree.action'
					});

			var tree = new Ext.tree.TreePanel({
						width : 400,
						height : 400,
						root : rootNode,
						loader : treeLoader,
						bbar : ptbar
					});

			var viewport = new Ext.Viewport({
						layout : 'fit',
						items : tree
					});

		});

Ext.ns("Ext.ux.tree");

Ext.ux.tree.PagingTreeLoader = function(config) {
	this.toolbar = config.pagingBar || new Ext.ux.tree.PagingTreeToolbar();
	this.pageSize = config.pageSize || 20;
	Ext.ux.tree.PagingTreeLoader.superclass.constructor.apply(this, arguments);
};

Ext.extend(Ext.ux.tree.PagingTreeLoader, Ext.tree.TreeLoader, {
			doPreload : function(treeNode) {
				var attr = treeNode.attributes;
				var pagingInfo = attr.pagingInfo;
				if (pagingInfo == undefined) {
					pagingInfo = {
						limit : this.toolbar.pageSize || 20,
						start : 0
					};
					treeNode.attributes.pagingInfo = pagingInfo;
				}
				Ext.apply(this.baseParams, pagingInfo);
				return false;
			},
			processResponse : function(response, node, callback) {
				var json = Ext.decode(response.responseText);

				var pi = json.pageInfo;
				var nodes = pi.result;
				node.beginUpdate();
				node.attributes.totalCount = pi.totalCount;
				node.attributes.totalPages = pi.totalPages;
				node.attributes.activepage = pi.prePage + 1;
				node.attributes.count = nodes.length;
				for (var i = 0; i < nodes.length; i++) {
					var n = this.createNode(nodes[i]);
					if (n) {
						// n.attributes.others=nodes[i].others; //附加其它属性(自定义属性)
                        //n.attributes.apply(node[i]);  //附加其它属性(自定义属性)
						node.appendChild(n);
						var tb = node.getOwnerTree().getBottomToolbar();
						n.on('beforeclick', function(n) {
									tb.bind(n);
									return true;
								});
					}

				}
				node.endUpdate();
				this.toolbar.bind(node);
				if (typeof callback == "function") {
					callback(this, node);
				}
			}
		});

var T = Ext.Toolbar;
Ext.ux.tree.PagingTreeToolbar = Ext.extend(Ext.Toolbar, {
	pageSize : 20,
	displayMsg : '{0}~{1}/{2}',
	emptyMsg : '没有数据',
	beforePageText : '',
	afterPageText : '/{0}页',
	firestText : '首页',
	preText : '上页',
	nextText : '下页',
	lastText : '尾页',
	refreshText : '刷新',
	displayInfo : true,
	// 构造方法
	initComponent : function() {
		var pagingItems = [this.first = new T.Button({
							tooltip : this.firstText,
							overflowText : this.firstText,
							iconCls : 'x-tbar-page-first',
							disabled : true,
							handler : this.moveFirst,
							scope : this
						}), this.prev = new T.Button({
							tooltip : this.prevText,
							overflowText : this.prevText,
							iconCls : 'x-tbar-page-prev',
							disabled : true,
							handler : this.movePrevious,
							scope : this
						}), '-', this.beforePageText,
				this.inputItem = new Ext.form.NumberField({
							cls : 'x-tbar-page-number',
							allowDecimals : false,
							allowNegative : false,
							enableKeyEvents : true,
							selectOnFocus : true,
							width : 22,
							listeners : {
								scope : this,
								keydown : this.onPagingKeyDown,
								blur : this.onPagingBlur
							}
						}), this.afterTextItem = new T.TextItem({
							text : String.format(this.afterPageText, 1)
						}), '-', this.next = new T.Button({
							tooltip : this.nextText,
							overflowText : this.nextText,
							iconCls : 'x-tbar-page-next',
							disabled : true,
							handler : this.moveNext,
							scope : this
						}), this.last = new T.Button({
							tooltip : this.lastText,
							overflowText : this.lastText,
							iconCls : 'x-tbar-page-last',
							disabled : true,
							handler : this.moveLast,
							scope : this
						}), '-', this.refresh = new T.Button({
							tooltip : this.refreshText,
							overflowText : this.refreshText,
							iconCls : 'x-tbar-loading',
							handler : this.refresh,
							scope : this
						}), '-', this.inputItem2 = new Ext.form.NumberField({
							cls : 'x-tbar-page-number',
							allowDecimals : false,
							allowNegative : false,
							enableKeyEvents : true,
							selectOnFocus : true,
							width : 22,
							value : 20,
							listeners : {
								scope : this,
								keydown : this.onPagingKeyDown2,
								blur : this.onPagingBlur2,
								change : function(t, n, o) {
									t.setValue(n);
								}
							}
						}), '/页'];
		var userItems = this.items || this.buttons || [];
		if (this.prependButtons) {
			this.items = userItems.concat(pagingItems);
		} else {
			this.items = pagingItems.concat(userItems);
		}
		delete this.buttons;
		if (this.displayInfo) {
			this.items.push('-');
			this.items.push(this.displayItem = new T.TextItem({}));
		}
		Ext.ux.tree.PagingTreeToolbar.superclass.initComponent.call(this);
		// this.addEvents('change', 'beforechange');
		this.on('afterlayout', this.onFirstLayout, this, {
					single : true
				});
		this.cursor = 0;
		this.bindTreeNode(this.treeNode);
	},
	onFirstLayout : function() {
		// if (this.treeNodeLoaded) {
		// this.onLoad.apply(this, this.treeNodeLoaded);
		// }
	},
	updateInfo : function(treeNode) {
		if (this.displayItem) {
			var count = treeNode.attributes.count;
			var msg = count == 0 ? this.emptyMsg : String.format(
					this.displayMsg, this.cursor + 1, this.cursor + count,
					treeNode.attributes.totalCount);
			this.displayItem.setText(msg);

		}
	},
	onLoad : function(treeNode, r, o) {
		var pagingInfo = treeNode.attributes.pagingInfo;
        //对未展开的节点赋初始值（与后台子节点数据不等，必须展开后才取得实际值）
		if (pagingInfo == undefined) {
			pagingInfo = {
				limit : this.pageSize || 20,
				start : 0
			};
			treeNode.attributes.pagingInfo = pagingInfo;
			treeNode.attributes.totalPages = 1;
			treeNode.attributes.totalCount = 0;
			treeNode.attributes.count = 0;
		}

		var cur = treeNode.attributes.pagingInfo.start;
		this.cursor = cur > 0 ? cur : 0;
		var ps = treeNode.attributes.totalPages; // 总页数
		var ap = Math.ceil((this.cursor + this.pageSize) / this.pageSize); // 当前页

		this.afterTextItem.setText(String.format(this.afterPageText, ps));
		this.inputItem.setValue(ap);
		this.first.setDisabled(ap == 1);
		this.prev.setDisabled(ap == 1);
		this.next.setDisabled(ap == ps);
		this.last.setDisabled(ap == ps);
		this.refresh.enable();
		this.updateInfo(treeNode);
		// this.fireEvent('change', this, d);

	},
	getPageData : function() {
		var total = this.treeNode.attributes.totalCount;
		return {
			total : total,
			activePage : Math.ceil((this.cursor + this.pageSize)
					/ this.pageSize),
			pages : total < this.pageSize ? 1 : Math
					.ceil(total / this.pageSize)

		}
	},
	bindTreeNode : function(treeNode, initial) {
		var doLoad;
		this.treeNode = treeNode;
		// 点中叶子节点的情况下绑定其父节点
		if (this.treeNode)
			if (this.treeNode.isLeaf())
				this.bindTreeNode(treeNode.parentNode);
			else {
				this.onLoad(this.treeNode);
			}

	},
	unbind : function(treeNode) {
		this.bindTreeNode(null);
	},
	bind : function(treeNode) {
		this.bindTreeNode(treeNode);
	},

	moveFirst : function() {
		this.doLoad(0);
	},
	movePrevious : function() {
		this.doLoad(Math.max(0, this.cursor - this.pageSize));
	},
	moveNext : function() {
		this.doLoad(this.cursor + this.pageSize);
	},
	moveLast : function() {
		var total = this.treeNode.attributes.totalCount;
		var extra = total % this.pageSize;

		this.doLoad(extra ? (total - extra) : total - this.pageSize);

	},
	refresh : function() {
		this.doLoad(this.cursor);
	},
	doLoad : function(start) {
		this.treeNode.attributes.pagingInfo.start = start;
		this.treeNode.attributes.pagingInfo.limit = this.pageSize;
		this.treeNode.reload();

	},
	// private
	readPage : function(d) {
		var v = this.inputItem.getValue(), pageNum;
		if (!v || isNaN(pageNum = parseInt(v, 10))) {
			this.inputItem.setValue(d.activePage);
			return false;
		}
		return pageNum;
	},

	onPagingKeyDown : function(field, e) {
		var k = e.getKey(), d = this.getPageData(), pageNum;
		if (k == e.RETURN) {
			e.stopEvent();
			pageNum = this.readPage(d);
			if (pageNum !== false) {
				pageNum = Math.min(Math.max(1, pageNum), d.pages) - 1;
				this.doLoad(pageNum * this.pageSize);
			}
		} else if (k == e.HOME || k == e.END) {
			e.stopEvent();
			pageNum = k == e.HOME ? 1 : d.pages;
			field.setValue(pageNum);
		} else if (k == e.UP || k == e.PAGEUP || k == e.DOWN || k == e.PAGEDOWN) {
			e.stopEvent();
			if ((pageNum = this.readPage(d))) {
				var increment = e.shiftKey ? 10 : 1;
				if (k == e.DOWN || k == e.PAGEDOWN) {
					increment *= -1;
				}
				pageNum += increment;
				if (pageNum >= 1 & pageNum <= d.pages) {
					field.setValue(pageNum);
				}
			}
		}

	},
	onPagingBlur : function(e) {
		this.inputItem.setValue(this.getPageData().activePage);
	},
	onPagingKeyDown2 : function(field, e) {
		var k = e.getKey();
		if (k == e.RETURN) {
			e.stopEvent();
			pageSize = field.getValue();
			pageSize = Math.min(Math.max(15, pageSize), 1000);
			this.pageSize = pageSize;
			this.doLoad(0);
		}

	},
	onPagingBlur2 : function(e) {
		this.inputItem2.setValue(this.pageSize);
	},

	onDestroy : function() {
		this.bindTreeNode(null);
		Ext.ux.tree.PagingTreeToolbar.superclass.onDestroy.call(this);
	},

	onPagingFocus : function() {
		this.inputItem.select();
	}

});

Ext.reg("treePaging", Ext.ux.tree.PagingTreeToolbar);