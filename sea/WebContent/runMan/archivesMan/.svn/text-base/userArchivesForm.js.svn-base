/*!
 * Author: Dolphin.Li
 * Date:11/26/2009
 * Description: 用户档案form实体
 * Update History: none
 */
Ext.ns('Ext.UserArchivesForm')

Ext.apply(this,{
	UserArchives: '用户档案',
	UsArCustomerNo: '客户编号',
	UsArCustomerName: '客户名称',
	UsArUserNo:'用户编号',
	UsArUserName:'用户名称',
	UsArDept:'单位',
	UsArUserType:'用户类型',
	UsArPowerSupplyStation :'供电所',
	UsArPowerSupplyLine :'供电线路',
	UsArTrade:'行业',
	UsArPowerCapacity:'受电容量',
	UsArPowerProperty:'用电属性',
	UsArMeasureFashion:'计量方式',
	UsArPowerSupplyVoltage:'供电电压',
	UsArPowerSupplyFashion:'供电方式',
	UsArAmmeterAreaNo:'抄表区码',
	UsArAmmeterDate:'抄表日',
	UsArDateOpened:'开户时间',
	UsArContact:'联系人'	
});//注入ArchivesFormPubNl

//Ext.apply(this,Ext.UserArchivesFormPro.UserArchivesFormNl);//注入UserArchivesFormNl

