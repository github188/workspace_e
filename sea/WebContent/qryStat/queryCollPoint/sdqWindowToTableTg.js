function randomColor() {
	return '' + (Math.random() * 0xffffff << 0).toString(16);
};
function getgrid(tgid,dataDate){
//alert(dataDate);
//alert(tgid);
groupStoreBy3D.baseParams = {
            CurDate : dataDate,
            TG_ID : tgid
        };
        groupStoreBy3D.load({
                    params : {
                        start : 0,
                        limit : DEFAULT_PAGE_SIZE
                    }
                });


}
function SDQXMLData2(dateData) {
   var xmlData = "<chart caption='' showValues='0' PYAxisName='Sales' SYAxisName='Total Downloads'>";
    var str = "<categories>";
    Ext.each(dateData,function(obj){

 

        str += "<category label='"+obj['dataDate'].substring(5)+"' />";
    });
    str += "</categories>";
    
    var str1 = "<dataset seriesName='总表电量' anchorBgColor='1D8BD1'>";
    Ext.each(dateData,function(obj){
        str1 += "<set value='"+obj['papEz']+"'link='javaScript:onclick=getgrid("
                            + sendDataQueryConId + ",\""
                            + obj['dataDate'] + "\")'/>";
        //i++;
    });
    str1 += "</dataset>";
    
   var str2 = "<dataset seriesName='分表电量' anchorBgColor='F1683C'>";
    Ext.each(dateData,function(obj){
        str2 += "<set value='"+obj['papEl']+"' />";
       // i++;
    });
    str2 += "</dataset>"; 
    
    var str3 = "<dataset seriesName='损失电量' parentYAxis='S'>";
    Ext.each(dateData,function(obj){
        str3 += "<set value='"+obj['papEf']+"' />";
       // i++;
    });
    str3 += "</dataset>";  
    xmlData = xmlData+str+str1+str2+str3+"<trendlines><line startValue='300' color='91C728' displayValue='Target' showOnTop='1'/></trendlines></chart>";
    //alert(xmlData);
    return xmlData;
};


function getSDQMessageF() {
    var sendDataQueryConsNO = Ext.getCmp("sendDataQueryConsNO").getValue();
    DateStart = Ext.getCmp("sendDataQueryDateStartNext2").getValue()
            .format('Y-m-d');
    DateStartX2 = Ext.getCmp("sendDataQueryDateStartNext2").getValue();
    DateEndX2 = Ext.getCmp("sendDataQueryDateEndNext2").getValue();
    DateEnd = Ext.getCmp("sendDataQueryDateEndNext2").getValue().format('Y-m-d');
    Ext.Ajax.request({
                url : 'qrystat/sendDataQueryAction!querySendDataQueryF.action',
                params : {
                    CONS_NO : sendDataQueryConsNO,
                    TG_ID : sendDataQueryConId,
                    DateStart : DateStart,
                    DateEnd : DateEnd
                },
                success : function(response) {
                    var result = Ext.decode(response.responseText).SDQListF;
                    var mydays = (DateEndX2 - DateStartX2) / (1000 * 24 * 3600)
                            + 1;
                    if (mydays > 15 || mydays <= 0) {
                        alert("您选择的日期范围不合理,建议查询范围半个月之间...");
                    } else {
                        var XMLstr2 = SDQXMLData2(result);
                        // alert(XMLstr);
                        fpanel3Chart.changeDataXML(XMLstr2);
                    }
                    addStr = '';
                    deleteStr = '';

                }
            });
}
// 3d图形界面下面的gird--------------------------------------------------------------------------------
var groupStoreBy3D =  new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
                    url : 'qrystat/sendDataQueryAction!querySendDataQueryD.action'
                }),
                reader : new Ext.data.JsonReader({
                    root : 'SDQList',
                    totalProperty : 'totalCount'
                }, ['CONS_ID', 'CONS_NO', 'CONS_NAME', 'ELEC_ADDR',
                    'RUN_CAP', 'TMNL_ASSET_NO', 'ASSET_NO', 'DATA_DATE',
                    'PAP_E'])
    });

