/**
 * 监视主页
 */

//var configUrl = './mydesk/config/config.xml';
var setToolWindow = new Ext.Window({
	title:'模块管理',
	width:420,
	border:false,
	autoHeight:true,
	resizable  :false,
	collapsible:true,
	modal:true,
	closeAction:'hide',
	items:[],
	buttonAlign:'center',
	buttons:[{
		text:'关闭',
		handler:function(){
			setToolWindow.hide();
		}
	}]
});

function createcustomSetTool(){
	var panel =new Ext.Panel({
		xtype:"panel",
		frame:true,
		width:400,
		autoHeight:true,
		layout:'fit',
		items:new Ext.DataView({
			store:config.store,
			tpl:config.tpl,
			autoHeight:10,
			multiSelect: true,
			overClass:'x-view-over',
			itemSelector:'div.thumb-wrap',
			 emptyText: 'No images to display',
			 listeners:{
			 	'beforeclick':function(dt,index,node,e){
			 		if(e.getTarget().type=="checkbox"){
			 			config.selectItem(dt,node);
			 			return false;
			 		}
			 	},
			 	'click':function(dt,index,node,e){
			 		var id =dt.getRecord(node).get('id');
			 		var selectBox=document.getElementById("mc_"+id);
			 		selectBox.checked=!selectBox.checked;
			 		config.selectItem(dt,node);
			 	}
			 },
			 prepareData:function(data){
			 	data.name = Ext.util.Format.ellipsis(data.name, 15);
			 	data.img = data.img;
			 	return data;
			 }
		})
	});
	
	setToolWindow.insert(0,panel);
}

var portletModel = Ext.data.Record.create([
		{name:'name'},
		{name:'id'},
		{name:'path'},
		{name:'image'}
	]);

