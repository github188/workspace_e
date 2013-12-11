/**
 * FusionChart EXT封装
 * 
 * @author 张中伟 zhongweizhang@163.com
 */
Ext.namespace("Ext.fc.xmlel", "Ext.fc.data");

/**
 * Ext.fc.xmlel.BaseXml 基础FusionChart元素对象基本类
 * 
 * @param config
 */
Ext.fc.xmlel.BaseXml = function(config) {
	Ext.apply(this, config);
}

Ext.fc.xmlel.BaseXml.prototype.parseToXml = function() {
	var strrtn = '';
	for (var obj in this) {
		if (obj != 'toObject' && !Ext.isEmpty(this[obj])
				&& Ext.isString(this[obj])) {
			strrtn += ' ' + obj + '=\'' + this[obj] + '\' ';
		}
	}
	return strrtn;
}

/**
 * FusionChart 样式 styles
 * 
 * @type Ext.fc.xmlel.ChartStyle
 * @description 仅支持一个样式应用到一个目标,如果要多个目标使用同一样式, 可以新建数个相同的样式
 */
Ext.fc.xmlel.ChartStyle = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * name 样式名
			 * 
			 * @type String
			 */
			name : '',
			/**
			 * type 样式类型
			 * 
			 * @type String 'font' 'shadow' 'glow' 'bevel' 'blur'
			 */
			type : '',
			// font 类型支持的属性
			/**
			 * font 字体名称
			 * 
			 * @type String
			 */
			font : '',
			/**
			 * size 字体大小
			 * 
			 * @type String
			 */
			size : '',
			/**
			 * Color 颜色
			 * 
			 * @type String hex
			 */
			Color : '',
			/**
			 * Bold 粗体
			 * 
			 * @type String '1' or '0'
			 */
			Bold : '',
			/**
			 * Italic 斜体
			 * 
			 * @type String '1' or '0'
			 */
			Italic : '',
			/**
			 * Underline 下划线
			 * 
			 * @type String '1' or '0'
			 */
			Underline : '',
			/**
			 * bgColor 背景色
			 * 
			 * @type String hex
			 */
			bgColor : '',
			/**
			 * borderColor 边框颜色
			 * 
			 * @type String hex
			 */
			borderColor : '',
			/**
			 * isHTML 是否使用html
			 * 
			 * @type String
			 */
			isHTML : '',
			/**
			 * leftMargin: 左边距
			 * 
			 * @type String
			 */
			leftMargin : '',
			/**
			 * 字符间距
			 * 
			 * @type String
			 */
			letterSpacing : '',

			/**
			 * param 应用参数
			 * 
			 * @type String
			 */
			param : '',
			/**
			 * start 开始
			 * 
			 * @type String
			 */
			start : '',
			/**
			 * duration 持续时间
			 * 
			 * @type String
			 */
			duration : '',
			/**
			 * toObject 样式应用目标
			 * 
			 * @type String
			 * @description 应用目标是图表中固定的元素(可以是jsonData里的对象,已自动处理)
			 */
			toObject : ''

		});

Ext.reg('chartstyles', Ext.fc.xmlel.ChartStyle);

/**
 * FusionChart 背景 border and back
 * 
 * @type Ext.fc.xmlel.ChartBack
 */
Ext.fc.xmlel.ChartBack = Ext.extend(Ext.fc.xmlel.BaseXml, {
	/**
	 * showBorder 是否显示边框 '1'或'0'
	 * 
	 * @type String
	 * @default '1' 显示
	 */
	showBorder : '',
	/**
	 * borderColor 边框颜色 hex
	 * 
	 * @type String
	 */
	borderColor : '',
	/**
	 * borderThickness 边框线条宽度
	 * 
	 * @type String 会被fusionChart 自动解析为数字 单位pix
	 * @default '1'
	 */
	borderThickness : '',
	/**
	 * borderAlpha 边框透明度 '0'－'100'
	 * 
	 * @type String 会被fusionChart 自动解析为百分比
	 * @default '60'
	 */
	borderAlpha : '',

	/**
	 * bgColor 背景颜色
	 * 
	 * @type String hex16 @ 可用逗号分隔支持多个（用于渐变）
	 * @default 'ffffff'
	 */
	bgColor : '',
	/**
	 * bgAlpha 背景透明度
	 * 
	 * @type String '0'-'100' @ 可用逗号分隔支持多个（用于渐变）
	 * @default '60'
	 */
	bgAlpha : '',
	/**
	 * bgRatio 每种颜色的比率
	 * 
	 * @type String '0'-'100' @ 可用逗号分隔支持多个（用于渐变）
	 * @default '100'
	 */
	bgRatio : '',
	/**
	 * bgAngle 背景渐变角度
	 * 
	 * @type String '0'-'360'
	 * @default '180'
	 */
	bgAngle : '',
	/**
	 * bgSWF 背景图片路径
	 * 
	 * @type String
	 * @default ''
	 */
	bgSWF : '',
	/**
	 * bgSWFAlpha 背景图片透明度
	 * 
	 * @type String
	 * @default ''
	 */
	bgSWFAlpha : '',
	/**
	 * toObject styles 应用对象
	 * 
	 * @type String
	 */
	toObject : 'Background'
		/**
		 * constructor 构造方法
		 */
		// constructor : function(config) {
		//
		// Ext.fc.xmlel.ChartBack.superclass.constructor.call(this);
		// Ext.apply(this, config);
		// },
		// parseToXml : function() {
		// var strXml='';
		// strXml+= parseXml(showBorder);
		//                
		// return strXml;
		// }
	});

Ext.reg("chartback", Ext.fc.xmlel.ChartBack);

/**
 * Ext.fc.xmlel.ChartCanvas FusionChart 画布属性
 * 
 * @type Ext.fc.xmlel.ChartCanvas
 */
