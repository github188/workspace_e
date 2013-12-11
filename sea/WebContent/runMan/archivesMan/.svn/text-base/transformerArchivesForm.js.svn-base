/*!
 * Author: Dolphin.Li
 * Date:11/27/2009
 * Description: 变压器档案form实体
 * Update History: none
 */
Ext.ns('Ext.TransformerArchivesForm'); 

//Ext.apply(this,Ext.ArchivesForm.ArchivesFormPubNl);//注入ArchivesFormPubNl
Ext.apply(this,{		
		TransformerArchives:'变压器档案',
	    TrArGroupNo:'变压器组号',
	    TrArNo:'编号',
	    TrArModel:'型号',
	    TrArType:'类型',
	    TrArManufacturer :'制造厂家',
	    TrArNameplateCapacity:'铭牌容量'
});
Ext.TransformerArchivesForm.TransformerArchivesFormItems ={
	labelWidth: 100, // label settings here cascade unless overridden
    frame:false,
    title:this.TransformerArchives,   
    bodyStyle:'padding:5px 5px 0px 5px',//定义容器内元素距离容器边框距离 上 右 下 左
    autoWidth: true,//定义自动根据容器尺寸调整
    border:false,
    items:[{//数据显示地一行
    	bodyStyle:'padding:3px 0px 0px 0px',
    	layout:'column',//定义容器内元素按照纵向排列
    	labelAlign : 'right', //定义textfield的标签靠右边排列
    	border:false,
		items: [{
			layout:'form',
        	columnWidth:.5,
        	//bodyStyle : 'padding-left:15px', 
        	border:false,
        	items: [
        	    new Ext.form.ComboBox({
        	    	fieldLabel: this.TrArGroupNo,
        	    	hiddenName: 'TrArGroupNo',
        	    	valueField:'TrArGroupNoID1',
        	    	id: 'TrArGroupNoID',
        	    	store: new Ext.data.ArrayStore({
        	    		fields: ['provincie']
        	    		//data : Ext.exampledata.dutch_provinces // from dutch-provinces.js
        	    	}),
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	/*emptyText: this.ComboBoxEmptyTextValue+this.TrArGroupNo,
        	    	invalidText: this.TrArGroupNo+this.InvalidTextValue,
        	    	blankText: this.TrArGroupNo+this.BlankTextValue*/
        	    	emptyText: '请选择'+this.TrArGroupNo,
        	    	invalidText: this.TrArGroupNo+'非法',
        	    	blankText: this.TrArGroupNo+'不能为空',
        	    	anchor: '90%'
            })]
        },{
        	layout:'form',
        	columnWidth:.5,
        	border:false,
        	items: [
        	    new Ext.form.ComboBox({
        	    	fieldLabel: this.TrArNo,
        	    	hiddenName: 'TrArNo',
        	    	store: new Ext.data.ArrayStore({
        	    		fields: ['provincie']
        	    	}),
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	emptyText: '请选择'+this.TrArNo,
        	    	blankText: this.TrArNo+'不能为空',
        	    	invalidText: this.TrArNo+'非法',
        	    	selectOnFocus:true,
        	    	anchor: '90%'
            })]
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
        	    	fieldLabel: this.TrArModel,      
        	    	store: new Ext.data.ArrayStore({  
        	    		fields: ['provincie']
        	    	}),            
        	    	displayField: 'provincie',
        	    	typeAhead: true,
        	    	mode: 'local',
        	    	triggerAction: 'all',
        	    	emptyText: '请输入'+this.TrArModel,
        	    	blankText: this.TrArModel+'不能为空',
        	    	invalidText:this.TrArModel+'非法',
        	    	selectOnFocus:true,
        	    	anchor: '90%'
            })]
        },{        	
            columnWidth:.5,
    		layout:'form',
    		border:false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.TrArType,  			
    			name: 'TrArType',  			
    			allowBlank:false,    			   			
    			blankText:this.TrArType+'不能为空',
    			invalidText :this.TrArType+'非法',   			
    			emptyText:'请输入'+this.TrArType,
    			anchor: '90%'
    		}]
        }]
    },{//定义第3行
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
    			fieldLabel: this.TrArManufacturer,  			
    			name: 'TrArManufacturer',  			
    			allowBlank:false,    			   			
    			blankText:this.TrArManufacturer+'不能为空',
    			emptyText:'请输入'+this.TrArManufacturer,
    			invalidText: this.TrArManufacturer+'非法',
    			anchor: '90%'
    		}]    		
        },{
        	columnWidth:.5,
    		layout:'form', 
    		border:false,
    		items: [{   			
    			xtype:'textfield',
    			fieldLabel: this.TrArNameplateCapacity,  			
    			name: 'TrArNameplateCapacity',  			
    			allowBlank:false,    			   			
    			blankText:this.TrArNameplateCapacity+'不能为空',
    			invalidText :this.TrArNameplateCapacity+'非法',   			
    			emptyText:'请输入'+this.TrArNameplateCapacity,
    			anchor: '90%'
    		}]
        }]
    }]
}


