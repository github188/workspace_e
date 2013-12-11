// 解决 firefox下Permission denied to access property 'dom' from a non-chrome
// context
Ext.override(Ext.Element, {
	contains : function() {
		var isXUL = Ext.isGecko ? function(node) {
			return Object.prototype.toString.call(node) == '[object XULElement]';
		}
				: Ext.emptyFn;

		return function(el) {
			return !this.dom.firstChild || // if this Element
					// has no
					// children, return false
					// immediately
					!el || isXUL(el) ? false : Ext.lib.Dom.isAncestor(this.dom,
					el.dom ? el.dom : el);
		};
	}(),
	// 解决grid load 在IE8下一直显示 loadmask 的问题
	setWidth : function(width, animate) {
		var me = this;
		if (!isNaN(width)) {
			width = me.adjustWidth(width);
			!animate || !me.anim ? me.dom.style.width = me.addUnits(width) : me
					.anim({
								width : {
									to : width
								}
							}, me.preanim(arguments, 1));
		}
		return me;
	}
});

// 实现中文拼音顺序排序
Ext.data.Store.prototype.applySort = function() {
	if (this.sortInfo && !this.remoteSort) {
		var s = this.sortInfo, f = s.field;
		var st = this.fields.get(f).sortType;
		var fn = function(r1, r2) {
			var v1 = st(r1.data[f]), v2 = st(r2.data[f]);
			if (typeof(v1) == "string") {
				return v1.localeCompare(v2);
			}
			return v1 > v2 ? 1 : (v1 < v2 ? -1 : 0);
		};
		this.data.sort(s.direction, fn);
		if (this.snapshot && this.snapshot != this.data) {
			this.snapshot.sort(s.direction, fn);
		}
	}

}
/**
 * 中文化补充
 * @author zhangzhw
 */

Ext.override(Ext.grid.GroupingView,{
groupByText : '按此字段分组',
showGroupsText : '在分组中显示',
 emptyGroupText : '(无)'



});

/**
 * 为导出 Excel 使用的方法
 * 
 * @author zhangzhw
 */
var Base64 = (function() {
	// Private property
	var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

	// Private method for UTF-8 encoding
	function utf8Encode(string) {
		string = string.replace(/\r\n/g, "\n");
		var utftext = "";
		for (var n = 0; n < string.length; n++) {
			var c = string.charCodeAt(n);
			if (c < 128) {
				utftext += String.fromCharCode(c);
			} else if ((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			} else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}
		}
		return utftext;
	}

	// Public method for encoding
	return {
		encode : (typeof btoa == 'function') ? function(input) {
			return btoa(utf8Encode(input));
		} : function(input) {
			var output = "";
			var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
			var i = 0;
			input = utf8Encode(input);
			while (i < input.length) {
				chr1 = input.charCodeAt(i++);
				chr2 = input.charCodeAt(i++);
				chr3 = input.charCodeAt(i++);
				enc1 = chr1 >> 2;
				enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
				enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
				enc4 = chr3 & 63;
				if (isNaN(chr2)) {
					enc3 = enc4 = 64;
				} else if (isNaN(chr3)) {
					enc4 = 64;
				}
				output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
						+ keyStr.charAt(enc3) + keyStr.charAt(enc4);
			}
			return output;
		}
	};
})();

/**
 * 解决 IE8 下 grid 列选择器显示不正常的问题
 * 
 * @author zhangzhw
 */
Ext.override(Ext.grid.GridView, {
			renderUI : function() {

				var header = this.renderHeaders();
				var body = this.templates.body.apply({
							rows : '&#160;'
						});

				var html = this.templates.master.apply({
							body : body,
							header : header,
							ostyle : 'width:' + this.getOffsetWidth() + ';',
							bstyle : 'width:' + this.getTotalWidth() + ';'
						});

				var g = this.grid;

				g.getGridEl().dom.innerHTML = html;

				this.initElements();

				// get mousedowns early
				Ext.fly(this.innerHd).on('click', this.handleHdDown, this);
				this.mainHd.on({
							scope : this,
							mouseover : this.handleHdOver,
							mouseout : this.handleHdOut,
							mousemove : this.handleHdMove
						});

				this.scroller.on('scroll', this.syncScroll, this);
				if (g.enableColumnResize !== false) {
					this.splitZone = new Ext.grid.GridView.SplitDragZone(g,
							this.mainHd.dom);
				}

				if (g.enableColumnMove) {
					this.columnDrag = new Ext.grid.GridView.ColumnDragZone(g,
							this.innerHd);
					this.columnDrop = new Ext.grid.HeaderDropZone(g,
							this.mainHd.dom);
				}

				if (g.enableHdMenu !== false) {
					this.hmenu = new Ext.menu.Menu({
								id : g.id + '-hctx'
							});
					this.hmenu.add({
								itemId : 'asc',
								text : this.sortAscText,
								cls : 'xg-hmenu-sort-asc'
							}, {
								itemId : 'desc',
								text : this.sortDescText,
								cls : 'xg-hmenu-sort-desc'
							});
					if (g.enableColumnHide !== false) {
						this.colMenu = new Ext.menu.Menu({
									id : g.id + '-hcols-menu',
									width : 140
								});
						this.colMenu.on({
									scope : this,
									beforeshow : this.beforeColMenuShow,
									itemclick : this.handleHdMenuClick
								});
						this.hmenu.add('-', {
									itemId : 'columns',
									hideOnClick : false,
									text : this.columnsText,
									menu : this.colMenu,
									iconCls : 'x-cols-icon'
								});
					}
					this.hmenu.on('itemclick', this.handleHdMenuClick, this);
				}

				if (g.trackMouseOver) {
					this.mainBody.on({
								scope : this,
								mouseover : this.onRowOver,
								mouseout : this.onRowOut
							});
				}

				if (g.enableDragDrop || g.enableDrag) {
					this.dragZone = new Ext.grid.GridDragZone(g, {
								ddGroup : g.ddGroup || 'GridDD'
							});
				}

				this.updateHeaderSortState();

			}
		});

/**
 * 添加导出功能。 对后台分页添加后台导出功能。
 * 
 * @author zhangzhw
 */
