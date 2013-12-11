function createXMLDOM() 
{
	var arrSignatures = ["MSXML2.DOMDocument.5.0", "MSXML2.DOMDocument.4.0",
						 "MSXML2.DOMDocument.3.0", "MSXML2.DOMDocument",
						 "Microsoft.XmlDom"];
                                 
	for (var i=0; i < arrSignatures.length; i++)
	{
		try
		{
			var oXmlDom = new ActiveXObject(arrSignatures[i]);
			return oXmlDom;
		} 
		catch (oError) 
		{
			return null;
		}
	}              

	throw new Error("���ϵͳû�а�װMSXML");           
 }

function createHttpRequest()
{
	var xmlhttp_request = false; 

	try
	{
		if( window.ActiveXObject )
		{
			for( var i = 5; i; i-- )
			{ 
				try
				{ 
					if( i == 2 )
					{
						xmlhttp_request = new ActiveXObject( "Microsoft.XMLHTTP" ); 
					}
					else
					{
						xmlhttp_request = new ActiveXObject( "Msxml2.XMLHTTP." + i + ".0" ); 
						xmlhttp_request.setRequestHeader("Content-Type","text/xml"); 
						xmlhttp_request.setRequestHeader("Content-Type","gb2312"); 
					}
					break;
				} 

				catch(e)
				{
					xmlhttp_request = false; 
				}
			}
		}
		else if( window.XMLHttpRequest )
		{
			xmlhttp_request = new XMLHttpRequest();

			if (xmlhttp_request.overrideMimeType)
			{
				xmlhttp_request.overrideMimeType('text/xml');
			}
		}
	}

	catch(e)
	{
		xmlhttp_request = false;
		alert("createHttpRequest failed!");
		return null;
	} 

	return xmlhttp_request ;

}

// create XMLHttpRequest first
var request = createHttpRequest();

/*
// XMLDom for svgMetas
var SvgMeats = null;

// Get all the svg basic metas through XMLHttpRequest
if (request != null)
	InitSvgMetas();

function InitSvgMetas()
{
	var url = "PowerMeta.lib.xml?spanTime=" + new Date().getTime();
	request.open("GET", url, true);
	request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	request.onreadystatechange = LoadSvgMetas;
	request.send(null);
}

function LoadSvgMetas()
{
	if (request.readyState == 4)
	{
		if (request.status == 200)
		{
			SvgMeats = request.responseXML;
			if (SvgMeats == null)
			{
				alert("failed to load svg metas!");
			}
		}
	}

}*/

// Get remote data and refresh the SVG graphics real-time
//window.setInterval("RefreshData()",10000)

// request realtime data
function RefreshData()
{
	var htmlObj = document.getElementById("svgitem");
	var svgDoc = htmlObj.getSVGDocument();
	var svgRoot = svgDoc.documentElement;
	var svgDev = svgRoot.getElementsByTagName("associate").item(0);
	var associateLst = svgDev.getElementsByTagName("dev");
	var nLen = associateLst.length;

	if (nLen == 0)
	{
		return;
	}
/*
	var requestInfo = "";
	
	for (var i=0; i<nLen; i++)
	{
		var associatItem = associateLst.item(i);
		requestInfo += "&" + associatItem.getAttribute("idx") + "." + associatItem.getAttribute("sta") + "." + associatItem.getAttribute("name") + "." + associatItem.getAttribute("pt") + "." + associatItem.getAttribute("type");
	}
*/
	var requestInfo = svgRoot.getAttribute("name");

    var url = "../cgi-bin/ReqRtd.cgi?spanTime=" + new Date().getTime();

	request.open("POST", url, true);
	request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	request.onreadystatechange = ParseRealtimeData;
	request.send(requestInfo);
}

function ParseRealtimeData()
{
	if (request.readyState == 4)
	{
		if (request.status == 200)
		{
			//alert(request.responseText);
			RefreshSvg(request.responseXML.documentElement);   
		}
	}
}

