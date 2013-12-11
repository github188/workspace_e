/*!
 * Author: Dolphin.Li
 * Date:11/27/2009
 * Description: 采集器档案form实体
 * Update History: none
 */
Ext.ns('Ext.CollectArchivesForm'); 

//Ext.apply(this,Ext.ArchivesForm.ArchivesFormPubNl);
Ext.apply(this,{
	
	 	CollectorArchives:'采集器档案',
	    CoArBranchNo:'采集器局号',
	    CoArDeptNo:'单位编码',
	    CoArTerminalBranchNo:'终端局号',
	    CoArUserNo:'用户编号',
	    CoArModel:'采集器型号',
	    CoArOutDate:'出场日期',
	    CoArOutNo:'出场编号',
	    CoArManufacturerNo:'厂家编码',
	    CoArStorageDate:'入库日期',
	    CoArInstalledDate:'安装日期',
	    CoArAddress:'通讯地址',
	    CoArAddressMounted:'安装地址',
	    CoArType:'采集器类型',
	    CoArRunState:'运行状态',
	    CoArCommunicationState:'通讯状态',
	    CoArCommunicationPort:'通讯端口号'	
});

Ext.CollectArchivesForm.CollectorArchivesFormItems ={
	labelWidth: 100, 
    frame:false,
    title:this.CollectorArchives,
    bodyStyle:'padding:5px 5px 0px 5px',
    autoWidth: true,
    border: false,
    items:[{//定义Form第1行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border: false,
		items: [{
			columnWidth:.5,
			layout:'form',
			border: false,
        	items: [       	    
        	    new Ext.form.ComboBox({      
        	    	fieldLabel: this.CoArBranchNo,      
        	    	store: new Ext.data.ArrayStore({  
        	    		fields: ['provincie']
        	    	}),            
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	//emptyText: this.ComboBoxEmptyTextValue+this.CoArBranchNo,
        	    	//invalidText:this.CoArBranchNo+this.InvalidTextValue,
        	    	//blankText: this.CoArBranchNo+this.BlankTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
        },{
        	columnWidth:.5,
    		layout:'form',
    		border: false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.CoArDeptNo,  			
    			name: 'CoArDeptNo',  			
    			allowBlank:false,
    			anchor:'90%'
    			//blankText:this.CoArDeptNo+this.BlankTextValue,
    			//invalidText :this.CoArDeptNo+this.InvalidTextVlalue,   			
    			//emptyText:this.TextFieldEmptyTextValue+this.CoArDeptNo   
    		}]
        }]
    },{//定义第2行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border: false,
    	items: [{
    		columnWidth:.5,
    		layout:'form',
    		border: false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.CoArTerminalBranchNo,  			
    			name: 'CoArTerminalBranchNo',  			
    			allowBlank:false,
    			anchor:'90%'
    			//blankText:this.CoArTerminalBranchNo+this.BlankTextValue,
    			//invalidText :this.CoArTerminalBranchNo+this.InvalidTextVlalue,   			
    			//emptyText:this.TextFieldEmptyTextValue+this.CoArTerminalBranchNo   
    		}] 		
        },{
        	columnWidth:.5,
    		layout:'form',
    		border: false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.CoArUserNo,  			
    			name: 'CoArUserNo',  			
    			allowBlank:false,
    			anchor:'90%'
    			//blankText:this.CoArUserNo+this.BlankTextValue,
    			//invalidText :this.CoArUserNo+this.InvalidTextValue,   			
    			//emptyText:this.TextFieldEmptyTextValue+this.CoArUserNo  
    		}]
        }]
    },{//定义第3行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border: false,
    	items: [{
    		columnWidth:.5,
    		layout:'form',
    		border: false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.CoArModel,  			
    			name: 'CoArModel',  			
    			allowBlank:false,
    			anchor:'90%'
    			//blankText:this.CoArModel+this.BlankTextValue,
    			//invalidText :this.CoArModel+this.InvalidTextVlalue,   			
    			//emptyText:this.TextFieldEmptyTextValue+this.CoArModel   
    		}] 		
        },{
        	columnWidth:.5,
        	layout:'form',
        	border: false,
        	items: [
    	        new Ext.form.DateField({//定义一个日期控件
    	        	fieldLabel: this.CoArOutDate,
    	        	name: 'CoArOutDate',
    	        	showToday:true,
    	        	format:'Y-m-d',
    	        	anchor:'90%'
    	        	//emptyText:this.DateFieldEmptyTextValue,
    	        	//blankText: this.CoArOutDate+this.BlankTextValue,
    	        	//invalidText: this.CoArOutDate+this.InvalidTextValue        	        	
        	   })
            ]
        }]
    },{//定义第4行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border: false,
    	items: [{
    		columnWidth:.5,
    		layout:'form',
    		border: false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.CoArOutNo,  			
    			name: 'CoArOutNo',  			
    			allowBlank:false,
    			anchor:'90%'
    			//blankText:this.CoArOutNo+this.BlankTextValue,
    			//invalidText :this.CoArOutNo+this.InvalidTextVlalue,   			
    			//emptyText:this.TextFieldEmptyTextValue+this.CoArOutNo   
    		}] 		
        },{
        	columnWidth:.5,
    		layout:'form',
    		border: false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.CoArManufacturerNo,  			
    			name: 'CoArManufacturerNo',  			
    			allowBlank:false,
    			anchor:'90%'
    			//blankText:this.CoArManufacturerNo+this.BlankTextValue,
    			//invalidText :this.CoArManufacturerNo+this.InvalidTextValue,   			
    			//emptyText:this.TextFieldEmptyTextValue+this.CoArManufacturerNo  
    		}]
        }]
    },{//定义第5行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border: false,
    	items: [{
    		columnWidth:.5,
        	layout:'form',
        	border: false,
        	items: [
    	        new Ext.form.DateField({//定义一个日期控件
    	        	fieldLabel: this.CoArStorageDate,
    	        	name: 'CoArStorageDate',
    	        	showToday:true,
    	        	format:'Y-m-d',
    	        	anchor:'90%'
    	        	//emptyText:this.DateFieldEmptyTextValue,
    	        	//blankText: this.CoArStorageDate+this.BlankTextValue,
    	        	//invalidText: this.CoArStorageDate+this.InvalidTextValue        	        	
        	   })
            ]	
        },{
        	columnWidth:.5,
        	layout:'form',
        	border: false,
        	items: [
    	        new Ext.form.DateField({//定义一个日期控件
    	        	fieldLabel: this.CoArInstalledDate,
    	        	name: 'CoArInstalledDate',
    	        	showToday:true,
    	        	format:'Y-m-d',
    	        	anchor:'90%'
    	        	//emptyText:this.DateFieldEmptyTextValue,
    	        	//blankText: this.CoArInstalledDate+this.BlankTextValue,
    	        	//invalidText: this.CoArInstalledDate+this.InvalidTextValue        	        	
        	   })
            ]
        }]
    },{//定义第6行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border: false,
    	items: [{
    		columnWidth:.5,
    		layout:'form',
    		border: false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.CoArAddress,  			
    			name: 'CoArAddress',  			
    			allowBlank:false,
    			anchor:'90%'
    			//blankText:this.CoArAddress+this.BlankTextValue,
    			//invalidText :this.CoArAddress+this.InvalidTextVlalue,   			
    			//emptyText:this.TextFieldEmptyTextValue+this.CoArAddress   
    		}] 		
        },{
        	columnWidth:.5,
    		layout:'form',
    		border: false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.CoArAddressMounted,  			
    			name: 'CoArAddressMounted',  			
    			allowBlank:false,
    			anchor:'90%'
    			//blankText:this.CoArAddressMounted+this.BlankTextValue,
    			//invalidText :this.CoArAddressMounted+this.InvalidTextValue,   			
    			//emptyText:this.TextFieldEmptyTextValue+this.CoArAddressMounted  
    		}]
        }]
    },{//定义Form第7行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border: false,
		items: [{
			columnWidth:.5,
			layout:'form',
			border: false,
        	items: [       	    
        	    new Ext.form.ComboBox({      
        	    	fieldLabel: this.CoArType,      
        	    	store: new Ext.data.ArrayStore({  
        	    		fields: ['provincie']
        	    	}),            
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	//emptyText: this.ComboBoxEmptyTextValue+this.CoArType,
        	    	//invalidText:this.CoArType+this.InvalidTextValue,
        	    	//blankText: this.CoArType+this.BlankTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
        },{
        	columnWidth:.5,
        	layout:'form',
        	border: false,
        	items: [
        	    new Ext.form.ComboBox({
        	    	fieldLabel: this.CoArRunState,
        	    	hiddenName: 'AmArMainStipulation',
        	    	store: new Ext.data.ArrayStore({
        	    		fields: ['provincie']
        	    	}),
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	//emptyText: this.ComboBoxEmptyTextValue+this.CoArRunState,
        	    	//blankText: this.CoArRunState+this.BlankTextValue,
        	    	//invalidText: this.CoArRunState+this.InvalidTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
        }]
    },{//定义Form第8行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border: false,
		items: [{
			columnWidth:.5,
			layout:'form',
			border: false,
        	items: [       	    
        	    new Ext.form.ComboBox({      
        	    	fieldLabel: this.CoArCommunicationState,      
        	    	store: new Ext.data.ArrayStore({  
        	    		fields: ['provincie']
        	    	}),            
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	//emptyText: this.ComboBoxEmptyTextValue+this.CoArCommunicationState,
        	    	//invalidText:this.CoArCommunicationState+this.InvalidTextValue,
        	    	//blankText: this.CoArCommunicationState+this.BlankTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
        },{
        	columnWidth:.5,
        	layout:'form',
        	border: false,
        	items: [
        	    new Ext.form.ComboBox({
        	    	fieldLabel: this.CoArCommunicationPort,
        	    	hiddenName: 'AmArMainStipulation',
        	    	store: new Ext.data.ArrayStore({
        	    		fields: ['provincie']
        	    	}),
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	//emptyText: this.ComboBoxEmptyTextValue+this.CoArCommunicationPort,
        	    	//blankText: this.CoArCommunicationPort+this.BlankTextValue,
        	    	//invalidText: this.CoArCommunicationPort+this.InvalidTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
        }]
    }]
}