Ext.fc.xmlel.ChartCanvas = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * canvasBorderColor 画布边框颜色
			 * 
			 * @type String '0'-'100'
			 * @default 'cccccc'
			 */
			canvasBorderColor : '',
			/**
			 * canvasBorderThickness 画布边框宽度
			 * 
			 * @type String 单位 pix
			 * @default '1'
			 */
			canvasBorderThickness : '',
			/**
			 * canvasBorderAlpha
			 * 
			 * @type String '0'-'100'
			 * @default '60'
			 */
			canvasBorderAlpha : '',
			/**
			 * canvasbgColor 画布背景颜色
			 * 
			 * @type String hex 支持逗号分隔
			 * @default 'ffffff'
			 */
			canvasBgColor : '',
			/**
			 * canvasbgAlpha 画布背景透明度
			 * 
			 * @type String '0'-'100' 支持逗号分隔 如 '40,60'
			 * @default '60'
			 */
			canvasBgAlpha : '',
			/**
			 * canvasBgRatio 画布背景比率 （需定义多种背景颜色）
			 * 
			 * @type String 支持逗号分隔
			 * @default '100'
			 */
			canvasBgRatio : '',
			/**
			 * canvasBgAngle 画布背景渐变角度
			 * 
			 * @type String '0'-'360'
			 * @default '180'
			 */
			canvasBgAngle : '',

			/**
			 * showCanvasBg 是否显示画布背景
			 * 
			 * @type String '1' or '0'
			 * @default '1'
			 */
			showCanvasBg : '',
			/**
			 * showCanvasBase 是否显示画布base(底？)
			 * 
			 * @type String '1' or '0'
			 * @default '1'
			 */
			showCanvasBase : '',
			/**
			 * canvasBaseDepth 画布base深度
			 * 
			 * @type String
			 * @default '10'
			 */
			canvasBaseDepth : '',
			/**
			 * canvasBgDepth 画布背景深度
			 * 
			 * @type String
			 * @default '3'
			 */
			canvasBgDepth : '',
			/**
			 * toObject 样式应用对象
			 * 
			 * @type String
			 */
			toObject : 'Canvas'

		});

Ext.reg('chartcanvas', Ext.fc.xmlel.ChartCanvas);

/**
 * Ext.fc.xmlel.ChartTitleAxis FusionChart 标题及坐标轴
 * 
 * @type Ext.fc.xmlel.ChartTitleAxis
 */
Ext.fc.xmlel.ChartTitleAxis = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * caption FusionChart 标题
			 * 
			 * @type String
			 */
			caption : '',
			/**
			 * subCaption FusionChart 副标题
			 * 
			 * @type String
			 */
			subCaption : '',
			/**
			 * xAxisName X 坐标轴名称
			 * 
			 * @type String
			 */
			xAxisName : '',
			/**
			 * yAxisname y 坐标轴名称
			 * 
			 * @type String
			 */
			yAxisname : '',
			/**
			 * outCnvbaseFont 标题及坐标所有字体名
			 * 
			 * @type String
			 */
			outCnvbaseFont : '',
			/**
			 * outCnvbaseFontSize 标题及坐标字体大小
			 * 
			 * @type String
			 * @default '10'
			 */
			outCnvbaseFontSize : '',
			/**
			 * outCnvbaseFontColor 标题及坐标字体颜色
			 * 
			 * @type String
			 * @default '000000' 黑
			 */
			outCnvbaseFontColor : '',
			/**
			 * toObject 样式应用对象
			 * 
			 * @type String TODO : toObject SubCation XAxisName YAxisName
			 */
			toObject : 'Caption'
		});

Ext.reg('titleaxis', Ext.fc.xmlel.ChartTitleAxis);

/**
 * Ext.fc.xmlel.DataPlot 数据块属性
 * 
 * @type Ext.fc.xmlel.DataPlot
 */
Ext.fc.xmlel.DataPlot = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * plotGradientColor 全局数据块渐变色
			 * 
			 * @type String hex color
			 * @default ''
			 */
			plotGradientColor : '',
			/**
			 * plotFillAngle 数据块填充角度
			 * 
			 * @type String '0'-'360'
			 */
			plotFillAngle : '',
			/**
			 * plotFillAlpha 数据填充透明度
			 * 
			 * @type String '0'-'100'
			 * @default ''
			 */
			plotFillAlpha : '',
			/**
			 * plotBorderDashed 数据块虚线边框
			 * 
			 * @type String '0' 或 '1'
			 * @default ''
			 */
			plotBorderDashed : '',
			/**
			 * plotBorderDashLen 数据块虚线边框实线长度
			 * 
			 * @type String pix
			 */
			plotBorderDashLen : '',
			/**
			 * plotBorderDashGap 数据块虚线边框缺口长度
			 * 
			 * @type String
			 */
			plotBorderDashGap : '',
			/**
			 * useRoundEdges 使用圆角
			 * 
			 * @type String '0' or '1'
			 * @default '1'
			 */
			useRoundEdges : '',
			/**
			 * toObject 样式应用对象
			 * 
			 * @type String
			 */
			toObject : 'DATAPLOT'
		});

Ext.reg('dataplot', Ext.fc.xmlel.DataPlot);

/**
 * Ext.fc.xmlel.DataLabel 数据标签
 * 
 * @type Ext.fc.xmlel.DataLabel
 */
Ext.fc.xmlel.DataLabel = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * labelDisplay 标签显示方式
			 * 
			 * @type String 'WRAP' 'ROTATE' 'Stagger'
			 * @default FusionChart Default 'WRAP'
			 */
			labelDisplay : '',
			/**
			 * slantLabels 标签倾斜
			 * 
			 * @type String
			 * @default 当lableDisplay 为'ROTATE'时设置为1则倾斜45度
			 */
			slantLabels : '',
			/**
			 * showLabels 是否显示数据标签
			 * 
			 * @type String '0' or '1'
			 * @default fusionChart default '1' ,设为0时可对数据单独设置是否显示使用showName='1'
			 */
			showLabels : '',
			/**
			 * toObject 样式应用对象
			 * 
			 * @type String
			 */
			toObject : 'DataLabels'
		});

Ext.reg("datalabel", Ext.fc.xmlel.DataLabel);

/**
 * Ext.fc.xmlel.DataValue 数值显示设置
 * 
 * @type Ext.fc.xmlel.DataValue
 */
Ext.fc.xmlel.DataValue = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * showValues 数值显示方式
			 * 
			 * @type String '0' or '1'
			 * @default fusionChart default '1'
			 * @description 如果要单独显示某几个值 设置为'0' 另在数据中进行设计
			 */
			showValues : '',
			/**
			 * rotateValues 数值旋转方式
			 * 
			 * @type String '0' or '1'
			 * @default fushionchart default '0'
			 */
			rotateValues : '',

			/**
			 * placeValuesInside 数值是否内置
			 * 
			 * @type String '0' or '1'
			 * @default fusionchart defaut '0'
			 */
			placeValuesInside : '',
			/**
			 * toObject 样式应用对象
			 * 
			 * @type String
			 */
			toObject : 'DataValues'
		});

