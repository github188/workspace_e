function ctrlCheckStaff(func, type){
	if(type =='ctrl') {
		//控制下发panel, 需要输入密码
		var checkPanel = new Ext.Panel({
	        height:100,
	        border : false,
	        layout:'form',
	        items:[{
	   	       layout:'form',
	   	       border : false,
	   	       labelAlign : "right",
	   	       labelWidth : 80,
	   	       bodyStyle : 'padding:10px 0px 0px 10px',
	   	       items:[{
	   	           xtype:'textfield',
	   	           name:'overtime',
	   	           width:'80',
	   	           value:45,
	   	           fieldLabel:'超时时间(秒)'
	   	       }] 
	   	    },{
	   	       layout:'form',
	   	       border : false,
	   	       labelAlign : "right",
	   	       labelWidth : 80,
	   	       bodyStyle : 'padding:5px 0px 0px 10px',
	   	       items:[{
	   	           xtype:'textfield',
	   	           name :'staffPwd',
	   	           inputType:'password',
	   	           width:'80',
	   	           fieldLabel:'操作员密码'
	   	       }] 
	   	    },{
	        	layout:'column',
	   	        border : false,
	   	        items:[{
	   	            columnWidth:.5,
	   	            layout:'form',
	   	            border : false,
	   	            bodyStyle : 'padding:5px 0px 0px 45px',
	   	            labelAlign: "right",
	   	            hideLabels:true,
	   	            items:[{
	   	            	xtype:'button',
						text:'执行',
						width:60,
						handler : function() {
							//取得输入框超时时间
							var overtime = checkPanel.find('name','overtime')[0].getValue();
							if(overtime==null ||overtime==''){
								Ext.MessageBox.alert("提示","请设定超时时间");
								return;
							}
							Ext.Ajax.timeout = 1000*overtime;
							//取得输入框密码
							var staffPwd = checkPanel.find('name','staffPwd')[0].getValue();
							if(staffPwd==null ||staffPwd==''){
								Ext.MessageBox.alert("提示","请输入密码");
								return;
							}
							Ext.Ajax.request({
								url:'./baseapp/checkStaff!checkStaffPwd.action',
				  		     		params : {
				  		     			staffPwd:staffPwd
				  		     		},
				  		     		success : function(response){
				  		     			var res = Ext.decode(response.responseText).checkStaffRes;
				  		     			if(res) {
				  		     				checkWindow.close();
				  		     				func();
				  		     			} else {
				  		     				Ext.MessageBox.alert("提示","密码输入错误")
				  		     			}
				  		     		}
							}); 
						}
	   	            }] 
	   	        }, {
	   	            columnWidth:.5,
	   	            layout:'form',
	   	            border : false,
	   	            bodyStyle : 'padding:5px 0px 0px 0px',
	   	            hideLabels:true,
	   	            items:[{
			   	         	xtype:'button',   	
							text:'取消',
							width:60,
							handler : function() {
								checkWindow.close();
							}
	   	            }] 
	   	        }]
	   	    }]
		});
		
		//控制下发校验密码窗口
		var checkWindow = new Ext.Window({
		   title:'操作认证',
	       name:'checkWindow',
	       height:138,
	       modal:true,
	       width:235,
		   resizable:false,
		   layout:'fit',
		   items:[checkPanel]
		});
		checkWindow.show();
		return;
	}

	//召测，参数下发panel，没有密码校验
	var setTimeoutPanel = new Ext.Panel({
        height:100,
        border : false,
        layout:'form',
        items:[{
   	       layout:'form',
   	       border : false,
   	       labelAlign : "right",
   	       labelWidth : 80,
   	       bodyStyle : 'padding:20px 0px 10px 10px',
   	       items:[{
   	           xtype:'textfield',
   	           name:'overtime',
   	           width:'80',
   	           value:45,
   	           fieldLabel:'超时时间(秒)'
   	       }] 
   	    },{
        	layout:'column',
   	        border : false,
   	        items:[{
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 45px',
   	            labelAlign: "right",
   	            hideLabels:true,
   	            items:[{
   	            	xtype:'button',
					text:'执行',
					width:60,
					handler : function() {
						//取得输入框超时时间
						var overtime = setTimeoutPanel.find('name','overtime')[0].getValue();
						if(overtime==null ||overtime==''){
							Ext.MessageBox.alert("提示","请设定超时时间");
							return;
						}
						Ext.Ajax.timeout = 1000*overtime;
						timeoutWindow.close();
						func();
					}
   	            }] 
   	        }, {
   	            columnWidth:.5,
   	            layout:'form',
   	            border : false,
   	            bodyStyle : 'padding:5px 0px 0px 0px',
   	            hideLabels:true,
   	            items:[{
	   	         	xtype:'button',   	
					text:'取消',
					width:60,
					handler : function() {
						timeoutWindow.close();
					}
   	            }] 
   	        }]
   	    }]
	});
	
	//参数下发召测超时设置窗口
	var timeoutWindow = new Ext.Window({
	   title:'超时设定',
       name:'checkWindow',
       height:138,
       modal:true,
       width:235,
	   resizable:false,
	   layout:'fit',
	   items:[setTimeoutPanel]
	});
	timeoutWindow.show();
}