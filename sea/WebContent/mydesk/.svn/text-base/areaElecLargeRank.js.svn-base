function getBrowser(){
	var oType ;
	if(navigator.userAgent.indexOf("MSIE")!=-1){
		oType = "IE"
	}else if(navigator.userAgent.indexOf("Firefox")!=-1){
		oType="FIREFOX";
	}
	return oType;
}	

Ext.onReady(function(){
	var cm = new Ext.grid.ColumnModel([
		{
			header : '排名',
			sortable : true,
			align : 'center',
			dataIndex : 'order',
			width : 50
		},{
			header : '上月用电量(kWh)',
			sortable : true,
			align : 'center',
			dataIndex : 'pap_e',
			renderer:function(val){
				var html = '<div align = "right" ext:qtitle="上月用电量(kWh)" ext:qtip="' + Ext.util.Format.number(val,'0,000.00')
				+ '">' + Ext.util.Format.number(val,'0,000.00') + '</div>';
				return html;
			}
		},{
			header : '用户编号',
			sortable : true,
			align : 'center',
			dataIndex : 'cons_no',
			width : 80
		}, {
			header : '用户名称',
			sortable : true,
			align : 'center',
			dataIndex : 'cons_name',
			width : 130,
			renderer:function(val){
				var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val
				+ '">' + val + '</div>';
				return html;
			}
		}, {
			header : '供电单位',
			sortable : true,
			align : 'center',
			dataIndex : 'org_name',
			width : 150,
			renderer:function(val){
				var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val
				+ '">' + val + '</div>';
				return html;
			}
		}
	]);
	
	var arrMonth = ['01','02','03','04','05','06','07','08','09','10','11','12'];
	
	var myDate = new Date();
	var year;
	if(getBrowser() == 'IE'){
		year = myDate.getYear();
	}else if(getBrowser() == 'FIREFOX'){
		year = myDate.getYear()+1900;
	}
	var month = myDate.getMonth();
	var date;
	if(month == 0){
		year = year - 1;
		date = (year+'-'+'12');
	}else{
		date = (year+'-'+arrMonth[month-1]);
	}
	
	var store = new Ext.data.Store({
		autoLoad:true,
		url:'mydesk/mydesk!queryElecOrder.action',
		baseParams:{date:date},
		reader:new Ext.data.JsonReader({
			root:'ls'
		},[
			{name:'order'},
			{name:'pap_e'},
			{name:'cons_no'},
			{name:'cons_name'},
			{name:'org_name'}
		])
	});
	
//	store.load();
	
	var grid = new Ext.grid.GridPanel({
		cm:cm,
		height:290,
		store:store,
		monitorResize : true,
		autoScroll : true,
		viewConfig:{
			forceFit: false
		}
	})
	
	renderModel(grid,'4');
//	grid.render('areaElecLargeRank');
})