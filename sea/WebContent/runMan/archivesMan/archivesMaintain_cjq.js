
Ext.namespace("Ext.ux");

var selectCpNo;
var selectItemsRecord = new Ext.data.Record.create([
   {name : 'consno',mapping : 'consno'},
   {name : 'consname',mapping : 'consname'},
   {name : 'instloc',mapping : 'instloc'},
   {name : 'meterid',mapping : 'meterid'},
   {name : 'mrsectno',mapping : 'mrsectno'},
   {name : 'tmnlassetno',mapping : 'tmnlassetno'},
   {name : 'fmrassetno',mapping : 'fmrassetno'},
   {name : 'assetno',mapping : 'assetno'},
   {name : 'collobjid',mapping : 'collobjid'}
]);

var ygbStore = new Ext.data.Store({//已挂表Store
	proxy : new Ext.data.HttpProxy({
		url : './runman/archivercpman!queryCmeterIsHang.action'
	}),
	reader : new Ext.data.JsonReader({
		root : 'cmeterlist',
		totalProperty:'totalCount'
	}, [{name:'consno'},{name:'consname'},{name:'instloc'},{name:'meterid'},{name:'mrsectno'},{name:'tmnlassetno'},
		{name:'fmrassetno'},{name:'assetno'},{name:'collobjid'}]
	)
});


//已选定的电能表信息GridPanel
var rightButGrid = new Ext.grid.GridPanel({
	store:ygbStore,
	viewConfig : {forceFit : false},
	baseCls:'x-plain',
	bodyStyle:'padding:0 0 0 0',
	margins:'0 0 0 0',
	//height:190,
	columns:[
		{header: "用户编号",dataindex:'consno'},
		{header: "用户名称",dataindex:'consname'},
		{header: "用电地址",dataindex:'instloc'},
		{header: "电能表标识",dataindex:'meterid'},
		{header: "抄表段号",dataindex:'mrsectno'},
		{header: "集中器资产",dataindex:'tmnlassetno'},
		{header: "采集器资产",dataindex:'fmrassetno'},
		{header: "表计资产号",dataindex:'assetno'},
		{header: "采集对象标识",dataindex:"collobjid"}
	],
	bbar : new Ext.ux.MyToolbar({
		id:'ydgStore',
		store : ygbStore
	})
});
rightButGrid.on("rowclick",function(grid,index,store){
	vboxPanel.findById('moveL').ids = index;
});


//查询指定采集点下的采集器信息Store ,ArchivesMaintain.js文件中有GridPanel的
//Store设为此Store,不可删除
var ReoDevStore = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
		url : './runman/archivercpman!queryREODev.action'
	}),
	reader : new Ext.data.JsonReader({
		root : 'reoDevlist'
	}, [{name:'collectorid'}, {name:'cpno'},{name:'preid'},{name:'collmode'},{name:'commaddr'},{name:'portno'},{name:'devmode'},{name:'devtypecode'},{name:'statuscode'},{name:'instdate'},{name:'installloc'},{name:'houseno'},{name:'floorno'},{name:'doorno'},{name:'ipaddr'},{name:'ipport'}]
	)
});

//采集器列表
var rightTopGrid = new Ext.grid.GridPanel({
	store:ReoDevStore,
	baseCls:'x-plain',
	bodyStyle:'padding:0 0 0 0',
	margins:'0 0 0 0',
	viewConfig : {forceFit : false},
	//height:190,
	columns:[
		{header: "采集器编号",dataIndex:'collectorid'},
		{header: "采集器资产编号",dataIndex : 'collectorid'},
		{header : '采集点编号',dataIndex : 'cpno'},
		{header: "采集器型号",dataIndex : 'devmode'},
		{header : 'IP地址  ',dataIndex : 'ipaddr'},
		{header : 'IP端口号  ',dataIndex : 'ipport'}
	]
});


var vboxPanel = new Ext.Panel({
	columnWidth:.1,
	//height:380,
	baseCls:'x-plain',
	layout: {
		type:'vbox',
		padding:'5',
		pack:'center',
		align:'center'
	},
	items:[{
		xtype:'button',
		id:'moveR',
		text: '&gt;&gt',
		handler:function(){	//选定数据移动到rightButGrid中
			var rowdata = dgbgridPanel.getSelectionModel().selections.items;
			if(null==rowdata || rowdata.length<=0){
				Ext.Msg.alert("提示","请选择要进行关联的用户信息!");
				return;
			}
			var recodeSelectData = new selectItemsRecord(rowdata[0].data);
			
			var cpNo = selectCpNo;//采集点标识
			var meterid = rowdata[0].get('meterid');//电能表标识
			Ext.Ajax.request({
				url: './runman/archivercpman!saveCmeterHangInfo.action',
			    success: function(response){
					var result =Ext.decode(response.responseText);
					if(result.success == true){
						var rcoid = result.rcoid;
						recodeSelectData.set("collobjid",rcoid);
						ygbStore.add(recodeSelectData);
						dgbgridPanel.getStore().removeAt(vboxPanel.findById('moveR').ids);
						
					}else{
						Ext.Msg.alert("警告","操作失败!");
					}
				},
			    params: { 
						'cpNo':cpNo ,
						'meterid':meterid
					}
			});
			
		}
	},{
		xtype:'button',
		id:'moveL',
		text: '&lt;&lt',
		handler:function(){
			var rowdata = rightButGrid.getSelectionModel().selections.items;
			if(null==rowdata || rowdata.length<=0){
				Ext.Msg.alert("提示","请选择要移除关联的用户信息!");
				return;
			}
			var recodeSelectData = new selectItemsRecord(rowdata[0].data);
			var collobjid = recodeSelectData.get("collobjid");
			Ext.Ajax.request({
				url: './runman/archivercpman!deleteCmeterHang.action',
				params: { 'collobjid':collobjid },
			    success: function(response){
					var result =Ext.decode(response.responseText);
					if(result.success == true){
						dgbStore.add(recodeSelectData);
						rightButGrid.getStore().removeAt(vboxPanel.findById('moveL').ids);
					}else{
						Ext.Msg.alert("警告","操作失败!");
					}
				}
			});
		}
	}]             
});




