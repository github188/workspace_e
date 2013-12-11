/*!
 * Author: Dolphin.Li
 * Date:11/26/2009
 * Description: 表计元素属性
 * Update History: none
 */
Ext.ns('Ext.ArchivesFrom'); 

//Ext.apply(this,Ext.ArchivesForm.ArchivesFormPubNl);//注入ArchivesFormPubNl

Ext.ArchivesFrom.QueryArchivesForm ={
    labelWidth: 100, // label settings here cascade unless overridden
    
    frame:false,
    
    border:false,
        
    bodyStyle:'padding:5px 5px 0px 5px',//定义容器内元素距离容器边框距离 上 右 下 左
    
    autoWidth: true,//定义自动根据容器尺寸调整
    
    layout:'column',//定义容器内元素按照纵向排列
    	
	items: [{
		
		layout:'form',//定义容器内元素按照横向排雷
		
		labelAlign : 'right', //定义textfield的标签靠右边排列
		
		border:false,
		
		items: [{
			
			xtype:'textfield', //定义元素类型为文本框
			
			fieldLabel: '用户编号',//定义文本框标签为this.UsArUserNo
			
			name: 'UsArUserNo',//定义文本框Name
			
			allowBlank:false,
			
			width:400,
			
			blankText:'用户编号不能为空',
			
			invalidText :'用户编号不存在',
			
			emptyText:'请选择用户编号'//定义textfield为空时提示文字
			
			//labelStyle: 'font-weight:bold;'//定义标签字体为黑色
				
		}]
		
    },{
    	layout:'form',
    	bodyStyle : 'padding-left:15px', 
    	
    	border:false,
    	items: [{
    		xtype: 'button',
    		text: '查询',
    		width:80       		
    	}]
    }]
}


