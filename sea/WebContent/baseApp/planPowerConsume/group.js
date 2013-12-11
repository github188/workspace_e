Ext.namespace("Ext.ux");
panel_contrl=new Ext.Panel({
		region:'north',
		height:30,
		layout : 'column',
		// frame:true,
		border : false,
		bodyStyle : 'padding:10px 0px 0px 75px',
		items : [{
					border : false,
					columnWidth : .4,
					items : [{
								xtype : 'radio',
								name : 'groupType',
								boxLabel : '普通群组',
								checked : true
							}]
				}, {
					border : false,
					columnWidth : .3,
					items : [{
								xtype : 'radio',
								name : 'groupType',
								boxLabel : '控制群组',
								listeners : {
									check : function(checkbox, checked) {
										if (checked) {
											panel_mx.layout.setActiveItem(1);
										} else {
											panel_mx.layout.setActiveItem(0);
										}
									}
								}
							}]
				}]
	});
	
//------------------------------控制类别
	
	var data_groupType=[
	   ['001','错峰群组'],
       ['002','避峰群组'],
       ['003','负控限电群组']
	];
	var ds_groupType=new Ext.data.SimpleStore({
		fields : ['groupTypeCode', 'groupType'],
		data:data_groupType
	});
	//ds_groupType.load();
    var ctrlGroupName1=new Ext.form.TextField({
        fieldLabel:'群组名称',
        readOnly:true,
        width:100
    });
    var ctrlGroupName2=new Ext.form.TextField({
        width:186,
        emptyText : '--请填写群组名称--'
    });
	var comb_groupType=new Ext.form.ComboBox({
	     store:ds_groupType,
		 displayField : 'groupType',
		 valueField : 'groupTypeCode',
		 typeAhead : true,
		 mode:'local',
		 forceSelection : true,
		 triggerAction : 'all',
		 fieldLabel:'群组类型',
		 emptyText : '--请选择群组类型--',
		 selectOnFocus : true,
		 width:290
	});
	var ds_ctrlScheme = new Ext.data.JsonStore({
		url:'baseapp/groupSet!queryCtrlSchemeType1.action',
		fields : [ 'ctrlSchemeNo', 'ctrlSchemeType'],
		root :'ctrlSchemeTypeIdList'
	});
	ds_ctrlScheme.load();
	var comb_ctrlSchemeType1 =new Ext.form.ComboBox({
		 store:ds_ctrlScheme,
		 displayField : 'ctrlSchemeType',
		 valueField : 'ctrlSchemeNo',
		 typeAhead : true,
		 mode : 'remote',
		 forceSelection : true,
		 triggerAction : 'all',
		 fieldLabel:'控制类别',
		 emptyText : '--请选择控制类别--',
		 blankText : '请选择控制类别',
		 selectOnFocus : true,
		 allowBlank:false,
		 width:290
	});
	comb_ctrlSchemeType1.on('select',function(combox){
		if(combox!=null||combox.getRawValue()!='')
			ctrlGroupName1.setValue(combox.getRawValue());  
	});
    
   
    var startDate2=new Ext.form.DateField({
        fieldLabel:'有效日期',
        width:120,
        format:'Y-m-d',
        value:new Date()
    });
    var finishDate2=new Ext.form.DateField({
        fieldLabel:'至',
        width:120,
        format:'Y-m-d',
        value:new Date().add(Date.DAY,30)
    });
    var isShare2=new Ext.form.Checkbox({
        fieldLabel:'是否共享'
    });
	var panel_contrlGroup=new Ext.Panel({
	    layout:'anchor',
	    border:false,
	    bodyStyle : 'padding:10px 0px 0px 10px',
	    items:[{
	    	anchor:'100% 20%',
	    	border:false,
			layout:'form',
			labelSeparator:' ',
			labelAlign:'right',
			labelWidth:60,
	    	items:[comb_groupType]
	    },{
	    	anchor:'100% 20%',
	    	border:false,
			layout:'form',
			labelSeparator:' ',
			labelAlign:'right',
			labelWidth:60,
	    	items:[comb_ctrlSchemeType1]
		},{
		    anchor:'100% 20%',
	    	border:false,
	    	layout:'column',
			items:[{
				columnWidth:.45,
				layout:'form',
				border:false,
				labelSeparator:' ',
				labelAlign:'right',
				labelWidth:60,
				items:[ctrlGroupName1]
			},{
				columnWidth:.55,
				layout:'form',
				border:false,
				hideLabels:true,
				items:[ctrlGroupName2]
			}]
		},{
			anchor:'100% 20%',
			border:false,
			layout:'column',
			items:[{
				border:false,
				columnWidth:.53,
				layout:'form',
				labelSeparator:' ',
				labelAlign:'right',
				labelWidth:60,
				items:[startDate2]
			},{
				border:false,
				columnWidth:.47,
				layout:'form',
				labelSeparator:' ',
				labelAlign:'right',
				labelWidth:32,
				items:[finishDate2]
			}]
		},{
			anchor:'100% 20%',
	    	border:false,
			layout:'column',
			items:[{
				columnWidth:.48,
				layout:'form',
			    border:false,
			    labelSeparator:' ',
			    labelAlign:'right',
			    labelWidth:60,
			    items:[isShare2]
			},{
				columnWidth:.25,
				buttonAlign:'right',
				border:false,
				items:[{
					xtype:'button',
					text:'确认',
					handler:addGroup
				}]
			},{
				columnWidth:.25,
				buttonAlign:'right',
				border:false,
				items:[{
					xtype:'button',
					text:'退出',
					handler:function(){
						addGroupWindow.hide(); 
					}
				}]
			}]
		}]
	});
	
	function addGroup(){
		var recs = sm_addGroupUserCM.getSelections();
 	    if(null==recs||recs.length==0){
 	    	Ext.MessageBox.alert("提示","请选择用户！");
 	    	return;
        }
 	    if(comb_ctrlSchemeType1.getRawValue()==""){
 	    	Ext.MessageBox.alert("提示","请选择控制类别！");
 	    	return;
 	    }
     	if(ctrlGroupName2.getValue()==""){
 	    	Ext.MessageBox.alert("提示","请填写群组名称！");
 	    	return;
     	 }  
     	 var startDate=startDate2.getValue();
     	 var finishDate=finishDate2.getValue();
 	     if(startDate==''||finishDate==''){
 	    	Ext.MessageBox.alert("提示","请选择起止日期！");
 	    	return;	
 	     }
 	    if(startDate>finishDate){
 	        Ext.MessageBox.alert("提示","开始日期不能大于结束日期！");
 	    	return;
 	     }
 	   // var orgNos=recs[0].get('orgNo');
 	    var consNos=recs[0].get('consNo');
 		var tmnlAssetNos=recs[0].get('tmnlAssetNo');
 	    for(var i = 1; i < recs.length; i++){	 
 		  // orgNos = orgNos +','+ recs[i].get('orgNo');
		   consNos = consNos +','+ recs[i].get('consNo');
		   tmnlAssetNos = tmnlAssetNos +','+ recs[i].get('tmnlAssetNo');
         }
 	    var isShare;
        if(isShare2.getValue()==true)
           isShare='1';
        else
           isShare='0';
        var validDays =parseInt((finishDate.getTime()-startDate.getTime())/(1000*24*3600)); 
        Ext.Ajax.request({
            url:'baseapp/groupSet!addCtrlGroup.action',
 	    	params:{
        	 groupName:ctrlGroupName1.getValue()+'-'+ctrlGroupName2.getValue(),  
        	 //orgNos:orgNos,
        	 isShare:isShare,
        	 startDate:startDate,
    		 validDays:validDays,
    		 consNos:consNos,
    		 tmnlAssetNos:tmnlAssetNos,
    		 ctrlSchemeType:comb_ctrlSchemeType1.getValue(),
    		 groupTypeCode:comb_groupType.getValue()
 	    	},
 	    	success : function(response){
     	    		var result = Ext.decode(response.responseText);
     	    		var flag= result.FLAG;
     	    		if(result.FLAG==0)
     	    		   Ext.MessageBox.alert("提示","群组名称已被使用！");
     	    		else if(result.FLAG==1){
                       addGroupWindow.hide(); 
     	    		   Ext.MessageBox.alert("提示","成功！");
                    }
     	    	}
        });
	};
	
    var commonGroupName=new Ext.form.TextField({
        fieldLabel:'群组名称',
        emptyText : '--请填写群组名称--',
        width:290
    });
    var startDate1=new Ext.form.DateField({
        fieldLabel:'有效日期',
        width:120,
        format:'Y-m-d',
        value:new Date()
    });
    var finishDate1=new Ext.form.DateField({
        fieldLabel:'至',
        width:120,
        format:'Y-m-d',
        value:new Date().add(Date.DAY,30)
    });
    var isShare1=new Ext.form.Checkbox({
        fieldLabel:'是否共享'
    });

	var panel_commonGroup=new Ext.Panel({
		layout:'anchor',
	    border:false,
	    bodyStyle : 'padding:10px 0px 0px 10px',
	    items:[{
	    	anchor:'100% 25%',
	    	border:false,
			layout:'form',
			labelSeparator:' ',
			labelAlign:'right',
			labelWidth:60,
	    	items:[commonGroupName]
	    },{
	        anchor:'100% 25%',
			border:false,
			layout:'column',
			items:[{
				border:false,
				columnWidth:.53,
				layout:'form',
				labelSeparator:' ',
				labelAlign:'right',
				labelWidth:60,
				items:[startDate1]
			},{
				border:false,
				columnWidth:.47,
				layout:'form',
				labelSeparator:' ',
				labelAlign:'right',
				labelWidth:32,
				items:[finishDate1]
			}]
	    },{
			anchor:'100% 25%',
	    	border:false,
			layout:'column',
			items:[{
				columnWidth:.48,
				layout:'form',
			    border:false,
			    labelSeparator:' ',
			    labelAlign:'right',
			    labelWidth:60,
			    items:[isShare1]
			},{
				columnWidth:.25,
				buttonAlign:'right',
				border:false,
				items:[{
					xtype:'button',
					text:'确认',
					handler:function(){
						var recs = sm_addGroupUserCM.getSelections();
	  		     	    if(null==recs||recs.length==0){
	  		     	    	Ext.MessageBox.alert("提示","请选择用户！");
	  		     	    	return;
	     	            }
	     	            if(commonGroupName.getValue()=="")
	  		     	    {
	  		     	    	Ext.MessageBox.alert("提示","请填写群组名称！");
	  		     	    	return;
	  		     	    }   
	  		     	    var startDate=startDate1.getValue();
	  		     	    var finishDate=finishDate1.getValue();
	  		     	    if(startDate==''||finishDate==''){
	  		     	    	Ext.MessageBox.alert("提示","请选择起止日期！");
	  		     	    	return;	
	  		     	    }
	  		     	    if(startDate>finishDate)
	  		     	    {
	  		     	        Ext.MessageBox.alert("提示","开始日期不能大于结束日期！");
	  		     	    	return;
	  		     	    }
	  		     		var consNos=recs[0].get('consNo');
	  		     		var tmnlAssetNos=recs[0].get('tmnlAssetNo');
	  		     	    for(var i = 1; i < recs.length; i++){	  		    
						    consNos = consNos +','+ recs[i].get('consNo');
						    tmnlAssetNos = tmnlAssetNos +','+ recs[i].get('tmnlAssetNo');
                         }
                        var isShare;
                        if(isShare1.getValue()==true)
                            isShare='1';
                        else
                            isShare='0';
                        var validDays =parseInt((finishDate.getTime()-startDate.getTime())/(1000*24*3600)); 
	  		     	    Ext.Ajax.request({
	  		     	    	url:'baseapp/groupSet!addCommonGroup.action',
	  		     	    	params:{
	  		     	    		groupName:commonGroupName.getValue(),
	  		     	    		isShare:isShare,
	  		     	    		startDate:startDate,
	  		     	    		validDays:validDays,
	  		     	    		consNos:consNos,
	  		     	    		tmnlAssetNos:tmnlAssetNos
	  		     	    	},
	  		     	    	success : function(response){
	  		     	    		var result = Ext.decode(response.responseText);
	  		     	    		var flag= result.FLAG;
	  		     	    		if(result.FLAG==0)
	  		     	    		   Ext.MessageBox.alert("提示","群组名称已被使用！");
	  		     	    		else if(result.FLAG==1){
                                   addGroupWindow.hide();
	  		     	    		   Ext.MessageBox.alert("提示","成功！");
                                }
	  		     	    	}
	  		     	    });
					}
				}]
				
			},{
				columnWidth:.25,
				buttonAlign:'right',
				border:false,
				items:[{
					xtype:'button',
					text:'退出',
					handler:function(){
					  addGroupWindow.hide();
					}
				}]
			}]
	    }]
	});
	
	var panel_mx = new  Ext.Panel({
	    region:'center',
	    border:false,
	    layout : 'card',
		activeItem : 0,
	    items:[panel_commonGroup,panel_contrlGroup]
	    
	});
	var addGroupWindow = new Ext.Window({
		title:'新增群组',
		name:'addGroupWindow',
		resizable : false,
        modal:true,
		//frame:true,
		width:400,
		height:250,
		closeAction : 'hide',
		//buttonAlign:'center',
		layout:'border',
		items:[panel_contrl,panel_mx]
	});