var CMBy3D = new Ext.grid.ColumnModel([{
			dataIndex : 'CONS_ID',
			header : '客户ID',
			sortable : true,
			align : 'center'
		}, {
			dataIndex : 'CONS_NO',
			header : '用户编号',
			sortable : true,
			align : 'center'
		}, {
			dataIndex : 'CONS_NAME',
			header : '用户名称',
			width : 120,
			sortable : true,
			align : 'center'
		}, {
			header : '用电地址',
			dataIndex : 'ELEC_ADDR',
			sortable : true,
			align : 'center'
		}, {
			header : '运行容量',
			dataIndex : 'RUN_CAP',
			sortable : true,
			align : 'center'
		}, {
			header : '终端资产号',
			dataIndex : 'TMNL_ASSET_NO',
			sortable : true,
			align : 'center'
		}, {
			header : '表计资产',
			dataIndex : 'ASSET_NO',
			sortable : true,
			align : 'center'
		}, {
			header : '抄表日期',
			dataIndex : 'DATA_DATE',
			sortable : true,
			align : 'center'
		}, {
			header : '有功电量',
			dataIndex : 'PAP_E',
			sortable : true,
			align : 'center'
		}]);
var gridBy3D = new Ext.grid.GridPanel({
			title : '居民未抄户',
			region : 'center',
			autoScroll : true,
			stripeRows : true,
			viewConfig : {
				forceFit : false
			},
			cm : CMBy3D,
			store : groupStoreBy3D,
			bbar : new Ext.ux.MyToolbar({
						store : groupStoreBy3D
					})
		});
// 抄表数据查询柱图fpanel
		var fpanel3Chart = new Ext.fc.FusionChart({
			wmode : 'transparent',
			backgroundColor : '000000',
			url : 'fusionCharts/MSColumn3DLineDY.swf',
			DataXML : "./qryStat/queryCollPoint/fDate3.xml"
		});

var fpanel3 = new Ext.Panel({
			//height : 250,
			monitorResize : true,
			region : 'center',
			items : [fpanel3Chart]
		});
// --------------------------------------------------------------------------
// sendDataQuerywindow--------------------------
var leftPanelIn = new Ext.Panel({
			title : '台区线损',
			plain : true,
			border : false,
			buttonAlign : 'center',
			buttons : [{
						xtype : 'button',
						text : "查询",
						width : 50,
						listeners : {
							"click" : function() {
								 getSDQMessageF();
							}
						}
					}],
			items : [{
						layout : 'form',
						buttonAlign : 'center',
						plain : true,
						border : false,
						labelWidth : 80,
						labelAlign : 'right',
						defaultType : "textfield",
						style : "padding-top:5px",
						items : [{
									fieldLabel : "总表电量",
									labelSeparator : "",
									width : 100
								}, {
									fieldLabel : "分表电量",
									labelSeparator : "",
									width : 100
								}, {
									fieldLabel : "损失电量",
									labelSeparator : "",
									width : 100
								}, {
									fieldLabel : "损失率",
									labelSeparator : "",
									width : 100
								}, {
									xtype : "datefield",
									format : "Y-m-d",
									value : new Date(),
									fieldLabel : "分析日期",
									width : 100,
									labelSeparator : "",
									id : 'sendDataQueryDateStartNext2',
									value : new Date().add(Date.DAY, -2)
								}, {
									xtype : "datefield",
									format : "Y-m-d",
									value : new Date(),
									fieldLabel : "到",
									width : 100,
									labelSeparator : "",
									id : 'sendDataQueryDateEndNext2',
									value : new Date().add(Date.DAY, -1)
								}]
					}]
		});
var leftPanel = new Ext.Panel({
			region : 'west',
			width : 205,
			plain : true,
			border : false,
			labelWidth : 50,
			// baseCls : "x-plain",
			items : [leftPanelIn]
		});
var SDQpk2Panel = new Ext.Panel({
			layout : 'border',
			plain : true,
			border : false,
			height : 240,
			baseCls : "x-plain",
			items : [leftPanel, fpanel3]
		});
var SDQpkPanel = new Ext.Panel({
			region : 'north',
			height : 250,
			plain : true,
			border : false,
			items : [SDQpk2Panel]
		});
// 设置顶层的抄表数据查询panel
	var sendDataQueryTableTgpanel = new Ext.Panel({
				autoScroll : true,
				border : false,
				layout : "border",
				items : [SDQpkPanel, gridBy3D]
			});
	renderModel(sendDataQueryTableTgpanel, '台区损耗分析');
	Ext.getCmp("sendDataQueryDateStartNext2").setValue(Ext
				.getCmp("sendDataQueryDateStart").getValue());
		Ext.getCmp("sendDataQueryDateEndNext2").setValue(Ext
				.getCmp("sendDataQueryDateEnd").getValue());
	getSDQMessageF();
	//fChart3.render(fpanel3.getId());// 渲染
