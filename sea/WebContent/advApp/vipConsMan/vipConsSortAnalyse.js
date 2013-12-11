var vcSortAnalyse_gridCm;
var vcSortAnalyse_ArrayCm = new Array();

// 开始日期
var vcSortAnalyse_startDate = new Ext.form.DateField( {
		fieldLabel : '从',
		name : 'vcSortAnalyse_startDate',
		labelStyle : "text-align:right;",
		format : 'Y-m-d',
		editable : false,
		labelSeparator : '',
		value : new Date().add(Date.MONTH, -1),
		allowBlank : false,
		width : 100
});

// 结束日期
var vcSortAnalyse_endDate = new Ext.form.DateField( {
		fieldLabel : '到',
		name : 'vcSortAnalyse_endDate',
		labelStyle : "text-align:right;",
		format : 'Y-m-d',
		labelSeparator : '',
		editable : false,
		value : new Date().add(Date.DAY, -1),
		allowBlank : false,
		width : 100
});	

//定义分析类型
var vcSortAnalyse_RG = new Ext.form.RadioGroup({
    width : 400,
    height :20,
    items : [new Ext.form.Radio({
	    boxLabel : '按负荷最大',
	    name : 'vcSortAnalyse-radioGroup',
	    inputValue : '1',
	    checked: true
    }), new Ext.form.Radio({
	    boxLabel : '按负荷最小',
	    name : 'vcSortAnalyse-radioGroup',
	    inputValue : '2'
    }), new Ext.form.Radio({
	    boxLabel : '按电能量最大',
	    name : 'vcSortAnalyse-radioGroup',
	    inputValue : '3'
    }), new Ext.form.Radio({
	    boxLabel : '按电能量最小',
	    name : 'vcSortAnalyse-radioGroup',
	    inputValue : '4'
    })]
});

//查询按钮
var vcSortAnalyse_queryBtn = new Ext.Button({
		text : '查询',
		name : 'vcSortAnalyse_queryBtn',
		width : 80,
	    labelSeparator:'',
	    handler:function(){
	        vcSortAnalyse_gridStore.removeAll();
	        generateVcSortAnalyseGridCm(vcSortAnalyse_RG.getValue().getRawValue());
	        vcSortAnalyse_gridStore.baseParams = {
   	            queryDate:vcSortAnalyse_startDate.getValue(),
   	            endDate:vcSortAnalyse_endDate.getValue(),
   	            flag:vcSortAnalyse_RG.getValue().getRawValue()
   	        };
	        vcSortAnalyse_gridStore.load();
	        vcSortAnalyse_gridPanel.reconfigure(vcSortAnalyse_gridStore,vcSortAnalyse_gridCm);
        }
});

//grid的cm初始化
generateVcSortAnalyseGridCm(1);

//定义Grid的store
var vcSortAnalyse_gridStore = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : './advapp/vipconsman/vipConsPowerSortAnalyse!queryGridData.action'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'sortList',
			totalProperty : 'totalCount',
			idProperty : 'sort'
		}, [ {
			name : 'sort'
		}, {
			name : 'orgNo'
		}, {
			name : 'orgName'
		}, {
			name : 'consNo'
		}, {
			name : 'consName'
		}, {
			name : 'maxP'
		}, {
			name : 'maxPTime'
		}, {
			name : 'minP'
		}, {
			name : 'minPTime'
		}, {
			name : 'maxPapE'
		}, {
			name : 'minPapE'
		} ]),
		sortInfo : {
			field : 'sort',
			direction : 'ASC'
		}
});

