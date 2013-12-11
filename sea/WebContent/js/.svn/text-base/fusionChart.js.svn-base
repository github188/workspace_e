/**
 * @描述: 返回单系列图表数据
 * @param {} jsonData json数组
 * @param {} keys key值
 * @param {} caption 图表标题
 * @param {} xAxisName x轴坐标名
 * @param {} yAxisName y轴坐标名
 * @param {} colors 图表颜色
 * @author 陈建国
 */
function getSingleXMLData(jsonData,keys,caption,xAxisName,yAxisName,colors,decimal,yMinValue,yMaxValue){
	var xmlData = "<graph baseFont='宋体' baseFontSize='14' rotateYAxisName='0'  caption='"+caption+"' " +
			      "xAxisName='"+xAxisName+"' " +
			      "yAxisName='"+yAxisName+"' " +
			      "decimalPrecision='0' " ;
			      if(decimal){
			      	xmlData += "decimals = '"+decimal+"' ";
			      }
			      if(yMinValue!=null){
			      	xmlData += "yAxisMinValue = '"+yMinValue+"' ";
			      }
			      if(yMaxValue!=null){
			      	xmlData += "yAxisMaxValue = '"+yMaxValue+"' ";
			      }
			      xmlData += "formatNumberScale='0'>";
	var i = 0 ;		      
	Ext.each(jsonData,function(obj){
		var color ;
		if(colors == null){
			color = randomColor();
		}else{
			color = colors[i];
		}
		xmlData += "<set name='"+obj[keys[0]]+"' value='"+obj[keys[1]]+"' color='"+color+"' isSliced='1' />";
		i++;
	});		
	
	xmlData += "</graph>";
	return xmlData;		      
}

/**
 * @描述: 返回多系列图表数据
 * @param {} jsonData json数组
 * @param {} keys key值
 * @param {} caption 图表标题
 * @param {} xAxisName x轴坐标名
 * @param {} yAxisName y轴坐标名
 * @param {} colors 图表颜色
 * @author 陈建国
 */
function getMultiXMLData(jsonData,keys,caption,xAxisName,yAxisName,colors,nameKeys,ystep,yMinValue,yMaxValue){
	var xmlData = "<graph baseFont='宋体' baseFontSize='14' rotateYAxisName='0'  xaxisname='"+xAxisName+"' " +
			      "yaxisname='"+yAxisName+"' " +
			      "hovercapbg='DEDEBE' " +
			      "hovercapborder='889E6D' " +
			      "rotateNames='0' " +
			     // "yAxisMaxValue='100' " +
			      "numdivlines='9' " +
			      "divLineColor='CCCCCC' " +
			      "divLineAlpha='80' " +
			      "decimalPrecision='0' " +
			      "showValues='0' " +
			      "legendPosition='RIGHT'" +
			      "showAlternateHGridColor='1' " +
			      "AlternateHGridAlpha='30' " +
			      "AlternateHGridColor='CCCCCC' ";
			      if(ystep){
			      	xmlData += "yAxisValuesStep = '"+ystep+"' ";
			      }
			      if(yMinValue!=null){
			      	xmlData += "yAxisMinValue = '"+yMinValue+"' ";
			      }
			      if(yMaxValue!=null){
			      	xmlData += "yAxisMaxValue = '"+yMaxValue+"' ";
			      }
			      xmlData += "caption='"+caption+"' " +
			      "subcaption='' >";

	var str = "<categories font='Arial' fontSize='12' fontColor='000000'>";
	Ext.each(jsonData,function(obj){
		str += "<category name='"+obj[keys[0]]+"' />";
	});
	str += "</categories>";
	
	var str1 = "";
	for(var i = 1 ; i < keys.length ; i++){
		var color ;
		if(colors == null){
			color = randomColor();
		}else{
			color = colors[i-1];
		}
		str1 += "<dataset seriesname='"+nameKeys[i-1]+"' color='"+color+"'> ";
		Ext.each(jsonData,function(obj){
			str1 += "<set value='"+obj[keys[i]]+"' />";
		});
		str1 += "</dataset>";
	}

	xmlData = xmlData+str+str1+'</graph>';
	return xmlData;
}