Ext.reg('datavalue', Ext.fc.xmlel.DataValue);

/**
 * Ext.fc.xmlel.FontProperties 基本字体样式
 * 
 * @type Ext.fc.xmlel.FontProperties
 * @description 控制所有字体 如果outCnvBaseFont 设置了则仅对Canvas内部有效
 */
Ext.fc.xmlel.FontProperty = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * baseFont 字体名称
			 * 
			 * @type String
			 * @default 无
			 */
			baseFont : '',
			/**
			 * baseFontSize 字体大小
			 * 
			 * @type String '0'-'72'
			 * @default 无
			 */
			baseFontSize : '',
			/**
			 * baseFontColor 字体颜色
			 * 
			 * @type String hex
			 * @default 无
			 */
			baseFontColor : '',
			/**
			 * toObject 样式应用对象
			 * 
			 * @type String
			 */
			toObject : 'FontProperties'
		});

Ext.reg('fontproperty', Ext.fc.xmlel.FontProperty);

/**
 * Ext.fc.xmlel.ChartLimit y轴最大最小值
 * 
 * @type Ext.fc.xmlel.ChartLimit
 * @default 无 设置Y轴最大最小值
 */
Ext.fc.xmlel.ChartLimit = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * yAxisMinValue y轴最小值
			 * 
			 * @type String
			 * @default 无
			 */
			yAxisMinValue : '',
			/**
			 * yAxisMaxValue y轴最大值
			 * 
			 * @type String
			 * @default 无
			 */
			yAxisMaxValue : '',
			/**
			 * setAdaptiveYMin y轴最大最小值自动调整
			 * 
			 * @type String
			 * @default fusionchart default 1
			 */
			setAdaptiveYMin : '',
			/**
			 * toObject 样式应用对象
			 * 
			 * @type String
			 */
			toObject : 'ChartLimit'
		});

Ext.reg('charlimit', Ext.fc.xmlel.ChartLimit);

/**
 * Ext.fc.xmlel.HorDivLine 分隔Y轴的横线
 * 
 * @type Ext.fc.xmlel.HorDivLine
 */
Ext.fc.xmlel.HDivLine = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * adjustDiv 自动调整分隔
			 * 
			 * @type String '0' or '1'
			 * @default fusionchart '1'
			 */
			adjustDiv : '',
			/**
			 * numDivLines 分隔间隔数值
			 * 
			 * @type String
			 * @default 无
			 */
			numDivLines : '',
			/**
			 * showYAxisValues 是否显示Y坐标数值
			 * 
			 * @type String '0' or '1'
			 * @default fusionchart '1'
			 */
			showYAxisValues : '',
			/**
			 * yAxisValuesStep Y坐标显示间隔（每隔几个值显示一次)
			 * 
			 * @type String
			 * @default fusionchart '1'
			 */
			yAxisValuesStep : '',
			/**
			 * divLineColor 分隔线颜色
			 * 
			 * @type String hex
			 * @default 无
			 */
			divLineColor : '',
			/**
			 * divLineThickness 分隔线宽度
			 * 
			 * @type String '1'-'5'
			 * @default 无
			 */
			divLineThickness : '',
			/**
			 * divLineAlpha 分隔线透明度
			 * 
			 * @type String '0'-'100'
			 */
			divLineAlpha : '',
			/**
			 * divLineIsDashed 是否使用虚线分隔
			 * 
			 * @type String '0' or '1'
			 */
			divLineIsDashed : '',
			/**
			 * divLineDashLen 使用虚线的实线点长度
			 * 
			 * @type String
			 */
			divLineDashLen : '',
			/**
			 * divLineDashGap 使用虚线的缺口长度
			 * 
			 * @type String
			 */
			divLineDashGap : '',
			/**
			 * showAlternateHGridColor 是否交替横格颜色
			 * 
			 * @type String
			 */
			showAlternateHGridColor : '',
			/**
			 * toObject 样式应用对象
			 * 
			 * @type String TODO toObject HGRID YAXISVALUES DIVELINES
			 */
			toObject : 'YAXISVALUES'
		});

Ext.reg('hdivline', Ext.fc.xmlel.HDivLine);

/**
 * Ext.fc.xmlel.VDivLine 竖向分隔线
 * 
 * @type Ext.fc.xmlel.VDivLine 图标的竖向分隔线
 */
Ext.fc.xmlel.VDivLine = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * numVDivLines 竖向分隔线间隔值
			 * 
			 * @type String
			 */
			numVDivLines : '',
			/**
			 * vDivLineColor 竖向分隔线颜色
			 * 
			 * @type String hex
			 */
			vDivLineColor : '',
			/**
			 * vDivLineThickness 竖向分隔线宽度
			 * 
			 * @type String pixes '1'-'5'
			 */
			vDivLineThickness : '',
			/**
			 * vDivLineAlpha 竖向分隔线透明度
			 * 
			 * @type String
			 */
			vDivLineAlpha : '',
			/**
			 * vDivLineIsDashed 竖向分隔线使用虚线
			 * 
			 * @type String '0' or '1'
			 */
			vDivLineIsDashed : '',
			/**
			 * vDivLineDashLen 使用虚线时实线段长度
			 * 
			 * @type String
			 */
			vDivLineDashLen : '',
			/**
			 * vDivLineDashGap 使用虚线时缺口段长度
			 * 
			 * @type String
			 */
			vDivLineDashGap : '',
			/**
			 * showAlternateVGridColor 是否交替竖格颜色
			 * 
			 * @type String
			 */
			showAlternateVGridColor : '',
			/**
			 * toObject 样式应用对象
			 * 
			 * @type String TODO toObject? VGRID XAXISVALUES VDIVELINES
			 */
			toObject : 'VGRID'
		});

Ext.reg('vdivline', Ext.fc.xmlel.VDivLine);

/**
 * Ext.fc.xmlel.ZeroPlane 水平线
 * 
 * @type Ext.fc.xmlel.ZeroPlane
 */
