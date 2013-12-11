<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
//.destroy();
tabpanel = Ext.getCmp('maintab').remove(Ext.getCmp("操作监测"));
//点击菜单打开页面的方法
function openTabT(title, url, tabpanel, iconCls) {

var p=	openTabBT({
				title : title,
				url : url,
				tabpanel : tabpanel,
				iconCls : iconCls
			});
return p;
}

function openTabBT(config) {
	var tabpanel = config.tabpanel;
	if (!tabpanel)
		tabpanel = Ext.getCmp('maintab');

	var tab = tabpanel.getComponent(config.title);
	if (tab) {
		tabpanel.setActiveTab(tab);
		return tab;
	} else {
		var p = new Ext.Panel({
					title : config.title,
					closable : true,
					isOpenTree : config.isOpenTree,
					iconCls : config.iconCls,
					// autoHeight : true,
					layout : 'fit',
					id : config.title,
					autoLoad : {
						url : config.url,
						nocache : true,
						text : 'Loading...',
						scripts : true,
						scope : p
					}
				});
		tabpanel.add(p);
		tabpanel.setActiveTab(p);
		p.on('activate', function(p) {
					if (p.isOpenTree == '0')
						Ext.getCmp('mainwest').collapse();
					else
						Ext.getCmp('mainwest').expand();

				});

		return p;
	}
}

var pp=openTabT("日志查询", "./runMan/dutyLog/logQuery.jsp", false,"icoqry");
pp&&pp.setTitle("操作监测");

</script>
