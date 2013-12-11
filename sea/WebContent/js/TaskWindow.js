Ext.namespace("Ext.ux")
Ext.ux.MyTaskWindow = Ext.extend(Ext.Window,{
	initComponent:function(){
		var pBar = new Ext.ProgressBar({
			width:300,
			text:'任务执行中',
			hidden:true
		});
		var textfield = new Ext.form.TextField({
			fieldLabel:'任务时间(秒)',
			value:'20'
		});
		
		var clsWindow = function(){
			//获取容器
			this.ownerCt.hide();
			textfield.show();
			textfield.getEl().up('.x-form-item').setDisplayed(true);
			pBar.reset();
			pBar.hide();
		};
		
		//任务时间
		var tasktime;
		var submit = function(){
			pBar.show();
			tasktime = textfield.getValue();
			textfield.hide();
			//隐藏前面的label
			textfield.getEl().up('.x-form-item').setDisplayed(false);
			//任务事件
			pBar.ownerCt.customHandler();
			Loader.start(pBar, totalCount, tasktime, function(){
				pBar.updateText('任务执行完成');
				Ext.MessageBox.alert("提示",'任务执行完成!');
//				pBar.ownerCt.hide();
				textfield.show();
				textfield.setValue("20"),
				textfield.getEl().up('.x-form-item').setDisplayed(true);
				pBar.reset();
				pBar.hide();
			});
		}
		
		//总记录数
		var totalCount ;
		totalCount = (this.taskCount=""?10:this.taskCount);
		
		var Loader = function() {
				var f = function(v, pbar, count, cb) {
					return function() {
						if (v > count) {
							cb();
						}else{
							pBar.updateProgress(v / count,'任务执行中...');
						}
					}
				};
				return {
					start : function(pbar, count, time, cb) {
						var totalms = time;// 执行任务总共所需要时间
						var ms = totalms*1000 / count;//每个任务执行所需要时间
						// var ms = 1000;
						for (var i = 1; i <= (count + 1); i++) {
							if (i == 1) {
								setTimeout(f(i, pbar, count, cb), 0);
							} else {
								setTimeout(f(i, pbar, count, cb), (i-1) * ms);
							}
						}
					}
				};
			}();		
		
		Ext.apply(this,{
			title:'任务等待',
			modal:true,
			closeAction:'hide',
			bodyStyle:'padding:20px,20px,20px,20px;',
			height:150,
			width:350,
			buttonAlign:'center',
			layout:'form',
			labelSeparator:' ',
			labelAlign:'right',
			draggable:false,
			resizable:false,
			items:[textfield,pBar],
			buttons:[{
				text:'确定',
				handler:submit
			},{
				text:'取消',
				handler:clsWindow
			}]				
		});
		
		Ext.ux.MyTaskWindow.superclass.initComponent.call(this);
	}
})
Ext.reg("mytaskwindow",Ext.ux.MyTaskWindow)