Ext.fc.xmlel.ZeroPlane = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * showZeroPlane 是否显示水平线
			 * 
			 * @type String
			 */
			showZeroPlane : '',
			/**
			 * zeroPlaneColor 水平线颜色
			 * 
			 * @type String hex
			 */
			zeroPlaneColor : '',
			/**
			 * zeroPlaneThickness 水平线宽度
			 * 
			 * @type String '0' -'5'
			 */
			zeroPlaneThickness : '',
			/**
			 * zeroPlaneAlpha 水平线透明度
			 * 
			 * @type String
			 */
			zeroPlaneAlpha : '',
			/**
			 * zeroPlaneShowBorder 是否显示border
			 * 
			 * @type String
			 */
			zeroPlaneShowBorder : '',
			/**
			 * zeroPlaneBorderColor 水平线边界颜色
			 * 
			 * @type String
			 */
			zeroPlaneBorderColor : '',
			/**
			 * toObject 样式应用对象
			 * 
			 * @type String
			 */
			toObject : 'DIVLINES'
		});

Ext.reg('zeroplane', Ext.fc.xmlel.ZeroPlane);

/**
 * Ext.fc.xmlel.TrendLine 参考线（走势线）
 * 
 * @type Ext.fc.xmlel.TrendLine
 */
Ext.fc.xmlel.TrendLine = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * startValue 参考线显示位置的值
			 * 
			 * @type String
			 */
			startValue : '',
			/**
			 * color 参考线颜色
			 * 
			 * @type String
			 */
			color : '',
			/**
			 * thickness 参考线宽度
			 * 
			 * @type String
			 */
			thickness : '',
			/**
			 * alpha 参考线透明度
			 * 
			 * @type String
			 */
			alpha : '',
			/**
			 * endValue 如果要显示斜线，第二个值
			 * 
			 * @type String
			 */
			endValue : '',
			/**
			 * displayvalue 参考线显示值
			 * 
			 * @type String
			 */
			displayvalue : '',
			/**
			 * valueOnRight 参考线显示位置 是否右侧显示
			 * 
			 * @type String '0' or '1'
			 */
			valueOnRight : '',
			/**
			 * dashed 是否显示虚线
			 * 
			 * @type String '0' or '1'
			 */
			dashed : '',
			/**
			 * dashLen 虚线中实线长度
			 * 
			 * @type String
			 */
			dashLen : '',
			/**
			 * dashGap 虚线中缺口长度
			 * 
			 * @type String
			 */
			dashGap : ''
		});

Ext.reg('trendline', Ext.fc.xmlel.TrendLine);

/**
 * Ext.fc.xmlel.ChartAnchor 锚点设置
 * 
 * @type Ext.fc.xmlel.ChartAnchor
 */
Ext.fc.xmlel.ChartAnchor = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * drawAnchors 是否显示锚点 （显示锚点才能显示tooltip)
			 * 
			 * @type String
			 * @default fusionchart '1'
			 * @description 如果要只显示tooltip,可以设置透明度为0
			 */
			drawAnchors : '',
			/**
			 * anchorSides 多边形边数
			 * 
			 * @type String 3-16 3三角形 4正方形
			 */
			anchorSides : '',
			/**
			 * anchorRadius 锚点半径
			 * 
			 * @type String pix
			 */
			anchorRadius : '',
			/**
			 * anchorBorderColor 锚点边界颜色
			 * 
			 * @type String hex
			 */
			anchorBorderColor : '',
			/**
			 * anchorBorderThickness 锚点边界宽度
			 * 
			 * @type String pix
			 */
			anchorBorderThickness : '',
			/**
			 * anchorBgColor 锚点背景颜色
			 * 
			 * @type String hex
			 */
			anchorBgColor : '',
			/**
			 * anchorAlpha 锚点透明度
			 * 
			 * @type String '0'='100'
			 */
			anchorAlpha : '',
			/**
			 * anchorBgAlpha 锚点背景透明度
			 * 
			 * @type String '0'-'100'
			 */
			anchorBgAlpha : '',
			/**
			 * toObject 样式应用对象
			 * 
			 * @type String
			 */
			toObject : 'ANCHORS'
		});

Ext.reg('chartanchor', Ext.fc.xmlel.ChartAnchor);

/**
 * Ext.fc.xmlel.ChartTooltip 提示
 * 
 * @type Ext.fc.xmlel.ChartTooltip
 * @description 提示内容特别定义需要在数据项中 如果要换行 使用 &lt;BR&gt;
 */
Ext.fc.xmlel.ChartTooltip = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * showToolTip 显示提示
			 * 
			 * @type String '0' or '1'
			 */
			showToolTip : '',
			/**
			 * toolTipBorderColor 提示边界颜色
			 * 
			 * @type String hex
			 */
			toolTipBorderColor : '',
			/**
			 * toolTipBgColor 提示背景颜色
			 * 
			 * @type String
			 */
			toolTipBgColor : '',
			/**
			 * toObject 样式应用对象
			 * 
			 * @type String
			 */
			toObject : 'ToolTip'
		});

Ext.reg('charttooltip', Ext.fc.xmlel.ChartTooltip);

/**
 * Ext.fc.xmlel.MarginPadding Chart空白和缩进
 * 
 * @type Ext.fc.xmlel.MarginPadding
 */
Ext.fc.xmlel.MarginPadding = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * chartLeftMargin 左空白
			 * 
			 * @type String pix
			 */
			chartLeftMargin : '',
			/**
			 * chartRightMargin 右空白
			 * 
			 * @type String
			 */
			chartRightMargin : '',
			/**
			 * chartTopMargin 上空白
			 * 
			 * @type String
			 */
			chartTopMargin : '',
			/**
			 * chartBottomMargin 下空白
			 * 
			 * @type String
			 */
			chartBottomMargin : '',
			/**
			 * captionPadding 标题缩进 （标题与图表间距离)
			 * 
			 * @type String
			 */
			captionPadding : '',
			/**
			 * xAxisNamePadding X轴缩进 （X轴值与X轴名称间距）
			 * 
			 * @type String
			 */
			xAxisNamePadding : '',
			/**
			 * yAxisNamePadding Y轴缩进 （Y轴值与Y轴名间的距离）
			 * 
			 * @type String
			 */
			yAxisNamePadding : '',
			/**
			 * yAxisValuesPadding Y轴值与Y轴间的距离
			 * 
			 * @type String
			 */
			yAxisValuesPadding : '',
			/**
			 * labelPadding X轴与X轴值（标签） 间距
			 * 
			 * @type String
			 */
			labelPadding : '',
			/**
			 * valuePadding 数据值与数据块之间的距离
			 * 
			 * @type String
			 */
			valuePadding : '',
			/**
			 * canvasPadding 画布内缩进
			 * 
			 * @type String
			 */
			canvasPadding : '',
			/**
			 * toObject 样式应用对象
			 * 
			 * @type String
			 */
			toObject : ''
		});

