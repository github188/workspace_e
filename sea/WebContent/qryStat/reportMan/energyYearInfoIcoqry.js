//采集年报表查询

	//供电单位label
	var orgLabel = new Ext.form.TextField({
        fieldLabel:'供电单位',
        readOnly:true,
        labelSeparator:'',
        allowBlank:false,
        width:180
	});

	var queryButt=new Ext.Button({
		width:50,
		text : '查询'
	});
	var yearReportQryPanel = new Ext.Panel({
		title:'采集年报表查询',
		region:'center',
		height:30,
		layout:'table',
		border:false,
		defaults:{height:30},
		layoutConfig:{columns:2},
		items:[{
		    border : false,
		    width :700,
	  		layout:'form',
			labelWidth : 55,
	 	    bodyStyle : 'padding:5px 0px 0px 20px',
	 	    items:[orgLabel]
        },{
		    border : false,
		    width :70,
	  		layout:'form',
	 	    bodyStyle : 'padding:5px 0px 0px 0px',
	 	    items:[queryButt]
		}]
	});

	var yearReportInfoPanel = new Ext.Panel({
		border:false,
		region:'center',
		layout:'border',
		height:300,
		items:[yearReportQryPanel]
	});