function getMultiXMLData_1(jsonData,keys,caption,xAxisName,yAxisName,colors,nameKeys,ystep,yMinValue,yMaxValue){
	var xmlData = "<graph baseFont='宋体' baseFontSize='12' rotateYAxisName='0'  xaxisname='"+xAxisName+"' " +
			      "yaxisname='"+yAxisName+"' " +
			      "hovercapbg='DEDEBE' " +
			      "hovercapborder='889E6D' " +
			      "rotateNames='0' " +
			     // "yAxisMaxValue='100' " +
			      "numdivlines='9' " +
			      "divLineColor='CCCCCC' " +
			      "divLineAlpha='80' " +
			      "showValues='0' " +
			      "decimalPrecision='0' " +
			      "legendPosition='bottom'" +
			      "showAlternateHGridColor='1' " +
			      "AlternateHGridAlpha='30' " +
			      "AlternateHGridColor='CCCCCC' ";
			      if(ystep){
			      	xmlData += "yAxisValuesStep = '"+ystep+"' ";
			      }
			      if(yMinValue!=null){
			      	xmlData += "yAxisMinValue = '"+yMinValue+"' ";
			      }
			      if(yMaxValue!=null){
			      	xmlData += "yAxisMaxValue = '"+yMaxValue+"' ";
			      }
			      xmlData += "caption='"+caption+"' " +
			      "subcaption='' >";

	var str = "<categories font='Arial' fontSize='12' fontColor='000000'>";
	Ext.each(jsonData,function(obj){
		str += "<category name='"+obj[keys[0]]+"' />";
	});
	str += "</categories>";
	
	var str1 = "";
	for(var i = 1 ; i < keys.length ; i++){
		var color ;
		if(colors == null){
			color = randomColor();
		}else{
			color = colors[i-1];
		}
		str1 += "<dataset seriesname='"+nameKeys[i-1]+"' color='"+color+"'> ";
		Ext.each(jsonData,function(obj){
			str1 += "<set value='"+obj[keys[i]]+"' />";
		});
		str1 += "</dataset>";
	}

	xmlData = xmlData+str+str1+'</graph>';
	return xmlData;
}

/**
 * @描述: 返回Column 3D+ Line数据，fusioncharts不支持y轴显示中文，所以必须横写rotateYAxisName设置为0
 * @param {} jsonData json数组
 * @param {} keys key值
 * @param {} caption 图表标题
 * @param {} xAxisName x轴坐标名
 * @param {} yAxisName y轴坐标名
 * @param {} colors 图表颜色
 * @param {} nameKeys keyname
 * @author 姜炜超
 */
function getMultiCLXMLData(jsonData,keys,caption,pYAxisName,sYAxisName,colors,nameKeys){
	var xmlData = " <chart baseFont='宋体' baseFontSize='12' palette='1' rotateYAxisName='0'  caption='" + caption +"'"+
	              " shownames='1' showvalues='0' numberPrefix='' sYAxisValuesDecimals='2' connectNullData='0' PYAxisName='" + pYAxisName+"'"+
	              " SYAxisName='"+ sYAxisName +"'" + ""+" numDivLines='4' formatNumberScale='0'>";
	
	if(null != jsonData && 0 < jsonData.length){
	    var str = " <categories> ";
	    Ext.each(jsonData,function(obj){
		    str += "<category label='"+obj[keys[0]]+"' />";
	    });
	    str += "</categories>";
	
	    var str1 = "";
	    var j = keys.length-1;
	    for(var i = 1 ; i < keys.length ; i++){
		    var color ;
		    if(colors == null){
			    color = randomColor();
		    }else{
			    color = colors[i-1];
		    }
		    j = j-1;
		    if(j > 1){
			    str1 += "<dataset seriesname='"+nameKeys[i-1]+"' color='"+color+"'" + " showValues='0'>";
			    Ext.each(jsonData,function(obj){
				    str1 += "<set value='"+obj[keys[i]]+"' />";
			    });
		    }
		    else if(j == 1){
			    str1 += "<dataset seriesname='"+nameKeys[i-1]+"' color='"+color+"'" + " showValues='0'>";
			    Ext.each(jsonData,function(obj){
				    str1 += "<set value='"+obj[keys[i]]+"' />";
			    });
			    str1 = str1.substring(0, str1.length-2);
			    str1 += " dashed='1' />"; 
		    }
		    else{
			    str1 += "<dataset seriesname='"+nameKeys[i-1]+"' color='"+color+"'" + " showValues='0' parentYAxis='S'>";
			    Ext.each(jsonData,function(obj){
				    str1 += "<set value='"+obj[keys[i]]+"' />";
			    });
		    }
		    str1 += " </dataset>";
	    }
	    xmlData = xmlData+str+str1+' </chart> ';
	}else{
		xmlData = xmlData+' </chart> ';
	}
	return xmlData;
}

/**
 * @描述 生成随机的颜色
 * @return {}
 */
function randomColor() {
	return ''+(Math.random()*0xffffff<<0).toString(16); 


}