Ext.UserArchivesForm.UserArchivesFormItems ={
    
	labelWidth: 100, // label settings here cascade unless overridden
    
    //frame:true,
    
    title:this.UserArchives,
    
    //defaults: {width: 220},//定义元素默认width
    
    bodyStyle:'padding:5px 5px 0px 5px',//定义容器内元素距离容器边框距离 上 右 下 左
    
    autoWidth: true,//定义自动根据容器尺寸调整
    
    border : false,
    
    items:[{//数据显示地一行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',//定义容器内元素按照纵向排列
    	border: false,
    	//defaults: {width: 300},
    	labelAlign : 'right', //定义textfield的标签靠右边排列
		items: [{
			border: false,
    		layout:'form',//定义容器内元素按照横向排列
    		columnWidth:.5,	
    		items: [{
    			border: false,
    			xtype:'textfield', //定义元素类型为文本框
    			
    			fieldLabel: this.UsArUserNo,//定义文本框标签为this.UsArUserNo
    			
    			name: 'UsArUserNo',//定义文本框Name
    			
    			allowBlank:false,
    			
    			//width:160,//定义textfield控件长度
    			
    			blankText:this.UsArUserNo+'不能为空',
    			
    			invalidText :this.UsArUserNo+'非法',
    			
    			emptyText:'请输入'+this.UsArUserNo,//定义textfield为空时提示文字
    			
    			//labelStyle: 'font-weight:bold;'//定义标签字体为黑色
    			
    			anchor:'90%'	
    		}]
    		
        },{
        	layout:'form',
        	columnWidth:.5,
        	//bodyStyle : 'padding-left:15px', 
        	border: false,
        	items: [
        	    new Ext.form.ComboBox({
                
        	    	fieldLabel: this.UsArDept,
                
        	    	hiddenName: 'UsArDept',
                
        	    	store: new Ext.data.ArrayStore({
                    
        	    		fields: ['provincie']
        	    		//data : Ext.exampledata.dutch_provinces // from dutch-provinces.js
                
        	    	}),
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	emptyText: '请选择'+this.UsArDept,//replace(/\s/g,this.UsArDept),
        	    	//invalidText: this.UsArDept+this.InvalidTextValue,
        	    	//blankText: this.UsArDept+this.BlankTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
        }]
    },{//数据显示第2行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right', 
    	border: false,
    	items: [{		
    		columnWidth:.5,
    		layout:'form',  
    		border:false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.UsArUserName,  			
    			name: 'UsArUserName',  			
    			allowBlank:false,    			   			
    			//blankText: this.UsArUserName+this.BlankTextValue,
    			//invalidText : this.UsArUserName+this.InvalidTextValue,   			
    			emptyText: '请输入'+this.UsArUserName,
    			anchor:'90%'
    		}]
        },{
        	layout:'form',
        	columnWidth:.5,
        	border: false,
        	items: [       	    
        	    new Ext.form.ComboBox({      
        	    	fieldLabel: this.UsArUserType,      
        	    	store: new Ext.data.ArrayStore({  
        	    		fields: ['provincie']
        	    	}),            
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	emptyText: '请选择'+this.UsArUserType, 
        	    	//invalidText: this.UsArUserType+this.InvalidTextValue,
        	    	//blankText: this.UsArUserType+this.BlankTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
        }]
    },{//定义Form第3行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border: false,
		items: [{
			columnWidth:.5,
			layout:'form',
			labelAlign : 'right',
			border: false,
        	items: [       	    
        	    new Ext.form.ComboBox({      
        	    	fieldLabel: this.UsArPowerSupplyStation,      
        	    	store: new Ext.data.ArrayStore({  
        	    		fields: ['provincie']
        	    	}),            
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	emptyText: '请选择'+this.UsArPowerSupplyStation, 
        	    	//invalidText: this.UsArPowerSupplyStation+this.InvalidTextValue,
        	    	//blankText: this.UsArPowerSupplyStation+this.BlankTextValue,                
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
    		
        },{
        	columnWidth:.5,
        	layout:'form',
        	border: false,
        	items: [
        	    new Ext.form.ComboBox({
        	    	fieldLabel: this.UsArPowerSupplyLine,
        	    	hiddenName: 'state',
        	    	store: new Ext.data.ArrayStore({
        	    		fields: ['provincie']
        	    	}),
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	emptyText: '请选择'+this.UsArPowerSupplyLine, 
        	    	//invalidText: this.UsArPowerSupplyLine+this.InvalidTextValue,
        	    	//blankText: this.UsArPowerSupplyLine+this.BlankTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
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
        	items: [       	    
        	    new Ext.form.ComboBox({      
        	    	fieldLabel: this.UsArTrade,      
        	    	store: new Ext.data.ArrayStore({  
        	    		fields: ['provincie']
        	    	}),            
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	emptyText: '请选择'+this.UsArTrade, 
        	    	//invalidText: this.UsArTrade+this.InvalidTextValue,
        	    	//blankText: this.UsArTrade+this.BlankTextValue,                
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]    		
        },{
        	columnWidth:.5,
    		layout:'form',
    		border: false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.UsArPowerCapacity,  			
    			name: 'UsArPowerCapacity',  			
    			allowBlank:false,    			   			
    			emptyText: '请输入'+this.UsArPowerCapacity,
    	    	//invalidText: this.UsArPowerCapacity+this.InvalidTextValue,
    	    	//blankText: this.UsArPowerCapacity+this.BlankTextValue
    			anchor:'90%'
    		}]
        }]
    },{//定义Form第5行
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
        	    	fieldLabel: this.UsArPowerProperty,      
        	    	store: new Ext.data.ArrayStore({  
        	    		fields: ['provincie']
        	    	}),            
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	emptyText: '请选择'+this.UsArPowerProperty, 
        	    	//invalidText: this.UsArPowerProperty+this.InvalidTextValue,
        	    	//blankText: this.UsArPowerProperty+this.BlankTextValue,                
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
    		
        },{
        	columnWidth:.5,
        	layout:'form',
        	border: false,
        	items: [
        	    new Ext.form.ComboBox({
        	    	fieldLabel: this.UsArMeasureFashion,
        	    	hiddenName: 'state',
        	    	store: new Ext.data.ArrayStore({
        	    		fields: ['provincie']
        	    	}),
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	emptyText: '请选择'+this.UsArMeasureFashion, 
        	    	//invalidText: this.UsArMeasureFashion+this.InvalidTextValue,
        	    	//blankText: this.UsArMeasureFashion+this.BlankTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
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
    			fieldLabel: this.UsArPowerSupplyVoltage,
    			name: 'UsArPowerSupplyVoltage',
    			allowBlank:false,
    			emptyText: '请选择'+this.UsArPowerSupplyVoltage,
    	    	//invalidText: this.UsArPowerSupplyVoltage+this.InvalidTextValue,
    	    	//blankText: this.UsArPowerSupplyVoltage+this.BlankTextValue
    			anchor:'90%'
    		}]
        },{
        	columnWidth:.5,
        	layout:'form',
        	border: false,
        	items: [
        	    new Ext.form.ComboBox({
        	    	fieldLabel: this.UsArPowerSupplyFashion,
        	    	hiddenName: 'UsArPowerSupplyFashion',
        	    	store: new Ext.data.ArrayStore({
        	    		fields: ['provincie']
        	    	}),
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	emptyText: '请选择'+this.UsArPowerSupplyFashion, 
        	    	//invalidText: this.UsArPowerSupplyFashion+this.InvalidTextValue,
        	    	//blankText: this.UsArPowerSupplyFashion+this.BlankTextValue,
        	    	selectOnFocus:true,
        	    	anchor:'90%'
            })]
        }]
    },{//定义第7行
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
    			fieldLabel: this.UsArAmmeterAreaNo,
    			name: 'UsArAmmeterAreaNo',
    			allowBlank:false,
    			emptyText: '请输入'+this.UsArAmmeterAreaNo,
    	    	//invalidText: this.UsArAmmeterAreaNo+this.InvalidTextValue,
    	    	//blankText: this.UsArAmmeterAreaNo+this.BlankTextValue
    			anchor:'90%'
    		}]
        },{
        	columnWidth:.5,
        	layout:'form',
        	border: false,
        	items: [
        	        
        	        new Ext.form.DateField({//定义一个日期控件
        	        	fieldLabel: this.UsArAmmeterDate,
        	        	name: 'UsArAmmeterDate',
        	        	showToday:true,
        	        	format:'Y-m-d',
        	        	emptyText: '请选择日期'+this.UsArAmmeterDate,
            	    	//invalidText: this.UsArAmmeterDate+this.InvalidTextValue,
            	    	//blankText: this.UsArAmmeterDate+this.BlankTextValue
        	        	anchor:'90%'
        	   })
            ]
        }]
    },{//定义第8行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',
    	labelAlign : 'right',
    	border: false,
		items: [{
    		layout:'form',
    		columnWidth:.5,
    		border: false,
    		items: [
    	        new Ext.form.DateField({
    	        	fieldLabel: this.UsArDateOpened,
    	        	name: 'UsArDateOpened',
    	        	showToday:true,
    	        	format:'Y-m-d',
    	        	emptyText: '请选择日期'+this.UsArDateOpened,
        	    	//invalidText: this.UsArDateOpened+this.InvalidTextValue,
        	    	//blankText: this.UsArDateOpened+this.BlankTextValue
    	        	anchor:'90%'
            	   }) 				
    		]
    		
        },{
        	layout:'form',
        	columnWidth:.5,
        	border: false,
        	//bodyStyle : 'padding-left:15px', 
        	items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.UsArContact,  			
    			name: 'UsArContact',  			
    			allowBlank:false,    			   			
    			emptyText: '请输入'+this.UsArContact,
    	    	//invalidText: this.UsArContact+this.InvalidTextValue,
    	    	//blankText: this.UsArContact+this.BlankTextValue
    			anchor:'90%'
    		}]
        }]
    }]
}


