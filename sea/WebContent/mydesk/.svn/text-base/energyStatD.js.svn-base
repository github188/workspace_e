Ext.onReady(function() {
	
	var cm = new Ext.grid.ColumnModel([
		{
			header : '全省关口电量',
			sortable : true,
			align : 'center',
			dataIndex : 'orgName',
			width : 150,
			renderer:function(val){
				var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val
				+ '">' + val + '</div>';
				return html;
			}
		},{
			header : '电量(万kWh)',
			sortable : true,
			width : 150,
			align : 'center',
			dataIndex : 'dataValue',
			renderer:function(val){
				val = val / 10000;
				var html = '<div align = "left" ext:qtitle="电量(万kWh)" ext:qtip="' 
				+ Ext.util.Format.number(val,'0,000.0000')
				+ '">' 
				+ Ext.util.Format.number(val,'0,000.0000') + '</div>';
				return html;
			}
		}, {
			header : '供电单位编码',
			sortable : true,
			align : 'center',
			dataIndex : 'orgNo',
			hidden : true,
			width : 130,
			renderer:function(val){
				var html = '<div align = "left" ext:qtitle="供电单位编码" ext:qtip="' + val
				+ '">' + val + '</div>';
				return html;
			}
		}
	]);
	
	var store = new Ext.data.Store({
//				autoLoad : true,
				url : 'mydesk/energyStatDay!queryEnergyStatDay.action',
				params : {
					startDate : new Date().add(Date.DAY, -1),
					endDate :  new Date()
				},
				reader : new Ext.data.JsonReader({
							root : 'energyStatDList'
						}, [{
									name : 'orgNo'
								}, {
									name : 'orgName'
								}, {
									name : 'dataValue'
//								}, {
//									name : 'cons_name'
//								}, {
//									name : 'org_name'
								}])
			});
	
		var grid = new Ext.grid.GridPanel({
		cm:cm,
		height:290,
		store:store,
		monitorResize : true,
		autoScroll : true,
		viewConfig:{
			forceFit: false
		}
	})
	store.load({
				params : {
					startDate : new Date().add(Date.DAY, -1),
					endDate : new Date()
				}
			});
	renderModel(grid, '5');
	
/*	

	//生成图片
	var energyStatDayPanel = new Ext.fc.FusionChart({
						border : false,
						title : '',
						wmode : 'transparent',
						backgroundColor : '000000',
						url : 'fusionCharts/MSColumn2D.swf',
						DataXML : ""
					});

			var category = '<categories>';
			var dataset = '<dataset seriesName="上网电量" color="AFD8F8" showValues="0">';
			var dataset1 = '<dataset seriesName="供电量" color="F6BD0F" showValues="0">';
			var dataset2 = '<dataset seriesName="售电量" color="8BBA00" showValues="0">';
			var dataset3 = '<dataset seriesName="互供电量" color="C8A1D1" showValues="0">';

			Ext.Ajax.request({
						url : 'mydesk/energyStatDay!queryEnergyStatDay.action',
						params : {
							startDate : new Date().add(Date.DAY, -1),
							endDate : new Date()
						},
						success : function(response) {
							var result = Ext.decode(response.responseText);
							if (result == null)
								return true;
							var energyStatDList = result.energyStatDList;
							
							if(null == energyStatDList)
								return true;

							var tempUpEnergy = 0;
							var tempMutEnergy = 0;
							var tempSpq = 0;
							var tempMutEnergy = 0;
							var maxValue = -1;
							for (var i = 0; i < energyStatDList.length; i++) {
								category += '<category label="'
										+ energyStatDList[i].orgName + '" />';
								tempUpEnergy = energyStatDList[i].upEnergy;
								if (tempUpEnergy == 0) {
									dataset += '<set value="' + tempUpEnergy
											+ '"  showvalues="0" /> ';
								} else {
									tempUpEnergy = tempUpEnergy / 10000;
									dataset += '<set value="' + tempUpEnergy
											+ '"  showvalues="0" /> ';
									if (maxValue < tempUpEnergy) {
										maxValue = tempUpEnergy;
									}
								}

								tempPpq = energyStatDList[i].ppq;
								if (energyStatDList[i].ppq == 0) {
									dataset1 += '<set value="' + tempPpq
											+ '" showvalues="0" /> ';

								} else {
									tempPpq = tempPpq / 10000;
									dataset1 += '<set value="' + tempPpq
											+ '"  showvalues="0" /> ';
									if (maxValue < tempPpq) {
										maxValue = tempPpq;
									}
								}

								tempSpq = energyStatDList[i].spq;
								if (energyStatDList[i].spq == 0) {
									dataset2 += '<set value="' + tempSpq
											+ '" showvalues="0" /> ';

								} else {
									tempSpq = tempSpq / 10000;
									dataset2 += '<set value="' + tempSpq
											+ '"  showvalues="0" /> ';
									if (maxValue < tempSpq) {
										maxValue = tempSpq;
									}
								}

								tempPpq = energyStatDList[i].mutEnergy;
								if (energyStatDList[i].mutEnergy == 0) {
									dataset3 += '<set value="' + tempMutEnergy
											+ '" showvalues="0" /> ';

								} else {
									tempMutEnergy = tempMutEnergy / 10000;
									dataset3 += '<set value="' + tempMutEnergy
											+ '"  showvalues="0" /> ';
									if (maxValue < tempMutEnergy) {
										maxValue = tempMutEnergy;
									}
								}
							}
							if (maxValue != 0) {
								maxValue = (Math.floor(maxValue * 1.2 / 1000))
										* 1000
							}

							var energyXmlData = ' numberprefix=""'
									+ ' xaxisName=""' + ' useRoundEdges="1"'
									//+ 'numberScaleValue="10000"  numberScaleUnit="万KW"' 
									+ ' showValues="0"'
									+ ' legendBorderAlpha="0"'
									+ ' baseFont="宋体" ' + ' showvalues="0" '
									+ ' yAxisMaxValue="' + maxValue + '"'
									+ ' formatNumberScale="0"'
									+ ' baseFontSize="12"> ';
							category += '</categories>';
							dataset += '</dataset>';
							dataset1 += '</dataset>';
							dataset2 += '</dataset>';
							dataset3 += '</dataset>';
							energyXmlData = energyXmlData + category + dataset
									+ dataset1 + dataset2 + dataset3
									+ '</chart>';

							energyXmlData = '<chart palette="2" caption="" '
									+ energyXmlData;
							energyStatDayPanel.setDataXML(energyXmlData);
							renderModel(energyStatDayPanel, '5');
						},
						failure : function() {
							Ext.MessageBox.alert("提示", "失败");
							return;
						}
					});
					
					*/
		});