Ext.override(Ext.grid.GridPanel, {
	getExcelXml : function(includeHidden, notIncludeData) {
		var worksheet = this.createWorksheet(includeHidden, notIncludeData);
		var totalWidth = this.getColumnModel().getTotalWidth(includeHidden);
		return '<xml version="1.0" encoding="utf-8">'
				+ '<ss:Workbook xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns:o="urn:schemas-microsoft-com:office:office">'
				+ '<o:DocumentProperties><o:Title>'
				+ this.title
				+ '</o:Title></o:DocumentProperties>'
				+ '<ss:ExcelWorkbook>'
				+ '<ss:WindowHeight>'
				+ worksheet.height
				+ '</ss:WindowHeight>'
				+ '<ss:WindowWidth>'
				+ worksheet.width
				+ '</ss:WindowWidth>'
				+ '<ss:ProtectStructure>False</ss:ProtectStructure>'
				+ '<ss:ProtectWindows>False</ss:ProtectWindows>'
				+ '</ss:ExcelWorkbook>'
				+ '<ss:Styles>'
				+ '<ss:Style ss:ID="Default">'
				+ '<ss:Alignment ss:Vertical="Top" ss:WrapText="1" />'
				+ '<ss:Font ss:FontName="arial" ss:Size="10" />'
				+ '<ss:Borders>'
				+ '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Top" />'
				+ '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Bottom" />'
				+ '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Left" />'
				+ '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Right" />'
				+ '</ss:Borders>'
				+ '<ss:Interior />'
				+ '<ss:NumberFormat />'
				+ '<ss:Protection />'
				+ '</ss:Style>'
				+ '<ss:Style ss:ID="title">'
				+ '<ss:Borders />'
				+ '<ss:Font />'
				+ '<ss:Alignment ss:WrapText="1" ss:Vertical="Center" ss:Horizontal="Center" />'
				+ '<ss:NumberFormat ss:Format="@" />' + '</ss:Style>'
				+ '<ss:Style ss:ID="headercell">'
				+ '<ss:Font ss:Bold="1" ss:Size="10" />'
				+ '<ss:Alignment ss:WrapText="1" ss:Horizontal="Center" />'
				+ '<ss:Interior ss:Pattern="Solid" ss:Color="#A3C9F1" />'
				+ '</ss:Style>' + '<ss:Style ss:ID="even">'
				+ '<ss:Interior ss:Pattern="Solid" ss:Color="#CCFFFF" />'
				+ '</ss:Style>'
				+ '<ss:Style ss:Parent="even" ss:ID="evendate">'
				+ '<ss:NumberFormat ss:Format="yyyy-mm-dd" />' + '</ss:Style>'
				+ '<ss:Style ss:Parent="even" ss:ID="evenint">'
				+ '<ss:NumberFormat ss:Format="0" />' + '</ss:Style>'
				+ '<ss:Style ss:Parent="even" ss:ID="evenfloat">'
				+ '<ss:NumberFormat ss:Format="0.00" />' + '</ss:Style>'
				+ '<ss:Style ss:ID="odd">'
				+ '<ss:Interior ss:Pattern="Solid" ss:Color="#CCCCFF" />'
				+ '</ss:Style>' + '<ss:Style ss:Parent="odd" ss:ID="odddate">'
				+ '<ss:NumberFormat ss:Format="yyyy-mm-dd" />' + '</ss:Style>'
				+ '<ss:Style ss:Parent="odd" ss:ID="oddint">'
				+ '<ss:NumberFormat ss:Format="0" />' + '</ss:Style>'
				+ '<ss:Style ss:Parent="odd" ss:ID="oddfloat">'
				+ '<ss:NumberFormat ss:Format="0.00" />' + '</ss:Style>'
				+ '</ss:Styles>' + worksheet.xml + '</ss:Workbook>';
	},

	createWorksheet : function(includeHidden, notIncludeData) {
		// Calculate cell data types and extra class names which affect
		// formatting
		var cellType = [];
		var cellTypeClass = [];
		var cm = this.getColumnModel();
		var totalWidthInPixels = 0;
		var colXml = '';
		var headerXml = '';
		var visibleColumnCountReduction = 0;
		var colCount = cm.getColumnCount();
		for (var i = 0; i < colCount; i++) {
			if ((cm.getDataIndex(i) != '')
					&& (includeHidden || !cm.isHidden(i))) {
				var w = cm.getColumnWidth(i)
				totalWidthInPixels += w;
				if (cm.getColumnHeader(i) === "") {
					cellType.push("None");
					cellTypeClass.push("");
					++visibleColumnCountReduction;
				} else {
					colXml += '<ss:Column ss:AutoFitWidth="1" ss:Width="' + w
							+ '" />';
					headerXml += '<ss:Cell ss:StyleID="headercell">'
							+ '<ss:Data ss:Type="String">'
							+ cm.getColumnHeader(i)
							+ '</ss:Data>'
							+ '<ss:NamedCell ss:Name="Print_Titles" /></ss:Cell>';
					var fld = this.store.recordType.prototype.fields.get(cm
							.getDataIndex(i));
					fld = fld || {};		
					switch (fld.type) {
						case "int" :
							cellType.push("Number");
							cellTypeClass.push("int");
							break;
						case "float" :
							cellType.push("Number");
							cellTypeClass.push("float");
							break;
						case "bool" :
						case "boolean" :
							cellType.push("String");
							cellTypeClass.push("");
							break;
						case "date" :
							cellType.push("DateTime");
							cellTypeClass.push("date");
							break;
						default :
							cellType.push("String");
							cellTypeClass.push("");
							break;
					}
				}
			}
		}
		var visibleColumnCount = cellType.length - visibleColumnCountReduction;

		var result = {
			height : 9000,
			width : Math.floor(totalWidthInPixels * 30) + 50
		};

		var rowCount = 0;
		var copyStore = this.store;
		if (!Ext.isEmpty(this.getBottomToolbar().allStore) && this.getBottomToolbar().expAllFlage) {
			this.store = this.getBottomToolbar().allStore;
			this.getBottomToolbar().expAllFlage = false;
		}
		if (!notIncludeData)
			rowCount = (this.store.getCount() + 2);
		else
			rowCount = "rowCountToreplace";
		// Generate worksheet header details.
		var t = '<ss:Worksheet ss:Name="' + this.title + '">' + '<ss:Names>'
				+ '<ss:NamedRange ss:Name="Print_Titles" ss:RefersTo="=\''
				+ this.title + '\'!R1:R2" />' + '</ss:Names>'
				+ '<ss:Table x:FullRows="1" x:FullColumns="1"'
				+ ' ss:ExpandedColumnCount="' + (visibleColumnCount + 2)
				+ '" ss:ExpandedRowCount="' + rowCount + '">' + colXml
				// + '<ss:Row ss:Height="38">'
				// + '<ss:Cell ss:StyleID="title" ss:MergeAcross="'
				// + (visibleColumnCount - 1)
				// + '">'
				// + '<ss:Data xmlns:html="http://www.w3.org/TR/REC-html40"
				// ss:Type="String">'
				// // + '<html:B>Generated by
				// ExtJS</html:B></ss:Data><ss:NamedCell
				// // ss:Name="Print_Titles" />'
				// + '<html:B></html:B></ss:Data><ss:NamedCell
				// ss:Name="Print_Titles" />'
				// + '</ss:Cell>' + '</ss:Row>'
				+ '<ss:Row ss:AutoFitHeight="1">' + headerXml + '</ss:Row>';

		// Generate the data rows from the data in the Store
		if (!notIncludeData)
			for (var i = 0, it = this.store.data.items, l = it.length; i < l; i++) {
				t += '<ss:Row>';
				var cellClass = (i & 1) ? 'odd' : 'even';
				r = it[i].data;
				var k = 0;
				for (var j = 0; j < colCount; j++) {
					if ((cm.getDataIndex(j) != '')
							&& (includeHidden || !cm.isHidden(j))) {
						var v = r[cm.getDataIndex(j)];
						if (cellType[k] !== "None") {
							t += '<ss:Cell ss:StyleID="' + cellClass
									+ cellTypeClass[k] + '"><ss:Data ss:Type="'
									+ cellType[k] + '">';
							if (cellType[k] == 'DateTime') {
								t += v.format('Y-m-d');
							} else {
								t += v;
							}
							t += '</ss:Data></ss:Cell>';
						}
						k++;
					}
				}
				t += '</ss:Row>';
			}
		else {
			t += 'gridStoreData';
		}
		this.store = copyStore;
		result.xml = t
				+ '</ss:Table>'
				+ '<x:WorksheetOptions>'
				+ '<x:PageSetup>'
				+ '<x:Layout x:CenterHorizontal="1" x:Orientation="Landscape" />'
				+ '<x:Footer x:Data="Page &amp;P of &amp;N" x:Margin="0.5" />'
				+ '<x:PageMargins x:Top="0.5" x:Right="0.5" x:Left="0.5" x:Bottom="0.8" />'
				+ '</x:PageSetup>' + '<x:FitToPage />' + '<x:Print>'
				+ '<x:PrintErrors>Blank</x:PrintErrors>'
				+ '<x:FitWidth>1</x:FitWidth>'
				+ '<x:FitHeight>32767</x:FitHeight>' + '<x:ValidPrinterInfo />'
				+ '<x:VerticalResolution>600</x:VerticalResolution>'
				+ '</x:Print>' + '<x:Selected />'
				+ '<x:DoNotDisplayGridlines />'
				+ '<x:ProtectObjects>False</x:ProtectObjects>'
				+ '<x:ProtectScenarios>False</x:ProtectScenarios>'
				+ '</x:WorksheetOptions>' + '</ss:Worksheet>';
		return result;
	},
	createCMInfo : function(includeHidden) {
		var cmDataIndex = '';
		var cmDataType = '';
		var result = {};
		var cm = this.getColumnModel();
		var colCount = cm.getColumnCount();
		for (var i = 0; i < colCount; i++) {
			if ((cm.getDataIndex(i) != '')
					&& (includeHidden || !cm.isHidden(i))) {
				var fld = this.store.recordType.prototype.fields.get(cm
						.getDataIndex(i));
				fld = fld || {};						
				switch (fld.type) {
					case "int" :
						cmDataIndex += cm.getDataIndex(i) + ',';
						cmDataType += "String,";
						break;
					case "float" :
						cmDataIndex += cm.getDataIndex(i) + ',';
						cmDataType += "String,";
						break;
					case "bool" :
					case "boolean" :
						cmDataIndex += cm.getDataIndex(i) + ',';
						cmDataType += "String,";
						break;
					case "date" :
						cmDataIndex += cm.getDataIndex(i) + ',';
						cmDataType += "DateTime,";
						break;
					default :
						cmDataIndex += cm.getDataIndex(i) + ',';
						cmDataType += "String,";
						break;
				}
			}
		}

		result.cmDataIndex = cmDataIndex;
		result.cmDataType = cmDataType;
		return result;
	},
	expPage : function() {
		var vExportContent = this.getExcelXml();
		if (Ext.isIE6 || Ext.isIE7 || Ext.isIE8 || Ext.isSafari
				|| Ext.isSafari2 || Ext.isSafari3) {
			var fd = Ext.get('frmDummy');
			if (!fd) {
				fd = Ext.DomHelper.append(Ext.getBody(), {
							tag : 'form',
							method : 'post',
							id : 'frmDummy',
							action : './exportexcel.jsp',
							target : '_blank',
							name : 'frmDummy',
							cls : 'x-hidden',
							cn : [{
										tag : 'input',
										name : 'exportContent',
										id : 'exportContent',
										type : 'hidden'
									}]
						}, true);
			}
			fd.child('#exportContent').set({
						value : vExportContent
					});
			fd.dom.submit();
		} else {
			document.location = 'data:application/vnd.ms-excel;base64,'
					+ Base64.encode(vExportContent);
		}
	},
	expAll : function() {
		var cm = this.getExcelXml(false, true);
		var url = this.store.url || this.store.proxy.url;
		if (Ext.isEmpty(url)) {
			this.expPage();
			return;
		}

		var dataMethod = this.getDataMethod(url);
		url = this.processUrl(url);

		url = Ext.urlAppend(url, Ext.urlEncode(this.store.baseParams));
		var rootProperty = this.store.reader.meta.root;
		var cmDataType = this.createCMInfo().cmDataType;
		var cmDataIndex = this.createCMInfo().cmDataIndex;

		var fd = Ext.get('exportExcel');
		if (!fd) {
			fd = Ext.DomHelper.append(Ext.getBody(), {
						tag : 'form',
						method : 'post',
						id : 'exportExcel',
						action : url,
						// target : '_blank',
						name : 'exportExcel',
						cls : 'x-hidden',
						cn : [{
									tag : 'input',
									name : 'dataMethod',
									id : 'dataMethod',
									type : 'hidden'
								}, {
									tag : 'input',
									name : 'rootProperty',
									id : 'rootProperty',
									type : 'hidden'
								}, {
									tag : 'input',
									name : 'cmDataType',
									id : 'cmDataType',
									type : 'hidden'
								}, {
									tag : 'input',
									name : 'cmDataIndex',
									id : 'cmDataIndex',
									type : 'hidden'
								}, {
									tag : 'input',
									name : 'cm',
									id : 'cm',
									type : 'hidden'
								}, {
									tag : 'input',
									name : 'limit',
									id : 'limit',
									type : 'hidden',
									value : 32767
								},
								{
									tag : 'input',
									name : 'start',
									id : 'start',
									type : 'hidden',
									value : 0
								}
								]
					}, true);
		}
        fd.dom.action=url;
		fd.child('#dataMethod').set({
					value : dataMethod
				});
		fd.child('#rootProperty').set({
					value : rootProperty
				});
		fd.child('#cmDataType').set({
					value : cmDataType
				});
		fd.child('#cmDataIndex').set({
					value : cmDataIndex
				});
		fd.child('#cm').set({
					value : cm
				});
		fd.dom.submit();
	},

	processUrl : function(url) {

		var newurl = "";
		if (url.indexOf("!") > 0)
			newurl = url.replace(/!.*\.action/, '!exportExcel.action');
		else
			newurl = url.replace(/\.action/, '!exportExcel.action');
		return newurl;
	},
	getDataMethod : function(url) {
		if (url.indexOf('!') < 1)
			return 'execute';
		var i = url.indexOf('!');
		var j = url.indexOf('.action');
		return url.substr(i + 1, j - i - 1);
	},
	onDisable : function() {
		if (this.rendered && this.maskDisabled) {
			this.body.mask();
		}
	},
	onEnable : function() {
		if (this.rendered && this.maskDisabled) {
			this.body.unmask();
		}
	}
});

