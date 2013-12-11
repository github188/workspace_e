var win = new Ext.Window({
	title : 'sea3000发布说明 build2010-07-10',
	width : '580',
	resizable : 'false',
	height : '400',
	closeAction:'hide',
	html: '<p>已修正的bug</p>'+
		  '<p>sea-0001：ie6兼容问题</p>'+
		  '<p>sea-0002:	变电站查询出现错误数据</p>'+
		  '<p>sea-0003: 低压集抄界面无法查询数据</p>'+
		  '<p>sea-0004: 采集点查询按照模糊查询无法查询集抄信息</p>'+
		  '<p>sea-0005: 从电网查询中，应能将线路下的台区、用户（专变、公变）信息按照树状结构进行显示</p>'+
		  '<p>sea-0006: 采集任务无法查询</p>'+
		  '<p>sea-0007: 数据召测成功后，召测数据应按照数据项进行显示，目前无法判断为那种数据项召测成功</p>'+
		  '<p>sea-0008：手工补召应在采集任务可以设置，手工补召次数限制</p>'+
		  '<p>sea-0009: 走终端剔除时，系统没有操作记录</p>'+
		  '<p>sea-0010: 主功能模块的字体被挡住</p>'+
		  '<p>sea-0011: “数据召测”功能下，对于测量点号/总加组号的选择上，最好是能看到电表的相关信息，以<BR/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;及对应的数字表示的是那块电能表，不然只有数字，对于资产的信息比较模糊</p>'+
		  '<p>sea-0012: 数据召测在备选用户－终端地址增加链接，点击链接显示终端对应的电能表，显示信息包<BR/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;括计量编号、计量名称、电能表资产号、测量点信息，操作员可选择<BR/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;表计进行召测。选择测量点召测或选择终端对 应的电能表召测，二种方式在操作中只能选<BR/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一个，不同时支持二种选择方式</p>'+
		  '<p>sea-0013: 档案维护中从营销获取数据不允许修改，且系统中显示采用营销后台的代码表示</p>'+
		  '<p>sea-0014: 按照营销单位管理权限进行设置权限</p>'+
		  '<p>sea-0015: 终端手工进行调试界面</p>'+
		  '<p>sea-0016: 原始报文查询：上行，来源地址应该是SIM卡的IP地址</p>'+
		  '<p>sea-0017: 抄表数据查询：请将无功和费率加进去</p>'+
		  '<p>sea-0018: 抄表数据查询：增加抄表成功率，并显示抄表成功数和抄表失败数</p>'+
		  '<p>sea-0019: 点击失败的抄表类信息后，出现错误数据。失败数据仅显示1-2户</p>'+
		  '<p>sea-0020: 点击失败{抄表类}超链接后，在手工补召页面，将页面显示的数据设为50条，刷新后，还<BR/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;是以前的显示数</p>'+
		  '<p>sea-0021: 终端实时工况中应增加终端IP</p>'
});

function open_win(){
	win.show();
}