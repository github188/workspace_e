

/** **工具*** */
var CreateUtil = {
	// 随机图形中显示的颜色
	randomColor : function() {
		var rand = Math.floor(Math.random() * 0xFFFFFF).toString(16);
		if (rand.length == 6) {
			return rand;
		} else {
			return randomColor();
		}
	},
	// 在一个数组倒数第二个位置插入一个数组或者值,如[1,2,3,4,5] 插入6后为
	// [1,2,3,4,6,5] 5一般为闭合xml元素,如<dataset>
	appendArr : function(arr, inputArr) {
		var len = arr.length;
		if (!(inputArr instanceof Array)) {
			inputArr = [inputArr];
		}
		Ext.each(inputArr, function(a) {
					len++;
					arr.splice(len - 2, 0, a);
				});
		return arr;
	},
	// 在一个数组的末尾插入一个元素或者一个数组
	insertAfter : function(arr, inputArr) {
		return Array.prototype.push.apply(arr, inputArr);
		// var len = arr.length;
		// if (!(inputArr instanceof Array)) {
		// inputArr = [inputArr];
		// }
		// Ext.each(inputArr, function(a) {
		// len++;
		// arr.splice(len - 1, 0, a);
		// });
		// return arr;
	},
	// 根据一个nodeType,一个对象生成一个能通过join连接生成正常xml片段的数组
	/** **通过close来确定是不是闭合的*** */
	createNode : function(nodeType, obj, close) {
		var rs = [];
		rs.push("<" + nodeType);
		Ext.iterate(obj, function(k, v) {
					if (typeof v == "function") {
						rs.push(" " + k + "=\"" + v(nodeType, obj) + "\" ")
					} else {
						rs.push(" " + k + "=\"" + v + "\" ")
					}
				});
		if (close === true) {
			rs.push(" />");
			return rs;
		}
		rs.push(" >");
		rs.push("</" + nodeType + ">");
		return rs;
	},
	// 通过参数来生成一个有所有的参数中的属性和值的新实体
	buildBean : function() {
		var m = {};
		for (var i = 0; i < arguments.length; i++) {
			var o = arguments[i];
			Ext.apply(m, o);
		}
		return m;
	},
	/** **通过某个url来的得到所用到的flash的文件名称** */
	getSwfName : function(url) {
		if (url.indexOf("/") < 0) {
			return url;
		}
		var arr = url.split("/");
		return arr[arr.length - 1];
	},
	/** **对一个FusionCharts对象进行处理，加入必要的配置** */
	buildFc : function(fc, born) {
		if (born.transparent) {
			fc.setTransparent(true);
		}
		var config = born.buildConfig;
		Ext.apply(fc.attributes, this.buildBean(CreateUtil.getConfig(config,
								"attributes"), born.attributes));
		Ext.apply(fc.variables, this.buildBean(CreateUtil.getConfig(config,
								"flashvars"), born.flashvars));
		Ext.apply(fc.params, this.buildBean(CreateUtil.getConfig(config,
								"params"), born.params));
	},
	/** *如果不存在返回一个没有任何属性方法的对象*** */
	getConfig : function(obj, name) {
		if (obj == null) {
			return {};
		}
		return obj[name] || {};
	}
}

/** **flash xml生成器*** */
String.prototype.endsWith = function(str) {
	return (this.indexOf(str) == (this.length - str.length - 1));
};
/** **默认生成策略配置器** */
var DefaultConfig = {
	"ScrollColumn2D.swf" : {
		"chart" : {
			"shownames" : 1,
			"useroundedges" : 1
		},
		"dataset" : {
			"color" : CreateUtil.randomColor,
			"showValues" : 0
		},
		// flash的params
		"params" : {

		},
		// flashvars
		"flashvars" : {

		}
	},
	"MSColumn3D.swf" : this["ScrollColumn2D.swf"],
	"Column2D.swf" : this["ScrollColumn2D.swf"]
}
/**
 * *xml文件生成器 默认的实现通过CreateUtil对数组进行 操作，最后通过join生成字符串，在处理大数据时 能得到较高的性能 ****
 */