/**
 * 自定义分页按钮
 * 
 * @author zhangzhw
 */
Ext.namespace("Ext.ux");
Ext.ux.MyToolbar = Ext.extend(Ext.PagingToolbar, {
			pageSize : 50,
			enableExpAll : false,
			enableExpPage : true,
			expAllText : "全部",
			expPageText : "当前页",
			displayInfo : true,
			initComponent : function() {
				var psize = new Ext.form.NumberField({
							cls : 'x-tbar-page-number',
							allowDecimals : false,
							allowNegative : false,
							enableKeyEvents : true,
							value : this.pageSize,
							selectOnFocus : true,
							listeners : {
								scope : this,
								keypress : function(field, e) {
									var k = e.getKey(), pageSize;
									if (k == e.RETURN) {
										// e.stopEvent();

										pageSize = field.getValue();

										pageSize = Math.min(Math.max(15,
														pageSize), 1000);
										// Ext.MessageBox.alert(pageSize);
										this.pageSize = pageSize;
										this.store.load({
													params : {
														start : 0,
														limit : pageSize
													}
												});
									}

								},
								change : function(t, n, o) {
									t.setValue(n);
								}
							}
						});

				if (this.enableExpAll || this.enableExpPage) {
					spExpBtn = new Ext.SplitButton({
								iconCls : 'exportExcel',
								tooltip : '导出EXCEL',
								handler : function(b) {
									b.showMenu();
								},
								menu : new Ext.menu.Menu([])

							});
					if (this.enableExpAll)
						spExpBtn.menu.addItem(new Ext.menu.Item({
									text : this.expAllText,
									iconCls : 'exportExcel',
									tooltip : '导出所有数据',
									handler : this.expAll.createDelegate(this)
								}));
					if (this.enableExpPage)
						spExpBtn.menu.addItem(new Ext.menu.Item({
									text : this.expPageText,
									iconCls : 'exportExcel',
									tooltip : '导出当前页',
									handler : this.expPage.createDelegate(this)
								}));
					this.items = ['-', spExpBtn, '-', psize, '条/页'];
				} else {
					this.items = ['-', psize, '条/页'];
				}

				if (this.displayInfo) {
					this.items.push('->');
					this.items.push(this.displayItem = new T.TextItem({}));
				}

				Ext.ux.MyToolbar.superclass.initComponent.call(this);
			},
			//longkc修正开始
			//前台分页时为动态生成store时，动态变更前台全局store
		    bindAllStore : function(allStore){
				if(allStore){
		        	allStore = Ext.StoreMgr.lookup(allStore);
		        }
		        this.allStore = allStore;
		    },
		    //longkc修正结束
			expPage : function() {
				var grid = this.ownerCt;
				grid.expPage();
			},
			expAll : function() {
				var grid = this.ownerCt;

				if (this.store.mm) {
					grid.local = true;
					grid.expPage();
					grid.local = false;
					return;
				}
				if (Ext.isEmpty(this.allStore)) {
					grid.expAll();
				} else {
					this.expAllFlage = true;
					grid.expPage();
				}				
				/*
				 * Ext.Ajax.request({ url : url, params : { cmDataType :
				 * cmDataType, cmDataIndex : cmDataIndex, rootProperty :
				 * rootProperty, dataMethod : dataMethod, cm : cm, start : 0,
				 * limit : 65535 // Excel 不能超过65535条数据 }, success :
				 * function(response) { alert(true); } });
				 */
			}
		});
