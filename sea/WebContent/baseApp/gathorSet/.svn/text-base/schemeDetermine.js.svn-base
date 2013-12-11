	Ext.onReady(function(){
		
	    	//采集点方案列表
	    var data1 = [
		             ['南京供电公司','123456','0897','300kwh','浦口','主站召测','未知','未知','新增'],
		             ['无锡供电公司','123456','0897','300kwh','未知','主站召测','未知','未知','新增'],
		             ['扬州供电公司','123456','0897','300kwh','未知','终端上送','未知','未知','新增'],
		             ['苏州供电公司','123456','0897','300kwh','未知','终端上送','未知','未知','新增']
		             ];
	          
		var cm_cjdlist = new Ext.grid.ColumnModel([                	
		   {header:'供电单位',dataIndex:'gddw',sortable:true,resizable:true,align:'center'},
		   {header:'传单编号',dataIndex:'cdbh',sortable:true,resizable:true,align:'center'},
		   {header:'用户编号',dataIndex:'yhbh',sortable:true,resizable:true,align:'center'},
		   {header:'设备类别',dataIndex:'sblb',sortable:true,resizable:true,align:'center'},
		   {header:'采集对象',dataIndex:'cjdx',sortable:true,resizable:true,align:'center'},
		   {header:'用户基本信息',dataIndex:'yhjbxx',sortable:true,resizable:true,align:'center'},
		   {header:'采集点设置信息',dataIndex:'cjdszxx',sortable:true,resizable:true,align:'center'},
		   {header:'采集点审查信息',dataIndex:'cjdscxx',sortable:true,resizable:true,align:'center'},
		   {header:'采集点勘察信息',dataIndex:'cjdkcxx',sortable:true,resizable:true,align:'center'}
		 ]);
		
		 var ds_cjdlist = new Ext.data.Store({
			    proxy: new Ext.data.MemoryProxy(data1),
			    reader: new Ext.data.ArrayReader({}, [
			    {name: 'gddw'},
			    {name: 'cdbh'},
			    {name: 'yhbh'},
			    {name: 'sblb'},
			    {name: 'cjdx'},
			    {name: 'yhjbxx'},
			    {name: 'cjdszxx'},
			    {name: 'cjdscxx'},
			    {name: 'cjdkcxx'}
			   ])
			   });
		 ds_cjdlist.load();
		 
	//采集点方案表格	 
	 var cjdlist = new Ext.grid.GridPanel({
	 	    region:'center',
	 	    title:'采集点方案',
	 	    layout:'fit',
		    //id:'cjdlist',
		    //border:false,
			ds: ds_cjdlist,
			cm: cm_cjdlist,
			stripeRows: true
			/*bbar : new Ext.ux.MyToolbar( {
					store : ds_cjdlist
			})*/
	 });
	 
	 //采集设备参数表格
	     var data2 = [
	
			            ['电压','500v','未知','未知']
	
			             ];
		    var sm_cjsbcs= new Ext.grid.CheckboxSelectionModel();        
			var cm_cjsbcs = new Ext.grid.ColumnModel([
		       sm_cjsbcs,                	
			   {header:'参数项名称',dataIndex:'csxmc',sortable:true,resizable:true,align:'center'},
			   {header:'参数项明细',dataIndex:'csxmx',sortable:true,resizable:true,align:'center'},
			   {header:'参数项内容',dataIndex:'csxnr',sortable:true,resizable:true,align:'center'},
			   {header:'参数项单位',dataIndex:'csxdw',sortable:true,resizable:true,align:'center'}
	
			 ]);
			
			 var ds_cjsbcs = new Ext.data.Store({
				    proxy: new Ext.data.MemoryProxy(data2),
				    reader: new Ext.data.ArrayReader({}, [
				    {name: 'csxmc'},
				    {name: 'csxmx'},
				    {name: 'csxnr'},
				    {name: 'csxdw'}
				   ])
				   });
		 ds_cjsbcs.load();
			 
		 var grid_cjsbcs = new Ext.grid.GridPanel({
		 	    region:'center',
		        // layout:'fit',
			    //id:'gridcjsbcs',
			    //border:false,
				ds: ds_cjsbcs,
				cm: cm_cjsbcs,
				sm: sm_cjsbcs,
				stripeRows: true
				/*bbar : new Ext.ux.MyToolbar( {
						store : ds_cjsbcs
				})*/
		 });
/*	var tree = new Ext.ux.tree.ColumnTree({
        width: 500,
        height: 200,
        rootVisible:false,
        autoScroll:true,
        columns:[{
            header:'参数名称',
            width:330,
            dataIndex:'csmc'
        },{
            header:'参数内容',
            width:100,
            dataIndex:'csnr'
        },{
            header:'单位',
            width:100,
            dataIndex:'dw'
        }],

        loader: new Ext.tree.TreeLoader({
            dataUrl:'./baseApp/gathorSet/data_cjsbcs.json',
            uiProviders:{
                'col': Ext.ux.tree.ColumnNodeUI
            }
        }),

        root: new Ext.tree.AsyncTreeNode({
           text:'Tasks'
        })
    });*/
		 
	 //增删改按钮
		var  zsg = new Ext.Panel({
			//columnWidth:.3,
			region:'east',
			width:200,
			border:false,
			layout:'form',
			//buttonAlign:'center',
	        //bodyStyle : 'padding:10px 10px 10px 30px',
			//frame:true,
			items:[{
				border:false,
				bodyStyle : 'padding:5px 0px 5px 30px',
				items:[{
				xtype:'button',
				text:'增加',
				width:100
				}]
			},{
				border:false,
				bodyStyle : 'padding:5px 0px 5px 30px',
				items:[{
				xtype:'button',
				text:'修改',
				width:100
				}]
			},{
				border:false,
				bodyStyle : 'padding:5px 0px 0px 30px',
				items:[{
				xtype:'button',
				text:'删除',
				width:100
				}]
			}]
			
		});
	
	var cldcs1 =new Ext.Panel({
		region:'north',
		border:false,
		height:35,
		layout:'column',
	    bodyStyle : 'padding:5px 0px 0px 10px',
		items:[{
		    columnWidth:0.15,
		    border:false,
		    items:[{
		    xtype:'button',
		   // width:100,
		    text:'增加参数项'
		    }]
		},{
		    columnWidth:0.3,
		    border:false,
		    items:[{
		    xtype:'button',
		    //width:100,
		    text:'删除参数项'
		    }]
		},{
		    columnWidth:0.25,
		    border:false,
		    layout:'form',
	  	    border:false,
	  		labelAlign: 'right',
	        labelWidth : 80,
		    items:[{
		    xtype:'combo',
		    fieldLabel:'测量点',
	  	    name:'cld',
	  		labelSeparator:'',
            anchor:'95%'
		    }]
		},{
		    columnWidth:0.15,
		    border:false,
		    items:[{
		    xtype:'button',
		    //width:100,
		    text:'增加 测量点'
		    }]
		},{
		    columnWidth:0.15,
		    border:false,
		    items:[{
		    xtype:'button',
		    //width:100,
		    text:'删除测量点'
		    }]
		}]
		 
	});
	
	//测量点参数
	var sm_cldcs=new Ext.grid.CheckboxSelectionModel();
	var cm_cldcs = new Ext.grid.ColumnModel(
	  [    sm_cldcs,            	
		   {header:'参数名称',dataIndex:'csmc',sortable:true,resizable:true,align:'center'},
		   {header:'参数内容',dataIndex:'csnr',sortable:true,resizable:true,align:'center'},
		   {header:'单位',dataIndex:'dw',sortable:true,resizable:true,align:'center'}

		 ]);
		
		 var ds_cldcs = new Ext.data.Store({
			    proxy: new Ext.data.MemoryProxy(data1),
			    reader: new Ext.data.ArrayReader({}, [
			    {name: 'csmc'},
			    {name: 'csnr'},
			    {name: 'dw'}
			   ])
			   });
		 ds_cldcs.load();
		 
	
	var cldcs2=new Ext.grid.GridPanel({
		region:'center',
		layout:'fit',
		ds: ds_cldcs,
		cm: cm_cldcs,
		sm: sm_cldcs,
		stripeRows: true,
		ViewConfig:{
			forceFit:true
		}
	   
	});
	
	var cjrwpz1 = new Ext.Panel({
		//height:25,
		anchor:'100% 15%',
		layout:'column',
		border:false,
	    items:[
				 {
				     columnWidth:.2,
				     border:false,
				     bodyStyle : 'padding:0px 0px 0px 7px',
					 items:[{
			  		 xtype:'label',
			  		 text:'任务编号'
		  		 }]
				}, {
				     columnWidth:.2,
				     border:false,
				     bodyStyle : 'padding:0px 0px 0px 7px',
					 items:[{
			  		 xtype:'label',
			  		 text:'测量点号'
		  		 }]
				}, {
				     columnWidth:.6,
				     border:false,
				     bodyStyle : 'padding:0px 0px 0px 7px',
					 items:[{
			  		 xtype:'label',
			  		 text:'任务名称'
		  		 }]
				}]
	});
	
	var cjrwpz2 = new Ext.Panel({
        //height:30,
        anchor:'100% 18%',
		layout:'column',
		border:false,
	    items:[
				 {
				     columnWidth:.2,
				     layout:'form',
				     border:false,
				     labelWidth: 1,
					 items:[{
			  		 xtype:'textfield',
			  		 anchor:'70%'
		  		 }]
				},{
				     columnWidth:.2,
				     layout:'form',
				     border:false,
				     labelWidth: 1,
					 items:[{
			  		 xtype:'textfield',
			  		 anchor:'70%'
		  		 }]
				},{
				     columnWidth:.4,
				     layout:'form',
				     labelWidth: 1,
				     border:false,
					 items:[{
			  		 xtype:'combo',
			  		 anchor:'100%'
		  		 }]
				},{
				     columnWidth:.1,
				     border:false,
				     bodyStyle : 'padding:0px 0px 0px 30px',
					 items:[{
			  		 xtype:'button',
			  		 width:40,
			  		 text:'修改'
		  		 }]
				},{
				     columnWidth:.1,
				     border:false,
					 items:[{
			  		 xtype:'button',
			  		 width:40,
			  		 text:'详细'
		  		 }]
				}]
	});
	
	var cjrwpz3 = new Ext.Panel({
        //height:30,
		anchor:'100% 18%',
		layout:'column',
		border:false,
	    items:[
				 {
				     columnWidth:.2,
				     layout:'form',
				     border:false,
				     labelWidth: 1,
					 items:[{
			  		 xtype:'textfield',
			  		 anchor:'70%'
		  		 }]
				},{
				     columnWidth:.2,
				     layout:'form',
				     border:false,
				     labelWidth: 1,
					 items:[{
			  		 xtype:'textfield',
			  		 anchor:'70%'
		  		 }]
				},{
				     columnWidth:.4,
				     layout:'form',
				     labelWidth: 1,
				     border:false,
					 items:[{
			  		 xtype:'combo',
			  		 anchor:'100%'
		  		 }]
				},{
				     columnWidth:.1,
				     border:false,
				     bodyStyle : 'padding:0px 0px 0px 30px',
					 items:[{
			  		 xtype:'button',
			  		 width:40,
			  		 text:'修改'
		  		 }]
				},{
				     columnWidth:.1,
				     border:false,
					 items:[{
			  		 xtype:'button',
			  		 width:40,
			  		 text:'详细'
		  		 }]
				}]
	});
	
	var cjrwpz4 = new Ext.Panel({
        //height:30,
		anchor:'100% 18%',
		layout:'column',
		border:false,
	    items:[
				 {
				     columnWidth:.2,
				     layout:'form',
				     border:false,
				     labelWidth: 1,
					 items:[{
			  		 xtype:'textfield',
			  		 anchor:'70%'
		  		 }]
				},{
				     columnWidth:.2,
				     layout:'form',
				     border:false,
				     labelWidth: 1,
					 items:[{
			  		 xtype:'textfield',
			  		 anchor:'70%'
		  		 }]
				},{
				     columnWidth:.4,
				     layout:'form',
				     labelWidth: 1,
				     border:false,
					 items:[{
			  		 xtype:'combo',
			  		 anchor:'100%'
		  		 }]
				},{
				     columnWidth:.1,
				     border:false,
				     bodyStyle : 'padding:0px 0px 0px 30px',
					 items:[{
			  		 xtype:'button',
			  		 width:40,
			  		 text:'修改'
		  		 }]
				},{
				     columnWidth:.1,
				     border:false,
					 items:[{
			  		 xtype:'button',
			  		 width:40,
			  		 text:'详细'
		  		 }]
				}]
	});
	
	var cm_zdazfa = new Ext.grid.ColumnModel([
		       //sm_zdazfa,                	
			   {header:'采集点编号',dataIndex:'cjdbh',sortable:true,resizable:true,align:'center'},
			   {header:'资产类别',dataIndex:'zclb',sortable:true,resizable:true,align:'center'},
			   {header:'终端类型',dataIndex:'zdlx',sortable:true,resizable:true,align:'center'},
			   {header:'采集方式',dataIndex:'cjfs',sortable:true,resizable:true,align:'center'},
	           {header:'终端规约',dataIndex:'zdgy',sortable:true,resizable:true,align:'center'},
	           {header:'任务上送方式',dataIndex:'rwssfs',sortable:true,resizable:true,align:'center',
	           editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               transform:'light',
               lazyRender: true
               //listClass: 'x-combo-list-small'
               })
			   }, 
	           {header:'终端资产编号',dataIndex:'zdzcbh',sortable:true,resizable:true,align:'center'},
	           {header:'电能表资产编号',dataIndex:'dnbzcbh',sortable:true,resizable:true,align:'center'},
	           {header:'安装方式',dataIndex:'azfs',sortable:true,resizable:true,align:'center'}
			 ]);
			
	var ds_zdazfa  = new Ext.data.Store({
		url: './baseApp/gathorSet/data_zdazfa.xml',
		reader: new Ext.data.XmlReader(
			{record: 'plant'},
			[
				{name:'cjdbh',type:'string'},
				{name:'zclb',type:'string'},
				{name:'zdlx',type:'string'},
				{name:'cjfs',type:'string'},
				{name:'zdgy',type:'string'},
				{name:'rwssfs'},
				{name:'zdzcbh',type:'string'},
				{name:'dnbzcbh',type:'string'},
				{name:'azfs',type:'string'}
			])
	});
			
			 
		 var grid_zdazfa = new Ext.grid.EditorGridPanel({
		        layout:'fit',
			    //id:'gridzdazfa',
			    //border:false,
				store: ds_zdazfa,
				cm: cm_zdazfa,
				stripeRows: true
				//sm: sm_zdazfa
				//height:85,
		 });
	 ds_zdazfa.load();
	
	
	
	//采集点参数任务tabs
	 var cjdtabs= new Ext.TabPanel({
		        activeTab: 1,
		        //margins:'0 0 20 0',
		        items:[{
		        	   border:false,
		                title: '终端安装方案',
		                layout:'fit',
		                items:[grid_zdazfa],
		                buttons:[
	    	            {text:'保存'}
	    	            ] 
		            },{
		            	border:false,
		            	//bodyStyle : 'padding:5px 5px 5px 5px',
		                title: '采集设备参数',
		                layout:'border',
		                items:[grid_cjsbcs,zsg]
		            },{
		                title: '测量点参数',
		                border:false,
		                layout:'border',
		                items:[cldcs1,cldcs2],
		                buttons:[
	    	           {text:'保存'},
	    	           {text:'退出'}
	    	            ]
		            },{
		                title: '采集任务配置',
		                border:false,
		                layout:'anchor',
		                bodyStyle : 'padding:10px 0px 0px 10px',
		                items:[cjrwpz1,cjrwpz2,cjrwpz3,cjrwpz4],
		                buttonAlign:'center',
		                buttons:[
	    	           {
	    	            text:'保存'
	    	            },
	    	           {
	    	           text:'退出'}
	    	            ]
		               }]
		           
		       });
		       
	var  cjdcsrw1=new Ext.Panel({
		    title:"采集点参数和任务",
			border:false,
		 	layout:'fit',
			items:[cjdtabs],
		 	buttons:[{
		     	text:'退回审查',
		     	width:100
		     },{
	
		     	text:'退回勘察',
		     	width:100
		     },{
		     	text:'确定',
		     	width:100
		     },{
	
		     	text:'启动装接',
		     	width:100
		     },{
	
		     	text:'打印',
		     	width:100
		     }]
		
		
	});
	
	   //下半部panel
	    var panel = new Ext.Panel({
		 layout:"fit",
		 region:"south",
		 //border:false,
		 split:true,
		 height:300,
	     items:[cjdcsrw1]
	});

		var viewPanel = new Ext.Panel({
			layout: 'border',
			//minWidth : Ext.get('安装方案确定').getWidth(),
			//height : Ext.get('安装方案确定').getHeight(),
			items: [cjdlist,panel],
			border : false
		});
		renderModel(viewPanel, '安装方案确定');
	});