function RefreshSvg(dataRoot)
{
	if (dataRoot == null)
	{
		return;
	}

	var htmlObj = document.getElementById("svgitem");
	var svgDoc = htmlObj.getSVGDocument();
	var svgRoot = svgDoc.documentElement;
	var RealDataList = dataRoot.getElementsByTagName("PowerMeta");//dataRoot.childNodes;
	var nLen = RealDataList.length;
	var i=0;
	
	for (i=0; i<nLen; i++)
	{
		if (RealDataList[i].nodeType == 8/*COMMENT_NODE*/)
			continue;

		// realtime data id
		var dataIdx = RealDataList[i].attributes.getNamedItem("id").nodeValue;
		var dataID = getAssociateUuid(dataIdx);
		
		// get the corresponding svg node according data id
		var svgNode = svgDoc.getElementById(dataID);
		
		if (svgNode == null)
			continue;

		// YC meta
		if (svgNode.getAttribute("type") == "yc")
		{
			var transElem = svgNode.firstChild.nextSibling;
			var textElem = transElem.firstChild.nextSibling;
			textElem.firstChild.data = RealDataList[i].firstChild.data + textElem.getAttribute("unit");
			continue;
		}
		else if (svgNode.getAttribute("type") == "xx") // no reladata for this meta
		{
			continue;
		}

		// YX meta

		// get the powerMeta id (appended with status)
		var powerMetaIdS = GetPowerMetaIdS(svgNode);

		// the current powerMeta status
		var strLst = powerMetaIdS.split("_");
		var powerMetaId = strLst[0] + "_" + strLst[1];
		var powerMetaStatus = strLst[2];

		// compare the powerMeta status to decide whether update needed
		if (powerMetaStatus != RealDataList[i].firstChild.data)
		{
			var powerMetaIdSNew = powerMetaId + "_" + RealDataList[i].firstChild.data;

			// Has this powerMeta been defined in the svg
			var powerMetaDefNode = svgDoc.getElementById(powerMetaIdSNew); 
			if (powerMetaDefNode == null) // if the powerMeta hasn't been defined, crate a new define node first
			{	/*
				var powerMeta = GetPowerMeta(powerMetaIdSNew);
				var powerMetaDef = svgDoc.createElement("defs");
				createSVGItem(svgDoc, powerMeta, powerMetaDef);
				svgRoot.insertBefore(powerMetaDef, svgNode);*/
				continue;
			}

			var useElem = svgNode.getElementsByTagName("use").item(0);
			useElem.setAttribute("xlink:href", "#" + powerMetaIdSNew);
		}
	}

}
/*
function createSVGItem(doc, srcItem, parentItem)
{
	var j=0;
	var newItem = doc.createElement(srcItem.nodeName);
	for (j=0; j<srcItem.attributes.length; j++)
	{
		newItem.setAttribute(srcItem.attributes[j].nodeName, srcItem.attributes[j].nodeValue);
	}
	
	var childItem = srcItem.firstChild;
	while (childItem)
	{
		createSVGItem(doc, childItem, newItem);
		childItem = childItem.nextSibling;
	}
	parentItem.appendChild(newItem);

}
*/
/*
function GetPowerMeta(ids)
{
	var strParse = "g[@id = \"" + ids + "\"]";
	var nodesList = SvgMeats.documentElement.selectNodes(strParse);
	return nodesList[0];
}
*/
function GetPowerMetaIdS(svgNode)
{
	var transElem= svgNode.firstChild.nextSibling;
	var useElem = transElem.getElementsByTagName("use").item(0);

	var s = useElem.getAttribute("xlink:href");
	var y = s.substring(1, s.length);
	return y;
}
function resetSvgSize()
{
		var htmlObj = document.getElementById("svgitem");
		var svgDoc = htmlObj.getSVGDocument();
		var svgRoot = svgDoc.documentElement;
		var svgWidth=svgRoot.attributes.getNamedItem("width").nodeValue;
		var svgHeight=svgRoot.attributes.getNamedItem("height").nodeValue;
		htmlObj.setAttribute("width", svgWidth);
		htmlObj.setAttribute("height", svgHeight);

		RefreshData();
}

function getAssociateUuid(idx)
{
		var htmlObj = document.getElementById("svgitem");
		var svgDoc = htmlObj.getSVGDocument();
		var svgRoot = svgDoc.documentElement;
		var associateRoot = svgRoot.getElementsByTagName("associate").item(0);
		var associateItems = associateRoot.getElementsByTagName("dev");
		var nlen = associateItems.length;
		
		for (i=0; i<nlen; i++)
		{
			var devAssociate = associateItems.item(i);
			if (devAssociate.getAttribute("idx") == idx)
			{
				return devAssociate.getAttribute("meta");
			}
		}

		return "";
}
/*setTimeout("resetSvgSize()", 500, "JavaScript");*/

function scaleSvg(num)
{
   // ����VIEWERʵ�ֵķŴ���С
   var root;
   root=svg.getSVGDocument().documentElement;
   svg.style.width=root.attributes.getNamedItem("width").nodeValue*num;
   svg.style.height=root.attributes.getNamedItem("height").nodeValue*num;
   root.currentScale =  num;
}