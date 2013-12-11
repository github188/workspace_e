
var t1 = new Ext.tree.TreePanel({

	border : false,
	rootVisible :false,
	root : new Ext.tree.AsyncTreeNode({
		text : "",
		expanded : true,
		children :[
		{id : "department", text : "部门管理", leaf :true},
		{id : "company", text : "公司管理", leaf :true},
		{id : "permissions", text : "权限管理", 
		 children : [{id: "permission", text : "权限管理", leaf : true},{id : "permissionType", text : "权限类别", leaf : true}]
		}]
	})
});

var t2 = new Ext.tree.TreePanel({});


var leftMenu = new Morik.Office.LeftMenu({
	border :  "north",
	title : "用电采集系统",
	height : "80",
	trees: [t1, t2]
});

Morik.Office.LeftMenu = function()
{	
	var d = Ext.apply({
	split : true,
	region : "west",
	defaults : {boeder : false},
	layoutConfig : {animate : true}},config || {});
	config = Ext.apply(d,{layout : 'accordion', collapsiblle : true});
	Morik.Office.LeftMenu.superclass.constructor.call(this, config||{});
	for(var i=0; i<this.trees.length; i++)
		this.add({
			title: this.trees[i].getRootNode().text,
			items : [this.trees[i]]
		});
	
}
Ext.extend(Morik.Office.LeftMenu, Ext.Panel,{

	initTreeEvent : function(){
	
	if(!this.items) return;
	for(var i = 0; i < this.items.length; i++){
		var p = this.items.itemAt(i);
		if(p) var t = p.items.itemAt(0);
		if(t) t.on({
			'click' : function(node, event){
				if(node && node.isLeaf()){
					event.stopEvent();
					this.fireEvent('nodeClick', node.attributes);},this}
			});
		}
	}
});



var mainpanel = new Ext.Panel({
			region : "center",
			html : '<div>主内容部分</div>'
		});
		
		
Ext.onReady(function() {
			// 设置顶层的panel
			new Ext.Viewport({
						layout : "border",
						items : [leftMenu, mainpanel]
					});
		});		
		
		
		