/**
 * @描述: 返回曲线，针对采集数据综合分析页面测量点曲线(合并一类二类曲线)，针对实时功率曲线
 * @author 姜炜超
 */
function  getGAPowerMltXMLData(generalPowerModelList,
		     generalPowerData,contrastPowerData,
             generalAPowerData,contrastAPowerData,
             generalBPowerData,contrastBPowerData,
	         generalCPowerData,contrastCPowerData,
	         mpDayRealPowerList,mpDayRealPowerConList,
	         generalPowerName,contrastPowerName,
	         generalAPowerName,contrastAPowerName,
	         generalBPowerName,contrastBPowerName,
	         generalCPowerName,contrastCPowerName,
	         mpDayRealPowerName,mpDayRealPowerConName,step,publicName, yAxisMaxValue, yAxisMinValue){
	
	var xmlData = "<chart caption='" + publicName + "' legendPosition='BOTTUM' lineThickness='1' showValues='0' formatNumberScale='0'" +
	    " anchorRadius='1' divLineAlpha='10' divLineColor='CC3300' " +
	    "divLineIsDashed='1' showAlternateHGridColor='1' " +
	    "alternateHGridAlpha='5' alternateHGridColor='CC3300'" +
	    " shadowAlpha='40' labelStep='" + step + "' numvdivlines='22' " +
	    "chartRightMargin='35' bgColor='FFFFFF,CC3300' " +
	    "bgAngle='270' bgAlpha='10,10' numberSuffix='kW' yAxisMinValue='" + yAxisMinValue + "' yAxisMaxValue='" + yAxisMaxValue + "' >";
	    //"bgAngle='270' bgAlpha='10,10' yAxisMinValue='" + yAxisMinValue + "' yAxisMaxValue='" + yAxisMaxValue + "' connectNullData='1' lineDashGap='6'>";

    var str = "<categories>";
    Ext.each(generalPowerModelList,function(obj){
        str += "<category label='"+obj['time']+"' />";
    });
    str += "</categories>";

    xmlData = xmlData+str;

    if(null != generalPowerName && 0 < generalPowerName.length){
        var str1 = "<dataset seriesName='查询日冻结功率'"+" color='1D8BD1' anchorBorderColor='1D8BD1' anchorBgColor='1D8BD1'>";
        Ext.each(generalPowerData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalAPowerName && 0 < generalAPowerName.length){
        var str1 = "<dataset seriesName='查询日冻结A相功率'"+" color='F5A901' anchorBorderColor='F5A901' anchorBgColor='F5A901'>";
        Ext.each(generalAPowerData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalBPowerName && 0 < generalBPowerName.length){
        var str1 = "<dataset seriesName='查询日冻结B相功率'"+" color='0025A5' anchorBorderColor='0025A5' anchorBgColor='0025A5'>";
        Ext.each(generalBPowerData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalCPowerName && 0 < generalCPowerName.length){
        var str1 = "<dataset seriesName='查询日冻结C相功率'"+" color='FF8B71' anchorBorderColor='FF8B71' anchorBgColor='FF8B71'>";
        Ext.each(generalCPowerData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastPowerName && 0 < contrastPowerName.length){
        var str1 = "<dataset seriesName='对比日冻结功率'"+" color='F1683C' anchorBorderColor='F1683C' anchorBgColor='F1683C'>";
        Ext.each(contrastPowerData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";
        xmlData = xmlData+str1;
	    
    }
    
    if(null != contrastAPowerName && 0 < contrastAPowerName.length){
        var str1 = "<dataset seriesName='对比日冻结A相功率'"+" color='FAEEB5' anchorBorderColor='FAEEB5' anchorBgColor='FAEEB5'>";
        Ext.each(contrastAPowerData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";
        xmlData = xmlData+str1;
	    
    }
    
    if(null != contrastBPowerName && 0 < contrastBPowerName.length){
        var str1 = "<dataset seriesName='对比日冻结B相功率'"+" color='ABEEBF' anchorBorderColor='ABEEBF' anchorBgColor='ABEEBF'>";
        Ext.each(contrastBPowerData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";
        xmlData = xmlData+str1;
	    
    }
    
    if(null != contrastCPowerName && 0 < contrastCPowerName.length){
        var str1 = "<dataset seriesName='对比日冻结C相功率'"+" color='DEE8EF' anchorBorderColor='DEE8EF' anchorBgColor='DEE8EF'>";
        Ext.each(contrastCPowerData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";
        xmlData = xmlData+str1;
    }
    
    if(null != mpDayRealPowerName && 0 < mpDayRealPowerName.length){
        var str1 = "<dataset seriesName='查询日实时功率'"+" color='58539B' anchorBorderColor='58539B' anchorBgColor='58539B'>";
//        var i = 0;
//        Ext.each(mpDayRealPowerList,function(obj){
//        	if(obj['flag']){
//        		if(i == 0){
//        			str1 += "<set />";
//        			i = i + 1;
//        		}else{
//                    str1 = str1.substring(0, str1.length-2)+" dashed='1' /> <set/>";
//        		}
//        	}else{
//        		str1 += "<set value='"+obj['power']+"'/>";
//        	}
//         });
        Ext.each(mpDayRealPowerList,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['power']+"'/>";
        	}
         });
        str1 += "</dataset>";
        	
	    var str2 = "<dataset seriesName='查询日实时A相功率' color='FFFF00' anchorBorderColor='FFFF00' anchorBgColor='FFFF00'>";
	    Ext.each(mpDayRealPowerList,function(obj){
	    	if(obj['flagA']){
                str2 += "<set />";
        	}else{
        		str2 += "<set value='"+obj['powerA']+"'/>";
        	}
	    });
	    str2 += "</dataset>";
	    
	    var str3 = "<dataset seriesName='查询日实时B相功率' color='00FF00' anchorBorderColor='00FF00' anchorBgColor='00FF00'>";
	    Ext.each(mpDayRealPowerList,function(obj){
	    	if(obj['flagB']){
                str3 += "<set />";
        	}else{
        		str3 += "<set value='"+obj['powerB']+"'/>";
        	}
	    });
	    str3 += "</dataset>";
	    
	    var str4 = "<dataset seriesName='查询日实时C相功率' color='FF0000' anchorBorderColor='FF0000' anchorBgColor='FF0000'>";
	    Ext.each(mpDayRealPowerList,function(obj){
	    	if(obj['flagC']){
                str4 += "<set />";
        	}else{
        		str4 += "<set value='"+obj['powerC']+"'/>";
        	}
	    });
	    str4 += "</dataset>";
	    
	    xmlData = xmlData+str1+str2+str3+str4;
    }
    
    if(null != mpDayRealPowerConName && 0 < mpDayRealPowerConName.length){
        var str1 = "<dataset seriesName='对比日实时功率'"+" color='62428F' anchorBorderColor='62428F' anchorBgColor='62428F'>";
        Ext.each(mpDayRealPowerConList,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['power']+"'/>";
        	}
         });
        str1 += "</dataset>";
        	
	    var str2 = "<dataset seriesName='对比日实时A相功率' color='EA8908' anchorBorderColor='EA8908' anchorBgColor='EA8908'>";
	    Ext.each(mpDayRealPowerConList,function(obj){
	    	if(obj['flagA']){
                str2 += "<set />";
        	}else{
        		str2 += "<set value='"+obj['powerA']+"'/>";
        	}
	    });
	    str2 += "</dataset>";
	    
	    var str3 = "<dataset seriesName='对比日实时B相功率' color='5C589F' anchorBorderColor='5C589F' anchorBgColor='5C589F'>";
	    Ext.each(mpDayRealPowerConList,function(obj){
	    	if(obj['flagB']){
                str3 += "<set />";
        	}else{
        		str3 += "<set value='"+obj['powerB']+"'/>";
        	}
	    });
	    str3 += "</dataset>";
	    
	    var str4 = "<dataset seriesName='对比日实时C相功率' color='4974CC' anchorBorderColor='4974CC' anchorBgColor='4974CC'>";
	    Ext.each(mpDayRealPowerConList,function(obj){
	    	if(obj['flagC']){
                str4 += "<set />";
        	}else{
        		str4 += "<set value='"+obj['powerC']+"'/>";
        	}
	    });
	    str4 += "</dataset>";
	    
	    xmlData = xmlData+str1+str2+str3+str4;
    }

    xmlData = xmlData+"<styles><definition>"+
        "<style name='CaptionFont' type='font' size='12' />"+
        "</definition><application>"+
        "<apply toObject='CAPTION' styles='CaptionFont' />"+ 
        "<apply toObject='SUBCAPTION' styles='CaptionFont' />"+
        "</application></styles></chart>";

    return xmlData;	
}

/**
 * @描述: 返回曲线，针对采集数据综合分析页面测量点曲线(合并一类二类曲线)，针对实时电压曲线
 * @author 姜炜超
 */
function  getGAVoltMltXMLData(generalPowerModelList,
		     generalAVoltData, contrastAVoltData, 
			 generalBVoltData, contrastBVoltData,
             generalCVoltData, contrastCVoltData,
             mpDayRealVoltList,mpDayRealVoltConList, 
             generalAVoltName, contrastAVoltName,
             generalBVoltName, contrastBVoltName,
             generalCVoltName, contrastCVoltName,
             mpDayRealVoltName,mpDayRealVoltConName,step,publicName, yAxisMaxValue, yAxisMinValue){
	
	var xmlData = "<chart caption='" + publicName + "' legendPosition='BOTTUM' lineThickness='1' showValues='0' formatNumberScale='0'" +
	    " anchorRadius='1' divLineAlpha='10' divLineColor='CC3300' " +
	    "divLineIsDashed='1' showAlternateHGridColor='1' " +
	    "alternateHGridAlpha='5' alternateHGridColor='CC3300'" +
	    " shadowAlpha='40' labelStep='" + step + "' numvdivlines='22' " +
	    "chartRightMargin='35' bgColor='FFFFFF,CC3300' " +
	    "bgAngle='270' bgAlpha='10,10' numberSuffix='V' yAxisMinValue='" + yAxisMinValue + "' yAxisMaxValue='" + yAxisMaxValue + "' >";

    var str = "<categories>";
    Ext.each(generalPowerModelList,function(obj){
        str += "<category label='"+obj['time']+"' />";
    });
    str += "</categories>";

    xmlData = xmlData+str;

    if(null != generalAVoltName && 0 < generalAVoltName.length){
        var str1 = "<dataset seriesName='查询日冻结A相电压'"+" color='1D8BD1' anchorBorderColor='1D8BD1' anchorBgColor='1D8BD1'>";
        Ext.each(generalAVoltData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalBVoltName && 0 < generalBVoltName.length){
        var str1 = "<dataset seriesName='查询日冻结B相电压'"+" color='F5A901' anchorBorderColor='F5A901' anchorBgColor='F5A901'>";
        Ext.each(generalBVoltData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalCVoltName && 0 < generalCVoltName.length){
        var str1 = "<dataset seriesName='查询日冻结C相电压'"+" color='0025A5' anchorBorderColor='0025A5' anchorBgColor='0025A5'>";
        Ext.each(generalCVoltData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastAVoltName && 0 < contrastAVoltName.length){
        var str1 = "<dataset seriesName='对比日冻结A相电压'"+" color='F1683C' anchorBorderColor='F1683C' anchorBgColor='F1683C'>";
        Ext.each(contrastAVoltData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastBVoltName && 0 < contrastBVoltName.length){
        var str1 = "<dataset seriesName='对比日冻结B相电压'"+" color='FF8B71' anchorBorderColor='FF8B71' anchorBgColor='FF8B71'>";
        Ext.each(contrastBVoltData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastCVoltName && 0 < contrastCVoltName.length){
        var str1 = "<dataset seriesName='对比日冻结C相电压'"+" color='FAEEB5' anchorBorderColor='FAEEB5' anchorBgColor='FAEEB5'>";
        Ext.each(contrastCVoltData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != mpDayRealVoltName && 0 < mpDayRealVoltName.length){
        var str1 = "<dataset seriesName='查询日实时A相电压'"+" color='FFFF00' anchorBorderColor='FFFF00' anchorBgColor='FFFF00'>";
        Ext.each(mpDayRealVoltList,function(obj){
        	if(obj['flagA']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['voltA']+"'/>";
        	}
         });
        str1 += "</dataset>";
        	
	    var str2 = "<dataset seriesName='查询日实时B相电压' color='00FF00' anchorBorderColor='00FF00' anchorBgColor='00FF00'>";
	    Ext.each(mpDayRealVoltList,function(obj){
	    	if(obj['flagB']){
                str2 += "<set />";
        	}else{
        		str2 += "<set value='"+obj['voltB']+"'/>";
        	}
	    });
	    str2 += "</dataset>";
	    
	    var str3 = "<dataset seriesName='查询日实时C相电压' color='FF0000' anchorBorderColor='FF0000' anchorBgColor='FF0000'>";
	    Ext.each(mpDayRealVoltList,function(obj){
	    	if(obj['flagC']){
                str3 += "<set />";
        	}else{
        		str3 += "<set value='"+obj['voltC']+"'/>";
        	}
	    });
	    str3 += "</dataset>";

        xmlData = xmlData+str1+str2+str3;
    }
    
    if(null != mpDayRealVoltConName && 0 < mpDayRealVoltConName.length){
        var str1 = "<dataset seriesName='对比日实时A相电压'"+" color='EA8908' anchorBorderColor='EA8908' anchorBgColor='EA8908'>";
        Ext.each(mpDayRealVoltConList,function(obj){
        	if(obj['flagA']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['voltA']+"'/>";
        	}
         });
        str1 += "</dataset>";
        	
	    var str2 = "<dataset seriesName='对比日实时B相电压' color='5C589F' anchorBorderColor='5C589F' anchorBgColor='5C589F'>";
	    Ext.each(mpDayRealVoltConList,function(obj){
	    	if(obj['flagB']){
                str2 += "<set />";
        	}else{
        		str2 += "<set value='"+obj['voltB']+"'/>";
        	}
	    });
	    str2 += "</dataset>";
	    
	    var str3 = "<dataset seriesName='对比日实时C相电压' color='4974CC' anchorBorderColor='4974CC' anchorBgColor='4974CC'>";
	    Ext.each(mpDayRealVoltConList,function(obj){
	    	if(obj['flagC']){
                str3 += "<set />";
        	}else{
        		str3 += "<set value='"+obj['voltC']+"'/>";
        	}
	    });
	    str3 += "</dataset>";

        xmlData = xmlData+str1+str2+str3;
    }

    xmlData = xmlData+"<styles><definition>"+
        "<style name='CaptionFont' type='font' size='12' />"+
        "</definition><application>"+
        "<apply toObject='CAPTION' styles='CaptionFont' />"+ 
        "<apply toObject='SUBCAPTION' styles='CaptionFont' />"+
        "</application></styles></chart>";

    return xmlData;	
}

/**
 * @描述: 返回曲线，针对采集数据综合分析页面测量点曲线(合并一类二类曲线)，针对实时电流曲线
 * @author 姜炜超
 */
function  getGACurMltXMLData(generalPowerModelList,
		     generalCurData,contrastCurData,
             generalACurData,contrastACurData,
             generalBCurData,contrastBCurData,
             generalCCurData,contrastCCurData,
             mpDayRealCurList,mpDayRealCurConList,
             generalCurName,contrastCurName,
             generalACurName,contrastACurName,
             generalBCurName,contrastBCurName,
             generalCCurName,contrastCCurName,
             mpDayRealCurName,mpDayRealCurConName,step,publicName, yAxisMaxValue, yAxisMinValue){
	
	var xmlData = "<chart caption='" + publicName + "' legendPosition='BOTTUM' lineThickness='1' showValues='0' formatNumberScale='0'" +
	    " anchorRadius='1' divLineAlpha='10' divLineColor='CC3300' " +
	    "divLineIsDashed='1' showAlternateHGridColor='1' " +
	    "alternateHGridAlpha='5' alternateHGridColor='CC3300'" +
	    " shadowAlpha='40' labelStep='" + step + "' numvdivlines='22' " +
	    "chartRightMargin='35' bgColor='FFFFFF,CC3300' " +
	    "bgAngle='270' bgAlpha='10,10' numberSuffix='A' yAxisMinValue='" + yAxisMinValue + "' yAxisMaxValue='" + yAxisMaxValue + "' >";

    var str = "<categories>";
    Ext.each(generalPowerModelList,function(obj){
        str += "<category label='"+obj['time']+"' />";
    });
    str += "</categories>";

    xmlData = xmlData+str;

    if(null != generalCurName && 0 < generalCurName.length){
        var str1 = "<dataset seriesName='查询日冻结零序电流'"+" color='1D8BD1' anchorBorderColor='1D8BD1' anchorBgColor='1D8BD1'>";
        Ext.each(generalCurData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalACurName && 0 < generalACurName.length){
        var str1 = "<dataset seriesName='查询日冻结A相电流'"+" color='F5A901' anchorBorderColor='F5A901' anchorBgColor='F5A901'>";
        Ext.each(generalACurData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalBCurName && 0 < generalBCurName.length){
        var str1 = "<dataset seriesName='查询日冻结B相电流'"+" color='0025A5' anchorBorderColor='0025A5' anchorBgColor='0025A5'>";
        Ext.each(generalBCurData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalCCurName && 0 < generalCCurName.length){
        var str1 = "<dataset seriesName='查询日冻结C相电流'"+" color='FF8B71' anchorBorderColor='FF8B71' anchorBgColor='FF8B71'>";
        Ext.each(generalCCurData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastCurName && 0 < contrastCurName.length){
        var str1 = "<dataset seriesName='对比日冻结零序电流'"+" color='F1683C' anchorBorderColor='F1683C' anchorBgColor='F1683C'>";
        Ext.each(contrastCurData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastACurName && 0 < contrastACurName.length){
        var str1 = "<dataset seriesName='对比日冻结A相电流'"+" color='FAEEB5' anchorBorderColor='FAEEB5' anchorBgColor='FAEEB5'>";
        Ext.each(contrastACurData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastBCurName && 0 < contrastBCurName.length){
        var str1 = "<dataset seriesName='对比日冻结B相电流'"+" color='ABEEBF' anchorBorderColor='ABEEBF' anchorBgColor='ABEEBF'>";
        Ext.each(contrastBCurData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastCCurName && 0 < contrastCCurName.length){
        var str1 = "<dataset seriesName='对比日冻结C相电流'"+" color='DEE8EF' anchorBorderColor='DEE8EF' anchorBgColor='DEE8EF'>";
        Ext.each(contrastCCurData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != mpDayRealCurName && 0 < mpDayRealCurName.length){
        var str1 = "<dataset seriesName='查询日实时A相电流'"+" color='FFFF00' anchorBorderColor='FFFF00' anchorBgColor='FFFF00'>";
        Ext.each(mpDayRealCurList,function(obj){
        	if(obj['flagA']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['curA']+"'/>";
        	}
         });
        str1 += "</dataset>";
        	
	    var str2 = "<dataset seriesName='查询日实时B相电流' color='00FF00' anchorBorderColor='00FF00' anchorBgColor='00FF00'>";
	    Ext.each(mpDayRealCurList,function(obj){
	    	if(obj['flagB']){
                str2 += "<set />";
        	}else{
        		str2 += "<set value='"+obj['curB']+"'/>";
        	}
	    });
	    str2 += "</dataset>";
	    
	    var str3 = "<dataset seriesName='查询日实时C相电流' color='FF0000' anchorBorderColor='FF0000' anchorBgColor='FF0000'>";
	    Ext.each(mpDayRealCurList,function(obj){
	    	if(obj['flagC']){
                str3 += "<set />";
        	}else{
        		str3 += "<set value='"+obj['curC']+"'/>";
        	}
	    });
	    str3 += "</dataset>";
	    
	    var str4 = "<dataset seriesName='查询日实时零序电流' color='800080' anchorBorderColor='800080' anchorBgColor='800080'>";
	    Ext.each(mpDayRealCurList,function(obj){
	    	if(obj['flag0']){
                str4 += "<set />";
        	}else{
        		str4 += "<set value='"+obj['cur0']+"'/>";
        	}
	    });
	    str4 += "</dataset>";

        xmlData = xmlData+str4+str1+str2+str3;
    }
    
    if(null != mpDayRealCurConName && 0 < mpDayRealCurConName.length){
        var str1 = "<dataset seriesName='对比日实时A相电流'"+" color='62428F' anchorBorderColor='62428F' anchorBgColor='62428F'>";
        Ext.each(mpDayRealCurConList,function(obj){
        	if(obj['flagA']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['curA']+"'/>";
        	}
         });
        str1 += "</dataset>";
        	
	    var str2 = "<dataset seriesName='对比日实时B相电流' color='EA8908' anchorBorderColor='EA8908' anchorBgColor='EA8908'>";
	    Ext.each(mpDayRealCurConList,function(obj){
	    	if(obj['flagB']){
                str2 += "<set />";
        	}else{
        		str2 += "<set value='"+obj['curB']+"'/>";
        	}
	    });
	    str2 += "</dataset>";
	    
	    var str3 = "<dataset seriesName='对比日实时C相电流' color='5C589F' anchorBorderColor='5C589F' anchorBgColor='5C589F'>";
	    Ext.each(mpDayRealCurConList,function(obj){
	    	if(obj['flagC']){
                str3 += "<set />";
        	}else{
        		str3 += "<set value='"+obj['curC']+"'/>";
        	}
	    });
	    str3 += "</dataset>";
	    
	    var str4 = "<dataset seriesName='对比日实时零序电流' color='4974CC' anchorBorderColor='4974CC' anchorBgColor='4974CC'>";
	    Ext.each(mpDayRealCurConList,function(obj){
	    	if(obj['flag0']){
                str4 += "<set />";
        	}else{
        		str4 += "<set value='"+obj['cur0']+"'/>";
        	}
	    });
	    str4 += "</dataset>";

        xmlData = xmlData+str4+str1+str2+str3;
    }
    
    xmlData = xmlData+"<styles><definition>"+
        "<style name='CaptionFont' type='font' size='12' />"+
        "</definition><application>"+
        "<apply toObject='CAPTION' styles='CaptionFont' />"+ 
        "<apply toObject='SUBCAPTION' styles='CaptionFont' />"+
        "</application></styles></chart>";

    return xmlData;	
}

/**
 * @描述: 返回曲线，针对采集数据综合分析页面测量点曲线(合并一类二类曲线)，针对实时功率因数曲线
 * @author 姜炜超
 */
function  getGAFactorMltXMLData(generalPowerModelList,
		     generalFactorData, contrastFactorData,
             generalAFactorData, contrastAFactorData,
             generalBFactorData, contrastBFactorData,
             generalCFactorData, contrastCFactorData,
             mpDayRealFactorList,mpDayRealFactorConList,
             generalFactorName, contrastFactorName,
             generalAFactorName, contrastAFactorName,
             generalBFactorName, contrastBFactorName,
             generalCFactorName, contrastCFactorName,
             mpDayRealFactorName,mpDayRealFactorConName,step,publicName, yAxisMaxValue, yAxisMinValue){
	
	var xmlData = "<chart caption='" + publicName + "' legendPosition='BOTTUM' lineThickness='1' showValues='0' formatNumberScale='0'" +
	    " anchorRadius='1' divLineAlpha='10' divLineColor='CC3300' " +
	    "divLineIsDashed='1' showAlternateHGridColor='1' " +
	    "alternateHGridAlpha='5' alternateHGridColor='CC3300'" +
	    " shadowAlpha='40' labelStep='" + step + "' numvdivlines='22' " +
	    "chartRightMargin='35' bgColor='FFFFFF,CC3300' " +
	    "bgAngle='270' bgAlpha='10,10' yAxisMinValue='" + yAxisMinValue + "' yAxisMaxValue='" + yAxisMaxValue + "' >";

    var str = "<categories>";
    Ext.each(generalPowerModelList,function(obj){
        str += "<category label='"+obj['time']+"' />";
    });
    str += "</categories>";

    xmlData = xmlData+str;

    if(null != generalFactorName && 0 < generalFactorName.length){
        var str1 = "<dataset seriesName='查询日冻结功率因数'"+" color='1D8BD1' anchorBorderColor='1D8BD1' anchorBgColor='1D8BD1'>";
        Ext.each(generalFactorData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalAFactorName && 0 < generalAFactorName.length){
        var str1 = "<dataset seriesName='查询日冻结A相功率因数'"+" color='F5A901' anchorBorderColor='F5A901' anchorBgColor='F5A901'>";
        Ext.each(generalAFactorData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalBFactorName && 0 < generalBFactorName.length){
        var str1 = "<dataset seriesName='查询日冻结B相功率因数'"+" color='0025A5' anchorBorderColor='0025A5' anchorBgColor='0025A5'>";
        Ext.each(generalBFactorData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalCFactorName && 0 < generalCFactorName.length){
        var str1 = "<dataset seriesName='查询日冻结C相功率因数'"+" color='FF8B71' anchorBorderColor='FF8B71' anchorBgColor='FF8B71'>";
        Ext.each(generalCFactorData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastFactorName && 0 < contrastFactorName.length){
        var str1 = "<dataset seriesName='对比日冻结功率因数'"+" color='F1683C' anchorBorderColor='F1683C' anchorBgColor='F1683C'>";
        Ext.each(contrastFactorData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastAFactorName && 0 < contrastAFactorName.length){
        var str1 = "<dataset seriesName='对比日冻结A相功率因数'"+" color='FAEEB5' anchorBorderColor='FAEEB5' anchorBgColor='FAEEB5'>";
        Ext.each(contrastAFactorData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastBFactorName && 0 < contrastBFactorName.length){
        var str1 = "<dataset seriesName='对比日冻结B相功率因数'"+" color='ABEEBF' anchorBorderColor='ABEEBF' anchorBgColor='ABEEBF'>";
        Ext.each(contrastBFactorData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastCFactorName && 0 < contrastCFactorName.length){
        var str1 = "<dataset seriesName='对比日冻结C相功率因数'"+" color='DEE8EF' anchorBorderColor='DEE8EF' anchorBgColor='DEE8EF'>";
        Ext.each(contrastCFactorData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != mpDayRealFactorName && 0 < mpDayRealFactorName.length){
        var str1 = "<dataset seriesName='查询日实时功率因数'"+" color='58539B' anchorBorderColor='58539B' anchorBgColor='58539B'>";
        Ext.each(mpDayRealFactorList,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['factor']+"'/>";
        	}
         });
        str1 += "</dataset>";
        	
	    var str2 = "<dataset seriesName='查询日实时A相功率因数' color='FFFF00' anchorBorderColor='FFFF00' anchorBgColor='FFFF00'>";
	    Ext.each(mpDayRealFactorList,function(obj){
	    	if(obj['flagA']){
                str2 += "<set />";
        	}else{
        		str2 += "<set value='"+obj['factorA']+"'/>";
        	}
	    });
	    str2 += "</dataset>";
	    
	    var str3 = "<dataset seriesName='查询日实时B相功率因数' color='00FF00' anchorBorderColor='00FF00' anchorBgColor='00FF00'>";
	    Ext.each(mpDayRealFactorList,function(obj){
	    	if(obj['flagB']){
                str3 += "<set />";
        	}else{
        		str3 += "<set value='"+obj['factorB']+"'/>";
        	}
	    });
	    str3 += "</dataset>";
	    
	    var str4 = "<dataset seriesName='查询日实时C相功率因数' color='FF0000' anchorBorderColor='FF0000' anchorBgColor='FF0000'>";
	    Ext.each(mpDayRealFactorList,function(obj){
	    	if(obj['flagC']){
                str4 += "<set />";
        	}else{
        		str4 += "<set value='"+obj['factorC']+"'/>";
        	}
	    });
	    str4 += "</dataset>";

        xmlData = xmlData+str1+str2+str3+str4;
    }
    
    if(null != mpDayRealFactorConName && 0 < mpDayRealFactorConName.length){
        var str1 = "<dataset seriesName='对比日实时功率因数'"+" color='62428F' anchorBorderColor='62428F' anchorBgColor='62428F'>";
        Ext.each(mpDayRealFactorConList,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['factor']+"'/>";
        	}
         });
        str1 += "</dataset>";
        	
	    var str2 = "<dataset seriesName='对比日实时A相功率因数' color='EA8908' anchorBorderColor='EA8908' anchorBgColor='EA8908'>";
	    Ext.each(mpDayRealFactorConList,function(obj){
	    	if(obj['flagA']){
                str2 += "<set />";
        	}else{
        		str2 += "<set value='"+obj['factorA']+"'/>";
        	}
	    });
	    str2 += "</dataset>";
	    
	    var str3 = "<dataset seriesName='对比日实时B相功率因数' color='5C589F' anchorBorderColor='5C589F' anchorBgColor='5C589F'>";
	    Ext.each(mpDayRealFactorConList,function(obj){
	    	if(obj['flagB']){
                str3 += "<set />";
        	}else{
        		str3 += "<set value='"+obj['factorB']+"'/>";
        	}
	    });
	    str3 += "</dataset>";
	    
	    var str4 = "<dataset seriesName='对比日实时C相功率因数' color='4974CC' anchorBorderColor='4974CC' anchorBgColor='4974CC'>";
	    Ext.each(mpDayRealFactorConList,function(obj){
	    	if(obj['flagC']){
                str4 += "<set />";
        	}else{
        		str4 += "<set value='"+obj['factorC']+"'/>";
        	}
	    });
	    str4 += "</dataset>";

        xmlData = xmlData+str1+str2+str3+str4;
    }
    
    xmlData = xmlData+"<styles><definition>"+
        "<style name='CaptionFont' type='font' size='12' />"+
        "</definition><application>"+
        "<apply toObject='CAPTION' styles='CaptionFont' />"+ 
        "<apply toObject='SUBCAPTION' styles='CaptionFont' />"+
        "</application></styles></chart>";

    return xmlData;	
}

/**
 * @描述: 返回曲线，针对采集数据综合分析页面总加组曲线(合并一类二类曲线)，针对实时功率曲线
 * @author 姜炜超
 */
function  getGATotalPowerMltXMLData(generalPowerModelList,
		     generalPowerData,contrastPowerData,
             generalAPowerData,contrastAPowerData,
             generalBPowerData,contrastBPowerData,
             generalCPowerData,contrastCPowerData,
             mpDayRealPowerList,mpDayRealPowerConList,
             generalPowerName,contrastPowerName,
             generalAPowerName,contrastAPowerName,
             generalBPowerName,contrastBPowerName,
             generalCPowerName,contrastCPowerName,
             mpDayRealPowerName,mpDayRealPowerConName,step,publicName, yAxisMaxValue, yAxisMinValue){
	
	var xmlData = "<chart caption='" + publicName + "' legendPosition='BOTTUM' lineThickness='1' showValues='0' formatNumberScale='0'" +
	    " anchorRadius='1' divLineAlpha='10' divLineColor='CC3300' " +
	    "divLineIsDashed='1' showAlternateHGridColor='1' " +
	    "alternateHGridAlpha='5' alternateHGridColor='CC3300'" +
	    " shadowAlpha='40' labelStep='" + step + "' numvdivlines='22' " +
	    "chartRightMargin='35' bgColor='FFFFFF,CC3300' " +
	    "bgAngle='270' bgAlpha='10,10' numberSuffix='kW' yAxisMinValue='" + yAxisMinValue + "' yAxisMaxValue='" + yAxisMaxValue + "' >";

    var str = "<categories>";
    Ext.each(generalPowerModelList,function(obj){
        str += "<category label='"+obj['time']+"' />";
    });
    str += "</categories>";

    xmlData = xmlData+str;

    if(null != generalPowerName && 0 < generalPowerName.length){
        var str1 = "<dataset seriesName='查询日冻结功率'"+" color='1D8BD1' anchorBorderColor='1D8BD1' anchorBgColor='1D8BD1'>";
        Ext.each(generalPowerData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalAPowerName && 0 < generalAPowerName.length){
        var str1 = "<dataset seriesName='查询日冻结A相功率'"+" color='F5A901' anchorBorderColor='F5A901' anchorBgColor='F5A901'>";
        Ext.each(generalAPowerData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalBPowerName && 0 < generalBPowerName.length){
        var str1 = "<dataset seriesName='查询日冻结B相功率'"+" color='0025A5' anchorBorderColor='0025A5' anchorBgColor='0025A5'>";
        Ext.each(generalBPowerData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalCPowerName && 0 < generalCPowerName.length){
        var str1 = "<dataset seriesName='查询日冻结C相功率'"+" color='FF8B71' anchorBorderColor='FF8B71' anchorBgColor='FF8B71'>";
        Ext.each(generalCPowerData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastPowerName && 0 < contrastPowerName.length){
        var str1 = "<dataset seriesName='对比日冻结功率'"+" color='F1683C' anchorBorderColor='F1683C' anchorBgColor='F1683C'>";
        Ext.each(contrastPowerData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";
        xmlData = xmlData+str1;
	    
    }
    
    if(null != contrastAPowerName && 0 < contrastAPowerName.length){
        var str1 = "<dataset seriesName='对比日冻结A相功率'"+" color='FAEEB5' anchorBorderColor='FAEEB5' anchorBgColor='FAEEB5'>";
        Ext.each(contrastAPowerData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";
        xmlData = xmlData+str1;
	    
    }
    
    if(null != contrastBPowerName && 0 < contrastBPowerName.length){
        var str1 = "<dataset seriesName='对比日冻结B相功率'"+" color='ABEEBF' anchorBorderColor='ABEEBF' anchorBgColor='ABEEBF'>";
        Ext.each(contrastBPowerData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";
        xmlData = xmlData+str1;
	    
    }
    
    if(null != contrastCPowerName && 0 < contrastCPowerName.length){
        var str1 = "<dataset seriesName='对比日冻结C相功率'"+" color='DEE8EF' anchorBorderColor='DEE8EF' anchorBgColor='DEE8EF'>";
        Ext.each(contrastCPowerData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";
        xmlData = xmlData+str1;
	    
    }
    
    if(null != mpDayRealPowerName && 0 < mpDayRealPowerName.length){
        var str1 = "<dataset seriesName='查询日实时功率'"+" color='58539B' anchorBorderColor='58539B' anchorBgColor='58539B'>";
        Ext.each(mpDayRealPowerList,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['power']+"'/>";
        	}
         });
        str1 += "</dataset>";
	    
	    xmlData = xmlData+str1;
    }
    
    if(null != mpDayRealPowerConName && 0 < mpDayRealPowerConName.length){
        var str1 = "<dataset seriesName='对比日实时功率'"+" color='62428F' anchorBorderColor='62428F' anchorBgColor='62428F'>";
        Ext.each(mpDayRealPowerConList,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['power']+"'/>";
        	}
         });
        str1 += "</dataset>";
	    
	    xmlData = xmlData+str1;
    }

    xmlData = xmlData+"<styles><definition>"+
        "<style name='CaptionFont' type='font' size='12' />"+
        "</definition><application>"+
        "<apply toObject='CAPTION' styles='CaptionFont' />"+ 
        "<apply toObject='SUBCAPTION' styles='CaptionFont' />"+
        "</application></styles></chart>";

    return xmlData;	
}

/**
 * @描述: 返回曲线，针对采集数据综合分析页面测量点曲线(合并一类二类曲线)，针对实时电能量曲线
 * @author 姜炜超
 */
function  getGAEnergyMltXMLData(generalPowerModelList, generalEnergyPapData,
				contrastEnergyPapData, generalEnergyPrpData,
				contrastEnergyPrpData, generalEnergyRapData,
				contrastEnergyRapData, generalEnergyRrpData,
				contrastEnergyRrpData, generalEnergyPapName,
				contrastEnergyPapName, generalEnergyPrpName,
				contrastEnergyPrpName, generalEnergyRapName,
				contrastEnergyRapName, generalEnergyRrpName,
				contrastEnergyRrpName, step, publicName,
				yAxisMaxValue, yAxisMinValue){
	var xmlData = "<chart caption='" + publicName + "' legendPosition='BOTTUM' lineThickness='1' showValues='0' formatNumberScale='0'" +
	    " anchorRadius='1' divLineAlpha='10' divLineColor='CC3300' " +
	    "divLineIsDashed='1' showAlternateHGridColor='1' " +
	    "alternateHGridAlpha='5' alternateHGridColor='CC3300'" +
	    " shadowAlpha='40' labelStep='" + step + "' numvdivlines='22' " +
	    "chartRightMargin='35' bgColor='FFFFFF,CC3300' " +
	    "bgAngle='270' bgAlpha='10,10' numberSuffix='kWh' yAxisMinValue='" + yAxisMinValue + "' yAxisMaxValue='" + yAxisMaxValue + "' >";

    var str = "<categories>";
    Ext.each(generalPowerModelList,function(obj){
        str += "<category label='"+obj['time']+"' />";
    });
    str += "</categories>";

    xmlData = xmlData+str;

    if(null != generalEnergyPapName && 0 < generalEnergyPapName.length){
        var str1 = "<dataset seriesName='查询日冻结正向有功电能量'"+" color='1D8BD1' anchorBorderColor='1D8BD1' anchorBgColor='1D8BD1'>";
        Ext.each(generalEnergyPapData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalEnergyPrpName && 0 < generalEnergyPrpName.length){
        var str1 = "<dataset seriesName='查询日冻结正向无功电能量'"+" color='F5A901' anchorBorderColor='F5A901' anchorBgColor='F5A901'>";
        Ext.each(generalEnergyPrpData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalEnergyRapName && 0 < generalEnergyRapName.length){
        var str1 = "<dataset seriesName='查询日冻结反向有功电能量'"+" color='0025A5' anchorBorderColor='0025A5' anchorBgColor='0025A5'>";
        Ext.each(generalEnergyRapData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalEnergyRrpName && 0 < generalEnergyRrpName.length){
        var str1 = "<dataset seriesName='查询日冻结反向无功电能量'"+" color='FF8B71' anchorBorderColor='FF8B71' anchorBgColor='FF8B71'>";
        Ext.each(generalEnergyRrpData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastEnergyPapName && 0 < contrastEnergyPapName.length){
        var str1 = "<dataset seriesName='对比日冻结正向有功电能量'"+" color='F1683C' anchorBorderColor='F1683C' anchorBgColor='F1683C'>";
        Ext.each(contrastEnergyPapData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastEnergyPrpName && 0 < contrastEnergyPrpName.length){
        var str1 = "<dataset seriesName='对比日冻结正向无功电能量'"+" color='FAEEB5' anchorBorderColor='FAEEB5' anchorBgColor='FAEEB5'>";
        Ext.each(contrastEnergyPrpData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastEnergyRapName && 0 < contrastEnergyRapName.length){
        var str1 = "<dataset seriesName='对比日冻结反向有功电能量'"+" color='ABEEBF' anchorBorderColor='ABEEBF' anchorBgColor='ABEEBF'>";
        Ext.each(contrastEnergyRapData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastEnergyRrpName && 0 < contrastEnergyRrpName.length){
        var str1 = "<dataset seriesName='对比日冻结反向无功电能量'"+" color='DEE8EF' anchorBorderColor='DEE8EF' anchorBgColor='DEE8EF'>";
        Ext.each(contrastEnergyRrpData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    xmlData = xmlData+"<styles><definition>"+
        "<style name='CaptionFont' type='font' size='12' />"+
        "</definition><application>"+
        "<apply toObject='CAPTION' styles='CaptionFont' />"+ 
        "<apply toObject='SUBCAPTION' styles='CaptionFont' />"+
        "</application></styles></chart>";

    return xmlData;	
}

/**
 * @描述: 返回曲线，针对采集数据综合分析页面测量点曲线(合并一类二类曲线)，针对实时电能示值曲线
 * @author 姜炜超
 */
function  getGAReadMltXMLData(generalPowerModelList,generalReadPapData,
				contrastReadPapData, generalReadPrpData,
				contrastReadPrpData, generalReadRapData,
				contrastReadRapData, generalReadRrpData,
				contrastReadRrpData, generalReadPapName,
				contrastReadPapName, generalReadPrpName,
				contrastReadPrpName, generalReadRapName,
				contrastReadRapName, generalReadRrpName,
				contrastReadRrpName, step, publicName,
				yAxisMaxValue, yAxisMinValue){
	var xmlData = "<chart caption='" + publicName + "' legendPosition='BOTTUM' lineThickness='1' showValues='0' formatNumberScale='0'" +
	    " anchorRadius='1' divLineAlpha='10' divLineColor='CC3300' " +
	    "divLineIsDashed='1' showAlternateHGridColor='1' " +
	    "alternateHGridAlpha='5' alternateHGridColor='CC3300'" +
	    " shadowAlpha='40' labelStep='" + step + "' numvdivlines='22' " +
	    "chartRightMargin='35' bgColor='FFFFFF,CC3300' " +
	    "bgAngle='270' bgAlpha='10,10' yAxisMinValue='" + yAxisMinValue + "' yAxisMaxValue='" + yAxisMaxValue + "' >";

    var str = "<categories>";
    Ext.each(generalPowerModelList,function(obj){
        str += "<category label='"+obj['time']+"' />";
    });
    str += "</categories>";

    xmlData = xmlData+str;

    if(null != generalReadPapName && 0 < generalReadPapName.length){
        var str1 = "<dataset seriesName='查询日冻结正向有功电能示值'"+" color='1D8BD1' anchorBorderColor='1D8BD1' anchorBgColor='1D8BD1'>";
        Ext.each(generalReadPapData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalReadPrpName && 0 < generalReadPrpName.length){
        var str1 = "<dataset seriesName='查询日冻结正向无功电能示值'"+" color='F5A901' anchorBorderColor='F5A901' anchorBgColor='F5A901'>";
        Ext.each(generalReadPrpData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalReadRapName && 0 < generalReadRapName.length){
        var str1 = "<dataset seriesName='查询日冻结反向有功电能示值'"+" color='0025A5' anchorBorderColor='0025A5' anchorBgColor='0025A5'>";
        Ext.each(generalReadRapData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != generalReadRrpName && 0 < generalReadRrpName.length){
        var str1 = "<dataset seriesName='查询日冻结反向无功电能示值'"+" color='FF8B71' anchorBorderColor='FF8B71' anchorBgColor='FF8B71'>";
        Ext.each(generalReadRrpData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastReadPapName && 0 < contrastReadPapName.length){
        var str1 = "<dataset seriesName='对比日冻结正向有功电能示值'"+" color='F1683C' anchorBorderColor='F1683C' anchorBgColor='F1683C'>";
        Ext.each(contrastReadPapData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastReadPrpName && 0 < contrastReadPrpName.length){
        var str1 = "<dataset seriesName='对比日冻结正向无功电能示值'"+" color='FAEEB5' anchorBorderColor='FAEEB5' anchorBgColor='FAEEB5'>";
        Ext.each(contrastReadPrpData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastReadRapName && 0 < contrastReadRapName.length){
        var str1 = "<dataset seriesName='对比日冻结反向有功电能示值'"+" color='ABEEBF' anchorBorderColor='ABEEBF' anchorBgColor='ABEEBF'>";
        Ext.each(contrastReadRapData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    if(null != contrastReadRrpName && 0 < contrastReadRrpName.length){
        var str1 = "<dataset seriesName='对比日冻结反向无功电能示值'"+" color='DEE8EF' anchorBorderColor='DEE8EF' anchorBgColor='DEE8EF'>";
        Ext.each(contrastReadRrpData,function(obj){
        	if(obj['flag']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['data']+"'/>";
        	}
         });
        str1 += "</dataset>";

        xmlData = xmlData+str1;
    }
    
    xmlData = xmlData+"<styles><definition>"+
        "<style name='CaptionFont' type='font' size='12' />"+
        "</definition><application>"+
        "<apply toObject='CAPTION' styles='CaptionFont' />"+ 
        "<apply toObject='SUBCAPTION' styles='CaptionFont' />"+
        "</application></styles></chart>";

    return xmlData;	
}