var XmlCreator = {
	// 一个用户pie图显示的xml生成器
	/***************************************************************************
	 * 需要配置label value renderer属性， b可以是数组，可以是store，或者是一个对象<br>
	 * renderer 参数为label的值，value的值，和每次循环的一条数据的值
	 **************************************************************************/
	"pieDeault" : function(b) {
		var config = b.buildConfig;
		var chart = CreateUtil.createNode("chart", CreateUtil.getConfig(config,
						"chart"));
		if (b.data instanceof Ext.data.Store) {
			b.data.each(function(r, i) {
						var ss = CreateUtil.getConfig(config, "set");
						ss["label"] = r.get(b.label);
						ss["value"] = r.get(b.value);
						b.renderer
								&& b.renderer.call(b.data, ss["label"],
										ss["value"], r, i) == true
								&& (ss["isSliced"] = 1);
						var set = CreateUtil.createNode("set", ss, true);
						CreateUtil.appendArr(chart, set);
					});
		} else if (b.data instanceof Array) {
			Ext.each(b.data, function(d, i) {
						var ss = CreateUtil.getConfig(config, "set");
						ss["label"] = d[b.label] || d.data[b.label];
						ss["value"] = d[b.value] || d.data[b.label];
						b.renderer
								&& b.renderer.call(b.data, ss["label"],
										ss["value"], d, i) == true
								&& (ss["isSliced"] = 1);
						var set = CreateUtil.createNode("set", ss, true);
						CreateUtil.appendArr(chart, set);
					});
		} else {
			// 这种情况假设b.data就是一个对象
			Ext.iterate(b.data, function(k, v) {
						var ss = CreateUtil.getConfig(config, "set");
						ss["value"] = v;
						ss["label"] = k;
						b.renderer && b.renderer
								&& b.renderer.call(b.data, k, v, b.data)
								&& (ss["isSliced"] = 1);
						var set = CreateUtil.createNode("set", ss, true);
						CreateUtil.appendArr(chart, set);
					});
		}
		return chart;
	},
	// XmlCreator的一个默认的实现，多数实现可以套用这个实现
	"default" : function(b) {
		var config = b.buildConfig;
		var chart = CreateUtil.createNode("chart", CreateUtil.getConfig(config,
						"chart"));
		var categories = CreateUtil.createNode("categories", CreateUtil
						.getConfig(config, "categories"));
		var ds = [];
		var flat = true;
		if (b.data instanceof Ext.data.Store) {
			Ext.iterate(b.nameData, function(k, v) {
						var dataset = CreateUtil.getConfig(config, "dataset");
						dataset["seriesName"] = k;
						var dataset = CreateUtil.createNode("dataset", dataset);
						b.data.each(function(r) {
									if (flat) {
										var cc = CreateUtil.getConfig(config,
												"category");
										cc["label"] = r.get(b.legend);
										var category = CreateUtil.createNode(
												"category", cc, true);
										CreateUtil.appendArr(categories,
												category);
									}
									var ss = CreateUtil
											.getConfig(config, "set");
									ss["value"] = r.get(v);
									var set = CreateUtil.createNode("set", ss,
											true);
									CreateUtil.appendArr(dataset, set);

								});
						flat = false;
						CreateUtil.insertAfter(ds, dataset);
					});
		} else if (b.data instanceof Array) {
			Ext.iterate(b.nameData, function(k, v) {
						var dataset = CreateUtil.getConfig(config, "dataset");
						dataset["seriesName"] = k;
						var dataset = CreateUtil.createNode("dataset", dataset);

						Ext.each(b.data, function(d) {
									if (flat) {
										var cc = CreateUtil.getConfig(config,
												"category");
										cc["label"] = d[b.legend];
										var category = CreateUtil.createNode(
												"category", cc, true);
										CreateUtil.appendArr(categories,
												category);
									}
									var ss = CreateUtil
											.getConfig(config, "set");
									ss["value"] = d[v];
									var set = CreateUtil.createNode("set", ss,
											true);
									CreateUtil.appendArr(dataset, set);
								});
						flat = false;
						CreateUtil.insertAfter(ds, dataset);
					});
		}
		CreateUtil.appendArr(chart, categories);
		CreateUtil.appendArr(chart, ds);
		return chart;
	},
	"MSColumn3D.swf" : function(b) {
		return this["default"](b).join("");
	},
	/** *Flash.Born对象作为这里的参数b*** */
	"ScrollColumn2D.swf" : function(b) {
		return this["default"](b).join("");
	},
	"Column2D.swf" : function() {
		return this["pieDeault"](b).join("")
	}
}

var Flash = {};
Flash.Born = function(config) {
	Ext.apply(this, config);
	this.type = CreateUtil.getSwfName(config.url);
	(typeof config.init == "function") && config.init();
}
Flash.Born.prototype = function() {
	// 私有部分
	return {
		// 公有部分
		/** *会认为是配置项的列表,还可以填充** */
		parseProperty : ["chart", "dataset", "params", "flashvars",
				"categories", "category", "set"],
		// 长宽的默认值
		width : 500,
		height : 200,

		/***********************************************************************
		 * *此方法默认调用默认配置中的方法，可以通过重写他来个性化定制， 也可以改变XmlCreator中的默认实现来对整体进行替换。
		 * 一般保持默认就行了
		 **********************************************************************/
		createSwfXml : function() {
			this.initConfig();
			this.xmlData = XmlCreator[this.type](this);
			return this.xmlData;
		},
		render : function(id, cache) {
			var fc = new FusionCharts(this.url, this.id, this.width,
					this.height);

			if (typeof this.data == "object") {
				if (cache && this.xmlData) {
					fc.setDataXML(this.xmlData);
				} else {
					this.xmlData = this.createSwfXml();
					fc.setDataXML(this.xmlData);
				}
			} else if (typeof this.data == "string") {
				if (this.data.indexOf("url:") != 0) {
					this.xmlData = this.data;
					fc.setDataXML(this.xmlData);
				} else {
					fc.setDataURL(this.data.replace(/^url:/, ""));
				}
			}
			CreateUtil.buildFc(fc, this);
			fc.render(id);
			this.renderTo = id;
		},
		/** **根据以前的配置重新进行渲染** */
		reRender : function(cache) {
			this.render(this.renderTo, cache);
		},
		/** **通过默认的配置和当前对象的配置生成一个配置对象*** */
		initConfig : function() {
			this.buildConfig = {}
			var defaultConfig = DefaultConfig[this.type];
			var me = this;
			Ext.iterate(defaultConfig, function(k, v) {
						me.buildConfig[k] = CreateUtil.buildBean(v, me[k]);
					});
			// 如入默认配置中没有的配置
			Ext.each(this.parseProperty, function(d) {
						!me.buildConfig[d] && me[d]
								&& (me.buildConfig[d] = me[d])
					});
		}
	}
}();