config = {
	model : [[1, 2], [3, 4], [5, 6]],
	store:new Ext.data.Store({
		autoLoad : true,
		url:'mydesk/mydesk!queryModel.action',
		reader:new Ext.data.JsonReader({
			root:'modelList',
			idProperty:'id'
		},portletModel)
	}),
//	store : new Ext.data.Store({
//				autoLoad : true,
//				proxy : new Ext.data.HttpProxy({
//							url : configUrl,
//							method : 'GET'
//						}),
//				reader : new Ext.data.XmlReader({
//							record : 'model',
//							id : "@id"
//						}, [{
//									name : 'name',
//									mapping : '@name'
//								}, {
//									name : 'path',
//									mapping : '@path'
//								}, {
//									name : 'id',
//									mapping : '@id'
//								}, {
//									name : 'img',
//									mapping : '@img'
//								}])
//			}),
			
	tpl : new Ext.XTemplate(
			'<tpl for=".">',
			'<div class="thumb-wrap" id="{name}"  style="width:170px;float: left;margin: 4px;margin-right: 0;padding: 5px;">',
			'<span class="x-editable" style="text-align: right; padding: 0px;margin: 0px;" ><input   type="checkBox"  id= "mc_{id}" /></span>',
			'{name}',
			'</div>',
			'</tpl>', '<div class="x-clear"></div>'),		
			
	selectItem:function(dt,node){
		var id =dt.getRecord(node).get('id');
		var name =dt.getRecord(node).get('name');
		var path =dt.getRecord(node).get('path');
		var selectBox=document.getElementById("mc_"+id);
		if(selectBox.checked){
			config.addPortlet(null,id);
		}else{
			config.delPortlet(id);
		}
		Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		Ext.state.Manager.set('model',config.getModel());
	},	
				
	/**
	 * 根据配置文件，初始化model
	 */		
	initModel : function() {
		var model = new Array();
		model[0] = new Array();
		model[1] = new Array();
		model[2] = new Array();
		config.store.on('load',function(store){
			var i = 0;
			store.each(function(dt){
				model[i%3][Math.floor(i / 3)] = dt.get('id');
				i++;
				
			});
		});
		config.model = model;
	},
	
	setModel:function(model){
		
		Ext.each(model,function(obj,i){
			if(obj != ''){//判断PortalColumn的items是否为空,
				Ext.each(obj,function(o,j){
					config.addPortlet(i,o);
				})
			}
		})
	},
	
	getModel:function(){
		var model = new Array();
		config.portal.items.each(function(item,i,le){
			model[i] = new Array();
			item.items.each(function(it,j,l){
				model[i][j] = it.getId();
			});
		});
		return model;
	},
	
	
	/**
	 * 初始化portal
	 */
	portal:new Ext.ux.Portal({
		id:'mainportal',
		xtype:'portal',
		region:'center',
		border:false,
		items:[{
			id:'pCol0',
			columnWidth:.33,
			style:'padding:10px 0 10px 10px'
		},{
			id:'pCol1',
			columnWidth:.33,
			style:'padding:10px 0 10px 10px'
		},{
			id:'pCol2',
			columnWidth:.33,
			style:'padding:10px 0 10px 10px'
		}],
		listeners:{
			'drop':function(e){
				Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
           		Ext.state.Manager.set('model',config.getModel());
			}
		}
	}),
	
	tools:[
//		{
//		id:'gear',
//        handler: function(){
//            Ext.Msg.alert('Message', 'The Settings tool was clicked.');
//        }
//	},
		{
		 id:'close',
        handler: function(e, target, panel){
            panel.ownerCt.remove(panel, true);
            Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
            Ext.state.Manager.set('model',config.getModel());
        }
	}],
	
	getPortlet:function(id){
		return config.portal.findById(id);
	},
	
	delPortlet:function(id){
		var portlet = config.portal.findById(id);
		if(portlet){
			var portalCol = portlet.ownerCt;
			portalCol.remove(portlet,true);
			portalCol.doLayout();
		}
	},
	
	//
	addPortlet:function(cid , pid){
		var record = config.store.getById(pid);
		var ptitle = record.get('name');
		var path = record.get("path");
		var portlet = config.portal.findById(pid);
		var portalcol ;
		//
		if(cid == null){////判断是否未指定父容器中
			if(portlet == null){//如果当前portlet不存在,刚加入到第一个
				portalcol = config.portal.findById("pCol0");
			}else{
				portalcol = portlet.ownerCt;
			}
		}else{
			portalcol = config.portal.findById("pCol" +cid)
		}
		
		if(portlet == null){
			portlet = new Ext.ux.Portlet({
				id:pid,
				height:320,
				title:ptitle,
				layout:'fit',
				tools:config.tools,
				autoLoad:{
					url:path,
					nocache : true,
					text : 'Loading...',
					scripts : true,
					scope:portlet
				}
			});
		}
		portalcol.add(portlet);
		portlet.show();
		portalcol.doLayout();
	}	
}

render = {
	render:function(){
		
		Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		var sys_model = Ext.state.Manager.get('model');
		var portal = config.portal;
		config.initModel();
		
		if(typeof(sys_model) == 'undefined'){			
			setTimeout(function(){
				config.setModel(config.model);
			},800);	
		}else{
			config.store.on("load",function(){config.setModel(sys_model);})
		}
		
		createcustomSetTool();
		
		var mainPanel = new Ext.Panel({
			border:false,
			layout:'border',
			items:[{
				border:false,
				region:'north',
				title:'<a href="javascript:modeManager();">模块管理</a>'
//				items:[{
//					xtype:'button',
//					text:'模块管理',
//					handler:function(){
//						
//						setToolWindow.show();
//						checkSelectItem();
//						
//					}
//				}]
//			},portal]
			},portal]
		});
		
		renderModel(mainPanel, '统计主页');
	}
}

function modeManager(){
	setToolWindow.show();
	checkSelectItem();
}

function checkSelectItem(){
	var model = config.model;
	Ext.each(model,function(obj,i){
		if(obj != null){
			Ext.each(obj,function(o,j){
				var selectBox=document.getElementById("mc_"+model[i][j]);
				var obj = config.getPortlet(model[i][j]);
				if(selectBox){
					if(obj != null && obj.isVisible()){
						selectBox.checked=true;
					}else{
						selectBox.checked=false;
					}
				}
				Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
				Ext.state.Manager.set('model',config.getModel());
			});
		}
	});	
}


Ext.onReady(function() {
	render.render();
});