Ext.reg('myToolbar', Ext.ux.MyToolbar);

// 通用下拉列表
Ext.namespace("Ext.ux");
Ext.ux.ComboBox = Ext.extend(Ext.form.ComboBox, {
	//添加三个属性，其它属性默认
			sql:'',     //全使用大写
			valueField:'VALUE',  //全使用大写（后台返回字段为大写）
			displayField:'DISP',    //全使用大写
			initComponent : function() {
				var actionurl = './sysman/dictionary!publicDictionary.action';
				this.store = new Ext.data.JsonStore({
							autoLoad : true,
							url : Ext.urlAppend(actionurl, this.sql),
							fields : [this.valueField, this.displayField],
							root : 'dicList'
						});

				Ext.ux.ComboBox.superclass.initComponent.call(this);

			}

		});

Ext.reg("commcombobox", Ext.ux.ComboBox);


/**
 * 以下为自定义图片按钮
 * 
 * @author zhangzhw
 * @description 仅用于主页一级菜单按钮
 */
Ext.namespace('Ext.ux');
Ext.ux.ImageButton = function(cfg) {
	Ext.ux.ImageButton.superclass.constructor.call(this, cfg);
};
Ext.extend(Ext.ux.ImageButton, Ext.Button, {
	onRender : function(ct, position) {
		this.disabledImgPath = this.disabledImgPath || this.imgPath;
		var tplHTML = '<div><table><tr><td width="64" height="64" align="center" valign="top"><img src="{imgPath}" border="0" width="{imgWidth}" height="{imgHeight}" alt="{tooltip}" style="cursor: {cursor};"/></td></table></div>';
		var tpl = new Ext.Template(tplHTML);
		var btn, targs = {
			imgPath : this.disabled ? this.disabledImgPath : this.imgPath,
			imgWidth : this.imgWidth || "",
			imgHeight : this.imgHeight || "",
			imgText : this.text || "",
			cursor : this.disabled ? "default" : "pointer",
			tooltip : this.tooltip || ""

		};
		btn = tpl.append(ct, targs, true);
		btn.on("click", this.onClick, this);
		if (this.cls) {
			btn.addClass(this.cls);
		}
		this.el = btn;
		if (this.hidden) {
			this.hide();
		}
	},
	disable : function(newImgPath) {
		var replaceImgPath = newImgPath || this.disabledImgPath;
		if (replaceImgPath)
			this.el.dom.firstChild.src = replaceImgPath;
		this.disabled = true;
	},
	enable : function(newImgPath) {
		var replaceImgPath = newImgPath || this.imgPath;
		if (replaceImgPath)
			this.el.dom.firstChild.src = replaceImgPath;
		this.disabled = false;
	}
});
Ext.reg('imagebutton', Ext.ux.ImageButton);

// var publicLoadMask = new Ext.LoadMask(Ext.getBody(), {
// msg : "加载中..."
// });

/**
 * 方法 requestException 处理网络请求失败的方法
 * 
 * @param {}
 *            conn
 * @param {}
 *            response
 * @param {}
 *            options
 * @author zhangzhw
 */
function requestException(conn, response, options) {
	// publicLoadMask.enable();
	// publicLoadMask.hide();
	// TODO : 替换为 Ext.MessageBox.alert('错误','网络连接失败或请求失败！'); 方法
	// alert('网络连接失败或请求失败！');
	Ext.MessageBox.alert('错误', '网络连接失败或请求失败！');
	options.failure = false;
}

Ext.Ajax.on('requestexception', requestException);

/**
 * 方法 requestPreProcess 通用request 异常处理
 * 
 * @param {}
 *            conn
 * @param {}
 *            response
 * @param {}
 *            options
 * @author zhangzhw
 */
function requestPreProcess(conn, response, options) {
	// if(options.url.indexof('.action')<0) return; //如果返回结果是jsp,则不处理
	// publicLoadMask.enable();
	// publicLoadMask.hide();
	var url = options.url;
	if (url.indexOf('action') > 0) {
		var result = Ext.decode(response.responseText);
		if (!result.success) {
			var msg;
			var title = "异常信息";
			if (result.errors) {
				if (!result.errors.msg) {
					msg = '未知错误！';
				} else {
					title = result.errors.title;
					msg = result.errors.msg;
				}
			} else
				msg = '未知错误！';

			// alert('后台错误：' + msg);
			Ext.MessageBox.alert(title, msg);
			// options.success = false;
			// TODO: 实际部署时去掉下面行的注释
			return false; // 此句可能导致模块开发时断点走不到无法调试错误，实际搭建时才取消注释
		}
	}
	// 未登录处理方法
	if (result && result.notlogin) {
		var win = Ext.getCmp("loginWindow");

		if (win) {
			win.show();
			return false;
		}

		var logo = new Ext.Panel({
					region : "north",
					height : 62,
					html : '<img src="images/ajaxlogo.png"/>',
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
								allowBlank : false,
								blankText : "用户工号不能为空",
								emptyText : "请输入用户工号"
							}, {
								xtype : "textfield",
								fieldLabel : "用户密码",
								anchor : "90%",
								name : "password",
								allowBlank : false,
								blankText : "用户密码不能为空",
								emptyText : "请输入用户密码",
								inputType : "password"
							}]
				});

		win = new Ext.Window({
			title : "用户登录",
			id : "loginWindow",
			width : 300,
			height : 200,
			layout : "border",
			buttonAlign : "center",
			resizable : false,
			plain : false,
			modal : true,
			items : [logo, loginForm],
			buttons : [{
				text : "登录",
				handler : function() {
					var fm = loginForm.getForm();
					fm.submit({
						url : "./sysman/ajaxlogin.action",
						success : function(form, action) {
							win.close();
							// 换用户登录的情况刷新页面
							if (action.result && action.result.pSysUser) {
								if (action.result.pSysUser.name != LOGGEDUSER)
									window.location = "index.jsp";
							} else {
								// 登录成功后继续上一次操作
								Ext.Ajax.request(options);
							}
						}
					});
				}
			}, {
				text : "关闭",
				handler : function() {
					win.close();
				}
			}]
		});

		win.show();

		return false;

	}

}

Ext.Ajax.on('requestcomplete', requestPreProcess);

function beforeRequest() {
	// publicLoadMask.show();

}
Ext.Ajax.on('beforerequest', beforeRequest);
// Ajax 修改完成

/**
 * 分页树的loader 及 toolbar 实现
 * 
 * @author zhangzhw
 */
Ext.ns("Ext.ux.tree");

Ext.ux.tree.PagingTreeLoader = function(config) {
	this.toolbar = config.pagingBar || new Ext.ux.tree.PagingTreeToolbar();
	this.pageSize = config.pageSize || 50;
	Ext.ux.tree.PagingTreeLoader.superclass.constructor.apply(this, arguments);
};