Ext.reg('marginpadding', Ext.fc.xmlel.MarginPadding);

/**
 * Ext.fc.xmlel.VLine 独立的竖线 可多条
 * 
 * @type Ext.fc.xmlel.VLine
 * @description VLine 使用 position 确定位置
 */
Ext.fc.xmlel.VLine = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * color 颜色
			 * 
			 * @type String
			 */
			color : '',
			/**
			 * thickness 宽度
			 * 
			 * @type String
			 */
			thickness : '',
			/**
			 * alpha 透明度
			 * 
			 * @type String
			 */
			alpha : '',
			/**
			 * dashed 是否虚线
			 * 
			 * @type String
			 */
			dashed : '',
			/**
			 * dashLen 虚线内实线长度
			 * 
			 * @type String
			 */
			dashLen : '',
			/**
			 * dashGap 虚线内缺口长度
			 * 
			 * @type String
			 */
			dashGap : '',
			/**
			 * position 竖线位置 在第几条记录后显示
			 * 
			 * @type String 超出记录数-1时无效
			 */
			position : ''
		});

Ext.reg('chartvline', Ext.fc.xmlel.VLine);

/**
 * Ext.fc.xmlel.SmartLine 饼图的智能标签和延伸线? Smart Label & Line
 * 
 * @type Ext.fc.xmlel.SmartLine
 */
Ext.fc.xmlel.SmartLabel = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * enableSmartLabels 是否使用智能标签
			 * 
			 * @type String '0' or '1'
			 */
			enableSmartLabels : '',
			/**
			 * smartLineColor 智能标签颜色
			 * 
			 * @type String hex
			 */
			smartLineColor : '',
			/**
			 * smartLineThickness 延伸线宽度
			 * 
			 * @type String pix
			 */
			smartLineThickness : '',
			/**
			 * smartLineAlpha 延伸线透明度
			 * 
			 * @type String '0' -'100'
			 */
			smartLineAlpha : '',
			/**
			 * isSmartLineSlanted 是否使用斜线
			 * 
			 * @type String '0' or '1'
			 */
			isSmartLineSlanted : '',

			/**
			 * labelDistance 标签距离图的距离
			 * 
			 * @type String pix
			 */
			labelDistance : '',
			/**
			 * smartLabelClearance 智能清空标签与图的距离?
			 * 
			 * @type String
			 */
			smartLabelClearance : '',
			/**
			 * skipOverlapLabels 忽略重叠标签
			 * 
			 * @type String
			 */
			skipOverlapLabels : '',
			/**
			 * showPercentValues 显示百分比
			 * 
			 * @type String '0' or '1'
			 * @default fusionchart default '0'
			 */
			showPercentValues : '',
			/**
			 * toObject 样式应用对象
			 * 
			 * @type String
			 */
			toObject : 'SmartLabel'
		});

Ext.reg('smartlabel', Ext.fc.xmlel.SmartLabel);

/**
 * Ext.fc.xmlel.ChartLegend 图例
 * 
 * @type Ext.fc.xmlel.ChartLegend
 */
Ext.fc.xmlel.ChartLegend = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * showLegend 是否显示图例
			 * 
			 * @type String '0' or '1'
			 * @default fusionchart default '1'
			 */
			showLegend : '',
			/**
			 * legendPosition 图例位置
			 * 
			 * @type String 'RIGHT' OR 'BOTTOM'
			 */
			legendPosition : '',
			/**
			 * legendBgColor 图例背景颜色
			 * 
			 * @type String hex
			 */
			legendBgColor : '',
			/**
			 * legendBgAlpha 背景透明度
			 * 
			 * @type String '0'-'100'
			 */
			legendBgAlpha : '',
			/**
			 * legendBorderColor 图例边框颜色
			 * 
			 * @type String hex
			 */
			legendBorderColor : '',
			/**
			 * legendBorderThickness 图例边框宽度
			 * 
			 * @type String pix
			 */
			legendBorderThickness : '',
			/**
			 * legendBorderAlpha 图例边框透明度
			 * 
			 * @type String '0' - '100'
			 */
			legendBorderAlpha : '',
			/**
			 * legendShadow 图例阴影
			 * 
			 * @type String '0' or '1'
			 */
			legendShadow : '',
			/**
			 * legendScrollBgColor 图例过多时滚动条背景色
			 * 
			 * @type String hex
			 */
			legendScrollBgColor : '',
			/**
			 * legendScrollBarColor 滚动条颜色
			 * 
			 * @type String
			 */
			legendScrollBarColor : '',
			/**
			 * legendScrollBtnColor 滚动条按钮颜色
			 * 
			 * @type String
			 */
			legendScrollBtnColor : '',
			/**
			 * toObject 样式应用对象
			 * 
			 * @type String
			 */
			toObject : 'Legend'
		});

Ext.reg('chartlegend', Ext.fc.xmlel.ChartLegend);

/**
 * Ext.fc.xmlel.NumberFormattiong 数字格式
 * 
 * @type Ext.fc.xmlel.NumberFormattiong
 */
Ext.fc.xmlel.NumberFormattiong = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * decimals 最多保留的小数位 ,小于这个数字的不变
			 * 
			 * @type String
			 */
			decimals : '',
			/**
			 * forceDecimals 强制补小数位
			 * 
			 * @type String
			 */
			forceDecimals : '',
			/**
			 * formatNumberScale 设置整数截取的位数,不设置则自动截取
			 * 
			 * @type String
			 * @description 不设置的话,fusionChart 按 k m b 自动截取
			 */
			formatNumberScale : '',
			/**
			 * formatNumber 是否显示数字中的逗号分隔
			 * 
			 * @type String '0' or '1'
			 * @default fusionchart default '1'
			 */
			formatNumber : '',
			/**
			 * decimalSeparator 自定义数字分隔符
			 * 
			 * @type String
			 */
			decimalSeparator : '',
			/**
			 * thousandSeparator 千分隔符
			 * 
			 * @type String
			 */
			thousandSeparator : '',
			/**
			 * numberPrefix 数字前缀
			 * 
			 * @type String
			 */
			numberPrefix : '',
			/**
			 * numberSuffix 数字后缀
			 * 
			 * @type String
			 */
			numberSuffix : '',
			/**
			 * yAxisValueDecimals y轴坐标值的小数位数
			 * 
			 * @type String
			 */
			yAxisValueDecimals : '',
			/**
			 * toObject 样式应用对象
			 * 
			 * @type String
			 */
			toObject : 'NumberFormatting'
		});