/**
 * 保存电能采集信息
 * @param store
 * @return
 */
var saveCemeter = function(store){
	
};



var rightPanel = new Ext.Panel({
	columnWidth:.45,
	//height:380,
	layout: {
		type:'vbox',
		padding:'0'
	},
	bodyStyle:'padding:0 0 0 0',
	margins:'0 0 0 0',
	items:[rightTopGrid,rightButGrid]
});


var dgbStore = new Ext.data.Store({//待挂点Store
	proxy : new Ext.data.HttpProxy({
		url : './runman/archivercpman!queryCmeterNotHang.action'
	}),
	reader : new Ext.data.JsonReader({
		root : 'cmeterlist',
		totalProperty:'totalCount'
	}, [{name:'consno'},{name:'consname'},{name:'instloc'},{name:'meterid'},{name:'mrsectno'},{name:'tmnlassetno'},
		{name:'fmrassetno'},{name:'assetno'}]
	)
});
var dgbgridPanel = new Ext.grid.GridPanel({//待挂表列表
	viewConfig : {forceFit : false},
	layout:'fit',
	columnWidth:.45,
	sm : new Ext.grid.RowSelectionModel(),
	store:dgbStore,
	autoScroll:true,
	columns:[
		{header: "用户编号",dataindex:'consno'},
		{header: "用户名称",dataindex:'consname'},
		{header: "用电地址",dataindex:'instloc'},
		{header: "电能表标识",dataindex:'meterid'},
		{header: "抄表段号",dataindex:'mrsectno'},
		{header: "集中器资产",dataindex:'tmnlassetno'},
		{header: "采集器资产",dataindex:'fmrassetno'},
		{header: "表计资产号",dataindex:'assetno'}
	],
	bbar : new Ext.ux.MyToolbar({
		id:'mytobar',
		store : dgbStore
	})
});


//定义待挂表列表行点击事件
dgbgridPanel.on("rowclick",function(grid,index,store){
	vboxPanel.findById('moveR').ids = index;
	
});




var jcdgbPanel = new Ext.Panel({
	margins:'0 0 0 0',
	bodyStyle:'padding:0 0 0 0',
    layout:'column',
    baseCls:'x-plain',
    region:'center',
	items:[dgbgridPanel,vboxPanel,rightPanel]
});

//当前jcdgbPanel面板大小发生变化时，更新内部面板，使之高度与
//jcdgbPanel的高度相等
jcdgbPanel.on('bodyresize',function(v){
	var jcdgbPanelHeight = jcdgbPanel.getHeight();
	dgbgridPanel.setHeight(jcdgbPanelHeight);
	vboxPanel.setHeight(jcdgbPanelHeight);
	rightPanel.setHeight(jcdgbPanelHeight);
	
	rightTopGrid.setHeight(jcdgbPanelHeight/2);
	rightButGrid.setHeight(jcdgbPanelHeight/2);
});

var dPanel = new Ext.Panel({
	margins:'0 0 0 0',
	bodyStyle:'padding:0 0 0 0',
	autoScroll:true,
	title:'集抄待挂表',
	layout:'border',
	items:[jcdgbPanel]
	
});
var jcygbPanel = new Ext.grid.GridPanel({//已挂表Gridpanel
	title:'集抄已挂表',
	store:ygbStore,
	baseCls:'x-plain',
	layout:'fit',
	columns:[
		{header: "用户编号",dataindex:'consno'},
		{header: "用户名称",dataindex:'consname'},
		{header: "用电地址",dataindex:'instloc'},
		{header: "电能表标识",dataindex:'meterid'},
		{header: "抄表段号",dataindex:'mrsectno'},
		{header: "集中器资产",dataindex:'tmnlassetno'},
		{header: "采集器资产",dataindex:'fmrassetno'},
		{header: "表计资产号",dataindex:'assetno'},
		{header: "采集对象标识",dataindex:"collobjid"}
	],
	bbar : new Ext.ux.MyToolbar({
		id:'ydgStore1',
		store : ygbStore
	})
});



//主面板
var vcq = new Ext.TabPanel({
	activeTab:0,
	resizeTabs:true,
	items:[jcygbPanel,dPanel]
});

var cplinkcq=new Ext.Panel({
	layout:'fit',
	items:[vcq]  
});
//定义Tab激活事件
cplinkcq.on("activate",function(apanel){
	
	//ygbStore.load({params:{cpNo:selectCpNo}});
	
	ygbStore.baseParams={
			cpNo:selectCpNo
		};
	ygbStore.load({params:{
			cpNo:selectCpNo,
			start:0,
			limit:50
	}});
	
	dgbStore.baseParams={
			cpNo:selectCpNo
		};
	dgbStore.load({params:{
			cpNo:selectCpNo,
			start:0,
			limit:50
	}});
});



