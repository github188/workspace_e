/*!
 * Author: Dolphin.Li
 * Date:11/27/2009
 * Description: 表计档案form实体
 * Update History: none
 */
Ext.ns('Ext.AmmeterArchivesForm'); 

//Ext.apply(this,Ext.ArchivesForm.ArchivesFormPubNl);
Ext.apply(this,{
	AmmeterArchives:'表计档案',
    AmArBranchNo:'表计局号',
    AmArManufacturerNo:'厂商编码',
    AmArAmmeterType:'表类型',
    AmArCollectModel:'接线方式',
    AmArMainStipulation:'主规约',
    AmArMStAddress:'主规约通信地址',
    AmArMStPassword:'主规约通信密码',
    AmArCT:'CT',
    AmArPT:'PT',
    AmArPulseCode:'脉冲常数',
    AmArStoichiometricPointNo:'计量点编号',
    AmArSelfMagnification:'自身倍率',
    AmArDigits:'位数',
    AmArPrecision :'精度',
    AmArDateInstalled:'装出日期'   
});

Ext.AmmeterArchivesForm.AmmeterArchivesFormItems ={
	labelWidth: 100, 
    frame:false,
    title:this.AmmeterArchives,
    bodyStyle:'padding:5px 5px 0px 5px',
    autoWidth: true,
    border : false,
    items:[{//定义Form第1行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border : false,
		items: [{
			columnWidth:.5,
			layout:'form',
			border : false,
        	items: [       	    
        	    new Ext.form.ComboBox({      
        	    	fieldLabel: this.AmArBranchNo,      
        	    	store: new Ext.data.ArrayStore({  
        	    		fields: ['provincie']
        	    	}),            
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	//emptyText: this.ComboBoxEmptyTextValue+this.AmArBranchNo,
        	    	//invalidText:this.AmArBranchNo+this.InvalidTextValue,
        	    	//blankText: this.AmArBranchNo+this.BlankTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
        },{
        	columnWidth:.5,
        	layout:'form',
        	border : false,
        	items: [
        	    new Ext.form.ComboBox({
        	    	fieldLabel: this.AmArManufacturerNo,
        	    	hiddenName: 'AmArManufacturerNo',
        	    	store: new Ext.data.ArrayStore({
        	    		fields: ['provincie']
        	    	}),
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	//emptyText: this.ComboBoxEmptyTextValue+this.AmArManufacturerNo,
        	    	//blankText: this.AmArManufacturerNo+this.BlankTextValue,
        	    	//invalidText: this.AmArManufacturerNo+this.InvalidTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
        }]
    },{//数据显示地2行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right', 
    	border : false,
		items: [{
			layout:'form',
        	columnWidth:1,
        	border : false,
        	items: [
        	    new Ext.form.ComboBox({
        	    	fieldLabel: this.AmArAmmeterType,
        	    	hiddenName: 'AmArAmmeterType',
        	    	store: new Ext.data.ArrayStore({
        	    		fields: ['provincie']
        	    	}),
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	//emptyText: this.ComboBoxEmptyTextValue+this.AmArAmmeterType,
        	    	//blankText:this.AmArAmmeterType+this.BlankTextValue,
        	    	//invalidText:this.AmArAmmeterType+this.InvalidTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
        }]
    },{//定义Form第3行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border : false,
		items: [{
			columnWidth:.5,
			layout:'form',
			border : false,
        	items: [       	    
        	    new Ext.form.ComboBox({      
        	    	fieldLabel: this.AmArCollectModel,      
        	    	store: new Ext.data.ArrayStore({  
        	    		fields: ['provincie']
        	    	}),            
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	//emptyText: this.ComboBoxEmptyTextValue+this.AmArCollectModel,
        	    	//invalidText:this.AmArCollectModel+this.InvalidTextValue,
        	    	//blankText: this.AmArCollectModel+this.BlankTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
        },{
        	columnWidth:.5,
        	layout:'form',
        	border : false,
        	items: [
        	    new Ext.form.ComboBox({
        	    	fieldLabel: this.AmArMainStipulation,
        	    	hiddenName: 'AmArMainStipulation',
        	    	store: new Ext.data.ArrayStore({
        	    		fields: ['provincie']
        	    	}),
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	//emptyText: this.ComboBoxEmptyTextValue+this.AmArMainStipulation,
        	    	//blankText: this.AmArMainStipulation+this.BlankTextValue,
        	    	//invalidText: this.AmArMainStipulation+this.InvalidTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
        }]
    },{//定义第4行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border : false,
    	items: [{
    		columnWidth:.5,
    		layout:'form',
    		border : false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.AmArMStAddress,  			
    			name: 'AmArMStAddress',  			
    			allowBlank:false,
    			anchor:'90%'
    			//blankText:this.AmArMStAddress+this.BlankTextValue,
    			//invalidText :this.AmArMStAddress+this.InvalidTextVlalue,   			
    			//emptyText:this.TextFieldEmptyTextValue+this.AmArMStAddress   
    		}] 		
        },{
        	columnWidth:.5,
    		layout:'form',   
    		border : false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.AmArMStPassword,  			
    			name: 'AmArMStPassword',  			
    			allowBlank:false,
    			anchor:'90%'
    			//blankText:this.AmArMStPassword+this.BlankTextValue,
    			//invalidText :this.AmArMStPassword+this.InvalidTextValue,   			
    			//emptyText:this.TextFieldEmptyTextValue+this.AmArMStPassword  
    		}]
        }]
    },{//定义第5行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border : false,
    	items: [{
    		columnWidth:.5,
    		layout:'form',
    		border : false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.AmArCT,  			
    			name: 'AmArCT',  			
    			allowBlank:false,
    			anchor:'90%'
    			//blankText:this.AmArCT+this.BlankTextValue,
    			//invalidText :this.AmArCT+this.InvalidTextVlalue,   			
    			//emptyText:this.TextFieldEmptyTextValue+this.AmArCT   
    		}] 		
        },{
        	columnWidth:.5,
    		layout:'form', 
    		border : false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.AmArPT,  			
    			name: 'AmArPT',  			
    			allowBlank:false,
    			anchor:'90%'
    			//blankText:this.AmArPT+this.BlankTextValue,
    			//nvalidText :this.AmArPT+this.InvalidTextValue,   			
    			//emptyText:this.TextFieldEmptyTextValue+this.AmArPT  
    		}]
        }]
    },{//定义第6行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border : false,
    	items: [{
    		columnWidth:.5,
    		layout:'form',		
    		border : false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.AmArPulseCode,  			
    			name: 'AmArPulseCode',  			
    			allowBlank:false,
    			anchor:'90%'
    			//blankText:this.AmArPulseCode+this.BlankTextValue,
    			//invalidText :this.AmArPulseCode+this.InvalidTextVlalue,   			
    			//emptyText:this.TextFieldEmptyTextValue+this.AmArPulseCode   
    		}] 		
        },{
        	columnWidth:.5,
    		layout:'form',   
    		border : false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.AmArStoichiometricPointNo,  			
    			name: 'AmArStoichiometricPointNo',  			
    			allowBlank:false,
    			anchor:'90%'
    			//blankText:this.AmArStoichiometricPointNo+this.BlankTextValue,
    			//invalidText :this.AmArStoichiometricPointNo+this.InvalidTextValue,   			
    			//emptyText:this.TextFieldEmptyTextValue+this.AmArStoichiometricPointNo  
    		}]
        }]
    },{//定义第7行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border : false,
    	items: [{
    		columnWidth:.5,
    		layout:'form',  
    		border : false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.AmArSelfMagnification,  			
    			name: 'AmArSelfMagnification',  			
    			allowBlank:false,
    			anchor:'90%'
    			//blankText:this.AmArSelfMagnification+this.BlankTextValue,
    			//invalidText :this.AmArSelfMagnification+this.InvalidTextVlalue,   			
    			//emptyText:this.TextFieldEmptyTextValue+this.AmArSelfMagnification   
    		}] 		
        },{
        	columnWidth:.5,
    		layout:'form',   
    		border : false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.AmArDigits,  			
    			name: 'AmArDigits',  			
    			allowBlank:false,
    			anchor:'90%'
    			//blankText:this.AmArDigits+this.BlankTextValue,
    			//invalidText :this.AmArDigits+this.InvalidTextValue,   			
    			//emptyText:this.TextFieldEmptyTextValue+this.AmArDigits  
    		}]
        }]
    },{//定义第8行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border : false,
    	items: [{
    		columnWidth:.5,
    		layout:'form',	
    		border : false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.AmArPrecision,  			
    			name: 'AmArPrecision',  			
    			allowBlank:false,
    			anchor:'90%'
    			//blankText:this.AmArPrecision+this.BlankTextValue,
    			//invalidText :this.AmArPrecision+this.InvalidTextVlalue,   			
    			//emptyText:this.TextFieldEmptyTextValue+this.AmArPrecision   
    		}] 		
        },{
        	layout:'form',
        	columnWidth:.5,
        	border : false,
        	items: [
        	    new Ext.form.ComboBox({
        	    	fieldLabel: this.AmArDateInstalled,
        	    	hiddenName: 'AmArDateInstalled',
        	    	store: new Ext.data.ArrayStore({
        	    		fields: ['provincie']
        	    	}),
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	//emptyText: this.ComboBoxEmptyTextValue+this.AmArDateInstalled,
        	    	//blankText:this.AmArDateInstalled+this.BlankTextValue,
        	    	//invalidText:this.AmArDateInstalled+this.InvalidTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
        }]
    }]
}