Ext.reg('', Ext.fc.xmlel.NumberFormattiong);

/**
 * Ext.fc.xmlel.NumberScaling 数字自动缩放
 * 
 * @type Ext.fc.xmlel.NumberScaling
 */
Ext.fc.xmlel.NumberScaling = Ext.extend(Ext.fc.xmlel.BaseXml, {
			/**
			 * numberScaleValue 数字缩放进制值
			 * 
			 * @type String
			 */
			numberScaleValue : '',
			/**
			 * numberScaleUnit 缩放单位
			 * 
			 * @type String
			 */
			numberScaleUnit : '',
			/**
			 * defaultNumberScale 默认的数字单位
			 * 
			 * @type String
			 */
			defaultNumberScale : '',
			/**
			 * toObject 样式应用对象
			 * 
			 * @type String
			 */
			toObject : 'NumberScaling'
		});

Ext.reg('numberscaling', Ext.fc.xmlel.NumberScaling);

/**
 * 默认的数字缩放格式
 * 
 * @class Ext.fc.xmlel.DefaultNumberScaling
 * @extends Ext.fc.xmlel.NumberScaling
 */
Ext.fc.xmlel.DefaultNumberScaling = Ext.extend(Ext.fc.xmlel.NumberScaling, {
			numberScaleValue : '1000,1000,1000',
			numberScaleUnit : 'K,M,B'

		});
/**
 * 默认的时间缩放格式
 * 
 * @class Ext.fc.xmlel.TimeNumberScaling
 * @extends Ext.fc.xmlel.NumberScaling
 */
Ext.fc.xmlel.TimeNumberScaling = Ext.extend(Ext.fc.xmlel.NumberScaling, {
			defaultNumberScale : 's',
			numberScaleValue : '60,60,24,7',
			numberScaleUnit : 'min,hr,day,wk'
		});
/**
 * 基本Fusion Chart Json数据
 * 
 * @class Ext.fc.data.ChartJsonData
 * @extends Ext.fc.xmlel.BaseXml
 */
Ext.fc.data.ChartJsonData = Ext.extend(Ext.util.Observable, {

			/**
			 * chartBack 背景相关设置
			 * 
			 * @type Ext.fc.xmlel.ChartBack
			 */
			chartBack : null,
			/**
			 * chartCanvas 画布相关设置
			 * 
			 * @type Ext.fc.xmlel.ChartCanvas
			 */
			chartCanvas : null,
			/**
			 * chartTitleAxis 标题及坐标轴相关属性设置
			 * 
			 * @type Ext.fc.xmlel.ChartTitleAxis
			 */
			chartTitleAxis : null,
			/**
			 * dataPlot 数据块相关设置
			 * 
			 * @type Ext.fc.xmlel.DataPlot
			 */
			chartDataPlot : null,
			/**
			 * dataLabel 数据标签相关设置
			 * 
			 * @type Ext.fc.xmlel.DataLabel
			 */
			chartDataLabel : null,
			/**
			 * dataValue 数据值设置
			 * 
			 * @type Ext.fc.xmlel.DataValue
			 */
			chartDataValue : null,
			/**
			 * fontProperty 字体属性
			 * 
			 * @type Ext.fc.xmlel.FontProperty
			 */
			chartFontProperty : null,
			/**
			 * chartLimit 图表极值设置
			 * 
			 * @type Ext.fc.xmlel.ChartLimit
			 */
			chartLimit : null,
			/**
			 * hDivLine 横向分隔线（表格线）
			 * 
			 * @type Ext.fc.xmlel.HDivLine
			 */
			chartHDivLine : null,
			/**
			 * vDivLine 竖向分隔线
			 * 
			 * @type Ext.fc.xmlel.VDivLine
			 */
			chartVDivLine : null,
			/**
			 * zeroPlane 水平线
			 * 
			 * @type Ext.fc.xmlel.ZeroPlane
			 */
			chartZeroPlane : null,
			/**
			 * trendLine 走势线 参考线
			 * 
			 * @type Ext.fc.xmlel.TrendLine []
			 */
			chartTrendLine : null,
			/**
			 * chartAnchor 图表锚点
			 * 
			 * @type Ext.fc.xmlel.ChartAnchor
			 */
			chartAnchor : null,
			/**
			 * chartTooltip 图表浮点
			 * 
			 * @type Ext.fc.xmlel.ChartTooltip
			 */
			chartTooltip : null,
			/**
			 * marginPadding 边距与缩进
			 * 
			 * @type Ext.fc.xmlel.MarginPadding
			 */
			chartMarginPadding : null,
			/**
			 * Ext.fc.xmlel.VLine 独立竖线
			 * 
			 * @type Ext.fc.xmlel.VLine []
			 */
			chartVLine : null,
			/**
			 * smartLabel 智能标签
			 * 
			 * @type Ext.fc.xmlel.SmartLabel
			 */
			chartSmartLabel : null,
			/**
			 * chartLegend 图例
			 * 
			 * @type Ext.fc.xmlel.ChartLegend
			 */
			chartLegend : null,
			/**
			 * numberFormattiong 数字格式
			 * 
			 * @type Ext.fc.xmlel.NumberFormattiong
			 */
			chartNumberFormattiong : null,
			/**
			 * numberScaling 数字截取
			 * 
			 * @type Ext.fc.xmlel.NumberScaling
			 */
			chartNumberScaling : null,
			/**
			 * chartStyles 样式表
			 * 
			 * @type Ext.fc.xmlel.ChartStyle
			 */
			chartStyles : null,
			/**
			 * constructor 构造方法
			 */
			constructor : function(config) {

				Ext.fc.data.ChartJsonData.superclass.constructor.call(this);
				Ext.apply(this, config);
			},
			/**
			 * getHeader 生成<chart> 头
			 */
			getHeader : function() {
				var strchart = '<chart ';

				for (var obj in this) {
					// 对包含chart字符的属性进行操作
					// 理论上不会出错
					if (obj.indexOf('chart') > -1 && !Ext.isEmpty(this[obj])) {
						var item = this[obj];
						var parstr = '';
						if (Ext.isFunction(item.parseToXml))
							parstr = item.parseToXml();
						strchart += parstr;
					}

				}

				strchart += '>';

				return strchart;

			},
			/**
			 * getBody 生成body
			 */
			getBody : function() {
				return '';
			},
			/**
			 * getLines 生成TrendLine 和Vline
			 */
			getLines : function() {
				// return '';
				var rtnstr = '';
				// 走势线
				if (!Ext.isEmpty(this.chartTrendLine)) {
					rtnstr = ' <trendLines>';
					Ext.each(this.chartTrendLine, function(line) {
								// var line = this.chartTrendLine[0];
								if (!Ext.isEmpty(line.startValue)) {
									rtnstr += '<line ';
									rtnstr += line.parseToXml();
									rtnstr += ' />';
								}

							});
					rtnstr += ' </trendLines>';

				}

				return rtnstr;

			},
			/**
			 * getStyels 生成样式表
			 */
			getStyels : function() {
				var rtnstr = '';
				if (Ext.isEmpty(this.chartStyles))
					return '';
				rtnstr = '<styles>\n<definition>\n';
				Ext.each(this.chartStyles, function(style) {
							if (Ext.isEmpty(style[name])
									|| Ext.isEmpty(style[toObject])
									|| Ext.isEmpty(style[type])) {
								var str = '<style ';
								str += style.parseToXml();
								str += '/>\n';
								rtnstr += str;
							}
						});
				rtnstr += '</definition>\n<application>\n';
				Ext.each(this.chartStyles, function(style) {
							if (Ext.isEmpty(style[name])
									|| Ext.isEmpty(style[toObject])
									|| Ext.isEmpty(style[type])) {
								var str = '<apply ';
								str += 'toObject=\'' + style['toObject']
										+ '\' styles=\'' + style['name']
										+ '\' />\n';

								rtnstr += str;
							}
						});
				rtnstr += '</application>\n';
				rtnstr += '</styles>\n';

				return rtnstr;
				// return '';
			},
			/**
			 * genXML 生成 XML 数据
			 * 
			 * @return 生成的XML的数据
			 */
			genXML : function() {

				var rtnstr = this.getHeader() + this.getBody()
						+ this.getLines() + this.getStyels() + '</chart>';
				// alert(rtnstr);
				return rtnstr;
			},
			/**
			 * getVLine 生成竖线
			 * 
			 * @param position
			 *            根据位置查找是否有
			 * @return {String}
			 */
			getVLine : function(position) {
				var rtnstr = '';
				// 独立竖线
				if (Ext.isEmpty(this.chartVLine))
					return '';

				Ext.each(this.chartVLine, function(line) {
							// var line = this.chartTrendLine[0];
							if (!Ext.isEmpty(line.position)
									&& line.position == position) {
								rtnstr += '<vLine ';
								rtnstr += line.parseToXml();
								rtnstr += ' />';
							}

						});

				return rtnstr;
			}
		});

