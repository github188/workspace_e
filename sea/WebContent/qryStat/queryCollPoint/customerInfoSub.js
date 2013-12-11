function getcustMessage() {
	Ext.getCmp("getConsNo").setValue(consNoforCCust);
	var cust_no = consIdforTerminal;
	Ext.Ajax.request({
				url : 'qrystat/ccustAction!queryCust.action',
				params : {
					custNo : cust_no
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					ctspanel.getForm().setValues(result.custList[0]);
					addStr = '';
					deleteStr = '';
				}
			});
};
    
    
    
    function getcontactMessage() {
        var cust_no = consIdforTerminal;
         CcontactStore.baseParams = {
            custNo : cust_no
        };
        CcontactStore.load({
                    params : {
                        start : 0,
                        limit : DEFAULT_PAGE_SIZE
                    }
                });
    };
//客户资料的第一个panel
	var ctpanel1 = new Ext.Panel({
		//height : 40,
		layout : "column",
		baseCls : "x-plain",
		style : "padding:5px",
		items : [{
					columnWidth : .3,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "用户编号",
                                id:'getConsNo',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 120
							}]
				}, {
					columnWidth : .4,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "用户名称",
                                name:'name',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 202
							}]
				}, {
					columnWidth : .3,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "产业分类",
                                name:'industryCode',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							}]
				}]
	});
	
//客户资料的第二个panel
	var ctpanel2 = new Ext.Panel({
		//height : 40,
		layout : "column",
		baseCls : "x-plain",
		style : "padding:5px",
		items : [{
					columnWidth : .3,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "经济类型",
                                name:'economyTypeCode',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 120
							},{
								fieldLabel : "信用等级",
                                name:'creditLevelCode',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 120
							}]
				}, {
					columnWidth : .25,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "年生产总值",
                                name:'annualGp',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							},{
								fieldLabel : "价值等级",
                                name:'valueLevelCode',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 80
							}]
				},{
					columnWidth : .15,
					layout : "form",
					labelWidth : 50,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "VIP等级",
                                name:'vipFlag',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 40
							},{
								fieldLabel : "风险等级",
                                name:'riskLevelCode',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 40
							}]
				},
				
				{
					columnWidth : .3,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "注册资本金",
                                name:'regCapital',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width :80
							},{
								fieldLabel : "资金总额",
                                name:'tCaptal',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width :80
							}]
				}]
	});
	
//针对企业简介的panel
	var ctpanel3 = new Ext.Panel({
		//height : 60,
		labelWidth : 80,
		layout : "form",
		baseCls : "x-plain",
		style : "padding:5px",
		items:[{xtype:"textarea",fieldLabel:"企业简介",name:'brief',labelStyle : "text-align:right;width:80;",width:620}]
	});
//panel4	
	var ctpanel4 = new Ext.Panel({
		//height : 30,
		layout : "column",
		baseCls : "x-plain",
		style : "padding-left:5px",
		items : [{
					columnWidth : .4,
					layout : "form",
					labelWidth : 80,
					baseCls : "x-plain",
					items : [{
								xtype : "textfield",
								labelStyle : "text-align:right;width:80;",
                               name:'entepriseWebsite',
								fieldLabel : "企业网址",
								width : 220
							}]
				}, {
					columnWidth : .6,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "法人代表",
                                name:'legalPerson',
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 282
							}]
				}]
	});
	
// 客户联系信息linkmanGrid-----------------------------------------------------------------------------------
	   var CcontactStore = new Ext.data.Store({
                // proxy : new Ext.data.MemoryProxy(),
                proxy : new Ext.data.HttpProxy({
                          url : 'qrystat/ccustAction!querycontact.action'
                        }),
                reader : new Ext.data.JsonReader({
                            root : 'contactList',
                            totalProperty : 'totalCount'
                        }, [
//                            {
//                                    name : 'contactNum'
//                                }, 
                                    {
                                    name : 'contactName'
                                }, {
                                    name : 'gender'
                                }, {
                                    name : 'deptNo'
                                }, {
                                    name : 'title'
                                }, {
                                    name : 'officeTel'
                                }, {
                                    name : 'mobile'
                                }, {
                                    name : 'homephone'
                                }, {
                                    name : 'contactMode'
                                }, {
                                    name : 'contactPrio'
                                }, {
                                    name : 'addr'
                                }, {
                                    name : 'contactRemark'
                                }])
            });
    var contactNum = 1;
    var ctlinkmanGrid = new Ext.grid.GridPanel({
		 title:'客户联系信息',
		// renderTo:Ext.getBody(),
		 height:200,
		// width:200,
		  stripeRows: true,
		//autoHeight : true,
		autoScroll:true,
		enableColumnMove : false,// 设置不可表头不可拖动
		colModel : new Ext.grid.ColumnModel([{
						header : "序号",
						dataIndex : 'contactNum',
						menuDisabled : true,//显示菜单
						align : "center",
						renderer : function() {
							return contactNum++;
						}
					},// 使下拉框消失
					{
						header : "联系人",
						dataIndex : 'contactName',
						menuDisabled : true,
						align : "center"
					},// 设置标签文字居中
					{
						header : "性别",
						dataIndex : 'gender',
						menuDisabled : true,
						align : "center"
					}, {
						header : "部门编号",
						dataIndex : 'deptNo',
						menuDisabled : true,
						align : "center"
					}, {
						header : "职务/职称",
						dataIndex : 'title',
						menuDisabled : true,
						align : "center"
					}, {
						header : "办公电话",
						dataIndex : 'officeTel',
						menuDisabled : true,
						align : "center"
					}, {
						header : "移动电话",
						dataIndex : 'mobile',
						menuDisabled : true,
						align : "center"
					}, {
						header : "住宅电话",
						dataIndex : 'homephone',
						menuDisabled : true,
						align : "center"
					}, {
						header : "联系类型",
						dataIndex : 'contactMode',
						menuDisabled : true,
						align : "center"
					}, {
						header : "联系优先级",
						dataIndex : 'contactPrio',
						menuDisabled : true,
						align : "center"
					}, {
						header : "联系地址",
						dataIndex : 'addr',
						menuDisabled : true,
						align : "center"
					}, {
						header : "联系备注",
						dataIndex : 'contactRemark',
						menuDisabled : true,
						align : "center"
					}]),
		ds : CcontactStore,
        bbar : new Ext.ux.MyToolbar({
						store : CcontactStore
					})
		});
// --------------------------
	var ctspanel = new Ext.FormPanel({
		//title:"客户资料",
		items:[ctpanel1,ctpanel2,ctpanel3,ctpanel4]
	});
	var customerInfoWindow = new Ext.Window({
		//id:customerInfoWindow,
		title:"客户资料",
		draggable:false,//窗体不可移动
		frame:true,
		modal:true,
		width:800,
		height:450,
		layout:'form',
		//labelWidth:45,
		plain:true,//设置背景颜色
		resizable:false,//不可移动
		bodyStyle:"padding:2px",
		buttonAlign:"center",//按钮的位置
		//closable:false,//设置窗体关闭按钮
		closeAction:'close',//将窗体隐藏而并不销毁
		items:[ctspanel,ctlinkmanGrid],
		buttons : [{
				text : '关闭',    //窗口隐藏，设置关闭有误?
				width : 60,
				handler : function() {
					customerInfoWindow.hide();
				}
			}]
	});
function customerInfoWindowShow(){
	customerInfoWindow.show();
        getcustMessage();
    getcontactMessage();
     
};