Ext.extend(Ext.ux.tree.PagingTreeLoader, Ext.tree.TreeLoader, {
			doPreload : function(treeNode) {
				var attr = treeNode.attributes;
				var pagingInfo = attr.pagingInfo;
				if (pagingInfo == undefined) {
					pagingInfo = {
						limit : this.toolbar.pageSize || 50,
						start : 0
					};
					treeNode.attributes.pagingInfo = pagingInfo;
					// treeNode.attributes.type = "unkown";
				}
				Ext.apply(this.baseParams, pagingInfo);
				return false;
			},
			processResponse : function(response, node, callback) {
				var json = Ext.decode(response.responseText);

				var pi = json.pageInfo;
				if (pi && pi.result.length > 0) {
					var nodes = pi.result;
					node.beginUpdate();
					// node.attributes.type = nodes.type;
					node.attributes.totalCount = pi.totalCount;
					node.attributes.totalPages = pi.totalPages;
					node.attributes.activepage = pi.prePage + 1;
					node.attributes.count = nodes.length;
					for (var i = 0; i < nodes.length; i++) {
						var n = this.createNode(nodes[i]);
						if (n) {
							n.attributes.others = nodes[i].attributes; // 附加其它属性(自定义属性)
							n.attributes.type = nodes[i].type;
							// n.attributes.apply(node[i].attributes);
							// //附加其它属性(自定义属性)
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
				}
				if (typeof callback == "function") {
					callback(this, node);
				}
			}
		});

var T = Ext.Toolbar;
Ext.ux.tree.PagingTreeToolbar = Ext.extend(Ext.Toolbar, {
	pageSize : 50,
	displayMsg : '{0}~{1}/{2}',
	emptyMsg : '没有数据',
	beforePageText : '',
	afterPageText : '/{0}页',
	firstText : '首页',
	prevText : '上页',
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
							value : 50,
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
			count = count ? count : 0;
			this.cursor = this.cursor ? this.cursor : 1;
			var msg = count == 0 ? this.emptyMsg : String.format(
					this.displayMsg, this.cursor , this.cursor + count-1,
					treeNode.attributes.totalCount
							? treeNode.attributes.totalCount
							: 0);
			this.displayItem.setText(msg);

		}
	},
	onLoad : function(treeNode, r, o) {
		var pagingInfo = treeNode.attributes.pagingInfo;
		var type = treeNode.attributes.type;
		// 对未展开的节点赋初始值（与后台子节点数据不等，必须展开后才取得实际值）
		if (pagingInfo == undefined) {
			pagingInfo = {
				limit : this.pageSize || 50,
				start : 0
			};
			treeNode.attributes.pagingInfo = pagingInfo;
			treeNode.attributes.totalPages = 1;
			treeNode.attributes.totalCount = 0;
			treeNode.attributes.count = 0;
			treeNode.attributes.type = type;
		}

		var cur = treeNode.attributes.pagingInfo.start;
		this.cursor = cur > 0 ? cur : 0;
		var ps = treeNode.attributes.totalPages; // 总页数
		if (ps == undefined)
			ps = 1;
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
		this.doLoad(Math.max(0, this.cursor - this.pageSize - 1));
	},
	moveNext : function() {
		this.doLoad(this.cursor + this.pageSize - 1);
	},
	moveLast : function() {
		var total = this.treeNode.attributes.totalCount;
		var extra = total % this.pageSize;

		this.doLoad(extra ? (total - extra) : total - this.pageSize);

	},
	refresh : function() {
		if (this.treeNode)
			this.doLoad(this.cursor - 1);
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
			var pageSize = field.getValue();
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

Ext.reg("treepaging", Ext.ux.tree.PagingTreeToolbar);

// 以下为公共标准样式覆盖

/**
 * 设置 button 的默认最小宽度
 * 
 * @descirbe zhangzhw
 */
Ext.override(Ext.Button, {
			minWidth : 80
		});

// 对 panel 进行设置
// Ext.override(Ext.Panel, {
// // border : false
// });

/**
 * 修改treeLoader 处理方式
 * 
 * @author zhangzhw
 */
Ext.override(Ext.tree.TreeLoader, {
			processResponse : function(response, node, callback, scope) {
				var json = response.responseText;
				var obj = Ext.decode(json);
				if (!obj)
					return false;
				var o = obj.treeNodeList;
				try {
					// var o = response.responseData || Ext.decode(json);
					node.beginUpdate();
					for (var i = 0, len = o.length; i < len; i++) {
						var n = this.createNode(o[i]);
						if (n) {
							node.appendChild(n);
						}
					}
					node.endUpdate();
					this.runCallback(callback, scope || node, [node]);
				} catch (e) {
					this.handleFailure(response);
				}
			}
		});

/**
 * 修改 BasicForm 中 radioGroup 和 checkboxGroup 的setValues的问题 3.1.1
 * 添加Ext.form.BasicForm的清空方法（代替reset)
 * 
 * @author zhangzhw
 */
Ext.override(Ext.form.BasicForm, {
	findField : function(id) {
		var field = this.items.get(id);
		if (!field) {
			this.items.each(function(f) {
				if (f.isXType('radiogroup') || f.isXType('checkboxgroup')) {
					f.items.each(function(c) {
								if (c.isFormField
										&& (c.dataIndex == id || c.id == id || c
												.getName() == id)) {
									field = c;
									return false;
								}
							});
				}

				if (f.isFormField
						&& (f.dataIndex == id || f.id == id || f.getName() == id)) {
					field = f;
					return false;
				}
			});
		}
		return field || null;
	},
	clear : function() {
		this.items.each(function(f) {
					f.setValue(f.originalValue = '');
				});
		return this;
	}
});

/**
 * @author zhangzhw
 * 解决 radioGroup 取值时返回对象的bug
 * @deprecated
 */
//Ext.override(Ext.form.RadioGroup, {
//            getValue : function() {
//                var out = null;
//                this.eachItem(function(item) {
//                            if (item.checked) {
//                                out = item.inputValue  ;
//                                return false;
//                            }
//                        });
//                return out;
//            }
//        });

/**
 * 解决后台返回数据格式为Y-m-dTH:i:s 时，前台数据格式不一致时无法setValues的问题
 * 
 * @author zhangzhw
 */
Ext.override(Ext.form.DateField, {
	parseDate : function(value) {
		if (!value || Ext.isDate(value)) {
			return value;
		}
		var v = Date.parseDate(value, this.format);
		if (!v)
			v = Date.parseDate(value, 'Y-m-dTH:i:s');
		if (!v && this.altFormats) {
			if (!this.altFormatsArray) {
				this.altFormatsArray = this.altFormats.split("|");
			}
			for (var i = 0, len = this.altFormatsArray.length; i < len && !v; i++) {
				v = Date.parseDate(value, this.altFormatsArray[i]);
			}
		}
		return v;
	}
});

/**
 * 设置 gridpanel 的设置
 * 
 * @author zhangzhw
 */
Ext.override(Ext.grid.GridPanel, {
			loadMask : true,
			stripeRows : true,
			columnLines : true,
			viewConfig : {
				forceFit : true,
				autoFill : true,
				enableRowBody : true
			}
		});

/**
 * 设置columnModel 默认可排序
 * 
 * @author zhangzhw
 */
Ext.override(Ext.grid.ColumnModel, {
			defaultSortable : true
		});

/**
 * 根据要求，设置combox 为只选， 并可通过键入前几个字符自动填充
 * 
 * @author zhangzhw
 */
Ext.override(Ext.form.ComboBox, {
			// editable : false,
			typeAhead : true,
			forceSelection : true,
			// typeAheadDelay : 500,
			onTypeAhead : function() {
				if (this.store.getCount() > 0) {
					var raw = this.getRawValue();
					var len = raw.length;

					for (var i = 0; i < this.store.getCount(); i++) {
						var r = this.store.getAt(i);
						var newValue = r.data[this.displayField];

						if (newValue.substr(0, len) == raw) {
							this.setRawValue(newValue);
							this.selectText(0, newValue.length);

							break;
						}

					}
					// var r = this.store.getAt(0);
					// var newValue = r.data[this.displayField];
					// var len = newValue.length;
					// var selStart = this.getRawValue().length;
					// if (selStart != len) {
					// this.setRawValue(newValue);
					// this.selectText(selStart, newValue.length);
					// }
				}
			}

		});

/**
 * 添加树节点级联选择
 * 
 * @author zhangzhw
 * @param {}
 *            node
 */
var checkSelect = function(node) {
	if (!node.isExpanded())
		node.expand();
	node.eachChild(function(item) {
				// item.getUI().toggleCheck(true); //会触发check事件，造成递归循环
				if (!item.isLeaf())
					checkSelect(item);
				item.attributes.checked = true;
				item.getUI().checkbox.checked = true;

			});

}

var checkDeselect = function(node) {
	if (!node.isExpanded())
		node.expand();
	node.eachChild(function(item) {
				if (!item.isLeaf())
					checkDeselect(item);
				item.attributes.checked = false;
				item.getUI().checkbox.checked = false;
			});

}

Ext.override(Ext.tree.TreePanel, {
			listeners : {
				'checkchange' : function(node, checked) {
					if (checked) {
						checkSelect(node);
					} else {
						checkDeselect(node);
					}
				}
			}
		});
// /////////////////////////////结束树节点级联

// 修改rowNumberer的renderer方法为公有方法
Ext.override(Ext.grid.RowNumberer, {
			width : 32,
			renderer : function(v, p, record, rowIndex) {
				if (this.rowspan) {
					p.cellAttr = 'rowspan="' + this.rowspan + '"';
				}
				return rowIndex + 1;
			}
		});

// 修改默认form的分隔符为""
// Ext.override(Ext.Panel, {
// labelSeparator : ''
// });
// Ext.override(Ext.form.FormPanel, {
// labelSeparator : ''
// });
/**
 * 修改默认分隔符为空， 并对非空字段前加红色*号
 * 
 * @author zhangzhw
 */
Ext.override(Ext.layout.FormLayout, {
			labelSeparator : '',
			getTemplateArgs : function(field) {
				var noLabelSep = !field.fieldLabel || field.hideLabel;
				if (field.allowBlank === false)
					field.fieldLabel += '<font color="red">*</font>';
				return {
					id : field.id,
					label : field.fieldLabel,
					labelStyle : this.getLabelStyle(field.labelStyle),
					elementStyle : this.elementStyle || '',
					labelSeparator : noLabelSep ? '' : (Ext
							.isDefined(field.labelSeparator)
							? field.labelSeparator
							: this.labelSeparator),
					itemCls : (field.itemCls || this.container.itemCls || '')
							+ (field.hideLabel ? ' x-hide-label' : ''),
					clearCls : field.clearCls || 'x-form-clear-left'
				};
			}
		});

// 重写store的loadRecords实现往grid里更新已存在记录------------------------------------------------------
Ext.override(Ext.data.Store, {
			loadRecords : function(o, options, success) {
				if (!o || success === false) {
					if (success !== false) {
						this.fireEvent("load", this, [], options);
					}
					if (options.callback) {
						options.callback.call(options.scope || this, [],
								options, false);
					}
					return;
				}
				var r = o.records, t = o.totalRecords || r.length;
				if (!options || options.add !== true) {
					if (this.pruneModifiedRecords) {
						this.modified = [];
					}
					for (var i = 0, len = r.length; i < len; i++) {
						r[i].join(this);
					}
					if (this.snapshot) {
						this.data = this.snapshot;
						delete this.snapshot;
					}
					this.data.clear();
					this.data.addAll(r);
					this.totalLength = t;
					this.applySort();
					this.fireEvent("datachanged", this);
				} else {
					var add = [];
					for (var i = 0, len = r.length; i < len; i++) {
						var record = r[i];
						if (this.getById(record.id)) {
							this.data.replace(record.id, record);
							this.fireEvent("update", this, record,
									Ext.data.Record.EDIT);
						} else {
							add.push(record);
						}
					}
					this.totalLength = Math.max(t, this.data.length
									+ add.length);
					this.add(add);
				}
				this.fireEvent("load", this, r, options);
				if (options.callback) {
					options.callback.call(options.scope || this, r, options,
							true);
				}
			}
		});

/**
 * 方法 renderModel
 * 
 * @param {}
 *            panel 要渲染到主页面的控件
 * @param {}
 *            modelname 模块名称
 * @description 将模块渲染到主页面中
 */
function renderModel(panel, modelname) {
	var parent = Ext.getCmp(modelname);
	if (!parent) {
		Ext.MessageBox.alert('错误', '未取到相应的模块名');
		return;
	}
	parent.insert(0, panel);
	parent.doLayout();
}

// 以下为公共方法
var colors = ['92bBb1', 'A89166', '80C31C', 'BCDD5A', 'FF7900', 'FBB36B',
		'AEBC21', 'D9DB56', '00477F', '4C88BE', '8DC3E9', 'B96A9A', 'D889B8',
		'FDE8D7', '9CC089', 'D8F3C9'];

function randomColor() {

	return colors[Math.round(Math.random() * 15)];

}

// 任务进度条
var f_taskTime = function(second, overFlag) {
	flag = 0;
	var task_s = (second == "") ? 30 : second;
	Ext.MessageBox.show({
				title : '请等待',
				msg : '任务执行中...',
				progressText : '初始化...',
				width : 300,
				progress : true,
				closable : false,
				buttons : {
					"cancel" : "取消"
				},
				fn : function(e) {
					flag = 0;
					clearInterval(aa);
				}
			});

	var f = function() {
		return function() {
			flag = flag + 1;
			if (flag == task_s + 1) {
				Ext.MessageBox.hide();
				flag = 0;
				clearInterval(aa);
			} else {
				var i = flag;
				Ext.MessageBox.updateProgress(i / (task_s), i + ' 秒');
			}
		};
	};
	var aa = setInterval(f(), 1000);
};
/*******************************************************************************
 * @param consNo
 *            如果consNo的名称不同需填写 默认为 consNo
 * @param tmnlSetNo
 *            如果终端资产好的名次可以修改 默认为 tmnlAssetNo
 ******************************************************************************/
function saveOrUpdateGroupWindowShow(groupTmnlList, consNo, tmnlAssetNo) {
	// ------------------------------------------------------------------------------------------新增群组-新加群组对话框
	// begin
	consNo = consNo || "consNo";
	tmnlAssetNo = tmnlAssetNo || "tmnlAssetNo";
	var groupTypeRadio1 = new Ext.form.RadioGroup({
		hideLabel  : true,
		width : 260,
		items : [new Ext.form.Radio({
						boxLabel : '普通群组',
						name : 'groupTypeRadio1',
						checked : true,
						inputValue : '0'
					}), new Ext.form.Radio({
						boxLabel : '控制群组',
						name : 'groupTypeRadio1',
						inputValue : '1',
						listeners : {
							'check' : function(checkbox, checked) {
								if (checked) {
									panel_mx.layout
											.setActiveItem(1);
								} else {
									panel_mx.layout
											.setActiveItem(0);
								}
							}
						}
					})]
	});
	
	panel_contrl = new Ext.Panel({
				region : 'north',
				height : 30,
				layout : 'form',
				border : false,
				bodyStyle : 'padding:10px 0px 0px 75px',
				items : [groupTypeRadio1]
			});

	// ------------------------------控制类别

	var data_groupType = [['001', '错峰群组'], ['002', '避峰群组'], ['003', '负控限电群组']];
	var ds_groupType = new Ext.data.SimpleStore({
				fields : ['groupTypeCode', 'groupType'],
				data : data_groupType
			});
	// ds_groupType.load();
	var ctrlGroupName1 = new Ext.form.TextField({
				fieldLabel : '群组名称',
				readOnly : true,
				width : 100,
				allowBlank : false,
			    blankText : '请选择控制类别'
			});
	var ctrlGroupName2 = new Ext.form.TextField({
				width : 186,
				emptyText : '请输入群组名称',
				allowBlank : false,
			    blankText : '请输入群组名称'
			});
	var comb_groupType = new Ext.form.ComboBox({
				store : ds_groupType,
				displayField : 'groupType',
				valueField : 'groupTypeCode',
				typeAhead : true,
				mode : 'local',
				forceSelection : true,
				triggerAction : 'all',
				fieldLabel : '群组类型',
				emptyText : '--请选择群组类型--',
				allowBlank : false,
			    blankText : '请选择群组类型',
				selectOnFocus : true,
				editable : false,
				width : 290
			});
	var ds_ctrlScheme = new Ext.data.JsonStore({
				url : 'baseapp/groupSet!queryCtrlSchemeType1.action',
				fields : ['ctrlSchemeNo', 'ctrlSchemeType'],
				root : 'ctrlSchemeTypeIdList',
				idProperty: 'ctrlSchemeNo'
			});
	ds_ctrlScheme.load();
	var comb_ctrlSchemeType1 = new Ext.form.ComboBox({
				store : ds_ctrlScheme,
				displayField : 'ctrlSchemeType',
				valueField : 'ctrlSchemeNo',
				typeAhead : true,
				mode : 'remote',
				forceSelection : true,
				triggerAction : 'all',
				fieldLabel : '控制类别',
				emptyText : '--请选择控制类别--',
				allowBlank : false,
				blankText : '请选择控制类别',
				selectOnFocus : true,
				editable : false,
				width : 290
			});

	comb_ctrlSchemeType1.on('select', function(combox) {
				if (combox != null || combox.getRawValue() != '')
					ctrlGroupName1.setValue(combox.getRawValue());
			});

	var startDate2 = new Ext.form.DateField({
				fieldLabel : '有效日期',
				width : 120,
				format : 'Y-m-d',
				allowBlank : false,
			    blankText : '请选择日期',
				value : new Date()
			});
	var finishDate2 = new Ext.form.DateField({
				fieldLabel : '至',
				width : 120,
				format : 'Y-m-d',
				allowBlank : false,
			    blankText : '请选择日期',
				value : new Date().add(Date.DAY, 30)
			});
	// var isShare2 = new Ext.form.Checkbox({
	// fieldLabel : '是否共享'
	// });
	var panel_contrlGroup = new Ext.Panel({
				layout : 'anchor',
				border : false,
				items : [{
							anchor : '100% 19%',
							border : false,
							layout : 'form',
							labelSeparator : ' ',
							labelAlign : 'right',
							labelWidth : 60,
							bodyStyle : 'padding:10px 0px 0px 10px',
							items : [comb_groupType]
						}, {
							anchor : '100% 19%',
							border : false,
							layout : 'form',
							labelSeparator : ' ',
							labelAlign : 'right',
							labelWidth : 60,
							bodyStyle : 'padding:10px 0px 0px 10px',
							items : [comb_ctrlSchemeType1]
						}, {
							anchor : '100% 19%',
							border : false,
							layout : 'column',
							bodyStyle : 'padding:10px 0px 0px 10px',
							items : [{
										columnWidth : .45,
										layout : 'form',
										border : false,
										labelSeparator : ' ',
										labelAlign : 'right',
										labelWidth : 60,
										items : [ctrlGroupName1]
									}, {
										columnWidth : .55,
										layout : 'form',
										border : false,
										hideLabels : true,
										items : [ctrlGroupName2]
									}]
						}, {
							anchor : '100% 22%',
							border : false,
							layout : 'column',
							bodyStyle : 'padding:10px 0px 0px 10px',
							items : [{
										border : false,
										columnWidth : .53,
										layout : 'form',
										labelSeparator : ' ',
										labelAlign : 'right',
										labelWidth : 60,
										items : [startDate2]
									}, {
										border : false,
										columnWidth : .47,
										layout : 'form',
										labelSeparator : ' ',
										labelAlign : 'right',
										labelWidth : 32,
										items : [finishDate2]
									}]
						}, {
							anchor : '100% 1%',
							border : false,
							layout : 'form',
							buttonAlign : 'center',
							buttons : [{
										text : '确认',
										handler : addGroup

									}, {
										text : '退出',
										handler : function() {
											saveOrUpdateGroupWindow.close();
										}
									}]
						}]
			});

	function addGroup() {
		if (comb_groupType.getValue() == "") {
			Ext.MessageBox.alert("提示", "请选择群组类型！");
			return;
		}
		if (comb_ctrlSchemeType1.getRawValue() == "") {
			Ext.MessageBox.alert("提示", "请选择控制类别！");
			return;
		}
		if (ctrlGroupName2.getValue().trim() == "") {
			Ext.MessageBox.alert("提示", "请填写群组名称！");
			return;
		}
		var startDate = startDate2.getValue();
		var finishDate = finishDate2.getValue();
		if (""==startDate) {
			Ext.MessageBox.alert("提示", "请选择开始日期！");
			return;
		}
		if (""==finishDate) {
			Ext.MessageBox.alert("提示", "请选择结束日期！");
			return;
		}
		if (startDate > finishDate) {
			Ext.MessageBox.alert("提示", "开始日期不能大于结束日期！");
			return;
		}
		// var isShare;
		// if (isShare2.getValue() == true)
		// isShare = '1';
		// else
		// isShare = '0';
		var validDays = parseInt((finishDate.getTime() - startDate.getTime())
				/ (1000 * 24 * 3600));
		saveOrUpdateGroupWindow.hide();
		Ext.getBody().mask("正在处理...");
		Ext.Ajax.request({
					url : 'baseapp/groupSet!addCtrlGroup.action',
					params : {
						groupName : ctrlGroupName1.getValue() + '-'
								+ ctrlGroupName2.getValue().trim(),
						// isShare : isShare,
						startDate : startDate,
						validDays : validDays,
						groupTmnlList : groupTmnlList,
						ctrlSchemeType : comb_ctrlSchemeType1.getValue(),
						groupTypeCode : comb_groupType.getValue()
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						if (null != result.FLAG) {
							if (result.FLAG == 0) {
								Ext.MessageBox.alert("提示", "群组名称已被使用！",
										function(btn) {
											saveOrUpdateGroupWindow.show();
										});
								return;
							} else if (result.FLAG == 1) {
								Ext.MessageBox.alert("提示", "新增群组成功！");
							}
						}
						
						//刷新左边控制群组树
						var controlTree = Ext.getCmp('controlTree');
						controlTree.root.reload();
						
						saveOrUpdateGroupWindow.close();
					},
					callback:function(){
						Ext.getBody().unmask();
					}
				});
	};

	var commonGroupName = new Ext.form.TextField({
				fieldLabel : '群组名称',
				emptyText : '请输入群组名称',
				allowBlank : false,
			    blankText : '请输入群组名称',
				width : 290
			});
	var startDate1 = new Ext.form.DateField({
				fieldLabel : '有效日期',
				width : 120,
				allowBlank : false,
			    blankText : '请选择日期',
				format : 'Y-m-d',
				value : new Date()
			});
	var finishDate1 = new Ext.form.DateField({
				fieldLabel : '至',
				width : 120,
				allowBlank : false,
			    blankText : '请选择日期',
				format : 'Y-m-d',
				value : new Date().add(Date.DAY, 30)
			});
	/*
	 * var isShare1 = new Ext.form.Checkbox({ fieldLabel : '是否共享' });
	 */

	var panel_commonGroup = new Ext.Panel({
		layout : 'form',
		border : false,
		items : [{
					height:  60,
					border : false,
					layout : 'form',
					labelSeparator : ' ',
					labelAlign : 'right',
					labelWidth : 60,
					bodyStyle : 'padding:20px 0px 0px 10px',
					items : [commonGroupName]
				}, {
					height:  50,
					border : false,
					layout : 'column',
					bodyStyle : 'padding:10px 0px 0px 10px',
					items : [{
								border : false,
								columnWidth : .53,
								layout : 'form',
								labelSeparator : ' ',
								labelAlign : 'right',
								labelWidth : 60,
								items : [startDate1]
							}, {
								border : false,
								columnWidth : .47,
								layout : 'form',
								labelSeparator : ' ',
								labelAlign : 'right',
								labelWidth : 32,
								items : [finishDate1]
							}]
				}, {
					height:  1,
					border : false,
					layout : 'form',
					buttonAlign : 'center',
					bodyStyle : 'padding:10px 0px 0px 0px',
					buttons : [{
						text : '确认',
						handler : function() {
							if (commonGroupName.getValue().trim() == "") {
								Ext.MessageBox.alert("提示", "请填写群组名称！");
								return;
							}
							var startDate = startDate1.getValue();
							var finishDate = finishDate1.getValue();
							if (""==startDate) {
								Ext.MessageBox.alert("提示", "请选择开始日期！");
								return;
							}
							if (""==finishDate) {
								Ext.MessageBox.alert("提示", "请选择结束日期！");
								return;
							}
							if (startDate > finishDate) {
								Ext.MessageBox.alert("提示", "开始日期不能大于结束日期！");
								return;
							}
							// var isShare;
							// if (isShare1.getValue() == true)
							// isShare = '1';
							// else
							// isShare = '0';
							var validDays = parseInt((finishDate.getTime() - startDate
									.getTime())
									/ (1000 * 24 * 3600));
							saveOrUpdateGroupWindow.hide();
							Ext.getBody().mask("正在处理...");
							Ext.Ajax.request({
										url : 'baseapp/groupSet!addCommonGroup.action',
										params : {
											groupName : commonGroupName
													.getValue().trim(),
											// isShare : isShare,
											startDate : startDate,
											validDays : validDays,
											groupTmnlList : groupTmnlList
										},
										success : function(response) {
											var result = Ext.decode(response.responseText);
											if (null != result.FLAG) {
												if (0 == result.FLAG) {
													Ext.MessageBox.alert("提示","群组名称已被使用！",
															function(btn) {saveOrUpdateGroupWindow.show();});
													return;
												} else if (1 == result.FLAG) {
													Ext.MessageBox.alert("提示","新增群组成功！");
												}
											}
											//刷新左边普通群组树
											var commTree = Ext.getCmp('backTree');
											commTree.root.reload();
											
											saveOrUpdateGroupWindow.close();
										},
										callback:function(){
											Ext.getBody().unmask();
										}
									});
						}
					}, {
						text : '退出',
						handler : function() {
							saveOrUpdateGroupWindow.close();
						}
					}]
				}]
	});

	var panel_mx = new Ext.Panel({
				region : 'center',
				border : false,
				layout : 'card',
				activeItem : 0,
				items : [panel_commonGroup, panel_contrlGroup]

			});
	var addGroupTab = new Ext.Panel({
				title : '新增群组',
				border : false,
				name : 'addGroupTab',
				layout : 'border',
				items : [panel_contrl, panel_mx]
			});
	// ------------------------------------------------------------------------------------------新增群组-新加群组对话框
	// end

	// ------------------------------------------------------------------------------------------新增群组-加入群组对话框
	// begin
	var groupTypeRadio2 = new Ext.form.RadioGroup({
				hideLabel  : true,
				width : 260,
				items : [new Ext.form.Radio({
									boxLabel : '普通群组',
									name : 'groupTypeRadio2',
									checked : true,
									inputValue : '0'
								}), new Ext.form.Radio({
									boxLabel : '控制群组',
									name : 'groupTypeRadio2',
									inputValue : '1'
								})]
			});
	
	var comb_ctrlSchemeType2 = new Ext.form.ComboBox({
				store : ds_ctrlScheme,
				displayField : 'ctrlSchemeType',
				valueField : 'ctrlSchemeNo',
				typeAhead : true,
				mode : 'remote',
				forceSelection : true,
				triggerAction : 'all',
				fieldLabel : '控制类别',
				emptyText : '--请选择控制类别--',
				selectOnFocus : true,
				width : 290,
				editable : false,
				disabled : true
			});
	
	comb_ctrlSchemeType2.on('change', function(combox) {
				ds_groupName2.removeAll();
				comb_groupName2.setValue("");
				if (comb_ctrlSchemeType2.getRawValue() == "")
					return;
				ds_groupName2.baseParams={
						groupType : groupTypeRadio2.getValue().getRawValue(),
						ctrlSchemeType : comb_ctrlSchemeType2.getValue()
				};
				ds_groupName2.load();
			});
	
	var ds_groupName2 = new Ext.data.JsonStore({ 
			url : 'baseapp/groupSet!queryGroup.action',
			fields : ['groupNo', 'groupName'],
			root : 'tTmnlGroupDtoList',
			idProperty: 'groupNo'
		});
	
	var comb_groupName2 = new Ext.form.ComboBox({
			store : ds_groupName2,
			displayField : 'groupName',
			valueField : 'groupNo',
			typeAhead : true,
			mode : 'local',
			forceSelection : true,
			triggerAction : 'all',
			fieldLabel : '群组名称',
			emptyText : '--请选择群组名称--',
		    //allowBlank:false,
		    //blankText : '请选择群组名称',
			selectOnFocus : true,
			editable : false,
			width : 290
		});
	
	groupTypeRadio2.on('afterrender', function() {
		ds_groupName2.baseParams={
				groupType : groupTypeRadio2.getValue().getRawValue(),
				ctrlSchemeType : comb_ctrlSchemeType2.getValue()
		};
		ds_groupName2.load();	
	});

	groupTypeRadio2.on('change', function() {
				ds_groupName2.removeAll();
				comb_groupName2.setValue("");
				if (groupTypeRadio2.getValue().getRawValue() == "0") {
					comb_ctrlSchemeType2.setValue("");
					comb_ctrlSchemeType2.setDisabled(true);
					ds_groupName2.baseParams={
							groupType : groupTypeRadio2.getValue().getRawValue(),
							ctrlSchemeType : comb_ctrlSchemeType2.getValue()
					};
					ds_groupName2.load();	
				}
				else if (groupTypeRadio2.getValue().getRawValue() == "1") {
					comb_ctrlSchemeType2.setDisabled(false);
				}
			});
	
	var panel1 = new Ext.Panel({
		border : false,
		layout : 'form',
		buttonAlign : 'center',
		items : [{
			        height : 30,
					border : false,
					layout : 'form',
					labelSeparator : '',
					labelAlign : 'right',
					bodyStyle : 'padding:10px 0px 0px 75px',
					items : [groupTypeRadio2]
				}, {
					height: 60,
					border : false,
					layout : 'form',
					labelSeparator : ' ',
					labelAlign : 'right',
					labelWidth : 60,
					bodyStyle : 'padding:20px 0px 0px 10px',
					items : [comb_ctrlSchemeType2]
				}, {
					height:  50,
					border : false,
					layout : 'form',
					labelSeparator : ' ',
					labelAlign : 'right',
					labelWidth : 60,
					bodyStyle : 'padding:10px 0px 0px 10px',
					items : [comb_groupName2]
				}, {
					height:  1,
					border : false,
					layout : 'form',
					bodyStyle : 'padding:10px 0px 0px 0px',
					buttonAlign : 'center',
					buttons : [{
						text : '确认',
						handler : function() {
							if (groupTypeRadio2.getValue().getRawValue() == "1"
									&& (comb_ctrlSchemeType2.getValue() == "" || comb_ctrlSchemeType2 == null)) {
								Ext.MessageBox.alert("提示", "请选择控制类别！");
								return;
							}
							if (comb_groupName2.getValue() == ""
									|| comb_groupName2 == null) {
								Ext.MessageBox.alert("提示", "请选择群组名称！");
								return;
							}
							saveOrUpdateGroupWindow.hide();
							Ext.getBody().mask("正在处理...");
							Ext.Ajax.request({
										url : 'baseapp/groupSet!insertToGroup.action',
										params : {
											groupType : groupTypeRadio2
													.getValue().getRawValue(),
											groupNo : comb_groupName2
													.getValue(),
											groupTmnlList : groupTmnlList
										},
										success : function(response) {
											Ext.MessageBox.alert("提示", "加入群组成功！");
										},
										callback:function(){
											Ext.getBody().unmask();
											saveOrUpdateGroupWindow.close();
											
											//刷新左边普通群组树
											var commTree = Ext.getCmp('backTree');
											commTree.root.reload();
											
											//刷新左边控制群组树
											var controlTree = Ext.getCmp('controlTree');
											controlTree.root.reload();
										}
									});
						}
					}, {
						text : '退出',
						handler : function() {
							saveOrUpdateGroupWindow.close();
						}
					}]
				}]
	});

	var joinGroupTab = new Ext.Panel({
				title : '加入群组',
				border : false,
				name : 'joinGrouptab',
				layout : 'fit',
				items : [panel1]
			});
	// ------------------------------------------------------------------------------------------新增群组-加入群组对话框
	// end
	var saveOrUpdateGroupPanel = new Ext.TabPanel({
				border : false,
				activeTab : 0,
				items : [addGroupTab, joinGroupTab]
			});
	var saveOrUpdateGroupWindow = new Ext.Window({
				title : '加入群组',
				resizable : false,
				modal : true,
				width : 400,
				height : 265,
				layout : 'fit',
				items : [saveOrUpdateGroupPanel]
			});
	saveOrUpdateGroupWindow.show();
};