Ext.reg('chartjsondata', Ext.fc.data.ChartJsonData);

/**
 * 单列数据类型数据
 * 
 * @class Ext.fc.data.SingleSeriesData
 * @extends Ext.fc.data.ChartJsonData
 * @description 由于使用循环处理数据,因此未处理单独某一列特殊显示的问题
 */
Ext.fc.data.SingleSeriesData = Ext.extend(Ext.fc.data.ChartJsonData, {
			/**
			 * dataJson 要显示图形的数据
			 * 
			 * @type Object Array[]
			 */
			dataJson : '',
			/**
			 * xData X轴对应的字段
			 * 
			 * @type String
			 */
			xData : '',
			/**
			 * yData Y轴对应的字段
			 * 
			 * @type String
			 */
			yData : '',
			/**
			 * 生成XML数据主体
			 */
			getBody : function() {
				var rtnstr = '';
				var el = ' <set '
				var x = this.xData;
				var y = this.yData;
				var json = this.dataJson;
				if (Ext.isEmpty(x) || Ext.isEmpty(y) || Ext.isEmpty(json))
					return '';
				var i = 0;
				Ext.iterate(json, function(item) {
							if (!Ext.isEmpty(item[x]) && !Ext.isEmpty(item[y])) {
								rtnstr += el + 'label=\'' + item[x]
										+ '\' value=\'' + item[y] + '\' />';
								rtnstr += this.getVLine(i++);
							}

						}, this);

				return rtnstr;

			}
		});

/**
 * 多列数据类型数据
 * 
 * @class Ext.fc.data.SingleSeriesData
 * @extends Ext.fc.data.ChartJsonData
 * @description 由于使用循环处理数据,因此未处理单独某一列特殊显示的问题
 */
Ext.fc.data.MultiSeriesData = Ext.extend(Ext.fc.data.ChartJsonData, {
	/**
	 * dataJson 要显示图形的数据
	 * 
	 * @type Object Array[]
	 */
	dataJson : '',
	/**
	 * xData X轴对应的字段
	 * 
	 * @type String
	 */
	xData : '',
	/**
	 * yData Y轴对应的字段
	 * 
	 * @type string[]
	 */
	yData : [],
	/**
	 * yDataLabel y轴显示数据集的名字
	 * 
	 * @type string[]
	 */
	yDataLabel : [],
	/**
	 * 生成XML数据主体
	 */
	getBody : function() {
		var rtnstr = '';

		var x = this.xData;
		var y = this.yData;
		var yl = this.yDataLabel;
		var json = this.dataJson;
		if (Ext.isEmpty(x) || Ext.isEmpty(y) || Ext.isEmpty(json)
				|| Ext.isEmpty(yl))
			return '';

		if (y.length != yl.length)
			return '';

		var xstr = '<categories> ';

		var i = 0;
		Ext.iterate(json, function(item) {
					if (!Ext.isEmpty(item[x])) {
						xstr += '<category label=\'' + item[x] + '\'/>';
						xstr += this.getVLine(i++);
					}
				}, this);

		xstr += ' </categories> ';

		var ystr = '';
		for (var i = 0; i < y.length; i++) {
			var ysubstr = '<dataset seriesName=\'' + this.yDataLabel[i] + '\'>';
			Ext.iterate(json, function(item) {
						if (!Ext.isEmpty(item[y[i]])) {
							ysubstr += '<set value=\'' + item[y[i]] + '\'/>';
						}
					}, this);
			ysubstr += '</dataset>';
			ystr += ysubstr;
		}

		return xstr + ystr;

	}
});

