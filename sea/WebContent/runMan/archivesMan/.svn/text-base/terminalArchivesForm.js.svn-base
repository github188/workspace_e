/*!
 * Author: Dolphin.Li
 * Date:11/27/2009
 * Description: 终端档案form实体
 * Update History: none
 */
Ext.ns('Ext.TerminalArchivesForm'); 

//Ext.apply(this,Ext.ArchivesForm.ArchivesFormPubNl);
Ext.apply(this,{
	TerminalArchives:'终端档案',
    TeArBranchNo:'终端局号',
    TeArLogicalAddress :'终端逻辑地址',
    TeArManufacturerNo:'制造厂家编码',
    TeArModel:'终端型号',
    TeArType:'终端类型',
    TeArSIMNo:'SIM卡号',
    TeArMainAddress:'主通信地址',
    TeArMessageAddress:'短信地址',
    TeArAPN:'APN',
    TeArConnection:'接线方式',
    TeArStipulationType:'规约类型',
    TeArCollectModel:'数据采集方式',
    TeArHightPassword:'高权限密码',
    TeArLowPassword:'低权限密码',
    TeArCT:'CT',
    TeArPT:'PT',
    TeArInstallDate:'终端安装日期'
});

Ext.TerminalArchivesForm.TerminalArchivesFormItems ={
	labelWidth: 100, 
    frame:false,
    title:this.TerminalArchives,
    bodyStyle:'padding:5px 5px 0px 5px',
    autoWidth: true,
    border:false,
    items:[{//数据显示地一行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right', 
    	border:false,
		items: [{
			layout:'form',
        	columnWidth:.5,
        	border:false,
        	items: [
        	    new Ext.form.ComboBox({
        	    	fieldLabel: this.TeArBranchNo,
        	    	hiddenName: 'TeArBranchNo',
        	    	store: new Ext.data.ArrayStore({
        	    		fields: ['provincie']
        	    	}),
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	emptyText: '请选择'+this.TeArBranchNo,
        	    	//blankText:this.TeArBranchNo+this.BlankTextValue,
        	    	//invalidText:this.TeArBranchNo+this.InvalidTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
        },{
        	layout:'form',
    		columnWidth:.5,	
    		border:false,
    		items: [{
    			xtype:'textfield', 
    			fieldLabel: this.TeArLogicalAddress,
    			name: 'TeArLogicalAddress',
    			allowBlank:false,
    			//blankText:this.TeArLogicalAddress+this.BlankTextValue,
    			//invalidText :this.TeArLogicalAddress+this.InvalidTextValue,
    			emptyText:'请输入'+this.TeArLogicalAddress,
    			anchor:'90%'
    		}]
        }]
    },{//数据显示第2行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right', 
    	border:false,
    	items: [{	
    		layout:'form',
        	columnWidth:.5,
        	border:false,
        	items: [       	    
        	    new Ext.form.ComboBox({      
        	    	fieldLabel: this.TeArManufacturerNo,      
        	    	store: new Ext.data.ArrayStore({  
        	    		fields: ['provincie']
        	    	}),            
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	emptyText: '请选择'+this.TeArManufacturerNo,
        	    	//blankText:this.TeArManufacturerNo+this.BlankTextValue,
        	    	//invalidText:this.TeArManufacturerNo+this.InvalidTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]    		
        },{
        	columnWidth:.5,
    		layout:'form',   
    		border:false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.TeArModel,  			
    			name: 'TeArModel',  			
    			allowBlank:false,    			   			
    			//blankText:this.TeArModel+this.BlankTextValue,
    			//invalidText :this.TeArModel+this.InvalidTextValue,   			
    			emptyText:'请输入'+this.TeArModel,
    			anchor:'90%'
    		}]
        }]
    },{//数据显示第3行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right', 
    	border:false,
    	items: [{	
    		layout:'form',
        	columnWidth:.5,
        	border:false,
        	items: [       	    
        	    new Ext.form.ComboBox({      
        	    	fieldLabel: this.TeArType,      
        	    	store: new Ext.data.ArrayStore({  
        	    		fields: ['provincie']
        	    	}),            
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	emptyText: '请选择'+this.TeArType,
        	    	//blankText:this.TeArType+this.BlankTextValue,
        	    	//invalidText:this.TeArType+this.InvalidTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]    		
        },{
        	columnWidth:.5,
    		layout:'form',  
    		border:false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.TeArSIMNo,  			
    			name: 'TeArSIMNo',  			
    			allowBlank:false,    			   			
    			//blankText:this.TeArSIMNo+this.BlankTextValue,
    			//invalidText :this.TeArSIMNo+this.InvalidTextValue,   			
    			emptyText:'请输入'+this.TeArSIMNo,
    			anchor:'90%'
    		}]
        }]
    },{//定义第4行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border:false,
    	items: [{
    		columnWidth:.5,
    		layout:'form',
    		border:false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.TeArMainAddress,  			
    			name: 'TeArMainAddress',  			
    			allowBlank:false,    			   			
    			blankText:this.TeArMainAddress+this.BlankTextValue,
    			invalidText :this.TeArMainAddress+this.InvalidTextVlalue,   			
    			emptyText:this.TextFieldEmptyTextValue+this.TeArMainAddress,
    			anchor:'90%'
    		}] 		
        },{
        	columnWidth:.5,
    		layout:'form',
    		border:false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.TeArMessageAddress,  			
    			name: 'TeArMessageAddress',  			
    			allowBlank:false,    			   			
    			blankText:this.TeArMessageAddress+this.BlankTextValue,
    			invalidText :this.TeArMessageAddress+this.InvalidTextValue,   			
    			emptyText:this.TextFieldEmptyTextValue+this.TeArMessageAddress,
    			anchor:'90%'
    		}]
        }]
    },{//定义第5行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border:false,
		items: [{
			columnWidth:.5,
    		layout:'form',
    		border:false,
    		items: [{
    			xtype:'textfield',
    			fieldLabel: this.TeArAPN,
    			name: 'TeArAPN',
    			allowBlank:false,
    			blankText:this.TeArAPN+this.EmptyTextValue,
    			invalidText :this.TeArAPN+this.InvalidTextValue,
    			emptyText:this.TextFieldEmptyTextValue+this.TeArAPN,
    			anchor:'90%'
    		}]
        },{
        	columnWidth:.5,
        	layout:'form',
        	border:false,
        	items: [
        	    new Ext.form.ComboBox({
        	    	fieldLabel: this.TeArConnection,
        	    	hiddenName: 'TeArConnection',
        	    	store: new Ext.data.ArrayStore({
        	    		fields: ['provincie']
        	    	}),
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	emptyText:this.ComboBoxEmptyTextValue+this.TeArConnection,
        	    	blankText:this.TeArConnection+this.BlankTextValue,
        	    	invalidText:this.TeArConnection+this.InvalidTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
        }]
    },{//定义Form第6行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border:false,
		items: [{
			columnWidth:.5,
			layout:'form',
			border:false,
        	items: [       	    
        	    new Ext.form.ComboBox({      
        	    	fieldLabel: this.TeArStipulationType,      
        	    	store: new Ext.data.ArrayStore({  
        	    		fields: ['provincie']
        	    	}),            
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	emptyText: this.ComboBoxEmptyTextValue+this.TeArStipulationType,
        	    	invalidText:this.TeArStipulationType+this.InvalidTextValue,
        	    	blankText: this.TeArStipulationType+this.BlankTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
    		
        },{
        	columnWidth:.5,
        	layout:'form',
        	border:false,
        	items: [
        	    new Ext.form.ComboBox({
        	    	fieldLabel: this.TeArCollectModel,
        	    	hiddenName: 'TeArCollectModel',
        	    	store: new Ext.data.ArrayStore({
        	    		fields: ['provincie']
        	    	}),
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	emptyText: this.ComboBoxEmptyTextValue+this.TeArCollectModel,
        	    	blankText: this.TeArCollectModel+this.BlankTextValue,
        	    	invalidText: this.TeArCollectModel+this.InvalidTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
        }]
    },{//定义第7行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border:false,
    	items: [{
    		columnWidth:.5,
    		layout:'form',
    		border:false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.TeArHightPassword,  			
    			name: 'TeArHightPassword',  			
    			allowBlank:false,    			   			
    			blankText:this.TeArHightPassword+this.BlankTextValue,
    			invalidText :this.TeArHightPassword+this.InvalidTextVlalue,   			
    			emptyText:this.TextFieldEmptyTextValue+this.TeArHightPassword,
    			anchor:'90%'
    		}] 		
        },{
        	columnWidth:.5,
    		layout:'form',   
    		border:false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.TeArLowPassword,  			
    			name: 'TeArLowPassword',  			
    			allowBlank:false,    			   			
    			blankText:this.TeArLowPassword+this.BlankTextValue,
    			invalidText :this.TeArLowPassword+this.InvalidTextValue,   			
    			emptyText:this.TextFieldEmptyTextValue+this.TeArLowPassword,
    			anchor:'90%'
    		}]
        }]
    },{//定义第8行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border:false,
    	items: [{
    		columnWidth:.5,
    		layout:'form',
    		border:false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.TeArCT,  			
    			name: 'TeArCT',  			
    			allowBlank:false,    			   			
    			blankText:this.TeArCT+this.BlankTextValue,
    			invalidText :this.TeArCT+this.InvalidTextVlalue,   			
    			emptyText:this.TextFieldEmptyTextValue+this.TeArCT,
    			anchor:'90%'
    		}] 		
        },{
        	columnWidth:.5,
    		layout:'form',   
    		border:false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.TeArPT,  			
    			name: 'TeArPT',  			
    			allowBlank:false,    			   			
    			blankText:this.TeArPT+this.BlankTextValue,
    			invalidText :this.TeArPT+this.InvalidTextValue,   			
    			emptyText:this.TextFieldEmptyTextValue+this.TeArPT,
    			anchor:'90%'
    		}]
        }]
    },{//定义第9行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border:false,
		items: [{
			columnWidth:.5,
        	layout:'form',
        	border:false,
        	items: [
    	        new Ext.form.DateField({//定义一个日期控件
    	        	fieldLabel: this.TeArInstallDate,
    	        	name: 'TeArInstallDate',
    	        	showToday:true,
    	        	format:'Y-m-d',
    	        	emptyText:this.DateFieldEmptyTextValue,
    	        	blankText: this.TeArInstallDate+this.BlankTextValue,
    	        	invalidText: this.TeArInstallDate+this.InvalidTextValue,
    	        	anchor:'90%'
        	   })
            ]
        }]
    }]
}


