Ext.onReady(function(){
	
	
	   //采集点方案列表
	    var data1 = [

		             ['南京供电公司','123456','0897','300kwh','浦口','未知','未知','未知','新增'],
		             ['无锡供电公司','123456','0897','300kwh','未知','未知','未知','未知','新增'],
		             ['扬州供电公司','123456','0897','300kwh','未知','未知','未知','未知','新增'],
		             ['苏州供电公司','123456','0897','300kwh','未知','未知','未知','未知','新增']
		         
		             ];
	          
		var cm_cjdlist = new Ext.grid.ColumnModel([                	
		   {header:'供电单位',dataIndex:'gddw',sortable:true,resizable:true,align:'center'},
		   {header:'传单编号',dataIndex:'cdbh',sortable:true,resizable:true,align:'center'},
		   {header:'用户编号',dataIndex:'yhbh',sortable:true,resizable:true,align:'center'},
		   {header:'申报容量',dataIndex:'sbrl',sortable:true,resizable:true,align:'center'},
		   {header:'采集对象',dataIndex:'cjdx',sortable:true,resizable:true,align:'center'},
		   {header:'控制方式',dataIndex:'kzfs',sortable:true,resizable:true,align:'center'},
		   {header:'计量方式',dataIndex:'jlfs',sortable:true,resizable:true,align:'center'},
		   {header:'设备类别',dataIndex:'sblb',sortable:true,resizable:true,align:'center'},
		   {header:'变更方式',dataIndex:'bgfs',sortable:true,resizable:true,align:'center'}
		 ]);
		
		 var ds_cjdlist = new Ext.data.Store({
			    proxy: new Ext.data.MemoryProxy(data1),
			    reader: new Ext.data.ArrayReader({}, [
			    {name: 'gddw'},
			    {name: 'cdbh'},
			    {name: 'yhbh'},
			    {name: 'sbrl'},
			    {name: 'cjdx'},
			    {name: 'kzfs'},
			    {name: 'jlfs'},
			    {name: 'sblb'},
			    {name: 'bgfs'}
			   ])
			   });
    	 
	 var cjdlist = new Ext.grid.GridPanel({
		    //id:'cjdlist',
		    region:'center',
		    layout:'fit',
		    //border:false,
		    title:"采集点方案",
			store: ds_cjdlist,
			cm: cm_cjdlist,
			stripeRows: true
			/*bbar : new Ext.ux.MyToolbar( {
					store : ds_cjdlist
			})*/
	 });
	 ds_cjdlist.load();
	
	

	 //查询条件
	var panel_cxtj = new Ext.Panel({
	 	 border : false,
	 	 // collapsible:true,
	 	 layout:'column', 
	 	  bodyStyle : 'padding:15px 5px 0px 10px',
         items:[{
	 	 	 //bodyStyle : 'padding:20px 5px 0px 10px',
	 	 	 layout:'form',
	 	 	 columnWidth:.42,
	 	 	 labelAlign: "right",
	 	 	 labelWidth : 80,
	 	 	 border:false,
	 	 	 items: [{
	 	 	   fieldLabel:'用户编号',
			   xtype:'textfield',
			   labelSeparator:'',
			   anchor:'60%'
	 	 	 }]
	 	 	 },{
	 	 	// bodyStyle : 'padding:20px 5px 0px 10px',
	 	 	 layout:'form',
	 	 	 columnWidth:.42,
	 	 	 labelAlign: "right",
	 	 	 labelWidth : 80,
	 	 	 border:false,
	 	 	 items: [{
	 	 	   fieldLabel:'传单编号',
			   xtype:'textfield',
			   labelSeparator:'',
			   anchor:'60%'
	 	 	 }]
	 	 	 },{
         	columnWidth:.16,
		    border:false,
		    bodyStyle : 'padding:0px 0px 0px 15px',
		    items: [{   	
                    xtype:'button',
                    text:'查询',
                    width:80
                   }]
		    } ]
	 
	 });
	 var panle_cx=new Ext.Panel({
	    region:"north",
	 	height:60,
	 	layout:'fit',
	 	border : false,
	 	items:[panel_cxtj]
	 });
	
	 var panel_zdazkc= new Ext.Panel({
		 region:'south',
		 height:200,
	 	 title:"终端安装勘察",
	 	 layout:'anchor',
	 	 border : false,
	 	 bodyStyle : 'padding:10px 5px 0px 10px',
	 	 items:[{
	 	 	 //title:'Item 1',
	 	 	 border:false,
	 	 	 xtype:'panel',
	 	 	 anchor:'100% 25%',
	 	 	 layout:'column',
	 	 	 items:[{
	 	 	 layout:'form',
	 	 	 columnWidth:.28,
	 	 	 labelAlign: "right",
	 	 	 labelWidth : 80,
	 	 	 border:false,
	 	 	 items: [{
	 	 	   fieldLabel:'采集点编号',
			   xtype:'textfield',
			   labelSeparator:'',
			   anchor:'95%'
	 	 	 }]
	 	 	 },{
	 	 	  layout:'form',
	 	 	 columnWidth:.28,
	 	 	 labelAlign: "right",
	 	 	 labelWidth : 80,
	 	 	  border:false,
	 	 	 items: [{
	 	 	   fieldLabel:'采集点类型',
			   xtype:'combo',
			   labelSeparator:'',
			    anchor:'95%'
	 	 	 }]
	 	 	 },{
	 	 	  layout:'form',
	 	 	 columnWidth:.28,
	 	 	 labelAlign: "right",
	 	 	 labelWidth : 80,
	 	 	 border:false,
	 	 	 items: [{
	 	 	   fieldLabel:'通讯方式',
			   xtype:'combo',
			   labelSeparator:'',
			    anchor:'94%'
	 	 	 }]
	 	 	 },
	 	 	 {
	 	 	 columnWidth:.16,
	 	 	 border:false,
	 	 	  items: [{
			   xtype:'button',
			   text:'通过',
			   width:80
	 	 	 }]
	 	 	 
	 	 	 }]
	 	 },{
	 	 	//title:'Item 2',
	 	 	 border:false,
	 	 	 xtype:'panel',
	 	 	 anchor:'100% 25%',
	 	 	 layout:'column',
	 	 	 items:[{ 
	 	 	 layout:'form',
	 	 	 columnWidth:.28,
	 	 	 labelAlign: "right",
	 	 	 labelWidth : 80,
	 	 	 border:false,
	 	 	 items: [{
	 	 	   fieldLabel:'通信场强',
			   xtype:'textfield',
			   labelSeparator:'',
			   anchor:'95%'
	 	 	 }] 
	 	 	 },{
	 	 	 layout:'form',
	 	 	 columnWidth:.56,
	 	 	 labelAlign: "right",
	 	 	 labelWidth : 80,
	 	 	 border:false,
	 	 	 items: [{
	 	 	   fieldLabel:'终端安装位置',
			   xtype:'textfield',
			   labelSeparator:'',
			   anchor:'97%'
	 	 	 }]
	 	 	 }] 
	 	 },{
	 	 	 //title:'Item 3',
	 	 	 border:false,
	 	 	 xtype:'panel',
	 	 	 anchor:'100% 50%',
	 	 	 layout:'column',
	 	 	 items:[{ 
	 	 	 layout:'form',
	 	 	 columnWidth:.84,
	 	 	 labelAlign: "right",
	 	 	 labelWidth : 80,
	 	 	 border:false,
	 	 	 items: [{
	 	 	   fieldLabel:'走线位置',
			   xtype:'textarea',
			   labelSeparator:'',
			   anchor:'98%'
	 	 	 }] 
	 	 	 }/*,{
             bodyStyle : 'padding:0px 5px 0px 70px',
	 	 	 columnWidth:.25,
	 	 	 border:false,
	 	 	 items: [{
	 	 	    xtype:'button',
			    text:'生成终端安装方案',
			    width:110
	 	 	 }]
	 	 	 }*/]
	 	 }]
	 });
	 var panelx=new Ext.Panel({
	 	region:'center',
	 	layout:'border',
	 	border : false,
	 	items:[cjdlist,panel_zdazkc]
	 	
	 });

	
	var viewPanel = new Ext.Panel({
		layout: 'border',
		//minWidth : Ext.get('勘查').getWidth(),
        //height : Ext.get('勘查').getHeight(),
		items: [panle_cx,panelx],
		border : false
	});
	renderModel(viewPanel, '勘查');
});