Ext.namespace("Ext.fc");
/**
 * FusionChart 对象封装
 * 
 * @class Ext.fc.FusionChart
 * @extends Ext.FlashComponent
 */
Ext.fc.FusionChart = Ext.extend(Ext.FlashComponent, {
			// /**
			// * initialDataSet 是否初始化数据
			// *
			// * @type Boolean
			// */
			// initialDataSet : false,
			/**
			 * params 参数
			 * 
			 * @type Object
			 * @description 对应fusionchart.js的 params
			 */
			flashParams : new Object(),

			/**
			 * variables 变量
			 * 
			 * @type Object
			 * @description 对应fusionchart.js的ariables Flash的quality
			 *              allowScriptAccess debugMode scaleMode
			 */
			flashVars : new Object(),
			/**
			 * DataURL 数据文件路径
			 * 
			 * @type string url
			 */
			DataURL : null,
			/**
			 * DataXML 数据
			 * 
			 * @type
			 */
			DataXML : null,
			/**
			 * JsonData 使用JsonData
			 * 
			 * @type
			 */
			JsonData : null,
			/**
			 * 是否监控容器尺寸变化
			 * 
			 * @type Boolean
			 * @default true
			 * @description 为true时第一次渲染会渲染两次 为false时，不跟随容器变化，注意！
			 */
			monitorParentResize : true,

			monitorResize : true,

			/**
			 * onRender 渲染
			 * 
			 * @description 渲染前处理数据 问题：第一次渲染会渲染两次
			 */

			onRender : function() {
				this.initData();
				Ext.fc.FusionChart.superclass.onRender.apply(this, arguments);
			},

            /**
             * onResize
             * @param {} adjWidth
             * @param {} adjHeight
             * @param {} rawWidth
             * @param {} rawHeight
             * 在尺寸改变的时候自动重绘
             */
			onResize : function(adjWidth, adjHeight, rawWidth, rawHeight) {
				this.swfHeight = rawHeight;
				this.swfWidth = rawWidth;
				this.reRender();
			},

			/**
			 * initData 初始化数据
			 * 
			 * @description 优先级: DataURL DataXML List or Array or Object
			 */
			initData : function() {
				if (!Ext.isEmpty(this.DataURL)) {
					this.setDataURL(this.DataURL);
					return;
				}
				if (!Ext.isEmpty(this.DataXML)) {
					this.setDataXML(this.DataXML);
					return;
				}
				if (!Ext.isEmpty(this.JsonData)) {
					if (Ext.isFunction(this.JsonData.genXML))
						this.setDataXML(this.JsonData.genXML());
				}
				// this.progressXml();

			},
			/**
			 * changeDataXML 切换数据XML 并重新渲染
			 * 
			 * @param strDataXML
			 */
			changeDataXML : function(strDataXML) {
				delete this.flashVars['dataURL'];
				this.DataURL = '';
				this.DataXML = strDataXML;

				if (this.rendered) {
					this.reRender();
				}
			},
			/**
			 * changeDataURL 切换数据源为 DataURL 的xml 文件并渲染
			 * 
			 * @param strDataXML
			 */
			changeDataURL : function(strDataURL) {
				this.DataURL = strDataURL;
				if (this.rendered) {
					this.reRender();
				}
			},
			/**
			 * changeJsonData 修改数据源为 JsonData 并渲染
			 * 
			 * @param jsonData
			 */
			changeJsonData : function(jsonData) {
				delete this.flashVars['dataURL'];
				delete this.flashVars['dataXML'];
				this.DataURL = '';
				this.DataXML = '';
				this.JsonData = jsonData;
				if (this.rendered) {
					this.reRender();
				}
			},
			/**
			 * reRender 重新生成图表
			 */
			reRender : function() {
				this.rendered = false;
				this.render();
			},

			/**
			 * attributes 属性
			 * 
			 * @type Array
			 * @description 保存 swfurl swfid width height等
			 * @deprecated 使用Ext处理
			 */
			// attributes : new Array(),
			/**
			 * setDataURL 添加DataURL
			 * 
			 * @param strDataURL
			 */
			setDataURL : function(strDataURL) {
				delete this.flashVars['dataURL'];
				delete this.flashVars['dataXML'];
				this.DataURL = strDataURL;
				this.DataXML = '';
				this.addFlashVars('dataURL', strDataURL);

			},
			/**
			 * setDataXML 设置 Flash图表数据
			 * 
			 * @param strDataXML
			 */
			setDataXML : function(strDataXML) {
				delete this.flashVars['dataURL'];
				delete this.flashVars['dataXML'];
				this.DataURL = '';
				this.DataXML = strDataXML;
				this.addFlashVars('dataXML', this.encodeDataXML(strDataXML));

			},
			/**
			 * addFlashVars
			 * 
			 * @param name
			 * @param value
			 */
			addFlashVars : function(name, value) {
				this.flashVars[name] = value;
			},
			/**
			 * getFlashVars
			 * 
			 * @param name
			 * @return
			 */
			getFlashVars : function(name) {
				return this.flashVars[name];
			},

			// private This function :
			// fixes the double quoted attributes to single quotes
			// Encodes all quotes inside attribute values
			// Encodes % to %25 and & to %26;
			encodeDataXML : function(strDataXML) {

				var regExpReservedCharacters = ["\\$", "\\+"];
				var arrDQAtt = strDataXML.match(/=\s*\".*?\"/g);
				if (arrDQAtt) {
					for (var i = 0; i < arrDQAtt.length; i++) {
						var repStr = arrDQAtt[i].replace(/^=\s*\"|\"$/g, "");
						repStr = repStr.replace(/\'/g, "%26apos;");
						var strTo = strDataXML.indexOf(arrDQAtt[i]);
						var repStrr = "='" + repStr + "'";
						var strStart = strDataXML.substring(0, strTo);
						var strEnd = strDataXML.substring(strTo
								+ arrDQAtt[i].length);
						var strDataXML = strStart + repStrr + strEnd;
					}
				}

				strDataXML = strDataXML.replace(/\"/g, "%26quot;");
				strDataXML = strDataXML.replace(/%(?![\da-f]{2}|[\da-f]{4})/ig,
						"%25");
				strDataXML = strDataXML.replace(/\&/g, "%26");

				return strDataXML;

			}

		});

Ext.reg('fusionchart', Ext.fc.FusionChart);