//查询条件面板
var vcSortAnalyse_northPanel=new Ext.Panel({
	border:false,
	region:'north',
	height:40,
	layout:'table',
	layoutConfig : {
		columns :4
    },
    defaults: {height: 35},
	items:[{
            border : false,
            layout:'form',
            width:150,
            labelWidth : 20,
  	        bodyStyle : 'padding:10px 0px 0px 5px',
	        items:[vcSortAnalyse_startDate]
		},{
            border : false,
            layout:'form',
            width:150,
            labelWidth : 20,
  	        bodyStyle : 'padding:10px 0px 0px 0px',
	        items:[vcSortAnalyse_endDate]
		},{
            border : false,
            layout:'form',
            width:400,
            hideLabels:true,
  	        bodyStyle : 'padding:10px 0px 0px 0px',
	        items:[vcSortAnalyse_RG]
		},{
            border : false,
            layout:'form',
            width:100,
  	        bodyStyle : 'padding:10px 0px 0px 0px',
	        items:[vcSortAnalyse_queryBtn]
		}]
});

//grid面板
var vcSortAnalyse_gridPanel = new Ext.grid.GridPanel( {
		region : 'center',
		autoScroll : true,
		stripeRows : true,
		viewConfig : {
			forceFit : false
		},
		cm : vcSortAnalyse_gridCm,
		store : vcSortAnalyse_gridStore,
		bbar : new Ext.ux.MyToolbar( {
			enableExpAll : true,
			store : vcSortAnalyse_gridStore
		}),
		tbar : [ {
			xtype : 'label',
			html : "<font font-weight:bold;>重点户排名分析</font>"
		} ]
});

//动态生成cm
function generateVcSortAnalyseGridCm(n){
	if(!Ext.isEmpty(vcSortAnalyse_gridCm)){
		vcSortAnalyse_ArrayCm.length = 0;
	}
	vcSortAnalyse_ArrayCm.push({
		header : '排名',
		dataIndex : 'sort',
		sortable : true,
		width : 50,
		align : 'center'
	});
	vcSortAnalyse_ArrayCm.push({
		header : '供电单位',
		dataIndex : 'orgName',
		width : 120,
		sortable : true,
		align : 'center',
		renderer : function(val) {
			if (Ext.isEmpty(val)) {
				val = "";
			}
			var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="'
					+ val + '">' + val + '</div>';
			return html;
		}
	});
	vcSortAnalyse_ArrayCm.push({
		header : '用户编号',
		dataIndex : 'consNo',
		width : 120,
		sortable : true,
		align : 'center'
	});
	vcSortAnalyse_ArrayCm.push({
		header : '用户名称',
		dataIndex : 'consName',
		sortable : true,
		width : 160,
		align : 'center',
		renderer : function(val) {
			if (Ext.isEmpty(val)) {
				val = "";
			}
			var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="'
					+ val + '">' + val + '</div>';
			return html;
		}
	});
	if(1 == n){
		vcSortAnalyse_ArrayCm.push({
			header : '最大负荷',
			dataIndex : 'maxP',
			sortable : true,
			width : 100,
			align : 'center'
		});
		vcSortAnalyse_ArrayCm.push({
			header : '最大负荷发生时间',
			dataIndex : 'maxPTime',
			sortable : true,
			width : 120,
			align : 'center'
		});
	}else if(2 == n){
		vcSortAnalyse_ArrayCm.push({
			header : '最小负荷',
			dataIndex : 'minP',
			sortable : true,
			width : 100,
			align : 'center'
		});
		vcSortAnalyse_ArrayCm.push({
			header : '最小负荷发生时间',
			dataIndex : 'minPTime',
			sortable : true,
			width : 120,
			align : 'center'
		});
	}else if(3 == n){
		vcSortAnalyse_ArrayCm.push({
			header : '最大用电量',
			dataIndex : 'maxPapE',
			sortable : true,
			width : 100,
			align : 'center'
		});
	}else if(4 == n){
		vcSortAnalyse_ArrayCm.push({
			header : '最小用电量',
			dataIndex : 'minPapE',
			sortable : true,
			width : 100,
			align : 'center'
		});
	}else{
		
	}
	vcSortAnalyse_gridCm = new Ext.grid.ColumnModel(vcSortAnalyse_ArrayCm);
}