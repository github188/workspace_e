Ext.onReady(function() {	
	/** 根据模板id获取模板具体信息**/
	function getTempInfo(value){
		propoComStore.load();
		Ext.Ajax.request({
			url:'sysman/taskModelMan!queryTaskTemplateJson.action',
			params:{templateId:value},
			success:function(response) {
				var result = Ext.decode(response.responseText);
				if (!result) {
					Ext.Msg.alert('提示','无数据返回');
					return true;
				}
				if(result.msg!=null && result.msg != ''){
					Ext.MessageBox.alert('后台错误', result.msg);
					return true;
				}
//				if(result.useSign == '1'){
//					Ext.getCmp('addDataItem').setDisabled(true);
//					Ext.getCmp('deleteDataItem').setDisabled(true);
//				}else{
//					Ext.getCmp('addDataItem').setDisabled(false);
//					Ext.getCmp('deleteDataItem').setDisabled(false);
//				}
				if(result.TTaskTemplate != null){ 
					viewPanel.getForm().setValues(result.TTaskTemplate);
					var newValue = timeCombox.getValue();
					if(newValue == '2' || newValue=='3'){
						reportCycleCombox.setReadOnly(true);
						Ext.getCmp('reportCyclePanel').setWidth(271);
					}else{
						reportCycleCombox.setReadOnly(false);
						Ext.getCmp('reportCyclePanel').setWidth(254);
					}
					var value = result.TTaskTemplate.dataType;
					if(value == '1'){
						currDataRadio.setValue(true);
						hisDataRadio.setValue(false);
						
						currDataRadio.setDisabled(false);
						hisDataRadio.setDisabled(true);
					}else if(value =='2'){
						hisDataRadio.setValue(true);
						currDataRadio.setValue(false);
						
						hisDataRadio.setDisabled(false);
						currDataRadio.setDisabled(true);
					}
					itemSelectStore.loadData(result);
				}
			}
		});
	}
	/* 任务名称 */
	var nameComStore = new Ext.data.JsonStore({
		url : "sysman/taskModelMan!queryTaskTemplatesJson.action",
		fields : [ 'templateId', 'templateName' ],
		root : "tTaskTemplateList"
	});
	var nameCombox = new Ext.form.ComboBox({
		store : nameComStore,
		displayField : 'templateName',
		name:'templateName',
		valueField : 'templateId',
		typeAhead : false,
		allowBlank : false,
		mode : 'remote',
		fieldLabel : '任务名称',
		labelSeparator : '',
		forceSelection : false,
		triggerAction : 'all',
		emptyText : '--请选择任务名称--',
		blankText : '--请选择任务名称--',
		selectOnFocus : false,
		anchor:'100%'
	});
	nameCombox.on('select',function(combox){
		if(combox!=null&&combox.getValue()!=''){
			getTempInfo(combox.getValue());
			taskIdText1.setValue(combox.getValue());
		}
	});
	
	var taskNameText = new Ext.form.TextField({
		name : 'templateName1',
		fieldLabel : '任务名称',
		allowBlank : false,
		blankText: '任务名称不能为空',
		labelSeparator : '',
		anchor : '100%'
	});
	taskNameText.on('blur',function(thisfield){
		Ext.Ajax.request({
			url:'sysman/taskModelMan!queryIsExsit.action',
			params:{templateName:taskNameText.getValue()},
			success:function(response) {
				var result = Ext.decode(response.responseText);
				if(result!=null){
					var isExist = result.isExist;
					if(isExist == '1'){
						Ext.getCmp('isExistTemp').setVisible(true);
						return true;
					}else {
						Ext.getCmp('isExistTemp').setVisible(false);
						return true;
					}
				}
			}
		});
	});
	var taskIdText1 = new Ext.form.TextField({
		name : 'templateId',
		fieldLabel : '',
		hidden:true,
		labelSeparator : '',
		anchor : '100%'
	});
	
	var namePanel = new Ext.Panel({
		layout : 'card',
		activeItem : 0,
		border : false,
		colspan: 2,
		items :[{
			xtype:'panel',
			border:false,
			layout:'form',
			items:[nameCombox]
		},{
			xtype:'panel',
			border:false,
			layout:'form',
			items:[taskNameText]
		}]
	});
	
	
	/* 数据类型 */
	var currDataRadio = new Ext.form.Radio({   
       	name : "dataType",
       	inputValue : "1",
//       	id : '1',
      	boxLabel : "实时数据",
      	checked:true
	});
    var hisDataRadio = new Ext.form.Radio({   
       	name : "dataType",
       	inputValue : "2",
//       	id : '2',
      	boxLabel : "历史数据"  
	});
	var dataTypeRg = new Ext.form.RadioGroup({
		anchor : '90%',
		fieldLabel:'',
		labelWidth:1,
		labelSeparator : '',
		border : false,
		items : [currDataRadio,hisDataRadio]
	});
	/* 时间单位 */
	var timeData = [ [ '0', '分' ], [ '2', '日' ], [ '3', '月' ] ];
	var timeComStore = new Ext.data.SimpleStore({
		fields : [ 'value', 'text' ],
		data : timeData
	});
	var timeCombox= new Ext.form.ComboBox({
		store : timeComStore,
		displayField : 'text',
		valueField : 'value',
		editable:false,
		name:'reportCycleUnit',
		mode : 'local',
		fieldLabel : '',
		forceSelection : true,
		triggerAction : 'all',
		value : '0',
		selectOnFocus : true,
		anchor : '100%'
	});
	timeCombox.on('change',function(c,newValue,oldValue){
		if(newValue == '2' || newValue=='3'){
			reportCycleCombox.setValue('1');
			reportCycleCombox.setReadOnly(true);
			Ext.getCmp('reportCyclePanel').setWidth(271);
//			reportCycleCombox.setWidth(145);
		}else{
			reportCycleCombox.setReadOnly(false);
//			reportCycleCombox.setWidth(127);
			Ext.getCmp('reportCyclePanel').setWidth(254);
			reportCycleCombox.setValue('5');
		}
	});
	/* 采集任务周期*/
	var reportCycleData = [[ '5', '5' ], [ '15', '15' ], [ '30', '30' ], [ '60', '60' ]];
	var reportCycleStore = new Ext.data.SimpleStore({
		fields : [ 'value', 'text' ],
		data : reportCycleData
	});
	var reportCycleCombox= new Ext.form.ComboBox({
		store : reportCycleStore,
		fieldLabel : '采集周期',
		allowBlank : false,
		displayField : 'text',
		valueField : 'value',
		name:'reportCycle',
		editable:false,
		mode : 'local',
		forceSelection : true,
		triggerAction : 'all',
		value : '5',
		selectOnFocus : true,
		anchor : '100%'
	});
	
	/* 任务属性 */
	var propoComStore = new Ext.data.JsonStore({
		url : "sysman/taskModelMan!queryTaskPropertyJson.action",
		fields : [ 'taskProperty', 'taskPropertyName' ],
		root : "tTaskPropertyList"
	});
	
	var propoCombox = new Ext.form.ComboBox({
		store : propoComStore,
		name : 'taskProperty',
		displayField : 'taskPropertyName',
		valueField : 'taskProperty',
		typeAhead : true,
		editable:false,
		mode : 'remote',
		fieldLabel : '任务属性',
		labelSeparator : '',
		forceSelection : true,
		triggerAction : 'all',
		emptyText : '--请选择任务属性--',
		blankText : '--请选择任务属性--',
		selectOnFocus : true,
		anchor:'100%',
		allowBlank:false
	});
	
	/* 采集任务周期 */
	var taskNoNumData = [ [ '1', '1' ], [ '2', '2' ], [ '3', '3' ],
			[ '4', '4' ], [ '5', '5' ], [ '6', '6' ], [ '7', '7' ],
			[ '8', '8' ], [ '9', '9' ], [ '10', '10' ], [ '11', '11' ],
			[ '12', '12' ], [ '13', '13' ], [ '14', '14' ], [ '15', '15' ],
			[ '16', '16' ], [ '17', '17' ], [ '18', '18' ], [ '19', '19' ],
			[ '20', '20' ], [ '21', '21' ], [ '22', '22' ], [ '23', '23' ],
			[ '24', '24' ], [ '25', '25' ], [ '26', '26' ], [ '27', '27' ],
			[ '28', '28' ], [ '29', '29' ], [ '30', '30' ], [ '31', '31' ],
			[ '32', '32' ], [ '33', '33' ], [ '34', '34' ], [ '35', '35' ],
			[ '36', '36' ], [ '37', '37' ], [ '38', '38' ], [ '39', '39' ],
			[ '40', '40' ], [ '41', '41' ], [ '42', '42' ], [ '43', '43' ],
			[ '44', '44' ], [ '45', '45' ], [ '46', '46' ], [ '47', '47' ],
			[ '48', '48' ], [ '49', '49' ], [ '50', '50' ], [ '51', '51' ],
			[ '52', '52' ], [ '53', '53' ], [ '54', '54' ], [ '55', '55' ],
			[ '56', '56' ], [ '57', '57' ], [ '58', '58' ], [ '59', '59' ],
			[ '60', '60' ], [ '61', '61' ], [ '62', '62' ], [ '63', '63' ],
			[ '64', '64' ] ];
	var taskNoNumStore = new Ext.data.SimpleStore({
		fields : [ 'taskNovalue', 'taskNotext' ],
		data : taskNoNumData
	});
	var taskNoNumCombox= new Ext.form.ComboBox({
		store : taskNoNumStore,
		fieldLabel : '任务号',
		allowBlank : false,
		displayField : 'taskNotext',
		valueField : 'taskNovalue',
		name:'taskNo',
		editable:false,
		mode : 'local',
		forceSelection : true,
		triggerAction : 'all',
		value : '1',
		selectOnFocus : true,
		anchor : '100%'
	});
	
// var taskNoNumField = new Ext.form.NumberField({
// fieldLabel: '任务号',
// name:'taskNo',
// minValue:1,
// maxValue :64,
// minText:'最小值为1',
// maxText:'最大值为64',
// allowBlank:false,
// emptyText:'填入1-64的数字',
// blankText:'填入1-64的数字',
// labelSeparator : '',
// anchor:'100%'
// });
	var referenceTimeField = new Ext.form.TextField({
		fieldLabel: '采集开始基准时间',
		name:'referenceTime',
		labelSeparator : '',
		validator:function(str){   
				var time = str.match(/^([01][0-9]|2[0-3])([:|：][0-5][0-9]){2}$/);   
				if (time == null) {return false;}   
				if (time [1]>23 || time[3]>59 || time[4]>59){   
				  	return false;
				}   
					return true;   
				},
		emptyText:'时:分:秒',
		invalidText:'请输入“09:09:09”格式的时间数据', 
		allowBlank:false,
		anchor:'100%'
	});
	referenceTimeField.on('change',function(tf,newValue,oldValue){
		if(newValue!=null && newValue!=''){
			var str = (newValue.replace(/：/g,':')==null)?'':newValue.replace(/：/g,':');
			str = str.trim();
			referenceTimeField.setValue(str);
		}else referenceTimeField.setValue('');
	});
	var randomValueNumField = new Ext.form.NumberField({
		fieldLabel: '采集随机最大数',
		name:'randomValue',
		minValue:1,
		maxValue :900,
		minText:'最小值为1',
		maxText:'最大值为900',
		allowBlank:false,
		emptyText:'填入1-900的数字',
		blankText:'填入1-900的数字',
		labelSeparator : '',
		anchor:'100%'
	});
	var randomValueUnitField = new Ext.form.TextField({
		fieldLabel: '',
		readOnly:true,
		value:'秒',
		name:'randomValueUnit',
		anchor:'100%'
	});
//	var tmnlRNumField = new Ext.form.NumberField({
//		fieldLabel: '抽取倍率',
//		name:'tmnlR',
//		allowBlank:false,
//		emptyText:'填入1-96的数字',
//		blankText:'填入1-96的数字',
//		minValue:1,
//		maxValue :96,
//		minText:'最小值为1',
//		maxText:'最大值为96',
//		labelSeparator : '',
//		anchor:'100%'
//	});
	var masterRNumField = new Ext.form.NumberField({
		fieldLabel: '单次采集数据点数',
		name:'masterR',
		allowBlank:false,
		emptyText:'填入1-96的数字',
		blankText:'填入1-96的数字',
		labelSeparator : '',
		anchor:'100%'
	});
	var recallPolicyField = new Ext.form.TextField({
		fieldLabel: '补召策略',
		emptyText : '补召天数1-10,每日补召次数1-10',
		name:'recallPolicy',
		validator:function(str){   
				if(str == '补召天数1-10,每日补召次数1-10' || str == ''){
					return true;
				}
				var time = str.match(/^([1-9]|10),([1-9]|10)$/);   
				if (time == null) {return false;}   
					return true;   
				},
//		regex : /^([1-9]|10),([1-9]|10)$/,
		invalidText : '补召天数1-10,每日补召次数1-10',
		labelSeparator : '',
		anchor:'100%'
	});
//	var isShareCheck = new Ext.form.Checkbox({
//		fieldLabel : '是否共享',
//		name:'isShare',
//		labelSeparator : '',
//		anchor : '40%',
//		inputValue: 1
//	});	
	var existPanle = new Ext.Panel({
		border : false,
		layout : 'form',
		id : 'isExistTemp',
		hidden : true,
		colspan: 1,
		items : [{
			xtype : 'label',
			html:'<font color=#ff0000 style="font-size:12px">&nbsp;&nbsp;' +
					'任务模板已存在</font>'
		}]
	});
	/* 左边的表单 */
	var leftForm = new Ext.Panel({
		defaultType : 'panel',
		labelAlign : 'right',
		labelWidth : 120,
		monitorResize : true,
		layout : 'table',
		border : false,
		bodyStyle : 'padding:5px 5px 0px 5px',
		layoutConfig : { columns : 3 },
		defaults : { width : 320, height : 41 , colspan:2},
		items : [{
			border : false,
			layout : 'form',
			colspan: 3,
			items : [taskIdText1]
		},namePanel,existPanle,{
			border:false,
			layout:'form',
			items:[propoCombox]
		},{
			border : false,
			layout : 'form',
			colspan: 1,
			items : [{
				xtype : 'label',
				html:'<font color=#ff0000></font>'
			}]
		},{
			border:false,
			layout:'form',
			items:[taskNoNumCombox]
		},{
			border : false,
			layout : 'form',
			colspan: 1,
			items : [{
				xtype : 'label',
				html:'<font color=#ff0000></font>'
			}]
		},{
			border : false,
			layout : 'form',
			items : [referenceTimeField]
		},{
			border : false,
			layout : 'form',
			colspan: 1,
			items : [{
				xtype : 'label',
				html:'<font color=#ff0000></font>'
			}]
		},{
			border:false,
			layout:'form',
			colspan:1,
			width:253,
			items:[randomValueNumField]
		},{
			border:false,
			layout:'form',
			colspan:1,
			width:50,
			labelWidth:1,
			items:[randomValueUnitField]
		},{
			border : false,
			layout : 'form',
			colspan: 1,
			items : [{
				xtype : 'label',
				html:'<font color=#ff0000></font>'
			}]
		},{
			border:false,
			id : 'reportCyclePanel',
			layout:'form',
			width:253,
			colspan:1,
			items:[reportCycleCombox]
		},{
			border : false,
			layout : 'form',
			width:50,
			colspan:1,
			labelWidth : 1,
			items : [timeCombox]
		},{
			border : false,
			layout : 'form',
			colspan: 1,
			items : [{
				xtype : 'label',
				html:'<font color=#ff0000></font>'
			}]
		},{
			border:false,
			layout:'form',
			items:[masterRNumField]
		},{
			border : false,
			layout : 'form',
			colspan: 1,
			items : [{
				xtype : 'label',
				html:'<font color=#ff0000></font>'
			}]
		},{
			border:false,
			layout:'form',
			items:[recallPolicyField]
		},{
			border : false,
			layout : 'form',
			colspan: 1,
			items : [{
				xtype : 'label',
				html:'<font color=#ff0000></font>'
			}]
		}
//		,{
//			border:false,	
//			layout:'form',
//			colspan:2,
//			items:[isShareCheck]
//		}
		]
	});
	
	/* 窗口树-------------------------------------------------------------------------------*/
	var dataItemRootNode = new Ext.tree.AsyncTreeNode({
				id : 'root',
				text : '规约项'
			});
	var dataItemTreeLoader = new Ext.tree.TreeLoader({
				autoScroll : true,
				clearOnLoad : true,
				dataUrl : 'baseapp/dataFetch!queryTreeNode.action'
			});
	dataItemTreeLoader.on("beforeload", function(treeLoader, node){
		if(dataTypeRg.getValue()!=null){
			treeLoader.baseParams.queryType = dataTypeRg.getValue().getRawValue();
		}else return true;
    });
//    dataItemTreeLoader.on('load',function(){
//    	dataItemRootNode.expand(true);
//    });
    
	var dataItemsTree = new Ext.tree.TreePanel({
				width : 330,
				height : 370,
				border : true,
				autoScroll : true,
//				rootVisible : false,
				frame : false,
				animate : false,
				root : dataItemRootNode,
				loader : dataItemTreeLoader
			});
	dataItemsTree.on('dblclick', function(node) {
				var obj = new Object();
				obj.protocolName = node.text;
				obj.protocolNo = node.id;
				
				var records = new Ext.data.Record(obj); 
				if(!node.isLeaf()){
					return;
				}
				if(node.parentNode.attributes.text == '规约项'){
					Ext.MessageBox.alert('提示','该类型无数据项，请选择其他数据项');
					return true;
				}
				if(itemSelectStore.query('protocolNo',obj.protocolNo,false).getCount() > 0){
					Ext.MessageBox.alert('提示','该数据项已经添加');
				}else{
					itemSelectStore.add(records);
				}
			});
//	dataItemsTree.on('click', function(node) {
//				alert('id' + " "+node.id);
//				alert('parentText' + " "+node.parentNode.attributes.text);
//			});
			
	/*数据项Panel----------------------------------------------------------------*/
	var itemSelectStore = new Ext.data.JsonStore({
		proxy : new Ext.data.MemoryProxy(),
		fields : [ 'protocolNo', 'protocolName','dataType' ],
		root : "BClearProtocolList"
	});
	var selectPanel = new Ext.Panel({
		title:'数据项',
		buttonAlign:'center',
		monitorResize:true,
		border:false,
		lableWidth:1,
		width: 300,
//		height: 320,
		layout:'fit',
		items:[new Ext.ux.form.MultiSelect({
				fieldLabel: '',
				labelSeparator : '',
				id:'taskData',
				name: 'taskData',
				displayField : 'protocolName',
				valueField : 'protocolNo',
				hiddenName : 'name',
				width: 300,
				height: 300,
				allowBlank:true,
				store: itemSelectStore,
				ddReorder: false
		})],
		buttons: [{
				text: '添加数据项',
				id:'addDataItem',
				name:'addDataItem',
				handler: function(){
//					var templateId = nameCombox.getValue();
//					if(templateId == null || templateId == ''){
//						Ext.Msg.alert('提示','请选择需要添加数据项的模板');
//						return false;
//					}
					itemTreeWindow.show();
					dataItemRootNode.removeAll();
					dataItemTreeLoader.load(dataItemRootNode);
				}
		},{
				text: '删除数据项',
				id:'deleteDataItem',
				name:'deleteDataItem',
				handler:function(){
					var mSelect = Ext.getCmp('taskData');
					if(mSelect.view.getSelectedIndexes().length == 0){
						Ext.Msg.alert('提示','请选择要删除的数据项！');
						return;
					}
					while(mSelect.view.getSelectedIndexes().length > 0){
						var selectionsArray = mSelect.view.getSelectedIndexes();
						var record = itemSelectStore.getAt(selectionsArray[selectionsArray.length-1]);
						itemSelectStore.removeAt(selectionsArray[selectionsArray.length-1]);
					}
				}
		}]
	});
	itemSelectStore.on('add',function(thisstore){
		if(thisstore.getCount()==0){
			hisDataRadio.setDisabled(false);
			currDataRadio.setDisabled(false);
		}else{
			var rvalue = dataTypeRg.getValue().getRawValue();
			if(rvalue == '1'){
				currDataRadio.setDisabled(false);
				hisDataRadio.setDisabled(true);
			}else if(rvalue == '2'){
				hisDataRadio.setDisabled(false);
				currDataRadio.setDisabled(true);
			}
		}
	});
	itemSelectStore.on('remove',function(thisstore){
		if(thisstore.getCount()==0){
			hisDataRadio.setDisabled(false);
			currDataRadio.setDisabled(false);
		}
	});
	
	/*radioGroup和数据项Panel----------------------------------------------------------------*/
	var rightForm = new Ext.Panel({
			bodyStyle: 'padding:10px 10px 0px 0px',
			border:false,
			lableWidth:1,
			layout:'form',
			items:[{
				xtype:'panel',
				border:false,
				width : 450,
				layout:'fit',
				items:[dataTypeRg]
			},selectPanel]
		});
		
	/*按钮控件-----------------------------------------------------------------------------*/
	var bottPanel = new Ext.Panel({
		layout:'column',
		border:true,
		height:410,
		region:'north',
		monitorResize:true,
		items:[{
			columnWidth:.55,
			border:false,
			items:[leftForm]
		},{
			columnWidth:.45,
			border:false,
			items:[rightForm]
		}]
	});
	
	/*按钮控件-----------------------------------------------------------------------------*/
	var buttonPanel = new Ext.Panel({
		layout : 'card',
		region:'north',
		activeItem : 0,
		height : 40,
		border : false,
		items : [{
			xtype : 'panel',
			buttonAlign : 'center',
			border : false,
			buttons : [{
				text : '添加',
				handler : function () {
//					Ext.getCmp('addDataItem').setDisabled(false);
//					Ext.getCmp('deleteDataItem').setDisabled(false);
					viewPanel.getForm().reset();	//清空表单数据
					itemSelectStore.removeAll();	//清空数据项列表
					namePanel.layout.setActiveItem(1);
					buttonPanel.layout.setActiveItem(1);
					currDataRadio.setDisabled(false);
					hisDataRadio.setDisabled(false);
					reportCycleCombox.setReadOnly(false);
					Ext.getCmp('reportCyclePanel').setWidth(254);
//					Ext.getCmp('addDataItem').setDisabled(true);
//					Ext.getCmp('deleteDataItem').setDisabled(true);
					dataTypeRg.setValue('1');
					existPanle.setVisible(false);
				}
			},{
				text : '注销',
				handler:function(){
					var templateId = nameCombox.getValue();
					if(templateId == null || templateId == ''){
						Ext.Msg.alert('提示','请选择要注销的模板');
						return false;
					}
					Ext.MessageBox.confirm('注销', '是否注销该模板！', function(btn){
						if(btn == 'no'){
							return true;
						}else{
							Ext.Ajax.request({
								url:'sysman/taskModelMan!cancelTaskTemplate.action',
								params:{templateId:nameCombox.getValue(),templateName1:nameCombox.getRawValue()},
								success:function(response) {
									var result = Ext.decode(response.responseText);
									if(result && result.msg!=null && result.msg == '前置集群通信中断'){
										Ext.Msg.alert('提示', result.msg);
									}
									if (result && result.success) {
										Ext.MessageBox.alert('提示', result.msg);
										viewPanel.getForm().reset();
										nameComStore.reload();
									}
								}
							});
						}
					});
				}
			},{
				text : '保存',
				handler:saveInfo
			}]
		},{
			xtype : 'panel',
			buttonAlign : 'center',
			border : false,
			buttons : [{
				text : '确定',
				handler : function () {
					var reportCycleUnit1 = timeCombox.getValue();
					var length = itemSelectStore.getCount();
					var itemStr = '';
					for(var i=0;i<length;i++){
						if(i==length-1){
							itemStr = itemStr + itemSelectStore.getAt(i).get('protocolNo');
						}else{
							itemStr = itemStr + itemSelectStore.getAt(i).get('protocolNo')+',';
						}
					}
					
					nameCombox.setValue("1");	//隐藏的任务名称combobox赋值，避免表单校验失败
					if(viewPanel.getForm().isValid()) { // 表单验证
						viewPanel.getForm().submit({ // 提交表单
							waitMsg : '正在保存新建的模板……', // 等待字符串
							waitTitle : '请稍后...',
							method : 'post',
							url:'sysman/taskModelMan!saveTaskTemplate.action',
							params:{reportCycleUnit1:reportCycleUnit1,taskProperty1:propoCombox.getValue(),itemStr:itemStr},
							success : function(form, action) { // 返回成功
								if(action && action.result && action.result.msg != null){
									Ext.Msg.alert('提示',action.result.msg);
									return true;
								}else{
//									setTimeout(function() {
//										Ext.Msg.alert("提示","保存成功");
//										nameComStore.reload();
//									}, 5010);
									Ext.Msg.alert("提示","保存成功");
									nameComStore.reload();
								}
							}
						});
					}else{
						Ext.Msg.alert('提示','模板信息未填写正确');
						return true;
					}
				}
			},{
				text : '返回',
				handler : function () {
					namePanel.layout.setActiveItem(0);
					buttonPanel.layout.setActiveItem(0);
					viewPanel.getForm().reset();
					existPanle.setVisible(false);
//					Ext.getCmp('addDataItem').setDisabled(false);
//					Ext.getCmp('deleteDataItem').setDisabled(false);
				}
			}]
		}]
	});
	/*保存系统用户修改信息---------------------------------------------------------------------*/
	function saveInfo() {
		var length = itemSelectStore.getCount();
		var itemStr = '';
		for(var i=0;i<length;i++){
			if(i==length-1){
				itemStr = itemStr + itemSelectStore.getAt(i).get('protocolNo');
			}else{
				itemStr = itemStr + itemSelectStore.getAt(i).get('protocolNo')+',';
			}
		}
		taskNameText.setValue('1');		//隐藏的任务名称textfield赋值，避免表单校验失败
		if(viewPanel.getForm().isValid()){
			var taskNewName = nameCombox.getRawValue();
			viewPanel.getForm().submit({
				waitTitle : '请稍后...',
				waitMsg : '正在保存修改的模板……',
				method : 'post',
				url:'./sysman/taskModelMan!updateTaskTemplate.action',
				params:{itemStr:itemStr,taskProperty1:propoCombox.getValue(),
						reportCycleUnit1:timeCombox.getValue()},
				success:function(form, action) {
					if(action!=null && action.result!=null){
						if(action.result.msg != null && action.result.msg != ''){
							Ext.MessageBox.alert('提示',action.result.msg);
							return true;
						}else {
							Ext.MessageBox.alert('提示','保存成功');
							nameComStore.reload();
							nameCombox.setValue(taskIdText1.getValue());
							nameCombox.setRawValue(taskNewName);
						}
					}
				}
			});
		}else{
			Ext.Msg.alert('信息', '请填写正确再提交!');
		}
	}
			
	/*弹出窗口-------------------------------------------------------------------------------*/
	var itemTreeWindow = new Ext.Window({
		title:'添加数据项',
		frame:true,
		width:350,
		height:450,
		layout:"form",
		modal:true,
		plain:true,//设置背景颜色
		resizable:false,//不可移动
		bodyStyle:"padding:2px",
		buttonAlign:"center",//按钮的位置
		closeAction:"hide",//将窗体隐藏而并不销毁
		items:[dataItemsTree],
		buttons:[{
			text:'添加',
			handler:function(){
				var obj = new Object();
				if(dataItemsTree.getSelectionModel().getSelectedNode()==null){
					Ext.MessageBox.alert('提示','请选择要添加的数据项！');
					return;
				}
				var node = dataItemsTree.getSelectionModel().getSelectedNode();
				var parentNode = dataItemsTree.getSelectionModel().getSelectedNode().parentNode;
				if (parentNode.id == 'root') {
					Ext.MessageBox.alert('出错', '不允许添加数据项类型！');
					return;
				}
				obj.protocolName = dataItemsTree.getSelectionModel().getSelectedNode().text;
				obj.protocolNo = dataItemsTree.getSelectionModel().getSelectedNode().id;
				
				var records = new Ext.data.Record(obj); 
				if(itemSelectStore.query('protocolNo',obj.protocolNo,false).getCount() > 0){
					Ext.MessageBox.alert('提示','该数据项已经添加');
				}else
					itemSelectStore.add(records);
			}
		},{
			text:'关闭',
			handler:function(){
				itemTreeWindow.hide();
			}
		}]
	});
			
	/*总视图---------------------------------------------------------------------------------*/		
	var viewPanel = new Ext.form.FormPanel({
		layout : 'border',
		border:false,
		bodyStyle : 'padding : 2px',
		monitorResize : true,
		items : [ bottPanel, {
				xtype:'panel',
				region:'center',
				layout:'border',
				border:false,
				items:[buttonPanel,{
									xtype:'panel',
									region:'center',
									border:false,
									items:[]
							}]
			}]
	});
	renderModel(viewPanel,'任务模板管理');
});