var xgfa;

function openWindow(){
	xgfa.show();
}

Ext.onReady(function(){
	
/*   var dataz = [
	             ['1','male','name1','descn1','2','female','name2'],
                 ['1','male','name1','descn1','2','female','name2'],
                 ['1','male','name1','descn1','2','female','name2'],
                 ['1','male','name1','descn1','2','female','name2'],
                 ['1','male','name1','descn1','2','female','name2'],
                 ['1','male','name1','descn1','2','female','name2']
	             ];
          
	 var cm_zdazfa = new Ext.grid.ColumnModel([
     //sm_zdazfa,
      new Ext.grid.RowNumberer({
	  header : '序号',
	  width : 35
      }),                 	
	   {header:'计量点号',dataIndex:'jldh',sortable:true,resizable:true},
	   {header:'终端编号',dataIndex:'zdbh',sortable:true,resizable:true},
	   {header:'电能表编号',dataIndex:'dnbbh',sortable:true,resizable:true},
	   {header:'终端方案ID',dataIndex:'zdfaid',sortable:true,resizable:true},
	   {header:'电能表方案ID',dataIndex:'dnbfaid',sortable:true,resizable:true},
	   {header:'附属电能表',dataIndex:'fsdnb',sortable:true,resizable:true},
	   {header:'变更类型',dataIndex:'bglx',sortable:true,resizable:true}
	 ]);
	
	 var ds_zdazfa = new Ext.data.Store({
		    proxy: new Ext.data.MemoryProxy(dataz),
		    reader: new Ext.data.ArrayReader({}, [
		    {name: 'jldh'},
		    {name: 'zdbh'},
		    {name: 'dnbbh'},
		    {name: 'zdfaid'},
		    {name: 'dnbfaid'},
		    {name: 'fsdnb'},
		    {name: 'bglx'}
		   ])
		   });
	 ds_zdazfa.load();
	 
	 var grid_zdazfa = new Ext.grid.GridPanel({
		    id:'zdazfagrid',
		    region:'center',
			ds: ds_zdazfa,
			cm: cm_zdazfa,
			//sm: cm_zdazfa,
			stripeRows: true,
			layout:'fit',
			//border:false
			bbar : new Ext.ux.MyToolbar( {
					store :  ds_zdazfa
			})
	 });*/
	//-----------------------------------------------------------采集点信息
	var formc1=new Ext.Panel({
	 anchor:'100% 20%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 70,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'采集点编号',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
	  		 },{
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 70,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'采集点名称',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             },{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 70,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'采集点类别',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             }]

	});
	
	var formc2=new Ext.Panel({
	 anchor:'100% 20%',
	 layout:'column',
	 border:false,
	 items:[ {
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 70,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'经度',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
	  		 },{
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 70,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'纬度',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             },{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 70,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'采集点状态',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             }]

	});
	
	var formc3= new Ext.Panel({
		anchor:'100% 20%',
		layout:'form',
		border:false,
		labelAlign: "right",
	    labelWidth : 70,
		items:[{
		     fieldLabel:'采集点地址',
	  		 xtype:'textfield',
	  		 labelSeparator:'',
             anchor:'97%'
		}]
	});
	
	//-----------------------------------------------------------终端方案
	var formz0=new Ext.Panel({
	anchor:'100% 16.5%',
	layout:'column',
	border:false,
	items:[ {
	        columnWidth:.33,
	  		layout:'form',
	  		border:false,
	  		labelAlign: "right",
	        labelWidth : 80,
	        items:[{ 
	  		xtype:'textfield',
	  	    fieldLabel:'终端变更方式',
	  	    name:'zdbgfs',
	  		labelSeparator:'',
            anchor:'95%'
            }]
	        },{
	        columnWidth:.33,
	  		layout:'form',
	  		border:false,
	  		 bodyStyle : 'padding:0px 0px 0px 30px',
	        items:[{ 
	  		xtype:'button',
           text:'拆除终端信息',
	  		//iconCls: "./baseApp/gathorSet/images/add16.gif",
            width:100
            }]
	        }]
	});

    var formz1=new Ext.Panel({
	 anchor:'100% 16.5%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'终端资产编号',
	  	     name:'zdzcbh',
	  		 labelSeparator:'',
	  		 width:100
             //anchor:'95%'
             }]
	  		 },{
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'终端地址',
	  	     name:'zddz',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             },{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'出厂编号',
	  	     name:'ccbh',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             }]

	});
	
	var formz2=new Ext.Panel({
	 anchor:'100% 16.5%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'采集点编号',
	  	     name:'cjdbh',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
	  		 },{
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'条形码',
	  	     name:'txm',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             },{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'派工日期',
	  	     name:'pgrq',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             }]

	});
	
	var formz3=new Ext.Panel({
	 anchor:'100% 16.5%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.28,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'与表距离',
	  	     name:'ybjl',
	  		 labelSeparator:'',
             anchor:'95%'
	  		 }]
	  		 },{
	  		 columnWidth:.05,
	  		 border:false,
	  		 layout:'form',
	  		 bodyStyle : 'padding:5px 0px 0px 0px',
	  		 items:[{
	  		 xtype:'label',
	  		 text:'CM'
	  		 }]
	  		 },{
	         columnWidth:.28,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'电源取向',
	  	     name:'dyqx',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             },{
	  		 columnWidth:.05,
	  		 border:false,
	  		 layout:'form',
	  		 bodyStyle : 'padding:5px 0px 0px 0px',
	  		 items:[{
	  		 xtype:'label',
	  		 text:'CM'
	  		 }]
	  		 },{
	         columnWidth:.28,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'电源线长',
	  	     name:'dyxc',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             },{
	  		 columnWidth:.05,
	  		 border:false,
	  		 layout:'form',
	  		 bodyStyle : 'padding:5px 0px 0px 0px',
	  		 items:[{
	  		 xtype:'label',
	  		 text:'CM'
	  		 }]
	  		 }]

	});
	
	var formz4 =new Ext.Panel({
	 anchor:'100% 16.5%',
	 layout:'column',
	 border:false,
	 items:[{
	         columnWidth:.28,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'天线高度',
	  	     name:'txgd',
	  		 labelSeparator:'',
             anchor:'95%'
	  		 }]
	         },{
	  		 columnWidth:.05,
	  		 border:false,
	  		 layout:'form',
	  		 bodyStyle : 'padding:5px 0px 0px 0px',
	  		 items:[{
	  		 xtype:'label',
	  		 text:'CM'
	  		 }]
	  		 },{   
	         columnWidth:.28,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'combo',
	  	     fieldLabel:'安装环境照片',
	  	     name:'azhjzp',
	  		 labelSeparator:'',
             anchor:'95%'
	  		 }]
	  		 },{
	  		 columnWidth:.05,
	  		 border:false,
	  		 layout:'form',
	  		// bodyStyle : 'padding:5px 0px 0px 0px',
	  		 items:[{
	  		 xtype:'button',
	  		 width:10,
	  		 iconCls: 'add16'
	  		 }]
	  		 },{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'SIM卡信息',
	  	     name:'simxx',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             }]
	});
	
	var formz5= new Ext.Panel({
		anchor:'100% 16.5%',
		layout:'form',
		border:false,
		labelAlign: "right",
	    labelWidth :80,
		items:[{
		     fieldLabel:'安装地址',
		     name:'azdz',
	  		 xtype:'textfield',
	  		 labelSeparator:'',
             anchor:'64%'
		}]
	});
	
	 var formz = new Ext.FormPanel({
       id:'zdfamx',
       region:'south',
       layout:'anchor',
	   height:240,
       border:false,
	   bodyStyle : 'padding:30px 0px 0px 0px',
	   items:[formz0,formz1,formz2,formz3,formz4,formz5]  
    	
    });
     var datazd = [
	               ['12332','未知','ad345','新增','2337','2313','09.2.1','100','356','8','213','21','2','未知'],
                   ['1232','未知','ad345','拆装','2337','2313','09.2.1','100','356','8','213','21','2','未知']
	             ];
          
	 var cm_zdfa = new Ext.grid.ColumnModel([
     //sm_zdfa,
      /*new Ext.grid.RowNumberer({
	  header : '序号',
	  width : 35
      }),     */            	
	   {header:'终端资产编号',dataIndex:'zdzcbh',sortable:true,resizable:true,align:'center'},
	   {header:'终端地址',dataIndex:'zddz',sortable:true,resizable:true,align:'center'},
	   {header:'出厂编号',dataIndex:'ccbh',sortable:true,resizable:true,align:'center'},
	   {header:'终端变更方式',dataIndex:'zdbgfs',sortable:true,resizable:true,align:'center'},
	   {header:'采集点编号',dataIndex:'cjdbh',hidden:true},
	   {header:'条形码',dataIndex:'txm',hidden:true},
	   {header:'派工日期',dataIndex:'pgrq',hidden:true},
	   {header:'与表距离',dataIndex:'ybjl',hidden:true},
	   {header:'电源取向',dataIndex:'dyqx',hidden:true},
	   {header:'电源线长',dataIndex:'dyxc',hidden:true},
	   {header:'天线高度',dataIndex:'txgd',hidden:true},
	   {header:'SIM卡信息',dataIndex:'simxx',hidden:true},
	   {header:'安装地址',dataIndex:'azdz',hidden:true}
	 ]);
	
	 var ds_zdfa = new Ext.data.Store({
		    proxy: new Ext.data.MemoryProxy(datazd),
		    reader: new Ext.data.ArrayReader({}, [
		    {name: 'zdzcbh'},
		    {name: 'zddz'},
		    {name: 'ccbh'},
		    {name: 'zdbgfs'},
		    {name: 'cjdbh'},
		    {name: 'txm'},
		    {name: 'pgrq'},
		    {name: 'ybjl'},
		    {name: 'dyqx'},
		    {name: 'dyxc'},
		    {name: 'txgd'},
		    {name: 'simxx'},
		    {name: 'azdz'}
		   ])
		   }); 
	 
	 var grid_zdfa = new Ext.grid.GridPanel({
		    //id:'zdfagrid',
		    region:'center',
			store: ds_zdfa,
			cm: cm_zdfa,
			sm: new Ext.grid.RowSelectionModel({
                    singleSelect: true,
                    listeners: {
                        rowselect: function(sm, row, rec) {
                            Ext.getCmp("zdfamx").getForm().loadRecord(rec);
                        }
                    }
                }),
			stripeRows: true,
			layout:'fit'
			//border:false
	 });
     ds_zdfa.load();
	
	 //-----------------------------------------------------------负控开关轮次     
	 var dataz = [
	             ['123','2','3000','未知','3','4','可控','压板'],
                 ['123','3','5000','未知','3','4','可控','压板'],
                 ['123','4','4000','未知','3','4','可控','压板']
	             ];
	 var cm_fkkglc = new Ext.grid.ColumnModel([
     //sm_fkkglc,
     /* new Ext.grid.RowNumberer({
	  header : '序号',
	  width : 35
      }),   */              	
	   {header:'轮次',dataIndex:'lcbh',sortable:true,resizable:true,width:70,align:'center'},
	   {header:'轮次名称',dataIndex:'lcmc',sortable:true,resizable:true,width:90,align:'center'},
	   {header:'可控负荷',dataIndex:'kkfh',sortable:true,resizable:true,width:90,align:'center'},
	   {header:'开关类型',dataIndex:'kglx',sortable:true,resizable:true,width:90,align:'center'},
	   {header:'脱扣方式',dataIndex:'tkfs',sortable:true,resizable:true,width:90,align:'center'},
	   {header:'辅助节点',dataIndex:'fzjd',sortable:true,resizable:true,width:90,align:'center'},
	   {header:'控制接入',dataIndex:'kzjr',sortable:true,resizable:true,width:90,align:'center'},
	   {header:'压板投入',dataIndex:'ybtr',sortable:true,resizable:true,width:90,align:'center'}
	 ]);
	
	 var ds_fkkglc = new Ext.data.Store({
		    proxy: new Ext.data.MemoryProxy(dataz),
		    reader: new Ext.data.ArrayReader({}, [
		    {name: 'lcbh'},
		    {name: 'lcmc'},
		    {name: 'kkfh'},
		    {name: 'kglx'},
		    {name: 'tkfs'},
		    {name: 'fzjd'},
		    {name: 'kzjr'},
		    {name: 'ybtr'}
		   ])
		   });
	 ds_fkkglc.load();
	 
	 var grid_fkkglc = new Ext.grid.GridPanel({
		   // id:'fkkglcgrid',
		    region:'center',
			ds: ds_fkkglc,
			cm: cm_fkkglc,
			 border:false,
			//sm: cm_fkkglc,
			stripeRows: true
			//layout:'fit'
			//border:false
	 });
	 
	 
	 //-----------------------------------------------------------电能表方案
	var formd0=new Ext.Panel({
	anchor:'100% 16.5%',
	layout:'column',
	border:false,
	items:[ {
	        columnWidth:.33,
	  		layout:'form',
	  		border:false,
	  		labelAlign: "right",
	        
	        items:[{ 
	  		xtype:'textfield',
	  	    fieldLabel:'电能表变更方式',
	  	    labelWidth : 85,
	  	    name:'dnbbgfs',
	  		labelSeparator:'',
            anchor:'95%'
            }]
	        },{
	        columnWidth:.33,
	  		layout:'form',
	  		border:false,
	  		 bodyStyle : 'padding:0px 0px 0px 30px',
	        items:[{ 
	  		xtype:'button',
            text:'拆除电能表信息',
            width:100
            }]
	        }]
	});
	
    var formd1=new Ext.Panel({
	 anchor:'100% 16.5%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'电能表资产编号',
	  	     labelWidth : 85,
	  	     name:'dnbzcbh',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
	  		 },{
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'电能表编号',
	  	     name:'dnbbh',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             },{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'采集点编号',
	  	     name:'cjdbh',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             }]

	});
	
	var formd2=new Ext.Panel({
	 anchor:'100% 16.5%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'采集端口',
	  	     name:'cjdk',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
	  		 },{
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'CT变比',
	  	     name:'ctbb',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             },{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'PT变比',
	  	     name:'ptbb',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             }]

	});
	
	var formd3=new Ext.Panel({
	 anchor:'100% 16.5%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.28,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'表常数',
	  	     name:'bcs',
	  		 labelSeparator:'',
             anchor:'95%'
	  		 }]
	  		 },{
	  		 columnWidth:.05,
	  		 border:false,
	  		 layout:'form',
	  		 bodyStyle : 'padding:5px 0px 0px 0px',
	  		 items:[{
	  		 xtype:'label',
	  		 text:'CM'
	  		 }]
	  		 },{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'端口号',
	  	     name:'dkh',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             },{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'脉冲属性',
	  	     name:'mcsx',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             }]

	});
	
	var formd4 =new Ext.Panel({
	 anchor:'100% 16.5%',
	 layout:'column',
	 border:false,
	 items:[{
	         columnWidth:.28,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'反向接入',
	  	     name:'fxjr',
	  		 labelSeparator:'',
             anchor:'95%'
	  		 }]
	         },{
	  		 columnWidth:.05,
	  		 border:false,
	  		 layout:'form',
	  		 bodyStyle : 'padding:5px 0px 0px 0px',
	  		 items:[{
	  		 xtype:'label',
	  		 text:'CM'
	  		 }]
	  		 },{   
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'通讯方式',
	  	     name:'txfs',
	  		 labelSeparator:'',
             anchor:'95%'
	  		 }]
	  		 },{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'通讯地址1',
	  	     name:'txdz1',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             }]
	});
	
	var formd5 =new Ext.Panel({
	 anchor:'100% 16.5%',
	 layout:'column',
	 border:false,
	 items:[{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'通讯地址2',
	  	     name:'txdz2',
	  		 labelSeparator:'',
             anchor:'95%'
	  		 }]
	         },{   
	         columnWidth:.66,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'通讯规约',
	  	     name:'txgy',
	  		 labelSeparator:'',
             anchor:'97%'
	  		 }]
	  		 }]
	});
	
	 var formd = new Ext.FormPanel({
	   id:'dnbfamx',
       region:'south',
       layout:'anchor',
	   height:240,
       border:false,
	   bodyStyle : 'padding:30px 0px 0px 0px',
	   items:[formd0,formd1,formd2,formd3,formd4,formd5]  
    	
    });
     var datadnb = [
	               ['1098129','3123','12321','新增','2','3','3','1','23','2','32','a','we','w','广东'],
                   ['1098129','3123','12321','新增','2','3','3','1','23','2','32','a','we','w','新疆'],
                   ['1098129','3123','12321','新增','2','3','3','1','23','2','32','a','we','w','浙江']
	             ];
          
	 var cm_dnbfa = new Ext.grid.ColumnModel([
     //sm_dnbfa,
      /*new Ext.grid.RowNumberer({
	  header : '序号',
	  width : 35
      }),     */      
	   {header:'电能表资产编号',dataIndex:'dnbzcbh',sortable:true,resizable:true,align:'center'},
	   {header:'电能表编号',dataIndex:'dnbbh',sortable:true,resizable:true,align:'center'},
	   {header:'采集点编号',dataIndex:'cjdbh',sortable:true,resizable:true,align:'center'},
	   {header:'电能表变更方式',dataIndex:'dnbbgfs',sortable:true,resizable:true,align:'center'},
	   {header:'采集端口',dataIndex:'cjdk',hidden:true},
	   {header:'CT变比',dataIndex:'ctbb',hidden:true},
	   {header:'PT变比',dataIndex:'ptbb',hidden:true},
	   {header:'表常数',dataIndex:'bcs',hidden:true},
	   {header:'端口号',dataIndex:'dkh',hidden:true},
	   {header:'脉冲属性',dataIndex:'mcsx',hidden:true},
	   {header:'反向接入',dataIndex:'fxjr',hidden:true},
	   {header:'通讯方式',dataIndex:'txfs',hidden:true},
	   {header:'通讯地址1',dataIndex:'txdz1',hidden:true},
	   {header:'通讯地址2',dataIndex:'txdz2',hidden:true},
	   {header:'通讯规约',dataIndex:'txgy',hidden:true}
	 ]);
	
	 var ds_dnbfa= new Ext.data.Store({
		    proxy: new Ext.data.MemoryProxy(datadnb),
		    reader: new Ext.data.ArrayReader({}, [ 
		    {name: 'dnbzcbh'},
		    {name: 'dnbbh'},
		    {name: 'cjdbh'},
		    {name: 'dnbbgfs'},
		    {name: 'cjdk'},
		    {name: 'ctbb'},
		    {name: 'ptbb'},
		    {name: 'bcs'},
		    {name: 'dkh'},
		    {name: 'mcsx'},
		    {name: 'fxjr'},
		    {name: 'txfs'},
		    {name: 'txdz1'},
		    {name: 'txdz2'},
		    {name: 'txgy'}
		    
		    ])
		   });
	
	 
	 var grid_dnbfa = new Ext.grid.GridPanel({
		    region:'center',
			store: ds_dnbfa,
			cm: cm_dnbfa,
			sm: new Ext.grid.RowSelectionModel({
                    singleSelect: true,
                    listeners: {
                        rowselect: function(sm, row, rec) {
                            Ext.getCmp("dnbfamx").getForm().loadRecord(rec);
                        }
                    }
                }),
			stripeRows: true,
			layout:'fit'
			//border:false
	 });
	 ds_dnbfa.load();
	
    //-----------------------------------------------------------计量点方案
	var formj1=new Ext.Panel({
	 anchor:'100% 20%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'计量点编号',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
	  		 },{
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'用户编号',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             },{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'电表出厂号',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             }]

	});
	
	
	var formj2=new Ext.Panel({
	 anchor:'100% 20%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'计量点名称',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
	  		 },{
	        columnWidth:.66,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'计量点地址',
	  		 labelSeparator:'',
             anchor:'97.5%'
             }]
             }]

	});
	
	var formj3=new Ext.Panel({
	 anchor:'100% 20%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'电压等级',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
	  		 },{
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'接线方式',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             },{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'已装终端',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             }]

	});
	//----------------------------------------------------------------电源方案
    var formdy0=new Ext.Panel({
	 anchor:'100% 16.5%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'电源编号',
	  	     name:'dybh',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
	  		 },{
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'电源类型',
	  	     name:'dylx',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             },{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'电源相数',
	  	     name:'dyxs',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             }]

	});      
	       
	 var formdy1=new Ext.Panel({
	 anchor:'100% 16.5%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'变电站标识',
	  	     name:'bdzbs',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
	  		 },{
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'线路标识',
	  	     name:'xlbs',
	  	     labelSeparator:'',
             anchor:'95%'
             }]
             },{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'台区标识',
	  	     name:'tqbs',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             }]

	});            
	       
	 var formdy2=new Ext.Panel({
	 anchor:'100% 16.5%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'供电电压',
	  	     name:'gddy',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
	  		 },{
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'电源容量',
	  	     name:'dyrl',
	  	     labelSeparator:'',
             anchor:'95%'
             }]
             },{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'电源性质',
	  	     name:'dyxz',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             }]

	}); 
	
	 var formdy3=new Ext.Panel({
	 anchor:'100% 16.5%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'低压接线箱号',
	  	     name:'dyjxxh',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
	  		 }]

	}); 
	
	 var formdy = new Ext.FormPanel({
       id:'dyfamx',
       region:'south',
       layout:'anchor',
	   height:240,
       border:false,
	   bodyStyle : 'padding:30px 0px 0px 0px',
	   items:[formdy0,formdy1,formdy2,formdy3]  
    	
    });
     var datady = [
	               ['121','231','3','sad','2','35000','500','1','500','3','6','2','8','7'],
                   ['121','231','4','sad','2','35000','500','1','500','3','6','2','8','7']
	             ];
          
	 var cm_dyfa = new Ext.grid.ColumnModel([
     //sm_dyfa,
      /*new Ext.grid.RowNumberer({
	  header : '序号',
	  width : 35
      }),     */      
	   {header:'电源编号',dataIndex:'dybh',sortable:true,resizable:true,align:'center'},
	   {header:'电源类型',dataIndex:'dylx',sortable:true,resizable:true,align:'center'},
	   {header:'电源相数',dataIndex:'dyxs',sortable:true,resizable:true,align:'center'},
	   {header:'变电站标识',dataIndex:'bdzbs',sortable:true,resizable:true,align:'center'},
	   {header:'线路标识',dataIndex:'xlbs',hidden:true},
	   {header:'台区标识',dataIndex:'tqbs',hidden:true},
	   {header:'供电电压',dataIndex:'gddy',hidden:true},
	   {header:'电源容量',dataIndex:'dyrl',hidden:true},
	   {header:'电源性质',dataIndex:'dyxz',hidden:true},
	   {header:'低压接线箱号',dataIndex:'dyjxxh',hidden:true}
	 ]);
	
	 var ds_dyfa= new Ext.data.Store({
		    proxy: new Ext.data.MemoryProxy(datady),
		    reader: new Ext.data.ArrayReader({}, [
		    {name: 'dybh'},
		    {name: 'dylx'},
		    {name: 'dyxs'},
		    {name: 'bdzbs'},
		    {name: 'xlbs'},
		    {name: 'tqbs'},
		    {name: 'gddy'},
		    {name: 'dyrl'},
		    {name: 'dyxz'},
		    {name: 'dyjxxh'}])
		   });

	 var grid_dyfa = new Ext.grid.GridPanel({
		    region:'center',
			store: ds_dyfa,
			cm: cm_dyfa,
			sm: new Ext.grid.RowSelectionModel({
                singleSelect: true,
                listeners: {
                    rowselect: function(sm, row, rec) {
                        Ext.getCmp("dyfamx").getForm().loadRecord(rec);
                    }
                }
            }),
			stripeRows: true,
			layout:'fit'
			//border:false
	 });
	 ds_dyfa.load();
	//--------------------------------------------------------------受电点方案
	
	var forms1=new Ext.Panel({
	 anchor:'100% 20%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'受电点标识',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
	  		 },{
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'受电点名称',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             },{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'受电点类型',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             }]

	});
	
	
	var forms2=new Ext.Panel({
	 anchor:'100% 20%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'电源联锁方式',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
	  		 },{
	        columnWidth:.66,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'受电点备注',
	  		 labelSeparator:'',
             anchor:'97.5%'
             }]
             }]

	});
	
	var forms3=new Ext.Panel({
	 anchor:'100% 20%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'电源切换方式',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
	  		 },{
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 105,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'自闭电源闭锁方式',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             },{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'电源数目',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             }]

	});
	
	var forms4=new Ext.Panel({
	 anchor:'100% 20%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'有无自备电源',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
	  		 },{
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'自备电源容量',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             }]

	});
	
	//------------------------------------------------------------用户电价方案

	var formy1=new Ext.Panel({
	 anchor:'100% 20%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'变电点标识',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
	  		 },{
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'用户编号',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             },{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'功率因数标准',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             }]

	});
	
	
	var formy2=new Ext.Panel({
	 anchor:'100% 20%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'电价行业类别',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
	  		 },{
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'电价码',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             },{
	         columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 105,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'是否执行峰谷标志',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
             }]

	});
	
	var formy3=new Ext.Panel({
	 anchor:'100% 20%',
	 layout:'column',
	 border:false,
	 items:[ {
	        columnWidth:.33,
	  		 layout:'form',
	  		 border:false,
	  		 labelAlign: "right",
	         labelWidth : 80,
	  		 items:[{ 
	  		 xtype:'textfield',
	  	     fieldLabel:'固定力率',
	  		 labelSeparator:'',
             anchor:'95%'
             }]
	  		 }]

	});
	
	var tabs_xgfa = new Ext.TabPanel({
        activeTab: 0,
	    border:false,
	    items:[{
	                title: '采集点信息',
	                layout:'anchor',
	                height:200,
	                border:false,
	                bodyStyle : 'padding:30px 0px 0px 0px',
	                items:[formc1,formc2,formc3]  
	            },{
	                title: '终端方案',
	                layout:'border',
	                height:390,
	                items:[grid_zdfa,formz]

	            },{
	                title: '负控开关轮次',
	                layout:'fit',
	                height:300,
	                border:false,
	                items:[grid_fkkglc]
	            },{
	                title: '电能表方案',
	                layout:'border',
	                height:390,
	                items:[grid_dnbfa,formd]
	            },{
	                title: '计量点方案',
	                layout:'anchor',
	                height:200,
	                border:false,
	                bodyStyle : 'padding:30px 0px 0px 0px',
	                items:[formj1,formj2,formj3]  
	            },{
	                title: '电源方案',
	                layout:'border',
	                border:false,
	                height:390,
	                items:[grid_dyfa,formdy]
	            },{
	                title: '受电点方案',
	                layout:'anchor',
	                height:200,
	                border:false,
	                bodyStyle : 'padding:30px 0px 0px 0px',
	                items:[forms1,forms2,forms3,forms4]  
	            },{
	                title: '用户电价方案',
	                layout:'anchor',
	                height:200,
	                border:false,
	                bodyStyle : 'padding:30px 0px 0px 0px',
	                items:[formy1,formy2,formy3]  
	            }
	        ]
	       });
	
    
	
	 var tabs =new Ext.Panel({
	   border:false,
	   items:[tabs_xgfa]
	 });      
	       
	       
	//-----------------------------------------------------------相关方案窗口
	   this.xgfa = new Ext.Window({
		title:'相关方案',
		width:750,
		height:460,
		layout:'fit',
		closeAction:'hide',
		resizable:false,
		//minimizable:true,
		//maximizable:true,
		items:[tabs]
	});
	 
	/*var panel_xgfa=new Ext.Panel({
		layout:'fit',
		items:[tabs_xgfa]
	});*/
	
	
	 
	//---------------------------------------------------------------申请信息
	var data1 = [
	            ['10086999','118114','南瑞采集点','南京浦口开发区','关口采集点','1200210011','DZ300011','待装']
	             ];
	 //var sm_sqxx= new Ext.grid.CheckboxSelectionModel();
	             
	 var cm_sqxx = new Ext.grid.ColumnModel([
     //sm_sqxx,
    /*  new Ext.grid.RowNumberer({
	  header : '序号',
	  width : 35
      }), */                	
	   {header:'申请编号',dataIndex:'sqbh',sortable:true,resizable:true,align:'center'},
	   {header:'采集点编号',dataIndex:'cjdbh',sortable:true,resizable:true,align:'center'},
	   {header:'采集点名称',dataIndex:'cjdmc',sortable:true,resizable:true,align:'center'},
	   {header:'方案',dataIndex:'fa',sortable:true,resizable:true,width:70,align:'center',renderer:function(){return "<a href='javascript:' onclick='openWindow();'>相关方案</a>";}},
	   {header:'采集点地址',dataIndex:'cjddz',sortable:true,resizable:true,align:'center'},
	   {header:'采集点类别',dataIndex:'cjdlb',sortable:true,resizable:true,align:'center'},
	   {header:'用户编号',dataIndex:'yhbh',sortable:true,resizable:true,align:'center'},
	   //{header:'电能表出厂号',dataIndex:'dnbcch',sortable:true,resizable:true,align:'center'},
	   {header:'安装状态',dataIndex:'azzt',sortable:true,resizable:true,width:70,align:'center'}
	 ]);

	 
	 var ds_sqxx = new Ext.data.Store({
		    proxy: new Ext.data.MemoryProxy(data1),
		    reader: new Ext.data.ArrayReader({}, [
		    {name: 'sqbh'},
		    {name: 'cjdbh'},
		    {name: 'cjdmc'},
		    {name: 'cjddz'},
		    {name: 'cjdlb'},
		    {name: 'yhbh'},
		    {name: 'dnbcch'},
		    {name: 'azzt'},
		    {name: 'fa'}
		   ])
		   });
	 
	 
	 var grid_sqxx = new Ext.grid.GridPanel({
		    //id:'sqxxgrid',
		    //title:"计量点申请信息",
		    region:'center',
			store: ds_sqxx,
			cm: cm_sqxx,
			//sm:sm_sqxx,
			stripeRows: true,
			layout:'fit'
			//border:false
			/*bbar : new Ext.ux.MyToolbar( {
					store : ds_sqxx
			})*/
	 });
	 ds_sqxx.load();
	 
	 //-------------------------------------------------------------------明细
	  var MX= new Ext.FormPanel({
	  	id:'mx',
	  	//anchor:'100% 48%',
	  	region:'south',
	  	height:115,
	  	layout:'column',
	  	border:false,
	  	bodyStyle : 'padding:5px 5px 0px 5px',
	  	items:[{
	  		columnWidth:.25,
	  		layout:'form',
	  		labelAlign: "right",
	 	    labelWidth : 105,
	 	    border:false,
	  		items:[{
	  	     fieldLabel:'资产类别',
	  		 xtype:'combo',
	  		 name:'zclb',
	  		 labelSeparator:'',
             anchor:'100%'
	  		},{
	  	     fieldLabel:'终端型号',
	  		 xtype:'combo',
	  		 labelSeparator:'',
	  		 name:'zdxh',
             anchor:'100%'
	  		},{
	  	     fieldLabel:'是否可控',
	  		 xtype:'combo',
	  		  labelSeparator:'',
             anchor:'100%'
	  		},{
	  	     fieldLabel:'最大遥控路数',
	  		 xtype:'textfield',
	  		 labelSeparator:'',
             anchor:'100%'
	  		}]
	  	  },{
	  		columnWidth:.25,
	  		border:false,
	  		layout:'form',
	  		labelAlign: "right",
	 	    labelWidth : 105,
	  		items:[{
	  	     fieldLabel:'终端类型',
	  		 xtype:'combo',
	  		 name:'zdlx',
	  		 labelSeparator:'',
             anchor:'100%'
	  		},{
	  	     fieldLabel:'接线方式',
	  		 xtype:'combo',
	  		 name:'jxfs',
	  		 labelSeparator:'',
             anchor:'100%'
	  		},{
	  	     fieldLabel:'终端数量',
	  		 xtype:'textfield',
	  		 labelSeparator:'',
             anchor:'100%'
	  		},{
	  	     fieldLabel:'最大遥信路数',
	  		 xtype:'textfield',
	  		 labelSeparator:'',
             anchor:'100%'
	  		}]
	  	  },{
	  		columnWidth:.25,
	  		border:false,
	  		layout:'form',
	  		labelAlign: "right",
	  		labelWidth : 105,
	  		items:[{
	  	     fieldLabel:'采集方式',
	  		 xtype:'combo',
	  		 name:'cjfs',
	  		 labelSeparator:'',
             anchor:'100%'
	  		},{
	  	     fieldLabel:'交集终端电源类型',
	  		 xtype:'combo',
	  		 labelSeparator:'',
             anchor:'100%'
	  		},{
	  	     fieldLabel:'最大485接入数',
	  		 xtype:'textfield',
	  		 labelSeparator:'',
             anchor:'100%'
	  		},{
	  	     fieldLabel:'电压',
	  		 xtype:'textfield',
	  		 labelSeparator:'',
             anchor:'100%'
	  		}]
	  	  },{
	  		columnWidth:.25,
	  		border:false,
	  		layout:'form',
	  		labelAlign: "right",
	 	    labelWidth : 105,
	  		items:[{
	  	     fieldLabel:'是否用户终端',
	  		 xtype:'combo',
	  		 labelSeparator:'',
             anchor:'100%'
	  		},{
	  	     fieldLabel:'天线类型',
	  		 xtype:'combo',
	  		 labelSeparator:'',
             anchor:'100%'
	  		},{
	  	     fieldLabel:'最大脉冲路数',
	  		 xtype:'textfield',
	  		 labelSeparator:'',
             anchor:'100%'
	  		},{
	  	     fieldLabel:'电流',
	  		 xtype:'textfield',
	  		 labelSeparator:'',
             anchor:'100%'
	  		}]
	  	  }]

	  });
	 
    //-------------------------------------------------------采集点方案设计表格
	var data2 = [

	            ['118114','负控设备 ','大用户负荷管理终端','GPRS/SMS','BZ-A00021','三相三线','','AD20010001','新增']

	             ];
	 //var sm_cjdfasj= new Ext.grid.CheckboxSelectionModel();
	             
	 var cm_cjdfasj = new Ext.grid.ColumnModel([
     //sm_cjdfasj,
      /*  new Ext.grid.RowNumberer({
	  header : '序号',
	  width : 35
      }), */                	
	   {header:'采集点编号',dataIndex:'cjdbh',sortable:true,resizable:true,align:'center',width:80},
	   {header:'资产类别',dataIndex:'zclb',sortable:true,resizable:true,align:'center'},
	   {header:'终端类型',dataIndex:'zdlx',sortable:true,resizable:true,align:'center'},
	   {header:'采集方式',dataIndex:'cjfs',sortable:true,resizable:true,align:'center'},
	  // {header:'终端型号',dataIndex:'zdxh',sortable:true,resizable:true,align:'center'},
	   {header:'接线方式',dataIndex:'jxfs',sortable:true,resizable:true,align:'center'},
	   {header:'资产标识',dataIndex:'zcbs',sortable:true,resizable:true,align:'center'},
	   {header:'终端出厂编号',dataIndex:'zdccbh',sortable:true,resizable:true,align:'center'},
	   {header:'变更方式',dataIndex:'bgfs',sortable:true,resizable:true,width:70,align:'center'}
	 ]);
	
	 var ds_cjdfasj = new Ext.data.Store({
		    proxy: new Ext.data.MemoryProxy(data2),
		    reader: new Ext.data.ArrayReader({}, [
		    {name: 'cjdbh'},
		    {name: 'zclb'},
		    {name: 'zdlx'},
		    {name: 'cjfs'},
		    {name: 'zdxh'},
		    {name: 'jxfs'},
		    {name: 'zcbs'},
		    {name: 'zdccbh'},
		    {name: 'bgfs'}
		   ])
		   });
	 
	 var grid_cjdfasj = new Ext.grid.GridPanel({
		    //id:'cjfasjgrid',
           	//anchor:'100% 52%',
		    region:'center',
		    //border:false,
			store: ds_cjdfasj,
			cm: cm_cjdfasj,
			sm: new Ext.grid.RowSelectionModel({
                    singleSelect: true,
                    listeners: {
                        rowselect: function(sm, row, rec) {
                            Ext.getCmp("mx").getForm().loadRecord(rec);
                        }
                    }
                }),
			stripeRows: true,
			layout:'fit',
			tbar:[ {
		                 xtype:"button",
		                 text:"导出EXCEL"
	                     },
	                     '-',
	                    {
		                 xtype:"button",
		                 text:"导出PDF"
	                    },
	                    '-',
	                    {
		                 xtype:"button",
		                 text:"打印"
	                }]
			/*bbar : new Ext.ux.MyToolbar( {
					store : ds_cjdfasj
			})*/
	 });
	 
	 ds_cjdfasj.load();
	/* ds_cjdfasj.on('load',function(store,recs){
		grid_cjdfasj.getSelectionModel().selectRow(0);
		Ext.getCmp("mx").getForm().loadRecord(recs[0]);
	});*/

	 /*var grid_cjdfasjandB= new Ext.Panel({
	 	anchor:'100% 50%',
	 	layout:'anchor',
	    items:[grid_cjdfasj,
	    	{ 
	    		border:false,
	    	   xtype:'panel',
	           anchor:'100% 35%',
	           buttons:[
	 	 	{text:'导出EXCEL'},
	 	 	{text:'导出PDF'},
	 	 	{text:'打印'}]
	        }]
	 });*/
	 		
	 
	  
	 //--------------------------------------------------------------审查说明
	 var scsm =new  Ext.Panel({
	  layout:'form',
	  border:false,
	  labelAlign: "right",
	  labelWidth : 100,
	  bodyStyle : 'padding:5px 0px 0px 0px',
	  buttonAlign:'center',
	  items:[{
	  	     fieldLabel:'审查说明',
	  		 xtype:'textarea',
	  		 labelSeparator:'',
             anchor:'90%',
             height:50
	  		}],
	  buttons:[{
            text:'审核通过',
     	    width:100
            },{
            text:'审核不通过',
     	    width:100
             },{
          	text:'全部审核通过',
     	     width:100
           }]	 
     });

	  
	//------------------------------------------------------------------中间部分
	var cjdfasj = new Ext.Panel({
		region:'south',
		height:260,
		title:"采集点方案设计",
		split:true,
		collapsible:true,
		layout:'border',
		items:[grid_cjdfasj,MX]
	});
	
	
	//-------------------------------------------------------------上半部panel
    var panel2 = new Ext.Panel({
    	//title:"My Panel1",
    	layout:"border",
    	border:false,
    	region:"center",
    	items:[grid_sqxx,cjdfasj]
    	
    });
	
	//------------------------------------------------------------下半部panel
	var panel3 = new Ext.Panel({
		title:"采集点方案审查",
		height:125,
		split:true,
		region:"south",
		collapsible:true,
		//border:false,
		layout:'fit',
		items:[scsm]
        
	});
	
	var viewPanel = new Ext.Panel({
		layout: 'border',
		//minWidth : Ext.get('采集点设计方案审查').getWidth(),
		//height : Ext.get('采集点设计方案审查').getHeight(),
		items: [panel2,panel3],
		border : false
	});
	renderModel(viewPanel, '采集点设计方案审查');
	
});